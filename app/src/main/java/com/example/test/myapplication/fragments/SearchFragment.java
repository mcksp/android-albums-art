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

    public static final String TAG = SearchFragment.class.getSimpleName();


    private static final String SEARCH_KEY = "SEARCH_KEY";
    private static final String PATH_KEY = "PATH_KEY";
    private SearchFragmentViewModel viewModel;

    String path;

    public static SearchFragment newInstance(String search, String path) {

        Bundle args = new Bundle();
        search = formatString(search);
        args.putString(SEARCH_KEY, search);
        args.putString(PATH_KEY, path);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSearchBinding binding = FragmentSearchBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        String search = "";
        if (getArguments() != null && getArguments().containsKey(SEARCH_KEY)) {
            search = getArguments().getString(SEARCH_KEY);
            path = getArguments().getString(PATH_KEY);
        }
        setViewModel(search);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void setViewModel(String search) {
        viewModel = new SearchFragmentViewModel(getContext(), search);
    }

    private static String formatString(String str) {
        str = str.replaceAll("\\(.*?\\) ?", "");
        str = str.replaceAll("\\[.*?\\] ?", "");
        str = str.replaceAll("\\<.*?\\> ?", "");
        return str.trim();
    }
}
