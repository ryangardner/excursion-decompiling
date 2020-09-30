package com.google.common.reflect;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Primitives;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class TypeToken<T> extends TypeCapture<T> implements Serializable {
   private static final long serialVersionUID = 3637540370352322684L;
   @MonotonicNonNullDecl
   private transient TypeResolver covariantTypeResolver;
   @MonotonicNonNullDecl
   private transient TypeResolver invariantTypeResolver;
   private final Type runtimeType;

   protected TypeToken() {
      Type var1 = this.capture();
      this.runtimeType = var1;
      Preconditions.checkState(var1 instanceof TypeVariable ^ true, "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", (Object)var1);
   }

   protected TypeToken(Class<?> var1) {
      Type var2 = super.capture();
      if (var2 instanceof Class) {
         this.runtimeType = var2;
      } else {
         this.runtimeType = TypeResolver.covariantly(var1).resolveType(var2);
      }

   }

   private TypeToken(Type var1) {
      this.runtimeType = (Type)Preconditions.checkNotNull(var1);
   }

   // $FF: synthetic method
   TypeToken(Type var1, Object var2) {
      this(var1);
   }

   private static TypeToken.Bounds any(Type[] var0) {
      return new TypeToken.Bounds(var0, true);
   }

   @NullableDecl
   private TypeToken<? super T> boundAsSuperclass(Type var1) {
      TypeToken var2 = of(var1);
      TypeToken var3 = var2;
      if (var2.getRawType().isInterface()) {
         var3 = null;
      }

      return var3;
   }

   private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] var1) {
      ImmutableList.Builder var2 = ImmutableList.builder();
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TypeToken var5 = of(var1[var4]);
         if (var5.getRawType().isInterface()) {
            var2.add((Object)var5);
         }
      }

      return var2.build();
   }

   private static Type canonicalizeTypeArg(TypeVariable<?> var0, Type var1) {
      Object var2;
      if (var1 instanceof WildcardType) {
         var2 = canonicalizeWildcardType(var0, (WildcardType)var1);
      } else {
         var2 = canonicalizeWildcardsInType(var1);
      }

      return (Type)var2;
   }

   private static WildcardType canonicalizeWildcardType(TypeVariable<?> var0, WildcardType var1) {
      Type[] var2 = var0.getBounds();
      ArrayList var7 = new ArrayList();
      Type[] var3 = var1.getUpperBounds();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type var6 = var3[var5];
         if (!any(var2).isSubtypeOf(var6)) {
            var7.add(canonicalizeWildcardsInType(var6));
         }
      }

      return new Types.WildcardTypeImpl(var1.getLowerBounds(), (Type[])var7.toArray(new Type[0]));
   }

   private static ParameterizedType canonicalizeWildcardsInParameterizedType(ParameterizedType var0) {
      Class var1 = (Class)var0.getRawType();
      TypeVariable[] var2 = var1.getTypeParameters();
      Type[] var3 = var0.getActualTypeArguments();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         var3[var4] = canonicalizeTypeArg(var2[var4], var3[var4]);
      }

      return Types.newParameterizedTypeWithOwner(var0.getOwnerType(), var1, var3);
   }

   private static Type canonicalizeWildcardsInType(Type var0) {
      if (var0 instanceof ParameterizedType) {
         return canonicalizeWildcardsInParameterizedType((ParameterizedType)var0);
      } else {
         Type var1 = var0;
         if (var0 instanceof GenericArrayType) {
            var1 = Types.newArrayType(canonicalizeWildcardsInType(((GenericArrayType)var0).getGenericComponentType()));
         }

         return var1;
      }
   }

   private static TypeToken.Bounds every(Type[] var0) {
      return new TypeToken.Bounds(var0, false);
   }

   private TypeToken<? extends T> getArraySubtype(Class<?> var1) {
      return of(newArrayClassOrGenericArrayType(this.getComponentType().getSubtype(var1.getComponentType()).runtimeType));
   }

   private TypeToken<? super T> getArraySupertype(Class<? super T> var1) {
      return of(newArrayClassOrGenericArrayType(((TypeToken)Preconditions.checkNotNull(this.getComponentType(), "%s isn't a super type of %s", var1, this)).getSupertype(var1.getComponentType()).runtimeType));
   }

   private TypeResolver getCovariantTypeResolver() {
      TypeResolver var1 = this.covariantTypeResolver;
      TypeResolver var2 = var1;
      if (var1 == null) {
         var2 = TypeResolver.covariantly(this.runtimeType);
         this.covariantTypeResolver = var2;
      }

      return var2;
   }

   private TypeResolver getInvariantTypeResolver() {
      TypeResolver var1 = this.invariantTypeResolver;
      TypeResolver var2 = var1;
      if (var1 == null) {
         var2 = TypeResolver.invariantly(this.runtimeType);
         this.invariantTypeResolver = var2;
      }

      return var2;
   }

   @NullableDecl
   private Type getOwnerTypeIfPresent() {
      Type var1 = this.runtimeType;
      if (var1 instanceof ParameterizedType) {
         return ((ParameterizedType)var1).getOwnerType();
      } else {
         return var1 instanceof Class ? ((Class)var1).getEnclosingClass() : null;
      }
   }

   private ImmutableSet<Class<? super T>> getRawTypes() {
      final ImmutableSet.Builder var1 = ImmutableSet.builder();
      (new TypeVisitor() {
         void visitClass(Class<?> var1x) {
            var1.add((Object)var1x);
         }

         void visitGenericArrayType(GenericArrayType var1x) {
            var1.add((Object)Types.getArrayClass(TypeToken.of(var1x.getGenericComponentType()).getRawType()));
         }

         void visitParameterizedType(ParameterizedType var1x) {
            var1.add((Object)((Class)var1x.getRawType()));
         }

         void visitTypeVariable(TypeVariable<?> var1x) {
            this.visit(var1x.getBounds());
         }

         void visitWildcardType(WildcardType var1x) {
            this.visit(var1x.getUpperBounds());
         }
      }).visit(new Type[]{this.runtimeType});
      return var1.build();
   }

   private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> var1, Type[] var2) {
      if (var2.length > 0) {
         return of(var2[0]).getSubtype(var1);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1);
         var3.append(" isn't a subclass of ");
         var3.append(this);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> var1, Type[] var2) {
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TypeToken var5 = of(var2[var4]);
         if (var5.isSubtypeOf((Type)var1)) {
            return var5.getSupertype(var1);
         }
      }

      StringBuilder var6 = new StringBuilder();
      var6.append(var1);
      var6.append(" isn't a super type of ");
      var6.append(this);
      throw new IllegalArgumentException(var6.toString());
   }

   private boolean is(Type var1, TypeVariable<?> var2) {
      boolean var3 = this.runtimeType.equals(var1);
      boolean var4 = true;
      if (var3) {
         return true;
      } else if (!(var1 instanceof WildcardType)) {
         return canonicalizeWildcardsInType(this.runtimeType).equals(canonicalizeWildcardsInType(var1));
      } else {
         WildcardType var5 = canonicalizeWildcardType(var2, (WildcardType)var1);
         if (!every(var5.getUpperBounds()).isSupertypeOf(this.runtimeType) || !every(var5.getLowerBounds()).isSubtypeOf(this.runtimeType)) {
            var4 = false;
         }

         return var4;
      }
   }

   private boolean isOwnedBySubtypeOf(Type var1) {
      Iterator var2 = this.getTypes().iterator();

      Type var3;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         var3 = ((TypeToken)var2.next()).getOwnerTypeIfPresent();
      } while(var3 == null || !of(var3).isSubtypeOf(var1));

      return true;
   }

   private boolean isSubtypeOfArrayType(GenericArrayType var1) {
      Type var2 = this.runtimeType;
      if (var2 instanceof Class) {
         Class var3 = (Class)var2;
         return !var3.isArray() ? false : of(var3.getComponentType()).isSubtypeOf(var1.getGenericComponentType());
      } else {
         return var2 instanceof GenericArrayType ? of(((GenericArrayType)var2).getGenericComponentType()).isSubtypeOf(var1.getGenericComponentType()) : false;
      }
   }

   private boolean isSubtypeOfParameterizedType(ParameterizedType var1) {
      Class var2 = of((Type)var1).getRawType();
      boolean var3 = this.someRawTypeIsSubclassOf(var2);
      boolean var4 = false;
      if (!var3) {
         return false;
      } else {
         TypeVariable[] var5 = var2.getTypeParameters();
         Type[] var7 = var1.getActualTypeArguments();

         for(int var6 = 0; var6 < var5.length; ++var6) {
            if (!of(this.getCovariantTypeResolver().resolveType(var5[var6])).is(var7[var6], var5[var6])) {
               return false;
            }
         }

         if (Modifier.isStatic(((Class)var1.getRawType()).getModifiers()) || var1.getOwnerType() == null || this.isOwnedBySubtypeOf(var1.getOwnerType())) {
            var4 = true;
         }

         return var4;
      }
   }

   private boolean isSupertypeOfArray(GenericArrayType var1) {
      Type var2 = this.runtimeType;
      if (var2 instanceof Class) {
         Class var3 = (Class)var2;
         return !var3.isArray() ? var3.isAssignableFrom(Object[].class) : of(var1.getGenericComponentType()).isSubtypeOf((Type)var3.getComponentType());
      } else {
         return var2 instanceof GenericArrayType ? of(var1.getGenericComponentType()).isSubtypeOf(((GenericArrayType)this.runtimeType).getGenericComponentType()) : false;
      }
   }

   private boolean isWrapper() {
      return Primitives.allWrapperTypes().contains(this.runtimeType);
   }

   private static Type newArrayClassOrGenericArrayType(Type var0) {
      return Types.JavaVersion.JAVA7.newArrayType(var0);
   }

   public static <T> TypeToken<T> of(Class<T> var0) {
      return new TypeToken.SimpleTypeToken(var0);
   }

   public static TypeToken<?> of(Type var0) {
      return new TypeToken.SimpleTypeToken(var0);
   }

   private TypeToken<?> resolveSupertype(Type var1) {
      TypeToken var2 = of(this.getCovariantTypeResolver().resolveType(var1));
      var2.covariantTypeResolver = this.covariantTypeResolver;
      var2.invariantTypeResolver = this.invariantTypeResolver;
      return var2;
   }

   private Type resolveTypeArgsForSubclass(Class<?> var1) {
      if (!(this.runtimeType instanceof Class) || var1.getTypeParameters().length != 0 && this.getRawType().getTypeParameters().length == 0) {
         TypeToken var3 = toGenericType(var1);
         Type var2 = var3.getSupertype(this.getRawType()).runtimeType;
         return (new TypeResolver()).where(var2, this.runtimeType).resolveType(var3.runtimeType);
      } else {
         return var1;
      }
   }

   private boolean someRawTypeIsSubclassOf(Class<?> var1) {
      UnmodifiableIterator var2 = this.getRawTypes().iterator();

      do {
         if (!var2.hasNext()) {
            return false;
         }
      } while(!var1.isAssignableFrom((Class)var2.next()));

      return true;
   }

   static <T> TypeToken<? extends T> toGenericType(Class<T> var0) {
      if (var0.isArray()) {
         return of(Types.newArrayType(toGenericType(var0.getComponentType()).runtimeType));
      } else {
         TypeVariable[] var1 = var0.getTypeParameters();
         Type var2;
         if (var0.isMemberClass() && !Modifier.isStatic(var0.getModifiers())) {
            var2 = toGenericType(var0.getEnclosingClass()).runtimeType;
         } else {
            var2 = null;
         }

         return var1.length > 0 || var2 != null && var2 != var0.getEnclosingClass() ? of((Type)Types.newParameterizedTypeWithOwner(var2, var0, var1)) : of(var0);
      }
   }

   public final Invokable<T, T> constructor(Constructor<?> var1) {
      boolean var2;
      if (var1.getDeclaringClass() == this.getRawType()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "%s not declared by %s", var1, this.getRawType());
      return new Invokable.ConstructorInvokable<T>(var1) {
         Type[] getGenericExceptionTypes() {
            return TypeToken.this.getCovariantTypeResolver().resolveTypesInPlace(super.getGenericExceptionTypes());
         }

         Type[] getGenericParameterTypes() {
            return TypeToken.this.getInvariantTypeResolver().resolveTypesInPlace(super.getGenericParameterTypes());
         }

         Type getGenericReturnType() {
            return TypeToken.this.getCovariantTypeResolver().resolveType(super.getGenericReturnType());
         }

         public TypeToken<T> getOwnerType() {
            return TypeToken.this;
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(this.getOwnerType());
            var1.append("(");
            var1.append(Joiner.on(", ").join((Object[])this.getGenericParameterTypes()));
            var1.append(")");
            return var1.toString();
         }
      };
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 instanceof TypeToken) {
         TypeToken var2 = (TypeToken)var1;
         return this.runtimeType.equals(var2.runtimeType);
      } else {
         return false;
      }
   }

   @NullableDecl
   public final TypeToken<?> getComponentType() {
      Type var1 = Types.getComponentType(this.runtimeType);
      return var1 == null ? null : of(var1);
   }

   final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
      Type var1 = this.runtimeType;
      if (var1 instanceof TypeVariable) {
         return this.boundsAsInterfaces(((TypeVariable)var1).getBounds());
      } else if (var1 instanceof WildcardType) {
         return this.boundsAsInterfaces(((WildcardType)var1).getUpperBounds());
      } else {
         ImmutableList.Builder var2 = ImmutableList.builder();
         Type[] var5 = this.getRawType().getGenericInterfaces();
         int var3 = var5.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2.add((Object)this.resolveSupertype(var5[var4]));
         }

         return var2.build();
      }
   }

   @NullableDecl
   final TypeToken<? super T> getGenericSuperclass() {
      Type var1 = this.runtimeType;
      if (var1 instanceof TypeVariable) {
         return this.boundAsSuperclass(((TypeVariable)var1).getBounds()[0]);
      } else if (var1 instanceof WildcardType) {
         return this.boundAsSuperclass(((WildcardType)var1).getUpperBounds()[0]);
      } else {
         var1 = this.getRawType().getGenericSuperclass();
         return var1 == null ? null : this.resolveSupertype(var1);
      }
   }

   public final Class<? super T> getRawType() {
      return (Class)this.getRawTypes().iterator().next();
   }

   public final TypeToken<? extends T> getSubtype(Class<?> var1) {
      Preconditions.checkArgument(this.runtimeType instanceof TypeVariable ^ true, "Cannot get subtype of type variable <%s>", (Object)this);
      Type var2 = this.runtimeType;
      if (var2 instanceof WildcardType) {
         return this.getSubtypeFromLowerBounds(var1, ((WildcardType)var2).getLowerBounds());
      } else if (this.isArray()) {
         return this.getArraySubtype(var1);
      } else {
         Preconditions.checkArgument(this.getRawType().isAssignableFrom(var1), "%s isn't a subclass of %s", var1, this);
         TypeToken var3 = of(this.resolveTypeArgsForSubclass(var1));
         Preconditions.checkArgument(var3.isSubtypeOf(this), "%s does not appear to be a subtype of %s", var3, this);
         return var3;
      }
   }

   public final TypeToken<? super T> getSupertype(Class<? super T> var1) {
      Preconditions.checkArgument(this.someRawTypeIsSubclassOf(var1), "%s is not a super class of %s", var1, this);
      Type var2 = this.runtimeType;
      if (var2 instanceof TypeVariable) {
         return this.getSupertypeFromUpperBounds(var1, ((TypeVariable)var2).getBounds());
      } else if (var2 instanceof WildcardType) {
         return this.getSupertypeFromUpperBounds(var1, ((WildcardType)var2).getUpperBounds());
      } else {
         return var1.isArray() ? this.getArraySupertype(var1) : this.resolveSupertype(toGenericType(var1).runtimeType);
      }
   }

   public final Type getType() {
      return this.runtimeType;
   }

   public final TypeToken<T>.TypeSet getTypes() {
      return new TypeToken.TypeSet();
   }

   public int hashCode() {
      return this.runtimeType.hashCode();
   }

   public final boolean isArray() {
      boolean var1;
      if (this.getComponentType() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isPrimitive() {
      Type var1 = this.runtimeType;
      boolean var2;
      if (var1 instanceof Class && ((Class)var1).isPrimitive()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final boolean isSubtypeOf(TypeToken<?> var1) {
      return this.isSubtypeOf(var1.getType());
   }

   public final boolean isSubtypeOf(Type var1) {
      Preconditions.checkNotNull(var1);
      if (var1 instanceof WildcardType) {
         return any(((WildcardType)var1).getLowerBounds()).isSupertypeOf(this.runtimeType);
      } else {
         Type var2 = this.runtimeType;
         if (var2 instanceof WildcardType) {
            return any(((WildcardType)var2).getUpperBounds()).isSubtypeOf(var1);
         } else {
            boolean var3 = var2 instanceof TypeVariable;
            boolean var4 = false;
            if (!var3) {
               if (var2 instanceof GenericArrayType) {
                  return of(var1).isSupertypeOfArray((GenericArrayType)this.runtimeType);
               } else if (var1 instanceof Class) {
                  return this.someRawTypeIsSubclassOf((Class)var1);
               } else if (var1 instanceof ParameterizedType) {
                  return this.isSubtypeOfParameterizedType((ParameterizedType)var1);
               } else {
                  return var1 instanceof GenericArrayType ? this.isSubtypeOfArrayType((GenericArrayType)var1) : false;
               }
            } else {
               if (var2.equals(var1) || any(((TypeVariable)this.runtimeType).getBounds()).isSubtypeOf(var1)) {
                  var4 = true;
               }

               return var4;
            }
         }
      }
   }

   public final boolean isSupertypeOf(TypeToken<?> var1) {
      return var1.isSubtypeOf(this.getType());
   }

   public final boolean isSupertypeOf(Type var1) {
      return of(var1).isSubtypeOf(this.getType());
   }

   public final Invokable<T, Object> method(Method var1) {
      Preconditions.checkArgument(this.someRawTypeIsSubclassOf(var1.getDeclaringClass()), "%s not declared by %s", var1, this);
      return new Invokable.MethodInvokable<T>(var1) {
         Type[] getGenericExceptionTypes() {
            return TypeToken.this.getCovariantTypeResolver().resolveTypesInPlace(super.getGenericExceptionTypes());
         }

         Type[] getGenericParameterTypes() {
            return TypeToken.this.getInvariantTypeResolver().resolveTypesInPlace(super.getGenericParameterTypes());
         }

         Type getGenericReturnType() {
            return TypeToken.this.getCovariantTypeResolver().resolveType(super.getGenericReturnType());
         }

         public TypeToken<T> getOwnerType() {
            return TypeToken.this;
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(this.getOwnerType());
            var1.append(".");
            var1.append(super.toString());
            return var1.toString();
         }
      };
   }

   final TypeToken<T> rejectTypeVariables() {
      (new TypeVisitor() {
         void visitGenericArrayType(GenericArrayType var1) {
            this.visit(new Type[]{var1.getGenericComponentType()});
         }

         void visitParameterizedType(ParameterizedType var1) {
            this.visit(var1.getActualTypeArguments());
            this.visit(new Type[]{var1.getOwnerType()});
         }

         void visitTypeVariable(TypeVariable<?> var1) {
            StringBuilder var2 = new StringBuilder();
            var2.append(TypeToken.this.runtimeType);
            var2.append("contains a type variable and is not safe for the operation");
            throw new IllegalArgumentException(var2.toString());
         }

         void visitWildcardType(WildcardType var1) {
            this.visit(var1.getLowerBounds());
            this.visit(var1.getUpperBounds());
         }
      }).visit(new Type[]{this.runtimeType});
      return this;
   }

   public final TypeToken<?> resolveType(Type var1) {
      Preconditions.checkNotNull(var1);
      return of(this.getInvariantTypeResolver().resolveType(var1));
   }

   public String toString() {
      return Types.toString(this.runtimeType);
   }

   public final TypeToken<T> unwrap() {
      return this.isWrapper() ? of(Primitives.unwrap((Class)this.runtimeType)) : this;
   }

   public final <X> TypeToken<T> where(TypeParameter<X> var1, TypeToken<X> var2) {
      return new TypeToken.SimpleTypeToken((new TypeResolver()).where(ImmutableMap.of(new TypeResolver.TypeVariableKey(var1.typeVariable), var2.runtimeType)).resolveType(this.runtimeType));
   }

   public final <X> TypeToken<T> where(TypeParameter<X> var1, Class<X> var2) {
      return this.where(var1, of(var2));
   }

   public final TypeToken<T> wrap() {
      return this.isPrimitive() ? of(Primitives.wrap((Class)this.runtimeType)) : this;
   }

   protected Object writeReplace() {
      return of((new TypeResolver()).resolveType(this.runtimeType));
   }

   private static class Bounds {
      private final Type[] bounds;
      private final boolean target;

      Bounds(Type[] var1, boolean var2) {
         this.bounds = var1;
         this.target = var2;
      }

      boolean isSubtypeOf(Type var1) {
         Type[] var2 = this.bounds;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            boolean var5 = TypeToken.of(var2[var4]).isSubtypeOf(var1);
            boolean var6 = this.target;
            if (var5 == var6) {
               return var6;
            }
         }

         return this.target ^ true;
      }

      boolean isSupertypeOf(Type var1) {
         TypeToken var7 = TypeToken.of(var1);
         Type[] var2 = this.bounds;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            boolean var5 = var7.isSubtypeOf(var2[var4]);
            boolean var6 = this.target;
            if (var5 == var6) {
               return var6;
            }
         }

         return this.target ^ true;
      }
   }

   private final class ClassSet extends TypeToken<T>.TypeSet {
      private static final long serialVersionUID = 0L;
      @MonotonicNonNullDecl
      private transient ImmutableSet<TypeToken<? super T>> classes;

      private ClassSet() {
         super();
      }

      // $FF: synthetic method
      ClassSet(Object var2) {
         this();
      }

      private Object readResolve() {
         return TypeToken.this.getTypes().classes();
      }

      public TypeToken<T>.TypeSet classes() {
         return this;
      }

      protected Set<TypeToken<? super T>> delegate() {
         ImmutableSet var1 = this.classes;
         ImmutableSet var2 = var1;
         if (var1 == null) {
            var2 = FluentIterable.from((Iterable)TypeToken.TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes((Object)TypeToken.this)).filter((Predicate)TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.classes = var2;
         }

         return var2;
      }

      public TypeToken<T>.TypeSet interfaces() {
         throw new UnsupportedOperationException("classes().interfaces() not supported.");
      }

      public Set<Class<? super T>> rawTypes() {
         return ImmutableSet.copyOf((Collection)TypeToken.TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes((Iterable)TypeToken.this.getRawTypes()));
      }
   }

   private final class InterfaceSet extends TypeToken<T>.TypeSet {
      private static final long serialVersionUID = 0L;
      private final transient TypeToken<T>.TypeSet allTypes;
      @MonotonicNonNullDecl
      private transient ImmutableSet<TypeToken<? super T>> interfaces;

      InterfaceSet(TypeToken<T>.TypeSet var2) {
         super();
         this.allTypes = var2;
      }

      private Object readResolve() {
         return TypeToken.this.getTypes().interfaces();
      }

      public TypeToken<T>.TypeSet classes() {
         throw new UnsupportedOperationException("interfaces().classes() not supported.");
      }

      protected Set<TypeToken<? super T>> delegate() {
         ImmutableSet var1 = this.interfaces;
         ImmutableSet var2 = var1;
         if (var1 == null) {
            var2 = FluentIterable.from((Iterable)this.allTypes).filter((Predicate)TypeToken.TypeFilter.INTERFACE_ONLY).toSet();
            this.interfaces = var2;
         }

         return var2;
      }

      public TypeToken<T>.TypeSet interfaces() {
         return this;
      }

      public Set<Class<? super T>> rawTypes() {
         return FluentIterable.from((Iterable)TypeToken.TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable)TypeToken.this.getRawTypes())).filter(new Predicate<Class<?>>() {
            public boolean apply(Class<?> var1) {
               return var1.isInterface();
            }
         }).toSet();
      }
   }

   private static final class SimpleTypeToken<T> extends TypeToken<T> {
      private static final long serialVersionUID = 0L;

      SimpleTypeToken(Type var1) {
         super(var1, null);
      }
   }

   private abstract static class TypeCollector<K> {
      static final TypeToken.TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new TypeToken.TypeCollector<TypeToken<?>>() {
         Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> var1) {
            return var1.getGenericInterfaces();
         }

         Class<?> getRawType(TypeToken<?> var1) {
            return var1.getRawType();
         }

         @NullableDecl
         TypeToken<?> getSuperclass(TypeToken<?> var1) {
            return var1.getGenericSuperclass();
         }
      };
      static final TypeToken.TypeCollector<Class<?>> FOR_RAW_TYPE = new TypeToken.TypeCollector<Class<?>>() {
         Iterable<? extends Class<?>> getInterfaces(Class<?> var1) {
            return Arrays.asList(var1.getInterfaces());
         }

         Class<?> getRawType(Class<?> var1) {
            return var1;
         }

         @NullableDecl
         Class<?> getSuperclass(Class<?> var1) {
            return var1.getSuperclass();
         }
      };

      private TypeCollector() {
      }

      // $FF: synthetic method
      TypeCollector(Object var1) {
         this();
      }

      private int collectTypes(K var1, Map<? super K, Integer> var2) {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeProviderType(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeTypeToSubRef(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\n");
      }

      private static <K, V> ImmutableList<K> sortKeysByValue(final Map<K, V> var0, final Comparator<? super V> var1) {
         return (new Ordering<K>() {
            public int compare(K var1x, K var2) {
               return var1.compare(var0.get(var1x), var0.get(var2));
            }
         }).immutableSortedCopy(var0.keySet());
      }

      final TypeToken.TypeCollector<K> classesOnly() {
         return new TypeToken.TypeCollector.ForwardingTypeCollector<K>(this) {
            ImmutableList<K> collectTypes(Iterable<? extends K> var1) {
               ImmutableList.Builder var2 = ImmutableList.builder();
               Iterator var3 = var1.iterator();

               while(var3.hasNext()) {
                  Object var4 = var3.next();
                  if (!this.getRawType(var4).isInterface()) {
                     var2.add(var4);
                  }
               }

               return super.collectTypes(var2.build());
            }

            Iterable<? extends K> getInterfaces(K var1) {
               return ImmutableSet.of();
            }
         };
      }

      ImmutableList<K> collectTypes(Iterable<? extends K> var1) {
         HashMap var2 = Maps.newHashMap();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            this.collectTypes(var3.next(), var2);
         }

         return sortKeysByValue(var2, Ordering.natural().reverse());
      }

      final ImmutableList<K> collectTypes(K var1) {
         return this.collectTypes((Iterable)ImmutableList.of(var1));
      }

      abstract Iterable<? extends K> getInterfaces(K var1);

      abstract Class<?> getRawType(K var1);

      @NullableDecl
      abstract K getSuperclass(K var1);

      private static class ForwardingTypeCollector<K> extends TypeToken.TypeCollector<K> {
         private final TypeToken.TypeCollector<K> delegate;

         ForwardingTypeCollector(TypeToken.TypeCollector<K> var1) {
            super(null);
            this.delegate = var1;
         }

         Iterable<? extends K> getInterfaces(K var1) {
            return this.delegate.getInterfaces(var1);
         }

         Class<?> getRawType(K var1) {
            return this.delegate.getRawType(var1);
         }

         K getSuperclass(K var1) {
            return this.delegate.getSuperclass(var1);
         }
      }
   }

   private static enum TypeFilter implements Predicate<TypeToken<?>> {
      IGNORE_TYPE_VARIABLE_OR_WILDCARD {
         public boolean apply(TypeToken<?> var1) {
            boolean var2;
            if (!(var1.runtimeType instanceof TypeVariable) && !(var1.runtimeType instanceof WildcardType)) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      },
      INTERFACE_ONLY;

      static {
         TypeToken.TypeFilter var0 = new TypeToken.TypeFilter("INTERFACE_ONLY", 1) {
            public boolean apply(TypeToken<?> var1) {
               return var1.getRawType().isInterface();
            }
         };
         INTERFACE_ONLY = var0;
      }

      private TypeFilter() {
      }

      // $FF: synthetic method
      TypeFilter(Object var3) {
         this();
      }
   }

   public class TypeSet extends ForwardingSet<TypeToken<? super T>> implements Serializable {
      private static final long serialVersionUID = 0L;
      @MonotonicNonNullDecl
      private transient ImmutableSet<TypeToken<? super T>> types;

      TypeSet() {
      }

      public TypeToken<T>.TypeSet classes() {
         return TypeToken.this.new ClassSet();
      }

      protected Set<TypeToken<? super T>> delegate() {
         ImmutableSet var1 = this.types;
         ImmutableSet var2 = var1;
         if (var1 == null) {
            var2 = FluentIterable.from((Iterable)TypeToken.TypeCollector.FOR_GENERIC_TYPE.collectTypes((Object)TypeToken.this)).filter((Predicate)TypeToken.TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.types = var2;
         }

         return var2;
      }

      public TypeToken<T>.TypeSet interfaces() {
         return TypeToken.this.new InterfaceSet(this);
      }

      public Set<Class<? super T>> rawTypes() {
         return ImmutableSet.copyOf((Collection)TypeToken.TypeCollector.FOR_RAW_TYPE.collectTypes((Iterable)TypeToken.this.getRawTypes()));
      }
   }
}
