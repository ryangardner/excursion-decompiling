package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public final class EvictingQueue<E> extends ForwardingQueue<E> implements Serializable {
   private static final long serialVersionUID = 0L;
   private final Queue<E> delegate;
   final int maxSize;

   private EvictingQueue(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "maxSize (%s) must >= 0", var1);
      this.delegate = new ArrayDeque(var1);
      this.maxSize = var1;
   }

   public static <E> EvictingQueue<E> create(int var0) {
      return new EvictingQueue(var0);
   }

   public boolean add(E var1) {
      Preconditions.checkNotNull(var1);
      if (this.maxSize == 0) {
         return true;
      } else {
         if (this.size() == this.maxSize) {
            this.delegate.remove();
         }

         this.delegate.add(var1);
         return true;
      }
   }

   public boolean addAll(Collection<? extends E> var1) {
      int var2 = var1.size();
      if (var2 >= this.maxSize) {
         this.clear();
         return Iterables.addAll(this, Iterables.skip(var1, var2 - this.maxSize));
      } else {
         return this.standardAddAll(var1);
      }
   }

   public boolean contains(Object var1) {
      return this.delegate().contains(Preconditions.checkNotNull(var1));
   }

   protected Queue<E> delegate() {
      return this.delegate;
   }

   public boolean offer(E var1) {
      return this.add(var1);
   }

   public int remainingCapacity() {
      return this.maxSize - this.size();
   }

   public boolean remove(Object var1) {
      return this.delegate().remove(Preconditions.checkNotNull(var1));
   }
}
