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

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00020\bH\u0007¢\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\n\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokio/HashingSink;", "Lokio/ForwardingSink;", "sink", "Lokio/Sink;", "algorithm", "", "(Lokio/Sink;Ljava/lang/String;)V", "key", "Lokio/ByteString;", "(Lokio/Sink;Lokio/ByteString;Ljava/lang/String;)V", "hash", "()Lokio/ByteString;", "mac", "Ljavax/crypto/Mac;", "messageDigest", "Ljava/security/MessageDigest;", "-deprecated_hash", "write", "", "source", "Lokio/Buffer;", "byteCount", "", "Companion", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class HashingSink extends ForwardingSink {
   public static final HashingSink.Companion Companion = new HashingSink.Companion((DefaultConstructorMarker)null);
   private final Mac mac;
   private final MessageDigest messageDigest;

   public HashingSink(Sink var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      Intrinsics.checkParameterIsNotNull(var2, "algorithm");
      super(var1);
      this.messageDigest = MessageDigest.getInstance(var2);
      this.mac = (Mac)null;
   }

   public HashingSink(Sink var1, ByteString var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      Intrinsics.checkParameterIsNotNull(var2, "key");
      Intrinsics.checkParameterIsNotNull(var3, "algorithm");
      super(var1);

      try {
         Mac var6 = Mac.getInstance(var3);
         SecretKeySpec var4 = new SecretKeySpec(var2.toByteArray(), var3);
         var6.init((Key)var4);
         this.mac = var6;
         this.messageDigest = (MessageDigest)null;
      } catch (InvalidKeyException var5) {
         throw (Throwable)(new IllegalArgumentException((Throwable)var5));
      }
   }

   @JvmStatic
   public static final HashingSink hmacSha1(Sink var0, ByteString var1) {
      return Companion.hmacSha1(var0, var1);
   }

   @JvmStatic
   public static final HashingSink hmacSha256(Sink var0, ByteString var1) {
      return Companion.hmacSha256(var0, var1);
   }

   @JvmStatic
   public static final HashingSink hmacSha512(Sink var0, ByteString var1) {
      return Companion.hmacSha512(var0, var1);
   }

   @JvmStatic
   public static final HashingSink md5(Sink var0) {
      return Companion.md5(var0);
   }

   @JvmStatic
   public static final HashingSink sha1(Sink var0) {
      return Companion.sha1(var0);
   }

   @JvmStatic
   public static final HashingSink sha256(Sink var0) {
      return Companion.sha256(var0);
   }

   @JvmStatic
   public static final HashingSink sha512(Sink var0) {
      return Companion.sha512(var0);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "hash",
   imports = {}
)
   )
   public final ByteString _deprecated_hash/* $FF was: -deprecated_hash*/() {
      return this.hash();
   }

   public final ByteString hash() {
      MessageDigest var1 = this.messageDigest;
      byte[] var2;
      if (var1 != null) {
         var2 = var1.digest();
      } else {
         Mac var3 = this.mac;
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         var2 = var3.doFinal();
      }

      Intrinsics.checkExpressionValueIsNotNull(var2, "result");
      return new ByteString(var2);
   }

   public void write(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      _Util.checkOffsetAndCount(var1.size(), 0L, var2);
      Segment var4 = var1.head;
      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      long var5 = 0L;

      while(var5 < var2) {
         int var7 = (int)Math.min(var2 - var5, (long)(var4.limit - var4.pos));
         MessageDigest var8 = this.messageDigest;
         if (var8 != null) {
            var8.update(var4.data, var4.pos, var7);
         } else {
            Mac var11 = this.mac;
            if (var11 == null) {
               Intrinsics.throwNpe();
            }

            var11.update(var4.data, var4.pos, var7);
         }

         long var9 = var5 + (long)var7;
         Segment var12 = var4.next;
         var4 = var12;
         var5 = var9;
         if (var12 == null) {
            Intrinsics.throwNpe();
            var4 = var12;
            var5 = var9;
         }
      }

      super.write(var1, var2);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000f"},
      d2 = {"Lokio/HashingSink$Companion;", "", "()V", "hmacSha1", "Lokio/HashingSink;", "sink", "Lokio/Sink;", "key", "Lokio/ByteString;", "hmacSha256", "hmacSha512", "md5", "sha1", "sha256", "sha512", "okio"},
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

      @JvmStatic
      public final HashingSink hmacSha1(Sink var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         return new HashingSink(var1, var2, "HmacSHA1");
      }

      @JvmStatic
      public final HashingSink hmacSha256(Sink var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         return new HashingSink(var1, var2, "HmacSHA256");
      }

      @JvmStatic
      public final HashingSink hmacSha512(Sink var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         return new HashingSink(var1, var2, "HmacSHA512");
      }

      @JvmStatic
      public final HashingSink md5(Sink var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         return new HashingSink(var1, "MD5");
      }

      @JvmStatic
      public final HashingSink sha1(Sink var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         return new HashingSink(var1, "SHA-1");
      }

      @JvmStatic
      public final HashingSink sha256(Sink var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         return new HashingSink(var1, "SHA-256");
      }

      @JvmStatic
      public final HashingSink sha512(Sink var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         return new HashingSink(var1, "SHA-512");
      }
   }
}
