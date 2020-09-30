/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpResponse {
    private String body;
    private Map fields;
    private String header;

    protected HttpResponse(String string2) throws IllegalArgumentException {
        if (string2 == null) throw new IllegalArgumentException("Empty HTTP response message");
        if (string2.trim().length() == 0) throw new IllegalArgumentException("Empty HTTP response message");
        AbstractStringBuilder abstractStringBuilder = new StringBuffer();
        this.fields = new HashMap();
        String[] arrstring = string2.split("\\r\\n");
        this.header = arrstring[0].trim();
        int n = 1;
        int n2 = 0;
        do {
            int n3;
            if (n >= arrstring.length) {
                if (n2 == 0) return;
                this.body = ((StringBuffer)abstractStringBuilder).toString();
                return;
            }
            string2 = arrstring[n];
            if (string2.length() == 0) {
                n3 = 1;
            } else if (n2 != 0) {
                ((StringBuffer)abstractStringBuilder).append(string2);
                ((StringBuffer)abstractStringBuilder).append("\r\n");
                n3 = n2;
            } else {
                n3 = n2;
                if (string2.length() > 0) {
                    n3 = string2.indexOf(58);
                    if (n3 == -1) {
                        abstractStringBuilder = new StringBuilder("Invalid HTTP message header :");
                        ((StringBuilder)abstractStringBuilder).append(string2);
                        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
                    }
                    String string3 = string2.substring(0, n3).toUpperCase();
                    string2 = string2.substring(n3 + 1).trim();
                    this.fields.put(string3, string2);
                    n3 = n2;
                }
            }
            ++n;
            n2 = n3;
        } while (true);
    }

    public String getBody() {
        return this.body;
    }

    public String getHTTPFieldElement(String charSequence, String string2) throws IllegalArgumentException {
        Object object = this.getHTTPHeaderField((String)charSequence);
        if (charSequence != null) {
            object = new StringTokenizer(((String)object).trim(), ",");
            while (((StringTokenizer)object).countTokens() > 0) {
                int n;
                charSequence = ((StringTokenizer)object).nextToken().trim();
                if (!((String)charSequence).startsWith(string2) || (n = ((String)charSequence).indexOf("=")) == -1) continue;
                return ((String)charSequence).substring(n + 1).trim();
            }
        }
        charSequence = new StringBuilder("HTTP element field ");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" is not present");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public String getHTTPHeaderField(String string2) throws IllegalArgumentException {
        CharSequence charSequence = (String)this.fields.get(string2.toUpperCase());
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder("HTTP field ");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" is not present");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public String getHeader() {
        return this.header;
    }
}

