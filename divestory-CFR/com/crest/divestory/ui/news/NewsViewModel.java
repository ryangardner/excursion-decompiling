/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.crest.divestory.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel
extends ViewModel {
    private MutableLiveData<String> mText;

    public NewsViewModel() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<String>();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}

