/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseBooleanArray
 */
package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class ParcelableSparseBooleanArray
extends SparseBooleanArray
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseBooleanArray> CREATOR = new Parcelable.Creator<ParcelableSparseBooleanArray>(){

        public ParcelableSparseBooleanArray createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            ParcelableSparseBooleanArray parcelableSparseBooleanArray = new ParcelableSparseBooleanArray(n);
            int[] arrn = new int[n];
            boolean[] arrbl = new boolean[n];
            parcel.readIntArray(arrn);
            parcel.readBooleanArray(arrbl);
            int n2 = 0;
            while (n2 < n) {
                parcelableSparseBooleanArray.put(arrn[n2], arrbl[n2]);
                ++n2;
            }
            return parcelableSparseBooleanArray;
        }

        public ParcelableSparseBooleanArray[] newArray(int n) {
            return new ParcelableSparseBooleanArray[n];
        }
    };

    public ParcelableSparseBooleanArray() {
    }

    public ParcelableSparseBooleanArray(int n) {
        super(n);
    }

    public ParcelableSparseBooleanArray(SparseBooleanArray sparseBooleanArray) {
        super(sparseBooleanArray.size());
        int n = 0;
        while (n < sparseBooleanArray.size()) {
            this.put(sparseBooleanArray.keyAt(n), sparseBooleanArray.valueAt(n));
            ++n;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int[] arrn = new int[this.size()];
        boolean[] arrbl = new boolean[this.size()];
        n = 0;
        do {
            if (n >= this.size()) {
                parcel.writeInt(this.size());
                parcel.writeIntArray(arrn);
                parcel.writeBooleanArray(arrbl);
                return;
            }
            arrn[n] = this.keyAt(n);
            arrbl[n] = this.valueAt(n);
            ++n;
        } while (true);
    }

}

