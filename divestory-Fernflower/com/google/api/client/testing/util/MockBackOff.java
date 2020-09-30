package com.google.api.client.testing.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class MockBackOff implements BackOff {
   private long backOffMillis;
   private int maxTries = 10;
   private int numTries;

   public final int getMaxTries() {
      return this.maxTries;
   }

   public final int getNumberOfTries() {
      return this.numTries;
   }

   public long nextBackOffMillis() throws IOException {
      int var1 = this.numTries;
      if (var1 < this.maxTries) {
         long var2 = this.backOffMillis;
         if (var2 != -1L) {
            this.numTries = var1 + 1;
            return var2;
         }
      }

      return -1L;
   }

   public void reset() throws IOException {
      this.numTries = 0;
   }

   public MockBackOff setBackOffMillis(long var1) {
      boolean var3;
      if (var1 != -1L && var1 < 0L) {
         var3 = false;
      } else {
         var3 = true;
      }

      Preconditions.checkArgument(var3);
      this.backOffMillis = var1;
      return this;
   }

   public MockBackOff setMaxTries(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.maxTries = var1;
      return this;
   }
}
