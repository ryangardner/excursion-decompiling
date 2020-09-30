package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import okio.internal.BufferKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0002\u0090\u0001B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0000H\u0016J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0000H\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016J\u0006\u0010\u0015\u001a\u00020\fJ\u0006\u0010\u0016\u001a\u00020\u0000J$\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\f2\b\b\u0002\u0010\u001b\u001a\u00020\fH\u0007J\u0018\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u001a\u001a\u00020\fJ \u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\fJ\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u0000H\u0016J\b\u0010!\u001a\u00020\u0000H\u0016J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\b\u0010&\u001a\u00020#H\u0016J\b\u0010'\u001a\u00020\u0012H\u0016J\u0016\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\fH\u0087\u0002¢\u0006\u0002\b+J\u0015\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020\fH\u0007¢\u0006\u0002\b-J\b\u0010.\u001a\u00020/H\u0016J\u0018\u00100\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u00101\u001a\u00020\u001dH\u0002J\u000e\u00102\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001dJ\u000e\u00103\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001dJ\u000e\u00104\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001dJ\u0010\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020)H\u0016J\u0018\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020)2\u0006\u00107\u001a\u00020\fH\u0016J \u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020)2\u0006\u00107\u001a\u00020\f2\u0006\u00108\u001a\u00020\fH\u0016J\u0010\u00105\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001dH\u0016J\u0018\u00105\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\fH\u0016J\u0010\u0010:\u001a\u00020\f2\u0006\u0010;\u001a\u00020\u001dH\u0016J\u0018\u0010:\u001a\u00020\f2\u0006\u0010;\u001a\u00020\u001d2\u0006\u00107\u001a\u00020\fH\u0016J\b\u0010<\u001a\u00020=H\u0016J\b\u0010>\u001a\u00020#H\u0016J\u0006\u0010?\u001a\u00020\u001dJ\b\u0010@\u001a\u00020\u0019H\u0016J\b\u0010A\u001a\u00020\u0001H\u0016J\u0018\u0010B\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001dH\u0016J(\u0010B\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\f2\u0006\u00109\u001a\u00020\u001d2\u0006\u0010C\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0010\u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020FH\u0016J\u0010\u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020GH\u0016J \u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020G2\u0006\u0010\u001a\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0018\u0010D\u001a\u00020\f2\u0006\u0010E\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010H\u001a\u00020\f2\u0006\u0010E\u001a\u00020IH\u0016J\u0012\u0010J\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020KH\u0007J\b\u0010M\u001a\u00020)H\u0016J\b\u0010N\u001a\u00020GH\u0016J\u0010\u0010N\u001a\u00020G2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010O\u001a\u00020\u001dH\u0016J\u0010\u0010O\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010P\u001a\u00020\fH\u0016J\u000e\u0010Q\u001a\u00020\u00002\u0006\u0010R\u001a\u00020=J\u0016\u0010Q\u001a\u00020\u00002\u0006\u0010R\u001a\u00020=2\u0006\u0010\u001b\u001a\u00020\fJ \u0010Q\u001a\u00020\u00122\u0006\u0010R\u001a\u00020=2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010S\u001a\u00020#H\u0002J\u0010\u0010T\u001a\u00020\u00122\u0006\u0010E\u001a\u00020GH\u0016J\u0018\u0010T\u001a\u00020\u00122\u0006\u0010E\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010U\u001a\u00020\fH\u0016J\b\u0010V\u001a\u00020/H\u0016J\b\u0010W\u001a\u00020/H\u0016J\b\u0010X\u001a\u00020\fH\u0016J\b\u0010Y\u001a\u00020\fH\u0016J\b\u0010Z\u001a\u00020[H\u0016J\b\u0010\\\u001a\u00020[H\u0016J\u0010\u0010]\u001a\u00020\u001f2\u0006\u0010^\u001a\u00020_H\u0016J\u0018\u0010]\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010^\u001a\u00020_H\u0016J\u0012\u0010`\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020KH\u0007J\b\u0010a\u001a\u00020\u001fH\u0016J\u0010\u0010a\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010b\u001a\u00020/H\u0016J\n\u0010c\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010d\u001a\u00020\u001fH\u0016J\u0010\u0010d\u001a\u00020\u001f2\u0006\u0010e\u001a\u00020\fH\u0016J\u0010\u0010f\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010g\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010h\u001a\u00020/2\u0006\u0010i\u001a\u00020jH\u0016J\u0006\u0010k\u001a\u00020\u001dJ\u0006\u0010l\u001a\u00020\u001dJ\u0006\u0010m\u001a\u00020\u001dJ\r\u0010\r\u001a\u00020\fH\u0007¢\u0006\u0002\bnJ\u0010\u0010o\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0006\u0010p\u001a\u00020\u001dJ\u000e\u0010p\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020/J\b\u0010q\u001a\u00020rH\u0016J\b\u0010s\u001a\u00020\u001fH\u0016J\u0015\u0010t\u001a\u00020\n2\u0006\u0010u\u001a\u00020/H\u0000¢\u0006\u0002\bvJ\u0010\u0010w\u001a\u00020/2\u0006\u0010x\u001a\u00020FH\u0016J\u0010\u0010w\u001a\u00020\u00002\u0006\u0010x\u001a\u00020GH\u0016J \u0010w\u001a\u00020\u00002\u0006\u0010x\u001a\u00020G2\u0006\u0010\u001a\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0018\u0010w\u001a\u00020\u00122\u0006\u0010x\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010w\u001a\u00020\u00002\u0006\u0010y\u001a\u00020\u001dH\u0016J \u0010w\u001a\u00020\u00002\u0006\u0010y\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020/H\u0016J\u0018\u0010w\u001a\u00020\u00002\u0006\u0010x\u001a\u00020z2\u0006\u0010\u001b\u001a\u00020\fH\u0016J\u0010\u0010{\u001a\u00020\f2\u0006\u0010x\u001a\u00020zH\u0016J\u0010\u0010|\u001a\u00020\u00002\u0006\u00106\u001a\u00020/H\u0016J\u0010\u0010}\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0010\u0010\u007f\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0012\u0010\u0080\u0001\u001a\u00020\u00002\u0007\u0010\u0081\u0001\u001a\u00020/H\u0016J\u0012\u0010\u0082\u0001\u001a\u00020\u00002\u0007\u0010\u0081\u0001\u001a\u00020/H\u0016J\u0011\u0010\u0083\u0001\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0011\u0010\u0084\u0001\u001a\u00020\u00002\u0006\u0010~\u001a\u00020\fH\u0016J\u0012\u0010\u0085\u0001\u001a\u00020\u00002\u0007\u0010\u0086\u0001\u001a\u00020/H\u0016J\u0012\u0010\u0087\u0001\u001a\u00020\u00002\u0007\u0010\u0086\u0001\u001a\u00020/H\u0016J\u001a\u0010\u0088\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001f2\u0006\u0010^\u001a\u00020_H\u0016J,\u0010\u0088\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001f2\u0007\u0010\u008a\u0001\u001a\u00020/2\u0007\u0010\u008b\u0001\u001a\u00020/2\u0006\u0010^\u001a\u00020_H\u0016J\u001b\u0010\u008c\u0001\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001b\u001a\u00020\fH\u0007J\u0012\u0010\u008d\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001fH\u0016J$\u0010\u008d\u0001\u001a\u00020\u00002\u0007\u0010\u0089\u0001\u001a\u00020\u001f2\u0007\u0010\u008a\u0001\u001a\u00020/2\u0007\u0010\u008b\u0001\u001a\u00020/H\u0016J\u0012\u0010\u008e\u0001\u001a\u00020\u00002\u0007\u0010\u008f\u0001\u001a\u00020/H\u0016R\u0014\u0010\u0006\u001a\u00020\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\n8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R&\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8G@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0091\u0001"},
   d2 = {"Lokio/Buffer;", "Lokio/BufferedSource;", "Lokio/BufferedSink;", "", "Ljava/nio/channels/ByteChannel;", "()V", "buffer", "getBuffer", "()Lokio/Buffer;", "head", "Lokio/Segment;", "<set-?>", "", "size", "()J", "setSize$okio", "(J)V", "clear", "", "clone", "close", "completeSegmentByteCount", "copy", "copyTo", "out", "Ljava/io/OutputStream;", "offset", "byteCount", "digest", "Lokio/ByteString;", "algorithm", "", "emit", "emitCompleteSegments", "equals", "", "other", "", "exhausted", "flush", "get", "", "pos", "getByte", "index", "-deprecated_getByte", "hashCode", "", "hmac", "key", "hmacSha1", "hmacSha256", "hmacSha512", "indexOf", "b", "fromIndex", "toIndex", "bytes", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "md5", "outputStream", "peek", "rangeEquals", "bytesOffset", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readAndWriteUnsafe", "Lokio/Buffer$UnsafeCursor;", "unsafeCursor", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFrom", "input", "forever", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "charset", "Ljava/nio/charset/Charset;", "readUnsafe", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "sha1", "sha256", "sha512", "-deprecated_size", "skip", "snapshot", "timeout", "Lokio/Timeout;", "toString", "writableSegment", "minimumCapacity", "writableSegment$okio", "write", "source", "byteString", "Lokio/Source;", "writeAll", "writeByte", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "beginIndex", "endIndex", "writeTo", "writeUtf8", "writeUtf8CodePoint", "codePoint", "UnsafeCursor", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Buffer implements BufferedSource, BufferedSink, Cloneable, ByteChannel {
   public Segment head;
   private long size;

   // $FF: synthetic method
   public static Buffer copyTo$default(Buffer var0, OutputStream var1, long var2, long var4, int var6, Object var7) throws IOException {
      if ((var6 & 2) != 0) {
         var2 = 0L;
      }

      if ((var6 & 4) != 0) {
         var4 = var0.size - var2;
      }

      return var0.copyTo(var1, var2, var4);
   }

   // $FF: synthetic method
   public static Buffer copyTo$default(Buffer var0, Buffer var1, long var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0L;
      }

      return var0.copyTo(var1, var2);
   }

   // $FF: synthetic method
   public static Buffer copyTo$default(Buffer var0, Buffer var1, long var2, long var4, int var6, Object var7) {
      if ((var6 & 2) != 0) {
         var2 = 0L;
      }

      return var0.copyTo(var1, var2, var4);
   }

   private final ByteString digest(String var1) {
      MessageDigest var2 = MessageDigest.getInstance(var1);
      Segment var3 = this.head;
      if (var3 != null) {
         var2.update(var3.data, var3.pos, var3.limit - var3.pos);
         Segment var4 = var3.next;
         Segment var5 = var4;
         if (var4 == null) {
            Intrinsics.throwNpe();
            var5 = var4;
         }

         while(var5 != var3) {
            var2.update(var5.data, var5.pos, var5.limit - var5.pos);
            var4 = var5.next;
            var5 = var4;
            if (var4 == null) {
               Intrinsics.throwNpe();
               var5 = var4;
            }
         }
      }

      byte[] var6 = var2.digest();
      Intrinsics.checkExpressionValueIsNotNull(var6, "messageDigest.digest()");
      return new ByteString(var6);
   }

   private final ByteString hmac(String var1, ByteString var2) {
      InvalidKeyException var10000;
      label55: {
         Mac var3;
         Segment var16;
         boolean var10001;
         try {
            var3 = Mac.getInstance(var1);
            SecretKeySpec var4 = new SecretKeySpec(var2.internalArray$okio(), var1);
            var3.init((Key)var4);
            var16 = this.head;
         } catch (InvalidKeyException var10) {
            var10000 = var10;
            var10001 = false;
            break label55;
         }

         if (var16 != null) {
            Segment var12;
            try {
               var3.update(var16.data, var16.pos, var16.limit - var16.pos);
               var12 = var16.next;
            } catch (InvalidKeyException var9) {
               var10000 = var9;
               var10001 = false;
               break label55;
            }

            Segment var11 = var12;
            if (var12 == null) {
               try {
                  Intrinsics.throwNpe();
               } catch (InvalidKeyException var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label55;
               }

               var11 = var12;
            }

            while(var11 != var16) {
               try {
                  var3.update(var11.data, var11.pos, var11.limit - var11.pos);
                  var12 = var11.next;
               } catch (InvalidKeyException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label55;
               }

               var11 = var12;
               if (var12 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (InvalidKeyException var6) {
                     var10000 = var6;
                     var10001 = false;
                     break label55;
                  }

                  var11 = var12;
               }
            }
         }

         try {
            byte[] var14 = var3.doFinal();
            Intrinsics.checkExpressionValueIsNotNull(var14, "mac.doFinal()");
            ByteString var15 = new ByteString(var14);
            return var15;
         } catch (InvalidKeyException var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      InvalidKeyException var13 = var10000;
      throw (Throwable)(new IllegalArgumentException((Throwable)var13));
   }

   // $FF: synthetic method
   public static Buffer.UnsafeCursor readAndWriteUnsafe$default(Buffer var0, Buffer.UnsafeCursor var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = new Buffer.UnsafeCursor();
      }

      return var0.readAndWriteUnsafe(var1);
   }

   private final void readFrom(InputStream var1, long var2, boolean var4) throws IOException {
      while(var2 > 0L || var4) {
         Segment var5 = this.writableSegment$okio(1);
         int var6 = (int)Math.min(var2, (long)(8192 - var5.limit));
         var6 = var1.read(var5.data, var5.limit, var6);
         if (var6 == -1) {
            if (var5.pos == var5.limit) {
               this.head = var5.pop();
               SegmentPool.recycle(var5);
            }

            if (var4) {
               return;
            }

            throw (Throwable)(new EOFException());
         }

         var5.limit += var6;
         long var7 = this.size;
         long var9 = (long)var6;
         this.size = var7 + var9;
         var2 -= var9;
      }

   }

   // $FF: synthetic method
   public static Buffer.UnsafeCursor readUnsafe$default(Buffer var0, Buffer.UnsafeCursor var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = new Buffer.UnsafeCursor();
      }

      return var0.readUnsafe(var1);
   }

   // $FF: synthetic method
   public static Buffer writeTo$default(Buffer var0, OutputStream var1, long var2, int var4, Object var5) throws IOException {
      if ((var4 & 2) != 0) {
         var2 = var0.size;
      }

      return var0.writeTo(var1, var2);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to operator function",
      replaceWith = @ReplaceWith(
   expression = "this[index]",
   imports = {}
)
   )
   public final byte _deprecated_getByte/* $FF was: -deprecated_getByte*/(long var1) {
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
   public final long _deprecated_size/* $FF was: -deprecated_size*/() {
      return this.size;
   }

   public Buffer buffer() {
      return this;
   }

   public final void clear() {
      this.skip(this.size());
   }

   public Buffer clone() {
      return this.copy();
   }

   public void close() {
   }

   public final long completeSegmentByteCount() {
      long var1 = this.size();
      long var3 = 0L;
      if (var1 != 0L) {
         Segment var5 = this.head;
         if (var5 == null) {
            Intrinsics.throwNpe();
         }

         var5 = var5.prev;
         if (var5 == null) {
            Intrinsics.throwNpe();
         }

         var3 = var1;
         if (var5.limit < 8192) {
            var3 = var1;
            if (var5.owner) {
               var3 = var1 - (long)(var5.limit - var5.pos);
            }
         }
      }

      return var3;
   }

   public final Buffer copy() {
      Buffer var1 = new Buffer();
      if (this.size() != 0L) {
         Segment var2 = this.head;
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         Segment var3 = var2.sharedCopy();
         var1.head = var3;
         var3.prev = var3;
         var3.next = var3.prev;

         for(Segment var4 = var2.next; var4 != var2; var4 = var4.next) {
            Segment var5 = var3.prev;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var5.push(var4.sharedCopy());
         }

         var1.setSize$okio(this.size());
      }

      return var1;
   }

   public final Buffer copyTo(OutputStream var1) throws IOException {
      return copyTo$default(this, (OutputStream)var1, 0L, 0L, 6, (Object)null);
   }

   public final Buffer copyTo(OutputStream var1, long var2) throws IOException {
      return copyTo$default(this, (OutputStream)var1, var2, 0L, 4, (Object)null);
   }

   public final Buffer copyTo(OutputStream var1, long var2, long var4) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      _Util.checkOffsetAndCount(this.size, var2, var4);
      if (var4 == 0L) {
         return this;
      } else {
         Segment var6 = this.head;

         while(true) {
            if (var6 == null) {
               Intrinsics.throwNpe();
            }

            Segment var7 = var6;
            long var8 = var2;
            long var10 = var4;
            if (var2 < (long)(var6.limit - var6.pos)) {
               while(var10 > 0L) {
                  if (var7 == null) {
                     Intrinsics.throwNpe();
                  }

                  int var12 = (int)((long)var7.pos + var8);
                  int var13 = (int)Math.min((long)(var7.limit - var12), var10);
                  var1.write(var7.data, var12, var13);
                  var10 -= (long)var13;
                  var7 = var7.next;
                  var8 = 0L;
               }

               return this;
            }

            var2 -= (long)(var6.limit - var6.pos);
            var6 = var6.next;
         }
      }
   }

   public final Buffer copyTo(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      return this.copyTo(var1, var2, this.size - var2);
   }

   public final Buffer copyTo(Buffer var1, long var2, long var4) {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      _Util.checkOffsetAndCount(this.size(), var2, var4);
      if (var4 != 0L) {
         var1.setSize$okio(var1.size() + var4);
         Segment var6 = this.head;

         while(true) {
            if (var6 == null) {
               Intrinsics.throwNpe();
            }

            Segment var7 = var6;
            long var8 = var2;
            long var10 = var4;
            if (var2 < (long)(var6.limit - var6.pos)) {
               while(var10 > 0L) {
                  if (var7 == null) {
                     Intrinsics.throwNpe();
                  }

                  var6 = var7.sharedCopy();
                  var6.pos += (int)var8;
                  var6.limit = Math.min(var6.pos + (int)var10, var6.limit);
                  Segment var12 = var1.head;
                  if (var12 == null) {
                     var6.prev = var6;
                     var6.next = var6.prev;
                     var1.head = var6.next;
                  } else {
                     if (var12 == null) {
                        Intrinsics.throwNpe();
                     }

                     var12 = var12.prev;
                     if (var12 == null) {
                        Intrinsics.throwNpe();
                     }

                     var12.push(var6);
                  }

                  var10 -= (long)(var6.limit - var6.pos);
                  var7 = var7.next;
                  var8 = 0L;
               }
               break;
            }

            var2 -= (long)(var6.limit - var6.pos);
            var6 = var6.next;
         }
      }

      return this;
   }

   public Buffer emit() {
      return this;
   }

   public Buffer emitCompleteSegments() {
      return this;
   }

   public boolean equals(Object var1) {
      boolean var2 = false;
      if (this != var1) {
         if (!(var1 instanceof Buffer)) {
            return var2;
         }

         long var3 = this.size();
         Buffer var14 = (Buffer)var1;
         if (var3 != var14.size()) {
            return var2;
         }

         if (this.size() != 0L) {
            Segment var5 = this.head;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            Segment var6 = var14.head;
            if (var6 == null) {
               Intrinsics.throwNpe();
            }

            int var7 = var5.pos;
            int var8 = var6.pos;

            int var13;
            for(var3 = 0L; var3 < this.size(); var8 = var13) {
               long var9 = (long)Math.min(var5.limit - var7, var6.limit - var8);
               long var11 = 0L;

               for(var13 = var7; var11 < var9; ++var8) {
                  if (var5.data[var13] != var6.data[var8]) {
                     return var2;
                  }

                  ++var11;
                  ++var13;
               }

               Segment var15 = var5;
               var7 = var13;
               if (var13 == var5.limit) {
                  var15 = var5.next;
                  if (var15 == null) {
                     Intrinsics.throwNpe();
                  }

                  var7 = var15.pos;
               }

               var5 = var6;
               var13 = var8;
               if (var8 == var6.limit) {
                  var5 = var6.next;
                  if (var5 == null) {
                     Intrinsics.throwNpe();
                  }

                  var13 = var5.pos;
               }

               var3 += var9;
               var6 = var5;
               var5 = var15;
            }
         }
      }

      var2 = true;
      return var2;
   }

   public boolean exhausted() {
      boolean var1;
      if (this.size == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void flush() {
   }

   public Buffer getBuffer() {
      return this;
   }

   public final byte getByte(long var1) {
      _Util.checkOffsetAndCount(this.size(), var1, 1L);
      Segment var3 = this.head;
      byte var6;
      byte var7;
      if (var3 != null) {
         long var4;
         if (this.size() - var1 < var1) {
            for(var4 = this.size(); var4 > var1; var4 -= (long)(var3.limit - var3.pos)) {
               var3 = var3.prev;
               if (var3 == null) {
                  Intrinsics.throwNpe();
               }
            }

            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            var6 = var3.data[(int)((long)var3.pos + var1 - var4)];
            var7 = var6;
         } else {
            var4 = 0L;

            while(true) {
               long var8 = (long)(var3.limit - var3.pos) + var4;
               if (var8 > var1) {
                  if (var3 == null) {
                     Intrinsics.throwNpe();
                  }

                  var6 = var3.data[(int)((long)var3.pos + var1 - var4)];
                  var7 = var6;
                  break;
               }

               var3 = var3.next;
               if (var3 == null) {
                  Intrinsics.throwNpe();
               }

               var4 = var8;
            }
         }
      } else {
         var3 = (Segment)null;
         Intrinsics.throwNpe();
         var6 = var3.data[(int)((long)var3.pos + var1 + 1L)];
         var7 = var6;
      }

      return var7;
   }

   public int hashCode() {
      Segment var1 = this.head;
      int var5;
      if (var1 != null) {
         int var2 = 1;

         Segment var6;
         do {
            int var3 = var1.pos;
            int var4 = var1.limit;

            for(var5 = var2; var3 < var4; ++var3) {
               var5 = var5 * 31 + var1.data[var3];
            }

            var6 = var1.next;
            if (var6 == null) {
               Intrinsics.throwNpe();
            }

            var1 = var6;
            var2 = var5;
         } while(var6 != this.head);
      } else {
         var5 = 0;
      }

      return var5;
   }

   public final ByteString hmacSha1(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return this.hmac("HmacSHA1", var1);
   }

   public final ByteString hmacSha256(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return this.hmac("HmacSHA256", var1);
   }

   public final ByteString hmacSha512(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      return this.hmac("HmacSHA512", var1);
   }

   public long indexOf(byte var1) {
      return this.indexOf(var1, 0L, Long.MAX_VALUE);
   }

   public long indexOf(byte var1, long var2) {
      return this.indexOf(var1, var2, Long.MAX_VALUE);
   }

   public long indexOf(byte var1, long var2, long var4) {
      long var6 = 0L;
      boolean var8;
      if (0L <= var2 && var4 >= var2) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         long var9 = var4;
         if (var4 > this.size()) {
            var9 = this.size();
         }

         long var11 = -1L;
         if (var2 == var9) {
            var6 = var11;
         } else {
            Segment var13 = this.head;
            Segment var20;
            if (var13 != null) {
               var4 = var6;
               var20 = var13;
               int var17;
               int var18;
               byte[] var19;
               if (this.size() - var2 < var2) {
                  var4 = this.size();

                  for(var20 = var13; var4 > var2; var4 -= (long)(var20.limit - var20.pos)) {
                     var20 = var20.prev;
                     if (var20 == null) {
                        Intrinsics.throwNpe();
                     }
                  }

                  var6 = var11;
                  if (var20 == null) {
                     return var6;
                  }

                  long var15 = var2;
                  var2 = var4;

                  label87:
                  while(true) {
                     var6 = var11;
                     if (var2 >= var9) {
                        return var6;
                     }

                     var19 = var20.data;
                     var17 = (int)Math.min((long)var20.limit, (long)var20.pos + var9 - var2);

                     for(var18 = (int)((long)var20.pos + var15 - var2); var18 < var17; ++var18) {
                        if (var19[var18] == var1) {
                           break label87;
                        }
                     }

                     var2 += (long)(var20.limit - var20.pos);
                     var20 = var20.next;
                     if (var20 == null) {
                        Intrinsics.throwNpe();
                     }

                     var15 = var2;
                  }
               } else {
                  label100:
                  while(true) {
                     var6 = (long)(var20.limit - var20.pos) + var4;
                     if (var6 > var2) {
                        var6 = var11;
                        if (var20 == null) {
                           return var6;
                        }

                        while(true) {
                           var6 = var11;
                           if (var4 >= var9) {
                              return var6;
                           }

                           var19 = var20.data;
                           var17 = (int)Math.min((long)var20.limit, (long)var20.pos + var9 - var4);

                           for(var18 = (int)((long)var20.pos + var2 - var4); var18 < var17; ++var18) {
                              if (var19[var18] == var1) {
                                 var2 = var4;
                                 break label100;
                              }
                           }

                           var4 += (long)(var20.limit - var20.pos);
                           var20 = var20.next;
                           if (var20 == null) {
                              Intrinsics.throwNpe();
                           }

                           var2 = var4;
                        }
                     }

                     var20 = var20.next;
                     if (var20 == null) {
                        Intrinsics.throwNpe();
                     }

                     var4 = var6;
                  }
               }

               var6 = (long)(var18 - var20.pos) + var2;
            } else {
               var20 = (Segment)null;
               var6 = var11;
            }
         }

         return var6;
      } else {
         StringBuilder var14 = new StringBuilder();
         var14.append("size=");
         var14.append(this.size());
         var14.append(" fromIndex=");
         var14.append(var2);
         var14.append(" toIndex=");
         var14.append(var4);
         throw (Throwable)(new IllegalArgumentException(var14.toString().toString()));
      }
   }

   public long indexOf(ByteString var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      return this.indexOf(var1, 0L);
   }

   public long indexOf(ByteString var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      boolean var4;
      if (var1.size() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         long var5 = 0L;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            StringBuilder var18 = new StringBuilder();
            var18.append("fromIndex < 0: ");
            var18.append(var2);
            throw (Throwable)(new IllegalArgumentException(var18.toString().toString()));
         } else {
            Segment var7 = this.head;
            if (var7 != null) {
               Segment var8 = var7;
               byte var9;
               int var10;
               long var11;
               long var13;
               int var15;
               byte[] var16;
               int var19;
               byte[] var20;
               if (this.size() - var2 < var2) {
                  var5 = this.size();

                  for(var8 = var7; var5 > var2; var5 -= (long)(var8.limit - var8.pos)) {
                     var8 = var8.prev;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }
                  }

                  if (var8 != null) {
                     var20 = var1.internalArray$okio();
                     var9 = var20[0];
                     var10 = var1.size();

                     for(var11 = this.size() - (long)var10 + 1L; var5 < var11; var2 = var5) {
                        var16 = var8.data;
                        var19 = var8.limit;
                        var13 = (long)var8.pos;
                        var15 = (int)Math.min((long)var19, var13 + var11 - var5);

                        for(var19 = (int)((long)var8.pos + var2 - var5); var19 < var15; ++var19) {
                           if (var16[var19] == var9 && BufferKt.rangeEquals(var8, var19 + 1, var20, 1, var10)) {
                              var2 = (long)(var19 - var8.pos) + var5;
                              return var2;
                           }
                        }

                        var5 += (long)(var8.limit - var8.pos);
                        var8 = var8.next;
                        if (var8 == null) {
                           Intrinsics.throwNpe();
                        }
                     }
                  }
               } else {
                  while(true) {
                     var11 = (long)(var8.limit - var8.pos) + var5;
                     if (var11 > var2) {
                        if (var8 != null) {
                           var20 = var1.internalArray$okio();
                           var9 = var20[0];
                           var10 = var1.size();

                           for(var11 = this.size() - (long)var10 + 1L; var5 < var11; var2 = var5) {
                              var16 = var8.data;
                              var19 = var8.limit;
                              var13 = (long)var8.pos;
                              var15 = (int)Math.min((long)var19, var13 + var11 - var5);

                              for(var19 = (int)((long)var8.pos + var2 - var5); var19 < var15; ++var19) {
                                 if (var16[var19] == var9 && BufferKt.rangeEquals(var8, var19 + 1, var20, 1, var10)) {
                                    var2 = (long)(var19 - var8.pos) + var5;
                                    return var2;
                                 }
                              }

                              var5 += (long)(var8.limit - var8.pos);
                              var8 = var8.next;
                              if (var8 == null) {
                                 Intrinsics.throwNpe();
                              }
                           }
                        }
                        break;
                     }

                     var8 = var8.next;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }

                     var5 = var11;
                  }
               }
            } else {
               Segment var17 = (Segment)null;
            }

            var2 = -1L;
            return var2;
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("bytes is empty".toString()));
      }
   }

   public long indexOfElement(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "targetBytes");
      return this.indexOfElement(var1, 0L);
   }

   public long indexOfElement(ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "targetBytes");
      long var4 = 0L;
      boolean var6;
      if (var2 >= 0L) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (!var6) {
         StringBuilder var20 = new StringBuilder();
         var20.append("fromIndex < 0: ");
         var20.append(var2);
         throw (Throwable)(new IllegalArgumentException(var20.toString().toString()));
      } else {
         Segment var7 = this.head;
         long var8 = -1L;
         long var11;
         Segment var18;
         if (var7 != null) {
            int var15;
            int var21;
            label184: {
               Segment var10;
               label205: {
                  var10 = var7;
                  byte var13;
                  byte var14;
                  int var16;
                  byte var17;
                  byte[] var19;
                  byte[] var22;
                  int var23;
                  if (this.size() - var2 < var2) {
                     var4 = this.size();

                     for(var10 = var7; var4 > var2; var4 -= (long)(var10.limit - var10.pos)) {
                        var10 = var10.prev;
                        if (var10 == null) {
                           Intrinsics.throwNpe();
                        }
                     }

                     var11 = var8;
                     if (var10 == null) {
                        return var11;
                     }

                     if (var1.size() != 2) {
                        var19 = var1.internalArray$okio();

                        while(true) {
                           var11 = var8;
                           if (var4 >= this.size()) {
                              return var11;
                           }

                           var22 = var10.data;
                           var21 = (int)((long)var10.pos + var2 - var4);

                           for(var23 = var10.limit; var21 < var23; ++var21) {
                              var13 = var22[var21];
                              var16 = var19.length;

                              for(var15 = 0; var15 < var16; ++var15) {
                                 if (var13 == var19[var15]) {
                                    break label205;
                                 }
                              }
                           }

                           var4 += (long)(var10.limit - var10.pos);
                           var10 = var10.next;
                           if (var10 == null) {
                              Intrinsics.throwNpe();
                           }

                           var2 = var4;
                        }
                     }

                     var13 = var1.getByte(0);
                     var14 = var1.getByte(1);

                     label127:
                     while(true) {
                        var11 = var8;
                        if (var4 >= this.size()) {
                           return var11;
                        }

                        var22 = var10.data;
                        var15 = (int)((long)var10.pos + var2 - var4);

                        for(var16 = var10.limit; var15 < var16; ++var15) {
                           var17 = var22[var15];
                           var2 = var4;
                           var18 = var10;
                           var21 = var15;
                           if (var17 == var13) {
                              break label127;
                           }

                           if (var17 == var14) {
                              var2 = var4;
                              var18 = var10;
                              var21 = var15;
                              break label127;
                           }
                        }

                        var4 += (long)(var10.limit - var10.pos);
                        var10 = var10.next;
                        if (var10 == null) {
                           Intrinsics.throwNpe();
                        }

                        var2 = var4;
                     }
                  } else {
                     label181:
                     while(true) {
                        var11 = (long)(var10.limit - var10.pos) + var4;
                        if (var11 > var2) {
                           var11 = var8;
                           if (var10 == null) {
                              return var11;
                           }

                           if (var1.size() != 2) {
                              var19 = var1.internalArray$okio();

                              while(true) {
                                 var11 = var8;
                                 if (var4 >= this.size()) {
                                    return var11;
                                 }

                                 var22 = var10.data;
                                 var21 = (int)((long)var10.pos + var2 - var4);

                                 for(var23 = var10.limit; var21 < var23; ++var21) {
                                    var13 = var22[var21];
                                    var16 = var19.length;

                                    for(var15 = 0; var15 < var16; ++var15) {
                                       if (var13 == var19[var15]) {
                                          break label205;
                                       }
                                    }
                                 }

                                 var4 += (long)(var10.limit - var10.pos);
                                 var10 = var10.next;
                                 if (var10 == null) {
                                    Intrinsics.throwNpe();
                                 }

                                 var2 = var4;
                              }
                           }

                           var14 = var1.getByte(0);
                           var13 = var1.getByte(1);

                           while(true) {
                              var11 = var8;
                              if (var4 >= this.size()) {
                                 return var11;
                              }

                              var22 = var10.data;
                              var15 = (int)((long)var10.pos + var2 - var4);

                              for(var16 = var10.limit; var15 < var16; ++var15) {
                                 var17 = var22[var15];
                                 var2 = var4;
                                 var18 = var10;
                                 var21 = var15;
                                 if (var17 == var14) {
                                    break label181;
                                 }

                                 if (var17 == var13) {
                                    var2 = var4;
                                    var18 = var10;
                                    var21 = var15;
                                    break label181;
                                 }
                              }

                              var4 += (long)(var10.limit - var10.pos);
                              var10 = var10.next;
                              if (var10 == null) {
                                 Intrinsics.throwNpe();
                              }

                              var2 = var4;
                           }
                        }

                        var10 = var10.next;
                        if (var10 == null) {
                           Intrinsics.throwNpe();
                        }

                        var4 = var11;
                     }
                  }

                  var15 = var18.pos;
                  break label184;
               }

               var15 = var10.pos;
               var2 = var4;
            }

            var11 = (long)(var21 - var15) + var2;
         } else {
            var18 = (Segment)null;
            var11 = var8;
         }

         return var11;
      }
   }

   public InputStream inputStream() {
      return (InputStream)(new InputStream() {
         public int available() {
            return (int)Math.min(Buffer.this.size(), (long)Integer.MAX_VALUE);
         }

         public void close() {
         }

         public int read() {
            int var1;
            if (Buffer.this.size() > 0L) {
               var1 = Buffer.this.readByte() & 255;
            } else {
               var1 = -1;
            }

            return var1;
         }

         public int read(byte[] var1, int var2, int var3) {
            Intrinsics.checkParameterIsNotNull(var1, "sink");
            return Buffer.this.read(var1, var2, var3);
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(Buffer.this);
            var1.append(".inputStream()");
            return var1.toString();
         }
      });
   }

   public boolean isOpen() {
      return true;
   }

   public final ByteString md5() {
      return this.digest("MD5");
   }

   public OutputStream outputStream() {
      return (OutputStream)(new OutputStream() {
         public void close() {
         }

         public void flush() {
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(Buffer.this);
            var1.append(".outputStream()");
            return var1.toString();
         }

         public void write(int var1) {
            Buffer.this.writeByte(var1);
         }

         public void write(byte[] var1, int var2, int var3) {
            Intrinsics.checkParameterIsNotNull(var1, "data");
            Buffer.this.write(var1, var2, var3);
         }
      });
   }

   public BufferedSource peek() {
      return Okio.buffer((Source)(new PeekSource((BufferedSource)this)));
   }

   public boolean rangeEquals(long var1, ByteString var3) {
      Intrinsics.checkParameterIsNotNull(var3, "bytes");
      return this.rangeEquals(var1, var3, 0, var3.size());
   }

   public boolean rangeEquals(long var1, ByteString var3, int var4, int var5) {
      Intrinsics.checkParameterIsNotNull(var3, "bytes");
      boolean var6 = false;
      boolean var7 = var6;
      if (var1 >= 0L) {
         var7 = var6;
         if (var4 >= 0) {
            var7 = var6;
            if (var5 >= 0) {
               var7 = var6;
               if (this.size() - var1 >= (long)var5) {
                  if (var3.size() - var4 < var5) {
                     var7 = var6;
                  } else {
                     int var8 = 0;

                     while(true) {
                        if (var8 >= var5) {
                           var7 = true;
                           break;
                        }

                        if (this.getByte((long)var8 + var1) != var3.getByte(var4 + var8)) {
                           var7 = var6;
                           break;
                        }

                        ++var8;
                     }
                  }
               }
            }
         }
      }

      return var7;
   }

   public int read(ByteBuffer var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      Segment var2 = this.head;
      if (var2 != null) {
         int var3 = Math.min(var1.remaining(), var2.limit - var2.pos);
         var1.put(var2.data, var2.pos, var3);
         var2.pos += var3;
         this.size -= (long)var3;
         if (var2.pos == var2.limit) {
            this.head = var2.pop();
            SegmentPool.recycle(var2);
         }

         return var3;
      } else {
         return -1;
      }
   }

   public int read(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      _Util.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
      Segment var4 = this.head;
      if (var4 != null) {
         var3 = Math.min(var3, var4.limit - var4.pos);
         ArraysKt.copyInto(var4.data, var1, var2, var4.pos, var4.pos + var3);
         var4.pos += var3;
         this.setSize$okio(this.size() - (long)var3);
         var2 = var3;
         if (var4.pos == var4.limit) {
            this.head = var4.pop();
            SegmentPool.recycle(var4);
            var2 = var3;
         }
      } else {
         var2 = -1;
      }

      return var2;
   }

   public long read(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      boolean var4;
      if (var2 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (this.size() == 0L) {
            var2 = -1L;
         } else {
            long var5 = var2;
            if (var2 > this.size()) {
               var5 = this.size();
            }

            var1.write(this, var5);
            var2 = var5;
         }

         return var2;
      } else {
         StringBuilder var7 = new StringBuilder();
         var7.append("byteCount < 0: ");
         var7.append(var2);
         throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
      }
   }

   public long readAll(Sink var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var2 = this.size();
      if (var2 > 0L) {
         var1.write(this, var2);
      }

      return var2;
   }

   public final Buffer.UnsafeCursor readAndWriteUnsafe() {
      return readAndWriteUnsafe$default(this, (Buffer.UnsafeCursor)null, 1, (Object)null);
   }

   public final Buffer.UnsafeCursor readAndWriteUnsafe(Buffer.UnsafeCursor var1) {
      Intrinsics.checkParameterIsNotNull(var1, "unsafeCursor");
      boolean var2;
      if (var1.buffer == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var1.buffer = (Buffer)this;
         var1.readWrite = true;
         return var1;
      } else {
         throw (Throwable)(new IllegalStateException("already attached to a buffer".toString()));
      }
   }

   public byte readByte() throws EOFException {
      if (this.size() != 0L) {
         Segment var1 = this.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         byte[] var4 = var1.data;
         int var5 = var2 + 1;
         byte var6 = var4[var2];
         this.setSize$okio(this.size() - 1L);
         if (var5 == var3) {
            this.head = var1.pop();
            SegmentPool.recycle(var1);
         } else {
            var1.pos = var5;
         }

         return var6;
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public byte[] readByteArray() {
      return this.readByteArray(this.size());
   }

   public byte[] readByteArray(long var1) throws EOFException {
      boolean var3;
      if (var1 >= 0L && var1 <= (long)Integer.MAX_VALUE) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         if (this.size() >= var1) {
            byte[] var5 = new byte[(int)var1];
            this.readFully(var5);
            return var5;
         } else {
            throw (Throwable)(new EOFException());
         }
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("byteCount: ");
         var4.append(var1);
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   public ByteString readByteString() {
      return this.readByteString(this.size());
   }

   public ByteString readByteString(long var1) throws EOFException {
      boolean var3;
      if (var1 >= 0L && var1 <= (long)Integer.MAX_VALUE) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         if (this.size() >= var1) {
            ByteString var5;
            if (var1 >= (long)4096) {
               var5 = this.snapshot((int)var1);
               this.skip(var1);
            } else {
               var5 = new ByteString(this.readByteArray(var1));
            }

            return var5;
         } else {
            throw (Throwable)(new EOFException());
         }
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("byteCount: ");
         var4.append(var1);
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   public long readDecimalLong() throws EOFException {
      long var1 = this.size();
      long var3 = 0L;
      if (var1 == 0L) {
         throw (Throwable)(new EOFException());
      } else {
         long var5 = -7L;
         int var7 = 0;
         boolean var8 = false;
         boolean var9 = false;

         while(true) {
            Segment var10 = this.head;
            if (var10 == null) {
               Intrinsics.throwNpe();
            }

            byte[] var11 = var10.data;
            int var12 = var10.pos;
            int var13 = var10.limit;
            boolean var14 = var8;
            int var15 = var7;
            var1 = var3;
            var3 = var5;

            while(true) {
               label78: {
                  if (var12 < var13) {
                     byte var16 = var11[var12];
                     byte var17 = (byte)48;
                     StringBuilder var19;
                     if (var16 >= var17 && var16 <= (byte)57) {
                        var7 = var17 - var16;
                        long var21;
                        int var18 = (var21 = var1 - -922337203685477580L) == 0L ? 0 : (var21 < 0L ? -1 : 1);
                        if (var18 >= 0 && (var18 != 0 || (long)var7 >= var3)) {
                           var1 = var1 * 10L + (long)var7;
                           break label78;
                        }

                        Buffer var20 = (new Buffer()).writeDecimalLong(var1).writeByte(var16);
                        if (!var14) {
                           var20.readByte();
                        }

                        var19 = new StringBuilder();
                        var19.append("Number too large: ");
                        var19.append(var20.readUtf8());
                        throw (Throwable)(new NumberFormatException(var19.toString()));
                     }

                     if (var16 == (byte)45 && var15 == 0) {
                        --var3;
                        var14 = true;
                        break label78;
                     }

                     if (var15 == 0) {
                        var19 = new StringBuilder();
                        var19.append("Expected leading [0-9] or '-' character but was 0x");
                        var19.append(_Util.toHexString(var16));
                        throw (Throwable)(new NumberFormatException(var19.toString()));
                     }

                     var9 = true;
                  }

                  if (var12 == var13) {
                     this.head = var10.pop();
                     SegmentPool.recycle(var10);
                  } else {
                     var10.pos = var12;
                  }

                  if (!var9) {
                     var5 = var3;
                     var3 = var1;
                     var7 = var15;
                     var8 = var14;
                     if (this.head != null) {
                        break;
                     }
                  }

                  this.setSize$okio(this.size() - (long)var15);
                  if (!var14) {
                     var1 = -var1;
                  }

                  return var1;
               }

               ++var12;
               ++var15;
            }
         }
      }
   }

   public final Buffer readFrom(InputStream var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      this.readFrom(var1, Long.MAX_VALUE, true);
      return this;
   }

   public final Buffer readFrom(InputStream var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      boolean var4;
      if (var2 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         this.readFrom(var1, var2, false);
         return this;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("byteCount < 0: ");
         var5.append(var2);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public void readFully(Buffer var1, long var2) throws EOFException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      if (this.size() >= var2) {
         var1.write(this, var2);
      } else {
         var1.write(this, this.size());
         throw (Throwable)(new EOFException());
      }
   }

   public void readFully(byte[] var1) throws EOFException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      int var3;
      for(int var2 = 0; var2 < var1.length; var2 += var3) {
         var3 = this.read(var1, var2, var1.length - var2);
         if (var3 == -1) {
            throw (Throwable)(new EOFException());
         }
      }

   }

   public long readHexadecimalUnsignedLong() throws EOFException {
      if (this.size() == 0L) {
         throw (Throwable)(new EOFException());
      } else {
         int var1 = 0;
         long var2 = 0L;
         boolean var4 = false;

         long var9;
         int var11;
         do {
            Segment var5 = this.head;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            byte[] var6 = var5.data;
            int var7 = var5.pos;
            int var8 = var5.limit;
            var9 = var2;
            var11 = var1;

            boolean var12;
            while(true) {
               var12 = var4;
               if (var7 >= var8) {
                  break;
               }

               byte var13 = var6[var7];
               byte var14 = (byte)48;
               StringBuilder var15;
               if (var13 >= var14 && var13 <= (byte)57) {
                  var1 = var13 - var14;
               } else {
                  var14 = (byte)97;
                  if (var13 < var14 || var13 > (byte)102) {
                     var14 = (byte)65;
                     if (var13 < var14 || var13 > (byte)70) {
                        if (var11 == 0) {
                           var15 = new StringBuilder();
                           var15.append("Expected leading [0-9a-fA-F] character but was 0x");
                           var15.append(_Util.toHexString(var13));
                           throw (Throwable)(new NumberFormatException(var15.toString()));
                        }

                        var12 = true;
                        break;
                     }
                  }

                  var1 = var13 - var14 + 10;
               }

               if ((-1152921504606846976L & var9) != 0L) {
                  Buffer var16 = (new Buffer()).writeHexadecimalUnsignedLong(var9).writeByte(var13);
                  var15 = new StringBuilder();
                  var15.append("Number too large: ");
                  var15.append(var16.readUtf8());
                  throw (Throwable)(new NumberFormatException(var15.toString()));
               }

               var9 = var9 << 4 | (long)var1;
               ++var7;
               ++var11;
            }

            if (var7 == var8) {
               this.head = var5.pop();
               SegmentPool.recycle(var5);
            } else {
               var5.pos = var7;
            }

            if (var12) {
               break;
            }

            var1 = var11;
            var4 = var12;
            var2 = var9;
         } while(this.head != null);

         this.setSize$okio(this.size() - (long)var11);
         return var9;
      }
   }

   public int readInt() throws EOFException {
      if (this.size() >= 4L) {
         Segment var1 = this.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         if ((long)(var3 - var2) < 4L) {
            var3 = (this.readByte() & 255) << 24 | (this.readByte() & 255) << 16 | (this.readByte() & 255) << 8 | this.readByte() & 255;
         } else {
            byte[] var4 = var1.data;
            int var5 = var2 + 1;
            byte var9 = var4[var2];
            int var6 = var5 + 1;
            byte var10 = var4[var5];
            int var7 = var6 + 1;
            byte var11 = var4[var6];
            int var8 = var7 + 1;
            byte var12 = var4[var7];
            this.setSize$okio(this.size() - 4L);
            if (var8 == var3) {
               this.head = var1.pop();
               SegmentPool.recycle(var1);
            } else {
               var1.pos = var8;
            }

            var3 = (var9 & 255) << 24 | (var10 & 255) << 16 | (var11 & 255) << 8 | var12 & 255;
         }

         return var3;
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public int readIntLe() throws EOFException {
      return _Util.reverseBytes(this.readInt());
   }

   public long readLong() throws EOFException {
      if (this.size() >= 8L) {
         Segment var1 = this.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         long var4;
         if ((long)(var3 - var2) < 8L) {
            var4 = ((long)this.readInt() & 4294967295L) << 32 | 4294967295L & (long)this.readInt();
         } else {
            byte[] var6 = var1.data;
            int var7 = var2 + 1;
            long var8 = (long)var6[var2];
            var2 = var7 + 1;
            long var10 = (long)var6[var7];
            var7 = var2 + 1;
            long var12 = (long)var6[var2];
            var2 = var7 + 1;
            long var14 = (long)var6[var7];
            var7 = var2 + 1;
            var4 = (long)var6[var2];
            var2 = var7 + 1;
            long var16 = (long)var6[var7];
            var7 = var2 + 1;
            long var18 = (long)var6[var2];
            var2 = var7 + 1;
            long var20 = (long)var6[var7];
            this.setSize$okio(this.size() - 8L);
            if (var2 == var3) {
               this.head = var1.pop();
               SegmentPool.recycle(var1);
            } else {
               var1.pos = var2;
            }

            var4 = (var14 & 255L) << 32 | (var8 & 255L) << 56 | (var10 & 255L) << 48 | (var12 & 255L) << 40 | (var4 & 255L) << 24 | (var16 & 255L) << 16 | (var18 & 255L) << 8 | var20 & 255L;
         }

         return var4;
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public long readLongLe() throws EOFException {
      return _Util.reverseBytes(this.readLong());
   }

   public short readShort() throws EOFException {
      if (this.size() >= 2L) {
         Segment var1 = this.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         short var4;
         short var9;
         if (var3 - var2 < 2) {
            var9 = (short)((this.readByte() & 255) << 8 | this.readByte() & 255);
            var4 = var9;
         } else {
            byte[] var5 = var1.data;
            int var6 = var2 + 1;
            byte var8 = var5[var2];
            int var7 = var6 + 1;
            byte var10 = var5[var6];
            this.setSize$okio(this.size() - 2L);
            if (var7 == var3) {
               this.head = var1.pop();
               SegmentPool.recycle(var1);
            } else {
               var1.pos = var7;
            }

            var9 = (short)((var8 & 255) << 8 | var10 & 255);
            var4 = var9;
         }

         return var4;
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public short readShortLe() throws EOFException {
      return _Util.reverseBytes(this.readShort());
   }

   public String readString(long var1, Charset var3) throws EOFException {
      Intrinsics.checkParameterIsNotNull(var3, "charset");
      long var11;
      int var4 = (var11 = var1 - 0L) == 0L ? 0 : (var11 < 0L ? -1 : 1);
      boolean var5;
      if (var4 >= 0 && var1 <= (long)Integer.MAX_VALUE) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         if (this.size >= var1) {
            if (var4 == 0) {
               return "";
            } else {
               Segment var6 = this.head;
               if (var6 == null) {
                  Intrinsics.throwNpe();
               }

               if ((long)var6.pos + var1 > (long)var6.limit) {
                  return new String(this.readByteArray(var1), var3);
               } else {
                  byte[] var7 = var6.data;
                  var4 = var6.pos;
                  int var10 = (int)var1;
                  String var9 = new String(var7, var4, var10, var3);
                  var6.pos += var10;
                  this.size -= var1;
                  if (var6.pos == var6.limit) {
                     this.head = var6.pop();
                     SegmentPool.recycle(var6);
                  }

                  return var9;
               }
            }
         } else {
            throw (Throwable)(new EOFException());
         }
      } else {
         StringBuilder var8 = new StringBuilder();
         var8.append("byteCount: ");
         var8.append(var1);
         throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
      }
   }

   public String readString(Charset var1) {
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      return this.readString(this.size, var1);
   }

   public final Buffer.UnsafeCursor readUnsafe() {
      return readUnsafe$default(this, (Buffer.UnsafeCursor)null, 1, (Object)null);
   }

   public final Buffer.UnsafeCursor readUnsafe(Buffer.UnsafeCursor var1) {
      Intrinsics.checkParameterIsNotNull(var1, "unsafeCursor");
      boolean var2;
      if (var1.buffer == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var1.buffer = (Buffer)this;
         var1.readWrite = false;
         return var1;
      } else {
         throw (Throwable)(new IllegalStateException("already attached to a buffer".toString()));
      }
   }

   public String readUtf8() {
      return this.readString(this.size, Charsets.UTF_8);
   }

   public String readUtf8(long var1) throws EOFException {
      return this.readString(var1, Charsets.UTF_8);
   }

   public int readUtf8CodePoint() throws EOFException {
      if (this.size() == 0L) {
         throw (Throwable)(new EOFException());
      } else {
         byte var1 = this.getByte(0L);
         int var2 = 1;
         char var3 = '�';
         int var4;
         byte var5;
         int var6;
         if ((var1 & 128) == 0) {
            var4 = var1 & 127;
            var5 = 1;
            var6 = 0;
         } else if ((var1 & 224) == 192) {
            var4 = var1 & 31;
            var5 = 2;
            var6 = 128;
         } else if ((var1 & 240) == 224) {
            var4 = var1 & 15;
            var5 = 3;
            var6 = 2048;
         } else {
            if ((var1 & 248) != 240) {
               this.skip(1L);
               var4 = var3;
               return var4;
            }

            var4 = var1 & 7;
            var5 = 4;
            var6 = 65536;
         }

         long var7 = this.size();
         long var9 = (long)var5;
         if (var7 < var9) {
            StringBuilder var12 = new StringBuilder();
            var12.append("size < ");
            var12.append(var5);
            var12.append(": ");
            var12.append(this.size());
            var12.append(" (to read code point prefixed 0x");
            var12.append(_Util.toHexString(var1));
            var12.append(')');
            throw (Throwable)(new EOFException(var12.toString()));
         } else {
            while(true) {
               if (var2 >= var5) {
                  this.skip(var9);
                  if (var4 > 1114111) {
                     var4 = var3;
                  } else if (55296 <= var4 && 57343 >= var4) {
                     var4 = var3;
                  } else if (var4 < var6) {
                     var4 = var3;
                  }
                  break;
               }

               var7 = (long)var2;
               byte var11 = this.getByte(var7);
               if ((var11 & 192) != 128) {
                  this.skip(var7);
                  var4 = var3;
                  break;
               }

               var4 = var4 << 6 | var11 & 63;
               ++var2;
            }

            return var4;
         }
      }
   }

   public String readUtf8Line() throws EOFException {
      long var1 = this.indexOf((byte)10);
      String var3;
      if (var1 != -1L) {
         var3 = BufferKt.readUtf8Line(this, var1);
      } else if (this.size() != 0L) {
         var3 = this.readUtf8(this.size());
      } else {
         var3 = null;
      }

      return var3;
   }

   public String readUtf8LineStrict() throws EOFException {
      return this.readUtf8LineStrict(Long.MAX_VALUE);
   }

   public String readUtf8LineStrict(long var1) throws EOFException {
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      StringBuilder var9;
      if (var3) {
         long var4 = Long.MAX_VALUE;
         if (var1 != Long.MAX_VALUE) {
            var4 = var1 + 1L;
         }

         byte var6 = (byte)10;
         long var7 = this.indexOf(var6, 0L, var4);
         String var11;
         if (var7 != -1L) {
            var11 = BufferKt.readUtf8Line(this, var7);
         } else {
            if (var4 >= this.size() || this.getByte(var4 - 1L) != (byte)13 || this.getByte(var4) != var6) {
               Buffer var10 = new Buffer();
               var4 = this.size();
               this.copyTo(var10, 0L, Math.min((long)32, var4));
               var9 = new StringBuilder();
               var9.append("\\n not found: limit=");
               var9.append(Math.min(this.size(), var1));
               var9.append(" content=");
               var9.append(var10.readByteString().hex());
               var9.append('…');
               throw (Throwable)(new EOFException(var9.toString()));
            }

            var11 = BufferKt.readUtf8Line(this, var4);
         }

         return var11;
      } else {
         var9 = new StringBuilder();
         var9.append("limit < 0: ");
         var9.append(var1);
         throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
      }
   }

   public boolean request(long var1) {
      boolean var3;
      if (this.size >= var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void require(long var1) throws EOFException {
      if (this.size < var1) {
         throw (Throwable)(new EOFException());
      }
   }

   public int select(Options var1) {
      Intrinsics.checkParameterIsNotNull(var1, "options");
      int var2 = BufferKt.selectPrefix$default(this, var1, false, 2, (Object)null);
      if (var2 == -1) {
         var2 = -1;
      } else {
         this.skip((long)var1.getByteStrings$okio()[var2].size());
      }

      return var2;
   }

   public final void setSize$okio(long var1) {
      this.size = var1;
   }

   public final ByteString sha1() {
      return this.digest("SHA-1");
   }

   public final ByteString sha256() {
      return this.digest("SHA-256");
   }

   public final ByteString sha512() {
      return this.digest("SHA-512");
   }

   public final long size() {
      return this.size;
   }

   public void skip(long var1) throws EOFException {
      while(true) {
         if (var1 > 0L) {
            Segment var3 = this.head;
            if (var3 != null) {
               int var4 = (int)Math.min(var1, (long)(var3.limit - var3.pos));
               long var5 = this.size();
               long var7 = (long)var4;
               this.setSize$okio(var5 - var7);
               var7 = var1 - var7;
               var3.pos += var4;
               var1 = var7;
               if (var3.pos == var3.limit) {
                  this.head = var3.pop();
                  SegmentPool.recycle(var3);
                  var1 = var7;
               }
               continue;
            }

            throw (Throwable)(new EOFException());
         }

         return;
      }
   }

   public final ByteString snapshot() {
      boolean var1;
      if (this.size() <= (long)Integer.MAX_VALUE) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return this.snapshot((int)this.size());
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("size > Int.MAX_VALUE: ");
         var2.append(this.size());
         throw (Throwable)(new IllegalStateException(var2.toString().toString()));
      }
   }

   public final ByteString snapshot(int var1) {
      ByteString var2;
      if (var1 == 0) {
         var2 = ByteString.EMPTY;
      } else {
         _Util.checkOffsetAndCount(this.size(), 0L, (long)var1);
         Segment var8 = this.head;
         byte var3 = 0;
         int var4 = 0;

         int var5;
         for(var5 = 0; var4 < var1; var8 = var8.next) {
            if (var8 == null) {
               Intrinsics.throwNpe();
            }

            if (var8.limit == var8.pos) {
               throw (Throwable)(new AssertionError("s.limit == s.pos"));
            }

            var4 += var8.limit - var8.pos;
            ++var5;
         }

         byte[][] var6 = new byte[var5][];
         int[] var7 = new int[var5 * 2];
         var8 = this.head;
         var5 = 0;

         for(var4 = var3; var4 < var1; var8 = var8.next) {
            if (var8 == null) {
               Intrinsics.throwNpe();
            }

            var6[var5] = var8.data;
            var4 += var8.limit - var8.pos;
            var7[var5] = Math.min(var4, var1);
            var7[((Object[])var6).length + var5] = var8.pos;
            var8.shared = true;
            ++var5;
         }

         var2 = (ByteString)(new SegmentedByteString((byte[][])var6, var7));
      }

      return var2;
   }

   public Timeout timeout() {
      return Timeout.NONE;
   }

   public String toString() {
      return this.snapshot().toString();
   }

   public final Segment writableSegment$okio(int var1) {
      boolean var2 = true;
      if (var1 < 1 || var1 > 8192) {
         var2 = false;
      }

      if (!var2) {
         throw (Throwable)(new IllegalArgumentException("unexpected capacity".toString()));
      } else {
         Segment var3 = this.head;
         if (var3 == null) {
            var3 = SegmentPool.take();
            this.head = var3;
            var3.prev = var3;
            var3.next = var3;
         } else {
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            var3 = var3.prev;
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            if (var3.limit + var1 > 8192 || !var3.owner) {
               var3 = var3.push(SegmentPool.take());
            }
         }

         return var3;
      }
   }

   public int write(ByteBuffer var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      int var2 = var1.remaining();

      Segment var4;
      int var5;
      for(int var3 = var2; var3 > 0; var4.limit += var5) {
         var4 = this.writableSegment$okio(1);
         var5 = Math.min(var3, 8192 - var4.limit);
         var1.get(var4.data, var4.limit, var5);
         var3 -= var5;
      }

      this.size += (long)var2;
      return var2;
   }

   public Buffer write(ByteString var1) {
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      var1.write$okio(this, 0, var1.size());
      return this;
   }

   public Buffer write(ByteString var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      var1.write$okio(this, var2, var3);
      return this;
   }

   public Buffer write(Source var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");

      while(var2 > 0L) {
         long var4 = var1.read(this, var2);
         if (var4 == -1L) {
            throw (Throwable)(new EOFException());
         }

         var2 -= var4;
      }

      return this;
   }

   public Buffer write(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      return this.write((byte[])var1, 0, var1.length);
   }

   public Buffer write(byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var4 = (long)var1.length;
      long var6 = (long)var2;
      long var8 = (long)var3;
      _Util.checkOffsetAndCount(var4, var6, var8);

      for(int var10 = var3 + var2; var2 < var10; var2 = var3) {
         Segment var11 = this.writableSegment$okio(1);
         int var12 = Math.min(var10 - var2, 8192 - var11.limit);
         byte[] var13 = var11.data;
         int var14 = var11.limit;
         var3 = var2 + var12;
         ArraysKt.copyInto(var1, var13, var14, var2, var3);
         var11.limit += var12;
      }

      this.setSize$okio(this.size() + var8);
      return this;
   }

   public void write(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      boolean var4;
      if (var1 != this) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         throw (Throwable)(new IllegalArgumentException("source == this".toString()));
      } else {
         _Util.checkOffsetAndCount(var1.size(), 0L, var2);

         while(var2 > 0L) {
            Segment var5 = var1.head;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            int var9 = var5.limit;
            var5 = var1.head;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            long var6;
            Segment var8;
            if (var2 < (long)(var9 - var5.pos)) {
               var5 = this.head;
               if (var5 != null) {
                  if (var5 == null) {
                     Intrinsics.throwNpe();
                  }

                  var5 = var5.prev;
               } else {
                  var5 = null;
               }

               if (var5 != null && var5.owner) {
                  var6 = (long)var5.limit;
                  if (var5.shared) {
                     var9 = 0;
                  } else {
                     var9 = var5.pos;
                  }

                  if (var6 + var2 - (long)var9 <= (long)8192) {
                     var8 = var1.head;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }

                     var8.writeTo(var5, (int)var2);
                     var1.setSize$okio(var1.size() - var2);
                     this.setSize$okio(this.size() + var2);
                     break;
                  }
               }

               var5 = var1.head;
               if (var5 == null) {
                  Intrinsics.throwNpe();
               }

               var1.head = var5.split((int)var2);
            }

            var5 = var1.head;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            var6 = (long)(var5.limit - var5.pos);
            var1.head = var5.pop();
            var8 = this.head;
            if (var8 == null) {
               this.head = var5;
               var5.prev = var5;
               var5.next = var5.prev;
            } else {
               if (var8 == null) {
                  Intrinsics.throwNpe();
               }

               var8 = var8.prev;
               if (var8 == null) {
                  Intrinsics.throwNpe();
               }

               var8.push(var5).compact();
            }

            var1.setSize$okio(var1.size() - var6);
            this.setSize$okio(this.size() + var6);
            var2 -= var6;
         }

      }
   }

   public long writeAll(Source var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var2 = 0L;

      while(true) {
         long var4 = var1.read(this, (long)8192);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
      }
   }

   public Buffer writeByte(int var1) {
      Segment var2 = this.writableSegment$okio(1);
      byte[] var3 = var2.data;
      int var4 = var2.limit++;
      var3[var4] = (byte)((byte)var1);
      this.setSize$okio(this.size() + 1L);
      return this;
   }

   public Buffer writeDecimalLong(long var1) {
      long var13;
      int var3 = (var13 = var1 - 0L) == 0L ? 0 : (var13 < 0L ? -1 : 1);
      Buffer var4;
      if (var3 == 0) {
         var4 = this.writeByte(48);
      } else {
         boolean var5 = false;
         byte var6 = 1;
         long var7 = var1;
         if (var3 < 0) {
            var7 = -var1;
            if (var7 < 0L) {
               var4 = this.writeUtf8("-9223372036854775808");
               return var4;
            }

            var5 = true;
         }

         if (var7 < 100000000L) {
            if (var7 < 10000L) {
               if (var7 < 100L) {
                  if (var7 >= 10L) {
                     var6 = 2;
                  }
               } else if (var7 < 1000L) {
                  var6 = 3;
               } else {
                  var6 = 4;
               }
            } else if (var7 < 1000000L) {
               if (var7 < 100000L) {
                  var6 = 5;
               } else {
                  var6 = 6;
               }
            } else if (var7 < 10000000L) {
               var6 = 7;
            } else {
               var6 = 8;
            }
         } else if (var7 < 1000000000000L) {
            if (var7 < 10000000000L) {
               if (var7 < 1000000000L) {
                  var6 = 9;
               } else {
                  var6 = 10;
               }
            } else if (var7 < 100000000000L) {
               var6 = 11;
            } else {
               var6 = 12;
            }
         } else if (var7 < 1000000000000000L) {
            if (var7 < 10000000000000L) {
               var6 = 13;
            } else if (var7 < 100000000000000L) {
               var6 = 14;
            } else {
               var6 = 15;
            }
         } else if (var7 < 100000000000000000L) {
            if (var7 < 10000000000000000L) {
               var6 = 16;
            } else {
               var6 = 17;
            }
         } else if (var7 < 1000000000000000000L) {
            var6 = 18;
         } else {
            var6 = 19;
         }

         var3 = var6;
         if (var5) {
            var3 = var6 + 1;
         }

         Segment var11 = this.writableSegment$okio(var3);
         byte[] var9 = var11.data;

         int var12;
         for(var12 = var11.limit + var3; var7 != 0L; var7 /= var1) {
            var1 = (long)10;
            int var10 = (int)(var7 % var1);
            --var12;
            var9[var12] = (byte)BufferKt.getHEX_DIGIT_BYTES()[var10];
         }

         if (var5) {
            var9[var12 - 1] = (byte)((byte)45);
         }

         var11.limit += var3;
         this.setSize$okio(this.size() + (long)var3);
         var4 = this;
      }

      return var4;
   }

   public Buffer writeHexadecimalUnsignedLong(long var1) {
      Buffer var3;
      if (var1 == 0L) {
         var3 = this.writeByte(48);
      } else {
         long var4 = var1 >>> 1 | var1;
         var4 |= var4 >>> 2;
         var4 |= var4 >>> 4;
         var4 |= var4 >>> 8;
         var4 |= var4 >>> 16;
         var4 |= var4 >>> 32;
         var4 -= var4 >>> 1 & 6148914691236517205L;
         var4 = (var4 >>> 2 & 3689348814741910323L) + (var4 & 3689348814741910323L);
         var4 = (var4 >>> 4) + var4 & 1085102592571150095L;
         var4 += var4 >>> 8;
         var4 += var4 >>> 16;
         int var6 = (int)(((var4 & 63L) + (var4 >>> 32 & 63L) + (long)3) / (long)4);
         Segment var7 = this.writableSegment$okio(var6);
         byte[] var10 = var7.data;
         int var8 = var7.limit + var6 - 1;

         for(int var9 = var7.limit; var8 >= var9; --var8) {
            var10[var8] = (byte)BufferKt.getHEX_DIGIT_BYTES()[(int)(15L & var1)];
            var1 >>>= 4;
         }

         var7.limit += var6;
         this.setSize$okio(this.size() + (long)var6);
         var3 = this;
      }

      return var3;
   }

   public Buffer writeInt(int var1) {
      Segment var2 = this.writableSegment$okio(4);
      byte[] var3 = var2.data;
      int var4 = var2.limit;
      int var5 = var4 + 1;
      var3[var4] = (byte)((byte)(var1 >>> 24 & 255));
      var4 = var5 + 1;
      var3[var5] = (byte)((byte)(var1 >>> 16 & 255));
      var5 = var4 + 1;
      var3[var4] = (byte)((byte)(var1 >>> 8 & 255));
      var3[var5] = (byte)((byte)(var1 & 255));
      var2.limit = var5 + 1;
      this.setSize$okio(this.size() + 4L);
      return this;
   }

   public Buffer writeIntLe(int var1) {
      return this.writeInt(_Util.reverseBytes(var1));
   }

   public Buffer writeLong(long var1) {
      Segment var3 = this.writableSegment$okio(8);
      byte[] var4 = var3.data;
      int var5 = var3.limit;
      int var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 56 & 255L)));
      var5 = var6 + 1;
      var4[var6] = (byte)((byte)((int)(var1 >>> 48 & 255L)));
      var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 40 & 255L)));
      int var7 = var6 + 1;
      var4[var6] = (byte)((byte)((int)(var1 >>> 32 & 255L)));
      var5 = var7 + 1;
      var4[var7] = (byte)((byte)((int)(var1 >>> 24 & 255L)));
      var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 16 & 255L)));
      var5 = var6 + 1;
      var4[var6] = (byte)((byte)((int)(var1 >>> 8 & 255L)));
      var4[var5] = (byte)((byte)((int)(var1 & 255L)));
      var3.limit = var5 + 1;
      this.setSize$okio(this.size() + 8L);
      return this;
   }

   public Buffer writeLongLe(long var1) {
      return this.writeLong(_Util.reverseBytes(var1));
   }

   public Buffer writeShort(int var1) {
      Segment var2 = this.writableSegment$okio(2);
      byte[] var3 = var2.data;
      int var4 = var2.limit;
      int var5 = var4 + 1;
      var3[var4] = (byte)((byte)(var1 >>> 8 & 255));
      var3[var5] = (byte)((byte)(var1 & 255));
      var2.limit = var5 + 1;
      this.setSize$okio(this.size() + 2L);
      return this;
   }

   public Buffer writeShortLe(int var1) {
      return this.writeShort(_Util.reverseBytes((short)var1));
   }

   public Buffer writeString(String var1, int var2, int var3, Charset var4) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      Intrinsics.checkParameterIsNotNull(var4, "charset");
      boolean var5 = true;
      boolean var6;
      if (var2 >= 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      StringBuilder var7;
      if (var6) {
         if (var3 >= var2) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            if (var3 <= var1.length()) {
               var6 = var5;
            } else {
               var6 = false;
            }

            if (var6) {
               if (Intrinsics.areEqual((Object)var4, (Object)Charsets.UTF_8)) {
                  return this.writeUtf8(var1, var2, var3);
               } else {
                  var1 = var1.substring(var2, var3);
                  Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                  if (var1 != null) {
                     byte[] var8 = var1.getBytes(var4);
                     Intrinsics.checkExpressionValueIsNotNull(var8, "(this as java.lang.String).getBytes(charset)");
                     return this.write((byte[])var8, 0, var8.length);
                  } else {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }
               }
            } else {
               StringBuilder var9 = new StringBuilder();
               var9.append("endIndex > string.length: ");
               var9.append(var3);
               var9.append(" > ");
               var9.append(var1.length());
               throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
            }
         } else {
            var7 = new StringBuilder();
            var7.append("endIndex < beginIndex: ");
            var7.append(var3);
            var7.append(" < ");
            var7.append(var2);
            throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
         }
      } else {
         var7 = new StringBuilder();
         var7.append("beginIndex < 0: ");
         var7.append(var2);
         throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
      }
   }

   public Buffer writeString(String var1, Charset var2) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      Intrinsics.checkParameterIsNotNull(var2, "charset");
      return this.writeString(var1, 0, var1.length(), var2);
   }

   public final Buffer writeTo(OutputStream var1) throws IOException {
      return writeTo$default(this, var1, 0L, 2, (Object)null);
   }

   public final Buffer writeTo(OutputStream var1, long var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "out");
      _Util.checkOffsetAndCount(this.size, 0L, var2);
      Segment var4 = this.head;

      while(var2 > 0L) {
         if (var4 == null) {
            Intrinsics.throwNpe();
         }

         int var5 = (int)Math.min(var2, (long)(var4.limit - var4.pos));
         var1.write(var4.data, var4.pos, var5);
         var4.pos += var5;
         long var6 = this.size;
         long var8 = (long)var5;
         this.size = var6 - var8;
         var8 = var2 - var8;
         var2 = var8;
         if (var4.pos == var4.limit) {
            Segment var10 = var4.pop();
            this.head = var10;
            SegmentPool.recycle(var4);
            var4 = var10;
            var2 = var8;
         }
      }

      return this;
   }

   public Buffer writeUtf8(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      return this.writeUtf8(var1, 0, var1.length());
   }

   public Buffer writeUtf8(String var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var1, "string");
      boolean var4;
      if (var2 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var10;
      if (!var4) {
         var10 = new StringBuilder();
         var10.append("beginIndex < 0: ");
         var10.append(var2);
         throw (Throwable)(new IllegalArgumentException(var10.toString().toString()));
      } else {
         if (var3 >= var2) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            var10 = new StringBuilder();
            var10.append("endIndex < beginIndex: ");
            var10.append(var3);
            var10.append(" < ");
            var10.append(var2);
            throw (Throwable)(new IllegalArgumentException(var10.toString().toString()));
         } else {
            if (var3 <= var1.length()) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               StringBuilder var14 = new StringBuilder();
               var14.append("endIndex > string.length: ");
               var14.append(var3);
               var14.append(" > ");
               var14.append(var1.length());
               throw (Throwable)(new IllegalArgumentException(var14.toString().toString()));
            } else {
               while(true) {
                  while(var2 < var3) {
                     char var5 = var1.charAt(var2);
                     int var9;
                     char var11;
                     int var13;
                     if (var5 < 128) {
                        Segment var6 = this.writableSegment$okio(1);
                        byte[] var12 = var6.data;
                        int var8 = var6.limit - var2;
                        var9 = Math.min(var3, 8192 - var8);
                        var13 = var2 + 1;
                        var12[var2 + var8] = (byte)((byte)var5);

                        for(var2 = var13; var2 < var9; ++var2) {
                           var11 = var1.charAt(var2);
                           if (var11 >= 128) {
                              break;
                           }

                           var12[var2 + var8] = (byte)((byte)var11);
                        }

                        var13 = var8 + var2 - var6.limit;
                        var6.limit += var13;
                        this.setSize$okio(this.size() + (long)var13);
                     } else {
                        Segment var7;
                        if (var5 < 2048) {
                           var7 = this.writableSegment$okio(2);
                           var7.data[var7.limit] = (byte)((byte)(var5 >> 6 | 192));
                           var7.data[var7.limit + 1] = (byte)((byte)(var5 & 63 | 128));
                           var7.limit += 2;
                           this.setSize$okio(this.size() + 2L);
                        } else {
                           if (var5 >= '\ud800' && var5 <= '\udfff') {
                              var9 = var2 + 1;
                              if (var9 < var3) {
                                 var11 = var1.charAt(var9);
                              } else {
                                 var11 = 0;
                              }

                              if (var5 <= '\udbff' && '\udc00' <= var11 && '\udfff' >= var11) {
                                 var13 = ((var5 & 1023) << 10 | var11 & 1023) + 65536;
                                 var7 = this.writableSegment$okio(4);
                                 var7.data[var7.limit] = (byte)((byte)(var13 >> 18 | 240));
                                 var7.data[var7.limit + 1] = (byte)((byte)(var13 >> 12 & 63 | 128));
                                 var7.data[var7.limit + 2] = (byte)((byte)(var13 >> 6 & 63 | 128));
                                 var7.data[var7.limit + 3] = (byte)((byte)(var13 & 63 | 128));
                                 var7.limit += 4;
                                 this.setSize$okio(this.size() + 4L);
                                 var2 += 2;
                                 continue;
                              }

                              this.writeByte(63);
                              var2 = var9;
                              continue;
                           }

                           var7 = this.writableSegment$okio(3);
                           var7.data[var7.limit] = (byte)((byte)(var5 >> 12 | 224));
                           var7.data[var7.limit + 1] = (byte)((byte)(63 & var5 >> 6 | 128));
                           var7.data[var7.limit + 2] = (byte)((byte)(var5 & 63 | 128));
                           var7.limit += 3;
                           this.setSize$okio(this.size() + 3L);
                        }

                        ++var2;
                     }
                  }

                  return this;
               }
            }
         }
      }
   }

   public Buffer writeUtf8CodePoint(int var1) {
      if (var1 < 128) {
         this.writeByte(var1);
      } else {
         Segment var2;
         if (var1 < 2048) {
            var2 = this.writableSegment$okio(2);
            var2.data[var2.limit] = (byte)((byte)(var1 >> 6 | 192));
            var2.data[var2.limit + 1] = (byte)((byte)(var1 & 63 | 128));
            var2.limit += 2;
            this.setSize$okio(this.size() + 2L);
         } else if (55296 <= var1 && 57343 >= var1) {
            this.writeByte(63);
         } else if (var1 < 65536) {
            var2 = this.writableSegment$okio(3);
            var2.data[var2.limit] = (byte)((byte)(var1 >> 12 | 224));
            var2.data[var2.limit + 1] = (byte)((byte)(var1 >> 6 & 63 | 128));
            var2.data[var2.limit + 2] = (byte)((byte)(var1 & 63 | 128));
            var2.limit += 3;
            this.setSize$okio(this.size() + 3L);
         } else {
            if (var1 > 1114111) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Unexpected code point: 0x");
               var3.append(_Util.toHexString(var1));
               throw (Throwable)(new IllegalArgumentException(var3.toString()));
            }

            var2 = this.writableSegment$okio(4);
            var2.data[var2.limit] = (byte)((byte)(var1 >> 18 | 240));
            var2.data[var2.limit + 1] = (byte)((byte)(var1 >> 12 & 63 | 128));
            var2.data[var2.limit + 2] = (byte)((byte)(var1 >> 6 & 63 | 128));
            var2.data[var2.limit + 3] = (byte)((byte)(var1 & 63 | 128));
            var2.limit += 4;
            this.setSize$okio(this.size() + 4L);
         }
      }

      return this;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\bJ\u0006\u0010\u0014\u001a\u00020\bJ\u000e\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nJ\u000e\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
      d2 = {"Lokio/Buffer$UnsafeCursor;", "Ljava/io/Closeable;", "()V", "buffer", "Lokio/Buffer;", "data", "", "end", "", "offset", "", "readWrite", "", "segment", "Lokio/Segment;", "start", "close", "", "expandBuffer", "minByteCount", "next", "resizeBuffer", "newSize", "seek", "okio"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class UnsafeCursor implements Closeable {
      public Buffer buffer;
      public byte[] data;
      public int end = -1;
      public long offset = -1L;
      public boolean readWrite;
      private Segment segment;
      public int start = -1;

      public void close() {
         boolean var1;
         if (this.buffer != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            this.buffer = (Buffer)null;
            this.segment = (Segment)null;
            this.offset = -1L;
            this.data = (byte[])null;
            this.start = -1;
            this.end = -1;
         } else {
            throw (Throwable)(new IllegalStateException("not attached to a buffer".toString()));
         }
      }

      public final long expandBuffer(int var1) {
         boolean var2 = true;
         boolean var3;
         if (var1 > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         StringBuilder var7;
         if (var3) {
            if (var1 <= 8192) {
               var3 = var2;
            } else {
               var3 = false;
            }

            if (var3) {
               Buffer var4 = this.buffer;
               if (var4 != null) {
                  if (this.readWrite) {
                     long var5 = var4.size();
                     Segment var10 = var4.writableSegment$okio(var1);
                     var1 = 8192 - var10.limit;
                     var10.limit = 8192;
                     long var8 = (long)var1;
                     var4.setSize$okio(var5 + var8);
                     this.segment = var10;
                     this.offset = var5;
                     this.data = var10.data;
                     this.start = 8192 - var1;
                     this.end = 8192;
                     return var8;
                  } else {
                     throw (Throwable)(new IllegalStateException("expandBuffer() only permitted for read/write buffers".toString()));
                  }
               } else {
                  throw (Throwable)(new IllegalStateException("not attached to a buffer".toString()));
               }
            } else {
               var7 = new StringBuilder();
               var7.append("minByteCount > Segment.SIZE: ");
               var7.append(var1);
               throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
            }
         } else {
            var7 = new StringBuilder();
            var7.append("minByteCount <= 0: ");
            var7.append(var1);
            throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
         }
      }

      public final int next() {
         long var1 = this.offset;
         Buffer var3 = this.buffer;
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         boolean var4;
         if (var1 != var3.size()) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var1 = this.offset;
            if (var1 == -1L) {
               var1 = 0L;
            } else {
               var1 += (long)(this.end - this.start);
            }

            return this.seek(var1);
         } else {
            throw (Throwable)(new IllegalStateException("no more bytes".toString()));
         }
      }

      public final long resizeBuffer(long var1) {
         Buffer var3 = this.buffer;
         if (var3 != null) {
            if (!this.readWrite) {
               throw (Throwable)(new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString()));
            } else {
               long var4 = var3.size();
               long var16;
               int var6 = (var16 = var1 - var4) == 0L ? 0 : (var16 < 0L ? -1 : 1);
               long var7;
               Segment var9;
               boolean var15;
               if (var6 <= 0) {
                  if (var1 >= 0L) {
                     var15 = true;
                  } else {
                     var15 = false;
                  }

                  if (!var15) {
                     StringBuilder var14 = new StringBuilder();
                     var14.append("newSize < 0: ");
                     var14.append(var1);
                     throw (Throwable)(new IllegalArgumentException(var14.toString().toString()));
                  }

                  long var10;
                  for(var7 = var4 - var1; var7 > 0L; var7 -= var10) {
                     var9 = var3.head;
                     if (var9 == null) {
                        Intrinsics.throwNpe();
                     }

                     var9 = var9.prev;
                     if (var9 == null) {
                        Intrinsics.throwNpe();
                     }

                     var10 = (long)(var9.limit - var9.pos);
                     if (var10 > var7) {
                        var9.limit -= (int)var7;
                        break;
                     }

                     var3.head = var9.pop();
                     SegmentPool.recycle(var9);
                  }

                  this.segment = (Segment)null;
                  this.offset = var1;
                  this.data = (byte[])null;
                  this.start = -1;
                  this.end = -1;
               } else if (var6 > 0) {
                  var7 = var1 - var4;

                  for(boolean var12 = true; var7 > 0L; var12 = var15) {
                     var9 = var3.writableSegment$okio(1);
                     int var13 = (int)Math.min(var7, (long)(8192 - var9.limit));
                     var9.limit += var13;
                     var7 -= (long)var13;
                     var15 = var12;
                     if (var12) {
                        this.segment = var9;
                        this.offset = var4;
                        this.data = var9.data;
                        this.start = var9.limit - var13;
                        this.end = var9.limit;
                        var15 = false;
                     }
                  }
               }

               var3.setSize$okio(var1);
               return var4;
            }
         } else {
            throw (Throwable)(new IllegalStateException("not attached to a buffer".toString()));
         }
      }

      public final int seek(long var1) {
         Buffer var3 = this.buffer;
         if (var3 == null) {
            throw (Throwable)(new IllegalStateException("not attached to a buffer".toString()));
         } else if (var1 >= (long)-1 && var1 <= var3.size()) {
            if (var1 != -1L && var1 != var3.size()) {
               long var4 = 0L;
               long var6 = var3.size();
               Segment var8 = var3.head;
               Segment var9 = var3.head;
               Segment var10 = this.segment;
               long var11 = var4;
               long var13 = var6;
               Segment var19 = var8;
               Segment var16 = var9;
               int var17;
               if (var10 != null) {
                  var11 = this.offset;
                  var17 = this.start;
                  if (var10 == null) {
                     Intrinsics.throwNpe();
                  }

                  var11 -= (long)(var17 - var10.pos);
                  if (var11 > var1) {
                     var16 = this.segment;
                     var13 = var11;
                     var11 = var4;
                     var19 = var8;
                  } else {
                     var19 = this.segment;
                     var16 = var9;
                     var13 = var6;
                  }
               }

               var6 = var13;
               if (var13 - var1 > var1 - var11) {
                  var16 = var19;

                  while(true) {
                     if (var16 == null) {
                        Intrinsics.throwNpe();
                     }

                     var13 = var11;
                     var19 = var16;
                     if (var1 < (long)(var16.limit - var16.pos) + var11) {
                        break;
                     }

                     var11 += (long)(var16.limit - var16.pos);
                     var16 = var16.next;
                  }
               } else {
                  for(; var6 > var1; var6 -= (long)(var16.limit - var16.pos)) {
                     if (var16 == null) {
                        Intrinsics.throwNpe();
                     }

                     var16 = var16.prev;
                     if (var16 == null) {
                        Intrinsics.throwNpe();
                     }
                  }

                  var13 = var6;
                  var19 = var16;
               }

               var16 = var19;
               if (this.readWrite) {
                  if (var19 == null) {
                     Intrinsics.throwNpe();
                  }

                  var16 = var19;
                  if (var19.shared) {
                     var16 = var19.unsharedCopy();
                     if (var3.head == var19) {
                        var3.head = var16;
                     }

                     var16 = var19.push(var16);
                     var19 = var16.prev;
                     if (var19 == null) {
                        Intrinsics.throwNpe();
                     }

                     var19.pop();
                  }
               }

               this.segment = var16;
               this.offset = var1;
               if (var16 == null) {
                  Intrinsics.throwNpe();
               }

               this.data = var16.data;
               this.start = var16.pos + (int)(var1 - var13);
               var17 = var16.limit;
               this.end = var17;
               return var17 - this.start;
            } else {
               this.segment = (Segment)null;
               this.offset = var1;
               this.data = (byte[])null;
               this.start = -1;
               this.end = -1;
               return -1;
            }
         } else {
            StringCompanionObject var15 = StringCompanionObject.INSTANCE;
            String var18 = String.format("offset=%s > size=%s", Arrays.copyOf(new Object[]{var1, var3.size()}, 2));
            Intrinsics.checkExpressionValueIsNotNull(var18, "java.lang.String.format(format, *args)");
            throw (Throwable)(new ArrayIndexOutOfBoundsException(var18));
         }
      }
   }
}
