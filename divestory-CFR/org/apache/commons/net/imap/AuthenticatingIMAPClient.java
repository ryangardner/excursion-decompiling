/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.imap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import org.apache.commons.net.imap.IMAP;
import org.apache.commons.net.imap.IMAPCommand;
import org.apache.commons.net.imap.IMAPReply;
import org.apache.commons.net.imap.IMAPSClient;
import org.apache.commons.net.util.Base64;

public class AuthenticatingIMAPClient
extends IMAPSClient {
    public AuthenticatingIMAPClient() {
        this("TLS", false);
    }

    public AuthenticatingIMAPClient(String string2) {
        this(string2, false);
    }

    public AuthenticatingIMAPClient(String string2, boolean bl) {
        this(string2, bl, null);
    }

    public AuthenticatingIMAPClient(String string2, boolean bl, SSLContext sSLContext) {
        super(string2, bl, sSLContext);
    }

    public AuthenticatingIMAPClient(SSLContext sSLContext) {
        this(false, sSLContext);
    }

    public AuthenticatingIMAPClient(boolean bl) {
        this("TLS", bl);
    }

    public AuthenticatingIMAPClient(boolean bl, SSLContext sSLContext) {
        this("TLS", bl, sSLContext);
    }

    private String _convertToHexString(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 2);
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = arrby[n2] & 255;
            if (n3 <= 15) {
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toHexString(n3));
            ++n2;
        }
        return stringBuilder.toString();
    }

    public boolean auth(AUTH_METHOD object, String arrby, String arrby2) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        boolean bl = IMAPReply.isContinuation(this.sendCommand(IMAPCommand.AUTHENTICATE, ((AUTH_METHOD)((Object)object)).getAuthName()));
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        if (!bl) {
            return false;
        }
        int n = 1.$SwitchMap$org$apache$commons$net$imap$AuthenticatingIMAPClient$AUTH_METHOD[((Enum)object).ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return false;
                    }
                    n = this.sendData((String)arrby);
                    if (n == 0) {
                        this.setState(IMAP.IMAPState.AUTH_STATE);
                    }
                    if (n != 0) return bl5;
                    return true;
                }
                if (this.sendData(Base64.encodeBase64StringUnChunked(arrby.getBytes(this.getCharsetName()))) != 3) {
                    return false;
                }
                n = this.sendData(Base64.encodeBase64StringUnChunked(arrby2.getBytes(this.getCharsetName())));
                if (n == 0) {
                    this.setState(IMAP.IMAPState.AUTH_STATE);
                }
                bl5 = bl2;
                if (n != 0) return bl5;
                return true;
            }
            byte[] arrby3 = Base64.decodeBase64(this.getReplyString().substring(2).trim());
            object = Mac.getInstance("HmacMD5");
            ((Mac)object).init(new SecretKeySpec(arrby2.getBytes(this.getCharsetName()), "HmacMD5"));
            object = this._convertToHexString(((Mac)object).doFinal(arrby3)).getBytes(this.getCharsetName());
            arrby2 = arrby.getBytes(this.getCharsetName());
            arrby = new byte[arrby2.length + 1 + ((Object)object).length];
            System.arraycopy(arrby2, 0, arrby, 0, arrby2.length);
            arrby[arrby2.length] = (byte)32;
            System.arraycopy(object, 0, arrby, arrby2.length + 1, ((Object)object).length);
            n = this.sendData(Base64.encodeBase64StringUnChunked(arrby));
            if (n == 0) {
                this.setState(IMAP.IMAPState.AUTH_STATE);
            }
            bl5 = bl3;
            if (n != 0) return bl5;
            return true;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("\u0000");
        ((StringBuilder)object).append((String)arrby);
        ((StringBuilder)object).append("\u0000");
        ((StringBuilder)object).append((String)arrby2);
        n = this.sendData(Base64.encodeBase64StringUnChunked(((StringBuilder)object).toString().getBytes(this.getCharsetName())));
        if (n == 0) {
            this.setState(IMAP.IMAPState.AUTH_STATE);
        }
        bl5 = bl4;
        if (n != 0) return bl5;
        return true;
    }

    public boolean authenticate(AUTH_METHOD aUTH_METHOD, String string2, String string3) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return this.auth(aUTH_METHOD, string2, string3);
    }

    public static final class AUTH_METHOD
    extends Enum<AUTH_METHOD> {
        private static final /* synthetic */ AUTH_METHOD[] $VALUES;
        public static final /* enum */ AUTH_METHOD CRAM_MD5;
        public static final /* enum */ AUTH_METHOD LOGIN;
        public static final /* enum */ AUTH_METHOD PLAIN;
        public static final /* enum */ AUTH_METHOD XOAUTH;
        private final String authName;

        static {
            AUTH_METHOD aUTH_METHOD;
            PLAIN = new AUTH_METHOD("PLAIN");
            CRAM_MD5 = new AUTH_METHOD("CRAM-MD5");
            LOGIN = new AUTH_METHOD("LOGIN");
            XOAUTH = aUTH_METHOD = new AUTH_METHOD("XOAUTH");
            $VALUES = new AUTH_METHOD[]{PLAIN, CRAM_MD5, LOGIN, aUTH_METHOD};
        }

        private AUTH_METHOD(String string3) {
            this.authName = string3;
        }

        public static AUTH_METHOD valueOf(String string2) {
            return Enum.valueOf(AUTH_METHOD.class, string2);
        }

        public static AUTH_METHOD[] values() {
            return (AUTH_METHOD[])$VALUES.clone();
        }

        public final String getAuthName() {
            return this.authName;
        }
    }

}

