package com.example.test.myapplication.viewmodels;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.test.myapplication.TestApp;
import com.example.test.myapplication.adapters.SongsAdapter;
import com.example.test.myapplication.models.LoadingItemModel;
import com.example.test.myapplication.rest.ApiService;
import com.example.test.myapplication.rest.models.SearchData;
import com.example.test.myapplication.rest.models.Song;
import com.example.test.myapplication.viewmodels.widgets.ButtonViewModel;
import com.example.test.myapplication.viewmodels.widgets.EditTextViewModel;
import com.example.test.myapplication.viewmodels.widgets.RecyclerViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by test on 26/08/16.
 */

public class SearchFragmentViewModel {

    @Inject
    ApiService apiService;

    public static final int ITEMS_PER_PAGE = 20;
    private int lastItemPos = 0;

    private ArrayList<Song> songs = new ArrayList<>();

    public EditTextViewModel searchText;
    public ButtonViewModel searchButton;
    public RecyclerViewModel recyclerView;
    public LoadingItemModel loadingItemModel = new LoadingItemModel("Loading more items...", true);

    public SongsAdapter adapter;

    private boolean internetWorking = true;
    private boolean fetching = false;

    public SearchFragmentViewModel(Context context) {
        initWidgets();
        adapter = new SongsAdapter(songs, loadingItemModel);
        ((TestApp) context.getApplicationContext()).getNetComponent().inject(this);
        initList(context);
        onStart();
    }

    private void initWidgets() {
        searchButton = new ButtonViewModel("search", v -> resetAndFetchSongs());
        searchText = new EditTextViewModel("");
    }

    private void initList(Context context) {
        recyclerView = new RecyclerViewModel();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setFixedSize(true);
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                SearchFragmentViewModel.this.recyclerView.scrollPosition = (((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
                if ((((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()) > lastItemPos - 2 &&
                        (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()) > 2) {
                    fetchSongs();
                }
            }
        };
        recyclerView.setScrollListener(scrollListener);
    }


    public void resetAndFetchSongs() {
        if (searchText.text.length() > 0) {
            internetWorking = true;
            lastItemPos = 0;
            songs.clear();
            adapter.notifyDataSetChanged();
            fetchSongs();
        }
    }

    public void fetchSongs() {
        if (internetWorking && !fetching) {
            fetching = true;
            loading();
            Observable<SearchData> tracksObservable = apiService.getSongs(searchText.getText(), "track", ITEMS_PER_PAGE, lastItemPos);
            tracksObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(throwable -> {
                        onError();
                        internetWorking = false;
                        fetching = false;
                    })
                    .onErrorResumeNext(throwable1 -> Observable.empty())
                    .subscribe(data -> {
                        if (data != null && data.tracks != null && data.tracks.songs != null) {
                            songs.addAll(data.tracks.songs);
                            adapter.notifyDataSetChanged();
                            lastItemPos = songs.size();
                            if (data.tracks.songs.size() < ITEMS_PER_PAGE) {
                                allFetched();
                            }
                        }
                        fetching = false;
                    });
        }
    }

    public void allFetched() {
        loadingItemModel.allFetched();
        adapter.notifyItemChanged(loadingItemModel.getPosition());
    }

    public void onError() {
        loadingItemModel.onError();
        adapter.notifyItemChanged(loadingItemModel.getPosition());
    }

    public void loading() {
        loadingItemModel.loading();
        adapter.notifyItemChanged(loadingItemModel.getPosition());
    }

    public void onStart() {
        loadingItemModel.onStart();
        adapter.notifyItemChanged(loadingItemModel.getPosition());
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
