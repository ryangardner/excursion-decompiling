package androidx.fragment.app;

import android.util.Log;
import androidx.core.util.LogWriter;
import androidx.lifecycle.Lifecycle;
import java.io.PrintWriter;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, FragmentManager.OpGenerator {
   private static final String TAG = "FragmentManager";
   boolean mCommitted;
   int mIndex;
   final FragmentManager mManager;

   BackStackRecord(FragmentManager var1) {
      FragmentFactory var2 = var1.getFragmentFactory();
      ClassLoader var3;
      if (var1.mHost != null) {
         var3 = var1.mHost.getContext().getClassLoader();
      } else {
         var3 = null;
      }

      super(var2, var3);
      this.mIndex = -1;
      this.mManager = var1;
   }

   private static boolean isFragmentPostponed(FragmentTransaction.Op var0) {
      Fragment var2 = var0.mFragment;
      boolean var1;
      if (var2 != null && var2.mAdded && var2.mView != null && !var2.mDetached && !var2.mHidden && var2.isPostponed()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void bumpBackStackNesting(int var1) {
      if (this.mAddToBackStack) {
         if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Bump nesting in ");
            var2.append(this);
            var2.append(" by ");
            var2.append(var1);
            Log.v("FragmentManager", var2.toString());
         }

         int var3 = this.mOps.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            FragmentTransaction.Op var6 = (FragmentTransaction.Op)this.mOps.get(var4);
            if (var6.mFragment != null) {
               Fragment var5 = var6.mFragment;
               var5.mBackStackNesting += var1;
               if (FragmentManager.isLoggingEnabled(2)) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append("Bump nesting of ");
                  var7.append(var6.mFragment);
                  var7.append(" to ");
                  var7.append(var6.mFragment.mBackStackNesting);
                  Log.v("FragmentManager", var7.toString());
               }
            }
         }

      }
   }

   public int commit() {
      return this.commitInternal(false);
   }

   public int commitAllowingStateLoss() {
      return this.commitInternal(true);
   }

   int commitInternal(boolean var1) {
      if (!this.mCommitted) {
         if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Commit: ");
            var2.append(this);
            Log.v("FragmentManager", var2.toString());
            PrintWriter var3 = new PrintWriter(new LogWriter("FragmentManager"));
            this.dump("  ", var3);
            var3.close();
         }

         this.mCommitted = true;
         if (this.mAddToBackStack) {
            this.mIndex = this.mManager.allocBackStackIndex();
         } else {
            this.mIndex = -1;
         }

         this.mManager.enqueueAction(this, var1);
         return this.mIndex;
      } else {
         throw new IllegalStateException("commit already called");
      }
   }

   public void commitNow() {
      this.disallowAddToBackStack();
      this.mManager.execSingleAction(this, false);
   }

   public void commitNowAllowingStateLoss() {
      this.disallowAddToBackStack();
      this.mManager.execSingleAction(this, true);
   }

   public FragmentTransaction detach(Fragment var1) {
      if (var1.mFragmentManager != null && var1.mFragmentManager != this.mManager) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot detach Fragment attached to a different FragmentManager. Fragment ");
         var2.append(var1.toString());
         var2.append(" is already attached to a FragmentManager.");
         throw new IllegalStateException(var2.toString());
      } else {
         return super.detach(var1);
      }
   }

   void doAddOp(int var1, Fragment var2, String var3, int var4) {
      super.doAddOp(var1, var2, var3, var4);
      var2.mFragmentManager = this.mManager;
   }

   public void dump(String var1, PrintWriter var2) {
      this.dump(var1, var2, true);
   }

   public void dump(String var1, PrintWriter var2, boolean var3) {
      if (var3) {
         var2.print(var1);
         var2.print("mName=");
         var2.print(this.mName);
         var2.print(" mIndex=");
         var2.print(this.mIndex);
         var2.print(" mCommitted=");
         var2.println(this.mCommitted);
         if (this.mTransition != 0) {
            var2.print(var1);
            var2.print("mTransition=#");
            var2.print(Integer.toHexString(this.mTransition));
         }

         if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
            var2.print(var1);
            var2.print("mEnterAnim=#");
            var2.print(Integer.toHexString(this.mEnterAnim));
            var2.print(" mExitAnim=#");
            var2.println(Integer.toHexString(this.mExitAnim));
         }

         if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
            var2.print(var1);
            var2.print("mPopEnterAnim=#");
            var2.print(Integer.toHexString(this.mPopEnterAnim));
            var2.print(" mPopExitAnim=#");
            var2.println(Integer.toHexString(this.mPopExitAnim));
         }

         if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
            var2.print(var1);
            var2.print("mBreadCrumbTitleRes=#");
            var2.print(Integer.toHexString(this.mBreadCrumbTitleRes));
            var2.print(" mBreadCrumbTitleText=");
            var2.println(this.mBreadCrumbTitleText);
         }

         if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
            var2.print(var1);
            var2.print("mBreadCrumbShortTitleRes=#");
            var2.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
            var2.print(" mBreadCrumbShortTitleText=");
            var2.println(this.mBreadCrumbShortTitleText);
         }
      }

      if (!this.mOps.isEmpty()) {
         var2.print(var1);
         var2.println("Operations:");
         int var4 = this.mOps.size();

         for(int var5 = 0; var5 < var4; ++var5) {
            FragmentTransaction.Op var6 = (FragmentTransaction.Op)this.mOps.get(var5);
            String var7;
            switch(var6.mCmd) {
            case 0:
               var7 = "NULL";
               break;
            case 1:
               var7 = "ADD";
               break;
            case 2:
               var7 = "REPLACE";
               break;
            case 3:
               var7 = "REMOVE";
               break;
            case 4:
               var7 = "HIDE";
               break;
            case 5:
               var7 = "SHOW";
               break;
            case 6:
               var7 = "DETACH";
               break;
            case 7:
               var7 = "ATTACH";
               break;
            case 8:
               var7 = "SET_PRIMARY_NAV";
               break;
            case 9:
               var7 = "UNSET_PRIMARY_NAV";
               break;
            case 10:
               var7 = "OP_SET_MAX_LIFECYCLE";
               break;
            default:
               StringBuilder var8 = new StringBuilder();
               var8.append("cmd=");
               var8.append(var6.mCmd);
               var7 = var8.toString();
            }

            var2.print(var1);
            var2.print("  Op #");
            var2.print(var5);
            var2.print(": ");
            var2.print(var7);
            var2.print(" ");
            var2.println(var6.mFragment);
            if (var3) {
               if (var6.mEnterAnim != 0 || var6.mExitAnim != 0) {
                  var2.print(var1);
                  var2.print("enterAnim=#");
                  var2.print(Integer.toHexString(var6.mEnterAnim));
                  var2.print(" exitAnim=#");
                  var2.println(Integer.toHexString(var6.mExitAnim));
               }

               if (var6.mPopEnterAnim != 0 || var6.mPopExitAnim != 0) {
                  var2.print(var1);
                  var2.print("popEnterAnim=#");
                  var2.print(Integer.toHexString(var6.mPopEnterAnim));
                  var2.print(" popExitAnim=#");
                  var2.println(Integer.toHexString(var6.mPopExitAnim));
               }
            }
         }
      }

   }

   void executeOps() {
      int var1 = this.mOps.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         FragmentTransaction.Op var3 = (FragmentTransaction.Op)this.mOps.get(var2);
         Fragment var4 = var3.mFragment;
         if (var4 != null) {
            var4.setNextTransition(this.mTransition);
         }

         switch(var3.mCmd) {
         case 1:
            var4.setNextAnim(var3.mEnterAnim);
            this.mManager.setExitAnimationOrder(var4, false);
            this.mManager.addFragment(var4);
            break;
         case 2:
         default:
            StringBuilder var6 = new StringBuilder();
            var6.append("Unknown cmd: ");
            var6.append(var3.mCmd);
            throw new IllegalArgumentException(var6.toString());
         case 3:
            var4.setNextAnim(var3.mExitAnim);
            this.mManager.removeFragment(var4);
            break;
         case 4:
            var4.setNextAnim(var3.mExitAnim);
            this.mManager.hideFragment(var4);
            break;
         case 5:
            var4.setNextAnim(var3.mEnterAnim);
            this.mManager.setExitAnimationOrder(var4, false);
            this.mManager.showFragment(var4);
            break;
         case 6:
            var4.setNextAnim(var3.mExitAnim);
            this.mManager.detachFragment(var4);
            break;
         case 7:
            var4.setNextAnim(var3.mEnterAnim);
            this.mManager.setExitAnimationOrder(var4, false);
            this.mManager.attachFragment(var4);
            break;
         case 8:
            this.mManager.setPrimaryNavigationFragment(var4);
            break;
         case 9:
            this.mManager.setPrimaryNavigationFragment((Fragment)null);
            break;
         case 10:
            this.mManager.setMaxLifecycle(var4, var3.mCurrentMaxState);
         }

         if (!this.mReorderingAllowed && var3.mCmd != 1 && var4 != null) {
            this.mManager.moveFragmentToExpectedState(var4);
         }
      }

      if (!this.mReorderingAllowed) {
         FragmentManager var5 = this.mManager;
         var5.moveToState(var5.mCurState, true);
      }

   }

   void executePopOps(boolean var1) {
      for(int var2 = this.mOps.size() - 1; var2 >= 0; --var2) {
         FragmentTransaction.Op var3 = (FragmentTransaction.Op)this.mOps.get(var2);
         Fragment var4 = var3.mFragment;
         if (var4 != null) {
            var4.setNextTransition(FragmentManager.reverseTransit(this.mTransition));
         }

         switch(var3.mCmd) {
         case 1:
            var4.setNextAnim(var3.mPopExitAnim);
            this.mManager.setExitAnimationOrder(var4, true);
            this.mManager.removeFragment(var4);
            break;
         case 2:
         default:
            StringBuilder var6 = new StringBuilder();
            var6.append("Unknown cmd: ");
            var6.append(var3.mCmd);
            throw new IllegalArgumentException(var6.toString());
         case 3:
            var4.setNextAnim(var3.mPopEnterAnim);
            this.mManager.addFragment(var4);
            break;
         case 4:
            var4.setNextAnim(var3.mPopEnterAnim);
            this.mManager.showFragment(var4);
            break;
         case 5:
            var4.setNextAnim(var3.mPopExitAnim);
            this.mManager.setExitAnimationOrder(var4, true);
            this.mManager.hideFragment(var4);
            break;
         case 6:
            var4.setNextAnim(var3.mPopEnterAnim);
            this.mManager.attachFragment(var4);
            break;
         case 7:
            var4.setNextAnim(var3.mPopExitAnim);
            this.mManager.setExitAnimationOrder(var4, true);
            this.mManager.detachFragment(var4);
            break;
         case 8:
            this.mManager.setPrimaryNavigationFragment((Fragment)null);
            break;
         case 9:
            this.mManager.setPrimaryNavigationFragment(var4);
            break;
         case 10:
            this.mManager.setMaxLifecycle(var4, var3.mOldMaxState);
         }

         if (!this.mReorderingAllowed && var3.mCmd != 3 && var4 != null) {
            this.mManager.moveFragmentToExpectedState(var4);
         }
      }

      if (!this.mReorderingAllowed && var1) {
         FragmentManager var5 = this.mManager;
         var5.moveToState(var5.mCurState, true);
      }

   }

   Fragment expandOps(ArrayList<Fragment> var1, Fragment var2) {
      int var3 = 0;

      Fragment var4;
      for(var4 = var2; var3 < this.mOps.size(); var4 = var2) {
         int var6;
         label57: {
            FragmentTransaction.Op var5 = (FragmentTransaction.Op)this.mOps.get(var3);
            var6 = var5.mCmd;
            if (var6 != 1) {
               if (var6 == 2) {
                  Fragment var7 = var5.mFragment;
                  int var8 = var7.mContainerId;
                  int var9 = var1.size() - 1;
                  boolean var10 = false;
                  var6 = var3;

                  boolean var13;
                  for(var2 = var4; var9 >= 0; var10 = var13) {
                     Fragment var11 = (Fragment)var1.get(var9);
                     var4 = var2;
                     int var12 = var6;
                     var13 = var10;
                     if (var11.mContainerId == var8) {
                        if (var11 == var7) {
                           var13 = true;
                           var4 = var2;
                           var12 = var6;
                        } else {
                           var4 = var2;
                           var3 = var6;
                           if (var11 == var2) {
                              this.mOps.add(var6, new FragmentTransaction.Op(9, var11));
                              var3 = var6 + 1;
                              var4 = null;
                           }

                           FragmentTransaction.Op var14 = new FragmentTransaction.Op(3, var11);
                           var14.mEnterAnim = var5.mEnterAnim;
                           var14.mPopEnterAnim = var5.mPopEnterAnim;
                           var14.mExitAnim = var5.mExitAnim;
                           var14.mPopExitAnim = var5.mPopExitAnim;
                           this.mOps.add(var3, var14);
                           var1.remove(var11);
                           var12 = var3 + 1;
                           var13 = var10;
                        }
                     }

                     --var9;
                     var2 = var4;
                     var6 = var12;
                  }

                  if (var10) {
                     this.mOps.remove(var6);
                     --var6;
                  } else {
                     var5.mCmd = 1;
                     var1.add(var7);
                  }
                  break label57;
               }

               if (var6 == 3 || var6 == 6) {
                  var1.remove(var5.mFragment);
                  var2 = var4;
                  var6 = var3;
                  if (var5.mFragment == var4) {
                     this.mOps.add(var3, new FragmentTransaction.Op(9, var5.mFragment));
                     var6 = var3 + 1;
                     var2 = null;
                  }
                  break label57;
               }

               if (var6 != 7) {
                  if (var6 != 8) {
                     var2 = var4;
                     var6 = var3;
                  } else {
                     this.mOps.add(var3, new FragmentTransaction.Op(9, var4));
                     var6 = var3 + 1;
                     var2 = var5.mFragment;
                  }
                  break label57;
               }
            }

            var1.add(var5.mFragment);
            var6 = var3;
            var2 = var4;
         }

         var3 = var6 + 1;
      }

      return var4;
   }

   public boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2) {
      if (FragmentManager.isLoggingEnabled(2)) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Run: ");
         var3.append(this);
         Log.v("FragmentManager", var3.toString());
      }

      var1.add(this);
      var2.add(false);
      if (this.mAddToBackStack) {
         this.mManager.addBackStackState(this);
      }

      return true;
   }

   public CharSequence getBreadCrumbShortTitle() {
      return this.mBreadCrumbShortTitleRes != 0 ? this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes) : this.mBreadCrumbShortTitleText;
   }

   public int getBreadCrumbShortTitleRes() {
      return this.mBreadCrumbShortTitleRes;
   }

   public CharSequence getBreadCrumbTitle() {
      return this.mBreadCrumbTitleRes != 0 ? this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes) : this.mBreadCrumbTitleText;
   }

   public int getBreadCrumbTitleRes() {
      return this.mBreadCrumbTitleRes;
   }

   public int getId() {
      return this.mIndex;
   }

   public String getName() {
      return this.mName;
   }

   public FragmentTransaction hide(Fragment var1) {
      if (var1.mFragmentManager != null && var1.mFragmentManager != this.mManager) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot hide Fragment attached to a different FragmentManager. Fragment ");
         var2.append(var1.toString());
         var2.append(" is already attached to a FragmentManager.");
         throw new IllegalStateException(var2.toString());
      } else {
         return super.hide(var1);
      }
   }

   boolean interactsWith(int var1) {
      int var2 = this.mOps.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         FragmentTransaction.Op var4 = (FragmentTransaction.Op)this.mOps.get(var3);
         int var5;
         if (var4.mFragment != null) {
            var5 = var4.mFragment.mContainerId;
         } else {
            var5 = 0;
         }

         if (var5 != 0 && var5 == var1) {
            return true;
         }
      }

      return false;
   }

   boolean interactsWith(ArrayList<BackStackRecord> var1, int var2, int var3) {
      if (var3 == var2) {
         return false;
      } else {
         int var4 = this.mOps.size();
         int var5 = -1;

         int var9;
         for(int var6 = 0; var6 < var4; var5 = var9) {
            FragmentTransaction.Op var7 = (FragmentTransaction.Op)this.mOps.get(var6);
            int var8;
            if (var7.mFragment != null) {
               var8 = var7.mFragment.mContainerId;
            } else {
               var8 = 0;
            }

            var9 = var5;
            if (var8 != 0) {
               var9 = var5;
               if (var8 != var5) {
                  var5 = var2;

                  while(true) {
                     if (var5 >= var3) {
                        var9 = var8;
                        break;
                     }

                     BackStackRecord var10 = (BackStackRecord)var1.get(var5);
                     int var11 = var10.mOps.size();

                     for(var9 = 0; var9 < var11; ++var9) {
                        var7 = (FragmentTransaction.Op)var10.mOps.get(var9);
                        int var12;
                        if (var7.mFragment != null) {
                           var12 = var7.mFragment.mContainerId;
                        } else {
                           var12 = 0;
                        }

                        if (var12 == var8) {
                           return true;
                        }
                     }

                     ++var5;
                  }
               }
            }

            ++var6;
         }

         return false;
      }
   }

   public boolean isEmpty() {
      return this.mOps.isEmpty();
   }

   boolean isPostponed() {
      for(int var1 = 0; var1 < this.mOps.size(); ++var1) {
         if (isFragmentPostponed((FragmentTransaction.Op)this.mOps.get(var1))) {
            return true;
         }
      }

      return false;
   }

   public FragmentTransaction remove(Fragment var1) {
      if (var1.mFragmentManager != null && var1.mFragmentManager != this.mManager) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot remove Fragment attached to a different FragmentManager. Fragment ");
         var2.append(var1.toString());
         var2.append(" is already attached to a FragmentManager.");
         throw new IllegalStateException(var2.toString());
      } else {
         return super.remove(var1);
      }
   }

   public void runOnCommitRunnables() {
      if (this.mCommitRunnables != null) {
         for(int var1 = 0; var1 < this.mCommitRunnables.size(); ++var1) {
            ((Runnable)this.mCommitRunnables.get(var1)).run();
         }

         this.mCommitRunnables = null;
      }

   }

   public FragmentTransaction setMaxLifecycle(Fragment var1, Lifecycle.State var2) {
      StringBuilder var3;
      if (var1.mFragmentManager == this.mManager) {
         if (var2.isAtLeast(Lifecycle.State.CREATED)) {
            return super.setMaxLifecycle(var1, var2);
         } else {
            var3 = new StringBuilder();
            var3.append("Cannot set maximum Lifecycle below ");
            var3.append(Lifecycle.State.CREATED);
            throw new IllegalArgumentException(var3.toString());
         }
      } else {
         var3 = new StringBuilder();
         var3.append("Cannot setMaxLifecycle for Fragment not attached to FragmentManager ");
         var3.append(this.mManager);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener var1) {
      for(int var2 = 0; var2 < this.mOps.size(); ++var2) {
         FragmentTransaction.Op var3 = (FragmentTransaction.Op)this.mOps.get(var2);
         if (isFragmentPostponed(var3)) {
            var3.mFragment.setOnStartEnterTransitionListener(var1);
         }
      }

   }

   public FragmentTransaction setPrimaryNavigationFragment(Fragment var1) {
      if (var1 != null && var1.mFragmentManager != null && var1.mFragmentManager != this.mManager) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment ");
         var2.append(var1.toString());
         var2.append(" is already attached to a FragmentManager.");
         throw new IllegalStateException(var2.toString());
      } else {
         return super.setPrimaryNavigationFragment(var1);
      }
   }

   public FragmentTransaction show(Fragment var1) {
      if (var1.mFragmentManager != null && var1.mFragmentManager != this.mManager) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot show Fragment attached to a different FragmentManager. Fragment ");
         var2.append(var1.toString());
         var2.append(" is already attached to a FragmentManager.");
         throw new IllegalStateException(var2.toString());
      } else {
         return super.show(var1);
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      var1.append("BackStackEntry{");
      var1.append(Integer.toHexString(System.identityHashCode(this)));
      if (this.mIndex >= 0) {
         var1.append(" #");
         var1.append(this.mIndex);
      }

      if (this.mName != null) {
         var1.append(" ");
         var1.append(this.mName);
      }

      var1.append("}");
      return var1.toString();
   }

   Fragment trackAddedFragmentsInPop(ArrayList<Fragment> var1, Fragment var2) {
      for(int var3 = this.mOps.size() - 1; var3 >= 0; --var3) {
         FragmentTransaction.Op var4;
         label40: {
            var4 = (FragmentTransaction.Op)this.mOps.get(var3);
            int var5 = var4.mCmd;
            if (var5 != 1) {
               if (var5 == 3) {
                  break label40;
               }

               switch(var5) {
               case 6:
                  break label40;
               case 7:
                  break;
               case 8:
                  var2 = null;
                  continue;
               case 9:
                  var2 = var4.mFragment;
                  continue;
               case 10:
                  var4.mCurrentMaxState = var4.mOldMaxState;
               default:
                  continue;
               }
            }

            var1.remove(var4.mFragment);
            continue;
         }

         var1.add(var4.mFragment);
      }

      return var2;
   }
}
