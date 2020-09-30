/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.Window
 */
package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DialogFragment
extends Fragment
implements DialogInterface.OnCancelListener,
DialogInterface.OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    int mBackStackId = -1;
    boolean mCancelable = true;
    Dialog mDialog;
    private Runnable mDismissRunnable = new Runnable(){

        @Override
        public void run() {
            DialogFragment.this.mOnDismissListener.onDismiss((DialogInterface)DialogFragment.this.mDialog);
        }
    };
    boolean mDismissed;
    private Handler mHandler;
    DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener(){

        public void onCancel(DialogInterface object) {
            if (DialogFragment.this.mDialog == null) return;
            object = DialogFragment.this;
            ((DialogFragment)object).onCancel((DialogInterface)((DialogFragment)object).mDialog);
        }
    };
    DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener(){

        public void onDismiss(DialogInterface object) {
            if (DialogFragment.this.mDialog == null) return;
            object = DialogFragment.this;
            ((DialogFragment)object).onDismiss((DialogInterface)((DialogFragment)object).mDialog);
        }
    };
    boolean mShownByMe;
    boolean mShowsDialog = true;
    int mStyle = 0;
    int mTheme = 0;
    boolean mViewDestroyed;

    public void dismiss() {
        this.dismissInternal(false, false);
    }

    public void dismissAllowingStateLoss() {
        this.dismissInternal(true, false);
    }

    void dismissInternal(boolean bl, boolean bl2) {
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        Object object = this.mDialog;
        if (object != null) {
            object.setOnDismissListener(null);
            this.mDialog.dismiss();
            if (!bl2) {
                if (Looper.myLooper() == this.mHandler.getLooper()) {
                    this.onDismiss((DialogInterface)this.mDialog);
                } else {
                    this.mHandler.post(this.mDismissRunnable);
                }
            }
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            this.getParentFragmentManager().popBackStack(this.mBackStackId, 1);
            this.mBackStackId = -1;
            return;
        }
        object = this.getParentFragmentManager().beginTransaction();
        ((FragmentTransaction)object).remove(this);
        if (bl) {
            ((FragmentTransaction)object).commitAllowingStateLoss();
            return;
        }
        ((FragmentTransaction)object).commit();
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (!this.mShowsDialog) {
            return;
        }
        Object object = this.getView();
        if (object != null) {
            if (object.getParent() != null) throw new IllegalStateException("DialogFragment can not be attached to a container view");
            this.mDialog.setContentView(object);
        }
        if ((object = this.getActivity()) != null) {
            this.mDialog.setOwnerActivity((Activity)object);
        }
        this.mDialog.setCancelable(this.mCancelable);
        this.mDialog.setOnCancelListener(this.mOnCancelListener);
        this.mDialog.setOnDismissListener(this.mOnDismissListener);
        if (bundle == null) return;
        if ((bundle = bundle.getBundle(SAVED_DIALOG_STATE_TAG)) == null) return;
        this.mDialog.onRestoreInstanceState(bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.mShownByMe) return;
        this.mDismissed = false;
    }

    public void onCancel(DialogInterface dialogInterface) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHandler = new Handler();
        boolean bl = this.mContainerId == 0;
        this.mShowsDialog = bl;
        if (bundle == null) return;
        this.mStyle = bundle.getInt(SAVED_STYLE, 0);
        this.mTheme = bundle.getInt(SAVED_THEME, 0);
        this.mCancelable = bundle.getBoolean(SAVED_CANCELABLE, true);
        this.mShowsDialog = bundle.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
        this.mBackStackId = bundle.getInt(SAVED_BACK_STACK_ID, -1);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return new Dialog(this.requireContext(), this.getTheme());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Dialog dialog = this.mDialog;
        if (dialog == null) return;
        this.mViewDestroyed = true;
        dialog.setOnDismissListener(null);
        this.mDialog.dismiss();
        if (!this.mDismissed) {
            this.onDismiss((DialogInterface)this.mDialog);
        }
        this.mDialog = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (this.mShownByMe) return;
        if (this.mDismissed) return;
        this.mDismissed = true;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.mViewDestroyed) return;
        this.dismissInternal(true, true);
    }

    @Override
    public LayoutInflater onGetLayoutInflater(Bundle bundle) {
        if (!this.mShowsDialog) {
            return super.onGetLayoutInflater(bundle);
        }
        bundle = this.onCreateDialog(bundle);
        this.mDialog = bundle;
        if (bundle == null) return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
        this.setupDialog((Dialog)bundle, this.mStyle);
        return (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        boolean bl;
        int n;
        super.onSaveInstanceState(bundle);
        Dialog dialog = this.mDialog;
        if (dialog != null && (dialog = dialog.onSaveInstanceState()) != null) {
            bundle.putBundle(SAVED_DIALOG_STATE_TAG, (Bundle)dialog);
        }
        if ((n = this.mStyle) != 0) {
            bundle.putInt(SAVED_STYLE, n);
        }
        if ((n = this.mTheme) != 0) {
            bundle.putInt(SAVED_THEME, n);
        }
        if (!(bl = this.mCancelable)) {
            bundle.putBoolean(SAVED_CANCELABLE, bl);
        }
        if (!(bl = this.mShowsDialog)) {
            bundle.putBoolean(SAVED_SHOWS_DIALOG, bl);
        }
        if ((n = this.mBackStackId) == -1) return;
        bundle.putInt(SAVED_BACK_STACK_ID, n);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = this.mDialog;
        if (dialog == null) return;
        this.mViewDestroyed = false;
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Dialog dialog = this.mDialog;
        if (dialog == null) return;
        dialog.hide();
    }

    public final Dialog requireDialog() {
        Object object = this.getDialog();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("DialogFragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" does not have a Dialog.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void setCancelable(boolean bl) {
        this.mCancelable = bl;
        Dialog dialog = this.mDialog;
        if (dialog == null) return;
        dialog.setCancelable(bl);
    }

    public void setShowsDialog(boolean bl) {
        this.mShowsDialog = bl;
    }

    public void setStyle(int n, int n2) {
        this.mStyle = n;
        if (n == 2 || n == 3) {
            this.mTheme = 16973913;
        }
        if (n2 == 0) return;
        this.mTheme = n2;
    }

    public void setupDialog(Dialog dialog, int n) {
        if (n != 1 && n != 2) {
            if (n != 3) {
                return;
            }
            dialog.getWindow().addFlags(24);
        }
        dialog.requestWindowFeature(1);
    }

    public int show(FragmentTransaction fragmentTransaction, String string2) {
        int n;
        this.mDismissed = false;
        this.mShownByMe = true;
        fragmentTransaction.add(this, string2);
        this.mViewDestroyed = false;
        this.mBackStackId = n = fragmentTransaction.commit();
        return n;
    }

    public void show(FragmentManager object, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        object = ((FragmentManager)object).beginTransaction();
        ((FragmentTransaction)object).add(this, string2);
        ((FragmentTransaction)object).commit();
    }

    public void showNow(FragmentManager object, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        object = ((FragmentManager)object).beginTransaction();
        ((FragmentTransaction)object).add(this, string2);
        ((FragmentTransaction)object).commitNow();
    }

}

