package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.util.LangUtils;

public final class BasicUserPrincipal implements Principal, Serializable {
   private static final long serialVersionUID = -2266305184969850467L;
   private final String username;

   public BasicUserPrincipal(String var1) {
      if (var1 != null) {
         this.username = var1;
      } else {
         throw new IllegalArgumentException("User name may not be null");
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 instanceof BasicUserPrincipal) {
            BasicUserPrincipal var2 = (BasicUserPrincipal)var1;
            if (LangUtils.equals((Object)this.username, (Object)var2.username)) {
               return true;
            }
         }

         return false;
      }
   }

   public String getName() {
      return this.username;
   }

   public int hashCode() {
      return LangUtils.hashCode(17, this.username);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[principal: ");
      var1.append(this.username);
      var1.append("]");
      return var1.toString();
   }
}
