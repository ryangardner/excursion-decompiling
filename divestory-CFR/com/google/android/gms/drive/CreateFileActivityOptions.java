/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.zzd;
import com.google.android.gms.internal.drive.zzq;

public final class CreateFileActivityOptions
extends zzq {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";

    private CreateFileActivityOptions(MetadataBundle metadataBundle, Integer n, String string2, DriveId driveId, int n2) {
        super(metadataBundle, n, string2, driveId, n2);
    }

    /* synthetic */ CreateFileActivityOptions(MetadataBundle metadataBundle, Integer n, String string2, DriveId driveId, int n2, zzd zzd2) {
        this(metadataBundle, n, string2, driveId, n2);
    }

    public static class Builder {
        protected final CreateFileActivityBuilder builder = new CreateFileActivityBuilder();

        public CreateFileActivityOptions build() {
            this.builder.zzg();
            return new CreateFileActivityOptions(this.builder.zzc().zzq(), this.builder.getRequestId(), this.builder.zze(), this.builder.zzd(), this.builder.zzf(), null);
        }

        public Builder setActivityStartFolder(DriveId driveId) {
            this.builder.setActivityStartFolder(driveId);
            return this;
        }

        public Builder setActivityTitle(String string2) {
            this.builder.setActivityTitle(string2);
            return this;
        }

        public Builder setInitialDriveContents(DriveContents driveContents) {
            this.builder.setInitialDriveContents(driveContents);
            return this;
        }

        public Builder setInitialMetadata(MetadataChangeSet metadataChangeSet) {
            this.builder.setInitialMetadata(metadataChangeSet);
            return this;
        }
    }

}

