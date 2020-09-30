package com.google.android.gms.tasks;

import java.util.ArrayDeque;
import java.util.Queue;

final class zzq<TResult> {
   private final Object zza = new Object();
   private Queue<zzr<TResult>> zzb;
   private boolean zzc;

   public final void zza(Task<TResult> var1) {
      Object var2 = this.zza;
      synchronized(var2){}

      Throwable var76;
      Throwable var10000;
      boolean var10001;
      label676: {
         label678: {
            try {
               if (this.zzb != null && !this.zzc) {
                  break label678;
               }
            } catch (Throwable var75) {
               var10000 = var75;
               var10001 = false;
               break label676;
            }

            try {
               return;
            } catch (Throwable var74) {
               var10000 = var74;
               var10001 = false;
               break label676;
            }
         }

         try {
            this.zzc = true;
         } catch (Throwable var73) {
            var10000 = var73;
            var10001 = false;
            break label676;
         }

         while(true) {
            var2 = this.zza;
            synchronized(var2){}

            label653: {
               zzr var3;
               try {
                  var3 = (zzr)this.zzb.poll();
               } catch (Throwable var72) {
                  var10000 = var72;
                  var10001 = false;
                  break label653;
               }

               if (var3 == null) {
                  label646:
                  try {
                     this.zzc = false;
                     return;
                  } catch (Throwable var70) {
                     var10000 = var70;
                     var10001 = false;
                     break label646;
                  }
               } else {
                  label649: {
                     try {
                        ;
                     } catch (Throwable var71) {
                        var10000 = var71;
                        var10001 = false;
                        break label649;
                     }

                     var3.zza(var1);
                     continue;
                  }
               }
            }

            while(true) {
               var76 = var10000;

               try {
                  throw var76;
               } catch (Throwable var68) {
                  var10000 = var68;
                  var10001 = false;
                  continue;
               }
            }
         }
      }

      while(true) {
         var76 = var10000;

         try {
            throw var76;
         } catch (Throwable var69) {
            var10000 = var69;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zza(zzr<TResult> var1) {
      Object var2 = this.zza;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (this.zzb == null) {
               ArrayDeque var3 = new ArrayDeque();
               this.zzb = var3;
            }
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            this.zzb.add(var1);
            return;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var16 = var10000;

         try {
            throw var16;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }
}
