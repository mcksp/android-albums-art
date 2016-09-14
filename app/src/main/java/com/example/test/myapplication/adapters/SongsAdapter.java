package com.example.test.myapplication.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.myapplication.R;
import com.example.test.myapplication.databinding.LoadingItemBinding;
import com.example.test.myapplication.databinding.SongItemBinding;
import com.example.test.myapplication.models.LoadingItemModel;
import com.example.test.myapplication.rest.models.Song;
import com.example.test.myapplication.viewmodels.LoadingItemViewModel;

import java.util.ArrayList;

/**
 * Created by test on 26/08/16.
 */

public class SongsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Song> songs;
    private LoadingItemModel loadingItemModel;

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    public SongsAdapter(ArrayList<Song> songs, LoadingItemModel loadingItemModel) {
        this.songs = songs;
        this.loadingItemModel = loadingItemModel;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
            NoteViewHolder holder = new NoteViewHolder(v);
            return holder;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoteViewHolder) {
            Song song = songs.get(position);
            ((NoteViewHolder) holder).getBinding().setViewModel(song);
            ((NoteViewHolder) holder).getBinding().executePendingBindings();
        } else {
            ((LoadingViewHolder) holder).getBinding().setViewModel(new LoadingItemViewModel(loadingItemModel));
            loadingItemModel.setPosition(position);
        }
    }

    @Override
    public int getItemCount() {
        return songs != null ? songs.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= songs.size()) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ITEM;
    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private SongItemBinding binding;

        public NoteViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public SongItemBinding getBinding() {
            return binding;
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private LoadingItemBinding binding;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public LoadingItemBinding getBinding() {
            return binding;
        }
    }

}
