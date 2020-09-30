package org.apache.http.impl.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractMessageParser implements HttpMessageParser {
   private static final int HEADERS = 1;
   private static final int HEAD_LINE = 0;
   private final List headerLines;
   protected final LineParser lineParser;
   private final int maxHeaderCount;
   private final int maxLineLen;
   private HttpMessage message;
   private final SessionInputBuffer sessionBuffer;
   private int state;

   public AbstractMessageParser(SessionInputBuffer var1, LineParser var2, HttpParams var3) {
      if (var1 != null) {
         if (var3 != null) {
            this.sessionBuffer = var1;
            this.maxHeaderCount = var3.getIntParameter("http.connection.max-header-count", -1);
            this.maxLineLen = var3.getIntParameter("http.connection.max-line-length", -1);
            if (var2 == null) {
               var2 = BasicLineParser.DEFAULT;
            }

            this.lineParser = (LineParser)var2;
            this.headerLines = new ArrayList();
            this.state = 0;
         } else {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         }
      } else {
         throw new IllegalArgumentException("Session input buffer may not be null");
      }
   }

   public static Header[] parseHeaders(SessionInputBuffer var0, int var1, int var2, LineParser var3) throws HttpException, IOException {
      Object var4 = var3;
      if (var3 == null) {
         var4 = BasicLineParser.DEFAULT;
      }

      return parseHeaders(var0, var1, var2, (LineParser)var4, new ArrayList());
   }

   public static Header[] parseHeaders(SessionInputBuffer var0, int var1, int var2, LineParser var3, List var4) throws HttpException, IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("Session input buffer may not be null");
      } else if (var3 == null) {
         throw new IllegalArgumentException("Line parser may not be null");
      } else if (var4 == null) {
         throw new IllegalArgumentException("Header line list may not be null");
      } else {
         CharArrayBuffer var5 = null;
         CharArrayBuffer var6 = var5;

         while(true) {
            CharArrayBuffer var7;
            if (var5 == null) {
               var7 = new CharArrayBuffer(64);
            } else {
               var5.clear();
               var7 = var5;
            }

            int var8 = var0.readLine(var7);
            byte var9 = 0;
            int var10 = 0;
            if (var8 == -1 || var7.length() < 1) {
               Header[] var13 = new Header[var4.size()];

               for(var1 = var9; var1 < var4.size(); ++var1) {
                  var7 = (CharArrayBuffer)var4.get(var1);

                  try {
                     var13[var1] = var3.parseHeader(var7);
                  } catch (ParseException var12) {
                     throw new ProtocolException(var12.getMessage());
                  }
               }

               return var13;
            }

            CharArrayBuffer var11;
            if ((var7.charAt(0) == ' ' || var7.charAt(0) == '\t') && var6 != null) {
               while(var10 < var7.length()) {
                  char var14 = var7.charAt(var10);
                  if (var14 != ' ' && var14 != '\t') {
                     break;
                  }

                  ++var10;
               }

               if (var2 > 0 && var6.length() + 1 + var7.length() - var10 > var2) {
                  throw new IOException("Maximum line length limit exceeded");
               }

               var6.append(' ');
               var6.append(var7, var10, var7.length() - var10);
               var11 = var6;
            } else {
               var4.add(var7);
               var5 = null;
               var11 = var7;
               var7 = var5;
            }

            var5 = var7;
            var6 = var11;
            if (var1 > 0) {
               if (var4.size() >= var1) {
                  throw new IOException("Maximum header count exceeded");
               }

               var5 = var7;
               var6 = var11;
            }
         }
      }
   }

   public HttpMessage parse() throws IOException, HttpException {
      int var1 = this.state;
      if (var1 != 0) {
         if (var1 != 1) {
            throw new IllegalStateException("Inconsistent parser state");
         }
      } else {
         try {
            this.message = this.parseHead(this.sessionBuffer);
         } catch (ParseException var3) {
            throw new ProtocolException(var3.getMessage(), var3);
         }

         this.state = 1;
      }

      Header[] var2 = parseHeaders(this.sessionBuffer, this.maxHeaderCount, this.maxLineLen, this.lineParser, this.headerLines);
      this.message.setHeaders(var2);
      HttpMessage var4 = this.message;
      this.message = null;
      this.headerLines.clear();
      this.state = 0;
      return var4;
   }

   protected abstract HttpMessage parseHead(SessionInputBuffer var1) throws IOException, HttpException, ParseException;
}
