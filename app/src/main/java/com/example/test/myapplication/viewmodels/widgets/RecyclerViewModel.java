package com.example.test.myapplication.viewmodels.widgets;

import android.databinding.Bindable;
import android.support.v7.widget.RecyclerView;

import com.example.test.myapplication.BR;

public class RecyclerViewModel extends BaseViewModel {

    @Bindable
    public boolean fixedSize;

    public void setFixedSize(boolean fixedSize) {
        this.fixedSize = fixedSize;
        notifyPropertyChanged(BR.fixedSize);
    }

    @Bindable
    public int scrollPosition;

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
        notifyPropertyChanged(BR.scrollPosition);
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    @Bindable
    public RecyclerView.Adapter adapter;

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public RecyclerView.LayoutManager layoutManager;

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    @Bindable
    public RecyclerView.OnScrollListener scrollListener;

    public void setScrollListener(RecyclerView.OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
        notifyPropertyChanged(BR.scrollListener);
    }


}
