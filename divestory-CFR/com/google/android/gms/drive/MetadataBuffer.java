/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive;

import android.os.Bundle;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.metadata.internal.zzf;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzhs;
import java.util.Iterator;

public final class MetadataBuffer
extends AbstractDataBuffer<Metadata> {
    private zza zzau;

    public MetadataBuffer(DataHolder dataHolder) {
        super(dataHolder);
        dataHolder.getMetadata().setClassLoader(MetadataBuffer.class.getClassLoader());
    }

    @Override
    public final Metadata get(int n) {
        zza zza2;
        zza zza3 = this.zzau;
        if (zza3 != null) {
            zza2 = zza3;
            if (zza3.row == n) return zza2;
        }
        this.zzau = zza2 = new zza(this.mDataHolder, n);
        return zza2;
    }

    @Deprecated
    public final String getNextPageToken() {
        return null;
    }

    @Override
    public final void release() {
        if (this.mDataHolder != null) {
            zzf.zza(this.mDataHolder);
        }
        super.release();
    }

    static final class zza
    extends Metadata {
        private final int row;
        private final DataHolder zzav;
        private final int zzaw;

        public zza(DataHolder dataHolder, int n) {
            this.zzav = dataHolder;
            this.row = n;
            this.zzaw = dataHolder.getWindowIndex(n);
        }

        @Override
        public final /* synthetic */ Object freeze() {
            MetadataBundle metadataBundle = MetadataBundle.zzbe();
            Iterator<MetadataField<?>> iterator2 = zzf.zzbc().iterator();
            while (iterator2.hasNext()) {
                MetadataField<?> metadataField = iterator2.next();
                if (metadataField == zzhs.zzkq) continue;
                metadataField.zza(this.zzav, metadataBundle, this.row, this.zzaw);
            }
            return new zzaa(metadataBundle);
        }

        @Override
        public final boolean isDataValid() {
            if (this.zzav.isClosed()) return false;
            return true;
        }

        @Override
        public final <T> T zza(MetadataField<T> metadataField) {
            return metadataField.zza(this.zzav, this.row, this.zzaw);
        }
    }

}

