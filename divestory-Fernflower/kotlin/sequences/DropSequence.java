package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0096\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/sequences/DropSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class DropSequence<T> implements Sequence<T>, DropTakeSequence<T> {
   private final int count;
   private final Sequence<T> sequence;

   public DropSequence(Sequence<? extends T> var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sequence");
      super();
      this.sequence = var1;
      this.count = var2;
      boolean var4;
      if (var2 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append("count must be non-negative, but was ");
         var3.append(this.count);
         var3.append('.');
         throw (Throwable)(new IllegalArgumentException(var3.toString().toString()));
      }
   }

   public Sequence<T> drop(int var1) {
      int var2 = this.count + var1;
      Sequence var3;
      if (var2 < 0) {
         var3 = (Sequence)(new DropSequence((Sequence)this, var1));
      } else {
         var3 = (Sequence)(new DropSequence(this.sequence, var2));
      }

      return var3;
   }

   public Iterator<T> iterator() {
      return (Iterator)(new Iterator<T>() {
         private final Iterator<T> iterator;
         private int left;

         {
            this.iterator = DropSequence.this.sequence.iterator();
            this.left = DropSequence.this.count;
         }

         private final void drop() {
            while(this.left > 0 && this.iterator.hasNext()) {
               this.iterator.next();
               --this.left;
            }

         }

         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getLeft() {
            return this.left;
         }

         public boolean hasNext() {
            this.drop();
            return this.iterator.hasNext();
         }

         public T next() {
            this.drop();
            return this.iterator.next();
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setLeft(int var1) {
            this.left = var1;
         }
      });
   }

   public Sequence<T> take(int var1) {
      int var2 = this.count + var1;
      Sequence var3;
      if (var2 < 0) {
         var3 = (Sequence)(new TakeSequence((Sequence)this, var1));
      } else {
         var3 = (Sequence)(new SubSequence(this.sequence, this.count, var2));
      }

      return var3;
   }
}
