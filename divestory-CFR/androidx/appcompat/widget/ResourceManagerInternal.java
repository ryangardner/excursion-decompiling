/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;
import androidx.appcompat.resources.R;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintInfo;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourceManagerInternal {
    private static final ColorFilterLruCache COLOR_FILTER_CACHE;
    private static final boolean DEBUG = false;
    private static final PorterDuff.Mode DEFAULT_MODE;
    private static ResourceManagerInternal INSTANCE;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "ResourceManagerInternal";
    private SimpleArrayMap<String, InflateDelegate> mDelegates;
    private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private ResourceManagerHooks mHooks;
    private SparseArrayCompat<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    static {
        DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
        COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    }

    private void addDelegate(String string2, InflateDelegate inflateDelegate) {
        if (this.mDelegates == null) {
            this.mDelegates = new SimpleArrayMap();
        }
        this.mDelegates.put(string2, inflateDelegate);
    }

    private boolean addDrawableToCache(Context object, long l, Drawable object2) {
        synchronized (this) {
            Drawable.ConstantState constantState = object2.getConstantState();
            if (constantState == null) return false;
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get(object);
            object2 = longSparseArray;
            if (longSparseArray == null) {
                object2 = new LongSparseArray();
                this.mDrawableCaches.put((Context)object, (LongSparseArray<WeakReference<Drawable.ConstantState>>)object2);
            }
            object = new WeakReference(constantState);
            ((LongSparseArray)object2).put(l, object);
            return true;
        }
    }

    private void addTintListToCache(Context context, int n, ColorStateList colorStateList) {
        SparseArrayCompat<ColorStateList> sparseArrayCompat;
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap();
        }
        SparseArrayCompat<Object> sparseArrayCompat2 = sparseArrayCompat = this.mTintLists.get((Object)context);
        if (sparseArrayCompat == null) {
            sparseArrayCompat2 = new SparseArrayCompat();
            this.mTintLists.put(context, sparseArrayCompat2);
        }
        sparseArrayCompat2.append(n, (Object)colorStateList);
    }

    private void checkVectorDrawableSetup(Context context) {
        if (this.mHasCheckedVectorDrawableSetup) {
            return;
        }
        this.mHasCheckedVectorDrawableSetup = true;
        if ((context = this.getDrawable(context, R.drawable.abc_vector_test)) != null && ResourceManagerInternal.isVectorDrawable((Drawable)context)) {
            return;
        }
        this.mHasCheckedVectorDrawableSetup = false;
        throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
    }

    private static long createCacheKey(TypedValue typedValue) {
        return (long)typedValue.assetCookie << 32 | (long)typedValue.data;
    }

    private Drawable createDrawableIfNeeded(Context context, int n) {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        context.getResources().getValue(n, typedValue, true);
        long l = ResourceManagerInternal.createCacheKey(typedValue);
        Object object = this.getCachedDrawable(context, l);
        if (object != null) {
            return object;
        }
        object = this.mHooks;
        object = object == null ? null : object.createDrawableFor(this, context, n);
        if (object == null) return object;
        object.setChangingConfigurations(typedValue.changingConfigurations);
        this.addDrawableToCache(context, l, (Drawable)object);
        return object;
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, int[] arrn) {
        if (colorStateList == null) return null;
        if (mode != null) return ResourceManagerInternal.getPorterDuffColorFilter(colorStateList.getColorForState(arrn, 0), mode);
        return null;
    }

    public static ResourceManagerInternal get() {
        synchronized (ResourceManagerInternal.class) {
            ResourceManagerInternal resourceManagerInternal;
            if (INSTANCE == null) {
                INSTANCE = resourceManagerInternal = new ResourceManagerInternal();
                ResourceManagerInternal.installDefaultInflateDelegates(resourceManagerInternal);
            }
            resourceManagerInternal = INSTANCE;
            return resourceManagerInternal;
        }
    }

    private Drawable getCachedDrawable(Context context, long l) {
        synchronized (this) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get((Object)context);
            if (longSparseArray == null) {
                return null;
            }
            Drawable.ConstantState constantState = longSparseArray.get(l);
            if (constantState == null) return null;
            if ((constantState = (Drawable.ConstantState)constantState.get()) != null) {
                context = constantState.newDrawable(context.getResources());
                return context;
            }
            longSparseArray.remove(l);
            return null;
        }
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int n, PorterDuff.Mode mode) {
        synchronized (ResourceManagerInternal.class) {
            PorterDuffColorFilter porterDuffColorFilter;
            PorterDuffColorFilter porterDuffColorFilter2 = porterDuffColorFilter = COLOR_FILTER_CACHE.get(n, mode);
            if (porterDuffColorFilter != null) return porterDuffColorFilter2;
            porterDuffColorFilter2 = new PorterDuffColorFilter(n, mode);
            COLOR_FILTER_CACHE.put(n, mode, porterDuffColorFilter2);
            return porterDuffColorFilter2;
        }
    }

    private ColorStateList getTintListFromCache(Context object, int n) {
        ColorStateList colorStateList;
        WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.mTintLists;
        ColorStateList colorStateList2 = colorStateList = null;
        if (weakHashMap == null) return colorStateList2;
        object = weakHashMap.get(object);
        colorStateList2 = colorStateList;
        if (object == null) return colorStateList2;
        return (ColorStateList)((SparseArrayCompat)object).get(n);
    }

    private static void installDefaultInflateDelegates(ResourceManagerInternal resourceManagerInternal) {
        if (Build.VERSION.SDK_INT >= 24) return;
        resourceManagerInternal.addDelegate("vector", new VdcInflateDelegate());
        resourceManagerInternal.addDelegate("animated-vector", new AvdcInflateDelegate());
        resourceManagerInternal.addDelegate("animated-selector", new AsldcInflateDelegate());
    }

    private static boolean isVectorDrawable(Drawable drawable2) {
        if (drawable2 instanceof VectorDrawableCompat) return true;
        if (PLATFORM_VD_CLAZZ.equals(drawable2.getClass().getName())) return true;
        return false;
    }

    private Drawable loadDrawableFromDelegates(Context object, int n) {
        Object object2 = this.mDelegates;
        if (object2 == null) return null;
        if (((SimpleArrayMap)object2).isEmpty()) return null;
        object2 = this.mKnownDrawableIdTags;
        if (object2 != null) {
            if (SKIP_DRAWABLE_TAG.equals(object2 = (String)((SparseArrayCompat)object2).get(n))) return null;
            if (object2 != null && this.mDelegates.get(object2) == null) {
                return null;
            }
        } else {
            this.mKnownDrawableIdTags = new SparseArrayCompat();
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        object2 = object.getResources();
        object2.getValue(n, typedValue, true);
        long l = ResourceManagerInternal.createCacheKey(typedValue);
        Drawable drawable2 = this.getCachedDrawable((Context)object, l);
        if (drawable2 != null) {
            return drawable2;
        }
        Object object3 = drawable2;
        if (typedValue.string != null) {
            object3 = drawable2;
            if (typedValue.string.toString().endsWith(".xml")) {
                object3 = drawable2;
                try {
                    int n2;
                    XmlResourceParser xmlResourceParser = object2.getXml(n);
                    object3 = drawable2;
                    AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
                    do {
                        object3 = drawable2;
                    } while ((n2 = xmlResourceParser.next()) != 2 && n2 != 1);
                    if (n2 != 2) {
                        object3 = drawable2;
                        object3 = drawable2;
                        object = new XmlPullParserException("No start tag found");
                        object3 = drawable2;
                        throw object;
                    }
                    object3 = drawable2;
                    object2 = xmlResourceParser.getName();
                    object3 = drawable2;
                    this.mKnownDrawableIdTags.append(n, (String)object2);
                    object3 = drawable2;
                    InflateDelegate inflateDelegate = this.mDelegates.get(object2);
                    object2 = drawable2;
                    if (inflateDelegate != null) {
                        object3 = drawable2;
                        object2 = inflateDelegate.createFromXmlInner((Context)object, xmlResourceParser, attributeSet, object.getTheme());
                    }
                    object3 = object2;
                    if (object2 != null) {
                        object3 = object2;
                        object2.setChangingConfigurations(typedValue.changingConfigurations);
                        object3 = object2;
                        this.addDrawableToCache((Context)object, l, (Drawable)object2);
                        object3 = object2;
                    }
                }
                catch (Exception exception) {
                    Log.e((String)TAG, (String)"Exception while inflating drawable", (Throwable)exception);
                }
            }
        }
        if (object3 != null) return object3;
        this.mKnownDrawableIdTags.append(n, SKIP_DRAWABLE_TAG);
        return object3;
    }

    private Drawable tintDrawable(Context context, int n, boolean bl, Drawable drawable2) {
        Object object = this.getTintList(context, n);
        if (object != null) {
            context = drawable2;
            if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
                context = drawable2.mutate();
            }
            context = DrawableCompat.wrap((Drawable)context);
            DrawableCompat.setTintList((Drawable)context, (ColorStateList)object);
            drawable2 = this.getTintMode(n);
            object = context;
            if (drawable2 == null) return object;
            DrawableCompat.setTintMode((Drawable)context, (PorterDuff.Mode)drawable2);
            return context;
        }
        object = this.mHooks;
        if (object != null && object.tintDrawable(context, n, drawable2)) {
            return drawable2;
        }
        object = drawable2;
        if (this.tintDrawableUsingColorFilter(context, n, drawable2)) return object;
        object = drawable2;
        if (!bl) return object;
        return null;
    }

    static void tintDrawable(Drawable drawable2, TintInfo tintInfo, int[] arrn) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable2) && drawable2.mutate() != drawable2) {
            Log.d((String)TAG, (String)"Mutated drawable is not the same instance as the input.");
            return;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            drawable2.clearColorFilter();
        } else {
            ColorStateList colorStateList = tintInfo.mHasTintList ? tintInfo.mTintList : null;
            tintInfo = tintInfo.mHasTintMode ? tintInfo.mTintMode : DEFAULT_MODE;
            drawable2.setColorFilter((ColorFilter)ResourceManagerInternal.createTintFilter(colorStateList, (PorterDuff.Mode)tintInfo, arrn));
        }
        if (Build.VERSION.SDK_INT > 23) return;
        drawable2.invalidateSelf();
    }

    public Drawable getDrawable(Context context, int n) {
        synchronized (this) {
            return this.getDrawable(context, n, false);
        }
    }

    Drawable getDrawable(Context context, int n, boolean bl) {
        synchronized (this) {
            Drawable drawable2;
            this.checkVectorDrawableSetup(context);
            Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n);
            if (drawable2 == null) {
                drawable3 = this.createDrawableIfNeeded(context, n);
            }
            drawable2 = drawable3;
            if (drawable3 == null) {
                drawable2 = ContextCompat.getDrawable(context, n);
            }
            drawable3 = drawable2;
            if (drawable2 != null) {
                drawable3 = this.tintDrawable(context, n, bl, drawable2);
            }
            if (drawable3 == null) return drawable3;
            DrawableUtils.fixDrawable(drawable3);
            return drawable3;
        }
    }

    ColorStateList getTintList(Context context, int n) {
        synchronized (this) {
            Object object;
            ColorStateList colorStateList = object = this.getTintListFromCache(context, n);
            if (object != null) return colorStateList;
            object = this.mHooks == null ? null : this.mHooks.getTintListForDrawableRes(context, n);
            colorStateList = object;
            if (object == null) return colorStateList;
            this.addTintListToCache(context, n, (ColorStateList)object);
            colorStateList = object;
            return colorStateList;
        }
    }

    PorterDuff.Mode getTintMode(int n) {
        ResourceManagerHooks resourceManagerHooks = this.mHooks;
        if (resourceManagerHooks != null) return resourceManagerHooks.getTintModeForDrawableRes(n);
        return null;
    }

    public void onConfigurationChanged(Context object) {
        synchronized (this) {
            object = this.mDrawableCaches.get(object);
            if (object == null) return;
            ((LongSparseArray)object).clear();
            return;
        }
    }

    Drawable onDrawableLoadedFromResources(Context context, VectorEnabledTintResources vectorEnabledTintResources, int n) {
        synchronized (this) {
            Drawable drawable2;
            Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n);
            if (drawable2 == null) {
                drawable3 = vectorEnabledTintResources.superGetDrawable(n);
            }
            if (drawable3 == null) return null;
            context = this.tintDrawable(context, n, false, drawable3);
            return context;
        }
    }

    public void setHooks(ResourceManagerHooks resourceManagerHooks) {
        synchronized (this) {
            this.mHooks = resourceManagerHooks;
            return;
        }
    }

    boolean tintDrawableUsingColorFilter(Context context, int n, Drawable drawable2) {
        ResourceManagerHooks resourceManagerHooks = this.mHooks;
        if (resourceManagerHooks == null) return false;
        if (!resourceManagerHooks.tintDrawableUsingColorFilter(context, n, drawable2)) return false;
        return true;
    }

    static class AsldcInflateDelegate
    implements InflateDelegate {
        AsldcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(Context object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return AnimatedStateListDrawableCompat.createFromXmlInner(object, object.getResources(), xmlPullParser, attributeSet, theme);
            }
            catch (Exception exception) {
                Log.e((String)"AsldcInflateDelegate", (String)"Exception while inflating <animated-selector>", (Throwable)exception);
                return null;
            }
        }
    }

    private static class AvdcInflateDelegate
    implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(Context object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(object, object.getResources(), xmlPullParser, attributeSet, theme);
            }
            catch (Exception exception) {
                Log.e((String)"AvdcInflateDelegate", (String)"Exception while inflating <animated-vector>", (Throwable)exception);
                return null;
            }
        }
    }

    private static class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int n) {
            super(n);
        }

        private static int generateCacheKey(int n, PorterDuff.Mode mode) {
            return (n + 31) * 31 + mode.hashCode();
        }

        PorterDuffColorFilter get(int n, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter)this.get(ColorFilterLruCache.generateCacheKey(n, mode));
        }

        PorterDuffColorFilter put(int n, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return this.put(ColorFilterLruCache.generateCacheKey(n, mode), porterDuffColorFilter);
        }
    }

    private static interface InflateDelegate {
        public Drawable createFromXmlInner(Context var1, XmlPullParser var2, AttributeSet var3, Resources.Theme var4);
    }

    static interface ResourceManagerHooks {
        public Drawable createDrawableFor(ResourceManagerInternal var1, Context var2, int var3);

        public ColorStateList getTintListForDrawableRes(Context var1, int var2);

        public PorterDuff.Mode getTintModeForDrawableRes(int var1);

        public boolean tintDrawable(Context var1, int var2, Drawable var3);

        public boolean tintDrawableUsingColorFilter(Context var1, int var2, Drawable var3);
    }

    private static class VdcInflateDelegate
    implements InflateDelegate {
        VdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(Context object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(object.getResources(), xmlPullParser, attributeSet, theme);
            }
            catch (Exception exception) {
                Log.e((String)"VdcInflateDelegate", (String)"Exception while inflating <vector>", (Throwable)exception);
                return null;
            }
        }
    }

}

