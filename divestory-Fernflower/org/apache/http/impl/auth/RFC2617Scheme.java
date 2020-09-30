package org.apache.http.impl.auth;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HeaderElement;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public abstract class RFC2617Scheme extends AuthSchemeBase {
   private Map<String, String> params;

   public String getParameter(String var1) {
      if (var1 != null) {
         Map var2 = this.params;
         return var2 == null ? null : (String)var2.get(var1.toLowerCase(Locale.ENGLISH));
      } else {
         throw new IllegalArgumentException("Parameter name may not be null");
      }
   }

   protected Map<String, String> getParameters() {
      if (this.params == null) {
         this.params = new HashMap();
      }

      return this.params;
   }

   public String getRealm() {
      return this.getParameter("realm");
   }

   protected void parseChallenge(CharArrayBuffer var1, int var2, int var3) throws MalformedChallengeException {
      HeaderElement[] var5 = BasicHeaderValueParser.DEFAULT.parseElements(var1, new ParserCursor(var2, var1.length()));
      if (var5.length == 0) {
         throw new MalformedChallengeException("Authentication challenge is empty");
      } else {
         this.params = new HashMap(var5.length);
         var3 = var5.length;

         for(var2 = 0; var2 < var3; ++var2) {
            HeaderElement var4 = var5[var2];
            this.params.put(var4.getName(), var4.getValue());
         }

      }
   }
}
