package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.IOException;

public class HttpResponseException extends IOException {
   private static final long serialVersionUID = -1875819453475890043L;
   private final String content;
   private final transient HttpHeaders headers;
   private final int statusCode;
   private final String statusMessage;

   public HttpResponseException(HttpResponse var1) {
      this(new HttpResponseException.Builder(var1));
   }

   protected HttpResponseException(HttpResponseException.Builder var1) {
      super(var1.message);
      this.statusCode = var1.statusCode;
      this.statusMessage = var1.statusMessage;
      this.headers = var1.headers;
      this.content = var1.content;
   }

   public static StringBuilder computeMessageBuffer(HttpResponse var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = var0.getStatusCode();
      if (var2 != 0) {
         var1.append(var2);
      }

      String var3 = var0.getStatusMessage();
      if (var3 != null) {
         if (var2 != 0) {
            var1.append(' ');
         }

         var1.append(var3);
      }

      return var1;
   }

   public final String getContent() {
      return this.content;
   }

   public HttpHeaders getHeaders() {
      return this.headers;
   }

   public final int getStatusCode() {
      return this.statusCode;
   }

   public final String getStatusMessage() {
      return this.statusMessage;
   }

   public final boolean isSuccessStatusCode() {
      return HttpStatusCodes.isSuccess(this.statusCode);
   }

   public static class Builder {
      String content;
      HttpHeaders headers;
      String message;
      int statusCode;
      String statusMessage;

      public Builder(int var1, String var2, HttpHeaders var3) {
         this.setStatusCode(var1);
         this.setStatusMessage(var2);
         this.setHeaders(var3);
      }

      public Builder(HttpResponse var1) {
         this(var1.getStatusCode(), var1.getStatusMessage(), var1.getHeaders());

         try {
            String var2 = var1.parseAsString();
            this.content = var2;
            if (var2.length() == 0) {
               this.content = null;
            }
         } catch (IOException var3) {
            var3.printStackTrace();
         } catch (IllegalArgumentException var4) {
            var4.printStackTrace();
         }

         StringBuilder var5 = HttpResponseException.computeMessageBuffer(var1);
         if (this.content != null) {
            var5.append(StringUtils.LINE_SEPARATOR);
            var5.append(this.content);
         }

         this.message = var5.toString();
      }

      public HttpResponseException build() {
         return new HttpResponseException(this);
      }

      public final String getContent() {
         return this.content;
      }

      public HttpHeaders getHeaders() {
         return this.headers;
      }

      public final String getMessage() {
         return this.message;
      }

      public final int getStatusCode() {
         return this.statusCode;
      }

      public final String getStatusMessage() {
         return this.statusMessage;
      }

      public HttpResponseException.Builder setContent(String var1) {
         this.content = var1;
         return this;
      }

      public HttpResponseException.Builder setHeaders(HttpHeaders var1) {
         this.headers = (HttpHeaders)Preconditions.checkNotNull(var1);
         return this;
      }

      public HttpResponseException.Builder setMessage(String var1) {
         this.message = var1;
         return this;
      }

      public HttpResponseException.Builder setStatusCode(int var1) {
         boolean var2;
         if (var1 >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2);
         this.statusCode = var1;
         return this;
      }

      public HttpResponseException.Builder setStatusMessage(String var1) {
         this.statusMessage = var1;
         return this;
      }
   }
}
