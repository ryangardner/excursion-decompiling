/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.reflect;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.reflect.KCallable;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003R\u001a\u0010\u0004\u001a\u00020\u00058&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\bR\u001a\u0010\t\u001a\u00020\u00058&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u0007\u001a\u0004\b\t\u0010\bR\u001a\u0010\u000b\u001a\u00020\u00058&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\f\u0010\u0007\u001a\u0004\b\u000b\u0010\bR\u001a\u0010\r\u001a\u00020\u00058&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u000e\u0010\u0007\u001a\u0004\b\r\u0010\bR\u001a\u0010\u000f\u001a\u00020\u00058&X\u00a7\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u000f\u0010\b\u00a8\u0006\u0011"}, d2={"Lkotlin/reflect/KFunction;", "R", "Lkotlin/reflect/KCallable;", "Lkotlin/Function;", "isExternal", "", "isExternal$annotations", "()V", "()Z", "isInfix", "isInfix$annotations", "isInline", "isInline$annotations", "isOperator", "isOperator$annotations", "isSuspend", "isSuspend$annotations", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public interface KFunction<R>
extends KCallable<R>,
Function<R> {
    public boolean isExternal();

    public boolean isInfix();

    public boolean isInline();

    public boolean isOperator();

    @Override
    public boolean isSuspend();

    @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
    public static final class DefaultImpls {
        public static /* synthetic */ void isExternal$annotations() {
        }

        public static /* synthetic */ void isInfix$annotations() {
        }

        public static /* synthetic */ void isInline$annotations() {
        }

        public static /* synthetic */ void isOperator$annotations() {
        }

        public static /* synthetic */ void isSuspend$annotations() {
        }
    }

}

