/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.ConstraintsChangedListener;
import androidx.constraintlayout.widget.Guideline;
import androidx.constraintlayout.widget.Placeholder;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.VirtualLayout;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout
extends ViewGroup {
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
    Measurer mMeasurer = new Measurer(this);
    private Metrics mMetrics;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOnMeasureHeightMeasureSpec = 0;
    private int mOnMeasureWidthMeasureSpec = 0;
    private int mOptimizationLevel = 263;
    private SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray();

    public ConstraintLayout(Context context) {
        super(context);
        this.init(null, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(attributeSet, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(attributeSet, n, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.init(attributeSet, n, n2);
    }

    private int getPaddingWidth() {
        int n = this.getPaddingLeft();
        int n2 = 0;
        n = Math.max(0, n) + Math.max(0, this.getPaddingRight());
        if (Build.VERSION.SDK_INT >= 17) {
            n2 = Math.max(0, this.getPaddingStart());
            n2 = Math.max(0, this.getPaddingEnd()) + n2;
        }
        if (n2 <= 0) return n;
        return n2;
    }

    private final ConstraintWidget getTargetWidget(int n) {
        View view;
        if (n == 0) {
            return this.mLayoutWidget;
        }
        Object object = view = (View)this.mChildrenByIds.get(n);
        if (view == null) {
            object = view = this.findViewById(n);
            if (view != null) {
                object = view;
                if (view != this) {
                    object = view;
                    if (view.getParent() == this) {
                        this.onViewAdded(view);
                        object = view;
                    }
                }
            }
        }
        if (object == this) {
            return this.mLayoutWidget;
        }
        if (object != null) return ((LayoutParams)object.getLayoutParams()).widget;
        return null;
    }

    private void init(AttributeSet attributeSet, int n, int n2) {
        this.mLayoutWidget.setCompanionWidget((Object)this);
        this.mLayoutWidget.setMeasurer(this.mMeasurer);
        this.mChildrenByIds.put(this.getId(), (Object)this);
        this.mConstraintSet = null;
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout, n, n2);
            n2 = attributeSet.getIndexCount();
            for (n = 0; n < n2; ++n) {
                int n3 = attributeSet.getIndex(n);
                if (n3 == R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = attributeSet.getDimensionPixelOffset(n3, this.mMinWidth);
                    continue;
                }
                if (n3 == R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = attributeSet.getDimensionPixelOffset(n3, this.mMinHeight);
                    continue;
                }
                if (n3 == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = attributeSet.getDimensionPixelOffset(n3, this.mMaxWidth);
                    continue;
                }
                if (n3 == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = attributeSet.getDimensionPixelOffset(n3, this.mMaxHeight);
                    continue;
                }
                if (n3 == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = attributeSet.getInt(n3, this.mOptimizationLevel);
                    continue;
                }
                if (n3 == R.styleable.ConstraintLayout_Layout_layoutDescription) {
                    if ((n3 = attributeSet.getResourceId(n3, 0)) == 0) continue;
                    try {
                        this.parseLayoutDescription(n3);
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        this.mConstraintLayoutSpec = null;
                    }
                    continue;
                }
                if (n3 != R.styleable.ConstraintLayout_Layout_constraintSet) continue;
                n3 = attributeSet.getResourceId(n3, 0);
                try {
                    ConstraintSet constraintSet;
                    this.mConstraintSet = constraintSet = new ConstraintSet();
                    constraintSet.load(this.getContext(), n3);
                }
                catch (Resources.NotFoundException notFoundException) {
                    this.mConstraintSet = null;
                }
                this.mConstraintSetId = n3;
            }
            attributeSet.recycle();
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

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void setChildrenConstraints() {
        var1_1 = this.isInEditMode();
        var2_2 = this.getChildCount();
        for (var3_3 = 0; var3_3 < var2_2; ++var3_3) {
            var4_4 = this.getViewWidget(this.getChildAt(var3_3));
            if (var4_4 == null) continue;
            var4_4.reset();
        }
        if (!var1_1) ** GOTO lbl22
        var3_3 = 0;
        block3 : do {
            if (var3_3 >= var2_2) ** GOTO lbl22
            var5_6 = this.getChildAt(var3_3);
            try {
                block15 : {
                    var6_9 = this.getResources().getResourceName(var5_6.getId());
                    this.setDesignInformation(0, var6_9, var5_6.getId());
                    var7_10 = var6_9.indexOf(47);
                    var4_4 = var6_9;
                    if (var7_10 != -1) {
                        var4_4 = var6_9.substring(var7_10 + 1);
                    }
                    this.getTargetWidget(var5_6.getId()).setDebugName((String)var4_4);
                    break block15;
lbl22: // 2 sources:
                    if (this.mConstraintSetId != -1) {
                        for (var3_3 = 0; var3_3 < var2_2; ++var3_3) {
                            var4_4 = this.getChildAt(var3_3);
                            if (var4_4.getId() != this.mConstraintSetId || !(var4_4 instanceof Constraints)) continue;
                            this.mConstraintSet = ((Constraints)var4_4).getConstraintSet();
                        }
                    }
                    if ((var4_4 = this.mConstraintSet) != null) {
                        var4_4.applyToInternal(this, true);
                    }
                    this.mLayoutWidget.removeAllChildren();
                    var7_10 = this.mConstraintHelpers.size();
                    if (var7_10 > 0) {
                        for (var3_3 = 0; var3_3 < var7_10; ++var3_3) {
                            this.mConstraintHelpers.get(var3_3).updatePreLayout(this);
                        }
                    }
                    for (var3_3 = 0; var3_3 < var2_2; ++var3_3) {
                        var4_4 = this.getChildAt(var3_3);
                        if (!(var4_4 instanceof Placeholder)) continue;
                        ((Placeholder)var4_4).updatePreLayout(this);
                    }
                    this.mTempMapIdToWidget.clear();
                    this.mTempMapIdToWidget.put(0, (Object)this.mLayoutWidget);
                    this.mTempMapIdToWidget.put(this.getId(), (Object)this.mLayoutWidget);
                    for (var3_3 = 0; var3_3 < var2_2; ++var3_3) {
                        var4_4 = this.getChildAt(var3_3);
                        var6_9 = this.getViewWidget((View)var4_4);
                        this.mTempMapIdToWidget.put(var4_4.getId(), var6_9);
                    }
                    var3_3 = 0;
                    break;
                }
lbl52: // 2 sources:
                do {
                    ++var3_3;
                    continue block3;
                    break;
                } while (true);
            }
            catch (Resources.NotFoundException var4_5) {
                ** continue;
            }
        } while (true);
        while (var3_3 < var2_2) {
            var4_4 = this.getChildAt(var3_3);
            var6_9 = this.getViewWidget((View)var4_4);
            if (var6_9 != null) {
                var5_8 = (LayoutParams)var4_4.getLayoutParams();
                this.mLayoutWidget.add((ConstraintWidget)var6_9);
                this.applyConstraintsFromLayoutParams(var1_1, (View)var4_4, (ConstraintWidget)var6_9, var5_8, this.mTempMapIdToWidget);
            }
            ++var3_3;
        }
    }

    private boolean updateHierarchy() {
        boolean bl;
        int n = this.getChildCount();
        boolean bl2 = false;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (this.getChildAt(n2).isLayoutRequested()) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        if (!bl) return bl;
        this.setChildrenConstraints();
        return bl;
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n, layoutParams);
        if (Build.VERSION.SDK_INT >= 14) return;
        this.onViewAdded(view);
    }

    protected void applyConstraintsFromLayoutParams(boolean bl, View object, ConstraintWidget constraintWidget, LayoutParams layoutParams, SparseArray<ConstraintWidget> object2) {
        layoutParams.validate();
        layoutParams.helped = false;
        constraintWidget.setVisibility(object.getVisibility());
        if (layoutParams.isInPlaceholder) {
            constraintWidget.setInPlaceholder(true);
            constraintWidget.setVisibility(8);
        }
        constraintWidget.setCompanionWidget(object);
        if (object instanceof ConstraintHelper) {
            ((ConstraintHelper)((Object)object)).resolveRtl(constraintWidget, this.mLayoutWidget.isRtl());
        }
        if (layoutParams.isGuideline) {
            object = (androidx.constraintlayout.solver.widgets.Guideline)constraintWidget;
            int n = layoutParams.resolvedGuideBegin;
            int n2 = layoutParams.resolvedGuideEnd;
            float f = layoutParams.resolvedGuidePercent;
            if (Build.VERSION.SDK_INT < 17) {
                n = layoutParams.guideBegin;
                n2 = layoutParams.guideEnd;
                f = layoutParams.guidePercent;
            }
            if (f != -1.0f) {
                ((androidx.constraintlayout.solver.widgets.Guideline)object).setGuidePercent(f);
                return;
            }
            if (n != -1) {
                ((androidx.constraintlayout.solver.widgets.Guideline)object).setGuideBegin(n);
                return;
            }
            if (n2 == -1) return;
            ((androidx.constraintlayout.solver.widgets.Guideline)object).setGuideEnd(n2);
            return;
        }
        int n = layoutParams.resolvedLeftToLeft;
        int n3 = layoutParams.resolvedLeftToRight;
        int n4 = layoutParams.resolvedRightToLeft;
        int n5 = layoutParams.resolvedRightToRight;
        int n6 = layoutParams.resolveGoneLeftMargin;
        int n7 = layoutParams.resolveGoneRightMargin;
        float f = layoutParams.resolvedHorizontalBias;
        if (Build.VERSION.SDK_INT < 17) {
            n4 = layoutParams.leftToLeft;
            n5 = layoutParams.leftToRight;
            int n8 = layoutParams.rightToLeft;
            int n9 = layoutParams.rightToRight;
            n6 = layoutParams.goneLeftMargin;
            n7 = layoutParams.goneRightMargin;
            f = layoutParams.horizontalBias;
            n = n4;
            n3 = n5;
            if (n4 == -1) {
                n = n4;
                n3 = n5;
                if (n5 == -1) {
                    if (layoutParams.startToStart != -1) {
                        n = layoutParams.startToStart;
                        n3 = n5;
                    } else {
                        n = n4;
                        n3 = n5;
                        if (layoutParams.startToEnd != -1) {
                            n3 = layoutParams.startToEnd;
                            n = n4;
                        }
                    }
                }
            }
            n4 = n8;
            n5 = n9;
            if (n8 == -1) {
                n4 = n8;
                n5 = n9;
                if (n9 == -1) {
                    if (layoutParams.endToStart != -1) {
                        n4 = layoutParams.endToStart;
                        n5 = n9;
                    } else {
                        n4 = n8;
                        n5 = n9;
                        if (layoutParams.endToEnd != -1) {
                            n5 = layoutParams.endToEnd;
                            n4 = n8;
                        }
                    }
                }
            }
        }
        if (layoutParams.circleConstraint != -1) {
            object = (ConstraintWidget)object2.get(layoutParams.circleConstraint);
            if (object != null) {
                constraintWidget.connectCircularConstraint((ConstraintWidget)object, layoutParams.circleAngle, layoutParams.circleRadius);
            }
        } else {
            if (n != -1) {
                object = (ConstraintWidget)object2.get(n);
                if (object != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)object, ConstraintAnchor.Type.LEFT, layoutParams.leftMargin, n6);
                }
            } else if (n3 != -1 && (object = (ConstraintWidget)object2.get(n3)) != null) {
                constraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)object, ConstraintAnchor.Type.RIGHT, layoutParams.leftMargin, n6);
            }
            if (n4 != -1) {
                object = (ConstraintWidget)object2.get(n4);
                if (object != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, (ConstraintWidget)object, ConstraintAnchor.Type.LEFT, layoutParams.rightMargin, n7);
                }
            } else if (n5 != -1 && (object = (ConstraintWidget)object2.get(n5)) != null) {
                constraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, (ConstraintWidget)object, ConstraintAnchor.Type.RIGHT, layoutParams.rightMargin, n7);
            }
            if (layoutParams.topToTop != -1) {
                object = (ConstraintWidget)object2.get(layoutParams.topToTop);
                if (object != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, (ConstraintWidget)object, ConstraintAnchor.Type.TOP, layoutParams.topMargin, layoutParams.goneTopMargin);
                }
            } else if (layoutParams.topToBottom != -1 && (object = (ConstraintWidget)object2.get(layoutParams.topToBottom)) != null) {
                constraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, (ConstraintWidget)object, ConstraintAnchor.Type.BOTTOM, layoutParams.topMargin, layoutParams.goneTopMargin);
            }
            if (layoutParams.bottomToTop != -1) {
                object = (ConstraintWidget)object2.get(layoutParams.bottomToTop);
                if (object != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, (ConstraintWidget)object, ConstraintAnchor.Type.TOP, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                }
            } else if (layoutParams.bottomToBottom != -1 && (object = (ConstraintWidget)object2.get(layoutParams.bottomToBottom)) != null) {
                constraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, (ConstraintWidget)object, ConstraintAnchor.Type.BOTTOM, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
            }
            if (layoutParams.baselineToBaseline != -1) {
                View view = (View)this.mChildrenByIds.get(layoutParams.baselineToBaseline);
                object = (ConstraintWidget)object2.get(layoutParams.baselineToBaseline);
                if (object != null && view != null && view.getLayoutParams() instanceof LayoutParams) {
                    object2 = (LayoutParams)view.getLayoutParams();
                    layoutParams.needsBaseline = true;
                    object2.needsBaseline = true;
                    constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE).connect(((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.BASELINE), 0, -1, true);
                    constraintWidget.setHasBaseline(true);
                    object2.widget.setHasBaseline(true);
                    constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).reset();
                    constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
                }
            }
            if (f >= 0.0f) {
                constraintWidget.setHorizontalBiasPercent(f);
            }
            if (layoutParams.verticalBias >= 0.0f) {
                constraintWidget.setVerticalBiasPercent(layoutParams.verticalBias);
            }
        }
        if (bl && (layoutParams.editorAbsoluteX != -1 || layoutParams.editorAbsoluteY != -1)) {
            constraintWidget.setOrigin(layoutParams.editorAbsoluteX, layoutParams.editorAbsoluteY);
        }
        if (!layoutParams.horizontalDimensionFixed) {
            if (layoutParams.width == -1) {
                if (layoutParams.constrainedWidth) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                } else {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                }
                constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.LEFT).mMargin = layoutParams.leftMargin;
                constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.RIGHT).mMargin = layoutParams.rightMargin;
            } else {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                constraintWidget.setWidth(0);
            }
        } else {
            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setWidth(layoutParams.width);
            if (layoutParams.width == -2) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        }
        if (!layoutParams.verticalDimensionFixed) {
            if (layoutParams.height == -1) {
                if (layoutParams.constrainedHeight) {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                } else {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                }
                constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.TOP).mMargin = layoutParams.topMargin;
                constraintWidget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.BOTTOM).mMargin = layoutParams.bottomMargin;
            } else {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                constraintWidget.setHeight(0);
            }
        } else {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setHeight(layoutParams.height);
            if (layoutParams.height == -2) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        }
        constraintWidget.setDimensionRatio(layoutParams.dimensionRatio);
        constraintWidget.setHorizontalWeight(layoutParams.horizontalWeight);
        constraintWidget.setVerticalWeight(layoutParams.verticalWeight);
        constraintWidget.setHorizontalChainStyle(layoutParams.horizontalChainStyle);
        constraintWidget.setVerticalChainStyle(layoutParams.verticalChainStyle);
        constraintWidget.setHorizontalMatchStyle(layoutParams.matchConstraintDefaultWidth, layoutParams.matchConstraintMinWidth, layoutParams.matchConstraintMaxWidth, layoutParams.matchConstraintPercentWidth);
        constraintWidget.setVerticalMatchStyle(layoutParams.matchConstraintDefaultHeight, layoutParams.matchConstraintMinHeight, layoutParams.matchConstraintMaxHeight, layoutParams.matchConstraintPercentHeight);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected void dispatchDraw(Canvas canvas) {
        int n;
        int n2;
        Paint paint = this.mConstraintHelpers;
        if (paint != null && (n2 = paint.size()) > 0) {
            for (n = 0; n < n2; ++n) {
                this.mConstraintHelpers.get(n).updatePreDraw(this);
            }
        }
        super.dispatchDraw(canvas);
        if (!this.isInEditMode()) return;
        n2 = this.getChildCount();
        float f = this.getWidth();
        float f2 = this.getHeight();
        n = 0;
        while (n < n2) {
            paint = this.getChildAt(n);
            if (paint.getVisibility() != 8 && (paint = paint.getTag()) != null && paint instanceof String && ((String[])(paint = ((String)paint).split(","))).length == 4) {
                int n3 = Integer.parseInt(paint[0]);
                int n4 = Integer.parseInt(paint[1]);
                int n5 = Integer.parseInt(paint[2]);
                int n6 = Integer.parseInt(paint[3]);
                n3 = (int)((float)n3 / 1080.0f * f);
                n4 = (int)((float)n4 / 1920.0f * f2);
                n5 = (int)((float)n5 / 1080.0f * f);
                n6 = (int)((float)n6 / 1920.0f * f2);
                paint = new Paint();
                paint.setColor(-65536);
                float f3 = n3;
                float f4 = n4;
                float f5 = n3 + n5;
                canvas.drawLine(f3, f4, f5, f4, paint);
                float f6 = n4 + n6;
                canvas.drawLine(f5, f4, f5, f6, paint);
                canvas.drawLine(f5, f6, f3, f6, paint);
                canvas.drawLine(f3, f6, f3, f4, paint);
                paint.setColor(-16711936);
                canvas.drawLine(f3, f4, f5, f6, paint);
                canvas.drawLine(f3, f6, f5, f4, paint);
            }
            ++n;
        }
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        this.mLayoutWidget.fillMetrics(metrics);
    }

    public void forceLayout() {
        this.markHierarchyDirty();
        super.forceLayout();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public Object getDesignInformation(int n, Object object) {
        if (n != 0) return null;
        if (!(object instanceof String)) return null;
        object = (String)object;
        HashMap<String, Integer> hashMap = this.mDesignIds;
        if (hashMap == null) return null;
        if (!hashMap.containsKey(object)) return null;
        return this.mDesignIds.get(object);
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

    public View getViewById(int n) {
        return (View)this.mChildrenByIds.get(n);
    }

    public final ConstraintWidget getViewWidget(View object) {
        if (object == this) {
            return this.mLayoutWidget;
        }
        if (object != null) return ((LayoutParams)object.getLayoutParams()).widget;
        return null;
    }

    protected boolean isRtl() {
        boolean bl;
        int n = Build.VERSION.SDK_INT;
        boolean bl2 = bl = false;
        if (n < 17) return bl2;
        n = (this.getContext().getApplicationInfo().flags & 4194304) != 0 ? 1 : 0;
        bl2 = bl;
        if (n == 0) return bl2;
        bl2 = bl;
        if (1 != this.getLayoutDirection()) return bl2;
        return true;
    }

    public void loadLayoutDescription(int n) {
        if (n == 0) {
            this.mConstraintLayoutSpec = null;
            return;
        }
        try {
            ConstraintLayoutStates constraintLayoutStates;
            this.mConstraintLayoutSpec = constraintLayoutStates = new ConstraintLayoutStates(this.getContext(), this, n);
            return;
        }
        catch (Resources.NotFoundException notFoundException) {
            this.mConstraintLayoutSpec = null;
            return;
        }
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n3 = this.getChildCount();
        bl = this.isInEditMode();
        n2 = 0;
        for (n = 0; n < n3; ++n) {
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            ConstraintWidget constraintWidget = layoutParams.widget;
            if (view.getVisibility() == 8 && !layoutParams.isGuideline && !layoutParams.isHelper && !layoutParams.isVirtualGroup && !bl || layoutParams.isInPlaceholder) continue;
            int n5 = constraintWidget.getX();
            int n6 = constraintWidget.getY();
            n4 = constraintWidget.getWidth() + n5;
            int n7 = constraintWidget.getHeight() + n6;
            view.layout(n5, n6, n4, n7);
            if (!(view instanceof Placeholder) || (layoutParams = ((Placeholder)view).getContent()) == null) continue;
            layoutParams.setVisibility(0);
            layoutParams.layout(n5, n6, n4, n7);
        }
        n3 = this.mConstraintHelpers.size();
        if (n3 <= 0) return;
        n = n2;
        while (n < n3) {
            this.mConstraintHelpers.get(n).updatePostLayout(this);
            ++n;
        }
    }

    protected void onMeasure(int n, int n2) {
        this.mOnMeasureWidthMeasureSpec = n;
        this.mOnMeasureHeightMeasureSpec = n2;
        this.mLayoutWidget.setRtl(this.isRtl());
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            if (this.updateHierarchy()) {
                this.mLayoutWidget.updateHierarchy();
            }
        }
        this.resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, n, n2);
        this.resolveMeasuredDimension(n, n2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
    }

    public void onViewAdded(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        Object object = this.getViewWidget(view);
        if (view instanceof Guideline && !(object instanceof androidx.constraintlayout.solver.widgets.Guideline)) {
            object = (LayoutParams)view.getLayoutParams();
            ((LayoutParams)object).widget = new androidx.constraintlayout.solver.widgets.Guideline();
            ((LayoutParams)object).isGuideline = true;
            ((androidx.constraintlayout.solver.widgets.Guideline)((LayoutParams)object).widget).setOrientation(((LayoutParams)object).orientation);
        }
        if (view instanceof ConstraintHelper) {
            object = (ConstraintHelper)view;
            ((ConstraintHelper)((Object)object)).validateParams();
            ((LayoutParams)view.getLayoutParams()).isHelper = true;
            if (!this.mConstraintHelpers.contains(object)) {
                this.mConstraintHelpers.add((ConstraintHelper)((Object)object));
            }
        }
        this.mChildrenByIds.put(view.getId(), (Object)view);
        this.mDirtyHierarchy = true;
    }

    public void onViewRemoved(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        ConstraintWidget constraintWidget = this.getViewWidget(view);
        this.mLayoutWidget.remove(constraintWidget);
        this.mConstraintHelpers.remove((Object)view);
        this.mDirtyHierarchy = true;
    }

    protected void parseLayoutDescription(int n) {
        this.mConstraintLayoutSpec = new ConstraintLayoutStates(this.getContext(), this, n);
    }

    public void removeView(View view) {
        super.removeView(view);
        if (Build.VERSION.SDK_INT >= 14) return;
        this.onViewRemoved(view);
    }

    public void requestLayout() {
        this.markHierarchyDirty();
        super.requestLayout();
    }

    protected void resolveMeasuredDimension(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        int n5 = this.mMeasurer.paddingHeight;
        n3 += this.mMeasurer.paddingWidth;
        n4 += n5;
        if (Build.VERSION.SDK_INT < 11) {
            this.setMeasuredDimension(n3, n4);
            this.mLastMeasureWidth = n3;
            this.mLastMeasureHeight = n4;
            return;
        }
        n = ConstraintLayout.resolveSizeAndState((int)n3, (int)n, (int)0);
        n3 = ConstraintLayout.resolveSizeAndState((int)n4, (int)n2, (int)0);
        n2 = Math.min(this.mMaxWidth, n & 16777215);
        n3 = Math.min(this.mMaxHeight, n3 & 16777215);
        n = n2;
        if (bl) {
            n = n2 | 16777216;
        }
        n2 = n3;
        if (bl2) {
            n2 = n3 | 16777216;
        }
        this.setMeasuredDimension(n, n2);
        this.mLastMeasureWidth = n;
        this.mLastMeasureHeight = n2;
    }

    protected void resolveSystem(ConstraintWidgetContainer constraintWidgetContainer, int n, int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n2);
        int n5 = View.MeasureSpec.getSize((int)n2);
        int n6 = View.MeasureSpec.getMode((int)n3);
        int n7 = View.MeasureSpec.getSize((int)n3);
        int n8 = Math.max(0, this.getPaddingTop());
        int n9 = Math.max(0, this.getPaddingBottom());
        int n10 = n8 + n9;
        int n11 = this.getPaddingWidth();
        this.mMeasurer.captureLayoutInfos(n2, n3, n8, n9, n11, n10);
        if (Build.VERSION.SDK_INT >= 17) {
            n2 = Math.max(0, this.getPaddingStart());
            n3 = Math.max(0, this.getPaddingEnd());
            if (n2 <= 0 && n3 <= 0) {
                n2 = Math.max(0, this.getPaddingLeft());
            } else if (this.isRtl()) {
                n2 = n3;
            }
        } else {
            n2 = Math.max(0, this.getPaddingLeft());
        }
        n3 = n5 - n11;
        this.setSelfDimensionBehaviour(constraintWidgetContainer, n4, n3, n6, n7 -= n10);
        constraintWidgetContainer.measure(n, n4, n3, n6, n7, this.mLastMeasureWidth, this.mLastMeasureHeight, n2, n8);
    }

    public void setConstraintSet(ConstraintSet constraintSet) {
        this.mConstraintSet = constraintSet;
    }

    public void setDesignInformation(int n, Object object, Object object2) {
        if (n != 0) return;
        if (!(object instanceof String)) return;
        if (!(object2 instanceof Integer)) return;
        if (this.mDesignIds == null) {
            this.mDesignIds = new HashMap();
        }
        String string2 = (String)object;
        n = string2.indexOf("/");
        object = string2;
        if (n != -1) {
            object = string2.substring(n + 1);
        }
        n = (Integer)object2;
        this.mDesignIds.put((String)object, n);
    }

    public void setId(int n) {
        this.mChildrenByIds.remove(this.getId());
        super.setId(n);
        this.mChildrenByIds.put(this.getId(), (Object)this);
    }

    public void setMaxHeight(int n) {
        if (n == this.mMaxHeight) {
            return;
        }
        this.mMaxHeight = n;
        this.requestLayout();
    }

    public void setMaxWidth(int n) {
        if (n == this.mMaxWidth) {
            return;
        }
        this.mMaxWidth = n;
        this.requestLayout();
    }

    public void setMinHeight(int n) {
        if (n == this.mMinHeight) {
            return;
        }
        this.mMinHeight = n;
        this.requestLayout();
    }

    public void setMinWidth(int n) {
        if (n == this.mMinWidth) {
            return;
        }
        this.mMinWidth = n;
        this.requestLayout();
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates == null) return;
        constraintLayoutStates.setOnConstraintsChanged(constraintsChangedListener);
    }

    public void setOptimizationLevel(int n) {
        this.mOptimizationLevel = n;
        this.mLayoutWidget.setOptimizationLevel(n);
    }

    /*
     * Unable to fully structure code
     */
    protected void setSelfDimensionBehaviour(ConstraintWidgetContainer var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block12 : {
            block10 : {
                block11 : {
                    block9 : {
                        block7 : {
                            block8 : {
                                var6_6 = this.mMeasurer.paddingHeight;
                                var7_7 = this.mMeasurer.paddingWidth;
                                var8_8 = ConstraintWidget.DimensionBehaviour.FIXED;
                                var9_9 = ConstraintWidget.DimensionBehaviour.FIXED;
                                var10_10 = this.getChildCount();
                                if (var2_2 == Integer.MIN_VALUE) break block7;
                                if (var2_2 == 0) break block8;
                                if (var2_2 != 1073741824) ** GOTO lbl-1000
                                var3_3 = Math.min(this.mMaxWidth - var7_7, var3_3);
                                break block9;
                            }
                            var8_8 = var11_11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                            if (var10_10 == 0) {
                                var3_3 = Math.max(0, this.mMinWidth);
                                var8_8 = var11_11;
                            } else lbl-1000: // 2 sources:
                            {
                                var3_3 = 0;
                            }
                            break block9;
                        }
                        var8_8 = var11_11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        if (var10_10 == 0) {
                            var3_3 = Math.max(0, this.mMinWidth);
                            var8_8 = var11_11;
                        }
                    }
                    if (var4_4 == Integer.MIN_VALUE) break block10;
                    if (var4_4 == 0) break block11;
                    if (var4_4 != 1073741824) ** GOTO lbl-1000
                    var5_5 = Math.min(this.mMaxHeight - var6_6, var5_5);
                    break block12;
                }
                var9_9 = var11_11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (var10_10 == 0) {
                    var5_5 = Math.max(0, this.mMinHeight);
                    var9_9 = var11_11;
                } else lbl-1000: // 2 sources:
                {
                    var5_5 = 0;
                }
                break block12;
            }
            var9_9 = var11_11 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (var10_10 == 0) {
                var5_5 = Math.max(0, this.mMinHeight);
                var9_9 = var11_11;
            }
        }
        if (var3_3 != var1_1.getWidth() || var5_5 != var1_1.getHeight()) {
            var1_1.invalidateMeasures();
        }
        var1_1.setX(0);
        var1_1.setY(0);
        var1_1.setMaxWidth(this.mMaxWidth - var7_7);
        var1_1.setMaxHeight(this.mMaxHeight - var6_6);
        var1_1.setMinWidth(0);
        var1_1.setMinHeight(0);
        var1_1.setHorizontalDimensionBehaviour(var8_8);
        var1_1.setWidth(var3_3);
        var1_1.setVerticalDimensionBehaviour(var9_9);
        var1_1.setHeight(var5_5);
        var1_1.setMinWidth(this.mMinWidth - var7_7);
        var1_1.setMinHeight(this.mMinHeight - var6_6);
    }

    public void setState(int n, int n2, int n3) {
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates == null) return;
        constraintLayoutStates.updateConstraints(n, n2, n3);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
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
        public float circleAngle = 0.0f;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        public String constraintTag = null;
        public String dimensionRatio = null;
        int dimensionRatioSide = 1;
        float dimensionRatioValue = 0.0f;
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
        public float guidePercent = -1.0f;
        public boolean helped = false;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        boolean horizontalDimensionFixed = true;
        public float horizontalWeight = -1.0f;
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
        public float matchConstraintPercentHeight = 1.0f;
        public float matchConstraintPercentWidth = 1.0f;
        boolean needsBaseline = false;
        public int orientation = -1;
        int resolveGoneLeftMargin = -1;
        int resolveGoneRightMargin = -1;
        int resolvedGuideBegin;
        int resolvedGuideEnd;
        float resolvedGuidePercent;
        float resolvedHorizontalBias = 0.5f;
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
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        boolean verticalDimensionFixed = true;
        public float verticalWeight = -1.0f;
        ConstraintWidget widget = new ConstraintWidget();

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        /*
         * Loose catch block
         * Enabled unnecessary exception pruning
         */
        public LayoutParams(Context context, AttributeSet object) {
            super(context, (AttributeSet)object);
            context = context.obtainStyledAttributes((AttributeSet)object, R.styleable.ConstraintLayout_Layout);
            int n = context.getIndexCount();
            int n2 = 0;
            do {
                block71 : {
                    if (n2 >= n) {
                        context.recycle();
                        this.validate();
                        return;
                    }
                    int n3 = context.getIndex(n2);
                    int n4 = Table.map.get(n3);
                    block7 : switch (n4) {
                        default: {
                            float f;
                            switch (n4) {
                                default: {
                                    break block7;
                                }
                                case 51: {
                                    this.constraintTag = context.getString(n3);
                                    break block7;
                                }
                                case 50: {
                                    this.editorAbsoluteY = context.getDimensionPixelOffset(n3, this.editorAbsoluteY);
                                    break block7;
                                }
                                case 49: {
                                    this.editorAbsoluteX = context.getDimensionPixelOffset(n3, this.editorAbsoluteX);
                                    break block7;
                                }
                                case 48: {
                                    this.verticalChainStyle = context.getInt(n3, 0);
                                    break block7;
                                }
                                case 47: {
                                    this.horizontalChainStyle = context.getInt(n3, 0);
                                    break block7;
                                }
                                case 46: {
                                    this.verticalWeight = context.getFloat(n3, this.verticalWeight);
                                    break block7;
                                }
                                case 45: {
                                    this.horizontalWeight = context.getFloat(n3, this.horizontalWeight);
                                    break block7;
                                }
                                case 44: 
                            }
                            this.dimensionRatio = object = context.getString(n3);
                            this.dimensionRatioValue = Float.NaN;
                            this.dimensionRatioSide = -1;
                            if (object == null) break;
                            n4 = ((String)object).length();
                            n3 = this.dimensionRatio.indexOf(44);
                            if (n3 > 0 && n3 < n4 - 1) {
                                object = this.dimensionRatio.substring(0, n3);
                                if (((String)object).equalsIgnoreCase("W")) {
                                    this.dimensionRatioSide = 0;
                                } else if (((String)object).equalsIgnoreCase("H")) {
                                    this.dimensionRatioSide = 1;
                                }
                                ++n3;
                            } else {
                                n3 = 0;
                            }
                            int n5 = this.dimensionRatio.indexOf(58);
                            if (n5 >= 0 && n5 < n4 - 1) {
                                object = this.dimensionRatio.substring(n3, n5);
                                String string2 = this.dimensionRatio.substring(n5 + 1);
                                if (((String)object).length() <= 0 || string2.length() <= 0) break;
                                f = Float.parseFloat((String)object);
                                float f2 = Float.parseFloat(string2);
                                if (!(f > 0.0f) || !(f2 > 0.0f)) break;
                                if (this.dimensionRatioSide == 1) {
                                    this.dimensionRatioValue = Math.abs(f2 / f);
                                    break;
                                }
                                this.dimensionRatioValue = Math.abs(f / f2);
                            }
                            object = this.dimensionRatio.substring(n3);
                            if (((String)object).length() <= 0) break;
                            this.dimensionRatioValue = Float.parseFloat((String)object);
                            break;
                        }
                        case 38: {
                            this.matchConstraintPercentHeight = Math.max(0.0f, context.getFloat(n3, this.matchConstraintPercentHeight));
                            this.matchConstraintDefaultHeight = 2;
                            break;
                        }
                        case 37: {
                            try {
                                this.matchConstraintMaxHeight = context.getDimensionPixelSize(n3, this.matchConstraintMaxHeight);
                            }
                            catch (Exception exception) {
                                if (context.getInt(n3, this.matchConstraintMaxHeight) != -2) break;
                                this.matchConstraintMaxHeight = -2;
                            }
                            break;
                        }
                        case 36: {
                            try {
                                this.matchConstraintMinHeight = context.getDimensionPixelSize(n3, this.matchConstraintMinHeight);
                            }
                            catch (Exception exception) {
                                if (context.getInt(n3, this.matchConstraintMinHeight) != -2) break;
                                this.matchConstraintMinHeight = -2;
                            }
                            break;
                        }
                        case 35: {
                            this.matchConstraintPercentWidth = Math.max(0.0f, context.getFloat(n3, this.matchConstraintPercentWidth));
                            this.matchConstraintDefaultWidth = 2;
                            break;
                        }
                        case 34: {
                            try {
                                this.matchConstraintMaxWidth = context.getDimensionPixelSize(n3, this.matchConstraintMaxWidth);
                            }
                            catch (Exception exception) {
                                if (context.getInt(n3, this.matchConstraintMaxWidth) != -2) break;
                                this.matchConstraintMaxWidth = -2;
                            }
                            break;
                        }
                        case 33: {
                            try {
                                this.matchConstraintMinWidth = context.getDimensionPixelSize(n3, this.matchConstraintMinWidth);
                            }
                            catch (Exception exception) {
                                if (context.getInt(n3, this.matchConstraintMinWidth) != -2) break;
                                this.matchConstraintMinWidth = -2;
                            }
                            break;
                        }
                        case 32: {
                            this.matchConstraintDefaultHeight = n3 = context.getInt(n3, 0);
                            if (n3 != 1) break;
                            Log.e((String)ConstraintLayout.TAG, (String)"layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        }
                        case 31: {
                            this.matchConstraintDefaultWidth = n3 = context.getInt(n3, 0);
                            if (n3 != 1) break;
                            Log.e((String)ConstraintLayout.TAG, (String)"layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        }
                        case 30: {
                            this.verticalBias = context.getFloat(n3, this.verticalBias);
                            break;
                        }
                        case 29: {
                            this.horizontalBias = context.getFloat(n3, this.horizontalBias);
                            break;
                        }
                        case 28: {
                            this.constrainedHeight = context.getBoolean(n3, this.constrainedHeight);
                            break;
                        }
                        case 27: {
                            this.constrainedWidth = context.getBoolean(n3, this.constrainedWidth);
                            break;
                        }
                        case 26: {
                            this.goneEndMargin = context.getDimensionPixelSize(n3, this.goneEndMargin);
                            break;
                        }
                        case 25: {
                            this.goneStartMargin = context.getDimensionPixelSize(n3, this.goneStartMargin);
                            break;
                        }
                        case 24: {
                            this.goneBottomMargin = context.getDimensionPixelSize(n3, this.goneBottomMargin);
                            break;
                        }
                        case 23: {
                            this.goneRightMargin = context.getDimensionPixelSize(n3, this.goneRightMargin);
                            break;
                        }
                        case 22: {
                            this.goneTopMargin = context.getDimensionPixelSize(n3, this.goneTopMargin);
                            break;
                        }
                        case 21: {
                            this.goneLeftMargin = context.getDimensionPixelSize(n3, this.goneLeftMargin);
                            break;
                        }
                        case 20: {
                            this.endToEnd = n4 = context.getResourceId(n3, this.endToEnd);
                            if (n4 != -1) break;
                            this.endToEnd = context.getInt(n3, -1);
                            break;
                        }
                        case 19: {
                            this.endToStart = n4 = context.getResourceId(n3, this.endToStart);
                            if (n4 != -1) break;
                            this.endToStart = context.getInt(n3, -1);
                            break;
                        }
                        case 18: {
                            this.startToStart = n4 = context.getResourceId(n3, this.startToStart);
                            if (n4 != -1) break;
                            this.startToStart = context.getInt(n3, -1);
                            break;
                        }
                        case 17: {
                            this.startToEnd = n4 = context.getResourceId(n3, this.startToEnd);
                            if (n4 != -1) break;
                            this.startToEnd = context.getInt(n3, -1);
                            break;
                        }
                        case 16: {
                            this.baselineToBaseline = n4 = context.getResourceId(n3, this.baselineToBaseline);
                            if (n4 != -1) break;
                            this.baselineToBaseline = context.getInt(n3, -1);
                            break;
                        }
                        case 15: {
                            this.bottomToBottom = n4 = context.getResourceId(n3, this.bottomToBottom);
                            if (n4 != -1) break;
                            this.bottomToBottom = context.getInt(n3, -1);
                            break;
                        }
                        case 14: {
                            this.bottomToTop = n4 = context.getResourceId(n3, this.bottomToTop);
                            if (n4 != -1) break;
                            this.bottomToTop = context.getInt(n3, -1);
                            break;
                        }
                        case 13: {
                            this.topToBottom = n4 = context.getResourceId(n3, this.topToBottom);
                            if (n4 != -1) break;
                            this.topToBottom = context.getInt(n3, -1);
                            break;
                        }
                        case 12: {
                            this.topToTop = n4 = context.getResourceId(n3, this.topToTop);
                            if (n4 != -1) break;
                            this.topToTop = context.getInt(n3, -1);
                            break;
                        }
                        case 11: {
                            this.rightToRight = n4 = context.getResourceId(n3, this.rightToRight);
                            if (n4 != -1) break;
                            this.rightToRight = context.getInt(n3, -1);
                            break;
                        }
                        case 10: {
                            this.rightToLeft = n4 = context.getResourceId(n3, this.rightToLeft);
                            if (n4 != -1) break;
                            this.rightToLeft = context.getInt(n3, -1);
                            break;
                        }
                        case 9: {
                            this.leftToRight = n4 = context.getResourceId(n3, this.leftToRight);
                            if (n4 != -1) break;
                            this.leftToRight = context.getInt(n3, -1);
                            break;
                        }
                        case 8: {
                            this.leftToLeft = n4 = context.getResourceId(n3, this.leftToLeft);
                            if (n4 != -1) break;
                            this.leftToLeft = context.getInt(n3, -1);
                            break;
                        }
                        case 7: {
                            this.guidePercent = context.getFloat(n3, this.guidePercent);
                            break;
                        }
                        case 6: {
                            this.guideEnd = context.getDimensionPixelOffset(n3, this.guideEnd);
                            break;
                        }
                        case 5: {
                            this.guideBegin = context.getDimensionPixelOffset(n3, this.guideBegin);
                            break;
                        }
                        case 4: {
                            float f;
                            this.circleAngle = f = context.getFloat(n3, this.circleAngle) % 360.0f;
                            if (!(f < 0.0f)) break;
                            this.circleAngle = (360.0f - f) % 360.0f;
                            break;
                        }
                        case 3: {
                            this.circleRadius = context.getDimensionPixelSize(n3, this.circleRadius);
                            break;
                        }
                        case 2: {
                            this.circleConstraint = n4 = context.getResourceId(n3, this.circleConstraint);
                            if (n4 != -1) break;
                            this.circleConstraint = context.getInt(n3, -1);
                            break;
                        }
                        case 1: {
                            this.orientation = context.getInt(n3, this.orientation);
                            break;
                        }
                    }
                    break block71;
                    catch (NumberFormatException numberFormatException) {}
                }
                ++n2;
            } while (true);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.guideBegin = layoutParams.guideBegin;
            this.guideEnd = layoutParams.guideEnd;
            this.guidePercent = layoutParams.guidePercent;
            this.leftToLeft = layoutParams.leftToLeft;
            this.leftToRight = layoutParams.leftToRight;
            this.rightToLeft = layoutParams.rightToLeft;
            this.rightToRight = layoutParams.rightToRight;
            this.topToTop = layoutParams.topToTop;
            this.topToBottom = layoutParams.topToBottom;
            this.bottomToTop = layoutParams.bottomToTop;
            this.bottomToBottom = layoutParams.bottomToBottom;
            this.baselineToBaseline = layoutParams.baselineToBaseline;
            this.circleConstraint = layoutParams.circleConstraint;
            this.circleRadius = layoutParams.circleRadius;
            this.circleAngle = layoutParams.circleAngle;
            this.startToEnd = layoutParams.startToEnd;
            this.startToStart = layoutParams.startToStart;
            this.endToStart = layoutParams.endToStart;
            this.endToEnd = layoutParams.endToEnd;
            this.goneLeftMargin = layoutParams.goneLeftMargin;
            this.goneTopMargin = layoutParams.goneTopMargin;
            this.goneRightMargin = layoutParams.goneRightMargin;
            this.goneBottomMargin = layoutParams.goneBottomMargin;
            this.goneStartMargin = layoutParams.goneStartMargin;
            this.goneEndMargin = layoutParams.goneEndMargin;
            this.horizontalBias = layoutParams.horizontalBias;
            this.verticalBias = layoutParams.verticalBias;
            this.dimensionRatio = layoutParams.dimensionRatio;
            this.dimensionRatioValue = layoutParams.dimensionRatioValue;
            this.dimensionRatioSide = layoutParams.dimensionRatioSide;
            this.horizontalWeight = layoutParams.horizontalWeight;
            this.verticalWeight = layoutParams.verticalWeight;
            this.horizontalChainStyle = layoutParams.horizontalChainStyle;
            this.verticalChainStyle = layoutParams.verticalChainStyle;
            this.constrainedWidth = layoutParams.constrainedWidth;
            this.constrainedHeight = layoutParams.constrainedHeight;
            this.matchConstraintDefaultWidth = layoutParams.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = layoutParams.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = layoutParams.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = layoutParams.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = layoutParams.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = layoutParams.matchConstraintMaxHeight;
            this.matchConstraintPercentWidth = layoutParams.matchConstraintPercentWidth;
            this.matchConstraintPercentHeight = layoutParams.matchConstraintPercentHeight;
            this.editorAbsoluteX = layoutParams.editorAbsoluteX;
            this.editorAbsoluteY = layoutParams.editorAbsoluteY;
            this.orientation = layoutParams.orientation;
            this.horizontalDimensionFixed = layoutParams.horizontalDimensionFixed;
            this.verticalDimensionFixed = layoutParams.verticalDimensionFixed;
            this.needsBaseline = layoutParams.needsBaseline;
            this.isGuideline = layoutParams.isGuideline;
            this.resolvedLeftToLeft = layoutParams.resolvedLeftToLeft;
            this.resolvedLeftToRight = layoutParams.resolvedLeftToRight;
            this.resolvedRightToLeft = layoutParams.resolvedRightToLeft;
            this.resolvedRightToRight = layoutParams.resolvedRightToRight;
            this.resolveGoneLeftMargin = layoutParams.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = layoutParams.resolveGoneRightMargin;
            this.resolvedHorizontalBias = layoutParams.resolvedHorizontalBias;
            this.constraintTag = layoutParams.constraintTag;
            this.widget = layoutParams.widget;
        }

        public String getConstraintTag() {
            return this.constraintTag;
        }

        public ConstraintWidget getConstraintWidget() {
            return this.widget;
        }

        public void reset() {
            ConstraintWidget constraintWidget = this.widget;
            if (constraintWidget == null) return;
            constraintWidget.reset();
        }

        /*
         * Unable to fully structure code
         */
        public void resolveLayoutDirection(int var1_1) {
            block34 : {
                block30 : {
                    block33 : {
                        block32 : {
                            block31 : {
                                var2_2 = this.leftMargin;
                                var3_3 = this.rightMargin;
                                var4_4 = Build.VERSION.SDK_INT;
                                var5_5 = 0;
                                if (var4_4 < 17) ** GOTO lbl-1000
                                super.resolveLayoutDirection(var1_1);
                                if (1 == this.getLayoutDirection()) {
                                    var1_1 = 1;
                                } else lbl-1000: // 2 sources:
                                {
                                    var1_1 = 0;
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
                                if (var1_1 == 0) break block30;
                                var1_1 = this.startToEnd;
                                if (var1_1 == -1) break block31;
                                this.resolvedRightToLeft = var1_1;
                                break block32;
                            }
                            var4_4 = this.startToStart;
                            var1_1 = var5_5;
                            if (var4_4 == -1) break block33;
                            this.resolvedRightToRight = var4_4;
                        }
                        var1_1 = 1;
                    }
                    if ((var5_5 = this.endToStart) != -1) {
                        this.resolvedLeftToRight = var5_5;
                        var1_1 = 1;
                    }
                    if ((var5_5 = this.endToEnd) != -1) {
                        this.resolvedLeftToLeft = var5_5;
                        var1_1 = 1;
                    }
                    if ((var5_5 = this.goneStartMargin) != -1) {
                        this.resolveGoneRightMargin = var5_5;
                    }
                    if ((var5_5 = this.goneEndMargin) != -1) {
                        this.resolveGoneLeftMargin = var5_5;
                    }
                    if (var1_1 != 0) {
                        this.resolvedHorizontalBias = 1.0f - this.horizontalBias;
                    }
                    if (this.isGuideline && this.orientation == 1) {
                        var6_6 = this.guidePercent;
                        if (var6_6 != -1.0f) {
                            this.resolvedGuidePercent = 1.0f - var6_6;
                            this.resolvedGuideBegin = -1;
                            this.resolvedGuideEnd = -1;
                        } else {
                            var1_1 = this.guideBegin;
                            if (var1_1 != -1) {
                                this.resolvedGuideEnd = var1_1;
                                this.resolvedGuideBegin = -1;
                                this.resolvedGuidePercent = -1.0f;
                            } else {
                                var1_1 = this.guideEnd;
                                if (var1_1 != -1) {
                                    this.resolvedGuideBegin = var1_1;
                                    this.resolvedGuideEnd = -1;
                                    this.resolvedGuidePercent = -1.0f;
                                }
                            }
                        }
                    }
                    break block34;
                }
                var1_1 = this.startToEnd;
                if (var1_1 != -1) {
                    this.resolvedLeftToRight = var1_1;
                }
                if ((var1_1 = this.startToStart) != -1) {
                    this.resolvedLeftToLeft = var1_1;
                }
                if ((var1_1 = this.endToStart) != -1) {
                    this.resolvedRightToLeft = var1_1;
                }
                if ((var1_1 = this.endToEnd) != -1) {
                    this.resolvedRightToRight = var1_1;
                }
                if ((var1_1 = this.goneStartMargin) != -1) {
                    this.resolveGoneLeftMargin = var1_1;
                }
                if ((var1_1 = this.goneEndMargin) != -1) {
                    this.resolveGoneRightMargin = var1_1;
                }
            }
            if (this.endToStart != -1) return;
            if (this.endToEnd != -1) return;
            if (this.startToStart != -1) return;
            if (this.startToEnd != -1) return;
            var1_1 = this.rightToLeft;
            if (var1_1 != -1) {
                this.resolvedRightToLeft = var1_1;
                if (this.rightMargin <= 0 && var3_3 > 0) {
                    this.rightMargin = var3_3;
                }
            } else {
                var1_1 = this.rightToRight;
                if (var1_1 != -1) {
                    this.resolvedRightToRight = var1_1;
                    if (this.rightMargin <= 0 && var3_3 > 0) {
                        this.rightMargin = var3_3;
                    }
                }
            }
            if ((var1_1 = this.leftToLeft) != -1) {
                this.resolvedLeftToLeft = var1_1;
                if (this.leftMargin > 0) return;
                if (var2_2 <= 0) return;
                this.leftMargin = var2_2;
                return;
            }
            var1_1 = this.leftToRight;
            if (var1_1 == -1) return;
            this.resolvedLeftToRight = var1_1;
            if (this.leftMargin > 0) return;
            if (var2_2 <= 0) return;
            this.leftMargin = var2_2;
        }

        public void setWidgetDebugName(String string2) {
            this.widget.setDebugName(string2);
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
            if (this.guidePercent == -1.0f && this.guideBegin == -1) {
                if (this.guideEnd == -1) return;
            }
            this.isGuideline = true;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            if (!(this.widget instanceof androidx.constraintlayout.solver.widgets.Guideline)) {
                this.widget = new androidx.constraintlayout.solver.widgets.Guideline();
            }
            ((androidx.constraintlayout.solver.widgets.Guideline)this.widget).setOrientation(this.orientation);
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
                SparseIntArray sparseIntArray;
                map = sparseIntArray = new SparseIntArray();
                sparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
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

            private Table() {
            }
        }

    }

    class Measurer
    implements BasicMeasure.Measurer {
        ConstraintLayout layout;
        int layoutHeightSpec;
        int layoutWidthSpec;
        int paddingBottom;
        int paddingHeight;
        int paddingTop;
        int paddingWidth;

        public Measurer(ConstraintLayout constraintLayout2) {
            this.layout = constraintLayout2;
        }

        public void captureLayoutInfos(int n, int n2, int n3, int n4, int n5, int n6) {
            this.paddingTop = n3;
            this.paddingBottom = n4;
            this.paddingWidth = n5;
            this.paddingHeight = n6;
            this.layoutWidthSpec = n;
            this.layoutHeightSpec = n2;
        }

        @Override
        public final void didMeasures() {
            int n;
            int n2 = this.layout.getChildCount();
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                View view = this.layout.getChildAt(n);
                if (!(view instanceof Placeholder)) continue;
                ((Placeholder)view).updatePostMeasure(this.layout);
            }
            n2 = this.layout.mConstraintHelpers.size();
            if (n2 <= 0) return;
            n = n3;
            while (n < n2) {
                ((ConstraintHelper)((Object)this.layout.mConstraintHelpers.get(n))).updatePostMeasure(this.layout);
                ++n;
            }
        }

        @Override
        public final void measure(ConstraintWidget constraintWidget, BasicMeasure.Measure measure) {
            int n;
            int n2;
            View view;
            int n3;
            Object object;
            int n4;
            Object object2;
            int n5;
            int n6;
            block34 : {
                block37 : {
                    block30 : {
                        block35 : {
                            block31 : {
                                block32 : {
                                    block36 : {
                                        block33 : {
                                            block29 : {
                                                block26 : {
                                                    block22 : {
                                                        block27 : {
                                                            block23 : {
                                                                block24 : {
                                                                    block28 : {
                                                                        block25 : {
                                                                            if (constraintWidget == null) {
                                                                                return;
                                                                            }
                                                                            if (constraintWidget.getVisibility() == 8 && !constraintWidget.isInPlaceholder()) {
                                                                                measure.measuredWidth = 0;
                                                                                measure.measuredHeight = 0;
                                                                                measure.measuredBaseline = 0;
                                                                                return;
                                                                            }
                                                                            object = measure.horizontalBehavior;
                                                                            object2 = measure.verticalBehavior;
                                                                            n3 = measure.horizontalDimension;
                                                                            n6 = measure.verticalDimension;
                                                                            n2 = this.paddingTop + this.paddingBottom;
                                                                            n4 = this.paddingWidth;
                                                                            view = (View)constraintWidget.getCompanionWidget();
                                                                            n = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[object.ordinal()];
                                                                            if (n == 1) break block22;
                                                                            if (n == 2) break block23;
                                                                            if (n == 3) break block24;
                                                                            if (n == 4) break block25;
                                                                            n4 = 0;
                                                                            break block26;
                                                                        }
                                                                        n5 = ViewGroup.getChildMeasureSpec((int)this.layoutWidthSpec, (int)n4, (int)-2);
                                                                        n4 = constraintWidget.mMatchConstraintDefaultWidth == 1 ? 1 : 0;
                                                                        constraintWidget.wrapMeasure[2] = 0;
                                                                        n3 = n5;
                                                                        if (!measure.useCurrentDimensions) break block27;
                                                                        n = n4 != 0 && constraintWidget.wrapMeasure[3] != 0 && constraintWidget.wrapMeasure[0] != constraintWidget.getWidth() || view instanceof Placeholder ? 1 : 0;
                                                                        if (n4 == 0) break block28;
                                                                        n3 = n5;
                                                                        if (n == 0) break block27;
                                                                    }
                                                                    n4 = View.MeasureSpec.makeMeasureSpec((int)constraintWidget.getWidth(), (int)1073741824);
                                                                    break block26;
                                                                }
                                                                n4 = ViewGroup.getChildMeasureSpec((int)this.layoutWidthSpec, (int)(n4 + constraintWidget.getHorizontalMargin()), (int)-1);
                                                                constraintWidget.wrapMeasure[2] = -1;
                                                                break block26;
                                                            }
                                                            n3 = ViewGroup.getChildMeasureSpec((int)this.layoutWidthSpec, (int)n4, (int)-2);
                                                            constraintWidget.wrapMeasure[2] = -2;
                                                        }
                                                        n5 = 1;
                                                        break block29;
                                                    }
                                                    n4 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824);
                                                    constraintWidget.wrapMeasure[2] = n3;
                                                }
                                                n5 = 0;
                                                n3 = n4;
                                            }
                                            n4 = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[object2.ordinal()];
                                            if (n4 == 1) break block30;
                                            if (n4 == 2) break block31;
                                            if (n4 == 3) break block32;
                                            if (n4 == 4) break block33;
                                            n = 0;
                                            n4 = 0;
                                            break block34;
                                        }
                                        n2 = ViewGroup.getChildMeasureSpec((int)this.layoutHeightSpec, (int)n2, (int)-2);
                                        n = constraintWidget.mMatchConstraintDefaultHeight == 1 ? 1 : 0;
                                        constraintWidget.wrapMeasure[3] = 0;
                                        n4 = n2;
                                        if (!measure.useCurrentDimensions) break block35;
                                        n6 = n != 0 && constraintWidget.wrapMeasure[2] != 0 && constraintWidget.wrapMeasure[1] != constraintWidget.getHeight() || view instanceof Placeholder ? 1 : 0;
                                        if (n == 0) break block36;
                                        n4 = n2;
                                        if (n6 == 0) break block35;
                                    }
                                    n4 = View.MeasureSpec.makeMeasureSpec((int)constraintWidget.getHeight(), (int)1073741824);
                                    break block37;
                                }
                                n4 = ViewGroup.getChildMeasureSpec((int)this.layoutHeightSpec, (int)(n2 + constraintWidget.getVerticalMargin()), (int)-1);
                                constraintWidget.wrapMeasure[3] = -1;
                                break block37;
                            }
                            n4 = ViewGroup.getChildMeasureSpec((int)this.layoutHeightSpec, (int)n2, (int)-2);
                            constraintWidget.wrapMeasure[3] = -2;
                        }
                        n = 1;
                        break block34;
                    }
                    n4 = View.MeasureSpec.makeMeasureSpec((int)n6, (int)1073741824);
                    constraintWidget.wrapMeasure[3] = n6;
                }
                n = 0;
            }
            n6 = object == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0;
            int n7 = object2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0;
            n2 = object2 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT && object2 != ConstraintWidget.DimensionBehaviour.FIXED ? 0 : 1;
            boolean bl = object == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || object == ConstraintWidget.DimensionBehaviour.FIXED;
            boolean bl2 = n6 != 0 && constraintWidget.mDimensionRatio > 0.0f;
            boolean bl3 = n7 != 0 && constraintWidget.mDimensionRatio > 0.0f;
            object2 = (LayoutParams)view.getLayoutParams();
            if (!measure.useCurrentDimensions && n6 != 0 && constraintWidget.mMatchConstraintDefaultWidth == 0 && n7 != 0 && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                n = 0;
                n3 = 0;
                n4 = 0;
            } else {
                if (view instanceof VirtualLayout && constraintWidget instanceof androidx.constraintlayout.solver.widgets.VirtualLayout) {
                    object = (androidx.constraintlayout.solver.widgets.VirtualLayout)constraintWidget;
                    ((VirtualLayout)view).onMeasure((androidx.constraintlayout.solver.widgets.VirtualLayout)object, n3, n4);
                } else {
                    view.measure(n3, n4);
                }
                int n8 = view.getMeasuredWidth();
                n7 = view.getMeasuredHeight();
                int n9 = view.getBaseline();
                if (n5 != 0) {
                    constraintWidget.wrapMeasure[0] = n8;
                    constraintWidget.wrapMeasure[2] = n7;
                } else {
                    constraintWidget.wrapMeasure[0] = 0;
                    constraintWidget.wrapMeasure[2] = 0;
                }
                if (n != 0) {
                    constraintWidget.wrapMeasure[1] = n7;
                    constraintWidget.wrapMeasure[3] = n8;
                } else {
                    constraintWidget.wrapMeasure[1] = 0;
                    constraintWidget.wrapMeasure[3] = 0;
                }
                n5 = constraintWidget.mMatchConstraintMinWidth > 0 ? Math.max(constraintWidget.mMatchConstraintMinWidth, n8) : n8;
                n = n5;
                if (constraintWidget.mMatchConstraintMaxWidth > 0) {
                    n = Math.min(constraintWidget.mMatchConstraintMaxWidth, n5);
                }
                n5 = constraintWidget.mMatchConstraintMinHeight > 0 ? Math.max(constraintWidget.mMatchConstraintMinHeight, n7) : n7;
                n6 = n5;
                if (constraintWidget.mMatchConstraintMaxHeight > 0) {
                    n6 = Math.min(constraintWidget.mMatchConstraintMaxHeight, n5);
                }
                if (bl2 && n2 != 0) {
                    float f = constraintWidget.mDimensionRatio;
                    n2 = (int)((float)n6 * f + 0.5f);
                    n5 = n6;
                } else {
                    n2 = n;
                    n5 = n6;
                    if (bl3) {
                        n2 = n;
                        n5 = n6;
                        if (bl) {
                            float f = constraintWidget.mDimensionRatio;
                            n5 = (int)((float)n / f + 0.5f);
                            n2 = n;
                        }
                    }
                }
                if (n8 == n2 && n7 == n5) {
                    n = n2;
                    n3 = n5;
                    n4 = n9;
                } else {
                    if (n8 != n2) {
                        n3 = View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824);
                    }
                    if (n7 != n5) {
                        n4 = View.MeasureSpec.makeMeasureSpec((int)n5, (int)1073741824);
                    }
                    view.measure(n3, n4);
                    n = view.getMeasuredWidth();
                    n3 = view.getMeasuredHeight();
                    n4 = view.getBaseline();
                }
            }
            boolean bl4 = n4 != -1;
            boolean bl5 = n != measure.horizontalDimension || n3 != measure.verticalDimension;
            measure.measuredNeedsSolverPass = bl5;
            if (((LayoutParams)object2).needsBaseline) {
                bl4 = true;
            }
            if (bl4 && n4 != -1 && constraintWidget.getBaselineDistance() != n4) {
                measure.measuredNeedsSolverPass = true;
            }
            measure.measuredWidth = n;
            measure.measuredHeight = n3;
            measure.measuredHasBaseline = bl4;
            measure.measuredBaseline = n4;
        }
    }

}

