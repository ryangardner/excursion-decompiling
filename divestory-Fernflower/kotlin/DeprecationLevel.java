package kotlin;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/DeprecationLevel;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "HIDDEN", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public enum DeprecationLevel {
   ERROR,
   HIDDEN,
   WARNING;

   static {
      DeprecationLevel var0 = new DeprecationLevel("WARNING", 0);
      WARNING = var0;
      DeprecationLevel var1 = new DeprecationLevel("ERROR", 1);
      ERROR = var1;
      DeprecationLevel var2 = new DeprecationLevel("HIDDEN", 2);
      HIDDEN = var2;
   }
}
