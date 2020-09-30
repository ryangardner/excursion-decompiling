package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
   private static final int CUTOFF = 751619276;
   private static final double DESIRED_LOAD_FACTOR = 0.7D;
   static final int MAX_TABLE_SIZE = 1073741824;
   @LazyInit
   @NullableDecl
   private transient ImmutableList<E> asList;

   ImmutableSet() {
   }

   public static <E> ImmutableSet.Builder<E> builder() {
      return new ImmutableSet.Builder();
   }

   public static <E> ImmutableSet.Builder<E> builderWithExpectedSize(int var0) {
      CollectPreconditions.checkNonnegative(var0, "expectedSize");
      return new ImmutableSet.Builder(var0);
   }

   static int chooseTableSize(int var0) {
      int var1 = Math.max(var0, 2);
      boolean var2 = true;
      if (var1 >= 751619276) {
         if (var1 >= 1073741824) {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "collection too large");
         return 1073741824;
      } else {
         for(var0 = Integer.highestOneBit(var1 - 1) << 1; (double)var0 * 0.7D < (double)var1; var0 <<= 1) {
         }

         return var0;
      }
   }

   private static <E> ImmutableSet<E> construct(int var0, Object... var1) {
      if (var0 == 0) {
         return of();
      } else if (var0 == 1) {
         return of(var1[0]);
      } else {
         int var2 = chooseTableSize(var0);
         Object[] var3 = new Object[var2];
         int var4 = var2 - 1;
         int var5 = 0;
         int var6 = 0;

         int var7;
         for(var7 = 0; var5 < var0; ++var5) {
            Object var8 = ObjectArrays.checkElementNotNull(var1[var5], var5);
            int var9 = var8.hashCode();
            int var10 = Hashing.smear(var9);

            while(true) {
               int var11 = var10 & var4;
               Object var12 = var3[var11];
               if (var12 == null) {
                  var1[var7] = var8;
                  var3[var11] = var8;
                  var6 += var9;
                  ++var7;
                  break;
               }

               if (var12.equals(var8)) {
                  break;
               }

               ++var10;
            }
         }

         Arrays.fill(var1, var7, var0, (Object)null);
         if (var7 == 1) {
            return new SingletonImmutableSet(var1[0], var6);
         } else if (chooseTableSize(var7) < var2 / 2) {
            return construct(var7, var1);
         } else {
            Object[] var13 = var1;
            if (shouldTrim(var7, var1.length)) {
               var13 = Arrays.copyOf(var1, var7);
            }

            return new RegularImmutableSet(var13, var6, var3, var4, var7);
         }
      }
   }

   public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> var0) {
      ImmutableSet var1;
      if (var0 instanceof Collection) {
         var1 = copyOf((Collection)var0);
      } else {
         var1 = copyOf(var0.iterator());
      }

      return var1;
   }

   public static <E> ImmutableSet<E> copyOf(Collection<? extends E> var0) {
      if (var0 instanceof ImmutableSet && !(var0 instanceof SortedSet)) {
         ImmutableSet var1 = (ImmutableSet)var0;
         if (!var1.isPartialView()) {
            return var1;
         }
      }

      Object[] var2 = var0.toArray();
      return construct(var2.length, var2);
   }

   public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> var0) {
      if (!var0.hasNext()) {
         return of();
      } else {
         Object var1 = var0.next();
         return !var0.hasNext() ? of(var1) : (new ImmutableSet.Builder()).add(var1).addAll(var0).build();
      }
   }

   public static <E> ImmutableSet<E> copyOf(E[] var0) {
      int var1 = var0.length;
      if (var1 != 0) {
         return var1 != 1 ? construct(var0.length, (Object[])var0.clone()) : of(var0[0]);
      } else {
         return of();
      }
   }

   public static <E> ImmutableSet<E> of() {
      return RegularImmutableSet.EMPTY;
   }

   public static <E> ImmutableSet<E> of(E var0) {
      return new SingletonImmutableSet(var0);
   }

   public static <E> ImmutableSet<E> of(E var0, E var1) {
      return construct(2, var0, var1);
   }

   public static <E> ImmutableSet<E> of(E var0, E var1, E var2) {
      return construct(3, var0, var1, var2);
   }

   public static <E> ImmutableSet<E> of(E var0, E var1, E var2, E var3) {
      return construct(4, var0, var1, var2, var3);
   }

   public static <E> ImmutableSet<E> of(E var0, E var1, E var2, E var3, E var4) {
      return construct(5, var0, var1, var2, var3, var4);
   }

   @SafeVarargs
   public static <E> ImmutableSet<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E... var6) {
      boolean var7;
      if (var6.length <= 2147483641) {
         var7 = true;
      } else {
         var7 = false;
      }

      Preconditions.checkArgument(var7, "the total number of elements must fit in an int");
      int var8 = var6.length + 6;
      Object[] var9 = new Object[var8];
      var9[0] = var0;
      var9[1] = var1;
      var9[2] = var2;
      var9[3] = var3;
      var9[4] = var4;
      var9[5] = var5;
      System.arraycopy(var6, 0, var9, 6, var6.length);
      return construct(var8, var9);
   }

   private static boolean shouldTrim(int var0, int var1) {
      boolean var2;
      if (var0 < (var1 >> 1) + (var1 >> 2)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public ImmutableList<E> asList() {
      ImmutableList var1 = this.asList;
      ImmutableList var2 = var1;
      if (var1 == null) {
         var2 = this.createAsList();
         this.asList = var2;
      }

      return var2;
   }

   ImmutableList<E> createAsList() {
      return ImmutableList.asImmutableList(this.toArray());
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof ImmutableSet && this.isHashCodeFast() && ((ImmutableSet)var1).isHashCodeFast() && this.hashCode() != var1.hashCode() ? false : Sets.equalsImpl(this, var1);
      }
   }

   public int hashCode() {
      return Sets.hashCodeImpl(this);
   }

   boolean isHashCodeFast() {
      return false;
   }

   public abstract UnmodifiableIterator<E> iterator();

   Object writeReplace() {
      return new ImmutableSet.SerializedForm(this.toArray());
   }

   public static class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
      private int hashCode;
      @NullableDecl
      Object[] hashTable;

      public Builder() {
         super(4);
      }

      Builder(int var1) {
         super(var1);
         this.hashTable = new Object[ImmutableSet.chooseTableSize(var1)];
      }

      private void addDeduping(E var1) {
         int var2 = this.hashTable.length;
         int var3 = var1.hashCode();
         int var4 = Hashing.smear(var3);

         while(true) {
            var4 &= var2 - 1;
            Object[] var5 = this.hashTable;
            Object var6 = var5[var4];
            if (var6 == null) {
               var5[var4] = var1;
               this.hashCode += var3;
               super.add(var1);
               return;
            }

            if (var6.equals(var1)) {
               return;
            }

            ++var4;
         }
      }

      public ImmutableSet.Builder<E> add(E var1) {
         Preconditions.checkNotNull(var1);
         if (this.hashTable != null && ImmutableSet.chooseTableSize(this.size) <= this.hashTable.length) {
            this.addDeduping(var1);
            return this;
         } else {
            this.hashTable = null;
            super.add(var1);
            return this;
         }
      }

      public ImmutableSet.Builder<E> add(E... var1) {
         if (this.hashTable != null) {
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               this.add(var1[var3]);
            }
         } else {
            super.add(var1);
         }

         return this;
      }

      public ImmutableSet.Builder<E> addAll(Iterable<? extends E> var1) {
         Preconditions.checkNotNull(var1);
         if (this.hashTable != null) {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.add(var2.next());
            }
         } else {
            super.addAll(var1);
         }

         return this;
      }

      public ImmutableSet.Builder<E> addAll(Iterator<? extends E> var1) {
         Preconditions.checkNotNull(var1);

         while(var1.hasNext()) {
            this.add(var1.next());
         }

         return this;
      }

      public ImmutableSet<E> build() {
         int var1 = this.size;
         if (var1 != 0) {
            if (var1 == 1) {
               return ImmutableSet.of(this.contents[0]);
            } else {
               Object var2;
               if (this.hashTable != null && ImmutableSet.chooseTableSize(this.size) == this.hashTable.length) {
                  Object[] var4;
                  if (ImmutableSet.shouldTrim(this.size, this.contents.length)) {
                     var4 = Arrays.copyOf(this.contents, this.size);
                  } else {
                     var4 = this.contents;
                  }

                  var1 = this.hashCode;
                  Object[] var3 = this.hashTable;
                  var2 = new RegularImmutableSet(var4, var1, var3, var3.length - 1, this.size);
               } else {
                  var2 = ImmutableSet.construct(this.size, this.contents);
                  this.size = ((ImmutableSet)var2).size();
               }

               this.forceCopy = true;
               this.hashTable = null;
               return (ImmutableSet)var2;
            }
         } else {
            return ImmutableSet.of();
         }
      }
   }

   private static class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      final Object[] elements;

      SerializedForm(Object[] var1) {
         this.elements = var1;
      }

      Object readResolve() {
         return ImmutableSet.copyOf(this.elements);
      }
   }
}
