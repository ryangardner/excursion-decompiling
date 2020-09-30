/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.RunGroup;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.Iterator;
import java.util.List;

class HelperReferences
extends WidgetRun {
    public HelperReferences(ConstraintWidget constraintWidget) {
        super(constraintWidget);
    }

    private void addDependency(DependencyNode dependencyNode) {
        this.start.dependencies.add(dependencyNode);
        dependencyNode.targets.add(this.start);
    }

    @Override
    void apply() {
        if (!(this.widget instanceof Barrier)) return;
        this.start.delegateToWidgetRun = true;
        Barrier barrier = (Barrier)this.widget;
        int n = barrier.getBarrierType();
        boolean bl = barrier.allowsGoneWidget();
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return;
                    }
                    this.start.type = DependencyNode.Type.BOTTOM;
                    do {
                        if (n5 >= barrier.mWidgetsCount) {
                            this.addDependency(this.widget.verticalRun.start);
                            this.addDependency(this.widget.verticalRun.end);
                            return;
                        }
                        Object object = barrier.mWidgets[n5];
                        if (bl || ((ConstraintWidget)object).getVisibility() != 8) {
                            object = object.verticalRun.end;
                            ((DependencyNode)object).dependencies.add(this.start);
                            this.start.targets.add((DependencyNode)object);
                        }
                        ++n5;
                    } while (true);
                }
                this.start.type = DependencyNode.Type.TOP;
                n5 = n2;
                do {
                    if (n5 >= barrier.mWidgetsCount) {
                        this.addDependency(this.widget.verticalRun.start);
                        this.addDependency(this.widget.verticalRun.end);
                        return;
                    }
                    Object object = barrier.mWidgets[n5];
                    if (bl || ((ConstraintWidget)object).getVisibility() != 8) {
                        object = object.verticalRun.start;
                        ((DependencyNode)object).dependencies.add(this.start);
                        this.start.targets.add((DependencyNode)object);
                    }
                    ++n5;
                } while (true);
            }
            this.start.type = DependencyNode.Type.RIGHT;
            n5 = n3;
            do {
                if (n5 >= barrier.mWidgetsCount) {
                    this.addDependency(this.widget.horizontalRun.start);
                    this.addDependency(this.widget.horizontalRun.end);
                    return;
                }
                Object object = barrier.mWidgets[n5];
                if (bl || ((ConstraintWidget)object).getVisibility() != 8) {
                    object = object.horizontalRun.end;
                    ((DependencyNode)object).dependencies.add(this.start);
                    this.start.targets.add((DependencyNode)object);
                }
                ++n5;
            } while (true);
        }
        this.start.type = DependencyNode.Type.LEFT;
        n5 = n4;
        do {
            if (n5 >= barrier.mWidgetsCount) {
                this.addDependency(this.widget.horizontalRun.start);
                this.addDependency(this.widget.horizontalRun.end);
                return;
            }
            Object object = barrier.mWidgets[n5];
            if (bl || ((ConstraintWidget)object).getVisibility() != 8) {
                object = object.horizontalRun.start;
                ((DependencyNode)object).dependencies.add(this.start);
                this.start.targets.add((DependencyNode)object);
            }
            ++n5;
        } while (true);
    }

    @Override
    public void applyToWidget() {
        if (!(this.widget instanceof Barrier)) return;
        int n = ((Barrier)this.widget).getBarrierType();
        if (n != 0 && n != 1) {
            this.widget.setY(this.start.value);
            return;
        }
        this.widget.setX(this.start.value);
    }

    @Override
    void clear() {
        this.runGroup = null;
        this.start.clear();
    }

    @Override
    void reset() {
        this.start.resolved = false;
    }

    @Override
    boolean supportsWrapComputation() {
        return false;
    }

    @Override
    public void update(Dependency object) {
        object = (Barrier)this.widget;
        int n = ((Barrier)object).getBarrierType();
        Iterator<DependencyNode> iterator2 = this.start.targets.iterator();
        int n2 = 0;
        int n3 = -1;
        while (iterator2.hasNext()) {
            int n4;
            int n5;
            block6 : {
                block5 : {
                    n5 = iterator2.next().value;
                    if (n3 == -1) break block5;
                    n4 = n3;
                    if (n5 >= n3) break block6;
                }
                n4 = n5;
            }
            n3 = n4;
            if (n2 >= n5) continue;
            n2 = n5;
            n3 = n4;
        }
        if (n != 0 && n != 2) {
            this.start.resolve(n2 + ((Barrier)object).getMargin());
            return;
        }
        this.start.resolve(n3 + ((Barrier)object).getMargin());
    }
}

