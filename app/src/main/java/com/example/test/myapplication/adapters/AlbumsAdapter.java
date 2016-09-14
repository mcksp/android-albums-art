package com.example.test.myapplication.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.myapplication.R;
import com.example.test.myapplication.databinding.AlbumItemBinding;
import com.example.test.myapplication.models.Album;

import java.util.ArrayList;

/**
 * Created by maciej on 14.09.16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Album> albums;
    private OnAlbumClick onAlbumClick;

    public AlbumsAdapter(ArrayList<Album> albums, OnAlbumClick onAlbumClick) {
        this.albums = albums;
        this.onAlbumClick = onAlbumClick;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        AlbumViewHolder holder = new AlbumViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Album album = albums.get(position);
        ((AlbumViewHolder) holder).getBinding().setViewModel(album);
        ((AlbumViewHolder) holder).getBinding().executePendingBindings();
        ((AlbumViewHolder) holder).getBinding().getRoot().setOnClickListener(v -> onAlbumClick.onAlbumClicked(album.id));

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private AlbumItemBinding binding;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public AlbumItemBinding getBinding() {
            return binding;
        }
    }

    public static interface OnAlbumClick {
        public void onAlbumClicked(long id);
    }
}
