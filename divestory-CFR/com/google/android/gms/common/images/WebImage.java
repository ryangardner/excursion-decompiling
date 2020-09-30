/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.zae;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<WebImage> CREATOR = new zae();
    private final int zaa;
    private final Uri zab;
    private final int zac;
    private final int zad;

    WebImage(int n, Uri uri, int n2, int n3) {
        this.zaa = n;
        this.zab = uri;
        this.zac = n2;
        this.zad = n3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int n, int n2) throws IllegalArgumentException {
        this(1, uri, n, n2);
        if (uri == null) throw new IllegalArgumentException("url cannot be null");
        if (n < 0) throw new IllegalArgumentException("width and height must not be negative");
        if (n2 < 0) throw new IllegalArgumentException("width and height must not be negative");
    }

    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(WebImage.zaa(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    private static Uri zaa(JSONObject jSONObject) {
        Uri uri;
        Uri uri2 = uri = Uri.EMPTY;
        if (!jSONObject.has("url")) return uri2;
        try {
            return Uri.parse((String)jSONObject.getString("url"));
        }
        catch (JSONException jSONException) {
            return uri;
        }
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (!(object instanceof WebImage)) {
            return false;
        }
        object = (WebImage)object;
        if (!Objects.equal((Object)this.zab, (Object)((WebImage)object).zab)) return false;
        if (this.zac != ((WebImage)object).zac) return false;
        if (this.zad != ((WebImage)object).zad) return false;
        return true;
    }

    public final int getHeight() {
        return this.zad;
    }

    public final Uri getUrl() {
        return this.zab;
    }

    public final int getWidth() {
        return this.zac;
    }

    public final int hashCode() {
        return Objects.hashCode(new Object[]{this.zab, this.zac, this.zad});
    }

    public final JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", (Object)this.zab.toString());
            jSONObject.put("width", this.zac);
            jSONObject.put("height", this.zad);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            return jSONObject;
        }
    }

    public final String toString() {
        return String.format(Locale.US, "Image %dx%d %s", this.zac, this.zad, this.zab.toString());
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeParcelable(parcel, 2, (Parcelable)this.getUrl(), n, false);
        SafeParcelWriter.writeInt(parcel, 3, this.getWidth());
        SafeParcelWriter.writeInt(parcel, 4, this.getHeight());
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

