package com.google.common.base;

import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Objects extends ExtraObjectsMethodsForWeb {
   private Objects() {
   }

   public static boolean equal(@NullableDecl Object var0, @NullableDecl Object var1) {
      boolean var2;
      if (var0 == var1 || var0 != null && var0.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static int hashCode(@NullableDecl Object... var0) {
      return Arrays.hashCode(var0);
   }
}
