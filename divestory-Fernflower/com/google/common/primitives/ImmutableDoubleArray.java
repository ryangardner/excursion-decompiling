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
public final class ImmutableDoubleArray implements Serializable {
   private static final ImmutableDoubleArray EMPTY = new ImmutableDoubleArray(new double[0]);
   private final double[] array;
   private final int end;
   private final transient int start;

   private ImmutableDoubleArray(double[] var1) {
      this(var1, 0, var1.length);
   }

   private ImmutableDoubleArray(double[] var1, int var2, int var3) {
      this.array = var1;
      this.start = var2;
      this.end = var3;
   }

   // $FF: synthetic method
   ImmutableDoubleArray(double[] var1, int var2, int var3, Object var4) {
      this(var1, var2, var3);
   }

   private static boolean areEqual(double var0, double var2) {
      boolean var4;
      if (Double.doubleToLongBits(var0) == Double.doubleToLongBits(var2)) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static ImmutableDoubleArray.Builder builder() {
      return new ImmutableDoubleArray.Builder(10);
   }

   public static ImmutableDoubleArray.Builder builder(int var0) {
      boolean var1;
      if (var0 >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Invalid initialCapacity: %s", var0);
      return new ImmutableDoubleArray.Builder(var0);
   }

   public static ImmutableDoubleArray copyOf(Iterable<Double> var0) {
      return var0 instanceof Collection ? copyOf((Collection)var0) : builder().addAll(var0).build();
   }

   public static ImmutableDoubleArray copyOf(Collection<Double> var0) {
      ImmutableDoubleArray var1;
      if (var0.isEmpty()) {
         var1 = EMPTY;
      } else {
         var1 = new ImmutableDoubleArray(Doubles.toArray(var0));
      }

      return var1;
   }

   public static ImmutableDoubleArray copyOf(double[] var0) {
      ImmutableDoubleArray var1;
      if (var0.length == 0) {
         var1 = EMPTY;
      } else {
         var1 = new ImmutableDoubleArray(Arrays.copyOf(var0, var0.length));
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

   public static ImmutableDoubleArray of() {
      return EMPTY;
   }

   public static ImmutableDoubleArray of(double var0) {
      return new ImmutableDoubleArray(new double[]{var0});
   }

   public static ImmutableDoubleArray of(double var0, double var2) {
      return new ImmutableDoubleArray(new double[]{var0, var2});
   }

   public static ImmutableDoubleArray of(double var0, double var2, double var4) {
      return new ImmutableDoubleArray(new double[]{var0, var2, var4});
   }

   public static ImmutableDoubleArray of(double var0, double var2, double var4, double var6) {
      return new ImmutableDoubleArray(new double[]{var0, var2, var4, var6});
   }

   public static ImmutableDoubleArray of(double var0, double var2, double var4, double var6, double var8) {
      return new ImmutableDoubleArray(new double[]{var0, var2, var4, var6, var8});
   }

   public static ImmutableDoubleArray of(double var0, double var2, double var4, double var6, double var8, double var10) {
      return new ImmutableDoubleArray(new double[]{var0, var2, var4, var6, var8, var10});
   }

   public static ImmutableDoubleArray of(double var0, double... var2) {
      boolean var3;
      if (var2.length <= 2147483646) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "the total number of elements must fit in an int");
      double[] var4 = new double[var2.length + 1];
      var4[0] = var0;
      System.arraycopy(var2, 0, var4, 1, var2.length);
      return new ImmutableDoubleArray(var4);
   }

   public List<Double> asList() {
      return new ImmutableDoubleArray.AsList(this);
   }

   public boolean contains(double var1) {
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
      } else if (!(var1 instanceof ImmutableDoubleArray)) {
         return false;
      } else {
         ImmutableDoubleArray var3 = (ImmutableDoubleArray)var1;
         if (this.length() != var3.length()) {
            return false;
         } else {
            for(int var2 = 0; var2 < this.length(); ++var2) {
               if (!areEqual(this.get(var2), var3.get(var2))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public double get(int var1) {
      Preconditions.checkElementIndex(var1, this.length());
      return this.array[this.start + var1];
   }

   public int hashCode() {
      int var1 = this.start;

      int var2;
      for(var2 = 1; var1 < this.end; ++var1) {
         var2 = var2 * 31 + Doubles.hashCode(this.array[var1]);
      }

      return var2;
   }

   public int indexOf(double var1) {
      for(int var3 = this.start; var3 < this.end; ++var3) {
         if (areEqual(this.array[var3], var1)) {
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

   public int lastIndexOf(double var1) {
      for(int var3 = this.end - 1; var3 >= this.start; --var3) {
         if (areEqual(this.array[var3], var1)) {
            return var3 - this.start;
         }
      }

      return -1;
   }

   public int length() {
      return this.end - this.start;
   }

   Object readResolve() {
      ImmutableDoubleArray var1;
      if (this.isEmpty()) {
         var1 = EMPTY;
      } else {
         var1 = this;
      }

      return var1;
   }

   public ImmutableDoubleArray subArray(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, this.length());
      ImmutableDoubleArray var3;
      if (var1 == var2) {
         var3 = EMPTY;
      } else {
         double[] var5 = this.array;
         int var4 = this.start;
         var3 = new ImmutableDoubleArray(var5, var1 + var4, var4 + var2);
      }

      return var3;
   }

   public double[] toArray() {
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

   public ImmutableDoubleArray trimmed() {
      ImmutableDoubleArray var1;
      if (this.isPartialView()) {
         var1 = new ImmutableDoubleArray(this.toArray());
      } else {
         var1 = this;
      }

      return var1;
   }

   Object writeReplace() {
      return this.trimmed();
   }

   static class AsList extends AbstractList<Double> implements RandomAccess, Serializable {
      private final ImmutableDoubleArray parent;

      private AsList(ImmutableDoubleArray var1) {
         this.parent = var1;
      }

      // $FF: synthetic method
      AsList(ImmutableDoubleArray var1, Object var2) {
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
         if (var1 instanceof ImmutableDoubleArray.AsList) {
            ImmutableDoubleArray.AsList var5 = (ImmutableDoubleArray.AsList)var1;
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
                  if (!(var1 instanceof Double) || !ImmutableDoubleArray.areEqual(this.parent.array[var2], (Double)var1)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      public Double get(int var1) {
         return this.parent.get(var1);
      }

      public int hashCode() {
         return this.parent.hashCode();
      }

      public int indexOf(Object var1) {
         int var2;
         if (var1 instanceof Double) {
            var2 = this.parent.indexOf((Double)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int lastIndexOf(Object var1) {
         int var2;
         if (var1 instanceof Double) {
            var2 = this.parent.lastIndexOf((Double)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int size() {
         return this.parent.length();
      }

      public List<Double> subList(int var1, int var2) {
         return this.parent.subArray(var1, var2).asList();
      }

      public String toString() {
         return this.parent.toString();
      }
   }

   public static final class Builder {
      private double[] array;
      private int count = 0;

      Builder(int var1) {
         this.array = new double[var1];
      }

      private void ensureRoomFor(int var1) {
         var1 += this.count;
         double[] var2 = this.array;
         if (var1 > var2.length) {
            var2 = new double[expandedCapacity(var2.length, var1)];
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

      public ImmutableDoubleArray.Builder add(double var1) {
         this.ensureRoomFor(1);
         double[] var3 = this.array;
         int var4 = this.count;
         var3[var4] = var1;
         this.count = var4 + 1;
         return this;
      }

      public ImmutableDoubleArray.Builder addAll(ImmutableDoubleArray var1) {
         this.ensureRoomFor(var1.length());
         System.arraycopy(var1.array, var1.start, this.array, this.count, var1.length());
         this.count += var1.length();
         return this;
      }

      public ImmutableDoubleArray.Builder addAll(Iterable<Double> var1) {
         if (var1 instanceof Collection) {
            return this.addAll((Collection)var1);
         } else {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.add((Double)var2.next());
            }

            return this;
         }
      }

      public ImmutableDoubleArray.Builder addAll(Collection<Double> var1) {
         this.ensureRoomFor(var1.size());

         Double var3;
         int var4;
         double[] var5;
         for(Iterator var2 = var1.iterator(); var2.hasNext(); var5[var4] = var3) {
            var3 = (Double)var2.next();
            var5 = this.array;
            var4 = this.count++;
         }

         return this;
      }

      public ImmutableDoubleArray.Builder addAll(double[] var1) {
         this.ensureRoomFor(var1.length);
         System.arraycopy(var1, 0, this.array, this.count, var1.length);
         this.count += var1.length;
         return this;
      }

      @CheckReturnValue
      public ImmutableDoubleArray build() {
         ImmutableDoubleArray var1;
         if (this.count == 0) {
            var1 = ImmutableDoubleArray.EMPTY;
         } else {
            var1 = new ImmutableDoubleArray(this.array, 0, this.count);
         }

         return var1;
      }
   }
}
