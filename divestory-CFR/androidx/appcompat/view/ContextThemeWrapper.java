/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 */
package androidx.appcompat.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import androidx.appcompat.R;

public class ContextThemeWrapper
extends ContextWrapper {
    private LayoutInflater mInflater;
    private Configuration mOverrideConfiguration;
    private Resources mResources;
    private Resources.Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper() {
        super(null);
    }

    public ContextThemeWrapper(Context context, int n) {
        super(context);
        this.mThemeResource = n;
    }

    public ContextThemeWrapper(Context context, Resources.Theme theme) {
        super(context);
        this.mTheme = theme;
    }

    private Resources getResourcesInternal() {
        if (this.mResources != null) return this.mResources;
        if (this.mOverrideConfiguration == null) {
            this.mResources = super.getResources();
            return this.mResources;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            this.mResources = this.createConfigurationContext(this.mOverrideConfiguration).getResources();
            return this.mResources;
        }
        Resources resources = super.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.updateFrom(this.mOverrideConfiguration);
        this.mResources = new Resources(resources.getAssets(), resources.getDisplayMetrics(), configuration);
        return this.mResources;
    }

    private void initializeTheme() {
        boolean bl = this.mTheme == null;
        if (bl) {
            this.mTheme = this.getResources().newTheme();
            Resources.Theme theme = this.getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        this.onApplyThemeResource(this.mTheme, this.mThemeResource, bl);
    }

    public void applyOverrideConfiguration(Configuration configuration) {
        if (this.mResources != null) throw new IllegalStateException("getResources() or getAssets() has already been called");
        if (this.mOverrideConfiguration != null) throw new IllegalStateException("Override configuration has already been set");
        this.mOverrideConfiguration = new Configuration(configuration);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public AssetManager getAssets() {
        return this.getResources().getAssets();
    }

    public Resources getResources() {
        return this.getResourcesInternal();
    }

    public Object getSystemService(String string2) {
        if (!"layout_inflater".equals(string2)) return this.getBaseContext().getSystemService(string2);
        if (this.mInflater != null) return this.mInflater;
        this.mInflater = LayoutInflater.from((Context)this.getBaseContext()).cloneInContext((Context)this);
        return this.mInflater;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.mTheme;
        if (theme != null) {
            return theme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = R.style.Theme_AppCompat_Light;
        }
        this.initializeTheme();
        return this.mTheme;
    }

    public int getThemeResId() {
        return this.mThemeResource;
    }

    protected void onApplyThemeResource(Resources.Theme theme, int n, boolean bl) {
        theme.applyStyle(n, true);
    }

    public void setTheme(int n) {
        if (this.mThemeResource == n) return;
        this.mThemeResource = n;
        this.initializeTheme();
    }
}

