package org.apache.commons.net.bsd;

import java.io.IOException;

public class RLoginClient extends RCommandClient {
   public static final int DEFAULT_PORT = 513;

   public RLoginClient() {
      this.setDefaultPort(513);
   }

   public void rlogin(String var1, String var2, String var3) throws IOException {
      this.rexec(var1, var2, var3, false);
   }

   public void rlogin(String var1, String var2, String var3, int var4) throws IOException {
      StringBuilder var5 = new StringBuilder();
      var5.append(var3);
      var5.append("/");
      var5.append(var4);
      this.rexec(var1, var2, var5.toString(), false);
   }
}
