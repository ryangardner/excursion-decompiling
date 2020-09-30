/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.RunGroup;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import java.util.List;

public abstract class WidgetRun
implements Dependency {
    DimensionDependency dimension = new DimensionDependency(this);
    protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public DependencyNode end = new DependencyNode(this);
    protected RunType mRunType = RunType.NONE;
    public int matchConstraintsType;
    public int orientation = 0;
    boolean resolved = false;
    RunGroup runGroup;
    public DependencyNode start = new DependencyNode(this);
    ConstraintWidget widget;

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.widget = constraintWidget;
    }

    private void resolveDimension(int n, int n2) {
        int n3 = this.matchConstraintsType;
        if (n3 == 0) {
            this.dimension.resolve(this.getLimitedDimension(n2, n));
            return;
        }
        if (n3 == 1) {
            n = this.getLimitedDimension(this.dimension.wrapValue, n);
            this.dimension.resolve(Math.min(n, n2));
            return;
        }
        if (n3 != 2) {
            if (n3 != 3) {
                return;
            }
            if (this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.horizontalRun.matchConstraintsType == 3 && this.widget.verticalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.verticalRun.matchConstraintsType == 3) {
                return;
            }
            Object object = this.widget;
            object = n == 0 ? ((ConstraintWidget)object).verticalRun : ((ConstraintWidget)object).horizontalRun;
            if (!object.dimension.resolved) return;
            float f = this.widget.getDimensionRatio();
            n = n == 1 ? (int)((float)object.dimension.value / f + 0.5f) : (int)(f * (float)object.dimension.value + 0.5f);
            this.dimension.resolve(n);
            return;
        }
        Object object = this.widget.getParent();
        if (object == null) return;
        object = n == 0 ? ((ConstraintWidget)object).horizontalRun : ((ConstraintWidget)object).verticalRun;
        if (!object.dimension.resolved) return;
        ConstraintWidget constraintWidget = this.widget;
        float f = n == 0 ? constraintWidget.mMatchConstraintPercentWidth : constraintWidget.mMatchConstraintPercentHeight;
        n2 = (int)((float)object.dimension.value * f + 0.5f);
        this.dimension.resolve(this.getLimitedDimension(n2, n));
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int n) {
        dependencyNode.targets.add(dependencyNode2);
        dependencyNode.margin = n;
        dependencyNode2.dependencies.add(dependencyNode);
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int n, DimensionDependency dimensionDependency) {
        dependencyNode.targets.add(dependencyNode2);
        dependencyNode.targets.add(this.dimension);
        dependencyNode.marginFactor = n;
        dependencyNode.marginDependency = dimensionDependency;
        dependencyNode2.dependencies.add(dependencyNode);
        dimensionDependency.dependencies.add(dependencyNode);
    }

    abstract void apply();

    abstract void applyToWidget();

    abstract void clear();

    protected final int getLimitedDimension(int n, int n2) {
        int n3;
        if (n2 == 0) {
            n3 = this.widget.mMatchConstraintMaxWidth;
            n2 = Math.max(this.widget.mMatchConstraintMinWidth, n);
            if (n3 > 0) {
                n2 = Math.min(n3, n);
            }
            n3 = n;
            if (n2 == n) return n3;
            return n2;
        } else {
            n3 = this.widget.mMatchConstraintMaxHeight;
            n2 = Math.max(this.widget.mMatchConstraintMinHeight, n);
            if (n3 > 0) {
                n2 = Math.min(n3, n);
            }
            n3 = n;
            if (n2 == n) return n3;
        }
        return n2;
    }

    protected final DependencyNode getTarget(ConstraintAnchor object) {
        Object object2 = object.mTarget;
        Object var3_3 = null;
        if (object2 == null) {
            return null;
        }
        object2 = object.mTarget.mOwner;
        object = object.mTarget.mType;
        int n = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[((Enum)object).ordinal()];
        if (n == 1) {
            return object2.horizontalRun.start;
        }
        if (n == 2) {
            return object2.horizontalRun.end;
        }
        if (n == 3) {
            return object2.verticalRun.start;
        }
        if (n == 4) {
            return object2.verticalRun.baseline;
        }
        if (n == 5) return object2.verticalRun.end;
        return var3_3;
    }

    protected final DependencyNode getTarget(ConstraintAnchor object, int n) {
        Object object2 = ((ConstraintAnchor)object).mTarget;
        Object var4_4 = null;
        if (object2 == null) {
            return null;
        }
        object2 = object.mTarget.mOwner;
        object2 = n == 0 ? ((ConstraintWidget)object2).horizontalRun : ((ConstraintWidget)object2).verticalRun;
        object = object.mTarget.mType;
        n = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[((Enum)object).ordinal()];
        if (n == 1) return ((WidgetRun)object2).start;
        if (n == 2) return ((WidgetRun)object2).end;
        if (n == 3) return ((WidgetRun)object2).start;
        if (n == 5) return ((WidgetRun)object2).end;
        return var4_4;
    }

    public long getWrapDimension() {
        if (!this.dimension.resolved) return 0L;
        return this.dimension.value;
    }

    public boolean isCenterConnection() {
        int n;
        int n2;
        int n3 = this.start.targets.size();
        boolean bl = false;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            n = n4;
            if (this.start.targets.get((int)n2).run != this) {
                n = n4 + 1;
            }
            n4 = n;
        }
        n3 = this.end.targets.size();
        n = 0;
        do {
            if (n >= n3) {
                if (n4 < 2) return bl;
                return true;
            }
            n2 = n4;
            if (this.end.targets.get((int)n).run != this) {
                n2 = n4 + 1;
            }
            ++n;
            n4 = n2;
        } while (true);
    }

    public boolean isDimensionResolved() {
        return this.dimension.resolved;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    abstract void reset();

    abstract boolean supportsWrapComputation();

    @Override
    public void update(Dependency dependency) {
    }

    protected void updateRunCenter(Dependency dependency, ConstraintAnchor object, ConstraintAnchor constraintAnchor, int n) {
        DependencyNode dependencyNode = this.getTarget((ConstraintAnchor)object);
        dependency = this.getTarget(constraintAnchor);
        if (!dependencyNode.resolved) return;
        if (!((DependencyNode)dependency).resolved) {
            return;
        }
        int n2 = dependencyNode.value + ((ConstraintAnchor)object).getMargin();
        int n3 = ((DependencyNode)dependency).value - constraintAnchor.getMargin();
        int n4 = n3 - n2;
        if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            this.resolveDimension(n, n4);
        }
        if (!this.dimension.resolved) {
            return;
        }
        if (this.dimension.value == n4) {
            this.start.resolve(n2);
            this.end.resolve(n3);
            return;
        }
        object = this.widget;
        float f = n == 0 ? ((ConstraintWidget)object).getHorizontalBiasPercent() : ((ConstraintWidget)object).getVerticalBiasPercent();
        n = n3;
        if (dependencyNode == dependency) {
            n2 = dependencyNode.value;
            n = ((DependencyNode)dependency).value;
            f = 0.5f;
        }
        n3 = this.dimension.value;
        this.start.resolve((int)((float)n2 + 0.5f + (float)(n - n2 - n3) * f));
        this.end.resolve(this.start.value + this.dimension.value);
    }

    protected void updateRunEnd(Dependency dependency) {
    }

    protected void updateRunStart(Dependency dependency) {
    }

    public long wrapSize(int n) {
        if (!this.dimension.resolved) return 0L;
        long l = this.dimension.value;
        if (this.isCenterConnection()) {
            n = this.start.margin - this.end.margin;
            return l += (long)n;
        } else {
            if (n != 0) {
                l -= (long)this.end.margin;
                return l;
            }
            n = this.start.margin;
        }
        return l += (long)n;
    }

    static final class RunType
    extends Enum<RunType> {
        private static final /* synthetic */ RunType[] $VALUES;
        public static final /* enum */ RunType CENTER;
        public static final /* enum */ RunType END;
        public static final /* enum */ RunType NONE;
        public static final /* enum */ RunType START;

        static {
            RunType runType;
            NONE = new RunType();
            START = new RunType();
            END = new RunType();
            CENTER = runType = new RunType();
            $VALUES = new RunType[]{NONE, START, END, runType};
        }

        public static RunType valueOf(String string2) {
            return Enum.valueOf(RunType.class, string2);
        }

        public static RunType[] values() {
            return (RunType[])$VALUES.clone();
        }
    }

}

