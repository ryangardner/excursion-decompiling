package com.google.common.cache;

public interface Weigher<K, V> {
   int weigh(K var1, V var2);
}
