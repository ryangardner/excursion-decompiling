/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.util.CharArrayBuffer;

public class BasicRequestLine
implements RequestLine,
Cloneable,
Serializable {
    private static final long serialVersionUID = 2810581718468737193L;
    private final String method;
    private final ProtocolVersion protoversion;
    private final String uri;

    public BasicRequestLine(String string2, String string3, ProtocolVersion protocolVersion) {
        if (string2 == null) throw new IllegalArgumentException("Method must not be null.");
        if (string3 == null) throw new IllegalArgumentException("URI must not be null.");
        if (protocolVersion == null) throw new IllegalArgumentException("Protocol version must not be null.");
        this.method = string2;
        this.uri = string3;
        this.protoversion = protocolVersion;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.protoversion;
    }

    @Override
    public String getUri() {
        return this.uri;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatRequestLine(null, this).toString();
    }
}

