/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.stateful;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.collection.SimpleArrayMap;
import androidx.customview.view.AbsSavedState;

public class ExtendableSavedState
extends AbsSavedState {
    public static final Parcelable.Creator<ExtendableSavedState> CREATOR = new Parcelable.ClassLoaderCreator<ExtendableSavedState>(){

        public ExtendableSavedState createFromParcel(Parcel parcel) {
            return new ExtendableSavedState(parcel, null);
        }

        public ExtendableSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new ExtendableSavedState(parcel, classLoader);
        }

        public ExtendableSavedState[] newArray(int n) {
            return new ExtendableSavedState[n];
        }
    };
    public final SimpleArrayMap<String, Bundle> extendableStates;

    private ExtendableSavedState(Parcel parcel, ClassLoader arrstring) {
        super(parcel, (ClassLoader)arrstring);
        int n = parcel.readInt();
        arrstring = new String[n];
        parcel.readStringArray(arrstring);
        Object[] arrobject = new Bundle[n];
        parcel.readTypedArray(arrobject, Bundle.CREATOR);
        this.extendableStates = new SimpleArrayMap(n);
        int n2 = 0;
        while (n2 < n) {
            this.extendableStates.put(arrstring[n2], (Bundle)arrobject[n2]);
            ++n2;
        }
    }

    public ExtendableSavedState(Parcelable parcelable) {
        super(parcelable);
        this.extendableStates = new SimpleArrayMap();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExtendableSavedState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" states=");
        stringBuilder.append(this.extendableStates);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        int n2 = this.extendableStates.size();
        parcel.writeInt(n2);
        String[] arrstring = new String[n2];
        Bundle[] arrbundle = new Bundle[n2];
        n = 0;
        do {
            if (n >= n2) {
                parcel.writeStringArray(arrstring);
                parcel.writeTypedArray((Parcelable[])arrbundle, 0);
                return;
            }
            arrstring[n] = this.extendableStates.keyAt(n);
            arrbundle[n] = this.extendableStates.valueAt(n);
            ++n;
        } while (true);
    }

}

