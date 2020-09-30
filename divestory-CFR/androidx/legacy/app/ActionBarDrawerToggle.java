/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.Window
 *  android.widget.ImageView
 */
package androidx.legacy.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import java.lang.reflect.Method;

@Deprecated
public class ActionBarDrawerToggle
implements DrawerLayout.DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final String TAG = "ActionBarDrawerToggle";
    private static final int[] THEME_ATTRS = new int[]{16843531};
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled = true;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private SetIndicatorInfo mSetIndicatorInfo;
    private SlideDrawable mSlider;

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int n, int n2, int n3) {
        this(activity, drawerLayout, ActionBarDrawerToggle.assumeMaterial((Context)activity) ^ true, n, n2, n3);
    }

    public ActionBarDrawerToggle(Activity object, DrawerLayout drawerLayout, boolean bl, int n, int n2, int n3) {
        this.mActivity = object;
        this.mActivityImpl = object instanceof DelegateProvider ? ((DelegateProvider)object).getDrawerToggleDelegate() : null;
        this.mDrawerLayout = drawerLayout;
        this.mDrawerImageResource = n;
        this.mOpenDrawerContentDescRes = n2;
        this.mCloseDrawerContentDescRes = n3;
        this.mHomeAsUpIndicator = this.getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable((Context)object, n);
        object = new SlideDrawable(this.mDrawerImage);
        this.mSlider = object;
        float f = bl ? 0.33333334f : 0.0f;
        ((SlideDrawable)((Object)object)).setOffset(f);
    }

    private static boolean assumeMaterial(Context context) {
        if (context.getApplicationInfo().targetSdkVersion < 21) return false;
        if (Build.VERSION.SDK_INT < 21) return false;
        return true;
    }

    private Drawable getThemeUpIndicator() {
        Delegate delegate = this.mActivityImpl;
        if (delegate != null) {
            return delegate.getThemeUpIndicator();
        }
        if (Build.VERSION.SDK_INT < 18) {
            delegate = this.mActivity.obtainStyledAttributes(THEME_ATTRS);
            Drawable drawable2 = delegate.getDrawable(0);
            delegate.recycle();
            return drawable2;
        }
        delegate = this.mActivity.getActionBar();
        delegate = delegate != null ? delegate.getThemedContext() : this.mActivity;
        delegate = delegate.obtainStyledAttributes(null, THEME_ATTRS, 16843470, 0);
        Drawable drawable3 = delegate.getDrawable(0);
        delegate.recycle();
        return drawable3;
    }

    private void setActionBarDescription(int n) {
        Delegate delegate = this.mActivityImpl;
        if (delegate != null) {
            delegate.setActionBarDescription(n);
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            delegate = this.mActivity.getActionBar();
            if (delegate == null) return;
            delegate.setHomeActionContentDescription(n);
            return;
        }
        if (this.mSetIndicatorInfo == null) {
            this.mSetIndicatorInfo = new SetIndicatorInfo(this.mActivity);
        }
        if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator == null) return;
        try {
            delegate = this.mActivity.getActionBar();
            this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(delegate, n);
            delegate.setSubtitle(delegate.getSubtitle());
            return;
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Couldn't set content description via JB-MR2 API", (Throwable)exception);
        }
    }

    private void setActionBarUpIndicator(Drawable drawable2, int n) {
        Delegate delegate = this.mActivityImpl;
        if (delegate != null) {
            delegate.setActionBarUpIndicator(drawable2, n);
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            delegate = this.mActivity.getActionBar();
            if (delegate == null) return;
            delegate.setHomeAsUpIndicator(drawable2);
            delegate.setHomeActionContentDescription(n);
            return;
        }
        if (this.mSetIndicatorInfo == null) {
            this.mSetIndicatorInfo = new SetIndicatorInfo(this.mActivity);
        }
        if (this.mSetIndicatorInfo.mSetHomeAsUpIndicator != null) {
            try {
                delegate = this.mActivity.getActionBar();
                this.mSetIndicatorInfo.mSetHomeAsUpIndicator.invoke(delegate, new Object[]{drawable2});
                this.mSetIndicatorInfo.mSetHomeActionContentDescription.invoke(delegate, n);
                return;
            }
            catch (Exception exception) {
                Log.w((String)TAG, (String)"Couldn't set home-as-up indicator via JB-MR2 API", (Throwable)exception);
                return;
            }
        }
        if (this.mSetIndicatorInfo.mUpIndicatorView != null) {
            this.mSetIndicatorInfo.mUpIndicatorView.setImageDrawable(drawable2);
            return;
        }
        Log.w((String)TAG, (String)"Couldn't set home-as-up indicator");
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = this.getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable((Context)this.mActivity, this.mDrawerImageResource);
        this.syncState();
    }

    @Override
    public void onDrawerClosed(View view) {
        this.mSlider.setPosition(0.0f);
        if (!this.mDrawerIndicatorEnabled) return;
        this.setActionBarDescription(this.mOpenDrawerContentDescRes);
    }

    @Override
    public void onDrawerOpened(View view) {
        this.mSlider.setPosition(1.0f);
        if (!this.mDrawerIndicatorEnabled) return;
        this.setActionBarDescription(this.mCloseDrawerContentDescRes);
    }

    @Override
    public void onDrawerSlide(View view, float f) {
        float f2 = this.mSlider.getPosition();
        f = f > 0.5f ? Math.max(f2, Math.max(0.0f, f - 0.5f) * 2.0f) : Math.min(f2, f * 2.0f);
        this.mSlider.setPosition(f);
    }

    @Override
    public void onDrawerStateChanged(int n) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null) return false;
        if (menuItem.getItemId() != 16908332) return false;
        if (!this.mDrawerIndicatorEnabled) return false;
        if (this.mDrawerLayout.isDrawerVisible(8388611)) {
            this.mDrawerLayout.closeDrawer(8388611);
            return true;
        }
        this.mDrawerLayout.openDrawer(8388611);
        return true;
    }

    public void setDrawerIndicatorEnabled(boolean bl) {
        if (bl == this.mDrawerIndicatorEnabled) return;
        if (bl) {
            SlideDrawable slideDrawable = this.mSlider;
            int n = this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
            this.setActionBarUpIndicator((Drawable)slideDrawable, n);
        } else {
            this.setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
        this.mDrawerIndicatorEnabled = bl;
    }

    public void setHomeAsUpIndicator(int n) {
        Drawable drawable2 = n != 0 ? ContextCompat.getDrawable((Context)this.mActivity, n) : null;
        this.setHomeAsUpIndicator(drawable2);
    }

    public void setHomeAsUpIndicator(Drawable drawable2) {
        if (drawable2 == null) {
            this.mHomeAsUpIndicator = this.getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = drawable2;
            this.mHasCustomUpIndicator = true;
        }
        if (this.mDrawerIndicatorEnabled) return;
        this.setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
    }

    public void syncState() {
        if (this.mDrawerLayout.isDrawerOpen(8388611)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (!this.mDrawerIndicatorEnabled) return;
        SlideDrawable slideDrawable = this.mSlider;
        int n = this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
        this.setActionBarUpIndicator((Drawable)slideDrawable, n);
    }

    @Deprecated
    public static interface Delegate {
        public Drawable getThemeUpIndicator();

        public void setActionBarDescription(int var1);

        public void setActionBarUpIndicator(Drawable var1, int var2);
    }

    @Deprecated
    public static interface DelegateProvider {
        public Delegate getDrawerToggleDelegate();
    }

    private static class SetIndicatorInfo {
        Method mSetHomeActionContentDescription;
        Method mSetHomeAsUpIndicator;
        ImageView mUpIndicatorView;

        SetIndicatorInfo(Activity activity) {
            try {
                this.mSetHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.mSetHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                activity = activity.findViewById(16908332);
                if (activity == null) {
                    return;
                }
                if ((activity = (ViewGroup)activity.getParent()).getChildCount() != 2) {
                    return;
                }
                View view = activity.getChildAt(0);
                View view2 = activity.getChildAt(1);
                activity = view;
                if (view.getId() == 16908332) {
                    activity = view2;
                }
                if (!(activity instanceof ImageView)) return;
                this.mUpIndicatorView = (ImageView)activity;
                return;
            }
        }
    }

    private class SlideDrawable
    extends InsetDrawable
    implements Drawable.Callback {
        private final boolean mHasMirroring;
        private float mOffset;
        private float mPosition;
        private final Rect mTmpRect;

        SlideDrawable(Drawable drawable2) {
            boolean bl = false;
            super(drawable2, 0);
            if (Build.VERSION.SDK_INT > 18) {
                bl = true;
            }
            this.mHasMirroring = bl;
            this.mTmpRect = new Rect();
        }

        public void draw(Canvas canvas) {
            this.copyBounds(this.mTmpRect);
            canvas.save();
            int n = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.mActivity.getWindow().getDecorView());
            int n2 = 1;
            n = n == 1 ? 1 : 0;
            if (n != 0) {
                n2 = -1;
            }
            int n3 = this.mTmpRect.width();
            float f = -this.mOffset;
            float f2 = n3;
            canvas.translate(f * f2 * this.mPosition * (float)n2, 0.0f);
            if (n != 0 && !this.mHasMirroring) {
                canvas.translate(f2, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            super.draw(canvas);
            canvas.restore();
        }

        public float getPosition() {
            return this.mPosition;
        }

        public void setOffset(float f) {
            this.mOffset = f;
            this.invalidateSelf();
        }

        public void setPosition(float f) {
            this.mPosition = f;
            this.invalidateSelf();
        }
    }

}

