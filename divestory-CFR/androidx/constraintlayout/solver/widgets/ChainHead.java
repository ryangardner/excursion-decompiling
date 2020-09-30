/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

public class ChainHead {
    private boolean mDefined;
    protected ConstraintWidget mFirst;
    protected ConstraintWidget mFirstMatchConstraintWidget;
    protected ConstraintWidget mFirstVisibleWidget;
    protected boolean mHasComplexMatchWeights;
    protected boolean mHasDefinedWeights;
    protected boolean mHasRatio;
    protected boolean mHasUndefinedWeights;
    protected ConstraintWidget mHead;
    private boolean mIsRtl = false;
    protected ConstraintWidget mLast;
    protected ConstraintWidget mLastMatchConstraintWidget;
    protected ConstraintWidget mLastVisibleWidget;
    boolean mOptimizable;
    private int mOrientation;
    int mTotalMargins;
    int mTotalSize;
    protected float mTotalWeight = 0.0f;
    int mVisibleWidgets;
    protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
    protected int mWidgetsCount;
    protected int mWidgetsMatchCount;

    public ChainHead(ConstraintWidget constraintWidget, int n, boolean bl) {
        this.mFirst = constraintWidget;
        this.mOrientation = n;
        this.mIsRtl = bl;
    }

    private void defineChainProperties() {
        int n = this.mOrientation * 2;
        Object object = this.mFirst;
        boolean bl = true;
        this.mOptimizable = true;
        Object object2 = object;
        boolean bl2 = false;
        while (!bl2) {
            ++this.mWidgetsCount;
            Object object3 = ((ConstraintWidget)object).mNextChainWidget;
            int n2 = this.mOrientation;
            Object object4 = null;
            object3[n2] = null;
            object.mListNextMatchConstraintsWidget[this.mOrientation] = null;
            if (((ConstraintWidget)object).getVisibility() != 8) {
                int n3;
                ++this.mVisibleWidgets;
                if (((ConstraintWidget)object).getDimensionBehaviour(this.mOrientation) != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mTotalSize += ((ConstraintWidget)object).getLength(this.mOrientation);
                }
                this.mTotalSize = n3 = this.mTotalSize + ((ConstraintWidget)object).mListAnchors[n].getMargin();
                object3 = ((ConstraintWidget)object).mListAnchors;
                n2 = n + 1;
                this.mTotalSize = n3 + ((ConstraintAnchor)((Object)object3[n2])).getMargin();
                this.mTotalMargins = n3 = this.mTotalMargins + ((ConstraintWidget)object).mListAnchors[n].getMargin();
                this.mTotalMargins = n3 + ((ConstraintWidget)object).mListAnchors[n2].getMargin();
                if (this.mFirstVisibleWidget == null) {
                    this.mFirstVisibleWidget = object;
                }
                this.mLastVisibleWidget = object;
                if (((ConstraintWidget)object).mListDimensionBehaviors[this.mOrientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (((ConstraintWidget)object).mResolvedMatchConstraintDefault[this.mOrientation] == 0 || ((ConstraintWidget)object).mResolvedMatchConstraintDefault[this.mOrientation] == 3 || ((ConstraintWidget)object).mResolvedMatchConstraintDefault[this.mOrientation] == 2) {
                        ++this.mWidgetsMatchCount;
                        float f = ((ConstraintWidget)object).mWeight[this.mOrientation];
                        if (f > 0.0f) {
                            this.mTotalWeight += ((ConstraintWidget)object).mWeight[this.mOrientation];
                        }
                        if (ChainHead.isMatchConstraintEqualityCandidate((ConstraintWidget)object, this.mOrientation)) {
                            if (f < 0.0f) {
                                this.mHasUndefinedWeights = true;
                            } else {
                                this.mHasDefinedWeights = true;
                            }
                            if (this.mWeightedMatchConstraintsWidgets == null) {
                                this.mWeightedMatchConstraintsWidgets = new ArrayList();
                            }
                            this.mWeightedMatchConstraintsWidgets.add((ConstraintWidget)object);
                        }
                        if (this.mFirstMatchConstraintWidget == null) {
                            this.mFirstMatchConstraintWidget = object;
                        }
                        if ((object3 = this.mLastMatchConstraintWidget) != null) {
                            object3.mListNextMatchConstraintsWidget[this.mOrientation] = object;
                        }
                        this.mLastMatchConstraintWidget = object;
                    }
                    if (this.mOrientation == 0) {
                        if (((ConstraintWidget)object).mMatchConstraintDefaultWidth != 0) {
                            this.mOptimizable = false;
                        } else if (((ConstraintWidget)object).mMatchConstraintMinWidth != 0 || ((ConstraintWidget)object).mMatchConstraintMaxWidth != 0) {
                            this.mOptimizable = false;
                        }
                    } else if (((ConstraintWidget)object).mMatchConstraintDefaultHeight != 0) {
                        this.mOptimizable = false;
                    } else if (((ConstraintWidget)object).mMatchConstraintMinHeight != 0 || ((ConstraintWidget)object).mMatchConstraintMaxHeight != 0) {
                        this.mOptimizable = false;
                    }
                    if (((ConstraintWidget)object).mDimensionRatio != 0.0f) {
                        this.mOptimizable = false;
                        this.mHasRatio = true;
                    }
                }
            }
            if (object2 != object) {
                object2.mNextChainWidget[this.mOrientation] = object;
            }
            object3 = object.mListAnchors[n + 1].mTarget;
            object2 = object4;
            if (object3 != null) {
                object3 = object3.mOwner;
                object2 = object4;
                if (object3.mListAnchors[n].mTarget != null) {
                    object2 = object3.mListAnchors[n].mTarget.mOwner != object ? object4 : object3;
                }
            }
            if (object2 == null) {
                object2 = object;
                bl2 = true;
            }
            object4 = object;
            object = object2;
            object2 = object4;
        }
        object2 = this.mFirstVisibleWidget;
        if (object2 != null) {
            this.mTotalSize -= ((ConstraintWidget)object2).mListAnchors[n].getMargin();
        }
        if ((object2 = this.mLastVisibleWidget) != null) {
            this.mTotalSize -= ((ConstraintWidget)object2).mListAnchors[n + 1].getMargin();
        }
        this.mLast = object;
        this.mHead = this.mOrientation == 0 && this.mIsRtl ? object : this.mFirst;
        if (!this.mHasDefinedWeights || !this.mHasUndefinedWeights) {
            bl = false;
        }
        this.mHasComplexMatchWeights = bl;
    }

    private static boolean isMatchConstraintEqualityCandidate(ConstraintWidget constraintWidget, int n) {
        if (constraintWidget.getVisibility() == 8) return false;
        if (constraintWidget.mListDimensionBehaviors[n] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) return false;
        if (constraintWidget.mResolvedMatchConstraintDefault[n] == 0) return true;
        if (constraintWidget.mResolvedMatchConstraintDefault[n] != 3) return false;
        return true;
    }

    public void define() {
        if (!this.mDefined) {
            this.defineChainProperties();
        }
        this.mDefined = true;
    }

    public ConstraintWidget getFirst() {
        return this.mFirst;
    }

    public ConstraintWidget getFirstMatchConstraintWidget() {
        return this.mFirstMatchConstraintWidget;
    }

    public ConstraintWidget getFirstVisibleWidget() {
        return this.mFirstVisibleWidget;
    }

    public ConstraintWidget getHead() {
        return this.mHead;
    }

    public ConstraintWidget getLast() {
        return this.mLast;
    }

    public ConstraintWidget getLastMatchConstraintWidget() {
        return this.mLastMatchConstraintWidget;
    }

    public ConstraintWidget getLastVisibleWidget() {
        return this.mLastVisibleWidget;
    }

    public float getTotalWeight() {
        return this.mTotalWeight;
    }
}

