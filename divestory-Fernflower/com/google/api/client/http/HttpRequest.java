package com.google.api.client.http;

import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.Span;
import io.opencensus.trace.Tracer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public final class HttpRequest {
   public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
   public static final String USER_AGENT_SUFFIX;
   public static final String VERSION = getVersion();
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
   private boolean responseReturnRawInputStream;
   @Deprecated
   private boolean retryOnExecuteIOException = false;
   private Sleeper sleeper;
   private boolean suppressUserAgentSuffix;
   private boolean throwExceptionOnExecuteError = true;
   private final Tracer tracer;
   private final HttpTransport transport;
   private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
   private GenericUrl url;
   private boolean useRawRedirectUrls = false;
   private int writeTimeout = 0;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append("Google-HTTP-Java-Client/");
      var0.append(VERSION);
      var0.append(" (gzip)");
      USER_AGENT_SUFFIX = var0.toString();
   }

   HttpRequest(HttpTransport var1, String var2) {
      this.sleeper = Sleeper.DEFAULT;
      this.tracer = OpenCensusUtils.getTracer();
      this.responseReturnRawInputStream = false;
      this.transport = var1;
      this.setRequestMethod(var2);
   }

   private static void addSpanAttribute(Span var0, String var1, String var2) {
      if (var2 != null) {
         var0.putAttribute(var1, AttributeValue.stringAttributeValue(var2));
      }

   }

   private static String getVersion() {
      String var0 = "unknown-version";
      String var1 = var0;

      boolean var10001;
      InputStream var2;
      try {
         var2 = HttpRequest.class.getResourceAsStream("/google-http-client.properties");
      } catch (IOException var30) {
         var10001 = false;
         return var1;
      }

      String var3 = var0;
      if (var2 != null) {
         try {
            Properties var33 = new Properties();
            var33.load(var2);
            var3 = var33.getProperty("google-http-client.version");
         } catch (Throwable var29) {
            Throwable var32 = var29;

            try {
               throw var32;
            } finally {
               if (var2 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var26) {
                     Throwable var31 = var26;
                     var1 = var0;

                     label210:
                     try {
                        var32.addSuppressed(var31);
                        break label210;
                     } catch (IOException var25) {
                        var10001 = false;
                        return var1;
                     }
                  }
               }

            }
         }
      }

      var1 = var3;
      if (var2 != null) {
         var1 = var3;

         try {
            var2.close();
         } catch (IOException var28) {
            var10001 = false;
            return var1;
         }

         var1 = var3;
      }

      return var1;
   }

   public HttpResponse execute() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Future<HttpResponse> executeAsync() {
      return this.executeAsync(Executors.newFixedThreadPool(1, (new ThreadFactoryBuilder()).setDaemon(true).build()));
   }

   public Future<HttpResponse> executeAsync(Executor var1) {
      FutureTask var2 = new FutureTask(new Callable<HttpResponse>() {
         public HttpResponse call() throws Exception {
            return HttpRequest.this.execute();
         }
      });
      var1.execute(var2);
      return var2;
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

   public boolean handleRedirect(int var1, HttpHeaders var2) {
      String var4 = var2.getLocation();
      if (this.getFollowRedirects() && HttpStatusCodes.isRedirect(var1) && var4 != null) {
         this.setUrl(new GenericUrl(this.url.toURL(var4), this.useRawRedirectUrls));
         if (var1 == 303) {
            this.setRequestMethod("GET");
            this.setContent((HttpContent)null);
         }

         HttpHeaders var3 = this.headers;
         var4 = (String)null;
         var3.setAuthorization(var4);
         this.headers.setIfMatch(var4);
         this.headers.setIfNoneMatch(var4);
         this.headers.setIfModifiedSince(var4);
         this.headers.setIfUnmodifiedSince(var4);
         this.headers.setIfRange(var4);
         return true;
      } else {
         return false;
      }
   }

   public boolean isCurlLoggingEnabled() {
      return this.curlLoggingEnabled;
   }

   public boolean isLoggingEnabled() {
      return this.loggingEnabled;
   }

   @Deprecated
   public HttpRequest setBackOffPolicy(BackOffPolicy var1) {
      this.backOffPolicy = var1;
      return this;
   }

   public HttpRequest setConnectTimeout(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.connectTimeout = var1;
      return this;
   }

   public HttpRequest setContent(HttpContent var1) {
      this.content = var1;
      return this;
   }

   public HttpRequest setContentLoggingLimit(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "The content logging limit must be non-negative.");
      this.contentLoggingLimit = var1;
      return this;
   }

   public HttpRequest setCurlLoggingEnabled(boolean var1) {
      this.curlLoggingEnabled = var1;
      return this;
   }

   public HttpRequest setEncoding(HttpEncoding var1) {
      this.encoding = var1;
      return this;
   }

   public HttpRequest setFollowRedirects(boolean var1) {
      this.followRedirects = var1;
      return this;
   }

   public HttpRequest setHeaders(HttpHeaders var1) {
      this.headers = (HttpHeaders)Preconditions.checkNotNull(var1);
      return this;
   }

   public HttpRequest setIOExceptionHandler(HttpIOExceptionHandler var1) {
      this.ioExceptionHandler = var1;
      return this;
   }

   public HttpRequest setInterceptor(HttpExecuteInterceptor var1) {
      this.executeInterceptor = var1;
      return this;
   }

   public HttpRequest setLoggingEnabled(boolean var1) {
      this.loggingEnabled = var1;
      return this;
   }

   public HttpRequest setNumberOfRetries(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.numRetries = var1;
      return this;
   }

   public HttpRequest setParser(ObjectParser var1) {
      this.objectParser = var1;
      return this;
   }

   public HttpRequest setReadTimeout(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.readTimeout = var1;
      return this;
   }

   public HttpRequest setRequestMethod(String var1) {
      boolean var2;
      if (var1 != null && !HttpMediaType.matchesToken(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      Preconditions.checkArgument(var2);
      this.requestMethod = var1;
      return this;
   }

   public HttpRequest setResponseHeaders(HttpHeaders var1) {
      this.responseHeaders = (HttpHeaders)Preconditions.checkNotNull(var1);
      return this;
   }

   public HttpRequest setResponseInterceptor(HttpResponseInterceptor var1) {
      this.responseInterceptor = var1;
      return this;
   }

   public HttpRequest setResponseReturnRawInputStream(boolean var1) {
      this.responseReturnRawInputStream = var1;
      return this;
   }

   @Deprecated
   public HttpRequest setRetryOnExecuteIOException(boolean var1) {
      this.retryOnExecuteIOException = var1;
      return this;
   }

   public HttpRequest setSleeper(Sleeper var1) {
      this.sleeper = (Sleeper)Preconditions.checkNotNull(var1);
      return this;
   }

   public HttpRequest setSuppressUserAgentSuffix(boolean var1) {
      this.suppressUserAgentSuffix = var1;
      return this;
   }

   public HttpRequest setThrowExceptionOnExecuteError(boolean var1) {
      this.throwExceptionOnExecuteError = var1;
      return this;
   }

   public HttpRequest setUnsuccessfulResponseHandler(HttpUnsuccessfulResponseHandler var1) {
      this.unsuccessfulResponseHandler = var1;
      return this;
   }

   public HttpRequest setUrl(GenericUrl var1) {
      this.url = (GenericUrl)Preconditions.checkNotNull(var1);
      return this;
   }

   public HttpRequest setUseRawRedirectUrls(boolean var1) {
      this.useRawRedirectUrls = var1;
      return this;
   }

   public HttpRequest setWriteTimeout(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.writeTimeout = var1;
      return this;
   }
}
