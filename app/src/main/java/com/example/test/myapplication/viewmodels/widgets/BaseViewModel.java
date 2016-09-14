package com.example.test.myapplication.viewmodels.widgets;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.test.myapplication.BR;

public class BaseViewModel extends BaseObservable {

    public BaseViewModel() {}

    public BaseViewModel(int visibility) {
        setVisibility(visibility);
    }

    public BaseViewModel(boolean visibility) {
        setVisibility(visibility);
    }

    @Bindable
    public int visibility;

    public void setVisibility(boolean visible) {
        visibility = visible ? View.VISIBLE : View.GONE;
        notifyPropertyChanged(BR.visibility);
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
        notifyPropertyChanged(BR.visibility);
    }
}
