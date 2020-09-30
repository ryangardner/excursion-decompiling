/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.TextUtils
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.common.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.internal.zzt;

@Deprecated
public final class GoogleServices {
    private static final Object zza = new Object();
    private static GoogleServices zzb;
    private final String zzc;
    private final Status zzd;
    private final boolean zze;
    private final boolean zzf;

    GoogleServices(Context context) {
        Object object = context.getResources();
        int n = object.getIdentifier("google_app_measurement_enable", "integer", object.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean bl = false;
        boolean bl2 = true;
        if (n != 0) {
            if (object.getInteger(n) != 0) {
                bl = true;
            }
            this.zzf = bl ^ true;
        } else {
            this.zzf = false;
            bl = bl2;
        }
        this.zze = bl;
        String string2 = zzt.zza(context);
        object = string2;
        if (string2 == null) {
            object = new StringResourceValueReader(context).getString("google_app_id");
        }
        if (TextUtils.isEmpty((CharSequence)object)) {
            this.zzd = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzc = null;
            return;
        }
        this.zzc = object;
        this.zzd = Status.RESULT_SUCCESS;
    }

    GoogleServices(String string2, boolean bl) {
        this.zzc = string2;
        this.zzd = Status.RESULT_SUCCESS;
        this.zze = bl;
        this.zzf = bl ^ true;
    }

    private static GoogleServices checkInitialized(String object) {
        Object object2 = zza;
        synchronized (object2) {
            if (zzb != null) {
                return zzb;
            }
            int n = String.valueOf(object).length();
            StringBuilder stringBuilder = new StringBuilder(n + 34);
            stringBuilder.append("Initialize must be called before ");
            stringBuilder.append((String)object);
            stringBuilder.append(".");
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    static void clearInstanceForTest() {
        Object object = zza;
        synchronized (object) {
            zzb = null;
            return;
        }
    }

    public static String getGoogleAppId() {
        return GoogleServices.checkInitialized((String)"getGoogleAppId").zzc;
    }

    public static Status initialize(Context object) {
        Preconditions.checkNotNull(object, "Context must not be null.");
        Object object2 = zza;
        synchronized (object2) {
            GoogleServices googleServices;
            if (zzb != null) return GoogleServices.zzb.zzd;
            zzb = googleServices = new GoogleServices((Context)object);
            return GoogleServices.zzb.zzd;
        }
    }

    public static Status initialize(Context object, String object2, boolean bl) {
        Preconditions.checkNotNull(object, "Context must not be null.");
        Preconditions.checkNotEmpty((String)object2, "App ID must be nonempty.");
        object = zza;
        synchronized (object) {
            GoogleServices googleServices;
            if (zzb != null) {
                return zzb.checkGoogleAppId((String)object2);
            }
            zzb = googleServices = new GoogleServices((String)object2, bl);
            return googleServices.zzd;
        }
    }

    public static boolean isMeasurementEnabled() {
        GoogleServices googleServices = GoogleServices.checkInitialized("isMeasurementEnabled");
        if (!googleServices.zzd.isSuccess()) return false;
        if (!googleServices.zze) return false;
        return true;
    }

    public static boolean isMeasurementExplicitlyDisabled() {
        return GoogleServices.checkInitialized((String)"isMeasurementExplicitlyDisabled").zzf;
    }

    final Status checkGoogleAppId(String string2) {
        CharSequence charSequence = this.zzc;
        if (charSequence == null) return Status.RESULT_SUCCESS;
        if (((String)charSequence).equals(string2)) return Status.RESULT_SUCCESS;
        string2 = this.zzc;
        charSequence = new StringBuilder(String.valueOf(string2).length() + 97);
        ((StringBuilder)charSequence).append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("'.");
        return new Status(10, ((StringBuilder)charSequence).toString());
    }
}

