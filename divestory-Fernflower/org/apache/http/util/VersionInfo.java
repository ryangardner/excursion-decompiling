package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class VersionInfo {
   public static final String PROPERTY_MODULE = "info.module";
   public static final String PROPERTY_RELEASE = "info.release";
   public static final String PROPERTY_TIMESTAMP = "info.timestamp";
   public static final String UNAVAILABLE = "UNAVAILABLE";
   public static final String VERSION_PROPERTY_FILE = "version.properties";
   private final String infoClassloader;
   private final String infoModule;
   private final String infoPackage;
   private final String infoRelease;
   private final String infoTimestamp;

   protected VersionInfo(String var1, String var2, String var3, String var4, String var5) {
      if (var1 != null) {
         this.infoPackage = var1;
         if (var2 == null) {
            var2 = "UNAVAILABLE";
         }

         this.infoModule = var2;
         if (var3 == null) {
            var3 = "UNAVAILABLE";
         }

         this.infoRelease = var3;
         if (var4 == null) {
            var4 = "UNAVAILABLE";
         }

         this.infoTimestamp = var4;
         if (var5 == null) {
            var5 = "UNAVAILABLE";
         }

         this.infoClassloader = var5;
      } else {
         throw new IllegalArgumentException("Package identifier must not be null.");
      }
   }

   protected static final VersionInfo fromMap(String var0, Map var1, ClassLoader var2) {
      if (var0 == null) {
         throw new IllegalArgumentException("Package identifier must not be null.");
      } else {
         Object var3 = null;
         String var4;
         String var5;
         String var6;
         String var7;
         if (var1 != null) {
            var4 = (String)var1.get("info.module");
            var5 = var4;
            if (var4 != null) {
               var5 = var4;
               if (var4.length() < 1) {
                  var5 = null;
               }
            }

            var6 = (String)var1.get("info.release");
            var4 = var6;
            if (var6 != null) {
               label51: {
                  if (var6.length() >= 1) {
                     var4 = var6;
                     if (!var6.equals("${pom.version}")) {
                        break label51;
                     }
                  }

                  var4 = null;
               }
            }

            var7 = (String)var1.get("info.timestamp");
            if (var7 != null && (var7.length() < 1 || var7.equals("${mvn.timestamp}"))) {
               var7 = null;
            }

            var6 = var5;
            var5 = var4;
            var4 = var6;
            var6 = var7;
         } else {
            var4 = null;
            var6 = var4;
            var5 = var4;
         }

         var7 = (String)var3;
         if (var2 != null) {
            var7 = var2.toString();
         }

         return new VersionInfo(var0, var4, var5, var6, var7);
      }
   }

   public static final VersionInfo loadVersionInfo(String var0, ClassLoader var1) {
      if (var0 == null) {
         throw new IllegalArgumentException("Package identifier must not be null.");
      } else {
         ClassLoader var2 = var1;
         if (var1 == null) {
            var2 = Thread.currentThread().getContextClassLoader();
         }

         VersionInfo var3 = null;

         Properties var15;
         label106: {
            label104: {
               boolean var10001;
               InputStream var4;
               try {
                  StringBuffer var14 = new StringBuffer();
                  var14.append(var0.replace('.', '/'));
                  var14.append("/");
                  var14.append("version.properties");
                  var4 = var2.getResourceAsStream(var14.toString());
               } catch (IOException var13) {
                  var10001 = false;
                  break label104;
               }

               if (var4 != null) {
                  label107: {
                     boolean var9 = false;

                     try {
                        var9 = true;
                        var15 = new Properties();
                        var15.load(var4);
                        var9 = false;
                     } finally {
                        if (var9) {
                           try {
                              var4.close();
                           } catch (IOException var11) {
                              var10001 = false;
                              break label107;
                           }
                        }
                     }

                     try {
                        var4.close();
                     } catch (IOException var10) {
                     }
                     break label106;
                  }
               }
            }

            var15 = null;
         }

         if (var15 != null) {
            var3 = fromMap(var0, var15, var2);
         }

         return var3;
      }
   }

   public static final VersionInfo[] loadVersionInfo(String[] var0, ClassLoader var1) {
      if (var0 != null) {
         ArrayList var2 = new ArrayList(var0.length);

         for(int var3 = 0; var3 < var0.length; ++var3) {
            VersionInfo var4 = loadVersionInfo(var0[var3], var1);
            if (var4 != null) {
               var2.add(var4);
            }
         }

         return (VersionInfo[])var2.toArray(new VersionInfo[var2.size()]);
      } else {
         throw new IllegalArgumentException("Package identifier list must not be null.");
      }
   }

   public final String getClassloader() {
      return this.infoClassloader;
   }

   public final String getModule() {
      return this.infoModule;
   }

   public final String getPackage() {
      return this.infoPackage;
   }

   public final String getRelease() {
      return this.infoRelease;
   }

   public final String getTimestamp() {
      return this.infoTimestamp;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(this.infoPackage.length() + 20 + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
      var1.append("VersionInfo(");
      var1.append(this.infoPackage);
      var1.append(':');
      var1.append(this.infoModule);
      if (!"UNAVAILABLE".equals(this.infoRelease)) {
         var1.append(':');
         var1.append(this.infoRelease);
      }

      if (!"UNAVAILABLE".equals(this.infoTimestamp)) {
         var1.append(':');
         var1.append(this.infoTimestamp);
      }

      var1.append(')');
      if (!"UNAVAILABLE".equals(this.infoClassloader)) {
         var1.append('@');
         var1.append(this.infoClassloader);
      }

      return var1.toString();
   }
}
