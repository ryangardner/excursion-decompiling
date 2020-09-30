package com.google.common.reflect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Invokable<T, R> extends Element implements GenericDeclaration {
   <M extends AccessibleObject & Member> Invokable(M var1) {
      super(var1);
   }

   public static <T> Invokable<T, T> from(Constructor<T> var0) {
      return new Invokable.ConstructorInvokable(var0);
   }

   public static Invokable<?, Object> from(Method var0) {
      return new Invokable.MethodInvokable(var0);
   }

   public final Class<? super T> getDeclaringClass() {
      return super.getDeclaringClass();
   }

   public final ImmutableList<TypeToken<? extends Throwable>> getExceptionTypes() {
      ImmutableList.Builder var1 = ImmutableList.builder();
      Type[] var2 = this.getGenericExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var1.add((Object)TypeToken.of(var2[var4]));
      }

      return var1.build();
   }

   abstract Type[] getGenericExceptionTypes();

   abstract Type[] getGenericParameterTypes();

   abstract Type getGenericReturnType();

   public TypeToken<T> getOwnerType() {
      return TypeToken.of(this.getDeclaringClass());
   }

   abstract Annotation[][] getParameterAnnotations();

   public final ImmutableList<Parameter> getParameters() {
      Type[] var1 = this.getGenericParameterTypes();
      Annotation[][] var2 = this.getParameterAnnotations();
      ImmutableList.Builder var3 = ImmutableList.builder();

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var3.add((Object)(new Parameter(this, var4, TypeToken.of(var1[var4]), var2[var4])));
      }

      return var3.build();
   }

   public final TypeToken<? extends R> getReturnType() {
      return TypeToken.of(this.getGenericReturnType());
   }

   public final R invoke(@NullableDecl T var1, Object... var2) throws InvocationTargetException, IllegalAccessException {
      return this.invokeInternal(var1, (Object[])Preconditions.checkNotNull(var2));
   }

   abstract Object invokeInternal(@NullableDecl Object var1, Object[] var2) throws InvocationTargetException, IllegalAccessException;

   public abstract boolean isOverridable();

   public abstract boolean isVarArgs();

   public final <R1 extends R> Invokable<T, R1> returning(TypeToken<R1> var1) {
      if (var1.isSupertypeOf(this.getReturnType())) {
         return this;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invokable is known to return ");
         var2.append(this.getReturnType());
         var2.append(", not ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public final <R1 extends R> Invokable<T, R1> returning(Class<R1> var1) {
      return this.returning(TypeToken.of(var1));
   }

   static class ConstructorInvokable<T> extends Invokable<T, T> {
      final Constructor<?> constructor;

      ConstructorInvokable(Constructor<?> var1) {
         super(var1);
         this.constructor = var1;
      }

      private boolean mayNeedHiddenThis() {
         Class var1 = this.constructor.getDeclaringClass();
         Constructor var2 = var1.getEnclosingConstructor();
         boolean var3 = true;
         if (var2 != null) {
            return true;
         } else {
            Method var4 = var1.getEnclosingMethod();
            if (var4 != null) {
               return Modifier.isStatic(var4.getModifiers()) ^ true;
            } else {
               if (var1.getEnclosingClass() == null || Modifier.isStatic(var1.getModifiers())) {
                  var3 = false;
               }

               return var3;
            }
         }
      }

      Type[] getGenericExceptionTypes() {
         return this.constructor.getGenericExceptionTypes();
      }

      Type[] getGenericParameterTypes() {
         Type[] var1 = this.constructor.getGenericParameterTypes();
         Type[] var2 = var1;
         if (var1.length > 0) {
            var2 = var1;
            if (this.mayNeedHiddenThis()) {
               Class[] var3 = this.constructor.getParameterTypes();
               var2 = var1;
               if (var1.length == var3.length) {
                  var2 = var1;
                  if (var3[0] == this.getDeclaringClass().getEnclosingClass()) {
                     var2 = (Type[])Arrays.copyOfRange(var1, 1, var1.length);
                  }
               }
            }
         }

         return var2;
      }

      Type getGenericReturnType() {
         Class var1 = this.getDeclaringClass();
         TypeVariable[] var2 = var1.getTypeParameters();
         Object var3 = var1;
         if (var2.length > 0) {
            var3 = Types.newParameterizedType(var1, var2);
         }

         return (Type)var3;
      }

      final Annotation[][] getParameterAnnotations() {
         return this.constructor.getParameterAnnotations();
      }

      public final TypeVariable<?>[] getTypeParameters() {
         TypeVariable[] var1 = this.getDeclaringClass().getTypeParameters();
         TypeVariable[] var2 = this.constructor.getTypeParameters();
         TypeVariable[] var3 = new TypeVariable[var1.length + var2.length];
         System.arraycopy(var1, 0, var3, 0, var1.length);
         System.arraycopy(var2, 0, var3, var1.length, var2.length);
         return var3;
      }

      final Object invokeInternal(@NullableDecl Object var1, Object[] var2) throws InvocationTargetException, IllegalAccessException {
         try {
            var1 = this.constructor.newInstance(var2);
            return var1;
         } catch (InstantiationException var3) {
            StringBuilder var4 = new StringBuilder();
            var4.append(this.constructor);
            var4.append(" failed.");
            throw new RuntimeException(var4.toString(), var3);
         }
      }

      public final boolean isOverridable() {
         return false;
      }

      public final boolean isVarArgs() {
         return this.constructor.isVarArgs();
      }
   }

   static class MethodInvokable<T> extends Invokable<T, Object> {
      final Method method;

      MethodInvokable(Method var1) {
         super(var1);
         this.method = var1;
      }

      Type[] getGenericExceptionTypes() {
         return this.method.getGenericExceptionTypes();
      }

      Type[] getGenericParameterTypes() {
         return this.method.getGenericParameterTypes();
      }

      Type getGenericReturnType() {
         return this.method.getGenericReturnType();
      }

      final Annotation[][] getParameterAnnotations() {
         return this.method.getParameterAnnotations();
      }

      public final TypeVariable<?>[] getTypeParameters() {
         return this.method.getTypeParameters();
      }

      final Object invokeInternal(@NullableDecl Object var1, Object[] var2) throws InvocationTargetException, IllegalAccessException {
         return this.method.invoke(var1, var2);
      }

      public final boolean isOverridable() {
         boolean var1;
         if (!this.isFinal() && !this.isPrivate() && !this.isStatic() && !Modifier.isFinal(this.getDeclaringClass().getModifiers())) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final boolean isVarArgs() {
         return this.method.isVarArgs();
      }
   }
}
