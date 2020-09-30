/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class MoreObjects {
    private MoreObjects() {
    }

    public static <T> T firstNonNull(@NullableDecl T t, @NullableDecl T t2) {
        if (t != null) {
            return t;
        }
        if (t2 == null) throw new NullPointerException("Both parameters are null");
        return t2;
    }

    public static ToStringHelper toStringHelper(Class<?> class_) {
        return new ToStringHelper(class_.getSimpleName());
    }

    public static ToStringHelper toStringHelper(Object object) {
        return new ToStringHelper(object.getClass().getSimpleName());
    }

    public static ToStringHelper toStringHelper(String string2) {
        return new ToStringHelper(string2);
    }

    public static final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String string2) {
            ValueHolder valueHolder;
            this.holderHead = valueHolder = new ValueHolder();
            this.holderTail = valueHolder;
            this.omitNullValues = false;
            this.className = Preconditions.checkNotNull(string2);
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder;
            this.holderTail.next = valueHolder = new ValueHolder();
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(@NullableDecl Object object) {
            this.addHolder().value = object;
            return this;
        }

        private ToStringHelper addHolder(String string2, @NullableDecl Object object) {
            ValueHolder valueHolder = this.addHolder();
            valueHolder.value = object;
            valueHolder.name = Preconditions.checkNotNull(string2);
            return this;
        }

        public ToStringHelper add(String string2, char c) {
            return this.addHolder(string2, String.valueOf(c));
        }

        public ToStringHelper add(String string2, double d) {
            return this.addHolder(string2, String.valueOf(d));
        }

        public ToStringHelper add(String string2, float f) {
            return this.addHolder(string2, String.valueOf(f));
        }

        public ToStringHelper add(String string2, int n) {
            return this.addHolder(string2, String.valueOf(n));
        }

        public ToStringHelper add(String string2, long l) {
            return this.addHolder(string2, String.valueOf(l));
        }

        public ToStringHelper add(String string2, @NullableDecl Object object) {
            return this.addHolder(string2, object);
        }

        public ToStringHelper add(String string2, boolean bl) {
            return this.addHolder(string2, String.valueOf(bl));
        }

        public ToStringHelper addValue(char c) {
            return this.addHolder(String.valueOf(c));
        }

        public ToStringHelper addValue(double d) {
            return this.addHolder(String.valueOf(d));
        }

        public ToStringHelper addValue(float f) {
            return this.addHolder(String.valueOf(f));
        }

        public ToStringHelper addValue(int n) {
            return this.addHolder(String.valueOf(n));
        }

        public ToStringHelper addValue(long l) {
            return this.addHolder(String.valueOf(l));
        }

        public ToStringHelper addValue(@NullableDecl Object object) {
            return this.addHolder(object);
        }

        public ToStringHelper addValue(boolean bl) {
            return this.addHolder(String.valueOf(bl));
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
                block9 : {
                    Object object;
                    block8 : {
                        if (valueHolder == null) {
                            stringBuilder.append('}');
                            return stringBuilder.toString();
                        }
                        object = valueHolder.value;
                        if (!bl) break block8;
                        string3 = string2;
                        if (object == null) break block9;
                    }
                    stringBuilder.append(string2);
                    if (valueHolder.name != null) {
                        stringBuilder.append(valueHolder.name);
                        stringBuilder.append('=');
                    }
                    if (object != null && object.getClass().isArray()) {
                        string2 = Arrays.deepToString(new Object[]{object});
                        stringBuilder.append(string2, 1, string2.length() - 1);
                    } else {
                        stringBuilder.append(object);
                    }
                    string3 = ", ";
                }
                valueHolder = valueHolder.next;
                string2 = string3;
            } while (true);
        }

        private static final class ValueHolder {
            @NullableDecl
            String name;
            @NullableDecl
            ValueHolder next;
            @NullableDecl
            Object value;

            private ValueHolder() {
            }
        }

    }

}

