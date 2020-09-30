package javax.mail;

import java.net.InetAddress;

public abstract class Authenticator {
   private int requestingPort;
   private String requestingPrompt;
   private String requestingProtocol;
   private InetAddress requestingSite;
   private String requestingUserName;

   private void reset() {
      this.requestingSite = null;
      this.requestingPort = -1;
      this.requestingProtocol = null;
      this.requestingPrompt = null;
      this.requestingUserName = null;
   }

   protected final String getDefaultUserName() {
      return this.requestingUserName;
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      return null;
   }

   protected final int getRequestingPort() {
      return this.requestingPort;
   }

   protected final String getRequestingPrompt() {
      return this.requestingPrompt;
   }

   protected final String getRequestingProtocol() {
      return this.requestingProtocol;
   }

   protected final InetAddress getRequestingSite() {
      return this.requestingSite;
   }

   final PasswordAuthentication requestPasswordAuthentication(InetAddress var1, int var2, String var3, String var4, String var5) {
      this.reset();
      this.requestingSite = var1;
      this.requestingPort = var2;
      this.requestingProtocol = var3;
      this.requestingPrompt = var4;
      this.requestingUserName = var5;
      return this.getPasswordAuthentication();
   }
}
