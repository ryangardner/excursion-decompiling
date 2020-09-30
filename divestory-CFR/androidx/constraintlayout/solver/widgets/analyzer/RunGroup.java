/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.HelperReferences;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    int direction;
    public boolean dual = false;
    WidgetRun firstRun = null;
    int groupIndex = 0;
    WidgetRun lastRun = null;
    public int position = 0;
    ArrayList<WidgetRun> runs = new ArrayList();

    public RunGroup(WidgetRun widgetRun, int n) {
        int n2;
        this.groupIndex = n2 = index;
        index = n2 + 1;
        this.firstRun = widgetRun;
        this.lastRun = widgetRun;
        this.direction = n;
    }

    private boolean defineTerminalWidget(WidgetRun widgetRun, int n) {
        Object object;
        if (!widgetRun.widget.isTerminalWidget[n]) {
            return false;
        }
        for (Dependency dependency : widgetRun.start.dependencies) {
            if (!(dependency instanceof DependencyNode)) continue;
            DependencyNode dependencyNode = (DependencyNode)dependency;
            if (dependencyNode.run == widgetRun || dependencyNode != dependencyNode.run.start) continue;
            if (widgetRun instanceof ChainRun) {
                object = ((ChainRun)widgetRun).widgets.iterator();
                while (object.hasNext()) {
                    this.defineTerminalWidget((WidgetRun)object.next(), n);
                }
            } else if (!(widgetRun instanceof HelperReferences)) {
                widgetRun.widget.isTerminalWidget[n] = false;
            }
            this.defineTerminalWidget(dependencyNode.run, n);
        }
        Iterator<Dependency> iterator2 = widgetRun.end.dependencies.iterator();
        while (iterator2.hasNext()) {
            Dependency dependency = iterator2.next();
            if (!(dependency instanceof DependencyNode)) continue;
            object = (DependencyNode)dependency;
            if (((DependencyNode)object).run == widgetRun || object != object.run.start) continue;
            if (widgetRun instanceof ChainRun) {
                Iterator<WidgetRun> iterator3 = ((ChainRun)widgetRun).widgets.iterator();
                while (iterator3.hasNext()) {
                    this.defineTerminalWidget(iterator3.next(), n);
                }
            } else if (!(widgetRun instanceof HelperReferences)) {
                widgetRun.widget.isTerminalWidget[n] = false;
            }
            this.defineTerminalWidget(((DependencyNode)object).run, n);
        }
        return false;
    }

    private long traverseEnd(DependencyNode dependencyNode, long l) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return l;
        }
        int n = dependencyNode.dependencies.size();
        int n2 = 0;
        long l2 = l;
        do {
            long l3;
            if (n2 >= n) {
                l3 = l2;
                if (dependencyNode != widgetRun.end) return l3;
                l3 = widgetRun.getWrapDimension();
                dependencyNode = widgetRun.start;
                l -= l3;
                return Math.min(Math.min(l2, this.traverseEnd(dependencyNode, l)), l - (long)widgetRun.start.margin);
            }
            Dependency dependency = dependencyNode.dependencies.get(n2);
            l3 = l2;
            if (dependency instanceof DependencyNode) {
                dependency = (DependencyNode)dependency;
                l3 = ((DependencyNode)dependency).run == widgetRun ? l2 : Math.min(l2, this.traverseEnd((DependencyNode)dependency, (long)((DependencyNode)dependency).margin + l));
            }
            ++n2;
            l2 = l3;
        } while (true);
    }

    private long traverseStart(DependencyNode dependencyNode, long l) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return l;
        }
        int n = dependencyNode.dependencies.size();
        int n2 = 0;
        long l2 = l;
        do {
            long l3;
            if (n2 >= n) {
                l3 = l2;
                if (dependencyNode != widgetRun.start) return l3;
                l3 = widgetRun.getWrapDimension();
                dependencyNode = widgetRun.end;
                l += l3;
                return Math.max(Math.max(l2, this.traverseStart(dependencyNode, l)), l - (long)widgetRun.end.margin);
            }
            Dependency dependency = dependencyNode.dependencies.get(n2);
            l3 = l2;
            if (dependency instanceof DependencyNode) {
                dependency = (DependencyNode)dependency;
                l3 = ((DependencyNode)dependency).run == widgetRun ? l2 : Math.max(l2, this.traverseStart((DependencyNode)dependency, (long)((DependencyNode)dependency).margin + l));
            }
            ++n2;
            l2 = l3;
        } while (true);
    }

    public void add(WidgetRun widgetRun) {
        this.runs.add(widgetRun);
        this.lastRun = widgetRun;
    }

    public long computeWrapSize(ConstraintWidgetContainer object, int n) {
        long l;
        Dependency dependency = this.firstRun;
        boolean bl = dependency instanceof ChainRun;
        long l2 = 0L;
        if (bl ? ((ChainRun)dependency).orientation != n : (n == 0 ? !(dependency instanceof HorizontalWidgetRun) : !(dependency instanceof VerticalWidgetRun))) {
            return 0L;
        }
        dependency = n == 0 ? object.horizontalRun.start : object.verticalRun.start;
        object = n == 0 ? object.horizontalRun.end : object.verticalRun.end;
        boolean bl2 = this.firstRun.start.targets.contains(dependency);
        bl = this.firstRun.end.targets.contains(object);
        long l3 = this.firstRun.getWrapDimension();
        if (bl2 && bl) {
            long l4;
            float f;
            l = this.traverseStart(this.firstRun.start, 0L);
            long l5 = this.traverseEnd(this.firstRun.end, 0L);
            l = l4 = l - l3;
            if (l4 >= (long)(-this.firstRun.end.margin)) {
                l = l4 + (long)this.firstRun.end.margin;
            }
            l4 = l5 = -l5 - l3 - (long)this.firstRun.start.margin;
            if (l5 >= (long)this.firstRun.start.margin) {
                l4 = l5 - (long)this.firstRun.start.margin;
            }
            if ((f = this.firstRun.widget.getBiasPercent(n)) > 0.0f) {
                l2 = (long)((float)l4 / f + (float)l / (1.0f - f));
            }
            float f2 = l2;
            l4 = (long)(f2 * f + 0.5f);
            l = (long)(f2 * (1.0f - f) + 0.5f);
            l = (long)this.firstRun.start.margin + (l4 + l3 + l);
            n = this.firstRun.end.margin;
        } else {
            if (bl2) {
                return Math.max(this.traverseStart(this.firstRun.start, this.firstRun.start.margin), (long)this.firstRun.start.margin + l3);
            }
            if (bl) {
                long l6 = this.traverseEnd(this.firstRun.end, this.firstRun.end.margin);
                l = -this.firstRun.end.margin;
                return Math.max(-l6, l + l3);
            }
            l = (long)this.firstRun.start.margin + this.firstRun.getWrapDimension();
            n = this.firstRun.end.margin;
        }
        l -= (long)n;
        return l;
    }

    public void defineTerminalWidgets(boolean bl, boolean bl2) {
        WidgetRun widgetRun;
        if (bl && (widgetRun = this.firstRun) instanceof HorizontalWidgetRun) {
            this.defineTerminalWidget(widgetRun, 0);
        }
        if (!bl2) return;
        widgetRun = this.firstRun;
        if (!(widgetRun instanceof VerticalWidgetRun)) return;
        this.defineTerminalWidget(widgetRun, 1);
    }
}

