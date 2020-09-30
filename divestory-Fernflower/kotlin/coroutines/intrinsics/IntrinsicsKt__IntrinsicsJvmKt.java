package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b¢\u0006\u0002\b\b\u001aD\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a]\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aZ\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"},
   d2 = {"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/coroutines/intrinsics/IntrinsicsKt"
)
class IntrinsicsKt__IntrinsicsJvmKt {
   public IntrinsicsKt__IntrinsicsJvmKt() {
   }

   private static final <T> Continuation<Unit> createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(final Continuation<? super T> var0, final Function1<? super Continuation<? super T>, ? extends Object> var1) {
      final CoroutineContext var2 = var0.getContext();
      if (var2 == EmptyCoroutineContext.INSTANCE) {
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
         }

         var0 = (Continuation)(new RestrictedContinuationImpl(var0) {
            private int label;

            protected Object invokeSuspend(Object var1x) {
               int var2 = this.label;
               if (var2 != 0) {
                  if (var2 != 1) {
                     throw (Throwable)(new IllegalStateException("This coroutine had already completed".toString()));
                  }

                  this.label = 2;
                  ResultKt.throwOnFailure(var1x);
               } else {
                  this.label = 1;
                  ResultKt.throwOnFailure(var1x);
                  var1x = var1.invoke(this);
               }

               return var1x;
            }
         });
      } else {
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
         }

         var0 = (Continuation)(new ContinuationImpl(var0, var2) {
            private int label;

            protected Object invokeSuspend(Object var1x) {
               int var2x = this.label;
               if (var2x != 0) {
                  if (var2x != 1) {
                     throw (Throwable)(new IllegalStateException("This coroutine had already completed".toString()));
                  }

                  this.label = 2;
                  ResultKt.throwOnFailure(var1x);
               } else {
                  this.label = 1;
                  ResultKt.throwOnFailure(var1x);
                  var1x = var1.invoke(this);
               }

               return var1x;
            }
         });
      }

      return var0;
   }

   public static final <T> Continuation<Unit> createCoroutineUnintercepted(final Function1<? super Continuation<? super T>, ? extends Object> var0, final Continuation<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$createCoroutineUnintercepted");
      Intrinsics.checkParameterIsNotNull(var1, "completion");
      var1 = DebugProbesKt.probeCoroutineCreated(var1);
      Continuation var3;
      if (var0 instanceof BaseContinuationImpl) {
         var3 = ((BaseContinuationImpl)var0).create(var1);
      } else {
         final CoroutineContext var2 = var1.getContext();
         if (var2 == EmptyCoroutineContext.INSTANCE) {
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var3 = (Continuation)(new RestrictedContinuationImpl(var1) {
               private int label;

               protected Object invokeSuspend(Object var1x) {
                  int var2 = this.label;
                  if (var2 != 0) {
                     if (var2 != 1) {
                        throw (Throwable)(new IllegalStateException("This coroutine had already completed".toString()));
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1x);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1x);
                     Continuation var3 = (Continuation)this;
                     Function1 var4 = var0;
                     if (var4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                     }

                     var1x = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var4, 1)).invoke(var3);
                  }

                  return var1x;
               }
            });
         } else {
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var3 = (Continuation)(new ContinuationImpl(var1, var2) {
               private int label;

               protected Object invokeSuspend(Object var1x) {
                  int var2x = this.label;
                  if (var2x != 0) {
                     if (var2x != 1) {
                        throw (Throwable)(new IllegalStateException("This coroutine had already completed".toString()));
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1x);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1x);
                     Continuation var3 = (Continuation)this;
                     Function1 var4 = var0;
                     if (var4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                     }

                     var1x = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var4, 1)).invoke(var3);
                  }

                  return var1x;
               }
            });
         }
      }

      return var3;
   }

   public static final <R, T> Continuation<Unit> createCoroutineUnintercepted(final Function2<? super R, ? super Continuation<? super T>, ? extends Object> var0, final R var1, Continuation<? super T> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$createCoroutineUnintercepted");
      Intrinsics.checkParameterIsNotNull(var2, "completion");
      final Continuation var3 = DebugProbesKt.probeCoroutineCreated(var2);
      Continuation var4;
      if (var0 instanceof BaseContinuationImpl) {
         var4 = ((BaseContinuationImpl)var0).create(var1, var3);
      } else {
         final CoroutineContext var5 = var3.getContext();
         if (var5 == EmptyCoroutineContext.INSTANCE) {
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var4 = (Continuation)(new RestrictedContinuationImpl(var3) {
               private int label;

               protected Object invokeSuspend(Object var1x) {
                  int var2 = this.label;
                  if (var2 != 0) {
                     if (var2 != 1) {
                        throw (Throwable)(new IllegalStateException("This coroutine had already completed".toString()));
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1x);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1x);
                     Continuation var4 = (Continuation)this;
                     Function2 var3x = var0;
                     if (var3x == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                     }

                     var1x = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var3x, 2)).invoke(var1, var4);
                  }

                  return var1x;
               }
            });
         } else {
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var4 = (Continuation)(new ContinuationImpl(var3, var5) {
               private int label;

               protected Object invokeSuspend(Object var1x) {
                  int var2 = this.label;
                  if (var2 != 0) {
                     if (var2 != 1) {
                        throw (Throwable)(new IllegalStateException("This coroutine had already completed".toString()));
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1x);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1x);
                     Continuation var4 = (Continuation)this;
                     Function2 var3x = var0;
                     if (var3x == null) {
                        throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
                     }

                     var1x = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var3x, 2)).invoke(var1, var4);
                  }

                  return var1x;
               }
            });
         }
      }

      return var4;
   }

   public static final <T> Continuation<T> intercepted(Continuation<? super T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$intercepted");
      Continuation var1;
      if (!(var0 instanceof ContinuationImpl)) {
         var1 = null;
      } else {
         var1 = var0;
      }

      ContinuationImpl var2 = (ContinuationImpl)var1;
      var1 = var0;
      if (var2 != null) {
         Continuation var3 = var2.intercepted();
         var1 = var0;
         if (var3 != null) {
            var1 = var3;
         }
      }

      return var1;
   }

   private static final <T> Object startCoroutineUninterceptedOrReturn(Function1<? super Continuation<? super T>, ? extends Object> var0, Continuation<? super T> var1) {
      if (var0 != null) {
         return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1)).invoke(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
      }
   }

   private static final <R, T> Object startCoroutineUninterceptedOrReturn(Function2<? super R, ? super Continuation<? super T>, ? extends Object> var0, R var1, Continuation<? super T> var2) {
      if (var0 != null) {
         return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 2)).invoke(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
      }
   }
}
