package com.google.android.gms.common.api;

final class zab implements PendingResult.StatusListener {
   // $FF: synthetic field
   private final Batch zaa;

   zab(Batch var1) {
      this.zaa = var1;
   }

   public final void onComplete(Status var1) {
      Object var2 = Batch.zaa(this.zaa);
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label859: {
         try {
            if (this.zaa.isCanceled()) {
               return;
            }
         } catch (Throwable var94) {
            var10000 = var94;
            var10001 = false;
            break label859;
         }

         label847: {
            try {
               if (var1.isCanceled()) {
                  Batch.zaa(this.zaa, true);
                  break label847;
               }
            } catch (Throwable var93) {
               var10000 = var93;
               var10001 = false;
               break label859;
            }

            try {
               if (!var1.isSuccess()) {
                  Batch.zab(this.zaa, true);
               }
            } catch (Throwable var92) {
               var10000 = var92;
               var10001 = false;
               break label859;
            }
         }

         label862: {
            label835:
            try {
               Batch.zab(this.zaa);
               if (Batch.zac(this.zaa) == 0) {
                  if (!Batch.zad(this.zaa)) {
                     break label835;
                  }

                  Batch.zae(this.zaa);
               }
               break label862;
            } catch (Throwable var91) {
               var10000 = var91;
               var10001 = false;
               break label859;
            }

            label826: {
               try {
                  if (Batch.zaf(this.zaa)) {
                     var1 = new Status(13);
                     break label826;
                  }
               } catch (Throwable var90) {
                  var10000 = var90;
                  var10001 = false;
                  break label859;
               }

               try {
                  var1 = Status.RESULT_SUCCESS;
               } catch (Throwable var89) {
                  var10000 = var89;
                  var10001 = false;
                  break label859;
               }
            }

            try {
               Batch var3 = this.zaa;
               BatchResult var4 = new BatchResult(var1, Batch.zag(this.zaa));
               var3.setResult(var4);
            } catch (Throwable var88) {
               var10000 = var88;
               var10001 = false;
               break label859;
            }
         }

         label816:
         try {
            return;
         } catch (Throwable var87) {
            var10000 = var87;
            var10001 = false;
            break label816;
         }
      }

      while(true) {
         Throwable var95 = var10000;

         try {
            throw var95;
         } catch (Throwable var86) {
            var10000 = var86;
            var10001 = false;
            continue;
         }
      }
   }
}
