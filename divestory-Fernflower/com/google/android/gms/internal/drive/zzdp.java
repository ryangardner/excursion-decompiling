package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeListener;
import java.util.ArrayList;
import java.util.Set;

public class zzdp implements DriveResource {
   protected final DriveId zzk;

   public zzdp(DriveId var1) {
      this.zzk = var1;
   }

   public PendingResult<Status> addChangeListener(GoogleApiClient var1, ChangeListener var2) {
      return ((zzaw)var1.getClient(Drive.CLIENT_KEY)).zza(var1, this.zzk, var2);
   }

   public PendingResult<Status> addChangeSubscription(GoogleApiClient var1) {
      zzaw var2 = (zzaw)var1.getClient(Drive.CLIENT_KEY);
      zzj var3 = new zzj(1, this.zzk);
      Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(var3.zzda, var3.zzk));
      Preconditions.checkState(var2.isConnected(), "Client must be connected");
      if (var2.zzec) {
         return var1.execute(new zzaz(var2, var1, var3));
      } else {
         throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to add event subscriptions");
      }
   }

   public PendingResult<Status> delete(GoogleApiClient var1) {
      return var1.execute(new zzdu(this, var1));
   }

   public DriveId getDriveId() {
      return this.zzk;
   }

   public PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient var1) {
      return var1.enqueue(new zzdq(this, var1, false));
   }

   public PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient var1) {
      return var1.enqueue(new zzdr(this, var1));
   }

   public PendingResult<Status> removeChangeListener(GoogleApiClient var1, ChangeListener var2) {
      return ((zzaw)var1.getClient(Drive.CLIENT_KEY)).zzb(var1, this.zzk, var2);
   }

   public PendingResult<Status> removeChangeSubscription(GoogleApiClient var1) {
      zzaw var2 = (zzaw)var1.getClient(Drive.CLIENT_KEY);
      DriveId var3 = this.zzk;
      Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, var3));
      Preconditions.checkState(var2.isConnected(), "Client must be connected");
      return var1.execute(new zzba(var2, var1, var3, 1));
   }

   public PendingResult<Status> setParents(GoogleApiClient var1, Set<DriveId> var2) {
      if (var2 != null) {
         return var1.execute(new zzds(this, var1, new ArrayList(var2)));
      } else {
         throw new IllegalArgumentException("ParentIds must be provided.");
      }
   }

   public PendingResult<Status> trash(GoogleApiClient var1) {
      return var1.execute(new zzdv(this, var1));
   }

   public PendingResult<Status> untrash(GoogleApiClient var1) {
      return var1.execute(new zzdw(this, var1));
   }

   public PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient var1, MetadataChangeSet var2) {
      if (var2 != null) {
         return var1.execute(new zzdt(this, var1, var2));
      } else {
         throw new IllegalArgumentException("ChangeSet must be provided.");
      }
   }
}
