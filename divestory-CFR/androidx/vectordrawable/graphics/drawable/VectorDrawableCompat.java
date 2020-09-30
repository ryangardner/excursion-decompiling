/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.PathMeasure
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.Shader
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.VectorDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 */
package androidx.vectordrawable.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import androidx.collection.ArrayMap;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.AndroidResources;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCommon;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat
extends VectorDrawableCommon {
    private static final boolean DBG_VECTOR_DRAWABLE = false;
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private static final int LINECAP_BUTT = 0;
    private static final int LINECAP_ROUND = 1;
    private static final int LINECAP_SQUARE = 2;
    private static final int LINEJOIN_BEVEL = 2;
    private static final int LINEJOIN_MITER = 0;
    private static final int LINEJOIN_ROUND = 1;
    static final String LOGTAG = "VectorDrawableCompat";
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    private boolean mAllowCaching = true;
    private Drawable.ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds = new Rect();
    private final float[] mTmpFloats = new float[9];
    private final Matrix mTmpMatrix = new Matrix();
    private VectorDrawableCompatState mVectorState;

    VectorDrawableCompat() {
        this.mVectorState = new VectorDrawableCompatState();
    }

    VectorDrawableCompat(VectorDrawableCompatState vectorDrawableCompatState) {
        this.mVectorState = vectorDrawableCompatState;
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
    }

    static int applyAlpha(int n, float f) {
        return n & 16777215 | (int)((float)Color.alpha((int)n) * f) << 24;
    }

    public static VectorDrawableCompat create(Resources object, int n, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = ResourcesCompat.getDrawable(object, n, theme);
            vectorDrawableCompat.mCachedConstantStateDelegate = new VectorDrawableDelegateState(vectorDrawableCompat.mDelegateDrawable.getConstantState());
            return vectorDrawableCompat;
        }
        try {
            XmlResourceParser xmlResourceParser = object.getXml(n);
            AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
            while ((n = xmlResourceParser.next()) != 2 && n != 1) {
            }
            if (n == 2) {
                return VectorDrawableCompat.createFromXmlInner(object, xmlResourceParser, attributeSet, theme);
            }
            object = new XmlPullParserException("No start tag found");
            throw object;
        }
        catch (IOException iOException) {
            Log.e((String)LOGTAG, (String)"parser error", (Throwable)iOException);
            return null;
        }
        catch (XmlPullParserException xmlPullParserException) {
            Log.e((String)LOGTAG, (String)"parser error", (Throwable)xmlPullParserException);
        }
        return null;
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    private void inflateInternal(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
        ArrayDeque<Object> arrayDeque = new ArrayDeque<Object>();
        arrayDeque.push(vPathRenderer.mRootGroup);
        int n = xmlPullParser.getEventType();
        int n2 = xmlPullParser.getDepth();
        int n3 = 1;
        while (n != 1 && (xmlPullParser.getDepth() >= n2 + 1 || n != 3)) {
            int n4;
            if (n == 2) {
                Object object = xmlPullParser.getName();
                VGroup vGroup = (VGroup)arrayDeque.peek();
                if (SHAPE_PATH.equals(object)) {
                    object = new VFullPath();
                    ((VFullPath)object).inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.mChildren.add((VObject)object);
                    if (((VPath)object).getPathName() != null) {
                        vPathRenderer.mVGTargetsMap.put(((VPath)object).getPathName(), object);
                    }
                    n4 = 0;
                    n3 = vectorDrawableCompatState.mChangingConfigurations;
                    vectorDrawableCompatState.mChangingConfigurations = ((VFullPath)object).mChangingConfigurations | n3;
                } else if (SHAPE_CLIP_PATH.equals(object)) {
                    object = new VClipPath();
                    ((VClipPath)object).inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.mChildren.add((VObject)object);
                    if (((VPath)object).getPathName() != null) {
                        vPathRenderer.mVGTargetsMap.put(((VPath)object).getPathName(), object);
                    }
                    n4 = vectorDrawableCompatState.mChangingConfigurations;
                    vectorDrawableCompatState.mChangingConfigurations = ((VClipPath)object).mChangingConfigurations | n4;
                    n4 = n3;
                } else {
                    n4 = n3;
                    if (SHAPE_GROUP.equals(object)) {
                        object = new VGroup();
                        ((VGroup)object).inflate(resources, attributeSet, theme, xmlPullParser);
                        vGroup.mChildren.add((VObject)object);
                        arrayDeque.push(object);
                        if (((VGroup)object).getGroupName() != null) {
                            vPathRenderer.mVGTargetsMap.put(((VGroup)object).getGroupName(), object);
                        }
                        n4 = vectorDrawableCompatState.mChangingConfigurations;
                        vectorDrawableCompatState.mChangingConfigurations = ((VGroup)object).mChangingConfigurations | n4;
                        n4 = n3;
                    }
                }
            } else {
                n4 = n3;
                if (n == 3) {
                    n4 = n3;
                    if (SHAPE_GROUP.equals(xmlPullParser.getName())) {
                        arrayDeque.pop();
                        n4 = n3;
                    }
                }
            }
            n = xmlPullParser.next();
            n3 = n4;
        }
        if (n3 != 0) throw new XmlPullParserException("no path defined");
    }

    private boolean needMirroring() {
        boolean bl;
        int n = Build.VERSION.SDK_INT;
        boolean bl2 = bl = false;
        if (n < 17) return bl2;
        bl2 = bl;
        if (!this.isAutoMirrored()) return bl2;
        bl2 = bl;
        if (DrawableCompat.getLayoutDirection(this) != 1) return bl2;
        return true;
    }

    private static PorterDuff.Mode parseTintModeCompat(int n, PorterDuff.Mode mode) {
        if (n == 3) return PorterDuff.Mode.SRC_OVER;
        if (n == 5) return PorterDuff.Mode.SRC_IN;
        if (n == 9) return PorterDuff.Mode.SRC_ATOP;
        switch (n) {
            default: {
                return mode;
            }
            case 16: {
                return PorterDuff.Mode.ADD;
            }
            case 15: {
                return PorterDuff.Mode.SCREEN;
            }
            case 14: 
        }
        return PorterDuff.Mode.MULTIPLY;
    }

    private void printGroupTree(VGroup vGroup, int n) {
        StringBuilder stringBuilder;
        int n2;
        int n3 = 0;
        Object object = "";
        for (n2 = 0; n2 < n; ++n2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("    ");
            object = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append("current group is :");
        stringBuilder.append(vGroup.getGroupName());
        stringBuilder.append(" rotation is ");
        stringBuilder.append(vGroup.mRotate);
        Log.v((String)LOGTAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append("matrix is :");
        stringBuilder.append(vGroup.getLocalMatrix().toString());
        Log.v((String)LOGTAG, (String)stringBuilder.toString());
        n2 = n3;
        while (n2 < vGroup.mChildren.size()) {
            object = vGroup.mChildren.get(n2);
            if (object instanceof VGroup) {
                this.printGroupTree((VGroup)object, n + 1);
            } else {
                ((VPath)object).printVPath(n + 1);
            }
            ++n2;
        }
    }

    private void updateStateFromTypedArray(TypedArray object, XmlPullParser object2, Resources.Theme theme) throws XmlPullParserException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
        vectorDrawableCompatState.mTintMode = VectorDrawableCompat.parseTintModeCompat(TypedArrayUtils.getNamedInt(object, (XmlPullParser)object2, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        if ((theme = TypedArrayUtils.getNamedColorStateList(object, (XmlPullParser)object2, theme, "tint", 1)) != null) {
            vectorDrawableCompatState.mTint = theme;
        }
        vectorDrawableCompatState.mAutoMirrored = TypedArrayUtils.getNamedBoolean(object, (XmlPullParser)object2, "autoMirrored", 5, vectorDrawableCompatState.mAutoMirrored);
        vPathRenderer.mViewportWidth = TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "viewportWidth", 7, vPathRenderer.mViewportWidth);
        vPathRenderer.mViewportHeight = TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "viewportHeight", 8, vPathRenderer.mViewportHeight);
        if (vPathRenderer.mViewportWidth <= 0.0f) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(object.getPositionDescription());
            ((StringBuilder)object2).append("<vector> tag requires viewportWidth > 0");
            throw new XmlPullParserException(((StringBuilder)object2).toString());
        }
        if (vPathRenderer.mViewportHeight <= 0.0f) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(object.getPositionDescription());
            ((StringBuilder)object2).append("<vector> tag requires viewportHeight > 0");
            throw new XmlPullParserException(((StringBuilder)object2).toString());
        }
        vPathRenderer.mBaseWidth = object.getDimension(3, vPathRenderer.mBaseWidth);
        vPathRenderer.mBaseHeight = object.getDimension(2, vPathRenderer.mBaseHeight);
        if (vPathRenderer.mBaseWidth <= 0.0f) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(object.getPositionDescription());
            ((StringBuilder)object2).append("<vector> tag requires width > 0");
            throw new XmlPullParserException(((StringBuilder)object2).toString());
        }
        if (!(vPathRenderer.mBaseHeight <= 0.0f)) {
            vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "alpha", 4, vPathRenderer.getAlpha()));
            object = object.getString(0);
            if (object == null) return;
            vPathRenderer.mRootName = object;
            vPathRenderer.mVGTargetsMap.put((String)object, vPathRenderer);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(object.getPositionDescription());
        ((StringBuilder)object2).append("<vector> tag requires height > 0");
        throw new XmlPullParserException(((StringBuilder)object2).toString());
    }

    public boolean canApplyTheme() {
        if (this.mDelegateDrawable == null) return false;
        DrawableCompat.canApplyTheme(this.mDelegateDrawable);
        return false;
    }

    public void draw(Canvas canvas) {
        ColorFilter colorFilter;
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.draw(canvas);
            return;
        }
        this.copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0) return;
        if (this.mTmpBounds.height() <= 0) {
            return;
        }
        ColorFilter colorFilter2 = colorFilter = this.mColorFilter;
        if (colorFilter == null) {
            colorFilter2 = this.mTintFilter;
        }
        canvas.getMatrix(this.mTmpMatrix);
        this.mTmpMatrix.getValues(this.mTmpFloats);
        float f = Math.abs(this.mTmpFloats[0]);
        float f2 = Math.abs(this.mTmpFloats[4]);
        float f3 = Math.abs(this.mTmpFloats[1]);
        float f4 = Math.abs(this.mTmpFloats[3]);
        if (f3 != 0.0f || f4 != 0.0f) {
            f = 1.0f;
            f2 = 1.0f;
        }
        int n = (int)((float)this.mTmpBounds.width() * f);
        int n2 = (int)((float)this.mTmpBounds.height() * f2);
        n = Math.min(2048, n);
        n2 = Math.min(2048, n2);
        if (n <= 0) return;
        if (n2 <= 0) {
            return;
        }
        int n3 = canvas.save();
        canvas.translate((float)this.mTmpBounds.left, (float)this.mTmpBounds.top);
        if (this.needMirroring()) {
            canvas.translate((float)this.mTmpBounds.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mTmpBounds.offsetTo(0, 0);
        this.mVectorState.createCachedBitmapIfNeeded(n, n2);
        if (!this.mAllowCaching) {
            this.mVectorState.updateCachedBitmap(n, n2);
        } else if (!this.mVectorState.canReuseCache()) {
            this.mVectorState.updateCachedBitmap(n, n2);
            this.mVectorState.updateCacheStates();
        }
        this.mVectorState.drawCachedBitmapWithRootAlpha(canvas, colorFilter2, this.mTmpBounds);
        canvas.restoreToCount(n3);
    }

    public int getAlpha() {
        if (this.mDelegateDrawable == null) return this.mVectorState.mVPathRenderer.getRootAlpha();
        return DrawableCompat.getAlpha(this.mDelegateDrawable);
    }

    public int getChangingConfigurations() {
        if (this.mDelegateDrawable == null) return super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
        return this.mDelegateDrawable.getChangingConfigurations();
    }

    public ColorFilter getColorFilter() {
        if (this.mDelegateDrawable == null) return this.mColorFilter;
        return DrawableCompat.getColorFilter(this.mDelegateDrawable);
    }

    public Drawable.ConstantState getConstantState() {
        if (this.mDelegateDrawable != null && Build.VERSION.SDK_INT >= 24) {
            return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        this.mVectorState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mVectorState;
    }

    public int getIntrinsicHeight() {
        if (this.mDelegateDrawable == null) return (int)this.mVectorState.mVPathRenderer.mBaseHeight;
        return this.mDelegateDrawable.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        if (this.mDelegateDrawable == null) return (int)this.mVectorState.mVPathRenderer.mBaseWidth;
        return this.mDelegateDrawable.getIntrinsicWidth();
    }

    public int getOpacity() {
        if (this.mDelegateDrawable == null) return -3;
        return this.mDelegateDrawable.getOpacity();
    }

    public float getPixelSize() {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState == null) return 1.0f;
        if (vectorDrawableCompatState.mVPathRenderer == null) return 1.0f;
        if (this.mVectorState.mVPathRenderer.mBaseWidth == 0.0f) return 1.0f;
        if (this.mVectorState.mVPathRenderer.mBaseHeight == 0.0f) return 1.0f;
        if (this.mVectorState.mVPathRenderer.mViewportHeight == 0.0f) return 1.0f;
        if (this.mVectorState.mVPathRenderer.mViewportWidth == 0.0f) {
            return 1.0f;
        }
        float f = this.mVectorState.mVPathRenderer.mBaseWidth;
        float f2 = this.mVectorState.mVPathRenderer.mBaseHeight;
        float f3 = this.mVectorState.mVPathRenderer.mViewportWidth;
        float f4 = this.mVectorState.mVPathRenderer.mViewportHeight;
        return Math.min(f3 / f, f4 / f2);
    }

    Object getTargetByName(String string2) {
        return this.mVectorState.mVPathRenderer.mVGTargetsMap.get(string2);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.inflate(resources, xmlPullParser, attributeSet);
            return;
        }
        this.inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.inflate(this.mDelegateDrawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        vectorDrawableCompatState.mVPathRenderer = new VPathRenderer();
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
        this.updateStateFromTypedArray(typedArray, xmlPullParser, theme);
        typedArray.recycle();
        vectorDrawableCompatState.mChangingConfigurations = this.getChangingConfigurations();
        vectorDrawableCompatState.mCacheDirty = true;
        this.inflateInternal(resources, xmlPullParser, attributeSet, theme);
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
    }

    public void invalidateSelf() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.invalidateSelf();
            return;
        }
        super.invalidateSelf();
    }

    public boolean isAutoMirrored() {
        if (this.mDelegateDrawable == null) return this.mVectorState.mAutoMirrored;
        return DrawableCompat.isAutoMirrored(this.mDelegateDrawable);
    }

    public boolean isStateful() {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.isStateful();
        }
        if (super.isStateful()) return true;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState == null) return false;
        if (vectorDrawableCompatState.isStateful()) return true;
        if (this.mVectorState.mTint == null) return false;
        if (!this.mVectorState.mTint.isStateful()) return false;
        return true;
    }

    public Drawable mutate() {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.mutate();
            return this;
        }
        if (this.mMutated) return this;
        if (super.mutate() != this) return this;
        this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
        this.mMutated = true;
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        if (this.mDelegateDrawable == null) return;
        this.mDelegateDrawable.setBounds(rect);
    }

    protected boolean onStateChange(int[] arrn) {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState(arrn);
        }
        boolean bl = false;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        ColorStateList colorStateList = vectorDrawableCompatState.mTint;
        boolean bl2 = true;
        boolean bl3 = bl;
        if (colorStateList != null) {
            bl3 = bl;
            if (vectorDrawableCompatState.mTintMode != null) {
                this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
                this.invalidateSelf();
                bl3 = true;
            }
        }
        if (!vectorDrawableCompatState.isStateful()) return bl3;
        if (!vectorDrawableCompatState.onStateChanged(arrn)) return bl3;
        this.invalidateSelf();
        return bl2;
    }

    public void scheduleSelf(Runnable runnable2, long l) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.scheduleSelf(runnable2, l);
            return;
        }
        super.scheduleSelf(runnable2, l);
    }

    void setAllowCaching(boolean bl) {
        this.mAllowCaching = bl;
    }

    public void setAlpha(int n) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setAlpha(n);
            return;
        }
        if (this.mVectorState.mVPathRenderer.getRootAlpha() == n) return;
        this.mVectorState.mVPathRenderer.setRootAlpha(n);
        this.invalidateSelf();
    }

    public void setAutoMirrored(boolean bl) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setAutoMirrored(this.mDelegateDrawable, bl);
            return;
        }
        this.mVectorState.mAutoMirrored = bl;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter(colorFilter);
            return;
        }
        this.mColorFilter = colorFilter;
        this.invalidateSelf();
    }

    @Override
    public void setTint(int n) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTint(this.mDelegateDrawable, n);
            return;
        }
        this.setTintList(ColorStateList.valueOf((int)n));
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintList(this.mDelegateDrawable, colorStateList);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.mTint == colorStateList) return;
        vectorDrawableCompatState.mTint = colorStateList;
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, colorStateList, vectorDrawableCompatState.mTintMode);
        this.invalidateSelf();
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintMode(this.mDelegateDrawable, mode);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.mTintMode == mode) return;
        vectorDrawableCompatState.mTintMode = mode;
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, mode);
        this.invalidateSelf();
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        if (this.mDelegateDrawable == null) return super.setVisible(bl, bl2);
        return this.mDelegateDrawable.setVisible(bl, bl2);
    }

    public void unscheduleSelf(Runnable runnable2) {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.unscheduleSelf(runnable2);
            return;
        }
        super.unscheduleSelf(runnable2);
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null) return null;
        if (mode != null) return new PorterDuffColorFilter(colorStateList.getColorForState(this.getState(), 0), mode);
        return null;
    }

    private static class VClipPath
    extends VPath {
        VClipPath() {
        }

        VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            String string2 = typedArray.getString(0);
            if (string2 != null) {
                this.mPathName = string2;
            }
            if ((string2 = typedArray.getString(1)) != null) {
                this.mNodes = PathParser.createNodesFromPathData(string2);
            }
            this.mFillRule = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 2, 0);
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (!TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                return;
            }
            resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
            this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser);
            resources.recycle();
        }

        @Override
        public boolean isClipPath() {
            return true;
        }
    }

    private static class VFullPath
    extends VPath {
        float mFillAlpha = 1.0f;
        ComplexColorCompat mFillColor;
        float mStrokeAlpha = 1.0f;
        ComplexColorCompat mStrokeColor;
        Paint.Cap mStrokeLineCap = Paint.Cap.BUTT;
        Paint.Join mStrokeLineJoin = Paint.Join.MITER;
        float mStrokeMiterlimit = 4.0f;
        float mStrokeWidth = 0.0f;
        private int[] mThemeAttrs;
        float mTrimPathEnd = 1.0f;
        float mTrimPathOffset = 0.0f;
        float mTrimPathStart = 0.0f;

        VFullPath() {
        }

        VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.mStrokeColor = vFullPath.mStrokeColor;
            this.mStrokeWidth = vFullPath.mStrokeWidth;
            this.mStrokeAlpha = vFullPath.mStrokeAlpha;
            this.mFillColor = vFullPath.mFillColor;
            this.mFillRule = vFullPath.mFillRule;
            this.mFillAlpha = vFullPath.mFillAlpha;
            this.mTrimPathStart = vFullPath.mTrimPathStart;
            this.mTrimPathEnd = vFullPath.mTrimPathEnd;
            this.mTrimPathOffset = vFullPath.mTrimPathOffset;
            this.mStrokeLineCap = vFullPath.mStrokeLineCap;
            this.mStrokeLineJoin = vFullPath.mStrokeLineJoin;
            this.mStrokeMiterlimit = vFullPath.mStrokeMiterlimit;
        }

        private Paint.Cap getStrokeLineCap(int n, Paint.Cap cap) {
            if (n == 0) return Paint.Cap.BUTT;
            if (n == 1) return Paint.Cap.ROUND;
            if (n == 2) return Paint.Cap.SQUARE;
            return cap;
        }

        private Paint.Join getStrokeLineJoin(int n, Paint.Join join) {
            if (n == 0) return Paint.Join.MITER;
            if (n == 1) return Paint.Join.ROUND;
            if (n == 2) return Paint.Join.BEVEL;
            return join;
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.mThemeAttrs = null;
            if (!TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                return;
            }
            String string2 = typedArray.getString(0);
            if (string2 != null) {
                this.mPathName = string2;
            }
            if ((string2 = typedArray.getString(2)) != null) {
                this.mNodes = PathParser.createNodesFromPathData(string2);
            }
            this.mFillColor = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "fillColor", 1, 0);
            this.mFillAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.mFillAlpha);
            this.mStrokeLineCap = this.getStrokeLineCap(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.mStrokeLineCap);
            this.mStrokeLineJoin = this.getStrokeLineJoin(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.mStrokeLineJoin);
            this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.mStrokeMiterlimit);
            this.mStrokeColor = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
            this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.mStrokeAlpha);
            this.mStrokeWidth = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.mStrokeWidth);
            this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.mTrimPathEnd);
            this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.mTrimPathOffset);
            this.mTrimPathStart = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.mTrimPathStart);
            this.mFillRule = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.mFillRule);
        }

        @Override
        public void applyTheme(Resources.Theme theme) {
            if (this.mThemeAttrs != null) return;
        }

        @Override
        public boolean canApplyTheme() {
            if (this.mThemeAttrs == null) return false;
            return true;
        }

        float getFillAlpha() {
            return this.mFillAlpha;
        }

        int getFillColor() {
            return this.mFillColor.getColor();
        }

        float getStrokeAlpha() {
            return this.mStrokeAlpha;
        }

        int getStrokeColor() {
            return this.mStrokeColor.getColor();
        }

        float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        float getTrimPathEnd() {
            return this.mTrimPathEnd;
        }

        float getTrimPathOffset() {
            return this.mTrimPathOffset;
        }

        float getTrimPathStart() {
            return this.mTrimPathStart;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
            this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser, theme);
            resources.recycle();
        }

        @Override
        public boolean isStateful() {
            if (this.mFillColor.isStateful()) return true;
            if (this.mStrokeColor.isStateful()) return true;
            return false;
        }

        @Override
        public boolean onStateChanged(int[] arrn) {
            boolean bl = this.mFillColor.onStateChanged(arrn);
            return this.mStrokeColor.onStateChanged(arrn) | bl;
        }

        void setFillAlpha(float f) {
            this.mFillAlpha = f;
        }

        void setFillColor(int n) {
            this.mFillColor.setColor(n);
        }

        void setStrokeAlpha(float f) {
            this.mStrokeAlpha = f;
        }

        void setStrokeColor(int n) {
            this.mStrokeColor.setColor(n);
        }

        void setStrokeWidth(float f) {
            this.mStrokeWidth = f;
        }

        void setTrimPathEnd(float f) {
            this.mTrimPathEnd = f;
        }

        void setTrimPathOffset(float f) {
            this.mTrimPathOffset = f;
        }

        void setTrimPathStart(float f) {
            this.mTrimPathStart = f;
        }
    }

    private static class VGroup
    extends VObject {
        int mChangingConfigurations;
        final ArrayList<VObject> mChildren = new ArrayList();
        private String mGroupName = null;
        final Matrix mLocalMatrix = new Matrix();
        private float mPivotX = 0.0f;
        private float mPivotY = 0.0f;
        float mRotate = 0.0f;
        private float mScaleX = 1.0f;
        private float mScaleY = 1.0f;
        final Matrix mStackedMatrix = new Matrix();
        private int[] mThemeAttrs;
        private float mTranslateX = 0.0f;
        private float mTranslateY = 0.0f;

        public VGroup() {
        }

        public VGroup(VGroup vObject, ArrayMap<String, Object> arrayMap) {
            this.mRotate = vObject.mRotate;
            this.mPivotX = vObject.mPivotX;
            this.mPivotY = vObject.mPivotY;
            this.mScaleX = vObject.mScaleX;
            this.mScaleY = vObject.mScaleY;
            this.mTranslateX = vObject.mTranslateX;
            this.mTranslateY = vObject.mTranslateY;
            this.mThemeAttrs = vObject.mThemeAttrs;
            Object object = vObject.mGroupName;
            this.mGroupName = object;
            this.mChangingConfigurations = vObject.mChangingConfigurations;
            if (object != null) {
                arrayMap.put((String)object, this);
            }
            this.mLocalMatrix.set(vObject.mLocalMatrix);
            object = vObject.mChildren;
            int n = 0;
            while (n < ((ArrayList)object).size()) {
                vObject = ((ArrayList)object).get(n);
                if (vObject instanceof VGroup) {
                    this.mChildren.add(new VGroup((VGroup)vObject, arrayMap));
                } else {
                    if (vObject instanceof VFullPath) {
                        vObject = new VFullPath((VFullPath)vObject);
                    } else {
                        if (!(vObject instanceof VClipPath)) throw new IllegalStateException("Unknown object in the tree!");
                        vObject = new VClipPath((VClipPath)vObject);
                    }
                    this.mChildren.add(vObject);
                    if (((VPath)vObject).mPathName != null) {
                        arrayMap.put(((VPath)vObject).mPathName, vObject);
                    }
                }
                ++n;
            }
        }

        private void updateLocalMatrix() {
            this.mLocalMatrix.reset();
            this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
            this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
            this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f);
            this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        private void updateStateFromTypedArray(TypedArray object, XmlPullParser xmlPullParser) {
            this.mThemeAttrs = null;
            this.mRotate = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "rotation", 5, this.mRotate);
            this.mPivotX = object.getFloat(1, this.mPivotX);
            this.mPivotY = object.getFloat(2, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "scaleX", 3, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "scaleY", 4, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "translateX", 6, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat(object, xmlPullParser, "translateY", 7, this.mTranslateY);
            if ((object = object.getString(0)) != null) {
                this.mGroupName = object;
            }
            this.updateLocalMatrix();
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.mLocalMatrix;
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public float getRotation() {
            return this.mRotate;
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
            this.updateStateFromTypedArray((TypedArray)resources, xmlPullParser);
            resources.recycle();
        }

        @Override
        public boolean isStateful() {
            int n = 0;
            while (n < this.mChildren.size()) {
                if (this.mChildren.get(n).isStateful()) {
                    return true;
                }
                ++n;
            }
            return false;
        }

        @Override
        public boolean onStateChanged(int[] arrn) {
            int n = 0;
            boolean bl = false;
            while (n < this.mChildren.size()) {
                bl |= this.mChildren.get(n).onStateChanged(arrn);
                ++n;
            }
            return bl;
        }

        public void setPivotX(float f) {
            if (f == this.mPivotX) return;
            this.mPivotX = f;
            this.updateLocalMatrix();
        }

        public void setPivotY(float f) {
            if (f == this.mPivotY) return;
            this.mPivotY = f;
            this.updateLocalMatrix();
        }

        public void setRotation(float f) {
            if (f == this.mRotate) return;
            this.mRotate = f;
            this.updateLocalMatrix();
        }

        public void setScaleX(float f) {
            if (f == this.mScaleX) return;
            this.mScaleX = f;
            this.updateLocalMatrix();
        }

        public void setScaleY(float f) {
            if (f == this.mScaleY) return;
            this.mScaleY = f;
            this.updateLocalMatrix();
        }

        public void setTranslateX(float f) {
            if (f == this.mTranslateX) return;
            this.mTranslateX = f;
            this.updateLocalMatrix();
        }

        public void setTranslateY(float f) {
            if (f == this.mTranslateY) return;
            this.mTranslateY = f;
            this.updateLocalMatrix();
        }
    }

    private static abstract class VObject {
        private VObject() {
        }

        public boolean isStateful() {
            return false;
        }

        public boolean onStateChanged(int[] arrn) {
            return false;
        }
    }

    private static abstract class VPath
    extends VObject {
        protected static final int FILL_TYPE_WINDING = 0;
        int mChangingConfigurations;
        int mFillRule = 0;
        protected PathParser.PathDataNode[] mNodes = null;
        String mPathName;

        public VPath() {
        }

        public VPath(VPath vPath) {
            this.mPathName = vPath.mPathName;
            this.mChangingConfigurations = vPath.mChangingConfigurations;
            this.mNodes = PathParser.deepCopyNodes(vPath.mNodes);
        }

        public void applyTheme(Resources.Theme theme) {
        }

        public boolean canApplyTheme() {
            return false;
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.mNodes;
        }

        public String getPathName() {
            return this.mPathName;
        }

        public boolean isClipPath() {
            return false;
        }

        public String nodesToString(PathParser.PathDataNode[] arrpathDataNode) {
            String string2 = " ";
            int n = 0;
            while (n < arrpathDataNode.length) {
                float[] arrf = new StringBuilder();
                arrf.append(string2);
                arrf.append(arrpathDataNode[n].mType);
                arrf.append(":");
                string2 = arrf.toString();
                arrf = arrpathDataNode[n].mParams;
                for (int i = 0; i < arrf.length; ++i) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append(arrf[i]);
                    stringBuilder.append(",");
                    string2 = stringBuilder.toString();
                }
                ++n;
            }
            return string2;
        }

        public void printVPath(int n) {
            String string2 = "";
            int n2 = 0;
            do {
                StringBuilder stringBuilder;
                if (n2 >= n) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append("current path is :");
                    stringBuilder.append(this.mPathName);
                    stringBuilder.append(" pathData is ");
                    stringBuilder.append(this.nodesToString(this.mNodes));
                    Log.v((String)VectorDrawableCompat.LOGTAG, (String)stringBuilder.toString());
                    return;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("    ");
                string2 = stringBuilder.toString();
                ++n2;
            } while (true);
        }

        public void setPathData(PathParser.PathDataNode[] arrpathDataNode) {
            if (!PathParser.canMorph(this.mNodes, arrpathDataNode)) {
                this.mNodes = PathParser.deepCopyNodes(arrpathDataNode);
                return;
            }
            PathParser.updateNodes(this.mNodes, arrpathDataNode);
        }

        public void toPath(Path path) {
            path.reset();
            PathParser.PathDataNode[] arrpathDataNode = this.mNodes;
            if (arrpathDataNode == null) return;
            PathParser.PathDataNode.nodesToPath(arrpathDataNode, path);
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX = new Matrix();
        float mBaseHeight = 0.0f;
        float mBaseWidth = 0.0f;
        private int mChangingConfigurations;
        Paint mFillPaint;
        private final Matrix mFinalPathMatrix = new Matrix();
        Boolean mIsStateful = null;
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;
        int mRootAlpha = 255;
        final VGroup mRootGroup;
        String mRootName = null;
        Paint mStrokePaint;
        final ArrayMap<String, Object> mVGTargetsMap = new ArrayMap();
        float mViewportHeight = 0.0f;
        float mViewportWidth = 0.0f;

        public VPathRenderer() {
            this.mRootGroup = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            this.mRootGroup = new VGroup(vPathRenderer.mRootGroup, this.mVGTargetsMap);
            this.mPath = new Path(vPathRenderer.mPath);
            this.mRenderPath = new Path(vPathRenderer.mRenderPath);
            this.mBaseWidth = vPathRenderer.mBaseWidth;
            this.mBaseHeight = vPathRenderer.mBaseHeight;
            this.mViewportWidth = vPathRenderer.mViewportWidth;
            this.mViewportHeight = vPathRenderer.mViewportHeight;
            this.mChangingConfigurations = vPathRenderer.mChangingConfigurations;
            this.mRootAlpha = vPathRenderer.mRootAlpha;
            this.mRootName = vPathRenderer.mRootName;
            String string2 = vPathRenderer.mRootName;
            if (string2 != null) {
                this.mVGTargetsMap.put(string2, this);
            }
            this.mIsStateful = vPathRenderer.mIsStateful;
        }

        private static float cross(float f, float f2, float f3, float f4) {
            return f * f4 - f2 * f3;
        }

        private void drawGroupTree(VGroup vGroup, Matrix object, Canvas canvas, int n, int n2, ColorFilter colorFilter) {
            vGroup.mStackedMatrix.set(object);
            vGroup.mStackedMatrix.preConcat(vGroup.mLocalMatrix);
            canvas.save();
            int n3 = 0;
            do {
                if (n3 >= vGroup.mChildren.size()) {
                    canvas.restore();
                    return;
                }
                object = vGroup.mChildren.get(n3);
                if (object instanceof VGroup) {
                    this.drawGroupTree((VGroup)object, vGroup.mStackedMatrix, canvas, n, n2, colorFilter);
                } else if (object instanceof VPath) {
                    this.drawPath(vGroup, (VPath)object, canvas, n, n2, colorFilter);
                }
                ++n3;
            } while (true);
        }

        private void drawPath(VGroup object, VPath vPath, Canvas canvas, int n, int n2, ColorFilter colorFilter) {
            float f = (float)n / this.mViewportWidth;
            float f2 = (float)n2 / this.mViewportHeight;
            float f3 = Math.min(f, f2);
            object = ((VGroup)object).mStackedMatrix;
            this.mFinalPathMatrix.set((Matrix)object);
            this.mFinalPathMatrix.postScale(f, f2);
            f2 = this.getMatrixScale((Matrix)object);
            if (f2 == 0.0f) {
                return;
            }
            vPath.toPath(this.mPath);
            Path path = this.mPath;
            this.mRenderPath.reset();
            if (vPath.isClipPath()) {
                colorFilter = this.mRenderPath;
                object = vPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                colorFilter.setFillType((Path.FillType)object);
                this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                canvas.clipPath(this.mRenderPath);
                return;
            }
            vPath = (VFullPath)vPath;
            if (((VFullPath)vPath).mTrimPathStart != 0.0f || ((VFullPath)vPath).mTrimPathEnd != 1.0f) {
                float f4 = ((VFullPath)vPath).mTrimPathStart;
                float f5 = ((VFullPath)vPath).mTrimPathOffset;
                float f6 = ((VFullPath)vPath).mTrimPathEnd;
                float f7 = ((VFullPath)vPath).mTrimPathOffset;
                if (this.mPathMeasure == null) {
                    this.mPathMeasure = new PathMeasure();
                }
                this.mPathMeasure.setPath(this.mPath, false);
                f = this.mPathMeasure.getLength();
                f4 = (f4 + f5) % 1.0f * f;
                f7 = (f6 + f7) % 1.0f * f;
                path.reset();
                if (f4 > f7) {
                    this.mPathMeasure.getSegment(f4, f, path, true);
                    this.mPathMeasure.getSegment(0.0f, f7, path, true);
                } else {
                    this.mPathMeasure.getSegment(f4, f7, path, true);
                }
                path.rLineTo(0.0f, 0.0f);
            }
            this.mRenderPath.addPath(path, this.mFinalPathMatrix);
            if (((VFullPath)vPath).mFillColor.willDraw()) {
                object = ((VFullPath)vPath).mFillColor;
                if (this.mFillPaint == null) {
                    path = new Paint(1);
                    this.mFillPaint = path;
                    path.setStyle(Paint.Style.FILL);
                }
                path = this.mFillPaint;
                if (((ComplexColorCompat)object).isGradient()) {
                    object = ((ComplexColorCompat)object).getShader();
                    object.setLocalMatrix(this.mFinalPathMatrix);
                    path.setShader((Shader)object);
                    path.setAlpha(Math.round(((VFullPath)vPath).mFillAlpha * 255.0f));
                } else {
                    path.setShader(null);
                    path.setAlpha(255);
                    path.setColor(VectorDrawableCompat.applyAlpha(((ComplexColorCompat)object).getColor(), ((VFullPath)vPath).mFillAlpha));
                }
                path.setColorFilter(colorFilter);
                Path path2 = this.mRenderPath;
                object = ((VFullPath)vPath).mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                path2.setFillType((Path.FillType)object);
                canvas.drawPath(this.mRenderPath, (Paint)path);
            }
            if (!((VFullPath)vPath).mStrokeColor.willDraw()) return;
            object = ((VFullPath)vPath).mStrokeColor;
            if (this.mStrokePaint == null) {
                path = new Paint(1);
                this.mStrokePaint = path;
                path.setStyle(Paint.Style.STROKE);
            }
            path = this.mStrokePaint;
            if (((VFullPath)vPath).mStrokeLineJoin != null) {
                path.setStrokeJoin(((VFullPath)vPath).mStrokeLineJoin);
            }
            if (((VFullPath)vPath).mStrokeLineCap != null) {
                path.setStrokeCap(((VFullPath)vPath).mStrokeLineCap);
            }
            path.setStrokeMiter(((VFullPath)vPath).mStrokeMiterlimit);
            if (((ComplexColorCompat)object).isGradient()) {
                object = ((ComplexColorCompat)object).getShader();
                object.setLocalMatrix(this.mFinalPathMatrix);
                path.setShader((Shader)object);
                path.setAlpha(Math.round(((VFullPath)vPath).mStrokeAlpha * 255.0f));
            } else {
                path.setShader(null);
                path.setAlpha(255);
                path.setColor(VectorDrawableCompat.applyAlpha(((ComplexColorCompat)object).getColor(), ((VFullPath)vPath).mStrokeAlpha));
            }
            path.setColorFilter(colorFilter);
            path.setStrokeWidth(((VFullPath)vPath).mStrokeWidth * (f3 * f2));
            canvas.drawPath(this.mRenderPath, (Paint)path);
        }

        private float getMatrixScale(Matrix matrix) {
            float[] arrf;
            float[] arrf2 = arrf = new float[4];
            arrf2[0] = 0.0f;
            arrf2[1] = 1.0f;
            arrf2[2] = 1.0f;
            arrf2[3] = 0.0f;
            matrix.mapVectors(arrf);
            float f = (float)Math.hypot(arrf[0], arrf[1]);
            float f2 = (float)Math.hypot(arrf[2], arrf[3]);
            float f3 = VPathRenderer.cross(arrf[0], arrf[1], arrf[2], arrf[3]);
            f2 = Math.max(f, f2);
            f = 0.0f;
            if (!(f2 > 0.0f)) return f;
            return Math.abs(f3) / f2;
        }

        public void draw(Canvas canvas, int n, int n2, ColorFilter colorFilter) {
            this.drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, canvas, n, n2, colorFilter);
        }

        public float getAlpha() {
            return (float)this.getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.mRootAlpha;
        }

        public boolean isStateful() {
            if (this.mIsStateful != null) return this.mIsStateful;
            this.mIsStateful = this.mRootGroup.isStateful();
            return this.mIsStateful;
        }

        public boolean onStateChanged(int[] arrn) {
            return this.mRootGroup.onStateChanged(arrn);
        }

        public void setAlpha(float f) {
            this.setRootAlpha((int)(f * 255.0f));
        }

        public void setRootAlpha(int n) {
            this.mRootAlpha = n;
        }
    }

    private static class VectorDrawableCompatState
    extends Drawable.ConstantState {
        boolean mAutoMirrored;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        Bitmap mCachedBitmap;
        int mCachedRootAlpha;
        int[] mCachedThemeAttrs;
        ColorStateList mCachedTint;
        PorterDuff.Mode mCachedTintMode;
        int mChangingConfigurations;
        Paint mTempPaint;
        ColorStateList mTint = null;
        PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;
        VPathRenderer mVPathRenderer;

        public VectorDrawableCompatState() {
            this.mVPathRenderer = new VPathRenderer();
        }

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            if (vectorDrawableCompatState == null) return;
            this.mChangingConfigurations = vectorDrawableCompatState.mChangingConfigurations;
            this.mVPathRenderer = new VPathRenderer(vectorDrawableCompatState.mVPathRenderer);
            if (vectorDrawableCompatState.mVPathRenderer.mFillPaint != null) {
                this.mVPathRenderer.mFillPaint = new Paint(vectorDrawableCompatState.mVPathRenderer.mFillPaint);
            }
            if (vectorDrawableCompatState.mVPathRenderer.mStrokePaint != null) {
                this.mVPathRenderer.mStrokePaint = new Paint(vectorDrawableCompatState.mVPathRenderer.mStrokePaint);
            }
            this.mTint = vectorDrawableCompatState.mTint;
            this.mTintMode = vectorDrawableCompatState.mTintMode;
            this.mAutoMirrored = vectorDrawableCompatState.mAutoMirrored;
        }

        public boolean canReuseBitmap(int n, int n2) {
            if (n != this.mCachedBitmap.getWidth()) return false;
            if (n2 != this.mCachedBitmap.getHeight()) return false;
            return true;
        }

        public boolean canReuseCache() {
            if (this.mCacheDirty) return false;
            if (this.mCachedTint != this.mTint) return false;
            if (this.mCachedTintMode != this.mTintMode) return false;
            if (this.mCachedAutoMirrored != this.mAutoMirrored) return false;
            if (this.mCachedRootAlpha != this.mVPathRenderer.getRootAlpha()) return false;
            return true;
        }

        public void createCachedBitmapIfNeeded(int n, int n2) {
            if (this.mCachedBitmap != null) {
                if (this.canReuseBitmap(n, n2)) return;
            }
            this.mCachedBitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            this.mCacheDirty = true;
        }

        public void drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            colorFilter = this.getPaint(colorFilter);
            canvas.drawBitmap(this.mCachedBitmap, null, rect, (Paint)colorFilter);
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public Paint getPaint(ColorFilter colorFilter) {
            if (!this.hasTranslucentRoot() && colorFilter == null) {
                return null;
            }
            if (this.mTempPaint == null) {
                Paint paint;
                this.mTempPaint = paint = new Paint();
                paint.setFilterBitmap(true);
            }
            this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha());
            this.mTempPaint.setColorFilter(colorFilter);
            return this.mTempPaint;
        }

        public boolean hasTranslucentRoot() {
            if (this.mVPathRenderer.getRootAlpha() >= 255) return false;
            return true;
        }

        public boolean isStateful() {
            return this.mVPathRenderer.isStateful();
        }

        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public boolean onStateChanged(int[] arrn) {
            boolean bl = this.mVPathRenderer.onStateChanged(arrn);
            this.mCacheDirty |= bl;
            return bl;
        }

        public void updateCacheStates() {
            this.mCachedTint = this.mTint;
            this.mCachedTintMode = this.mTintMode;
            this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha();
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public void updateCachedBitmap(int n, int n2) {
            this.mCachedBitmap.eraseColor(0);
            Canvas canvas = new Canvas(this.mCachedBitmap);
            this.mVPathRenderer.draw(canvas, n, n2, null);
        }
    }

    private static class VectorDrawableDelegateState
    extends Drawable.ConstantState {
        private final Drawable.ConstantState mDelegateState;

        public VectorDrawableDelegateState(Drawable.ConstantState constantState) {
            this.mDelegateState = constantState;
        }

        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable)this.mDelegateState.newDrawable();
            return vectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable)this.mDelegateState.newDrawable(resources);
            return vectorDrawableCompat;
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.mDelegateDrawable = (VectorDrawable)this.mDelegateState.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }
    }

}

