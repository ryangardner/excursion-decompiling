package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnLayoutChangeListener;
import android.widget.FrameLayout;
import androidx.collection.ArraySet;
import androidx.collection.LongSparseArray;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Iterator;

public abstract class FragmentStateAdapter extends RecyclerView.Adapter<FragmentViewHolder> implements StatefulAdapter {
   private static final long GRACE_WINDOW_TIME_MS = 10000L;
   private static final String KEY_PREFIX_FRAGMENT = "f#";
   private static final String KEY_PREFIX_STATE = "s#";
   final FragmentManager mFragmentManager;
   private FragmentStateAdapter.FragmentMaxLifecycleEnforcer mFragmentMaxLifecycleEnforcer;
   final LongSparseArray<Fragment> mFragments;
   private boolean mHasStaleFragments;
   boolean mIsInGracePeriod;
   private final LongSparseArray<Integer> mItemIdToViewHolder;
   final Lifecycle mLifecycle;
   private final LongSparseArray<Fragment.SavedState> mSavedStates;

   public FragmentStateAdapter(Fragment var1) {
      this(var1.getChildFragmentManager(), var1.getLifecycle());
   }

   public FragmentStateAdapter(FragmentActivity var1) {
      this(var1.getSupportFragmentManager(), var1.getLifecycle());
   }

   public FragmentStateAdapter(FragmentManager var1, Lifecycle var2) {
      this.mFragments = new LongSparseArray();
      this.mSavedStates = new LongSparseArray();
      this.mItemIdToViewHolder = new LongSparseArray();
      this.mIsInGracePeriod = false;
      this.mHasStaleFragments = false;
      this.mFragmentManager = var1;
      this.mLifecycle = var2;
      super.setHasStableIds(true);
   }

