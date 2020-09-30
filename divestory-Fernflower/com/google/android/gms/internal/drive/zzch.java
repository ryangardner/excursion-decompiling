package com.google.android.gms.internal.drive;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ListenerToken;
import com.google.android.gms.drive.events.OnChangeListener;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzch extends DriveResourceClient {
   private static final AtomicInteger zzfn = new AtomicInteger();

   public zzch(Activity var1, Drive.zza var2) {
      super(var1, var2);
   }

   public zzch(Context var1, Drive.zza var2) {
      super(var1, var2);
   }

   // $FF: synthetic method
   static final ListenerToken zza(ListenerHolder var0, Task var1) throws Exception {
      if (var1.isSuccessful()) {
         return new zzg(var0.getListenerKey());
      } else {
         throw var1.getException();
      }
   }

   // $FF: synthetic method
   static final ListenerToken zza(zzg var0, Task var1) throws Exception {
      if (var1.isSuccessful()) {
         return var0;
      } else {
         throw var1.getException();
      }
   }

   private static void zze(int var0) {
      if (var0 != 268435456 && var0 != 536870912 && var0 != 805306368) {
         throw new IllegalArgumentException("Invalid openMode provided");
      }
   }

   public final Task<ListenerToken> addChangeListener(DriveResource var1, OnChangeListener var2) {
      Preconditions.checkNotNull(var1.getDriveId());
      Preconditions.checkNotNull(var2, "listener");
      zzdi var5 = new zzdi(this, var2, var1.getDriveId());
      int var3 = zzfn.incrementAndGet();
      StringBuilder var4 = new StringBuilder(27);
      var4.append("OnChangeListener");
      var4.append(var3);
      ListenerHolder var6 = this.registerListener(var5, var4.toString());
      return this.doRegisterEventListener(new zzcp(this, var6, var1, var5), new zzcq(this, var6.getListenerKey(), var1, var5)).continueWith(new zzci(var6));
   }

   public final Task<Void> addChangeSubscription(DriveResource var1) {
      Preconditions.checkNotNull(var1.getDriveId());
      Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, var1.getDriveId()));
      return this.doWrite(new zzcr(this, var1));
   }

   public final Task<Boolean> cancelOpenFileCallback(ListenerToken var1) {
      if (var1 instanceof zzg) {
         return this.doUnregisterEventListener(((zzg)var1).zzad());
      } else {
         throw new IllegalArgumentException("Unrecognized ListenerToken");
      }
   }

   public final Task<Void> commitContents(DriveContents var1, MetadataChangeSet var2) {
      return this.commitContents(var1, var2, (com.google.android.gms.drive.zzn)(new com.google.android.gms.drive.zzp()).build());
   }

   public final Task<Void> commitContents(DriveContents var1, MetadataChangeSet var2, ExecutionOptions var3) {
      Preconditions.checkNotNull(var3, "Execution options cannot be null.");
      boolean var4 = var1.zzk();
      boolean var5 = true;
      Preconditions.checkArgument(var4 ^ true, "DriveContents is already closed");
      if (var1.getMode() == 268435456) {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "Cannot commit contents opened in MODE_READ_ONLY.");
      Preconditions.checkNotNull(var1.getDriveId(), "Only DriveContents obtained through DriveFile.open can be committed.");
      com.google.android.gms.drive.zzn var6 = com.google.android.gms.drive.zzn.zza(var3);
      if (ExecutionOptions.zza(var6.zzn()) && !var1.zzi().zzb()) {
         throw new IllegalStateException("DriveContents must be valid for conflict detection.");
      } else {
         MetadataChangeSet var7 = var2;
         if (var2 == null) {
            var7 = MetadataChangeSet.zzax;
         }

         return this.doWrite(new zzcy(this, var6, var1, var7));
      }
   }

   public final Task<DriveContents> createContents() {
      Preconditions.checkArgument(true, "Contents can only be created in MODE_WRITE_ONLY or MODE_READ_WRITE.");
      return this.doWrite(new zzcw(this, 536870912));
   }

   public final Task<DriveFile> createFile(DriveFolder var1, MetadataChangeSet var2, DriveContents var3) {
      return this.createFile(var1, var2, var3, (new ExecutionOptions.Builder()).build());
   }

   public final Task<DriveFile> createFile(DriveFolder var1, MetadataChangeSet var2, DriveContents var3, ExecutionOptions var4) {
      zzbs.zzb(var2);
      return this.doWrite(new zzdh(var1, var2, var3, var4, (String)null));
   }

   public final Task<DriveFolder> createFolder(DriveFolder var1, MetadataChangeSet var2) {
      Preconditions.checkNotNull(var2, "MetadataChangeSet must be provided.");
      if (var2.getMimeType() != null && !var2.getMimeType().equals("application/vnd.google-apps.folder")) {
         throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
      } else {
         return this.doWrite(new zzdb(this, var2, var1));
      }
   }

   public final Task<Void> delete(DriveResource var1) {
      Preconditions.checkNotNull(var1.getDriveId());
      return this.doWrite(new zzcl(this, var1));
   }

   public final Task<Void> discardContents(DriveContents var1) {
      Preconditions.checkArgument(var1.zzk() ^ true, "DriveContents is already closed");
      var1.zzj();
      return this.doWrite(new zzda(this, var1));
   }

   public final Task<DriveFolder> getAppFolder() {
      return this.doRead(new zzco(this));
   }

   public final Task<Metadata> getMetadata(DriveResource var1) {
      Preconditions.checkNotNull(var1, "DriveResource must not be null");
      Preconditions.checkNotNull(var1.getDriveId(), "Resource's DriveId must not be null");
      return this.doRead(new zzdc(this, var1, false));
   }

   public final Task<DriveFolder> getRootFolder() {
      return this.doRead(new zzck(this));
   }

   public final Task<MetadataBuffer> listChildren(DriveFolder var1) {
      Preconditions.checkNotNull(var1, "folder cannot be null.");
      return this.query(zzbs.zza((Query)null, (DriveId)var1.getDriveId()));
   }

   public final Task<MetadataBuffer> listParents(DriveResource var1) {
      Preconditions.checkNotNull(var1.getDriveId());
      return this.doRead(new zzde(this, var1));
   }

   public final Task<DriveContents> openFile(DriveFile var1, int var2) {
      zze(var2);
      return this.doRead(new zzct(this, var1, var2));
   }

   public final Task<ListenerToken> openFile(DriveFile var1, int var2, OpenFileCallback var3) {
      zze(var2);
      int var4 = zzfn.incrementAndGet();
      StringBuilder var5 = new StringBuilder(27);
      var5.append("OpenFileCallback");
      var5.append(var4);
      ListenerHolder var7 = this.registerListener(var3, var5.toString());
      ListenerHolder.ListenerKey var6 = var7.getListenerKey();
      zzg var8 = new zzg(var6);
      return this.doRegisterEventListener(new zzcu(this, var7, var1, var2, var8, var7), new zzcv(this, var6, var8)).continueWith(new zzcj(var8));
   }

   public final Task<MetadataBuffer> query(Query var1) {
      Preconditions.checkNotNull(var1, "query cannot be null.");
      return this.doRead(new zzcz(this, var1));
   }

   public final Task<MetadataBuffer> queryChildren(DriveFolder var1, Query var2) {
      Preconditions.checkNotNull(var1, "folder cannot be null.");
      Preconditions.checkNotNull(var2, "query cannot be null.");
      return this.query(zzbs.zza(var2, var1.getDriveId()));
   }

   public final Task<Boolean> removeChangeListener(ListenerToken var1) {
      Preconditions.checkNotNull(var1, "Token is required to unregister listener.");
      if (var1 instanceof zzg) {
         return this.doUnregisterEventListener(((zzg)var1).zzad());
      } else {
         throw new IllegalStateException("Could not recover key from ListenerToken");
      }
   }

   public final Task<Void> removeChangeSubscription(DriveResource var1) {
      Preconditions.checkNotNull(var1.getDriveId());
      Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, var1.getDriveId()));
      return this.doWrite(new zzcs(this, var1));
   }

   public final Task<DriveContents> reopenContentsForWrite(DriveContents var1) {
      boolean var2 = var1.zzk();
      boolean var3 = true;
      Preconditions.checkArgument(var2 ^ true, "DriveContents is already closed");
      if (var1.getMode() != 268435456) {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "This method can only be called on contents that are currently opened in MODE_READ_ONLY.");
      var1.zzj();
      return this.doRead(new zzcx(this, var1));
   }

   public final Task<Void> setParents(DriveResource var1, Set<DriveId> var2) {
      Preconditions.checkNotNull(var1.getDriveId());
      Preconditions.checkNotNull(var2);
      return this.doWrite(new zzdf(this, var1, new ArrayList(var2)));
   }

   public final Task<Void> trash(DriveResource var1) {
      Preconditions.checkNotNull(var1.getDriveId());
      return this.doWrite(new zzcm(this, var1));
   }

   public final Task<Void> untrash(DriveResource var1) {
      Preconditions.checkNotNull(var1.getDriveId());
      return this.doWrite(new zzcn(this, var1));
   }

   public final Task<Metadata> updateMetadata(DriveResource var1, MetadataChangeSet var2) {
      Preconditions.checkNotNull(var1.getDriveId());
      Preconditions.checkNotNull(var2);
      return this.doWrite(new zzdd(this, var2, var1));
   }
}
