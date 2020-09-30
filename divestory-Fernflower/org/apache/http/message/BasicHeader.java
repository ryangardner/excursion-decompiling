package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeader implements Header, Cloneable, Serializable {
   private static final long serialVersionUID = -5427236326487562174L;
   private final String name;
   private final String value;

   public BasicHeader(String var1, String var2) {
      if (var1 != null) {
         this.name = var1;
         this.value = var2;
      } else {
         throw new IllegalArgumentException("Name may not be null");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public HeaderElement[] getElements() throws ParseException {
      String var1 = this.value;
      return var1 != null ? BasicHeaderValueParser.parseElements((String)var1, (HeaderValueParser)null) : new HeaderElement[0];
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      return BasicLineFormatter.DEFAULT.formatHeader((CharArrayBuffer)null, (Header)this).toString();
   }
}
