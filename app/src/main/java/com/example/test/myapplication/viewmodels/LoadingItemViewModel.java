package com.example.test.myapplication.viewmodels;

import com.example.test.myapplication.models.LoadingItemModel;
import com.example.test.myapplication.viewmodels.widgets.BaseViewModel;
import com.example.test.myapplication.viewmodels.widgets.TextViewModel;

public class LoadingItemViewModel {
    public TextViewModel title;
    public BaseViewModel progress;

    public LoadingItemViewModel(LoadingItemModel model) {
        title = new TextViewModel(model.getText());
        progress = new BaseViewModel(model.isProgressBarVisibility());
    }
}
