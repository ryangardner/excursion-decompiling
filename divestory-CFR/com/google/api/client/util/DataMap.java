/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

final class DataMap
extends AbstractMap<String, Object> {
    final ClassInfo classInfo;
    final Object object;

    DataMap(Object object, boolean bl) {
        this.object = object;
        this.classInfo = ClassInfo.of(object.getClass(), bl);
    }

    @Override
    public boolean containsKey(Object object) {
        if (this.get(object) == null) return false;
        return true;
    }

    public EntrySet entrySet() {
        return new EntrySet();
    }

    @Override
    public Object get(Object object) {
        if (!(object instanceof String)) {
            return null;
        }
        if ((object = this.classInfo.getFieldInfo((String)object)) != null) return ((FieldInfo)object).getValue(this.object);
        return null;
    }

    @Override
    public Object put(String object, Object object2) {
        FieldInfo fieldInfo = this.classInfo.getFieldInfo((String)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("no field of key ");
        stringBuilder.append((String)object);
        Preconditions.checkNotNull(fieldInfo, stringBuilder.toString());
        object = fieldInfo.getValue(this.object);
        fieldInfo.setValue(this.object, Preconditions.checkNotNull(object2));
        return object;
    }

    final class Entry
    implements Map.Entry<String, Object> {
        private final FieldInfo fieldInfo;
        private Object fieldValue;

        Entry(FieldInfo fieldInfo, Object object) {
            this.fieldInfo = fieldInfo;
            this.fieldValue = Preconditions.checkNotNull(object);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            if (!((String)this.getKey()).equals(object.getKey())) return false;
            if (!this.getValue().equals(object.getValue())) return false;
            return bl;
        }

        @Override
        public String getKey() {
            String string2;
            String string3 = string2 = this.fieldInfo.getName();
            if (!DataMap.this.classInfo.getIgnoreCase()) return string3;
            return string2.toLowerCase(Locale.US);
        }

        @Override
        public Object getValue() {
            return this.fieldValue;
        }

        @Override
        public int hashCode() {
            return ((String)this.getKey()).hashCode() ^ this.getValue().hashCode();
        }

        @Override
        public Object setValue(Object object) {
            Object object2 = this.fieldValue;
            this.fieldValue = Preconditions.checkNotNull(object);
            this.fieldInfo.setValue(DataMap.this.object, object);
            return object2;
        }
    }

    final class EntryIterator
    implements Iterator<Map.Entry<String, Object>> {
        private FieldInfo currentFieldInfo;
        private boolean isComputed;
        private boolean isRemoved;
        private FieldInfo nextFieldInfo;
        private Object nextFieldValue;
        private int nextKeyIndex = -1;

        EntryIterator() {
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.isComputed;
            boolean bl2 = true;
            if (!bl) {
                this.isComputed = true;
                this.nextFieldValue = null;
                while (this.nextFieldValue == null) {
                    int n;
                    FieldInfo fieldInfo;
                    this.nextKeyIndex = n = this.nextKeyIndex + 1;
                    if (n >= DataMap.this.classInfo.names.size()) break;
                    this.nextFieldInfo = fieldInfo = DataMap.this.classInfo.getFieldInfo(DataMap.this.classInfo.names.get(this.nextKeyIndex));
                    this.nextFieldValue = fieldInfo.getValue(DataMap.this.object);
                }
            }
            if (this.nextFieldValue == null) return false;
            return bl2;
        }

        @Override
        public Map.Entry<String, Object> next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            this.currentFieldInfo = this.nextFieldInfo;
            Object object = this.nextFieldValue;
            this.isComputed = false;
            this.isRemoved = false;
            this.nextFieldInfo = null;
            this.nextFieldValue = null;
            return new Entry(this.currentFieldInfo, object);
        }

        @Override
        public void remove() {
            boolean bl = this.currentFieldInfo != null && !this.isRemoved;
            Preconditions.checkState(bl);
            this.isRemoved = true;
            this.currentFieldInfo.setValue(DataMap.this.object, null);
        }
    }

    final class EntrySet
    extends AbstractSet<Map.Entry<String, Object>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            Iterator<String> iterator2 = DataMap.this.classInfo.names.iterator();
            while (iterator2.hasNext()) {
                String string2 = iterator2.next();
                DataMap.this.classInfo.getFieldInfo(string2).setValue(DataMap.this.object, null);
            }
        }

        @Override
        public boolean isEmpty() {
            String string2;
            Iterator<String> iterator2 = DataMap.this.classInfo.names.iterator();
            do {
                if (!iterator2.hasNext()) return true;
            } while (DataMap.this.classInfo.getFieldInfo(string2 = iterator2.next()).getValue(DataMap.this.object) == null);
            return false;
        }

        public EntryIterator iterator() {
            return new EntryIterator();
        }

        @Override
        public int size() {
            Iterator<String> iterator2 = DataMap.this.classInfo.names.iterator();
            int n = 0;
            while (iterator2.hasNext()) {
                String string2 = iterator2.next();
                if (DataMap.this.classInfo.getFieldInfo(string2).getValue(DataMap.this.object) == null) continue;
                ++n;
            }
            return n;
        }
    }

}

