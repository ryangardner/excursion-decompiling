/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.platform.android;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u0000 \r2\u00020\u0001:\u0001\rB#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lokhttp3/internal/platform/android/CloseGuard;", "", "getMethod", "Ljava/lang/reflect/Method;", "openMethod", "warnIfOpenMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "createAndOpen", "closer", "", "warnIfOpen", "", "closeGuardInstance", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class CloseGuard {
    public static final Companion Companion = new Companion(null);
    private final Method getMethod;
    private final Method openMethod;
    private final Method warnIfOpenMethod;

    public CloseGuard(Method method, Method method2, Method method3) {
        this.getMethod = method;
        this.openMethod = method2;
        this.warnIfOpenMethod = method3;
    }

    public final Object createAndOpen(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "closer");
        Method method = this.getMethod;
        if (method == null) return null;
        try {
            Object object = method.invoke(null, new Object[0]);
            method = this.openMethod;
            if (method == null) {
                Intrinsics.throwNpe();
            }
            method.invoke(object, string2);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public final boolean warnIfOpen(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object == null) return bl2;
        try {
            Method method = this.warnIfOpenMethod;
            if (method == null) {
                Intrinsics.throwNpe();
            }
            method.invoke(object, new Object[0]);
            return true;
        }
        catch (Exception exception) {
            return bl;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/platform/android/CloseGuard$Companion;", "", "()V", "get", "Lokhttp3/internal/platform/android/CloseGuard;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final CloseGuard get() {
            GenericDeclaration genericDeclaration;
            GenericDeclaration genericDeclaration2;
            Method method;
            try {
                genericDeclaration2 = Class.forName("dalvik.system.CloseGuard");
                method = ((Class)genericDeclaration2).getMethod("get", new Class[0]);
                genericDeclaration = ((Class)genericDeclaration2).getMethod("open", String.class);
                genericDeclaration2 = ((Class)genericDeclaration2).getMethod("warnIfOpen", new Class[0]);
                return new CloseGuard(method, (Method)genericDeclaration, (Method)genericDeclaration2);
            }
            catch (Exception exception) {
                method = null;
                genericDeclaration = genericDeclaration2 = method;
            }
            return new CloseGuard(method, (Method)genericDeclaration, (Method)genericDeclaration2);
        }
    }

}

