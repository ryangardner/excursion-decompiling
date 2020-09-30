package com.google.api.client.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
   public static final BigDecimal NULL_BIG_DECIMAL = new BigDecimal("0");
   public static final BigInteger NULL_BIG_INTEGER = new BigInteger("0");
   public static final Boolean NULL_BOOLEAN = new Boolean(true);
   public static final Byte NULL_BYTE = new Byte((byte)0);
   private static final ConcurrentHashMap<Class<?>, Object> NULL_CACHE;
   public static final Character NULL_CHARACTER = new Character('\u0000');
   public static final DateTime NULL_DATE_TIME = new DateTime(0L);
   public static final Double NULL_DOUBLE = new Double(0.0D);
   public static final Float NULL_FLOAT = new Float(0.0F);
   public static final Integer NULL_INTEGER = new Integer(0);
   public static final Long NULL_LONG = new Long(0L);
   public static final Short NULL_SHORT = new Short((short)0);
   public static final String NULL_STRING = new String();

   static {
      ConcurrentHashMap var0 = new ConcurrentHashMap();
      NULL_CACHE = var0;
      var0.put(Boolean.class, NULL_BOOLEAN);
      NULL_CACHE.put(String.class, NULL_STRING);
      NULL_CACHE.put(Character.class, NULL_CHARACTER);
      NULL_CACHE.put(Byte.class, NULL_BYTE);
      NULL_CACHE.put(Short.class, NULL_SHORT);
      NULL_CACHE.put(Integer.class, NULL_INTEGER);
      NULL_CACHE.put(Float.class, NULL_FLOAT);
      NULL_CACHE.put(Long.class, NULL_LONG);
      NULL_CACHE.put(Double.class, NULL_DOUBLE);
      NULL_CACHE.put(BigInteger.class, NULL_BIG_INTEGER);
      NULL_CACHE.put(BigDecimal.class, NULL_BIG_DECIMAL);
      NULL_CACHE.put(DateTime.class, NULL_DATE_TIME);
   }

   public static <T> T clone(T var0) {
      if (var0 != null && !isPrimitive(var0.getClass())) {
         if (var0 instanceof GenericData) {
            return ((GenericData)var0).clone();
         } else {
            Class var1 = var0.getClass();
            Object var3;
            if (var1.isArray()) {
               var3 = Array.newInstance(var1.getComponentType(), Array.getLength(var0));
            } else if (var0 instanceof ArrayMap) {
               var3 = ((ArrayMap)var0).clone();
            } else {
               if ("java.util.Arrays$ArrayList".equals(var1.getName())) {
                  Object[] var2 = ((List)var0).toArray();
                  deepCopy(var2, var2);
                  return Arrays.asList(var2);
               }

               var3 = Types.newInstance(var1);
            }

            deepCopy(var0, var3);
            return var3;
         }
      } else {
         return var0;
      }
   }

   private static Object createNullInstance(Class<?> var0) {
      boolean var1 = var0.isArray();
      int var2 = 0;
      if (!var1) {
         if (var0.isEnum()) {
            FieldInfo var5 = ClassInfo.of(var0).getFieldInfo((String)null);
            Preconditions.checkNotNull(var5, "enum missing constant with @NullValue annotation: %s", var0);
            return var5.enumValue();
         } else {
            return Types.newInstance(var0);
         }
      } else {
         Class var3;
         int var4;
         do {
            var3 = var0.getComponentType();
            var4 = var2 + 1;
            var2 = var4;
            var0 = var3;
         } while(var3.isArray());

         return Array.newInstance(var3, new int[var4]);
      }
   }

   public static void deepCopy(Object var0, Object var1) {
      Class var2 = var0.getClass();
      Class var3 = var1.getClass();
      boolean var4 = true;
      int var5 = 0;
      byte var6 = 0;
      boolean var7;
      if (var2 == var3) {
         var7 = true;
      } else {
         var7 = false;
      }

      Preconditions.checkArgument(var7);
      Iterator var10;
      if (var2.isArray()) {
         if (Array.getLength(var0) == Array.getLength(var1)) {
            var7 = var4;
         } else {
            var7 = false;
         }

         Preconditions.checkArgument(var7);
         var10 = Types.iterableOf(var0).iterator();

         for(var5 = var6; var10.hasNext(); ++var5) {
            Array.set(var1, var5, clone(var10.next()));
         }
      } else if (Collection.class.isAssignableFrom(var2)) {
         Collection var11 = (Collection)var0;
         if (ArrayList.class.isAssignableFrom(var2)) {
            ((ArrayList)var1).ensureCapacity(var11.size());
         }

         Collection var12 = (Collection)var1;
         var10 = var11.iterator();

         while(var10.hasNext()) {
            var12.add(clone(var10.next()));
         }
      } else {
         var7 = GenericData.class.isAssignableFrom(var2);
         if (!var7 && Map.class.isAssignableFrom(var2)) {
            if (ArrayMap.class.isAssignableFrom(var2)) {
               ArrayMap var14 = (ArrayMap)var1;
               ArrayMap var13 = (ArrayMap)var0;

               for(int var20 = var13.size(); var5 < var20; ++var5) {
                  var14.set(var5, clone(var13.getValue(var5)));
               }
            } else {
               Map var16 = (Map)var1;
               Iterator var18 = ((Map)var0).entrySet().iterator();

               while(var18.hasNext()) {
                  Entry var15 = (Entry)var18.next();
                  var16.put(var15.getKey(), clone(var15.getValue()));
               }
            }
         } else {
            ClassInfo var17;
            if (var7) {
               var17 = ((GenericData)var0).classInfo;
            } else {
               var17 = ClassInfo.of(var2);
            }

            Iterator var8 = var17.names.iterator();

            while(true) {
               FieldInfo var9;
               do {
                  do {
                     if (!var8.hasNext()) {
                        return;
                     }

                     var9 = var17.getFieldInfo((String)var8.next());
                  } while(var9.isFinal());
               } while(var7 && var9.isPrimitive());

               Object var19 = var9.getValue(var0);
               if (var19 != null) {
                  var9.setValue(var1, clone(var19));
               }
            }
         }
      }

   }

   public static boolean isNull(Object var0) {
      boolean var1;
      if (var0 != null && var0 == NULL_CACHE.get(var0.getClass())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPrimitive(Type var0) {
      Type var1 = var0;
      if (var0 instanceof WildcardType) {
         var1 = Types.getBound((WildcardType)var0);
      }

      boolean var2 = var1 instanceof Class;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         Class var4 = (Class)var1;
         if (var4.isPrimitive() || var4 == Character.class || var4 == String.class || var4 == Integer.class || var4 == Long.class || var4 == Short.class || var4 == Byte.class || var4 == Float.class || var4 == Double.class || var4 == BigInteger.class || var4 == BigDecimal.class || var4 == DateTime.class || var4 == Boolean.class) {
            var3 = true;
         }

         return var3;
      }
   }

   public static boolean isValueOfPrimitiveType(Object var0) {
      boolean var1;
      if (var0 != null && !isPrimitive(var0.getClass())) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static Map<String, Object> mapOf(Object var0) {
      if (var0 != null && !isNull(var0)) {
         return (Map)(var0 instanceof Map ? (Map)var0 : new DataMap(var0, false));
      } else {
         return Collections.emptyMap();
      }
   }

   public static Collection<Object> newCollectionInstance(Type var0) {
      Type var1 = var0;
      if (var0 instanceof WildcardType) {
         var1 = Types.getBound((WildcardType)var0);
      }

      var0 = var1;
      if (var1 instanceof ParameterizedType) {
         var0 = ((ParameterizedType)var1).getRawType();
      }

      Class var2;
      if (var0 instanceof Class) {
         var2 = (Class)var0;
      } else {
         var2 = null;
      }

      if (var0 != null && !(var0 instanceof GenericArrayType) && (var2 == null || !var2.isArray() && !var2.isAssignableFrom(ArrayList.class))) {
         if (var2 != null) {
            if (var2.isAssignableFrom(HashSet.class)) {
               return new HashSet();
            } else {
               return (Collection)(var2.isAssignableFrom(TreeSet.class) ? new TreeSet() : (Collection)Types.newInstance(var2));
            }
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("unable to create new instance of type: ");
            var3.append(var0);
            throw new IllegalArgumentException(var3.toString());
         }
      } else {
         return new ArrayList();
      }
   }

   public static Map<String, Object> newMapInstance(Class<?> var0) {
      if (var0 != null && !var0.isAssignableFrom(ArrayMap.class)) {
         return (Map)(var0.isAssignableFrom(TreeMap.class) ? new TreeMap() : (Map)Types.newInstance(var0));
      } else {
         return ArrayMap.create();
      }
   }

   public static <T> T nullOf(Class<T> var0) {
      Object var1 = NULL_CACHE.get(var0);
      Object var2 = var1;
      if (var1 == null) {
         var2 = createNullInstance(var0);
         Object var3 = NULL_CACHE.putIfAbsent(var0, var2);
         if (var3 != null) {
            var2 = var3;
         }
      }

      return var2;
   }

   public static Object parsePrimitiveValue(Type var0, String var1) {
      Class var2;
      if (var0 instanceof Class) {
         var2 = (Class)var0;
      } else {
         var2 = null;
      }

      if (var0 == null || var2 != null) {
         if (var2 == Void.class) {
            return null;
         }

         if (var1 == null || var2 == null || var2.isAssignableFrom(String.class)) {
            return var1;
         }

         if (var2 == Character.class || var2 == Character.TYPE) {
            if (var1.length() == 1) {
               return var1.charAt(0);
            }

            StringBuilder var3 = new StringBuilder();
            var3.append("expected type Character/char but got ");
            var3.append(var2);
            throw new IllegalArgumentException(var3.toString());
         }

         if (var2 == Boolean.class || var2 == Boolean.TYPE) {
            return Boolean.valueOf(var1);
         }

         if (var2 == Byte.class || var2 == Byte.TYPE) {
            return Byte.valueOf(var1);
         }

         if (var2 == Short.class || var2 == Short.TYPE) {
            return Short.valueOf(var1);
         }

         if (var2 == Integer.class || var2 == Integer.TYPE) {
            return Integer.valueOf(var1);
         }

         if (var2 == Long.class || var2 == Long.TYPE) {
            return Long.valueOf(var1);
         }

         if (var2 == Float.class || var2 == Float.TYPE) {
            return Float.valueOf(var1);
         }

         if (var2 == Double.class || var2 == Double.TYPE) {
            return Double.valueOf(var1);
         }

         if (var2 == DateTime.class) {
            return DateTime.parseRfc3339(var1);
         }

         if (var2 == BigInteger.class) {
            return new BigInteger(var1);
         }

         if (var2 == BigDecimal.class) {
            return new BigDecimal(var1);
         }

         if (var2.isEnum()) {
            if (ClassInfo.of(var2).names.contains(var1)) {
               return ClassInfo.of(var2).getFieldInfo(var1).enumValue();
            }

            throw new IllegalArgumentException(String.format("given enum name %s not part of enumeration", var1));
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("expected primitive class, but got: ");
      var4.append(var0);
      throw new IllegalArgumentException(var4.toString());
   }

   public static Type resolveWildcardTypeOrTypeVariable(List<Type> var0, Type var1) {
      Type var2 = var1;
      if (var1 instanceof WildcardType) {
         var2 = Types.getBound((WildcardType)var1);
      }

      while(var2 instanceof TypeVariable) {
         Type var3 = Types.resolveTypeVariable(var0, (TypeVariable)var2);
         var1 = var2;
         if (var3 != null) {
            var1 = var3;
         }

         var2 = var1;
         if (var1 instanceof TypeVariable) {
            var2 = ((TypeVariable)var1).getBounds()[0];
         }
      }

      return var2;
   }
}
