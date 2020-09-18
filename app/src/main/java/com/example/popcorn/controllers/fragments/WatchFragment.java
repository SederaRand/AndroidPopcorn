package com.example.popcorn.controllers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.popcorn.R;


public class WatchFragment extends Fragment {

    WebView webViews;


    public WatchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_watch, container, false);

        webViews = (WebView)result.findViewById(R.id.html_res);

        WebSettings ws = webViews.getSettings();
        ws.setJavaScriptEnabled(true);

        webViews.loadUrl("file:///android_asset/header_watch.html");

        return result;
    }
}