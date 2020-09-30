/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.metadata.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.zzf;
import com.google.android.gms.drive.metadata.internal.zzj;
import com.google.android.gms.internal.drive.zzhs;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MetadataBundle
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<MetadataBundle> CREATOR;
    private static final GmsLogger zzbz;
    private final Bundle zzjh;

    static {
        zzbz = new GmsLogger("MetadataBundle", "");
        CREATOR = new zzj();
    }

    MetadataBundle(Bundle object) {
        int n;
        object = Preconditions.checkNotNull(object);
        this.zzjh = object;
        object.setClassLoader(this.getClass().getClassLoader());
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Object object2 = this.zzjh.keySet().iterator();
        do {
            boolean bl = object2.hasNext();
            n = 0;
            if (!bl) break;
            object = (String)object2.next();
            if (zzf.zzf((String)object) != null) continue;
            arrayList.add(object);
            zzbz.wfmt("MetadataBundle", "Ignored unknown metadata field in bundle: %s", object);
        } while (true);
        object = arrayList;
        int n2 = ((ArrayList)object).size();
        while (n < n2) {
            object2 = ((ArrayList)object).get(n);
            ++n;
            object2 = (String)object2;
            this.zzjh.remove((String)object2);
        }
    }

    public static <T> MetadataBundle zza(MetadataField<T> metadataField, T t) {
        MetadataBundle metadataBundle = MetadataBundle.zzbe();
        metadataBundle.zzb(metadataField, t);
        return metadataBundle;
    }

    public static MetadataBundle zzbe() {
        return new MetadataBundle(new Bundle());
    }

    public final boolean equals(Object object) {
        String string2;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        object = (MetadataBundle)object;
        Object object2 = this.zzjh.keySet();
        if (!object2.equals(((MetadataBundle)object).zzjh.keySet())) {
            return false;
        }
        object2 = object2.iterator();
        do {
            if (!object2.hasNext()) return true;
        } while (Objects.equal(this.zzjh.get(string2 = (String)object2.next()), ((MetadataBundle)object).zzjh.get(string2)));
        return false;
    }

    public final int hashCode() {
        Iterator iterator2 = this.zzjh.keySet().iterator();
        int n = 1;
        while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            n = n * 31 + this.zzjh.get(string2).hashCode();
        }
        return n;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzjh, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }

    public final <T> T zza(MetadataField<T> metadataField) {
        return metadataField.zza(this.zzjh);
    }

    public final void zza(Context context) {
        BitmapTeleporter bitmapTeleporter = this.zza(zzhs.zzkq);
        if (bitmapTeleporter == null) return;
        bitmapTeleporter.setTempDir(context.getCacheDir());
    }

    public final <T> void zzb(MetadataField<T> object, T t) {
        if (zzf.zzf(object.getName()) != null) {
            object.zza(t, this.zzjh);
            return;
        }
        if (((String)(object = String.valueOf(object.getName()))).length() != 0) {
            object = "Unregistered field: ".concat((String)object);
            throw new IllegalArgumentException((String)object);
        }
        object = new String("Unregistered field: ");
        throw new IllegalArgumentException((String)object);
    }

    public final MetadataBundle zzbf() {
        return new MetadataBundle(new Bundle(this.zzjh));
    }

    public final Set<MetadataField<?>> zzbg() {
        HashSet hashSet = new HashSet();
        Iterator iterator2 = this.zzjh.keySet().iterator();
        while (iterator2.hasNext()) {
            hashSet.add(zzf.zzf((String)iterator2.next()));
        }
        return hashSet;
    }

    public final <T> T zzc(MetadataField<T> metadataField) {
        T t = this.zza(metadataField);
        this.zzjh.remove(metadataField.getName());
        return t;
    }

    public final boolean zzd(MetadataField<?> metadataField) {
        return this.zzjh.containsKey(metadataField.getName());
    }
}

