package com.example.test.myapplication.viewmodels.widgets;

import android.databinding.Bindable;
import android.view.View;

import com.example.test.myapplication.BR;


public class ButtonViewModel extends TextViewModel {

    @Bindable
    public View.OnClickListener onClickListener;

    public ButtonViewModel(String text, View.OnClickListener onClickListener) {
        super(text);
        this.onClickListener = onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        notifyPropertyChanged(BR.onClickListener);
    }
}
