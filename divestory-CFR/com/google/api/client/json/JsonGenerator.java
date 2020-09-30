/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class JsonGenerator
implements Closeable,
Flushable {
    private void serialize(boolean bl, Object object) throws IOException {
        if (object == null) {
            return;
        }
        Object object2 = object.getClass();
        if (Data.isNull(object)) {
            this.writeNull();
            return;
        }
        if (object instanceof String) {
            this.writeString((String)object);
            return;
        }
        boolean bl2 = object instanceof Number;
        boolean bl3 = true;
        boolean bl4 = true;
        if (bl2) {
            if (bl) {
                this.writeString(object.toString());
                return;
            }
            if (object instanceof BigDecimal) {
                this.writeNumber((BigDecimal)object);
                return;
            }
            if (object instanceof BigInteger) {
                this.writeNumber((BigInteger)object);
                return;
            }
            if (object instanceof Long) {
                this.writeNumber((Long)object);
                return;
            }
            if (object instanceof Float) {
                float f = ((Number)object).floatValue();
                bl = !Float.isInfinite(f) && !Float.isNaN(f) ? bl4 : false;
                Preconditions.checkArgument(bl);
                this.writeNumber(f);
                return;
            }
            if (!(object instanceof Integer || object instanceof Short || object instanceof Byte)) {
                double d = ((Number)object).doubleValue();
                bl = !Double.isInfinite(d) && !Double.isNaN(d) ? bl3 : false;
                Preconditions.checkArgument(bl);
                this.writeNumber(d);
                return;
            }
            this.writeNumber(((Number)object).intValue());
            return;
        }
        if (object instanceof Boolean) {
            this.writeBoolean((Boolean)object);
            return;
        }
        if (object instanceof DateTime) {
            this.writeString(((DateTime)object).toStringRfc3339());
            return;
        }
        if ((object instanceof Iterable || ((Class)object2).isArray()) && !(object instanceof Map) && !(object instanceof GenericData)) {
            this.writeStartArray();
            object = Types.iterableOf(object).iterator();
            do {
                if (!object.hasNext()) {
                    this.writeEndArray();
                    return;
                }
                this.serialize(bl, object.next());
            } while (true);
        }
        if (((Class)object2).isEnum()) {
            if ((object = FieldInfo.of((Enum)object).getName()) == null) {
                this.writeNull();
                return;
            }
            this.writeString((String)object);
            return;
        }
        this.writeStartObject();
        boolean bl5 = object instanceof Map && !(object instanceof GenericData);
        object2 = bl5 ? null : ClassInfo.of(object2);
        Iterator<Map.Entry<String, Object>> iterator2 = Data.mapOf(object).entrySet().iterator();
        do {
            Field field;
            if (!iterator2.hasNext()) {
                this.writeEndObject();
                return;
            }
            Map.Entry<String, Object> entry = iterator2.next();
            object = entry.getValue();
            if (object == null) continue;
            entry = entry.getKey();
            bl4 = bl5 ? bl : (field = ((ClassInfo)object2).getField((String)((Object)entry))) != null && field.getAnnotation(JsonString.class) != null;
            this.writeFieldName((String)((Object)entry));
            this.serialize(bl4, object);
        } while (true);
    }

    @Override
    public abstract void close() throws IOException;

    public void enablePrettyPrint() throws IOException {
    }

    @Override
    public abstract void flush() throws IOException;

    public abstract JsonFactory getFactory();

    public final void serialize(Object object) throws IOException {
        this.serialize(false, object);
    }

    public abstract void writeBoolean(boolean var1) throws IOException;

    public abstract void writeEndArray() throws IOException;

    public abstract void writeEndObject() throws IOException;

    public abstract void writeFieldName(String var1) throws IOException;

    public abstract void writeNull() throws IOException;

    public abstract void writeNumber(double var1) throws IOException;

    public abstract void writeNumber(float var1) throws IOException;

    public abstract void writeNumber(int var1) throws IOException;

    public abstract void writeNumber(long var1) throws IOException;

    public abstract void writeNumber(String var1) throws IOException;

    public abstract void writeNumber(BigDecimal var1) throws IOException;

    public abstract void writeNumber(BigInteger var1) throws IOException;

    public abstract void writeStartArray() throws IOException;

    public abstract void writeStartObject() throws IOException;

    public abstract void writeString(String var1) throws IOException;
}

