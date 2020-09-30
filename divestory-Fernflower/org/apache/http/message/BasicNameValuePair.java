package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

public class BasicNameValuePair implements NameValuePair, Cloneable, Serializable {
   private static final long serialVersionUID = -6437800749411518984L;
   private final String name;
   private final String value;

   public BasicNameValuePair(String var1, String var2) {
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

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof NameValuePair)) {
         return false;
      } else {
         BasicNameValuePair var3 = (BasicNameValuePair)var1;
         if (!this.name.equals(var3.name) || !LangUtils.equals((Object)this.value, (Object)var3.value)) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public int hashCode() {
      return LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.value);
   }

   public String toString() {
      if (this.value == null) {
         return this.name;
      } else {
         CharArrayBuffer var1 = new CharArrayBuffer(this.name.length() + 1 + this.value.length());
         var1.append(this.name);
         var1.append("=");
         var1.append(this.value);
         return var1.toString();
      }
   }
}
