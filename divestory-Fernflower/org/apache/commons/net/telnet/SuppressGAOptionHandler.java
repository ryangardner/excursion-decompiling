package org.apache.commons.net.telnet;

public class SuppressGAOptionHandler extends TelnetOptionHandler {
   public SuppressGAOptionHandler() {
      super(3, false, false, false, false);
   }

   public SuppressGAOptionHandler(boolean var1, boolean var2, boolean var3, boolean var4) {
      super(3, var1, var2, var3, var4);
   }
}
