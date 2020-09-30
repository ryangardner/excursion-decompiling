package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(
   containerOf = {"B"}
)
public final class ImmutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
   private static final ImmutableClassToInstanceMap<Object> EMPTY = new ImmutableClassToInstanceMap(ImmutableMap.of());
   private final ImmutableMap<Class<? extends B>, B> delegate;

   private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> var1) {
      this.delegate = var1;
   }

   // $FF: synthetic method
   ImmutableClassToInstanceMap(ImmutableMap var1, Object var2) {
      this(var1);
   }

   public static <B> ImmutableClassToInstanceMap.Builder<B> builder() {
      return new ImmutableClassToInstanceMap.Builder();
   }

   public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> var0) {
      return var0 instanceof ImmutableClassToInstanceMap ? (ImmutableClassToInstanceMap)var0 : (new ImmutableClassToInstanceMap.Builder()).putAll(var0).build();
   }

   public static <B> ImmutableClassToInstanceMap<B> of() {
      return EMPTY;
   }

   public static <B, T extends B> ImmutableClassToInstanceMap<B> of(Class<T> var0, T var1) {
      return new ImmutableClassToInstanceMap(ImmutableMap.of(var0, var1));
   }

   protected Map<Class<? extends B>, B> delegate() {
      return this.delegate;
   }

   @NullableDecl
   public <T extends B> T getInstance(Class<T> var1) {
      return this.delegate.get(Preconditions.checkNotNull(var1));
   }

   @Deprecated
   public <T extends B> T putInstance(Class<T> var1, T var2) {
      throw new UnsupportedOperationException();
   }

   Object readResolve() {
      ImmutableClassToInstanceMap var1;
      if (this.isEmpty()) {
         var1 = of();
      } else {
         var1 = this;
      }

      return var1;
   }

   public static final class Builder<B> {
      private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();

      private static <B, T extends B> T cast(Class<T> var0, B var1) {
         return Primitives.wrap(var0).cast(var1);
      }

      public ImmutableClassToInstanceMap<B> build() {
         ImmutableMap var1 = this.mapBuilder.build();
         return var1.isEmpty() ? ImmutableClassToInstanceMap.EMPTY : new ImmutableClassToInstanceMap(var1);
      }

      public <T extends B> ImmutableClassToInstanceMap.Builder<B> put(Class<T> var1, T var2) {
         this.mapBuilder.put(var1, var2);
         return this;
      }

      public <T extends B> ImmutableClassToInstanceMap.Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> var1) {
         Iterator var2 = var1.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Class var4 = (Class)var3.getKey();
            Object var5 = var3.getValue();
            this.mapBuilder.put(var4, cast(var4, var5));
         }

         return this;
      }
   }
}
