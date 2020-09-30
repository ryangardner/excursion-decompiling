package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.Typeface.CustomFallbackBuilder;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontFamily.Builder;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;

public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
   public Typeface createFromFontFamilyFilesResourceEntry(Context var1, FontResourcesParserCompat.FontFamilyFilesResourceEntry var2, Resources var3, int var4) {
      FontResourcesParserCompat.FontFileResourceEntry[] var5 = var2.getEntries();
      int var6 = var5.length;
      byte var7 = 0;
      Builder var15 = null;
      int var8 = 0;

      while(true) {
         byte var9 = 1;
         if (var8 >= var6) {
            if (var15 == null) {
               return null;
            } else {
               short var19;
               if ((var4 & 1) != 0) {
                  var19 = 700;
               } else {
                  var19 = 400;
               }

               var9 = var7;
               if ((var4 & 2) != 0) {
                  var9 = 1;
               }

               FontStyle var18 = new FontStyle(var19, var9);
               return (new CustomFallbackBuilder(var15.build())).setStyle(var18).build();
            }
         }

         FontResourcesParserCompat.FontFileResourceEntry var16 = var5[var8];

         label70: {
            android.graphics.fonts.Font.Builder var10;
            boolean var10001;
            label58: {
               try {
                  var10 = new android.graphics.fonts.Font.Builder(var3, var16.getResourceId());
                  var10 = var10.setWeight(var16.getWeight());
                  if (var16.isItalic()) {
                     break label58;
                  }
               } catch (IOException var14) {
                  var10001 = false;
                  break label70;
               }

               var9 = 0;
            }

            Font var20;
            try {
               var20 = var10.setSlant(var9).setTtcIndex(var16.getTtcIndex()).setFontVariationSettings(var16.getVariationSettings()).build();
            } catch (IOException var13) {
               var10001 = false;
               break label70;
            }

            if (var15 == null) {
               label46: {
                  Builder var17;
                  try {
                     var17 = new Builder(var20);
                  } catch (IOException var11) {
                     var10001 = false;
                     break label46;
                  }

                  var15 = var17;
               }
            } else {
               try {
                  var15.addFont(var20);
               } catch (IOException var12) {
                  var10001 = false;
               }
            }
         }

         ++var8;
      }
   }

   public Typeface createFromFontInfo(Context var1, CancellationSignal var2, FontsContractCompat.FontInfo[] var3, int var4) {
      ContentResolver var5 = var1.getContentResolver();
      int var6 = var3.length;
      byte var7 = 0;
      Builder var67 = null;
      int var8 = 0;

      while(true) {
         byte var9 = 1;
         if (var8 >= var6) {
            if (var67 == null) {
               return null;
            } else {
               short var69;
               if ((var4 & 1) != 0) {
                  var69 = 700;
               } else {
                  var69 = 400;
               }

               var9 = var7;
               if ((var4 & 2) != 0) {
                  var9 = 1;
               }

               FontStyle var68 = new FontStyle(var69, var9);
               return (new CustomFallbackBuilder(var67.build())).setStyle(var68).build();
            }
         }

         FontsContractCompat.FontInfo var10 = var3[var8];
         Builder var11 = var67;

         label696: {
            ParcelFileDescriptor var12;
            boolean var10001;
            try {
               var12 = var5.openFileDescriptor(var10.getUri(), "r", var2);
            } catch (IOException var66) {
               var10001 = false;
               break label696;
            }

            if (var12 == null) {
               var11 = var67;
               if (var12 == null) {
                  break label696;
               }
            } else {
               label689: {
                  Throwable var10000;
                  label691: {
                     android.graphics.fonts.Font.Builder var71;
                     label668: {
                        try {
                           var71 = new android.graphics.fonts.Font.Builder(var12);
                           var71 = var71.setWeight(var10.getWeight());
                           if (var10.isItalic()) {
                              break label668;
                           }
                        } catch (Throwable var65) {
                           var10000 = var65;
                           var10001 = false;
                           break label691;
                        }

                        var9 = 0;
                     }

                     Font var72;
                     try {
                        var72 = var71.setSlant(var9).setTtcIndex(var10.getTtcIndex()).build();
                     } catch (Throwable var64) {
                        var10000 = var64;
                        var10001 = false;
                        break label691;
                     }

                     if (var67 == null) {
                        label656: {
                           try {
                              var11 = new Builder(var72);
                           } catch (Throwable var62) {
                              var10000 = var62;
                              var10001 = false;
                              break label656;
                           }

                           var67 = var11;
                        }
                     } else {
                        label658:
                        try {
                           var67.addFont(var72);
                        } catch (Throwable var63) {
                           var10000 = var63;
                           var10001 = false;
                           break label658;
                        }
                     }
                     break label689;
                  }

                  Throwable var70 = var10000;
                  if (var12 != null) {
                     try {
                        var12.close();
                     } catch (Throwable var60) {
                        Throwable var73 = var60;
                        var11 = var67;

                        label640:
                        try {
                           var70.addSuppressed(var73);
                           break label640;
                        } catch (IOException var59) {
                           var10001 = false;
                           break label696;
                        }
                     }
                  }

                  var11 = var67;

                  try {
                     throw var70;
                  } catch (IOException var58) {
                     var10001 = false;
                     break label696;
                  }
               }

               var11 = var67;
               if (var12 == null) {
                  break label696;
               }
            }

            var11 = var67;

            try {
               var12.close();
            } catch (IOException var61) {
               var10001 = false;
               break label696;
            }

            var11 = var67;
         }

         ++var8;
         var67 = var11;
      }
   }

   protected Typeface createFromInputStream(Context var1, InputStream var2) {
      throw new RuntimeException("Do not use this function in API 29 or later.");
   }

   public Typeface createFromResourcesFontFile(Context var1, Resources var2, int var3, String var4, int var5) {
      Font var8;
      FontFamily var10;
      try {
         android.graphics.fonts.Font.Builder var7 = new android.graphics.fonts.Font.Builder(var2, var3);
         var8 = var7.build();
         Builder var9 = new Builder(var8);
         var10 = var9.build();
      } catch (IOException var6) {
         return null;
      }

      return (new CustomFallbackBuilder(var10)).setStyle(var8.getStyle()).build();
   }

   protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] var1, int var2) {
      throw new RuntimeException("Do not use this function in API 29 or later.");
   }
}
