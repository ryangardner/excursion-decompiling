/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkq;

final class zzng {
    private static void zza(byte by, byte by2, byte by3, byte by4, char[] arrc, int n) throws zzkq {
        if (zzng.zzg(by2)) throw zzkq.zzdn();
        if ((by << 28) + (by2 + 112) >> 30 != 0) throw zzkq.zzdn();
        if (zzng.zzg(by3)) throw zzkq.zzdn();
        if (zzng.zzg(by4)) throw zzkq.zzdn();
        by = (byte)((by & 7) << 18 | (by2 & 63) << 12 | (by3 & 63) << 6 | by4 & 63);
        arrc[n] = (char)((by >>> 10) + 55232);
        arrc[n + 1] = (char)((by & 1023) + 56320);
    }

    private static void zza(byte by, byte by2, byte by3, char[] arrc, int n) throws zzkq {
        if (zzng.zzg(by2)) throw zzkq.zzdn();
        if (by == -32) {
            if (by2 < -96) throw zzkq.zzdn();
        }
        if (by == -19) {
            if (by2 >= -96) throw zzkq.zzdn();
        }
        if (zzng.zzg(by3)) throw zzkq.zzdn();
        arrc[n] = (char)((by & 15) << 12 | (by2 & 63) << 6 | by3 & 63);
    }

    private static void zza(byte by, byte by2, char[] arrc, int n) throws zzkq {
        if (by < -62) throw zzkq.zzdn();
        if (zzng.zzg(by2)) throw zzkq.zzdn();
        arrc[n] = (char)((by & 31) << 6 | by2 & 63);
    }

    private static void zza(byte by, char[] arrc, int n) {
        arrc[n] = (char)by;
    }

    static /* synthetic */ void zzb(byte by, byte by2, byte by3, byte by4, char[] arrc, int n) throws zzkq {
        zzng.zza(by, by2, by3, by4, arrc, n);
    }

    static /* synthetic */ void zzb(byte by, byte by2, byte by3, char[] arrc, int n) throws zzkq {
        zzng.zza(by, by2, by3, arrc, n);
    }

    static /* synthetic */ void zzb(byte by, byte by2, char[] arrc, int n) throws zzkq {
        zzng.zza(by, by2, arrc, n);
    }

    static /* synthetic */ void zzb(byte by, char[] arrc, int n) {
        zzng.zza(by, arrc, n);
    }

    private static boolean zzd(byte by) {
        if (by < 0) return false;
        return true;
    }

    private static boolean zze(byte by) {
        if (by >= -32) return false;
        return true;
    }

    private static boolean zzf(byte by) {
        if (by >= -16) return false;
        return true;
    }

    private static boolean zzg(byte by) {
        if (by <= -65) return false;
        return true;
    }

    static /* synthetic */ boolean zzh(byte by) {
        return zzng.zzd(by);
    }

    static /* synthetic */ boolean zzi(byte by) {
        return zzng.zze(by);
    }

    static /* synthetic */ boolean zzj(byte by) {
        return zzng.zzf(by);
    }
}

