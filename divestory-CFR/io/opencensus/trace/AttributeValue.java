/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;
import io.opencensus.trace.AutoValue_AttributeValue_AttributeValueBoolean;
import io.opencensus.trace.AutoValue_AttributeValue_AttributeValueDouble;
import io.opencensus.trace.AutoValue_AttributeValue_AttributeValueLong;
import io.opencensus.trace.AutoValue_AttributeValue_AttributeValueString;

public abstract class AttributeValue {
    AttributeValue() {
    }

    public static AttributeValue booleanAttributeValue(boolean bl) {
        return AttributeValueBoolean.create(bl);
    }

    public static AttributeValue doubleAttributeValue(double d) {
        return AttributeValueDouble.create(d);
    }

    public static AttributeValue longAttributeValue(long l) {
        return AttributeValueLong.create(l);
    }

    public static AttributeValue stringAttributeValue(String string2) {
        return AttributeValueString.create(string2);
    }

    @Deprecated
    public abstract <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<Object, T> var4);

    public abstract <T> T match(Function<? super String, T> var1, Function<? super Boolean, T> var2, Function<? super Long, T> var3, Function<? super Double, T> var4, Function<Object, T> var5);

    static abstract class AttributeValueBoolean
    extends AttributeValue {
        AttributeValueBoolean() {
        }

        static AttributeValue create(Boolean bl) {
            return new AutoValue_AttributeValue_AttributeValueBoolean(Utils.checkNotNull(bl, "booleanValue"));
        }

        abstract Boolean getBooleanValue();

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function2.apply(this.getBooleanValue());
        }

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function2.apply(this.getBooleanValue());
        }
    }

    static abstract class AttributeValueDouble
    extends AttributeValue {
        AttributeValueDouble() {
        }

        static AttributeValue create(Double d) {
            return new AutoValue_AttributeValue_AttributeValueDouble(Utils.checkNotNull(d, "doubleValue"));
        }

        abstract Double getDoubleValue();

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function4.apply(this.getDoubleValue());
        }

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function4.apply(this.getDoubleValue());
        }
    }

    static abstract class AttributeValueLong
    extends AttributeValue {
        AttributeValueLong() {
        }

        static AttributeValue create(Long l) {
            return new AutoValue_AttributeValue_AttributeValueLong(Utils.checkNotNull(l, "longValue"));
        }

        abstract Long getLongValue();

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function3.apply(this.getLongValue());
        }

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function3.apply(this.getLongValue());
        }
    }

    static abstract class AttributeValueString
    extends AttributeValue {
        AttributeValueString() {
        }

        static AttributeValue create(String string2) {
            return new AutoValue_AttributeValue_AttributeValueString(Utils.checkNotNull(string2, "stringValue"));
        }

        abstract String getStringValue();

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<Object, T> function4) {
            return function.apply(this.getStringValue());
        }

        @Override
        public final <T> T match(Function<? super String, T> function, Function<? super Boolean, T> function2, Function<? super Long, T> function3, Function<? super Double, T> function4, Function<Object, T> function5) {
            return function.apply(this.getStringValue());
        }
    }

}

