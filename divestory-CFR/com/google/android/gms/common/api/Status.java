/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class Status
extends AbstractSafeParcelable
implements Result,
ReflectedParcelable {
    public static final Parcelable.Creator<Status> CREATOR;
    public static final Status RESULT_CANCELED;
    public static final Status RESULT_DEAD_CLIENT;
    public static final Status RESULT_INTERNAL_ERROR;
    public static final Status RESULT_INTERRUPTED;
    public static final Status RESULT_SUCCESS;
    public static final Status RESULT_TIMEOUT;
    private static final Status zza;
    private final int zzb;
    private final int zzc;
    private final String zzd;
    private final PendingIntent zze;
    private final ConnectionResult zzf;

    static {
        RESULT_SUCCESS = new Status(0);
        RESULT_INTERRUPTED = new Status(14);
        RESULT_INTERNAL_ERROR = new Status(8);
        RESULT_TIMEOUT = new Status(15);
        RESULT_CANCELED = new Status(16);
        zza = new Status(17);
        RESULT_DEAD_CLIENT = new Status(18);
        CREATOR = new zzb();
    }

    public Status(int n) {
        this(n, null);
    }

    Status(int n, int n2, String string2, PendingIntent pendingIntent) {
        this(n, n2, string2, pendingIntent, null);
    }

    Status(int n, int n2, String string2, PendingIntent pendingIntent, ConnectionResult connectionResult) {
        this.zzb = n;
        this.zzc = n2;
        this.zzd = string2;
        this.zze = pendingIntent;
        this.zzf = connectionResult;
    }

    public Status(int n, String string2) {
        this(1, n, string2, null);
    }

    public Status(int n, String string2, PendingIntent pendingIntent) {
        this(1, n, string2, pendingIntent);
    }

    public Status(ConnectionResult connectionResult, String string2) {
        this(connectionResult, string2, 17);
    }

    @Deprecated
    public Status(ConnectionResult connectionResult, String string2, int n) {
        this(1, n, string2, connectionResult.getResolution(), connectionResult);
    }

    public final boolean equals(Object object) {
        if (!(object instanceof Status)) {
            return false;
        }
        object = (Status)object;
        if (this.zzb != ((Status)object).zzb) return false;
        if (this.zzc != ((Status)object).zzc) return false;
        if (!Objects.equal(this.zzd, ((Status)object).zzd)) return false;
        if (!Objects.equal((Object)this.zze, (Object)((Status)object).zze)) return false;
        if (!Objects.equal(this.zzf, ((Status)object).zzf)) return false;
        return true;
    }

    public final ConnectionResult getConnectionResult() {
        return this.zzf;
    }

    public final PendingIntent getResolution() {
        return this.zze;
    }

    @Override
    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzc;
    }

    public final String getStatusMessage() {
        return this.zzd;
    }

    public final boolean hasResolution() {
        if (this.zze == null) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(new Object[]{this.zzb, this.zzc, this.zzd, this.zze, this.zzf});
    }

    public final boolean isCanceled() {
        if (this.zzc != 16) return false;
        return true;
    }

    public final boolean isInterrupted() {
        if (this.zzc != 14) return false;
        return true;
    }

    public final boolean isSuccess() {
        if (this.zzc > 0) return false;
        return true;
    }

    public final void startResolutionForResult(Activity activity, int n) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(Preconditions.checkNotNull(this.zze).getIntentSender(), n, null, 0, 0, 0);
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("statusCode", this.zza()).add("resolution", (Object)this.zze).toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.getStatusCode());
        SafeParcelWriter.writeString(parcel, 2, this.getStatusMessage(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, (Parcelable)this.zze, n, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.getConnectionResult(), n, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zzb);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final String zza() {
        String string2 = this.zzd;
        if (string2 == null) return CommonStatusCodes.getStatusCodeString(this.zzc);
        return string2;
    }
}

