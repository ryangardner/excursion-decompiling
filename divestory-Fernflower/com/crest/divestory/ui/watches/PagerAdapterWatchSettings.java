package com.crest.divestory.ui.watches;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PagerAdapterWatchSettings extends FragmentStatePagerAdapter {
   Context context;
   private int pages_count = 3;

   public PagerAdapterWatchSettings(FragmentManager var1) {
      super(var1);
   }

   public void destroyItem(View var1, int var2, Object var3) {
      ((ViewPager)var1).removeView((View)var3);
   }

   public int getCount() {
      return this.pages_count;
   }

   public Fragment getItem(int var1) {
      Object var2;
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               var2 = null;
            } else {
               var2 = FragmentWatchSettingsFreeDive.newInstance(var1);
            }
         } else {
            var2 = FragmentWatchSettingsScuba.newInstance(var1);
         }
      } else {
         var2 = FragmentWatchSettingsBasic.newInstance(var1);
      }

      return (Fragment)var2;
   }

   public int getItemPosition(Object var1) {
      return var1 == null ? -2 : super.getItemPosition(var1);
   }

   public Object instantiateItem(ViewGroup var1, int var2) {
      return super.instantiateItem(var1, var2);
   }

   public void set_para(Context var1) {
      this.context = var1;
   }
}
