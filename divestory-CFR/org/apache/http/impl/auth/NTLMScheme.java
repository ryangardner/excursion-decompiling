/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.NTCredentials;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

public class NTLMScheme
extends AuthSchemeBase {
    private String challenge;
    private final NTLMEngine engine;
    private State state;

    public NTLMScheme(NTLMEngine nTLMEngine) {
        if (nTLMEngine == null) throw new IllegalArgumentException("NTLM engine may not be null");
        this.engine = nTLMEngine;
        this.state = State.UNINITIATED;
        this.challenge = null;
    }

    @Override
    public Header authenticate(Credentials object, HttpRequest object2) throws AuthenticationException {
        block6 : {
            block4 : {
                block5 : {
                    try {
                        object2 = (NTCredentials)object;
                        if (this.state == State.CHALLENGE_RECEIVED || this.state == State.FAILED) break block4;
                        if (this.state != State.MSG_TYPE2_RECEVIED) break block5;
                    }
                    catch (ClassCastException classCastException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Credentials cannot be used for NTLM authentication: ");
                        stringBuilder.append(object.getClass().getName());
                        throw new InvalidCredentialsException(stringBuilder.toString());
                    }
                    object = this.engine.generateType3Msg(((NTCredentials)object2).getUserName(), ((NTCredentials)object2).getPassword(), ((NTCredentials)object2).getDomain(), ((NTCredentials)object2).getWorkstation(), this.challenge);
                    this.state = State.MSG_TYPE3_GENERATED;
                    break block6;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected state: ");
                ((StringBuilder)object).append((Object)this.state);
                throw new AuthenticationException(((StringBuilder)object).toString());
            }
            object = this.engine.generateType1Msg(((NTCredentials)object2).getDomain(), ((NTCredentials)object2).getWorkstation());
            this.state = State.MSG_TYPE1_GENERATED;
        }
        object2 = new CharArrayBuffer(32);
        if (this.isProxy()) {
            ((CharArrayBuffer)object2).append("Proxy-Authorization");
        } else {
            ((CharArrayBuffer)object2).append("Authorization");
        }
        ((CharArrayBuffer)object2).append(": NTLM ");
        ((CharArrayBuffer)object2).append((String)object);
        return new BufferedHeader((CharArrayBuffer)object2);
    }

    @Override
    public String getParameter(String string2) {
        return null;
    }

    @Override
    public String getRealm() {
        return null;
    }

    @Override
    public String getSchemeName() {
        return "ntlm";
    }

    @Override
    public boolean isComplete() {
        if (this.state == State.MSG_TYPE3_GENERATED) return true;
        if (this.state == State.FAILED) return true;
        return false;
    }

    @Override
    public boolean isConnectionBased() {
        return true;
    }

    @Override
    protected void parseChallenge(CharArrayBuffer object, int n, int n2) throws MalformedChallengeException {
        if (((String)(object = ((CharArrayBuffer)object).substringTrimmed(n, n2))).length() != 0) {
            this.state = State.MSG_TYPE2_RECEVIED;
            this.challenge = object;
            return;
        }
        this.state = this.state == State.UNINITIATED ? State.CHALLENGE_RECEIVED : State.FAILED;
        this.challenge = null;
    }

    static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State CHALLENGE_RECEIVED;
        public static final /* enum */ State FAILED;
        public static final /* enum */ State MSG_TYPE1_GENERATED;
        public static final /* enum */ State MSG_TYPE2_RECEVIED;
        public static final /* enum */ State MSG_TYPE3_GENERATED;
        public static final /* enum */ State UNINITIATED;

        static {
            State state;
            UNINITIATED = new State();
            CHALLENGE_RECEIVED = new State();
            MSG_TYPE1_GENERATED = new State();
            MSG_TYPE2_RECEVIED = new State();
            MSG_TYPE3_GENERATED = new State();
            FAILED = state = new State();
            $VALUES = new State[]{UNINITIATED, CHALLENGE_RECEIVED, MSG_TYPE1_GENERATED, MSG_TYPE2_RECEVIED, MSG_TYPE3_GENERATED, state};
        }

        public static State valueOf(String string2) {
            return Enum.valueOf(State.class, string2);
        }

        public static final State[] values() {
            return (State[])$VALUES.clone();
        }
    }

}

