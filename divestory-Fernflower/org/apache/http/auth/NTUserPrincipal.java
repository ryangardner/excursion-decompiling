package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.util.LangUtils;

public class NTUserPrincipal implements Principal, Serializable {
   private static final long serialVersionUID = -6870169797924406894L;
   private final String domain;
   private final String ntname;
   private final String username;

   public NTUserPrincipal(String var1, String var2) {
      if (var2 != null) {
         this.username = var2;
         if (var1 != null) {
            this.domain = var1.toUpperCase(Locale.ENGLISH);
         } else {
            this.domain = null;
         }

         var1 = this.domain;
         if (var1 != null && var1.length() > 0) {
            StringBuilder var3 = new StringBuilder();
            var3.append(this.domain);
            var3.append('/');
            var3.append(this.username);
            this.ntname = var3.toString();
         } else {
            this.ntname = this.username;
         }

      } else {
         throw new IllegalArgumentException("User name may not be null");
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 instanceof NTUserPrincipal) {
            NTUserPrincipal var2 = (NTUserPrincipal)var1;
            if (LangUtils.equals((Object)this.username, (Object)var2.username) && LangUtils.equals((Object)this.domain, (Object)var2.domain)) {
               return true;
            }
         }

         return false;
      }
   }

   public String getDomain() {
      return this.domain;
   }

   public String getName() {
      return this.ntname;
   }

   public String getUsername() {
      return this.username;
   }

   public int hashCode() {
      return LangUtils.hashCode(LangUtils.hashCode(17, this.username), this.domain);
   }

   public String toString() {
      return this.ntname;
   }
}
