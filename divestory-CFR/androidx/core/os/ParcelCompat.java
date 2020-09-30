/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package androidx.core.os;

import android.os.Parcel;

public final class ParcelCompat {
    private ParcelCompat() {
    }

    public static boolean readBoolean(Parcel parcel) {
        if (parcel.readInt() == 0) return false;
        return true;
    }

    public static void writeBoolean(Parcel parcel, boolean bl) {
        parcel.writeInt((int)bl);
    }
}

