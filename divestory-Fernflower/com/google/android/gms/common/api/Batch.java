package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends BasePendingResult<BatchResult> {
   private int zab;
   private boolean zac;
   private boolean zad;
   private final PendingResult<?>[] zae;
   private final Object zaf;

   private Batch(List<PendingResult<?>> var1, GoogleApiClient var2) {
      super(var2);
      this.zaf = new Object();
      int var3 = var1.size();
      this.zab = var3;
      this.zae = new PendingResult[var3];
      if (var1.isEmpty()) {
         this.setResult(new BatchResult(Status.RESULT_SUCCESS, this.zae));
      } else {
         for(var3 = 0; var3 < var1.size(); ++var3) {
            PendingResult var4 = (PendingResult)var1.get(var3);
            this.zae[var3] = var4;
            var4.addStatusListener(new zab(this));
         }

      }
   }

   // $FF: synthetic method
   Batch(List var1, GoogleApiClient var2, zab var3) {
      this(var1, var2);
   }

   // $FF: synthetic method
   static Object zaa(Batch var0) {
      return var0.zaf;
   }

   // $FF: synthetic method
   static boolean zaa(Batch var0, boolean var1) {
      var0.zad = true;
      return true;
   }

   // $FF: synthetic method
   static int zab(Batch var0) {
      int var1 = var0.zab--;
      return var1;
   }

   // $FF: synthetic method
   static boolean zab(Batch var0, boolean var1) {
      var0.zac = true;
      return true;
   }

   // $FF: synthetic method
   static int zac(Batch var0) {
      return var0.zab;
   }

   // $FF: synthetic method
   static boolean zad(Batch var0) {
      return var0.zad;
   }

   // $FF: synthetic method
   static void zae(Batch var0) {
      var0.cancel();
   }

   // $FF: synthetic method
   static boolean zaf(Batch var0) {
      return var0.zac;
   }

   // $FF: synthetic method
   static PendingResult[] zag(Batch var0) {
      return var0.zae;
   }

   public final void cancel() {
      super.cancel();
      PendingResult[] var1 = this.zae;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].cancel();
      }

   }

   public final BatchResult createFailedResult(Status var1) {
      return new BatchResult(var1, this.zae);
   }

   public static final class Builder {
      private List<PendingResult<?>> zaa = new ArrayList();
      private GoogleApiClient zab;

      public Builder(GoogleApiClient var1) {
         this.zab = var1;
      }

      public final <R extends Result> BatchResultToken<R> add(PendingResult<R> var1) {
         BatchResultToken var2 = new BatchResultToken(this.zaa.size());
         this.zaa.add(var1);
         return var2;
      }

      public final Batch build() {
         return new Batch(this.zaa, this.zab, (zab)null);
      }
   }
}
