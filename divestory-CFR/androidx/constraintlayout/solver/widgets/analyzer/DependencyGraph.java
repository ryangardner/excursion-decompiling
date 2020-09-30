/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency;
import androidx.constraintlayout.solver.widgets.analyzer.GuidelineReference;
import androidx.constraintlayout.solver.widgets.analyzer.HelperReferences;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.RunGroup;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DependencyGraph {
    private static final boolean USE_GROUPS = true;
    private ConstraintWidgetContainer container;
    private ConstraintWidgetContainer mContainer;
    ArrayList<RunGroup> mGroups = new ArrayList();
    private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    private BasicMeasure.Measurer mMeasurer = null;
    private boolean mNeedBuildGraph = true;
    private boolean mNeedRedoMeasures = true;
    private ArrayList<WidgetRun> mRuns = new ArrayList();
    private ArrayList<RunGroup> runGroups = new ArrayList();

    public DependencyGraph(ConstraintWidgetContainer constraintWidgetContainer) {
        this.container = constraintWidgetContainer;
        this.mContainer = constraintWidgetContainer;
    }

    private void applyGroup(DependencyNode object, int n, int n2, DependencyNode dependencyNode, ArrayList<RunGroup> arrayList, RunGroup object22) {
        Dependency dependency = ((DependencyNode)object).run;
        if (dependency.runGroup != null) return;
        if (dependency == this.container.horizontalRun) return;
        if (dependency == this.container.verticalRun) {
            return;
        }
        object = object22;
        if (object22 == null) {
            object = new RunGroup((WidgetRun)dependency, n2);
            arrayList.add((RunGroup)object);
        }
        dependency.runGroup = object;
        ((RunGroup)object).add((WidgetRun)dependency);
        for (Dependency dependency2 : dependency.start.dependencies) {
            if (!(dependency2 instanceof DependencyNode)) continue;
            this.applyGroup((DependencyNode)dependency2, n, 0, dependencyNode, arrayList, (RunGroup)object);
        }
        for (Dependency dependency3 : dependency.end.dependencies) {
            if (!(dependency3 instanceof DependencyNode)) continue;
            this.applyGroup((DependencyNode)dependency3, n, 1, dependencyNode, arrayList, (RunGroup)object);
        }
        if (n == 1 && dependency instanceof VerticalWidgetRun) {
            for (Dependency dependency4 : ((VerticalWidgetRun)dependency).baseline.dependencies) {
                if (!(dependency4 instanceof DependencyNode)) continue;
                this.applyGroup((DependencyNode)dependency4, n, 2, dependencyNode, arrayList, (RunGroup)object);
            }
        }
        for (DependencyNode dependencyNode2 : dependency.start.targets) {
            if (dependencyNode2 == dependencyNode) {
                ((RunGroup)object).dual = true;
            }
            this.applyGroup(dependencyNode2, n, 0, dependencyNode, arrayList, (RunGroup)object);
        }
        for (DependencyNode dependencyNode3 : dependency.end.targets) {
            if (dependencyNode3 == dependencyNode) {
                ((RunGroup)object).dual = true;
            }
            this.applyGroup(dependencyNode3, n, 1, dependencyNode, arrayList, (RunGroup)object);
        }
        if (n != 1) return;
        if (!(dependency instanceof VerticalWidgetRun)) return;
        Iterator<DependencyNode> iterator2 = ((VerticalWidgetRun)dependency).baseline.targets.iterator();
        while (iterator2.hasNext()) {
            dependency = iterator2.next();
            this.applyGroup((DependencyNode)dependency, n, 2, dependencyNode, arrayList, (RunGroup)object);
        }
    }

    private boolean basicMeasureWidgets(ConstraintWidgetContainer constraintWidgetContainer) {
        Iterator iterator2 = constraintWidgetContainer.mChildren.iterator();
        while (iterator2.hasNext()) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
            int n;
            int n2;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3;
            ConstraintWidget constraintWidget;
            int n3;
            block37 : {
                block38 : {
                    constraintWidget = (ConstraintWidget)iterator2.next();
                    dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[0];
                    dimensionBehaviour = constraintWidget.mListDimensionBehaviors[1];
                    if (constraintWidget.getVisibility() == 8) {
                        constraintWidget.measured = true;
                        continue;
                    }
                    if (constraintWidget.mMatchConstraintPercentWidth < 1.0f && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        constraintWidget.mMatchConstraintDefaultWidth = 2;
                    }
                    if (constraintWidget.mMatchConstraintPercentHeight < 1.0f && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        constraintWidget.mMatchConstraintDefaultHeight = 2;
                    }
                    if (constraintWidget.getDimensionRatio() > 0.0f) {
                        if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED)) {
                            constraintWidget.mMatchConstraintDefaultWidth = 3;
                        } else if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                            constraintWidget.mMatchConstraintDefaultHeight = 3;
                        } else if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                                constraintWidget.mMatchConstraintDefaultWidth = 3;
                            }
                            if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                                constraintWidget.mMatchConstraintDefaultHeight = 3;
                            }
                        }
                    }
                    dimensionBehaviour3 = dimensionBehaviour2;
                    if (dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) break block37;
                    dimensionBehaviour3 = dimensionBehaviour2;
                    if (constraintWidget.mMatchConstraintDefaultWidth != 1) break block37;
                    if (constraintWidget.mLeft.mTarget == null) break block38;
                    dimensionBehaviour3 = dimensionBehaviour2;
                    if (constraintWidget.mRight.mTarget != null) break block37;
                }
                dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            }
            dimensionBehaviour2 = dimensionBehaviour3;
            dimensionBehaviour3 = dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 1 && (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null) ? ConstraintWidget.DimensionBehaviour.WRAP_CONTENT : dimensionBehaviour;
            constraintWidget.horizontalRun.dimensionBehavior = dimensionBehaviour2;
            constraintWidget.horizontalRun.matchConstraintsType = constraintWidget.mMatchConstraintDefaultWidth;
            constraintWidget.verticalRun.dimensionBehavior = dimensionBehaviour3;
            constraintWidget.verticalRun.matchConstraintsType = constraintWidget.mMatchConstraintDefaultHeight;
            if (dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT && dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.FIXED && dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT && dimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.FIXED && dimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                float f;
                float f2;
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                    if (constraintWidget.mMatchConstraintDefaultWidth == 3) {
                        if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        }
                        n = constraintWidget.getHeight();
                        n3 = (int)((float)n * constraintWidget.mDimensionRatio + 0.5f);
                        this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, n3, ConstraintWidget.DimensionBehaviour.FIXED, n);
                        constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                        constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                        continue;
                    }
                    if (constraintWidget.mMatchConstraintDefaultWidth == 1) {
                        this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour3, 0);
                        constraintWidget.horizontalRun.dimension.wrapValue = constraintWidget.getWidth();
                        continue;
                    }
                    if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
                        if (constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                            n = (int)(constraintWidget.mMatchConstraintPercentWidth * (float)constraintWidgetContainer.getWidth() + 0.5f);
                            n3 = constraintWidget.getHeight();
                            this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, n, dimensionBehaviour3, n3);
                            constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                            continue;
                        }
                    } else if (constraintWidget.mListAnchors[0].mTarget == null || constraintWidget.mListAnchors[1].mTarget == null) {
                        this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour3, 0);
                        constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                        constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                        continue;
                    }
                }
                if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                    if (constraintWidget.mMatchConstraintDefaultHeight == 3) {
                        if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        }
                        n = constraintWidget.getWidth();
                        f2 = f = constraintWidget.mDimensionRatio;
                        if (constraintWidget.getDimensionRatioSide() == -1) {
                            f2 = 1.0f / f;
                        }
                        n3 = (int)((float)n * f2 + 0.5f);
                        this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, n, ConstraintWidget.DimensionBehaviour.FIXED, n3);
                        constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                        constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                        continue;
                    }
                    if (constraintWidget.mMatchConstraintDefaultHeight == 1) {
                        this.measure(constraintWidget, dimensionBehaviour2, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        constraintWidget.verticalRun.dimension.wrapValue = constraintWidget.getHeight();
                        continue;
                    }
                    if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
                        if (constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                            f2 = constraintWidget.mMatchConstraintPercentHeight;
                            n3 = constraintWidget.getWidth();
                            n = (int)(f2 * (float)constraintWidgetContainer.getHeight() + 0.5f);
                            this.measure(constraintWidget, dimensionBehaviour2, n3, ConstraintWidget.DimensionBehaviour.FIXED, n);
                            constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                            constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                            constraintWidget.measured = true;
                            continue;
                        }
                    } else if (constraintWidget.mListAnchors[2].mTarget == null || constraintWidget.mListAnchors[3].mTarget == null) {
                        this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, dimensionBehaviour3, 0);
                        constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                        constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                        constraintWidget.measured = true;
                        continue;
                    }
                }
                if (dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || dimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) continue;
                if (constraintWidget.mMatchConstraintDefaultWidth != 1 && constraintWidget.mMatchConstraintDefaultHeight != 1) {
                    if (constraintWidget.mMatchConstraintDefaultHeight != 2 || constraintWidget.mMatchConstraintDefaultWidth != 2 || constraintWidgetContainer.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.FIXED && constraintWidgetContainer.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.FIXED || constraintWidgetContainer.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.FIXED && constraintWidgetContainer.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.FIXED) continue;
                    f = constraintWidget.mMatchConstraintPercentWidth;
                    f2 = constraintWidget.mMatchConstraintPercentHeight;
                    n = (int)(f * (float)constraintWidgetContainer.getWidth() + 0.5f);
                    n3 = (int)(f2 * (float)constraintWidgetContainer.getHeight() + 0.5f);
                    this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, n, ConstraintWidget.DimensionBehaviour.FIXED, n3);
                    constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                    constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                    constraintWidget.measured = true;
                    continue;
                }
                this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                constraintWidget.horizontalRun.dimension.wrapValue = constraintWidget.getWidth();
                constraintWidget.verticalRun.dimension.wrapValue = constraintWidget.getHeight();
                continue;
            }
            n = constraintWidget.getWidth();
            if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                n2 = constraintWidgetContainer.getWidth();
                n = constraintWidget.mLeft.mMargin;
                n3 = constraintWidget.mRight.mMargin;
                dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
                n = n2 - n - n3;
            }
            n3 = constraintWidget.getHeight();
            if (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                n2 = constraintWidgetContainer.getHeight();
                int n4 = constraintWidget.mTop.mMargin;
                n3 = constraintWidget.mBottom.mMargin;
                dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.FIXED;
                n3 = n2 - n4 - n3;
            }
            this.measure(constraintWidget, dimensionBehaviour2, n, dimensionBehaviour3, n3);
            constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
            constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
            constraintWidget.measured = true;
        }
        return false;
    }

    private int computeWrap(ConstraintWidgetContainer constraintWidgetContainer, int n) {
        int n2 = this.mGroups.size();
        long l = 0L;
        int n3 = 0;
        while (n3 < n2) {
            l = Math.max(l, this.mGroups.get(n3).computeWrapSize(constraintWidgetContainer, n));
            ++n3;
        }
        return (int)l;
    }

    private void displayGraph() {
        Object object = this.mRuns.iterator();
        Object object2 = "digraph {\n";
        do {
            if (!object.hasNext()) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append("\n}\n");
                object = ((StringBuilder)object).toString();
                object2 = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("content:<<\n");
                stringBuilder.append((String)object);
                stringBuilder.append("\n>>");
                ((PrintStream)object2).println(stringBuilder.toString());
                return;
            }
            object2 = this.generateDisplayGraph(object.next(), (String)object2);
        } while (true);
    }

    private void findGroup(WidgetRun object, int n, ArrayList<RunGroup> arrayList) {
        for (Dependency dependency : object.start.dependencies) {
            if (dependency instanceof DependencyNode) {
                this.applyGroup((DependencyNode)dependency, n, 0, ((WidgetRun)object).end, arrayList, null);
                continue;
            }
            if (!(dependency instanceof WidgetRun)) continue;
            this.applyGroup(((WidgetRun)dependency).start, n, 0, ((WidgetRun)object).end, arrayList, null);
        }
        for (Dependency dependency : object.end.dependencies) {
            if (dependency instanceof DependencyNode) {
                this.applyGroup((DependencyNode)dependency, n, 1, ((WidgetRun)object).start, arrayList, null);
                continue;
            }
            if (!(dependency instanceof WidgetRun)) continue;
            this.applyGroup(((WidgetRun)dependency).end, n, 1, ((WidgetRun)object).start, arrayList, null);
        }
        if (n != 1) return;
        object = ((VerticalWidgetRun)object).baseline.dependencies.iterator();
        while (object.hasNext()) {
            Dependency dependency = (Dependency)object.next();
            if (!(dependency instanceof DependencyNode)) continue;
            this.applyGroup((DependencyNode)dependency, n, 2, null, arrayList, null);
        }
    }

    private String generateChainDisplayGraph(ChainRun object, String string2) {
        CharSequence charSequence;
        int n = ((ChainRun)object).orientation;
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("cluster_");
        ((StringBuilder)charSequence2).append(((ChainRun)object).widget.getDebugName());
        charSequence2 = ((StringBuilder)charSequence2).toString();
        if (n == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("_h");
            charSequence2 = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("_v");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("subgraph ");
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(" {\n");
        charSequence2 = ((StringBuilder)charSequence).toString();
        Iterator<WidgetRun> iterator2 = ((ChainRun)object).widgets.iterator();
        object = "";
        do {
            StringBuilder stringBuilder;
            if (!iterator2.hasNext()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("}\n");
                charSequence = ((StringBuilder)charSequence).toString();
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(string2);
                ((StringBuilder)charSequence2).append((String)object);
                ((StringBuilder)charSequence2).append((String)charSequence);
                return ((StringBuilder)charSequence2).toString();
            }
            WidgetRun widgetRun = iterator2.next();
            charSequence = widgetRun.widget.getDebugName();
            if (n == 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append("_HORIZONTAL");
                charSequence = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence);
                stringBuilder.append("_VERTICAL");
                charSequence = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            stringBuilder.append((String)charSequence);
            stringBuilder.append(";\n");
            charSequence2 = stringBuilder.toString();
            object = this.generateDisplayGraph(widgetRun, (String)object);
        } while (true);
    }

    private String generateDisplayGraph(WidgetRun widgetRun, String charSequence) {
        block22 : {
            Object object;
            CharSequence charSequence2;
            DependencyNode dependencyNode;
            block20 : {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour;
                block21 : {
                    dependencyNode = widgetRun.start;
                    object = widgetRun.end;
                    if (!(widgetRun instanceof HelperReferences) && dependencyNode.dependencies.isEmpty() && ((DependencyNode)object).dependencies.isEmpty() & dependencyNode.targets.isEmpty() && ((DependencyNode)object).targets.isEmpty()) {
                        return charSequence;
                    }
                    charSequence2 = new StringBuilder();
                    charSequence2.append((String)charSequence);
                    charSequence2.append(this.nodeDefinition(widgetRun));
                    charSequence = charSequence2.toString();
                    boolean bl = this.isCenteredConnection(dependencyNode, (DependencyNode)object);
                    charSequence = this.generateDisplayNode((DependencyNode)object, bl, this.generateDisplayNode(dependencyNode, bl, (String)charSequence));
                    boolean bl2 = widgetRun instanceof VerticalWidgetRun;
                    charSequence2 = charSequence;
                    if (bl2) {
                        charSequence2 = this.generateDisplayNode(((VerticalWidgetRun)widgetRun).baseline, bl, (String)charSequence);
                    }
                    if (widgetRun instanceof HorizontalWidgetRun || (bl = widgetRun instanceof ChainRun) && ((ChainRun)widgetRun).orientation == 0) break block20;
                    if (bl2) break block21;
                    charSequence = charSequence2;
                    if (!bl) break block22;
                    charSequence = charSequence2;
                    if (((ChainRun)widgetRun).orientation != 1) break block22;
                }
                if ((dimensionBehaviour = widgetRun.widget.getVerticalDimensionBehaviour()) != ConstraintWidget.DimensionBehaviour.FIXED && dimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    charSequence = charSequence2;
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        charSequence = charSequence2;
                        if (widgetRun.widget.getDimensionRatio() > 0.0f) {
                            widgetRun.widget.getDebugName();
                            charSequence = charSequence2;
                        }
                    }
                } else if (!dependencyNode.targets.isEmpty() && ((DependencyNode)object).targets.isEmpty()) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("\n");
                    ((StringBuilder)charSequence).append(((DependencyNode)object).name());
                    ((StringBuilder)charSequence).append(" -> ");
                    ((StringBuilder)charSequence).append(dependencyNode.name());
                    ((StringBuilder)charSequence).append("\n");
                    object = ((StringBuilder)charSequence).toString();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append((String)object);
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = charSequence2;
                    if (dependencyNode.targets.isEmpty()) {
                        charSequence = charSequence2;
                        if (!((DependencyNode)object).targets.isEmpty()) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("\n");
                            ((StringBuilder)charSequence).append(dependencyNode.name());
                            ((StringBuilder)charSequence).append(" -> ");
                            ((StringBuilder)charSequence).append(((DependencyNode)object).name());
                            ((StringBuilder)charSequence).append("\n");
                            object = ((StringBuilder)charSequence).toString();
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append((String)charSequence2);
                            ((StringBuilder)charSequence).append((String)object);
                            charSequence = ((StringBuilder)charSequence).toString();
                        }
                    }
                }
                break block22;
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = widgetRun.widget.getHorizontalDimensionBehaviour();
            if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.FIXED && dimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                charSequence = charSequence2;
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    charSequence = charSequence2;
                    if (widgetRun.widget.getDimensionRatio() > 0.0f) {
                        widgetRun.widget.getDebugName();
                        charSequence = charSequence2;
                    }
                }
            } else if (!dependencyNode.targets.isEmpty() && ((DependencyNode)object).targets.isEmpty()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("\n");
                ((StringBuilder)charSequence).append(((DependencyNode)object).name());
                ((StringBuilder)charSequence).append(" -> ");
                ((StringBuilder)charSequence).append(dependencyNode.name());
                ((StringBuilder)charSequence).append("\n");
                charSequence = ((StringBuilder)charSequence).toString();
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence2);
                ((StringBuilder)object).append((String)charSequence);
                charSequence = ((StringBuilder)object).toString();
            } else {
                charSequence = charSequence2;
                if (dependencyNode.targets.isEmpty()) {
                    charSequence = charSequence2;
                    if (!((DependencyNode)object).targets.isEmpty()) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("\n");
                        ((StringBuilder)charSequence).append(dependencyNode.name());
                        ((StringBuilder)charSequence).append(" -> ");
                        ((StringBuilder)charSequence).append(((DependencyNode)object).name());
                        ((StringBuilder)charSequence).append("\n");
                        object = ((StringBuilder)charSequence).toString();
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        ((StringBuilder)charSequence).append((String)object);
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                }
            }
        }
        if (!(widgetRun instanceof ChainRun)) return charSequence;
        return this.generateChainDisplayGraph((ChainRun)widgetRun, (String)charSequence);
    }

    private String generateDisplayNode(DependencyNode dependencyNode, boolean bl, String object) {
        Iterator<DependencyNode> iterator2 = dependencyNode.targets.iterator();
        String string2 = object;
        while (iterator2.hasNext()) {
            Object object2;
            block9 : {
                block8 : {
                    object = iterator2.next();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("\n");
                    ((StringBuilder)object2).append(dependencyNode.name());
                    String string3 = ((StringBuilder)object2).toString();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(string3);
                    ((StringBuilder)object2).append(" -> ");
                    ((StringBuilder)object2).append(((DependencyNode)object).name());
                    object2 = ((StringBuilder)object2).toString();
                    if (dependencyNode.margin > 0 || bl) break block8;
                    object = object2;
                    if (!(dependencyNode.run instanceof HelperReferences)) break block9;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append("[");
                object = object2 = ((StringBuilder)object).toString();
                if (dependencyNode.margin > 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append("label=\"");
                    ((StringBuilder)object).append(dependencyNode.margin);
                    ((StringBuilder)object).append("\"");
                    object = object2 = ((StringBuilder)object).toString();
                    if (bl) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append(",");
                        object = ((StringBuilder)object).toString();
                    }
                }
                object2 = object;
                if (bl) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(" style=dashed ");
                    object2 = ((StringBuilder)object2).toString();
                }
                object = object2;
                if (dependencyNode.run instanceof HelperReferences) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append(" style=bold,color=gray ");
                    object = ((StringBuilder)object).toString();
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("]");
                object = ((StringBuilder)object2).toString();
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("\n");
            object2 = ((StringBuilder)object2).toString();
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append((String)object2);
            string2 = ((StringBuilder)object).toString();
        }
        return string2;
    }

    private boolean isCenteredConnection(DependencyNode dependencyNode, DependencyNode object) {
        Iterator<DependencyNode> iterator2 = dependencyNode.targets.iterator();
        boolean bl = false;
        int n = 0;
        while (iterator2.hasNext()) {
            if (iterator2.next() == object) continue;
            ++n;
        }
        object = ((DependencyNode)object).targets.iterator();
        int n2 = 0;
        do {
            if (!object.hasNext()) {
                boolean bl2 = bl;
                if (n <= 0) return bl2;
                bl2 = bl;
                if (n2 <= 0) return bl2;
                return true;
            }
            if ((DependencyNode)object.next() == dependencyNode) continue;
            ++n2;
        } while (true);
    }

    private void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int n, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int n2) {
        this.mMeasure.horizontalBehavior = dimensionBehaviour;
        this.mMeasure.verticalBehavior = dimensionBehaviour2;
        this.mMeasure.horizontalDimension = n;
        this.mMeasure.verticalDimension = n2;
        this.mMeasurer.measure(constraintWidget, this.mMeasure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }

    private String nodeDefinition(WidgetRun object) {
        boolean bl = object instanceof VerticalWidgetRun;
        String string2 = ((WidgetRun)object).widget.getDebugName();
        Object object2 = ((WidgetRun)object).widget;
        Object object3 = !bl ? ((ConstraintWidget)object2).getHorizontalDimensionBehaviour() : ((ConstraintWidget)object2).getVerticalDimensionBehaviour();
        RunGroup runGroup = ((WidgetRun)object).runGroup;
        if (!bl) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("_HORIZONTAL");
            object2 = ((StringBuilder)object2).toString();
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("_VERTICAL");
            object2 = ((StringBuilder)object2).toString();
        }
        Object object4 = new StringBuilder();
        ((StringBuilder)object4).append((String)object2);
        ((StringBuilder)object4).append(" [shape=none, label=<");
        object4 = ((StringBuilder)object4).toString();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object4);
        ((StringBuilder)object2).append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">");
        object4 = ((StringBuilder)object2).toString();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object4);
        ((StringBuilder)object2).append("  <TR>");
        object2 = ((StringBuilder)object2).toString();
        if (!bl) {
            object4 = new StringBuilder();
            ((StringBuilder)object4).append((String)object2);
            ((StringBuilder)object4).append("    <TD ");
            object2 = object4 = ((StringBuilder)object4).toString();
            if (object.start.resolved) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object4);
                ((StringBuilder)object2).append(" BGCOLOR=\"green\"");
                object2 = ((StringBuilder)object2).toString();
            }
            object4 = new StringBuilder();
            ((StringBuilder)object4).append((String)object2);
            ((StringBuilder)object4).append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>");
            object2 = ((StringBuilder)object4).toString();
        } else {
            object4 = new StringBuilder();
            ((StringBuilder)object4).append((String)object2);
            ((StringBuilder)object4).append("    <TD ");
            object2 = object4 = ((StringBuilder)object4).toString();
            if (object.start.resolved) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object4);
                ((StringBuilder)object2).append(" BGCOLOR=\"green\"");
                object2 = ((StringBuilder)object2).toString();
            }
            object4 = new StringBuilder();
            ((StringBuilder)object4).append((String)object2);
            ((StringBuilder)object4).append(" PORT=\"TOP\" BORDER=\"1\">T</TD>");
            object2 = ((StringBuilder)object4).toString();
        }
        object4 = new StringBuilder();
        ((StringBuilder)object4).append((String)object2);
        ((StringBuilder)object4).append("    <TD BORDER=\"1\" ");
        object4 = ((StringBuilder)object4).toString();
        if (object.dimension.resolved && !object.widget.measured) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object4);
            ((StringBuilder)object2).append(" BGCOLOR=\"green\" ");
            object2 = ((StringBuilder)object2).toString();
        } else if (object.dimension.resolved && object.widget.measured) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object4);
            ((StringBuilder)object2).append(" BGCOLOR=\"lightgray\" ");
            object2 = ((StringBuilder)object2).toString();
        } else {
            object2 = object4;
            if (!object.dimension.resolved) {
                object2 = object4;
                if (object.widget.measured) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object4);
                    ((StringBuilder)object2).append(" BGCOLOR=\"yellow\" ");
                    object2 = ((StringBuilder)object2).toString();
                }
            }
        }
        object4 = object2;
        if (object3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append("style=\"dashed\"");
            object4 = ((StringBuilder)object3).toString();
        }
        if (runGroup != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" [");
            ((StringBuilder)object2).append(runGroup.groupIndex + 1);
            ((StringBuilder)object2).append("/");
            ((StringBuilder)object2).append(RunGroup.index);
            ((StringBuilder)object2).append("]");
            object2 = ((StringBuilder)object2).toString();
        } else {
            object2 = "";
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append((String)object4);
        ((StringBuilder)object3).append(">");
        ((StringBuilder)object3).append(string2);
        ((StringBuilder)object3).append((String)object2);
        ((StringBuilder)object3).append(" </TD>");
        object2 = ((StringBuilder)object3).toString();
        if (!bl) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append("    <TD ");
            object2 = object3 = ((StringBuilder)object3).toString();
            if (object.end.resolved) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(" BGCOLOR=\"green\"");
                object2 = ((StringBuilder)object).toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>");
            object = ((StringBuilder)object).toString();
        } else {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append("    <TD ");
            object2 = object3 = ((StringBuilder)object3).toString();
            if (bl) {
                object2 = object3;
                if (((VerticalWidgetRun)object).baseline.resolved) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append((String)object3);
                    ((StringBuilder)object2).append(" BGCOLOR=\"green\"");
                    object2 = ((StringBuilder)object2).toString();
                }
            }
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>");
            object2 = ((StringBuilder)object3).toString();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append("    <TD ");
            object2 = object3 = ((StringBuilder)object3).toString();
            if (object.end.resolved) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(" BGCOLOR=\"green\"");
                object2 = ((StringBuilder)object).toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>");
            object = ((StringBuilder)object).toString();
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("  </TR></TABLE>");
        object = ((StringBuilder)object2).toString();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(">];\n");
        return ((StringBuilder)object2).toString();
    }

    public void buildGraph() {
        this.buildGraph(this.mRuns);
        this.mGroups.clear();
        RunGroup.index = 0;
        this.findGroup(this.container.horizontalRun, 0, this.mGroups);
        this.findGroup(this.container.verticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public void buildGraph(ArrayList<WidgetRun> object) {
        ((ArrayList)object).clear();
        this.mContainer.horizontalRun.clear();
        this.mContainer.verticalRun.clear();
        ((ArrayList)object).add((WidgetRun)this.mContainer.horizontalRun);
        ((ArrayList)object).add((WidgetRun)this.mContainer.verticalRun);
        Iterator iterator2 = this.mContainer.mChildren.iterator();
        Object object2 = null;
        while (iterator2.hasNext()) {
            Object object3;
            ConstraintWidget constraintWidget = (ConstraintWidget)iterator2.next();
            if (constraintWidget instanceof Guideline) {
                ((ArrayList)object).add((WidgetRun)new GuidelineReference(constraintWidget));
                continue;
            }
            if (constraintWidget.isInHorizontalChain()) {
                if (constraintWidget.horizontalChainRun == null) {
                    constraintWidget.horizontalChainRun = new ChainRun(constraintWidget, 0);
                }
                object3 = object2;
                if (object2 == null) {
                    object3 = new HashSet();
                }
                ((HashSet)object3).add((ChainRun)constraintWidget.horizontalChainRun);
                object2 = object3;
            } else {
                ((ArrayList)object).add((WidgetRun)constraintWidget.horizontalRun);
            }
            if (constraintWidget.isInVerticalChain()) {
                if (constraintWidget.verticalChainRun == null) {
                    constraintWidget.verticalChainRun = new ChainRun(constraintWidget, 1);
                }
                object3 = object2;
                if (object2 == null) {
                    object3 = new HashSet();
                }
                ((HashSet)object3).add((ChainRun)constraintWidget.verticalChainRun);
            } else {
                ((ArrayList)object).add(constraintWidget.verticalRun);
                object3 = object2;
            }
            object2 = object3;
            if (!(constraintWidget instanceof HelperWidget)) continue;
            ((ArrayList)object).add(new HelperReferences(constraintWidget));
            object2 = object3;
        }
        if (object2 != null) {
            ((ArrayList)object).addAll(object2);
        }
        object2 = ((ArrayList)object).iterator();
        while (object2.hasNext()) {
            object2.next().clear();
        }
        object2 = ((ArrayList)object).iterator();
        while (object2.hasNext()) {
            object = object2.next();
            if (((WidgetRun)object).widget == this.mContainer) continue;
            ((WidgetRun)object).apply();
        }
    }

    public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2) {
        Object object;
        if (!this.mNeedBuildGraph) return;
        this.buildGraph();
        Object object2 = this.container.mChildren.iterator();
        boolean bl = false;
        while (object2.hasNext()) {
            object = (ConstraintWidget)object2.next();
            object.isTerminalWidget[0] = true;
            object.isTerminalWidget[1] = true;
            if (!(object instanceof Barrier)) continue;
            bl = true;
        }
        if (bl) return;
        object = this.mGroups.iterator();
        while (object.hasNext()) {
            object2 = (RunGroup)object.next();
            boolean bl2 = dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            boolean bl3 = dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            ((RunGroup)object2).defineTerminalWidgets(bl2, bl3);
        }
    }

    public boolean directMeasure(boolean bl) {
        Object object;
        Object object22;
        block15 : {
            Iterator<WidgetRun> iterator2;
            WidgetRun widgetRun2;
            int n;
            boolean bl2 = true;
            int n2 = bl & true;
            if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
                for (Object object22 : this.container.mChildren) {
                    object22.measured = false;
                    object22.horizontalRun.reset();
                    object22.verticalRun.reset();
                }
                this.container.measured = false;
                this.container.horizontalRun.reset();
                this.container.verticalRun.reset();
                this.mNeedRedoMeasures = false;
            }
            if (this.basicMeasureWidgets(this.mContainer)) {
                return false;
            }
            this.container.setX(0);
            this.container.setY(0);
            object22 = this.container.getDimensionBehaviour(0);
            object = this.container.getDimensionBehaviour(1);
            if (this.mNeedBuildGraph) {
                this.buildGraph();
            }
            int n3 = this.container.getX();
            int n4 = this.container.getY();
            this.container.horizontalRun.start.resolve(n3);
            this.container.verticalRun.start.resolve(n4);
            this.measureWidgets();
            if (object22 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || object == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                block14 : {
                    n = n2;
                    if (n2 != 0) {
                        iterator2 = this.mRuns.iterator();
                        do {
                            n = n2;
                            if (!iterator2.hasNext()) break block14;
                        } while (iterator2.next().supportsWrapComputation());
                        n = 0;
                    }
                }
                if (n != 0 && object22 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    iterator2 = this.container;
                    ((ConstraintWidget)((Object)iterator2)).setWidth(this.computeWrap((ConstraintWidgetContainer)((Object)iterator2), 0));
                    this.container.horizontalRun.dimension.resolve(this.container.getWidth());
                }
                if (n != 0 && object == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    iterator2 = this.container;
                    ((ConstraintWidget)((Object)iterator2)).setHeight(this.computeWrap((ConstraintWidgetContainer)((Object)iterator2), 1));
                    this.container.verticalRun.dimension.resolve(this.container.getHeight());
                }
            }
            if (this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.FIXED && this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                n = 0;
            } else {
                n = this.container.getWidth() + n3;
                this.container.horizontalRun.end.resolve(n);
                this.container.horizontalRun.dimension.resolve(n - n3);
                this.measureWidgets();
                if (this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                    n = this.container.getHeight() + n4;
                    this.container.verticalRun.end.resolve(n);
                    this.container.verticalRun.dimension.resolve(n - n4);
                }
                this.measureWidgets();
                n = 1;
            }
            for (WidgetRun widgetRun2 : this.mRuns) {
                if (widgetRun2.widget == this.container && !widgetRun2.resolved) continue;
                widgetRun2.applyToWidget();
            }
            iterator2 = this.mRuns.iterator();
            do {
                bl = bl2;
                if (!iterator2.hasNext()) break block15;
                widgetRun2 = iterator2.next();
            } while (n == 0 && widgetRun2.widget == this.container || widgetRun2.start.resolved && (widgetRun2.end.resolved || widgetRun2 instanceof GuidelineReference) && (widgetRun2.dimension.resolved || widgetRun2 instanceof ChainRun || widgetRun2 instanceof GuidelineReference));
            bl = false;
        }
        this.container.setHorizontalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)((Object)object22));
        this.container.setVerticalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)((Object)object));
        return bl;
    }

    public boolean directMeasureSetup(boolean bl) {
        if (this.mNeedBuildGraph) {
            for (ConstraintWidget constraintWidget : this.container.mChildren) {
                constraintWidget.measured = false;
                constraintWidget.horizontalRun.dimension.resolved = false;
                constraintWidget.horizontalRun.resolved = false;
                constraintWidget.horizontalRun.reset();
                constraintWidget.verticalRun.dimension.resolved = false;
                constraintWidget.verticalRun.resolved = false;
                constraintWidget.verticalRun.reset();
            }
            this.container.measured = false;
            this.container.horizontalRun.dimension.resolved = false;
            this.container.horizontalRun.resolved = false;
            this.container.horizontalRun.reset();
            this.container.verticalRun.dimension.resolved = false;
            this.container.verticalRun.resolved = false;
            this.container.verticalRun.reset();
            this.buildGraph();
        }
        if (this.basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.container.setX(0);
        this.container.setY(0);
        this.container.horizontalRun.start.resolve(0);
        this.container.verticalRun.start.resolve(0);
        return true;
    }

    /*
     * Unable to fully structure code
     */
    public boolean directMeasureWithOrientation(boolean var1_1, int var2_2) {
        block11 : {
            block12 : {
                var3_3 = true;
                var4_4 = var1_1 & true;
                var5_5 = this.container.getDimensionBehaviour(0);
                var6_6 = this.container.getDimensionBehaviour(1);
                var7_7 = this.container.getX();
                var8_8 = this.container.getY();
                if (var4_4 != 0 && (var5_5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var6_6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
                    block10 : {
                        var9_9 = this.mRuns.iterator();
                        do {
                            var10_17 = var4_4;
                            if (!var9_9.hasNext()) break block10;
                            var11_18 = var9_9.next();
                        } while (var11_18.orientation != var2_2 || var11_18.supportsWrapComputation());
                        var10_17 = 0;
                    }
                    if (var2_2 == 0) {
                        if (var10_17 != 0 && var5_5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                            var9_10 = this.container;
                            var9_10.setWidth(this.computeWrap(var9_10, 0));
                            this.container.horizontalRun.dimension.resolve(this.container.getWidth());
                        }
                    } else if (var10_17 != 0 && var6_6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                        var9_11 = this.container;
                        var9_11.setHeight(this.computeWrap(var9_11, 1));
                        this.container.verticalRun.dimension.resolve(this.container.getHeight());
                    }
                }
                if (var2_2 != 0) break block12;
                if (this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.FIXED && this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) ** GOTO lbl-1000
                var10_17 = this.container.getWidth() + var7_7;
                this.container.horizontalRun.end.resolve(var10_17);
                this.container.horizontalRun.dimension.resolve(var10_17 - var7_7);
                ** GOTO lbl41
            }
            if (this.container.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.FIXED && this.container.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) lbl-1000: // 2 sources:
            {
                var10_17 = 0;
            } else {
                var10_17 = this.container.getHeight() + var8_8;
                this.container.verticalRun.end.resolve(var10_17);
                this.container.verticalRun.dimension.resolve(var10_17 - var8_8);
lbl41: // 2 sources:
                var10_17 = 1;
            }
            this.measureWidgets();
            for (WidgetRun var9_14 : this.mRuns) {
                if (var9_14.orientation != var2_2 || var9_14.widget == this.container && !var9_14.resolved) continue;
                var9_14.applyToWidget();
            }
            var11_18 = this.mRuns.iterator();
            do {
                var1_1 = var3_3;
                if (!var11_18.hasNext()) break block11;
                var9_16 = var11_18.next();
            } while (var9_16.orientation != var2_2 || var10_17 == 0 && var9_16.widget == this.container || var9_16.start.resolved && var9_16.end.resolved && (var9_16 instanceof ChainRun || var9_16.dimension.resolved));
            var1_1 = false;
        }
        this.container.setHorizontalDimensionBehaviour(var5_5);
        this.container.setVerticalDimensionBehaviour(var6_6);
        return var1_1;
    }

    public void invalidateGraph() {
        this.mNeedBuildGraph = true;
    }

    public void invalidateMeasures() {
        this.mNeedRedoMeasures = true;
    }

    public void measureWidgets() {
        Iterator iterator2 = this.container.mChildren.iterator();
        while (iterator2.hasNext()) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour;
            boolean bl;
            ConstraintWidget constraintWidget;
            int n;
            Object object;
            block14 : {
                block13 : {
                    constraintWidget = (ConstraintWidget)iterator2.next();
                    if (constraintWidget.measured) continue;
                    object = constraintWidget.mListDimensionBehaviors;
                    boolean bl2 = false;
                    object = object[0];
                    dimensionBehaviour = constraintWidget.mListDimensionBehaviors[1];
                    n = constraintWidget.mMatchConstraintDefaultWidth;
                    int n2 = constraintWidget.mMatchConstraintDefaultHeight;
                    n = object != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && (object != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || n != 1) ? 0 : 1;
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) break block13;
                    bl = bl2;
                    if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) break block14;
                    bl = bl2;
                    if (n2 != 1) break block14;
                }
                bl = true;
            }
            boolean bl3 = constraintWidget.horizontalRun.dimension.resolved;
            boolean bl4 = constraintWidget.verticalRun.dimension.resolved;
            if (bl3 && bl4) {
                this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.verticalRun.dimension.value);
                constraintWidget.measured = true;
            } else if (bl3 && bl) {
                this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, constraintWidget.verticalRun.dimension.value);
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.verticalRun.dimension.wrapValue = constraintWidget.getHeight();
                } else {
                    constraintWidget.verticalRun.dimension.resolve(constraintWidget.getHeight());
                    constraintWidget.measured = true;
                }
            } else if (bl4 && n != 0) {
                this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, constraintWidget.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.FIXED, constraintWidget.verticalRun.dimension.value);
                if (object == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    constraintWidget.horizontalRun.dimension.wrapValue = constraintWidget.getWidth();
                } else {
                    constraintWidget.horizontalRun.dimension.resolve(constraintWidget.getWidth());
                    constraintWidget.measured = true;
                }
            }
            if (!constraintWidget.measured || constraintWidget.verticalRun.baselineDimension == null) continue;
            constraintWidget.verticalRun.baselineDimension.resolve(constraintWidget.getBaselineDistance());
        }
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
    }
}

