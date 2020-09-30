/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.FileUploadPreferences;

@Deprecated
public interface DrivePreferencesApi {
    @Deprecated
    public PendingResult<FileUploadPreferencesResult> getFileUploadPreferences(GoogleApiClient var1);

    @Deprecated
    public PendingResult<Status> setFileUploadPreferences(GoogleApiClient var1, FileUploadPreferences var2);

    @Deprecated
    public static interface FileUploadPreferencesResult
    extends Result {
        public FileUploadPreferences getFileUploadPreferences();
    }

}

