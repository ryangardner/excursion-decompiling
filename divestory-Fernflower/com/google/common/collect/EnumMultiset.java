package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class EnumMultiset<E extends Enum<E>> extends AbstractMultiset<E> implements Serializable {
   private static final long serialVersionUID = 0L;
   private transient int[] counts;
   private transient int distinctElements;
   private transient E[] enumConstants;
   private transient long size;
   private transient Class<E> type;

   private EnumMultiset(Class<E> var1) {
      this.type = var1;
      Preconditions.checkArgument(var1.isEnum());
      Enum[] var2 = (Enum[])var1.getEnumConstants();
      this.enumConstants = var2;
      this.counts = new int[var2.length];
   }

   // $FF: synthetic method
   static int access$210(EnumMultiset var0) {
      int var1 = var0.distinctElements--;
      return var1;
   }

   public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> var0) {
      return new EnumMultiset(var0);
   }

   public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> var0) {
      Iterator var1 = var0.iterator();
      Preconditions.checkArgument(var1.hasNext(), "EnumMultiset constructor passed empty Iterable");
      EnumMultiset var2 = new EnumMultiset(((Enum)var1.next()).getDeclaringClass());
      Iterables.addAll(var2, var0);
      return var2;
   }

   public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> var0, Class<E> var1) {
      EnumMultiset var2 = create(var1);
      Iterables.addAll(var2, var0);
      return var2;
   }

   private boolean isActuallyE(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Enum;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Enum var7 = (Enum)var1;
         int var5 = var7.ordinal();
         Enum[] var6 = this.enumConstants;
         var4 = var3;
         if (var5 < var6.length) {
            var4 = var3;
            if (var6[var5] == var7) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Class var2 = (Class)var1.readObject();
      this.type = var2;
      Enum[] var3 = (Enum[])var2.getEnumConstants();
      this.enumConstants = var3;
      this.counts = new int[var3.length];
      Serialization.populateMultiset(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.type);
      Serialization.writeMultiset(this, var1);
   }

   public int add(E var1, int var2) {
      this.checkIsE(var1);
      CollectPreconditions.checkNonnegative(var2, "occurrences");
      if (var2 == 0) {
         return this.count(var1);
      } else {
         int var3 = var1.ordinal();
         int var4 = this.counts[var3];
         long var5 = (long)var4;
         long var7 = (long)var2;
         var5 += var7;
         boolean var9;
         if (var5 <= 2147483647L) {
            var9 = true;
         } else {
            var9 = false;
         }

         Preconditions.checkArgument(var9, "too many occurrences: %s", var5);
         this.counts[var3] = (int)var5;
         if (var4 == 0) {
            ++this.distinctElements;
         }

         this.size += var7;
         return var4;
      }
   }

   void checkIsE(@NullableDecl Object var1) {
      Preconditions.checkNotNull(var1);
      if (!this.isActuallyE(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Expected an ");
         var2.append(this.type);
         var2.append(" but got ");
         var2.append(var1);
         throw new ClassCastException(var2.toString());
      }
   }

   public void clear() {
      Arrays.fill(this.counts, 0);
      this.size = 0L;
      this.distinctElements = 0;
   }

   public int count(@NullableDecl Object var1) {
      if (!this.isActuallyE(var1)) {
         return 0;
      } else {
         Enum var2 = (Enum)var1;
         return this.counts[var2.ordinal()];
      }
   }

   int distinctElements() {
      return this.distinctElements;
   }

   Iterator<E> elementIterator() {
      return new EnumMultiset<E>.Itr<E>() {
         E output(int var1) {
            return EnumMultiset.this.enumConstants[var1];
         }
      };
   }

   Iterator<Multiset.Entry<E>> entryIterator() {
      return new EnumMultiset<E>.Itr<Multiset.Entry<E>>() {
         Multiset.Entry<E> output(final int var1) {
            return new Multisets.AbstractEntry<E>() {
               public int getCount() {
                  return EnumMultiset.this.counts[var1];
               }

               public E getElement() {
                  return EnumMultiset.this.enumConstants[var1];
               }
            };
         }
      };
   }

   public Iterator<E> iterator() {
      return Multisets.iteratorImpl(this);
   }

   public int remove(@NullableDecl Object var1, int var2) {
      if (!this.isActuallyE(var1)) {
         return 0;
      } else {
         Enum var3 = (Enum)var1;
         CollectPreconditions.checkNonnegative(var2, "occurrences");
         if (var2 == 0) {
            return this.count(var1);
         } else {
            int var4 = var3.ordinal();
            int[] var6 = this.counts;
            int var5 = var6[var4];
            if (var5 == 0) {
               return 0;
            } else {
               if (var5 <= var2) {
                  var6[var4] = 0;
                  --this.distinctElements;
                  this.size -= (long)var5;
               } else {
                  var6[var4] = var5 - var2;
                  this.size -= (long)var2;
               }

               return var5;
            }
         }
      }
   }

   public int setCount(E var1, int var2) {
      this.checkIsE(var1);
      CollectPreconditions.checkNonnegative(var2, "count");
      int var3 = var1.ordinal();
      int[] var5 = this.counts;
      int var4 = var5[var3];
      var5[var3] = var2;
      this.size += (long)(var2 - var4);
      if (var4 == 0 && var2 > 0) {
         ++this.distinctElements;
      } else if (var4 > 0 && var2 == 0) {
         --this.distinctElements;
      }

      return var4;
   }

   public int size() {
      return Ints.saturatedCast(this.size);
   }

   abstract class Itr<T> implements Iterator<T> {
      int index = 0;
      int toRemove = -1;

      public boolean hasNext() {
         while(this.index < EnumMultiset.this.enumConstants.length) {
            int[] var1 = EnumMultiset.this.counts;
            int var2 = this.index;
            if (var1[var2] > 0) {
               return true;
            }

            this.index = var2 + 1;
         }

         return false;
      }

      public T next() {
         if (this.hasNext()) {
            Object var1 = this.output(this.index);
            int var2 = this.index;
            this.toRemove = var2;
            this.index = var2 + 1;
            return var1;
         } else {
            throw new NoSuchElementException();
         }
      }

      abstract T output(int var1);

      public void remove() {
         boolean var1;
         if (this.toRemove >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         CollectPreconditions.checkRemove(var1);
         if (EnumMultiset.this.counts[this.toRemove] > 0) {
            EnumMultiset.access$210(EnumMultiset.this);
            EnumMultiset var2 = EnumMultiset.this;
            var2.size = var2.size - (long)EnumMultiset.this.counts[this.toRemove];
            EnumMultiset.this.counts[this.toRemove] = 0;
         }

         this.toRemove = -1;
      }
   }
}
