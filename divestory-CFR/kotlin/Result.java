/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0087@\u0018\u0000 \u001e*\u0006\b\u0000\u0010\u0001 \u00012\u00060\u0002j\u0002`\u0003:\u0002\u001e\u001fB\u0016\b\u0001\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u0010\u001a\u00020\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00d6\u0003J\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0012\u0010\u0016\u001a\u0004\u0018\u00018\u0000H\u0087\b\u00a2\u0006\u0004\b\u0017\u0010\u0007J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\u000f\u0010\u001a\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dR\u0011\u0010\b\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\u000f\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006 "}, d2={"Lkotlin/Result;", "T", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "value", "", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "isFailure", "", "isFailure-impl", "(Ljava/lang/Object;)Z", "isSuccess", "isSuccess-impl", "value$annotations", "()V", "equals", "other", "exceptionOrNull", "", "exceptionOrNull-impl", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getOrNull", "getOrNull-impl", "hashCode", "", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "Companion", "Failure", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class Result<T>
implements Serializable {
    public static final Companion Companion = new Companion(null);
    private final Object value;

    private /* synthetic */ Result(Object object) {
        this.value = object;
    }

    public static final /* synthetic */ Result box-impl(Object object) {
        return new Result<T>(object);
    }

    public static Object constructor-impl(Object object) {
        return object;
    }

    public static boolean equals-impl(Object object, Object object2) {
        if (!(object2 instanceof Result)) return false;
        if (!Intrinsics.areEqual(object, ((Result)object2).unbox-impl())) return false;
        return true;
    }

    public static final boolean equals-impl0(Object object, Object object2) {
        return Intrinsics.areEqual(object, object2);
    }

    public static final Throwable exceptionOrNull-impl(Object object) {
        if (!(object instanceof Failure)) return null;
        return ((Failure)object).exception;
    }

    private static final T getOrNull-impl(Object object) {
        Object object2 = object;
        if (!Result.isFailure-impl(object)) return (T)object2;
        object2 = null;
        return (T)object2;
    }

    public static int hashCode-impl(Object object) {
        if (object == null) return 0;
        return object.hashCode();
    }

    public static final boolean isFailure-impl(Object object) {
        return object instanceof Failure;
    }

    public static final boolean isSuccess-impl(Object object) {
        return object instanceof Failure ^ true;
    }

    public static String toString-impl(Object object) {
        if (object instanceof Failure) {
            return object.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Success(");
        stringBuilder.append(object);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public static /* synthetic */ void value$annotations() {
    }

    public boolean equals(Object object) {
        return Result.equals-impl(this.value, object);
    }

    public int hashCode() {
        return Result.hashCode-impl(this.value);
    }

    public String toString() {
        return Result.toString-impl(this.value);
    }

    public final /* synthetic */ Object unbox-impl() {
        return this.value;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ%\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\n\u001a\u0002H\u0005H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"Lkotlin/Result$Companion;", "", "()V", "failure", "Lkotlin/Result;", "T", "exception", "", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "success", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final <T> Object failure(Throwable throwable) {
            return Result.constructor-impl(ResultKt.createFailure(throwable));
        }

        private final <T> Object success(T t) {
            return Result.constructor-impl(t);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0096\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lkotlin/Result$Failure;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "exception", "", "(Ljava/lang/Throwable;)V", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Failure
    implements Serializable {
        public final Throwable exception;

        public Failure(Throwable throwable) {
            Intrinsics.checkParameterIsNotNull(throwable, "exception");
            this.exception = throwable;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Failure)) return false;
            if (!Intrinsics.areEqual(this.exception, ((Failure)object).exception)) return false;
            return true;
        }

        public int hashCode() {
            return this.exception.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failure(");
            stringBuilder.append(this.exception);
            stringBuilder.append(')');
            return stringBuilder.toString();
        }
    }

}

