/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.auth;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.impl.auth.SpnegoTokenGenerator;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class NegotiateScheme
extends AuthSchemeBase {
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
    private GSSContext gssContext = null;
    private final Log log = LogFactory.getLog(this.getClass());
    private Oid negotiationOid = null;
    private final SpnegoTokenGenerator spengoGenerator;
    private State state = State.UNINITIATED;
    private final boolean stripPort;
    private byte[] token;

    public NegotiateScheme() {
        this(null, false);
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegoTokenGenerator) {
        this(spnegoTokenGenerator, false);
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegoTokenGenerator, boolean bl) {
        this.spengoGenerator = spnegoTokenGenerator;
        this.stripPort = bl;
    }

    @Deprecated
    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public Header authenticate(Credentials var1_1, HttpRequest var2_4, HttpContext var3_6) throws AuthenticationException {
        if (var2_4 == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (this.state != State.CHALLENGE_RECEIVED) throw new IllegalStateException("Negotiation authentication process has not been initiated");
        try {
            var1_1 = this.isProxy() != false ? "http.proxy_host" : "http.target_host";
            var1_1 = (HttpHost)var3_6.getAttribute((String)var1_1);
            if (var1_1 == null) ** GOTO lbl84
            var1_1 = this.stripPort == false && var1_1.getPort() > 0 ? var1_1.toHostString() : var1_1.getHostName();
            if (this.log.isDebugEnabled()) {
                var2_4 = this.log;
                var3_6 = new StringBuilder();
                var3_6.append("init ");
                var3_6.append((String)var1_1);
                var2_4.debug((Object)var3_6.toString());
            }
            this.negotiationOid = var2_4 = new Oid("1.3.6.1.5.5.2");
            {
                catch (GSSException var1_3) {
                    this.state = State.FAILED;
                    if (var1_3.getMajor() == 9) throw new InvalidCredentialsException(var1_3.getMessage(), var1_3);
                    if (var1_3.getMajor() == 8) throw new InvalidCredentialsException(var1_3.getMessage(), var1_3);
                    if (var1_3.getMajor() == 13) throw new InvalidCredentialsException(var1_3.getMessage(), var1_3);
                    if (var1_3.getMajor() == 10) throw new AuthenticationException(var1_3.getMessage(), var1_3);
                    if (var1_3.getMajor() == 19) throw new AuthenticationException(var1_3.getMessage(), var1_3);
                    if (var1_3.getMajor() != 20) throw new AuthenticationException(var1_3.getMessage());
                    throw new AuthenticationException(var1_3.getMessage(), var1_3);
                }
            }
            try {
                var2_4 = this.getManager();
                var3_6 = new StringBuilder();
                var3_6.append("HTTP@");
                var3_6.append((String)var1_1);
                this.gssContext = var2_4 = var2_4.createContext(var2_4.createName(var3_6.toString(), GSSName.NT_HOSTBASED_SERVICE).canonicalize(this.negotiationOid), this.negotiationOid, null, 0);
                var2_4.requestMutualAuth(true);
                this.gssContext.requestCredDeleg(true);
                var4_7 = false;
                ** GOTO lbl44
            }
            catch (GSSException var2_5) {
                if (var2_5.getMajor() != 2) throw var2_5;
                this.log.debug((Object)"GSSException BAD_MECH, retry with Kerberos MECH");
                var4_7 = true;
lbl44: // 2 sources:
                if (var4_7) {
                    this.log.debug((Object)"Using Kerberos MECH 1.2.840.113554.1.2.2");
                    this.negotiationOid = var2_4 = new Oid("1.2.840.113554.1.2.2");
                    var2_4 = this.getManager();
                    var3_6 = new StringBuilder();
                    var3_6.append("HTTP@");
                    var3_6.append((String)var1_1);
                    this.gssContext = var1_1 = var2_4.createContext(var2_4.createName(var3_6.toString(), GSSName.NT_HOSTBASED_SERVICE).canonicalize(this.negotiationOid), this.negotiationOid, null, 0);
                    var1_1.requestMutualAuth(true);
                    this.gssContext.requestCredDeleg(true);
                }
                if (this.token == null) {
                    this.token = new byte[0];
                }
                var1_1 = this.gssContext.initSecContext(this.token, 0, this.token.length);
                this.token = var1_1;
                if (var1_1 == null) {
                    this.state = State.FAILED;
                    var1_1 = new AuthenticationException("GSS security context initialization failed");
                    throw var1_1;
                }
                if (this.spengoGenerator != null && this.negotiationOid.toString().equals("1.2.840.113554.1.2.2")) {
                    this.token = this.spengoGenerator.generateSpnegoDERObject(this.token);
                }
                this.state = State.TOKEN_GENERATED;
                var1_1 = new String(Base64.encodeBase64(this.token, false));
                if (this.log.isDebugEnabled()) {
                    var2_4 = this.log;
                    var3_6 = new StringBuilder();
                    var3_6.append("Sending response '");
                    var3_6.append((String)var1_1);
                    var3_6.append("' back to the auth server");
                    var2_4.debug((Object)var3_6.toString());
                }
                var2_4 = new StringBuilder();
                var2_4.append("Negotiate ");
                var2_4.append((String)var1_1);
                return new BasicHeader("Authorization", var2_4.toString());
lbl84: // 1 sources:
                var1_1 = new AuthenticationException("Authentication host is not set in the execution context");
                throw var1_1;
            }
        }
        catch (IOException var1_2) {
            this.state = State.FAILED;
            throw new AuthenticationException(var1_2.getMessage());
        }
    }

    protected GSSManager getManager() {
        return GSSManager.getInstance();
    }

    @Override
    public String getParameter(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Parameter name may not be null");
        return null;
    }

    @Override
    public String getRealm() {
        return null;
    }

    @Override
    public String getSchemeName() {
        return "Negotiate";
    }

    @Override
    public boolean isComplete() {
        if (this.state == State.TOKEN_GENERATED) return true;
        if (this.state == State.FAILED) return true;
        return false;
    }

    @Override
    public boolean isConnectionBased() {
        return true;
    }

    @Override
    protected void parseChallenge(CharArrayBuffer object, int n, int n2) throws MalformedChallengeException {
        object = ((CharArrayBuffer)object).substringTrimmed(n, n2);
        if (this.log.isDebugEnabled()) {
            Log log = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Received challenge '");
            stringBuilder.append((String)object);
            stringBuilder.append("' from the auth server");
            log.debug((Object)stringBuilder.toString());
        }
        if (this.state == State.UNINITIATED) {
            this.token = new Base64().decode(((String)object).getBytes());
            this.state = State.CHALLENGE_RECEIVED;
            return;
        }
        this.log.debug((Object)"Authentication already attempted");
        this.state = State.FAILED;
    }

    static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State CHALLENGE_RECEIVED;
        public static final /* enum */ State FAILED;
        public static final /* enum */ State TOKEN_GENERATED;
        public static final /* enum */ State UNINITIATED;

        static {
            State state;
            UNINITIATED = new State();
            CHALLENGE_RECEIVED = new State();
            TOKEN_GENERATED = new State();
            FAILED = state = new State();
            $VALUES = new State[]{UNINITIATED, CHALLENGE_RECEIVED, TOKEN_GENERATED, state};
        }

        public static State valueOf(String string2) {
            return Enum.valueOf(State.class, string2);
        }

        public static final State[] values() {
            return (State[])$VALUES.clone();
        }
    }

}

