/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.DataMap;
import com.google.api.client.util.FieldInfo;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GenericData
extends AbstractMap<String, Object>
implements Cloneable {
    final ClassInfo classInfo;
    Map<String, Object> unknownFields = ArrayMap.create();

    public GenericData() {
        this(EnumSet.noneOf(Flags.class));
    }

    public GenericData(EnumSet<Flags> enumSet) {
        this.classInfo = ClassInfo.of(this.getClass(), enumSet.contains((Object)Flags.IGNORE_CASE));
    }

    @Override
    public GenericData clone() {
        try {
            GenericData genericData = (GenericData)super.clone();
            Data.deepCopy(this, genericData);
            genericData.unknownFields = Data.clone(this.unknownFields);
            return genericData;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new IllegalStateException(cloneNotSupportedException);
        }
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return new EntrySet();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object == null) return false;
        if (!(object instanceof GenericData)) {
            return false;
        }
        if (!super.equals(object = (GenericData)object)) return false;
        if (!Objects.equals(this.classInfo, ((GenericData)object).classInfo)) return false;
        return bl;
    }

    @Override
    public final Object get(Object object) {
        if (!(object instanceof String)) {
            return null;
        }
        String string2 = (String)object;
        object = this.classInfo.getFieldInfo(string2);
        if (object != null) {
            return ((FieldInfo)object).getValue(this);
        }
        object = string2;
        if (!this.classInfo.getIgnoreCase()) return this.unknownFields.get(object);
        object = string2.toLowerCase(Locale.US);
        return this.unknownFields.get(object);
    }

    public final ClassInfo getClassInfo() {
        return this.classInfo;
    }

    public final Map<String, Object> getUnknownKeys() {
        return this.unknownFields;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.classInfo);
    }

    @Override
    public final Object put(String object, Object object2) {
        Object object3 = this.classInfo.getFieldInfo((String)object);
        if (object3 != null) {
            object = ((FieldInfo)object3).getValue(this);
            ((FieldInfo)object3).setValue(this, object2);
            return object;
        }
        object3 = object;
        if (!this.classInfo.getIgnoreCase()) return this.unknownFields.put((String)object3, object2);
        object3 = ((String)object).toLowerCase(Locale.US);
        return this.unknownFields.put((String)object3, object2);
    }

    @Override
    public final void putAll(Map<? extends String, ?> object) {
        Iterator<Map.Entry<String, ?>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            this.set((String)object.getKey(), object.getValue());
        }
    }

    @Override
    public final Object remove(Object object) {
        if (!(object instanceof String)) {
            return null;
        }
        String string2 = (String)object;
        if (this.classInfo.getFieldInfo(string2) != null) throw new UnsupportedOperationException();
        object = string2;
        if (!this.classInfo.getIgnoreCase()) return this.unknownFields.remove(object);
        object = string2.toLowerCase(Locale.US);
        return this.unknownFields.remove(object);
    }

    public GenericData set(String string2, Object object) {
        Object object2 = this.classInfo.getFieldInfo(string2);
        if (object2 != null) {
            ((FieldInfo)object2).setValue(this, object);
            return this;
        }
        object2 = string2;
        if (this.classInfo.getIgnoreCase()) {
            object2 = string2.toLowerCase(Locale.US);
        }
        this.unknownFields.put((String)object2, object);
        return this;
    }

    public final void setUnknownKeys(Map<String, Object> map) {
        this.unknownFields = map;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GenericData{classInfo=");
        stringBuilder.append(this.classInfo.names);
        stringBuilder.append(", ");
        stringBuilder.append(super.toString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    final class EntryIterator
    implements Iterator<Map.Entry<String, Object>> {
        private final Iterator<Map.Entry<String, Object>> fieldIterator;
        private boolean startedUnknown;
        private final Iterator<Map.Entry<String, Object>> unknownIterator;

        EntryIterator(DataMap.EntrySet entrySet) {
            this.fieldIterator = entrySet.iterator();
            this.unknownIterator = GenericData.this.unknownFields.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            if (this.fieldIterator.hasNext()) return true;
            if (this.unknownIterator.hasNext()) return true;
            return false;
        }

        @Override
        public Map.Entry<String, Object> next() {
            if (this.startedUnknown) return this.unknownIterator.next();
            if (this.fieldIterator.hasNext()) {
                return this.fieldIterator.next();
            }
            this.startedUnknown = true;
            return this.unknownIterator.next();
        }

        @Override
        public void remove() {
            if (this.startedUnknown) {
                this.unknownIterator.remove();
            }
            this.fieldIterator.remove();
        }
    }

    final class EntrySet
    extends AbstractSet<Map.Entry<String, Object>> {
        private final DataMap.EntrySet dataEntrySet;

        EntrySet() {
            this.dataEntrySet = new DataMap(GenericData.this, GenericData.this.classInfo.getIgnoreCase()).entrySet();
        }

        @Override
        public void clear() {
            GenericData.this.unknownFields.clear();
            this.dataEntrySet.clear();
        }

        @Override
        public Iterator<Map.Entry<String, Object>> iterator() {
            return new EntryIterator(this.dataEntrySet);
        }

        @Override
        public int size() {
            return GenericData.this.unknownFields.size() + this.dataEntrySet.size();
        }
    }

    public static final class Flags
    extends Enum<Flags> {
        private static final /* synthetic */ Flags[] $VALUES;
        public static final /* enum */ Flags IGNORE_CASE;

        static {
            Flags flags;
            IGNORE_CASE = flags = new Flags();
            $VALUES = new Flags[]{flags};
        }

        public static Flags valueOf(String string2) {
            return Enum.valueOf(Flags.class, string2);
        }

        public static Flags[] values() {
            return (Flags[])$VALUES.clone();
        }
    }

}

