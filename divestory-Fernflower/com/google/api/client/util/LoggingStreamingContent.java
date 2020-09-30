package com.google.api.client.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggingStreamingContent implements StreamingContent {
   private final StreamingContent content;
   private final int contentLoggingLimit;
   private final Logger logger;
   private final Level loggingLevel;

   public LoggingStreamingContent(StreamingContent var1, Logger var2, Level var3, int var4) {
      this.content = var1;
      this.logger = var2;
      this.loggingLevel = var3;
      this.contentLoggingLimit = var4;
   }

   public void writeTo(OutputStream var1) throws IOException {
      LoggingOutputStream var2 = new LoggingOutputStream(var1, this.logger, this.loggingLevel, this.contentLoggingLimit);

      try {
         this.content.writeTo(var2);
      } finally {
         var2.getLogStream().close();
      }

      var1.flush();
   }
}
