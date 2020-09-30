/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u001c2\u00020\u0001:\u0002\u001b\u001cB#\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\r\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\b\u0012J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bJ\u001a\u0010\u0014\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8G\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\t\u00a8\u0006\u001d"}, d2={"Lokhttp3/FormBody;", "Lokhttp3/RequestBody;", "encodedNames", "", "", "encodedValues", "(Ljava/util/List;Ljava/util/List;)V", "size", "", "()I", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "encodedName", "index", "encodedValue", "name", "-deprecated_size", "value", "writeOrCountBytes", "sink", "Lokio/BufferedSink;", "countBytes", "", "writeTo", "", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class FormBody
extends RequestBody {
    private static final MediaType CONTENT_TYPE;
    public static final Companion Companion;
    private final List<String> encodedNames;
    private final List<String> encodedValues;

    static {
        Companion = new Companion(null);
        CONTENT_TYPE = MediaType.Companion.get("application/x-www-form-urlencoded");
    }

    public FormBody(List<String> list, List<String> list2) {
        Intrinsics.checkParameterIsNotNull(list, "encodedNames");
        Intrinsics.checkParameterIsNotNull(list2, "encodedValues");
        this.encodedNames = Util.toImmutableList(list);
        this.encodedValues = Util.toImmutableList(list2);
    }

    private final long writeOrCountBytes(BufferedSink bufferedSink, boolean bl) {
        if (bl) {
            bufferedSink = new Buffer();
        } else {
            if (bufferedSink == null) {
                Intrinsics.throwNpe();
            }
            bufferedSink = bufferedSink.getBuffer();
        }
        int n = this.encodedNames.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                ((Buffer)bufferedSink).writeByte(38);
            }
            ((Buffer)bufferedSink).writeUtf8(this.encodedNames.get(i));
            ((Buffer)bufferedSink).writeByte(61);
            ((Buffer)bufferedSink).writeUtf8(this.encodedValues.get(i));
        }
        if (!bl) return 0L;
        long l = ((Buffer)bufferedSink).size();
        ((Buffer)bufferedSink).clear();
        return l;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
    public final int -deprecated_size() {
        return this.size();
    }

    @Override
    public long contentLength() {
        return this.writeOrCountBytes(null, true);
    }

    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    public final String encodedName(int n) {
        return this.encodedNames.get(n);
    }

    public final String encodedValue(int n) {
        return this.encodedValues.get(n);
    }

    public final String name(int n) {
        return HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedName(n), 0, 0, true, 3, null);
    }

    public final int size() {
        return this.encodedNames.size();
    }

    public final String value(int n) {
        return HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedValue(n), 0, 0, true, 3, null);
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
        this.writeOrCountBytes(bufferedSink, false);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\b\u0007\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u0016\u0010\f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u0006\u0010\r\u001a\u00020\u000eR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lokhttp3/FormBody$Builder;", "", "charset", "Ljava/nio/charset/Charset;", "(Ljava/nio/charset/Charset;)V", "names", "", "", "values", "add", "name", "value", "addEncoded", "build", "Lokhttp3/FormBody;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private final Charset charset;
        private final List<String> names;
        private final List<String> values;

        public Builder() {
            this(null, 1, null);
        }

        public Builder(Charset charset) {
            this.charset = charset;
            this.names = new ArrayList();
            this.values = new ArrayList();
        }

        public /* synthetic */ Builder(Charset charset, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 1) != 0) {
                charset = null;
            }
            this(charset);
        }

        public final Builder add(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            ((Collection)builder.names).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, string2, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, builder.charset, 91, null));
            ((Collection)builder.values).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, string3, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, builder.charset, 91, null));
            return builder;
        }

        public final Builder addEncoded(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            ((Collection)builder.names).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, string2, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, builder.charset, 83, null));
            ((Collection)builder.values).add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, string3, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, builder.charset, 83, null));
            return builder;
        }

        public final FormBody build() {
            return new FormBody(this.names, this.values);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lokhttp3/FormBody$Companion;", "", "()V", "CONTENT_TYPE", "Lokhttp3/MediaType;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}

