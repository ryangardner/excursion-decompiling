/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.metadata.internal.zzl;
import com.google.android.gms.drive.metadata.zzb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public final class zzhz
extends zzl<DriveSpace> {
    public zzhz(int n) {
        super("spaces", Arrays.asList("inDriveSpace", "isAppData", "inGooglePhotosSpace"), Collections.<String>emptySet(), 7000000);
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return ((zzb)this).zzd(dataHolder, n, n2);
    }

    @Override
    protected final Collection<DriveSpace> zzd(DataHolder dataHolder, int n, int n2) {
        ArrayList<DriveSpace> arrayList = new ArrayList<DriveSpace>();
        if (dataHolder.getBoolean("inDriveSpace", n, n2)) {
            arrayList.add(DriveSpace.zzah);
        }
        if (dataHolder.getBoolean("isAppData", n, n2)) {
            arrayList.add(DriveSpace.zzai);
        }
        if (!dataHolder.getBoolean("inGooglePhotosSpace", n, n2)) return arrayList;
        arrayList.add(DriveSpace.zzaj);
        return arrayList;
    }
}

