package com.google.api.client.googleapis.batch;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BatchRequest {
   private static final String GLOBAL_BATCH_ENDPOINT = "https://www.googleapis.com/batch";
   private static final String GLOBAL_BATCH_ENDPOINT_WARNING = "You are using the global batch endpoint which will soon be shut down. Please instantiate your BatchRequest via your service client's `batch(HttpRequestInitializer)` method. For an example, please see https://github.com/googleapis/google-api-java-client#batching.";
   private static final Logger LOGGER = Logger.getLogger(BatchRequest.class.getName());
   private GenericUrl batchUrl = new GenericUrl("https://www.googleapis.com/batch");
   private final HttpRequestFactory requestFactory;
   List<BatchRequest.RequestInfo<?, ?>> requestInfos = new ArrayList();
   private Sleeper sleeper;

   @Deprecated
   public BatchRequest(HttpTransport var1, HttpRequestInitializer var2) {
      this.sleeper = Sleeper.DEFAULT;
      HttpRequestFactory var3;
      if (var2 == null) {
         var3 = var1.createRequestFactory();
      } else {
         var3 = var1.createRequestFactory(var2);
      }

      this.requestFactory = var3;
   }

   public void execute() throws IOException {
      Preconditions.checkState(this.requestInfos.isEmpty() ^ true);
      if ("https://www.googleapis.com/batch".equals(this.batchUrl.toString())) {
         LOGGER.log(Level.WARNING, "You are using the global batch endpoint which will soon be shut down. Please instantiate your BatchRequest via your service client's `batch(HttpRequestInitializer)` method. For an example, please see https://github.com/googleapis/google-api-java-client#batching.");
      }

      HttpRequest var1 = this.requestFactory.buildPostRequest(this.batchUrl, (HttpContent)null);
      var1.setInterceptor(new BatchRequest.BatchInterceptor(var1.getInterceptor()));
      int var2 = var1.getNumberOfRetries();

      boolean var3;
      do {
         if (var2 > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         MultipartContent var4 = new MultipartContent();
         var4.getMediaType().setSubType("mixed");
         Iterator var5 = this.requestInfos.iterator();

         for(int var6 = 1; var5.hasNext(); ++var6) {
            BatchRequest.RequestInfo var7 = (BatchRequest.RequestInfo)var5.next();
            var4.addPart(new MultipartContent.Part((new HttpHeaders()).setAcceptEncoding((String)null).set("Content-ID", var6), new HttpRequestContent(var7.request)));
         }

         var1.setContent(var4);
         HttpResponse var19 = var1.execute();

         BatchUnparsedResponse var8;
         label160: {
            Throwable var10000;
            label159: {
               boolean var10001;
               try {
                  StringBuilder var16 = new StringBuilder();
                  var16.append("--");
                  var16.append(var19.getMediaType().getParameter("boundary"));
                  String var17 = var16.toString();
                  InputStream var18 = var19.getContent();
                  var8 = new BatchUnparsedResponse(var18, var17, this.requestInfos, var3);
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label159;
               }

               while(true) {
                  try {
                     if (!var8.hasNext) {
                        break label160;
                     }

                     var8.parseNextResponse();
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var15 = var10000;
            var19.disconnect();
            throw var15;
         }

         var19.disconnect();
         List var20 = var8.unsuccessfulRequestInfos;
         if (var20.isEmpty()) {
            break;
         }

         this.requestInfos = var20;
         --var2;
      } while(var3);

      this.requestInfos.clear();
   }

   public GenericUrl getBatchUrl() {
      return this.batchUrl;
   }

   public Sleeper getSleeper() {
      return this.sleeper;
   }

   public <T, E> BatchRequest queue(HttpRequest var1, Class<T> var2, Class<E> var3, BatchCallback<T, E> var4) throws IOException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var3);
      this.requestInfos.add(new BatchRequest.RequestInfo(var4, var2, var3, var1));
      return this;
   }

   public BatchRequest setBatchUrl(GenericUrl var1) {
      this.batchUrl = var1;
      return this;
   }

   public BatchRequest setSleeper(Sleeper var1) {
      this.sleeper = (Sleeper)Preconditions.checkNotNull(var1);
      return this;
   }

   public int size() {
      return this.requestInfos.size();
   }

   class BatchInterceptor implements HttpExecuteInterceptor {
      private HttpExecuteInterceptor originalInterceptor;

      BatchInterceptor(HttpExecuteInterceptor var2) {
         this.originalInterceptor = var2;
      }

      public void intercept(HttpRequest var1) throws IOException {
         HttpExecuteInterceptor var2 = this.originalInterceptor;
         if (var2 != null) {
            var2.intercept(var1);
         }

         Iterator var3 = BatchRequest.this.requestInfos.iterator();

         while(var3.hasNext()) {
            BatchRequest.RequestInfo var4 = (BatchRequest.RequestInfo)var3.next();
            var2 = var4.request.getInterceptor();
            if (var2 != null) {
               var2.intercept(var4.request);
            }
         }

      }
   }

   static class RequestInfo<T, E> {
      final BatchCallback<T, E> callback;
      final Class<T> dataClass;
      final Class<E> errorClass;
      final HttpRequest request;

      RequestInfo(BatchCallback<T, E> var1, Class<T> var2, Class<E> var3, HttpRequest var4) {
         this.callback = var1;
         this.dataClass = var2;
         this.errorClass = var3;
         this.request = var4;
      }
   }
}
