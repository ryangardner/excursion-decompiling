/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.Chain;
import androidx.constraintlayout.solver.widgets.ChainHead;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer
extends WidgetContainer {
    private static final boolean DEBUG = false;
    static final boolean DEBUG_GRAPH = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final int MAX_ITERATIONS = 8;
    BasicMeasure mBasicMeasureSolver = new BasicMeasure(this);
    int mDebugSolverPassCount = 0;
    public DependencyGraph mDependencyGraph = new DependencyGraph(this);
    public boolean mGroupsWrapOptimized = false;
    private boolean mHeightMeasuredTooSmall = false;
    ChainHead[] mHorizontalChainsArray = new ChainHead[4];
    int mHorizontalChainsSize = 0;
    public boolean mHorizontalWrapOptimized = false;
    private boolean mIsRtl = false;
    protected BasicMeasure.Measurer mMeasurer = null;
    public Metrics mMetrics;
    private int mOptimizationLevel = 263;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    public boolean mSkipSolver = false;
    protected LinearSystem mSystem = new LinearSystem();
    ChainHead[] mVerticalChainsArray = new ChainHead[4];
    int mVerticalChainsSize = 0;
    public boolean mVerticalWrapOptimized = false;
    private boolean mWidthMeasuredTooSmall = false;
    public int mWrapFixedHeight = 0;
    public int mWrapFixedWidth = 0;

    public ConstraintWidgetContainer() {
    }

    public ConstraintWidgetContainer(int n, int n2) {
        super(n, n2);
    }

    public ConstraintWidgetContainer(int n, int n2, int n3, int n4) {
        super(n, n2, n3, n4);
    }

    private void addHorizontalChain(ConstraintWidget constraintWidget) {
        int n = this.mHorizontalChainsSize;
        ChainHead[] arrchainHead = this.mHorizontalChainsArray;
        if (n + 1 >= arrchainHead.length) {
            this.mHorizontalChainsArray = Arrays.copyOf(arrchainHead, arrchainHead.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(constraintWidget, 0, this.isRtl());
        ++this.mHorizontalChainsSize;
    }

    private void addVerticalChain(ConstraintWidget constraintWidget) {
        int n = this.mVerticalChainsSize;
        ChainHead[] arrchainHead = this.mVerticalChainsArray;
        if (n + 1 >= arrchainHead.length) {
            this.mVerticalChainsArray = Arrays.copyOf(arrchainHead, arrchainHead.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(constraintWidget, 1, this.isRtl());
        ++this.mVerticalChainsSize;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    void addChain(ConstraintWidget constraintWidget, int n) {
        if (n == 0) {
            this.addHorizontalChain(constraintWidget);
            return;
        }
        if (n != 1) return;
        this.addVerticalChain(constraintWidget);
    }

    public boolean addChildrenToSolver(LinearSystem linearSystem) {
        Object object;
        int n;
        this.addToSolver(linearSystem);
        int n2 = this.mChildren.size();
        boolean bl = false;
        for (n = 0; n < n2; ++n) {
            object = (ConstraintWidget)this.mChildren.get(n);
            object.setInBarrier(0, false);
            object.setInBarrier(1, false);
            if (!(object instanceof Barrier)) continue;
            bl = true;
        }
        if (bl) {
            for (n = 0; n < n2; ++n) {
                object = (ConstraintWidget)this.mChildren.get(n);
                if (!(object instanceof Barrier)) continue;
                ((Barrier)object).markWidgets();
            }
        }
        for (n = 0; n < n2; ++n) {
            object = (ConstraintWidget)this.mChildren.get(n);
            if (!object.addFirst()) continue;
            object.addToSolver(linearSystem);
        }
        for (n = 0; n < n2; ++n) {
            ConstraintWidget constraintWidget = (ConstraintWidget)this.mChildren.get(n);
            if (constraintWidget instanceof ConstraintWidgetContainer) {
                object = constraintWidget.mListDimensionBehaviors[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.mListDimensionBehaviors[1];
                if (object == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                constraintWidget.addToSolver(linearSystem);
                if (object == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)((Object)object));
                }
                if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) continue;
                constraintWidget.setVerticalDimensionBehaviour(dimensionBehaviour);
                continue;
            }
            Optimizer.checkMatchParent(this, linearSystem, constraintWidget);
            if (constraintWidget.addFirst()) continue;
            constraintWidget.addToSolver(linearSystem);
        }
        if (this.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 0);
        }
        if (this.mVerticalChainsSize <= 0) return true;
        Chain.applyChainConstraints(this, linearSystem, 1);
        return true;
    }

    public void defineTerminalWidgets() {
        this.mDependencyGraph.defineTerminalWidgets(this.getHorizontalDimensionBehaviour(), this.getVerticalDimensionBehaviour());
    }

    public boolean directMeasure(boolean bl) {
        return this.mDependencyGraph.directMeasure(bl);
    }

    public boolean directMeasureSetup(boolean bl) {
        return this.mDependencyGraph.directMeasureSetup(bl);
    }

    public boolean directMeasureWithOrientation(boolean bl, int n) {
        return this.mDependencyGraph.directMeasureWithOrientation(bl, n);
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        this.mSystem.fillMetrics(metrics);
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<Guideline>();
        int n = this.mChildren.size();
        int n2 = 0;
        while (n2 < n) {
            ConstraintWidget constraintWidget = (ConstraintWidget)this.mChildren.get(n2);
            if (constraintWidget instanceof Guideline && ((Guideline)(constraintWidget = (Guideline)constraintWidget)).getOrientation() == 0) {
                arrayList.add((Guideline)constraintWidget);
            }
            ++n2;
        }
        return arrayList;
    }

    public BasicMeasure.Measurer getMeasurer() {
        return this.mMeasurer;
    }

    public int getOptimizationLevel() {
        return this.mOptimizationLevel;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    @Override
    public String getType() {
        return "ConstraintLayout";
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<Guideline>();
        int n = this.mChildren.size();
        int n2 = 0;
        while (n2 < n) {
            ConstraintWidget constraintWidget = (ConstraintWidget)this.mChildren.get(n2);
            if (constraintWidget instanceof Guideline && ((Guideline)(constraintWidget = (Guideline)constraintWidget)).getOrientation() == 1) {
                arrayList.add((Guideline)constraintWidget);
            }
            ++n2;
        }
        return arrayList;
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public void invalidateGraph() {
        this.mDependencyGraph.invalidateGraph();
    }

    public void invalidateMeasures() {
        this.mDependencyGraph.invalidateMeasures();
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public boolean isRtl() {
        return this.mIsRtl;
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public void layout() {
        this.mX = 0;
        this.mY = 0;
        var1_1 = Math.max(0, this.getWidth());
        var2_2 = Math.max(0, this.getHeight());
        this.mWidthMeasuredTooSmall = false;
        this.mHeightMeasuredTooSmall = false;
        var3_3 = !this.optimizeFor(64) && !this.optimizeFor(128) ? 0 : 1;
        this.mSystem.graphOptimizer = false;
        this.mSystem.newgraphOptimizer = false;
        if (this.mOptimizationLevel != 0 && var3_3 != 0) {
            this.mSystem.newgraphOptimizer = true;
        }
        var4_4 = this.mListDimensionBehaviors[1];
        var5_5 = this.mListDimensionBehaviors[0];
        var6_6 = this.mChildren;
        var7_7 = this.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || this.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        this.resetChains();
        var8_8 = this.mChildren.size();
        for (var3_3 = 0; var3_3 < var8_8; ++var3_3) {
            var9_9 = (ConstraintWidget)this.mChildren.get(var3_3);
            if (!(var9_9 instanceof WidgetContainer)) continue;
            ((WidgetContainer)var9_9).layout();
        }
        var10_10 = 0;
        var11_11 = true;
        var3_3 = 0;
        while (var11_11) {
            var12_12 = var10_10 + 1;
            var13_13 = var11_11;
            try {
                this.mSystem.reset();
                var13_13 = var11_11;
                this.resetChains();
                var13_13 = var11_11;
                this.createObjectVariables(this.mSystem);
                for (var10_10 = 0; var10_10 < var8_8; ++var10_10) {
                    var13_13 = var11_11;
                    ((ConstraintWidget)this.mChildren.get(var10_10)).createObjectVariables(this.mSystem);
                }
                var13_13 = var11_11;
                var13_13 = var11_11 = this.addChildrenToSolver(this.mSystem);
                if (var11_11) {
                    var13_13 = var11_11;
                    this.mSystem.minimize();
                    var13_13 = var11_11;
                }
            }
            catch (Exception var14_14) {
                var14_14.printStackTrace();
                var9_9 = System.out;
                var15_15 = new StringBuilder();
                var15_15.append("EXCEPTION : ");
                var15_15.append(var14_14);
                var9_9.println(var15_15.toString());
            }
            if (var13_13) {
                this.updateChildrenFromSolver(this.mSystem, Optimizer.flags);
            } else {
                this.updateFromSolver(this.mSystem);
                for (var10_10 = 0; var10_10 < var8_8; ++var10_10) {
                    ((ConstraintWidget)this.mChildren.get(var10_10)).updateFromSolver(this.mSystem);
                }
            }
            if (var7_7 && var12_12 < 8 && Optimizer.flags[2]) {
                var17_17 = 0;
                var10_10 = 0;
                for (var16_16 = 0; var16_16 < var8_8; ++var16_16) {
                    var9_9 = (ConstraintWidget)this.mChildren.get(var16_16);
                    var17_17 = Math.max(var17_17, var9_9.mX + var9_9.getWidth());
                    var10_10 = Math.max(var10_10, var9_9.mY + var9_9.getHeight());
                }
                var17_17 = Math.max(this.mMinWidth, var17_17);
                var16_16 = Math.max(this.mMinHeight, var10_10);
                if (var5_5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this.getWidth() < var17_17) {
                    this.setWidth(var17_17);
                    this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    var11_11 = true;
                    var10_10 = 1;
                } else {
                    var11_11 = false;
                    var10_10 = var3_3;
                }
                var13_13 = var11_11;
                var3_3 = var10_10;
                if (var4_4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    var13_13 = var11_11;
                    var3_3 = var10_10;
                    if (this.getHeight() < var16_16) {
                        this.setHeight(var16_16);
                        this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                        var13_13 = true;
                        var3_3 = 1;
                    }
                }
            } else {
                var13_13 = false;
            }
            if ((var10_10 = Math.max(this.mMinWidth, this.getWidth())) > this.getWidth()) {
                this.setWidth(var10_10);
                this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                var13_13 = true;
                var3_3 = 1;
            }
            if ((var10_10 = Math.max(this.mMinHeight, this.getHeight())) > this.getHeight()) {
                this.setHeight(var10_10);
                this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                var13_13 = true;
                var3_3 = 1;
            }
            var11_11 = var13_13;
            var16_16 = var3_3;
            if (var3_3 != 0) ** GOTO lbl-1000
            var18_18 = var13_13;
            var10_10 = var3_3;
            if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                var18_18 = var13_13;
                var10_10 = var3_3;
                if (var1_1 > 0) {
                    var18_18 = var13_13;
                    var10_10 = var3_3;
                    if (this.getWidth() > var1_1) {
                        this.mWidthMeasuredTooSmall = true;
                        this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                        this.setWidth(var1_1);
                        var18_18 = true;
                        var10_10 = 1;
                    }
                }
            }
            var11_11 = var18_18;
            var16_16 = var10_10;
            if (this.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) ** GOTO lbl-1000
            var11_11 = var18_18;
            var16_16 = var10_10;
            if (var2_2 <= 0) ** GOTO lbl-1000
            var11_11 = var18_18;
            var16_16 = var10_10;
            if (this.getHeight() > var2_2) {
                this.mHeightMeasuredTooSmall = true;
                this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                this.setHeight(var2_2);
                var11_11 = true;
                var3_3 = 1;
            } else lbl-1000: // 4 sources:
            {
                var3_3 = var16_16;
            }
            var10_10 = var12_12;
        }
        this.mChildren = var6_6;
        if (var3_3 != 0) {
            this.mListDimensionBehaviors[0] = var5_5;
            this.mListDimensionBehaviors[1] = var4_4;
        }
        this.resetSolverVariables(this.mSystem.getCache());
    }

    public long measure(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.mPaddingLeft = n8;
        this.mPaddingTop = n9;
        return this.mBasicMeasureSolver.solverMeasure(this, n, n8, n9, n2, n3, n4, n5, n6, n7);
    }

    public boolean optimizeFor(int n) {
        if ((this.mOptimizationLevel & n) != n) return false;
        return true;
    }

    @Override
    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        this.mSkipSolver = false;
        super.reset();
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
        this.mDependencyGraph.setMeasurer(measurer);
    }

    public void setOptimizationLevel(int n) {
        this.mOptimizationLevel = n;
        LinearSystem.OPTIMIZED_ENGINE = Optimizer.enabled(n, 256);
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        this.mPaddingLeft = n;
        this.mPaddingTop = n2;
        this.mPaddingRight = n3;
        this.mPaddingBottom = n4;
    }

    public void setRtl(boolean bl) {
        this.mIsRtl = bl;
    }

    public void updateChildrenFromSolver(LinearSystem linearSystem, boolean[] arrbl) {
        int n = 0;
        arrbl[2] = false;
        this.updateFromSolver(linearSystem);
        int n2 = this.mChildren.size();
        while (n < n2) {
            ((ConstraintWidget)this.mChildren.get(n)).updateFromSolver(linearSystem);
            ++n;
        }
    }

    @Override
    public void updateFromRuns(boolean bl, boolean bl2) {
        super.updateFromRuns(bl, bl2);
        int n = this.mChildren.size();
        int n2 = 0;
        while (n2 < n) {
            ((ConstraintWidget)this.mChildren.get(n2)).updateFromRuns(bl, bl2);
            ++n2;
        }
    }

    public void updateHierarchy() {
        this.mBasicMeasureSolver.updateHierarchy(this);
    }
}

