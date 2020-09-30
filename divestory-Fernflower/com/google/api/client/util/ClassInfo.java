package com.google.api.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ClassInfo {
   private static final ConcurrentMap<Class<?>, ClassInfo> CACHE = new ConcurrentHashMap();
   private static final ConcurrentMap<Class<?>, ClassInfo> CACHE_IGNORE_CASE = new ConcurrentHashMap();
   private final Class<?> clazz;
   private final boolean ignoreCase;
   private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap = new IdentityHashMap();
   final List<String> names;

   private ClassInfo(Class<?> var1, boolean var2) {
      this.clazz = var1;
      this.ignoreCase = var2;
      boolean var3;
      if (var2 && var1.isEnum()) {
         var3 = false;
      } else {
         var3 = true;
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("cannot ignore case on an enum: ");
      var4.append(var1);
      Preconditions.checkArgument(var3, var4.toString());
      TreeSet var5 = new TreeSet(new Comparator<String>() {
         public int compare(String var1, String var2) {
            int var3;
            if (Objects.equal(var1, var2)) {
               var3 = 0;
            } else if (var1 == null) {
               var3 = -1;
            } else if (var2 == null) {
               var3 = 1;
            } else {
               var3 = var1.compareTo(var2);
            }

            return var3;
         }
      });
      Field[] var6 = var1.getDeclaredFields();
      int var7 = var6.length;

      String var11;
      for(int var8 = 0; var8 < var7; ++var8) {
         Field var9 = var6[var8];
         FieldInfo var10 = FieldInfo.of(var9);
         if (var10 != null) {
            var11 = var10.getName();
            String var16 = var11;
            if (var2) {
               var16 = var11.toLowerCase(Locale.US).intern();
            }

            FieldInfo var12 = (FieldInfo)this.nameToFieldInfoMap.get(var16);
            if (var12 == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var2) {
               var11 = "case-insensitive ";
            } else {
               var11 = "";
            }

            Field var18;
            if (var12 == null) {
               var18 = null;
            } else {
               var18 = var12.getField();
            }

            Preconditions.checkArgument(var3, "two fields have the same %sname <%s>: %s and %s", var11, var16, var9, var18);
            this.nameToFieldInfoMap.put(var16, var10);
            var5.add(var16);
         }
      }

      var1 = var1.getSuperclass();
      if (var1 != null) {
         ClassInfo var13 = of(var1, var2);
         var5.addAll(var13.names);
         Iterator var14 = var13.nameToFieldInfoMap.entrySet().iterator();

         while(var14.hasNext()) {
            Entry var17 = (Entry)var14.next();
            var11 = (String)var17.getKey();
            if (!this.nameToFieldInfoMap.containsKey(var11)) {
               this.nameToFieldInfoMap.put(var11, var17.getValue());
            }
         }
      }

      List var15;
      if (var5.isEmpty()) {
         var15 = Collections.emptyList();
      } else {
         var15 = Collections.unmodifiableList(new ArrayList(var5));
      }

      this.names = var15;
   }

   public static ClassInfo of(Class<?> var0) {
      return of(var0, false);
   }

   public static ClassInfo of(Class<?> var0, boolean var1) {
      if (var0 == null) {
         return null;
      } else {
         ConcurrentMap var2;
         if (var1) {
            var2 = CACHE_IGNORE_CASE;
         } else {
            var2 = CACHE;
         }

         ClassInfo var3 = (ClassInfo)var2.get(var0);
         ClassInfo var4 = var3;
         if (var3 == null) {
            var4 = new ClassInfo(var0, var1);
            ClassInfo var5 = (ClassInfo)var2.putIfAbsent(var0, var4);
            if (var5 != null) {
               var4 = var5;
            }
         }

         return var4;
      }
   }

   public Field getField(String var1) {
      FieldInfo var2 = this.getFieldInfo(var1);
      Field var3;
      if (var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getField();
      }

      return var3;
   }

   public FieldInfo getFieldInfo(String var1) {
      String var2 = var1;
      if (var1 != null) {
         var2 = var1;
         if (this.ignoreCase) {
            var2 = var1.toLowerCase(Locale.US);
         }

         var2 = var2.intern();
      }

      return (FieldInfo)this.nameToFieldInfoMap.get(var2);
   }

   public Collection<FieldInfo> getFieldInfos() {
      return Collections.unmodifiableCollection(this.nameToFieldInfoMap.values());
   }

   public final boolean getIgnoreCase() {
      return this.ignoreCase;
   }

   public Collection<String> getNames() {
      return this.names;
   }

   public Class<?> getUnderlyingClass() {
      return this.clazz;
   }

   public boolean isEnum() {
      return this.clazz.isEnum();
   }
}
