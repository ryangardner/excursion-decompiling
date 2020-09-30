/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.UninitializedPropertyAccessException;

public class Intrinsics {
    private Intrinsics() {
    }

    public static boolean areEqual(double d, Double d2) {
        if (d2 == null) return false;
        if (d != d2) return false;
        return true;
    }

    public static boolean areEqual(float f, Float f2) {
        if (f2 == null) return false;
        if (f != f2.floatValue()) return false;
        return true;
    }

    public static boolean areEqual(Double d, double d2) {
        if (d == null) return false;
        if (d != d2) return false;
        return true;
    }

    public static boolean areEqual(Double d, Double d2) {
        boolean bl = true;
        if (d == null) {
            if (d2 != null) return false;
            return bl;
        }
        if (d2 == null) return false;
        if (d.doubleValue() != d2.doubleValue()) return false;
        return bl;
    }

    public static boolean areEqual(Float f, float f2) {
        if (f == null) return false;
        if (f.floatValue() != f2) return false;
        return true;
    }

    public static boolean areEqual(Float f, Float f2) {
        boolean bl = true;
        if (f == null) {
            if (f2 != null) return false;
            return bl;
        }
        if (f2 == null) return false;
        if (f.floatValue() != f2.floatValue()) return false;
        return bl;
    }

    public static boolean areEqual(Object object, Object object2) {
        if (object != null) {
            return object.equals(object2);
        }
        if (object2 != null) return false;
        return true;
    }

