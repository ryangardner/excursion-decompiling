/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio._Util;
import okio.internal.SegmentedByteStringKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0015\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0010H\u0010\u00a2\u0006\u0002\b\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\r\u0010\u0019\u001a\u00020\u001aH\u0010\u00a2\u0006\u0002\b\u001bJ\b\u0010\u001c\u001a\u00020\u001aH\u0016J\b\u0010\u001d\u001a\u00020\u0010H\u0016J\u001d\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0001H\u0010\u00a2\u0006\u0002\b J\u0018\u0010!\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u001aH\u0016J\r\u0010#\u001a\u00020\u0004H\u0010\u00a2\u0006\u0002\b$J\u0015\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001aH\u0010\u00a2\u0006\u0002\b(J\u0018\u0010)\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u001aH\u0016J(\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u001aH\u0016J(\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u001aH\u0016J\u0010\u0010.\u001a\u00020\u00102\u0006\u0010/\u001a\u000200H\u0016J\u0018\u00101\u001a\u00020\u00012\u0006\u00102\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\u001aH\u0016J\b\u00104\u001a\u00020\u0001H\u0016J\b\u00105\u001a\u00020\u0001H\u0016J\b\u00106\u001a\u00020\u0004H\u0016J\b\u00107\u001a\u00020\u0001H\u0002J\b\u00108\u001a\u00020\u0010H\u0016J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0016J%\u00109\u001a\u00020:2\u0006\u0010=\u001a\u00020>2\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u001aH\u0010\u00a2\u0006\u0002\b?J\b\u0010@\u001a\u00020AH\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u00a8\u0006B"}, d2={"Lokio/SegmentedByteString;", "Lokio/ByteString;", "segments", "", "", "directory", "", "([[B[I)V", "getDirectory$okio", "()[I", "getSegments$okio", "()[[B", "[[B", "asByteBuffer", "Ljava/nio/ByteBuffer;", "base64", "", "base64Url", "digest", "algorithm", "digest$okio", "equals", "", "other", "", "getSize", "", "getSize$okio", "hashCode", "hex", "hmac", "key", "hmac$okio", "indexOf", "fromIndex", "internalArray", "internalArray$okio", "internalGet", "", "pos", "internalGet$okio", "lastIndexOf", "rangeEquals", "offset", "otherOffset", "byteCount", "string", "charset", "Ljava/nio/charset/Charset;", "substring", "beginIndex", "endIndex", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toByteString", "toString", "write", "", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$okio", "writeReplace", "Ljava/lang/Object;", "okio"}, k=1, mv={1, 1, 16})
public final class SegmentedByteString
extends ByteString {
    private final transient int[] directory;
    private final transient byte[][] segments;

    public SegmentedByteString(byte[][] arrby, int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrby, "segments");
        Intrinsics.checkParameterIsNotNull(arrn, "directory");
        super(ByteString.EMPTY.getData$okio());
        this.segments = arrby;
        this.directory = arrn;
    }

    private final ByteString toByteString() {
        return new ByteString(this.toByteArray());
    }

    private final Object writeReplace() {
        ByteString byteString = this.toByteString();
        if (byteString == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
        return byteString;
    }

    @Override
    public ByteBuffer asByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.toByteArray()).asReadOnlyBuffer();
        Intrinsics.checkExpressionValueIsNotNull(byteBuffer, "ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer()");
        return byteBuffer;
    }

    @Override
    public String base64() {
        return this.toByteString().base64();
    }

    @Override
    public String base64Url() {
        return this.toByteString().base64Url();
    }

    @Override
    public ByteString digest$okio(String arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "algorithm");
        arrby = MessageDigest.getInstance((String)arrby);
        int n = ((Object[])this.getSegments$okio()).length;
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= n) {
                arrby = arrby.digest();
                Intrinsics.checkExpressionValueIsNotNull(arrby, "digest.digest()");
                return new ByteString(arrby);
            }
            int n4 = this.getDirectory$okio()[n + n2];
            int n5 = this.getDirectory$okio()[n2];
            arrby.update(this.getSegments$okio()[n2], n4, n5 - n3);
            ++n2;
            n3 = n5;
        } while (true);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return bl;
        }
        if (!(object instanceof ByteString)) return false;
        if (((ByteString)(object = (ByteString)object)).size() != this.size()) return false;
        if (!this.rangeEquals(0, (ByteString)object, 0, this.size())) return false;
        return bl;
    }

    public final int[] getDirectory$okio() {
        return this.directory;
    }

    public final byte[][] getSegments$okio() {
        return this.segments;
    }

    @Override
    public int getSize$okio() {
        return this.getDirectory$okio()[((Object[])this.getSegments$okio()).length - 1];
    }

    @Override
    public int hashCode() {
        int n = this.getHashCode$okio();
        if (n != 0) {
            return n;
        }
        int n2 = ((Object[])this.getSegments$okio()).length;
        int n3 = 0;
        n = 1;
        int n4 = 0;
        do {
            if (n3 >= n2) {
                this.setHashCode$okio(n);
                return n;
            }
            int n5 = this.getDirectory$okio()[n2 + n3];
            int n6 = this.getDirectory$okio()[n3];
            byte[] arrby = this.getSegments$okio()[n3];
            for (int i = n5; i < n6 - n4 + n5; ++i) {
                n = n * 31 + arrby[i];
            }
            ++n3;
            n4 = n6;
        } while (true);
    }

    @Override
    public String hex() {
        return this.toByteString().hex();
    }

    @Override
    public ByteString hmac$okio(String object, ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(object, "algorithm");
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        try {
            Mac mac = Mac.getInstance((String)object);
            SecretKeySpec secretKeySpec = new SecretKeySpec(byteString.toByteArray(), (String)object);
            mac.init(secretKeySpec);
            int n = ((Object[])this.getSegments$okio()).length;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                int n3 = this.getDirectory$okio()[n + i];
                int n4 = this.getDirectory$okio()[i];
                mac.update(this.getSegments$okio()[i], n3, n4 - n2);
                n2 = n4;
            }
            object = mac.doFinal();
            Intrinsics.checkExpressionValueIsNotNull(object, "mac.doFinal()");
            return new ByteString((byte[])object);
        }
        catch (InvalidKeyException invalidKeyException) {
            throw (Throwable)new IllegalArgumentException(invalidKeyException);
        }
    }

    @Override
    public int indexOf(byte[] arrby, int n) {
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        return this.toByteString().indexOf(arrby, n);
    }

    @Override
    public byte[] internalArray$okio() {
        return this.toByteArray();
    }

    @Override
    public byte internalGet$okio(int n) {
        _Util.checkOffsetAndCount(this.getDirectory$okio()[((Object[])this.getSegments$okio()).length - 1], n, 1L);
        int n2 = SegmentedByteStringKt.segment(this, n);
        int n3 = n2 == 0 ? 0 : this.getDirectory$okio()[n2 - 1];
        int n4 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + n2];
        return this.getSegments$okio()[n2][n - n3 + n4];
    }

    @Override
    public int lastIndexOf(byte[] arrby, int n) {
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        return this.toByteString().lastIndexOf(arrby, n);
    }

    @Override
    public boolean rangeEquals(int n, ByteString byteString, int n2, int n3) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(byteString, "other");
        boolean bl2 = bl = false;
        if (n < 0) return bl2;
        if (n > this.size() - n3) {
            return bl;
        }
        int n4 = n3 + n;
        int n5 = SegmentedByteStringKt.segment(this, n);
        n3 = n2;
        n2 = n5;
        while (n < n4) {
            n5 = n2 == 0 ? 0 : this.getDirectory$okio()[n2 - 1];
            int n6 = this.getDirectory$okio()[n2];
            int n7 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + n2];
            n6 = Math.min(n4, n6 - n5 + n5) - n;
            if (!byteString.rangeEquals(n3, this.getSegments$okio()[n2], n7 + (n - n5), n6)) {
                return bl;
            }
            n3 += n6;
            n += n6;
            ++n2;
        }
        return true;
    }

    @Override
    public boolean rangeEquals(int n, byte[] arrby, int n2, int n3) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        boolean bl2 = bl = false;
        if (n < 0) return bl2;
        bl2 = bl;
        if (n > this.size() - n3) return bl2;
        bl2 = bl;
        if (n2 < 0) return bl2;
        if (n2 > arrby.length - n3) {
            return bl;
        }
        int n4 = n3 + n;
        int n5 = SegmentedByteStringKt.segment(this, n);
        n3 = n;
        n = n5;
        while (n3 < n4) {
            n5 = n == 0 ? 0 : this.getDirectory$okio()[n - 1];
            int n6 = this.getDirectory$okio()[n];
            int n7 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + n];
            n6 = Math.min(n4, n6 - n5 + n5) - n3;
            if (!_Util.arrayRangeEquals(this.getSegments$okio()[n], n7 + (n3 - n5), arrby, n2, n6)) {
                return bl;
            }
            n2 += n6;
            n3 += n6;
            ++n;
        }
        return true;
    }

    @Override
    public String string(Charset charset) {
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        return this.toByteString().string(charset);
    }

    @Override
    public ByteString substring(int n, int n2) {
        int n3 = 0;
        int n4 = n >= 0 ? 1 : 0;
        if (n4 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("beginIndex=");
            stringBuilder.append(n);
            stringBuilder.append(" < 0");
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        n4 = n2 <= this.size() ? 1 : 0;
        if (n4 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex=");
            stringBuilder.append(n2);
            stringBuilder.append(" > length(");
            stringBuilder.append(this.size());
            stringBuilder.append(')');
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        int n5 = n2 - n;
        n4 = n5 >= 0 ? 1 : 0;
        if (n4 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex=");
            stringBuilder.append(n2);
            stringBuilder.append(" < beginIndex=");
            stringBuilder.append(n);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        if (n == n2) {
            return ByteString.EMPTY;
        }
        int n6 = SegmentedByteStringKt.segment(this, n);
        int n7 = SegmentedByteStringKt.segment(this, n2 - 1);
        byte[][] arrby = (byte[][])ArraysKt.copyOfRange((Object[])this.getSegments$okio(), n6, n7 + 1);
        Object object = (Object[])arrby;
        int[] arrn = new int[((Object[])object).length * 2];
        if (n6 <= n7) {
            n4 = n6;
            n2 = 0;
            do {
                arrn[n2] = Math.min(this.getDirectory$okio()[n4] - n, n5);
                arrn[n2 + ((Object[])object).length] = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + n4];
                if (n4 == n7) break;
                ++n4;
                ++n2;
            } while (true);
        }
        n2 = n6 == 0 ? n3 : this.getDirectory$okio()[n6 - 1];
        n4 = ((Object[])object).length;
        arrn[n4] = arrn[n4] + (n - n2);
        return new SegmentedByteString(arrby, arrn);
    }

    @Override
    public ByteString toAsciiLowercase() {
        return this.toByteString().toAsciiLowercase();
    }

    @Override
    public ByteString toAsciiUppercase() {
        return this.toByteString().toAsciiUppercase();
    }

    @Override
    public byte[] toByteArray() {
        byte[] arrby = new byte[this.size()];
        int n = ((Object[])this.getSegments$okio()).length;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        while (n2 < n) {
            int n5 = this.getDirectory$okio()[n + n2];
            int n6 = this.getDirectory$okio()[n2];
            byte[] arrby2 = this.getSegments$okio()[n2];
            n3 = n6 - n3;
            ArraysKt.copyInto(arrby2, arrby, n4, n5, n5 + n3);
            n4 += n3;
            ++n2;
            n3 = n6;
        }
        return arrby;
    }

    @Override
    public String toString() {
        return this.toByteString().toString();
    }

    @Override
    public void write(OutputStream outputStream2) throws IOException {
        Intrinsics.checkParameterIsNotNull(outputStream2, "out");
        int n = ((Object[])this.getSegments$okio()).length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = this.getDirectory$okio()[n + n2];
            int n5 = this.getDirectory$okio()[n2];
            outputStream2.write(this.getSegments$okio()[n2], n4, n5 - n3);
            ++n2;
            n3 = n5;
        }
    }

    @Override
    public void write$okio(Buffer buffer, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        int n3 = n2 + n;
        int n4 = SegmentedByteStringKt.segment(this, n);
        n2 = n;
        n = n4;
        do {
            if (n2 >= n3) {
                buffer.setSize$okio(buffer.size() + (long)this.size());
                return;
            }
            n4 = n == 0 ? 0 : this.getDirectory$okio()[n - 1];
            int n5 = this.getDirectory$okio()[n];
            int n6 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + n];
            n5 = Math.min(n3, n5 - n4 + n4) - n2;
            n4 = n6 + (n2 - n4);
            Segment segment = new Segment(this.getSegments$okio()[n], n4, n4 + n5, true, false);
            if (buffer.head == null) {
                buffer.head = segment.next = (segment.prev = segment);
            } else {
                Segment segment2 = buffer.head;
                if (segment2 == null) {
                    Intrinsics.throwNpe();
                }
                if ((segment2 = segment2.prev) == null) {
                    Intrinsics.throwNpe();
                }
                segment2.push(segment);
            }
            n2 += n5;
            ++n;
        } while (true);
    }
}

