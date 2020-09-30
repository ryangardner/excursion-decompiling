package com.google.common.collect;

import com.google.common.base.Strings;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

final class Platform {
   private static final String GWT_RPC_PROPERTY_NAME = "guava.gwt.emergency_reenable_rpc";

   private Platform() {
   }

   static void checkGwtRpcEnabled() {
      if (!Boolean.parseBoolean(System.getProperty("guava.gwt.emergency_reenable_rpc", "true"))) {
         throw new UnsupportedOperationException(Strings.lenientFormat("We are removing GWT-RPC support for Guava types. You can temporarily reenable support by setting the system property %s to true. For more about system properties, see %s. For more about Guava's GWT-RPC support, see %s.", "guava.gwt.emergency_reenable_rpc", "https://stackoverflow.com/q/5189914/28465", "https://groups.google.com/d/msg/guava-announce/zHZTFg7YF3o/rQNnwdHeEwAJ"));
      }
   }

   static <T> T[] copy(Object[] var0, int var1, int var2, T[] var3) {
      return Arrays.copyOfRange(var0, var1, var2, var3.getClass());
   }

   static <T> T[] newArray(T[] var0, int var1) {
      return (Object[])Array.newInstance(var0.getClass().getComponentType(), var1);
   }

   static <K, V> Map<K, V> newHashMapWithExpectedSize(int var0) {
      return CompactHashMap.createWithExpectedSize(var0);
   }

   static <E> Set<E> newHashSetWithExpectedSize(int var0) {
      return CompactHashSet.createWithExpectedSize(var0);
   }

   static <K, V> Map<K, V> newLinkedHashMapWithExpectedSize(int var0) {
      return CompactLinkedHashMap.createWithExpectedSize(var0);
   }

   static <E> Set<E> newLinkedHashSetWithExpectedSize(int var0) {
      return CompactLinkedHashSet.createWithExpectedSize(var0);
   }

   static <E> Set<E> preservesInsertionOrderOnAddsSet() {
      return CompactHashSet.create();
   }

   static <K, V> Map<K, V> preservesInsertionOrderOnPutsMap() {
      return CompactHashMap.create();
   }

   static int reduceExponentIfGwt(int var0) {
      return var0;
   }

   static int reduceIterationsIfGwt(int var0) {
      return var0;
   }

   static MapMaker tryWeakKeys(MapMaker var0) {
      return var0.weakKeys();
   }
}
