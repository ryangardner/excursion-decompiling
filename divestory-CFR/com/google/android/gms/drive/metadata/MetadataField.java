/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public interface MetadataField<T> {
    public String getName();

    public T zza(Bundle var1);

    public T zza(DataHolder var1, int var2, int var3);

    public void zza(DataHolder var1, MetadataBundle var2, int var3, int var4);

    public void zza(T var1, Bundle var2);
}

