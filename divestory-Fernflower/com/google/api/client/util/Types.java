package com.google.api.client.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Types {
   private Types() {
   }

   private static Type getActualParameterAtPosition(Type var0, Class<?> var1, int var2) {
      ParameterizedType var3 = getSuperParameterizedType(var0, var1);
      if (var3 == null) {
         return null;
      } else {
         Type var4 = var3.getActualTypeArguments()[var2];
         if (var4 instanceof TypeVariable) {
            var0 = resolveTypeVariable(Arrays.asList(var0), (TypeVariable)var4);
            if (var0 != null) {
               return var0;
            }
         }

         return var4;
      }
   }

   public static Type getArrayComponentType(Type var0) {
      Object var1;
      if (var0 instanceof GenericArrayType) {
         var1 = ((GenericArrayType)var0).getGenericComponentType();
      } else {
         var1 = ((Class)var0).getComponentType();
      }

      return (Type)var1;
   }

   public static Type getBound(WildcardType var0) {
      Type[] var1 = var0.getLowerBounds();
      return var1.length != 0 ? var1[0] : var0.getUpperBounds()[0];
   }

   public static Type getIterableParameter(Type var0) {
      return getActualParameterAtPosition(var0, Iterable.class, 0);
   }

   public static Type getMapValueParameter(Type var0) {
      return getActualParameterAtPosition(var0, Map.class, 1);
   }

   public static Class<?> getRawArrayComponentType(List<Type> var0, Type var1) {
      Type var2 = var1;
      if (var1 instanceof TypeVariable) {
         var2 = resolveTypeVariable(var0, (TypeVariable)var1);
      }

      if (var2 instanceof GenericArrayType) {
         return Array.newInstance(getRawArrayComponentType(var0, getArrayComponentType(var2)), 0).getClass();
      } else if (var2 instanceof Class) {
         return (Class)var2;
      } else if (var2 instanceof ParameterizedType) {
         return getRawClass((ParameterizedType)var2);
      } else {
         boolean var3;
         if (var2 == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "wildcard type is not supported: %s", var2);
         return Object.class;
      }
   }

   public static Class<?> getRawClass(ParameterizedType var0) {
      return (Class)var0.getRawType();
   }

   public static ParameterizedType getSuperParameterizedType(Type var0, Class<?> var1) {
      Type var2 = var0;
      if (!(var0 instanceof Class)) {
         if (!(var0 instanceof ParameterizedType)) {
            return null;
         }

         var2 = var0;
      }

      label50:
      while(var2 != null && var2 != Object.class) {
         Class var7;
         if (var2 instanceof Class) {
            var7 = (Class)var2;
         } else {
            ParameterizedType var8 = (ParameterizedType)var2;
            Class var9 = getRawClass(var8);
            if (var9 == var1) {
               return var8;
            }

            if (var1.isInterface()) {
               Type[] var3 = var9.getGenericInterfaces();
               int var4 = var3.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Type var6 = var3[var5];
                  if (var6 instanceof Class) {
                     var7 = (Class)var6;
                  } else {
                     var7 = getRawClass((ParameterizedType)var6);
                  }

                  if (var1.isAssignableFrom(var7)) {
                     var2 = var6;
                     continue label50;
                  }
               }
            }

            var7 = var9;
         }

         var2 = var7.getGenericSuperclass();
      }

      return null;
   }

   private static IllegalArgumentException handleExceptionForNewInstance(Exception var0, Class<?> var1) {
      StringBuilder var2 = new StringBuilder("unable to create new instance of class ");
      var2.append(var1.getName());
      ArrayList var3 = new ArrayList();
      boolean var4 = var1.isArray();
      boolean var5 = false;
      if (var4) {
         var3.add("because it is an array");
      } else if (var1.isPrimitive()) {
         var3.add("because it is primitive");
      } else if (var1 == Void.class) {
         var3.add("because it is void");
      } else {
         if (Modifier.isInterface(var1.getModifiers())) {
            var3.add("because it is an interface");
         } else if (Modifier.isAbstract(var1.getModifiers())) {
            var3.add("because it is abstract");
         }

         if (var1.getEnclosingClass() != null && !Modifier.isStatic(var1.getModifiers())) {
            var3.add("because it is not static");
         }

         if (!Modifier.isPublic(var1.getModifiers())) {
            var3.add("possibly because it is not public");
         } else {
            try {
               var1.getConstructor();
            } catch (NoSuchMethodException var6) {
               var3.add("because it has no accessible default constructor");
            }
         }
      }

      Iterator var7 = var3.iterator();

      while(var7.hasNext()) {
         String var8 = (String)var7.next();
         if (var5) {
            var2.append(" and");
         } else {
            var5 = true;
         }

         var2.append(" ");
         var2.append(var8);
      }

      return new IllegalArgumentException(var2.toString(), var0);
   }

   public static boolean isArray(Type var0) {
      boolean var1;
      if (var0 instanceof GenericArrayType || var0 instanceof Class && ((Class)var0).isArray()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isAssignableToOrFrom(Class<?> var0, Class<?> var1) {
      boolean var2;
      if (!var0.isAssignableFrom(var1) && !var1.isAssignableFrom(var0)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static <T> Iterable<T> iterableOf(final Object var0) {
      if (var0 instanceof Iterable) {
         return (Iterable)var0;
      } else {
         Class var1 = var0.getClass();
         Preconditions.checkArgument(var1.isArray(), "not an array or Iterable: %s", var1);
         return (Iterable)(!var1.getComponentType().isPrimitive() ? Arrays.asList((Object[])var0) : new Iterable<T>() {
            public Iterator<T> iterator() {
               return new Iterator<T>() {
                  int index = 0;
                  final int length = Array.getLength(var0);

                  public boolean hasNext() {
                     boolean var1;
                     if (this.index < this.length) {
                        var1 = true;
                     } else {
                        var1 = false;
                     }

                     return var1;
                  }

                  public T next() {
                     if (this.hasNext()) {
                        Object var1 = var0;
                        int var2 = this.index++;
                        return Array.get(var1, var2);
                     } else {
                        throw new NoSuchElementException();
                     }
                  }

                  public void remove() {
                     throw new UnsupportedOperationException();
                  }
               };
            }
         });
      }
   }

   public static <T> T newInstance(Class<T> var0) {
      try {
         Object var1 = var0.newInstance();
         return var1;
      } catch (IllegalAccessException var2) {
         throw handleExceptionForNewInstance(var2, var0);
      } catch (InstantiationException var3) {
         throw handleExceptionForNewInstance(var3, var0);
      }
   }

   public static Type resolveTypeVariable(List<Type> var0, TypeVariable<?> var1) {
      GenericDeclaration var2 = var1.getGenericDeclaration();
      if (var2 instanceof Class) {
         Class var3 = (Class)var2;
         int var4 = var0.size();

         ParameterizedType var5;
         for(var5 = null; var5 == null; var5 = getSuperParameterizedType((Type)var0.get(var4), var3)) {
            --var4;
            if (var4 < 0) {
               break;
            }
         }

         if (var5 != null) {
            TypeVariable[] var8 = var2.getTypeParameters();

            for(var4 = 0; var4 < var8.length && !var8[var4].equals(var1); ++var4) {
            }

            Type var7 = var5.getActualTypeArguments()[var4];
            if (var7 instanceof TypeVariable) {
               Type var6 = resolveTypeVariable(var0, (TypeVariable)var7);
               if (var6 != null) {
                  return var6;
               }
            }

            return var7;
         }
      }

      return null;
   }

   public static Object toArray(Collection<?> var0, Class<?> var1) {
      if (!var1.isPrimitive()) {
         return var0.toArray((Object[])Array.newInstance(var1, var0.size()));
      } else {
         Object var4 = Array.newInstance(var1, var0.size());
         int var2 = 0;

         for(Iterator var3 = var0.iterator(); var3.hasNext(); ++var2) {
            Array.set(var4, var2, var3.next());
         }

         return var4;
      }
   }
}
