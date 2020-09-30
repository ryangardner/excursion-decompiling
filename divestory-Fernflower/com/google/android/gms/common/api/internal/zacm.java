package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

final class zacm extends com.google.android.gms.internal.base.zap {
   // $FF: synthetic field
   private final zack zaa;

   public zacm(zack var1, Looper var2) {
      super(var2);
      this.zaa = var1;
   }

   public final void handleMessage(Message var1) {
      int var2 = var1.what;
      if (var2 != 0) {
         if (var2 != 1) {
            var2 = var1.what;
            StringBuilder var49 = new StringBuilder(70);
            var49.append("TransformationResultHandler received unknown message type: ");
            var49.append(var2);
            Log.e("TransformedResultImpl", var49.toString());
         } else {
            RuntimeException var51 = (RuntimeException)var1.obj;
            String var48 = String.valueOf(var51.getMessage());
            if (var48.length() != 0) {
               var48 = "Runtime exception on the transformation worker thread: ".concat(var48);
            } else {
               var48 = new String("Runtime exception on the transformation worker thread: ");
            }

            Log.e("TransformedResultImpl", var48);
            throw var51;
         }
      } else {
         PendingResult var4 = (PendingResult)var1.obj;
         Object var47 = zack.zad(this.zaa);
         synchronized(var47){}

         Throwable var10000;
         boolean var10001;
         label456: {
            zack var3;
            try {
               var3 = (zack)Preconditions.checkNotNull(zack.zae(this.zaa));
            } catch (Throwable var46) {
               var10000 = var46;
               var10001 = false;
               break label456;
            }

            if (var4 == null) {
               try {
                  Status var52 = new Status(13, "Transform returned null");
                  zack.zaa(var3, var52);
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label456;
               }
            } else {
               label459: {
                  try {
                     if (var4 instanceof zabz) {
                        zack.zaa(var3, ((zabz)var4).zaa());
                        break label459;
                     }
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break label456;
                  }

                  try {
                     var3.zaa(var4);
                  } catch (Throwable var43) {
                     var10000 = var43;
                     var10001 = false;
                     break label456;
                  }
               }
            }

            label433:
            try {
               return;
            } catch (Throwable var42) {
               var10000 = var42;
               var10001 = false;
               break label433;
            }
         }

         while(true) {
            Throwable var50 = var10000;

            try {
               throw var50;
            } catch (Throwable var41) {
               var10000 = var41;
               var10001 = false;
               continue;
            }
         }
      }
   }
}
