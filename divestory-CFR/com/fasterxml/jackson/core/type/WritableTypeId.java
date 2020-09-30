/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.type;

import com.fasterxml.jackson.core.JsonToken;

public class WritableTypeId {
    public String asProperty;
    public Object extra;
    public Object forValue;
    public Class<?> forValueType;
    public Object id;
    public Inclusion include;
    public JsonToken valueShape;
    public boolean wrapperWritten;

    public WritableTypeId() {
    }

    public WritableTypeId(Object object, JsonToken jsonToken) {
        this(object, jsonToken, null);
    }

    public WritableTypeId(Object object, JsonToken jsonToken, Object object2) {
        this.forValue = object;
        this.id = object2;
        this.valueShape = jsonToken;
    }

    public WritableTypeId(Object object, Class<?> class_, JsonToken jsonToken) {
        this(object, jsonToken, null);
        this.forValueType = class_;
    }

    public static final class Inclusion
    extends Enum<Inclusion> {
        private static final /* synthetic */ Inclusion[] $VALUES;
        public static final /* enum */ Inclusion METADATA_PROPERTY;
        public static final /* enum */ Inclusion PARENT_PROPERTY;
        public static final /* enum */ Inclusion PAYLOAD_PROPERTY;
        public static final /* enum */ Inclusion WRAPPER_ARRAY;
        public static final /* enum */ Inclusion WRAPPER_OBJECT;

        static {
            Inclusion inclusion;
            WRAPPER_ARRAY = new Inclusion();
            WRAPPER_OBJECT = new Inclusion();
            METADATA_PROPERTY = new Inclusion();
            PAYLOAD_PROPERTY = new Inclusion();
            PARENT_PROPERTY = inclusion = new Inclusion();
            $VALUES = new Inclusion[]{WRAPPER_ARRAY, WRAPPER_OBJECT, METADATA_PROPERTY, PAYLOAD_PROPERTY, inclusion};
        }

        public static Inclusion valueOf(String string2) {
            return Enum.valueOf(Inclusion.class, string2);
        }

        public static Inclusion[] values() {
            return (Inclusion[])$VALUES.clone();
        }

        public boolean requiresObjectContext() {
            if (this == METADATA_PROPERTY) return true;
            if (this == PAYLOAD_PROPERTY) return true;
            return false;
        }
    }

}

