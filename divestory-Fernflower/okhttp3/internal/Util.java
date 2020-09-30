package okhttp3.internal;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
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
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Header;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000¸\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u001a\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u0017\u001a'\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00112\u0012\u0010 \u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0!\"\u00020\"¢\u0006\u0002\u0010#\u001a\u0017\u0010$\u001a\u00020\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0&H\u0086\b\u001a-\u0010'\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)2\u0012\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u0002H)0!\"\u0002H)H\u0007¢\u0006\u0002\u0010+\u001a1\u0010,\u001a\u0004\u0018\u0001H)\"\u0004\b\u0000\u0010)2\u0006\u0010-\u001a\u00020\"2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H)0/2\u0006\u00100\u001a\u00020\u0011¢\u0006\u0002\u00101\u001a\u0016\u00102\u001a\u0002032\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u00104\u001a\u00020\u000f\u001a\u001f\u00105\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u00112\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001b0&H\u0086\b\u001a%\u00106\u001a\u00020\u001b\"\u0004\b\u0000\u00107*\b\u0012\u0004\u0012\u0002H7082\u0006\u00109\u001a\u0002H7H\u0000¢\u0006\u0002\u0010:\u001a\u0015\u0010;\u001a\u00020\u0014*\u00020<2\u0006\u0010=\u001a\u00020\u0014H\u0086\u0004\u001a\u0015\u0010;\u001a\u00020\u0017*\u00020\u00142\u0006\u0010=\u001a\u00020\u0017H\u0086\u0004\u001a\u0015\u0010;\u001a\u00020\u0014*\u00020>2\u0006\u0010=\u001a\u00020\u0014H\u0086\u0004\u001a\n\u0010?\u001a\u00020@*\u00020A\u001a\r\u0010B\u001a\u00020\u001b*\u00020\"H\u0080\b\u001a\r\u0010C\u001a\u00020\u001b*\u00020\"H\u0080\b\u001a\n\u0010D\u001a\u00020\u000f*\u00020\u0011\u001a\u0012\u0010E\u001a\u00020\u000f*\u00020F2\u0006\u0010G\u001a\u00020F\u001a\n\u0010H\u001a\u00020\u001b*\u00020I\u001a\n\u0010H\u001a\u00020\u001b*\u00020J\u001a\n\u0010H\u001a\u00020\u001b*\u00020K\u001a#\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00110!*\b\u0012\u0004\u0012\u00020\u00110!2\u0006\u0010M\u001a\u00020\u0011¢\u0006\u0002\u0010N\u001a&\u0010O\u001a\u00020\u0014*\u00020\u00112\u0006\u0010P\u001a\u00020Q2\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a&\u0010O\u001a\u00020\u0014*\u00020\u00112\u0006\u0010T\u001a\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u001a\u0010U\u001a\u00020\u000f*\u00020V2\u0006\u0010W\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0019\u001a8\u0010Y\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0Z2\u0017\u0010[\u001a\u0013\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020\u000f0\\¢\u0006\u0002\b]H\u0086\b\u001a5\u0010^\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u00110!2\u000e\u0010G\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010!2\u000e\u0010_\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110`¢\u0006\u0002\u0010a\u001a\n\u0010b\u001a\u00020\u0017*\u00020c\u001a+\u0010d\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00110!2\u0006\u0010M\u001a\u00020\u00112\f\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00110`¢\u0006\u0002\u0010e\u001a\n\u0010f\u001a\u00020\u0014*\u00020\u0011\u001a\u001e\u0010g\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u001e\u0010h\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u0014\u0010i\u001a\u00020\u0014*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u0014\u001a9\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00110!*\b\u0012\u0004\u0012\u00020\u00110!2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00110!2\u000e\u0010_\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00110`¢\u0006\u0002\u0010k\u001a\u0012\u0010l\u001a\u00020\u000f*\u00020m2\u0006\u0010n\u001a\u00020o\u001a\u0012\u0010p\u001a\u00020\u000f*\u00020K2\u0006\u0010q\u001a\u00020r\u001a\r\u0010s\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\r\u0010t\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a\n\u0010u\u001a\u00020\u0014*\u00020Q\u001a\n\u0010v\u001a\u00020\u0011*\u00020K\u001a\u0012\u0010w\u001a\u00020x*\u00020r2\u0006\u0010y\u001a\u00020x\u001a\n\u0010z\u001a\u00020\u0014*\u00020r\u001a\u0012\u0010{\u001a\u00020\u0014*\u00020|2\u0006\u0010}\u001a\u00020<\u001a\u001a\u0010{\u001a\u00020\u000f*\u00020V2\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0019\u001a\u0010\u0010~\u001a\b\u0012\u0004\u0012\u00020\u007f0(*\u00020\u0003\u001a\u0011\u0010\u0080\u0001\u001a\u00020\u0003*\b\u0012\u0004\u0012\u00020\u007f0(\u001a\u000b\u0010\u0081\u0001\u001a\u00020\u0011*\u00020\u0014\u001a\u000b\u0010\u0081\u0001\u001a\u00020\u0011*\u00020\u0017\u001a\u0016\u0010\u0082\u0001\u001a\u00020\u0011*\u00020F2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\u000f\u001a\u001d\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u0002H)0(\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0(\u001a7\u0010\u0085\u0001\u001a\u0011\u0012\u0005\u0012\u0003H\u0087\u0001\u0012\u0005\u0012\u0003H\u0088\u00010\u0086\u0001\"\u0005\b\u0000\u0010\u0087\u0001\"\u0005\b\u0001\u0010\u0088\u0001*\u0011\u0012\u0005\u0012\u0003H\u0087\u0001\u0012\u0005\u0012\u0003H\u0088\u00010\u0086\u0001\u001a\u0014\u0010\u0089\u0001\u001a\u00020\u0017*\u00020\u00112\u0007\u0010\u008a\u0001\u001a\u00020\u0017\u001a\u0016\u0010\u008b\u0001\u001a\u00020\u0014*\u0004\u0018\u00010\u00112\u0007\u0010\u008a\u0001\u001a\u00020\u0014\u001a\u001f\u0010\u008c\u0001\u001a\u00020\u0011*\u00020\u00112\b\b\u0002\u0010R\u001a\u00020\u00142\b\b\u0002\u0010S\u001a\u00020\u0014\u001a\u000e\u0010\u008d\u0001\u001a\u00020\u001b*\u00020\"H\u0086\b\u001a'\u0010\u008e\u0001\u001a\u00030\u008f\u0001*\b0\u0090\u0001j\u0003`\u0091\u00012\u0013\u0010\u0092\u0001\u001a\u000e\u0012\n\u0012\b0\u0090\u0001j\u0003`\u0091\u00010(\u001a\u0015\u0010\u0093\u0001\u001a\u00020\u001b*\u00030\u0094\u00012\u0007\u0010\u0095\u0001\u001a\u00020\u0014\"\u0010\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000e\u001a\u00020\u000f8\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0010\u001a\u00020\u00118\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0011X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0096\u0001"},
   d2 = {"EMPTY_BYTE_ARRAY", "", "EMPTY_HEADERS", "Lokhttp3/Headers;", "EMPTY_REQUEST", "Lokhttp3/RequestBody;", "EMPTY_RESPONSE", "Lokhttp3/ResponseBody;", "UNICODE_BOMS", "Lokio/Options;", "UTC", "Ljava/util/TimeZone;", "VERIFY_AS_IP_ADDRESS", "Lkotlin/text/Regex;", "assertionsEnabled", "", "okHttpName", "", "userAgent", "checkDuration", "", "name", "duration", "", "unit", "Ljava/util/concurrent/TimeUnit;", "checkOffsetAndCount", "", "arrayLength", "offset", "count", "format", "args", "", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "ignoreIoExceptions", "block", "Lkotlin/Function0;", "immutableListOf", "", "T", "elements", "([Ljava/lang/Object;)Ljava/util/List;", "readFieldOrNull", "instance", "fieldType", "Ljava/lang/Class;", "fieldName", "(Ljava/lang/Object;Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "daemon", "threadName", "addIfAbsent", "E", "", "element", "(Ljava/util/List;Ljava/lang/Object;)V", "and", "", "mask", "", "asFactory", "Lokhttp3/EventListener$Factory;", "Lokhttp3/EventListener;", "assertThreadDoesntHoldLock", "assertThreadHoldsLock", "canParseAsIpAddress", "canReuseConnectionFor", "Lokhttp3/HttpUrl;", "other", "closeQuietly", "Ljava/io/Closeable;", "Ljava/net/ServerSocket;", "Ljava/net/Socket;", "concat", "value", "([Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "delimiterOffset", "delimiter", "", "startIndex", "endIndex", "delimiters", "discard", "Lokio/Source;", "timeout", "timeUnit", "filterList", "", "predicate", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "hasIntersection", "comparator", "Ljava/util/Comparator;", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)Z", "headersContentLength", "Lokhttp3/Response;", "indexOf", "([Ljava/lang/String;Ljava/lang/String;Ljava/util/Comparator;)I", "indexOfControlOrNonAscii", "indexOfFirstNonAsciiWhitespace", "indexOfLastNonAsciiWhitespace", "indexOfNonWhitespace", "intersect", "([Ljava/lang/String;[Ljava/lang/String;Ljava/util/Comparator;)[Ljava/lang/String;", "isCivilized", "Lokhttp3/internal/io/FileSystem;", "file", "Ljava/io/File;", "isHealthy", "source", "Lokio/BufferedSource;", "notify", "notifyAll", "parseHexDigit", "peerName", "readBomAsCharset", "Ljava/nio/charset/Charset;", "default", "readMedium", "skipAll", "Lokio/Buffer;", "b", "toHeaderList", "Lokhttp3/internal/http2/Header;", "toHeaders", "toHexString", "toHostHeader", "includeDefaultPort", "toImmutableList", "toImmutableMap", "", "K", "V", "toLongOrDefault", "defaultValue", "toNonNegativeInt", "trimSubstring", "wait", "withSuppressed", "", "Ljava/lang/Exception;", "Lkotlin/Exception;", "suppressed", "writeMedium", "Lokio/BufferedSink;", "medium", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class Util {
   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   public static final Headers EMPTY_HEADERS;
   public static final RequestBody EMPTY_REQUEST;
   public static final ResponseBody EMPTY_RESPONSE;
   private static final Options UNICODE_BOMS;
   public static final TimeZone UTC;
   private static final Regex VERIFY_AS_IP_ADDRESS;
   public static final boolean assertionsEnabled;
   public static final String okHttpName;
   public static final String userAgent = "okhttp/4.8.1";

   static {
      EMPTY_HEADERS = Headers.Companion.of();
      EMPTY_RESPONSE = ResponseBody.Companion.create$default(ResponseBody.Companion, (byte[])EMPTY_BYTE_ARRAY, (MediaType)null, 1, (Object)null);
      EMPTY_REQUEST = RequestBody.Companion.create$default(RequestBody.Companion, (byte[])EMPTY_BYTE_ARRAY, (MediaType)null, 0, 0, 7, (Object)null);
      UNICODE_BOMS = Options.Companion.of(ByteString.Companion.decodeHex("efbbbf"), ByteString.Companion.decodeHex("feff"), ByteString.Companion.decodeHex("fffe"), ByteString.Companion.decodeHex("0000ffff"), ByteString.Companion.decodeHex("ffff0000"));
      TimeZone var0 = TimeZone.getTimeZone("GMT");
      if (var0 == null) {
         Intrinsics.throwNpe();
      }

      UTC = var0;
      VERIFY_AS_IP_ADDRESS = new Regex("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
      assertionsEnabled = OkHttpClient.class.desiredAssertionStatus();
      String var1 = OkHttpClient.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "OkHttpClient::class.java.name");
      okHttpName = StringsKt.removeSuffix(StringsKt.removePrefix(var1, (CharSequence)"okhttp3."), (CharSequence)"Client");
   }

   public static final <E> void addIfAbsent(List<E> var0, E var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$addIfAbsent");
      if (!var0.contains(var1)) {
         var0.add(var1);
      }

   }

   public static final int and(byte var0, int var1) {
      return var0 & var1;
   }

   public static final int and(short var0, int var1) {
      return var0 & var1;
   }

   public static final long and(int var0, long var1) {
      return (long)var0 & var1;
   }

   public static final EventListener.Factory asFactory(final EventListener var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asFactory");
      return (EventListener.Factory)(new EventListener.Factory() {
         public EventListener create(Call var1) {
            Intrinsics.checkParameterIsNotNull(var1, "call");
            return var0;
         }
      });
   }

   public static final void assertThreadDoesntHoldLock(Object var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$assertThreadDoesntHoldLock");
      if (assertionsEnabled && Thread.holdsLock(var0)) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Thread ");
         Thread var2 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
         var1.append(var2.getName());
         var1.append(" MUST NOT hold lock on ");
         var1.append(var0);
         throw (Throwable)(new AssertionError(var1.toString()));
      }
   }

   public static final void assertThreadHoldsLock(Object var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$assertThreadHoldsLock");
      if (assertionsEnabled && !Thread.holdsLock(var0)) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Thread ");
         Thread var2 = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
         var1.append(var2.getName());
         var1.append(" MUST hold lock on ");
         var1.append(var0);
         throw (Throwable)(new AssertionError(var1.toString()));
      }
   }

   public static final boolean canParseAsIpAddress(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$canParseAsIpAddress");
      return VERIFY_AS_IP_ADDRESS.matches((CharSequence)var0);
   }

   public static final boolean canReuseConnectionFor(HttpUrl var0, HttpUrl var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$canReuseConnectionFor");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      boolean var2;
      if (Intrinsics.areEqual((Object)var0.host(), (Object)var1.host()) && var0.port() == var1.port() && Intrinsics.areEqual((Object)var0.scheme(), (Object)var1.scheme())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final int checkDuration(String var0, long var1, TimeUnit var3) {
      Intrinsics.checkParameterIsNotNull(var0, "name");
      boolean var4 = true;
      long var8;
      int var5 = (var8 = var1 - 0L) == 0L ? 0 : (var8 < 0L ? -1 : 1);
      boolean var6;
      if (var5 >= 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      StringBuilder var7;
      if (var6) {
         if (var3 != null) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var1 = var3.toMillis(var1);
            if (var1 <= (long)Integer.MAX_VALUE) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               var6 = var4;
               if (var1 == 0L) {
                  if (var5 <= 0) {
                     var6 = var4;
                  } else {
                     var6 = false;
                  }
               }

               if (var6) {
                  return (int)var1;
               } else {
                  var7 = new StringBuilder();
                  var7.append(var0);
                  var7.append(" too small.");
                  throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
               }
            } else {
               var7 = new StringBuilder();
               var7.append(var0);
               var7.append(" too large.");
               throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
            }
         } else {
            throw (Throwable)(new IllegalStateException("unit == null".toString()));
         }
      } else {
         var7 = new StringBuilder();
         var7.append(var0);
         var7.append(" < 0");
         throw (Throwable)(new IllegalStateException(var7.toString().toString()));
      }
   }

   public static final void checkOffsetAndCount(long var0, long var2, long var4) {
      if ((var2 | var4) < 0L || var2 > var0 || var0 - var2 < var4) {
         throw (Throwable)(new ArrayIndexOutOfBoundsException());
      }
   }

   public static final void closeQuietly(Closeable var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$closeQuietly");

      try {
         var0.close();
      } catch (RuntimeException var1) {
         throw (Throwable)var1;
      } catch (Exception var2) {
      }

   }

   public static final void closeQuietly(ServerSocket var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$closeQuietly");

      try {
         var0.close();
      } catch (RuntimeException var1) {
         throw (Throwable)var1;
      } catch (Exception var2) {
      }

   }

   public static final void closeQuietly(Socket var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$closeQuietly");

      try {
         var0.close();
      } catch (AssertionError var1) {
         throw (Throwable)var1;
      } catch (RuntimeException var2) {
         throw (Throwable)var2;
      } catch (Exception var3) {
      }

   }

   public static final String[] concat(String[] var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$concat");
      Intrinsics.checkParameterIsNotNull(var1, "value");
      Object[] var2 = Arrays.copyOf(var0, var0.length + 1);
      Intrinsics.checkExpressionValueIsNotNull(var2, "java.util.Arrays.copyOf(this, newSize)");
      var0 = (String[])var2;
      var0[ArraysKt.getLastIndex(var0)] = var1;
      if (var0 != null) {
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
      }
   }

   public static final int delimiterOffset(String var0, char var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$delimiterOffset");

      while(var2 < var3) {
         if (var0.charAt(var2) == var1) {
            return var2;
         }

         ++var2;
      }

      return var3;
   }

   public static final int delimiterOffset(String var0, String var1, int var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$delimiterOffset");
      Intrinsics.checkParameterIsNotNull(var1, "delimiters");

      while(var2 < var3) {
         if (StringsKt.contains$default((CharSequence)var1, var0.charAt(var2), false, 2, (Object)null)) {
            return var2;
         }

         ++var2;
      }

      return var3;
   }

   // $FF: synthetic method
   public static int delimiterOffset$default(String var0, char var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.length();
      }

      return delimiterOffset(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   public static int delimiterOffset$default(String var0, String var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.length();
      }

      return delimiterOffset(var0, var1, var2, var3);
   }

   public static final boolean discard(Source var0, int var1, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$discard");
      Intrinsics.checkParameterIsNotNull(var2, "timeUnit");

      boolean var3;
      try {
         var3 = skipAll(var0, var1, var2);
      } catch (IOException var4) {
         var3 = false;
      }

      return var3;
   }

   public static final <T> List<T> filterList(Iterable<? extends T> var0, Function1<? super T, Boolean> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterList");
      Intrinsics.checkParameterIsNotNull(var1, "predicate");
      List var2 = CollectionsKt.emptyList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if ((Boolean)var1.invoke(var4)) {
            List var5 = var2;
            if (var2.isEmpty()) {
               var5 = (List)(new ArrayList());
            }

            if (var5 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
            }

            TypeIntrinsics.asMutableList(var5).add(var4);
            var2 = var5;
         }
      }

      return var2;
   }

   public static final String format(String var0, Object... var1) {
      Intrinsics.checkParameterIsNotNull(var0, "format");
      Intrinsics.checkParameterIsNotNull(var1, "args");
      StringCompanionObject var2 = StringCompanionObject.INSTANCE;
      Locale var3 = Locale.US;
      Intrinsics.checkExpressionValueIsNotNull(var3, "Locale.US");
      var1 = Arrays.copyOf(var1, var1.length);
      var0 = String.format(var3, var0, Arrays.copyOf(var1, var1.length));
      Intrinsics.checkExpressionValueIsNotNull(var0, "java.lang.String.format(locale, format, *args)");
      return var0;
   }

   public static final boolean hasIntersection(String[] var0, String[] var1, Comparator<? super String> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$hasIntersection");
      Intrinsics.checkParameterIsNotNull(var2, "comparator");
      boolean var3;
      if (var0.length == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3 && var1 != null) {
         if (var1.length == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (!var3) {
            int var4 = var0.length;

            for(int var8 = 0; var8 < var4; ++var8) {
               String var5 = var0[var8];
               int var6 = var1.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  if (var2.compare(var5, var1[var7]) == 0) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public static final long headersContentLength(Response var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$headersContentLength");
      String var3 = var0.headers().get("Content-Length");
      long var1 = -1L;
      if (var3 != null) {
         var1 = toLongOrDefault(var3, -1L);
      }

      return var1;
   }

   public static final void ignoreIoExceptions(Function0<Unit> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "block");

      try {
         var0.invoke();
      } catch (IOException var1) {
      }

   }

   @SafeVarargs
   public static final <T> List<T> immutableListOf(T... var0) {
      Intrinsics.checkParameterIsNotNull(var0, "elements");
      var0 = (Object[])var0.clone();
      List var1 = Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(var0, var0.length)));
      Intrinsics.checkExpressionValueIsNotNull(var1, "Collections.unmodifiable…sList(*elements.clone()))");
      return var1;
   }

   public static final int indexOf(String[] var0, String var1, Comparator<String> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOf");
      Intrinsics.checkParameterIsNotNull(var1, "value");
      Intrinsics.checkParameterIsNotNull(var2, "comparator");
      int var3 = var0.length;
      int var4 = 0;

      while(true) {
         if (var4 >= var3) {
            var4 = -1;
            break;
         }

         boolean var5;
         if (var2.compare(var0[var4], var1) == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            break;
         }

         ++var4;
      }

      return var4;
   }

   public static final int indexOfControlOrNonAscii(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfControlOrNonAscii");
      int var1 = var0.length();

      for(int var2 = 0; var2 < var1; ++var2) {
         char var3 = var0.charAt(var2);
         if (var3 <= 31 || var3 >= 127) {
            return var2;
         }
      }

      return -1;
   }

   public static final int indexOfFirstNonAsciiWhitespace(String var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfFirstNonAsciiWhitespace");

      while(var1 < var2) {
         char var3 = var0.charAt(var1);
         if (var3 != '\t' && var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != ' ') {
            return var1;
         }

         ++var1;
      }

      return var2;
   }

   // $FF: synthetic method
   public static int indexOfFirstNonAsciiWhitespace$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return indexOfFirstNonAsciiWhitespace(var0, var1, var2);
   }

   public static final int indexOfLastNonAsciiWhitespace(String var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfLastNonAsciiWhitespace");
      --var2;
      if (var2 >= var1) {
         while(true) {
            char var3 = var0.charAt(var2);
            if (var3 != '\t' && var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != ' ') {
               return var2 + 1;
            }

            if (var2 == var1) {
               break;
            }

            --var2;
         }
      }

      return var1;
   }

   // $FF: synthetic method
   public static int indexOfLastNonAsciiWhitespace$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return indexOfLastNonAsciiWhitespace(var0, var1, var2);
   }

   public static final int indexOfNonWhitespace(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfNonWhitespace");

      for(int var2 = var0.length(); var1 < var2; ++var1) {
         char var3 = var0.charAt(var1);
         if (var3 != ' ' && var3 != '\t') {
            return var1;
         }
      }

      return var0.length();
   }

   // $FF: synthetic method
   public static int indexOfNonWhitespace$default(String var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 0;
      }

      return indexOfNonWhitespace(var0, var1);
   }

   public static final String[] intersect(String[] var0, String[] var1, Comparator<? super String> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$intersect");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      Intrinsics.checkParameterIsNotNull(var2, "comparator");
      List var3 = (List)(new ArrayList());
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var0[var5];
         int var7 = var1.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            if (var2.compare(var6, var1[var8]) == 0) {
               var3.add(var6);
               break;
            }
         }
      }

      Object[] var9 = ((Collection)var3).toArray(new String[0]);
      if (var9 != null) {
         return (String[])var9;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }

   public static final boolean isCivilized(FileSystem param0, File param1) {
      // $FF: Couldn't be decompiled
   }

   public static final boolean isHealthy(Socket var0, BufferedSource var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$isHealthy");
      Intrinsics.checkParameterIsNotNull(var1, "source");

      boolean var10001;
      int var2;
      try {
         var2 = var0.getSoTimeout();
      } catch (SocketTimeoutException var14) {
         var10001 = false;
         return true;
      } catch (IOException var15) {
         var10001 = false;
         return false;
      }

      try {
         var0.setSoTimeout(1);
         boolean var3 = var1.exhausted();
      } finally {
         try {
            var0.setSoTimeout(var2);
         } catch (SocketTimeoutException var12) {
            var10001 = false;
            return true;
         } catch (IOException var13) {
            var10001 = false;
            return false;
         }
      }

   }

   public static final void notify(Object var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$notify");
      var0.notify();
   }

   public static final void notifyAll(Object var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$notifyAll");
      var0.notifyAll();
   }

   public static final int parseHexDigit(char var0) {
      int var2;
      if ('0' <= var0 && '9' >= var0) {
         var2 = var0 - 48;
      } else {
         byte var1 = 97;
         if ('a' > var0 || 'f' < var0) {
            var1 = 65;
            if ('A' > var0 || 'F' < var0) {
               var2 = -1;
               return var2;
            }
         }

         var2 = var0 - var1 + 10;
      }

      return var2;
   }

   public static final String peerName(Socket var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$peerName");
      SocketAddress var1 = var0.getRemoteSocketAddress();
      String var2;
      if (var1 instanceof InetSocketAddress) {
         var2 = ((InetSocketAddress)var1).getHostName();
         Intrinsics.checkExpressionValueIsNotNull(var2, "address.hostName");
      } else {
         var2 = var1.toString();
      }

      return var2;
   }

   public static final Charset readBomAsCharset(BufferedSource var0, Charset var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readBomAsCharset");
      Intrinsics.checkParameterIsNotNull(var1, "default");
      int var2 = var0.select(UNICODE_BOMS);
      if (var2 != -1) {
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        throw (Throwable)(new AssertionError());
                     }

                     var1 = Charsets.INSTANCE.UTF32_LE();
                  } else {
                     var1 = Charsets.INSTANCE.UTF32_BE();
                  }
               } else {
                  var1 = StandardCharsets.UTF_16LE;
                  Intrinsics.checkExpressionValueIsNotNull(var1, "UTF_16LE");
               }
            } else {
               var1 = StandardCharsets.UTF_16BE;
               Intrinsics.checkExpressionValueIsNotNull(var1, "UTF_16BE");
            }
         } else {
            var1 = StandardCharsets.UTF_8;
            Intrinsics.checkExpressionValueIsNotNull(var1, "UTF_8");
         }
      }

      return var1;
   }

   public static final <T> T readFieldOrNull(Object var0, Class<T> var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var0, "instance");
      Intrinsics.checkParameterIsNotNull(var1, "fieldType");
      Intrinsics.checkParameterIsNotNull(var2, "fieldName");
      Class var3 = var0.getClass();

      while(true) {
         boolean var4 = Intrinsics.areEqual((Object)var3, (Object)Object.class);
         Object var5 = null;
         if (!(var4 ^ true)) {
            if (Intrinsics.areEqual((Object)var2, (Object)"delegate") ^ true) {
               var0 = readFieldOrNull(var0, Object.class, "delegate");
               if (var0 != null) {
                  return readFieldOrNull(var0, var1, var2);
               }
            }

            return null;
         }

         label29: {
            try {
               Field var6 = var3.getDeclaredField(var2);
               Intrinsics.checkExpressionValueIsNotNull(var6, "field");
               var6.setAccessible(true);
               Object var8 = var6.get(var0);
               if (!var1.isInstance(var8)) {
                  break label29;
               }

               var5 = var1.cast(var8);
            } catch (NoSuchFieldException var7) {
               var3 = var3.getSuperclass();
               Intrinsics.checkExpressionValueIsNotNull(var3, "c.superclass");
               continue;
            }

            var0 = var5;
            break;
         }

         var0 = var5;
         break;
      }

      return var0;
   }

   public static final int readMedium(BufferedSource var0) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$readMedium");
      int var1 = and((byte)var0.readByte(), 255);
      int var2 = and((byte)var0.readByte(), 255);
      return and((byte)var0.readByte(), 255) | var1 << 16 | var2 << 8;
   }

   public static final int skipAll(Buffer var0, byte var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$skipAll");
      int var2 = 0;

      while(!var0.exhausted() && var0.getByte(0L) == var1) {
         ++var2;
         var0.readByte();
      }

      return var2;
   }

   public static final boolean skipAll(Source param0, int param1, TimeUnit param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static final ThreadFactory threadFactory(final String var0, final boolean var1) {
      Intrinsics.checkParameterIsNotNull(var0, "name");
      return (ThreadFactory)(new ThreadFactory() {
         public final Thread newThread(Runnable var1x) {
            Thread var2 = new Thread(var1x, var0);
            var2.setDaemon(var1);
            return var2;
         }
      });
   }

   public static final void threadName(String var0, Function0<Unit> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "name");
      Intrinsics.checkParameterIsNotNull(var1, "block");
      Thread var2 = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(var2, "currentThread");
      String var3 = var2.getName();
      var2.setName(var0);

      try {
         var1.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         var2.setName(var3);
         InlineMarker.finallyEnd(1);
      }

   }

   public static final List<Header> toHeaderList(Headers var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toHeaderList");
      Iterable var1 = (Iterable)RangesKt.until(0, var0.size());
      Collection var2 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10)));
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         int var3 = ((IntIterator)var4).nextInt();
         var2.add(new Header(var0.name(var3), var0.value(var3)));
      }

      return (List)var2;
   }

   public static final Headers toHeaders(List<Header> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toHeaders");
      Headers.Builder var1 = new Headers.Builder();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Header var2 = (Header)var4.next();
         ByteString var3 = var2.component1();
         ByteString var5 = var2.component2();
         var1.addLenient$okhttp(var3.utf8(), var5.utf8());
      }

      return var1.build();
   }

   public static final String toHexString(int var0) {
      String var1 = Integer.toHexString(var0);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Integer.toHexString(this)");
      return var1;
   }

   public static final String toHexString(long var0) {
      String var2 = Long.toHexString(var0);
      Intrinsics.checkExpressionValueIsNotNull(var2, "java.lang.Long.toHexString(this)");
      return var2;
   }

   public static final String toHostHeader(HttpUrl var0, boolean var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toHostHeader");
      String var4;
      if (StringsKt.contains$default((CharSequence)var0.host(), (CharSequence)":", false, 2, (Object)null)) {
         StringBuilder var2 = new StringBuilder();
         var2.append('[');
         var2.append(var0.host());
         var2.append(']');
         var4 = var2.toString();
      } else {
         var4 = var0.host();
      }

      String var3;
      if (!var1) {
         var3 = var4;
         if (var0.port() == HttpUrl.Companion.defaultPort(var0.scheme())) {
            return var3;
         }
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(var4);
      var5.append(':');
      var5.append(var0.port());
      var3 = var5.toString();
      return var3;
   }

   // $FF: synthetic method
   public static String toHostHeader$default(HttpUrl var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      return toHostHeader(var0, var1);
   }

   public static final <T> List<T> toImmutableList(List<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toImmutableList");
      var0 = Collections.unmodifiableList(CollectionsKt.toMutableList((Collection)var0));
      Intrinsics.checkExpressionValueIsNotNull(var0, "Collections.unmodifiableList(toMutableList())");
      return var0;
   }

   public static final <K, V> Map<K, V> toImmutableMap(Map<K, ? extends V> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toImmutableMap");
      if (var0.isEmpty()) {
         var0 = MapsKt.emptyMap();
      } else {
         var0 = Collections.unmodifiableMap((Map)(new LinkedHashMap(var0)));
         Intrinsics.checkExpressionValueIsNotNull(var0, "Collections.unmodifiableMap(LinkedHashMap(this))");
      }

      return var0;
   }

   public static final long toLongOrDefault(String var0, long var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toLongOrDefault");

      long var3;
      try {
         var3 = Long.parseLong(var0);
      } catch (NumberFormatException var5) {
         return var1;
      }

      var1 = var3;
      return var1;
   }

   public static final int toNonNegativeInt(String var0, int var1) {
      if (var0 != null) {
         long var2;
         try {
            var2 = Long.parseLong(var0);
         } catch (NumberFormatException var4) {
            return var1;
         }

         var1 = Integer.MAX_VALUE;
         if (var2 <= (long)Integer.MAX_VALUE) {
            if (var2 < 0L) {
               var1 = 0;
            } else {
               var1 = (int)var2;
            }
         }

         return var1;
      } else {
         return var1;
      }
   }

   public static final String trimSubstring(String var0, int var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$trimSubstring");
      var1 = indexOfFirstNonAsciiWhitespace(var0, var1, var2);
      var0 = var0.substring(var1, indexOfLastNonAsciiWhitespace(var0, var1, var2));
      Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return var0;
   }

   // $FF: synthetic method
   public static String trimSubstring$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return trimSubstring(var0, var1, var2);
   }

   public static final void wait(Object var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$wait");
      var0.wait();
   }

   public static final Throwable withSuppressed(Exception var0, List<? extends Exception> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$withSuppressed");
      Intrinsics.checkParameterIsNotNull(var1, "suppressed");
      if (var1.size() > 1) {
         System.out.println(var1);
      }

      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         var0.addSuppressed((Throwable)((Exception)var2.next()));
      }

      return (Throwable)var0;
   }

   public static final void writeMedium(BufferedSink var0, int var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var0, "$this$writeMedium");
      var0.writeByte(var1 >>> 16 & 255);
      var0.writeByte(var1 >>> 8 & 255);
      var0.writeByte(var1 & 255);
   }
}
