package com.example.test.myapplication.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.myapplication.DatabaseHelper;
import com.example.test.myapplication.R;
import com.example.test.myapplication.adapters.AlbumsAdapter;
import com.example.test.myapplication.databinding.FragmentBrowseAlbumsBinding;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.viewmodels.BrowseAlbumsFragmentViewModel;

import java.util.ArrayList;

/**
 * Created by maciej on 14.09.16.
 */
public class BrowseAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {
    public static final String TAG = BrowseAlbumsFragment.class.getSimpleName();

    private BrowseAlbumsFragmentViewModel viewModel;
    private ArrayList<Album> albums = new ArrayList<>();
    private Album choosedAlbum = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAlbumsData();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshChoosedAlbum();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBrowseAlbumsBinding binding = FragmentBrowseAlbumsBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        setViewModel();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void readAlbumsData() {
        Cursor cursor = DatabaseHelper.getAlbumsCursor(getContext());
        saveAlbumsToArray(cursor);
    }

    private void saveAlbumsToArray(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String nameAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                Album album = new Album(nameAlbum, artist, albumArt, "file://" + albumArt, id);
                albums.add(album);
            } while (cursor.moveToNext());
        }
    }

    private void refreshChoosedAlbum() {
        if (choosedAlbum != null) {
            Cursor cursor = DatabaseHelper.getAlbumsCursor(getContext(), choosedAlbum.id);
            if (cursor.moveToFirst()) {
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                choosedAlbum.albumArt = "file://" + albumArt;
                viewModel.notifyAdapter();
            }
        }
        choosedAlbum = null;
    }

    private void setViewModel() {
        viewModel = new BrowseAlbumsFragmentViewModel(getContext(), albums, this);
    }

    @Override
    public void onAlbumClicked(Album album) {
        choosedAlbum = album;

        SearchFragment fragment = SearchFragment.newInstance(album);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(SearchFragment.TAG);
        transaction.commit();
    }
}
