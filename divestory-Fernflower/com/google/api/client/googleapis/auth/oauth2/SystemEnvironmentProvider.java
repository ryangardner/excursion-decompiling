package com.google.api.client.googleapis.auth.oauth2;

class SystemEnvironmentProvider {
   static final SystemEnvironmentProvider INSTANCE = new SystemEnvironmentProvider();

   String getEnv(String var1) {
      return System.getenv(var1);
   }

   boolean getEnvEquals(String var1, String var2) {
      boolean var3;
      if (System.getenv().containsKey(var1) && System.getenv(var1).equals(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
