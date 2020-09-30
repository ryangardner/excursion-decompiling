/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 */
package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;
import java.util.List;

final class BackStackState
implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>(){

        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        public BackStackState[] newArray(int n) {
            return new BackStackState[n];
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

    public BackStackState(Parcel parcel) {
        this.mOps = parcel.createIntArray();
        this.mFragmentWhos = parcel.createStringArrayList();
        this.mOldMaxLifecycleStates = parcel.createIntArray();
        this.mCurrentMaxLifecycleStates = parcel.createIntArray();
        this.mTransition = parcel.readInt();
        this.mName = parcel.readString();
        this.mIndex = parcel.readInt();
        this.mBreadCrumbTitleRes = parcel.readInt();
        this.mBreadCrumbTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mBreadCrumbShortTitleRes = parcel.readInt();
        this.mBreadCrumbShortTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSharedElementSourceNames = parcel.createStringArrayList();
        this.mSharedElementTargetNames = parcel.createStringArrayList();
        boolean bl = parcel.readInt() != 0;
        this.mReorderingAllowed = bl;
    }

    public BackStackState(BackStackRecord backStackRecord) {
        int n = backStackRecord.mOps.size();
        this.mOps = new int[n * 5];
        if (!backStackRecord.mAddToBackStack) throw new IllegalStateException("Not on back stack");
        this.mFragmentWhos = new ArrayList(n);
        this.mOldMaxLifecycleStates = new int[n];
        this.mCurrentMaxLifecycleStates = new int[n];
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= n) {
                this.mTransition = backStackRecord.mTransition;
                this.mName = backStackRecord.mName;
                this.mIndex = backStackRecord.mIndex;
                this.mBreadCrumbTitleRes = backStackRecord.mBreadCrumbTitleRes;
                this.mBreadCrumbTitleText = backStackRecord.mBreadCrumbTitleText;
                this.mBreadCrumbShortTitleRes = backStackRecord.mBreadCrumbShortTitleRes;
                this.mBreadCrumbShortTitleText = backStackRecord.mBreadCrumbShortTitleText;
                this.mSharedElementSourceNames = backStackRecord.mSharedElementSourceNames;
                this.mSharedElementTargetNames = backStackRecord.mSharedElementTargetNames;
                this.mReorderingAllowed = backStackRecord.mReorderingAllowed;
                return;
            }
            FragmentTransaction.Op op = (FragmentTransaction.Op)backStackRecord.mOps.get(n2);
            Object object = this.mOps;
            int n4 = n3 + 1;
            object[n3] = op.mCmd;
            ArrayList<String> arrayList = this.mFragmentWhos;
            object = op.mFragment != null ? op.mFragment.mWho : null;
            arrayList.add((String)object);
            object = this.mOps;
            int n5 = n4 + 1;
            object[n4] = op.mEnterAnim;
            object = this.mOps;
            n3 = n5 + 1;
            object[n5] = op.mExitAnim;
            object = this.mOps;
            n4 = n3 + 1;
            object[n3] = op.mPopEnterAnim;
            this.mOps[n4] = op.mPopExitAnim;
            this.mOldMaxLifecycleStates[n2] = op.mOldMaxState.ordinal();
            this.mCurrentMaxLifecycleStates[n2] = op.mCurrentMaxState.ordinal();
            ++n2;
            n3 = n4 + 1;
        } while (true);
    }

    public int describeContents() {
        return 0;
    }

    public BackStackRecord instantiate(FragmentManager fragmentManager) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
        int n = 0;
        int n2 = 0;
        do {
            if (n >= this.mOps.length) {
                backStackRecord.mTransition = this.mTransition;
                backStackRecord.mName = this.mName;
                backStackRecord.mIndex = this.mIndex;
                backStackRecord.mAddToBackStack = true;
                backStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
                backStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
                backStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
                backStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
                backStackRecord.mSharedElementSourceNames = this.mSharedElementSourceNames;
                backStackRecord.mSharedElementTargetNames = this.mSharedElementTargetNames;
                backStackRecord.mReorderingAllowed = this.mReorderingAllowed;
                backStackRecord.bumpBackStackNesting(1);
                return backStackRecord;
            }
            FragmentTransaction.Op op = new FragmentTransaction.Op();
            Object object = this.mOps;
            int n3 = n + 1;
            op.mCmd = object[n];
            if (FragmentManager.isLoggingEnabled(2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Instantiate ");
                ((StringBuilder)object).append(backStackRecord);
                ((StringBuilder)object).append(" op #");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" base fragment #");
                ((StringBuilder)object).append(this.mOps[n3]);
                Log.v((String)TAG, (String)((StringBuilder)object).toString());
            }
            op.mFragment = (object = this.mFragmentWhos.get(n2)) != null ? fragmentManager.findActiveFragment((String)object) : null;
            op.mOldMaxState = Lifecycle.State.values()[this.mOldMaxLifecycleStates[n2]];
            op.mCurrentMaxState = Lifecycle.State.values()[this.mCurrentMaxLifecycleStates[n2]];
            object = this.mOps;
            n = n3 + 1;
            op.mEnterAnim = (int)object[n3];
            object = this.mOps;
            n3 = n + 1;
            op.mExitAnim = (int)object[n];
            object = this.mOps;
            n = n3 + 1;
            op.mPopEnterAnim = (int)object[n3];
            op.mPopExitAnim = this.mOps[n];
            backStackRecord.mEnterAnim = op.mEnterAnim;
            backStackRecord.mExitAnim = op.mExitAnim;
            backStackRecord.mPopEnterAnim = op.mPopEnterAnim;
            backStackRecord.mPopExitAnim = op.mPopExitAnim;
            backStackRecord.addOp(op);
            ++n2;
            ++n;
        } while (true);
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeIntArray(this.mOps);
        parcel.writeStringList(this.mFragmentWhos);
        parcel.writeIntArray(this.mOldMaxLifecycleStates);
        parcel.writeIntArray(this.mCurrentMaxLifecycleStates);
        parcel.writeInt(this.mTransition);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.mBreadCrumbTitleRes);
        TextUtils.writeToParcel((CharSequence)this.mBreadCrumbTitleText, (Parcel)parcel, (int)0);
        parcel.writeInt(this.mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel((CharSequence)this.mBreadCrumbShortTitleText, (Parcel)parcel, (int)0);
        parcel.writeStringList(this.mSharedElementSourceNames);
        parcel.writeStringList(this.mSharedElementTargetNames);
        parcel.writeInt((int)this.mReorderingAllowed);
    }

}

