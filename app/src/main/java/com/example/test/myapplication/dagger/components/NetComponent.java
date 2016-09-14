package com.example.test.myapplication.dagger.components;

import com.example.test.myapplication.dagger.modules.AppModule;
import com.example.test.myapplication.dagger.modules.NetModule;
import com.example.test.myapplication.viewmodels.SearchFragmentViewModel;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(SearchFragmentViewModel viewModel);
}
