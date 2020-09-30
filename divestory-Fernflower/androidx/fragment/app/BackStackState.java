package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

final class BackStackState implements Parcelable {
   public static final Creator<BackStackState> CREATOR = new Creator<BackStackState>() {
      public BackStackState createFromParcel(Parcel var1) {
         return new BackStackState(var1);
      }

      public BackStackState[] newArray(int var1) {
         return new BackStackState[var1];
      }
   };
   private static final String TAG = "FragmentManager";
   final int mBreadCrumbShortTitleRes;
   final CharSequence mBreadCrumbShortTitleText;
   final int mBreadCrumbTitleRes;
   final CharSequence mBreadCrumbTitleText;
   final int[] mCurrentMaxLifecycleStates;
   final ArrayList<String> mFragmentWhos;
   final int mIndex;
   final String mName;
   final int[] mOldMaxLifecycleStates;
   final int[] mOps;
   final boolean mReorderingAllowed;
   final ArrayList<String> mSharedElementSourceNames;
   final ArrayList<String> mSharedElementTargetNames;
   final int mTransition;

   public BackStackState(Parcel var1) {
      this.mOps = var1.createIntArray();
      this.mFragmentWhos = var1.createStringArrayList();
      this.mOldMaxLifecycleStates = var1.createIntArray();
      this.mCurrentMaxLifecycleStates = var1.createIntArray();
      this.mTransition = var1.readInt();
      this.mName = var1.readString();
      this.mIndex = var1.readInt();
      this.mBreadCrumbTitleRes = var1.readInt();
      this.mBreadCrumbTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(var1);
      this.mBreadCrumbShortTitleRes = var1.readInt();
      this.mBreadCrumbShortTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(var1);
      this.mSharedElementSourceNames = var1.createStringArrayList();
      this.mSharedElementTargetNames = var1.createStringArrayList();
      boolean var2;
      if (var1.readInt() != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.mReorderingAllowed = var2;
   }

   public BackStackState(BackStackRecord var1) {
      int var2 = var1.mOps.size();
      this.mOps = new int[var2 * 5];
      if (var1.mAddToBackStack) {
         this.mFragmentWhos = new ArrayList(var2);
         this.mOldMaxLifecycleStates = new int[var2];
         this.mCurrentMaxLifecycleStates = new int[var2];
         int var3 = 0;

         int var7;
         for(int var4 = 0; var3 < var2; var4 = var7 + 1) {
            FragmentTransaction.Op var5 = (FragmentTransaction.Op)var1.mOps.get(var3);
            int[] var6 = this.mOps;
            var7 = var4 + 1;
            var6[var4] = var5.mCmd;
            ArrayList var8 = this.mFragmentWhos;
            String var10;
            if (var5.mFragment != null) {
               var10 = var5.mFragment.mWho;
            } else {
               var10 = null;
            }

            var8.add(var10);
            var6 = this.mOps;
            int var9 = var7 + 1;
            var6[var7] = var5.mEnterAnim;
            var6 = this.mOps;
            var4 = var9 + 1;
            var6[var9] = var5.mExitAnim;
            var6 = this.mOps;
            var7 = var4 + 1;
            var6[var4] = var5.mPopEnterAnim;
            this.mOps[var7] = var5.mPopExitAnim;
            this.mOldMaxLifecycleStates[var3] = var5.mOldMaxState.ordinal();
            this.mCurrentMaxLifecycleStates[var3] = var5.mCurrentMaxState.ordinal();
            ++var3;
         }

         this.mTransition = var1.mTransition;
         this.mName = var1.mName;
         this.mIndex = var1.mIndex;
         this.mBreadCrumbTitleRes = var1.mBreadCrumbTitleRes;
         this.mBreadCrumbTitleText = var1.mBreadCrumbTitleText;
         this.mBreadCrumbShortTitleRes = var1.mBreadCrumbShortTitleRes;
         this.mBreadCrumbShortTitleText = var1.mBreadCrumbShortTitleText;
         this.mSharedElementSourceNames = var1.mSharedElementSourceNames;
         this.mSharedElementTargetNames = var1.mSharedElementTargetNames;
         this.mReorderingAllowed = var1.mReorderingAllowed;
      } else {
         throw new IllegalStateException("Not on back stack");
      }
   }

   public int describeContents() {
      return 0;
   }

   public BackStackRecord instantiate(FragmentManager var1) {
      BackStackRecord var2 = new BackStackRecord(var1);
      int var3 = 0;

      for(int var4 = 0; var3 < this.mOps.length; ++var3) {
         FragmentTransaction.Op var5 = new FragmentTransaction.Op();
         int[] var6 = this.mOps;
         int var7 = var3 + 1;
         var5.mCmd = var6[var3];
         if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Instantiate ");
            var8.append(var2);
            var8.append(" op #");
            var8.append(var4);
            var8.append(" base fragment #");
            var8.append(this.mOps[var7]);
            Log.v("FragmentManager", var8.toString());
         }

         String var9 = (String)this.mFragmentWhos.get(var4);
         if (var9 != null) {
            var5.mFragment = var1.findActiveFragment(var9);
         } else {
            var5.mFragment = null;
         }

         var5.mOldMaxState = Lifecycle.State.values()[this.mOldMaxLifecycleStates[var4]];
         var5.mCurrentMaxState = Lifecycle.State.values()[this.mCurrentMaxLifecycleStates[var4]];
         var6 = this.mOps;
         var3 = var7 + 1;
         var5.mEnterAnim = var6[var7];
         var6 = this.mOps;
         var7 = var3 + 1;
         var5.mExitAnim = var6[var3];
         var6 = this.mOps;
         var3 = var7 + 1;
         var5.mPopEnterAnim = var6[var7];
         var5.mPopExitAnim = this.mOps[var3];
         var2.mEnterAnim = var5.mEnterAnim;
         var2.mExitAnim = var5.mExitAnim;
         var2.mPopEnterAnim = var5.mPopEnterAnim;
         var2.mPopExitAnim = var5.mPopExitAnim;
         var2.addOp(var5);
         ++var4;
      }

      var2.mTransition = this.mTransition;
      var2.mName = this.mName;
      var2.mIndex = this.mIndex;
      var2.mAddToBackStack = true;
      var2.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
      var2.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
      var2.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
      var2.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
      var2.mSharedElementSourceNames = this.mSharedElementSourceNames;
      var2.mSharedElementTargetNames = this.mSharedElementTargetNames;
      var2.mReorderingAllowed = this.mReorderingAllowed;
      var2.bumpBackStackNesting(1);
      return var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeIntArray(this.mOps);
      var1.writeStringList(this.mFragmentWhos);
      var1.writeIntArray(this.mOldMaxLifecycleStates);
      var1.writeIntArray(this.mCurrentMaxLifecycleStates);
      var1.writeInt(this.mTransition);
      var1.writeString(this.mName);
      var1.writeInt(this.mIndex);
      var1.writeInt(this.mBreadCrumbTitleRes);
      TextUtils.writeToParcel(this.mBreadCrumbTitleText, var1, 0);
      var1.writeInt(this.mBreadCrumbShortTitleRes);
      TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, var1, 0);
      var1.writeStringList(this.mSharedElementSourceNames);
      var1.writeStringList(this.mSharedElementTargetNames);
      var1.writeInt(this.mReorderingAllowed);
   }
}
