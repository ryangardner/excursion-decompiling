package com.google.common.base;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractIterator<T> implements Iterator<T> {
   @NullableDecl
   private T next;
   private AbstractIterator.State state;

   protected AbstractIterator() {
      this.state = AbstractIterator.State.NOT_READY;
   }

   private boolean tryToComputeNext() {
      this.state = AbstractIterator.State.FAILED;
      this.next = this.computeNext();
      if (this.state != AbstractIterator.State.DONE) {
         this.state = AbstractIterator.State.READY;
         return true;
      } else {
         return false;
      }
   }

   protected abstract T computeNext();

   @NullableDecl
   protected final T endOfData() {
      this.state = AbstractIterator.State.DONE;
      return null;
   }

   public final boolean hasNext() {
      boolean var1;
      if (this.state != AbstractIterator.State.FAILED) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1);
      int var2 = null.$SwitchMap$com$google$common$base$AbstractIterator$State[this.state.ordinal()];
      if (var2 != 1) {
         return var2 != 2 ? this.tryToComputeNext() : true;
      } else {
         return false;
      }
   }

   public final T next() {
      if (this.hasNext()) {
         this.state = AbstractIterator.State.NOT_READY;
         Object var1 = this.next;
         this.next = null;
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   public final void remove() {
      throw new UnsupportedOperationException();
   }

   private static enum State {
      DONE,
      FAILED,
      NOT_READY,
      READY;

      static {
         AbstractIterator.State var0 = new AbstractIterator.State("FAILED", 3);
         FAILED = var0;
      }
   }
}
