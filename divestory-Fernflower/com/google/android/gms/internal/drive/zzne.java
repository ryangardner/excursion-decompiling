package com.google.android.gms.internal.drive;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class zzne implements PrivilegedExceptionAction<Unsafe> {
   // $FF: synthetic method
   public final Object run() throws Exception {
      Field[] var1 = Unsafe.class.getDeclaredFields();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Field var4 = var1[var3];
         var4.setAccessible(true);
         Object var5 = var4.get((Object)null);
         if (Unsafe.class.isInstance(var5)) {
            return (Unsafe)Unsafe.class.cast(var5);
         }
      }

      return null;
   }
}
