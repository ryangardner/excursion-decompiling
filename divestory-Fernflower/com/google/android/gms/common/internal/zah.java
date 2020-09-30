package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zah implements Callback {
   @NotOnlyInitialized
   private final zak zaa;
   private final ArrayList<GoogleApiClient.ConnectionCallbacks> zab = new ArrayList();
   private final ArrayList<GoogleApiClient.ConnectionCallbacks> zac = new ArrayList();
   private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zad = new ArrayList();
   private volatile boolean zae = false;
   private final AtomicInteger zaf = new AtomicInteger(0);
   private boolean zag = false;
   private final Handler zah;
   private final Object zai = new Object();

   public zah(Looper var1, zak var2) {
      this.zaa = var2;
      this.zah = new com.google.android.gms.internal.base.zap(var1, this);
   }

   public final boolean handleMessage(Message var1) {
      if (var1.what == 1) {
         GoogleApiClient.ConnectionCallbacks var2 = (GoogleApiClient.ConnectionCallbacks)var1.obj;
         Object var17 = this.zai;
         synchronized(var17){}

         Throwable var10000;
         boolean var10001;
         label152: {
            try {
               if (this.zae && this.zaa.isConnected() && this.zab.contains(var2)) {
                  var2.onConnected(this.zaa.getConnectionHint());
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label152;
            }

            label149:
            try {
               return true;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label149;
            }
         }

         while(true) {
            Throwable var18 = var10000;

            try {
               throw var18;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      } else {
         int var3 = var1.what;
         StringBuilder var16 = new StringBuilder(45);
         var16.append("Don't know how to handle message: ");
         var16.append(var3);
         Log.wtf("GmsClientEvents", var16.toString(), new Exception());
         return false;
      }
   }

   public final void zaa() {
      this.zae = false;
      this.zaf.incrementAndGet();
   }

   public final void zaa(int var1) {
      Preconditions.checkHandlerThread(this.zah, "onUnintentionalDisconnection must only be called on the Handler thread");
      this.zah.removeMessages(1);
      Object var2 = this.zai;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label434: {
         ArrayList var3;
         int var4;
         int var5;
         try {
            this.zag = true;
            var3 = new ArrayList(this.zab);
            var4 = this.zaf.get();
            var3 = (ArrayList)var3;
            var5 = var3.size();
         } catch (Throwable var50) {
            var10000 = var50;
            var10001 = false;
            break label434;
         }

         int var6 = 0;

         while(var6 < var5) {
            Object var7;
            try {
               var7 = var3.get(var6);
            } catch (Throwable var47) {
               var10000 = var47;
               var10001 = false;
               break label434;
            }

            int var8 = var6 + 1;

            GoogleApiClient.ConnectionCallbacks var52;
            try {
               var52 = (GoogleApiClient.ConnectionCallbacks)var7;
               if (!this.zae || this.zaf.get() != var4) {
                  break;
               }
            } catch (Throwable var49) {
               var10000 = var49;
               var10001 = false;
               break label434;
            }

            var6 = var8;

            try {
               if (!this.zab.contains(var52)) {
                  continue;
               }

               var52.onConnectionSuspended(var1);
            } catch (Throwable var48) {
               var10000 = var48;
               var10001 = false;
               break label434;
            }

            var6 = var8;
         }

         label407:
         try {
            this.zac.clear();
            this.zag = false;
            return;
         } catch (Throwable var46) {
            var10000 = var46;
            var10001 = false;
            break label407;
         }
      }

      while(true) {
         Throwable var51 = var10000;

         try {
            throw var51;
         } catch (Throwable var45) {
            var10000 = var45;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zaa(Bundle var1) {
      Preconditions.checkHandlerThread(this.zah, "onConnectionSuccess must only be called on the Handler thread");
      Object var2 = this.zai;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label791: {
         boolean var3;
         try {
            var3 = this.zag;
         } catch (Throwable var82) {
            var10000 = var82;
            var10001 = false;
            break label791;
         }

         boolean var4 = true;
         if (!var3) {
            var3 = true;
         } else {
            var3 = false;
         }

         label782: {
            label781: {
               try {
                  Preconditions.checkState(var3);
                  this.zah.removeMessages(1);
                  this.zag = true;
                  if (this.zac.size() == 0) {
                     break label781;
                  }
               } catch (Throwable var81) {
                  var10000 = var81;
                  var10001 = false;
                  break label791;
               }

               var3 = false;
               break label782;
            }

            var3 = var4;
         }

         ArrayList var5;
         int var6;
         int var7;
         try {
            Preconditions.checkState(var3);
            var5 = new ArrayList(this.zab);
            var6 = this.zaf.get();
            var5 = (ArrayList)var5;
            var7 = var5.size();
         } catch (Throwable var80) {
            var10000 = var80;
            var10001 = false;
            break label791;
         }

         int var8 = 0;

         while(var8 < var7) {
            Object var9;
            try {
               var9 = var5.get(var8);
            } catch (Throwable var77) {
               var10000 = var77;
               var10001 = false;
               break label791;
            }

            int var10 = var8 + 1;

            GoogleApiClient.ConnectionCallbacks var84;
            try {
               var84 = (GoogleApiClient.ConnectionCallbacks)var9;
               if (!this.zae || !this.zaa.isConnected() || this.zaf.get() != var6) {
                  break;
               }
            } catch (Throwable var79) {
               var10000 = var79;
               var10001 = false;
               break label791;
            }

            var8 = var10;

            try {
               if (this.zac.contains(var84)) {
                  continue;
               }

               var84.onConnected(var1);
            } catch (Throwable var78) {
               var10000 = var78;
               var10001 = false;
               break label791;
            }

            var8 = var10;
         }

         label750:
         try {
            this.zac.clear();
            this.zag = false;
            return;
         } catch (Throwable var76) {
            var10000 = var76;
            var10001 = false;
            break label750;
         }
      }

      while(true) {
         Throwable var83 = var10000;

         try {
            throw var83;
         } catch (Throwable var75) {
            var10000 = var75;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zaa(ConnectionResult var1) {
      Preconditions.checkHandlerThread(this.zah, "onConnectionFailure must only be called on the Handler thread");
      this.zah.removeMessages(1);
      Object var2 = this.zai;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label572: {
         ArrayList var3;
         int var4;
         int var5;
         try {
            var3 = new ArrayList(this.zad);
            var4 = this.zaf.get();
            var3 = (ArrayList)var3;
            var5 = var3.size();
         } catch (Throwable var64) {
            var10000 = var64;
            var10001 = false;
            break label572;
         }

         int var6 = 0;

         while(true) {
            if (var6 >= var5) {
               try {
                  return;
               } catch (Throwable var60) {
                  var10000 = var60;
                  var10001 = false;
                  break;
               }
            }

            Object var7;
            try {
               var7 = var3.get(var6);
            } catch (Throwable var61) {
               var10000 = var61;
               var10001 = false;
               break;
            }

            int var8 = var6 + 1;

            GoogleApiClient.OnConnectionFailedListener var66;
            label574: {
               try {
                  var66 = (GoogleApiClient.OnConnectionFailedListener)var7;
                  if (this.zae && this.zaf.get() == var4) {
                     break label574;
                  }
               } catch (Throwable var63) {
                  var10000 = var63;
                  var10001 = false;
                  break;
               }

               try {
                  return;
               } catch (Throwable var59) {
                  var10000 = var59;
                  var10001 = false;
                  break;
               }
            }

            var6 = var8;

            try {
               if (!this.zad.contains(var66)) {
                  continue;
               }

               var66.onConnectionFailed(var1);
            } catch (Throwable var62) {
               var10000 = var62;
               var10001 = false;
               break;
            }

            var6 = var8;
         }
      }

      while(true) {
         Throwable var65 = var10000;

         try {
            throw var65;
         } catch (Throwable var58) {
            var10000 = var58;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zaa(GoogleApiClient.ConnectionCallbacks var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = this.zai;
      synchronized(var2){}

      label229: {
         Throwable var10000;
         boolean var10001;
         label230: {
            label222: {
               try {
                  if (this.zab.contains(var1)) {
                     String var3 = String.valueOf(var1);
                     int var4 = String.valueOf(var3).length();
                     StringBuilder var5 = new StringBuilder(var4 + 62);
                     var5.append("registerConnectionCallbacks(): listener ");
                     var5.append(var3);
                     var5.append(" is already registered");
                     Log.w("GmsClientEvents", var5.toString());
                     break label222;
                  }
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break label230;
               }

               try {
                  this.zab.add(var1);
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label230;
               }
            }

            label213:
            try {
               break label229;
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label213;
            }
         }

         while(true) {
            Throwable var26 = var10000;

            try {
               throw var26;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               continue;
            }
         }
      }

      if (this.zaa.isConnected()) {
         Handler var27 = this.zah;
         var27.sendMessage(var27.obtainMessage(1, var1));
      }

   }

   public final void zaa(GoogleApiClient.OnConnectionFailedListener var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = this.zai;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label195: {
         label189: {
            try {
               if (this.zad.contains(var1)) {
                  String var3 = String.valueOf(var1);
                  int var4 = String.valueOf(var3).length();
                  StringBuilder var25 = new StringBuilder(var4 + 67);
                  var25.append("registerConnectionFailedListener(): listener ");
                  var25.append(var3);
                  var25.append(" is already registered");
                  Log.w("GmsClientEvents", var25.toString());
                  break label189;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label195;
            }

            try {
               this.zad.add(var1);
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label195;
            }
         }

         label180:
         try {
            return;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label180;
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zab() {
      this.zae = true;
   }

   public final boolean zab(GoogleApiClient.ConnectionCallbacks param1) {
      // $FF: Couldn't be decompiled
   }

   public final boolean zab(GoogleApiClient.OnConnectionFailedListener param1) {
      // $FF: Couldn't be decompiled
   }

   public final void zac(GoogleApiClient.ConnectionCallbacks var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = this.zai;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label200: {
         label204: {
            try {
               if (!this.zab.remove(var1)) {
                  String var25 = String.valueOf(var1);
                  int var3 = String.valueOf(var25).length();
                  StringBuilder var4 = new StringBuilder(var3 + 52);
                  var4.append("unregisterConnectionCallbacks(): listener ");
                  var4.append(var25);
                  var4.append(" not found");
                  Log.w("GmsClientEvents", var4.toString());
                  break label204;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label200;
            }

            try {
               if (this.zag) {
                  this.zac.add(var1);
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label200;
            }
         }

         label190:
         try {
            return;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label190;
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zac(GoogleApiClient.OnConnectionFailedListener var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = this.zai;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (!this.zad.remove(var1)) {
               String var17 = String.valueOf(var1);
               int var3 = String.valueOf(var17).length();
               StringBuilder var4 = new StringBuilder(var3 + 57);
               var4.append("unregisterConnectionFailedListener(): listener ");
               var4.append(var17);
               var4.append(" not found");
               Log.w("GmsClientEvents", var4.toString());
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var18 = var10000;

         try {
            throw var18;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            continue;
         }
      }
   }
}
