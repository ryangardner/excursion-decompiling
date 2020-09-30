/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Finalizer
implements Runnable {
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    @NullableDecl
    private static final Constructor<Thread> bigThreadConstructor;
    @NullableDecl
    private static final Field inheritableThreadLocals;
    private static final Logger logger;
    private final WeakReference<Class<?>> finalizableReferenceClassReference;
    private final PhantomReference<Object> frqReference;
    private final ReferenceQueue<Object> queue;

    static {
        logger = Logger.getLogger(Finalizer.class.getName());
        AccessibleObject accessibleObject = Finalizer.getBigThreadConstructor();
        bigThreadConstructor = accessibleObject;
        accessibleObject = accessibleObject == null ? Finalizer.getInheritableThreadLocalsField() : null;
        inheritableThreadLocals = accessibleObject;
    }

    private Finalizer(Class<?> class_, ReferenceQueue<Object> referenceQueue, PhantomReference<Object> phantomReference) {
        this.queue = referenceQueue;
        this.finalizableReferenceClassReference = new WeakReference(class_);
        this.frqReference = phantomReference;
    }

    private boolean cleanUp(Reference<?> reference) {
        Reference<Object> reference2;
        Method method = this.getFinalizeReferentMethod();
        if (method == null) {
            return false;
        }
        do {
            reference.clear();
            if (reference == this.frqReference) {
                return false;
            }
            try {
                method.invoke(reference, new Object[0]);
            }
            catch (Throwable throwable) {
                logger.log(Level.SEVERE, "Error cleaning up after reference.", throwable);
            }
            reference2 = this.queue.poll();
            reference = reference2;
        } while (reference2 != null);
        return true;
    }

    @NullableDecl
    private static Constructor<Thread> getBigThreadConstructor() {
        try {
            return Thread.class.getConstructor(ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    @NullableDecl
    private Method getFinalizeReferentMethod() {
        GenericDeclaration genericDeclaration = (Class)this.finalizableReferenceClassReference.get();
        if (genericDeclaration == null) {
            return null;
        }
        try {
            return genericDeclaration.getMethod("finalizeReferent", new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new AssertionError(noSuchMethodException);
        }
    }

    @NullableDecl
    private static Field getInheritableThreadLocalsField() {
        try {
            Field field = Thread.class.getDeclaredField("inheritableThreadLocals");
            field.setAccessible(true);
            return field;
        }
        catch (Throwable throwable) {
            logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            return null;
        }
    }

    public static void startFinalizer(Class<?> constructor, ReferenceQueue<Object> object, PhantomReference<Object> object2) {
        String string2;
        block7 : {
            if (!((Class)((Object)constructor)).getName().equals(FINALIZABLE_REFERENCE)) throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
            object2 = new Finalizer((Class<?>)((Object)constructor), (ReferenceQueue<Object>)object, (PhantomReference<Object>)object2);
            string2 = Finalizer.class.getName();
            constructor = bigThreadConstructor;
            if (constructor != null) {
                try {
                    constructor = (Thread)constructor.newInstance(null, object2, string2, 0L, false);
                    break block7;
                }
                catch (Throwable throwable) {
                    logger.log(Level.INFO, "Failed to create a thread without inherited thread-local values", throwable);
                }
            }
            constructor = null;
        }
        object = constructor;
        if (constructor == null) {
            object = new Thread(null, (Runnable)object2, string2);
        }
        ((Thread)object).setDaemon(true);
        try {
            if (inheritableThreadLocals != null) {
                inheritableThreadLocals.set(object, null);
            }
        }
        catch (Throwable throwable) {
            logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", throwable);
        }
        ((Thread)object).start();
    }

    @Override
    public void run() {
        do {
            try {
                boolean bl;
                while (bl = this.cleanUp(this.queue.remove())) {
                }
                return;
            }
            catch (InterruptedException interruptedException) {
                continue;
            }
            break;
        } while (true);
    }
}

