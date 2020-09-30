package androidx.constraintlayout.solver.widgets;

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
   protected float mTotalWeight = 0.0F;
   int mVisibleWidgets;
   protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
   protected int mWidgetsCount;
   protected int mWidgetsMatchCount;

   public ChainHead(ConstraintWidget var1, int var2, boolean var3) {
      this.mFirst = var1;
      this.mOrientation = var2;
      this.mIsRtl = var3;
   }

   private void defineChainProperties() {
      int var1 = this.mOrientation * 2;
      ConstraintWidget var2 = this.mFirst;
      boolean var3 = true;
      this.mOptimizable = true;
      ConstraintWidget var4 = var2;

      ConstraintWidget var8;
      for(boolean var5 = false; !var5; var4 = var8) {
         ++this.mWidgetsCount;
         ConstraintWidget[] var6 = var2.mNextChainWidget;
         int var7 = this.mOrientation;
         var8 = null;
         var6[var7] = null;
         var2.mListNextMatchConstraintsWidget[this.mOrientation] = null;
         ConstraintWidget var12;
         if (var2.getVisibility() != 8) {
            ++this.mVisibleWidgets;
            if (var2.getDimensionBehaviour(this.mOrientation) != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               this.mTotalSize += var2.getLength(this.mOrientation);
            }

            int var9 = this.mTotalSize + var2.mListAnchors[var1].getMargin();
            this.mTotalSize = var9;
            ConstraintAnchor[] var11 = var2.mListAnchors;
            var7 = var1 + 1;
            this.mTotalSize = var9 + var11[var7].getMargin();
            var9 = this.mTotalMargins + var2.mListAnchors[var1].getMargin();
            this.mTotalMargins = var9;
            this.mTotalMargins = var9 + var2.mListAnchors[var7].getMargin();
            if (this.mFirstVisibleWidget == null) {
               this.mFirstVisibleWidget = var2;
            }

            this.mLastVisibleWidget = var2;
            if (var2.mListDimensionBehaviors[this.mOrientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               if (var2.mResolvedMatchConstraintDefault[this.mOrientation] == 0 || var2.mResolvedMatchConstraintDefault[this.mOrientation] == 3 || var2.mResolvedMatchConstraintDefault[this.mOrientation] == 2) {
                  ++this.mWidgetsMatchCount;
                  float var10 = var2.mWeight[this.mOrientation];
                  if (var10 > 0.0F) {
                     this.mTotalWeight += var2.mWeight[this.mOrientation];
                  }

                  if (isMatchConstraintEqualityCandidate(var2, this.mOrientation)) {
                     if (var10 < 0.0F) {
                        this.mHasUndefinedWeights = true;
                     } else {
                        this.mHasDefinedWeights = true;
                     }

                     if (this.mWeightedMatchConstraintsWidgets == null) {
                        this.mWeightedMatchConstraintsWidgets = new ArrayList();
                     }

                     this.mWeightedMatchConstraintsWidgets.add(var2);
                  }

                  if (this.mFirstMatchConstraintWidget == null) {
                     this.mFirstMatchConstraintWidget = var2;
                  }

                  var12 = this.mLastMatchConstraintWidget;
                  if (var12 != null) {
                     var12.mListNextMatchConstraintsWidget[this.mOrientation] = var2;
                  }

                  this.mLastMatchConstraintWidget = var2;
               }

               if (this.mOrientation == 0) {
                  if (var2.mMatchConstraintDefaultWidth != 0) {
                     this.mOptimizable = false;
                  } else if (var2.mMatchConstraintMinWidth != 0 || var2.mMatchConstraintMaxWidth != 0) {
                     this.mOptimizable = false;
                  }
               } else if (var2.mMatchConstraintDefaultHeight != 0) {
                  this.mOptimizable = false;
               } else if (var2.mMatchConstraintMinHeight != 0 || var2.mMatchConstraintMaxHeight != 0) {
                  this.mOptimizable = false;
               }

               if (var2.mDimensionRatio != 0.0F) {
                  this.mOptimizable = false;
                  this.mHasRatio = true;
               }
            }
         }

         if (var4 != var2) {
            var4.mNextChainWidget[this.mOrientation] = var2;
         }

         ConstraintAnchor var13 = var2.mListAnchors[var1 + 1].mTarget;
         var4 = var8;
         if (var13 != null) {
            var12 = var13.mOwner;
            var4 = var8;
            if (var12.mListAnchors[var1].mTarget != null) {
               if (var12.mListAnchors[var1].mTarget.mOwner != var2) {
                  var4 = var8;
               } else {
                  var4 = var12;
               }
            }
         }

         if (var4 == null) {
            var4 = var2;
            var5 = true;
         }

         var8 = var2;
         var2 = var4;
      }

      var4 = this.mFirstVisibleWidget;
      if (var4 != null) {
         this.mTotalSize -= var4.mListAnchors[var1].getMargin();
      }

      var4 = this.mLastVisibleWidget;
      if (var4 != null) {
         this.mTotalSize -= var4.mListAnchors[var1 + 1].getMargin();
      }

      this.mLast = var2;
      if (this.mOrientation == 0 && this.mIsRtl) {
         this.mHead = var2;
      } else {
         this.mHead = this.mFirst;
      }

      if (!this.mHasDefinedWeights || !this.mHasUndefinedWeights) {
         var3 = false;
      }

      this.mHasComplexMatchWeights = var3;
   }

   private static boolean isMatchConstraintEqualityCandidate(ConstraintWidget var0, int var1) {
      boolean var2;
      if (var0.getVisibility() == 8 || var0.mListDimensionBehaviors[var1] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var0.mResolvedMatchConstraintDefault[var1] != 0 && var0.mResolvedMatchConstraintDefault[var1] != 3) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
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
