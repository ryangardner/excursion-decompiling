/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.UiModeManager
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.media.AudioManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.LocaleList
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.PowerManager
 *  android.text.TextUtils
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.ContextThemeWrapper
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.KeyboardShortcutGroup
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow
 *  android.widget.TextView
 */
package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.app.ResourcesFlusher;
import androidx.appcompat.app.ToolbarActionBar;
import androidx.appcompat.app.TwilightManager;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;

class AppCompatDelegateImpl
extends AppCompatDelegate
implements MenuBuilder.Callback,
LayoutInflater.Factory2 {
    static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
    private static final boolean IS_PRE_LOLLIPOP;
    private static final boolean sCanApplyOverrideConfiguration;
    private static final boolean sCanReturnDifferentContext;
    private static boolean sInstalledExceptionHandler;
    private static final SimpleArrayMap<String, Integer> sLocalNightModes;
    private static final int[] sWindowBackgroundStyleable;
    ActionBar mActionBar;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private boolean mActivityHandlesUiMode;
    private boolean mActivityHandlesUiModeChecked;
    final AppCompatCallback mAppCompatCallback;
    private AppCompatViewInflater mAppCompatViewInflater;
    private AppCompatWindowCallback mAppCompatWindowCallback;
    private AutoNightModeManager mAutoBatteryNightModeManager;
    private AutoNightModeManager mAutoTimeNightModeManager;
    private boolean mBaseContextAttached;
    private boolean mClosingActionMenu;
    final Context mContext;
    private boolean mCreated;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim = null;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private boolean mHandleNativeActionModes = true;
    boolean mHasActionBar;
    final Object mHost;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable(){

        @Override
        public void run() {
            if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 1) != 0) {
                AppCompatDelegateImpl.this.doInvalidatePanelMenu(0);
            }
            if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 4096) != 0) {
                AppCompatDelegateImpl.this.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImpl.this.mInvalidatePanelMenuPosted = false;
            AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures = 0;
        }
    };
    boolean mIsDestroyed;
    boolean mIsFloating;
    private int mLocalNightMode = -100;
    private boolean mLongPressBackDown;
    MenuInflater mMenuInflater;
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private boolean mStarted;
    private View mStatusGuard;
    ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private int mThemeResId;
    private CharSequence mTitle;
    private TextView mTitleView;
    Window mWindow;
    boolean mWindowNoTitle;

    static {
        sLocalNightModes = new SimpleArrayMap();
        int n = Build.VERSION.SDK_INT;
        boolean bl = false;
        boolean bl2 = n < 21;
        IS_PRE_LOLLIPOP = bl2;
        sWindowBackgroundStyleable = new int[]{16842836};
        sCanReturnDifferentContext = "robolectric".equals(Build.FINGERPRINT) ^ true;
        bl2 = bl;
        if (Build.VERSION.SDK_INT >= 17) {
            bl2 = true;
        }
        sCanApplyOverrideConfiguration = bl2;
        if (!IS_PRE_LOLLIPOP) return;
        if (sInstalledExceptionHandler) return;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

            private boolean shouldWrapException(Throwable object) {
                boolean bl;
                boolean bl2 = object instanceof Resources.NotFoundException;
                boolean bl3 = bl = false;
                if (!bl2) return bl3;
                object = ((Throwable)object).getMessage();
                bl3 = bl;
                if (object == null) return bl3;
                if (((String)object).contains("drawable")) return true;
                bl3 = bl;
                if (!((String)object).contains("Drawable")) return bl3;
                return true;
            }

            @Override
            public void uncaughtException(Thread thread2, Throwable throwable) {
                if (this.shouldWrapException(throwable)) {
                    Object object = new StringBuilder();
                    ((StringBuilder)object).append(throwable.getMessage());
                    ((StringBuilder)object).append(AppCompatDelegateImpl.EXCEPTION_HANDLER_MESSAGE_SUFFIX);
                    object = new Resources.NotFoundException(((StringBuilder)object).toString());
                    ((Throwable)object).initCause(throwable.getCause());
                    ((Throwable)object).setStackTrace(throwable.getStackTrace());
                    UncaughtExceptionHandler.this.uncaughtException(thread2, (Throwable)object);
                    return;
                }
                UncaughtExceptionHandler.this.uncaughtException(thread2, throwable);
            }
        });
        sInstalledExceptionHandler = true;
    }

    AppCompatDelegateImpl(Activity activity, AppCompatCallback appCompatCallback) {
        this((Context)activity, null, appCompatCallback, (Object)activity);
    }

    AppCompatDelegateImpl(Dialog dialog, AppCompatCallback appCompatCallback) {
        this(dialog.getContext(), dialog.getWindow(), appCompatCallback, (Object)dialog);
    }

    AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback appCompatCallback) {
        this(context, null, appCompatCallback, (Object)activity);
    }

    AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this(context, window, appCompatCallback, (Object)context);
    }

    private AppCompatDelegateImpl(Context object, Window window, AppCompatCallback appCompatCallback, Object object2) {
        this.mContext = object;
        this.mAppCompatCallback = appCompatCallback;
        this.mHost = object2;
        if (this.mLocalNightMode == -100 && object2 instanceof Dialog && (object = this.tryUnwrapContext()) != null) {
            this.mLocalNightMode = ((AppCompatActivity)object).getDelegate().getLocalNightMode();
        }
        if (this.mLocalNightMode == -100 && (object = sLocalNightModes.get(this.mHost.getClass().getName())) != null) {
            this.mLocalNightMode = (Integer)object;
            sLocalNightModes.remove(this.mHost.getClass().getName());
        }
        if (window != null) {
            this.attachToWindow(window);
        }
        AppCompatDrawableManager.preload();
    }

    private boolean applyDayNight(boolean bl) {
        AutoNightModeManager autoNightModeManager;
        if (this.mIsDestroyed) {
            return false;
        }
        int n = this.calculateNightMode();
        bl = this.updateForNightMode(this.mapNightMode(this.mContext, n), bl);
        if (n == 0) {
            this.getAutoTimeNightModeManager(this.mContext).setup();
        } else {
            autoNightModeManager = this.mAutoTimeNightModeManager;
            if (autoNightModeManager != null) {
                autoNightModeManager.cleanup();
            }
        }
        if (n == 3) {
            this.getAutoBatteryNightModeManager(this.mContext).setup();
            return bl;
        }
        autoNightModeManager = this.mAutoBatteryNightModeManager;
        if (autoNightModeManager == null) return bl;
        autoNightModeManager.cleanup();
        return bl;
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
        View view = this.mWindow.getDecorView();
        contentFrameLayout.setDecorPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        view = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
        view.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        view.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (view.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            view.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        view.recycle();
        contentFrameLayout.requestLayout();
    }

    private void attachToWindow(Window window) {
        if (this.mWindow != null) throw new IllegalStateException("AppCompat has already installed itself into the Window");
        Object object = window.getCallback();
        if (object instanceof AppCompatWindowCallback) throw new IllegalStateException("AppCompat has already installed itself into the Window");
        object = new AppCompatWindowCallback((Window.Callback)object);
        this.mAppCompatWindowCallback = object;
        window.setCallback((Window.Callback)object);
        object = TintTypedArray.obtainStyledAttributes(this.mContext, null, sWindowBackgroundStyleable);
        Drawable drawable2 = ((TintTypedArray)object).getDrawableIfKnown(0);
        if (drawable2 != null) {
            window.setBackgroundDrawable(drawable2);
        }
        ((TintTypedArray)object).recycle();
        this.mWindow = window;
    }

    private int calculateNightMode() {
        int n = this.mLocalNightMode;
        if (n == -100) return AppCompatDelegateImpl.getDefaultNightMode();
        return n;
    }

    private void cleanupAutoManagers() {
        AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.cleanup();
        }
        if ((autoNightModeManager = this.mAutoBatteryNightModeManager) == null) return;
        autoNightModeManager.cleanup();
    }

    private Configuration createOverrideConfigurationForDayNight(Context context, int n, Configuration configuration) {
        n = n != 1 ? (n != 2 ? context.getApplicationContext().getResources().getConfiguration().uiMode & 48 : 32) : 16;
        context = new Configuration();
        context.fontScale = 0.0f;
        if (configuration != null) {
            context.setTo(configuration);
        }
        context.uiMode = n | context.uiMode & -49;
        return context;
    }

    private ViewGroup createSubDecor() {
        ViewGroup viewGroup;
        Object object = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!object.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            object.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (object.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
            this.requestWindowFeature(1);
        } else if (object.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
            this.requestWindowFeature(108);
        }
        if (object.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            this.requestWindowFeature(109);
        }
        if (object.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            this.requestWindowFeature(10);
        }
        this.mIsFloating = object.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
        object.recycle();
        this.ensureWindow();
        this.mWindow.getDecorView();
        object = LayoutInflater.from((Context)this.mContext);
        if (!this.mWindowNoTitle) {
            if (this.mIsFloating) {
                object = (ViewGroup)object.inflate(R.layout.abc_dialog_title_material, null);
                this.mOverlayActionBar = false;
                this.mHasActionBar = false;
            } else if (this.mHasActionBar) {
                object = new TypedValue();
                this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, (TypedValue)object, true);
                object = object.resourceId != 0 ? new ContextThemeWrapper(this.mContext, object.resourceId) : this.mContext;
                viewGroup = (ViewGroup)LayoutInflater.from((Context)object).inflate(R.layout.abc_screen_toolbar, null);
                this.mDecorContentParent = object = (DecorContentParent)viewGroup.findViewById(R.id.decor_content_parent);
                object.setWindowCallback(this.getWindowCallback());
                if (this.mOverlayActionBar) {
                    this.mDecorContentParent.initFeature(109);
                }
                if (this.mFeatureProgress) {
                    this.mDecorContentParent.initFeature(2);
                }
                object = viewGroup;
                if (this.mFeatureIndeterminateProgress) {
                    this.mDecorContentParent.initFeature(5);
                    object = viewGroup;
                }
            } else {
                object = null;
            }
        } else {
            object = this.mOverlayActionMode ? (ViewGroup)object.inflate(R.layout.abc_screen_simple_overlay_action_mode, null) : (ViewGroup)object.inflate(R.layout.abc_screen_simple, null);
        }
        if (object == null) {
            object = new StringBuilder();
            object.append("AppCompat does not support the current theme features: { windowActionBar: ");
            object.append(this.mHasActionBar);
            object.append(", windowActionBarOverlay: ");
            object.append(this.mOverlayActionBar);
            object.append(", android:windowIsFloating: ");
            object.append(this.mIsFloating);
            object.append(", windowActionModeOverlay: ");
            object.append(this.mOverlayActionMode);
            object.append(", windowNoTitle: ");
            object.append(this.mWindowNoTitle);
            object.append(" }");
            throw new IllegalArgumentException(object.toString());
        }
        if (Build.VERSION.SDK_INT >= 21) {
            ViewCompat.setOnApplyWindowInsetsListener((View)object, new OnApplyWindowInsetsListener(){

                @Override
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    int n = windowInsetsCompat.getSystemWindowInsetTop();
                    int n2 = AppCompatDelegateImpl.this.updateStatusGuard(windowInsetsCompat, null);
                    WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
                    if (n == n2) return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat2);
                    windowInsetsCompat2 = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), n2, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                    return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat2);
                }
            });
        } else if (object instanceof FitWindowsViewGroup) {
            ((FitWindowsViewGroup)object).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener(){

                @Override
                public void onFitSystemWindows(Rect rect) {
                    rect.top = AppCompatDelegateImpl.this.updateStatusGuard(null, rect);
                }
            });
        }
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView)object.findViewById(R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows((View)object);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)object.findViewById(R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup)this.mWindow.findViewById(16908290);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                viewGroup = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView((View)viewGroup);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(16908290);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout)viewGroup2).setForeground(null);
            }
        }
        this.mWindow.setContentView((View)object);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener(){

            @Override
            public void onAttachedFromWindow() {
            }

            @Override
            public void onDetachedFromWindow() {
                AppCompatDelegateImpl.this.dismissPopups();
            }
        });
        return object;
    }

    private void ensureSubDecor() {
        if (this.mSubDecorInstalled) return;
        this.mSubDecor = this.createSubDecor();
        Object object = this.getTitle();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            DecorContentParent decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null) {
                decorContentParent.setWindowTitle((CharSequence)object);
            } else if (this.peekSupportActionBar() != null) {
                this.peekSupportActionBar().setWindowTitle((CharSequence)object);
            } else {
                decorContentParent = this.mTitleView;
                if (decorContentParent != null) {
                    decorContentParent.setText((CharSequence)object);
                }
            }
        }
        this.applyFixedSizeWindow();
        this.onSubDecorInstalled(this.mSubDecor);
        this.mSubDecorInstalled = true;
        object = this.getPanelState(0, false);
        if (this.mIsDestroyed) return;
        if (object != null) {
            if (((PanelFeatureState)object).menu != null) return;
        }
        this.invalidatePanelMenu(108);
    }

    private void ensureWindow() {
        Object object;
        if (this.mWindow == null && (object = this.mHost) instanceof Activity) {
            this.attachToWindow(((Activity)object).getWindow());
        }
        if (this.mWindow == null) throw new IllegalStateException("We have not been given a Window");
    }

    private static Configuration generateConfigDelta(Configuration configuration, Configuration configuration2) {
        Configuration configuration3 = new Configuration();
        configuration3.fontScale = 0.0f;
        if (configuration2 == null) return configuration3;
        if (configuration.diff(configuration2) == 0) {
            return configuration3;
        }
        if (configuration.fontScale != configuration2.fontScale) {
            configuration3.fontScale = configuration2.fontScale;
        }
        if (configuration.mcc != configuration2.mcc) {
            configuration3.mcc = configuration2.mcc;
        }
        if (configuration.mnc != configuration2.mnc) {
            configuration3.mnc = configuration2.mnc;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            ConfigurationImplApi24.generateConfigDelta_locale(configuration, configuration2, configuration3);
        } else if (!ObjectsCompat.equals(configuration.locale, configuration2.locale)) {
            configuration3.locale = configuration2.locale;
        }
        if (configuration.touchscreen != configuration2.touchscreen) {
            configuration3.touchscreen = configuration2.touchscreen;
        }
        if (configuration.keyboard != configuration2.keyboard) {
            configuration3.keyboard = configuration2.keyboard;
        }
        if (configuration.keyboardHidden != configuration2.keyboardHidden) {
            configuration3.keyboardHidden = configuration2.keyboardHidden;
        }
        if (configuration.navigation != configuration2.navigation) {
            configuration3.navigation = configuration2.navigation;
        }
        if (configuration.navigationHidden != configuration2.navigationHidden) {
            configuration3.navigationHidden = configuration2.navigationHidden;
        }
        if (configuration.orientation != configuration2.orientation) {
            configuration3.orientation = configuration2.orientation;
        }
        if ((configuration.screenLayout & 15) != (configuration2.screenLayout & 15)) {
            configuration3.screenLayout |= configuration2.screenLayout & 15;
        }
        if ((configuration.screenLayout & 192) != (configuration2.screenLayout & 192)) {
            configuration3.screenLayout |= configuration2.screenLayout & 192;
        }
        if ((configuration.screenLayout & 48) != (configuration2.screenLayout & 48)) {
            configuration3.screenLayout |= configuration2.screenLayout & 48;
        }
        if ((configuration.screenLayout & 768) != (configuration2.screenLayout & 768)) {
            configuration3.screenLayout |= configuration2.screenLayout & 768;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            ConfigurationImplApi26.generateConfigDelta_colorMode(configuration, configuration2, configuration3);
        }
        if ((configuration.uiMode & 15) != (configuration2.uiMode & 15)) {
            configuration3.uiMode |= configuration2.uiMode & 15;
        }
        if ((configuration.uiMode & 48) != (configuration2.uiMode & 48)) {
            configuration3.uiMode |= configuration2.uiMode & 48;
        }
        if (configuration.screenWidthDp != configuration2.screenWidthDp) {
            configuration3.screenWidthDp = configuration2.screenWidthDp;
        }
        if (configuration.screenHeightDp != configuration2.screenHeightDp) {
            configuration3.screenHeightDp = configuration2.screenHeightDp;
        }
        if (configuration.smallestScreenWidthDp != configuration2.smallestScreenWidthDp) {
            configuration3.smallestScreenWidthDp = configuration2.smallestScreenWidthDp;
        }
        if (Build.VERSION.SDK_INT < 17) return configuration3;
        ConfigurationImplApi17.generateConfigDelta_densityDpi(configuration, configuration2, configuration3);
        return configuration3;
    }

    private AutoNightModeManager getAutoBatteryNightModeManager(Context context) {
        if (this.mAutoBatteryNightModeManager != null) return this.mAutoBatteryNightModeManager;
        this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
        return this.mAutoBatteryNightModeManager;
    }

    private AutoNightModeManager getAutoTimeNightModeManager(Context context) {
        if (this.mAutoTimeNightModeManager != null) return this.mAutoTimeNightModeManager;
        this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(context));
        return this.mAutoTimeNightModeManager;
    }

    private void initWindowDecorActionBar() {
        this.ensureSubDecor();
        if (!this.mHasActionBar) return;
        if (this.mActionBar != null) {
            return;
        }
        Object object = this.mHost;
        if (object instanceof Activity) {
            this.mActionBar = new WindowDecorActionBar((Activity)this.mHost, this.mOverlayActionBar);
        } else if (object instanceof Dialog) {
            this.mActionBar = new WindowDecorActionBar((Dialog)this.mHost);
        }
        object = this.mActionBar;
        if (object == null) return;
        ((ActionBar)object).setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
    }

    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        View view = panelFeatureState.createdPanelView;
        boolean bl = true;
        if (view != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView;
            return true;
        }
        if (panelFeatureState.menu == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        panelFeatureState.shownPanelView = (View)panelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback);
        if (panelFeatureState.shownPanelView == null) return false;
        return bl;
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.setStyle(this.getActionBarThemedContext());
        panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
        panelFeatureState.gravity = 81;
        return true;
    }

    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Object object;
        block10 : {
            Context context;
            block9 : {
                context = this.mContext;
                if (panelFeatureState.featureId == 0) break block9;
                object = context;
                if (panelFeatureState.featureId != 108) break block10;
            }
            object = context;
            if (this.mDecorContentParent != null) {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = context.getTheme();
                theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                object = null;
                if (typedValue.resourceId != 0) {
                    object = context.getResources().newTheme();
                    object.setTo(theme);
                    object.applyStyle(typedValue.resourceId, true);
                    object.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                } else {
                    theme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                }
                Object object2 = object;
                if (typedValue.resourceId != 0) {
                    object2 = object;
                    if (object == null) {
                        object2 = context.getResources().newTheme();
                        object2.setTo(theme);
                    }
                    object2.applyStyle(typedValue.resourceId, true);
                }
                object = context;
                if (object2 != null) {
                    object = new ContextThemeWrapper(context, 0);
                    object.getTheme().setTo((Resources.Theme)object2);
                }
            }
        }
        object = new MenuBuilder((Context)object);
        object.setCallback(this);
        panelFeatureState.setMenu((MenuBuilder)object);
        return true;
    }

    private void invalidatePanelMenu(int n) {
        this.mInvalidatePanelMenuFeatures = 1 << n | this.mInvalidatePanelMenuFeatures;
        if (this.mInvalidatePanelMenuPosted) return;
        ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
        this.mInvalidatePanelMenuPosted = true;
    }

    private boolean isActivityManifestHandlingUiMode() {
        if (!this.mActivityHandlesUiModeChecked && this.mHost instanceof Activity) {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            try {
                int n = Build.VERSION.SDK_INT >= 29 ? 269221888 : (Build.VERSION.SDK_INT >= 24 ? 786432 : 0);
                ComponentName componentName = new ComponentName(this.mContext, this.mHost.getClass());
                packageManager = packageManager.getActivityInfo(componentName, n);
                boolean bl = packageManager != null && (packageManager.configChanges & 512) != 0;
                this.mActivityHandlesUiMode = bl;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.d((String)"AppCompatDelegate", (String)"Exception while getting ActivityInfo", (Throwable)nameNotFoundException);
                this.mActivityHandlesUiMode = false;
            }
        }
        this.mActivityHandlesUiModeChecked = true;
        return this.mActivityHandlesUiMode;
    }

    private boolean onKeyDownPanel(int n, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) return false;
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (panelFeatureState.isOpen) return false;
        return this.preparePanel(panelFeatureState, keyEvent);
    }

    /*
     * Unable to fully structure code
     */
    private boolean onKeyUpPanel(int var1_1, KeyEvent var2_2) {
        block8 : {
            block9 : {
                block6 : {
                    block7 : {
                        if (this.mActionMode != null) {
                            return false;
                        }
                        var3_3 = true;
                        var4_4 = this.getPanelState(var1_1, true);
                        if (var1_1 != 0 || (var5_5 = this.mDecorContentParent) == null || !var5_5.canShowOverflowMenu() || ViewConfiguration.get((Context)this.mContext).hasPermanentMenuKey()) break block6;
                        if (this.mDecorContentParent.isOverflowMenuShowing()) break block7;
                        if (this.mIsDestroyed || !this.preparePanel(var4_4, var2_2)) ** GOTO lbl-1000
                        var6_6 = this.mDecorContentParent.showOverflowMenu();
                        break block8;
                    }
                    var6_6 = this.mDecorContentParent.hideOverflowMenu();
                    break block8;
                }
                if (var4_4.isOpen || var4_4.isHandled) break block9;
                if (!var4_4.isPrepared) ** GOTO lbl-1000
                if (var4_4.refreshMenuContent) {
                    var4_4.isPrepared = false;
                    var6_6 = this.preparePanel(var4_4, var2_2);
                } else {
                    var6_6 = true;
                }
                if (var6_6) {
                    this.openPanel(var4_4, var2_2);
                    var6_6 = var3_3;
                } else lbl-1000: // 3 sources:
                {
                    var6_6 = false;
                }
                break block8;
            }
            var6_6 = var4_4.isOpen;
            this.closePanel(var4_4, true);
        }
        if (var6_6 == false) return var6_6;
        var2_2 = (AudioManager)this.mContext.getApplicationContext().getSystemService("audio");
        if (var2_2 != null) {
            var2_2.playSoundEffect(0);
            return var6_6;
        }
        Log.w((String)"AppCompatDelegate", (String)"Couldn't get audio manager");
        return var6_6;
    }

    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        block21 : {
            int n;
            WindowManager windowManager;
            block20 : {
                block19 : {
                    Window.Callback callback;
                    block18 : {
                        if (panelFeatureState.isOpen) return;
                        if (this.mIsDestroyed) {
                            return;
                        }
                        if (panelFeatureState.featureId == 0) {
                            if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                                return;
                            }
                            n = 0;
                            if (n != 0) {
                                return;
                            }
                        }
                        if ((callback = this.getWindowCallback()) != null && !callback.onMenuOpened(panelFeatureState.featureId, (Menu)panelFeatureState.menu)) {
                            this.closePanel(panelFeatureState, true);
                            return;
                        }
                        windowManager = (WindowManager)this.mContext.getSystemService("window");
                        if (windowManager == null) {
                            return;
                        }
                        if (!this.preparePanel(panelFeatureState, keyEvent)) {
                            return;
                        }
                        if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) break block18;
                        if (panelFeatureState.createdPanelView == null || (keyEvent = panelFeatureState.createdPanelView.getLayoutParams()) == null || keyEvent.width != -1) break block19;
                        n = -1;
                        break block20;
                    }
                    if (panelFeatureState.decorView == null) {
                        if (!this.initializePanelDecor(panelFeatureState)) return;
                        if (panelFeatureState.decorView == null) {
                            return;
                        }
                    } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                        panelFeatureState.decorView.removeAllViews();
                    }
                    if (!this.initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) break block21;
                    callback = panelFeatureState.shownPanelView.getLayoutParams();
                    keyEvent = callback;
                    if (callback == null) {
                        keyEvent = new ViewGroup.LayoutParams(-2, -2);
                    }
                    n = panelFeatureState.background;
                    panelFeatureState.decorView.setBackgroundResource(n);
                    callback = panelFeatureState.shownPanelView.getParent();
                    if (callback instanceof ViewGroup) {
                        ((ViewGroup)callback).removeView(panelFeatureState.shownPanelView);
                    }
                    panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, (ViewGroup.LayoutParams)keyEvent);
                    if (!panelFeatureState.shownPanelView.hasFocus()) {
                        panelFeatureState.shownPanelView.requestFocus();
                    }
                }
                n = -2;
            }
            panelFeatureState.isHandled = false;
            keyEvent = new WindowManager.LayoutParams(n, -2, panelFeatureState.x, panelFeatureState.y, 1002, 8519680, -3);
            keyEvent.gravity = panelFeatureState.gravity;
            keyEvent.windowAnimations = panelFeatureState.windowAnimations;
            windowManager.addView((View)panelFeatureState.decorView, (ViewGroup.LayoutParams)keyEvent);
            panelFeatureState.isOpen = true;
            return;
        }
        panelFeatureState.refreshDecorView = true;
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int n, KeyEvent keyEvent, int n2) {
        boolean bl;
        block6 : {
            boolean bl2;
            block5 : {
                bl = keyEvent.isSystem();
                bl2 = false;
                if (bl) {
                    return false;
                }
                if (panelFeatureState.isPrepared) break block5;
                bl = bl2;
                if (!this.preparePanel(panelFeatureState, keyEvent)) break block6;
            }
            bl = bl2;
            if (panelFeatureState.menu != null) {
                bl = panelFeatureState.menu.performShortcut(n, keyEvent, n2);
            }
        }
        if (!bl) return bl;
        if ((n2 & 1) != 0) return bl;
        if (this.mDecorContentParent != null) return bl;
        this.closePanel(panelFeatureState, true);
        return bl;
    }

    private boolean preparePanel(PanelFeatureState object, KeyEvent object2) {
        Window.Callback callback;
        if (this.mIsDestroyed) {
            return false;
        }
        if (((PanelFeatureState)object).isPrepared) {
            return true;
        }
        Object object3 = this.mPreparedPanel;
        if (object3 != null && object3 != object) {
            this.closePanel((PanelFeatureState)object3, false);
        }
        if ((callback = this.getWindowCallback()) != null) {
            ((PanelFeatureState)object).createdPanelView = callback.onCreatePanelView(((PanelFeatureState)object).featureId);
        }
        int n = ((PanelFeatureState)object).featureId != 0 && ((PanelFeatureState)object).featureId != 108 ? 0 : 1;
        if (n != 0 && (object3 = this.mDecorContentParent) != null) {
            object3.setMenuPrepared();
        }
        if (!(((PanelFeatureState)object).createdPanelView != null || n != 0 && this.peekSupportActionBar() instanceof ToolbarActionBar)) {
            if (((PanelFeatureState)object).menu == null || ((PanelFeatureState)object).refreshMenuContent) {
                if (((PanelFeatureState)object).menu == null) {
                    if (!this.initializePanelMenu((PanelFeatureState)object)) return false;
                    if (((PanelFeatureState)object).menu == null) {
                        return false;
                    }
                }
                if (n != 0 && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(((PanelFeatureState)object).menu, this.mActionMenuPresenterCallback);
                }
                ((PanelFeatureState)object).menu.stopDispatchingItemsChanged();
                if (!callback.onCreatePanelMenu(((PanelFeatureState)object).featureId, (Menu)((PanelFeatureState)object).menu)) {
                    ((PanelFeatureState)object).setMenu(null);
                    if (n == 0) return false;
                    object = this.mDecorContentParent;
                    if (object == null) return false;
                    object.setMenu(null, this.mActionMenuPresenterCallback);
                    return false;
                }
                ((PanelFeatureState)object).refreshMenuContent = false;
            }
            ((PanelFeatureState)object).menu.stopDispatchingItemsChanged();
            if (((PanelFeatureState)object).frozenActionViewState != null) {
                ((PanelFeatureState)object).menu.restoreActionViewStates(((PanelFeatureState)object).frozenActionViewState);
                ((PanelFeatureState)object).frozenActionViewState = null;
            }
            if (!callback.onPreparePanel(0, ((PanelFeatureState)object).createdPanelView, (Menu)((PanelFeatureState)object).menu)) {
                if (n != 0 && (object2 = this.mDecorContentParent) != null) {
                    object2.setMenu(null, this.mActionMenuPresenterCallback);
                }
                ((PanelFeatureState)object).menu.startDispatchingItemsChanged();
                return false;
            }
            n = object2 != null ? object2.getDeviceId() : -1;
            boolean bl = KeyCharacterMap.load((int)n).getKeyboardType() != 1;
            ((PanelFeatureState)object).qwertyMode = bl;
            ((PanelFeatureState)object).menu.setQwertyMode(((PanelFeatureState)object).qwertyMode);
            ((PanelFeatureState)object).menu.startDispatchingItemsChanged();
        }
        ((PanelFeatureState)object).isPrepared = true;
        ((PanelFeatureState)object).isHandled = false;
        this.mPreparedPanel = object;
        return true;
    }

    private void reopenMenu(boolean bl) {
        Object object = this.mDecorContentParent;
        if (object != null && object.canShowOverflowMenu() && (!ViewConfiguration.get((Context)this.mContext).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
            object = this.getWindowCallback();
            if (this.mDecorContentParent.isOverflowMenuShowing() && bl) {
                this.mDecorContentParent.hideOverflowMenu();
                if (this.mIsDestroyed) return;
                object.onPanelClosed(108, (Menu)this.getPanelState((int)0, (boolean)true).menu);
                return;
            }
            if (object == null) return;
            if (this.mIsDestroyed) return;
            if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                this.mInvalidatePanelMenuRunnable.run();
            }
            PanelFeatureState panelFeatureState = this.getPanelState(0, true);
            if (panelFeatureState.menu == null) return;
            if (panelFeatureState.refreshMenuContent) return;
            if (!object.onPreparePanel(0, panelFeatureState.createdPanelView, (Menu)panelFeatureState.menu)) return;
            object.onMenuOpened(108, (Menu)panelFeatureState.menu);
            this.mDecorContentParent.showOverflowMenu();
            return;
        }
        object = this.getPanelState(0, true);
        ((PanelFeatureState)object).refreshDecorView = true;
        this.closePanel((PanelFeatureState)object, false);
        this.openPanel((PanelFeatureState)object, null);
    }

    private int sanitizeWindowFeatureId(int n) {
        if (n == 8) {
            Log.i((String)"AppCompatDelegate", (String)"You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        int n2 = n;
        if (n != 9) return n2;
        Log.i((String)"AppCompatDelegate", (String)"You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
        return 109;
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View view = this.mWindow.getDecorView();
        while (viewParent != null) {
            if (viewParent == view) return false;
            if (!(viewParent instanceof View)) return false;
            if (ViewCompat.isAttachedToWindow((View)viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) throw new AndroidRuntimeException("Window feature must be requested before adding content");
    }

    private AppCompatActivity tryUnwrapContext() {
        Context context = this.mContext;
        while (context != null) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity)context;
            }
            if (!(context instanceof ContextWrapper)) return null;
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private boolean updateForNightMode(int n, boolean bl) {
        Object object = this.createOverrideConfigurationForDayNight(this.mContext, n, null);
        boolean bl2 = this.isActivityManifestHandlingUiMode();
        int n2 = this.mContext.getResources().getConfiguration().uiMode & 48;
        int n3 = object.uiMode & 48;
        boolean bl3 = true;
        if (n2 != n3 && bl && !bl2 && this.mBaseContextAttached && (sCanReturnDifferentContext || this.mCreated) && (object = this.mHost) instanceof Activity && !((Activity)object).isChild()) {
            ActivityCompat.recreate((Activity)this.mHost);
            bl = true;
        } else {
            bl = false;
        }
        if (!bl && n2 != n3) {
            this.updateResourcesConfigurationForNightMode(n3, bl2, null);
            bl = bl3;
        }
        if (!bl) return bl;
        object = this.mHost;
        if (!(object instanceof AppCompatActivity)) return bl;
        ((AppCompatActivity)object).onNightModeChanged(n);
        return bl;
    }

    private void updateResourcesConfigurationForNightMode(int n, boolean bl, Configuration object) {
        Resources resources = this.mContext.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (object != null) {
            configuration.updateFrom(object);
        }
        configuration.uiMode = n | resources.getConfiguration().uiMode & -49;
        resources.updateConfiguration(configuration, null);
        if (Build.VERSION.SDK_INT < 26) {
            ResourcesFlusher.flush(resources);
        }
        if ((n = this.mThemeResId) != 0) {
            this.mContext.setTheme(n);
            if (Build.VERSION.SDK_INT >= 23) {
                this.mContext.getTheme().applyStyle(this.mThemeResId, true);
            }
        }
        if (!bl) return;
        object = this.mHost;
        if (!(object instanceof Activity)) return;
        if ((object = (Activity)object) instanceof LifecycleOwner) {
            if (!((LifecycleOwner)object).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) return;
            object.onConfigurationChanged(configuration);
            return;
        }
        if (!this.mStarted) return;
        object.onConfigurationChanged(configuration);
    }

    private void updateStatusGuardColor(View view) {
        int n = (ViewCompat.getWindowSystemUiVisibility(view) & 8192) != 0 ? 1 : 0;
        n = n != 0 ? ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard_light) : ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard);
        view.setBackgroundColor(n);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureSubDecor();
        ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(view, layoutParams);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override
    public boolean applyDayNight() {
        return this.applyDayNight(true);
    }

    @Override
    public Context attachBaseContext2(Context context) {
        Configuration configuration;
        int n = 1;
        this.mBaseContextAttached = true;
        int n2 = this.mapNightMode(context, this.calculateNightMode());
        boolean bl = sCanApplyOverrideConfiguration;
        Object object = null;
        if (bl && context instanceof android.view.ContextThemeWrapper) {
            Configuration configuration2 = this.createOverrideConfigurationForDayNight(context, n2, null);
            try {
                ContextThemeWrapperCompatApi17Impl.applyOverrideConfiguration((android.view.ContextThemeWrapper)context, configuration2);
                return context;
            }
            catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
        }
        if (context instanceof ContextThemeWrapper) {
            Configuration configuration3 = this.createOverrideConfigurationForDayNight(context, n2, null);
            try {
                ((ContextThemeWrapper)context).applyOverrideConfiguration(configuration3);
                return context;
            }
            catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
        }
        if (!sCanReturnDifferentContext) {
            return super.attachBaseContext2(context);
        }
        try {
            configuration = context.getPackageManager().getResourcesForApplication(context.getApplicationInfo()).getConfiguration();
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new RuntimeException("Application failed to obtain resources from itself", nameNotFoundException);
        }
        Configuration configuration4 = context.getResources().getConfiguration();
        if (!configuration.equals(configuration4)) {
            object = AppCompatDelegateImpl.generateConfigDelta(configuration, configuration4);
        }
        configuration4 = this.createOverrideConfigurationForDayNight(context, n2, (Configuration)object);
        object = new ContextThemeWrapper(context, R.style.Theme_AppCompat_Empty);
        ((ContextThemeWrapper)((Object)object)).applyOverrideConfiguration(configuration4);
        n2 = 0;
        try {
            context = context.getTheme();
            if (context == null) {
                n = 0;
            }
        }
        catch (NullPointerException nullPointerException) {
            n = n2;
        }
        if (n == 0) return super.attachBaseContext2((Context)object);
        ResourcesCompat.ThemeCompat.rebase(((ContextThemeWrapper)((Object)object)).getTheme());
        return super.attachBaseContext2((Context)object);
    }

    void callOnPanelClosed(int n, PanelFeatureState arrpanelFeatureState, Menu menu2) {
        PanelFeatureState[] arrpanelFeatureState2 = arrpanelFeatureState;
        Menu menu3 = menu2;
        if (menu2 == null) {
            Object object = arrpanelFeatureState;
            if (arrpanelFeatureState == null) {
                object = arrpanelFeatureState;
                if (n >= 0) {
                    arrpanelFeatureState2 = this.mPanels;
                    object = arrpanelFeatureState;
                    if (n < arrpanelFeatureState2.length) {
                        object = arrpanelFeatureState2[n];
                    }
                }
            }
            arrpanelFeatureState2 = object;
            menu3 = menu2;
            if (object != null) {
                menu3 = object.menu;
                arrpanelFeatureState2 = object;
            }
        }
        if (arrpanelFeatureState2 != null && !arrpanelFeatureState2.isOpen) {
            return;
        }
        if (this.mIsDestroyed) return;
        this.mAppCompatWindowCallback.getWrapped().onPanelClosed(n, menu3);
    }

    void checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback callback = this.getWindowCallback();
        if (callback != null && !this.mIsDestroyed) {
            callback.onPanelClosed(108, (Menu)menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    void closePanel(int n) {
        this.closePanel(this.getPanelState(n, true), true);
    }

    void closePanel(PanelFeatureState panelFeatureState, boolean bl) {
        DecorContentParent decorContentParent;
        if (bl && panelFeatureState.featureId == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.isOverflowMenuShowing()) {
            this.checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        decorContentParent = (WindowManager)this.mContext.getSystemService("window");
        if (decorContentParent != null && panelFeatureState.isOpen && panelFeatureState.decorView != null) {
            decorContentParent.removeView((View)panelFeatureState.decorView);
            if (bl) {
                this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        panelFeatureState.refreshDecorView = true;
        if (this.mPreparedPanel != panelFeatureState) return;
        this.mPreparedPanel = null;
    }

    @Override
    public View createView(View view, String string2, Context context, AttributeSet attributeSet) {
        AppCompatViewInflater appCompatViewInflater = this.mAppCompatViewInflater;
        boolean bl = false;
        if (appCompatViewInflater == null) {
            String string3 = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (string3 == null) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater)Class.forName(string3).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Throwable throwable) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to instantiate custom view inflater ");
                    stringBuilder.append(string3);
                    stringBuilder.append(". Falling back to default.");
                    Log.i((String)"AppCompatDelegate", (String)stringBuilder.toString(), (Throwable)throwable);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        if (!IS_PRE_LOLLIPOP) {
            bl = false;
            return this.mAppCompatViewInflater.createView(view, string2, context, attributeSet, bl, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
        }
        if (attributeSet instanceof XmlPullParser) {
            if (((XmlPullParser)attributeSet).getDepth() <= 1) return this.mAppCompatViewInflater.createView(view, string2, context, attributeSet, bl, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
            bl = true;
            return this.mAppCompatViewInflater.createView(view, string2, context, attributeSet, bl, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
        }
        bl = this.shouldInheritContext((ViewParent)view);
        return this.mAppCompatViewInflater.createView(view, string2, context, attributeSet, bl, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }

    void dismissPopups() {
        Object object = this.mDecorContentParent;
        if (object != null) {
            object.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                }
                catch (IllegalArgumentException illegalArgumentException) {}
            }
            this.mActionModePopup = null;
        }
        this.endOnGoingFadeAnimation();
        object = this.getPanelState(0, false);
        if (object == null) return;
        if (((PanelFeatureState)object).menu == null) return;
        ((PanelFeatureState)object).menu.close();
    }

    boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Object object = this.mHost;
        boolean bl = object instanceof KeyEventDispatcher.Component;
        boolean bl2 = true;
        if ((bl || object instanceof AppCompatDialog) && (object = this.mWindow.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy((View)object, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int n = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            bl2 = false;
        }
        if (!bl2) return this.onKeyUp(n, keyEvent);
        return this.onKeyDown(n, keyEvent);
    }

    void doInvalidatePanelMenu(int n) {
        Object object;
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (panelFeatureState.menu != null) {
            object = new Bundle();
            panelFeatureState.menu.saveActionViewStates((Bundle)object);
            if (object.size() > 0) {
                panelFeatureState.frozenActionViewState = object;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            panelFeatureState.menu.clear();
        }
        panelFeatureState.refreshMenuContent = true;
        panelFeatureState.refreshDecorView = true;
        if (n != 108) {
            if (n != 0) return;
        }
        if (this.mDecorContentParent == null) return;
        object = this.getPanelState(0, false);
        if (object == null) return;
        object.isPrepared = false;
        this.preparePanel((PanelFeatureState)object, null);
    }

    void endOnGoingFadeAnimation() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
        if (viewPropertyAnimatorCompat == null) return;
        viewPropertyAnimatorCompat.cancel();
    }

    PanelFeatureState findMenuPanel(Menu menu2) {
        PanelFeatureState[] arrpanelFeatureState = this.mPanels;
        int n = 0;
        int n2 = arrpanelFeatureState != null ? arrpanelFeatureState.length : 0;
        while (n < n2) {
            PanelFeatureState panelFeatureState = arrpanelFeatureState[n];
            if (panelFeatureState != null && panelFeatureState.menu == menu2) {
                return panelFeatureState;
            }
            ++n;
        }
        return null;
    }

    @Override
    public <T extends View> T findViewById(int n) {
        this.ensureSubDecor();
        return (T)this.mWindow.findViewById(n);
    }

    final Context getActionBarThemedContext() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar = actionBar != null ? actionBar.getThemedContext() : null;
        ActionBar actionBar2 = actionBar;
        if (actionBar != null) return actionBar2;
        return this.mContext;
    }

    final AutoNightModeManager getAutoTimeNightModeManager() {
        return this.getAutoTimeNightModeManager(this.mContext);
    }

    @Override
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    @Override
    public int getLocalNightMode() {
        return this.mLocalNightMode;
    }

    @Override
    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater != null) return this.mMenuInflater;
        this.initWindowDecorActionBar();
        ActionBar actionBar = this.mActionBar;
        actionBar = actionBar != null ? actionBar.getThemedContext() : this.mContext;
        this.mMenuInflater = new SupportMenuInflater((Context)actionBar);
        return this.mMenuInflater;
    }

    protected PanelFeatureState getPanelState(int n, boolean bl) {
        Object object;
        PanelFeatureState[] arrpanelFeatureState;
        block5 : {
            block4 : {
                object = this.mPanels;
                if (object == null) break block4;
                arrpanelFeatureState = object;
                if (((PanelFeatureState[])object).length > n) break block5;
            }
            arrpanelFeatureState = new PanelFeatureState[n + 1];
            if (object != null) {
                System.arraycopy(object, 0, arrpanelFeatureState, 0, ((PanelFeatureState[])object).length);
            }
            this.mPanels = arrpanelFeatureState;
        }
        PanelFeatureState panelFeatureState = arrpanelFeatureState[n];
        object = panelFeatureState;
        if (panelFeatureState != null) return object;
        arrpanelFeatureState[n] = object = new PanelFeatureState(n);
        return object;
    }

    ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    @Override
    public ActionBar getSupportActionBar() {
        this.initWindowDecorActionBar();
        return this.mActionBar;
    }

    final CharSequence getTitle() {
        Object object = this.mHost;
        if (!(object instanceof Activity)) return this.mTitle;
        return ((Activity)object).getTitle();
    }

    final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    @Override
    public boolean hasWindowFeature(int n) {
        int n2 = this.sanitizeWindowFeatureId(n);
        boolean bl = true;
        boolean bl2 = n2 != 1 ? (n2 != 2 ? (n2 != 5 ? (n2 != 10 ? (n2 != 108 ? (n2 != 109 ? false : this.mOverlayActionBar) : this.mHasActionBar) : this.mOverlayActionMode) : this.mFeatureIndeterminateProgress) : this.mFeatureProgress) : this.mWindowNoTitle;
        boolean bl3 = bl;
        if (bl2) return bl3;
        if (!this.mWindow.hasFeature(n)) return false;
        return bl;
    }

    @Override
    public void installViewFactory() {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.mContext);
        if (layoutInflater.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, this);
            return;
        }
        if (layoutInflater.getFactory2() instanceof AppCompatDelegateImpl) return;
        Log.i((String)"AppCompatDelegate", (String)"The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    }

    @Override
    public void invalidateOptionsMenu() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null && actionBar.invalidateOptionsMenu()) {
            return;
        }
        this.invalidatePanelMenu(0);
    }

    @Override
    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    int mapNightMode(Context context, int n) {
        if (n == -100) return -1;
        if (n == -1) return n;
        if (n != 0) {
            if (n == 1) return n;
            if (n == 2) return n;
            if (n != 3) throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
            return this.getAutoBatteryNightModeManager(context).getApplyableNightMode();
        }
        if (Build.VERSION.SDK_INT < 23) return this.getAutoTimeNightModeManager(context).getApplyableNightMode();
        if (((UiModeManager)context.getApplicationContext().getSystemService(UiModeManager.class)).getNightMode() != 0) return this.getAutoTimeNightModeManager(context).getApplyableNightMode();
        return -1;
    }

    boolean onBackPressed() {
        Object object = this.mActionMode;
        if (object != null) {
            ((ActionMode)object).finish();
            return true;
        }
        object = this.getSupportActionBar();
        if (object == null) return false;
        if (!((ActionBar)object).collapseActionView()) return false;
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        ActionBar actionBar;
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBar = this.getSupportActionBar()) != null) {
            actionBar.onConfigurationChanged(configuration);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        this.applyDayNight(false);
    }

    @Override
    public void onCreate(Bundle object) {
        this.mBaseContextAttached = true;
        this.applyDayNight(false);
        this.ensureWindow();
        Object object2 = this.mHost;
        if (object2 instanceof Activity) {
            object = null;
            try {
                object = object2 = NavUtils.getParentActivityName((Activity)object2);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            if (object != null) {
                object = this.peekSupportActionBar();
                if (object == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    ((ActionBar)object).setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            AppCompatDelegateImpl.addActiveDelegate(this);
        }
        this.mCreated = true;
    }

    public final View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        return this.createView(view, string2, context, attributeSet);
    }

    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string2, context, attributeSet);
    }

    @Override
    public void onDestroy() {
        Object object;
        if (this.mHost instanceof Activity) {
            AppCompatDelegateImpl.removeActivityDelegate(this);
        }
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        this.mStarted = false;
        this.mIsDestroyed = true;
        if (this.mLocalNightMode != -100 && (object = this.mHost) instanceof Activity && ((Activity)object).isChangingConfigurations()) {
            sLocalNightModes.put(this.mHost.getClass().getName(), this.mLocalNightMode);
        } else {
            sLocalNightModes.remove(this.mHost.getClass().getName());
        }
        object = this.mActionBar;
        if (object != null) {
            ((ActionBar)object).onDestroy();
        }
        this.cleanupAutoManagers();
    }

    boolean onKeyDown(int n, KeyEvent keyEvent) {
        boolean bl = true;
        if (n != 4) {
            if (n != 82) {
                return false;
            }
            this.onKeyDownPanel(0, keyEvent);
            return true;
        }
        if ((keyEvent.getFlags() & 128) == 0) {
            bl = false;
        }
        this.mLongPressBackDown = bl;
        return false;
    }

    boolean onKeyShortcut(int n, KeyEvent object) {
        Object object2 = this.getSupportActionBar();
        if (object2 != null && ((ActionBar)object2).onKeyShortcut(n, (KeyEvent)object)) {
            return true;
        }
        object2 = this.mPreparedPanel;
        if (object2 != null && this.performPanelShortcut((PanelFeatureState)object2, object.getKeyCode(), (KeyEvent)object, 1)) {
            object = this.mPreparedPanel;
            if (object == null) return true;
            object.isHandled = true;
            return true;
        }
        if (this.mPreparedPanel != null) return false;
        object2 = this.getPanelState(0, true);
        this.preparePanel((PanelFeatureState)object2, (KeyEvent)object);
        boolean bl = this.performPanelShortcut((PanelFeatureState)object2, object.getKeyCode(), (KeyEvent)object, 1);
        ((PanelFeatureState)object2).isPrepared = false;
        if (!bl) return false;
        return true;
    }

    boolean onKeyUp(int n, KeyEvent object) {
        if (n != 4) {
            if (n != 82) {
                return false;
            }
            this.onKeyUpPanel(0, (KeyEvent)object);
            return true;
        }
        boolean bl = this.mLongPressBackDown;
        this.mLongPressBackDown = false;
        object = this.getPanelState(0, false);
        if (object != null && object.isOpen) {
            if (bl) return true;
            this.closePanel((PanelFeatureState)object, true);
            return true;
        }
        if (!this.onBackPressed()) return false;
        return true;
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder object, MenuItem menuItem) {
        Window.Callback callback = this.getWindowCallback();
        if (callback == null) return false;
        if (this.mIsDestroyed) return false;
        if ((object = this.findMenuPanel(((MenuBuilder)object).getRootMenu())) == null) return false;
        return callback.onMenuItemSelected(((PanelFeatureState)object).featureId, menuItem);
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        this.reopenMenu(true);
    }

    void onMenuOpened(int n) {
        if (n != 108) return;
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar == null) return;
        actionBar.dispatchMenuVisibilityChanged(true);
    }

    void onPanelClosed(int n) {
        if (n == 108) {
            ActionBar actionBar = this.getSupportActionBar();
            if (actionBar == null) return;
            actionBar.dispatchMenuVisibilityChanged(false);
            return;
        }
        if (n != 0) return;
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (!panelFeatureState.isOpen) return;
        this.closePanel(panelFeatureState, false);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        this.ensureSubDecor();
    }

    @Override
    public void onPostResume() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setShowHideAnimationEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override
    public void onStart() {
        this.mStarted = true;
        this.applyDayNight();
    }

    @Override
    public void onStop() {
        this.mStarted = false;
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setShowHideAnimationEnabled(false);
    }

    void onSubDecorInstalled(ViewGroup viewGroup) {
    }

    final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    @Override
    public boolean requestWindowFeature(int n) {
        n = this.sanitizeWindowFeatureId(n);
        if (this.mWindowNoTitle && n == 108) {
            return false;
        }
        if (this.mHasActionBar && n == 1) {
            this.mHasActionBar = false;
        }
        if (n == 1) {
            this.throwFeatureRequestIfSubDecorInstalled();
            this.mWindowNoTitle = true;
            return true;
        }
        if (n == 2) {
            this.throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureProgress = true;
            return true;
        }
        if (n == 5) {
            this.throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureIndeterminateProgress = true;
            return true;
        }
        if (n == 10) {
            this.throwFeatureRequestIfSubDecorInstalled();
            this.mOverlayActionMode = true;
            return true;
        }
        if (n == 108) {
            this.throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
        }
        if (n != 109) {
            return this.mWindow.requestFeature(n);
        }
        this.throwFeatureRequestIfSubDecorInstalled();
        this.mOverlayActionBar = true;
        return true;
    }

    @Override
    public void setContentView(int n) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from((Context)this.mContext).inflate(n, viewGroup);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override
    public void setHandleNativeActionModesEnabled(boolean bl) {
        this.mHandleNativeActionModes = bl;
    }

    @Override
    public void setLocalNightMode(int n) {
        if (this.mLocalNightMode == n) return;
        this.mLocalNightMode = n;
        if (!this.mBaseContextAttached) return;
        this.applyDayNight();
    }

    @Override
    public void setSupportActionBar(Toolbar object) {
        if (!(this.mHost instanceof Activity)) {
            return;
        }
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar instanceof WindowDecorActionBar) throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        this.mMenuInflater = null;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        if (object != null) {
            object = new ToolbarActionBar((Toolbar)((Object)object), this.getTitle(), this.mAppCompatWindowCallback);
            this.mActionBar = object;
            this.mWindow.setCallback(((ToolbarActionBar)object).getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback((Window.Callback)this.mAppCompatWindowCallback);
        }
        this.invalidateOptionsMenu();
    }

    @Override
    public void setTheme(int n) {
        this.mThemeResId = n;
    }

    @Override
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
            return;
        }
        if (this.peekSupportActionBar() != null) {
            this.peekSupportActionBar().setWindowTitle(charSequence);
            return;
        }
        decorContentParent = this.mTitleView;
        if (decorContentParent == null) return;
        decorContentParent.setText(charSequence);
    }

    final boolean shouldAnimateActionModeView() {
        if (!this.mSubDecorInstalled) return false;
        ViewGroup viewGroup = this.mSubDecor;
        if (viewGroup == null) return false;
        if (!ViewCompat.isLaidOut((View)viewGroup)) return false;
        return true;
    }

    @Override
    public ActionMode startSupportActionMode(ActionMode.Callback callback) {
        if (callback == null) throw new IllegalArgumentException("ActionMode callback can not be null.");
        Object object = this.mActionMode;
        if (object != null) {
            ((ActionMode)object).finish();
        }
        callback = new ActionModeCallbackWrapperV9(callback);
        object = this.getSupportActionBar();
        if (object != null) {
            ActionMode actionMode;
            this.mActionMode = actionMode = ((ActionBar)object).startActionMode(callback);
            if (actionMode != null && (object = this.mAppCompatCallback) != null) {
                object.onSupportActionModeStarted(actionMode);
            }
        }
        if (this.mActionMode != null) return this.mActionMode;
        this.mActionMode = this.startSupportActionModeFromWindow(callback);
        return this.mActionMode;
    }

    ActionMode startSupportActionModeFromWindow(ActionMode.Callback object) {
        Object object2;
        block21 : {
            this.endOnGoingFadeAnimation();
            object2 = this.mActionMode;
            if (object2 != null) {
                ((ActionMode)object2).finish();
            }
            object2 = object;
            if (!(object instanceof ActionModeCallbackWrapperV9)) {
                object2 = new ActionModeCallbackWrapperV9((ActionMode.Callback)object);
            }
            if ((object = this.mAppCompatCallback) != null && !this.mIsDestroyed) {
                try {
                    object = object.onWindowStartingSupportActionMode((ActionMode.Callback)object2);
                    break block21;
                }
                catch (AbstractMethodError abstractMethodError) {}
            }
            object = null;
        }
        if (object != null) {
            this.mActionMode = object;
        } else {
            TypedValue typedValue;
            object = this.mActionModeView;
            boolean bl = true;
            if (object == null) {
                if (this.mIsFloating) {
                    Resources.Theme theme;
                    typedValue = new TypedValue();
                    object = this.mContext.getTheme();
                    object.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        theme = this.mContext.getResources().newTheme();
                        theme.setTo((Resources.Theme)object);
                        theme.applyStyle(typedValue.resourceId, true);
                        object = new ContextThemeWrapper(this.mContext, 0);
                        object.getTheme().setTo(theme);
                    } else {
                        object = this.mContext;
                    }
                    this.mActionModeView = new ActionBarContextView((Context)object);
                    theme = new PopupWindow((Context)object, null, R.attr.actionModePopupWindowStyle);
                    this.mActionModePopup = theme;
                    PopupWindowCompat.setWindowLayoutType((PopupWindow)theme, 2);
                    this.mActionModePopup.setContentView((View)this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    object.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
                    int n = TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)object.getResources().getDisplayMetrics());
                    this.mActionModeView.setContentHeight(n);
                    this.mActionModePopup.setHeight(-2);
                    this.mShowActionModePopup = new Runnable(){

                        @Override
                        public void run() {
                            AppCompatDelegateImpl.this.mActionModePopup.showAtLocation((View)AppCompatDelegateImpl.this.mActionModeView, 55, 0, 0);
                            AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
                            if (AppCompatDelegateImpl.this.shouldAnimateActionModeView()) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl.mFadeAnim = ViewCompat.animate((View)appCompatDelegateImpl.mActionModeView).alpha(1.0f);
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                                    @Override
                                    public void onAnimationEnd(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                        AppCompatDelegateImpl.this.mFadeAnim = null;
                                    }

                                    @Override
                                    public void onAnimationStart(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                    }
                                });
                                return;
                            }
                            AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                            AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                        }

                    };
                } else {
                    object = (ViewStubCompat)this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
                    if (object != null) {
                        ((ViewStubCompat)((Object)object)).setLayoutInflater(LayoutInflater.from((Context)this.getActionBarThemedContext()));
                        this.mActionModeView = (ActionBarContextView)((ViewStubCompat)((Object)object)).inflate();
                    }
                }
            }
            if (this.mActionModeView != null) {
                this.endOnGoingFadeAnimation();
                this.mActionModeView.killMode();
                typedValue = this.mActionModeView.getContext();
                object = this.mActionModeView;
                if (this.mActionModePopup != null) {
                    bl = false;
                }
                object = new StandaloneActionMode((Context)typedValue, (ActionBarContextView)((Object)object), (ActionMode.Callback)object2, bl);
                if (object2.onCreateActionMode((ActionMode)object, ((ActionMode)object).getMenu())) {
                    ((ActionMode)object).invalidate();
                    this.mActionModeView.initForMode((ActionMode)object);
                    this.mActionMode = object;
                    if (this.shouldAnimateActionModeView()) {
                        this.mActionModeView.setAlpha(0.0f);
                        object = ViewCompat.animate((View)this.mActionModeView).alpha(1.0f);
                        this.mFadeAnim = object;
                        ((ViewPropertyAnimatorCompat)object).setListener(new ViewPropertyAnimatorListenerAdapter(){

                            @Override
                            public void onAnimationEnd(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                AppCompatDelegateImpl.this.mFadeAnim = null;
                            }

                            @Override
                            public void onAnimationStart(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                AppCompatDelegateImpl.this.mActionModeView.sendAccessibilityEvent(32);
                                if (!(AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View)) return;
                                ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
                            }
                        });
                    } else {
                        this.mActionModeView.setAlpha(1.0f);
                        this.mActionModeView.setVisibility(0);
                        this.mActionModeView.sendAccessibilityEvent(32);
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View)this.mActionModeView.getParent());
                        }
                    }
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                } else {
                    this.mActionMode = null;
                }
            }
        }
        object = this.mActionMode;
        if (object == null) return this.mActionMode;
        object2 = this.mAppCompatCallback;
        if (object2 == null) return this.mActionMode;
        object2.onSupportActionModeStarted((ActionMode)object);
        return this.mActionMode;
    }

    final int updateStatusGuard(WindowInsetsCompat windowInsetsCompat, Rect rect) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : (rect != null ? rect.top : 0);
        ActionBarContextView actionBarContextView = this.mActionModeView;
        if (actionBarContextView != null && actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            int n5;
            actionBarContextView = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
            boolean bl = this.mActionModeView.isShown();
            int n6 = 1;
            n2 = 1;
            if (bl) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect rect2 = this.mTempRect1;
                Rect rect3 = this.mTempRect2;
                if (windowInsetsCompat == null) {
                    rect2.set(rect);
                } else {
                    rect2.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                ViewUtils.computeFitSystemWindows((View)this.mSubDecor, rect2, rect3);
                int n7 = rect2.top;
                int n8 = rect2.left;
                n5 = rect2.right;
                windowInsetsCompat = ViewCompat.getRootWindowInsets((View)this.mSubDecor);
                n6 = windowInsetsCompat == null ? 0 : windowInsetsCompat.getSystemWindowInsetLeft();
                n = windowInsetsCompat == null ? 0 : windowInsetsCompat.getSystemWindowInsetRight();
                if (((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin == n7 && ((ViewGroup.MarginLayoutParams)actionBarContextView).leftMargin == n8 && ((ViewGroup.MarginLayoutParams)actionBarContextView).rightMargin == n5) {
                    n5 = 0;
                } else {
                    ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin = n7;
                    ((ViewGroup.MarginLayoutParams)actionBarContextView).leftMargin = n8;
                    ((ViewGroup.MarginLayoutParams)actionBarContextView).rightMargin = n5;
                    n5 = 1;
                }
                if (n7 > 0 && this.mStatusGuard == null) {
                    windowInsetsCompat = new View(this.mContext);
                    this.mStatusGuard = windowInsetsCompat;
                    windowInsetsCompat.setVisibility(8);
                    windowInsetsCompat = new FrameLayout.LayoutParams(-1, ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin, 51);
                    ((FrameLayout.LayoutParams)windowInsetsCompat).leftMargin = n6;
                    ((FrameLayout.LayoutParams)windowInsetsCompat).rightMargin = n;
                    this.mSubDecor.addView(this.mStatusGuard, -1, (ViewGroup.LayoutParams)windowInsetsCompat);
                } else {
                    windowInsetsCompat = this.mStatusGuard;
                    if (windowInsetsCompat != null) {
                        windowInsetsCompat = (ViewGroup.MarginLayoutParams)windowInsetsCompat.getLayoutParams();
                        if (((ViewGroup.MarginLayoutParams)windowInsetsCompat).height != ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin || ((ViewGroup.MarginLayoutParams)windowInsetsCompat).leftMargin != n6 || ((ViewGroup.MarginLayoutParams)windowInsetsCompat).rightMargin != n) {
                            ((ViewGroup.MarginLayoutParams)windowInsetsCompat).height = ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin;
                            ((ViewGroup.MarginLayoutParams)windowInsetsCompat).leftMargin = n6;
                            ((ViewGroup.MarginLayoutParams)windowInsetsCompat).rightMargin = n;
                            this.mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)windowInsetsCompat);
                        }
                    }
                }
                n = this.mStatusGuard != null ? n2 : 0;
                if (n != 0 && this.mStatusGuard.getVisibility() != 0) {
                    this.updateStatusGuardColor(this.mStatusGuard);
                }
                n6 = n4;
                if (!this.mOverlayActionMode) {
                    n6 = n4;
                    if (n != 0) {
                        n6 = 0;
                    }
                }
                n4 = n6;
                n6 = n5;
                n5 = n;
            } else if (((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin != 0) {
                ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin = 0;
                n5 = 0;
            } else {
                n5 = 0;
                n6 = 0;
            }
            n = n4;
            n2 = n5;
            if (n6 != 0) {
                this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)actionBarContextView);
                n = n4;
                n2 = n5;
            }
        } else {
            n2 = 0;
            n = n4;
        }
        if ((windowInsetsCompat = this.mStatusGuard) == null) return n;
        n4 = n2 != 0 ? n3 : 8;
        windowInsetsCompat.setVisibility(n4);
        return n;
    }

    private class ActionBarDrawableToggleImpl
    implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        @Override
        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.getActionBarThemedContext();
        }

        @Override
        public Drawable getThemeUpIndicator() {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.getActionBarThemedContext(), null, new int[]{R.attr.homeAsUpIndicator});
            Drawable drawable2 = tintTypedArray.getDrawable(0);
            tintTypedArray.recycle();
            return drawable2;
        }

        @Override
        public boolean isNavigationVisible() {
            ActionBar actionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (actionBar == null) return false;
            if ((actionBar.getDisplayOptions() & 4) == 0) return false;
            return true;
        }

        @Override
        public void setActionBarDescription(int n) {
            ActionBar actionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (actionBar == null) return;
            actionBar.setHomeActionContentDescription(n);
        }

        @Override
        public void setActionBarUpIndicator(Drawable drawable2, int n) {
            ActionBar actionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (actionBar == null) return;
            actionBar.setHomeAsUpIndicator(drawable2);
            actionBar.setHomeActionContentDescription(n);
        }
    }

    private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menuBuilder);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = AppCompatDelegateImpl.this.getWindowCallback();
            if (callback == null) return true;
            callback.onMenuOpened(108, (Menu)menuBuilder);
            return true;
        }
    }

    class ActionModeCallbackWrapperV9
    implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            return this.mWrapped.onCreateActionMode(actionMode, menu2);
        }

        @Override
        public void onDestroyActionMode(ActionMode object) {
            this.mWrapped.onDestroyActionMode((ActionMode)object);
            if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                AppCompatDelegateImpl.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
            }
            if (AppCompatDelegateImpl.this.mActionModeView != null) {
                AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
                object = AppCompatDelegateImpl.this;
                ((AppCompatDelegateImpl)object).mFadeAnim = ViewCompat.animate((View)((AppCompatDelegateImpl)object).mActionModeView).alpha(0.0f);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                            AppCompatDelegateImpl.this.mActionModePopup.dismiss();
                        } else if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImpl.this.mActionModeView.removeAllViews();
                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                        AppCompatDelegateImpl.this.mFadeAnim = null;
                        ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
                    }
                });
            }
            if (AppCompatDelegateImpl.this.mAppCompatCallback != null) {
                AppCompatDelegateImpl.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImpl.this.mActionMode);
            }
            AppCompatDelegateImpl.this.mActionMode = null;
            ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mSubDecor);
            return this.mWrapped.onPrepareActionMode(actionMode, menu2);
        }

    }

    class AppCompatWindowCallback
    extends WindowCallbackWrapper {
        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent)) return true;
            if (super.dispatchKeyEvent(keyEvent)) return true;
            return false;
        }

        @Override
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            if (super.dispatchKeyShortcutEvent(keyEvent)) return true;
            if (AppCompatDelegateImpl.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent)) return true;
            return false;
        }

        @Override
        public void onContentChanged() {
        }

        @Override
        public boolean onCreatePanelMenu(int n, Menu menu2) {
            if (n != 0) return super.onCreatePanelMenu(n, menu2);
            if (menu2 instanceof MenuBuilder) return super.onCreatePanelMenu(n, menu2);
            return false;
        }

        @Override
        public boolean onMenuOpened(int n, Menu menu2) {
            super.onMenuOpened(n, menu2);
            AppCompatDelegateImpl.this.onMenuOpened(n);
            return true;
        }

        @Override
        public void onPanelClosed(int n, Menu menu2) {
            super.onPanelClosed(n, menu2);
            AppCompatDelegateImpl.this.onPanelClosed(n);
        }

        @Override
        public boolean onPreparePanel(int n, View view, Menu menu2) {
            MenuBuilder menuBuilder = menu2 instanceof MenuBuilder ? (MenuBuilder)menu2 : null;
            if (n == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean bl = super.onPreparePanel(n, view, menu2);
            if (menuBuilder == null) return bl;
            menuBuilder.setOverrideVisibleItems(false);
            return bl;
        }

        @Override
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu2, int n) {
            PanelFeatureState panelFeatureState = AppCompatDelegateImpl.this.getPanelState(0, true);
            if (panelFeatureState != null && panelFeatureState.menu != null) {
                super.onProvideKeyboardShortcuts(list, panelFeatureState.menu, n);
                return;
            }
            super.onProvideKeyboardShortcuts(list, menu2, n);
        }

        @Override
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            if (!AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) return super.onWindowStartingActionMode(callback);
            return this.startAsSupportActionMode(callback);
        }

        @Override
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int n) {
            if (!AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) return super.onWindowStartingActionMode(callback, n);
            if (n == 0) return this.startAsSupportActionMode(callback);
            return super.onWindowStartingActionMode(callback, n);
        }

        final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback object) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, (ActionMode.Callback)object);
            object = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (object == null) return null;
            return callbackWrapper.getActionModeWrapper((ActionMode)object);
        }
    }

    private class AutoBatteryNightModeManager
    extends AutoNightModeManager {
        private final PowerManager mPowerManager;

        AutoBatteryNightModeManager(Context context) {
            this.mPowerManager = (PowerManager)context.getApplicationContext().getSystemService("power");
        }

        @Override
        IntentFilter createIntentFilterForBroadcastReceiver() {
            if (Build.VERSION.SDK_INT < 21) return null;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        @Override
        public int getApplyableNightMode() {
            int n;
            int n2 = Build.VERSION.SDK_INT;
            int n3 = n = 1;
            if (n2 < 21) return n3;
            n3 = n;
            if (!this.mPowerManager.isPowerSaveMode()) return n3;
            return 2;
        }

        @Override
        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    abstract class AutoNightModeManager {
        private BroadcastReceiver mReceiver;

        AutoNightModeManager() {
        }

        void cleanup() {
            if (this.mReceiver == null) return;
            try {
                AppCompatDelegateImpl.this.mContext.unregisterReceiver(this.mReceiver);
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            this.mReceiver = null;
        }

        abstract IntentFilter createIntentFilterForBroadcastReceiver();

        abstract int getApplyableNightMode();

        boolean isListening() {
            if (this.mReceiver == null) return false;
            return true;
        }

        abstract void onChange();

        void setup() {
            this.cleanup();
            IntentFilter intentFilter = this.createIntentFilterForBroadcastReceiver();
            if (intentFilter == null) return;
            if (intentFilter.countActions() == 0) {
                return;
            }
            if (this.mReceiver == null) {
                this.mReceiver = new BroadcastReceiver(){

                    public void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.onChange();
                    }
                };
            }
            AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, intentFilter);
        }

    }

    private class AutoTimeNightModeManager
    extends AutoNightModeManager {
        private final TwilightManager mTwilightManager;

        AutoTimeNightModeManager(TwilightManager twilightManager) {
            this.mTwilightManager = twilightManager;
        }

        @Override
        IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        @Override
        public int getApplyableNightMode() {
            if (!this.mTwilightManager.isNight()) return 1;
            return 2;
        }

        @Override
        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    static class ConfigurationImplApi17 {
        private ConfigurationImplApi17() {
        }

        static void generateConfigDelta_densityDpi(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            if (configuration.densityDpi == configuration2.densityDpi) return;
            configuration3.densityDpi = configuration2.densityDpi;
        }
    }

    static class ConfigurationImplApi24 {
        private ConfigurationImplApi24() {
        }

        static void generateConfigDelta_locale(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            LocaleList localeList;
            if ((configuration = configuration.getLocales()).equals((Object)(localeList = configuration2.getLocales()))) return;
            configuration3.setLocales(localeList);
            configuration3.locale = configuration2.locale;
        }
    }

    static class ConfigurationImplApi26 {
        private ConfigurationImplApi26() {
        }

        static void generateConfigDelta_colorMode(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                configuration3.colorMode |= configuration2.colorMode & 3;
            }
            if ((configuration.colorMode & 12) == (configuration2.colorMode & 12)) return;
            configuration3.colorMode |= configuration2.colorMode & 12;
        }
    }

    private static class ContextThemeWrapperCompatApi17Impl {
        private ContextThemeWrapperCompatApi17Impl() {
        }

        static void applyOverrideConfiguration(android.view.ContextThemeWrapper contextThemeWrapper, Configuration configuration) {
            contextThemeWrapper.applyOverrideConfiguration(configuration);
        }
    }

    private class ListMenuDecorView
    extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        private boolean isOutOfBounds(int n, int n2) {
            if (n < -5) return true;
            if (n2 < -5) return true;
            if (n > this.getWidth() + 5) return true;
            if (n2 > this.getHeight() + 5) return true;
            return false;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent)) return true;
            if (super.dispatchKeyEvent(keyEvent)) return true;
            return false;
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) return super.onInterceptTouchEvent(motionEvent);
            if (!this.isOutOfBounds((int)motionEvent.getX(), (int)motionEvent.getY())) return super.onInterceptTouchEvent(motionEvent);
            AppCompatDelegateImpl.this.closePanel(0);
            return true;
        }

        public void setBackgroundResource(int n) {
            this.setBackgroundDrawable(AppCompatResources.getDrawable(this.getContext(), n));
        }
    }

    protected static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int n) {
            this.featureId = n;
            this.refreshDecorView = false;
        }

        void applyFrozenState() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder == null) return;
            Bundle bundle = this.frozenMenuState;
            if (bundle == null) return;
            menuBuilder.restorePresenterStates(bundle);
            this.frozenMenuState = null;
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        MenuView getListMenuView(MenuPresenter.Callback callback) {
            ListMenuPresenter listMenuPresenter;
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter != null) return this.listMenuPresenter.getMenuView(this.decorView);
            this.listMenuPresenter = listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
            listMenuPresenter.setCallback(callback);
            this.menu.addMenuPresenter(this.listMenuPresenter);
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        public boolean hasPanelItems() {
            View view = this.shownPanelView;
            boolean bl = false;
            if (view == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            if (this.listMenuPresenter.getAdapter().getCount() <= 0) return bl;
            return true;
        }

        void onRestoreInstanceState(Parcelable parcelable) {
            parcelable = (SavedState)parcelable;
            this.featureId = parcelable.featureId;
            this.wasLastOpen = parcelable.isOpen;
            this.frozenMenuState = parcelable.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            if (this.menu == null) return savedState;
            savedState.menuState = new Bundle();
            this.menu.savePresenterStates(savedState.menuState);
            return savedState;
        }

        void setMenu(MenuBuilder menuBuilder) {
            Object object = this.menu;
            if (menuBuilder == object) {
                return;
            }
            if (object != null) {
                ((MenuBuilder)object).removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menuBuilder;
            if (menuBuilder == null) return;
            object = this.listMenuPresenter;
            if (object == null) return;
            menuBuilder.addMenuPresenter((MenuPresenter)object);
        }

        void setStyle(Context object) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = object.getResources().newTheme();
            theme.setTo(object.getTheme());
            theme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme.applyStyle(typedValue.resourceId, true);
            }
            theme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme.applyStyle(typedValue.resourceId, true);
            } else {
                theme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
            }
            object = new ContextThemeWrapper((Context)object, 0);
            object.getTheme().setTo(theme);
            this.listPresenterContext = object;
            object = object.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.background = object.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = object.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            object.recycle();
        }

        private static class SavedState
        implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.readFromParcel(parcel, null);
                }

                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.readFromParcel(parcel, classLoader);
                }

                public SavedState[] newArray(int n) {
                    return new SavedState[n];
                }
            };
            int featureId;
            boolean isOpen;
            Bundle menuState;

            SavedState() {
            }

            static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.featureId = parcel.readInt();
                int n = parcel.readInt();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                savedState.isOpen = bl;
                if (!bl) return savedState;
                savedState.menuState = parcel.readBundle(classLoader);
                return savedState;
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int n) {
                parcel.writeInt(this.featureId);
                parcel.writeInt((int)this.isOpen);
                if (!this.isOpen) return;
                parcel.writeBundle(this.menuState);
            }

        }

    }

    private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder object, boolean bl) {
            MenuBuilder menuBuilder = ((MenuBuilder)object).getRootMenu();
            boolean bl2 = menuBuilder != object;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (bl2) {
                object = menuBuilder;
            }
            if ((object = appCompatDelegateImpl.findMenuPanel((Menu)object)) == null) return;
            if (bl2) {
                AppCompatDelegateImpl.this.callOnPanelClosed(((PanelFeatureState)object).featureId, (PanelFeatureState)object, menuBuilder);
                AppCompatDelegateImpl.this.closePanel((PanelFeatureState)object, true);
                return;
            }
            AppCompatDelegateImpl.this.closePanel((PanelFeatureState)object, bl);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder != menuBuilder.getRootMenu()) return true;
            if (!AppCompatDelegateImpl.this.mHasActionBar) return true;
            Window.Callback callback = AppCompatDelegateImpl.this.getWindowCallback();
            if (callback == null) return true;
            if (AppCompatDelegateImpl.this.mIsDestroyed) return true;
            callback.onMenuOpened(108, (Menu)menuBuilder);
            return true;
        }
    }

}

