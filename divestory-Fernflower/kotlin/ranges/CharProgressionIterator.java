package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CharIterator;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\t\u001a\u00020\nH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u0003H\u0016R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000f"},
   d2 = {"Lkotlin/ranges/CharProgressionIterator;", "Lkotlin/collections/CharIterator;", "first", "", "last", "step", "", "(CCI)V", "finalElement", "hasNext", "", "next", "getStep", "()I", "nextChar", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CharProgressionIterator extends CharIterator {
   private final int finalElement;
   private boolean hasNext;
   private int next;
   private final int step;

   public CharProgressionIterator(char var1, char var2, int var3) {
      boolean var4;
      label20: {
         super();
         this.step = var3;
         this.finalElement = var2;
         var4 = true;
         if (var3 > 0) {
            if (var1 <= var2) {
               break label20;
            }
         } else if (var1 >= var2) {
            break label20;
         }

         var4 = false;
      }

      this.hasNext = var4;
      if (!var4) {
         var1 = this.finalElement;
      }

      this.next = var1;
   }

   public final int getStep() {
      return this.step;
   }

   public boolean hasNext() {
      return this.hasNext;
   }

   public char nextChar() {
      int var1 = this.next;
      if (var1 == this.finalElement) {
         if (!this.hasNext) {
            throw (Throwable)(new NoSuchElementException());
         }

         this.hasNext = false;
      } else {
         this.next = this.step + var1;
      }

      return (char)var1;
   }
}
