/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSequenceReader;
import com.google.common.io.CharSink;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;
import com.google.common.io.LineProcessor;
import com.google.common.io.MultiReader;
import com.google.common.io.ReaderInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class CharSource {
    protected CharSource() {
    }

    public static CharSource concat(Iterable<? extends CharSource> iterable) {
        return new ConcatenatedCharSource(iterable);
    }

    public static CharSource concat(Iterator<? extends CharSource> iterator2) {
        return CharSource.concat(ImmutableList.copyOf(iterator2));
    }

    public static CharSource concat(CharSource ... arrcharSource) {
        return CharSource.concat(ImmutableList.copyOf(arrcharSource));
    }

    private long countBySkipping(Reader reader) throws IOException {
        long l;
        long l2 = 0L;
        while ((l = reader.skip(Long.MAX_VALUE)) != 0L) {
            l2 += l;
        }
        return l2;
    }

    public static CharSource empty() {
        return EmptyCharSource.INSTANCE;
    }

    public static CharSource wrap(CharSequence object) {
        if (!(object instanceof String)) return new CharSequenceCharSource((CharSequence)object);
        return new StringCharSource((String)object);
    }

    public ByteSource asByteSource(Charset charset) {
        return new AsByteSource(charset);
    }

    public long copyTo(CharSink charSink) throws IOException {
        long l;
        Preconditions.checkNotNull(charSink);
        Closer closer = Closer.create();
        try {
            l = CharStreams.copy(closer.register(this.openStream()), closer.register(charSink.openStream()));
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return l;
    }

    public long copyTo(Appendable appendable) throws IOException {
        long l;
        Preconditions.checkNotNull(appendable);
        Closer closer = Closer.create();
        try {
            l = CharStreams.copy(closer.register(this.openStream()), appendable);
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return l;
    }

    public boolean isEmpty() throws IOException {
        Object object = this.lengthIfKnown();
        boolean bl = ((Optional)object).isPresent();
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (((Optional)object).get() != 0L) return false;
            return bl3;
        }
        object = Closer.create();
        try {
            int n = ((Closer)object).register(this.openStream()).read();
            bl3 = n == -1 ? bl2 : false;
        }
        catch (Throwable throwable) {
            try {
                throw ((Closer)object).rethrow(throwable);
            }
            catch (Throwable throwable2) {
                ((Closer)object).close();
                throw throwable2;
            }
        }
        ((Closer)object).close();
        return bl3;
    }

    public long length() throws IOException {
        long l;
        Optional<Long> optional = this.lengthIfKnown();
        if (optional.isPresent()) {
            return optional.get();
        }
        optional = Closer.create();
        try {
            l = this.countBySkipping(((Closer)((Object)optional)).register(this.openStream()));
        }
        catch (Throwable throwable) {
            try {
                throw ((Closer)((Object)optional)).rethrow(throwable);
            }
            catch (Throwable throwable2) {
                ((Closer)((Object)optional)).close();
                throw throwable2;
            }
        }
        ((Closer)((Object)optional)).close();
        return l;
    }

    public Optional<Long> lengthIfKnown() {
        return Optional.absent();
    }

    public BufferedReader openBufferedStream() throws IOException {
        Reader reader = this.openStream();
        if (!(reader instanceof BufferedReader)) return new BufferedReader(reader);
        return (BufferedReader)reader;
    }

    public abstract Reader openStream() throws IOException;

    public String read() throws IOException {
        String string2;
        Closer closer = Closer.create();
        try {
            string2 = CharStreams.toString(closer.register(this.openStream()));
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return string2;
    }

    @NullableDecl
    public String readFirstLine() throws IOException {
        String string2;
        Closer closer = Closer.create();
        try {
            string2 = closer.register(this.openBufferedStream()).readLine();
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return string2;
    }

    public ImmutableList<String> readLines() throws IOException {
        Object object;
        Closer closer = Closer.create();
        try {
            BufferedReader bufferedReader = closer.register(this.openBufferedStream());
            ArrayList<String> arrayList = Lists.newArrayList();
            while ((object = bufferedReader.readLine()) != null) {
                arrayList.add((String)object);
            }
            object = ImmutableList.copyOf(arrayList);
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return object;
    }

    public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
        Preconditions.checkNotNull(lineProcessor);
        Closer closer = Closer.create();
        try {
            lineProcessor = CharStreams.readLines(closer.register(this.openStream()), lineProcessor);
        }
        catch (Throwable throwable) {
            try {
                throw closer.rethrow(throwable);
            }
            catch (Throwable throwable2) {
                closer.close();
                throw throwable2;
            }
        }
        closer.close();
        return (T)lineProcessor;
    }

    private final class AsByteSource
    extends ByteSource {
        final Charset charset;

        AsByteSource(Charset charset) {
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public CharSource asCharSource(Charset charset) {
            if (!charset.equals(this.charset)) return super.asCharSource(charset);
            return CharSource.this;
        }

        @Override
        public InputStream openStream() throws IOException {
            return new ReaderInputStream(CharSource.this.openStream(), this.charset, 8192);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CharSource.this.toString());
            stringBuilder.append(".asByteSource(");
            stringBuilder.append(this.charset);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class CharSequenceCharSource
    extends CharSource {
        private static final Splitter LINE_SPLITTER = Splitter.onPattern("\r\n|\n|\r");
        protected final CharSequence seq;

        protected CharSequenceCharSource(CharSequence charSequence) {
            this.seq = Preconditions.checkNotNull(charSequence);
        }

        private Iterator<String> linesIterator() {
            return new AbstractIterator<String>(){
                Iterator<String> lines;
                {
                    this.lines = LINE_SPLITTER.split(CharSequenceCharSource.this.seq).iterator();
                }

                @Override
                protected String computeNext() {
                    if (!this.lines.hasNext()) return (String)this.endOfData();
                    String string2 = this.lines.next();
                    if (this.lines.hasNext()) return string2;
                    if (string2.isEmpty()) return (String)this.endOfData();
                    return string2;
                }
            };
        }

        @Override
        public boolean isEmpty() {
            if (this.seq.length() != 0) return false;
            return true;
        }

        @Override
        public long length() {
            return this.seq.length();
        }

        @Override
        public Optional<Long> lengthIfKnown() {
            return Optional.of(Long.valueOf(this.seq.length()));
        }

        @Override
        public Reader openStream() {
            return new CharSequenceReader(this.seq);
        }

        @Override
        public String read() {
            return this.seq.toString();
        }

        @Override
        public String readFirstLine() {
            Iterator<String> iterator2 = this.linesIterator();
            if (!iterator2.hasNext()) return null;
            return iterator2.next();
        }

        @Override
        public ImmutableList<String> readLines() {
            return ImmutableList.copyOf(this.linesIterator());
        }

        @Override
        public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
            Iterator<String> iterator2 = this.linesIterator();
            do {
                if (!iterator2.hasNext()) return lineProcessor.getResult();
            } while (lineProcessor.processLine(iterator2.next()));
            return lineProcessor.getResult();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharSource.wrap(");
            stringBuilder.append(Ascii.truncate(this.seq, 30, "..."));
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

    }

    private static final class ConcatenatedCharSource
    extends CharSource {
        private final Iterable<? extends CharSource> sources;

        ConcatenatedCharSource(Iterable<? extends CharSource> iterable) {
            this.sources = Preconditions.checkNotNull(iterable);
        }

        @Override
        public boolean isEmpty() throws IOException {
            Iterator<? extends CharSource> iterator2 = this.sources.iterator();
            do {
                if (!iterator2.hasNext()) return true;
            } while (iterator2.next().isEmpty());
            return false;
        }

        @Override
        public long length() throws IOException {
            Iterator<? extends CharSource> iterator2 = this.sources.iterator();
            long l = 0L;
            while (iterator2.hasNext()) {
                l += iterator2.next().length();
            }
            return l;
        }

        @Override
        public Optional<Long> lengthIfKnown() {
            Iterator<? extends CharSource> iterator2 = this.sources.iterator();
            long l = 0L;
            while (iterator2.hasNext()) {
                Optional<Long> optional = iterator2.next().lengthIfKnown();
                if (!optional.isPresent()) {
                    return Optional.absent();
                }
                l += optional.get().longValue();
            }
            return Optional.of(l);
        }

        @Override
        public Reader openStream() throws IOException {
            return new MultiReader(this.sources.iterator());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharSource.concat(");
            stringBuilder.append(this.sources);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class EmptyCharSource
    extends StringCharSource {
        private static final EmptyCharSource INSTANCE = new EmptyCharSource();

        private EmptyCharSource() {
            super("");
        }

        @Override
        public String toString() {
            return "CharSource.empty()";
        }
    }

    private static class StringCharSource
    extends CharSequenceCharSource {
        protected StringCharSource(String string2) {
            super(string2);
        }

        @Override
        public long copyTo(CharSink charSink) throws IOException {
            int n;
            Preconditions.checkNotNull(charSink);
            Closer closer = Closer.create();
            try {
                closer.register(charSink.openStream()).write((String)this.seq);
                n = this.seq.length();
            }
            catch (Throwable throwable) {
                try {
                    throw closer.rethrow(throwable);
                }
                catch (Throwable throwable2) {
                    closer.close();
                    throw throwable2;
                }
            }
            long l = n;
            closer.close();
            return l;
        }

        @Override
        public long copyTo(Appendable appendable) throws IOException {
            appendable.append(this.seq);
            return this.seq.length();
        }

        @Override
        public Reader openStream() {
            return new StringReader((String)this.seq);
        }
    }

}

