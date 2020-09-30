package javax.mail.internet;

import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;

public class NewsAddress extends Address {
   private static final long serialVersionUID = -4203797299824684143L;
   protected String host;
   protected String newsgroup;

   public NewsAddress() {
   }

   public NewsAddress(String var1) {
      this(var1, (String)null);
   }

   public NewsAddress(String var1, String var2) {
      this.newsgroup = var1;
      this.host = var2;
   }

   public static NewsAddress[] parse(String var0) throws AddressException {
      StringTokenizer var1 = new StringTokenizer(var0, ",");
      Vector var3 = new Vector();

      while(var1.hasMoreTokens()) {
         var3.addElement(new NewsAddress(var1.nextToken()));
      }

      int var2 = var3.size();
      NewsAddress[] var4 = new NewsAddress[var2];
      if (var2 > 0) {
         var3.copyInto(var4);
      }

      return var4;
   }

   public static String toString(Address[] var0) {
      if (var0 != null && var0.length != 0) {
         StringBuffer var1 = new StringBuffer(((NewsAddress)var0[0]).toString());

         for(int var2 = 1; var2 < var0.length; ++var2) {
            var1.append(",");
            var1.append(((NewsAddress)var0[var2]).toString());
         }

         return var1.toString();
      } else {
         return null;
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof NewsAddress)) {
         return false;
      } else {
         NewsAddress var2 = (NewsAddress)var1;
         if (this.newsgroup.equals(var2.newsgroup)) {
            if (this.host == null && var2.host == null) {
               return true;
            }

            String var3 = this.host;
            if (var3 != null) {
               String var4 = var2.host;
               if (var4 != null && var3.equalsIgnoreCase(var4)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public String getHost() {
      return this.host;
   }

   public String getNewsgroup() {
      return this.newsgroup;
   }

   public String getType() {
      return "news";
   }

   public int hashCode() {
      String var1 = this.newsgroup;
      int var2 = 0;
      if (var1 != null) {
         var2 = 0 + var1.hashCode();
      }

      var1 = this.host;
      int var3 = var2;
      if (var1 != null) {
         var3 = var2 + var1.toLowerCase(Locale.ENGLISH).hashCode();
      }

      return var3;
   }

   public void setHost(String var1) {
      this.host = var1;
   }

   public void setNewsgroup(String var1) {
      this.newsgroup = var1;
   }

   public String toString() {
      return this.newsgroup;
   }
}
