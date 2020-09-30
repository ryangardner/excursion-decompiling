/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.Locale;

public class URLName {
    static final int caseDiff = 32;
    private static boolean doEncode = true;
    static BitSet dontNeedEncoding;
    private String file;
    protected String fullURL;
    private int hashCode = 0;
    private String host;
    private InetAddress hostAddress;
    private boolean hostAddressKnown = false;
    private String password;
    private int port = -1;
    private String protocol;
    private String ref;
    private String username;

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static {
        try {
            var0 = Boolean.getBoolean("mail.URLName.dontencode") == false;
            URLName.doEncode = var0;
lbl4: // 2 sources:
            do {
                URLName.dontNeedEncoding = new BitSet(256);
                var1_2 = 97;
                break;
            } while (true);
        }
        catch (Exception var2_1) {
            ** continue;
        }
        do {
            if (var1_2 > 122) break;
            URLName.dontNeedEncoding.set(var1_2);
            ++var1_2;
        } while (true);
        var1_2 = 65;
        do {
            if (var1_2 > 90) break;
            URLName.dontNeedEncoding.set(var1_2);
            ++var1_2;
        } while (true);
        var1_2 = 48;
        do {
            if (var1_2 > 57) {
                URLName.dontNeedEncoding.set(32);
                URLName.dontNeedEncoding.set(45);
                URLName.dontNeedEncoding.set(95);
                URLName.dontNeedEncoding.set(46);
                URLName.dontNeedEncoding.set(42);
                return;
            }
            URLName.dontNeedEncoding.set(var1_2);
            ++var1_2;
        } while (true);
    }

    public URLName(String string2) {
        this.parseString(string2);
    }

    public URLName(String string2, String string3, int n, String string4, String string5, String string6) {
        this.protocol = string2;
        this.host = string3;
        this.port = n;
        if (string4 != null && (n = string4.indexOf(35)) != -1) {
            this.file = string4.substring(0, n);
            this.ref = string4.substring(n + 1);
        } else {
            this.file = string4;
            this.ref = null;
        }
        string2 = string5;
        if (doEncode) {
            string2 = URLName.encode(string5);
        }
        this.username = string2;
        string2 = string6;
        if (doEncode) {
            string2 = URLName.encode(string6);
        }
        this.password = string2;
    }

    public URLName(URL uRL) {
        this(uRL.toString());
    }

