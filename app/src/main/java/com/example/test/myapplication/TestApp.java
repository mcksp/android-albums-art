package com.example.test.myapplication;

import android.app.Application;

import com.example.test.myapplication.dagger.components.DaggerNetComponent;
import com.example.test.myapplication.dagger.components.NetComponent;
import com.example.test.myapplication.dagger.modules.NetModule;

/**
 * Created by test on 26/08/16.
 */

public class TestApp extends Application {

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {

        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule("https://api.spotify.com/v1/"))
                .build();

    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

}
