/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.google.android.gms.dynamic;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.common.zza;
import com.google.android.gms.internal.common.zzb;

public interface IObjectWrapper
extends IInterface {

    public static class Stub
    extends com.google.android.gms.internal.common.zza
    implements IObjectWrapper {
        public Stub() {
            super("com.google.android.gms.dynamic.IObjectWrapper");
        }

        public static IObjectWrapper asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            if (!(iInterface instanceof IObjectWrapper)) return new zza(iBinder);
            return (IObjectWrapper)iInterface;
        }

        public static final class zza
        extends zzb
        implements IObjectWrapper {
            zza(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.dynamic.IObjectWrapper");
            }
        }

    }

}

