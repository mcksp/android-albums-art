package com.example.test.myapplication.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.test.myapplication.R;
import com.example.test.myapplication.adapters.AlbumsAdapter;
import com.example.test.myapplication.databinding.FragmentBrowseAlbumsBinding;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.viewmodels.BrowseAlbumsFragmentViewModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by maciej on 14.09.16.
 */
public class BrowseAlbumsFragment extends Fragment implements AlbumsAdapter.OnAlbumClick {
    public static final String TAG = BrowseAlbumsFragment.class.getSimpleName();

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
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                Album album = new Album(nameAlbum, artist, albumArt, "file://" + albumArt, id);
                albums.add(album);
            } while (cursor.moveToNext());
        }
        // setNewArt(albums.get(2), Uri.parse(albums.get(2).albumArt), albums.get(2).id);
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
        final String xddd = MediaStore.Audio.Albums.FIRST_YEAR;
        final String[] columns = {_id, album_name, artist, albumArt, songsNumber, year, _id};
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


        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        int lol = getContext().getContentResolver().delete(ContentUris.withAppendedId(albumArtUri, album.id), null, null);
        Log.d("DELETED", String.valueOf(lol));


        String filename = Environment.getExternalStorageDirectory().getPath() + "/albumthumbs/" + Long.toString(Calendar.getInstance().getTimeInMillis());
        Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.ic_launcher);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
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
        values.put("album_id", album.id);
        values.put("_data", filename);
        Uri num_updates = getContext().getContentResolver().insert(albumArtUri, values);




       /* SearchFragment fragment = SearchFragment.newInstance(album.title, album.albumArt);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(fragment.TAG);

        transaction.commit();*/


    }
}
