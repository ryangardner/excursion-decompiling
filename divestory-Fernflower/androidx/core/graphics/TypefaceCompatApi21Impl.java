package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
   private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
   private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
   private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
   private static final String TAG = "TypefaceCompatApi21Impl";
   private static Method sAddFontWeightStyle;
   private static Method sCreateFromFamiliesWithDefault;
   private static Class<?> sFontFamily;
   private static Constructor<?> sFontFamilyCtor;
   private static boolean sHasInitBeenCalled;

   private static boolean addFontWeightStyle(Object var0, String var1, int var2, boolean var3) {
      init();

      try {
         var3 = (Boolean)sAddFontWeightStyle.invoke(var0, var1, var2, var3);
         return var3;
      } catch (IllegalAccessException var4) {
         var0 = var4;
      } catch (InvocationTargetException var5) {
         var0 = var5;
      }

      throw new RuntimeException((Throwable)var0);
   }

   private static Typeface createFromFamiliesWithDefault(Object var0) {
      init();

      try {
         Object var1 = Array.newInstance(sFontFamily, 1);
         Array.set(var1, 0, var0);
         Typeface var4 = (Typeface)sCreateFromFamiliesWithDefault.invoke((Object)null, var1);
         return var4;
      } catch (IllegalAccessException var2) {
         var0 = var2;
      } catch (InvocationTargetException var3) {
         var0 = var3;
      }

      throw new RuntimeException((Throwable)var0);
   }

   private File getFile(ParcelFileDescriptor var1) {
      try {
         StringBuilder var2 = new StringBuilder();
         var2.append("/proc/self/fd/");
         var2.append(var1.getFd());
         String var4 = Os.readlink(var2.toString());
         if (OsConstants.S_ISREG(Os.stat(var4).st_mode)) {
            File var5 = new File(var4);
            return var5;
         }
      } catch (ErrnoException var3) {
      }

      return null;
   }

   private static void init() {
      if (!sHasInitBeenCalled) {
         sHasInitBeenCalled = true;
         Constructor var0 = null;

         Object var1;
         Method var3;
         Method var4;
         label20: {
            Constructor var2;
            label19: {
               try {
                  var1 = Class.forName("android.graphics.FontFamily");
                  var2 = ((Class)var1).getConstructor();
                  var3 = ((Class)var1).getMethod("addFontWeightStyle", String.class, Integer.TYPE, Boolean.TYPE);
                  var4 = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance((Class)var1, 1).getClass());
                  break label19;
               } catch (ClassNotFoundException var5) {
                  var1 = var5;
               } catch (NoSuchMethodException var6) {
                  var1 = var6;
               }

               Log.e("TypefaceCompatApi21Impl", var1.getClass().getName(), (Throwable)var1);
               var4 = null;
               var1 = var4;
               var3 = var4;
               break label20;
            }

            var0 = var2;
         }

         sFontFamilyCtor = var0;
         sFontFamily = (Class)var1;
         sAddFontWeightStyle = var3;
         sCreateFromFamiliesWithDefault = var4;
      }
   }

   private static Object newFamily() {
      init();

      Object var0;
      try {
         var0 = sFontFamilyCtor.newInstance();
         return var0;
      } catch (IllegalAccessException var1) {
         var0 = var1;
      } catch (InstantiationException var2) {
         var0 = var2;
      } catch (InvocationTargetException var3) {
         var0 = var3;
      }

      throw new RuntimeException((Throwable)var0);
   }

   public Typeface createFromFontFamilyFilesResourceEntry(Context param1, FontResourcesParserCompat.FontFamilyFilesResourceEntry param2, Resources param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public Typeface createFromFontInfo(Context var1, CancellationSignal var2, FontsContractCompat.FontInfo[] var3, int var4) {
      if (var3.length < 1) {
         return null;
      } else {
         FontsContractCompat.FontInfo var5 = this.findBestInfo(var3, var4);
         ContentResolver var186 = var1.getContentResolver();

         boolean var10001;
         ParcelFileDescriptor var184;
         try {
            var184 = var186.openFileDescriptor(var5.getUri(), "r", var2);
         } catch (IOException var181) {
            var10001 = false;
            return null;
         }

         if (var184 == null) {
            if (var184 != null) {
               try {
                  var184.close();
               } catch (IOException var170) {
                  var10001 = false;
                  return null;
               }
            }

            return null;
         } else {
            Throwable var10000;
            Throwable var182;
            label1331: {
               File var187;
               try {
                  var187 = this.getFile(var184);
               } catch (Throwable var180) {
                  var10000 = var180;
                  var10001 = false;
                  break label1331;
               }

               Typeface var183;
               label1332: {
                  if (var187 != null) {
                     try {
                        if (var187.canRead()) {
                           break label1332;
                        }
                     } catch (Throwable var179) {
                        var10000 = var179;
                        var10001 = false;
                        break label1331;
                     }
                  }

                  FileInputStream var188;
                  try {
                     var188 = new FileInputStream(var184.getFileDescriptor());
                  } catch (Throwable var178) {
                     var10000 = var178;
                     var10001 = false;
                     break label1331;
                  }

                  try {
                     var183 = super.createFromInputStream(var1, var188);
                  } catch (Throwable var177) {
                     var182 = var177;

                     try {
                        var188.close();
                     } catch (Throwable var174) {
                        Throwable var189 = var174;

                        label1291:
                        try {
                           var182.addSuppressed(var189);
                           break label1291;
                        } catch (Throwable var173) {
                           var10000 = var173;
                           var10001 = false;
                           break label1331;
                        }
                     }

                     try {
                        throw var182;
                     } catch (Throwable var169) {
                        var10000 = var169;
                        var10001 = false;
                        break label1331;
                     }
                  }

                  try {
                     var188.close();
                  } catch (Throwable var176) {
                     var10000 = var176;
                     var10001 = false;
                     break label1331;
                  }

                  if (var184 != null) {
                     try {
                        var184.close();
                     } catch (IOException var172) {
                        var10001 = false;
                        return null;
                     }
                  }

                  return var183;
               }

               try {
                  var183 = Typeface.createFromFile(var187);
               } catch (Throwable var175) {
                  var10000 = var175;
                  var10001 = false;
                  break label1331;
               }

               if (var184 != null) {
                  try {
                     var184.close();
                  } catch (IOException var171) {
                     var10001 = false;
                     return null;
                  }
               }

               return var183;
            }

            var182 = var10000;
            if (var184 != null) {
               try {
                  var184.close();
               } catch (Throwable var168) {
                  Throwable var185 = var168;

                  label1269:
                  try {
                     var182.addSuppressed(var185);
                     break label1269;
                  } catch (IOException var167) {
                     var10001 = false;
                     return null;
                  }
               }
            }

            try {
               throw var182;
            } catch (IOException var166) {
               var10001 = false;
               return null;
            }
         }
      }
   }
}
