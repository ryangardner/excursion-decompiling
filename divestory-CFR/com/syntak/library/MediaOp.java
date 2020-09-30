/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.ColorMatrix
 *  android.graphics.ColorMatrixColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Point
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Xfermode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.media.ThumbnailUtils
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Files
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Images$Thumbnails
 *  android.provider.MediaStore$Video
 *  android.provider.MediaStore$Video$Media
 *  android.provider.MediaStore$Video$Thumbnails
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.Display
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.view.WindowManager
 *  android.webkit.MimeTypeMap
 *  android.widget.ImageView
 *  android.widget.VideoView
 */
package com.syntak.library;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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
import com.syntak.library.FileOp;
import com.syntak.library.R;
import com.syntak.library.TimeOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    static final int[] eedFilterAtkinson;
    static final int[] eedFilterBurkes;
    static final int[] eedFilterFS;
    static final int[] eedFilterFalseFS;
    static final int[] eedFilterJJN;
    static final int[] eedFilterNone;
    static final int[] eedFilterSierra;
    static final int[] eedFilterSierra2;
    static final int[] eedFilterSierraLite;
    static final int[] eedFilterStucki;

    static {
        eedFilterNone = new int[]{1};
        eedFilterFS = new int[]{0, 0, 7, 3, 5, 1};
        eedFilterFalseFS = new int[]{0, 3, 3, 2};
        eedFilterJJN = new int[]{0, 0, 0, 7, 5, 3, 5, 7, 5, 3, 1, 3, 5, 3, 1};
        eedFilterStucki = new int[]{0, 0, 0, 8, 4, 2, 4, 8, 4, 2, 1, 2, 4, 2, 1};
        eedFilterAtkinson = new int[]{0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0};
        eedFilterBurkes = new int[]{0, 0, 0, 8, 4, 2, 4, 8, 4, 2};
        eedFilterSierra = new int[]{0, 0, 0, 5, 3, 2, 4, 5, 4, 2, 0, 2, 3, 2, 0};
        eedFilterSierra2 = new int[]{0, 0, 0, 4, 3, 1, 2, 3, 2, 1};
        eedFilterSierraLite = new int[]{0, 0, 2, 1, 1, 0};
    }

    public static Bitmap BitMatrixToBitmap(BitMatrix bitMatrix) {
        int n = bitMatrix.getHeight();
        int n2 = bitMatrix.getWidth();
        Bitmap bitmap = Bitmap.createBitmap((int)n2, (int)n, (Bitmap.Config)Bitmap.Config.RGB_565);
        int n3 = 0;
        while (n3 < n2) {
            for (int i = 0; i < n; ++i) {
                int n4 = bitMatrix.get(n3, i) ? -16777216 : -1;
                bitmap.setPixel(n3, i, n4);
            }
            ++n3;
        }
        return bitmap;
    }

    public static int DimensionScaling(int n, int n2, int n3) {
        if (n2 != 0) return (int)((float)n / (float)n2 * (float)n3);
        return 0;
    }

    public static int DpToPx(Context context, int n) {
        float f = context.getResources().getDisplayMetrics().density;
        return Math.round((float)n * f);
    }

    public static int PxToDp(Context context, int n) {
        float f = context.getResources().getDisplayMetrics().density;
        return Math.round((float)n / f);
    }

    public static CIELAB_COLOR RgbToCielabColor(int n, int n2, int n3) {
        CIELAB_COLOR cIELAB_COLOR = new CIELAB_COLOR();
        float f = (float)n / 255.0f;
        float f2 = (float)n2 / 255.0f;
        float f3 = (float)n3 / 255.0f;
        f = f > 0.04045f ? (float)Math.pow((f + 0.055f) / 1.055f, 2.4000000953674316) : (f /= 12.92f);
        f2 = f2 > 0.04045f ? (float)Math.pow((f2 + 0.055f) / 1.055f, 2.4000000953674316) : (f2 /= 12.92f);
        f3 = f3 > 0.04045f ? (float)Math.pow(((double)f3 + 0.055) / 1.0549999475479126, 2.4000000953674316) : (f3 /= 12.92f);
        float f4 = f2 * 100.0f;
        float f5 = (0.4124f * (f *= 100.0f) + 0.3576f * f4 + 0.1805f * (f3 *= 100.0f)) / 95.047f;
        f2 = (0.2126f * f + 0.7152f * f4 + 0.0722f * f3) / 100.0f;
        f3 = (f * 0.0193f + f4 * 0.1192f + f3 * 0.9505f) / 108.883f;
        f = f5 > 0.008856f ? (float)Math.pow(f5, 0.3333333432674408) : f5 * 7.787f + 0.13793103f;
        f2 = f2 > 0.008856f ? (float)Math.pow(f2, 0.3333333432674408) : f2 * 7.787f + 0.13793103f;
        f3 = f3 > 0.008856f ? (float)Math.pow(f3, 0.3333333432674408) : f3 * 7.787f + 0.13793103f;
        cIELAB_COLOR.L = 116.0f * f2 - 16.0f;
        cIELAB_COLOR.a = (f - f2) * 500.0f;
        cIELAB_COLOR.b = (f2 - f3) * 200.0f;
        return cIELAB_COLOR;
    }

    public static boolean TakeAudioWithIntent(Context context, int n) {
        Intent intent = new Intent("android.provider.MediaStore.RECORD_SOUND");
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        ((Activity)context).startActivityForResult(intent, n);
        return true;
    }

    public static boolean TakePhotoWithIntent(Context context, int n, Uri uri) {
        return MediaOp.TakePhotoWithIntent(context, n, uri, 0);
    }

    public static boolean TakePhotoWithIntent(Context context, int n, Uri uri, int n2) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (n2 > 0) {
            intent.putExtra("android.intent.extras.CAMERA_FACING", n2);
        }
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        intent.putExtra("output", (Parcelable)uri);
        ((Activity)context).startActivityForResult(intent, n);
        return true;
    }

    public static boolean TakeVideoWithIntent(Context context, int n) {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        ((Activity)context).startActivityForResult(intent, n);
        return true;
    }

    public static Bitmap TextToBitmap(String string2, int n, int n2, int n3, int n4, int n5, Paint paint) {
        Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(n3);
        canvas.drawText(string2, (float)n4, (float)n5, paint);
        return bitmap;
    }

    public static Bitmap TextToQR(String string2, int n, ErrorCorrectionLevel errorCorrectionLevel) {
        QRCodeWriter qRCodeWriter = new QRCodeWriter();
        HashMap<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        try {
            return MediaOp.BitMatrixToBitmap(qRCodeWriter.encode(string2, BarcodeFormat.QR_CODE, n, n, hashMap));
        }
        catch (WriterException writerException) {
            writerException.printStackTrace();
            return null;
        }
    }

    public static boolean ViewFileWithIntent(Context context, String string2) {
        String string3 = MediaOp.getMimeType(string2);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse((String)string2), string3);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        context.startActivity(intent);
        return true;
    }

    public static int[] calculateFittingSize(Context context, int n, int n2, float f) {
        int[] arrn;
        int[] arrn2 = arrn = new int[2];
        arrn2[0] = 0;
        arrn2[1] = 0;
        context = MediaOp.getScreenSizePx(context);
        int n3 = context.x;
        int n4 = context.y;
        int n5 = n;
        if (n > n3) {
            n5 = n3;
        }
        n = n2;
        if (n2 > n4) {
            n = n4;
        }
        if (n > (n2 = (int)((float)n5 * f))) {
            n4 = n5;
        } else {
            n3 = (int)((float)n / f);
            n4 = n5;
            n2 = n;
            if (n5 > n3) {
                n4 = n3;
                n2 = n;
            }
        }
        arrn[0] = n4;
        arrn[1] = n2;
        return arrn;
    }

    public static int calculateInSampleSize(int n, int n2, int n3, int n4) {
        long l = n3 * n4;
        long l2 = n * n2;
        n = 1;
        while (l2 > l) {
            n *= 2;
            l2 /= 4L;
        }
        return n;
    }

    public static boolean checkImageExist(String string2) {
        boolean bl = FileOp.checkFileExist(string2);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
        int n = options.outHeight;
        int n2 = options.outWidth;
        bl = bl2;
        if (n < 0) return bl;
        bl = bl2;
        if (n2 < 0) return bl;
        return true;
    }

    public static Bitmap convertBitmapGreyscale(Bitmap bitmap) {
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        Paint paint = new Paint();
        paint.setColorFilter((ColorFilter)new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return bitmap2;
    }

    public static Bitmap convertBitmapMonochrome(Bitmap bitmap) {
        int n = bitmap.getWidth();
        int n2 = bitmap.getHeight();
        Bitmap bitmap2 = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)bitmap.getConfig());
        int n3 = 0;
        while (n3 < n2) {
            for (int i = 0; i < n; ++i) {
                int n4 = bitmap.getPixel(i, n3);
                int n5 = Color.alpha((int)n4);
                n4 = Color.red((int)n4) * 2 + Color.green((int)n4) * 5 + Color.blue((int)n4) >= 1024 ? 255 : 0;
                bitmap2.setPixel(i, n3, Color.argb((int)n5, (int)n4, (int)n4, (int)n4));
            }
            ++n3;
        }
        return bitmap2;
    }

    public static Loader<Cursor> createCursorLoader(Context context, int n, Bundle object) {
        object = MediaOp.prepareCursorQuery(n);
        if (object == null) return null;
        return new CursorLoader(context, object.uri, object.columns, object.selection, object.selectionArgs, object.sortOrder);
    }

    public static MediaPlayer displayAV(String string2) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(string2);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error open");
            stringBuilder.append(string2);
            Log.e((String)"displayAV", (String)stringBuilder.toString(), (Throwable)iOException);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });
        return mediaPlayer;
    }

    public static Object displayMedia(Context context, String string2, View view) {
        int n = MediaOp.getMediaTypeFromPath(string2);
        if (n == 1) {
            MediaOp.fitImageToView(context, (ImageView)view, string2, false, 0, 0, 0);
            return null;
        }
        if (n != 2 && n != 3) {
            return null;
        }
        MediaOp.displayAV(string2);
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public static Bitmap ditherBitmap(Bitmap var0, int var1_1, int var2_2, int var3_3) {
        block30 : {
            block31 : {
                var4_4 = var0.getWidth();
                var5_5 = var0.getHeight();
                var6_6 = Bitmap.createBitmap((int)var4_4, (int)var5_5, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                var7_7 = (int)Math.pow(2.0, var1_1);
                var8_8 = new int[var7_7];
                var9_9 = new CIELAB_COLOR[var7_7];
                var10_14 = 5;
                var11_15 = 255.0f;
                if (var1_1 != 8) {
                    if (var1_1 != 16) {
                        var10_14 = 1;
                        var12_16 = 1;
                        var13_17 = 1;
                        var14_18 = 255.0f;
                        var15_19 = 255.0f;
                    } else {
                        var12_16 = 5;
                        var11_15 = 8.2f;
                        var13_17 = 6;
                        var14_18 = 8.2f;
                        var15_19 = 4.0f;
                    }
                } else {
                    var14_18 = 85.0f;
                    var10_14 = 3;
                    var12_16 = 2;
                    var11_15 = 36.4f;
                    var13_17 = 3;
                    var15_19 = 336.4f;
                }
                if (var1_1 != 1) break block31;
                var8_8[0] = Color.rgb((int)0, (int)0, (int)0);
                var9_9[0] = MediaOp.RgbToCielabColor(0, 0, 0);
                var8_8[1] = Color.rgb((int)255, (int)255, (int)255);
                var9_9[1] = MediaOp.RgbToCielabColor(255, 255, 255);
                var12_16 = var4_4;
lbl35: // 2 sources:
                do {
                    block32 : {
                        var4_4 = var5_5;
                        var16_20 = 1;
                        var10_14 = 1;
                        var13_17 = 1;
                        var1_1 = -1;
                        switch (var2_2) {
                            default: {
                                var24_27 = MediaOp.eedFilterNone;
                                var1_1 = 0;
                                var2_2 = 0;
                                var10_14 = 0;
                                var13_17 = 1;
                                break block30;
                            }
                            case 10: {
                                var24_27 = MediaOp.eedFilterSierraLite;
                                var13_17 = 4;
                                var16_20 = 1;
                                var2_2 = var10_14;
                                var10_14 = var16_20;
                                break block30;
                            }
                            case 9: {
                                var24_27 = MediaOp.eedFilterSierra2;
                                var1_1 = -2;
                                var2_2 = 2;
                                break block32;
                            }
                            case 8: {
                                var24_27 = MediaOp.eedFilterSierra;
                                var10_14 = 2;
                                ** GOTO lbl68
                            }
                            case 7: {
                                var24_27 = MediaOp.eedFilterBurkes;
                                var10_14 = 1;
lbl68: // 2 sources:
                                var2_2 = 2;
                                var1_1 = -2;
                                var13_17 = 32;
                                break block30;
                            }
                            case 6: {
                                var24_27 = MediaOp.eedFilterAtkinson;
                                var2_2 = 2;
                                var10_14 = 2;
                                ** GOTO lbl93
                            }
                            case 5: {
                                var24_27 = MediaOp.eedFilterStucki;
                                var13_17 = 42;
                                ** GOTO lbl84
                            }
                            case 4: {
                                var24_27 = MediaOp.eedFilterJJN;
                                var13_17 = 48;
lbl84: // 2 sources:
                                var1_1 = -2;
                                var2_2 = 2;
                                var10_14 = 2;
                                break block30;
                            }
                            case 3: {
                                var24_27 = MediaOp.eedFilterFalseFS;
                                var1_1 = 0;
                                var10_14 = 1;
                                var2_2 = var13_17;
lbl93: // 2 sources:
                                var13_17 = 8;
                                break block30;
                            }
                            case 1: 
                        }
                        var24_27 = MediaOp.eedFilterFS;
                        var2_2 = var16_20;
                    }
                    var10_14 = 1;
                    var13_17 = 16;
                    break block30;
                    break;
                } while (true);
            }
            var17_21 = 0;
            var18_22 = 0;
            var19_23 = var10_14;
            var1_1 = var4_4;
            block12 : do {
                if (var17_21 >= (int)Math.pow(2.0, var20_24 = (double)var19_23) || var18_22 >= var7_7) {
                    var12_16 = var1_1;
                    ** continue;
                }
                var10_14 = var17_21 == 0 ? 0 : (var17_21 == (int)Math.pow(2.0, var20_24) - 1 ? 255 : (int)((float)var17_21 * var11_15));
                var22_25 = 0;
                do {
                    if (var22_25 < (int)Math.pow(2.0, var20_24 = (double)var13_17) && var18_22 < var7_7) {
                        var4_4 = var22_25 == 0 ? 0 : (var22_25 == (int)Math.pow(2.0, var20_24) - 1 ? 255 : (int)((float)var17_21 * var15_19));
                    } else {
                        ++var17_21;
                        continue block12;
                    }
                    for (var23_26 = 0; var23_26 < (int)Math.pow(2.0, var20_24 = (double)var12_16) && var18_22 < var7_7; ++var18_22, ++var23_26) {
                        var16_20 = var23_26 == 0 ? 0 : (var23_26 == (int)Math.pow(2.0, var20_24) - 1 ? 255 : (int)((float)var17_21 * var14_18));
                        var8_8[var18_22] = Color.rgb((int)var10_14, (int)var4_4, (int)var16_20);
                        var9_9[var18_22] = MediaOp.RgbToCielabColor(var10_14, var4_4, var16_20);
                    }
                    ++var22_25;
                } while (true);
                break;
            } while (true);
        }
        var22_25 = var12_16 + (var2_2 - var1_1);
        var16_20 = (var4_4 + var10_14) * var22_25;
        var25_28 = new int[var16_20];
        var26_29 = new int[var16_20];
        var27_30 = new int[var16_20];
        var5_5 = var4_4;
        var4_4 = 0;
        var28_31 = var6_6;
        var16_20 = var13_17;
        var6_6 = var25_28;
        var23_26 = var7_7;
        var13_17 = var10_14;
        var17_21 = var2_2;
        var18_22 = var1_1;
        var25_28 = var24_27;
        block15 : do {
            var19_23 = 0;
            if (var4_4 >= var5_5) return var28_32;
            var10_14 = var12_16;
            var24_27 = var28_32;
            var12_16 = var16_20;
            var16_20 = var5_5;
            var28_33 = var9_10;
            var2_2 = var23_26;
            var1_1 = var13_17;
            var13_17 = var18_22;
            do {
                if (var19_23 < var10_14) {
                    var18_22 = var0.getPixel(var19_23, var4_4);
                    var7_7 = Color.red((int)var18_22);
                    var23_26 = Color.green((int)var18_22);
                    var18_22 = Color.blue((int)var18_22);
                    var29_35 = var4_4 * var22_25;
                    var5_5 = var19_23 - var13_17 + var29_35;
                    var30_36 = (var6_6[var5_5] + var7_7 * var12_16) / var12_16;
                    var31_37 = var26_29[var5_5];
                    var7_7 = var10_14;
                    var10_14 = (var31_37 + var23_26 * var12_16) / var12_16;
                    var18_22 = (var27_30[var5_5] + var18_22 * var12_16) / var12_16;
                    var31_37 = Math.min(255, Math.max(var30_36, 0));
                    var32_38 = Math.min(255, Math.max(var10_14, 0));
                    var33_39 = Math.min(255, Math.max(var18_22, 0));
                    var9_12 = MediaOp.RgbToCielabColor(var31_37, var32_38, var33_39);
                    var23_26 = 65535;
                    var18_22 = 0;
                    for (var10_14 = 0; var10_14 < var2_2; ++var10_14) {
                        var30_36 = (int)MediaOp.getCielabDistance(var9_12, (CIELAB_COLOR)var28_33[var10_14]);
                        var5_5 = var23_26;
                        if (var30_36 < var23_26) {
                            var5_5 = var30_36;
                            var18_22 = var10_14;
                        }
                        var23_26 = var5_5;
                    }
                    var10_14 = var8_8[var18_22];
                    var24_27.setPixel(var19_23, var4_4, var10_14);
                    var34_40 = Color.red((int)var8_8[var18_22]);
                    var30_36 = Color.green((int)var8_8[var18_22]);
                    var35_41 = Color.blue((int)var8_8[var18_22]);
                    var10_14 = 0;
                    var23_26 = 0;
                    var18_22 = var22_25;
                    var22_25 = var10_14;
                    var10_14 = var13_17;
                } else {
                    ++var4_4;
                    var18_22 = var13_17;
                    var13_17 = var1_1;
                    var23_26 = var2_2;
                    var9_13 = var28_33;
                    var5_5 = var16_20;
                    var16_20 = var12_16;
                    var28_34 = var24_27;
                    var12_16 = var10_14;
                    continue block15;
                }
                while (var22_25 <= var1_1) {
                    var13_17 = var23_26;
                    for (var5_5 = var10_14; var5_5 <= var17_21; ++var5_5, ++var13_17) {
                        var36_42 = (long)var3_3 * (long)var25_28[var13_17];
                        var23_26 = var22_25 * var18_22 + var5_5 + var19_23 - var10_14 + var29_35;
                        var6_6[var23_26] = (int)((long)var6_6[var23_26] + ((long)(var31_37 - var34_40) * var36_42 >> 7));
                        var26_29[var23_26] = (int)((long)var26_29[var23_26] + ((long)(var32_38 - var30_36) * var36_42 >> 7));
                        var27_30[var23_26] = (int)((long)var27_30[var23_26] + ((long)(var33_39 - var35_41) * var36_42 >> 7));
                    }
                    ++var22_25;
                    var23_26 = var13_17;
                }
                ++var19_23;
                var13_17 = var10_14;
                var22_25 = var18_22;
                var10_14 = var7_7;
            } while (true);
            break;
        } while (true);
    }

    public static Bitmap drawCircil(int n, int n2, int n3, int n4, int n5) {
        float f;
        Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        float f2 = (float)bitmap.getWidth() / 2.0f;
        float f3 = f2 < (f = (float)bitmap.getHeight() / 2.0f) ? f2 - (float)n4 : f - (float)n4;
        paint.setColor(n5);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(f2, f, f3, paint);
        paint.setColor(n3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float)n4);
        paint.setAntiAlias(true);
        canvas.drawCircle(f2, f, f3, paint);
        return bitmap;
    }

    public static void fitDrawableToView(final ImageView imageView, final Drawable drawable2) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                int n = imageView.getMeasuredHeight();
                int n2 = imageView.getMeasuredWidth();
                drawable2.setBounds(0, 0, n2, n);
                imageView.setImageDrawable(drawable2);
                return true;
            }
        });
    }

    public static void fitImageToView(Context context, ImageView imageView, Bitmap bitmap) {
        MediaOp.fitImageToView(context, imageView, bitmap, false, 0, 0, 0);
    }

    public static void fitImageToView(Context arrn, final ImageView imageView, final Bitmap bitmap, final boolean bl, final int n, final int n2, final int n3) {
        if (bitmap == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int n4 = layoutParams.width;
        int n5 = layoutParams.height;
        float f = MediaOp.getImageHeightWidthRatio(bitmap);
        if (f <= 0.0f) {
            return;
        }
        if (f != 0.0f && n4 > 0 && n5 > 0) {
            arrn = MediaOp.calculateFittingSize((Context)arrn, n4, n5, f);
            n5 = arrn[0];
            layoutParams.height = arrn[1];
            layoutParams.width = n5;
            imageView.setLayoutParams(layoutParams);
        }
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                imageView.getMeasuredHeight();
                int n5 = imageView.getMeasuredWidth();
                int n22 = bitmap.getWidth();
                bitmap.getHeight();
                int n32 = n5 / n22;
                n32 = n2 * n22 / n5;
                int n4 = n3;
                n22 = n22 * n4 / n5;
                Bitmap bitmap3 = bitmap;
                if (bl && n4 > 0) {
                    bitmap3 = MediaOp.getBitmapRoundCornerBorder(bitmap3, n22, n, n32);
                } else {
                    Bitmap bitmap2 = bitmap3;
                    if (n3 > 0) {
                        bitmap2 = MediaOp.getBitmapRoundCorner(bitmap3, n22);
                    }
                    bitmap3 = bitmap2;
                    if (bl) {
                        bitmap3 = MediaOp.getBitmapWithBorder(bitmap2, n, n32);
                    }
                }
                imageView.setImageBitmap(bitmap3);
                return true;
            }
        });
    }

    public static void fitImageToView(Context context, ImageView imageView, String string2) {
        MediaOp.fitImageToView(context, imageView, string2, false, 0, 0, 0);
    }

    public static void fitImageToView(final Context context, final ImageView imageView, final String string2, final boolean bl, final int n, final int n2, final int n3) {
        if (!MediaOp.checkImageExist(string2)) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int n4 = layoutParams.width;
        int n5 = layoutParams.height;
        float f = MediaOp.getImageHeightWidthRatio(string2);
        if (f <= 0.0f) {
            return;
        }
        if (f != 0.0f && n4 > 0 && n5 > 0) {
            int[] arrn = MediaOp.calculateFittingSize(context, n4, n5, f);
            n5 = arrn[0];
            layoutParams.height = arrn[1];
            layoutParams.width = n5;
            imageView.setLayoutParams(layoutParams);
        }
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                int n5 = imageView.getMeasuredHeight();
                int n22 = imageView.getMeasuredWidth();
                Bitmap bitmap = MediaOp.getThumbnailFromPath(context, string2, n22, n5, 3);
                if (bitmap == null) return true;
                int n32 = bitmap.getWidth();
                bitmap.getHeight();
                n5 = n22 / n32;
                n5 = n2 * n32 / n22;
                int n4 = n3;
                n22 = n32 * n4 / n22;
                if (bl && n4 > 0) {
                    bitmap = MediaOp.getBitmapRoundCornerBorder(bitmap, n22, n, n5);
                } else {
                    Bitmap bitmap2 = bitmap;
                    if (n3 > 0) {
                        bitmap2 = MediaOp.getBitmapRoundCorner(bitmap, n22);
                    }
                    bitmap = bitmap2;
                    if (bl) {
                        bitmap = MediaOp.getBitmapWithBorder(bitmap2, n, n5);
                    }
                }
                imageView.setImageBitmap(bitmap);
                return true;
            }
        });
    }

    public static void fitImageToViewCircled(final Context context, final ImageView imageView, final String string2) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                int n = imageView.getMeasuredHeight();
                int n2 = imageView.getMeasuredWidth();
                Bitmap bitmap = MediaOp.getThumbnailFromPath(context, string2, n2, n, 3);
                imageView.setImageBitmap(MediaOp.getBitmapCirciled(bitmap));
                return true;
            }
        });
    }

    public static Bitmap getBitmapCenterCrop(Bitmap bitmap) {
        int n;
        if (bitmap == null) {
            return null;
        }
        int n2 = bitmap.getWidth();
        if (n2 == (n = bitmap.getHeight())) {
            return bitmap;
        }
        int n3 = n < n2 ? n : n2;
        Bitmap bitmap2 = Bitmap.createBitmap((int)n3, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawColor(-16777216);
        Rect rect = new Rect((n2 - n3) / 2, (n - n3) / 2, n3, n3);
        float f = n3;
        RectF rectF = new RectF(0.0f, 0.0f, f, f);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return bitmap2;
    }

    public static Bitmap getBitmapCenterCrop(Bitmap bitmap, int n) {
        int n2;
        if (bitmap == null) {
            return null;
        }
        int n3 = bitmap.getWidth();
        if (n3 == (n2 = bitmap.getHeight())) {
            return bitmap;
        }
        int n4 = Math.max(n3, n2);
        int n5 = Math.min(n3, n2);
        float f = n4;
        float f2 = n5;
        if (!((f /= f2) <= 1.2f)) {
            n5 = (int)(((f - 1.2f) / 2.0f + 1.0f) * f2);
        }
        Bitmap bitmap2 = Bitmap.createBitmap((int)n5, (int)n5, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        canvas.drawColor(n);
        n4 = Math.min(n3, n5);
        n = Math.min(n2, n5);
        Rect rect = new Rect((n3 - n4) / 2, (n2 - n) / 2, n4, n);
        RectF rectF = new RectF((float)(n5 - n4) / 2.0f, (float)(n5 - n) / 2.0f, (float)n4, (float)n);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return bitmap2;
    }

    public static Bitmap getBitmapCirciled(Bitmap bitmap) {
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        new RectF(rect);
        float f = (float)bitmap.getWidth() / 2.0f;
        float f2 = (float)bitmap.getHeight() / 2.0f;
        float f3 = Math.min(f, f2);
        paint.setColor(-12434878);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(f, f2, f3, paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
    }

    public static Bitmap getBitmapFitImageView(String string2, ImageView imageView) {
        int n = imageView.getHeight();
        int n2 = imageView.getWidth();
        imageView = new BitmapFactory.Options();
        imageView.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)imageView);
        int n3 = imageView.outHeight;
        int n4 = imageView.outWidth;
        if (n3 > n || n4 > n2) {
            imageView.inSampleSize = MediaOp.calculateInSampleSize(imageView.outWidth, imageView.outHeight, n2, n);
        }
        imageView.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)imageView);
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable2) {
        return ((BitmapDrawable)drawable2).getBitmap();
    }

    public static Bitmap getBitmapFromPath(String string2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static Bitmap getBitmapFromURL(String object) {
        URL uRL;
        block4 : {
            Object var1_1;
            block5 : {
                var1_1 = null;
                uRL = new URL((String)object);
                object = (HttpURLConnection)uRL.openConnection();
                try {
                    ((URLConnection)object).connect();
                    uRL = BitmapFactory.decodeStream((InputStream)((URLConnection)object).getInputStream());
                    break block4;
                }
                catch (IOException iOException) {
                    break block5;
                }
                catch (IOException iOException) {
                    object = null;
                }
            }
            Log.d((String)"getBitmapFromURL", (String)((Throwable)((Object)uRL)).toString());
            ((Throwable)((Object)uRL)).printStackTrace();
            uRL = var1_1;
        }
        if (object == null) return uRL;
        ((HttpURLConnection)object).disconnect();
        return uRL;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap((ContentResolver)context.getContentResolver(), (Uri)uri);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static float getBitmapHeightWidthRatio(Bitmap bitmap) {
        return (float)bitmap.getHeight() / (float)bitmap.getWidth();
    }

    public static Bitmap getBitmapRoundCorner(Bitmap bitmap, int n) {
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setColor(-12434878);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        float f = n;
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
    }

    public static Bitmap getBitmapRoundCornerBorder(Bitmap bitmap, int n, int n2, int n3) {
        int n4 = bitmap.getWidth();
        int n5 = n3 * 2;
        Bitmap bitmap2 = Bitmap.createBitmap((int)(n4 + n5), (int)(bitmap.getHeight() + n5), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Bitmap bitmap3 = MediaOp.getBitmapRoundCorner(bitmap, n);
        Canvas canvas = new Canvas(bitmap2);
        bitmap = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF((Rect)bitmap);
        float f = n3;
        rectF.offset(f, f);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        float f2 = n + n3;
        canvas.drawRoundRect(rectF, f2, f2, paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap3, (Rect)bitmap, rectF, paint);
        paint.setColor(n2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(f);
        canvas.drawRoundRect(rectF, f2, f2, paint);
        return bitmap2;
    }

    public static Bitmap getBitmapWithBorder(Bitmap bitmap, int n, int n2) {
        if (bitmap == null) {
            return null;
        }
        int n3 = bitmap.getWidth();
        int n4 = n2 * 2;
        Bitmap bitmap2 = Bitmap.createBitmap((int)(n3 + n4), (int)(bitmap.getHeight() + n4), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = n2;
        rectF.offset(f, f);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawColor(n);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return bitmap2;
    }

    static float getCielabDistance(CIELAB_COLOR cIELAB_COLOR, CIELAB_COLOR cIELAB_COLOR2) {
        return (cIELAB_COLOR.L - cIELAB_COLOR2.L) * (cIELAB_COLOR.L - cIELAB_COLOR2.L) + (cIELAB_COLOR.a - cIELAB_COLOR2.a) * (cIELAB_COLOR.a - cIELAB_COLOR2.a) + (cIELAB_COLOR.b - cIELAB_COLOR2.b) * (cIELAB_COLOR.b - cIELAB_COLOR2.b);
    }

    public static Drawable getDrawableFromBitmap(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable getDrawableFromPath(String string2) {
        return Drawable.createFromPath((String)string2);
    }

    public static float getImageHeightWidthRatio(Bitmap bitmap) {
        float f = 0.0f;
        if (bitmap == null) {
            return 0.0f;
        }
        int n = bitmap.getHeight();
        int n2 = bitmap.getWidth();
        float f2 = f;
        if (n <= 0) return f2;
        f2 = f;
        if (n2 <= 0) return f2;
        return (float)n / (float)n2;
    }

    public static float getImageHeightWidthRatio(String string2) {
        boolean bl = MediaOp.checkImageExist(string2);
        float f = 0.0f;
        if (!bl) {
            return 0.0f;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
        int n = options.outHeight;
        int n2 = options.outWidth;
        float f2 = f;
        if (n <= 0) return f2;
        f2 = f;
        if (n2 <= 0) return f2;
        return (float)n / (float)n2;
    }

    public static int getImageSampleRate(String string2, int n, int n2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
        return MediaOp.calculateInSampleSize(options.outWidth, options.outHeight, n, n2);
    }

    public static Cursor getMediaCursor(Context context, int n) {
        context = context.getContentResolver();
        CursorQuery cursorQuery = MediaOp.prepareCursorQuery(n);
        if (cursorQuery == null) return null;
        return context.query(cursorQuery.uri, cursorQuery.columns, cursorQuery.selection, cursorQuery.selectionArgs, cursorQuery.sortOrder);
    }

    public static ArrayList<MediaInfo> getMediaInfoFromCursor(Cursor cursor, int n, ArrayList<MediaInfo> arrayList, int n2, int n3) {
        int n4;
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.clear();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    n4 = cursor.getColumnIndexOrThrow("_data");
                    n3 = cursor.getColumnIndexOrThrow("_id");
                    n2 = cursor.getColumnIndexOrThrow("mime_type");
                } else {
                    n4 = cursor.getColumnIndexOrThrow("_data");
                    n3 = cursor.getColumnIndexOrThrow("_id");
                    n2 = cursor.getColumnIndexOrThrow("mime_type");
                }
            } else {
                n4 = cursor.getColumnIndexOrThrow("_data");
                n3 = cursor.getColumnIndexOrThrow("_id");
                n2 = cursor.getColumnIndexOrThrow("mime_type");
            }
        } else {
            n4 = cursor.getColumnIndexOrThrow("_data");
            n3 = cursor.getColumnIndexOrThrow("_id");
            n2 = cursor.getColumnIndexOrThrow("mime_type");
        }
        int n5 = 0;
        while (n5 < cursor.getCount()) {
            MediaInfo mediaInfo;
            cursor.moveToPosition(n5);
            int n6 = cursor.getInt(n3);
            String string2 = cursor.getString(n4);
            String string3 = FileOp.getFilenameFromPath(string2);
            String string4 = FileOp.getFolderChainFromPath(string2);
            String string5 = string4.substring(string4.lastIndexOf(File.separator) + 1);
            String string6 = cursor.getString(n2);
            if (!arrayList2.contains(string4)) {
                mediaInfo = new MediaInfo();
                mediaInfo.type = 0;
                mediaInfo.mime_type = string6;
                mediaInfo.id = n6;
                mediaInfo.media_path = string2;
                mediaInfo.folder_path = string4;
                mediaInfo.folder_name = string5;
                arrayList.add(mediaInfo);
                arrayList2.add(string4);
            }
            mediaInfo = new MediaInfo();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (string6.contains("audio")) {
                            mediaInfo.type = 3;
                        }
                        if (string6.contains("image")) {
                            mediaInfo.type = 1;
                        }
                        if (string6.contains("video")) {
                            mediaInfo.type = 2;
                        }
                    } else {
                        mediaInfo.type = 3;
                    }
                } else {
                    mediaInfo.type = 2;
                }
            } else {
                mediaInfo.type = 1;
            }
            mediaInfo.mime_type = string6;
            mediaInfo.id = n6;
            mediaInfo.media_path = string2;
            mediaInfo.media_name = string3;
            mediaInfo.folder_path = string4;
            mediaInfo.folder_name = string5;
            arrayList.add(mediaInfo);
            ++n5;
        }
        return arrayList;
    }

    public static int getMediaTypeFromPath(String string2) {
        if ((string2 = MediaOp.getMimeType(string2)) == null) {
            return 0;
        }
        int n = string2.contains("image");
        if (string2.contains("audio")) {
            n = 2;
        }
        if (!string2.contains("video")) return n;
        return 3;
    }

    public static String getMimeType(String string2) {
        if ((string2 = FileOp.getFileExtension(string2)) == null) return null;
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(string2.toLowerCase());
    }

    public static void getPhotoThumbFromCameraWithIntent(Activity activity, int n, String string2) {
        string2 = new Intent();
        string2.setAction("android.media.action.IMAGE_CAPTURE");
        activity.startActivityForResult((Intent)string2, n);
    }

    public static void getPhotoUriFromGalleryWithIntent(Activity activity, int n, String string2) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        activity.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)string2), n);
    }

    public static Bitmap getSampledBitmapFromPath(String string2, int n, int n2) {
        boolean bl = FileOp.checkFileExist(string2);
        Bitmap bitmap = null;
        if (!bl) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
        options.inSampleSize = MediaOp.calculateInSampleSize(options.outWidth, options.outHeight, n, n2);
        options.inJustDecodeBounds = false;
        try {
            Bitmap bitmap2 = BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
            return bitmap2;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            while (bitmap == null) {
                options.inSampleSize *= 2;
                bitmap = BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
            }
            return bitmap;
        }
    }

    public static Point getScreenSizePx(Context context) {
        context = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        context.getSize(point);
        return point;
    }

    public static Bitmap getThumbnailFromImage(String string2, int n, int n2) {
        return ThumbnailUtils.extractThumbnail((Bitmap)BitmapFactory.decodeFile((String)string2), (int)n, (int)n2);
    }

    public static Bitmap getThumbnailFromPath(Context context, String string2, int n, int n2, int n3) {
        int n4 = MediaOp.getMediaTypeFromPath(string2);
        if (n4 == 0) {
            return BitmapFactory.decodeResource((Resources)context.getResources(), (int)R.mipmap.ic_file_unknown);
        }
        if (n4 != 1) {
            if (n4 == 2) {
                return BitmapFactory.decodeResource((Resources)context.getResources(), (int)R.mipmap.ic_audio);
            }
            if (n4 != 3) return null;
            return MediaOp.getThumbnailFromVideo(string2, n3);
        }
        if (n <= 0) return null;
        if (n2 <= 0) return null;
        return MediaOp.getThumbnailFromImage(string2, n, n2);
    }

    public static Bitmap getThumbnailFromVideo(String string2) {
        return MediaOp.getThumbnailFromVideo(string2, 3);
    }

    public static Bitmap getThumbnailFromVideo(String string2, int n) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail((String)string2, (int)n);
        if (bitmap == null) {
            return null;
        }
        string2 = new Canvas(bitmap);
        Paint paint = new Paint();
        float f = (float)bitmap.getWidth() / 2.0f;
        float f2 = (float)bitmap.getHeight() / 2.0f;
        float f3 = (float)Math.min(bitmap.getWidth(), bitmap.getHeight()) / 8.0f;
        paint.setColor(-2013265920);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        string2.drawCircle(f, f2, f3, paint);
        Path path = new Path();
        path.moveTo(f3 *= 0.7f, 0.0f);
        float f4 = -f3 / 2.0f;
        path.lineTo(f4, 0.866f * f3);
        path.lineTo(f4, f3 * -0.866f);
        path.close();
        path.offset(f, f2);
        paint.setColor(-1996488705);
        paint.setStyle(Paint.Style.FILL);
        string2.drawPath(path, paint);
        return bitmap;
    }

    public static String getThumbnailPath(Context object, String string2, int n) {
        long l;
        String[] arrstring = new String[]{string2};
        string2 = object.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", arrstring, null);
        if (string2 != null && string2.moveToFirst()) {
            l = string2.getInt(string2.getColumnIndex("_id"));
            string2.close();
        } else {
            l = -1L;
        }
        object = object.getContentResolver();
        string2 = null;
        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail((ContentResolver)object, (long)l, (int)n, null);
        object = string2;
        if (cursor == null) return object;
        object = string2;
        if (cursor.getCount() <= 0) return object;
        cursor.moveToFirst();
        object = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        cursor.close();
        return object;
    }

    private static CursorQuery prepareCursorQuery(int n) {
        CursorQuery cursorQuery = new CursorQuery();
        if (n == 0) {
            cursorQuery.uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            cursorQuery.columns = new String[]{"_display_name", "_data", "_id", "mime_type", "_display_name"};
            return cursorQuery;
        }
        if (n == 1) {
            cursorQuery.uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            cursorQuery.columns = new String[]{"_data", "_id", "mime_type", "_display_name"};
            return cursorQuery;
        }
        if (n == 3) {
            cursorQuery.uri = MediaStore.Files.getContentUri((String)"external");
            cursorQuery.columns = new String[]{"_data", "date_added"};
            cursorQuery.selection = "media_type=1 OR media_type=3";
            return cursorQuery;
        }
        if (n == 4) {
            cursorQuery.uri = MediaStore.Files.getContentUri((String)"external");
            cursorQuery.columns = new String[]{"_data", "date_added", "mime_type", "_id"};
            cursorQuery.selection = "media_type=1 OR media_type=3 OR media_type=2";
            return cursorQuery;
        }
        if (n == 10) {
            cursorQuery.uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
            cursorQuery.columns = new String[]{"image_id", "_data"};
            return cursorQuery;
        }
        if (n != 11) {
            return null;
        }
        cursorQuery.uri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        cursorQuery.columns = new String[]{"video_id", "_data"};
        return cursorQuery;
    }

    public static String saveBitmapToJpg(Bitmap bitmap, String string2, String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append((String)charSequence);
        charSequence2.append(".jpg");
        charSequence2 = charSequence2.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append((String)charSequence2);
        MediaOp.saveBitmapToPath(bitmap, ((StringBuilder)charSequence).toString(), Bitmap.CompressFormat.JPEG, 80);
        return charSequence2;
    }

    public static void saveBitmapToJpgPath(Bitmap bitmap, String string2) {
        MediaOp.saveBitmapToPath(bitmap, string2, Bitmap.CompressFormat.JPEG, 80);
    }

    public static void saveBitmapToPath(Bitmap bitmap, String string2, Bitmap.CompressFormat compressFormat, int n) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(string2);
            bitmap.compress(compressFormat, n, (OutputStream)fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        }
        catch (IOException iOException) {
            Log.e((String)"saveBitmapToJpg", (String)string2, (Throwable)iOException);
        }
    }

    public static void saveVideoThumbnail(String string2, String string3) {
        MediaOp.saveBitmapToJpgPath(MediaOp.getThumbnailFromVideo(string2), string3);
    }

    public static void setViewSizePx(View view, int n, int n2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = n;
        layoutParams.height = n2;
        view.setLayoutParams(layoutParams);
    }

    public static String shrinkImage(String string2, String string3, String string4, Bitmap.CompressFormat compressFormat, int n, int n2) {
        Object object = Bitmap.CompressFormat.JPEG;
        object = MimeTypeMap.getSingleton();
        MediaOp.getMimeType(string2);
        Object object2 = FileOp.getFileExtension(string2);
        String string5 = "jpg";
        if (object2 != null && ((String)object2).length() > 0) {
            object = object.getMimeTypeFromExtension((String)object2);
        } else {
            object2 = "jpg";
            object = "image/jpg";
        }
        if (object != null && ((String)object).length() > 0) {
            string5 = object2;
            object2 = object;
            object = string5;
        } else {
            object2 = "image/jpg";
            object = string5;
        }
        string5 = MediaOp.getSampledBitmapFromPath(string2, n, n2);
        if (string5 == null) {
            return null;
        }
        n = -1;
        switch (((String)object2).hashCode()) {
            default: {
                break;
            }
            case 1130818877: {
                if (!((String)object2).equals("image/g3fax")) break;
                n = 3;
                break;
            }
            case -879258763: {
                if (!((String)object2).equals("image/png")) break;
                n = 0;
                break;
            }
            case -879264467: {
                if (!((String)object2).equals("image/jpg")) break;
                n = 5;
                break;
            }
            case -879267568: {
                if (!((String)object2).equals("image/gif")) break;
                n = 1;
                break;
            }
            case -879271467: {
                if (!((String)object2).equals("image/cgm")) break;
                n = 2;
                break;
            }
            case -879272239: {
                if (!((String)object2).equals("image/bmp")) break;
                n = 7;
                break;
            }
            case -1487103447: {
                if (!((String)object2).equals("image/tiff")) break;
                n = 4;
                break;
            }
            case -1487394660: {
                if (!((String)object2).equals("image/jpeg")) break;
                n = 6;
            }
        }
        if (n != 0 && n != 1) {
            string2 = Bitmap.CompressFormat.JPEG;
            n = 80;
        } else {
            string2 = Bitmap.CompressFormat.PNG;
            n = 100;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string4);
        ((StringBuilder)object2).append(".");
        ((StringBuilder)object2).append((String)object);
        string4 = ((StringBuilder)object2).toString();
        object = FileOp.combinePath(string3, string4);
        string3 = compressFormat;
        if (compressFormat == null) {
            string3 = string2;
        }
        MediaOp.saveBitmapToPath((Bitmap)string5, (String)object, (Bitmap.CompressFormat)string3, n);
        return string4;
    }

    public static String shrinkImageToJpg(String string2, String string3, String string4, int n, int n2) {
        return MediaOp.shrinkImage(string2, string3, string4, Bitmap.CompressFormat.JPEG, n, n2);
    }

    public static String[] shrinkImages(String[] arrstring, String string2, String string3, Bitmap.CompressFormat compressFormat, int n, int n2) {
        String[] arrstring2 = new String[arrstring.length];
        int n3 = 0;
        while (n3 < arrstring.length) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string3);
            charSequence.append(TimeOp.UniqueTimeString());
            charSequence = charSequence.toString();
            arrstring2[n3] = MediaOp.shrinkImage(arrstring[n3], string2, (String)charSequence, compressFormat, n, n2);
            ++n3;
        }
        return arrstring2;
    }

    public static String shrinkUriToJpg(Context context, Uri uri, String string2, String string3, int n, int n2) {
        return MediaOp.shrinkImage(FileOp.getPathFromURI(context, uri), string2, string3, Bitmap.CompressFormat.JPEG, n, n2);
    }

    public static String[] shrinkUrisToJpgs(Context context, ArrayList<Uri> arrayList, String string2, String string3, int n, int n2) {
        String[] arrstring = new String[arrayList.size()];
        int n3 = 0;
        while (n3 < arrayList.size()) {
            Uri uri = arrayList.get(n3);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(TimeOp.UniqueTimeString());
            arrstring[n3] = MediaOp.shrinkUriToJpg(context, uri, string2, stringBuilder.toString(), n, n2);
            ++n3;
        }
        return arrstring;
    }

    public static void stampBitmap(Bitmap bitmap, Bitmap bitmap2, int n, int n2) {
        new Canvas(bitmap).drawBitmap(bitmap2, (float)n, (float)n2, null);
    }

    public static boolean writeContentUriToPath(Context object, Uri object2, String string2) {
        try {
            object2 = object.getContentResolver().openInputStream((Uri)object2);
            object = new FileOutputStream(string2);
            FileOp.copyStream((InputStream)object2, (OutputStream)object);
            ((OutputStream)object).flush();
            ((OutputStream)object).close();
            ((InputStream)object2).close();
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public Bitmap resizeBitmap(Bitmap bitmap, int n, int n2) {
        int n3 = bitmap.getWidth();
        int n4 = bitmap.getHeight();
        float f = (float)n / (float)n3;
        float f2 = (float)n2 / (float)n4;
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        matrix = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)n3, (int)n4, (Matrix)matrix, (boolean)false);
        bitmap.recycle();
        return matrix;
    }

    public Bitmap scaleBitmap(Bitmap bitmap, float f) {
        int n = bitmap.getWidth();
        int n2 = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        matrix = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)n, (int)n2, (Matrix)matrix, (boolean)false);
        bitmap.recycle();
        return matrix;
    }

    public void scaleJpgPath(String string2, int n) {
        MediaOp.saveBitmapToJpgPath(MediaOp.getSampledBitmapFromPath(string2, n, n), string2);
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
        final int MODE_DECODE_M3U8;
        final int MODE_IDLE;
        final int MODE_PLAY_VIDEO;
        final int MODE_READ_M3U8;
        Handler handler = null;
        int index_m3u8 = 0;
        int index_media = -1;
        ArrayList<String> m3u8 = null;
        ArrayList<String> media = null;
        int mode = 1;
        String play_list;
        FileOp.ReadTextFromWebFile readTextFromWebFile = null;
        VideoView video;

        public PlayStreamingVideo(String string2, VideoView videoView) {
            this.MODE_IDLE = 0;
            this.MODE_READ_M3U8 = 1;
            this.MODE_DECODE_M3U8 = 2;
            this.MODE_PLAY_VIDEO = 3;
            new LooperThread().start();
            this.video = videoView;
            this.play_list = string2;
            this.m3u8 = new ArrayList();
            this.media = new ArrayList();
            this.m3u8.add(string2);
        }

        private void play_video() {
            Uri uri = Uri.parse((String)this.media.get(this.index_media));
            this.video.setZOrderOnTop(true);
            this.video.setVideoURI(uri);
            this.video.start();
            this.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                public void onCompletion(MediaPlayer object) {
                    object = PlayStreamingVideo.this;
                    ++object.index_media;
                    if (PlayStreamingVideo.this.index_media < PlayStreamingVideo.this.media.size()) {
                        PlayStreamingVideo.this.handler.obtainMessage(3).sendToTarget();
                        return;
                    }
                    PlayStreamingVideo.this.m3u8.clear();
                    PlayStreamingVideo.this.media.clear();
                    PlayStreamingVideo.this.index_m3u8 = 0;
                    PlayStreamingVideo.this.index_media = 0;
                    PlayStreamingVideo.this.m3u8.add(PlayStreamingVideo.this.play_list);
                    PlayStreamingVideo.this.handler.obtainMessage(1).sendToTarget();
                }
            });
        }

        private void read_m3u8(String string2) {
            this.readTextFromWebFile = new FileOp.ReadTextFromWebFile(string2){

                @Override
                public void OnTextRead(String string2) {
                    PlayStreamingVideo.this.handler.obtainMessage(2, (Object)string2).sendToTarget();
                }
            };
        }

        public void start() {
            this.handler.obtainMessage(1).sendToTarget();
        }

        public void stop() {
            this.video.stopPlayback();
        }

        class LooperThread
        extends Thread {
            LooperThread() {
            }

            @Override
            public void run() {
                Looper.prepare();
                PlayStreamingVideo.this.handler = new Handler(){

                    public void handleMessage(Message object) {
                        int n = object.what;
                        if (n == 1) {
                            PlayStreamingVideo.this.read_m3u8(PlayStreamingVideo.this.m3u8.get(PlayStreamingVideo.this.index_m3u8));
                            object = PlayStreamingVideo.this;
                            ++object.index_m3u8;
                            return;
                        }
                        if (n != 2) {
                            if (n != 3) {
                                return;
                            }
                            PlayStreamingVideo.this.play_video();
                            return;
                        }
                        PlayStreamingVideo.this.readTextFromWebFile = null;
                        FileOp.getListsFromM3U8((String)object.obj, PlayStreamingVideo.this.m3u8, PlayStreamingVideo.this.media);
                        if (PlayStreamingVideo.this.index_m3u8 < PlayStreamingVideo.this.m3u8.size()) {
                            PlayStreamingVideo.this.handler.obtainMessage(1).sendToTarget();
                            return;
                        }
                        PlayStreamingVideo.this.handler.obtainMessage(3).sendToTarget();
                    }
                };
                Looper.loop();
            }

        }

    }

}

