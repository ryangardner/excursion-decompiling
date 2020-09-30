package com.google.android.gms.internal.drive;

public class zzkx {
   private static final zzjx zzng = zzjx.zzci();
   private zzjc zzth;
   private volatile zzlq zzti;
   private volatile zzjc zztj;

   private final zzlq zzh(zzlq param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zzkx)) {
         return false;
      } else {
         zzkx var2 = (zzkx)var1;
         zzlq var3 = this.zzti;
         zzlq var4 = var2.zzti;
         if (var3 == null && var4 == null) {
            return this.zzbl().equals(var2.zzbl());
         } else if (var3 != null && var4 != null) {
            return var3.equals(var4);
         } else {
            return var3 != null ? var3.equals(var2.zzh(var3.zzda())) : this.zzh(var4.zzda()).equals(var4);
         }
      }
   }

   public int hashCode() {
      return 1;
   }

   public final zzjc zzbl() {
      if (this.zztj != null) {
         return this.zztj;
      } else {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label305: {
            zzjc var32;
            try {
               if (this.zztj != null) {
                  var32 = this.zztj;
                  return var32;
               }
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break label305;
            }

            label307: {
               try {
                  if (this.zzti == null) {
                     this.zztj = zzjc.zznq;
                     break label307;
                  }
               } catch (Throwable var30) {
                  var10000 = var30;
                  var10001 = false;
                  break label305;
               }

               try {
                  this.zztj = this.zzti.zzbl();
               } catch (Throwable var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label305;
               }
            }

            label286:
            try {
               var32 = this.zztj;
               return var32;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break label286;
            }
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var27) {
               var10000 = var27;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public final int zzcx() {
      if (this.zztj != null) {
         return this.zztj.size();
      } else {
         return this.zzti != null ? this.zzti.zzcx() : 0;
      }
   }

   public final zzlq zzi(zzlq var1) {
      zzlq var2 = this.zzti;
      this.zzth = null;
      this.zztj = null;
      this.zzti = var1;
      return var2;
   }
}
