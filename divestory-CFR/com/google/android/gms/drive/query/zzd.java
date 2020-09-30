/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.zzb;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.Iterator;
import java.util.List;

public final class zzd
implements zzj<String> {
    @Override
    public final /* synthetic */ Object zza(zzb zzb2, Object object) {
        return String.format("contains(%s,%s)", zzb2.getName(), object);
    }

    @Override
    public final /* synthetic */ Object zza(zzx zzx2, MetadataField metadataField, Object object) {
        return String.format("cmp(%s,%s,%s)", zzx2.getTag(), metadataField.getName(), object);
    }

    @Override
    public final /* synthetic */ Object zza(zzx object, List object2) {
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(((zzx)object).getTag()).concat("("));
        object2 = object2.iterator();
        object = "";
        do {
            if (!object2.hasNext()) {
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            String string2 = (String)object2.next();
            stringBuilder.append((String)object);
            stringBuilder.append(string2);
            object = ",";
        } while (true);
    }

    @Override
    public final /* synthetic */ Object zza(Object object) {
        return String.format("not(%s)", (String)object);
    }

    @Override
    public final /* synthetic */ Object zzbj() {
        return "ownedByMe()";
    }

    @Override
    public final /* synthetic */ Object zzbk() {
        return "all()";
    }

    @Override
    public final /* synthetic */ Object zzc(MetadataField metadataField, Object object) {
        return String.format("has(%s,%s)", metadataField.getName(), object);
    }

    @Override
    public final /* synthetic */ Object zze(MetadataField metadataField) {
        return String.format("fieldOnly(%s)", metadataField.getName());
    }

    @Override
    public final /* synthetic */ Object zzi(String string2) {
        return String.format("fullTextSearch(%s)", string2);
    }
}

