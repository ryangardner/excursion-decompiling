package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout extends ViewGroup {
   private static final boolean DEBUG = false;
   private static final boolean DEBUG_DRAW_CONSTRAINTS = false;
   public static final int DESIGN_INFO_ID = 0;
   private static final boolean MEASURE = false;
   private static final String TAG = "ConstraintLayout";
   private static final boolean USE_CONSTRAINTS_HELPER = true;
   public static final String VERSION = "ConstraintLayout-2.0.1";
   SparseArray<View> mChildrenByIds = new SparseArray();
   private ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList(4);
   protected ConstraintLayoutStates mConstraintLayoutSpec = null;
   private ConstraintSet mConstraintSet = null;
   private int mConstraintSetId = -1;
   private ConstraintsChangedListener mConstraintsChangedListener;
   private HashMap<String, Integer> mDesignIds = new HashMap();
   protected boolean mDirtyHierarchy = true;
   private int mLastMeasureHeight = -1;
   int mLastMeasureHeightMode = 0;
   int mLastMeasureHeightSize = -1;
   private int mLastMeasureWidth = -1;
   int mLastMeasureWidthMode = 0;
   int mLastMeasureWidthSize = -1;
   protected ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
   private int mMaxHeight = Integer.MAX_VALUE;
   private int mMaxWidth = Integer.MAX_VALUE;
   ConstraintLayout.Measurer mMeasurer = new ConstraintLayout.Measurer(this);
   private Metrics mMetrics;
   private int mMinHeight = 0;
   private int mMinWidth = 0;
   private int mOnMeasureHeightMeasureSpec = 0;
   private int mOnMeasureWidthMeasureSpec = 0;
   private int mOptimizationLevel = 263;
   private SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray();

   public ConstraintLayout(Context var1) {
      super(var1);
      this.init((AttributeSet)null, 0, 0);
   }

   public ConstraintLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var2, 0, 0);
   }

   public ConstraintLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var2, var3, 0);
   }

   public ConstraintLayout(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.init(var2, var3, var4);
   }

   private int getPaddingWidth() {
      int var1 = this.getPaddingLeft();
      int var2 = 0;
      var1 = Math.max(0, var1) + Math.max(0, this.getPaddingRight());
      if (android.os.Build.VERSION.SDK_INT >= 17) {
         var2 = Math.max(0, this.getPaddingStart());
         var2 += Math.max(0, this.getPaddingEnd());
      }

      if (var2 > 0) {
         var1 = var2;
      }

      return var1;
   }

   private final ConstraintWidget getTargetWidget(int var1) {
      if (var1 == 0) {
         return this.mLayoutWidget;
      } else {
         View var2 = (View)this.mChildrenByIds.get(var1);
         View var3 = var2;
         if (var2 == null) {
            var2 = this.findViewById(var1);
            var3 = var2;
            if (var2 != null) {
               var3 = var2;
               if (var2 != this) {
                  var3 = var2;
                  if (var2.getParent() == this) {
                     this.onViewAdded(var2);
                     var3 = var2;
                  }
               }
            }
         }

         if (var3 == this) {
            return this.mLayoutWidget;
         } else {
            ConstraintWidget var4;
            if (var3 == null) {
               var4 = null;
            } else {
               var4 = ((ConstraintLayout.LayoutParams)var3.getLayoutParams()).widget;
            }

            return var4;
         }
      }
   }

   private void init(AttributeSet var1, int var2, int var3) {
      this.mLayoutWidget.setCompanionWidget(this);
      this.mLayoutWidget.setMeasurer(this.mMeasurer);
      this.mChildrenByIds.put(this.getId(), this);
      this.mConstraintSet = null;
      if (var1 != null) {
         TypedArray var8 = this.getContext().obtainStyledAttributes(var1, R.styleable.ConstraintLayout_Layout, var2, var3);
         var3 = var8.getIndexCount();

         for(var2 = 0; var2 < var3; ++var2) {
            int var4 = var8.getIndex(var2);
            if (var4 == R.styleable.ConstraintLayout_Layout_android_minWidth) {
               this.mMinWidth = var8.getDimensionPixelOffset(var4, this.mMinWidth);
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_minHeight) {
               this.mMinHeight = var8.getDimensionPixelOffset(var4, this.mMinHeight);
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
               this.mMaxWidth = var8.getDimensionPixelOffset(var4, this.mMaxWidth);
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
               this.mMaxHeight = var8.getDimensionPixelOffset(var4, this.mMaxHeight);
            } else if (var4 == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
               this.mOptimizationLevel = var8.getInt(var4, this.mOptimizationLevel);
            } else if (var4 == R.styleable.ConstraintLayout_Layout_layoutDescription) {
               var4 = var8.getResourceId(var4, 0);
               if (var4 != 0) {
                  try {
                     this.parseLayoutDescription(var4);
                  } catch (NotFoundException var7) {
                     this.mConstraintLayoutSpec = null;
                  }
               }
            } else if (var4 == R.styleable.ConstraintLayout_Layout_constraintSet) {
               var4 = var8.getResourceId(var4, 0);

               try {
                  ConstraintSet var5 = new ConstraintSet();
                  this.mConstraintSet = var5;
                  var5.load(this.getContext(), var4);
               } catch (NotFoundException var6) {
                  this.mConstraintSet = null;
               }

               this.mConstraintSetId = var4;
            }
         }

         var8.recycle();
      }

      this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
   }

   private void markHierarchyDirty() {
      this.mDirtyHierarchy = true;
      this.mLastMeasureWidth = -1;
      this.mLastMeasureHeight = -1;
      this.mLastMeasureWidthSize = -1;
      this.mLastMeasureHeightSize = -1;
      this.mLastMeasureWidthMode = 0;
      this.mLastMeasureHeightMode = 0;
   }

   private void setChildrenConstraints() {
      boolean var1 = this.isInEditMode();
      int var2 = this.getChildCount();

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         ConstraintWidget var4 = this.getViewWidget(this.getChildAt(var3));
         if (var4 != null) {
            var4.reset();
         }
      }

      int var7;
      if (var1) {
         for(var3 = 0; var3 < var2; ++var3) {
            View var5 = this.getChildAt(var3);

            String var6;
            boolean var10001;
            try {
               var6 = this.getResources().getResourceName(var5.getId());
               this.setDesignInformation(0, var6, var5.getId());
               var7 = var6.indexOf(47);
            } catch (NotFoundException var10) {
               var10001 = false;
               continue;
            }

            String var11 = var6;
            if (var7 != -1) {
               try {
                  var11 = var6.substring(var7 + 1);
               } catch (NotFoundException var9) {
                  var10001 = false;
                  continue;
               }
            }

            try {
               this.getTargetWidget(var5.getId()).setDebugName(var11);
            } catch (NotFoundException var8) {
               var10001 = false;
            }
         }
      }

      View var12;
      if (this.mConstraintSetId != -1) {
         for(var3 = 0; var3 < var2; ++var3) {
            var12 = this.getChildAt(var3);
            if (var12.getId() == this.mConstraintSetId && var12 instanceof Constraints) {
               this.mConstraintSet = ((Constraints)var12).getConstraintSet();
            }
         }
      }

      ConstraintSet var14 = this.mConstraintSet;
      if (var14 != null) {
         var14.applyToInternal(this, true);
      }

      this.mLayoutWidget.removeAllChildren();
      var7 = this.mConstraintHelpers.size();
      if (var7 > 0) {
         for(var3 = 0; var3 < var7; ++var3) {
            ((ConstraintHelper)this.mConstraintHelpers.get(var3)).updatePreLayout(this);
         }
      }

      for(var3 = 0; var3 < var2; ++var3) {
         var12 = this.getChildAt(var3);
         if (var12 instanceof Placeholder) {
            ((Placeholder)var12).updatePreLayout(this);
         }
      }

      this.mTempMapIdToWidget.clear();
      this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
      this.mTempMapIdToWidget.put(this.getId(), this.mLayoutWidget);

      ConstraintWidget var15;
      for(var3 = 0; var3 < var2; ++var3) {
         var12 = this.getChildAt(var3);
         var15 = this.getViewWidget(var12);
         this.mTempMapIdToWidget.put(var12.getId(), var15);
      }

      for(var3 = 0; var3 < var2; ++var3) {
         var12 = this.getChildAt(var3);
         var15 = this.getViewWidget(var12);
         if (var15 != null) {
            ConstraintLayout.LayoutParams var13 = (ConstraintLayout.LayoutParams)var12.getLayoutParams();
            this.mLayoutWidget.add(var15);
            this.applyConstraintsFromLayoutParams(var1, var12, var15, var13, this.mTempMapIdToWidget);
         }
      }

   }

   private boolean updateHierarchy() {
      int var1 = this.getChildCount();
      boolean var2 = false;
      int var3 = 0;

      boolean var4;
      while(true) {
         var4 = var2;
         if (var3 >= var1) {
            break;
         }

         if (this.getChildAt(var3).isLayoutRequested()) {
            var4 = true;
            break;
         }

         ++var3;
      }

      if (var4) {
         this.setChildrenConstraints();
      }

      return var4;
   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      super.addView(var1, var2, var3);
      if (android.os.Build.VERSION.SDK_INT < 14) {
         this.onViewAdded(var1);
      }

   }

   protected void applyConstraintsFromLayoutParams(boolean var1, View var2, ConstraintWidget var3, ConstraintLayout.LayoutParams var4, SparseArray<ConstraintWidget> var5) {
      var4.validate();
      var4.helped = false;
      var3.setVisibility(var2.getVisibility());
      if (var4.isInPlaceholder) {
         var3.setInPlaceholder(true);
         var3.setVisibility(8);
      }

      var3.setCompanionWidget(var2);
      if (var2 instanceof ConstraintHelper) {
         ((ConstraintHelper)var2).resolveRtl(var3, this.mLayoutWidget.isRtl());
      }

      int var6;
      int var7;
      float var8;
      if (var4.isGuideline) {
         androidx.constraintlayout.solver.widgets.Guideline var16 = (androidx.constraintlayout.solver.widgets.Guideline)var3;
         var6 = var4.resolvedGuideBegin;
         var7 = var4.resolvedGuideEnd;
         var8 = var4.resolvedGuidePercent;
         if (android.os.Build.VERSION.SDK_INT < 17) {
            var6 = var4.guideBegin;
            var7 = var4.guideEnd;
            var8 = var4.guidePercent;
         }

         if (var8 != -1.0F) {
            var16.setGuidePercent(var8);
         } else if (var6 != -1) {
            var16.setGuideBegin(var6);
         } else if (var7 != -1) {
            var16.setGuideEnd(var7);
         }
      } else {
         var6 = var4.resolvedLeftToLeft;
         var7 = var4.resolvedLeftToRight;
         int var9 = var4.resolvedRightToLeft;
         int var10 = var4.resolvedRightToRight;
         int var11 = var4.resolveGoneLeftMargin;
         int var12 = var4.resolveGoneRightMargin;
         var8 = var4.resolvedHorizontalBias;
         if (android.os.Build.VERSION.SDK_INT < 17) {
            var9 = var4.leftToLeft;
            var10 = var4.leftToRight;
            int var13 = var4.rightToLeft;
            int var14 = var4.rightToRight;
            var11 = var4.goneLeftMargin;
            var12 = var4.goneRightMargin;
            var8 = var4.horizontalBias;
            var6 = var9;
            var7 = var10;
            if (var9 == -1) {
               var6 = var9;
               var7 = var10;
               if (var10 == -1) {
                  if (var4.startToStart != -1) {
                     var6 = var4.startToStart;
                     var7 = var10;
                  } else {
                     var6 = var9;
                     var7 = var10;
                     if (var4.startToEnd != -1) {
                        var7 = var4.startToEnd;
                        var6 = var9;
                     }
                  }
               }
            }

            var9 = var13;
            var10 = var14;
            if (var13 == -1) {
               var9 = var13;
               var10 = var14;
               if (var14 == -1) {
                  if (var4.endToStart != -1) {
                     var9 = var4.endToStart;
                     var10 = var14;
                  } else {
                     var9 = var13;
                     var10 = var14;
                     if (var4.endToEnd != -1) {
                        var10 = var4.endToEnd;
                        var9 = var13;
                     }
                  }
               }
            }
         }

         ConstraintWidget var17;
         if (var4.circleConstraint != -1) {
            var17 = (ConstraintWidget)var5.get(var4.circleConstraint);
            if (var17 != null) {
               var3.connectCircularConstraint(var17, var4.circleAngle, var4.circleRadius);
            }
         } else {
            if (var6 != -1) {
               var17 = (ConstraintWidget)var5.get(var6);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.LEFT, var17, ConstraintAnchor.Type.LEFT, var4.leftMargin, var11);
               }
            } else if (var7 != -1) {
               var17 = (ConstraintWidget)var5.get(var7);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.LEFT, var17, ConstraintAnchor.Type.RIGHT, var4.leftMargin, var11);
               }
            }

            if (var9 != -1) {
               var17 = (ConstraintWidget)var5.get(var9);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.RIGHT, var17, ConstraintAnchor.Type.LEFT, var4.rightMargin, var12);
               }
            } else if (var10 != -1) {
               var17 = (ConstraintWidget)var5.get(var10);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.RIGHT, var17, ConstraintAnchor.Type.RIGHT, var4.rightMargin, var12);
               }
            }

            if (var4.topToTop != -1) {
               var17 = (ConstraintWidget)var5.get(var4.topToTop);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.TOP, var17, ConstraintAnchor.Type.TOP, var4.topMargin, var4.goneTopMargin);
               }
            } else if (var4.topToBottom != -1) {
               var17 = (ConstraintWidget)var5.get(var4.topToBottom);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.TOP, var17, ConstraintAnchor.Type.BOTTOM, var4.topMargin, var4.goneTopMargin);
               }
            }

            if (var4.bottomToTop != -1) {
               var17 = (ConstraintWidget)var5.get(var4.bottomToTop);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.BOTTOM, var17, ConstraintAnchor.Type.TOP, var4.bottomMargin, var4.goneBottomMargin);
               }
            } else if (var4.bottomToBottom != -1) {
               var17 = (ConstraintWidget)var5.get(var4.bottomToBottom);
               if (var17 != null) {
                  var3.immediateConnect(ConstraintAnchor.Type.BOTTOM, var17, ConstraintAnchor.Type.BOTTOM, var4.bottomMargin, var4.goneBottomMargin);
               }
            }

            if (var4.baselineToBaseline != -1) {
               View var15 = (View)this.mChildrenByIds.get(var4.baselineToBaseline);
               var17 = (ConstraintWidget)var5.get(var4.baselineToBaseline);
               if (var17 != null && var15 != null && var15.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
                  ConstraintLayout.LayoutParams var18 = (ConstraintLayout.LayoutParams)var15.getLayoutParams();
                  var4.needsBaseline = true;
                  var18.needsBaseline = true;
                  var3.getAnchor(ConstraintAnchor.Type.BASELINE).connect(var17.getAnchor(ConstraintAnchor.Type.BASELINE), 0, -1, true);
                  var3.setHasBaseline(true);
                  var18.widget.setHasBaseline(true);
                  var3.getAnchor(ConstraintAnchor.Type.TOP).reset();
                  var3.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
               }
            }

            if (var8 >= 0.0F) {
               var3.setHorizontalBiasPercent(var8);
            }

            if (var4.verticalBias >= 0.0F) {
               var3.setVerticalBiasPercent(var4.verticalBias);
            }
         }

         if (var1 && (var4.editorAbsoluteX != -1 || var4.editorAbsoluteY != -1)) {
            var3.setOrigin(var4.editorAbsoluteX, var4.editorAbsoluteY);
         }

         if (!var4.horizontalDimensionFixed) {
            if (var4.width == -1) {
               if (var4.constrainedWidth) {
                  var3.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
               } else {
                  var3.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
               }

               var3.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = var4.leftMargin;
               var3.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = var4.rightMargin;
            } else {
               var3.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
               var3.setWidth(0);
            }
         } else {
            var3.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            var3.setWidth(var4.width);
            if (var4.width == -2) {
               var3.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
         }

         if (!var4.verticalDimensionFixed) {
            if (var4.height == -1) {
               if (var4.constrainedHeight) {
                  var3.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
               } else {
                  var3.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
               }

               var3.getAnchor(ConstraintAnchor.Type.TOP).mMargin = var4.topMargin;
               var3.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = var4.bottomMargin;
            } else {
               var3.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
               var3.setHeight(0);
            }
         } else {
            var3.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            var3.setHeight(var4.height);
            if (var4.height == -2) {
               var3.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
         }

         var3.setDimensionRatio(var4.dimensionRatio);
         var3.setHorizontalWeight(var4.horizontalWeight);
         var3.setVerticalWeight(var4.verticalWeight);
         var3.setHorizontalChainStyle(var4.horizontalChainStyle);
         var3.setVerticalChainStyle(var4.verticalChainStyle);
         var3.setHorizontalMatchStyle(var4.matchConstraintDefaultWidth, var4.matchConstraintMinWidth, var4.matchConstraintMaxWidth, var4.matchConstraintPercentWidth);
         var3.setVerticalMatchStyle(var4.matchConstraintDefaultHeight, var4.matchConstraintMinHeight, var4.matchConstraintMaxHeight, var4.matchConstraintPercentHeight);
      }

   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof ConstraintLayout.LayoutParams;
   }

   protected void dispatchDraw(Canvas var1) {
      ArrayList var2 = this.mConstraintHelpers;
      int var3;
      int var4;
      if (var2 != null) {
         var3 = var2.size();
         if (var3 > 0) {
            for(var4 = 0; var4 < var3; ++var4) {
               ((ConstraintHelper)this.mConstraintHelpers.get(var4)).updatePreDraw(this);
            }
         }
      }

      super.dispatchDraw(var1);
      if (this.isInEditMode()) {
         var3 = this.getChildCount();
         float var5 = (float)this.getWidth();
         float var6 = (float)this.getHeight();

         for(var4 = 0; var4 < var3; ++var4) {
            View var15 = this.getChildAt(var4);
            if (var15.getVisibility() != 8) {
               Object var16 = var15.getTag();
               if (var16 != null && var16 instanceof String) {
                  String[] var17 = ((String)var16).split(",");
                  if (var17.length == 4) {
                     int var7 = Integer.parseInt(var17[0]);
                     int var8 = Integer.parseInt(var17[1]);
                     int var9 = Integer.parseInt(var17[2]);
                     int var10 = Integer.parseInt(var17[3]);
                     var7 = (int)((float)var7 / 1080.0F * var5);
                     var8 = (int)((float)var8 / 1920.0F * var6);
                     var9 = (int)((float)var9 / 1080.0F * var5);
                     var10 = (int)((float)var10 / 1920.0F * var6);
                     Paint var18 = new Paint();
                     var18.setColor(-65536);
                     float var11 = (float)var7;
                     float var12 = (float)var8;
                     float var13 = (float)(var7 + var9);
                     var1.drawLine(var11, var12, var13, var12, var18);
                     float var14 = (float)(var8 + var10);
                     var1.drawLine(var13, var12, var13, var14, var18);
                     var1.drawLine(var13, var14, var11, var14, var18);
                     var1.drawLine(var11, var14, var11, var12, var18);
                     var18.setColor(-16711936);
                     var1.drawLine(var11, var12, var13, var14, var18);
                     var1.drawLine(var11, var14, var13, var12, var18);
                  }
               }
            }
         }
      }

   }

   public void fillMetrics(Metrics var1) {
      this.mMetrics = var1;
      this.mLayoutWidget.fillMetrics(var1);
   }

   public void forceLayout() {
      this.markHierarchyDirty();
      super.forceLayout();
   }

   protected ConstraintLayout.LayoutParams generateDefaultLayoutParams() {
      return new ConstraintLayout.LayoutParams(-2, -2);
   }

   protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return new ConstraintLayout.LayoutParams(var1);
   }

   public ConstraintLayout.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new ConstraintLayout.LayoutParams(this.getContext(), var1);
   }

   public Object getDesignInformation(int var1, Object var2) {
      if (var1 == 0 && var2 instanceof String) {
         String var4 = (String)var2;
         HashMap var3 = this.mDesignIds;
         if (var3 != null && var3.containsKey(var4)) {
            return this.mDesignIds.get(var4);
         }
      }

      return null;
   }

   public int getMaxHeight() {
      return this.mMaxHeight;
   }

   public int getMaxWidth() {
      return this.mMaxWidth;
   }

   public int getMinHeight() {
      return this.mMinHeight;
   }

   public int getMinWidth() {
      return this.mMinWidth;
   }

   public int getOptimizationLevel() {
      return this.mLayoutWidget.getOptimizationLevel();
   }

   public View getViewById(int var1) {
      return (View)this.mChildrenByIds.get(var1);
   }

   public final ConstraintWidget getViewWidget(View var1) {
      if (var1 == this) {
         return this.mLayoutWidget;
      } else {
         ConstraintWidget var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = ((ConstraintLayout.LayoutParams)var1.getLayoutParams()).widget;
         }

         return var2;
      }
   }

   protected boolean isRtl() {
      int var1 = android.os.Build.VERSION.SDK_INT;
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 >= 17) {
         boolean var4;
         if ((this.getContext().getApplicationInfo().flags & 4194304) != 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         var3 = var2;
         if (var4) {
            var3 = var2;
            if (1 == this.getLayoutDirection()) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   public void loadLayoutDescription(int var1) {
      if (var1 != 0) {
         try {
            ConstraintLayoutStates var2 = new ConstraintLayoutStates(this.getContext(), this, var1);
            this.mConstraintLayoutSpec = var2;
         } catch (NotFoundException var3) {
            this.mConstraintLayoutSpec = null;
         }
      } else {
         this.mConstraintLayoutSpec = null;
      }

   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      var4 = this.getChildCount();
      var1 = this.isInEditMode();
      byte var12 = 0;

      for(var2 = 0; var2 < var4; ++var2) {
         View var6 = this.getChildAt(var2);
         ConstraintLayout.LayoutParams var7 = (ConstraintLayout.LayoutParams)var6.getLayoutParams();
         ConstraintWidget var8 = var7.widget;
         if ((var6.getVisibility() != 8 || var7.isGuideline || var7.isHelper || var7.isVirtualGroup || var1) && !var7.isInPlaceholder) {
            int var9 = var8.getX();
            int var10 = var8.getY();
            var5 = var8.getWidth() + var9;
            int var11 = var8.getHeight() + var10;
            var6.layout(var9, var10, var5, var11);
            if (var6 instanceof Placeholder) {
               View var13 = ((Placeholder)var6).getContent();
               if (var13 != null) {
                  var13.setVisibility(0);
                  var13.layout(var9, var10, var5, var11);
               }
            }
         }
      }

      var4 = this.mConstraintHelpers.size();
      if (var4 > 0) {
         for(var2 = var12; var2 < var4; ++var2) {
            ((ConstraintHelper)this.mConstraintHelpers.get(var2)).updatePostLayout(this);
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      this.mOnMeasureWidthMeasureSpec = var1;
      this.mOnMeasureHeightMeasureSpec = var2;
      this.mLayoutWidget.setRtl(this.isRtl());
      if (this.mDirtyHierarchy) {
         this.mDirtyHierarchy = false;
         if (this.updateHierarchy()) {
            this.mLayoutWidget.updateHierarchy();
         }
      }

      this.resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, var1, var2);
      this.resolveMeasuredDimension(var1, var2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
   }

   public void onViewAdded(View var1) {
      if (android.os.Build.VERSION.SDK_INT >= 14) {
         super.onViewAdded(var1);
      }

      ConstraintWidget var2 = this.getViewWidget(var1);
      if (var1 instanceof Guideline && !(var2 instanceof androidx.constraintlayout.solver.widgets.Guideline)) {
         ConstraintLayout.LayoutParams var3 = (ConstraintLayout.LayoutParams)var1.getLayoutParams();
         var3.widget = new androidx.constraintlayout.solver.widgets.Guideline();
         var3.isGuideline = true;
         ((androidx.constraintlayout.solver.widgets.Guideline)var3.widget).setOrientation(var3.orientation);
      }

      if (var1 instanceof ConstraintHelper) {
         ConstraintHelper var4 = (ConstraintHelper)var1;
         var4.validateParams();
         ((ConstraintLayout.LayoutParams)var1.getLayoutParams()).isHelper = true;
         if (!this.mConstraintHelpers.contains(var4)) {
            this.mConstraintHelpers.add(var4);
         }
      }

      this.mChildrenByIds.put(var1.getId(), var1);
      this.mDirtyHierarchy = true;
   }

   public void onViewRemoved(View var1) {
      if (android.os.Build.VERSION.SDK_INT >= 14) {
         super.onViewRemoved(var1);
      }

      this.mChildrenByIds.remove(var1.getId());
      ConstraintWidget var2 = this.getViewWidget(var1);
      this.mLayoutWidget.remove(var2);
      this.mConstraintHelpers.remove(var1);
      this.mDirtyHierarchy = true;
   }

   protected void parseLayoutDescription(int var1) {
      this.mConstraintLayoutSpec = new ConstraintLayoutStates(this.getContext(), this, var1);
   }

   public void removeView(View var1) {
      super.removeView(var1);
      if (android.os.Build.VERSION.SDK_INT < 14) {
         this.onViewRemoved(var1);
      }

   }

   public void requestLayout() {
      this.markHierarchyDirty();
      super.requestLayout();
   }

   protected void resolveMeasuredDimension(int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      int var7 = this.mMeasurer.paddingHeight;
      var3 += this.mMeasurer.paddingWidth;
      var4 += var7;
      if (android.os.Build.VERSION.SDK_INT >= 11) {
         var1 = resolveSizeAndState(var3, var1, 0);
         var3 = resolveSizeAndState(var4, var2, 0);
         var2 = Math.min(this.mMaxWidth, var1 & 16777215);
         var3 = Math.min(this.mMaxHeight, var3 & 16777215);
         var1 = var2;
         if (var5) {
            var1 = var2 | 16777216;
         }

         var2 = var3;
         if (var6) {
            var2 = var3 | 16777216;
         }

         this.setMeasuredDimension(var1, var2);
         this.mLastMeasureWidth = var1;
         this.mLastMeasureHeight = var2;
      } else {
         this.setMeasuredDimension(var3, var4);
         this.mLastMeasureWidth = var3;
         this.mLastMeasureHeight = var4;
      }

   }

   protected void resolveSystem(ConstraintWidgetContainer var1, int var2, int var3, int var4) {
      int var5 = MeasureSpec.getMode(var3);
      int var6 = MeasureSpec.getSize(var3);
      int var7 = MeasureSpec.getMode(var4);
      int var8 = MeasureSpec.getSize(var4);
      int var9 = Math.max(0, this.getPaddingTop());
      int var10 = Math.max(0, this.getPaddingBottom());
      int var11 = var9 + var10;
      int var12 = this.getPaddingWidth();
      this.mMeasurer.captureLayoutInfos(var3, var4, var9, var10, var12, var11);
      if (android.os.Build.VERSION.SDK_INT >= 17) {
         var3 = Math.max(0, this.getPaddingStart());
         var4 = Math.max(0, this.getPaddingEnd());
         if (var3 <= 0 && var4 <= 0) {
            var3 = Math.max(0, this.getPaddingLeft());
         } else if (this.isRtl()) {
            var3 = var4;
         }
      } else {
         var3 = Math.max(0, this.getPaddingLeft());
      }

      var4 = var6 - var12;
      var8 -= var11;
      this.setSelfDimensionBehaviour(var1, var5, var4, var7, var8);
      var1.measure(var2, var5, var4, var7, var8, this.mLastMeasureWidth, this.mLastMeasureHeight, var3, var9);
   }

   public void setConstraintSet(ConstraintSet var1) {
      this.mConstraintSet = var1;
   }

   public void setDesignInformation(int var1, Object var2, Object var3) {
      if (var1 == 0 && var2 instanceof String && var3 instanceof Integer) {
         if (this.mDesignIds == null) {
            this.mDesignIds = new HashMap();
         }

         String var4 = (String)var2;
         var1 = var4.indexOf("/");
         String var5 = var4;
         if (var1 != -1) {
            var5 = var4.substring(var1 + 1);
         }

         var1 = (Integer)var3;
         this.mDesignIds.put(var5, var1);
      }

   }

   public void setId(int var1) {
      this.mChildrenByIds.remove(this.getId());
      super.setId(var1);
      this.mChildrenByIds.put(this.getId(), this);
   }

   public void setMaxHeight(int var1) {
      if (var1 != this.mMaxHeight) {
         this.mMaxHeight = var1;
         this.requestLayout();
      }
   }

   public void setMaxWidth(int var1) {
      if (var1 != this.mMaxWidth) {
         this.mMaxWidth = var1;
         this.requestLayout();
      }
   }

   public void setMinHeight(int var1) {
      if (var1 != this.mMinHeight) {
         this.mMinHeight = var1;
         this.requestLayout();
      }
   }

   public void setMinWidth(int var1) {
      if (var1 != this.mMinWidth) {
         this.mMinWidth = var1;
         this.requestLayout();
      }
   }

   public void setOnConstraintsChanged(ConstraintsChangedListener var1) {
      this.mConstraintsChangedListener = var1;
      ConstraintLayoutStates var2 = this.mConstraintLayoutSpec;
      if (var2 != null) {
         var2.setOnConstraintsChanged(var1);
      }

   }

   public void setOptimizationLevel(int var1) {
      this.mOptimizationLevel = var1;
      this.mLayoutWidget.setOptimizationLevel(var1);
   }

   protected void setSelfDimensionBehaviour(ConstraintWidgetContainer var1, int var2, int var3, int var4, int var5) {
      int var6 = this.mMeasurer.paddingHeight;
      int var7 = this.mMeasurer.paddingWidth;
      ConstraintWidget.DimensionBehaviour var8 = ConstraintWidget.DimensionBehaviour.FIXED;
      ConstraintWidget.DimensionBehaviour var9 = ConstraintWidget.DimensionBehaviour.FIXED;
      int var10 = this.getChildCount();
      ConstraintWidget.DimensionBehaviour var11;
      if (var2 != Integer.MIN_VALUE) {
         label44: {
            if (var2 != 0) {
               if (var2 == 1073741824) {
                  var3 = Math.min(this.mMaxWidth - var7, var3);
                  break label44;
               }
            } else {
               var11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
               var8 = var11;
               if (var10 == 0) {
                  var3 = Math.max(0, this.mMinWidth);
                  var8 = var11;
                  break label44;
               }
            }

            var3 = 0;
         }
      } else {
         var11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
         var8 = var11;
         if (var10 == 0) {
            var3 = Math.max(0, this.mMinWidth);
            var8 = var11;
         }
      }

      if (var4 != Integer.MIN_VALUE) {
         label37: {
            if (var4 != 0) {
               if (var4 == 1073741824) {
                  var5 = Math.min(this.mMaxHeight - var6, var5);
                  break label37;
               }
            } else {
               var11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
               var9 = var11;
               if (var10 == 0) {
                  var5 = Math.max(0, this.mMinHeight);
                  var9 = var11;
                  break label37;
               }
            }

            var5 = 0;
         }
      } else {
         var11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
         var9 = var11;
         if (var10 == 0) {
            var5 = Math.max(0, this.mMinHeight);
            var9 = var11;
         }
      }

      if (var3 != var1.getWidth() || var5 != var1.getHeight()) {
         var1.invalidateMeasures();
      }

      var1.setX(0);
      var1.setY(0);
      var1.setMaxWidth(this.mMaxWidth - var7);
      var1.setMaxHeight(this.mMaxHeight - var6);
      var1.setMinWidth(0);
      var1.setMinHeight(0);
      var1.setHorizontalDimensionBehaviour(var8);
      var1.setWidth(var3);
      var1.setVerticalDimensionBehaviour(var9);
      var1.setHeight(var5);
      var1.setMinWidth(this.mMinWidth - var7);
      var1.setMinHeight(this.mMinHeight - var6);
   }

   public void setState(int var1, int var2, int var3) {
      ConstraintLayoutStates var4 = this.mConstraintLayoutSpec;
      if (var4 != null) {
         var4.updateConstraints(var1, (float)var2, (float)var3);
      }

   }

   public boolean shouldDelayChildPressedState() {
      return false;
   }

   public static class LayoutParams extends MarginLayoutParams {
      public static final int BASELINE = 5;
      public static final int BOTTOM = 4;
      public static final int CHAIN_PACKED = 2;
      public static final int CHAIN_SPREAD = 0;
      public static final int CHAIN_SPREAD_INSIDE = 1;
      public static final int END = 7;
      public static final int HORIZONTAL = 0;
      public static final int LEFT = 1;
      public static final int MATCH_CONSTRAINT = 0;
      public static final int MATCH_CONSTRAINT_PERCENT = 2;
      public static final int MATCH_CONSTRAINT_SPREAD = 0;
      public static final int MATCH_CONSTRAINT_WRAP = 1;
      public static final int PARENT_ID = 0;
      public static final int RIGHT = 2;
      public static final int START = 6;
      public static final int TOP = 3;
      public static final int UNSET = -1;
      public static final int VERTICAL = 1;
      public int baselineToBaseline = -1;
      public int bottomToBottom = -1;
      public int bottomToTop = -1;
      public float circleAngle = 0.0F;
      public int circleConstraint = -1;
      public int circleRadius = 0;
      public boolean constrainedHeight = false;
      public boolean constrainedWidth = false;
      public String constraintTag = null;
      public String dimensionRatio = null;
      int dimensionRatioSide = 1;
      float dimensionRatioValue = 0.0F;
      public int editorAbsoluteX = -1;
      public int editorAbsoluteY = -1;
      public int endToEnd = -1;
      public int endToStart = -1;
      public int goneBottomMargin = -1;
      public int goneEndMargin = -1;
      public int goneLeftMargin = -1;
      public int goneRightMargin = -1;
      public int goneStartMargin = -1;
      public int goneTopMargin = -1;
      public int guideBegin = -1;
      public int guideEnd = -1;
      public float guidePercent = -1.0F;
      public boolean helped = false;
      public float horizontalBias = 0.5F;
      public int horizontalChainStyle = 0;
      boolean horizontalDimensionFixed = true;
      public float horizontalWeight = -1.0F;
      boolean isGuideline = false;
      boolean isHelper = false;
      boolean isInPlaceholder = false;
      boolean isVirtualGroup = false;
      public int leftToLeft = -1;
      public int leftToRight = -1;
      public int matchConstraintDefaultHeight = 0;
      public int matchConstraintDefaultWidth = 0;
      public int matchConstraintMaxHeight = 0;
      public int matchConstraintMaxWidth = 0;
      public int matchConstraintMinHeight = 0;
      public int matchConstraintMinWidth = 0;
      public float matchConstraintPercentHeight = 1.0F;
      public float matchConstraintPercentWidth = 1.0F;
      boolean needsBaseline = false;
      public int orientation = -1;
      int resolveGoneLeftMargin = -1;
      int resolveGoneRightMargin = -1;
      int resolvedGuideBegin;
      int resolvedGuideEnd;
      float resolvedGuidePercent;
      float resolvedHorizontalBias = 0.5F;
      int resolvedLeftToLeft = -1;
      int resolvedLeftToRight = -1;
      int resolvedRightToLeft = -1;
      int resolvedRightToRight = -1;
      public int rightToLeft = -1;
      public int rightToRight = -1;
      public int startToEnd = -1;
      public int startToStart = -1;
      public int topToBottom = -1;
      public int topToTop = -1;
      public float verticalBias = 0.5F;
      public int verticalChainStyle = 0;
      boolean verticalDimensionFixed = true;
      public float verticalWeight = -1.0F;
      ConstraintWidget widget = new ConstraintWidget();

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var19 = var1.obtainStyledAttributes(var2, R.styleable.ConstraintLayout_Layout);
         int var3 = var19.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var19.getIndex(var4);
            int var6 = ConstraintLayout.LayoutParams.Table.map.get(var5);
            float var9;
            switch(var6) {
            case 1:
               this.orientation = var19.getInt(var5, this.orientation);
               continue;
            case 2:
               var6 = var19.getResourceId(var5, this.circleConstraint);
               this.circleConstraint = var6;
               if (var6 == -1) {
                  this.circleConstraint = var19.getInt(var5, -1);
               }
               continue;
            case 3:
               this.circleRadius = var19.getDimensionPixelSize(var5, this.circleRadius);
               continue;
            case 4:
               var9 = var19.getFloat(var5, this.circleAngle) % 360.0F;
               this.circleAngle = var9;
               if (var9 < 0.0F) {
                  this.circleAngle = (360.0F - var9) % 360.0F;
               }
               continue;
            case 5:
               this.guideBegin = var19.getDimensionPixelOffset(var5, this.guideBegin);
               continue;
            case 6:
               this.guideEnd = var19.getDimensionPixelOffset(var5, this.guideEnd);
               continue;
            case 7:
               this.guidePercent = var19.getFloat(var5, this.guidePercent);
               continue;
            case 8:
               var6 = var19.getResourceId(var5, this.leftToLeft);
               this.leftToLeft = var6;
               if (var6 == -1) {
                  this.leftToLeft = var19.getInt(var5, -1);
               }
               continue;
            case 9:
               var6 = var19.getResourceId(var5, this.leftToRight);
               this.leftToRight = var6;
               if (var6 == -1) {
                  this.leftToRight = var19.getInt(var5, -1);
               }
               continue;
            case 10:
               var6 = var19.getResourceId(var5, this.rightToLeft);
               this.rightToLeft = var6;
               if (var6 == -1) {
                  this.rightToLeft = var19.getInt(var5, -1);
               }
               continue;
            case 11:
               var6 = var19.getResourceId(var5, this.rightToRight);
               this.rightToRight = var6;
               if (var6 == -1) {
                  this.rightToRight = var19.getInt(var5, -1);
               }
               continue;
            case 12:
               var6 = var19.getResourceId(var5, this.topToTop);
               this.topToTop = var6;
               if (var6 == -1) {
                  this.topToTop = var19.getInt(var5, -1);
               }
               continue;
            case 13:
               var6 = var19.getResourceId(var5, this.topToBottom);
               this.topToBottom = var6;
               if (var6 == -1) {
                  this.topToBottom = var19.getInt(var5, -1);
               }
               continue;
            case 14:
               var6 = var19.getResourceId(var5, this.bottomToTop);
               this.bottomToTop = var6;
               if (var6 == -1) {
                  this.bottomToTop = var19.getInt(var5, -1);
               }
               continue;
            case 15:
               var6 = var19.getResourceId(var5, this.bottomToBottom);
               this.bottomToBottom = var6;
               if (var6 == -1) {
                  this.bottomToBottom = var19.getInt(var5, -1);
               }
               continue;
            case 16:
               var6 = var19.getResourceId(var5, this.baselineToBaseline);
               this.baselineToBaseline = var6;
               if (var6 == -1) {
                  this.baselineToBaseline = var19.getInt(var5, -1);
               }
               continue;
            case 17:
               var6 = var19.getResourceId(var5, this.startToEnd);
               this.startToEnd = var6;
               if (var6 == -1) {
                  this.startToEnd = var19.getInt(var5, -1);
               }
               continue;
            case 18:
               var6 = var19.getResourceId(var5, this.startToStart);
               this.startToStart = var6;
               if (var6 == -1) {
                  this.startToStart = var19.getInt(var5, -1);
               }
               continue;
            case 19:
               var6 = var19.getResourceId(var5, this.endToStart);
               this.endToStart = var6;
               if (var6 == -1) {
                  this.endToStart = var19.getInt(var5, -1);
               }
               continue;
            case 20:
               var6 = var19.getResourceId(var5, this.endToEnd);
               this.endToEnd = var6;
               if (var6 == -1) {
                  this.endToEnd = var19.getInt(var5, -1);
               }
               continue;
            case 21:
               this.goneLeftMargin = var19.getDimensionPixelSize(var5, this.goneLeftMargin);
               continue;
            case 22:
               this.goneTopMargin = var19.getDimensionPixelSize(var5, this.goneTopMargin);
               continue;
            case 23:
               this.goneRightMargin = var19.getDimensionPixelSize(var5, this.goneRightMargin);
               continue;
            case 24:
               this.goneBottomMargin = var19.getDimensionPixelSize(var5, this.goneBottomMargin);
               continue;
            case 25:
               this.goneStartMargin = var19.getDimensionPixelSize(var5, this.goneStartMargin);
               continue;
            case 26:
               this.goneEndMargin = var19.getDimensionPixelSize(var5, this.goneEndMargin);
               continue;
            case 27:
               this.constrainedWidth = var19.getBoolean(var5, this.constrainedWidth);
               continue;
            case 28:
               this.constrainedHeight = var19.getBoolean(var5, this.constrainedHeight);
               continue;
            case 29:
               this.horizontalBias = var19.getFloat(var5, this.horizontalBias);
               continue;
            case 30:
               this.verticalBias = var19.getFloat(var5, this.verticalBias);
               continue;
            case 31:
               var5 = var19.getInt(var5, 0);
               this.matchConstraintDefaultWidth = var5;
               if (var5 == 1) {
                  Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
               }
               continue;
            case 32:
               var5 = var19.getInt(var5, 0);
               this.matchConstraintDefaultHeight = var5;
               if (var5 == 1) {
                  Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
               }
               continue;
            case 33:
               try {
                  this.matchConstraintMinWidth = var19.getDimensionPixelSize(var5, this.matchConstraintMinWidth);
               } catch (Exception var14) {
                  if (var19.getInt(var5, this.matchConstraintMinWidth) == -2) {
                     this.matchConstraintMinWidth = -2;
                  }
               }
               continue;
            case 34:
               try {
                  this.matchConstraintMaxWidth = var19.getDimensionPixelSize(var5, this.matchConstraintMaxWidth);
               } catch (Exception var13) {
                  if (var19.getInt(var5, this.matchConstraintMaxWidth) == -2) {
                     this.matchConstraintMaxWidth = -2;
                  }
               }
               continue;
            case 35:
               this.matchConstraintPercentWidth = Math.max(0.0F, var19.getFloat(var5, this.matchConstraintPercentWidth));
               this.matchConstraintDefaultWidth = 2;
               continue;
            case 36:
               try {
                  this.matchConstraintMinHeight = var19.getDimensionPixelSize(var5, this.matchConstraintMinHeight);
               } catch (Exception var12) {
                  if (var19.getInt(var5, this.matchConstraintMinHeight) == -2) {
                     this.matchConstraintMinHeight = -2;
                  }
               }
               continue;
            case 37:
               try {
                  this.matchConstraintMaxHeight = var19.getDimensionPixelSize(var5, this.matchConstraintMaxHeight);
               } catch (Exception var11) {
                  if (var19.getInt(var5, this.matchConstraintMaxHeight) == -2) {
                     this.matchConstraintMaxHeight = -2;
                  }
               }
               continue;
            case 38:
               this.matchConstraintPercentHeight = Math.max(0.0F, var19.getFloat(var5, this.matchConstraintPercentHeight));
               this.matchConstraintDefaultHeight = 2;
               continue;
            }

            switch(var6) {
            case 44:
               String var20 = var19.getString(var5);
               this.dimensionRatio = var20;
               this.dimensionRatioValue = Float.NaN;
               this.dimensionRatioSide = -1;
               if (var20 == null) {
                  break;
               }

               var6 = var20.length();
               var5 = this.dimensionRatio.indexOf(44);
               if (var5 > 0 && var5 < var6 - 1) {
                  var20 = this.dimensionRatio.substring(0, var5);
                  if (var20.equalsIgnoreCase("W")) {
                     this.dimensionRatioSide = 0;
                  } else if (var20.equalsIgnoreCase("H")) {
                     this.dimensionRatioSide = 1;
                  }

                  ++var5;
               } else {
                  var5 = 0;
               }

               int var7 = this.dimensionRatio.indexOf(58);
               boolean var10001;
               if (var7 >= 0 && var7 < var6 - 1) {
                  var20 = this.dimensionRatio.substring(var5, var7);
                  String var8 = this.dimensionRatio.substring(var7 + 1);
                  if (var20.length() > 0 && var8.length() > 0) {
                     float var10;
                     try {
                        var9 = Float.parseFloat(var20);
                        var10 = Float.parseFloat(var8);
                     } catch (NumberFormatException var17) {
                        var10001 = false;
                        break;
                     }

                     if (var9 > 0.0F && var10 > 0.0F) {
                        try {
                           if (this.dimensionRatioSide == 1) {
                              this.dimensionRatioValue = Math.abs(var10 / var9);
                              break;
                           }
                        } catch (NumberFormatException var18) {
                           var10001 = false;
                           break;
                        }

                        try {
                           this.dimensionRatioValue = Math.abs(var9 / var10);
                        } catch (NumberFormatException var15) {
                           var10001 = false;
                        }
                     }
                  }
               } else {
                  var20 = this.dimensionRatio.substring(var5);
                  if (var20.length() > 0) {
                     try {
                        this.dimensionRatioValue = Float.parseFloat(var20);
                     } catch (NumberFormatException var16) {
                        var10001 = false;
                     }
                  }
               }
               break;
            case 45:
               this.horizontalWeight = var19.getFloat(var5, this.horizontalWeight);
               break;
            case 46:
               this.verticalWeight = var19.getFloat(var5, this.verticalWeight);
               break;
            case 47:
               this.horizontalChainStyle = var19.getInt(var5, 0);
               break;
            case 48:
               this.verticalChainStyle = var19.getInt(var5, 0);
               break;
            case 49:
               this.editorAbsoluteX = var19.getDimensionPixelOffset(var5, this.editorAbsoluteX);
               break;
            case 50:
               this.editorAbsoluteY = var19.getDimensionPixelOffset(var5, this.editorAbsoluteY);
               break;
            case 51:
               this.constraintTag = var19.getString(var5);
            }
         }

         var19.recycle();
         this.validate();
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(ConstraintLayout.LayoutParams var1) {
         super(var1);
         this.guideBegin = var1.guideBegin;
         this.guideEnd = var1.guideEnd;
         this.guidePercent = var1.guidePercent;
         this.leftToLeft = var1.leftToLeft;
         this.leftToRight = var1.leftToRight;
         this.rightToLeft = var1.rightToLeft;
         this.rightToRight = var1.rightToRight;
         this.topToTop = var1.topToTop;
         this.topToBottom = var1.topToBottom;
         this.bottomToTop = var1.bottomToTop;
         this.bottomToBottom = var1.bottomToBottom;
         this.baselineToBaseline = var1.baselineToBaseline;
         this.circleConstraint = var1.circleConstraint;
         this.circleRadius = var1.circleRadius;
         this.circleAngle = var1.circleAngle;
         this.startToEnd = var1.startToEnd;
         this.startToStart = var1.startToStart;
         this.endToStart = var1.endToStart;
         this.endToEnd = var1.endToEnd;
         this.goneLeftMargin = var1.goneLeftMargin;
         this.goneTopMargin = var1.goneTopMargin;
         this.goneRightMargin = var1.goneRightMargin;
         this.goneBottomMargin = var1.goneBottomMargin;
         this.goneStartMargin = var1.goneStartMargin;
         this.goneEndMargin = var1.goneEndMargin;
         this.horizontalBias = var1.horizontalBias;
         this.verticalBias = var1.verticalBias;
         this.dimensionRatio = var1.dimensionRatio;
         this.dimensionRatioValue = var1.dimensionRatioValue;
         this.dimensionRatioSide = var1.dimensionRatioSide;
         this.horizontalWeight = var1.horizontalWeight;
         this.verticalWeight = var1.verticalWeight;
         this.horizontalChainStyle = var1.horizontalChainStyle;
         this.verticalChainStyle = var1.verticalChainStyle;
         this.constrainedWidth = var1.constrainedWidth;
         this.constrainedHeight = var1.constrainedHeight;
         this.matchConstraintDefaultWidth = var1.matchConstraintDefaultWidth;
         this.matchConstraintDefaultHeight = var1.matchConstraintDefaultHeight;
         this.matchConstraintMinWidth = var1.matchConstraintMinWidth;
         this.matchConstraintMaxWidth = var1.matchConstraintMaxWidth;
         this.matchConstraintMinHeight = var1.matchConstraintMinHeight;
         this.matchConstraintMaxHeight = var1.matchConstraintMaxHeight;
         this.matchConstraintPercentWidth = var1.matchConstraintPercentWidth;
         this.matchConstraintPercentHeight = var1.matchConstraintPercentHeight;
         this.editorAbsoluteX = var1.editorAbsoluteX;
         this.editorAbsoluteY = var1.editorAbsoluteY;
         this.orientation = var1.orientation;
         this.horizontalDimensionFixed = var1.horizontalDimensionFixed;
         this.verticalDimensionFixed = var1.verticalDimensionFixed;
         this.needsBaseline = var1.needsBaseline;
         this.isGuideline = var1.isGuideline;
         this.resolvedLeftToLeft = var1.resolvedLeftToLeft;
         this.resolvedLeftToRight = var1.resolvedLeftToRight;
         this.resolvedRightToLeft = var1.resolvedRightToLeft;
         this.resolvedRightToRight = var1.resolvedRightToRight;
         this.resolveGoneLeftMargin = var1.resolveGoneLeftMargin;
         this.resolveGoneRightMargin = var1.resolveGoneRightMargin;
         this.resolvedHorizontalBias = var1.resolvedHorizontalBias;
         this.constraintTag = var1.constraintTag;
         this.widget = var1.widget;
      }

      public String getConstraintTag() {
         return this.constraintTag;
      }

      public ConstraintWidget getConstraintWidget() {
         return this.widget;
      }

      public void reset() {
         ConstraintWidget var1 = this.widget;
         if (var1 != null) {
            var1.reset();
         }

      }

      public void resolveLayoutDirection(int var1) {
         int var2;
         int var3;
         int var4;
         boolean var5;
         boolean var7;
         label120: {
            var2 = this.leftMargin;
            var3 = this.rightMargin;
            var4 = android.os.Build.VERSION.SDK_INT;
            var5 = false;
            if (var4 >= 17) {
               super.resolveLayoutDirection(var1);
               if (1 == this.getLayoutDirection()) {
                  var7 = true;
                  break label120;
               }
            }

            var7 = false;
         }

         this.resolvedRightToLeft = -1;
         this.resolvedRightToRight = -1;
         this.resolvedLeftToLeft = -1;
         this.resolvedLeftToRight = -1;
         this.resolveGoneLeftMargin = -1;
         this.resolveGoneRightMargin = -1;
         this.resolveGoneLeftMargin = this.goneLeftMargin;
         this.resolveGoneRightMargin = this.goneRightMargin;
         this.resolvedHorizontalBias = this.horizontalBias;
         this.resolvedGuideBegin = this.guideBegin;
         this.resolvedGuideEnd = this.guideEnd;
         this.resolvedGuidePercent = this.guidePercent;
         if (var7) {
            label113: {
               var1 = this.startToEnd;
               if (var1 != -1) {
                  this.resolvedRightToLeft = var1;
               } else {
                  var4 = this.startToStart;
                  var7 = var5;
                  if (var4 == -1) {
                     break label113;
                  }

                  this.resolvedRightToRight = var4;
               }

               var7 = true;
            }

            int var8 = this.endToStart;
            if (var8 != -1) {
               this.resolvedLeftToRight = var8;
               var7 = true;
            }

            var8 = this.endToEnd;
            if (var8 != -1) {
               this.resolvedLeftToLeft = var8;
               var7 = true;
            }

            var8 = this.goneStartMargin;
            if (var8 != -1) {
               this.resolveGoneRightMargin = var8;
            }

            var8 = this.goneEndMargin;
            if (var8 != -1) {
               this.resolveGoneLeftMargin = var8;
            }

            if (var7) {
               this.resolvedHorizontalBias = 1.0F - this.horizontalBias;
            }

            if (this.isGuideline && this.orientation == 1) {
               float var6 = this.guidePercent;
               if (var6 != -1.0F) {
                  this.resolvedGuidePercent = 1.0F - var6;
                  this.resolvedGuideBegin = -1;
                  this.resolvedGuideEnd = -1;
               } else {
                  var1 = this.guideBegin;
                  if (var1 != -1) {
                     this.resolvedGuideEnd = var1;
                     this.resolvedGuideBegin = -1;
                     this.resolvedGuidePercent = -1.0F;
                  } else {
                     var1 = this.guideEnd;
                     if (var1 != -1) {
                        this.resolvedGuideBegin = var1;
                        this.resolvedGuideEnd = -1;
                        this.resolvedGuidePercent = -1.0F;
                     }
                  }
               }
            }
         } else {
            var1 = this.startToEnd;
            if (var1 != -1) {
               this.resolvedLeftToRight = var1;
            }

            var1 = this.startToStart;
            if (var1 != -1) {
               this.resolvedLeftToLeft = var1;
            }

            var1 = this.endToStart;
            if (var1 != -1) {
               this.resolvedRightToLeft = var1;
            }

            var1 = this.endToEnd;
            if (var1 != -1) {
               this.resolvedRightToRight = var1;
            }

            var1 = this.goneStartMargin;
            if (var1 != -1) {
               this.resolveGoneLeftMargin = var1;
            }

            var1 = this.goneEndMargin;
            if (var1 != -1) {
               this.resolveGoneRightMargin = var1;
            }
         }

         if (this.endToStart == -1 && this.endToEnd == -1 && this.startToStart == -1 && this.startToEnd == -1) {
            var1 = this.rightToLeft;
            if (var1 != -1) {
               this.resolvedRightToLeft = var1;
               if (this.rightMargin <= 0 && var3 > 0) {
                  this.rightMargin = var3;
               }
            } else {
               var1 = this.rightToRight;
               if (var1 != -1) {
                  this.resolvedRightToRight = var1;
                  if (this.rightMargin <= 0 && var3 > 0) {
                     this.rightMargin = var3;
                  }
               }
            }

            var1 = this.leftToLeft;
            if (var1 != -1) {
               this.resolvedLeftToLeft = var1;
               if (this.leftMargin <= 0 && var2 > 0) {
                  this.leftMargin = var2;
               }
            } else {
               var1 = this.leftToRight;
               if (var1 != -1) {
                  this.resolvedLeftToRight = var1;
                  if (this.leftMargin <= 0 && var2 > 0) {
                     this.leftMargin = var2;
                  }
               }
            }
         }

      }

      public void setWidgetDebugName(String var1) {
         this.widget.setDebugName(var1);
      }

      public void validate() {
         this.isGuideline = false;
         this.horizontalDimensionFixed = true;
         this.verticalDimensionFixed = true;
         if (this.width == -2 && this.constrainedWidth) {
            this.horizontalDimensionFixed = false;
            if (this.matchConstraintDefaultWidth == 0) {
               this.matchConstraintDefaultWidth = 1;
            }
         }

         if (this.height == -2 && this.constrainedHeight) {
            this.verticalDimensionFixed = false;
            if (this.matchConstraintDefaultHeight == 0) {
               this.matchConstraintDefaultHeight = 1;
            }
         }

         if (this.width == 0 || this.width == -1) {
            this.horizontalDimensionFixed = false;
            if (this.width == 0 && this.matchConstraintDefaultWidth == 1) {
               this.width = -2;
               this.constrainedWidth = true;
            }
         }

         if (this.height == 0 || this.height == -1) {
            this.verticalDimensionFixed = false;
            if (this.height == 0 && this.matchConstraintDefaultHeight == 1) {
               this.height = -2;
               this.constrainedHeight = true;
            }
         }

         if (this.guidePercent != -1.0F || this.guideBegin != -1 || this.guideEnd != -1) {
            this.isGuideline = true;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            if (!(this.widget instanceof androidx.constraintlayout.solver.widgets.Guideline)) {
               this.widget = new androidx.constraintlayout.solver.widgets.Guideline();
            }

            ((androidx.constraintlayout.solver.widgets.Guideline)this.widget).setOrientation(this.orientation);
         }

      }

      private static class Table {
         public static final int ANDROID_ORIENTATION = 1;
         public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
         public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
         public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
         public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
         public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
         public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
         public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
         public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
         public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
         public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
         public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
         public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
         public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
         public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
         public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
         public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
         public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
         public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
         public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
         public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
         public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
         public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
         public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
         public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
         public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
         public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
         public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
         public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
         public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
         public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
         public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
         public static final int LAYOUT_CONSTRAINT_TAG = 51;
         public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
         public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
         public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
         public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
         public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
         public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
         public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
         public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
         public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
         public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
         public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
         public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
         public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
         public static final int LAYOUT_GONE_MARGIN_END = 26;
         public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
         public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
         public static final int LAYOUT_GONE_MARGIN_START = 25;
         public static final int LAYOUT_GONE_MARGIN_TOP = 22;
         public static final int UNUSED = 0;
         public static final SparseIntArray map;

         static {
            SparseIntArray var0 = new SparseIntArray();
            map = var0;
            var0.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
            map.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
            map.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
            map.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
            map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
            map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
            map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
            map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
            map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
            map.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
            map.append(R.styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
         }
      }
   }

   class Measurer implements BasicMeasure.Measurer {
      ConstraintLayout layout;
      int layoutHeightSpec;
      int layoutWidthSpec;
      int paddingBottom;
      int paddingHeight;
      int paddingTop;
      int paddingWidth;

      public Measurer(ConstraintLayout var2) {
         this.layout = var2;
      }

      public void captureLayoutInfos(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.paddingTop = var3;
         this.paddingBottom = var4;
         this.paddingWidth = var5;
         this.paddingHeight = var6;
         this.layoutWidthSpec = var1;
         this.layoutHeightSpec = var2;
      }

      public final void didMeasures() {
         int var1 = this.layout.getChildCount();
         byte var2 = 0;

         int var3;
         for(var3 = 0; var3 < var1; ++var3) {
            View var4 = this.layout.getChildAt(var3);
            if (var4 instanceof Placeholder) {
               ((Placeholder)var4).updatePostMeasure(this.layout);
            }
         }

         var1 = this.layout.mConstraintHelpers.size();
         if (var1 > 0) {
            for(var3 = var2; var3 < var1; ++var3) {
               ((ConstraintHelper)this.layout.mConstraintHelpers.get(var3)).updatePostMeasure(this.layout);
            }
         }

      }

      public final void measure(ConstraintWidget var1, BasicMeasure.Measure var2) {
         if (var1 != null) {
            if (var1.getVisibility() == 8 && !var1.isInPlaceholder()) {
               var2.measuredWidth = 0;
               var2.measuredHeight = 0;
               var2.measuredBaseline = 0;
            } else {
               ConstraintWidget.DimensionBehaviour var3;
               ConstraintWidget.DimensionBehaviour var4;
               int var5;
               int var6;
               int var7;
               int var8;
               View var9;
               int var10;
               int var11;
               boolean var26;
               boolean var27;
               label293: {
                  var3 = var2.horizontalBehavior;
                  var4 = var2.verticalBehavior;
                  var5 = var2.horizontalDimension;
                  var6 = var2.verticalDimension;
                  var7 = this.paddingTop + this.paddingBottom;
                  var8 = this.paddingWidth;
                  var9 = (View)var1.getCompanionWidget();
                  var10 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[var3.ordinal()];
                  if (var10 == 1) {
                     var8 = MeasureSpec.makeMeasureSpec(var5, 1073741824);
                     var1.wrapMeasure[2] = var5;
                  } else {
                     label307: {
                        label294: {
                           if (var10 != 2) {
                              if (var10 == 3) {
                                 var8 = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, var8 + var1.getHorizontalMargin(), -1);
                                 var1.wrapMeasure[2] = -1;
                                 break label307;
                              }

                              if (var10 != 4) {
                                 var8 = 0;
                                 break label307;
                              }

                              var11 = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, var8, -2);
                              boolean var25;
                              if (var1.mMatchConstraintDefaultWidth == 1) {
                                 var25 = true;
                              } else {
                                 var25 = false;
                              }

                              var1.wrapMeasure[2] = 0;
                              var5 = var11;
                              if (var2.useCurrentDimensions) {
                                 if ((!var25 || var1.wrapMeasure[3] == 0 || var1.wrapMeasure[0] == var1.getWidth()) && !(var9 instanceof Placeholder)) {
                                    var26 = false;
                                 } else {
                                    var26 = true;
                                 }

                                 if (!var25) {
                                    break label294;
                                 }

                                 var5 = var11;
                                 if (var26) {
                                    break label294;
                                 }
                              }
                           } else {
                              var5 = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, var8, -2);
                              var1.wrapMeasure[2] = -2;
                           }

                           var27 = true;
                           break label293;
                        }

                        var8 = MeasureSpec.makeMeasureSpec(var1.getWidth(), 1073741824);
                     }
                  }

                  var27 = false;
                  var5 = var8;
               }

               boolean var23;
               label244: {
                  label243: {
                     var8 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[var4.ordinal()];
                     if (var8 != 1) {
                        if (var8 == 2) {
                           var8 = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, var7, -2);
                           var1.wrapMeasure[3] = -2;
                           break label243;
                        }

                        if (var8 != 3) {
                           if (var8 != 4) {
                              var26 = false;
                              var8 = 0;
                              break label244;
                           }

                           var7 = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, var7, -2);
                           if (var1.mMatchConstraintDefaultHeight == 1) {
                              var26 = true;
                           } else {
                              var26 = false;
                           }

                           var1.wrapMeasure[3] = 0;
                           var8 = var7;
                           if (!var2.useCurrentDimensions) {
                              break label243;
                           }

                           if ((!var26 || var1.wrapMeasure[2] == 0 || var1.wrapMeasure[1] == var1.getHeight()) && !(var9 instanceof Placeholder)) {
                              var23 = false;
                           } else {
                              var23 = true;
                           }

                           if (var26) {
                              var8 = var7;
                              if (!var23) {
                                 break label243;
                              }
                           }

                           var8 = MeasureSpec.makeMeasureSpec(var1.getHeight(), 1073741824);
                        } else {
                           var8 = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, var7 + var1.getVerticalMargin(), -1);
                           var1.wrapMeasure[3] = -1;
                        }
                     } else {
                        var8 = MeasureSpec.makeMeasureSpec(var6, 1073741824);
                        var1.wrapMeasure[3] = var6;
                     }

                     var26 = false;
                     break label244;
                  }

                  var26 = true;
               }

               if (var3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var23 = true;
               } else {
                  var23 = false;
               }

               boolean var12;
               if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var12 = true;
               } else {
                  var12 = false;
               }

               boolean var24;
               if (var4 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT && var4 != ConstraintWidget.DimensionBehaviour.FIXED) {
                  var24 = false;
               } else {
                  var24 = true;
               }

               boolean var13;
               if (var3 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT && var3 != ConstraintWidget.DimensionBehaviour.FIXED) {
                  var13 = false;
               } else {
                  var13 = true;
               }

               boolean var14;
               if (var23 && var1.mDimensionRatio > 0.0F) {
                  var14 = true;
               } else {
                  var14 = false;
               }

               boolean var15;
               if (var12 && var1.mDimensionRatio > 0.0F) {
                  var15 = true;
               } else {
                  var15 = false;
               }

               ConstraintLayout.LayoutParams var22 = (ConstraintLayout.LayoutParams)var9.getLayoutParams();
               if (!var2.useCurrentDimensions && var23 && var1.mMatchConstraintDefaultWidth == 0 && var12 && var1.mMatchConstraintDefaultHeight == 0) {
                  var10 = 0;
                  var5 = 0;
                  var8 = 0;
               } else {
                  if (var9 instanceof VirtualLayout && var1 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout) {
                     androidx.constraintlayout.solver.widgets.VirtualLayout var21 = (androidx.constraintlayout.solver.widgets.VirtualLayout)var1;
                     ((VirtualLayout)var9).onMeasure(var21, var5, var8);
                  } else {
                     var9.measure(var5, var8);
                  }

                  int var16 = var9.getMeasuredWidth();
                  int var28 = var9.getMeasuredHeight();
                  int var17 = var9.getBaseline();
                  if (var27) {
                     var1.wrapMeasure[0] = var16;
                     var1.wrapMeasure[2] = var28;
                  } else {
                     var1.wrapMeasure[0] = 0;
                     var1.wrapMeasure[2] = 0;
                  }

                  if (var26) {
                     var1.wrapMeasure[1] = var28;
                     var1.wrapMeasure[3] = var16;
                  } else {
                     var1.wrapMeasure[1] = 0;
                     var1.wrapMeasure[3] = 0;
                  }

                  if (var1.mMatchConstraintMinWidth > 0) {
                     var11 = Math.max(var1.mMatchConstraintMinWidth, var16);
                  } else {
                     var11 = var16;
                  }

                  var10 = var11;
                  if (var1.mMatchConstraintMaxWidth > 0) {
                     var10 = Math.min(var1.mMatchConstraintMaxWidth, var11);
                  }

                  if (var1.mMatchConstraintMinHeight > 0) {
                     var11 = Math.max(var1.mMatchConstraintMinHeight, var28);
                  } else {
                     var11 = var28;
                  }

                  var6 = var11;
                  if (var1.mMatchConstraintMaxHeight > 0) {
                     var6 = Math.min(var1.mMatchConstraintMaxHeight, var11);
                  }

                  float var18;
                  if (var14 && var24) {
                     var18 = var1.mDimensionRatio;
                     var7 = (int)((float)var6 * var18 + 0.5F);
                     var11 = var6;
                  } else {
                     var7 = var10;
                     var11 = var6;
                     if (var15) {
                        var7 = var10;
                        var11 = var6;
                        if (var13) {
                           var18 = var1.mDimensionRatio;
                           var11 = (int)((float)var10 / var18 + 0.5F);
                           var7 = var10;
                        }
                     }
                  }

                  if (var16 == var7 && var28 == var11) {
                     var10 = var7;
                     var5 = var11;
                     var8 = var17;
                  } else {
                     if (var16 != var7) {
                        var5 = MeasureSpec.makeMeasureSpec(var7, 1073741824);
                     }

                     if (var28 != var11) {
                        var8 = MeasureSpec.makeMeasureSpec(var11, 1073741824);
                     }

                     var9.measure(var5, var8);
                     var10 = var9.getMeasuredWidth();
                     var5 = var9.getMeasuredHeight();
                     var8 = var9.getBaseline();
                  }
               }

               boolean var19;
               if (var8 != -1) {
                  var19 = true;
               } else {
                  var19 = false;
               }

               boolean var20;
               if (var10 == var2.horizontalDimension && var5 == var2.verticalDimension) {
                  var20 = false;
               } else {
                  var20 = true;
               }

               var2.measuredNeedsSolverPass = var20;
               if (var22.needsBaseline) {
                  var19 = true;
               }

               if (var19 && var8 != -1 && var1.getBaselineDistance() != var8) {
                  var2.measuredNeedsSolverPass = true;
               }

               var2.measuredWidth = var10;
               var2.measuredHeight = var5;
               var2.measuredHasBaseline = var19;
               var2.measuredBaseline = var8;
            }
         }
      }
   }
}
