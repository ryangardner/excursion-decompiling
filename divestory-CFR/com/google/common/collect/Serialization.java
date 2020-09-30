/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class Serialization {
    private Serialization() {
    }

    static <T> FieldSetter<T> getFieldSetter(Class<T> object, String string2) {
        try {
            return new FieldSetter(((Class)object).getDeclaredField(string2));
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new AssertionError(noSuchFieldException);
        }
    }

    static <K, V> void populateMap(Map<K, V> map, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Serialization.populateMap(map, objectInputStream, objectInputStream.readInt());
    }

    static <K, V> void populateMap(Map<K, V> map, ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        int n2 = 0;
        while (n2 < n) {
            map.put(objectInputStream.readObject(), objectInputStream.readObject());
            ++n2;
        }
    }

    static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Serialization.populateMultimap(multimap, objectInputStream, objectInputStream.readInt());
    }

    static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        int n2 = 0;
        while (n2 < n) {
            Collection<V> collection = multimap.get(objectInputStream.readObject());
            int n3 = objectInputStream.readInt();
            for (int i = 0; i < n3; ++i) {
                collection.add(objectInputStream.readObject());
            }
            ++n2;
        }
    }

    static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Serialization.populateMultiset(multiset, objectInputStream, objectInputStream.readInt());
    }

    static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream objectInputStream, int n) throws IOException, ClassNotFoundException {
        int n2 = 0;
        while (n2 < n) {
            multiset.add(objectInputStream.readObject(), objectInputStream.readInt());
            ++n2;
        }
    }

    static int readCount(ObjectInputStream objectInputStream) throws IOException {
        return objectInputStream.readInt();
    }

    static <K, V> void writeMap(Map<K, V> object, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(object.size());
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    static <K, V> void writeMultimap(Multimap<K, V> object, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(object.asMap().size());
        object = object.asMap().entrySet().iterator();
        block0 : while (object.hasNext()) {
            Object object2 = (Map.Entry)object.next();
            objectOutputStream.writeObject(object2.getKey());
            objectOutputStream.writeInt(((Collection)object2.getValue()).size());
            object2 = ((Collection)object2.getValue()).iterator();
            do {
                if (!object2.hasNext()) continue block0;
                objectOutputStream.writeObject(object2.next());
            } while (true);
            break;
        }
        return;
    }

    static <E> void writeMultiset(Multiset<E> object, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(object.entrySet().size());
        Iterator<Multiset.Entry<E>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            objectOutputStream.writeObject(object.getElement());
            objectOutputStream.writeInt(object.getCount());
        }
    }

    static final class FieldSetter<T> {
        private final Field field;

        private FieldSetter(Field field) {
            this.field = field;
            field.setAccessible(true);
        }

        void set(T t, int n) {
            try {
                this.field.set(t, n);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
        }

        void set(T t, Object object) {
            try {
                this.field.set(t, object);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
        }
    }

}

