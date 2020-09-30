package com.fasterxml.jackson.core.util;

import java.util.concurrent.ConcurrentHashMap;

public final class InternCache extends ConcurrentHashMap<String, String> {
   private static final int MAX_ENTRIES = 180;
   public static final InternCache instance = new InternCache();
   private static final long serialVersionUID = 1L;
   private final Object lock = new Object();

   private InternCache() {
      super(180, 0.8F, 4);
   }

   public String intern(String var1) {
      String var2 = (String)this.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         if (this.size() >= 180) {
            label171: {
               Object var16 = this.lock;
               synchronized(var16){}

               Throwable var10000;
               boolean var10001;
               label162: {
                  try {
                     if (this.size() >= 180) {
                        this.clear();
                     }
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label162;
                  }

                  label159:
                  try {
                     break label171;
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label159;
                  }
               }

               while(true) {
                  Throwable var15 = var10000;

                  try {
                     throw var15;
                  } catch (Throwable var12) {
                     var10000 = var12;
                     var10001 = false;
                     continue;
                  }
               }
            }
         }

         var1 = var1.intern();
         this.put(var1, var1);
         return var1;
      }
   }
}
