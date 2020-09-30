/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;

public final class GmsLogger {
    private static final int zza = 15;
    private static final String zzb;
    private final String zzc;
    private final String zzd;

    public GmsLogger(String string2) {
        this(string2, null);
    }

    public GmsLogger(String string2, String string3) {
        Preconditions.checkNotNull(string2, "log tag cannot be null");
        boolean bl = string2.length() <= 23;
        Preconditions.checkArgument(bl, "tag \"%s\" is longer than the %d character maximum", string2, 23);
        this.zzc = string2;
        if (string3 != null && string3.length() > 0) {
            this.zzd = string3;
            return;
        }
        this.zzd = null;
    }

    private final String zza(String string2) {
        String string3 = this.zzd;
        if (string3 != null) return string3.concat(string2);
        return string2;
    }

    private final String zza(String string2, Object ... object) {
        object = String.format(string2, object);
        string2 = this.zzd;
        if (string2 != null) return string2.concat((String)object);
        return object;
    }

    public final boolean canLog(int n) {
        return Log.isLoggable((String)this.zzc, (int)n);
    }

    public final boolean canLogPii() {
        return false;
    }

    public final void d(String string2, String string3) {
        if (!this.canLog(3)) return;
        Log.d((String)string2, (String)this.zza(string3));
    }

    public final void d(String string2, String string3, Throwable throwable) {
        if (!this.canLog(3)) return;
        Log.d((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }

    public final void e(String string2, String string3) {
        if (!this.canLog(6)) return;
        Log.e((String)string2, (String)this.zza(string3));
    }

    public final void e(String string2, String string3, Throwable throwable) {
        if (!this.canLog(6)) return;
        Log.e((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }

    public final void efmt(String string2, String string3, Object ... arrobject) {
        if (!this.canLog(6)) return;
        Log.e((String)string2, (String)this.zza(string3, arrobject));
    }

    public final void i(String string2, String string3) {
        if (!this.canLog(4)) return;
        Log.i((String)string2, (String)this.zza(string3));
    }

    public final void i(String string2, String string3, Throwable throwable) {
        if (!this.canLog(4)) return;
        Log.i((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }

    public final void pii(String string2, String string3) {
        if (!this.canLogPii()) return;
        string2 = String.valueOf(string2);
        string2 = " PII_LOG".length() != 0 ? string2.concat(" PII_LOG") : new String(string2);
        Log.i((String)string2, (String)this.zza(string3));
    }

    public final void pii(String string2, String string3, Throwable throwable) {
        if (!this.canLogPii()) return;
        string2 = String.valueOf(string2);
        string2 = " PII_LOG".length() != 0 ? string2.concat(" PII_LOG") : new String(string2);
        Log.i((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }

    public final void v(String string2, String string3) {
        if (!this.canLog(2)) return;
        Log.v((String)string2, (String)this.zza(string3));
    }

    public final void v(String string2, String string3, Throwable throwable) {
        if (!this.canLog(2)) return;
        Log.v((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }

    public final void w(String string2, String string3) {
        if (!this.canLog(5)) return;
        Log.w((String)string2, (String)this.zza(string3));
    }

    public final void w(String string2, String string3, Throwable throwable) {
        if (!this.canLog(5)) return;
        Log.w((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }

    public final void wfmt(String string2, String string3, Object ... arrobject) {
        if (!this.canLog(5)) return;
        Log.w((String)this.zzc, (String)this.zza(string3, arrobject));
    }

    public final void wtf(String string2, String string3, Throwable throwable) {
        if (!this.canLog(7)) return;
        Log.e((String)string2, (String)this.zza(string3), (Throwable)throwable);
        Log.wtf((String)string2, (String)this.zza(string3), (Throwable)throwable);
    }
}

