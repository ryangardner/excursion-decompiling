/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.google.android.gms.drive;

import android.graphics.Bitmap;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzhv;
import com.google.android.gms.internal.drive.zzhw;
import com.google.android.gms.internal.drive.zzhx;
import com.google.android.gms.internal.drive.zzia;
import com.google.android.gms.internal.drive.zzib;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzih;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public final class MetadataChangeSet {
    public static final int CUSTOM_PROPERTY_SIZE_LIMIT_BYTES = 124;
    public static final int INDEXABLE_TEXT_SIZE_LIMIT_BYTES = 131072;
    public static final int MAX_PRIVATE_PROPERTIES_PER_RESOURCE_PER_APP = 30;
    public static final int MAX_PUBLIC_PROPERTIES_PER_RESOURCE = 30;
    public static final int MAX_TOTAL_PROPERTIES_PER_RESOURCE = 100;
    public static final MetadataChangeSet zzax = new MetadataChangeSet(MetadataBundle.zzbe());
    private final MetadataBundle zzay;

    public MetadataChangeSet(MetadataBundle metadataBundle) {
        this.zzay = metadataBundle.zzbf();
    }

    public final Map<CustomPropertyKey, String> getCustomPropertyChangeMap() {
        AppVisibleCustomProperties appVisibleCustomProperties = this.zzay.zza(zzhs.zzjn);
        if (appVisibleCustomProperties != null) return appVisibleCustomProperties.zzba();
        return Collections.emptyMap();
    }

    public final String getDescription() {
        return this.zzay.zza(zzhs.zzjo);
    }

    public final String getIndexableText() {
        return this.zzay.zza(zzhs.zzju);
    }

    public final Date getLastViewedByMeDate() {
        return this.zzay.zza(zzif.zzle);
    }

    public final String getMimeType() {
        return this.zzay.zza(zzhs.zzki);
    }

    public final Bitmap getThumbnail() {
        BitmapTeleporter bitmapTeleporter = this.zzay.zza(zzhs.zzkq);
        if (bitmapTeleporter != null) return bitmapTeleporter.get();
        return null;
    }

    public final String getTitle() {
        return this.zzay.zza(zzhs.zzkr);
    }

    public final Boolean isPinned() {
        return this.zzay.zza(zzhs.zzka);
    }

    public final Boolean isStarred() {
        return this.zzay.zza(zzhs.zzkp);
    }

    public final Boolean isViewed() {
        return this.zzay.zza(zzhs.zzkh);
    }

    public final MetadataBundle zzq() {
        return this.zzay;
    }

    public static class Builder {
        private final MetadataBundle zzay = MetadataBundle.zzbe();
        private AppVisibleCustomProperties.zza zzaz;

        private static void zza(String string2, int n, int n2) {
            boolean bl = n2 <= n;
            Preconditions.checkArgument(bl, String.format(Locale.US, "%s must be no more than %d bytes, but is %d bytes.", string2, n, n2));
        }

        private static int zzb(String string2) {
            if (string2 != null) return string2.getBytes().length;
            return 0;
        }

        private final AppVisibleCustomProperties.zza zzr() {
            if (this.zzaz != null) return this.zzaz;
            this.zzaz = new AppVisibleCustomProperties.zza();
            return this.zzaz;
        }

        public MetadataChangeSet build() {
            if (this.zzaz == null) return new MetadataChangeSet(this.zzay);
            this.zzay.zzb(zzhs.zzjn, this.zzaz.zzbb());
            return new MetadataChangeSet(this.zzay);
        }

        public Builder deleteCustomProperty(CustomPropertyKey customPropertyKey) {
            Preconditions.checkNotNull(customPropertyKey, "key");
            this.zzr().zza(customPropertyKey, null);
            return this;
        }

        public Builder setCustomProperty(CustomPropertyKey customPropertyKey, String string2) {
            Preconditions.checkNotNull(customPropertyKey, "key");
            Preconditions.checkNotNull(string2, "value");
            Builder.zza("The total size of key string and value string of a custom property", 124, Builder.zzb(customPropertyKey.getKey()) + Builder.zzb(string2));
            this.zzr().zza(customPropertyKey, string2);
            return this;
        }

        public Builder setDescription(String string2) {
            this.zzay.zzb(zzhs.zzjo, string2);
            return this;
        }

        public Builder setIndexableText(String string2) {
            Builder.zza("Indexable text size", 131072, Builder.zzb(string2));
            this.zzay.zzb(zzhs.zzju, string2);
            return this;
        }

        public Builder setLastViewedByMeDate(Date date) {
            this.zzay.zzb(zzif.zzle, date);
            return this;
        }

        public Builder setMimeType(String string2) {
            Preconditions.checkNotNull(string2);
            this.zzay.zzb(zzhs.zzki, string2);
            return this;
        }

        public Builder setPinned(boolean bl) {
            this.zzay.zzb(zzhs.zzka, bl);
            return this;
        }

        public Builder setStarred(boolean bl) {
            this.zzay.zzb(zzhs.zzkp, bl);
            return this;
        }

        public Builder setTitle(String string2) {
            Preconditions.checkNotNull(string2, "Title cannot be null.");
            this.zzay.zzb(zzhs.zzkr, string2);
            return this;
        }

        public Builder setViewed() {
            this.zzay.zzb(zzhs.zzkh, true);
            return this;
        }

        @Deprecated
        public Builder setViewed(boolean bl) {
            if (bl) {
                this.zzay.zzb(zzhs.zzkh, true);
                return this;
            }
            if (!this.zzay.zzd(zzhs.zzkh)) return this;
            this.zzay.zzc(zzhs.zzkh);
            return this;
        }
    }

}

