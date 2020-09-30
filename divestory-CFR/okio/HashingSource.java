/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.ForwardingSource;
import okio.Segment;
import okio.Source;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u001f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00020\bH\u0007\u00a2\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0016R\u0011\u0010\n\u001a\u00020\b8G\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lokio/HashingSource;", "Lokio/ForwardingSource;", "source", "Lokio/Source;", "algorithm", "", "(Lokio/Source;Ljava/lang/String;)V", "key", "Lokio/ByteString;", "(Lokio/Source;Lokio/ByteString;Ljava/lang/String;)V", "hash", "()Lokio/ByteString;", "mac", "Ljavax/crypto/Mac;", "messageDigest", "Ljava/security/MessageDigest;", "-deprecated_hash", "read", "", "sink", "Lokio/Buffer;", "byteCount", "Companion", "okio"}, k=1, mv={1, 1, 16})
public final class HashingSource
extends ForwardingSource {
    public static final Companion Companion = new Companion(null);
    private final Mac mac;
    private final MessageDigest messageDigest;

    public HashingSource(Source source2, String string2) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        Intrinsics.checkParameterIsNotNull(string2, "algorithm");
        super(source2);
        this.messageDigest = MessageDigest.getInstance(string2);
        this.mac = null;
    }

    public HashingSource(Source object, ByteString byteString, String string2) {
        Intrinsics.checkParameterIsNotNull(object, "source");
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        Intrinsics.checkParameterIsNotNull(string2, "algorithm");
        super((Source)object);
        try {
            object = Mac.getInstance(string2);
            SecretKeySpec secretKeySpec = new SecretKeySpec(byteString.toByteArray(), string2);
            ((Mac)object).init(secretKeySpec);
            this.mac = object;
            this.messageDigest = null;
            return;
        }
        catch (InvalidKeyException invalidKeyException) {
            throw (Throwable)new IllegalArgumentException(invalidKeyException);
        }
    }

    @JvmStatic
    public static final HashingSource hmacSha1(Source source2, ByteString byteString) {
        return Companion.hmacSha1(source2, byteString);
    }

    @JvmStatic
    public static final HashingSource hmacSha256(Source source2, ByteString byteString) {
        return Companion.hmacSha256(source2, byteString);
    }

    @JvmStatic
    public static final HashingSource hmacSha512(Source source2, ByteString byteString) {
        return Companion.hmacSha512(source2, byteString);
    }

    @JvmStatic
    public static final HashingSource md5(Source source2) {
        return Companion.md5(source2);
    }

    @JvmStatic
    public static final HashingSource sha1(Source source2) {
        return Companion.sha1(source2);
    }

    @JvmStatic
    public static final HashingSource sha256(Source source2) {
        return Companion.sha256(source2);
    }

    @JvmStatic
    public static final HashingSource sha512(Source source2) {
        return Companion.sha512(source2);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hash", imports={}))
    public final ByteString -deprecated_hash() {
        return this.hash();
    }

    public final ByteString hash() {
        Object object = this.messageDigest;
        if (object != null) {
            object = ((MessageDigest)object).digest();
        } else {
            object = this.mac;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            object = ((Mac)object).doFinal();
        }
        Intrinsics.checkExpressionValueIsNotNull(object, "result");
        return new ByteString((byte[])object);
    }

    @Override
    public long read(Buffer buffer, long l) throws IOException {
        long l2;
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        long l3 = super.read(buffer, l);
        if (l3 == -1L) return l3;
        long l4 = buffer.size() - l3;
        long l5 = buffer.size();
        Segment segment = buffer.head;
        l = l5;
        Object object = segment;
        if (segment == null) {
            Intrinsics.throwNpe();
            object = segment;
            l = l5;
        }
        do {
            l5 = l4;
            l2 = l;
            segment = object;
            if (l <= l4) break;
            object = ((Segment)object).prev;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            l -= (long)(((Segment)object).limit - ((Segment)object).pos);
        } while (true);
        while (l2 < buffer.size()) {
            int n = (int)((long)segment.pos + l5 - l2);
            object = this.messageDigest;
            if (object != null) {
                ((MessageDigest)object).update(segment.data, n, segment.limit - n);
            } else {
                object = this.mac;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                ((Mac)object).update(segment.data, n, segment.limit - n);
            }
            l2 += (long)(segment.limit - segment.pos);
            segment = segment.next;
            if (segment == null) {
                Intrinsics.throwNpe();
            }
            l5 = l2;
        }
        return l3;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u000f"}, d2={"Lokio/HashingSource$Companion;", "", "()V", "hmacSha1", "Lokio/HashingSource;", "source", "Lokio/Source;", "key", "Lokio/ByteString;", "hmacSha256", "hmacSha512", "md5", "sha1", "sha256", "sha512", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final HashingSource hmacSha1(Source source2, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            Intrinsics.checkParameterIsNotNull(byteString, "key");
            return new HashingSource(source2, byteString, "HmacSHA1");
        }

        @JvmStatic
        public final HashingSource hmacSha256(Source source2, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            Intrinsics.checkParameterIsNotNull(byteString, "key");
            return new HashingSource(source2, byteString, "HmacSHA256");
        }

        @JvmStatic
        public final HashingSource hmacSha512(Source source2, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            Intrinsics.checkParameterIsNotNull(byteString, "key");
            return new HashingSource(source2, byteString, "HmacSHA512");
        }

        @JvmStatic
        public final HashingSource md5(Source source2) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            return new HashingSource(source2, "MD5");
        }

        @JvmStatic
        public final HashingSource sha1(Source source2) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            return new HashingSource(source2, "SHA-1");
        }

        @JvmStatic
        public final HashingSource sha256(Source source2) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            return new HashingSource(source2, "SHA-256");
        }

        @JvmStatic
        public final HashingSource sha512(Source source2) {
            Intrinsics.checkParameterIsNotNull(source2, "source");
            return new HashingSource(source2, "SHA-512");
        }
    }

}

