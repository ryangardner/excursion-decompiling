package com.google.common.base;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Suppliers {
   private Suppliers() {
   }

   public static <F, T> Supplier<T> compose(Function<? super F, T> var0, Supplier<F> var1) {
      return new Suppliers.SupplierComposition(var0, var1);
   }

   public static <T> Supplier<T> memoize(Supplier<T> var0) {
      if (!(var0 instanceof Suppliers.NonSerializableMemoizingSupplier) && !(var0 instanceof Suppliers.MemoizingSupplier)) {
         Object var1;
         if (var0 instanceof Serializable) {
            var1 = new Suppliers.MemoizingSupplier(var0);
         } else {
            var1 = new Suppliers.NonSerializableMemoizingSupplier(var0);
         }

         return (Supplier)var1;
      } else {
         return var0;
      }
   }

   public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> var0, long var1, TimeUnit var3) {
      return new Suppliers.ExpiringMemoizingSupplier(var0, var1, var3);
   }

   public static <T> Supplier<T> ofInstance(@NullableDecl T var0) {
      return new Suppliers.SupplierOfInstance(var0);
   }

   public static <T> Function<Supplier<T>, T> supplierFunction() {
      return Suppliers.SupplierFunctionImpl.INSTANCE;
   }

   public static <T> Supplier<T> synchronizedSupplier(Supplier<T> var0) {
      return new Suppliers.ThreadSafeSupplier(var0);
   }

   static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
      private static final long serialVersionUID = 0L;
      final Supplier<T> delegate;
      final long durationNanos;
      transient volatile long expirationNanos;
      @NullableDecl
      transient volatile T value;

      ExpiringMemoizingSupplier(Supplier<T> var1, long var2, TimeUnit var4) {
         this.delegate = (Supplier)Preconditions.checkNotNull(var1);
         this.durationNanos = var4.toNanos(var2);
         boolean var5;
         if (var2 > 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "duration (%s %s) must be > 0", var2, var4);
      }

      public T get() {
         Throwable var10000;
         boolean var10001;
         label261: {
            long var1 = this.expirationNanos;
            long var3 = Platform.systemNanoTime();
            if (var1 == 0L || var3 - var1 >= 0L) {
               synchronized(this){}

               label259: {
                  Object var5;
                  try {
                     if (var1 != this.expirationNanos) {
                        break label259;
                     }

                     var5 = this.delegate.get();
                     this.value = var5;
                     var1 = var3 + this.durationNanos;
                  } catch (Throwable var25) {
                     var10000 = var25;
                     var10001 = false;
                     break label261;
                  }

                  var3 = var1;
                  if (var1 == 0L) {
                     var3 = 1L;
                  }

                  try {
                     this.expirationNanos = var3;
                     return var5;
                  } catch (Throwable var23) {
                     var10000 = var23;
                     var10001 = false;
                     break label261;
                  }
               }

               try {
                  ;
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label261;
               }
            }

            return this.value;
         }

         while(true) {
            Throwable var26 = var10000;

            try {
               throw var26;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               continue;
            }
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Suppliers.memoizeWithExpiration(");
         var1.append(this.delegate);
         var1.append(", ");
         var1.append(this.durationNanos);
         var1.append(", NANOS)");
         return var1.toString();
      }
   }

   static class MemoizingSupplier<T> implements Supplier<T>, Serializable {
      private static final long serialVersionUID = 0L;
      final Supplier<T> delegate;
      transient volatile boolean initialized;
      @NullableDecl
      transient T value;

      MemoizingSupplier(Supplier<T> var1) {
         this.delegate = (Supplier)Preconditions.checkNotNull(var1);
      }

      public T get() {
         Throwable var10000;
         boolean var10001;
         label152: {
            if (!this.initialized) {
               synchronized(this){}

               try {
                  if (!this.initialized) {
                     Object var14 = this.delegate.get();
                     this.value = var14;
                     this.initialized = true;
                     return var14;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label152;
               }

               try {
                  ;
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label152;
               }
            }

            return this.value;
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               continue;
            }
         }
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Suppliers.memoize(");
         Object var3;
         if (this.initialized) {
            StringBuilder var2 = new StringBuilder();
            var2.append("<supplier that returned ");
            var2.append(this.value);
            var2.append(">");
            var3 = var2.toString();
         } else {
            var3 = this.delegate;
         }

         var1.append(var3);
         var1.append(")");
         return var1.toString();
      }
   }

   static class NonSerializableMemoizingSupplier<T> implements Supplier<T> {
      volatile Supplier<T> delegate;
      volatile boolean initialized;
      @NullableDecl
      T value;

      NonSerializableMemoizingSupplier(Supplier<T> var1) {
         this.delegate = (Supplier)Preconditions.checkNotNull(var1);
      }

      public T get() {
         Throwable var10000;
         boolean var10001;
         label152: {
            if (!this.initialized) {
               synchronized(this){}

               try {
                  if (!this.initialized) {
                     Object var14 = this.delegate.get();
                     this.value = var14;
                     this.initialized = true;
                     this.delegate = null;
                     return var14;
                  }
               } catch (Throwable var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label152;
               }

               try {
                  ;
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label152;
               }
            }

            return this.value;
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               continue;
            }
         }
      }

      public String toString() {
         Supplier var1 = this.delegate;
         StringBuilder var2 = new StringBuilder();
         var2.append("Suppliers.memoize(");
         Object var3 = var1;
         if (var1 == null) {
            StringBuilder var4 = new StringBuilder();
            var4.append("<supplier that returned ");
            var4.append(this.value);
            var4.append(">");
            var3 = var4.toString();
         }

         var2.append(var3);
         var2.append(")");
         return var2.toString();
      }
   }

   private static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
      private static final long serialVersionUID = 0L;
      final Function<? super F, T> function;
      final Supplier<F> supplier;

      SupplierComposition(Function<? super F, T> var1, Supplier<F> var2) {
         this.function = (Function)Preconditions.checkNotNull(var1);
         this.supplier = (Supplier)Preconditions.checkNotNull(var2);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Suppliers.SupplierComposition;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Suppliers.SupplierComposition var5 = (Suppliers.SupplierComposition)var1;
            var4 = var3;
            if (this.function.equals(var5.function)) {
               var4 = var3;
               if (this.supplier.equals(var5.supplier)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public T get() {
         return this.function.apply(this.supplier.get());
      }

      public int hashCode() {
         return Objects.hashCode(this.function, this.supplier);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Suppliers.compose(");
         var1.append(this.function);
         var1.append(", ");
         var1.append(this.supplier);
         var1.append(")");
         return var1.toString();
      }
   }

   private interface SupplierFunction<T> extends Function<Supplier<T>, T> {
   }

   private static enum SupplierFunctionImpl implements Suppliers.SupplierFunction<Object> {
      INSTANCE;

      static {
         Suppliers.SupplierFunctionImpl var0 = new Suppliers.SupplierFunctionImpl("INSTANCE", 0);
         INSTANCE = var0;
      }

      public Object apply(Supplier<Object> var1) {
         return var1.get();
      }

      public String toString() {
         return "Suppliers.supplierFunction()";
      }
   }

   private static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
      private static final long serialVersionUID = 0L;
      @NullableDecl
      final T instance;

      SupplierOfInstance(@NullableDecl T var1) {
         this.instance = var1;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Suppliers.SupplierOfInstance) {
            Suppliers.SupplierOfInstance var2 = (Suppliers.SupplierOfInstance)var1;
            return Objects.equal(this.instance, var2.instance);
         } else {
            return false;
         }
      }

      public T get() {
         return this.instance;
      }

      public int hashCode() {
         return Objects.hashCode(this.instance);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Suppliers.ofInstance(");
         var1.append(this.instance);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
      private static final long serialVersionUID = 0L;
      final Supplier<T> delegate;

      ThreadSafeSupplier(Supplier<T> var1) {
         this.delegate = (Supplier)Preconditions.checkNotNull(var1);
      }

      public T get() {
         // $FF: Couldn't be decompiled
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Suppliers.synchronizedSupplier(");
         var1.append(this.delegate);
         var1.append(")");
         return var1.toString();
      }
   }
}
