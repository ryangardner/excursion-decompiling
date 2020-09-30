package com.crest.divestory.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {
   private MutableLiveData<String> mText;

   public NewsViewModel() {
      MutableLiveData var1 = new MutableLiveData();
      this.mText = var1;
      var1.setValue("This is home fragment");
   }

   public LiveData<String> getText() {
      return this.mText;
   }
}
