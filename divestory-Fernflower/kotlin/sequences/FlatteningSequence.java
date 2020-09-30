package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u0004BA\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\tH\u0096\u0002R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/sequences/FlatteningSequence;", "T", "R", "E", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function1;", "iterator", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class FlatteningSequence<T, R, E> implements Sequence<E> {
   private final Function1<R, Iterator<E>> iterator;
   private final Sequence<T> sequence;
   private final Function1<T, R> transformer;

   public FlatteningSequence(Sequence<? extends T> var1, Function1<? super T, ? extends R> var2, Function1<? super R, ? extends Iterator<? extends E>> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sequence");
      Intrinsics.checkParameterIsNotNull(var2, "transformer");
      Intrinsics.checkParameterIsNotNull(var3, "iterator");
      super();
      this.sequence = var1;
      this.transformer = var2;
      this.iterator = var3;
   }

   public Iterator<E> iterator() {
      return (Iterator)(new Iterator<E>() {
         private Iterator<? extends E> itemIterator;
         private final Iterator<T> iterator;

         {
            this.iterator = FlatteningSequence.this.sequence.iterator();
         }

         private final boolean ensureItemIterator() {
            Iterator var1 = this.itemIterator;
            if (var1 != null && !var1.hasNext()) {
               this.itemIterator = (Iterator)null;
            }

            while(this.itemIterator == null) {
               if (!this.iterator.hasNext()) {
                  return false;
               }

               Object var2 = this.iterator.next();
               var1 = (Iterator)FlatteningSequence.this.iterator.invoke(FlatteningSequence.this.transformer.invoke(var2));
               if (var1.hasNext()) {
                  this.itemIterator = var1;
                  break;
               }
            }

            return true;
         }

         public final Iterator<E> getItemIterator() {
            return this.itemIterator;
         }

         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public boolean hasNext() {
            return this.ensureItemIterator();
         }

         public E next() {
            if (this.ensureItemIterator()) {
               Iterator var1 = this.itemIterator;
               if (var1 == null) {
                  Intrinsics.throwNpe();
               }

               return var1.next();
            } else {
               throw (Throwable)(new NoSuchElementException());
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setItemIterator(Iterator<? extends E> var1) {
            this.itemIterator = var1;
         }
      });
   }
}
