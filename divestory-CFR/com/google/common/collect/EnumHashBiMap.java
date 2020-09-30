/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractBiMap;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class EnumHashBiMap<K extends Enum<K>, V>
extends AbstractBiMap<K, V> {
    private static final long serialVersionUID = 0L;
    private transient Class<K> keyType;

    private EnumHashBiMap(Class<K> class_) {
        super(new EnumMap(class_), Maps.newHashMapWithExpectedSize(((Enum[])class_.getEnumConstants()).length));
        this.keyType = class_;
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> class_) {
        return new EnumHashBiMap<K, V>(class_);
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
        EnumHashBiMap<K, V> enumHashBiMap = EnumHashBiMap.create(EnumBiMap.inferKeyType(map));
        enumHashBiMap.putAll(map);
        return enumHashBiMap;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyType = (Class)objectInputStream.readObject();
        this.setDelegates(new EnumMap(this.keyType), new HashMap(((Enum[])this.keyType.getEnumConstants()).length * 3 / 2));
        Serialization.populateMap(this, objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.keyType);
        Serialization.writeMap(this, objectOutputStream);
    }

    @Override
    K checkKey(K k) {
        return (K)((Enum)Preconditions.checkNotNull(k));
    }

    @Override
    public V forcePut(K k, @NullableDecl V v) {
        return super.forcePut(k, v);
    }

    public Class<K> keyType() {
        return this.keyType;
    }

    @Override
    public V put(K k, @NullableDecl V v) {
        return super.put(k, v);
    }
}

