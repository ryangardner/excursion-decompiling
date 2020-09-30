/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.Window
 */
package androidx.appcompat.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

public class AppCompatDialogFragment
extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return new AppCompatDialog(this.getContext(), this.getTheme());
    }

    @Override
    public void setupDialog(Dialog dialog, int n) {
        if (!(dialog instanceof AppCompatDialog)) {
            super.setupDialog(dialog, n);
            return;
        }
        AppCompatDialog appCompatDialog = (AppCompatDialog)dialog;
        if (n != 1 && n != 2) {
            if (n != 3) {
                return;
            }
            dialog.getWindow().addFlags(24);
        }
        appCompatDialog.supportRequestWindowFeature(1);
    }
}

