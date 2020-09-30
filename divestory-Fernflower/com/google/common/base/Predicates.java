package com.google.common.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Predicates {
   private Predicates() {
   }

   public static <T> Predicate<T> alwaysFalse() {
      return Predicates.ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
   }

   public static <T> Predicate<T> alwaysTrue() {
      return Predicates.ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
   }

   public static <T> Predicate<T> and(Predicate<? super T> var0, Predicate<? super T> var1) {
      return new Predicates.AndPredicate(asList((Predicate)Preconditions.checkNotNull(var0), (Predicate)Preconditions.checkNotNull(var1)));
   }

   public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> var0) {
      return new Predicates.AndPredicate(defensiveCopy(var0));
   }

   @SafeVarargs
   public static <T> Predicate<T> and(Predicate<? super T>... var0) {
      return new Predicates.AndPredicate(defensiveCopy((Object[])var0));
   }

   private static <T> List<Predicate<? super T>> asList(Predicate<? super T> var0, Predicate<? super T> var1) {
      return Arrays.asList(var0, var1);
   }

   public static <A, B> Predicate<A> compose(Predicate<B> var0, Function<A, ? extends B> var1) {
      return new Predicates.CompositionPredicate(var0, var1);
   }

   public static Predicate<CharSequence> contains(Pattern var0) {
      return new Predicates.ContainsPatternPredicate(new JdkPattern(var0));
   }

   public static Predicate<CharSequence> containsPattern(String var0) {
      return new Predicates.ContainsPatternFromStringPredicate(var0);
   }

   static <T> List<T> defensiveCopy(Iterable<T> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         var1.add(Preconditions.checkNotNull(var2.next()));
      }

      return var1;
   }

   private static <T> List<T> defensiveCopy(T... var0) {
      return defensiveCopy((Iterable)Arrays.asList(var0));
   }

   public static <T> Predicate<T> equalTo(@NullableDecl T var0) {
      if (var0 == null) {
         var0 = isNull();
      } else {
         var0 = new Predicates.IsEqualToPredicate(var0);
      }

      return (Predicate)var0;
   }

   public static <T> Predicate<T> in(Collection<? extends T> var0) {
      return new Predicates.InPredicate(var0);
   }

   public static Predicate<Object> instanceOf(Class<?> var0) {
      return new Predicates.InstanceOfPredicate(var0);
   }

   public static <T> Predicate<T> isNull() {
      return Predicates.ObjectPredicate.IS_NULL.withNarrowedType();
   }

   public static <T> Predicate<T> not(Predicate<T> var0) {
      return new Predicates.NotPredicate(var0);
   }

   public static <T> Predicate<T> notNull() {
      return Predicates.ObjectPredicate.NOT_NULL.withNarrowedType();
   }

   public static <T> Predicate<T> or(Predicate<? super T> var0, Predicate<? super T> var1) {
      return new Predicates.OrPredicate(asList((Predicate)Preconditions.checkNotNull(var0), (Predicate)Preconditions.checkNotNull(var1)));
   }

   public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> var0) {
      return new Predicates.OrPredicate(defensiveCopy(var0));
   }

   @SafeVarargs
   public static <T> Predicate<T> or(Predicate<? super T>... var0) {
      return new Predicates.OrPredicate(defensiveCopy((Object[])var0));
   }

   public static Predicate<Class<?>> subtypeOf(Class<?> var0) {
      return new Predicates.SubtypeOfPredicate(var0);
   }

   private static String toStringHelper(String var0, Iterable<?> var1) {
      StringBuilder var2 = new StringBuilder("Predicates.");
      var2.append(var0);
      var2.append('(');
      Iterator var4 = var1.iterator();

      for(boolean var3 = true; var4.hasNext(); var3 = false) {
         Object var5 = var4.next();
         if (!var3) {
            var2.append(',');
         }

         var2.append(var5);
      }

      var2.append(')');
      return var2.toString();
   }

   private static class AndPredicate<T> implements Predicate<T>, Serializable {
      private static final long serialVersionUID = 0L;
      private final List<? extends Predicate<? super T>> components;

      private AndPredicate(List<? extends Predicate<? super T>> var1) {
         this.components = var1;
      }

      // $FF: synthetic method
      AndPredicate(List var1, Object var2) {
         this(var1);
      }

      public boolean apply(@NullableDecl T var1) {
         for(int var2 = 0; var2 < this.components.size(); ++var2) {
            if (!((Predicate)this.components.get(var2)).apply(var1)) {
               return false;
            }
         }

         return true;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Predicates.AndPredicate) {
            Predicates.AndPredicate var2 = (Predicates.AndPredicate)var1;
            return this.components.equals(var2.components);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.components.hashCode() + 306654252;
      }

      public String toString() {
         return Predicates.toStringHelper("and", this.components);
      }
   }

   private static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {
      private static final long serialVersionUID = 0L;
      final Function<A, ? extends B> f;
      final Predicate<B> p;

      private CompositionPredicate(Predicate<B> var1, Function<A, ? extends B> var2) {
         this.p = (Predicate)Preconditions.checkNotNull(var1);
         this.f = (Function)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      CompositionPredicate(Predicate var1, Function var2, Object var3) {
         this(var1, var2);
      }

      public boolean apply(@NullableDecl A var1) {
         return this.p.apply(this.f.apply(var1));
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Predicates.CompositionPredicate;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Predicates.CompositionPredicate var5 = (Predicates.CompositionPredicate)var1;
            var4 = var3;
            if (this.f.equals(var5.f)) {
               var4 = var3;
               if (this.p.equals(var5.p)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.f.hashCode() ^ this.p.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.p);
         var1.append("(");
         var1.append(this.f);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class ContainsPatternFromStringPredicate extends Predicates.ContainsPatternPredicate {
      private static final long serialVersionUID = 0L;

      ContainsPatternFromStringPredicate(String var1) {
         super(Platform.compilePattern(var1));
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Predicates.containsPattern(");
         var1.append(this.pattern.pattern());
         var1.append(")");
         return var1.toString();
      }
   }

   private static class ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {
      private static final long serialVersionUID = 0L;
      final CommonPattern pattern;

      ContainsPatternPredicate(CommonPattern var1) {
         this.pattern = (CommonPattern)Preconditions.checkNotNull(var1);
      }

      public boolean apply(CharSequence var1) {
         return this.pattern.matcher(var1).find();
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Predicates.ContainsPatternPredicate;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Predicates.ContainsPatternPredicate var5 = (Predicates.ContainsPatternPredicate)var1;
            var4 = var3;
            if (Objects.equal(this.pattern.pattern(), var5.pattern.pattern())) {
               var4 = var3;
               if (this.pattern.flags() == var5.pattern.flags()) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return Objects.hashCode(this.pattern.pattern(), this.pattern.flags());
      }

      public String toString() {
         String var1 = MoreObjects.toStringHelper((Object)this.pattern).add("pattern", this.pattern.pattern()).add("pattern.flags", this.pattern.flags()).toString();
         StringBuilder var2 = new StringBuilder();
         var2.append("Predicates.contains(");
         var2.append(var1);
         var2.append(")");
         return var2.toString();
      }
   }

   private static class InPredicate<T> implements Predicate<T>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Collection<?> target;

      private InPredicate(Collection<?> var1) {
         this.target = (Collection)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      InPredicate(Collection var1, Object var2) {
         this(var1);
      }

      public boolean apply(@NullableDecl T var1) {
         try {
            boolean var2 = this.target.contains(var1);
            return var2;
         } catch (ClassCastException | NullPointerException var3) {
            return false;
         }
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Predicates.InPredicate) {
            Predicates.InPredicate var2 = (Predicates.InPredicate)var1;
            return this.target.equals(var2.target);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.target.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Predicates.in(");
         var1.append(this.target);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class InstanceOfPredicate implements Predicate<Object>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Class<?> clazz;

      private InstanceOfPredicate(Class<?> var1) {
         this.clazz = (Class)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      InstanceOfPredicate(Class var1, Object var2) {
         this(var1);
      }

      public boolean apply(@NullableDecl Object var1) {
         return this.clazz.isInstance(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Predicates.InstanceOfPredicate;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Predicates.InstanceOfPredicate var5 = (Predicates.InstanceOfPredicate)var1;
            var4 = var3;
            if (this.clazz == var5.clazz) {
               var4 = true;
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.clazz.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Predicates.instanceOf(");
         var1.append(this.clazz.getName());
         var1.append(")");
         return var1.toString();
      }
   }

   private static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
      private static final long serialVersionUID = 0L;
      private final T target;

      private IsEqualToPredicate(T var1) {
         this.target = var1;
      }

      // $FF: synthetic method
      IsEqualToPredicate(Object var1, Object var2) {
         this(var1);
      }

      public boolean apply(T var1) {
         return this.target.equals(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Predicates.IsEqualToPredicate) {
            Predicates.IsEqualToPredicate var2 = (Predicates.IsEqualToPredicate)var1;
            return this.target.equals(var2.target);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.target.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Predicates.equalTo(");
         var1.append(this.target);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class NotPredicate<T> implements Predicate<T>, Serializable {
      private static final long serialVersionUID = 0L;
      final Predicate<T> predicate;

      NotPredicate(Predicate<T> var1) {
         this.predicate = (Predicate)Preconditions.checkNotNull(var1);
      }

      public boolean apply(@NullableDecl T var1) {
         return this.predicate.apply(var1) ^ true;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Predicates.NotPredicate) {
            Predicates.NotPredicate var2 = (Predicates.NotPredicate)var1;
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
         var1.append("Predicates.not(");
         var1.append(this.predicate);
         var1.append(")");
         return var1.toString();
      }
   }

   static enum ObjectPredicate implements Predicate<Object> {
      ALWAYS_FALSE {
         public boolean apply(@NullableDecl Object var1) {
            return false;
         }

         public String toString() {
            return "Predicates.alwaysFalse()";
         }
      },
      ALWAYS_TRUE {
         public boolean apply(@NullableDecl Object var1) {
            return true;
         }

         public String toString() {
            return "Predicates.alwaysTrue()";
         }
      },
      IS_NULL {
         public boolean apply(@NullableDecl Object var1) {
            boolean var2;
            if (var1 == null) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }

         public String toString() {
            return "Predicates.isNull()";
         }
      },
      NOT_NULL;

      static {
         Predicates.ObjectPredicate var0 = new Predicates.ObjectPredicate("NOT_NULL", 3) {
            public boolean apply(@NullableDecl Object var1) {
               boolean var2;
               if (var1 != null) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               return var2;
            }

            public String toString() {
               return "Predicates.notNull()";
            }
         };
         NOT_NULL = var0;
      }

      private ObjectPredicate() {
      }

      // $FF: synthetic method
      ObjectPredicate(Object var3) {
         this();
      }

      <T> Predicate<T> withNarrowedType() {
         return this;
      }
   }

   private static class OrPredicate<T> implements Predicate<T>, Serializable {
      private static final long serialVersionUID = 0L;
      private final List<? extends Predicate<? super T>> components;

      private OrPredicate(List<? extends Predicate<? super T>> var1) {
         this.components = var1;
      }

      // $FF: synthetic method
      OrPredicate(List var1, Object var2) {
         this(var1);
      }

      public boolean apply(@NullableDecl T var1) {
         for(int var2 = 0; var2 < this.components.size(); ++var2) {
            if (((Predicate)this.components.get(var2)).apply(var1)) {
               return true;
            }
         }

         return false;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Predicates.OrPredicate) {
            Predicates.OrPredicate var2 = (Predicates.OrPredicate)var1;
            return this.components.equals(var2.components);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.components.hashCode() + 87855567;
      }

      public String toString() {
         return Predicates.toStringHelper("or", this.components);
      }
   }

   private static class SubtypeOfPredicate implements Predicate<Class<?>>, Serializable {
      private static final long serialVersionUID = 0L;
      private final Class<?> clazz;

      private SubtypeOfPredicate(Class<?> var1) {
         this.clazz = (Class)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      SubtypeOfPredicate(Class var1, Object var2) {
         this(var1);
      }

      public boolean apply(Class<?> var1) {
         return this.clazz.isAssignableFrom(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Predicates.SubtypeOfPredicate;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Predicates.SubtypeOfPredicate var5 = (Predicates.SubtypeOfPredicate)var1;
            var4 = var3;
            if (this.clazz == var5.clazz) {
               var4 = true;
            }
         }

         return var4;
      }

      public int hashCode() {
         return this.clazz.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Predicates.subtypeOf(");
         var1.append(this.clazz.getName());
         var1.append(")");
         return var1.toString();
      }
   }
}
