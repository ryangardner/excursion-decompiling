/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseIntArray
 */
package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

public class ParcelableSparseIntArray
extends SparseIntArray
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseIntArray> CREATOR = new Parcelable.Creator<ParcelableSparseIntArray>(){

        public ParcelableSparseIntArray createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            ParcelableSparseIntArray parcelableSparseIntArray = new ParcelableSparseIntArray(n);
            int[] arrn = new int[n];
            int[] arrn2 = new int[n];
            parcel.readIntArray(arrn);
            parcel.readIntArray(arrn2);
            int n2 = 0;
            while (n2 < n) {
                parcelableSparseIntArray.put(arrn[n2], arrn2[n2]);
                ++n2;
            }
            return parcelableSparseIntArray;
        }

        public ParcelableSparseIntArray[] newArray(int n) {
            return new ParcelableSparseIntArray[n];
        }
    };

    public ParcelableSparseIntArray() {
    }

    public ParcelableSparseIntArray(int n) {
        super(n);
    }

    public ParcelableSparseIntArray(SparseIntArray sparseIntArray) {
        int n = 0;
        while (n < sparseIntArray.size()) {
            this.put(sparseIntArray.keyAt(n), sparseIntArray.valueAt(n));
            ++n;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int[] arrn = new int[this.size()];
        int[] arrn2 = new int[this.size()];
        n = 0;
        do {
            if (n >= this.size()) {
                parcel.writeInt(this.size());
                parcel.writeIntArray(arrn);
                parcel.writeIntArray(arrn2);
                return;
            }
            arrn[n] = this.keyAt(n);
            arrn2[n] = this.valueAt(n);
            ++n;
        } while (true);
    }

}

