/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.grpc;

import io.grpc.Context;
import java.util.logging.Level;
import java.util.logging.Logger;

final class ThreadLocalContextStorage
extends Context.Storage {
    static final ThreadLocal<Context> localContext;
    private static final Logger log;

    static {
        log = Logger.getLogger(ThreadLocalContextStorage.class.getName());
        localContext = new ThreadLocal();
    }

    ThreadLocalContextStorage() {
    }

    @Override
    public Context current() {
        Context context;
        Context context2 = context = localContext.get();
        if (context != null) return context2;
        return Context.ROOT;
    }

    @Override
    public void detach(Context context, Context context2) {
        if (this.current() != context) {
            log.log(Level.SEVERE, "Context was not attached when detaching", new Throwable().fillInStackTrace());
        }
        if (context2 != Context.ROOT) {
            localContext.set(context2);
            return;
        }
        localContext.set(null);
    }

    @Override
    public Context doAttach(Context context) {
        Context context2 = this.current();
        localContext.set(context);
        return context2;
    }
}

