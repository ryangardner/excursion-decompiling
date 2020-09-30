package org.apache.http.impl.conn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;

public class Wire {
   private final Log log;

   public Wire(Log var1) {
      this.log = var1;
   }

   private void wire(String var1, InputStream var2) throws IOException {
      StringBuilder var3 = new StringBuilder();

      while(true) {
         while(true) {
            int var4 = var2.read();
            if (var4 == -1) {
               if (var3.length() > 0) {
                  var3.append('"');
                  var3.insert(0, '"');
                  var3.insert(0, var1);
                  this.log.debug(var3.toString());
               }

               return;
            }

            if (var4 == 13) {
               var3.append("[\\r]");
            } else if (var4 == 10) {
               var3.append("[\\n]\"");
               var3.insert(0, "\"");
               var3.insert(0, var1);
               this.log.debug(var3.toString());
               var3.setLength(0);
            } else if (var4 >= 32 && var4 <= 127) {
               var3.append((char)var4);
            } else {
               var3.append("[0x");
               var3.append(Integer.toHexString(var4));
               var3.append("]");
            }
         }
      }
   }

   public boolean enabled() {
      return this.log.isDebugEnabled();
   }

   public void input(int var1) throws IOException {
      this.input(new byte[]{(byte)var1});
   }

   public void input(InputStream var1) throws IOException {
      if (var1 != null) {
         this.wire("<< ", var1);
      } else {
         throw new IllegalArgumentException("Input may not be null");
      }
   }

   @Deprecated
   public void input(String var1) throws IOException {
      if (var1 != null) {
         this.input(var1.getBytes());
      } else {
         throw new IllegalArgumentException("Input may not be null");
      }
   }

   public void input(byte[] var1) throws IOException {
      if (var1 != null) {
         this.wire("<< ", new ByteArrayInputStream(var1));
      } else {
         throw new IllegalArgumentException("Input may not be null");
      }
   }

   public void input(byte[] var1, int var2, int var3) throws IOException {
      if (var1 != null) {
         this.wire("<< ", new ByteArrayInputStream(var1, var2, var3));
      } else {
         throw new IllegalArgumentException("Input may not be null");
      }
   }

   public void output(int var1) throws IOException {
      this.output(new byte[]{(byte)var1});
   }

   public void output(InputStream var1) throws IOException {
      if (var1 != null) {
         this.wire(">> ", var1);
      } else {
         throw new IllegalArgumentException("Output may not be null");
      }
   }

   @Deprecated
   public void output(String var1) throws IOException {
      if (var1 != null) {
         this.output(var1.getBytes());
      } else {
         throw new IllegalArgumentException("Output may not be null");
      }
   }

   public void output(byte[] var1) throws IOException {
      if (var1 != null) {
         this.wire(">> ", new ByteArrayInputStream(var1));
      } else {
         throw new IllegalArgumentException("Output may not be null");
      }
   }

   public void output(byte[] var1, int var2, int var3) throws IOException {
      if (var1 != null) {
         this.wire(">> ", new ByteArrayInputStream(var1, var2, var3));
      } else {
         throw new IllegalArgumentException("Output may not be null");
      }
   }
}
