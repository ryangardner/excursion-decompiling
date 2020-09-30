package org.apache.http.impl;

import java.io.IOException;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpServerConnection;
import org.apache.http.impl.entity.EntityDeserializer;
import org.apache.http.impl.entity.EntitySerializer;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.HttpRequestParser;
import org.apache.http.impl.io.HttpResponseWriter;
import org.apache.http.io.EofSensor;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineFormatter;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;

public abstract class AbstractHttpServerConnection implements HttpServerConnection {
   private final EntityDeserializer entitydeserializer = this.createEntityDeserializer();
   private final EntitySerializer entityserializer = this.createEntitySerializer();
   private EofSensor eofSensor = null;
   private SessionInputBuffer inbuffer = null;
   private HttpConnectionMetricsImpl metrics = null;
   private SessionOutputBuffer outbuffer = null;
   private HttpMessageParser requestParser = null;
   private HttpMessageWriter responseWriter = null;

   protected abstract void assertOpen() throws IllegalStateException;

   protected HttpConnectionMetricsImpl createConnectionMetrics(HttpTransportMetrics var1, HttpTransportMetrics var2) {
      return new HttpConnectionMetricsImpl(var1, var2);
   }

   protected EntityDeserializer createEntityDeserializer() {
      return new EntityDeserializer(new LaxContentLengthStrategy());
   }

   protected EntitySerializer createEntitySerializer() {
      return new EntitySerializer(new StrictContentLengthStrategy());
   }

   protected HttpRequestFactory createHttpRequestFactory() {
      return new DefaultHttpRequestFactory();
   }

   protected HttpMessageParser createRequestParser(SessionInputBuffer var1, HttpRequestFactory var2, HttpParams var3) {
      return new HttpRequestParser(var1, (LineParser)null, var2, var3);
   }

   protected HttpMessageWriter createResponseWriter(SessionOutputBuffer var1, HttpParams var2) {
      return new HttpResponseWriter(var1, (LineFormatter)null, var2);
   }

   protected void doFlush() throws IOException {
      this.outbuffer.flush();
   }

   public void flush() throws IOException {
      this.assertOpen();
      this.doFlush();
   }

   public HttpConnectionMetrics getMetrics() {
      return this.metrics;
   }

   protected void init(SessionInputBuffer var1, SessionOutputBuffer var2, HttpParams var3) {
      if (var1 != null) {
         if (var2 != null) {
            this.inbuffer = var1;
            this.outbuffer = var2;
            if (var1 instanceof EofSensor) {
               this.eofSensor = (EofSensor)var1;
            }

            this.requestParser = this.createRequestParser(var1, this.createHttpRequestFactory(), var3);
            this.responseWriter = this.createResponseWriter(var2, var3);
            this.metrics = this.createConnectionMetrics(var1.getMetrics(), var2.getMetrics());
         } else {
            throw new IllegalArgumentException("Output session buffer may not be null");
         }
      } else {
         throw new IllegalArgumentException("Input session buffer may not be null");
      }
   }

   protected boolean isEof() {
      EofSensor var1 = this.eofSensor;
      boolean var2;
      if (var1 != null && var1.isEof()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isStale() {
      if (!this.isOpen()) {
         return true;
      } else if (this.isEof()) {
         return true;
      } else {
         try {
            this.inbuffer.isDataAvailable(1);
            boolean var1 = this.isEof();
            return var1;
         } catch (IOException var3) {
            return true;
         }
      }
   }

   public void receiveRequestEntity(HttpEntityEnclosingRequest var1) throws HttpException, IOException {
      if (var1 != null) {
         this.assertOpen();
         var1.setEntity(this.entitydeserializer.deserialize(this.inbuffer, var1));
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }

   public HttpRequest receiveRequestHeader() throws HttpException, IOException {
      this.assertOpen();
      HttpRequest var1 = (HttpRequest)this.requestParser.parse();
      this.metrics.incrementRequestCount();
      return var1;
   }

   public void sendResponseEntity(HttpResponse var1) throws HttpException, IOException {
      if (var1.getEntity() != null) {
         this.entityserializer.serialize(this.outbuffer, var1, var1.getEntity());
      }
   }

   public void sendResponseHeader(HttpResponse var1) throws HttpException, IOException {
      if (var1 != null) {
         this.assertOpen();
         this.responseWriter.write(var1);
         if (var1.getStatusLine().getStatusCode() >= 200) {
            this.metrics.incrementResponseCount();
         }

      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }
}
