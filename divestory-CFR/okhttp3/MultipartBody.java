/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 #2\u00020\u0001:\u0003\"#$B%\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00020\u000bH\u0007\u00a2\u0006\u0002\b\u0015J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0012J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007\u00a2\u0006\u0002\b\u0018J\r\u0010\u0011\u001a\u00020\u0012H\u0007\u00a2\u0006\u0002\b\u0019J\r\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\b\u001aJ\u001a\u0010\u001b\u001a\u00020\u000e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\n\u001a\u00020\u000b8G\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128G\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0013\u0010\u0004\u001a\u00020\u00058\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0014\u00a8\u0006%"}, d2={"Lokhttp3/MultipartBody;", "Lokhttp3/RequestBody;", "boundaryByteString", "Lokio/ByteString;", "type", "Lokhttp3/MediaType;", "parts", "", "Lokhttp3/MultipartBody$Part;", "(Lokio/ByteString;Lokhttp3/MediaType;Ljava/util/List;)V", "boundary", "", "()Ljava/lang/String;", "contentLength", "", "contentType", "()Ljava/util/List;", "size", "", "()I", "()Lokhttp3/MediaType;", "-deprecated_boundary", "part", "index", "-deprecated_parts", "-deprecated_size", "-deprecated_type", "writeOrCountBytes", "sink", "Lokio/BufferedSink;", "countBytes", "", "writeTo", "", "Builder", "Companion", "Part", "okhttp"}, k=1, mv={1, 1, 16})
public final class MultipartBody
extends RequestBody {
    public static final MediaType ALTERNATIVE;
    private static final byte[] COLONSPACE;
    private static final byte[] CRLF;
    public static final Companion Companion;
    private static final byte[] DASHDASH;
    public static final MediaType DIGEST;
    public static final MediaType FORM;
    public static final MediaType MIXED;
    public static final MediaType PARALLEL;
    private final ByteString boundaryByteString;
    private long contentLength;
    private final MediaType contentType;
    private final List<Part> parts;
    private final MediaType type;

    static {
        Companion = new Companion(null);
        MIXED = MediaType.Companion.get("multipart/mixed");
        ALTERNATIVE = MediaType.Companion.get("multipart/alternative");
        DIGEST = MediaType.Companion.get("multipart/digest");
        PARALLEL = MediaType.Companion.get("multipart/parallel");
        FORM = MediaType.Companion.get("multipart/form-data");
        COLONSPACE = new byte[]{(byte)58, (byte)32};
        CRLF = new byte[]{(byte)13, (byte)10};
        byte by = (byte)45;
        DASHDASH = new byte[]{by, by};
    }

    public MultipartBody(ByteString comparable, MediaType object, List<Part> list) {
        Intrinsics.checkParameterIsNotNull(comparable, "boundaryByteString");
        Intrinsics.checkParameterIsNotNull(object, "type");
        Intrinsics.checkParameterIsNotNull(list, "parts");
        this.boundaryByteString = comparable;
        this.type = object;
        this.parts = list;
        object = MediaType.Companion;
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append(this.type);
        ((StringBuilder)comparable).append("; boundary=");
        ((StringBuilder)comparable).append(this.boundary());
        this.contentType = ((MediaType.Companion)object).get(((StringBuilder)comparable).toString());
        this.contentLength = -1L;
    }

    private final long writeOrCountBytes(BufferedSink bufferedSink, boolean bl) throws IOException {
        long l;
        Buffer buffer = null;
        if (bl) {
            buffer = new Buffer();
            bufferedSink = buffer;
        }
        int n = this.parts.size();
        long l2 = 0L;
        for (int i = 0; i < n; ++i) {
            Object object = this.parts.get(i);
            Object object2 = ((Part)object).headers();
            object = ((Part)object).body();
            if (bufferedSink == null) {
                Intrinsics.throwNpe();
            }
            bufferedSink.write(DASHDASH);
            bufferedSink.write(this.boundaryByteString);
            bufferedSink.write(CRLF);
            if (object2 != null) {
                int n2 = ((Headers)object2).size();
                for (int j = 0; j < n2; ++j) {
                    bufferedSink.writeUtf8(((Headers)object2).name(j)).write(COLONSPACE).writeUtf8(((Headers)object2).value(j)).write(CRLF);
                }
            }
            if ((object2 = ((RequestBody)object).contentType()) != null) {
                bufferedSink.writeUtf8("Content-Type: ").writeUtf8(((MediaType)object2).toString()).write(CRLF);
            }
            if ((l = ((RequestBody)object).contentLength()) != -1L) {
                bufferedSink.writeUtf8("Content-Length: ").writeDecimalLong(l).write(CRLF);
            } else if (bl) {
                if (buffer == null) {
                    Intrinsics.throwNpe();
                }
                buffer.clear();
                return -1L;
            }
            bufferedSink.write(CRLF);
            if (bl) {
                l2 += l;
            } else {
                ((RequestBody)object).writeTo(bufferedSink);
            }
            bufferedSink.write(CRLF);
        }
        if (bufferedSink == null) {
            Intrinsics.throwNpe();
        }
        bufferedSink.write(DASHDASH);
        bufferedSink.write(this.boundaryByteString);
        bufferedSink.write(DASHDASH);
        bufferedSink.write(CRLF);
        l = l2;
        if (!bl) return l;
        if (buffer == null) {
            Intrinsics.throwNpe();
        }
        l = l2 + buffer.size();
        buffer.clear();
        return l;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="boundary", imports={}))
    public final String -deprecated_boundary() {
        return this.boundary();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="parts", imports={}))
    public final List<Part> -deprecated_parts() {
        return this.parts;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
    public final int -deprecated_size() {
        return this.size();
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="type", imports={}))
    public final MediaType -deprecated_type() {
        return this.type;
    }

    public final String boundary() {
        return this.boundaryByteString.utf8();
    }

    @Override
    public long contentLength() throws IOException {
        long l;
        long l2 = l = this.contentLength;
        if (l != -1L) return l2;
        this.contentLength = l2 = this.writeOrCountBytes(null, true);
        return l2;
    }

    @Override
    public MediaType contentType() {
        return this.contentType;
    }

    public final Part part(int n) {
        return this.parts.get(n);
    }

    public final List<Part> parts() {
        return this.parts;
    }

    public final int size() {
        return this.parts.size();
    }

    public final MediaType type() {
        return this.type;
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
        this.writeOrCountBytes(bufferedSink, false);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003J \u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokhttp3/MultipartBody$Builder;", "", "boundary", "", "(Ljava/lang/String;)V", "Lokio/ByteString;", "parts", "", "Lokhttp3/MultipartBody$Part;", "type", "Lokhttp3/MediaType;", "addFormDataPart", "name", "value", "filename", "body", "Lokhttp3/RequestBody;", "addPart", "headers", "Lokhttp3/Headers;", "part", "build", "Lokhttp3/MultipartBody;", "setType", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private final ByteString boundary;
        private final List<Part> parts;
        private MediaType type;

        public Builder() {
            this(null, 1, null);
        }

        public Builder(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "boundary");
            this.boundary = ByteString.Companion.encodeUtf8(string2);
            this.type = MIXED;
            this.parts = new ArrayList();
        }

        public /* synthetic */ Builder(String string2, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 1) != 0) {
                string2 = UUID.randomUUID().toString();
                Intrinsics.checkExpressionValueIsNotNull(string2, "UUID.randomUUID().toString()");
            }
            this(string2);
        }

        public final Builder addFormDataPart(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            builder.addPart(Part.Companion.createFormData(string2, string3));
            return builder;
        }

        public final Builder addFormDataPart(String string2, String string3, RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(requestBody, "body");
            Builder builder = this;
            builder.addPart(Part.Companion.createFormData(string2, string3, requestBody));
            return builder;
        }

        public final Builder addPart(Headers headers, RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(requestBody, "body");
            Builder builder = this;
            builder.addPart(Part.Companion.create(headers, requestBody));
            return builder;
        }

        public final Builder addPart(Part part) {
            Intrinsics.checkParameterIsNotNull(part, "part");
            Builder builder = this;
            ((Collection)builder.parts).add(part);
            return builder;
        }

        public final Builder addPart(RequestBody requestBody) {
            Intrinsics.checkParameterIsNotNull(requestBody, "body");
            Builder builder = this;
            builder.addPart(Part.Companion.create(requestBody));
            return builder;
        }

        public final MultipartBody build() {
            if (!(((Collection)this.parts).isEmpty() ^ true)) throw (Throwable)new IllegalStateException("Multipart body must have at least one part.".toString());
            return new MultipartBody(this.boundary, this.type, Util.toImmutableList(this.parts));
        }

        public final Builder setType(MediaType mediaType) {
            Intrinsics.checkParameterIsNotNull(mediaType, "type");
            Object object = this;
            if (Intrinsics.areEqual(mediaType.type(), "multipart")) {
                ((Builder)object).type = mediaType;
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("multipart != ");
            ((StringBuilder)object).append(mediaType);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001d\u0010\r\u001a\u00020\u000e*\u00060\u000fj\u0002`\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u00a2\u0006\u0002\b\u0013R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lokhttp3/MultipartBody$Companion;", "", "()V", "ALTERNATIVE", "Lokhttp3/MediaType;", "COLONSPACE", "", "CRLF", "DASHDASH", "DIGEST", "FORM", "MIXED", "PARALLEL", "appendQuotedString", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "key", "", "appendQuotedString$okhttp", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void appendQuotedString$okhttp(StringBuilder stringBuilder, String string2) {
            Intrinsics.checkParameterIsNotNull(stringBuilder, "$this$appendQuotedString");
            Intrinsics.checkParameterIsNotNull(string2, "key");
            stringBuilder.append('\"');
            int n = string2.length();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    stringBuilder.append('\"');
                    return;
                }
                char c = string2.charAt(n2);
                if (c == '\n') {
                    stringBuilder.append("%0A");
                } else if (c == '\r') {
                    stringBuilder.append("%0D");
                } else if (c == '\"') {
                    stringBuilder.append("%22");
                } else {
                    stringBuilder.append(c);
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0019\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\r\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\b\tJ\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0007\u00a2\u0006\u0002\b\nR\u0013\u0010\u0004\u001a\u00020\u00058\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\b\u00a8\u0006\f"}, d2={"Lokhttp3/MultipartBody$Part;", "", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "(Lokhttp3/Headers;Lokhttp3/RequestBody;)V", "()Lokhttp3/RequestBody;", "()Lokhttp3/Headers;", "-deprecated_body", "-deprecated_headers", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Part {
        public static final Companion Companion = new Companion(null);
        private final RequestBody body;
        private final Headers headers;

        private Part(Headers headers, RequestBody requestBody) {
            this.headers = headers;
            this.body = requestBody;
        }

        public /* synthetic */ Part(Headers headers, RequestBody requestBody, DefaultConstructorMarker defaultConstructorMarker) {
            this(headers, requestBody);
        }

        @JvmStatic
        public static final Part create(Headers headers, RequestBody requestBody) {
            return Companion.create(headers, requestBody);
        }

        @JvmStatic
        public static final Part create(RequestBody requestBody) {
            return Companion.create(requestBody);
        }

        @JvmStatic
        public static final Part createFormData(String string2, String string3) {
            return Companion.createFormData(string2, string3);
        }

        @JvmStatic
        public static final Part createFormData(String string2, String string3, RequestBody requestBody) {
            return Companion.createFormData(string2, string3, requestBody);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="body", imports={}))
        public final RequestBody -deprecated_body() {
            return this.body;
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="headers", imports={}))
        public final Headers -deprecated_headers() {
            return this.headers;
        }

        public final RequestBody body() {
            return this.body;
        }

        public final Headers headers() {
            return this.headers;
        }

        @Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0007J\"\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\u000e"}, d2={"Lokhttp3/MultipartBody$Part$Companion;", "", "()V", "create", "Lokhttp3/MultipartBody$Part;", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/RequestBody;", "createFormData", "name", "", "value", "filename", "okhttp"}, k=1, mv={1, 1, 16})
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            public final Part create(Headers headers, RequestBody requestBody) {
                Intrinsics.checkParameterIsNotNull(requestBody, "body");
                String string2 = headers != null ? headers.get("Content-Type") : null;
                boolean bl = true;
                boolean bl2 = string2 == null;
                if (!bl2) throw (Throwable)new IllegalArgumentException("Unexpected header: Content-Type".toString());
                string2 = headers != null ? headers.get("Content-Length") : null;
                bl2 = string2 == null ? bl : false;
                if (!bl2) throw (Throwable)new IllegalArgumentException("Unexpected header: Content-Length".toString());
                return new Part(headers, requestBody, null);
            }

            @JvmStatic
            public final Part create(RequestBody requestBody) {
                Intrinsics.checkParameterIsNotNull(requestBody, "body");
                return this.create(null, requestBody);
            }

            @JvmStatic
            public final Part createFormData(String string2, String string3) {
                Intrinsics.checkParameterIsNotNull(string2, "name");
                Intrinsics.checkParameterIsNotNull(string3, "value");
                return this.createFormData(string2, null, RequestBody.Companion.create$default(RequestBody.Companion, string3, null, 1, null));
            }

            @JvmStatic
            public final Part createFormData(String object, String string2, RequestBody requestBody) {
                Intrinsics.checkParameterIsNotNull(object, "name");
                Intrinsics.checkParameterIsNotNull(requestBody, "body");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("form-data; name=");
                MultipartBody.Companion.appendQuotedString$okhttp(stringBuilder, (String)object);
                if (string2 != null) {
                    stringBuilder.append("; filename=");
                    MultipartBody.Companion.appendQuotedString$okhttp(stringBuilder, string2);
                }
                object = stringBuilder.toString();
                Intrinsics.checkExpressionValueIsNotNull(object, "StringBuilder().apply(builderAction).toString()");
                object = new Headers.Builder().addUnsafeNonAscii("Content-Disposition", (String)object).build();
                return this.create((Headers)object, requestBody);
            }
        }

    }

}

