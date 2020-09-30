package org.apache.commons.net.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public final class Util {
   public static final int DEFAULT_COPY_BUFFER_SIZE = 1024;

   private Util() {
   }

   public static void closeQuietly(Closeable var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
         }
      }

   }

   public static void closeQuietly(Socket var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
         }
      }

   }

   public static final long copyReader(Reader var0, Writer var1) throws CopyStreamException {
      return copyReader(var0, var1, 1024);
   }

   public static final long copyReader(Reader var0, Writer var1, int var2) throws CopyStreamException {
      return copyReader(var0, var1, var2, -1L, (CopyStreamListener)null);
   }

   public static final long copyReader(Reader var0, Writer var1, int var2, long var3, CopyStreamListener var5) throws CopyStreamException {
      if (var2 <= 0) {
         var2 = 1024;
      }

      char[] var6 = new char[var2];
      long var7 = 0L;

      while(true) {
         long var9 = var7;

         IOException var10000;
         label102: {
            boolean var10001;
            try {
               var2 = var0.read(var6);
            } catch (IOException var20) {
               var10000 = var20;
               var10001 = false;
               break label102;
            }

            if (var2 == -1) {
               break;
            }

            long var11;
            if (var2 == 0) {
               label103: {
                  var9 = var7;

                  try {
                     var2 = var0.read();
                  } catch (IOException var16) {
                     var10000 = var16;
                     var10001 = false;
                     break label103;
                  }

                  if (var2 < 0) {
                     break;
                  }

                  var9 = var7;

                  try {
                     var1.write(var2);
                  } catch (IOException var15) {
                     var10000 = var15;
                     var10001 = false;
                     break label103;
                  }

                  var9 = var7;

                  try {
                     var1.flush();
                  } catch (IOException var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label103;
                  }

                  var11 = var7 + 1L;
                  var7 = var11;
                  if (var5 == null) {
                     continue;
                  }

                  var9 = var11;

                  try {
                     var5.bytesTransferred(var11, 1, var3);
                  } catch (IOException var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label103;
                  }

                  var7 = var11;
               }
            } else {
               label104: {
                  var9 = var7;

                  try {
                     var1.write(var6, 0, var2);
                  } catch (IOException var19) {
                     var10000 = var19;
                     var10001 = false;
                     break label104;
                  }

                  var9 = var7;

                  try {
                     var1.flush();
                  } catch (IOException var18) {
                     var10000 = var18;
                     var10001 = false;
                     break label104;
                  }

                  var11 = var7 + (long)var2;
                  var7 = var11;
                  if (var5 == null) {
                     continue;
                  }

                  var9 = var11;

                  try {
                     var5.bytesTransferred(var11, var2, var3);
                  } catch (IOException var17) {
                     var10000 = var17;
                     var10001 = false;
                     break label104;
                  }

                  var7 = var11;
               }
            }
            continue;
         }

         IOException var21 = var10000;
         throw new CopyStreamException("IOException caught while copying.", var9, var21);
      }

      return var7;
   }

   public static final long copyStream(InputStream var0, OutputStream var1) throws CopyStreamException {
      return copyStream(var0, var1, 1024);
   }

   public static final long copyStream(InputStream var0, OutputStream var1, int var2) throws CopyStreamException {
      return copyStream(var0, var1, var2, -1L, (CopyStreamListener)null);
   }

   public static final long copyStream(InputStream var0, OutputStream var1, int var2, long var3, CopyStreamListener var5) throws CopyStreamException {
      return copyStream(var0, var1, var2, var3, var5, true);
   }

   public static final long copyStream(InputStream var0, OutputStream var1, int var2, long var3, CopyStreamListener var5, boolean var6) throws CopyStreamException {
      if (var2 <= 0) {
         var2 = 1024;
      }

      byte[] var7 = new byte[var2];
      long var8 = 0L;

      IOException var10000;
      IOException var20;
      label112: {
         while(true) {
            boolean var10001;
            try {
               var2 = var0.read(var7);
            } catch (IOException var19) {
               var10000 = var19;
               var10001 = false;
               break;
            }

            if (var2 == -1) {
               return var8;
            }

            long var10;
            if (var2 == 0) {
               try {
                  var2 = var0.read();
               } catch (IOException var16) {
                  var10000 = var16;
                  var10001 = false;
                  break;
               }

               if (var2 < 0) {
                  return var8;
               }

               try {
                  var1.write(var2);
               } catch (IOException var15) {
                  var10000 = var15;
                  var10001 = false;
                  break;
               }

               if (var6) {
                  try {
                     var1.flush();
                  } catch (IOException var14) {
                     var10000 = var14;
                     var10001 = false;
                     break;
                  }
               }

               var10 = var8 + 1L;
               var8 = var10;
               if (var5 != null) {
                  var8 = var10;

                  try {
                     var5.bytesTransferred(var10, 1, var3);
                  } catch (IOException var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label112;
                  }

                  var8 = var10;
               }
            } else {
               try {
                  var1.write(var7, 0, var2);
               } catch (IOException var18) {
                  var10000 = var18;
                  var10001 = false;
                  break;
               }

               if (var6) {
                  try {
                     var1.flush();
                  } catch (IOException var17) {
                     var10000 = var17;
                     var10001 = false;
                     break;
                  }
               }

               var10 = var8 + (long)var2;
               var8 = var10;
               if (var5 != null) {
                  var8 = var10;

                  try {
                     var5.bytesTransferred(var10, var2, var3);
                  } catch (IOException var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label112;
                  }

                  var8 = var10;
               }
            }
         }

         var20 = var10000;
         throw new CopyStreamException("IOException caught while copying.", var8, var20);
      }

      var20 = var10000;
      throw new CopyStreamException("IOException caught while copying.", var8, var20);
   }
}
