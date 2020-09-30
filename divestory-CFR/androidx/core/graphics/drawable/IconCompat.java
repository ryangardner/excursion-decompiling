/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.Intent$ShortcutIconResource
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.AdaptiveIconDrawable
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Icon
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

public class IconCompat
extends CustomVersionedParcelable {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final String EXTRA_INT1 = "int1";
    private static final String EXTRA_INT2 = "int2";
    private static final String EXTRA_OBJ = "obj";
    private static final String EXTRA_TINT_LIST = "tint_list";
    private static final String EXTRA_TINT_MODE = "tint_mode";
    private static final String EXTRA_TYPE = "type";
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
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
    PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;
    public String mTintModeStr = null;
    public int mType = -1;

    public IconCompat() {
    }

    private IconCompat(int n) {
        this.mType = n;
    }

    public static IconCompat createFromBundle(Bundle object) {
        int n = object.getInt(EXTRA_TYPE);
        IconCompat iconCompat = new IconCompat(n);
        iconCompat.mInt1 = object.getInt(EXTRA_INT1);
        iconCompat.mInt2 = object.getInt(EXTRA_INT2);
        if (object.containsKey(EXTRA_TINT_LIST)) {
            iconCompat.mTintList = (ColorStateList)object.getParcelable(EXTRA_TINT_LIST);
        }
        if (object.containsKey(EXTRA_TINT_MODE)) {
            iconCompat.mTintMode = PorterDuff.Mode.valueOf((String)object.getString(EXTRA_TINT_MODE));
        }
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown type ");
                ((StringBuilder)object).append(n);
                Log.w((String)TAG, (String)((StringBuilder)object).toString());
                return null;
            }
            case 3: {
                iconCompat.mObj1 = object.getByteArray(EXTRA_OBJ);
                return iconCompat;
            }
            case 2: 
            case 4: 
            case 6: {
                iconCompat.mObj1 = object.getString(EXTRA_OBJ);
                return iconCompat;
            }
            case -1: 
            case 1: 
            case 5: 
        }
        iconCompat.mObj1 = object.getParcelable(EXTRA_OBJ);
        return iconCompat;
    }

    public static IconCompat createFromIcon(Context object, Icon icon) {
        Preconditions.checkNotNull(icon);
        int n = IconCompat.getType(icon);
        if (n != 2) {
            if (n == 4) return IconCompat.createWithContentUri(IconCompat.getUri(icon));
            if (n == 6) return IconCompat.createWithAdaptiveBitmapContentUri(IconCompat.getUri(icon));
            object = new IconCompat(-1);
            object.mObj1 = icon;
            return object;
        }
        String string2 = IconCompat.getResPackage(icon);
        try {
            return IconCompat.createWithResource(IconCompat.getResources(object, string2), string2, IconCompat.getResId(icon));
        }
        catch (Resources.NotFoundException notFoundException) {
            throw new IllegalArgumentException("Icon resource cannot be found");
        }
    }

    public static IconCompat createFromIcon(Icon icon) {
        Preconditions.checkNotNull(icon);
        int n = IconCompat.getType(icon);
        if (n == 2) return IconCompat.createWithResource(null, IconCompat.getResPackage(icon), IconCompat.getResId(icon));
        if (n == 4) return IconCompat.createWithContentUri(IconCompat.getUri(icon));
        if (n == 6) return IconCompat.createWithAdaptiveBitmapContentUri(IconCompat.getUri(icon));
        IconCompat iconCompat = new IconCompat(-1);
        iconCompat.mObj1 = icon;
        return iconCompat;
    }

    public static IconCompat createFromIconOrNullIfZeroResId(Icon icon) {
        if (IconCompat.getType(icon) != 2) return IconCompat.createFromIcon(icon);
        if (IconCompat.getResId(icon) != 0) return IconCompat.createFromIcon(icon);
        return null;
    }

    static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap bitmap, boolean bl) {
        int n = (int)((float)Math.min(bitmap.getWidth(), bitmap.getHeight()) * 0.6666667f);
        Bitmap bitmap2 = Bitmap.createBitmap((int)n, (int)n, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint(3);
        float f = n;
        float f2 = 0.5f * f;
        float f3 = 0.9166667f * f2;
        if (bl) {
            float f4 = 0.010416667f * f;
            paint.setColor(0);
            paint.setShadowLayer(f4, 0.0f, f * 0.020833334f, 1023410176);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.setShadowLayer(f4, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(-16777216);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate((float)(-(bitmap.getWidth() - n) / 2), (float)(-(bitmap.getHeight() - n) / 2));
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader((Shader)bitmapShader);
        canvas.drawCircle(f2, f2, f3, paint);
        canvas.setBitmap(null);
        return bitmap2;
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap == null) throw new IllegalArgumentException("Bitmap must not be null.");
        IconCompat iconCompat = new IconCompat(5);
        iconCompat.mObj1 = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithAdaptiveBitmapContentUri(Uri uri) {
        if (uri == null) throw new IllegalArgumentException("Uri must not be null.");
        return IconCompat.createWithAdaptiveBitmapContentUri(uri.toString());
    }

    public static IconCompat createWithAdaptiveBitmapContentUri(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Uri must not be null.");
        IconCompat iconCompat = new IconCompat(6);
        iconCompat.mObj1 = string2;
        return iconCompat;
    }

    public static IconCompat createWithBitmap(Bitmap bitmap) {
        if (bitmap == null) throw new IllegalArgumentException("Bitmap must not be null.");
        IconCompat iconCompat = new IconCompat(1);
        iconCompat.mObj1 = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri == null) throw new IllegalArgumentException("Uri must not be null.");
        return IconCompat.createWithContentUri(uri.toString());
    }

    public static IconCompat createWithContentUri(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Uri must not be null.");
        IconCompat iconCompat = new IconCompat(4);
        iconCompat.mObj1 = string2;
        return iconCompat;
    }

    public static IconCompat createWithData(byte[] arrby, int n, int n2) {
        if (arrby == null) throw new IllegalArgumentException("Data must not be null.");
        IconCompat iconCompat = new IconCompat(3);
        iconCompat.mObj1 = arrby;
        iconCompat.mInt1 = n;
        iconCompat.mInt2 = n2;
        return iconCompat;
    }

    public static IconCompat createWithResource(Context context, int n) {
        if (context == null) throw new IllegalArgumentException("Context must not be null.");
        return IconCompat.createWithResource(context.getResources(), context.getPackageName(), n);
    }

    public static IconCompat createWithResource(Resources resources, String string2, int n) {
        if (string2 == null) throw new IllegalArgumentException("Package must not be null.");
        if (n == 0) throw new IllegalArgumentException("Drawable resource ID must not be 0");
        IconCompat iconCompat = new IconCompat(2);
        iconCompat.mInt1 = n;
        if (resources == null) {
            iconCompat.mObj1 = string2;
            return iconCompat;
        }
        try {
            iconCompat.mObj1 = resources.getResourceName(n);
            return iconCompat;
        }
        catch (Resources.NotFoundException notFoundException) {
            throw new IllegalArgumentException("Icon resource cannot be found");
        }
    }

    private static int getResId(Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResId();
        }
        try {
            return (Integer)icon.getClass().getMethod("getResId", new Class[0]).invoke((Object)icon, new Object[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"Unable to get icon resource", (Throwable)noSuchMethodException);
            return 0;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Unable to get icon resource", (Throwable)invocationTargetException);
            return 0;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Unable to get icon resource", (Throwable)illegalAccessException);
            return 0;
        }
    }

    private static String getResPackage(Icon object) {
        if (Build.VERSION.SDK_INT >= 28) {
            return object.getResPackage();
        }
        try {
            return (String)object.getClass().getMethod("getResPackage", new Class[0]).invoke(object, new Object[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"Unable to get icon package", (Throwable)noSuchMethodException);
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Unable to get icon package", (Throwable)invocationTargetException);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Unable to get icon package", (Throwable)illegalAccessException);
            return null;
        }
    }

    private static Resources getResources(Context context, String string2) {
        if ("android".equals(string2)) {
            return Resources.getSystem();
        }
        context = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo(string2, 8192);
            if (applicationInfo == null) return null;
            return context.getResourcesForApplication(applicationInfo);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)TAG, (String)String.format("Unable to find pkg=%s for icon", string2), (Throwable)nameNotFoundException);
            return null;
        }
    }

    private static int getType(Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getType();
        }
        try {
            return (Integer)icon.getClass().getMethod("getType", new Class[0]).invoke((Object)icon, new Object[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to get icon type ");
            stringBuilder.append((Object)icon);
            Log.e((String)TAG, (String)stringBuilder.toString(), (Throwable)noSuchMethodException);
            return -1;
        }
        catch (InvocationTargetException invocationTargetException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to get icon type ");
            stringBuilder.append((Object)icon);
            Log.e((String)TAG, (String)stringBuilder.toString(), (Throwable)invocationTargetException);
            return -1;
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to get icon type ");
            stringBuilder.append((Object)icon);
            Log.e((String)TAG, (String)stringBuilder.toString(), (Throwable)illegalAccessException);
            return -1;
        }
    }

    private static Uri getUri(Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getUri();
        }
        try {
            return (Uri)icon.getClass().getMethod("getUri", new Class[0]).invoke((Object)icon, new Object[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"Unable to get icon uri", (Throwable)noSuchMethodException);
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Unable to get icon uri", (Throwable)invocationTargetException);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Unable to get icon uri", (Throwable)illegalAccessException);
            return null;
        }
    }

    private InputStream getUriInputStream(Context object) {
        Uri uri = this.getUri();
        CharSequence charSequence = uri.getScheme();
        if (!"content".equals(charSequence) && !"file".equals(charSequence)) {
            try {
                object = new File((String)this.mObj1);
                return new FileInputStream((File)object);
            }
            catch (FileNotFoundException fileNotFoundException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unable to load image from path: ");
                ((StringBuilder)charSequence).append((Object)uri);
                Log.w((String)TAG, (String)((StringBuilder)charSequence).toString(), (Throwable)fileNotFoundException);
                return null;
            }
        }
        try {
            return object.getContentResolver().openInputStream(uri);
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to load image from URI: ");
            ((StringBuilder)object).append((Object)uri);
            Log.w((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)exception);
        }
        return null;
    }

    private Drawable loadDrawableInner(Context context) {
        switch (this.mType) {
            default: {
                return null;
            }
            case 6: {
                InputStream inputStream2 = this.getUriInputStream(context);
                if (inputStream2 == null) return null;
                if (Build.VERSION.SDK_INT < 26) return new BitmapDrawable(context.getResources(), IconCompat.createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream((InputStream)inputStream2), false));
                return new AdaptiveIconDrawable(null, (Drawable)new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream((InputStream)inputStream2)));
            }
            case 5: {
                return new BitmapDrawable(context.getResources(), IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
            }
            case 4: {
                InputStream inputStream3 = this.getUriInputStream(context);
                if (inputStream3 == null) return null;
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream((InputStream)inputStream3));
            }
            case 3: {
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((byte[])((byte[])this.mObj1), (int)this.mInt1, (int)this.mInt2));
            }
            case 2: {
                String string2;
                String string3 = string2 = this.getResPackage();
                if (TextUtils.isEmpty((CharSequence)string2)) {
                    string3 = context.getPackageName();
                }
                string3 = IconCompat.getResources(context, string3);
                try {
                    return ResourcesCompat.getDrawable((Resources)string3, this.mInt1, context.getTheme());
                }
                catch (RuntimeException runtimeException) {
                    Log.e((String)TAG, (String)String.format("Unable to load resource 0x%08x from pkg=%s", this.mInt1, this.mObj1), (Throwable)runtimeException);
                    return null;
                }
            }
            case 1: 
        }
        return new BitmapDrawable(context.getResources(), (Bitmap)this.mObj1);
    }

    private static String typeToString(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 6: {
                return "URI_MASKABLE";
            }
            case 5: {
                return "BITMAP_MASKABLE";
            }
            case 4: {
                return "URI";
            }
            case 3: {
                return "DATA";
            }
            case 2: {
                return "RESOURCE";
            }
            case 1: 
        }
        return "BITMAP";
    }

    public void addToShortcutIntent(Intent intent, Drawable object, Context context) {
        this.checkResource(context);
        int n = this.mType;
        if (n != 1) {
            if (n != 2) {
                if (n != 5) throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
                context = IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
            } else {
                try {
                    context = context.createPackageContext(this.getResPackage(), 0);
                    if (object == null) {
                        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", (Parcelable)Intent.ShortcutIconResource.fromContext((Context)context, (int)this.mInt1));
                        return;
                    }
                    Drawable drawable2 = ContextCompat.getDrawable(context, this.mInt1);
                    if (drawable2.getIntrinsicWidth() > 0 && drawable2.getIntrinsicHeight() > 0) {
                        context = Bitmap.createBitmap((int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    } else {
                        n = ((ActivityManager)context.getSystemService("activity")).getLauncherLargeIconSize();
                        context = Bitmap.createBitmap((int)n, (int)n, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    }
                    drawable2.setBounds(0, 0, context.getWidth(), context.getHeight());
                    Canvas canvas = new Canvas((Bitmap)context);
                    drawable2.draw(canvas);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Can't find package ");
                    ((StringBuilder)object).append(this.mObj1);
                    throw new IllegalArgumentException(((StringBuilder)object).toString(), nameNotFoundException);
                }
            }
        } else {
            Bitmap bitmap = (Bitmap)this.mObj1;
            context = bitmap;
            if (object != null) {
                context = bitmap.copy(bitmap.getConfig(), true);
            }
        }
        if (object != null) {
            n = context.getWidth();
            int n2 = context.getHeight();
            object.setBounds(n / 2, n2 / 2, n, n2);
            object.draw(new Canvas((Bitmap)context));
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", (Parcelable)context);
    }

    public void checkResource(Context object) {
        if (this.mType != 2) return;
        String string2 = (String)this.mObj1;
        if (!string2.contains(":")) {
            return;
        }
        String string3 = string2.split(":", -1)[1];
        String string4 = string3.split("/", -1)[0];
        string3 = string3.split("/", -1)[1];
        int n = IconCompat.getResources((Context)object, string2 = string2.split(":", -1)[0]).getIdentifier(string3, string4, string2);
        if (this.mInt1 == n) return;
        object = new StringBuilder();
        ((StringBuilder)object).append("Id has changed for ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("/");
        ((StringBuilder)object).append(string3);
        Log.i((String)TAG, (String)((StringBuilder)object).toString());
        this.mInt1 = n;
    }

    public Bitmap getBitmap() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            Object object = this.mObj1;
            if (!(object instanceof Bitmap)) return null;
            return (Bitmap)object;
        }
        int n = this.mType;
        if (n == 1) {
            return (Bitmap)this.mObj1;
        }
        if (n == 5) {
            return IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getBitmap() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getResId() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return IconCompat.getResId((Icon)this.mObj1);
        }
        if (this.mType == 2) {
            return this.mInt1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResId() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String getResPackage() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return IconCompat.getResPackage((Icon)this.mObj1);
        }
        if (this.mType == 2) {
            return ((String)this.mObj1).split(":", -1)[0];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResPackage() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getType() {
        if (this.mType != -1) return this.mType;
        if (Build.VERSION.SDK_INT < 23) return this.mType;
        return IconCompat.getType((Icon)this.mObj1);
    }

    public Uri getUri() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return IconCompat.getUri((Icon)this.mObj1);
        }
        int n = this.mType;
        if (n == 4) return Uri.parse((String)((String)this.mObj1));
        if (n == 6) {
            return Uri.parse((String)((String)this.mObj1));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getUri() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Drawable loadDrawable(Context context) {
        this.checkResource(context);
        if (Build.VERSION.SDK_INT >= 23) {
            return this.toIcon(context).loadDrawable(context);
        }
        if ((context = this.loadDrawableInner(context)) == null) return context;
        if (this.mTintList == null) {
            if (this.mTintMode == DEFAULT_TINT_MODE) return context;
        }
        context.mutate();
        DrawableCompat.setTintList((Drawable)context, this.mTintList);
        DrawableCompat.setTintMode((Drawable)context, this.mTintMode);
        return context;
    }

    @Override
    public void onPostParceling() {
        this.mTintMode = PorterDuff.Mode.valueOf((String)this.mTintModeStr);
        switch (this.mType) {
            default: {
                return;
            }
            case 3: {
                this.mObj1 = this.mData;
                return;
            }
            case 2: 
            case 4: 
            case 6: {
                this.mObj1 = new String(this.mData, Charset.forName("UTF-16"));
                return;
            }
            case 1: 
            case 5: {
                byte[] arrby = this.mParcelable;
                if (arrby != null) {
                    this.mObj1 = arrby;
                    return;
                }
                this.mObj1 = arrby = this.mData;
                this.mType = 3;
                this.mInt1 = 0;
                this.mInt2 = arrby.length;
                return;
            }
            case -1: 
        }
        Parcelable parcelable = this.mParcelable;
        if (parcelable == null) throw new IllegalArgumentException("Invalid icon");
        this.mObj1 = parcelable;
    }

    @Override
    public void onPreParceling(boolean bl) {
        this.mTintModeStr = this.mTintMode.name();
        switch (this.mType) {
            default: {
                return;
            }
            case 4: 
            case 6: {
                this.mData = this.mObj1.toString().getBytes(Charset.forName("UTF-16"));
                return;
            }
            case 3: {
                this.mData = (byte[])this.mObj1;
                return;
            }
            case 2: {
                this.mData = ((String)this.mObj1).getBytes(Charset.forName("UTF-16"));
                return;
            }
            case 1: 
            case 5: {
                if (bl) {
                    Bitmap bitmap = (Bitmap)this.mObj1;
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, (OutputStream)byteArrayOutputStream);
                    this.mData = byteArrayOutputStream.toByteArray();
                    return;
                }
                this.mParcelable = (Parcelable)this.mObj1;
                return;
            }
            case -1: 
        }
        if (bl) throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
        this.mParcelable = (Parcelable)this.mObj1;
    }

    public IconCompat setTint(int n) {
        return this.setTintList(ColorStateList.valueOf((int)n));
    }

    public IconCompat setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    public IconCompat setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        return this;
    }

    /*
     * Unable to fully structure code
     */
    public Bundle toBundle() {
        var1_1 = new Bundle();
        switch (this.mType) {
            default: {
                throw new IllegalArgumentException("Invalid icon");
            }
            case 3: {
                var1_1.putByteArray("obj", (byte[])this.mObj1);
                ** break;
            }
            case 2: 
            case 4: 
            case 6: {
                var1_1.putString("obj", (String)this.mObj1);
                ** break;
            }
            case 1: 
            case 5: {
                var1_1.putParcelable("obj", (Parcelable)((Bitmap)this.mObj1));
                ** break;
            }
            case -1: 
        }
        var1_1.putParcelable("obj", (Parcelable)this.mObj1);
lbl16: // 4 sources:
        var1_1.putInt("type", this.mType);
        var1_1.putInt("int1", this.mInt1);
        var1_1.putInt("int2", this.mInt2);
        var2_2 = this.mTintList;
        if (var2_2 != null) {
            var1_1.putParcelable("tint_list", (Parcelable)var2_2);
        }
        if ((var2_2 = this.mTintMode) == IconCompat.DEFAULT_TINT_MODE) return var1_1;
        var1_1.putString("tint_mode", var2_2.name());
        return var1_1;
    }

    @Deprecated
    public Icon toIcon() {
        return this.toIcon(null);
    }

    /*
     * Unable to fully structure code
     */
    public Icon toIcon(Context var1_1) {
        switch (this.mType) {
            default: {
                throw new IllegalArgumentException("Unknown type");
            }
            case 6: {
                if (var1_1 == null) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Context is required to resolve the file uri of the icon: ");
                    var1_1.append((Object)this.getUri());
                    throw new IllegalArgumentException(var1_1.toString());
                }
                if ((var1_1 = this.getUriInputStream((Context)var1_1)) == null) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Cannot load adaptive icon from uri: ");
                    var1_1.append((Object)this.getUri());
                    throw new IllegalStateException(var1_1.toString());
                }
                var1_1 = Build.VERSION.SDK_INT >= 26 ? Icon.createWithAdaptiveBitmap((Bitmap)BitmapFactory.decodeStream((InputStream)var1_1)) : Icon.createWithBitmap((Bitmap)IconCompat.createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream((InputStream)var1_1), false));
                ** GOTO lbl35
            }
            case 5: {
                var1_1 = Build.VERSION.SDK_INT >= 26 ? Icon.createWithAdaptiveBitmap((Bitmap)((Bitmap)this.mObj1)) : Icon.createWithBitmap((Bitmap)IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
                ** GOTO lbl35
            }
            case 4: {
                var1_1 = Icon.createWithContentUri((String)((String)this.mObj1));
                ** GOTO lbl35
            }
            case 3: {
                var1_1 = Icon.createWithData((byte[])((byte[])this.mObj1), (int)this.mInt1, (int)this.mInt2);
                ** GOTO lbl35
            }
            case 2: {
                var1_1 = Icon.createWithResource((String)this.getResPackage(), (int)this.mInt1);
                ** GOTO lbl35
            }
            case 1: {
                var1_1 = Icon.createWithBitmap((Bitmap)((Bitmap)this.mObj1));
lbl35: // 6 sources:
                var2_2 = this.mTintList;
                if (var2_2 != null) {
                    var1_1.setTintList(var2_2);
                }
                if ((var2_2 = this.mTintMode) == IconCompat.DEFAULT_TINT_MODE) return var1_1;
                var1_1.setTintMode((PorterDuff.Mode)var2_2);
                return var1_1;
            }
            case -1: 
        }
        return (Icon)this.mObj1;
    }

    public String toString() {
        if (this.mType == -1) {
            return String.valueOf(this.mObj1);
        }
        StringBuilder stringBuilder = new StringBuilder("Icon(typ=");
        stringBuilder.append(IconCompat.typeToString(this.mType));
        switch (this.mType) {
            default: {
                break;
            }
            case 4: 
            case 6: {
                stringBuilder.append(" uri=");
                stringBuilder.append(this.mObj1);
                break;
            }
            case 3: {
                stringBuilder.append(" len=");
                stringBuilder.append(this.mInt1);
                if (this.mInt2 == 0) break;
                stringBuilder.append(" off=");
                stringBuilder.append(this.mInt2);
                break;
            }
            case 2: {
                stringBuilder.append(" pkg=");
                stringBuilder.append(this.getResPackage());
                stringBuilder.append(" id=");
                stringBuilder.append(String.format("0x%08x", this.getResId()));
                break;
            }
            case 1: 
            case 5: {
                stringBuilder.append(" size=");
                stringBuilder.append(((Bitmap)this.mObj1).getWidth());
                stringBuilder.append("x");
                stringBuilder.append(((Bitmap)this.mObj1).getHeight());
            }
        }
        if (this.mTintList != null) {
            stringBuilder.append(" tint=");
            stringBuilder.append((Object)this.mTintList);
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            stringBuilder.append(" mode=");
            stringBuilder.append((Object)this.mTintMode);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IconType {
    }

}

