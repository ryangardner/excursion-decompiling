/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.pop3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.net.pop3.POP3SClient;
import org.apache.commons.net.util.Base64;

public class ExtendedPOP3Client
extends POP3SClient {
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
        int n = this.sendCommand(13, ((AUTH_METHOD)((Object)object)).getAuthName());
        boolean bl = false;
        boolean bl2 = false;
        if (n != 2) {
            return false;
        }
        n = 1.$SwitchMap$org$apache$commons$net$pop3$ExtendedPOP3Client$AUTH_METHOD[((Enum)object).ordinal()];
        if (n == 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("\u0000");
            ((StringBuilder)object).append((String)arrby);
            ((StringBuilder)object).append("\u0000");
            ((StringBuilder)object).append((String)arrby2);
            bl2 = bl;
            if (this.sendCommand(new String(Base64.encodeBase64(((StringBuilder)object).toString().getBytes(this.getCharsetName())), this.getCharsetName())) != 0) return bl2;
            return true;
        }
        if (n != 2) {
            return false;
        }
        object = Base64.decodeBase64(this.getReplyString().substring(2).trim());
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(new SecretKeySpec(arrby2.getBytes(this.getCharsetName()), "HmacMD5"));
        object = this._convertToHexString(mac.doFinal((byte[])object)).getBytes(this.getCharsetName());
        arrby2 = arrby.getBytes(this.getCharsetName());
        arrby = new byte[arrby2.length + 1 + ((Object)object).length];
        System.arraycopy(arrby2, 0, arrby, 0, arrby2.length);
        arrby[arrby2.length] = (byte)32;
        System.arraycopy(object, 0, arrby, arrby2.length + 1, ((Object)object).length);
        if (this.sendCommand(Base64.encodeBase64StringUnChunked(arrby)) != 0) return bl2;
        return true;
    }

    public static final class AUTH_METHOD
    extends Enum<AUTH_METHOD> {
        private static final /* synthetic */ AUTH_METHOD[] $VALUES;
        public static final /* enum */ AUTH_METHOD CRAM_MD5;
        public static final /* enum */ AUTH_METHOD PLAIN;
        private final String methodName;

        static {
            AUTH_METHOD aUTH_METHOD;
            PLAIN = new AUTH_METHOD("PLAIN");
            CRAM_MD5 = aUTH_METHOD = new AUTH_METHOD("CRAM-MD5");
            $VALUES = new AUTH_METHOD[]{PLAIN, aUTH_METHOD};
        }

        private AUTH_METHOD(String string3) {
            this.methodName = string3;
        }

        public static AUTH_METHOD valueOf(String string2) {
            return Enum.valueOf(AUTH_METHOD.class, string2);
        }

        public static AUTH_METHOD[] values() {
            return (AUTH_METHOD[])$VALUES.clone();
        }

        public final String getAuthName() {
            return this.methodName;
        }
    }

}

