/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okio.Buffer;
import okio._Base64;
import okio._Platform;
import okio._Util;
import okio.internal.ByteStringKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u001a\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 Z2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002:\u0001ZB\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0016J\u0011\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0000H\u0096\u0002J\u0015\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0010H\u0010\u00a2\u0006\u0002\b\u001dJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0000J\u0013\u0010!\u001a\u00020\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\"H\u0096\u0002J\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\tH\u0087\u0002\u00a2\u0006\u0002\b&J\u0015\u0010&\u001a\u00020$2\u0006\u0010%\u001a\u00020\tH\u0007\u00a2\u0006\u0002\b'J\r\u0010(\u001a\u00020\tH\u0010\u00a2\u0006\u0002\b)J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010*\u001a\u00020\u0010H\u0016J\u001d\u0010+\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u00102\u0006\u0010,\u001a\u00020\u0000H\u0010\u00a2\u0006\u0002\b-J\u0010\u0010.\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0000H\u0016J\u0010\u0010/\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0000H\u0016J\u0010\u00100\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0000H\u0016J\u001a\u00101\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00042\b\b\u0002\u00102\u001a\u00020\tH\u0017J\u001a\u00101\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u00102\u001a\u00020\tH\u0007J\r\u00103\u001a\u00020\u0004H\u0010\u00a2\u0006\u0002\b4J\u0015\u00105\u001a\u00020$2\u0006\u00106\u001a\u00020\tH\u0010\u00a2\u0006\u0002\b7J\u001a\u00108\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00042\b\b\u0002\u00102\u001a\u00020\tH\u0017J\u001a\u00108\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u00102\u001a\u00020\tH\u0007J\b\u00109\u001a\u00020\u0000H\u0016J(\u0010:\u001a\u00020\u001f2\u0006\u0010;\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\tH\u0016J(\u0010:\u001a\u00020\u001f2\u0006\u0010;\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00002\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\tH\u0016J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0002J\b\u0010B\u001a\u00020\u0000H\u0016J\b\u0010C\u001a\u00020\u0000H\u0016J\b\u0010D\u001a\u00020\u0000H\u0016J\r\u0010\u000e\u001a\u00020\tH\u0007\u00a2\u0006\u0002\bEJ\u000e\u0010F\u001a\u00020\u001f2\u0006\u0010G\u001a\u00020\u0004J\u000e\u0010F\u001a\u00020\u001f2\u0006\u0010G\u001a\u00020\u0000J\u0010\u0010H\u001a\u00020\u00102\u0006\u0010I\u001a\u00020JH\u0016J\u001c\u0010K\u001a\u00020\u00002\b\b\u0002\u0010L\u001a\u00020\t2\b\b\u0002\u0010M\u001a\u00020\tH\u0017J\b\u0010N\u001a\u00020\u0000H\u0016J\b\u0010O\u001a\u00020\u0000H\u0016J\b\u0010P\u001a\u00020\u0004H\u0016J\b\u0010Q\u001a\u00020\u0010H\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010R\u001a\u00020?2\u0006\u0010S\u001a\u00020TH\u0016J%\u0010R\u001a\u00020?2\u0006\u0010U\u001a\u00020V2\u0006\u0010;\u001a\u00020\t2\u0006\u0010=\u001a\u00020\tH\u0010\u00a2\u0006\u0002\bWJ\u0010\u0010X\u001a\u00020?2\u0006\u0010S\u001a\u00020YH\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\t8G\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006["}, d2={"Lokio/ByteString;", "Ljava/io/Serializable;", "", "data", "", "([B)V", "getData$okio", "()[B", "hashCode", "", "getHashCode$okio", "()I", "setHashCode$okio", "(I)V", "size", "utf8", "", "getUtf8$okio", "()Ljava/lang/String;", "setUtf8$okio", "(Ljava/lang/String;)V", "asByteBuffer", "Ljava/nio/ByteBuffer;", "base64", "base64Url", "compareTo", "other", "digest", "algorithm", "digest$okio", "endsWith", "", "suffix", "equals", "", "get", "", "index", "getByte", "-deprecated_getByte", "getSize", "getSize$okio", "hex", "hmac", "key", "hmac$okio", "hmacSha1", "hmacSha256", "hmacSha512", "indexOf", "fromIndex", "internalArray", "internalArray$okio", "internalGet", "pos", "internalGet$okio", "lastIndexOf", "md5", "rangeEquals", "offset", "otherOffset", "byteCount", "readObject", "", "in", "Ljava/io/ObjectInputStream;", "sha1", "sha256", "sha512", "-deprecated_size", "startsWith", "prefix", "string", "charset", "Ljava/nio/charset/Charset;", "substring", "beginIndex", "endIndex", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toString", "write", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$okio", "writeObject", "Ljava/io/ObjectOutputStream;", "Companion", "okio"}, k=1, mv={1, 1, 16})
public class ByteString
implements Serializable,
Comparable<ByteString> {
    public static final Companion Companion = new Companion(null);
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private static final long serialVersionUID = 1L;
    private final byte[] data;
    private transient int hashCode;
    private transient String utf8;

    public ByteString(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "data");
        this.data = arrby;
    }

    @JvmStatic
    public static final ByteString decodeBase64(String string2) {
        return Companion.decodeBase64(string2);
    }

    @JvmStatic
    public static final ByteString decodeHex(String string2) {
        return Companion.decodeHex(string2);
    }

    @JvmStatic
    public static final ByteString encodeString(String string2, Charset charset) {
        return Companion.encodeString(string2, charset);
    }

    @JvmStatic
    public static final ByteString encodeUtf8(String string2) {
        return Companion.encodeUtf8(string2);
    }

    public static /* synthetic */ int indexOf$default(ByteString byteString, ByteString byteString2, int n, int n2, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
        if ((n2 & 2) == 0) return byteString.indexOf(byteString2, n);
        n = 0;
        return byteString.indexOf(byteString2, n);
    }

    public static /* synthetic */ int indexOf$default(ByteString byteString, byte[] arrby, int n, int n2, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
        if ((n2 & 2) == 0) return byteString.indexOf(arrby, n);
        n = 0;
        return byteString.indexOf(arrby, n);
    }

    public static /* synthetic */ int lastIndexOf$default(ByteString byteString, ByteString byteString2, int n, int n2, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
        if ((n2 & 2) == 0) return byteString.lastIndexOf(byteString2, n);
        n = byteString.size();
        return byteString.lastIndexOf(byteString2, n);
    }

    public static /* synthetic */ int lastIndexOf$default(ByteString byteString, byte[] arrby, int n, int n2, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
        if ((n2 & 2) == 0) return byteString.lastIndexOf(arrby, n);
        n = byteString.size();
        return byteString.lastIndexOf(arrby, n);
    }

    @JvmStatic
    public static final ByteString of(ByteBuffer byteBuffer) {
        return Companion.of(byteBuffer);
    }

    @JvmStatic
    public static final ByteString of(byte ... arrby) {
        return Companion.of(arrby);
    }

    @JvmStatic
    public static final ByteString of(byte[] arrby, int n, int n2) {
        return Companion.of(arrby, n, n2);
    }

    @JvmStatic
    public static final ByteString read(InputStream inputStream2, int n) throws IOException {
        return Companion.read(inputStream2, n);
    }

    private final void readObject(ObjectInputStream object) throws IOException {
        int n = ((ObjectInputStream)object).readInt();
        ByteString byteString = Companion.read((InputStream)object, n);
        object = ByteString.class.getDeclaredField("data");
        Intrinsics.checkExpressionValueIsNotNull(object, "field");
        ((Field)object).setAccessible(true);
        ((Field)object).set(this, byteString.data);
    }

    public static /* synthetic */ ByteString substring$default(ByteString byteString, int n, int n2, int n3, Object object) {
        if (object != null) throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: substring");
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return byteString.substring(n, n2);
        n2 = byteString.size();
        return byteString.substring(n, n2);
    }

    private final void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to operator function", replaceWith=@ReplaceWith(expression="this[index]", imports={}))
    public final byte -deprecated_getByte(int n) {
        return this.getByte(n);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
    public final int -deprecated_size() {
        return this.size();
    }

    public ByteBuffer asByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.data).asReadOnlyBuffer();
        Intrinsics.checkExpressionValueIsNotNull(byteBuffer, "ByteBuffer.wrap(data).asReadOnlyBuffer()");
        return byteBuffer;
    }

    public String base64() {
        return _Base64.encodeBase64$default(this.getData$okio(), null, 1, null);
    }

    public String base64Url() {
        return _Base64.encodeBase64(this.getData$okio(), _Base64.getBASE64_URL_SAFE());
    }

    @Override
    public int compareTo(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "other");
        int n = this.size();
        int n2 = byteString.size();
        int n3 = Math.min(n, n2);
        int n4 = 0;
        for (int i = 0; i < n3; ++i) {
            int n5;
            int n6 = this.getByte(i) & 255;
            if (n6 == (n5 = byteString.getByte(i) & 255)) {
                continue;
            }
            if (n6 >= n5) return 1;
            return -1;
        }
        if (n == n2) {
            return n4;
        }
        if (n >= n2) return 1;
        return -1;
    }

    public ByteString digest$okio(String arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "algorithm");
        arrby = MessageDigest.getInstance((String)arrby).digest(this.data);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "MessageDigest.getInstance(algorithm).digest(data)");
        return new ByteString(arrby);
    }

    public final boolean endsWith(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "suffix");
        return this.rangeEquals(this.size() - byteString.size(), byteString, 0, byteString.size());
    }

    public final boolean endsWith(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "suffix");
        return this.rangeEquals(this.size() - arrby.length, arrby, 0, arrby.length);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return bl;
        }
        if (!(object instanceof ByteString)) return false;
        if (((ByteString)(object = (ByteString)object)).size() != this.getData$okio().length) return false;
        if (!((ByteString)object).rangeEquals(0, this.getData$okio(), 0, this.getData$okio().length)) return false;
        return bl;
    }

    public final byte getByte(int n) {
        return this.internalGet$okio(n);
    }

    public final byte[] getData$okio() {
        return this.data;
    }

    public final int getHashCode$okio() {
        return this.hashCode;
    }

    public int getSize$okio() {
        return this.getData$okio().length;
    }

    public final String getUtf8$okio() {
        return this.utf8;
    }

    public int hashCode() {
        int n = this.getHashCode$okio();
        if (n != 0) {
            return n;
        }
        n = Arrays.hashCode(this.getData$okio());
        this.setHashCode$okio(n);
        return n;
    }

    public String hex() {
        char[] arrc = new char[this.getData$okio().length * 2];
        byte[] arrby = this.getData$okio();
        int n = arrby.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            byte by = arrby[n2];
            int n4 = n3 + 1;
            arrc[n3] = ByteStringKt.getHEX_DIGIT_CHARS()[by >> 4 & 15];
            n3 = n4 + 1;
            arrc[n4] = ByteStringKt.getHEX_DIGIT_CHARS()[by & 15];
            ++n2;
        }
        return new String(arrc);
    }

    public ByteString hmac$okio(String object, ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(object, "algorithm");
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        try {
            Mac mac = Mac.getInstance((String)object);
            SecretKeySpec secretKeySpec = new SecretKeySpec(byteString.toByteArray(), (String)object);
            mac.init(secretKeySpec);
            object = mac.doFinal(this.data);
            Intrinsics.checkExpressionValueIsNotNull(object, "mac.doFinal(data)");
            return new ByteString((byte[])object);
        }
        catch (InvalidKeyException invalidKeyException) {
            throw (Throwable)new IllegalArgumentException(invalidKeyException);
        }
    }

    public ByteString hmacSha1(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        return this.hmac$okio("HmacSHA1", byteString);
    }

    public ByteString hmacSha256(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        return this.hmac$okio("HmacSHA256", byteString);
    }

    public ByteString hmacSha512(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "key");
        return this.hmac$okio("HmacSHA512", byteString);
    }

    public final int indexOf(ByteString byteString) {
        return ByteString.indexOf$default(this, byteString, 0, 2, null);
    }

    public final int indexOf(ByteString byteString, int n) {
        Intrinsics.checkParameterIsNotNull(byteString, "other");
        return this.indexOf(byteString.internalArray$okio(), n);
    }

    public int indexOf(byte[] arrby) {
        return ByteString.indexOf$default(this, arrby, 0, 2, null);
    }

    public int indexOf(byte[] arrby, int n) {
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        int n2 = this.getData$okio().length - arrby.length;
        n = Math.max(n, 0);
        if (n > n2) return -1;
        while (!_Util.arrayRangeEquals(this.getData$okio(), n, arrby, 0, arrby.length)) {
            if (n == n2) return -1;
            ++n;
        }
        return n;
    }

    public byte[] internalArray$okio() {
        return this.getData$okio();
    }

    public byte internalGet$okio(int n) {
        return this.getData$okio()[n];
    }

    public final int lastIndexOf(ByteString byteString) {
        return ByteString.lastIndexOf$default(this, byteString, 0, 2, null);
    }

    public final int lastIndexOf(ByteString byteString, int n) {
        Intrinsics.checkParameterIsNotNull(byteString, "other");
        return this.lastIndexOf(byteString.internalArray$okio(), n);
    }

    public int lastIndexOf(byte[] arrby) {
        return ByteString.lastIndexOf$default(this, arrby, 0, 2, null);
    }

    public int lastIndexOf(byte[] arrby, int n) {
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        n = Math.min(n, this.getData$okio().length - arrby.length);
        while (n >= 0) {
            if (_Util.arrayRangeEquals(this.getData$okio(), n, arrby, 0, arrby.length)) {
                return n;
            }
            --n;
        }
        return -1;
    }

    public ByteString md5() {
        return this.digest$okio("MD5");
    }

    public boolean rangeEquals(int n, ByteString byteString, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(byteString, "other");
        return byteString.rangeEquals(n2, this.getData$okio(), n, n3);
    }

    public boolean rangeEquals(int n, byte[] arrby, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        if (n < 0) return false;
        if (n > this.getData$okio().length - n3) return false;
        if (n2 < 0) return false;
        if (n2 > arrby.length - n3) return false;
        if (!_Util.arrayRangeEquals(this.getData$okio(), n, arrby, n2, n3)) return false;
        return true;
    }

    public final void setHashCode$okio(int n) {
        this.hashCode = n;
    }

    public final void setUtf8$okio(String string2) {
        this.utf8 = string2;
    }

    public ByteString sha1() {
        return this.digest$okio("SHA-1");
    }

    public ByteString sha256() {
        return this.digest$okio("SHA-256");
    }

    public ByteString sha512() {
        return this.digest$okio("SHA-512");
    }

    public final int size() {
        return this.getSize$okio();
    }

    public final boolean startsWith(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "prefix");
        return this.rangeEquals(0, byteString, 0, byteString.size());
    }

    public final boolean startsWith(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "prefix");
        return this.rangeEquals(0, arrby, 0, arrby.length);
    }

    public String string(Charset charset) {
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        return new String(this.data, charset);
    }

    public ByteString substring() {
        return ByteString.substring$default(this, 0, 0, 3, null);
    }

    public ByteString substring(int n) {
        return ByteString.substring$default(this, n, 0, 2, null);
    }

    public ByteString substring(int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        if (!bl2) throw (Throwable)new IllegalArgumentException("beginIndex < 0".toString());
        bl2 = n2 <= this.getData$okio().length;
        if (!bl2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex > length(");
            stringBuilder.append(this.getData$okio().length);
            stringBuilder.append(')');
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        bl2 = n2 - n >= 0 ? bl : false;
        if (!bl2) throw (Throwable)new IllegalArgumentException("endIndex < beginIndex".toString());
        if (n != 0) return new ByteString(ArraysKt.copyOfRange(this.getData$okio(), n, n2));
        if (n2 != this.getData$okio().length) return new ByteString(ArraysKt.copyOfRange(this.getData$okio(), n, n2));
        return this;
    }

    public ByteString toAsciiLowercase() {
        byte by;
        byte by2;
        byte by3;
        int n = 0;
        do {
            if (n >= this.getData$okio().length) {
                return this;
            }
            by = this.getData$okio()[n];
            if (by >= (by3 = (byte)65) && by <= (by2 = (byte)90)) break;
            ++n;
        } while (true);
        Object object = this.getData$okio();
        object = Arrays.copyOf(object, ((byte[])object).length);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.copyOf(this, size)");
        int n2 = n + 1;
        object[n] = (byte)(by + 32);
        n = n2;
        while (n < ((byte[])object).length) {
            n2 = object[n];
            if (n2 >= by3 && n2 <= by2) {
                object[n] = (byte)(n2 + 32);
            }
            ++n;
        }
        return new ByteString((byte[])object);
    }

    public ByteString toAsciiUppercase() {
        byte by;
        byte by2;
        byte by3;
        int n = 0;
        do {
            if (n >= this.getData$okio().length) {
                return this;
            }
            by = this.getData$okio()[n];
            if (by >= (by3 = (byte)97) && by <= (by2 = (byte)122)) break;
            ++n;
        } while (true);
        Object object = this.getData$okio();
        object = Arrays.copyOf(object, ((byte[])object).length);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.copyOf(this, size)");
        int n2 = n + 1;
        object[n] = (byte)(by - 32);
        n = n2;
        while (n < ((byte[])object).length) {
            n2 = object[n];
            if (n2 >= by3 && n2 <= by2) {
                object[n] = (byte)(n2 - 32);
            }
            ++n;
        }
        return new ByteString((byte[])object);
    }

    public byte[] toByteArray() {
        byte[] arrby = this.getData$okio();
        arrby = Arrays.copyOf(arrby, arrby.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        return arrby;
    }

    public String toString() {
        Object object;
        int n = this.getData$okio().length;
        int n2 = 1;
        if (n == 0) {
            return "[size=0]";
        }
        n = 0;
        if (n != 0) {
            return "[size=0]";
        }
        n = ByteStringKt.access$codePointIndexToCharIndex(this.getData$okio(), 64);
        if (n == -1) {
            if (this.getData$okio().length <= 64) {
                object = new StringBuilder();
                ((StringBuilder)object).append("[hex=");
                ((StringBuilder)object).append(this.hex());
                ((StringBuilder)object).append(']');
                return ((StringBuilder)object).toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[size=");
            stringBuilder.append(this.getData$okio().length);
            stringBuilder.append(" hex=");
            n = 64 <= this.getData$okio().length ? n2 : 0;
            if (n == 0) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("endIndex > length(");
                stringBuilder2.append(this.getData$okio().length);
                stringBuilder2.append(')');
                throw (Throwable)new IllegalArgumentException(stringBuilder2.toString().toString());
            }
            object = 64 == this.getData$okio().length ? this : new ByteString(ArraysKt.copyOfRange(this.getData$okio(), 0, 64));
            stringBuilder.append(((ByteString)object).hex());
            stringBuilder.append("\u2026]");
            return stringBuilder.toString();
        }
        CharSequence charSequence = this.utf8();
        if (charSequence == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        object = ((String)charSequence).substring(0, n);
        Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        object = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default((String)object, "\\", "\\\\", false, 4, null), "\n", "\\n", false, 4, null), "\r", "\\r", false, 4, null);
        if (n < ((String)charSequence).length()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[size=");
            ((StringBuilder)charSequence).append(this.getData$okio().length);
            ((StringBuilder)charSequence).append(" text=");
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("\u2026]");
            return ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("[text=");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(']');
        return ((StringBuilder)charSequence).toString();
    }

    public String utf8() {
        String string2;
        String string3 = string2 = this.getUtf8$okio();
        if (string2 != null) return string3;
        string3 = _Platform.toUtf8String(this.internalArray$okio());
        this.setUtf8$okio(string3);
        return string3;
    }

    public void write(OutputStream outputStream2) throws IOException {
        Intrinsics.checkParameterIsNotNull(outputStream2, "out");
        outputStream2.write(this.data);
    }

    public void write$okio(Buffer buffer, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        ByteStringKt.commonWrite(this, buffer, n, n2);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0017\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tH\u0007\u00a2\u0006\u0002\b\nJ\u0015\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007\u00a2\u0006\u0002\b\fJ\u001d\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\b\u0010J\u0015\u0010\u0011\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007\u00a2\u0006\u0002\b\u0012J\u0015\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H\u0007\u00a2\u0006\u0002\b\u0016J\u0014\u0010\u0013\u001a\u00020\u00042\n\u0010\u0017\u001a\u00020\u0018\"\u00020\u0019H\u0007J%\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0007\u00a2\u0006\u0002\b\u0016J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001cH\u0007\u00a2\u0006\u0002\b!J\u000e\u0010\u0007\u001a\u0004\u0018\u00010\u0004*\u00020\tH\u0007J\f\u0010\u000b\u001a\u00020\u0004*\u00020\tH\u0007J\u001b\u0010\"\u001a\u00020\u0004*\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u00a2\u0006\u0002\b\rJ\f\u0010\u0011\u001a\u00020\u0004*\u00020\tH\u0007J\u0019\u0010#\u001a\u00020\u0004*\u00020 2\u0006\u0010\u001d\u001a\u00020\u001cH\u0007\u00a2\u0006\u0002\b\u001eJ\u0011\u0010$\u001a\u00020\u0004*\u00020\u0015H\u0007\u00a2\u0006\u0002\b\u0013J%\u0010$\u001a\u00020\u0004*\u00020\u00182\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001cH\u0007\u00a2\u0006\u0002\b\u0013R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2={"Lokio/ByteString$Companion;", "", "()V", "EMPTY", "Lokio/ByteString;", "serialVersionUID", "", "decodeBase64", "string", "", "-deprecated_decodeBase64", "decodeHex", "-deprecated_decodeHex", "encodeString", "charset", "Ljava/nio/charset/Charset;", "-deprecated_encodeString", "encodeUtf8", "-deprecated_encodeUtf8", "of", "buffer", "Ljava/nio/ByteBuffer;", "-deprecated_of", "data", "", "", "array", "offset", "", "byteCount", "read", "inputstream", "Ljava/io/InputStream;", "-deprecated_read", "encode", "readByteString", "toByteString", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ ByteString encodeString$default(Companion companion, String string2, Charset charset, int n, Object object) {
            if ((n & 1) == 0) return companion.encodeString(string2, charset);
            charset = Charsets.UTF_8;
            return companion.encodeString(string2, charset);
        }

        public static /* synthetic */ ByteString of$default(Companion companion, byte[] arrby, int n, int n2, int n3, Object object) {
            if ((n3 & 1) != 0) {
                n = 0;
            }
            if ((n3 & 2) == 0) return companion.of(arrby, n, n2);
            n2 = arrby.length;
            return companion.of(arrby, n, n2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="string.decodeBase64()", imports={"okio.ByteString.Companion.decodeBase64"}))
        public final ByteString -deprecated_decodeBase64(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "string");
            return this.decodeBase64(string2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="string.decodeHex()", imports={"okio.ByteString.Companion.decodeHex"}))
        public final ByteString -deprecated_decodeHex(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "string");
            return this.decodeHex(string2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="string.encode(charset)", imports={"okio.ByteString.Companion.encode"}))
        public final ByteString -deprecated_encodeString(String string2, Charset charset) {
            Intrinsics.checkParameterIsNotNull(string2, "string");
            Intrinsics.checkParameterIsNotNull(charset, "charset");
            return this.encodeString(string2, charset);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="string.encodeUtf8()", imports={"okio.ByteString.Companion.encodeUtf8"}))
        public final ByteString -deprecated_encodeUtf8(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "string");
            return this.encodeUtf8(string2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="buffer.toByteString()", imports={"okio.ByteString.Companion.toByteString"}))
        public final ByteString -deprecated_of(ByteBuffer byteBuffer) {
            Intrinsics.checkParameterIsNotNull(byteBuffer, "buffer");
            return this.of(byteBuffer);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="array.toByteString(offset, byteCount)", imports={"okio.ByteString.Companion.toByteString"}))
        public final ByteString -deprecated_of(byte[] arrby, int n, int n2) {
            Intrinsics.checkParameterIsNotNull(arrby, "array");
            return this.of(arrby, n, n2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="inputstream.readByteString(byteCount)", imports={"okio.ByteString.Companion.readByteString"}))
        public final ByteString -deprecated_read(InputStream inputStream2, int n) {
            Intrinsics.checkParameterIsNotNull(inputStream2, "inputstream");
            return this.read(inputStream2, n);
        }

        @JvmStatic
        public final ByteString decodeBase64(String object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$decodeBase64");
            object = _Base64.decodeBase64ToArray((String)object);
            if (object == null) return null;
            return new ByteString((byte[])object);
        }

        @JvmStatic
        public final ByteString decodeHex(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$decodeHex");
            int n = string2.length();
            int n2 = 0;
            n = n % 2 == 0 ? 1 : 0;
            if (n == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected hex string: ");
                stringBuilder.append(string2);
                throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
            }
            int n3 = string2.length() / 2;
            byte[] arrby = new byte[n3];
            n = n2;
            while (n < n3) {
                n2 = n * 2;
                arrby[n] = (byte)((ByteStringKt.access$decodeHexDigit(string2.charAt(n2)) << 4) + ByteStringKt.access$decodeHexDigit(string2.charAt(n2 + 1)));
                ++n;
            }
            return new ByteString(arrby);
        }

        @JvmStatic
        public final ByteString encodeString(String arrby, Charset charset) {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$encode");
            Intrinsics.checkParameterIsNotNull(charset, "charset");
            arrby = arrby.getBytes(charset);
            Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
            return new ByteString(arrby);
        }

        @JvmStatic
        public final ByteString encodeUtf8(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$encodeUtf8");
            ByteString byteString = new ByteString(_Platform.asUtf8ToByteArray(string2));
            byteString.setUtf8$okio(string2);
            return byteString;
        }

        @JvmStatic
        public final ByteString of(ByteBuffer byteBuffer) {
            Intrinsics.checkParameterIsNotNull(byteBuffer, "$this$toByteString");
            byte[] arrby = new byte[byteBuffer.remaining()];
            byteBuffer.get(arrby);
            return new ByteString(arrby);
        }

        @JvmStatic
        public final ByteString of(byte ... arrby) {
            Intrinsics.checkParameterIsNotNull(arrby, "data");
            arrby = Arrays.copyOf(arrby, arrby.length);
            Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
            return new ByteString(arrby);
        }

        @JvmStatic
        public final ByteString of(byte[] arrby, int n, int n2) {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$toByteString");
            _Util.checkOffsetAndCount(arrby.length, n, n2);
            return new ByteString(ArraysKt.copyOfRange(arrby, n, n2 + n));
        }

        @JvmStatic
        public final ByteString read(InputStream object, int n) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "$this$readByteString");
            int n2 = 0;
            int n3 = n >= 0 ? 1 : 0;
            if (n3 == 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("byteCount < 0: ");
                ((StringBuilder)object).append(n);
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
            }
            byte[] arrby = new byte[n];
            n3 = n2;
            while (n3 < n) {
                n2 = ((InputStream)object).read(arrby, n3, n - n3);
                if (n2 == -1) throw (Throwable)new EOFException();
                n3 += n2;
            }
            return new ByteString(arrby);
        }
    }

}

