package com.example.test.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.myapplication.databinding.FragmentSearchBinding;
import com.example.test.myapplication.viewmodels.SearchFragmentViewModel;

/**
 * Created by test on 26/08/16.
 */

public class SearchFragment extends Fragment {

    private SearchFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSearchBinding binding = FragmentSearchBinding.inflate(getLayoutInflater(savedInstanceState),container,false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void setViewModel() {
        viewModel = new SearchFragmentViewModel(getContext());
    }
}
