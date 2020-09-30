package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;

public final class Interners {
   private Interners() {
   }

   public static <E> Function<E, E> asFunction(Interner<E> var0) {
      return new Interners.InternerFunction((Interner)Preconditions.checkNotNull(var0));
   }

   public static Interners.InternerBuilder newBuilder() {
      return new Interners.InternerBuilder();
   }

   public static <E> Interner<E> newStrongInterner() {
      return newBuilder().strong().build();
   }

   public static <E> Interner<E> newWeakInterner() {
      return newBuilder().weak().build();
   }

   public static class InternerBuilder {
      private final MapMaker mapMaker;
      private boolean strong;

      private InternerBuilder() {
         this.mapMaker = new MapMaker();
         this.strong = true;
      }

      // $FF: synthetic method
      InternerBuilder(Object var1) {
         this();
      }

      public <E> Interner<E> build() {
         if (!this.strong) {
            this.mapMaker.weakKeys();
         }

         return new Interners.InternerImpl(this.mapMaker);
      }

      public Interners.InternerBuilder concurrencyLevel(int var1) {
         this.mapMaker.concurrencyLevel(var1);
         return this;
      }

      public Interners.InternerBuilder strong() {
         this.strong = true;
         return this;
      }

      public Interners.InternerBuilder weak() {
         this.strong = false;
         return this;
      }
   }

   private static class InternerFunction<E> implements Function<E, E> {
      private final Interner<E> interner;

      public InternerFunction(Interner<E> var1) {
         this.interner = var1;
      }

      public E apply(E var1) {
         return this.interner.intern(var1);
      }

      public boolean equals(Object var1) {
         if (var1 instanceof Interners.InternerFunction) {
            Interners.InternerFunction var2 = (Interners.InternerFunction)var1;
            return this.interner.equals(var2.interner);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.interner.hashCode();
      }
   }

   static final class InternerImpl<E> implements Interner<E> {
      final MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> map;

      private InternerImpl(MapMaker var1) {
         this.map = MapMakerInternalMap.createWithDummyValues(var1.keyEquivalence(Equivalence.equals()));
      }

      // $FF: synthetic method
      InternerImpl(MapMaker var1, Object var2) {
         this(var1);
      }

      public E intern(E var1) {
         do {
            MapMakerInternalMap.InternalEntry var2 = this.map.getEntry(var1);
            if (var2 != null) {
               Object var3 = var2.getKey();
               if (var3 != null) {
                  return var3;
               }
            }
         } while((MapMaker.Dummy)this.map.putIfAbsent(var1, MapMaker.Dummy.VALUE) != null);

         return var1;
      }
   }
}
