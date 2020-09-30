/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineException;
import org.apache.http.util.EncodingUtils;

final class NTLMEngineImpl
implements NTLMEngine {
    static final String DEFAULT_CHARSET = "ASCII";
    protected static final int FLAG_NEGOTIATE_128 = 536870912;
    protected static final int FLAG_NEGOTIATE_ALWAYS_SIGN = 32768;
    protected static final int FLAG_NEGOTIATE_KEY_EXCH = 1073741824;
    protected static final int FLAG_NEGOTIATE_NTLM = 512;
    protected static final int FLAG_NEGOTIATE_NTLM2 = 524288;
    protected static final int FLAG_NEGOTIATE_SEAL = 32;
    protected static final int FLAG_NEGOTIATE_SIGN = 16;
    protected static final int FLAG_TARGET_DESIRED = 4;
    protected static final int FLAG_UNICODE_ENCODING = 1;
    private static final SecureRandom RND_GEN;
    private static byte[] SIGNATURE;
    private String credentialCharset = "ASCII";

    static {
        byte[] arrby;
        try {
            arrby = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (Exception exception) {
            arrby = null;
        }
        RND_GEN = arrby;
        arrby = EncodingUtils.getBytes("NTLMSSP", DEFAULT_CHARSET);
        byte[] arrby2 = new byte[arrby.length + 1];
        SIGNATURE = arrby2;
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        NTLMEngineImpl.SIGNATURE[arrby.length] = (byte)(false ? 1 : 0);
    }

    NTLMEngineImpl() {
    }

    static int F(int n, int n2, int n3) {
        return n & n3 | n2 & n;
    }

    static int G(int n, int n2, int n3) {
        return n & n3 | n & n2 | n2 & n3;
    }

    static int H(int n, int n2, int n3) {
        return n ^ n2 ^ n3;
    }

    static /* synthetic */ byte[] access$600() throws NTLMEngineException {
        return NTLMEngineImpl.makeRandomChallenge();
    }

    static /* synthetic */ byte[] access$700() throws NTLMEngineException {
        return NTLMEngineImpl.makeNTLM2RandomChallenge();
    }

    private static String convertDomain(String string2) {
        return NTLMEngineImpl.stripDotSuffix(string2);
    }

    private static String convertHost(String string2) {
        return NTLMEngineImpl.stripDotSuffix(string2);
    }

    private static byte[] createBlob(byte[] arrby, byte[] arrby2) {
        long l = (System.currentTimeMillis() + 11644473600000L) * 10000L;
        byte[] arrby3 = new byte[8];
        int n = 0;
        do {
            if (n >= 8) {
                byte[] arrby4 = new byte[arrby2.length + 28];
                System.arraycopy(new byte[]{1, 1, 0, 0}, 0, arrby4, 0, 4);
                System.arraycopy(new byte[]{0, 0, 0, 0}, 0, arrby4, 4, 4);
                System.arraycopy(arrby3, 0, arrby4, 8, 8);
                System.arraycopy(arrby, 0, arrby4, 16, 8);
                System.arraycopy(new byte[]{0, 0, 0, 0}, 0, arrby4, 24, 4);
                System.arraycopy(arrby2, 0, arrby4, 28, arrby2.length);
                return arrby4;
            }
            arrby3[n] = (byte)l;
            l >>>= 8;
            ++n;
        } while (true);
    }

    private static Key createDESKey(byte[] arrby, int n) {
        byte[] arrby2 = new byte[7];
        System.arraycopy(arrby, n, arrby2, 0, 7);
        arrby = new byte[]{arrby2[0], (byte)(arrby2[0] << 7 | (arrby2[1] & 255) >>> 1), (byte)(arrby2[1] << 6 | (arrby2[2] & 255) >>> 2), (byte)(arrby2[2] << 5 | (arrby2[3] & 255) >>> 3), (byte)(arrby2[3] << 4 | (arrby2[4] & 255) >>> 4), (byte)(arrby2[4] << 3 | (arrby2[5] & 255) >>> 5), (byte)(arrby2[5] << 2 | (arrby2[6] & 255) >>> 6), (byte)(arrby2[6] << 1)};
        NTLMEngineImpl.oddParity(arrby);
        return new SecretKeySpec(arrby, "DES");
    }

    static byte[] getLMResponse(String string2, byte[] arrby) throws NTLMEngineException {
        return NTLMEngineImpl.lmResponse(NTLMEngineImpl.lmHash(string2), arrby);
    }

    static byte[] getLMv2Response(String string2, String string3, String string4, byte[] arrby, byte[] arrby2) throws NTLMEngineException {
        return NTLMEngineImpl.lmv2Response(NTLMEngineImpl.ntlmv2Hash(string2, string3, string4), arrby, arrby2);
    }

    static byte[] getNTLM2SessionResponse(String arrby, byte[] arrby2, byte[] arrby3) throws NTLMEngineException {
        try {
            arrby = NTLMEngineImpl.ntlmHash((String)arrby);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(arrby2);
            messageDigest.update(arrby3);
            arrby3 = messageDigest.digest();
            arrby2 = new byte[8];
            System.arraycopy(arrby3, 0, arrby2, 0, 8);
            return NTLMEngineImpl.lmResponse(arrby, arrby2);
        }
        catch (Exception exception) {
            if (!(exception instanceof NTLMEngineException)) throw new NTLMEngineException(exception.getMessage(), exception);
            throw (NTLMEngineException)exception;
        }
    }

    static byte[] getNTLMResponse(String string2, byte[] arrby) throws NTLMEngineException {
        return NTLMEngineImpl.lmResponse(NTLMEngineImpl.ntlmHash(string2), arrby);
    }

    static byte[] getNTLMv2Response(String string2, String string3, String string4, byte[] arrby, byte[] arrby2, byte[] arrby3) throws NTLMEngineException {
        return NTLMEngineImpl.lmv2Response(NTLMEngineImpl.ntlmv2Hash(string2, string3, string4), arrby, NTLMEngineImpl.createBlob(arrby2, arrby3));
    }

    private static byte[] lmHash(String object) throws NTLMEngineException {
        try {
            object = object.toUpperCase().getBytes("US-ASCII");
            int n = Math.min(((byte[])object).length, 14);
            Object object2 = new byte[14];
            System.arraycopy(object, 0, object2, 0, n);
            object = NTLMEngineImpl.createDESKey(object2, 0);
            object2 = NTLMEngineImpl.createDESKey(object2, 7);
            byte[] arrby = "KGS!@#$%".getBytes("US-ASCII");
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, (Key)object);
            object = cipher.doFinal(arrby);
            cipher.init(1, (Key)object2);
            arrby = cipher.doFinal(arrby);
            object2 = new byte[16];
            System.arraycopy(object, 0, object2, 0, 8);
            System.arraycopy(arrby, 0, object2, 8, 8);
            return object2;
        }
        catch (Exception exception) {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
    }

    private static byte[] lmResponse(byte[] object, byte[] arrby) throws NTLMEngineException {
        try {
            Object object2 = new byte[21];
            System.arraycopy(object, 0, object2, 0, 16);
            object = NTLMEngineImpl.createDESKey(object2, 0);
            byte[] arrby2 = NTLMEngineImpl.createDESKey(object2, 7);
            object2 = NTLMEngineImpl.createDESKey(object2, 14);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, (Key)object);
            object = cipher.doFinal(arrby);
            cipher.init(1, (Key)arrby2);
            arrby2 = cipher.doFinal(arrby);
            cipher.init(1, (Key)object2);
            object2 = cipher.doFinal(arrby);
            arrby = new byte[24];
            System.arraycopy(object, 0, arrby, 0, 8);
            System.arraycopy(arrby2, 0, arrby, 8, 8);
            System.arraycopy(object2, 0, arrby, 16, 8);
            return arrby;
        }
        catch (Exception exception) {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
    }

    private static byte[] lmv2Response(byte[] object, byte[] arrby, byte[] arrby2) throws NTLMEngineException {
        object = new HMACMD5((byte[])object);
        ((HMACMD5)object).update(arrby);
        ((HMACMD5)object).update(arrby2);
        arrby = ((HMACMD5)object).getOutput();
        object = new byte[arrby.length + arrby2.length];
        System.arraycopy(arrby, 0, object, 0, arrby.length);
        System.arraycopy(arrby2, 0, object, arrby.length, arrby2.length);
        return object;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private static byte[] makeNTLM2RandomChallenge() throws NTLMEngineException {
        SecureRandom secureRandom = RND_GEN;
        if (secureRandom == null) throw new NTLMEngineException("Random generator not available");
        byte[] arrby = new byte[24];
        synchronized (secureRandom) {
            RND_GEN.nextBytes(arrby);
        }
        Arrays.fill(arrby, 8, 24, (byte)0);
        return arrby;
    }

    private static byte[] makeRandomChallenge() throws NTLMEngineException {
        SecureRandom secureRandom = RND_GEN;
        if (secureRandom == null) throw new NTLMEngineException("Random generator not available");
        byte[] arrby = new byte[8];
        synchronized (secureRandom) {
            RND_GEN.nextBytes(arrby);
            return arrby;
        }
    }

    private static byte[] ntlmHash(String arrby) throws NTLMEngineException {
        try {
            byte[] arrby2 = arrby.getBytes("UnicodeLittleUnmarked");
            arrby = new MD4();
            arrby.update(arrby2);
            return arrby.getOutput();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            arrby = new StringBuilder();
            arrby.append("Unicode not supported: ");
            arrby.append(unsupportedEncodingException.getMessage());
            throw new NTLMEngineException(arrby.toString(), unsupportedEncodingException);
        }
    }

    private static byte[] ntlmv2Hash(String arrby, String charSequence, String object) throws NTLMEngineException {
        try {
            byte[] arrby2 = NTLMEngineImpl.ntlmHash((String)object);
            object = new HMACMD5(arrby2);
            ((HMACMD5)object).update(((String)charSequence).toUpperCase().getBytes("UnicodeLittleUnmarked"));
            ((HMACMD5)object).update(arrby.getBytes("UnicodeLittleUnmarked"));
            return ((HMACMD5)object).getOutput();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unicode not supported! ");
            ((StringBuilder)charSequence).append(unsupportedEncodingException.getMessage());
            throw new NTLMEngineException(((StringBuilder)charSequence).toString(), unsupportedEncodingException);
        }
    }

    private static void oddParity(byte[] arrby) {
        int n = 0;
        while (n < arrby.length) {
            byte by = arrby[n];
            by = ((by >>> 1 ^ (by >>> 7 ^ by >>> 6 ^ by >>> 5 ^ by >>> 4 ^ by >>> 3 ^ by >>> 2)) & 1) == 0 ? (byte)1 : 0;
            arrby[n] = by != 0 ? (byte)((byte)(arrby[n] | 1)) : (byte)((byte)(arrby[n] & -2));
            ++n;
        }
    }

    private static byte[] readSecurityBuffer(byte[] arrby, int n) throws NTLMEngineException {
        int n2 = NTLMEngineImpl.readUShort(arrby, n);
        if (arrby.length < (n = NTLMEngineImpl.readULong(arrby, n + 4)) + n2) throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return arrby2;
    }

    private static int readULong(byte[] arrby, int n) throws NTLMEngineException {
        if (arrby.length < n + 4) throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
        byte by = arrby[n];
        byte by2 = arrby[n + 1];
        byte by3 = arrby[n + 2];
        return (arrby[n + 3] & 255) << 24 | (by & 255 | (by2 & 255) << 8 | (by3 & 255) << 16);
    }

    private static int readUShort(byte[] arrby, int n) throws NTLMEngineException {
        if (arrby.length < n + 2) throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
        byte by = arrby[n];
        return (arrby[n + 1] & 255) << 8 | by & 255;
    }

    static int rotintlft(int n, int n2) {
        return n >>> 32 - n2 | n << n2;
    }

    private static String stripDotSuffix(String string2) {
        int n = string2.indexOf(".");
        String string3 = string2;
        if (n == -1) return string3;
        return string2.substring(0, n);
    }

    static void writeULong(byte[] arrby, int n, int n2) {
        arrby[n2] = (byte)(n & 255);
        arrby[n2 + 1] = (byte)(n >> 8 & 255);
        arrby[n2 + 2] = (byte)(n >> 16 & 255);
        arrby[n2 + 3] = (byte)(n >> 24 & 255);
    }

    @Override
    public String generateType1Msg(String string2, String string3) throws NTLMEngineException {
        return this.getType1Message(string3, string2);
    }

    @Override
    public String generateType3Msg(String string2, String string3, String string4, String string5, String object) throws NTLMEngineException {
        object = new Type2Message((String)object);
        return this.getType3Message(string2, string3, string5, string4, ((Type2Message)object).getChallenge(), ((Type2Message)object).getFlags(), ((Type2Message)object).getTarget(), ((Type2Message)object).getTargetInfo());
    }

    String getCredentialCharset() {
        return this.credentialCharset;
    }

    final String getResponseFor(String object, String string2, String string3, String string4, String string5) throws NTLMEngineException {
        if (object == null) return this.getType1Message(string4, string5);
        if (((String)object).trim().equals("")) return this.getType1Message(string4, string5);
        object = new Type2Message((String)object);
        return this.getType3Message(string2, string3, string4, string5, ((Type2Message)object).getChallenge(), ((Type2Message)object).getFlags(), ((Type2Message)object).getTarget(), ((Type2Message)object).getTargetInfo());
    }

    String getType1Message(String string2, String string3) throws NTLMEngineException {
        return new Type1Message(string3, string2).getResponse();
    }

    String getType3Message(String string2, String string3, String string4, String string5, byte[] arrby, int n, String string6, byte[] arrby2) throws NTLMEngineException {
        return new Type3Message(string5, string4, string2, string3, arrby, n, string6, arrby2).getResponse();
    }

    void setCredentialCharset(String string2) {
        this.credentialCharset = string2;
    }

    static class HMACMD5 {
        protected byte[] ipad;
        protected MessageDigest md5;
        protected byte[] opad;

        HMACMD5(byte[] object) throws NTLMEngineException {
            MessageDigest messageDigest;
            int n;
            int n2;
            try {
                this.md5 = messageDigest = MessageDigest.getInstance("MD5");
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error getting md5 message digest implementation: ");
                ((StringBuilder)object).append(exception.getMessage());
                throw new NTLMEngineException(((StringBuilder)object).toString(), exception);
            }
            this.ipad = new byte[64];
            this.opad = new byte[64];
            int n3 = n2 = ((Object)object).length;
            byte[] arrby = object;
            if (n2 > 64) {
                messageDigest.update((byte[])object);
                arrby = this.md5.digest();
                n3 = arrby.length;
            }
            n2 = 0;
            do {
                n = ++n2;
                if (n2 >= n3) break;
                this.ipad[n2] = (byte)(54 ^ arrby[n2]);
                this.opad[n2] = (byte)(92 ^ arrby[n2]);
            } while (true);
            do {
                if (n >= 64) {
                    this.md5.reset();
                    this.md5.update(this.ipad);
                    return;
                }
                this.ipad[n] = (byte)54;
                this.opad[n] = (byte)92;
                ++n;
            } while (true);
        }

        byte[] getOutput() {
            byte[] arrby = this.md5.digest();
            this.md5.update(this.opad);
            return this.md5.digest(arrby);
        }

        void update(byte[] arrby) {
            this.md5.update(arrby);
        }

        void update(byte[] arrby, int n, int n2) {
            this.md5.update(arrby, n, n2);
        }
    }

    static class MD4 {
        protected int A = 1732584193;
        protected int B = -271733879;
        protected int C = -1732584194;
        protected int D = 271733878;
        protected long count = 0L;
        protected byte[] dataBuffer = new byte[64];

        MD4() {
        }

        byte[] getOutput() {
            int n = (int)(this.count & 63L);
            n = n < 56 ? 56 - n : 120 - n;
            byte[] arrby = new byte[n + 8];
            arrby[0] = (byte)-128;
            int n2 = 0;
            do {
                if (n2 >= 8) {
                    this.update(arrby);
                    arrby = new byte[16];
                    NTLMEngineImpl.writeULong(arrby, this.A, 0);
                    NTLMEngineImpl.writeULong(arrby, this.B, 4);
                    NTLMEngineImpl.writeULong(arrby, this.C, 8);
                    NTLMEngineImpl.writeULong(arrby, this.D, 12);
                    return arrby;
                }
                arrby[n + n2] = (byte)(this.count * 8L >>> n2 * 8);
                ++n2;
            } while (true);
        }

        protected void processBuffer() {
            int[] arrn = new int[16];
            int n = 0;
            do {
                int n2;
                if (n >= 16) {
                    n = this.A;
                    int n3 = this.B;
                    int n4 = this.C;
                    n2 = this.D;
                    this.round1(arrn);
                    this.round2(arrn);
                    this.round3(arrn);
                    this.A += n;
                    this.B += n3;
                    this.C += n4;
                    this.D += n2;
                    return;
                }
                byte[] arrby = this.dataBuffer;
                n2 = n * 4;
                arrn[n] = (arrby[n2] & 255) + ((arrby[n2 + 1] & 255) << 8) + ((arrby[n2 + 2] & 255) << 16) + ((arrby[n2 + 3] & 255) << 24);
                ++n;
            } while (true);
        }

        protected void round1(int[] arrn) {
            int n;
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + arrn[0], 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(n, this.B, this.C) + arrn[1], 7);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(n, this.A, this.B) + arrn[2], 11);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(n, this.D, this.A) + arrn[3], 19);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(n, this.C, this.D) + arrn[4], 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(n, this.B, this.C) + arrn[5], 7);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(n, this.A, this.B) + arrn[6], 11);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(n, this.D, this.A) + arrn[7], 19);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(n, this.C, this.D) + arrn[8], 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(n, this.B, this.C) + arrn[9], 7);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(n, this.A, this.B) + arrn[10], 11);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(n, this.D, this.A) + arrn[11], 19);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(n, this.C, this.D) + arrn[12], 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(n, this.B, this.C) + arrn[13], 7);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(n, this.A, this.B) + arrn[14], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(n, this.D, this.A) + arrn[15], 19);
        }

        protected void round2(int[] arrn) {
            int n;
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + arrn[0] + 1518500249, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(n, this.B, this.C) + arrn[4] + 1518500249, 5);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(n, this.A, this.B) + arrn[8] + 1518500249, 9);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(n, this.D, this.A) + arrn[12] + 1518500249, 13);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(n, this.C, this.D) + arrn[1] + 1518500249, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(n, this.B, this.C) + arrn[5] + 1518500249, 5);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(n, this.A, this.B) + arrn[9] + 1518500249, 9);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(n, this.D, this.A) + arrn[13] + 1518500249, 13);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(n, this.C, this.D) + arrn[2] + 1518500249, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(n, this.B, this.C) + arrn[6] + 1518500249, 5);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(n, this.A, this.B) + arrn[10] + 1518500249, 9);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(n, this.D, this.A) + arrn[14] + 1518500249, 13);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(n, this.C, this.D) + arrn[3] + 1518500249, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(n, this.B, this.C) + arrn[7] + 1518500249, 5);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(n, this.A, this.B) + arrn[11] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(n, this.D, this.A) + arrn[15] + 1518500249, 13);
        }

        protected void round3(int[] arrn) {
            int n;
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + arrn[0] + 1859775393, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(n, this.B, this.C) + arrn[8] + 1859775393, 9);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(n, this.A, this.B) + arrn[4] + 1859775393, 11);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(n, this.D, this.A) + arrn[12] + 1859775393, 15);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(n, this.C, this.D) + arrn[2] + 1859775393, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(n, this.B, this.C) + arrn[10] + 1859775393, 9);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(n, this.A, this.B) + arrn[6] + 1859775393, 11);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(n, this.D, this.A) + arrn[14] + 1859775393, 15);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(n, this.C, this.D) + arrn[1] + 1859775393, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(n, this.B, this.C) + arrn[9] + 1859775393, 9);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(n, this.A, this.B) + arrn[5] + 1859775393, 11);
            this.B = n = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(n, this.D, this.A) + arrn[13] + 1859775393, 15);
            this.A = n = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(n, this.C, this.D) + arrn[3] + 1859775393, 3);
            this.D = n = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(n, this.B, this.C) + arrn[11] + 1859775393, 9);
            this.C = n = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(n, this.A, this.B) + arrn[7] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(n, this.D, this.A) + arrn[15] + 1859775393, 15);
        }

        void update(byte[] arrby) {
            int n = (int)(this.count & 63L);
            int n2 = 0;
            do {
                int n3;
                byte[] arrby2;
                if ((n3 = arrby.length) - n2 + n < (arrby2 = this.dataBuffer).length) {
                    if (n2 >= arrby.length) return;
                    n3 = arrby.length - n2;
                    System.arraycopy(arrby, n2, arrby2, n, n3);
                    this.count += (long)n3;
                    return;
                }
                n3 = arrby2.length - n;
                System.arraycopy(arrby, n2, arrby2, n, n3);
                this.count += (long)n3;
                n2 += n3;
                this.processBuffer();
                n = 0;
            } while (true);
        }
    }

    static class NTLMMessage {
        private int currentOutputPosition = 0;
        private byte[] messageContents = null;

        NTLMMessage() {
        }

        NTLMMessage(String object, int n) throws NTLMEngineException {
            int n2;
            object = Base64.decodeBase64(EncodingUtils.getBytes((String)object, NTLMEngineImpl.DEFAULT_CHARSET));
            this.messageContents = object;
            if (((byte[])object).length < SIGNATURE.length) throw new NTLMEngineException("NTLM message decoding error - packet too short");
            for (n2 = 0; n2 < SIGNATURE.length; ++n2) {
                if (this.messageContents[n2] != SIGNATURE[n2]) throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
            }
            n2 = this.readULong(SIGNATURE.length);
            if (n2 == n) {
                this.currentOutputPosition = this.messageContents.length;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("NTLM type ");
            ((StringBuilder)object).append(Integer.toString(n));
            ((StringBuilder)object).append(" message expected - instead got type ");
            ((StringBuilder)object).append(Integer.toString(n2));
            throw new NTLMEngineException(((StringBuilder)object).toString());
        }

        protected void addByte(byte by) {
            byte[] arrby = this.messageContents;
            int n = this.currentOutputPosition;
            arrby[n] = by;
            this.currentOutputPosition = n + 1;
        }

        protected void addBytes(byte[] arrby) {
            int n = 0;
            while (n < arrby.length) {
                byte[] arrby2 = this.messageContents;
                int n2 = this.currentOutputPosition;
                arrby2[n2] = arrby[n];
                this.currentOutputPosition = n2 + 1;
                ++n;
            }
        }

        protected void addULong(int n) {
            this.addByte((byte)(n & 255));
            this.addByte((byte)(n >> 8 & 255));
            this.addByte((byte)(n >> 16 & 255));
            this.addByte((byte)(n >> 24 & 255));
        }

        protected void addUShort(int n) {
            this.addByte((byte)(n & 255));
            this.addByte((byte)(n >> 8 & 255));
        }

        protected int getMessageLength() {
            return this.currentOutputPosition;
        }

        protected int getPreambleLength() {
            return SIGNATURE.length + 4;
        }

        String getResponse() {
            byte[] arrby = this.messageContents;
            int n = arrby.length;
            int n2 = this.currentOutputPosition;
            if (n <= n2) return EncodingUtils.getAsciiString(Base64.encodeBase64(arrby));
            byte[] arrby2 = new byte[n2];
            n = 0;
            do {
                arrby = arrby2;
                if (n >= this.currentOutputPosition) return EncodingUtils.getAsciiString(Base64.encodeBase64(arrby));
                arrby2[n] = this.messageContents[n];
                ++n;
            } while (true);
        }

        protected void prepareResponse(int n, int n2) {
            this.messageContents = new byte[n];
            this.currentOutputPosition = 0;
            this.addBytes(SIGNATURE);
            this.addULong(n2);
        }

        protected byte readByte(int n) throws NTLMEngineException {
            byte[] arrby = this.messageContents;
            if (arrby.length < n + 1) throw new NTLMEngineException("NTLM: Message too short");
            return arrby[n];
        }

        protected void readBytes(byte[] arrby, int n) throws NTLMEngineException {
            byte[] arrby2 = this.messageContents;
            if (arrby2.length < arrby.length + n) throw new NTLMEngineException("NTLM: Message too short");
            System.arraycopy(arrby2, n, arrby, 0, arrby.length);
        }

        protected byte[] readSecurityBuffer(int n) throws NTLMEngineException {
            return NTLMEngineImpl.readSecurityBuffer(this.messageContents, n);
        }

        protected int readULong(int n) throws NTLMEngineException {
            return NTLMEngineImpl.readULong(this.messageContents, n);
        }

        protected int readUShort(int n) throws NTLMEngineException {
            return NTLMEngineImpl.readUShort(this.messageContents, n);
        }
    }

    static class Type1Message
    extends NTLMMessage {
        protected byte[] domainBytes;
        protected byte[] hostBytes;

        Type1Message(String string2, String charSequence) throws NTLMEngineException {
            try {
                charSequence = NTLMEngineImpl.convertHost((String)charSequence);
                string2 = NTLMEngineImpl.convertDomain(string2);
                this.hostBytes = ((String)charSequence).getBytes("UnicodeLittleUnmarked");
                this.domainBytes = string2.toUpperCase().getBytes("UnicodeLittleUnmarked");
                return;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unicode unsupported: ");
                ((StringBuilder)charSequence).append(unsupportedEncodingException.getMessage());
                throw new NTLMEngineException(((StringBuilder)charSequence).toString(), unsupportedEncodingException);
            }
        }

        @Override
        String getResponse() {
            this.prepareResponse(this.hostBytes.length + 32 + this.domainBytes.length, 1);
            this.addULong(537395765);
            this.addUShort(this.domainBytes.length);
            this.addUShort(this.domainBytes.length);
            this.addULong(this.hostBytes.length + 32);
            this.addUShort(this.hostBytes.length);
            this.addUShort(this.hostBytes.length);
            this.addULong(32);
            this.addBytes(this.hostBytes);
            this.addBytes(this.domainBytes);
            return super.getResponse();
        }
    }

    static class Type2Message
    extends NTLMMessage {
        protected byte[] challenge;
        protected int flags;
        protected String target;
        protected byte[] targetInfo;

        Type2Message(String object) throws NTLMEngineException {
            super((String)object, 2);
            int n;
            object = new byte[8];
            this.challenge = object;
            this.readBytes((byte[])object, 24);
            this.flags = n = this.readULong(20);
            if ((n & 1) == 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("NTLM type 2 message has flags that make no sense: ");
                ((StringBuilder)object).append(Integer.toString(this.flags));
                throw new NTLMEngineException(((StringBuilder)object).toString());
            }
            this.target = null;
            if (this.getMessageLength() >= 20 && ((Object)(object = this.readSecurityBuffer(12))).length != 0) {
                try {
                    String string2;
                    this.target = string2 = new String((byte[])object, "UnicodeLittleUnmarked");
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new NTLMEngineException(unsupportedEncodingException.getMessage(), unsupportedEncodingException);
                }
            }
            this.targetInfo = null;
            if (this.getMessageLength() < 48) return;
            object = this.readSecurityBuffer(40);
            if (((Object)object).length == 0) return;
            this.targetInfo = object;
        }

        byte[] getChallenge() {
            return this.challenge;
        }

        int getFlags() {
            return this.flags;
        }

        String getTarget() {
            return this.target;
        }

        byte[] getTargetInfo() {
            return this.targetInfo;
        }
    }

    static class Type3Message
    extends NTLMMessage {
        protected byte[] domainBytes;
        protected byte[] hostBytes;
        protected byte[] lmResp;
        protected byte[] ntResp;
        protected int type2Flags;
        protected byte[] userBytes;

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        Type3Message(String var1_1, String var2_2, String var3_4, String var4_5, byte[] var5_6, int var6_7, String var7_8, byte[] var8_10) throws NTLMEngineException {
            block6 : {
                super();
                this.type2Flags = var6_7;
                var2_2 = NTLMEngineImpl.access$400(var2_2);
                var1_1 = NTLMEngineImpl.access$500((String)var1_1);
                if (var8_10 == null || var7_8 == null) ** GOTO lbl11
                try {
                    var9_11 = NTLMEngineImpl.access$600();
                    this.ntResp = NTLMEngineImpl.getNTLMv2Response((String)var7_8, var3_4, var4_5, var5_6, var9_11, var8_10);
                    this.lmResp = NTLMEngineImpl.getLMv2Response((String)var7_8, var3_4, var4_5, var5_6, var9_11);
                    break block6;
lbl11: // 1 sources:
                    if ((var6_7 & 524288) != 0) {
                        var7_8 = NTLMEngineImpl.access$700();
                        this.ntResp = NTLMEngineImpl.getNTLM2SessionResponse(var4_5, var5_6, var7_8);
                        this.lmResp = var7_8;
                    } else {
                        this.ntResp = NTLMEngineImpl.getNTLMResponse(var4_5, var5_6);
                        this.lmResp = NTLMEngineImpl.getLMResponse(var4_5, var5_6);
                    }
                }
                catch (NTLMEngineException var7_9) {
                    this.ntResp = new byte[0];
                    this.lmResp = NTLMEngineImpl.getLMResponse(var4_5, var5_6);
                }
            }
            try {
                this.domainBytes = var1_1.toUpperCase().getBytes("UnicodeLittleUnmarked");
                this.hostBytes = var2_2.getBytes("UnicodeLittleUnmarked");
                this.userBytes = var3_4.getBytes("UnicodeLittleUnmarked");
                return;
            }
            catch (UnsupportedEncodingException var2_3) {
                var1_1 = new StringBuilder();
                var1_1.append("Unicode not supported: ");
                var1_1.append(var2_3.getMessage());
                throw new NTLMEngineException(var1_1.toString(), var2_3);
            }
        }

        @Override
        String getResponse() {
            int n = this.ntResp.length;
            int n2 = this.lmResp.length;
            int n3 = this.domainBytes.length;
            int n4 = this.hostBytes.length;
            int n5 = this.userBytes.length;
            int n6 = n2 + 64;
            int n7 = n6 + n;
            int n8 = n7 + n3;
            int n9 = n8 + n5;
            int n10 = n9 + n4 + 0;
            this.prepareResponse(n10, 3);
            this.addUShort(n2);
            this.addUShort(n2);
            this.addULong(64);
            this.addUShort(n);
            this.addUShort(n);
            this.addULong(n6);
            this.addUShort(n3);
            this.addUShort(n3);
            this.addULong(n7);
            this.addUShort(n5);
            this.addUShort(n5);
            this.addULong(n8);
            this.addUShort(n4);
            this.addUShort(n4);
            this.addULong(n9);
            this.addULong(0);
            this.addULong(n10);
            n4 = this.type2Flags;
            this.addULong(n4 & 32768 | (524288 & n4 | 536871429 | n4 & 16 | n4 & 32 | 1073741824 & n4));
            this.addBytes(this.lmResp);
            this.addBytes(this.ntResp);
            this.addBytes(this.domainBytes);
            this.addBytes(this.userBytes);
            this.addBytes(this.hostBytes);
            return super.getResponse();
        }
    }

}

