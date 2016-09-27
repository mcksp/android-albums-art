package com.example.test.myapplication.viewmodels;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.test.myapplication.TestApp;
import com.example.test.myapplication.adapters.AlbumsAdapter;
import com.example.test.myapplication.models.Album;
import com.example.test.myapplication.rest.ApiService;
import com.example.test.myapplication.rest.models.Record;
import com.example.test.myapplication.rest.models.SearchData;
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

    private ArrayList<Album> albums = new ArrayList<>();

    public EditTextViewModel searchText;
    public ButtonViewModel searchButton;
    public RecyclerViewModel recyclerView;

    public AlbumsAdapter adapter;

    private boolean internetWorking = true;
    private boolean fetching = false;
    private Album album;

    public SearchFragmentViewModel(Context context, Album album, AlbumsAdapter.OnAlbumClick onAlbumClick) {
        initWidgets();
        this.album = album;
        adapter = new AlbumsAdapter(albums, onAlbumClick);
        ((TestApp) context.getApplicationContext()).getNetComponent().inject(this);
        initList(context);
        searchText.setText(formatString(album.title));
        fetchSongs();
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
            albums.clear();
            adapter.notifyDataSetChanged();
            fetchSongs();
        }
    }

    public void fetchSongs() {
        if (internetWorking && !fetching) {
            fetching = true;
            loading();
            Observable<SearchData> tracksObservable = apiService.getSongs(searchText.getText(), "album", ITEMS_PER_PAGE, lastItemPos);
            tracksObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(throwable -> {
                        onError();
                        internetWorking = false;
                        fetching = false;
                    })
                    .onErrorResumeNext(throwable1 -> Observable.empty())
                    .subscribe(data -> {
                        if (data != null && data.data != null && data.data.records != null) {
                            for (Record album : data.data.records) {
                                Album a = new Album(album.name, album.type, album.uri, album.getImageUrl(), album.getBigImageUrl(), albums.size());
                                albums.add(a);
                            }

                            adapter.notifyDataSetChanged();
                            lastItemPos = albums.size();
                            if (data.data.records.size() < ITEMS_PER_PAGE) {
                                allFetched();
                            }
                        }
                        fetching = false;
                    });
        }
    }

    public void allFetched() {
    }

    public void onError() {
    }

    public void loading() {
    }

    private static String formatString(String str) {
        str = str.replaceAll("\\(.*?\\) ?", "");
        str = str.replaceAll("\\[.*?\\] ?", "");
        str = str.replaceAll("\\<.*?\\> ?", "");
        return str.trim();
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
}
