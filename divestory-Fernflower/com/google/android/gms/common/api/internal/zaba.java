package com.google.android.gms.common.api.internal;

abstract class zaba {
   private final zaay zaa;

   protected zaba(zaay var1) {
      this.zaa = var1;
   }

   protected abstract void zaa();

   public final void zaa(zaax var1) {
      zaax.zaa(var1).lock();

      Throwable var10000;
      label78: {
         boolean var10001;
         zaay var2;
         zaay var3;
         try {
            var2 = zaax.zab(var1);
            var3 = this.zaa;
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label78;
         }

         if (var2 != var3) {
            zaax.zaa(var1).unlock();
            return;
         }

         try {
            this.zaa();
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label78;
         }

         zaax.zaa(var1).unlock();
         return;
      }

      Throwable var10 = var10000;
      zaax.zaa(var1).unlock();
      throw var10;
   }
}
