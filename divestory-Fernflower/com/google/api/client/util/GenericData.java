package com.google.api.client.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class GenericData extends AbstractMap<String, Object> implements Cloneable {
   final ClassInfo classInfo;
   Map<String, Object> unknownFields;

   public GenericData() {
      this(EnumSet.noneOf(GenericData.Flags.class));
   }

   public GenericData(EnumSet<GenericData.Flags> var1) {
      this.unknownFields = ArrayMap.create();
      this.classInfo = ClassInfo.of(this.getClass(), var1.contains(GenericData.Flags.IGNORE_CASE));
   }

   public GenericData clone() {
      try {
         GenericData var1 = (GenericData)super.clone();
         Data.deepCopy(this, var1);
         var1.unknownFields = (Map)Data.clone(this.unknownFields);
         return var1;
      } catch (CloneNotSupportedException var2) {
         throw new IllegalStateException(var2);
      }
   }

   public Set<Entry<String, Object>> entrySet() {
      return new GenericData.EntrySet();
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 != null && var1 instanceof GenericData) {
         GenericData var3 = (GenericData)var1;
         if (!super.equals(var3) || !java.util.Objects.equals(this.classInfo, var3.classInfo)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public final Object get(Object var1) {
      if (!(var1 instanceof String)) {
         return null;
      } else {
         String var2 = (String)var1;
         FieldInfo var3 = this.classInfo.getFieldInfo(var2);
         if (var3 != null) {
            return var3.getValue(this);
         } else {
            String var4 = var2;
            if (this.classInfo.getIgnoreCase()) {
               var4 = var2.toLowerCase(Locale.US);
            }

            return this.unknownFields.get(var4);
         }
      }
   }

   public final ClassInfo getClassInfo() {
      return this.classInfo;
   }

   public final Map<String, Object> getUnknownKeys() {
      return this.unknownFields;
   }

   public int hashCode() {
      return java.util.Objects.hash(new Object[]{super.hashCode(), this.classInfo});
   }

   public final Object put(String var1, Object var2) {
      FieldInfo var3 = this.classInfo.getFieldInfo(var1);
      if (var3 != null) {
         Object var4 = var3.getValue(this);
         var3.setValue(this, var2);
         return var4;
      } else {
         String var5 = var1;
         if (this.classInfo.getIgnoreCase()) {
            var5 = var1.toLowerCase(Locale.US);
         }

         return this.unknownFields.put(var5, var2);
      }
   }

   public final void putAll(Map<? extends String, ?> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         this.set((String)var3.getKey(), var3.getValue());
      }

   }

   public final Object remove(Object var1) {
      if (!(var1 instanceof String)) {
         return null;
      } else {
         String var2 = (String)var1;
         if (this.classInfo.getFieldInfo(var2) == null) {
            String var3 = var2;
            if (this.classInfo.getIgnoreCase()) {
               var3 = var2.toLowerCase(Locale.US);
            }

            return this.unknownFields.remove(var3);
         } else {
            throw new UnsupportedOperationException();
         }
      }
   }

   public GenericData set(String var1, Object var2) {
      FieldInfo var3 = this.classInfo.getFieldInfo(var1);
      if (var3 != null) {
         var3.setValue(this, var2);
      } else {
         String var4 = var1;
         if (this.classInfo.getIgnoreCase()) {
            var4 = var1.toLowerCase(Locale.US);
         }

         this.unknownFields.put(var4, var2);
      }

      return this;
   }

   public final void setUnknownKeys(Map<String, Object> var1) {
      this.unknownFields = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("GenericData{classInfo=");
      var1.append(this.classInfo.names);
      var1.append(", ");
      var1.append(super.toString());
      var1.append("}");
      return var1.toString();
   }

   final class EntryIterator implements Iterator<Entry<String, Object>> {
      private final Iterator<Entry<String, Object>> fieldIterator;
      private boolean startedUnknown;
      private final Iterator<Entry<String, Object>> unknownIterator;

      EntryIterator(DataMap.EntrySet var2) {
         this.fieldIterator = var2.iterator();
         this.unknownIterator = GenericData.this.unknownFields.entrySet().iterator();
      }

      public boolean hasNext() {
         boolean var1;
         if (!this.fieldIterator.hasNext() && !this.unknownIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Entry<String, Object> next() {
         if (!this.startedUnknown) {
            if (this.fieldIterator.hasNext()) {
               return (Entry)this.fieldIterator.next();
            }

            this.startedUnknown = true;
         }

         return (Entry)this.unknownIterator.next();
      }

      public void remove() {
         if (this.startedUnknown) {
            this.unknownIterator.remove();
         }

         this.fieldIterator.remove();
      }
   }

   final class EntrySet extends AbstractSet<Entry<String, Object>> {
      private final DataMap.EntrySet dataEntrySet;

      EntrySet() {
         this.dataEntrySet = (new DataMap(GenericData.this, GenericData.this.classInfo.getIgnoreCase())).entrySet();
      }

      public void clear() {
         GenericData.this.unknownFields.clear();
         this.dataEntrySet.clear();
      }

      public Iterator<Entry<String, Object>> iterator() {
         return GenericData.this.new EntryIterator(this.dataEntrySet);
      }

      public int size() {
         return GenericData.this.unknownFields.size() + this.dataEntrySet.size();
      }
   }

   public static enum Flags {
      IGNORE_CASE;

      static {
         GenericData.Flags var0 = new GenericData.Flags("IGNORE_CASE", 0);
         IGNORE_CASE = var0;
      }
   }
}
