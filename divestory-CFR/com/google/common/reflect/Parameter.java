/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Parameter
implements AnnotatedElement {
    private final ImmutableList<Annotation> annotations;
    private final Invokable<?, ?> declaration;
    private final int position;
    private final TypeToken<?> type;

    Parameter(Invokable<?, ?> invokable, int n, TypeToken<?> typeToken, Annotation[] arrannotation) {
        this.declaration = invokable;
        this.position = n;
        this.type = typeToken;
        this.annotations = ImmutableList.copyOf(arrannotation);
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Parameter;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Parameter)object;
        bl3 = bl;
        if (this.position != ((Parameter)object).position) return bl3;
        bl3 = bl;
        if (!this.declaration.equals(((Parameter)object).declaration)) return bl3;
        return true;
    }

    @NullableDecl
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        Annotation annotation;
        Preconditions.checkNotNull(class_);
        Iterator iterator2 = this.annotations.iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!class_.isInstance(annotation = (Annotation)iterator2.next()));
        return (A)((Annotation)class_.cast(annotation));
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> class_) {
        return this.getDeclaredAnnotationsByType(class_);
    }

    @NullableDecl
    public <A extends Annotation> A getDeclaredAnnotation(Class<A> class_) {
        Preconditions.checkNotNull(class_);
        return (A)((Annotation)FluentIterable.from(this.annotations).filter(class_).first().orNull());
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.annotations.toArray(new Annotation[0]);
    }

    public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> class_) {
        return (Annotation[])FluentIterable.from(this.annotations).filter(class_).toArray(class_);
    }

    public Invokable<?, ?> getDeclaringInvokable() {
        return this.declaration;
    }

    public TypeToken<?> getType() {
        return this.type;
    }

    public int hashCode() {
        return this.position;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        if (this.getAnnotation(class_) == null) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.type);
        stringBuilder.append(" arg");
        stringBuilder.append(this.position);
        return stringBuilder.toString();
    }
}

