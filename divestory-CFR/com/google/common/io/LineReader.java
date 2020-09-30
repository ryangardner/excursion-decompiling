/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.io.LineBuffer;
import java.io.IOException;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class LineReader {
    private final char[] buf;
    private final CharBuffer cbuf;
    private final LineBuffer lineBuf;
    private final Queue<String> lines;
    private final Readable readable;
    @NullableDecl
    private final Reader reader;

    public LineReader(Readable readable) {
        CharBuffer charBuffer;
        this.cbuf = charBuffer = CharStreams.createBuffer();
        this.buf = charBuffer.array();
        this.lines = new LinkedList<String>();
        this.lineBuf = new LineBuffer(){

            @Override
            protected void handleLine(String string2, String string3) {
                LineReader.this.lines.add(string2);
            }
        };
        this.readable = Preconditions.checkNotNull(readable);
        readable = readable instanceof Reader ? (Reader)readable : null;
        this.reader = readable;
    }

    public String readLine() throws IOException {
        while (this.lines.peek() == null) {
            int n;
            this.cbuf.clear();
            Reader reader = this.reader;
            if (reader != null) {
                char[] arrc = this.buf;
                n = reader.read(arrc, 0, arrc.length);
            } else {
                n = this.readable.read(this.cbuf);
            }
            if (n == -1) {
                this.lineBuf.finish();
                return this.lines.poll();
            }
            this.lineBuf.add(this.buf, 0, n);
        }
        return this.lines.poll();
    }

}

