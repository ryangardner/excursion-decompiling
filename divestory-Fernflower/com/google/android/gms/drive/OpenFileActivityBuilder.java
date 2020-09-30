package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzgm;

@Deprecated
public class OpenFileActivityBuilder {
   public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
   private String zzba;
   private String[] zzbb;
   private Filter zzbc;
   private DriveId zzbd;

   public IntentSender build(GoogleApiClient var1) {
      Preconditions.checkState(var1.isConnected(), "Client must be connected");
      this.zzg();
      FilterHolder var2;
      if (this.zzbc == null) {
         var2 = null;
      } else {
         var2 = new FilterHolder(this.zzbc);
      }

      try {
         zzeo var5 = (zzeo)((zzaw)var1.getClient(Drive.CLIENT_KEY)).getService();
         zzgm var3 = new zzgm(this.zzba, this.zzbb, this.zzbd, var2);
         IntentSender var6 = var5.zza(var3);
         return var6;
      } catch (RemoteException var4) {
         throw new RuntimeException("Unable to connect Drive Play Service", var4);
      }
   }

   final String getTitle() {
      return this.zzba;
   }

   public OpenFileActivityBuilder setActivityStartFolder(DriveId var1) {
      this.zzbd = (DriveId)Preconditions.checkNotNull(var1);
      return this;
   }

   public OpenFileActivityBuilder setActivityTitle(String var1) {
      this.zzba = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public OpenFileActivityBuilder setMimeType(String[] var1) {
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "mimeTypes may not be null");
      this.zzbb = var1;
      return this;
   }

   public OpenFileActivityBuilder setSelectionFilter(Filter var1) {
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "filter may not be null");
      Preconditions.checkArgument(true ^ com.google.android.gms.drive.query.internal.zzk.zza(var1), "FullTextSearchFilter cannot be used as a selection filter");
      this.zzbc = var1;
      return this;
   }

   final void zzg() {
      if (this.zzbb == null) {
         this.zzbb = new String[0];
      }

      if (this.zzbb.length > 0 && this.zzbc != null) {
         throw new IllegalStateException("Cannot use a selection filter and set mimetypes simultaneously");
      }
   }

   final String[] zzs() {
      return this.zzbb;
   }

   final Filter zzt() {
      return this.zzbc;
   }

   final DriveId zzu() {
      return this.zzbd;
   }
}
