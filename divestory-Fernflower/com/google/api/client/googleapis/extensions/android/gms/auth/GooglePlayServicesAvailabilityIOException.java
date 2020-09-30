package com.google.api.client.googleapis.extensions.android.gms.auth;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;

public class GooglePlayServicesAvailabilityIOException extends UserRecoverableAuthIOException {
   private static final long serialVersionUID = 1L;

   public GooglePlayServicesAvailabilityIOException(GooglePlayServicesAvailabilityException var1) {
      super(var1);
   }

   public GooglePlayServicesAvailabilityException getCause() {
      return (GooglePlayServicesAvailabilityException)super.getCause();
   }

   public final int getConnectionStatusCode() {
      return this.getCause().getConnectionStatusCode();
   }
}
