package com.crest.divestory;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class FragmentPage extends Fragment {
   private static final String ARG_PARAM1 = "param1";
   private static final String ARG_PARAM2 = "param2";
   private String mParam1;
   private String mParam2;
   public boolean needRefresh = false;
   public int page = -1;

   public static FragmentPage newInstance(String var0, String var1) {
      FragmentPage var2 = new FragmentPage();
      Bundle var3 = new Bundle();
      var3.putString("param1", var0);
      var3.putString("param2", var1);
      var2.setArguments(var3);
      return var2;
   }

   public boolean askNeedRefresh() {
      return this.needRefresh;
   }

   public int getPage() {
      return this.page;
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if (this.getArguments() != null) {
         this.mParam1 = this.getArguments().getString("param1");
         this.mParam2 = this.getArguments().getString("param2");
      }

   }

   public void setNeedRefresh(boolean var1) {
      this.needRefresh = var1;
   }

   public void setPage(int var1) {
      this.page = var1;
   }
}
