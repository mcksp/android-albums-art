package com.example.test.myapplication.models;

public class LoadingItemModel {
    private String text;
    private boolean progressBarVisibility;
    private int position;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(boolean progressBarVisibility) {
        this.progressBarVisibility = progressBarVisibility;
    }

    public LoadingItemModel(String text, boolean progressBarVisibility) {
        this.text = text;
        this.progressBarVisibility = progressBarVisibility;
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void allFetched() {
        text = "That's all!";
        progressBarVisibility = false;
    }

    public void onError() {
        text = "Check your internet connection.";
        progressBarVisibility = false;
    }

    public void loading() {
        text = "Loading more items...";
        progressBarVisibility = true;
    }

    public void onStart() {
        text = "";
        progressBarVisibility = false;
    }
}
