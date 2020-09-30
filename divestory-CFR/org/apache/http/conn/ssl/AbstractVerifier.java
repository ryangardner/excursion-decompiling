/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.AbstractCollection;
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.InetAddressUtils;

public abstract class AbstractVerifier
implements X509HostnameVerifier {
    private static final String[] BAD_COUNTRY_2LDS;

    static {
        Object[] arrobject = new String[]{"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};
        BAD_COUNTRY_2LDS = arrobject;
        Arrays.sort(arrobject);
    }

    public static boolean acceptableCountryWildcard(String arrstring) {
        boolean bl;
        arrstring = arrstring.split("\\.");
        int n = arrstring.length;
        boolean bl2 = bl = true;
        if (n != 3) return bl2;
        if (arrstring[2].length() != 2) {
            return bl;
        }
        if (Arrays.binarySearch(BAD_COUNTRY_2LDS, arrstring[1]) >= 0) return false;
        return bl;
    }

    public static int countDots(String string2) {
        int n = 0;
        int n2 = 0;
        while (n < string2.length()) {
            int n3 = n2;
            if (string2.charAt(n) == '.') {
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    public static String[] getCNs(X509Certificate object) {
        LinkedList<String> linkedList = new LinkedList<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(object.getSubjectX500Principal().toString(), ",");
        do {
            if (!stringTokenizer.hasMoreTokens()) {
                if (linkedList.isEmpty()) return null;
                object = new String[linkedList.size()];
                linkedList.toArray((T[])object);
                return object;
            }
            object = stringTokenizer.nextToken();
            int n = object.indexOf("CN=");
            if (n < 0) continue;
            linkedList.add(object.substring(n + 3));
        } while (true);
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509Certificate) {
        return AbstractVerifier.getSubjectAlts(x509Certificate, null);
    }

    private static String[] getSubjectAlts(X509Certificate arrstring, String object) {
        int n = AbstractVerifier.isIPAddress((String)((Object)object)) ? 7 : 2;
        object = new LinkedList<String>();
        try {
            arrstring = ((X509Certificate)arrstring).getSubjectAlternativeNames();
        }
        catch (CertificateParsingException certificateParsingException) {
            Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", certificateParsingException);
            arrstring = null;
        }
        if (arrstring != null) {
            arrstring = arrstring.iterator();
            while (arrstring.hasNext()) {
                List list = (List)arrstring.next();
                if ((Integer)list.get(0) != n) continue;
                object.add((String)list.get(1));
            }
        }
        if (object.isEmpty()) return null;
        arrstring = new String[object.size()];
        object.toArray((T[])arrstring);
        return arrstring;
    }

    private static boolean isIPAddress(String string2) {
        if (string2 == null) return false;
        if (InetAddressUtils.isIPv4Address(string2)) return true;
        if (!InetAddressUtils.isIPv6Address(string2)) return false;
        return true;
    }

    @Override
    public final void verify(String string2, X509Certificate x509Certificate) throws SSLException {
        this.verify(string2, AbstractVerifier.getCNs(x509Certificate), AbstractVerifier.getSubjectAlts(x509Certificate, string2));
    }

    @Override
    public final void verify(String string2, SSLSocket sSLSocket) throws IOException {
        SSLSession sSLSession;
        if (string2 == null) throw new NullPointerException("host to verify is null");
        SSLSession sSLSession2 = sSLSession = sSLSocket.getSession();
        if (sSLSession == null) {
            sSLSocket.getInputStream().available();
            sSLSession2 = sSLSession = sSLSocket.getSession();
            if (sSLSession == null) {
                sSLSocket.startHandshake();
                sSLSession2 = sSLSocket.getSession();
            }
        }
        this.verify(string2, (X509Certificate)sSLSession2.getPeerCertificates()[0]);
    }

    public final void verify(String string2, String[] object4, String[] object2, boolean bl) throws SSLException {
        boolean bl2;
        Object object3 = new LinkedList<Object>();
        if (object4 != null && ((String[])object4).length > 0 && object4[0] != null) {
            ((LinkedList)object3).add(object4[0]);
        }
        if (object2 != null) {
            for (Object object4 : object2) {
                if (object4 == null) continue;
                ((LinkedList)object3).add(object4);
            }
        }
        if (((AbstractCollection)object3).isEmpty()) {
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Certificate for <");
            ((StringBuilder)object4).append(string2);
            ((StringBuilder)object4).append("> doesn't contain CN or DNS subjectAlt");
            throw new SSLException(((StringBuilder)object4).toString());
        }
        object4 = new StringBuilder();
        object2 = string2.trim().toLowerCase(Locale.ENGLISH);
        object3 = ((AbstractSequentialList)object3).iterator();
        boolean bl3 = false;
        do {
            bl2 = bl3;
            if (!object3.hasNext()) break;
            String string3 = ((String)object3.next()).toLowerCase(Locale.ENGLISH);
            ((StringBuilder)object4).append(" <");
            ((StringBuilder)object4).append(string3);
            ((StringBuilder)object4).append('>');
            if (object3.hasNext()) {
                ((StringBuilder)object4).append(" OR");
            }
            Object object5 = string3.split("\\.");
            int n = ((String[])object5).length;
            bl3 = true;
            if ((n = n >= 3 && object5[0].endsWith("*") && AbstractVerifier.acceptableCountryWildcard(string3) && !AbstractVerifier.isIPAddress(string2) ? 1 : 0) != 0) {
                if (object5[0].length() > 1) {
                    String string4 = object5[0].substring(0, ((String[])object5).length - 2);
                    String string5 = string3.substring(object5[0].length());
                    object5 = ((String)object2).substring(string4.length());
                    bl2 = ((String)object2).startsWith(string4) && ((String)object5).endsWith(string5);
                } else {
                    bl2 = ((String)object2).endsWith(string3.substring(1));
                }
                if (bl2 && bl) {
                    bl2 = AbstractVerifier.countDots((String)object2) == AbstractVerifier.countDots(string3) ? bl3 : false;
                }
            } else {
                bl2 = ((String)object2).equals(string3);
            }
            bl3 = bl2;
        } while (!bl2);
        if (bl2) {
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("hostname in certificate didn't match: <");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append("> !=");
        ((StringBuilder)object2).append(object4);
        throw new SSLException(((StringBuilder)object2).toString());
    }

    @Override
    public final boolean verify(String string2, SSLSession sSLSession) {
        try {
            this.verify(string2, (X509Certificate)sSLSession.getPeerCertificates()[0]);
            return true;
        }
        catch (SSLException sSLException) {
            return false;
        }
    }
}

