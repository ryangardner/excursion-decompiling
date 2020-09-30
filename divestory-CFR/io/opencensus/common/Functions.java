/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.Function;
import javax.annotation.Nullable;

public final class Functions {
    private static final Function<Object, Void> RETURN_NULL = new Function<Object, Void>(){

        @Nullable
        @Override
        public Void apply(Object object) {
            return null;
        }
    };
    private static final Function<Object, String> RETURN_TO_STRING;
    private static final Function<Object, Void> THROW_ASSERTION_ERROR;
    private static final Function<Object, Void> THROW_ILLEGAL_ARGUMENT_EXCEPTION;

    static {
        THROW_ILLEGAL_ARGUMENT_EXCEPTION = new Function<Object, Void>(){

            @Override
            public Void apply(Object object) {
                throw new IllegalArgumentException();
            }
        };
        THROW_ASSERTION_ERROR = new Function<Object, Void>(){

            @Override
            public Void apply(Object object) {
                throw new AssertionError();
            }
        };
        RETURN_TO_STRING = new Function<Object, String>(){

            @Override
            public String apply(Object object) {
                if (object != null) return object.toString();
                return null;
            }
        };
    }

    private Functions() {
    }

    public static <T> Function<Object, T> returnConstant(T t) {
        return new Function<Object, T>(){

            @Override
            public T apply(Object object) {
                return (T)Object.this;
            }
        };
    }

    public static <T> Function<Object, T> returnNull() {
        return RETURN_NULL;
    }

    public static Function<Object, String> returnToString() {
        return RETURN_TO_STRING;
    }

    public static <T> Function<Object, T> throwAssertionError() {
        return THROW_ASSERTION_ERROR;
    }

    public static <T> Function<Object, T> throwIllegalArgumentException() {
        return THROW_ILLEGAL_ARGUMENT_EXCEPTION;
    }

}

