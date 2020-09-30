/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.util.ArrayList;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderValueParser
implements HeaderValueParser {
    private static final char[] ALL_DELIMITERS;
    public static final BasicHeaderValueParser DEFAULT;
    private static final char ELEM_DELIMITER = ',';
    private static final char PARAM_DELIMITER = ';';

    static {
        DEFAULT = new BasicHeaderValueParser();
        ALL_DELIMITERS = new char[]{';', ','};
    }

    private static boolean isOneOf(char c, char[] arrc) {
        if (arrc == null) return false;
        int n = 0;
        while (n < arrc.length) {
            if (c == arrc[n]) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public static final HeaderElement[] parseElements(String string2, HeaderValueParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null");
        HeaderValueParser headerValueParser = object;
        if (object == null) {
            headerValueParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return headerValueParser.parseElements((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    public static final HeaderElement parseHeaderElement(String string2, HeaderValueParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null");
        HeaderValueParser headerValueParser = object;
        if (object == null) {
            headerValueParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return headerValueParser.parseHeaderElement((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    public static final NameValuePair parseNameValuePair(String string2, HeaderValueParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null");
        HeaderValueParser headerValueParser = object;
        if (object == null) {
            headerValueParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return headerValueParser.parseNameValuePair((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    public static final NameValuePair[] parseParameters(String string2, HeaderValueParser object) throws ParseException {
        if (string2 == null) throw new IllegalArgumentException("Value to parse may not be null");
        HeaderValueParser headerValueParser = object;
        if (object == null) {
            headerValueParser = DEFAULT;
        }
        object = new CharArrayBuffer(string2.length());
        ((CharArrayBuffer)object).append(string2);
        return headerValueParser.parseParameters((CharArrayBuffer)object, new ParserCursor(0, string2.length()));
    }

    protected HeaderElement createHeaderElement(String string2, String string3, NameValuePair[] arrnameValuePair) {
        return new BasicHeaderElement(string2, string3, arrnameValuePair);
    }

    protected NameValuePair createNameValuePair(String string2, String string3) {
        return new BasicNameValuePair(string2, string3);
    }

    @Override
    public HeaderElement[] parseElements(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (parserCursor == null) throw new IllegalArgumentException("Parser cursor may not be null");
        ArrayList<HeaderElement> arrayList = new ArrayList<HeaderElement>();
        while (!parserCursor.atEnd()) {
            HeaderElement headerElement = this.parseHeaderElement(charArrayBuffer, parserCursor);
            if (headerElement.getName().length() == 0 && headerElement.getValue() == null) continue;
            arrayList.add(headerElement);
        }
        return arrayList.toArray(new HeaderElement[arrayList.size()]);
    }

    @Override
    public HeaderElement parseHeaderElement(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        NameValuePair[] arrnameValuePair;
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (parserCursor == null) throw new IllegalArgumentException("Parser cursor may not be null");
        NameValuePair nameValuePair = this.parseNameValuePair(charArrayBuffer, parserCursor);
        NameValuePair[] arrnameValuePair2 = arrnameValuePair = null;
        if (parserCursor.atEnd()) return this.createHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), arrnameValuePair2);
        arrnameValuePair2 = arrnameValuePair;
        if (charArrayBuffer.charAt(parserCursor.getPos() - 1) == ',') return this.createHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), arrnameValuePair2);
        arrnameValuePair2 = this.parseParameters(charArrayBuffer, parserCursor);
        return this.createHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), arrnameValuePair2);
    }

    @Override
    public NameValuePair parseNameValuePair(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        return this.parseNameValuePair(charArrayBuffer, parserCursor, ALL_DELIMITERS);
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer object, ParserCursor parserCursor, char[] arrc) {
        int n;
        int n2;
        String string2;
        int n3;
        int n4;
        int n5;
        int n6;
        block16 : {
            char c;
            int n7;
            int n8;
            block15 : {
                if (object == null) throw new IllegalArgumentException("Char array buffer may not be null");
                if (parserCursor == null) throw new IllegalArgumentException("Parser cursor may not be null");
                n = parserCursor.getPos();
                n6 = parserCursor.getPos();
                n8 = parserCursor.getUpperBound();
                do {
                    n7 = 1;
                    if (n >= n8 || (c = ((CharArrayBuffer)object).charAt(n)) == '=') break;
                    if (BasicHeaderValueParser.isOneOf(c, arrc)) {
                        n3 = 1;
                        break block15;
                    }
                    ++n;
                } while (true);
                n3 = 0;
            }
            if (n == n8) {
                string2 = ((CharArrayBuffer)object).substringTrimmed(n6, n8);
                n3 = 1;
            } else {
                string2 = ((CharArrayBuffer)object).substringTrimmed(n6, n);
                ++n;
            }
            if (n3 != 0) {
                parserCursor.updatePos(n);
                return this.createNameValuePair(string2, null);
            }
            n5 = 0;
            n2 = 0;
            for (n6 = n; n6 < n8; ++n6) {
                c = ((CharArrayBuffer)object).charAt(n6);
                n4 = n2;
                if (c == '\"') {
                    n4 = n2;
                    if (n5 == 0) {
                        n4 = n2 ^ 1;
                    }
                }
                if (n4 == 0 && n5 == 0 && BasicHeaderValueParser.isOneOf(c, arrc)) {
                    n5 = n7;
                    break block16;
                }
                n5 = n5 == 0 && n4 != 0 && c == '\\' ? 1 : 0;
                n2 = n4;
            }
            n5 = n3;
        }
        while (n < n6 && HTTP.isWhitespace(((CharArrayBuffer)object).charAt(n))) {
            ++n;
        }
        for (n3 = n6; n3 > n && HTTP.isWhitespace(((CharArrayBuffer)object).charAt(n3 - 1)); --n3) {
        }
        n2 = n;
        n4 = n3;
        if (n3 - n >= 2) {
            n2 = n;
            n4 = n3;
            if (((CharArrayBuffer)object).charAt(n) == '\"') {
                n2 = n;
                n4 = n3;
                if (((CharArrayBuffer)object).charAt(n3 - 1) == '\"') {
                    n2 = n + 1;
                    n4 = n3 - 1;
                }
            }
        }
        object = ((CharArrayBuffer)object).substring(n2, n4);
        n = n6;
        if (n5 != 0) {
            n = n6 + 1;
        }
        parserCursor.updatePos(n);
        return this.createNameValuePair(string2, (String)object);
    }

    @Override
    public NameValuePair[] parseParameters(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int n;
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (parserCursor == null) throw new IllegalArgumentException("Parser cursor may not be null");
        int n2 = parserCursor.getUpperBound();
        for (n = parserCursor.getPos(); n < n2 && HTTP.isWhitespace(charArrayBuffer.charAt(n)); ++n) {
        }
        parserCursor.updatePos(n);
        if (parserCursor.atEnd()) {
            return new NameValuePair[0];
        }
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        do {
            if (parserCursor.atEnd()) return arrayList.toArray(new NameValuePair[arrayList.size()]);
            arrayList.add(this.parseNameValuePair(charArrayBuffer, parserCursor));
        } while (charArrayBuffer.charAt(parserCursor.getPos() - 1) != ',');
        return arrayList.toArray(new NameValuePair[arrayList.size()]);
    }
}

