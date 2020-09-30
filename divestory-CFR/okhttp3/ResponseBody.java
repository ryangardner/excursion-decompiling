/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.ResponseBody$Companion$asResponseBody
 */
package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u0000 !2\u00020\u0001:\u0002 !B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J@\u0010\u0010\u001a\u0002H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u00110\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00020\u00170\u0014H\u0082\b\u00a2\u0006\u0002\u0010\u0018J\b\u0010\u0019\u001a\u00020\u001aH&J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH&J\b\u0010\u001d\u001a\u00020\u0015H&J\u0006\u0010\u001e\u001a\u00020\u001fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2={"Lokhttp3/ResponseBody;", "Ljava/io/Closeable;", "()V", "reader", "Ljava/io/Reader;", "byteStream", "Ljava/io/InputStream;", "byteString", "Lokio/ByteString;", "bytes", "", "charStream", "charset", "Ljava/nio/charset/Charset;", "close", "", "consumeSource", "T", "", "consumer", "Lkotlin/Function1;", "Lokio/BufferedSource;", "sizeMapper", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "source", "string", "", "BomAwareReader", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public abstract class ResponseBody
implements Closeable {
    public static final Companion Companion = new Companion(null);
    private Reader reader;

    private final Charset charset() {
        Object object = this.contentType();
        if (object == null) return Charsets.UTF_8;
        if ((object = ((MediaType)object).charset(Charsets.UTF_8)) == null) return Charsets.UTF_8;
        return object;
    }

    private final <T> T consumeSource(Function1<? super BufferedSource, ? extends T> object, Function1<? super T, Integer> function1) {
        long l = this.contentLength();
        if (l > (long)Integer.MAX_VALUE) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot buffer entire body for content length: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        Closeable closeable = this.source();
        Throwable throwable = null;
        try {
            object = object.invoke(closeable);
        }
        catch (Throwable throwable2) {
            try {
                throw throwable2;
            }
            catch (Throwable throwable3) {
                InlineMarker.finallyStart(1);
                CloseableKt.closeFinally(closeable, throwable2);
                InlineMarker.finallyEnd(1);
                throw throwable3;
            }
        }
        InlineMarker.finallyStart(1);
        CloseableKt.closeFinally(closeable, throwable);
        InlineMarker.finallyEnd(1);
        int n = ((Number)function1.invoke(object)).intValue();
        if (l == -1L) return (T)object;
        if (l == (long)n) {
            return (T)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Content-Length (");
        ((StringBuilder)object).append(l);
        ((StringBuilder)object).append(") and stream length (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") disagree");
        throw (Throwable)new IOException(((StringBuilder)object).toString());
    }

    @JvmStatic
    public static final ResponseBody create(String string2, MediaType mediaType) {
        return Companion.create(string2, mediaType);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.asResponseBody(contentType, contentLength)", imports={"okhttp3.ResponseBody.Companion.asResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, long l, BufferedSource bufferedSource) {
        return Companion.create(mediaType, l, bufferedSource);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, String string2) {
        return Companion.create(mediaType, string2);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, ByteString byteString) {
        return Companion.create(mediaType, byteString);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
    @JvmStatic
    public static final ResponseBody create(MediaType mediaType, byte[] arrby) {
        return Companion.create(mediaType, arrby);
    }

    @JvmStatic
    public static final ResponseBody create(BufferedSource bufferedSource, MediaType mediaType, long l) {
        return Companion.create(bufferedSource, mediaType, l);
    }

    @JvmStatic
    public static final ResponseBody create(ByteString byteString, MediaType mediaType) {
        return Companion.create(byteString, mediaType);
    }

    @JvmStatic
    public static final ResponseBody create(byte[] arrby, MediaType mediaType) {
        return Companion.create(arrby, mediaType);
    }

    public final InputStream byteStream() {
        return this.source().inputStream();
    }

    public final ByteString byteString() throws IOException {
        ByteString byteString;
        long l = this.contentLength();
        if (l > (long)Integer.MAX_VALUE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot buffer entire body for content length: ");
            stringBuilder.append(l);
            throw (Throwable)new IOException(stringBuilder.toString());
        }
        Object object = this.source();
        Throwable throwable = null;
        try {
            byteString = ((BufferedSource)object).readByteString();
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
        CloseableKt.closeFinally((Closeable)object, throwable);
        int n = byteString.size();
        if (l == -1L) return byteString;
        if (l == (long)n) {
            return byteString;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Content-Length (");
        ((StringBuilder)object).append(l);
        ((StringBuilder)object).append(") and stream length (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") disagree");
        throw (Throwable)new IOException(((StringBuilder)object).toString());
    }

    public final byte[] bytes() throws IOException {
        byte[] arrby;
        long l = this.contentLength();
        if (l > (long)Integer.MAX_VALUE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot buffer entire body for content length: ");
            stringBuilder.append(l);
            throw (Throwable)new IOException(stringBuilder.toString());
        }
        Object object = this.source();
        Throwable throwable = null;
        try {
            arrby = ((BufferedSource)object).readByteArray();
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
        CloseableKt.closeFinally((Closeable)object, throwable);
        int n = arrby.length;
        if (l == -1L) return arrby;
        if (l == (long)n) {
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Content-Length (");
        ((StringBuilder)object).append(l);
        ((StringBuilder)object).append(") and stream length (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") disagree");
        throw (Throwable)new IOException(((StringBuilder)object).toString());
    }

    public final Reader charStream() {
        Reader reader = this.reader;
        if (reader != null) {
            return reader;
        }
        this.reader = reader = (Reader)new BomAwareReader(this.source(), this.charset());
        return reader;
    }

    @Override
    public void close() {
        Util.closeQuietly(this.source());
    }

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract BufferedSource source();

    public final String string() throws IOException {
        Object object;
        Closeable closeable = this.source();
        Throwable throwable = null;
        try {
            object = (BufferedSource)closeable;
            object = object.readString(Util.readBomAsCharset((BufferedSource)object, this.charset()));
        }
        catch (Throwable throwable2) {
            try {
                throw throwable2;
            }
            catch (Throwable throwable3) {
                CloseableKt.closeFinally(closeable, throwable2);
                throw throwable3;
            }
        }
        CloseableKt.closeFinally(closeable, throwable);
        return object;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/ResponseBody$BomAwareReader;", "Ljava/io/Reader;", "source", "Lokio/BufferedSource;", "charset", "Ljava/nio/charset/Charset;", "(Lokio/BufferedSource;Ljava/nio/charset/Charset;)V", "closed", "", "delegate", "close", "", "read", "", "cbuf", "", "off", "len", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class BomAwareReader
    extends Reader {
        private final Charset charset;
        private boolean closed;
        private Reader delegate;
        private final BufferedSource source;

        public BomAwareReader(BufferedSource bufferedSource, Charset charset) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
            Intrinsics.checkParameterIsNotNull(charset, "charset");
            this.source = bufferedSource;
            this.charset = charset;
        }

        @Override
        public void close() throws IOException {
            this.closed = true;
            Reader reader = this.delegate;
            if (reader != null) {
                reader.close();
                return;
            }
            this.source.close();
        }

        @Override
        public int read(char[] arrc, int n, int n2) throws IOException {
            Intrinsics.checkParameterIsNotNull(arrc, "cbuf");
            if (this.closed) throw (Throwable)new IOException("Stream closed");
            Reader reader = this.delegate;
            if (reader != null) {
                return reader.read(arrc, n, n2);
            }
            this.delegate = reader = (Reader)new InputStreamReader(this.source.inputStream(), Util.readBomAsCharset(this.source, this.charset));
            return reader.read(arrc, n, n2);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\"\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u000bH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\fH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\rH\u0007J'\u0010\u000e\u001a\u00020\u0004*\u00020\u000b2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u00a2\u0006\u0002\b\u0003J\u001d\u0010\u000f\u001a\u00020\u0004*\u00020\b2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\b\u0003J\u001d\u0010\u000f\u001a\u00020\u0004*\u00020\f2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\b\u0003J\u001d\u0010\u000f\u001a\u00020\u0004*\u00020\r2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\b\u0003\u00a8\u0006\u0010"}, d2={"Lokhttp3/ResponseBody$Companion;", "", "()V", "create", "Lokhttp3/ResponseBody;", "contentType", "Lokhttp3/MediaType;", "content", "", "contentLength", "", "Lokio/BufferedSource;", "", "Lokio/ByteString;", "asResponseBody", "toResponseBody", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ ResponseBody create$default(Companion companion, String string2, MediaType mediaType, int n, Object object) {
            if ((n & 1) == 0) return companion.create(string2, mediaType);
            mediaType = null;
            return companion.create(string2, mediaType);
        }

        public static /* synthetic */ ResponseBody create$default(Companion companion, BufferedSource bufferedSource, MediaType mediaType, long l, int n, Object object) {
            if ((n & 1) != 0) {
                mediaType = null;
            }
            if ((n & 2) == 0) return companion.create(bufferedSource, mediaType, l);
            l = -1L;
            return companion.create(bufferedSource, mediaType, l);
        }

        public static /* synthetic */ ResponseBody create$default(Companion companion, ByteString byteString, MediaType mediaType, int n, Object object) {
            if ((n & 1) == 0) return companion.create(byteString, mediaType);
            mediaType = null;
            return companion.create(byteString, mediaType);
        }

        public static /* synthetic */ ResponseBody create$default(Companion companion, byte[] arrby, MediaType mediaType, int n, Object object) {
            if ((n & 1) == 0) return companion.create(arrby, mediaType);
            mediaType = null;
            return companion.create(arrby, mediaType);
        }

        @JvmStatic
        public final ResponseBody create(String object, MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(object, "$this$toResponseBody");
            Charset charset = Charsets.UTF_8;
            Object object2 = mediaType;
            if (mediaType != null) {
                Object object3 = MediaType.charset$default(mediaType, null, 1, null);
                charset = object3;
                object2 = mediaType;
                if (object3 == null) {
                    charset = Charsets.UTF_8;
                    object3 = MediaType.Companion;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(mediaType);
                    ((StringBuilder)object2).append("; charset=utf-8");
                    object2 = ((MediaType.Companion)object3).parse(((StringBuilder)object2).toString());
                }
            }
            object = new Buffer().writeString((String)object, charset);
            return this.create((BufferedSource)object, (MediaType)object2, ((Buffer)object).size());
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.asResponseBody(contentType, contentLength)", imports={"okhttp3.ResponseBody.Companion.asResponseBody"}))
        @JvmStatic
        public final ResponseBody create(MediaType mediaType, long l, BufferedSource bufferedSource) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "content");
            return this.create(bufferedSource, mediaType, l);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
        @JvmStatic
        public final ResponseBody create(MediaType mediaType, String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "content");
            return this.create(string2, mediaType);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
        @JvmStatic
        public final ResponseBody create(MediaType mediaType, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(byteString, "content");
            return this.create(byteString, mediaType);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toResponseBody(contentType)", imports={"okhttp3.ResponseBody.Companion.toResponseBody"}))
        @JvmStatic
        public final ResponseBody create(MediaType mediaType, byte[] arrby) {
            Intrinsics.checkParameterIsNotNull(arrby, "content");
            return this.create(arrby, mediaType);
        }

        @JvmStatic
        public final ResponseBody create(BufferedSource bufferedSource, MediaType mediaType, long l) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "$this$asResponseBody");
            return new ResponseBody(bufferedSource, mediaType, l){
                final /* synthetic */ long $contentLength;
                final /* synthetic */ MediaType $contentType;
                final /* synthetic */ BufferedSource $this_asResponseBody;
                {
                    this.$this_asResponseBody = bufferedSource;
                    this.$contentType = mediaType;
                    this.$contentLength = l;
                }

                public long contentLength() {
                    return this.$contentLength;
                }

                public MediaType contentType() {
                    return this.$contentType;
                }

                public BufferedSource source() {
                    return this.$this_asResponseBody;
                }
            };
        }

        @JvmStatic
        public final ResponseBody create(ByteString byteString, MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(byteString, "$this$toResponseBody");
            return this.create(new Buffer().write(byteString), mediaType, byteString.size());
        }

        @JvmStatic
        public final ResponseBody create(byte[] arrby, MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$toResponseBody");
            return this.create(new Buffer().write(arrby), mediaType, arrby.length);
        }
    }

}

