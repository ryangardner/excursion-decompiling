package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Stack;
import org.apache.http.HttpHost;

public class URIUtils {
   private URIUtils() {
   }

   public static URI createURI(String var0, String var1, int var2, String var3, String var4, String var5) throws URISyntaxException {
      StringBuilder var6 = new StringBuilder();
      if (var1 != null) {
         if (var0 != null) {
            var6.append(var0);
            var6.append("://");
         }

         var6.append(var1);
         if (var2 > 0) {
            var6.append(':');
            var6.append(var2);
         }
      }

      if (var3 == null || !var3.startsWith("/")) {
         var6.append('/');
      }

      if (var3 != null) {
         var6.append(var3);
      }

      if (var4 != null) {
         var6.append('?');
         var6.append(var4);
      }

      if (var5 != null) {
         var6.append('#');
         var6.append(var5);
      }

      return new URI(var6.toString());
   }

   public static HttpHost extractHost(URI var0) {
      Object var1 = null;
      if (var0 == null) {
         return null;
      } else {
         HttpHost var2 = (HttpHost)var1;
         if (var0.isAbsolute()) {
            int var3 = var0.getPort();
            String var9 = var0.getHost();
            int var4 = var3;
            String var5 = var9;
            if (var9 == null) {
               String var6 = var0.getAuthority();
               var4 = var3;
               var5 = var6;
               if (var6 != null) {
                  int var7 = var6.indexOf(64);
                  var9 = var6;
                  if (var7 >= 0) {
                     var4 = var6.length();
                     ++var7;
                     if (var4 > var7) {
                        var9 = var6.substring(var7);
                     } else {
                        var9 = null;
                     }
                  }

                  var4 = var3;
                  var5 = var9;
                  if (var9 != null) {
                     var7 = var9.indexOf(58);
                     var4 = var3;
                     var5 = var9;
                     if (var7 >= 0) {
                        var4 = var7 + 1;
                        if (var4 < var9.length()) {
                           var3 = Integer.parseInt(var9.substring(var4));
                        }

                        var5 = var9.substring(0, var7);
                        var4 = var3;
                     }
                  }
               }
            }

            String var8 = var0.getScheme();
            var2 = (HttpHost)var1;
            if (var5 != null) {
               var2 = new HttpHost(var5, var4, var8);
            }
         }

         return var2;
      }
   }

   private static String normalizePath(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1;
         for(var1 = 0; var1 < var0.length() && var0.charAt(var1) == '/'; ++var1) {
         }

         String var2 = var0;
         if (var1 > 1) {
            var2 = var0.substring(var1 - 1);
         }

         return var2;
      }
   }

   private static URI removeDotSegments(URI var0) {
      String var1 = var0.getPath();
      if (var1 != null && var1.indexOf("/.") != -1) {
         String[] var6 = var1.split("/");
         Stack var2 = new Stack();

         for(int var3 = 0; var3 < var6.length; ++var3) {
            if (var6[var3].length() != 0 && !".".equals(var6[var3])) {
               if ("..".equals(var6[var3])) {
                  if (!var2.isEmpty()) {
                     var2.pop();
                  }
               } else {
                  var2.push(var6[var3]);
               }
            }
         }

         StringBuilder var7 = new StringBuilder();
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            String var8 = (String)var4.next();
            var7.append('/');
            var7.append(var8);
         }

         try {
            var0 = new URI(var0.getScheme(), var0.getAuthority(), var7.toString(), var0.getQuery(), var0.getFragment());
            return var0;
         } catch (URISyntaxException var5) {
            throw new IllegalArgumentException(var5);
         }
      } else {
         return var0;
      }
   }

   public static URI resolve(URI var0, String var1) {
      return resolve(var0, URI.create(var1));
   }

   public static URI resolve(URI var0, URI var1) {
      if (var0 != null) {
         if (var1 != null) {
            String var2 = var1.toString();
            if (var2.startsWith("?")) {
               return resolveReferenceStartingWithQueryString(var0, var1);
            } else {
               boolean var3;
               if (var2.length() == 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var3) {
                  var1 = URI.create("#");
               }

               var1 = var0.resolve(var1);
               var0 = var1;
               if (var3) {
                  String var4 = var1.toString();
                  var0 = URI.create(var4.substring(0, var4.indexOf(35)));
               }

               return removeDotSegments(var0);
            }
         } else {
            throw new IllegalArgumentException("Reference URI may nor be null");
         }
      } else {
         throw new IllegalArgumentException("Base URI may nor be null");
      }
   }

   private static URI resolveReferenceStartingWithQueryString(URI var0, URI var1) {
      String var2 = var0.toString();
      String var3 = var2;
      if (var2.indexOf(63) > -1) {
         var3 = var2.substring(0, var2.indexOf(63));
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var3);
      var4.append(var1.toString());
      return URI.create(var4.toString());
   }

   public static URI rewriteURI(URI var0, HttpHost var1) throws URISyntaxException {
      return rewriteURI(var0, var1, false);
   }

   public static URI rewriteURI(URI var0, HttpHost var1, boolean var2) throws URISyntaxException {
      if (var0 != null) {
         String var3 = null;
         String var4 = null;
         String var8;
         String var9;
         if (var1 != null) {
            String var5 = var1.getSchemeName();
            var3 = var1.getHostName();
            int var6 = var1.getPort();
            String var7 = normalizePath(var0.getRawPath());
            var9 = var0.getRawQuery();
            if (var2) {
               var8 = var4;
            } else {
               var8 = var0.getRawFragment();
            }

            return createURI(var5, var3, var6, var7, var9, var8);
         } else {
            var9 = normalizePath(var0.getRawPath());
            var4 = var0.getRawQuery();
            if (var2) {
               var8 = var3;
            } else {
               var8 = var0.getRawFragment();
            }

            return createURI((String)null, (String)null, -1, var9, var4, var8);
         }
      } else {
         throw new IllegalArgumentException("URI may nor be null");
      }
   }
}
