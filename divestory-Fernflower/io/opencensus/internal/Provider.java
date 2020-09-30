package io.opencensus.internal;

import java.util.ServiceConfigurationError;

public final class Provider {
   private Provider() {
   }

   public static <T> T createInstance(Class<?> var0, Class<T> var1) {
      try {
         Object var4 = var0.asSubclass(var1).getConstructor().newInstance();
         return var4;
      } catch (Exception var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Provider ");
         var2.append(var0.getName());
         var2.append(" could not be instantiated.");
         throw new ServiceConfigurationError(var2.toString(), var3);
      }
   }
}
