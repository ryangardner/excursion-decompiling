/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzit;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzlr;

public abstract class zziu<MessageType extends zzit<MessageType, BuilderType>, BuilderType extends zziu<MessageType, BuilderType>>
implements zzlr {
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzbn();
    }

    protected abstract BuilderType zza(MessageType var1);

    @Override
    public final /* synthetic */ zzlr zza(zzlq zzlq2) {
        if (!this.zzda().getClass().isInstance(zzlq2)) throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        return this.zza((MessageType)((zzit)zzlq2));
    }

    public abstract BuilderType zzbn();
}

