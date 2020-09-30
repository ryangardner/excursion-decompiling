package org.apache.commons.net.whois;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.finger.FingerClient;

public final class WhoisClient extends FingerClient {
   public static final String DEFAULT_HOST = "whois.internic.net";
   public static final int DEFAULT_PORT = 43;

   public WhoisClient() {
      this.setDefaultPort(43);
   }

   public InputStream getInputStream(String var1) throws IOException {
      return this.getInputStream(false, var1);
   }

   public String query(String var1) throws IOException {
      return this.query(false, var1);
   }
}
