package org.apache.http.conn.ssl;

import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.util.InetAddressUtils;

public abstract class AbstractVerifier implements X509HostnameVerifier {
   private static final String[] BAD_COUNTRY_2LDS;

   static {
      String[] var0 = new String[]{"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};
      BAD_COUNTRY_2LDS = var0;
      Arrays.sort(var0);
   }

   public static boolean acceptableCountryWildcard(String var0) {
      String[] var4 = var0.split("\\.");
      int var1 = var4.length;
      boolean var2 = true;
      boolean var3 = var2;
      if (var1 == 3) {
         if (var4[2].length() != 2) {
            var3 = var2;
         } else if (Arrays.binarySearch(BAD_COUNTRY_2LDS, var4[1]) < 0) {
            var3 = var2;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public static int countDots(String var0) {
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < var0.length(); var2 = var3) {
         var3 = var2;
         if (var0.charAt(var1) == '.') {
            var3 = var2 + 1;
         }

         ++var1;
      }

      return var2;
   }

   public static String[] getCNs(X509Certificate var0) {
      LinkedList var1 = new LinkedList();
      StringTokenizer var2 = new StringTokenizer(var0.getSubjectX500Principal().toString(), ",");

      while(var2.hasMoreTokens()) {
         String var4 = var2.nextToken();
         int var3 = var4.indexOf("CN=");
         if (var3 >= 0) {
            var1.add(var4.substring(var3 + 3));
         }
      }

      if (!var1.isEmpty()) {
         String[] var5 = new String[var1.size()];
         var1.toArray(var5);
         return var5;
      } else {
         return null;
      }
   }

   public static String[] getDNSSubjectAlts(X509Certificate var0) {
      return getSubjectAlts(var0, (String)null);
   }

   private static String[] getSubjectAlts(X509Certificate var0, String var1) {
      byte var2;
      if (isIPAddress(var1)) {
         var2 = 7;
      } else {
         var2 = 2;
      }

      LinkedList var6 = new LinkedList();

      Collection var5;
      try {
         var5 = var0.getSubjectAlternativeNames();
      } catch (CertificateParsingException var4) {
         Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", var4);
         var5 = null;
      }

      if (var5 != null) {
         Iterator var7 = var5.iterator();

         while(var7.hasNext()) {
            List var3 = (List)var7.next();
            if ((Integer)var3.get(0) == var2) {
               var6.add((String)var3.get(1));
            }
         }
      }

      if (!var6.isEmpty()) {
         String[] var8 = new String[var6.size()];
         var6.toArray(var8);
         return var8;
      } else {
         return null;
      }
   }

   private static boolean isIPAddress(String var0) {
      boolean var1;
      if (var0 == null || !InetAddressUtils.isIPv4Address(var0) && !InetAddressUtils.isIPv6Address(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public final void verify(String var1, X509Certificate var2) throws SSLException {
      this.verify(var1, getCNs(var2), getSubjectAlts(var2, var1));
   }

   public final void verify(String var1, SSLSocket var2) throws IOException {
      if (var1 != null) {
         SSLSession var3 = var2.getSession();
         SSLSession var4 = var3;
         if (var3 == null) {
            var2.getInputStream().available();
            var3 = var2.getSession();
            var4 = var3;
            if (var3 == null) {
               var2.startHandshake();
               var4 = var2.getSession();
            }
         }

         this.verify(var1, (X509Certificate)var4.getPeerCertificates()[0]);
      } else {
         throw new NullPointerException("host to verify is null");
      }
   }

   public final void verify(String var1, String[] var2, String[] var3, boolean var4) throws SSLException {
      LinkedList var5 = new LinkedList();
      if (var2 != null && var2.length > 0 && var2[0] != null) {
         var5.add(var2[0]);
      }

      int var7;
      if (var3 != null) {
         int var6 = var3.length;

         for(var7 = 0; var7 < var6; ++var7) {
            String var14 = var3[var7];
            if (var14 != null) {
               var5.add(var14);
            }
         }
      }

      StringBuilder var15;
      if (var5.isEmpty()) {
         var15 = new StringBuilder();
         var15.append("Certificate for <");
         var15.append(var1);
         var15.append("> doesn't contain CN or DNS subjectAlt");
         throw new SSLException(var15.toString());
      } else {
         var15 = new StringBuilder();
         String var16 = var1.trim().toLowerCase(Locale.ENGLISH);
         Iterator var18 = var5.iterator();
         boolean var8 = false;

         boolean var9;
         do {
            var9 = var8;
            if (!var18.hasNext()) {
               break;
            }

            String var10 = ((String)var18.next()).toLowerCase(Locale.ENGLISH);
            var15.append(" <");
            var15.append(var10);
            var15.append('>');
            if (var18.hasNext()) {
               var15.append(" OR");
            }

            String[] var11 = var10.split("\\.");
            var7 = var11.length;
            var8 = true;
            boolean var19;
            if (var7 >= 3 && var11[0].endsWith("*") && acceptableCountryWildcard(var10) && !isIPAddress(var1)) {
               var19 = true;
            } else {
               var19 = false;
            }

            if (var19) {
               if (var11[0].length() > 1) {
                  String var12 = var11[0].substring(0, var11.length - 2);
                  String var13 = var10.substring(var11[0].length());
                  String var20 = var16.substring(var12.length());
                  if (var16.startsWith(var12) && var20.endsWith(var13)) {
                     var9 = true;
                  } else {
                     var9 = false;
                  }
               } else {
                  var9 = var16.endsWith(var10.substring(1));
               }

               if (var9 && var4) {
                  if (countDots(var16) == countDots(var10)) {
                     var9 = var8;
                  } else {
                     var9 = false;
                  }
               }
            } else {
               var9 = var16.equals(var10);
            }

            var8 = var9;
         } while(!var9);

         if (!var9) {
            StringBuilder var17 = new StringBuilder();
            var17.append("hostname in certificate didn't match: <");
            var17.append(var1);
            var17.append("> !=");
            var17.append(var15);
            throw new SSLException(var17.toString());
         }
      }
   }

   public final boolean verify(String var1, SSLSession var2) {
      try {
         this.verify(var1, (X509Certificate)var2.getPeerCertificates()[0]);
         return true;
      } catch (SSLException var3) {
         return false;
      }
   }
}
