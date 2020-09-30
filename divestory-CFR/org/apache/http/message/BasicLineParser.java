/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.LineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class BasicLineParser
implements LineParser {
    public static final BasicLineParser DEFAULT = new BasicLineParser();
    protected final ProtocolVersion protocol;

    public BasicLineParser() {
        this(null);
    }

    public BasicLineParser(ProtocolVersion protocolVersion) {
        ProtocolVersion protocolVersion2 = protocolVersion;
        if (protocolVersion == null) {
            protocolVersion2 = HttpVersion.HTTP_1_1;
        }
        this.protocol = protocolVersion2;
    }

    public static final Header parseHeader(String string2, LineParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null");
        LineParser lineParser = object;
        if (object == null) {
            lineParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return lineParser.parseHeader((CharArrayBuffer)object);
    }

    public static final ProtocolVersion parseProtocolVersion(String string2, LineParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null.");
        LineParser lineParser = object;
        if (object == null) {
            lineParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return lineParser.parseProtocolVersion((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    public static final RequestLine parseRequestLine(String string2, LineParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null.");
        LineParser lineParser = object;
        if (object == null) {
            lineParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return lineParser.parseRequestLine((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    public static final StatusLine parseStatusLine(String string2, LineParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null.");
        LineParser lineParser = object;
        if (object == null) {
            lineParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return lineParser.parseStatusLine((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    protected ProtocolVersion createProtocolVersion(int n, int n2) {
        return this.protocol.forVersion(n, n2);
    }

    protected RequestLine createRequestLine(String string2, String string3, ProtocolVersion protocolVersion) {
        return new BasicRequestLine(string2, string3, protocolVersion);
    }

    protected StatusLine createStatusLine(ProtocolVersion protocolVersion, int n, String string2) {
        return new BasicStatusLine(protocolVersion, n, string2);
    }

    @Override
    public boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor object) {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (object == null) throw new IllegalArgumentException("Parser cursor may not be null");
        int n = ((ParserCursor)object).getPos();
        object = this.protocol.getProtocol();
        int n2 = ((String)object).length();
        int n3 = charArrayBuffer.length();
        boolean bl = false;
        if (n3 < n2 + 4) {
            return false;
        }
        if (n < 0) {
            n3 = charArrayBuffer.length() - 4 - n2;
        } else {
            n3 = n;
            if (n == 0) {
                do {
                    n3 = n;
                    if (n >= charArrayBuffer.length()) break;
                    n3 = n;
                    if (!HTTP.isWhitespace(charArrayBuffer.charAt(n))) break;
                    ++n;
                } while (true);
            }
        }
        int n4 = n3 + n2;
        if (n4 + 4 > charArrayBuffer.length()) {
            return false;
        }
        boolean bl2 = true;
        for (n = 0; bl2 && n < n2; ++n) {
            bl2 = charArrayBuffer.charAt(n3 + n) == ((String)object).charAt(n);
        }
        boolean bl3 = bl2;
        if (!bl2) return bl3;
        bl2 = bl;
        if (charArrayBuffer.charAt(n4) != '/') return bl2;
        return true;
    }

    @Override
    public Header parseHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        return new BufferedHeader(charArrayBuffer);
    }

    @Override
    public ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor object) throws ParseException {
        int n;
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (object == null) throw new IllegalArgumentException("Parser cursor may not be null");
        String string2 = this.protocol.getProtocol();
        int n2 = string2.length();
        int n3 = ((ParserCursor)object).getPos();
        int n4 = ((ParserCursor)object).getUpperBound();
        this.skipWhitespace(charArrayBuffer, (ParserCursor)object);
        int n5 = ((ParserCursor)object).getPos();
        int n6 = n5 + n2;
        if (n6 + 4 > n4) {
            object = new StringBuffer();
            ((StringBuffer)object).append("Not a valid protocol version: ");
            ((StringBuffer)object).append(charArrayBuffer.substring(n3, n4));
            throw new ParseException(((StringBuffer)object).toString());
        }
        int n7 = 0;
        int n8 = 1;
        for (n = 0; n8 != 0 && n < n2; ++n) {
            n8 = charArrayBuffer.charAt(n5 + n) == string2.charAt(n) ? 1 : 0;
        }
        n = n8;
        if (n8 != 0) {
            n8 = n7;
            if (charArrayBuffer.charAt(n6) == '/') {
                n8 = 1;
            }
            n = n8;
        }
        if (n == 0) {
            object = new StringBuffer();
            ((StringBuffer)object).append("Not a valid protocol version: ");
            ((StringBuffer)object).append(charArrayBuffer.substring(n3, n4));
            throw new ParseException(((StringBuffer)object).toString());
        }
        n = n5 + (n2 + 1);
        n8 = charArrayBuffer.indexOf(46, n, n4);
        if (n8 == -1) {
            object = new StringBuffer();
            ((StringBuffer)object).append("Invalid protocol version number: ");
            ((StringBuffer)object).append(charArrayBuffer.substring(n3, n4));
            throw new ParseException(((StringBuffer)object).toString());
        }
        try {
            n7 = Integer.parseInt(charArrayBuffer.substringTrimmed(n, n8));
            n5 = n8 + 1;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid protocol major version number: ");
            stringBuffer.append(charArrayBuffer.substring(n3, n4));
            throw new ParseException(stringBuffer.toString());
        }
        n8 = n = charArrayBuffer.indexOf(32, n5, n4);
        if (n == -1) {
            n8 = n4;
        }
        try {
            n = Integer.parseInt(charArrayBuffer.substringTrimmed(n5, n8));
        }
        catch (NumberFormatException numberFormatException) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid protocol minor version number: ");
            stringBuffer.append(charArrayBuffer.substring(n3, n4));
            throw new ParseException(stringBuffer.toString());
        }
        ((ParserCursor)object).updatePos(n8);
        return this.createProtocolVersion(n7, n);
    }

    @Override
    public RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor object) throws ParseException {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (object == null) throw new IllegalArgumentException("Parser cursor may not be null");
        int n = ((ParserCursor)object).getPos();
        int n2 = ((ParserCursor)object).getUpperBound();
        try {
            this.skipWhitespace(charArrayBuffer, (ParserCursor)object);
            int n3 = ((ParserCursor)object).getPos();
            int n4 = charArrayBuffer.indexOf(32, n3, n2);
            if (n4 < 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid request line: ");
                stringBuffer.append(charArrayBuffer.substring(n, n2));
                object = new ParseException(stringBuffer.toString());
                throw object;
            }
            String string2 = charArrayBuffer.substringTrimmed(n3, n4);
            ((ParserCursor)object).updatePos(n4);
            this.skipWhitespace(charArrayBuffer, (ParserCursor)object);
            n4 = ((ParserCursor)object).getPos();
            n3 = charArrayBuffer.indexOf(32, n4, n2);
            if (n3 < 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid request line: ");
                stringBuffer.append(charArrayBuffer.substring(n, n2));
                object = new ParseException(stringBuffer.toString());
                throw object;
            }
            String string3 = charArrayBuffer.substringTrimmed(n4, n3);
            ((ParserCursor)object).updatePos(n3);
            Serializable serializable = this.parseProtocolVersion(charArrayBuffer, (ParserCursor)object);
            this.skipWhitespace(charArrayBuffer, (ParserCursor)object);
            if (((ParserCursor)object).atEnd()) {
                return this.createRequestLine(string2, string3, (ProtocolVersion)serializable);
            }
            serializable = new StringBuffer();
            ((StringBuffer)serializable).append("Invalid request line: ");
            ((StringBuffer)serializable).append(charArrayBuffer.substring(n, n2));
            object = new ParseException(((StringBuffer)serializable).toString());
            throw object;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid request line: ");
            stringBuffer.append(charArrayBuffer.substring(n, n2));
            throw new ParseException(stringBuffer.toString());
        }
    }

    @Override
    public StatusLine parseStatusLine(CharArrayBuffer charArrayBuffer, ParserCursor object) throws ParseException {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (object == null) throw new IllegalArgumentException("Parser cursor may not be null");
        int n = ((ParserCursor)object).getPos();
        int n2 = ((ParserCursor)object).getUpperBound();
        try {
            Serializable serializable;
            int n3;
            block8 : {
                serializable = this.parseProtocolVersion(charArrayBuffer, (ParserCursor)object);
                this.skipWhitespace(charArrayBuffer, (ParserCursor)object);
                int n4 = ((ParserCursor)object).getPos();
                int n5 = n3 = charArrayBuffer.indexOf(32, n4, n2);
                if (n3 < 0) {
                    n5 = n2;
                }
                object = charArrayBuffer.substringTrimmed(n4, n5);
                for (n3 = 0; n3 < (n4 = ((String)object).length()); ++n3) {
                    if (Character.isDigit(((String)object).charAt(n3))) {
                        continue;
                    }
                    serializable = new StringBuffer();
                    ((StringBuffer)serializable).append("Status line contains invalid status code: ");
                    ((StringBuffer)serializable).append(charArrayBuffer.substring(n, n2));
                    object = new ParseException(((StringBuffer)serializable).toString());
                    throw object;
                }
                try {
                    n3 = Integer.parseInt((String)object);
                    if (n5 >= n2) break block8;
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Status line contains invalid status code: ");
                    stringBuffer.append(charArrayBuffer.substring(n, n2));
                    serializable = new ParseException(stringBuffer.toString());
                    throw serializable;
                }
                object = charArrayBuffer.substringTrimmed(n5, n2);
                return this.createStatusLine((ProtocolVersion)serializable, n3, (String)object);
            }
            object = "";
            return this.createStatusLine((ProtocolVersion)serializable, n3, (String)object);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid status line: ");
            stringBuffer.append(charArrayBuffer.substring(n, n2));
            throw new ParseException(stringBuffer.toString());
        }
    }

    protected void skipWhitespace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int n;
        int n2 = parserCursor.getUpperBound();
        for (n = parserCursor.getPos(); n < n2 && HTTP.isWhitespace(charArrayBuffer.charAt(n)); ++n) {
        }
        parserCursor.updatePos(n);
    }
}

