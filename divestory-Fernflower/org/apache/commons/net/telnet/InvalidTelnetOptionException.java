package org.apache.commons.net.telnet;

public class InvalidTelnetOptionException extends Exception {
   private static final long serialVersionUID = -2516777155928793597L;
   private final String msg;
   private final int optionCode;

   public InvalidTelnetOptionException(String var1, int var2) {
      this.optionCode = var2;
      this.msg = var1;
   }

   public String getMessage() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.msg);
      var1.append(": ");
      var1.append(this.optionCode);
      return var1.toString();
   }
}
