/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.UserMetadata;
import com.google.android.gms.drive.metadata.internal.zzm;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public final class zzu
extends zzm<UserMetadata> {
    public zzu(String string2, int n) {
        super(string2, Arrays.asList(zzu.zza(string2, "permissionId"), zzu.zza(string2, "displayName"), zzu.zza(string2, "picture"), zzu.zza(string2, "isAuthenticatedUser"), zzu.zza(string2, "emailAddress")), Collections.<String>emptyList(), 6000000);
    }

    private static String zza(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(string3).length());
        stringBuilder.append(string2);
        stringBuilder.append(".");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    private final String zzh(String string2) {
        return zzu.zza(this.getName(), string2);
    }

    @Override
    protected final boolean zzb(DataHolder dataHolder, int n, int n2) {
        if (!dataHolder.hasColumn(this.zzh("permissionId"))) return false;
        if (dataHolder.hasNull(this.zzh("permissionId"), n, n2)) return false;
        return true;
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder object, int n, int n2) {
        String string2 = ((DataHolder)object).getString(this.zzh("permissionId"), n, n2);
        if (string2 == null) return null;
        String string3 = ((DataHolder)object).getString(this.zzh("displayName"), n, n2);
        String string4 = ((DataHolder)object).getString(this.zzh("picture"), n, n2);
        boolean bl = ((DataHolder)object).getBoolean(this.zzh("isAuthenticatedUser"), n, n2);
        object = ((DataHolder)object).getString(this.zzh("emailAddress"), n, n2);
        return new UserMetadata(string2, string3, string4, bl, (String)object);
    }
}

