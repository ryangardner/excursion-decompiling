/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.HeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderElementIterator
implements HeaderElementIterator {
    private CharArrayBuffer buffer = null;
    private HeaderElement currentElement = null;
    private ParserCursor cursor = null;
    private final HeaderIterator headerIt;
    private final HeaderValueParser parser;

    public BasicHeaderElementIterator(HeaderIterator headerIterator) {
        this(headerIterator, BasicHeaderValueParser.DEFAULT);
    }

    public BasicHeaderElementIterator(HeaderIterator headerIterator, HeaderValueParser headerValueParser) {
        if (headerIterator == null) throw new IllegalArgumentException("Header iterator may not be null");
        if (headerValueParser == null) throw new IllegalArgumentException("Parser may not be null");
        this.headerIt = headerIterator;
        this.parser = headerValueParser;
    }

    private void bufferHeaderValue() {
        Object object;
        Object object2;
        this.cursor = null;
        this.buffer = null;
        do {
            if (!this.headerIt.hasNext()) return;
            object = this.headerIt.nextHeader();
            if (!(object instanceof FormattedHeader)) continue;
            object2 = (FormattedHeader)object;
            this.buffer = object2.getBuffer();
            this.cursor = object = new ParserCursor(0, this.buffer.length());
            ((ParserCursor)object).updatePos(object2.getValuePos());
            return;
        } while ((object2 = object.getValue()) == null);
        this.buffer = object = new CharArrayBuffer(((String)object2).length());
        ((CharArrayBuffer)object).append((String)object2);
        this.cursor = new ParserCursor(0, this.buffer.length());
    }

    private void parseNextElement() {
        do {
            Object object;
            if (!this.headerIt.hasNext()) {
                if (this.cursor == null) return;
            }
            if ((object = this.cursor) == null || ((ParserCursor)object).atEnd()) {
                this.bufferHeaderValue();
            }
            if (this.cursor == null) continue;
            while (!this.cursor.atEnd()) {
                object = this.parser.parseHeaderElement(this.buffer, this.cursor);
                if (object.getName().length() == 0 && object.getValue() == null) continue;
                this.currentElement = object;
                return;
            }
            if (!this.cursor.atEnd()) continue;
            this.cursor = null;
            this.buffer = null;
        } while (true);
    }

    @Override
    public boolean hasNext() {
        if (this.currentElement == null) {
            this.parseNextElement();
        }
        if (this.currentElement == null) return false;
        return true;
    }

    public final Object next() throws NoSuchElementException {
        return this.nextElement();
    }

    @Override
    public HeaderElement nextElement() throws NoSuchElementException {
        HeaderElement headerElement;
        if (this.currentElement == null) {
            this.parseNextElement();
        }
        if ((headerElement = this.currentElement) == null) throw new NoSuchElementException("No more header elements available");
        this.currentElement = null;
        return headerElement;
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove not supported");
    }
}

