/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.concurrent.ThreadsKt$thread
 *  kotlin.concurrent.ThreadsKt$thread$thread
 */
package kotlin.concurrent;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aJ\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a0\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u000f0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000f0\fH\u0087\b\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2={"thread", "Ljava/lang/Thread;", "start", "", "isDaemon", "contextClassLoader", "Ljava/lang/ClassLoader;", "name", "", "priority", "", "block", "Lkotlin/Function0;", "", "getOrSet", "T", "", "Ljava/lang/ThreadLocal;", "default", "(Ljava/lang/ThreadLocal;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ThreadsKt {
    private static final <T> T getOrSet(ThreadLocal<T> object, Function0<? extends T> function0) {
        T t = ((ThreadLocal)object).get();
        if (t != null) {
            object = t;
            return (T)object;
        }
        function0 = function0.invoke();
        ((ThreadLocal)object).set(function0);
        object = function0;
        return (T)object;
    }

    public static final Thread thread(boolean bl, boolean bl2, ClassLoader classLoader, String string2, int n, Function0<Unit> object) {
        Intrinsics.checkParameterIsNotNull(object, "block");
        object = new Thread((Function0)object){
            final /* synthetic */ Function0 $block;
            {
                this.$block = function0;
            }

            public void run() {
                this.$block.invoke();
            }
        };
        if (bl2) {
            ((Thread)object).setDaemon(true);
        }
        if (n > 0) {
            ((Thread)object).setPriority(n);
        }
        if (string2 != null) {
            ((Thread)object).setName(string2);
        }
        if (classLoader != null) {
            ((Thread)object).setContextClassLoader(classLoader);
        }
        if (!bl) return (Thread)object;
        ((Thread)object).start();
        return (Thread)object;
    }

    public static /* synthetic */ Thread thread$default(boolean bl, boolean bl2, ClassLoader classLoader, String string2, int n, Function0 function0, int n2, Object object) {
        if ((n2 & 1) != 0) {
            bl = true;
        }
        if ((n2 & 2) != 0) {
            bl2 = false;
        }
        if ((n2 & 4) != 0) {
            classLoader = null;
        }
        if ((n2 & 8) != 0) {
            string2 = null;
        }
        if ((n2 & 16) == 0) return ThreadsKt.thread(bl, bl2, classLoader, string2, n, function0);
        n = -1;
        return ThreadsKt.thread(bl, bl2, classLoader, string2, n, function0);
    }
}
