package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

public class BasicHeaderElement implements HeaderElement, Cloneable {
   private final String name;
   private final NameValuePair[] parameters;
   private final String value;

   public BasicHeaderElement(String var1, String var2) {
      this(var1, var2, (NameValuePair[])null);
   }

   public BasicHeaderElement(String var1, String var2, NameValuePair[] var3) {
      if (var1 != null) {
         this.name = var1;
         this.value = var2;
         if (var3 != null) {
            this.parameters = var3;
         } else {
            this.parameters = new NameValuePair[0];
         }

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
      } else if (!(var1 instanceof HeaderElement)) {
         return false;
      } else {
         BasicHeaderElement var3 = (BasicHeaderElement)var1;
         if (!this.name.equals(var3.name) || !LangUtils.equals((Object)this.value, (Object)var3.value) || !LangUtils.equals((Object[])this.parameters, (Object[])var3.parameters)) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getName() {
      return this.name;
   }

   public NameValuePair getParameter(int var1) {
      return this.parameters[var1];
   }

   public NameValuePair getParameterByName(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Name may not be null");
      } else {
         Object var2 = null;
         int var3 = 0;

         NameValuePair var5;
         while(true) {
            NameValuePair[] var4 = this.parameters;
            var5 = (NameValuePair)var2;
            if (var3 >= var4.length) {
               break;
            }

            var5 = var4[var3];
            if (var5.getName().equalsIgnoreCase(var1)) {
               break;
            }

            ++var3;
         }

         return var5;
      }
   }

   public int getParameterCount() {
      return this.parameters.length;
   }

   public NameValuePair[] getParameters() {
      return (NameValuePair[])this.parameters.clone();
   }

   public String getValue() {
      return this.value;
   }

   public int hashCode() {
      int var1 = LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.value);
      int var2 = 0;

      while(true) {
         NameValuePair[] var3 = this.parameters;
         if (var2 >= var3.length) {
            return var1;
         }

         var1 = LangUtils.hashCode(var1, var3[var2]);
         ++var2;
      }
   }

   public String toString() {
      CharArrayBuffer var1 = new CharArrayBuffer(64);
      var1.append(this.name);
      if (this.value != null) {
         var1.append("=");
         var1.append(this.value);
      }

      for(int var2 = 0; var2 < this.parameters.length; ++var2) {
         var1.append("; ");
         var1.append((Object)this.parameters[var2]);
      }

      return var1.toString();
   }
}
