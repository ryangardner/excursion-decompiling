package org.apache.harmony.awt.internal.nls;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
   private static ResourceBundle bundle;

   static {
      label20:
      try {
         bundle = setLocale(Locale.getDefault(), "org.apache.harmony.awt.internal.nls.messages");
      } catch (Throwable var2) {
         var2.printStackTrace();
         break label20;
      }

   }

   public static String format(String var0, Object[] var1) {
      StringBuilder var2 = new StringBuilder(var0.length() + var1.length * 20);
      int var3 = var1.length;
      String[] var4 = new String[var3];
      byte var5 = 0;

      int var6;
      for(var6 = 0; var6 < var1.length; ++var6) {
         if (var1[var6] == null) {
            var4[var6] = "<null>";
         } else {
            var4[var6] = var1[var6].toString();
         }
      }

      var6 = var5;

      while(true) {
         while(true) {
            int var7 = var0.indexOf(123, var6);
            if (var7 < 0) {
               if (var6 < var0.length()) {
                  var2.append(var0.substring(var6, var0.length()));
               }

               return var2.toString();
            }

            int var9;
            if (var7 != 0) {
               var9 = var7 - 1;
               if (var0.charAt(var9) == '\\') {
                  if (var7 != 1) {
                     var2.append(var0.substring(var6, var9));
                  }

                  var2.append('{');
                  var6 = var7 + 1;
                  continue;
               }
            }

            if (var7 > var0.length() - 3) {
               var2.append(var0.substring(var6, var0.length()));
               var6 = var0.length();
            } else {
               var9 = var7 + 1;
               byte var8 = (byte)Character.digit(var0.charAt(var9), 10);
               if (var8 >= 0 && var0.charAt(var7 + 2) == '}') {
                  var2.append(var0.substring(var6, var7));
                  if (var8 >= var3) {
                     var2.append("<missing argument>");
                  } else {
                     var2.append(var4[var8]);
                  }

                  var6 = var7 + 3;
               } else {
                  var2.append(var0.substring(var6, var9));
                  var6 = var9;
               }
            }
         }
      }
   }

   public static String getString(String var0) {
      ResourceBundle var1 = bundle;
      if (var1 == null) {
         return var0;
      } else {
         try {
            String var4 = var1.getString(var0);
            return var4;
         } catch (MissingResourceException var2) {
            StringBuilder var3 = new StringBuilder("Missing message: ");
            var3.append(var0);
            return var3.toString();
         }
      }
   }

   public static String getString(String var0, char var1) {
      return getString(var0, new Object[]{String.valueOf(var1)});
   }

   public static String getString(String var0, int var1) {
      return getString(var0, new Object[]{Integer.toString(var1)});
   }

   public static String getString(String var0, Object var1) {
      return getString(var0, new Object[]{var1});
   }

   public static String getString(String var0, Object var1, Object var2) {
      return getString(var0, new Object[]{var1, var2});
   }

   public static String getString(String var0, Object[] var1) {
      ResourceBundle var2 = bundle;
      String var3 = var0;
      if (var2 != null) {
         try {
            var3 = var2.getString(var0);
         } catch (MissingResourceException var4) {
            var3 = var0;
         }
      }

      return format(var3, var1);
   }

   public static ResourceBundle setLocale(final Locale var0, final String var1) {
      try {
         PrivilegedAction var2 = new PrivilegedAction<Object>((ClassLoader)null) {
            // $FF: synthetic field
            private final ClassLoader val$loader;

            {
               this.val$loader = var3;
            }

            public Object run() {
               String var1x = var1;
               Locale var2 = var0;
               ClassLoader var3 = this.val$loader;
               if (var3 == null) {
                  var3 = ClassLoader.getSystemClassLoader();
               }

               return ResourceBundle.getBundle(var1x, var2, var3);
            }
         };
         ResourceBundle var4 = (ResourceBundle)AccessController.doPrivileged(var2);
         return var4;
      } catch (MissingResourceException var3) {
         return null;
      }
   }
}
