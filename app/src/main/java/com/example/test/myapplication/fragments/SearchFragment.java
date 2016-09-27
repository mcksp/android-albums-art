package com.example.test.myapplication.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.myapplication.DatabaseHelper;
import com.example.test.myapplication.R;
import com.example.test.myapplication.adapters.AlbumsAdapter;
import com.example.test.myapplication.databinding.FragmentSearchBinding;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.viewmodels.SearchFragmentViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by test on 26/08/16.
 */

public class SearchFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {

    public static final String TAG = SearchFragment.class.getSimpleName();


    private static final String ALBUM_KEY = "ALBUM_KEY";
    private SearchFragmentViewModel viewModel;
    private Album albumToChange = null;

    public static SearchFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putParcelable(ALBUM_KEY, album);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSearchBinding binding = FragmentSearchBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        if (getArguments() != null && getArguments().containsKey(ALBUM_KEY)) {
            albumToChange = getArguments().getParcelable(ALBUM_KEY);
        }
        setViewModel(albumToChange);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void setViewModel(Album album) {
        viewModel = new SearchFragmentViewModel(getContext(), album, this);
    }


    @Override
    public void onAlbumClicked(Album album) {
        createSetAlbumCoverDialog(album.bigAlbumArt);
    }

    private void createSetAlbumCoverDialog(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_album_cover, null);
        Picasso.with(getContext()).load(url).into((ImageView) dialogView.findViewById(R.id.cover_art_preview));
        builder.setView(dialogView);
        builder.setPositiveButton("SET", (dialog, which) -> {
            Picasso.with(getContext())
                    .load(url)
                    .into(new DownloadTarget());
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }


    private class DownloadTarget implements Target {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            DatabaseHelper.setAlbumCover(getContext(), bitmap, albumToChange);
            getActivity().getSupportFragmentManager().popBackStack();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
