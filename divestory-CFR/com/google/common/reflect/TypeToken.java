/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeCapture;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeResolver;
import com.google.common.reflect.TypeVisitor;
import com.google.common.reflect.Types;
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

public abstract class TypeToken<T>
extends TypeCapture<T>
implements Serializable {
    private static final long serialVersionUID = 3637540370352322684L;
    @MonotonicNonNullDecl
    private transient TypeResolver covariantTypeResolver;
    @MonotonicNonNullDecl
    private transient TypeResolver invariantTypeResolver;
    private final Type runtimeType;

    protected TypeToken() {
        Type type;
        this.runtimeType = type = this.capture();
        Preconditions.checkState(type instanceof TypeVariable ^ true, "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", (Object)type);
    }

    protected TypeToken(Class<?> class_) {
        Type type = super.capture();
        if (type instanceof Class) {
            this.runtimeType = type;
            return;
        }
        this.runtimeType = TypeResolver.covariantly(class_).resolveType(type);
    }

    private TypeToken(Type type) {
        this.runtimeType = Preconditions.checkNotNull(type);
    }

    private static Bounds any(Type[] arrtype) {
        return new Bounds(arrtype, true);
    }

    @NullableDecl
    private TypeToken<? super T> boundAsSuperclass(Type object) {
        TypeToken<?> typeToken = TypeToken.of((Type)object);
        object = typeToken;
        if (!typeToken.getRawType().isInterface()) return object;
        return null;
    }

    private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] arrtype) {
        ImmutableList.Builder builder = ImmutableList.builder();
        int n = arrtype.length;
        int n2 = 0;
        while (n2 < n) {
            TypeToken<?> typeToken = TypeToken.of(arrtype[n2]);
            if (typeToken.getRawType().isInterface()) {
                builder.add(typeToken);
            }
            ++n2;
        }
        return builder.build();
    }

    private static Type canonicalizeTypeArg(TypeVariable<?> type, Type type2) {
        if (!(type2 instanceof WildcardType)) return TypeToken.canonicalizeWildcardsInType(type2);
        return TypeToken.canonicalizeWildcardType(type, (WildcardType)type2);
    }

    private static WildcardType canonicalizeWildcardType(TypeVariable<?> object, WildcardType wildcardType) {
        Type[] arrtype = object.getBounds();
        object = new ArrayList();
        Type[] arrtype2 = wildcardType.getUpperBounds();
        int n = arrtype2.length;
        int n2 = 0;
        while (n2 < n) {
            Type type = arrtype2[n2];
            if (!TypeToken.any(arrtype).isSubtypeOf(type)) {
                object.add(TypeToken.canonicalizeWildcardsInType(type));
            }
            ++n2;
        }
        return new Types.WildcardTypeImpl(wildcardType.getLowerBounds(), object.toArray(new Type[0]));
    }

    private static ParameterizedType canonicalizeWildcardsInParameterizedType(ParameterizedType parameterizedType) {
        Class class_ = (Class)parameterizedType.getRawType();
        TypeVariable<Class<T>>[] arrtypeVariable = class_.getTypeParameters();
        Type[] arrtype = parameterizedType.getActualTypeArguments();
        int n = 0;
        while (n < arrtype.length) {
            arrtype[n] = TypeToken.canonicalizeTypeArg(arrtypeVariable[n], arrtype[n]);
            ++n;
        }
        return Types.newParameterizedTypeWithOwner(parameterizedType.getOwnerType(), class_, arrtype);
    }

    private static Type canonicalizeWildcardsInType(Type type) {
        if (type instanceof ParameterizedType) {
            return TypeToken.canonicalizeWildcardsInParameterizedType((ParameterizedType)type);
        }
        Type type2 = type;
        if (!(type instanceof GenericArrayType)) return type2;
        return Types.newArrayType(TypeToken.canonicalizeWildcardsInType(((GenericArrayType)type).getGenericComponentType()));
    }

    private static Bounds every(Type[] arrtype) {
        return new Bounds(arrtype, false);
    }

    private TypeToken<? extends T> getArraySubtype(Class<?> class_) {
        return TypeToken.of(TypeToken.newArrayClassOrGenericArrayType(this.getComponentType().getSubtype(class_.getComponentType()).runtimeType));
    }

    private TypeToken<? super T> getArraySupertype(Class<? super T> class_) {
        return TypeToken.of(TypeToken.newArrayClassOrGenericArrayType(Preconditions.checkNotNull(this.getComponentType(), (String)"%s isn't a super type of %s", class_, (Object)this).getSupertype(class_.getComponentType()).runtimeType));
    }

    private TypeResolver getCovariantTypeResolver() {
        TypeResolver typeResolver;
        TypeResolver typeResolver2 = typeResolver = this.covariantTypeResolver;
        if (typeResolver != null) return typeResolver2;
        this.covariantTypeResolver = typeResolver2 = TypeResolver.covariantly(this.runtimeType);
        return typeResolver2;
    }

    private TypeResolver getInvariantTypeResolver() {
        TypeResolver typeResolver;
        TypeResolver typeResolver2 = typeResolver = this.invariantTypeResolver;
        if (typeResolver != null) return typeResolver2;
        this.invariantTypeResolver = typeResolver2 = TypeResolver.invariantly(this.runtimeType);
        return typeResolver2;
    }

    @NullableDecl
    private Type getOwnerTypeIfPresent() {
        Type type = this.runtimeType;
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType)type).getOwnerType();
        }
        if (!(type instanceof Class)) return null;
        return ((Class)type).getEnclosingClass();
    }

    private ImmutableSet<Class<? super T>> getRawTypes() {
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        new TypeVisitor(){

            @Override
            void visitClass(Class<?> class_) {
                builder.add(class_);
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                builder.add(Types.getArrayClass(TypeToken.of(genericArrayType.getGenericComponentType()).getRawType()));
            }

            @Override
            void visitParameterizedType(ParameterizedType parameterizedType) {
                builder.add((Class)parameterizedType.getRawType());
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                this.visit(typeVariable.getBounds());
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                this.visit(wildcardType.getUpperBounds());
            }
        }.visit(this.runtimeType);
        return builder.build();
    }

    private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> class_, Type[] object) {
        if (((Type[])object).length > 0) {
            return TypeToken.of(object[0]).getSubtype(class_);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(class_);
        ((StringBuilder)object).append(" isn't a subclass of ");
        ((StringBuilder)object).append(this);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> class_, Type[] object) {
        int n = ((Type[])object).length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                object = new StringBuilder();
                ((StringBuilder)object).append(class_);
                ((StringBuilder)object).append(" isn't a super type of ");
                ((StringBuilder)object).append(this);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            TypeToken<?> typeToken = TypeToken.of(object[n2]);
            if (typeToken.isSubtypeOf(class_)) {
                return typeToken.getSupertype(class_);
            }
            ++n2;
        } while (true);
    }

    private boolean is(Type type, TypeVariable<?> typeVariable) {
        boolean bl = this.runtimeType.equals(type);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (!(type instanceof WildcardType)) return TypeToken.canonicalizeWildcardsInType(this.runtimeType).equals(TypeToken.canonicalizeWildcardsInType(type));
        if (!TypeToken.every((type = TypeToken.canonicalizeWildcardType(typeVariable, (WildcardType)type)).getUpperBounds()).isSupertypeOf(this.runtimeType)) return false;
        if (!TypeToken.every(type.getLowerBounds()).isSubtypeOf(this.runtimeType)) return false;
        return bl2;
    }

    private boolean isOwnedBySubtypeOf(Type type) {
        Type type2;
        Iterator iterator2 = this.getTypes().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while ((type2 = ((TypeToken)iterator2.next()).getOwnerTypeIfPresent()) == null || !TypeToken.of(type2).isSubtypeOf(type));
        return true;
    }

    private boolean isSubtypeOfArrayType(GenericArrayType genericArrayType) {
        Type type = this.runtimeType;
        if (type instanceof Class) {
            if (((Class)(type = (Class)type)).isArray()) return TypeToken.of(((Class)type).getComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
            return false;
        }
        if (!(type instanceof GenericArrayType)) return false;
        return TypeToken.of(((GenericArrayType)type).getGenericComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
    }

    private boolean isSubtypeOfParameterizedType(ParameterizedType parameterizedType) {
        Type[] arrtype = TypeToken.of(parameterizedType).getRawType();
        boolean bl = this.someRawTypeIsSubclassOf((Class<?>)arrtype);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        TypeVariable<Class<?>>[] arrtypeVariable = arrtype.getTypeParameters();
        arrtype = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < arrtypeVariable.length; ++i) {
            if (TypeToken.super.is(arrtype[i], arrtypeVariable[i])) continue;
            return false;
        }
        if (Modifier.isStatic(((Class)parameterizedType.getRawType()).getModifiers())) return true;
        if (parameterizedType.getOwnerType() == null) return true;
        if (!this.isOwnedBySubtypeOf(parameterizedType.getOwnerType())) return bl2;
        return true;
    }

    private boolean isSupertypeOfArray(GenericArrayType genericArrayType) {
        Type type = this.runtimeType;
        if (type instanceof Class) {
            if (((Class)(type = (Class)type)).isArray()) return TypeToken.of(genericArrayType.getGenericComponentType()).isSubtypeOf(((Class)type).getComponentType());
            return ((Class)type).isAssignableFrom(Object[].class);
        }
        if (!(type instanceof GenericArrayType)) return false;
        return TypeToken.of(genericArrayType.getGenericComponentType()).isSubtypeOf(((GenericArrayType)this.runtimeType).getGenericComponentType());
    }

    private boolean isWrapper() {
        return Primitives.allWrapperTypes().contains(this.runtimeType);
    }

    private static Type newArrayClassOrGenericArrayType(Type type) {
        return Types.JavaVersion.JAVA7.newArrayType(type);
    }

    public static <T> TypeToken<T> of(Class<T> class_) {
        return new SimpleTypeToken(class_);
    }

    public static TypeToken<?> of(Type type) {
        return new SimpleTypeToken(type);
    }

    private TypeToken<?> resolveSupertype(Type object) {
        object = TypeToken.of(this.getCovariantTypeResolver().resolveType((Type)object));
        ((TypeToken)object).covariantTypeResolver = this.covariantTypeResolver;
        ((TypeToken)object).invariantTypeResolver = this.invariantTypeResolver;
        return object;
    }

    private Type resolveTypeArgsForSubclass(Class<?> serializable) {
        if (this.runtimeType instanceof Class) {
            if (((Class)serializable).getTypeParameters().length == 0) return serializable;
            if (this.getRawType().getTypeParameters().length != 0) {
                return serializable;
            }
        }
        serializable = TypeToken.toGenericType(serializable);
        Type type = serializable.getSupertype(this.getRawType()).runtimeType;
        return new TypeResolver().where(type, this.runtimeType).resolveType(((TypeToken)serializable).runtimeType);
    }

    private boolean someRawTypeIsSubclassOf(Class<?> class_) {
        Iterator iterator2 = this.getRawTypes().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!class_.isAssignableFrom((Class)iterator2.next()));
        return true;
    }

    static <T> TypeToken<? extends T> toGenericType(Class<T> class_) {
        if (class_.isArray()) {
            return TypeToken.of(Types.newArrayType(TypeToken.toGenericType(class_.getComponentType()).runtimeType));
        }
        Type[] arrtype = class_.getTypeParameters();
        Type type = class_.isMemberClass() && !Modifier.isStatic(class_.getModifiers()) ? TypeToken.toGenericType(class_.getEnclosingClass()).runtimeType : null;
        if (arrtype.length > 0) return TypeToken.of(Types.newParameterizedTypeWithOwner(type, class_, arrtype));
        if (type == null) return TypeToken.of(class_);
        if (type == class_.getEnclosingClass()) return TypeToken.of(class_);
        return TypeToken.of(Types.newParameterizedTypeWithOwner(type, class_, arrtype));
    }

    public final Invokable<T, T> constructor(Constructor<?> constructor) {
        boolean bl = constructor.getDeclaringClass() == this.getRawType();
        Preconditions.checkArgument(bl, "%s not declared by %s", constructor, this.getRawType());
        return new Invokable.ConstructorInvokable<T>(constructor){

            @Override
            Type[] getGenericExceptionTypes() {
                return TypeToken.this.getCovariantTypeResolver().resolveTypesInPlace(super.getGenericExceptionTypes());
            }

            @Override
            Type[] getGenericParameterTypes() {
                return TypeToken.this.getInvariantTypeResolver().resolveTypesInPlace(super.getGenericParameterTypes());
            }

            @Override
            Type getGenericReturnType() {
                return TypeToken.this.getCovariantTypeResolver().resolveType(super.getGenericReturnType());
            }

            @Override
            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.getOwnerType());
                stringBuilder.append("(");
                stringBuilder.append(Joiner.on(", ").join(this.getGenericParameterTypes()));
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
        };
    }

    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof TypeToken)) return false;
        object = (TypeToken)object;
        return this.runtimeType.equals(((TypeToken)object).runtimeType);
    }

    @NullableDecl
    public final TypeToken<?> getComponentType() {
        Type type = Types.getComponentType(this.runtimeType);
        if (type != null) return TypeToken.of(type);
        return null;
    }

    final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
        Type[] arrtype = this.runtimeType;
        if (arrtype instanceof TypeVariable) {
            return this.boundsAsInterfaces(((TypeVariable)arrtype).getBounds());
        }
        if (arrtype instanceof WildcardType) {
            return this.boundsAsInterfaces(((WildcardType)arrtype).getUpperBounds());
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        arrtype = this.getRawType().getGenericInterfaces();
        int n = arrtype.length;
        int n2 = 0;
        while (n2 < n) {
            builder.add(this.resolveSupertype(arrtype[n2]));
            ++n2;
        }
        return builder.build();
    }

    @NullableDecl
    final TypeToken<? super T> getGenericSuperclass() {
        Type type = this.runtimeType;
        if (type instanceof TypeVariable) {
            return this.boundAsSuperclass(((TypeVariable)type).getBounds()[0]);
        }
        if (type instanceof WildcardType) {
            return this.boundAsSuperclass(((WildcardType)type).getUpperBounds()[0]);
        }
        type = this.getRawType().getGenericSuperclass();
        if (type != null) return this.resolveSupertype(type);
        return null;
    }

    public final Class<? super T> getRawType() {
        return (Class)this.getRawTypes().iterator().next();
    }

    public final TypeToken<? extends T> getSubtype(Class<?> serializable) {
        Preconditions.checkArgument(this.runtimeType instanceof TypeVariable ^ true, "Cannot get subtype of type variable <%s>", (Object)this);
        Type type = this.runtimeType;
        if (type instanceof WildcardType) {
            return this.getSubtypeFromLowerBounds((Class<?>)serializable, ((WildcardType)type).getLowerBounds());
        }
        if (this.isArray()) {
            return this.getArraySubtype((Class<?>)serializable);
        }
        Preconditions.checkArgument(this.getRawType().isAssignableFrom((Class<?>)serializable), "%s isn't a subclass of %s", serializable, (Object)this);
        serializable = TypeToken.of(this.resolveTypeArgsForSubclass((Class<?>)serializable));
        Preconditions.checkArgument(((TypeToken)serializable).isSubtypeOf(this), "%s does not appear to be a subtype of %s", (Object)serializable, (Object)this);
        return serializable;
    }

    public final TypeToken<? super T> getSupertype(Class<? super T> class_) {
        Preconditions.checkArgument(this.someRawTypeIsSubclassOf(class_), "%s is not a super class of %s", class_, (Object)this);
        Type type = this.runtimeType;
        if (type instanceof TypeVariable) {
            return this.getSupertypeFromUpperBounds(class_, ((TypeVariable)type).getBounds());
        }
        if (type instanceof WildcardType) {
            return this.getSupertypeFromUpperBounds(class_, ((WildcardType)type).getUpperBounds());
        }
        if (!class_.isArray()) return this.resolveSupertype(TypeToken.toGenericType(class_).runtimeType);
        return this.getArraySupertype(class_);
    }

    public final Type getType() {
        return this.runtimeType;
    }

    public final TypeToken<T> getTypes() {
        return new TypeSet();
    }

    public int hashCode() {
        return this.runtimeType.hashCode();
    }

    public final boolean isArray() {
        if (this.getComponentType() == null) return false;
        return true;
    }

    public final boolean isPrimitive() {
        Type type = this.runtimeType;
        if (!(type instanceof Class)) return false;
        if (!((Class)type).isPrimitive()) return false;
        return true;
    }

    public final boolean isSubtypeOf(TypeToken<?> typeToken) {
        return this.isSubtypeOf(typeToken.getType());
    }

    public final boolean isSubtypeOf(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof WildcardType) {
            return TypeToken.any(((WildcardType)type).getLowerBounds()).isSupertypeOf(this.runtimeType);
        }
        Type type2 = this.runtimeType;
        if (type2 instanceof WildcardType) {
            return TypeToken.any(((WildcardType)type2).getUpperBounds()).isSubtypeOf(type);
        }
        boolean bl = type2 instanceof TypeVariable;
        boolean bl2 = false;
        if (bl) {
            if (type2.equals(type)) return true;
            if (!TypeToken.any(((TypeVariable)this.runtimeType).getBounds()).isSubtypeOf(type)) return bl2;
            return true;
        }
        if (type2 instanceof GenericArrayType) {
            return TypeToken.super.isSupertypeOfArray((GenericArrayType)this.runtimeType);
        }
        if (type instanceof Class) {
            return this.someRawTypeIsSubclassOf((Class)type);
        }
        if (type instanceof ParameterizedType) {
            return this.isSubtypeOfParameterizedType((ParameterizedType)type);
        }
        if (!(type instanceof GenericArrayType)) return false;
        return this.isSubtypeOfArrayType((GenericArrayType)type);
    }

    public final boolean isSupertypeOf(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(this.getType());
    }

    public final boolean isSupertypeOf(Type type) {
        return TypeToken.of(type).isSubtypeOf(this.getType());
    }

    public final Invokable<T, Object> method(Method method) {
        Preconditions.checkArgument(this.someRawTypeIsSubclassOf(method.getDeclaringClass()), "%s not declared by %s", (Object)method, (Object)this);
        return new Invokable.MethodInvokable<T>(method){

            @Override
            Type[] getGenericExceptionTypes() {
                return TypeToken.this.getCovariantTypeResolver().resolveTypesInPlace(super.getGenericExceptionTypes());
            }

            @Override
            Type[] getGenericParameterTypes() {
                return TypeToken.this.getInvariantTypeResolver().resolveTypesInPlace(super.getGenericParameterTypes());
            }

            @Override
            Type getGenericReturnType() {
                return TypeToken.this.getCovariantTypeResolver().resolveType(super.getGenericReturnType());
            }

            @Override
            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.getOwnerType());
                stringBuilder.append(".");
                stringBuilder.append(super.toString());
                return stringBuilder.toString();
            }
        };
    }

    final TypeToken<T> rejectTypeVariables() {
        new TypeVisitor(){

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                this.visit(genericArrayType.getGenericComponentType());
            }

            @Override
            void visitParameterizedType(ParameterizedType parameterizedType) {
                this.visit(parameterizedType.getActualTypeArguments());
                this.visit(parameterizedType.getOwnerType());
            }

            @Override
            void visitTypeVariable(TypeVariable<?> object) {
                object = new StringBuilder();
                ((StringBuilder)object).append(TypeToken.this.runtimeType);
                ((StringBuilder)object).append("contains a type variable and is not safe for the operation");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                this.visit(wildcardType.getLowerBounds());
                this.visit(wildcardType.getUpperBounds());
            }
        }.visit(this.runtimeType);
        return this;
    }

    public final TypeToken<?> resolveType(Type type) {
        Preconditions.checkNotNull(type);
        return TypeToken.of(this.getInvariantTypeResolver().resolveType(type));
    }

    public String toString() {
        return Types.toString(this.runtimeType);
    }

    public final TypeToken<T> unwrap() {
        if (!this.isWrapper()) return this;
        return TypeToken.of(Primitives.unwrap((Class)this.runtimeType));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, TypeToken<X> typeToken) {
        return new SimpleTypeToken(new TypeResolver().where(ImmutableMap.of(new TypeResolver.TypeVariableKey(typeParameter.typeVariable), typeToken.runtimeType)).resolveType(this.runtimeType));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, Class<X> class_) {
        return this.where(typeParameter, TypeToken.of(class_));
    }

    public final TypeToken<T> wrap() {
        if (!this.isPrimitive()) return this;
        return TypeToken.of(Primitives.wrap((Class)this.runtimeType));
    }

    protected Object writeReplace() {
        return TypeToken.of(new TypeResolver().resolveType(this.runtimeType));
    }

    private static class Bounds {
        private final Type[] bounds;
        private final boolean target;

        Bounds(Type[] arrtype, boolean bl) {
            this.bounds = arrtype;
            this.target = bl;
        }

        boolean isSubtypeOf(Type type) {
            Type[] arrtype = this.bounds;
            int n = arrtype.length;
            int n2 = 0;
            while (n2 < n) {
                boolean bl;
                boolean bl2 = TypeToken.of(arrtype[n2]).isSubtypeOf(type);
                if (bl2 == (bl = this.target)) {
                    return bl;
                }
                ++n2;
            }
            return this.target ^ true;
        }

        boolean isSupertypeOf(Type object) {
            object = TypeToken.of((Type)object);
            Type[] arrtype = this.bounds;
            int n = arrtype.length;
            int n2 = 0;
            while (n2 < n) {
                boolean bl;
                boolean bl2 = ((TypeToken)object).isSubtypeOf(arrtype[n2]);
                if (bl2 == (bl = this.target)) {
                    return bl;
                }
                ++n2;
            }
            return this.target ^ true;
        }
    }

    private final class ClassSet
    extends TypeToken<T> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        private transient ImmutableSet<TypeToken<? super T>> classes;

        private ClassSet() {
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().classes();
        }

        public TypeToken<T> classes() {
            return this;
        }

        protected Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<T>> immutableSet;
            ImmutableSet<TypeToken<T>> immutableSet2 = immutableSet = this.classes;
            if (immutableSet != null) return immutableSet2;
            immutableSet2 = FluentIterable.from(TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes(TypeToken.this)).filter(TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.classes = immutableSet2;
            return immutableSet2;
        }

        public TypeToken<T> interfaces() {
            throw new UnsupportedOperationException("classes().interfaces() not supported.");
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf(TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes((Class<?>)((Object)TypeToken.this.getRawTypes())));
        }
    }

    private final class InterfaceSet
    extends TypeToken<T> {
        private static final long serialVersionUID = 0L;
        private final transient TypeToken<T> allTypes;
        @MonotonicNonNullDecl
        private transient ImmutableSet<TypeToken<? super T>> interfaces;

        InterfaceSet(TypeToken<T> typeToken) {
            this.allTypes = typeToken;
        }

        private Object readResolve() {
            return this$0.getTypes().interfaces();
        }

        public TypeToken<T> classes() {
            throw new UnsupportedOperationException("interfaces().classes() not supported.");
        }

        protected Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<T>> immutableSet;
            ImmutableSet<TypeToken<T>> immutableSet2 = immutableSet = this.interfaces;
            if (immutableSet != null) return immutableSet2;
            immutableSet2 = FluentIterable.from(this.allTypes).filter(TypeFilter.INTERFACE_ONLY).toSet();
            this.interfaces = immutableSet2;
            return immutableSet2;
        }

        public TypeToken<T> interfaces() {
            return this;
        }

        public Set<Class<? super T>> rawTypes() {
            return FluentIterable.from(TypeCollector.FOR_RAW_TYPE.collectTypes((Class<?>)((Object)this$0.getRawTypes()))).filter(new Predicate<Class<?>>(){

                @Override
                public boolean apply(Class<?> class_) {
                    return class_.isInterface();
                }
            }).toSet();
        }

    }

    private static final class SimpleTypeToken<T>
    extends TypeToken<T> {
        private static final long serialVersionUID = 0L;

        SimpleTypeToken(Type type) {
            super(type);
        }
    }

    private static abstract class TypeCollector<K> {
        static final TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new TypeCollector<TypeToken<?>>(){

            @Override
            Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> typeToken) {
                return typeToken.getGenericInterfaces();
            }

            @Override
            Class<?> getRawType(TypeToken<?> typeToken) {
                return typeToken.getRawType();
            }

            @NullableDecl
            @Override
            TypeToken<?> getSuperclass(TypeToken<?> typeToken) {
                return typeToken.getGenericSuperclass();
            }
        };
        static final TypeCollector<Class<?>> FOR_RAW_TYPE = new TypeCollector<Class<?>>(){

            @Override
            Iterable<? extends Class<?>> getInterfaces(Class<?> class_) {
                return Arrays.asList(class_.getInterfaces());
            }

            @Override
            Class<?> getRawType(Class<?> class_) {
                return class_;
            }

            @NullableDecl
            @Override
            Class<?> getSuperclass(Class<?> class_) {
                return class_.getSuperclass();
            }
        };

        private TypeCollector() {
        }

        private int collectTypes(K k, Map<? super K, Integer> map) {
            throw new RuntimeException("d2j fail translate: java.lang.RuntimeException\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeProviderType(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeTypeToSubRef(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\n");
        }

        private static <K, V> ImmutableList<K> sortKeysByValue(final Map<K, V> map, final Comparator<? super V> comparator) {
            return new Ordering<K>(){

                @Override
                public int compare(K k, K k2) {
                    return comparator.compare(map.get(k), map.get(k2));
                }
            }.immutableSortedCopy(map.keySet());
        }

        final TypeCollector<K> classesOnly() {
            return new ForwardingTypeCollector<K>(this){

                @Override
                ImmutableList<K> collectTypes(Iterable<? extends K> iterable) {
                    ImmutableList.Builder builder = ImmutableList.builder();
                    Iterator<K> iterator2 = iterable.iterator();
                    while (iterator2.hasNext()) {
                        iterable = iterator2.next();
                        if (this.getRawType(iterable).isInterface()) continue;
                        builder.add(iterable);
                    }
                    return super.collectTypes(builder.build());
                }

                @Override
                Iterable<? extends K> getInterfaces(K k) {
                    return ImmutableSet.of();
                }
            };
        }

        ImmutableList<K> collectTypes(Iterable<? extends K> object) {
            HashMap hashMap = Maps.newHashMap();
            object = object.iterator();
            while (object.hasNext()) {
                this.collectTypes(object.next(), hashMap);
            }
            return TypeCollector.sortKeysByValue(hashMap, Ordering.natural().reverse());
        }

        final ImmutableList<K> collectTypes(K k) {
            return this.collectTypes((K)ImmutableList.of(k));
        }

        abstract Iterable<? extends K> getInterfaces(K var1);

        abstract Class<?> getRawType(K var1);

        @NullableDecl
        abstract K getSuperclass(K var1);

        private static class ForwardingTypeCollector<K>
        extends TypeCollector<K> {
            private final TypeCollector<K> delegate;

            ForwardingTypeCollector(TypeCollector<K> typeCollector) {
                this.delegate = typeCollector;
            }

            @Override
            Iterable<? extends K> getInterfaces(K k) {
                return this.delegate.getInterfaces(k);
            }

            @Override
            Class<?> getRawType(K k) {
                return this.delegate.getRawType(k);
            }

            @Override
            K getSuperclass(K k) {
                return this.delegate.getSuperclass(k);
            }
        }

    }

    private static abstract class TypeFilter
    extends Enum<TypeFilter>
    implements Predicate<TypeToken<?>> {
        private static final /* synthetic */ TypeFilter[] $VALUES;
        public static final /* enum */ TypeFilter IGNORE_TYPE_VARIABLE_OR_WILDCARD;
        public static final /* enum */ TypeFilter INTERFACE_ONLY;

        static {
            TypeFilter typeFilter;
            IGNORE_TYPE_VARIABLE_OR_WILDCARD = new TypeFilter(){

                @Override
                public boolean apply(TypeToken<?> typeToken) {
                    if (typeToken.runtimeType instanceof TypeVariable) return false;
                    if (typeToken.runtimeType instanceof WildcardType) return false;
                    return true;
                }
            };
            INTERFACE_ONLY = typeFilter = new TypeFilter(){

                @Override
                public boolean apply(TypeToken<?> typeToken) {
                    return typeToken.getRawType().isInterface();
                }
            };
            $VALUES = new TypeFilter[]{IGNORE_TYPE_VARIABLE_OR_WILDCARD, typeFilter};
        }

        public static TypeFilter valueOf(String string2) {
            return Enum.valueOf(TypeFilter.class, string2);
        }

        public static TypeFilter[] values() {
            return (TypeFilter[])$VALUES.clone();
        }

    }

    public class TypeSet
    extends ForwardingSet<TypeToken<? super T>>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        private transient ImmutableSet<TypeToken<? super T>> types;

        TypeSet() {
        }

        public TypeToken<T> classes() {
            return new ClassSet();
        }

        @Override
        protected Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<T>> immutableSet;
            ImmutableSet<TypeToken<T>> immutableSet2 = immutableSet = this.types;
            if (immutableSet != null) return immutableSet2;
            immutableSet2 = FluentIterable.from(TypeCollector.FOR_GENERIC_TYPE.collectTypes(TypeToken.this)).filter(TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.types = immutableSet2;
            return immutableSet2;
        }

        public TypeToken<T> interfaces() {
            return new InterfaceSet(TypeToken.this, this);
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf(TypeCollector.FOR_RAW_TYPE.collectTypes((Class<?>)((Object)TypeToken.this.getRawTypes())));
        }
    }

}

