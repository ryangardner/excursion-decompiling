/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import java.security.MessageDigest;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.RequestLine;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthParams;
import org.apache.http.impl.auth.RFC2617Scheme;
import org.apache.http.impl.auth.UnsupportedDigestAlgorithmException;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class DigestScheme
extends RFC2617Scheme {
    private static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_UNKNOWN = -1;
    private String a1;
    private String a2;
    private String cnonce;
    private boolean complete = false;
    private String lastNonce;
    private long nounceCount;

    public static String createCnonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] arrby = new byte[8];
        secureRandom.nextBytes(arrby);
        return DigestScheme.encode(arrby);
    }

    private Header createDigestHeader(Credentials object) throws AuthenticationException {
        int n;
        String string2;
        String string3;
        Object object2;
        String string4;
        Object object3;
        CharSequence charSequence;
        String string5;
        String string6;
        ArrayList<BasicNameValuePair> arrayList;
        block25 : {
            string6 = this.getParameter("uri");
            string3 = this.getParameter("realm");
            charSequence = this.getParameter("nonce");
            string5 = this.getParameter("opaque");
            string2 = this.getParameter("methodname");
            object3 = this.getParameter("algorithm");
            if (string6 == null) throw new IllegalStateException("URI may not be null");
            if (string3 == null) throw new IllegalStateException("Realm may not be null");
            if (charSequence == null) throw new IllegalStateException("Nonce may not be null");
            string4 = this.getParameter("qop");
            if (string4 != null) {
                arrayList = new StringTokenizer(string4, ",");
                while (((StringTokenizer)((Object)arrayList)).hasMoreTokens()) {
                    if (!((StringTokenizer)((Object)arrayList)).nextToken().trim().equals("auth")) continue;
                    n = 2;
                    break block25;
                }
                n = -1;
            } else {
                n = 0;
            }
        }
        if (n == -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("None of the qop methods is supported: ");
            ((StringBuilder)object).append(string4);
            throw new AuthenticationException(((StringBuilder)object).toString());
        }
        string4 = object3;
        if (object3 == null) {
            string4 = "MD5";
        }
        arrayList = this.getParameter("charset");
        object3 = arrayList;
        if (arrayList == null) {
            object3 = "ISO-8859-1";
        }
        arrayList = "MD5";
        if (!string4.equalsIgnoreCase("MD5-sess")) {
            arrayList = string4;
        }
        try {
            object2 = DigestScheme.createMessageDigest((String)((Object)arrayList));
        }
        catch (UnsupportedDigestAlgorithmException unsupportedDigestAlgorithmException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsuppported digest algorithm: ");
            stringBuilder.append((String)((Object)arrayList));
            throw new AuthenticationException(stringBuilder.toString());
        }
        String string7 = object.getUserPrincipal().getName();
        arrayList = object.getPassword();
        if (((String)charSequence).equals(this.lastNonce)) {
            ++this.nounceCount;
        } else {
            this.nounceCount = 1L;
            this.cnonce = null;
            this.lastNonce = charSequence;
        }
        object = object2;
        object2 = object3;
        StringBuilder stringBuilder = new StringBuilder(256);
        new Formatter(stringBuilder, Locale.US).format("%08x", this.nounceCount);
        String string8 = stringBuilder.toString();
        if (this.cnonce == null) {
            this.cnonce = DigestScheme.createCnonce();
        }
        this.a1 = null;
        this.a2 = null;
        if (string4.equalsIgnoreCase("MD5-sess")) {
            stringBuilder.setLength(0);
            stringBuilder.append(string7);
            stringBuilder.append(':');
            stringBuilder.append(string3);
            stringBuilder.append(':');
            stringBuilder.append((String)((Object)arrayList));
            object3 = DigestScheme.encode(((MessageDigest)object).digest(EncodingUtils.getBytes(stringBuilder.toString(), (String)object2)));
            stringBuilder.setLength(0);
            stringBuilder.append((String)object3);
            stringBuilder.append(':');
            stringBuilder.append((String)charSequence);
            stringBuilder.append(':');
            stringBuilder.append(this.cnonce);
            this.a1 = stringBuilder.toString();
        } else {
            stringBuilder.setLength(0);
            stringBuilder.append(string7);
            stringBuilder.append(':');
            stringBuilder.append(string3);
            stringBuilder.append(':');
            stringBuilder.append((String)((Object)arrayList));
            this.a1 = stringBuilder.toString();
        }
        object3 = charSequence;
        arrayList = object;
        object = DigestScheme.encode(((MessageDigest)((Object)arrayList)).digest(EncodingUtils.getBytes(this.a1, (String)object2)));
        if (n == 2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(':');
            ((StringBuilder)charSequence).append(string6);
            this.a2 = ((StringBuilder)charSequence).toString();
        } else {
            if (n == 1) throw new AuthenticationException("qop-int method is not suppported");
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(':');
            ((StringBuilder)charSequence).append(string6);
            this.a2 = ((StringBuilder)charSequence).toString();
        }
        object2 = DigestScheme.encode(((MessageDigest)((Object)arrayList)).digest(EncodingUtils.getBytes(this.a2, (String)object2)));
        charSequence = "auth-int";
        if (n == 0) {
            stringBuilder.setLength(0);
            stringBuilder.append((String)object);
            stringBuilder.append(':');
            stringBuilder.append((String)object3);
            stringBuilder.append(':');
            stringBuilder.append((String)object2);
            object = stringBuilder.toString();
        } else {
            stringBuilder.setLength(0);
            stringBuilder.append((String)object);
            stringBuilder.append(':');
            stringBuilder.append((String)object3);
            stringBuilder.append(':');
            stringBuilder.append(string8);
            stringBuilder.append(':');
            stringBuilder.append(this.cnonce);
            stringBuilder.append(':');
            object = n == 1 ? "auth-int" : "auth";
            stringBuilder.append((String)object);
            stringBuilder.append(':');
            stringBuilder.append((String)object2);
            object = stringBuilder.toString();
        }
        object = DigestScheme.encode(((MessageDigest)((Object)arrayList)).digest(EncodingUtils.getAsciiBytes((String)object)));
        object2 = new CharArrayBuffer(128);
        if (this.isProxy()) {
            ((CharArrayBuffer)object2).append("Proxy-Authorization");
        } else {
            ((CharArrayBuffer)object2).append("Authorization");
        }
        ((CharArrayBuffer)object2).append(": Digest ");
        arrayList = new ArrayList<BasicNameValuePair>(20);
        arrayList.add(new BasicNameValuePair("username", string7));
        arrayList.add(new BasicNameValuePair("realm", string3));
        arrayList.add(new BasicNameValuePair("nonce", (String)object3));
        arrayList.add(new BasicNameValuePair("uri", string6));
        arrayList.add(new BasicNameValuePair("response", (String)object));
        if (n != 0) {
            object = n == 1 ? charSequence : "auth";
            arrayList.add(new BasicNameValuePair("qop", (String)object));
            arrayList.add(new BasicNameValuePair("nc", string8));
            arrayList.add(new BasicNameValuePair("cnonce", this.cnonce));
        }
        if (string4 != null) {
            arrayList.add(new BasicNameValuePair("algorithm", string4));
        }
        if (string5 != null) {
            arrayList.add(new BasicNameValuePair("opaque", string5));
        }
        n = 0;
        while (n < arrayList.size()) {
            object = (BasicNameValuePair)arrayList.get(n);
            if (n > 0) {
                ((CharArrayBuffer)object2).append(", ");
            }
            boolean bl = "nc".equals(((BasicNameValuePair)object).getName()) || "qop".equals(((BasicNameValuePair)object).getName());
            BasicHeaderValueFormatter.DEFAULT.formatNameValuePair((CharArrayBuffer)object2, (NameValuePair)object, bl ^ true);
            ++n;
        }
        return new BufferedHeader((CharArrayBuffer)object2);
    }

    private static MessageDigest createMessageDigest(String string2) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(string2);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported algorithm in HTTP Digest authentication: ");
            stringBuilder.append(string2);
            throw new UnsupportedDigestAlgorithmException(stringBuilder.toString());
        }
    }

    private static String encode(byte[] arrby) {
        int n = arrby.length;
        char[] arrc = new char[n * 2];
        int n2 = 0;
        while (n2 < n) {
            byte by = arrby[n2];
            byte by2 = arrby[n2];
            int n3 = n2 * 2;
            char[] arrc2 = HEXADECIMAL;
            arrc[n3] = arrc2[(by2 & 240) >> 4];
            arrc[n3 + 1] = arrc2[by & 15];
            ++n2;
        }
        return new String(arrc);
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest object) throws AuthenticationException {
        if (credentials == null) throw new IllegalArgumentException("Credentials may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP request may not be null");
        this.getParameters().put("methodname", object.getRequestLine().getMethod());
        this.getParameters().put("uri", object.getRequestLine().getUri());
        if (this.getParameter("charset") != null) return this.createDigestHeader(credentials);
        object = AuthParams.getCredentialCharset(object.getParams());
        this.getParameters().put("charset", (String)object);
        return this.createDigestHeader(credentials);
    }

    String getA1() {
        return this.a1;
    }

    String getA2() {
        return this.a2;
    }

    String getCnonce() {
        return this.cnonce;
    }

    @Override
    public String getSchemeName() {
        return "digest";
    }

    @Override
    public boolean isComplete() {
        if (!"true".equalsIgnoreCase(this.getParameter("stale"))) return this.complete;
        return false;
    }

    @Override
    public boolean isConnectionBased() {
        return false;
    }

    public void overrideParamter(String string2, String string3) {
        this.getParameters().put(string2, string3);
    }

    @Override
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        if (this.getParameter("realm") == null) throw new MalformedChallengeException("missing realm in challenge");
        if (this.getParameter("nonce") == null) throw new MalformedChallengeException("missing nonce in challenge");
        this.complete = true;
    }
}

