package com.google.common.base;

import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Present<T> extends Optional<T> {
   private static final long serialVersionUID = 0L;
   private final T reference;

   Present(T var1) {
      this.reference = var1;
   }

   public Set<T> asSet() {
      return Collections.singleton(this.reference);
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 instanceof Present) {
         Present var2 = (Present)var1;
         return this.reference.equals(var2.reference);
      } else {
         return false;
      }
   }

   public T get() {
      return this.reference;
   }

   public int hashCode() {
      return this.reference.hashCode() + 1502476572;
   }

   public boolean isPresent() {
      return true;
   }

   public Optional<T> or(Optional<? extends T> var1) {
      Preconditions.checkNotNull(var1);
      return this;
   }

   public T or(Supplier<? extends T> var1) {
      Preconditions.checkNotNull(var1);
      return this.reference;
   }

   public T or(T var1) {
      Preconditions.checkNotNull(var1, "use Optional.orNull() instead of Optional.or(null)");
      return this.reference;
   }

   public T orNull() {
      return this.reference;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Optional.of(");
      var1.append(this.reference);
      var1.append(")");
      return var1.toString();
   }

   public <V> Optional<V> transform(Function<? super T, V> var1) {
      return new Present(Preconditions.checkNotNull(var1.apply(this.reference), "the Function passed to Optional.transform() must not return null."));
   }
}
