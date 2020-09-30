/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveResource;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DriveFile
extends DriveResource {
    public static final int MODE_READ_ONLY = 268435456;
    public static final int MODE_READ_WRITE = 805306368;
    public static final int MODE_WRITE_ONLY = 536870912;

    @Deprecated
    public PendingResult<DriveApi.DriveContentsResult> open(GoogleApiClient var1, int var2, DownloadProgressListener var3);

    @Deprecated
    public static interface DownloadProgressListener {
        public void onProgress(long var1, long var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OpenMode {
    }

}

