/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeVisitor;
import com.google.common.reflect.Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class TypeResolver {
    private final TypeTable typeTable;

    public TypeResolver() {
        this.typeTable = new TypeTable();
    }

    private TypeResolver(TypeTable typeTable) {
        this.typeTable = typeTable;
    }

    static TypeResolver covariantly(Type type) {
        return new TypeResolver().where(TypeMappingIntrospector.getTypeMappings(type));
    }

    private static <T> T expectArgument(Class<T> class_, Object object) {
        T t;
        try {
            t = class_.cast(object);
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object);
            stringBuilder.append(" is not a ");
            stringBuilder.append(class_.getSimpleName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return t;
    }

    static TypeResolver invariantly(Type type) {
        type = WildcardCapturer.INSTANCE.capture(type);
        return new TypeResolver().where(TypeMappingIntrospector.getTypeMappings(type));
    }

    private static void populateTypeMappings(final Map<TypeVariableKey, Type> map, Type type, final Type type2) {
        if (type.equals(type2)) {
            return;
        }
        new TypeVisitor(){

            @Override
            void visitClass(Class<?> class_) {
                if (type2 instanceof WildcardType) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No type mapping from ");
                stringBuilder.append(class_);
                stringBuilder.append(" to ");
                stringBuilder.append(type2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                Type type = type2;
                if (type instanceof WildcardType) {
                    return;
                }
                boolean bl = (type = Types.getComponentType(type)) != null;
                Preconditions.checkArgument(bl, "%s is not an array type.", (Object)type2);
                TypeResolver.populateTypeMappings(map, genericArrayType.getGenericComponentType(), type);
            }

            @Override
            void visitParameterizedType(ParameterizedType parameterizedType) {
                Type type = type2;
                if (type instanceof WildcardType) {
                    return;
                }
                type = (ParameterizedType)TypeResolver.expectArgument(ParameterizedType.class, type);
                if (parameterizedType.getOwnerType() != null && type.getOwnerType() != null) {
                    TypeResolver.populateTypeMappings(map, parameterizedType.getOwnerType(), type.getOwnerType());
                }
                Preconditions.checkArgument(parameterizedType.getRawType().equals(type.getRawType()), "Inconsistent raw type: %s vs. %s", (Object)parameterizedType, (Object)type2);
                Type[] arrtype = parameterizedType.getActualTypeArguments();
                Type[] arrtype2 = type.getActualTypeArguments();
                int n = arrtype.length;
                int n2 = arrtype2.length;
                int n3 = 0;
                boolean bl = n == n2;
                Preconditions.checkArgument(bl, "%s not compatible with %s", (Object)parameterizedType, (Object)type);
                while (n3 < arrtype.length) {
                    TypeResolver.populateTypeMappings(map, arrtype[n3], arrtype2[n3]);
                    ++n3;
                }
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                map.put(new TypeVariableKey(typeVariable), type2);
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                Type[] arrtype = type2;
                if (!(arrtype instanceof WildcardType)) {
                    return;
                }
                Type[] arrtype2 = arrtype;
                arrtype = wildcardType.getUpperBounds();
                Type[] arrtype3 = arrtype2.getUpperBounds();
                Type[] arrtype4 = wildcardType.getLowerBounds();
                arrtype2 = arrtype2.getLowerBounds();
                int n = arrtype.length;
                int n2 = arrtype3.length;
                int n3 = 0;
                boolean bl = n == n2 && arrtype4.length == arrtype2.length;
                Preconditions.checkArgument(bl, "Incompatible type: %s vs. %s", (Object)wildcardType, (Object)type2);
                n2 = 0;
                do {
                    n = n3;
                    if (n2 >= arrtype.length) {
                        while (n < arrtype4.length) {
                            TypeResolver.populateTypeMappings(map, arrtype4[n], arrtype2[n]);
                            ++n;
                        }
                        return;
                    }
                    TypeResolver.populateTypeMappings(map, arrtype[n2], arrtype3[n2]);
                    ++n2;
                } while (true);
            }
        }.visit(type);
    }

    private Type resolveGenericArrayType(GenericArrayType genericArrayType) {
        return Types.newArrayType(this.resolveType(genericArrayType.getGenericComponentType()));
    }

    private ParameterizedType resolveParameterizedType(ParameterizedType arrtype) {
        Type type = arrtype.getOwnerType();
        type = type == null ? null : this.resolveType(type);
        Type type2 = this.resolveType(arrtype.getRawType());
        arrtype = this.resolveTypes(arrtype.getActualTypeArguments());
        return Types.newParameterizedTypeWithOwner(type, (Class)type2, arrtype);
    }

    private Type[] resolveTypes(Type[] arrtype) {
        Type[] arrtype2 = new Type[arrtype.length];
        int n = 0;
        while (n < arrtype.length) {
            arrtype2[n] = this.resolveType(arrtype[n]);
            ++n;
        }
        return arrtype2;
    }

    private WildcardType resolveWildcardType(WildcardType arrtype) {
        Type[] arrtype2 = arrtype.getLowerBounds();
        arrtype = arrtype.getUpperBounds();
        return new Types.WildcardTypeImpl(this.resolveTypes(arrtype2), this.resolveTypes(arrtype));
    }

    public Type resolveType(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof TypeVariable) {
            return this.typeTable.resolve((TypeVariable)type);
        }
        if (type instanceof ParameterizedType) {
            return this.resolveParameterizedType((ParameterizedType)type);
        }
        if (type instanceof GenericArrayType) {
            return this.resolveGenericArrayType((GenericArrayType)type);
        }
        Type type2 = type;
        if (!(type instanceof WildcardType)) return type2;
        return this.resolveWildcardType((WildcardType)type);
    }

    Type[] resolveTypesInPlace(Type[] arrtype) {
        int n = 0;
        while (n < arrtype.length) {
            arrtype[n] = this.resolveType(arrtype[n]);
            ++n;
        }
        return arrtype;
    }

    public TypeResolver where(Type type, Type type2) {
        HashMap<TypeVariableKey, Type> hashMap = Maps.newHashMap();
        TypeResolver.populateTypeMappings(hashMap, Preconditions.checkNotNull(type), Preconditions.checkNotNull(type2));
        return this.where(hashMap);
    }

    TypeResolver where(Map<TypeVariableKey, ? extends Type> map) {
        return new TypeResolver(this.typeTable.where(map));
    }

    private static final class TypeMappingIntrospector
    extends TypeVisitor {
        private final Map<TypeVariableKey, Type> mappings = Maps.newHashMap();

        private TypeMappingIntrospector() {
        }

        static ImmutableMap<TypeVariableKey, Type> getTypeMappings(Type type) {
            Preconditions.checkNotNull(type);
            TypeMappingIntrospector typeMappingIntrospector = new TypeMappingIntrospector();
            typeMappingIntrospector.visit(type);
            return ImmutableMap.copyOf(typeMappingIntrospector.mappings);
        }

        private void map(TypeVariableKey typeVariableKey, Type type) {
            if (this.mappings.containsKey(typeVariableKey)) {
                return;
            }
            Type type2 = type;
            do {
                if (type2 == null) {
                    this.mappings.put(typeVariableKey, type);
                    return;
                }
                if (typeVariableKey.equalsType(type2)) {
                    while (type != null) {
                        type = this.mappings.remove(TypeVariableKey.forLookup(type));
                    }
                    return;
                }
                type2 = this.mappings.get(TypeVariableKey.forLookup(type2));
            } while (true);
        }

        @Override
        void visitClass(Class<?> class_) {
            this.visit(class_.getGenericSuperclass());
            this.visit(class_.getGenericInterfaces());
        }

        @Override
        void visitParameterizedType(ParameterizedType parameterizedType) {
            Type[] arrtype;
            Class class_ = (Class)parameterizedType.getRawType();
            TypeVariable<Class<T>>[] arrtypeVariable = class_.getTypeParameters();
            boolean bl = arrtypeVariable.length == (arrtype = parameterizedType.getActualTypeArguments()).length;
            Preconditions.checkState(bl);
            int n = 0;
            do {
                if (n >= arrtypeVariable.length) {
                    this.visit(class_);
                    this.visit(parameterizedType.getOwnerType());
                    return;
                }
                this.map(new TypeVariableKey(arrtypeVariable[n]), arrtype[n]);
                ++n;
            } while (true);
        }

        @Override
        void visitTypeVariable(TypeVariable<?> typeVariable) {
            this.visit(typeVariable.getBounds());
        }

        @Override
        void visitWildcardType(WildcardType wildcardType) {
            this.visit(wildcardType.getUpperBounds());
        }
    }

    private static class TypeTable {
        private final ImmutableMap<TypeVariableKey, Type> map;

        TypeTable() {
            this.map = ImmutableMap.of();
        }

        private TypeTable(ImmutableMap<TypeVariableKey, Type> immutableMap) {
            this.map = immutableMap;
        }

        final Type resolve(final TypeVariable<?> typeVariable) {
            return this.resolveInternal(typeVariable, new TypeTable(){

                @Override
                public Type resolveInternal(TypeVariable<?> typeVariable2, TypeTable typeTable) {
                    if (!typeVariable2.getGenericDeclaration().equals(typeVariable.getGenericDeclaration())) return this.resolveInternal(typeVariable2, typeTable);
                    return typeVariable2;
                }
            });
        }

        Type resolveInternal(TypeVariable<?> typeVariable, TypeTable arrobject) {
            Object[] arrobject2 = this.map.get(new TypeVariableKey(typeVariable));
            if (arrobject2 != null) return new TypeResolver((TypeTable)arrobject).resolveType((Type)arrobject2);
            arrobject2 = typeVariable.getBounds();
            if (arrobject2.length == 0) {
                return typeVariable;
            }
            arrobject = new TypeResolver((TypeTable)arrobject).resolveTypes((Type[])arrobject2);
            if (!Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) return Types.newArtificialTypeVariable(typeVariable.getGenericDeclaration(), typeVariable.getName(), (Type[])arrobject);
            if (!Arrays.equals(arrobject2, arrobject)) return Types.newArtificialTypeVariable(typeVariable.getGenericDeclaration(), typeVariable.getName(), (Type[])arrobject);
            return typeVariable;
        }

        final TypeTable where(Map<TypeVariableKey, ? extends Type> object) {
            ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
            builder.putAll(this.map);
            Iterator<Map.Entry<TypeVariableKey, ? extends Type>> iterator2 = object.entrySet().iterator();
            while (iterator2.hasNext()) {
                Object object2 = iterator2.next();
                object = object2.getKey();
                object2 = object2.getValue();
                Preconditions.checkArgument(((TypeVariableKey)object).equalsType((Type)object2) ^ true, "Type variable %s bound to itself", object);
                builder.put(object, object2);
            }
            return new TypeTable(builder.build());
        }

    }

    static final class TypeVariableKey {
        private final TypeVariable<?> var;

        TypeVariableKey(TypeVariable<?> typeVariable) {
            this.var = Preconditions.checkNotNull(typeVariable);
        }

        private boolean equalsTypeVariable(TypeVariable<?> typeVariable) {
            if (!this.var.getGenericDeclaration().equals(typeVariable.getGenericDeclaration())) return false;
            if (!this.var.getName().equals(typeVariable.getName())) return false;
            return true;
        }

        static TypeVariableKey forLookup(Type type) {
            if (!(type instanceof TypeVariable)) return null;
            return new TypeVariableKey((TypeVariable)type);
        }

        public boolean equals(Object object) {
            if (!(object instanceof TypeVariableKey)) return false;
            return this.equalsTypeVariable(((TypeVariableKey)object).var);
        }

        boolean equalsType(Type type) {
            if (!(type instanceof TypeVariable)) return false;
            return this.equalsTypeVariable((TypeVariable)type);
        }

        public int hashCode() {
            return Objects.hashCode(this.var.getGenericDeclaration(), this.var.getName());
        }

        public String toString() {
            return this.var.toString();
        }
    }

    private static class WildcardCapturer {
        static final WildcardCapturer INSTANCE = new WildcardCapturer();
        private final AtomicInteger id;

        private WildcardCapturer() {
            this(new AtomicInteger());
        }

        private WildcardCapturer(AtomicInteger atomicInteger) {
            this.id = atomicInteger;
        }

        private Type captureNullable(@NullableDecl Type type) {
            if (type != null) return this.capture(type);
            return null;
        }

        private WildcardCapturer forTypeVariable(final TypeVariable<?> typeVariable) {
            return new WildcardCapturer(this.id){

                @Override
                TypeVariable<?> captureAsTypeVariable(Type[] object) {
                    object = new LinkedHashSet<Type>(Arrays.asList(object));
                    object.addAll(Arrays.asList(typeVariable.getBounds()));
                    if (object.size() <= 1) return super.captureAsTypeVariable(object.toArray(new Type[0]));
                    object.remove(Object.class);
                    return super.captureAsTypeVariable(object.toArray(new Type[0]));
                }
            };
        }

        private WildcardCapturer notForTypeVariable() {
            return new WildcardCapturer(this.id);
        }

        final Type capture(Type typeVariable) {
            Preconditions.checkNotNull(typeVariable);
            if (typeVariable instanceof Class) {
                return typeVariable;
            }
            if (typeVariable instanceof TypeVariable) {
                return typeVariable;
            }
            if (typeVariable instanceof GenericArrayType) {
                typeVariable = (GenericArrayType)((Object)typeVariable);
                return Types.newArrayType(this.notForTypeVariable().capture(typeVariable.getGenericComponentType()));
            }
            if (!(typeVariable instanceof ParameterizedType)) {
                if (!(typeVariable instanceof WildcardType)) throw new AssertionError((Object)"must have been one of the known types");
                WildcardType wildcardType = (WildcardType)((Object)typeVariable);
                if (wildcardType.getLowerBounds().length != 0) return typeVariable;
                return this.captureAsTypeVariable(wildcardType.getUpperBounds());
            }
            typeVariable = (ParameterizedType)((Object)typeVariable);
            Class class_ = (Class)typeVariable.getRawType();
            TypeVariable<Class<T>>[] arrtypeVariable = class_.getTypeParameters();
            Type[] arrtype = typeVariable.getActualTypeArguments();
            int n = 0;
            while (n < arrtype.length) {
                arrtype[n] = this.forTypeVariable(arrtypeVariable[n]).capture(arrtype[n]);
                ++n;
            }
            return Types.newParameterizedTypeWithOwner(this.notForTypeVariable().captureNullable(typeVariable.getOwnerType()), class_, arrtype);
        }

        TypeVariable<?> captureAsTypeVariable(Type[] arrtype) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("capture#");
            stringBuilder.append(this.id.incrementAndGet());
            stringBuilder.append("-of ? extends ");
            stringBuilder.append(Joiner.on('&').join(arrtype));
            return Types.newArtificialTypeVariable(WildcardCapturer.class, stringBuilder.toString(), arrtype);
        }

    }

}

