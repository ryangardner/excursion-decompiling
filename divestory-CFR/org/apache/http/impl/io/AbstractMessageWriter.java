/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractMessageWriter
implements HttpMessageWriter {
    protected final CharArrayBuffer lineBuf;
    protected final LineFormatter lineFormatter;
    protected final SessionOutputBuffer sessionBuffer;

    public AbstractMessageWriter(SessionOutputBuffer sessionOutputBuffer, LineFormatter lineFormatter, HttpParams httpParams) {
        if (sessionOutputBuffer == null) throw new IllegalArgumentException("Session input buffer may not be null");
        this.sessionBuffer = sessionOutputBuffer;
        this.lineBuf = new CharArrayBuffer(128);
        if (lineFormatter == null) {
            lineFormatter = BasicLineFormatter.DEFAULT;
        }
        this.lineFormatter = lineFormatter;
    }

    @Override
    public void write(HttpMessage object) throws IOException, HttpException {
        if (object == null) throw new IllegalArgumentException("HTTP message may not be null");
        this.writeHeadLine((HttpMessage)object);
        HeaderIterator headerIterator = object.headerIterator();
        do {
            if (!headerIterator.hasNext()) {
                this.lineBuf.clear();
                this.sessionBuffer.writeLine(this.lineBuf);
                return;
            }
            object = (Header)headerIterator.next();
            this.sessionBuffer.writeLine(this.lineFormatter.formatHeader(this.lineBuf, (Header)object));
        } while (true);
    }

    protected abstract void writeHeadLine(HttpMessage var1) throws IOException;
}

