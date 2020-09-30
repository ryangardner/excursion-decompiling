/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArrayValueMap {
    private final Object destination;
    private final Map<Field, ArrayValue> fieldMap = ArrayMap.create();
    private final Map<String, ArrayValue> keyMap = ArrayMap.create();

    public ArrayValueMap(Object object) {
        this.destination = object;
    }

    public void put(String string2, Class<?> class_, Object object) {
        ArrayValue arrayValue;
        ArrayValue arrayValue2 = arrayValue = this.keyMap.get(string2);
        if (arrayValue == null) {
            arrayValue2 = new ArrayValue(class_);
            this.keyMap.put(string2, arrayValue2);
        }
        arrayValue2.addValue(class_, object);
    }

    public void put(Field field, Class<?> class_, Object object) {
        ArrayValue arrayValue;
        ArrayValue arrayValue2 = arrayValue = this.fieldMap.get(field);
        if (arrayValue == null) {
            arrayValue2 = new ArrayValue(class_);
            this.fieldMap.put(field, arrayValue2);
        }
        arrayValue2.addValue(class_, object);
    }

    public void setValues() {
        for (Map.Entry<String, ArrayValue> entry : this.keyMap.entrySet()) {
            ((Map)this.destination).put(entry.getKey(), entry.getValue().toArray());
        }
        Iterator<Map.Entry<Object, ArrayValue>> iterator2 = this.fieldMap.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Object, ArrayValue> entry = iterator2.next();
            FieldInfo.setFieldValue((Field)entry.getKey(), this.destination, entry.getValue().toArray());
        }
    }

    static class ArrayValue {
        final Class<?> componentType;
        final ArrayList<Object> values = new ArrayList();

        ArrayValue(Class<?> class_) {
            this.componentType = class_;
        }

        void addValue(Class<?> class_, Object object) {
            boolean bl = class_ == this.componentType;
            Preconditions.checkArgument(bl);
            this.values.add(object);
        }

        Object toArray() {
            return Types.toArray(this.values, this.componentType);
        }
    }

}

