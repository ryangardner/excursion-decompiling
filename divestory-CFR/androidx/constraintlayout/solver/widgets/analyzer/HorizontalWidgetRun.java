/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency;
import androidx.constraintlayout.solver.widgets.analyzer.RunGroup;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.List;

public class HorizontalWidgetRun
extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    private void computeInsetRatio(int[] arrn, int n, int n2, int n3, int n4, float f, int n5) {
        n = n2 - n;
        n2 = n4 - n3;
        if (n5 != -1) {
            if (n5 == 0) {
                arrn[0] = (int)((float)n2 * f + 0.5f);
                arrn[1] = n2;
                return;
            }
            if (n5 != 1) {
                return;
            }
            n2 = (int)((float)n * f + 0.5f);
            arrn[0] = n;
            arrn[1] = n2;
            return;
        }
        n3 = (int)((float)n2 * f + 0.5f);
        n4 = (int)((float)n / f + 0.5f);
        if (n3 <= n) {
            arrn[0] = n3;
            arrn[1] = n2;
            return;
        }
        if (n4 > n2) return;
        arrn[0] = n;
        arrn[1] = n4;
    }

    @Override
    void apply() {
        Object object;
        if (this.widget.measured) {
            this.dimension.resolve(this.widget.getWidth());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && ((object = this.widget.getParent()) != null && ((ConstraintWidget)object).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || ((ConstraintWidget)object).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int n = ((ConstraintWidget)object).getWidth();
                    int n2 = this.widget.mLeft.getMargin();
                    int n3 = this.widget.mRight.getMargin();
                    this.addTarget(this.start, object.horizontalRun.start, this.widget.mLeft.getMargin());
                    this.addTarget(this.end, object.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(n - n2 - n3);
                    return;
                }
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && ((object = this.widget.getParent()) != null && ((ConstraintWidget)object).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || ((ConstraintWidget)object).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            this.addTarget(this.start, object.horizontalRun.start, this.widget.mLeft.getMargin());
            this.addTarget(this.end, object.horizontalRun.end, -this.widget.mRight.getMargin());
            return;
        }
        if (this.dimension.resolved && this.widget.measured) {
            if (this.widget.mListAnchors[0].mTarget != null && this.widget.mListAnchors[1].mTarget != null) {
                if (this.widget.isInHorizontalChain()) {
                    this.start.margin = this.widget.mListAnchors[0].getMargin();
                    this.end.margin = -this.widget.mListAnchors[1].getMargin();
                    return;
                }
                object = this.getTarget(this.widget.mListAnchors[0]);
                if (object != null) {
                    this.addTarget(this.start, (DependencyNode)object, this.widget.mListAnchors[0].getMargin());
                }
                if ((object = this.getTarget(this.widget.mListAnchors[1])) != null) {
                    this.addTarget(this.end, (DependencyNode)object, -this.widget.mListAnchors[1].getMargin());
                }
                this.start.delegateToWidgetRun = true;
                this.end.delegateToWidgetRun = true;
                return;
            }
            if (this.widget.mListAnchors[0].mTarget != null) {
                object = this.getTarget(this.widget.mListAnchors[0]);
                if (object == null) return;
                this.addTarget(this.start, (DependencyNode)object, this.widget.mListAnchors[0].getMargin());
                this.addTarget(this.end, this.start, this.dimension.value);
                return;
            }
            if (this.widget.mListAnchors[1].mTarget != null) {
                object = this.getTarget(this.widget.mListAnchors[1]);
                if (object == null) return;
                this.addTarget(this.end, (DependencyNode)object, -this.widget.mListAnchors[1].getMargin());
                this.addTarget(this.start, this.end, -this.dimension.value);
                return;
            }
            if (this.widget instanceof Helper) return;
            if (this.widget.getParent() == null) return;
            if (this.widget.getAnchor((ConstraintAnchor.Type)ConstraintAnchor.Type.CENTER).mTarget != null) return;
            object = this.widget.getParent().horizontalRun.start;
            this.addTarget(this.start, (DependencyNode)object, this.widget.getX());
            this.addTarget(this.end, this.start, this.dimension.value);
            return;
        }
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int n = this.widget.mMatchConstraintDefaultWidth;
            if (n != 2) {
                if (n == 3) {
                    if (this.widget.mMatchConstraintDefaultHeight == 3) {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        this.widget.verticalRun.start.updateDelegate = this;
                        this.widget.verticalRun.end.updateDelegate = this;
                        this.dimension.updateDelegate = this;
                        if (this.widget.isInVerticalChain()) {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            this.widget.verticalRun.dimension.updateDelegate = this;
                            this.dimension.targets.add(this.widget.verticalRun.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            this.widget.verticalRun.end.dependencies.add(this.dimension);
                        } else if (this.widget.isInHorizontalChain()) {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                            this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                        } else {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                        }
                    } else {
                        object = this.widget.verticalRun.dimension;
                        this.dimension.targets.add(object);
                        ((DependencyNode)object).dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        this.end.targets.add(this.dimension);
                    }
                }
            } else {
                object = this.widget.getParent();
                if (object != null) {
                    object = object.verticalRun.dimension;
                    this.dimension.targets.add(object);
                    ((DependencyNode)object).dependencies.add(this.dimension);
                    this.dimension.delegateToWidgetRun = true;
                    this.dimension.dependencies.add(this.start);
                    this.dimension.dependencies.add(this.end);
                }
            }
        }
        if (this.widget.mListAnchors[0].mTarget != null && this.widget.mListAnchors[1].mTarget != null) {
            if (this.widget.isInHorizontalChain()) {
                this.start.margin = this.widget.mListAnchors[0].getMargin();
                this.end.margin = -this.widget.mListAnchors[1].getMargin();
                return;
            }
            DependencyNode dependencyNode = this.getTarget(this.widget.mListAnchors[0]);
            object = this.getTarget(this.widget.mListAnchors[1]);
            dependencyNode.addDependency(this);
            ((DependencyNode)object).addDependency(this);
            this.mRunType = WidgetRun.RunType.CENTER;
            return;
        }
        if (this.widget.mListAnchors[0].mTarget != null) {
            object = this.getTarget(this.widget.mListAnchors[0]);
            if (object == null) return;
            this.addTarget(this.start, (DependencyNode)object, this.widget.mListAnchors[0].getMargin());
            this.addTarget(this.end, this.start, 1, this.dimension);
            return;
        }
        if (this.widget.mListAnchors[1].mTarget != null) {
            object = this.getTarget(this.widget.mListAnchors[1]);
            if (object == null) return;
            this.addTarget(this.end, (DependencyNode)object, -this.widget.mListAnchors[1].getMargin());
            this.addTarget(this.start, this.end, -1, this.dimension);
            return;
        }
        if (this.widget instanceof Helper) return;
        if (this.widget.getParent() == null) return;
        object = this.widget.getParent().horizontalRun.start;
        this.addTarget(this.start, (DependencyNode)object, this.widget.getX());
        this.addTarget(this.end, this.start, 1, this.dimension);
    }

    @Override
    public void applyToWidget() {
        if (!this.start.resolved) return;
        this.widget.setX(this.start.value);
    }

    @Override
    void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    @Override
    void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.dimension.resolved = false;
    }

    @Override
    boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) return true;
        if (this.widget.mMatchConstraintDefaultWidth != 0) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HorizontalRun ");
        stringBuilder.append(this.widget.getDebugName());
        return stringBuilder.toString();
    }

    @Override
    public void update(Dependency object) {
        int n;
        int n2;
        DependencyNode dependencyNode;
        int n3;
        float f;
        block27 : {
            block28 : {
                block38 : {
                    block39 : {
                        int n4;
                        int n5;
                        block37 : {
                            block36 : {
                                int n6;
                                int n7;
                                int n8;
                                int n9;
                                block29 : {
                                    block33 : {
                                        block35 : {
                                            float f2;
                                            block34 : {
                                                block30 : {
                                                    block31 : {
                                                        block32 : {
                                                            n2 = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
                                                            if (n2 != 1) {
                                                                if (n2 != 2) {
                                                                    if (n2 == 3) {
                                                                        this.updateRunCenter((Dependency)object, this.widget.mLeft, this.widget.mRight, 0);
                                                                        return;
                                                                    }
                                                                } else {
                                                                    this.updateRunEnd((Dependency)object);
                                                                }
                                                            } else {
                                                                this.updateRunStart((Dependency)object);
                                                            }
                                                            if (this.dimension.resolved || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) break block27;
                                                            n2 = this.widget.mMatchConstraintDefaultWidth;
                                                            if (n2 == 2) break block28;
                                                            if (n2 != 3) break block27;
                                                            if (this.widget.mMatchConstraintDefaultHeight == 0 || this.widget.mMatchConstraintDefaultHeight == 3) break block29;
                                                            n2 = this.widget.getDimensionRatioSide();
                                                            if (n2 == -1) break block30;
                                                            if (n2 == 0) break block31;
                                                            if (n2 == 1) break block32;
                                                            n2 = 0;
                                                            break block33;
                                                        }
                                                        f = this.widget.verticalRun.dimension.value;
                                                        f2 = this.widget.getDimensionRatio();
                                                        break block34;
                                                    }
                                                    f = (float)this.widget.verticalRun.dimension.value / this.widget.getDimensionRatio();
                                                    break block35;
                                                }
                                                f = this.widget.verticalRun.dimension.value;
                                                f2 = this.widget.getDimensionRatio();
                                            }
                                            f *= f2;
                                        }
                                        n2 = (int)(f + 0.5f);
                                    }
                                    this.dimension.resolve(n2);
                                    break block27;
                                }
                                dependencyNode = this.widget.verticalRun.start;
                                object = this.widget.verticalRun.end;
                                n2 = this.widget.mLeft.mTarget != null ? 1 : 0;
                                n3 = this.widget.mTop.mTarget != null ? 1 : 0;
                                n = this.widget.mRight.mTarget != null ? 1 : 0;
                                n5 = this.widget.mBottom.mTarget != null ? 1 : 0;
                                n4 = this.widget.getDimensionRatioSide();
                                if (n2 == 0 || n3 == 0 || n == 0 || n5 == 0) break block36;
                                f = this.widget.getDimensionRatio();
                                if (dependencyNode.resolved && ((DependencyNode)object).resolved) {
                                    if (!this.start.readyToSolve) return;
                                    if (!this.end.readyToSolve) {
                                        return;
                                    }
                                    int n10 = this.start.targets.get((int)0).value;
                                    n5 = this.start.margin;
                                    n = this.end.targets.get((int)0).value;
                                    int n11 = this.end.margin;
                                    int n12 = dependencyNode.value;
                                    n2 = dependencyNode.margin;
                                    n3 = ((DependencyNode)object).value;
                                    int n13 = ((DependencyNode)object).margin;
                                    this.computeInsetRatio(tempDimensions, n10 + n5, n - n11, n12 + n2, n3 - n13, f, n4);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    return;
                                }
                                if (this.start.resolved && this.end.resolved) {
                                    if (!dependencyNode.readyToSolve) return;
                                    if (!((DependencyNode)object).readyToSolve) {
                                        return;
                                    }
                                    n3 = this.start.value;
                                    n7 = this.start.margin;
                                    n5 = this.end.value;
                                    n6 = this.end.margin;
                                    n8 = dependencyNode.targets.get((int)0).value;
                                    n2 = dependencyNode.margin;
                                    n9 = object.targets.get((int)0).value;
                                    n = ((DependencyNode)object).margin;
                                    this.computeInsetRatio(tempDimensions, n3 + n7, n5 - n6, n8 + n2, n9 - n, f, n4);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                }
                                if (!this.start.readyToSolve) return;
                                if (!this.end.readyToSolve) return;
                                if (!dependencyNode.readyToSolve) return;
                                if (!((DependencyNode)object).readyToSolve) {
                                    return;
                                }
                                n6 = this.start.targets.get((int)0).value;
                                n9 = this.start.margin;
                                n = this.end.targets.get((int)0).value;
                                n3 = this.end.margin;
                                n5 = dependencyNode.targets.get((int)0).value;
                                n2 = dependencyNode.margin;
                                n7 = object.targets.get((int)0).value;
                                n8 = ((DependencyNode)object).margin;
                                this.computeInsetRatio(tempDimensions, n6 + n9, n - n3, n5 + n2, n7 - n8, f, n4);
                                this.dimension.resolve(tempDimensions[0]);
                                this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                break block27;
                            }
                            if (n2 == 0 || n == 0) break block37;
                            if (!this.start.readyToSolve) return;
                            if (!this.end.readyToSolve) {
                                return;
                            }
                            f = this.widget.getDimensionRatio();
                            n2 = this.start.targets.get((int)0).value + this.start.margin;
                            n3 = this.end.targets.get((int)0).value - this.end.margin;
                            if (n4 != -1 && n4 != 0) {
                                if (n4 == 1) {
                                    n2 = this.getLimitedDimension(n3 - n2, 0);
                                    n = (int)((float)n2 / f + 0.5f);
                                    n3 = this.getLimitedDimension(n, 1);
                                    if (n != n3) {
                                        n2 = (int)((float)n3 * f + 0.5f);
                                    }
                                    this.dimension.resolve(n2);
                                    this.widget.verticalRun.dimension.resolve(n3);
                                }
                            } else {
                                n2 = this.getLimitedDimension(n3 - n2, 0);
                                n = (int)((float)n2 * f + 0.5f);
                                n3 = this.getLimitedDimension(n, 1);
                                if (n != n3) {
                                    n2 = (int)((float)n3 / f + 0.5f);
                                }
                                this.dimension.resolve(n2);
                                this.widget.verticalRun.dimension.resolve(n3);
                            }
                            break block27;
                        }
                        if (n3 == 0 || n5 == 0) break block27;
                        if (!dependencyNode.readyToSolve) return;
                        if (!((DependencyNode)object).readyToSolve) {
                            return;
                        }
                        f = this.widget.getDimensionRatio();
                        n3 = dependencyNode.targets.get((int)0).value + dependencyNode.margin;
                        n2 = object.targets.get((int)0).value - ((DependencyNode)object).margin;
                        if (n4 == -1) break block38;
                        if (n4 == 0) break block39;
                        if (n4 == 1) break block38;
                        break block27;
                    }
                    n2 = this.getLimitedDimension(n2 - n3, 1);
                    n = (int)((float)n2 * f + 0.5f);
                    n3 = this.getLimitedDimension(n, 0);
                    if (n != n3) {
                        n2 = (int)((float)n3 / f + 0.5f);
                    }
                    this.dimension.resolve(n3);
                    this.widget.verticalRun.dimension.resolve(n2);
                    break block27;
                }
                n2 = this.getLimitedDimension(n2 - n3, 1);
                n = (int)((float)n2 / f + 0.5f);
                n3 = this.getLimitedDimension(n, 0);
                if (n != n3) {
                    n2 = (int)((float)n3 * f + 0.5f);
                }
                this.dimension.resolve(n3);
                this.widget.verticalRun.dimension.resolve(n2);
                break block27;
            }
            object = this.widget.getParent();
            if (object != null && object.horizontalRun.dimension.resolved) {
                f = this.widget.mMatchConstraintPercentWidth;
                n2 = (int)((float)object.horizontalRun.dimension.value * f + 0.5f);
                this.dimension.resolve(n2);
            }
        }
        if (!this.start.readyToSolve) return;
        if (!this.end.readyToSolve) {
            return;
        }
        if (this.start.resolved && this.end.resolved && this.dimension.resolved) {
            return;
        }
        if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.mMatchConstraintDefaultWidth == 0 && !this.widget.isInHorizontalChain()) {
            object = this.start.targets.get(0);
            dependencyNode = this.end.targets.get(0);
            n3 = ((DependencyNode)object).value + this.start.margin;
            n2 = dependencyNode.value + this.end.margin;
            this.start.resolve(n3);
            this.end.resolve(n2);
            this.dimension.resolve(n2 - n3);
            return;
        }
        if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
            object = this.start.targets.get(0);
            dependencyNode = this.end.targets.get(0);
            n2 = ((DependencyNode)object).value;
            n3 = this.start.margin;
            n2 = Math.min(dependencyNode.value + this.end.margin - (n2 + n3), this.dimension.wrapValue);
            n = this.widget.mMatchConstraintMaxWidth;
            n2 = n3 = Math.max(this.widget.mMatchConstraintMinWidth, n2);
            if (n > 0) {
                n2 = Math.min(n, n3);
            }
            this.dimension.resolve(n2);
        }
        if (!this.dimension.resolved) {
            return;
        }
        dependencyNode = this.start.targets.get(0);
        object = this.end.targets.get(0);
        n2 = dependencyNode.value + this.start.margin;
        n3 = ((DependencyNode)object).value + this.end.margin;
        f = this.widget.getHorizontalBiasPercent();
        if (dependencyNode == object) {
            n2 = dependencyNode.value;
            n3 = ((DependencyNode)object).value;
            f = 0.5f;
        }
        n = this.dimension.value;
        this.start.resolve((int)((float)n2 + 0.5f + (float)(n3 - n2 - n) * f));
        this.end.resolve(this.start.value + this.dimension.value);
    }

}

