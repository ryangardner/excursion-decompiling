/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.cache2;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.cache2.FileOperator;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u0000 :2\u00020\u0001:\u0002:;B3\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ\u000e\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\tJ\b\u00105\u001a\u0004\u0018\u00010\u0005J \u00106\u001a\u0002032\u0006\u00107\u001a\u00020\t2\u0006\u00104\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u0007H\u0002J\u0010\u00109\u001a\u0002032\u0006\u00104\u001a\u00020\u0007H\u0002R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0015R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0011\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010-X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101\u00a8\u0006<"}, d2={"Lokhttp3/internal/cache2/Relay;", "", "file", "Ljava/io/RandomAccessFile;", "upstream", "Lokio/Source;", "upstreamPos", "", "metadata", "Lokio/ByteString;", "bufferMaxSize", "(Ljava/io/RandomAccessFile;Lokio/Source;JLokio/ByteString;J)V", "buffer", "Lokio/Buffer;", "getBuffer", "()Lokio/Buffer;", "getBufferMaxSize", "()J", "complete", "", "getComplete", "()Z", "setComplete", "(Z)V", "getFile", "()Ljava/io/RandomAccessFile;", "setFile", "(Ljava/io/RandomAccessFile;)V", "isClosed", "sourceCount", "", "getSourceCount", "()I", "setSourceCount", "(I)V", "getUpstream", "()Lokio/Source;", "setUpstream", "(Lokio/Source;)V", "upstreamBuffer", "getUpstreamBuffer", "getUpstreamPos", "setUpstreamPos", "(J)V", "upstreamReader", "Ljava/lang/Thread;", "getUpstreamReader", "()Ljava/lang/Thread;", "setUpstreamReader", "(Ljava/lang/Thread;)V", "commit", "", "upstreamSize", "newSource", "writeHeader", "prefix", "metadataSize", "writeMetadata", "Companion", "RelaySource", "okhttp"}, k=1, mv={1, 1, 16})
public final class Relay {
    public static final Companion Companion = new Companion(null);
    private static final long FILE_HEADER_SIZE = 32L;
    public static final ByteString PREFIX_CLEAN = ByteString.Companion.encodeUtf8("OkHttp cache v1\n");
    public static final ByteString PREFIX_DIRTY = ByteString.Companion.encodeUtf8("OkHttp DIRTY :(\n");
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    private final Buffer buffer;
    private final long bufferMaxSize;
    private boolean complete;
    private RandomAccessFile file;
    private final ByteString metadata;
    private int sourceCount;
    private Source upstream;
    private final Buffer upstreamBuffer;
    private long upstreamPos;
    private Thread upstreamReader;

    private Relay(RandomAccessFile randomAccessFile, Source source2, long l, ByteString byteString, long l2) {
        this.file = randomAccessFile;
        this.upstream = source2;
        this.upstreamPos = l;
        this.metadata = byteString;
        this.bufferMaxSize = l2;
        this.upstreamBuffer = new Buffer();
        boolean bl = this.upstream == null;
        this.complete = bl;
        this.buffer = new Buffer();
    }

    public /* synthetic */ Relay(RandomAccessFile randomAccessFile, Source source2, long l, ByteString byteString, long l2, DefaultConstructorMarker defaultConstructorMarker) {
        this(randomAccessFile, source2, l, byteString, l2);
    }

