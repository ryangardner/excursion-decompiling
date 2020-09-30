package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;

final class zzbp implements DriveFile.DownloadProgressListener {
   private final ListenerHolder<DriveFile.DownloadProgressListener> zzfa;

   public zzbp(ListenerHolder<DriveFile.DownloadProgressListener> var1) {
      this.zzfa = var1;
   }

   public final void onProgress(long var1, long var3) {
      this.zzfa.notifyListener(new zzbq(this, var1, var3));
   }
}
