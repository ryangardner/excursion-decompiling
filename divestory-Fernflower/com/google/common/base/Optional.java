package com.google.common.base;

import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use Optional.of(value) or Optional.absent()")
public abstract class Optional<T> implements Serializable {
   private static final long serialVersionUID = 0L;

   Optional() {
   }

   public static <T> Optional<T> absent() {
      return Absent.withType();
   }

   public static <T> Optional<T> fromNullable(@NullableDecl T var0) {
      if (var0 == null) {
         var0 = absent();
      } else {
         var0 = new Present(var0);
      }

      return (Optional)var0;
   }

   public static <T> Optional<T> of(T var0) {
      return new Present(Preconditions.checkNotNull(var0));
   }

   public static <T> Iterable<T> presentInstances(final Iterable<? extends Optional<? extends T>> var0) {
      Preconditions.checkNotNull(var0);
      return new Iterable<T>() {
         public Iterator<T> iterator() {
            return new AbstractIterator<T>() {
               private final Iterator<? extends Optional<? extends T>> iterator = (Iterator)Preconditions.checkNotNull(var0.iterator());

               protected T computeNext() {
                  while(true) {
                     if (this.iterator.hasNext()) {
                        Optional var1 = (Optional)this.iterator.next();
                        if (!var1.isPresent()) {
                           continue;
                        }

                        return var1.get();
                     }

                     return this.endOfData();
                  }
               }
            };
         }
      };
   }

   public abstract Set<T> asSet();

   public abstract boolean equals(@NullableDecl Object var1);

   public abstract T get();

   public abstract int hashCode();

   public abstract boolean isPresent();

   public abstract Optional<T> or(Optional<? extends T> var1);

   public abstract T or(Supplier<? extends T> var1);

   public abstract T or(T var1);

   @NullableDecl
   public abstract T orNull();

   public abstract String toString();

   public abstract <V> Optional<V> transform(Function<? super T, V> var1);
}
