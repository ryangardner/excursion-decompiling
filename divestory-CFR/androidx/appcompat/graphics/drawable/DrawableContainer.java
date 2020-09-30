/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Outline
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.SystemClock
 *  android.util.DisplayMetrics
 *  android.util.SparseArray
 */
package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import androidx.core.graphics.drawable.DrawableCompat;

class DrawableContainer
extends Drawable
implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_DITHER = true;
    private static final String TAG = "DrawableContainer";
    private int mAlpha = 255;
    private Runnable mAnimationRunnable;
    private BlockInvalidateCallback mBlockInvalidateCallback;
    private int mCurIndex = -1;
    private Drawable mCurrDrawable;
    private DrawableContainerState mDrawableContainerState;
    private long mEnterAnimationEnd;
    private long mExitAnimationEnd;
    private boolean mHasAlpha;
    private Rect mHotspotBounds;
    private Drawable mLastDrawable;
    private boolean mMutated;

    DrawableContainer() {
    }

    private void initializeDrawableForDisplay(Drawable drawable2) {
        if (this.mBlockInvalidateCallback == null) {
            this.mBlockInvalidateCallback = new BlockInvalidateCallback();
        }
        drawable2.setCallback((Drawable.Callback)this.mBlockInvalidateCallback.wrap(drawable2.getCallback()));
        try {
            if (this.mDrawableContainerState.mEnterFadeDuration <= 0 && this.mHasAlpha) {
                drawable2.setAlpha(this.mAlpha);
            }
            if (this.mDrawableContainerState.mHasColorFilter) {
                drawable2.setColorFilter(this.mDrawableContainerState.mColorFilter);
            } else {
                if (this.mDrawableContainerState.mHasTintList) {
                    DrawableCompat.setTintList(drawable2, this.mDrawableContainerState.mTintList);
                }
                if (this.mDrawableContainerState.mHasTintMode) {
                    DrawableCompat.setTintMode(drawable2, this.mDrawableContainerState.mTintMode);
                }
            }
            drawable2.setVisible(this.isVisible(), true);
            drawable2.setDither(this.mDrawableContainerState.mDither);
            drawable2.setState(this.getState());
            drawable2.setLevel(this.getLevel());
            drawable2.setBounds(this.getBounds());
            if (Build.VERSION.SDK_INT >= 23) {
                drawable2.setLayoutDirection(this.getLayoutDirection());
            }
            if (Build.VERSION.SDK_INT >= 19) {
                drawable2.setAutoMirrored(this.mDrawableContainerState.mAutoMirrored);
            }
            Rect rect = this.mHotspotBounds;
            if (Build.VERSION.SDK_INT < 21) return;
            if (rect == null) return;
            drawable2.setHotspotBounds(rect.left, rect.top, rect.right, rect.bottom);
            return;
        }
        finally {
            drawable2.setCallback(this.mBlockInvalidateCallback.unwrap());
        }
    }

    private boolean needsMirroring() {
        boolean bl = this.isAutoMirrored();
        boolean bl2 = true;
        if (!bl) return false;
        if (DrawableCompat.getLayoutDirection(this) != 1) return false;
        return bl2;
    }

    static int resolveDensity(Resources resources, int n) {
        if (resources != null) {
            n = resources.getDisplayMetrics().densityDpi;
        }
        int n2 = n;
        if (n != 0) return n2;
        return 160;
    }

    void animate(boolean bl) {
        int n;
        int n2;
        long l;
        long l2;
        Drawable drawable2;
        block8 : {
            block6 : {
                block5 : {
                    block7 : {
                        n2 = 1;
                        this.mHasAlpha = true;
                        l2 = SystemClock.uptimeMillis();
                        drawable2 = this.mCurrDrawable;
                        if (drawable2 == null) break block5;
                        l = this.mEnterAnimationEnd;
                        if (l == 0L) break block6;
                        if (l > l2) break block7;
                        drawable2.setAlpha(this.mAlpha);
                        this.mEnterAnimationEnd = 0L;
                        break block6;
                    }
                    n = (int)((l - l2) * 255L) / this.mDrawableContainerState.mEnterFadeDuration;
                    this.mCurrDrawable.setAlpha((255 - n) * this.mAlpha / 255);
                    n = 1;
                    break block8;
                }
                this.mEnterAnimationEnd = 0L;
            }
            n = 0;
        }
        drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            l = this.mExitAnimationEnd;
            if (l != 0L) {
                if (l <= l2) {
                    drawable2.setVisible(false, false);
                    this.mLastDrawable = null;
                    this.mExitAnimationEnd = 0L;
                } else {
                    n = (int)((l - l2) * 255L) / this.mDrawableContainerState.mExitFadeDuration;
                    this.mLastDrawable.setAlpha(n * this.mAlpha / 255);
                    n = n2;
                }
            }
        } else {
            this.mExitAnimationEnd = 0L;
        }
        if (!bl) return;
        if (n == 0) return;
        this.scheduleSelf(this.mAnimationRunnable, l2 + 16L);
    }

    public void applyTheme(Resources.Theme theme) {
        this.mDrawableContainerState.applyTheme(theme);
    }

    public boolean canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme();
    }

    void clearMutated() {
        this.mDrawableContainerState.clearMutated();
        this.mMutated = false;
    }

    DrawableContainerState cloneConstantState() {
        return this.mDrawableContainerState;
    }

    public void draw(Canvas canvas) {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        if ((drawable2 = this.mLastDrawable) == null) return;
        drawable2.draw(canvas);
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
    }

    public final Drawable.ConstantState getConstantState() {
        if (!this.mDrawableContainerState.canConstantState()) return null;
        this.mDrawableContainerState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mDrawableContainerState;
    }

    public Drawable getCurrent() {
        return this.mCurrDrawable;
    }

    int getCurrentIndex() {
        return this.mCurIndex;
    }

    public void getHotspotBounds(Rect rect) {
        Rect rect2 = this.mHotspotBounds;
        if (rect2 != null) {
            rect.set(rect2);
            return;
        }
        super.getHotspotBounds(rect);
    }

    public int getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight();
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return -1;
        return drawable2.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth();
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return -1;
        return drawable2.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight();
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return 0;
        return drawable2.getMinimumHeight();
    }

    public int getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth();
        }
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return 0;
        return drawable2.getMinimumWidth();
    }

    public int getOpacity() {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return -2;
        if (!drawable2.isVisible()) return -2;
        return this.mDrawableContainerState.getOpacity();
    }

    public void getOutline(Outline outline) {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return;
        drawable2.getOutline(outline);
    }

    public boolean getPadding(Rect rect) {
        int n;
        boolean bl;
        Rect rect2 = this.mDrawableContainerState.getConstantPadding();
        if (rect2 != null) {
            rect.set(rect2);
            int n2 = rect2.left;
            n = rect2.top;
            int n3 = rect2.bottom;
            bl = (rect2.right | (n2 | n | n3)) != 0;
        } else {
            rect2 = this.mCurrDrawable;
            bl = rect2 != null ? rect2.getPadding(rect) : super.getPadding(rect);
        }
        if (!this.needsMirroring()) return bl;
        n = rect.left;
        rect.left = rect.right;
        rect.right = n;
        return bl;
    }

    public void invalidateDrawable(Drawable drawable2) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState != null) {
            drawableContainerState.invalidateCache();
        }
        if (drawable2 != this.mCurrDrawable) return;
        if (this.getCallback() == null) return;
        this.getCallback().invalidateDrawable((Drawable)this);
    }

    public boolean isAutoMirrored() {
        return this.mDrawableContainerState.mAutoMirrored;
    }

    public boolean isStateful() {
        return this.mDrawableContainerState.isStateful();
    }

    public void jumpToCurrentState() {
        boolean bl;
        Drawable drawable2 = this.mLastDrawable;
        boolean bl2 = true;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            this.mLastDrawable = null;
            bl = true;
        } else {
            bl = false;
        }
        drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha);
            }
        }
        if (this.mExitAnimationEnd != 0L) {
            this.mExitAnimationEnd = 0L;
            bl = true;
        }
        if (this.mEnterAnimationEnd != 0L) {
            this.mEnterAnimationEnd = 0L;
            bl = bl2;
        }
        if (!bl) return;
        this.invalidateSelf();
    }

    public Drawable mutate() {
        if (this.mMutated) return this;
        if (super.mutate() != this) return this;
        DrawableContainerState drawableContainerState = this.cloneConstantState();
        drawableContainerState.mutate();
        this.setConstantState(drawableContainerState);
        this.mMutated = true;
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
        if ((drawable2 = this.mCurrDrawable) == null) return;
        drawable2.setBounds(rect);
    }

    public boolean onLayoutDirectionChanged(int n) {
        return this.mDrawableContainerState.setLayoutDirection(n, this.getCurrentIndex());
    }

    protected boolean onLevelChange(int n) {
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            return drawable2.setLevel(n);
        }
        drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return false;
        return drawable2.setLevel(n);
    }

    protected boolean onStateChange(int[] arrn) {
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            return drawable2.setState(arrn);
        }
        drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return false;
        return drawable2.setState(arrn);
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable2, long l) {
        if (drawable2 != this.mCurrDrawable) return;
        if (this.getCallback() == null) return;
        this.getCallback().scheduleDrawable((Drawable)this, runnable2, l);
    }

    boolean selectDrawable(int n) {
        Object object;
        if (n == this.mCurIndex) {
            return false;
        }
        long l = SystemClock.uptimeMillis();
        if (this.mDrawableContainerState.mExitFadeDuration > 0) {
            object = this.mLastDrawable;
            if (object != null) {
                object.setVisible(false, false);
            }
            if ((object = this.mCurrDrawable) != null) {
                this.mLastDrawable = object;
                this.mExitAnimationEnd = (long)this.mDrawableContainerState.mExitFadeDuration + l;
            } else {
                this.mLastDrawable = null;
                this.mExitAnimationEnd = 0L;
            }
        } else {
            object = this.mCurrDrawable;
            if (object != null) {
                object.setVisible(false, false);
            }
        }
        if (n >= 0 && n < this.mDrawableContainerState.mNumChildren) {
            this.mCurrDrawable = object = this.mDrawableContainerState.getChild(n);
            this.mCurIndex = n;
            if (object != null) {
                if (this.mDrawableContainerState.mEnterFadeDuration > 0) {
                    this.mEnterAnimationEnd = l + (long)this.mDrawableContainerState.mEnterFadeDuration;
                }
                this.initializeDrawableForDisplay((Drawable)object);
            }
        } else {
            this.mCurrDrawable = null;
            this.mCurIndex = -1;
        }
        if (this.mEnterAnimationEnd != 0L || this.mExitAnimationEnd != 0L) {
            object = this.mAnimationRunnable;
            if (object == null) {
                this.mAnimationRunnable = new Runnable(){

                    @Override
                    public void run() {
                        DrawableContainer.this.animate(true);
                        DrawableContainer.this.invalidateSelf();
                    }
                };
            } else {
                this.unscheduleSelf((Runnable)object);
            }
            this.animate(true);
        }
        this.invalidateSelf();
        return true;
    }

    public void setAlpha(int n) {
        if (this.mHasAlpha) {
            if (this.mAlpha == n) return;
        }
        this.mHasAlpha = true;
        this.mAlpha = n;
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return;
        if (this.mEnterAnimationEnd == 0L) {
            drawable2.setAlpha(n);
            return;
        }
        this.animate(false);
    }

    public void setAutoMirrored(boolean bl) {
        if (this.mDrawableContainerState.mAutoMirrored == bl) return;
        this.mDrawableContainerState.mAutoMirrored = bl;
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return;
        DrawableCompat.setAutoMirrored(drawable2, this.mDrawableContainerState.mAutoMirrored);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawableContainerState.mHasColorFilter = true;
        if (this.mDrawableContainerState.mColorFilter == colorFilter) return;
        this.mDrawableContainerState.mColorFilter = colorFilter;
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return;
        drawable2.setColorFilter(colorFilter);
    }

    void setConstantState(DrawableContainerState drawableContainerState) {
        this.mDrawableContainerState = drawableContainerState;
        int n = this.mCurIndex;
        if (n >= 0) {
            drawableContainerState = drawableContainerState.getChild(n);
            this.mCurrDrawable = drawableContainerState;
            if (drawableContainerState != null) {
                this.initializeDrawableForDisplay((Drawable)drawableContainerState);
            }
        }
        this.mLastDrawable = null;
    }

    void setCurrentIndex(int n) {
        this.selectDrawable(n);
    }

    public void setDither(boolean bl) {
        if (this.mDrawableContainerState.mDither == bl) return;
        this.mDrawableContainerState.mDither = bl;
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return;
        drawable2.setDither(this.mDrawableContainerState.mDither);
    }

    public void setEnterFadeDuration(int n) {
        this.mDrawableContainerState.mEnterFadeDuration = n;
    }

    public void setExitFadeDuration(int n) {
        this.mDrawableContainerState.mExitFadeDuration = n;
    }

    public void setHotspot(float f, float f2) {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 == null) return;
        DrawableCompat.setHotspot(drawable2, f, f2);
    }

    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        Rect rect = this.mHotspotBounds;
        if (rect == null) {
            this.mHotspotBounds = new Rect(n, n2, n3, n4);
        } else {
            rect.set(n, n2, n3, n4);
        }
        rect = this.mCurrDrawable;
        if (rect == null) return;
        DrawableCompat.setHotspotBounds((Drawable)rect, n, n2, n3, n4);
    }

    public void setTintList(ColorStateList colorStateList) {
        this.mDrawableContainerState.mHasTintList = true;
        if (this.mDrawableContainerState.mTintList == colorStateList) return;
        this.mDrawableContainerState.mTintList = colorStateList;
        DrawableCompat.setTintList(this.mCurrDrawable, colorStateList);
    }

    public void setTintMode(PorterDuff.Mode mode) {
        this.mDrawableContainerState.mHasTintMode = true;
        if (this.mDrawableContainerState.mTintMode == mode) return;
        this.mDrawableContainerState.mTintMode = mode;
        DrawableCompat.setTintMode(this.mCurrDrawable, mode);
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.setVisible(bl, bl2);
        }
        if ((drawable2 = this.mCurrDrawable) == null) return bl3;
        drawable2.setVisible(bl, bl2);
        return bl3;
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable2) {
        if (drawable2 != this.mCurrDrawable) return;
        if (this.getCallback() == null) return;
        this.getCallback().unscheduleDrawable((Drawable)this, runnable2);
    }

    final void updateDensity(Resources resources) {
        this.mDrawableContainerState.updateDensity(resources);
    }

    static class BlockInvalidateCallback
    implements Drawable.Callback {
        private Drawable.Callback mCallback;

        BlockInvalidateCallback() {
        }

        public void invalidateDrawable(Drawable drawable2) {
        }

        public void scheduleDrawable(Drawable drawable2, Runnable runnable2, long l) {
            Drawable.Callback callback = this.mCallback;
            if (callback == null) return;
            callback.scheduleDrawable(drawable2, runnable2, l);
        }

        public void unscheduleDrawable(Drawable drawable2, Runnable runnable2) {
            Drawable.Callback callback = this.mCallback;
            if (callback == null) return;
            callback.unscheduleDrawable(drawable2, runnable2);
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback;
            this.mCallback = null;
            return callback;
        }

        public BlockInvalidateCallback wrap(Drawable.Callback callback) {
            this.mCallback = callback;
            return this;
        }
    }

    static abstract class DrawableContainerState
    extends Drawable.ConstantState {
        boolean mAutoMirrored;
        boolean mCanConstantState;
        int mChangingConfigurations;
        boolean mCheckedConstantSize;
        boolean mCheckedConstantState;
        boolean mCheckedOpacity;
        boolean mCheckedPadding;
        boolean mCheckedStateful;
        int mChildrenChangingConfigurations;
        ColorFilter mColorFilter;
        int mConstantHeight;
        int mConstantMinimumHeight;
        int mConstantMinimumWidth;
        Rect mConstantPadding;
        boolean mConstantSize;
        int mConstantWidth;
        int mDensity = 160;
        boolean mDither;
        SparseArray<Drawable.ConstantState> mDrawableFutures;
        Drawable[] mDrawables;
        int mEnterFadeDuration;
        int mExitFadeDuration;
        boolean mHasColorFilter;
        boolean mHasTintList;
        boolean mHasTintMode;
        int mLayoutDirection;
        boolean mMutated;
        int mNumChildren;
        int mOpacity;
        final DrawableContainer mOwner;
        Resources mSourceRes;
        boolean mStateful;
        ColorStateList mTintList;
        PorterDuff.Mode mTintMode;
        boolean mVariablePadding;

        DrawableContainerState(DrawableContainerState constantState, DrawableContainer object, Resources resources) {
            int n = 0;
            this.mVariablePadding = false;
            this.mConstantSize = false;
            this.mDither = true;
            this.mEnterFadeDuration = 0;
            this.mExitFadeDuration = 0;
            this.mOwner = object;
            object = resources != null ? resources : (constantState != null ? constantState.mSourceRes : null);
            this.mSourceRes = object;
            int n2 = constantState != null ? constantState.mDensity : 0;
            this.mDensity = n2 = DrawableContainer.resolveDensity(resources, n2);
            if (constantState == null) {
                this.mDrawables = new Drawable[10];
                this.mNumChildren = 0;
                return;
            }
            this.mChangingConfigurations = constantState.mChangingConfigurations;
            this.mChildrenChangingConfigurations = constantState.mChildrenChangingConfigurations;
            this.mCheckedConstantState = true;
            this.mCanConstantState = true;
            this.mVariablePadding = constantState.mVariablePadding;
            this.mConstantSize = constantState.mConstantSize;
            this.mDither = constantState.mDither;
            this.mMutated = constantState.mMutated;
            this.mLayoutDirection = constantState.mLayoutDirection;
            this.mEnterFadeDuration = constantState.mEnterFadeDuration;
            this.mExitFadeDuration = constantState.mExitFadeDuration;
            this.mAutoMirrored = constantState.mAutoMirrored;
            this.mColorFilter = constantState.mColorFilter;
            this.mHasColorFilter = constantState.mHasColorFilter;
            this.mTintList = constantState.mTintList;
            this.mTintMode = constantState.mTintMode;
            this.mHasTintList = constantState.mHasTintList;
            this.mHasTintMode = constantState.mHasTintMode;
            if (constantState.mDensity == n2) {
                if (constantState.mCheckedPadding) {
                    this.mConstantPadding = new Rect(constantState.mConstantPadding);
                    this.mCheckedPadding = true;
                }
                if (constantState.mCheckedConstantSize) {
                    this.mConstantWidth = constantState.mConstantWidth;
                    this.mConstantHeight = constantState.mConstantHeight;
                    this.mConstantMinimumWidth = constantState.mConstantMinimumWidth;
                    this.mConstantMinimumHeight = constantState.mConstantMinimumHeight;
                    this.mCheckedConstantSize = true;
                }
            }
            if (constantState.mCheckedOpacity) {
                this.mOpacity = constantState.mOpacity;
                this.mCheckedOpacity = true;
            }
            if (constantState.mCheckedStateful) {
                this.mStateful = constantState.mStateful;
                this.mCheckedStateful = true;
            }
            object = constantState.mDrawables;
            this.mDrawables = new Drawable[((Drawable[])object).length];
            this.mNumChildren = constantState.mNumChildren;
            constantState = constantState.mDrawableFutures;
            this.mDrawableFutures = constantState != null ? constantState.clone() : new SparseArray(this.mNumChildren);
            int n3 = this.mNumChildren;
            n2 = n;
            while (n2 < n3) {
                if (object[n2] != null) {
                    constantState = object[n2].getConstantState();
                    if (constantState != null) {
                        this.mDrawableFutures.put(n2, (Object)constantState);
                    } else {
                        this.mDrawables[n2] = object[n2];
                    }
                }
                ++n2;
            }
        }

        private void createAllFutures() {
            Drawable.ConstantState constantState = this.mDrawableFutures;
            if (constantState == null) return;
            int n = constantState.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mDrawableFutures = null;
                    return;
                }
                int n3 = this.mDrawableFutures.keyAt(n2);
                constantState = (Drawable.ConstantState)this.mDrawableFutures.valueAt(n2);
                this.mDrawables[n3] = this.prepareDrawable(constantState.newDrawable(this.mSourceRes));
                ++n2;
            } while (true);
        }

        private Drawable prepareDrawable(Drawable drawable2) {
            if (Build.VERSION.SDK_INT >= 23) {
                drawable2.setLayoutDirection(this.mLayoutDirection);
            }
            drawable2 = drawable2.mutate();
            drawable2.setCallback((Drawable.Callback)this.mOwner);
            return drawable2;
        }

        public final int addChild(Drawable drawable2) {
            int n = this.mNumChildren;
            if (n >= this.mDrawables.length) {
                this.growArray(n, n + 10);
            }
            drawable2.mutate();
            drawable2.setVisible(false, true);
            drawable2.setCallback((Drawable.Callback)this.mOwner);
            this.mDrawables[n] = drawable2;
            ++this.mNumChildren;
            int n2 = this.mChildrenChangingConfigurations;
            this.mChildrenChangingConfigurations = drawable2.getChangingConfigurations() | n2;
            this.invalidateCache();
            this.mConstantPadding = null;
            this.mCheckedPadding = false;
            this.mCheckedConstantSize = false;
            this.mCheckedConstantState = false;
            return n;
        }

        final void applyTheme(Resources.Theme theme) {
            if (theme == null) return;
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.updateDensity(theme.getResources());
                    return;
                }
                if (arrdrawable[n2] != null && arrdrawable[n2].canApplyTheme()) {
                    arrdrawable[n2].applyTheme(theme);
                    this.mChildrenChangingConfigurations |= arrdrawable[n2].getChangingConfigurations();
                }
                ++n2;
            } while (true);
        }

        public boolean canApplyTheme() {
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n2 = 0;
            while (n2 < n) {
                Drawable drawable2 = arrdrawable[n2];
                if (drawable2 != null ? drawable2.canApplyTheme() : (drawable2 = (Drawable.ConstantState)this.mDrawableFutures.get(n2)) != null && drawable2.canApplyTheme()) {
                    return true;
                }
                ++n2;
            }
            return false;
        }

        public boolean canConstantState() {
            synchronized (this) {
                if (this.mCheckedConstantState) {
                    return this.mCanConstantState;
                }
                this.createAllFutures();
                this.mCheckedConstantState = true;
                int n = this.mNumChildren;
                Drawable[] arrdrawable = this.mDrawables;
                for (int i = 0; i < n; ++i) {
                    if (arrdrawable[i].getConstantState() != null) continue;
                    this.mCanConstantState = false;
                    return false;
                }
                this.mCanConstantState = true;
                return true;
            }
        }

        final void clearMutated() {
            this.mMutated = false;
        }

        protected void computeConstantSize() {
            this.mCheckedConstantSize = true;
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            this.mConstantHeight = -1;
            this.mConstantWidth = -1;
            int n2 = 0;
            this.mConstantMinimumHeight = 0;
            this.mConstantMinimumWidth = 0;
            while (n2 < n) {
                Drawable drawable2 = arrdrawable[n2];
                int n3 = drawable2.getIntrinsicWidth();
                if (n3 > this.mConstantWidth) {
                    this.mConstantWidth = n3;
                }
                if ((n3 = drawable2.getIntrinsicHeight()) > this.mConstantHeight) {
                    this.mConstantHeight = n3;
                }
                if ((n3 = drawable2.getMinimumWidth()) > this.mConstantMinimumWidth) {
                    this.mConstantMinimumWidth = n3;
                }
                if ((n3 = drawable2.getMinimumHeight()) > this.mConstantMinimumHeight) {
                    this.mConstantMinimumHeight = n3;
                }
                ++n2;
            }
        }

        final int getCapacity() {
            return this.mDrawables.length;
        }

        public int getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
        }

        public final Drawable getChild(int n) {
            Drawable drawable2 = this.mDrawables[n];
            if (drawable2 != null) {
                return drawable2;
            }
            drawable2 = this.mDrawableFutures;
            if (drawable2 == null) return null;
            int n2 = drawable2.indexOfKey(n);
            if (n2 < 0) return null;
            this.mDrawables[n] = drawable2 = this.prepareDrawable(((Drawable.ConstantState)this.mDrawableFutures.valueAt(n2)).newDrawable(this.mSourceRes));
            this.mDrawableFutures.removeAt(n2);
            if (this.mDrawableFutures.size() != 0) return drawable2;
            this.mDrawableFutures = null;
            return drawable2;
        }

        public final int getChildCount() {
            return this.mNumChildren;
        }

        public final int getConstantHeight() {
            if (this.mCheckedConstantSize) return this.mConstantHeight;
            this.computeConstantSize();
            return this.mConstantHeight;
        }

        public final int getConstantMinimumHeight() {
            if (this.mCheckedConstantSize) return this.mConstantMinimumHeight;
            this.computeConstantSize();
            return this.mConstantMinimumHeight;
        }

        public final int getConstantMinimumWidth() {
            if (this.mCheckedConstantSize) return this.mConstantMinimumWidth;
            this.computeConstantSize();
            return this.mConstantMinimumWidth;
        }

        public final Rect getConstantPadding() {
            boolean bl = this.mVariablePadding;
            Rect rect = null;
            if (bl) {
                return null;
            }
            if (this.mConstantPadding != null) return this.mConstantPadding;
            if (this.mCheckedPadding) {
                return this.mConstantPadding;
            }
            this.createAllFutures();
            Rect rect2 = new Rect();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mCheckedPadding = true;
                    this.mConstantPadding = rect;
                    return rect;
                }
                Rect rect3 = rect;
                if (arrdrawable[n2].getPadding(rect2)) {
                    Rect rect4 = rect;
                    if (rect == null) {
                        rect4 = new Rect(0, 0, 0, 0);
                    }
                    if (rect2.left > rect4.left) {
                        rect4.left = rect2.left;
                    }
                    if (rect2.top > rect4.top) {
                        rect4.top = rect2.top;
                    }
                    if (rect2.right > rect4.right) {
                        rect4.right = rect2.right;
                    }
                    rect3 = rect4;
                    if (rect2.bottom > rect4.bottom) {
                        rect4.bottom = rect2.bottom;
                        rect3 = rect4;
                    }
                }
                ++n2;
                rect = rect3;
            } while (true);
        }

        public final int getConstantWidth() {
            if (this.mCheckedConstantSize) return this.mConstantWidth;
            this.computeConstantSize();
            return this.mConstantWidth;
        }

        public final int getEnterFadeDuration() {
            return this.mEnterFadeDuration;
        }

        public final int getExitFadeDuration() {
            return this.mExitFadeDuration;
        }

        public final int getOpacity() {
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n2 = n > 0 ? arrdrawable[0].getOpacity() : -2;
            int n3 = 1;
            do {
                if (n3 >= n) {
                    this.mOpacity = n2;
                    this.mCheckedOpacity = true;
                    return n2;
                }
                n2 = Drawable.resolveOpacity((int)n2, (int)arrdrawable[n3].getOpacity());
                ++n3;
            } while (true);
        }

        public void growArray(int n, int n2) {
            Drawable[] arrdrawable = new Drawable[n2];
            System.arraycopy(this.mDrawables, 0, arrdrawable, 0, n);
            this.mDrawables = arrdrawable;
        }

        void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }

        public final boolean isConstantSize() {
            return this.mConstantSize;
        }

        public final boolean isStateful() {
            boolean bl;
            if (this.mCheckedStateful) {
                return this.mStateful;
            }
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            boolean bl2 = false;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                if (arrdrawable[n2].isStateful()) {
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            this.mStateful = bl;
            this.mCheckedStateful = true;
            return bl;
        }

        void mutate() {
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mMutated = true;
                    return;
                }
                if (arrdrawable[n2] != null) {
                    arrdrawable[n2].mutate();
                }
                ++n2;
            } while (true);
        }

        public final void setConstantSize(boolean bl) {
            this.mConstantSize = bl;
        }

        public final void setEnterFadeDuration(int n) {
            this.mEnterFadeDuration = n;
        }

        public final void setExitFadeDuration(int n) {
            this.mExitFadeDuration = n;
        }

        final boolean setLayoutDirection(int n, int n2) {
            int n3 = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n4 = 0;
            boolean bl = false;
            do {
                if (n4 >= n3) {
                    this.mLayoutDirection = n;
                    return bl;
                }
                boolean bl2 = bl;
                if (arrdrawable[n4] != null) {
                    boolean bl3 = Build.VERSION.SDK_INT >= 23 ? arrdrawable[n4].setLayoutDirection(n) : false;
                    bl2 = bl;
                    if (n4 == n2) {
                        bl2 = bl3;
                    }
                }
                ++n4;
                bl = bl2;
            } while (true);
        }

        public final void setVariablePadding(boolean bl) {
            this.mVariablePadding = bl;
        }

        final void updateDensity(Resources resources) {
            if (resources == null) return;
            this.mSourceRes = resources;
            int n = DrawableContainer.resolveDensity(resources, this.mDensity);
            int n2 = this.mDensity;
            this.mDensity = n;
            if (n2 == n) return;
            this.mCheckedConstantSize = false;
            this.mCheckedPadding = false;
        }
    }

}

