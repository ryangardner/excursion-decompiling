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
import okio.internal.SegmentedByteStringKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0015\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\r\u0010\u0019\u001a\u00020\u001aH\u0010¢\u0006\u0002\b\u001bJ\b\u0010\u001c\u001a\u00020\u001aH\u0016J\b\u0010\u001d\u001a\u00020\u0010H\u0016J\u001d\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0001H\u0010¢\u0006\u0002\b J\u0018\u0010!\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u001aH\u0016J\r\u0010#\u001a\u00020\u0004H\u0010¢\u0006\u0002\b$J\u0015\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001aH\u0010¢\u0006\u0002\b(J\u0018\u0010)\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u001aH\u0016J(\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u001aH\u0016J(\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u001aH\u0016J\u0010\u0010.\u001a\u00020\u00102\u0006\u0010/\u001a\u000200H\u0016J\u0018\u00101\u001a\u00020\u00012\u0006\u00102\u001a\u00020\u001a2\u0006\u00103\u001a\u00020\u001aH\u0016J\b\u00104\u001a\u00020\u0001H\u0016J\b\u00105\u001a\u00020\u0001H\u0016J\b\u00106\u001a\u00020\u0004H\u0016J\b\u00107\u001a\u00020\u0001H\u0002J\b\u00108\u001a\u00020\u0010H\u0016J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0016J%\u00109\u001a\u00020:2\u0006\u0010=\u001a\u00020>2\u0006\u0010+\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020\u001aH\u0010¢\u0006\u0002\b?J\b\u0010@\u001a\u00020AH\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0080\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006B"},
   d2 = {"Lokio/SegmentedByteString;", "Lokio/ByteString;", "segments", "", "", "directory", "", "([[B[I)V", "getDirectory$okio", "()[I", "getSegments$okio", "()[[B", "[[B", "asByteBuffer", "Ljava/nio/ByteBuffer;", "base64", "", "base64Url", "digest", "algorithm", "digest$okio", "equals", "", "other", "", "getSize", "", "getSize$okio", "hashCode", "hex", "hmac", "key", "hmac$okio", "indexOf", "fromIndex", "internalArray", "internalArray$okio", "internalGet", "", "pos", "internalGet$okio", "lastIndexOf", "rangeEquals", "offset", "otherOffset", "byteCount", "string", "charset", "Ljava/nio/charset/Charset;", "substring", "beginIndex", "endIndex", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toByteString", "toString", "write", "", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$okio", "writeReplace", "Ljava/lang/Object;", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class SegmentedByteString extends ByteString {
   private final transient int[] directory;
   private final transient byte[][] segments;

   public SegmentedByteString(byte[][] var1, int[] var2) {
      Intrinsics.checkParameterIsNotNull(var1, "segments");
      Intrinsics.checkParameterIsNotNull(var2, "directory");
      super(ByteString.EMPTY.getData$okio());
      this.segments = var1;
      this.directory = var2;
   }

   private final ByteString toByteString() {
      return new ByteString(this.toByteArray());
   }

   private final Object writeReplace() {
      ByteString var1 = this.toByteString();
      if (var1 != null) {
         return (Object)var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
      }
   }

   public ByteBuffer asByteBuffer() {
      ByteBuffer var1 = ByteBuffer.wrap(this.toByteArray()).asReadOnlyBuffer();
      Intrinsics.checkExpressionValueIsNotNull(var1, "ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer()");
      return var1;
   }

   public String base64() {
      return this.toByteString().base64();
   }

   public String base64Url() {
      return this.toByteString().base64Url();
   }

   public ByteString digest$okio(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "algorithm");
      MessageDigest var7 = MessageDigest.getInstance(var1);
      int var2 = ((Object[])this.getSegments$okio()).length;
      int var3 = 0;

      int var6;
      for(int var4 = 0; var3 < var2; var4 = var6) {
         int var5 = this.getDirectory$okio()[var2 + var3];
         var6 = this.getDirectory$okio()[var3];
         var7.update(this.getSegments$okio()[var3], var5, var6 - var4);
         ++var3;
      }

      byte[] var8 = var7.digest();
      Intrinsics.checkExpressionValueIsNotNull(var8, "digest.digest()");
      return new ByteString(var8);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 != this) {
         if (var1 instanceof ByteString) {
            ByteString var3 = (ByteString)var1;
            if (var3.size() == this.size() && this.rangeEquals(0, (ByteString)var3, 0, this.size())) {
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public final int[] getDirectory$okio() {
      return this.directory;
   }

   public final byte[][] getSegments$okio() {
      return this.segments;
   }

   public int getSize$okio() {
      return this.getDirectory$okio()[((Object[])this.getSegments$okio()).length - 1];
   }

   public int hashCode() {
      int var1 = this.getHashCode$okio();
      if (var1 == 0) {
         int var2 = ((Object[])this.getSegments$okio()).length;
         int var3 = 0;
         var1 = 1;

         int var6;
         for(int var4 = 0; var3 < var2; var4 = var6) {
            int var5 = this.getDirectory$okio()[var2 + var3];
            var6 = this.getDirectory$okio()[var3];
            byte[] var7 = this.getSegments$okio()[var3];

            for(int var8 = var5; var8 < var6 - var4 + var5; ++var8) {
               var1 = var1 * 31 + var7[var8];
            }

            ++var3;
         }

         this.setHashCode$okio(var1);
      }

      return var1;
   }

   public String hex() {
      return this.toByteString().hex();
   }

   public ByteString hmac$okio(String var1, ByteString var2) {
      Intrinsics.checkParameterIsNotNull(var1, "algorithm");
      Intrinsics.checkParameterIsNotNull(var2, "key");

      InvalidKeyException var10000;
      label38: {
         Mac var3;
         int var5;
         boolean var10001;
         try {
            var3 = Mac.getInstance(var1);
            SecretKeySpec var4 = new SecretKeySpec(var2.toByteArray(), var1);
            var3.init((Key)var4);
            var5 = ((Object[])this.getSegments$okio()).length;
         } catch (InvalidKeyException var12) {
            var10000 = var12;
            var10001 = false;
            break label38;
         }

         int var6 = 0;

         int var9;
         for(int var7 = 0; var6 < var5; var7 = var9) {
            try {
               int var8 = this.getDirectory$okio()[var5 + var6];
               var9 = this.getDirectory$okio()[var6];
               var3.update(this.getSegments$okio()[var6], var8, var9 - var7);
            } catch (InvalidKeyException var11) {
               var10000 = var11;
               var10001 = false;
               break label38;
            }

            ++var6;
         }

         try {
            byte[] var14 = var3.doFinal();
            Intrinsics.checkExpressionValueIsNotNull(var14, "mac.doFinal()");
            ByteString var15 = new ByteString(var14);
            return var15;
         } catch (InvalidKeyException var10) {
            var10000 = var10;
            var10001 = false;
         }
      }

      InvalidKeyException var13 = var10000;
      throw (Throwable)(new IllegalArgumentException((Throwable)var13));
   }

   public int indexOf(byte[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return this.toByteString().indexOf(var1, var2);
   }

   public byte[] internalArray$okio() {
      return this.toByteArray();
   }

   public byte internalGet$okio(int var1) {
      _Util.checkOffsetAndCount((long)this.getDirectory$okio()[((Object[])this.getSegments$okio()).length - 1], (long)var1, 1L);
      int var2 = SegmentedByteStringKt.segment(this, var1);
      int var3;
      if (var2 == 0) {
         var3 = 0;
      } else {
         var3 = this.getDirectory$okio()[var2 - 1];
      }

      int var4 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + var2];
      return this.getSegments$okio()[var2][var1 - var3 + var4];
   }

   public int lastIndexOf(byte[] var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "other");
      return this.toByteString().lastIndexOf(var1, var2);
   }

   public boolean rangeEquals(int var1, ByteString var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var2, "other");
      boolean var5 = false;
      boolean var6 = var5;
      if (var1 >= 0) {
         if (var1 > this.size() - var4) {
            var6 = var5;
         } else {
            int var7 = var4 + var1;
            int var8 = SegmentedByteStringKt.segment(this, var1);
            var4 = var3;

            for(var3 = var8; var1 < var7; ++var3) {
               if (var3 == 0) {
                  var8 = 0;
               } else {
                  var8 = this.getDirectory$okio()[var3 - 1];
               }

               int var9 = this.getDirectory$okio()[var3];
               int var10 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + var3];
               var9 = Math.min(var7, var9 - var8 + var8) - var1;
               if (!var2.rangeEquals(var4, this.getSegments$okio()[var3], var10 + (var1 - var8), var9)) {
                  var6 = var5;
                  return var6;
               }

               var4 += var9;
               var1 += var9;
            }

            var6 = true;
         }
      }

      return var6;
   }

   public boolean rangeEquals(int var1, byte[] var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var2, "other");
      boolean var5 = false;
      boolean var6 = var5;
      if (var1 >= 0) {
         var6 = var5;
         if (var1 <= this.size() - var4) {
            var6 = var5;
            if (var3 >= 0) {
               if (var3 > var2.length - var4) {
                  var6 = var5;
               } else {
                  int var7 = var4 + var1;
                  int var8 = SegmentedByteStringKt.segment(this, var1);
                  var4 = var1;

                  for(var1 = var8; var4 < var7; ++var1) {
                     if (var1 == 0) {
                        var8 = 0;
                     } else {
                        var8 = this.getDirectory$okio()[var1 - 1];
                     }

                     int var9 = this.getDirectory$okio()[var1];
                     int var10 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + var1];
                     var9 = Math.min(var7, var9 - var8 + var8) - var4;
                     if (!_Util.arrayRangeEquals(this.getSegments$okio()[var1], var10 + (var4 - var8), var2, var3, var9)) {
                        var6 = var5;
                        return var6;
                     }

                     var3 += var9;
                     var4 += var9;
                  }

                  var6 = true;
               }
            }
         }
      }

      return var6;
   }

   public String string(Charset var1) {
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      return this.toByteString().string(var1);
   }

   public ByteString substring(int var1, int var2) {
      byte var3 = 0;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var6;
      if (var4) {
         if (var2 <= this.size()) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            int var5 = var2 - var1;
            if (var5 >= 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               var6 = new StringBuilder();
               var6.append("endIndex=");
               var6.append(var2);
               var6.append(" < beginIndex=");
               var6.append(var1);
               throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
            } else {
               ByteString var11;
               if (var1 == 0 && var2 == this.size()) {
                  var11 = (ByteString)this;
               } else if (var1 == var2) {
                  var11 = ByteString.EMPTY;
               } else {
                  int var7 = SegmentedByteStringKt.segment(this, var1);
                  int var8 = SegmentedByteStringKt.segment(this, var2 - 1);
                  byte[][] var9 = (byte[][])ArraysKt.copyOfRange((Object[])this.getSegments$okio(), var7, var8 + 1);
                  Object[] var12 = (Object[])var9;
                  int[] var10 = new int[var12.length * 2];
                  int var13;
                  if (var7 <= var8) {
                     var13 = var7;
                     var2 = 0;

                     while(true) {
                        var10[var2] = Math.min(this.getDirectory$okio()[var13] - var1, var5);
                        var10[var2 + var12.length] = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + var13];
                        if (var13 == var8) {
                           break;
                        }

                        ++var13;
                        ++var2;
                     }
                  }

                  if (var7 == 0) {
                     var2 = var3;
                  } else {
                     var2 = this.getDirectory$okio()[var7 - 1];
                  }

                  var13 = var12.length;
                  var10[var13] += var1 - var2;
                  var11 = (ByteString)(new SegmentedByteString(var9, var10));
               }

               return var11;
            }
         } else {
            var6 = new StringBuilder();
            var6.append("endIndex=");
            var6.append(var2);
            var6.append(" > length(");
            var6.append(this.size());
            var6.append(')');
            throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
         }
      } else {
         var6 = new StringBuilder();
         var6.append("beginIndex=");
         var6.append(var1);
         var6.append(" < 0");
         throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
      }
   }

   public ByteString toAsciiLowercase() {
      return this.toByteString().toAsciiLowercase();
   }

   public ByteString toAsciiUppercase() {
      return this.toByteString().toAsciiUppercase();
   }

   public byte[] toByteArray() {
      byte[] var1 = new byte[this.size()];
      int var2 = ((Object[])this.getSegments$okio()).length;
      int var3 = 0;
      int var4 = 0;

      int var7;
      for(int var5 = 0; var3 < var2; var4 = var7) {
         int var6 = this.getDirectory$okio()[var2 + var3];
         var7 = this.getDirectory$okio()[var3];
         byte[] var8 = this.getSegments$okio()[var3];
         var4 = var7 - var4;
         ArraysKt.copyInto(var8, var1, var5, var6, var6 + var4);
         var5 += var4;
         ++var3;
      }

      return var1;
   }

   public String toString() {
      return this.toByteString().toString();
   }

   public void write(OutputStream var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      int var2 = ((Object[])this.getSegments$okio()).length;
      int var3 = 0;

      int var6;
      for(int var4 = 0; var3 < var2; var4 = var6) {
         int var5 = this.getDirectory$okio()[var2 + var3];
         var6 = this.getDirectory$okio()[var3];
         var1.write(this.getSegments$okio()[var3], var5, var6 - var4);
         ++var3;
      }

   }

   public void write$okio(Buffer var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      int var4 = var3 + var2;
      int var5 = SegmentedByteStringKt.segment(this, var2);
      var3 = var2;

      for(var2 = var5; var3 < var4; ++var2) {
         if (var2 == 0) {
            var5 = 0;
         } else {
            var5 = this.getDirectory$okio()[var2 - 1];
         }

         int var6 = this.getDirectory$okio()[var2];
         int var7 = this.getDirectory$okio()[((Object[])this.getSegments$okio()).length + var2];
         var6 = Math.min(var4, var6 - var5 + var5) - var3;
         var5 = var7 + (var3 - var5);
         Segment var8 = new Segment(this.getSegments$okio()[var2], var5, var5 + var6, true, false);
         if (var1.head == null) {
            var8.prev = var8;
            var8.next = var8.prev;
            var1.head = var8.next;
         } else {
            Segment var9 = var1.head;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            var9 = var9.prev;
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            var9.push(var8);
         }

         var3 += var6;
      }

      var1.setSize$okio(var1.size() + (long)this.size());
   }
}
