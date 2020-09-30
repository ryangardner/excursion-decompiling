package com.google.api.client.googleapis.media;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Preconditions;
import com.google.common.base.MoreObjects;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.OutputStream;

public final class MediaHttpDownloader {
   public static final int MAXIMUM_CHUNK_SIZE = 33554432;
   private long bytesDownloaded;
   private int chunkSize = 33554432;
   private boolean directDownloadEnabled = false;
   private MediaHttpDownloader.DownloadState downloadState;
   private long lastBytePos;
   private long mediaContentLength;
   private MediaHttpDownloaderProgressListener progressListener;
   private final HttpRequestFactory requestFactory;
   private final HttpTransport transport;

   public MediaHttpDownloader(HttpTransport var1, HttpRequestInitializer var2) {
      this.downloadState = MediaHttpDownloader.DownloadState.NOT_STARTED;
      this.lastBytePos = -1L;
      this.transport = (HttpTransport)Preconditions.checkNotNull(var1);
      HttpRequestFactory var3;
      if (var2 == null) {
         var3 = var1.createRequestFactory();
      } else {
         var3 = var1.createRequestFactory(var2);
      }

      this.requestFactory = var3;
   }

   private HttpResponse executeCurrentRequest(long var1, GenericUrl var3, HttpHeaders var4, OutputStream var5) throws IOException {
      HttpRequest var8 = this.requestFactory.buildGetRequest(var3);
      if (var4 != null) {
         var8.getHeaders().putAll(var4);
      }

      if (this.bytesDownloaded != 0L || var1 != -1L) {
         StringBuilder var10 = new StringBuilder();
         var10.append("bytes=");
         var10.append(this.bytesDownloaded);
         var10.append("-");
         if (var1 != -1L) {
            var10.append(var1);
         }

         var8.getHeaders().setRange(var10.toString());
      }

      HttpResponse var9 = var8.execute();

      try {
         ByteStreams.copy(var9.getContent(), var5);
      } finally {
         var9.disconnect();
      }

      return var9;
   }

   private long getNextByteIndex(String var1) {
      return var1 == null ? 0L : Long.parseLong(var1.substring(var1.indexOf(45) + 1, var1.indexOf(47))) + 1L;
   }

   private void setMediaContentLength(String var1) {
      if (var1 != null) {
         if (this.mediaContentLength == 0L) {
            this.mediaContentLength = Long.parseLong(var1.substring(var1.indexOf(47) + 1));
         }

      }
   }

   private void updateStateAndNotifyListener(MediaHttpDownloader.DownloadState var1) throws IOException {
      this.downloadState = var1;
      MediaHttpDownloaderProgressListener var2 = this.progressListener;
      if (var2 != null) {
         var2.progressChanged(this);
      }

   }

   public void download(GenericUrl var1, HttpHeaders var2, OutputStream var3) throws IOException {
      boolean var4;
      if (this.downloadState == MediaHttpDownloader.DownloadState.NOT_STARTED) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      var1.put("alt", "media");
      long var5;
      if (this.directDownloadEnabled) {
         this.updateStateAndNotifyListener(MediaHttpDownloader.DownloadState.MEDIA_IN_PROGRESS);
         var5 = (Long)MoreObjects.firstNonNull(this.executeCurrentRequest(this.lastBytePos, var1, var2, var3).getHeaders().getContentLength(), this.mediaContentLength);
         this.mediaContentLength = var5;
         this.bytesDownloaded = var5;
         this.updateStateAndNotifyListener(MediaHttpDownloader.DownloadState.MEDIA_COMPLETE);
      } else {
         while(true) {
            long var7 = this.bytesDownloaded + (long)this.chunkSize - 1L;
            long var9 = this.lastBytePos;
            var5 = var7;
            if (var9 != -1L) {
               var5 = Math.min(var9, var7);
            }

            String var11 = this.executeCurrentRequest(var5, var1, var2, var3).getHeaders().getContentRange();
            var5 = this.getNextByteIndex(var11);
            this.setMediaContentLength(var11);
            var7 = this.lastBytePos;
            if (var7 != -1L && var7 <= var5) {
               this.bytesDownloaded = var7;
               this.updateStateAndNotifyListener(MediaHttpDownloader.DownloadState.MEDIA_COMPLETE);
               return;
            }

            var7 = this.mediaContentLength;
            if (var7 <= var5) {
               this.bytesDownloaded = var7;
               this.updateStateAndNotifyListener(MediaHttpDownloader.DownloadState.MEDIA_COMPLETE);
               return;
            }

            this.bytesDownloaded = var5;
            this.updateStateAndNotifyListener(MediaHttpDownloader.DownloadState.MEDIA_IN_PROGRESS);
         }
      }
   }

   public void download(GenericUrl var1, OutputStream var2) throws IOException {
      this.download(var1, (HttpHeaders)null, var2);
   }

   public int getChunkSize() {
      return this.chunkSize;
   }

   public MediaHttpDownloader.DownloadState getDownloadState() {
      return this.downloadState;
   }

   public long getLastBytePosition() {
      return this.lastBytePos;
   }

   public long getNumBytesDownloaded() {
      return this.bytesDownloaded;
   }

   public double getProgress() {
      long var1 = this.mediaContentLength;
      double var3;
      if (var1 == 0L) {
         var3 = 0.0D;
      } else {
         var3 = (double)this.bytesDownloaded / (double)var1;
      }

      return var3;
   }

   public MediaHttpDownloaderProgressListener getProgressListener() {
      return this.progressListener;
   }

   public HttpTransport getTransport() {
      return this.transport;
   }

   public boolean isDirectDownloadEnabled() {
      return this.directDownloadEnabled;
   }

   public MediaHttpDownloader setBytesDownloaded(long var1) {
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.bytesDownloaded = var1;
      return this;
   }

   public MediaHttpDownloader setChunkSize(int var1) {
      boolean var2;
      if (var1 > 0 && var1 <= 33554432) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.chunkSize = var1;
      return this;
   }

   @Deprecated
   public MediaHttpDownloader setContentRange(long var1, int var3) {
      return this.setContentRange(var1, (long)var3);
   }

   public MediaHttpDownloader setContentRange(long var1, long var3) {
      boolean var5;
      if (var3 >= var1) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      this.setBytesDownloaded(var1);
      this.lastBytePos = var3;
      return this;
   }

   public MediaHttpDownloader setDirectDownloadEnabled(boolean var1) {
      this.directDownloadEnabled = var1;
      return this;
   }

   public MediaHttpDownloader setProgressListener(MediaHttpDownloaderProgressListener var1) {
      this.progressListener = var1;
      return this;
   }

   public static enum DownloadState {
      MEDIA_COMPLETE,
      MEDIA_IN_PROGRESS,
      NOT_STARTED;

      static {
         MediaHttpDownloader.DownloadState var0 = new MediaHttpDownloader.DownloadState("MEDIA_COMPLETE", 2);
         MEDIA_COMPLETE = var0;
      }
   }
}
