/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.Closeables;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Closer
implements Closeable {
    private static final Suppressor SUPPRESSOR;
    private final Deque<Closeable> stack = new ArrayDeque<Closeable>(4);
    final Suppressor suppressor;
    @MonotonicNonNullDecl
    private Throwable thrown;

    static {
        Suppressor suppressor = SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE;
        SUPPRESSOR = suppressor;
    }

    Closer(Suppressor suppressor) {
        this.suppressor = Preconditions.checkNotNull(suppressor);
    }

    public static Closer create() {
        return new Closer(SUPPRESSOR);
    }

    @Override
    public void close() throws IOException {
        Throwable throwable = this.thrown;
        while (!this.stack.isEmpty()) {
            Closeable closeable = this.stack.removeFirst();
            try {
                closeable.close();
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                    continue;
                }
                this.suppressor.suppress(closeable, throwable, throwable2);
            }
        }
        if (this.thrown != null) return;
        if (throwable == null) {
            return;
        }
        Throwables.propagateIfPossible(throwable, IOException.class);
        throw new AssertionError(throwable);
    }

    public <C extends Closeable> C register(@NullableDecl C c) {
        if (c == null) return c;
        this.stack.addFirst(c);
        return c;
    }

    public RuntimeException rethrow(Throwable throwable) throws IOException {
        Preconditions.checkNotNull(throwable);
        this.thrown = throwable;
        Throwables.propagateIfPossible(throwable, IOException.class);
        throw new RuntimeException(throwable);
    }

    public <X extends Exception> RuntimeException rethrow(Throwable throwable, Class<X> class_) throws IOException, Exception {
        Preconditions.checkNotNull(throwable);
        this.thrown = throwable;
        Throwables.propagateIfPossible(throwable, IOException.class);
        Throwables.propagateIfPossible(throwable, class_);
        throw new RuntimeException(throwable);
    }

    public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable throwable, Class<X1> class_, Class<X2> class_2) throws IOException, Exception {
        Preconditions.checkNotNull(throwable);
        this.thrown = throwable;
        Throwables.propagateIfPossible(throwable, IOException.class);
        Throwables.propagateIfPossible(throwable, class_, class_2);
        throw new RuntimeException(throwable);
    }

    static final class LoggingSuppressor
    implements Suppressor {
        static final LoggingSuppressor INSTANCE = new LoggingSuppressor();

        LoggingSuppressor() {
        }

        @Override
        public void suppress(Closeable closeable, Throwable object, Throwable throwable) {
            object = Closeables.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppressing exception thrown when closing ");
            stringBuilder.append(closeable);
            ((Logger)object).log(level, stringBuilder.toString(), throwable);
        }
    }

    static final class SuppressingSuppressor
    implements Suppressor {
        static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
        static final Method addSuppressed = SuppressingSuppressor.addSuppressedMethodOrNull();

        SuppressingSuppressor() {
        }

        private static Method addSuppressedMethodOrNull() {
            try {
                return Throwable.class.getMethod("addSuppressed", Throwable.class);
            }
            catch (Throwable throwable) {
                return null;
            }
        }

        static boolean isAvailable() {
            if (addSuppressed == null) return false;
            return true;
        }

        @Override
        public void suppress(Closeable closeable, Throwable throwable, Throwable throwable2) {
            if (throwable == throwable2) {
                return;
            }
            try {
                addSuppressed.invoke(throwable, throwable2);
                return;
            }
            catch (Throwable throwable3) {
                LoggingSuppressor.INSTANCE.suppress(closeable, throwable, throwable2);
            }
        }
    }

    static interface Suppressor {
        public void suppress(Closeable var1, Throwable var2, Throwable var3);
    }

}

