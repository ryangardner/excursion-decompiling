/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.zzb;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.List;

public interface zzj<F> {
    public <T> F zza(zzb<T> var1, T var2);

    public <T> F zza(zzx var1, MetadataField<T> var2, T var3);

    public F zza(zzx var1, List<F> var2);

    public F zza(F var1);

    public F zzbj();

    public F zzbk();

    public <T> F zzc(MetadataField<T> var1, T var2);

    public F zze(MetadataField<?> var1);

    public F zzi(String var1);
}

