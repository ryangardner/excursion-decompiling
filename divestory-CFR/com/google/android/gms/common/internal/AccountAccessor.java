/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.IAccountAccessor;

public class AccountAccessor
extends IAccountAccessor.Stub {
    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static Account getAccountBinderSafe(IAccountAccessor iAccountAccessor) {
        Throwable throwable2222;
        if (iAccountAccessor == null) return null;
        long l = Binder.clearCallingIdentity();
        iAccountAccessor = iAccountAccessor.zza();
        Binder.restoreCallingIdentity((long)l);
        return iAccountAccessor;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                Log.w((String)"AccountAccessor", (String)"Remote account accessor probably died");
            }
            Binder.restoreCallingIdentity((long)l);
            return null;
        }
        Binder.restoreCallingIdentity((long)l);
        throw throwable2222;
    }

    public boolean equals(Object object) {
        throw new NoSuchMethodError();
    }

    @Override
    public final Account zza() {
        throw new NoSuchMethodError();
    }
}

