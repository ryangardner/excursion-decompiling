/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Preconditions;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
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

    private static Type getActualParameterAtPosition(Type type, Class<?> type2, int n) {
        if ((type2 = Types.getSuperParameterizedType(type, type2)) == null) {
            return null;
        }
        if (!((type2 = type2.getActualTypeArguments()[n]) instanceof TypeVariable)) return type2;
        Type[] arrtype = new Type[]{type};
        type = Types.resolveTypeVariable(Arrays.asList(arrtype), (TypeVariable)type2);
        if (type == null) return type2;
        return type;
    }

    public static Type getArrayComponentType(Type class_) {
        if (!(class_ instanceof GenericArrayType)) return ((Class)class_).getComponentType();
        return ((GenericArrayType)((Object)class_)).getGenericComponentType();
    }

    public static Type getBound(WildcardType wildcardType) {
        Type[] arrtype = wildcardType.getLowerBounds();
        if (arrtype.length == 0) return wildcardType.getUpperBounds()[0];
        return arrtype[0];
    }

    public static Type getIterableParameter(Type type) {
        return Types.getActualParameterAtPosition(type, Iterable.class, 0);
    }

    public static Type getMapValueParameter(Type type) {
        return Types.getActualParameterAtPosition(type, Map.class, 1);
    }

    public static Class<?> getRawArrayComponentType(List<Type> list, Type type) {
        Type type2 = type;
        if (type instanceof TypeVariable) {
            type2 = Types.resolveTypeVariable(list, (TypeVariable)type);
        }
        if (type2 instanceof GenericArrayType) {
            return Array.newInstance(Types.getRawArrayComponentType(list, Types.getArrayComponentType(type2)), 0).getClass();
        }
        if (type2 instanceof Class) {
            return (Class)type2;
        }
        if (type2 instanceof ParameterizedType) {
            return Types.getRawClass((ParameterizedType)type2);
        }
        boolean bl = type2 == null;
        Preconditions.checkArgument(bl, "wildcard type is not supported: %s", type2);
        return Object.class;
    }

    public static Class<?> getRawClass(ParameterizedType parameterizedType) {
        return (Class)parameterizedType.getRawType();
    }

    public static ParameterizedType getSuperParameterizedType(Type class_, Class<?> class_2) {
        Type type = class_;
        if (!(class_ instanceof Class)) {
            if (!(class_ instanceof ParameterizedType)) return null;
            type = class_;
        }
        block0 : while (type != null) {
            if (type == Object.class) return null;
            if (type instanceof Class) {
                class_ = (Class)type;
            } else {
                class_ = (ParameterizedType)type;
                type = Types.getRawClass((ParameterizedType)((Object)class_));
                if (type == class_2) {
                    return class_;
                }
                if (class_2.isInterface()) {
                    for (Type type2 : ((Class)type).getGenericInterfaces()) {
                        class_ = type2 instanceof Class ? (Class<?>)type2 : Types.getRawClass((ParameterizedType)type2);
                        if (!class_2.isAssignableFrom(class_)) continue;
                        type = type2;
                        continue block0;
                    }
                }
                class_ = type;
            }
            type = class_.getGenericSuperclass();
        }
        return null;
    }

    private static IllegalArgumentException handleExceptionForNewInstance(Exception exception, Class<?> object) {
        StringBuilder stringBuilder = new StringBuilder("unable to create new instance of class ");
        stringBuilder.append(((Class)object).getName());
        Object object2 = new ArrayList<String>();
        boolean bl = ((Class)object).isArray();
        boolean bl2 = false;
        if (bl) {
            ((ArrayList)object2).add("because it is an array");
        } else if (((Class)object).isPrimitive()) {
            ((ArrayList)object2).add("because it is primitive");
        } else if (object == Void.class) {
            ((ArrayList)object2).add("because it is void");
        } else {
            if (Modifier.isInterface(((Class)object).getModifiers())) {
                ((ArrayList)object2).add("because it is an interface");
            } else if (Modifier.isAbstract(((Class)object).getModifiers())) {
                ((ArrayList)object2).add("because it is abstract");
            }
            if (((Class)object).getEnclosingClass() != null && !Modifier.isStatic(((Class)object).getModifiers())) {
                ((ArrayList)object2).add("because it is not static");
            }
            if (!Modifier.isPublic(((Class)object).getModifiers())) {
                ((ArrayList)object2).add("possibly because it is not public");
            } else {
                try {
                    ((Class)object).getConstructor(new Class[0]);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    ((ArrayList)object2).add("because it has no accessible default constructor");
                }
            }
        }
        object = ((ArrayList)object2).iterator();
        while (object.hasNext()) {
            object2 = (String)object.next();
            if (bl2) {
                stringBuilder.append(" and");
            } else {
                bl2 = true;
            }
            stringBuilder.append(" ");
            stringBuilder.append((String)object2);
        }
        return new IllegalArgumentException(stringBuilder.toString(), exception);
    }

    public static boolean isArray(Type type) {
        if (type instanceof GenericArrayType) return true;
        if (!(type instanceof Class)) return false;
        if (!((Class)type).isArray()) return false;
        return true;
    }

    public static boolean isAssignableToOrFrom(Class<?> class_, Class<?> class_2) {
        if (class_.isAssignableFrom(class_2)) return true;
        if (class_2.isAssignableFrom(class_)) return true;
        return false;
    }

    public static <T> Iterable<T> iterableOf(final Object object) {
        if (object instanceof Iterable) {
            return (Iterable)object;
        }
        Class<?> class_ = object.getClass();
        Preconditions.checkArgument(class_.isArray(), "not an array or Iterable: %s", class_);
        if (class_.getComponentType().isPrimitive()) return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>(){
                    int index;
                    final int length;
                    {
                        this.length = Array.getLength(object);
                        this.index = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        if (this.index >= this.length) return false;
                        return true;
                    }

                    @Override
                    public T next() {
                        if (!this.hasNext()) throw new NoSuchElementException();
                        Object object = object;
                        int n = this.index;
                        this.index = n + 1;
                        return (T)Array.get(object, n);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

        };
        return Arrays.asList((Object[])object);
    }

    public static <T> T newInstance(Class<T> class_) {
        T t;
        try {
            t = class_.newInstance();
        }
        catch (InstantiationException instantiationException) {
            throw Types.handleExceptionForNewInstance(instantiationException, class_);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw Types.handleExceptionForNewInstance(illegalAccessException, class_);
        }
        return t;
    }

    public static Type resolveTypeVariable(List<Type> object, TypeVariable<?> type) {
        Object object2 = type.getGenericDeclaration();
        if (!(object2 instanceof Class)) return null;
        Class class_ = (Class)object2;
        int n = object.size();
        ParameterizedType parameterizedType = null;
        while (parameterizedType == null && --n >= 0) {
            parameterizedType = Types.getSuperParameterizedType(object.get(n), class_);
        }
        if (parameterizedType == null) return null;
        object2 = object2.getTypeParameters();
        for (n = 0; n < ((?)object2).length && !object2[n].equals(type); ++n) {
        }
        type = parameterizedType.getActualTypeArguments()[n];
        if (!(type instanceof TypeVariable)) return type;
        if ((object = Types.resolveTypeVariable(object, type)) == null) return type;
        return object;
    }

    public static Object toArray(Collection<?> object, Class<?> object2) {
        if (!((Class)object2).isPrimitive()) return object.toArray((Object[])Array.newInstance(object2, object.size()));
        object2 = Array.newInstance(object2, object.size());
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            Array.set(object2, n, object.next());
            ++n;
        }
        return object2;
    }

}

