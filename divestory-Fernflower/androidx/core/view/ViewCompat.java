package androidx.core.view;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.View.AccessibilityDelegate;
import android.view.View.DragShadowBuilder;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnUnhandledKeyEventListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.collection.SimpleArrayMap;
import androidx.core.R;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewCompat {
   private static final int[] ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
   public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
   public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
   public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
   public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
   public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
   public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
   public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
   @Deprecated
   public static final int LAYER_TYPE_HARDWARE = 2;
   @Deprecated
   public static final int LAYER_TYPE_NONE = 0;
   @Deprecated
   public static final int LAYER_TYPE_SOFTWARE = 1;
   public static final int LAYOUT_DIRECTION_INHERIT = 2;
   public static final int LAYOUT_DIRECTION_LOCALE = 3;
   public static final int LAYOUT_DIRECTION_LTR = 0;
   public static final int LAYOUT_DIRECTION_RTL = 1;
   @Deprecated
   public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
   @Deprecated
   public static final int MEASURED_SIZE_MASK = 16777215;
   @Deprecated
   public static final int MEASURED_STATE_MASK = -16777216;
   @Deprecated
   public static final int MEASURED_STATE_TOO_SMALL = 16777216;
   @Deprecated
   public static final int OVER_SCROLL_ALWAYS = 0;
   @Deprecated
   public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
   @Deprecated
   public static final int OVER_SCROLL_NEVER = 2;
   public static final int SCROLL_AXIS_HORIZONTAL = 1;
   public static final int SCROLL_AXIS_NONE = 0;
   public static final int SCROLL_AXIS_VERTICAL = 2;
   public static final int SCROLL_INDICATOR_BOTTOM = 2;
   public static final int SCROLL_INDICATOR_END = 32;
   public static final int SCROLL_INDICATOR_LEFT = 4;
   public static final int SCROLL_INDICATOR_RIGHT = 8;
   public static final int SCROLL_INDICATOR_START = 16;
   public static final int SCROLL_INDICATOR_TOP = 1;
   private static final String TAG = "ViewCompat";
   public static final int TYPE_NON_TOUCH = 1;
   public static final int TYPE_TOUCH = 0;
   private static boolean sAccessibilityDelegateCheckFailed = false;
   private static Field sAccessibilityDelegateField;
   private static ViewCompat.AccessibilityPaneVisibilityManager sAccessibilityPaneVisibilityManager;
   private static Method sChildrenDrawingOrderMethod;
   private static Method sDispatchFinishTemporaryDetach;
   private static Method sDispatchStartTemporaryDetach;
   private static Field sMinHeightField;
   private static boolean sMinHeightFieldFetched;
   private static Field sMinWidthField;
   private static boolean sMinWidthFieldFetched;
   private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
   private static boolean sTempDetachBound;
   private static ThreadLocal<Rect> sThreadLocalRect;
   private static WeakHashMap<View, String> sTransitionNameMap;
   private static WeakHashMap<View, ViewPropertyAnimatorCompat> sViewPropertyAnimatorMap = null;

   static {
      ACCESSIBILITY_ACTIONS_RESOURCE_IDS = new int[]{R.id.accessibility_custom_action_0, R.id.accessibility_custom_action_1, R.id.accessibility_custom_action_2, R.id.accessibility_custom_action_3, R.id.accessibility_custom_action_4, R.id.accessibility_custom_action_5, R.id.accessibility_custom_action_6, R.id.accessibility_custom_action_7, R.id.accessibility_custom_action_8, R.id.accessibility_custom_action_9, R.id.accessibility_custom_action_10, R.id.accessibility_custom_action_11, R.id.accessibility_custom_action_12, R.id.accessibility_custom_action_13, R.id.accessibility_custom_action_14, R.id.accessibility_custom_action_15, R.id.accessibility_custom_action_16, R.id.accessibility_custom_action_17, R.id.accessibility_custom_action_18, R.id.accessibility_custom_action_19, R.id.accessibility_custom_action_20, R.id.accessibility_custom_action_21, R.id.accessibility_custom_action_22, R.id.accessibility_custom_action_23, R.id.accessibility_custom_action_24, R.id.accessibility_custom_action_25, R.id.accessibility_custom_action_26, R.id.accessibility_custom_action_27, R.id.accessibility_custom_action_28, R.id.accessibility_custom_action_29, R.id.accessibility_custom_action_30, R.id.accessibility_custom_action_31};
      sAccessibilityPaneVisibilityManager = new ViewCompat.AccessibilityPaneVisibilityManager();
   }

   protected ViewCompat() {
   }

   private static ViewCompat.AccessibilityViewProperty<Boolean> accessibilityHeadingProperty() {
      return new ViewCompat.AccessibilityViewProperty<Boolean>(R.id.tag_accessibility_heading, Boolean.class, 28) {
         Boolean frameworkGet(View var1) {
            return var1.isAccessibilityHeading();
         }

         void frameworkSet(View var1, Boolean var2) {
            var1.setAccessibilityHeading(var2);
         }

         boolean shouldUpdate(Boolean var1, Boolean var2) {
            return this.booleanNullToFalseEquals(var1, var2) ^ true;
         }
      };
   }

   public static int addAccessibilityAction(View var0, CharSequence var1, AccessibilityViewCommand var2) {
      int var3 = getAvailableActionIdFromResources(var0);
      if (var3 != -1) {
         addAccessibilityAction(var0, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var3, var1, var2));
      }

      return var3;
   }

   private static void addAccessibilityAction(View var0, AccessibilityNodeInfoCompat.AccessibilityActionCompat var1) {
      if (VERSION.SDK_INT >= 21) {
         getOrCreateAccessibilityDelegateCompat(var0);
         removeActionWithId(var1.getId(), var0);
         getActionList(var0).add(var1);
         notifyViewAccessibilityStateChangedIfNeeded(var0, 0);
      }

   }

   public static void addKeyboardNavigationClusters(View var0, Collection<View> var1, int var2) {
      if (VERSION.SDK_INT >= 26) {
         var0.addKeyboardNavigationClusters(var1, var2);
      }

   }

   public static void addOnUnhandledKeyEventListener(View var0, final ViewCompat.OnUnhandledKeyEventListenerCompat var1) {
      if (VERSION.SDK_INT >= 28) {
         SimpleArrayMap var4 = (SimpleArrayMap)var0.getTag(R.id.tag_unhandled_key_listeners);
         SimpleArrayMap var6 = var4;
         if (var4 == null) {
            var6 = new SimpleArrayMap();
            var0.setTag(R.id.tag_unhandled_key_listeners, var6);
         }

         OnUnhandledKeyEventListener var5 = new OnUnhandledKeyEventListener() {
            public boolean onUnhandledKeyEvent(View var1x, KeyEvent var2) {
               return var1.onUnhandledKeyEvent(var1x, var2);
            }
         };
         var6.put(var1, var5);
         var0.addOnUnhandledKeyEventListener(var5);
      } else {
         ArrayList var2 = (ArrayList)var0.getTag(R.id.tag_unhandled_key_listeners);
         ArrayList var3 = var2;
         if (var2 == null) {
            var3 = new ArrayList();
            var0.setTag(R.id.tag_unhandled_key_listeners, var3);
         }

         var3.add(var1);
         if (var3.size() == 1) {
            ViewCompat.UnhandledKeyEventManager.registerListeningView(var0);
         }

      }
   }

   public static ViewPropertyAnimatorCompat animate(View var0) {
      if (sViewPropertyAnimatorMap == null) {
         sViewPropertyAnimatorMap = new WeakHashMap();
      }

      ViewPropertyAnimatorCompat var1 = (ViewPropertyAnimatorCompat)sViewPropertyAnimatorMap.get(var0);
      ViewPropertyAnimatorCompat var2 = var1;
      if (var1 == null) {
         var2 = new ViewPropertyAnimatorCompat(var0);
         sViewPropertyAnimatorMap.put(var0, var2);
      }

      return var2;
   }

   private static void bindTempDetach() {
      try {
         sDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach");
         sDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach");
      } catch (NoSuchMethodException var1) {
         Log.e("ViewCompat", "Couldn't find method", var1);
      }

      sTempDetachBound = true;
   }

   @Deprecated
   public static boolean canScrollHorizontally(View var0, int var1) {
      return var0.canScrollHorizontally(var1);
   }

   @Deprecated
   public static boolean canScrollVertically(View var0, int var1) {
      return var0.canScrollVertically(var1);
   }

   public static void cancelDragAndDrop(View var0) {
      if (VERSION.SDK_INT >= 24) {
         var0.cancelDragAndDrop();
      }

   }

   @Deprecated
   public static int combineMeasuredStates(int var0, int var1) {
      return View.combineMeasuredStates(var0, var1);
   }

   private static void compatOffsetLeftAndRight(View var0, int var1) {
      var0.offsetLeftAndRight(var1);
      if (var0.getVisibility() == 0) {
         tickleInvalidationFlag(var0);
         ViewParent var2 = var0.getParent();
         if (var2 instanceof View) {
            tickleInvalidationFlag((View)var2);
         }
      }

   }

   private static void compatOffsetTopAndBottom(View var0, int var1) {
      var0.offsetTopAndBottom(var1);
      if (var0.getVisibility() == 0) {
         tickleInvalidationFlag(var0);
         ViewParent var2 = var0.getParent();
         if (var2 instanceof View) {
            tickleInvalidationFlag((View)var2);
         }
      }

   }

   public static WindowInsetsCompat computeSystemWindowInsets(View var0, WindowInsetsCompat var1, Rect var2) {
      return VERSION.SDK_INT >= 21 ? ViewCompat.Api21Impl.computeSystemWindowInsets(var0, var1, var2) : var1;
   }

   public static WindowInsetsCompat dispatchApplyWindowInsets(View var0, WindowInsetsCompat var1) {
      if (VERSION.SDK_INT >= 21) {
         WindowInsets var2 = var1.toWindowInsets();
         if (var2 != null && !var0.dispatchApplyWindowInsets(var2).equals(var2)) {
            return WindowInsetsCompat.toWindowInsetsCompat(var2);
         }
      }

      return var1;
   }

   public static void dispatchFinishTemporaryDetach(View var0) {
      if (VERSION.SDK_INT >= 24) {
         var0.dispatchFinishTemporaryDetach();
      } else {
         if (!sTempDetachBound) {
            bindTempDetach();
         }

         Method var1 = sDispatchFinishTemporaryDetach;
         if (var1 != null) {
            try {
               var1.invoke(var0);
            } catch (Exception var2) {
               Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", var2);
            }
         } else {
            var0.onFinishTemporaryDetach();
         }
      }

   }

   public static boolean dispatchNestedFling(View var0, float var1, float var2, boolean var3) {
      if (VERSION.SDK_INT >= 21) {
         return var0.dispatchNestedFling(var1, var2, var3);
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).dispatchNestedFling(var1, var2, var3) : false;
      }
   }

   public static boolean dispatchNestedPreFling(View var0, float var1, float var2) {
      if (VERSION.SDK_INT >= 21) {
         return var0.dispatchNestedPreFling(var1, var2);
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).dispatchNestedPreFling(var1, var2) : false;
      }
   }

   public static boolean dispatchNestedPreScroll(View var0, int var1, int var2, int[] var3, int[] var4) {
      if (VERSION.SDK_INT >= 21) {
         return var0.dispatchNestedPreScroll(var1, var2, var3, var4);
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).dispatchNestedPreScroll(var1, var2, var3, var4) : false;
      }
   }

   public static boolean dispatchNestedPreScroll(View var0, int var1, int var2, int[] var3, int[] var4, int var5) {
      if (var0 instanceof NestedScrollingChild2) {
         return ((NestedScrollingChild2)var0).dispatchNestedPreScroll(var1, var2, var3, var4, var5);
      } else {
         return var5 == 0 ? dispatchNestedPreScroll(var0, var1, var2, var3, var4) : false;
      }
   }

   public static void dispatchNestedScroll(View var0, int var1, int var2, int var3, int var4, int[] var5, int var6, int[] var7) {
      if (var0 instanceof NestedScrollingChild3) {
         ((NestedScrollingChild3)var0).dispatchNestedScroll(var1, var2, var3, var4, var5, var6, var7);
      } else {
         dispatchNestedScroll(var0, var1, var2, var3, var4, var5, var6);
      }

   }

   public static boolean dispatchNestedScroll(View var0, int var1, int var2, int var3, int var4, int[] var5) {
      if (VERSION.SDK_INT >= 21) {
         return var0.dispatchNestedScroll(var1, var2, var3, var4, var5);
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).dispatchNestedScroll(var1, var2, var3, var4, var5) : false;
      }
   }

   public static boolean dispatchNestedScroll(View var0, int var1, int var2, int var3, int var4, int[] var5, int var6) {
      if (var0 instanceof NestedScrollingChild2) {
         return ((NestedScrollingChild2)var0).dispatchNestedScroll(var1, var2, var3, var4, var5, var6);
      } else {
         return var6 == 0 ? dispatchNestedScroll(var0, var1, var2, var3, var4, var5) : false;
      }
   }

   public static void dispatchStartTemporaryDetach(View var0) {
      if (VERSION.SDK_INT >= 24) {
         var0.dispatchStartTemporaryDetach();
      } else {
         if (!sTempDetachBound) {
            bindTempDetach();
         }

         Method var1 = sDispatchStartTemporaryDetach;
         if (var1 != null) {
            try {
               var1.invoke(var0);
            } catch (Exception var2) {
               Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", var2);
            }
         } else {
            var0.onStartTemporaryDetach();
         }
      }

   }

   static boolean dispatchUnhandledKeyEventBeforeCallback(View var0, KeyEvent var1) {
      return VERSION.SDK_INT >= 28 ? false : ViewCompat.UnhandledKeyEventManager.at(var0).dispatch(var0, var1);
   }

   static boolean dispatchUnhandledKeyEventBeforeHierarchy(View var0, KeyEvent var1) {
      return VERSION.SDK_INT >= 28 ? false : ViewCompat.UnhandledKeyEventManager.at(var0).preDispatch(var1);
   }

   public static void enableAccessibleClickableSpanSupport(View var0) {
      if (VERSION.SDK_INT >= 19) {
         getOrCreateAccessibilityDelegateCompat(var0);
      }

   }

   public static int generateViewId() {
      if (VERSION.SDK_INT >= 17) {
         return View.generateViewId();
      } else {
         int var0;
         int var2;
         do {
            var0 = sNextGeneratedId.get();
            int var1 = var0 + 1;
            var2 = var1;
            if (var1 > 16777215) {
               var2 = 1;
            }
         } while(!sNextGeneratedId.compareAndSet(var0, var2));

         return var0;
      }
   }

   public static AccessibilityDelegateCompat getAccessibilityDelegate(View var0) {
      AccessibilityDelegate var1 = getAccessibilityDelegateInternal(var0);
      if (var1 == null) {
         return null;
      } else {
         return var1 instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter ? ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter)var1).mCompat : new AccessibilityDelegateCompat(var1);
      }
   }

   private static AccessibilityDelegate getAccessibilityDelegateInternal(View var0) {
      return VERSION.SDK_INT >= 29 ? var0.getAccessibilityDelegate() : getAccessibilityDelegateThroughReflection(var0);
   }

   private static AccessibilityDelegate getAccessibilityDelegateThroughReflection(View var0) {
      if (sAccessibilityDelegateCheckFailed) {
         return null;
      } else {
         if (sAccessibilityDelegateField == null) {
            boolean var6 = false;

            try {
               var6 = true;
               Field var1 = View.class.getDeclaredField("mAccessibilityDelegate");
               sAccessibilityDelegateField = var1;
               var1.setAccessible(true);
               var6 = false;
            } finally {
               if (var6) {
                  sAccessibilityDelegateCheckFailed = true;
                  return null;
               }
            }
         }

         try {
            Object var9 = sAccessibilityDelegateField.get(var0);
            if (var9 instanceof AccessibilityDelegate) {
               AccessibilityDelegate var10 = (AccessibilityDelegate)var9;
               return var10;
            } else {
               return null;
            }
         } finally {
            sAccessibilityDelegateCheckFailed = true;
            return null;
         }
      }
   }

   public static int getAccessibilityLiveRegion(View var0) {
      return VERSION.SDK_INT >= 19 ? var0.getAccessibilityLiveRegion() : 0;
   }

   public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View var0) {
      if (VERSION.SDK_INT >= 16) {
         AccessibilityNodeProvider var1 = var0.getAccessibilityNodeProvider();
         if (var1 != null) {
            return new AccessibilityNodeProviderCompat(var1);
         }
      }

      return null;
   }

   public static CharSequence getAccessibilityPaneTitle(View var0) {
      return (CharSequence)paneTitleProperty().get(var0);
   }

   private static List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList(View var0) {
      ArrayList var1 = (ArrayList)var0.getTag(R.id.tag_accessibility_actions);
      ArrayList var2 = var1;
      if (var1 == null) {
         var2 = new ArrayList();
         var0.setTag(R.id.tag_accessibility_actions, var2);
      }

      return var2;
   }

   @Deprecated
   public static float getAlpha(View var0) {
      return var0.getAlpha();
   }

   private static int getAvailableActionIdFromResources(View var0) {
      List var1 = getActionList(var0);
      int var2 = 0;
      int var3 = -1;

      while(true) {
         int[] var8 = ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
         if (var2 >= var8.length || var3 != -1) {
            return var3;
         }

         int var4 = var8[var2];
         int var5 = 0;

         boolean var6;
         for(var6 = true; var5 < var1.size(); ++var5) {
            boolean var7;
            if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var1.get(var5)).getId() != var4) {
               var7 = true;
            } else {
               var7 = false;
            }

            var6 &= var7;
         }

         if (var6) {
            var3 = var4;
         }

         ++var2;
      }
   }

   public static ColorStateList getBackgroundTintList(View var0) {
      if (VERSION.SDK_INT >= 21) {
         return var0.getBackgroundTintList();
      } else {
         ColorStateList var1;
         if (var0 instanceof TintableBackgroundView) {
            var1 = ((TintableBackgroundView)var0).getSupportBackgroundTintList();
         } else {
            var1 = null;
         }

         return var1;
      }
   }

   public static Mode getBackgroundTintMode(View var0) {
      if (VERSION.SDK_INT >= 21) {
         return var0.getBackgroundTintMode();
      } else {
         Mode var1;
         if (var0 instanceof TintableBackgroundView) {
            var1 = ((TintableBackgroundView)var0).getSupportBackgroundTintMode();
         } else {
            var1 = null;
         }

         return var1;
      }
   }

   public static Rect getClipBounds(View var0) {
      return VERSION.SDK_INT >= 18 ? var0.getClipBounds() : null;
   }

   public static Display getDisplay(View var0) {
      if (VERSION.SDK_INT >= 17) {
         return var0.getDisplay();
      } else {
         return isAttachedToWindow(var0) ? ((WindowManager)var0.getContext().getSystemService("window")).getDefaultDisplay() : null;
      }
   }

   public static float getElevation(View var0) {
      return VERSION.SDK_INT >= 21 ? var0.getElevation() : 0.0F;
   }

   private static Rect getEmptyTempRect() {
      if (sThreadLocalRect == null) {
         sThreadLocalRect = new ThreadLocal();
      }

      Rect var0 = (Rect)sThreadLocalRect.get();
      Rect var1 = var0;
      if (var0 == null) {
         var1 = new Rect();
         sThreadLocalRect.set(var1);
      }

      var1.setEmpty();
      return var1;
   }

   public static boolean getFitsSystemWindows(View var0) {
      return VERSION.SDK_INT >= 16 ? var0.getFitsSystemWindows() : false;
   }

   public static int getImportantForAccessibility(View var0) {
      return VERSION.SDK_INT >= 16 ? var0.getImportantForAccessibility() : 0;
   }

   public static int getImportantForAutofill(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.getImportantForAutofill() : 0;
   }

   public static int getLabelFor(View var0) {
      return VERSION.SDK_INT >= 17 ? var0.getLabelFor() : 0;
   }

   @Deprecated
   public static int getLayerType(View var0) {
      return var0.getLayerType();
   }

   public static int getLayoutDirection(View var0) {
      return VERSION.SDK_INT >= 17 ? var0.getLayoutDirection() : 0;
   }

   @Deprecated
   public static Matrix getMatrix(View var0) {
      return var0.getMatrix();
   }

   @Deprecated
   public static int getMeasuredHeightAndState(View var0) {
      return var0.getMeasuredHeightAndState();
   }

   @Deprecated
   public static int getMeasuredState(View var0) {
      return var0.getMeasuredState();
   }

   @Deprecated
   public static int getMeasuredWidthAndState(View var0) {
      return var0.getMeasuredWidthAndState();
   }

   public static int getMinimumHeight(View var0) {
      if (VERSION.SDK_INT >= 16) {
         return var0.getMinimumHeight();
      } else {
         Field var1;
         if (!sMinHeightFieldFetched) {
            try {
               var1 = View.class.getDeclaredField("mMinHeight");
               sMinHeightField = var1;
               var1.setAccessible(true);
            } catch (NoSuchFieldException var3) {
            }

            sMinHeightFieldFetched = true;
         }

         var1 = sMinHeightField;
         if (var1 != null) {
            try {
               int var2 = (Integer)var1.get(var0);
               return var2;
            } catch (Exception var4) {
            }
         }

         return 0;
      }
   }

   public static int getMinimumWidth(View var0) {
      if (VERSION.SDK_INT >= 16) {
         return var0.getMinimumWidth();
      } else {
         Field var1;
         if (!sMinWidthFieldFetched) {
            try {
               var1 = View.class.getDeclaredField("mMinWidth");
               sMinWidthField = var1;
               var1.setAccessible(true);
            } catch (NoSuchFieldException var3) {
            }

            sMinWidthFieldFetched = true;
         }

         var1 = sMinWidthField;
         if (var1 != null) {
            try {
               int var2 = (Integer)var1.get(var0);
               return var2;
            } catch (Exception var4) {
            }
         }

         return 0;
      }
   }

   public static int getNextClusterForwardId(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.getNextClusterForwardId() : -1;
   }

   static AccessibilityDelegateCompat getOrCreateAccessibilityDelegateCompat(View var0) {
      AccessibilityDelegateCompat var1 = getAccessibilityDelegate(var0);
      AccessibilityDelegateCompat var2 = var1;
      if (var1 == null) {
         var2 = new AccessibilityDelegateCompat();
      }

      setAccessibilityDelegate(var0, var2);
      return var2;
   }

   @Deprecated
   public static int getOverScrollMode(View var0) {
      return var0.getOverScrollMode();
   }

   public static int getPaddingEnd(View var0) {
      return VERSION.SDK_INT >= 17 ? var0.getPaddingEnd() : var0.getPaddingRight();
   }

   public static int getPaddingStart(View var0) {
      return VERSION.SDK_INT >= 17 ? var0.getPaddingStart() : var0.getPaddingLeft();
   }

   public static ViewParent getParentForAccessibility(View var0) {
      return VERSION.SDK_INT >= 16 ? var0.getParentForAccessibility() : var0.getParent();
   }

   @Deprecated
   public static float getPivotX(View var0) {
      return var0.getPivotX();
   }

   @Deprecated
   public static float getPivotY(View var0) {
      return var0.getPivotY();
   }

   public static WindowInsetsCompat getRootWindowInsets(View var0) {
      return VERSION.SDK_INT >= 23 ? WindowInsetsCompat.toWindowInsetsCompat(var0.getRootWindowInsets()) : null;
   }

   @Deprecated
   public static float getRotation(View var0) {
      return var0.getRotation();
   }

   @Deprecated
   public static float getRotationX(View var0) {
      return var0.getRotationX();
   }

   @Deprecated
   public static float getRotationY(View var0) {
      return var0.getRotationY();
   }

   @Deprecated
   public static float getScaleX(View var0) {
      return var0.getScaleX();
   }

   @Deprecated
   public static float getScaleY(View var0) {
      return var0.getScaleY();
   }

   public static int getScrollIndicators(View var0) {
      return VERSION.SDK_INT >= 23 ? var0.getScrollIndicators() : 0;
   }

   public static List<Rect> getSystemGestureExclusionRects(View var0) {
      return VERSION.SDK_INT >= 29 ? var0.getSystemGestureExclusionRects() : Collections.emptyList();
   }

   public static String getTransitionName(View var0) {
      if (VERSION.SDK_INT >= 21) {
         return var0.getTransitionName();
      } else {
         WeakHashMap var1 = sTransitionNameMap;
         return var1 == null ? null : (String)var1.get(var0);
      }
   }

   @Deprecated
   public static float getTranslationX(View var0) {
      return var0.getTranslationX();
   }

   @Deprecated
   public static float getTranslationY(View var0) {
      return var0.getTranslationY();
   }

   public static float getTranslationZ(View var0) {
      return VERSION.SDK_INT >= 21 ? var0.getTranslationZ() : 0.0F;
   }

   public static int getWindowSystemUiVisibility(View var0) {
      return VERSION.SDK_INT >= 16 ? var0.getWindowSystemUiVisibility() : 0;
   }

   @Deprecated
   public static float getX(View var0) {
      return var0.getX();
   }

   @Deprecated
   public static float getY(View var0) {
      return var0.getY();
   }

   public static float getZ(View var0) {
      return VERSION.SDK_INT >= 21 ? var0.getZ() : 0.0F;
   }

   public static boolean hasAccessibilityDelegate(View var0) {
      boolean var1;
      if (getAccessibilityDelegateInternal(var0) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean hasExplicitFocusable(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.hasExplicitFocusable() : var0.hasFocusable();
   }

   public static boolean hasNestedScrollingParent(View var0) {
      if (VERSION.SDK_INT >= 21) {
         return var0.hasNestedScrollingParent();
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).hasNestedScrollingParent() : false;
      }
   }

   public static boolean hasNestedScrollingParent(View var0, int var1) {
      if (var0 instanceof NestedScrollingChild2) {
         ((NestedScrollingChild2)var0).hasNestedScrollingParent(var1);
      } else if (var1 == 0) {
         return hasNestedScrollingParent(var0);
      }

      return false;
   }

   public static boolean hasOnClickListeners(View var0) {
      return VERSION.SDK_INT >= 15 ? var0.hasOnClickListeners() : false;
   }

   public static boolean hasOverlappingRendering(View var0) {
      return VERSION.SDK_INT >= 16 ? var0.hasOverlappingRendering() : true;
   }

   public static boolean hasTransientState(View var0) {
      return VERSION.SDK_INT >= 16 ? var0.hasTransientState() : false;
   }

   public static boolean isAccessibilityHeading(View var0) {
      Boolean var2 = (Boolean)accessibilityHeadingProperty().get(var0);
      boolean var1;
      if (var2 == null) {
         var1 = false;
      } else {
         var1 = var2;
      }

      return var1;
   }

   public static boolean isAttachedToWindow(View var0) {
      if (VERSION.SDK_INT >= 19) {
         return var0.isAttachedToWindow();
      } else {
         boolean var1;
         if (var0.getWindowToken() != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public static boolean isFocusedByDefault(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.isFocusedByDefault() : false;
   }

   public static boolean isImportantForAccessibility(View var0) {
      return VERSION.SDK_INT >= 21 ? var0.isImportantForAccessibility() : true;
   }

   public static boolean isImportantForAutofill(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.isImportantForAutofill() : true;
   }

   public static boolean isInLayout(View var0) {
      return VERSION.SDK_INT >= 18 ? var0.isInLayout() : false;
   }

   public static boolean isKeyboardNavigationCluster(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.isKeyboardNavigationCluster() : false;
   }

   public static boolean isLaidOut(View var0) {
      if (VERSION.SDK_INT >= 19) {
         return var0.isLaidOut();
      } else {
         boolean var1;
         if (var0.getWidth() > 0 && var0.getHeight() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public static boolean isLayoutDirectionResolved(View var0) {
      return VERSION.SDK_INT >= 19 ? var0.isLayoutDirectionResolved() : false;
   }

   public static boolean isNestedScrollingEnabled(View var0) {
      if (VERSION.SDK_INT >= 21) {
         return var0.isNestedScrollingEnabled();
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).isNestedScrollingEnabled() : false;
      }
   }

   @Deprecated
   public static boolean isOpaque(View var0) {
      return var0.isOpaque();
   }

   public static boolean isPaddingRelative(View var0) {
      return VERSION.SDK_INT >= 17 ? var0.isPaddingRelative() : false;
   }

   public static boolean isScreenReaderFocusable(View var0) {
      Boolean var2 = (Boolean)screenReaderFocusableProperty().get(var0);
      boolean var1;
      if (var2 == null) {
         var1 = false;
      } else {
         var1 = var2;
      }

      return var1;
   }

   @Deprecated
   public static void jumpDrawablesToCurrentState(View var0) {
      var0.jumpDrawablesToCurrentState();
   }

   public static View keyboardNavigationClusterSearch(View var0, View var1, int var2) {
      return VERSION.SDK_INT >= 26 ? var0.keyboardNavigationClusterSearch(var1, var2) : null;
   }

   static void notifyViewAccessibilityStateChangedIfNeeded(View var0, int var1) {
      if (((AccessibilityManager)var0.getContext().getSystemService("accessibility")).isEnabled()) {
         boolean var2;
         if (getAccessibilityPaneTitle(var0) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (getAccessibilityLiveRegion(var0) == 0 && (!var2 || var0.getVisibility() != 0)) {
            if (var0.getParent() != null) {
               try {
                  var0.getParent().notifySubtreeAccessibilityStateChanged(var0, var0, var1);
               } catch (AbstractMethodError var5) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append(var0.getParent().getClass().getSimpleName());
                  var7.append(" does not fully implement ViewParent");
                  Log.e("ViewCompat", var7.toString(), var5);
               }
            }
         } else {
            AccessibilityEvent var4 = AccessibilityEvent.obtain();
            short var6;
            if (var2) {
               var6 = 32;
            } else {
               var6 = 2048;
            }

            var4.setEventType(var6);
            var4.setContentChangeTypes(var1);
            var0.sendAccessibilityEventUnchecked(var4);
         }

      }
   }

   public static void offsetLeftAndRight(View var0, int var1) {
      if (VERSION.SDK_INT >= 23) {
         var0.offsetLeftAndRight(var1);
      } else if (VERSION.SDK_INT >= 21) {
         Rect var2 = getEmptyTempRect();
         boolean var3 = false;
         ViewParent var4 = var0.getParent();
         if (var4 instanceof View) {
            View var5 = (View)var4;
            var2.set(var5.getLeft(), var5.getTop(), var5.getRight(), var5.getBottom());
            var3 = var2.intersects(var0.getLeft(), var0.getTop(), var0.getRight(), var0.getBottom()) ^ true;
         }

         compatOffsetLeftAndRight(var0, var1);
         if (var3 && var2.intersect(var0.getLeft(), var0.getTop(), var0.getRight(), var0.getBottom())) {
            ((View)var4).invalidate(var2);
         }
      } else {
         compatOffsetLeftAndRight(var0, var1);
      }

   }

   public static void offsetTopAndBottom(View var0, int var1) {
      if (VERSION.SDK_INT >= 23) {
         var0.offsetTopAndBottom(var1);
      } else if (VERSION.SDK_INT >= 21) {
         Rect var2 = getEmptyTempRect();
         boolean var3 = false;
         ViewParent var4 = var0.getParent();
         if (var4 instanceof View) {
            View var5 = (View)var4;
            var2.set(var5.getLeft(), var5.getTop(), var5.getRight(), var5.getBottom());
            var3 = var2.intersects(var0.getLeft(), var0.getTop(), var0.getRight(), var0.getBottom()) ^ true;
         }

         compatOffsetTopAndBottom(var0, var1);
         if (var3 && var2.intersect(var0.getLeft(), var0.getTop(), var0.getRight(), var0.getBottom())) {
            ((View)var4).invalidate(var2);
         }
      } else {
         compatOffsetTopAndBottom(var0, var1);
      }

   }

   public static WindowInsetsCompat onApplyWindowInsets(View var0, WindowInsetsCompat var1) {
      if (VERSION.SDK_INT >= 21) {
         WindowInsets var2 = var1.toWindowInsets();
         if (var2 != null) {
            WindowInsets var3 = var0.onApplyWindowInsets(var2);
            if (!var3.equals(var2)) {
               return WindowInsetsCompat.toWindowInsetsCompat(var3);
            }
         }
      }

      return var1;
   }

   @Deprecated
   public static void onInitializeAccessibilityEvent(View var0, AccessibilityEvent var1) {
      var0.onInitializeAccessibilityEvent(var1);
   }

   public static void onInitializeAccessibilityNodeInfo(View var0, AccessibilityNodeInfoCompat var1) {
      var0.onInitializeAccessibilityNodeInfo(var1.unwrap());
   }

   @Deprecated
   public static void onPopulateAccessibilityEvent(View var0, AccessibilityEvent var1) {
      var0.onPopulateAccessibilityEvent(var1);
   }

   private static ViewCompat.AccessibilityViewProperty<CharSequence> paneTitleProperty() {
      return new ViewCompat.AccessibilityViewProperty<CharSequence>(R.id.tag_accessibility_pane_title, CharSequence.class, 8, 28) {
         CharSequence frameworkGet(View var1) {
            return var1.getAccessibilityPaneTitle();
         }

         void frameworkSet(View var1, CharSequence var2) {
            var1.setAccessibilityPaneTitle(var2);
         }

         boolean shouldUpdate(CharSequence var1, CharSequence var2) {
            return TextUtils.equals(var1, var2) ^ true;
         }
      };
   }

   public static boolean performAccessibilityAction(View var0, int var1, Bundle var2) {
      return VERSION.SDK_INT >= 16 ? var0.performAccessibilityAction(var1, var2) : false;
   }

   public static void postInvalidateOnAnimation(View var0) {
      if (VERSION.SDK_INT >= 16) {
         var0.postInvalidateOnAnimation();
      } else {
         var0.postInvalidate();
      }

   }

   public static void postInvalidateOnAnimation(View var0, int var1, int var2, int var3, int var4) {
      if (VERSION.SDK_INT >= 16) {
         var0.postInvalidateOnAnimation(var1, var2, var3, var4);
      } else {
         var0.postInvalidate(var1, var2, var3, var4);
      }

   }

   public static void postOnAnimation(View var0, Runnable var1) {
      if (VERSION.SDK_INT >= 16) {
         var0.postOnAnimation(var1);
      } else {
         var0.postDelayed(var1, ValueAnimator.getFrameDelay());
      }

   }

   public static void postOnAnimationDelayed(View var0, Runnable var1, long var2) {
      if (VERSION.SDK_INT >= 16) {
         var0.postOnAnimationDelayed(var1, var2);
      } else {
         var0.postDelayed(var1, ValueAnimator.getFrameDelay() + var2);
      }

   }

   public static void removeAccessibilityAction(View var0, int var1) {
      if (VERSION.SDK_INT >= 21) {
         removeActionWithId(var1, var0);
         notifyViewAccessibilityStateChangedIfNeeded(var0, 0);
      }

   }

   private static void removeActionWithId(int var0, View var1) {
      List var3 = getActionList(var1);

      for(int var2 = 0; var2 < var3.size(); ++var2) {
         if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var3.get(var2)).getId() == var0) {
            var3.remove(var2);
            break;
         }
      }

   }

   public static void removeOnUnhandledKeyEventListener(View var0, ViewCompat.OnUnhandledKeyEventListenerCompat var1) {
      if (VERSION.SDK_INT >= 28) {
         SimpleArrayMap var4 = (SimpleArrayMap)var0.getTag(R.id.tag_unhandled_key_listeners);
         if (var4 != null) {
            OnUnhandledKeyEventListener var3 = (OnUnhandledKeyEventListener)var4.get(var1);
            if (var3 != null) {
               var0.removeOnUnhandledKeyEventListener(var3);
            }

         }
      } else {
         ArrayList var2 = (ArrayList)var0.getTag(R.id.tag_unhandled_key_listeners);
         if (var2 != null) {
            var2.remove(var1);
            if (var2.size() == 0) {
               ViewCompat.UnhandledKeyEventManager.unregisterListeningView(var0);
            }
         }

      }
   }

   public static void replaceAccessibilityAction(View var0, AccessibilityNodeInfoCompat.AccessibilityActionCompat var1, CharSequence var2, AccessibilityViewCommand var3) {
      if (var3 == null && var2 == null) {
         removeAccessibilityAction(var0, var1.getId());
      } else {
         addAccessibilityAction(var0, var1.createReplacementAction(var2, var3));
      }

   }

   public static void requestApplyInsets(View var0) {
      if (VERSION.SDK_INT >= 20) {
         var0.requestApplyInsets();
      } else if (VERSION.SDK_INT >= 16) {
         var0.requestFitSystemWindows();
      }

   }

   public static <T extends View> T requireViewById(View var0, int var1) {
      if (VERSION.SDK_INT >= 28) {
         return var0.requireViewById(var1);
      } else {
         var0 = var0.findViewById(var1);
         if (var0 != null) {
            return var0;
         } else {
            throw new IllegalArgumentException("ID does not reference a View inside this View");
         }
      }
   }

   @Deprecated
   public static int resolveSizeAndState(int var0, int var1, int var2) {
      return View.resolveSizeAndState(var0, var1, var2);
   }

   public static boolean restoreDefaultFocus(View var0) {
      return VERSION.SDK_INT >= 26 ? var0.restoreDefaultFocus() : var0.requestFocus();
   }

   public static void saveAttributeDataForStyleable(View var0, Context var1, int[] var2, AttributeSet var3, TypedArray var4, int var5, int var6) {
      if (VERSION.SDK_INT >= 29) {
         var0.saveAttributeDataForStyleable(var1, var2, var3, var4, var5, var6);
      }

   }

   private static ViewCompat.AccessibilityViewProperty<Boolean> screenReaderFocusableProperty() {
      return new ViewCompat.AccessibilityViewProperty<Boolean>(R.id.tag_screen_reader_focusable, Boolean.class, 28) {
         Boolean frameworkGet(View var1) {
            return var1.isScreenReaderFocusable();
         }

         void frameworkSet(View var1, Boolean var2) {
            var1.setScreenReaderFocusable(var2);
         }

         boolean shouldUpdate(Boolean var1, Boolean var2) {
            return this.booleanNullToFalseEquals(var1, var2) ^ true;
         }
      };
   }

   public static void setAccessibilityDelegate(View var0, AccessibilityDelegateCompat var1) {
      AccessibilityDelegateCompat var2 = var1;
      if (var1 == null) {
         var2 = var1;
         if (getAccessibilityDelegateInternal(var0) instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter) {
            var2 = new AccessibilityDelegateCompat();
         }
      }

      AccessibilityDelegate var3;
      if (var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getBridge();
      }

      var0.setAccessibilityDelegate(var3);
   }

   public static void setAccessibilityHeading(View var0, boolean var1) {
      accessibilityHeadingProperty().set(var0, var1);
   }

   public static void setAccessibilityLiveRegion(View var0, int var1) {
      if (VERSION.SDK_INT >= 19) {
         var0.setAccessibilityLiveRegion(var1);
      }

   }

   public static void setAccessibilityPaneTitle(View var0, CharSequence var1) {
      if (VERSION.SDK_INT >= 19) {
         paneTitleProperty().set(var0, var1);
         if (var1 != null) {
            sAccessibilityPaneVisibilityManager.addAccessibilityPane(var0);
         } else {
            sAccessibilityPaneVisibilityManager.removeAccessibilityPane(var0);
         }
      }

   }

   @Deprecated
   public static void setActivated(View var0, boolean var1) {
      var0.setActivated(var1);
   }

   @Deprecated
   public static void setAlpha(View var0, float var1) {
      var0.setAlpha(var1);
   }

   public static void setAutofillHints(View var0, String... var1) {
      if (VERSION.SDK_INT >= 26) {
         var0.setAutofillHints(var1);
      }

   }

   public static void setBackground(View var0, Drawable var1) {
      if (VERSION.SDK_INT >= 16) {
         var0.setBackground(var1);
      } else {
         var0.setBackgroundDrawable(var1);
      }

   }

   public static void setBackgroundTintList(View var0, ColorStateList var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setBackgroundTintList(var1);
         if (VERSION.SDK_INT == 21) {
            Drawable var3 = var0.getBackground();
            boolean var2;
            if (var0.getBackgroundTintList() == null && var0.getBackgroundTintMode() == null) {
               var2 = false;
            } else {
               var2 = true;
            }

            if (var3 != null && var2) {
               if (var3.isStateful()) {
                  var3.setState(var0.getDrawableState());
               }

               var0.setBackground(var3);
            }
         }
      } else if (var0 instanceof TintableBackgroundView) {
         ((TintableBackgroundView)var0).setSupportBackgroundTintList(var1);
      }

   }

   public static void setBackgroundTintMode(View var0, Mode var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setBackgroundTintMode(var1);
         if (VERSION.SDK_INT == 21) {
            Drawable var3 = var0.getBackground();
            boolean var2;
            if (var0.getBackgroundTintList() == null && var0.getBackgroundTintMode() == null) {
               var2 = false;
            } else {
               var2 = true;
            }

            if (var3 != null && var2) {
               if (var3.isStateful()) {
                  var3.setState(var0.getDrawableState());
               }

               var0.setBackground(var3);
            }
         }
      } else if (var0 instanceof TintableBackgroundView) {
         ((TintableBackgroundView)var0).setSupportBackgroundTintMode(var1);
      }

   }

   @Deprecated
   public static void setChildrenDrawingOrderEnabled(ViewGroup var0, boolean var1) {
      if (sChildrenDrawingOrderMethod == null) {
         try {
            sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", Boolean.TYPE);
         } catch (NoSuchMethodException var6) {
            Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", var6);
         }

         sChildrenDrawingOrderMethod.setAccessible(true);
      }

      try {
         sChildrenDrawingOrderMethod.invoke(var0, var1);
      } catch (IllegalAccessException var3) {
         Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", var3);
      } catch (IllegalArgumentException var4) {
         Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", var4);
      } catch (InvocationTargetException var5) {
         Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", var5);
      }

   }

   public static void setClipBounds(View var0, Rect var1) {
      if (VERSION.SDK_INT >= 18) {
         var0.setClipBounds(var1);
      }

   }

   public static void setElevation(View var0, float var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setElevation(var1);
      }

   }

   @Deprecated
   public static void setFitsSystemWindows(View var0, boolean var1) {
      var0.setFitsSystemWindows(var1);
   }

   public static void setFocusedByDefault(View var0, boolean var1) {
      if (VERSION.SDK_INT >= 26) {
         var0.setFocusedByDefault(var1);
      }

   }

   public static void setHasTransientState(View var0, boolean var1) {
      if (VERSION.SDK_INT >= 16) {
         var0.setHasTransientState(var1);
      }

   }

   public static void setImportantForAccessibility(View var0, int var1) {
      if (VERSION.SDK_INT >= 19) {
         var0.setImportantForAccessibility(var1);
      } else if (VERSION.SDK_INT >= 16) {
         int var2 = var1;
         if (var1 == 4) {
            var2 = 2;
         }

         var0.setImportantForAccessibility(var2);
      }

   }

   public static void setImportantForAutofill(View var0, int var1) {
      if (VERSION.SDK_INT >= 26) {
         var0.setImportantForAutofill(var1);
      }

   }

   public static void setKeyboardNavigationCluster(View var0, boolean var1) {
      if (VERSION.SDK_INT >= 26) {
         var0.setKeyboardNavigationCluster(var1);
      }

   }

   public static void setLabelFor(View var0, int var1) {
      if (VERSION.SDK_INT >= 17) {
         var0.setLabelFor(var1);
      }

   }

   public static void setLayerPaint(View var0, Paint var1) {
      if (VERSION.SDK_INT >= 17) {
         var0.setLayerPaint(var1);
      } else {
         var0.setLayerType(var0.getLayerType(), var1);
         var0.invalidate();
      }

   }

   @Deprecated
   public static void setLayerType(View var0, int var1, Paint var2) {
      var0.setLayerType(var1, var2);
   }

   public static void setLayoutDirection(View var0, int var1) {
      if (VERSION.SDK_INT >= 17) {
         var0.setLayoutDirection(var1);
      }

   }

   public static void setNestedScrollingEnabled(View var0, boolean var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setNestedScrollingEnabled(var1);
      } else if (var0 instanceof NestedScrollingChild) {
         ((NestedScrollingChild)var0).setNestedScrollingEnabled(var1);
      }

   }

   public static void setNextClusterForwardId(View var0, int var1) {
      if (VERSION.SDK_INT >= 26) {
         var0.setNextClusterForwardId(var1);
      }

   }

   public static void setOnApplyWindowInsetsListener(View var0, final OnApplyWindowInsetsListener var1) {
      if (VERSION.SDK_INT >= 21) {
         if (var1 == null) {
            var0.setOnApplyWindowInsetsListener((android.view.View.OnApplyWindowInsetsListener)null);
            return;
         }

         var0.setOnApplyWindowInsetsListener(new android.view.View.OnApplyWindowInsetsListener() {
            public WindowInsets onApplyWindowInsets(View var1x, WindowInsets var2) {
               WindowInsetsCompat var3 = WindowInsetsCompat.toWindowInsetsCompat(var2);
               return var1.onApplyWindowInsets(var1x, var3).toWindowInsets();
            }
         });
      }

   }

   @Deprecated
   public static void setOverScrollMode(View var0, int var1) {
      var0.setOverScrollMode(var1);
   }

   public static void setPaddingRelative(View var0, int var1, int var2, int var3, int var4) {
      if (VERSION.SDK_INT >= 17) {
         var0.setPaddingRelative(var1, var2, var3, var4);
      } else {
         var0.setPadding(var1, var2, var3, var4);
      }

   }

   @Deprecated
   public static void setPivotX(View var0, float var1) {
      var0.setPivotX(var1);
   }

   @Deprecated
   public static void setPivotY(View var0, float var1) {
      var0.setPivotY(var1);
   }

   public static void setPointerIcon(View var0, PointerIconCompat var1) {
      if (VERSION.SDK_INT >= 24) {
         Object var2;
         if (var1 != null) {
            var2 = var1.getPointerIcon();
         } else {
            var2 = null;
         }

         var0.setPointerIcon((PointerIcon)var2);
      }

   }

   @Deprecated
   public static void setRotation(View var0, float var1) {
      var0.setRotation(var1);
   }

   @Deprecated
   public static void setRotationX(View var0, float var1) {
      var0.setRotationX(var1);
   }

   @Deprecated
   public static void setRotationY(View var0, float var1) {
      var0.setRotationY(var1);
   }

   @Deprecated
   public static void setSaveFromParentEnabled(View var0, boolean var1) {
      var0.setSaveFromParentEnabled(var1);
   }

   @Deprecated
   public static void setScaleX(View var0, float var1) {
      var0.setScaleX(var1);
   }

   @Deprecated
   public static void setScaleY(View var0, float var1) {
      var0.setScaleY(var1);
   }

   public static void setScreenReaderFocusable(View var0, boolean var1) {
      screenReaderFocusableProperty().set(var0, var1);
   }

   public static void setScrollIndicators(View var0, int var1) {
      if (VERSION.SDK_INT >= 23) {
         var0.setScrollIndicators(var1);
      }

   }

   public static void setScrollIndicators(View var0, int var1, int var2) {
      if (VERSION.SDK_INT >= 23) {
         var0.setScrollIndicators(var1, var2);
      }

   }

   public static void setSystemGestureExclusionRects(View var0, List<Rect> var1) {
      if (VERSION.SDK_INT >= 29) {
         var0.setSystemGestureExclusionRects(var1);
      }

   }

   public static void setTooltipText(View var0, CharSequence var1) {
      if (VERSION.SDK_INT >= 26) {
         var0.setTooltipText(var1);
      }

   }

   public static void setTransitionName(View var0, String var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setTransitionName(var1);
      } else {
         if (sTransitionNameMap == null) {
            sTransitionNameMap = new WeakHashMap();
         }

         sTransitionNameMap.put(var0, var1);
      }

   }

   @Deprecated
   public static void setTranslationX(View var0, float var1) {
      var0.setTranslationX(var1);
   }

   @Deprecated
   public static void setTranslationY(View var0, float var1) {
      var0.setTranslationY(var1);
   }

   public static void setTranslationZ(View var0, float var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setTranslationZ(var1);
      }

   }

   @Deprecated
   public static void setX(View var0, float var1) {
      var0.setX(var1);
   }

   @Deprecated
   public static void setY(View var0, float var1) {
      var0.setY(var1);
   }

   public static void setZ(View var0, float var1) {
      if (VERSION.SDK_INT >= 21) {
         var0.setZ(var1);
      }

   }

   public static boolean startDragAndDrop(View var0, ClipData var1, DragShadowBuilder var2, Object var3, int var4) {
      return VERSION.SDK_INT >= 24 ? var0.startDragAndDrop(var1, var2, var3, var4) : var0.startDrag(var1, var2, var3, var4);
   }

   public static boolean startNestedScroll(View var0, int var1) {
      if (VERSION.SDK_INT >= 21) {
         return var0.startNestedScroll(var1);
      } else {
         return var0 instanceof NestedScrollingChild ? ((NestedScrollingChild)var0).startNestedScroll(var1) : false;
      }
   }

   public static boolean startNestedScroll(View var0, int var1, int var2) {
      if (var0 instanceof NestedScrollingChild2) {
         return ((NestedScrollingChild2)var0).startNestedScroll(var1, var2);
      } else {
         return var2 == 0 ? startNestedScroll(var0, var1) : false;
      }
   }

   public static void stopNestedScroll(View var0) {
      if (VERSION.SDK_INT >= 21) {
         var0.stopNestedScroll();
      } else if (var0 instanceof NestedScrollingChild) {
         ((NestedScrollingChild)var0).stopNestedScroll();
      }

   }

   public static void stopNestedScroll(View var0, int var1) {
      if (var0 instanceof NestedScrollingChild2) {
         ((NestedScrollingChild2)var0).stopNestedScroll(var1);
      } else if (var1 == 0) {
         stopNestedScroll(var0);
      }

   }

   private static void tickleInvalidationFlag(View var0) {
      float var1 = var0.getTranslationY();
      var0.setTranslationY(1.0F + var1);
      var0.setTranslationY(var1);
   }

   public static void updateDragShadow(View var0, DragShadowBuilder var1) {
      if (VERSION.SDK_INT >= 24) {
         var0.updateDragShadow(var1);
      }

   }

   static class AccessibilityPaneVisibilityManager implements OnGlobalLayoutListener, OnAttachStateChangeListener {
      private WeakHashMap<View, Boolean> mPanesToVisible = new WeakHashMap();

      private void checkPaneVisibility(View var1, boolean var2) {
         boolean var3;
         if (var1.getVisibility() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var2 != var3) {
            if (var3) {
               ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(var1, 16);
            }

            this.mPanesToVisible.put(var1, var3);
         }

      }

      private void registerForLayoutCallback(View var1) {
         var1.getViewTreeObserver().addOnGlobalLayoutListener(this);
      }

      private void unregisterForLayoutCallback(View var1) {
         var1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }

      void addAccessibilityPane(View var1) {
         WeakHashMap var2 = this.mPanesToVisible;
         boolean var3;
         if (var1.getVisibility() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         var2.put(var1, var3);
         var1.addOnAttachStateChangeListener(this);
         if (var1.isAttachedToWindow()) {
            this.registerForLayoutCallback(var1);
         }

      }

      public void onGlobalLayout() {
         Iterator var1 = this.mPanesToVisible.entrySet().iterator();

         while(var1.hasNext()) {
            Entry var2 = (Entry)var1.next();
            this.checkPaneVisibility((View)var2.getKey(), (Boolean)var2.getValue());
         }

      }

      public void onViewAttachedToWindow(View var1) {
         this.registerForLayoutCallback(var1);
      }

      public void onViewDetachedFromWindow(View var1) {
      }

      void removeAccessibilityPane(View var1) {
         this.mPanesToVisible.remove(var1);
         var1.removeOnAttachStateChangeListener(this);
         this.unregisterForLayoutCallback(var1);
      }
   }

   abstract static class AccessibilityViewProperty<T> {
      private final int mFrameworkMinimumSdk;
      private final int mTagKey;
      private final Class<T> mType;

      AccessibilityViewProperty(int var1, Class<T> var2, int var3) {
         this(var1, var2, 0, var3);
      }

      AccessibilityViewProperty(int var1, Class<T> var2, int var3, int var4) {
         this.mTagKey = var1;
         this.mType = var2;
         this.mFrameworkMinimumSdk = var4;
      }

      private boolean extrasAvailable() {
         boolean var1;
         if (VERSION.SDK_INT >= 19) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private boolean frameworkAvailable() {
         boolean var1;
         if (VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      boolean booleanNullToFalseEquals(Boolean var1, Boolean var2) {
         boolean var3 = false;
         boolean var4;
         if (var1 == null) {
            var4 = false;
         } else {
            var4 = var1;
         }

         boolean var5;
         if (var2 == null) {
            var5 = false;
         } else {
            var5 = var2;
         }

         if (var4 == var5) {
            var3 = true;
         }

         return var3;
      }

      abstract T frameworkGet(View var1);

      abstract void frameworkSet(View var1, T var2);

      T get(View var1) {
         if (this.frameworkAvailable()) {
            return this.frameworkGet(var1);
         } else {
            if (this.extrasAvailable()) {
               Object var2 = var1.getTag(this.mTagKey);
               if (this.mType.isInstance(var2)) {
                  return var2;
               }
            }

            return null;
         }
      }

      void set(View var1, T var2) {
         if (this.frameworkAvailable()) {
            this.frameworkSet(var1, var2);
         } else if (this.extrasAvailable() && this.shouldUpdate(this.get(var1), var2)) {
            ViewCompat.getOrCreateAccessibilityDelegateCompat(var1);
            var1.setTag(this.mTagKey, var2);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(var1, 0);
         }

      }

      boolean shouldUpdate(T var1, T var2) {
         return var2.equals(var1) ^ true;
      }
   }

   private static class Api21Impl {
      static WindowInsetsCompat computeSystemWindowInsets(View var0, WindowInsetsCompat var1, Rect var2) {
         WindowInsets var3 = var1.toWindowInsets();
         if (var3 != null) {
            return WindowInsetsCompat.toWindowInsetsCompat(var0.computeSystemWindowInsets(var3, var2));
         } else {
            var2.setEmpty();
            return var1;
         }
      }
   }

   private static class Api23Impl {
      public static WindowInsets getRootWindowInsets(View var0) {
         return var0.getRootWindowInsets();
      }
   }

   private static class Api29Impl {
      static void saveAttributeDataForStyleable(View var0, Context var1, int[] var2, AttributeSet var3, TypedArray var4, int var5, int var6) {
         var0.saveAttributeDataForStyleable(var1, var2, var3, var4, var5, var6);
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FocusDirection {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FocusRealDirection {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FocusRelativeDirection {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface NestedScrollType {
   }

   public interface OnUnhandledKeyEventListenerCompat {
      boolean onUnhandledKeyEvent(View var1, KeyEvent var2);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ScrollAxis {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ScrollIndicators {
   }

   static class UnhandledKeyEventManager {
      private static final ArrayList<WeakReference<View>> sViewsWithListeners = new ArrayList();
      private SparseArray<WeakReference<View>> mCapturedKeys = null;
      private WeakReference<KeyEvent> mLastDispatchedPreViewKeyEvent = null;
      private WeakHashMap<View, Boolean> mViewsContainingListeners = null;

      static ViewCompat.UnhandledKeyEventManager at(View var0) {
         ViewCompat.UnhandledKeyEventManager var1 = (ViewCompat.UnhandledKeyEventManager)var0.getTag(R.id.tag_unhandled_key_event_manager);
         ViewCompat.UnhandledKeyEventManager var2 = var1;
         if (var1 == null) {
            var2 = new ViewCompat.UnhandledKeyEventManager();
            var0.setTag(R.id.tag_unhandled_key_event_manager, var2);
         }

         return var2;
      }

      private View dispatchInOrder(View var1, KeyEvent var2) {
         WeakHashMap var3 = this.mViewsContainingListeners;
         if (var3 != null && var3.containsKey(var1)) {
            if (var1 instanceof ViewGroup) {
               ViewGroup var4 = (ViewGroup)var1;

               for(int var5 = var4.getChildCount() - 1; var5 >= 0; --var5) {
                  View var6 = this.dispatchInOrder(var4.getChildAt(var5), var2);
                  if (var6 != null) {
                     return var6;
                  }
               }
            }

            if (this.onUnhandledKeyEvent(var1, var2)) {
               return var1;
            }
         }

         return null;
      }

      private SparseArray<WeakReference<View>> getCapturedKeys() {
         if (this.mCapturedKeys == null) {
            this.mCapturedKeys = new SparseArray();
         }

         return this.mCapturedKeys;
      }

      private boolean onUnhandledKeyEvent(View var1, KeyEvent var2) {
         ArrayList var3 = (ArrayList)var1.getTag(R.id.tag_unhandled_key_listeners);
         if (var3 != null) {
            for(int var4 = var3.size() - 1; var4 >= 0; --var4) {
               if (((ViewCompat.OnUnhandledKeyEventListenerCompat)var3.get(var4)).onUnhandledKeyEvent(var1, var2)) {
                  return true;
               }
            }
         }

         return false;
      }

      private void recalcViewsWithUnhandled() {
         WeakHashMap var1 = this.mViewsContainingListeners;
         if (var1 != null) {
            var1.clear();
         }

         if (!sViewsWithListeners.isEmpty()) {
            ArrayList var2 = sViewsWithListeners;
            synchronized(var2){}

            Throwable var10000;
            boolean var10001;
            label694: {
               try {
                  if (this.mViewsContainingListeners == null) {
                     var1 = new WeakHashMap();
                     this.mViewsContainingListeners = var1;
                  }
               } catch (Throwable var75) {
                  var10000 = var75;
                  var10001 = false;
                  break label694;
               }

               int var3;
               try {
                  var3 = sViewsWithListeners.size() - 1;
               } catch (Throwable var73) {
                  var10000 = var73;
                  var10001 = false;
                  break label694;
               }

               for(; var3 >= 0; --var3) {
                  View var76;
                  try {
                     var76 = (View)((WeakReference)sViewsWithListeners.get(var3)).get();
                  } catch (Throwable var72) {
                     var10000 = var72;
                     var10001 = false;
                     break label694;
                  }

                  if (var76 == null) {
                     try {
                        sViewsWithListeners.remove(var3);
                     } catch (Throwable var71) {
                        var10000 = var71;
                        var10001 = false;
                        break label694;
                     }
                  } else {
                     ViewParent var77;
                     try {
                        this.mViewsContainingListeners.put(var76, Boolean.TRUE);
                        var77 = var76.getParent();
                     } catch (Throwable var70) {
                        var10000 = var70;
                        var10001 = false;
                        break label694;
                     }

                     while(true) {
                        try {
                           if (!(var77 instanceof View)) {
                              break;
                           }

                           this.mViewsContainingListeners.put((View)var77, Boolean.TRUE);
                           var77 = var77.getParent();
                        } catch (Throwable var74) {
                           var10000 = var74;
                           var10001 = false;
                           break label694;
                        }
                     }
                  }
               }

               label663:
               try {
                  return;
               } catch (Throwable var69) {
                  var10000 = var69;
                  var10001 = false;
                  break label663;
               }
            }

            while(true) {
               Throwable var78 = var10000;

               try {
                  throw var78;
               } catch (Throwable var68) {
                  var10000 = var68;
                  var10001 = false;
                  continue;
               }
            }
         }
      }

      static void registerListeningView(View var0) {
         ArrayList var1 = sViewsWithListeners;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label202: {
            Iterator var2;
            try {
               var2 = sViewsWithListeners.iterator();
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label202;
            }

            try {
               while(var2.hasNext()) {
                  if (((WeakReference)var2.next()).get() == var0) {
                     return;
                  }
               }
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label202;
            }

            label192:
            try {
               ArrayList var25 = sViewsWithListeners;
               WeakReference var3 = new WeakReference(var0);
               var25.add(var3);
               return;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label192;
            }
         }

         while(true) {
            Throwable var24 = var10000;

            try {
               throw var24;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               continue;
            }
         }
      }

      static void unregisterListeningView(View var0) {
         ArrayList var1 = sViewsWithListeners;
         synchronized(var1){}
         int var2 = 0;

         while(true) {
            label161: {
               Throwable var10000;
               boolean var10001;
               label156: {
                  try {
                     if (var2 < sViewsWithListeners.size()) {
                        if (((WeakReference)sViewsWithListeners.get(var2)).get() != var0) {
                           break label161;
                        }

                        sViewsWithListeners.remove(var2);
                        return;
                     }
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label156;
                  }

                  label149:
                  try {
                     return;
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label149;
                  }
               }

               while(true) {
                  Throwable var15 = var10000;

                  try {
                     throw var15;
                  } catch (Throwable var12) {
                     var10000 = var12;
                     var10001 = false;
                     continue;
                  }
               }
            }

            ++var2;
         }
      }

      boolean dispatch(View var1, KeyEvent var2) {
         if (var2.getAction() == 0) {
            this.recalcViewsWithUnhandled();
         }

         var1 = this.dispatchInOrder(var1, var2);
         if (var2.getAction() == 0) {
            int var3 = var2.getKeyCode();
            if (var1 != null && !KeyEvent.isModifierKey(var3)) {
               this.getCapturedKeys().put(var3, new WeakReference(var1));
            }
         }

         boolean var4;
         if (var1 != null) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      boolean preDispatch(KeyEvent var1) {
         WeakReference var2 = this.mLastDispatchedPreViewKeyEvent;
         if (var2 != null && var2.get() == var1) {
            return false;
         } else {
            this.mLastDispatchedPreViewKeyEvent = new WeakReference(var1);
            WeakReference var3 = null;
            SparseArray var4 = this.getCapturedKeys();
            var2 = var3;
            if (var1.getAction() == 1) {
               int var5 = var4.indexOfKey(var1.getKeyCode());
               var2 = var3;
               if (var5 >= 0) {
                  var2 = (WeakReference)var4.valueAt(var5);
                  var4.removeAt(var5);
               }
            }

            var3 = var2;
            if (var2 == null) {
               var3 = (WeakReference)var4.get(var1.getKeyCode());
            }

            if (var3 != null) {
               View var6 = (View)var3.get();
               if (var6 != null && ViewCompat.isAttachedToWindow(var6)) {
                  this.onUnhandledKeyEvent(var6, var1);
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }
}
