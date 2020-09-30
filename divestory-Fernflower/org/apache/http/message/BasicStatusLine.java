package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.util.CharArrayBuffer;

public class BasicStatusLine implements StatusLine, Cloneable, Serializable {
   private static final long serialVersionUID = -2443303766890459269L;
   private final ProtocolVersion protoVersion;
   private final String reasonPhrase;
   private final int statusCode;

   public BasicStatusLine(ProtocolVersion var1, int var2, String var3) {
      if (var1 != null) {
         if (var2 >= 0) {
            this.protoVersion = var1;
            this.statusCode = var2;
            this.reasonPhrase = var3;
         } else {
            throw new IllegalArgumentException("Status code may not be negative.");
         }
      } else {
         throw new IllegalArgumentException("Protocol version may not be null.");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public ProtocolVersion getProtocolVersion() {
      return this.protoVersion;
   }

   public String getReasonPhrase() {
      return this.reasonPhrase;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public String toString() {
      return BasicLineFormatter.DEFAULT.formatStatusLine((CharArrayBuffer)null, (StatusLine)this).toString();
   }
}
