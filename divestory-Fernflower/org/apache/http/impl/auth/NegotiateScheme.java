package org.apache.http.impl.auth;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.Oid;

public class NegotiateScheme extends AuthSchemeBase {
   private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
   private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
   private GSSContext gssContext;
   private final Log log;
   private Oid negotiationOid;
   private final SpnegoTokenGenerator spengoGenerator;
   private NegotiateScheme.State state;
   private final boolean stripPort;
   private byte[] token;

   public NegotiateScheme() {
      this((SpnegoTokenGenerator)null, false);
   }

   public NegotiateScheme(SpnegoTokenGenerator var1) {
      this(var1, false);
   }

   public NegotiateScheme(SpnegoTokenGenerator var1, boolean var2) {
      this.log = LogFactory.getLog(this.getClass());
      this.gssContext = null;
      this.negotiationOid = null;
      this.state = NegotiateScheme.State.UNINITIATED;
      this.spengoGenerator = var1;
      this.stripPort = var2;
   }

   @Deprecated
   public Header authenticate(Credentials var1, HttpRequest var2) throws AuthenticationException {
      return this.authenticate(var1, var2, (HttpContext)null);
   }

   public Header authenticate(Credentials param1, HttpRequest param2, HttpContext param3) throws AuthenticationException {
      // $FF: Couldn't be decompiled
   }

   protected GSSManager getManager() {
      return GSSManager.getInstance();
   }

   public String getParameter(String var1) {
      if (var1 != null) {
         return null;
      } else {
         throw new IllegalArgumentException("Parameter name may not be null");
      }
   }

   public String getRealm() {
      return null;
   }

   public String getSchemeName() {
      return "Negotiate";
   }

   public boolean isComplete() {
      boolean var1;
      if (this.state != NegotiateScheme.State.TOKEN_GENERATED && this.state != NegotiateScheme.State.FAILED) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isConnectionBased() {
      return true;
   }

   protected void parseChallenge(CharArrayBuffer var1, int var2, int var3) throws MalformedChallengeException {
      String var6 = var1.substringTrimmed(var2, var3);
      if (this.log.isDebugEnabled()) {
         Log var4 = this.log;
         StringBuilder var5 = new StringBuilder();
         var5.append("Received challenge '");
         var5.append(var6);
         var5.append("' from the auth server");
         var4.debug(var5.toString());
      }

      if (this.state == NegotiateScheme.State.UNINITIATED) {
         this.token = (new Base64()).decode(var6.getBytes());
         this.state = NegotiateScheme.State.CHALLENGE_RECEIVED;
      } else {
         this.log.debug("Authentication already attempted");
         this.state = NegotiateScheme.State.FAILED;
      }

   }

   static enum State {
      CHALLENGE_RECEIVED,
      FAILED,
      TOKEN_GENERATED,
      UNINITIATED;

      static {
         NegotiateScheme.State var0 = new NegotiateScheme.State("FAILED", 3);
         FAILED = var0;
      }
   }
}
