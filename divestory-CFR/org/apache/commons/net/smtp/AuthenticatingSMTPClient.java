/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.smtp;

import java.io.IOException;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SMTPSClient;
import org.apache.commons.net.util.Base64;

public class AuthenticatingSMTPClient
extends SMTPSClient {
    public AuthenticatingSMTPClient() {
    }

    public AuthenticatingSMTPClient(String string2) {
        super(string2);
    }

    public AuthenticatingSMTPClient(String string2, String string3) {
        super(string2, false, string3);
    }

    public AuthenticatingSMTPClient(String string2, boolean bl) {
        super(string2, bl);
    }

    public AuthenticatingSMTPClient(String string2, boolean bl, String string3) {
        super(string2, bl, string3);
    }

    public AuthenticatingSMTPClient(boolean bl, SSLContext sSLContext) {
        super(bl, sSLContext);
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
        if (!SMTPReply.isPositiveIntermediate(this.sendCommand(14, AUTH_METHOD.getAuthName((AUTH_METHOD)((Object)object))))) {
            return false;
        }
        if (((Enum)object).equals((Object)AUTH_METHOD.PLAIN)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("\u0000");
            ((StringBuilder)object).append((String)arrby);
            ((StringBuilder)object).append("\u0000");
            ((StringBuilder)object).append((String)arrby2);
            return SMTPReply.isPositiveCompletion(this.sendCommand(Base64.encodeBase64StringUnChunked(((StringBuilder)object).toString().getBytes(this.getCharsetName()))));
        }
        if (((Enum)object).equals((Object)AUTH_METHOD.CRAM_MD5)) {
            object = Base64.decodeBase64(this.getReplyString().substring(4).trim());
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(new SecretKeySpec(arrby2.getBytes(this.getCharsetName()), "HmacMD5"));
            object = this._convertToHexString(mac.doFinal((byte[])object)).getBytes(this.getCharsetName());
            arrby = arrby.getBytes(this.getCharsetName());
            arrby2 = new byte[arrby.length + 1 + ((Object)object).length];
            System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
            arrby2[arrby.length] = (byte)32;
            System.arraycopy(object, 0, arrby2, arrby.length + 1, ((Object)object).length);
            return SMTPReply.isPositiveCompletion(this.sendCommand(Base64.encodeBase64StringUnChunked(arrby2)));
        }
        if (((Enum)object).equals((Object)AUTH_METHOD.LOGIN)) {
            if (SMTPReply.isPositiveIntermediate(this.sendCommand(Base64.encodeBase64StringUnChunked(arrby.getBytes(this.getCharsetName()))))) return SMTPReply.isPositiveCompletion(this.sendCommand(Base64.encodeBase64StringUnChunked(arrby2.getBytes(this.getCharsetName()))));
            return false;
        }
        if (!((Enum)object).equals((Object)AUTH_METHOD.XOAUTH)) return false;
        return SMTPReply.isPositiveIntermediate(this.sendCommand(Base64.encodeBase64StringUnChunked(arrby.getBytes(this.getCharsetName()))));
    }

    public int ehlo(String string2) throws IOException {
        return this.sendCommand(15, string2);
    }

    public boolean elogin() throws IOException {
        String string2 = this.getLocalAddress().getHostName();
        if (string2 != null) return SMTPReply.isPositiveCompletion(this.ehlo(string2));
        return false;
    }

    public boolean elogin(String string2) throws IOException {
        return SMTPReply.isPositiveCompletion(this.ehlo(string2));
    }

    public int[] getEnhancedReplyCode() {
        String[] arrstring = this.getReplyString().substring(4);
        int n = arrstring.indexOf(32);
        int n2 = 0;
        arrstring = arrstring.substring(0, n).split("\\.");
        int[] arrn = new int[arrstring.length];
        while (n2 < arrstring.length) {
            arrn[n2] = Integer.parseInt(arrstring[n2]);
            ++n2;
        }
        return arrn;
    }

    public static final class AUTH_METHOD
    extends Enum<AUTH_METHOD> {
        private static final /* synthetic */ AUTH_METHOD[] $VALUES;
        public static final /* enum */ AUTH_METHOD CRAM_MD5;
        public static final /* enum */ AUTH_METHOD LOGIN;
        public static final /* enum */ AUTH_METHOD PLAIN;
        public static final /* enum */ AUTH_METHOD XOAUTH;

        static {
            AUTH_METHOD aUTH_METHOD;
            PLAIN = new AUTH_METHOD();
            CRAM_MD5 = new AUTH_METHOD();
            LOGIN = new AUTH_METHOD();
            XOAUTH = aUTH_METHOD = new AUTH_METHOD();
            $VALUES = new AUTH_METHOD[]{PLAIN, CRAM_MD5, LOGIN, aUTH_METHOD};
        }

        public static final String getAuthName(AUTH_METHOD aUTH_METHOD) {
            if (aUTH_METHOD.equals((Object)PLAIN)) {
                return "PLAIN";
            }
            if (aUTH_METHOD.equals((Object)CRAM_MD5)) {
                return "CRAM-MD5";
            }
            if (aUTH_METHOD.equals((Object)LOGIN)) {
                return "LOGIN";
            }
            if (!aUTH_METHOD.equals((Object)XOAUTH)) return null;
            return "XOAUTH";
        }

        public static AUTH_METHOD valueOf(String string2) {
            return Enum.valueOf(AUTH_METHOD.class, string2);
        }

        public static AUTH_METHOD[] values() {
            return (AUTH_METHOD[])$VALUES.clone();
        }
    }

}

