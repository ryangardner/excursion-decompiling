package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Logger;

public class MultipartContent extends AbstractHttpContent {
   static final String NEWLINE = "\r\n";
   private static final String TWO_DASHES = "--";
   private ArrayList<MultipartContent.Part> parts;

   public MultipartContent() {
      StringBuilder var1 = new StringBuilder();
      var1.append("__END_OF_PART__");
      var1.append(UUID.randomUUID().toString());
      var1.append("__");
      this(var1.toString());
   }

   public MultipartContent(String var1) {
      super((new HttpMediaType("multipart/related")).setParameter("boundary", var1));
      this.parts = new ArrayList();
   }

   public MultipartContent addPart(MultipartContent.Part var1) {
      this.parts.add(Preconditions.checkNotNull(var1));
      return this;
   }

   public final String getBoundary() {
      return this.getMediaType().getParameter("boundary");
   }

   public final Collection<MultipartContent.Part> getParts() {
      return Collections.unmodifiableCollection(this.parts);
   }

   public boolean retrySupported() {
      Iterator var1 = this.parts.iterator();

      do {
         if (!var1.hasNext()) {
            return true;
         }
      } while(((MultipartContent.Part)var1.next()).content.retrySupported());

      return false;
   }

   public MultipartContent setBoundary(String var1) {
      this.getMediaType().setParameter("boundary", (String)Preconditions.checkNotNull(var1));
      return this;
   }

   public MultipartContent setContentParts(Collection<? extends HttpContent> var1) {
      this.parts = new ArrayList(var1.size());
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.addPart(new MultipartContent.Part((HttpContent)var2.next()));
      }

      return this;
   }

   public MultipartContent setMediaType(HttpMediaType var1) {
      super.setMediaType(var1);
      return this;
   }

   public MultipartContent setParts(Collection<MultipartContent.Part> var1) {
      this.parts = new ArrayList(var1);
      return this;
   }

   public void writeTo(OutputStream var1) throws IOException {
      OutputStreamWriter var2 = new OutputStreamWriter(var1, this.getCharset());
      String var3 = this.getBoundary();

      for(Iterator var4 = this.parts.iterator(); var4.hasNext(); var2.write("\r\n")) {
         MultipartContent.Part var5 = (MultipartContent.Part)var4.next();
         HttpHeaders var6 = (new HttpHeaders()).setAcceptEncoding((String)null);
         if (var5.headers != null) {
            var6.fromHttpHeaders(var5.headers);
         }

         var6.setContentEncoding((String)null).setUserAgent((String)null).setContentType((String)null).setContentLength((Long)null).set("Content-Transfer-Encoding", (Object)null);
         Object var7 = var5.content;
         Object var12;
         if (var7 != null) {
            var6.set("Content-Transfer-Encoding", Arrays.asList("binary"));
            var6.setContentType(((HttpContent)var7).getType());
            HttpEncoding var10 = var5.encoding;
            long var8;
            if (var10 == null) {
               var8 = ((HttpContent)var7).getLength();
            } else {
               var6.setContentEncoding(var10.getName());
               HttpEncodingStreamingContent var11 = new HttpEncodingStreamingContent((StreamingContent)var7, var10);
               var8 = AbstractHttpContent.computeLength((HttpContent)var7);
               var7 = var11;
            }

            var12 = var7;
            if (var8 != -1L) {
               var6.setContentLength(var8);
               var12 = var7;
            }
         } else {
            var12 = null;
         }

         var2.write("--");
         var2.write(var3);
         var2.write("\r\n");
         HttpHeaders.serializeHeadersForMultipartRequests(var6, (StringBuilder)null, (Logger)null, var2);
         if (var12 != null) {
            var2.write("\r\n");
            var2.flush();
            ((StreamingContent)var12).writeTo(var1);
         }
      }

      var2.write("--");
      var2.write(var3);
      var2.write("--");
      var2.write("\r\n");
      var2.flush();
   }

   public static final class Part {
      HttpContent content;
      HttpEncoding encoding;
      HttpHeaders headers;

      public Part() {
         this((HttpContent)null);
      }

      public Part(HttpContent var1) {
         this((HttpHeaders)null, var1);
      }

      public Part(HttpHeaders var1, HttpContent var2) {
         this.setHeaders(var1);
         this.setContent(var2);
      }

      public HttpContent getContent() {
         return this.content;
      }

      public HttpEncoding getEncoding() {
         return this.encoding;
      }

      public HttpHeaders getHeaders() {
         return this.headers;
      }

      public MultipartContent.Part setContent(HttpContent var1) {
         this.content = var1;
         return this;
      }

      public MultipartContent.Part setEncoding(HttpEncoding var1) {
         this.encoding = var1;
         return this;
      }

      public MultipartContent.Part setHeaders(HttpHeaders var1) {
         this.headers = var1;
         return this;
      }
   }
}
