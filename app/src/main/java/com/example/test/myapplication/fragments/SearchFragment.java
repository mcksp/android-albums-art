package com.example.test.myapplication.fragments;

import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.myapplication.adapters.AlbumsAdapter;
import com.example.test.myapplication.databinding.FragmentSearchBinding;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.viewmodels.SearchFragmentViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

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
        String search = "";
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
    public void onAlbumClicked(long id) {
        String url = "";
        for (Album album : viewModel.getAlbums()) {
            if (album.id == id) {
                url = album.albumArt;
                break;
            }
        }
        Toast.makeText(getContext(), Long.toString(id), Toast.LENGTH_LONG).show();


        Picasso.with(getContext())
                .load(url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        setAlbumCover(bitmap);
                        Toast.makeText(getContext(), "Downloaded", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Toast.makeText(getContext(), "onprepare", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void setAlbumCover(Bitmap bitmap) {
        Toast.makeText(getContext(), bitmap.toString(), Toast.LENGTH_LONG).show();
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        int lol = getContext().getContentResolver().delete(ContentUris.withAppendedId(albumArtUri, albumToChange.id), null, null);
        Log.d("DELETED", String.valueOf(lol));


        String filename = Environment.getExternalStorageDirectory().getPath() + "/albumthumbs/" + Long.toString(Calendar.getInstance().getTimeInMillis());

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        ContentValues values = new ContentValues();
        values.put("album_id", albumToChange.id);
        values.put("_data", filename);

        ContentValues values2 = new ContentValues();
        Uri num_updates = getContext().getContentResolver().insert(albumArtUri, values);
        Log.d("NUM UPDATES", num_updates.toString());
    }
}
