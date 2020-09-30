package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
   private static final int CORNER_ANIMATION_DURATION = 500;
   private static final int DEF_STYLE_RES;
   private static final float HIDE_FRICTION = 0.1F;
   private static final float HIDE_THRESHOLD = 0.5F;
   public static final int PEEK_HEIGHT_AUTO = -1;
   public static final int SAVE_ALL = -1;
   public static final int SAVE_FIT_TO_CONTENTS = 2;
   public static final int SAVE_HIDEABLE = 4;
   public static final int SAVE_NONE = 0;
   public static final int SAVE_PEEK_HEIGHT = 1;
   public static final int SAVE_SKIP_COLLAPSED = 8;
   private static final int SIGNIFICANT_VEL_THRESHOLD = 500;
   public static final int STATE_COLLAPSED = 4;
   public static final int STATE_DRAGGING = 1;
   public static final int STATE_EXPANDED = 3;
   public static final int STATE_HALF_EXPANDED = 6;
   public static final int STATE_HIDDEN = 5;
   public static final int STATE_SETTLING = 2;
   private static final String TAG = "BottomSheetBehavior";
   int activePointerId;
   private final ArrayList<BottomSheetBehavior.BottomSheetCallback> callbacks = new ArrayList();
   private int childHeight;
   int collapsedOffset;
   private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {
      private boolean releasedLow(View var1) {
         boolean var2;
         if (var1.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int clampViewPositionHorizontal(View var1, int var2, int var3) {
         return var1.getLeft();
      }

      public int clampViewPositionVertical(View var1, int var2, int var3) {
         int var4 = BottomSheetBehavior.this.getExpandedOffset();
         if (BottomSheetBehavior.this.hideable) {
            var3 = BottomSheetBehavior.this.parentHeight;
         } else {
            var3 = BottomSheetBehavior.this.collapsedOffset;
         }

         return MathUtils.clamp(var2, var4, var3);
      }

      public int getViewVerticalDragRange(View var1) {
         return BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset;
      }

      public void onViewDragStateChanged(int var1) {
         if (var1 == 1 && BottomSheetBehavior.this.draggable) {
            BottomSheetBehavior.this.setStateInternal(1);
         }

      }

      public void onViewPositionChanged(View var1, int var2, int var3, int var4, int var5) {
         BottomSheetBehavior.this.dispatchOnSlide(var3);
      }

      public void onViewReleased(View var1, float var2, float var3) {
         byte var4;
         int var5;
         label83: {
            label90: {
               var4 = 4;
               if (var3 < 0.0F) {
                  if (BottomSheetBehavior.this.fitToContents) {
                     var5 = BottomSheetBehavior.this.fitToContentsOffset;
                     break label90;
                  }

                  if (var1.getTop() <= BottomSheetBehavior.this.halfExpandedOffset) {
                     var5 = BottomSheetBehavior.this.expandedOffset;
                     break label90;
                  }

                  var5 = BottomSheetBehavior.this.halfExpandedOffset;
               } else if (BottomSheetBehavior.this.hideable && BottomSheetBehavior.this.shouldHide(var1, var3)) {
                  if (Math.abs(var2) < Math.abs(var3) && var3 > 500.0F || this.releasedLow(var1)) {
                     var5 = BottomSheetBehavior.this.parentHeight;
                     var4 = 5;
                     break label83;
                  }

                  if (BottomSheetBehavior.this.fitToContents) {
                     var5 = BottomSheetBehavior.this.fitToContentsOffset;
                     break label90;
                  }

                  if (Math.abs(var1.getTop() - BottomSheetBehavior.this.expandedOffset) < Math.abs(var1.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                     var5 = BottomSheetBehavior.this.expandedOffset;
                     break label90;
                  }

                  var5 = BottomSheetBehavior.this.halfExpandedOffset;
               } else if (var3 != 0.0F && Math.abs(var2) <= Math.abs(var3)) {
                  if (BottomSheetBehavior.this.fitToContents) {
                     var5 = BottomSheetBehavior.this.collapsedOffset;
                     break label83;
                  }

                  var5 = var1.getTop();
                  if (Math.abs(var5 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(var5 - BottomSheetBehavior.this.collapsedOffset)) {
                     var5 = BottomSheetBehavior.this.collapsedOffset;
                     break label83;
                  }

                  var5 = BottomSheetBehavior.this.halfExpandedOffset;
               } else {
                  var5 = var1.getTop();
                  if (BottomSheetBehavior.this.fitToContents) {
                     if (Math.abs(var5 - BottomSheetBehavior.this.fitToContentsOffset) >= Math.abs(var5 - BottomSheetBehavior.this.collapsedOffset)) {
                        var5 = BottomSheetBehavior.this.collapsedOffset;
                        break label83;
                     }

                     var5 = BottomSheetBehavior.this.fitToContentsOffset;
                     break label90;
                  }

                  if (var5 < BottomSheetBehavior.this.halfExpandedOffset) {
                     if (var5 < Math.abs(var5 - BottomSheetBehavior.this.collapsedOffset)) {
                        var5 = BottomSheetBehavior.this.expandedOffset;
                        break label90;
                     }

                     var5 = BottomSheetBehavior.this.halfExpandedOffset;
                  } else {
                     if (Math.abs(var5 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(var5 - BottomSheetBehavior.this.collapsedOffset)) {
                        var5 = BottomSheetBehavior.this.collapsedOffset;
                        break label83;
                     }

                     var5 = BottomSheetBehavior.this.halfExpandedOffset;
                  }
               }

               var4 = 6;
               break label83;
            }

            var4 = 3;
         }

         BottomSheetBehavior.this.startSettlingAnimation(var1, var4, var5, true);
      }

      public boolean tryCaptureView(View var1, int var2) {
         int var3 = BottomSheetBehavior.this.state;
         boolean var4 = true;
         if (var3 == 1) {
            return false;
         } else if (BottomSheetBehavior.this.touchingScrollingChild) {
            return false;
         } else {
            if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == var2) {
               View var5;
               if (BottomSheetBehavior.this.nestedScrollingChildRef != null) {
                  var5 = (View)BottomSheetBehavior.this.nestedScrollingChildRef.get();
               } else {
                  var5 = null;
               }

               if (var5 != null && var5.canScrollVertically(-1)) {
                  return false;
               }
            }

            if (BottomSheetBehavior.this.viewRef == null || BottomSheetBehavior.this.viewRef.get() != var1) {
               var4 = false;
            }

            return var4;
         }
      }
   };
   private boolean draggable = true;
   float elevation = -1.0F;
   int expandedOffset;
   private boolean fitToContents = true;
   int fitToContentsOffset;
   private int gestureInsetBottom;
   private boolean gestureInsetBottomIgnored;
   int halfExpandedOffset;
   float halfExpandedRatio = 0.5F;
   boolean hideable;
   private boolean ignoreEvents;
   private Map<View, Integer> importantForAccessibilityMap;
   private int initialY;
   private ValueAnimator interpolatorAnimator;
   private boolean isShapeExpanded;
   private int lastNestedScrollDy;
   private MaterialShapeDrawable materialShapeDrawable;
   private float maximumVelocity;
   private boolean nestedScrolled;
   WeakReference<View> nestedScrollingChildRef;
   int parentHeight;
   int parentWidth;
   private int peekHeight;
   private boolean peekHeightAuto;
   private int peekHeightGestureInsetBuffer;
   private int peekHeightMin;
   private int saveFlags = 0;
   private BottomSheetBehavior<V>.SettleRunnable settleRunnable = null;
   private ShapeAppearanceModel shapeAppearanceModelDefault;
   private boolean shapeThemingEnabled;
   private boolean skipCollapsed;
   int state = 4;
   boolean touchingScrollingChild;
   private boolean updateImportantForAccessibilityOnSiblings = false;
   private VelocityTracker velocityTracker;
   ViewDragHelper viewDragHelper;
   WeakReference<V> viewRef;

   static {
      DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
   }

   public BottomSheetBehavior() {
   }

   public BottomSheetBehavior(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.peekHeightGestureInsetBuffer = var1.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
      TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.BottomSheetBehavior_Layout);
      this.shapeThemingEnabled = var3.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance);
      boolean var4 = var3.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint);
      if (var4) {
         this.createMaterialShapeDrawable(var1, var2, var4, MaterialResources.getColorStateList(var1, var3, R.styleable.BottomSheetBehavior_Layout_backgroundTint));
      } else {
         this.createMaterialShapeDrawable(var1, var2, var4);
      }

      this.createShapeValueAnimator();
      if (VERSION.SDK_INT >= 21) {
         this.elevation = var3.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0F);
      }

      TypedValue var5 = var3.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
      if (var5 != null && var5.data == -1) {
         this.setPeekHeight(var5.data);
      } else {
         this.setPeekHeight(var3.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
      }

      this.setHideable(var3.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
      this.setGestureInsetBottomIgnored(var3.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
      this.setFitToContents(var3.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
      this.setSkipCollapsed(var3.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
      this.setDraggable(var3.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
      this.setSaveFlags(var3.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
      this.setHalfExpandedRatio(var3.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5F));
      var5 = var3.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
      if (var5 != null && var5.type == 16) {
         this.setExpandedOffset(var5.data);
      } else {
         this.setExpandedOffset(var3.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
      }

      var3.recycle();
      this.maximumVelocity = (float)ViewConfiguration.get(var1).getScaledMaximumFlingVelocity();
   }

   private void addAccessibilityActionForState(V var1, AccessibilityNodeInfoCompat.AccessibilityActionCompat var2, final int var3) {
      ViewCompat.replaceAccessibilityAction(var1, var2, (CharSequence)null, new AccessibilityViewCommand() {
         public boolean perform(View var1, AccessibilityViewCommand.CommandArguments var2) {
            BottomSheetBehavior.this.setState(var3);
            return true;
         }
      });
   }

   private void calculateCollapsedOffset() {
      int var1 = this.calculatePeekHeight();
      if (this.fitToContents) {
         this.collapsedOffset = Math.max(this.parentHeight - var1, this.fitToContentsOffset);
      } else {
         this.collapsedOffset = this.parentHeight - var1;
      }

   }

   private void calculateHalfExpandedOffset() {
      this.halfExpandedOffset = (int)((float)this.parentHeight * (1.0F - this.halfExpandedRatio));
   }

   private int calculatePeekHeight() {
      if (this.peekHeightAuto) {
         return Math.min(Math.max(this.peekHeightMin, this.parentHeight - this.parentWidth * 9 / 16), this.childHeight);
      } else {
         if (!this.gestureInsetBottomIgnored) {
            int var1 = this.gestureInsetBottom;
            if (var1 > 0) {
               return Math.max(this.peekHeight, var1 + this.peekHeightGestureInsetBuffer);
            }
         }

         return this.peekHeight;
      }
   }

   private void createMaterialShapeDrawable(Context var1, AttributeSet var2, boolean var3) {
      this.createMaterialShapeDrawable(var1, var2, var3, (ColorStateList)null);
   }

   private void createMaterialShapeDrawable(Context var1, AttributeSet var2, boolean var3, ColorStateList var4) {
      if (this.shapeThemingEnabled) {
         this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(var1, var2, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
         MaterialShapeDrawable var5 = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
         this.materialShapeDrawable = var5;
         var5.initializeElevationOverlay(var1);
         if (var3 && var4 != null) {
            this.materialShapeDrawable.setFillColor(var4);
         } else {
            TypedValue var6 = new TypedValue();
            var1.getTheme().resolveAttribute(16842801, var6, true);
            this.materialShapeDrawable.setTint(var6.data);
         }
      }

   }

   private void createShapeValueAnimator() {
      ValueAnimator var1 = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
      this.interpolatorAnimator = var1;
      var1.setDuration(500L);
      this.interpolatorAnimator.addUpdateListener(new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1) {
            float var2 = (Float)var1.getAnimatedValue();
            if (BottomSheetBehavior.this.materialShapeDrawable != null) {
               BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(var2);
            }

         }
      });
   }

   public static <V extends View> BottomSheetBehavior<V> from(V var0) {
      LayoutParams var1 = var0.getLayoutParams();
      if (var1 instanceof CoordinatorLayout.LayoutParams) {
         CoordinatorLayout.Behavior var2 = ((CoordinatorLayout.LayoutParams)var1).getBehavior();
         if (var2 instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior)var2;
         } else {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
         }
      } else {
         throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
      }
   }

   private float getYVelocity() {
      VelocityTracker var1 = this.velocityTracker;
      if (var1 == null) {
         return 0.0F;
      } else {
         var1.computeCurrentVelocity(1000, this.maximumVelocity);
         return this.velocityTracker.getYVelocity(this.activePointerId);
      }
   }

   private void reset() {
      this.activePointerId = -1;
      VelocityTracker var1 = this.velocityTracker;
      if (var1 != null) {
         var1.recycle();
         this.velocityTracker = null;
      }

   }

   private void restoreOptionalState(BottomSheetBehavior.SavedState var1) {
      int var2 = this.saveFlags;
      if (var2 != 0) {
         if (var2 == -1 || (var2 & 1) == 1) {
            this.peekHeight = var1.peekHeight;
         }

         var2 = this.saveFlags;
         if (var2 == -1 || (var2 & 2) == 2) {
            this.fitToContents = var1.fitToContents;
         }

         var2 = this.saveFlags;
         if (var2 == -1 || (var2 & 4) == 4) {
            this.hideable = var1.hideable;
         }

         var2 = this.saveFlags;
         if (var2 == -1 || (var2 & 8) == 8) {
            this.skipCollapsed = var1.skipCollapsed;
         }

      }
   }

   private void setSystemGestureInsets(View var1) {
      if (VERSION.SDK_INT >= 29 && !this.isGestureInsetBottomIgnored() && !this.peekHeightAuto) {
         ViewUtils.doOnApplyWindowInsets(var1, new ViewUtils.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2, ViewUtils.RelativePadding var3) {
               BottomSheetBehavior.this.gestureInsetBottom = var2.getMandatorySystemGestureInsets().bottom;
               BottomSheetBehavior.this.updatePeekHeight(false);
               return var2;
            }
         });
      }

   }

   private void settleToStatePendingLayout(final int var1) {
      final View var2 = (View)this.viewRef.get();
      if (var2 != null) {
         ViewParent var3 = var2.getParent();
         if (var3 != null && var3.isLayoutRequested() && ViewCompat.isAttachedToWindow(var2)) {
            var2.post(new Runnable() {
               public void run() {
                  BottomSheetBehavior.this.settleToState(var2, var1);
               }
            });
         } else {
            this.settleToState(var2, var1);
         }

      }
   }

   private void updateAccessibilityActions() {
      WeakReference var1 = this.viewRef;
      if (var1 != null) {
         View var4 = (View)var1.get();
         if (var4 != null) {
            ViewCompat.removeAccessibilityAction(var4, 524288);
            ViewCompat.removeAccessibilityAction(var4, 262144);
            ViewCompat.removeAccessibilityAction(var4, 1048576);
            if (this.hideable && this.state != 5) {
               this.addAccessibilityActionForState(var4, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }

            int var2 = this.state;
            byte var3 = 6;
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 == 6) {
                     this.addAccessibilityActionForState(var4, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                     this.addAccessibilityActionForState(var4, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                  }
               } else {
                  if (this.fitToContents) {
                     var3 = 3;
                  }

                  this.addAccessibilityActionForState(var4, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, var3);
               }
            } else {
               if (this.fitToContents) {
                  var3 = 4;
               }

               this.addAccessibilityActionForState(var4, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, var3);
            }

         }
      }
   }

   private void updateDrawableForTargetState(int var1) {
      if (var1 != 2) {
         boolean var2;
         if (var1 == 3) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (this.isShapeExpanded != var2) {
            this.isShapeExpanded = var2;
            if (this.materialShapeDrawable != null) {
               ValueAnimator var3 = this.interpolatorAnimator;
               if (var3 != null) {
                  if (var3.isRunning()) {
                     this.interpolatorAnimator.reverse();
                  } else {
                     float var4;
                     if (var2) {
                        var4 = 0.0F;
                     } else {
                        var4 = 1.0F;
                     }

                     this.interpolatorAnimator.setFloatValues(new float[]{1.0F - var4, var4});
                     this.interpolatorAnimator.start();
                  }
               }
            }
         }

      }
   }

   private void updateImportantForAccessibility(boolean var1) {
      WeakReference var2 = this.viewRef;
      if (var2 != null) {
         ViewParent var7 = ((View)var2.get()).getParent();
         if (var7 instanceof CoordinatorLayout) {
            CoordinatorLayout var3 = (CoordinatorLayout)var7;
            int var4 = var3.getChildCount();
            if (VERSION.SDK_INT >= 16 && var1) {
               if (this.importantForAccessibilityMap != null) {
                  return;
               }

               this.importantForAccessibilityMap = new HashMap(var4);
            }

            for(int var5 = 0; var5 < var4; ++var5) {
               View var8 = var3.getChildAt(var5);
               if (var8 != this.viewRef.get()) {
                  if (var1) {
                     if (VERSION.SDK_INT >= 16) {
                        this.importantForAccessibilityMap.put(var8, var8.getImportantForAccessibility());
                     }

                     if (this.updateImportantForAccessibilityOnSiblings) {
                        ViewCompat.setImportantForAccessibility(var8, 4);
                     }
                  } else if (this.updateImportantForAccessibilityOnSiblings) {
                     Map var6 = this.importantForAccessibilityMap;
                     if (var6 != null && var6.containsKey(var8)) {
                        ViewCompat.setImportantForAccessibility(var8, (Integer)this.importantForAccessibilityMap.get(var8));
                     }
                  }
               }
            }

            if (!var1) {
               this.importantForAccessibilityMap = null;
            }

         }
      }
   }

   private void updatePeekHeight(boolean var1) {
      if (this.viewRef != null) {
         this.calculateCollapsedOffset();
         if (this.state == 4) {
            View var2 = (View)this.viewRef.get();
            if (var2 != null) {
               if (var1) {
                  this.settleToStatePendingLayout(this.state);
               } else {
                  var2.requestLayout();
               }
            }
         }
      }

   }

   public void addBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback var1) {
      if (!this.callbacks.contains(var1)) {
         this.callbacks.add(var1);
      }

   }

   public void disableShapeAnimations() {
      this.interpolatorAnimator = null;
   }

   void dispatchOnSlide(int var1) {
      View var2 = (View)this.viewRef.get();
      if (var2 != null && !this.callbacks.isEmpty()) {
         int var3 = this.collapsedOffset;
         float var4;
         float var5;
         if (var1 <= var3 && var3 != this.getExpandedOffset()) {
            var3 = this.collapsedOffset;
            var4 = (float)(var3 - var1);
            var5 = (float)(var3 - this.getExpandedOffset());
         } else {
            var3 = this.collapsedOffset;
            var4 = (float)(var3 - var1);
            var5 = (float)(this.parentHeight - var3);
         }

         var5 = var4 / var5;

         for(var1 = 0; var1 < this.callbacks.size(); ++var1) {
            ((BottomSheetBehavior.BottomSheetCallback)this.callbacks.get(var1)).onSlide(var2, var5);
         }
      }

   }

   View findScrollingChild(View var1) {
      if (ViewCompat.isNestedScrollingEnabled(var1)) {
         return var1;
      } else {
         if (var1 instanceof ViewGroup) {
            ViewGroup var5 = (ViewGroup)var1;
            int var2 = 0;

            for(int var3 = var5.getChildCount(); var2 < var3; ++var2) {
               View var4 = this.findScrollingChild(var5.getChildAt(var2));
               if (var4 != null) {
                  return var4;
               }
            }
         }

         return null;
      }
   }

   public int getExpandedOffset() {
      int var1;
      if (this.fitToContents) {
         var1 = this.fitToContentsOffset;
      } else {
         var1 = this.expandedOffset;
      }

      return var1;
   }

   public float getHalfExpandedRatio() {
      return this.halfExpandedRatio;
   }

   public int getPeekHeight() {
      int var1;
      if (this.peekHeightAuto) {
         var1 = -1;
      } else {
         var1 = this.peekHeight;
      }

      return var1;
   }

   int getPeekHeightMin() {
      return this.peekHeightMin;
   }

   public int getSaveFlags() {
      return this.saveFlags;
   }

   public boolean getSkipCollapsed() {
      return this.skipCollapsed;
   }

   public int getState() {
      return this.state;
   }

   public boolean isDraggable() {
      return this.draggable;
   }

   public boolean isFitToContents() {
      return this.fitToContents;
   }

   public boolean isGestureInsetBottomIgnored() {
      return this.gestureInsetBottomIgnored;
   }

   public boolean isHideable() {
      return this.hideable;
   }

   public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams var1) {
      super.onAttachedToLayoutParams(var1);
      this.viewRef = null;
      this.viewDragHelper = null;
   }

   public void onDetachedFromLayoutParams() {
      super.onDetachedFromLayoutParams();
      this.viewRef = null;
      this.viewDragHelper = null;
   }

   public boolean onInterceptTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
      boolean var4 = var2.isShown();
      boolean var5 = false;
      if (var4 && this.draggable) {
         int var6 = var3.getActionMasked();
         if (var6 == 0) {
            this.reset();
         }

         if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
         }

         this.velocityTracker.addMovement(var3);
         Object var7 = null;
         WeakReference var9;
         if (var6 != 0) {
            if (var6 == 1 || var6 == 3) {
               this.touchingScrollingChild = false;
               this.activePointerId = -1;
               if (this.ignoreEvents) {
                  this.ignoreEvents = false;
                  return false;
               }
            }
         } else {
            int var8 = (int)var3.getX();
            this.initialY = (int)var3.getY();
            if (this.state != 2) {
               var9 = this.nestedScrollingChildRef;
               View var11;
               if (var9 != null) {
                  var11 = (View)var9.get();
               } else {
                  var11 = null;
               }

               if (var11 != null && var1.isPointInChildBounds(var11, var8, this.initialY)) {
                  this.activePointerId = var3.getPointerId(var3.getActionIndex());
                  this.touchingScrollingChild = true;
               }
            }

            if (this.activePointerId == -1 && !var1.isPointInChildBounds(var2, var8, this.initialY)) {
               var4 = true;
            } else {
               var4 = false;
            }

            this.ignoreEvents = var4;
         }

         if (!this.ignoreEvents) {
            ViewDragHelper var10 = this.viewDragHelper;
            if (var10 != null && var10.shouldInterceptTouchEvent(var3)) {
               return true;
            }
         }

         var9 = this.nestedScrollingChildRef;
         var2 = (View)var7;
         if (var9 != null) {
            var2 = (View)var9.get();
         }

         var4 = var5;
         if (var6 == 2) {
            var4 = var5;
            if (var2 != null) {
               var4 = var5;
               if (!this.ignoreEvents) {
                  var4 = var5;
                  if (this.state != 1) {
                     var4 = var5;
                     if (!var1.isPointInChildBounds(var2, (int)var3.getX(), (int)var3.getY())) {
                        var4 = var5;
                        if (this.viewDragHelper != null) {
                           var4 = var5;
                           if (Math.abs((float)this.initialY - var3.getY()) > (float)this.viewDragHelper.getTouchSlop()) {
                              var4 = true;
                           }
                        }
                     }
                  }
               }
            }
         }

         return var4;
      } else {
         this.ignoreEvents = true;
         return false;
      }
   }

   public boolean onLayoutChild(CoordinatorLayout var1, V var2, int var3) {
      if (ViewCompat.getFitsSystemWindows(var1) && !ViewCompat.getFitsSystemWindows(var2)) {
         var2.setFitsSystemWindows(true);
      }

      if (this.viewRef == null) {
         this.peekHeightMin = var1.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
         this.setSystemGestureInsets(var2);
         this.viewRef = new WeakReference(var2);
         MaterialShapeDrawable var4;
         if (this.shapeThemingEnabled) {
            var4 = this.materialShapeDrawable;
            if (var4 != null) {
               ViewCompat.setBackground(var2, var4);
            }
         }

         var4 = this.materialShapeDrawable;
         if (var4 != null) {
            float var5 = this.elevation;
            float var6 = var5;
            if (var5 == -1.0F) {
               var6 = ViewCompat.getElevation(var2);
            }

            var4.setElevation(var6);
            boolean var7;
            if (this.state == 3) {
               var7 = true;
            } else {
               var7 = false;
            }

            this.isShapeExpanded = var7;
            var4 = this.materialShapeDrawable;
            if (var7) {
               var6 = 0.0F;
            } else {
               var6 = 1.0F;
            }

            var4.setInterpolation(var6);
         }

         this.updateAccessibilityActions();
         if (ViewCompat.getImportantForAccessibility(var2) == 0) {
            ViewCompat.setImportantForAccessibility(var2, 1);
         }
      }

      if (this.viewDragHelper == null) {
         this.viewDragHelper = ViewDragHelper.create(var1, this.dragCallback);
      }

      int var8 = var2.getTop();
      var1.onLayoutChild(var2, var3);
      this.parentWidth = var1.getWidth();
      this.parentHeight = var1.getHeight();
      var3 = var2.getHeight();
      this.childHeight = var3;
      this.fitToContentsOffset = Math.max(0, this.parentHeight - var3);
      this.calculateHalfExpandedOffset();
      this.calculateCollapsedOffset();
      var3 = this.state;
      if (var3 == 3) {
         ViewCompat.offsetTopAndBottom(var2, this.getExpandedOffset());
      } else if (var3 == 6) {
         ViewCompat.offsetTopAndBottom(var2, this.halfExpandedOffset);
      } else if (this.hideable && var3 == 5) {
         ViewCompat.offsetTopAndBottom(var2, this.parentHeight);
      } else {
         var3 = this.state;
         if (var3 == 4) {
            ViewCompat.offsetTopAndBottom(var2, this.collapsedOffset);
         } else if (var3 == 1 || var3 == 2) {
            ViewCompat.offsetTopAndBottom(var2, var8 - var2.getTop());
         }
      }

      this.nestedScrollingChildRef = new WeakReference(this.findScrollingChild(var2));
      return true;
   }

   public boolean onNestedPreFling(CoordinatorLayout var1, V var2, View var3, float var4, float var5) {
      WeakReference var6 = this.nestedScrollingChildRef;
      boolean var7 = false;
      boolean var8 = var7;
      if (var6 != null) {
         var8 = var7;
         if (var3 == var6.get()) {
            if (this.state == 3) {
               var8 = var7;
               if (!super.onNestedPreFling(var1, var2, var3, var4, var5)) {
                  return var8;
               }
            }

            var8 = true;
         }
      }

      return var8;
   }

   public void onNestedPreScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int[] var6, int var7) {
      if (var7 != 1) {
         WeakReference var9 = this.nestedScrollingChildRef;
         View var10;
         if (var9 != null) {
            var10 = (View)var9.get();
         } else {
            var10 = null;
         }

         if (var3 == var10) {
            var4 = var2.getTop();
            int var8 = var4 - var5;
            if (var5 > 0) {
               if (var8 < this.getExpandedOffset()) {
                  var6[1] = var4 - this.getExpandedOffset();
                  ViewCompat.offsetTopAndBottom(var2, -var6[1]);
                  this.setStateInternal(3);
               } else {
                  if (!this.draggable) {
                     return;
                  }

                  var6[1] = var5;
                  ViewCompat.offsetTopAndBottom(var2, -var5);
                  this.setStateInternal(1);
               }
            } else if (var5 < 0 && !var3.canScrollVertically(-1)) {
               var7 = this.collapsedOffset;
               if (var8 > var7 && !this.hideable) {
                  var6[1] = var4 - var7;
                  ViewCompat.offsetTopAndBottom(var2, -var6[1]);
                  this.setStateInternal(4);
               } else {
                  if (!this.draggable) {
                     return;
                  }

                  var6[1] = var5;
                  ViewCompat.offsetTopAndBottom(var2, -var5);
                  this.setStateInternal(1);
               }
            }

            this.dispatchOnSlide(var2.getTop());
            this.lastNestedScrollDy = var5;
            this.nestedScrolled = true;
         }
      }
   }

   public void onNestedScroll(CoordinatorLayout var1, V var2, View var3, int var4, int var5, int var6, int var7, int var8, int[] var9) {
   }

   public void onRestoreInstanceState(CoordinatorLayout var1, V var2, Parcelable var3) {
      BottomSheetBehavior.SavedState var4 = (BottomSheetBehavior.SavedState)var3;
      super.onRestoreInstanceState(var1, var2, var4.getSuperState());
      this.restoreOptionalState(var4);
      if (var4.state != 1 && var4.state != 2) {
         this.state = var4.state;
      } else {
         this.state = 4;
      }

   }

   public Parcelable onSaveInstanceState(CoordinatorLayout var1, V var2) {
      return new BottomSheetBehavior.SavedState(super.onSaveInstanceState(var1, var2), this);
   }

   public boolean onStartNestedScroll(CoordinatorLayout var1, V var2, View var3, View var4, int var5, int var6) {
      boolean var7 = false;
      this.lastNestedScrollDy = 0;
      this.nestedScrolled = false;
      if ((var5 & 2) != 0) {
         var7 = true;
      }

      return var7;
   }

   public void onStopNestedScroll(CoordinatorLayout var1, V var2, View var3, int var4) {
      var4 = var2.getTop();
      int var5 = this.getExpandedOffset();
      byte var6 = 3;
      if (var4 == var5) {
         this.setStateInternal(3);
      } else {
         WeakReference var7 = this.nestedScrollingChildRef;
         if (var7 != null && var3 == var7.get() && this.nestedScrolled) {
            label77: {
               if (this.lastNestedScrollDy > 0) {
                  if (this.fitToContents) {
                     var4 = this.fitToContentsOffset;
                     break label77;
                  }

                  var5 = var2.getTop();
                  var4 = this.halfExpandedOffset;
                  if (var5 <= var4) {
                     var4 = this.expandedOffset;
                     break label77;
                  }
               } else {
                  label76: {
                     if (this.hideable && this.shouldHide(var2, this.getYVelocity())) {
                        var4 = this.parentHeight;
                        var6 = 5;
                        break label77;
                     }

                     if (this.lastNestedScrollDy == 0) {
                        var5 = var2.getTop();
                        if (this.fitToContents) {
                           if (Math.abs(var5 - this.fitToContentsOffset) < Math.abs(var5 - this.collapsedOffset)) {
                              var4 = this.fitToContentsOffset;
                              break label77;
                           }

                           var4 = this.collapsedOffset;
                        } else {
                           var4 = this.halfExpandedOffset;
                           if (var5 < var4) {
                              if (var5 < Math.abs(var5 - this.collapsedOffset)) {
                                 var4 = this.expandedOffset;
                                 break label77;
                              }

                              var4 = this.halfExpandedOffset;
                              break label76;
                           }

                           if (Math.abs(var5 - var4) < Math.abs(var5 - this.collapsedOffset)) {
                              var4 = this.halfExpandedOffset;
                              break label76;
                           }

                           var4 = this.collapsedOffset;
                        }
                     } else if (this.fitToContents) {
                        var4 = this.collapsedOffset;
                     } else {
                        var4 = var2.getTop();
                        if (Math.abs(var4 - this.halfExpandedOffset) < Math.abs(var4 - this.collapsedOffset)) {
                           var4 = this.halfExpandedOffset;
                           break label76;
                        }

                        var4 = this.collapsedOffset;
                     }

                     var6 = 4;
                     break label77;
                  }
               }

               var6 = 6;
            }

            this.startSettlingAnimation(var2, var6, var4, false);
            this.nestedScrolled = false;
         }

      }
   }

   public boolean onTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
      if (!var2.isShown()) {
         return false;
      } else {
         int var4 = var3.getActionMasked();
         if (this.state == 1 && var4 == 0) {
            return true;
         } else {
            ViewDragHelper var5 = this.viewDragHelper;
            if (var5 != null) {
               var5.processTouchEvent(var3);
            }

            if (var4 == 0) {
               this.reset();
            }

            if (this.velocityTracker == null) {
               this.velocityTracker = VelocityTracker.obtain();
            }

            this.velocityTracker.addMovement(var3);
            if (this.viewDragHelper != null && var4 == 2 && !this.ignoreEvents && Math.abs((float)this.initialY - var3.getY()) > (float)this.viewDragHelper.getTouchSlop()) {
               this.viewDragHelper.captureChildView(var2, var3.getPointerId(var3.getActionIndex()));
            }

            return this.ignoreEvents ^ true;
         }
      }
   }

   public void removeBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback var1) {
      this.callbacks.remove(var1);
   }

   @Deprecated
   public void setBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback var1) {
      Log.w("BottomSheetBehavior", "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
      this.callbacks.clear();
      if (var1 != null) {
         this.callbacks.add(var1);
      }

   }

   public void setDraggable(boolean var1) {
      this.draggable = var1;
   }

   public void setExpandedOffset(int var1) {
      if (var1 >= 0) {
         this.expandedOffset = var1;
      } else {
         throw new IllegalArgumentException("offset must be greater than or equal to 0");
      }
   }

   public void setFitToContents(boolean var1) {
      if (this.fitToContents != var1) {
         this.fitToContents = var1;
         if (this.viewRef != null) {
            this.calculateCollapsedOffset();
         }

         int var2;
         if (this.fitToContents && this.state == 6) {
            var2 = 3;
         } else {
            var2 = this.state;
         }

         this.setStateInternal(var2);
         this.updateAccessibilityActions();
      }
   }

   public void setGestureInsetBottomIgnored(boolean var1) {
      this.gestureInsetBottomIgnored = var1;
   }

   public void setHalfExpandedRatio(float var1) {
      if (var1 > 0.0F && var1 < 1.0F) {
         this.halfExpandedRatio = var1;
         if (this.viewRef != null) {
            this.calculateHalfExpandedOffset();
         }

      } else {
         throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
      }
   }

   public void setHideable(boolean var1) {
      if (this.hideable != var1) {
         this.hideable = var1;
         if (!var1 && this.state == 5) {
            this.setState(4);
         }

         this.updateAccessibilityActions();
      }

   }

   public void setPeekHeight(int var1) {
      this.setPeekHeight(var1, false);
   }

   public final void setPeekHeight(int var1, boolean var2) {
      boolean var4;
      label30: {
         boolean var3 = true;
         if (var1 == -1) {
            if (!this.peekHeightAuto) {
               this.peekHeightAuto = true;
               var4 = var3;
               break label30;
            }
         } else if (this.peekHeightAuto || this.peekHeight != var1) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, var1);
            var4 = var3;
            break label30;
         }

         var4 = false;
      }

      if (var4) {
         this.updatePeekHeight(var2);
      }

   }

   public void setSaveFlags(int var1) {
      this.saveFlags = var1;
   }

   public void setSkipCollapsed(boolean var1) {
      this.skipCollapsed = var1;
   }

   public void setState(int var1) {
      if (var1 != this.state) {
         if (this.viewRef != null) {
            this.settleToStatePendingLayout(var1);
         } else {
            if (var1 == 4 || var1 == 3 || var1 == 6 || this.hideable && var1 == 5) {
               this.state = var1;
            }

         }
      }
   }

   void setStateInternal(int var1) {
      if (this.state != var1) {
         this.state = var1;
         WeakReference var2 = this.viewRef;
         if (var2 != null) {
            View var4 = (View)var2.get();
            if (var4 != null) {
               int var3 = 0;
               if (var1 == 3) {
                  this.updateImportantForAccessibility(true);
               } else if (var1 == 6 || var1 == 5 || var1 == 4) {
                  this.updateImportantForAccessibility(false);
               }

               this.updateDrawableForTargetState(var1);

               while(var3 < this.callbacks.size()) {
                  ((BottomSheetBehavior.BottomSheetCallback)this.callbacks.get(var3)).onStateChanged(var4, var1);
                  ++var3;
               }

               this.updateAccessibilityActions();
            }
         }
      }
   }

   public void setUpdateImportantForAccessibilityOnSiblings(boolean var1) {
      this.updateImportantForAccessibilityOnSiblings = var1;
   }

   void settleToState(View var1, int var2) {
      int var3;
      if (var2 == 4) {
         var3 = this.collapsedOffset;
      } else if (var2 == 6) {
         label21: {
            int var4 = this.halfExpandedOffset;
            if (this.fitToContents) {
               var3 = this.fitToContentsOffset;
               if (var4 <= var3) {
                  var2 = 3;
                  break label21;
               }
            }

            var3 = var4;
         }
      } else if (var2 == 3) {
         var3 = this.getExpandedOffset();
      } else {
         if (!this.hideable || var2 != 5) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Illegal state argument: ");
            var5.append(var2);
            throw new IllegalArgumentException(var5.toString());
         }

         var3 = this.parentHeight;
      }

      this.startSettlingAnimation(var1, var2, var3, false);
   }

   boolean shouldHide(View var1, float var2) {
      boolean var3 = this.skipCollapsed;
      boolean var4 = true;
      if (var3) {
         return true;
      } else if (var1.getTop() < this.collapsedOffset) {
         return false;
      } else {
         int var5 = this.calculatePeekHeight();
         if (Math.abs((float)var1.getTop() + var2 * 0.1F - (float)this.collapsedOffset) / (float)var5 <= 0.5F) {
            var4 = false;
         }

         return var4;
      }
   }

   void startSettlingAnimation(View var1, int var2, int var3, boolean var4) {
      boolean var6;
      label31: {
         label30: {
            ViewDragHelper var5 = this.viewDragHelper;
            if (var5 != null) {
               if (var4) {
                  if (var5.settleCapturedViewAt(var1.getLeft(), var3)) {
                     break label30;
                  }
               } else if (var5.smoothSlideViewTo(var1, var1.getLeft(), var3)) {
                  break label30;
               }
            }

            var6 = false;
            break label31;
         }

         var6 = true;
      }

      if (var6) {
         this.setStateInternal(2);
         this.updateDrawableForTargetState(var2);
         if (this.settleRunnable == null) {
            this.settleRunnable = new BottomSheetBehavior.SettleRunnable(var1, var2);
         }

         if (!this.settleRunnable.isPosted) {
            this.settleRunnable.targetState = var2;
            ViewCompat.postOnAnimation(var1, this.settleRunnable);
            this.settleRunnable.isPosted = true;
         } else {
            this.settleRunnable.targetState = var2;
         }
      } else {
         this.setStateInternal(var2);
      }

   }

   public abstract static class BottomSheetCallback {
      public abstract void onSlide(View var1, float var2);

      public abstract void onStateChanged(View var1, int var2);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface SaveFlags {
   }

   protected static class SavedState extends AbsSavedState {
      public static final Creator<BottomSheetBehavior.SavedState> CREATOR = new ClassLoaderCreator<BottomSheetBehavior.SavedState>() {
         public BottomSheetBehavior.SavedState createFromParcel(Parcel var1) {
            return new BottomSheetBehavior.SavedState(var1, (ClassLoader)null);
         }

         public BottomSheetBehavior.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new BottomSheetBehavior.SavedState(var1, var2);
         }

         public BottomSheetBehavior.SavedState[] newArray(int var1) {
            return new BottomSheetBehavior.SavedState[var1];
         }
      };
      boolean fitToContents;
      boolean hideable;
      int peekHeight;
      boolean skipCollapsed;
      final int state;

      public SavedState(Parcel var1) {
         this((Parcel)var1, (ClassLoader)null);
      }

      public SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         this.state = var1.readInt();
         this.peekHeight = var1.readInt();
         int var3 = var1.readInt();
         boolean var4 = false;
         boolean var5;
         if (var3 == 1) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.fitToContents = var5;
         if (var1.readInt() == 1) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.hideable = var5;
         var5 = var4;
         if (var1.readInt() == 1) {
            var5 = true;
         }

         this.skipCollapsed = var5;
      }

      @Deprecated
      public SavedState(Parcelable var1, int var2) {
         super(var1);
         this.state = var2;
      }

      public SavedState(Parcelable var1, BottomSheetBehavior<?> var2) {
         super(var1);
         this.state = var2.state;
         this.peekHeight = var2.peekHeight;
         this.fitToContents = var2.fitToContents;
         this.hideable = var2.hideable;
         this.skipCollapsed = var2.skipCollapsed;
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeInt(this.state);
         var1.writeInt(this.peekHeight);
         var1.writeInt(this.fitToContents);
         var1.writeInt(this.hideable);
         var1.writeInt(this.skipCollapsed);
      }
   }

   private class SettleRunnable implements Runnable {
      private boolean isPosted;
      int targetState;
      private final View view;

      SettleRunnable(View var2, int var3) {
         this.view = var2;
         this.targetState = var3;
      }

      public void run() {
         if (BottomSheetBehavior.this.viewDragHelper != null && BottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
            ViewCompat.postOnAnimation(this.view, this);
         } else {
            BottomSheetBehavior.this.setStateInternal(this.targetState);
         }

         this.isPosted = false;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface State {
   }
}
