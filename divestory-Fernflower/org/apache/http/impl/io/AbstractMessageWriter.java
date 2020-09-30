package org.apache.http.impl.io;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractMessageWriter implements HttpMessageWriter {
   protected final CharArrayBuffer lineBuf;
   protected final LineFormatter lineFormatter;
   protected final SessionOutputBuffer sessionBuffer;

   public AbstractMessageWriter(SessionOutputBuffer var1, LineFormatter var2, HttpParams var3) {
      if (var1 != null) {
         this.sessionBuffer = var1;
         this.lineBuf = new CharArrayBuffer(128);
         if (var2 == null) {
            var2 = BasicLineFormatter.DEFAULT;
         }

         this.lineFormatter = (LineFormatter)var2;
      } else {
         throw new IllegalArgumentException("Session input buffer may not be null");
      }
   }

   public void write(HttpMessage var1) throws IOException, HttpException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP message may not be null");
      } else {
         this.writeHeadLine(var1);
         HeaderIterator var2 = var1.headerIterator();

         while(var2.hasNext()) {
            Header var3 = (Header)var2.next();
            this.sessionBuffer.writeLine(this.lineFormatter.formatHeader(this.lineBuf, var3));
         }

         this.lineBuf.clear();
         this.sessionBuffer.writeLine(this.lineBuf);
      }
   }

   protected abstract void writeHeadLine(HttpMessage var1) throws IOException;
}
