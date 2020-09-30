/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.Cache$urls
 */
package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010)\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 C2\u00020\u00012\u00020\u0002:\u0004BCDEB\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0018\u00010\"R\u00020\fH\u0002J\b\u0010#\u001a\u00020 H\u0016J\u0006\u0010$\u001a\u00020 J\r\u0010\u0003\u001a\u00020\u0004H\u0007\u00a2\u0006\u0002\b%J\u0006\u0010&\u001a\u00020 J\b\u0010'\u001a\u00020 H\u0016J\u0017\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020+H\u0000\u00a2\u0006\u0002\b,J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010-\u001a\u00020 J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0015\u001a\u00020\u0011J\u0017\u0010.\u001a\u0004\u0018\u00010/2\u0006\u00100\u001a\u00020)H\u0000\u00a2\u0006\u0002\b1J\u0015\u00102\u001a\u00020 2\u0006\u0010*\u001a\u00020+H\u0000\u00a2\u0006\u0002\b3J\u0006\u0010\u0016\u001a\u00020\u0011J\u0006\u00104\u001a\u00020\u0006J\r\u00105\u001a\u00020 H\u0000\u00a2\u0006\u0002\b6J\u0015\u00107\u001a\u00020 2\u0006\u00108\u001a\u000209H\u0000\u00a2\u0006\u0002\b:J\u001d\u0010;\u001a\u00020 2\u0006\u0010<\u001a\u00020)2\u0006\u0010=\u001a\u00020)H\u0000\u00a2\u0006\u0002\b>J\f\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@J\u0006\u0010\u0017\u001a\u00020\u0011J\u0006\u0010\u001c\u001a\u00020\u0011R\u0014\u0010\u000b\u001a\u00020\fX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u00048G\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0011X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0011X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001b\u00a8\u0006F"}, d2={"Lokhttp3/Cache;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "directory", "Ljava/io/File;", "maxSize", "", "(Ljava/io/File;J)V", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "(Ljava/io/File;JLokhttp3/internal/io/FileSystem;)V", "cache", "Lokhttp3/internal/cache/DiskLruCache;", "getCache$okhttp", "()Lokhttp3/internal/cache/DiskLruCache;", "()Ljava/io/File;", "hitCount", "", "isClosed", "", "()Z", "networkCount", "requestCount", "writeAbortCount", "getWriteAbortCount$okhttp", "()I", "setWriteAbortCount$okhttp", "(I)V", "writeSuccessCount", "getWriteSuccessCount$okhttp", "setWriteSuccessCount$okhttp", "abortQuietly", "", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "close", "delete", "-deprecated_directory", "evictAll", "flush", "get", "Lokhttp3/Response;", "request", "Lokhttp3/Request;", "get$okhttp", "initialize", "put", "Lokhttp3/internal/cache/CacheRequest;", "response", "put$okhttp", "remove", "remove$okhttp", "size", "trackConditionalCacheHit", "trackConditionalCacheHit$okhttp", "trackResponse", "cacheStrategy", "Lokhttp3/internal/cache/CacheStrategy;", "trackResponse$okhttp", "update", "cached", "network", "update$okhttp", "urls", "", "", "CacheResponseBody", "Companion", "Entry", "RealCacheRequest", "okhttp"}, k=1, mv={1, 1, 16})
public final class Cache
implements Closeable,
Flushable {
    public static final Companion Companion = new Companion(null);
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    public Cache(File file, long l) {
        Intrinsics.checkParameterIsNotNull(file, "directory");
        this(file, l, FileSystem.SYSTEM);
    }

    public Cache(File file, long l, FileSystem fileSystem) {
        Intrinsics.checkParameterIsNotNull(file, "directory");
        Intrinsics.checkParameterIsNotNull(fileSystem, "fileSystem");
        this.cache = new DiskLruCache(fileSystem, file, 201105, 2, l, TaskRunner.INSTANCE);
    }

    private final void abortQuietly(DiskLruCache.Editor editor) {
        if (editor == null) return;
        try {
            editor.abort();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    @JvmStatic
    public static final String key(HttpUrl httpUrl) {
        return Companion.key(httpUrl);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="directory", imports={}))
    public final File -deprecated_directory() {
        return this.cache.getDirectory();
    }

    @Override
    public void close() throws IOException {
        this.cache.close();
    }

    public final void delete() throws IOException {
        this.cache.delete();
    }

    public final File directory() {
        return this.cache.getDirectory();
    }

    public final void evictAll() throws IOException {
        this.cache.evictAll();
    }

    @Override
    public void flush() throws IOException {
        this.cache.flush();
    }

    public final Response get$okhttp(Request object) {
        Closeable closeable;
        Intrinsics.checkParameterIsNotNull(object, "request");
        Object object2 = Companion.key(((Request)object).url());
        try {
            closeable = this.cache.get((String)object2);
            if (closeable == null) return null;
        }
        catch (IOException iOException) {
            return null;
        }
        try {
            object2 = new Entry(((DiskLruCache.Snapshot)closeable).getSource(0));
            closeable = ((Entry)object2).response((DiskLruCache.Snapshot)closeable);
            if (((Entry)object2).matches((Request)object, (Response)closeable)) return closeable;
            object = ((Response)closeable).body();
            if (object == null) return null;
        }
        catch (IOException iOException) {
            Util.closeQuietly(closeable);
        }
        Util.closeQuietly((Closeable)object);
        return null;
    }

    public final DiskLruCache getCache$okhttp() {
        return this.cache;
    }

    public final int getWriteAbortCount$okhttp() {
        return this.writeAbortCount;
    }

    public final int getWriteSuccessCount$okhttp() {
        return this.writeSuccessCount;
    }

    public final int hitCount() {
        synchronized (this) {
            return this.hitCount;
        }
    }

    public final void initialize() throws IOException {
        this.cache.initialize();
    }

    public final boolean isClosed() {
        return this.cache.isClosed();
    }

    public final long maxSize() {
        return this.cache.getMaxSize();
    }

    public final int networkCount() {
        synchronized (this) {
            return this.networkCount;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public final CacheRequest put$okhttp(Response object) {
        Intrinsics.checkParameterIsNotNull(object, "response");
        Object object2 = ((Response)object).request().method();
        if (HttpMethod.INSTANCE.invalidatesCache(((Response)object).request().method())) {
            this.remove$okhttp(((Response)object).request());
            return null;
        }
        if (Intrinsics.areEqual(object2, "GET") ^ true) {
            return null;
        }
        if (Companion.hasVaryAll((Response)object)) {
            return null;
        }
        Object object3 = new Entry((Response)object);
        object2 = null;
        try {
            object = DiskLruCache.edit$default(this.cache, Companion.key(((Response)object).request().url()), 0L, 2, null);
            if (object == null) return null;
            object2 = object;
            ((Entry)object3).writeTo((DiskLruCache.Editor)object);
            object2 = object;
            object2 = object;
            object3 = new RealCacheRequest((DiskLruCache.Editor)object);
            object2 = object;
            return (CacheRequest)object3;
        }
        catch (IOException iOException) {
            this.abortQuietly((DiskLruCache.Editor)object2);
            return null;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public final void remove$okhttp(Request request) throws IOException {
        Intrinsics.checkParameterIsNotNull(request, "request");
        this.cache.remove(Companion.key(request.url()));
    }

    public final int requestCount() {
        synchronized (this) {
            return this.requestCount;
        }
    }

    public final void setWriteAbortCount$okhttp(int n) {
        this.writeAbortCount = n;
    }

    public final void setWriteSuccessCount$okhttp(int n) {
        this.writeSuccessCount = n;
    }

    public final long size() throws IOException {
        return this.cache.size();
    }

    public final void trackConditionalCacheHit$okhttp() {
        synchronized (this) {
            ++this.hitCount;
            return;
        }
    }

    public final void trackResponse$okhttp(CacheStrategy cacheStrategy) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(cacheStrategy, "cacheStrategy");
            ++this.requestCount;
            if (cacheStrategy.getNetworkRequest() != null) {
                ++this.networkCount;
            } else {
                if (cacheStrategy.getCacheResponse() == null) return;
                ++this.hitCount;
            }
            return;
        }
    }

    public final void update$okhttp(Response object, Response object2) {
        Intrinsics.checkParameterIsNotNull(object, "cached");
        Intrinsics.checkParameterIsNotNull(object2, "network");
        Entry entry = new Entry((Response)object2);
        object = ((Response)object).body();
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type okhttp3.Cache.CacheResponseBody");
        object2 = ((CacheResponseBody)object).getSnapshot$okhttp();
        object = null;
        try {
            object2 = ((DiskLruCache.Snapshot)object2).edit();
            if (object2 == null) return;
            object = object2;
            entry.writeTo((DiskLruCache.Editor)object2);
            object = object2;
            ((DiskLruCache.Editor)object2).commit();
            return;
        }
        catch (IOException iOException) {
            this.abortQuietly((DiskLruCache.Editor)object);
        }
    }

    public final Iterator<String> urls() throws IOException {
        return new Iterator<String>(this){
            private boolean canRemove;
            private final Iterator<DiskLruCache.Snapshot> delegate;
            private String nextUrl;
            final /* synthetic */ Cache this$0;
            {
                this.this$0 = cache;
                this.delegate = cache.getCache$okhttp().snapshots();
            }

            /*
             * Exception decompiling
             */
            public boolean hasNext() {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.dump(Method.java:475)
                // org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperAnonymousInner.dumpWithArgs(ClassFileDumperAnonymousInner.java:87)
                // org.benf.cfr.reader.bytecode.analysis.parse.expression.ConstructorInvokationAnonymousInner.dumpInner(ConstructorInvokationAnonymousInner.java:73)
                // org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dumpWithOuterPrecedence(AbstractExpression.java:113)
                // org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dump(AbstractExpression.java:74)
                // org.benf.cfr.reader.util.output.StreamDumper.dump(StreamDumper.java:146)
                // org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredReturn.dump(StructuredReturn.java:52)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:204)
                // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.dump(Block.java:559)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:204)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.dump(AttributeCode.java:141)
                // org.benf.cfr.reader.util.output.StreamDumper.dump(StreamDumper.java:146)
                // org.benf.cfr.reader.entities.Method.dump(Method.java:494)
                // org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperNormal.dump(ClassFileDumperNormal.java:87)
                // org.benf.cfr.reader.entities.ClassFile.dump(ClassFile.java:1016)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:231)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
                // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
                throw new IllegalStateException("Decompilation failed");
            }

            public String next() {
                if (!this.hasNext()) throw (Throwable)new java.util.NoSuchElementException();
                String string2 = this.nextUrl;
                if (string2 == null) {
                    Intrinsics.throwNpe();
                }
                this.nextUrl = null;
                this.canRemove = true;
                return string2;
            }

            public void remove() {
                if (!this.canRemove) throw (Throwable)new java.lang.IllegalStateException("remove() before next()".toString());
                this.delegate.remove();
            }
        };
    }

    public final int writeAbortCount() {
        synchronized (this) {
            return this.writeAbortCount;
        }
    }

    public final int writeSuccessCount() {
        synchronized (this) {
            return this.writeSuccessCount;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B'\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0007\u001a\u00020\rH\u0016J\n\u0010\u0005\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0010"}, d2={"Lokhttp3/Cache$CacheResponseBody;", "Lokhttp3/ResponseBody;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "contentType", "", "contentLength", "(Lokhttp3/internal/cache/DiskLruCache$Snapshot;Ljava/lang/String;Ljava/lang/String;)V", "bodySource", "Lokio/BufferedSource;", "getSnapshot$okhttp", "()Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "", "Lokhttp3/MediaType;", "source", "okhttp"}, k=1, mv={1, 1, 16})
    private static final class CacheResponseBody
    extends ResponseBody {
        private final BufferedSource bodySource;
        private final String contentLength;
        private final String contentType;
        private final DiskLruCache.Snapshot snapshot;

        public CacheResponseBody(DiskLruCache.Snapshot closeable, String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(closeable, "snapshot");
            this.snapshot = closeable;
            this.contentType = string2;
            this.contentLength = string3;
            closeable = closeable.getSource(1);
            this.bodySource = Okio.buffer(new ForwardingSource((Source)closeable, (Source)closeable){
                final /* synthetic */ Source $source;
                {
                    this.$source = source2;
                    super(source3);
                }

                @Override
                public void close() throws IOException {
                    this.getSnapshot$okhttp().close();
                    super.close();
                }
            });
        }

        @Override
        public long contentLength() {
            String string2 = this.contentLength;
            long l = -1L;
            if (string2 == null) return l;
            return Util.toLongOrDefault(string2, -1L);
        }

        @Override
        public MediaType contentType() {
            String string2 = this.contentType;
            if (string2 == null) return null;
            return MediaType.Companion.parse(string2);
        }

        public final DiskLruCache.Snapshot getSnapshot$okhttp() {
            return this.snapshot;
        }

        @Override
        public BufferedSource source() {
            return this.bodySource;
        }

    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0015\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0000\u00a2\u0006\u0002\b\u000fJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\n\u0010\u001b\u001a\u00020\u0015*\u00020\u0017J\u0012\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u001d*\u00020\u0011H\u0002J\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lokhttp3/Cache$Companion;", "", "()V", "ENTRY_BODY", "", "ENTRY_COUNT", "ENTRY_METADATA", "VERSION", "key", "", "url", "Lokhttp3/HttpUrl;", "readInt", "source", "Lokio/BufferedSource;", "readInt$okhttp", "varyHeaders", "Lokhttp3/Headers;", "requestHeaders", "responseHeaders", "varyMatches", "", "cachedResponse", "Lokhttp3/Response;", "cachedRequest", "newRequest", "Lokhttp3/Request;", "hasVaryAll", "varyFields", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Set<String> varyFields(Headers headers) {
            Object object = null;
            int n = headers.size();
            block0 : for (int i = 0; i < n; ++i) {
                if (!StringsKt.equals("Vary", headers.name(i), true)) continue;
                Object object2 = headers.value(i);
                Set set = object;
                if (object == null) {
                    set = new TreeSet<String>(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
                }
                object2 = StringsKt.split$default((CharSequence)object2, new char[]{','}, false, 0, 6, null).iterator();
                do {
                    object = set;
                    if (!object2.hasNext()) continue block0;
                    object = (String)object2.next();
                    if (object == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                    set.add(((Object)StringsKt.trim((CharSequence)object)).toString());
                } while (true);
            }
            if (object == null) return SetsKt.emptySet();
            return object;
        }

        private final Headers varyHeaders(Headers headers, Headers object) {
            Set<String> set = this.varyFields((Headers)object);
            if (set.isEmpty()) {
                return Util.EMPTY_HEADERS;
            }
            Headers.Builder builder = new Headers.Builder();
            int n = 0;
            int n2 = headers.size();
            while (n < n2) {
                object = headers.name(n);
                if (set.contains(object)) {
                    builder.add((String)object, headers.value(n));
                }
                ++n;
            }
            return builder.build();
        }

        public final boolean hasVaryAll(Response response) {
            Intrinsics.checkParameterIsNotNull(response, "$this$hasVaryAll");
            return this.varyFields(response.headers()).contains("*");
        }

        @JvmStatic
        public final String key(HttpUrl httpUrl) {
            Intrinsics.checkParameterIsNotNull(httpUrl, "url");
            return ByteString.Companion.encodeUtf8(httpUrl.toString()).md5().hex();
        }

        public final int readInt$okhttp(BufferedSource object) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "source");
            try {
                boolean bl;
                long l = object.readDecimalLong();
                String string2 = object.readUtf8LineStrict();
                if (l >= 0L && l <= (long)Integer.MAX_VALUE && !(bl = ((CharSequence)string2).length() > 0)) {
                    return (int)l;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("expected an int but was \"");
                stringBuilder.append(l);
                stringBuilder.append(string2);
                stringBuilder.append('\"');
                object = new IOException(stringBuilder.toString());
                throw (Throwable)object;
            }
            catch (NumberFormatException numberFormatException) {
                throw (Throwable)new IOException(numberFormatException.getMessage());
            }
        }

        public final Headers varyHeaders(Response object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$varyHeaders");
            Object object2 = ((Response)object).networkResponse();
            if (object2 == null) {
                Intrinsics.throwNpe();
            }
            object2 = ((Response)object2).request().headers();
            object = ((Response)object).headers();
            return this.varyHeaders((Headers)object2, (Headers)object);
        }

        public final boolean varyMatches(Response object, Headers headers, Request request) {
            Intrinsics.checkParameterIsNotNull(object, "cachedResponse");
            Intrinsics.checkParameterIsNotNull(headers, "cachedRequest");
            Intrinsics.checkParameterIsNotNull(request, "newRequest");
            object = this.varyFields(((Response)object).headers());
            boolean bl = object instanceof Collection;
            boolean bl2 = true;
            if (bl && ((Collection)object).isEmpty()) {
                return bl2;
            }
            Iterator iterator2 = object.iterator();
            do {
                bl = bl2;
                if (!iterator2.hasNext()) return bl;
            } while (!(Intrinsics.areEqual(headers.values((String)(object = (String)iterator2.next())), request.headers((String)object)) ^ true));
            return false;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u000f\b\u0010\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010!\u001a\u00020\"H\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\n\u0010#\u001a\u00060$R\u00020%J\u001e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002J\u0012\u0010+\u001a\u00020'2\n\u0010,\u001a\u00060-R\u00020%R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2={"Lokhttp3/Cache$Entry;", "", "rawSource", "Lokio/Source;", "(Lokio/Source;)V", "response", "Lokhttp3/Response;", "(Lokhttp3/Response;)V", "code", "", "handshake", "Lokhttp3/Handshake;", "isHttps", "", "()Z", "message", "", "protocol", "Lokhttp3/Protocol;", "receivedResponseMillis", "", "requestMethod", "responseHeaders", "Lokhttp3/Headers;", "sentRequestMillis", "url", "varyHeaders", "matches", "request", "Lokhttp3/Request;", "readCertificateList", "", "Ljava/security/cert/Certificate;", "source", "Lokio/BufferedSource;", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "writeCertList", "", "sink", "Lokio/BufferedSink;", "certificates", "writeTo", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
    private static final class Entry {
        public static final Companion Companion = new Companion(null);
        private static final String RECEIVED_MILLIS;
        private static final String SENT_MILLIS;
        private final int code;
        private final Handshake handshake;
        private final String message;
        private final Protocol protocol;
        private final long receivedResponseMillis;
        private final String requestMethod;
        private final Headers responseHeaders;
        private final long sentRequestMillis;
        private final String url;
        private final Headers varyHeaders;

        static {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Platform.Companion.get().getPrefix());
            stringBuilder.append("-Sent-Millis");
            SENT_MILLIS = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(Platform.Companion.get().getPrefix());
            stringBuilder.append("-Received-Millis");
            RECEIVED_MILLIS = stringBuilder.toString();
        }

        public Entry(Response response) {
            Intrinsics.checkParameterIsNotNull(response, "response");
            this.url = response.request().url().toString();
            this.varyHeaders = Companion.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
            this.sentRequestMillis = response.sentRequestAtMillis();
            this.receivedResponseMillis = response.receivedResponseAtMillis();
        }

        public Entry(Source source2) throws IOException {
            int n;
            Intrinsics.checkParameterIsNotNull(source2, "rawSource");
            Object object = Okio.buffer(source2);
            this.url = object.readUtf8LineStrict();
            this.requestMethod = object.readUtf8LineStrict();
            List<Certificate> list = new List<Certificate>();
            int n2 = Companion.readInt$okhttp((BufferedSource)object);
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                ((Headers.Builder)((Object)list)).addLenient$okhttp(object.readUtf8LineStrict());
            }
            this.varyHeaders = ((Headers.Builder)((Object)list)).build();
            list = StatusLine.Companion.parse(object.readUtf8LineStrict());
            this.protocol = ((StatusLine)list).protocol;
            this.code = ((StatusLine)list).code;
            this.message = ((StatusLine)list).message;
            list = new List<Certificate>();
            n2 = Companion.readInt$okhttp((BufferedSource)object);
            for (n = 0; n < n2; ++n) {
                ((Headers.Builder)((Object)list)).addLenient$okhttp(object.readUtf8LineStrict());
            }
            try {
                Object object2 = ((Headers.Builder)((Object)list)).get(SENT_MILLIS);
                Object object3 = ((Headers.Builder)((Object)list)).get(RECEIVED_MILLIS);
                ((Headers.Builder)((Object)list)).removeAll(SENT_MILLIS);
                ((Headers.Builder)((Object)list)).removeAll(RECEIVED_MILLIS);
                long l = 0L;
                long l2 = object2 != null ? Long.parseLong((String)object2) : 0L;
                this.sentRequestMillis = l2;
                l2 = l;
                if (object3 != null) {
                    l2 = Long.parseLong((String)object3);
                }
                this.receivedResponseMillis = l2;
                this.responseHeaders = ((Headers.Builder)((Object)list)).build();
                if (this.isHttps()) {
                    list = object.readUtf8LineStrict();
                    n = n3;
                    if (((CharSequence)((Object)list)).length() > 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("expected \"\" but was \"");
                        ((StringBuilder)object).append((String)((Object)list));
                        ((StringBuilder)object).append('\"');
                        object2 = new IOException(((StringBuilder)object).toString());
                        throw (Throwable)object2;
                    }
                    list = object.readUtf8LineStrict();
                    object2 = CipherSuite.Companion.forJavaName((String)((Object)list));
                    object3 = this.readCertificateList((BufferedSource)object);
                    list = this.readCertificateList((BufferedSource)object);
                    object = !object.exhausted() ? TlsVersion.Companion.forJavaName(object.readUtf8LineStrict()) : TlsVersion.SSL_3_0;
                    this.handshake = Handshake.Companion.get((TlsVersion)((Object)object), (CipherSuite)object2, (List<? extends Certificate>)object3, list);
                    return;
                }
                this.handshake = null;
                return;
            }
            finally {
                source2.close();
            }
        }

        private final boolean isHttps() {
            return StringsKt.startsWith$default(this.url, "https://", false, 2, null);
        }

        private final List<Certificate> readCertificateList(BufferedSource object) throws IOException {
            int n = Companion.readInt$okhttp((BufferedSource)object);
            if (n == -1) {
                return CollectionsKt.emptyList();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ArrayList<Certificate> arrayList = new ArrayList<Certificate>(n);
                int n2 = 0;
                while (n2 < n) {
                    Object object2 = object.readUtf8LineStrict();
                    Buffer buffer = new Buffer();
                    if ((object2 = ByteString.Companion.decodeBase64((String)object2)) == null) {
                        Intrinsics.throwNpe();
                    }
                    buffer.write((ByteString)object2);
                    arrayList.add(certificateFactory.generateCertificate(buffer.inputStream()));
                    ++n2;
                }
                return arrayList;
            }
            catch (CertificateException certificateException) {
                throw (Throwable)new IOException(certificateException.getMessage());
            }
        }

        private final void writeCertList(BufferedSink bufferedSink, List<? extends Certificate> list) throws IOException {
            try {
                bufferedSink.writeDecimalLong(list.size()).writeByte(10);
                int n = 0;
                int n2 = list.size();
                while (n < n2) {
                    byte[] arrby = list.get(n).getEncoded();
                    ByteString.Companion companion = ByteString.Companion;
                    Intrinsics.checkExpressionValueIsNotNull(arrby, "bytes");
                    bufferedSink.writeUtf8(ByteString.Companion.of$default(companion, arrby, 0, 0, 3, null).base64()).writeByte(10);
                    ++n;
                }
                return;
            }
            catch (CertificateEncodingException certificateEncodingException) {
                throw (Throwable)new IOException(certificateEncodingException.getMessage());
            }
        }

        public final boolean matches(Request request, Response response) {
            Intrinsics.checkParameterIsNotNull(request, "request");
            Intrinsics.checkParameterIsNotNull(response, "response");
            if (!Intrinsics.areEqual(this.url, request.url().toString())) return false;
            if (!Intrinsics.areEqual(this.requestMethod, request.method())) return false;
            if (!Companion.varyMatches(response, this.varyHeaders, request)) return false;
            return true;
        }

        public final Response response(DiskLruCache.Snapshot snapshot) {
            Intrinsics.checkParameterIsNotNull(snapshot, "snapshot");
            String string2 = this.responseHeaders.get("Content-Type");
            String string3 = this.responseHeaders.get("Content-Length");
            Request request = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
            return new Response.Builder().request(request).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, string2, string3)).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
        }

        public final void writeTo(DiskLruCache.Editor object) throws IOException {
            Object object2;
            Object object3;
            Intrinsics.checkParameterIsNotNull(object, "editor");
            int n = 0;
            object = Okio.buffer(((DiskLruCache.Editor)object).newSink(0));
            Throwable throwable = null;
            try {
                int n2;
                object2 = (BufferedSink)object;
                object2.writeUtf8(this.url).writeByte(10);
                object2.writeUtf8(this.requestMethod).writeByte(10);
                object2.writeDecimalLong(this.varyHeaders.size()).writeByte(10);
                int n3 = this.varyHeaders.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    object2.writeUtf8(this.varyHeaders.name(n2)).writeUtf8(": ").writeUtf8(this.varyHeaders.value(n2)).writeByte(10);
                }
                object3 = new StatusLine(this.protocol, this.code, this.message);
                object2.writeUtf8(((StatusLine)object3).toString()).writeByte(10);
                object2.writeDecimalLong(this.responseHeaders.size() + 2).writeByte(10);
                n3 = this.responseHeaders.size();
                for (n2 = n; n2 < n3; ++n2) {
                    object2.writeUtf8(this.responseHeaders.name(n2)).writeUtf8(": ").writeUtf8(this.responseHeaders.value(n2)).writeByte(10);
                }
            }
            catch (Throwable throwable2) {
                try {
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    CloseableKt.closeFinally((Closeable)object, throwable2);
                    throw throwable3;
                }
            }
            object2.writeUtf8(SENT_MILLIS).writeUtf8(": ").writeDecimalLong(this.sentRequestMillis).writeByte(10);
            object2.writeUtf8(RECEIVED_MILLIS).writeUtf8(": ").writeDecimalLong(this.receivedResponseMillis).writeByte(10);
            if (this.isHttps()) {
                object2.writeByte(10);
                object3 = this.handshake;
                if (object3 == null) {
                    Intrinsics.throwNpe();
                }
                object2.writeUtf8(((Handshake)object3).cipherSuite().javaName()).writeByte(10);
                this.writeCertList((BufferedSink)object2, this.handshake.peerCertificates());
                this.writeCertList((BufferedSink)object2, this.handshake.localCertificates());
                object2.writeUtf8(this.handshake.tlsVersion().javaName()).writeByte(10);
            }
            object2 = Unit.INSTANCE;
            CloseableKt.closeFinally((Closeable)object, throwable);
        }

        @Metadata(bv={1, 0, 3}, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lokhttp3/Cache$Entry$Companion;", "", "()V", "RECEIVED_MILLIS", "", "SENT_MILLIS", "okhttp"}, k=1, mv={1, 1, 16})
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0013\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0012\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lokhttp3/Cache$RealCacheRequest;", "Lokhttp3/internal/cache/CacheRequest;", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "(Lokhttp3/Cache;Lokhttp3/internal/cache/DiskLruCache$Editor;)V", "body", "Lokio/Sink;", "cacheOut", "done", "", "getDone$okhttp", "()Z", "setDone$okhttp", "(Z)V", "abort", "", "okhttp"}, k=1, mv={1, 1, 16})
    private final class RealCacheRequest
    implements CacheRequest {
        private final Sink body;
        private final Sink cacheOut;
        private boolean done;
        private final DiskLruCache.Editor editor;

        public RealCacheRequest(DiskLruCache.Editor editor) {
            Intrinsics.checkParameterIsNotNull(editor, "editor");
            this.editor = editor;
            this.cacheOut = editor.newSink(1);
            this.body = new ForwardingSink(this.cacheOut){

                /*
                 * Enabled unnecessary exception pruning
                 */
                @Override
                public void close() throws IOException {
                    Cache cache = Cache.this;
                    synchronized (cache) {
                        boolean bl = this.getDone$okhttp();
                        if (bl) {
                            return;
                        }
                        this.setDone$okhttp(true);
                        Cache cache2 = Cache.this;
                        cache2.setWriteSuccessCount$okhttp(cache2.getWriteSuccessCount$okhttp() + 1);
                    }
                    super.close();
                    editor.commit();
                }
            };
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void abort() {
            Cache cache = Cache.this;
            synchronized (cache) {
                boolean bl = this.done;
                if (bl) {
                    return;
                }
                this.done = true;
                Cache cache2 = Cache.this;
                cache2.setWriteAbortCount$okhttp(cache2.getWriteAbortCount$okhttp() + 1);
            }
            Util.closeQuietly(this.cacheOut);
            try {
                this.editor.abort();
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        @Override
        public Sink body() {
            return this.body;
        }

        public final boolean getDone$okhttp() {
            return this.done;
        }

        public final void setDone$okhttp(boolean bl) {
            this.done = bl;
        }

    }

}

