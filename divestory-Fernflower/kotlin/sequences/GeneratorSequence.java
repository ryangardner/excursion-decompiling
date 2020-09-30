package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B+\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u0096\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/sequences/GeneratorSequence;", "T", "", "Lkotlin/sequences/Sequence;", "getInitialValue", "Lkotlin/Function0;", "getNextValue", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class GeneratorSequence<T> implements Sequence<T> {
   private final Function0<T> getInitialValue;
   private final Function1<T, T> getNextValue;

   public GeneratorSequence(Function0<? extends T> var1, Function1<? super T, ? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "getInitialValue");
      Intrinsics.checkParameterIsNotNull(var2, "getNextValue");
      super();
      this.getInitialValue = var1;
      this.getNextValue = var2;
   }

   public Iterator<T> iterator() {
      return (Iterator)(new Iterator<T>() {
         private T nextItem;
         private int nextState = -2;

         private final void calcNext() {
            Object var1;
            if (this.nextState == -2) {
               var1 = GeneratorSequence.this.getInitialValue.invoke();
            } else {
               Function1 var4 = GeneratorSequence.this.getNextValue;
               Object var2 = this.nextItem;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               var1 = var4.invoke(var2);
            }

            this.nextItem = var1;
            byte var3;
            if (var1 == null) {
               var3 = 0;
            } else {
               var3 = 1;
            }

            this.nextState = var3;
         }

         public final T getNextItem() {
            return this.nextItem;
         }

         public final int getNextState() {
            return this.nextState;
         }

         public boolean hasNext() {
            if (this.nextState < 0) {
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
            if (this.nextState < 0) {
               this.calcNext();
            }

            if (this.nextState != 0) {
               Object var1 = this.nextItem;
               if (var1 != null) {
                  this.nextState = -1;
                  return var1;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type T");
               }
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
