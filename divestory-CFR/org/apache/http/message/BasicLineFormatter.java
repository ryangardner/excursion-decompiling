/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.message.LineFormatter;
import org.apache.http.util.CharArrayBuffer;

public class BasicLineFormatter
implements LineFormatter {
    public static final BasicLineFormatter DEFAULT = new BasicLineFormatter();

    public static final String formatHeader(Header header, LineFormatter lineFormatter) {
        LineFormatter lineFormatter2 = lineFormatter;
        if (lineFormatter != null) return lineFormatter2.formatHeader(null, header).toString();
        lineFormatter2 = DEFAULT;
        return lineFormatter2.formatHeader(null, header).toString();
    }

    public static final String formatProtocolVersion(ProtocolVersion protocolVersion, LineFormatter lineFormatter) {
        LineFormatter lineFormatter2 = lineFormatter;
        if (lineFormatter != null) return lineFormatter2.appendProtocolVersion(null, protocolVersion).toString();
        lineFormatter2 = DEFAULT;
        return lineFormatter2.appendProtocolVersion(null, protocolVersion).toString();
    }

    public static final String formatRequestLine(RequestLine requestLine, LineFormatter lineFormatter) {
        LineFormatter lineFormatter2 = lineFormatter;
        if (lineFormatter != null) return lineFormatter2.formatRequestLine(null, requestLine).toString();
        lineFormatter2 = DEFAULT;
        return lineFormatter2.formatRequestLine(null, requestLine).toString();
    }

    public static final String formatStatusLine(StatusLine statusLine, LineFormatter lineFormatter) {
        LineFormatter lineFormatter2 = lineFormatter;
        if (lineFormatter != null) return lineFormatter2.formatStatusLine(null, statusLine).toString();
        lineFormatter2 = DEFAULT;
        return lineFormatter2.formatStatusLine(null, statusLine).toString();
    }

    @Override
    public CharArrayBuffer appendProtocolVersion(CharArrayBuffer charArrayBuffer, ProtocolVersion protocolVersion) {
        if (protocolVersion == null) throw new IllegalArgumentException("Protocol version may not be null");
        int n = this.estimateProtocolVersionLen(protocolVersion);
        if (charArrayBuffer == null) {
            charArrayBuffer = new CharArrayBuffer(n);
        } else {
            charArrayBuffer.ensureCapacity(n);
        }
        charArrayBuffer.append(protocolVersion.getProtocol());
        charArrayBuffer.append('/');
        charArrayBuffer.append(Integer.toString(protocolVersion.getMajor()));
        charArrayBuffer.append('.');
        charArrayBuffer.append(Integer.toString(protocolVersion.getMinor()));
        return charArrayBuffer;
    }

    protected void doFormatHeader(CharArrayBuffer charArrayBuffer, Header object) {
        int n;
        String string2 = object.getName();
        object = object.getValue();
        int n2 = n = string2.length() + 2;
        if (object != null) {
            n2 = n + ((String)object).length();
        }
        charArrayBuffer.ensureCapacity(n2);
        charArrayBuffer.append(string2);
        charArrayBuffer.append(": ");
        if (object == null) return;
        charArrayBuffer.append((String)object);
    }

    protected void doFormatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine) {
        String string2 = requestLine.getMethod();
        String string3 = requestLine.getUri();
        charArrayBuffer.ensureCapacity(string2.length() + 1 + string3.length() + 1 + this.estimateProtocolVersionLen(requestLine.getProtocolVersion()));
        charArrayBuffer.append(string2);
        charArrayBuffer.append(' ');
        charArrayBuffer.append(string3);
        charArrayBuffer.append(' ');
        this.appendProtocolVersion(charArrayBuffer, requestLine.getProtocolVersion());
    }

    protected void doFormatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine) {
        int n = this.estimateProtocolVersionLen(statusLine.getProtocolVersion()) + 1 + 3 + 1;
        String string2 = statusLine.getReasonPhrase();
        int n2 = n;
        if (string2 != null) {
            n2 = n + string2.length();
        }
        charArrayBuffer.ensureCapacity(n2);
        this.appendProtocolVersion(charArrayBuffer, statusLine.getProtocolVersion());
        charArrayBuffer.append(' ');
        charArrayBuffer.append(Integer.toString(statusLine.getStatusCode()));
        charArrayBuffer.append(' ');
        if (string2 == null) return;
        charArrayBuffer.append(string2);
    }

    protected int estimateProtocolVersionLen(ProtocolVersion protocolVersion) {
        return protocolVersion.getProtocol().length() + 4;
    }

    @Override
    public CharArrayBuffer formatHeader(CharArrayBuffer charArrayBuffer, Header header) {
        if (header == null) throw new IllegalArgumentException("Header may not be null");
        if (header instanceof FormattedHeader) {
            return ((FormattedHeader)header).getBuffer();
        }
        charArrayBuffer = this.initBuffer(charArrayBuffer);
        this.doFormatHeader(charArrayBuffer, header);
        return charArrayBuffer;
    }

    @Override
    public CharArrayBuffer formatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine) {
        if (requestLine == null) throw new IllegalArgumentException("Request line may not be null");
        charArrayBuffer = this.initBuffer(charArrayBuffer);
        this.doFormatRequestLine(charArrayBuffer, requestLine);
        return charArrayBuffer;
    }

    @Override
    public CharArrayBuffer formatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine) {
        if (statusLine == null) throw new IllegalArgumentException("Status line may not be null");
        charArrayBuffer = this.initBuffer(charArrayBuffer);
        this.doFormatStatusLine(charArrayBuffer, statusLine);
        return charArrayBuffer;
    }

    protected CharArrayBuffer initBuffer(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) return new CharArrayBuffer(64);
        charArrayBuffer.clear();
        return charArrayBuffer;
    }
}

