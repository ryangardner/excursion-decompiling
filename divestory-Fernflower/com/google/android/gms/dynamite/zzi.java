package com.google.android.gms.dynamite;

import dalvik.system.PathClassLoader;

final class zzi extends PathClassLoader {
   zzi(String var1, ClassLoader var2) {
      super(var1, var2);
   }

   protected final Class<?> loadClass(String var1, boolean var2) throws ClassNotFoundException {
      if (!var1.startsWith("java.") && !var1.startsWith("android.")) {
         try {
            Class var3 = this.findClass(var1);
            return var3;
         } catch (ClassNotFoundException var4) {
         }
      }

      return super.loadClass(var1, var2);
   }
}
