package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

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

   public RunGroup(WidgetRun var1, int var2) {
      int var3 = index;
      this.groupIndex = var3;
      index = var3 + 1;
      this.firstRun = var1;
      this.lastRun = var1;
      this.direction = var2;
   }

   private boolean defineTerminalWidget(WidgetRun var1, int var2) {
      if (!var1.widget.isTerminalWidget[var2]) {
         return false;
      } else {
         Iterator var3 = var1.start.dependencies.iterator();

         while(true) {
            DependencyNode var6;
            do {
               do {
                  Dependency var4;
                  do {
                     if (!var3.hasNext()) {
                        var3 = var1.end.dependencies.iterator();

                        while(true) {
                           DependencyNode var8;
                           do {
                              do {
                                 do {
                                    if (!var3.hasNext()) {
                                       return false;
                                    }

                                    var4 = (Dependency)var3.next();
                                 } while(!(var4 instanceof DependencyNode));

                                 var8 = (DependencyNode)var4;
                              } while(var8.run == var1);
                           } while(var8 != var8.run.start);

                           if (var1 instanceof ChainRun) {
                              Iterator var7 = ((ChainRun)var1).widgets.iterator();

                              while(var7.hasNext()) {
                                 this.defineTerminalWidget((WidgetRun)var7.next(), var2);
                              }
                           } else if (!(var1 instanceof HelperReferences)) {
                              var1.widget.isTerminalWidget[var2] = false;
                           }

                           this.defineTerminalWidget(var8.run, var2);
                        }
                     }

                     var4 = (Dependency)var3.next();
                  } while(!(var4 instanceof DependencyNode));

                  var6 = (DependencyNode)var4;
               } while(var6.run == var1);
            } while(var6 != var6.run.start);

            if (var1 instanceof ChainRun) {
               Iterator var5 = ((ChainRun)var1).widgets.iterator();

               while(var5.hasNext()) {
                  this.defineTerminalWidget((WidgetRun)var5.next(), var2);
               }
            } else if (!(var1 instanceof HelperReferences)) {
               var1.widget.isTerminalWidget[var2] = false;
            }

            this.defineTerminalWidget(var6.run, var2);
         }
      }
   }

   private long traverseEnd(DependencyNode var1, long var2) {
      WidgetRun var4 = var1.run;
      if (var4 instanceof HelperReferences) {
         return var2;
      } else {
         int var5 = var1.dependencies.size();
         int var6 = 0;

         long var7;
         long var10;
         for(var7 = var2; var6 < var5; var7 = var10) {
            Dependency var9 = (Dependency)var1.dependencies.get(var6);
            var10 = var7;
            if (var9 instanceof DependencyNode) {
               DependencyNode var12 = (DependencyNode)var9;
               if (var12.run == var4) {
                  var10 = var7;
               } else {
                  var10 = Math.min(var7, this.traverseEnd(var12, (long)var12.margin + var2));
               }
            }

            ++var6;
         }

         var10 = var7;
         if (var1 == var4.end) {
            var10 = var4.getWrapDimension();
            var1 = var4.start;
            var2 -= var10;
            var10 = Math.min(Math.min(var7, this.traverseEnd(var1, var2)), var2 - (long)var4.start.margin);
         }

         return var10;
      }
   }

   private long traverseStart(DependencyNode var1, long var2) {
      WidgetRun var4 = var1.run;
      if (var4 instanceof HelperReferences) {
         return var2;
      } else {
         int var5 = var1.dependencies.size();
         int var6 = 0;

         long var7;
         long var10;
         for(var7 = var2; var6 < var5; var7 = var10) {
            Dependency var9 = (Dependency)var1.dependencies.get(var6);
            var10 = var7;
            if (var9 instanceof DependencyNode) {
               DependencyNode var12 = (DependencyNode)var9;
               if (var12.run == var4) {
                  var10 = var7;
               } else {
                  var10 = Math.max(var7, this.traverseStart(var12, (long)var12.margin + var2));
               }
            }

            ++var6;
         }

         var10 = var7;
         if (var1 == var4.start) {
            var10 = var4.getWrapDimension();
            var1 = var4.end;
            var2 += var10;
            var10 = Math.max(Math.max(var7, this.traverseStart(var1, var2)), var2 - (long)var4.end.margin);
         }

         return var10;
      }
   }

   public void add(WidgetRun var1) {
      this.runs.add(var1);
      this.lastRun = var1;
   }

   public long computeWrapSize(ConstraintWidgetContainer var1, int var2) {
      WidgetRun var3 = this.firstRun;
      boolean var4 = var3 instanceof ChainRun;
      long var5 = 0L;
      if (var4) {
         if (((ChainRun)var3).orientation != var2) {
            return 0L;
         }
      } else if (var2 == 0) {
         if (!(var3 instanceof HorizontalWidgetRun)) {
            return 0L;
         }
      } else if (!(var3 instanceof VerticalWidgetRun)) {
         return 0L;
      }

      DependencyNode var19;
      if (var2 == 0) {
         var19 = var1.horizontalRun.start;
      } else {
         var19 = var1.verticalRun.start;
      }

      DependencyNode var18;
      if (var2 == 0) {
         var18 = var1.horizontalRun.end;
      } else {
         var18 = var1.verticalRun.end;
      }

      boolean var7 = this.firstRun.start.targets.contains(var19);
      var4 = this.firstRun.end.targets.contains(var18);
      long var8 = this.firstRun.getWrapDimension();
      long var10;
      long var14;
      if (var7 && var4) {
         var10 = this.traverseStart(this.firstRun.start, 0L);
         long var12 = this.traverseEnd(this.firstRun.end, 0L);
         var14 = var10 - var8;
         var10 = var14;
         if (var14 >= (long)(-this.firstRun.end.margin)) {
            var10 = var14 + (long)this.firstRun.end.margin;
         }

         var12 = -var12 - var8 - (long)this.firstRun.start.margin;
         var14 = var12;
         if (var12 >= (long)this.firstRun.start.margin) {
            var14 = var12 - (long)this.firstRun.start.margin;
         }

         float var16 = this.firstRun.widget.getBiasPercent(var2);
         if (var16 > 0.0F) {
            var5 = (long)((float)var14 / var16 + (float)var10 / (1.0F - var16));
         }

         float var17 = (float)var5;
         var14 = (long)(var17 * var16 + 0.5F);
         var10 = (long)(var17 * (1.0F - var16) + 0.5F);
         var10 = (long)this.firstRun.start.margin + var14 + var8 + var10;
         var2 = this.firstRun.end.margin;
      } else {
         if (var7) {
            var10 = Math.max(this.traverseStart(this.firstRun.start, (long)this.firstRun.start.margin), (long)this.firstRun.start.margin + var8);
            return var10;
         }

         if (var4) {
            var14 = this.traverseEnd(this.firstRun.end, (long)this.firstRun.end.margin);
            var10 = (long)(-this.firstRun.end.margin);
            var10 = Math.max(-var14, var10 + var8);
            return var10;
         }

         var10 = (long)this.firstRun.start.margin + this.firstRun.getWrapDimension();
         var2 = this.firstRun.end.margin;
      }

      var10 -= (long)var2;
      return var10;
   }

   public void defineTerminalWidgets(boolean var1, boolean var2) {
      WidgetRun var3;
      if (var1) {
         var3 = this.firstRun;
         if (var3 instanceof HorizontalWidgetRun) {
            this.defineTerminalWidget(var3, 0);
         }
      }

      if (var2) {
         var3 = this.firstRun;
         if (var3 instanceof VerticalWidgetRun) {
            this.defineTerminalWidget(var3, 1);
         }
      }

   }
}
