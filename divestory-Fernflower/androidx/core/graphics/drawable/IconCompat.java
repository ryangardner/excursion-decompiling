package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

public class IconCompat extends CustomVersionedParcelable {
   private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25F;
   private static final int AMBIENT_SHADOW_ALPHA = 30;
   private static final float BLUR_FACTOR = 0.010416667F;
   static final Mode DEFAULT_TINT_MODE;
   private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667F;
   private static final String EXTRA_INT1 = "int1";
   private static final String EXTRA_INT2 = "int2";
   private static final String EXTRA_OBJ = "obj";
   private static final String EXTRA_TINT_LIST = "tint_list";
   private static final String EXTRA_TINT_MODE = "tint_mode";
   private static final String EXTRA_TYPE = "type";
   private static final float ICON_DIAMETER_FACTOR = 0.9166667F;
   private static final int KEY_SHADOW_ALPHA = 61;
   private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334F;
   private static final String TAG = "IconCompat";
   public static final int TYPE_ADAPTIVE_BITMAP = 5;
   public static final int TYPE_BITMAP = 1;
   public static final int TYPE_DATA = 3;
   public static final int TYPE_RESOURCE = 2;
   public static final int TYPE_UNKNOWN = -1;
   public static final int TYPE_URI = 4;
   public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
   public byte[] mData = null;
   public int mInt1 = 0;
   public int mInt2 = 0;
   Object mObj1;
   public Parcelable mParcelable = null;
   public ColorStateList mTintList = null;
   Mode mTintMode;
   public String mTintModeStr;
   public int mType = -1;

   static {
      DEFAULT_TINT_MODE = Mode.SRC_IN;
   }

   public IconCompat() {
      this.mTintMode = DEFAULT_TINT_MODE;
      this.mTintModeStr = null;
   }

   private IconCompat(int var1) {
      this.mTintMode = DEFAULT_TINT_MODE;
      this.mTintModeStr = null;
      this.mType = var1;
   }

   public static IconCompat createFromBundle(Bundle var0) {
      int var1 = var0.getInt("type");
      IconCompat var2 = new IconCompat(var1);
      var2.mInt1 = var0.getInt("int1");
      var2.mInt2 = var0.getInt("int2");
      if (var0.containsKey("tint_list")) {
         var2.mTintList = (ColorStateList)var0.getParcelable("tint_list");
      }

      if (var0.containsKey("tint_mode")) {
         var2.mTintMode = Mode.valueOf(var0.getString("tint_mode"));
      }

      switch(var1) {
      case -1:
      case 1:
      case 5:
         var2.mObj1 = var0.getParcelable("obj");
         break;
      case 0:
      default:
         StringBuilder var3 = new StringBuilder();
         var3.append("Unknown type ");
         var3.append(var1);
         Log.w("IconCompat", var3.toString());
         return null;
      case 2:
      case 4:
      case 6:
         var2.mObj1 = var0.getString("obj");
         break;
      case 3:
         var2.mObj1 = var0.getByteArray("obj");
      }

      return var2;
   }

   public static IconCompat createFromIcon(Context var0, Icon var1) {
      Preconditions.checkNotNull(var1);
      int var2 = getType(var1);
      IconCompat var5;
      if (var2 != 2) {
         if (var2 != 4) {
            if (var2 != 6) {
               var5 = new IconCompat(-1);
               var5.mObj1 = var1;
               return var5;
            } else {
               return createWithAdaptiveBitmapContentUri(getUri(var1));
            }
         } else {
            return createWithContentUri(getUri(var1));
         }
      } else {
         String var3 = getResPackage(var1);

         try {
            var5 = createWithResource(getResources(var0, var3), var3, getResId(var1));
            return var5;
         } catch (NotFoundException var4) {
            throw new IllegalArgumentException("Icon resource cannot be found");
         }
      }
   }

