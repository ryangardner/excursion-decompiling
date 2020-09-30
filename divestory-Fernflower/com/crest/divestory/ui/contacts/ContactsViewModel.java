package com.crest.divestory.ui.contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactsViewModel extends ViewModel {
   private MutableLiveData<String> mText;

   public ContactsViewModel() {
      MutableLiveData var1 = new MutableLiveData();
      this.mText = var1;
      var1.setValue("This is Contacts fragment");
   }

   public LiveData<String> getText() {
      return this.mText;
   }
}
