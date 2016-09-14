package com.example.test.myapplication.viewmodels.widgets;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.test.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by test on 26/08/16.
 */

public class ImageViewModel extends BaseViewModel {


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl){
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
