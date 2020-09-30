package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.json.JsonFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Deprecated
public class CloudShellCredential extends GoogleCredential {
   private static final int ACCESS_TOKEN_INDEX = 2;
   protected static final String GET_AUTH_TOKEN_REQUEST = "2\n[]";
   private static final int READ_TIMEOUT_MS = 5000;
   private final int authPort;
   private final JsonFactory jsonFactory;

   public CloudShellCredential(int var1, JsonFactory var2) {
      this.authPort = var1;
      this.jsonFactory = var2;
   }

   protected TokenResponse executeRefreshToken() throws IOException {
      Socket var1 = new Socket("localhost", this.getAuthPort());
      var1.setSoTimeout(5000);
      TokenResponse var2 = new TokenResponse();

      try {
         PrintWriter var3 = new PrintWriter(var1.getOutputStream(), true);
         var3.println("2\n[]");
         InputStreamReader var4 = new InputStreamReader(var1.getInputStream());
         BufferedReader var7 = new BufferedReader(var4);
         var7.readLine();
         var2.setAccessToken(((List)this.jsonFactory.createJsonParser((Reader)var7).parseArray(LinkedList.class, Object.class)).get(2).toString());
      } finally {
         var1.close();
      }

      return var2;
   }

   protected int getAuthPort() {
      return this.authPort;
   }
}
