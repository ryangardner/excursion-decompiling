package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class ImmutableIntArray implements Serializable {
   private static final ImmutableIntArray EMPTY = new ImmutableIntArray(new int[0]);
   private final int[] array;
   private final int end;
   private final transient int start;

   private ImmutableIntArray(int[] var1) {
      this(var1, 0, var1.length);
   }

   private ImmutableIntArray(int[] var1, int var2, int var3) {
      this.array = var1;
      this.start = var2;
      this.end = var3;
   }

   // $FF: synthetic method
   ImmutableIntArray(int[] var1, int var2, int var3, Object var4) {
      this(var1, var2, var3);
   }

   public static ImmutableIntArray.Builder builder() {
      return new ImmutableIntArray.Builder(10);
   }

   public static ImmutableIntArray.Builder builder(int var0) {
      boolean var1;
      if (var0 >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Invalid initialCapacity: %s", var0);
      return new ImmutableIntArray.Builder(var0);
   }

   public static ImmutableIntArray copyOf(Iterable<Integer> var0) {
      return var0 instanceof Collection ? copyOf((Collection)var0) : builder().addAll(var0).build();
   }

   public static ImmutableIntArray copyOf(Collection<Integer> var0) {
      ImmutableIntArray var1;
      if (var0.isEmpty()) {
         var1 = EMPTY;
      } else {
         var1 = new ImmutableIntArray(Ints.toArray(var0));
      }

      return var1;
   }

   public static ImmutableIntArray copyOf(int[] var0) {
      ImmutableIntArray var1;
      if (var0.length == 0) {
         var1 = EMPTY;
      } else {
         var1 = new ImmutableIntArray(Arrays.copyOf(var0, var0.length));
      }

      return var1;
   }

   private boolean isPartialView() {
      boolean var1;
      if (this.start <= 0 && this.end >= this.array.length) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static ImmutableIntArray of() {
      return EMPTY;
   }

   public static ImmutableIntArray of(int var0) {
      return new ImmutableIntArray(new int[]{var0});
   }

   public static ImmutableIntArray of(int var0, int var1) {
      return new ImmutableIntArray(new int[]{var0, var1});
   }

   public static ImmutableIntArray of(int var0, int var1, int var2) {
      return new ImmutableIntArray(new int[]{var0, var1, var2});
   }

   public static ImmutableIntArray of(int var0, int var1, int var2, int var3) {
      return new ImmutableIntArray(new int[]{var0, var1, var2, var3});
   }

   public static ImmutableIntArray of(int var0, int var1, int var2, int var3, int var4) {
      return new ImmutableIntArray(new int[]{var0, var1, var2, var3, var4});
   }

   public static ImmutableIntArray of(int var0, int var1, int var2, int var3, int var4, int var5) {
      return new ImmutableIntArray(new int[]{var0, var1, var2, var3, var4, var5});
   }

   public static ImmutableIntArray of(int var0, int... var1) {
      boolean var2;
      if (var1.length <= 2147483646) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "the total number of elements must fit in an int");
      int[] var3 = new int[var1.length + 1];
      var3[0] = var0;
      System.arraycopy(var1, 0, var3, 1, var1.length);
      return new ImmutableIntArray(var3);
   }

   public List<Integer> asList() {
      return new ImmutableIntArray.AsList(this);
   }

   public boolean contains(int var1) {
      boolean var2;
      if (this.indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ImmutableIntArray)) {
         return false;
      } else {
         ImmutableIntArray var3 = (ImmutableIntArray)var1;
         if (this.length() != var3.length()) {
            return false;
         } else {
            for(int var2 = 0; var2 < this.length(); ++var2) {
               if (this.get(var2) != var3.get(var2)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int get(int var1) {
      Preconditions.checkElementIndex(var1, this.length());
      return this.array[this.start + var1];
   }

   public int hashCode() {
      int var1 = this.start;

      int var2;
      for(var2 = 1; var1 < this.end; ++var1) {
         var2 = var2 * 31 + Ints.hashCode(this.array[var1]);
      }

      return var2;
   }

   public int indexOf(int var1) {
      for(int var2 = this.start; var2 < this.end; ++var2) {
         if (this.array[var2] == var1) {
            return var2 - this.start;
         }
      }

      return -1;
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.end == this.start) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int lastIndexOf(int var1) {
      int var2 = this.end - 1;

      while(true) {
         int var3 = this.start;
         if (var2 < var3) {
            return -1;
         }

         if (this.array[var2] == var1) {
            return var2 - var3;
         }

         --var2;
      }
   }

   public int length() {
      return this.end - this.start;
   }

   Object readResolve() {
      ImmutableIntArray var1;
      if (this.isEmpty()) {
         var1 = EMPTY;
      } else {
         var1 = this;
      }

      return var1;
   }

   public ImmutableIntArray subArray(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, this.length());
      ImmutableIntArray var3;
      if (var1 == var2) {
         var3 = EMPTY;
      } else {
         int[] var5 = this.array;
         int var4 = this.start;
         var3 = new ImmutableIntArray(var5, var1 + var4, var4 + var2);
      }

      return var3;
   }

   public int[] toArray() {
      return Arrays.copyOfRange(this.array, this.start, this.end);
   }

   public String toString() {
      if (this.isEmpty()) {
         return "[]";
      } else {
         StringBuilder var1 = new StringBuilder(this.length() * 5);
         var1.append('[');
         var1.append(this.array[this.start]);
         int var2 = this.start;

         while(true) {
            ++var2;
            if (var2 >= this.end) {
               var1.append(']');
               return var1.toString();
            }

            var1.append(", ");
            var1.append(this.array[var2]);
         }
      }
   }

   public ImmutableIntArray trimmed() {
      ImmutableIntArray var1;
      if (this.isPartialView()) {
         var1 = new ImmutableIntArray(this.toArray());
      } else {
         var1 = this;
      }

      return var1;
   }

   Object writeReplace() {
      return this.trimmed();
   }

   static class AsList extends AbstractList<Integer> implements RandomAccess, Serializable {
      private final ImmutableIntArray parent;

      private AsList(ImmutableIntArray var1) {
         this.parent = var1;
      }

      // $FF: synthetic method
      AsList(ImmutableIntArray var1, Object var2) {
         this(var1);
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (this.indexOf(var1) >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof ImmutableIntArray.AsList) {
            ImmutableIntArray.AsList var6 = (ImmutableIntArray.AsList)var1;
            return this.parent.equals(var6.parent);
         } else if (!(var1 instanceof List)) {
            return false;
         } else {
            List var4 = (List)var1;
            if (this.size() != var4.size()) {
               return false;
            } else {
               int var2 = this.parent.start;

               for(Iterator var5 = var4.iterator(); var5.hasNext(); ++var2) {
                  Object var3 = var5.next();
                  if (!(var3 instanceof Integer) || this.parent.array[var2] != (Integer)var3) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      public Integer get(int var1) {
         return this.parent.get(var1);
      }

      public int hashCode() {
         return this.parent.hashCode();
      }

      public int indexOf(Object var1) {
         int var2;
         if (var1 instanceof Integer) {
            var2 = this.parent.indexOf((Integer)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int lastIndexOf(Object var1) {
         int var2;
         if (var1 instanceof Integer) {
            var2 = this.parent.lastIndexOf((Integer)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int size() {
         return this.parent.length();
      }

      public List<Integer> subList(int var1, int var2) {
         return this.parent.subArray(var1, var2).asList();
      }

      public String toString() {
         return this.parent.toString();
      }
   }

   public static final class Builder {
      private int[] array;
      private int count = 0;

      Builder(int var1) {
         this.array = new int[var1];
      }

      private void ensureRoomFor(int var1) {
         var1 += this.count;
         int[] var2 = this.array;
         if (var1 > var2.length) {
            var2 = new int[expandedCapacity(var2.length, var1)];
            System.arraycopy(this.array, 0, var2, 0, this.count);
            this.array = var2;
         }

      }

      private static int expandedCapacity(int var0, int var1) {
         if (var1 >= 0) {
            int var2 = var0 + (var0 >> 1) + 1;
            var0 = var2;
            if (var2 < var1) {
               var0 = Integer.highestOneBit(var1 - 1) << 1;
            }

            var1 = var0;
            if (var0 < 0) {
               var1 = Integer.MAX_VALUE;
            }

            return var1;
         } else {
            throw new AssertionError("cannot store more than MAX_VALUE elements");
         }
      }

      public ImmutableIntArray.Builder add(int var1) {
         this.ensureRoomFor(1);
         int[] var2 = this.array;
         int var3 = this.count;
         var2[var3] = var1;
         this.count = var3 + 1;
         return this;
      }

      public ImmutableIntArray.Builder addAll(ImmutableIntArray var1) {
         this.ensureRoomFor(var1.length());
         System.arraycopy(var1.array, var1.start, this.array, this.count, var1.length());
         this.count += var1.length();
         return this;
      }

      public ImmutableIntArray.Builder addAll(Iterable<Integer> var1) {
         if (var1 instanceof Collection) {
            return this.addAll((Collection)var1);
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.add((Integer)var2.next());
            }

            return this;
         }
      }

      public ImmutableIntArray.Builder addAll(Collection<Integer> var1) {
         this.ensureRoomFor(var1.size());

         Integer var2;
         int[] var3;
         int var4;
         for(Iterator var5 = var1.iterator(); var5.hasNext(); var3[var4] = var2) {
            var2 = (Integer)var5.next();
            var3 = this.array;
            var4 = this.count++;
         }

         return this;
      }

      public ImmutableIntArray.Builder addAll(int[] var1) {
         this.ensureRoomFor(var1.length);
         System.arraycopy(var1, 0, this.array, this.count, var1.length);
         this.count += var1.length;
         return this;
      }

      @CheckReturnValue
      public ImmutableIntArray build() {
         ImmutableIntArray var1;
         if (this.count == 0) {
            var1 = ImmutableIntArray.EMPTY;
         } else {
            var1 = new ImmutableIntArray(this.array, 0, this.count);
         }

         return var1;
      }
   }
}
