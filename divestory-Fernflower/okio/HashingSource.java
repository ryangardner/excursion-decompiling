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
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00020\bH\u0007¢\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0016R\u0011\u0010\n\u001a\u00020\b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lokio/HashingSource;", "Lokio/ForwardingSource;", "source", "Lokio/Source;", "algorithm", "", "(Lokio/Source;Ljava/lang/String;)V", "key", "Lokio/ByteString;", "(Lokio/Source;Lokio/ByteString;Ljava/lang/String;)V", "hash", "()Lokio/ByteString;", "mac", "Ljavax/crypto/Mac;", "messageDigest", "Ljava/security/MessageDigest;", "-deprecated_hash", "read", "", "sink", "Lokio/Buffer;", "byteCount", "Companion", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class HashingSource extends ForwardingSource {
   public static final HashingSource.Companion Companion = new HashingSource.Companion((DefaultConstructorMarker)null);
   private final Mac mac;
   private final MessageDigest messageDigest;

   public HashingSource(Source var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "algorithm");
      super(var1);
      this.messageDigest = MessageDigest.getInstance(var2);
      this.mac = (Mac)null;
   }

   public HashingSource(Source var1, ByteString var2, String var3) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
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
   public static final HashingSource hmacSha1(Source var0, ByteString var1) {
      return Companion.hmacSha1(var0, var1);
   }

   @JvmStatic
   public static final HashingSource hmacSha256(Source var0, ByteString var1) {
      return Companion.hmacSha256(var0, var1);
   }

   @JvmStatic
   public static final HashingSource hmacSha512(Source var0, ByteString var1) {
      return Companion.hmacSha512(var0, var1);
   }

   @JvmStatic
   public static final HashingSource md5(Source var0) {
      return Companion.md5(var0);
   }

   @JvmStatic
   public static final HashingSource sha1(Source var0) {
      return Companion.sha1(var0);
   }

   @JvmStatic
   public static final HashingSource sha256(Source var0) {
      return Companion.sha256(var0);
   }

   @JvmStatic
   public static final HashingSource sha512(Source var0) {
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

   public long read(Buffer var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var4 = super.read(var1, var2);
      if (var4 != -1L) {
         long var6 = var1.size() - var4;
         long var8 = var1.size();
         Segment var10 = var1.head;
         var2 = var8;
         Segment var11 = var10;
         if (var10 == null) {
            Intrinsics.throwNpe();
            var11 = var10;
            var2 = var8;
         }

         while(true) {
            var8 = var6;
            long var12 = var2;
            var10 = var11;
            if (var2 <= var6) {
               for(; var12 < var1.size(); var8 = var12) {
                  int var14 = (int)((long)var10.pos + var8 - var12);
                  MessageDigest var15 = this.messageDigest;
                  if (var15 != null) {
                     var15.update(var10.data, var14, var10.limit - var14);
                  } else {
                     Mac var16 = this.mac;
                     if (var16 == null) {
                        Intrinsics.throwNpe();
                     }

                     var16.update(var10.data, var14, var10.limit - var14);
                  }

                  var12 += (long)(var10.limit - var10.pos);
                  var10 = var10.next;
                  if (var10 == null) {
                     Intrinsics.throwNpe();
                  }
               }
               break;
            }

            var11 = var11.prev;
            if (var11 == null) {
               Intrinsics.throwNpe();
            }

            var2 -= (long)(var11.limit - var11.pos);
         }
      }

      return var4;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000f"},
      d2 = {"Lokio/HashingSource$Companion;", "", "()V", "hmacSha1", "Lokio/HashingSource;", "source", "Lokio/Source;", "key", "Lokio/ByteString;", "hmacSha256", "hmacSha512", "md5", "sha1", "sha256", "sha512", "okio"},
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
      public final HashingSource hmacSha1(Source var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         return new HashingSource(var1, var2, "HmacSHA1");
      }

      @JvmStatic
      public final HashingSource hmacSha256(Source var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         return new HashingSource(var1, var2, "HmacSHA256");
      }

      @JvmStatic
      public final HashingSource hmacSha512(Source var1, ByteString var2) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         return new HashingSource(var1, var2, "HmacSHA512");
      }

      @JvmStatic
      public final HashingSource md5(Source var1) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         return new HashingSource(var1, "MD5");
      }

      @JvmStatic
      public final HashingSource sha1(Source var1) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         return new HashingSource(var1, "SHA-1");
      }

      @JvmStatic
      public final HashingSource sha256(Source var1) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         return new HashingSource(var1, "SHA-256");
      }

      @JvmStatic
      public final HashingSource sha512(Source var1) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         return new HashingSource(var1, "SHA-512");
      }
   }
}
