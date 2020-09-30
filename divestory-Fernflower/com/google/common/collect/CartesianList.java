package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class CartesianList<E> extends AbstractList<List<E>> implements RandomAccess {
   private final transient ImmutableList<List<E>> axes;
   private final transient int[] axesSizeProduct;

   CartesianList(ImmutableList<List<E>> var1) {
      this.axes = var1;
      int[] var2 = new int[var1.size() + 1];
      var2[var1.size()] = 1;

      boolean var10001;
      int var3;
      try {
         var3 = var1.size() - 1;
      } catch (ArithmeticException var5) {
         var10001 = false;
         throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
      }

      for(; var3 >= 0; --var3) {
         try {
            var2[var3] = IntMath.checkedMultiply(var2[var3 + 1], ((List)var1.get(var3)).size());
         } catch (ArithmeticException var4) {
            var10001 = false;
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
         }
      }

      this.axesSizeProduct = var2;
   }

   static <E> List<List<E>> create(List<? extends List<? extends E>> var0) {
      ImmutableList.Builder var1 = new ImmutableList.Builder(var0.size());
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         ImmutableList var3 = ImmutableList.copyOf((Collection)((List)var2.next()));
         if (var3.isEmpty()) {
            return ImmutableList.of();
         }

         var1.add((Object)var3);
      }

      return new CartesianList(var1.build());
   }

   private int getAxisIndexForProductIndex(int var1, int var2) {
      return var1 / this.axesSizeProduct[var2 + 1] % ((List)this.axes.get(var2)).size();
   }

   public boolean contains(@NullableDecl Object var1) {
      boolean var2;
      if (this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public ImmutableList<E> get(final int var1) {
      Preconditions.checkElementIndex(var1, this.size());
      return new ImmutableList<E>() {
         public E get(int var1x) {
            Preconditions.checkElementIndex(var1x, this.size());
            int var2 = CartesianList.this.getAxisIndexForProductIndex(var1, var1x);
            return ((List)CartesianList.this.axes.get(var1x)).get(var2);
         }

         boolean isPartialView() {
            return true;
         }

         public int size() {
            return CartesianList.this.axes.size();
         }
      };
   }

   public int indexOf(Object var1) {
      if (!(var1 instanceof List)) {
         return -1;
      } else {
         List var5 = (List)var1;
         if (var5.size() != this.axes.size()) {
            return -1;
         } else {
            ListIterator var6 = var5.listIterator();

            int var2;
            int var3;
            int var4;
            for(var2 = 0; var6.hasNext(); var2 += var4 * this.axesSizeProduct[var3 + 1]) {
               var3 = var6.nextIndex();
               var4 = ((List)this.axes.get(var3)).indexOf(var6.next());
               if (var4 == -1) {
                  return -1;
               }
            }

            return var2;
         }
      }
   }

   public int size() {
      return this.axesSizeProduct[0];
   }
}
