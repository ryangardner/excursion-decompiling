/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.LongSparseArray
 */
package androidx.appcompat.app;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher {
    private static final String TAG = "ResourcesFlusher";
    private static Field sDrawableCacheField;
    private static boolean sDrawableCacheFieldFetched;
    private static Field sResourcesImplField;
    private static boolean sResourcesImplFieldFetched;
    private static Class<?> sThemedResourceCacheClazz;
    private static boolean sThemedResourceCacheClazzFetched;
    private static Field sThemedResourceCache_mUnthemedEntriesField;
    private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;

    private ResourcesFlusher() {
    }

    static void flush(Resources resources) {
        if (Build.VERSION.SDK_INT >= 28) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            ResourcesFlusher.flushNougats(resources);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            ResourcesFlusher.flushMarshmallows(resources);
            return;
        }
        if (Build.VERSION.SDK_INT < 21) return;
        ResourcesFlusher.flushLollipops(resources);
    }

    private static void flushLollipops(Resources object) {
        Field field;
        Field field2;
        if (!sDrawableCacheFieldFetched) {
            try {
                sDrawableCacheField = field = Resources.class.getDeclaredField("mDrawableCache");
                field.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve Resources#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        if ((field2 = sDrawableCacheField) == null) return;
        field = null;
        try {
            object = (Map)field2.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Could not retrieve value from Resources#mDrawableCache", (Throwable)illegalAccessException);
            object = field;
        }
        if (object == null) return;
        object.clear();
    }

    private static void flushMarshmallows(Resources resources) {
        Object object;
        if (!sDrawableCacheFieldFetched) {
            try {
                object = Resources.class.getDeclaredField("mDrawableCache");
                sDrawableCacheField = object;
                ((Field)object).setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve Resources#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        Object var2_4 = null;
        Field field = sDrawableCacheField;
        object = var2_4;
        if (field != null) {
            try {
                object = field.get((Object)resources);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Could not retrieve value from Resources#mDrawableCache", (Throwable)illegalAccessException);
                object = var2_4;
            }
        }
        if (object == null) {
            return;
        }
        ResourcesFlusher.flushThemedResourcesCache(object);
    }

    private static void flushNougats(Resources object) {
        Object object2;
        if (!sResourcesImplFieldFetched) {
            try {
                object2 = Resources.class.getDeclaredField("mResourcesImpl");
                sResourcesImplField = object2;
                ((Field)object2).setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve Resources#mResourcesImpl field", (Throwable)noSuchFieldException);
            }
            sResourcesImplFieldFetched = true;
        }
        if ((object2 = sResourcesImplField) == null) {
            return;
        }
        Object var2_6 = null;
        try {
            object = ((Field)object2).get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Could not retrieve value from Resources#mResourcesImpl", (Throwable)illegalAccessException);
            return;
        }
        if (object == null) {
            return;
        }
        if (!sDrawableCacheFieldFetched) {
            try {
                object2 = object.getClass().getDeclaredField("mDrawableCache");
                sDrawableCacheField = object2;
                ((Field)object2).setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve ResourcesImpl#mDrawableCache field", (Throwable)noSuchFieldException);
            }
            sDrawableCacheFieldFetched = true;
        }
        Field field = sDrawableCacheField;
        object2 = var2_6;
        if (field != null) {
            try {
                object2 = field.get(object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Could not retrieve value from ResourcesImpl#mDrawableCache", (Throwable)illegalAccessException);
                object2 = var2_6;
            }
        }
        if (object2 == null) return;
        ResourcesFlusher.flushThemedResourcesCache(object2);
    }

    private static void flushThemedResourcesCache(Object object) {
        Field field;
        AnnotatedElement annotatedElement;
        if (!sThemedResourceCacheClazzFetched) {
            try {
                sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
            }
            catch (ClassNotFoundException classNotFoundException) {
                Log.e((String)TAG, (String)"Could not find ThemedResourceCache class", (Throwable)classNotFoundException);
            }
            sThemedResourceCacheClazzFetched = true;
        }
        if ((annotatedElement = sThemedResourceCacheClazz) == null) {
            return;
        }
        if (!sThemedResourceCache_mUnthemedEntriesFieldFetched) {
            try {
                annotatedElement = ((Class)annotatedElement).getDeclaredField("mUnthemedEntries");
                sThemedResourceCache_mUnthemedEntriesField = annotatedElement;
                ((Field)annotatedElement).setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)TAG, (String)"Could not retrieve ThemedResourceCache#mUnthemedEntries field", (Throwable)noSuchFieldException);
            }
            sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
        }
        if ((field = sThemedResourceCache_mUnthemedEntriesField) == null) {
            return;
        }
        annotatedElement = null;
        try {
            object = (LongSparseArray)field.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Could not retrieve value from ThemedResourceCache#mUnthemedEntries", (Throwable)illegalAccessException);
            object = annotatedElement;
        }
        if (object == null) return;
        object.clear();
    }
}

