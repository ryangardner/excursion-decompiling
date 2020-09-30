/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.RequestBody$Companion$asRequestBody
 *  okhttp3.RequestBody$Companion$toRequestBody
 */
package okhttp3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&\u00a8\u0006\u000f"}, d2={"Lokhttp3/RequestBody;", "", "()V", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "isDuplex", "", "isOneShot", "writeTo", "", "sink", "Lokio/BufferedSink;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public abstract class RequestBody {
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final RequestBody create(File file, MediaType mediaType) {
        return Companion.create(file, mediaType);
    }

    @JvmStatic
    public static final RequestBody create(String string2, MediaType mediaType) {
        return Companion.create(string2, mediaType);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'file' argument first to fix Java", replaceWith=@ReplaceWith(expression="file.asRequestBody(contentType)", imports={"okhttp3.RequestBody.Companion.asRequestBody"}))
    @JvmStatic
    public static final RequestBody create(MediaType mediaType, File file) {
        return Companion.create(mediaType, file);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
    @JvmStatic
    public static final RequestBody create(MediaType mediaType, String string2) {
        return Companion.create(mediaType, string2);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
    @JvmStatic
    public static final RequestBody create(MediaType mediaType, ByteString byteString) {
        return Companion.create(mediaType, byteString);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType, offset, byteCount)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
    @JvmStatic
    public static final RequestBody create(MediaType mediaType, byte[] arrby) {
        return Companion.create$default(Companion, mediaType, arrby, 0, 0, 12, null);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType, offset, byteCount)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
    @JvmStatic
    public static final RequestBody create(MediaType mediaType, byte[] arrby, int n) {
        return Companion.create$default(Companion, mediaType, arrby, n, 0, 8, null);
    }

    @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType, offset, byteCount)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
    @JvmStatic
    public static final RequestBody create(MediaType mediaType, byte[] arrby, int n, int n2) {
        return Companion.create(mediaType, arrby, n, n2);
    }

    @JvmStatic
    public static final RequestBody create(ByteString byteString, MediaType mediaType) {
        return Companion.create(byteString, mediaType);
    }

    @JvmStatic
    public static final RequestBody create(byte[] arrby) {
        return Companion.create$default(Companion, arrby, null, 0, 0, 7, null);
    }

    @JvmStatic
    public static final RequestBody create(byte[] arrby, MediaType mediaType) {
        return Companion.create$default(Companion, arrby, mediaType, 0, 0, 6, null);
    }

    @JvmStatic
    public static final RequestBody create(byte[] arrby, MediaType mediaType, int n) {
        return Companion.create$default(Companion, arrby, mediaType, n, 0, 4, null);
    }

    @JvmStatic
    public static final RequestBody create(byte[] arrby, MediaType mediaType, int n, int n2) {
        return Companion.create(arrby, mediaType, n, n2);
    }

    public long contentLength() throws IOException {
        return -1L;
    }

    public abstract MediaType contentType();

    public boolean isDuplex() {
        return false;
    }

    public boolean isOneShot() {
        return false;
    }

    public abstract void writeTo(BufferedSink var1) throws IOException;

    @Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J.\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u000eH\u0007J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u000fH\u0007J\u001d\u0010\u0010\u001a\u00020\u0004*\u00020\b2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\b\u0003J1\u0010\u0011\u001a\u00020\u0004*\u00020\n2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH\u0007\u00a2\u0006\u0002\b\u0003J\u001d\u0010\u0011\u001a\u00020\u0004*\u00020\u000e2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\b\u0003J\u001d\u0010\u0011\u001a\u00020\u0004*\u00020\u000f2\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\b\u0003\u00a8\u0006\u0012"}, d2={"Lokhttp3/RequestBody$Companion;", "", "()V", "create", "Lokhttp3/RequestBody;", "contentType", "Lokhttp3/MediaType;", "file", "Ljava/io/File;", "content", "", "offset", "", "byteCount", "", "Lokio/ByteString;", "asRequestBody", "toRequestBody", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ RequestBody create$default(Companion companion, File file, MediaType mediaType, int n, Object object) {
            if ((n & 1) == 0) return companion.create(file, mediaType);
            mediaType = null;
            return companion.create(file, mediaType);
        }

        public static /* synthetic */ RequestBody create$default(Companion companion, String string2, MediaType mediaType, int n, Object object) {
            if ((n & 1) == 0) return companion.create(string2, mediaType);
            mediaType = null;
            return companion.create(string2, mediaType);
        }

        public static /* synthetic */ RequestBody create$default(Companion companion, MediaType mediaType, byte[] arrby, int n, int n2, int n3, Object object) {
            if ((n3 & 4) != 0) {
                n = 0;
            }
            if ((n3 & 8) == 0) return companion.create(mediaType, arrby, n, n2);
            n2 = arrby.length;
            return companion.create(mediaType, arrby, n, n2);
        }

        public static /* synthetic */ RequestBody create$default(Companion companion, ByteString byteString, MediaType mediaType, int n, Object object) {
            if ((n & 1) == 0) return companion.create(byteString, mediaType);
            mediaType = null;
            return companion.create(byteString, mediaType);
        }

        public static /* synthetic */ RequestBody create$default(Companion companion, byte[] arrby, MediaType mediaType, int n, int n2, int n3, Object object) {
            if ((n3 & 1) != 0) {
                mediaType = null;
            }
            if ((n3 & 2) != 0) {
                n = 0;
            }
            if ((n3 & 4) == 0) return companion.create(arrby, mediaType, n, n2);
            n2 = arrby.length;
            return companion.create(arrby, mediaType, n, n2);
        }

        @JvmStatic
        public final RequestBody create(File file, MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(file, "$this$asRequestBody");
            return new RequestBody(file, mediaType){
                final /* synthetic */ MediaType $contentType;
                final /* synthetic */ File $this_asRequestBody;
                {
                    this.$this_asRequestBody = file;
                    this.$contentType = mediaType;
                }

                public long contentLength() {
                    return this.$this_asRequestBody.length();
                }

                public MediaType contentType() {
                    return this.$contentType;
                }

                public void writeTo(BufferedSink bufferedSink) {
                    Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
                    java.io.Closeable closeable = okio.Okio.source(this.$this_asRequestBody);
                    java.lang.Throwable throwable = null;
                    try {
                        bufferedSink.writeAll((okio.Source)closeable);
                    }
                    catch (java.lang.Throwable throwable2) {
                        try {
                            throw throwable2;
                        }
                        catch (java.lang.Throwable throwable3) {
                            kotlin.io.CloseableKt.closeFinally(closeable, throwable2);
                            throw throwable3;
                        }
                    }
                    kotlin.io.CloseableKt.closeFinally(closeable, throwable);
                }
            };
        }

        @JvmStatic
        public final RequestBody create(String arrby, MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$toRequestBody");
            Charset charset = Charsets.UTF_8;
            Object object = mediaType;
            if (mediaType != null) {
                Object object2 = MediaType.charset$default(mediaType, null, 1, null);
                charset = object2;
                object = mediaType;
                if (object2 == null) {
                    charset = Charsets.UTF_8;
                    object2 = MediaType.Companion;
                    object = new StringBuilder();
                    ((StringBuilder)object).append(mediaType);
                    ((StringBuilder)object).append("; charset=utf-8");
                    object = ((MediaType.Companion)object2).parse(((StringBuilder)object).toString());
                }
            }
            arrby = arrby.getBytes(charset);
            Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
            return this.create(arrby, (MediaType)object, 0, arrby.length);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'file' argument first to fix Java", replaceWith=@ReplaceWith(expression="file.asRequestBody(contentType)", imports={"okhttp3.RequestBody.Companion.asRequestBody"}))
        @JvmStatic
        public final RequestBody create(MediaType mediaType, File file) {
            Intrinsics.checkParameterIsNotNull(file, "file");
            return this.create(file, mediaType);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
        @JvmStatic
        public final RequestBody create(MediaType mediaType, String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "content");
            return this.create(string2, mediaType);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
        @JvmStatic
        public final RequestBody create(MediaType mediaType, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(byteString, "content");
            return this.create(byteString, mediaType);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType, offset, byteCount)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
        @JvmStatic
        public final RequestBody create(MediaType mediaType, byte[] arrby) {
            return Companion.create$default(this, mediaType, arrby, 0, 0, 12, null);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType, offset, byteCount)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
        @JvmStatic
        public final RequestBody create(MediaType mediaType, byte[] arrby, int n) {
            return Companion.create$default(this, mediaType, arrby, n, 0, 8, null);
        }

        @Deprecated(level=DeprecationLevel.WARNING, message="Moved to extension function. Put the 'content' argument first to fix Java", replaceWith=@ReplaceWith(expression="content.toRequestBody(contentType, offset, byteCount)", imports={"okhttp3.RequestBody.Companion.toRequestBody"}))
        @JvmStatic
        public final RequestBody create(MediaType mediaType, byte[] arrby, int n, int n2) {
            Intrinsics.checkParameterIsNotNull(arrby, "content");
            return this.create(arrby, mediaType, n, n2);
        }

        @JvmStatic
        public final RequestBody create(ByteString byteString, MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(byteString, "$this$toRequestBody");
            return new RequestBody(byteString, mediaType){
                final /* synthetic */ MediaType $contentType;
                final /* synthetic */ ByteString $this_toRequestBody;
                {
                    this.$this_toRequestBody = byteString;
                    this.$contentType = mediaType;
                }

                public long contentLength() {
                    return this.$this_toRequestBody.size();
                }

                public MediaType contentType() {
                    return this.$contentType;
                }

                public void writeTo(BufferedSink bufferedSink) {
                    Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
                    bufferedSink.write(this.$this_toRequestBody);
                }
            };
        }

        @JvmStatic
        public final RequestBody create(byte[] arrby) {
            return Companion.create$default(this, arrby, null, 0, 0, 7, null);
        }

        @JvmStatic
        public final RequestBody create(byte[] arrby, MediaType mediaType) {
            return Companion.create$default(this, arrby, mediaType, 0, 0, 6, null);
        }

        @JvmStatic
        public final RequestBody create(byte[] arrby, MediaType mediaType, int n) {
            return Companion.create$default(this, arrby, mediaType, n, 0, 4, null);
        }

        @JvmStatic
        public final RequestBody create(byte[] arrby, MediaType mediaType, int n, int n2) {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$toRequestBody");
            Util.checkOffsetAndCount(arrby.length, n, n2);
            return new RequestBody(arrby, mediaType, n2, n){
                final /* synthetic */ int $byteCount;
                final /* synthetic */ MediaType $contentType;
                final /* synthetic */ int $offset;
                final /* synthetic */ byte[] $this_toRequestBody;
                {
                    this.$this_toRequestBody = arrby;
                    this.$contentType = mediaType;
                    this.$byteCount = n;
                    this.$offset = n2;
                }

                public long contentLength() {
                    return this.$byteCount;
                }

                public MediaType contentType() {
                    return this.$contentType;
                }

                public void writeTo(BufferedSink bufferedSink) {
                    Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
                    bufferedSink.write(this.$this_toRequestBody, this.$offset, this.$byteCount);
                }
            };
        }
    }

}