    private static String _encode(String string2) {
        StringBuffer stringBuffer = new StringBuffer(string2.length());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
        int n = 0;
        do {
            block11 : {
                int n2;
                if (n >= string2.length()) {
                    return stringBuffer.toString();
                }
                int n3 = string2.charAt(n);
                if (dontNeedEncoding.get(n3)) {
                    n2 = n3;
                    if (n3 == 32) {
                        n2 = 43;
                    }
                    stringBuffer.append((char)n2);
                } else {
                    byte[] arrby;
                    try {
                        outputStreamWriter.write(n3);
                        outputStreamWriter.flush();
                        arrby = byteArrayOutputStream.toByteArray();
                        n2 = 0;
                    }
                    catch (IOException iOException) {
                        byteArrayOutputStream.reset();
                        break block11;
                    }
                    do {
                        int n4;
                        if (n2 >= arrby.length) {
                            byteArrayOutputStream.reset();
                            break;
                        }
                        stringBuffer.append('%');
                        int n5 = n4 = Character.forDigit(arrby[n2] >> 4 & 15, 16);
                        if (Character.isLetter((char)n4)) {
                            n5 = n3 = (int)((char)(n4 - 32));
                        }
                        stringBuffer.append((char)n5);
                        n5 = n4 = (int)Character.forDigit(arrby[n2] & 15, 16);
                        if (Character.isLetter((char)n4)) {
                            n5 = n3 = (char)(n4 - 32);
                        }
                        stringBuffer.append((char)n5);
                        ++n2;
                    } while (true);
                }
            }
            ++n;
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static String decode(String charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (URLName.indexOfAny((String)charSequence, "+%") == -1) {
            return charSequence;
        }
        CharSequence charSequence2 = new StringBuffer();
        int n = 0;
        do {
            if (n >= ((String)charSequence).length()) {
                charSequence = ((StringBuffer)charSequence2).toString();
                byte[] arrby = ((String)charSequence).getBytes("8859_1");
                charSequence2 = new String(arrby);
                return charSequence2;
            }
            char c = ((String)charSequence).charAt(n);
            if (c != '%') {
                if (c != '+') {
                    ((StringBuffer)charSequence2).append(c);
                } else {
                    ((StringBuffer)charSequence2).append(' ');
                }
            } else {
                ((StringBuffer)charSequence2).append((char)Integer.parseInt(((String)charSequence).substring(n + 1, n + 3), 16));
                n += 2;
            }
            ++n;
        } while (true);
        catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return charSequence;
        }
    }

    static String encode(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c == ' ') return URLName._encode(string2);
            if (!dontNeedEncoding.get(c)) {
                return URLName._encode(string2);
            }
            ++n;
        }
        return string2;
    }

    private InetAddress getHostAddress() {
        synchronized (this) {
            if (this.hostAddressKnown) {
                InetAddress inetAddress = this.hostAddress;
                return inetAddress;
            }
            Object object = this.host;
            if (object == null) {
                return null;
            }
            try {
                this.hostAddress = InetAddress.getByName(this.host);
            }
            catch (UnknownHostException unknownHostException) {
                this.hostAddress = null;
            }
            this.hostAddressKnown = true;
            object = this.hostAddress;
            return object;
        }
    }

    private static int indexOfAny(String string2, String string3) {
        return URLName.indexOfAny(string2, string3, 0);
    }

    private static int indexOfAny(String string2, String string3, int n) {
        try {
            int n2 = string2.length();
            do {
                if (n >= n2) {
                    return -1;
                }
                int n3 = string3.indexOf(string2.charAt(n));
                if (n3 >= 0) {
                    return n;
                }
                ++n;
            } while (true);
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            return -1;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof URLName)) {
            return false;
        }
        URLName uRLName = (URLName)object;
        object = uRLName.protocol;
        if (object == null) return false;
        if (!((String)object).equals(this.protocol)) {
            return false;
        }
        object = this.getHostAddress();
        Object object2 = uRLName.getHostAddress();
        if (object != null && object2 != null ? !((InetAddress)object).equals(object2) : ((object = this.host) != null && (object2 = uRLName.host) != null ? !((String)object).equalsIgnoreCase((String)object2) : this.host != uRLName.host)) {
            return false;
        }
        object2 = this.username;
        object = uRLName.username;
        if (object2 != object) {
            if (object2 == null) return false;
            if (!((String)object2).equals(object)) {
                return false;
            }
        }
        String string2 = this.file;
        object2 = "";
        object = string2;
        if (string2 == null) {
            object = "";
        }
        if ((string2 = uRLName.file) != null) {
            object2 = string2;
        }
        if (!((String)object).equals(object2)) {
            return false;
        }
        if (this.port == uRLName.port) return true;
        return false;
    }

    public String getFile() {
        return this.file;
    }

    public String getHost() {
        return this.host;
    }

    public String getPassword() {
        if (!doEncode) return this.password;
        return URLName.decode(this.password);
    }

    public int getPort() {
        return this.port;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getRef() {
        return this.ref;
    }

    public URL getURL() throws MalformedURLException {
        return new URL(this.getProtocol(), this.getHost(), this.getPort(), this.getFile());
    }

    public String getUsername() {
        if (!doEncode) return this.username;
        return URLName.decode(this.username);
    }

    public int hashCode() {
        int n = this.hashCode;
        if (n != 0) {
            return n;
        }
        Object object = this.protocol;
        if (object != null) {
            this.hashCode = n + ((String)object).hashCode();
        }
        if ((object = this.getHostAddress()) != null) {
            this.hashCode += ((InetAddress)object).hashCode();
        } else {
            object = this.host;
            if (object != null) {
                this.hashCode += ((String)object).toLowerCase(Locale.ENGLISH).hashCode();
            }
        }
        object = this.username;
        if (object != null) {
            this.hashCode += ((String)object).hashCode();
        }
        if ((object = this.file) != null) {
            this.hashCode += ((String)object).hashCode();
        }
        this.hashCode = n = this.hashCode + this.port;
        return n;
    }

    protected void parseString(String string2) {
        int n;
        this.password = null;
        this.username = null;
        this.host = null;
        this.ref = null;
        this.file = null;
        this.protocol = null;
        this.port = -1;
        int n2 = string2.length();
        int n3 = string2.indexOf(58);
        if (n3 != -1) {
            this.protocol = string2.substring(0, n3);
        }
        if (string2.regionMatches(n = n3 + 1, "//", 0, 2)) {
            String string3;
            n = string2.indexOf(47, n3 += 3);
            if (n != -1) {
                string3 = string2.substring(n3, n);
                n3 = n + 1;
                if (n3 < n2) {
                    this.file = string2.substring(n3);
                    string2 = string3;
                } else {
                    this.file = "";
                    string2 = string3;
                }
            } else {
                string2 = string2.substring(n3);
            }
            n2 = string2.indexOf(64);
            string3 = string2;
            if (n2 != -1) {
                String string4 = string2.substring(0, n2);
                string3 = string2.substring(n2 + 1);
                n2 = string4.indexOf(58);
                if (n2 != -1) {
                    this.username = string4.substring(0, n2);
                    this.password = string4.substring(n2 + 1);
                } else {
                    this.username = string4;
                }
            }
            if ((n2 = string3.length() > 0 && string3.charAt(0) == '[' ? string3.indexOf(58, string3.indexOf(93)) : string3.indexOf(58)) != -1) {
                string2 = string3.substring(n2 + 1);
                if (string2.length() > 0) {
                    try {
                        this.port = Integer.parseInt(string2);
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.port = -1;
                    }
                }
                this.host = string3.substring(0, n2);
            } else {
                this.host = string3;
            }
        } else if (n < n2) {
            this.file = string2.substring(n);
        }
        string2 = this.file;
        if (string2 == null) return;
        n2 = string2.indexOf(35);
        if (n2 == -1) return;
        this.ref = this.file.substring(n2 + 1);
        this.file = this.file.substring(0, n2);
    }

    public String toString() {
        if (this.fullURL != null) return this.fullURL;
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = this.protocol;
        if (string2 != null) {
            stringBuffer.append(string2);
            stringBuffer.append(":");
        }
        if (this.username != null || this.host != null) {
            stringBuffer.append("//");
            string2 = this.username;
            if (string2 != null) {
                stringBuffer.append(string2);
                if (this.password != null) {
                    stringBuffer.append(":");
                    stringBuffer.append(this.password);
                }
                stringBuffer.append("@");
            }
            if ((string2 = this.host) != null) {
                stringBuffer.append(string2);
            }
            if (this.port != -1) {
                stringBuffer.append(":");
                stringBuffer.append(Integer.toString(this.port));
            }
            if (this.file != null) {
                stringBuffer.append("/");
            }
        }
        if ((string2 = this.file) != null) {
            stringBuffer.append(string2);
        }
        if (this.ref != null) {
            stringBuffer.append("#");
            stringBuffer.append(this.ref);
        }
        this.fullURL = stringBuffer.toString();
        return this.fullURL;
    }
}

