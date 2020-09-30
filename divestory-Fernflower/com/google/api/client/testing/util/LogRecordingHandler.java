package com.google.api.client.testing.util;

import com.google.api.client.util.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogRecordingHandler extends Handler {
   private final List<LogRecord> records = Lists.newArrayList();

   public void close() throws SecurityException {
   }

   public void flush() {
   }

   public List<String> messages() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.records.iterator();

      while(var2.hasNext()) {
         var1.add(((LogRecord)var2.next()).getMessage());
      }

      return var1;
   }

   public void publish(LogRecord var1) {
      this.records.add(var1);
   }
}
