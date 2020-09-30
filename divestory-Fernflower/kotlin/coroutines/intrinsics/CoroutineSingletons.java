package kotlin.coroutines.intrinsics;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/coroutines/intrinsics/CoroutineSingletons;", "", "(Ljava/lang/String;I)V", "COROUTINE_SUSPENDED", "UNDECIDED", "RESUMED", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum CoroutineSingletons {
   COROUTINE_SUSPENDED,
   RESUMED,
   UNDECIDED;

   static {
      CoroutineSingletons var0 = new CoroutineSingletons("COROUTINE_SUSPENDED", 0);
      COROUTINE_SUSPENDED = var0;
      CoroutineSingletons var1 = new CoroutineSingletons("UNDECIDED", 1);
      UNDECIDED = var1;
      CoroutineSingletons var2 = new CoroutineSingletons("RESUMED", 2);
      RESUMED = var2;
   }
}
