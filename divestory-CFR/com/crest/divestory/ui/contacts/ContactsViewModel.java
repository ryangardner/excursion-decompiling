/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.crest.divestory.ui.contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactsViewModel
extends ViewModel {
    private MutableLiveData<String> mText;

    public ContactsViewModel() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<String>();
        this.mText = mutableLiveData;
        mutableLiveData.setValue("This is Contacts fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}

