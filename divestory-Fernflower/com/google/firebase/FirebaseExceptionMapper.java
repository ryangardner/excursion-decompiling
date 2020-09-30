package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;

public class FirebaseExceptionMapper implements StatusExceptionMapper {
   public Exception getException(Status var1) {
      return (Exception)(var1.getStatusCode() == 8 ? new FirebaseException(var1.zza()) : new FirebaseApiNotAvailableException(var1.zza()));
   }
}
