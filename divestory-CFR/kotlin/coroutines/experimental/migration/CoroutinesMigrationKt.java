/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.EmptyCoroutineContext;
import kotlin.coroutines.experimental.migration.ContextMigration;
import kotlin.coroutines.experimental.migration.ContinuationInterceptorMigration;
import kotlin.coroutines.experimental.migration.ContinuationMigration;
import kotlin.coroutines.experimental.migration.ExperimentalContextMigration;
import kotlin.coroutines.experimental.migration.ExperimentalContinuationInterceptorMigration;
import kotlin.coroutines.experimental.migration.ExperimentalContinuationMigration;
import kotlin.coroutines.experimental.migration.ExperimentalSuspendFunction0Migration;
import kotlin.coroutines.experimental.migration.ExperimentalSuspendFunction1Migration;
import kotlin.coroutines.experimental.migration.ExperimentalSuspendFunction2Migration;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0005H\u0007\u001a\f\u0010\f\u001a\u00020\t*\u00020\bH\u0007\u001a^\u0010\r\u001a\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u0011*\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eH\u0000\u001aL\u0010\r\u001a\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0011*\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013H\u0000\u001a:\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\"\u0004\b\u0000\u0010\u0011*\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014H\u0000\u00a8\u0006\u0015"}, d2={"toContinuation", "Lkotlin/coroutines/Continuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "toContinuationInterceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "toCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "toExperimentalContinuation", "toExperimentalContinuationInterceptor", "toExperimentalCoroutineContext", "toExperimentalSuspendFunction", "Lkotlin/Function3;", "T1", "T2", "R", "", "Lkotlin/Function2;", "Lkotlin/Function1;", "kotlin-stdlib-coroutines"}, k=2, mv={1, 1, 16})
public final class CoroutinesMigrationKt {
    public static final <T> Continuation<T> toContinuation(kotlin.coroutines.experimental.Continuation<? super T> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toContinuation");
        Object object2 = !(object instanceof ExperimentalContinuationMigration) ? null : object;
        object2 = (ExperimentalContinuationMigration)object2;
        if (object2 == null) return new ContinuationMigration(object);
        if ((object2 = ((ExperimentalContinuationMigration)object2).getContinuation()) == null) return new ContinuationMigration(object);
        return object2;
    }

    public static final ContinuationInterceptor toContinuationInterceptor(kotlin.coroutines.experimental.ContinuationInterceptor object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toContinuationInterceptor");
        Object object2 = !(object instanceof ExperimentalContinuationInterceptorMigration) ? null : object;
        object2 = (ExperimentalContinuationInterceptorMigration)object2;
        if (object2 == null) return new ContinuationInterceptorMigration((kotlin.coroutines.experimental.ContinuationInterceptor)object);
        if ((object2 = ((ExperimentalContinuationInterceptorMigration)object2).getInterceptor()) == null) return new ContinuationInterceptorMigration((kotlin.coroutines.experimental.ContinuationInterceptor)object);
        return object2;
    }

    public static final CoroutineContext toCoroutineContext(kotlin.coroutines.experimental.CoroutineContext object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toCoroutineContext");
        kotlin.coroutines.experimental.ContinuationInterceptor continuationInterceptor = (kotlin.coroutines.experimental.ContinuationInterceptor)object.get(kotlin.coroutines.experimental.ContinuationInterceptor.Key);
        ExperimentalContextMigration experimentalContextMigration = (ExperimentalContextMigration)object.get(ExperimentalContextMigration.Key);
        kotlin.coroutines.experimental.CoroutineContext coroutineContext = object.minusKey(kotlin.coroutines.experimental.ContinuationInterceptor.Key).minusKey(ExperimentalContextMigration.Key);
        if (experimentalContextMigration == null || (object = experimentalContextMigration.getContext()) == null) {
            object = kotlin.coroutines.EmptyCoroutineContext.INSTANCE;
        }
        if (coroutineContext != EmptyCoroutineContext.INSTANCE) {
            object = object.plus(new ContextMigration(coroutineContext));
        }
        if (continuationInterceptor != null) return object.plus(CoroutinesMigrationKt.toContinuationInterceptor(continuationInterceptor));
        return object;
    }

    public static final <T> kotlin.coroutines.experimental.Continuation<T> toExperimentalContinuation(Continuation<? super T> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toExperimentalContinuation");
        Object object2 = !(object instanceof ContinuationMigration) ? null : object;
        object2 = (ContinuationMigration)object2;
        if (object2 == null) return new ExperimentalContinuationMigration(object);
        if ((object2 = ((ContinuationMigration)object2).getContinuation()) == null) return new ExperimentalContinuationMigration(object);
        return object2;
    }

    public static final kotlin.coroutines.experimental.ContinuationInterceptor toExperimentalContinuationInterceptor(ContinuationInterceptor object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toExperimentalContinuationInterceptor");
        Object object2 = !(object instanceof ContinuationInterceptorMigration) ? null : object;
        object2 = (ContinuationInterceptorMigration)object2;
        if (object2 == null) return new ExperimentalContinuationInterceptorMigration((ContinuationInterceptor)object);
        if ((object2 = ((ContinuationInterceptorMigration)object2).getInterceptor()) == null) return new ExperimentalContinuationInterceptorMigration((ContinuationInterceptor)object);
        return object2;
    }

    public static final kotlin.coroutines.experimental.CoroutineContext toExperimentalCoroutineContext(CoroutineContext object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toExperimentalCoroutineContext");
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor)object.get(ContinuationInterceptor.Key);
        ContextMigration contextMigration = (ContextMigration)object.get(ContextMigration.Key);
        CoroutineContext coroutineContext = object.minusKey(ContinuationInterceptor.Key).minusKey(ContextMigration.Key);
        if (contextMigration == null || (object = contextMigration.getContext()) == null) {
            object = EmptyCoroutineContext.INSTANCE;
        }
        if (coroutineContext != kotlin.coroutines.EmptyCoroutineContext.INSTANCE) {
            object = object.plus(new ExperimentalContextMigration(coroutineContext));
        }
        if (continuationInterceptor != null) return object.plus(CoroutinesMigrationKt.toExperimentalContinuationInterceptor(continuationInterceptor));
        return object;
    }

    public static final <R> Function1<kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(Function1<? super Continuation<? super R>, ? extends Object> function1) {
        Intrinsics.checkParameterIsNotNull(function1, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction0Migration(function1);
    }

    public static final <T1, R> Function2<T1, kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(Function2<? super T1, ? super Continuation<? super R>, ? extends Object> function2) {
        Intrinsics.checkParameterIsNotNull(function2, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction1Migration(function2);
    }

    public static final <T1, T2, R> Function3<T1, T2, kotlin.coroutines.experimental.Continuation<? super R>, Object> toExperimentalSuspendFunction(Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3) {
        Intrinsics.checkParameterIsNotNull(function3, "$this$toExperimentalSuspendFunction");
        return new ExperimentalSuspendFunction2Migration(function3);
    }
}