    public static void checkExpressionValueIsNotNull(Object object, String string2) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" must not be null");
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException(((StringBuilder)object).toString()));
    }

    public static void checkFieldIsNotNull(Object object, String string2) {
        if (object == null) throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string2));
    }

    public static void checkFieldIsNotNull(Object object, String string2, String string3) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Field specified as non-null is null: ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(".");
        ((StringBuilder)object).append(string3);
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException(((StringBuilder)object).toString()));
    }

    public static void checkHasClass(String charSequence) throws ClassNotFoundException {
        String string2 = ((String)charSequence).replace('/', '.');
        try {
            Class.forName(string2);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Class ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" is not found. Please update the Kotlin runtime to the latest version");
            throw Intrinsics.sanitizeStackTrace(new ClassNotFoundException(((StringBuilder)charSequence).toString(), classNotFoundException));
        }
    }

    public static void checkHasClass(String charSequence, String string2) throws ClassNotFoundException {
        String string3 = ((String)charSequence).replace('/', '.');
        try {
            Class.forName(string3);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Class ");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(" is not found: this code requires the Kotlin runtime of version at least ");
            ((StringBuilder)charSequence).append(string2);
            throw Intrinsics.sanitizeStackTrace(new ClassNotFoundException(((StringBuilder)charSequence).toString(), classNotFoundException));
        }
    }

    public static void checkNotNull(Object object) {
        if (object != null) return;
        Intrinsics.throwJavaNpe();
    }

    public static void checkNotNull(Object object, String string2) {
        if (object != null) return;
        Intrinsics.throwJavaNpe(string2);
    }

    public static void checkNotNullExpressionValue(Object object, String string2) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" must not be null");
        throw Intrinsics.sanitizeStackTrace(new NullPointerException(((StringBuilder)object).toString()));
    }

    public static void checkNotNullParameter(Object object, String string2) {
        if (object == null) throw Intrinsics.sanitizeStackTrace(new NullPointerException(string2));
    }

    public static void checkParameterIsNotNull(Object object, String string2) {
        if (object != null) return;
        Intrinsics.throwParameterIsNullException(string2);
    }

    public static void checkReturnedValueIsNotNull(Object object, String string2) {
        if (object == null) throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string2));
    }

    public static void checkReturnedValueIsNotNull(Object object, String string2, String string3) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Method specified as non-null returned null: ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(".");
        ((StringBuilder)object).append(string3);
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException(((StringBuilder)object).toString()));
    }

    public static int compare(int n, int n2) {
        if (n < n2) {
            return -1;
        }
        if (n != n2) return 1;
        return 0;
    }

    public static int compare(long l, long l2) {
        long l3 = l LCMP l2;
        if (l3 < 0) {
            return (int)((long)-1);
        }
        if (l3 != false) return (int)((long)1);
        return (int)((long)0);
    }

    public static void needClassReification() {
        Intrinsics.throwUndefinedForReified();
    }

    public static void needClassReification(String string2) {
        Intrinsics.throwUndefinedForReified(string2);
    }

    public static void reifiedOperationMarker(int n, String string2) {
        Intrinsics.throwUndefinedForReified();
    }

    public static void reifiedOperationMarker(int n, String string2, String string3) {
        Intrinsics.throwUndefinedForReified(string3);
    }

    private static <T extends Throwable> T sanitizeStackTrace(T t) {
        return Intrinsics.sanitizeStackTrace(t, Intrinsics.class.getName());
    }

    static <T extends Throwable> T sanitizeStackTrace(T t, String string2) {
        StackTraceElement[] arrstackTraceElement = ((Throwable)t).getStackTrace();
        int n = arrstackTraceElement.length;
        int n2 = -1;
        int n3 = 0;
        do {
            if (n3 >= n) {
                ((Throwable)t).setStackTrace(Arrays.copyOfRange(arrstackTraceElement, n2 + 1, n));
                return t;
            }
            if (string2.equals(arrstackTraceElement[n3].getClassName())) {
                n2 = n3;
            }
            ++n3;
        } while (true);
    }

    public static String stringPlus(String string2, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(object);
        return stringBuilder.toString();
    }

    public static void throwAssert() {
        throw Intrinsics.sanitizeStackTrace(new AssertionError());
    }

    public static void throwAssert(String string2) {
        throw Intrinsics.sanitizeStackTrace(new AssertionError((Object)string2));
    }

    public static void throwIllegalArgument() {
        throw Intrinsics.sanitizeStackTrace(new IllegalArgumentException());
    }

    public static void throwIllegalArgument(String string2) {
        throw Intrinsics.sanitizeStackTrace(new IllegalArgumentException(string2));
    }

    public static void throwIllegalState() {
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException());
    }

    public static void throwIllegalState(String string2) {
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string2));
    }

    public static void throwJavaNpe() {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException());
    }

    public static void throwJavaNpe(String string2) {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException(string2));
    }

    public static void throwNpe() {
        throw Intrinsics.sanitizeStackTrace(new KotlinNullPointerException());
    }

    public static void throwNpe(String string2) {
        throw Intrinsics.sanitizeStackTrace(new KotlinNullPointerException(string2));
    }

    private static void throwParameterIsNullException(String string2) {
        Object object = Thread.currentThread().getStackTrace()[3];
        String string3 = ((StackTraceElement)object).getClassName();
        object = ((StackTraceElement)object).getMethodName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Parameter specified as non-null is null: method ");
        stringBuilder.append(string3);
        stringBuilder.append(".");
        stringBuilder.append((String)object);
        stringBuilder.append(", parameter ");
        stringBuilder.append(string2);
        throw Intrinsics.sanitizeStackTrace(new IllegalArgumentException(stringBuilder.toString()));
    }

    public static void throwUndefinedForReified() {
        Intrinsics.throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
    }

    public static void throwUndefinedForReified(String string2) {
        throw new UnsupportedOperationException(string2);
    }

    public static void throwUninitializedProperty(String string2) {
        throw Intrinsics.sanitizeStackTrace(new UninitializedPropertyAccessException(string2));
    }

    public static void throwUninitializedPropertyAccessException(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lateinit property ");
        stringBuilder.append(string2);
        stringBuilder.append(" has not been initialized");
        Intrinsics.throwUninitializedProperty(stringBuilder.toString());
    }
}

