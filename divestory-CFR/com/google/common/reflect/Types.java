/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeCapture;
import com.google.common.reflect.TypeVisitor;
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
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Types {
    private static final Joiner COMMA_JOINER;
    private static final Function<Type, String> TYPE_NAME;

    static {
        TYPE_NAME = new Function<Type, String>(){

            @Override
            public String apply(Type type) {
                return JavaVersion.CURRENT.typeName(type);
            }
        };
        COMMA_JOINER = Joiner.on(", ").useForNull("null");
    }

    private Types() {
    }

    private static void disallowPrimitiveType(Type[] arrtype, String string2) {
        int n = arrtype.length;
        int n2 = 0;
        while (n2 < n) {
            Type type = arrtype[n2];
            if (type instanceof Class) {
                type = (Class)type;
                Preconditions.checkArgument(((Class)type).isPrimitive() ^ true, "Primitive type '%s' used as %s", (Object)type, (Object)string2);
            }
            ++n2;
        }
    }

    private static Iterable<Type> filterUpperBounds(Iterable<Type> iterable) {
        return Iterables.filter(iterable, Predicates.not(Predicates.equalTo(Object.class)));
    }

    static Class<?> getArrayClass(Class<?> class_) {
        return Array.newInstance(class_, 0).getClass();
    }

    @NullableDecl
    static Type getComponentType(Type type) {
        Preconditions.checkNotNull(type);
        final AtomicReference atomicReference = new AtomicReference();
        new TypeVisitor(){

            @Override
            void visitClass(Class<?> class_) {
                atomicReference.set(class_.getComponentType());
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                atomicReference.set(genericArrayType.getGenericComponentType());
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                atomicReference.set(Types.subtypeOfComponentType(typeVariable.getBounds()));
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                atomicReference.set(Types.subtypeOfComponentType(wildcardType.getUpperBounds()));
            }
        }.visit(type);
        return (Type)atomicReference.get();
    }

    static Type newArrayType(Type arrtype) {
        if (!(arrtype instanceof WildcardType)) return JavaVersion.CURRENT.newArrayType((Type)arrtype);
        WildcardType wildcardType = (WildcardType)arrtype;
        arrtype = wildcardType.getLowerBounds();
        int n = arrtype.length;
        boolean bl = true;
        boolean bl2 = n <= 1;
        Preconditions.checkArgument(bl2, "Wildcard cannot have more than one lower bounds.");
        if (arrtype.length == 1) {
            return Types.supertypeOf(Types.newArrayType(arrtype[0]));
        }
        arrtype = wildcardType.getUpperBounds();
        bl2 = arrtype.length == 1 ? bl : false;
        Preconditions.checkArgument(bl2, "Wildcard should have only one upper bound.");
        return Types.subtypeOf(Types.newArrayType(arrtype[0]));
    }

    static <D extends GenericDeclaration> TypeVariable<D> newArtificialTypeVariable(D d, String string2, Type ... arrtype) {
        Type[] arrtype2 = arrtype;
        if (arrtype.length != 0) return Types.newTypeVariableImpl(d, string2, arrtype2);
        arrtype2 = new Type[]{Object.class};
        return Types.newTypeVariableImpl(d, string2, arrtype2);
    }

    static ParameterizedType newParameterizedType(Class<?> class_, Type ... arrtype) {
        return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(class_), class_, arrtype);
    }

    static ParameterizedType newParameterizedTypeWithOwner(@NullableDecl Type type, Class<?> class_, Type ... arrtype) {
        if (type == null) {
            return Types.newParameterizedType(class_, arrtype);
        }
        Preconditions.checkNotNull(arrtype);
        boolean bl = class_.getEnclosingClass() != null;
        Preconditions.checkArgument(bl, "Owner type for unenclosed %s", class_);
        return new ParameterizedTypeImpl(type, class_, arrtype);
    }

    private static <D extends GenericDeclaration> TypeVariable<D> newTypeVariableImpl(D d, String string2, Type[] arrtype) {
        return Reflection.newProxy(TypeVariable.class, new TypeVariableInvocationHandler(new TypeVariableImpl<D>(d, string2, arrtype)));
    }

    static WildcardType subtypeOf(Type type) {
        return new WildcardTypeImpl(new Type[0], new Type[]{type});
    }

    @NullableDecl
    private static Type subtypeOfComponentType(Type[] object) {
        int n = ((Type[])object).length;
        int n2 = 0;
        while (n2 < n) {
            Type type = Types.getComponentType(object[n2]);
            if (type != null) {
                if (!(type instanceof Class)) return Types.subtypeOf(type);
                object = (Class)type;
                if (!((Class)object).isPrimitive()) return Types.subtypeOf(type);
                return object;
            }
            ++n2;
        }
        return null;
    }

    static WildcardType supertypeOf(Type type) {
        return new WildcardTypeImpl(new Type[]{type}, new Type[]{Object.class});
    }

    private static Type[] toArray(Collection<Type> collection) {
        return collection.toArray(new Type[0]);
    }

    static String toString(Type object) {
        if (!(object instanceof Class)) return object.toString();
        return ((Class)object).getName();
    }

    private static abstract class ClassOwnership
    extends Enum<ClassOwnership> {
        private static final /* synthetic */ ClassOwnership[] $VALUES;
        static final ClassOwnership JVM_BEHAVIOR;
        public static final /* enum */ ClassOwnership LOCAL_CLASS_HAS_NO_OWNER;
        public static final /* enum */ ClassOwnership OWNED_BY_ENCLOSING_CLASS;

        static {
            ClassOwnership classOwnership;
            OWNED_BY_ENCLOSING_CLASS = new ClassOwnership(){

                @NullableDecl
                @Override
                Class<?> getOwnerType(Class<?> class_) {
                    return class_.getEnclosingClass();
                }
            };
            LOCAL_CLASS_HAS_NO_OWNER = classOwnership = new ClassOwnership(){

                @NullableDecl
                @Override
                Class<?> getOwnerType(Class<?> class_) {
                    if (!class_.isLocalClass()) return class_.getEnclosingClass();
                    return null;
                }
            };
            $VALUES = new ClassOwnership[]{OWNED_BY_ENCLOSING_CLASS, classOwnership};
            JVM_BEHAVIOR = ClassOwnership.detectJvmBehavior();
        }

        private static ClassOwnership detectJvmBehavior() {
            ParameterizedType parameterizedType = (ParameterizedType)new 1LocalClass<String>(){}.getClass().getGenericSuperclass();
            ClassOwnership[] arrclassOwnership = ClassOwnership.values();
            int n = arrclassOwnership.length;
            int n2 = 0;
            while (n2 < n) {
                ClassOwnership classOwnership = arrclassOwnership[n2];
                if (classOwnership.getOwnerType(1LocalClass.class) == parameterizedType.getOwnerType()) {
                    return classOwnership;
                }
                ++n2;
            }
            throw new AssertionError();
        }

        public static ClassOwnership valueOf(String string2) {
            return Enum.valueOf(ClassOwnership.class, string2);
        }

        public static ClassOwnership[] values() {
            return (ClassOwnership[])$VALUES.clone();
        }

        @NullableDecl
        abstract Class<?> getOwnerType(Class<?> var1);

        class 1LocalClass<T> {
            1LocalClass() {
            }
        }

    }

    private static final class GenericArrayTypeImpl
    implements GenericArrayType,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Type componentType;

        GenericArrayTypeImpl(Type type) {
            this.componentType = JavaVersion.CURRENT.usedInGenericType(type);
        }

        public boolean equals(Object object) {
            if (!(object instanceof GenericArrayType)) return false;
            object = (GenericArrayType)object;
            return Objects.equal(this.getGenericComponentType(), object.getGenericComponentType());
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Types.toString(this.componentType));
            stringBuilder.append("[]");
            return stringBuilder.toString();
        }
    }

    static abstract class JavaVersion
    extends Enum<JavaVersion> {
        private static final /* synthetic */ JavaVersion[] $VALUES;
        static final JavaVersion CURRENT;
        public static final /* enum */ JavaVersion JAVA6;
        public static final /* enum */ JavaVersion JAVA7;
        public static final /* enum */ JavaVersion JAVA8;
        public static final /* enum */ JavaVersion JAVA9;

        static {
            JavaVersion javaVersion;
            JAVA6 = new JavaVersion(){

                @Override
                GenericArrayType newArrayType(Type type) {
                    return new GenericArrayTypeImpl(type);
                }

                @Override
                Type usedInGenericType(Type type) {
                    Preconditions.checkNotNull(type);
                    Type type2 = type;
                    if (!(type instanceof Class)) return type2;
                    Class class_ = (Class)type;
                    type2 = type;
                    if (!class_.isArray()) return type2;
                    return new GenericArrayTypeImpl(class_.getComponentType());
                }
            };
            JAVA7 = new JavaVersion(){

                @Override
                Type newArrayType(Type type) {
                    if (!(type instanceof Class)) return new GenericArrayTypeImpl(type);
                    return Types.getArrayClass((Class)type);
                }

                @Override
                Type usedInGenericType(Type type) {
                    return Preconditions.checkNotNull(type);
                }
            };
            JAVA8 = new JavaVersion(){

                @Override
                Type newArrayType(Type type) {
                    return JAVA7.newArrayType(type);
                }

                @Override
                String typeName(Type object) {
                    try {
                        return (String)Type.class.getMethod("getTypeName", new Class[0]).invoke(object, new Object[0]);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        throw new RuntimeException(illegalAccessException);
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        throw new RuntimeException(invocationTargetException);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        throw new AssertionError((Object)"Type.getTypeName should be available in Java 8");
                    }
                }

                @Override
                Type usedInGenericType(Type type) {
                    return JAVA7.usedInGenericType(type);
                }
            };
            JAVA9 = javaVersion = new JavaVersion(){

                @Override
                boolean jdkTypeDuplicatesOwnerName() {
                    return false;
                }

                @Override
                Type newArrayType(Type type) {
                    return JAVA8.newArrayType(type);
                }

                @Override
                String typeName(Type type) {
                    return JAVA8.typeName(type);
                }

                @Override
                Type usedInGenericType(Type type) {
                    return JAVA8.usedInGenericType(type);
                }
            };
            $VALUES = new JavaVersion[]{JAVA6, JAVA7, JAVA8, javaVersion};
            if (AnnotatedElement.class.isAssignableFrom(TypeVariable.class)) {
                if (new TypeCapture<Map.Entry<String, int[][]>>(){}.capture().toString().contains("java.util.Map.java.util.Map")) {
                    CURRENT = JAVA8;
                    return;
                }
                CURRENT = JAVA9;
                return;
            }
            if (new TypeCapture<int[]>(){}.capture() instanceof Class) {
                CURRENT = JAVA7;
                return;
            }
            CURRENT = JAVA6;
        }

        public static JavaVersion valueOf(String string2) {
            return Enum.valueOf(JavaVersion.class, string2);
        }

        public static JavaVersion[] values() {
            return (JavaVersion[])$VALUES.clone();
        }

        boolean jdkTypeDuplicatesOwnerName() {
            return true;
        }

        abstract Type newArrayType(Type var1);

        String typeName(Type type) {
            return Types.toString(type);
        }

        final ImmutableList<Type> usedInGenericType(Type[] arrtype) {
            ImmutableList.Builder<E> builder = ImmutableList.builder();
            int n = arrtype.length;
            int n2 = 0;
            while (n2 < n) {
                builder.add(this.usedInGenericType(arrtype[n2]));
                ++n2;
            }
            return builder.build();
        }

        abstract Type usedInGenericType(Type var1);

    }

    static final class NativeTypeVariableEquals<X> {
        static final boolean NATIVE_TYPE_VARIABLE_ONLY = NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X", new Type[0])) ^ true;

        NativeTypeVariableEquals() {
        }
    }

    private static final class ParameterizedTypeImpl
    implements ParameterizedType,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final ImmutableList<Type> argumentsList;
        @NullableDecl
        private final Type ownerType;
        private final Class<?> rawType;

        ParameterizedTypeImpl(@NullableDecl Type type, Class<?> class_, Type[] arrtype) {
            Preconditions.checkNotNull(class_);
            boolean bl = arrtype.length == class_.getTypeParameters().length;
            Preconditions.checkArgument(bl);
            Types.disallowPrimitiveType(arrtype, "type parameter");
            this.ownerType = type;
            this.rawType = class_;
            this.argumentsList = JavaVersion.CURRENT.usedInGenericType(arrtype);
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ParameterizedType;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (ParameterizedType)object;
            bl = bl2;
            if (!this.getRawType().equals(object.getRawType())) return bl;
            bl = bl2;
            if (!Objects.equal(this.getOwnerType(), object.getOwnerType())) return bl;
            bl = bl2;
            if (!Arrays.equals(this.getActualTypeArguments(), object.getActualTypeArguments())) return bl;
            return true;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return Types.toArray(this.argumentsList);
        }

        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        public int hashCode() {
            int n;
            Type type = this.ownerType;
            if (type == null) {
                n = 0;
                return n ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
            }
            n = type.hashCode();
            return n ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.ownerType != null && JavaVersion.CURRENT.jdkTypeDuplicatesOwnerName()) {
                stringBuilder.append(JavaVersion.CURRENT.typeName(this.ownerType));
                stringBuilder.append('.');
            }
            stringBuilder.append(this.rawType.getName());
            stringBuilder.append('<');
            stringBuilder.append(COMMA_JOINER.join(Iterables.transform(this.argumentsList, TYPE_NAME)));
            stringBuilder.append('>');
            return stringBuilder.toString();
        }
    }

    private static final class TypeVariableImpl<D extends GenericDeclaration> {
        private final ImmutableList<Type> bounds;
        private final D genericDeclaration;
        private final String name;

        TypeVariableImpl(D d, String string2, Type[] arrtype) {
            Types.disallowPrimitiveType(arrtype, "bound for type variable");
            this.genericDeclaration = (GenericDeclaration)Preconditions.checkNotNull(d);
            this.name = Preconditions.checkNotNull(string2);
            this.bounds = ImmutableList.copyOf(arrtype);
        }

        public boolean equals(Object object) {
            boolean bl = NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY;
            boolean bl2 = true;
            boolean bl3 = true;
            if (bl) {
                if (object == null) return false;
                if (!Proxy.isProxyClass(object.getClass())) return false;
                if (!(Proxy.getInvocationHandler(object) instanceof TypeVariableInvocationHandler)) return false;
                if (!this.name.equals(((TypeVariableImpl)(object = ((TypeVariableInvocationHandler)Proxy.getInvocationHandler(object)).typeVariableImpl)).getName())) return false;
                if (!this.genericDeclaration.equals(((TypeVariableImpl)object).getGenericDeclaration())) return false;
                if (!this.bounds.equals(((TypeVariableImpl)object).bounds)) return false;
                return bl3;
            }
            if (!(object instanceof TypeVariable)) return false;
            if (!this.name.equals((object = (TypeVariable)object).getName())) return false;
            if (!this.genericDeclaration.equals(object.getGenericDeclaration())) return false;
            return bl2;
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

    private static final class TypeVariableInvocationHandler
    implements InvocationHandler {
        private static final ImmutableMap<String, Method> typeVariableMethods;
        private final TypeVariableImpl<?> typeVariableImpl;

        static {
            ImmutableMap.Builder<String, Method> builder = ImmutableMap.builder();
            Method[] arrmethod = TypeVariableImpl.class.getMethods();
            int n = arrmethod.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    typeVariableMethods = builder.build();
                    return;
                }
                Method method = arrmethod[n2];
                if (method.getDeclaringClass().equals(TypeVariableImpl.class)) {
                    try {
                        method.setAccessible(true);
                    }
                    catch (AccessControlException accessControlException) {}
                    builder.put(method.getName(), method);
                }
                ++n2;
            } while (true);
        }

        TypeVariableInvocationHandler(TypeVariableImpl<?> typeVariableImpl) {
            this.typeVariableImpl = typeVariableImpl;
        }

        @Override
        public Object invoke(Object object, Method method, Object[] arrobject) throws Throwable {
            object = method.getName();
            method = typeVariableMethods.get(object);
            if (method == null) throw new UnsupportedOperationException((String)object);
            try {
                return method.invoke(this.typeVariableImpl, arrobject);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw invocationTargetException.getCause();
            }
        }
    }

    static final class WildcardTypeImpl
    implements WildcardType,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final ImmutableList<Type> lowerBounds;
        private final ImmutableList<Type> upperBounds;

        WildcardTypeImpl(Type[] arrtype, Type[] arrtype2) {
            Types.disallowPrimitiveType(arrtype, "lower bound for wildcard");
            Types.disallowPrimitiveType(arrtype2, "upper bound for wildcard");
            this.lowerBounds = JavaVersion.CURRENT.usedInGenericType(arrtype);
            this.upperBounds = JavaVersion.CURRENT.usedInGenericType(arrtype2);
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = object instanceof WildcardType;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (WildcardType)object;
            bl3 = bl;
            if (!this.lowerBounds.equals(Arrays.asList(object.getLowerBounds()))) return bl3;
            bl3 = bl;
            if (!this.upperBounds.equals(Arrays.asList(object.getUpperBounds()))) return bl3;
            return true;
        }

        @Override
        public Type[] getLowerBounds() {
            return Types.toArray(this.lowerBounds);
        }

        @Override
        public Type[] getUpperBounds() {
            return Types.toArray(this.upperBounds);
        }

        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("?");
            for (Type object2 : this.lowerBounds) {
                stringBuilder.append(" super ");
                stringBuilder.append(JavaVersion.CURRENT.typeName(object2));
            }
            Iterator iterator2 = Types.filterUpperBounds(this.upperBounds).iterator();
            while (iterator2.hasNext()) {
                Type type = (Type)iterator2.next();
                stringBuilder.append(" extends ");
                stringBuilder.append(JavaVersion.CURRENT.typeName(type));
            }
            return stringBuilder.toString();
        }
    }

}

