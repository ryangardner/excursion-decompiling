/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Preconditions;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Throwables {
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
    static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    @NullableDecl
    private static final Method getStackTraceDepthMethod;
    @NullableDecl
    private static final Method getStackTraceElementMethod;
    @NullableDecl
    private static final Object jla;

    static {
        Object object;
        jla = object = Throwables.getJLA();
        Object var1_1 = null;
        object = object == null ? null : Throwables.getGetMethod();
        getStackTraceElementMethod = object;
        object = jla == null ? var1_1 : Throwables.getSizeMethod();
        getStackTraceDepthMethod = object;
    }

    private Throwables() {
    }

    public static List<Throwable> getCausalChain(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>(4);
        arrayList.add(throwable);
        boolean bl = false;
        Throwable throwable2 = throwable;
        Throwable throwable3 = throwable;
        throwable = throwable2;
        while ((throwable2 = throwable3.getCause()) != null) {
            arrayList.add(throwable2);
            if (throwable2 == throwable) throw new IllegalArgumentException("Loop in causal chain detected.", throwable2);
            throwable3 = throwable;
            if (bl) {
                throwable3 = throwable.getCause();
            }
            bl ^= true;
            throwable = throwable3;
            throwable3 = throwable2;
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static <X extends Throwable> X getCauseAs(Throwable throwable, Class<X> serializable) {
        try {
            serializable = (Throwable)serializable.cast(throwable.getCause());
        }
        catch (ClassCastException classCastException) {
            classCastException.initCause(throwable);
            throw classCastException;
        }
        return (X)serializable;
    }

    @NullableDecl
    private static Method getGetMethod() {
        return Throwables.getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }

    @NullableDecl
    private static Object getJLA() {
        Object object = null;
        try {
            Object object2 = Class.forName(SHARED_SECRETS_CLASSNAME, false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
            return object2;
        }
        catch (ThreadDeath threadDeath) {
            throw threadDeath;
        }
        catch (Throwable throwable) {
            return object;
        }
    }

    @NullableDecl
    private static Method getJlaMethod(String object, Class<?> ... arrclass) throws ThreadDeath {
        try {
            return Class.forName(JAVA_LANG_ACCESS_CLASSNAME, false, null).getMethod((String)object, arrclass);
        }
        catch (Throwable throwable) {
            return null;
        }
        catch (ThreadDeath threadDeath) {
            throw threadDeath;
        }
    }

    public static Throwable getRootCause(Throwable throwable) {
        boolean bl = false;
        Throwable throwable2 = throwable;
        Throwable throwable3 = throwable;
        throwable = throwable2;
        while ((throwable2 = throwable3.getCause()) != null) {
            if (throwable2 == throwable) throw new IllegalArgumentException("Loop in causal chain detected.", throwable2);
            throwable3 = throwable;
            if (bl) {
                throwable3 = throwable.getCause();
            }
            bl ^= true;
            throwable = throwable3;
            throwable3 = throwable2;
        }
        return throwable3;
    }

    @NullableDecl
    private static Method getSizeMethod() {
        try {
            Method method = Throwables.getJlaMethod("getStackTraceDepth", Throwable.class);
            if (method == null) {
                return null;
            }
            Object object = Throwables.getJLA();
            Throwable throwable = new Throwable();
            method.invoke(object, throwable);
            return method;
        }
        catch (IllegalAccessException | UnsupportedOperationException | InvocationTargetException exception) {
            return null;
        }
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static Object invokeAccessibleNonThrowingMethod(Method object, Object object2, Object ... arrobject) {
        try {
            return ((Method)object).invoke(object2, arrobject);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw Throwables.propagate(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }

    private static List<StackTraceElement> jlaStackTrace(final Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new AbstractList<StackTraceElement>(){

            @Override
            public StackTraceElement get(int n) {
                return (StackTraceElement)Throwables.invokeAccessibleNonThrowingMethod(getStackTraceElementMethod, jla, new Object[]{throwable, n});
            }

            @Override
            public int size() {
                return (Integer)Throwables.invokeAccessibleNonThrowingMethod(getStackTraceDepthMethod, jla, new Object[]{throwable});
            }
        };
    }

    public static List<StackTraceElement> lazyStackTrace(Throwable list) {
        if (!Throwables.lazyStackTraceIsLazy()) return Collections.unmodifiableList(Arrays.asList(((Throwable)((Object)list)).getStackTrace()));
        return Throwables.jlaStackTrace((Throwable)((Object)list));
    }

    public static boolean lazyStackTraceIsLazy() {
        if (getStackTraceElementMethod == null) return false;
        if (getStackTraceDepthMethod == null) return false;
        return true;
    }

    @Deprecated
    public static RuntimeException propagate(Throwable throwable) {
        Throwables.throwIfUnchecked(throwable);
        throw new RuntimeException(throwable);
    }

    @Deprecated
    public static <X extends Throwable> void propagateIfInstanceOf(@NullableDecl Throwable throwable, Class<X> class_) throws Throwable {
        if (throwable == null) return;
        Throwables.throwIfInstanceOf(throwable, class_);
    }

    @Deprecated
    public static void propagateIfPossible(@NullableDecl Throwable throwable) {
        if (throwable == null) return;
        Throwables.throwIfUnchecked(throwable);
    }

    public static <X extends Throwable> void propagateIfPossible(@NullableDecl Throwable throwable, Class<X> class_) throws Throwable {
        Throwables.propagateIfInstanceOf(throwable, class_);
        Throwables.propagateIfPossible(throwable);
    }

    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@NullableDecl Throwable throwable, Class<X1> class_, Class<X2> class_2) throws Throwable {
        Preconditions.checkNotNull(class_2);
        Throwables.propagateIfInstanceOf(throwable, class_);
        Throwables.propagateIfPossible(throwable, class_2);
    }

    public static <X extends Throwable> void throwIfInstanceOf(Throwable throwable, Class<X> class_) throws Throwable {
        Preconditions.checkNotNull(throwable);
        if (class_.isInstance(throwable)) throw (Throwable)class_.cast(throwable);
    }

    public static void throwIfUnchecked(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        if (throwable instanceof RuntimeException) throw (RuntimeException)throwable;
        if (throwable instanceof Error) throw (Error)throwable;
    }

}

