package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.BooleanIterator;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lkotlin/jvm/internal/ArrayBooleanIterator;", "Lkotlin/collections/BooleanIterator;", "array", "", "([Z)V", "index", "", "hasNext", "", "nextBoolean", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class ArrayBooleanIterator extends BooleanIterator {
   private final boolean[] array;
   private int index;

   public ArrayBooleanIterator(boolean[] var1) {
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

   public boolean nextBoolean() {
      boolean[] var1;
      int var2;
      try {
         var1 = this.array;
         var2 = this.index++;
      } catch (ArrayIndexOutOfBoundsException var4) {
         --this.index;
         throw (Throwable)(new NoSuchElementException(var4.getMessage()));
      }

      boolean var3 = var1[var2];
      return var3;
   }
}
