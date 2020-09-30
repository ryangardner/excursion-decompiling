package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;

final class zzbq implements ListenerHolder.Notifier<DriveFile.DownloadProgressListener> {
   // $FF: synthetic field
   private final long zzfb;
   // $FF: synthetic field
   private final long zzfc;

   zzbq(zzbp var1, long var2, long var4) {
      this.zzfb = var2;
      this.zzfc = var4;
   }

   // $FF: synthetic method
   public final void notifyListener(Object var1) {
      ((DriveFile.DownloadProgressListener)var1).onProgress(this.zzfb, this.zzfc);
   }

   public final void onNotifyListenerFailed() {
   }
}
