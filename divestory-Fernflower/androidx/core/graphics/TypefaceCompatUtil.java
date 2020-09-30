package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class TypefaceCompatUtil {
   private static final String CACHE_FILE_PREFIX = ".font";
   private static final String TAG = "TypefaceCompatUtil";

   private TypefaceCompatUtil() {
   }

   public static void closeQuietly(Closeable var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
         }
      }

   }

   public static ByteBuffer copyToDirectBuffer(Context var0, Resources var1, int var2) {
      File var10 = getTempFile(var0);
      if (var10 == null) {
         return null;
      } else {
         Throwable var10000;
         label84: {
            boolean var10001;
            boolean var3;
            try {
               var3 = copyToFile(var10, var1, var2);
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label84;
            }

            if (!var3) {
               var10.delete();
               return null;
            }

            ByteBuffer var12;
            try {
               var12 = mmap(var10);
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label84;
            }

            var10.delete();
            return var12;
         }

         Throwable var11 = var10000;
         var10.delete();
         throw var11;
      }
   }

   public static boolean copyToFile(File var0, Resources var1, int var2) {
      InputStream var10;
      try {
         var10 = var1.openRawResource(var2);
      } finally {
         ;
      }

      try {
         copyToFile(var0, var10);
      } finally {
         closeQuietly(var10);
         throw var0;
      }

   }

   public static boolean copyToFile(File param0, InputStream param1) {
      // $FF: Couldn't be decompiled
   }

   public static File getTempFile(Context var0) {
      File var6 = var0.getCacheDir();
      if (var6 == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append(".font");
         var1.append(Process.myPid());
         var1.append("-");
         var1.append(Process.myTid());
         var1.append("-");
         String var7 = var1.toString();

         for(int var2 = 0; var2 < 100; ++var2) {
            StringBuilder var3 = new StringBuilder();
            var3.append(var7);
            var3.append(var2);
            File var8 = new File(var6, var3.toString());

            boolean var4;
            try {
               var4 = var8.createNewFile();
            } catch (IOException var5) {
               continue;
            }

            if (var4) {
               return var8;
            }
         }

         return null;
      }
   }

   public static ByteBuffer mmap(Context var0, CancellationSignal var1, Uri var2) {
      ContentResolver var101 = var0.getContentResolver();

      ParcelFileDescriptor var102;
      boolean var10001;
      try {
         var102 = var101.openFileDescriptor(var2, "r", var1);
      } catch (IOException var100) {
         var10001 = false;
         return null;
      }

      if (var102 == null) {
         if (var102 != null) {
            try {
               var102.close();
            } catch (IOException var93) {
               var10001 = false;
               return null;
            }
         }

         return null;
      } else {
         MappedByteBuffer var108;
         label693: {
            Throwable var105;
            Throwable var10000;
            label694: {
               FileInputStream var104;
               try {
                  var104 = new FileInputStream(var102.getFileDescriptor());
               } catch (Throwable var99) {
                  var10000 = var99;
                  var10001 = false;
                  break label694;
               }

               try {
                  FileChannel var107 = var104.getChannel();
                  long var3 = var107.size();
                  var108 = var107.map(MapMode.READ_ONLY, 0L, var3);
               } catch (Throwable var98) {
                  Throwable var106 = var98;

                  try {
                     var104.close();
                  } catch (Throwable var96) {
                     var105 = var96;

                     label668:
                     try {
                        var106.addSuppressed(var105);
                        break label668;
                     } catch (Throwable var95) {
                        var10000 = var95;
                        var10001 = false;
                        break label694;
                     }
                  }

                  try {
                     throw var106;
                  } catch (Throwable var92) {
                     var10000 = var92;
                     var10001 = false;
                     break label694;
                  }
               }

               label676:
               try {
                  var104.close();
                  break label693;
               } catch (Throwable var97) {
                  var10000 = var97;
                  var10001 = false;
                  break label676;
               }
            }

            var105 = var10000;
            if (var102 != null) {
               try {
                  var102.close();
               } catch (Throwable var91) {
                  Throwable var103 = var91;

                  label650:
                  try {
                     var105.addSuppressed(var103);
                     break label650;
                  } catch (IOException var90) {
                     var10001 = false;
                     return null;
                  }
               }
            }

            try {
               throw var105;
            } catch (IOException var89) {
               var10001 = false;
               return null;
            }
         }

         if (var102 != null) {
            try {
               var102.close();
            } catch (IOException var94) {
               var10001 = false;
               return null;
            }
         }

         return var108;
      }
   }

   private static ByteBuffer mmap(File var0) {
      FileInputStream var1;
      boolean var10001;
      try {
         var1 = new FileInputStream(var0);
      } catch (IOException var21) {
         var10001 = false;
         return null;
      }

      MappedByteBuffer var24;
      try {
         FileChannel var23 = var1.getChannel();
         long var2 = var23.size();
         var24 = var23.map(MapMode.READ_ONLY, 0L, var2);
      } catch (Throwable var20) {
         Throwable var22 = var20;

         try {
            var1.close();
         } catch (Throwable var18) {
            Throwable var25 = var18;

            label101:
            try {
               var22.addSuppressed(var25);
               break label101;
            } catch (IOException var17) {
               var10001 = false;
               return null;
            }
         }

         try {
            throw var22;
         } catch (IOException var16) {
            var10001 = false;
            return null;
         }
      }

      try {
         var1.close();
         return var24;
      } catch (IOException var19) {
         var10001 = false;
         return null;
      }
   }
}
