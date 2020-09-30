/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.zzq;
import java.util.List;

public final class OpenFileActivityOptions {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    public final String zzba;
    public final String[] zzbb;
    public final DriveId zzbd;
    public final FilterHolder zzbe;

    private OpenFileActivityOptions(String object, String[] arrstring, Filter filter, DriveId driveId) {
        this.zzba = object;
        this.zzbb = arrstring;
        object = filter == null ? null : new FilterHolder(filter);
        this.zzbe = object;
        this.zzbd = driveId;
    }

    /* synthetic */ OpenFileActivityOptions(String string2, String[] arrstring, Filter filter, DriveId driveId, zzq zzq2) {
        this(string2, arrstring, filter, driveId);
    }

    public static class Builder {
        private final OpenFileActivityBuilder zzbf = new OpenFileActivityBuilder();

        public OpenFileActivityOptions build() {
            this.zzbf.zzg();
            return new OpenFileActivityOptions(this.zzbf.getTitle(), this.zzbf.zzs(), this.zzbf.zzt(), this.zzbf.zzu(), null);
        }

        public Builder setActivityStartFolder(DriveId driveId) {
            this.zzbf.setActivityStartFolder(driveId);
            return this;
        }

        public Builder setActivityTitle(String string2) {
            this.zzbf.setActivityTitle(string2);
            return this;
        }

        public Builder setMimeType(List<String> list) {
            this.zzbf.setMimeType(list.toArray(new String[0]));
            return this;
        }

        public Builder setSelectionFilter(Filter filter) {
            this.zzbf.setSelectionFilter(filter);
            return this;
        }
    }

}

