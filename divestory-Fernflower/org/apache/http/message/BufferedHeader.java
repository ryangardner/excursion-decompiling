package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.FormattedHeader;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.util.CharArrayBuffer;

public class BufferedHeader implements FormattedHeader, Cloneable, Serializable {
   private static final long serialVersionUID = -2768352615787625448L;
   private final CharArrayBuffer buffer;
   private final String name;
   private final int valuePos;

   public BufferedHeader(CharArrayBuffer var1) throws ParseException {
      if (var1 != null) {
         int var2 = var1.indexOf(58);
         StringBuffer var3;
         if (var2 != -1) {
            String var4 = var1.substringTrimmed(0, var2);
            if (var4.length() != 0) {
               this.buffer = var1;
               this.name = var4;
               this.valuePos = var2 + 1;
            } else {
               var3 = new StringBuffer();
               var3.append("Invalid header: ");
               var3.append(var1.toString());
               throw new ParseException(var3.toString());
            }
         } else {
            var3 = new StringBuffer();
            var3.append("Invalid header: ");
            var3.append(var1.toString());
            throw new ParseException(var3.toString());
         }
      } else {
         throw new IllegalArgumentException("Char array buffer may not be null");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public CharArrayBuffer getBuffer() {
      return this.buffer;
   }

   public HeaderElement[] getElements() throws ParseException {
      ParserCursor var1 = new ParserCursor(0, this.buffer.length());
      var1.updatePos(this.valuePos);
      return BasicHeaderValueParser.DEFAULT.parseElements(this.buffer, var1);
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      CharArrayBuffer var1 = this.buffer;
      return var1.substringTrimmed(this.valuePos, var1.length());
   }

   public int getValuePos() {
      return this.valuePos;
   }

   public String toString() {
      return this.buffer.toString();
   }
}
