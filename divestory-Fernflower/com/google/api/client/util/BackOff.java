package com.google.api.client.util;

import java.io.IOException;

public interface BackOff {
   long STOP = -1L;
   BackOff STOP_BACKOFF = new BackOff() {
      public long nextBackOffMillis() throws IOException {
         return -1L;
      }

      public void reset() throws IOException {
      }
   };
   BackOff ZERO_BACKOFF = new BackOff() {
      public long nextBackOffMillis() throws IOException {
         return 0L;
      }

      public void reset() throws IOException {
      }
   };

   long nextBackOffMillis() throws IOException;

   void reset() throws IOException;
}
