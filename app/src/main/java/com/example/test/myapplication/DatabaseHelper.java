package com.example.test.myapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.test.myapplication.models.Album;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by maciej on 27/09/16.
 */
public class DatabaseHelper {

    public static void setAlbumCover(Context context, Bitmap bitmap, Album album) {
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        int lol = context.getContentResolver().delete(ContentUris.withAppendedId(albumArtUri, album.id), null, null);
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
        values.put("album_id", album.id);
        values.put("_data", filename);

        ContentValues values2 = new ContentValues();
        Uri num_updates = context.getContentResolver().insert(albumArtUri, values);
        Log.d("NUM UPDATES", num_updates.toString());
    }

    public static Cursor getAlbumsCursor(Context context) {
        return getAlbumsCursor(context, -1);
    }

    public static Cursor getAlbumsCursor(Context context, long id) {
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
}
