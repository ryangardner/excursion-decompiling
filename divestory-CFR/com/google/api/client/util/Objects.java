/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Preconditions;

public final class Objects {
    private Objects() {
    }

    public static boolean equal(Object object, Object object2) {
        return com.google.common.base.Objects.equal(object, object2);
    }

    public static ToStringHelper toStringHelper(Object object) {
        return new ToStringHelper(object.getClass().getSimpleName());
    }

    public static final class ToStringHelper {
        private final String className;
        private ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        ToStringHelper(String string2) {
            ValueHolder valueHolder;
            this.holderHead = valueHolder = new ValueHolder();
            this.holderTail = valueHolder;
            this.className = string2;
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder;
            this.holderTail.next = valueHolder = new ValueHolder();
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(String string2, Object object) {
            ValueHolder valueHolder = this.addHolder();
            valueHolder.value = object;
            valueHolder.name = Preconditions.checkNotNull(string2);
            return this;
        }

        public ToStringHelper add(String string2, Object object) {
            return this.addHolder(string2, object);
        }

        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public String toString() {
            boolean bl = this.omitNullValues;
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append(this.className);
            stringBuilder.append('{');
            ValueHolder valueHolder = this.holderHead.next;
            String string2 = "";
            do {
                String string3;
                block7 : {
                    block6 : {
                        if (valueHolder == null) {
                            stringBuilder.append('}');
                            return stringBuilder.toString();
                        }
                        if (!bl) break block6;
                        string3 = string2;
                        if (valueHolder.value == null) break block7;
                    }
                    stringBuilder.append(string2);
                    if (valueHolder.name != null) {
                        stringBuilder.append(valueHolder.name);
                        stringBuilder.append('=');
                    }
                    stringBuilder.append(valueHolder.value);
                    string3 = ", ";
                }
                valueHolder = valueHolder.next;
                string2 = string3;
            } while (true);
        }

        private static final class ValueHolder {
            String name;
            ValueHolder next;
            Object value;

            private ValueHolder() {
            }
        }

    }

}

