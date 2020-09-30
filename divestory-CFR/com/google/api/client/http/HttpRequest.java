/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.BackOffPolicy;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpEncoding;
import com.google.api.client.http.HttpEncodingStreamingContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpResponseInterceptor;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.http.OpenCensusUtils;
import com.google.api.client.util.LoggingStreamingContent;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import com.google.api.client.util.StreamingContent;
import com.google.api.client.util.StringUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.opencensus.common.Scope;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.Tracer;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpRequest {
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    public static final String USER_AGENT_SUFFIX;
    public static final String VERSION;
    @Deprecated
    private BackOffPolicy backOffPolicy;
    private int connectTimeout = 20000;
    private HttpContent content;
    private int contentLoggingLimit = 16384;
    private boolean curlLoggingEnabled = true;
    private HttpEncoding encoding;
    private HttpExecuteInterceptor executeInterceptor;
    private boolean followRedirects = true;
    private HttpHeaders headers = new HttpHeaders();
    private HttpIOExceptionHandler ioExceptionHandler;
    private boolean loggingEnabled = true;
    private int numRetries = 10;
    private ObjectParser objectParser;
    private int readTimeout = 20000;
    private String requestMethod;
    private HttpHeaders responseHeaders = new HttpHeaders();
    private HttpResponseInterceptor responseInterceptor;
    private boolean responseReturnRawInputStream = false;
    @Deprecated
    private boolean retryOnExecuteIOException = false;
    private Sleeper sleeper = Sleeper.DEFAULT;
    private boolean suppressUserAgentSuffix;
    private boolean throwExceptionOnExecuteError = true;
    private final Tracer tracer = OpenCensusUtils.getTracer();
    private final HttpTransport transport;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    private GenericUrl url;
    private boolean useRawRedirectUrls = false;
    private int writeTimeout = 0;

    static {
        VERSION = HttpRequest.getVersion();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Google-HTTP-Java-Client/");
        stringBuilder.append(VERSION);
        stringBuilder.append(" (gzip)");
        USER_AGENT_SUFFIX = stringBuilder.toString();
    }

    HttpRequest(HttpTransport httpTransport, String string2) {
        this.transport = httpTransport;
        this.setRequestMethod(string2);
    }

    private static void addSpanAttribute(Span span, String string2, String string3) {
        if (string3 == null) return;
        span.putAttribute(string2, AttributeValue.stringAttributeValue(string3));
    }

    /*
     * Exception decompiling
     */
    private static String getVersion() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public HttpResponse execute() throws IOException {
        var1_1 = this;
        var2_4 = var1_1.numRetries >= 0;
        Preconditions.checkArgument(var2_4);
        var3_5 = var1_1.numRetries;
        var4_6 = var1_1.backOffPolicy;
        if (var4_6 != null) {
            var4_6.reset();
        }
        Preconditions.checkNotNull(var1_1.requestMethod);
        Preconditions.checkNotNull(var1_1.url);
        var1_1 = var1_1.tracer.spanBuilder(OpenCensusUtils.SPAN_NAME_HTTP_REQUEST_EXECUTE).setRecordEvents(OpenCensusUtils.isRecordEvent()).startSpan();
        var4_6 = null;
        do {
            block45 : {
                block44 : {
                    block48 : {
                        block47 : {
                            block46 : {
                                var5_9 = this;
                                var6_11 = new StringBuilder();
                                var6_11.append("retry #");
                                var6_11.append(var5_9.numRetries - var3_5);
                                var1_1.addAnnotation(var6_11.toString());
                                if (var4_6 != null) {
                                    var4_6.ignore();
                                }
                                if ((var4_6 = var5_9.executeInterceptor) != null) {
                                    var4_6.intercept((HttpRequest)var5_9);
                                }
                                var7_13 = var5_9.url.build();
                                HttpRequest.addSpanAttribute((Span)var1_1, "http.method", var5_9.requestMethod);
                                HttpRequest.addSpanAttribute((Span)var1_1, "http.host", var5_9.url.getHost());
                                HttpRequest.addSpanAttribute((Span)var1_1, "http.path", var5_9.url.getRawPath());
                                HttpRequest.addSpanAttribute((Span)var1_1, "http.url", var7_13);
                                var8_14 = var5_9.transport.buildRequest(var5_9.requestMethod, var7_13);
                                var9_15 = HttpTransport.LOGGER;
                                var10_16 = var5_9.loggingEnabled != false && var9_15.isLoggable(Level.CONFIG) != false ? 1 : 0;
                                if (var10_16 == 0) break block46;
                                var11_17 = new StringBuilder();
                                var11_17.append("-------------- REQUEST  --------------");
                                var11_17.append(StringUtils.LINE_SEPARATOR);
                                var11_17.append(var5_9.requestMethod);
                                var11_17.append(' ');
                                var11_17.append(var7_13);
                                var11_17.append(StringUtils.LINE_SEPARATOR);
                                var4_6 = var11_17;
                                if (!var5_9.curlLoggingEnabled) break block47;
                                var4_6 = new StringBuilder("curl -v --compressed");
                                var12_18 = var11_17;
                                var6_11 = var4_6;
                                if (!var5_9.requestMethod.equals("GET")) {
                                    var4_6.append(" -X ");
                                    var4_6.append(var5_9.requestMethod);
                                    var12_18 = var11_17;
                                    var6_11 = var4_6;
                                }
                                break block48;
                            }
                            var4_6 = null;
                        }
                        var6_11 = null;
                        var12_18 = var4_6;
                    }
                    var4_6 = var5_9.headers.getUserAgent();
                    if (!var5_9.suppressUserAgentSuffix) {
                        if (var4_6 == null) {
                            var5_9.headers.setUserAgent(HttpRequest.USER_AGENT_SUFFIX);
                            HttpRequest.addSpanAttribute((Span)var1_1, "http.user_agent", HttpRequest.USER_AGENT_SUFFIX);
                        } else {
                            var11_17 = new StringBuilder();
                            var11_17.append((String)var4_6);
                            var11_17.append(" ");
                            var11_17.append(HttpRequest.USER_AGENT_SUFFIX);
                            var11_17 = var11_17.toString();
                            var5_9.headers.setUserAgent((String)var11_17);
                            HttpRequest.addSpanAttribute((Span)var1_1, "http.user_agent", (String)var11_17);
                        }
                    }
                    OpenCensusUtils.propagateTracingContext((Span)var1_1, var5_9.headers);
                    HttpHeaders.serializeHeaders(var5_9.headers, (StringBuilder)var12_18, (StringBuilder)var6_11, var9_15, var8_14);
                    if (!var5_9.suppressUserAgentSuffix) {
                        var5_9.headers.setUserAgent((String)var4_6);
                    }
                    var13_19 = (var4_6 = var5_9.content) == null || var4_6.retrySupported();
                    if (var4_6 != null) {
                        var14_20 = var5_9.content.getType();
                        if (var10_16 != 0) {
                            var4_6 = new LoggingStreamingContent((StreamingContent)var4_6, HttpTransport.LOGGER, Level.CONFIG, var5_9.contentLoggingLimit);
                        }
                        if ((var11_17 = var5_9.encoding) == null) {
                            var15_21 = var5_9.content.getLength();
                            var11_17 = null;
                        } else {
                            var11_17 = var11_17.getName();
                            var4_6 = new HttpEncodingStreamingContent((StreamingContent)var4_6, var5_9.encoding);
                            var15_21 = -1L;
                        }
                        if (var10_16 != 0) {
                            if (var14_20 != null) {
                                var5_9 = new StringBuilder();
                                var5_9.append("Content-Type: ");
                                var5_9.append(var14_20);
                                var17_22 = var5_9.toString();
                                var12_18.append(var17_22);
                                var12_18.append(StringUtils.LINE_SEPARATOR);
                                if (var6_11 != null) {
                                    var5_9 = new StringBuilder();
                                    var5_9.append(" -H '");
                                    var5_9.append(var17_22);
                                    var5_9.append("'");
                                    var6_11.append(var5_9.toString());
                                }
                            }
                            if (var11_17 != null) {
                                var5_9 = new StringBuilder();
                                var5_9.append("Content-Encoding: ");
                                var5_9.append((String)var11_17);
                                var17_22 = var5_9.toString();
                                var12_18.append(var17_22);
                                var12_18.append(StringUtils.LINE_SEPARATOR);
                                if (var6_11 != null) {
                                    var5_9 = new StringBuilder();
                                    var5_9.append(" -H '");
                                    var5_9.append(var17_22);
                                    var5_9.append("'");
                                    var6_11.append(var5_9.toString());
                                }
                            }
                            if (var15_21 >= 0L) {
                                var5_9 = new StringBuilder();
                                var5_9.append("Content-Length: ");
                                var5_9.append(var15_21);
                                var12_18.append(var5_9.toString());
                                var12_18.append(StringUtils.LINE_SEPARATOR);
                            }
                        }
                        if (var6_11 != null) {
                            var6_11.append(" -d '@-'");
                        }
                        var8_14.setContentType(var14_20);
                        var8_14.setContentEncoding((String)var11_17);
                        var8_14.setContentLength(var15_21);
                        var8_14.setStreamingContent((StreamingContent)var4_6);
                        var11_17 = var4_6;
                    } else {
                        var11_17 = var4_6;
                    }
                    var18_23 = var3_5;
                    var4_6 = var1_1;
                    if (var10_16 != 0) {
                        var9_15.config(var12_18.toString());
                        if (var6_11 != null) {
                            var6_11.append(" -- '");
                            var6_11.append(var7_13.replaceAll("'", "'\"'\"'"));
                            var6_11.append("'");
                            if (var11_17 != null) {
                                var6_11.append(" << $$$");
                            }
                            var9_15.config(var6_11.toString());
                        }
                    }
                    var19_24 = var13_19 != false && var18_23 > 0;
                    var11_17 = this;
                    var8_14.setTimeout(var11_17.connectTimeout, var11_17.readTimeout);
                    var8_14.setWriteTimeout(var11_17.writeTimeout);
                    var12_18 = var11_17.tracer.withSpan((Span)var4_6);
                    OpenCensusUtils.recordSentMessageEvent((Span)var4_6, var8_14.getContentLength());
                    var6_11 = var8_14.execute();
                    if (var6_11 == null) break block44;
                    OpenCensusUtils.recordReceivedMessageEvent((Span)var4_6, var6_11.getContentLength());
                }
                try {
                    var1_1 = new HttpResponse((HttpRequest)var11_17, (LowLevelHttpResponse)var6_11);
                    var12_18.close();
                    var6_11 = null;
                }
                catch (Throwable var1_2) {
                    try {
                        var6_11 = var6_11.getContent();
                        if (var6_11 == null) throw var1_2;
                        var6_11.close();
                        throw var1_2;
                    }
                    catch (Throwable var1_3) {
                        ** GOTO lbl238
                    }
                    catch (IOException var6_12) {
                        if (!(var11_17.retryOnExecuteIOException || var11_17.ioExceptionHandler != null && var11_17.ioExceptionHandler.handleIOException((HttpRequest)var11_17, var19_24))) {
                            var4_6.end(OpenCensusUtils.getEndSpanOptions(null));
                            throw var6_12;
                        }
                        if (var10_16 != 0) {
                            var9_15.log(Level.WARNING, "exception thrown while executing request", var6_12);
                        }
                        var12_18.close();
                        var1_1 = null;
                    }
                }
                var12_18 = null;
                if (var1_1 == null) ** GOTO lbl234
                try {
                    block50 : {
                        block51 : {
                            block49 : {
                                if (var1_1.isSuccessStatusCode()) break block49;
                                var2_4 = var11_17.unsuccessfulResponseHandler != null ? var11_17.unsuccessfulResponseHandler.handleResponse((HttpRequest)var11_17, (HttpResponse)var1_1, var19_24) : false;
                                var20_25 = var2_4;
                                if (var2_4) break block50;
                                if (var11_17.handleRedirect(var1_1.getStatusCode(), var1_1.getHeaders())) break block51;
                                var20_25 = var2_4;
                                if (!var19_24) break block50;
                                var20_25 = var2_4;
                                if (var11_17.backOffPolicy == null) break block50;
                                var20_25 = var2_4;
                                if (!var11_17.backOffPolicy.isBackOffRequired(var1_1.getStatusCode())) break block50;
                                var15_21 = var11_17.backOffPolicy.getNextBackOffMillis();
                                var20_25 = var2_4;
                                if (var15_21 == -1L) break block50;
                                try {
                                    var11_17.sleeper.sleep(var15_21);
                                }
                                catch (InterruptedException var5_10) {}
                            }
                            var3_5 = var1_1 == null ? 1 : 0;
                            var3_5 = var19_24 & var3_5;
                            break block45;
lbl238: // 1 sources:
                            var12_18.close();
                            throw var1_3;
                        }
                        var20_25 = true;
                    }
                    var3_5 = var10_16 = var19_24 & var20_25;
                    if (var10_16 != 0) {
                        var1_1.ignore();
                        var3_5 = var10_16;
                    }
                }
                catch (Throwable var4_7) {
                    if (var1_1 == null) throw var4_7;
                    var1_1.disconnect();
                    throw var4_7;
                }
            }
            var10_16 = var18_23 - 1;
            if (var3_5 == 0) {
                if (var1_1 != null) {
                    var12_18 = var1_1.getStatusCode();
                }
                var4_6.end(OpenCensusUtils.getEndSpanOptions((Integer)var12_18));
                if (var1_1 == null) throw var6_11;
                var4_6 = var11_17.responseInterceptor;
                if (var4_6 != null) {
                    var4_6.interceptResponse((HttpResponse)var1_1);
                }
                if (var11_17.throwExceptionOnExecuteError == false) return var1_1;
                if (var1_1.isSuccessStatusCode()) {
                    return var1_1;
                }
                try {
                    var4_6 = new HttpResponseException((HttpResponse)var1_1);
                    throw var4_6;
                }
                catch (Throwable var4_8) {
                    var1_1.disconnect();
                    throw var4_8;
                }
            }
            var6_11 = var1_1;
            var1_1 = var4_6;
            var4_6 = var6_11;
            var3_5 = var10_16;
        } while (true);
    }

    public Future<HttpResponse> executeAsync() {
        return this.executeAsync(Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setDaemon(true).build()));
    }

    public Future<HttpResponse> executeAsync(Executor executor) {
        FutureTask<HttpResponse> futureTask = new FutureTask<HttpResponse>(new Callable<HttpResponse>(){

            @Override
            public HttpResponse call() throws Exception {
                return HttpRequest.this.execute();
            }
        });
        executor.execute(futureTask);
        return futureTask;
    }

    @Deprecated
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public HttpContent getContent() {
        return this.content;
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public HttpEncoding getEncoding() {
        return this.encoding;
    }

    public boolean getFollowRedirects() {
        return this.followRedirects;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }

    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }

    public int getNumberOfRetries() {
        return this.numRetries;
    }

    public final ObjectParser getParser() {
        return this.objectParser;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }

    public boolean getResponseReturnRawInputStream() {
        return this.responseReturnRawInputStream;
    }

    @Deprecated
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }

    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }

    public HttpTransport getTransport() {
        return this.transport;
    }

    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }

    public GenericUrl getUrl() {
        return this.url;
    }

    public boolean getUseRawRedirectUrls() {
        return this.useRawRedirectUrls;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public boolean handleRedirect(int n, HttpHeaders object) {
        object = ((HttpHeaders)object).getLocation();
        if (!this.getFollowRedirects()) return false;
        if (!HttpStatusCodes.isRedirect(n)) return false;
        if (object == null) return false;
        this.setUrl(new GenericUrl(this.url.toURL((String)object), this.useRawRedirectUrls));
        if (n == 303) {
            this.setRequestMethod("GET");
            this.setContent(null);
        }
        HttpHeaders httpHeaders = this.headers;
        object = null;
        httpHeaders.setAuthorization((String)object);
        this.headers.setIfMatch((String)object);
        this.headers.setIfNoneMatch((String)object);
        this.headers.setIfModifiedSince((String)object);
        this.headers.setIfUnmodifiedSince((String)object);
        this.headers.setIfRange((String)object);
        return true;
    }

    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    @Deprecated
    public HttpRequest setBackOffPolicy(BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
        return this;
    }

    public HttpRequest setConnectTimeout(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        this.connectTimeout = n;
        return this;
    }

    public HttpRequest setContent(HttpContent httpContent) {
        this.content = httpContent;
        return this;
    }

    public HttpRequest setContentLoggingLimit(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = n;
        return this;
    }

    public HttpRequest setCurlLoggingEnabled(boolean bl) {
        this.curlLoggingEnabled = bl;
        return this;
    }

    public HttpRequest setEncoding(HttpEncoding httpEncoding) {
        this.encoding = httpEncoding;
        return this;
    }

    public HttpRequest setFollowRedirects(boolean bl) {
        this.followRedirects = bl;
        return this;
    }

    public HttpRequest setHeaders(HttpHeaders httpHeaders) {
        this.headers = Preconditions.checkNotNull(httpHeaders);
        return this;
    }

    public HttpRequest setIOExceptionHandler(HttpIOExceptionHandler httpIOExceptionHandler) {
        this.ioExceptionHandler = httpIOExceptionHandler;
        return this;
    }

    public HttpRequest setInterceptor(HttpExecuteInterceptor httpExecuteInterceptor) {
        this.executeInterceptor = httpExecuteInterceptor;
        return this;
    }

    public HttpRequest setLoggingEnabled(boolean bl) {
        this.loggingEnabled = bl;
        return this;
    }

    public HttpRequest setNumberOfRetries(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        this.numRetries = n;
        return this;
    }

    public HttpRequest setParser(ObjectParser objectParser) {
        this.objectParser = objectParser;
        return this;
    }

    public HttpRequest setReadTimeout(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        this.readTimeout = n;
        return this;
    }

    public HttpRequest setRequestMethod(String string2) {
        boolean bl = string2 == null || HttpMediaType.matchesToken(string2);
        Preconditions.checkArgument(bl);
        this.requestMethod = string2;
        return this;
    }

    public HttpRequest setResponseHeaders(HttpHeaders httpHeaders) {
        this.responseHeaders = Preconditions.checkNotNull(httpHeaders);
        return this;
    }

    public HttpRequest setResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        this.responseInterceptor = httpResponseInterceptor;
        return this;
    }

    public HttpRequest setResponseReturnRawInputStream(boolean bl) {
        this.responseReturnRawInputStream = bl;
        return this;
    }

    @Deprecated
    public HttpRequest setRetryOnExecuteIOException(boolean bl) {
        this.retryOnExecuteIOException = bl;
        return this;
    }

    public HttpRequest setSleeper(Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }

    public HttpRequest setSuppressUserAgentSuffix(boolean bl) {
        this.suppressUserAgentSuffix = bl;
        return this;
    }

    public HttpRequest setThrowExceptionOnExecuteError(boolean bl) {
        this.throwExceptionOnExecuteError = bl;
        return this;
    }

    public HttpRequest setUnsuccessfulResponseHandler(HttpUnsuccessfulResponseHandler httpUnsuccessfulResponseHandler) {
        this.unsuccessfulResponseHandler = httpUnsuccessfulResponseHandler;
        return this;
    }

    public HttpRequest setUrl(GenericUrl genericUrl) {
        this.url = Preconditions.checkNotNull(genericUrl);
        return this;
    }

    public HttpRequest setUseRawRedirectUrls(boolean bl) {
        this.useRawRedirectUrls = bl;
        return this;
    }

    public HttpRequest setWriteTimeout(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        this.writeTimeout = n;
        return this;
    }

}

