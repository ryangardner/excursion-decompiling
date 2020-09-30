package okhttp3.internal.http2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0018\u0019B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J\u0014\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001a"},
   d2 = {"Lokhttp3/internal/http2/Hpack;", "", "()V", "NAME_TO_FIRST_INDEX", "", "Lokio/ByteString;", "", "getNAME_TO_FIRST_INDEX", "()Ljava/util/Map;", "PREFIX_4_BITS", "PREFIX_5_BITS", "PREFIX_6_BITS", "PREFIX_7_BITS", "SETTINGS_HEADER_TABLE_SIZE", "SETTINGS_HEADER_TABLE_SIZE_LIMIT", "STATIC_HEADER_TABLE", "", "Lokhttp3/internal/http2/Header;", "getSTATIC_HEADER_TABLE", "()[Lokhttp3/internal/http2/Header;", "[Lokhttp3/internal/http2/Header;", "checkLowercase", "name", "nameToFirstIndex", "Reader", "Writer", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Hpack {
   public static final Hpack INSTANCE;
   private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX;
   private static final int PREFIX_4_BITS = 15;
   private static final int PREFIX_5_BITS = 31;
   private static final int PREFIX_6_BITS = 63;
   private static final int PREFIX_7_BITS = 127;
   private static final int SETTINGS_HEADER_TABLE_SIZE = 4096;
   private static final int SETTINGS_HEADER_TABLE_SIZE_LIMIT = 16384;
   private static final Header[] STATIC_HEADER_TABLE;

   static {
      Hpack var0 = new Hpack();
      INSTANCE = var0;
      STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
      NAME_TO_FIRST_INDEX = var0.nameToFirstIndex();
   }

   private Hpack() {
   }

   private final Map<ByteString, Integer> nameToFirstIndex() {
      LinkedHashMap var1 = new LinkedHashMap(STATIC_HEADER_TABLE.length);
      int var2 = STATIC_HEADER_TABLE.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!var1.containsKey(STATIC_HEADER_TABLE[var3].name)) {
            ((Map)var1).put(STATIC_HEADER_TABLE[var3].name, var3);
         }
      }

      Map var4 = Collections.unmodifiableMap((Map)var1);
      Intrinsics.checkExpressionValueIsNotNull(var4, "Collections.unmodifiableMap(result)");
      return var4;
   }

   public final ByteString checkLowercase(ByteString var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      int var2 = var1.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         byte var4 = (byte)65;
         byte var5 = (byte)90;
         byte var6 = var1.getByte(var3);
         if (var4 <= var6 && var5 >= var6) {
            StringBuilder var7 = new StringBuilder();
            var7.append("PROTOCOL_ERROR response malformed: mixed case name: ");
            var7.append(var1.utf8());
            throw (Throwable)(new IOException(var7.toString()));
         }
      }

      return var1;
   }

   public final Map<ByteString, Integer> getNAME_TO_FIRST_INDEX() {
      return NAME_TO_FIRST_INDEX;
   }

   public final Header[] getSTATIC_HEADER_TABLE() {
      return STATIC_HEADER_TABLE;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0010\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005H\u0002J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0018\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0006\u0010\u0006\u001a\u00020\u0005J\b\u0010!\u001a\u00020\u0005H\u0002J\u0006\u0010\"\u001a\u00020\u001cJ\u0006\u0010#\u001a\u00020\u0013J\u0010\u0010$\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0016\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u00052\u0006\u0010'\u001a\u00020\u0005J\u0010\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u0005H\u0002J\b\u0010*\u001a\u00020\u0013H\u0002J\u0010\u0010+\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\b\u0010,\u001a\u00020\u0013H\u0002R\u001c\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u0012\u0010\f\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"},
      d2 = {"Lokhttp3/internal/http2/Hpack$Reader;", "", "source", "Lokio/Source;", "headerTableSizeSetting", "", "maxDynamicTableByteCount", "(Lokio/Source;II)V", "dynamicTable", "", "Lokhttp3/internal/http2/Header;", "[Lokhttp3/internal/http2/Header;", "dynamicTableByteCount", "headerCount", "headerList", "", "nextHeaderIndex", "Lokio/BufferedSource;", "adjustDynamicTableByteCount", "", "clearDynamicTable", "dynamicTableIndex", "index", "evictToRecoverBytes", "bytesToRecover", "getAndResetHeaderList", "", "getName", "Lokio/ByteString;", "insertIntoDynamicTable", "entry", "isStaticHeader", "", "readByte", "readByteString", "readHeaders", "readIndexedHeader", "readInt", "firstByte", "prefixMask", "readLiteralHeaderWithIncrementalIndexingIndexedName", "nameIndex", "readLiteralHeaderWithIncrementalIndexingNewName", "readLiteralHeaderWithoutIndexingIndexedName", "readLiteralHeaderWithoutIndexingNewName", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Reader {
      public Header[] dynamicTable;
      public int dynamicTableByteCount;
      public int headerCount;
      private final List<Header> headerList;
      private final int headerTableSizeSetting;
      private int maxDynamicTableByteCount;
      private int nextHeaderIndex;
      private final BufferedSource source;

      public Reader(Source var1, int var2) {
         this(var1, var2, 0, 4, (DefaultConstructorMarker)null);
      }

      public Reader(Source var1, int var2, int var3) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         super();
         this.headerTableSizeSetting = var2;
         this.maxDynamicTableByteCount = var3;
         this.headerList = (List)(new ArrayList());
         this.source = Okio.buffer(var1);
         Header[] var4 = new Header[8];
         this.dynamicTable = var4;
         this.nextHeaderIndex = var4.length - 1;
      }

      // $FF: synthetic method
      public Reader(Source var1, int var2, int var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 4) != 0) {
            var3 = var2;
         }

         this(var1, var2, var3);
      }

      private final void adjustDynamicTableByteCount() {
         int var1 = this.maxDynamicTableByteCount;
         int var2 = this.dynamicTableByteCount;
         if (var1 < var2) {
            if (var1 == 0) {
               this.clearDynamicTable();
            } else {
               this.evictToRecoverBytes(var2 - var1);
            }
         }

      }

      private final void clearDynamicTable() {
         ArraysKt.fill$default(this.dynamicTable, (Object)null, 0, 0, 6, (Object)null);
         this.nextHeaderIndex = this.dynamicTable.length - 1;
         this.headerCount = 0;
         this.dynamicTableByteCount = 0;
      }

      private final int dynamicTableIndex(int var1) {
         return this.nextHeaderIndex + 1 + var1;
      }

      private final int evictToRecoverBytes(int var1) {
         int var2 = 0;
         byte var3 = 0;
         if (var1 > 0) {
            var2 = this.dynamicTable.length - 1;
            int var4 = var1;

            for(var1 = var3; var2 >= this.nextHeaderIndex && var4 > 0; --var2) {
               Header var5 = this.dynamicTable[var2];
               if (var5 == null) {
                  Intrinsics.throwNpe();
               }

               var4 -= var5.hpackSize;
               this.dynamicTableByteCount -= var5.hpackSize;
               --this.headerCount;
               ++var1;
            }

            Header[] var6 = this.dynamicTable;
            var2 = this.nextHeaderIndex;
            System.arraycopy(var6, var2 + 1, var6, var2 + 1 + var1, this.headerCount);
            this.nextHeaderIndex += var1;
            var2 = var1;
         }

         return var2;
      }

      private final ByteString getName(int var1) throws IOException {
         ByteString var6;
         if (this.isStaticHeader(var1)) {
            var6 = Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var1].name;
            return var6;
         } else {
            int var3 = this.dynamicTableIndex(var1 - Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length);
            if (var3 >= 0) {
               Header[] var2 = this.dynamicTable;
               if (var3 < var2.length) {
                  Header var5 = var2[var3];
                  if (var5 == null) {
                     Intrinsics.throwNpe();
                  }

                  var6 = var5.name;
                  return var6;
               }
            }

            StringBuilder var4 = new StringBuilder();
            var4.append("Header index too large ");
            var4.append(var1 + 1);
            throw (Throwable)(new IOException(var4.toString()));
         }
      }

      private final void insertIntoDynamicTable(int var1, Header var2) {
         this.headerList.add(var2);
         int var3 = var2.hpackSize;
         int var4 = var3;
         if (var1 != -1) {
            Header var5 = this.dynamicTable[this.dynamicTableIndex(var1)];
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            var4 = var3 - var5.hpackSize;
         }

         var3 = this.maxDynamicTableByteCount;
         if (var4 > var3) {
            this.clearDynamicTable();
         } else {
            var3 = this.evictToRecoverBytes(this.dynamicTableByteCount + var4 - var3);
            if (var1 == -1) {
               var1 = this.headerCount;
               Header[] var6 = this.dynamicTable;
               if (var1 + 1 > var6.length) {
                  Header[] var8 = new Header[var6.length * 2];
                  System.arraycopy(var6, 0, var8, var6.length, var6.length);
                  this.nextHeaderIndex = this.dynamicTable.length - 1;
                  this.dynamicTable = var8;
               }

               var1 = this.nextHeaderIndex--;
               this.dynamicTable[var1] = var2;
               ++this.headerCount;
            } else {
               int var7 = this.dynamicTableIndex(var1);
               this.dynamicTable[var1 + var7 + var3] = var2;
            }

            this.dynamicTableByteCount += var4;
         }
      }

      private final boolean isStaticHeader(int var1) {
         boolean var2 = true;
         if (var1 < 0 || var1 > Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length - 1) {
            var2 = false;
         }

         return var2;
      }

      private final int readByte() throws IOException {
         return Util.and((byte)this.source.readByte(), 255);
      }

      private final void readIndexedHeader(int var1) throws IOException {
         if (this.isStaticHeader(var1)) {
            Header var6 = Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var1];
            this.headerList.add(var6);
         } else {
            int var3 = this.dynamicTableIndex(var1 - Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length);
            if (var3 >= 0) {
               Header[] var4 = this.dynamicTable;
               if (var3 < var4.length) {
                  Collection var5 = (Collection)this.headerList;
                  Header var7 = var4[var3];
                  if (var7 == null) {
                     Intrinsics.throwNpe();
                  }

                  var5.add(var7);
                  return;
               }
            }

            StringBuilder var2 = new StringBuilder();
            var2.append("Header index too large ");
            var2.append(var1 + 1);
            throw (Throwable)(new IOException(var2.toString()));
         }
      }

      private final void readLiteralHeaderWithIncrementalIndexingIndexedName(int var1) throws IOException {
         this.insertIntoDynamicTable(-1, new Header(this.getName(var1), this.readByteString()));
      }

      private final void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
         this.insertIntoDynamicTable(-1, new Header(Hpack.INSTANCE.checkLowercase(this.readByteString()), this.readByteString()));
      }

      private final void readLiteralHeaderWithoutIndexingIndexedName(int var1) throws IOException {
         ByteString var2 = this.getName(var1);
         ByteString var3 = this.readByteString();
         this.headerList.add(new Header(var2, var3));
      }

      private final void readLiteralHeaderWithoutIndexingNewName() throws IOException {
         ByteString var1 = Hpack.INSTANCE.checkLowercase(this.readByteString());
         ByteString var2 = this.readByteString();
         this.headerList.add(new Header(var1, var2));
      }

      public final List<Header> getAndResetHeaderList() {
         List var1 = CollectionsKt.toList((Iterable)this.headerList);
         this.headerList.clear();
         return var1;
      }

      public final int maxDynamicTableByteCount() {
         return this.maxDynamicTableByteCount;
      }

      public final ByteString readByteString() throws IOException {
         int var1 = this.readByte();
         boolean var2;
         if ((var1 & 128) == 128) {
            var2 = true;
         } else {
            var2 = false;
         }

         long var3 = (long)this.readInt(var1, 127);
         ByteString var6;
         if (var2) {
            Buffer var5 = new Buffer();
            Huffman.INSTANCE.decode(this.source, var3, (BufferedSink)var5);
            var6 = var5.readByteString();
         } else {
            var6 = this.source.readByteString(var3);
         }

         return var6;
      }

      public final void readHeaders() throws IOException {
         while(true) {
            if (!this.source.exhausted()) {
               int var1 = Util.and((byte)this.source.readByte(), 255);
               if (var1 != 128) {
                  if ((var1 & 128) == 128) {
                     this.readIndexedHeader(this.readInt(var1, 127) - 1);
                     continue;
                  }

                  if (var1 == 64) {
                     this.readLiteralHeaderWithIncrementalIndexingNewName();
                     continue;
                  }

                  if ((var1 & 64) == 64) {
                     this.readLiteralHeaderWithIncrementalIndexingIndexedName(this.readInt(var1, 63) - 1);
                     continue;
                  }

                  if ((var1 & 32) == 32) {
                     var1 = this.readInt(var1, 31);
                     this.maxDynamicTableByteCount = var1;
                     if (var1 >= 0 && var1 <= this.headerTableSizeSetting) {
                        this.adjustDynamicTableByteCount();
                        continue;
                     }

                     StringBuilder var2 = new StringBuilder();
                     var2.append("Invalid dynamic table size update ");
                     var2.append(this.maxDynamicTableByteCount);
                     throw (Throwable)(new IOException(var2.toString()));
                  }

                  if (var1 != 16 && var1 != 0) {
                     this.readLiteralHeaderWithoutIndexingIndexedName(this.readInt(var1, 15) - 1);
                     continue;
                  }

                  this.readLiteralHeaderWithoutIndexingNewName();
                  continue;
               }

               throw (Throwable)(new IOException("index == 0"));
            }

            return;
         }
      }

      public final int readInt(int var1, int var2) throws IOException {
         var1 &= var2;
         if (var1 < var2) {
            return var1;
         } else {
            var1 = 0;

            while(true) {
               int var3 = this.readByte();
               if ((var3 & 128) == 0) {
                  return var2 + (var3 << var1);
               }

               var2 += (var3 & 127) << var1;
               var1 += 7;
            }
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001B#\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003H\u0002J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dJ\u0014\u0010\u001e\u001a\u00020\u00142\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0 J\u001e\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u0003R\u001c\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0004\n\u0002\u0010\fR\u0012\u0010\r\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0010\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"},
      d2 = {"Lokhttp3/internal/http2/Hpack$Writer;", "", "headerTableSizeSetting", "", "useCompression", "", "out", "Lokio/Buffer;", "(IZLokio/Buffer;)V", "dynamicTable", "", "Lokhttp3/internal/http2/Header;", "[Lokhttp3/internal/http2/Header;", "dynamicTableByteCount", "emitDynamicTableSizeUpdate", "headerCount", "maxDynamicTableByteCount", "nextHeaderIndex", "smallestHeaderTableSizeSetting", "adjustDynamicTableByteCount", "", "clearDynamicTable", "evictToRecoverBytes", "bytesToRecover", "insertIntoDynamicTable", "entry", "resizeHeaderTable", "writeByteString", "data", "Lokio/ByteString;", "writeHeaders", "headerBlock", "", "writeInt", "value", "prefixMask", "bits", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Writer {
      public Header[] dynamicTable;
      public int dynamicTableByteCount;
      private boolean emitDynamicTableSizeUpdate;
      public int headerCount;
      public int headerTableSizeSetting;
      public int maxDynamicTableByteCount;
      private int nextHeaderIndex;
      private final Buffer out;
      private int smallestHeaderTableSizeSetting;
      private final boolean useCompression;

      public Writer(int var1, Buffer var2) {
         this(var1, false, var2, 2, (DefaultConstructorMarker)null);
      }

      public Writer(int var1, boolean var2, Buffer var3) {
         Intrinsics.checkParameterIsNotNull(var3, "out");
         super();
         this.headerTableSizeSetting = var1;
         this.useCompression = var2;
         this.out = var3;
         this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
         this.maxDynamicTableByteCount = var1;
         Header[] var4 = new Header[8];
         this.dynamicTable = var4;
         this.nextHeaderIndex = var4.length - 1;
      }

      // $FF: synthetic method
      public Writer(int var1, boolean var2, Buffer var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 1) != 0) {
            var1 = 4096;
         }

         if ((var4 & 2) != 0) {
            var2 = true;
         }

         this(var1, var2, var3);
      }

      public Writer(Buffer var1) {
         this(0, false, var1, 3, (DefaultConstructorMarker)null);
      }

      private final void adjustDynamicTableByteCount() {
         int var1 = this.maxDynamicTableByteCount;
         int var2 = this.dynamicTableByteCount;
         if (var1 < var2) {
            if (var1 == 0) {
               this.clearDynamicTable();
            } else {
               this.evictToRecoverBytes(var2 - var1);
            }
         }

      }

      private final void clearDynamicTable() {
         ArraysKt.fill$default(this.dynamicTable, (Object)null, 0, 0, 6, (Object)null);
         this.nextHeaderIndex = this.dynamicTable.length - 1;
         this.headerCount = 0;
         this.dynamicTableByteCount = 0;
      }

      private final int evictToRecoverBytes(int var1) {
         int var2 = 0;
         byte var3 = 0;
         if (var1 > 0) {
            var2 = this.dynamicTable.length - 1;
            int var4 = var1;

            for(var1 = var3; var2 >= this.nextHeaderIndex && var4 > 0; --var2) {
               Header var5 = this.dynamicTable[var2];
               if (var5 == null) {
                  Intrinsics.throwNpe();
               }

               var4 -= var5.hpackSize;
               int var6 = this.dynamicTableByteCount;
               var5 = this.dynamicTable[var2];
               if (var5 == null) {
                  Intrinsics.throwNpe();
               }

               this.dynamicTableByteCount = var6 - var5.hpackSize;
               --this.headerCount;
               ++var1;
            }

            Header[] var7 = this.dynamicTable;
            var2 = this.nextHeaderIndex;
            System.arraycopy(var7, var2 + 1, var7, var2 + 1 + var1, this.headerCount);
            var7 = this.dynamicTable;
            var2 = this.nextHeaderIndex;
            Arrays.fill(var7, var2 + 1, var2 + 1 + var1, (Object)null);
            this.nextHeaderIndex += var1;
            var2 = var1;
         }

         return var2;
      }

      private final void insertIntoDynamicTable(Header var1) {
         int var2 = var1.hpackSize;
         int var3 = this.maxDynamicTableByteCount;
         if (var2 > var3) {
            this.clearDynamicTable();
         } else {
            this.evictToRecoverBytes(this.dynamicTableByteCount + var2 - var3);
            var3 = this.headerCount;
            Header[] var4 = this.dynamicTable;
            if (var3 + 1 > var4.length) {
               Header[] var5 = new Header[var4.length * 2];
               System.arraycopy(var4, 0, var5, var4.length, var4.length);
               this.nextHeaderIndex = this.dynamicTable.length - 1;
               this.dynamicTable = var5;
            }

            var3 = this.nextHeaderIndex--;
            this.dynamicTable[var3] = var1;
            ++this.headerCount;
            this.dynamicTableByteCount += var2;
         }
      }

      public final void resizeHeaderTable(int var1) {
         this.headerTableSizeSetting = var1;
         int var2 = Math.min(var1, 16384);
         var1 = this.maxDynamicTableByteCount;
         if (var1 != var2) {
            if (var2 < var1) {
               this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, var2);
            }

            this.emitDynamicTableSizeUpdate = true;
            this.maxDynamicTableByteCount = var2;
            this.adjustDynamicTableByteCount();
         }
      }

      public final void writeByteString(ByteString var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "data");
         if (this.useCompression && Huffman.INSTANCE.encodedLength(var1) < var1.size()) {
            Buffer var2 = new Buffer();
            Huffman.INSTANCE.encode(var1, (BufferedSink)var2);
            var1 = var2.readByteString();
            this.writeInt(var1.size(), 127, 128);
            this.out.write(var1);
         } else {
            this.writeInt(var1.size(), 127, 0);
            this.out.write(var1);
         }

      }

      public final void writeHeaders(List<Header> var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "headerBlock");
         int var2;
         if (this.emitDynamicTableSizeUpdate) {
            var2 = this.smallestHeaderTableSizeSetting;
            if (var2 < this.maxDynamicTableByteCount) {
               this.writeInt(var2, 31, 32);
            }

            this.emitDynamicTableSizeUpdate = false;
            this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
            this.writeInt(this.maxDynamicTableByteCount, 31, 32);
         }

         int var3 = var1.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            Header var5 = (Header)var1.get(var4);
            ByteString var6 = var5.name.toAsciiLowercase();
            ByteString var7 = var5.value;
            Integer var8 = (Integer)Hpack.INSTANCE.getNAME_TO_FIRST_INDEX().get(var6);
            int var9;
            if (var8 != null) {
               label57: {
                  var9 = var8 + 1;
                  if (2 <= var9 && 7 >= var9) {
                     if (Intrinsics.areEqual((Object)Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var9 - 1].value, (Object)var7)) {
                        var2 = var9;
                        break label57;
                     }

                     if (Intrinsics.areEqual((Object)Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var9].value, (Object)var7)) {
                        var2 = var9++;
                        break label57;
                     }
                  }

                  var2 = var9;
                  var9 = -1;
               }
            } else {
               var9 = -1;
               var2 = -1;
            }

            int var10 = var9;
            int var11 = var2;
            if (var9 == -1) {
               int var12 = this.nextHeaderIndex + 1;
               int var13 = this.dynamicTable.length;

               while(true) {
                  var10 = var9;
                  var11 = var2;
                  if (var12 >= var13) {
                     break;
                  }

                  Header var14 = this.dynamicTable[var12];
                  if (var14 == null) {
                     Intrinsics.throwNpe();
                  }

                  var10 = var2;
                  if (Intrinsics.areEqual((Object)var14.name, (Object)var6)) {
                     var14 = this.dynamicTable[var12];
                     if (var14 == null) {
                        Intrinsics.throwNpe();
                     }

                     if (Intrinsics.areEqual((Object)var14.value, (Object)var7)) {
                        var9 = this.nextHeaderIndex;
                        var10 = Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length + (var12 - var9);
                        var11 = var2;
                        break;
                     }

                     var10 = var2;
                     if (var2 == -1) {
                        var10 = var12 - this.nextHeaderIndex + Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length;
                     }
                  }

                  ++var12;
                  var2 = var10;
               }
            }

            if (var10 != -1) {
               this.writeInt(var10, 127, 128);
            } else if (var11 == -1) {
               this.out.writeByte(64);
               this.writeByteString(var6);
               this.writeByteString(var7);
               this.insertIntoDynamicTable(var5);
            } else if (var6.startsWith(Header.PSEUDO_PREFIX) && Intrinsics.areEqual((Object)Header.TARGET_AUTHORITY, (Object)var6) ^ true) {
               this.writeInt(var11, 15, 0);
               this.writeByteString(var7);
            } else {
               this.writeInt(var11, 63, 64);
               this.writeByteString(var7);
               this.insertIntoDynamicTable(var5);
            }
         }

      }

      public final void writeInt(int var1, int var2, int var3) {
         if (var1 < var2) {
            this.out.writeByte(var1 | var3);
         } else {
            this.out.writeByte(var3 | var2);

            for(var1 -= var2; var1 >= 128; var1 >>>= 7) {
               this.out.writeByte(128 | var1 & 127);
            }

            this.out.writeByte(var1);
         }
      }
   }
}
