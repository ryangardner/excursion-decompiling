package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

public final class HashMultimap<K, V> extends HashMultimapGwtSerializationDependencies<K, V> {
   private static final int DEFAULT_VALUES_PER_KEY = 2;
   private static final long serialVersionUID = 0L;
   transient int expectedValuesPerKey;

   private HashMultimap() {
      this(12, 2);
   }

   private HashMultimap(int var1, int var2) {
      super(Platform.newHashMapWithExpectedSize(var1));
      this.expectedValuesPerKey = 2;
      boolean var3;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.expectedValuesPerKey = var2;
   }

   private HashMultimap(Multimap<? extends K, ? extends V> var1) {
      super(Platform.newHashMapWithExpectedSize(var1.keySet().size()));
      this.expectedValuesPerKey = 2;
      this.putAll(var1);
   }

   public static <K, V> HashMultimap<K, V> create() {
      return new HashMultimap();
   }

   public static <K, V> HashMultimap<K, V> create(int var0, int var1) {
      return new HashMultimap(var0, var1);
   }

   public static <K, V> HashMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new HashMultimap(var0);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.expectedValuesPerKey = 2;
      int var2 = Serialization.readCount(var1);
      this.setMap(Platform.newHashMapWithExpectedSize(12));
      Serialization.populateMultimap(this, var1, var2);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultimap(this, var1);
   }

   Set<V> createCollection() {
      return Platform.newHashSetWithExpectedSize(this.expectedValuesPerKey);
   }
}
