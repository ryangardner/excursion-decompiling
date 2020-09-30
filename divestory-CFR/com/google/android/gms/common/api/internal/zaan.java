/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaag;

abstract class zaan
implements Runnable {
    private final /* synthetic */ zaad zaa;

    private zaan(zaad zaad2) {
        this.zaa = zaad2;
    }

    /* synthetic */ zaan(zaad zaad2, zaag zaag2) {
        this(zaad2);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public void run() {
        Throwable throwable2222;
        block6 : {
            block5 : {
                zaad.zac(this.zaa).lock();
                boolean bl = Thread.interrupted();
                if (!bl) break block5;
                zaad.zac(this.zaa).unlock();
                return;
            }
            this.zaa();
            zaad.zac(this.zaa).unlock();
            return;
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RuntimeException runtimeException) {}
                {
                    zaad.zad(this.zaa).zaa(runtimeException);
                    zaad.zac(this.zaa).unlock();
                    return;
                }
            }
        }
        zaad.zac(this.zaa).unlock();
        throw throwable2222;
    }

    protected abstract void zaa();
}

