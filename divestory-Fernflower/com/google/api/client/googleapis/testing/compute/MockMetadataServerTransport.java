package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;

public class MockMetadataServerTransport extends MockHttpTransport {
   static final JsonFactory JSON_FACTORY;
   private static final String METADATA_SERVER_URL = OAuth2Utils.getMetadataServerUrl();
   private static final String METADATA_TOKEN_SERVER_URL;
   String accessToken;
   Integer tokenRequestStatusCode;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(METADATA_SERVER_URL);
      var0.append("/computeMetadata/v1/instance/service-accounts/default/token");
      METADATA_TOKEN_SERVER_URL = var0.toString();
      JSON_FACTORY = new JacksonFactory();
   }

   public MockMetadataServerTransport(String var1) {
      this.accessToken = var1;
   }

   public LowLevelHttpRequest buildRequest(String var1, String var2) throws IOException {
      if (var2.equals(METADATA_TOKEN_SERVER_URL)) {
         return new MockLowLevelHttpRequest(var2) {
            public LowLevelHttpResponse execute() throws IOException {
               if (MockMetadataServerTransport.this.tokenRequestStatusCode != null) {
                  return (new MockLowLevelHttpResponse()).setStatusCode(MockMetadataServerTransport.this.tokenRequestStatusCode).setContent("Token Fetch Error");
               } else if ("Google".equals(this.getFirstHeaderValue("Metadata-Flavor"))) {
                  GenericJson var1 = new GenericJson();
                  var1.setFactory(MockMetadataServerTransport.JSON_FACTORY);
                  var1.put("access_token", MockMetadataServerTransport.this.accessToken);
                  var1.put("expires_in", 3600000);
                  var1.put("token_type", "Bearer");
                  String var2 = var1.toPrettyString();
                  return (new MockLowLevelHttpResponse()).setContentType("application/json; charset=UTF-8").setContent(var2);
               } else {
                  throw new IOException("Metadata request header not found.");
               }
            }
         };
      } else {
         return (LowLevelHttpRequest)(var2.equals(METADATA_SERVER_URL) ? new MockLowLevelHttpRequest(var2) {
            public LowLevelHttpResponse execute() {
               MockLowLevelHttpResponse var1 = new MockLowLevelHttpResponse();
               var1.addHeader("Metadata-Flavor", "Google");
               return var1;
            }
         } : super.buildRequest(var1, var2));
      }
   }

   public void setTokenRequestStatusCode(Integer var1) {
      this.tokenRequestStatusCode = var1;
   }
}
