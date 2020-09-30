package javax.activation;

import java.io.File;

public abstract class FileTypeMap {
   private static FileTypeMap defaultMap;

   public static FileTypeMap getDefaultFileTypeMap() {
      if (defaultMap == null) {
         defaultMap = new MimetypesFileTypeMap();
      }

      return defaultMap;
   }

   public static void setDefaultFileTypeMap(FileTypeMap var0) {
      SecurityManager var1 = System.getSecurityManager();
      if (var1 != null) {
         try {
            var1.checkSetFactory();
         } catch (SecurityException var2) {
            if (FileTypeMap.class.getClassLoader() != var0.getClass().getClassLoader()) {
               throw var2;
            }
         }
      }

      defaultMap = var0;
   }

   public abstract String getContentType(File var1);

   public abstract String getContentType(String var1);
}
