/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.lifecycle.FullLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

class FullLifecycleObserverAdapter
implements LifecycleEventObserver {
    private final FullLifecycleObserver mFullLifecycleObserver;
    private final LifecycleEventObserver mLifecycleEventObserver;

    FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver, LifecycleEventObserver lifecycleEventObserver) {
        this.mFullLifecycleObserver = fullLifecycleObserver;
        this.mLifecycleEventObserver = lifecycleEventObserver;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onStateChanged(LifecycleOwner var1_1, Lifecycle.Event var2_2) {
        switch (1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[var2_2.ordinal()]) {
            default: {
                ** break;
            }
            case 7: {
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            }
            case 6: {
                this.mFullLifecycleObserver.onDestroy(var1_1);
                ** break;
            }
            case 5: {
                this.mFullLifecycleObserver.onStop(var1_1);
                ** break;
            }
            case 4: {
                this.mFullLifecycleObserver.onPause(var1_1);
                ** break;
            }
            case 3: {
                this.mFullLifecycleObserver.onResume(var1_1);
                ** break;
            }
            case 2: {
                this.mFullLifecycleObserver.onStart(var1_1);
                ** break;
            }
            case 1: 
        }
        this.mFullLifecycleObserver.onCreate(var1_1);
lbl23: // 7 sources:
        var3_3 = this.mLifecycleEventObserver;
        if (var3_3 == null) return;
        var3_3.onStateChanged(var1_1, var2_2);
    }

}

