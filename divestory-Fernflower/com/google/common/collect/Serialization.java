package com.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class Serialization {
   private Serialization() {
   }

   static <T> Serialization.FieldSetter<T> getFieldSetter(Class<T> var0, String var1) {
      try {
         Serialization.FieldSetter var3 = new Serialization.FieldSetter(var0.getDeclaredField(var1));
         return var3;
      } catch (NoSuchFieldException var2) {
         throw new AssertionError(var2);
      }
   }

   static <K, V> void populateMap(Map<K, V> var0, ObjectInputStream var1) throws IOException, ClassNotFoundException {
      populateMap(var0, var1, var1.readInt());
   }

   static <K, V> void populateMap(Map<K, V> var0, ObjectInputStream var1, int var2) throws IOException, ClassNotFoundException {
      for(int var3 = 0; var3 < var2; ++var3) {
         var0.put(var1.readObject(), var1.readObject());
      }

   }

   static <K, V> void populateMultimap(Multimap<K, V> var0, ObjectInputStream var1) throws IOException, ClassNotFoundException {
      populateMultimap(var0, var1, var1.readInt());
   }

   static <K, V> void populateMultimap(Multimap<K, V> var0, ObjectInputStream var1, int var2) throws IOException, ClassNotFoundException {
      for(int var3 = 0; var3 < var2; ++var3) {
         Collection var4 = var0.get(var1.readObject());
         int var5 = var1.readInt();

         for(int var6 = 0; var6 < var5; ++var6) {
            var4.add(var1.readObject());
         }
      }

   }

   static <E> void populateMultiset(Multiset<E> var0, ObjectInputStream var1) throws IOException, ClassNotFoundException {
      populateMultiset(var0, var1, var1.readInt());
   }

   static <E> void populateMultiset(Multiset<E> var0, ObjectInputStream var1, int var2) throws IOException, ClassNotFoundException {
      for(int var3 = 0; var3 < var2; ++var3) {
         var0.add(var1.readObject(), var1.readInt());
      }

   }

   static int readCount(ObjectInputStream var0) throws IOException {
      return var0.readInt();
   }

   static <K, V> void writeMap(Map<K, V> var0, ObjectOutputStream var1) throws IOException {
      var1.writeInt(var0.size());
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         var1.writeObject(var2.getKey());
         var1.writeObject(var2.getValue());
      }

   }

   static <K, V> void writeMultimap(Multimap<K, V> var0, ObjectOutputStream var1) throws IOException {
      var1.writeInt(var0.asMap().size());
      Iterator var3 = var0.asMap().entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         var1.writeObject(var2.getKey());
         var1.writeInt(((Collection)var2.getValue()).size());
         Iterator var4 = ((Collection)var2.getValue()).iterator();

         while(var4.hasNext()) {
            var1.writeObject(var4.next());
         }
      }

   }

   static <E> void writeMultiset(Multiset<E> var0, ObjectOutputStream var1) throws IOException {
      var1.writeInt(var0.entrySet().size());
      Iterator var2 = var0.entrySet().iterator();

      while(var2.hasNext()) {
         Multiset.Entry var3 = (Multiset.Entry)var2.next();
         var1.writeObject(var3.getElement());
         var1.writeInt(var3.getCount());
      }

   }

   static final class FieldSetter<T> {
      private final Field field;

      private FieldSetter(Field var1) {
         this.field = var1;
         var1.setAccessible(true);
      }

      // $FF: synthetic method
      FieldSetter(Field var1, Object var2) {
         this(var1);
      }

      void set(T var1, int var2) {
         try {
            this.field.set(var1, var2);
         } catch (IllegalAccessException var3) {
            throw new AssertionError(var3);
         }
      }

      void set(T var1, Object var2) {
         try {
            this.field.set(var1, var2);
         } catch (IllegalAccessException var3) {
            throw new AssertionError(var3);
         }
      }
   }
}
