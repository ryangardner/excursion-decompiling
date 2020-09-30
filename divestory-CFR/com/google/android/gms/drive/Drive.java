/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.zze;
import com.google.android.gms.drive.zzf;
import com.google.android.gms.drive.zzg;
import com.google.android.gms.drive.zzj;
import com.google.android.gms.drive.zzl;
import com.google.android.gms.internal.drive.zzaf;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbb;
import com.google.android.gms.internal.drive.zzbr;
import com.google.android.gms.internal.drive.zzcb;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeb;
import java.util.Set;

@Deprecated
public final class Drive {
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final Api.ClientKey<zzaw> CLIENT_KEY;
    @Deprecated
    public static final DriveApi DriveApi;
    @Deprecated
    public static final DrivePreferencesApi DrivePreferencesApi;
    public static final Scope SCOPE_APPFOLDER;
    public static final Scope SCOPE_FILE;
    private static final Api.AbstractClientBuilder<zzaw, Api.ApiOptions.NoOptions> zzq;
    private static final Api.AbstractClientBuilder<zzaw, zzb> zzr;
    private static final Api.AbstractClientBuilder<zzaw, zza> zzs;
    private static final Scope zzt;
    private static final Scope zzu;
    private static final Api<zzb> zzv;
    public static final Api<zza> zzw;
    @Deprecated
    private static final zzj zzx;
    private static final zzl zzy;

    static {
        CLIENT_KEY = new Api.ClientKey();
        zzq = new zze();
        zzr = new zzf();
        zzs = new zzg();
        SCOPE_FILE = new Scope("https://www.googleapis.com/auth/drive.file");
        SCOPE_APPFOLDER = new Scope("https://www.googleapis.com/auth/drive.appdata");
        zzt = new Scope("https://www.googleapis.com/auth/drive");
        zzu = new Scope("https://www.googleapis.com/auth/drive.apps");
        API = new Api<Api.ApiOptions.NoOptions>("Drive.API", zzq, CLIENT_KEY);
        zzv = new Api<zzb>("Drive.INTERNAL_API", zzr, CLIENT_KEY);
        zzw = new Api<zza>("Drive.API_CONNECTIONLESS", zzs, CLIENT_KEY);
        DriveApi = new zzaf();
        zzx = new zzbr();
        zzy = new zzeb();
        DrivePreferencesApi = new zzcb();
    }

    private Drive() {
    }

    @Deprecated
    public static DriveClient getDriveClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Drive.zza(googleSignInAccount);
        return new zzbb(activity, new zza(googleSignInAccount));
    }

    @Deprecated
    public static DriveClient getDriveClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Drive.zza(googleSignInAccount);
        return new zzbb(context, new zza(googleSignInAccount));
    }

    @Deprecated
    public static DriveResourceClient getDriveResourceClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Drive.zza(googleSignInAccount);
        return new zzch(activity, new zza(googleSignInAccount));
    }

    @Deprecated
    public static DriveResourceClient getDriveResourceClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Drive.zza(googleSignInAccount);
        return new zzch(context, new zza(googleSignInAccount));
    }

    private static void zza(GoogleSignInAccount object) {
        Preconditions.checkNotNull(object);
        object = ((GoogleSignInAccount)object).getRequestedScopes();
        boolean bl = object.contains(SCOPE_FILE) || object.contains(SCOPE_APPFOLDER) || object.contains(zzt) || object.contains(zzu);
        Preconditions.checkArgument(bl, "You must request a Drive scope in order to interact with the Drive API.");
    }

    public static final class zza
    implements Api.ApiOptions.HasGoogleSignInAccountOptions {
        private final GoogleSignInAccount zzaa;
        private final Bundle zzz = new Bundle();

        public zza(GoogleSignInAccount googleSignInAccount) {
            this.zzaa = googleSignInAccount;
        }

        public final boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object == null) return false;
            if (object.getClass() != this.getClass()) {
                return false;
            }
            if (!Objects.equal(this.zzaa, ((zza)(object = (zza)object)).getGoogleSignInAccount())) {
                return false;
            }
            String string2 = this.zzz.getString("method_trace_filename");
            String string3 = ((zza)object).zzz.getString("method_trace_filename");
            if (string2 != null || string3 != null) {
                if (string2 == null) return false;
                if (string3 == null) return false;
                if (!string2.equals(string3)) return false;
            }
            if (this.zzz.getBoolean("bypass_initial_sync") != ((zza)object).zzz.getBoolean("bypass_initial_sync")) return false;
            if (this.zzz.getInt("proxy_type") != ((zza)object).zzz.getInt("proxy_type")) return false;
            return true;
        }

        @Override
        public final GoogleSignInAccount getGoogleSignInAccount() {
            return this.zzaa;
        }

        public final int hashCode() {
            String string2 = this.zzz.getString("method_trace_filename", "");
            int n = this.zzz.getInt("proxy_type");
            boolean bl = this.zzz.getBoolean("bypass_initial_sync");
            return Objects.hashCode(this.zzaa, string2, n, bl);
        }

        public final Bundle zzh() {
            return this.zzz;
        }
    }

    public static final class zzb
    implements Api.ApiOptions.Optional {
    }

}

