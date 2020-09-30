package kotlin.coroutines.jvm.internal;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b!\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u00032\u00020\u0004B\u0017\u0012\u0010\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\u0002\u0010\u0006J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\"\u0010\u0011\u001a\u0004\u0018\u00010\u00022\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0013H$ø\u0001\u0000¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\rH\u0014J\u001e\u0010\u0016\u001a\u00020\r2\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0013ø\u0001\u0000¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"},
   d2 = {"Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Ljava/io/Serializable;", "completion", "(Lkotlin/coroutines/Continuation;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getCompletion", "()Lkotlin/coroutines/Continuation;", "create", "", "value", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "invokeSuspend", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "releaseIntercepted", "resumeWith", "(Ljava/lang/Object;)V", "toString", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class BaseContinuationImpl implements Continuation<Object>, CoroutineStackFrame, Serializable {
   private final Continuation<Object> completion;

   public BaseContinuationImpl(Continuation<Object> var1) {
      this.completion = var1;
   }

   public Continuation<Unit> create(Object var1, Continuation<?> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "completion");
      throw (Throwable)(new UnsupportedOperationException("create(Any?;Continuation) has not been overridden"));
   }

   public Continuation<Unit> create(Continuation<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "completion");
      throw (Throwable)(new UnsupportedOperationException("create(Continuation) has not been overridden"));
   }

   public CoroutineStackFrame getCallerFrame() {
      Continuation var1 = this.completion;
      Continuation var2 = var1;
      if (!(var1 instanceof CoroutineStackFrame)) {
         var2 = null;
      }

      return (CoroutineStackFrame)var2;
   }

   public final Continuation<Object> getCompletion() {
      return this.completion;
   }

   public StackTraceElement getStackTraceElement() {
      return DebugMetadataKt.getStackTraceElement(this);
   }

   protected abstract Object invokeSuspend(Object var1);

   protected void releaseIntercepted() {
   }

   public final void resumeWith(Object var1) {
      BaseContinuationImpl var2 = (BaseContinuationImpl)this;

      while(true) {
         DebugProbesKt.probeCoroutineResumed((Continuation)var2);
         Continuation var3 = var2.completion;
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         label55:
         try {
            var1 = var2.invokeSuspend(var1);
            if (var1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               return;
            }

            Result.Companion var4 = Result.Companion;
            var1 = Result.constructor-impl(var1);
         } catch (Throwable var6) {
            Result.Companion var7 = Result.Companion;
            var1 = Result.constructor-impl(ResultKt.createFailure(var6));
            break label55;
         }

         var2.releaseIntercepted();
         if (!(var3 instanceof BaseContinuationImpl)) {
            var3.resumeWith(var1);
            return;
         }

         var2 = (BaseContinuationImpl)var3;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Continuation at ");
      Object var2 = this.getStackTraceElement();
      if (var2 == null) {
         var2 = this.getClass().getName();
      }

      var1.append((Serializable)var2);
      return var1.toString();
   }
}
