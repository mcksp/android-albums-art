package com.example.test.myapplication.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by test on 26/08/16.
 */

public class Song {

    @SerializedName("artists")
    @Expose
    public List<Artist> artists;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("album")
    @Expose
    public Album album;

    public String getFirstArtist(){
        return artists.get(0).name;
    }

}
