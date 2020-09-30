package com.syntak.library;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaOp {
   public static final int CAMERA_NOT_USE = 0;
   public static final int CAMERA_USE_MAIN = 1;
   public static final int CAMERA_USE_SELFIE = 2;
   public static final int CURSOR_TYPE_AUDIO = 2;
   public static final int CURSOR_TYPE_IMAGE = 0;
   public static final int CURSOR_TYPE_IMAGE_THUMBNAIL = 10;
   public static final int CURSOR_TYPE_IMAGE_VIDEO = 3;
   public static final int CURSOR_TYPE_MEDIA = 4;
   public static final int CURSOR_TYPE_VIDEO = 1;
   public static final int CURSOR_TYPE_VIDEO_THUMBNAIL = 11;
   public static final int DITHER_TYPE_ATKINSON = 6;
   public static final int DITHER_TYPE_BURKES = 7;
   public static final int DITHER_TYPE_FALSE_FLOYD_STEINBERG = 3;
   public static final int DITHER_TYPE_FLOYD_STEINBERG = 1;
   public static final int DITHER_TYPE_JARVIS_JUDIS_NINKE = 4;
   public static final int DITHER_TYPE_NONE = 0;
   public static final int DITHER_TYPE_SIERRA = 8;
   public static final int DITHER_TYPE_SIERRA_LITE = 10;
   public static final int DITHER_TYPE_STUCKI = 5;
   public static final int DITHER_TYPE_TWO_ROW_SIERRA = 9;
   public static final int IconMicroOfVideo = 3;
   public static final int IconMiniOfImage = 1;
   public static final int IconMiniOfVideo = 1;
   public static final int IconMircoOfImage = 3;
   public static final String TAG_CAMERA_USAGE = "camera_usage";
   public static final String TAG_CURSOR_TYPE = "cursor_type";
   static final int[] eedFilterAtkinson = new int[]{0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0};
   static final int[] eedFilterBurkes = new int[]{0, 0, 0, 8, 4, 2, 4, 8, 4, 2};
   static final int[] eedFilterFS = new int[]{0, 0, 7, 3, 5, 1};
   static final int[] eedFilterFalseFS = new int[]{0, 3, 3, 2};
   static final int[] eedFilterJJN = new int[]{0, 0, 0, 7, 5, 3, 5, 7, 5, 3, 1, 3, 5, 3, 1};
   static final int[] eedFilterNone = new int[]{1};
   static final int[] eedFilterSierra = new int[]{0, 0, 0, 5, 3, 2, 4, 5, 4, 2, 0, 2, 3, 2, 0};
   static final int[] eedFilterSierra2 = new int[]{0, 0, 0, 4, 3, 1, 2, 3, 2, 1};
   static final int[] eedFilterSierraLite = new int[]{0, 0, 2, 1, 1, 0};
   static final int[] eedFilterStucki = new int[]{0, 0, 0, 8, 4, 2, 4, 8, 4, 2, 1, 2, 4, 2, 1};

   public static Bitmap BitMatrixToBitmap(BitMatrix var0) {
      int var1 = var0.getHeight();
      int var2 = var0.getWidth();
      Bitmap var3 = Bitmap.createBitmap(var2, var1, Config.RGB_565);

      for(int var4 = 0; var4 < var2; ++var4) {
         for(int var5 = 0; var5 < var1; ++var5) {
            int var6;
            if (var0.get(var4, var5)) {
               var6 = -16777216;
            } else {
               var6 = -1;
            }

            var3.setPixel(var4, var5, var6);
         }
      }

      return var3;
   }

   public static int DimensionScaling(int var0, int var1, int var2) {
      return var1 == 0 ? 0 : (int)((float)var0 / (float)var1 * (float)var2);
   }

   public static int DpToPx(Context var0, int var1) {
      float var2 = var0.getResources().getDisplayMetrics().density;
      return Math.round((float)var1 * var2);
   }

   public static int PxToDp(Context var0, int var1) {
      float var2 = var0.getResources().getDisplayMetrics().density;
      return Math.round((float)var1 / var2);
   }

   public static MediaOp.CIELAB_COLOR RgbToCielabColor(int var0, int var1, int var2) {
      MediaOp.CIELAB_COLOR var3 = new MediaOp.CIELAB_COLOR();
      float var4 = (float)var0 / 255.0F;
      float var5 = (float)var1 / 255.0F;
      float var6 = (float)var2 / 255.0F;
      if (var4 > 0.04045F) {
         var4 = (float)Math.pow((double)((var4 + 0.055F) / 1.055F), 2.4000000953674316D);
      } else {
         var4 /= 12.92F;
      }

      if (var5 > 0.04045F) {
         var5 = (float)Math.pow((double)((var5 + 0.055F) / 1.055F), 2.4000000953674316D);
      } else {
         var5 /= 12.92F;
      }

      if (var6 > 0.04045F) {
         var6 = (float)Math.pow(((double)var6 + 0.055D) / 1.0549999475479126D, 2.4000000953674316D);
      } else {
         var6 /= 12.92F;
      }

      var4 *= 100.0F;
      float var7 = var5 * 100.0F;
      var6 *= 100.0F;
      float var8 = (0.4124F * var4 + 0.3576F * var7 + 0.1805F * var6) / 95.047F;
      var5 = (0.2126F * var4 + 0.7152F * var7 + 0.0722F * var6) / 100.0F;
      var6 = (var4 * 0.0193F + var7 * 0.1192F + var6 * 0.9505F) / 108.883F;
      if (var8 > 0.008856F) {
         var4 = (float)Math.pow((double)var8, 0.3333333432674408D);
      } else {
         var4 = var8 * 7.787F + 0.13793103F;
      }

      if (var5 > 0.008856F) {
         var5 = (float)Math.pow((double)var5, 0.3333333432674408D);
      } else {
         var5 = var5 * 7.787F + 0.13793103F;
      }

      if (var6 > 0.008856F) {
         var6 = (float)Math.pow((double)var6, 0.3333333432674408D);
      } else {
         var6 = var6 * 7.787F + 0.13793103F;
      }

      var3.L = 116.0F * var5 - 16.0F;
      var3.a = (var4 - var5) * 500.0F;
      var3.b = (var5 - var6) * 200.0F;
      return var3;
   }

   public static boolean TakeAudioWithIntent(Context var0, int var1) {
      Intent var2 = new Intent("android.provider.MediaStore.RECORD_SOUND");
      if (var2.resolveActivity(var0.getPackageManager()) == null) {
         return false;
      } else {
         ((Activity)var0).startActivityForResult(var2, var1);
         return true;
      }
   }

   public static boolean TakePhotoWithIntent(Context var0, int var1, Uri var2) {
      return TakePhotoWithIntent(var0, var1, var2, 0);
   }

   public static boolean TakePhotoWithIntent(Context var0, int var1, Uri var2, int var3) {
      Intent var4 = new Intent("android.media.action.IMAGE_CAPTURE");
      if (var3 > 0) {
         var4.putExtra("android.intent.extras.CAMERA_FACING", var3);
      }

      if (var4.resolveActivity(var0.getPackageManager()) == null) {
         return false;
      } else {
         var4.putExtra("output", var2);
         ((Activity)var0).startActivityForResult(var4, var1);
         return true;
      }
   }

   public static boolean TakeVideoWithIntent(Context var0, int var1) {
      Intent var2 = new Intent("android.media.action.VIDEO_CAPTURE");
      if (var2.resolveActivity(var0.getPackageManager()) == null) {
         return false;
      } else {
         ((Activity)var0).startActivityForResult(var2, var1);
         return true;
      }
   }

   public static Bitmap TextToBitmap(String var0, int var1, int var2, int var3, int var4, int var5, Paint var6) {
      Bitmap var7 = Bitmap.createBitmap(var1, var2, Config.RGB_565);
      Canvas var8 = new Canvas(var7);
      var8.drawColor(var3);
      var8.drawText(var0, (float)var4, (float)var5, var6);
      return var7;
   }

   public static Bitmap TextToQR(String var0, int var1, ErrorCorrectionLevel var2) {
      QRCodeWriter var3 = new QRCodeWriter();
      HashMap var4 = new HashMap();
      var4.put(EncodeHintType.ERROR_CORRECTION, var2);

      Bitmap var6;
      try {
         var6 = BitMatrixToBitmap(var3.encode(var0, BarcodeFormat.QR_CODE, var1, var1, var4));
      } catch (WriterException var5) {
         var5.printStackTrace();
         var6 = null;
      }

      return var6;
   }

   public static boolean ViewFileWithIntent(Context var0, String var1) {
      String var2 = getMimeType(var1);
      Intent var3 = new Intent();
      var3.setAction("android.intent.action.VIEW");
      var3.setDataAndType(Uri.parse(var1), var2);
      if (var3.resolveActivity(var0.getPackageManager()) == null) {
         return false;
      } else {
         var0.startActivity(var3);
         return true;
      }
   }

   public static int[] calculateFittingSize(Context var0, int var1, int var2, float var3) {
      int[] var4 = new int[]{0, 0};
      Point var8 = getScreenSizePx(var0);
      int var5 = var8.x;
      int var6 = var8.y;
      int var7 = var1;
      if (var1 > var5) {
         var7 = var5;
      }

      var1 = var2;
      if (var2 > var6) {
         var1 = var6;
      }

      var2 = (int)((float)var7 * var3);
      if (var1 > var2) {
         var6 = var7;
      } else {
         var5 = (int)((float)var1 / var3);
         var6 = var7;
         var2 = var1;
         if (var7 > var5) {
            var6 = var5;
            var2 = var1;
         }
      }

      var4[0] = var6;
      var4[1] = var2;
      return var4;
   }

   public static int calculateInSampleSize(int var0, int var1, int var2, int var3) {
      long var4 = (long)(var2 * var3);
      long var6 = (long)(var0 * var1);

      for(var0 = 1; var6 > var4; var6 /= 4L) {
         var0 *= 2;
      }

      return var0;
   }

   public static boolean checkImageExist(String var0) {
      boolean var1 = FileOp.checkFileExist(var0);
      boolean var2 = false;
      if (!var1) {
         return false;
      } else {
         Options var3 = new Options();
         var3.inJustDecodeBounds = true;
         BitmapFactory.decodeFile(var0, var3);
         int var4 = var3.outHeight;
         int var5 = var3.outWidth;
         var1 = var2;
         if (var4 >= 0) {
            var1 = var2;
            if (var5 >= 0) {
               var1 = true;
            }
         }

         return var1;
      }
   }

   public static Bitmap convertBitmapGreyscale(Bitmap var0) {
      Bitmap var1 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
      Canvas var2 = new Canvas(var1);
      ColorMatrix var3 = new ColorMatrix();
      var3.setSaturation(0.0F);
      Paint var4 = new Paint();
      var4.setColorFilter(new ColorMatrixColorFilter(var3));
      var2.drawBitmap(var0, 0.0F, 0.0F, var4);
      return var1;
   }

   public static Bitmap convertBitmapMonochrome(Bitmap var0) {
      int var1 = var0.getWidth();
      int var2 = var0.getHeight();
      Bitmap var3 = Bitmap.createBitmap(var1, var2, var0.getConfig());

      for(int var4 = 0; var4 < var2; ++var4) {
         for(int var5 = 0; var5 < var1; ++var5) {
            int var6 = var0.getPixel(var5, var4);
            int var7 = Color.alpha(var6);
            short var8;
            if (Color.red(var6) * 2 + Color.green(var6) * 5 + Color.blue(var6) >= 1024) {
               var8 = 255;
            } else {
               var8 = 0;
            }

            var3.setPixel(var5, var4, Color.argb(var7, var8, var8, var8));
         }
      }

      return var3;
   }

   public static Loader<Cursor> createCursorLoader(Context var0, int var1, Bundle var2) {
      MediaOp.CursorQuery var3 = prepareCursorQuery(var1);
      return var3 != null ? new CursorLoader(var0, var3.uri, var3.columns, var3.selection, var3.selectionArgs, var3.sortOrder) : null;
   }

   public static MediaPlayer displayAV(String var0) {
      MediaPlayer var1 = new MediaPlayer();

      try {
         var1.setDataSource(var0);
         var1.prepare();
         var1.start();
      } catch (IOException var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Error open");
         var3.append(var0);
         Log.e("displayAV", var3.toString(), var4);
      }

      var1.setOnCompletionListener(new OnCompletionListener() {
         public void onCompletion(MediaPlayer var1) {
            var1.stop();
            var1.release();
         }
      });
      return var1;
   }

   public static Object displayMedia(Context var0, String var1, View var2) {
      int var3 = getMediaTypeFromPath(var1);
      if (var3 != 1) {
         if (var3 == 2 || var3 == 3) {
            displayAV(var1);
         }
      } else {
         fitImageToView(var0, (ImageView)var2, (String)var1, false, 0, 0, 0);
      }

      return null;
   }

   public static Bitmap ditherBitmap(Bitmap var0, int var1, int var2, int var3) {
      int var4 = var0.getWidth();
      int var5 = var0.getHeight();
      Bitmap var6 = Bitmap.createBitmap(var4, var5, Config.ARGB_8888);
      int var7 = (int)Math.pow(2.0D, (double)var1);
      int[] var8 = new int[var7];
      MediaOp.CIELAB_COLOR[] var9 = new MediaOp.CIELAB_COLOR[var7];
      byte var10 = 5;
      float var11 = 255.0F;
      byte var12;
      byte var13;
      float var14;
      float var15;
      if (var1 != 8) {
         if (var1 != 16) {
            var10 = 1;
            var12 = 1;
            var13 = 1;
            var14 = 255.0F;
            var15 = 255.0F;
         } else {
            var12 = 5;
            var11 = 8.2F;
            var13 = 6;
            var14 = 8.2F;
            var15 = 4.0F;
         }
      } else {
         var14 = 85.0F;
         var10 = 3;
         var12 = 2;
         var11 = 36.4F;
         var13 = 3;
         var15 = 336.4F;
      }

      int var16;
      int var18;
      int var22;
      int var23;
      int var43;
      int var44;
      if (var1 == 1) {
         var8[0] = Color.rgb(0, 0, 0);
         var9[0] = RgbToCielabColor(0, 0, 0);
         var8[1] = Color.rgb(255, 255, 255);
         var9[1] = RgbToCielabColor(255, 255, 255);
         var44 = var4;
      } else {
         int var17 = 0;
         var18 = 0;
         byte var19 = var10;

         while(true) {
            double var20 = (double)var19;
            if (var17 >= (int)Math.pow(2.0D, var20) || var18 >= var7) {
               var44 = var4;
               break;
            }

            if (var17 == 0) {
               var43 = 0;
            } else if (var17 == (int)Math.pow(2.0D, var20) - 1) {
               var43 = 255;
            } else {
               var43 = (int)((float)var17 * var11);
            }

            var22 = 0;

            while(true) {
               var20 = (double)var13;
               if (var22 >= (int)Math.pow(2.0D, var20) || var18 >= var7) {
                  ++var17;
                  break;
               }

               if (var22 == 0) {
                  var4 = 0;
               } else if (var22 == (int)Math.pow(2.0D, var20) - 1) {
                  var4 = 255;
               } else {
                  var4 = (int)((float)var17 * var15);
               }

               var23 = 0;

               while(true) {
                  var20 = (double)var12;
                  if (var23 >= (int)Math.pow(2.0D, var20) || var18 >= var7) {
                     ++var22;
                     break;
                  }

                  if (var23 == 0) {
                     var16 = 0;
                  } else if (var23 == (int)Math.pow(2.0D, var20) - 1) {
                     var16 = 255;
                  } else {
                     var16 = (int)((float)var17 * var14);
                  }

                  var8[var18] = Color.rgb(var43, var4, var16);
                  var9[var18] = RgbToCielabColor(var43, var4, var16);
                  ++var18;
                  ++var23;
               }
            }
         }
      }

      int[] var24;
      byte var38;
      byte var39;
      byte var45;
      label125: {
         label124: {
            label123: {
               label122: {
                  var45 = 1;
                  var10 = 1;
                  var13 = 1;
                  var38 = -1;
                  switch(var2) {
                  case 1:
                     var24 = eedFilterFS;
                     var39 = var45;
                     break label122;
                  case 2:
                  default:
                     var24 = eedFilterNone;
                     var38 = 0;
                     var39 = 0;
                     var10 = 0;
                     var13 = 1;
                     break label125;
                  case 3:
                     var24 = eedFilterFalseFS;
                     var38 = 0;
                     var10 = 1;
                     var39 = var13;
                     break label123;
                  case 4:
                     var24 = eedFilterJJN;
                     var13 = 48;
                     break label124;
                  case 5:
                     var24 = eedFilterStucki;
                     var13 = 42;
                     break label124;
                  case 6:
                     var24 = eedFilterAtkinson;
                     var39 = 2;
                     var10 = 2;
                     break label123;
                  case 7:
                     var24 = eedFilterBurkes;
                     var10 = 1;
                     break;
                  case 8:
                     var24 = eedFilterSierra;
                     var10 = 2;
                     break;
                  case 9:
                     var24 = eedFilterSierra2;
                     var38 = -2;
                     var39 = 2;
                     break label122;
                  case 10:
                     var24 = eedFilterSierraLite;
                     var13 = 4;
                     var45 = 1;
                     var39 = var10;
                     var10 = var45;
                     break label125;
                  }

                  var39 = 2;
                  var38 = -2;
                  var13 = 32;
                  break label125;
               }

               var10 = 1;
               var13 = 16;
               break label125;
            }

            var13 = 8;
            break label125;
         }

         var38 = -2;
         var39 = 2;
         var10 = 2;
      }

      var22 = var44 + (var39 - var38);
      var16 = (var5 + var10) * var22;
      int[] var25 = new int[var16];
      int[] var26 = new int[var16];
      int[] var27 = new int[var16];
      var5 = var5;
      var4 = 0;
      Bitmap var28 = var6;
      var45 = var13;
      int[] var41 = var25;
      var23 = var7;
      var13 = var10;
      byte var46 = var39;
      byte var50 = var38;
      var25 = var24;

      while(true) {
         int var51 = 0;
         if (var4 >= var5) {
            return var28;
         }

         var43 = var44;
         Bitmap var52 = var28;
         var12 = var45;
         MediaOp.CIELAB_COLOR[] var53 = var9;
         var2 = var23;
         byte var40 = var13;

         byte var47;
         for(var47 = var50; var51 < var43; var43 = var7) {
            var18 = var0.getPixel(var51, var4);
            var7 = Color.red(var18);
            var23 = Color.green(var18);
            var18 = Color.blue(var18);
            int var29 = var4 * var22;
            var5 = var51 - var47 + var29;
            int var30 = (var41[var5] + var7 * var12) / var12;
            int var31 = var26[var5];
            var7 = var43;
            var43 = (var31 + var23 * var12) / var12;
            var18 = (var27[var5] + var18 * var12) / var12;
            var31 = Math.min(255, Math.max(var30, 0));
            int var32 = Math.min(255, Math.max(var43, 0));
            int var33 = Math.min(255, Math.max(var18, 0));
            MediaOp.CIELAB_COLOR var42 = RgbToCielabColor(var31, var32, var33);
            var43 = 0;
            var23 = 65535;

            for(var18 = 0; var43 < var2; var23 = var5) {
               var30 = (int)getCielabDistance(var42, var53[var43]);
               var5 = var23;
               if (var30 < var23) {
                  var5 = var30;
                  var18 = var43;
               }

               ++var43;
            }

            var43 = var8[var18];
            var52.setPixel(var51, var4, var43);
            int var34 = Color.red(var8[var18]);
            var30 = Color.green(var8[var18]);
            int var35 = Color.blue(var8[var18]);
            var10 = 0;
            var23 = 0;
            var18 = var22;
            var22 = var10;

            byte var48;
            int var49;
            for(var48 = var47; var22 <= var40; var23 = var49) {
               var5 = var48;

               for(var49 = var23; var5 <= var46; ++var49) {
                  long var36 = (long)var3 * (long)var25[var49];
                  var23 = var22 * var18 + var5 + var51 - var48 + var29;
                  var41[var23] = (int)((long)var41[var23] + ((long)(var31 - var34) * var36 >> 7));
                  var26[var23] = (int)((long)var26[var23] + ((long)(var32 - var30) * var36 >> 7));
                  var27[var23] = (int)((long)var27[var23] + ((long)(var33 - var35) * var36 >> 7));
                  ++var5;
               }

               ++var22;
            }

            ++var51;
            var47 = var48;
            var22 = var18;
         }

         ++var4;
         var50 = var47;
         var13 = var40;
         var23 = var2;
         var9 = var53;
         var5 = var5;
         var45 = var12;
         var28 = var52;
         var44 = var43;
      }
   }

   public static Bitmap drawCircil(int var0, int var1, int var2, int var3, int var4) {
      Bitmap var5 = Bitmap.createBitmap(var0, var1, Config.ARGB_8888);
      Canvas var6 = new Canvas(var5);
      Paint var7 = new Paint();
      float var8 = (float)var5.getWidth() / 2.0F;
      float var9 = (float)var5.getHeight() / 2.0F;
      float var10;
      if (var8 < var9) {
         var10 = var8 - (float)var3;
      } else {
         var10 = var9 - (float)var3;
      }

      var7.setColor(var4);
      var7.setStyle(Style.FILL);
      var7.setAntiAlias(true);
      var6.drawCircle(var8, var9, var10, var7);
      var7.setColor(var2);
      var7.setStyle(Style.STROKE);
      var7.setStrokeWidth((float)var3);
      var7.setAntiAlias(true);
      var6.drawCircle(var8, var9, var10, var7);
      return var5;
   }

   public static void fitDrawableToView(final ImageView var0, final Drawable var1) {
      var0.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
         public boolean onPreDraw() {
            var0.getViewTreeObserver().removeOnPreDrawListener(this);
            int var1x = var0.getMeasuredHeight();
            int var2 = var0.getMeasuredWidth();
            var1.setBounds(0, 0, var2, var1x);
            var0.setImageDrawable(var1);
            return true;
         }
      });
   }

   public static void fitImageToView(Context var0, ImageView var1, Bitmap var2) {
      fitImageToView(var0, var1, (Bitmap)var2, false, 0, 0, 0);
   }

   public static void fitImageToView(Context var0, final ImageView var1, final Bitmap var2, final boolean var3, final int var4, final int var5, final int var6) {
      if (var2 != null) {
         LayoutParams var7 = var1.getLayoutParams();
         int var8 = var7.width;
         int var9 = var7.height;
         float var10 = getImageHeightWidthRatio(var2);
         if (var10 > 0.0F) {
            if (var10 != 0.0F && var8 > 0 && var9 > 0) {
               int[] var11 = calculateFittingSize(var0, var8, var9, var10);
               var9 = var11[0];
               var7.height = var11[1];
               var7.width = var9;
               var1.setLayoutParams(var7);
            }

            var1.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
               public boolean onPreDraw() {
                  var1.getViewTreeObserver().removeOnPreDrawListener(this);
                  var1.getMeasuredHeight();
                  int var1x = var1.getMeasuredWidth();
                  int var2x = var2.getWidth();
                  var2.getHeight();
                  int var10000 = var1x / var2x;
                  int var3x = var5 * var2x / var1x;
                  int var4x = var6;
                  var2x = var2x * var4x / var1x;
                  Bitmap var5x = var2;
                  if (var3 && var4x > 0) {
                     var5x = MediaOp.getBitmapRoundCornerBorder(var5x, var2x, var4, var3x);
                  } else {
                     Bitmap var6x = var5x;
                     if (var6 > 0) {
                        var6x = MediaOp.getBitmapRoundCorner(var5x, var2x);
                     }

                     var5x = var6x;
                     if (var3) {
                        var5x = MediaOp.getBitmapWithBorder(var6x, var4, var3x);
                     }
                  }

                  var1.setImageBitmap(var5x);
                  return true;
               }
            });
         }
      }
   }

   public static void fitImageToView(Context var0, ImageView var1, String var2) {
      fitImageToView(var0, var1, (String)var2, false, 0, 0, 0);
   }

   public static void fitImageToView(final Context var0, final ImageView var1, final String var2, final boolean var3, final int var4, final int var5, final int var6) {
      if (checkImageExist(var2)) {
         LayoutParams var7 = var1.getLayoutParams();
         int var8 = var7.width;
         int var9 = var7.height;
         float var10 = getImageHeightWidthRatio(var2);
         if (var10 > 0.0F) {
            if (var10 != 0.0F && var8 > 0 && var9 > 0) {
               int[] var11 = calculateFittingSize(var0, var8, var9, var10);
               var9 = var11[0];
               var7.height = var11[1];
               var7.width = var9;
               var1.setLayoutParams(var7);
            }

            var1.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
               public boolean onPreDraw() {
                  var1.getViewTreeObserver().removeOnPreDrawListener(this);
                  int var1x = var1.getMeasuredHeight();
                  int var2x = var1.getMeasuredWidth();
                  Bitmap var3x = MediaOp.getThumbnailFromPath(var0, var2, var2x, var1x, 3);
                  if (var3x != null) {
                     int var4x = var3x.getWidth();
                     var3x.getHeight();
                     int var10000 = var2x / var4x;
                     var1x = var5 * var4x / var2x;
                     int var5x = var6;
                     var2x = var4x * var5x / var2x;
                     if (var3 && var5x > 0) {
                        var3x = MediaOp.getBitmapRoundCornerBorder(var3x, var2x, var4, var1x);
                     } else {
                        Bitmap var6x = var3x;
                        if (var6 > 0) {
                           var6x = MediaOp.getBitmapRoundCorner(var3x, var2x);
                        }

                        var3x = var6x;
                        if (var3) {
                           var3x = MediaOp.getBitmapWithBorder(var6x, var4, var1x);
                        }
                     }

                     var1.setImageBitmap(var3x);
                  }

                  return true;
               }
            });
         }
      }
   }

   public static void fitImageToViewCircled(final Context var0, final ImageView var1, final String var2) {
      var1.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
         public boolean onPreDraw() {
            var1.getViewTreeObserver().removeOnPreDrawListener(this);
            int var1x = var1.getMeasuredHeight();
            int var2x = var1.getMeasuredWidth();
            Bitmap var3 = MediaOp.getThumbnailFromPath(var0, var2, var2x, var1x, 3);
            var1.setImageBitmap(MediaOp.getBitmapCirciled(var3));
            return true;
         }
      });
   }

   public static Bitmap getBitmapCenterCrop(Bitmap var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.getWidth();
         int var2 = var0.getHeight();
         if (var1 == var2) {
            return var0;
         } else {
            int var3;
            if (var2 < var1) {
               var3 = var2;
            } else {
               var3 = var1;
            }

            Bitmap var4 = Bitmap.createBitmap(var3, var3, Config.ARGB_8888);
            Canvas var5 = new Canvas(var4);
            var5.drawColor(-16777216);
            Rect var6 = new Rect((var1 - var3) / 2, (var2 - var3) / 2, var3, var3);
            float var7 = (float)var3;
            RectF var8 = new RectF(0.0F, 0.0F, var7, var7);
            Paint var9 = new Paint();
            var9.setAntiAlias(true);
            var9.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            var5.drawBitmap(var0, var6, var8, var9);
            return var4;
         }
      }
   }

   public static Bitmap getBitmapCenterCrop(Bitmap var0, int var1) {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.getWidth();
         int var3 = var0.getHeight();
         if (var2 == var3) {
            return var0;
         } else {
            int var4 = Math.max(var2, var3);
            int var5 = Math.min(var2, var3);
            float var6 = (float)var4;
            float var7 = (float)var5;
            var6 /= var7;
            if (var6 > 1.2F) {
               var5 = (int)(((var6 - 1.2F) / 2.0F + 1.0F) * var7);
            }

            Bitmap var8 = Bitmap.createBitmap(var5, var5, Config.ARGB_8888);
            Canvas var9 = new Canvas(var8);
            var9.drawColor(var1);
            var4 = Math.min(var2, var5);
            var1 = Math.min(var3, var5);
            Rect var10 = new Rect((var2 - var4) / 2, (var3 - var1) / 2, var4, var1);
            RectF var11 = new RectF((float)(var5 - var4) / 2.0F, (float)(var5 - var1) / 2.0F, (float)var4, (float)var1);
            Paint var12 = new Paint();
            var12.setAntiAlias(true);
            var12.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            var9.drawBitmap(var0, var10, var11, var12);
            return var8;
         }
      }
   }

   public static Bitmap getBitmapCirciled(Bitmap var0) {
      Bitmap var1 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
      Canvas var2 = new Canvas(var1);
      Paint var3 = new Paint();
      Rect var4 = new Rect(0, 0, var0.getWidth(), var0.getHeight());
      new RectF(var4);
      float var5 = (float)var0.getWidth() / 2.0F;
      float var6 = (float)var0.getHeight() / 2.0F;
      float var7 = Math.min(var5, var6);
      var3.setColor(-12434878);
      var3.setAntiAlias(true);
      var2.drawARGB(0, 0, 0, 0);
      var2.drawCircle(var5, var6, var7, var3);
      var3.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
      var2.drawBitmap(var0, var4, var4, var3);
      return var1;
   }

   public static Bitmap getBitmapFitImageView(String var0, ImageView var1) {
      int var2 = var1.getHeight();
      int var3 = var1.getWidth();
      Options var6 = new Options();
      var6.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(var0, var6);
      int var4 = var6.outHeight;
      int var5 = var6.outWidth;
      if (var4 > var2 || var5 > var3) {
         var6.inSampleSize = calculateInSampleSize(var6.outWidth, var6.outHeight, var3, var2);
      }

      var6.inJustDecodeBounds = false;
      return BitmapFactory.decodeFile(var0, var6);
   }

   public static Bitmap getBitmapFromDrawable(Drawable var0) {
      return ((BitmapDrawable)var0).getBitmap();
   }

   public static Bitmap getBitmapFromPath(String var0) {
      Options var1 = new Options();
      var1.inPreferredConfig = Config.ARGB_8888;
      return BitmapFactory.decodeFile(var0, var1);
   }

   public static Bitmap getBitmapFromURL(String var0) {
      Object var1 = null;

      HttpURLConnection var5;
      Bitmap var7;
      label25: {
         IOException var2;
         label24: {
            try {
               URL var6 = new URL(var0);
               var5 = (HttpURLConnection)var6.openConnection();
            } catch (IOException var4) {
               var2 = var4;
               var5 = null;
               break label24;
            }

            try {
               var5.connect();
               var7 = BitmapFactory.decodeStream(var5.getInputStream());
               break label25;
            } catch (IOException var3) {
               var2 = var3;
            }
         }

         Log.d("getBitmapFromURL", var2.toString());
         var2.printStackTrace();
         var7 = (Bitmap)var1;
      }

      if (var5 != null) {
         var5.disconnect();
      }

      return var7;
   }

   public static Bitmap getBitmapFromUri(Context var0, Uri var1) {
      Bitmap var3;
      try {
         var3 = Media.getBitmap(var0.getContentResolver(), var1);
      } catch (IOException var2) {
         var3 = null;
      }

      return var3;
   }

   public static float getBitmapHeightWidthRatio(Bitmap var0) {
      return (float)var0.getHeight() / (float)var0.getWidth();
   }

   public static Bitmap getBitmapRoundCorner(Bitmap var0, int var1) {
      Bitmap var2 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
      Canvas var3 = new Canvas(var2);
      Rect var4 = new Rect(0, 0, var0.getWidth(), var0.getHeight());
      RectF var5 = new RectF(var4);
      Paint var6 = new Paint();
      var6.setColor(-12434878);
      var6.setAntiAlias(true);
      var3.drawARGB(0, 0, 0, 0);
      float var7 = (float)var1;
      var3.drawRoundRect(var5, var7, var7, var6);
      var6.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
      var3.drawBitmap(var0, var4, var4, var6);
      return var2;
   }

   public static Bitmap getBitmapRoundCornerBorder(Bitmap var0, int var1, int var2, int var3) {
      int var4 = var0.getWidth();
      int var5 = var3 * 2;
      Bitmap var6 = Bitmap.createBitmap(var4 + var5, var0.getHeight() + var5, Config.ARGB_8888);
      Bitmap var7 = getBitmapRoundCorner(var0, var1);
      Canvas var8 = new Canvas(var6);
      Rect var13 = new Rect(0, 0, var0.getWidth(), var0.getHeight());
      RectF var9 = new RectF(var13);
      float var10 = (float)var3;
      var9.offset(var10, var10);
      Paint var11 = new Paint();
      var11.setAntiAlias(true);
      var8.drawARGB(0, 0, 0, 0);
      float var12 = (float)(var1 + var3);
      var8.drawRoundRect(var9, var12, var12, var11);
      var11.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
      var8.drawBitmap(var7, var13, var9, var11);
      var11.setColor(var2);
      var11.setStyle(Style.STROKE);
      var11.setStrokeWidth(var10);
      var8.drawRoundRect(var9, var12, var12, var11);
      return var6;
   }

   public static Bitmap getBitmapWithBorder(Bitmap var0, int var1, int var2) {
      if (var0 == null) {
         return null;
      } else {
         int var3 = var0.getWidth();
         int var4 = var2 * 2;
         Bitmap var5 = Bitmap.createBitmap(var3 + var4, var0.getHeight() + var4, Config.ARGB_8888);
         Canvas var6 = new Canvas(var5);
         Rect var7 = new Rect(0, 0, var0.getWidth(), var0.getHeight());
         RectF var8 = new RectF(var7);
         float var9 = (float)var2;
         var8.offset(var9, var9);
         Paint var10 = new Paint();
         var10.setAntiAlias(true);
         var6.drawColor(var1);
         var10.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
         var6.drawBitmap(var0, var7, var8, var10);
         return var5;
      }
   }

   static float getCielabDistance(MediaOp.CIELAB_COLOR var0, MediaOp.CIELAB_COLOR var1) {
      return (var0.L - var1.L) * (var0.L - var1.L) + (var0.a - var1.a) * (var0.a - var1.a) + (var0.b - var1.b) * (var0.b - var1.b);
   }

   public static Drawable getDrawableFromBitmap(Context var0, Bitmap var1) {
      return new BitmapDrawable(var0.getResources(), var1);
   }

   public static Drawable getDrawableFromPath(String var0) {
      return Drawable.createFromPath(var0);
   }

   public static float getImageHeightWidthRatio(Bitmap var0) {
      float var1 = 0.0F;
      if (var0 == null) {
         return 0.0F;
      } else {
         int var2 = var0.getHeight();
         int var3 = var0.getWidth();
         float var4 = var1;
         if (var2 > 0) {
            var4 = var1;
            if (var3 > 0) {
               var4 = (float)var2 / (float)var3;
            }
         }

         return var4;
      }
   }

   public static float getImageHeightWidthRatio(String var0) {
      boolean var1 = checkImageExist(var0);
      float var2 = 0.0F;
      if (!var1) {
         return 0.0F;
      } else {
         Options var3 = new Options();
         var3.inJustDecodeBounds = true;
         BitmapFactory.decodeFile(var0, var3);
         int var4 = var3.outHeight;
         int var5 = var3.outWidth;
         float var6 = var2;
         if (var4 > 0) {
            var6 = var2;
            if (var5 > 0) {
               var6 = (float)var4 / (float)var5;
            }
         }

         return var6;
      }
   }

   public static int getImageSampleRate(String var0, int var1, int var2) {
      Options var3 = new Options();
      var3.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(var0, var3);
      return calculateInSampleSize(var3.outWidth, var3.outHeight, var1, var2);
   }

   public static Cursor getMediaCursor(Context var0, int var1) {
      ContentResolver var3 = var0.getContentResolver();
      MediaOp.CursorQuery var2 = prepareCursorQuery(var1);
      return var2 != null ? var3.query(var2.uri, var2.columns, var2.selection, var2.selectionArgs, var2.sortOrder) : null;
   }

   public static ArrayList<MediaOp.MediaInfo> getMediaInfoFromCursor(Cursor var0, int var1, ArrayList<MediaOp.MediaInfo> var2, int var3, int var4) {
      if (var2 == null) {
         var2 = new ArrayList();
      }

      var2.clear();
      ArrayList var5 = new ArrayList();
      int var6;
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               var6 = var0.getColumnIndexOrThrow("_data");
               var4 = var0.getColumnIndexOrThrow("_id");
               var3 = var0.getColumnIndexOrThrow("mime_type");
            } else {
               var6 = var0.getColumnIndexOrThrow("_data");
               var4 = var0.getColumnIndexOrThrow("_id");
               var3 = var0.getColumnIndexOrThrow("mime_type");
            }
         } else {
            var6 = var0.getColumnIndexOrThrow("_data");
            var4 = var0.getColumnIndexOrThrow("_id");
            var3 = var0.getColumnIndexOrThrow("mime_type");
         }
      } else {
         var6 = var0.getColumnIndexOrThrow("_data");
         var4 = var0.getColumnIndexOrThrow("_id");
         var3 = var0.getColumnIndexOrThrow("mime_type");
      }

      for(int var7 = 0; var7 < var0.getCount(); ++var7) {
         var0.moveToPosition(var7);
         int var8 = var0.getInt(var4);
         String var9 = var0.getString(var6);
         String var10 = FileOp.getFilenameFromPath(var9);
         String var11 = FileOp.getFolderChainFromPath(var9);
         String var12 = var11.substring(var11.lastIndexOf(File.separator) + 1);
         String var13 = var0.getString(var3);
         MediaOp.MediaInfo var14;
         if (!var5.contains(var11)) {
            var14 = new MediaOp.MediaInfo();
            var14.type = 0;
            var14.mime_type = var13;
            var14.id = var8;
            var14.media_path = var9;
            var14.folder_path = var11;
            var14.folder_name = var12;
            var2.add(var14);
            var5.add(var11);
         }

         var14 = new MediaOp.MediaInfo();
         if (var1 != 0) {
            if (var1 != 1) {
               if (var1 != 2) {
                  if (var13.contains("audio")) {
                     var14.type = 3;
                  }

                  if (var13.contains("image")) {
                     var14.type = 1;
                  }

                  if (var13.contains("video")) {
                     var14.type = 2;
                  }
               } else {
                  var14.type = 3;
               }
            } else {
               var14.type = 2;
            }
         } else {
            var14.type = 1;
         }

         var14.mime_type = var13;
         var14.id = var8;
         var14.media_path = var9;
         var14.media_name = var10;
         var14.folder_path = var11;
         var14.folder_name = var12;
         var2.add(var14);
      }

      return var2;
   }

   public static int getMediaTypeFromPath(String var0) {
      var0 = getMimeType(var0);
      if (var0 == null) {
         return 0;
      } else {
         byte var1 = var0.contains("image");
         if (var0.contains("audio")) {
            var1 = 2;
         }

         if (var0.contains("video")) {
            var1 = 3;
         }

         return var1;
      }
   }

   public static String getMimeType(String var0) {
      var0 = FileOp.getFileExtension(var0);
      if (var0 != null) {
         var0 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var0.toLowerCase());
      } else {
         var0 = null;
      }

      return var0;
   }

   public static void getPhotoThumbFromCameraWithIntent(Activity var0, int var1, String var2) {
      Intent var3 = new Intent();
      var3.setAction("android.media.action.IMAGE_CAPTURE");
      var0.startActivityForResult(var3, var1);
   }

   public static void getPhotoUriFromGalleryWithIntent(Activity var0, int var1, String var2) {
      Intent var3 = new Intent();
      var3.setType("image/*");
      var3.setAction("android.intent.action.GET_CONTENT");
      var0.startActivityForResult(Intent.createChooser(var3, var2), var1);
   }

   public static Bitmap getSampledBitmapFromPath(String var0, int var1, int var2) {
      boolean var3 = FileOp.checkFileExist(var0);
      Bitmap var4 = null;
      if (!var3) {
         return null;
      } else {
         Options var5 = new Options();
         var5.inJustDecodeBounds = true;
         BitmapFactory.decodeFile(var0, var5);
         var5.inSampleSize = calculateInSampleSize(var5.outWidth, var5.outHeight, var1, var2);
         var5.inJustDecodeBounds = false;

         Bitmap var6;
         Bitmap var8;
         try {
            var6 = BitmapFactory.decodeFile(var0, var5);
         } catch (OutOfMemoryError var7) {
            while(var4 == null) {
               var5.inSampleSize *= 2;
               var4 = BitmapFactory.decodeFile(var0, var5);
            }

            var8 = var4;
            return var8;
         }

         var8 = var6;
         return var8;
      }
   }

   public static Point getScreenSizePx(Context var0) {
      Display var2 = ((WindowManager)var0.getSystemService("window")).getDefaultDisplay();
      Point var1 = new Point();
      var2.getSize(var1);
      return var1;
   }

   public static Bitmap getThumbnailFromImage(String var0, int var1, int var2) {
      return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(var0), var1, var2);
   }

   public static Bitmap getThumbnailFromPath(Context var0, String var1, int var2, int var3, int var4) {
      int var5 = getMediaTypeFromPath(var1);
      Bitmap var6;
      if (var5 != 0) {
         if (var5 != 1) {
            if (var5 == 2) {
               var6 = BitmapFactory.decodeResource(var0.getResources(), R.mipmap.ic_audio);
               return var6;
            }

            if (var5 == 3) {
               var6 = getThumbnailFromVideo(var1, var4);
               return var6;
            }
         } else if (var2 > 0 && var3 > 0) {
            var6 = getThumbnailFromImage(var1, var2, var3);
            return var6;
         }

         var6 = null;
      } else {
         var6 = BitmapFactory.decodeResource(var0.getResources(), R.mipmap.ic_file_unknown);
      }

      return var6;
   }

   public static Bitmap getThumbnailFromVideo(String var0) {
      return getThumbnailFromVideo(var0, 3);
   }

   public static Bitmap getThumbnailFromVideo(String var0, int var1) {
      Bitmap var2 = ThumbnailUtils.createVideoThumbnail(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Canvas var9 = new Canvas(var2);
         Paint var3 = new Paint();
         float var4 = (float)var2.getWidth() / 2.0F;
         float var5 = (float)var2.getHeight() / 2.0F;
         float var6 = (float)Math.min(var2.getWidth(), var2.getHeight()) / 8.0F;
         var3.setColor(-2013265920);
         var3.setStyle(Style.FILL);
         var3.setAntiAlias(true);
         var9.drawCircle(var4, var5, var6, var3);
         Path var7 = new Path();
         var6 *= 0.7F;
         var7.moveTo(var6, 0.0F);
         float var8 = -var6 / 2.0F;
         var7.lineTo(var8, 0.866F * var6);
         var7.lineTo(var8, var6 * -0.866F);
         var7.close();
         var7.offset(var4, var5);
         var3.setColor(-1996488705);
         var3.setStyle(Style.FILL);
         var9.drawPath(var7, var3);
         return var2;
      }
   }

   public static String getThumbnailPath(Context var0, String var1, int var2) {
      Cursor var8 = var0.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{var1}, (String)null);
      long var3;
      if (var8 != null && var8.moveToFirst()) {
         var3 = (long)var8.getInt(var8.getColumnIndex("_id"));
         var8.close();
      } else {
         var3 = -1L;
      }

      ContentResolver var6 = var0.getContentResolver();
      var1 = null;
      Cursor var5 = Thumbnails.queryMiniThumbnail(var6, var3, var2, (String[])null);
      String var7 = var1;
      if (var5 != null) {
         var7 = var1;
         if (var5.getCount() > 0) {
            var5.moveToFirst();
            var7 = var5.getString(var5.getColumnIndexOrThrow("_data"));
            var5.close();
         }
      }

      return var7;
   }

   private static MediaOp.CursorQuery prepareCursorQuery(int var0) {
      MediaOp.CursorQuery var1 = new MediaOp.CursorQuery();
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 3) {
               if (var0 != 4) {
                  if (var0 != 10) {
                     if (var0 != 11) {
                        return null;
                     }

                     var1.uri = android.provider.MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
                     var1.columns = new String[]{"video_id", "_data"};
                  } else {
                     var1.uri = Thumbnails.EXTERNAL_CONTENT_URI;
                     var1.columns = new String[]{"image_id", "_data"};
                  }
               } else {
                  var1.uri = Files.getContentUri("external");
                  var1.columns = new String[]{"_data", "date_added", "mime_type", "_id"};
                  var1.selection = "media_type=1 OR media_type=3 OR media_type=2";
               }
            } else {
               var1.uri = Files.getContentUri("external");
               var1.columns = new String[]{"_data", "date_added"};
               var1.selection = "media_type=1 OR media_type=3";
            }
         } else {
            var1.uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            var1.columns = new String[]{"_data", "_id", "mime_type", "_display_name"};
         }
      } else {
         var1.uri = Media.EXTERNAL_CONTENT_URI;
         var1.columns = new String[]{"_display_name", "_data", "_id", "mime_type", "_display_name"};
      }

      return var1;
   }

   public static String saveBitmapToJpg(Bitmap var0, String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var2);
      var3.append(".jpg");
      String var5 = var3.toString();
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(var5);
      saveBitmapToPath(var0, var4.toString(), CompressFormat.JPEG, 80);
      return var5;
   }

   public static void saveBitmapToJpgPath(Bitmap var0, String var1) {
      saveBitmapToPath(var0, var1, CompressFormat.JPEG, 80);
   }

   public static void saveBitmapToPath(Bitmap var0, String var1, CompressFormat var2, int var3) {
      try {
         FileOutputStream var4 = new FileOutputStream(var1);
         var0.compress(var2, var3, var4);
         var4.flush();
         var4.close();
      } catch (IOException var5) {
         Log.e("saveBitmapToJpg", var1, var5);
      }

   }

   public static void saveVideoThumbnail(String var0, String var1) {
      saveBitmapToJpgPath(getThumbnailFromVideo(var0), var1);
   }

   public static void setViewSizePx(View var0, int var1, int var2) {
      LayoutParams var3 = var0.getLayoutParams();
      var3.width = var1;
      var3.height = var2;
      var0.setLayoutParams(var3);
   }

   public static String shrinkImage(String var0, String var1, String var2, CompressFormat var3, int var4, int var5) {
      CompressFormat var6 = CompressFormat.JPEG;
      MimeTypeMap var12 = MimeTypeMap.getSingleton();
      getMimeType(var0);
      String var7 = FileOp.getFileExtension(var0);
      String var8 = "jpg";
      String var13;
      if (var7 != null && var7.length() > 0) {
         var13 = var12.getMimeTypeFromExtension(var7);
      } else {
         var7 = "jpg";
         var13 = "image/jpg";
      }

      if (var13 != null && var13.length() > 0) {
         var8 = var7;
         var7 = var13;
         var13 = var8;
      } else {
         var7 = "image/jpg";
         var13 = var8;
      }

      Bitmap var15 = getSampledBitmapFromPath(var0, var4, var5);
      if (var15 == null) {
         return null;
      } else {
         byte var11 = -1;
         switch(var7.hashCode()) {
         case -1487394660:
            if (var7.equals("image/jpeg")) {
               var11 = 6;
            }
            break;
         case -1487103447:
            if (var7.equals("image/tiff")) {
               var11 = 4;
            }
            break;
         case -879272239:
            if (var7.equals("image/bmp")) {
               var11 = 7;
            }
            break;
         case -879271467:
            if (var7.equals("image/cgm")) {
               var11 = 2;
            }
            break;
         case -879267568:
            if (var7.equals("image/gif")) {
               var11 = 1;
            }
            break;
         case -879264467:
            if (var7.equals("image/jpg")) {
               var11 = 5;
            }
            break;
         case -879258763:
            if (var7.equals("image/png")) {
               var11 = 0;
            }
            break;
         case 1130818877:
            if (var7.equals("image/g3fax")) {
               var11 = 3;
            }
         }

         CompressFormat var9;
         byte var14;
         if (var11 != 0 && var11 != 1) {
            var9 = CompressFormat.JPEG;
            var14 = 80;
         } else {
            var9 = CompressFormat.PNG;
            var14 = 100;
         }

         StringBuilder var16 = new StringBuilder();
         var16.append(var2);
         var16.append(".");
         var16.append(var13);
         var2 = var16.toString();
         var13 = FileOp.combinePath(var1, var2);
         CompressFormat var10 = var3;
         if (var3 == null) {
            var10 = var9;
         }

         saveBitmapToPath(var15, var13, var10, var14);
         return var2;
      }
   }

   public static String shrinkImageToJpg(String var0, String var1, String var2, int var3, int var4) {
      return shrinkImage(var0, var1, var2, CompressFormat.JPEG, var3, var4);
   }

   public static String[] shrinkImages(String[] var0, String var1, String var2, CompressFormat var3, int var4, int var5) {
      String[] var6 = new String[var0.length];

      for(int var7 = 0; var7 < var0.length; ++var7) {
         StringBuilder var8 = new StringBuilder();
         var8.append(var2);
         var8.append(TimeOp.UniqueTimeString());
         String var9 = var8.toString();
         var6[var7] = shrinkImage(var0[var7], var1, var9, var3, var4, var5);
      }

      return var6;
   }

   public static String shrinkUriToJpg(Context var0, Uri var1, String var2, String var3, int var4, int var5) {
      return shrinkImage(FileOp.getPathFromURI(var0, var1), var2, var3, CompressFormat.JPEG, var4, var5);
   }

   public static String[] shrinkUrisToJpgs(Context var0, ArrayList<Uri> var1, String var2, String var3, int var4, int var5) {
      String[] var6 = new String[var1.size()];

      for(int var7 = 0; var7 < var1.size(); ++var7) {
         Uri var8 = (Uri)var1.get(var7);
         StringBuilder var9 = new StringBuilder();
         var9.append(var3);
         var9.append(TimeOp.UniqueTimeString());
         var6[var7] = shrinkUriToJpg(var0, var8, var2, var9.toString(), var4, var5);
      }

      return var6;
   }

   public static void stampBitmap(Bitmap var0, Bitmap var1, int var2, int var3) {
      (new Canvas(var0)).drawBitmap(var1, (float)var2, (float)var3, (Paint)null);
   }

   public static boolean writeContentUriToPath(Context var0, Uri var1, String var2) {
      boolean var3;
      try {
         InputStream var6 = var0.getContentResolver().openInputStream(var1);
         FileOutputStream var5 = new FileOutputStream(var2);
         FileOp.copyStream(var6, var5);
         var5.flush();
         var5.close();
         var6.close();
      } catch (IOException var4) {
         var4.printStackTrace();
         var3 = false;
         return var3;
      }

      var3 = true;
      return var3;
   }

   public Bitmap resizeBitmap(Bitmap var1, int var2, int var3) {
      int var4 = var1.getWidth();
      int var5 = var1.getHeight();
      float var6 = (float)var2 / (float)var4;
      float var7 = (float)var3 / (float)var5;
      Matrix var8 = new Matrix();
      var8.postScale(var6, var7);
      Bitmap var9 = Bitmap.createBitmap(var1, 0, 0, var4, var5, var8, false);
      var1.recycle();
      return var9;
   }

   public Bitmap scaleBitmap(Bitmap var1, float var2) {
      int var3 = var1.getWidth();
      int var4 = var1.getHeight();
      Matrix var5 = new Matrix();
      var5.postScale(var2, var2);
      Bitmap var6 = Bitmap.createBitmap(var1, 0, 0, var3, var4, var5, false);
      var1.recycle();
      return var6;
   }

   public void scaleJpgPath(String var1, int var2) {
      saveBitmapToJpgPath(getSampledBitmapFromPath(var1, var2, var2), var1);
   }

   public static class CIELAB_COLOR {
      float L;
      float a;
      float b;
   }

   public static class CursorQuery {
      public String[] columns = null;
      public String selection = null;
      public String[] selectionArgs = null;
      public String sortOrder = null;
      public Uri uri = null;
   }

   public static class MediaInfo {
      public static final int TYPE_AUDIO = 3;
      public static final int TYPE_FOLDER = 0;
      public static final int TYPE_IMAGE = 1;
      public static final int TYPE_MEDIA = 4;
      public static final int TYPE_OTHER = 9;
      public static final int TYPE_VIDEO = 2;
      public String folder_name;
      public String folder_path;
      public Bitmap icon;
      public int id;
      public String media_name;
      public String media_path;
      public String mime_type;
      public int type;
   }

   public static class PlayStreamingVideo {
      final int MODE_DECODE_M3U8 = 2;
      final int MODE_IDLE = 0;
      final int MODE_PLAY_VIDEO = 3;
      final int MODE_READ_M3U8 = 1;
      Handler handler = null;
      int index_m3u8 = 0;
      int index_media = -1;
      ArrayList<String> m3u8 = null;
      ArrayList<String> media = null;
      int mode = 1;
      String play_list;
      FileOp.ReadTextFromWebFile readTextFromWebFile = null;
      VideoView video;

      public PlayStreamingVideo(String var1, VideoView var2) {
         (new MediaOp.PlayStreamingVideo.LooperThread()).start();
         this.video = var2;
         this.play_list = var1;
         this.m3u8 = new ArrayList();
         this.media = new ArrayList();
         this.m3u8.add(var1);
      }

      private void play_video() {
         Uri var1 = Uri.parse((String)this.media.get(this.index_media));
         this.video.setZOrderOnTop(true);
         this.video.setVideoURI(var1);
         this.video.start();
         this.video.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer var1) {
               MediaOp.PlayStreamingVideo var2 = PlayStreamingVideo.this;
               ++var2.index_media;
               if (PlayStreamingVideo.this.index_media < PlayStreamingVideo.this.media.size()) {
                  PlayStreamingVideo.this.handler.obtainMessage(3).sendToTarget();
               } else {
                  PlayStreamingVideo.this.m3u8.clear();
                  PlayStreamingVideo.this.media.clear();
                  PlayStreamingVideo.this.index_m3u8 = 0;
                  PlayStreamingVideo.this.index_media = 0;
                  PlayStreamingVideo.this.m3u8.add(PlayStreamingVideo.this.play_list);
                  PlayStreamingVideo.this.handler.obtainMessage(1).sendToTarget();
               }

            }
         });
      }

      private void read_m3u8(String var1) {
         this.readTextFromWebFile = new FileOp.ReadTextFromWebFile(var1) {
            public void OnTextRead(String var1) {
               PlayStreamingVideo.this.handler.obtainMessage(2, var1).sendToTarget();
            }
         };
      }

      public void start() {
         this.handler.obtainMessage(1).sendToTarget();
      }

      public void stop() {
         this.video.stopPlayback();
      }

      class LooperThread extends Thread {
         public void run() {
            Looper.prepare();
            PlayStreamingVideo.this.handler = new Handler() {
               public void handleMessage(Message var1) {
                  int var2 = var1.what;
                  if (var2 != 1) {
                     if (var2 != 2) {
                        if (var2 == 3) {
                           PlayStreamingVideo.this.play_video();
                        }
                     } else {
                        PlayStreamingVideo.this.readTextFromWebFile = null;
                        FileOp.getListsFromM3U8((String)var1.obj, PlayStreamingVideo.this.m3u8, PlayStreamingVideo.this.media);
                        if (PlayStreamingVideo.this.index_m3u8 < PlayStreamingVideo.this.m3u8.size()) {
                           PlayStreamingVideo.this.handler.obtainMessage(1).sendToTarget();
                        } else {
                           PlayStreamingVideo.this.handler.obtainMessage(3).sendToTarget();
                        }
                     }
                  } else {
                     PlayStreamingVideo.this.read_m3u8((String)PlayStreamingVideo.this.m3u8.get(PlayStreamingVideo.this.index_m3u8));
                     MediaOp.PlayStreamingVideo var3 = PlayStreamingVideo.this;
                     ++var3.index_m3u8;
                  }

               }
            };
            Looper.loop();
         }
      }
   }
}
