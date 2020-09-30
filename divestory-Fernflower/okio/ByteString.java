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
import okio.internal.ByteStringKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u001a\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 Z2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002:\u0001ZB\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0016J\u0011\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0000H\u0096\u0002J\u0015\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u001dJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0000J\u0013\u0010!\u001a\u00020\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\"H\u0096\u0002J\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b&J\u0015\u0010&\u001a\u00020$2\u0006\u0010%\u001a\u00020\tH\u0007¢\u0006\u0002\b'J\r\u0010(\u001a\u00020\tH\u0010¢\u0006\u0002\b)J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010*\u001a\u00020\u0010H\u0016J\u001d\u0010+\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u00102\u0006\u0010,\u001a\u00020\u0000H\u0010¢\u0006\u0002\b-J\u0010\u0010.\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0000H\u0016J\u0010\u0010/\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0000H\u0016J\u0010\u00100\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u0000H\u0016J\u001a\u00101\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00042\b\b\u0002\u00102\u001a\u00020\tH\u0017J\u001a\u00101\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u00102\u001a\u00020\tH\u0007J\r\u00103\u001a\u00020\u0004H\u0010¢\u0006\u0002\b4J\u0015\u00105\u001a\u00020$2\u0006\u00106\u001a\u00020\tH\u0010¢\u0006\u0002\b7J\u001a\u00108\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00042\b\b\u0002\u00102\u001a\u00020\tH\u0017J\u001a\u00108\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u00102\u001a\u00020\tH\u0007J\b\u00109\u001a\u00020\u0000H\u0016J(\u0010:\u001a\u00020\u001f2\u0006\u0010;\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\tH\u0016J(\u0010:\u001a\u00020\u001f2\u0006\u0010;\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00002\u0006\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\tH\u0016J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0002J\b\u0010B\u001a\u00020\u0000H\u0016J\b\u0010C\u001a\u00020\u0000H\u0016J\b\u0010D\u001a\u00020\u0000H\u0016J\r\u0010\u000e\u001a\u00020\tH\u0007¢\u0006\u0002\bEJ\u000e\u0010F\u001a\u00020\u001f2\u0006\u0010G\u001a\u00020\u0004J\u000e\u0010F\u001a\u00020\u001f2\u0006\u0010G\u001a\u00020\u0000J\u0010\u0010H\u001a\u00020\u00102\u0006\u0010I\u001a\u00020JH\u0016J\u001c\u0010K\u001a\u00020\u00002\b\b\u0002\u0010L\u001a\u00020\t2\b\b\u0002\u0010M\u001a\u00020\tH\u0017J\b\u0010N\u001a\u00020\u0000H\u0016J\b\u0010O\u001a\u00020\u0000H\u0016J\b\u0010P\u001a\u00020\u0004H\u0016J\b\u0010Q\u001a\u00020\u0010H\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010R\u001a\u00020?2\u0006\u0010S\u001a\u00020TH\u0016J%\u0010R\u001a\u00020?2\u0006\u0010U\u001a\u00020V2\u0006\u0010;\u001a\u00020\t2\u0006\u0010=\u001a\u00020\tH\u0010¢\u0006\u0002\bWJ\u0010\u0010X\u001a\u00020?2\u0006\u0010S\u001a\u00020YH\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\t8G¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006["},
   d2 = {"Lokio/ByteString;", "Ljava/io/Serializable;", "", "data", "", "([B)V", "getData$okio", "()[B", "hashCode", "", "getHashCode$okio", "()I", "setHashCode$okio", "(I)V", "size", "utf8", "", "getUtf8$okio", "()Ljava/lang/String;", "setUtf8$okio", "(Ljava/lang/String;)V", "asByteBuffer", "Ljava/nio/ByteBuffer;", "base64", "base64Url", "compareTo", "other", "digest", "algorithm", "digest$okio", "endsWith", "", "suffix", "equals", "", "get", "", "index", "getByte", "-deprecated_getByte", "getSize", "getSize$okio", "hex", "hmac", "key", "hmac$okio", "hmacSha1", "hmacSha256", "hmacSha512", "indexOf", "fromIndex", "internalArray", "internalArray$okio", "internalGet", "pos", "internalGet$okio", "lastIndexOf", "md5", "rangeEquals", "offset", "otherOffset", "byteCount", "readObject", "", "in", "Ljava/io/ObjectInputStream;", "sha1", "sha256", "sha512", "-deprecated_size", "startsWith", "prefix", "string", "charset", "Ljava/nio/charset/Charset;", "substring", "beginIndex", "endIndex", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toString", "write", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$okio", "writeObject", "Ljava/io/ObjectOutputStream;", "Companion", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public class ByteString implements Serializable, Comparable<ByteString> {
   public static final ByteString.Companion Companion = new ByteString.Companion((DefaultConstructorMarker)null);
   public static final ByteString EMPTY = new ByteString(new byte[0]);
   private static final long serialVersionUID = 1L;
   private final byte[] data;
   private transient int hashCode;
   private transient String utf8;

   public ByteString(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "data");
      super();
      this.data = var1;
   }

   @JvmStatic
   public static final ByteString decodeBase64(String var0) {
      return Companion.decodeBase64(var0);
   }

   @JvmStatic
   public static final ByteString decodeHex(String var0) {
      return Companion.decodeHex(var0);
   }

   @JvmStatic
   public static final ByteString encodeString(String var0, Charset var1) {
      return Companion.encodeString(var0, var1);
   }

   @JvmStatic
   public static final ByteString encodeUtf8(String var0) {
      return Companion.encodeUtf8(var0);
   }

   // $FF: synthetic method
   public static int indexOf$default(ByteString var0, ByteString var1, int var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = 0;
         }

         return var0.indexOf(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
      }
   }

   // $FF: synthetic method
   public static int indexOf$default(ByteString var0, byte[] var1, int var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = 0;
         }

         return var0.indexOf(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
      }
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(ByteString var0, ByteString var1, int var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = var0.size();
         }

         return var0.lastIndexOf(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
      }
   }

   // $FF: synthetic method
   public static int lastIndexOf$default(ByteString var0, byte[] var1, int var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 2) != 0) {
            var2 = var0.size();
         }

         return var0.lastIndexOf(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
      }
   }

   @JvmStatic
   public static final ByteString of(ByteBuffer var0) {
      return Companion.of(var0);
   }

   @JvmStatic
   public static final ByteString of(byte... var0) {
      return Companion.of(var0);
   }

   @JvmStatic
   public static final ByteString of(byte[] var0, int var1, int var2) {
      return Companion.of(var0, var1, var2);
   }

   @JvmStatic
   public static final ByteString read(InputStream var0, int var1) throws IOException {
      return Companion.read(var0, var1);
   }

   private final void readObject(ObjectInputStream var1) throws IOException {
      int var2 = var1.readInt();
      ByteString var3 = Companion.read((InputStream)var1, var2);
      Field var4 = ByteString.class.getDeclaredField("data");
      Intrinsics.checkExpressionValueIsNotNull(var4, "field");
      var4.setAccessible(true);
      var4.set(this, var3.data);
   }

   // $FF: synthetic method
   public static ByteString substring$default(ByteString var0, int var1, int var2, int var3, Object var4) {
      if (var4 == null) {
         if ((var3 & 1) != 0) {
            var1 = 0;
         }

         if ((var3 & 2) != 0) {
            var2 = var0.size();
         }

         return var0.substring(var1, var2);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: substring");
      }
   }

   private final void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeInt(this.data.length);
      var1.write(this.data);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to operator function",
      replaceWith = @ReplaceWith(
   expression = "this[index]",
   imports = {}
)
   )
   public final byte _deprecated_getByte/* $FF was: -deprecated_getByte*/(int var1) {
      return this.getByte(var1);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "size",
   imports = {}
)
   )
   public final int _deprecated_size/* $FF was: -deprecated_size*/() {
      return this.size();
   }

   public ByteBuffer asByteBuffer() {
      ByteBuffer var1 = ByteBuffer.wrap(this.data).asReadOnlyBuffer();
      Intrinsics.checkExpressionValueIsNotNull(var1, "ByteBuffer.wrap(data).asReadOnlyBuffer()");
      return var1;
   }

   public String base64() {
      return _Base64.encodeBase64$default(this.getData$okio(), (byte[])null, 1, (Object)null);
   }

   public String base64Url() {
      return _Base64.encodeBase64(this.getData$okio(), _Base64.getBASE64_URL_SAFE());
   }

   public int compareTo(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var2 = this.size();
      int var3 = var1.size();
      int var4 = Math.min(var2, var3);
      byte var5 = 0;
      int var6 = 0;

      byte var9;
      while(true) {
         if (var6 < var4) {
            int var7 = this.getByte(var6) & 255;
            int var8 = var1.getByte(var6) & 255;
            if (var7 == var8) {
               ++var6;
               continue;
            }

            if (var7 < var8) {
               break;
            }
         } else {
            if (var2 == var3) {
               var9 = var5;
               return var9;
            }

            if (var2 < var3) {
               break;
            }
         }

         var9 = 1;
         return var9;
      }

      var9 = -1;
      return var9;
   }

   public ByteString digest$okio(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "algorithm");
      byte[] var2 = MessageDigest.getInstance(var1).digest(this.data);
      Intrinsics.checkExpressionValueIsNotNull(var2, "MessageDigest.getInstance(algorithm).digest(data)");
      return new ByteString(var2);
   }

   public final boolean endsWith(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return this.rangeEquals(this.size() - var1.size(), (ByteString)var1, 0, var1.size());
   }

   public final boolean endsWith(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "suffix");
      return this.rangeEquals(this.size() - var1.length, (byte[])var1, 0, var1.length);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 != this) {
         if (var1 instanceof ByteString) {
            ByteString var3 = (ByteString)var1;
            if (var3.size() == this.getData$okio().length && var3.rangeEquals(0, (byte[])this.getData$okio(), 0, this.getData$okio().length)) {
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public final byte getByte(int var1) {
      return this.internalGet$okio(var1);
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
      int var1 = this.getHashCode$okio();
      if (var1 == 0) {
         var1 = Arrays.hashCode(this.getData$okio());
         this.setHashCode$okio(var1);
      }

      return var1;
   }

   public String hex() {
      char[] var1 = new char[this.getData$okio().length * 2];
      byte[] var2 = this.getData$okio();
      int var3 = var2.length;
      int var4 = 0;

      for(int var5 = 0; var4 < var3; ++var4) {
         byte var6 = var2[var4];
         int var7 = var5 + 1;
         var1[var5] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var6 >> 4 & 15];
         var5 = var7 + 1;
         var1[var7] = (char)ByteStringKt.getHEX_DIGIT_CHARS()[var6 & 15];
      }

      return new String(var1);
   }

   public ByteString hmac$okio(String var1, ByteString var2) {
      Intrinsics.checkParameterIsNotNull(var1, "algorithm");
      Intrinsics.checkParameterIsNotNull(var2, "key");

      try {
         Mac var3 = Mac.getInstance(var1);
         SecretKeySpec var4 = new SecretKeySpec(var2.toByteArray(), var1);
         var3.init((Key)var4);
         byte[] var6 = var3.doFinal(this.data);
         Intrinsics.checkExpressionValueIsNotNull(var6, "mac.doFinal(data)");
         ByteString var7 = new ByteString(var6);
         return var7;
      } catch (InvalidKeyException var5) {
         throw (Throwable)(new IllegalArgumentException((Throwable)var5));
      }
   }

   public ByteString hmacSha1(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return this.hmac$okio("HmacSHA1", var1);
   }

   public ByteString hmacSha256(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return this.hmac$okio("HmacSHA256", var1);
   }

   public ByteString hmacSha512(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return this.hmac$okio("HmacSHA512", var1);
   }

   public final int indexOf(ByteString var1) {
      return indexOf$default(this, (ByteString)var1, 0, 2, (Object)null);
   }

   public final int indexOf(ByteString var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return this.indexOf(var1.internalArray$okio(), var2);
   }

   public int indexOf(byte[] var1) {
      return indexOf$default(this, (byte[])var1, 0, 2, (Object)null);
   }

   public int indexOf(byte[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      int var3 = this.getData$okio().length - var1.length;
      var2 = Math.max(var2, 0);
      if (var2 <= var3) {
         while(true) {
            if (_Util.arrayRangeEquals(this.getData$okio(), var2, var1, 0, var1.length)) {
               return var2;
            }

            if (var2 == var3) {
               break;
            }

            ++var2;
         }
      }

      var2 = -1;
      return var2;
   }

   public byte[] internalArray$okio() {
      return this.getData$okio();
   }

   public byte internalGet$okio(int var1) {
      return this.getData$okio()[var1];
   }

   public final int lastIndexOf(ByteString var1) {
      return lastIndexOf$default(this, (ByteString)var1, 0, 2, (Object)null);
   }

   public final int lastIndexOf(ByteString var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return this.lastIndexOf(var1.internalArray$okio(), var2);
   }

   public int lastIndexOf(byte[] var1) {
      return lastIndexOf$default(this, (byte[])var1, 0, 2, (Object)null);
   }

   public int lastIndexOf(byte[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      var2 = Math.min(var2, this.getData$okio().length - var1.length);

      while(true) {
         if (var2 < 0) {
            var2 = -1;
            break;
         }

         if (_Util.arrayRangeEquals(this.getData$okio(), var2, var1, 0, var1.length)) {
            break;
         }

         --var2;
      }

      return var2;
   }

   public ByteString md5() {
      return this.digest$okio("MD5");
   }

   public boolean rangeEquals(int var1, ByteString var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var2, "other");
      return var2.rangeEquals(var3, this.getData$okio(), var1, var4);
   }

   public boolean rangeEquals(int var1, byte[] var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var2, "other");
      boolean var5;
      if (var1 >= 0 && var1 <= this.getData$okio().length - var4 && var3 >= 0 && var3 <= var2.length - var4 && _Util.arrayRangeEquals(this.getData$okio(), var1, var2, var3, var4)) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public final void setHashCode$okio(int var1) {
      this.hashCode = var1;
   }

   public final void setUtf8$okio(String var1) {
      this.utf8 = var1;
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

   public final boolean startsWith(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return this.rangeEquals(0, (ByteString)var1, 0, var1.size());
   }

   public final boolean startsWith(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "prefix");
      return this.rangeEquals(0, (byte[])var1, 0, var1.length);
   }

   public String string(Charset var1) {
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      return new String(this.data, var1);
   }

   public ByteString substring() {
      return substring$default(this, 0, 0, 3, (Object)null);
   }

   public ByteString substring(int var1) {
      return substring$default(this, var1, 0, 2, (Object)null);
   }

   public ByteString substring(int var1, int var2) {
      boolean var3 = true;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (var2 <= this.getData$okio().length) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            if (var2 - var1 >= 0) {
               var4 = var3;
            } else {
               var4 = false;
            }

            if (!var4) {
               throw (Throwable)(new IllegalArgumentException("endIndex < beginIndex".toString()));
            } else {
               ByteString var6;
               if (var1 == 0 && var2 == this.getData$okio().length) {
                  var6 = this;
               } else {
                  var6 = new ByteString(ArraysKt.copyOfRange(this.getData$okio(), var1, var2));
               }

               return var6;
            }
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("endIndex > length(");
            var5.append(this.getData$okio().length);
            var5.append(')');
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("beginIndex < 0".toString()));
      }
   }

   public ByteString toAsciiLowercase() {
      int var1 = 0;

      ByteString var7;
      while(true) {
         if (var1 >= this.getData$okio().length) {
            var7 = this;
            break;
         }

         byte var2 = this.getData$okio()[var1];
         byte var3 = (byte)65;
         if (var2 >= var3) {
            byte var4 = (byte)90;
            if (var2 <= var4) {
               byte[] var5 = this.getData$okio();
               var5 = Arrays.copyOf(var5, var5.length);
               Intrinsics.checkExpressionValueIsNotNull(var5, "java.util.Arrays.copyOf(this, size)");
               int var6 = var1 + 1;
               var5[var1] = (byte)((byte)(var2 + 32));

               for(var1 = var6; var1 < var5.length; ++var1) {
                  byte var8 = var5[var1];
                  if (var8 >= var3 && var8 <= var4) {
                     var5[var1] = (byte)((byte)(var8 + 32));
                  }
               }

               var7 = new ByteString(var5);
               break;
            }
         }

         ++var1;
      }

      return var7;
   }

   public ByteString toAsciiUppercase() {
      int var1 = 0;

      ByteString var7;
      while(true) {
         if (var1 >= this.getData$okio().length) {
            var7 = this;
            break;
         }

         byte var2 = this.getData$okio()[var1];
         byte var3 = (byte)97;
         if (var2 >= var3) {
            byte var4 = (byte)122;
            if (var2 <= var4) {
               byte[] var5 = this.getData$okio();
               var5 = Arrays.copyOf(var5, var5.length);
               Intrinsics.checkExpressionValueIsNotNull(var5, "java.util.Arrays.copyOf(this, size)");
               int var6 = var1 + 1;
               var5[var1] = (byte)((byte)(var2 - 32));

               for(var1 = var6; var1 < var5.length; ++var1) {
                  byte var8 = var5[var1];
                  if (var8 >= var3 && var8 <= var4) {
                     var5[var1] = (byte)((byte)(var8 - 32));
                  }
               }

               var7 = new ByteString(var5);
               break;
            }
         }

         ++var1;
      }

      return var7;
   }

   public byte[] toByteArray() {
      byte[] var1 = this.getData$okio();
      var1 = Arrays.copyOf(var1, var1.length);
      Intrinsics.checkExpressionValueIsNotNull(var1, "java.util.Arrays.copyOf(this, size)");
      return var1;
   }

   public String toString() {
      int var1 = this.getData$okio().length;
      boolean var2 = true;
      boolean var5;
      if (var1 == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      String var3;
      if (var5) {
         var3 = "[size=0]";
      } else {
         var1 = ByteStringKt.access$codePointIndexToCharIndex(this.getData$okio(), 64);
         StringBuilder var4;
         if (var1 == -1) {
            StringBuilder var6;
            if (this.getData$okio().length <= 64) {
               var6 = new StringBuilder();
               var6.append("[hex=");
               var6.append(this.hex());
               var6.append(']');
               var3 = var6.toString();
            } else {
               var4 = new StringBuilder();
               var4.append("[size=");
               var4.append(this.getData$okio().length);
               var4.append(" hex=");
               if (64 <= this.getData$okio().length) {
                  var5 = var2;
               } else {
                  var5 = false;
               }

               if (!var5) {
                  var6 = new StringBuilder();
                  var6.append("endIndex > length(");
                  var6.append(this.getData$okio().length);
                  var6.append(')');
                  throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
               }

               ByteString var7;
               if (64 == this.getData$okio().length) {
                  var7 = this;
               } else {
                  var7 = new ByteString(ArraysKt.copyOfRange(this.getData$okio(), 0, 64));
               }

               var4.append(var7.hex());
               var4.append("…]");
               var3 = var4.toString();
            }
         } else {
            String var8 = this.utf8();
            if (var8 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var3 = var8.substring(0, var1);
            Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            var3 = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(var3, "\\", "\\\\", false, 4, (Object)null), "\n", "\\n", false, 4, (Object)null), "\r", "\\r", false, 4, (Object)null);
            if (var1 < var8.length()) {
               var4 = new StringBuilder();
               var4.append("[size=");
               var4.append(this.getData$okio().length);
               var4.append(" text=");
               var4.append(var3);
               var4.append("…]");
               var3 = var4.toString();
            } else {
               var4 = new StringBuilder();
               var4.append("[text=");
               var4.append(var3);
               var4.append(']');
               var3 = var4.toString();
            }
         }
      }

      return var3;
   }

   public String utf8() {
      String var1 = this.getUtf8$okio();
      String var2 = var1;
      if (var1 == null) {
         var2 = _Platform.toUtf8String(this.internalArray$okio());
         this.setUtf8$okio(var2);
      }

      return var2;
   }

   public void write(OutputStream var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      var1.write(this.data);
   }

   public void write$okio(Buffer var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      ByteStringKt.commonWrite(this, var1, var2, var3);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b\nJ\u0015\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b\fJ\u001d\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007¢\u0006\u0002\b\u0010J\u0015\u0010\u0011\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b\u0012J\u0015\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H\u0007¢\u0006\u0002\b\u0016J\u0014\u0010\u0013\u001a\u00020\u00042\n\u0010\u0017\u001a\u00020\u0018\"\u00020\u0019H\u0007J%\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0007¢\u0006\u0002\b\u0016J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001cH\u0007¢\u0006\u0002\b!J\u000e\u0010\u0007\u001a\u0004\u0018\u00010\u0004*\u00020\tH\u0007J\f\u0010\u000b\u001a\u00020\u0004*\u00020\tH\u0007J\u001b\u0010\"\u001a\u00020\u0004*\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007¢\u0006\u0002\b\rJ\f\u0010\u0011\u001a\u00020\u0004*\u00020\tH\u0007J\u0019\u0010#\u001a\u00020\u0004*\u00020 2\u0006\u0010\u001d\u001a\u00020\u001cH\u0007¢\u0006\u0002\b\u001eJ\u0011\u0010$\u001a\u00020\u0004*\u00020\u0015H\u0007¢\u0006\u0002\b\u0013J%\u0010$\u001a\u00020\u0004*\u00020\u00182\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001cH\u0007¢\u0006\u0002\b\u0013R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006%"},
      d2 = {"Lokio/ByteString$Companion;", "", "()V", "EMPTY", "Lokio/ByteString;", "serialVersionUID", "", "decodeBase64", "string", "", "-deprecated_decodeBase64", "decodeHex", "-deprecated_decodeHex", "encodeString", "charset", "Ljava/nio/charset/Charset;", "-deprecated_encodeString", "encodeUtf8", "-deprecated_encodeUtf8", "of", "buffer", "Ljava/nio/ByteBuffer;", "-deprecated_of", "data", "", "", "array", "offset", "", "byteCount", "read", "inputstream", "Ljava/io/InputStream;", "-deprecated_read", "encode", "readByteString", "toByteString", "okio"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $FF: synthetic method
      public static ByteString encodeString$default(ByteString.Companion var0, String var1, Charset var2, int var3, Object var4) {
         if ((var3 & 1) != 0) {
            var2 = Charsets.UTF_8;
         }

         return var0.encodeString(var1, var2);
      }

      // $FF: synthetic method
      public static ByteString of$default(ByteString.Companion var0, byte[] var1, int var2, int var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var2 = 0;
         }

         if ((var4 & 2) != 0) {
            var3 = var1.length;
         }

         return var0.of(var1, var2, var3);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "string.decodeBase64()",
   imports = {"okio.ByteString.Companion.decodeBase64"}
)
      )
      public final ByteString _deprecated_decodeBase64/* $FF was: -deprecated_decodeBase64*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "string");
         return ((ByteString.Companion)this).decodeBase64(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "string.decodeHex()",
   imports = {"okio.ByteString.Companion.decodeHex"}
)
      )
      public final ByteString _deprecated_decodeHex/* $FF was: -deprecated_decodeHex*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "string");
         return ((ByteString.Companion)this).decodeHex(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "string.encode(charset)",
   imports = {"okio.ByteString.Companion.encode"}
)
      )
      public final ByteString _deprecated_encodeString/* $FF was: -deprecated_encodeString*/(String var1, Charset var2) {
         Intrinsics.checkParameterIsNotNull(var1, "string");
         Intrinsics.checkParameterIsNotNull(var2, "charset");
         return ((ByteString.Companion)this).encodeString(var1, var2);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "string.encodeUtf8()",
   imports = {"okio.ByteString.Companion.encodeUtf8"}
)
      )
      public final ByteString _deprecated_encodeUtf8/* $FF was: -deprecated_encodeUtf8*/(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "string");
         return ((ByteString.Companion)this).encodeUtf8(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "buffer.toByteString()",
   imports = {"okio.ByteString.Companion.toByteString"}
)
      )
      public final ByteString _deprecated_of/* $FF was: -deprecated_of*/(ByteBuffer var1) {
         Intrinsics.checkParameterIsNotNull(var1, "buffer");
         return ((ByteString.Companion)this).of(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "array.toByteString(offset, byteCount)",
   imports = {"okio.ByteString.Companion.toByteString"}
)
      )
      public final ByteString _deprecated_of/* $FF was: -deprecated_of*/(byte[] var1, int var2, int var3) {
         Intrinsics.checkParameterIsNotNull(var1, "array");
         return ((ByteString.Companion)this).of(var1, var2, var3);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "inputstream.readByteString(byteCount)",
   imports = {"okio.ByteString.Companion.readByteString"}
)
      )
      public final ByteString _deprecated_read/* $FF was: -deprecated_read*/(InputStream var1, int var2) {
         Intrinsics.checkParameterIsNotNull(var1, "inputstream");
         return ((ByteString.Companion)this).read(var1, var2);
      }

      @JvmStatic
      public final ByteString decodeBase64(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$decodeBase64");
         byte[] var2 = _Base64.decodeBase64ToArray(var1);
         ByteString var3;
         if (var2 != null) {
            var3 = new ByteString(var2);
         } else {
            var3 = null;
         }

         return var3;
      }

      @JvmStatic
      public final ByteString decodeHex(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$decodeHex");
         int var2 = var1.length();
         byte var3 = 0;
         boolean var6;
         if (var2 % 2 == 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var6) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Unexpected hex string: ");
            var8.append(var1);
            throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
         } else {
            int var4 = var1.length() / 2;
            byte[] var5 = new byte[var4];

            for(var2 = var3; var2 < var4; ++var2) {
               int var7 = var2 * 2;
               var5[var2] = (byte)((byte)((ByteStringKt.access$decodeHexDigit(var1.charAt(var7)) << 4) + ByteStringKt.access$decodeHexDigit(var1.charAt(var7 + 1))));
            }

            return new ByteString(var5);
         }
      }

      @JvmStatic
      public final ByteString encodeString(String var1, Charset var2) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$encode");
         Intrinsics.checkParameterIsNotNull(var2, "charset");
         byte[] var3 = var1.getBytes(var2);
         Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.String).getBytes(charset)");
         return new ByteString(var3);
      }

      @JvmStatic
      public final ByteString encodeUtf8(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$encodeUtf8");
         ByteString var2 = new ByteString(_Platform.asUtf8ToByteArray(var1));
         var2.setUtf8$okio(var1);
         return var2;
      }

      @JvmStatic
      public final ByteString of(ByteBuffer var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toByteString");
         byte[] var2 = new byte[var1.remaining()];
         var1.get(var2);
         return new ByteString(var2);
      }

      @JvmStatic
      public final ByteString of(byte... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "data");
         var1 = Arrays.copyOf(var1, var1.length);
         Intrinsics.checkExpressionValueIsNotNull(var1, "java.util.Arrays.copyOf(this, size)");
         return new ByteString(var1);
      }

      @JvmStatic
      public final ByteString of(byte[] var1, int var2, int var3) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toByteString");
         _Util.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
         return new ByteString(ArraysKt.copyOfRange(var1, var2, var3 + var2));
      }

      @JvmStatic
      public final ByteString read(InputStream var1, int var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "$this$readByteString");
         byte var3 = 0;
         boolean var4;
         if (var2 >= 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            byte[] var5 = new byte[var2];

            int var7;
            for(int var8 = var3; var8 < var2; var8 += var7) {
               var7 = var1.read(var5, var8, var2 - var8);
               if (var7 == -1) {
                  throw (Throwable)(new EOFException());
               }
            }

            return new ByteString(var5);
         } else {
            StringBuilder var6 = new StringBuilder();
            var6.append("byteCount < 0: ");
            var6.append(var2);
            throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
         }
      }
   }
}
