package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.collections.UIntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0003\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u000bH\u0096\u0002J\u0010\u0010\r\u001a\u00020\u0003H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000eR\u0013\u0010\b\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\f\u001a\u00020\u0003X\u0082\u000eø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\u0005\u001a\u00020\u0003X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"},
   d2 = {"Lkotlin/ranges/UIntProgressionIterator;", "Lkotlin/collections/UIntIterator;", "first", "Lkotlin/UInt;", "last", "step", "", "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "finalElement", "I", "hasNext", "", "next", "nextUInt", "()I", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class UIntProgressionIterator extends UIntIterator {
   private final int finalElement;
   private boolean hasNext;
   private int next;
   private final int step;

   private UIntProgressionIterator(int var1, int var2, int var3) {
      boolean var4;
      label20: {
         super();
         this.finalElement = var2;
         var4 = true;
         var2 = UnsignedKt.uintCompare(var1, var2);
         if (var3 > 0) {
            if (var2 <= 0) {
               break label20;
            }
         } else if (var2 >= 0) {
            break label20;
         }

         var4 = false;
      }

      this.hasNext = var4;
      this.step = UInt.constructor-impl(var3);
      if (!this.hasNext) {
         var1 = this.finalElement;
      }

      this.next = var1;
   }

   // $FF: synthetic method
   public UIntProgressionIterator(int var1, int var2, int var3, DefaultConstructorMarker var4) {
      this(var1, var2, var3);
   }

   public boolean hasNext() {
      return this.hasNext;
   }

   public int nextUInt() {
      int var1 = this.next;
      if (var1 == this.finalElement) {
         if (!this.hasNext) {
            throw (Throwable)(new NoSuchElementException());
         }

         this.hasNext = false;
      } else {
         this.next = UInt.constructor-impl(this.step + var1);
      }

      return var1;
   }
}
