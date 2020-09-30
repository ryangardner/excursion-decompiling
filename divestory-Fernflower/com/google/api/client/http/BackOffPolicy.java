package com.google.api.client.http;

import java.io.IOException;

@Deprecated
public interface BackOffPolicy {
   long STOP = -1L;

   long getNextBackOffMillis() throws IOException;

   boolean isBackOffRequired(int var1);

   void reset();
}
