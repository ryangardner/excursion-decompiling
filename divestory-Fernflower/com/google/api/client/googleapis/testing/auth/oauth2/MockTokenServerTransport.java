package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.googleapis.testing.TestUtils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MockTokenServerTransport extends MockHttpTransport {
   static final String EXPECTED_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
   static final JsonFactory JSON_FACTORY = new JacksonFactory();
   private static final String LEGACY_TOKEN_SERVER_URL = "https://accounts.google.com/o/oauth2/token";
   private static final Logger LOGGER = Logger.getLogger(MockTokenServerTransport.class.getName());
   Map<String, String> clients;
   Map<String, String> refreshTokens;
   Map<String, String> serviceAccounts;
   final String tokenServerUrl;

   public MockTokenServerTransport() {
      this("https://oauth2.googleapis.com/token");
   }

   public MockTokenServerTransport(String var1) {
      this.serviceAccounts = new HashMap();
      this.clients = new HashMap();
      this.refreshTokens = new HashMap();
      this.tokenServerUrl = var1;
   }

   private MockLowLevelHttpRequest buildTokenRequest(String var1) {
      return new MockLowLevelHttpRequest(var1) {
         public LowLevelHttpResponse execute() throws IOException {
            Map var1 = TestUtils.parseQuery(this.getContentAsString());
            String var2 = (String)var1.get("client_id");
            String var3;
            String var4;
            if (var2 != null) {
               if (!MockTokenServerTransport.this.clients.containsKey(var2)) {
                  throw new IOException("Client ID not found.");
               }

               var3 = (String)var1.get("client_secret");
               var2 = (String)MockTokenServerTransport.this.clients.get(var2);
               if (var3 == null || !var3.equals(var2)) {
                  throw new IOException("Client secret not found.");
               }

               var4 = (String)var1.get("refresh_token");
               if (!MockTokenServerTransport.this.refreshTokens.containsKey(var4)) {
                  throw new IOException("Refresh Token not found.");
               }

               var4 = (String)MockTokenServerTransport.this.refreshTokens.get(var4);
            } else {
               if (!var1.containsKey("grant_type")) {
                  throw new IOException("Unknown token type.");
               }

               if (!"urn:ietf:params:oauth:grant-type:jwt-bearer".equals((String)var1.get("grant_type"))) {
                  throw new IOException("Unexpected Grant Type.");
               }

               var4 = (String)var1.get("assertion");
               JsonWebSignature var5 = JsonWebSignature.parse(MockTokenServerTransport.JSON_FACTORY, var4);
               var4 = var5.getPayload().getIssuer();
               if (!MockTokenServerTransport.this.serviceAccounts.containsKey(var4)) {
                  throw new IOException("Service Account Email not found as issuer.");
               }

               var4 = (String)MockTokenServerTransport.this.serviceAccounts.get(var4);
               var3 = (String)var5.getPayload().get("scope");
               if (var3 == null || var3.length() == 0) {
                  throw new IOException("Scopes not found.");
               }
            }

            GenericJson var6 = new GenericJson();
            var6.setFactory(MockTokenServerTransport.JSON_FACTORY);
            var6.put("access_token", var4);
            var6.put("expires_in", 3600);
            var6.put("token_type", "Bearer");
            var4 = var6.toPrettyString();
            return (new MockLowLevelHttpResponse()).setContentType("application/json; charset=UTF-8").setContent(var4);
         }
      };
   }

   public void addClient(String var1, String var2) {
      this.clients.put(var1, var2);
   }

   public void addRefreshToken(String var1, String var2) {
      this.refreshTokens.put(var1, var2);
   }

   public void addServiceAccount(String var1, String var2) {
      this.serviceAccounts.put(var1, var2);
   }

   public LowLevelHttpRequest buildRequest(String var1, String var2) throws IOException {
      if (var2.equals(this.tokenServerUrl)) {
         return this.buildTokenRequest(var2);
      } else if (var2.equals("https://accounts.google.com/o/oauth2/token")) {
         LOGGER.warning("Your configured token_uri is using a legacy endpoint. You may want to redownload your credentials.");
         return this.buildTokenRequest(var2);
      } else {
         return super.buildRequest(var1, var2);
      }
   }
}
