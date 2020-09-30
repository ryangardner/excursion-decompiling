/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class DigestMD5 {
    private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private String clientResponse;
    private PrintStream debugout;
    private MessageDigest md5;
    private String uri;

    public DigestMD5(PrintStream printStream) {
        this.debugout = printStream;
        if (printStream == null) return;
        printStream.println("DEBUG DIGEST-MD5: Loaded");
    }

    private static String toHex(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = arrby[n] & 255;
            int n4 = n2 + 1;
            char[] arrc2 = digits;
            arrc[n2] = arrc2[n3 >> 4];
            n2 = n4 + 1;
            arrc[n4] = arrc2[n3 & 15];
            ++n;
        }
        return new String(arrc);
    }

    private Hashtable tokenize(String object) throws IOException {
        Hashtable<byte[], String> hashtable = new Hashtable<byte[], String>();
        object = object.getBytes();
        StreamTokenizer streamTokenizer = new StreamTokenizer(new InputStreamReader(new BASE64DecoderStream(new ByteArrayInputStream((byte[])object, 4, ((byte[])object).length - 4))));
        streamTokenizer.ordinaryChars(48, 57);
        streamTokenizer.wordChars(48, 57);
        do {
            StringBuilder stringBuilder;
            PrintStream printStream;
            object = null;
            do {
                int n;
                if ((n = streamTokenizer.nextToken()) == -1) {
                    return hashtable;
                }
                if (n != -3) {
                    if (n == 34) break;
                    continue;
                }
                if (object != null) break;
                object = streamTokenizer.sval;
            } while (true);
            if ((printStream = this.debugout) != null) {
                stringBuilder = new StringBuilder("DEBUG DIGEST-MD5: Received => ");
                stringBuilder.append((String)object);
                stringBuilder.append("='");
                stringBuilder.append(streamTokenizer.sval);
                stringBuilder.append("'");
                printStream.println(stringBuilder.toString());
            }
            if (hashtable.containsKey(object)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(hashtable.get(object));
                stringBuilder.append(",");
                stringBuilder.append(streamTokenizer.sval);
                hashtable.put((byte[])object, stringBuilder.toString());
                continue;
            }
            hashtable.put((byte[])object, streamTokenizer.sval);
        } while (true);
    }

    public byte[] authClient(String object, String charSequence, String object2, String string2, String charSequence2) throws IOException {
        Object object3;
        Object object4;
        StringBuffer stringBuffer;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream(byteArrayOutputStream, Integer.MAX_VALUE);
        try {
            object3 = new SecureRandom();
            this.md5 = MessageDigest.getInstance("MD5");
            stringBuffer = new StringBuffer();
            object4 = new StringBuilder("smtp/");
            ((StringBuilder)object4).append((String)object);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            object2 = this.debugout;
            if (object2 == null) throw new IOException(noSuchAlgorithmException.toString());
            object = new StringBuilder("DEBUG DIGEST-MD5: ");
            ((StringBuilder)object).append(noSuchAlgorithmException);
            ((PrintStream)object2).println(((StringBuilder)object).toString());
            throw new IOException(noSuchAlgorithmException.toString());
        }
        this.uri = ((StringBuilder)object4).toString();
        object4 = new byte[32];
        Object object5 = this.debugout;
        if (object5 != null) {
            ((PrintStream)object5).println("DEBUG DIGEST-MD5: Begin authentication ...");
        }
        object5 = this.tokenize((String)charSequence2);
        charSequence2 = string2;
        if (string2 == null) {
            string2 = (String)((Hashtable)object5).get("realm");
            if (string2 != null) {
                object = new StringTokenizer(string2, ",").nextToken();
            }
            charSequence2 = object;
        }
        object = (String)((Hashtable)object5).get("nonce");
        ((SecureRandom)object3).nextBytes((byte[])object4);
        ((OutputStream)bASE64EncoderStream).write((byte[])object4);
        ((OutputStream)bASE64EncoderStream).flush();
        string2 = byteArrayOutputStream.toString();
        byteArrayOutputStream.reset();
        object3 = this.md5;
        object4 = new StringBuilder(String.valueOf(charSequence));
        ((StringBuilder)object4).append(":");
        ((StringBuilder)object4).append((String)charSequence2);
        ((StringBuilder)object4).append(":");
        ((StringBuilder)object4).append((String)object2);
        ((MessageDigest)object3).update(((MessageDigest)object3).digest(ASCIIUtility.getBytes(((StringBuilder)object4).toString())));
        object2 = this.md5;
        object3 = new StringBuilder(":");
        ((StringBuilder)object3).append((String)object);
        ((StringBuilder)object3).append(":");
        ((StringBuilder)object3).append(string2);
        ((MessageDigest)object2).update(ASCIIUtility.getBytes(((StringBuilder)object3).toString()));
        object2 = new StringBuilder(String.valueOf(DigestMD5.toHex(this.md5.digest())));
        ((StringBuilder)object2).append(":");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(":");
        ((StringBuilder)object2).append("00000001");
        ((StringBuilder)object2).append(":");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(":");
        ((StringBuilder)object2).append("auth");
        ((StringBuilder)object2).append(":");
        this.clientResponse = ((StringBuilder)object2).toString();
        object2 = this.md5;
        object3 = new StringBuilder("AUTHENTICATE:");
        ((StringBuilder)object3).append(this.uri);
        ((MessageDigest)object2).update(ASCIIUtility.getBytes(((StringBuilder)object3).toString()));
        object3 = this.md5;
        object2 = new StringBuilder(String.valueOf(this.clientResponse));
        ((StringBuilder)object2).append(DigestMD5.toHex(this.md5.digest()));
        ((MessageDigest)object3).update(ASCIIUtility.getBytes(((StringBuilder)object2).toString()));
        object2 = new StringBuilder("username=\"");
        ((StringBuilder)object2).append((String)charSequence);
        ((StringBuilder)object2).append("\"");
        stringBuffer.append(((StringBuilder)object2).toString());
        charSequence = new StringBuilder(",realm=\"");
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\"");
        stringBuffer.append(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder(",qop=");
        ((StringBuilder)charSequence).append("auth");
        stringBuffer.append(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder(",nc=");
        ((StringBuilder)charSequence).append("00000001");
        stringBuffer.append(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder(",nonce=\"");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("\"");
        stringBuffer.append(((StringBuilder)charSequence).toString());
        object = new StringBuilder(",cnonce=\"");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("\"");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder(",digest-uri=\"");
        ((StringBuilder)object).append(this.uri);
        ((StringBuilder)object).append("\"");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder(",response=");
        ((StringBuilder)object).append(DigestMD5.toHex(this.md5.digest()));
        stringBuffer.append(((StringBuilder)object).toString());
        object = this.debugout;
        if (object != null) {
            charSequence = new StringBuilder("DEBUG DIGEST-MD5: Response => ");
            ((StringBuilder)charSequence).append(stringBuffer.toString());
            ((PrintStream)object).println(((StringBuilder)charSequence).toString());
        }
        ((OutputStream)bASE64EncoderStream).write(ASCIIUtility.getBytes(stringBuffer.toString()));
        ((OutputStream)bASE64EncoderStream).flush();
        return byteArrayOutputStream.toByteArray();
    }

    public boolean authServer(String object) throws IOException {
        object = this.tokenize((String)object);
        Object object2 = this.md5;
        StringBuilder stringBuilder = new StringBuilder(":");
        stringBuilder.append(this.uri);
        ((MessageDigest)object2).update(ASCIIUtility.getBytes(stringBuilder.toString()));
        object2 = this.md5;
        stringBuilder = new StringBuilder(String.valueOf(this.clientResponse));
        stringBuilder.append(DigestMD5.toHex(this.md5.digest()));
        ((MessageDigest)object2).update(ASCIIUtility.getBytes(stringBuilder.toString()));
        object2 = DigestMD5.toHex(this.md5.digest());
        if (((String)object2).equals((String)((Hashtable)object).get("rspauth"))) return true;
        object = this.debugout;
        if (object == null) return false;
        stringBuilder = new StringBuilder("DEBUG DIGEST-MD5: Expected => rspauth=");
        stringBuilder.append((String)object2);
        ((PrintStream)object).println(stringBuilder.toString());
        return false;
    }
}

