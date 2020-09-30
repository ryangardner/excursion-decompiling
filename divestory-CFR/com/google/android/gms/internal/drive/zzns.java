/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzlj;
import com.google.android.gms.internal.drive.zzmf;
import java.io.IOException;
import java.util.List;
import java.util.Map;

interface zzns {
    public void zza(int var1, double var2) throws IOException;

    public void zza(int var1, float var2) throws IOException;

    public void zza(int var1, long var2) throws IOException;

    public void zza(int var1, zzjc var2) throws IOException;

    public <K, V> void zza(int var1, zzlj<K, V> var2, Map<K, V> var3) throws IOException;

    public void zza(int var1, Object var2) throws IOException;

    public void zza(int var1, Object var2, zzmf var3) throws IOException;

    public void zza(int var1, String var2) throws IOException;

    public void zza(int var1, List<String> var2) throws IOException;

    public void zza(int var1, List<?> var2, zzmf var3) throws IOException;

    public void zza(int var1, List<Integer> var2, boolean var3) throws IOException;

    @Deprecated
    public void zzak(int var1) throws IOException;

    @Deprecated
    public void zzal(int var1) throws IOException;

    public void zzb(int var1, long var2) throws IOException;

    @Deprecated
    public void zzb(int var1, Object var2, zzmf var3) throws IOException;

    public void zzb(int var1, List<zzjc> var2) throws IOException;

    @Deprecated
    public void zzb(int var1, List<?> var2, zzmf var3) throws IOException;

    public void zzb(int var1, List<Integer> var2, boolean var3) throws IOException;

    public void zzb(int var1, boolean var2) throws IOException;

    public void zzc(int var1, int var2) throws IOException;

    public void zzc(int var1, long var2) throws IOException;

    public void zzc(int var1, List<Long> var2, boolean var3) throws IOException;

    public int zzcd();

    public void zzd(int var1, int var2) throws IOException;

    public void zzd(int var1, List<Long> var2, boolean var3) throws IOException;

    public void zze(int var1, int var2) throws IOException;

    public void zze(int var1, List<Long> var2, boolean var3) throws IOException;

    public void zzf(int var1, int var2) throws IOException;

    public void zzf(int var1, List<Float> var2, boolean var3) throws IOException;

    public void zzg(int var1, List<Double> var2, boolean var3) throws IOException;

    public void zzh(int var1, List<Integer> var2, boolean var3) throws IOException;

    public void zzi(int var1, long var2) throws IOException;

    public void zzi(int var1, List<Boolean> var2, boolean var3) throws IOException;

    public void zzj(int var1, long var2) throws IOException;

    public void zzj(int var1, List<Integer> var2, boolean var3) throws IOException;

    public void zzk(int var1, List<Integer> var2, boolean var3) throws IOException;

    public void zzl(int var1, List<Long> var2, boolean var3) throws IOException;

    public void zzm(int var1, int var2) throws IOException;

    public void zzm(int var1, List<Integer> var2, boolean var3) throws IOException;

    public void zzn(int var1, int var2) throws IOException;

    public void zzn(int var1, List<Long> var2, boolean var3) throws IOException;
}

