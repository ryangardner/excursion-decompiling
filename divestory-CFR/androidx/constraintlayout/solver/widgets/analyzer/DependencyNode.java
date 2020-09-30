/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DependencyNode
implements Dependency {
    public boolean delegateToWidgetRun = false;
    List<Dependency> dependencies = new ArrayList<Dependency>();
    int margin;
    DimensionDependency marginDependency = null;
    int marginFactor = 1;
    public boolean readyToSolve = false;
    public boolean resolved = false;
    WidgetRun run;
    List<DependencyNode> targets = new ArrayList<DependencyNode>();
    Type type = Type.UNKNOWN;
    public Dependency updateDelegate = null;
    public int value;

    public DependencyNode(WidgetRun widgetRun) {
        this.run = widgetRun;
    }

    public void addDependency(Dependency dependency) {
        this.dependencies.add(dependency);
        if (!this.resolved) return;
        dependency.update(dependency);
    }

    public void clear() {
        this.targets.clear();
        this.dependencies.clear();
        this.resolved = false;
        this.value = 0;
        this.readyToSolve = false;
        this.delegateToWidgetRun = false;
    }

    public String name() {
        StringBuilder stringBuilder;
        String string2 = this.run.widget.getDebugName();
        if (this.type != Type.LEFT && this.type != Type.RIGHT) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("_VERTICAL");
            string2 = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("_HORIZONTAL");
            string2 = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(":");
        stringBuilder.append(this.type.name());
        return stringBuilder.toString();
    }

    public void resolve(int n) {
        if (this.resolved) {
            return;
        }
        this.resolved = true;
        this.value = n;
        Iterator<Dependency> iterator2 = this.dependencies.iterator();
        while (iterator2.hasNext()) {
            Dependency dependency = iterator2.next();
            dependency.update(dependency);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.run.widget.getDebugName());
        stringBuilder.append(":");
        stringBuilder.append((Object)this.type);
        stringBuilder.append("(");
        Object object = this.resolved ? Integer.valueOf(this.value) : "unresolved";
        stringBuilder.append(object);
        stringBuilder.append(") <t=");
        stringBuilder.append(this.targets.size());
        stringBuilder.append(":d=");
        stringBuilder.append(this.dependencies.size());
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    @Override
    public void update(Dependency object) {
        object = this.targets.iterator();
        while (object.hasNext()) {
            if (((DependencyNode)object.next()).resolved) continue;
            return;
        }
        this.readyToSolve = true;
        object = this.updateDelegate;
        if (object != null) {
            object.update(this);
        }
        if (this.delegateToWidgetRun) {
            this.run.update(this);
            return;
        }
        object = null;
        int n = 0;
        for (DependencyNode dependencyNode : this.targets) {
            if (dependencyNode instanceof DimensionDependency) continue;
            ++n;
            object = dependencyNode;
        }
        if (object != null && n == 1 && ((DependencyNode)object).resolved) {
            DependencyNode dependencyNode;
            dependencyNode = this.marginDependency;
            if (dependencyNode != null) {
                if (!((DimensionDependency)dependencyNode).resolved) return;
                this.margin = this.marginFactor * this.marginDependency.value;
            }
            this.resolve(((DependencyNode)object).value + this.margin);
        }
        if ((object = this.updateDelegate) == null) return;
        object.update(this);
    }

    static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type BASELINE;
        public static final /* enum */ Type BOTTOM;
        public static final /* enum */ Type HORIZONTAL_DIMENSION;
        public static final /* enum */ Type LEFT;
        public static final /* enum */ Type RIGHT;
        public static final /* enum */ Type TOP;
        public static final /* enum */ Type UNKNOWN;
        public static final /* enum */ Type VERTICAL_DIMENSION;

        static {
            Type type;
            UNKNOWN = new Type();
            HORIZONTAL_DIMENSION = new Type();
            VERTICAL_DIMENSION = new Type();
            LEFT = new Type();
            RIGHT = new Type();
            TOP = new Type();
            BOTTOM = new Type();
            BASELINE = type = new Type();
            $VALUES = new Type[]{UNKNOWN, HORIZONTAL_DIMENSION, VERTICAL_DIMENSION, LEFT, RIGHT, TOP, BOTTOM, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

