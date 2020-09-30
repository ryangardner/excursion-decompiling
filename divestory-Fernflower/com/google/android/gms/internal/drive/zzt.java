package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

@Deprecated
public final class zzt {
   private String zzba;
   private DriveId zzbd;
   private Integer zzdk;
   private final int zzdl = 0;
   private MetadataChangeSet zzdm;

   public zzt(int var1) {
   }

   public final IntentSender build(GoogleApiClient var1) {
      Preconditions.checkState(var1.isConnected(), "Client must be connected");
      this.zzg();
      zzaw var4 = (zzaw)var1.getClient(Drive.CLIENT_KEY);
      this.zzdm.zzq().zza(var4.getContext());

      try {
         zzeo var5 = (zzeo)var4.getService();
         zzu var2 = new zzu(this.zzdm.zzq(), this.zzdk, this.zzba, this.zzbd, 0);
         IntentSender var6 = var5.zza(var2);
         return var6;
      } catch (RemoteException var3) {
         throw new RuntimeException("Unable to connect Drive Play Service", var3);
      }
   }

   public final int getRequestId() {
      return this.zzdk;
   }

   public final void zza(DriveId var1) {
      this.zzbd = (DriveId)Preconditions.checkNotNull(var1);
   }

   public final void zza(MetadataChangeSet var1) {
      this.zzdm = (MetadataChangeSet)Preconditions.checkNotNull(var1);
   }

   public final MetadataChangeSet zzc() {
      return this.zzdm;
   }

   public final void zzc(String var1) {
      this.zzba = (String)Preconditions.checkNotNull(var1);
   }

   public final DriveId zzd() {
      return this.zzbd;
   }

   public final void zzd(int var1) {
      this.zzdk = var1;
   }

   public final String zze() {
      return this.zzba;
   }

   public final void zzg() {
      Preconditions.checkNotNull(this.zzdm, "Must provide initial metadata via setInitialMetadata.");
      Integer var1 = this.zzdk;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1;
      }

      this.zzdk = var2;
   }
}
