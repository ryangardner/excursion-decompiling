package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.io.IOException;

public final class BasicAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor {
   private final String password;
   private final String username;

   public BasicAuthentication(String var1, String var2) {
      this.username = (String)Preconditions.checkNotNull(var1);
      this.password = (String)Preconditions.checkNotNull(var2);
   }

   public String getPassword() {
      return this.password;
   }

   public String getUsername() {
      return this.username;
   }

   public void initialize(HttpRequest var1) throws IOException {
      var1.setInterceptor(this);
   }

   public void intercept(HttpRequest var1) throws IOException {
      var1.getHeaders().setBasicAuthentication(this.username, this.password);
   }
}
