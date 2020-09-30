package org.apache.commons.net.telnet;

public class EchoOptionHandler extends TelnetOptionHandler {
   public EchoOptionHandler() {
      super(1, false, false, false, false);
   }

   public EchoOptionHandler(boolean var1, boolean var2, boolean var3, boolean var4) {
      super(1, var1, var2, var3, var4);
   }
}
