package com.google.api.client.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

final class DataMap extends AbstractMap<String, Object> {
   final ClassInfo classInfo;
   final Object object;

   DataMap(Object var1, boolean var2) {
      this.object = var1;
      this.classInfo = ClassInfo.of(var1.getClass(), var2);
   }

   public boolean containsKey(Object var1) {
      boolean var2;
      if (this.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public DataMap.EntrySet entrySet() {
      return new DataMap.EntrySet();
   }

   public Object get(Object var1) {
      if (!(var1 instanceof String)) {
         return null;
      } else {
         FieldInfo var2 = this.classInfo.getFieldInfo((String)var1);
         return var2 == null ? null : var2.getValue(this.object);
      }
   }

   public Object put(String var1, Object var2) {
      FieldInfo var3 = this.classInfo.getFieldInfo(var1);
      StringBuilder var4 = new StringBuilder();
      var4.append("no field of key ");
      var4.append(var1);
      Preconditions.checkNotNull(var3, var4.toString());
      Object var5 = var3.getValue(this.object);
      var3.setValue(this.object, Preconditions.checkNotNull(var2));
      return var5;
   }

   final class Entry implements java.util.Map.Entry<String, Object> {
      private final FieldInfo fieldInfo;
      private Object fieldValue;

      Entry(FieldInfo var2, Object var3) {
         this.fieldInfo = var2;
         this.fieldValue = Preconditions.checkNotNull(var3);
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry var3 = (java.util.Map.Entry)var1;
            if (!this.getKey().equals(var3.getKey()) || !this.getValue().equals(var3.getValue())) {
               var2 = false;
            }

            return var2;
         }
      }

      public String getKey() {
         String var1 = this.fieldInfo.getName();
         String var2 = var1;
         if (DataMap.this.classInfo.getIgnoreCase()) {
            var2 = var1.toLowerCase(Locale.US);
         }

         return var2;
      }

      public Object getValue() {
         return this.fieldValue;
      }

      public int hashCode() {
         return this.getKey().hashCode() ^ this.getValue().hashCode();
      }

      public Object setValue(Object var1) {
         Object var2 = this.fieldValue;
         this.fieldValue = Preconditions.checkNotNull(var1);
         this.fieldInfo.setValue(DataMap.this.object, var1);
         return var2;
      }
   }

   final class EntryIterator implements Iterator<java.util.Map.Entry<String, Object>> {
      private FieldInfo currentFieldInfo;
      private boolean isComputed;
      private boolean isRemoved;
      private FieldInfo nextFieldInfo;
      private Object nextFieldValue;
      private int nextKeyIndex = -1;

      public boolean hasNext() {
         boolean var1 = this.isComputed;
         boolean var2 = true;
         if (!var1) {
            this.isComputed = true;

            FieldInfo var4;
            for(this.nextFieldValue = null; this.nextFieldValue == null; this.nextFieldValue = var4.getValue(DataMap.this.object)) {
               int var3 = this.nextKeyIndex + 1;
               this.nextKeyIndex = var3;
               if (var3 >= DataMap.this.classInfo.names.size()) {
                  break;
               }

               var4 = DataMap.this.classInfo.getFieldInfo((String)DataMap.this.classInfo.names.get(this.nextKeyIndex));
               this.nextFieldInfo = var4;
            }
         }

         if (this.nextFieldValue == null) {
            var2 = false;
         }

         return var2;
      }

      public java.util.Map.Entry<String, Object> next() {
         if (this.hasNext()) {
            this.currentFieldInfo = this.nextFieldInfo;
            Object var1 = this.nextFieldValue;
            this.isComputed = false;
            this.isRemoved = false;
            this.nextFieldInfo = null;
            this.nextFieldValue = null;
            return DataMap.this.new Entry(this.currentFieldInfo, var1);
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         boolean var1;
         if (this.currentFieldInfo != null && !this.isRemoved) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1);
         this.isRemoved = true;
         this.currentFieldInfo.setValue(DataMap.this.object, (Object)null);
      }
   }

   final class EntrySet extends AbstractSet<java.util.Map.Entry<String, Object>> {
      public void clear() {
         Iterator var1 = DataMap.this.classInfo.names.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            DataMap.this.classInfo.getFieldInfo(var2).setValue(DataMap.this.object, (Object)null);
         }

      }

      public boolean isEmpty() {
         Iterator var1 = DataMap.this.classInfo.names.iterator();

         String var2;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            var2 = (String)var1.next();
         } while(DataMap.this.classInfo.getFieldInfo(var2).getValue(DataMap.this.object) == null);

         return false;
      }

      public DataMap.EntryIterator iterator() {
         return DataMap.this.new EntryIterator();
      }

      public int size() {
         Iterator var1 = DataMap.this.classInfo.names.iterator();
         int var2 = 0;

         while(var1.hasNext()) {
            String var3 = (String)var1.next();
            if (DataMap.this.classInfo.getFieldInfo(var3).getValue(DataMap.this.object) != null) {
               ++var2;
            }
         }

         return var2;
      }
   }
}
