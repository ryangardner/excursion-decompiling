package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {
   private static final Pattern V_SEP = Pattern.compile("[-_./;:]");

   protected VersionUtil() {
   }

   private static final void _close(Closeable var0) {
      try {
         var0.close();
      } catch (IOException var1) {
      }

   }

   @Deprecated
   public static Version mavenVersionFor(ClassLoader var0, String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("META-INF/maven/");
      var3.append(var1.replaceAll("\\.", "/"));
      var3.append("/");
      var3.append(var2);
      var3.append("/pom.properties");
      InputStream var8 = var0.getResourceAsStream(var3.toString());
      if (var8 != null) {
         Version var9;
         try {
            Properties var10 = new Properties();
            var10.load(var8);
            var1 = var10.getProperty("version");
            var2 = var10.getProperty("artifactId");
            var9 = parseVersion(var1, var10.getProperty("groupId"), var2);
         } catch (IOException var6) {
            return Version.unknownVersion();
         } finally {
            _close(var8);
         }

         return var9;
      } else {
         return Version.unknownVersion();
      }
   }

   public static Version packageVersionFor(Class<?> param0) {
      // $FF: Couldn't be decompiled
   }

   public static Version parseVersion(String var0, String var1, String var2) {
      if (var0 != null) {
         var0 = var0.trim();
         if (var0.length() > 0) {
            String[] var6 = V_SEP.split(var0);
            int var3 = parseVersionPart(var6[0]);
            int var4;
            if (var6.length > 1) {
               var4 = parseVersionPart(var6[1]);
            } else {
               var4 = 0;
            }

            int var5;
            if (var6.length > 2) {
               var5 = parseVersionPart(var6[2]);
            } else {
               var5 = 0;
            }

            if (var6.length > 3) {
               var0 = var6[3];
            } else {
               var0 = null;
            }

            return new Version(var3, var4, var5, var0, var1, var2);
         }
      }

      return Version.unknownVersion();
   }

   protected static int parseVersionPart(String var0) {
      int var1 = var0.length();
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         char var4 = var0.charAt(var2);
         if (var4 > '9' || var4 < '0') {
            break;
         }

         var3 = var3 * 10 + (var4 - 48);
      }

      return var3;
   }

   public static final void throwInternal() {
      throw new RuntimeException("Internal error: this code path should never get executed");
   }

   public static Version versionFor(Class<?> var0) {
      Version var1 = packageVersionFor(var0);
      Version var2 = var1;
      if (var1 == null) {
         var2 = Version.unknownVersion();
      }

      return var2;
   }

   @Deprecated
   public Version version() {
      return Version.unknownVersion();
   }
}
