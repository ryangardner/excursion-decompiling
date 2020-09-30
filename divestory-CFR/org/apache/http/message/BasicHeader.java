/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeader
implements Header,
Cloneable,
Serializable {
    private static final long serialVersionUID = -5427236326487562174L;
    private final String name;
    private final String value;

    public BasicHeader(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        this.name = string2;
        this.value = string3;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public HeaderElement[] getElements() throws ParseException {
        String string2 = this.value;
        if (string2 == null) return new HeaderElement[0];
        return BasicHeaderValueParser.parseElements(string2, null);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatHeader(null, this).toString();
    }
}

