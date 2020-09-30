/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.drive.zzm;
import java.util.Set;
import java.util.regex.Pattern;

public class DriveSpace
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<DriveSpace> CREATOR = new zzm();
    public static final DriveSpace zzah = new DriveSpace("DRIVE");
    public static final DriveSpace zzai = new DriveSpace("APP_DATA_FOLDER");
    public static final DriveSpace zzaj;
    private static final Set<DriveSpace> zzak;
    private static final String zzal;
    private static final Pattern zzam;
    private final String name;

    static {
        Object object = new DriveSpace("PHOTOS");
        zzaj = object;
        zzak = object = CollectionUtils.setOf(zzah, zzai, object);
        zzal = TextUtils.join((CharSequence)",", (Object[])object.toArray());
        zzam = Pattern.compile("[A-Z0-9_]*");
    }

    DriveSpace(String string2) {
        this.name = Preconditions.checkNotNull(string2);
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() == DriveSpace.class) return this.name.equals(((DriveSpace)object).name);
        return false;
    }

    public int hashCode() {
        return this.name.hashCode() ^ 1247068382;
    }

    public String toString() {
        return this.name;
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

