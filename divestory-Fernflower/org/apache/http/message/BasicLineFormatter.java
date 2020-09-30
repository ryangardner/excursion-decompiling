package org.apache.http.message;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.util.CharArrayBuffer;

public class BasicLineFormatter implements LineFormatter {
   public static final BasicLineFormatter DEFAULT = new BasicLineFormatter();

   public static final String formatHeader(Header var0, LineFormatter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DEFAULT;
      }

      return ((LineFormatter)var2).formatHeader((CharArrayBuffer)null, var0).toString();
   }

   public static final String formatProtocolVersion(ProtocolVersion var0, LineFormatter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DEFAULT;
      }

      return ((LineFormatter)var2).appendProtocolVersion((CharArrayBuffer)null, var0).toString();
   }

   public static final String formatRequestLine(RequestLine var0, LineFormatter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DEFAULT;
      }

      return ((LineFormatter)var2).formatRequestLine((CharArrayBuffer)null, var0).toString();
   }

   public static final String formatStatusLine(StatusLine var0, LineFormatter var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = DEFAULT;
      }

      return ((LineFormatter)var2).formatStatusLine((CharArrayBuffer)null, var0).toString();
   }

   public CharArrayBuffer appendProtocolVersion(CharArrayBuffer var1, ProtocolVersion var2) {
      if (var2 != null) {
         int var3 = this.estimateProtocolVersionLen(var2);
         if (var1 == null) {
            var1 = new CharArrayBuffer(var3);
         } else {
            var1.ensureCapacity(var3);
         }

         var1.append(var2.getProtocol());
         var1.append('/');
         var1.append(Integer.toString(var2.getMajor()));
         var1.append('.');
         var1.append(Integer.toString(var2.getMinor()));
         return var1;
      } else {
         throw new IllegalArgumentException("Protocol version may not be null");
      }
   }

   protected void doFormatHeader(CharArrayBuffer var1, Header var2) {
      String var3 = var2.getName();
      String var6 = var2.getValue();
      int var4 = var3.length() + 2;
      int var5 = var4;
      if (var6 != null) {
         var5 = var4 + var6.length();
      }

      var1.ensureCapacity(var5);
      var1.append(var3);
      var1.append(": ");
      if (var6 != null) {
         var1.append(var6);
      }

   }

   protected void doFormatRequestLine(CharArrayBuffer var1, RequestLine var2) {
      String var3 = var2.getMethod();
      String var4 = var2.getUri();
      var1.ensureCapacity(var3.length() + 1 + var4.length() + 1 + this.estimateProtocolVersionLen(var2.getProtocolVersion()));
      var1.append(var3);
      var1.append(' ');
      var1.append(var4);
      var1.append(' ');
      this.appendProtocolVersion(var1, var2.getProtocolVersion());
   }

   protected void doFormatStatusLine(CharArrayBuffer var1, StatusLine var2) {
      int var3 = this.estimateProtocolVersionLen(var2.getProtocolVersion()) + 1 + 3 + 1;
      String var4 = var2.getReasonPhrase();
      int var5 = var3;
      if (var4 != null) {
         var5 = var3 + var4.length();
      }

      var1.ensureCapacity(var5);
      this.appendProtocolVersion(var1, var2.getProtocolVersion());
      var1.append(' ');
      var1.append(Integer.toString(var2.getStatusCode()));
      var1.append(' ');
      if (var4 != null) {
         var1.append(var4);
      }

   }

   protected int estimateProtocolVersionLen(ProtocolVersion var1) {
      return var1.getProtocol().length() + 4;
   }

   public CharArrayBuffer formatHeader(CharArrayBuffer var1, Header var2) {
      if (var2 != null) {
         if (var2 instanceof FormattedHeader) {
            var1 = ((FormattedHeader)var2).getBuffer();
         } else {
            var1 = this.initBuffer(var1);
            this.doFormatHeader(var1, var2);
         }

         return var1;
      } else {
         throw new IllegalArgumentException("Header may not be null");
      }
   }

   public CharArrayBuffer formatRequestLine(CharArrayBuffer var1, RequestLine var2) {
      if (var2 != null) {
         var1 = this.initBuffer(var1);
         this.doFormatRequestLine(var1, var2);
         return var1;
      } else {
         throw new IllegalArgumentException("Request line may not be null");
      }
   }

   public CharArrayBuffer formatStatusLine(CharArrayBuffer var1, StatusLine var2) {
      if (var2 != null) {
         var1 = this.initBuffer(var1);
         this.doFormatStatusLine(var1, var2);
         return var1;
      } else {
         throw new IllegalArgumentException("Status line may not be null");
      }
   }

   protected CharArrayBuffer initBuffer(CharArrayBuffer var1) {
      if (var1 != null) {
         var1.clear();
      } else {
         var1 = new CharArrayBuffer(64);
      }

      return var1;
   }
}
