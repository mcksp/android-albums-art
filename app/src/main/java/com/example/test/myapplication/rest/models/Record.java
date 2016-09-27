package com.example.test.myapplication.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 26/08/16.
 */

public class Record {

    @SerializedName("images")
    @Expose
    public List<Image> images = new ArrayList<>();
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("uri")
    @Expose
    public String uri;

    public String getImageUrl() {
        return images.size() > 1 ? images.get(1).url : getBigImageUrl();
    }

    public String getBigImageUrl() {
        return images.size() > 0 ? images.get(0).url : "empty path";
    }

}
