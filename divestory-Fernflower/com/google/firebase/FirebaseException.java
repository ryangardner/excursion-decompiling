package com.google.firebase;

import com.google.android.gms.common.internal.Preconditions;

public class FirebaseException extends Exception {
   @Deprecated
   protected FirebaseException() {
   }

   public FirebaseException(String var1) {
      super(Preconditions.checkNotEmpty(var1, "Detail message must not be empty"));
   }

   public FirebaseException(String var1, Throwable var2) {
      super(Preconditions.checkNotEmpty(var1, "Detail message must not be empty"), var2);
   }
}
