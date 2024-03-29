package com.google.android.material.tabs;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.ref.WeakReference;

public final class TabLayoutMediator {
   private RecyclerView.Adapter<?> adapter;
   private boolean attached;
   private final boolean autoRefresh;
   private TabLayoutMediator.TabLayoutOnPageChangeCallback onPageChangeCallback;
   private TabLayout.OnTabSelectedListener onTabSelectedListener;
   private RecyclerView.AdapterDataObserver pagerAdapterObserver;
   private final boolean smoothScroll;
   private final TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy;
   private final TabLayout tabLayout;
   private final ViewPager2 viewPager;

   public TabLayoutMediator(TabLayout var1, ViewPager2 var2, TabLayoutMediator.TabConfigurationStrategy var3) {
      this(var1, var2, true, var3);
   }

   public TabLayoutMediator(TabLayout var1, ViewPager2 var2, boolean var3, TabLayoutMediator.TabConfigurationStrategy var4) {
      this(var1, var2, var3, true, var4);
   }

   public TabLayoutMediator(TabLayout var1, ViewPager2 var2, boolean var3, boolean var4, TabLayoutMediator.TabConfigurationStrategy var5) {
      this.tabLayout = var1;
      this.viewPager = var2;
      this.autoRefresh = var3;
      this.smoothScroll = var4;
      this.tabConfigurationStrategy = var5;
   }

   public void attach() {
      if (!this.attached) {
         RecyclerView.Adapter var1 = this.viewPager.getAdapter();
         this.adapter = var1;
         if (var1 != null) {
            this.attached = true;
            TabLayoutMediator.TabLayoutOnPageChangeCallback var2 = new TabLayoutMediator.TabLayoutOnPageChangeCallback(this.tabLayout);
            this.onPageChangeCallback = var2;
            this.viewPager.registerOnPageChangeCallback(var2);
            TabLayoutMediator.ViewPagerOnTabSelectedListener var3 = new TabLayoutMediator.ViewPagerOnTabSelectedListener(this.viewPager, this.smoothScroll);
            this.onTabSelectedListener = var3;
            this.tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener)var3);
            if (this.autoRefresh) {
               TabLayoutMediator.PagerAdapterObserver var4 = new TabLayoutMediator.PagerAdapterObserver();
               this.pagerAdapterObserver = var4;
               this.adapter.registerAdapterDataObserver(var4);
            }

            this.populateTabsFromPagerAdapter();
            this.tabLayout.setScrollPosition(this.viewPager.getCurrentItem(), 0.0F, true);
         } else {
            throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
         }
      } else {
         throw new IllegalStateException("TabLayoutMediator is already attached");
      }
   }

   public void detach() {
      if (this.autoRefresh) {
         RecyclerView.Adapter var1 = this.adapter;
         if (var1 != null) {
            var1.unregisterAdapterDataObserver(this.pagerAdapterObserver);
            this.pagerAdapterObserver = null;
         }
      }

      this.tabLayout.removeOnTabSelectedListener(this.onTabSelectedListener);
      this.viewPager.unregisterOnPageChangeCallback(this.onPageChangeCallback);
      this.onTabSelectedListener = null;
      this.onPageChangeCallback = null;
      this.adapter = null;
      this.attached = false;
   }

   void populateTabsFromPagerAdapter() {
      this.tabLayout.removeAllTabs();
      RecyclerView.Adapter var1 = this.adapter;
      if (var1 != null) {
         int var2 = var1.getItemCount();

         int var3;
         for(var3 = 0; var3 < var2; ++var3) {
            TabLayout.Tab var4 = this.tabLayout.newTab();
            this.tabConfigurationStrategy.onConfigureTab(var4, var3);
            this.tabLayout.addTab(var4, false);
         }

         if (var2 > 0) {
            var3 = this.tabLayout.getTabCount();
            var3 = Math.min(this.viewPager.getCurrentItem(), var3 - 1);
            if (var3 != this.tabLayout.getSelectedTabPosition()) {
               TabLayout var5 = this.tabLayout;
               var5.selectTab(var5.getTabAt(var3));
            }
         }
      }

   }

   private class PagerAdapterObserver extends RecyclerView.AdapterDataObserver {
      PagerAdapterObserver() {
      }

      public void onChanged() {
         TabLayoutMediator.this.populateTabsFromPagerAdapter();
      }

      public void onItemRangeChanged(int var1, int var2) {
         TabLayoutMediator.this.populateTabsFromPagerAdapter();
      }

      public void onItemRangeChanged(int var1, int var2, Object var3) {
         TabLayoutMediator.this.populateTabsFromPagerAdapter();
      }

      public void onItemRangeInserted(int var1, int var2) {
         TabLayoutMediator.this.populateTabsFromPagerAdapter();
      }

      public void onItemRangeMoved(int var1, int var2, int var3) {
         TabLayoutMediator.this.populateTabsFromPagerAdapter();
      }

      public void onItemRangeRemoved(int var1, int var2) {
         TabLayoutMediator.this.populateTabsFromPagerAdapter();
      }
   }

   public interface TabConfigurationStrategy {
      void onConfigureTab(TabLayout.Tab var1, int var2);
   }

   private static class TabLayoutOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
      private int previousScrollState;
      private int scrollState;
      private final WeakReference<TabLayout> tabLayoutRef;

      TabLayoutOnPageChangeCallback(TabLayout var1) {
         this.tabLayoutRef = new WeakReference(var1);
         this.reset();
      }

      public void onPageScrollStateChanged(int var1) {
         this.previousScrollState = this.scrollState;
         this.scrollState = var1;
      }

      public void onPageScrolled(int var1, float var2, int var3) {
         TabLayout var4 = (TabLayout)this.tabLayoutRef.get();
         if (var4 != null) {
            var3 = this.scrollState;
            boolean var5 = false;
            boolean var6;
            if (var3 == 2 && this.previousScrollState != 1) {
               var6 = false;
            } else {
               var6 = true;
            }

            if (this.scrollState != 2 || this.previousScrollState != 0) {
               var5 = true;
            }

            var4.setScrollPosition(var1, var2, var6, var5);
         }

      }

      public void onPageSelected(int var1) {
         TabLayout var2 = (TabLayout)this.tabLayoutRef.get();
         if (var2 != null && var2.getSelectedTabPosition() != var1 && var1 < var2.getTabCount()) {
            int var3 = this.scrollState;
            boolean var4;
            if (var3 == 0 || var3 == 2 && this.previousScrollState == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            var2.selectTab(var2.getTabAt(var1), var4);
         }

      }

      void reset() {
         this.scrollState = 0;
         this.previousScrollState = 0;
      }
   }

   private static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
      private final boolean smoothScroll;
      private final ViewPager2 viewPager;

      ViewPagerOnTabSelectedListener(ViewPager2 var1, boolean var2) {
         this.viewPager = var1;
         this.smoothScroll = var2;
      }

      public void onTabReselected(TabLayout.Tab var1) {
      }

      public void onTabSelected(TabLayout.Tab var1) {
         this.viewPager.setCurrentItem(var1.getPosition(), this.smoothScroll);
      }

      public void onTabUnselected(TabLayout.Tab var1) {
      }
   }
}
