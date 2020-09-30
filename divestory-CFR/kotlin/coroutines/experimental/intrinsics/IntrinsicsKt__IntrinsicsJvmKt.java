/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall
 *  kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation
 *  kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$
 *  kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined
 *  kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall
 *  kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt
 */
package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$;
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a:\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u00072\u0010\b\u0004\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fH\u0082\b\u00a2\u0006\u0002\b\r\u001aD\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a]\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012\u00a2\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u001aA\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u001aZ\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012\u00a2\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018\"\u001a\u0010\u0000\u001a\u00020\u00018FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\t\u00a8\u0006\u0019"}, d2={"COROUTINE_SUSPENDED", "", "COROUTINE_SUSPENDED$annotations", "()V", "getCOROUTINE_SUSPENDED", "()Ljava/lang/Object;", "buildContinuationByInvokeCall", "Lkotlin/coroutines/experimental/Continuation;", "", "T", "completion", "block", "Lkotlin/Function0;", "buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnchecked", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/coroutines/experimental/intrinsics/IntrinsicsKt")
class IntrinsicsKt__IntrinsicsJvmKt {
    public static /* synthetic */ void COROUTINE_SUSPENDED$annotations() {
    }

    private static final <T> Continuation<Unit> buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt(Continuation<? super T> continuation2, Function0<? extends Object> object) {
        object = new Continuation<Unit>(continuation2, object){
            final /* synthetic */ Function0 $block;
            final /* synthetic */ Continuation $completion;
            {
                this.$completion = continuation2;
                this.$block = function0;
            }

            public CoroutineContext getContext() {
                return this.$completion.getContext();
            }

            public void resume(Unit object) {
                Intrinsics.checkParameterIsNotNull(object, "value");
                object = this.$completion;
                try {
                    Object object2 = this.$block.invoke();
                    if (object2 == kotlin.coroutines.experimental.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()) return;
                    if (object != null) {
                        object.resume(object2);
                        return;
                    }
                    object2 = new Object("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                    throw object2;
                }
                catch (java.lang.Throwable throwable) {
                    object.resumeWithException(throwable);
                }
            }

            public void resumeWithException(java.lang.Throwable throwable) {
                Intrinsics.checkParameterIsNotNull(throwable, "exception");
                this.$completion.resumeWithException(throwable);
            }
        };
        return CoroutineIntrinsics.interceptContinuationIfNeeded(continuation2.getContext(), (Continuation)object);
    }

    public static final <T> Continuation<Unit> createCoroutineUnchecked(Function1<? super Continuation<? super T>, ? extends Object> continuation2, Continuation<? super T> continuation3) {
        Intrinsics.checkParameterIsNotNull(continuation2, "$this$createCoroutineUnchecked");
        Intrinsics.checkParameterIsNotNull(continuation3, "completion");
        if (!(continuation2 instanceof CoroutineImpl)) {
            continuation2 = new Continuation<Unit>(continuation3, continuation2, continuation3){
                final /* synthetic */ Continuation $completion;
                final /* synthetic */ Continuation $completion$inlined;
                final /* synthetic */ Function1 $this_createCoroutineUnchecked$inlined;
                {
                    this.$completion = continuation2;
                    this.$this_createCoroutineUnchecked$inlined = function1;
                    this.$completion$inlined = continuation3;
                }

                public CoroutineContext getContext() {
                    return this.$completion.getContext();
                }

                public void resume(Unit object) {
                    Intrinsics.checkParameterIsNotNull(object, "value");
                    object = this.$completion;
                    try {
                        Object object2 = this.$this_createCoroutineUnchecked$inlined;
                        if (object2 == null) {
                            object2 = new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
                            throw object2;
                        }
                        if ((object2 = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(object2, 1)).invoke(this.$completion$inlined)) == kotlin.coroutines.experimental.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()) return;
                        if (object != null) {
                            object.resume(object2);
                            return;
                        }
                        object2 = new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                        throw object2;
                    }
                    catch (java.lang.Throwable throwable) {
                        object.resumeWithException(throwable);
                    }
                }

                public void resumeWithException(java.lang.Throwable throwable) {
                    Intrinsics.checkParameterIsNotNull(throwable, "exception");
                    this.$completion.resumeWithException(throwable);
                }
            };
            return CoroutineIntrinsics.interceptContinuationIfNeeded(continuation3.getContext(), (Continuation)continuation2);
        }
        if ((continuation2 = ((CoroutineImpl)continuation2).create(continuation3)) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
        return ((CoroutineImpl)continuation2).getFacade();
    }

    public static final <R, T> Continuation<Unit> createCoroutineUnchecked(Function2<? super R, ? super Continuation<? super T>, ? extends Object> continuation2, R r, Continuation<? super T> continuation3) {
        Intrinsics.checkParameterIsNotNull(continuation2, "$this$createCoroutineUnchecked");
        Intrinsics.checkParameterIsNotNull(continuation3, "completion");
        if (!(continuation2 instanceof CoroutineImpl)) {
            continuation2 = new Continuation<Unit>(continuation3, continuation2, r, continuation3){
                final /* synthetic */ Continuation $completion;
                final /* synthetic */ Continuation $completion$inlined;
                final /* synthetic */ Object $receiver$inlined;
                final /* synthetic */ Function2 $this_createCoroutineUnchecked$inlined;
                {
                    this.$completion = continuation2;
                    this.$this_createCoroutineUnchecked$inlined = function2;
                    this.$receiver$inlined = object;
                    this.$completion$inlined = continuation3;
                }

                public CoroutineContext getContext() {
                    return this.$completion.getContext();
                }

                public void resume(Unit object) {
                    Intrinsics.checkParameterIsNotNull(object, "value");
                    object = this.$completion;
                    try {
                        Object object2 = this.$this_createCoroutineUnchecked$inlined;
                        if (object2 == null) {
                            object2 = new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
                            throw object2;
                        }
                        if ((object2 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(object2, 2)).invoke(this.$receiver$inlined, this.$completion$inlined)) == kotlin.coroutines.experimental.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()) return;
                        if (object != null) {
                            object.resume(object2);
                            return;
                        }
                        object2 = new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                        throw object2;
                    }
                    catch (java.lang.Throwable throwable) {
                        object.resumeWithException(throwable);
                    }
                }

                public void resumeWithException(java.lang.Throwable throwable) {
                    Intrinsics.checkParameterIsNotNull(throwable, "exception");
                    this.$completion.resumeWithException(throwable);
                }
            };
            return CoroutineIntrinsics.interceptContinuationIfNeeded(continuation3.getContext(), (Continuation)continuation2);
        }
        if ((continuation2 = ((CoroutineImpl)continuation2).create(r, continuation3)) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
        return ((CoroutineImpl)continuation2).getFacade();
    }

    public static final Object getCOROUTINE_SUSPENDED() {
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    private static final <T> Object startCoroutineUninterceptedOrReturn(Function1<? super Continuation<? super T>, ? extends Object> function1, Continuation<? super T> continuation2) {
        if (function1 == null) throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
        return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(continuation2);
    }

    private static final <R, T> Object startCoroutineUninterceptedOrReturn(Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2, R r, Continuation<? super T> continuation2) {
        if (function2 == null) throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
        return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(r, continuation2);
    }
}

