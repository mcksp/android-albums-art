package com.example.test.myapplication.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.myapplication.databinding.FragmentBrowseAlbumsBinding;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.viewmodels.BrowseAlbumsFragmentViewModel;

import java.util.ArrayList;

/**
 * Created by maciej on 14.09.16.
 */
public class BrowseAlbumsFragment extends Fragment {

    private BrowseAlbumsFragmentViewModel viewModel;
    private ArrayList<Album> albums = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cursor = getAlbumAlbumcursor(getContext());
        if (cursor.moveToFirst()) {
            do {
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String nameAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                Album album = new Album(nameAlbum, artist, albumArt, "file://" + albumArt);
                albums.add(album);
            } while (cursor.moveToNext());
        }
        setViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBrowseAlbumsBinding binding = FragmentBrowseAlbumsBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void setViewModel() {
        viewModel = new BrowseAlbumsFragmentViewModel(getContext(), albums);
    }

    public Cursor getAlbumAlbumcursor(Context context) {
        String where = null;
        ContentResolver cr = context.getContentResolver();
        final Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final String _id = MediaStore.Audio.Albums._ID;
        final String album_id = MediaStore.Audio.Albums.ALBUM_ID;
        final String album_name = MediaStore.Audio.Albums.ALBUM;
        final String artist = MediaStore.Audio.Albums.ARTIST;
        final String albumArt = MediaStore.Audio.Albums.ALBUM_ART;
        final String songsNumber = MediaStore.Audio.Albums.NUMBER_OF_SONGS;
        final String year = MediaStore.Audio.Albums.FIRST_YEAR;
        final String[] columns = {_id, album_name, artist, albumArt, songsNumber, year};
        return cr.query(uri, columns, where, null, null);
    }
}
