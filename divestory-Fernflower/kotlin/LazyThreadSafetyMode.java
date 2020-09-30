package kotlin;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/LazyThreadSafetyMode;", "", "(Ljava/lang/String;I)V", "SYNCHRONIZED", "PUBLICATION", "NONE", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum LazyThreadSafetyMode {
   NONE,
   PUBLICATION,
   SYNCHRONIZED;

   static {
      LazyThreadSafetyMode var0 = new LazyThreadSafetyMode("SYNCHRONIZED", 0);
      SYNCHRONIZED = var0;
      LazyThreadSafetyMode var1 = new LazyThreadSafetyMode("PUBLICATION", 1);
      PUBLICATION = var1;
      LazyThreadSafetyMode var2 = new LazyThreadSafetyMode("NONE", 2);
      NONE = var2;
   }
}
