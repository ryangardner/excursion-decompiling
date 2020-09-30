/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.drive.metadata;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.zzc;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomPropertyKey
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CustomPropertyKey> CREATOR = new zzc();
    public static final int PRIVATE = 1;
    public static final int PUBLIC = 0;
    private static final Pattern zzja = Pattern.compile("[\\w.!@$%^&*()/-]+");
    private final int visibility;
    private final String zziz;

    public CustomPropertyKey(String string2, int n) {
        boolean bl;
        Preconditions.checkNotNull(string2, "key");
        Preconditions.checkArgument(zzja.matcher(string2).matches(), "key name characters must be alphanumeric or one of .!@$%^&*()-_/");
        boolean bl2 = bl = true;
        if (n != 0) {
            bl2 = n == 1 ? bl : false;
        }
        Preconditions.checkArgument(bl2, "visibility must be either PUBLIC or PRIVATE");
        this.zziz = string2;
        this.visibility = n;
    }

    public static CustomPropertyKey fromJson(JSONObject jSONObject) throws JSONException {
        return new CustomPropertyKey(jSONObject.getString("key"), jSONObject.getInt("visibility"));
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (!((CustomPropertyKey)(object = (CustomPropertyKey)object)).getKey().equals(this.zziz)) return false;
        if (((CustomPropertyKey)object).getVisibility() != this.visibility) return false;
        return true;
    }

    public String getKey() {
        return this.zziz;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public int hashCode() {
        String string2 = this.zziz;
        int n = this.visibility;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 11);
        stringBuilder.append(string2);
        stringBuilder.append(n);
        return stringBuilder.toString().hashCode();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("key", (Object)this.getKey());
        jSONObject.put("visibility", this.getVisibility());
        return jSONObject;
    }

    public String toString() {
        String string2 = this.zziz;
        int n = this.visibility;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 31);
        stringBuilder.append("CustomPropertyKey(");
        stringBuilder.append(string2);
        stringBuilder.append(",");
        stringBuilder.append(n);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zziz, false);
        SafeParcelWriter.writeInt(parcel, 3, this.visibility);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

