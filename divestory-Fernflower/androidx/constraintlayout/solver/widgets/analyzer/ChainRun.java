package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun extends WidgetRun {
   private int chainStyle;
   ArrayList<WidgetRun> widgets = new ArrayList();

   public ChainRun(ConstraintWidget var1, int var2) {
      super(var1);
      this.orientation = var2;
      this.build();
   }

   private void build() {
      ConstraintWidget var1 = this.widget;

      ConstraintWidget var2;
      ConstraintWidget var3;
      for(var2 = var1.getPreviousChainMember(this.orientation); var2 != null; var2 = var3) {
         var3 = var2.getPreviousChainMember(this.orientation);
         var1 = var2;
      }

      this.widget = var1;
      this.widgets.add(var1.getRun(this.orientation));

      for(var2 = var1.getNextChainMember(this.orientation); var2 != null; var2 = var2.getNextChainMember(this.orientation)) {
         this.widgets.add(var2.getRun(this.orientation));
      }

      Iterator var6 = this.widgets.iterator();

      while(var6.hasNext()) {
         WidgetRun var5 = (WidgetRun)var6.next();
         if (this.orientation == 0) {
            var5.widget.horizontalChainRun = this;
         } else if (this.orientation == 1) {
            var5.widget.verticalChainRun = this;
         }
      }

      boolean var4;
      if (this.orientation == 0 && ((ConstraintWidgetContainer)this.widget.getParent()).isRtl()) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4 && this.widgets.size() > 1) {
         ArrayList var7 = this.widgets;
         this.widget = ((WidgetRun)var7.get(var7.size() - 1)).widget;
      }

      int var8;
      if (this.orientation == 0) {
         var8 = this.widget.getHorizontalChainStyle();
      } else {
         var8 = this.widget.getVerticalChainStyle();
      }

      this.chainStyle = var8;
   }

   private ConstraintWidget getFirstVisibleWidget() {
      for(int var1 = 0; var1 < this.widgets.size(); ++var1) {
         WidgetRun var2 = (WidgetRun)this.widgets.get(var1);
         if (var2.widget.getVisibility() != 8) {
            return var2.widget;
         }
      }

      return null;
   }

   private ConstraintWidget getLastVisibleWidget() {
      for(int var1 = this.widgets.size() - 1; var1 >= 0; --var1) {
         WidgetRun var2 = (WidgetRun)this.widgets.get(var1);
         if (var2.widget.getVisibility() != 8) {
            return var2.widget;
         }
      }

      return null;
   }

   void apply() {
      Iterator var1 = this.widgets.iterator();

      while(var1.hasNext()) {
         ((WidgetRun)var1.next()).apply();
      }

      int var2 = this.widgets.size();
      if (var2 >= 1) {
         ConstraintWidget var3 = ((WidgetRun)this.widgets.get(0)).widget;
         ConstraintWidget var5 = ((WidgetRun)this.widgets.get(var2 - 1)).widget;
         ConstraintAnchor var4;
         ConstraintAnchor var6;
         DependencyNode var7;
         ConstraintWidget var8;
         if (this.orientation == 0) {
            var4 = var3.mLeft;
            var6 = var5.mRight;
            var7 = this.getTarget(var4, 0);
            var2 = var4.getMargin();
            var8 = this.getFirstVisibleWidget();
            if (var8 != null) {
               var2 = var8.mLeft.getMargin();
            }

            if (var7 != null) {
               this.addTarget(this.start, var7, var2);
            }

            var7 = this.getTarget(var6, 0);
            var2 = var6.getMargin();
            var5 = this.getLastVisibleWidget();
            if (var5 != null) {
               var2 = var5.mRight.getMargin();
            }

            if (var7 != null) {
               this.addTarget(this.end, var7, -var2);
            }
         } else {
            var4 = var3.mTop;
            var6 = var5.mBottom;
            var7 = this.getTarget(var4, 1);
            var2 = var4.getMargin();
            var8 = this.getFirstVisibleWidget();
            if (var8 != null) {
               var2 = var8.mTop.getMargin();
            }

            if (var7 != null) {
               this.addTarget(this.start, var7, var2);
            }

            var7 = this.getTarget(var6, 1);
            var2 = var6.getMargin();
            var5 = this.getLastVisibleWidget();
            if (var5 != null) {
               var2 = var5.mBottom.getMargin();
            }

            if (var7 != null) {
               this.addTarget(this.end, var7, -var2);
            }
         }

         this.start.updateDelegate = this;
         this.end.updateDelegate = this;
      }
   }

   public void applyToWidget() {
      for(int var1 = 0; var1 < this.widgets.size(); ++var1) {
         ((WidgetRun)this.widgets.get(var1)).applyToWidget();
      }

   }

   void clear() {
      this.runGroup = null;
      Iterator var1 = this.widgets.iterator();

      while(var1.hasNext()) {
         ((WidgetRun)var1.next()).clear();
      }

   }

   public long getWrapDimension() {
      int var1 = this.widgets.size();
      long var2 = 0L;

      for(int var4 = 0; var4 < var1; ++var4) {
         WidgetRun var5 = (WidgetRun)this.widgets.get(var4);
         var2 = var2 + (long)var5.start.margin + var5.getWrapDimension() + (long)var5.end.margin;
      }

      return var2;
   }

   void reset() {
      this.start.resolved = false;
      this.end.resolved = false;
   }

   boolean supportsWrapComputation() {
      int var1 = this.widgets.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (!((WidgetRun)this.widgets.get(var2)).supportsWrapComputation()) {
            return false;
         }
      }

      return true;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ChainRun ");
      String var2;
      if (this.orientation == 0) {
         var2 = "horizontal : ";
      } else {
         var2 = "vertical : ";
      }

      var1.append(var2);
      var2 = var1.toString();

      StringBuilder var6;
      for(Iterator var5 = this.widgets.iterator(); var5.hasNext(); var2 = var6.toString()) {
         WidgetRun var3 = (WidgetRun)var5.next();
         StringBuilder var4 = new StringBuilder();
         var4.append(var2);
         var4.append("<");
         var2 = var4.toString();
         var4 = new StringBuilder();
         var4.append(var2);
         var4.append(var3);
         var2 = var4.toString();
         var6 = new StringBuilder();
         var6.append(var2);
         var6.append("> ");
      }

      return var2;
   }

   public void update(Dependency var1) {
      if (this.start.resolved && this.end.resolved) {
         ConstraintWidget var22 = this.widget.getParent();
         boolean var2;
         if (var22 != null && var22 instanceof ConstraintWidgetContainer) {
            var2 = ((ConstraintWidgetContainer)var22).isRtl();
         } else {
            var2 = false;
         }

         int var3 = this.end.value - this.start.value;
         int var4 = this.widgets.size();
         int var5 = 0;

         byte var6;
         int var7;
         while(true) {
            var6 = -1;
            if (var5 >= var4) {
               var7 = -1;
               break;
            }

            var7 = var5;
            if (((WidgetRun)this.widgets.get(var5)).widget.getVisibility() != 8) {
               break;
            }

            ++var5;
         }

         int var8 = var4 - 1;
         var5 = var8;

         int var9;
         while(true) {
            var9 = var6;
            if (var5 < 0) {
               break;
            }

            if (((WidgetRun)this.widgets.get(var5)).widget.getVisibility() != 8) {
               var9 = var5;
               break;
            }

            --var5;
         }

         int var10 = 0;

         int var11;
         int var12;
         int var13;
         float var14;
         int var15;
         int var16;
         int var18;
         float var20;
         WidgetRun var23;
         int var24;
         while(true) {
            if (var10 >= 2) {
               var16 = 0;
               var12 = 0;
               var24 = 0;
               var14 = 0.0F;
               break;
            }

            var11 = 0;
            var12 = 0;
            var5 = 0;
            var13 = 0;

            for(var14 = 0.0F; var11 < var4; var5 = var15) {
               var23 = (WidgetRun)this.widgets.get(var11);
               if (var23.widget.getVisibility() == 8) {
                  var24 = var12;
                  var15 = var5;
               } else {
                  var16 = var13 + 1;
                  var24 = var12;
                  if (var11 > 0) {
                     var24 = var12;
                     if (var11 >= var7) {
                        var24 = var12 + var23.start.margin;
                     }
                  }

                  var15 = var23.dimension.value;
                  boolean var25;
                  if (var23.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var25 = true;
                  } else {
                     var25 = false;
                  }

                  boolean var17;
                  if (var25) {
                     if (this.orientation == 0 && !var23.widget.horizontalRun.dimension.resolved) {
                        return;
                     }

                     var17 = var25;
                     var18 = var15;
                     var12 = var5;
                     if (this.orientation == 1) {
                        var17 = var25;
                        var18 = var15;
                        var12 = var5;
                        if (!var23.widget.verticalRun.dimension.resolved) {
                           return;
                        }
                     }
                  } else {
                     label498: {
                        if (var23.matchConstraintsType == 1 && var10 == 0) {
                           var13 = var23.dimension.wrapValue;
                           var12 = var5 + 1;
                           var5 = var13;
                        } else {
                           var17 = var25;
                           var18 = var15;
                           var12 = var5;
                           if (!var23.dimension.resolved) {
                              break label498;
                           }

                           var12 = var5;
                           var5 = var15;
                        }

                        var17 = true;
                        var18 = var5;
                     }
                  }

                  if (!var17) {
                     var13 = var12 + 1;
                     float var19 = var23.widget.mWeight[this.orientation];
                     var5 = var24;
                     var12 = var13;
                     var20 = var14;
                     if (var19 >= 0.0F) {
                        var20 = var14 + var19;
                        var5 = var24;
                        var12 = var13;
                     }
                  } else {
                     var5 = var24 + var18;
                     var20 = var14;
                  }

                  var24 = var5;
                  var15 = var12;
                  var13 = var16;
                  var14 = var20;
                  if (var11 < var8) {
                     var24 = var5;
                     var15 = var12;
                     var13 = var16;
                     var14 = var20;
                     if (var11 < var9) {
                        var24 = var5 + -var23.end.margin;
                        var14 = var20;
                        var13 = var16;
                        var15 = var12;
                     }
                  }
               }

               ++var11;
               var12 = var24;
            }

            if (var12 < var3 || var5 == 0) {
               var16 = var13;
               var24 = var5;
               break;
            }

            ++var10;
         }

         var13 = this.start.value;
         if (var2) {
            var13 = this.end.value;
         }

         var5 = var13;
         if (var12 > var3) {
            if (var2) {
               var5 = var13 + (int)((float)(var12 - var3) / 2.0F + 0.5F);
            } else {
               var5 = var13 - (int)((float)(var12 - var3) / 2.0F + 0.5F);
            }
         }

         if (var24 <= 0) {
            var10 = var24;
         } else {
            var20 = (float)(var3 - var12);
            var11 = (int)(var20 / (float)var24 + 0.5F);
            int var21 = 0;
            var13 = 0;
            var15 = var5;

            while(true) {
               if (var21 >= var4) {
                  if (var13 > 0) {
                     var10 = var24 - var13;
                     var24 = 0;

                     for(var5 = 0; var24 < var4; ++var24) {
                        var23 = (WidgetRun)this.widgets.get(var24);
                        if (var23.widget.getVisibility() != 8) {
                           var12 = var5;
                           if (var24 > 0) {
                              var12 = var5;
                              if (var24 >= var7) {
                                 var12 = var5 + var23.start.margin;
                              }
                           }

                           var12 += var23.dimension.value;
                           var5 = var12;
                           if (var24 < var8) {
                              var5 = var12;
                              if (var24 < var9) {
                                 var5 = var12 + -var23.end.margin;
                              }
                           }
                        }
                     }

                     var24 = var10;
                  } else {
                     var5 = var12;
                  }

                  if (this.chainStyle == 2 && var13 == 0) {
                     this.chainStyle = 0;
                     var12 = var5;
                     var10 = var24;
                     var5 = var15;
                     break;
                  }

                  var12 = var5;
                  var10 = var24;
                  var5 = var15;
                  break;
               }

               var23 = (WidgetRun)this.widgets.get(var21);
               if (var23.widget.getVisibility() != 8 && var23.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !var23.dimension.resolved) {
                  if (var14 > 0.0F) {
                     var5 = (int)(var23.widget.mWeight[this.orientation] * var20 / var14 + 0.5F);
                  } else {
                     var5 = var11;
                  }

                  int var26;
                  if (this.orientation == 0) {
                     var26 = var23.widget.mMatchConstraintMaxWidth;
                     var18 = var23.widget.mMatchConstraintMinWidth;
                     if (var23.matchConstraintsType == 1) {
                        var12 = Math.min(var5, var23.dimension.wrapValue);
                     } else {
                        var12 = var5;
                     }

                     var12 = Math.max(var18, var12);
                     var18 = var12;
                     if (var26 > 0) {
                        var18 = Math.min(var26, var12);
                     }

                     var26 = var5;
                     var12 = var13;
                     if (var18 != var5) {
                        var12 = var13 + 1;
                        var26 = var18;
                     }
                  } else {
                     var26 = var23.widget.mMatchConstraintMaxHeight;
                     var18 = var23.widget.mMatchConstraintMinHeight;
                     if (var23.matchConstraintsType == 1) {
                        var12 = Math.min(var5, var23.dimension.wrapValue);
                     } else {
                        var12 = var5;
                     }

                     var12 = Math.max(var18, var12);
                     var18 = var12;
                     if (var26 > 0) {
                        var18 = Math.min(var26, var12);
                     }

                     var26 = var5;
                     var12 = var13;
                     if (var18 != var5) {
                        var12 = var13 + 1;
                        var26 = var18;
                     }
                  }

                  var23.dimension.resolve(var26);
               } else {
                  var12 = var13;
               }

               ++var21;
               var13 = var12;
            }
         }

         if (var12 > var3) {
            this.chainStyle = 2;
         }

         if (var16 > 0 && var10 == 0 && var7 == var9) {
            this.chainStyle = 2;
         }

         var24 = this.chainStyle;
         if (var24 == 1) {
            if (var16 > 1) {
               var24 = (var3 - var12) / (var16 - 1);
            } else if (var16 == 1) {
               var24 = (var3 - var12) / 2;
            } else {
               var24 = 0;
            }

            var13 = var24;
            if (var10 > 0) {
               var13 = 0;
            }

            var24 = 0;

            for(var12 = var5; var24 < var4; var12 = var5) {
               if (var2) {
                  var5 = var4 - (var24 + 1);
               } else {
                  var5 = var24;
               }

               var23 = (WidgetRun)this.widgets.get(var5);
               if (var23.widget.getVisibility() == 8) {
                  var23.start.resolve(var12);
                  var23.end.resolve(var12);
                  var5 = var12;
               } else {
                  var5 = var12;
                  if (var24 > 0) {
                     if (var2) {
                        var5 = var12 - var13;
                     } else {
                        var5 = var12 + var13;
                     }
                  }

                  var12 = var5;
                  if (var24 > 0) {
                     var12 = var5;
                     if (var24 >= var7) {
                        if (var2) {
                           var12 = var5 - var23.start.margin;
                        } else {
                           var12 = var5 + var23.start.margin;
                        }
                     }
                  }

                  if (var2) {
                     var23.end.resolve(var12);
                  } else {
                     var23.start.resolve(var12);
                  }

                  var10 = var23.dimension.value;
                  var5 = var10;
                  if (var23.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var5 = var10;
                     if (var23.matchConstraintsType == 1) {
                        var5 = var23.dimension.wrapValue;
                     }
                  }

                  if (var2) {
                     var12 -= var5;
                  } else {
                     var12 += var5;
                  }

                  if (var2) {
                     var23.start.resolve(var12);
                  } else {
                     var23.end.resolve(var12);
                  }

                  var23.resolved = true;
                  var5 = var12;
                  if (var24 < var8) {
                     var5 = var12;
                     if (var24 < var9) {
                        if (var2) {
                           var5 = var12 - -var23.end.margin;
                        } else {
                           var5 = var12 + -var23.end.margin;
                        }
                     }
                  }
               }

               ++var24;
            }
         } else if (var24 == 0) {
            var13 = (var3 - var12) / (var16 + 1);
            if (var10 > 0) {
               var13 = 0;
            }

            for(var24 = 0; var24 < var4; ++var24) {
               if (var2) {
                  var12 = var4 - (var24 + 1);
               } else {
                  var12 = var24;
               }

               var23 = (WidgetRun)this.widgets.get(var12);
               if (var23.widget.getVisibility() == 8) {
                  var23.start.resolve(var5);
                  var23.end.resolve(var5);
               } else {
                  if (var2) {
                     var12 = var5 - var13;
                  } else {
                     var12 = var5 + var13;
                  }

                  var5 = var12;
                  if (var24 > 0) {
                     var5 = var12;
                     if (var24 >= var7) {
                        if (var2) {
                           var5 = var12 - var23.start.margin;
                        } else {
                           var5 = var12 + var23.start.margin;
                        }
                     }
                  }

                  if (var2) {
                     var23.end.resolve(var5);
                  } else {
                     var23.start.resolve(var5);
                  }

                  var10 = var23.dimension.value;
                  var12 = var10;
                  if (var23.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var12 = var10;
                     if (var23.matchConstraintsType == 1) {
                        var12 = Math.min(var10, var23.dimension.wrapValue);
                     }
                  }

                  if (var2) {
                     var12 = var5 - var12;
                  } else {
                     var12 += var5;
                  }

                  if (var2) {
                     var23.start.resolve(var12);
                  } else {
                     var23.end.resolve(var12);
                  }

                  var5 = var12;
                  if (var24 < var8) {
                     var5 = var12;
                     if (var24 < var9) {
                        if (var2) {
                           var5 = var12 - -var23.end.margin;
                        } else {
                           var5 = var12 + -var23.end.margin;
                        }
                     }
                  }
               }
            }
         } else if (var24 == 2) {
            if (this.orientation == 0) {
               var14 = this.widget.getHorizontalBiasPercent();
            } else {
               var14 = this.widget.getVerticalBiasPercent();
            }

            var20 = var14;
            if (var2) {
               var20 = 1.0F - var14;
            }

            var24 = (int)((float)(var3 - var12) * var20 + 0.5F);
            if (var24 < 0 || var10 > 0) {
               var24 = 0;
            }

            if (var2) {
               var5 -= var24;
            } else {
               var5 += var24;
            }

            for(var24 = 0; var24 < var4; ++var24) {
               if (var2) {
                  var12 = var4 - (var24 + 1);
               } else {
                  var12 = var24;
               }

               var23 = (WidgetRun)this.widgets.get(var12);
               if (var23.widget.getVisibility() == 8) {
                  var23.start.resolve(var5);
                  var23.end.resolve(var5);
               } else {
                  var12 = var5;
                  if (var24 > 0) {
                     var12 = var5;
                     if (var24 >= var7) {
                        if (var2) {
                           var12 = var5 - var23.start.margin;
                        } else {
                           var12 = var5 + var23.start.margin;
                        }
                     }
                  }

                  if (var2) {
                     var23.end.resolve(var12);
                  } else {
                     var23.start.resolve(var12);
                  }

                  var5 = var23.dimension.value;
                  if (var23.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var23.matchConstraintsType == 1) {
                     var5 = var23.dimension.wrapValue;
                  }

                  if (var2) {
                     var12 -= var5;
                  } else {
                     var12 += var5;
                  }

                  if (var2) {
                     var23.start.resolve(var12);
                  } else {
                     var23.end.resolve(var12);
                  }

                  var5 = var12;
                  if (var24 < var8) {
                     var5 = var12;
                     if (var24 < var9) {
                        if (var2) {
                           var5 = var12 - -var23.end.margin;
                        } else {
                           var5 = var12 + -var23.end.margin;
                        }
                     }
                  }
               }
            }
         }
      }

   }
}
