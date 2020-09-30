package com.google.common.base;

import java.io.Serializable;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Functions {
   private Functions() {
   }

   public static <A, B, C> Function<A, C> compose(Function<B, C> var0, Function<A, ? extends B> var1) {
      return new Functions.FunctionComposition(var0, var1);
   }

   public static <E> Function<Object, E> constant(@NullableDecl E var0) {
      return new Functions.ConstantFunction(var0);
   }

   public static <K, V> Function<K, V> forMap(Map<K, V> var0) {
      return new Functions.FunctionForMapNoDefault(var0);
   }

   public static <K, V> Function<K, V> forMap(Map<K, ? extends V> var0, @NullableDecl V var1) {
      return new Functions.ForMapWithDefault(var0, var1);
   }

   public static <T> Function<T, Boolean> forPredicate(Predicate<T> var0) {
      return new Functions.PredicateFunction(var0);
   }

   public static <T> Function<Object, T> forSupplier(Supplier<T> var0) {
      return new Functions.SupplierFunction(var0);
   }

   public static <E> Function<E, E> identity() {
      return Functions.IdentityFunction.INSTANCE;
   }

   public static Function<Object, String> toStringFunction() {
      return Functions.ToStringFunction.INSTANCE;
   }

   private static class ConstantFunction<E> implements Function<Object, E>, Serializable {
      private static final long serialVersionUID = 0L;
      @NullableDecl
      private final E value;

      public ConstantFunction(@NullableDecl E var1) {
         this.value = var1;
      }

      public E apply(@NullableDecl Object var1) {
         return this.value;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Functions.ConstantFunction) {
            Functions.ConstantFunction var2 = (Functions.ConstantFunction)var1;
            return Objects.equal(this.value, var2.value);
         } else {
            return false;
         }
      }

      public int hashCode() {
         Object var1 = this.value;
         int var2;
         if (var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.hashCode();
         }

         return var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Functions.constant(");
         var1.append(this.value);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
      private static final long serialVersionUID = 0L;
      @NullableDecl
      final V defaultValue;
      final Map<K, ? extends V> map;

      ForMapWithDefault(Map<K, ? extends V> var1, @NullableDecl V var2) {
         this.map = (Map)Preconditions.checkNotNull(var1);
         this.defaultValue = var2;
      }

      public V apply(@NullableDecl K var1) {
         Object var2 = this.map.get(var1);
         Object var3 = var2;
         if (var2 == null) {
            if (this.map.containsKey(var1)) {
               var3 = var2;
            } else {
               var3 = this.defaultValue;
            }
         }

         return var3;
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Functions.ForMapWithDefault;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Functions.ForMapWithDefault var5 = (Functions.ForMapWithDefault)var1;
            var4 = var3;
            if (this.map.equals(var5.map)) {
               var4 = var3;
               if (Objects.equal(this.defaultValue, var5.defaultValue)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return Objects.hashCode(this.map, this.defaultValue);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Functions.forMap(");
         var1.append(this.map);
         var1.append(", defaultValue=");
         var1.append(this.defaultValue);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Function<A, ? extends B> f;
      private final Function<B, C> g;

      public FunctionComposition(Function<B, C> var1, Function<A, ? extends B> var2) {
         this.g = (Function)Preconditions.checkNotNull(var1);
         this.f = (Function)Preconditions.checkNotNull(var2);
      }

      public C apply(@NullableDecl A var1) {
         return this.g.apply(this.f.apply(var1));
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Functions.FunctionComposition;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Functions.FunctionComposition var5 = (Functions.FunctionComposition)var1;
            var4 = var3;
            if (this.f.equals(var5.f)) {
               var4 = var3;
               if (this.g.equals(var5.g)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.f.hashCode() ^ this.g.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.g);
         var1.append("(");
         var1.append(this.f);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
      private static final long serialVersionUID = 0L;
      final Map<K, V> map;

      FunctionForMapNoDefault(Map<K, V> var1) {
         this.map = (Map)Preconditions.checkNotNull(var1);
      }

      public V apply(@NullableDecl K var1) {
         Object var2 = this.map.get(var1);
         boolean var3;
         if (var2 == null && !this.map.containsKey(var1)) {
            var3 = false;
         } else {
            var3 = true;
         }

         Preconditions.checkArgument(var3, "Key '%s' not present in map", var1);
         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Functions.FunctionForMapNoDefault) {
            Functions.FunctionForMapNoDefault var2 = (Functions.FunctionForMapNoDefault)var1;
            return this.map.equals(var2.map);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.map.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Functions.forMap(");
         var1.append(this.map);
         var1.append(")");
         return var1.toString();
      }
   }

   private static enum IdentityFunction implements Function<Object, Object> {
      INSTANCE;

      static {
         Functions.IdentityFunction var0 = new Functions.IdentityFunction("INSTANCE", 0);
         INSTANCE = var0;
      }

      @NullableDecl
      public Object apply(@NullableDecl Object var1) {
         return var1;
      }

      public String toString() {
         return "Functions.identity()";
      }
   }

   private static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Predicate<T> predicate;

      private PredicateFunction(Predicate<T> var1) {
         this.predicate = (Predicate)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      PredicateFunction(Predicate var1, Object var2) {
         this(var1);
      }

      public Boolean apply(@NullableDecl T var1) {
         return this.predicate.apply(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Functions.PredicateFunction) {
            Functions.PredicateFunction var2 = (Functions.PredicateFunction)var1;
            return this.predicate.equals(var2.predicate);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.predicate.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Functions.forPredicate(");
         var1.append(this.predicate);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class SupplierFunction<T> implements Function<Object, T>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Supplier<T> supplier;

      private SupplierFunction(Supplier<T> var1) {
         this.supplier = (Supplier)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      SupplierFunction(Supplier var1, Object var2) {
         this(var1);
      }

      public T apply(@NullableDecl Object var1) {
         return this.supplier.get();
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Functions.SupplierFunction) {
            Functions.SupplierFunction var2 = (Functions.SupplierFunction)var1;
            return this.supplier.equals(var2.supplier);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.supplier.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Functions.forSupplier(");
         var1.append(this.supplier);
         var1.append(")");
         return var1.toString();
      }
   }

   private static enum ToStringFunction implements Function<Object, String> {
      INSTANCE;

      static {
         Functions.ToStringFunction var0 = new Functions.ToStringFunction("INSTANCE", 0);
         INSTANCE = var0;
      }

      public String apply(Object var1) {
         Preconditions.checkNotNull(var1);
         return var1.toString();
      }

      public String toString() {
         return "Functions.toStringFunction()";
      }
   }
}
