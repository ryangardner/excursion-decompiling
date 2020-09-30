/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Huffman;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0018\u0019B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J\u0014\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001a"}, d2={"Lokhttp3/internal/http2/Hpack;", "", "()V", "NAME_TO_FIRST_INDEX", "", "Lokio/ByteString;", "", "getNAME_TO_FIRST_INDEX", "()Ljava/util/Map;", "PREFIX_4_BITS", "PREFIX_5_BITS", "PREFIX_6_BITS", "PREFIX_7_BITS", "SETTINGS_HEADER_TABLE_SIZE", "SETTINGS_HEADER_TABLE_SIZE_LIMIT", "STATIC_HEADER_TABLE", "", "Lokhttp3/internal/http2/Header;", "getSTATIC_HEADER_TABLE", "()[Lokhttp3/internal/http2/Header;", "[Lokhttp3/internal/http2/Header;", "checkLowercase", "name", "nameToFirstIndex", "Reader", "Writer", "okhttp"}, k=1, mv={1, 1, 16})
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
        Hpack hpack;
        INSTANCE = hpack = new Hpack();
        STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
        NAME_TO_FIRST_INDEX = hpack.nameToFirstIndex();
    }

    private Hpack() {
    }

    private final Map<ByteString, Integer> nameToFirstIndex() {
        LinkedHashMap<ByteString, Integer> linkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        int n = STATIC_HEADER_TABLE.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                linkedHashMap = Collections.unmodifiableMap((Map)linkedHashMap);
                Intrinsics.checkExpressionValueIsNotNull(linkedHashMap, "Collections.unmodifiableMap(result)");
                return linkedHashMap;
            }
            if (!linkedHashMap.containsKey(Hpack.STATIC_HEADER_TABLE[n2].name)) {
                ((Map)linkedHashMap).put(Hpack.STATIC_HEADER_TABLE[n2].name, n2);
            }
            ++n2;
        } while (true);
    }

    public final ByteString checkLowercase(ByteString byteString) throws IOException {
        Intrinsics.checkParameterIsNotNull(byteString, "name");
        int n = byteString.size();
        int n2 = 0;
        while (n2 < n) {
            byte by = (byte)65;
            byte by2 = (byte)90;
            byte by3 = byteString.getByte(n2);
            if (by <= by3 && by2 >= by3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
                stringBuilder.append(byteString.utf8());
                throw (Throwable)new IOException(stringBuilder.toString());
            }
            ++n2;
        }
        return byteString;
    }

    public final Map<ByteString, Integer> getNAME_TO_FIRST_INDEX() {
        return NAME_TO_FIRST_INDEX;
    }

    public final Header[] getSTATIC_HEADER_TABLE() {
        return STATIC_HEADER_TABLE;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0010\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005H\u0002J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0018\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0006\u0010\u0006\u001a\u00020\u0005J\b\u0010!\u001a\u00020\u0005H\u0002J\u0006\u0010\"\u001a\u00020\u001cJ\u0006\u0010#\u001a\u00020\u0013J\u0010\u0010$\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0016\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u00052\u0006\u0010'\u001a\u00020\u0005J\u0010\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u0005H\u0002J\b\u0010*\u001a\u00020\u0013H\u0002J\u0010\u0010+\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\b\u0010,\u001a\u00020\u0013H\u0002R\u001c\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u0012\u0010\f\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2={"Lokhttp3/internal/http2/Hpack$Reader;", "", "source", "Lokio/Source;", "headerTableSizeSetting", "", "maxDynamicTableByteCount", "(Lokio/Source;II)V", "dynamicTable", "", "Lokhttp3/internal/http2/Header;", "[Lokhttp3/internal/http2/Header;", "dynamicTableByteCount", "headerCount", "headerList", "", "nextHeaderIndex", "Lokio/BufferedSource;", "adjustDynamicTableByteCount", "", "clearDynamicTable", "dynamicTableIndex", "index", "evictToRecoverBytes", "bytesToRecover", "getAndResetHeaderList", "", "getName", "Lokio/ByteString;", "insertIntoDynamicTable", "entry", "isStaticHeader", "", "readByte", "readByteString", "readHeaders", "readIndexedHeader", "readInt", "firstByte", "prefixMask", "readLiteralHeaderWithIncrementalIndexingIndexedName", "nameIndex", "readLiteralHeaderWithIncrementalIndexingNewName", "readLiteralHeaderWithoutIndexingIndexedName", "readLiteralHeaderWithoutIndexingNewName", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Reader {
        public Header[] dynamicTable;
        public int dynamicTableByteCount;
        public int headerCount;
        private final List<Header> headerList;
        private final int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        private int nextHeaderIndex;
        private final BufferedSource source;

        public Reader(Source source2, int n) {
            this(source2, n, 0, 4, null);
        }

        public Reader(Source arrheader, int n, int n2) {
            Intrinsics.checkParameterIsNotNull(arrheader, "source");
            this.headerTableSizeSetting = n;
            this.maxDynamicTableByteCount = n2;
            this.headerList = new ArrayList();
            this.source = Okio.buffer((Source)arrheader);
            arrheader = new Header[8];
            this.dynamicTable = arrheader;
            this.nextHeaderIndex = arrheader.length - 1;
        }

        public /* synthetic */ Reader(Source source2, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n3 & 4) != 0) {
                n2 = n;
            }
            this(source2, n, n2);
        }

        private final void adjustDynamicTableByteCount() {
            int n = this.maxDynamicTableByteCount;
            int n2 = this.dynamicTableByteCount;
            if (n >= n2) return;
            if (n == 0) {
                this.clearDynamicTable();
                return;
            }
            this.evictToRecoverBytes(n2 - n);
        }

        private final void clearDynamicTable() {
            ArraysKt.fill$default(this.dynamicTable, null, 0, 0, 6, null);
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private final int dynamicTableIndex(int n) {
            return this.nextHeaderIndex + 1 + n;
        }

        private final int evictToRecoverBytes(int n) {
            Object object;
            int n2 = 0;
            int n3 = 0;
            if (n <= 0) return n2;
            int n4 = n;
            n = n3;
            for (n2 = this.dynamicTable.length - 1; n2 >= this.nextHeaderIndex && n4 > 0; n4 -= object.hpackSize, this.dynamicTableByteCount -= object.hpackSize, --this.headerCount, ++n, --n2) {
                object = this.dynamicTable[n2];
                if (object != null) continue;
                Intrinsics.throwNpe();
            }
            object = this.dynamicTable;
            n2 = this.nextHeaderIndex;
            System.arraycopy(object, n2 + 1, object, n2 + 1 + n, this.headerCount);
            this.nextHeaderIndex += n;
            return n;
        }

        private final ByteString getName(int n) throws IOException {
            Object object;
            if (this.isStaticHeader(n)) {
                return Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[n].name;
            }
            int n2 = this.dynamicTableIndex(n - INSTANCE.getSTATIC_HEADER_TABLE().length);
            if (n2 >= 0 && n2 < ((Header[])(object = this.dynamicTable)).length) {
                if ((object = object[n2]) != null) return ((Header)object).name;
                Intrinsics.throwNpe();
                return ((Header)object).name;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Header index too large ");
            ((StringBuilder)object).append(n + 1);
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }

        private final void insertIntoDynamicTable(int n, Header header) {
            Header[] arrheader;
            int n2;
            this.headerList.add(header);
            int n3 = n2 = header.hpackSize;
            if (n != -1) {
                arrheader = this.dynamicTable[this.dynamicTableIndex(n)];
                if (arrheader == null) {
                    Intrinsics.throwNpe();
                }
                n3 = n2 - arrheader.hpackSize;
            }
            if (n3 > (n2 = this.maxDynamicTableByteCount)) {
                this.clearDynamicTable();
                return;
            }
            n2 = this.evictToRecoverBytes(this.dynamicTableByteCount + n3 - n2);
            if (n == -1) {
                n = this.headerCount;
                Header[] arrheader2 = this.dynamicTable;
                if (n + 1 > arrheader2.length) {
                    arrheader = new Header[arrheader2.length * 2];
                    System.arraycopy(arrheader2, 0, arrheader, arrheader2.length, arrheader2.length);
                    this.nextHeaderIndex = this.dynamicTable.length - 1;
                    this.dynamicTable = arrheader;
                }
                n = this.nextHeaderIndex;
                this.nextHeaderIndex = n - 1;
                this.dynamicTable[n] = header;
                ++this.headerCount;
            } else {
                int n4 = this.dynamicTableIndex(n);
                this.dynamicTable[n + (n4 + n2)] = header;
            }
            this.dynamicTableByteCount += n3;
        }

        private final boolean isStaticHeader(int n) {
            boolean bl = true;
            if (n < 0) return false;
            if (n > INSTANCE.getSTATIC_HEADER_TABLE().length - 1) return false;
            return bl;
        }

        private final int readByte() throws IOException {
            return Util.and(this.source.readByte(), 255);
        }

        private final void readIndexedHeader(int n) throws IOException {
            Object object;
            if (this.isStaticHeader(n)) {
                Header header = INSTANCE.getSTATIC_HEADER_TABLE()[n];
                this.headerList.add(header);
                return;
            }
            int n2 = this.dynamicTableIndex(n - INSTANCE.getSTATIC_HEADER_TABLE().length);
            if (n2 >= 0 && n2 < ((Header[])(object = this.dynamicTable)).length) {
                Collection collection = this.headerList;
                if ((object = object[n2]) == null) {
                    Intrinsics.throwNpe();
                }
                collection.add(object);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Header index too large ");
            stringBuilder.append(n + 1);
            throw (Throwable)new IOException(stringBuilder.toString());
        }

        private final void readLiteralHeaderWithIncrementalIndexingIndexedName(int n) throws IOException {
            this.insertIntoDynamicTable(-1, new Header(this.getName(n), this.readByteString()));
        }

        private final void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            this.insertIntoDynamicTable(-1, new Header(INSTANCE.checkLowercase(this.readByteString()), this.readByteString()));
        }

        private final void readLiteralHeaderWithoutIndexingIndexedName(int n) throws IOException {
            ByteString byteString = this.getName(n);
            ByteString byteString2 = this.readByteString();
            this.headerList.add(new Header(byteString, byteString2));
        }

        private final void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            ByteString byteString = INSTANCE.checkLowercase(this.readByteString());
            ByteString byteString2 = this.readByteString();
            this.headerList.add(new Header(byteString, byteString2));
        }

        public final List<Header> getAndResetHeaderList() {
            List<Header> list = CollectionsKt.toList((Iterable)this.headerList);
            this.headerList.clear();
            return list;
        }

        public final int maxDynamicTableByteCount() {
            return this.maxDynamicTableByteCount;
        }

        public final ByteString readByteString() throws IOException {
            int n = this.readByte();
            boolean bl = (n & 128) == 128;
            long l = this.readInt(n, 127);
            if (!bl) return this.source.readByteString(l);
            Buffer buffer = new Buffer();
            Huffman.INSTANCE.decode(this.source, l, buffer);
            return buffer.readByteString();
        }

        public final void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int n = Util.and(this.source.readByte(), 255);
                if (n == 128) throw (Throwable)new IOException("index == 0");
                if ((n & 128) == 128) {
                    this.readIndexedHeader(this.readInt(n, 127) - 1);
                    continue;
                }
                if (n == 64) {
                    this.readLiteralHeaderWithIncrementalIndexingNewName();
                    continue;
                }
                if ((n & 64) == 64) {
                    this.readLiteralHeaderWithIncrementalIndexingIndexedName(this.readInt(n, 63) - 1);
                    continue;
                }
                if ((n & 32) == 32) {
                    this.maxDynamicTableByteCount = n = this.readInt(n, 31);
                    if (n >= 0 && n <= this.headerTableSizeSetting) {
                        this.adjustDynamicTableByteCount();
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid dynamic table size update ");
                    stringBuilder.append(this.maxDynamicTableByteCount);
                    throw (Throwable)new IOException(stringBuilder.toString());
                }
                if (n != 16 && n != 0) {
                    this.readLiteralHeaderWithoutIndexingIndexedName(this.readInt(n, 15) - 1);
                    continue;
                }
                this.readLiteralHeaderWithoutIndexingNewName();
            }
        }

        public final int readInt(int n, int n2) throws IOException {
            int n3;
            if ((n &= n2) < n2) {
                return n;
            }
            n = 0;
            while (((n3 = this.readByte()) & 128) != 0) {
                n2 += (n3 & 127) << n;
                n += 7;
            }
            return n2 + (n3 << n);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001B#\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003H\u0002J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dJ\u0014\u0010\u001e\u001a\u00020\u00142\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0 J\u001e\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u0003R\u001c\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0004\n\u0002\u0010\fR\u0012\u0010\r\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0010\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2={"Lokhttp3/internal/http2/Hpack$Writer;", "", "headerTableSizeSetting", "", "useCompression", "", "out", "Lokio/Buffer;", "(IZLokio/Buffer;)V", "dynamicTable", "", "Lokhttp3/internal/http2/Header;", "[Lokhttp3/internal/http2/Header;", "dynamicTableByteCount", "emitDynamicTableSizeUpdate", "headerCount", "maxDynamicTableByteCount", "nextHeaderIndex", "smallestHeaderTableSizeSetting", "adjustDynamicTableByteCount", "", "clearDynamicTable", "evictToRecoverBytes", "bytesToRecover", "insertIntoDynamicTable", "entry", "resizeHeaderTable", "writeByteString", "data", "Lokio/ByteString;", "writeHeaders", "headerBlock", "", "writeInt", "value", "prefixMask", "bits", "okhttp"}, k=1, mv={1, 1, 16})
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

        public Writer(int n, Buffer buffer) {
            this(n, false, buffer, 2, null);
        }

        public Writer(int n, boolean bl, Buffer arrheader) {
            Intrinsics.checkParameterIsNotNull(arrheader, "out");
            this.headerTableSizeSetting = n;
            this.useCompression = bl;
            this.out = arrheader;
            this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
            this.maxDynamicTableByteCount = n;
            arrheader = new Header[8];
            this.dynamicTable = arrheader;
            this.nextHeaderIndex = arrheader.length - 1;
        }

        public /* synthetic */ Writer(int n, boolean bl, Buffer buffer, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 1) != 0) {
                n = 4096;
            }
            if ((n2 & 2) != 0) {
                bl = true;
            }
            this(n, bl, buffer);
        }

        public Writer(Buffer buffer) {
            this(0, false, buffer, 3, null);
        }

        private final void adjustDynamicTableByteCount() {
            int n = this.maxDynamicTableByteCount;
            int n2 = this.dynamicTableByteCount;
            if (n >= n2) return;
            if (n == 0) {
                this.clearDynamicTable();
                return;
            }
            this.evictToRecoverBytes(n2 - n);
        }

        private final void clearDynamicTable() {
            ArraysKt.fill$default(this.dynamicTable, null, 0, 0, 6, null);
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private final int evictToRecoverBytes(int n) {
            Object object;
            int n2 = 0;
            int n3 = 0;
            if (n <= 0) return n2;
            int n4 = n;
            n = n3;
            for (n2 = this.dynamicTable.length - 1; n2 >= this.nextHeaderIndex && n4 > 0; --this.headerCount, ++n, --n2) {
                object = this.dynamicTable[n2];
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                n4 -= object.hpackSize;
                n3 = this.dynamicTableByteCount;
                object = this.dynamicTable[n2];
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                this.dynamicTableByteCount = n3 - object.hpackSize;
            }
            object = this.dynamicTable;
            n2 = this.nextHeaderIndex;
            System.arraycopy(object, n2 + 1, object, n2 + 1 + n, this.headerCount);
            object = this.dynamicTable;
            n2 = this.nextHeaderIndex;
            Arrays.fill(object, n2 + 1, n2 + 1 + n, null);
            this.nextHeaderIndex += n;
            return n;
        }

        private final void insertIntoDynamicTable(Header header) {
            int n = header.hpackSize;
            int n2 = this.maxDynamicTableByteCount;
            if (n > n2) {
                this.clearDynamicTable();
                return;
            }
            this.evictToRecoverBytes(this.dynamicTableByteCount + n - n2);
            n2 = this.headerCount;
            Header[] arrheader = this.dynamicTable;
            if (n2 + 1 > arrheader.length) {
                Header[] arrheader2 = new Header[arrheader.length * 2];
                System.arraycopy(arrheader, 0, arrheader2, arrheader.length, arrheader.length);
                this.nextHeaderIndex = this.dynamicTable.length - 1;
                this.dynamicTable = arrheader2;
            }
            n2 = this.nextHeaderIndex;
            this.nextHeaderIndex = n2 - 1;
            this.dynamicTable[n2] = header;
            ++this.headerCount;
            this.dynamicTableByteCount += n;
        }

        public final void resizeHeaderTable(int n) {
            this.headerTableSizeSetting = n;
            int n2 = Math.min(n, 16384);
            n = this.maxDynamicTableByteCount;
            if (n == n2) {
                return;
            }
            if (n2 < n) {
                this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, n2);
            }
            this.emitDynamicTableSizeUpdate = true;
            this.maxDynamicTableByteCount = n2;
            this.adjustDynamicTableByteCount();
        }

        public final void writeByteString(ByteString byteString) throws IOException {
            Intrinsics.checkParameterIsNotNull(byteString, "data");
            if (this.useCompression && Huffman.INSTANCE.encodedLength(byteString) < byteString.size()) {
                Buffer buffer = new Buffer();
                Huffman.INSTANCE.encode(byteString, buffer);
                byteString = buffer.readByteString();
                this.writeInt(byteString.size(), 127, 128);
                this.out.write(byteString);
                return;
            }
            this.writeInt(byteString.size(), 127, 0);
            this.out.write(byteString);
        }

        /*
         * Unable to fully structure code
         */
        public final void writeHeaders(List<Header> var1_1) throws IOException {
            Intrinsics.checkParameterIsNotNull(var1_1, "headerBlock");
            if (this.emitDynamicTableSizeUpdate) {
                var2_2 = this.smallestHeaderTableSizeSetting;
                if (var2_2 < this.maxDynamicTableByteCount) {
                    this.writeInt(var2_2, 31, 32);
                }
                this.emitDynamicTableSizeUpdate = false;
                this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
                this.writeInt(this.maxDynamicTableByteCount, 31, 32);
            }
            var3_3 = var1_1.size();
            var4_4 = 0;
            while (var4_4 < var3_3) {
                block21 : {
                    block20 : {
                        var5_5 = var1_1.get(var4_4);
                        var6_6 = var5_5.name.toAsciiLowercase();
                        var7_7 = var5_5.value;
                        var8_8 = Hpack.INSTANCE.getNAME_TO_FIRST_INDEX().get(var6_6);
                        if (var8_8 == null) break block20;
                        var9_9 = var8_8.intValue() + 1;
                        if (2 > var9_9 || 7 < var9_9) ** GOTO lbl-1000
                        if (Intrinsics.areEqual(Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var9_9 - 1].value, var7_7)) {
                            var2_2 = var9_9;
                        } else if (Intrinsics.areEqual(Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var9_9].value, var7_7)) {
                            var2_2 = var9_9++;
                        } else lbl-1000: // 2 sources:
                        {
                            var2_2 = var9_9;
                            var9_9 = -1;
                        }
                        break block21;
                    }
                    var9_9 = -1;
                    var2_2 = -1;
                }
                var10_10 = var9_9;
                var11_11 = var2_2;
                if (var9_9 == -1) {
                    var12_12 = this.nextHeaderIndex + 1;
                    var13_13 = this.dynamicTable.length;
                    do {
                        var10_10 = var9_9;
                        var11_11 = var2_2;
                        if (var12_12 >= var13_13) break;
                        var8_8 = this.dynamicTable[var12_12];
                        if (var8_8 == null) {
                            Intrinsics.throwNpe();
                        }
                        var10_10 = var2_2;
                        if (Intrinsics.areEqual(var8_8.name, var6_6)) {
                            var8_8 = this.dynamicTable[var12_12];
                            if (var8_8 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (Intrinsics.areEqual(var8_8.value, var7_7)) {
                                var9_9 = this.nextHeaderIndex;
                                var10_10 = Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length + (var12_12 - var9_9);
                                var11_11 = var2_2;
                                break;
                            }
                            var10_10 = var2_2;
                            if (var2_2 == -1) {
                                var10_10 = var12_12 - this.nextHeaderIndex + Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length;
                            }
                        }
                        ++var12_12;
                        var2_2 = var10_10;
                    } while (true);
                }
                if (var10_10 != -1) {
                    this.writeInt(var10_10, 127, 128);
                } else if (var11_11 == -1) {
                    this.out.writeByte(64);
                    this.writeByteString(var6_6);
                    this.writeByteString(var7_7);
                    this.insertIntoDynamicTable(var5_5);
                } else if (var6_6.startsWith(Header.PSEUDO_PREFIX) && Intrinsics.areEqual(Header.TARGET_AUTHORITY, var6_6) ^ true) {
                    this.writeInt(var11_11, 15, 0);
                    this.writeByteString(var7_7);
                } else {
                    this.writeInt(var11_11, 63, 64);
                    this.writeByteString(var7_7);
                    this.insertIntoDynamicTable(var5_5);
                }
                ++var4_4;
            }
        }

        public final void writeInt(int n, int n2, int n3) {
            if (n < n2) {
                this.out.writeByte(n | n3);
                return;
            }
            this.out.writeByte(n3 | n2);
            n -= n2;
            do {
                if (n < 128) {
                    this.out.writeByte(n);
                    return;
                }
                this.out.writeByte(128 | n & 127);
                n >>>= 7;
            } while (true);
        }
    }

}

