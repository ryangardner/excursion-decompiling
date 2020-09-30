package com.google.common.collect;

import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface BiMap<K, V> extends Map<K, V> {
   @NullableDecl
   V forcePut(@NullableDecl K var1, @NullableDecl V var2);

   BiMap<V, K> inverse();

   @NullableDecl
   V put(@NullableDecl K var1, @NullableDecl V var2);

   void putAll(Map<? extends K, ? extends V> var1);

   Set<V> values();
}
