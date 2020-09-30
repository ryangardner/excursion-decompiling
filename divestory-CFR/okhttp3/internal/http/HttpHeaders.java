/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0005\n\u0000\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0004*\u00020\u0006\u001a\u001a\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0012H\u0002\u001a\u000e\u0010\u0013\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u000e\u0010\u0014\u001a\u0004\u0018\u00010\f*\u00020\u0010H\u0002\u001a\u001a\u0010\u0015\u001a\u00020\u000f*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\n\u001a\f\u0010\u001a\u001a\u00020\u0004*\u00020\u0010H\u0002\u001a\u0014\u0010\u001b\u001a\u00020\u0004*\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"QUOTED_STRING_DELIMITERS", "Lokio/ByteString;", "TOKEN_DELIMITERS", "hasBody", "", "response", "Lokhttp3/Response;", "parseChallenges", "", "Lokhttp3/Challenge;", "Lokhttp3/Headers;", "headerName", "", "promisesBody", "readChallengeHeader", "", "Lokio/Buffer;", "result", "", "readQuotedString", "readToken", "receiveHeaders", "Lokhttp3/CookieJar;", "url", "Lokhttp3/HttpUrl;", "headers", "skipCommasAndWhitespace", "startsWith", "prefix", "", "okhttp"}, k=2, mv={1, 1, 16})
public final class HttpHeaders {
    private static final ByteString QUOTED_STRING_DELIMITERS = ByteString.Companion.encodeUtf8("\"\\");
    private static final ByteString TOKEN_DELIMITERS = ByteString.Companion.encodeUtf8("\t ,=");

    @Deprecated(level=DeprecationLevel.ERROR, message="No longer supported", replaceWith=@ReplaceWith(expression="response.promisesBody()", imports={}))
    public static final boolean hasBody(Response response) {
        Intrinsics.checkParameterIsNotNull(response, "response");
        return HttpHeaders.promisesBody(response);
    }

    public static final List<Challenge> parseChallenges(Headers headers, String string2) {
        Intrinsics.checkParameterIsNotNull(headers, "$this$parseChallenges");
        Intrinsics.checkParameterIsNotNull(string2, "headerName");
        List list = new ArrayList();
        int n = headers.size();
        int n2 = 0;
        while (n2 < n) {
            if (StringsKt.equals(string2, headers.name(n2), true)) {
                Buffer buffer = new Buffer().writeUtf8(headers.value(n2));
                try {
                    HttpHeaders.readChallengeHeader(buffer, list);
                }
                catch (EOFException eOFException) {
                    Platform.Companion.get().log("Unable to parse challenge", 5, eOFException);
                }
            }
            ++n2;
        }
        return list;
    }

    public static final boolean promisesBody(Response response) {
        Intrinsics.checkParameterIsNotNull(response, "$this$promisesBody");
        if (Intrinsics.areEqual(response.request().method(), "HEAD")) {
            return false;
        }
        int n = response.code();
        if ((n < 100 || n >= 200) && n != 204 && n != 304) {
            return true;
        }
        if (Util.headersContentLength(response) != -1L) return true;
        if (!StringsKt.equals("chunked", Response.header$default(response, "Transfer-Encoding", null, 2, null), true)) return false;
        return true;
    }

    /*
     * Exception decompiling
     */
    private static final void readChallengeHeader(Buffer var0, List<Challenge> var1_1) throws EOFException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[UNCONDITIONALDOLOOP]], but top level block is 2[UNCONDITIONALDOLOOP]
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

    private static final String readQuotedString(Buffer buffer) throws EOFException {
        byte by;
        byte by2 = buffer.readByte();
        by2 = by2 == (by = (byte)34) ? (byte)1 : 0;
        if (by2 == 0) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
        Buffer buffer2 = new Buffer();
        long l;
        while ((l = buffer.indexOfElement(QUOTED_STRING_DELIMITERS)) != -1L) {
            if (buffer.getByte(l) == by) {
                buffer2.write(buffer, l);
                buffer.readByte();
                return buffer2.readUtf8();
            }
            if (buffer.size() == l + 1L) {
                return null;
            }
            buffer2.write(buffer, l);
            buffer.readByte();
            buffer2.write(buffer, 1L);
        }
        return null;
    }

    private static final String readToken(Buffer object) {
        long l;
        long l2 = l = ((Buffer)object).indexOfElement(TOKEN_DELIMITERS);
        if (l == -1L) {
            l2 = ((Buffer)object).size();
        }
        if (l2 == 0L) return null;
        return ((Buffer)object).readUtf8(l2);
    }

    public static final void receiveHeaders(CookieJar cookieJar, HttpUrl httpUrl, Headers iterable) {
        Intrinsics.checkParameterIsNotNull(cookieJar, "$this$receiveHeaders");
        Intrinsics.checkParameterIsNotNull(httpUrl, "url");
        Intrinsics.checkParameterIsNotNull(iterable, "headers");
        if (cookieJar == CookieJar.NO_COOKIES) {
            return;
        }
        if ((iterable = Cookie.Companion.parseAll(httpUrl, (Headers)iterable)).isEmpty()) {
            return;
        }
        cookieJar.saveFromResponse(httpUrl, (List<Cookie>)iterable);
    }

    private static final boolean skipCommasAndWhitespace(Buffer buffer) {
        boolean bl = false;
        while (!buffer.exhausted()) {
            byte by = buffer.getByte(0L);
            if (by != 9 && by != 32) {
                if (by != 44) {
                    return bl;
                }
                buffer.readByte();
                bl = true;
                continue;
            }
            buffer.readByte();
        }
        return bl;
    }

    private static final boolean startsWith(Buffer buffer, byte by) {
        if (buffer.exhausted()) return false;
        if (buffer.getByte(0L) != by) return false;
        return true;
    }
}

