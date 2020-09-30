package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
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
   public static final Api.ClientKey<zzaw> CLIENT_KEY = new Api.ClientKey();
   @Deprecated
   public static final DriveApi DriveApi;
   @Deprecated
   public static final DrivePreferencesApi DrivePreferencesApi;
   public static final Scope SCOPE_APPFOLDER = new Scope("https://www.googleapis.com/auth/drive.appdata");
   public static final Scope SCOPE_FILE = new Scope("https://www.googleapis.com/auth/drive.file");
   private static final Api.AbstractClientBuilder<zzaw, Api.ApiOptions.NoOptions> zzq = new zze();
   private static final Api.AbstractClientBuilder<zzaw, Drive.zzb> zzr = new zzf();
   private static final Api.AbstractClientBuilder<zzaw, Drive.zza> zzs = new zzg();
   private static final Scope zzt = new Scope("https://www.googleapis.com/auth/drive");
   private static final Scope zzu = new Scope("https://www.googleapis.com/auth/drive.apps");
   private static final Api<Drive.zzb> zzv;
   public static final Api<Drive.zza> zzw;
   @Deprecated
   private static final zzj zzx;
   private static final zzl zzy;

   static {
      API = new Api("Drive.API", zzq, CLIENT_KEY);
      zzv = new Api("Drive.INTERNAL_API", zzr, CLIENT_KEY);
      zzw = new Api("Drive.API_CONNECTIONLESS", zzs, CLIENT_KEY);
      DriveApi = new zzaf();
      zzx = new zzbr();
      zzy = new zzeb();
      DrivePreferencesApi = new zzcb();
   }

   private Drive() {
   }

   @Deprecated
   public static DriveClient getDriveClient(Activity var0, GoogleSignInAccount var1) {
      zza(var1);
      return new zzbb(var0, new Drive.zza(var1));
   }

   @Deprecated
   public static DriveClient getDriveClient(Context var0, GoogleSignInAccount var1) {
      zza(var1);
      return new zzbb(var0, new Drive.zza(var1));
   }

   @Deprecated
   public static DriveResourceClient getDriveResourceClient(Activity var0, GoogleSignInAccount var1) {
      zza(var1);
      return new zzch(var0, new Drive.zza(var1));
   }

   @Deprecated
   public static DriveResourceClient getDriveResourceClient(Context var0, GoogleSignInAccount var1) {
      zza(var1);
      return new zzch(var0, new Drive.zza(var1));
   }

   private static void zza(GoogleSignInAccount var0) {
      Preconditions.checkNotNull(var0);
      Set var2 = var0.getRequestedScopes();
      boolean var1;
      if (!var2.contains(SCOPE_FILE) && !var2.contains(SCOPE_APPFOLDER) && !var2.contains(zzt) && !var2.contains(zzu)) {
         var1 = false;
      } else {
         var1 = true;
      }

      Preconditions.checkArgument(var1, "You must request a Drive scope in order to interact with the Drive API.");
   }

   public static final class zza implements Api.ApiOptions.HasGoogleSignInAccountOptions {
      private final GoogleSignInAccount zzaa;
      private final Bundle zzz = new Bundle();

      public zza(GoogleSignInAccount var1) {
         this.zzaa = var1;
      }

      public final boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else {
            if (var1 != null && var1.getClass() == this.getClass()) {
               Drive.zza var4 = (Drive.zza)var1;
               if (!Objects.equal(this.zzaa, var4.getGoogleSignInAccount())) {
                  return false;
               }

               String var2 = this.zzz.getString("method_trace_filename");
               String var3 = var4.zzz.getString("method_trace_filename");
               if ((var2 == null && var3 == null || var2 != null && var3 != null && var2.equals(var3)) && this.zzz.getBoolean("bypass_initial_sync") == var4.zzz.getBoolean("bypass_initial_sync") && this.zzz.getInt("proxy_type") == var4.zzz.getInt("proxy_type")) {
                  return true;
               }
            }

            return false;
         }
      }

      public final GoogleSignInAccount getGoogleSignInAccount() {
         return this.zzaa;
      }

      public final int hashCode() {
         String var1 = this.zzz.getString("method_trace_filename", "");
         int var2 = this.zzz.getInt("proxy_type");
         boolean var3 = this.zzz.getBoolean("bypass_initial_sync");
         return Objects.hashCode(this.zzaa, var1, var2, var3);
      }

      public final Bundle zzh() {
         return this.zzz;
      }
   }

   public static final class zzb implements Api.ApiOptions.Optional {
   }
}
