package org.apache.commons.net;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PrintCommandListener implements ProtocolCommandListener {
   private final boolean __directionMarker;
   private final char __eolMarker;
   private final boolean __nologin;
   private final PrintWriter __writer;

   public PrintCommandListener(PrintStream var1) {
      this(new PrintWriter(var1));
   }

   public PrintCommandListener(PrintStream var1, boolean var2) {
      this(new PrintWriter(var1), var2);
   }

   public PrintCommandListener(PrintStream var1, boolean var2, char var3) {
      this(new PrintWriter(var1), var2, var3);
   }

   public PrintCommandListener(PrintStream var1, boolean var2, char var3, boolean var4) {
      this(new PrintWriter(var1), var2, var3, var4);
   }

   public PrintCommandListener(PrintWriter var1) {
      this(var1, false);
   }

   public PrintCommandListener(PrintWriter var1, boolean var2) {
      this(var1, var2, '\u0000');
   }

   public PrintCommandListener(PrintWriter var1, boolean var2, char var3) {
      this(var1, var2, var3, false);
   }

   public PrintCommandListener(PrintWriter var1, boolean var2, char var3, boolean var4) {
      this.__writer = var1;
      this.__nologin = var2;
      this.__eolMarker = (char)var3;
      this.__directionMarker = var4;
   }

   private String getPrintableString(String var1) {
      if (this.__eolMarker == 0) {
         return var1;
      } else {
         int var2 = var1.indexOf("\r\n");
         String var3 = var1;
         if (var2 > 0) {
            StringBuilder var4 = new StringBuilder();
            var4.append(var1.substring(0, var2));
            var4.append(this.__eolMarker);
            var4.append(var1.substring(var2));
            var3 = var4.toString();
         }

         return var3;
      }
   }

   public void protocolCommandSent(ProtocolCommandEvent var1) {
      if (this.__directionMarker) {
         this.__writer.print("> ");
      }

      if (this.__nologin) {
         String var2 = var1.getCommand();
         if (!"PASS".equalsIgnoreCase(var2) && !"USER".equalsIgnoreCase(var2)) {
            if ("LOGIN".equalsIgnoreCase(var2)) {
               String var3 = var1.getMessage();
               var3 = var3.substring(0, var3.indexOf("LOGIN") + 5);
               this.__writer.print(var3);
               this.__writer.println(" *******");
            } else {
               this.__writer.print(this.getPrintableString(var1.getMessage()));
            }
         } else {
            this.__writer.print(var2);
            this.__writer.println(" *******");
         }
      } else {
         this.__writer.print(this.getPrintableString(var1.getMessage()));
      }

      this.__writer.flush();
   }

   public void protocolReplyReceived(ProtocolCommandEvent var1) {
      if (this.__directionMarker) {
         this.__writer.print("< ");
      }

      this.__writer.print(var1.getMessage());
      this.__writer.flush();
   }
}
