package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

class ConsumingQueueIterator<T> extends AbstractIterator<T> {
   private final Queue<T> queue;

   ConsumingQueueIterator(Queue<T> var1) {
      this.queue = (Queue)Preconditions.checkNotNull(var1);
   }

   ConsumingQueueIterator(T... var1) {
      ArrayDeque var2 = new ArrayDeque(var1.length);
      this.queue = var2;
      Collections.addAll(var2, var1);
   }

   public T computeNext() {
      Object var1;
      if (this.queue.isEmpty()) {
         var1 = this.endOfData();
      } else {
         var1 = this.queue.remove();
      }

      return var1;
   }
}
