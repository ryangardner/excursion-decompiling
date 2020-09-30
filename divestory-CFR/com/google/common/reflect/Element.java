/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class Element
extends AccessibleObject
implements Member {
    private final AccessibleObject accessibleObject;
    private final Member member;

    <M extends AccessibleObject> Element(M m) {
        Preconditions.checkNotNull(m);
        this.accessibleObject = m;
        this.member = (Member)m;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Element;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Element)object;
        bl3 = bl;
        if (!this.getOwnerType().equals(((Element)object).getOwnerType())) return bl3;
        bl3 = bl;
        if (!this.member.equals(((Element)object).member)) return bl3;
        return true;
    }

    public final <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this.accessibleObject.getAnnotation(class_);
    }

    @Override
    public final Annotation[] getAnnotations() {
        return this.accessibleObject.getAnnotations();
    }

    @Override
    public final Annotation[] getDeclaredAnnotations() {
        return this.accessibleObject.getDeclaredAnnotations();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.member.getDeclaringClass();
    }

    @Override
    public final int getModifiers() {
        return this.member.getModifiers();
    }

    @Override
    public final String getName() {
        return this.member.getName();
    }

    public TypeToken<?> getOwnerType() {
        return TypeToken.of(this.getDeclaringClass());
    }

    public int hashCode() {
        return this.member.hashCode();
    }

    public final boolean isAbstract() {
        return Modifier.isAbstract(this.getModifiers());
    }

    @Override
    public final boolean isAccessible() {
        return this.accessibleObject.isAccessible();
    }

    @Override
    public final boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        return this.accessibleObject.isAnnotationPresent(class_);
    }

    public final boolean isFinal() {
        return Modifier.isFinal(this.getModifiers());
    }

    public final boolean isNative() {
        return Modifier.isNative(this.getModifiers());
    }

    public final boolean isPackagePrivate() {
        if (this.isPrivate()) return false;
        if (this.isPublic()) return false;
        if (this.isProtected()) return false;
        return true;
    }

    public final boolean isPrivate() {
        return Modifier.isPrivate(this.getModifiers());
    }

    public final boolean isProtected() {
        return Modifier.isProtected(this.getModifiers());
    }

    public final boolean isPublic() {
        return Modifier.isPublic(this.getModifiers());
    }

    public final boolean isStatic() {
        return Modifier.isStatic(this.getModifiers());
    }

    public final boolean isSynchronized() {
        return Modifier.isSynchronized(this.getModifiers());
    }

    @Override
    public final boolean isSynthetic() {
        return this.member.isSynthetic();
    }

    final boolean isTransient() {
        return Modifier.isTransient(this.getModifiers());
    }

    final boolean isVolatile() {
        return Modifier.isVolatile(this.getModifiers());
    }

    @Override
    public final void setAccessible(boolean bl) throws SecurityException {
        this.accessibleObject.setAccessible(bl);
    }

    public String toString() {
        return this.member.toString();
    }
}

