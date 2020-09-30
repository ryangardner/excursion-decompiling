/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzg;

public abstract class GmsClientSupervisor {
    private static int zza = 4225;
    private static final Object zzb = new Object();
    private static GmsClientSupervisor zzc;

    public static int getDefaultBindFlags() {
        return zza;
    }

    public static GmsClientSupervisor getInstance(Context context) {
        Object object = zzb;
        synchronized (object) {
            if (zzc != null) return zzc;
            zzg zzg2 = new zzg(context.getApplicationContext());
            zzc = zzg2;
            return zzc;
        }
    }

    public boolean bindService(ComponentName componentName, ServiceConnection serviceConnection, String string2) {
        return this.zza(new zza(componentName, GmsClientSupervisor.getDefaultBindFlags()), serviceConnection, string2);
    }

    public boolean bindService(String string2, ServiceConnection serviceConnection, String string3) {
        return this.zza(new zza(string2, GmsClientSupervisor.getDefaultBindFlags()), serviceConnection, string3);
    }

    public void unbindService(ComponentName componentName, ServiceConnection serviceConnection, String string2) {
        this.zzb(new zza(componentName, GmsClientSupervisor.getDefaultBindFlags()), serviceConnection, string2);
    }

    public void unbindService(String string2, ServiceConnection serviceConnection, String string3) {
        this.zzb(new zza(string2, GmsClientSupervisor.getDefaultBindFlags()), serviceConnection, string3);
    }

    public final void zza(String string2, String string3, int n, ServiceConnection serviceConnection, String string4, boolean bl) {
        this.zzb(new zza(string2, string3, n, bl), serviceConnection, string4);
    }

    protected abstract boolean zza(zza var1, ServiceConnection var2, String var3);

    protected abstract void zzb(zza var1, ServiceConnection var2, String var3);

    protected static final class zza {
        private static final Uri zzf = new Uri.Builder().scheme("content").authority("com.google.android.gms.chimera").build();
        private final String zza;
        private final String zzb;
        private final ComponentName zzc;
        private final int zzd;
        private final boolean zze;

        public zza(ComponentName componentName, int n) {
            this.zza = null;
            this.zzb = null;
            this.zzc = Preconditions.checkNotNull(componentName);
            this.zzd = n;
            this.zze = false;
        }

        public zza(String string2, int n) {
            this(string2, "com.google.android.gms", n);
        }

        private zza(String string2, String string3, int n) {
            this(string2, string3, n, false);
        }

        public zza(String string2, String string3, int n, boolean bl) {
            this.zza = Preconditions.checkNotEmpty(string2);
            this.zzb = Preconditions.checkNotEmpty(string3);
            this.zzc = null;
            this.zzd = n;
            this.zze = bl;
        }

        private final Intent zzb(Context object) {
            Object object2 = new Bundle();
            object2.putString("serviceActionBundleKey", this.zza);
            String string2 = null;
            try {
                object = object.getContentResolver().call(zzf, "serviceIntentCall", null, object2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                object2 = String.valueOf(illegalArgumentException);
                object = new StringBuilder(String.valueOf(object2).length() + 34);
                ((StringBuilder)object).append("Dynamic intent resolution failed: ");
                ((StringBuilder)object).append((String)object2);
                Log.w((String)"ConnectionStatusConfig", (String)((StringBuilder)object).toString());
                object = null;
            }
            object = object == null ? string2 : (Intent)object.getParcelable("serviceResponseIntentKey");
            if (object != null) return object;
            string2 = String.valueOf(this.zza);
            string2 = string2.length() != 0 ? "Dynamic lookup for intent failed for action: ".concat(string2) : new String("Dynamic lookup for intent failed for action: ");
            Log.w((String)"ConnectionStatusConfig", (String)string2);
            return object;
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof zza)) {
                return false;
            }
            object = (zza)object;
            if (!Objects.equal(this.zza, ((zza)object).zza)) return false;
            if (!Objects.equal(this.zzb, ((zza)object).zzb)) return false;
            if (!Objects.equal((Object)this.zzc, (Object)((zza)object).zzc)) return false;
            if (this.zzd != ((zza)object).zzd) return false;
            if (this.zze != ((zza)object).zze) return false;
            return true;
        }

        public final int hashCode() {
            return Objects.hashCode(new Object[]{this.zza, this.zzb, this.zzc, this.zzd, this.zze});
        }

        public final String toString() {
            String string2;
            String string3 = string2 = this.zza;
            if (string2 != null) return string3;
            Preconditions.checkNotNull(this.zzc);
            return this.zzc.flattenToString();
        }

        public final Intent zza(Context context) {
            if (this.zza == null) {
                return new Intent().setComponent(this.zzc);
            }
            Intent intent = this.zze ? this.zzb(context) : null;
            context = intent;
            if (intent != null) return context;
            return new Intent(this.zza).setPackage(this.zzb);
        }

        public final String zza() {
            return this.zzb;
        }

        public final ComponentName zzb() {
            return this.zzc;
        }

        public final int zzc() {
            return this.zzd;
        }
    }

}

