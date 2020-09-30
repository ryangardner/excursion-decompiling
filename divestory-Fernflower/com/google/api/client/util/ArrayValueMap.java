package com.google.api.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class ArrayValueMap {
   private final Object destination;
   private final Map<Field, ArrayValueMap.ArrayValue> fieldMap = ArrayMap.create();
   private final Map<String, ArrayValueMap.ArrayValue> keyMap = ArrayMap.create();

   public ArrayValueMap(Object var1) {
      this.destination = var1;
   }

   public void put(String var1, Class<?> var2, Object var3) {
      ArrayValueMap.ArrayValue var4 = (ArrayValueMap.ArrayValue)this.keyMap.get(var1);
      ArrayValueMap.ArrayValue var5 = var4;
      if (var4 == null) {
         var5 = new ArrayValueMap.ArrayValue(var2);
         this.keyMap.put(var1, var5);
      }

      var5.addValue(var2, var3);
   }

   public void put(Field var1, Class<?> var2, Object var3) {
      ArrayValueMap.ArrayValue var4 = (ArrayValueMap.ArrayValue)this.fieldMap.get(var1);
      ArrayValueMap.ArrayValue var5 = var4;
      if (var4 == null) {
         var5 = new ArrayValueMap.ArrayValue(var2);
         this.fieldMap.put(var1, var5);
      }

      var5.addValue(var2, var3);
   }

   public void setValues() {
      Iterator var1 = this.keyMap.entrySet().iterator();

      Entry var2;
      while(var1.hasNext()) {
         var2 = (Entry)var1.next();
         ((Map)this.destination).put(var2.getKey(), ((ArrayValueMap.ArrayValue)var2.getValue()).toArray());
      }

      var1 = this.fieldMap.entrySet().iterator();

      while(var1.hasNext()) {
         var2 = (Entry)var1.next();
         FieldInfo.setFieldValue((Field)var2.getKey(), this.destination, ((ArrayValueMap.ArrayValue)var2.getValue()).toArray());
      }

   }

   static class ArrayValue {
      final Class<?> componentType;
      final ArrayList<Object> values = new ArrayList();

      ArrayValue(Class<?> var1) {
         this.componentType = var1;
      }

      void addValue(Class<?> var1, Object var2) {
         boolean var3;
         if (var1 == this.componentType) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
         this.values.add(var2);
      }

      Object toArray() {
         return Types.toArray(this.values, this.componentType);
      }
   }
}
