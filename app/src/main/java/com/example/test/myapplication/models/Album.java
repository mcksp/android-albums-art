package com.example.test.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maciej on 14.09.16.
 */
public class Album implements Parcelable {
    public String title;
    public String author;
    public String year;
    public String albumArt;
    public long id;

    public Album(String title, String author, String year, String albumArt, long id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.albumArt = albumArt;
        this.id = id;
    }


    protected Album(Parcel in) {
        title = in.readString();
        author = in.readString();
        year = in.readString();
        albumArt = in.readString();
        id = in.readLong();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(year);
        dest.writeString(albumArt);
        dest.writeLong(id);
    }
}
