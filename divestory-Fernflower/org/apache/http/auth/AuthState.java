package org.apache.http.auth;

public class AuthState {
   private AuthScheme authScheme;
   private AuthScope authScope;
   private Credentials credentials;

   public AuthScheme getAuthScheme() {
      return this.authScheme;
   }

   public AuthScope getAuthScope() {
      return this.authScope;
   }

   public Credentials getCredentials() {
      return this.credentials;
   }

   public void invalidate() {
      this.authScheme = null;
      this.authScope = null;
      this.credentials = null;
   }

   public boolean isValid() {
      boolean var1;
      if (this.authScheme != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setAuthScheme(AuthScheme var1) {
      if (var1 == null) {
         this.invalidate();
      } else {
         this.authScheme = var1;
      }
   }

   public void setAuthScope(AuthScope var1) {
      this.authScope = var1;
   }

   public void setCredentials(Credentials var1) {
      this.credentials = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("auth scope [");
      var1.append(this.authScope);
      var1.append("]; credentials set [");
      String var2;
      if (this.credentials != null) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      var1.append(var2);
      var1.append("]");
      return var1.toString();
   }
}
