package org.apache.commons.net.ftp;

public class FTPFileFilters {
   public static final FTPFileFilter ALL = new FTPFileFilter() {
      public boolean accept(FTPFile var1) {
         return true;
      }
   };
   public static final FTPFileFilter DIRECTORIES = new FTPFileFilter() {
      public boolean accept(FTPFile var1) {
         boolean var2;
         if (var1 != null && var1.isDirectory()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   };
   public static final FTPFileFilter NON_NULL = new FTPFileFilter() {
      public boolean accept(FTPFile var1) {
         boolean var2;
         if (var1 != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   };
}
