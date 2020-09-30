/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.collect.Sets;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Set;

abstract class TypeVisitor {
    private final Set<Type> visited = Sets.newHashSet();

    TypeVisitor() {
    }

    public final void visit(Type ... object) {
        int n = ((Type[])object).length;
        int n2 = 0;
        while (n2 < n) {
            Type type = object[n2];
            if (type != null && this.visited.add(type)) {
                try {
                    if (type instanceof TypeVariable) {
                        this.visitTypeVariable((TypeVariable)type);
                    } else if (type instanceof WildcardType) {
                        this.visitWildcardType((WildcardType)type);
                    } else if (type instanceof ParameterizedType) {
                        this.visitParameterizedType((ParameterizedType)type);
                    } else if (type instanceof Class) {
                        this.visitClass((Class)type);
                    } else {
                        if (!(type instanceof GenericArrayType)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown type: ");
                            ((StringBuilder)object).append(type);
                            AssertionError assertionError = new AssertionError((Object)((StringBuilder)object).toString());
                            throw assertionError;
                        }
                        this.visitGenericArrayType((GenericArrayType)type);
                    }
                }
                catch (Throwable throwable) {
                    this.visited.remove(type);
                    throw throwable;
                }
            }
            ++n2;
        }
    }

    void visitClass(Class<?> class_) {
    }

    void visitGenericArrayType(GenericArrayType genericArrayType) {
    }

    void visitParameterizedType(ParameterizedType parameterizedType) {
    }

    void visitTypeVariable(TypeVariable<?> typeVariable) {
    }

    void visitWildcardType(WildcardType wildcardType) {
    }
}

