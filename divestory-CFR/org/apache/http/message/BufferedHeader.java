/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.FormattedHeader;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class BufferedHeader
implements FormattedHeader,
Cloneable,
Serializable {
    private static final long serialVersionUID = -2768352615787625448L;
    private final CharArrayBuffer buffer;
    private final String name;
    private final int valuePos;

    public BufferedHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        int n = charArrayBuffer.indexOf(58);
        if (n == -1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid header: ");
            stringBuffer.append(charArrayBuffer.toString());
            throw new ParseException(stringBuffer.toString());
        }
        CharSequence charSequence = charArrayBuffer.substringTrimmed(0, n);
        if (((String)charSequence).length() != 0) {
            this.buffer = charArrayBuffer;
            this.name = charSequence;
            this.valuePos = n + 1;
            return;
        }
        charSequence = new StringBuffer();
        ((StringBuffer)charSequence).append("Invalid header: ");
        ((StringBuffer)charSequence).append(charArrayBuffer.toString());
        throw new ParseException(((StringBuffer)charSequence).toString());
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public CharArrayBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public HeaderElement[] getElements() throws ParseException {
        ParserCursor parserCursor = new ParserCursor(0, this.buffer.length());
        parserCursor.updatePos(this.valuePos);
        return BasicHeaderValueParser.DEFAULT.parseElements(this.buffer, parserCursor);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        CharArrayBuffer charArrayBuffer = this.buffer;
        return charArrayBuffer.substringTrimmed(this.valuePos, charArrayBuffer.length());
    }

    @Override
    public int getValuePos() {
        return this.valuePos;
    }

    public String toString() {
        return this.buffer.toString();
    }
}

