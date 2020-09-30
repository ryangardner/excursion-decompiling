/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.TransformedIterator;
import com.google.common.primitives.Primitives;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class MutableClassToInstanceMap<B>
extends ForwardingMap<Class<? extends B>, B>
implements ClassToInstanceMap<B>,
Serializable {
    private final Map<Class<? extends B>, B> delegate;

    private MutableClassToInstanceMap(Map<Class<? extends B>, B> map) {
        this.delegate = Preconditions.checkNotNull(map);
    }

    private static <B, T extends B> T cast(Class<T> class_, B b) {
        return Primitives.wrap(class_).cast(b);
    }

    static <B> Map.Entry<Class<? extends B>, B> checkedEntry(final Map.Entry<Class<? extends B>, B> entry) {
        return new ForwardingMapEntry<Class<? extends B>, B>(){

            @Override
            protected Map.Entry<Class<? extends B>, B> delegate() {
                return entry;
            }

            @Override
            public B setValue(B b) {
                return (B)super.setValue(MutableClassToInstanceMap.cast((Class)this.getKey(), b));
            }
        };
    }

    public static <B> MutableClassToInstanceMap<B> create() {
        return new MutableClassToInstanceMap(new HashMap());
    }

    public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> map) {
        return new MutableClassToInstanceMap<B>(map);
    }

    private Object writeReplace() {
        return new SerializedForm(this.delegate());
    }

    @Override
    protected Map<Class<? extends B>, B> delegate() {
        return this.delegate;
    }

    @Override
    public Set<Map.Entry<Class<? extends B>, B>> entrySet() {
        return new ForwardingSet<Map.Entry<Class<? extends B>, B>>(){

            @Override
            protected Set<Map.Entry<Class<? extends B>, B>> delegate() {
                return MutableClassToInstanceMap.this.delegate().entrySet();
            }

            @Override
            public Iterator<Map.Entry<Class<? extends B>, B>> iterator() {
                return new TransformedIterator<Map.Entry<Class<? extends B>, B>, Map.Entry<Class<? extends B>, B>>(this.delegate().iterator()){

                    @Override
                    Map.Entry<Class<? extends B>, B> transform(Map.Entry<Class<? extends B>, B> entry) {
                        return MutableClassToInstanceMap.checkedEntry(entry);
                    }
                };
            }

            @Override
            public Object[] toArray() {
                return this.standardToArray();
            }

            @Override
            public <T> T[] toArray(T[] arrT) {
                return this.standardToArray(arrT);
            }

        };
    }

    @Override
    public <T extends B> T getInstance(Class<T> class_) {
        return MutableClassToInstanceMap.cast(class_, this.get(class_));
    }

    @Override
    public B put(Class<? extends B> class_, B b) {
        return super.put(class_, MutableClassToInstanceMap.cast(class_, b));
    }

    @Override
    public void putAll(Map<? extends Class<? extends B>, ? extends B> object) {
        LinkedHashMap<Class<B>, B> linkedHashMap = new LinkedHashMap<Class<B>, B>((Map<Class<B>, B>)object);
        object = linkedHashMap.entrySet().iterator();
        do {
            if (!object.hasNext()) {
                super.putAll(linkedHashMap);
                return;
            }
            Map.Entry entry = (Map.Entry)object.next();
            MutableClassToInstanceMap.cast((Class)entry.getKey(), entry.getValue());
        } while (true);
    }

    @Override
    public <T extends B> T putInstance(Class<T> class_, T t) {
        return MutableClassToInstanceMap.cast(class_, this.put(class_, t));
    }

    private static final class SerializedForm<B>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Map<Class<? extends B>, B> backingMap;

        SerializedForm(Map<Class<? extends B>, B> map) {
            this.backingMap = map;
        }

        Object readResolve() {
            return MutableClassToInstanceMap.create(this.backingMap);
        }
    }

}

