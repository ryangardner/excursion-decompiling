/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class ParcelableSparseArray
extends SparseArray<Parcelable>
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseArray> CREATOR = new Parcelable.ClassLoaderCreator<ParcelableSparseArray>(){

        public ParcelableSparseArray createFromParcel(Parcel parcel) {
            return new ParcelableSparseArray(parcel, null);
        }

        public ParcelableSparseArray createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new ParcelableSparseArray(parcel, classLoader);
        }

        public ParcelableSparseArray[] newArray(int n) {
            return new ParcelableSparseArray[n];
        }
    };

    public ParcelableSparseArray() {
    }

    public ParcelableSparseArray(Parcel arrparcelable, ClassLoader classLoader) {
        int n = arrparcelable.readInt();
        int[] arrn = new int[n];
        arrparcelable.readIntArray(arrn);
        arrparcelable = arrparcelable.readParcelableArray(classLoader);
        int n2 = 0;
        while (n2 < n) {
            this.put(arrn[n2], (Object)arrparcelable[n2]);
            ++n2;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = this.size();
        int[] arrn = new int[n2];
        Parcelable[] arrparcelable = new Parcelable[n2];
        int n3 = 0;
        do {
            if (n3 >= n2) {
                parcel.writeInt(n2);
                parcel.writeIntArray(arrn);
                parcel.writeParcelableArray(arrparcelable, n);
                return;
            }
            arrn[n3] = this.keyAt(n3);
            arrparcelable[n3] = (Parcelable)this.valueAt(n3);
            ++n3;
        } while (true);
    }

}

