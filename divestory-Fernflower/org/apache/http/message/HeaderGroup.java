package org.apache.http.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.util.CharArrayBuffer;

public class HeaderGroup implements Cloneable, Serializable {
   private static final long serialVersionUID = 2608834160639271617L;
   private final List headers = new ArrayList(16);

   public void addHeader(Header var1) {
      if (var1 != null) {
         this.headers.add(var1);
      }
   }

   public void clear() {
      this.headers.clear();
   }

   public Object clone() throws CloneNotSupportedException {
      HeaderGroup var1 = (HeaderGroup)super.clone();
      var1.headers.clear();
      var1.headers.addAll(this.headers);
      return var1;
   }

   public boolean containsHeader(String var1) {
      for(int var2 = 0; var2 < this.headers.size(); ++var2) {
         if (((Header)this.headers.get(var2)).getName().equalsIgnoreCase(var1)) {
            return true;
         }
      }

      return false;
   }

   public HeaderGroup copy() {
      HeaderGroup var1 = new HeaderGroup();
      var1.headers.addAll(this.headers);
      return var1;
   }

   public Header[] getAllHeaders() {
      List var1 = this.headers;
      return (Header[])var1.toArray(new Header[var1.size()]);
   }

   public Header getCondensedHeader(String var1) {
      Header[] var2 = this.getHeaders(var1);
      if (var2.length == 0) {
         return null;
      } else {
         int var3 = var2.length;
         int var4 = 1;
         if (var3 == 1) {
            return var2[0];
         } else {
            CharArrayBuffer var5 = new CharArrayBuffer(128);
            var5.append(var2[0].getValue());

            while(var4 < var2.length) {
               var5.append(", ");
               var5.append(var2[var4].getValue());
               ++var4;
            }

            return new BasicHeader(var1.toLowerCase(Locale.ENGLISH), var5.toString());
         }
      }
   }

   public Header getFirstHeader(String var1) {
      for(int var2 = 0; var2 < this.headers.size(); ++var2) {
         Header var3 = (Header)this.headers.get(var2);
         if (var3.getName().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public Header[] getHeaders(String var1) {
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < this.headers.size(); ++var3) {
         Header var4 = (Header)this.headers.get(var3);
         if (var4.getName().equalsIgnoreCase(var1)) {
            var2.add(var4);
         }
      }

      return (Header[])var2.toArray(new Header[var2.size()]);
   }

   public Header getLastHeader(String var1) {
      for(int var2 = this.headers.size() - 1; var2 >= 0; --var2) {
         Header var3 = (Header)this.headers.get(var2);
         if (var3.getName().equalsIgnoreCase(var1)) {
            return var3;
         }
      }

      return null;
   }

   public HeaderIterator iterator() {
      return new BasicListHeaderIterator(this.headers, (String)null);
   }

   public HeaderIterator iterator(String var1) {
      return new BasicListHeaderIterator(this.headers, var1);
   }

   public void removeHeader(Header var1) {
      if (var1 != null) {
         this.headers.remove(var1);
      }
   }

   public void setHeaders(Header[] var1) {
      this.clear();
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.headers.add(var1[var2]);
         }

      }
   }

   public String toString() {
      return this.headers.toString();
   }

   public void updateHeader(Header var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < this.headers.size(); ++var2) {
            if (((Header)this.headers.get(var2)).getName().equalsIgnoreCase(var1.getName())) {
               this.headers.set(var2, var1);
               return;
            }
         }

         this.headers.add(var1);
      }
   }
}
