/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class NetscapeDraftHeaderParser {
    public static final NetscapeDraftHeaderParser DEFAULT = new NetscapeDraftHeaderParser();

    private NameValuePair parseNameValuePair(CharArrayBuffer object, ParserCursor parserCursor) {
        int n;
        int n2;
        String string2;
        int n3;
        int n4;
        int n5;
        block9 : {
            n5 = parserCursor.getPos();
            n = parserCursor.getPos();
            n4 = parserCursor.getUpperBound();
            do {
                n3 = 1;
                if (n5 >= n4 || (n2 = ((CharArrayBuffer)object).charAt(n5)) == 61) break;
                if (n2 == 59) {
                    n2 = 1;
                    break block9;
                }
                ++n5;
            } while (true);
            n2 = 0;
        }
        if (n5 == n4) {
            string2 = ((CharArrayBuffer)object).substringTrimmed(n, n4);
            n2 = 1;
        } else {
            string2 = ((CharArrayBuffer)object).substringTrimmed(n, n5);
            ++n5;
        }
        if (n2 != 0) {
            parserCursor.updatePos(n5);
            return new BasicNameValuePair(string2, null);
        }
        for (n = n5; n < n4; ++n) {
            if (((CharArrayBuffer)object).charAt(n) != ';') continue;
            n2 = n3;
            break;
        }
        while (n5 < n && HTTP.isWhitespace(((CharArrayBuffer)object).charAt(n5))) {
            ++n5;
        }
        for (n3 = n; n3 > n5 && HTTP.isWhitespace(((CharArrayBuffer)object).charAt(n3 - 1)); --n3) {
        }
        object = ((CharArrayBuffer)object).substring(n5, n3);
        n5 = n;
        if (n2 != 0) {
            n5 = n + 1;
        }
        parserCursor.updatePos(n5);
        return new BasicNameValuePair(string2, (String)object);
    }

    public HeaderElement parseHeader(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        if (charArrayBuffer == null) throw new IllegalArgumentException("Char array buffer may not be null");
        if (parserCursor == null) throw new IllegalArgumentException("Parser cursor may not be null");
        NameValuePair nameValuePair = this.parseNameValuePair(charArrayBuffer, parserCursor);
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        while (!parserCursor.atEnd()) {
            arrayList.add(this.parseNameValuePair(charArrayBuffer, parserCursor));
        }
        return new BasicHeaderElement(nameValuePair.getName(), nameValuePair.getValue(), arrayList.toArray(new NameValuePair[arrayList.size()]));
    }
}

