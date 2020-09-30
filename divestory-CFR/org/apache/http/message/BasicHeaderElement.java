/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

public class BasicHeaderElement
implements HeaderElement,
Cloneable {
    private final String name;
    private final NameValuePair[] parameters;
    private final String value;

    public BasicHeaderElement(String string2, String string3) {
        this(string2, string3, null);
    }

    public BasicHeaderElement(String string2, String string3, NameValuePair[] arrnameValuePair) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        this.name = string2;
        this.value = string3;
        if (arrnameValuePair != null) {
            this.parameters = arrnameValuePair;
            return;
        }
        this.parameters = new NameValuePair[0];
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof HeaderElement)) return false;
        object = (BasicHeaderElement)object;
        if (!this.name.equals(((BasicHeaderElement)object).name)) return false;
        if (!LangUtils.equals(this.value, ((BasicHeaderElement)object).value)) return false;
        if (!LangUtils.equals(this.parameters, ((BasicHeaderElement)object).parameters)) return false;
        return bl;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public NameValuePair getParameter(int n) {
        return this.parameters[n];
    }

    @Override
    public NameValuePair getParameterByName(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        NameValuePair nameValuePair = null;
        int n = 0;
        do {
            NameValuePair[] arrnameValuePair = this.parameters;
            NameValuePair nameValuePair2 = nameValuePair;
            if (n >= arrnameValuePair.length) return nameValuePair2;
            nameValuePair2 = arrnameValuePair[n];
            if (nameValuePair2.getName().equalsIgnoreCase(string2)) {
                return nameValuePair2;
            }
            ++n;
        } while (true);
    }

    @Override
    public int getParameterCount() {
        return this.parameters.length;
    }

    @Override
    public NameValuePair[] getParameters() {
        return (NameValuePair[])this.parameters.clone();
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        NameValuePair[] arrnameValuePair;
        int n = LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.value);
        int n2 = 0;
        while (n2 < (arrnameValuePair = this.parameters).length) {
            n = LangUtils.hashCode(n, arrnameValuePair[n2]);
            ++n2;
        }
        return n;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        charArrayBuffer.append(this.name);
        if (this.value != null) {
            charArrayBuffer.append("=");
            charArrayBuffer.append(this.value);
        }
        int n = 0;
        while (n < this.parameters.length) {
            charArrayBuffer.append("; ");
            charArrayBuffer.append(this.parameters[n]);
            ++n;
        }
        return charArrayBuffer.toString();
    }
}

