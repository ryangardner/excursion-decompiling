package androidx.constraintlayout.solver;

final class Pools {
   private static final boolean DEBUG = false;

   private Pools() {
   }

   interface Pool<T> {
      T acquire();

      boolean release(T var1);

      void releaseAll(T[] var1, int var2);
   }

   static class SimplePool<T> implements Pools.Pool<T> {
      private final Object[] mPool;
      private int mPoolSize;

      SimplePool(int var1) {
         if (var1 > 0) {
            this.mPool = new Object[var1];
         } else {
            throw new IllegalArgumentException("The max pool size must be > 0");
         }
      }

      private boolean isInPool(T var1) {
         for(int var2 = 0; var2 < this.mPoolSize; ++var2) {
            if (this.mPool[var2] == var1) {
               return true;
            }
         }

         return false;
      }

      public T acquire() {
         int var1 = this.mPoolSize;
         if (var1 > 0) {
            int var2 = var1 - 1;
            Object[] var3 = this.mPool;
            Object var4 = var3[var2];
            var3[var2] = null;
            this.mPoolSize = var1 - 1;
            return var4;
         } else {
            return null;
         }
      }

      public boolean release(T var1) {
         int var2 = this.mPoolSize;
         Object[] var3 = this.mPool;
         if (var2 < var3.length) {
            var3[var2] = var1;
            this.mPoolSize = var2 + 1;
            return true;
         } else {
            return false;
         }
      }

      public void releaseAll(T[] var1, int var2) {
         int var3 = var2;
         if (var2 > var1.length) {
            var3 = var1.length;
         }

         for(var2 = 0; var2 < var3; ++var2) {
            Object var4 = var1[var2];
            int var5 = this.mPoolSize;
            Object[] var6 = this.mPool;
            if (var5 < var6.length) {
               var6[var5] = var4;
               this.mPoolSize = var5 + 1;
            }
         }

      }
   }
}
