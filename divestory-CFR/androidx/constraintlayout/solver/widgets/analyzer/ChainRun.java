/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.RunGroup;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun
extends WidgetRun {
    private int chainStyle;
    ArrayList<WidgetRun> widgets = new ArrayList();

    public ChainRun(ConstraintWidget constraintWidget, int n) {
        super(constraintWidget);
        this.orientation = n;
        this.build();
    }

    /*
     * WARNING - void declaration
     */
    private void build() {
        void var1_2;
        ConstraintWidget object2 = this.widget;
        ArrayList<WidgetRun> arrayList = object2.getPreviousChainMember(this.orientation);
        while (arrayList != null) {
            ConstraintWidget constraintWidget = ((ConstraintWidget)((Object)arrayList)).getPreviousChainMember(this.orientation);
            ConstraintWidget constraintWidget2 = arrayList;
            arrayList = constraintWidget;
        }
        this.widget = var1_2;
        this.widgets.add(var1_2.getRun(this.orientation));
        for (arrayList = var1_2.getNextChainMember((int)this.orientation); arrayList != null; arrayList = arrayList.getNextChainMember((int)this.orientation)) {
            this.widgets.add(((ConstraintWidget)((Object)arrayList)).getRun(this.orientation));
        }
        for (WidgetRun widgetRun : this.widgets) {
            if (this.orientation == 0) {
                widgetRun.widget.horizontalChainRun = this;
                continue;
            }
            if (this.orientation != 1) continue;
            widgetRun.widget.verticalChainRun = this;
        }
        int n = this.orientation == 0 && ((ConstraintWidgetContainer)this.widget.getParent()).isRtl() ? 1 : 0;
        if (n != 0 && this.widgets.size() > 1) {
            arrayList = this.widgets;
            this.widget = arrayList.get((int)(arrayList.size() - 1)).widget;
        }
        n = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
        this.chainStyle = n;
    }

    private ConstraintWidget getFirstVisibleWidget() {
        int n = 0;
        while (n < this.widgets.size()) {
            WidgetRun widgetRun = this.widgets.get(n);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
            ++n;
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        int n = this.widgets.size() - 1;
        while (n >= 0) {
            WidgetRun widgetRun = this.widgets.get(n);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
            --n;
        }
        return null;
    }

    @Override
    void apply() {
        Object object = this.widgets.iterator();
        while (object.hasNext()) {
            object.next().apply();
        }
        int n = this.widgets.size();
        if (n < 1) {
            return;
        }
        Object object2 = this.widgets.get((int)0).widget;
        object = this.widgets.get((int)(n - 1)).widget;
        if (this.orientation == 0) {
            Object object3 = ((ConstraintWidget)object2).mLeft;
            object = ((ConstraintWidget)object).mRight;
            object2 = this.getTarget((ConstraintAnchor)object3, 0);
            n = ((ConstraintAnchor)object3).getMargin();
            object3 = this.getFirstVisibleWidget();
            if (object3 != null) {
                n = ((ConstraintWidget)object3).mLeft.getMargin();
            }
            if (object2 != null) {
                this.addTarget(this.start, (DependencyNode)object2, n);
            }
            object2 = this.getTarget((ConstraintAnchor)object, 0);
            n = ((ConstraintAnchor)object).getMargin();
            object = this.getLastVisibleWidget();
            if (object != null) {
                n = ((ConstraintWidget)object).mRight.getMargin();
            }
            if (object2 != null) {
                this.addTarget(this.end, (DependencyNode)object2, -n);
            }
        } else {
            Object object4 = ((ConstraintWidget)object2).mTop;
            object = ((ConstraintWidget)object).mBottom;
            object2 = this.getTarget((ConstraintAnchor)object4, 1);
            n = ((ConstraintAnchor)object4).getMargin();
            object4 = this.getFirstVisibleWidget();
            if (object4 != null) {
                n = ((ConstraintWidget)object4).mTop.getMargin();
            }
            if (object2 != null) {
                this.addTarget(this.start, (DependencyNode)object2, n);
            }
            object2 = this.getTarget((ConstraintAnchor)object, 1);
            n = ((ConstraintAnchor)object).getMargin();
            object = this.getLastVisibleWidget();
            if (object != null) {
                n = ((ConstraintWidget)object).mBottom.getMargin();
            }
            if (object2 != null) {
                this.addTarget(this.end, (DependencyNode)object2, -n);
            }
        }
        this.start.updateDelegate = this;
        this.end.updateDelegate = this;
    }

    @Override
    public void applyToWidget() {
        int n = 0;
        while (n < this.widgets.size()) {
            this.widgets.get(n).applyToWidget();
            ++n;
        }
    }

    @Override
    void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> iterator2 = this.widgets.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().clear();
        }
    }

    @Override
    public long getWrapDimension() {
        int n = this.widgets.size();
        long l = 0L;
        int n2 = 0;
        while (n2 < n) {
            WidgetRun widgetRun = this.widgets.get(n2);
            l = l + (long)widgetRun.start.margin + widgetRun.getWrapDimension() + (long)widgetRun.end.margin;
            ++n2;
        }
        return l;
    }

    @Override
    void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    @Override
    boolean supportsWrapComputation() {
        int n = this.widgets.size();
        int n2 = 0;
        while (n2 < n) {
            if (!this.widgets.get(n2).supportsWrapComputation()) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public String toString() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("ChainRun ");
        String string2 = this.orientation == 0 ? "horizontal : " : "vertical : ";
        ((StringBuilder)object).append(string2);
        string2 = ((StringBuilder)object).toString();
        object = this.widgets.iterator();
        while (object.hasNext()) {
            Object object2 = (WidgetRun)object.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("<");
            string2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(object2);
            string2 = stringBuilder.toString();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("> ");
            string2 = ((StringBuilder)object2).toString();
        }
        return string2;
    }

    @Override
    public void update(Dependency object) {
        int n;
        int n2;
        int n3;
        boolean bl;
        int n4;
        int n5;
        int n6;
        int n7;
        block100 : {
            int n8;
            int n9;
            block99 : {
                block97 : {
                    float f;
                    float f2;
                    int n10;
                    block98 : {
                        int n11;
                        block96 : {
                            int n12;
                            int n13;
                            int n14;
                            int n15;
                            block89 : {
                                block88 : {
                                    block87 : {
                                        if (!this.start.resolved) return;
                                        if (!this.end.resolved) {
                                            return;
                                        }
                                        object = this.widget.getParent();
                                        bl = object != null && object instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer)object).isRtl() : false;
                                        n10 = this.end.value - this.start.value;
                                        n = this.widgets.size();
                                        n4 = 0;
                                        do {
                                            n7 = -1;
                                            if (n4 >= n) break;
                                            n2 = n4++;
                                            if (this.widgets.get((int)n4).widget.getVisibility() == 8) {
                                                continue;
                                            }
                                            break block87;
                                            break;
                                        } while (true);
                                        n2 = -1;
                                    }
                                    n4 = n5 = n - 1;
                                    do {
                                        n3 = n7;
                                        if (n4 < 0) break block88;
                                        if (this.widgets.get((int)n4).widget.getVisibility() != 8) break;
                                        --n4;
                                    } while (true);
                                    n3 = n4;
                                }
                                n8 = 0;
                                do {
                                    if (n8 >= 2) {
                                        n11 = 0;
                                        n6 = 0;
                                        n7 = 0;
                                        f2 = 0.0f;
                                        break block89;
                                    }
                                    n6 = 0;
                                    n4 = 0;
                                    n9 = 0;
                                    f2 = 0.0f;
                                    for (n14 = 0; n14 < n; ++n14) {
                                        block91 : {
                                            block93 : {
                                                block95 : {
                                                    block94 : {
                                                        block92 : {
                                                            block90 : {
                                                                object = this.widgets.get(n14);
                                                                if (((WidgetRun)object).widget.getVisibility() != 8) break block90;
                                                                n7 = n6;
                                                                n13 = n4;
                                                                break block91;
                                                            }
                                                            n11 = n9 + 1;
                                                            n7 = n6;
                                                            if (n14 > 0) {
                                                                n7 = n6;
                                                                if (n14 >= n2) {
                                                                    n7 = n6 + object.start.margin;
                                                                }
                                                            }
                                                            n13 = object.dimension.value;
                                                            n9 = ((WidgetRun)object).dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0;
                                                            if (n9 == 0) break block92;
                                                            if (this.orientation == 0 && !object.widget.horizontalRun.dimension.resolved) {
                                                                return;
                                                            }
                                                            n15 = n9;
                                                            n12 = n13;
                                                            n6 = n4;
                                                            if (this.orientation == 1) {
                                                                n15 = n9;
                                                                n12 = n13;
                                                                n6 = n4;
                                                                if (!object.widget.verticalRun.dimension.resolved) {
                                                                    return;
                                                                }
                                                            }
                                                            break block93;
                                                        }
                                                        if (((WidgetRun)object).matchConstraintsType != 1 || n8 != 0) break block94;
                                                        n9 = object.dimension.wrapValue;
                                                        n6 = n4 + 1;
                                                        n4 = n9;
                                                        break block95;
                                                    }
                                                    n15 = n9;
                                                    n12 = n13;
                                                    n6 = n4;
                                                    if (!object.dimension.resolved) break block93;
                                                    n6 = n4;
                                                    n4 = n13;
                                                }
                                                n15 = 1;
                                                n12 = n4;
                                            }
                                            if (n15 == 0) {
                                                n9 = n6 + 1;
                                                float f3 = object.widget.mWeight[this.orientation];
                                                n4 = n7;
                                                n6 = n9;
                                                f = f2;
                                                if (f3 >= 0.0f) {
                                                    f = f2 + f3;
                                                    n4 = n7;
                                                    n6 = n9;
                                                }
                                            } else {
                                                n4 = n7 + n12;
                                                f = f2;
                                            }
                                            n7 = n4;
                                            n13 = n6;
                                            n9 = n11;
                                            f2 = f;
                                            if (n14 < n5) {
                                                n7 = n4;
                                                n13 = n6;
                                                n9 = n11;
                                                f2 = f;
                                                if (n14 < n3) {
                                                    n7 = n4 + -object.end.margin;
                                                    f2 = f;
                                                    n9 = n11;
                                                    n13 = n6;
                                                }
                                            }
                                        }
                                        n6 = n7;
                                        n4 = n13;
                                    }
                                    if (n6 < n10 || n4 == 0) break;
                                    ++n8;
                                } while (true);
                                n11 = n9;
                                n7 = n4;
                            }
                            n9 = this.start.value;
                            if (bl) {
                                n9 = this.end.value;
                            }
                            n4 = n9;
                            if (n6 > n10) {
                                n4 = bl ? n9 + (int)((float)(n6 - n10) / 2.0f + 0.5f) : n9 - (int)((float)(n6 - n10) / 2.0f + 0.5f);
                            }
                            if (n7 <= 0) {
                                n8 = n7;
                            } else {
                                f = n10 - n6;
                                n14 = (int)(f / (float)n7 + 0.5f);
                                n9 = 0;
                                n8 = n6;
                                n13 = n4;
                                for (int i = 0; i < n; ++i) {
                                    object = this.widgets.get(i);
                                    if (((WidgetRun)object).widget.getVisibility() != 8 && ((WidgetRun)object).dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !object.dimension.resolved) {
                                        n4 = f2 > 0.0f ? (int)(object.widget.mWeight[this.orientation] * f / f2 + 0.5f) : n14;
                                        if (this.orientation == 0) {
                                            n15 = object.widget.mMatchConstraintMaxWidth;
                                            n12 = object.widget.mMatchConstraintMinWidth;
                                            n6 = ((WidgetRun)object).matchConstraintsType == 1 ? Math.min(n4, object.dimension.wrapValue) : n4;
                                            n12 = n6 = Math.max(n12, n6);
                                            if (n15 > 0) {
                                                n12 = Math.min(n15, n6);
                                            }
                                            n15 = n4;
                                            n6 = n9;
                                            if (n12 != n4) {
                                                n6 = n9 + 1;
                                                n15 = n12;
                                            }
                                        } else {
                                            n15 = object.widget.mMatchConstraintMaxHeight;
                                            n12 = object.widget.mMatchConstraintMinHeight;
                                            n6 = ((WidgetRun)object).matchConstraintsType == 1 ? Math.min(n4, object.dimension.wrapValue) : n4;
                                            n12 = n6 = Math.max(n12, n6);
                                            if (n15 > 0) {
                                                n12 = Math.min(n15, n6);
                                            }
                                            n15 = n4;
                                            n6 = n9;
                                            if (n12 != n4) {
                                                n6 = n9 + 1;
                                                n15 = n12;
                                            }
                                        }
                                        ((WidgetRun)object).dimension.resolve(n15);
                                    } else {
                                        n6 = n9;
                                    }
                                    n9 = n6;
                                }
                                if (n9 <= 0) {
                                    n4 = n8;
                                } else {
                                    n8 = n7 - n9;
                                    n4 = 0;
                                    for (n7 = 0; n7 < n; ++n7) {
                                        object = this.widgets.get(n7);
                                        if (((WidgetRun)object).widget.getVisibility() == 8) continue;
                                        n6 = n4;
                                        if (n7 > 0) {
                                            n6 = n4;
                                            if (n7 >= n2) {
                                                n6 = n4 + object.start.margin;
                                            }
                                        }
                                        n4 = n6 += object.dimension.value;
                                        if (n7 >= n5) continue;
                                        n4 = n6;
                                        if (n7 >= n3) continue;
                                        n4 = n6 + -object.end.margin;
                                    }
                                    n7 = n8;
                                }
                                if (this.chainStyle == 2 && n9 == 0) {
                                    this.chainStyle = 0;
                                    n6 = n4;
                                    n8 = n7;
                                    n4 = n13;
                                } else {
                                    n6 = n4;
                                    n8 = n7;
                                    n4 = n13;
                                }
                            }
                            if (n6 > n10) {
                                this.chainStyle = 2;
                            }
                            if (n11 > 0 && n8 == 0 && n2 == n3) {
                                this.chainStyle = 2;
                            }
                            if ((n7 = this.chainStyle) != 1) break block96;
                            n7 = n11 > 1 ? (n10 - n6) / (n11 - 1) : (n11 == 1 ? (n10 - n6) / 2 : 0);
                            n9 = n7;
                            if (n8 > 0) {
                                n9 = 0;
                            }
                            break block97;
                        }
                        if (n7 != 0) break block98;
                        n9 = (n10 - n6) / (n11 + 1);
                        if (n8 > 0) {
                            n9 = 0;
                        }
                        break block99;
                    }
                    if (n7 != 2) return;
                    f2 = this.orientation == 0 ? this.widget.getHorizontalBiasPercent() : this.widget.getVerticalBiasPercent();
                    f = f2;
                    if (bl) {
                        f = 1.0f - f2;
                    }
                    if ((n7 = (int)((float)(n10 - n6) * f + 0.5f)) < 0 || n8 > 0) {
                        n7 = 0;
                    }
                    n4 = bl ? (n4 -= n7) : (n4 += n7);
                    break block100;
                }
                n7 = 0;
                n6 = n4;
                while (n7 < n) {
                    n4 = bl ? n - (n7 + 1) : n7;
                    object = this.widgets.get(n4);
                    if (((WidgetRun)object).widget.getVisibility() == 8) {
                        ((WidgetRun)object).start.resolve(n6);
                        ((WidgetRun)object).end.resolve(n6);
                        n4 = n6;
                    } else {
                        n4 = n6;
                        if (n7 > 0) {
                            n4 = bl ? n6 - n9 : n6 + n9;
                        }
                        n6 = n4;
                        if (n7 > 0) {
                            n6 = n4;
                            if (n7 >= n2) {
                                n6 = bl ? n4 - object.start.margin : n4 + object.start.margin;
                            }
                        }
                        if (bl) {
                            ((WidgetRun)object).end.resolve(n6);
                        } else {
                            ((WidgetRun)object).start.resolve(n6);
                        }
                        n4 = n8 = object.dimension.value;
                        if (((WidgetRun)object).dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            n4 = n8;
                            if (((WidgetRun)object).matchConstraintsType == 1) {
                                n4 = object.dimension.wrapValue;
                            }
                        }
                        n6 = bl ? (n6 -= n4) : (n6 += n4);
                        if (bl) {
                            ((WidgetRun)object).start.resolve(n6);
                        } else {
                            ((WidgetRun)object).end.resolve(n6);
                        }
                        ((WidgetRun)object).resolved = true;
                        n4 = n6;
                        if (n7 < n5) {
                            n4 = n6;
                            if (n7 < n3) {
                                n4 = bl ? n6 - -object.end.margin : n6 + -object.end.margin;
                            }
                        }
                    }
                    ++n7;
                    n6 = n4;
                }
                return;
            }
            n7 = 0;
            while (n7 < n) {
                n6 = bl ? n - (n7 + 1) : n7;
                object = this.widgets.get(n6);
                if (((WidgetRun)object).widget.getVisibility() == 8) {
                    ((WidgetRun)object).start.resolve(n4);
                    ((WidgetRun)object).end.resolve(n4);
                } else {
                    n6 = bl ? n4 - n9 : n4 + n9;
                    n4 = n6;
                    if (n7 > 0) {
                        n4 = n6;
                        if (n7 >= n2) {
                            n4 = bl ? n6 - object.start.margin : n6 + object.start.margin;
                        }
                    }
                    if (bl) {
                        ((WidgetRun)object).end.resolve(n4);
                    } else {
                        ((WidgetRun)object).start.resolve(n4);
                    }
                    n6 = n8 = object.dimension.value;
                    if (((WidgetRun)object).dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        n6 = n8;
                        if (((WidgetRun)object).matchConstraintsType == 1) {
                            n6 = Math.min(n8, object.dimension.wrapValue);
                        }
                    }
                    n6 = bl ? n4 - n6 : n4 + n6;
                    if (bl) {
                        ((WidgetRun)object).start.resolve(n6);
                    } else {
                        ((WidgetRun)object).end.resolve(n6);
                    }
                    n4 = n6;
                    if (n7 < n5) {
                        n4 = n6;
                        if (n7 < n3) {
                            n4 = bl ? n6 - -object.end.margin : n6 + -object.end.margin;
                        }
                    }
                }
                ++n7;
            }
            return;
        }
        n7 = 0;
        while (n7 < n) {
            n6 = bl ? n - (n7 + 1) : n7;
            object = this.widgets.get(n6);
            if (((WidgetRun)object).widget.getVisibility() == 8) {
                ((WidgetRun)object).start.resolve(n4);
                ((WidgetRun)object).end.resolve(n4);
            } else {
                n6 = n4;
                if (n7 > 0) {
                    n6 = n4;
                    if (n7 >= n2) {
                        n6 = bl ? n4 - object.start.margin : n4 + object.start.margin;
                    }
                }
                if (bl) {
                    ((WidgetRun)object).end.resolve(n6);
                } else {
                    ((WidgetRun)object).start.resolve(n6);
                }
                n4 = object.dimension.value;
                if (((WidgetRun)object).dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((WidgetRun)object).matchConstraintsType == 1) {
                    n4 = object.dimension.wrapValue;
                }
                n6 = bl ? (n6 -= n4) : (n6 += n4);
                if (bl) {
                    ((WidgetRun)object).start.resolve(n6);
                } else {
                    ((WidgetRun)object).end.resolve(n6);
                }
                n4 = n6;
                if (n7 < n5) {
                    n4 = n6;
                    if (n7 < n3) {
                        n4 = bl ? n6 - -object.end.margin : n6 + -object.end.margin;
                    }
                }
            }
            ++n7;
        }
    }
}

