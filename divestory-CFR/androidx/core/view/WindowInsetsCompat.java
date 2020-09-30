/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.DisplayCutout
 *  android.view.WindowInsets
 *  android.view.WindowInsets$Builder
 */
package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.DisplayCutoutCompat;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

public class WindowInsetsCompat {
    public static final WindowInsetsCompat CONSUMED = new Builder().build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
    private static final String TAG = "WindowInsetsCompat";
    private final Impl mImpl;

    private WindowInsetsCompat(WindowInsets windowInsets) {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mImpl = new Impl29(this, windowInsets);
            return;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mImpl = new Impl28(this, windowInsets);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new Impl21(this, windowInsets);
            return;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mImpl = new Impl20(this, windowInsets);
            return;
        }
        this.mImpl = new Impl(this);
    }

    public WindowInsetsCompat(WindowInsetsCompat object) {
        if (object == null) {
            this.mImpl = new Impl(this);
            return;
        }
        object = ((WindowInsetsCompat)object).mImpl;
        if (Build.VERSION.SDK_INT >= 29 && object instanceof Impl29) {
            this.mImpl = new Impl29(this, (Impl29)object);
            return;
        }
        if (Build.VERSION.SDK_INT >= 28 && object instanceof Impl28) {
            this.mImpl = new Impl28(this, (Impl28)object);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21 && object instanceof Impl21) {
            this.mImpl = new Impl21(this, (Impl21)object);
            return;
        }
        if (Build.VERSION.SDK_INT >= 20 && object instanceof Impl20) {
            this.mImpl = new Impl20(this, (Impl20)object);
            return;
        }
        this.mImpl = new Impl(this);
    }

    static Insets insetInsets(Insets insets, int n, int n2, int n3, int n4) {
        int n5 = Math.max(0, insets.left - n);
        int n6 = Math.max(0, insets.top - n2);
        int n7 = Math.max(0, insets.right - n3);
        int n8 = Math.max(0, insets.bottom - n4);
        if (n5 != n) return Insets.of(n5, n6, n7, n8);
        if (n6 != n2) return Insets.of(n5, n6, n7, n8);
        if (n7 != n3) return Insets.of(n5, n6, n7, n8);
        if (n8 != n4) return Insets.of(n5, n6, n7, n8);
        return insets;
    }

    public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets windowInsets) {
        return new WindowInsetsCompat(Preconditions.checkNotNull(windowInsets));
    }

    public WindowInsetsCompat consumeDisplayCutout() {
        return this.mImpl.consumeDisplayCutout();
    }

    public WindowInsetsCompat consumeStableInsets() {
        return this.mImpl.consumeStableInsets();
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return this.mImpl.consumeSystemWindowInsets();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WindowInsetsCompat)) {
            return false;
        }
        object = (WindowInsetsCompat)object;
        return ObjectsCompat.equals(this.mImpl, ((WindowInsetsCompat)object).mImpl);
    }

    public DisplayCutoutCompat getDisplayCutout() {
        return this.mImpl.getDisplayCutout();
    }

    public Insets getMandatorySystemGestureInsets() {
        return this.mImpl.getMandatorySystemGestureInsets();
    }

    public int getStableInsetBottom() {
        return this.getStableInsets().bottom;
    }

    public int getStableInsetLeft() {
        return this.getStableInsets().left;
    }

    public int getStableInsetRight() {
        return this.getStableInsets().right;
    }

    public int getStableInsetTop() {
        return this.getStableInsets().top;
    }

    public Insets getStableInsets() {
        return this.mImpl.getStableInsets();
    }

    public Insets getSystemGestureInsets() {
        return this.mImpl.getSystemGestureInsets();
    }

    public int getSystemWindowInsetBottom() {
        return this.getSystemWindowInsets().bottom;
    }

    public int getSystemWindowInsetLeft() {
        return this.getSystemWindowInsets().left;
    }

    public int getSystemWindowInsetRight() {
        return this.getSystemWindowInsets().right;
    }

    public int getSystemWindowInsetTop() {
        return this.getSystemWindowInsets().top;
    }

    public Insets getSystemWindowInsets() {
        return this.mImpl.getSystemWindowInsets();
    }

    public Insets getTappableElementInsets() {
        return this.mImpl.getTappableElementInsets();
    }

    public boolean hasInsets() {
        if (this.hasSystemWindowInsets()) return true;
        if (this.hasStableInsets()) return true;
        if (this.getDisplayCutout() != null) return true;
        if (!this.getSystemGestureInsets().equals(Insets.NONE)) return true;
        if (!this.getMandatorySystemGestureInsets().equals(Insets.NONE)) return true;
        if (!this.getTappableElementInsets().equals(Insets.NONE)) return true;
        return false;
    }

    public boolean hasStableInsets() {
        return this.getStableInsets().equals(Insets.NONE) ^ true;
    }

    public boolean hasSystemWindowInsets() {
        return this.getSystemWindowInsets().equals(Insets.NONE) ^ true;
    }

    public int hashCode() {
        Impl impl = this.mImpl;
        if (impl != null) return impl.hashCode();
        return 0;
    }

    public WindowInsetsCompat inset(int n, int n2, int n3, int n4) {
        return this.mImpl.inset(n, n2, n3, n4);
    }

    public WindowInsetsCompat inset(Insets insets) {
        return this.inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public boolean isConsumed() {
        return this.mImpl.isConsumed();
    }

    public boolean isRound() {
        return this.mImpl.isRound();
    }

    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(int n, int n2, int n3, int n4) {
        return new Builder(this).setSystemWindowInsets(Insets.of(n, n2, n3, n4)).build();
    }

    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(Rect rect) {
        return new Builder(this).setSystemWindowInsets(Insets.of(rect)).build();
    }

    public WindowInsets toWindowInsets() {
        Impl impl = this.mImpl;
        if (!(impl instanceof Impl20)) return null;
        return ((Impl20)impl).mPlatformInsets;
    }

    public static final class Builder {
        private final BuilderImpl mImpl;

        public Builder() {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29();
                return;
            }
            if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20();
                return;
            }
            this.mImpl = new BuilderImpl();
        }

        public Builder(WindowInsetsCompat windowInsetsCompat) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mImpl = new BuilderImpl29(windowInsetsCompat);
                return;
            }
            if (Build.VERSION.SDK_INT >= 20) {
                this.mImpl = new BuilderImpl20(windowInsetsCompat);
                return;
            }
            this.mImpl = new BuilderImpl(windowInsetsCompat);
        }

        public WindowInsetsCompat build() {
            return this.mImpl.build();
        }

        public Builder setDisplayCutout(DisplayCutoutCompat displayCutoutCompat) {
            this.mImpl.setDisplayCutout(displayCutoutCompat);
            return this;
        }

        public Builder setMandatorySystemGestureInsets(Insets insets) {
            this.mImpl.setMandatorySystemGestureInsets(insets);
            return this;
        }

        public Builder setStableInsets(Insets insets) {
            this.mImpl.setStableInsets(insets);
            return this;
        }

        public Builder setSystemGestureInsets(Insets insets) {
            this.mImpl.setSystemGestureInsets(insets);
            return this;
        }

        public Builder setSystemWindowInsets(Insets insets) {
            this.mImpl.setSystemWindowInsets(insets);
            return this;
        }

        public Builder setTappableElementInsets(Insets insets) {
            this.mImpl.setTappableElementInsets(insets);
            return this;
        }
    }

    private static class BuilderImpl {
        private final WindowInsetsCompat mInsets;

        BuilderImpl() {
            this(new WindowInsetsCompat((WindowInsetsCompat)null));
        }

        BuilderImpl(WindowInsetsCompat windowInsetsCompat) {
            this.mInsets = windowInsetsCompat;
        }

        WindowInsetsCompat build() {
            return this.mInsets;
        }

        void setDisplayCutout(DisplayCutoutCompat displayCutoutCompat) {
        }

        void setMandatorySystemGestureInsets(Insets insets) {
        }

        void setStableInsets(Insets insets) {
        }

        void setSystemGestureInsets(Insets insets) {
        }

        void setSystemWindowInsets(Insets insets) {
        }

        void setTappableElementInsets(Insets insets) {
        }
    }

    private static class BuilderImpl20
    extends BuilderImpl {
        private static Constructor<WindowInsets> sConstructor;
        private static boolean sConstructorFetched = false;
        private static Field sConsumedField;
        private static boolean sConsumedFieldFetched = false;
        private WindowInsets mInsets;

        BuilderImpl20() {
            this.mInsets = BuilderImpl20.createWindowInsetsInstance();
        }

        BuilderImpl20(WindowInsetsCompat windowInsetsCompat) {
            this.mInsets = windowInsetsCompat.toWindowInsets();
        }

        private static WindowInsets createWindowInsetsInstance() {
            AccessibleObject accessibleObject;
            if (!sConsumedFieldFetched) {
                try {
                    sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    Log.i((String)WindowInsetsCompat.TAG, (String)"Could not retrieve WindowInsets.CONSUMED field", (Throwable)reflectiveOperationException);
                }
                sConsumedFieldFetched = true;
            }
            if ((accessibleObject = sConsumedField) != null) {
                try {
                    accessibleObject = (WindowInsets)((Field)accessibleObject).get(null);
                    if (accessibleObject != null) {
                        return new WindowInsets((WindowInsets)accessibleObject);
                    }
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    Log.i((String)WindowInsetsCompat.TAG, (String)"Could not get value from WindowInsets.CONSUMED field", (Throwable)reflectiveOperationException);
                }
            }
            if (!sConstructorFetched) {
                try {
                    sConstructor = WindowInsets.class.getConstructor(Rect.class);
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    Log.i((String)WindowInsetsCompat.TAG, (String)"Could not retrieve WindowInsets(Rect) constructor", (Throwable)reflectiveOperationException);
                }
                sConstructorFetched = true;
            }
            if ((accessibleObject = sConstructor) == null) return null;
            try {
                Rect rect = new Rect();
                return (WindowInsets)((Constructor)accessibleObject).newInstance(new Object[]{rect});
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                Log.i((String)WindowInsetsCompat.TAG, (String)"Could not invoke WindowInsets(Rect) constructor", (Throwable)reflectiveOperationException);
            }
            return null;
        }

        @Override
        WindowInsetsCompat build() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mInsets);
        }

        @Override
        void setSystemWindowInsets(Insets insets) {
            WindowInsets windowInsets = this.mInsets;
            if (windowInsets == null) return;
            this.mInsets = windowInsets.replaceSystemWindowInsets(insets.left, insets.top, insets.right, insets.bottom);
        }
    }

    private static class BuilderImpl29
    extends BuilderImpl {
        final WindowInsets.Builder mPlatBuilder;

        BuilderImpl29() {
            this.mPlatBuilder = new WindowInsets.Builder();
        }

        BuilderImpl29(WindowInsetsCompat windowInsetsCompat) {
            windowInsetsCompat = windowInsetsCompat.toWindowInsets();
            windowInsetsCompat = windowInsetsCompat != null ? new WindowInsets.Builder((WindowInsets)windowInsetsCompat) : new WindowInsets.Builder();
            this.mPlatBuilder = windowInsetsCompat;
        }

        @Override
        WindowInsetsCompat build() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
        }

        @Override
        void setDisplayCutout(DisplayCutoutCompat displayCutoutCompat) {
            WindowInsets.Builder builder = this.mPlatBuilder;
            displayCutoutCompat = displayCutoutCompat != null ? displayCutoutCompat.unwrap() : null;
            builder.setDisplayCutout((DisplayCutout)displayCutoutCompat);
        }

        @Override
        void setMandatorySystemGestureInsets(Insets insets) {
            this.mPlatBuilder.setMandatorySystemGestureInsets(insets.toPlatformInsets());
        }

        @Override
        void setStableInsets(Insets insets) {
            this.mPlatBuilder.setStableInsets(insets.toPlatformInsets());
        }

        @Override
        void setSystemGestureInsets(Insets insets) {
            this.mPlatBuilder.setSystemGestureInsets(insets.toPlatformInsets());
        }

        @Override
        void setSystemWindowInsets(Insets insets) {
            this.mPlatBuilder.setSystemWindowInsets(insets.toPlatformInsets());
        }

        @Override
        void setTappableElementInsets(Insets insets) {
            this.mPlatBuilder.setTappableElementInsets(insets.toPlatformInsets());
        }
    }

    private static class Impl {
        final WindowInsetsCompat mHost;

        Impl(WindowInsetsCompat windowInsetsCompat) {
            this.mHost = windowInsetsCompat;
        }

        WindowInsetsCompat consumeDisplayCutout() {
            return this.mHost;
        }

        WindowInsetsCompat consumeStableInsets() {
            return this.mHost;
        }

        WindowInsetsCompat consumeSystemWindowInsets() {
            return this.mHost;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Impl)) {
                return false;
            }
            object = (Impl)object;
            if (this.isRound() != ((Impl)object).isRound()) return false;
            if (this.isConsumed() != ((Impl)object).isConsumed()) return false;
            if (!ObjectsCompat.equals(this.getSystemWindowInsets(), ((Impl)object).getSystemWindowInsets())) return false;
            if (!ObjectsCompat.equals(this.getStableInsets(), ((Impl)object).getStableInsets())) return false;
            if (!ObjectsCompat.equals(this.getDisplayCutout(), ((Impl)object).getDisplayCutout())) return false;
            return bl;
        }

        DisplayCutoutCompat getDisplayCutout() {
            return null;
        }

        Insets getMandatorySystemGestureInsets() {
            return this.getSystemWindowInsets();
        }

        Insets getStableInsets() {
            return Insets.NONE;
        }

        Insets getSystemGestureInsets() {
            return this.getSystemWindowInsets();
        }

        Insets getSystemWindowInsets() {
            return Insets.NONE;
        }

        Insets getTappableElementInsets() {
            return this.getSystemWindowInsets();
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.isRound(), this.isConsumed(), this.getSystemWindowInsets(), this.getStableInsets(), this.getDisplayCutout());
        }

        WindowInsetsCompat inset(int n, int n2, int n3, int n4) {
            return CONSUMED;
        }

        boolean isConsumed() {
            return false;
        }

        boolean isRound() {
            return false;
        }
    }

    private static class Impl20
    extends Impl {
        final WindowInsets mPlatformInsets;
        private Insets mSystemWindowInsets = null;

        Impl20(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat);
            this.mPlatformInsets = windowInsets;
        }

        Impl20(WindowInsetsCompat windowInsetsCompat, Impl20 impl20) {
            this(windowInsetsCompat, new WindowInsets(impl20.mPlatformInsets));
        }

        @Override
        final Insets getSystemWindowInsets() {
            if (this.mSystemWindowInsets != null) return this.mSystemWindowInsets;
            this.mSystemWindowInsets = Insets.of(this.mPlatformInsets.getSystemWindowInsetLeft(), this.mPlatformInsets.getSystemWindowInsetTop(), this.mPlatformInsets.getSystemWindowInsetRight(), this.mPlatformInsets.getSystemWindowInsetBottom());
            return this.mSystemWindowInsets;
        }

        @Override
        WindowInsetsCompat inset(int n, int n2, int n3, int n4) {
            Builder builder = new Builder(WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets));
            builder.setSystemWindowInsets(WindowInsetsCompat.insetInsets(this.getSystemWindowInsets(), n, n2, n3, n4));
            builder.setStableInsets(WindowInsetsCompat.insetInsets(this.getStableInsets(), n, n2, n3, n4));
            return builder.build();
        }

        @Override
        boolean isRound() {
            return this.mPlatformInsets.isRound();
        }
    }

    private static class Impl21
    extends Impl20 {
        private Insets mStableInsets = null;

        Impl21(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        Impl21(WindowInsetsCompat windowInsetsCompat, Impl21 impl21) {
            super(windowInsetsCompat, impl21);
        }

        @Override
        WindowInsetsCompat consumeStableInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeStableInsets());
        }

        @Override
        WindowInsetsCompat consumeSystemWindowInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeSystemWindowInsets());
        }

        @Override
        final Insets getStableInsets() {
            if (this.mStableInsets != null) return this.mStableInsets;
            this.mStableInsets = Insets.of(this.mPlatformInsets.getStableInsetLeft(), this.mPlatformInsets.getStableInsetTop(), this.mPlatformInsets.getStableInsetRight(), this.mPlatformInsets.getStableInsetBottom());
            return this.mStableInsets;
        }

        @Override
        boolean isConsumed() {
            return this.mPlatformInsets.isConsumed();
        }
    }

    private static class Impl28
    extends Impl21 {
        Impl28(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        Impl28(WindowInsetsCompat windowInsetsCompat, Impl28 impl28) {
            super(windowInsetsCompat, impl28);
        }

        @Override
        WindowInsetsCompat consumeDisplayCutout() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeDisplayCutout());
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Impl28)) {
                return false;
            }
            object = (Impl28)object;
            return Objects.equals((Object)this.mPlatformInsets, (Object)((Impl28)object).mPlatformInsets);
        }

        @Override
        DisplayCutoutCompat getDisplayCutout() {
            return DisplayCutoutCompat.wrap((Object)this.mPlatformInsets.getDisplayCutout());
        }

        @Override
        public int hashCode() {
            return this.mPlatformInsets.hashCode();
        }
    }

    private static class Impl29
    extends Impl28 {
        private Insets mMandatorySystemGestureInsets = null;
        private Insets mSystemGestureInsets = null;
        private Insets mTappableElementInsets = null;

        Impl29(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        Impl29(WindowInsetsCompat windowInsetsCompat, Impl29 impl29) {
            super(windowInsetsCompat, impl29);
        }

        @Override
        Insets getMandatorySystemGestureInsets() {
            if (this.mMandatorySystemGestureInsets != null) return this.mMandatorySystemGestureInsets;
            this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets());
            return this.mMandatorySystemGestureInsets;
        }

        @Override
        Insets getSystemGestureInsets() {
            if (this.mSystemGestureInsets != null) return this.mSystemGestureInsets;
            this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets());
            return this.mSystemGestureInsets;
        }

        @Override
        Insets getTappableElementInsets() {
            if (this.mTappableElementInsets != null) return this.mTappableElementInsets;
            this.mTappableElementInsets = Insets.toCompatInsets(this.mPlatformInsets.getTappableElementInsets());
            return this.mTappableElementInsets;
        }

        @Override
        WindowInsetsCompat inset(int n, int n2, int n3, int n4) {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.inset(n, n2, n3, n4));
        }
    }

}

