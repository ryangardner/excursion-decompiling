package com.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ArrayListMultimap<K, V> extends ArrayListMultimapGwtSerializationDependencies<K, V> {
   private static final int DEFAULT_VALUES_PER_KEY = 3;
   private static final long serialVersionUID = 0L;
   transient int expectedValuesPerKey;

   private ArrayListMultimap() {
      this(12, 3);
   }

   private ArrayListMultimap(int var1, int var2) {
      super(Platform.newHashMapWithExpectedSize(var1));
      CollectPreconditions.checkNonnegative(var2, "expectedValuesPerKey");
      this.expectedValuesPerKey = var2;
   }

   private ArrayListMultimap(Multimap<? extends K, ? extends V> var1) {
      int var2 = var1.keySet().size();
      int var3;
      if (var1 instanceof ArrayListMultimap) {
         var3 = ((ArrayListMultimap)var1).expectedValuesPerKey;
      } else {
         var3 = 3;
      }

      this(var2, var3);
      this.putAll(var1);
   }

   public static <K, V> ArrayListMultimap<K, V> create() {
      return new ArrayListMultimap();
   }

   public static <K, V> ArrayListMultimap<K, V> create(int var0, int var1) {
      return new ArrayListMultimap(var0, var1);
   }

   public static <K, V> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new ArrayListMultimap(var0);
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.expectedValuesPerKey = 3;
      int var2 = Serialization.readCount(var1);
      this.setMap(CompactHashMap.create());
      Serialization.populateMultimap(this, var1, var2);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultimap(this, var1);
   }

   List<V> createCollection() {
      return new ArrayList(this.expectedValuesPerKey);
   }

   @Deprecated
   public void trimToSize() {
      Iterator var1 = this.backingMap().values().iterator();

      while(var1.hasNext()) {
         ((ArrayList)((Collection)var1.next())).trimToSize();
      }

   }
}
