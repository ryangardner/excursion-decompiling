package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Contents extends AbstractSafeParcelable {
   public static final Creator<Contents> CREATOR = new zzc();
   private final int mode;
   private final ParcelFileDescriptor zzi;
   final int zzj;
   private final DriveId zzk;
   private final boolean zzl;
   private final String zzm;

   public Contents(ParcelFileDescriptor var1, int var2, int var3, DriveId var4, boolean var5, String var6) {
      this.zzi = var1;
      this.zzj = var2;
      this.mode = var3;
      this.zzk = var4;
      this.zzl = var5;
      this.zzm = var6;
   }

   public final DriveId getDriveId() {
      return this.zzk;
   }

   public final InputStream getInputStream() {
      return new FileInputStream(this.zzi.getFileDescriptor());
   }

   public final int getMode() {
      return this.mode;
   }

   public final OutputStream getOutputStream() {
      return new FileOutputStream(this.zzi.getFileDescriptor());
   }

   public ParcelFileDescriptor getParcelFileDescriptor() {
      return this.zzi;
   }

   public final int getRequestId() {
      return this.zzj;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzi, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zzj);
      SafeParcelWriter.writeInt(var1, 4, this.mode);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzk, var2, false);
      SafeParcelWriter.writeBoolean(var1, 7, this.zzl);
      SafeParcelWriter.writeString(var1, 8, this.zzm, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final boolean zzb() {
      return this.zzl;
   }
}
