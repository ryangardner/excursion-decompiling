package com.google.common.base;

import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Absent<T> extends Optional<T> {
   static final Absent<Object> INSTANCE = new Absent();
   private static final long serialVersionUID = 0L;

   private Absent() {
   }

   private Object readResolve() {
      return INSTANCE;
   }

   static <T> Optional<T> withType() {
      return INSTANCE;
   }

   public Set<T> asSet() {
      return Collections.emptySet();
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2;
      if (var1 == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public T get() {
      throw new IllegalStateException("Optional.get() cannot be called on an absent value");
   }

   public int hashCode() {
      return 2040732332;
   }

   public boolean isPresent() {
      return false;
   }

   public Optional<T> or(Optional<? extends T> var1) {
      return (Optional)Preconditions.checkNotNull(var1);
   }

   public T or(Supplier<? extends T> var1) {
      return Preconditions.checkNotNull(var1.get(), "use Optional.orNull() instead of a Supplier that returns null");
   }

   public T or(T var1) {
      return Preconditions.checkNotNull(var1, "use Optional.orNull() instead of Optional.or(null)");
   }

   @NullableDecl
   public T orNull() {
      return null;
   }

   public String toString() {
      return "Optional.absent()";
   }

   public <V> Optional<V> transform(Function<? super T, V> var1) {
      Preconditions.checkNotNull(var1);
      return Optional.absent();
   }
}
