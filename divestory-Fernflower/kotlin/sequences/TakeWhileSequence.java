package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0096\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lkotlin/sequences/TakeWhileSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "predicate", "Lkotlin/Function1;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class TakeWhileSequence<T> implements Sequence<T> {
   private final Function1<T, Boolean> predicate;
   private final Sequence<T> sequence;

   public TakeWhileSequence(Sequence<? extends T> var1, Function1<? super T, Boolean> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sequence");
      Intrinsics.checkParameterIsNotNull(var2, "predicate");
      super();
      this.sequence = var1;
      this.predicate = var2;
   }

   public Iterator<T> iterator() {
      return (Iterator)(new Iterator<T>() {
         private final Iterator<T> iterator;
         private T nextItem;
         private int nextState;

         {
            this.iterator = TakeWhileSequence.this.sequence.iterator();
            this.nextState = -1;
         }

         private final void calcNext() {
            if (this.iterator.hasNext()) {
               Object var1 = this.iterator.next();
               if ((Boolean)TakeWhileSequence.this.predicate.invoke(var1)) {
                  this.nextState = 1;
                  this.nextItem = var1;
                  return;
               }
            }

            this.nextState = 0;
         }

         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public final T getNextItem() {
            return this.nextItem;
         }

         public final int getNextState() {
            return this.nextState;
         }

         public boolean hasNext() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            int var1 = this.nextState;
            boolean var2 = true;
            if (var1 != 1) {
               var2 = false;
            }

            return var2;
         }

         public T next() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            if (this.nextState != 0) {
               Object var1 = this.nextItem;
               this.nextItem = null;
               this.nextState = -1;
               return var1;
            } else {
               throw (Throwable)(new NoSuchElementException());
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setNextItem(T var1) {
            this.nextItem = var1;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }
      });
   }
}
