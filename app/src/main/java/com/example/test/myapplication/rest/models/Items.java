package com.example.test.myapplication.rest.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciej on 15.09.16.
 */
public class Items {
    @SerializedName("items")
    @Expose
    public List<Record> records = new ArrayList<>();
}
