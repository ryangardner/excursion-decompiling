package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Enumeration;

class SecuritySupport {
   private SecuritySupport() {
   }

   public static ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader var1;
            try {
               var1 = Thread.currentThread().getContextClassLoader();
            } catch (SecurityException var2) {
               var1 = null;
            }

            return var1;
         }
      });
   }

   public static InputStream getResourceAsStream(final Class var0, final String var1) throws IOException {
      try {
         PrivilegedExceptionAction var2 = new PrivilegedExceptionAction() {
            public Object run() throws IOException {
               return var0.getResourceAsStream(var1);
            }
         };
         InputStream var4 = (InputStream)AccessController.doPrivileged(var2);
         return var4;
      } catch (PrivilegedActionException var3) {
         throw (IOException)var3.getException();
      }
   }

   public static URL[] getResources(final ClassLoader var0, final String var1) {
      return (URL[])AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            URL[] var1x = (URL[])null;
            URL[] var2 = var1x;

            URL[] var15;
            label90: {
               boolean var10001;
               ArrayList var3;
               try {
                  var3 = new ArrayList;
               } catch (SecurityException | IOException var14) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1x;

               try {
                  var3.<init>();
               } catch (SecurityException | IOException var13) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1x;

               Enumeration var4;
               try {
                  var4 = var0.getResources(var1);
               } catch (SecurityException | IOException var11) {
                  var10001 = false;
                  break label90;
               }

               while(var4 != null) {
                  var2 = var1x;

                  try {
                     if (!var4.hasMoreElements()) {
                        break;
                     }
                  } catch (SecurityException | IOException var12) {
                     var10001 = false;
                     break label90;
                  }

                  var2 = var1x;

                  URL var5;
                  try {
                     var5 = (URL)var4.nextElement();
                  } catch (SecurityException | IOException var10) {
                     var10001 = false;
                     break label90;
                  }

                  if (var5 != null) {
                     var2 = var1x;

                     try {
                        var3.add(var5);
                     } catch (SecurityException | IOException var9) {
                        var10001 = false;
                        break label90;
                     }
                  }
               }

               var15 = var1x;
               var2 = var1x;

               try {
                  if (var3.size() <= 0) {
                     return var15;
                  }
               } catch (SecurityException | IOException var8) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1x;

               try {
                  var15 = new URL[var3.size()];
               } catch (SecurityException | IOException var7) {
                  var10001 = false;
                  break label90;
               }

               var2 = var15;

               try {
                  var15 = (URL[])var3.toArray(var15);
                  return var15;
               } catch (SecurityException | IOException var6) {
                  var10001 = false;
               }
            }

            var15 = var2;
            return var15;
         }
      });
   }

   public static URL[] getSystemResources(final String var0) {
      return (URL[])AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            URL[] var1 = (URL[])null;
            URL[] var2 = var1;

            URL[] var15;
            label90: {
               boolean var10001;
               ArrayList var3;
               try {
                  var3 = new ArrayList;
               } catch (SecurityException | IOException var14) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1;

               try {
                  var3.<init>();
               } catch (SecurityException | IOException var13) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1;

               Enumeration var4;
               try {
                  var4 = ClassLoader.getSystemResources(var0);
               } catch (SecurityException | IOException var11) {
                  var10001 = false;
                  break label90;
               }

               while(var4 != null) {
                  var2 = var1;

                  try {
                     if (!var4.hasMoreElements()) {
                        break;
                     }
                  } catch (SecurityException | IOException var12) {
                     var10001 = false;
                     break label90;
                  }

                  var2 = var1;

                  URL var5;
                  try {
                     var5 = (URL)var4.nextElement();
                  } catch (SecurityException | IOException var10) {
                     var10001 = false;
                     break label90;
                  }

                  if (var5 != null) {
                     var2 = var1;

                     try {
                        var3.add(var5);
                     } catch (SecurityException | IOException var9) {
                        var10001 = false;
                        break label90;
                     }
                  }
               }

               var15 = var1;
               var2 = var1;

               try {
                  if (var3.size() <= 0) {
                     return var15;
                  }
               } catch (SecurityException | IOException var8) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1;

               try {
                  var15 = new URL[var3.size()];
               } catch (SecurityException | IOException var7) {
                  var10001 = false;
                  break label90;
               }

               var2 = var15;

               try {
                  var15 = (URL[])var3.toArray(var15);
                  return var15;
               } catch (SecurityException | IOException var6) {
                  var10001 = false;
               }
            }

            var15 = var2;
            return var15;
         }
      });
   }

   public static InputStream openStream(final URL var0) throws IOException {
      try {
         PrivilegedExceptionAction var1 = new PrivilegedExceptionAction() {
            public Object run() throws IOException {
               return var0.openStream();
            }
         };
         InputStream var3 = (InputStream)AccessController.doPrivileged(var1);
         return var3;
      } catch (PrivilegedActionException var2) {
         throw (IOException)var2.getException();
      }
   }
}
