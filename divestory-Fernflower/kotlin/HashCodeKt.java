package kotlin;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0000\u001a\u000f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b¨\u0006\u0003"},
   d2 = {"hashCode", "", "", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class HashCodeKt {
   private static final int hashCode(Object var0) {
      int var1;
      if (var0 != null) {
         var1 = var0.hashCode();
      } else {
         var1 = 0;
      }

      return var1;
   }
}
