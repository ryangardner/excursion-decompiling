package com.google.api.client.googleapis.media;

import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GZipEncoding;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.util.ByteStreams;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class MediaHttpUploader {
   public static final String CONTENT_LENGTH_HEADER = "X-Upload-Content-Length";
   public static final String CONTENT_TYPE_HEADER = "X-Upload-Content-Type";
   public static final int DEFAULT_CHUNK_SIZE = 10485760;
   private static final int KB = 1024;
   static final int MB = 1048576;
   public static final int MINIMUM_CHUNK_SIZE = 262144;
   private Byte cachedByte;
   private int chunkSize;
   private InputStream contentInputStream;
   private int currentChunkLength;
   private HttpRequest currentRequest;
   private byte[] currentRequestContentBuffer;
   private boolean directUploadEnabled;
   private boolean disableGZipContent;
   private HttpHeaders initiationHeaders;
   private String initiationRequestMethod;
   private boolean isMediaContentLengthCalculated;
   private final AbstractInputStreamContent mediaContent;
   private long mediaContentLength;
   String mediaContentLengthStr;
   private HttpContent metadata;
   private MediaHttpUploaderProgressListener progressListener;
   private final HttpRequestFactory requestFactory;
   Sleeper sleeper;
   private long totalBytesClientSent;
   private long totalBytesServerReceived;
   private final HttpTransport transport;
   private MediaHttpUploader.UploadState uploadState;

   public MediaHttpUploader(AbstractInputStreamContent var1, HttpTransport var2, HttpRequestInitializer var3) {
      this.uploadState = MediaHttpUploader.UploadState.NOT_STARTED;
      this.initiationRequestMethod = "POST";
      this.initiationHeaders = new HttpHeaders();
      this.mediaContentLengthStr = "*";
      this.chunkSize = 10485760;
      this.sleeper = Sleeper.DEFAULT;
      this.mediaContent = (AbstractInputStreamContent)Preconditions.checkNotNull(var1);
      this.transport = (HttpTransport)Preconditions.checkNotNull(var2);
      HttpRequestFactory var4;
      if (var3 == null) {
         var4 = var2.createRequestFactory();
      } else {
         var4 = var2.createRequestFactory(var3);
      }

      this.requestFactory = var4;
   }

   private MediaHttpUploader.ContentChunk buildContentChunk() throws IOException {
      int var1;
      if (this.isMediaLengthKnown()) {
         var1 = (int)Math.min((long)this.chunkSize, this.getMediaContentLength() - this.totalBytesServerReceived);
      } else {
         var1 = this.chunkSize;
      }

      Object var9;
      if (this.isMediaLengthKnown()) {
         this.contentInputStream.mark(var1);
         InputStream var2 = this.contentInputStream;
         long var3 = (long)var1;
         var2 = ByteStreams.limit(var2, var3);
         var9 = (new InputStreamContent(this.mediaContent.getType(), var2)).setRetrySupported(true).setLength(var3).setCloseInputStream(false);
         this.mediaContentLengthStr = String.valueOf(this.getMediaContentLength());
      } else {
         byte[] var10 = this.currentRequestContentBuffer;
         int var5;
         int var8;
         if (var10 == null) {
            if (this.cachedByte == null) {
               var5 = var1 + 1;
            } else {
               var5 = var1;
            }

            var10 = new byte[var1 + 1];
            this.currentRequestContentBuffer = var10;
            Byte var6 = this.cachedByte;
            if (var6 != null) {
               var10[0] = var6;
            }

            byte var7 = 0;
            var8 = var5;
            var5 = var7;
         } else {
            var5 = (int)(this.totalBytesClientSent - this.totalBytesServerReceived);
            System.arraycopy(var10, this.currentChunkLength - var5, var10, 0, var5);
            Byte var11 = this.cachedByte;
            if (var11 != null) {
               this.currentRequestContentBuffer[var5] = var11;
            }

            var8 = var1 - var5;
         }

         int var14 = ByteStreams.read(this.contentInputStream, this.currentRequestContentBuffer, var1 + 1 - var8, var8);
         if (var14 < var8) {
            var5 += Math.max(0, var14);
            var1 = var5;
            if (this.cachedByte != null) {
               var1 = var5 + 1;
               this.cachedByte = null;
            }

            if (this.mediaContentLengthStr.equals("*")) {
               this.mediaContentLengthStr = String.valueOf(this.totalBytesServerReceived + (long)var1);
            }
         } else {
            this.cachedByte = this.currentRequestContentBuffer[var1];
         }

         var9 = new ByteArrayContent(this.mediaContent.getType(), this.currentRequestContentBuffer, 0, var1);
         this.totalBytesClientSent = this.totalBytesServerReceived + (long)var1;
      }

      this.currentChunkLength = var1;
      StringBuilder var12;
      String var13;
      if (var1 == 0) {
         var12 = new StringBuilder();
         var12.append("bytes */");
         var12.append(this.mediaContentLengthStr);
         var13 = var12.toString();
      } else {
         var12 = new StringBuilder();
         var12.append("bytes ");
         var12.append(this.totalBytesServerReceived);
         var12.append("-");
         var12.append(this.totalBytesServerReceived + (long)var1 - 1L);
         var12.append("/");
         var12.append(this.mediaContentLengthStr);
         var13 = var12.toString();
      }

      return new MediaHttpUploader.ContentChunk((AbstractInputStreamContent)var9, var13);
   }

   private HttpResponse directUpload(GenericUrl var1) throws IOException {
      this.updateStateAndNotifyListener(MediaHttpUploader.UploadState.MEDIA_IN_PROGRESS);
      Object var2 = this.mediaContent;
      if (this.metadata != null) {
         var2 = (new MultipartContent()).setContentParts(Arrays.asList(this.metadata, this.mediaContent));
         var1.put("uploadType", "multipart");
      } else {
         var1.put("uploadType", "media");
      }

      HttpRequest var5 = this.requestFactory.buildRequest(this.initiationRequestMethod, var1, (HttpContent)var2);
      var5.getHeaders().putAll(this.initiationHeaders);
      HttpResponse var6 = this.executeCurrentRequest(var5);

      try {
         if (this.isMediaLengthKnown()) {
            this.totalBytesServerReceived = this.getMediaContentLength();
         }

         this.updateStateAndNotifyListener(MediaHttpUploader.UploadState.MEDIA_COMPLETE);
         return var6;
      } finally {
         var6.disconnect();
      }
   }

   private HttpResponse executeCurrentRequest(HttpRequest var1) throws IOException {
      if (!this.disableGZipContent && !(var1.getContent() instanceof EmptyContent)) {
         var1.setEncoding(new GZipEncoding());
      }

      return this.executeCurrentRequestWithoutGZip(var1);
   }

   private HttpResponse executeCurrentRequestWithoutGZip(HttpRequest var1) throws IOException {
      (new MethodOverride()).intercept(var1);
      var1.setThrowExceptionOnExecuteError(false);
      return var1.execute();
   }

   private HttpResponse executeUploadInitiation(GenericUrl var1) throws IOException {
      this.updateStateAndNotifyListener(MediaHttpUploader.UploadState.INITIATION_STARTED);
      var1.put("uploadType", "resumable");
      HttpContent var2 = this.metadata;
      Object var3 = var2;
      if (var2 == null) {
         var3 = new EmptyContent();
      }

      HttpRequest var6 = this.requestFactory.buildRequest(this.initiationRequestMethod, var1, (HttpContent)var3);
      this.initiationHeaders.set("X-Upload-Content-Type", this.mediaContent.getType());
      if (this.isMediaLengthKnown()) {
         this.initiationHeaders.set("X-Upload-Content-Length", this.getMediaContentLength());
      }

      var6.getHeaders().putAll(this.initiationHeaders);
      HttpResponse var7 = this.executeCurrentRequest(var6);

      try {
         this.updateStateAndNotifyListener(MediaHttpUploader.UploadState.INITIATION_COMPLETE);
         return var7;
      } finally {
         var7.disconnect();
      }
   }

   private long getMediaContentLength() throws IOException {
      if (!this.isMediaContentLengthCalculated) {
         this.mediaContentLength = this.mediaContent.getLength();
         this.isMediaContentLengthCalculated = true;
      }

      return this.mediaContentLength;
   }

   private long getNextByteIndex(String var1) {
      return var1 == null ? 0L : Long.parseLong(var1.substring(var1.indexOf(45) + 1)) + 1L;
   }

   private boolean isMediaLengthKnown() throws IOException {
      boolean var1;
      if (this.getMediaContentLength() >= 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private HttpResponse resumableUpload(GenericUrl var1) throws IOException {
      HttpResponse var194 = this.executeUploadInitiation(var1);
      if (!var194.isSuccessStatusCode()) {
         return var194;
      } else {
         GenericUrl var2;
         try {
            var2 = new GenericUrl(var194.getHeaders().getLocation());
         } finally {
            var194.disconnect();
         }

         InputStream var3 = this.mediaContent.getInputStream();
         this.contentInputStream = var3;
         var1 = var2;
         if (!var3.markSupported()) {
            var1 = var2;
            if (this.isMediaLengthKnown()) {
               this.contentInputStream = new BufferedInputStream(this.contentInputStream);
               var1 = var2;
            }
         }

         while(true) {
            MediaHttpUploader.ContentChunk var195 = this.buildContentChunk();
            HttpRequest var197 = this.requestFactory.buildPutRequest(var1, (HttpContent)null);
            this.currentRequest = var197;
            var197.setContent(var195.getContent());
            this.currentRequest.getHeaders().setContentRange(var195.getContentRange());
            new MediaUploadErrorHandler(this, this.currentRequest);
            HttpResponse var196;
            if (this.isMediaLengthKnown()) {
               var196 = this.executeCurrentRequestWithoutGZip(this.currentRequest);
            } else {
               var196 = this.executeCurrentRequest(this.currentRequest);
            }

            Throwable var10000;
            label1893: {
               boolean var10001;
               label1900: {
                  try {
                     if (var196.isSuccessStatusCode()) {
                        this.totalBytesServerReceived = this.getMediaContentLength();
                        if (this.mediaContent.getCloseInputStream()) {
                           this.contentInputStream.close();
                        }
                        break label1900;
                     }
                  } catch (Throwable var193) {
                     var10000 = var193;
                     var10001 = false;
                     break label1893;
                  }

                  try {
                     if (var196.getStatusCode() != 308) {
                        if (this.mediaContent.getCloseInputStream()) {
                           this.contentInputStream.close();
                        }

                        return var196;
                     }
                  } catch (Throwable var192) {
                     var10000 = var192;
                     var10001 = false;
                     break label1893;
                  }

                  String var199;
                  try {
                     var199 = var196.getHeaders().getLocation();
                  } catch (Throwable var191) {
                     var10000 = var191;
                     var10001 = false;
                     break label1893;
                  }

                  if (var199 != null) {
                     try {
                        var1 = new GenericUrl(var199);
                     } catch (Throwable var190) {
                        var10000 = var190;
                        var10001 = false;
                        break label1893;
                     }
                  }

                  long var4;
                  long var6;
                  try {
                     var4 = this.getNextByteIndex(var196.getHeaders().getRange());
                     var6 = var4 - this.totalBytesServerReceived;
                  } catch (Throwable var189) {
                     var10000 = var189;
                     var10001 = false;
                     break label1893;
                  }

                  boolean var8;
                  boolean var9;
                  label1868: {
                     label1867: {
                        var8 = true;
                        if (var6 >= 0L) {
                           try {
                              if (var6 <= (long)this.currentChunkLength) {
                                 break label1867;
                              }
                           } catch (Throwable var188) {
                              var10000 = var188;
                              var10001 = false;
                              break label1893;
                           }
                        }

                        var9 = false;
                        break label1868;
                     }

                     var9 = true;
                  }

                  label1859: {
                     long var10;
                     label1858: {
                        try {
                           Preconditions.checkState(var9);
                           var10 = (long)this.currentChunkLength - var6;
                           if (!this.isMediaLengthKnown()) {
                              break label1858;
                           }
                        } catch (Throwable var187) {
                           var10000 = var187;
                           var10001 = false;
                           break label1893;
                        }

                        if (var10 > 0L) {
                           label1851: {
                              label1850: {
                                 try {
                                    this.contentInputStream.reset();
                                    if (var6 == this.contentInputStream.skip(var6)) {
                                       break label1850;
                                    }
                                 } catch (Throwable var186) {
                                    var10000 = var186;
                                    var10001 = false;
                                    break label1893;
                                 }

                                 var9 = false;
                                 break label1851;
                              }

                              var9 = var8;
                           }

                           try {
                              Preconditions.checkState(var9);
                           } catch (Throwable var185) {
                              var10000 = var185;
                              var10001 = false;
                              break label1893;
                           }
                        }
                        break label1859;
                     }

                     if (var10 == 0L) {
                        try {
                           this.currentRequestContentBuffer = null;
                        } catch (Throwable var184) {
                           var10000 = var184;
                           var10001 = false;
                           break label1893;
                        }
                     }
                  }

                  try {
                     this.totalBytesServerReceived = var4;
                     this.updateStateAndNotifyListener(MediaHttpUploader.UploadState.MEDIA_IN_PROGRESS);
                  } catch (Throwable var183) {
                     var10000 = var183;
                     var10001 = false;
                     break label1893;
                  }

                  var196.disconnect();
                  continue;
               }

               label1838:
               try {
                  this.updateStateAndNotifyListener(MediaHttpUploader.UploadState.MEDIA_COMPLETE);
                  return var196;
               } catch (Throwable var182) {
                  var10000 = var182;
                  var10001 = false;
                  break label1838;
               }
            }

            Throwable var198 = var10000;
            var196.disconnect();
            throw var198;
         }
      }
   }

   private void updateStateAndNotifyListener(MediaHttpUploader.UploadState var1) throws IOException {
      this.uploadState = var1;
      MediaHttpUploaderProgressListener var2 = this.progressListener;
      if (var2 != null) {
         var2.progressChanged(this);
      }

   }

   public int getChunkSize() {
      return this.chunkSize;
   }

   public boolean getDisableGZipContent() {
      return this.disableGZipContent;
   }

   public HttpHeaders getInitiationHeaders() {
      return this.initiationHeaders;
   }

   public String getInitiationRequestMethod() {
      return this.initiationRequestMethod;
   }

   public HttpContent getMediaContent() {
      return this.mediaContent;
   }

   public HttpContent getMetadata() {
      return this.metadata;
   }

   public long getNumBytesUploaded() {
      return this.totalBytesServerReceived;
   }

   public double getProgress() throws IOException {
      Preconditions.checkArgument(this.isMediaLengthKnown(), "Cannot call getProgress() if the specified AbstractInputStreamContent has no content length. Use  getNumBytesUploaded() to denote progress instead.");
      double var1;
      if (this.getMediaContentLength() == 0L) {
         var1 = 0.0D;
      } else {
         var1 = (double)this.totalBytesServerReceived / (double)this.getMediaContentLength();
      }

      return var1;
   }

   public MediaHttpUploaderProgressListener getProgressListener() {
      return this.progressListener;
   }

   public Sleeper getSleeper() {
      return this.sleeper;
   }

   public HttpTransport getTransport() {
      return this.transport;
   }

   public MediaHttpUploader.UploadState getUploadState() {
      return this.uploadState;
   }

   public boolean isDirectUploadEnabled() {
      return this.directUploadEnabled;
   }

   void serverErrorCallback() throws IOException {
      Preconditions.checkNotNull(this.currentRequest, "The current request should not be null");
      this.currentRequest.setContent(new EmptyContent());
      HttpHeaders var1 = this.currentRequest.getHeaders();
      StringBuilder var2 = new StringBuilder();
      var2.append("bytes */");
      var2.append(this.mediaContentLengthStr);
      var1.setContentRange(var2.toString());
   }

   public MediaHttpUploader setChunkSize(int var1) {
      boolean var2;
      if (var1 > 0 && var1 % 262144 == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "chunkSize must be a positive multiple of 262144.");
      this.chunkSize = var1;
      return this;
   }

   public MediaHttpUploader setDirectUploadEnabled(boolean var1) {
      this.directUploadEnabled = var1;
      return this;
   }

   public MediaHttpUploader setDisableGZipContent(boolean var1) {
      this.disableGZipContent = var1;
      return this;
   }

   public MediaHttpUploader setInitiationHeaders(HttpHeaders var1) {
      this.initiationHeaders = var1;
      return this;
   }

   public MediaHttpUploader setInitiationRequestMethod(String var1) {
      boolean var2;
      if (!var1.equals("POST") && !var1.equals("PUT") && !var1.equals("PATCH")) {
         var2 = false;
      } else {
         var2 = true;
      }

      Preconditions.checkArgument(var2);
      this.initiationRequestMethod = var1;
      return this;
   }

   public MediaHttpUploader setMetadata(HttpContent var1) {
      this.metadata = var1;
      return this;
   }

   public MediaHttpUploader setProgressListener(MediaHttpUploaderProgressListener var1) {
      this.progressListener = var1;
      return this;
   }

   public MediaHttpUploader setSleeper(Sleeper var1) {
      this.sleeper = var1;
      return this;
   }

   public HttpResponse upload(GenericUrl var1) throws IOException {
      boolean var2;
      if (this.uploadState == MediaHttpUploader.UploadState.NOT_STARTED) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      return this.directUploadEnabled ? this.directUpload(var1) : this.resumableUpload(var1);
   }

   private static class ContentChunk {
      private final AbstractInputStreamContent content;
      private final String contentRange;

      ContentChunk(AbstractInputStreamContent var1, String var2) {
         this.content = var1;
         this.contentRange = var2;
      }

      AbstractInputStreamContent getContent() {
         return this.content;
      }

      String getContentRange() {
         return this.contentRange;
      }
   }

   public static enum UploadState {
      INITIATION_COMPLETE,
      INITIATION_STARTED,
      MEDIA_COMPLETE,
      MEDIA_IN_PROGRESS,
      NOT_STARTED;

      static {
         MediaHttpUploader.UploadState var0 = new MediaHttpUploader.UploadState("MEDIA_COMPLETE", 4);
         MEDIA_COMPLETE = var0;
      }
   }
}
