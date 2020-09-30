/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package androidx.transition;

import android.os.IBinder;
import androidx.transition.WindowIdImpl;

class WindowIdApi14
implements WindowIdImpl {
    private final IBinder mToken;

    WindowIdApi14(IBinder iBinder) {
        this.mToken = iBinder;
    }

    public boolean equals(Object object) {
        if (!(object instanceof WindowIdApi14)) return false;
        if (!((WindowIdApi14)object).mToken.equals((Object)this.mToken)) return false;
        return true;
    }

    public int hashCode() {
        return this.mToken.hashCode();
    }
}

