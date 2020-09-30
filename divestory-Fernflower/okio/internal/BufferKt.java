package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Options;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio._Platform;
import okio._Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000v\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0000\u001a\r\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0080\b\u001a\r\u0010\u0014\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u0010\u0015\u001a\u00020\u0013*\u00020\u0013H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0017\u0010\u001a\u001a\u00020\n*\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u001e*\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0005H\u0080\b\u001a\r\u0010 \u001a\u00020\b*\u00020\u0013H\u0080\b\u001a%\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010&\u001a\u00020\u0005*\u00020\u00132\u0006\u0010'\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a-\u0010(\u001a\u00020\n*\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u0015\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\u0080\b\u001a%\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010)\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010+\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020,H\u0080\b\u001a\r\u0010-\u001a\u00020\u001e*\u00020\u0013H\u0080\b\u001a\r\u0010.\u001a\u00020\u0001*\u00020\u0013H\u0080\b\u001a\u0015\u0010.\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u0010/\u001a\u00020%*\u00020\u0013H\u0080\b\u001a\u0015\u0010/\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00100\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\u0080\b\u001a\u001d\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00102\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u00103\u001a\u00020\b*\u00020\u0013H\u0080\b\u001a\r\u00104\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0013H\u0080\b\u001a\u0015\u00107\u001a\u000208*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00109\u001a\u00020\b*\u00020\u0013H\u0080\b\u001a\u000f\u0010:\u001a\u0004\u0018\u000108*\u00020\u0013H\u0080\b\u001a\u0015\u0010;\u001a\u000208*\u00020\u00132\u0006\u0010<\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010=\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?H\u0080\b\u001a\u0015\u0010@\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u0010A\u001a\u00020%*\u00020\u0013H\u0080\b\u001a\u0015\u0010A\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u0015\u0010B\u001a\u00020\f*\u00020\u00132\u0006\u0010C\u001a\u00020\bH\u0080\b\u001a\u0015\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u0001H\u0080\b\u001a%\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010D\u001a\u00020\u0012*\u00020\u00132\u0006\u0010E\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a)\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010F\u001a\u00020%2\b\b\u0002\u0010\u0018\u001a\u00020\b2\b\b\u0002\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020G2\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010H\u001a\u00020\u0005*\u00020\u00132\u0006\u0010E\u001a\u00020GH\u0080\b\u001a\u0015\u0010I\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\"\u001a\u00020\bH\u0080\b\u001a\u0015\u0010J\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010L\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010M\u001a\u00020\u0013*\u00020\u00132\u0006\u0010N\u001a\u00020\bH\u0080\b\u001a\u0015\u0010O\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010P\u001a\u00020\u0013*\u00020\u00132\u0006\u0010Q\u001a\u00020\bH\u0080\b\u001a%\u0010R\u001a\u00020\u0013*\u00020\u00132\u0006\u0010S\u001a\u0002082\u0006\u0010T\u001a\u00020\b2\u0006\u0010U\u001a\u00020\bH\u0080\b\u001a\u0015\u0010V\u001a\u00020\u0013*\u00020\u00132\u0006\u0010W\u001a\u00020\bH\u0080\b\u001a\u0014\u0010X\u001a\u000208*\u00020\u00132\u0006\u0010Y\u001a\u00020\u0005H\u0000\u001a<\u0010Z\u001a\u0002H[\"\u0004\b\u0000\u0010[*\u00020\u00132\u0006\u0010#\u001a\u00020\u00052\u001a\u0010\\\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H[0]H\u0080\b¢\u0006\u0002\u0010^\u001a\u001e\u0010_\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010`\u001a\u00020\nH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000¨\u0006a"},
   d2 = {"HEX_DIGIT_BYTES", "", "getHEX_DIGIT_BYTES", "()[B", "OVERFLOW_DIGIT_START", "", "OVERFLOW_ZONE", "SEGMENTING_THRESHOLD", "", "rangeEquals", "", "segment", "Lokio/Segment;", "segmentPos", "bytes", "bytesOffset", "bytesLimit", "commonClear", "", "Lokio/Buffer;", "commonCompleteSegmentByteCount", "commonCopy", "commonCopyTo", "out", "offset", "byteCount", "commonEquals", "other", "", "commonGet", "", "pos", "commonHashCode", "commonIndexOf", "b", "fromIndex", "toIndex", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonRead", "sink", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadLong", "commonReadShort", "", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonSnapshot", "commonWritableSegment", "minimumCapacity", "commonWrite", "source", "byteString", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteLong", "commonWriteShort", "s", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "readUtf8Line", "newline", "seek", "T", "lambda", "Lkotlin/Function2;", "(Lokio/Buffer;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "selectPrefix", "selectTruncated", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class BufferKt {
   private static final byte[] HEX_DIGIT_BYTES = _Platform.asUtf8ToByteArray("0123456789abcdef");
   public static final long OVERFLOW_DIGIT_START = -7L;
   public static final long OVERFLOW_ZONE = -922337203685477580L;
   public static final int SEGMENTING_THRESHOLD = 4096;

   public static final void commonClear(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonClear");
      var0.skip(var0.size());
   }

   public static final long commonCompleteSegmentByteCount(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonCompleteSegmentByteCount");
      long var1 = var0.size();
      if (var1 == 0L) {
         return 0L;
      } else {
         Segment var5 = var0.head;
         if (var5 == null) {
            Intrinsics.throwNpe();
         }

         var5 = var5.prev;
         if (var5 == null) {
            Intrinsics.throwNpe();
         }

         long var3 = var1;
         if (var5.limit < 8192) {
            var3 = var1;
            if (var5.owner) {
               var3 = var1 - (long)(var5.limit - var5.pos);
            }
         }

         return var3;
      }
   }

   public static final Buffer commonCopy(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonCopy");
      Buffer var1 = new Buffer();
      if (var0.size() == 0L) {
         return var1;
      } else {
         Segment var2 = var0.head;
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         Segment var3 = var2.sharedCopy();
         var1.head = var3;
         var3.prev = var1.head;
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

         var1.setSize$okio(var0.size());
         return var1;
      }
   }

   public static final Buffer commonCopyTo(Buffer var0, Buffer var1, long var2, long var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonCopyTo");
      Intrinsics.checkParameterIsNotNull(var1, "out");
      _Util.checkOffsetAndCount(var0.size(), var2, var4);
      if (var4 == 0L) {
         return var0;
      } else {
         var1.setSize$okio(var1.size() + var4);
         Segment var6 = var0.head;

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
                  if (var1.head == null) {
                     var6.prev = var6;
                     var6.next = var6.prev;
                     var1.head = var6.next;
                  } else {
                     Segment var12 = var1.head;
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

               return var0;
            }

            var2 -= (long)(var6.limit - var6.pos);
            var6 = var6.next;
         }
      }
   }

   public static final boolean commonEquals(Buffer var0, Object var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonEquals");
      if (var0 == var1) {
         return true;
      } else if (!(var1 instanceof Buffer)) {
         return false;
      } else {
         long var2 = var0.size();
         Buffer var13 = (Buffer)var1;
         if (var2 != var13.size()) {
            return false;
         } else if (var0.size() == 0L) {
            return true;
         } else {
            Segment var4 = var0.head;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            Segment var14 = var13.head;
            if (var14 == null) {
               Intrinsics.throwNpe();
            }

            int var5 = var4.pos;
            int var6 = var14.pos;

            int var11;
            for(var2 = 0L; var2 < var0.size(); var6 = var11) {
               long var7 = (long)Math.min(var4.limit - var5, var14.limit - var6);
               long var9 = 0L;

               for(var11 = var5; var9 < var7; ++var6) {
                  if (var4.data[var11] != var14.data[var6]) {
                     return false;
                  }

                  ++var9;
                  ++var11;
               }

               Segment var12 = var4;
               var5 = var11;
               if (var11 == var4.limit) {
                  var12 = var4.next;
                  if (var12 == null) {
                     Intrinsics.throwNpe();
                  }

                  var5 = var12.pos;
               }

               var4 = var14;
               var11 = var6;
               if (var6 == var14.limit) {
                  var4 = var14.next;
                  if (var4 == null) {
                     Intrinsics.throwNpe();
                  }

                  var11 = var4.pos;
               }

               var2 += var7;
               var14 = var4;
               var4 = var12;
            }

            return true;
         }
      }
   }

   public static final byte commonGet(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonGet");
      _Util.checkOffsetAndCount(var0.size(), var1, 1L);
      Segment var3 = var0.head;
      if (var3 != null) {
         long var4;
         if (var0.size() - var1 < var1) {
            for(var4 = var0.size(); var4 > var1; var4 -= (long)(var3.limit - var3.pos)) {
               var3 = var3.prev;
               if (var3 == null) {
                  Intrinsics.throwNpe();
               }
            }

            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            return var3.data[(int)((long)var3.pos + var1 - var4)];
         } else {
            var4 = 0L;

            while(true) {
               long var6 = (long)(var3.limit - var3.pos) + var4;
               if (var6 > var1) {
                  if (var3 == null) {
                     Intrinsics.throwNpe();
                  }

                  return var3.data[(int)((long)var3.pos + var1 - var4)];
               }

               var3 = var3.next;
               if (var3 == null) {
                  Intrinsics.throwNpe();
               }

               var4 = var6;
            }
         }
      } else {
         Segment var8 = (Segment)null;
         Intrinsics.throwNpe();
         return var8.data[(int)((long)var8.pos + var1 + 1L)];
      }
   }

   public static final int commonHashCode(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonHashCode");
      Segment var1 = var0.head;
      if (var1 == null) {
         return 0;
      } else {
         int var2 = 1;

         int var5;
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
         } while(var6 != var0.head);

         return var5;
      }
   }

   public static final long commonIndexOf(Buffer var0, byte var1, long var2, long var4) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOf");
      long var6 = 0L;
      boolean var8;
      if (0L <= var2 && var4 >= var2) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         long var9 = var4;
         if (var4 > var0.size()) {
            var9 = var0.size();
         }

         if (var2 == var9) {
            return -1L;
         } else {
            Segment var11 = var0.head;
            Segment var15;
            if (var11 == null) {
               var15 = (Segment)null;
               return -1L;
            } else {
               var4 = var6;
               Segment var17 = var11;
               int var13;
               int var16;
               if (var0.size() - var2 < var2) {
                  var4 = var0.size();

                  for(var15 = var11; var4 > var2; var4 -= (long)(var15.limit - var15.pos)) {
                     var15 = var15.prev;
                     if (var15 == null) {
                        Intrinsics.throwNpe();
                     }
                  }

                  if (var15 != null) {
                     var6 = var2;

                     for(var2 = var4; var2 < var9; var6 = var2) {
                        byte[] var18 = var15.data;
                        var13 = (int)Math.min((long)var15.limit, (long)var15.pos + var9 - var2);

                        for(var16 = (int)((long)var15.pos + var6 - var2); var16 < var13; ++var16) {
                           if (var18[var16] == var1) {
                              return (long)(var16 - var15.pos) + var2;
                           }
                        }

                        var2 += (long)(var15.limit - var15.pos);
                        var15 = var15.next;
                        if (var15 == null) {
                           Intrinsics.throwNpe();
                        }
                     }
                  }

                  return -1L;
               } else {
                  while(true) {
                     var6 = (long)(var17.limit - var17.pos) + var4;
                     if (var6 > var2) {
                        if (var17 != null) {
                           for(; var4 < var9; var2 = var4) {
                              byte[] var14 = var17.data;
                              var13 = (int)Math.min((long)var17.limit, (long)var17.pos + var9 - var4);

                              for(var16 = (int)((long)var17.pos + var2 - var4); var16 < var13; ++var16) {
                                 if (var14[var16] == var1) {
                                    var2 = var4;
                                    var15 = var17;
                                    return (long)(var16 - var15.pos) + var2;
                                 }
                              }

                              var4 += (long)(var17.limit - var17.pos);
                              var17 = var17.next;
                              if (var17 == null) {
                                 Intrinsics.throwNpe();
                              }
                           }
                        }

                        return -1L;
                     }

                     var17 = var17.next;
                     if (var17 == null) {
                        Intrinsics.throwNpe();
                     }

                     var4 = var6;
                  }
               }
            }
         }
      } else {
         StringBuilder var12 = new StringBuilder();
         var12.append("size=");
         var12.append(var0.size());
         var12.append(" fromIndex=");
         var12.append(var2);
         var12.append(" toIndex=");
         var12.append(var4);
         throw (Throwable)(new IllegalArgumentException(var12.toString().toString()));
      }
   }

   public static final long commonIndexOf(Buffer var0, ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOf");
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

         if (var4) {
            Segment var7 = var0.head;
            if (var7 == null) {
               Segment var18 = (Segment)null;
               return -1L;
            } else {
               Segment var8 = var7;
               long var11;
               long var13;
               int var15;
               byte[] var17;
               int var19;
               byte[] var20;
               if (var0.size() - var2 < var2) {
                  var5 = var0.size();

                  for(var8 = var7; var5 > var2; var5 -= (long)(var8.limit - var8.pos)) {
                     var8 = var8.prev;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }
                  }

                  if (var8 == null) {
                     return -1L;
                  } else {
                     var20 = var1.internalArray$okio();
                     byte var21 = var20[0];
                     int var22 = var1.size();

                     for(var11 = var0.size() - (long)var22 + 1L; var5 < var11; var2 = var5) {
                        var17 = var8.data;
                        var19 = var8.limit;
                        var13 = (long)var8.pos;
                        var15 = (int)Math.min((long)var19, var13 + var11 - var5);

                        for(var19 = (int)((long)var8.pos + var2 - var5); var19 < var15; ++var19) {
                           if (var17[var19] == var21 && rangeEquals(var8, var19 + 1, var20, 1, var22)) {
                              return (long)(var19 - var8.pos) + var5;
                           }
                        }

                        var5 += (long)(var8.limit - var8.pos);
                        var8 = var8.next;
                        if (var8 == null) {
                           Intrinsics.throwNpe();
                        }
                     }

                     return -1L;
                  }
               } else {
                  while(true) {
                     var11 = (long)(var8.limit - var8.pos) + var5;
                     if (var11 > var2) {
                        if (var8 == null) {
                           return -1L;
                        }

                        var20 = var1.internalArray$okio();
                        byte var10 = var20[0];
                        int var9 = var1.size();

                        for(var11 = var0.size() - (long)var9 + 1L; var5 < var11; var2 = var5) {
                           var17 = var8.data;
                           var19 = var8.limit;
                           var13 = (long)var8.pos;
                           var15 = (int)Math.min((long)var19, var13 + var11 - var5);

                           for(var19 = (int)((long)var8.pos + var2 - var5); var19 < var15; ++var19) {
                              if (var17[var19] == var10 && rangeEquals(var8, var19 + 1, var20, 1, var9)) {
                                 return (long)(var19 - var8.pos) + var5;
                              }
                           }

                           var5 += (long)(var8.limit - var8.pos);
                           var8 = var8.next;
                           if (var8 == null) {
                              Intrinsics.throwNpe();
                           }
                        }

                        return -1L;
                     }

                     var8 = var8.next;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }

                     var5 = var11;
                  }
               }
            }
         } else {
            StringBuilder var16 = new StringBuilder();
            var16.append("fromIndex < 0: ");
            var16.append(var2);
            throw (Throwable)(new IllegalArgumentException(var16.toString().toString()));
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("bytes is empty".toString()));
      }
   }

   public static final long commonIndexOfElement(Buffer var0, ByteString var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonIndexOfElement");
      Intrinsics.checkParameterIsNotNull(var1, "targetBytes");
      long var4 = 0L;
      boolean var6;
      if (var2 >= 0L) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         Segment var7 = var0.head;
         if (var7 == null) {
            Segment var17 = (Segment)null;
            return -1L;
         } else {
            Segment var8;
            int var11;
            int var20;
            label231: {
               var8 = var7;
               byte var9;
               byte var10;
               int var12;
               byte var13;
               Segment var18;
               byte[] var19;
               byte[] var21;
               int var22;
               if (var0.size() - var2 < var2) {
                  var4 = var0.size();

                  for(var8 = var7; var4 > var2; var4 -= (long)(var8.limit - var8.pos)) {
                     var8 = var8.prev;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }
                  }

                  if (var8 == null) {
                     return -1L;
                  }

                  if (var1.size() != 2) {
                     for(var19 = var1.internalArray$okio(); var4 < var0.size(); var2 = var4) {
                        var21 = var8.data;
                        var20 = (int)((long)var8.pos + var2 - var4);

                        for(var12 = var8.limit; var20 < var12; ++var20) {
                           var10 = var21[var20];
                           var22 = var19.length;

                           for(var11 = 0; var11 < var22; ++var11) {
                              if (var10 == var19[var11]) {
                                 break label231;
                              }
                           }
                        }

                        var4 += (long)(var8.limit - var8.pos);
                        var8 = var8.next;
                        if (var8 == null) {
                           Intrinsics.throwNpe();
                        }
                     }

                     return -1L;
                  }

                  var9 = var1.getByte(0);
                  var10 = var1.getByte(1);

                  label129:
                  while(true) {
                     if (var4 >= var0.size()) {
                        return -1L;
                     }

                     var21 = var8.data;
                     var11 = (int)((long)var8.pos + var2 - var4);

                     for(var12 = var8.limit; var11 < var12; ++var11) {
                        var13 = var21[var11];
                        var2 = var4;
                        var18 = var8;
                        var20 = var11;
                        if (var13 == var9) {
                           break label129;
                        }

                        if (var13 == var10) {
                           var2 = var4;
                           var18 = var8;
                           var20 = var11;
                           break label129;
                        }
                     }

                     var4 += (long)(var8.limit - var8.pos);
                     var8 = var8.next;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }

                     var2 = var4;
                  }
               } else {
                  label183:
                  while(true) {
                     long var14 = (long)(var8.limit - var8.pos) + var4;
                     if (var14 > var2) {
                        if (var8 == null) {
                           return -1L;
                        }

                        if (var1.size() != 2) {
                           for(var19 = var1.internalArray$okio(); var4 < var0.size(); var2 = var4) {
                              var21 = var8.data;
                              var20 = (int)((long)var8.pos + var2 - var4);

                              for(var12 = var8.limit; var20 < var12; ++var20) {
                                 var10 = var21[var20];
                                 var22 = var19.length;

                                 for(var11 = 0; var11 < var22; ++var11) {
                                    if (var10 == var19[var11]) {
                                       break label231;
                                    }
                                 }
                              }

                              var4 += (long)(var8.limit - var8.pos);
                              var8 = var8.next;
                              if (var8 == null) {
                                 Intrinsics.throwNpe();
                              }
                           }

                           return -1L;
                        }

                        var10 = var1.getByte(0);

                        for(var9 = var1.getByte(1); var4 < var0.size(); var2 = var4) {
                           var21 = var8.data;
                           var11 = (int)((long)var8.pos + var2 - var4);

                           for(var12 = var8.limit; var11 < var12; ++var11) {
                              var13 = var21[var11];
                              var2 = var4;
                              var18 = var8;
                              var20 = var11;
                              if (var13 == var10) {
                                 break label183;
                              }

                              if (var13 == var9) {
                                 var2 = var4;
                                 var18 = var8;
                                 var20 = var11;
                                 break label183;
                              }
                           }

                           var4 += (long)(var8.limit - var8.pos);
                           var8 = var8.next;
                           if (var8 == null) {
                              Intrinsics.throwNpe();
                           }
                        }

                        return -1L;
                     }

                     var8 = var8.next;
                     if (var8 == null) {
                        Intrinsics.throwNpe();
                     }

                     var4 = var14;
                  }
               }

               var11 = var18.pos;
               return (long)(var20 - var11) + var2;
            }

            var11 = var8.pos;
            var2 = var4;
            return (long)(var20 - var11) + var2;
         }
      } else {
         StringBuilder var16 = new StringBuilder();
         var16.append("fromIndex < 0: ");
         var16.append(var2);
         throw (Throwable)(new IllegalArgumentException(var16.toString().toString()));
      }
   }

   public static final boolean commonRangeEquals(Buffer var0, long var1, ByteString var3, int var4, int var5) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRangeEquals");
      Intrinsics.checkParameterIsNotNull(var3, "bytes");
      if (var1 >= 0L && var4 >= 0 && var5 >= 0 && var0.size() - var1 >= (long)var5 && var3.size() - var4 >= var5) {
         for(int var6 = 0; var6 < var5; ++var6) {
            if (var0.getByte((long)var6 + var1) != var3.getByte(var4 + var6)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static final int commonRead(Buffer var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRead");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      return var0.read(var1, 0, var1.length);
   }

   public static final int commonRead(Buffer var0, byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRead");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      _Util.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
      Segment var4 = var0.head;
      if (var4 != null) {
         var3 = Math.min(var3, var4.limit - var4.pos);
         ArraysKt.copyInto(var4.data, var1, var2, var4.pos, var4.pos + var3);
         var4.pos += var3;
         var0.setSize$okio(var0.size() - (long)var3);
         if (var4.pos == var4.limit) {
            var0.head = var4.pop();
            SegmentPool.recycle(var4);
         }

         return var3;
      } else {
         return -1;
      }
   }

   public static final long commonRead(Buffer var0, Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonRead");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      boolean var4;
      if (var2 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (var0.size() == 0L) {
            return -1L;
         } else {
            long var5 = var2;
            if (var2 > var0.size()) {
               var5 = var0.size();
            }

            var1.write(var0, var5);
            return var5;
         }
      } else {
         StringBuilder var7 = new StringBuilder();
         var7.append("byteCount < 0: ");
         var7.append(var2);
         throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
      }
   }

   public static final long commonReadAll(Buffer var0, Sink var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadAll");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var2 = var0.size();
      if (var2 > 0L) {
         var1.write(var0, var2);
      }

      return var2;
   }

   public static final byte commonReadByte(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByte");
      if (var0.size() != 0L) {
         Segment var1 = var0.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         byte[] var4 = var1.data;
         int var5 = var2 + 1;
         byte var6 = var4[var2];
         var0.setSize$okio(var0.size() - 1L);
         if (var5 == var3) {
            var0.head = var1.pop();
            SegmentPool.recycle(var1);
         } else {
            var1.pos = var5;
         }

         return var6;
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public static final byte[] commonReadByteArray(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteArray");
      return var0.readByteArray(var0.size());
   }

   public static final byte[] commonReadByteArray(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteArray");
      boolean var3;
      if (var1 >= 0L && var1 <= (long)Integer.MAX_VALUE) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         if (var0.size() >= var1) {
            byte[] var4 = new byte[(int)var1];
            var0.readFully(var4);
            return var4;
         } else {
            throw (Throwable)(new EOFException());
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("byteCount: ");
         var5.append(var1);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public static final ByteString commonReadByteString(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteString");
      return var0.readByteString(var0.size());
   }

   public static final ByteString commonReadByteString(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadByteString");
      boolean var3;
      if (var1 >= 0L && var1 <= (long)Integer.MAX_VALUE) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         if (var0.size() >= var1) {
            if (var1 >= (long)4096) {
               ByteString var4 = var0.snapshot((int)var1);
               var0.skip(var1);
               return var4;
            } else {
               return new ByteString(var0.readByteArray(var1));
            }
         } else {
            throw (Throwable)(new EOFException());
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("byteCount: ");
         var5.append(var1);
         throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
      }
   }

   public static final long commonReadDecimalLong(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadDecimalLong");
      long var1 = var0.size();
      long var3 = 0L;
      if (var1 == 0L) {
         throw (Throwable)(new EOFException());
      } else {
         long var5 = -7L;
         int var7 = 0;
         boolean var8 = false;
         boolean var9 = false;

         while(true) {
            Segment var10 = var0.head;
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
                     byte var18 = (byte)48;
                     StringBuilder var17;
                     if (var16 >= var18 && var16 <= (byte)57) {
                        var7 = var18 - var16;
                        long var21;
                        int var19 = (var21 = var1 - -922337203685477580L) == 0L ? 0 : (var21 < 0L ? -1 : 1);
                        if (var19 >= 0 && (var19 != 0 || (long)var7 >= var3)) {
                           var1 = var1 * 10L + (long)var7;
                           break label78;
                        }

                        Buffer var20 = (new Buffer()).writeDecimalLong(var1).writeByte(var16);
                        if (!var14) {
                           var20.readByte();
                        }

                        var17 = new StringBuilder();
                        var17.append("Number too large: ");
                        var17.append(var20.readUtf8());
                        throw (Throwable)(new NumberFormatException(var17.toString()));
                     }

                     if (var16 == (byte)45 && var15 == 0) {
                        --var3;
                        var14 = true;
                        break label78;
                     }

                     if (var15 == 0) {
                        var17 = new StringBuilder();
                        var17.append("Expected leading [0-9] or '-' character but was 0x");
                        var17.append(_Util.toHexString(var16));
                        throw (Throwable)(new NumberFormatException(var17.toString()));
                     }

                     var9 = true;
                  }

                  if (var12 == var13) {
                     var0.head = var10.pop();
                     SegmentPool.recycle(var10);
                  } else {
                     var10.pos = var12;
                  }

                  if (!var9) {
                     var5 = var3;
                     var3 = var1;
                     var7 = var15;
                     var8 = var14;
                     if (var0.head != null) {
                        break;
                     }
                  }

                  var0.setSize$okio(var0.size() - (long)var15);
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

   public static final void commonReadFully(Buffer var0, Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadFully");
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      if (var0.size() >= var2) {
         var1.write(var0, var2);
      } else {
         var1.write(var0, var0.size());
         throw (Throwable)(new EOFException());
      }
   }

   public static final void commonReadFully(Buffer var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadFully");
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      int var3;
      for(int var2 = 0; var2 < var1.length; var2 += var3) {
         var3 = var0.read(var1, var2, var1.length - var2);
         if (var3 == -1) {
            throw (Throwable)(new EOFException());
         }
      }

   }

   public static final long commonReadHexadecimalUnsignedLong(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadHexadecimalUnsignedLong");
      if (var0.size() == 0L) {
         throw (Throwable)(new EOFException());
      } else {
         int var1 = 0;
         long var2 = 0L;
         boolean var4 = false;

         long var9;
         int var11;
         do {
            Segment var5 = var0.head;
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
               byte var15 = (byte)48;
               StringBuilder var14;
               if (var13 >= var15 && var13 <= (byte)57) {
                  var1 = var13 - var15;
               } else {
                  var15 = (byte)97;
                  if (var13 < var15 || var13 > (byte)102) {
                     var15 = (byte)65;
                     if (var13 < var15 || var13 > (byte)70) {
                        if (var11 == 0) {
                           var14 = new StringBuilder();
                           var14.append("Expected leading [0-9a-fA-F] character but was 0x");
                           var14.append(_Util.toHexString(var13));
                           throw (Throwable)(new NumberFormatException(var14.toString()));
                        }

                        var12 = true;
                        break;
                     }
                  }

                  var1 = var13 - var15 + 10;
               }

               if ((-1152921504606846976L & var9) != 0L) {
                  Buffer var16 = (new Buffer()).writeHexadecimalUnsignedLong(var9).writeByte(var13);
                  var14 = new StringBuilder();
                  var14.append("Number too large: ");
                  var14.append(var16.readUtf8());
                  throw (Throwable)(new NumberFormatException(var14.toString()));
               }

               var9 = var9 << 4 | (long)var1;
               ++var7;
               ++var11;
            }

            if (var7 == var8) {
               var0.head = var5.pop();
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
         } while(var0.head != null);

         var0.setSize$okio(var0.size() - (long)var11);
         return var9;
      }
   }

   public static final int commonReadInt(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadInt");
      if (var0.size() >= 4L) {
         Segment var1 = var0.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         byte var9;
         byte var11;
         if ((long)(var3 - var2) < 4L) {
            byte var10 = var0.readByte();
            var11 = var0.readByte();
            var9 = var0.readByte();
            return var0.readByte() & 255 | (var10 & 255) << 24 | (var11 & 255) << 16 | (var9 & 255) << 8;
         } else {
            byte[] var5 = var1.data;
            int var4 = var2 + 1;
            var9 = var5[var2];
            int var6 = var4 + 1;
            var11 = var5[var4];
            int var7 = var6 + 1;
            byte var8 = var5[var6];
            var6 = var7 + 1;
            byte var12 = var5[var7];
            var0.setSize$okio(var0.size() - 4L);
            if (var6 == var3) {
               var0.head = var1.pop();
               SegmentPool.recycle(var1);
            } else {
               var1.pos = var6;
            }

            return (var9 & 255) << 24 | (var11 & 255) << 16 | (var8 & 255) << 8 | var12 & 255;
         }
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public static final long commonReadLong(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadLong");
      if (var0.size() >= 8L) {
         Segment var1 = var0.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         if ((long)(var3 - var2) < 8L) {
            return ((long)var0.readInt() & 4294967295L) << 32 | 4294967295L & (long)var0.readInt();
         } else {
            byte[] var4 = var1.data;
            int var5 = var2 + 1;
            long var6 = (long)var4[var2];
            var2 = var5 + 1;
            long var8 = (long)var4[var5];
            var5 = var2 + 1;
            long var10 = (long)var4[var2];
            var2 = var5 + 1;
            long var12 = (long)var4[var5];
            var5 = var2 + 1;
            long var14 = (long)var4[var2];
            var2 = var5 + 1;
            long var16 = (long)var4[var5];
            var5 = var2 + 1;
            long var18 = (long)var4[var2];
            var2 = var5 + 1;
            long var20 = (long)var4[var5];
            var0.setSize$okio(var0.size() - 8L);
            if (var2 == var3) {
               var0.head = var1.pop();
               SegmentPool.recycle(var1);
            } else {
               var1.pos = var2;
            }

            return (var12 & 255L) << 32 | (var6 & 255L) << 56 | (var8 & 255L) << 48 | (var10 & 255L) << 40 | (var14 & 255L) << 24 | (var16 & 255L) << 16 | (var18 & 255L) << 8 | var20 & 255L;
         }
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public static final short commonReadShort(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadShort");
      if (var0.size() >= 2L) {
         Segment var1 = var0.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         int var2 = var1.pos;
         int var3 = var1.limit;
         if (var3 - var2 < 2) {
            byte var7 = var0.readByte();
            return (short)(var0.readByte() & 255 | (var7 & 255) << 8);
         } else {
            byte[] var4 = var1.data;
            int var5 = var2 + 1;
            byte var6 = var4[var2];
            var2 = var5 + 1;
            byte var8 = var4[var5];
            var0.setSize$okio(var0.size() - 2L);
            if (var2 == var3) {
               var0.head = var1.pop();
               SegmentPool.recycle(var1);
            } else {
               var1.pos = var2;
            }

            return (short)((var6 & 255) << 8 | var8 & 255);
         }
      } else {
         throw (Throwable)(new EOFException());
      }
   }

   public static final String commonReadUtf8(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8");
      long var11;
      int var3 = (var11 = var1 - 0L) == 0L ? 0 : (var11 < 0L ? -1 : 1);
      boolean var4;
      if (var3 >= 0 && var1 <= (long)Integer.MAX_VALUE) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (var0.size() >= var1) {
            if (var3 == 0) {
               return "";
            } else {
               Segment var5 = var0.head;
               if (var5 == null) {
                  Intrinsics.throwNpe();
               }

               if ((long)var5.pos + var1 > (long)var5.limit) {
                  return _Utf8Kt.commonToUtf8String$default(var0.readByteArray(var1), 0, 0, 3, (Object)null);
               } else {
                  byte[] var6 = var5.data;
                  int var9 = var5.pos;
                  int var7 = var5.pos;
                  var3 = (int)var1;
                  String var10 = _Utf8Kt.commonToUtf8String(var6, var9, var7 + var3);
                  var5.pos += var3;
                  var0.setSize$okio(var0.size() - var1);
                  if (var5.pos == var5.limit) {
                     var0.head = var5.pop();
                     SegmentPool.recycle(var5);
                  }

                  return var10;
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

   public static final int commonReadUtf8CodePoint(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8CodePoint");
      if (var0.size() == 0L) {
         throw (Throwable)(new EOFException());
      } else {
         byte var1 = var0.getByte(0L);
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
               var0.skip(1L);
               return 65533;
            }

            var4 = var1 & 7;
            var5 = 4;
            var6 = 65536;
         }

         long var7 = var0.size();
         long var9 = (long)var5;
         if (var7 < var9) {
            StringBuilder var12 = new StringBuilder();
            var12.append("size < ");
            var12.append(var5);
            var12.append(": ");
            var12.append(var0.size());
            var12.append(" (to read code point prefixed 0x");
            var12.append(_Util.toHexString(var1));
            var12.append(')');
            throw (Throwable)(new EOFException(var12.toString()));
         } else {
            while(var2 < var5) {
               var7 = (long)var2;
               byte var11 = var0.getByte(var7);
               if ((var11 & 192) != 128) {
                  var0.skip(var7);
                  return 65533;
               }

               var4 = var4 << 6 | var11 & 63;
               ++var2;
            }

            var0.skip(var9);
            if (var4 > 1114111) {
               var4 = var3;
            } else if (55296 <= var4 && 57343 >= var4) {
               var4 = var3;
            } else if (var4 < var6) {
               var4 = var3;
            }

            return var4;
         }
      }
   }

   public static final String commonReadUtf8Line(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8Line");
      long var1 = var0.indexOf((byte)10);
      String var3;
      if (var1 != -1L) {
         var3 = readUtf8Line(var0, var1);
      } else if (var0.size() != 0L) {
         var3 = var0.readUtf8(var0.size());
      } else {
         var3 = null;
      }

      return var3;
   }

   public static final String commonReadUtf8LineStrict(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonReadUtf8LineStrict");
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         long var4 = Long.MAX_VALUE;
         if (var1 != Long.MAX_VALUE) {
            var4 = var1 + 1L;
         }

         byte var6 = (byte)10;
         long var7 = var0.indexOf(var6, 0L, var4);
         if (var7 != -1L) {
            return readUtf8Line(var0, var7);
         } else if (var4 < var0.size() && var0.getByte(var4 - 1L) == (byte)13 && var0.getByte(var4) == var6) {
            return readUtf8Line(var0, var4);
         } else {
            Buffer var9 = new Buffer();
            var4 = var0.size();
            var0.copyTo(var9, 0L, Math.min((long)32, var4));
            StringBuilder var10 = new StringBuilder();
            var10.append("\\n not found: limit=");
            var10.append(Math.min(var0.size(), var1));
            var10.append(" content=");
            var10.append(var9.readByteString().hex());
            var10.append('…');
            throw (Throwable)(new EOFException(var10.toString()));
         }
      } else {
         StringBuilder var11 = new StringBuilder();
         var11.append("limit < 0: ");
         var11.append(var1);
         throw (Throwable)(new IllegalArgumentException(var11.toString().toString()));
      }
   }

   public static final int commonSelect(Buffer var0, Options var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSelect");
      Intrinsics.checkParameterIsNotNull(var1, "options");
      int var2 = selectPrefix$default(var0, var1, false, 2, (Object)null);
      if (var2 == -1) {
         return -1;
      } else {
         var0.skip((long)var1.getByteStrings$okio()[var2].size());
         return var2;
      }
   }

   public static final void commonSkip(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSkip");

      while(var1 > 0L) {
         Segment var3 = var0.head;
         if (var3 == null) {
            throw (Throwable)(new EOFException());
         }

         int var4 = (int)Math.min(var1, (long)(var3.limit - var3.pos));
         long var5 = var0.size();
         long var7 = (long)var4;
         var0.setSize$okio(var5 - var7);
         var7 = var1 - var7;
         var3.pos += var4;
         var1 = var7;
         if (var3.pos == var3.limit) {
            var0.head = var3.pop();
            SegmentPool.recycle(var3);
            var1 = var7;
         }
      }

   }

   public static final ByteString commonSnapshot(Buffer var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSnapshot");
      boolean var1;
      if (var0.size() <= (long)Integer.MAX_VALUE) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return var0.snapshot((int)var0.size());
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("size > Int.MAX_VALUE: ");
         var2.append(var0.size());
         throw (Throwable)(new IllegalStateException(var2.toString().toString()));
      }
   }

   public static final ByteString commonSnapshot(Buffer var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonSnapshot");
      if (var1 == 0) {
         return ByteString.EMPTY;
      } else {
         _Util.checkOffsetAndCount(var0.size(), 0L, (long)var1);
         Segment var2 = var0.head;
         byte var3 = 0;
         int var4 = 0;

         int var5;
         for(var5 = 0; var4 < var1; var2 = var2.next) {
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            if (var2.limit == var2.pos) {
               throw (Throwable)(new AssertionError("s.limit == s.pos"));
            }

            var4 += var2.limit - var2.pos;
            ++var5;
         }

         byte[][] var8 = new byte[var5][];
         int[] var6 = new int[var5 * 2];
         Segment var7 = var0.head;
         var5 = 0;

         for(var4 = var3; var4 < var1; var7 = var7.next) {
            if (var7 == null) {
               Intrinsics.throwNpe();
            }

            var8[var5] = var7.data;
            var4 += var7.limit - var7.pos;
            var6[var5] = Math.min(var4, var1);
            var6[((Object[])var8).length + var5] = var7.pos;
            var7.shared = true;
            ++var5;
         }

         return (ByteString)(new SegmentedByteString((byte[][])var8, var6));
      }
   }

   public static final Segment commonWritableSegment(Buffer var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWritableSegment");
      boolean var2 = true;
      if (var1 < 1 || var1 > 8192) {
         var2 = false;
      }

      if (var2) {
         Segment var3;
         if (var0.head == null) {
            var3 = SegmentPool.take();
            var0.head = var3;
            var3.prev = var3;
            var3.next = var3;
            return var3;
         } else {
            Segment var4 = var0.head;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var3 = var4.prev;
            if (var3 == null) {
               Intrinsics.throwNpe();
            }

            if (var3.limit + var1 <= 8192) {
               var4 = var3;
               if (var3.owner) {
                  return var4;
               }
            }

            var4 = var3.push(SegmentPool.take());
            return var4;
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("unexpected capacity".toString()));
      }
   }

   public static final Buffer commonWrite(Buffer var0, ByteString var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      var1.write$okio(var0, var2, var3);
      return var0;
   }

   public static final Buffer commonWrite(Buffer var0, Source var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");

      while(var2 > 0L) {
         long var4 = var1.read(var0, var2);
         if (var4 == -1L) {
            throw (Throwable)(new EOFException());
         }

         var2 -= var4;
      }

      return var0;
   }

   public static final Buffer commonWrite(Buffer var0, byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      return var0.write((byte[])var1, 0, var1.length);
   }

   public static final Buffer commonWrite(Buffer var0, byte[] var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var4 = (long)var1.length;
      long var6 = (long)var2;
      long var8 = (long)var3;
      _Util.checkOffsetAndCount(var4, var6, var8);

      for(int var10 = var3 + var2; var2 < var10; var2 = var3) {
         Segment var11 = var0.writableSegment$okio(1);
         int var12 = Math.min(var10 - var2, 8192 - var11.limit);
         byte[] var13 = var11.data;
         int var14 = var11.limit;
         var3 = var2 + var12;
         ArraysKt.copyInto(var1, var13, var14, var2, var3);
         var11.limit += var12;
      }

      var0.setSize$okio(var0.size() + var8);
      return var0;
   }

   public static final void commonWrite(Buffer var0, Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      boolean var4;
      if (var1 != var0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
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
               if (var0.head != null) {
                  var5 = var0.head;
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
                     var0.setSize$okio(var0.size() + var2);
                     return;
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
            if (var0.head == null) {
               var0.head = var5;
               var5.prev = var5;
               var5.next = var5.prev;
            } else {
               var8 = var0.head;
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
            var0.setSize$okio(var0.size() + var6);
            var2 -= var6;
         }

      } else {
         throw (Throwable)(new IllegalArgumentException("source == this".toString()));
      }
   }

   // $FF: synthetic method
   public static Buffer commonWrite$default(Buffer var0, ByteString var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var1.size();
      }

      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWrite");
      Intrinsics.checkParameterIsNotNull(var1, "byteString");
      var1.write$okio(var0, var2, var3);
      return var0;
   }

   public static final long commonWriteAll(Buffer var0, Source var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteAll");
      Intrinsics.checkParameterIsNotNull(var1, "source");
      long var2 = 0L;

      while(true) {
         long var4 = var1.read(var0, (long)8192);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
      }
   }

   public static final Buffer commonWriteByte(Buffer var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteByte");
      Segment var2 = var0.writableSegment$okio(1);
      byte[] var3 = var2.data;
      int var4 = var2.limit++;
      var3[var4] = (byte)((byte)var1);
      var0.setSize$okio(var0.size() + 1L);
      return var0;
   }

   public static final Buffer commonWriteDecimalLong(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteDecimalLong");
      long var12;
      int var3 = (var12 = var1 - 0L) == 0L ? 0 : (var12 < 0L ? -1 : 1);
      if (var3 == 0) {
         return var0.writeByte(48);
      } else {
         boolean var4 = false;
         byte var5 = 1;
         long var6 = var1;
         if (var3 < 0) {
            var6 = -var1;
            if (var6 < 0L) {
               return var0.writeUtf8("-9223372036854775808");
            }

            var4 = true;
         }

         if (var6 < 100000000L) {
            if (var6 < 10000L) {
               if (var6 < 100L) {
                  if (var6 >= 10L) {
                     var5 = 2;
                  }
               } else if (var6 < 1000L) {
                  var5 = 3;
               } else {
                  var5 = 4;
               }
            } else if (var6 < 1000000L) {
               if (var6 < 100000L) {
                  var5 = 5;
               } else {
                  var5 = 6;
               }
            } else if (var6 < 10000000L) {
               var5 = 7;
            } else {
               var5 = 8;
            }
         } else if (var6 < 1000000000000L) {
            if (var6 < 10000000000L) {
               if (var6 < 1000000000L) {
                  var5 = 9;
               } else {
                  var5 = 10;
               }
            } else if (var6 < 100000000000L) {
               var5 = 11;
            } else {
               var5 = 12;
            }
         } else if (var6 < 1000000000000000L) {
            if (var6 < 10000000000000L) {
               var5 = 13;
            } else if (var6 < 100000000000000L) {
               var5 = 14;
            } else {
               var5 = 15;
            }
         } else if (var6 < 100000000000000000L) {
            if (var6 < 10000000000000000L) {
               var5 = 16;
            } else {
               var5 = 17;
            }
         } else if (var6 < 1000000000000000000L) {
            var5 = 18;
         } else {
            var5 = 19;
         }

         var3 = var5;
         if (var4) {
            var3 = var5 + 1;
         }

         Segment var8 = var0.writableSegment$okio(var3);
         byte[] var9 = var8.data;

         int var11;
         for(var11 = var8.limit + var3; var6 != 0L; var6 /= var1) {
            var1 = (long)10;
            int var10 = (int)(var6 % var1);
            --var11;
            var9[var11] = (byte)getHEX_DIGIT_BYTES()[var10];
         }

         if (var4) {
            var9[var11 - 1] = (byte)((byte)45);
         }

         var8.limit += var3;
         var0.setSize$okio(var0.size() + (long)var3);
         return var0;
      }
   }

   public static final Buffer commonWriteHexadecimalUnsignedLong(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteHexadecimalUnsignedLong");
      if (var1 == 0L) {
         return var0.writeByte(48);
      } else {
         long var3 = var1 >>> 1 | var1;
         var3 |= var3 >>> 2;
         var3 |= var3 >>> 4;
         var3 |= var3 >>> 8;
         var3 |= var3 >>> 16;
         var3 |= var3 >>> 32;
         var3 -= var3 >>> 1 & 6148914691236517205L;
         var3 = (var3 >>> 2 & 3689348814741910323L) + (var3 & 3689348814741910323L);
         var3 = (var3 >>> 4) + var3 & 1085102592571150095L;
         var3 += var3 >>> 8;
         var3 += var3 >>> 16;
         int var5 = (int)(((var3 & 63L) + (var3 >>> 32 & 63L) + (long)3) / (long)4);
         Segment var6 = var0.writableSegment$okio(var5);
         byte[] var7 = var6.data;
         int var8 = var6.limit + var5 - 1;

         for(int var9 = var6.limit; var8 >= var9; --var8) {
            var7[var8] = (byte)getHEX_DIGIT_BYTES()[(int)(15L & var1)];
            var1 >>>= 4;
         }

         var6.limit += var5;
         var0.setSize$okio(var0.size() + (long)var5);
         return var0;
      }
   }

   public static final Buffer commonWriteInt(Buffer var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteInt");
      Segment var2 = var0.writableSegment$okio(4);
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
      var0.setSize$okio(var0.size() + 4L);
      return var0;
   }

   public static final Buffer commonWriteLong(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteLong");
      Segment var3 = var0.writableSegment$okio(8);
      byte[] var4 = var3.data;
      int var5 = var3.limit;
      int var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 56 & 255L)));
      var5 = var6 + 1;
      var4[var6] = (byte)((byte)((int)(var1 >>> 48 & 255L)));
      var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 40 & 255L)));
      var5 = var6 + 1;
      var4[var6] = (byte)((byte)((int)(var1 >>> 32 & 255L)));
      var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 24 & 255L)));
      var5 = var6 + 1;
      var4[var6] = (byte)((byte)((int)(var1 >>> 16 & 255L)));
      var6 = var5 + 1;
      var4[var5] = (byte)((byte)((int)(var1 >>> 8 & 255L)));
      var4[var6] = (byte)((byte)((int)(var1 & 255L)));
      var3.limit = var6 + 1;
      var0.setSize$okio(var0.size() + 8L);
      return var0;
   }

   public static final Buffer commonWriteShort(Buffer var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteShort");
      Segment var2 = var0.writableSegment$okio(2);
      byte[] var3 = var2.data;
      int var4 = var2.limit;
      int var5 = var4 + 1;
      var3[var4] = (byte)((byte)(var1 >>> 8 & 255));
      var3[var5] = (byte)((byte)(var1 & 255));
      var2.limit = var5 + 1;
      var0.setSize$okio(var0.size() + 2L);
      return var0;
   }

   public static final Buffer commonWriteUtf8(Buffer var0, String var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteUtf8");
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
               var10 = new StringBuilder();
               var10.append("endIndex > string.length: ");
               var10.append(var3);
               var10.append(" > ");
               var10.append(var1.length());
               throw (Throwable)(new IllegalArgumentException(var10.toString().toString()));
            } else {
               while(true) {
                  while(var2 < var3) {
                     char var5 = var1.charAt(var2);
                     Segment var6;
                     int var9;
                     char var11;
                     int var12;
                     if (var5 < 128) {
                        var6 = var0.writableSegment$okio(1);
                        byte[] var7 = var6.data;
                        int var8 = var6.limit - var2;
                        var9 = Math.min(var3, 8192 - var8);
                        var12 = var2 + 1;
                        var7[var2 + var8] = (byte)((byte)var5);

                        for(var2 = var12; var2 < var9; ++var2) {
                           var11 = var1.charAt(var2);
                           if (var11 >= 128) {
                              break;
                           }

                           var7[var2 + var8] = (byte)((byte)var11);
                        }

                        var12 = var8 + var2 - var6.limit;
                        var6.limit += var12;
                        var0.setSize$okio(var0.size() + (long)var12);
                     } else {
                        if (var5 < 2048) {
                           var6 = var0.writableSegment$okio(2);
                           var6.data[var6.limit] = (byte)((byte)(var5 >> 6 | 192));
                           var6.data[var6.limit + 1] = (byte)((byte)(var5 & 63 | 128));
                           var6.limit += 2;
                           var0.setSize$okio(var0.size() + 2L);
                        } else {
                           if (var5 >= '\ud800' && var5 <= '\udfff') {
                              var9 = var2 + 1;
                              if (var9 < var3) {
                                 var11 = var1.charAt(var9);
                              } else {
                                 var11 = 0;
                              }

                              if (var5 <= '\udbff' && '\udc00' <= var11 && '\udfff' >= var11) {
                                 var12 = ((var5 & 1023) << 10 | var11 & 1023) + 65536;
                                 var6 = var0.writableSegment$okio(4);
                                 var6.data[var6.limit] = (byte)((byte)(var12 >> 18 | 240));
                                 var6.data[var6.limit + 1] = (byte)((byte)(var12 >> 12 & 63 | 128));
                                 var6.data[var6.limit + 2] = (byte)((byte)(var12 >> 6 & 63 | 128));
                                 var6.data[var6.limit + 3] = (byte)((byte)(var12 & 63 | 128));
                                 var6.limit += 4;
                                 var0.setSize$okio(var0.size() + 4L);
                                 var2 += 2;
                                 continue;
                              }

                              var0.writeByte(63);
                              var2 = var9;
                              continue;
                           }

                           var6 = var0.writableSegment$okio(3);
                           var6.data[var6.limit] = (byte)((byte)(var5 >> 12 | 224));
                           var6.data[var6.limit + 1] = (byte)((byte)(63 & var5 >> 6 | 128));
                           var6.data[var6.limit + 2] = (byte)((byte)(var5 & 63 | 128));
                           var6.limit += 3;
                           var0.setSize$okio(var0.size() + 3L);
                        }

                        ++var2;
                     }
                  }

                  return var0;
               }
            }
         }
      }
   }

   public static final Buffer commonWriteUtf8CodePoint(Buffer var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$commonWriteUtf8CodePoint");
      if (var1 < 128) {
         var0.writeByte(var1);
      } else {
         Segment var2;
         if (var1 < 2048) {
            var2 = var0.writableSegment$okio(2);
            var2.data[var2.limit] = (byte)((byte)(var1 >> 6 | 192));
            var2.data[var2.limit + 1] = (byte)((byte)(var1 & 63 | 128));
            var2.limit += 2;
            var0.setSize$okio(var0.size() + 2L);
         } else if (55296 <= var1 && 57343 >= var1) {
            var0.writeByte(63);
         } else if (var1 < 65536) {
            var2 = var0.writableSegment$okio(3);
            var2.data[var2.limit] = (byte)((byte)(var1 >> 12 | 224));
            var2.data[var2.limit + 1] = (byte)((byte)(var1 >> 6 & 63 | 128));
            var2.data[var2.limit + 2] = (byte)((byte)(var1 & 63 | 128));
            var2.limit += 3;
            var0.setSize$okio(var0.size() + 3L);
         } else {
            if (var1 > 1114111) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Unexpected code point: 0x");
               var3.append(_Util.toHexString(var1));
               throw (Throwable)(new IllegalArgumentException(var3.toString()));
            }

            var2 = var0.writableSegment$okio(4);
            var2.data[var2.limit] = (byte)((byte)(var1 >> 18 | 240));
            var2.data[var2.limit + 1] = (byte)((byte)(var1 >> 12 & 63 | 128));
            var2.data[var2.limit + 2] = (byte)((byte)(var1 >> 6 & 63 | 128));
            var2.data[var2.limit + 3] = (byte)((byte)(var1 & 63 | 128));
            var2.limit += 4;
            var0.setSize$okio(var0.size() + 4L);
         }
      }

      return var0;
   }

   public static final byte[] getHEX_DIGIT_BYTES() {
      return HEX_DIGIT_BYTES;
   }

   public static final boolean rangeEquals(Segment var0, int var1, byte[] var2, int var3, int var4) {
      Intrinsics.checkParameterIsNotNull(var0, "segment");
      Intrinsics.checkParameterIsNotNull(var2, "bytes");
      int var5 = var0.limit;

      Segment var8;
      for(byte[] var6 = var0.data; var3 < var4; var0 = var8) {
         int var7 = var5;
         var8 = var0;
         int var9 = var1;
         if (var1 == var5) {
            var8 = var0.next;
            if (var8 == null) {
               Intrinsics.throwNpe();
            }

            byte[] var10 = var8.data;
            var9 = var8.pos;
            var7 = var8.limit;
            var6 = var10;
         }

         if (var6[var9] != var2[var3]) {
            return false;
         }

         var1 = var9 + 1;
         ++var3;
         var5 = var7;
      }

      return true;
   }

   public static final String readUtf8Line(Buffer var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readUtf8Line");
      String var5;
      String var6;
      if (var1 > 0L) {
         long var3 = var1 - 1L;
         if (var0.getByte(var3) == (byte)13) {
            var5 = var0.readUtf8(var3);
            var0.skip(2L);
            var6 = var5;
            return var6;
         }
      }

      var5 = var0.readUtf8(var1);
      var0.skip(1L);
      var6 = var5;
      return var6;
   }

   public static final <T> T seek(Buffer var0, long var1, Function2<? super Segment, ? super Long, ? extends T> var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$seek");
      Intrinsics.checkParameterIsNotNull(var3, "lambda");
      Segment var4 = var0.head;
      if (var4 != null) {
         long var5;
         if (var0.size() - var1 < var1) {
            for(var5 = var0.size(); var5 > var1; var5 -= (long)(var4.limit - var4.pos)) {
               var4 = var4.prev;
               if (var4 == null) {
                  Intrinsics.throwNpe();
               }
            }

            return var3.invoke(var4, var5);
         } else {
            var5 = 0L;

            while(true) {
               long var7 = (long)(var4.limit - var4.pos) + var5;
               if (var7 > var1) {
                  return var3.invoke(var4, var5);
               }

               var4 = var4.next;
               if (var4 == null) {
                  Intrinsics.throwNpe();
               }

               var5 = var7;
            }
         }
      } else {
         return var3.invoke((Object)null, -1L);
      }
   }

   public static final int selectPrefix(Buffer var0, Options var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$selectPrefix");
      Intrinsics.checkParameterIsNotNull(var1, "options");
      Segment var3 = var0.head;
      byte var4 = -2;
      if (var3 == null) {
         if (!var2) {
            var4 = -1;
         }

         return var4;
      } else {
         byte[] var5 = var3.data;
         int var17 = var3.pos;
         int var6 = var3.limit;
         int[] var7 = var1.getTrie$okio();
         Segment var14 = var3;
         int var8 = 0;
         int var9 = -1;

         while(true) {
            int var10 = var8 + 1;
            int var11 = var7[var8];
            int var12 = var10 + 1;
            var8 = var7[var10];
            if (var8 != -1) {
               var9 = var8;
            }

            if (var14 != null) {
               label119: {
                  int var13;
                  Segment var16;
                  if (var11 >= 0) {
                     var10 = var17 + 1;
                     byte var20 = var5[var17];
                     var17 = var12;

                     while(true) {
                        if (var17 == var12 + var11) {
                           return var9;
                        }

                        if ((var20 & 255) == var7[var17]) {
                           var13 = var7[var17 + var11];
                           var12 = var13;
                           var8 = var6;
                           var16 = var14;
                           var17 = var10;
                           if (var10 == var6) {
                              var14 = var14.next;
                              if (var14 == null) {
                                 Intrinsics.throwNpe();
                              }

                              var17 = var14.pos;
                              var5 = var14.data;
                              var8 = var14.limit;
                              var16 = var14;
                              if (var14 == var3) {
                                 var16 = (Segment)null;
                              }

                              var12 = var13;
                           }
                           break;
                        }

                        ++var17;
                     }
                  } else {
                     var8 = var12;
                     byte[] var15 = var5;

                     while(true) {
                        var10 = var17 + 1;
                        var4 = var15[var17];
                        var13 = var8 + 1;
                        if ((var4 & 255) != var7[var8]) {
                           return var9;
                        }

                        boolean var19;
                        if (var13 == var12 + var11 * -1) {
                           var19 = true;
                        } else {
                           var19 = false;
                        }

                        if (var10 == var6) {
                           if (var14 == null) {
                              Intrinsics.throwNpe();
                           }

                           Segment var18 = var14.next;
                           if (var18 == null) {
                              Intrinsics.throwNpe();
                           }

                           var6 = var18.pos;
                           var15 = var18.data;
                           var17 = var18.limit;
                           var14 = var18;
                           if (var18 == var3) {
                              if (!var19) {
                                 break label119;
                              }

                              var14 = (Segment)null;
                           }
                        } else {
                           var17 = var6;
                           var6 = var10;
                        }

                        if (var19) {
                           var12 = var7[var13];
                           var5 = var15;
                           var8 = var17;
                           var16 = var14;
                           var17 = var6;
                           break;
                        }

                        var10 = var17;
                        var8 = var13;
                        var17 = var6;
                        var6 = var10;
                     }
                  }

                  if (var12 >= 0) {
                     return var12;
                  }

                  var12 = -var12;
                  var6 = var8;
                  var8 = var12;
                  var14 = var16;
                  continue;
               }
            }

            if (var2) {
               return -2;
            }

            return var9;
         }
      }
   }

   // $FF: synthetic method
   public static int selectPrefix$default(Buffer var0, Options var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return selectPrefix(var0, var1, var2);
   }
}
