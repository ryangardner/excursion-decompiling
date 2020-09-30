package kotlin.reflect;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/reflect/KVariance;", "", "(Ljava/lang/String;I)V", "INVARIANT", "IN", "OUT", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum KVariance {
   IN,
   INVARIANT,
   OUT;

   static {
      KVariance var0 = new KVariance("INVARIANT", 0);
      INVARIANT = var0;
      KVariance var1 = new KVariance("IN", 1);
      IN = var1;
      KVariance var2 = new KVariance("OUT", 2);
      OUT = var2;
   }
}
