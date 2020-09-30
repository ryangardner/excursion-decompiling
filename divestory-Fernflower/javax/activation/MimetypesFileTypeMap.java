package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MimeTypeFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class MimetypesFileTypeMap extends FileTypeMap {
   private static final int PROG = 0;
   private static MimeTypeFile defDB;
   private static String defaultType;
   private MimeTypeFile[] DB;

   public MimetypesFileTypeMap() {
      Vector var1 = new Vector(5);
      var1.addElement((Object)null);
      LogSupport.log("MimetypesFileTypeMap: load HOME");

      MimeTypeFile var37;
      boolean var10001;
      label339: {
         String var2;
         try {
            var2 = System.getProperty("user.home");
         } catch (SecurityException var35) {
            var10001 = false;
            break label339;
         }

         if (var2 != null) {
            label343: {
               try {
                  StringBuilder var3 = new StringBuilder(String.valueOf(var2));
                  var3.append(File.separator);
                  var3.append(".mime.types");
                  var37 = this.loadFile(var3.toString());
               } catch (SecurityException var34) {
                  var10001 = false;
                  break label343;
               }

               if (var37 != null) {
                  try {
                     var1.addElement(var37);
                  } catch (SecurityException var33) {
                     var10001 = false;
                  }
               }
            }
         }
      }

      LogSupport.log("MimetypesFileTypeMap: load SYS");

      label340: {
         try {
            StringBuilder var38 = new StringBuilder(String.valueOf(System.getProperty("java.home")));
            var38.append(File.separator);
            var38.append("lib");
            var38.append(File.separator);
            var38.append("mime.types");
            var37 = this.loadFile(var38.toString());
         } catch (SecurityException var32) {
            var10001 = false;
            break label340;
         }

         if (var37 != null) {
            try {
               var1.addElement(var37);
            } catch (SecurityException var31) {
               var10001 = false;
            }
         }
      }

      LogSupport.log("MimetypesFileTypeMap: load JAR");
      this.loadAllResources(var1, "mime.types");
      LogSupport.log("MimetypesFileTypeMap: load DEF");
      synchronized(MimetypesFileTypeMap.class){}

      label341: {
         Throwable var10000;
         label307: {
            try {
               if (defDB == null) {
                  defDB = this.loadResource("/mimetypes.default");
               }
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label307;
            }

            label304:
            try {
               break label341;
            } catch (Throwable var29) {
               var10000 = var29;
               var10001 = false;
               break label304;
            }
         }

         while(true) {
            Throwable var36 = var10000;

            try {
               throw var36;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               continue;
            }
         }
      }

      var37 = defDB;
      if (var37 != null) {
         var1.addElement(var37);
      }

      MimeTypeFile[] var39 = new MimeTypeFile[var1.size()];
      this.DB = var39;
      var1.copyInto(var39);
   }

   public MimetypesFileTypeMap(InputStream var1) {
      this();

      MimeTypeFile[] var2;
      MimeTypeFile var3;
      try {
         var2 = this.DB;
         var3 = new MimeTypeFile(var1);
      } catch (IOException var4) {
         return;
      }

      var2[0] = var3;
   }

   public MimetypesFileTypeMap(String var1) throws IOException {
      this();
      this.DB[0] = new MimeTypeFile(var1);
   }

   private void loadAllResources(Vector param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   private MimeTypeFile loadFile(String var1) {
      MimeTypeFile var2;
      MimeTypeFile var4;
      try {
         var2 = new MimeTypeFile(var1);
      } catch (IOException var3) {
         var4 = null;
         return var4;
      }

      var4 = var2;
      return var4;
   }

   private MimeTypeFile loadResource(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void addMimeTypes(String var1) {
      synchronized(this){}

      Throwable var10000;
      label77: {
         boolean var10001;
         label76: {
            MimeTypeFile[] var2;
            MimeTypeFile var3;
            try {
               if (this.DB[0] != null) {
                  break label76;
               }

               var2 = this.DB;
               var3 = new MimeTypeFile();
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label77;
            }

            var2[0] = var3;
         }

         label70:
         try {
            this.DB[0].appendToRegistry(var1);
            return;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label70;
         }
      }

      Throwable var10 = var10000;
      throw var10;
   }

   public String getContentType(File var1) {
      return this.getContentType(var1.getName());
   }

   public String getContentType(String var1) {
      synchronized(this){}

      Throwable var10000;
      label476: {
         int var2;
         boolean var10001;
         try {
            var2 = var1.lastIndexOf(".");
         } catch (Throwable var44) {
            var10000 = var44;
            var10001 = false;
            break label476;
         }

         if (var2 < 0) {
            label443: {
               try {
                  var1 = defaultType;
               } catch (Throwable var40) {
                  var10000 = var40;
                  var10001 = false;
                  break label443;
               }

               return var1;
            }
         } else {
            label479: {
               String var3;
               try {
                  var3 = var1.substring(var2 + 1);
                  if (var3.length() == 0) {
                     var1 = defaultType;
                     return var1;
                  }
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label479;
               }

               var2 = 0;

               while(true) {
                  label456: {
                     try {
                        if (var2 < this.DB.length) {
                           break label456;
                        }

                        var1 = defaultType;
                     } catch (Throwable var43) {
                        var10000 = var43;
                        var10001 = false;
                        break;
                     }

                     return var1;
                  }

                  label478: {
                     try {
                        if (this.DB[var2] == null) {
                           break label478;
                        }
                     } catch (Throwable var42) {
                        var10000 = var42;
                        var10001 = false;
                        break;
                     }

                     try {
                        var1 = this.DB[var2].getMIMETypeString(var3);
                     } catch (Throwable var41) {
                        var10000 = var41;
                        var10001 = false;
                        break;
                     }

                     if (var1 != null) {
                        return var1;
                     }
                  }

                  ++var2;
               }
            }
         }
      }

      Throwable var46 = var10000;
      throw var46;
   }
}
