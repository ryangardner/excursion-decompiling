package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.OptionalPendingResultImpl;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.internal.Preconditions;

public final class PendingResults {
   private PendingResults() {
   }

   public static PendingResult<Status> canceledPendingResult() {
      StatusPendingResult var0 = new StatusPendingResult(Looper.getMainLooper());
      var0.cancel();
      return var0;
   }

   public static <R extends Result> PendingResult<R> canceledPendingResult(R var0) {
      Preconditions.checkNotNull(var0, "Result must not be null");
      boolean var1;
      if (var0.getStatus().getStatusCode() == 16) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Status code must be CommonStatusCodes.CANCELED");
      PendingResults.zab var2 = new PendingResults.zab(var0);
      var2.cancel();
      return var2;
   }

   public static <R extends Result> PendingResult<R> immediateFailedResult(R var0, GoogleApiClient var1) {
      Preconditions.checkNotNull(var0, "Result must not be null");
      Preconditions.checkArgument(var0.getStatus().isSuccess() ^ true, "Status code must not be SUCCESS");
      PendingResults.zaa var2 = new PendingResults.zaa(var1, var0);
      var2.setResult(var0);
      return var2;
   }

   public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R var0) {
      Preconditions.checkNotNull(var0, "Result must not be null");
      PendingResults.zac var1 = new PendingResults.zac((GoogleApiClient)null);
      var1.setResult(var0);
      return new OptionalPendingResultImpl(var1);
   }

   public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R var0, GoogleApiClient var1) {
      Preconditions.checkNotNull(var0, "Result must not be null");
      PendingResults.zac var2 = new PendingResults.zac(var1);
      var2.setResult(var0);
      return new OptionalPendingResultImpl(var2);
   }

   public static PendingResult<Status> immediatePendingResult(Status var0) {
      Preconditions.checkNotNull(var0, "Result must not be null");
      StatusPendingResult var1 = new StatusPendingResult(Looper.getMainLooper());
      var1.setResult(var0);
      return var1;
   }

   public static PendingResult<Status> immediatePendingResult(Status var0, GoogleApiClient var1) {
      Preconditions.checkNotNull(var0, "Result must not be null");
      StatusPendingResult var2 = new StatusPendingResult(var1);
      var2.setResult(var0);
      return var2;
   }

   private static final class zaa<R extends Result> extends BasePendingResult<R> {
      private final R zab;

      public zaa(GoogleApiClient var1, R var2) {
         super(var1);
         this.zab = var2;
      }

      protected final R createFailedResult(Status var1) {
         return this.zab;
      }
   }

   private static final class zab<R extends Result> extends BasePendingResult<R> {
      private final R zab;

      public zab(R var1) {
         super(Looper.getMainLooper());
         this.zab = var1;
      }

      protected final R createFailedResult(Status var1) {
         if (var1.getStatusCode() == this.zab.getStatus().getStatusCode()) {
            return this.zab;
         } else {
            throw new UnsupportedOperationException("Creating failed results is not supported");
         }
      }
   }

   private static final class zac<R extends Result> extends BasePendingResult<R> {
      public zac(GoogleApiClient var1) {
         super(var1);
      }

      protected final R createFailedResult(Status var1) {
         throw new UnsupportedOperationException("Creating failed results is not supported");
      }
   }
}
