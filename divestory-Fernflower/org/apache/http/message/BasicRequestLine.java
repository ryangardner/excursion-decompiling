package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.util.CharArrayBuffer;

public class BasicRequestLine implements RequestLine, Cloneable, Serializable {
   private static final long serialVersionUID = 2810581718468737193L;
   private final String method;
   private final ProtocolVersion protoversion;
   private final String uri;

   public BasicRequestLine(String var1, String var2, ProtocolVersion var3) {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               this.method = var1;
               this.uri = var2;
               this.protoversion = var3;
            } else {
               throw new IllegalArgumentException("Protocol version must not be null.");
            }
         } else {
            throw new IllegalArgumentException("URI must not be null.");
         }
      } else {
         throw new IllegalArgumentException("Method must not be null.");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public String getMethod() {
      return this.method;
   }

   public ProtocolVersion getProtocolVersion() {
      return this.protoversion;
   }

   public String getUri() {
      return this.uri;
   }

   public String toString() {
      return BasicLineFormatter.DEFAULT.formatRequestLine((CharArrayBuffer)null, (RequestLine)this).toString();
   }
}
