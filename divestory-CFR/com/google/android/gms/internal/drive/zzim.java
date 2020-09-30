/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal.drive;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.zzm;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzhx;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class zzim
extends zzm<DriveId> {
    public static final zzim zzlj = new zzim();

    private zzim() {
        super("driveId", Arrays.asList("sqlId", "resourceId", "mimeType"), Arrays.asList("dbInstanceId"), 4100000);
    }

    @Override
    protected final boolean zzb(DataHolder dataHolder, int n, int n2) {
        Iterator<String> iterator2 = this.zzaz().iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (dataHolder.hasColumn(iterator2.next()));
        return false;
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder object, int n, int n2) {
        long l = ((DataHolder)object).getMetadata().getLong("dbInstanceId");
        int n3 = "application/vnd.google-apps.folder".equals(((DataHolder)object).getString(zzhs.zzki.getName(), n, n2));
        String string2 = ((DataHolder)object).getString("resourceId", n, n2);
        long l2 = ((DataHolder)object).getLong("sqlId", n, n2);
        if ("generated-android-null".equals(string2)) {
            object = null;
            return new DriveId((String)object, l2, l, n3);
        }
        object = string2;
        return new DriveId((String)object, l2, l, n3);
    }
}

