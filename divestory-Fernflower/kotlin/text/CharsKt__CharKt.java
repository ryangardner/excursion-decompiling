package kotlin.text;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0004\u001a\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\n¨\u0006\b"},
   d2 = {"equals", "", "", "other", "ignoreCase", "isSurrogate", "plus", "", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/CharsKt"
)
class CharsKt__CharKt extends CharsKt__CharJVMKt {
   public CharsKt__CharKt() {
   }

   public static final boolean equals(char var0, char var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (!var2) {
         return false;
      } else if (Character.toUpperCase(var0) == Character.toUpperCase(var1)) {
         return true;
      } else {
         return Character.toLowerCase(var0) == Character.toLowerCase(var1);
      }
   }

   // $FF: synthetic method
   public static boolean equals$default(char var0, char var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return CharsKt.equals(var0, var1, var2);
   }

   public static final boolean isSurrogate(char var0) {
      boolean var1;
      if ('\ud800' <= var0 && '\udfff' >= var0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final String plus(char var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(String.valueOf(var0));
      var2.append(var1);
      return var2.toString();
   }
}
