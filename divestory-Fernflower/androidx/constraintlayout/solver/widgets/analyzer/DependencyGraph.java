package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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

   public DependencyGraph(ConstraintWidgetContainer var1) {
      this.container = var1;
      this.mContainer = var1;
   }

   private void applyGroup(DependencyNode var1, int var2, int var3, DependencyNode var4, ArrayList<RunGroup> var5, RunGroup var6) {
      WidgetRun var7 = var1.run;
      if (var7.runGroup == null && var7 != this.container.horizontalRun && var7 != this.container.verticalRun) {
         RunGroup var11 = var6;
         if (var6 == null) {
            var11 = new RunGroup(var7, var3);
            var5.add(var11);
         }

         var7.runGroup = var11;
         var11.add(var7);
         Iterator var8 = var7.start.dependencies.iterator();

         Dependency var12;
         while(var8.hasNext()) {
            var12 = (Dependency)var8.next();
            if (var12 instanceof DependencyNode) {
               this.applyGroup((DependencyNode)var12, var2, 0, var4, var5, var11);
            }
         }

         Iterator var13 = var7.end.dependencies.iterator();

         while(var13.hasNext()) {
            Dependency var15 = (Dependency)var13.next();
            if (var15 instanceof DependencyNode) {
               this.applyGroup((DependencyNode)var15, var2, 1, var4, var5, var11);
            }
         }

         if (var2 == 1 && var7 instanceof VerticalWidgetRun) {
            var8 = ((VerticalWidgetRun)var7).baseline.dependencies.iterator();

            while(var8.hasNext()) {
               var12 = (Dependency)var8.next();
               if (var12 instanceof DependencyNode) {
                  this.applyGroup((DependencyNode)var12, var2, 2, var4, var5, var11);
               }
            }
         }

         DependencyNode var16;
         for(var13 = var7.start.targets.iterator(); var13.hasNext(); this.applyGroup(var16, var2, 0, var4, var5, var11)) {
            var16 = (DependencyNode)var13.next();
            if (var16 == var4) {
               var11.dual = true;
            }
         }

         for(var13 = var7.end.targets.iterator(); var13.hasNext(); this.applyGroup(var16, var2, 1, var4, var5, var11)) {
            var16 = (DependencyNode)var13.next();
            if (var16 == var4) {
               var11.dual = true;
            }
         }

         if (var2 == 1 && var7 instanceof VerticalWidgetRun) {
            var13 = ((VerticalWidgetRun)var7).baseline.targets.iterator();

            while(var13.hasNext()) {
               DependencyNode var14 = (DependencyNode)var13.next();

               try {
                  this.applyGroup(var14, var2, 2, var4, var5, var11);
               } finally {
                  ;
               }
            }
         }
      }

   }

   private boolean basicMeasureWidgets(ConstraintWidgetContainer var1) {
      Iterator var2 = var1.mChildren.iterator();

      while(true) {
         while(var2.hasNext()) {
            ConstraintWidget var3 = (ConstraintWidget)var2.next();
            ConstraintWidget.DimensionBehaviour var4 = var3.mListDimensionBehaviors[0];
            ConstraintWidget.DimensionBehaviour var5 = var3.mListDimensionBehaviors[1];
            if (var3.getVisibility() == 8) {
               var3.measured = true;
            } else {
               if (var3.mMatchConstraintPercentWidth < 1.0F && var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var3.mMatchConstraintDefaultWidth = 2;
               }

               if (var3.mMatchConstraintPercentHeight < 1.0F && var5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var3.mMatchConstraintDefaultHeight = 2;
               }

               if (var3.getDimensionRatio() > 0.0F) {
                  if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (var5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var5 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                     var3.mMatchConstraintDefaultWidth = 3;
                  } else if (var5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (var4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var4 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                     var3.mMatchConstraintDefaultHeight = 3;
                  } else if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     if (var3.mMatchConstraintDefaultWidth == 0) {
                        var3.mMatchConstraintDefaultWidth = 3;
                     }

                     if (var3.mMatchConstraintDefaultHeight == 0) {
                        var3.mMatchConstraintDefaultHeight = 3;
                     }
                  }
               }

               ConstraintWidget.DimensionBehaviour var6 = var4;
               if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var6 = var4;
                  if (var3.mMatchConstraintDefaultWidth == 1) {
                     label206: {
                        if (var3.mLeft.mTarget != null) {
                           var6 = var4;
                           if (var3.mRight.mTarget != null) {
                              break label206;
                           }
                        }

                        var6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                     }
                  }
               }

               var4 = var6;
               if (var5 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var3.mMatchConstraintDefaultHeight != 1 || var3.mTop.mTarget != null && var3.mBottom.mTarget != null) {
                  var6 = var5;
               } else {
                  var6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
               }

               var3.horizontalRun.dimensionBehavior = var4;
               var3.horizontalRun.matchConstraintsType = var3.mMatchConstraintDefaultWidth;
               var3.verticalRun.dimensionBehavior = var6;
               var3.verticalRun.matchConstraintsType = var3.mMatchConstraintDefaultHeight;
               int var7;
               int var8;
               if ((var4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || var4 == ConstraintWidget.DimensionBehaviour.FIXED || var4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (var6 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || var6 == ConstraintWidget.DimensionBehaviour.FIXED || var6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
                  var7 = var3.getWidth();
                  int var11;
                  if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                     var11 = var1.getWidth();
                     var7 = var3.mLeft.mMargin;
                     var8 = var3.mRight.mMargin;
                     var4 = ConstraintWidget.DimensionBehaviour.FIXED;
                     var7 = var11 - var7 - var8;
                  }

                  var8 = var3.getHeight();
                  if (var6 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                     var11 = var1.getHeight();
                     int var12 = var3.mTop.mMargin;
                     var8 = var3.mBottom.mMargin;
                     var6 = ConstraintWidget.DimensionBehaviour.FIXED;
                     var8 = var11 - var12 - var8;
                  }

                  this.measure(var3, var4, var7, var6, var8);
                  var3.horizontalRun.dimension.resolve(var3.getWidth());
                  var3.verticalRun.dimension.resolve(var3.getHeight());
                  var3.measured = true;
               } else {
                  if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (var6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var6 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                     if (var3.mMatchConstraintDefaultWidth == 3) {
                        if (var6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                           this.measure(var3, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        }

                        var7 = var3.getHeight();
                        var8 = (int)((float)var7 * var3.mDimensionRatio + 0.5F);
                        this.measure(var3, ConstraintWidget.DimensionBehaviour.FIXED, var8, ConstraintWidget.DimensionBehaviour.FIXED, var7);
                        var3.horizontalRun.dimension.resolve(var3.getWidth());
                        var3.verticalRun.dimension.resolve(var3.getHeight());
                        var3.measured = true;
                        continue;
                     }

                     if (var3.mMatchConstraintDefaultWidth == 1) {
                        this.measure(var3, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, var6, 0);
                        var3.horizontalRun.dimension.wrapValue = var3.getWidth();
                        continue;
                     }

                     if (var3.mMatchConstraintDefaultWidth == 2) {
                        if (var1.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || var1.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                           var7 = (int)(var3.mMatchConstraintPercentWidth * (float)var1.getWidth() + 0.5F);
                           var8 = var3.getHeight();
                           this.measure(var3, ConstraintWidget.DimensionBehaviour.FIXED, var7, var6, var8);
                           var3.horizontalRun.dimension.resolve(var3.getWidth());
                           var3.verticalRun.dimension.resolve(var3.getHeight());
                           var3.measured = true;
                           continue;
                        }
                     } else if (var3.mListAnchors[0].mTarget == null || var3.mListAnchors[1].mTarget == null) {
                        this.measure(var3, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, var6, 0);
                        var3.horizontalRun.dimension.resolve(var3.getWidth());
                        var3.verticalRun.dimension.resolve(var3.getHeight());
                        var3.measured = true;
                        continue;
                     }
                  }

                  float var9;
                  float var10;
                  if (var6 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && (var4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var4 == ConstraintWidget.DimensionBehaviour.FIXED)) {
                     if (var3.mMatchConstraintDefaultHeight == 3) {
                        if (var4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                           this.measure(var3, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        }

                        var7 = var3.getWidth();
                        var9 = var3.mDimensionRatio;
                        var10 = var9;
                        if (var3.getDimensionRatioSide() == -1) {
                           var10 = 1.0F / var9;
                        }

                        var8 = (int)((float)var7 * var10 + 0.5F);
                        this.measure(var3, ConstraintWidget.DimensionBehaviour.FIXED, var7, ConstraintWidget.DimensionBehaviour.FIXED, var8);
                        var3.horizontalRun.dimension.resolve(var3.getWidth());
                        var3.verticalRun.dimension.resolve(var3.getHeight());
                        var3.measured = true;
                        continue;
                     }

                     if (var3.mMatchConstraintDefaultHeight == 1) {
                        this.measure(var3, var4, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        var3.verticalRun.dimension.wrapValue = var3.getHeight();
                        continue;
                     }

                     if (var3.mMatchConstraintDefaultHeight == 2) {
                        if (var1.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || var1.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                           var10 = var3.mMatchConstraintPercentHeight;
                           var8 = var3.getWidth();
                           var7 = (int)(var10 * (float)var1.getHeight() + 0.5F);
                           this.measure(var3, var4, var8, ConstraintWidget.DimensionBehaviour.FIXED, var7);
                           var3.horizontalRun.dimension.resolve(var3.getWidth());
                           var3.verticalRun.dimension.resolve(var3.getHeight());
                           var3.measured = true;
                           continue;
                        }
                     } else if (var3.mListAnchors[2].mTarget == null || var3.mListAnchors[3].mTarget == null) {
                        this.measure(var3, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, var6, 0);
                        var3.horizontalRun.dimension.resolve(var3.getWidth());
                        var3.verticalRun.dimension.resolve(var3.getHeight());
                        var3.measured = true;
                        continue;
                     }
                  }

                  if (var4 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var6 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     if (var3.mMatchConstraintDefaultWidth != 1 && var3.mMatchConstraintDefaultHeight != 1) {
                        if (var3.mMatchConstraintDefaultHeight == 2 && var3.mMatchConstraintDefaultWidth == 2 && (var1.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || var1.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) && (var1.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || var1.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED)) {
                           var9 = var3.mMatchConstraintPercentWidth;
                           var10 = var3.mMatchConstraintPercentHeight;
                           var7 = (int)(var9 * (float)var1.getWidth() + 0.5F);
                           var8 = (int)(var10 * (float)var1.getHeight() + 0.5F);
                           this.measure(var3, ConstraintWidget.DimensionBehaviour.FIXED, var7, ConstraintWidget.DimensionBehaviour.FIXED, var8);
                           var3.horizontalRun.dimension.resolve(var3.getWidth());
                           var3.verticalRun.dimension.resolve(var3.getHeight());
                           var3.measured = true;
                        }
                     } else {
                        this.measure(var3, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
                        var3.horizontalRun.dimension.wrapValue = var3.getWidth();
                        var3.verticalRun.dimension.wrapValue = var3.getHeight();
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private int computeWrap(ConstraintWidgetContainer var1, int var2) {
      int var3 = this.mGroups.size();
      long var4 = 0L;

      for(int var6 = 0; var6 < var3; ++var6) {
         var4 = Math.max(var4, ((RunGroup)this.mGroups.get(var6)).computeWrapSize(var1, var2));
      }

      return (int)var4;
   }

   private void displayGraph() {
      Iterator var1 = this.mRuns.iterator();

      String var2;
      for(var2 = "digraph {\n"; var1.hasNext(); var2 = this.generateDisplayGraph((WidgetRun)var1.next(), var2)) {
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append("\n}\n");
      String var5 = var4.toString();
      PrintStream var6 = System.out;
      StringBuilder var3 = new StringBuilder();
      var3.append("content:<<\n");
      var3.append(var5);
      var3.append("\n>>");
      var6.println(var3.toString());
   }

   private void findGroup(WidgetRun var1, int var2, ArrayList<RunGroup> var3) {
      Iterator var4 = var1.start.dependencies.iterator();

      Dependency var5;
      while(var4.hasNext()) {
         var5 = (Dependency)var4.next();
         if (var5 instanceof DependencyNode) {
            this.applyGroup((DependencyNode)var5, var2, 0, var1.end, var3, (RunGroup)null);
         } else if (var5 instanceof WidgetRun) {
            this.applyGroup(((WidgetRun)var5).start, var2, 0, var1.end, var3, (RunGroup)null);
         }
      }

      var4 = var1.end.dependencies.iterator();

      while(var4.hasNext()) {
         var5 = (Dependency)var4.next();
         if (var5 instanceof DependencyNode) {
            this.applyGroup((DependencyNode)var5, var2, 1, var1.start, var3, (RunGroup)null);
         } else if (var5 instanceof WidgetRun) {
            this.applyGroup(((WidgetRun)var5).end, var2, 1, var1.start, var3, (RunGroup)null);
         }
      }

      if (var2 == 1) {
         Iterator var6 = ((VerticalWidgetRun)var1).baseline.dependencies.iterator();

         while(var6.hasNext()) {
            Dependency var7 = (Dependency)var6.next();
            if (var7 instanceof DependencyNode) {
               this.applyGroup((DependencyNode)var7, var2, 2, (DependencyNode)null, var3, (RunGroup)null);
            }
         }
      }

   }

   private String generateChainDisplayGraph(ChainRun var1, String var2) {
      int var3 = var1.orientation;
      StringBuilder var4 = new StringBuilder();
      var4.append("cluster_");
      var4.append(var1.widget.getDebugName());
      String var10 = var4.toString();
      StringBuilder var5;
      if (var3 == 0) {
         var5 = new StringBuilder();
         var5.append(var10);
         var5.append("_h");
         var10 = var5.toString();
      } else {
         var5 = new StringBuilder();
         var5.append(var10);
         var5.append("_v");
         var10 = var5.toString();
      }

      var5 = new StringBuilder();
      var5.append("subgraph ");
      var5.append(var10);
      var5.append(" {\n");
      var10 = var5.toString();
      Iterator var6 = var1.widgets.iterator();

      WidgetRun var7;
      String var9;
      String var11;
      for(var9 = ""; var6.hasNext(); var9 = this.generateDisplayGraph(var7, var9)) {
         var7 = (WidgetRun)var6.next();
         var11 = var7.widget.getDebugName();
         StringBuilder var8;
         if (var3 == 0) {
            var8 = new StringBuilder();
            var8.append(var11);
            var8.append("_HORIZONTAL");
            var11 = var8.toString();
         } else {
            var8 = new StringBuilder();
            var8.append(var11);
            var8.append("_VERTICAL");
            var11 = var8.toString();
         }

         var8 = new StringBuilder();
         var8.append(var10);
         var8.append(var11);
         var8.append(";\n");
         var10 = var8.toString();
      }

      var5 = new StringBuilder();
      var5.append(var10);
      var5.append("}\n");
      var11 = var5.toString();
      var4 = new StringBuilder();
      var4.append(var2);
      var4.append(var9);
      var4.append(var11);
      return var4.toString();
   }

   private String generateDisplayGraph(WidgetRun var1, String var2) {
      DependencyNode var3 = var1.start;
      DependencyNode var4 = var1.end;
      if (!(var1 instanceof HelperReferences) && var3.dependencies.isEmpty() && var4.dependencies.isEmpty() & var3.targets.isEmpty() && var4.targets.isEmpty()) {
         return var2;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append(var2);
         var5.append(this.nodeDefinition(var1));
         var2 = var5.toString();
         boolean var6 = this.isCenteredConnection(var3, var4);
         var2 = this.generateDisplayNode(var4, var6, this.generateDisplayNode(var3, var6, var2));
         boolean var7 = var1 instanceof VerticalWidgetRun;
         String var12 = var2;
         if (var7) {
            var12 = this.generateDisplayNode(((VerticalWidgetRun)var1).baseline, var6, var2);
         }

         ConstraintWidget.DimensionBehaviour var8;
         StringBuilder var9;
         String var10;
         if (!(var1 instanceof HorizontalWidgetRun)) {
            var6 = var1 instanceof ChainRun;
            if (!var6 || ((ChainRun)var1).orientation != 0) {
               if (!var7) {
                  var2 = var12;
                  if (!var6) {
                     return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
                  }

                  var2 = var12;
                  if (((ChainRun)var1).orientation != 1) {
                     return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
                  }
               }

               var8 = var1.widget.getVerticalDimensionBehaviour();
               if (var8 != ConstraintWidget.DimensionBehaviour.FIXED && var8 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var2 = var12;
                  if (var8 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var2 = var12;
                     if (var1.widget.getDimensionRatio() > 0.0F) {
                        var1.widget.getDebugName();
                        var2 = var12;
                     }

                     return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
                  }

                  return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
               } else {
                  if (!var3.targets.isEmpty() && var4.targets.isEmpty()) {
                     var9 = new StringBuilder();
                     var9.append("\n");
                     var9.append(var4.name());
                     var9.append(" -> ");
                     var9.append(var3.name());
                     var9.append("\n");
                     var10 = var9.toString();
                     var9 = new StringBuilder();
                     var9.append(var12);
                     var9.append(var10);
                     var2 = var9.toString();
                  } else {
                     var2 = var12;
                     if (var3.targets.isEmpty()) {
                        var2 = var12;
                        if (!var4.targets.isEmpty()) {
                           var9 = new StringBuilder();
                           var9.append("\n");
                           var9.append(var3.name());
                           var9.append(" -> ");
                           var9.append(var4.name());
                           var9.append("\n");
                           var10 = var9.toString();
                           var9 = new StringBuilder();
                           var9.append(var12);
                           var9.append(var10);
                           var2 = var9.toString();
                        }

                        return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
                     }
                  }

                  return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
               }
            }
         }

         var8 = var1.widget.getHorizontalDimensionBehaviour();
         if (var8 != ConstraintWidget.DimensionBehaviour.FIXED && var8 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            var2 = var12;
            if (var8 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               var2 = var12;
               if (var1.widget.getDimensionRatio() > 0.0F) {
                  var1.widget.getDebugName();
                  var2 = var12;
               }
            }
         } else if (!var3.targets.isEmpty() && var4.targets.isEmpty()) {
            var9 = new StringBuilder();
            var9.append("\n");
            var9.append(var4.name());
            var9.append(" -> ");
            var9.append(var3.name());
            var9.append("\n");
            var2 = var9.toString();
            StringBuilder var11 = new StringBuilder();
            var11.append(var12);
            var11.append(var2);
            var2 = var11.toString();
         } else {
            var2 = var12;
            if (var3.targets.isEmpty()) {
               var2 = var12;
               if (!var4.targets.isEmpty()) {
                  var9 = new StringBuilder();
                  var9.append("\n");
                  var9.append(var3.name());
                  var9.append(" -> ");
                  var9.append(var4.name());
                  var9.append("\n");
                  var10 = var9.toString();
                  var9 = new StringBuilder();
                  var9.append(var12);
                  var9.append(var10);
                  var2 = var9.toString();
               }
            }
         }

         return var1 instanceof ChainRun ? this.generateChainDisplayGraph((ChainRun)var1, var2) : var2;
      }
   }

   private String generateDisplayNode(DependencyNode var1, boolean var2, String var3) {
      Iterator var4 = var1.targets.iterator();

      String var5;
      StringBuilder var9;
      for(var5 = var3; var4.hasNext(); var5 = var9.toString()) {
         StringBuilder var6;
         String var10;
         label37: {
            DependencyNode var8 = (DependencyNode)var4.next();
            var6 = new StringBuilder();
            var6.append("\n");
            var6.append(var1.name());
            String var7 = var6.toString();
            var6 = new StringBuilder();
            var6.append(var7);
            var6.append(" -> ");
            var6.append(var8.name());
            var10 = var6.toString();
            if (var1.margin <= 0 && !var2) {
               var3 = var10;
               if (!(var1.run instanceof HelperReferences)) {
                  break label37;
               }
            }

            var9 = new StringBuilder();
            var9.append(var10);
            var9.append("[");
            var10 = var9.toString();
            var3 = var10;
            if (var1.margin > 0) {
               var9 = new StringBuilder();
               var9.append(var10);
               var9.append("label=\"");
               var9.append(var1.margin);
               var9.append("\"");
               var10 = var9.toString();
               var3 = var10;
               if (var2) {
                  var9 = new StringBuilder();
                  var9.append(var10);
                  var9.append(",");
                  var3 = var9.toString();
               }
            }

            var10 = var3;
            if (var2) {
               var6 = new StringBuilder();
               var6.append(var3);
               var6.append(" style=dashed ");
               var10 = var6.toString();
            }

            var3 = var10;
            if (var1.run instanceof HelperReferences) {
               var9 = new StringBuilder();
               var9.append(var10);
               var9.append(" style=bold,color=gray ");
               var3 = var9.toString();
            }

            var6 = new StringBuilder();
            var6.append(var3);
            var6.append("]");
            var3 = var6.toString();
         }

         var6 = new StringBuilder();
         var6.append(var3);
         var6.append("\n");
         var10 = var6.toString();
         var9 = new StringBuilder();
         var9.append(var5);
         var9.append(var10);
      }

      return var5;
   }

   private boolean isCenteredConnection(DependencyNode var1, DependencyNode var2) {
      Iterator var3 = var1.targets.iterator();
      boolean var4 = false;
      int var5 = 0;

      while(var3.hasNext()) {
         if ((DependencyNode)var3.next() != var2) {
            ++var5;
         }
      }

      Iterator var8 = var2.targets.iterator();
      int var6 = 0;

      while(var8.hasNext()) {
         if ((DependencyNode)var8.next() != var1) {
            ++var6;
         }
      }

      boolean var7 = var4;
      if (var5 > 0) {
         var7 = var4;
         if (var6 > 0) {
            var7 = true;
         }
      }

      return var7;
   }

   private void measure(ConstraintWidget var1, ConstraintWidget.DimensionBehaviour var2, int var3, ConstraintWidget.DimensionBehaviour var4, int var5) {
      this.mMeasure.horizontalBehavior = var2;
      this.mMeasure.verticalBehavior = var4;
      this.mMeasure.horizontalDimension = var3;
      this.mMeasure.verticalDimension = var5;
      this.mMeasurer.measure(var1, this.mMeasure);
      var1.setWidth(this.mMeasure.measuredWidth);
      var1.setHeight(this.mMeasure.measuredHeight);
      var1.setHasBaseline(this.mMeasure.measuredHasBaseline);
      var1.setBaselineDistance(this.mMeasure.measuredBaseline);
   }

   private String nodeDefinition(WidgetRun var1) {
      boolean var2 = var1 instanceof VerticalWidgetRun;
      String var3 = var1.widget.getDebugName();
      ConstraintWidget var4 = var1.widget;
      ConstraintWidget.DimensionBehaviour var5;
      if (!var2) {
         var5 = var4.getHorizontalDimensionBehaviour();
      } else {
         var5 = var4.getVerticalDimensionBehaviour();
      }

      RunGroup var6 = var1.runGroup;
      StringBuilder var10;
      String var11;
      if (!var2) {
         var10 = new StringBuilder();
         var10.append(var3);
         var10.append("_HORIZONTAL");
         var11 = var10.toString();
      } else {
         var10 = new StringBuilder();
         var10.append(var3);
         var10.append("_VERTICAL");
         var11 = var10.toString();
      }

      StringBuilder var7 = new StringBuilder();
      var7.append(var11);
      var7.append(" [shape=none, label=<");
      String var14 = var7.toString();
      var10 = new StringBuilder();
      var10.append(var14);
      var10.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">");
      var14 = var10.toString();
      var10 = new StringBuilder();
      var10.append(var14);
      var10.append("  <TR>");
      var11 = var10.toString();
      if (!var2) {
         var7 = new StringBuilder();
         var7.append(var11);
         var7.append("    <TD ");
         var14 = var7.toString();
         var11 = var14;
         if (var1.start.resolved) {
            var10 = new StringBuilder();
            var10.append(var14);
            var10.append(" BGCOLOR=\"green\"");
            var11 = var10.toString();
         }

         var7 = new StringBuilder();
         var7.append(var11);
         var7.append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>");
         var11 = var7.toString();
      } else {
         var7 = new StringBuilder();
         var7.append(var11);
         var7.append("    <TD ");
         var14 = var7.toString();
         var11 = var14;
         if (var1.start.resolved) {
            var10 = new StringBuilder();
            var10.append(var14);
            var10.append(" BGCOLOR=\"green\"");
            var11 = var10.toString();
         }

         var7 = new StringBuilder();
         var7.append(var11);
         var7.append(" PORT=\"TOP\" BORDER=\"1\">T</TD>");
         var11 = var7.toString();
      }

      var7 = new StringBuilder();
      var7.append(var11);
      var7.append("    <TD BORDER=\"1\" ");
      var14 = var7.toString();
      if (var1.dimension.resolved && !var1.widget.measured) {
         var10 = new StringBuilder();
         var10.append(var14);
         var10.append(" BGCOLOR=\"green\" ");
         var11 = var10.toString();
      } else if (var1.dimension.resolved && var1.widget.measured) {
         var10 = new StringBuilder();
         var10.append(var14);
         var10.append(" BGCOLOR=\"lightgray\" ");
         var11 = var10.toString();
      } else {
         var11 = var14;
         if (!var1.dimension.resolved) {
            var11 = var14;
            if (var1.widget.measured) {
               var10 = new StringBuilder();
               var10.append(var14);
               var10.append(" BGCOLOR=\"yellow\" ");
               var11 = var10.toString();
            }
         }
      }

      var14 = var11;
      StringBuilder var12;
      if (var5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var12 = new StringBuilder();
         var12.append(var11);
         var12.append("style=\"dashed\"");
         var14 = var12.toString();
      }

      if (var6 != null) {
         var10 = new StringBuilder();
         var10.append(" [");
         var10.append(var6.groupIndex + 1);
         var10.append("/");
         var10.append(RunGroup.index);
         var10.append("]");
         var11 = var10.toString();
      } else {
         var11 = "";
      }

      var12 = new StringBuilder();
      var12.append(var14);
      var12.append(">");
      var12.append(var3);
      var12.append(var11);
      var12.append(" </TD>");
      var11 = var12.toString();
      StringBuilder var8;
      String var9;
      String var13;
      if (!var2) {
         var12 = new StringBuilder();
         var12.append(var11);
         var12.append("    <TD ");
         var13 = var12.toString();
         var11 = var13;
         if (var1.end.resolved) {
            var8 = new StringBuilder();
            var8.append(var13);
            var8.append(" BGCOLOR=\"green\"");
            var11 = var8.toString();
         }

         var8 = new StringBuilder();
         var8.append(var11);
         var8.append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>");
         var9 = var8.toString();
      } else {
         var12 = new StringBuilder();
         var12.append(var11);
         var12.append("    <TD ");
         var13 = var12.toString();
         var11 = var13;
         if (var2) {
            var11 = var13;
            if (((VerticalWidgetRun)var1).baseline.resolved) {
               var10 = new StringBuilder();
               var10.append(var13);
               var10.append(" BGCOLOR=\"green\"");
               var11 = var10.toString();
            }
         }

         var12 = new StringBuilder();
         var12.append(var11);
         var12.append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>");
         var11 = var12.toString();
         var12 = new StringBuilder();
         var12.append(var11);
         var12.append("    <TD ");
         var13 = var12.toString();
         var11 = var13;
         if (var1.end.resolved) {
            var8 = new StringBuilder();
            var8.append(var13);
            var8.append(" BGCOLOR=\"green\"");
            var11 = var8.toString();
         }

         var8 = new StringBuilder();
         var8.append(var11);
         var8.append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>");
         var9 = var8.toString();
      }

      var10 = new StringBuilder();
      var10.append(var9);
      var10.append("  </TR></TABLE>");
      var9 = var10.toString();
      var10 = new StringBuilder();
      var10.append(var9);
      var10.append(">];\n");
      return var10.toString();
   }

   public void buildGraph() {
      this.buildGraph(this.mRuns);
      this.mGroups.clear();
      RunGroup.index = 0;
      this.findGroup(this.container.horizontalRun, 0, this.mGroups);
      this.findGroup(this.container.verticalRun, 1, this.mGroups);
      this.mNeedBuildGraph = false;
   }

   public void buildGraph(ArrayList<WidgetRun> var1) {
      var1.clear();
      this.mContainer.horizontalRun.clear();
      this.mContainer.verticalRun.clear();
      var1.add(this.mContainer.horizontalRun);
      var1.add(this.mContainer.verticalRun);
      Iterator var2 = this.mContainer.mChildren.iterator();
      HashSet var3 = null;

      while(var2.hasNext()) {
         ConstraintWidget var4 = (ConstraintWidget)var2.next();
         if (var4 instanceof Guideline) {
            var1.add(new GuidelineReference(var4));
         } else {
            HashSet var5;
            if (var4.isInHorizontalChain()) {
               if (var4.horizontalChainRun == null) {
                  var4.horizontalChainRun = new ChainRun(var4, 0);
               }

               var5 = var3;
               if (var3 == null) {
                  var5 = new HashSet();
               }

               var5.add(var4.horizontalChainRun);
               var3 = var5;
            } else {
               var1.add(var4.horizontalRun);
            }

            if (var4.isInVerticalChain()) {
               if (var4.verticalChainRun == null) {
                  var4.verticalChainRun = new ChainRun(var4, 1);
               }

               var5 = var3;
               if (var3 == null) {
                  var5 = new HashSet();
               }

               var5.add(var4.verticalChainRun);
            } else {
               var1.add(var4.verticalRun);
               var5 = var3;
            }

            var3 = var5;
            if (var4 instanceof HelperWidget) {
               var1.add(new HelperReferences(var4));
               var3 = var5;
            }
         }
      }

      if (var3 != null) {
         var1.addAll(var3);
      }

      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         ((WidgetRun)var7.next()).clear();
      }

      var7 = var1.iterator();

      while(var7.hasNext()) {
         WidgetRun var6 = (WidgetRun)var7.next();
         if (var6.widget != this.mContainer) {
            var6.apply();
         }
      }

   }

   public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour var1, ConstraintWidget.DimensionBehaviour var2) {
      if (this.mNeedBuildGraph) {
         this.buildGraph();
         Iterator var3 = this.container.mChildren.iterator();
         boolean var4 = false;

         while(var3.hasNext()) {
            ConstraintWidget var5 = (ConstraintWidget)var3.next();
            var5.isTerminalWidget[0] = true;
            var5.isTerminalWidget[1] = true;
            if (var5 instanceof Barrier) {
               var4 = true;
            }
         }

         boolean var6;
         boolean var7;
         RunGroup var8;
         if (!var4) {
            for(Iterator var9 = this.mGroups.iterator(); var9.hasNext(); var8.defineTerminalWidgets(var6, var7)) {
               var8 = (RunGroup)var9.next();
               if (var1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (var2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var7 = true;
               } else {
                  var7 = false;
               }
            }
         }
      }

   }

   public boolean directMeasure(boolean var1) {
      boolean var2 = true;
      boolean var3 = var1 & true;
      if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
         Iterator var4 = this.container.mChildren.iterator();

         while(var4.hasNext()) {
            ConstraintWidget var5 = (ConstraintWidget)var4.next();
            var5.measured = false;
            var5.horizontalRun.reset();
            var5.verticalRun.reset();
         }

         this.container.measured = false;
         this.container.horizontalRun.reset();
         this.container.verticalRun.reset();
         this.mNeedRedoMeasures = false;
      }

      if (this.basicMeasureWidgets(this.mContainer)) {
         return false;
      } else {
         this.container.setX(0);
         this.container.setY(0);
         ConstraintWidget.DimensionBehaviour var12 = this.container.getDimensionBehaviour(0);
         ConstraintWidget.DimensionBehaviour var11 = this.container.getDimensionBehaviour(1);
         if (this.mNeedBuildGraph) {
            this.buildGraph();
         }

         int var6 = this.container.getX();
         int var7 = this.container.getY();
         this.container.horizontalRun.start.resolve(var6);
         this.container.verticalRun.start.resolve(var7);
         this.measureWidgets();
         boolean var8;
         Iterator var9;
         if (var12 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var11 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            var8 = var3;
            if (var3) {
               var9 = this.mRuns.iterator();

               while(true) {
                  var8 = var3;
                  if (!var9.hasNext()) {
                     break;
                  }

                  if (!((WidgetRun)var9.next()).supportsWrapComputation()) {
                     var8 = false;
                     break;
                  }
               }
            }

            ConstraintWidgetContainer var14;
            if (var8 && var12 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
               var14 = this.container;
               var14.setWidth(this.computeWrap(var14, 0));
               this.container.horizontalRun.dimension.resolve(this.container.getWidth());
            }

            if (var8 && var11 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
               var14 = this.container;
               var14.setHeight(this.computeWrap(var14, 1));
               this.container.verticalRun.dimension.resolve(this.container.getHeight());
            }
         }

         if (this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.FIXED && this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            var8 = false;
         } else {
            int var13 = this.container.getWidth() + var6;
            this.container.horizontalRun.end.resolve(var13);
            this.container.horizontalRun.dimension.resolve(var13 - var6);
            this.measureWidgets();
            if (this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED || this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
               var13 = this.container.getHeight() + var7;
               this.container.verticalRun.end.resolve(var13);
               this.container.verticalRun.dimension.resolve(var13 - var7);
            }

            this.measureWidgets();
            var8 = true;
         }

         var9 = this.mRuns.iterator();

         while(true) {
            WidgetRun var10;
            do {
               if (!var9.hasNext()) {
                  var9 = this.mRuns.iterator();

                  while(true) {
                     var1 = var2;
                     if (!var9.hasNext()) {
                        break;
                     }

                     var10 = (WidgetRun)var9.next();
                     if ((var8 || var10.widget != this.container) && (!var10.start.resolved || !var10.end.resolved && !(var10 instanceof GuidelineReference) || !var10.dimension.resolved && !(var10 instanceof ChainRun) && !(var10 instanceof GuidelineReference))) {
                        var1 = false;
                        break;
                     }
                  }

                  this.container.setHorizontalDimensionBehaviour(var12);
                  this.container.setVerticalDimensionBehaviour(var11);
                  return var1;
               }

               var10 = (WidgetRun)var9.next();
            } while(var10.widget == this.container && !var10.resolved);

            var10.applyToWidget();
         }
      }
   }

   public boolean directMeasureSetup(boolean var1) {
      if (this.mNeedBuildGraph) {
         Iterator var2 = this.container.mChildren.iterator();

         while(var2.hasNext()) {
            ConstraintWidget var3 = (ConstraintWidget)var2.next();
            var3.measured = false;
            var3.horizontalRun.dimension.resolved = false;
            var3.horizontalRun.resolved = false;
            var3.horizontalRun.reset();
            var3.verticalRun.dimension.resolved = false;
            var3.verticalRun.resolved = false;
            var3.verticalRun.reset();
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
      } else {
         this.container.setX(0);
         this.container.setY(0);
         this.container.horizontalRun.start.resolve(0);
         this.container.verticalRun.start.resolve(0);
         return true;
      }
   }

   public boolean directMeasureWithOrientation(boolean var1, int var2) {
      boolean var3 = true;
      boolean var4 = var1 & true;
      ConstraintWidget.DimensionBehaviour var5 = this.container.getDimensionBehaviour(0);
      ConstraintWidget.DimensionBehaviour var6 = this.container.getDimensionBehaviour(1);
      int var7 = this.container.getX();
      int var8 = this.container.getY();
      boolean var10;
      if (var4 && (var5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
         Iterator var9 = this.mRuns.iterator();

         while(true) {
            var10 = var4;
            if (!var9.hasNext()) {
               break;
            }

            WidgetRun var11 = (WidgetRun)var9.next();
            if (var11.orientation == var2 && !var11.supportsWrapComputation()) {
               var10 = false;
               break;
            }
         }

         ConstraintWidgetContainer var12;
         if (var2 == 0) {
            if (var10 && var5 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
               var12 = this.container;
               var12.setWidth(this.computeWrap(var12, 0));
               this.container.horizontalRun.dimension.resolve(this.container.getWidth());
            }
         } else if (var10 && var6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            var12 = this.container;
            var12.setHeight(this.computeWrap(var12, 1));
            this.container.verticalRun.dimension.resolve(this.container.getHeight());
         }
      }

      label98: {
         label97: {
            int var14;
            if (var2 == 0) {
               if (this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.FIXED && this.container.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                  break label97;
               }

               var14 = this.container.getWidth() + var7;
               this.container.horizontalRun.end.resolve(var14);
               this.container.horizontalRun.dimension.resolve(var14 - var7);
            } else {
               if (this.container.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.FIXED && this.container.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                  break label97;
               }

               var14 = this.container.getHeight() + var8;
               this.container.verticalRun.end.resolve(var14);
               this.container.verticalRun.dimension.resolve(var14 - var8);
            }

            var10 = true;
            break label98;
         }

         var10 = false;
      }

      this.measureWidgets();
      Iterator var15 = this.mRuns.iterator();

      while(true) {
         WidgetRun var13;
         do {
            do {
               if (!var15.hasNext()) {
                  var15 = this.mRuns.iterator();

                  while(true) {
                     var1 = var3;
                     if (!var15.hasNext()) {
                        break;
                     }

                     var13 = (WidgetRun)var15.next();
                     if (var13.orientation == var2 && (var10 || var13.widget != this.container) && (!var13.start.resolved || !var13.end.resolved || !(var13 instanceof ChainRun) && !var13.dimension.resolved)) {
                        var1 = false;
                        break;
                     }
                  }

                  this.container.setHorizontalDimensionBehaviour(var5);
                  this.container.setVerticalDimensionBehaviour(var6);
                  return var1;
               }

               var13 = (WidgetRun)var15.next();
            } while(var13.orientation != var2);
         } while(var13.widget == this.container && !var13.resolved);

         var13.applyToWidget();
      }
   }

   public void invalidateGraph() {
      this.mNeedBuildGraph = true;
   }

   public void invalidateMeasures() {
      this.mNeedRedoMeasures = true;
   }

   public void measureWidgets() {
      Iterator var1 = this.container.mChildren.iterator();

      while(true) {
         ConstraintWidget var2;
         do {
            if (!var1.hasNext()) {
               return;
            }

            var2 = (ConstraintWidget)var1.next();
         } while(var2.measured);

         ConstraintWidget.DimensionBehaviour[] var3 = var2.mListDimensionBehaviors;
         boolean var4 = false;
         ConstraintWidget.DimensionBehaviour var11 = var3[0];
         ConstraintWidget.DimensionBehaviour var5 = var2.mListDimensionBehaviors[1];
         int var6 = var2.mMatchConstraintDefaultWidth;
         int var7 = var2.mMatchConstraintDefaultHeight;
         boolean var12;
         if (var11 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || var11 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var6 == 1) {
            var12 = true;
         } else {
            var12 = false;
         }

         boolean var8;
         label61: {
            if (var5 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var8 = var4;
               if (var5 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  break label61;
               }

               var8 = var4;
               if (var7 != 1) {
                  break label61;
               }
            }

            var8 = true;
         }

         boolean var9 = var2.horizontalRun.dimension.resolved;
         boolean var10 = var2.verticalRun.dimension.resolved;
         if (var9 && var10) {
            this.measure(var2, ConstraintWidget.DimensionBehaviour.FIXED, var2.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.FIXED, var2.verticalRun.dimension.value);
            var2.measured = true;
         } else if (var9 && var8) {
            this.measure(var2, ConstraintWidget.DimensionBehaviour.FIXED, var2.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, var2.verticalRun.dimension.value);
            if (var5 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               var2.verticalRun.dimension.wrapValue = var2.getHeight();
            } else {
               var2.verticalRun.dimension.resolve(var2.getHeight());
               var2.measured = true;
            }
         } else if (var10 && var12) {
            this.measure(var2, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, var2.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.FIXED, var2.verticalRun.dimension.value);
            if (var11 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               var2.horizontalRun.dimension.wrapValue = var2.getWidth();
            } else {
               var2.horizontalRun.dimension.resolve(var2.getWidth());
               var2.measured = true;
            }
         }

         if (var2.measured && var2.verticalRun.baselineDimension != null) {
            var2.verticalRun.baselineDimension.resolve(var2.getBaselineDistance());
         }
      }
   }

   public void setMeasurer(BasicMeasure.Measurer var1) {
      this.mMeasurer = var1;
   }
}
