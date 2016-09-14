package com.example.test.myapplication.viewmodels.widgets;

import android.databinding.Bindable;

import com.example.test.myapplication.BR;


public class TextViewModel extends BaseViewModel {

    @Bindable
    public String text;

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    public String getText() {
        return text;
    }

    public TextViewModel(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }
}
