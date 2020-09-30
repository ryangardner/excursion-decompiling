package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0016J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0096\u0002J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0016R\u0014\u0010\t\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lkotlin/sequences/SubSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "startIndex", "", "endIndex", "(Lkotlin/sequences/Sequence;II)V", "count", "getCount", "()I", "drop", "n", "iterator", "", "take", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class SubSequence<T> implements Sequence<T>, DropTakeSequence<T> {
   private final int endIndex;
   private final Sequence<T> sequence;
   private final int startIndex;

   public SubSequence(Sequence<? extends T> var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sequence");
      super();
      this.sequence = var1;
      this.startIndex = var2;
      this.endIndex = var3;
      boolean var6 = true;
      boolean var5;
      if (var2 >= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      StringBuilder var4;
      if (var5) {
         if (this.endIndex >= 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            if (this.endIndex >= this.startIndex) {
               var5 = var6;
            } else {
               var5 = false;
            }

            if (!var5) {
               var4 = new StringBuilder();
               var4.append("endIndex should be not less than startIndex, but was ");
               var4.append(this.endIndex);
               var4.append(" < ");
               var4.append(this.startIndex);
               throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
            }
         } else {
            var4 = new StringBuilder();
            var4.append("endIndex should be non-negative, but is ");
            var4.append(this.endIndex);
            throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
         }
      } else {
         var4 = new StringBuilder();
         var4.append("startIndex should be non-negative, but is ");
         var4.append(this.startIndex);
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   private final int getCount() {
      return this.endIndex - this.startIndex;
   }

   public Sequence<T> drop(int var1) {
      Sequence var2;
      if (var1 >= this.getCount()) {
         var2 = SequencesKt.emptySequence();
      } else {
         var2 = (Sequence)(new SubSequence(this.sequence, this.startIndex + var1, this.endIndex));
      }

      return var2;
   }

   public Iterator<T> iterator() {
      return (Iterator)(new Iterator<T>() {
         private final Iterator<T> iterator;
         private int position;

         {
            this.iterator = SubSequence.this.sequence.iterator();
         }

         private final void drop() {
            while(this.position < SubSequence.this.startIndex && this.iterator.hasNext()) {
               this.iterator.next();
               ++this.position;
            }

         }

         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getPosition() {
            return this.position;
         }

         public boolean hasNext() {
            this.drop();
            boolean var1;
            if (this.position < SubSequence.this.endIndex && this.iterator.hasNext()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public T next() {
            this.drop();
            if (this.position < SubSequence.this.endIndex) {
               ++this.position;
               return this.iterator.next();
            } else {
               throw (Throwable)(new NoSuchElementException());
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setPosition(int var1) {
            this.position = var1;
         }
      });
   }

   public Sequence<T> take(int var1) {
      Sequence var2;
      if (var1 >= this.getCount()) {
         var2 = (Sequence)this;
      } else {
         var2 = this.sequence;
         int var3 = this.startIndex;
         var2 = (Sequence)(new SubSequence(var2, var3, var1 + var3));
      }

      return var2;
   }
}
