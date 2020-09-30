/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction
 *  kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$
 *  kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined
 *  kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction
 *  kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt
 */
package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b\u00a2\u0006\u0002\b\b\u001aD\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\n\u001a]\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012\u001aZ\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2={"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/coroutines/intrinsics/IntrinsicsKt")
class IntrinsicsKt__IntrinsicsJvmKt {
    private static final <T> Continuation<Unit> createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(Continuation<? super T> continuation2, Function1<? super Continuation<? super T>, ? extends Object> function1) {
        CoroutineContext coroutineContext = continuation2.getContext();
        if (coroutineContext == EmptyCoroutineContext.INSTANCE) {
            if (continuation2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            return new RestrictedContinuationImpl(function1, continuation2, continuation2){
                final /* synthetic */ Function1 $block;
                final /* synthetic */ Continuation $completion;
                private int label;
                {
                    this.$block = function1;
                    this.$completion = continuation2;
                    super(continuation3);
                }

                protected Object invokeSuspend(Object object) {
                    int n = this.label;
                    if (n != 0) {
                        if (n != 1) throw (java.lang.Throwable)new java.lang.IllegalStateException("This coroutine had already completed".toString());
                        this.label = 2;
                        kotlin.ResultKt.throwOnFailure(object);
                        return object;
                    }
                    this.label = 1;
                    kotlin.ResultKt.throwOnFailure(object);
                    return this.$block.invoke(this);
                }
            };
        }
        if (continuation2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        return new ContinuationImpl(function1, continuation2, coroutineContext, continuation2, coroutineContext){
            final /* synthetic */ Function1 $block;
            final /* synthetic */ Continuation $completion;
            final /* synthetic */ CoroutineContext $context;
            private int label;
            {
                this.$block = function1;
                this.$completion = continuation2;
                this.$context = coroutineContext;
                super(continuation3, coroutineContext2);
            }

            protected Object invokeSuspend(Object object) {
                int n = this.label;
                if (n != 0) {
                    if (n != 1) throw (java.lang.Throwable)new java.lang.IllegalStateException("This coroutine had already completed".toString());
                    this.label = 2;
                    kotlin.ResultKt.throwOnFailure(object);
                    return object;
                }
                this.label = 1;
                kotlin.ResultKt.throwOnFailure(object);
                return this.$block.invoke(this);
            }
        };
    }

    public static final <T> Continuation<Unit> createCoroutineUnintercepted(Function1<? super Continuation<? super T>, ? extends Object> object, Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$createCoroutineUnintercepted");
        Intrinsics.checkParameterIsNotNull(continuation2, "completion");
        continuation2 = DebugProbesKt.probeCoroutineCreated(continuation2);
        if (object instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl)object).create(continuation2);
        }
        CoroutineContext coroutineContext = continuation2.getContext();
        if (coroutineContext == EmptyCoroutineContext.INSTANCE) {
            if (continuation2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            return new RestrictedContinuationImpl(continuation2, continuation2, (Function1)object){
                final /* synthetic */ Continuation $completion;
                final /* synthetic */ Function1 $this_createCoroutineUnintercepted$inlined;
                private int label;
                {
                    this.$completion = continuation2;
                    this.$this_createCoroutineUnintercepted$inlined = function1;
                    super(continuation3);
                }

                protected Object invokeSuspend(Object object) {
                    int n = this.label;
                    if (n != 0) {
                        if (n != 1) throw (java.lang.Throwable)new java.lang.IllegalStateException("This coroutine had already completed".toString());
                        this.label = 2;
                        kotlin.ResultKt.throwOnFailure(object);
                        return object;
                    }
                    this.label = 1;
                    kotlin.ResultKt.throwOnFailure(object);
                    Continuation continuation2 = this;
                    object = this.$this_createCoroutineUnintercepted$inlined;
                    if (object == null) throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                    return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(object, 1)).invoke(continuation2);
                }
            };
        }
        if (continuation2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        return new ContinuationImpl(continuation2, coroutineContext, continuation2, coroutineContext, (Function1)object){
            final /* synthetic */ Continuation $completion;
            final /* synthetic */ CoroutineContext $context;
            final /* synthetic */ Function1 $this_createCoroutineUnintercepted$inlined;
            private int label;
            {
                this.$completion = continuation2;
                this.$context = coroutineContext;
                this.$this_createCoroutineUnintercepted$inlined = function1;
                super(continuation3, coroutineContext2);
            }

            protected Object invokeSuspend(Object object) {
                int n = this.label;
                if (n != 0) {
                    if (n != 1) throw (java.lang.Throwable)new java.lang.IllegalStateException("This coroutine had already completed".toString());
                    this.label = 2;
                    kotlin.ResultKt.throwOnFailure(object);
                    return object;
                }
                this.label = 1;
                kotlin.ResultKt.throwOnFailure(object);
                Continuation continuation2 = this;
                object = this.$this_createCoroutineUnintercepted$inlined;
                if (object == null) throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(object, 1)).invoke(continuation2);
            }
        };
    }

