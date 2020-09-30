/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 */
package androidx.core.os;

import android.os.Build;
import androidx.core.os.OperationCanceledException;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private Object mCancellationSignalObj;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void cancel() {
        block12 : {
            // MONITORENTER : this
            if (this.mIsCanceled) {
                // MONITOREXIT : this
                return;
            }
            this.mIsCanceled = true;
            this.mCancelInProgress = true;
            var1_1 = this.mOnCancelListener;
            var2_2 = this.mCancellationSignalObj;
            // MONITOREXIT : this
            if (var1_1 == null) ** GOTO lbl13
            try {
                var1_1.onCancel();
lbl13: // 2 sources:
                if (var2_2 != null && Build.VERSION.SDK_INT >= 16) {
                    ((android.os.CancellationSignal)var2_2).cancel();
                }
                break block12;
            }
            catch (Throwable var2_3) {}
            this.mCancelInProgress = false;
            this.notifyAll();
            // MONITOREXIT : this
            throw var2_3;
        }
        // MONITORENTER : this
        this.mCancelInProgress = false;
        this.notifyAll();
        // MONITOREXIT : this
    }

    public Object getCancellationSignalObject() {
        if (Build.VERSION.SDK_INT < 16) {
            return null;
        }
        synchronized (this) {
            if (this.mCancellationSignalObj != null) return this.mCancellationSignalObj;
            Object object = new android.os.CancellationSignal();
            this.mCancellationSignalObj = object;
            if (!this.mIsCanceled) return this.mCancellationSignalObj;
            object.cancel();
            return this.mCancellationSignalObj;
        }
    }

    public boolean isCanceled() {
        synchronized (this) {
            return this.mIsCanceled;
        }
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            this.waitForCancelFinishedLocked();
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (!this.mIsCanceled) return;
            if (onCancelListener == null) return;
            // MONITOREXIT [0, 2] lbl10 : MonitorExitStatement: MONITOREXIT : this
            onCancelListener.onCancel();
            return;
        }
    }

    public void throwIfCanceled() {
        if (this.isCanceled()) throw new OperationCanceledException();
    }

    public static interface OnCancelListener {
        public void onCancel();
    }

}

