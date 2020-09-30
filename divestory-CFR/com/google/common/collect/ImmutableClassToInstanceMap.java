/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf={"B"})
public final class ImmutableClassToInstanceMap<B>
extends ForwardingMap<Class<? extends B>, B>
implements ClassToInstanceMap<B>,
Serializable {
    private static final ImmutableClassToInstanceMap<Object> EMPTY = new ImmutableClassToInstanceMap(ImmutableMap.of());
    private final ImmutableMap<Class<? extends B>, B> delegate;

    private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> immutableMap) {
        this.delegate = immutableMap;
    }

    public static <B> Builder<B> builder() {
        return new Builder();
    }

    public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
        if (!(map instanceof ImmutableClassToInstanceMap)) return new Builder().putAll(map).build();
        return (ImmutableClassToInstanceMap)map;
    }

    public static <B> ImmutableClassToInstanceMap<B> of() {
        return EMPTY;
    }

    public static <B, T extends B> ImmutableClassToInstanceMap<B> of(Class<T> class_, T t) {
        return new ImmutableClassToInstanceMap<T>(ImmutableMap.of(class_, t));
    }

    @Override
    protected Map<Class<? extends B>, B> delegate() {
        return this.delegate;
    }

    @NullableDecl
    @Override
    public <T extends B> T getInstance(Class<T> class_) {
        return (T)this.delegate.get(Preconditions.checkNotNull(class_));
    }

    @Deprecated
    @Override
    public <T extends B> T putInstance(Class<T> class_, T t) {
        throw new UnsupportedOperationException();
    }

    Object readResolve() {
        if (!this.isEmpty()) return this;
        return ImmutableClassToInstanceMap.of();
    }

    public static final class Builder<B> {
        private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();

        private static <B, T extends B> T cast(Class<T> class_, B b) {
            return Primitives.wrap(class_).cast(b);
        }

        public ImmutableClassToInstanceMap<B> build() {
            ImmutableMap<Class<B>, B> immutableMap = this.mapBuilder.build();
            if (!immutableMap.isEmpty()) return new ImmutableClassToInstanceMap(immutableMap);
            return ImmutableClassToInstanceMap.of();
        }

        public <T extends B> Builder<B> put(Class<T> class_, T t) {
            this.mapBuilder.put(class_, t);
            return this;
        }

        public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> object) {
            Iterator<Map.Entry<Class<T>, T>> iterator2 = object.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<Class<Object>, Object> entry = iterator2.next();
                object = entry.getKey();
                entry = entry.getValue();
                this.mapBuilder.put((Class<B>)object, (B)Builder.cast(object, entry));
            }
            return this;
        }
    }

}

