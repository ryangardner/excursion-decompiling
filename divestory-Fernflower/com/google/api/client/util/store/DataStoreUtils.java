package com.google.api.client.util.store;

import java.io.IOException;
import java.util.Iterator;

public final class DataStoreUtils {
   private DataStoreUtils() {
   }

   public static String toString(DataStore<?> var0) {
      IOException var10000;
      label52: {
         StringBuilder var1;
         boolean var10001;
         try {
            var1 = new StringBuilder();
            var1.append('{');
         } catch (IOException var10) {
            var10000 = var10;
            var10001 = false;
            break label52;
         }

         boolean var2 = true;

         Iterator var3;
         try {
            var3 = var0.keySet().iterator();
         } catch (IOException var8) {
            var10000 = var8;
            var10001 = false;
            break label52;
         }

         while(true) {
            String var4;
            try {
               if (!var3.hasNext()) {
                  break;
               }

               var4 = (String)var3.next();
            } catch (IOException var9) {
               var10000 = var9;
               var10001 = false;
               break label52;
            }

            if (var2) {
               var2 = false;
            } else {
               try {
                  var1.append(", ");
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label52;
               }
            }

            try {
               var1.append(var4);
               var1.append('=');
               var1.append(var0.get(var4));
            } catch (IOException var6) {
               var10000 = var6;
               var10001 = false;
               break label52;
            }
         }

         try {
            var1.append('}');
            String var12 = var1.toString();
            return var12;
         } catch (IOException var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      IOException var11 = var10000;
      throw new RuntimeException(var11);
   }
}
