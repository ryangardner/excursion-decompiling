/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ProtocolException;
import org.apache.http.StatusLine;
import org.apache.http.impl.io.AbstractMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.LineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

public class DefaultResponseParser
extends AbstractMessageParser {
    private final CharArrayBuffer lineBuf;
    private final Log log = LogFactory.getLog(this.getClass());
    private final int maxGarbageLines;
    private final HttpResponseFactory responseFactory;

    public DefaultResponseParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        if (httpResponseFactory == null) throw new IllegalArgumentException("Response factory may not be null");
        this.responseFactory = httpResponseFactory;
        this.lineBuf = new CharArrayBuffer(128);
        this.maxGarbageLines = httpParams.getIntParameter("http.connection.max-status-line-garbage", Integer.MAX_VALUE);
    }

    @Override
    protected HttpMessage parseHead(SessionInputBuffer object) throws IOException, HttpException {
        int n = 0;
        do {
            ParserCursor parserCursor;
            this.lineBuf.clear();
            int n2 = object.readLine(this.lineBuf);
            if (n2 == -1) {
                if (n == 0) throw new NoHttpResponseException("The target server failed to respond");
            }
            if (this.lineParser.hasProtocolVersion(this.lineBuf, parserCursor = new ParserCursor(0, this.lineBuf.length()))) {
                object = this.lineParser.parseStatusLine(this.lineBuf, parserCursor);
                return this.responseFactory.newHttpResponse((StatusLine)object, null);
            }
            if (n2 == -1) throw new ProtocolException("The server failed to respond with a valid HTTP response");
            if (n >= this.maxGarbageLines) throw new ProtocolException("The server failed to respond with a valid HTTP response");
            if (this.log.isDebugEnabled()) {
                parserCursor = this.log;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Garbage in response: ");
                stringBuilder.append(this.lineBuf.toString());
                parserCursor.debug((Object)stringBuilder.toString());
            }
            ++n;
        } while (true);
    }
}

