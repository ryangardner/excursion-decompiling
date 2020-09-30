/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import org.apache.harmony.awt.datatransfer.DTK;

public final class ContextStorage {
    private static final ContextStorage globalContext = new ContextStorage();
    private final Object contextLock = new ContextLock();
    private DTK dtk;
    private GraphicsEnvironment graphicsEnvironment;
    private volatile boolean shutdownPending = false;
    private Toolkit toolkit;

    public static Object getContextLock() {
        return ContextStorage.getCurrentContext().contextLock;
    }

    private static ContextStorage getCurrentContext() {
        return globalContext;
    }

    public static DTK getDTK() {
        return ContextStorage.getCurrentContext().dtk;
    }

    public static Toolkit getDefaultToolkit() {
        return ContextStorage.getCurrentContext().toolkit;
    }

    public static GraphicsEnvironment getGraphicsEnvironment() {
        return ContextStorage.getCurrentContext().graphicsEnvironment;
    }

    public static void setDTK(DTK dTK) {
        ContextStorage.getCurrentContext().dtk = dTK;
    }

    public static void setDefaultToolkit(Toolkit toolkit) {
        ContextStorage.getCurrentContext().toolkit = toolkit;
    }

    public static void setGraphicsEnvironment(GraphicsEnvironment graphicsEnvironment) {
        ContextStorage.getCurrentContext().graphicsEnvironment = graphicsEnvironment;
    }

    public static boolean shutdownPending() {
        return ContextStorage.getCurrentContext().shutdownPending;
    }

    void shutdown() {
    }

    private class ContextLock {
        private ContextLock() {
        }
    }

}

