/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.system;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u000e\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u00a8\u0006\u0004"}, d2={"exitProcess", "", "status", "", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ProcessKt {
    private static final Void exitProcess(int n) {
        System.exit(n);
        throw (Throwable)new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }
}

