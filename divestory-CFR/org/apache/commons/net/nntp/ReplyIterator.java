/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.io.Util;

class ReplyIterator
implements Iterator<String>,
Iterable<String> {
    private String line;
    private final BufferedReader reader;
    private Exception savedException;

    ReplyIterator(BufferedReader bufferedReader) throws IOException {
        this(bufferedReader, true);
    }

    ReplyIterator(BufferedReader object, boolean bl) throws IOException {
        BufferedReader bufferedReader = object;
        if (bl) {
            bufferedReader = new DotTerminatedMessageReader((Reader)object);
        }
        this.reader = bufferedReader;
        this.line = object = bufferedReader.readLine();
        if (object != null) return;
        Util.closeQuietly(this.reader);
    }

    @Override
    public boolean hasNext() {
        if (this.savedException != null) throw new NoSuchElementException(this.savedException.toString());
        if (this.line == null) return false;
        return true;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public String next() throws NoSuchElementException {
        if (this.savedException != null) throw new NoSuchElementException(this.savedException.toString());
        String string2 = this.line;
        if (string2 == null) throw new NoSuchElementException();
        try {
            String string3;
            this.line = string3 = this.reader.readLine();
            if (string3 != null) return string2;
            Util.closeQuietly(this.reader);
            return string2;
        }
        catch (IOException iOException) {
            this.savedException = iOException;
            Util.closeQuietly(this.reader);
        }
        return string2;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

