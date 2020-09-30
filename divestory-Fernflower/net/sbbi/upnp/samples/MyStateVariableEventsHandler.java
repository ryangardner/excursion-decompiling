package net.sbbi.upnp.samples;

import java.io.PrintStream;
import net.sbbi.upnp.ServiceEventHandler;

public class MyStateVariableEventsHandler implements ServiceEventHandler {
   public static void main(String[] param0) {
      // $FF: Couldn't be decompiled
   }

   public void handleStateVariableEvent(String var1, String var2) {
      PrintStream var3 = System.out;
      StringBuilder var4 = new StringBuilder("State variable ");
      var4.append(var1);
      var4.append(" changed to ");
      var4.append(var2);
      var3.println(var4.toString());
   }
}
