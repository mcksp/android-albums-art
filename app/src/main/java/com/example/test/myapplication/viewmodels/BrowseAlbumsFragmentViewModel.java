package com.example.test.myapplication.viewmodels;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.example.test.myapplication.adapters.AlbumsAdapter;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.viewmodels.widgets.RecyclerViewModel;

import java.util.ArrayList;

/**
 * Created by maciej on 14.09.16.
 */
public class BrowseAlbumsFragmentViewModel {

    public RecyclerViewModel recyclerView;
    private AlbumsAdapter adapter;
    private ArrayList<Album> albums = new ArrayList<>();

    public BrowseAlbumsFragmentViewModel(Context context, ArrayList<Album> albums, AlbumsAdapter.OnAlbumClick onAlbumClick) {
        this.albums = albums;
        adapter = new AlbumsAdapter(this.albums, onAlbumClick);
        initList(context);
    }


    private void initList(Context context) {
        recyclerView = new RecyclerViewModel();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setFixedSize(true);
    }


}