    private final void writeHeader(ByteString object, long l, long l2) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write((ByteString)object);
        buffer.writeLong(l);
        buffer.writeLong(l2);
        boolean bl = buffer.size() == 32L;
        if (!bl) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
        object = this.file;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        object = ((RandomAccessFile)object).getChannel();
        Intrinsics.checkExpressionValueIsNotNull(object, "file!!.channel");
        new FileOperator((FileChannel)object).write(0L, buffer, 32L);
    }

    private final void writeMetadata(long l) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(this.metadata);
        Closeable closeable = this.file;
        if (closeable == null) {
            Intrinsics.throwNpe();
        }
        closeable = closeable.getChannel();
        Intrinsics.checkExpressionValueIsNotNull(closeable, "file!!.channel");
        new FileOperator((FileChannel)closeable).write(32L + l, buffer, this.metadata.size());
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void commit(long l) throws IOException {
        this.writeMetadata(l);
        Object object = this.file;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        ((RandomAccessFile)object).getChannel().force(false);
        this.writeHeader(PREFIX_CLEAN, l, this.metadata.size());
        object = this.file;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        ((RandomAccessFile)object).getChannel().force(false);
        synchronized (this) {
            this.complete = true;
            object = Unit.INSTANCE;
        }
        object = this.upstream;
        if (object != null) {
            Util.closeQuietly((Closeable)object);
        }
        this.upstream = null;
    }

    public final Buffer getBuffer() {
        return this.buffer;
    }

    public final long getBufferMaxSize() {
        return this.bufferMaxSize;
    }

    public final boolean getComplete() {
        return this.complete;
    }

    public final RandomAccessFile getFile() {
        return this.file;
    }

    public final int getSourceCount() {
        return this.sourceCount;
    }

    public final Source getUpstream() {
        return this.upstream;
    }

    public final Buffer getUpstreamBuffer() {
        return this.upstreamBuffer;
    }

    public final long getUpstreamPos() {
        return this.upstreamPos;
    }

    public final Thread getUpstreamReader() {
        return this.upstreamReader;
    }

    public final boolean isClosed() {
        if (this.file != null) return false;
        return true;
    }

    public final ByteString metadata() {
        return this.metadata;
    }

    public final Source newSource() {
        synchronized (this) {
            block4 : {
                RandomAccessFile randomAccessFile = this.file;
                if (randomAccessFile != null) break block4;
                return null;
            }
            ++this.sourceCount;
            return new RelaySource();
        }
    }

    public final void setComplete(boolean bl) {
        this.complete = bl;
    }

    public final void setFile(RandomAccessFile randomAccessFile) {
        this.file = randomAccessFile;
    }

    public final void setSourceCount(int n) {
        this.sourceCount = n;
    }

    public final void setUpstream(Source source2) {
        this.upstream = source2;
    }

    public final void setUpstreamPos(long l) {
        this.upstreamPos = l;
    }

    public final void setUpstreamReader(Thread thread2) {
        this.upstreamReader = thread2;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lokhttp3/internal/cache2/Relay$Companion;", "", "()V", "FILE_HEADER_SIZE", "", "PREFIX_CLEAN", "Lokio/ByteString;", "PREFIX_DIRTY", "SOURCE_FILE", "", "SOURCE_UPSTREAM", "edit", "Lokhttp3/internal/cache2/Relay;", "file", "Ljava/io/File;", "upstream", "Lokio/Source;", "metadata", "bufferMaxSize", "read", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Relay edit(File object, Source object2, ByteString byteString, long l) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "file");
            Intrinsics.checkParameterIsNotNull(object2, "upstream");
            Intrinsics.checkParameterIsNotNull(byteString, "metadata");
            object = new RandomAccessFile((File)object, "rw");
            object2 = new Relay((RandomAccessFile)object, (Source)object2, 0L, byteString, l, null);
            ((RandomAccessFile)object).setLength(0L);
            ((Relay)object2).writeHeader(PREFIX_DIRTY, -1L, -1L);
            return object2;
        }

        public final Relay read(File object) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "file");
            object = new RandomAccessFile((File)object, "rw");
            Object object2 = ((RandomAccessFile)object).getChannel();
            Intrinsics.checkExpressionValueIsNotNull(object2, "randomAccessFile.channel");
            object2 = new FileOperator((FileChannel)object2);
            Buffer buffer = new Buffer();
            ((FileOperator)object2).read(0L, buffer, 32L);
            if (Intrinsics.areEqual(buffer.readByteString(PREFIX_CLEAN.size()), PREFIX_CLEAN) ^ true) throw (Throwable)new IOException("unreadable cache file");
            long l = buffer.readLong();
            long l2 = buffer.readLong();
            buffer = new Buffer();
            ((FileOperator)object2).read(l + 32L, buffer, l2);
            return new Relay((RandomAccessFile)object, null, l, buffer.readByteString(), 0L, null);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/cache2/Relay$RelaySource;", "Lokio/Source;", "(Lokhttp3/internal/cache2/Relay;)V", "fileOperator", "Lokhttp3/internal/cache2/FileOperator;", "sourcePos", "", "timeout", "Lokio/Timeout;", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"}, k=1, mv={1, 1, 16})
    public final class RelaySource
    implements Source {
        private FileOperator fileOperator;
        private long sourcePos;
        private final Timeout timeout = new Timeout();

        public RelaySource() {
            Relay.this = ((Relay)Relay.this).getFile();
            if (Relay.this == null) {
                Intrinsics.throwNpe();
            }
            Relay.this = ((RandomAccessFile)Relay.this).getChannel();
            Intrinsics.checkExpressionValueIsNotNull(Relay.this, "file!!.channel");
            this.fileOperator = new FileOperator((FileChannel)Relay.this);
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void close() throws IOException {
            if (this.fileOperator == null) {
                return;
            }
            this.fileOperator = null;
            RandomAccessFile randomAccessFile = null;
            Relay relay = Relay.this;
            synchronized (relay) {
                Object object = Relay.this;
                ((Relay)object).setSourceCount(((Relay)object).getSourceCount() - 1);
                if (Relay.this.getSourceCount() == 0) {
                    randomAccessFile = Relay.this.getFile();
                    Relay.this.setFile(null);
                }
                object = Unit.INSTANCE;
            }
            if (randomAccessFile == null) return;
            Util.closeQuietly(randomAccessFile);
        }

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        @Override
        public long read(Buffer object, long l) throws IOException {
            Object object2;
            long l2;
            long l3;
            block28 : {
                block27 : {
                    int n;
                    block26 : {
                        Intrinsics.checkParameterIsNotNull(object, "sink");
                        object2 = this.fileOperator;
                        int n2 = 1;
                        n = object2 != null ? 1 : 0;
                        if (n == 0) throw (Throwable)new IllegalStateException("Check failed.".toString());
                        object2 = Relay.this;
                        // MONITORENTER : object2
                        do {
                            if (this.sourcePos != (l3 = Relay.this.getUpstreamPos())) {
                                l3 = Relay.this.getUpstreamPos() - Relay.this.getBuffer().size();
                                if (this.sourcePos >= l3) {
                                    l2 = Math.min(l2, Relay.this.getUpstreamPos() - this.sourcePos);
                                    Relay.this.getBuffer().copyTo((Buffer)object, this.sourcePos - l3, l2);
                                    this.sourcePos += l2;
                                    // MONITOREXIT : object2
                                    return l2;
                                }
                                n = 2;
                                break block26;
                            }
                            boolean bl = Relay.this.getComplete();
                            if (bl) {
                                // MONITOREXIT : object2
                                return -1L;
                            }
                            if (Relay.this.getUpstreamReader() == null) break;
                            this.timeout.waitUntilNotified(Relay.this);
                        } while (true);
                        Relay.this.setUpstreamReader(Thread.currentThread());
                        n = n2;
                    }
                    // MONITOREXIT : object2
                    if (n == 2) {
                        l2 = Math.min(l2, Relay.this.getUpstreamPos() - this.sourcePos);
                        object2 = this.fileOperator;
                        if (object2 == null) {
                            Intrinsics.throwNpe();
                        }
                        ((FileOperator)object2).read(this.sourcePos + 32L, (Buffer)object, l2);
                        this.sourcePos += l2;
                        return l2;
                    }
                    try {
                        object2 = Relay.this.getUpstream();
                        if (object2 == null) {
                            Intrinsics.throwNpe();
                        }
                        if ((l3 = object2.read(Relay.this.getUpstreamBuffer(), Relay.this.getBufferMaxSize())) != -1L) break block27;
                        Relay.this.commit(Relay.this.getUpstreamPos());
                        object = Relay.this;
                    }
                    catch (Throwable throwable) {
                        object = Relay.this;
                        // MONITORENTER : object
                        Relay.this.setUpstreamReader(null);
                        Object object3 = Relay.this;
                        if (object3 == null) {
                            TypeCastException typeCastException = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                            throw typeCastException;
                        }
                        ((Object)object3).notifyAll();
                        object3 = Unit.INSTANCE;
                        // MONITOREXIT : object
                        throw throwable;
                    }
                    Relay.this.setUpstreamReader(null);
                    object2 = Relay.this;
                    if (object2 != null) {
                        object2.notifyAll();
                        object2 = Unit.INSTANCE;
                        // MONITOREXIT : object
                        return -1L;
                    }
                    object2 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    throw object2;
                }
                l2 = Math.min(l3, l2);
                Relay.this.getUpstreamBuffer().copyTo((Buffer)object, 0L, l2);
                this.sourcePos += l2;
                object = this.fileOperator;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                ((FileOperator)object).write(Relay.this.getUpstreamPos() + 32L, (Buffer)Relay.this.getUpstreamBuffer().clone(), l3);
                object = Relay.this;
                // MONITORENTER : object
                Relay.this.getBuffer().write(Relay.this.getUpstreamBuffer(), l3);
                if (Relay.this.getBuffer().size() <= Relay.this.getBufferMaxSize()) break block28;
                Relay.this.getBuffer().skip(Relay.this.getBuffer().size() - Relay.this.getBufferMaxSize());
            }
            object2 = Relay.this;
            ((Relay)object2).setUpstreamPos(((Relay)object2).getUpstreamPos() + l3);
            object2 = Unit.INSTANCE;
            // MONITOREXIT : object
            object = Relay.this;
            // MONITORENTER : object
            Relay.this.setUpstreamReader(null);
            object2 = Relay.this;
            if (object2 != null) {
                object2.notifyAll();
                object2 = Unit.INSTANCE;
                // MONITOREXIT : object
                return l2;
            }
            object2 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
            throw object2;
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }
    }

}

