package androidx.core.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

public class WindowInsetsCompat {
   public static final WindowInsetsCompat CONSUMED = (new WindowInsetsCompat.Builder()).build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
   private static final String TAG = "WindowInsetsCompat";
   private final WindowInsetsCompat.Impl mImpl;

   private WindowInsetsCompat(WindowInsets var1) {
      if (VERSION.SDK_INT >= 29) {
         this.mImpl = new WindowInsetsCompat.Impl29(this, var1);
      } else if (VERSION.SDK_INT >= 28) {
         this.mImpl = new WindowInsetsCompat.Impl28(this, var1);
      } else if (VERSION.SDK_INT >= 21) {
         this.mImpl = new WindowInsetsCompat.Impl21(this, var1);
      } else if (VERSION.SDK_INT >= 20) {
         this.mImpl = new WindowInsetsCompat.Impl20(this, var1);
      } else {
         this.mImpl = new WindowInsetsCompat.Impl(this);
      }

   }

   public WindowInsetsCompat(WindowInsetsCompat var1) {
      if (var1 != null) {
         WindowInsetsCompat.Impl var2 = var1.mImpl;
         if (VERSION.SDK_INT >= 29 && var2 instanceof WindowInsetsCompat.Impl29) {
            this.mImpl = new WindowInsetsCompat.Impl29(this, (WindowInsetsCompat.Impl29)var2);
         } else if (VERSION.SDK_INT >= 28 && var2 instanceof WindowInsetsCompat.Impl28) {
            this.mImpl = new WindowInsetsCompat.Impl28(this, (WindowInsetsCompat.Impl28)var2);
         } else if (VERSION.SDK_INT >= 21 && var2 instanceof WindowInsetsCompat.Impl21) {
            this.mImpl = new WindowInsetsCompat.Impl21(this, (WindowInsetsCompat.Impl21)var2);
         } else if (VERSION.SDK_INT >= 20 && var2 instanceof WindowInsetsCompat.Impl20) {
            this.mImpl = new WindowInsetsCompat.Impl20(this, (WindowInsetsCompat.Impl20)var2);
         } else {
            this.mImpl = new WindowInsetsCompat.Impl(this);
         }
      } else {
         this.mImpl = new WindowInsetsCompat.Impl(this);
      }

   }

   static Insets insetInsets(Insets var0, int var1, int var2, int var3, int var4) {
      int var5 = Math.max(0, var0.left - var1);
      int var6 = Math.max(0, var0.top - var2);
      int var7 = Math.max(0, var0.right - var3);
      int var8 = Math.max(0, var0.bottom - var4);
      return var5 == var1 && var6 == var2 && var7 == var3 && var8 == var4 ? var0 : Insets.of(var5, var6, var7, var8);
   }

