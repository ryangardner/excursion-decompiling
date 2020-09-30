package com.google.android.gms.internal.drive;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Process;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzaw extends GmsClient<zzeo> {
   private final String zzeb;
   protected final boolean zzec;
   private volatile DriveId zzed;
   private volatile DriveId zzee;
   private volatile boolean zzef = false;
   private final Map<DriveId, Map<ChangeListener, zzee>> zzeg = new HashMap();
   private final Map<com.google.android.gms.drive.events.zzd, zzee> zzeh = new HashMap();
   private final Map<DriveId, Map<com.google.android.gms.drive.events.zzl, zzee>> zzei = new HashMap();
   private final Map<DriveId, Map<com.google.android.gms.drive.events.zzl, zzee>> zzej = new HashMap();
   private final Bundle zzz;

   public zzaw(Context var1, Looper var2, ClientSettings var3, GoogleApiClient.ConnectionCallbacks var4, GoogleApiClient.OnConnectionFailedListener var5, Bundle var6) {
      super(var1, var2, 11, var3, (GoogleApiClient.ConnectionCallbacks)var4, (GoogleApiClient.OnConnectionFailedListener)var5);
      this.zzeb = var3.getRealClientPackageName();
      this.zzz = var6;
      Intent var11 = new Intent("com.google.android.gms.drive.events.HANDLE_EVENT");
      var11.setPackage(var1.getPackageName());
      List var8 = var1.getPackageManager().queryIntentServices(var11, 0);
      int var7 = var8.size();
      if (var7 != 0) {
         String var9;
         StringBuilder var12;
         if (var7 == 1) {
            ServiceInfo var10 = ((ResolveInfo)var8.get(0)).serviceInfo;
            if (var10.exported) {
               this.zzec = true;
            } else {
               var9 = var10.name;
               var12 = new StringBuilder(String.valueOf(var9).length() + 60);
               var12.append("Drive event service ");
               var12.append(var9);
               var12.append(" must be exported in AndroidManifest.xml");
               throw new IllegalStateException(var12.toString());
            }
         } else {
            var9 = var11.getAction();
            var12 = new StringBuilder(String.valueOf(var9).length() + 72);
            var12.append("AndroidManifest.xml can only define one service that handles the ");
            var12.append(var9);
            var12.append(" action");
            throw new IllegalStateException(var12.toString());
         }
      } else {
         this.zzec = false;
      }
   }

   // $FF: synthetic method
   protected final IInterface createServiceInterface(IBinder var1) {
      if (var1 == null) {
         return null;
      } else {
         IInterface var2 = var1.queryLocalInterface("com.google.android.gms.drive.internal.IDriveService");
         return (IInterface)(var2 instanceof zzeo ? (zzeo)var2 : new zzep(var1));
      }
   }

   public final void disconnect() {
      // $FF: Couldn't be decompiled
   }

   protected final Bundle getGetServiceRequestExtraArgs() {
      String var1 = this.getContext().getPackageName();
      Preconditions.checkNotNull(var1);
      Preconditions.checkState(this.getClientSettings().getAllRequestedScopes().isEmpty() ^ true);
      Bundle var2 = new Bundle();
      if (!var1.equals(this.zzeb)) {
         var2.putString("proxy_package_name", this.zzeb);
      }

      var2.putAll(this.zzz);
      return var2;
   }

   public final int getMinApkVersion() {
      return 12451000;
   }

   protected final String getServiceDescriptor() {
      return "com.google.android.gms.drive.internal.IDriveService";
   }

   protected final String getStartServiceAction() {
      return "com.google.android.gms.drive.ApiService.START";
   }

   protected final void onPostInitHandler(int var1, IBinder var2, Bundle var3, int var4) {
      if (var3 != null) {
         var3.setClassLoader(this.getClass().getClassLoader());
         this.zzed = (DriveId)var3.getParcelable("com.google.android.gms.drive.root_id");
         this.zzee = (DriveId)var3.getParcelable("com.google.android.gms.drive.appdata_id");
         this.zzef = true;
      }

      super.onPostInitHandler(var1, var2, var3, var4);
   }

   public final boolean requiresAccount() {
      return true;
   }

   public final boolean requiresSignIn() {
      return !this.getContext().getPackageName().equals(this.zzeb) || !UidVerifier.isGooglePlayServicesUid(this.getContext(), Process.myUid());
   }

   final PendingResult<Status> zza(GoogleApiClient var1, DriveId var2, ChangeListener var3) {
      Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, var2));
      Preconditions.checkNotNull(var3, "listener");
      Preconditions.checkState(this.isConnected(), "Client must be connected");
      Map var4 = this.zzeg;
      synchronized(var4){}

      Throwable var10000;
      boolean var10001;
      label450: {
         Map var5;
         try {
            var5 = (Map)this.zzeg.get(var2);
         } catch (Throwable var62) {
            var10000 = var62;
            var10001 = false;
            break label450;
         }

         Object var6 = var5;
         if (var5 == null) {
            try {
               var6 = new HashMap();
               this.zzeg.put(var2, var6);
            } catch (Throwable var61) {
               var10000 = var61;
               var10001 = false;
               break label450;
            }
         }

         zzee var68;
         try {
            var68 = (zzee)((Map)var6).get(var3);
         } catch (Throwable var60) {
            var10000 = var60;
            var10001 = false;
            break label450;
         }

         zzee var67;
         if (var68 == null) {
            try {
               var68 = new zzee(this.getLooper(), this.getContext(), 1, var3);
               ((Map)var6).put(var3, var68);
            } catch (Throwable var59) {
               var10000 = var59;
               var10001 = false;
               break label450;
            }

            var67 = var68;
         } else {
            var67 = var68;

            try {
               if (var68.zzg(1)) {
                  zzat var66 = new zzat(var1, Status.RESULT_SUCCESS);
                  return var66;
               }
            } catch (Throwable var58) {
               var10000 = var58;
               var10001 = false;
               break label450;
            }
         }

         label429:
         try {
            var67.zzf(1);
            zzj var69 = new zzj(1, var2);
            zzax var65 = new zzax(this, var1, var69, var67);
            BaseImplementation.ApiMethodImpl var64 = var1.execute(var65);
            return var64;
         } catch (Throwable var57) {
            var10000 = var57;
            var10001 = false;
            break label429;
         }
      }

      while(true) {
         Throwable var63 = var10000;

         try {
            throw var63;
         } catch (Throwable var56) {
            var10000 = var56;
            var10001 = false;
            continue;
         }
      }
   }

   public final DriveId zzae() {
      return this.zzed;
   }

   public final DriveId zzaf() {
      return this.zzee;
   }

   public final boolean zzag() {
      return this.zzef;
   }

   public final boolean zzah() {
      return this.zzec;
   }

   final PendingResult<Status> zzb(GoogleApiClient var1, DriveId var2, ChangeListener var3) {
      Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, var2));
      Preconditions.checkState(this.isConnected(), "Client must be connected");
      Preconditions.checkNotNull(var3, "listener");
      Map var4 = this.zzeg;
      synchronized(var4){}

      Throwable var10000;
      boolean var10001;
      label424: {
         Map var5;
         try {
            var5 = (Map)this.zzeg.get(var2);
         } catch (Throwable var61) {
            var10000 = var61;
            var10001 = false;
            break label424;
         }

         zzat var64;
         if (var5 == null) {
            label409:
            try {
               var64 = new zzat(var1, Status.RESULT_SUCCESS);
               return var64;
            } catch (Throwable var56) {
               var10000 = var56;
               var10001 = false;
               break label409;
            }
         } else {
            label420: {
               zzee var66;
               try {
                  var66 = (zzee)var5.remove(var3);
               } catch (Throwable var60) {
                  var10000 = var60;
                  var10001 = false;
                  break label420;
               }

               if (var66 == null) {
                  label411:
                  try {
                     var64 = new zzat(var1, Status.RESULT_SUCCESS);
                     return var64;
                  } catch (Throwable var57) {
                     var10000 = var57;
                     var10001 = false;
                     break label411;
                  }
               } else {
                  label416: {
                     try {
                        if (var5.isEmpty()) {
                           this.zzeg.remove(var2);
                        }
                     } catch (Throwable var59) {
                        var10000 = var59;
                        var10001 = false;
                        break label416;
                     }

                     label413:
                     try {
                        zzgs var67 = new zzgs(var2, 1);
                        zzay var65 = new zzay(this, var1, var67, var66);
                        BaseImplementation.ApiMethodImpl var63 = var1.execute(var65);
                        return var63;
                     } catch (Throwable var58) {
                        var10000 = var58;
                        var10001 = false;
                        break label413;
                     }
                  }
               }
            }
         }
      }

      while(true) {
         Throwable var62 = var10000;

         try {
            throw var62;
         } catch (Throwable var55) {
            var10000 = var55;
            var10001 = false;
            continue;
         }
      }
   }
}
