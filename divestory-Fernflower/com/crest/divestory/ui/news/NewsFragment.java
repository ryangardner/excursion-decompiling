package com.crest.divestory.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NewsFragment extends Fragment {
   private NewsViewModel newsViewModel;

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      this.newsViewModel = (NewsViewModel)ViewModelProviders.of((Fragment)this).get(NewsViewModel.class);
      View var4 = var1.inflate(2131427396, var2, false);
      TextView var5 = (TextView)var4.findViewById(2131231393);
      this.newsViewModel.getText().observe(this.getViewLifecycleOwner(), new Observer<String>() {
         public void onChanged(String var1) {
         }
      });
      return var4;
   }
}
