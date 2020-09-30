/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.graphics.TypefaceCompatBaseImpl;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.provider.FontsContractCompat;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class TypefaceCompatApi24Impl
extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi24Impl";
    private static final Method sAddFontWeightStyle;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class<?> sFontFamily;
    private static final Constructor<?> sFontFamilyCtor;

    static {
        GenericDeclaration genericDeclaration;
        Class<?> class_;
        GenericDeclaration genericDeclaration2;
        GenericDeclaration genericDeclaration3;
        block3 : {
            Object var0 = null;
            try {
                class_ = Class.forName(FONT_FAMILY_CLASS);
                genericDeclaration2 = class_.getConstructor(new Class[0]);
                genericDeclaration3 = class_.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE);
                genericDeclaration = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(class_, 1).getClass());
                break block3;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            Log.e((String)TAG, (String)genericDeclaration2.getClass().getName(), (Throwable)((Object)genericDeclaration2));
            class_ = null;
            genericDeclaration3 = genericDeclaration2 = class_;
            genericDeclaration = genericDeclaration2;
            genericDeclaration2 = var0;
        }
        sFontFamilyCtor = genericDeclaration2;
        sFontFamily = class_;
        sAddFontWeightStyle = genericDeclaration3;
        sCreateFromFamiliesWithDefault = genericDeclaration;
    }

    TypefaceCompatApi24Impl() {
    }

    private static boolean addFontWeightStyle(Object object, ByteBuffer byteBuffer, int n, int n2, boolean bl) {
        try {
            return (Boolean)sAddFontWeightStyle.invoke(object, byteBuffer, n, null, n2, bl);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object object) {
        try {
            Object object2 = Array.newInstance(sFontFamily, 1);
            Array.set(object2, 0, object);
            return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, object2);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return null;
        }
    }

    public static boolean isUsable() {
        if (sAddFontWeightStyle == null) {
            Log.w((String)TAG, (String)"Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        if (sAddFontWeightStyle == null) return false;
        return true;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            return null;
        }
    }

    @Override
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry object, Resources resources, int n) {
        Object object2 = TypefaceCompatApi24Impl.newFamily();
        if (object2 == null) {
            return null;
        }
        FontResourcesParserCompat.FontFileResourceEntry[] arrfontFileResourceEntry = ((FontResourcesParserCompat.FontFamilyFilesResourceEntry)object).getEntries();
        int n2 = arrfontFileResourceEntry.length;
        n = 0;
        while (n < n2) {
            FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrfontFileResourceEntry[n];
            object = TypefaceCompatUtil.copyToDirectBuffer(context, resources, fontFileResourceEntry.getResourceId());
            if (object == null) {
                return null;
            }
            if (!TypefaceCompatApi24Impl.addFontWeightStyle(object2, (ByteBuffer)object, fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic())) {
                return null;
            }
            ++n;
        }
        return TypefaceCompatApi24Impl.createFromFamiliesWithDefault(object2);
    }

    @Override
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] arrfontInfo, int n) {
        Object object = TypefaceCompatApi24Impl.newFamily();
        if (object == null) {
            return null;
        }
        SimpleArrayMap<Uri, ByteBuffer> simpleArrayMap = new SimpleArrayMap<Uri, ByteBuffer>();
        int n2 = arrfontInfo.length;
        int n3 = 0;
        do {
            ByteBuffer byteBuffer;
            if (n3 >= n2) {
                context = TypefaceCompatApi24Impl.createFromFamiliesWithDefault(object);
                if (context != null) return Typeface.create((Typeface)context, (int)n);
                return null;
            }
            FontsContractCompat.FontInfo fontInfo = arrfontInfo[n3];
            Uri uri = fontInfo.getUri();
            ByteBuffer byteBuffer2 = byteBuffer = (ByteBuffer)simpleArrayMap.get((Object)uri);
            if (byteBuffer == null) {
                byteBuffer2 = TypefaceCompatUtil.mmap(context, cancellationSignal, uri);
                simpleArrayMap.put(uri, byteBuffer2);
            }
            if (byteBuffer2 == null) {
                return null;
            }
            if (!TypefaceCompatApi24Impl.addFontWeightStyle(object, byteBuffer2, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic())) {
                return null;
            }
            ++n3;
        } while (true);
    }
}

