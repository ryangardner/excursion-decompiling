/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 */
package com.google.android.gms.internal.drive;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Contents;
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
import com.google.android.gms.drive.events.zzj;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.zzn;
import com.google.android.gms.drive.zzp;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzci;
import com.google.android.gms.internal.drive.zzcj;
import com.google.android.gms.internal.drive.zzck;
import com.google.android.gms.internal.drive.zzcl;
import com.google.android.gms.internal.drive.zzcm;
import com.google.android.gms.internal.drive.zzcn;
import com.google.android.gms.internal.drive.zzco;
import com.google.android.gms.internal.drive.zzcp;
import com.google.android.gms.internal.drive.zzcq;
import com.google.android.gms.internal.drive.zzcr;
import com.google.android.gms.internal.drive.zzcs;
import com.google.android.gms.internal.drive.zzct;
import com.google.android.gms.internal.drive.zzcu;
import com.google.android.gms.internal.drive.zzcv;
import com.google.android.gms.internal.drive.zzcw;
import com.google.android.gms.internal.drive.zzcx;
import com.google.android.gms.internal.drive.zzcy;
import com.google.android.gms.internal.drive.zzcz;
import com.google.android.gms.internal.drive.zzda;
import com.google.android.gms.internal.drive.zzdb;
import com.google.android.gms.internal.drive.zzdc;
import com.google.android.gms.internal.drive.zzdd;
import com.google.android.gms.internal.drive.zzde;
import com.google.android.gms.internal.drive.zzdf;
import com.google.android.gms.internal.drive.zzdh;
import com.google.android.gms.internal.drive.zzdi;
import com.google.android.gms.internal.drive.zzg;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzch
extends DriveResourceClient {
    private static final AtomicInteger zzfn = new AtomicInteger();

    public zzch(Activity activity, Drive.zza zza2) {
        super(activity, zza2);
    }

    public zzch(Context context, Drive.zza zza2) {
        super(context, zza2);
    }

    static final /* synthetic */ ListenerToken zza(ListenerHolder listenerHolder, Task task) throws Exception {
        if (!task.isSuccessful()) throw task.getException();
        return new zzg(listenerHolder.getListenerKey());
    }

    static final /* synthetic */ ListenerToken zza(zzg zzg2, Task task) throws Exception {
        if (!task.isSuccessful()) throw task.getException();
        return zzg2;
    }

    private static void zze(int n) {
        if (n == 268435456) return;
        if (n == 536870912) return;
        if (n != 805306368) throw new IllegalArgumentException("Invalid openMode provided");
    }

    @Override
    public final Task<ListenerToken> addChangeListener(DriveResource driveResource, OnChangeListener object) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        Preconditions.checkNotNull(object, "listener");
        object = new zzdi(this, (OnChangeListener)object, driveResource.getDriveId());
        int n = zzfn.incrementAndGet();
        Object object2 = new StringBuilder(27);
        ((StringBuilder)object2).append("OnChangeListener");
        ((StringBuilder)object2).append(n);
        object2 = this.registerListener(object, ((StringBuilder)object2).toString());
        return this.doRegisterEventListener(new zzcp(this, (ListenerHolder)object2, driveResource, (zzdi)object), new zzcq(this, ((ListenerHolder)object2).getListenerKey(), driveResource, (zzdi)object)).continueWith(new zzci((ListenerHolder)object2));
    }

    @Override
    public final Task<Void> addChangeSubscription(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        Preconditions.checkArgument(zzj.zza(1, driveResource.getDriveId()));
        return this.doWrite(new zzcr(this, driveResource));
    }

    @Override
    public final Task<Boolean> cancelOpenFileCallback(ListenerToken listenerToken) {
        if (!(listenerToken instanceof zzg)) throw new IllegalArgumentException("Unrecognized ListenerToken");
        return this.doUnregisterEventListener(((zzg)listenerToken).zzad());
    }

    @Override
    public final Task<Void> commitContents(DriveContents driveContents, MetadataChangeSet metadataChangeSet) {
        return ((DriveResourceClient)this).commitContents(driveContents, metadataChangeSet, (zzn)((ExecutionOptions.Builder)new zzp()).build());
    }

    @Override
    public final Task<Void> commitContents(DriveContents driveContents, MetadataChangeSet metadataChangeSet, ExecutionOptions object) {
        Preconditions.checkNotNull(object, "Execution options cannot be null.");
        boolean bl = driveContents.zzk();
        boolean bl2 = true;
        Preconditions.checkArgument(bl ^ true, "DriveContents is already closed");
        if (driveContents.getMode() == 268435456) {
            bl2 = false;
        }
        Preconditions.checkArgument(bl2, "Cannot commit contents opened in MODE_READ_ONLY.");
        Preconditions.checkNotNull(driveContents.getDriveId(), "Only DriveContents obtained through DriveFile.open can be committed.");
        zzn zzn2 = zzn.zza((ExecutionOptions)object);
        if (ExecutionOptions.zza(zzn2.zzn())) {
            if (!driveContents.zzi().zzb()) throw new IllegalStateException("DriveContents must be valid for conflict detection.");
        }
        object = metadataChangeSet;
        if (metadataChangeSet != null) return this.doWrite(new zzcy(this, zzn2, driveContents, (MetadataChangeSet)object));
        object = MetadataChangeSet.zzax;
        return this.doWrite(new zzcy(this, zzn2, driveContents, (MetadataChangeSet)object));
    }

    @Override
    public final Task<DriveContents> createContents() {
        Preconditions.checkArgument(true, "Contents can only be created in MODE_WRITE_ONLY or MODE_READ_WRITE.");
        return this.doWrite(new zzcw(this, 536870912));
    }

    @Override
    public final Task<DriveFile> createFile(DriveFolder driveFolder, MetadataChangeSet metadataChangeSet, DriveContents driveContents) {
        return ((DriveResourceClient)this).createFile(driveFolder, metadataChangeSet, driveContents, new ExecutionOptions.Builder().build());
    }

    @Override
    public final Task<DriveFile> createFile(DriveFolder driveFolder, MetadataChangeSet metadataChangeSet, DriveContents driveContents, ExecutionOptions executionOptions) {
        zzbs.zzb(metadataChangeSet);
        return this.doWrite(new zzdh(driveFolder, metadataChangeSet, driveContents, executionOptions, null));
    }

    @Override
    public final Task<DriveFolder> createFolder(DriveFolder driveFolder, MetadataChangeSet metadataChangeSet) {
        Preconditions.checkNotNull(metadataChangeSet, "MetadataChangeSet must be provided.");
        if (metadataChangeSet.getMimeType() == null) return this.doWrite(new zzdb(this, metadataChangeSet, driveFolder));
        if (!metadataChangeSet.getMimeType().equals("application/vnd.google-apps.folder")) throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        return this.doWrite(new zzdb(this, metadataChangeSet, driveFolder));
    }

    @Override
    public final Task<Void> delete(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        return this.doWrite(new zzcl(this, driveResource));
    }

    @Override
    public final Task<Void> discardContents(DriveContents driveContents) {
        Preconditions.checkArgument(driveContents.zzk() ^ true, "DriveContents is already closed");
        driveContents.zzj();
        return this.doWrite(new zzda(this, driveContents));
    }

    @Override
    public final Task<DriveFolder> getAppFolder() {
        return this.doRead(new zzco(this));
    }

    @Override
    public final Task<Metadata> getMetadata(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource, "DriveResource must not be null");
        Preconditions.checkNotNull(driveResource.getDriveId(), "Resource's DriveId must not be null");
        return this.doRead(new zzdc(this, driveResource, false));
    }

    @Override
    public final Task<DriveFolder> getRootFolder() {
        return this.doRead(new zzck(this));
    }

    @Override
    public final Task<MetadataBuffer> listChildren(DriveFolder driveFolder) {
        Preconditions.checkNotNull(driveFolder, "folder cannot be null.");
        return ((DriveResourceClient)this).query(zzbs.zza(null, driveFolder.getDriveId()));
    }

    @Override
    public final Task<MetadataBuffer> listParents(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        return this.doRead(new zzde(this, driveResource));
    }

    @Override
    public final Task<DriveContents> openFile(DriveFile driveFile, int n) {
        zzch.zze(n);
        return this.doRead(new zzct(this, driveFile, n));
    }

    @Override
    public final Task<ListenerToken> openFile(DriveFile driveFile, int n, OpenFileCallback object) {
        zzch.zze(n);
        int n2 = zzfn.incrementAndGet();
        Object object2 = new StringBuilder(27);
        ((StringBuilder)object2).append("OpenFileCallback");
        ((StringBuilder)object2).append(n2);
        object2 = this.registerListener(object, ((StringBuilder)object2).toString());
        ListenerHolder.ListenerKey listenerKey = ((ListenerHolder)object2).getListenerKey();
        object = new zzg(listenerKey);
        return this.doRegisterEventListener(new zzcu(this, (ListenerHolder)object2, driveFile, n, (zzg)object, (ListenerHolder)object2), new zzcv(this, listenerKey, (zzg)object)).continueWith(new zzcj((zzg)object));
    }

    @Override
    public final Task<MetadataBuffer> query(Query query) {
        Preconditions.checkNotNull(query, "query cannot be null.");
        return this.doRead(new zzcz(this, query));
    }

    @Override
    public final Task<MetadataBuffer> queryChildren(DriveFolder driveFolder, Query query) {
        Preconditions.checkNotNull(driveFolder, "folder cannot be null.");
        Preconditions.checkNotNull(query, "query cannot be null.");
        return ((DriveResourceClient)this).query(zzbs.zza(query, driveFolder.getDriveId()));
    }

    @Override
    public final Task<Boolean> removeChangeListener(ListenerToken listenerToken) {
        Preconditions.checkNotNull(listenerToken, "Token is required to unregister listener.");
        if (!(listenerToken instanceof zzg)) throw new IllegalStateException("Could not recover key from ListenerToken");
        return this.doUnregisterEventListener(((zzg)listenerToken).zzad());
    }

    @Override
    public final Task<Void> removeChangeSubscription(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        Preconditions.checkArgument(zzj.zza(1, driveResource.getDriveId()));
        return this.doWrite(new zzcs(this, driveResource));
    }

    @Override
    public final Task<DriveContents> reopenContentsForWrite(DriveContents driveContents) {
        boolean bl = driveContents.zzk();
        boolean bl2 = true;
        Preconditions.checkArgument(bl ^ true, "DriveContents is already closed");
        if (driveContents.getMode() != 268435456) {
            bl2 = false;
        }
        Preconditions.checkArgument(bl2, "This method can only be called on contents that are currently opened in MODE_READ_ONLY.");
        driveContents.zzj();
        return this.doRead(new zzcx(this, driveContents));
    }

    @Override
    public final Task<Void> setParents(DriveResource driveResource, Set<DriveId> set) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        Preconditions.checkNotNull(set);
        return this.doWrite(new zzdf(this, driveResource, new ArrayList<DriveId>(set)));
    }

    @Override
    public final Task<Void> trash(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        return this.doWrite(new zzcm(this, driveResource));
    }

    @Override
    public final Task<Void> untrash(DriveResource driveResource) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        return this.doWrite(new zzcn(this, driveResource));
    }

    @Override
    public final Task<Metadata> updateMetadata(DriveResource driveResource, MetadataChangeSet metadataChangeSet) {
        Preconditions.checkNotNull(driveResource.getDriveId());
        Preconditions.checkNotNull(metadataChangeSet);
        return this.doWrite(new zzdd(this, metadataChangeSet, driveResource));
    }
}

