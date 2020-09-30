package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.content.Intent;
import com.google.android.gms.auth.UserRecoverableAuthException;

public class UserRecoverableAuthIOException extends GoogleAuthIOException {
   private static final long serialVersionUID = 1L;

   public UserRecoverableAuthIOException(UserRecoverableAuthException var1) {
      super(var1);
   }

   public UserRecoverableAuthException getCause() {
      return (UserRecoverableAuthException)super.getCause();
   }

   public final Intent getIntent() {
      return this.getCause().getIntent();
   }
}
