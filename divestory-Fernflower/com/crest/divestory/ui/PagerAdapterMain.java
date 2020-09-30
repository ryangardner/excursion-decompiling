package com.crest.divestory.ui;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.AppBase;
import com.crest.divestory.ui.logs.FragmentDiveLogsList;
import com.crest.divestory.ui.settings.FragmentAppSettings;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;

public class PagerAdapterMain extends FragmentStatePagerAdapter {
   final int PAGES_COUNT = 3;

   public PagerAdapterMain(FragmentManager var1) {
      super(var1);
   }

   public void destroyItem(View var1, int var2, Object var3) {
      ((ViewPager)var1).removeView((View)var3);
   }

   public int getCount() {
      return 3;
   }

   public Fragment getItem(int var1) {
      Object var2;
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               var2 = null;
            } else {
               var2 = new FragmentAppSettings();
               AppBase.fragmentAppSettings = (FragmentAppSettings)var2;
            }
         } else {
            var2 = new FragmentDiveLogsList();
            AppBase.fragmentDiveLogsList = (FragmentDiveLogsList)var2;
         }
      } else {
         var2 = new FragmentSyncedWatchesList();
         AppBase.fragmentSyncedWatchesList = (FragmentSyncedWatchesList)var2;
      }

      return (Fragment)var2;
   }

   public int getItemPosition(Object var1) {
      return -2;
   }

   public Object instantiateItem(ViewGroup var1, int var2) {
      return super.instantiateItem(var1, var2);
   }
}
