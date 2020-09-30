package com.google.api.client.util;

public interface Clock {
   Clock SYSTEM = new Clock() {
      public long currentTimeMillis() {
         return System.currentTimeMillis();
      }
   };

   long currentTimeMillis();
}
