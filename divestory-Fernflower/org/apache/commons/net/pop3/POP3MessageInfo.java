package org.apache.commons.net.pop3;

public final class POP3MessageInfo {
   public String identifier;
   public int number;
   public int size;

   public POP3MessageInfo() {
      this(0, (String)null, 0);
   }

   public POP3MessageInfo(int var1, int var2) {
      this(var1, (String)null, var2);
   }

   public POP3MessageInfo(int var1, String var2) {
      this(var1, var2, -1);
   }

   private POP3MessageInfo(int var1, String var2, int var3) {
      this.number = var1;
      this.size = var3;
      this.identifier = var2;
   }
}
