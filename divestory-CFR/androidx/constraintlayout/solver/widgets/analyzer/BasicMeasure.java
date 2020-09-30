/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import java.util.ArrayList;

public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    private static final boolean DEBUG = false;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    private static final int MODE_SHIFT = 30;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private ConstraintWidgetContainer constraintWidgetContainer;
    private Measure mMeasure = new Measure();
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList();

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer) {
        this.constraintWidgetContainer = constraintWidgetContainer;
    }

    private boolean measure(Measurer measurer, ConstraintWidget constraintWidget, boolean bl) {
        this.mMeasure.horizontalBehavior = constraintWidget.getHorizontalDimensionBehaviour();
        this.mMeasure.verticalBehavior = constraintWidget.getVerticalDimensionBehaviour();
        this.mMeasure.horizontalDimension = constraintWidget.getWidth();
        this.mMeasure.verticalDimension = constraintWidget.getHeight();
        this.mMeasure.measuredNeedsSolverPass = false;
        this.mMeasure.useCurrentDimensions = bl;
        boolean bl2 = this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean bl3 = this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        bl2 = bl2 && constraintWidget.mDimensionRatio > 0.0f;
        bl3 = bl3 && constraintWidget.mDimensionRatio > 0.0f;
        if (bl2 && constraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
            this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (bl3 && constraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
            this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(constraintWidget, this.mMeasure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
        this.mMeasure.useCurrentDimensions = false;
        return this.mMeasure.measuredNeedsSolverPass;
    }

    private void measureChildren(ConstraintWidgetContainer constraintWidgetContainer) {
        int n = constraintWidgetContainer.mChildren.size();
        Measurer measurer = constraintWidgetContainer.getMeasurer();
        int n2 = 0;
        do {
            if (n2 >= n) {
                measurer.didMeasures();
                return;
            }
            ConstraintWidget constraintWidget = (ConstraintWidget)constraintWidgetContainer.mChildren.get(n2);
            if (!(constraintWidget instanceof Guideline || constraintWidget.horizontalRun.dimension.resolved && constraintWidget.verticalRun.dimension.resolved)) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(0);
                boolean bl = true;
                Object object = constraintWidget.getDimensionBehaviour(1);
                if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultWidth == 1 || object != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultHeight == 1) {
                    bl = false;
                }
                if (!bl) {
                    this.measure(measurer, constraintWidget, false);
                    if (constraintWidgetContainer.mMetrics != null) {
                        object = constraintWidgetContainer.mMetrics;
                        ++((Metrics)object).measuredWidgets;
                    }
                }
            }
            ++n2;
        } while (true);
    }

    private void solveLinearSystem(ConstraintWidgetContainer constraintWidgetContainer, String string2, int n, int n2) {
        int n3 = constraintWidgetContainer.getMinWidth();
        int n4 = constraintWidgetContainer.getMinHeight();
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setWidth(n);
        constraintWidgetContainer.setHeight(n2);
        constraintWidgetContainer.setMinWidth(n3);
        constraintWidgetContainer.setMinHeight(n4);
        this.constraintWidgetContainer.layout();
    }

    public long solverMeasure(ConstraintWidgetContainer constraintWidgetContainer, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        Object object;
        Measurer measurer = constraintWidgetContainer.getMeasurer();
        n9 = constraintWidgetContainer.mChildren.size();
        int n10 = constraintWidgetContainer.getWidth();
        int n11 = constraintWidgetContainer.getHeight();
        boolean bl = Optimizer.enabled(n, 128);
        n = !bl && !Optimizer.enabled(n, 64) ? 0 : 1;
        n3 = n;
        if (n != 0) {
            n2 = 0;
            do {
                n3 = n;
                if (n2 >= n9) break;
                object = (ConstraintWidget)constraintWidgetContainer.mChildren.get(n2);
                n3 = ((ConstraintWidget)object).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0;
                n8 = ((ConstraintWidget)object).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0;
                n3 = n3 != 0 && n8 != 0 && ((ConstraintWidget)object).getDimensionRatio() > 0.0f ? 1 : 0;
                if (((ConstraintWidget)object).isInHorizontalChain() && n3 != 0 || ((ConstraintWidget)object).isInVerticalChain() && n3 != 0 || object instanceof VirtualLayout || ((ConstraintWidget)object).isInHorizontalChain() || ((ConstraintWidget)object).isInVerticalChain()) {
                    n3 = 0;
                    break;
                }
                ++n2;
            } while (true);
        }
        if (n3 != 0 && LinearSystem.sMetrics != null) {
            object = LinearSystem.sMetrics;
            ++((Metrics)object).measures;
        }
        if ((n3 & (n = n4 == 1073741824 && n6 == 1073741824 || bl ? 1 : 0)) != 0) {
            boolean bl2;
            n = Math.min(constraintWidgetContainer.getMaxWidth(), n5);
            n2 = Math.min(constraintWidgetContainer.getMaxHeight(), n7);
            if (n4 == 1073741824 && constraintWidgetContainer.getWidth() != n) {
                constraintWidgetContainer.setWidth(n);
                constraintWidgetContainer.invalidateGraph();
            }
            if (n6 == 1073741824 && constraintWidgetContainer.getHeight() != n2) {
                constraintWidgetContainer.setHeight(n2);
                constraintWidgetContainer.invalidateGraph();
            }
            if (n4 == 1073741824 && n6 == 1073741824) {
                bl2 = constraintWidgetContainer.directMeasure(bl);
                n = 2;
            } else {
                bl2 = constraintWidgetContainer.directMeasureSetup(bl);
                if (n4 == 1073741824) {
                    bl2 &= constraintWidgetContainer.directMeasureWithOrientation(bl, 0);
                    n = 1;
                } else {
                    n = 0;
                }
                if (n6 == 1073741824) {
                    bl = constraintWidgetContainer.directMeasureWithOrientation(bl, 1);
                    ++n;
                    bl2 = bl & bl2;
                }
            }
            bl = bl2;
            n2 = n;
            if (bl2) {
                bl = n4 == 1073741824;
                boolean bl3 = n6 == 1073741824;
                constraintWidgetContainer.updateFromRuns(bl, bl3);
                bl = bl2;
                n2 = n;
            }
        } else {
            bl = false;
            n2 = 0;
        }
        if (bl) {
            if (n2 == 2) return 0L;
        }
        if (n9 > 0) {
            this.measureChildren(constraintWidgetContainer);
        }
        n3 = constraintWidgetContainer.getOptimizationLevel();
        int n12 = this.mVariableDimensionsWidgets.size();
        if (n9 > 0) {
            this.solveLinearSystem(constraintWidgetContainer, "First pass", n10, n11);
        }
        n = n3;
        if (n12 > 0) {
            int n13;
            Metrics metrics;
            int n14;
            int n15;
            n8 = constraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
            n9 = constraintWidgetContainer.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
            n2 = Math.max(constraintWidgetContainer.getWidth(), this.constraintWidgetContainer.getMinWidth());
            n = Math.max(constraintWidgetContainer.getHeight(), this.constraintWidgetContainer.getMinHeight());
            n6 = 0;
            n4 = n11;
            n5 = n10;
            for (n13 = 0; n13 < n12; ++n13) {
                object = this.mVariableDimensionsWidgets.get(n13);
                if (!(object instanceof VirtualLayout)) {
                    n7 = n6;
                } else {
                    n15 = ((ConstraintWidget)object).getWidth();
                    n10 = ((ConstraintWidget)object).getHeight();
                    n7 = n6 | this.measure(measurer, (ConstraintWidget)object, true);
                    if (constraintWidgetContainer.mMetrics != null) {
                        metrics = constraintWidgetContainer.mMetrics;
                        ++metrics.measuredMatchWidgets;
                    }
                    n14 = ((ConstraintWidget)object).getWidth();
                    n11 = ((ConstraintWidget)object).getHeight();
                    n6 = n2;
                    if (n14 != n15) {
                        ((ConstraintWidget)object).setWidth(n14);
                        n6 = n2;
                        if (n8 != 0) {
                            n6 = n2;
                            if (((ConstraintWidget)object).getRight() > n2) {
                                n6 = Math.max(n2, ((ConstraintWidget)object).getRight() + ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                            }
                        }
                        n7 = 1;
                    }
                    n2 = n;
                    if (n11 != n10) {
                        ((ConstraintWidget)object).setHeight(n11);
                        n2 = n;
                        if (n9 != 0) {
                            n2 = n;
                            if (((ConstraintWidget)object).getBottom() > n) {
                                n2 = Math.max(n, ((ConstraintWidget)object).getBottom() + ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                            }
                        }
                        n7 = 1;
                    }
                    n7 |= ((VirtualLayout)object).needSolverPass();
                    n = n2;
                    n2 = n6;
                }
                n6 = n7;
            }
            n11 = 0;
            n13 = n4;
            n10 = n5;
            n5 = n6;
            n7 = n;
            n4 = n12;
            n6 = n11;
            do {
                if (n6 < 2) {
                    n = n7;
                    n11 = n6;
                } else {
                    if (n5 != 0) {
                        this.solveLinearSystem(constraintWidgetContainer, "2nd pass", n10, n13);
                        if (constraintWidgetContainer.getWidth() < n2) {
                            constraintWidgetContainer.setWidth(n2);
                            n = 1;
                        } else {
                            n = 0;
                        }
                        if (constraintWidgetContainer.getHeight() < n7) {
                            constraintWidgetContainer.setHeight(n7);
                            n = 1;
                        }
                        if (n != 0) {
                            this.solveLinearSystem(constraintWidgetContainer, "3rd pass", n10, n13);
                        }
                    }
                    n = n3;
                    break;
                }
                for (n12 = 0; n12 < n4; ++n12) {
                    object = this.mVariableDimensionsWidgets.get(n12);
                    if (object instanceof Helper && !(object instanceof VirtualLayout) || object instanceof Guideline || ((ConstraintWidget)object).getVisibility() == 8 || object.horizontalRun.dimension.resolved && object.verticalRun.dimension.resolved || object instanceof VirtualLayout) {
                        n15 = n2;
                        n6 = n5;
                    } else {
                        n7 = ((ConstraintWidget)object).getWidth();
                        n15 = ((ConstraintWidget)object).getHeight();
                        n14 = ((ConstraintWidget)object).getBaselineDistance();
                        n6 = n5 | this.measure(measurer, (ConstraintWidget)object, true);
                        if (constraintWidgetContainer.mMetrics != null) {
                            metrics = constraintWidgetContainer.mMetrics;
                            ++metrics.measuredMatchWidgets;
                        }
                        int n16 = ((ConstraintWidget)object).getWidth();
                        int n17 = ((ConstraintWidget)object).getHeight();
                        n5 = n2;
                        if (n16 != n7) {
                            ((ConstraintWidget)object).setWidth(n16);
                            n5 = n2;
                            if (n8 != 0) {
                                n5 = n2;
                                if (((ConstraintWidget)object).getRight() > n2) {
                                    n5 = Math.max(n2, ((ConstraintWidget)object).getRight() + ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                                }
                            }
                            n6 = 1;
                        }
                        n2 = n;
                        n7 = n6;
                        if (n17 != n15) {
                            ((ConstraintWidget)object).setHeight(n17);
                            n2 = n;
                            if (n9 != 0) {
                                n2 = n;
                                if (((ConstraintWidget)object).getBottom() > n) {
                                    n2 = Math.max(n, ((ConstraintWidget)object).getBottom() + ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                                }
                            }
                            n7 = 1;
                        }
                        n15 = n5;
                        n = n2;
                        n6 = n7;
                        if (((ConstraintWidget)object).hasBaseline()) {
                            n15 = n5;
                            n = n2;
                            n6 = n7;
                            if (n14 != ((ConstraintWidget)object).getBaselineDistance()) {
                                n6 = 1;
                                n = n2;
                                n15 = n5;
                            }
                        }
                    }
                    n2 = n15;
                    n5 = n6;
                }
                if (n5 != 0) {
                    this.solveLinearSystem(constraintWidgetContainer, "intermediate pass", n10, n13);
                    n5 = 0;
                }
                n6 = n11 + 1;
                n7 = n;
            } while (true);
        }
        constraintWidgetContainer.setOptimizationLevel(n);
        return 0L;
    }

    public void updateHierarchy(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mVariableDimensionsWidgets.clear();
        int n = constraintWidgetContainer.mChildren.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                constraintWidgetContainer.invalidateGraph();
                return;
            }
            ConstraintWidget constraintWidget = (ConstraintWidget)constraintWidgetContainer.mChildren.get(n2);
            if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                this.mVariableDimensionsWidgets.add(constraintWidget);
            }
            ++n2;
        } while (true);
    }

    public static class Measure {
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public boolean useCurrentDimensions;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    public static final class MeasureType
    extends Enum<MeasureType> {
        private static final /* synthetic */ MeasureType[] $VALUES;

        static {
            $VALUES = new MeasureType[0];
        }

        public static MeasureType valueOf(String string2) {
            return Enum.valueOf(MeasureType.class, string2);
        }

        public static MeasureType[] values() {
            return (MeasureType[])$VALUES.clone();
        }
    }

    public static interface Measurer {
        public void didMeasures();

        public void measure(ConstraintWidget var1, Measure var2);
    }

}

