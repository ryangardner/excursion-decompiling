package com.google.common.base;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Equivalence<T> {
   protected Equivalence() {
   }

   public static Equivalence<Object> equals() {
      return Equivalence.Equals.INSTANCE;
   }

   public static Equivalence<Object> identity() {
      return Equivalence.Identity.INSTANCE;
   }

   protected abstract boolean doEquivalent(T var1, T var2);

   protected abstract int doHash(T var1);

   public final boolean equivalent(@NullableDecl T var1, @NullableDecl T var2) {
      if (var1 == var2) {
         return true;
      } else {
         return var1 != null && var2 != null ? this.doEquivalent(var1, var2) : false;
      }
   }

   public final Predicate<T> equivalentTo(@NullableDecl T var1) {
      return new Equivalence.EquivalentToPredicate(this, var1);
   }

   public final int hash(@NullableDecl T var1) {
      return var1 == null ? 0 : this.doHash(var1);
   }

   public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> var1) {
      return new FunctionalEquivalence(var1, this);
   }

   public final <S extends T> Equivalence<Iterable<S>> pairwise() {
      return new PairwiseEquivalence(this);
   }

   public final <S extends T> Equivalence.Wrapper<S> wrap(@NullableDecl S var1) {
      return new Equivalence.Wrapper(this, var1);
   }

   static final class Equals extends Equivalence<Object> implements Serializable {
      static final Equivalence.Equals INSTANCE = new Equivalence.Equals();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected boolean doEquivalent(Object var1, Object var2) {
         return var1.equals(var2);
      }

      protected int doHash(Object var1) {
         return var1.hashCode();
      }
   }

   private static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Equivalence<T> equivalence;
      @NullableDecl
      private final T target;

      EquivalentToPredicate(Equivalence<T> var1, @NullableDecl T var2) {
         this.equivalence = (Equivalence)Preconditions.checkNotNull(var1);
         this.target = var2;
      }

      public boolean apply(@NullableDecl T var1) {
         return this.equivalence.equivalent(var1, this.target);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof Equivalence.EquivalentToPredicate)) {
            return false;
         } else {
            Equivalence.EquivalentToPredicate var3 = (Equivalence.EquivalentToPredicate)var1;
            if (!this.equivalence.equals(var3.equivalence) || !Objects.equal(this.target, var3.target)) {
               var2 = false;
            }

            return var2;
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.equivalence, this.target);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.equivalence);
         var1.append(".equivalentTo(");
         var1.append(this.target);
         var1.append(")");
         return var1.toString();
      }
   }

   static final class Identity extends Equivalence<Object> implements Serializable {
      static final Equivalence.Identity INSTANCE = new Equivalence.Identity();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected boolean doEquivalent(Object var1, Object var2) {
         return false;
      }

      protected int doHash(Object var1) {
         return System.identityHashCode(var1);
      }
   }

   public static final class Wrapper<T> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final Equivalence<? super T> equivalence;
      @NullableDecl
      private final T reference;

      private Wrapper(Equivalence<? super T> var1, @NullableDecl T var2) {
         this.equivalence = (Equivalence)Preconditions.checkNotNull(var1);
         this.reference = var2;
      }

      // $FF: synthetic method
      Wrapper(Equivalence var1, Object var2, Object var3) {
         this(var1, var2);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else {
            if (var1 instanceof Equivalence.Wrapper) {
               Equivalence.Wrapper var2 = (Equivalence.Wrapper)var1;
               if (this.equivalence.equals(var2.equivalence)) {
                  return this.equivalence.equivalent(this.reference, var2.reference);
               }
            }

            return false;
         }
      }

      @NullableDecl
      public T get() {
         return this.reference;
      }

      public int hashCode() {
         return this.equivalence.hash(this.reference);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.equivalence);
         var1.append(".wrap(");
         var1.append(this.reference);
         var1.append(")");
         return var1.toString();
      }
   }
}
