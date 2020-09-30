/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.FinalizableReference;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class FinalizableReferenceQueue
implements Closeable {
    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final Method startFinalizer = FinalizableReferenceQueue.getStartFinalizer(FinalizableReferenceQueue.loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader()));
    final PhantomReference<Object> frqRef;
    final ReferenceQueue<Object> queue = new ReferenceQueue();
    final boolean threadStarted;

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public FinalizableReferenceQueue() {
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(this, this.queue);
        this.frqRef = phantomReference;
        boolean bl = true;
        try {
            startFinalizer.invoke(null, FinalizableReference.class, this.queue, phantomReference);
        }
        catch (Throwable throwable) {
            logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", throwable);
            bl = false;
        }
        this.threadStarted = bl;
        return;
        catch (IllegalAccessException illegalAccessException) {
            throw new AssertionError(illegalAccessException);
        }
    }

    static Method getStartFinalizer(Class<?> genericDeclaration) {
        try {
            return genericDeclaration.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new AssertionError(noSuchMethodException);
        }
    }

    private static Class<?> loadFinalizer(FinalizerLoader ... arrfinalizerLoader) {
        int n = arrfinalizerLoader.length;
        int n2 = 0;
        while (n2 < n) {
            Class<?> class_ = arrfinalizerLoader[n2].loadFinalizer();
            if (class_ != null) {
                return class_;
            }
            ++n2;
        }
        throw new AssertionError();
    }

    void cleanUp() {
        Reference<Object> reference;
        if (this.threadStarted) {
            return;
        }
        while ((reference = this.queue.poll()) != null) {
            reference.clear();
            try {
                ((FinalizableReference)((Object)reference)).finalizeReferent();
            }
            catch (Throwable throwable) {
                logger.log(Level.SEVERE, "Error cleaning up after reference.", throwable);
            }
        }
    }

    @Override
    public void close() {
        this.frqRef.enqueue();
        this.cleanUp();
    }

    static class DecoupledLoader
    implements FinalizerLoader {
        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.";

        DecoupledLoader() {
        }

        URL getBaseUrl() throws IOException {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(FinalizableReferenceQueue.FINALIZER_CLASS_NAME.replace('.', '/'));
            ((StringBuilder)charSequence).append(".class");
            String string2 = ((StringBuilder)charSequence).toString();
            Serializable serializable = this.getClass().getClassLoader().getResource(string2);
            if (serializable == null) throw new FileNotFoundException(string2);
            charSequence = ((URL)serializable).toString();
            if (((String)charSequence).endsWith(string2)) {
                return new URL((URL)serializable, ((String)charSequence).substring(0, ((String)charSequence).length() - string2.length()));
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Unsupported path style: ");
            ((StringBuilder)serializable).append((String)charSequence);
            throw new IOException(((StringBuilder)serializable).toString());
        }

        @NullableDecl
        @Override
        public Class<?> loadFinalizer() {
            try {
                return this.newLoader(this.getBaseUrl()).loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            }
            catch (Exception exception) {
                logger.log(Level.WARNING, LOADING_ERROR, exception);
                return null;
            }
        }

        URLClassLoader newLoader(URL uRL) {
            return new URLClassLoader(new URL[]{uRL}, null);
        }
    }

    static class DirectLoader
    implements FinalizerLoader {
        DirectLoader() {
        }

        @Override
        public Class<?> loadFinalizer() {
            try {
                return Class.forName(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new AssertionError(classNotFoundException);
            }
        }
    }

    static interface FinalizerLoader {
        @NullableDecl
        public Class<?> loadFinalizer();
    }

    static class SystemLoader
    implements FinalizerLoader {
        static boolean disabled;

        SystemLoader() {
        }

        @NullableDecl
        @Override
        public Class<?> loadFinalizer() {
            ClassLoader classLoader;
            if (disabled) {
                return null;
            }
            try {
                classLoader = ClassLoader.getSystemClassLoader();
                if (classLoader == null) return null;
            }
            catch (SecurityException securityException) {
                logger.info("Not allowed to access system class loader.");
                return null;
            }
            try {
                return classLoader.loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            }
            catch (ClassNotFoundException classNotFoundException) {
                return null;
            }
        }
    }

}

