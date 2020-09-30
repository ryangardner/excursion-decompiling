/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.crest.divestory.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import com.crest.divestory.ui.news.NewsViewModel;

public class NewsFragment
extends Fragment {
    private NewsViewModel newsViewModel;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        layoutInflater = layoutInflater.inflate(2131427396, viewGroup, false);
        viewGroup = (TextView)layoutInflater.findViewById(2131231393);
        this.newsViewModel.getText().observe(this.getViewLifecycleOwner(), new Observer<String>(){

            @Override
            public void onChanged(String string2) {
            }
        });
        return layoutInflater;
    }

}

