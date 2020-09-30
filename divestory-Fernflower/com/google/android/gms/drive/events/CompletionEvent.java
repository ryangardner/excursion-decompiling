package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzev;
import com.google.android.gms.internal.drive.zzhs;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class CompletionEvent extends AbstractSafeParcelable implements ResourceEvent {
   public static final Creator<CompletionEvent> CREATOR = new zzg();
   public static final int STATUS_CANCELED = 3;
   public static final int STATUS_CONFLICT = 2;
   public static final int STATUS_FAILURE = 1;
   public static final int STATUS_SUCCESS = 0;
   private static final GmsLogger zzbz = new GmsLogger("CompletionEvent", "");
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

   CompletionEvent(DriveId var1, String var2, ParcelFileDescriptor var3, ParcelFileDescriptor var4, MetadataBundle var5, List<String> var6, int var7, IBinder var8) {
      this.zzk = var1;
      this.zzca = var2;
      this.zzcb = var3;
      this.zzcc = var4;
      this.zzcd = var5;
      this.zzce = var6;
      this.status = var7;
      this.zzcf = var8;
   }

   private final void zza(boolean var1) {
      this.zzv();
      this.zzci = true;
      IOUtils.closeQuietly(this.zzcb);
      IOUtils.closeQuietly(this.zzcc);
      MetadataBundle var2 = this.zzcd;
      if (var2 != null && var2.zzd(zzhs.zzkq)) {
         ((BitmapTeleporter)this.zzcd.zza(zzhs.zzkq)).release();
      }

      IBinder var3 = this.zzcf;
      String var5 = "snooze";
      if (var3 == null) {
         if (!var1) {
            var5 = "dismiss";
         }

         zzbz.efmt("CompletionEvent", "No callback on %s", var5);
      } else {
         try {
            zzev.zza(var3).zza(var1);
         } catch (RemoteException var4) {
            if (!var1) {
               var5 = "dismiss";
            }

            zzbz.e("CompletionEvent", String.format("RemoteException on %s", var5), var4);
         }
      }
   }

   private final void zzv() {
      if (this.zzci) {
         throw new IllegalStateException("Event has already been dismissed or snoozed.");
      }
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
      } else if (!this.zzcg) {
         this.zzcg = true;
         return new FileInputStream(this.zzcb.getFileDescriptor());
      } else {
         throw new IllegalStateException("getBaseInputStream() can only be called once per CompletionEvent instance.");
      }
   }

   public final DriveId getDriveId() {
      this.zzv();
      return this.zzk;
   }

   public final InputStream getModifiedContentsInputStream() {
      this.zzv();
      if (this.zzcc == null) {
         return null;
      } else if (!this.zzch) {
         this.zzch = true;
         return new FileInputStream(this.zzcc.getFileDescriptor());
      } else {
         throw new IllegalStateException("getModifiedInputStream() can only be called once per CompletionEvent instance.");
      }
   }

   public final MetadataChangeSet getModifiedMetadataChangeSet() {
      this.zzv();
      return this.zzcd != null ? new MetadataChangeSet(this.zzcd) : null;
   }

   public final int getStatus() {
      this.zzv();
      return this.status;
   }

   public final List<String> getTrackingTags() {
      this.zzv();
      return new ArrayList(this.zzce);
   }

   public final int getType() {
      return 2;
   }

   public final void snooze() {
      this.zza(true);
   }

   public final String toString() {
      List var1 = this.zzce;
      String var3;
      if (var1 == null) {
         var3 = "<null>";
      } else {
         var3 = TextUtils.join("','", var1);
         StringBuilder var2 = new StringBuilder(String.valueOf(var3).length() + 2);
         var2.append("'");
         var2.append(var3);
         var2.append("'");
         var3 = var2.toString();
      }

      return String.format(Locale.US, "CompletionEvent [id=%s, status=%s, trackingTag=%s]", this.zzk, this.status, var3);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 |= 1;
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzk, var2, false);
      SafeParcelWriter.writeString(var1, 3, this.zzca, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzcb, var2, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzcc, var2, false);
      SafeParcelWriter.writeParcelable(var1, 6, this.zzcd, var2, false);
      SafeParcelWriter.writeStringList(var1, 7, this.zzce, false);
      SafeParcelWriter.writeInt(var1, 8, this.status);
      SafeParcelWriter.writeIBinder(var1, 9, this.zzcf, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
