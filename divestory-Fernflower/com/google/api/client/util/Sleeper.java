package com.google.api.client.util;

public interface Sleeper {
   Sleeper DEFAULT = new Sleeper() {
      public void sleep(long var1) throws InterruptedException {
         Thread.sleep(var1);
      }
   };

   void sleep(long var1) throws InterruptedException;
}