    public static final <R, T> Continuation<Unit> createCoroutineUnintercepted(Function2<? super R, ? super Continuation<? super T>, ? extends Object> object, R r, Continuation<? super T> object2) {
        Intrinsics.checkParameterIsNotNull(object, "$this$createCoroutineUnintercepted");
        Intrinsics.checkParameterIsNotNull(object2, "completion");
        Continuation<T> continuation2 = DebugProbesKt.probeCoroutineCreated(object2);
        if (object instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl)object).create(r, continuation2);
        }
        object2 = continuation2.getContext();
        if (object2 == EmptyCoroutineContext.INSTANCE) {
            if (continuation2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            return new RestrictedContinuationImpl(continuation2, continuation2, (Function2)object, r){
                final /* synthetic */ Continuation $completion;
                final /* synthetic */ Object $receiver$inlined;
                final /* synthetic */ Function2 $this_createCoroutineUnintercepted$inlined;
                private int label;
                {
                    this.$completion = continuation2;
                    this.$this_createCoroutineUnintercepted$inlined = function2;
                    this.$receiver$inlined = object;
                    super(continuation3);
                }

                protected Object invokeSuspend(Object object) {
                    int n = this.label;
                    if (n != 0) {
                        if (n != 1) throw (java.lang.Throwable)new java.lang.IllegalStateException("This coroutine had already completed".toString());
                        this.label = 2;
                        kotlin.ResultKt.throwOnFailure(object);
                        return object;
                    }
                    this.label = 1;
                    kotlin.ResultKt.throwOnFailure(object);
                    object = this;
                    Function2 function2 = this.$this_createCoroutineUnintercepted$inlined;
                    if (function2 == null) throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                    return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(this.$receiver$inlined, object);
                }
            };
        }
        if (continuation2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        return new ContinuationImpl(continuation2, (CoroutineContext)object2, continuation2, (CoroutineContext)object2, (Function2)object, r){
            final /* synthetic */ Continuation $completion;
            final /* synthetic */ CoroutineContext $context;
            final /* synthetic */ Object $receiver$inlined;
            final /* synthetic */ Function2 $this_createCoroutineUnintercepted$inlined;
            private int label;
            {
                this.$completion = continuation2;
                this.$context = coroutineContext;
                this.$this_createCoroutineUnintercepted$inlined = function2;
                this.$receiver$inlined = object;
                super(continuation3, coroutineContext2);
            }

            protected Object invokeSuspend(Object object) {
                int n = this.label;
                if (n != 0) {
                    if (n != 1) throw (java.lang.Throwable)new java.lang.IllegalStateException("This coroutine had already completed".toString());
                    this.label = 2;
                    kotlin.ResultKt.throwOnFailure(object);
                    return object;
                }
                this.label = 1;
                kotlin.ResultKt.throwOnFailure(object);
                object = this;
                Function2 function2 = this.$this_createCoroutineUnintercepted$inlined;
                if (function2 == null) throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(this.$receiver$inlined, object);
            }
        };
    }

    public static final <T> Continuation<T> intercepted(Continuation<? super T> continuation2) {
        Intrinsics.checkParameterIsNotNull(continuation2, "$this$intercepted");
        Continuation<Object> continuation3 = !(continuation2 instanceof ContinuationImpl) ? null : continuation2;
        Continuation<Object> continuation4 = (ContinuationImpl)continuation3;
        continuation3 = continuation2;
        if (continuation4 == null) return continuation3;
        continuation4 = ((ContinuationImpl)continuation4).intercepted();
        continuation3 = continuation2;
        if (continuation4 == null) return continuation3;
        return continuation4;
    }

    private static final <T> Object startCoroutineUninterceptedOrReturn(Function1<? super Continuation<? super T>, ? extends Object> function1, Continuation<? super T> continuation2) {
        if (function1 == null) throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(continuation2);
    }

    private static final <R, T> Object startCoroutineUninterceptedOrReturn(Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2, R r, Continuation<? super T> continuation2) {
        if (function2 == null) throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(r, continuation2);
    }
}

