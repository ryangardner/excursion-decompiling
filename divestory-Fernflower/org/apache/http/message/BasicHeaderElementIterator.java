package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderElementIterator implements HeaderElementIterator {
   private CharArrayBuffer buffer;
   private HeaderElement currentElement;
   private ParserCursor cursor;
   private final HeaderIterator headerIt;
   private final HeaderValueParser parser;

   public BasicHeaderElementIterator(HeaderIterator var1) {
      this(var1, BasicHeaderValueParser.DEFAULT);
   }

   public BasicHeaderElementIterator(HeaderIterator var1, HeaderValueParser var2) {
      this.currentElement = null;
      this.buffer = null;
      this.cursor = null;
      if (var1 != null) {
         if (var2 != null) {
            this.headerIt = var1;
            this.parser = var2;
         } else {
            throw new IllegalArgumentException("Parser may not be null");
         }
      } else {
         throw new IllegalArgumentException("Header iterator may not be null");
      }
   }

   private void bufferHeaderValue() {
      this.cursor = null;
      this.buffer = null;

      while(this.headerIt.hasNext()) {
         Header var1 = this.headerIt.nextHeader();
         if (var1 instanceof FormattedHeader) {
            FormattedHeader var5 = (FormattedHeader)var1;
            this.buffer = var5.getBuffer();
            ParserCursor var4 = new ParserCursor(0, this.buffer.length());
            this.cursor = var4;
            var4.updatePos(var5.getValuePos());
            break;
         }

         String var2 = var1.getValue();
         if (var2 != null) {
            CharArrayBuffer var3 = new CharArrayBuffer(var2.length());
            this.buffer = var3;
            var3.append(var2);
            this.cursor = new ParserCursor(0, this.buffer.length());
            break;
         }
      }

   }

   private void parseNextElement() {
      label38:
      while(this.headerIt.hasNext() || this.cursor != null) {
         ParserCursor var1 = this.cursor;
         if (var1 == null || var1.atEnd()) {
            this.bufferHeaderValue();
         }

         if (this.cursor != null) {
            HeaderElement var2;
            do {
               if (this.cursor.atEnd()) {
                  if (this.cursor.atEnd()) {
                     this.cursor = null;
                     this.buffer = null;
                  }
                  continue label38;
               }

               var2 = this.parser.parseHeaderElement(this.buffer, this.cursor);
            } while(var2.getName().length() == 0 && var2.getValue() == null);

            this.currentElement = var2;
            return;
         }
      }

   }

   public boolean hasNext() {
      if (this.currentElement == null) {
         this.parseNextElement();
      }

      boolean var1;
      if (this.currentElement != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final Object next() throws NoSuchElementException {
      return this.nextElement();
   }

   public HeaderElement nextElement() throws NoSuchElementException {
      if (this.currentElement == null) {
         this.parseNextElement();
      }

      HeaderElement var1 = this.currentElement;
      if (var1 != null) {
         this.currentElement = null;
         return var1;
      } else {
         throw new NoSuchElementException("No more header elements available");
      }
   }

   public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Remove not supported");
   }
}
