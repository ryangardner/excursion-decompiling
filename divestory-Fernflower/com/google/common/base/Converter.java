package com.google.common.base;

import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Converter<A, B> implements Function<A, B> {
   private final boolean handleNullAutomatically;
   @LazyInit
   @MonotonicNonNullDecl
   private transient Converter<B, A> reverse;

   protected Converter() {
      this(true);
   }

   Converter(boolean var1) {
      this.handleNullAutomatically = var1;
   }

   public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> var0, Function<? super B, ? extends A> var1) {
      return new Converter.FunctionBasedConverter(var0, var1);
   }

   public static <T> Converter<T, T> identity() {
      return Converter.IdentityConverter.INSTANCE;
   }

   public final <C> Converter<A, C> andThen(Converter<B, C> var1) {
      return this.doAndThen(var1);
   }

   @Deprecated
   @NullableDecl
   public final B apply(@NullableDecl A var1) {
      return this.convert(var1);
   }

   @NullableDecl
   public final B convert(@NullableDecl A var1) {
      return this.correctedDoForward(var1);
   }

   public Iterable<B> convertAll(final Iterable<? extends A> var1) {
      Preconditions.checkNotNull(var1, "fromIterable");
      return new Iterable<B>() {
         public Iterator<B> iterator() {
            return new Iterator<B>() {
               private final Iterator<? extends A> fromIterator = var1.iterator();

               public boolean hasNext() {
                  return this.fromIterator.hasNext();
               }

               public B next() {
                  return Converter.this.convert(this.fromIterator.next());
               }

               public void remove() {
                  this.fromIterator.remove();
               }
            };
         }
      };
   }

   @NullableDecl
   A correctedDoBackward(@NullableDecl B var1) {
      if (this.handleNullAutomatically) {
         if (var1 == null) {
            var1 = null;
         } else {
            var1 = Preconditions.checkNotNull(this.doBackward(var1));
         }

         return var1;
      } else {
         return this.doBackward(var1);
      }
   }

   @NullableDecl
   B correctedDoForward(@NullableDecl A var1) {
      if (this.handleNullAutomatically) {
         if (var1 == null) {
            var1 = null;
         } else {
            var1 = Preconditions.checkNotNull(this.doForward(var1));
         }

         return var1;
      } else {
         return this.doForward(var1);
      }
   }

   <C> Converter<A, C> doAndThen(Converter<B, C> var1) {
      return new Converter.ConverterComposition(this, (Converter)Preconditions.checkNotNull(var1));
   }

   protected abstract A doBackward(B var1);

   protected abstract B doForward(A var1);

   public boolean equals(@NullableDecl Object var1) {
      return super.equals(var1);
   }

   public Converter<B, A> reverse() {
      Converter var1 = this.reverse;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new Converter.ReverseConverter(this);
         this.reverse = (Converter)var2;
      }

      return (Converter)var2;
   }

   private static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {
      private static final long serialVersionUID = 0L;
      final Converter<A, B> first;
      final Converter<B, C> second;

      ConverterComposition(Converter<A, B> var1, Converter<B, C> var2) {
         this.first = var1;
         this.second = var2;
      }

      @NullableDecl
      A correctedDoBackward(@NullableDecl C var1) {
         return this.first.correctedDoBackward(this.second.correctedDoBackward(var1));
      }

      @NullableDecl
      C correctedDoForward(@NullableDecl A var1) {
         return this.second.correctedDoForward(this.first.correctedDoForward(var1));
      }

      protected A doBackward(C var1) {
         throw new AssertionError();
      }

      protected C doForward(A var1) {
         throw new AssertionError();
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Converter.ConverterComposition;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Converter.ConverterComposition var5 = (Converter.ConverterComposition)var1;
            var4 = var3;
            if (this.first.equals(var5.first)) {
               var4 = var3;
               if (this.second.equals(var5.second)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.first.hashCode() * 31 + this.second.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.first);
         var1.append(".andThen(");
         var1.append(this.second);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
      private final Function<? super B, ? extends A> backwardFunction;
      private final Function<? super A, ? extends B> forwardFunction;

      private FunctionBasedConverter(Function<? super A, ? extends B> var1, Function<? super B, ? extends A> var2) {
         this.forwardFunction = (Function)Preconditions.checkNotNull(var1);
         this.backwardFunction = (Function)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      FunctionBasedConverter(Function var1, Function var2, Object var3) {
         this(var1, var2);
      }

      protected A doBackward(B var1) {
         return this.backwardFunction.apply(var1);
      }

      protected B doForward(A var1) {
         return this.forwardFunction.apply(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Converter.FunctionBasedConverter;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Converter.FunctionBasedConverter var5 = (Converter.FunctionBasedConverter)var1;
            var4 = var3;
            if (this.forwardFunction.equals(var5.forwardFunction)) {
               var4 = var3;
               if (this.backwardFunction.equals(var5.backwardFunction)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Converter.from(");
         var1.append(this.forwardFunction);
         var1.append(", ");
         var1.append(this.backwardFunction);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {
      static final Converter.IdentityConverter<?> INSTANCE = new Converter.IdentityConverter();
      private static final long serialVersionUID = 0L;

      private Object readResolve() {
         return INSTANCE;
      }

      <S> Converter<T, S> doAndThen(Converter<T, S> var1) {
         return (Converter)Preconditions.checkNotNull(var1, "otherConverter");
      }

      protected T doBackward(T var1) {
         return var1;
      }

      protected T doForward(T var1) {
         return var1;
      }

      public Converter.IdentityConverter<T> reverse() {
         return this;
      }

      public String toString() {
         return "Converter.identity()";
      }
   }

   private static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {
      private static final long serialVersionUID = 0L;
      final Converter<A, B> original;

      ReverseConverter(Converter<A, B> var1) {
         this.original = var1;
      }

      @NullableDecl
      B correctedDoBackward(@NullableDecl A var1) {
         return this.original.correctedDoForward(var1);
      }

      @NullableDecl
      A correctedDoForward(@NullableDecl B var1) {
         return this.original.correctedDoBackward(var1);
      }

      protected B doBackward(A var1) {
         throw new AssertionError();
      }

      protected A doForward(B var1) {
         throw new AssertionError();
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Converter.ReverseConverter) {
            Converter.ReverseConverter var2 = (Converter.ReverseConverter)var1;
            return this.original.equals(var2.original);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.original.hashCode();
      }

      public Converter<A, B> reverse() {
         return this.original;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.original);
         var1.append(".reverse()");
         return var1.toString();
      }
   }
}
