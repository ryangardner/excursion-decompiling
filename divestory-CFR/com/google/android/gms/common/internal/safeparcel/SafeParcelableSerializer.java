/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Base64Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public final class SafeParcelableSerializer {
    public static <T extends SafeParcelable> T deserializeFromBytes(byte[] object, Parcelable.Creator<T> creator) {
        Preconditions.checkNotNull(creator);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (SafeParcelable)creator.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }

    public static <T extends SafeParcelable> T deserializeFromIntentExtra(Intent arrby, String string2, Parcelable.Creator<T> creator) {
        if ((arrby = arrby.getByteArrayExtra(string2)) != null) return SafeParcelableSerializer.deserializeFromBytes(arrby, creator);
        return null;
    }

    public static <T extends SafeParcelable> T deserializeFromString(String string2, Parcelable.Creator<T> creator) {
        return SafeParcelableSerializer.deserializeFromBytes(Base64Utils.decodeUrlSafe(string2), creator);
    }

    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromBundle(Bundle object, String object2, Parcelable.Creator<T> creator) {
        if ((object2 = (ArrayList)object.getSerializable((String)object2)) == null) {
            return null;
        }
        object = new ArrayList(((ArrayList)object2).size());
        object2 = (ArrayList)object2;
        int n = ((ArrayList)object2).size();
        int n2 = 0;
        while (n2 < n) {
            Object e = ((ArrayList)object2).get(n2);
            ++n2;
            ((ArrayList)object).add(SafeParcelableSerializer.deserializeFromBytes((byte[])e, creator));
        }
        return object;
    }

    public static <T extends SafeParcelable> ArrayList<T> deserializeIterableFromIntentExtra(Intent object, String object2, Parcelable.Creator<T> creator) {
        if ((object2 = (ArrayList)object.getSerializableExtra((String)object2)) == null) {
            return null;
        }
        object = new ArrayList(((ArrayList)object2).size());
        ArrayList arrayList = (ArrayList)object2;
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            object2 = arrayList.get(n2);
            ++n2;
            ((ArrayList)object).add(SafeParcelableSerializer.deserializeFromBytes((byte[])object2, creator));
        }
        return object;
    }

    public static <T extends SafeParcelable> void serializeIterableToBundle(Iterable<T> object, Bundle bundle, String string2) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                bundle.putSerializable(string2, arrayList);
                return;
            }
            arrayList.add(SafeParcelableSerializer.serializeToBytes((SafeParcelable)object.next()));
        } while (true);
    }

    public static <T extends SafeParcelable> void serializeIterableToIntentExtra(Iterable<T> object, Intent intent, String string2) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                intent.putExtra(string2, arrayList);
                return;
            }
            arrayList.add(SafeParcelableSerializer.serializeToBytes((SafeParcelable)object.next()));
        } while (true);
    }

    public static <T extends SafeParcelable> byte[] serializeToBytes(T object) {
        Parcel parcel = Parcel.obtain();
        object.writeToParcel(parcel, 0);
        object = parcel.marshall();
        parcel.recycle();
        return object;
    }

    public static <T extends SafeParcelable> void serializeToIntentExtra(T t, Intent intent, String string2) {
        intent.putExtra(string2, SafeParcelableSerializer.serializeToBytes(t));
    }

    public static <T extends SafeParcelable> String serializeToString(T t) {
        return Base64Utils.encodeUrlSafe(SafeParcelableSerializer.serializeToBytes(t));
    }
}

