package com.google.api.client.googleapis.testing.json;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;

public final class GoogleJsonResponseExceptionFactoryTesting {
   public static GoogleJsonResponseException newMock(JsonFactory var0, int var1, String var2) throws IOException {
      MockLowLevelHttpResponse var3 = (new MockLowLevelHttpResponse()).setStatusCode(var1).setReasonPhrase(var2).setContentType("application/json; charset=UTF-8");
      StringBuilder var4 = new StringBuilder();
      var4.append("{ \"error\": { \"errors\": [ { \"reason\": \"");
      var4.append(var2);
      var4.append("\" } ], \"code\": ");
      var4.append(var1);
      var4.append(" } }");
      MockLowLevelHttpResponse var5 = var3.setContent(var4.toString());
      HttpRequest var6 = (new MockHttpTransport.Builder()).setLowLevelHttpResponse(var5).build().createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
      var6.setThrowExceptionOnExecuteError(false);
      return GoogleJsonResponseException.from(var0, var6.execute());
   }
}
