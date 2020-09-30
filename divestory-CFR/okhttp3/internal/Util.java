/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.Util$asFactory
 *  okhttp3.internal.Util$threadFactory
 */
package okhttp3.internal;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Header;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u00b8\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u001a\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u0017\u001a'\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00112\u0012\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0!\"\u00020\"\u00a2\u0006\u0002\u0010#\u001a\u0017\u0010$\u001a\u00020\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0&H\u0086\b\u001a-\u0010'\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)2\u0012\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u0002H)0!\"\u0002H)H\u0007\u00a2\u0006\u0002\u0010+\u001a1\u0010,\u001a\u0004\u0018\u0001H)\"\u0004\b\u0000\u0010)2\u0006\u0010-\u001a\u00020\"2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H)0/2\u0006\u00100\u001a\u00020\u0011\u00a2\u0006\u0002\u00101\u001a\u0016\u00102\u001a\u0002032\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u000f\u001a\u001f\u00105\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u00112\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0&H\u0086\b\u001a%\u00106\u001a\u00020\u001b\"\u0004\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H7082\u0006\u00109\u001a\u0002H7H\u0000\u00a2\u0006\u0002\u0010:\u001a\u0015\u0010;\u001a\u00020\u0014*\u00020<2\u0006\u0010=\u001a\u00020\u0014H\u0086\u0004\u001a\u0015\u0010;\u001a\u00020\u0017*\u00020\u00142\u0006\u0010=\u001a\u00020\u0017H\u0086\u0004\u001a\u0015\u0010;\u001a\u00020\u0014*\u00020>2\u0006\u0010=\u001a\u00020\u0014H\u0086\u0004\u001a\n\u0010?\u001a\u00020@*\u00020A\u001a\r\u0010B\u001a\u00020\u001b*\u00020\"H\b\u001a\r\u0010C\u001a\u00020\u001b*\u00020\"H\b\u001a\n\u0010D\u001a\u00020\u000f*\u00020\u0011\u001a\u0012\u0010E\u001a\u00020\u000f*\u00020F2\u0006\u0010G\u001a\u00020F\u001a\n\u0010H\u001a\u00020\u001b*\u00020I\u001a\n\u0010H\u001a\u00020\u001b*\u00020J\u001a\n\u0010H\u001a\u00020\u001b*\u00020K\u001a#\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00110!*\b\u0012\u0004\u0012\u00020\u00110!2\u0006\u0010M\u001a\u00020\u0011\u00a2\u0006\u0002\u0010N\u001a&\u0010O\u001a\u00020\u0014*\u00020\u00112\u0006\u0010P\u001a\u00020Q2\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a&\u0010O\u001a\u00020\u0014*\u00020\u00112\u0006\u0010T\u001a\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u001a\u0010U\u001a\u00020\u000f*\u00020V2\u0006\u0010W\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0019\u001a8\u0010Y\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0Z2\u0017\u0010[\u001a\u0013\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020\u000f0\\\u00a2\u0006\u0002\b]H\u0086\b\u001a5\u0010^\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00110!2\u000e\u0010G\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010!2\u000e\u0010_\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110`\u00a2\u0006\u0002\u0010a\u001a\n\u0010b\u001a\u00020\u0017*\u00020c\u001a+\u0010d\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00110!2\u0006\u0010M\u001a\u00020\u00112\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00110`\u00a2\u0006\u0002\u0010e\u001a\n\u0010f\u001a\u00020\u0014*\u00020\u0011\u001a\u001e\u0010g\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u001e\u0010h\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u0014\u0010i\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u0014\u001a9\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00110!*\b\u0012\u0004\u0012\u00020\u00110!2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00110!2\u000e\u0010_\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110`\u00a2\u0006\u0002\u0010k\u001a\u0012\u0010l\u001a\u00020\u000f*\u00020m2\u0006\u0010n\u001a\u00020o\u001a\u0012\u0010p\u001a\u00020\u000f*\u00020K2\u0006\u0010q\u001a\u00020r\u001a\r\u0010s\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\r\u0010t\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\n\u0010u\u001a\u00020\u0014*\u00020Q\u001a\n\u0010v\u001a\u00020\u0011*\u00020K\u001a\u0012\u0010w\u001a\u00020x*\u00020r2\u0006\u0010y\u001a\u00020x\u001a\n\u0010z\u001a\u00020\u0014*\u00020r\u001a\u0012\u0010{\u001a\u00020\u0014*\u00020|2\u0006\u0010}\u001a\u00020<\u001a\u001a\u0010{\u001a\u00020\u000f*\u00020V2\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0019\u001a\u0010\u0010~\u001a\b\u0012\u0004\u0012\u000200(*\u00020\u0003\u001a\u0011\u0010\u0001\u001a\u00020\u0003*\b\u0012\u0004\u0012\u000200(\u001a\u000b\u0010\u0081\u0001\u001a\u00020\u0011*\u00020\u0014\u001a\u000b\u0010\u0081\u0001\u001a\u00020\u0011*\u00020\u0017\u001a\u0016\u0010\u0082\u0001\u001a\u00020\u0011*\u00020F2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u000f\u001a\u001d\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0(\u001a7\u0010\u0085\u0001\u001a\u0011\u0012\u0005\u0012\u0003H\u0087\u0001\u0012\u0005\u0012\u0003H\u0088\u00010\u0086\u0001\"\u0005\b\u0000\u0010\u0087\u0001\"\u0005\b\u0001\u0010\u0088\u0001*\u0011\u0012\u0005\u0012\u0003H\u0087\u0001\u0012\u0005\u0012\u0003H\u0088\u00010\u0086\u0001\u001a\u0014\u0010\u0089\u0001\u001a\u00020\u0017*\u00020\u00112\u0007\u0010\u008a\u0001\u001a\u00020\u0017\u001a\u0016\u0010\u008b\u0001\u001a\u00020\u0014*\u0004\u0018\u00010\u00112\u0007\u0010\u008a\u0001\u001a\u00020\u0014\u001a\u001f\u0010\u008c\u0001\u001a\u00020\u0011*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u000e\u0010\u008d\u0001\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a'\u0010\u008e\u0001\u001a\u00030\u008f\u0001*\b0\u0090\u0001j\u0003`\u0091\u00012\u0013\u0010\u0092\u0001\u001a\u000e\u0012\n\u0012\b0\u0090\u0001j\u0003`\u0091\u00010(\u001a\u0015\u0010\u0093\u0001\u001a\u00020\u001b*\u00030\u0094\u00012\u0007\u0010\u0095\u0001\u001a\u00020\u0014\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u000e\u001a\u00020\u000f8\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0010\u001a\u00020\u00118\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0011X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0096\u0001"}, d2={"EMPTY_BYTE_ARRAY", "", "EMPTY_HEADERS", "Lokhttp3/Headers;", "EMPTY_REQUEST", "Lokhttp3/RequestBody;", "EMPTY_RESPONSE", "Lokhttp3/ResponseBody;", "UNICODE_BOMS", "Lokio/Options;", "UTC", "Ljava/util/TimeZone;", "VERIFY_AS_IP_ADDRESS", "Lkotlin/text/Regex;", "assertionsEnabled", "", "okHttpName", "", "userAgent", "checkDuration", "", "name", "duration", "", "unit", "Ljava/util/concurrent/TimeUnit;", "checkOffsetAndCount", "", "arrayLength", "offset", "count", "format", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "ignoreIoExceptions", "block", "Lkotlin/Function0;", "immutableListOf", "", "T", "elements", "([Ljava/lang/Object;)Ljava/util/List;", "readFieldOrNull", "instance", "fieldType", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "daemon", "threadName", "addIfAbsent", "E", "", "element", "(Ljava/util/List;Ljava/lang/Object;)V", "and", "", "mask", "", "asFactory", "Lokhttp3/EventListener$Factory;", "Lokhttp3/EventListener;", "assertThreadDoesntHoldLock", "assertThreadHoldsLock", "canParseAsIpAddress", "canReuseConnectionFor", "Lokhttp3/HttpUrl;", "other", "closeQuietly", "Ljava/io/Closeable;", "Ljava/net/ServerSocket;", "Ljava/net/Socket;", "concat", "value", "([Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "delimiterOffset", "delimiter", "", "startIndex", "endIndex", "delimiters", "discard", "Lokio/Source;", "timeout", "timeUnit", "filterList", "", "predicate", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "hasIntersection", "comparator", "Ljava/util/Comparator;", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)Z", "headersContentLength", "Lokhttp3/Response;", "indexOf", "([Ljava/lang/String;Ljava/lang/String;Ljava/util/Comparator;)I", "indexOfControlOrNonAscii", "indexOfFirstNonAsciiWhitespace", "indexOfLastNonAsciiWhitespace", "indexOfNonWhitespace", "intersect", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)[Ljava/lang/String;", "isCivilized", "Lokhttp3/internal/io/FileSystem;", "file", "Ljava/io/File;", "isHealthy", "source", "Lokio/BufferedSource;", "notify", "notifyAll", "parseHexDigit", "peerName", "readBomAsCharset", "Ljava/nio/charset/Charset;", "default", "readMedium", "skipAll", "Lokio/Buffer;", "b", "toHeaderList", "Lokhttp3/internal/http2/Header;", "toHeaders", "toHexString", "toHostHeader", "includeDefaultPort", "toImmutableList", "toImmutableMap", "", "K", "V", "toLongOrDefault", "defaultValue", "toNonNegativeInt", "trimSubstring", "wait", "withSuppressed", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "suppressed", "writeMedium", "Lokio/BufferedSink;", "medium", "okhttp"}, k=2, mv={1, 1, 16})
public final class Util {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Headers EMPTY_HEADERS = Headers.Companion.of(new String[0]);
    public static final RequestBody EMPTY_REQUEST;
    public static final ResponseBody EMPTY_RESPONSE;
    private static final Options UNICODE_BOMS;
    public static final TimeZone UTC;
    private static final Regex VERIFY_AS_IP_ADDRESS;
    public static final boolean assertionsEnabled;
    public static final String okHttpName;
    public static final String userAgent = "okhttp/4.8.1";

    static {
        EMPTY_RESPONSE = ResponseBody.Companion.create$default(ResponseBody.Companion, EMPTY_BYTE_ARRAY, null, 1, null);
        EMPTY_REQUEST = RequestBody.Companion.create$default(RequestBody.Companion, EMPTY_BYTE_ARRAY, null, 0, 0, 7, null);
        UNICODE_BOMS = Options.Companion.of(ByteString.Companion.decodeHex("efbbbf"), ByteString.Companion.decodeHex("feff"), ByteString.Companion.decodeHex("fffe"), ByteString.Companion.decodeHex("0000ffff"), ByteString.Companion.decodeHex("ffff0000"));
        Object object = TimeZone.getTimeZone("GMT");
        if (object == null) {
            Intrinsics.throwNpe();
        }
        UTC = object;
        VERIFY_AS_IP_ADDRESS = new Regex("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
        assertionsEnabled = OkHttpClient.class.desiredAssertionStatus();
        object = OkHttpClient.class.getName();
        Intrinsics.checkExpressionValueIsNotNull(object, "OkHttpClient::class.java.name");
        okHttpName = StringsKt.removeSuffix(StringsKt.removePrefix((String)object, (CharSequence)"okhttp3."), (CharSequence)"Client");
    }

    public static final <E> void addIfAbsent(List<E> list, E e) {
        Intrinsics.checkParameterIsNotNull(list, "$this$addIfAbsent");
        if (list.contains(e)) return;
        list.add(e);
    }

    public static final int and(byte by, int n) {
        return by & n;
    }

    public static final int and(short s, int n) {
        return s & n;
    }

    public static final long and(int n, long l) {
        return (long)n & l;
    }

    public static final EventListener.Factory asFactory(EventListener eventListener) {
        Intrinsics.checkParameterIsNotNull(eventListener, "$this$asFactory");
        return new EventListener.Factory(eventListener){
            final /* synthetic */ EventListener $this_asFactory;
            {
                this.$this_asFactory = eventListener;
            }

            public EventListener create(okhttp3.Call call) {
                Intrinsics.checkParameterIsNotNull(call, "call");
                return this.$this_asFactory;
            }
        };
    }

    public static final void assertThreadDoesntHoldLock(Object object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$assertThreadDoesntHoldLock");
        if (!assertionsEnabled) return;
        if (!Thread.holdsLock(object)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thread ");
        Thread thread2 = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
        stringBuilder.append(thread2.getName());
        stringBuilder.append(" MUST NOT hold lock on ");
        stringBuilder.append(object);
        throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
    }

    public static final void assertThreadHoldsLock(Object object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$assertThreadHoldsLock");
        if (!assertionsEnabled) return;
        if (Thread.holdsLock(object)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thread ");
        Thread thread2 = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
        stringBuilder.append(thread2.getName());
        stringBuilder.append(" MUST hold lock on ");
        stringBuilder.append(object);
        throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
    }

    public static final boolean canParseAsIpAddress(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$canParseAsIpAddress");
        return VERIFY_AS_IP_ADDRESS.matches(string2);
    }

    public static final boolean canReuseConnectionFor(HttpUrl httpUrl, HttpUrl httpUrl2) {
        Intrinsics.checkParameterIsNotNull(httpUrl, "$this$canReuseConnectionFor");
        Intrinsics.checkParameterIsNotNull(httpUrl2, "other");
        if (!Intrinsics.areEqual(httpUrl.host(), httpUrl2.host())) return false;
        if (httpUrl.port() != httpUrl2.port()) return false;
        if (!Intrinsics.areEqual(httpUrl.scheme(), httpUrl2.scheme())) return false;
        return true;
    }

    public static final int checkDuration(String string2, long l, TimeUnit object) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        boolean bl = true;
        long l2 = l LCMP 0L;
        boolean bl2 = l2 >= 0;
        if (!bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" < 0");
            throw (Throwable)new IllegalStateException(((StringBuilder)object).toString().toString());
        }
        bl2 = object != null;
        if (!bl2) throw (Throwable)new IllegalStateException("unit == null".toString());
        bl2 = (l = ((TimeUnit)((Object)object)).toMillis(l)) <= (long)Integer.MAX_VALUE;
        if (!bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" too large.");
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        bl2 = bl;
        if (l == 0L) {
            bl2 = l2 <= 0 ? bl : false;
        }
        if (bl2) {
            return (int)l;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" too small.");
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public static final void checkOffsetAndCount(long l, long l2, long l3) {
        if ((l2 | l3) < 0L) throw (Throwable)new ArrayIndexOutOfBoundsException();
        if (l2 > l) throw (Throwable)new ArrayIndexOutOfBoundsException();
        if (l - l2 < l3) throw (Throwable)new ArrayIndexOutOfBoundsException();
    }

    public static final void closeQuietly(Closeable closeable) {
        Intrinsics.checkParameterIsNotNull(closeable, "$this$closeQuietly");
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw (Throwable)runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static final void closeQuietly(ServerSocket serverSocket) {
        Intrinsics.checkParameterIsNotNull(serverSocket, "$this$closeQuietly");
        try {
            serverSocket.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw (Throwable)runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static final void closeQuietly(Socket socket) {
        Intrinsics.checkParameterIsNotNull(socket, "$this$closeQuietly");
        try {
            socket.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw (Throwable)runtimeException;
        }
        catch (AssertionError assertionError) {
            throw (Throwable)((Object)assertionError);
        }
        catch (Exception exception) {
            return;
        }
    }

    public static final String[] concat(String[] arrstring, String string2) {
        Intrinsics.checkParameterIsNotNull(arrstring, "$this$concat");
        Intrinsics.checkParameterIsNotNull(string2, "value");
        arrstring = Arrays.copyOf(arrstring, arrstring.length + 1);
        Intrinsics.checkExpressionValueIsNotNull(arrstring, "java.util.Arrays.copyOf(this, newSize)");
        arrstring[ArraysKt.getLastIndex(arrstring)] = string2;
        if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
        return arrstring;
    }

    public static final int delimiterOffset(String string2, char c, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$delimiterOffset");
        while (n < n2) {
            if (string2.charAt(n) == c) {
                return n;
            }
            ++n;
        }
        return n2;
    }

    public static final int delimiterOffset(String string2, String string3, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$delimiterOffset");
        Intrinsics.checkParameterIsNotNull(string3, "delimiters");
        while (n < n2) {
            if (StringsKt.contains$default((CharSequence)string3, string2.charAt(n), false, 2, null)) {
                return n;
            }
            ++n;
        }
        return n2;
    }

    public static /* synthetic */ int delimiterOffset$default(String string2, char c, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return Util.delimiterOffset(string2, c, n, n2);
        n2 = string2.length();
        return Util.delimiterOffset(string2, c, n, n2);
    }

    public static /* synthetic */ int delimiterOffset$default(String string2, String string3, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return Util.delimiterOffset(string2, string3, n, n2);
        n2 = string2.length();
        return Util.delimiterOffset(string2, string3, n, n2);
    }

    public static final boolean discard(Source source2, int n, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull(source2, "$this$discard");
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "timeUnit");
        try {
            return Util.skipAll(source2, n, timeUnit);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static final <T> List<T> filterList(Iterable<? extends T> list, Function1<? super T, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(list, "$this$filterList");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        List list2 = CollectionsKt.emptyList();
        Iterator<T> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (!function1.invoke(t).booleanValue()) continue;
            list = list2;
            if (list2.isEmpty()) {
                list = new ArrayList();
            }
            if (list == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
            TypeIntrinsics.asMutableList(list).add(t);
            list2 = list;
        }
        return list2;
    }

    public static final String format(String string2, Object ... arrobject) {
        Intrinsics.checkParameterIsNotNull(string2, "format");
        Intrinsics.checkParameterIsNotNull(arrobject, "args");
        Object object = StringCompanionObject.INSTANCE;
        object = Locale.US;
        Intrinsics.checkExpressionValueIsNotNull(object, "Locale.US");
        arrobject = Arrays.copyOf(arrobject, arrobject.length);
        string2 = String.format((Locale)object, string2, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.String.format(locale, format, *args)");
        return string2;
    }

    public static final boolean hasIntersection(String[] arrstring, String[] arrstring2, Comparator<? super String> comparator) {
        Intrinsics.checkParameterIsNotNull(arrstring, "$this$hasIntersection");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int n = arrstring.length == 0 ? 1 : 0;
        if (n != 0) return false;
        if (arrstring2 == null) return false;
        if (arrstring2.length == 0) {
            return false;
        }
        n = 0;
        if (n != 0) {
            return false;
        }
        int n2 = arrstring.length;
        n = 0;
        while (n < n2) {
            String string2 = arrstring[n];
            int n3 = arrstring2.length;
            for (int i = 0; i < n3; ++i) {
                if (comparator.compare(string2, arrstring2[i]) != 0) continue;
                return true;
            }
            ++n;
        }
        return false;
    }

    public static final long headersContentLength(Response object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$headersContentLength");
        object = ((Response)object).headers().get("Content-Length");
        long l = -1L;
        if (object == null) return l;
        return Util.toLongOrDefault((String)object, -1L);
    }

    public static final void ignoreIoExceptions(Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(function0, "block");
        try {
            function0.invoke();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    @SafeVarargs
    public static final <T> List<T> immutableListOf(T ... object) {
        Intrinsics.checkParameterIsNotNull(object, "elements");
        object = (Object[])object.clone();
        object = Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(object, ((T[])object).length)));
        Intrinsics.checkExpressionValueIsNotNull(object, "Collections.unmodifiable\u2026sList(*elements.clone()))");
        return object;
    }

    public static final int indexOf(String[] arrstring, String string2, Comparator<String> comparator) {
        Intrinsics.checkParameterIsNotNull(arrstring, "$this$indexOf");
        Intrinsics.checkParameterIsNotNull(string2, "value");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            boolean bl = comparator.compare(arrstring[n2], string2) == 0;
            if (bl) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static final int indexOfControlOrNonAscii(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$indexOfControlOrNonAscii");
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c <= '\u001f') return n2;
            if (c >= '') {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static final int indexOfFirstNonAsciiWhitespace(String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$indexOfFirstNonAsciiWhitespace");
        while (n < n2) {
            char c = string2.charAt(n);
            if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                return n;
            }
            ++n;
        }
        return n2;
    }

    public static /* synthetic */ int indexOfFirstNonAsciiWhitespace$default(String string2, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return Util.indexOfFirstNonAsciiWhitespace(string2, n, n2);
        n2 = string2.length();
        return Util.indexOfFirstNonAsciiWhitespace(string2, n, n2);
    }

    public static final int indexOfLastNonAsciiWhitespace(String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$indexOfLastNonAsciiWhitespace");
        if (--n2 < n) return n;
        char c;
        while ((c = string2.charAt(n2)) == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ') {
            if (n2 == n) return n;
            --n2;
        }
        return n2 + 1;
    }

    public static /* synthetic */ int indexOfLastNonAsciiWhitespace$default(String string2, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return Util.indexOfLastNonAsciiWhitespace(string2, n, n2);
        n2 = string2.length();
        return Util.indexOfLastNonAsciiWhitespace(string2, n, n2);
    }

    public static final int indexOfNonWhitespace(String string2, int n) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$indexOfNonWhitespace");
        int n2 = string2.length();
        while (n < n2) {
            char c = string2.charAt(n);
            if (c != ' ' && c != '\t') {
                return n;
            }
            ++n;
        }
        return string2.length();
    }

    public static /* synthetic */ int indexOfNonWhitespace$default(String string2, int n, int n2, Object object) {
        if ((n2 & 1) == 0) return Util.indexOfNonWhitespace(string2, n);
        n = 0;
        return Util.indexOfNonWhitespace(string2, n);
    }

    public static final String[] intersect(String[] arrstring, String[] arrstring2, Comparator<? super String> comparator) {
        Intrinsics.checkParameterIsNotNull(arrstring, "$this$intersect");
        Intrinsics.checkParameterIsNotNull(arrstring2, "other");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        List list = new ArrayList();
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                arrstring = ((Collection)list).toArray(new String[0]);
                if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                return arrstring;
            }
            String string2 = arrstring[n2];
            int n3 = arrstring2.length;
            for (int i = 0; i < n3; ++i) {
                if (comparator.compare(string2, arrstring2[i]) != 0) continue;
                list.add(string2);
                break;
            }
            ++n2;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    public static final boolean isCivilized(FileSystem var0, File var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public static final boolean isHealthy(Socket socket, BufferedSource bufferedSource) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(socket, "$this$isHealthy");
        Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
        int n = socket.getSoTimeout();
        try {
            socket.setSoTimeout(1);
            bl = bufferedSource.exhausted();
        }
        catch (Throwable throwable) {
            try {
                socket.setSoTimeout(n);
                throw throwable;
            }
            catch (IOException iOException) {
                return false;
            }
            catch (SocketTimeoutException socketTimeoutException) {
                return true;
            }
        }
        socket.setSoTimeout(n);
        return bl ^ true;
    }

    public static final void notify(Object object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$notify");
        object.notify();
    }

    public static final void notifyAll(Object object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$notifyAll");
        object.notifyAll();
    }

    public static final int parseHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return (char)(c - 48);
        }
        int n = 97;
        if ('a' <= c) {
            if ('f' >= c) return (char)(c - n + 10);
        }
        n = 65;
        if ('A' > c) return (char)-1;
        if ('F' < c) return (char)-1;
        return (char)(c - n + 10);
    }

    public static final String peerName(Socket object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$peerName");
        object = ((Socket)object).getRemoteSocketAddress();
        if (!(object instanceof InetSocketAddress)) return object.toString();
        object = ((InetSocketAddress)object).getHostName();
        Intrinsics.checkExpressionValueIsNotNull(object, "address.hostName");
        return object;
    }

    public static final Charset readBomAsCharset(BufferedSource bufferedSource, Charset charset) throws IOException {
        Intrinsics.checkParameterIsNotNull(bufferedSource, "$this$readBomAsCharset");
        Intrinsics.checkParameterIsNotNull(charset, "default");
        int n = bufferedSource.select(UNICODE_BOMS);
        if (n == -1) return charset;
        if (n == 0) {
            charset = StandardCharsets.UTF_8;
            Intrinsics.checkExpressionValueIsNotNull(charset, "UTF_8");
            return charset;
        }
        if (n == 1) {
            charset = StandardCharsets.UTF_16BE;
            Intrinsics.checkExpressionValueIsNotNull(charset, "UTF_16BE");
            return charset;
        }
        if (n == 2) {
            charset = StandardCharsets.UTF_16LE;
            Intrinsics.checkExpressionValueIsNotNull(charset, "UTF_16LE");
            return charset;
        }
        if (n == 3) return Charsets.INSTANCE.UTF32_BE();
        if (n != 4) throw (Throwable)((Object)new AssertionError());
        return Charsets.INSTANCE.UTF32_LE();
    }

    public static final <T> T readFieldOrNull(Object object, Class<T> class_, String string2) {
        Intrinsics.checkParameterIsNotNull(object, "instance");
        Intrinsics.checkParameterIsNotNull(class_, "fieldType");
        Intrinsics.checkParameterIsNotNull(string2, "fieldName");
        Class<?> class_2 = object.getClass();
        do {
            boolean bl = Intrinsics.areEqual(class_2, Object.class);
            Object var5_6 = null;
            if (!(bl ^ true)) {
                if (!(Intrinsics.areEqual(string2, "delegate") ^ true)) return null;
                if ((object = Util.readFieldOrNull(object, Object.class, "delegate")) == null) return null;
                return Util.readFieldOrNull(object, class_, string2);
            }
            try {
                Object object2 = class_2.getDeclaredField(string2);
                Intrinsics.checkExpressionValueIsNotNull(object2, "field");
                ((Field)object2).setAccessible(true);
                object2 = ((Field)object2).get(object);
                if (!class_.isInstance(object2)) {
                    object = var5_6;
                    return (T)object;
                }
                var5_6 = class_.cast(object2);
                object = var5_6;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                class_2 = class_2.getSuperclass();
                Intrinsics.checkExpressionValueIsNotNull(class_2, "c.superclass");
                continue;
            }
            return (T)object;
            break;
        } while (true);
    }

    public static final int readMedium(BufferedSource bufferedSource) throws IOException {
        Intrinsics.checkParameterIsNotNull(bufferedSource, "$this$readMedium");
        int n = Util.and(bufferedSource.readByte(), 255);
        int n2 = Util.and(bufferedSource.readByte(), 255);
        return Util.and(bufferedSource.readByte(), 255) | (n << 16 | n2 << 8);
    }

    public static final int skipAll(Buffer buffer, byte by) {
        Intrinsics.checkParameterIsNotNull(buffer, "$this$skipAll");
        int n = 0;
        while (!buffer.exhausted()) {
            if (buffer.getByte(0L) != by) return n;
            ++n;
            buffer.readByte();
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static final boolean skipAll(Source var0, int var1_1, TimeUnit var2_2) throws IOException {
        block7 : {
            Intrinsics.checkParameterIsNotNull(var0, "$this$skipAll");
            Intrinsics.checkParameterIsNotNull(var2_2, "timeUnit");
            var3_5 = System.nanoTime();
            var5_6 = var0.timeout().hasDeadline() != false ? var0.timeout().deadlineNanoTime() - var3_5 : Long.MAX_VALUE;
            var0.timeout().deadlineNanoTime(Math.min(var5_6, var2_2.toNanos(var1_1)) + var3_5);
            try {
                var2_2 = new Buffer();
                while (var0.read((Buffer)var2_2, 8192L) != -1L) {
                    var2_2.clear();
                }
                var7_7 = true;
                var8_8 = true;
                if (var5_6 != Long.MAX_VALUE) break block7;
                var7_7 = var8_8;
lbl16: // 2 sources:
                do {
                    var0.timeout().clearDeadline();
                    return var7_7;
                    break;
                } while (true);
            }
            catch (Throwable var2_3) {
                if (var5_6 == Long.MAX_VALUE) {
                    var0.timeout().clearDeadline();
                    throw var2_3;
                }
                var0.timeout().deadlineNanoTime(var3_5 + var5_6);
                throw var2_3;
            }
            catch (InterruptedIOException var2_4) {
                var7_7 = false;
                var8_9 = false;
                if (var5_6 == Long.MAX_VALUE) {
                    var7_7 = var8_9;
                    ** continue;
                }
            }
        }
        var0.timeout().deadlineNanoTime(var3_5 + var5_6);
        return var7_7;
    }

    public static final ThreadFactory threadFactory(String string2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        return new ThreadFactory(string2, bl){
            final /* synthetic */ boolean $daemon;
            final /* synthetic */ String $name;
            {
                this.$name = string2;
                this.$daemon = bl;
            }

            public final Thread newThread(java.lang.Runnable runnable2) {
                runnable2 = new Thread(runnable2, this.$name);
                ((Thread)runnable2).setDaemon(this.$daemon);
                return runnable2;
            }
        };
    }

    public static final void threadName(String string2, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        Thread thread2 = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(thread2, "currentThread");
        String string3 = thread2.getName();
        thread2.setName(string2);
        try {
            function0.invoke();
            return;
        }
        finally {
            InlineMarker.finallyStart(1);
            thread2.setName(string3);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final List<Header> toHeaderList(Headers headers) {
        Intrinsics.checkParameterIsNotNull(headers, "$this$toHeaderList");
        Object object = RangesKt.until(0, headers.size());
        Collection collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(object, 10));
        object = object.iterator();
        while (object.hasNext()) {
            int n = ((IntIterator)object).nextInt();
            collection.add(new Header(headers.name(n), headers.value(n)));
        }
        return (List)collection;
    }

    public static final Headers toHeaders(List<Header> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toHeaders");
        Headers.Builder builder = new Headers.Builder();
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = (Header)object.next();
            ByteString byteString = ((Header)object2).component1();
            object2 = ((Header)object2).component2();
            builder.addLenient$okhttp(byteString.utf8(), ((ByteString)object2).utf8());
        }
        return builder.build();
    }

    public static final String toHexString(int n) {
        String string2 = Integer.toHexString(n);
        Intrinsics.checkExpressionValueIsNotNull(string2, "Integer.toHexString(this)");
        return string2;
    }

    public static final String toHexString(long l) {
        String string2 = Long.toHexString(l);
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Long.toHexString(this)");
        return string2;
    }

    public static final String toHostHeader(HttpUrl httpUrl, boolean bl) {
        CharSequence charSequence;
        CharSequence charSequence2;
        Intrinsics.checkParameterIsNotNull(httpUrl, "$this$toHostHeader");
        if (StringsKt.contains$default((CharSequence)httpUrl.host(), ":", false, 2, null)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append('[');
            ((StringBuilder)charSequence).append(httpUrl.host());
            ((StringBuilder)charSequence).append(']');
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = httpUrl.host();
        }
        if (!bl) {
            charSequence2 = charSequence;
            if (httpUrl.port() == HttpUrl.Companion.defaultPort(httpUrl.scheme())) return charSequence2;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(':');
        ((StringBuilder)charSequence2).append(httpUrl.port());
        return ((StringBuilder)charSequence2).toString();
    }

    public static /* synthetic */ String toHostHeader$default(HttpUrl httpUrl, boolean bl, int n, Object object) {
        if ((n & 1) == 0) return Util.toHostHeader(httpUrl, bl);
        bl = false;
        return Util.toHostHeader(httpUrl, bl);
    }

    public static final <T> List<T> toImmutableList(List<? extends T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$toImmutableList");
        list = Collections.unmodifiableList(CollectionsKt.toMutableList((Collection)list));
        Intrinsics.checkExpressionValueIsNotNull(list, "Collections.unmodifiableList(toMutableList())");
        return list;
    }

    public static final <K, V> Map<K, V> toImmutableMap(Map<K, ? extends V> map) {
        Intrinsics.checkParameterIsNotNull(map, "$this$toImmutableMap");
        if (map.isEmpty()) {
            return MapsKt.emptyMap();
        }
        map = Collections.unmodifiableMap((Map)new LinkedHashMap<K, V>(map));
        Intrinsics.checkExpressionValueIsNotNull(map, "Collections.unmodifiableMap(LinkedHashMap(this))");
        return map;
    }

    public static final long toLongOrDefault(String string2, long l) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toLongOrDefault");
        try {
            long l2 = Long.parseLong(string2);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    public static final int toNonNegativeInt(String string2, int n) {
        long l;
        if (string2 == null) return n;
        try {
            l = Long.parseLong(string2);
            n = Integer.MAX_VALUE;
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
        if (l > (long)Integer.MAX_VALUE) {
            return n;
        }
        if (l >= 0L) return (int)l;
        return 0;
    }

    public static final String trimSubstring(String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$trimSubstring");
        n = Util.indexOfFirstNonAsciiWhitespace(string2, n, n2);
        string2 = string2.substring(n, Util.indexOfLastNonAsciiWhitespace(string2, n, n2));
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    public static /* synthetic */ String trimSubstring$default(String string2, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return Util.trimSubstring(string2, n, n2);
        n2 = string2.length();
        return Util.trimSubstring(string2, n, n2);
    }

    public static final void wait(Object object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$wait");
        object.wait();
    }

    public static final Throwable withSuppressed(Exception exception, List<? extends Exception> object) {
        Intrinsics.checkParameterIsNotNull(exception, "$this$withSuppressed");
        Intrinsics.checkParameterIsNotNull(object, "suppressed");
        if (object.size() > 1) {
            System.out.println(object);
        }
        object = object.iterator();
        while (object.hasNext()) {
            exception.addSuppressed((Exception)object.next());
        }
        return exception;
    }

    public static final void writeMedium(BufferedSink bufferedSink, int n) throws IOException {
        Intrinsics.checkParameterIsNotNull(bufferedSink, "$this$writeMedium");
        bufferedSink.writeByte(n >>> 16 & 255);
        bufferedSink.writeByte(n >>> 8 & 255);
        bufferedSink.writeByte(n & 255);
    }
}

