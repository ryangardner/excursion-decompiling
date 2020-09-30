/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 */
package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ListenerToken;
import com.google.android.gms.drive.events.OnChangeListener;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.tasks.Task;
import java.util.Set;

@Deprecated
public abstract class DriveResourceClient
extends GoogleApi<Drive.zza> {
    public DriveResourceClient(Activity activity, Drive.zza zza2) {
        super(activity, Drive.zzw, zza2, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public DriveResourceClient(Context context, Drive.zza zza2) {
        super(context, Drive.zzw, zza2, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @Deprecated
    public abstract Task<ListenerToken> addChangeListener(DriveResource var1, OnChangeListener var2);

    @Deprecated
    public abstract Task<Void> addChangeSubscription(DriveResource var1);

    @Deprecated
    public abstract Task<Boolean> cancelOpenFileCallback(ListenerToken var1);

    @Deprecated
    public abstract Task<Void> commitContents(DriveContents var1, MetadataChangeSet var2);

    @Deprecated
    public abstract Task<Void> commitContents(DriveContents var1, MetadataChangeSet var2, ExecutionOptions var3);

    @Deprecated
    public abstract Task<DriveContents> createContents();

    @Deprecated
    public abstract Task<DriveFile> createFile(DriveFolder var1, MetadataChangeSet var2, DriveContents var3);

    @Deprecated
    public abstract Task<DriveFile> createFile(DriveFolder var1, MetadataChangeSet var2, DriveContents var3, ExecutionOptions var4);

    @Deprecated
    public abstract Task<DriveFolder> createFolder(DriveFolder var1, MetadataChangeSet var2);

    @Deprecated
    public abstract Task<Void> delete(DriveResource var1);

    @Deprecated
    public abstract Task<Void> discardContents(DriveContents var1);

    @Deprecated
    public abstract Task<DriveFolder> getAppFolder();

    @Deprecated
    public abstract Task<Metadata> getMetadata(DriveResource var1);

    @Deprecated
    public abstract Task<DriveFolder> getRootFolder();

    @Deprecated
    public abstract Task<MetadataBuffer> listChildren(DriveFolder var1);

    @Deprecated
    public abstract Task<MetadataBuffer> listParents(DriveResource var1);

    @Deprecated
    public abstract Task<DriveContents> openFile(DriveFile var1, int var2);

    @Deprecated
    public abstract Task<ListenerToken> openFile(DriveFile var1, int var2, OpenFileCallback var3);

    @Deprecated
    public abstract Task<MetadataBuffer> query(Query var1);

    @Deprecated
    public abstract Task<MetadataBuffer> queryChildren(DriveFolder var1, Query var2);

    @Deprecated
    public abstract Task<Boolean> removeChangeListener(ListenerToken var1);

    @Deprecated
    public abstract Task<Void> removeChangeSubscription(DriveResource var1);

    @Deprecated
    public abstract Task<DriveContents> reopenContentsForWrite(DriveContents var1);

    @Deprecated
    public abstract Task<Void> setParents(DriveResource var1, Set<DriveId> var2);

    @Deprecated
    public abstract Task<Void> trash(DriveResource var1);

    @Deprecated
    public abstract Task<Void> untrash(DriveResource var1);

    @Deprecated
    public abstract Task<Metadata> updateMetadata(DriveResource var1, MetadataChangeSet var2);
}

