package kotlin.contracts;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/contracts/InvocationKind;", "", "(Ljava/lang/String;I)V", "AT_MOST_ONCE", "AT_LEAST_ONCE", "EXACTLY_ONCE", "UNKNOWN", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum InvocationKind {
   AT_LEAST_ONCE,
   AT_MOST_ONCE,
   EXACTLY_ONCE,
   UNKNOWN;

   static {
      InvocationKind var0 = new InvocationKind("AT_MOST_ONCE", 0);
      AT_MOST_ONCE = var0;
      InvocationKind var1 = new InvocationKind("AT_LEAST_ONCE", 1);
      AT_LEAST_ONCE = var1;
      InvocationKind var2 = new InvocationKind("EXACTLY_ONCE", 2);
      EXACTLY_ONCE = var2;
      InvocationKind var3 = new InvocationKind("UNKNOWN", 3);
      UNKNOWN = var3;
   }
}
