/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Base64
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.zzk;
import com.google.android.gms.internal.drive.zzbn;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzfb;
import com.google.android.gms.internal.drive.zzfd;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzlq;

public class DriveId
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<DriveId> CREATOR = new zzk();
    public static final int RESOURCE_TYPE_FILE = 0;
    public static final int RESOURCE_TYPE_FOLDER = 1;
    public static final int RESOURCE_TYPE_UNKNOWN = -1;
    private final String zzad;
    private final long zzae;
    private final int zzaf;
    private volatile String zzag = null;
    private final long zzf;
    private volatile String zzh = null;

    public DriveId(String string2, long l, long l2, int n) {
        this.zzad = string2;
        boolean bl = "".equals(string2);
        boolean bl2 = true;
        Preconditions.checkArgument(bl ^ true);
        bl = bl2;
        if (string2 == null) {
            bl = l != -1L ? bl2 : false;
        }
        Preconditions.checkArgument(bl);
        this.zzae = l;
        this.zzf = l2;
        this.zzaf = n;
    }

    public static DriveId decodeFromString(String string2) {
        boolean bl = string2.startsWith("DriveId:");
        String string3 = String.valueOf(string2);
        string3 = string3.length() != 0 ? "Invalid DriveId: ".concat(string3) : new String("Invalid DriveId: ");
        Preconditions.checkArgument(bl, string3);
        return DriveId.zza(Base64.decode((String)string2.substring(8), (int)10));
    }

    public static DriveId zza(String string2) {
        Preconditions.checkNotNull(string2);
        return new DriveId(string2, -1L, -1L, -1);
    }

    private static DriveId zza(byte[] object) {
        try {
            zzfb zzfb2 = zzfb.zza(object, zzjx.zzcj());
            if ("".equals(zzfb2.getResourceId())) {
                object = null;
                return new DriveId((String)object, zzfb2.zzal(), zzfb2.zzam(), zzfb2.getResourceType());
            }
            object = zzfb2.getResourceId();
            return new DriveId((String)object, zzfb2.zzal(), zzfb2.zzam(), zzfb2.getResourceType());
        }
        catch (zzkq zzkq2) {
            throw new IllegalArgumentException();
        }
    }

    public DriveFile asDriveFile() {
        if (this.zzaf == 1) throw new IllegalStateException("This DriveId corresponds to a folder. Call asDriveFolder instead.");
        return new zzbn(this);
    }

    public DriveFolder asDriveFolder() {
        if (this.zzaf == 0) throw new IllegalStateException("This DriveId corresponds to a file. Call asDriveFile instead.");
        return new zzbs(this);
    }

    public DriveResource asDriveResource() {
        int n = this.zzaf;
        if (n == 1) {
            return this.asDriveFolder();
        }
        if (n != 0) return new zzdp(this);
        return this.asDriveFile();
    }

    public final String encodeToString() {
        String string2;
        if (this.zzh != null) return this.zzh;
        zzfb.zza zza2 = zzfb.zzan().zzm(1);
        String string3 = string2 = this.zzad;
        if (string2 == null) {
            string3 = "";
        }
        string3 = (string3 = String.valueOf(Base64.encodeToString((byte[])((zzfb)((zzkk)zza2.zze(string3).zzg(this.zzae).zzh(this.zzf).zzn(this.zzaf).zzdf())).toByteArray(), (int)10))).length() != 0 ? "DriveId:".concat(string3) : new String("DriveId:");
        this.zzh = string3;
        return this.zzh;
    }

    public boolean equals(Object object) {
        String string2;
        if (object == null) return false;
        if (object.getClass() != DriveId.class) {
            return false;
        }
        object = (DriveId)object;
        if (((DriveId)object).zzf != this.zzf) {
            return false;
        }
        if (((DriveId)object).zzae == -1L && this.zzae == -1L) {
            return ((DriveId)object).zzad.equals(this.zzad);
        }
        String string3 = this.zzad;
        if (string3 != null && (string2 = ((DriveId)object).zzad) != null) {
            if (((DriveId)object).zzae != this.zzae) return false;
            if (!string2.equals(string3)) return false;
            return true;
        }
        if (((DriveId)object).zzae != this.zzae) return false;
        return true;
    }

    public String getResourceId() {
        return this.zzad;
    }

    public int getResourceType() {
        return this.zzaf;
    }

    public int hashCode() {
        if (this.zzae == -1L) {
            return this.zzad.hashCode();
        }
        String string2 = String.valueOf(String.valueOf(this.zzf));
        String string3 = String.valueOf(String.valueOf(this.zzae));
        if (string3.length() != 0) {
            string2 = string2.concat(string3);
            return string2.hashCode();
        }
        string2 = new String(string2);
        return string2.hashCode();
    }

    public final String toInvariantString() {
        if (this.zzag != null) return this.zzag;
        this.zzag = Base64.encodeToString((byte[])((zzfd)((zzkk)zzfd.zzap().zzi(this.zzae).zzj(this.zzf).zzdf())).toByteArray(), (int)10);
        return this.zzag;
    }

    public String toString() {
        return this.encodeToString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzad, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzae);
        SafeParcelWriter.writeLong(parcel, 4, this.zzf);
        SafeParcelWriter.writeInt(parcel, 5, this.zzaf);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

