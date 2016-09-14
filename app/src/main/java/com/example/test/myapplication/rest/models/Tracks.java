package com.example.test.myapplication.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by test on 26/08/16.
 */

public class Tracks {

    @SerializedName("items")
    @Expose
    public List<Song> songs;

}
