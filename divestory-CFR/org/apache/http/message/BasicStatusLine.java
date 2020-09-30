/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.util.CharArrayBuffer;

public class BasicStatusLine
implements StatusLine,
Cloneable,
Serializable {
    private static final long serialVersionUID = -2443303766890459269L;
    private final ProtocolVersion protoVersion;
    private final String reasonPhrase;
    private final int statusCode;

    public BasicStatusLine(ProtocolVersion protocolVersion, int n, String string2) {
        if (protocolVersion == null) throw new IllegalArgumentException("Protocol version may not be null.");
        if (n < 0) throw new IllegalArgumentException("Status code may not be negative.");
        this.protoVersion = protocolVersion;
        this.statusCode = n;
        this.reasonPhrase = string2;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.protoVersion;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatStatusLine(null, this).toString();
    }
}

