package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.DoubleIterator;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0013\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/jvm/internal/ArrayDoubleIterator;", "Lkotlin/collections/DoubleIterator;", "array", "", "([D)V", "index", "", "hasNext", "", "nextDouble", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class ArrayDoubleIterator extends DoubleIterator {
   private final double[] array;
   private int index;

   public ArrayDoubleIterator(double[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "array");
      super();
      this.array = var1;
   }

   public boolean hasNext() {
      boolean var1;
      if (this.index < this.array.length) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public double nextDouble() {
      double[] var1;
      int var2;
      try {
         var1 = this.array;
         var2 = this.index++;
      } catch (ArrayIndexOutOfBoundsException var5) {
         --this.index;
         throw (Throwable)(new NoSuchElementException(var5.getMessage()));
      }

      double var3 = var1[var2];
      return var3;
   }
}
