/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.logging;

import android.util.Log;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.Locale;

public class Logger {
    private final String zza;
    private final String zzb;
    private final GmsLogger zzc;
    private final int zzd;

    private Logger(String string2, String string3) {
        int n;
        this.zzb = string3;
        this.zza = string2;
        this.zzc = new GmsLogger(string2);
        for (n = 2; 7 >= n && !Log.isLoggable((String)this.zza, (int)n); ++n) {
        }
        this.zzd = n;
    }

    /*
     * WARNING - void declaration
     */
    public Logger(String string2, String ... object) {
        void var2_5;
        if (((Object)object).length == 0) {
            String string3 = "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            for (Object object2 : object) {
                if (stringBuilder.length() > 1) {
                    stringBuilder.append(",");
                }
                stringBuilder.append((String)object2);
            }
            stringBuilder.append(']');
            stringBuilder.append(' ');
            String string4 = stringBuilder.toString();
        }
        this(string2, (String)var2_5);
    }

    public void d(String string2, Throwable throwable, Object ... arrobject) {
        if (!this.isLoggable(3)) return;
        Log.d((String)this.zza, (String)this.format(string2, arrobject), (Throwable)throwable);
    }

    public void d(String string2, Object ... arrobject) {
        if (!this.isLoggable(3)) return;
        Log.d((String)this.zza, (String)this.format(string2, arrobject));
    }

    public void e(String string2, Throwable throwable, Object ... arrobject) {
        Log.e((String)this.zza, (String)this.format(string2, arrobject), (Throwable)throwable);
    }

    public void e(String string2, Object ... arrobject) {
        Log.e((String)this.zza, (String)this.format(string2, arrobject));
    }

    protected String format(String string2, Object ... arrobject) {
        String string3 = string2;
        if (arrobject == null) return this.zzb.concat(string3);
        string3 = string2;
        if (arrobject.length <= 0) return this.zzb.concat(string3);
        string3 = String.format(Locale.US, string2, arrobject);
        return this.zzb.concat(string3);
    }

    public String getTag() {
        return this.zza;
    }

    public void i(String string2, Object ... arrobject) {
        Log.i((String)this.zza, (String)this.format(string2, arrobject));
    }

    public boolean isLoggable(int n) {
        if (this.zzd > n) return false;
        return true;
    }

    public void v(String string2, Throwable throwable, Object ... arrobject) {
        if (!this.isLoggable(2)) return;
        Log.v((String)this.zza, (String)this.format(string2, arrobject), (Throwable)throwable);
    }

    public void v(String string2, Object ... arrobject) {
        if (!this.isLoggable(2)) return;
        Log.v((String)this.zza, (String)this.format(string2, arrobject));
    }

    public void w(String string2, Object ... arrobject) {
        Log.w((String)this.zza, (String)this.format(string2, arrobject));
    }

    public void wtf(String string2, Throwable throwable, Object ... arrobject) {
        Log.wtf((String)this.zza, (String)this.format(string2, arrobject), (Throwable)throwable);
    }

    public void wtf(Throwable throwable) {
        Log.wtf((String)this.zza, (Throwable)throwable);
    }
}

