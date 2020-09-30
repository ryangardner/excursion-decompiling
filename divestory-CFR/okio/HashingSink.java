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
import okio.ForwardingSink;
import okio.Segment;
import okio.Sink;
import okio._Util;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u001f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00020\bH\u0007\u00a2\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\n\u001a\u00020\b8G\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lokio/HashingSink;", "Lokio/ForwardingSink;", "sink", "Lokio/Sink;", "algorithm", "", "(Lokio/Sink;Ljava/lang/String;)V", "key", "Lokio/ByteString;", "(Lokio/Sink;Lokio/ByteString;Ljava/lang/String;)V", "hash", "()Lokio/ByteString;", "mac", "Ljavax/crypto/Mac;", "messageDigest", "Ljava/security/MessageDigest;", "-deprecated_hash", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "Companion", "okio"}, k=1, mv={1, 1, 16})
public final class HashingSink
extends ForwardingSink {
    public static final Companion Companion = new Companion(null);
    private final Mac mac;
    private final MessageDigest messageDigest;

    public HashingSink(Sink sink2, String string2) {
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        Intrinsics.checkParameterIsNotNull(string2, "algorithm");
        super(sink2);
        this.messageDigest = MessageDigest.getInstance(string2);
        this.mac = null;
    }

    public HashingSink(Sink object, ByteString byteString, String string2) {
        Intrinsics.checkParameterIsNotNull(object, "sink");
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        Intrinsics.checkParameterIsNotNull(string2, "algorithm");
        super((Sink)object);
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
    public static final HashingSink hmacSha1(Sink sink2, ByteString byteString) {
        return Companion.hmacSha1(sink2, byteString);
    }

    @JvmStatic
    public static final HashingSink hmacSha256(Sink sink2, ByteString byteString) {
        return Companion.hmacSha256(sink2, byteString);
    }

    @JvmStatic
    public static final HashingSink hmacSha512(Sink sink2, ByteString byteString) {
        return Companion.hmacSha512(sink2, byteString);
    }

    @JvmStatic
    public static final HashingSink md5(Sink sink2) {
        return Companion.md5(sink2);
    }

    @JvmStatic
    public static final HashingSink sha1(Sink sink2) {
        return Companion.sha1(sink2);
    }

    @JvmStatic
    public static final HashingSink sha256(Sink sink2) {
        return Companion.sha256(sink2);
    }

    @JvmStatic
    public static final HashingSink sha512(Sink sink2) {
        return Companion.sha512(sink2);
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
    public void write(Buffer buffer, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        _Util.checkOffsetAndCount(buffer.size(), 0L, l);
        Object object = buffer.head;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        long l2 = 0L;
        do {
            if (l2 >= l) {
                super.write(buffer, l);
                return;
            }
            int n = (int)Math.min(l - l2, (long)(((Segment)object).limit - ((Segment)object).pos));
            Object object2 = this.messageDigest;
            if (object2 != null) {
                ((MessageDigest)object2).update(((Segment)object).data, ((Segment)object).pos, n);
            } else {
                object2 = this.mac;
                if (object2 == null) {
                    Intrinsics.throwNpe();
                }
                ((Mac)object2).update(((Segment)object).data, ((Segment)object).pos, n);
            }
            long l3 = l2 + (long)n;
            object = object2 = ((Segment)object).next;
            l2 = l3;
            if (object2 != null) continue;
            Intrinsics.throwNpe();
            object = object2;
            l2 = l3;
        } while (true);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u000f"}, d2={"Lokio/HashingSink$Companion;", "", "()V", "hmacSha1", "Lokio/HashingSink;", "sink", "Lokio/Sink;", "key", "Lokio/ByteString;", "hmacSha256", "hmacSha512", "md5", "sha1", "sha256", "sha512", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final HashingSink hmacSha1(Sink sink2, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            Intrinsics.checkParameterIsNotNull(byteString, "key");
            return new HashingSink(sink2, byteString, "HmacSHA1");
        }

        @JvmStatic
        public final HashingSink hmacSha256(Sink sink2, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            Intrinsics.checkParameterIsNotNull(byteString, "key");
            return new HashingSink(sink2, byteString, "HmacSHA256");
        }

        @JvmStatic
        public final HashingSink hmacSha512(Sink sink2, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            Intrinsics.checkParameterIsNotNull(byteString, "key");
            return new HashingSink(sink2, byteString, "HmacSHA512");
        }

        @JvmStatic
        public final HashingSink md5(Sink sink2) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            return new HashingSink(sink2, "MD5");
        }

        @JvmStatic
        public final HashingSink sha1(Sink sink2) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            return new HashingSink(sink2, "SHA-1");
        }

        @JvmStatic
        public final HashingSink sha256(Sink sink2) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            return new HashingSink(sink2, "SHA-256");
        }

        @JvmStatic
        public final HashingSink sha512(Sink sink2) {
            Intrinsics.checkParameterIsNotNull(sink2, "sink");
            return new HashingSink(sink2, "SHA-512");
        }
    }

}

