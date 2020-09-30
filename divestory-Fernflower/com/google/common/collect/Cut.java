package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
   private static final long serialVersionUID = 0L;
   @NullableDecl
   final C endpoint;

   Cut(@NullableDecl C var1) {
      this.endpoint = var1;
   }

   static <C extends Comparable> Cut<C> aboveAll() {
      return Cut.AboveAll.INSTANCE;
   }

   static <C extends Comparable> Cut<C> aboveValue(C var0) {
      return new Cut.AboveValue(var0);
   }

   static <C extends Comparable> Cut<C> belowAll() {
      return Cut.BelowAll.INSTANCE;
   }

   static <C extends Comparable> Cut<C> belowValue(C var0) {
      return new Cut.BelowValue(var0);
   }

   Cut<C> canonical(DiscreteDomain<C> var1) {
      return this;
   }

   public int compareTo(Cut<C> var1) {
      if (var1 == belowAll()) {
         return 1;
      } else if (var1 == aboveAll()) {
         return -1;
      } else {
         int var2 = Range.compareOrThrow(this.endpoint, var1.endpoint);
         return var2 != 0 ? var2 : Booleans.compare(this instanceof Cut.AboveValue, var1 instanceof Cut.AboveValue);
      }
   }

   abstract void describeAsLowerBound(StringBuilder var1);

   abstract void describeAsUpperBound(StringBuilder var1);

   C endpoint() {
      return this.endpoint;
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof Cut;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Cut var7 = (Cut)var1;

         int var5;
         try {
            var5 = this.compareTo(var7);
         } catch (ClassCastException var6) {
            var4 = var3;
            return var4;
         }

         var4 = var3;
         if (var5 == 0) {
            var4 = true;
         }
      }

      return var4;
   }

   abstract C greatestValueBelow(DiscreteDomain<C> var1);

   public abstract int hashCode();

   abstract boolean isLessThan(C var1);

   abstract C leastValueAbove(DiscreteDomain<C> var1);

   abstract BoundType typeAsLowerBound();

   abstract BoundType typeAsUpperBound();

   abstract Cut<C> withLowerBoundType(BoundType var1, DiscreteDomain<C> var2);

   abstract Cut<C> withUpperBoundType(BoundType var1, DiscreteDomain<C> var2);

   private static final class AboveAll extends Cut<Comparable<?>> {
      private static final Cut.AboveAll INSTANCE = new Cut.AboveAll();
      private static final long serialVersionUID = 0L;

      private AboveAll() {
         super((Comparable)null);
      }

      private Object readResolve() {
         return INSTANCE;
      }

      public int compareTo(Cut<Comparable<?>> var1) {
         byte var2;
         if (var1 == this) {
            var2 = 0;
         } else {
            var2 = 1;
         }

         return var2;
      }

      void describeAsLowerBound(StringBuilder var1) {
         throw new AssertionError();
      }

      void describeAsUpperBound(StringBuilder var1) {
         var1.append("+∞)");
      }

      Comparable<?> endpoint() {
         throw new IllegalStateException("range unbounded on this side");
      }

      Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> var1) {
         return var1.maxValue();
      }

      public int hashCode() {
         return System.identityHashCode(this);
      }

      boolean isLessThan(Comparable<?> var1) {
         return false;
      }

      Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> var1) {
         throw new AssertionError();
      }

      public String toString() {
         return "+∞";
      }

      BoundType typeAsLowerBound() {
         throw new AssertionError("this statement should be unreachable");
      }

      BoundType typeAsUpperBound() {
         throw new IllegalStateException();
      }

      Cut<Comparable<?>> withLowerBoundType(BoundType var1, DiscreteDomain<Comparable<?>> var2) {
         throw new AssertionError("this statement should be unreachable");
      }

      Cut<Comparable<?>> withUpperBoundType(BoundType var1, DiscreteDomain<Comparable<?>> var2) {
         throw new IllegalStateException();
      }
   }

   private static final class AboveValue<C extends Comparable> extends Cut<C> {
      private static final long serialVersionUID = 0L;

      AboveValue(C var1) {
         super((Comparable)Preconditions.checkNotNull(var1));
      }

      Cut<C> canonical(DiscreteDomain<C> var1) {
         Comparable var2 = this.leastValueAbove(var1);
         Cut var3;
         if (var2 != null) {
            var3 = belowValue(var2);
         } else {
            var3 = Cut.aboveAll();
         }

         return var3;
      }

      void describeAsLowerBound(StringBuilder var1) {
         var1.append('(');
         var1.append(this.endpoint);
      }

      void describeAsUpperBound(StringBuilder var1) {
         var1.append(this.endpoint);
         var1.append(']');
      }

      C greatestValueBelow(DiscreteDomain<C> var1) {
         return this.endpoint;
      }

      public int hashCode() {
         return this.endpoint.hashCode();
      }

      boolean isLessThan(C var1) {
         boolean var2;
         if (Range.compareOrThrow(this.endpoint, var1) < 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      C leastValueAbove(DiscreteDomain<C> var1) {
         return var1.next(this.endpoint);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("/");
         var1.append(this.endpoint);
         var1.append("\\");
         return var1.toString();
      }

      BoundType typeAsLowerBound() {
         return BoundType.OPEN;
      }

      BoundType typeAsUpperBound() {
         return BoundType.CLOSED;
      }

      Cut<C> withLowerBoundType(BoundType var1, DiscreteDomain<C> var2) {
         int var3 = null.$SwitchMap$com$google$common$collect$BoundType[var1.ordinal()];
         if (var3 != 1) {
            if (var3 == 2) {
               return this;
            } else {
               throw new AssertionError();
            }
         } else {
            Comparable var4 = var2.next(this.endpoint);
            Cut var5;
            if (var4 == null) {
               var5 = Cut.belowAll();
            } else {
               var5 = belowValue(var4);
            }

            return var5;
         }
      }

      Cut<C> withUpperBoundType(BoundType var1, DiscreteDomain<C> var2) {
         int var3 = null.$SwitchMap$com$google$common$collect$BoundType[var1.ordinal()];
         if (var3 != 1) {
            if (var3 == 2) {
               Comparable var4 = var2.next(this.endpoint);
               Cut var5;
               if (var4 == null) {
                  var5 = Cut.aboveAll();
               } else {
                  var5 = belowValue(var4);
               }

               return var5;
            } else {
               throw new AssertionError();
            }
         } else {
            return this;
         }
      }
   }

   private static final class BelowAll extends Cut<Comparable<?>> {
      private static final Cut.BelowAll INSTANCE = new Cut.BelowAll();
      private static final long serialVersionUID = 0L;

      private BelowAll() {
         super((Comparable)null);
      }

      private Object readResolve() {
         return INSTANCE;
      }

      Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> var1) {
         try {
            Cut var3 = Cut.belowValue(var1.minValue());
            return var3;
         } catch (NoSuchElementException var2) {
            return this;
         }
      }

      public int compareTo(Cut<Comparable<?>> var1) {
         byte var2;
         if (var1 == this) {
            var2 = 0;
         } else {
            var2 = -1;
         }

         return var2;
      }

      void describeAsLowerBound(StringBuilder var1) {
         var1.append("(-∞");
      }

      void describeAsUpperBound(StringBuilder var1) {
         throw new AssertionError();
      }

      Comparable<?> endpoint() {
         throw new IllegalStateException("range unbounded on this side");
      }

      Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> var1) {
         throw new AssertionError();
      }

      public int hashCode() {
         return System.identityHashCode(this);
      }

      boolean isLessThan(Comparable<?> var1) {
         return true;
      }

      Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> var1) {
         return var1.minValue();
      }

      public String toString() {
         return "-∞";
      }

      BoundType typeAsLowerBound() {
         throw new IllegalStateException();
      }

      BoundType typeAsUpperBound() {
         throw new AssertionError("this statement should be unreachable");
      }

      Cut<Comparable<?>> withLowerBoundType(BoundType var1, DiscreteDomain<Comparable<?>> var2) {
         throw new IllegalStateException();
      }

      Cut<Comparable<?>> withUpperBoundType(BoundType var1, DiscreteDomain<Comparable<?>> var2) {
         throw new AssertionError("this statement should be unreachable");
      }
   }

   private static final class BelowValue<C extends Comparable> extends Cut<C> {
      private static final long serialVersionUID = 0L;

      BelowValue(C var1) {
         super((Comparable)Preconditions.checkNotNull(var1));
      }

      void describeAsLowerBound(StringBuilder var1) {
         var1.append('[');
         var1.append(this.endpoint);
      }

      void describeAsUpperBound(StringBuilder var1) {
         var1.append(this.endpoint);
         var1.append(')');
      }

      C greatestValueBelow(DiscreteDomain<C> var1) {
         return var1.previous(this.endpoint);
      }

      public int hashCode() {
         return this.endpoint.hashCode();
      }

      boolean isLessThan(C var1) {
         boolean var2;
         if (Range.compareOrThrow(this.endpoint, var1) <= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      C leastValueAbove(DiscreteDomain<C> var1) {
         return this.endpoint;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("\\");
         var1.append(this.endpoint);
         var1.append("/");
         return var1.toString();
      }

      BoundType typeAsLowerBound() {
         return BoundType.CLOSED;
      }

      BoundType typeAsUpperBound() {
         return BoundType.OPEN;
      }

      Cut<C> withLowerBoundType(BoundType var1, DiscreteDomain<C> var2) {
         int var3 = null.$SwitchMap$com$google$common$collect$BoundType[var1.ordinal()];
         if (var3 != 1) {
            if (var3 == 2) {
               Comparable var4 = var2.previous(this.endpoint);
               Object var5;
               if (var4 == null) {
                  var5 = Cut.belowAll();
               } else {
                  var5 = new Cut.AboveValue(var4);
               }

               return (Cut)var5;
            } else {
               throw new AssertionError();
            }
         } else {
            return this;
         }
      }

      Cut<C> withUpperBoundType(BoundType var1, DiscreteDomain<C> var2) {
         int var3 = null.$SwitchMap$com$google$common$collect$BoundType[var1.ordinal()];
         if (var3 != 1) {
            if (var3 == 2) {
               return this;
            } else {
               throw new AssertionError();
            }
         } else {
            Comparable var4 = var2.previous(this.endpoint);
            Object var5;
            if (var4 == null) {
               var5 = Cut.aboveAll();
            } else {
               var5 = new Cut.AboveValue(var4);
            }

            return (Cut)var5;
         }
      }
   }
}
