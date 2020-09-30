/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzhv;
import com.google.android.gms.internal.drive.zzhw;
import com.google.android.gms.internal.drive.zzhx;
import com.google.android.gms.internal.drive.zzhy;
import com.google.android.gms.internal.drive.zzia;
import com.google.android.gms.internal.drive.zzib;
import com.google.android.gms.internal.drive.zzic;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzig;
import com.google.android.gms.internal.drive.zzih;
import com.google.android.gms.internal.drive.zzii;
import com.google.android.gms.internal.drive.zzij;
import com.google.android.gms.internal.drive.zzil;
import com.google.android.gms.internal.drive.zzin;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public abstract class Metadata
implements Freezable<Metadata> {
    public static final int CONTENT_AVAILABLE_LOCALLY = 1;
    public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

    public String getAlternateLink() {
        return this.zza(zzhs.zzjm);
    }

    public int getContentAvailability() {
        Integer n = this.zza(zzin.zzlk);
        if (n != null) return n;
        return 0;
    }

    public Date getCreatedDate() {
        return this.zza(zzif.zzld);
    }

    public Map<CustomPropertyKey, String> getCustomProperties() {
        AppVisibleCustomProperties appVisibleCustomProperties = this.zza(zzhs.zzjn);
        if (appVisibleCustomProperties != null) return appVisibleCustomProperties.zzba();
        return Collections.emptyMap();
    }

    public String getDescription() {
        return this.zza(zzhs.zzjo);
    }

    public DriveId getDriveId() {
        return this.zza(zzhs.zzjl);
    }

    public String getEmbedLink() {
        return this.zza(zzhs.zzjp);
    }

    public String getFileExtension() {
        return this.zza(zzhs.zzjq);
    }

    public long getFileSize() {
        return this.zza(zzhs.zzjr);
    }

    public Date getLastViewedByMeDate() {
        return this.zza(zzif.zzle);
    }

    public String getMimeType() {
        return this.zza(zzhs.zzki);
    }

    public Date getModifiedByMeDate() {
        return this.zza(zzif.zzlg);
    }

    public Date getModifiedDate() {
        return this.zza(zzif.zzlf);
    }

    public String getOriginalFilename() {
        return this.zza(zzhs.zzkj);
    }

    public long getQuotaBytesUsed() {
        return this.zza(zzhs.zzko);
    }

    public Date getSharedWithMeDate() {
        return this.zza(zzif.zzlh);
    }

    public String getTitle() {
        return this.zza(zzhs.zzkr);
    }

    public String getWebContentLink() {
        return this.zza(zzhs.zzkt);
    }

    public String getWebViewLink() {
        return this.zza(zzhs.zzku);
    }

    public boolean isEditable() {
        Boolean bl = this.zza(zzhs.zzjx);
        if (bl != null) return bl;
        return false;
    }

    public boolean isExplicitlyTrashed() {
        Boolean bl = this.zza(zzhs.zzjy);
        if (bl != null) return bl;
        return false;
    }

    public boolean isFolder() {
        return "application/vnd.google-apps.folder".equals(this.getMimeType());
    }

    public boolean isInAppFolder() {
        Boolean bl = this.zza(zzhs.zzjv);
        if (bl != null) return bl;
        return false;
    }

    public boolean isPinnable() {
        Boolean bl = this.zza(zzin.zzll);
        if (bl != null) return bl;
        return false;
    }

    public boolean isPinned() {
        Boolean bl = this.zza(zzhs.zzka);
        if (bl != null) return bl;
        return false;
    }

    public boolean isRestricted() {
        Boolean bl = this.zza(zzhs.zzkc);
        if (bl != null) return bl;
        return false;
    }

    public boolean isShared() {
        Boolean bl = this.zza(zzhs.zzkd);
        if (bl != null) return bl;
        return false;
    }

    public boolean isStarred() {
        Boolean bl = this.zza(zzhs.zzkp);
        if (bl != null) return bl;
        return false;
    }

    public boolean isTrashable() {
        Boolean bl = this.zza(zzhs.zzkg);
        if (bl == null) return true;
        if (bl == false) return false;
        return true;
    }

    public boolean isTrashed() {
        Boolean bl = this.zza(zzhs.zzks);
        if (bl != null) return bl;
        return false;
    }

    public boolean isViewed() {
        Boolean bl = this.zza(zzhs.zzkh);
        if (bl != null) return bl;
        return false;
    }

    public abstract <T> T zza(MetadataField<T> var1);
}

