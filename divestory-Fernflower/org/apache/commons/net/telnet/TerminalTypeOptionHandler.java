package org.apache.commons.net.telnet;

public class TerminalTypeOptionHandler extends TelnetOptionHandler {
   protected static final int TERMINAL_TYPE = 24;
   protected static final int TERMINAL_TYPE_IS = 0;
   protected static final int TERMINAL_TYPE_SEND = 1;
   private final String termType;

   public TerminalTypeOptionHandler(String var1) {
      super(24, false, false, false, false);
      this.termType = var1;
   }

   public TerminalTypeOptionHandler(String var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      super(24, var2, var3, var4, var5);
      this.termType = var1;
   }

   public int[] answerSubnegotiation(int[] var1, int var2) {
      if (var1 != null && var2 > 1) {
         String var3 = this.termType;
         if (var3 != null) {
            var2 = 0;
            if (var1[0] == 24 && var1[1] == 1) {
               var1 = new int[var3.length() + 2];
               var1[0] = 24;

               for(var1[1] = 0; var2 < this.termType.length(); ++var2) {
                  var1[var2 + 2] = this.termType.charAt(var2);
               }

               return var1;
            }
         }
      }

      return null;
   }
}
