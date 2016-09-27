package com.example.test.myapplication.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Cursor cursor = getAlbumsCursor(getContext());
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

    @Override
    public void onResume() {
        super.onResume();
        if (choosedAlbum != null) {
            Cursor cursor = getAlbumsCursor(getContext(), choosedAlbum.id);
            if (cursor.moveToFirst()) {
                do {
                    String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    choosedAlbum.albumArt = "file://" + albumArt;
                    viewModel.notifyAdapter();
                } while (cursor.moveToNext());
            }
        }
        choosedAlbum = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBrowseAlbumsBinding binding = FragmentBrowseAlbumsBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        setViewModel();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void setViewModel() {
        viewModel = new BrowseAlbumsFragmentViewModel(getContext(), albums, this);
    }

    public Cursor getAlbumsCursor(Context context) {
        return getAlbumsCursor(context, -1);
    }

    public Cursor getAlbumsCursor(Context context, long id) {
        String where = null;
        if (id != -1) {
            where = MediaStore.Audio.Albums._ID + " = " + Long.toString(id);
        }
        ContentResolver cr = context.getContentResolver();
        final Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final String _id = MediaStore.Audio.Albums._ID;
        final String album_name = MediaStore.Audio.Albums.ALBUM;
        final String artist = MediaStore.Audio.Albums.ARTIST;
        final String albumArt = MediaStore.Audio.Albums.ALBUM_ART;
        final String[] columns = {_id, album_name, artist, albumArt};
        return cr.query(uri, columns, where, null, null);
    }

    @Override
    public void onAlbumClicked(long id) {
        Album album = null;

        for (Album i : albums) {
            if (i.id == id) {
                album = i;
                break;
            }
        }
        choosedAlbum = album;

        SearchFragment fragment = SearchFragment.newInstance(album);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(SearchFragment.TAG);
        transaction.commit();
    }


}
