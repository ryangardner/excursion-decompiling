package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import androidx.coordinatorlayout.R;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2, NestedScrollingParent3 {
   static final Class<?>[] CONSTRUCTOR_PARAMS;
   static final int EVENT_NESTED_SCROLL = 1;
   static final int EVENT_PRE_DRAW = 0;
   static final int EVENT_VIEW_REMOVED = 2;
   static final String TAG = "CoordinatorLayout";
   static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
   private static final int TYPE_ON_INTERCEPT = 0;
   private static final int TYPE_ON_TOUCH = 1;
   static final String WIDGET_PACKAGE_NAME;
   static final ThreadLocal<Map<String, Constructor<CoordinatorLayout.Behavior>>> sConstructors;
   private static final Pools.Pool<Rect> sRectPool;
   private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
   private final int[] mBehaviorConsumed;
   private View mBehaviorTouchView;
   private final DirectedAcyclicGraph<View> mChildDag;
   private final List<View> mDependencySortedChildren;
   private boolean mDisallowInterceptReset;
   private boolean mDrawStatusBarBackground;
   private boolean mIsAttachedToWindow;
   private int[] mKeylines;
   private WindowInsetsCompat mLastInsets;
   private boolean mNeedsPreDrawListener;
   private final NestedScrollingParentHelper mNestedScrollingParentHelper;
   private View mNestedScrollingTarget;
   private final int[] mNestedScrollingV2ConsumedCompat;
   OnHierarchyChangeListener mOnHierarchyChangeListener;
   private CoordinatorLayout.OnPreDrawListener mOnPreDrawListener;
   private Paint mScrimPaint;
   private Drawable mStatusBarBackground;
   private final List<View> mTempDependenciesList;
   private final List<View> mTempList1;

   static {
      Package var0 = CoordinatorLayout.class.getPackage();
      String var1;
      if (var0 != null) {
         var1 = var0.getName();
      } else {
         var1 = null;
      }

      WIDGET_PACKAGE_NAME = var1;
      if (VERSION.SDK_INT >= 21) {
         TOP_SORTED_CHILDREN_COMPARATOR = new CoordinatorLayout.ViewElevationComparator();
      } else {
         TOP_SORTED_CHILDREN_COMPARATOR = null;
      }

      CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
      sConstructors = new ThreadLocal();
      sRectPool = new Pools.SynchronizedPool(12);
   }

   public CoordinatorLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public CoordinatorLayout(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.coordinatorLayoutStyle);
   }

   public CoordinatorLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mDependencySortedChildren = new ArrayList();
      this.mChildDag = new DirectedAcyclicGraph();
      this.mTempList1 = new ArrayList();
      this.mTempDependenciesList = new ArrayList();
      this.mBehaviorConsumed = new int[2];
      this.mNestedScrollingV2ConsumedCompat = new int[2];
      this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
      byte var4 = 0;
      TypedArray var5;
      if (var3 == 0) {
         var5 = var1.obtainStyledAttributes(var2, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout);
      } else {
         var5 = var1.obtainStyledAttributes(var2, R.styleable.CoordinatorLayout, var3, 0);
      }

      if (VERSION.SDK_INT >= 29) {
         if (var3 == 0) {
            this.saveAttributeDataForStyleable(var1, R.styleable.CoordinatorLayout, var2, var5, 0, R.style.Widget_Support_CoordinatorLayout);
         } else {
            this.saveAttributeDataForStyleable(var1, R.styleable.CoordinatorLayout, var2, var5, var3, 0);
         }
      }

      var3 = var5.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
      if (var3 != 0) {
         Resources var8 = var1.getResources();
         this.mKeylines = var8.getIntArray(var3);
         float var6 = var8.getDisplayMetrics().density;
         int var7 = this.mKeylines.length;

         for(var3 = var4; var3 < var7; ++var3) {
            int[] var9 = this.mKeylines;
            var9[var3] = (int)((float)var9[var3] * var6);
         }
      }

      this.mStatusBarBackground = var5.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
      var5.recycle();
      this.setupForInsets();
      super.setOnHierarchyChangeListener(new CoordinatorLayout.HierarchyChangeListener());
      if (ViewCompat.getImportantForAccessibility(this) == 0) {
         ViewCompat.setImportantForAccessibility(this, 1);
      }

   }

   private static Rect acquireTempRect() {
      Rect var0 = (Rect)sRectPool.acquire();
      Rect var1 = var0;
      if (var0 == null) {
         var1 = new Rect();
      }

      return var1;
   }

   private static int clamp(int var0, int var1, int var2) {
      if (var0 < var1) {
         return var1;
      } else {
         return var0 > var2 ? var2 : var0;
      }
   }

   private void constrainChildRect(CoordinatorLayout.LayoutParams var1, Rect var2, int var3, int var4) {
      int var5 = this.getWidth();
      int var6 = this.getHeight();
      var5 = Math.max(this.getPaddingLeft() + var1.leftMargin, Math.min(var2.left, var5 - this.getPaddingRight() - var3 - var1.rightMargin));
      var6 = Math.max(this.getPaddingTop() + var1.topMargin, Math.min(var2.top, var6 - this.getPaddingBottom() - var4 - var1.bottomMargin));
      var2.set(var5, var6, var3 + var5, var4 + var6);
   }

   private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat var1) {
      if (var1.isConsumed()) {
         return var1;
      } else {
         int var2 = 0;
         int var3 = this.getChildCount();

         WindowInsetsCompat var4;
         while(true) {
            var4 = var1;
            if (var2 >= var3) {
               break;
            }

            View var5 = this.getChildAt(var2);
            var4 = var1;
            if (ViewCompat.getFitsSystemWindows(var5)) {
               CoordinatorLayout.Behavior var6 = ((CoordinatorLayout.LayoutParams)var5.getLayoutParams()).getBehavior();
               var4 = var1;
               if (var6 != null) {
                  var1 = var6.onApplyWindowInsets(this, var5, var1);
                  var4 = var1;
                  if (var1.isConsumed()) {
                     var4 = var1;
                     break;
                  }
               }
            }

            ++var2;
            var1 = var4;
         }

         return var4;
      }
   }

   private void getDesiredAnchoredChildRectWithoutConstraints(View var1, int var2, Rect var3, Rect var4, CoordinatorLayout.LayoutParams var5, int var6, int var7) {
      int var8 = GravityCompat.getAbsoluteGravity(resolveAnchoredChildGravity(var5.gravity), var2);
      var2 = GravityCompat.getAbsoluteGravity(resolveGravity(var5.anchorGravity), var2);
      int var9 = var8 & 7;
      int var10 = var8 & 112;
      int var11 = var2 & 7;
      var8 = var2 & 112;
      if (var11 != 1) {
         if (var11 != 5) {
            var2 = var3.left;
         } else {
            var2 = var3.right;
         }
      } else {
         var2 = var3.left + var3.width() / 2;
      }

      if (var8 != 16) {
         if (var8 != 80) {
            var8 = var3.top;
         } else {
            var8 = var3.bottom;
         }
      } else {
         var8 = var3.top + var3.height() / 2;
      }

      if (var9 != 1) {
         var11 = var2;
         if (var9 != 5) {
            var11 = var2 - var6;
         }
      } else {
         var11 = var2 - var6 / 2;
      }

      if (var10 != 16) {
         var2 = var8;
         if (var10 != 80) {
            var2 = var8 - var7;
         }
      } else {
         var2 = var8 - var7 / 2;
      }

      var4.set(var11, var2, var6 + var11, var7 + var2);
   }

   private int getKeyline(int var1) {
      int[] var2 = this.mKeylines;
      StringBuilder var3;
      if (var2 == null) {
         var3 = new StringBuilder();
         var3.append("No keylines defined for ");
         var3.append(this);
         var3.append(" - attempted index lookup ");
         var3.append(var1);
         Log.e("CoordinatorLayout", var3.toString());
         return 0;
      } else if (var1 >= 0 && var1 < var2.length) {
         return var2[var1];
      } else {
         var3 = new StringBuilder();
         var3.append("Keyline index ");
         var3.append(var1);
         var3.append(" out of range for ");
         var3.append(this);
         Log.e("CoordinatorLayout", var3.toString());
         return 0;
      }
   }

   private void getTopSortedChildren(List<View> var1) {
      var1.clear();
      boolean var2 = this.isChildrenDrawingOrderEnabled();
      int var3 = this.getChildCount();

      for(int var4 = var3 - 1; var4 >= 0; --var4) {
         int var5;
         if (var2) {
            var5 = this.getChildDrawingOrder(var3, var4);
         } else {
            var5 = var4;
         }

         var1.add(this.getChildAt(var5));
      }

      Comparator var6 = TOP_SORTED_CHILDREN_COMPARATOR;
      if (var6 != null) {
         Collections.sort(var1, var6);
      }

   }

   private boolean hasDependencies(View var1) {
      return this.mChildDag.hasOutgoingEdges(var1);
   }

   private void layoutChild(View var1, int var2) {
      CoordinatorLayout.LayoutParams var3 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      Rect var4 = acquireTempRect();
      var4.set(this.getPaddingLeft() + var3.leftMargin, this.getPaddingTop() + var3.topMargin, this.getWidth() - this.getPaddingRight() - var3.rightMargin, this.getHeight() - this.getPaddingBottom() - var3.bottomMargin);
      if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this) && !ViewCompat.getFitsSystemWindows(var1)) {
         var4.left += this.mLastInsets.getSystemWindowInsetLeft();
         var4.top += this.mLastInsets.getSystemWindowInsetTop();
         var4.right -= this.mLastInsets.getSystemWindowInsetRight();
         var4.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
      }

      Rect var5 = acquireTempRect();
      GravityCompat.apply(resolveGravity(var3.gravity), var1.getMeasuredWidth(), var1.getMeasuredHeight(), var4, var5, var2);
      var1.layout(var5.left, var5.top, var5.right, var5.bottom);
      releaseTempRect(var4);
      releaseTempRect(var5);
   }

   private void layoutChildWithAnchor(View var1, View var2, int var3) {
      Rect var4 = acquireTempRect();
      Rect var5 = acquireTempRect();

      try {
         this.getDescendantRect(var2, var4);
         this.getDesiredAnchoredChildRect(var1, var3, var4, var5);
         var1.layout(var5.left, var5.top, var5.right, var5.bottom);
      } finally {
         releaseTempRect(var4);
         releaseTempRect(var5);
      }

   }

   private void layoutChildWithKeyline(View var1, int var2, int var3) {
      CoordinatorLayout.LayoutParams var4 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      int var5 = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(var4.gravity), var3);
      int var6 = var5 & 7;
      int var7 = var5 & 112;
      int var8 = this.getWidth();
      int var9 = this.getHeight();
      int var10 = var1.getMeasuredWidth();
      int var11 = var1.getMeasuredHeight();
      var5 = var2;
      if (var3 == 1) {
         var5 = var8 - var2;
      }

      var2 = this.getKeyline(var5) - var10;
      var3 = 0;
      if (var6 != 1) {
         if (var6 == 5) {
            var2 += var10;
         }
      } else {
         var2 += var10 / 2;
      }

      if (var7 != 16) {
         if (var7 == 80) {
            var3 = var11 + 0;
         }
      } else {
         var3 = 0 + var11 / 2;
      }

      var2 = Math.max(this.getPaddingLeft() + var4.leftMargin, Math.min(var2, var8 - this.getPaddingRight() - var10 - var4.rightMargin));
      var3 = Math.max(this.getPaddingTop() + var4.topMargin, Math.min(var3, var9 - this.getPaddingBottom() - var11 - var4.bottomMargin));
      var1.layout(var2, var3, var10 + var2, var11 + var3);
   }

   private void offsetChildByInset(View var1, Rect var2, int var3) {
      if (ViewCompat.isLaidOut(var1)) {
         if (var1.getWidth() > 0 && var1.getHeight() > 0) {
            CoordinatorLayout.LayoutParams var4 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
            CoordinatorLayout.Behavior var5 = var4.getBehavior();
            Rect var6 = acquireTempRect();
            Rect var7 = acquireTempRect();
            var7.set(var1.getLeft(), var1.getTop(), var1.getRight(), var1.getBottom());
            if (var5 != null && var5.getInsetDodgeRect(this, var1, var6)) {
               if (!var7.contains(var6)) {
                  StringBuilder var12 = new StringBuilder();
                  var12.append("Rect should be within the child's bounds. Rect:");
                  var12.append(var6.toShortString());
                  var12.append(" | Bounds:");
                  var12.append(var7.toShortString());
                  throw new IllegalArgumentException(var12.toString());
               }
            } else {
               var6.set(var7);
            }

            releaseTempRect(var7);
            if (var6.isEmpty()) {
               releaseTempRect(var6);
               return;
            }

            int var8;
            boolean var9;
            boolean var13;
            label58: {
               var8 = GravityCompat.getAbsoluteGravity(var4.dodgeInsetEdges, var3);
               var9 = true;
               if ((var8 & 48) == 48) {
                  var3 = var6.top - var4.topMargin - var4.mInsetOffsetY;
                  if (var3 < var2.top) {
                     this.setInsetOffsetY(var1, var2.top - var3);
                     var13 = true;
                     break label58;
                  }
               }

               var13 = false;
            }

            boolean var10 = var13;
            if ((var8 & 80) == 80) {
               int var11 = this.getHeight() - var6.bottom - var4.bottomMargin + var4.mInsetOffsetY;
               var10 = var13;
               if (var11 < var2.bottom) {
                  this.setInsetOffsetY(var1, var11 - var2.bottom);
                  var10 = true;
               }
            }

            if (!var10) {
               this.setInsetOffsetY(var1, 0);
            }

            label50: {
               if ((var8 & 3) == 3) {
                  var3 = var6.left - var4.leftMargin - var4.mInsetOffsetX;
                  if (var3 < var2.left) {
                     this.setInsetOffsetX(var1, var2.left - var3);
                     var13 = true;
                     break label50;
                  }
               }

               var13 = false;
            }

            if ((var8 & 5) == 5) {
               int var14 = this.getWidth() - var6.right - var4.rightMargin + var4.mInsetOffsetX;
               if (var14 < var2.right) {
                  this.setInsetOffsetX(var1, var14 - var2.right);
                  var13 = var9;
               }
            }

            if (!var13) {
               this.setInsetOffsetX(var1, 0);
            }

            releaseTempRect(var6);
         }

      }
   }

   static CoordinatorLayout.Behavior parseBehavior(Context var0, AttributeSet var1, String var2) {
      if (TextUtils.isEmpty(var2)) {
         return null;
      } else {
         StringBuilder var3;
         String var15;
         if (var2.startsWith(".")) {
            var3 = new StringBuilder();
            var3.append(var0.getPackageName());
            var3.append(var2);
            var15 = var3.toString();
         } else if (var2.indexOf(46) >= 0) {
            var15 = var2;
         } else {
            var15 = var2;
            if (!TextUtils.isEmpty(WIDGET_PACKAGE_NAME)) {
               var3 = new StringBuilder();
               var3.append(WIDGET_PACKAGE_NAME);
               var3.append('.');
               var3.append(var2);
               var15 = var3.toString();
            }
         }

         Exception var10000;
         label57: {
            Map var4;
            boolean var10001;
            try {
               var4 = (Map)sConstructors.get();
            } catch (Exception var10) {
               var10000 = var10;
               var10001 = false;
               break label57;
            }

            Object var14 = var4;
            if (var4 == null) {
               try {
                  var14 = new HashMap();
                  sConstructors.set(var14);
               } catch (Exception var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label57;
               }
            }

            Constructor var5;
            try {
               var5 = (Constructor)((Map)var14).get(var15);
            } catch (Exception var8) {
               var10000 = var8;
               var10001 = false;
               break label57;
            }

            Constructor var16 = var5;
            if (var5 == null) {
               try {
                  var16 = Class.forName(var15, false, var0.getClassLoader()).getConstructor(CONSTRUCTOR_PARAMS);
                  var16.setAccessible(true);
                  ((Map)var14).put(var15, var16);
               } catch (Exception var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label57;
               }
            }

            try {
               CoordinatorLayout.Behavior var12 = (CoordinatorLayout.Behavior)var16.newInstance(var0, var1);
               return var12;
            } catch (Exception var6) {
               var10000 = var6;
               var10001 = false;
            }
         }

         Exception var13 = var10000;
         StringBuilder var11 = new StringBuilder();
         var11.append("Could not inflate Behavior subclass ");
         var11.append(var15);
         throw new RuntimeException(var11.toString(), var13);
      }
   }

   private boolean performIntercept(MotionEvent var1, int var2) {
      int var3 = var1.getActionMasked();
      List var4 = this.mTempList1;
      this.getTopSortedChildren(var4);
      int var5 = var4.size();
      MotionEvent var6 = null;
      int var7 = 0;
      boolean var8 = false;
      boolean var9 = false;

      boolean var10;
      while(true) {
         var10 = var8;
         if (var7 >= var5) {
            break;
         }

         View var11 = (View)var4.get(var7);
         CoordinatorLayout.LayoutParams var12 = (CoordinatorLayout.LayoutParams)var11.getLayoutParams();
         CoordinatorLayout.Behavior var13 = var12.getBehavior();
         boolean var14;
         boolean var15;
         MotionEvent var18;
         if ((var8 || var9) && var3 != 0) {
            var18 = var6;
            var14 = var8;
            var15 = var9;
            if (var13 != null) {
               var18 = var6;
               if (var6 == null) {
                  long var16 = SystemClock.uptimeMillis();
                  var18 = MotionEvent.obtain(var16, var16, 3, 0.0F, 0.0F, 0);
               }

               if (var2 != 0) {
                  if (var2 != 1) {
                     var14 = var8;
                     var15 = var9;
                  } else {
                     var13.onTouchEvent(this, var11, var18);
                     var14 = var8;
                     var15 = var9;
                  }
               } else {
                  var13.onInterceptTouchEvent(this, var11, var18);
                  var14 = var8;
                  var15 = var9;
               }
            }
         } else {
            var10 = var8;
            if (!var8) {
               var10 = var8;
               if (var13 != null) {
                  if (var2 != 0) {
                     if (var2 == 1) {
                        var8 = var13.onTouchEvent(this, var11, var1);
                     }
                  } else {
                     var8 = var13.onInterceptTouchEvent(this, var11, var1);
                  }

                  var10 = var8;
                  if (var8) {
                     this.mBehaviorTouchView = var11;
                     var10 = var8;
                  }
               }
            }

            var14 = var12.didBlockInteraction();
            var8 = var12.isBlockingInteractionBelow(this, var11);
            if (var8 && !var14) {
               var9 = true;
            } else {
               var9 = false;
            }

            var18 = var6;
            var14 = var10;
            var15 = var9;
            if (var8) {
               var18 = var6;
               var14 = var10;
               var15 = var9;
               if (!var9) {
                  break;
               }
            }
         }

         ++var7;
         var6 = var18;
         var8 = var14;
         var9 = var15;
      }

      var4.clear();
      return var10;
   }

   private void prepareChildren() {
      this.mDependencySortedChildren.clear();
      this.mChildDag.clear();
      int var1 = this.getChildCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         View var3 = this.getChildAt(var2);
         CoordinatorLayout.LayoutParams var4 = this.getResolvedLayoutParams(var3);
         var4.findAnchorView(this, var3);
         this.mChildDag.addNode(var3);

         for(int var5 = 0; var5 < var1; ++var5) {
            if (var5 != var2) {
               View var6 = this.getChildAt(var5);
               if (var4.dependsOn(this, var3, var6)) {
                  if (!this.mChildDag.contains(var6)) {
                     this.mChildDag.addNode(var6);
                  }

                  this.mChildDag.addEdge(var6, var3);
               }
            }
         }
      }

      this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
      Collections.reverse(this.mDependencySortedChildren);
   }

   private static void releaseTempRect(Rect var0) {
      var0.setEmpty();
      sRectPool.release(var0);
   }

   private void resetTouchBehaviors(boolean var1) {
      int var2 = this.getChildCount();

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         View var4 = this.getChildAt(var3);
         CoordinatorLayout.Behavior var5 = ((CoordinatorLayout.LayoutParams)var4.getLayoutParams()).getBehavior();
         if (var5 != null) {
            long var6 = SystemClock.uptimeMillis();
            MotionEvent var8 = MotionEvent.obtain(var6, var6, 3, 0.0F, 0.0F, 0);
            if (var1) {
               var5.onInterceptTouchEvent(this, var4, var8);
            } else {
               var5.onTouchEvent(this, var4, var8);
            }

            var8.recycle();
         }
      }

      for(var3 = 0; var3 < var2; ++var3) {
         ((CoordinatorLayout.LayoutParams)this.getChildAt(var3).getLayoutParams()).resetTouchBehaviorTracking();
      }

      this.mBehaviorTouchView = null;
      this.mDisallowInterceptReset = false;
   }

   private static int resolveAnchoredChildGravity(int var0) {
      int var1 = var0;
      if (var0 == 0) {
         var1 = 17;
      }

      return var1;
   }

   private static int resolveGravity(int var0) {
      int var1 = var0;
      if ((var0 & 7) == 0) {
         var1 = var0 | 8388611;
      }

      var0 = var1;
      if ((var1 & 112) == 0) {
         var0 = var1 | 48;
      }

      return var0;
   }

   private static int resolveKeylineGravity(int var0) {
      int var1 = var0;
      if (var0 == 0) {
         var1 = 8388661;
      }

      return var1;
   }

   private void setInsetOffsetX(View var1, int var2) {
      CoordinatorLayout.LayoutParams var3 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      if (var3.mInsetOffsetX != var2) {
         ViewCompat.offsetLeftAndRight(var1, var2 - var3.mInsetOffsetX);
         var3.mInsetOffsetX = var2;
      }

   }

   private void setInsetOffsetY(View var1, int var2) {
      CoordinatorLayout.LayoutParams var3 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      if (var3.mInsetOffsetY != var2) {
         ViewCompat.offsetTopAndBottom(var1, var2 - var3.mInsetOffsetY);
         var3.mInsetOffsetY = var2;
      }

   }

   private void setupForInsets() {
      if (VERSION.SDK_INT >= 21) {
         if (ViewCompat.getFitsSystemWindows(this)) {
            if (this.mApplyWindowInsetsListener == null) {
               this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener() {
                  public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2) {
                     return CoordinatorLayout.this.setWindowInsets(var2);
                  }
               };
            }

            ViewCompat.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener);
            this.setSystemUiVisibility(1280);
         } else {
            ViewCompat.setOnApplyWindowInsetsListener(this, (OnApplyWindowInsetsListener)null);
         }

      }
   }

   void addPreDrawListener() {
      if (this.mIsAttachedToWindow) {
         if (this.mOnPreDrawListener == null) {
            this.mOnPreDrawListener = new CoordinatorLayout.OnPreDrawListener();
         }

         this.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
      }

      this.mNeedsPreDrawListener = true;
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      boolean var2;
      if (var1 instanceof CoordinatorLayout.LayoutParams && super.checkLayoutParams(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void dispatchDependentViewsChanged(View var1) {
      List var2 = this.mChildDag.getIncomingEdges(var1);
      if (var2 != null && !var2.isEmpty()) {
         for(int var3 = 0; var3 < var2.size(); ++var3) {
            View var4 = (View)var2.get(var3);
            CoordinatorLayout.Behavior var5 = ((CoordinatorLayout.LayoutParams)var4.getLayoutParams()).getBehavior();
            if (var5 != null) {
               var5.onDependentViewChanged(this, var4, var1);
            }
         }
      }

   }

   public boolean doViewsOverlap(View var1, View var2) {
      int var3 = var1.getVisibility();
      boolean var4 = false;
      if (var3 == 0 && var2.getVisibility() == 0) {
         Rect var5 = acquireTempRect();
         boolean var6;
         if (var1.getParent() != this) {
            var6 = true;
         } else {
            var6 = false;
         }

         this.getChildRect(var1, var6, var5);
         Rect var20 = acquireTempRect();
         if (var2.getParent() != this) {
            var6 = true;
         } else {
            var6 = false;
         }

         this.getChildRect(var2, var6, var20);
         var6 = var4;

         label221: {
            int var7;
            label220: {
               Throwable var10000;
               label228: {
                  boolean var10001;
                  try {
                     if (var5.left > var20.right) {
                        break label221;
                     }
                  } catch (Throwable var19) {
                     var10000 = var19;
                     var10001 = false;
                     break label228;
                  }

                  var6 = var4;

                  try {
                     if (var5.top > var20.bottom) {
                        break label221;
                     }
                  } catch (Throwable var18) {
                     var10000 = var18;
                     var10001 = false;
                     break label228;
                  }

                  var6 = var4;

                  label211:
                  try {
                     if (var5.right < var20.left) {
                        break label221;
                     }

                     var7 = var5.bottom;
                     var3 = var20.top;
                     break label220;
                  } catch (Throwable var17) {
                     var10000 = var17;
                     var10001 = false;
                     break label211;
                  }
               }

               Throwable var21 = var10000;
               releaseTempRect(var5);
               releaseTempRect(var20);
               throw var21;
            }

            var6 = var4;
            if (var7 >= var3) {
               var6 = true;
            }
         }

         releaseTempRect(var5);
         releaseTempRect(var20);
         return var6;
      } else {
         return false;
      }
   }

   protected boolean drawChild(Canvas var1, View var2, long var3) {
      CoordinatorLayout.LayoutParams var5 = (CoordinatorLayout.LayoutParams)var2.getLayoutParams();
      if (var5.mBehavior != null) {
         float var6 = var5.mBehavior.getScrimOpacity(this, var2);
         if (var6 > 0.0F) {
            if (this.mScrimPaint == null) {
               this.mScrimPaint = new Paint();
            }

            this.mScrimPaint.setColor(var5.mBehavior.getScrimColor(this, var2));
            this.mScrimPaint.setAlpha(clamp(Math.round(var6 * 255.0F), 0, 255));
            int var7 = var1.save();
            if (var2.isOpaque()) {
               var1.clipRect((float)var2.getLeft(), (float)var2.getTop(), (float)var2.getRight(), (float)var2.getBottom(), Op.DIFFERENCE);
            }

            var1.drawRect((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()), this.mScrimPaint);
            var1.restoreToCount(var7);
         }
      }

      return super.drawChild(var1, var2, var3);
   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      int[] var1 = this.getDrawableState();
      Drawable var2 = this.mStatusBarBackground;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2 != null) {
         var4 = var3;
         if (var2.isStateful()) {
            var4 = false | var2.setState(var1);
         }
      }

      if (var4) {
         this.invalidate();
      }

   }

   void ensurePreDrawListener() {
      int var1 = this.getChildCount();
      boolean var2 = false;
      int var3 = 0;

      boolean var4;
      while(true) {
         var4 = var2;
         if (var3 >= var1) {
            break;
         }

         if (this.hasDependencies(this.getChildAt(var3))) {
            var4 = true;
            break;
         }

         ++var3;
      }

      if (var4 != this.mNeedsPreDrawListener) {
         if (var4) {
            this.addPreDrawListener();
         } else {
            this.removePreDrawListener();
         }
      }

   }

   protected CoordinatorLayout.LayoutParams generateDefaultLayoutParams() {
      return new CoordinatorLayout.LayoutParams(-2, -2);
   }

   public CoordinatorLayout.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new CoordinatorLayout.LayoutParams(this.getContext(), var1);
   }

   protected CoordinatorLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      if (var1 instanceof CoordinatorLayout.LayoutParams) {
         return new CoordinatorLayout.LayoutParams((CoordinatorLayout.LayoutParams)var1);
      } else {
         return var1 instanceof MarginLayoutParams ? new CoordinatorLayout.LayoutParams((MarginLayoutParams)var1) : new CoordinatorLayout.LayoutParams(var1);
      }
   }

   void getChildRect(View var1, boolean var2, Rect var3) {
      if (!var1.isLayoutRequested() && var1.getVisibility() != 8) {
         if (var2) {
            this.getDescendantRect(var1, var3);
         } else {
            var3.set(var1.getLeft(), var1.getTop(), var1.getRight(), var1.getBottom());
         }

      } else {
         var3.setEmpty();
      }
   }

   public List<View> getDependencies(View var1) {
      List var2 = this.mChildDag.getOutgoingEdges(var1);
      this.mTempDependenciesList.clear();
      if (var2 != null) {
         this.mTempDependenciesList.addAll(var2);
      }

      return this.mTempDependenciesList;
   }

   final List<View> getDependencySortedChildren() {
      this.prepareChildren();
      return Collections.unmodifiableList(this.mDependencySortedChildren);
   }

   public List<View> getDependents(View var1) {
      List var2 = this.mChildDag.getIncomingEdges(var1);
      this.mTempDependenciesList.clear();
      if (var2 != null) {
         this.mTempDependenciesList.addAll(var2);
      }

      return this.mTempDependenciesList;
   }

   void getDescendantRect(View var1, Rect var2) {
      ViewGroupUtils.getDescendantRect(this, var1, var2);
   }

   void getDesiredAnchoredChildRect(View var1, int var2, Rect var3, Rect var4) {
      CoordinatorLayout.LayoutParams var5 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      int var6 = var1.getMeasuredWidth();
      int var7 = var1.getMeasuredHeight();
      this.getDesiredAnchoredChildRectWithoutConstraints(var1, var2, var3, var4, var5, var6, var7);
      this.constrainChildRect(var5, var4, var6, var7);
   }

   void getLastChildRect(View var1, Rect var2) {
      var2.set(((CoordinatorLayout.LayoutParams)var1.getLayoutParams()).getLastChildRect());
   }

   public final WindowInsetsCompat getLastWindowInsets() {
      return this.mLastInsets;
   }

   public int getNestedScrollAxes() {
      return this.mNestedScrollingParentHelper.getNestedScrollAxes();
   }

   CoordinatorLayout.LayoutParams getResolvedLayoutParams(View var1) {
      CoordinatorLayout.LayoutParams var2 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      if (!var2.mBehaviorResolved) {
         if (var1 instanceof CoordinatorLayout.AttachedBehavior) {
            CoordinatorLayout.Behavior var6 = ((CoordinatorLayout.AttachedBehavior)var1).getBehavior();
            if (var6 == null) {
               Log.e("CoordinatorLayout", "Attached behavior class is null");
            }

            var2.setBehavior(var6);
            var2.mBehaviorResolved = true;
         } else {
            Class var3 = var1.getClass();

            CoordinatorLayout.DefaultBehavior var4;
            CoordinatorLayout.DefaultBehavior var7;
            for(var7 = null; var3 != null; var7 = var4) {
               var4 = (CoordinatorLayout.DefaultBehavior)var3.getAnnotation(CoordinatorLayout.DefaultBehavior.class);
               var7 = var4;
               if (var4 != null) {
                  break;
               }

               var3 = var3.getSuperclass();
            }

            if (var7 != null) {
               try {
                  var2.setBehavior((CoordinatorLayout.Behavior)var7.value().getDeclaredConstructor().newInstance());
               } catch (Exception var5) {
                  StringBuilder var8 = new StringBuilder();
                  var8.append("Default behavior class ");
                  var8.append(var7.value().getName());
                  var8.append(" could not be instantiated. Did you forget a default constructor?");
                  Log.e("CoordinatorLayout", var8.toString(), var5);
               }
            }

            var2.mBehaviorResolved = true;
         }
      }

      return var2;
   }

   public Drawable getStatusBarBackground() {
      return this.mStatusBarBackground;
   }

   protected int getSuggestedMinimumHeight() {
      return Math.max(super.getSuggestedMinimumHeight(), this.getPaddingTop() + this.getPaddingBottom());
   }

   protected int getSuggestedMinimumWidth() {
      return Math.max(super.getSuggestedMinimumWidth(), this.getPaddingLeft() + this.getPaddingRight());
   }

   public boolean isPointInChildBounds(View var1, int var2, int var3) {
      Rect var4 = acquireTempRect();
      this.getDescendantRect(var1, var4);

      boolean var5;
      try {
         var5 = var4.contains(var2, var3);
      } finally {
         releaseTempRect(var4);
      }

      return var5;
   }

   void offsetChildToAnchor(View var1, int var2) {
      CoordinatorLayout.LayoutParams var3 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      if (var3.mAnchorView != null) {
         Rect var4;
         Rect var5;
         Rect var6;
         int var8;
         int var9;
         boolean var11;
         label27: {
            var4 = acquireTempRect();
            var5 = acquireTempRect();
            var6 = acquireTempRect();
            this.getDescendantRect(var3.mAnchorView, var4);
            boolean var7 = false;
            this.getChildRect(var1, false, var5);
            var8 = var1.getMeasuredWidth();
            var9 = var1.getMeasuredHeight();
            this.getDesiredAnchoredChildRectWithoutConstraints(var1, var2, var4, var6, var3, var8, var9);
            if (var6.left == var5.left) {
               var11 = var7;
               if (var6.top == var5.top) {
                  break label27;
               }
            }

            var11 = true;
         }

         this.constrainChildRect(var3, var6, var8, var9);
         var9 = var6.left - var5.left;
         int var12 = var6.top - var5.top;
         if (var9 != 0) {
            ViewCompat.offsetLeftAndRight(var1, var9);
         }

         if (var12 != 0) {
            ViewCompat.offsetTopAndBottom(var1, var12);
         }

         if (var11) {
            CoordinatorLayout.Behavior var10 = var3.getBehavior();
            if (var10 != null) {
               var10.onDependentViewChanged(this, var1, var3.mAnchorView);
            }
         }

         releaseTempRect(var4);
         releaseTempRect(var5);
         releaseTempRect(var6);
      }

   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.resetTouchBehaviors(false);
      if (this.mNeedsPreDrawListener) {
         if (this.mOnPreDrawListener == null) {
            this.mOnPreDrawListener = new CoordinatorLayout.OnPreDrawListener();
         }

         this.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
      }

      if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows(this)) {
         ViewCompat.requestApplyInsets(this);
      }

      this.mIsAttachedToWindow = true;
   }

   final void onChildViewsChanged(int var1) {
      int var2 = ViewCompat.getLayoutDirection(this);
      int var3 = this.mDependencySortedChildren.size();
      Rect var4 = acquireTempRect();
      Rect var5 = acquireTempRect();
      Rect var6 = acquireTempRect();

      for(int var7 = 0; var7 < var3; ++var7) {
         View var8 = (View)this.mDependencySortedChildren.get(var7);
         CoordinatorLayout.LayoutParams var9 = (CoordinatorLayout.LayoutParams)var8.getLayoutParams();
         if (var1 != 0 || var8.getVisibility() != 8) {
            int var10;
            for(var10 = 0; var10 < var7; ++var10) {
               View var11 = (View)this.mDependencySortedChildren.get(var10);
               if (var9.mAnchorDirectChild == var11) {
                  this.offsetChildToAnchor(var8, var2);
               }
            }

            this.getChildRect(var8, true, var5);
            if (var9.insetEdge != 0 && !var5.isEmpty()) {
               int var12 = GravityCompat.getAbsoluteGravity(var9.insetEdge, var2);
               var10 = var12 & 112;
               if (var10 != 48) {
                  if (var10 == 80) {
                     var4.bottom = Math.max(var4.bottom, this.getHeight() - var5.top);
                  }
               } else {
                  var4.top = Math.max(var4.top, var5.bottom);
               }

               var10 = var12 & 7;
               if (var10 != 3) {
                  if (var10 == 5) {
                     var4.right = Math.max(var4.right, this.getWidth() - var5.left);
                  }
               } else {
                  var4.left = Math.max(var4.left, var5.right);
               }
            }

            if (var9.dodgeInsetEdges != 0 && var8.getVisibility() == 0) {
               this.offsetChildByInset(var8, var4, var2);
            }

            if (var1 != 2) {
               this.getLastChildRect(var8, var6);
               if (var6.equals(var5)) {
                  continue;
               }

               this.recordLastChildRect(var8, var5);
            }

            for(var10 = var7 + 1; var10 < var3; ++var10) {
               View var13 = (View)this.mDependencySortedChildren.get(var10);
               var9 = (CoordinatorLayout.LayoutParams)var13.getLayoutParams();
               CoordinatorLayout.Behavior var15 = var9.getBehavior();
               if (var15 != null && var15.layoutDependsOn(this, var13, var8)) {
                  if (var1 == 0 && var9.getChangedAfterNestedScroll()) {
                     var9.resetChangedAfterNestedScroll();
                  } else {
                     boolean var14;
                     if (var1 != 2) {
                        var14 = var15.onDependentViewChanged(this, var13, var8);
                     } else {
                        var15.onDependentViewRemoved(this, var13, var8);
                        var14 = true;
                     }

                     if (var1 == 1) {
                        var9.setChangedAfterNestedScroll(var14);
                     }
                  }
               }
            }
         }
      }

      releaseTempRect(var4);
      releaseTempRect(var5);
      releaseTempRect(var6);
   }

   public void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.resetTouchBehaviors(false);
      if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
         this.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
      }

      View var1 = this.mNestedScrollingTarget;
      if (var1 != null) {
         this.onStopNestedScroll(var1);
      }

      this.mIsAttachedToWindow = false;
   }

   public void onDraw(Canvas var1) {
      super.onDraw(var1);
      if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
         WindowInsetsCompat var2 = this.mLastInsets;
         int var3;
         if (var2 != null) {
            var3 = var2.getSystemWindowInsetTop();
         } else {
            var3 = 0;
         }

         if (var3 > 0) {
            this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), var3);
            this.mStatusBarBackground.draw(var1);
         }
      }

   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      int var2 = var1.getActionMasked();
      if (var2 == 0) {
         this.resetTouchBehaviors(true);
      }

      boolean var3 = this.performIntercept(var1, 0);
      if (var2 == 1 || var2 == 3) {
         this.resetTouchBehaviors(true);
      }

      return var3;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      var4 = ViewCompat.getLayoutDirection(this);
      var3 = this.mDependencySortedChildren.size();

      for(var2 = 0; var2 < var3; ++var2) {
         View var6 = (View)this.mDependencySortedChildren.get(var2);
         if (var6.getVisibility() != 8) {
            CoordinatorLayout.Behavior var7 = ((CoordinatorLayout.LayoutParams)var6.getLayoutParams()).getBehavior();
            if (var7 == null || !var7.onLayoutChild(this, var6, var4)) {
               this.onLayoutChild(var6, var4);
            }
         }
      }

   }

   public void onLayoutChild(View var1, int var2) {
      CoordinatorLayout.LayoutParams var3 = (CoordinatorLayout.LayoutParams)var1.getLayoutParams();
      if (!var3.checkAnchorChanged()) {
         if (var3.mAnchorView != null) {
            this.layoutChildWithAnchor(var1, var3.mAnchorView, var2);
         } else if (var3.keyline >= 0) {
            this.layoutChildWithKeyline(var1, var3.keyline, var2);
         } else {
            this.layoutChild(var1, var2);
         }

      } else {
         throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
      }
   }

   protected void onMeasure(int var1, int var2) {
      this.prepareChildren();
      this.ensurePreDrawListener();
      int var3 = this.getPaddingLeft();
      int var4 = this.getPaddingTop();
      int var5 = this.getPaddingRight();
      int var6 = this.getPaddingBottom();
      int var7 = ViewCompat.getLayoutDirection(this);
      boolean var8;
      if (var7 == 1) {
         var8 = true;
      } else {
         var8 = false;
      }

      int var9 = MeasureSpec.getMode(var1);
      int var10 = MeasureSpec.getSize(var1);
      int var11 = MeasureSpec.getMode(var2);
      int var12 = MeasureSpec.getSize(var2);
      int var13 = this.getSuggestedMinimumWidth();
      int var14 = this.getSuggestedMinimumHeight();
      boolean var15;
      if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this)) {
         var15 = true;
      } else {
         var15 = false;
      }

      int var16 = this.mDependencySortedChildren.size();
      int var17 = 0;
      int var18 = 0;
      int var19 = var3;

      while(true) {
         int var20 = var19;
         if (var18 >= var16) {
            this.setMeasuredDimension(View.resolveSizeAndState(var13, var1, -16777216 & var17), View.resolveSizeAndState(var14, var2, var17 << 16));
            return;
         }

         View var21 = (View)this.mDependencySortedChildren.get(var18);
         if (var21.getVisibility() != 8) {
            CoordinatorLayout.LayoutParams var22;
            int var23;
            label73: {
               var22 = (CoordinatorLayout.LayoutParams)var21.getLayoutParams();
               if (var22.keyline >= 0 && var9 != 0) {
                  var19 = this.getKeyline(var22.keyline);
                  var23 = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(var22.gravity), var7) & 7;
                  if (var23 == 3 && !var8 || var23 == 5 && var8) {
                     var19 = Math.max(0, var10 - var5 - var19);
                     break label73;
                  }

                  if (var23 == 5 && !var8 || var23 == 3 && var8) {
                     var19 = Math.max(0, var19 - var20);
                     break label73;
                  }
               }

               var19 = 0;
            }

            int var26;
            if (var15 && !ViewCompat.getFitsSystemWindows(var21)) {
               int var25 = this.mLastInsets.getSystemWindowInsetLeft();
               var23 = this.mLastInsets.getSystemWindowInsetRight();
               var17 = this.mLastInsets.getSystemWindowInsetTop();
               var26 = this.mLastInsets.getSystemWindowInsetBottom();
               var23 = MeasureSpec.makeMeasureSpec(var10 - (var25 + var23), var9);
               var17 = MeasureSpec.makeMeasureSpec(var12 - (var17 + var26), var11);
               var26 = var17;
            } else {
               var26 = var2;
               var23 = var1;
            }

            CoordinatorLayout.Behavior var27 = var22.getBehavior();
            if (var27 == null || !var27.onMeasureChild(this, var21, var23, var19, var26, 0)) {
               this.onMeasureChild(var21, var23, var19, var26, 0);
            }

            var13 = Math.max(var13, var3 + var5 + var21.getMeasuredWidth() + var22.leftMargin + var22.rightMargin);
            var14 = Math.max(var14, var4 + var6 + var21.getMeasuredHeight() + var22.topMargin + var22.bottomMargin);
            var17 = View.combineMeasuredStates(var17, var21.getMeasuredState());
         }

         ++var18;
         var19 = var19;
      }
   }

   public void onMeasureChild(View var1, int var2, int var3, int var4, int var5) {
      this.measureChildWithMargins(var1, var2, var3, var4, var5);
   }

   public boolean onNestedFling(View var1, float var2, float var3, boolean var4) {
      int var5 = this.getChildCount();
      int var6 = 0;

      boolean var7;
      boolean var9;
      for(var7 = false; var6 < var5; var7 = var9) {
         View var8 = this.getChildAt(var6);
         if (var8.getVisibility() == 8) {
            var9 = var7;
         } else {
            CoordinatorLayout.LayoutParams var10 = (CoordinatorLayout.LayoutParams)var8.getLayoutParams();
            if (!var10.isNestedScrollAccepted(0)) {
               var9 = var7;
            } else {
               CoordinatorLayout.Behavior var11 = var10.getBehavior();
               var9 = var7;
               if (var11 != null) {
                  var9 = var7 | var11.onNestedFling(this, var8, var1, var2, var3, var4);
               }
            }
         }

         ++var6;
      }

      if (var7) {
         this.onChildViewsChanged(1);
      }

      return var7;
   }

   public boolean onNestedPreFling(View var1, float var2, float var3) {
      int var4 = this.getChildCount();
      int var5 = 0;

      boolean var6;
      boolean var8;
      for(var6 = false; var5 < var4; var6 = var8) {
         View var7 = this.getChildAt(var5);
         if (var7.getVisibility() == 8) {
            var8 = var6;
         } else {
            CoordinatorLayout.LayoutParams var9 = (CoordinatorLayout.LayoutParams)var7.getLayoutParams();
            if (!var9.isNestedScrollAccepted(0)) {
               var8 = var6;
            } else {
               CoordinatorLayout.Behavior var10 = var9.getBehavior();
               var8 = var6;
               if (var10 != null) {
                  var8 = var6 | var10.onNestedPreFling(this, var7, var1, var2, var3);
               }
            }
         }

         ++var5;
      }

      return var6;
   }

   public void onNestedPreScroll(View var1, int var2, int var3, int[] var4) {
      this.onNestedPreScroll(var1, var2, var3, var4, 0);
   }

   public void onNestedPreScroll(View var1, int var2, int var3, int[] var4, int var5) {
      int var6 = this.getChildCount();
      boolean var7 = false;
      int var8 = 0;
      int var9 = 0;

      int var10;
      int var13;
      for(var10 = 0; var8 < var6; var10 = var13) {
         View var11 = this.getChildAt(var8);
         int var12;
         if (var11.getVisibility() == 8) {
            var12 = var9;
            var13 = var10;
         } else {
            CoordinatorLayout.LayoutParams var14 = (CoordinatorLayout.LayoutParams)var11.getLayoutParams();
            if (!var14.isNestedScrollAccepted(var5)) {
               var12 = var9;
               var13 = var10;
            } else {
               CoordinatorLayout.Behavior var15 = var14.getBehavior();
               var12 = var9;
               var13 = var10;
               if (var15 != null) {
                  int[] var17 = this.mBehaviorConsumed;
                  var17[0] = 0;
                  var17[1] = 0;
                  var15.onNestedPreScroll(this, var11, var1, var2, var3, var17, var5);
                  int[] var16 = this.mBehaviorConsumed;
                  if (var2 > 0) {
                     var13 = Math.max(var9, var16[0]);
                  } else {
                     var13 = Math.min(var9, var16[0]);
                  }

                  var12 = var13;
                  var16 = this.mBehaviorConsumed;
                  if (var3 > 0) {
                     var13 = Math.max(var10, var16[1]);
                  } else {
                     var13 = Math.min(var10, var16[1]);
                  }

                  var7 = true;
               }
            }
         }

         ++var8;
         var9 = var12;
      }

      var4[0] = var9;
      var4[1] = var10;
      if (var7) {
         this.onChildViewsChanged(1);
      }

   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5) {
      this.onNestedScroll(var1, var2, var3, var4, var5, 0);
   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5, int var6) {
      this.onNestedScroll(var1, var2, var3, var4, var5, 0, this.mNestedScrollingV2ConsumedCompat);
   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      int var8 = this.getChildCount();
      boolean var9 = false;
      int var10 = 0;
      int var11 = 0;

      int var12;
      int var15;
      for(var12 = 0; var10 < var8; var12 = var15) {
         View var13 = this.getChildAt(var10);
         int var14;
         if (var13.getVisibility() == 8) {
            var14 = var11;
            var15 = var12;
         } else {
            CoordinatorLayout.LayoutParams var16 = (CoordinatorLayout.LayoutParams)var13.getLayoutParams();
            if (!var16.isNestedScrollAccepted(var6)) {
               var14 = var11;
               var15 = var12;
            } else {
               CoordinatorLayout.Behavior var19 = var16.getBehavior();
               var14 = var11;
               var15 = var12;
               if (var19 != null) {
                  int[] var17 = this.mBehaviorConsumed;
                  var17[0] = 0;
                  var17[1] = 0;
                  var19.onNestedScroll(this, var13, var1, var2, var3, var4, var5, var6, var17);
                  int[] var18 = this.mBehaviorConsumed;
                  if (var4 > 0) {
                     var15 = Math.max(var11, var18[0]);
                  } else {
                     var15 = Math.min(var11, var18[0]);
                  }

                  var14 = var15;
                  if (var5 > 0) {
                     var15 = Math.max(var12, this.mBehaviorConsumed[1]);
                  } else {
                     var15 = Math.min(var12, this.mBehaviorConsumed[1]);
                  }

                  var9 = true;
               }
            }
         }

         ++var10;
         var11 = var14;
      }

      var7[0] += var11;
      var7[1] += var12;
      if (var9) {
         this.onChildViewsChanged(1);
      }

   }

   public void onNestedScrollAccepted(View var1, View var2, int var3) {
      this.onNestedScrollAccepted(var1, var2, var3, 0);
   }

   public void onNestedScrollAccepted(View var1, View var2, int var3, int var4) {
      this.mNestedScrollingParentHelper.onNestedScrollAccepted(var1, var2, var3, var4);
      this.mNestedScrollingTarget = var2;
      int var5 = this.getChildCount();

      for(int var6 = 0; var6 < var5; ++var6) {
         View var7 = this.getChildAt(var6);
         CoordinatorLayout.LayoutParams var8 = (CoordinatorLayout.LayoutParams)var7.getLayoutParams();
         if (var8.isNestedScrollAccepted(var4)) {
            CoordinatorLayout.Behavior var9 = var8.getBehavior();
            if (var9 != null) {
               var9.onNestedScrollAccepted(this, var7, var1, var2, var3, var4);
            }
         }
      }

   }

   protected void onRestoreInstanceState(Parcelable var1) {
      if (!(var1 instanceof CoordinatorLayout.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         CoordinatorLayout.SavedState var8 = (CoordinatorLayout.SavedState)var1;
         super.onRestoreInstanceState(var8.getSuperState());
         SparseArray var9 = var8.behaviorStates;
         int var2 = 0;

         for(int var3 = this.getChildCount(); var2 < var3; ++var2) {
            View var4 = this.getChildAt(var2);
            int var5 = var4.getId();
            CoordinatorLayout.Behavior var6 = this.getResolvedLayoutParams(var4).getBehavior();
            if (var5 != -1 && var6 != null) {
               Parcelable var7 = (Parcelable)var9.get(var5);
               if (var7 != null) {
                  var6.onRestoreInstanceState(this, var4, var7);
               }
            }
         }

      }
   }

   protected Parcelable onSaveInstanceState() {
      CoordinatorLayout.SavedState var1 = new CoordinatorLayout.SavedState(super.onSaveInstanceState());
      SparseArray var2 = new SparseArray();
      int var3 = this.getChildCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         View var5 = this.getChildAt(var4);
         int var6 = var5.getId();
         CoordinatorLayout.Behavior var7 = ((CoordinatorLayout.LayoutParams)var5.getLayoutParams()).getBehavior();
         if (var6 != -1 && var7 != null) {
            Parcelable var8 = var7.onSaveInstanceState(this, var5);
            if (var8 != null) {
               var2.append(var6, var8);
            }
         }
      }

      var1.behaviorStates = var2;
      return var1;
   }

   public boolean onStartNestedScroll(View var1, View var2, int var3) {
      return this.onStartNestedScroll(var1, var2, var3, 0);
   }

   public boolean onStartNestedScroll(View var1, View var2, int var3, int var4) {
      int var5 = this.getChildCount();
      int var6 = 0;

      boolean var7;
      for(var7 = false; var6 < var5; ++var6) {
         View var8 = this.getChildAt(var6);
         if (var8.getVisibility() != 8) {
            CoordinatorLayout.LayoutParams var9 = (CoordinatorLayout.LayoutParams)var8.getLayoutParams();
            CoordinatorLayout.Behavior var10 = var9.getBehavior();
            if (var10 != null) {
               boolean var11 = var10.onStartNestedScroll(this, var8, var1, var2, var3, var4);
               var7 |= var11;
               var9.setNestedScrollAccepted(var4, var11);
            } else {
               var9.setNestedScrollAccepted(var4, false);
            }
         }
      }

      return var7;
   }

   public void onStopNestedScroll(View var1) {
      this.onStopNestedScroll(var1, 0);
   }

   public void onStopNestedScroll(View var1, int var2) {
      this.mNestedScrollingParentHelper.onStopNestedScroll(var1, var2);
      int var3 = this.getChildCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         View var5 = this.getChildAt(var4);
         CoordinatorLayout.LayoutParams var6 = (CoordinatorLayout.LayoutParams)var5.getLayoutParams();
         if (var6.isNestedScrollAccepted(var2)) {
            CoordinatorLayout.Behavior var7 = var6.getBehavior();
            if (var7 != null) {
               var7.onStopNestedScroll(this, var5, var1, var2);
            }

            var6.resetNestedScroll(var2);
            var6.resetChangedAfterNestedScroll();
         }
      }

      this.mNestedScrollingTarget = null;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      int var2;
      boolean var3;
      boolean var4;
      CoordinatorLayout.Behavior var5;
      boolean var6;
      label37: {
         label36: {
            var2 = var1.getActionMasked();
            if (this.mBehaviorTouchView == null) {
               var3 = this.performIntercept(var1, 1);
               var4 = var3;
               if (!var3) {
                  break label36;
               }
            } else {
               var3 = false;
            }

            var5 = ((CoordinatorLayout.LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
            var4 = var3;
            if (var5 != null) {
               var6 = var5.onTouchEvent(this, this.mBehaviorTouchView, var1);
               var4 = var3;
               var3 = var6;
               break label37;
            }
         }

         var3 = false;
      }

      View var7 = this.mBehaviorTouchView;
      var5 = null;
      if (var7 == null) {
         var6 = var3 | super.onTouchEvent(var1);
         var1 = var5;
      } else {
         var6 = var3;
         var1 = var5;
         if (var4) {
            long var8 = SystemClock.uptimeMillis();
            var1 = MotionEvent.obtain(var8, var8, 3, 0.0F, 0.0F, 0);
            super.onTouchEvent(var1);
            var6 = var3;
         }
      }

      if (var1 != null) {
         var1.recycle();
      }

      if (var2 == 1 || var2 == 3) {
         this.resetTouchBehaviors(false);
      }

      return var6;
   }

   void recordLastChildRect(View var1, Rect var2) {
      ((CoordinatorLayout.LayoutParams)var1.getLayoutParams()).setLastChildRect(var2);
   }

   void removePreDrawListener() {
      if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
         this.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
      }

      this.mNeedsPreDrawListener = false;
   }

   public boolean requestChildRectangleOnScreen(View var1, Rect var2, boolean var3) {
      CoordinatorLayout.Behavior var4 = ((CoordinatorLayout.LayoutParams)var1.getLayoutParams()).getBehavior();
      return var4 != null && var4.onRequestChildRectangleOnScreen(this, var1, var2, var3) ? true : super.requestChildRectangleOnScreen(var1, var2, var3);
   }

   public void requestDisallowInterceptTouchEvent(boolean var1) {
      super.requestDisallowInterceptTouchEvent(var1);
      if (var1 && !this.mDisallowInterceptReset) {
         this.resetTouchBehaviors(false);
         this.mDisallowInterceptReset = true;
      }

   }

   public void setFitsSystemWindows(boolean var1) {
      super.setFitsSystemWindows(var1);
      this.setupForInsets();
   }

   public void setOnHierarchyChangeListener(OnHierarchyChangeListener var1) {
      this.mOnHierarchyChangeListener = var1;
   }

   public void setStatusBarBackground(Drawable var1) {
      Drawable var2 = this.mStatusBarBackground;
      if (var2 != var1) {
         Drawable var3 = null;
         if (var2 != null) {
            var2.setCallback((Callback)null);
         }

         if (var1 != null) {
            var3 = var1.mutate();
         }

         this.mStatusBarBackground = var3;
         if (var3 != null) {
            if (var3.isStateful()) {
               this.mStatusBarBackground.setState(this.getDrawableState());
            }

            DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection(this));
            var1 = this.mStatusBarBackground;
            boolean var4;
            if (this.getVisibility() == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            var1.setVisible(var4, false);
            this.mStatusBarBackground.setCallback(this);
         }

         ViewCompat.postInvalidateOnAnimation(this);
      }

   }

   public void setStatusBarBackgroundColor(int var1) {
      this.setStatusBarBackground(new ColorDrawable(var1));
   }

   public void setStatusBarBackgroundResource(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = ContextCompat.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setStatusBarBackground(var2);
   }

   public void setVisibility(int var1) {
      super.setVisibility(var1);
      boolean var2;
      if (var1 == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Drawable var3 = this.mStatusBarBackground;
      if (var3 != null && var3.isVisible() != var2) {
         this.mStatusBarBackground.setVisible(var2, false);
      }

   }

   final WindowInsetsCompat setWindowInsets(WindowInsetsCompat var1) {
      WindowInsetsCompat var2 = var1;
      if (!ObjectsCompat.equals(this.mLastInsets, var1)) {
         this.mLastInsets = var1;
         boolean var3 = true;
         boolean var4;
         if (var1 != null && var1.getSystemWindowInsetTop() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         this.mDrawStatusBarBackground = var4;
         if (!var4 && this.getBackground() == null) {
            var4 = var3;
         } else {
            var4 = false;
         }

         this.setWillNotDraw(var4);
         var2 = this.dispatchApplyWindowInsetsToBehaviors(var1);
         this.requestLayout();
      }

      return var2;
   }

   protected boolean verifyDrawable(Drawable var1) {
      boolean var2;
      if (!super.verifyDrawable(var1) && var1 != this.mStatusBarBackground) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public interface AttachedBehavior {
      CoordinatorLayout.Behavior getBehavior();
   }

   public abstract static class Behavior<V extends View> {
      public Behavior() {
      }

      public Behavior(Context var1, AttributeSet var2) {
      }

      public static Object getTag(View var0) {
         return ((CoordinatorLayout.LayoutParams)var0.getLayoutParams()).mBehaviorTag;
      }

      public static void setTag(View var0, Object var1) {
         ((CoordinatorLayout.LayoutParams)var0.getLayoutParams()).mBehaviorTag = var1;
      }

      public boolean blocksInteractionBelow(CoordinatorLayout var1, V var2) {
         boolean var3;
         if (this.getScrimOpacity(var1, var2) > 0.0F) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public boolean getInsetDodgeRect(CoordinatorLayout var1, V var2, Rect var3) {
         return false;
      }

      public int getScrimColor(CoordinatorLayout var1, V var2) {
         return -16777216;
      }

      public float getScrimOpacity(CoordinatorLayout var1, V var2) {
         return 0.0F;
      }

      public boolean layoutDependsOn(CoordinatorLayout var1, V var2, View var3) {
         return false;
      }

      public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout var1, V var2, WindowInsetsCompat var3) {
         return var3;
      }

      public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams var1) {
      }

      public boolean onDependentViewChanged(CoordinatorLayout var1, V var2, View var3) {
         return false;
      }

      public void onDependentViewRemoved(CoordinatorLayout var1, V var2, View var3) {
      }

      public void onDetachedFromLayoutParams() {
      }

      public boolean onInterceptTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
         return false;
      }

      public boolean onLayoutChild(CoordinatorLayout var1, V var2, int var3) {
         return false;
      }

      public boolean onMeasureChild(CoordinatorLayout var1, V var2, int var3, int var4, int var5, int var6) {
         return false;
      }

      public boolean onNestedFling(CoordinatorLayout var1, V var2, View var3, float var4, float var5, boolean var6) {
         return false;
      }

      public boolean onNestedPreFling(CoordinatorLayout var1, V var2, View var3, float var4, float var5) {
         return false;
      }

      @Deprecated
      public void onNestedPreScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int[] var6) {
      }

      public void onNestedPreScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int[] var6, int var7) {
         if (var7 == 0) {
            this.onNestedPreScroll(var1, var2, var3, var4, var5, var6);
         }

      }

      @Deprecated
      public void onNestedScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int var6, int var7) {
      }

      @Deprecated
      public void onNestedScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int var6, int var7, int var8) {
         if (var8 == 0) {
            this.onNestedScroll(var1, var2, var3, var4, var5, var6, var7);
         }

      }

      public void onNestedScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int var6, int var7, int var8, int[] var9) {
         var9[0] += var6;
         var9[1] += var7;
         this.onNestedScroll(var1, var2, var3, var4, var5, var6, var7, var8);
      }

      @Deprecated
      public void onNestedScrollAccepted(CoordinatorLayout var1, V var2, View var3, View var4, int var5) {
      }

      public void onNestedScrollAccepted(CoordinatorLayout var1, V var2, View var3, View var4, int var5, int var6) {
         if (var6 == 0) {
            this.onNestedScrollAccepted(var1, var2, var3, var4, var5);
         }

      }

      public boolean onRequestChildRectangleOnScreen(CoordinatorLayout var1, V var2, Rect var3, boolean var4) {
         return false;
      }

      public void onRestoreInstanceState(CoordinatorLayout var1, V var2, Parcelable var3) {
      }

      public Parcelable onSaveInstanceState(CoordinatorLayout var1, V var2) {
         return BaseSavedState.EMPTY_STATE;
      }

      @Deprecated
      public boolean onStartNestedScroll(CoordinatorLayout var1, V var2, View var3, View var4, int var5) {
         return false;
      }

      public boolean onStartNestedScroll(CoordinatorLayout var1, V var2, View var3, View var4, int var5, int var6) {
         return var6 == 0 ? this.onStartNestedScroll(var1, var2, var3, var4, var5) : false;
      }

      @Deprecated
      public void onStopNestedScroll(CoordinatorLayout var1, V var2, View var3) {
      }

      public void onStopNestedScroll(CoordinatorLayout var1, V var2, View var3, int var4) {
         if (var4 == 0) {
            this.onStopNestedScroll(var1, var2, var3);
         }

      }

      public boolean onTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
         return false;
      }
   }

   @Deprecated
   @Retention(RetentionPolicy.RUNTIME)
   public @interface DefaultBehavior {
      Class<? extends CoordinatorLayout.Behavior> value();
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface DispatchChangeEvent {
   }

   private class HierarchyChangeListener implements OnHierarchyChangeListener {
      HierarchyChangeListener() {
      }

      public void onChildViewAdded(View var1, View var2) {
         if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
            CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(var1, var2);
         }

      }

      public void onChildViewRemoved(View var1, View var2) {
         CoordinatorLayout.this.onChildViewsChanged(2);
         if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
            CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(var1, var2);
         }

      }
   }

   public static class LayoutParams extends MarginLayoutParams {
      public int anchorGravity = 0;
      public int dodgeInsetEdges = 0;
      public int gravity = 0;
      public int insetEdge = 0;
      public int keyline = -1;
      View mAnchorDirectChild;
      int mAnchorId = -1;
      View mAnchorView;
      CoordinatorLayout.Behavior mBehavior;
      boolean mBehaviorResolved = false;
      Object mBehaviorTag;
      private boolean mDidAcceptNestedScrollNonTouch;
      private boolean mDidAcceptNestedScrollTouch;
      private boolean mDidBlockInteraction;
      private boolean mDidChangeAfterNestedScroll;
      int mInsetOffsetX;
      int mInsetOffsetY;
      final Rect mLastChildRect = new Rect();

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.CoordinatorLayout_Layout);
         this.gravity = var3.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
         this.mAnchorId = var3.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
         this.anchorGravity = var3.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
         this.keyline = var3.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
         this.insetEdge = var3.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
         this.dodgeInsetEdges = var3.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
         boolean var4 = var3.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
         this.mBehaviorResolved = var4;
         if (var4) {
            this.mBehavior = CoordinatorLayout.parseBehavior(var1, var2, var3.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
         }

         var3.recycle();
         CoordinatorLayout.Behavior var5 = this.mBehavior;
         if (var5 != null) {
            var5.onAttachedToLayoutParams(this);
         }

      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }

      public LayoutParams(CoordinatorLayout.LayoutParams var1) {
         super(var1);
      }

      private void resolveAnchorView(View var1, CoordinatorLayout var2) {
         View var3 = var2.findViewById(this.mAnchorId);
         this.mAnchorView = var3;
         if (var3 == null) {
            if (var2.isInEditMode()) {
               this.mAnchorDirectChild = null;
               this.mAnchorView = null;
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append("Could not find CoordinatorLayout descendant view with id ");
               var5.append(var2.getResources().getResourceName(this.mAnchorId));
               var5.append(" to anchor view ");
               var5.append(var1);
               throw new IllegalStateException(var5.toString());
            }
         } else if (var3 == var2) {
            if (var2.isInEditMode()) {
               this.mAnchorDirectChild = null;
               this.mAnchorView = null;
            } else {
               throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
            }
         } else {
            for(ViewParent var4 = var3.getParent(); var4 != var2 && var4 != null; var4 = var4.getParent()) {
               if (var4 == var1) {
                  if (var2.isInEditMode()) {
                     this.mAnchorDirectChild = null;
                     this.mAnchorView = null;
                     return;
                  }

                  throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
               }

               if (var4 instanceof View) {
                  var3 = (View)var4;
               }
            }

            this.mAnchorDirectChild = var3;
         }
      }

      private boolean shouldDodge(View var1, int var2) {
         int var3 = GravityCompat.getAbsoluteGravity(((CoordinatorLayout.LayoutParams)var1.getLayoutParams()).insetEdge, var2);
         boolean var4;
         if (var3 != 0 && (GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, var2) & var3) == var3) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      private boolean verifyAnchorView(View var1, CoordinatorLayout var2) {
         if (this.mAnchorView.getId() != this.mAnchorId) {
            return false;
         } else {
            View var3 = this.mAnchorView;

            for(ViewParent var4 = var3.getParent(); var4 != var2; var4 = var4.getParent()) {
               if (var4 == null || var4 == var1) {
                  this.mAnchorDirectChild = null;
                  this.mAnchorView = null;
                  return false;
               }

               if (var4 instanceof View) {
                  var3 = (View)var4;
               }
            }

            this.mAnchorDirectChild = var3;
            return true;
         }
      }

      boolean checkAnchorChanged() {
         boolean var1;
         if (this.mAnchorView == null && this.mAnchorId != -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      boolean dependsOn(CoordinatorLayout var1, View var2, View var3) {
         boolean var5;
         if (var3 != this.mAnchorDirectChild && !this.shouldDodge(var3, ViewCompat.getLayoutDirection(var1))) {
            CoordinatorLayout.Behavior var4 = this.mBehavior;
            if (var4 == null || !var4.layoutDependsOn(var1, var2, var3)) {
               var5 = false;
               return var5;
            }
         }

         var5 = true;
         return var5;
      }

      boolean didBlockInteraction() {
         if (this.mBehavior == null) {
            this.mDidBlockInteraction = false;
         }

         return this.mDidBlockInteraction;
      }

      View findAnchorView(CoordinatorLayout var1, View var2) {
         if (this.mAnchorId == -1) {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return null;
         } else {
            if (this.mAnchorView == null || !this.verifyAnchorView(var2, var1)) {
               this.resolveAnchorView(var2, var1);
            }

            return this.mAnchorView;
         }
      }

      public int getAnchorId() {
         return this.mAnchorId;
      }

      public CoordinatorLayout.Behavior getBehavior() {
         return this.mBehavior;
      }

      boolean getChangedAfterNestedScroll() {
         return this.mDidChangeAfterNestedScroll;
      }

      Rect getLastChildRect() {
         return this.mLastChildRect;
      }

      void invalidateAnchor() {
         this.mAnchorDirectChild = null;
         this.mAnchorView = null;
      }

      boolean isBlockingInteractionBelow(CoordinatorLayout var1, View var2) {
         boolean var3 = this.mDidBlockInteraction;
         if (var3) {
            return true;
         } else {
            CoordinatorLayout.Behavior var4 = this.mBehavior;
            boolean var5;
            if (var4 != null) {
               var5 = var4.blocksInteractionBelow(var1, var2);
            } else {
               var5 = false;
            }

            var5 |= var3;
            this.mDidBlockInteraction = var5;
            return var5;
         }
      }

      boolean isNestedScrollAccepted(int var1) {
         if (var1 != 0) {
            return var1 != 1 ? false : this.mDidAcceptNestedScrollNonTouch;
         } else {
            return this.mDidAcceptNestedScrollTouch;
         }
      }

      void resetChangedAfterNestedScroll() {
         this.mDidChangeAfterNestedScroll = false;
      }

      void resetNestedScroll(int var1) {
         this.setNestedScrollAccepted(var1, false);
      }

      void resetTouchBehaviorTracking() {
         this.mDidBlockInteraction = false;
      }

      public void setAnchorId(int var1) {
         this.invalidateAnchor();
         this.mAnchorId = var1;
      }

      public void setBehavior(CoordinatorLayout.Behavior var1) {
         CoordinatorLayout.Behavior var2 = this.mBehavior;
         if (var2 != var1) {
            if (var2 != null) {
               var2.onDetachedFromLayoutParams();
            }

            this.mBehavior = var1;
            this.mBehaviorTag = null;
            this.mBehaviorResolved = true;
            if (var1 != null) {
               var1.onAttachedToLayoutParams(this);
            }
         }

      }

      void setChangedAfterNestedScroll(boolean var1) {
         this.mDidChangeAfterNestedScroll = var1;
      }

      void setLastChildRect(Rect var1) {
         this.mLastChildRect.set(var1);
      }

      void setNestedScrollAccepted(int var1, boolean var2) {
         if (var1 != 0) {
            if (var1 == 1) {
               this.mDidAcceptNestedScrollNonTouch = var2;
            }
         } else {
            this.mDidAcceptNestedScrollTouch = var2;
         }

      }
   }

   class OnPreDrawListener implements android.view.ViewTreeObserver.OnPreDrawListener {
      public boolean onPreDraw() {
         CoordinatorLayout.this.onChildViewsChanged(0);
         return true;
      }
   }

   protected static class SavedState extends AbsSavedState {
      public static final Creator<CoordinatorLayout.SavedState> CREATOR = new ClassLoaderCreator<CoordinatorLayout.SavedState>() {
         public CoordinatorLayout.SavedState createFromParcel(Parcel var1) {
            return new CoordinatorLayout.SavedState(var1, (ClassLoader)null);
         }

         public CoordinatorLayout.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new CoordinatorLayout.SavedState(var1, var2);
         }

         public CoordinatorLayout.SavedState[] newArray(int var1) {
            return new CoordinatorLayout.SavedState[var1];
         }
      };
      SparseArray<Parcelable> behaviorStates;

      public SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         int var3 = var1.readInt();
         int[] var4 = new int[var3];
         var1.readIntArray(var4);
         Parcelable[] var6 = var1.readParcelableArray(var2);
         this.behaviorStates = new SparseArray(var3);

         for(int var5 = 0; var5 < var3; ++var5) {
            this.behaviorStates.append(var4[var5], var6[var5]);
         }

      }

      public SavedState(Parcelable var1) {
         super(var1);
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         SparseArray var3 = this.behaviorStates;
         int var4 = 0;
         int var5;
         if (var3 != null) {
            var5 = var3.size();
         } else {
            var5 = 0;
         }

         var1.writeInt(var5);
         int[] var6 = new int[var5];

         Parcelable[] var7;
         for(var7 = new Parcelable[var5]; var4 < var5; ++var4) {
            var6[var4] = this.behaviorStates.keyAt(var4);
            var7[var4] = (Parcelable)this.behaviorStates.valueAt(var4);
         }

         var1.writeIntArray(var6);
         var1.writeParcelableArray(var7, var2);
      }
   }

   static class ViewElevationComparator implements Comparator<View> {
      public int compare(View var1, View var2) {
         float var3 = ViewCompat.getZ(var1);
         float var4 = ViewCompat.getZ(var2);
         if (var3 > var4) {
            return -1;
         } else {
            return var3 < var4 ? 1 : 0;
         }
      }
   }
}