   public static IconCompat createFromIcon(Icon var0) {
      Preconditions.checkNotNull(var0);
      int var1 = getType(var0);
      if (var1 != 2) {
         if (var1 != 4) {
            if (var1 != 6) {
               IconCompat var2 = new IconCompat(-1);
               var2.mObj1 = var0;
               return var2;
            } else {
               return createWithAdaptiveBitmapContentUri(getUri(var0));
            }
         } else {
            return createWithContentUri(getUri(var0));
         }
      } else {
         return createWithResource((Resources)null, getResPackage(var0), getResId(var0));
      }
   }

   public static IconCompat createFromIconOrNullIfZeroResId(Icon var0) {
      return getType(var0) == 2 && getResId(var0) == 0 ? null : createFromIcon(var0);
   }

   static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap var0, boolean var1) {
      int var2 = (int)((float)Math.min(var0.getWidth(), var0.getHeight()) * 0.6666667F);
      Bitmap var3 = Bitmap.createBitmap(var2, var2, Config.ARGB_8888);
      Canvas var4 = new Canvas(var3);
      Paint var5 = new Paint(3);
      float var6 = (float)var2;
      float var7 = 0.5F * var6;
      float var8 = 0.9166667F * var7;
      if (var1) {
         float var9 = 0.010416667F * var6;
         var5.setColor(0);
         var5.setShadowLayer(var9, 0.0F, var6 * 0.020833334F, 1023410176);
         var4.drawCircle(var7, var7, var8, var5);
         var5.setShadowLayer(var9, 0.0F, 0.0F, 503316480);
         var4.drawCircle(var7, var7, var8, var5);
         var5.clearShadowLayer();
      }

      var5.setColor(-16777216);
      BitmapShader var10 = new BitmapShader(var0, TileMode.CLAMP, TileMode.CLAMP);
      Matrix var11 = new Matrix();
      var11.setTranslate((float)(-(var0.getWidth() - var2) / 2), (float)(-(var0.getHeight() - var2) / 2));
      var10.setLocalMatrix(var11);
      var5.setShader(var10);
      var4.drawCircle(var7, var7, var8, var5);
      var4.setBitmap((Bitmap)null);
      return var3;
   }

   public static IconCompat createWithAdaptiveBitmap(Bitmap var0) {
      if (var0 != null) {
         IconCompat var1 = new IconCompat(5);
         var1.mObj1 = var0;
         return var1;
      } else {
         throw new IllegalArgumentException("Bitmap must not be null.");
      }
   }

   public static IconCompat createWithAdaptiveBitmapContentUri(Uri var0) {
      if (var0 != null) {
         return createWithAdaptiveBitmapContentUri(var0.toString());
      } else {
         throw new IllegalArgumentException("Uri must not be null.");
      }
   }

   public static IconCompat createWithAdaptiveBitmapContentUri(String var0) {
      if (var0 != null) {
         IconCompat var1 = new IconCompat(6);
         var1.mObj1 = var0;
         return var1;
      } else {
         throw new IllegalArgumentException("Uri must not be null.");
      }
   }

   public static IconCompat createWithBitmap(Bitmap var0) {
      if (var0 != null) {
         IconCompat var1 = new IconCompat(1);
         var1.mObj1 = var0;
         return var1;
      } else {
         throw new IllegalArgumentException("Bitmap must not be null.");
      }
   }

   public static IconCompat createWithContentUri(Uri var0) {
      if (var0 != null) {
         return createWithContentUri(var0.toString());
      } else {
         throw new IllegalArgumentException("Uri must not be null.");
      }
   }

   public static IconCompat createWithContentUri(String var0) {
      if (var0 != null) {
         IconCompat var1 = new IconCompat(4);
         var1.mObj1 = var0;
         return var1;
      } else {
         throw new IllegalArgumentException("Uri must not be null.");
      }
   }

   public static IconCompat createWithData(byte[] var0, int var1, int var2) {
      if (var0 != null) {
         IconCompat var3 = new IconCompat(3);
         var3.mObj1 = var0;
         var3.mInt1 = var1;
         var3.mInt2 = var2;
         return var3;
      } else {
         throw new IllegalArgumentException("Data must not be null.");
      }
   }

   public static IconCompat createWithResource(Context var0, int var1) {
      if (var0 != null) {
         return createWithResource(var0.getResources(), var0.getPackageName(), var1);
      } else {
         throw new IllegalArgumentException("Context must not be null.");
      }
   }

   public static IconCompat createWithResource(Resources var0, String var1, int var2) {
      if (var1 != null) {
         if (var2 != 0) {
            IconCompat var3 = new IconCompat(2);
            var3.mInt1 = var2;
            if (var0 != null) {
               try {
                  var3.mObj1 = var0.getResourceName(var2);
               } catch (NotFoundException var4) {
                  throw new IllegalArgumentException("Icon resource cannot be found");
               }
            } else {
               var3.mObj1 = var1;
            }

            return var3;
         } else {
            throw new IllegalArgumentException("Drawable resource ID must not be 0");
         }
      } else {
         throw new IllegalArgumentException("Package must not be null.");
      }
   }

   private static int getResId(Icon var0) {
      if (VERSION.SDK_INT >= 28) {
         return var0.getResId();
      } else {
         try {
            int var1 = (Integer)var0.getClass().getMethod("getResId").invoke(var0);
            return var1;
         } catch (IllegalAccessException var2) {
            Log.e("IconCompat", "Unable to get icon resource", var2);
            return 0;
         } catch (InvocationTargetException var3) {
            Log.e("IconCompat", "Unable to get icon resource", var3);
            return 0;
         } catch (NoSuchMethodException var4) {
            Log.e("IconCompat", "Unable to get icon resource", var4);
            return 0;
         }
      }
   }

   private static String getResPackage(Icon var0) {
      if (VERSION.SDK_INT >= 28) {
         return var0.getResPackage();
      } else {
         try {
            String var4 = (String)var0.getClass().getMethod("getResPackage").invoke(var0);
            return var4;
         } catch (IllegalAccessException var1) {
            Log.e("IconCompat", "Unable to get icon package", var1);
            return null;
         } catch (InvocationTargetException var2) {
            Log.e("IconCompat", "Unable to get icon package", var2);
            return null;
         } catch (NoSuchMethodException var3) {
            Log.e("IconCompat", "Unable to get icon package", var3);
            return null;
         }
      }
   }

   private static Resources getResources(Context var0, String var1) {
      if ("android".equals(var1)) {
         return Resources.getSystem();
      } else {
         PackageManager var5 = var0.getPackageManager();

         NameNotFoundException var10000;
         label31: {
            boolean var10001;
            ApplicationInfo var2;
            try {
               var2 = var5.getApplicationInfo(var1, 8192);
            } catch (NameNotFoundException var4) {
               var10000 = var4;
               var10001 = false;
               break label31;
            }

            if (var2 == null) {
               return null;
            }

            try {
               Resources var7 = var5.getResourcesForApplication(var2);
               return var7;
            } catch (NameNotFoundException var3) {
               var10000 = var3;
               var10001 = false;
            }
         }

         NameNotFoundException var6 = var10000;
         Log.e("IconCompat", String.format("Unable to find pkg=%s for icon", var1), var6);
         return null;
      }
   }

   private static int getType(Icon var0) {
      if (VERSION.SDK_INT >= 28) {
         return var0.getType();
      } else {
         StringBuilder var3;
         try {
            int var1 = (Integer)var0.getClass().getMethod("getType").invoke(var0);
            return var1;
         } catch (IllegalAccessException var4) {
            var3 = new StringBuilder();
            var3.append("Unable to get icon type ");
            var3.append(var0);
            Log.e("IconCompat", var3.toString(), var4);
            return -1;
         } catch (InvocationTargetException var5) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Unable to get icon type ");
            var2.append(var0);
            Log.e("IconCompat", var2.toString(), var5);
            return -1;
         } catch (NoSuchMethodException var6) {
            var3 = new StringBuilder();
            var3.append("Unable to get icon type ");
            var3.append(var0);
            Log.e("IconCompat", var3.toString(), var6);
            return -1;
         }
      }
   }

   private static Uri getUri(Icon var0) {
      if (VERSION.SDK_INT >= 28) {
         return var0.getUri();
      } else {
         try {
            Uri var4 = (Uri)var0.getClass().getMethod("getUri").invoke(var0);
            return var4;
         } catch (IllegalAccessException var1) {
            Log.e("IconCompat", "Unable to get icon uri", var1);
            return null;
         } catch (InvocationTargetException var2) {
            Log.e("IconCompat", "Unable to get icon uri", var2);
            return null;
         } catch (NoSuchMethodException var3) {
            Log.e("IconCompat", "Unable to get icon uri", var3);
            return null;
         }
      }
   }

   private InputStream getUriInputStream(Context var1) {
      Uri var2 = this.getUri();
      String var3 = var2.getScheme();
      if (!"content".equals(var3) && !"file".equals(var3)) {
         try {
            File var8 = new File((String)this.mObj1);
            FileInputStream var9 = new FileInputStream(var8);
            return var9;
         } catch (FileNotFoundException var4) {
            StringBuilder var10 = new StringBuilder();
            var10.append("Unable to load image from path: ");
            var10.append(var2);
            Log.w("IconCompat", var10.toString(), var4);
         }
      } else {
         try {
            InputStream var7 = var1.getContentResolver().openInputStream(var2);
            return var7;
         } catch (Exception var5) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Unable to load image from URI: ");
            var6.append(var2);
            Log.w("IconCompat", var6.toString(), var5);
         }
      }

      return null;
   }

   private Drawable loadDrawableInner(Context var1) {
      InputStream var2;
      switch(this.mType) {
      case 1:
         return new BitmapDrawable(var1.getResources(), (Bitmap)this.mObj1);
      case 2:
         String var3 = this.getResPackage();
         String var6 = var3;
         if (TextUtils.isEmpty(var3)) {
            var6 = var1.getPackageName();
         }

         Resources var7 = getResources(var1, var6);

         try {
            Drawable var5 = ResourcesCompat.getDrawable(var7, this.mInt1, var1.getTheme());
            return var5;
         } catch (RuntimeException var4) {
            Log.e("IconCompat", String.format("Unable to load resource 0x%08x from pkg=%s", this.mInt1, this.mObj1), var4);
            break;
         }
      case 3:
         return new BitmapDrawable(var1.getResources(), BitmapFactory.decodeByteArray((byte[])this.mObj1, this.mInt1, this.mInt2));
      case 4:
         var2 = this.getUriInputStream(var1);
         if (var2 != null) {
            return new BitmapDrawable(var1.getResources(), BitmapFactory.decodeStream(var2));
         }
         break;
      case 5:
         return new BitmapDrawable(var1.getResources(), createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
      case 6:
         var2 = this.getUriInputStream(var1);
         if (var2 != null) {
            if (VERSION.SDK_INT >= 26) {
               return new AdaptiveIconDrawable((Drawable)null, new BitmapDrawable(var1.getResources(), BitmapFactory.decodeStream(var2)));
            }

            return new BitmapDrawable(var1.getResources(), createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(var2), false));
         }
      }

      return null;
   }

   private static String typeToString(int var0) {
      switch(var0) {
      case 1:
         return "BITMAP";
      case 2:
         return "RESOURCE";
      case 3:
         return "DATA";
      case 4:
         return "URI";
      case 5:
         return "BITMAP_MASKABLE";
      case 6:
         return "URI_MASKABLE";
      default:
         return "UNKNOWN";
      }
   }

   public void addToShortcutIntent(Intent var1, Drawable var2, Context var3) {
      this.checkResource(var3);
      int var4 = this.mType;
      Bitmap var16;
      if (var4 == 1) {
         Bitmap var17 = (Bitmap)this.mObj1;
         var16 = var17;
         if (var2 != null) {
            var16 = var17.copy(var17.getConfig(), true);
         }
      } else if (var4 != 2) {
         if (var4 != 5) {
            throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
         }

         var16 = createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
      } else {
         label80: {
            NameNotFoundException var10000;
            label70: {
               boolean var10001;
               try {
                  var3 = var3.createPackageContext(this.getResPackage(), 0);
               } catch (NameNotFoundException var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label70;
               }

               if (var2 == null) {
                  try {
                     var1.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(var3, this.mInt1));
                     return;
                  } catch (NameNotFoundException var8) {
                     var10000 = var8;
                     var10001 = false;
                  }
               } else {
                  label66: {
                     Drawable var5;
                     label65: {
                        label82: {
                           label62:
                           try {
                              var5 = ContextCompat.getDrawable(var3, this.mInt1);
                              if (var5.getIntrinsicWidth() > 0 && var5.getIntrinsicHeight() > 0) {
                                 break label62;
                              }
                              break label82;
                           } catch (NameNotFoundException var12) {
                              var10000 = var12;
                              var10001 = false;
                              break label66;
                           }

                           try {
                              var16 = Bitmap.createBitmap(var5.getIntrinsicWidth(), var5.getIntrinsicHeight(), Config.ARGB_8888);
                              break label65;
                           } catch (NameNotFoundException var11) {
                              var10000 = var11;
                              var10001 = false;
                              break label66;
                           }
                        }

                        try {
                           var4 = ((ActivityManager)var3.getSystemService("activity")).getLauncherLargeIconSize();
                           var16 = Bitmap.createBitmap(var4, var4, Config.ARGB_8888);
                        } catch (NameNotFoundException var10) {
                           var10000 = var10;
                           var10001 = false;
                           break label66;
                        }
                     }

                     try {
                        var5.setBounds(0, 0, var16.getWidth(), var16.getHeight());
                        Canvas var6 = new Canvas(var16);
                        var5.draw(var6);
                        break label80;
                     } catch (NameNotFoundException var9) {
                        var10000 = var9;
                        var10001 = false;
                     }
                  }
               }
            }

            NameNotFoundException var14 = var10000;
            StringBuilder var15 = new StringBuilder();
            var15.append("Can't find package ");
            var15.append(this.mObj1);
            throw new IllegalArgumentException(var15.toString(), var14);
         }
      }

      if (var2 != null) {
         var4 = var16.getWidth();
         int var7 = var16.getHeight();
         var2.setBounds(var4 / 2, var7 / 2, var4, var7);
         var2.draw(new Canvas(var16));
      }

      var1.putExtra("android.intent.extra.shortcut.ICON", var16);
   }

   public void checkResource(Context var1) {
      if (this.mType == 2) {
         String var2 = (String)this.mObj1;
         if (!var2.contains(":")) {
            return;
         }

         String var3 = var2.split(":", -1)[1];
         String var4 = var3.split("/", -1)[0];
         var3 = var3.split("/", -1)[1];
         var2 = var2.split(":", -1)[0];
         int var5 = getResources(var1, var2).getIdentifier(var3, var4, var2);
         if (this.mInt1 != var5) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Id has changed for ");
            var6.append(var2);
            var6.append("/");
            var6.append(var3);
            Log.i("IconCompat", var6.toString());
            this.mInt1 = var5;
         }
      }

   }

   public Bitmap getBitmap() {
      if (this.mType == -1 && VERSION.SDK_INT >= 23) {
         Object var3 = this.mObj1;
         return var3 instanceof Bitmap ? (Bitmap)var3 : null;
      } else {
         int var2 = this.mType;
         if (var2 == 1) {
            return (Bitmap)this.mObj1;
         } else if (var2 == 5) {
            return createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
         } else {
            StringBuilder var1 = new StringBuilder();
            var1.append("called getBitmap() on ");
            var1.append(this);
            throw new IllegalStateException(var1.toString());
         }
      }
   }

   public int getResId() {
      if (this.mType == -1 && VERSION.SDK_INT >= 23) {
         return getResId((Icon)this.mObj1);
      } else if (this.mType == 2) {
         return this.mInt1;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("called getResId() on ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   public String getResPackage() {
      if (this.mType == -1 && VERSION.SDK_INT >= 23) {
         return getResPackage((Icon)this.mObj1);
      } else if (this.mType == 2) {
         return ((String)this.mObj1).split(":", -1)[0];
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("called getResPackage() on ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   public int getType() {
      return this.mType == -1 && VERSION.SDK_INT >= 23 ? getType((Icon)this.mObj1) : this.mType;
   }

   public Uri getUri() {
      if (this.mType == -1 && VERSION.SDK_INT >= 23) {
         return getUri((Icon)this.mObj1);
      } else {
         int var1 = this.mType;
         if (var1 != 4 && var1 != 6) {
            StringBuilder var2 = new StringBuilder();
            var2.append("called getUri() on ");
            var2.append(this);
            throw new IllegalStateException(var2.toString());
         } else {
            return Uri.parse((String)this.mObj1);
         }
      }
   }

   public Drawable loadDrawable(Context var1) {
      this.checkResource(var1);
      if (VERSION.SDK_INT >= 23) {
         return this.toIcon(var1).loadDrawable(var1);
      } else {
         Drawable var2 = this.loadDrawableInner(var1);
         if (var2 != null && (this.mTintList != null || this.mTintMode != DEFAULT_TINT_MODE)) {
            var2.mutate();
            DrawableCompat.setTintList(var2, this.mTintList);
            DrawableCompat.setTintMode(var2, this.mTintMode);
         }

         return var2;
      }
   }

   public void onPostParceling() {
      this.mTintMode = Mode.valueOf(this.mTintModeStr);
      Parcelable var1;
      switch(this.mType) {
      case -1:
         var1 = this.mParcelable;
         if (var1 == null) {
            throw new IllegalArgumentException("Invalid icon");
         }

         this.mObj1 = var1;
      case 0:
      default:
         break;
      case 1:
      case 5:
         var1 = this.mParcelable;
         if (var1 != null) {
            this.mObj1 = var1;
         } else {
            byte[] var2 = this.mData;
            this.mObj1 = var2;
            this.mType = 3;
            this.mInt1 = 0;
            this.mInt2 = var2.length;
         }
         break;
      case 2:
      case 4:
      case 6:
         this.mObj1 = new String(this.mData, Charset.forName("UTF-16"));
         break;
      case 3:
         this.mObj1 = this.mData;
      }

   }

   public void onPreParceling(boolean var1) {
      this.mTintModeStr = this.mTintMode.name();
      switch(this.mType) {
      case -1:
         if (var1) {
            throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
         }

         this.mParcelable = (Parcelable)this.mObj1;
      case 0:
      default:
         break;
      case 1:
      case 5:
         if (var1) {
            Bitmap var2 = (Bitmap)this.mObj1;
            ByteArrayOutputStream var3 = new ByteArrayOutputStream();
            var2.compress(CompressFormat.PNG, 90, var3);
            this.mData = var3.toByteArray();
         } else {
            this.mParcelable = (Parcelable)this.mObj1;
         }
         break;
      case 2:
         this.mData = ((String)this.mObj1).getBytes(Charset.forName("UTF-16"));
         break;
      case 3:
         this.mData = (byte[])this.mObj1;
         break;
      case 4:
      case 6:
         this.mData = this.mObj1.toString().getBytes(Charset.forName("UTF-16"));
      }

   }

   public IconCompat setTint(int var1) {
      return this.setTintList(ColorStateList.valueOf(var1));
   }

   public IconCompat setTintList(ColorStateList var1) {
      this.mTintList = var1;
      return this;
   }

   public IconCompat setTintMode(Mode var1) {
      this.mTintMode = var1;
      return this;
   }

   public Bundle toBundle() {
      Bundle var1 = new Bundle();
      switch(this.mType) {
      case -1:
         var1.putParcelable("obj", (Parcelable)this.mObj1);
         break;
      case 0:
      default:
         throw new IllegalArgumentException("Invalid icon");
      case 1:
      case 5:
         var1.putParcelable("obj", (Bitmap)this.mObj1);
         break;
      case 2:
      case 4:
      case 6:
         var1.putString("obj", (String)this.mObj1);
         break;
      case 3:
         var1.putByteArray("obj", (byte[])this.mObj1);
      }

      var1.putInt("type", this.mType);
      var1.putInt("int1", this.mInt1);
      var1.putInt("int2", this.mInt2);
      ColorStateList var2 = this.mTintList;
      if (var2 != null) {
         var1.putParcelable("tint_list", var2);
      }

      Mode var3 = this.mTintMode;
      if (var3 != DEFAULT_TINT_MODE) {
         var1.putString("tint_mode", var3.name());
      }

      return var1;
   }

   @Deprecated
   public Icon toIcon() {
      return this.toIcon((Context)null);
   }

   public Icon toIcon(Context var1) {
      Icon var4;
      switch(this.mType) {
      case -1:
         return (Icon)this.mObj1;
      case 0:
      default:
         throw new IllegalArgumentException("Unknown type");
      case 1:
         var4 = Icon.createWithBitmap((Bitmap)this.mObj1);
         break;
      case 2:
         var4 = Icon.createWithResource(this.getResPackage(), this.mInt1);
         break;
      case 3:
         var4 = Icon.createWithData((byte[])this.mObj1, this.mInt1, this.mInt2);
         break;
      case 4:
         var4 = Icon.createWithContentUri((String)this.mObj1);
         break;
      case 5:
         if (VERSION.SDK_INT >= 26) {
            var4 = Icon.createWithAdaptiveBitmap((Bitmap)this.mObj1);
         } else {
            var4 = Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
         }
         break;
      case 6:
         StringBuilder var6;
         if (var1 == null) {
            var6 = new StringBuilder();
            var6.append("Context is required to resolve the file uri of the icon: ");
            var6.append(this.getUri());
            throw new IllegalArgumentException(var6.toString());
         }

         InputStream var3 = this.getUriInputStream(var1);
         if (var3 == null) {
            var6 = new StringBuilder();
            var6.append("Cannot load adaptive icon from uri: ");
            var6.append(this.getUri());
            throw new IllegalStateException(var6.toString());
         }

         if (VERSION.SDK_INT >= 26) {
            var4 = Icon.createWithAdaptiveBitmap(BitmapFactory.decodeStream(var3));
         } else {
            var4 = Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(var3), false));
         }
      }

      ColorStateList var2 = this.mTintList;
      if (var2 != null) {
         var4.setTintList(var2);
      }

      Mode var5 = this.mTintMode;
      if (var5 != DEFAULT_TINT_MODE) {
         var4.setTintMode(var5);
      }

      return var4;
   }

   public String toString() {
      if (this.mType == -1) {
         return String.valueOf(this.mObj1);
      } else {
         StringBuilder var1 = new StringBuilder("Icon(typ=");
         var1.append(typeToString(this.mType));
         switch(this.mType) {
         case 1:
         case 5:
            var1.append(" size=");
            var1.append(((Bitmap)this.mObj1).getWidth());
            var1.append("x");
            var1.append(((Bitmap)this.mObj1).getHeight());
            break;
         case 2:
            var1.append(" pkg=");
            var1.append(this.getResPackage());
            var1.append(" id=");
            var1.append(String.format("0x%08x", this.getResId()));
            break;
         case 3:
            var1.append(" len=");
            var1.append(this.mInt1);
            if (this.mInt2 != 0) {
               var1.append(" off=");
               var1.append(this.mInt2);
            }
            break;
         case 4:
         case 6:
            var1.append(" uri=");
            var1.append(this.mObj1);
         }

         if (this.mTintList != null) {
            var1.append(" tint=");
            var1.append(this.mTintList);
         }

         if (this.mTintMode != DEFAULT_TINT_MODE) {
            var1.append(" mode=");
            var1.append(this.mTintMode);
         }

         var1.append(")");
         return var1.toString();
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface IconType {
   }
}
