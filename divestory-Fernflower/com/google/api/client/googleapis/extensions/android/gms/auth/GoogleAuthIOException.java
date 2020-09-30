package com.google.api.client.googleapis.extensions.android.gms.auth;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class GoogleAuthIOException extends IOException {
   private static final long serialVersionUID = 1L;

   public GoogleAuthIOException(GoogleAuthException var1) {
      this.initCause((Throwable)Preconditions.checkNotNull(var1));
   }

   public GoogleAuthException getCause() {
      return (GoogleAuthException)super.getCause();
   }
}
