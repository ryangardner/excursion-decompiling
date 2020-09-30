package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {
   private static final long serialVersionUID = 0L;
   transient ObjectCountHashMap<E> backingMap;
   transient long size;

   AbstractMapBasedMultiset(int var1) {
      this.init(var1);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = Serialization.readCount(var1);
      this.init(3);
      Serialization.populateMultiset(this, var1, var2);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultiset(this, var1);
   }

   public final int add(@NullableDecl E var1, int var2) {
      if (var2 == 0) {
         return this.count(var1);
      } else {
         boolean var3 = true;
         boolean var4;
         if (var2 > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "occurrences cannot be negative: %s", var2);
         int var5 = this.backingMap.indexOf(var1);
         if (var5 == -1) {
            this.backingMap.put(var1, var2);
            this.size += (long)var2;
            return 0;
         } else {
            int var6 = this.backingMap.getValue(var5);
            long var7 = (long)var6;
            long var9 = (long)var2;
            var7 += var9;
            if (var7 <= 2147483647L) {
               var4 = var3;
            } else {
               var4 = false;
            }

            Preconditions.checkArgument(var4, "too many occurrences: %s", var7);
            this.backingMap.setValue(var5, (int)var7);
            this.size += var9;
            return var6;
         }
      }
   }

   void addTo(Multiset<? super E> var1) {
      Preconditions.checkNotNull(var1);

      for(int var2 = this.backingMap.firstIndex(); var2 >= 0; var2 = this.backingMap.nextIndex(var2)) {
         var1.add(this.backingMap.getKey(var2), this.backingMap.getValue(var2));
      }

   }

   public final void clear() {
      this.backingMap.clear();
      this.size = 0L;
   }

   public final int count(@NullableDecl Object var1) {
      return this.backingMap.get(var1);
   }

   final int distinctElements() {
      return this.backingMap.size();
   }

   final Iterator<E> elementIterator() {
      return new AbstractMapBasedMultiset<E>.Itr<E>() {
         E result(int var1) {
            return AbstractMapBasedMultiset.this.backingMap.getKey(var1);
         }
      };
   }

   final Iterator<Multiset.Entry<E>> entryIterator() {
      return new AbstractMapBasedMultiset<E>.Itr<Multiset.Entry<E>>() {
         Multiset.Entry<E> result(int var1) {
            return AbstractMapBasedMultiset.this.backingMap.getEntry(var1);
         }
      };
   }

   abstract void init(int var1);

   public final Iterator<E> iterator() {
      return Multisets.iteratorImpl(this);
   }

   public final int remove(@NullableDecl Object var1, int var2) {
      if (var2 == 0) {
         return this.count(var1);
      } else {
         boolean var3;
         if (var2 > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "occurrences cannot be negative: %s", var2);
         int var4 = this.backingMap.indexOf(var1);
         if (var4 == -1) {
            return 0;
         } else {
            int var5 = this.backingMap.getValue(var4);
            if (var5 > var2) {
               this.backingMap.setValue(var4, var5 - var2);
            } else {
               this.backingMap.removeEntry(var4);
               var2 = var5;
            }

            this.size -= (long)var2;
            return var5;
         }
      }
   }

   public final int setCount(@NullableDecl E var1, int var2) {
      CollectPreconditions.checkNonnegative(var2, "count");
      ObjectCountHashMap var3 = this.backingMap;
      int var4;
      if (var2 == 0) {
         var4 = var3.remove(var1);
      } else {
         var4 = var3.put(var1, var2);
      }

      this.size += (long)(var2 - var4);
      return var4;
   }

   public final boolean setCount(@NullableDecl E var1, int var2, int var3) {
      CollectPreconditions.checkNonnegative(var2, "oldCount");
      CollectPreconditions.checkNonnegative(var3, "newCount");
      int var4 = this.backingMap.indexOf(var1);
      if (var4 == -1) {
         if (var2 != 0) {
            return false;
         } else {
            if (var3 > 0) {
               this.backingMap.put(var1, var3);
               this.size += (long)var3;
            }

            return true;
         }
      } else if (this.backingMap.getValue(var4) != var2) {
         return false;
      } else {
         if (var3 == 0) {
            this.backingMap.removeEntry(var4);
            this.size -= (long)var2;
         } else {
            this.backingMap.setValue(var4, var3);
            this.size += (long)(var3 - var2);
         }

         return true;
      }
   }

   public final int size() {
      return Ints.saturatedCast(this.size);
   }

   abstract class Itr<T> implements Iterator<T> {
      int entryIndex;
      int expectedModCount;
      int toRemove;

      Itr() {
         this.entryIndex = AbstractMapBasedMultiset.this.backingMap.firstIndex();
         this.toRemove = -1;
         this.expectedModCount = AbstractMapBasedMultiset.this.backingMap.modCount;
      }

      private void checkForConcurrentModification() {
         if (AbstractMapBasedMultiset.this.backingMap.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      public boolean hasNext() {
         this.checkForConcurrentModification();
         boolean var1;
         if (this.entryIndex >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public T next() {
         if (this.hasNext()) {
            Object var1 = this.result(this.entryIndex);
            this.toRemove = this.entryIndex;
            this.entryIndex = AbstractMapBasedMultiset.this.backingMap.nextIndex(this.entryIndex);
            return var1;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         this.checkForConcurrentModification();
         boolean var1;
         if (this.toRemove != -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         CollectPreconditions.checkRemove(var1);
         AbstractMapBasedMultiset var2 = AbstractMapBasedMultiset.this;
         var2.size -= (long)AbstractMapBasedMultiset.this.backingMap.removeEntry(this.toRemove);
         this.entryIndex = AbstractMapBasedMultiset.this.backingMap.nextIndexAfterRemove(this.entryIndex, this.toRemove);
         this.toRemove = -1;
         this.expectedModCount = AbstractMapBasedMultiset.this.backingMap.modCount;
      }

      abstract T result(int var1);
   }
}
