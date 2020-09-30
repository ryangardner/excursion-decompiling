package com.google.common.base;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Enums {
   private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap();

   private Enums() {
   }

   static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> var0) {
      Map var1 = enumConstantCache;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label176: {
         Map var2;
         try {
            var2 = (Map)enumConstantCache.get(var0);
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label176;
         }

         Map var3 = var2;
         if (var2 == null) {
            try {
               var3 = populateCache(var0);
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            return var3;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public static Field getField(Enum<?> var0) {
      Class var1 = var0.getDeclaringClass();

      try {
         Field var3 = var1.getDeclaredField(var0.name());
         return var3;
      } catch (NoSuchFieldException var2) {
         throw new AssertionError(var2);
      }
   }

   public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> var0, String var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return Platform.getEnumIfPresent(var0, var1);
   }

   private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> var0) {
      HashMap var1 = new HashMap();
      Iterator var2 = EnumSet.allOf(var0).iterator();

      while(var2.hasNext()) {
         Enum var3 = (Enum)var2.next();
         var1.put(var3.name(), new WeakReference(var3));
      }

      enumConstantCache.put(var0, var1);
      return var1;
   }

   public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> var0) {
      return new Enums.StringConverter(var0);
   }

   private static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final Class<T> enumClass;

      StringConverter(Class<T> var1) {
         this.enumClass = (Class)Preconditions.checkNotNull(var1);
      }

      protected String doBackward(T var1) {
         return var1.name();
      }

      protected T doForward(String var1) {
         return Enum.valueOf(this.enumClass, var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Enums.StringConverter) {
            Enums.StringConverter var2 = (Enums.StringConverter)var1;
            return this.enumClass.equals(var2.enumClass);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.enumClass.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Enums.stringConverter(");
         var1.append(this.enumClass.getName());
         var1.append(".class)");
         return var1.toString();
      }
   }
}
