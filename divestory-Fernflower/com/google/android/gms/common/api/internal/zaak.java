package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;

final class zaak extends zaba {
   // $FF: synthetic field
   private final BaseGmsClient.ConnectionProgressReportCallbacks zaa;

   zaak(zaai var1, zaay var2, BaseGmsClient.ConnectionProgressReportCallbacks var3) {
      super(var2);
      this.zaa = var3;
   }

   public final void zaa() {
      this.zaa.onReportServiceBinding(new ConnectionResult(16, (PendingIntent)null));
   }
}
