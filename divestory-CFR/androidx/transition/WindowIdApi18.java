/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.WindowId
 */
package androidx.transition;

import android.view.View;
import android.view.WindowId;
import androidx.transition.WindowIdImpl;

class WindowIdApi18
implements WindowIdImpl {
    private final WindowId mWindowId;

    WindowIdApi18(View view) {
        this.mWindowId = view.getWindowId();
    }

    public boolean equals(Object object) {
        if (!(object instanceof WindowIdApi18)) return false;
        if (!((WindowIdApi18)object).mWindowId.equals((Object)this.mWindowId)) return false;
        return true;
    }

    public int hashCode() {
        return this.mWindowId.hashCode();
    }
}

