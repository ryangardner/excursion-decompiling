package com.google.common.reflect;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Types {
   private static final Joiner COMMA_JOINER = Joiner.on(", ").useForNull("null");
   private static final Function<Type, String> TYPE_NAME = new Function<Type, String>() {
      public String apply(Type var1) {
         return Types.JavaVersion.CURRENT.typeName(var1);
      }
   };

   private Types() {
   }

   private static void disallowPrimitiveType(Type[] var0, String var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type var4 = var0[var3];
         if (var4 instanceof Class) {
            Class var5 = (Class)var4;
            Preconditions.checkArgument(var5.isPrimitive() ^ true, "Primitive type '%s' used as %s", var5, var1);
         }
      }

   }

   private static Iterable<Type> filterUpperBounds(Iterable<Type> var0) {
      return Iterables.filter(var0, Predicates.not(Predicates.equalTo(Object.class)));
   }

   static Class<?> getArrayClass(Class<?> var0) {
      return Array.newInstance(var0, 0).getClass();
   }

   @NullableDecl
   static Type getComponentType(Type var0) {
      Preconditions.checkNotNull(var0);
      final AtomicReference var1 = new AtomicReference();
      (new TypeVisitor() {
         void visitClass(Class<?> var1x) {
            var1.set(var1x.getComponentType());
         }

         void visitGenericArrayType(GenericArrayType var1x) {
            var1.set(var1x.getGenericComponentType());
         }

         void visitTypeVariable(TypeVariable<?> var1x) {
            var1.set(Types.subtypeOfComponentType(var1x.getBounds()));
         }

         void visitWildcardType(WildcardType var1x) {
            var1.set(Types.subtypeOfComponentType(var1x.getUpperBounds()));
         }
      }).visit(new Type[]{var0});
      return (Type)var1.get();
   }

   static Type newArrayType(Type var0) {
      if (var0 instanceof WildcardType) {
         WildcardType var1 = (WildcardType)var0;
         Type[] var5 = var1.getLowerBounds();
         int var2 = var5.length;
         boolean var3 = true;
         boolean var4;
         if (var2 <= 1) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "Wildcard cannot have more than one lower bounds.");
         if (var5.length == 1) {
            return supertypeOf(newArrayType(var5[0]));
         } else {
            var5 = var1.getUpperBounds();
            if (var5.length == 1) {
               var4 = var3;
            } else {
               var4 = false;
            }

            Preconditions.checkArgument(var4, "Wildcard should have only one upper bound.");
            return subtypeOf(newArrayType(var5[0]));
         }
      } else {
         return Types.JavaVersion.CURRENT.newArrayType(var0);
      }
   }

   static <D extends GenericDeclaration> TypeVariable<D> newArtificialTypeVariable(D var0, String var1, Type... var2) {
      Type[] var3 = var2;
      if (var2.length == 0) {
         var3 = new Type[]{Object.class};
      }

      return newTypeVariableImpl(var0, var1, var3);
   }

   static ParameterizedType newParameterizedType(Class<?> var0, Type... var1) {
      return new Types.ParameterizedTypeImpl(Types.ClassOwnership.JVM_BEHAVIOR.getOwnerType(var0), var0, var1);
   }

   static ParameterizedType newParameterizedTypeWithOwner(@NullableDecl Type var0, Class<?> var1, Type... var2) {
      if (var0 == null) {
         return newParameterizedType(var1, var2);
      } else {
         Preconditions.checkNotNull(var2);
         boolean var3;
         if (var1.getEnclosingClass() != null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "Owner type for unenclosed %s", (Object)var1);
         return new Types.ParameterizedTypeImpl(var0, var1, var2);
      }
   }

   private static <D extends GenericDeclaration> TypeVariable<D> newTypeVariableImpl(D var0, String var1, Type[] var2) {
      return (TypeVariable)Reflection.newProxy(TypeVariable.class, new Types.TypeVariableInvocationHandler(new Types.TypeVariableImpl(var0, var1, var2)));
   }

   static WildcardType subtypeOf(Type var0) {
      return new Types.WildcardTypeImpl(new Type[0], new Type[]{var0});
   }

   @NullableDecl
   private static Type subtypeOfComponentType(Type[] var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Type var3 = getComponentType(var0[var2]);
         if (var3 != null) {
            if (var3 instanceof Class) {
               Class var4 = (Class)var3;
               if (var4.isPrimitive()) {
                  return var4;
               }
            }

            return subtypeOf(var3);
         }
      }

      return null;
   }

   static WildcardType supertypeOf(Type var0) {
      return new Types.WildcardTypeImpl(new Type[]{var0}, new Type[]{Object.class});
   }

   private static Type[] toArray(Collection<Type> var0) {
      return (Type[])var0.toArray(new Type[0]);
   }

   static String toString(Type var0) {
      String var1;
      if (var0 instanceof Class) {
         var1 = ((Class)var0).getName();
      } else {
         var1 = var0.toString();
      }

      return var1;
   }

   private static enum ClassOwnership {
      static final Types.ClassOwnership JVM_BEHAVIOR;
      LOCAL_CLASS_HAS_NO_OWNER,
      OWNED_BY_ENCLOSING_CLASS {
         @NullableDecl
         Class<?> getOwnerType(Class<?> var1) {
            return var1.getEnclosingClass();
         }
      };

      static {
         Types.ClassOwnership var0 = new Types.ClassOwnership("LOCAL_CLASS_HAS_NO_OWNER", 1) {
            @NullableDecl
            Class<?> getOwnerType(Class<?> var1) {
               return var1.isLocalClass() ? null : var1.getEnclosingClass();
            }
         };
         LOCAL_CLASS_HAS_NO_OWNER = var0;
         JVM_BEHAVIOR = detectJvmBehavior();
      }

      private ClassOwnership() {
      }

      // $FF: synthetic method
      ClassOwnership(Object var3) {
         this();
      }

      private static Types.ClassOwnership detectJvmBehavior() {
         ParameterizedType var0 = (ParameterizedType)(new Types$ClassOwnership$1LocalClass<String>() {
         }).getClass().getGenericSuperclass();
         Types.ClassOwnership[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Types.ClassOwnership var4 = var1[var3];
            if (var4.getOwnerType(Types$ClassOwnership$1LocalClass.class) == var0.getOwnerType()) {
               return var4;
            }
         }

         throw new AssertionError();
      }

      @NullableDecl
      abstract Class<?> getOwnerType(Class<?> var1);
   }

   private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
      private static final long serialVersionUID = 0L;
      private final Type componentType;

      GenericArrayTypeImpl(Type var1) {
         this.componentType = Types.JavaVersion.CURRENT.usedInGenericType(var1);
      }

      public boolean equals(Object var1) {
         if (var1 instanceof GenericArrayType) {
            GenericArrayType var2 = (GenericArrayType)var1;
            return Objects.equal(this.getGenericComponentType(), var2.getGenericComponentType());
         } else {
            return false;
         }
      }

      public Type getGenericComponentType() {
         return this.componentType;
      }

      public int hashCode() {
         return this.componentType.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(Types.toString(this.componentType));
         var1.append("[]");
         return var1.toString();
      }
   }

   static enum JavaVersion {
      static final Types.JavaVersion CURRENT;
      JAVA6 {
         GenericArrayType newArrayType(Type var1) {
            return new Types.GenericArrayTypeImpl(var1);
         }

         Type usedInGenericType(Type var1) {
            Preconditions.checkNotNull(var1);
            Object var2 = var1;
            if (var1 instanceof Class) {
               Class var3 = (Class)var1;
               var2 = var1;
               if (var3.isArray()) {
                  var2 = new Types.GenericArrayTypeImpl(var3.getComponentType());
               }
            }

            return (Type)var2;
         }
      },
      JAVA7 {
         Type newArrayType(Type var1) {
            return (Type)(var1 instanceof Class ? Types.getArrayClass((Class)var1) : new Types.GenericArrayTypeImpl(var1));
         }

         Type usedInGenericType(Type var1) {
            return (Type)Preconditions.checkNotNull(var1);
         }
      },
      JAVA8 {
         Type newArrayType(Type var1) {
            return JAVA7.newArrayType(var1);
         }

         String typeName(Type var1) {
            try {
               String var5 = (String)Type.class.getMethod("getTypeName").invoke(var1);
               return var5;
            } catch (NoSuchMethodException var2) {
               throw new AssertionError("Type.getTypeName should be available in Java 8");
            } catch (InvocationTargetException var3) {
               throw new RuntimeException(var3);
            } catch (IllegalAccessException var4) {
               throw new RuntimeException(var4);
            }
         }

         Type usedInGenericType(Type var1) {
            return JAVA7.usedInGenericType(var1);
         }
      },
      JAVA9;

      static {
         Types.JavaVersion var0 = new Types.JavaVersion("JAVA9", 3) {
            boolean jdkTypeDuplicatesOwnerName() {
               return false;
            }

            Type newArrayType(Type var1) {
               return JAVA8.newArrayType(var1);
            }

            String typeName(Type var1) {
               return JAVA8.typeName(var1);
            }

            Type usedInGenericType(Type var1) {
               return JAVA8.usedInGenericType(var1);
            }
         };
         JAVA9 = var0;
         if (AnnotatedElement.class.isAssignableFrom(TypeVariable.class)) {
            if ((new TypeCapture<Entry<String, int[][]>>() {
            }).capture().toString().contains("java.util.Map.java.util.Map")) {
               CURRENT = JAVA8;
            } else {
               CURRENT = JAVA9;
            }
         } else if ((new TypeCapture<int[]>() {
         }).capture() instanceof Class) {
            CURRENT = JAVA7;
         } else {
            CURRENT = JAVA6;
         }

      }

      private JavaVersion() {
      }

      // $FF: synthetic method
      JavaVersion(Object var3) {
         this();
      }

      boolean jdkTypeDuplicatesOwnerName() {
         return true;
      }

      abstract Type newArrayType(Type var1);

      String typeName(Type var1) {
         return Types.toString(var1);
      }

      final ImmutableList<Type> usedInGenericType(Type[] var1) {
         ImmutableList.Builder var2 = ImmutableList.builder();
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2.add((Object)this.usedInGenericType(var1[var4]));
         }

         return var2.build();
      }

      abstract Type usedInGenericType(Type var1);
   }

   static final class NativeTypeVariableEquals<X> {
      static final boolean NATIVE_TYPE_VARIABLE_ONLY = Types.NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(Types.NativeTypeVariableEquals.class, "X")) ^ true;
   }

   private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
      private static final long serialVersionUID = 0L;
      private final ImmutableList<Type> argumentsList;
      @NullableDecl
      private final Type ownerType;
      private final Class<?> rawType;

      ParameterizedTypeImpl(@NullableDecl Type var1, Class<?> var2, Type[] var3) {
         Preconditions.checkNotNull(var2);
         boolean var4;
         if (var3.length == var2.getTypeParameters().length) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         Types.disallowPrimitiveType(var3, "type parameter");
         this.ownerType = var1;
         this.rawType = var2;
         this.argumentsList = Types.JavaVersion.CURRENT.usedInGenericType(var3);
      }

      public boolean equals(Object var1) {
         boolean var2 = var1 instanceof ParameterizedType;
         boolean var3 = false;
         if (!var2) {
            return false;
         } else {
            ParameterizedType var4 = (ParameterizedType)var1;
            var2 = var3;
            if (this.getRawType().equals(var4.getRawType())) {
               var2 = var3;
               if (Objects.equal(this.getOwnerType(), var4.getOwnerType())) {
                  var2 = var3;
                  if (Arrays.equals(this.getActualTypeArguments(), var4.getActualTypeArguments())) {
                     var2 = true;
                  }
               }
            }

            return var2;
         }
      }

      public Type[] getActualTypeArguments() {
         return Types.toArray(this.argumentsList);
      }

      public Type getOwnerType() {
         return this.ownerType;
      }

      public Type getRawType() {
         return this.rawType;
      }

      public int hashCode() {
         Type var1 = this.ownerType;
         int var2;
         if (var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.hashCode();
         }

         return var2 ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         if (this.ownerType != null && Types.JavaVersion.CURRENT.jdkTypeDuplicatesOwnerName()) {
            var1.append(Types.JavaVersion.CURRENT.typeName(this.ownerType));
            var1.append('.');
         }

         var1.append(this.rawType.getName());
         var1.append('<');
         var1.append(Types.COMMA_JOINER.join(Iterables.transform(this.argumentsList, Types.TYPE_NAME)));
         var1.append('>');
         return var1.toString();
      }
   }

   private static final class TypeVariableImpl<D extends GenericDeclaration> {
      private final ImmutableList<Type> bounds;
      private final D genericDeclaration;
      private final String name;

      TypeVariableImpl(D var1, String var2, Type[] var3) {
         Types.disallowPrimitiveType(var3, "bound for type variable");
         this.genericDeclaration = (GenericDeclaration)Preconditions.checkNotNull(var1);
         this.name = (String)Preconditions.checkNotNull(var2);
         this.bounds = ImmutableList.copyOf((Object[])var3);
      }

      public boolean equals(Object var1) {
         boolean var2 = Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY;
         boolean var3 = true;
         boolean var4 = true;
         if (var2) {
            if (var1 != null && Proxy.isProxyClass(var1.getClass()) && Proxy.getInvocationHandler(var1) instanceof Types.TypeVariableInvocationHandler) {
               Types.TypeVariableImpl var6 = ((Types.TypeVariableInvocationHandler)Proxy.getInvocationHandler(var1)).typeVariableImpl;
               if (!this.name.equals(var6.getName()) || !this.genericDeclaration.equals(var6.getGenericDeclaration()) || !this.bounds.equals(var6.bounds)) {
                  var4 = false;
               }

               return var4;
            } else {
               return false;
            }
         } else if (!(var1 instanceof TypeVariable)) {
            return false;
         } else {
            TypeVariable var5 = (TypeVariable)var1;
            if (this.name.equals(var5.getName()) && this.genericDeclaration.equals(var5.getGenericDeclaration())) {
               var4 = var3;
            } else {
               var4 = false;
            }

            return var4;
         }
      }

      public Type[] getBounds() {
         return Types.toArray(this.bounds);
      }

      public D getGenericDeclaration() {
         return this.genericDeclaration;
      }

      public String getName() {
         return this.name;
      }

      public String getTypeName() {
         return this.name;
      }

      public int hashCode() {
         return this.genericDeclaration.hashCode() ^ this.name.hashCode();
      }

      public String toString() {
         return this.name;
      }
   }

   private static final class TypeVariableInvocationHandler implements InvocationHandler {
      private static final ImmutableMap<String, Method> typeVariableMethods;
      private final Types.TypeVariableImpl<?> typeVariableImpl;

      static {
         ImmutableMap.Builder var0 = ImmutableMap.builder();
         Method[] var1 = Types.TypeVariableImpl.class.getMethods();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Method var4 = var1[var3];
            if (var4.getDeclaringClass().equals(Types.TypeVariableImpl.class)) {
               try {
                  var4.setAccessible(true);
               } catch (AccessControlException var6) {
               }

               var0.put(var4.getName(), var4);
            }
         }

         typeVariableMethods = var0.build();
      }

      TypeVariableInvocationHandler(Types.TypeVariableImpl<?> var1) {
         this.typeVariableImpl = var1;
      }

      public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
         String var5 = var2.getName();
         var2 = (Method)typeVariableMethods.get(var5);
         if (var2 != null) {
            try {
               var1 = var2.invoke(this.typeVariableImpl, var3);
               return var1;
            } catch (InvocationTargetException var4) {
               throw var4.getCause();
            }
         } else {
            throw new UnsupportedOperationException(var5);
         }
      }
   }

   static final class WildcardTypeImpl implements WildcardType, Serializable {
      private static final long serialVersionUID = 0L;
      private final ImmutableList<Type> lowerBounds;
      private final ImmutableList<Type> upperBounds;

      WildcardTypeImpl(Type[] var1, Type[] var2) {
         Types.disallowPrimitiveType(var1, "lower bound for wildcard");
         Types.disallowPrimitiveType(var2, "upper bound for wildcard");
         this.lowerBounds = Types.JavaVersion.CURRENT.usedInGenericType(var1);
         this.upperBounds = Types.JavaVersion.CURRENT.usedInGenericType(var2);
      }

      public boolean equals(Object var1) {
         boolean var2 = var1 instanceof WildcardType;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            WildcardType var5 = (WildcardType)var1;
            var4 = var3;
            if (this.lowerBounds.equals(Arrays.asList(var5.getLowerBounds()))) {
               var4 = var3;
               if (this.upperBounds.equals(Arrays.asList(var5.getUpperBounds()))) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public Type[] getLowerBounds() {
         return Types.toArray(this.lowerBounds);
      }

      public Type[] getUpperBounds() {
         return Types.toArray(this.upperBounds);
      }

      public int hashCode() {
         return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder("?");
         UnmodifiableIterator var2 = this.lowerBounds.iterator();

         while(var2.hasNext()) {
            Type var3 = (Type)var2.next();
            var1.append(" super ");
            var1.append(Types.JavaVersion.CURRENT.typeName(var3));
         }

         Iterator var5 = Types.filterUpperBounds(this.upperBounds).iterator();

         while(var5.hasNext()) {
            Type var4 = (Type)var5.next();
            var1.append(" extends ");
            var1.append(Types.JavaVersion.CURRENT.typeName(var4));
         }

         return var1.toString();
      }
   }
}
