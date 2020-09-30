/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ResourceEvent;
import com.google.android.gms.drive.events.zzg;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzev;
import com.google.android.gms.internal.drive.zzhs;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public final class CompletionEvent
extends AbstractSafeParcelable
implements ResourceEvent {
    public static final Parcelable.Creator<CompletionEvent> CREATOR;
    public static final int STATUS_CANCELED = 3;
    public static final int STATUS_CONFLICT = 2;
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_SUCCESS = 0;
    private static final GmsLogger zzbz;
    private final int status;
    private final String zzca;
    private final ParcelFileDescriptor zzcb;
    private final ParcelFileDescriptor zzcc;
    private final MetadataBundle zzcd;
    private final List<String> zzce;
    private final IBinder zzcf;
    private boolean zzcg = false;
    private boolean zzch = false;
    private boolean zzci = false;
    private final DriveId zzk;

    static {
        zzbz = new GmsLogger("CompletionEvent", "");
        CREATOR = new zzg();
    }

    CompletionEvent(DriveId driveId, String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, MetadataBundle metadataBundle, List<String> list, int n, IBinder iBinder) {
        this.zzk = driveId;
        this.zzca = string2;
        this.zzcb = parcelFileDescriptor;
        this.zzcc = parcelFileDescriptor2;
        this.zzcd = metadataBundle;
        this.zzce = list;
        this.status = n;
        this.zzcf = iBinder;
    }

    private final void zza(boolean bl) {
        this.zzv();
        this.zzci = true;
        IOUtils.closeQuietly(this.zzcb);
        IOUtils.closeQuietly(this.zzcc);
        Object object = this.zzcd;
        if (object != null && ((MetadataBundle)object).zzd(zzhs.zzkq)) {
            this.zzcd.zza(zzhs.zzkq).release();
        }
        IBinder iBinder = this.zzcf;
        object = "snooze";
        if (iBinder == null) {
            if (!bl) {
                object = "dismiss";
            }
            zzbz.efmt("CompletionEvent", "No callback on %s", object);
            return;
        }
        try {
            zzev.zza(iBinder).zza(bl);
            return;
        }
        catch (RemoteException remoteException) {
            if (!bl) {
                object = "dismiss";
            }
            zzbz.e("CompletionEvent", String.format("RemoteException on %s", object), remoteException);
            return;
        }
    }

    private final void zzv() {
        if (this.zzci) throw new IllegalStateException("Event has already been dismissed or snoozed.");
    }

    public final void dismiss() {
        this.zza(false);
    }

    public final String getAccountName() {
        this.zzv();
        return this.zzca;
    }

    public final InputStream getBaseContentsInputStream() {
        this.zzv();
        if (this.zzcb == null) {
            return null;
        }
        if (this.zzcg) throw new IllegalStateException("getBaseInputStream() can only be called once per CompletionEvent instance.");
        this.zzcg = true;
        return new FileInputStream(this.zzcb.getFileDescriptor());
    }

    @Override
    public final DriveId getDriveId() {
        this.zzv();
        return this.zzk;
    }

    public final InputStream getModifiedContentsInputStream() {
        this.zzv();
        if (this.zzcc == null) {
            return null;
        }
        if (this.zzch) throw new IllegalStateException("getModifiedInputStream() can only be called once per CompletionEvent instance.");
        this.zzch = true;
        return new FileInputStream(this.zzcc.getFileDescriptor());
    }

    public final MetadataChangeSet getModifiedMetadataChangeSet() {
        this.zzv();
        if (this.zzcd == null) return null;
        return new MetadataChangeSet(this.zzcd);
    }

    public final int getStatus() {
        this.zzv();
        return this.status;
    }

    public final List<String> getTrackingTags() {
        this.zzv();
        return new ArrayList<String>(this.zzce);
    }

    @Override
    public final int getType() {
        return 2;
    }

    public final void snooze() {
        this.zza(true);
    }

    public final String toString() {
        List<String> list = this.zzce;
        if (list == null) {
            list = "<null>";
            return String.format(Locale.US, "CompletionEvent [id=%s, status=%s, trackingTag=%s]", this.zzk, this.status, list);
        } else {
            list = TextUtils.join((CharSequence)"','", (Iterable)list);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(list).length() + 2);
            stringBuilder.append("'");
            stringBuilder.append((String)((Object)list));
            stringBuilder.append("'");
            list = stringBuilder.toString();
        }
        return String.format(Locale.US, "CompletionEvent [id=%s, status=%s, trackingTag=%s]", this.zzk, this.status, list);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, n |= 1, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzca, false);
        SafeParcelWriter.writeParcelable(parcel, 4, (Parcelable)this.zzcb, n, false);
        SafeParcelWriter.writeParcelable(parcel, 5, (Parcelable)this.zzcc, n, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzcd, n, false);
        SafeParcelWriter.writeStringList(parcel, 7, this.zzce, false);
        SafeParcelWriter.writeInt(parcel, 8, this.status);
        SafeParcelWriter.writeIBinder(parcel, 9, this.zzcf, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

