package com.google.common.graph;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class ElementOrder<T> {
   @NullableDecl
   private final Comparator<T> comparator;
   private final ElementOrder.Type type;

   private ElementOrder(ElementOrder.Type var1, @NullableDecl Comparator<T> var2) {
      this.type = (ElementOrder.Type)Preconditions.checkNotNull(var1);
      this.comparator = var2;
      ElementOrder.Type var3 = ElementOrder.Type.SORTED;
      boolean var4 = true;
      boolean var5;
      if (var1 == var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      boolean var6;
      if (var2 != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var5 != var6) {
         var4 = false;
      }

      Preconditions.checkState(var4);
   }

   public static <S> ElementOrder<S> insertion() {
      return new ElementOrder(ElementOrder.Type.INSERTION, (Comparator)null);
   }

   public static <S extends Comparable<? super S>> ElementOrder<S> natural() {
      return new ElementOrder(ElementOrder.Type.SORTED, Ordering.natural());
   }

   public static <S> ElementOrder<S> sorted(Comparator<S> var0) {
      return new ElementOrder(ElementOrder.Type.SORTED, var0);
   }

   static <S> ElementOrder<S> stable() {
      return new ElementOrder(ElementOrder.Type.STABLE, (Comparator)null);
   }

   public static <S> ElementOrder<S> unordered() {
      return new ElementOrder(ElementOrder.Type.UNORDERED, (Comparator)null);
   }

   <T1 extends T> ElementOrder<T1> cast() {
      return this;
   }

   public Comparator<T> comparator() {
      Comparator var1 = this.comparator;
      if (var1 != null) {
         return var1;
      } else {
         throw new UnsupportedOperationException("This ordering does not define a comparator.");
      }
   }

   <K extends T, V> Map<K, V> createMap(int var1) {
      int var2 = null.$SwitchMap$com$google$common$graph$ElementOrder$Type[this.type.ordinal()];
      if (var2 != 1) {
         if (var2 != 2 && var2 != 3) {
            if (var2 == 4) {
               return Maps.newTreeMap(this.comparator());
            } else {
               throw new AssertionError();
            }
         } else {
            return Maps.newLinkedHashMapWithExpectedSize(var1);
         }
      } else {
         return Maps.newHashMapWithExpectedSize(var1);
      }
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ElementOrder)) {
         return false;
      } else {
         ElementOrder var3 = (ElementOrder)var1;
         if (this.type != var3.type || !Objects.equal(this.comparator, var3.comparator)) {
            var2 = false;
         }

         return var2;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.type, this.comparator);
   }

   public String toString() {
      MoreObjects.ToStringHelper var1 = MoreObjects.toStringHelper((Object)this).add("type", this.type);
      Comparator var2 = this.comparator;
      if (var2 != null) {
         var1.add("comparator", var2);
      }

      return var1.toString();
   }

   public ElementOrder.Type type() {
      return this.type;
   }

   public static enum Type {
      INSERTION,
      SORTED,
      STABLE,
      UNORDERED;

      static {
         ElementOrder.Type var0 = new ElementOrder.Type("SORTED", 3);
         SORTED = var0;
      }
   }
}
