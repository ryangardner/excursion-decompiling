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
public final class ImmutableLongArray implements Serializable {
   private static final ImmutableLongArray EMPTY = new ImmutableLongArray(new long[0]);
   private final long[] array;
   private final int end;
   private final transient int start;

   private ImmutableLongArray(long[] var1) {
      this(var1, 0, var1.length);
   }

   private ImmutableLongArray(long[] var1, int var2, int var3) {
      this.array = var1;
      this.start = var2;
      this.end = var3;
   }

   // $FF: synthetic method
   ImmutableLongArray(long[] var1, int var2, int var3, Object var4) {
      this(var1, var2, var3);
   }

   public static ImmutableLongArray.Builder builder() {
      return new ImmutableLongArray.Builder(10);
   }

   public static ImmutableLongArray.Builder builder(int var0) {
      boolean var1;
      if (var0 >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Invalid initialCapacity: %s", var0);
      return new ImmutableLongArray.Builder(var0);
   }

   public static ImmutableLongArray copyOf(Iterable<Long> var0) {
      return var0 instanceof Collection ? copyOf((Collection)var0) : builder().addAll(var0).build();
   }

   public static ImmutableLongArray copyOf(Collection<Long> var0) {
      ImmutableLongArray var1;
      if (var0.isEmpty()) {
         var1 = EMPTY;
      } else {
         var1 = new ImmutableLongArray(Longs.toArray(var0));
      }

      return var1;
   }

   public static ImmutableLongArray copyOf(long[] var0) {
      ImmutableLongArray var1;
      if (var0.length == 0) {
         var1 = EMPTY;
      } else {
         var1 = new ImmutableLongArray(Arrays.copyOf(var0, var0.length));
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

   public static ImmutableLongArray of() {
      return EMPTY;
   }

   public static ImmutableLongArray of(long var0) {
      return new ImmutableLongArray(new long[]{var0});
   }

   public static ImmutableLongArray of(long var0, long var2) {
      return new ImmutableLongArray(new long[]{var0, var2});
   }

   public static ImmutableLongArray of(long var0, long var2, long var4) {
      return new ImmutableLongArray(new long[]{var0, var2, var4});
   }

   public static ImmutableLongArray of(long var0, long var2, long var4, long var6) {
      return new ImmutableLongArray(new long[]{var0, var2, var4, var6});
   }

   public static ImmutableLongArray of(long var0, long var2, long var4, long var6, long var8) {
      return new ImmutableLongArray(new long[]{var0, var2, var4, var6, var8});
   }

   public static ImmutableLongArray of(long var0, long var2, long var4, long var6, long var8, long var10) {
      return new ImmutableLongArray(new long[]{var0, var2, var4, var6, var8, var10});
   }

   public static ImmutableLongArray of(long var0, long... var2) {
      boolean var3;
      if (var2.length <= 2147483646) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "the total number of elements must fit in an int");
      long[] var4 = new long[var2.length + 1];
      var4[0] = var0;
      System.arraycopy(var2, 0, var4, 1, var2.length);
      return new ImmutableLongArray(var4);
   }

   public List<Long> asList() {
      return new ImmutableLongArray.AsList(this);
   }

   public boolean contains(long var1) {
      boolean var3;
      if (this.indexOf(var1) >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ImmutableLongArray)) {
         return false;
      } else {
         ImmutableLongArray var3 = (ImmutableLongArray)var1;
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

   public long get(int var1) {
      Preconditions.checkElementIndex(var1, this.length());
      return this.array[this.start + var1];
   }

   public int hashCode() {
      int var1 = this.start;

      int var2;
      for(var2 = 1; var1 < this.end; ++var1) {
         var2 = var2 * 31 + Longs.hashCode(this.array[var1]);
      }

      return var2;
   }

   public int indexOf(long var1) {
      for(int var3 = this.start; var3 < this.end; ++var3) {
         if (this.array[var3] == var1) {
            return var3 - this.start;
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

   public int lastIndexOf(long var1) {
      int var3 = this.end - 1;

      while(true) {
         int var4 = this.start;
         if (var3 < var4) {
            return -1;
         }

         if (this.array[var3] == var1) {
            return var3 - var4;
         }

         --var3;
      }
   }

   public int length() {
      return this.end - this.start;
   }

   Object readResolve() {
      ImmutableLongArray var1;
      if (this.isEmpty()) {
         var1 = EMPTY;
      } else {
         var1 = this;
      }

      return var1;
   }

   public ImmutableLongArray subArray(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, this.length());
      ImmutableLongArray var3;
      if (var1 == var2) {
         var3 = EMPTY;
      } else {
         long[] var5 = this.array;
         int var4 = this.start;
         var3 = new ImmutableLongArray(var5, var1 + var4, var4 + var2);
      }

      return var3;
   }

   public long[] toArray() {
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

   public ImmutableLongArray trimmed() {
      ImmutableLongArray var1;
      if (this.isPartialView()) {
         var1 = new ImmutableLongArray(this.toArray());
      } else {
         var1 = this;
      }

      return var1;
   }

   Object writeReplace() {
      return this.trimmed();
   }

   static class AsList extends AbstractList<Long> implements RandomAccess, Serializable {
      private final ImmutableLongArray parent;

      private AsList(ImmutableLongArray var1) {
         this.parent = var1;
      }

      // $FF: synthetic method
      AsList(ImmutableLongArray var1, Object var2) {
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
         if (var1 instanceof ImmutableLongArray.AsList) {
            ImmutableLongArray.AsList var5 = (ImmutableLongArray.AsList)var1;
            return this.parent.equals(var5.parent);
         } else if (!(var1 instanceof List)) {
            return false;
         } else {
            List var4 = (List)var1;
            if (this.size() != var4.size()) {
               return false;
            } else {
               int var2 = this.parent.start;

               for(Iterator var3 = var4.iterator(); var3.hasNext(); ++var2) {
                  var1 = var3.next();
                  if (!(var1 instanceof Long) || this.parent.array[var2] != (Long)var1) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      public Long get(int var1) {
         return this.parent.get(var1);
      }

      public int hashCode() {
         return this.parent.hashCode();
      }

      public int indexOf(Object var1) {
         int var2;
         if (var1 instanceof Long) {
            var2 = this.parent.indexOf((Long)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int lastIndexOf(Object var1) {
         int var2;
         if (var1 instanceof Long) {
            var2 = this.parent.lastIndexOf((Long)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int size() {
         return this.parent.length();
      }

      public List<Long> subList(int var1, int var2) {
         return this.parent.subArray(var1, var2).asList();
      }

      public String toString() {
         return this.parent.toString();
      }
   }

   public static final class Builder {
      private long[] array;
      private int count = 0;

      Builder(int var1) {
         this.array = new long[var1];
      }

      private void ensureRoomFor(int var1) {
         var1 += this.count;
         long[] var2 = this.array;
         if (var1 > var2.length) {
            var2 = new long[expandedCapacity(var2.length, var1)];
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

      public ImmutableLongArray.Builder add(long var1) {
         this.ensureRoomFor(1);
         long[] var3 = this.array;
         int var4 = this.count;
         var3[var4] = var1;
         this.count = var4 + 1;
         return this;
      }

      public ImmutableLongArray.Builder addAll(ImmutableLongArray var1) {
         this.ensureRoomFor(var1.length());
         System.arraycopy(var1.array, var1.start, this.array, this.count, var1.length());
         this.count += var1.length();
         return this;
      }

      public ImmutableLongArray.Builder addAll(Iterable<Long> var1) {
         if (var1 instanceof Collection) {
            return this.addAll((Collection)var1);
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.add((Long)var2.next());
            }

            return this;
         }
      }

      public ImmutableLongArray.Builder addAll(Collection<Long> var1) {
         this.ensureRoomFor(var1.size());

         long[] var3;
         int var4;
         Long var5;
         for(Iterator var2 = var1.iterator(); var2.hasNext(); var3[var4] = var5) {
            var5 = (Long)var2.next();
            var3 = this.array;
            var4 = this.count++;
         }

         return this;
      }

      public ImmutableLongArray.Builder addAll(long[] var1) {
         this.ensureRoomFor(var1.length);
         System.arraycopy(var1, 0, this.array, this.count, var1.length);
         this.count += var1.length;
         return this;
      }

      @CheckReturnValue
      public ImmutableLongArray build() {
         ImmutableLongArray var1;
         if (this.count == 0) {
            var1 = ImmutableLongArray.EMPTY;
         } else {
            var1 = new ImmutableLongArray(this.array, 0, this.count);
         }

         return var1;
      }
   }
}
