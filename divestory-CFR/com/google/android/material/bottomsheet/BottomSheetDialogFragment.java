/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.google.android.material.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetDialogFragment
extends AppCompatDialogFragment {
    private boolean waitingForDismissAllowingStateLoss;

    private void dismissAfterAnimation() {
        if (this.waitingForDismissAllowingStateLoss) {
            super.dismissAllowingStateLoss();
            return;
        }
        super.dismiss();
    }

    private void dismissWithAnimation(BottomSheetBehavior<?> bottomSheetBehavior, boolean bl) {
        this.waitingForDismissAllowingStateLoss = bl;
        if (bottomSheetBehavior.getState() == 5) {
            this.dismissAfterAnimation();
            return;
        }
        if (this.getDialog() instanceof BottomSheetDialog) {
            ((BottomSheetDialog)this.getDialog()).removeDefaultCallback();
        }
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetDismissCallback());
        bottomSheetBehavior.setState(5);
    }

    private boolean tryDismissWithAnimation(boolean bl) {
        Dialog dialog = this.getDialog();
        if (!(dialog instanceof BottomSheetDialog)) return false;
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = (dialog = (BottomSheetDialog)dialog).getBehavior();
        if (!bottomSheetBehavior.isHideable()) return false;
        if (!dialog.getDismissWithAnimation()) return false;
        this.dismissWithAnimation(bottomSheetBehavior, bl);
        return true;
    }

    @Override
    public void dismiss() {
        if (this.tryDismissWithAnimation(false)) return;
        super.dismiss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        if (this.tryDismissWithAnimation(true)) return;
        super.dismissAllowingStateLoss();
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return new BottomSheetDialog(this.getContext(), this.getTheme());
    }

    private class BottomSheetDismissCallback
    extends BottomSheetBehavior.BottomSheetCallback {
        private BottomSheetDismissCallback() {
        }

        @Override
        public void onSlide(View view, float f) {
        }

        @Override
        public void onStateChanged(View view, int n) {
            if (n != 5) return;
            BottomSheetDialogFragment.this.dismissAfterAnimation();
        }
    }

}

