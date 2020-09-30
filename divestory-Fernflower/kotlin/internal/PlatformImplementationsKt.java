package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class PlatformImplementationsKt {
   public static final PlatformImplementations IMPLEMENTATIONS;

   static {
      // $FF: Couldn't be decompiled
   }

   public static final boolean apiVersionIsAtLeast(int var0, int var1, int var2) {
      return KotlinVersion.CURRENT.isAtLeast(var0, var1, var2);
   }

   // $FF: synthetic method
   private static final <T> T castToBaseType(Object var0) {
      try {
         Intrinsics.reifiedOperationMarker(1, "T");
         Object var1 = (Object)var0;
         return var1;
      } catch (ClassCastException var4) {
         ClassLoader var5 = var0.getClass().getClassLoader();
         Intrinsics.reifiedOperationMarker(4, "T");
         ClassLoader var2 = Object.class.getClassLoader();
         StringBuilder var3 = new StringBuilder();
         var3.append("Instance classloader: ");
         var3.append(var5);
         var3.append(", base type classloader: ");
         var3.append(var2);
         Throwable var6 = (new ClassCastException(var3.toString())).initCause((Throwable)var4);
         Intrinsics.checkExpressionValueIsNotNull(var6, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
         throw var6;
      }
   }

   private static final int getJavaVersion() {
      String var0 = System.getProperty("java.specification.version");
      int var1 = 65542;
      if (var0 == null) {
         return 65542;
      } else {
         CharSequence var2 = (CharSequence)var0;
         int var3 = StringsKt.indexOf$default(var2, '.', 0, false, 6, (Object)null);
         int var4;
         if (var3 < 0) {
            try {
               var4 = Integer.parseInt(var0);
            } catch (NumberFormatException var7) {
               return var1;
            }

            var1 = var4 * 65536;
            return var1;
         } else {
            int var5 = var3 + 1;
            int var6 = StringsKt.indexOf$default(var2, '.', var5, false, 4, (Object)null);
            var4 = var6;
            if (var6 < 0) {
               var4 = var0.length();
            }

            if (var0 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
               String var9 = var0.substring(0, var3);
               Intrinsics.checkExpressionValueIsNotNull(var9, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               if (var0 != null) {
                  var0 = var0.substring(var5, var4);
                  Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");

                  try {
                     var6 = Integer.parseInt(var9);
                     var4 = Integer.parseInt(var0);
                  } catch (NumberFormatException var8) {
                     return var1;
                  }

                  var1 = var6 * 65536 + var4;
                  return var1;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }
            }
         }
      }
   }
}
