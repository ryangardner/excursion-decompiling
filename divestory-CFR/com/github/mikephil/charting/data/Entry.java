/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.ParcelFormatException
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.utils.Utils;

public class Entry
extends BaseEntry
implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>(){

        public Entry createFromParcel(Parcel parcel) {
            return new Entry(parcel);
        }

        public Entry[] newArray(int n) {
            return new Entry[n];
        }
    };
    private float x = 0.0f;

    public Entry() {
    }

    public Entry(float f, float f2) {
        super(f2);
        this.x = f;
    }

    public Entry(float f, float f2, Drawable drawable2) {
        super(f2, drawable2);
        this.x = f;
    }

    public Entry(float f, float f2, Drawable drawable2, Object object) {
        super(f2, drawable2, object);
        this.x = f;
    }

    public Entry(float f, float f2, Object object) {
        super(f2, object);
        this.x = f;
    }

    protected Entry(Parcel parcel) {
        this.x = parcel.readFloat();
        this.setY(parcel.readFloat());
        if (parcel.readInt() != 1) return;
        this.setData((Object)parcel.readParcelable(Object.class.getClassLoader()));
    }

    public Entry copy() {
        return new Entry(this.x, this.getY(), this.getData());
    }

    public int describeContents() {
        return 0;
    }

    public boolean equalTo(Entry entry) {
        if (entry == null) {
            return false;
        }
        if (entry.getData() != this.getData()) {
            return false;
        }
        if (Math.abs(entry.x - this.x) > Utils.FLOAT_EPSILON) {
            return false;
        }
        if (!(Math.abs(entry.getY() - this.getY()) > Utils.FLOAT_EPSILON)) return true;
        return false;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entry, x: ");
        stringBuilder.append(this.x);
        stringBuilder.append(" y: ");
        stringBuilder.append(this.getY());
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.getY());
        if (this.getData() != null) {
            if (!(this.getData() instanceof Parcelable)) throw new ParcelFormatException("Cannot parcel an Entry with non-parcelable data");
            parcel.writeInt(1);
            parcel.writeParcelable((Parcelable)this.getData(), n);
            return;
        }
        parcel.writeInt(0);
    }

}

