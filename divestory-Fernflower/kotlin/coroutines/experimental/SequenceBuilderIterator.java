package kotlin.coroutines.experimental;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\t\u0010\u0018\u001a\u00020\u0019H\u0096\u0002J\u000e\u0010\u001a\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001bJ\r\u0010\u001c\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001bJ\u0015\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u001fJ\u0010\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0017H\u0016J\u0019\u0010\"\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010#J\u001f\u0010$\u001a\u00020\u00052\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010&R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0012R\u0012\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\t¨\u0006'"},
   d2 = {"Lkotlin/coroutines/experimental/SequenceBuilderIterator;", "T", "Lkotlin/coroutines/experimental/SequenceBuilder;", "", "Lkotlin/coroutines/experimental/Continuation;", "", "()V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "nextIterator", "nextStep", "getNextStep", "()Lkotlin/coroutines/experimental/Continuation;", "setNextStep", "(Lkotlin/coroutines/experimental/Continuation;)V", "nextValue", "Ljava/lang/Object;", "state", "", "Lkotlin/coroutines/experimental/State;", "exceptionalState", "", "hasNext", "", "next", "()Ljava/lang/Object;", "nextNotReady", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "yield", "(Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "yieldAll", "iterator", "(Ljava/util/Iterator;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"},
   k = 1,
   mv = {1, 1, 16}
)
final class SequenceBuilderIterator<T> extends SequenceBuilder<T> implements Iterator<T>, Continuation<Unit>, KMappedMarker {
   private Iterator<? extends T> nextIterator;
   private Continuation<? super Unit> nextStep;
   private T nextValue;
   private int state;

   public SequenceBuilderIterator() {
   }

   private final Throwable exceptionalState() {
      int var1 = this.state;
      Throwable var3;
      if (var1 != 4) {
         if (var1 != 5) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Unexpected state of the iterator: ");
            var2.append(this.state);
            var3 = (Throwable)(new IllegalStateException(var2.toString()));
         } else {
            var3 = (Throwable)(new IllegalStateException("Iterator has failed."));
         }
      } else {
         var3 = (Throwable)(new NoSuchElementException());
      }

      return var3;
   }

   private final T nextNotReady() {
      if (this.hasNext()) {
         return this.next();
      } else {
         throw (Throwable)(new NoSuchElementException());
      }
   }

   public CoroutineContext getContext() {
      return (CoroutineContext)EmptyCoroutineContext.INSTANCE;
   }

   public final Continuation<Unit> getNextStep() {
      return this.nextStep;
   }

   public boolean hasNext() {
      while(true) {
         int var1 = this.state;
         if (var1 != 0) {
            if (var1 != 1) {
               if (var1 != 2 && var1 != 3) {
                  if (var1 == 4) {
                     return false;
                  }

                  throw this.exceptionalState();
               }

               return true;
            }

            Iterator var2 = this.nextIterator;
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            if (var2.hasNext()) {
               this.state = 2;
               return true;
            }

            this.nextIterator = (Iterator)null;
         }

         this.state = 5;
         Continuation var3 = this.nextStep;
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         this.nextStep = (Continuation)null;
         var3.resume(Unit.INSTANCE);
      }
   }

   public T next() {
      int var1 = this.state;
      if (var1 != 0 && var1 != 1) {
         if (var1 != 2) {
            if (var1 == 3) {
               this.state = 0;
               Object var3 = this.nextValue;
               this.nextValue = null;
               return var3;
            } else {
               throw this.exceptionalState();
            }
         } else {
            this.state = 1;
            Iterator var2 = this.nextIterator;
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            return var2.next();
         }
      } else {
         return this.nextNotReady();
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void resume(Unit var1) {
      Intrinsics.checkParameterIsNotNull(var1, "value");
      this.state = 4;
   }

   public void resumeWithException(Throwable var1) {
      Intrinsics.checkParameterIsNotNull(var1, "exception");
      throw var1;
   }

   public final void setNextStep(Continuation<? super Unit> var1) {
      this.nextStep = var1;
   }

   public Object yield(T var1, Continuation<? super Unit> var2) {
      this.nextValue = var1;
      this.state = 3;
      this.setNextStep(CoroutineIntrinsics.normalizeContinuation(var2));
      return IntrinsicsKt.getCOROUTINE_SUSPENDED();
   }

   public Object yieldAll(Iterator<? extends T> var1, Continuation<? super Unit> var2) {
      if (!var1.hasNext()) {
         return Unit.INSTANCE;
      } else {
         this.nextIterator = var1;
         this.state = 2;
         this.setNextStep(CoroutineIntrinsics.normalizeContinuation(var2));
         return IntrinsicsKt.getCOROUTINE_SUSPENDED();
      }
   }
}
