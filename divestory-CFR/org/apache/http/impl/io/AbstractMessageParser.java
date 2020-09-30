/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractMessageParser
implements HttpMessageParser {
    private static final int HEADERS = 1;
    private static final int HEAD_LINE = 0;
    private final List headerLines;
    protected final LineParser lineParser;
    private final int maxHeaderCount;
    private final int maxLineLen;
    private HttpMessage message;
    private final SessionInputBuffer sessionBuffer;
    private int state;

    public AbstractMessageParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpParams httpParams) {
        if (sessionInputBuffer == null) throw new IllegalArgumentException("Session input buffer may not be null");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.sessionBuffer = sessionInputBuffer;
        this.maxHeaderCount = httpParams.getIntParameter("http.connection.max-header-count", -1);
        this.maxLineLen = httpParams.getIntParameter("http.connection.max-line-length", -1);
        if (lineParser == null) {
            lineParser = BasicLineParser.DEFAULT;
        }
        this.lineParser = lineParser;
        this.headerLines = new ArrayList();
        this.state = 0;
    }

    public static Header[] parseHeaders(SessionInputBuffer sessionInputBuffer, int n, int n2, LineParser lineParser) throws HttpException, IOException {
        LineParser lineParser2 = lineParser;
        if (lineParser != null) return AbstractMessageParser.parseHeaders(sessionInputBuffer, n, n2, lineParser2, new ArrayList());
        lineParser2 = BasicLineParser.DEFAULT;
        return AbstractMessageParser.parseHeaders(sessionInputBuffer, n, n2, lineParser2, new ArrayList());
    }

    public static Header[] parseHeaders(SessionInputBuffer arrheader, int n, int n2, LineParser lineParser, List list) throws HttpException, IOException {
        CharArrayBuffer charArrayBuffer;
        CharArrayBuffer charArrayBuffer2;
        int n3;
        if (arrheader == null) throw new IllegalArgumentException("Session input buffer may not be null");
        if (lineParser == null) throw new IllegalArgumentException("Line parser may not be null");
        if (list == null) throw new IllegalArgumentException("Header line list may not be null");
        CharArrayBuffer charArrayBuffer3 = charArrayBuffer = null;
        do {
            CharArrayBuffer charArrayBuffer4;
            if (charArrayBuffer == null) {
                charArrayBuffer2 = new CharArrayBuffer(64);
            } else {
                charArrayBuffer.clear();
                charArrayBuffer2 = charArrayBuffer;
            }
            int n4 = arrheader.readLine(charArrayBuffer2);
            n3 = 0;
            if (n4 == -1 || charArrayBuffer2.length() < 1) break;
            if (charArrayBuffer2.charAt(0) != ' ' && charArrayBuffer2.charAt(0) != '\t' || charArrayBuffer3 == null) {
                list.add(charArrayBuffer2);
                charArrayBuffer = null;
                charArrayBuffer4 = charArrayBuffer2;
                charArrayBuffer2 = charArrayBuffer;
            } else {
                int n5;
                for (n5 = 0; n5 < charArrayBuffer2.length() && ((n3 = (int)charArrayBuffer2.charAt(n5)) == 32 || n3 == 9); ++n5) {
                }
                if (n2 > 0) {
                    if (charArrayBuffer3.length() + 1 + charArrayBuffer2.length() - n5 > n2) throw new IOException("Maximum line length limit exceeded");
                }
                charArrayBuffer3.append(' ');
                charArrayBuffer3.append(charArrayBuffer2, n5, charArrayBuffer2.length() - n5);
                charArrayBuffer4 = charArrayBuffer3;
            }
            charArrayBuffer = charArrayBuffer2;
            charArrayBuffer3 = charArrayBuffer4;
            if (n <= 0) continue;
            if (list.size() >= n) throw new IOException("Maximum header count exceeded");
            charArrayBuffer = charArrayBuffer2;
            charArrayBuffer3 = charArrayBuffer4;
        } while (true);
        arrheader = new Header[list.size()];
        n = n3;
        while (n < list.size()) {
            charArrayBuffer2 = (CharArrayBuffer)list.get(n);
            try {
                arrheader[n] = lineParser.parseHeader(charArrayBuffer2);
                ++n;
            }
            catch (ParseException parseException) {
                throw new ProtocolException(parseException.getMessage());
            }
        }
        return arrheader;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public HttpMessage parse() throws IOException, HttpException {
        int n = this.state;
        if (n != 0) {
            if (n != 1) throw new IllegalStateException("Inconsistent parser state");
        } else {
            this.message = this.parseHead(this.sessionBuffer);
            this.state = 1;
        }
        Object object = AbstractMessageParser.parseHeaders(this.sessionBuffer, this.maxHeaderCount, this.maxLineLen, this.lineParser, this.headerLines);
        this.message.setHeaders((Header[])object);
        object = this.message;
        this.message = null;
        this.headerLines.clear();
        this.state = 0;
        return object;
        catch (ParseException parseException) {
            throw new ProtocolException(parseException.getMessage(), parseException);
        }
    }

    protected abstract HttpMessage parseHead(SessionInputBuffer var1) throws IOException, HttpException, ParseException;
}