   public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets var0) {
      return new WindowInsetsCompat((WindowInsets)Preconditions.checkNotNull(var0));
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

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof WindowInsetsCompat)) {
         return false;
      } else {
         WindowInsetsCompat var2 = (WindowInsetsCompat)var1;
         return ObjectsCompat.equals(this.mImpl, var2.mImpl);
      }
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
      boolean var1;
      if (!this.hasSystemWindowInsets() && !this.hasStableInsets() && this.getDisplayCutout() == null && this.getSystemGestureInsets().equals(Insets.NONE) && this.getMandatorySystemGestureInsets().equals(Insets.NONE) && this.getTappableElementInsets().equals(Insets.NONE)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean hasStableInsets() {
      return this.getStableInsets().equals(Insets.NONE) ^ true;
   }

   public boolean hasSystemWindowInsets() {
      return this.getSystemWindowInsets().equals(Insets.NONE) ^ true;
   }

   public int hashCode() {
      WindowInsetsCompat.Impl var1 = this.mImpl;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return var2;
   }

   public WindowInsetsCompat inset(int var1, int var2, int var3, int var4) {
      return this.mImpl.inset(var1, var2, var3, var4);
   }

   public WindowInsetsCompat inset(Insets var1) {
      return this.inset(var1.left, var1.top, var1.right, var1.bottom);
   }

   public boolean isConsumed() {
      return this.mImpl.isConsumed();
   }

   public boolean isRound() {
      return this.mImpl.isRound();
   }

   @Deprecated
   public WindowInsetsCompat replaceSystemWindowInsets(int var1, int var2, int var3, int var4) {
      return (new WindowInsetsCompat.Builder(this)).setSystemWindowInsets(Insets.of(var1, var2, var3, var4)).build();
   }

   @Deprecated
   public WindowInsetsCompat replaceSystemWindowInsets(Rect var1) {
      return (new WindowInsetsCompat.Builder(this)).setSystemWindowInsets(Insets.of(var1)).build();
   }

   public WindowInsets toWindowInsets() {
      WindowInsetsCompat.Impl var1 = this.mImpl;
      WindowInsets var2;
      if (var1 instanceof WindowInsetsCompat.Impl20) {
         var2 = ((WindowInsetsCompat.Impl20)var1).mPlatformInsets;
      } else {
         var2 = null;
      }

      return var2;
   }

   public static final class Builder {
      private final WindowInsetsCompat.BuilderImpl mImpl;

      public Builder() {
         if (VERSION.SDK_INT >= 29) {
            this.mImpl = new WindowInsetsCompat.BuilderImpl29();
         } else if (VERSION.SDK_INT >= 20) {
            this.mImpl = new WindowInsetsCompat.BuilderImpl20();
         } else {
            this.mImpl = new WindowInsetsCompat.BuilderImpl();
         }

      }

      public Builder(WindowInsetsCompat var1) {
         if (VERSION.SDK_INT >= 29) {
            this.mImpl = new WindowInsetsCompat.BuilderImpl29(var1);
         } else if (VERSION.SDK_INT >= 20) {
            this.mImpl = new WindowInsetsCompat.BuilderImpl20(var1);
         } else {
            this.mImpl = new WindowInsetsCompat.BuilderImpl(var1);
         }

      }

      public WindowInsetsCompat build() {
         return this.mImpl.build();
      }

      public WindowInsetsCompat.Builder setDisplayCutout(DisplayCutoutCompat var1) {
         this.mImpl.setDisplayCutout(var1);
         return this;
      }

      public WindowInsetsCompat.Builder setMandatorySystemGestureInsets(Insets var1) {
         this.mImpl.setMandatorySystemGestureInsets(var1);
         return this;
      }

      public WindowInsetsCompat.Builder setStableInsets(Insets var1) {
         this.mImpl.setStableInsets(var1);
         return this;
      }

      public WindowInsetsCompat.Builder setSystemGestureInsets(Insets var1) {
         this.mImpl.setSystemGestureInsets(var1);
         return this;
      }

      public WindowInsetsCompat.Builder setSystemWindowInsets(Insets var1) {
         this.mImpl.setSystemWindowInsets(var1);
         return this;
      }

      public WindowInsetsCompat.Builder setTappableElementInsets(Insets var1) {
         this.mImpl.setTappableElementInsets(var1);
         return this;
      }
   }

   private static class BuilderImpl {
      private final WindowInsetsCompat mInsets;

      BuilderImpl() {
         this(new WindowInsetsCompat((WindowInsetsCompat)null));
      }

      BuilderImpl(WindowInsetsCompat var1) {
         this.mInsets = var1;
      }

      WindowInsetsCompat build() {
         return this.mInsets;
      }

      void setDisplayCutout(DisplayCutoutCompat var1) {
      }

      void setMandatorySystemGestureInsets(Insets var1) {
      }

      void setStableInsets(Insets var1) {
      }

      void setSystemGestureInsets(Insets var1) {
      }

      void setSystemWindowInsets(Insets var1) {
      }

      void setTappableElementInsets(Insets var1) {
      }
   }

   private static class BuilderImpl20 extends WindowInsetsCompat.BuilderImpl {
      private static Constructor<WindowInsets> sConstructor;
      private static boolean sConstructorFetched;
      private static Field sConsumedField;
      private static boolean sConsumedFieldFetched;
      private WindowInsets mInsets;

      BuilderImpl20() {
         this.mInsets = createWindowInsetsInstance();
      }

      BuilderImpl20(WindowInsetsCompat var1) {
         this.mInsets = var1.toWindowInsets();
      }

      private static WindowInsets createWindowInsetsInstance() {
         if (!sConsumedFieldFetched) {
            try {
               sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
            } catch (ReflectiveOperationException var3) {
               Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets.CONSUMED field", var3);
            }

            sConsumedFieldFetched = true;
         }

         Field var0 = sConsumedField;
         WindowInsets var7;
         if (var0 != null) {
            label54: {
               ReflectiveOperationException var10000;
               label59: {
                  boolean var10001;
                  try {
                     var7 = (WindowInsets)var0.get((Object)null);
                  } catch (ReflectiveOperationException var6) {
                     var10000 = var6;
                     var10001 = false;
                     break label59;
                  }

                  if (var7 == null) {
                     break label54;
                  }

                  try {
                     var7 = new WindowInsets(var7);
                     return var7;
                  } catch (ReflectiveOperationException var5) {
                     var10000 = var5;
                     var10001 = false;
                  }
               }

               ReflectiveOperationException var8 = var10000;
               Log.i("WindowInsetsCompat", "Could not get value from WindowInsets.CONSUMED field", var8);
            }
         }

         if (!sConstructorFetched) {
            try {
               sConstructor = WindowInsets.class.getConstructor(Rect.class);
            } catch (ReflectiveOperationException var2) {
               Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets(Rect) constructor", var2);
            }

            sConstructorFetched = true;
         }

         Constructor var9 = sConstructor;
         if (var9 != null) {
            try {
               Rect var1 = new Rect();
               var7 = (WindowInsets)var9.newInstance(var1);
               return var7;
            } catch (ReflectiveOperationException var4) {
               Log.i("WindowInsetsCompat", "Could not invoke WindowInsets(Rect) constructor", var4);
            }
         }

         return null;
      }

      WindowInsetsCompat build() {
         return WindowInsetsCompat.toWindowInsetsCompat(this.mInsets);
      }

      void setSystemWindowInsets(Insets var1) {
         WindowInsets var2 = this.mInsets;
         if (var2 != null) {
            this.mInsets = var2.replaceSystemWindowInsets(var1.left, var1.top, var1.right, var1.bottom);
         }

      }
   }

   private static class BuilderImpl29 extends WindowInsetsCompat.BuilderImpl {
      final android.view.WindowInsets.Builder mPlatBuilder;

      BuilderImpl29() {
         this.mPlatBuilder = new android.view.WindowInsets.Builder();
      }

      BuilderImpl29(WindowInsetsCompat var1) {
         WindowInsets var2 = var1.toWindowInsets();
         android.view.WindowInsets.Builder var3;
         if (var2 != null) {
            var3 = new android.view.WindowInsets.Builder(var2);
         } else {
            var3 = new android.view.WindowInsets.Builder();
         }

         this.mPlatBuilder = var3;
      }

      WindowInsetsCompat build() {
         return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
      }

      void setDisplayCutout(DisplayCutoutCompat var1) {
         android.view.WindowInsets.Builder var2 = this.mPlatBuilder;
         DisplayCutout var3;
         if (var1 != null) {
            var3 = var1.unwrap();
         } else {
            var3 = null;
         }

         var2.setDisplayCutout(var3);
      }

      void setMandatorySystemGestureInsets(Insets var1) {
         this.mPlatBuilder.setMandatorySystemGestureInsets(var1.toPlatformInsets());
      }

      void setStableInsets(Insets var1) {
         this.mPlatBuilder.setStableInsets(var1.toPlatformInsets());
      }

      void setSystemGestureInsets(Insets var1) {
         this.mPlatBuilder.setSystemGestureInsets(var1.toPlatformInsets());
      }

      void setSystemWindowInsets(Insets var1) {
         this.mPlatBuilder.setSystemWindowInsets(var1.toPlatformInsets());
      }

      void setTappableElementInsets(Insets var1) {
         this.mPlatBuilder.setTappableElementInsets(var1.toPlatformInsets());
      }
   }

   private static class Impl {
      final WindowInsetsCompat mHost;

      Impl(WindowInsetsCompat var1) {
         this.mHost = var1;
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

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof WindowInsetsCompat.Impl)) {
            return false;
         } else {
            WindowInsetsCompat.Impl var3 = (WindowInsetsCompat.Impl)var1;
            if (this.isRound() != var3.isRound() || this.isConsumed() != var3.isConsumed() || !ObjectsCompat.equals(this.getSystemWindowInsets(), var3.getSystemWindowInsets()) || !ObjectsCompat.equals(this.getStableInsets(), var3.getStableInsets()) || !ObjectsCompat.equals(this.getDisplayCutout(), var3.getDisplayCutout())) {
               var2 = false;
            }

            return var2;
         }
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

      WindowInsetsCompat inset(int var1, int var2, int var3, int var4) {
         return WindowInsetsCompat.CONSUMED;
      }

      boolean isConsumed() {
         return false;
      }

      boolean isRound() {
         return false;
      }
   }

   private static class Impl20 extends WindowInsetsCompat.Impl {
      final WindowInsets mPlatformInsets;
      private Insets mSystemWindowInsets;

      Impl20(WindowInsetsCompat var1, WindowInsets var2) {
         super(var1);
         this.mSystemWindowInsets = null;
         this.mPlatformInsets = var2;
      }

      Impl20(WindowInsetsCompat var1, WindowInsetsCompat.Impl20 var2) {
         this(var1, new WindowInsets(var2.mPlatformInsets));
      }

      final Insets getSystemWindowInsets() {
         if (this.mSystemWindowInsets == null) {
            this.mSystemWindowInsets = Insets.of(this.mPlatformInsets.getSystemWindowInsetLeft(), this.mPlatformInsets.getSystemWindowInsetTop(), this.mPlatformInsets.getSystemWindowInsetRight(), this.mPlatformInsets.getSystemWindowInsetBottom());
         }

         return this.mSystemWindowInsets;
      }

      WindowInsetsCompat inset(int var1, int var2, int var3, int var4) {
         WindowInsetsCompat.Builder var5 = new WindowInsetsCompat.Builder(WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets));
         var5.setSystemWindowInsets(WindowInsetsCompat.insetInsets(this.getSystemWindowInsets(), var1, var2, var3, var4));
         var5.setStableInsets(WindowInsetsCompat.insetInsets(this.getStableInsets(), var1, var2, var3, var4));
         return var5.build();
      }

      boolean isRound() {
         return this.mPlatformInsets.isRound();
      }
   }

   private static class Impl21 extends WindowInsetsCompat.Impl20 {
      private Insets mStableInsets = null;

      Impl21(WindowInsetsCompat var1, WindowInsets var2) {
         super(var1, var2);
      }

      Impl21(WindowInsetsCompat var1, WindowInsetsCompat.Impl21 var2) {
         super(var1, (WindowInsetsCompat.Impl20)var2);
      }

      WindowInsetsCompat consumeStableInsets() {
         return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeStableInsets());
      }

      WindowInsetsCompat consumeSystemWindowInsets() {
         return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeSystemWindowInsets());
      }

      final Insets getStableInsets() {
         if (this.mStableInsets == null) {
            this.mStableInsets = Insets.of(this.mPlatformInsets.getStableInsetLeft(), this.mPlatformInsets.getStableInsetTop(), this.mPlatformInsets.getStableInsetRight(), this.mPlatformInsets.getStableInsetBottom());
         }

         return this.mStableInsets;
      }

      boolean isConsumed() {
         return this.mPlatformInsets.isConsumed();
      }
   }

   private static class Impl28 extends WindowInsetsCompat.Impl21 {
      Impl28(WindowInsetsCompat var1, WindowInsets var2) {
         super(var1, var2);
      }

      Impl28(WindowInsetsCompat var1, WindowInsetsCompat.Impl28 var2) {
         super(var1, (WindowInsetsCompat.Impl21)var2);
      }

      WindowInsetsCompat consumeDisplayCutout() {
         return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeDisplayCutout());
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof WindowInsetsCompat.Impl28)) {
            return false;
         } else {
            WindowInsetsCompat.Impl28 var2 = (WindowInsetsCompat.Impl28)var1;
            return Objects.equals(this.mPlatformInsets, var2.mPlatformInsets);
         }
      }

      DisplayCutoutCompat getDisplayCutout() {
         return DisplayCutoutCompat.wrap(this.mPlatformInsets.getDisplayCutout());
      }

      public int hashCode() {
         return this.mPlatformInsets.hashCode();
      }
   }

   private static class Impl29 extends WindowInsetsCompat.Impl28 {
      private Insets mMandatorySystemGestureInsets = null;
      private Insets mSystemGestureInsets = null;
      private Insets mTappableElementInsets = null;

      Impl29(WindowInsetsCompat var1, WindowInsets var2) {
         super(var1, var2);
      }

      Impl29(WindowInsetsCompat var1, WindowInsetsCompat.Impl29 var2) {
         super(var1, (WindowInsetsCompat.Impl28)var2);
      }

      Insets getMandatorySystemGestureInsets() {
         if (this.mMandatorySystemGestureInsets == null) {
            this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets());
         }

         return this.mMandatorySystemGestureInsets;
      }

      Insets getSystemGestureInsets() {
         if (this.mSystemGestureInsets == null) {
            this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets());
         }

         return this.mSystemGestureInsets;
      }

      Insets getTappableElementInsets() {
         if (this.mTappableElementInsets == null) {
            this.mTappableElementInsets = Insets.toCompatInsets(this.mPlatformInsets.getTappableElementInsets());
         }

         return this.mTappableElementInsets;
      }

      WindowInsetsCompat inset(int var1, int var2, int var3, int var4) {
         return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.inset(var1, var2, var3, var4));
      }
   }
}
