package com.google.common.cache;

import com.google.common.base.Preconditions;
import java.util.AbstractMap.SimpleImmutableEntry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class RemovalNotification<K, V> extends SimpleImmutableEntry<K, V> {
   private static final long serialVersionUID = 0L;
   private final RemovalCause cause;

   private RemovalNotification(@NullableDecl K var1, @NullableDecl V var2, RemovalCause var3) {
      super(var1, var2);
      this.cause = (RemovalCause)Preconditions.checkNotNull(var3);
   }

   public static <K, V> RemovalNotification<K, V> create(@NullableDecl K var0, @NullableDecl V var1, RemovalCause var2) {
      return new RemovalNotification(var0, var1, var2);
   }

   public RemovalCause getCause() {
      return this.cause;
   }

   public boolean wasEvicted() {
      return this.cause.wasEvicted();
   }
}
