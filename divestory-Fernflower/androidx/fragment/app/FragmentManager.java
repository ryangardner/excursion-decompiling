package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.LayoutInflater.Factory2;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.core.util.LogWriter;
import androidx.fragment.R;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager {
   private static boolean DEBUG;
   public static final int POP_BACK_STACK_INCLUSIVE = 1;
   static final String TAG = "FragmentManager";
   ArrayList<BackStackRecord> mBackStack;
   private ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
   private final AtomicInteger mBackStackIndex = new AtomicInteger();
   FragmentContainer mContainer;
   private ArrayList<Fragment> mCreatedMenus;
   int mCurState = -1;
   private boolean mDestroyed;
   private Runnable mExecCommit = new Runnable() {
      public void run() {
         FragmentManager.this.execPendingActions(true);
      }
   };
   private boolean mExecutingActions;
   private ConcurrentHashMap<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = new ConcurrentHashMap();
   private FragmentFactory mFragmentFactory = null;
   private final FragmentStore mFragmentStore = new FragmentStore();
   private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback() {
      public void onComplete(Fragment var1, CancellationSignal var2) {
         if (!var2.isCanceled()) {
            FragmentManager.this.removeCancellationSignal(var1, var2);
         }

      }

      public void onStart(Fragment var1, CancellationSignal var2) {
         FragmentManager.this.addCancellationSignal(var1, var2);
      }
   };
   private boolean mHavePendingDeferredStart;
   FragmentHostCallback<?> mHost;
   private FragmentFactory mHostFragmentFactory = new FragmentFactory() {
      public Fragment instantiate(ClassLoader var1, String var2) {
         return FragmentManager.this.mHost.instantiate(FragmentManager.this.mHost.getContext(), var2, (Bundle)null);
      }
   };
   private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
   private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
   private boolean mNeedMenuInvalidate;
   private FragmentManagerViewModel mNonConfig;
   private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
      public void handleOnBackPressed() {
         FragmentManager.this.handleOnBackPressed();
      }
   };
   private OnBackPressedDispatcher mOnBackPressedDispatcher;
   private Fragment mParent;
   private final ArrayList<FragmentManager.OpGenerator> mPendingActions = new ArrayList();
   private ArrayList<FragmentManager.StartEnterTransitionListener> mPostponedTransactions;
   Fragment mPrimaryNav;
   private boolean mStateSaved;
   private boolean mStopped;
   private ArrayList<Fragment> mTmpAddedFragments;
   private ArrayList<Boolean> mTmpIsPop;
   private ArrayList<BackStackRecord> mTmpRecords;

   private void addAddedFragments(ArraySet<Fragment> var1) {
      int var2 = this.mCurState;
      if (var2 >= 1) {
         var2 = Math.min(var2, 3);
         Iterator var3 = this.mFragmentStore.getFragments().iterator();

         while(var3.hasNext()) {
            Fragment var4 = (Fragment)var3.next();
            if (var4.mState < var2) {
               this.moveToState(var4, var2);
               if (var4.mView != null && !var4.mHidden && var4.mIsNewlyAdded) {
                  var1.add(var4);
               }
            }
         }

      }
   }

   private void cancelExitAnimation(Fragment var1) {
      HashSet var2 = (HashSet)this.mExitAnimationCancellationSignals.get(var1);
      if (var2 != null) {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            ((CancellationSignal)var3.next()).cancel();
         }

         var2.clear();
         this.destroyFragmentView(var1);
         this.mExitAnimationCancellationSignals.remove(var1);
      }

   }

   private void checkStateLoss() {
      if (this.isStateSaved()) {
         throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
      }
   }

   private void cleanupExec() {
      this.mExecutingActions = false;
      this.mTmpIsPop.clear();
      this.mTmpRecords.clear();
   }

   private void completeShowHideFragment(final Fragment var1) {
      if (var1.mView != null) {
         FragmentAnim.AnimationOrAnimator var2 = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, var1, var1.mHidden ^ true);
         if (var2 != null && var2.animator != null) {
            var2.animator.setTarget(var1.mView);
            if (var1.mHidden) {
               if (var1.isHideReplaced()) {
                  var1.setHideReplaced(false);
               } else {
                  final ViewGroup var3 = var1.mContainer;
                  final View var4 = var1.mView;
                  var3.startViewTransition(var4);
                  var2.animator.addListener(new AnimatorListenerAdapter() {
                     public void onAnimationEnd(Animator var1x) {
                        var3.endViewTransition(var4);
                        var1x.removeListener(this);
                        if (var1.mView != null && var1.mHidden) {
                           var1.mView.setVisibility(8);
                        }

                     }
                  });
               }
            } else {
               var1.mView.setVisibility(0);
            }

            var2.animator.start();
         } else {
            if (var2 != null) {
               var1.mView.startAnimation(var2.animation);
               var2.animation.start();
            }

            byte var5;
            if (var1.mHidden && !var1.isHideReplaced()) {
               var5 = 8;
            } else {
               var5 = 0;
            }

            var1.mView.setVisibility(var5);
            if (var1.isHideReplaced()) {
               var1.setHideReplaced(false);
            }
         }
      }

      if (var1.mAdded && this.isMenuAvailable(var1)) {
         this.mNeedMenuInvalidate = true;
      }

      var1.mHiddenChanged = false;
      var1.onHiddenChanged(var1.mHidden);
   }

   private void destroyFragmentView(Fragment var1) {
      var1.performDestroyView();
      this.mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(var1, false);
      var1.mContainer = null;
      var1.mView = null;
      var1.mViewLifecycleOwner = null;
      var1.mViewLifecycleOwnerLiveData.setValue((Object)null);
      var1.mInLayout = false;
   }

   private void dispatchParentPrimaryNavigationFragmentChanged(Fragment var1) {
      if (var1 != null && var1.equals(this.findActiveFragment(var1.mWho))) {
         var1.performPrimaryNavigationFragmentChanged();
      }

   }

   private void dispatchStateChange(int var1) {
      try {
         this.mExecutingActions = true;
         this.mFragmentStore.dispatchStateChange(var1);
         this.moveToState(var1, false);
      } finally {
         this.mExecutingActions = false;
      }

      this.execPendingActions(true);
   }

   private void doPendingDeferredStart() {
      if (this.mHavePendingDeferredStart) {
         this.mHavePendingDeferredStart = false;
         this.startPendingDeferredFragments();
      }

   }

   @Deprecated
   public static void enableDebugLogging(boolean var0) {
      DEBUG = var0;
   }

   private void endAnimatingAwayFragments() {
      if (!this.mExitAnimationCancellationSignals.isEmpty()) {
         Iterator var1 = this.mExitAnimationCancellationSignals.keySet().iterator();

         while(var1.hasNext()) {
            Fragment var2 = (Fragment)var1.next();
            this.cancelExitAnimation(var2);
            this.moveToState(var2, var2.getStateAfterAnimating());
         }
      }

   }

   private void ensureExecReady(boolean var1) {
      if (!this.mExecutingActions) {
         if (this.mHost == null) {
            if (this.mDestroyed) {
               throw new IllegalStateException("FragmentManager has been destroyed");
            } else {
               throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
         } else if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
            if (!var1) {
               this.checkStateLoss();
            }

            if (this.mTmpRecords == null) {
               this.mTmpRecords = new ArrayList();
               this.mTmpIsPop = new ArrayList();
            }

            this.mExecutingActions = true;

            try {
               this.executePostponedTransaction((ArrayList)null, (ArrayList)null);
            } finally {
               this.mExecutingActions = false;
            }

         } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
         }
      } else {
         throw new IllegalStateException("FragmentManager is already executing transactions");
      }
   }

   private static void executeOps(ArrayList<BackStackRecord> var0, ArrayList<Boolean> var1, int var2, int var3) {
      for(; var2 < var3; ++var2) {
         BackStackRecord var4 = (BackStackRecord)var0.get(var2);
         boolean var5 = (Boolean)var1.get(var2);
         boolean var6 = true;
         if (var5) {
            var4.bumpBackStackNesting(-1);
            if (var2 != var3 - 1) {
               var6 = false;
            }

            var4.executePopOps(var6);
         } else {
            var4.bumpBackStackNesting(1);
            var4.executeOps();
         }
      }

   }

   private void executeOpsTogether(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2, int var3, int var4) {
      boolean var6 = ((BackStackRecord)var1.get(var3)).mReorderingAllowed;
      ArrayList var7 = this.mTmpAddedFragments;
      if (var7 == null) {
         this.mTmpAddedFragments = new ArrayList();
      } else {
         var7.clear();
      }

      this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
      Fragment var12 = this.getPrimaryNavigationFragment();
      int var8 = var3;

      boolean var9;
      for(var9 = false; var8 < var4; ++var8) {
         BackStackRecord var10 = (BackStackRecord)var1.get(var8);
         if (!(Boolean)var2.get(var8)) {
            var12 = var10.expandOps(this.mTmpAddedFragments, var12);
         } else {
            var12 = var10.trackAddedFragmentsInPop(this.mTmpAddedFragments, var12);
         }

         if (!var9 && !var10.mAddToBackStack) {
            var9 = false;
         } else {
            var9 = true;
         }
      }

      this.mTmpAddedFragments.clear();
      if (!var6) {
         FragmentTransition.startTransitions(this, var1, var2, var3, var4, false, this.mFragmentTransitionCallback);
      }

      executeOps(var1, var2, var3, var4);
      if (var6) {
         ArraySet var13 = new ArraySet();
         this.addAddedFragments(var13);
         var8 = this.postponePostponableTransactions(var1, var2, var3, var4, var13);
         this.makeRemovedFragmentsInvisible(var13);
      } else {
         var8 = var4;
      }

      int var11 = var3;
      if (var8 != var3) {
         var11 = var3;
         if (var6) {
            FragmentTransition.startTransitions(this, var1, var2, var3, var8, true, this.mFragmentTransitionCallback);
            this.moveToState(this.mCurState, true);
            var11 = var3;
         }
      }

      while(var11 < var4) {
         BackStackRecord var14 = (BackStackRecord)var1.get(var11);
         if ((Boolean)var2.get(var11) && var14.mIndex >= 0) {
            var14.mIndex = -1;
         }

         var14.runOnCommitRunnables();
         ++var11;
      }

      if (var9) {
         this.reportBackStackChanged();
      }

   }

   private void executePostponedTransaction(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2) {
      ArrayList var3 = this.mPostponedTransactions;
      int var4;
      if (var3 == null) {
         var4 = 0;
      } else {
         var4 = var3.size();
      }

      int var5 = 0;

      for(int var6 = var4; var5 < var6; var6 = var4) {
         int var7;
         label54: {
            FragmentManager.StartEnterTransitionListener var8 = (FragmentManager.StartEnterTransitionListener)this.mPostponedTransactions.get(var5);
            if (var1 != null && !var8.mIsBack) {
               var4 = var1.indexOf(var8.mRecord);
               if (var4 != -1 && var2 != null && (Boolean)var2.get(var4)) {
                  this.mPostponedTransactions.remove(var5);
                  var7 = var5 - 1;
                  var4 = var6 - 1;
                  var8.cancelTransaction();
                  break label54;
               }
            }

            if (!var8.isReady()) {
               var4 = var6;
               var7 = var5;
               if (var1 == null) {
                  break label54;
               }

               var4 = var6;
               var7 = var5;
               if (!var8.mRecord.interactsWith(var1, 0, var1.size())) {
                  break label54;
               }
            }

            this.mPostponedTransactions.remove(var5);
            var7 = var5 - 1;
            var4 = var6 - 1;
            if (var1 != null && !var8.mIsBack) {
               var5 = var1.indexOf(var8.mRecord);
               if (var5 != -1 && var2 != null && (Boolean)var2.get(var5)) {
                  var8.cancelTransaction();
                  break label54;
               }
            }

            var8.completeTransaction();
         }

         var5 = var7 + 1;
      }

   }

   public static <F extends Fragment> F findFragment(View var0) {
      Fragment var1 = findViewFragment(var0);
      if (var1 != null) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("View ");
         var2.append(var0);
         var2.append(" does not have a Fragment set");
         throw new IllegalStateException(var2.toString());
      }
   }

   static FragmentManager findFragmentManager(View var0) {
      Fragment var1 = findViewFragment(var0);
      FragmentManager var4;
      if (var1 != null) {
         var4 = var1.getChildFragmentManager();
      } else {
         Context var2 = var0.getContext();
         Object var3 = null;

         FragmentActivity var5;
         while(true) {
            var5 = (FragmentActivity)var3;
            if (!(var2 instanceof ContextWrapper)) {
               break;
            }

            if (var2 instanceof FragmentActivity) {
               var5 = (FragmentActivity)var2;
               break;
            }

            var2 = ((ContextWrapper)var2).getBaseContext();
         }

         if (var5 == null) {
            StringBuilder var6 = new StringBuilder();
            var6.append("View ");
            var6.append(var0);
            var6.append(" is not within a subclass of FragmentActivity.");
            throw new IllegalStateException(var6.toString());
         }

         var4 = var5.getSupportFragmentManager();
      }

      return var4;
   }

   private static Fragment findViewFragment(View var0) {
      while(var0 != null) {
         Fragment var1 = getViewFragment(var0);
         if (var1 != null) {
            return var1;
         }

         ViewParent var2 = var0.getParent();
         if (var2 instanceof View) {
            var0 = (View)var2;
         } else {
            var0 = null;
         }
      }

      return null;
   }

   private void forcePostponedTransactions() {
      if (this.mPostponedTransactions != null) {
         while(!this.mPostponedTransactions.isEmpty()) {
            ((FragmentManager.StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();
         }
      }

   }

   private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2) {
      ArrayList var3 = this.mPendingActions;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label375: {
         boolean var4;
         try {
            var4 = this.mPendingActions.isEmpty();
         } catch (Throwable var48) {
            var10000 = var48;
            var10001 = false;
            break label375;
         }

         int var5 = 0;
         if (var4) {
            label357:
            try {
               return false;
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label357;
            }
         } else {
            label379: {
               int var6;
               try {
                  var6 = this.mPendingActions.size();
               } catch (Throwable var47) {
                  var10000 = var47;
                  var10001 = false;
                  break label379;
               }

               for(var4 = false; var5 < var6; ++var5) {
                  try {
                     var4 |= ((FragmentManager.OpGenerator)this.mPendingActions.get(var5)).generateOps(var1, var2);
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label379;
                  }
               }

               label359:
               try {
                  this.mPendingActions.clear();
                  this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                  return var4;
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label359;
               }
            }
         }
      }

      while(true) {
         Throwable var49 = var10000;

         try {
            throw var49;
         } catch (Throwable var43) {
            var10000 = var43;
            var10001 = false;
            continue;
         }
      }
   }

   private FragmentManagerViewModel getChildNonConfig(Fragment var1) {
      return this.mNonConfig.getChildNonConfig(var1);
   }

   private ViewGroup getFragmentContainer(Fragment var1) {
      if (var1.mContainerId <= 0) {
         return null;
      } else {
         if (this.mContainer.onHasView()) {
            View var2 = this.mContainer.onFindViewById(var1.mContainerId);
            if (var2 instanceof ViewGroup) {
               return (ViewGroup)var2;
            }
         }

         return null;
      }
   }

   static Fragment getViewFragment(View var0) {
      Object var1 = var0.getTag(R.id.fragment_container_view_tag);
      return var1 instanceof Fragment ? (Fragment)var1 : null;
   }

   static boolean isLoggingEnabled(int var0) {
      boolean var1;
      if (!DEBUG && !Log.isLoggable("FragmentManager", var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private boolean isMenuAvailable(Fragment var1) {
      boolean var2;
      if ((!var1.mHasMenu || !var1.mMenuVisible) && !var1.mChildFragmentManager.checkForMenus()) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private void makeInactive(FragmentStateManager var1) {
      Fragment var2 = var1.getFragment();
      if (this.mFragmentStore.containsActiveFragment(var2.mWho)) {
         if (isLoggingEnabled(2)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Removed fragment from active set ");
            var3.append(var2);
            Log.v("FragmentManager", var3.toString());
         }

         this.mFragmentStore.makeInactive(var1);
         this.removeRetainedFragment(var2);
      }
   }

   private void makeRemovedFragmentsInvisible(ArraySet<Fragment> var1) {
      int var2 = var1.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         Fragment var4 = (Fragment)var1.valueAt(var3);
         if (!var4.mAdded) {
            View var5 = var4.requireView();
            var4.mPostponedAlpha = var5.getAlpha();
            var5.setAlpha(0.0F);
         }
      }

   }

   private boolean popBackStackImmediate(String var1, int var2, int var3) {
      this.execPendingActions(false);
      this.ensureExecReady(true);
      Fragment var4 = this.mPrimaryNav;
      if (var4 != null && var2 < 0 && var1 == null && var4.getChildFragmentManager().popBackStackImmediate()) {
         return true;
      } else {
         boolean var5 = this.popBackStackState(this.mTmpRecords, this.mTmpIsPop, var1, var2, var3);
         if (var5) {
            this.mExecutingActions = true;

            try {
               this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
               this.cleanupExec();
            }
         }

         this.updateOnBackPressedCallbackEnabled();
         this.doPendingDeferredStart();
         this.mFragmentStore.burpActive();
         return var5;
      }
   }

   private int postponePostponableTransactions(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2, int var3, int var4, ArraySet<Fragment> var5) {
      int var6 = var4 - 1;

      int var7;
      int var11;
      for(var7 = var4; var6 >= var3; var7 = var11) {
         BackStackRecord var8 = (BackStackRecord)var1.get(var6);
         boolean var9 = (Boolean)var2.get(var6);
         boolean var10;
         if (var8.isPostponed() && !var8.interactsWith(var1, var6 + 1, var4)) {
            var10 = true;
         } else {
            var10 = false;
         }

         var11 = var7;
         if (var10) {
            if (this.mPostponedTransactions == null) {
               this.mPostponedTransactions = new ArrayList();
            }

            FragmentManager.StartEnterTransitionListener var12 = new FragmentManager.StartEnterTransitionListener(var8, var9);
            this.mPostponedTransactions.add(var12);
            var8.setOnStartPostponedListener(var12);
            if (var9) {
               var8.executeOps();
            } else {
               var8.executePopOps(false);
            }

            var11 = var7 - 1;
            if (var6 != var11) {
               var1.remove(var6);
               var1.add(var11, var8);
            }

            this.addAddedFragments(var5);
         }

         --var6;
      }

      return var7;
   }

   private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2) {
      if (!var1.isEmpty()) {
         if (var1.size() != var2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
         } else {
            this.executePostponedTransaction(var1, var2);
            int var3 = var1.size();
            int var4 = 0;

            int var5;
            int var7;
            for(var5 = 0; var4 < var3; var5 = var7) {
               int var6 = var4;
               var7 = var5;
               if (!((BackStackRecord)var1.get(var4)).mReorderingAllowed) {
                  if (var5 != var4) {
                     this.executeOpsTogether(var1, var2, var5, var4);
                  }

                  var5 = var4 + 1;
                  var7 = var5;
                  if ((Boolean)var2.get(var4)) {
                     while(true) {
                        var7 = var5;
                        if (var5 >= var3) {
                           break;
                        }

                        var7 = var5;
                        if (!(Boolean)var2.get(var5)) {
                           break;
                        }

                        var7 = var5;
                        if (((BackStackRecord)var1.get(var5)).mReorderingAllowed) {
                           break;
                        }

                        ++var5;
                     }
                  }

                  this.executeOpsTogether(var1, var2, var4, var7);
                  var6 = var7 - 1;
               }

               var4 = var6 + 1;
            }

            if (var5 != var3) {
               this.executeOpsTogether(var1, var2, var5, var3);
            }

         }
      }
   }

   private void reportBackStackChanged() {
      if (this.mBackStackChangeListeners != null) {
         for(int var1 = 0; var1 < this.mBackStackChangeListeners.size(); ++var1) {
            ((FragmentManager.OnBackStackChangedListener)this.mBackStackChangeListeners.get(var1)).onBackStackChanged();
         }
      }

   }

   static int reverseTransit(int var0) {
      short var1 = 8194;
      if (var0 != 4097) {
         if (var0 != 4099) {
            if (var0 != 8194) {
               var1 = 0;
            } else {
               var1 = 4097;
            }
         } else {
            var1 = 4099;
         }
      }

      return var1;
   }

   private void setVisibleRemovingFragment(Fragment var1) {
      ViewGroup var2 = this.getFragmentContainer(var1);
      if (var2 != null) {
         if (var2.getTag(R.id.visible_removing_fragment_view_tag) == null) {
            var2.setTag(R.id.visible_removing_fragment_view_tag, var1);
         }

         ((Fragment)var2.getTag(R.id.visible_removing_fragment_view_tag)).setNextAnim(var1.getNextAnim());
      }

   }

   private void startPendingDeferredFragments() {
      Iterator var1 = this.mFragmentStore.getActiveFragments().iterator();

      while(var1.hasNext()) {
         Fragment var2 = (Fragment)var1.next();
         if (var2 != null) {
            this.performPendingDeferredStart(var2);
         }
      }

   }

   private void throwException(RuntimeException var1) {
      Log.e("FragmentManager", var1.getMessage());
      Log.e("FragmentManager", "Activity state:");
      PrintWriter var2 = new PrintWriter(new LogWriter("FragmentManager"));
      FragmentHostCallback var3 = this.mHost;
      if (var3 != null) {
         try {
            var3.onDump("  ", (FileDescriptor)null, var2, new String[0]);
         } catch (Exception var5) {
            Log.e("FragmentManager", "Failed dumping state", var5);
         }
      } else {
         try {
            this.dump("  ", (FileDescriptor)null, var2, new String[0]);
         } catch (Exception var4) {
            Log.e("FragmentManager", "Failed dumping state", var4);
         }
      }

      throw var1;
   }

   private void updateOnBackPressedCallbackEnabled() {
      ArrayList var1 = this.mPendingActions;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label218: {
         boolean var2;
         try {
            var2 = this.mPendingActions.isEmpty();
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label218;
         }

         boolean var3 = true;
         if (!var2) {
            label211:
            try {
               this.mOnBackPressedCallback.setEnabled(true);
               return;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label211;
            }
         } else {
            label223: {
               try {
                  ;
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label223;
               }

               OnBackPressedCallback var25 = this.mOnBackPressedCallback;
               if (this.getBackStackEntryCount() <= 0 || !this.isPrimaryNavigation(this.mParent)) {
                  var3 = false;
               }

               var25.setEnabled(var3);
               return;
            }
         }
      }

      while(true) {
         Throwable var4 = var10000;

         try {
            throw var4;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   void addBackStackState(BackStackRecord var1) {
      if (this.mBackStack == null) {
         this.mBackStack = new ArrayList();
      }

      this.mBackStack.add(var1);
   }

   void addCancellationSignal(Fragment var1, CancellationSignal var2) {
      if (this.mExitAnimationCancellationSignals.get(var1) == null) {
         this.mExitAnimationCancellationSignals.put(var1, new HashSet());
      }

      ((HashSet)this.mExitAnimationCancellationSignals.get(var1)).add(var2);
   }

   void addFragment(Fragment var1) {
      if (isLoggingEnabled(2)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("add: ");
         var2.append(var1);
         Log.v("FragmentManager", var2.toString());
      }

      this.makeActive(var1);
      if (!var1.mDetached) {
         this.mFragmentStore.addFragment(var1);
         var1.mRemoving = false;
         if (var1.mView == null) {
            var1.mHiddenChanged = false;
         }

         if (this.isMenuAvailable(var1)) {
            this.mNeedMenuInvalidate = true;
         }
      }

   }

   public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener var1) {
      if (this.mBackStackChangeListeners == null) {
         this.mBackStackChangeListeners = new ArrayList();
      }

      this.mBackStackChangeListeners.add(var1);
   }

   void addRetainedFragment(Fragment var1) {
      if (this.isStateSaved()) {
         if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
         }

      } else {
         if (this.mNonConfig.addRetainedFragment(var1) && isLoggingEnabled(2)) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Updating retained Fragments: Added ");
            var2.append(var1);
            Log.v("FragmentManager", var2.toString());
         }

      }
   }

   int allocBackStackIndex() {
      return this.mBackStackIndex.getAndIncrement();
   }

   void attachController(FragmentHostCallback<?> var1, FragmentContainer var2, Fragment var3) {
      if (this.mHost == null) {
         this.mHost = var1;
         this.mContainer = var2;
         this.mParent = var3;
         if (var3 != null) {
            this.updateOnBackPressedCallbackEnabled();
         }

         if (var1 instanceof OnBackPressedDispatcherOwner) {
            Object var4 = (OnBackPressedDispatcherOwner)var1;
            this.mOnBackPressedDispatcher = ((OnBackPressedDispatcherOwner)var4).getOnBackPressedDispatcher();
            if (var3 != null) {
               var4 = var3;
            }

            this.mOnBackPressedDispatcher.addCallback((LifecycleOwner)var4, this.mOnBackPressedCallback);
         }

         if (var3 != null) {
            this.mNonConfig = var3.mFragmentManager.getChildNonConfig(var3);
         } else if (var1 instanceof ViewModelStoreOwner) {
            this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner)var1).getViewModelStore());
         } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
         }

      } else {
         throw new IllegalStateException("Already attached");
      }
   }

   void attachFragment(Fragment var1) {
      StringBuilder var2;
      if (isLoggingEnabled(2)) {
         var2 = new StringBuilder();
         var2.append("attach: ");
         var2.append(var1);
         Log.v("FragmentManager", var2.toString());
      }

      if (var1.mDetached) {
         var1.mDetached = false;
         if (!var1.mAdded) {
            this.mFragmentStore.addFragment(var1);
            if (isLoggingEnabled(2)) {
               var2 = new StringBuilder();
               var2.append("add from attach: ");
               var2.append(var1);
               Log.v("FragmentManager", var2.toString());
            }

            if (this.isMenuAvailable(var1)) {
               this.mNeedMenuInvalidate = true;
            }
         }
      }

   }

   public FragmentTransaction beginTransaction() {
      return new BackStackRecord(this);
   }

   boolean checkForMenus() {
      Iterator var1 = this.mFragmentStore.getActiveFragments().iterator();
      boolean var2 = false;

      boolean var4;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         Fragment var3 = (Fragment)var1.next();
         var4 = var2;
         if (var3 != null) {
            var4 = this.isMenuAvailable(var3);
         }

         var2 = var4;
      } while(!var4);

      return true;
   }

   void completeExecute(BackStackRecord var1, boolean var2, boolean var3, boolean var4) {
      if (var2) {
         var1.executePopOps(var4);
      } else {
         var1.executeOps();
      }

      ArrayList var5 = new ArrayList(1);
      ArrayList var6 = new ArrayList(1);
      var5.add(var1);
      var6.add(var2);
      if (var3) {
         FragmentTransition.startTransitions(this, var5, var6, 0, 1, true, this.mFragmentTransitionCallback);
      }

      if (var4) {
         this.moveToState(this.mCurState, true);
      }

      Iterator var8 = this.mFragmentStore.getActiveFragments().iterator();

      while(var8.hasNext()) {
         Fragment var7 = (Fragment)var8.next();
         if (var7 != null && var7.mView != null && var7.mIsNewlyAdded && var1.interactsWith(var7.mContainerId)) {
            if (var7.mPostponedAlpha > 0.0F) {
               var7.mView.setAlpha(var7.mPostponedAlpha);
            }

            if (var4) {
               var7.mPostponedAlpha = 0.0F;
            } else {
               var7.mPostponedAlpha = -1.0F;
               var7.mIsNewlyAdded = false;
            }
         }
      }

   }

   void detachFragment(Fragment var1) {
      StringBuilder var2;
      if (isLoggingEnabled(2)) {
         var2 = new StringBuilder();
         var2.append("detach: ");
         var2.append(var1);
         Log.v("FragmentManager", var2.toString());
      }

      if (!var1.mDetached) {
         var1.mDetached = true;
         if (var1.mAdded) {
            if (isLoggingEnabled(2)) {
               var2 = new StringBuilder();
               var2.append("remove from detach: ");
               var2.append(var1);
               Log.v("FragmentManager", var2.toString());
            }

            this.mFragmentStore.removeFragment(var1);
            if (this.isMenuAvailable(var1)) {
               this.mNeedMenuInvalidate = true;
            }

            this.setVisibleRemovingFragment(var1);
         }
      }

   }

   void dispatchActivityCreated() {
      this.mStateSaved = false;
      this.mStopped = false;
      this.dispatchStateChange(2);
   }

   void dispatchConfigurationChanged(Configuration var1) {
      Iterator var2 = this.mFragmentStore.getFragments().iterator();

      while(var2.hasNext()) {
         Fragment var3 = (Fragment)var2.next();
         if (var3 != null) {
            var3.performConfigurationChanged(var1);
         }
      }

   }

   boolean dispatchContextItemSelected(MenuItem var1) {
      if (this.mCurState < 1) {
         return false;
      } else {
         Iterator var2 = this.mFragmentStore.getFragments().iterator();

         Fragment var3;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            var3 = (Fragment)var2.next();
         } while(var3 == null || !var3.performContextItemSelected(var1));

         return true;
      }
   }

   void dispatchCreate() {
      this.mStateSaved = false;
      this.mStopped = false;
      this.dispatchStateChange(1);
   }

   boolean dispatchCreateOptionsMenu(Menu var1, MenuInflater var2) {
      int var3 = this.mCurState;
      int var4 = 0;
      if (var3 < 1) {
         return false;
      } else {
         ArrayList var5 = null;
         Iterator var6 = this.mFragmentStore.getFragments().iterator();
         boolean var7 = false;

         while(var6.hasNext()) {
            Fragment var8 = (Fragment)var6.next();
            if (var8 != null && var8.performCreateOptionsMenu(var1, var2)) {
               ArrayList var9 = var5;
               if (var5 == null) {
                  var9 = new ArrayList();
               }

               var9.add(var8);
               var7 = true;
               var5 = var9;
            }
         }

         if (this.mCreatedMenus != null) {
            for(; var4 < this.mCreatedMenus.size(); ++var4) {
               Fragment var10 = (Fragment)this.mCreatedMenus.get(var4);
               if (var5 == null || !var5.contains(var10)) {
                  var10.onDestroyOptionsMenu();
               }
            }
         }

         this.mCreatedMenus = var5;
         return var7;
      }
   }

   void dispatchDestroy() {
      this.mDestroyed = true;
      this.execPendingActions(true);
      this.endAnimatingAwayFragments();
      this.dispatchStateChange(-1);
      this.mHost = null;
      this.mContainer = null;
      this.mParent = null;
      if (this.mOnBackPressedDispatcher != null) {
         this.mOnBackPressedCallback.remove();
         this.mOnBackPressedDispatcher = null;
      }

   }

   void dispatchDestroyView() {
      this.dispatchStateChange(1);
   }

   void dispatchLowMemory() {
      Iterator var1 = this.mFragmentStore.getFragments().iterator();

      while(var1.hasNext()) {
         Fragment var2 = (Fragment)var1.next();
         if (var2 != null) {
            var2.performLowMemory();
         }
      }

   }

   void dispatchMultiWindowModeChanged(boolean var1) {
      Iterator var2 = this.mFragmentStore.getFragments().iterator();

      while(var2.hasNext()) {
         Fragment var3 = (Fragment)var2.next();
         if (var3 != null) {
            var3.performMultiWindowModeChanged(var1);
         }
      }

   }

   boolean dispatchOptionsItemSelected(MenuItem var1) {
      if (this.mCurState < 1) {
         return false;
      } else {
         Iterator var2 = this.mFragmentStore.getFragments().iterator();

         Fragment var3;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            var3 = (Fragment)var2.next();
         } while(var3 == null || !var3.performOptionsItemSelected(var1));

         return true;
      }
   }

   void dispatchOptionsMenuClosed(Menu var1) {
      if (this.mCurState >= 1) {
         Iterator var2 = this.mFragmentStore.getFragments().iterator();

         while(var2.hasNext()) {
            Fragment var3 = (Fragment)var2.next();
            if (var3 != null) {
               var3.performOptionsMenuClosed(var1);
            }
         }

      }
   }

   void dispatchPause() {
      this.dispatchStateChange(3);
   }

   void dispatchPictureInPictureModeChanged(boolean var1) {
      Iterator var2 = this.mFragmentStore.getFragments().iterator();

      while(var2.hasNext()) {
         Fragment var3 = (Fragment)var2.next();
         if (var3 != null) {
            var3.performPictureInPictureModeChanged(var1);
         }
      }

   }

   boolean dispatchPrepareOptionsMenu(Menu var1) {
      int var2 = this.mCurState;
      boolean var3 = false;
      if (var2 < 1) {
         return false;
      } else {
         Iterator var4 = this.mFragmentStore.getFragments().iterator();

         while(var4.hasNext()) {
            Fragment var5 = (Fragment)var4.next();
            if (var5 != null && var5.performPrepareOptionsMenu(var1)) {
               var3 = true;
            }
         }

         return var3;
      }
   }

   void dispatchPrimaryNavigationFragmentChanged() {
      this.updateOnBackPressedCallbackEnabled();
      this.dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
   }

   void dispatchResume() {
      this.mStateSaved = false;
      this.mStopped = false;
      this.dispatchStateChange(4);
   }

   void dispatchStart() {
      this.mStateSaved = false;
      this.mStopped = false;
      this.dispatchStateChange(3);
   }

   void dispatchStop() {
      this.mStopped = true;
      this.dispatchStateChange(2);
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      StringBuilder var5 = new StringBuilder();
      var5.append(var1);
      var5.append("    ");
      String var45 = var5.toString();
      this.mFragmentStore.dump(var1, var2, var3, var4);
      ArrayList var40 = this.mCreatedMenus;
      byte var6 = 0;
      int var7;
      int var8;
      if (var40 != null) {
         var7 = var40.size();
         if (var7 > 0) {
            var3.print(var1);
            var3.println("Fragments Created Menus:");

            for(var8 = 0; var8 < var7; ++var8) {
               Fragment var41 = (Fragment)this.mCreatedMenus.get(var8);
               var3.print(var1);
               var3.print("  #");
               var3.print(var8);
               var3.print(": ");
               var3.println(var41.toString());
            }
         }
      }

      var40 = this.mBackStack;
      if (var40 != null) {
         var7 = var40.size();
         if (var7 > 0) {
            var3.print(var1);
            var3.println("Back Stack:");

            for(var8 = 0; var8 < var7; ++var8) {
               BackStackRecord var42 = (BackStackRecord)this.mBackStack.get(var8);
               var3.print(var1);
               var3.print("  #");
               var3.print(var8);
               var3.print(": ");
               var3.println(var42.toString());
               var42.dump(var45, var3);
            }
         }
      }

      var3.print(var1);
      StringBuilder var43 = new StringBuilder();
      var43.append("Back Stack Index: ");
      var43.append(this.mBackStackIndex.get());
      var3.println(var43.toString());
      var40 = this.mPendingActions;
      synchronized(var40){}

      label519: {
         Throwable var10000;
         boolean var10001;
         label520: {
            try {
               var7 = this.mPendingActions.size();
            } catch (Throwable var38) {
               var10000 = var38;
               var10001 = false;
               break label520;
            }

            if (var7 > 0) {
               try {
                  var3.print(var1);
                  var3.println("Pending Actions:");
               } catch (Throwable var37) {
                  var10000 = var37;
                  var10001 = false;
                  break label520;
               }

               for(var8 = var6; var8 < var7; ++var8) {
                  try {
                     FragmentManager.OpGenerator var44 = (FragmentManager.OpGenerator)this.mPendingActions.get(var8);
                     var3.print(var1);
                     var3.print("  #");
                     var3.print(var8);
                     var3.print(": ");
                     var3.println(var44);
                  } catch (Throwable var36) {
                     var10000 = var36;
                     var10001 = false;
                     break label520;
                  }
               }
            }

            label483:
            try {
               break label519;
            } catch (Throwable var35) {
               var10000 = var35;
               var10001 = false;
               break label483;
            }
         }

         while(true) {
            Throwable var39 = var10000;

            try {
               throw var39;
            } catch (Throwable var34) {
               var10000 = var34;
               var10001 = false;
               continue;
            }
         }
      }

      var3.print(var1);
      var3.println("FragmentManager misc state:");
      var3.print(var1);
      var3.print("  mHost=");
      var3.println(this.mHost);
      var3.print(var1);
      var3.print("  mContainer=");
      var3.println(this.mContainer);
      if (this.mParent != null) {
         var3.print(var1);
         var3.print("  mParent=");
         var3.println(this.mParent);
      }

      var3.print(var1);
      var3.print("  mCurState=");
      var3.print(this.mCurState);
      var3.print(" mStateSaved=");
      var3.print(this.mStateSaved);
      var3.print(" mStopped=");
      var3.print(this.mStopped);
      var3.print(" mDestroyed=");
      var3.println(this.mDestroyed);
      if (this.mNeedMenuInvalidate) {
         var3.print(var1);
         var3.print("  mNeedMenuInvalidate=");
         var3.println(this.mNeedMenuInvalidate);
      }

   }

   void enqueueAction(FragmentManager.OpGenerator var1, boolean var2) {
      if (!var2) {
         if (this.mHost == null) {
            if (this.mDestroyed) {
               throw new IllegalStateException("FragmentManager has been destroyed");
            }

            throw new IllegalStateException("FragmentManager has not been attached to a host.");
         }

         this.checkStateLoss();
      }

      ArrayList var3 = this.mPendingActions;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label309: {
         label308: {
            try {
               if (this.mHost == null) {
                  break label308;
               }
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label309;
            }

            try {
               this.mPendingActions.add(var1);
               this.scheduleCommit();
               return;
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break label309;
            }
         }

         if (var2) {
            label298:
            try {
               return;
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label298;
            }
         } else {
            label300:
            try {
               IllegalStateException var35 = new IllegalStateException("Activity has been destroyed");
               throw var35;
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break label300;
            }
         }
      }

      while(true) {
         Throwable var34 = var10000;

         try {
            throw var34;
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            continue;
         }
      }
   }

   boolean execPendingActions(boolean var1) {
      this.ensureExecReady(var1);

      for(var1 = false; this.generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop); var1 = true) {
         this.mExecutingActions = true;

         try {
            this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
         } finally {
            this.cleanupExec();
         }
      }

      this.updateOnBackPressedCallbackEnabled();
      this.doPendingDeferredStart();
      this.mFragmentStore.burpActive();
      return var1;
   }

   void execSingleAction(FragmentManager.OpGenerator var1, boolean var2) {
      if (!var2 || this.mHost != null && !this.mDestroyed) {
         this.ensureExecReady(var2);
         if (var1.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;

            try {
               this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
               this.cleanupExec();
            }
         }

         this.updateOnBackPressedCallbackEnabled();
         this.doPendingDeferredStart();
         this.mFragmentStore.burpActive();
      }
   }

   public boolean executePendingTransactions() {
      boolean var1 = this.execPendingActions(true);
      this.forcePostponedTransactions();
      return var1;
   }

   Fragment findActiveFragment(String var1) {
      return this.mFragmentStore.findActiveFragment(var1);
   }

   public Fragment findFragmentById(int var1) {
      return this.mFragmentStore.findFragmentById(var1);
   }

   public Fragment findFragmentByTag(String var1) {
      return this.mFragmentStore.findFragmentByTag(var1);
   }

   Fragment findFragmentByWho(String var1) {
      return this.mFragmentStore.findFragmentByWho(var1);
   }

   int getActiveFragmentCount() {
      return this.mFragmentStore.getActiveFragmentCount();
   }

   List<Fragment> getActiveFragments() {
      return this.mFragmentStore.getActiveFragments();
   }

   public FragmentManager.BackStackEntry getBackStackEntryAt(int var1) {
      return (FragmentManager.BackStackEntry)this.mBackStack.get(var1);
   }

   public int getBackStackEntryCount() {
      ArrayList var1 = this.mBackStack;
      int var2;
      if (var1 != null) {
         var2 = var1.size();
      } else {
         var2 = 0;
      }

      return var2;
   }

   public Fragment getFragment(Bundle var1, String var2) {
      String var3 = var1.getString(var2);
      if (var3 == null) {
         return null;
      } else {
         Fragment var4 = this.findActiveFragment(var3);
         if (var4 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Fragment no longer exists for key ");
            var5.append(var2);
            var5.append(": unique id ");
            var5.append(var3);
            this.throwException(new IllegalStateException(var5.toString()));
         }

         return var4;
      }
   }

   public FragmentFactory getFragmentFactory() {
      FragmentFactory var1 = this.mFragmentFactory;
      if (var1 != null) {
         return var1;
      } else {
         Fragment var2 = this.mParent;
         return var2 != null ? var2.mFragmentManager.getFragmentFactory() : this.mHostFragmentFactory;
      }
   }

   public List<Fragment> getFragments() {
      return this.mFragmentStore.getFragments();
   }

   Factory2 getLayoutInflaterFactory() {
      return this.mLayoutInflaterFactory;
   }

   FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
      return this.mLifecycleCallbacksDispatcher;
   }

   Fragment getParent() {
      return this.mParent;
   }

   public Fragment getPrimaryNavigationFragment() {
      return this.mPrimaryNav;
   }

   ViewModelStore getViewModelStore(Fragment var1) {
      return this.mNonConfig.getViewModelStore(var1);
   }

   void handleOnBackPressed() {
      this.execPendingActions(true);
      if (this.mOnBackPressedCallback.isEnabled()) {
         this.popBackStackImmediate();
      } else {
         this.mOnBackPressedDispatcher.onBackPressed();
      }

   }

   void hideFragment(Fragment var1) {
      if (isLoggingEnabled(2)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("hide: ");
         var2.append(var1);
         Log.v("FragmentManager", var2.toString());
      }

      if (!var1.mHidden) {
         var1.mHidden = true;
         var1.mHiddenChanged ^= true;
         this.setVisibleRemovingFragment(var1);
      }

   }

   public boolean isDestroyed() {
      return this.mDestroyed;
   }

   boolean isPrimaryNavigation(Fragment var1) {
      boolean var2 = true;
      if (var1 == null) {
         return true;
      } else {
         FragmentManager var3 = var1.mFragmentManager;
         if (!var1.equals(var3.getPrimaryNavigationFragment()) || !this.isPrimaryNavigation(var3.mParent)) {
            var2 = false;
         }

         return var2;
      }
   }

   boolean isStateAtLeast(int var1) {
      boolean var2;
      if (this.mCurState >= var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isStateSaved() {
      boolean var1;
      if (!this.mStateSaved && !this.mStopped) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   void makeActive(Fragment var1) {
      if (!this.mFragmentStore.containsActiveFragment(var1.mWho)) {
         FragmentStateManager var2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, var1);
         var2.restoreState(this.mHost.getContext().getClassLoader());
         this.mFragmentStore.makeActive(var2);
         if (var1.mRetainInstanceChangedWhileDetached) {
            if (var1.mRetainInstance) {
               this.addRetainedFragment(var1);
            } else {
               this.removeRetainedFragment(var1);
            }

            var1.mRetainInstanceChangedWhileDetached = false;
         }

         var2.setFragmentManagerState(this.mCurState);
         if (isLoggingEnabled(2)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Added fragment to active set ");
            var3.append(var1);
            Log.v("FragmentManager", var3.toString());
         }

      }
   }

   void moveFragmentToExpectedState(Fragment var1) {
      if (!this.mFragmentStore.containsActiveFragment(var1.mWho)) {
         if (isLoggingEnabled(3)) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Ignoring moving ");
            var8.append(var1);
            var8.append(" to state ");
            var8.append(this.mCurState);
            var8.append("since it is not added to ");
            var8.append(this);
            Log.d("FragmentManager", var8.toString());
         }

      } else {
         this.moveToState(var1);
         if (var1.mView != null) {
            Fragment var2 = this.mFragmentStore.findFragmentUnder(var1);
            if (var2 != null) {
               View var3 = var2.mView;
               ViewGroup var6 = var1.mContainer;
               int var4 = var6.indexOfChild(var3);
               int var5 = var6.indexOfChild(var1.mView);
               if (var5 < var4) {
                  var6.removeViewAt(var5);
                  var6.addView(var1.mView, var4);
               }
            }

            if (var1.mIsNewlyAdded && var1.mContainer != null) {
               if (var1.mPostponedAlpha > 0.0F) {
                  var1.mView.setAlpha(var1.mPostponedAlpha);
               }

               var1.mPostponedAlpha = 0.0F;
               var1.mIsNewlyAdded = false;
               FragmentAnim.AnimationOrAnimator var7 = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, var1, true);
               if (var7 != null) {
                  if (var7.animation != null) {
                     var1.mView.startAnimation(var7.animation);
                  } else {
                     var7.animator.setTarget(var1.mView);
                     var7.animator.start();
                  }
               }
            }
         }

         if (var1.mHiddenChanged) {
            this.completeShowHideFragment(var1);
         }

      }
   }

   void moveToState(int var1, boolean var2) {
      if (this.mHost == null && var1 != -1) {
         throw new IllegalStateException("No activity");
      } else if (var2 || var1 != this.mCurState) {
         this.mCurState = var1;
         Iterator var3 = this.mFragmentStore.getFragments().iterator();

         while(var3.hasNext()) {
            this.moveFragmentToExpectedState((Fragment)var3.next());
         }

         Iterator var4 = this.mFragmentStore.getActiveFragments().iterator();

         while(var4.hasNext()) {
            Fragment var5 = (Fragment)var4.next();
            if (var5 != null && !var5.mIsNewlyAdded) {
               this.moveFragmentToExpectedState(var5);
            }
         }

         this.startPendingDeferredFragments();
         if (this.mNeedMenuInvalidate) {
            FragmentHostCallback var6 = this.mHost;
            if (var6 != null && this.mCurState == 4) {
               var6.onSupportInvalidateOptionsMenu();
               this.mNeedMenuInvalidate = false;
            }
         }

      }
   }

   void moveToState(Fragment var1) {
      this.moveToState(var1, this.mCurState);
   }

   void moveToState(Fragment var1, int var2) {
      FragmentStateManager var3 = this.mFragmentStore.getFragmentStateManager(var1.mWho);
      byte var4 = 1;
      FragmentStateManager var5 = var3;
      if (var3 == null) {
         var5 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, var1);
         var5.setFragmentManagerState(1);
      }

      var2 = Math.min(var2, var5.computeMaxState());
      int var6 = var1.mState;
      ViewGroup var7 = null;
      StringBuilder var10;
      Fragment var11;
      StringBuilder var13;
      if (var6 <= var2) {
         label191: {
            if (var1.mState < var2 && !this.mExitAnimationCancellationSignals.isEmpty()) {
               this.cancelExitAnimation(var1);
            }

            label184: {
               label192: {
                  label182: {
                     var6 = var1.mState;
                     if (var6 != -1) {
                        if (var6 != 0) {
                           if (var6 != 1) {
                              if (var6 != 2) {
                                 if (var6 != 3) {
                                    var6 = var2;
                                    break label191;
                                 }
                                 break label184;
                              }
                              break label192;
                           }
                           break label182;
                        }
                     } else if (var2 > -1) {
                        if (isLoggingEnabled(3)) {
                           var10 = new StringBuilder();
                           var10.append("moveto ATTACHED: ");
                           var10.append(var1);
                           Log.d("FragmentManager", var10.toString());
                        }

                        if (var1.mTarget != null) {
                           if (!var1.mTarget.equals(this.findActiveFragment(var1.mTarget.mWho))) {
                              var13 = new StringBuilder();
                              var13.append("Fragment ");
                              var13.append(var1);
                              var13.append(" declared target fragment ");
                              var13.append(var1.mTarget);
                              var13.append(" that does not belong to this FragmentManager!");
                              throw new IllegalStateException(var13.toString());
                           }

                           if (var1.mTarget.mState < 1) {
                              this.moveToState(var1.mTarget, 1);
                           }

                           var1.mTargetWho = var1.mTarget.mWho;
                           var1.mTarget = null;
                        }

                        if (var1.mTargetWho != null) {
                           var11 = this.findActiveFragment(var1.mTargetWho);
                           if (var11 == null) {
                              var13 = new StringBuilder();
                              var13.append("Fragment ");
                              var13.append(var1);
                              var13.append(" declared target fragment ");
                              var13.append(var1.mTargetWho);
                              var13.append(" that does not belong to this FragmentManager!");
                              throw new IllegalStateException(var13.toString());
                           }

                           if (var11.mState < 1) {
                              this.moveToState(var11, 1);
                           }
                        }

                        var5.attach(this.mHost, this, this.mParent);
                     }

                     if (var2 > 0) {
                        var5.create();
                     }
                  }

                  if (var2 > -1) {
                     var5.ensureInflatedView();
                  }

                  if (var2 > 1) {
                     var5.createView(this.mContainer);
                     var5.activityCreated();
                     var5.restoreViewState();
                  }
               }

               if (var2 > 2) {
                  var5.start();
               }
            }

            var6 = var2;
            if (var2 > 3) {
               var5.resume();
               var6 = var2;
            }
         }
      } else {
         var6 = var2;
         if (var1.mState > var2) {
            label195: {
               var6 = var1.mState;
               if (var6 != 0) {
                  boolean var8 = false;
                  if (var6 != 1) {
                     if (var6 != 2) {
                        if (var6 != 3) {
                           if (var6 != 4) {
                              var6 = var2;
                              break label195;
                           }

                           if (var2 < 4) {
                              var5.pause();
                           }
                        }

                        if (var2 < 3) {
                           var5.stop();
                        }
                     }

                     if (var2 < 2) {
                        if (isLoggingEnabled(3)) {
                           var10 = new StringBuilder();
                           var10.append("movefrom ACTIVITY_CREATED: ");
                           var10.append(var1);
                           Log.d("FragmentManager", var10.toString());
                        }

                        if (var1.mView != null && this.mHost.onShouldSaveFragmentState(var1) && var1.mSavedViewState == null) {
                           var5.saveViewState();
                        }

                        if (var1.mView != null && var1.mContainer != null) {
                           var1.mContainer.endViewTransition(var1.mView);
                           var1.mView.clearAnimation();
                           if (!var1.isRemovingParent()) {
                              FragmentAnim.AnimationOrAnimator var12 = var7;
                              if (this.mCurState > -1) {
                                 var12 = var7;
                                 if (!this.mDestroyed) {
                                    var12 = var7;
                                    if (var1.mView.getVisibility() == 0) {
                                       var12 = var7;
                                       if (var1.mPostponedAlpha >= 0.0F) {
                                          var12 = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, var1, false);
                                       }
                                    }
                                 }
                              }

                              var1.mPostponedAlpha = 0.0F;
                              var7 = var1.mContainer;
                              View var9 = var1.mView;
                              if (var12 != null) {
                                 var1.setStateAfterAnimating(var2);
                                 FragmentAnim.animateRemoveFragment(var1, var12, this.mFragmentTransitionCallback);
                              }

                              var7.removeView(var9);
                              if (var7 != var1.mContainer) {
                                 return;
                              }
                           }
                        }

                        if (this.mExitAnimationCancellationSignals.get(var1) == null) {
                           this.destroyFragmentView(var1);
                        } else {
                           var1.setStateAfterAnimating(var2);
                        }
                     }
                  }

                  if (var2 < 1) {
                     boolean var14 = var8;
                     if (var1.mRemoving) {
                        var14 = var8;
                        if (!var1.isInBackStack()) {
                           var14 = true;
                        }
                     }

                     if (!var14 && !this.mNonConfig.shouldDestroy(var1)) {
                        if (var1.mTargetWho != null) {
                           var11 = this.findActiveFragment(var1.mTargetWho);
                           if (var11 != null && var11.getRetainInstance()) {
                              var1.mTarget = var11;
                           }
                        }
                     } else {
                        this.makeInactive(var5);
                     }

                     if (this.mExitAnimationCancellationSignals.get(var1) != null) {
                        var1.setStateAfterAnimating(var2);
                        var2 = var4;
                     } else {
                        var5.destroy(this.mHost, this.mNonConfig);
                     }
                  }
               }

               if (var2 < 0) {
                  var5.detach(this.mNonConfig);
               }

               var6 = var2;
            }
         }
      }

      if (var1.mState != var6) {
         if (isLoggingEnabled(3)) {
            var13 = new StringBuilder();
            var13.append("moveToState: Fragment state for ");
            var13.append(var1);
            var13.append(" not updated inline; expected state ");
            var13.append(var6);
            var13.append(" found ");
            var13.append(var1.mState);
            Log.d("FragmentManager", var13.toString());
         }

         var1.mState = var6;
      }

   }

   void noteStateNotSaved() {
      this.mStateSaved = false;
      this.mStopped = false;
      Iterator var1 = this.mFragmentStore.getFragments().iterator();

      while(var1.hasNext()) {
         Fragment var2 = (Fragment)var1.next();
         if (var2 != null) {
            var2.noteStateNotSaved();
         }
      }

   }

   @Deprecated
   public FragmentTransaction openTransaction() {
      return this.beginTransaction();
   }

   void performPendingDeferredStart(Fragment var1) {
      if (var1.mDeferStart) {
         if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
         }

         var1.mDeferStart = false;
         this.moveToState(var1, this.mCurState);
      }

   }

   public void popBackStack() {
      this.enqueueAction(new FragmentManager.PopBackStackState((String)null, -1, 0), false);
   }

   public void popBackStack(int var1, int var2) {
      if (var1 >= 0) {
         this.enqueueAction(new FragmentManager.PopBackStackState((String)null, var1, var2), false);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Bad id: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public void popBackStack(String var1, int var2) {
      this.enqueueAction(new FragmentManager.PopBackStackState(var1, -1, var2), false);
   }

   public boolean popBackStackImmediate() {
      return this.popBackStackImmediate((String)null, -1, 0);
   }

   public boolean popBackStackImmediate(int var1, int var2) {
      if (var1 >= 0) {
         return this.popBackStackImmediate((String)null, var1, var2);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Bad id: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public boolean popBackStackImmediate(String var1, int var2) {
      return this.popBackStackImmediate(var1, -1, var2);
   }

   boolean popBackStackState(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2, String var3, int var4, int var5) {
      ArrayList var6 = this.mBackStack;
      if (var6 == null) {
         return false;
      } else {
         if (var3 == null && var4 < 0 && (var5 & 1) == 0) {
            var4 = var6.size() - 1;
            if (var4 < 0) {
               return false;
            }

            var1.add(this.mBackStack.remove(var4));
            var2.add(true);
         } else {
            if (var3 == null && var4 < 0) {
               var4 = -1;
            } else {
               int var7;
               BackStackRecord var9;
               for(var7 = this.mBackStack.size() - 1; var7 >= 0; --var7) {
                  var9 = (BackStackRecord)this.mBackStack.get(var7);
                  if (var3 != null && var3.equals(var9.getName()) || var4 >= 0 && var4 == var9.mIndex) {
                     break;
                  }
               }

               if (var7 < 0) {
                  return false;
               }

               int var8 = var7;
               if ((var5 & 1) != 0) {
                  label70:
                  while(true) {
                     do {
                        var5 = var7 - 1;
                        var8 = var5;
                        if (var5 < 0) {
                           break label70;
                        }

                        var9 = (BackStackRecord)this.mBackStack.get(var5);
                        if (var3 == null) {
                           break;
                        }

                        var7 = var5;
                     } while(var3.equals(var9.getName()));

                     var8 = var5;
                     if (var4 < 0) {
                        break;
                     }

                     var8 = var5;
                     if (var4 != var9.mIndex) {
                        break;
                     }

                     var7 = var5;
                  }
               }

               var4 = var8;
            }

            if (var4 == this.mBackStack.size() - 1) {
               return false;
            }

            for(var5 = this.mBackStack.size() - 1; var5 > var4; --var5) {
               var1.add(this.mBackStack.remove(var5));
               var2.add(true);
            }
         }

         return true;
      }
   }

   public void putFragment(Bundle var1, String var2, Fragment var3) {
      if (var3.mFragmentManager != this) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Fragment ");
         var4.append(var3);
         var4.append(" is not currently in the FragmentManager");
         this.throwException(new IllegalStateException(var4.toString()));
      }

      var1.putString(var2, var3.mWho);
   }

   public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks var1, boolean var2) {
      this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(var1, var2);
   }

   void removeCancellationSignal(Fragment var1, CancellationSignal var2) {
      HashSet var3 = (HashSet)this.mExitAnimationCancellationSignals.get(var1);
      if (var3 != null && var3.remove(var2) && var3.isEmpty()) {
         this.mExitAnimationCancellationSignals.remove(var1);
         if (var1.mState < 3) {
            this.destroyFragmentView(var1);
            this.moveToState(var1, var1.getStateAfterAnimating());
         }
      }

   }

   void removeFragment(Fragment var1) {
      if (isLoggingEnabled(2)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("remove: ");
         var2.append(var1);
         var2.append(" nesting=");
         var2.append(var1.mBackStackNesting);
         Log.v("FragmentManager", var2.toString());
      }

      boolean var3 = var1.isInBackStack();
      if (!var1.mDetached || var3 ^ true) {
         this.mFragmentStore.removeFragment(var1);
         if (this.isMenuAvailable(var1)) {
            this.mNeedMenuInvalidate = true;
         }

         var1.mRemoving = true;
         this.setVisibleRemovingFragment(var1);
      }

   }

   public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener var1) {
      ArrayList var2 = this.mBackStackChangeListeners;
      if (var2 != null) {
         var2.remove(var1);
      }

   }

   void removeRetainedFragment(Fragment var1) {
      if (this.isStateSaved()) {
         if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
         }

      } else {
         if (this.mNonConfig.removeRetainedFragment(var1) && isLoggingEnabled(2)) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Updating retained Fragments: Removed ");
            var2.append(var1);
            Log.v("FragmentManager", var2.toString());
         }

      }
   }

   void restoreAllState(Parcelable var1, FragmentManagerNonConfig var2) {
      if (this.mHost instanceof ViewModelStoreOwner) {
         this.throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
      }

      this.mNonConfig.restoreFromSnapshot(var2);
      this.restoreSaveState(var1);
   }

   void restoreSaveState(Parcelable var1) {
      if (var1 != null) {
         FragmentManagerState var2 = (FragmentManagerState)var1;
         if (var2.mActive != null) {
            this.mFragmentStore.resetActiveFragments();
            Iterator var3 = var2.mActive.iterator();

            StringBuilder var7;
            while(var3.hasNext()) {
               FragmentState var4 = (FragmentState)var3.next();
               if (var4 != null) {
                  Fragment var5 = this.mNonConfig.findRetainedFragmentByWho(var4.mWho);
                  FragmentStateManager var8;
                  if (var5 != null) {
                     if (isLoggingEnabled(2)) {
                        var7 = new StringBuilder();
                        var7.append("restoreSaveState: re-attaching retained ");
                        var7.append(var5);
                        Log.v("FragmentManager", var7.toString());
                     }

                     var8 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, var5, var4);
                  } else {
                     var8 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mHost.getContext().getClassLoader(), this.getFragmentFactory(), var4);
                  }

                  Fragment var14 = var8.getFragment();
                  var14.mFragmentManager = this;
                  if (isLoggingEnabled(2)) {
                     StringBuilder var15 = new StringBuilder();
                     var15.append("restoreSaveState: active (");
                     var15.append(var14.mWho);
                     var15.append("): ");
                     var15.append(var14);
                     Log.v("FragmentManager", var15.toString());
                  }

                  var8.restoreState(this.mHost.getContext().getClassLoader());
                  this.mFragmentStore.makeActive(var8);
                  var8.setFragmentManagerState(this.mCurState);
               }
            }

            Iterator var16 = this.mNonConfig.getRetainedFragments().iterator();

            while(var16.hasNext()) {
               Fragment var10 = (Fragment)var16.next();
               if (!this.mFragmentStore.containsActiveFragment(var10.mWho)) {
                  if (isLoggingEnabled(2)) {
                     var7 = new StringBuilder();
                     var7.append("Discarding retained Fragment ");
                     var7.append(var10);
                     var7.append(" that was not found in the set of active Fragments ");
                     var7.append(var2.mActive);
                     Log.v("FragmentManager", var7.toString());
                  }

                  this.moveToState(var10, 1);
                  var10.mRemoving = true;
                  this.moveToState(var10, -1);
               }
            }

            this.mFragmentStore.restoreAddedFragments(var2.mAdded);
            if (var2.mBackStack != null) {
               this.mBackStack = new ArrayList(var2.mBackStack.length);

               for(int var6 = 0; var6 < var2.mBackStack.length; ++var6) {
                  BackStackRecord var9 = var2.mBackStack[var6].instantiate(this);
                  if (isLoggingEnabled(2)) {
                     StringBuilder var12 = new StringBuilder();
                     var12.append("restoreAllState: back stack #");
                     var12.append(var6);
                     var12.append(" (index ");
                     var12.append(var9.mIndex);
                     var12.append("): ");
                     var12.append(var9);
                     Log.v("FragmentManager", var12.toString());
                     PrintWriter var13 = new PrintWriter(new LogWriter("FragmentManager"));
                     var9.dump("  ", var13, false);
                     var13.close();
                  }

                  this.mBackStack.add(var9);
               }
            } else {
               this.mBackStack = null;
            }

            this.mBackStackIndex.set(var2.mBackStackIndex);
            if (var2.mPrimaryNavActiveWho != null) {
               Fragment var11 = this.findActiveFragment(var2.mPrimaryNavActiveWho);
               this.mPrimaryNav = var11;
               this.dispatchParentPrimaryNavigationFragmentChanged(var11);
            }

         }
      }
   }

   @Deprecated
   FragmentManagerNonConfig retainNonConfig() {
      if (this.mHost instanceof ViewModelStoreOwner) {
         this.throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
      }

      return this.mNonConfig.getSnapshot();
   }

   Parcelable saveAllState() {
      this.forcePostponedTransactions();
      this.endAnimatingAwayFragments();
      this.execPendingActions(true);
      this.mStateSaved = true;
      ArrayList var1 = this.mFragmentStore.saveActiveFragments();
      boolean var2 = var1.isEmpty();
      BackStackState[] var3 = null;
      if (var2) {
         if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "saveAllState: no fragments!");
         }

         return null;
      } else {
         ArrayList var4 = this.mFragmentStore.saveAddedFragments();
         ArrayList var5 = this.mBackStack;
         BackStackState[] var6 = var3;
         if (var5 != null) {
            int var7 = var5.size();
            var6 = var3;
            if (var7 > 0) {
               var3 = new BackStackState[var7];
               int var8 = 0;

               while(true) {
                  var6 = var3;
                  if (var8 >= var7) {
                     break;
                  }

                  var3[var8] = new BackStackState((BackStackRecord)this.mBackStack.get(var8));
                  if (isLoggingEnabled(2)) {
                     StringBuilder var10 = new StringBuilder();
                     var10.append("saveAllState: adding back stack #");
                     var10.append(var8);
                     var10.append(": ");
                     var10.append(this.mBackStack.get(var8));
                     Log.v("FragmentManager", var10.toString());
                  }

                  ++var8;
               }
            }
         }

         FragmentManagerState var9 = new FragmentManagerState();
         var9.mActive = var1;
         var9.mAdded = var4;
         var9.mBackStack = var6;
         var9.mBackStackIndex = this.mBackStackIndex.get();
         Fragment var11 = this.mPrimaryNav;
         if (var11 != null) {
            var9.mPrimaryNavActiveWho = var11.mWho;
         }

         return var9;
      }
   }

   public Fragment.SavedState saveFragmentInstanceState(Fragment var1) {
      FragmentStateManager var2 = this.mFragmentStore.getFragmentStateManager(var1.mWho);
      if (var2 == null || !var2.getFragment().equals(var1)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Fragment ");
         var3.append(var1);
         var3.append(" is not currently in the FragmentManager");
         this.throwException(new IllegalStateException(var3.toString()));
      }

      return var2.saveInstanceState();
   }

   void scheduleCommit() {
      ArrayList var1 = this.mPendingActions;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label436: {
         ArrayList var2;
         try {
            var2 = this.mPostponedTransactions;
         } catch (Throwable var46) {
            var10000 = var46;
            var10001 = false;
            break label436;
         }

         boolean var3;
         boolean var4;
         label427: {
            label426: {
               var3 = false;
               if (var2 != null) {
                  try {
                     if (!this.mPostponedTransactions.isEmpty()) {
                        break label426;
                     }
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break label436;
                  }
               }

               var4 = false;
               break label427;
            }

            var4 = true;
         }

         label418: {
            try {
               if (this.mPendingActions.size() != 1) {
                  break label418;
               }
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label436;
            }

            var3 = true;
         }

         if (var4 || var3) {
            try {
               this.mHost.getHandler().removeCallbacks(this.mExecCommit);
               this.mHost.getHandler().post(this.mExecCommit);
               this.updateOnBackPressedCallbackEnabled();
            } catch (Throwable var43) {
               var10000 = var43;
               var10001 = false;
               break label436;
            }
         }

         label406:
         try {
            return;
         } catch (Throwable var42) {
            var10000 = var42;
            var10001 = false;
            break label406;
         }
      }

      while(true) {
         Throwable var47 = var10000;

         try {
            throw var47;
         } catch (Throwable var41) {
            var10000 = var41;
            var10001 = false;
            continue;
         }
      }
   }

   void setExitAnimationOrder(Fragment var1, boolean var2) {
      ViewGroup var3 = this.getFragmentContainer(var1);
      if (var3 != null && var3 instanceof FragmentContainerView) {
         ((FragmentContainerView)var3).setDrawDisappearingViewsLast(var2 ^ true);
      }

   }

   public void setFragmentFactory(FragmentFactory var1) {
      this.mFragmentFactory = var1;
   }

   void setMaxLifecycle(Fragment var1, Lifecycle.State var2) {
      if (!var1.equals(this.findActiveFragment(var1.mWho)) || var1.mHost != null && var1.mFragmentManager != this) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Fragment ");
         var3.append(var1);
         var3.append(" is not an active fragment of FragmentManager ");
         var3.append(this);
         throw new IllegalArgumentException(var3.toString());
      } else {
         var1.mMaxState = var2;
      }
   }

   void setPrimaryNavigationFragment(Fragment var1) {
      if (var1 == null || var1.equals(this.findActiveFragment(var1.mWho)) && (var1.mHost == null || var1.mFragmentManager == this)) {
         Fragment var3 = this.mPrimaryNav;
         this.mPrimaryNav = var1;
         this.dispatchParentPrimaryNavigationFragmentChanged(var3);
         this.dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Fragment ");
         var2.append(var1);
         var2.append(" is not an active fragment of FragmentManager ");
         var2.append(this);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   void showFragment(Fragment var1) {
      if (isLoggingEnabled(2)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("show: ");
         var2.append(var1);
         Log.v("FragmentManager", var2.toString());
      }

      if (var1.mHidden) {
         var1.mHidden = false;
         var1.mHiddenChanged ^= true;
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      var1.append("FragmentManager{");
      var1.append(Integer.toHexString(System.identityHashCode(this)));
      var1.append(" in ");
      Fragment var2 = this.mParent;
      if (var2 != null) {
         var1.append(var2.getClass().getSimpleName());
         var1.append("{");
         var1.append(Integer.toHexString(System.identityHashCode(this.mParent)));
         var1.append("}");
      } else {
         var1.append(this.mHost.getClass().getSimpleName());
         var1.append("{");
         var1.append(Integer.toHexString(System.identityHashCode(this.mHost)));
         var1.append("}");
      }

      var1.append("}}");
      return var1.toString();
   }

   public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks var1) {
      this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(var1);
   }

   public interface BackStackEntry {
      @Deprecated
      CharSequence getBreadCrumbShortTitle();

      @Deprecated
      int getBreadCrumbShortTitleRes();

      @Deprecated
      CharSequence getBreadCrumbTitle();

      @Deprecated
      int getBreadCrumbTitleRes();

      int getId();

      String getName();
   }

   public abstract static class FragmentLifecycleCallbacks {
      public void onFragmentActivityCreated(FragmentManager var1, Fragment var2, Bundle var3) {
      }

      public void onFragmentAttached(FragmentManager var1, Fragment var2, Context var3) {
      }

      public void onFragmentCreated(FragmentManager var1, Fragment var2, Bundle var3) {
      }

      public void onFragmentDestroyed(FragmentManager var1, Fragment var2) {
      }

      public void onFragmentDetached(FragmentManager var1, Fragment var2) {
      }

      public void onFragmentPaused(FragmentManager var1, Fragment var2) {
      }

      public void onFragmentPreAttached(FragmentManager var1, Fragment var2, Context var3) {
      }

      public void onFragmentPreCreated(FragmentManager var1, Fragment var2, Bundle var3) {
      }

      public void onFragmentResumed(FragmentManager var1, Fragment var2) {
      }

      public void onFragmentSaveInstanceState(FragmentManager var1, Fragment var2, Bundle var3) {
      }

      public void onFragmentStarted(FragmentManager var1, Fragment var2) {
      }

      public void onFragmentStopped(FragmentManager var1, Fragment var2) {
      }

      public void onFragmentViewCreated(FragmentManager var1, Fragment var2, View var3, Bundle var4) {
      }

      public void onFragmentViewDestroyed(FragmentManager var1, Fragment var2) {
      }
   }

   public interface OnBackStackChangedListener {
      void onBackStackChanged();
   }

   interface OpGenerator {
      boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2);
   }

   private class PopBackStackState implements FragmentManager.OpGenerator {
      final int mFlags;
      final int mId;
      final String mName;

      PopBackStackState(String var2, int var3, int var4) {
         this.mName = var2;
         this.mId = var3;
         this.mFlags = var4;
      }

      public boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2) {
         return FragmentManager.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && FragmentManager.this.mPrimaryNav.getChildFragmentManager().popBackStackImmediate() ? false : FragmentManager.this.popBackStackState(var1, var2, this.mName, this.mId, this.mFlags);
      }
   }

   static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
      final boolean mIsBack;
      private int mNumPostponed;
      final BackStackRecord mRecord;

      StartEnterTransitionListener(BackStackRecord var1, boolean var2) {
         this.mIsBack = var2;
         this.mRecord = var1;
      }

      void cancelTransaction() {
         this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
      }

      void completeTransaction() {
         boolean var1;
         if (this.mNumPostponed > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         Iterator var2 = this.mRecord.mManager.getFragments().iterator();

         while(var2.hasNext()) {
            Fragment var3 = (Fragment)var2.next();
            var3.setOnStartEnterTransitionListener((Fragment.OnStartEnterTransitionListener)null);
            if (var1 && var3.isPostponed()) {
               var3.startPostponedEnterTransition();
            }
         }

         this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, var1 ^ true, true);
      }

      public boolean isReady() {
         boolean var1;
         if (this.mNumPostponed == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onStartEnterTransition() {
         int var1 = this.mNumPostponed - 1;
         this.mNumPostponed = var1;
         if (var1 == 0) {
            this.mRecord.mManager.scheduleCommit();
         }
      }

      public void startListening() {
         ++this.mNumPostponed;
      }
   }
}
