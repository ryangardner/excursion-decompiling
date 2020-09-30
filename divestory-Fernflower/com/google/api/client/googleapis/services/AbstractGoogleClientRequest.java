package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GZipEncoding;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpResponseInterceptor;
import com.google.api.client.http.UriTemplate;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.common.base.StandardSystemProperty;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractGoogleClientRequest<T> extends GenericData {
   private static final String API_VERSION_HEADER = "X-Goog-Api-Client";
   public static final String USER_AGENT_SUFFIX = "Google-API-Java-Client";
   private final AbstractGoogleClient abstractGoogleClient;
   private boolean disableGZipContent;
   private MediaHttpDownloader downloader;
   private final HttpContent httpContent;
   private HttpHeaders lastResponseHeaders;
   private int lastStatusCode = -1;
   private String lastStatusMessage;
   private HttpHeaders requestHeaders = new HttpHeaders();
   private final String requestMethod;
   private Class<T> responseClass;
   private boolean returnRawInputStream;
   private MediaHttpUploader uploader;
   private final String uriTemplate;

   protected AbstractGoogleClientRequest(AbstractGoogleClient var1, String var2, String var3, HttpContent var4, Class<T> var5) {
      this.responseClass = (Class)Preconditions.checkNotNull(var5);
      this.abstractGoogleClient = (AbstractGoogleClient)Preconditions.checkNotNull(var1);
      this.requestMethod = (String)Preconditions.checkNotNull(var2);
      this.uriTemplate = (String)Preconditions.checkNotNull(var3);
      this.httpContent = var4;
      String var6 = var1.getApplicationName();
      HttpHeaders var8;
      if (var6 != null) {
         var8 = this.requestHeaders;
         StringBuilder var9 = new StringBuilder();
         var9.append(var6);
         var9.append(" ");
         var9.append("Google-API-Java-Client");
         var9.append("/");
         var9.append(GoogleUtils.VERSION);
         var8.setUserAgent(var9.toString());
      } else {
         var8 = this.requestHeaders;
         StringBuilder var7 = new StringBuilder();
         var7.append("Google-API-Java-Client/");
         var7.append(GoogleUtils.VERSION);
         var8.setUserAgent(var7.toString());
      }

      this.requestHeaders.set("X-Goog-Api-Client", AbstractGoogleClientRequest.ApiClientVersion.DEFAULT_VERSION);
   }

   private HttpRequest buildHttpRequest(boolean var1) throws IOException {
      MediaHttpUploader var2 = this.uploader;
      boolean var3 = true;
      boolean var4;
      if (var2 == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      var4 = var3;
      if (var1) {
         if (this.requestMethod.equals("GET")) {
            var4 = var3;
         } else {
            var4 = false;
         }
      }

      Preconditions.checkArgument(var4);
      String var5;
      if (var1) {
         var5 = "HEAD";
      } else {
         var5 = this.requestMethod;
      }

      final HttpRequest var6 = this.getAbstractGoogleClient().getRequestFactory().buildRequest(var5, this.buildHttpRequestUrl(), this.httpContent);
      (new MethodOverride()).intercept(var6);
      var6.setParser(this.getAbstractGoogleClient().getObjectParser());
      if (this.httpContent == null && (this.requestMethod.equals("POST") || this.requestMethod.equals("PUT") || this.requestMethod.equals("PATCH"))) {
         var6.setContent(new EmptyContent());
      }

      var6.getHeaders().putAll(this.requestHeaders);
      if (!this.disableGZipContent) {
         var6.setEncoding(new GZipEncoding());
      }

      var6.setResponseReturnRawInputStream(this.returnRawInputStream);
      var6.setResponseInterceptor(new HttpResponseInterceptor(var6.getResponseInterceptor()) {
         // $FF: synthetic field
         final HttpResponseInterceptor val$responseInterceptor;

         {
            this.val$responseInterceptor = var2;
         }

         public void interceptResponse(HttpResponse var1) throws IOException {
            HttpResponseInterceptor var2 = this.val$responseInterceptor;
            if (var2 != null) {
               var2.interceptResponse(var1);
            }

            if (!var1.isSuccessStatusCode() && var6.getThrowExceptionOnExecuteError()) {
               throw AbstractGoogleClientRequest.this.newExceptionOnError(var1);
            }
         }
      });
      return var6;
   }

   private HttpResponse executeUnparsed(boolean var1) throws IOException {
      HttpResponse var2;
      if (this.uploader == null) {
         var2 = this.buildHttpRequest(var1).execute();
      } else {
         GenericUrl var4 = this.buildHttpRequestUrl();
         var1 = this.getAbstractGoogleClient().getRequestFactory().buildRequest(this.requestMethod, var4, this.httpContent).getThrowExceptionOnExecuteError();
         HttpResponse var3 = this.uploader.setInitiationHeaders(this.requestHeaders).setDisableGZipContent(this.disableGZipContent).upload(var4);
         var3.getRequest().setParser(this.getAbstractGoogleClient().getObjectParser());
         var2 = var3;
         if (var1) {
            if (!var3.isSuccessStatusCode()) {
               throw this.newExceptionOnError(var3);
            }

            var2 = var3;
         }
      }

      this.lastResponseHeaders = var2.getHeaders();
      this.lastStatusCode = var2.getStatusCode();
      this.lastStatusMessage = var2.getStatusMessage();
      return var2;
   }

   public HttpRequest buildHttpRequest() throws IOException {
      return this.buildHttpRequest(false);
   }

   public GenericUrl buildHttpRequestUrl() {
      return new GenericUrl(UriTemplate.expand(this.abstractGoogleClient.getBaseUrl(), this.uriTemplate, this, true));
   }

   protected HttpRequest buildHttpRequestUsingHead() throws IOException {
      return this.buildHttpRequest(true);
   }

   protected final void checkRequiredParameter(Object var1, String var2) {
      boolean var3;
      if (!this.abstractGoogleClient.getSuppressRequiredParameterChecks() && var1 == null) {
         var3 = false;
      } else {
         var3 = true;
      }

      Preconditions.checkArgument(var3, "Required parameter %s must be specified", var2);
   }

   public T execute() throws IOException {
      return this.executeUnparsed().parseAs(this.responseClass);
   }

   public void executeAndDownloadTo(OutputStream var1) throws IOException {
      this.executeUnparsed().download(var1);
   }

   public InputStream executeAsInputStream() throws IOException {
      return this.executeUnparsed().getContent();
   }

   protected HttpResponse executeMedia() throws IOException {
      this.set("alt", "media");
      return this.executeUnparsed();
   }

   protected void executeMediaAndDownloadTo(OutputStream var1) throws IOException {
      MediaHttpDownloader var2 = this.downloader;
      if (var2 == null) {
         this.executeMedia().download(var1);
      } else {
         var2.download(this.buildHttpRequestUrl(), this.requestHeaders, var1);
      }

   }

   protected InputStream executeMediaAsInputStream() throws IOException {
      return this.executeMedia().getContent();
   }

   public HttpResponse executeUnparsed() throws IOException {
      return this.executeUnparsed(false);
   }

   protected HttpResponse executeUsingHead() throws IOException {
      boolean var1;
      if (this.uploader == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1);
      HttpResponse var2 = this.executeUnparsed(true);
      var2.ignore();
      return var2;
   }

   public AbstractGoogleClient getAbstractGoogleClient() {
      return this.abstractGoogleClient;
   }

   public final boolean getDisableGZipContent() {
      return this.disableGZipContent;
   }

   public final HttpContent getHttpContent() {
      return this.httpContent;
   }

   public final HttpHeaders getLastResponseHeaders() {
      return this.lastResponseHeaders;
   }

   public final int getLastStatusCode() {
      return this.lastStatusCode;
   }

   public final String getLastStatusMessage() {
      return this.lastStatusMessage;
   }

   public final MediaHttpDownloader getMediaHttpDownloader() {
      return this.downloader;
   }

   public final MediaHttpUploader getMediaHttpUploader() {
      return this.uploader;
   }

   public final HttpHeaders getRequestHeaders() {
      return this.requestHeaders;
   }

   public final String getRequestMethod() {
      return this.requestMethod;
   }

   public final Class<T> getResponseClass() {
      return this.responseClass;
   }

   public final boolean getReturnRawInputSteam() {
      return this.returnRawInputStream;
   }

   public final String getUriTemplate() {
      return this.uriTemplate;
   }

   protected final void initializeMediaDownload() {
      HttpRequestFactory var1 = this.abstractGoogleClient.getRequestFactory();
      this.downloader = new MediaHttpDownloader(var1.getTransport(), var1.getInitializer());
   }

   protected final void initializeMediaUpload(AbstractInputStreamContent var1) {
      HttpRequestFactory var2 = this.abstractGoogleClient.getRequestFactory();
      MediaHttpUploader var3 = new MediaHttpUploader(var1, var2.getTransport(), var2.getInitializer());
      this.uploader = var3;
      var3.setInitiationRequestMethod(this.requestMethod);
      HttpContent var4 = this.httpContent;
      if (var4 != null) {
         this.uploader.setMetadata(var4);
      }

   }

   protected IOException newExceptionOnError(HttpResponse var1) {
      return new HttpResponseException(var1);
   }

   public final <E> void queue(BatchRequest var1, Class<E> var2, BatchCallback<T, E> var3) throws IOException {
      boolean var4;
      if (this.uploader == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Batching media requests is not supported");
      var1.queue(this.buildHttpRequest(), this.getResponseClass(), var2, var3);
   }

   public AbstractGoogleClientRequest<T> set(String var1, Object var2) {
      return (AbstractGoogleClientRequest)super.set(var1, var2);
   }

   public AbstractGoogleClientRequest<T> setDisableGZipContent(boolean var1) {
      this.disableGZipContent = var1;
      return this;
   }

   public AbstractGoogleClientRequest<T> setRequestHeaders(HttpHeaders var1) {
      this.requestHeaders = var1;
      return this;
   }

   public AbstractGoogleClientRequest<T> setReturnRawInputStream(boolean var1) {
      this.returnRawInputStream = var1;
      return this;
   }

   static class ApiClientVersion {
      static final String DEFAULT_VERSION = (new AbstractGoogleClientRequest.ApiClientVersion()).toString();
      private final String versionString;

      ApiClientVersion() {
         this(getJavaVersion(), StandardSystemProperty.OS_NAME.value(), StandardSystemProperty.OS_VERSION.value(), GoogleUtils.VERSION);
      }

      ApiClientVersion(String var1, String var2, String var3, String var4) {
         StringBuilder var5 = new StringBuilder("gl-java/");
         var5.append(formatSemver(var1));
         var5.append(" gdcl/");
         var5.append(formatSemver(var4));
         if (var2 != null && var3 != null) {
            var5.append(" ");
            var5.append(formatName(var2));
            var5.append("/");
            var5.append(formatSemver(var3));
         }

         this.versionString = var5.toString();
      }

      private static String formatName(String var0) {
         return var0.toLowerCase().replaceAll("[^\\w\\d\\-]", "-");
      }

      private static String formatSemver(String var0) {
         return formatSemver(var0, var0);
      }

      private static String formatSemver(String var0, String var1) {
         if (var0 == null) {
            return null;
         } else {
            Matcher var2 = Pattern.compile("(\\d+\\.\\d+\\.\\d+).*").matcher(var0);
            return var2.find() ? var2.group(1) : var1;
         }
      }

      private static String getJavaVersion() {
         String var0 = System.getProperty("java.version");
         if (var0 == null) {
            return null;
         } else {
            String var1 = formatSemver(var0, (String)null);
            if (var1 != null) {
               return var1;
            } else {
               Matcher var2 = Pattern.compile("^(\\d+)[^\\d]?").matcher(var0);
               if (var2.find()) {
                  StringBuilder var3 = new StringBuilder();
                  var3.append(var2.group(1));
                  var3.append(".0.0");
                  return var3.toString();
               } else {
                  return null;
               }
            }
         }
      }

      public String toString() {
         return this.versionString;
      }
   }
}
