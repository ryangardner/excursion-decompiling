/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.util.Preconditions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat {
    public static final int ID_NULL = 0;
    private static final String TAG = "ResourcesCompat";

    private ResourcesCompat() {
    }

    public static int getColor(Resources resources, int n, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT < 23) return resources.getColor(n);
        return resources.getColor(n, theme);
    }

    public static ColorStateList getColorStateList(Resources resources, int n, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT < 23) return resources.getColorStateList(n);
        return resources.getColorStateList(n, theme);
    }

    public static Drawable getDrawable(Resources resources, int n, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT < 21) return resources.getDrawable(n);
        return resources.getDrawable(n, theme);
    }

    public static Drawable getDrawableForDensity(Resources resources, int n, int n2, Resources.Theme theme) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawableForDensity(n, n2, theme);
        }
        if (Build.VERSION.SDK_INT < 15) return resources.getDrawable(n);
        return resources.getDrawableForDensity(n, n2);
    }

    public static float getFloat(Resources object, int n) {
        TypedValue typedValue = new TypedValue();
        object.getValue(n, typedValue, true);
        if (typedValue.type == 4) {
            return typedValue.getFloat();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        ((StringBuilder)object).append(" type #0x");
        ((StringBuilder)object).append(Integer.toHexString(typedValue.type));
        ((StringBuilder)object).append(" is not valid");
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    public static Typeface getFont(Context context, int n) throws Resources.NotFoundException {
        if (!context.isRestricted()) return ResourcesCompat.loadFont(context, n, new TypedValue(), 0, null, null, false);
        return null;
    }

    public static Typeface getFont(Context context, int n, TypedValue typedValue, int n2, FontCallback fontCallback) throws Resources.NotFoundException {
        if (!context.isRestricted()) return ResourcesCompat.loadFont(context, n, typedValue, n2, fontCallback, null, true);
        return null;
    }

    public static void getFont(Context context, int n, FontCallback fontCallback, Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
            return;
        }
        ResourcesCompat.loadFont(context, n, new TypedValue(), 0, fontCallback, handler, false);
    }

    private static Typeface loadFont(Context object, int n, TypedValue typedValue, int n2, FontCallback fontCallback, Handler handler, boolean bl) {
        Resources resources = object.getResources();
        resources.getValue(n, typedValue, true);
        object = ResourcesCompat.loadFont((Context)object, resources, typedValue, n, n2, fontCallback, handler, bl);
        if (object != null) return object;
        if (fontCallback != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Font resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        ((StringBuilder)object).append(" could not be retrieved.");
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    private static Typeface loadFont(Context object, Resources resources, TypedValue object2, int n, int n2, FontCallback fontCallback, Handler handler, boolean bl) {
        if (((TypedValue)object2).string == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Resource \"");
            ((StringBuilder)object).append(resources.getResourceName(n));
            ((StringBuilder)object).append("\" (");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(") is not a Font: ");
            ((StringBuilder)object).append(object2);
            throw new Resources.NotFoundException(((StringBuilder)object).toString());
        }
        object2 = ((TypedValue)object2).string.toString();
        if (!((String)object2).startsWith("res/")) {
            if (fontCallback == null) return null;
            fontCallback.callbackFailAsync(-3, handler);
            return null;
        }
        Object object3 = TypefaceCompat.findFromCache(resources, n, n2);
        if (object3 != null) {
            if (fontCallback == null) return object3;
            fontCallback.callbackSuccessAsync((Typeface)object3, handler);
            return object3;
        }
        try {
            if (((String)object2).toLowerCase().endsWith(".xml")) {
                object3 = FontResourcesParserCompat.parse(resources.getXml(n), resources);
                if (object3 != null) return TypefaceCompat.createFromResourcesFamilyXml((Context)object, (FontResourcesParserCompat.FamilyResourceEntry)object3, resources, n, n2, fontCallback, handler, bl);
                Log.e((String)TAG, (String)"Failed to find font-family tag");
                if (fontCallback == null) return null;
                fontCallback.callbackFailAsync(-3, handler);
                return null;
            }
            object = TypefaceCompat.createFromResourcesFontFile((Context)object, resources, n, (String)object2, n2);
            if (fontCallback == null) return object;
            if (object != null) {
                fontCallback.callbackSuccessAsync((Typeface)object, handler);
                return object;
            }
            fontCallback.callbackFailAsync(-3, handler);
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to read xml resource ");
            ((StringBuilder)object).append((String)object2);
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)iOException);
        }
        catch (XmlPullParserException xmlPullParserException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to parse xml resource ");
            ((StringBuilder)object).append((String)object2);
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)xmlPullParserException);
        }
        if (fontCallback == null) return null;
        fontCallback.callbackFailAsync(-3, handler);
        return null;
    }

    public static abstract class FontCallback {
        public final void callbackFailAsync(final int n, Handler handler) {
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler(Looper.getMainLooper());
            }
            handler2.post(new Runnable(){

                @Override
                public void run() {
                    FontCallback.this.onFontRetrievalFailed(n);
                }
            });
        }

        public final void callbackSuccessAsync(final Typeface typeface, Handler handler) {
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler(Looper.getMainLooper());
            }
            handler2.post(new Runnable(){

                @Override
                public void run() {
                    FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }

        public abstract void onFontRetrievalFailed(int var1);

        public abstract void onFontRetrieved(Typeface var1);

    }

    public static final class ThemeCompat {
        private ThemeCompat() {
        }

        public static void rebase(Resources.Theme theme) {
            if (Build.VERSION.SDK_INT >= 29) {
                ImplApi29.rebase(theme);
                return;
            }
            if (Build.VERSION.SDK_INT < 23) return;
            ImplApi23.rebase(theme);
        }

        static class ImplApi23 {
            private static Method sRebaseMethod;
            private static boolean sRebaseMethodFetched;
            private static final Object sRebaseMethodLock;

            static {
                sRebaseMethodLock = new Object();
            }

            private ImplApi23() {
            }

            /*
             * WARNING - void declaration
             */
            static void rebase(Resources.Theme theme) {
                Object object = sRebaseMethodLock;
                synchronized (object) {
                    block9 : {
                        Method method;
                        void var0_3;
                        boolean bl = sRebaseMethodFetched;
                        if (!bl) {
                            try {
                                sRebaseMethod = method = Resources.Theme.class.getDeclaredMethod("rebase", new Class[0]);
                                method.setAccessible(true);
                            }
                            catch (NoSuchMethodException noSuchMethodException) {
                                Log.i((String)ResourcesCompat.TAG, (String)"Failed to retrieve rebase() method", (Throwable)noSuchMethodException);
                            }
                            sRebaseMethodFetched = true;
                        }
                        if ((method = sRebaseMethod) == null) return;
                        try {
                            sRebaseMethod.invoke((Object)theme, new Object[0]);
                            break block9;
                        }
                        catch (InvocationTargetException invocationTargetException) {
                        }
                        catch (IllegalAccessException illegalAccessException) {
                            // empty catch block
                        }
                        Log.i((String)ResourcesCompat.TAG, (String)"Failed to invoke rebase() method via reflection", (Throwable)var0_3);
                        sRebaseMethod = null;
                    }
                    return;
                }
            }
        }

        static class ImplApi29 {
            private ImplApi29() {
            }

            static void rebase(Resources.Theme theme) {
                theme.rebase();
            }
        }

    }

}

