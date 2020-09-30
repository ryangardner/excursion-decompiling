package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b!\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005B!\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J\b\u0010\r\u001a\u00020\u000eH\u0014R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "completion", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "_context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;)V", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "intercepted", "releaseIntercepted", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class ContinuationImpl extends BaseContinuationImpl {
   private final CoroutineContext _context;
   private transient Continuation<Object> intercepted;

   public ContinuationImpl(Continuation<Object> var1) {
      CoroutineContext var2;
      if (var1 != null) {
         var2 = var1.getContext();
      } else {
         var2 = null;
      }

      this(var1, var2);
   }

   public ContinuationImpl(Continuation<Object> var1, CoroutineContext var2) {
      super(var1);
      this._context = var2;
   }

   public CoroutineContext getContext() {
      CoroutineContext var1 = this._context;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   public final Continuation<Object> intercepted() {
      Continuation var1 = this.intercepted;
      if (var1 == null) {
         label15: {
            ContinuationInterceptor var2 = (ContinuationInterceptor)this.getContext().get((CoroutineContext.Key)ContinuationInterceptor.Key);
            if (var2 != null) {
               var1 = var2.interceptContinuation((Continuation)this);
               if (var1 != null) {
                  break label15;
               }
            }

            var1 = (Continuation)this;
         }

         this.intercepted = var1;
      }

      return var1;
   }

   protected void releaseIntercepted() {
      Continuation var1 = this.intercepted;
      if (var1 != null && var1 != (ContinuationImpl)this) {
         CoroutineContext.Element var2 = this.getContext().get((CoroutineContext.Key)ContinuationInterceptor.Key);
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         ((ContinuationInterceptor)var2).releaseInterceptedContinuation(var1);
      }

      this.intercepted = (Continuation)CompletedContinuation.INSTANCE;
   }
}
