/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Element;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import com.google.common.reflect.Types;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Invokable<T, R>
extends Element
implements GenericDeclaration {
    <M extends AccessibleObject> Invokable(M m) {
        super(m);
    }

    public static <T> Invokable<T, T> from(Constructor<T> constructor) {
        return new ConstructorInvokable(constructor);
    }

    public static Invokable<?, Object> from(Method method) {
        return new MethodInvokable(method);
    }

    public final Class<? super T> getDeclaringClass() {
        return super.getDeclaringClass();
    }

    public final ImmutableList<TypeToken<? extends Throwable>> getExceptionTypes() {
        ImmutableList.Builder builder = ImmutableList.builder();
        Type[] arrtype = this.getGenericExceptionTypes();
        int n = arrtype.length;
        int n2 = 0;
        while (n2 < n) {
            builder.add(TypeToken.of(arrtype[n2]));
            ++n2;
        }
        return builder.build();
    }

    abstract Type[] getGenericExceptionTypes();

    abstract Type[] getGenericParameterTypes();

    abstract Type getGenericReturnType();

    public TypeToken<T> getOwnerType() {
        return TypeToken.of(this.getDeclaringClass());
    }

    abstract Annotation[][] getParameterAnnotations();

    public final ImmutableList<Parameter> getParameters() {
        Type[] arrtype = this.getGenericParameterTypes();
        Annotation[][] arrannotation = this.getParameterAnnotations();
        ImmutableList.Builder<E> builder = ImmutableList.builder();
        int n = 0;
        while (n < arrtype.length) {
            builder.add(new Parameter(this, n, TypeToken.of(arrtype[n]), arrannotation[n]));
            ++n;
        }
        return builder.build();
    }

    public final TypeToken<? extends R> getReturnType() {
        return TypeToken.of(this.getGenericReturnType());
    }

    public final R invoke(@NullableDecl T t, Object ... arrobject) throws InvocationTargetException, IllegalAccessException {
        return (R)this.invokeInternal(t, Preconditions.checkNotNull(arrobject));
    }

    abstract Object invokeInternal(@NullableDecl Object var1, Object[] var2) throws InvocationTargetException, IllegalAccessException;

    public abstract boolean isOverridable();

    public abstract boolean isVarArgs();

    public final <R1 extends R> Invokable<T, R1> returning(TypeToken<R1> typeToken) {
        if (typeToken.isSupertypeOf(this.getReturnType())) {
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invokable is known to return ");
        stringBuilder.append(this.getReturnType());
        stringBuilder.append(", not ");
        stringBuilder.append(typeToken);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final <R1 extends R> Invokable<T, R1> returning(Class<R1> class_) {
        return this.returning(TypeToken.of(class_));
    }

    static class ConstructorInvokable<T>
    extends Invokable<T, T> {
        final Constructor<?> constructor;

        ConstructorInvokable(Constructor<?> constructor) {
            super(constructor);
            this.constructor = constructor;
        }

        private boolean mayNeedHiddenThis() {
            Class<?> class_ = this.constructor.getDeclaringClass();
            Executable executable = class_.getEnclosingConstructor();
            boolean bl = true;
            if (executable != null) {
                return true;
            }
            executable = class_.getEnclosingMethod();
            if (executable != null) {
                return Modifier.isStatic(((Method)executable).getModifiers()) ^ true;
            }
            if (class_.getEnclosingClass() == null) return false;
            if (Modifier.isStatic(class_.getModifiers())) return false;
            return bl;
        }

        @Override
        Type[] getGenericExceptionTypes() {
            return this.constructor.getGenericExceptionTypes();
        }

        @Override
        Type[] getGenericParameterTypes() {
            Type[] arrtype;
            Type[] arrtype2 = arrtype = this.constructor.getGenericParameterTypes();
            if (arrtype.length <= 0) return arrtype2;
            arrtype2 = arrtype;
            if (!this.mayNeedHiddenThis()) return arrtype2;
            Class<?>[] arrclass = this.constructor.getParameterTypes();
            arrtype2 = arrtype;
            if (arrtype.length != arrclass.length) return arrtype2;
            arrtype2 = arrtype;
            if (arrclass[0] != this.getDeclaringClass().getEnclosingClass()) return arrtype2;
            return Arrays.copyOfRange(arrtype, 1, arrtype.length);
        }

        @Override
        Type getGenericReturnType() {
            Class class_ = this.getDeclaringClass();
            Type[] arrtype = class_.getTypeParameters();
            Type type = class_;
            if (arrtype.length <= 0) return type;
            return Types.newParameterizedType(class_, arrtype);
        }

        @Override
        final Annotation[][] getParameterAnnotations() {
            return this.constructor.getParameterAnnotations();
        }

        @Override
        public final TypeVariable<?>[] getTypeParameters() {
            TypeVariable<Class<T>>[] arrtypeVariable = this.getDeclaringClass().getTypeParameters();
            TypeVariable<Constructor<?>>[] arrtypeVariable2 = this.constructor.getTypeParameters();
            TypeVariable[] arrtypeVariable3 = new TypeVariable[arrtypeVariable.length + arrtypeVariable2.length];
            System.arraycopy(arrtypeVariable, 0, arrtypeVariable3, 0, arrtypeVariable.length);
            System.arraycopy(arrtypeVariable2, 0, arrtypeVariable3, arrtypeVariable.length, arrtypeVariable2.length);
            return arrtypeVariable3;
        }

        @Override
        final Object invokeInternal(@NullableDecl Object object, Object[] object2) throws InvocationTargetException, IllegalAccessException {
            try {
                return this.constructor.newInstance((Object[])object2);
            }
            catch (InstantiationException instantiationException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(this.constructor);
                ((StringBuilder)object2).append(" failed.");
                throw new RuntimeException(((StringBuilder)object2).toString(), instantiationException);
            }
        }

        @Override
        public final boolean isOverridable() {
            return false;
        }

        @Override
        public final boolean isVarArgs() {
            return this.constructor.isVarArgs();
        }
    }

    static class MethodInvokable<T>
    extends Invokable<T, Object> {
        final Method method;

        MethodInvokable(Method method) {
            super(method);
            this.method = method;
        }

        @Override
        Type[] getGenericExceptionTypes() {
            return this.method.getGenericExceptionTypes();
        }

        @Override
        Type[] getGenericParameterTypes() {
            return this.method.getGenericParameterTypes();
        }

        @Override
        Type getGenericReturnType() {
            return this.method.getGenericReturnType();
        }

        @Override
        final Annotation[][] getParameterAnnotations() {
            return this.method.getParameterAnnotations();
        }

        @Override
        public final TypeVariable<?>[] getTypeParameters() {
            return this.method.getTypeParameters();
        }

        @Override
        final Object invokeInternal(@NullableDecl Object object, Object[] arrobject) throws InvocationTargetException, IllegalAccessException {
            return this.method.invoke(object, arrobject);
        }

        @Override
        public final boolean isOverridable() {
            if (this.isFinal()) return false;
            if (this.isPrivate()) return false;
            if (this.isStatic()) return false;
            if (Modifier.isFinal(this.getDeclaringClass().getModifiers())) return false;
            return true;
        }

        @Override
        public final boolean isVarArgs() {
            return this.method.isVarArgs();
        }
    }

}

