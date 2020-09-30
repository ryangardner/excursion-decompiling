/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zal;
import com.google.android.gms.common.internal.zam;
import com.google.android.gms.common.internal.zau;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.RemoteCreator;

public final class zaw
extends RemoteCreator<zam> {
    private static final zaw zaa = new zaw();

    private zaw() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zaa(Context context, int n, int n2) throws RemoteCreator.RemoteCreatorException {
        return zaa.zab(context, n, n2);
    }

    private final View zab(Context object, int n, int n2) throws RemoteCreator.RemoteCreatorException {
        try {
            zau zau2 = new zau(n, n2, null);
            IObjectWrapper iObjectWrapper = ObjectWrapper.wrap(object);
            return (View)ObjectWrapper.unwrap(((zam)this.getRemoteCreatorInstance((Context)object)).zaa(iObjectWrapper, zau2));
        }
        catch (Exception exception) {
            object = new StringBuilder(64);
            ((StringBuilder)object).append("Could not get button with size ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" and color ");
            ((StringBuilder)object).append(n2);
            throw new RemoteCreator.RemoteCreatorException(((StringBuilder)object).toString(), exception);
        }
    }

    @Override
    public final /* synthetic */ Object getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
        if (!(iInterface instanceof zam)) return new zal(iBinder);
        return (zam)iInterface;
    }
}

