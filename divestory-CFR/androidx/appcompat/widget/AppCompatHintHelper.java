/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 */
package androidx.appcompat.widget;

import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.WithHint;

class AppCompatHintHelper {
    private AppCompatHintHelper() {
    }

    static InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo, View view) {
        if (inputConnection == null) return inputConnection;
        if (editorInfo.hintText != null) return inputConnection;
        view = view.getParent();
        while (view instanceof View) {
            if (view instanceof WithHint) {
                editorInfo.hintText = ((WithHint)view).getHint();
                return inputConnection;
            }
            view = view.getParent();
        }
        return inputConnection;
    }
}

