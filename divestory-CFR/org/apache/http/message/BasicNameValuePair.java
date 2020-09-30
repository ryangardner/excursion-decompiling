/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

public class BasicNameValuePair
implements NameValuePair,
Cloneable,
Serializable {
    private static final long serialVersionUID = -6437800749411518984L;
    private final String name;
    private final String value;

    public BasicNameValuePair(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        this.name = string2;
        this.value = string3;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof NameValuePair)) return false;
        object = (BasicNameValuePair)object;
        if (!this.name.equals(((BasicNameValuePair)object).name)) return false;
        if (!LangUtils.equals(this.value, ((BasicNameValuePair)object).value)) return false;
        return bl;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.value);
    }

    public String toString() {
        if (this.value == null) {
            return this.name;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(this.name.length() + 1 + this.value.length());
        charArrayBuffer.append(this.name);
        charArrayBuffer.append("=");
        charArrayBuffer.append(this.value);
        return charArrayBuffer.toString();
    }
}

