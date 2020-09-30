package com.google.api.client.googleapis.media;

import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class MediaUploadErrorHandler implements HttpUnsuccessfulResponseHandler, HttpIOExceptionHandler {
   static final Logger LOGGER = Logger.getLogger(MediaUploadErrorHandler.class.getName());
   private final HttpIOExceptionHandler originalIOExceptionHandler;
   private final HttpUnsuccessfulResponseHandler originalUnsuccessfulHandler;
   private final MediaHttpUploader uploader;

   public MediaUploadErrorHandler(MediaHttpUploader var1, HttpRequest var2) {
      this.uploader = (MediaHttpUploader)Preconditions.checkNotNull(var1);
      this.originalIOExceptionHandler = var2.getIOExceptionHandler();
      this.originalUnsuccessfulHandler = var2.getUnsuccessfulResponseHandler();
      var2.setIOExceptionHandler(this);
      var2.setUnsuccessfulResponseHandler(this);
   }

   public boolean handleIOException(HttpRequest var1, boolean var2) throws IOException {
      HttpIOExceptionHandler var3 = this.originalIOExceptionHandler;
      if (var3 != null && var3.handleIOException(var1, var2)) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         try {
            this.uploader.serverErrorCallback();
         } catch (IOException var4) {
            LOGGER.log(Level.WARNING, "exception thrown while calling server callback", var4);
         }
      }

      return var2;
   }

   public boolean handleResponse(HttpRequest var1, HttpResponse var2, boolean var3) throws IOException {
      HttpUnsuccessfulResponseHandler var4 = this.originalUnsuccessfulHandler;
      boolean var5;
      if (var4 != null && var4.handleResponse(var1, var2, var3)) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5 && var3 && var2.getStatusCode() / 100 == 5) {
         try {
            this.uploader.serverErrorCallback();
         } catch (IOException var6) {
            LOGGER.log(Level.WARNING, "exception thrown while calling server callback", var6);
         }
      }

      return var5;
   }
}
