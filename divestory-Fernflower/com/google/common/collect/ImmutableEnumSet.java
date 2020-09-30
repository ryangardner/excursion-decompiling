package com.google.common.collect;

import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E> {
   private final transient EnumSet<E> delegate;
   @LazyInit
   private transient int hashCode;

   private ImmutableEnumSet(EnumSet<E> var1) {
      this.delegate = var1;
   }

   // $FF: synthetic method
   ImmutableEnumSet(EnumSet var1, Object var2) {
      this(var1);
   }

   static ImmutableSet asImmutable(EnumSet var0) {
      int var1 = var0.size();
      if (var1 != 0) {
         return (ImmutableSet)(var1 != 1 ? new ImmutableEnumSet(var0) : ImmutableSet.of(Iterables.getOnlyElement(var0)));
      } else {
         return ImmutableSet.of();
      }
   }

   public boolean contains(Object var1) {
      return this.delegate.contains(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      Object var2 = var1;
      if (var1 instanceof ImmutableEnumSet) {
         var2 = ((ImmutableEnumSet)var1).delegate;
      }

      return this.delegate.containsAll((Collection)var2);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         Object var2 = var1;
         if (var1 instanceof ImmutableEnumSet) {
            var2 = ((ImmutableEnumSet)var1).delegate;
         }

         return this.delegate.equals(var2);
      }
   }

   public int hashCode() {
      int var1 = this.hashCode;
      int var2 = var1;
      if (var1 == 0) {
         var2 = this.delegate.hashCode();
         this.hashCode = var2;
      }

      return var2;
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   boolean isHashCodeFast() {
      return true;
   }

   boolean isPartialView() {
      return false;
   }

   public UnmodifiableIterator<E> iterator() {
      return Iterators.unmodifiableIterator(this.delegate.iterator());
   }

   public int size() {
      return this.delegate.size();
   }

   public String toString() {
      return this.delegate.toString();
   }

   Object writeReplace() {
      return new ImmutableEnumSet.EnumSerializedForm(this.delegate);
   }

   private static class EnumSerializedForm<E extends Enum<E>> implements Serializable {
      private static final long serialVersionUID = 0L;
      final EnumSet<E> delegate;

      EnumSerializedForm(EnumSet<E> var1) {
         this.delegate = var1;
      }

      Object readResolve() {
         return new ImmutableEnumSet(this.delegate.clone());
      }
   }
}
