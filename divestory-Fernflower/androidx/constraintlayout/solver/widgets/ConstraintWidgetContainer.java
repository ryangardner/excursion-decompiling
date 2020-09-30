package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer {
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

   public ConstraintWidgetContainer(int var1, int var2) {
      super(var1, var2);
   }

   public ConstraintWidgetContainer(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   private void addHorizontalChain(ConstraintWidget var1) {
      int var2 = this.mHorizontalChainsSize;
      ChainHead[] var3 = this.mHorizontalChainsArray;
      if (var2 + 1 >= var3.length) {
         this.mHorizontalChainsArray = (ChainHead[])Arrays.copyOf(var3, var3.length * 2);
      }

      this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(var1, 0, this.isRtl());
      ++this.mHorizontalChainsSize;
   }

   private void addVerticalChain(ConstraintWidget var1) {
      int var2 = this.mVerticalChainsSize;
      ChainHead[] var3 = this.mVerticalChainsArray;
      if (var2 + 1 >= var3.length) {
         this.mVerticalChainsArray = (ChainHead[])Arrays.copyOf(var3, var3.length * 2);
      }

      this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(var1, 1, this.isRtl());
      ++this.mVerticalChainsSize;
   }

   private void resetChains() {
      this.mHorizontalChainsSize = 0;
      this.mVerticalChainsSize = 0;
   }

   void addChain(ConstraintWidget var1, int var2) {
      if (var2 == 0) {
         this.addHorizontalChain(var1);
      } else if (var2 == 1) {
         this.addVerticalChain(var1);
      }

   }

   public boolean addChildrenToSolver(LinearSystem var1) {
      this.addToSolver(var1);
      int var2 = this.mChildren.size();
      int var3 = 0;

      boolean var4;
      ConstraintWidget var5;
      for(var4 = false; var3 < var2; ++var3) {
         var5 = (ConstraintWidget)this.mChildren.get(var3);
         var5.setInBarrier(0, false);
         var5.setInBarrier(1, false);
         if (var5 instanceof Barrier) {
            var4 = true;
         }
      }

      if (var4) {
         for(var3 = 0; var3 < var2; ++var3) {
            var5 = (ConstraintWidget)this.mChildren.get(var3);
            if (var5 instanceof Barrier) {
               ((Barrier)var5).markWidgets();
            }
         }
      }

      for(var3 = 0; var3 < var2; ++var3) {
         var5 = (ConstraintWidget)this.mChildren.get(var3);
         if (var5.addFirst()) {
            var5.addToSolver(var1);
         }
      }

      for(var3 = 0; var3 < var2; ++var3) {
         ConstraintWidget var6 = (ConstraintWidget)this.mChildren.get(var3);
         if (var6 instanceof ConstraintWidgetContainer) {
            ConstraintWidget.DimensionBehaviour var8 = var6.mListDimensionBehaviors[0];
            ConstraintWidget.DimensionBehaviour var7 = var6.mListDimensionBehaviors[1];
            if (var8 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            }

            if (var7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            }

            var6.addToSolver(var1);
            if (var8 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setHorizontalDimensionBehaviour(var8);
            }

            if (var7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setVerticalDimensionBehaviour(var7);
            }
         } else {
            Optimizer.checkMatchParent(this, var1, var6);
            if (!var6.addFirst()) {
               var6.addToSolver(var1);
            }
         }
      }

      if (this.mHorizontalChainsSize > 0) {
         Chain.applyChainConstraints(this, var1, 0);
      }

      if (this.mVerticalChainsSize > 0) {
         Chain.applyChainConstraints(this, var1, 1);
      }

      return true;
   }

   public void defineTerminalWidgets() {
      this.mDependencyGraph.defineTerminalWidgets(this.getHorizontalDimensionBehaviour(), this.getVerticalDimensionBehaviour());
   }

   public boolean directMeasure(boolean var1) {
      return this.mDependencyGraph.directMeasure(var1);
   }

   public boolean directMeasureSetup(boolean var1) {
      return this.mDependencyGraph.directMeasureSetup(var1);
   }

   public boolean directMeasureWithOrientation(boolean var1, int var2) {
      return this.mDependencyGraph.directMeasureWithOrientation(var1, var2);
   }

   public void fillMetrics(Metrics var1) {
      this.mMetrics = var1;
      this.mSystem.fillMetrics(var1);
   }

   public ArrayList<Guideline> getHorizontalGuidelines() {
      ArrayList var1 = new ArrayList();
      int var2 = this.mChildren.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ConstraintWidget var4 = (ConstraintWidget)this.mChildren.get(var3);
         if (var4 instanceof Guideline) {
            Guideline var5 = (Guideline)var4;
            if (var5.getOrientation() == 0) {
               var1.add(var5);
            }
         }
      }

      return var1;
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

   public String getType() {
      return "ConstraintLayout";
   }

   public ArrayList<Guideline> getVerticalGuidelines() {
      ArrayList var1 = new ArrayList();
      int var2 = this.mChildren.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ConstraintWidget var4 = (ConstraintWidget)this.mChildren.get(var3);
         if (var4 instanceof Guideline) {
            Guideline var5 = (Guideline)var4;
            if (var5.getOrientation() == 1) {
               var1.add(var5);
            }
         }
      }

      return var1;
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

   public void layout() {
      this.mX = 0;
      this.mY = 0;
      int var1 = Math.max(0, this.getWidth());
      int var2 = Math.max(0, this.getHeight());
      this.mWidthMeasuredTooSmall = false;
      this.mHeightMeasuredTooSmall = false;
      boolean var3;
      if (!this.optimizeFor(64) && !this.optimizeFor(128)) {
         var3 = false;
      } else {
         var3 = true;
      }

      this.mSystem.graphOptimizer = false;
      this.mSystem.newgraphOptimizer = false;
      if (this.mOptimizationLevel != 0 && var3) {
         this.mSystem.newgraphOptimizer = true;
      }

      ConstraintWidget.DimensionBehaviour var4 = this.mListDimensionBehaviors[1];
      ConstraintWidget.DimensionBehaviour var5 = this.mListDimensionBehaviors[0];
      ArrayList var6 = this.mChildren;
      boolean var7;
      if (this.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
         var7 = false;
      } else {
         var7 = true;
      }

      this.resetChains();
      int var8 = this.mChildren.size();

      ConstraintWidget var9;
      for(int var25 = 0; var25 < var8; ++var25) {
         var9 = (ConstraintWidget)this.mChildren.get(var25);
         if (var9 instanceof WidgetContainer) {
            ((WidgetContainer)var9).layout();
         }
      }

      int var10 = 0;
      boolean var11 = true;

      int var12;
      for(var3 = false; var11; var10 = var12) {
         var12 = var10 + 1;
         boolean var13 = var11;

         label175: {
            label174: {
               Exception var10000;
               label201: {
                  boolean var10001;
                  try {
                     this.mSystem.reset();
                  } catch (Exception var24) {
                     var10000 = var24;
                     var10001 = false;
                     break label201;
                  }

                  var13 = var11;

                  try {
                     this.resetChains();
                  } catch (Exception var23) {
                     var10000 = var23;
                     var10001 = false;
                     break label201;
                  }

                  var13 = var11;

                  try {
                     this.createObjectVariables(this.mSystem);
                  } catch (Exception var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label201;
                  }

                  for(var10 = 0; var10 < var8; ++var10) {
                     var13 = var11;

                     try {
                        ((ConstraintWidget)this.mChildren.get(var10)).createObjectVariables(this.mSystem);
                     } catch (Exception var21) {
                        var10000 = var21;
                        var10001 = false;
                        break label201;
                     }
                  }

                  var13 = var11;

                  try {
                     var11 = this.addChildrenToSolver(this.mSystem);
                  } catch (Exception var20) {
                     var10000 = var20;
                     var10001 = false;
                     break label201;
                  }

                  var13 = var11;
                  if (!var11) {
                     break label175;
                  }

                  var13 = var11;

                  try {
                     this.mSystem.minimize();
                     break label174;
                  } catch (Exception var19) {
                     var10000 = var19;
                     var10001 = false;
                  }
               }

               Exception var14 = var10000;
               var14.printStackTrace();
               PrintStream var26 = System.out;
               StringBuilder var15 = new StringBuilder();
               var15.append("EXCEPTION : ");
               var15.append(var14);
               var26.println(var15.toString());
               break label175;
            }

            var13 = var11;
         }

         if (var13) {
            this.updateChildrenFromSolver(this.mSystem, Optimizer.flags);
         } else {
            this.updateFromSolver(this.mSystem);

            for(var10 = 0; var10 < var8; ++var10) {
               ((ConstraintWidget)this.mChildren.get(var10)).updateFromSolver(this.mSystem);
            }
         }

         boolean var27;
         if (var7 && var12 < 8 && Optimizer.flags[2]) {
            int var16 = 0;
            int var17 = 0;

            for(var10 = 0; var16 < var8; ++var16) {
               var9 = (ConstraintWidget)this.mChildren.get(var16);
               var17 = Math.max(var17, var9.mX + var9.getWidth());
               var10 = Math.max(var10, var9.mY + var9.getHeight());
            }

            var17 = Math.max(this.mMinWidth, var17);
            var16 = Math.max(this.mMinHeight, var10);
            if (var5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this.getWidth() < var17) {
               this.setWidth(var17);
               this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
               var11 = true;
               var27 = true;
            } else {
               var11 = false;
               var27 = var3;
            }

            var13 = var11;
            var3 = var27;
            if (var4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var13 = var11;
               var3 = var27;
               if (this.getHeight() < var16) {
                  this.setHeight(var16);
                  this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                  var13 = true;
                  var3 = true;
               }
            }
         } else {
            var13 = false;
         }

         var10 = Math.max(this.mMinWidth, this.getWidth());
         if (var10 > this.getWidth()) {
            this.setWidth(var10);
            this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
            var13 = true;
            var3 = true;
         }

         var10 = Math.max(this.mMinHeight, this.getHeight());
         if (var10 > this.getHeight()) {
            this.setHeight(var10);
            this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
            var13 = true;
            var3 = true;
         }

         var11 = var13;
         boolean var28 = var3;
         if (!var3) {
            boolean var18 = var13;
            var27 = var3;
            if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var18 = var13;
               var27 = var3;
               if (var1 > 0) {
                  var18 = var13;
                  var27 = var3;
                  if (this.getWidth() > var1) {
                     this.mWidthMeasuredTooSmall = true;
                     this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                     this.setWidth(var1);
                     var18 = true;
                     var27 = true;
                  }
               }
            }

            var11 = var18;
            var28 = var27;
            if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var11 = var18;
               var28 = var27;
               if (var2 > 0) {
                  var11 = var18;
                  var28 = var27;
                  if (this.getHeight() > var2) {
                     this.mHeightMeasuredTooSmall = true;
                     this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                     this.setHeight(var2);
                     var11 = true;
                     var3 = true;
                     continue;
                  }
               }
            }
         }

         var3 = var28;
      }

      this.mChildren = (ArrayList)var6;
      if (var3) {
         this.mListDimensionBehaviors[0] = var5;
         this.mListDimensionBehaviors[1] = var4;
      }

      this.resetSolverVariables(this.mSystem.getCache());
   }

   public long measure(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this.mPaddingLeft = var8;
      this.mPaddingTop = var9;
      return this.mBasicMeasureSolver.solverMeasure(this, var1, var8, var9, var2, var3, var4, var5, var6, var7);
   }

   public boolean optimizeFor(int var1) {
      boolean var2;
      if ((this.mOptimizationLevel & var1) == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void reset() {
      this.mSystem.reset();
      this.mPaddingLeft = 0;
      this.mPaddingRight = 0;
      this.mPaddingTop = 0;
      this.mPaddingBottom = 0;
      this.mSkipSolver = false;
      super.reset();
   }

   public void setMeasurer(BasicMeasure.Measurer var1) {
      this.mMeasurer = var1;
      this.mDependencyGraph.setMeasurer(var1);
   }

   public void setOptimizationLevel(int var1) {
      this.mOptimizationLevel = var1;
      LinearSystem.OPTIMIZED_ENGINE = Optimizer.enabled(var1, 256);
   }

   public void setPadding(int var1, int var2, int var3, int var4) {
      this.mPaddingLeft = var1;
      this.mPaddingTop = var2;
      this.mPaddingRight = var3;
      this.mPaddingBottom = var4;
   }

   public void setRtl(boolean var1) {
      this.mIsRtl = var1;
   }

   public void updateChildrenFromSolver(LinearSystem var1, boolean[] var2) {
      int var3 = 0;
      var2[2] = false;
      this.updateFromSolver(var1);

      for(int var4 = this.mChildren.size(); var3 < var4; ++var3) {
         ((ConstraintWidget)this.mChildren.get(var3)).updateFromSolver(var1);
      }

   }

   public void updateFromRuns(boolean var1, boolean var2) {
      super.updateFromRuns(var1, var2);
      int var3 = this.mChildren.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         ((ConstraintWidget)this.mChildren.get(var4)).updateFromRuns(var1, var2);
      }

   }

   public void updateHierarchy() {
      this.mBasicMeasureSolver.updateHierarchy(this);
   }
}
