package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;

public class ResolvableApiException extends ApiException {
   public ResolvableApiException(Status var1) {
      super(var1);
   }

   public PendingIntent getResolution() {
      return this.getStatus().getResolution();
   }

   public void startResolutionForResult(Activity var1, int var2) throws SendIntentException {
      this.getStatus().startResolutionForResult(var1, var2);
   }
}