   private static String createKey(String var0, long var1) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var0);
      var3.append(var1);
      return var3.toString();
   }

   private void ensureFragment(int var1) {
      long var2 = this.getItemId(var1);
      if (!this.mFragments.containsKey(var2)) {
         Fragment var4 = this.createFragment(var1);
         var4.setInitialSavedState((Fragment.SavedState)this.mSavedStates.get(var2));
         this.mFragments.put(var2, var4);
      }

   }

   private boolean isFragmentViewBound(long var1) {
      boolean var3 = this.mItemIdToViewHolder.containsKey(var1);
      boolean var4 = true;
      if (var3) {
         return true;
      } else {
         Fragment var5 = (Fragment)this.mFragments.get(var1);
         if (var5 == null) {
            return false;
         } else {
            View var6 = var5.getView();
            if (var6 == null) {
               return false;
            } else {
               if (var6.getParent() == null) {
                  var4 = false;
               }

               return var4;
            }
         }
      }
   }

   private static boolean isValidKey(String var0, String var1) {
      boolean var2;
      if (var0.startsWith(var1) && var0.length() > var1.length()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private Long itemForViewHolder(int var1) {
      Long var2 = null;

      Long var4;
      for(int var3 = 0; var3 < this.mItemIdToViewHolder.size(); var2 = var4) {
         var4 = var2;
         if ((Integer)this.mItemIdToViewHolder.valueAt(var3) == var1) {
            if (var2 != null) {
               throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
            }

            var4 = this.mItemIdToViewHolder.keyAt(var3);
         }

         ++var3;
      }

      return var2;
   }

   private static long parseIdFromKey(String var0, String var1) {
      return Long.parseLong(var0.substring(var1.length()));
   }

   private void removeFragment(long var1) {
      Fragment var3 = (Fragment)this.mFragments.get(var1);
      if (var3 != null) {
         if (var3.getView() != null) {
            ViewParent var4 = var3.getView().getParent();
            if (var4 != null) {
               ((FrameLayout)var4).removeAllViews();
            }
         }

         if (!this.containsItem(var1)) {
            this.mSavedStates.remove(var1);
         }

         if (!var3.isAdded()) {
            this.mFragments.remove(var1);
         } else if (this.shouldDelayFragmentTransactions()) {
            this.mHasStaleFragments = true;
         } else {
            if (var3.isAdded() && this.containsItem(var1)) {
               this.mSavedStates.put(var1, this.mFragmentManager.saveFragmentInstanceState(var3));
            }

            this.mFragmentManager.beginTransaction().remove(var3).commitNow();
            this.mFragments.remove(var1);
         }
      }
   }

   private void scheduleGracePeriodEnd() {
      final Handler var1 = new Handler(Looper.getMainLooper());
      final Runnable var2 = new Runnable() {
         public void run() {
            FragmentStateAdapter.this.mIsInGracePeriod = false;
            FragmentStateAdapter.this.gcFragments();
         }
      };
      this.mLifecycle.addObserver(new LifecycleEventObserver() {
         public void onStateChanged(LifecycleOwner var1x, Lifecycle.Event var2x) {
            if (var2x == Lifecycle.Event.ON_DESTROY) {
               var1.removeCallbacks(var2);
               var1x.getLifecycle().removeObserver(this);
            }

         }
      });
      var1.postDelayed(var2, 10000L);
   }

   private void scheduleViewAttach(final Fragment var1, final FrameLayout var2) {
      this.mFragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
         public void onFragmentViewCreated(FragmentManager var1x, Fragment var2x, View var3, Bundle var4) {
            if (var2x == var1) {
               var1x.unregisterFragmentLifecycleCallbacks(this);
               FragmentStateAdapter.this.addViewToContainer(var3, var2);
            }

         }
      }, false);
   }

   void addViewToContainer(View var1, FrameLayout var2) {
      if (var2.getChildCount() <= 1) {
         if (var1.getParent() != var2) {
            if (var2.getChildCount() > 0) {
               var2.removeAllViews();
            }

            if (var1.getParent() != null) {
               ((ViewGroup)var1.getParent()).removeView(var1);
            }

            var2.addView(var1);
         }
      } else {
         throw new IllegalStateException("Design assumption violated.");
      }
   }

   public boolean containsItem(long var1) {
      boolean var3;
      if (var1 >= 0L && var1 < (long)this.getItemCount()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public abstract Fragment createFragment(int var1);

   void gcFragments() {
      if (this.mHasStaleFragments && !this.shouldDelayFragmentTransactions()) {
         ArraySet var1 = new ArraySet();
         byte var2 = 0;

         int var3;
         long var4;
         for(var3 = 0; var3 < this.mFragments.size(); ++var3) {
            var4 = this.mFragments.keyAt(var3);
            if (!this.containsItem(var4)) {
               var1.add(var4);
               this.mItemIdToViewHolder.remove(var4);
            }
         }

         if (!this.mIsInGracePeriod) {
            this.mHasStaleFragments = false;

            for(var3 = var2; var3 < this.mFragments.size(); ++var3) {
               var4 = this.mFragments.keyAt(var3);
               if (!this.isFragmentViewBound(var4)) {
                  var1.add(var4);
               }
            }
         }

         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            this.removeFragment((Long)var6.next());
         }
      }

   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public void onAttachedToRecyclerView(RecyclerView var1) {
      boolean var2;
      if (this.mFragmentMaxLifecycleEnforcer == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      FragmentStateAdapter.FragmentMaxLifecycleEnforcer var3 = new FragmentStateAdapter.FragmentMaxLifecycleEnforcer();
      this.mFragmentMaxLifecycleEnforcer = var3;
      var3.register(var1);
   }

   public final void onBindViewHolder(final FragmentViewHolder var1, int var2) {
      long var3 = var1.getItemId();
      int var5 = var1.getContainer().getId();
      Long var6 = this.itemForViewHolder(var5);
      if (var6 != null && var6 != var3) {
         this.removeFragment(var6);
         this.mItemIdToViewHolder.remove(var6);
      }

      this.mItemIdToViewHolder.put(var3, var5);
      this.ensureFragment(var2);
      final FrameLayout var7 = var1.getContainer();
      if (ViewCompat.isAttachedToWindow(var7)) {
         if (var7.getParent() != null) {
            throw new IllegalStateException("Design assumption violated.");
         }

         var7.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            public void onLayoutChange(View var1x, int var2, int var3, int var4, int var5, int var6, int var7x, int var8, int var9) {
               if (var7.getParent() != null) {
                  var7.removeOnLayoutChangeListener(this);
                  FragmentStateAdapter.this.placeFragmentInViewHolder(var1);
               }

            }
         });
      }

      this.gcFragments();
   }

   public final FragmentViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
      return FragmentViewHolder.create(var1);
   }

   public void onDetachedFromRecyclerView(RecyclerView var1) {
      this.mFragmentMaxLifecycleEnforcer.unregister(var1);
      this.mFragmentMaxLifecycleEnforcer = null;
   }

   public final boolean onFailedToRecycleView(FragmentViewHolder var1) {
      return true;
   }

   public final void onViewAttachedToWindow(FragmentViewHolder var1) {
      this.placeFragmentInViewHolder(var1);
      this.gcFragments();
   }

   public final void onViewRecycled(FragmentViewHolder var1) {
      Long var2 = this.itemForViewHolder(var1.getContainer().getId());
      if (var2 != null) {
         this.removeFragment(var2);
         this.mItemIdToViewHolder.remove(var2);
      }

   }

   void placeFragmentInViewHolder(final FragmentViewHolder var1) {
      Fragment var2 = (Fragment)this.mFragments.get(var1.getItemId());
      if (var2 != null) {
         FrameLayout var3 = var1.getContainer();
         View var4 = var2.getView();
         if (!var2.isAdded() && var4 != null) {
            throw new IllegalStateException("Design assumption violated.");
         } else if (var2.isAdded() && var4 == null) {
            this.scheduleViewAttach(var2, var3);
         } else if (var2.isAdded() && var4.getParent() != null) {
            if (var4.getParent() != var3) {
               this.addViewToContainer(var4, var3);
            }

         } else if (var2.isAdded()) {
            this.addViewToContainer(var4, var3);
         } else {
            if (!this.shouldDelayFragmentTransactions()) {
               this.scheduleViewAttach(var2, var3);
               FragmentTransaction var6 = this.mFragmentManager.beginTransaction();
               StringBuilder var5 = new StringBuilder();
               var5.append("f");
               var5.append(var1.getItemId());
               var6.add(var2, var5.toString()).setMaxLifecycle(var2, Lifecycle.State.STARTED).commitNow();
               this.mFragmentMaxLifecycleEnforcer.updateFragmentMaxLifecycle(false);
            } else {
               if (this.mFragmentManager.isDestroyed()) {
                  return;
               }

               this.mLifecycle.addObserver(new LifecycleEventObserver() {
                  public void onStateChanged(LifecycleOwner var1x, Lifecycle.Event var2) {
                     if (!FragmentStateAdapter.this.shouldDelayFragmentTransactions()) {
                        var1x.getLifecycle().removeObserver(this);
                        if (ViewCompat.isAttachedToWindow(var1.getContainer())) {
                           FragmentStateAdapter.this.placeFragmentInViewHolder(var1);
                        }

                     }
                  }
               });
            }

         }
      } else {
         throw new IllegalStateException("Design assumption violated.");
      }
   }

   public final void restoreState(Parcelable var1) {
      if (this.mSavedStates.isEmpty() && this.mFragments.isEmpty()) {
         Bundle var6 = (Bundle)var1;
         if (var6.getClassLoader() == null) {
            var6.setClassLoader(this.getClass().getClassLoader());
         }

         Iterator var2 = var6.keySet().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            long var4;
            if (isValidKey(var3, "f#")) {
               var4 = parseIdFromKey(var3, "f#");
               Fragment var8 = this.mFragmentManager.getFragment(var6, var3);
               this.mFragments.put(var4, var8);
            } else {
               if (!isValidKey(var3, "s#")) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append("Unexpected key in savedState: ");
                  var7.append(var3);
                  throw new IllegalArgumentException(var7.toString());
               }

               var4 = parseIdFromKey(var3, "s#");
               Fragment.SavedState var9 = (Fragment.SavedState)var6.getParcelable(var3);
               if (this.containsItem(var4)) {
                  this.mSavedStates.put(var4, var9);
               }
            }
         }

         if (!this.mFragments.isEmpty()) {
            this.mHasStaleFragments = true;
            this.mIsInGracePeriod = true;
            this.gcFragments();
            this.scheduleGracePeriodEnd();
         }

      } else {
         throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
      }
   }

   public final Parcelable saveState() {
      Bundle var1 = new Bundle(this.mFragments.size() + this.mSavedStates.size());
      byte var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var2;
         long var5;
         if (var3 >= this.mFragments.size()) {
            for(; var4 < this.mSavedStates.size(); ++var4) {
               var5 = this.mSavedStates.keyAt(var4);
               if (this.containsItem(var5)) {
                  var1.putParcelable(createKey("s#", var5), (Parcelable)this.mSavedStates.get(var5));
               }
            }

            return var1;
         }

         var5 = this.mFragments.keyAt(var3);
         Fragment var7 = (Fragment)this.mFragments.get(var5);
         if (var7 != null && var7.isAdded()) {
            String var8 = createKey("f#", var5);
            this.mFragmentManager.putFragment(var1, var8, var7);
         }

         ++var3;
      }
   }

   public final void setHasStableIds(boolean var1) {
      throw new UnsupportedOperationException("Stable Ids are required for the adapter to function properly, and the adapter takes care of setting the flag.");
   }

   boolean shouldDelayFragmentTransactions() {
      return this.mFragmentManager.isStateSaved();
   }

   private abstract static class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
      private DataSetChangeObserver() {
      }

      // $FF: synthetic method
      DataSetChangeObserver(Object var1) {
         this();
      }

      public abstract void onChanged();

      public final void onItemRangeChanged(int var1, int var2) {
         this.onChanged();
      }

      public final void onItemRangeChanged(int var1, int var2, Object var3) {
         this.onChanged();
      }

      public final void onItemRangeInserted(int var1, int var2) {
         this.onChanged();
      }

      public final void onItemRangeMoved(int var1, int var2, int var3) {
         this.onChanged();
      }

      public final void onItemRangeRemoved(int var1, int var2) {
         this.onChanged();
      }
   }

   class FragmentMaxLifecycleEnforcer {
      private RecyclerView.AdapterDataObserver mDataObserver;
      private LifecycleEventObserver mLifecycleObserver;
      private ViewPager2.OnPageChangeCallback mPageChangeCallback;
      private long mPrimaryItemId = -1L;
      private ViewPager2 mViewPager;

      private ViewPager2 inferViewPager(RecyclerView var1) {
         ViewParent var3 = var1.getParent();
         if (var3 instanceof ViewPager2) {
            return (ViewPager2)var3;
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append("Expected ViewPager2 instance. Got: ");
            var2.append(var3);
            throw new IllegalStateException(var2.toString());
         }
      }

      void register(RecyclerView var1) {
         this.mViewPager = this.inferViewPager(var1);
         ViewPager2.OnPageChangeCallback var2 = new ViewPager2.OnPageChangeCallback() {
            public void onPageScrollStateChanged(int var1) {
               FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
            }

            public void onPageSelected(int var1) {
               FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
            }
         };
         this.mPageChangeCallback = var2;
         this.mViewPager.registerOnPageChangeCallback(var2);
         FragmentStateAdapter.DataSetChangeObserver var3 = new FragmentStateAdapter.DataSetChangeObserver() {
            public void onChanged() {
               FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(true);
            }
         };
         this.mDataObserver = var3;
         FragmentStateAdapter.this.registerAdapterDataObserver(var3);
         this.mLifecycleObserver = new LifecycleEventObserver() {
            public void onStateChanged(LifecycleOwner var1, Lifecycle.Event var2) {
               FragmentMaxLifecycleEnforcer.this.updateFragmentMaxLifecycle(false);
            }
         };
         FragmentStateAdapter.this.mLifecycle.addObserver(this.mLifecycleObserver);
      }

      void unregister(RecyclerView var1) {
         this.inferViewPager(var1).unregisterOnPageChangeCallback(this.mPageChangeCallback);
         FragmentStateAdapter.this.unregisterAdapterDataObserver(this.mDataObserver);
         FragmentStateAdapter.this.mLifecycle.removeObserver(this.mLifecycleObserver);
         this.mViewPager = null;
      }

      void updateFragmentMaxLifecycle(boolean var1) {
         if (!FragmentStateAdapter.this.shouldDelayFragmentTransactions()) {
            if (this.mViewPager.getScrollState() == 0) {
               if (!FragmentStateAdapter.this.mFragments.isEmpty() && FragmentStateAdapter.this.getItemCount() != 0) {
                  int var2 = this.mViewPager.getCurrentItem();
                  if (var2 >= FragmentStateAdapter.this.getItemCount()) {
                     return;
                  }

                  long var3 = FragmentStateAdapter.this.getItemId(var2);
                  if (var3 == this.mPrimaryItemId && !var1) {
                     return;
                  }

                  Fragment var5 = (Fragment)FragmentStateAdapter.this.mFragments.get(var3);
                  if (var5 != null && var5.isAdded()) {
                     this.mPrimaryItemId = var3;
                     FragmentTransaction var6 = FragmentStateAdapter.this.mFragmentManager.beginTransaction();
                     var5 = null;

                     for(var2 = 0; var2 < FragmentStateAdapter.this.mFragments.size(); ++var2) {
                        var3 = FragmentStateAdapter.this.mFragments.keyAt(var2);
                        Fragment var7 = (Fragment)FragmentStateAdapter.this.mFragments.valueAt(var2);
                        if (var7.isAdded()) {
                           if (var3 != this.mPrimaryItemId) {
                              var6.setMaxLifecycle(var7, Lifecycle.State.STARTED);
                           } else {
                              var5 = var7;
                           }

                           if (var3 == this.mPrimaryItemId) {
                              var1 = true;
                           } else {
                              var1 = false;
                           }

                           var7.setMenuVisibility(var1);
                        }
                     }

                     if (var5 != null) {
                        var6.setMaxLifecycle(var5, Lifecycle.State.RESUMED);
                     }

                     if (!var6.isEmpty()) {
                        var6.commitNow();
                     }
                  }
               }

            }
         }
      }
   }
}
