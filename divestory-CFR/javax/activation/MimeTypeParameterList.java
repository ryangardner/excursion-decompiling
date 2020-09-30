/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import javax.activation.MimeTypeParseException;

public class MimeTypeParameterList {
    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
    private Hashtable parameters = new Hashtable();

    public MimeTypeParameterList() {
    }

    public MimeTypeParameterList(String string2) throws MimeTypeParseException {
        this.parse(string2);
    }

    private static boolean isTokenChar(char c) {
        if (c <= ' ') return false;
        if (c >= '') return false;
        if (TSPECIALS.indexOf(c) >= 0) return false;
        return true;
    }

    private static String quote(String string2) {
        int n;
        int n2 = string2.length();
        int n3 = 0;
        boolean bl = false;
        for (n = 0; n < n2 && !bl; ++n) {
            bl = MimeTypeParameterList.isTokenChar(string2.charAt(n)) ^ true;
        }
        if (!bl) return string2;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity((int)((double)n2 * 1.5));
        stringBuffer.append('\"');
        n = n3;
        do {
            if (n >= n2) {
                stringBuffer.append('\"');
                return stringBuffer.toString();
            }
            char c = string2.charAt(n);
            if (c == '\\' || c == '\"') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(c);
            ++n;
        } while (true);
    }

    private static int skipWhiteSpace(String string2, int n) {
        int n2 = string2.length();
        while (n < n2) {
            if (!Character.isWhitespace(string2.charAt(n))) {
                return n;
            }
            ++n;
        }
        return n;
    }

    private static String unquote(String string2) {
        int n = string2.length();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity(n);
        int n2 = 0;
        boolean bl = false;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (!bl && c != '\\') {
                stringBuffer.append(c);
            } else if (bl) {
                stringBuffer.append(c);
                bl = false;
            } else {
                bl = true;
            }
            ++n2;
        }
        return stringBuffer.toString();
    }

    public String get(String string2) {
        return (String)this.parameters.get(string2.trim().toLowerCase(Locale.ENGLISH));
    }

    public Enumeration getNames() {
        return this.parameters.keys();
    }

    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    protected void parse(String charSequence) throws MimeTypeParseException {
        if (charSequence == null) {
            return;
        }
        int n = ((String)charSequence).length();
        if (n <= 0) {
            return;
        }
        int n2 = MimeTypeParameterList.skipWhiteSpace((String)charSequence, 0);
        while (n2 < n && ((String)charSequence).charAt(n2) == ';') {
            String string2;
            String string3;
            block13 : {
                int n3 = MimeTypeParameterList.skipWhiteSpace((String)charSequence, n2 + 1);
                if (n3 >= n) {
                    return;
                }
                for (n2 = n3; n2 < n && MimeTypeParameterList.isTokenChar(((String)charSequence).charAt(n2)); ++n2) {
                }
                string3 = ((String)charSequence).substring(n3, n2).toLowerCase(Locale.ENGLISH);
                if ((n2 = MimeTypeParameterList.skipWhiteSpace((String)charSequence, n2)) >= n) throw new MimeTypeParseException("Couldn't find the '=' that separates a parameter name from its value.");
                if (((String)charSequence).charAt(n2) != '=') throw new MimeTypeParseException("Couldn't find the '=' that separates a parameter name from its value.");
                n3 = MimeTypeParameterList.skipWhiteSpace((String)charSequence, n2 + 1);
                if (n3 >= n) {
                    charSequence = new StringBuilder("Couldn't find a value for parameter named ");
                    ((StringBuilder)charSequence).append(string3);
                    throw new MimeTypeParseException(((StringBuilder)charSequence).toString());
                }
                char c = ((String)charSequence).charAt(n3);
                if (c != '\"') {
                    if (!MimeTypeParameterList.isTokenChar(c)) {
                        charSequence = new StringBuilder("Unexpected character encountered at index ");
                        ((StringBuilder)charSequence).append(n3);
                        throw new MimeTypeParseException(((StringBuilder)charSequence).toString());
                    }
                } else {
                    int n4 = n3 + 1;
                    if (n4 >= n) throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
                    n2 = n4;
                    n3 = c;
                    do {
                        if (n2 >= n || (n3 = (int)((String)charSequence).charAt(n2)) == 34) {
                            if (n3 != 34) throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
                            string2 = MimeTypeParameterList.unquote(((String)charSequence).substring(n4, n2));
                            ++n2;
                            break block13;
                        }
                        int n5 = n2;
                        if (n3 == 92) {
                            n5 = n2 + 1;
                        }
                        n2 = n5 + 1;
                    } while (true);
                }
                for (n2 = n3; n2 < n && MimeTypeParameterList.isTokenChar(((String)charSequence).charAt(n2)); ++n2) {
                }
                string2 = ((String)charSequence).substring(n3, n2);
            }
            this.parameters.put(string3, string2);
            n2 = MimeTypeParameterList.skipWhiteSpace((String)charSequence, n2);
        }
        if (n2 < n) throw new MimeTypeParseException("More characters encountered in input than expected.");
    }

    public void remove(String string2) {
        this.parameters.remove(string2.trim().toLowerCase(Locale.ENGLISH));
    }

    public void set(String string2, String string3) {
        this.parameters.put(string2.trim().toLowerCase(Locale.ENGLISH), string3);
    }

    public int size() {
        return this.parameters.size();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.ensureCapacity(this.parameters.size() * 16);
        Enumeration enumeration = this.parameters.keys();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            stringBuffer.append("; ");
            stringBuffer.append(string2);
            stringBuffer.append('=');
            stringBuffer.append(MimeTypeParameterList.quote((String)this.parameters.get(string2)));
        }
        return stringBuffer.toString();
    }
}

