package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a:\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u00072\u0010\b\u0004\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fH\u0082\b¢\u0006\u0002\b\r\u001aD\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a]\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012¢\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001aA\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001aZ\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012¢\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\"\u001a\u0010\u0000\u001a\u00020\u00018FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\t¨\u0006\u0019"},
   d2 = {"COROUTINE_SUSPENDED", "", "COROUTINE_SUSPENDED$annotations", "()V", "getCOROUTINE_SUSPENDED", "()Ljava/lang/Object;", "buildContinuationByInvokeCall", "Lkotlin/coroutines/experimental/Continuation;", "", "T", "completion", "block", "Lkotlin/Function0;", "buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnchecked", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/coroutines/experimental/intrinsics/IntrinsicsKt"
)
class IntrinsicsKt__IntrinsicsJvmKt {
   public IntrinsicsKt__IntrinsicsJvmKt() {
   }

   // $FF: synthetic method
   public static void COROUTINE_SUSPENDED$annotations() {
   }

   private static final <T> Continuation<Unit> buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt(final Continuation<? super T> var0, final Function0<? extends Object> var1) {
      Continuation var2 = new Continuation<Unit>() {
         public CoroutineContext getContext() {
            return var0.getContext();
         }

         public void resume(Unit var1x) {
            Intrinsics.checkParameterIsNotNull(var1x, "value");
            Continuation var15 = var0;

            Throwable var10000;
            label113: {
               boolean var10001;
               Object var2;
               try {
                  var2 = var1.invoke();
                  if (var2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                     return;
                  }
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label113;
               }

               if (var15 != null) {
                  label109:
                  try {
                     var15.resume(var2);
                     return;
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label109;
                  }
               } else {
                  label105:
                  try {
                     TypeCastException var17 = new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                     throw var17;
                  } catch (Throwable var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label105;
                  }
               }
            }

            Throwable var16 = var10000;
            var15.resumeWithException(var16);
         }

         public void resumeWithException(Throwable var1x) {
            Intrinsics.checkParameterIsNotNull(var1x, "exception");
            var0.resumeWithException(var1x);
         }
      };
      return CoroutineIntrinsics.interceptContinuationIfNeeded(var0.getContext(), (Continuation)var2);
   }

   public static final <T> Continuation<Unit> createCoroutineUnchecked(final Function1<? super Continuation<? super T>, ? extends Object> var0, final Continuation<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$createCoroutineUnchecked");
      Intrinsics.checkParameterIsNotNull(var1, "completion");
      Continuation var2;
      if (!(var0 instanceof CoroutineImpl)) {
         var2 = new Continuation<Unit>() {
            public CoroutineContext getContext() {
               return var1.getContext();
            }

            public void resume(Unit var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "value");
               Continuation var33 = var1;

               Throwable var10000;
               label237: {
                  Function1 var2;
                  boolean var10001;
                  try {
                     var2 = var0;
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break label237;
                  }

                  TypeCastException var35;
                  if (var2 != null) {
                     label233: {
                        Object var34;
                        try {
                           var34 = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 1)).invoke(var1);
                           if (var34 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                              return;
                           }
                        } catch (Throwable var31) {
                           var10000 = var31;
                           var10001 = false;
                           break label233;
                        }

                        if (var33 != null) {
                           label229:
                           try {
                              var33.resume(var34);
                              return;
                           } catch (Throwable var30) {
                              var10000 = var30;
                              var10001 = false;
                              break label229;
                           }
                        } else {
                           label223:
                           try {
                              var35 = new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                              throw var35;
                           } catch (Throwable var28) {
                              var10000 = var28;
                              var10001 = false;
                              break label223;
                           }
                        }
                     }
                  } else {
                     label225:
                     try {
                        var35 = new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
                        throw var35;
                     } catch (Throwable var29) {
                        var10000 = var29;
                        var10001 = false;
                        break label225;
                     }
                  }
               }

               Throwable var36 = var10000;
               var33.resumeWithException(var36);
            }

            public void resumeWithException(Throwable var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "exception");
               var1.resumeWithException(var1x);
            }
         };
         var2 = CoroutineIntrinsics.interceptContinuationIfNeeded(var1.getContext(), (Continuation)var2);
      } else {
         var2 = ((CoroutineImpl)var0).create(var1);
         if (var2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
         }

         var2 = ((CoroutineImpl)var2).getFacade();
      }

      return var2;
   }

   public static final <R, T> Continuation<Unit> createCoroutineUnchecked(final Function2<? super R, ? super Continuation<? super T>, ? extends Object> var0, final R var1, final Continuation<? super T> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$createCoroutineUnchecked");
      Intrinsics.checkParameterIsNotNull(var2, "completion");
      Continuation var3;
      if (!(var0 instanceof CoroutineImpl)) {
         var3 = new Continuation<Unit>() {
            public CoroutineContext getContext() {
               return var2.getContext();
            }

            public void resume(Unit var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "value");
               Continuation var33 = var2;

               Throwable var10000;
               label237: {
                  Function2 var2x;
                  boolean var10001;
                  try {
                     var2x = var0;
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break label237;
                  }

                  TypeCastException var35;
                  if (var2x != null) {
                     label233: {
                        Object var34;
                        try {
                           var34 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2x, 2)).invoke(var1, var2);
                           if (var34 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                              return;
                           }
                        } catch (Throwable var31) {
                           var10000 = var31;
                           var10001 = false;
                           break label233;
                        }

                        if (var33 != null) {
                           label229:
                           try {
                              var33.resume(var34);
                              return;
                           } catch (Throwable var30) {
                              var10000 = var30;
                              var10001 = false;
                              break label229;
                           }
                        } else {
                           label223:
                           try {
                              var35 = new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                              throw var35;
                           } catch (Throwable var28) {
                              var10000 = var28;
                              var10001 = false;
                              break label223;
                           }
                        }
                     }
                  } else {
                     label225:
                     try {
                        var35 = new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
                        throw var35;
                     } catch (Throwable var29) {
                        var10000 = var29;
                        var10001 = false;
                        break label225;
                     }
                  }
               }

               Throwable var36 = var10000;
               var33.resumeWithException(var36);
            }

            public void resumeWithException(Throwable var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "exception");
               var2.resumeWithException(var1x);
            }
         };
         var3 = CoroutineIntrinsics.interceptContinuationIfNeeded(var2.getContext(), (Continuation)var3);
      } else {
         var3 = ((CoroutineImpl)var0).create(var1, var2);
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
         }

         var3 = ((CoroutineImpl)var3).getFacade();
      }

      return var3;
   }

   public static final Object getCOROUTINE_SUSPENDED() {
      return kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
   }

   private static final <T> Object startCoroutineUninterceptedOrReturn(Function1<? super Continuation<? super T>, ? extends Object> var0, Continuation<? super T> var1) {
      if (var0 != null) {
         return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1)).invoke(var1);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
      }
   }

   private static final <R, T> Object startCoroutineUninterceptedOrReturn(Function2<? super R, ? super Continuation<? super T>, ? extends Object> var0, R var1, Continuation<? super T> var2) {
      if (var0 != null) {
         return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 2)).invoke(var1, var2);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
      }
   }
}
