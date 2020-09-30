package com.google.common.cache;

import com.google.common.base.Supplier;
import java.util.concurrent.atomic.AtomicLong;

final class LongAddables {
   private static final Supplier<LongAddable> SUPPLIER;

   static {
      boolean var2 = false;

      Supplier var0;
      label21:
      try {
         var2 = true;
         new LongAdder();
         var0 = new Supplier<LongAddable>() {
            public LongAddable get() {
               return new LongAdder();
            }
         };
         var2 = false;
      } finally {
         if (var2) {
            var0 = new Supplier<LongAddable>() {
               public LongAddable get() {
                  return new LongAddables.PureJavaLongAddable();
               }
            };
            break label21;
         }
      }

      SUPPLIER = var0;
   }

   public static LongAddable create() {
      return (LongAddable)SUPPLIER.get();
   }

   private static final class PureJavaLongAddable extends AtomicLong implements LongAddable {
      private PureJavaLongAddable() {
      }

      // $FF: synthetic method
      PureJavaLongAddable(Object var1) {
         this();
      }

      public void add(long var1) {
         this.getAndAdd(var1);
      }

      public void increment() {
         this.getAndIncrement();
      }

      public long sum() {
         return this.get();
      }
   }
}
