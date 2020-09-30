package org.apache.http.impl.entity;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.ChunkedOutputStream;
import org.apache.http.impl.io.ContentLengthOutputStream;
import org.apache.http.impl.io.IdentityOutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class EntitySerializer {
   private final ContentLengthStrategy lenStrategy;

   public EntitySerializer(ContentLengthStrategy var1) {
      if (var1 != null) {
         this.lenStrategy = var1;
      } else {
         throw new IllegalArgumentException("Content length strategy may not be null");
      }
   }

   protected OutputStream doSerialize(SessionOutputBuffer var1, HttpMessage var2) throws HttpException, IOException {
      long var3 = this.lenStrategy.determineLength(var2);
      if (var3 == -2L) {
         return new ChunkedOutputStream(var1);
      } else {
         return (OutputStream)(var3 == -1L ? new IdentityOutputStream(var1) : new ContentLengthOutputStream(var1, var3));
      }
   }

   public void serialize(SessionOutputBuffer var1, HttpMessage var2, HttpEntity var3) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               OutputStream var4 = this.doSerialize(var1, var2);
               var3.writeTo(var4);
               var4.close();
            } else {
               throw new IllegalArgumentException("HTTP entity may not be null");
            }
         } else {
            throw new IllegalArgumentException("HTTP message may not be null");
         }
      } else {
         throw new IllegalArgumentException("Session output buffer may not be null");
      }
   }
}
