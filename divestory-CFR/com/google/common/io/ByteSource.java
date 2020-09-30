/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSource;
import com.google.common.io.Closer;
import com.google.common.io.MultiInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class ByteSource {
    protected ByteSource() {
    }

    public static ByteSource concat(Iterable<? extends ByteSource> iterable) {
        return new ConcatenatedByteSource(iterable);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> iterator2) {
        return ByteSource.concat(ImmutableList.copyOf(iterator2));
    }

    public static ByteSource concat(ByteSource ... arrbyteSource) {
        return ByteSource.concat(ImmutableList.copyOf(arrbyteSource));
    }

    private long countBySkipping(InputStream inputStream2) throws IOException {
        long l;
        long l2 = 0L;
        while ((l = ByteStreams.skipUpTo(inputStream2, Integer.MAX_VALUE)) > 0L) {
            l2 += l;
        }
        return l2;
    }

    public static ByteSource empty() {
        return EmptyByteSource.INSTANCE;
    }

    public static ByteSource wrap(byte[] arrby) {
        return new ByteArrayByteSource(arrby);
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(charset);
    }

    public boolean contentEquals(ByteSource object) throws IOException {
        Preconditions.checkNotNull(object);
        byte[] arrby = ByteStreams.createBuffer();
        byte[] arrby2 = ByteStreams.createBuffer();
        Closer closer = Closer.create();
        try {
            int n;
            InputStream inputStream2 = closer.register(this.openStream());
            object = closer.register(((ByteSource)object).openStream());
            while ((n = ByteStreams.read(inputStream2, arrby, 0, arrby.length)) == ByteStreams.read((InputStream)object, arrby2, 0, arrby2.length) && Arrays.equals(arrby, arrby2)) {
                int n2 = arrby.length;
                if (n == n2) continue;
            }
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
        {
            closer.close();
            return true;
        }
        closer.close();
        return false;
    }

    public long copyTo(ByteSink byteSink) throws IOException {
        long l;
        Preconditions.checkNotNull(byteSink);
        Closer closer = Closer.create();
        try {
            l = ByteStreams.copy(closer.register(this.openStream()), closer.register(byteSink.openStream()));
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

    public long copyTo(OutputStream outputStream2) throws IOException {
        long l;
        Preconditions.checkNotNull(outputStream2);
        Closer closer = Closer.create();
        try {
            l = ByteStreams.copy(closer.register(this.openStream()), outputStream2);
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

    public HashCode hash(HashFunction object) throws IOException {
        object = object.newHasher();
        this.copyTo(Funnels.asOutputStream((PrimitiveSink)object));
        return object.hash();
    }

    public boolean isEmpty() throws IOException {
        Object object = this.sizeIfKnown();
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

    public InputStream openBufferedStream() throws IOException {
        InputStream inputStream2 = this.openStream();
        if (!(inputStream2 instanceof BufferedInputStream)) return new BufferedInputStream(inputStream2);
        return (BufferedInputStream)inputStream2;
    }

    public abstract InputStream openStream() throws IOException;

    public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
        Preconditions.checkNotNull(byteProcessor);
        Closer closer = Closer.create();
        try {
            byteProcessor = ByteStreams.readBytes(closer.register(this.openStream()), byteProcessor);
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
        return (T)byteProcessor;
    }

    public byte[] read() throws IOException {
        byte[] arrby;
        Closer closer = Closer.create();
        try {
            arrby = closer.register(this.openStream());
            Optional<Long> optional = this.sizeIfKnown();
            arrby = optional.isPresent() ? ByteStreams.toByteArray((InputStream)arrby, optional.get()) : ByteStreams.toByteArray((InputStream)arrby);
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
        return arrby;
    }

    public long size() throws IOException {
        Optional<Long> optional = this.sizeIfKnown();
        if (optional.isPresent()) {
            return optional.get();
        }
        optional = Closer.create();
        try {
            long l = this.countBySkipping(((Closer)((Object)optional)).register(this.openStream()));
            return l;
        }
        catch (IOException iOException) {
            ((Closer)((Object)optional)).close();
            optional = Closer.create();
            try {
                long l = ByteStreams.exhaust(((Closer)((Object)optional)).register(this.openStream()));
                return l;
            }
            catch (Throwable throwable) {
                throw ((Closer)((Object)optional)).rethrow(throwable);
            }
        }
        finally {
            ((Closer)((Object)optional)).close();
        }
    }

    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    public ByteSource slice(long l, long l2) {
        return new SlicedByteSource(l, l2);
    }

    class AsCharSource
    extends CharSource {
        final Charset charset;

        AsCharSource(Charset charset) {
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public ByteSource asByteSource(Charset charset) {
            if (!charset.equals(this.charset)) return super.asByteSource(charset);
            return ByteSource.this;
        }

        @Override
        public Reader openStream() throws IOException {
            return new InputStreamReader(ByteSource.this.openStream(), this.charset);
        }

        @Override
        public String read() throws IOException {
            return new String(ByteSource.this.read(), this.charset);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ByteSource.this.toString());
            stringBuilder.append(".asCharSource(");
            stringBuilder.append(this.charset);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class ByteArrayByteSource
    extends ByteSource {
        final byte[] bytes;
        final int length;
        final int offset;

        ByteArrayByteSource(byte[] arrby) {
            this(arrby, 0, arrby.length);
        }

        ByteArrayByteSource(byte[] arrby, int n, int n2) {
            this.bytes = arrby;
            this.offset = n;
            this.length = n2;
        }

        @Override
        public long copyTo(OutputStream outputStream2) throws IOException {
            outputStream2.write(this.bytes, this.offset, this.length);
            return this.length;
        }

        @Override
        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.bytes, this.offset, this.length);
        }

        @Override
        public boolean isEmpty() {
            if (this.length != 0) return false;
            return true;
        }

        @Override
        public InputStream openBufferedStream() throws IOException {
            return this.openStream();
        }

        @Override
        public InputStream openStream() {
            return new ByteArrayInputStream(this.bytes, this.offset, this.length);
        }

        @Override
        public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
            byteProcessor.processBytes(this.bytes, this.offset, this.length);
            return byteProcessor.getResult();
        }

        @Override
        public byte[] read() {
            byte[] arrby = this.bytes;
            int n = this.offset;
            return Arrays.copyOfRange(arrby, n, this.length + n);
        }

        @Override
        public long size() {
            return this.length;
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf(this.length));
        }

        @Override
        public ByteSource slice(long l, long l2) {
            boolean bl = true;
            boolean bl2 = l >= 0L;
            Preconditions.checkArgument(bl2, "offset (%s) may not be negative", l);
            bl2 = l2 >= 0L ? bl : false;
            Preconditions.checkArgument(bl2, "length (%s) may not be negative", l2);
            l = Math.min(l, (long)this.length);
            l2 = Math.min(l2, (long)this.length - l);
            int n = this.offset;
            int n2 = (int)l;
            return new ByteArrayByteSource(this.bytes, n + n2, (int)l2);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ByteSource.wrap(");
            stringBuilder.append(Ascii.truncate(BaseEncoding.base16().encode(this.bytes, this.offset, this.length), 30, "..."));
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class ConcatenatedByteSource
    extends ByteSource {
        final Iterable<? extends ByteSource> sources;

        ConcatenatedByteSource(Iterable<? extends ByteSource> iterable) {
            this.sources = Preconditions.checkNotNull(iterable);
        }

        @Override
        public boolean isEmpty() throws IOException {
            Iterator<? extends ByteSource> iterator2 = this.sources.iterator();
            do {
                if (!iterator2.hasNext()) return true;
            } while (iterator2.next().isEmpty());
            return false;
        }

        @Override
        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.sources.iterator());
        }

        @Override
        public long size() throws IOException {
            long l;
            Iterator<? extends ByteSource> iterator2 = this.sources.iterator();
            long l2 = 0L;
            do {
                if (!iterator2.hasNext()) return l2;
                l2 = l = l2 + iterator2.next().size();
            } while (l >= 0L);
            return Long.MAX_VALUE;
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            long l;
            Object object = this.sources;
            if (!(object instanceof Collection)) {
                return Optional.absent();
            }
            Iterator<? extends ByteSource> iterator2 = object.iterator();
            long l2 = 0L;
            do {
                if (!iterator2.hasNext()) return Optional.of(l2);
                object = iterator2.next().sizeIfKnown();
                if (!((Optional)object).isPresent()) {
                    return Optional.absent();
                }
                l2 = l = l2 + (Long)((Optional)object).get();
            } while (l >= 0L);
            return Optional.of(Long.MAX_VALUE);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ByteSource.concat(");
            stringBuilder.append(this.sources);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class EmptyByteSource
    extends ByteArrayByteSource {
        static final EmptyByteSource INSTANCE = new EmptyByteSource();

        EmptyByteSource() {
            super(new byte[0]);
        }

        @Override
        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        @Override
        public byte[] read() {
            return this.bytes;
        }

        @Override
        public String toString() {
            return "ByteSource.empty()";
        }
    }

    private final class SlicedByteSource
    extends ByteSource {
        final long length;
        final long offset;

        SlicedByteSource(long l, long l2) {
            boolean bl = true;
            boolean bl2 = l >= 0L;
            Preconditions.checkArgument(bl2, "offset (%s) may not be negative", l);
            bl2 = l2 >= 0L ? bl : false;
            Preconditions.checkArgument(bl2, "length (%s) may not be negative", l2);
            this.offset = l;
            this.length = l2;
        }

        private InputStream sliceStream(InputStream inputStream2) throws IOException {
            long l = this.offset;
            if (l <= 0L) return ByteStreams.limit(inputStream2, this.length);
            try {
                l = ByteStreams.skipUpTo(inputStream2, l);
                if (l >= this.offset) return ByteStreams.limit(inputStream2, this.length);
            }
            catch (Throwable throwable) {
                Closer closer = Closer.create();
                closer.register(inputStream2);
                try {
                    throw closer.rethrow(throwable);
                }
                catch (Throwable throwable2) {
                    closer.close();
                    throw throwable2;
                }
            }
            inputStream2.close();
            return new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public boolean isEmpty() throws IOException {
            if (this.length == 0L) return true;
            if (super.isEmpty()) return true;
            return false;
        }

        @Override
        public InputStream openBufferedStream() throws IOException {
            return this.sliceStream(ByteSource.this.openBufferedStream());
        }

        @Override
        public InputStream openStream() throws IOException {
            return this.sliceStream(ByteSource.this.openStream());
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            Optional<Long> optional = ByteSource.this.sizeIfKnown();
            if (!optional.isPresent()) return Optional.absent();
            long l = optional.get();
            long l2 = Math.min(this.offset, l);
            return Optional.of(Math.min(this.length, l - l2));
        }

        @Override
        public ByteSource slice(long l, long l2) {
            boolean bl = true;
            boolean bl2 = l >= 0L;
            Preconditions.checkArgument(bl2, "offset (%s) may not be negative", l);
            bl2 = l2 >= 0L ? bl : false;
            Preconditions.checkArgument(bl2, "length (%s) may not be negative", l2);
            long l3 = this.length;
            return ByteSource.this.slice(this.offset + l, Math.min(l2, l3 - l));
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ByteSource.this.toString());
            stringBuilder.append(".slice(");
            stringBuilder.append(this.offset);
            stringBuilder.append(", ");
            stringBuilder.append(this.length);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

