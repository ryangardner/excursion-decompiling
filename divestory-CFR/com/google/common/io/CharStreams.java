/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.AppendableWriter;
import com.google.common.io.LineProcessor;
import com.google.common.io.LineReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public final class CharStreams {
    private static final int DEFAULT_BUF_SIZE = 2048;

    private CharStreams() {
    }

    public static Writer asWriter(Appendable appendable) {
        if (!(appendable instanceof Writer)) return new AppendableWriter(appendable);
        return (Writer)appendable;
    }

    public static long copy(Readable readable, Appendable appendable) throws IOException {
        if (readable instanceof Reader) {
            if (!(appendable instanceof StringBuilder)) return CharStreams.copyReaderToWriter((Reader)readable, CharStreams.asWriter(appendable));
            return CharStreams.copyReaderToBuilder((Reader)readable, (StringBuilder)appendable);
        }
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(appendable);
        long l = 0L;
        CharBuffer charBuffer = CharStreams.createBuffer();
        while (readable.read(charBuffer) != -1) {
            charBuffer.flip();
            appendable.append(charBuffer);
            l += (long)charBuffer.remaining();
            charBuffer.clear();
        }
        return l;
    }

    static long copyReaderToBuilder(Reader reader, StringBuilder stringBuilder) throws IOException {
        int n;
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(stringBuilder);
        char[] arrc = new char[2048];
        long l = 0L;
        while ((n = reader.read(arrc)) != -1) {
            stringBuilder.append(arrc, 0, n);
            l += (long)n;
        }
        return l;
    }

    static long copyReaderToWriter(Reader reader, Writer writer) throws IOException {
        int n;
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(writer);
        char[] arrc = new char[2048];
        long l = 0L;
        while ((n = reader.read(arrc)) != -1) {
            writer.write(arrc, 0, n);
            l += (long)n;
        }
        return l;
    }

    static CharBuffer createBuffer() {
        return CharBuffer.allocate(2048);
    }

    public static long exhaust(Readable readable) throws IOException {
        long l;
        CharBuffer charBuffer = CharStreams.createBuffer();
        long l2 = 0L;
        while ((l = (long)readable.read(charBuffer)) != -1L) {
            l2 += l;
            charBuffer.clear();
        }
        return l2;
    }

    public static Writer nullWriter() {
        return NullWriter.INSTANCE;
    }

    public static <T> T readLines(Readable object, LineProcessor<T> lineProcessor) throws IOException {
        String string2;
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(lineProcessor);
        object = new LineReader((Readable)object);
        do {
            if ((string2 = ((LineReader)object).readLine()) == null) return lineProcessor.getResult();
        } while (lineProcessor.processLine(string2));
        return lineProcessor.getResult();
    }

    public static List<String> readLines(Readable object) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        LineReader lineReader = new LineReader((Readable)object);
        while ((object = lineReader.readLine()) != null) {
            arrayList.add((String)object);
        }
        return arrayList;
    }

    public static void skipFully(Reader reader, long l) throws IOException {
        Preconditions.checkNotNull(reader);
        while (l > 0L) {
            long l2 = reader.skip(l);
            if (l2 == 0L) throw new EOFException();
            l -= l2;
        }
    }

    public static String toString(Readable readable) throws IOException {
        return CharStreams.toStringBuilder(readable).toString();
    }

    private static StringBuilder toStringBuilder(Readable readable) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (readable instanceof Reader) {
            CharStreams.copyReaderToBuilder((Reader)readable, stringBuilder);
            return stringBuilder;
        }
        CharStreams.copy(readable, stringBuilder);
        return stringBuilder;
    }

    private static final class NullWriter
    extends Writer {
        private static final NullWriter INSTANCE = new NullWriter();

        private NullWriter() {
        }

        @Override
        public Writer append(char c) {
            return this;
        }

        @Override
        public Writer append(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return this;
        }

        @Override
        public Writer append(CharSequence charSequence, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, charSequence.length());
            return this;
        }

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        public String toString() {
            return "CharStreams.nullWriter()";
        }

        @Override
        public void write(int n) {
        }

        @Override
        public void write(String string2) {
            Preconditions.checkNotNull(string2);
        }

        @Override
        public void write(String string2, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2 + n, string2.length());
        }

        @Override
        public void write(char[] arrc) {
            Preconditions.checkNotNull(arrc);
        }

        @Override
        public void write(char[] arrc, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2 + n, arrc.length);
        }
    }

}

