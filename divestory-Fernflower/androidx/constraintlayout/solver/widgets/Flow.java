package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Flow extends VirtualLayout {
   public static final int HORIZONTAL_ALIGN_CENTER = 2;
   public static final int HORIZONTAL_ALIGN_END = 1;
   public static final int HORIZONTAL_ALIGN_START = 0;
   public static final int VERTICAL_ALIGN_BASELINE = 3;
   public static final int VERTICAL_ALIGN_BOTTOM = 1;
   public static final int VERTICAL_ALIGN_CENTER = 2;
   public static final int VERTICAL_ALIGN_TOP = 0;
   public static final int WRAP_ALIGNED = 2;
   public static final int WRAP_CHAIN = 1;
   public static final int WRAP_NONE = 0;
   private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
   private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
   private int[] mAlignedDimensions = null;
   private ArrayList<Flow.WidgetsList> mChainList = new ArrayList();
   private ConstraintWidget[] mDisplayedWidgets;
   private int mDisplayedWidgetsCount = 0;
   private float mFirstHorizontalBias = 0.5F;
   private int mFirstHorizontalStyle = -1;
   private float mFirstVerticalBias = 0.5F;
   private int mFirstVerticalStyle = -1;
   private int mHorizontalAlign = 2;
   private float mHorizontalBias = 0.5F;
   private int mHorizontalGap = 0;
   private int mHorizontalStyle = -1;
   private float mLastHorizontalBias = 0.5F;
   private int mLastHorizontalStyle = -1;
   private float mLastVerticalBias = 0.5F;
   private int mLastVerticalStyle = -1;
   private int mMaxElementsWrap = -1;
   private int mOrientation = 0;
   private int mVerticalAlign = 2;
   private float mVerticalBias = 0.5F;
   private int mVerticalGap = 0;
   private int mVerticalStyle = -1;
   private int mWrapMode = 0;

   private void createAlignedConstraints(boolean var1) {
      if (this.mAlignedDimensions != null && this.mAlignedBiggestElementsInCols != null && this.mAlignedBiggestElementsInRows != null) {
         int var2;
         for(var2 = 0; var2 < this.mDisplayedWidgetsCount; ++var2) {
            this.mDisplayedWidgets[var2].resetAnchors();
         }

         int[] var3 = this.mAlignedDimensions;
         int var4 = var3[0];
         int var5 = var3[1];
         ConstraintWidget var10 = null;

         int var6;
         ConstraintWidget var7;
         ConstraintWidget var8;
         for(var2 = 0; var2 < var4; var10 = var8) {
            if (var1) {
               var6 = var4 - var2 - 1;
            } else {
               var6 = var2;
            }

            var7 = this.mAlignedBiggestElementsInCols[var6];
            var8 = var10;
            if (var7 != null) {
               if (var7.getVisibility() == 8) {
                  var8 = var10;
               } else {
                  if (var2 == 0) {
                     var7.connect(var7.mLeft, this.mLeft, this.getPaddingLeft());
                     var7.setHorizontalChainStyle(this.mHorizontalStyle);
                     var7.setHorizontalBiasPercent(this.mHorizontalBias);
                  }

                  if (var2 == var4 - 1) {
                     var7.connect(var7.mRight, this.mRight, this.getPaddingRight());
                  }

                  if (var2 > 0) {
                     var7.connect(var7.mLeft, var10.mRight, this.mHorizontalGap);
                     var10.connect(var10.mRight, var7.mLeft, 0);
                  }

                  var8 = var7;
               }
            }

            ++var2;
         }

         var2 = 0;

         for(var8 = var10; var2 < var5; var8 = var10) {
            var7 = this.mAlignedBiggestElementsInRows[var2];
            var10 = var8;
            if (var7 != null) {
               if (var7.getVisibility() == 8) {
                  var10 = var8;
               } else {
                  if (var2 == 0) {
                     var7.connect(var7.mTop, this.mTop, this.getPaddingTop());
                     var7.setVerticalChainStyle(this.mVerticalStyle);
                     var7.setVerticalBiasPercent(this.mVerticalBias);
                  }

                  if (var2 == var5 - 1) {
                     var7.connect(var7.mBottom, this.mBottom, this.getPaddingBottom());
                  }

                  if (var2 > 0) {
                     var7.connect(var7.mTop, var8.mBottom, this.mVerticalGap);
                     var8.connect(var8.mBottom, var7.mTop, 0);
                  }

                  var10 = var7;
               }
            }

            ++var2;
         }

         for(var2 = 0; var2 < var4; ++var2) {
            for(var6 = 0; var6 < var5; ++var6) {
               int var9 = var6 * var4 + var2;
               if (this.mOrientation == 1) {
                  var9 = var2 * var5 + var6;
               }

               ConstraintWidget[] var11 = this.mDisplayedWidgets;
               if (var9 < var11.length) {
                  var8 = var11[var9];
                  if (var8 != null && var8.getVisibility() != 8) {
                     var7 = this.mAlignedBiggestElementsInCols[var2];
                     var10 = this.mAlignedBiggestElementsInRows[var6];
                     if (var8 != var7) {
                        var8.connect(var8.mLeft, var7.mLeft, 0);
                        var8.connect(var8.mRight, var7.mRight, 0);
                     }

                     if (var8 != var10) {
                        var8.connect(var8.mTop, var10.mTop, 0);
                        var8.connect(var8.mBottom, var10.mBottom, 0);
                     }
                  }
               }
            }
         }
      }

   }

   private final int getWidgetHeight(ConstraintWidget var1, int var2) {
      if (var1 == null) {
         return 0;
      } else {
         if (var1.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (var1.mMatchConstraintDefaultHeight == 0) {
               return 0;
            }

            if (var1.mMatchConstraintDefaultHeight == 2) {
               var2 = (int)(var1.mMatchConstraintPercentHeight * (float)var2);
               if (var2 != var1.getHeight()) {
                  this.measure(var1, var1.getHorizontalDimensionBehaviour(), var1.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, var2);
               }

               return var2;
            }

            if (var1.mMatchConstraintDefaultHeight == 1) {
               return var1.getHeight();
            }

            if (var1.mMatchConstraintDefaultHeight == 3) {
               return (int)((float)var1.getWidth() * var1.mDimensionRatio + 0.5F);
            }
         }

         return var1.getHeight();
      }
   }

   private final int getWidgetWidth(ConstraintWidget var1, int var2) {
      if (var1 == null) {
         return 0;
      } else {
         if (var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (var1.mMatchConstraintDefaultWidth == 0) {
               return 0;
            }

            if (var1.mMatchConstraintDefaultWidth == 2) {
               var2 = (int)(var1.mMatchConstraintPercentWidth * (float)var2);
               if (var2 != var1.getWidth()) {
                  this.measure(var1, ConstraintWidget.DimensionBehaviour.FIXED, var2, var1.getVerticalDimensionBehaviour(), var1.getHeight());
               }

               return var2;
            }

            if (var1.mMatchConstraintDefaultWidth == 1) {
               return var1.getWidth();
            }

            if (var1.mMatchConstraintDefaultWidth == 3) {
               return (int)((float)var1.getHeight() * var1.mDimensionRatio + 0.5F);
            }
         }

         return var1.getWidth();
      }
   }

   private void measureAligned(ConstraintWidget[] var1, int var2, int var3, int var4, int[] var5) {
      int var6;
      int var7;
      int var8;
      int var9;
      ConstraintWidget var10;
      if (var3 == 0) {
         var6 = this.mMaxElementsWrap;
         var7 = var6;
         if (var6 <= 0) {
            var6 = 0;
            var8 = 0;
            var9 = 0;

            while(true) {
               var7 = var6;
               if (var8 >= var2) {
                  break;
               }

               var7 = var9;
               if (var8 > 0) {
                  var7 = var9 + this.mHorizontalGap;
               }

               var10 = var1[var8];
               if (var10 == null) {
                  var9 = var7;
               } else {
                  var9 = var7 + this.getWidgetWidth(var10, var4);
                  if (var9 > var4) {
                     var7 = var6;
                     break;
                  }

                  ++var6;
               }

               ++var8;
            }
         }

         var9 = var7;
         var6 = 0;
      } else {
         var6 = this.mMaxElementsWrap;
         var7 = var6;
         if (var6 <= 0) {
            var6 = 0;
            var8 = 0;
            var9 = 0;

            while(true) {
               var7 = var6;
               if (var8 >= var2) {
                  break;
               }

               var7 = var9;
               if (var8 > 0) {
                  var7 = var9 + this.mVerticalGap;
               }

               var10 = var1[var8];
               if (var10 == null) {
                  var9 = var7;
               } else {
                  var9 = var7 + this.getWidgetHeight(var10, var4);
                  if (var9 > var4) {
                     var7 = var6;
                     break;
                  }

                  ++var6;
               }

               ++var8;
            }
         }

         var9 = 0;
         var6 = var7;
      }

      if (this.mAlignedDimensions == null) {
         this.mAlignedDimensions = new int[2];
      }

      boolean var12;
      if (var6 == 0 && var3 == 1) {
         var12 = true;
         var6 = var6;
         var9 = var9;
      } else if (var9 == 0) {
         if (var3 == 0) {
            var12 = true;
            var6 = var6;
            var9 = var9;
         } else {
            var12 = false;
         }
      } else {
         var12 = false;
      }

      while(true) {
         while(!var12) {
            if (var3 == 0) {
               var6 = (int)Math.ceil((double)((float)var2 / (float)var9));
            } else {
               var9 = (int)Math.ceil((double)((float)var2 / (float)var6));
            }

            ConstraintWidget[] var16 = this.mAlignedBiggestElementsInCols;
            if (var16 != null) {
               if (var16.length < var9) {
                  this.mAlignedBiggestElementsInCols = new ConstraintWidget[var9];
               } else {
                  Arrays.fill(var16, (Object)null);
               }
            } else {
               this.mAlignedBiggestElementsInCols = new ConstraintWidget[var9];
            }

            var16 = this.mAlignedBiggestElementsInRows;
            if (var16 != null) {
               if (var16.length < var6) {
                  this.mAlignedBiggestElementsInRows = new ConstraintWidget[var6];
                  var7 = 0;
               } else {
                  Arrays.fill(var16, (Object)null);
                  var7 = 0;
               }
            } else {
               this.mAlignedBiggestElementsInRows = new ConstraintWidget[var6];
               var7 = 0;
            }

            int var13;
            while(var7 < var9) {
               var8 = 0;

               while(var8 < var6) {
                  var13 = var8 * var9 + var7;
                  if (var3 == 1) {
                     var13 = var7 * var6 + var8;
                  }

                  if (var13 >= var1.length) {
                     ++var8;
                  } else {
                     var10 = var1[var13];
                     if (var10 == null) {
                        ++var8;
                     } else {
                        var13 = this.getWidgetWidth(var10, var4);
                        ConstraintWidget[] var14 = this.mAlignedBiggestElementsInCols;
                        if (var14[var7] != null) {
                           if (var14[var7].getWidth() < var13) {
                              this.mAlignedBiggestElementsInCols[var7] = var10;
                           }
                        } else {
                           this.mAlignedBiggestElementsInCols[var7] = var10;
                        }

                        var13 = this.getWidgetHeight(var10, var4);
                        var14 = this.mAlignedBiggestElementsInRows;
                        if (var14[var8] != null) {
                           if (var14[var8].getHeight() < var13) {
                              this.mAlignedBiggestElementsInRows[var8] = var10;
                              ++var8;
                           } else {
                              ++var8;
                           }
                        } else {
                           this.mAlignedBiggestElementsInRows[var8] = var10;
                           ++var8;
                        }
                     }
                  }
               }

               ++var7;
            }

            var8 = 0;

            for(var7 = 0; var8 < var9; var7 = var13) {
               var10 = this.mAlignedBiggestElementsInCols[var8];
               var13 = var7;
               if (var10 != null) {
                  var13 = var7;
                  if (var8 > 0) {
                     var13 = var7 + this.mHorizontalGap;
                  }

                  var13 += this.getWidgetWidth(var10, var4);
               }

               ++var8;
            }

            var8 = 0;

            int var11;
            for(var13 = 0; var8 < var6; var13 = var11) {
               var10 = this.mAlignedBiggestElementsInRows[var8];
               var11 = var13;
               if (var10 != null) {
                  var11 = var13;
                  if (var8 > 0) {
                     var11 = var13 + this.mVerticalGap;
                  }

                  var11 += this.getWidgetHeight(var10, var4);
               }

               ++var8;
            }

            var5[0] = var7;
            var5[1] = var13;
            if (var3 == 0) {
               var11 = var6;
               var8 = var9;
               if (var7 > var4) {
                  var11 = var6;
                  var8 = var9;
                  if (var9 > 1) {
                     --var9;
                     continue;
                  }
               }
            } else {
               var11 = var6;
               var8 = var9;
               if (var13 > var4) {
                  var11 = var6;
                  var8 = var9;
                  if (var6 > 1) {
                     --var6;
                     continue;
                  }
               }
            }

            var12 = true;
            var6 = var11;
            var9 = var8;
         }

         int[] var15 = this.mAlignedDimensions;
         var15[0] = var9;
         var15[1] = var6;
         return;
      }
   }

   private void measureChainWrap(ConstraintWidget[] var1, int var2, int var3, int var4, int[] var5) {
      if (var2 != 0) {
         this.mChainList.clear();
         Flow.WidgetsList var6 = new Flow.WidgetsList(var3, this.mLeft, this.mTop, this.mRight, this.mBottom, var4);
         this.mChainList.add(var6);
         int var7;
         int var8;
         int var9;
         int var10;
         ConstraintWidget var11;
         int var12;
         boolean var13;
         int var14;
         Flow.WidgetsList var15;
         boolean var21;
         if (var3 == 0) {
            var7 = 0;
            var8 = 0;
            var9 = 0;

            while(true) {
               var10 = var7;
               if (var9 >= var2) {
                  break;
               }

               var11 = var1[var9];
               var12 = this.getWidgetWidth(var11, var4);
               var10 = var7;
               if (var11.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var10 = var7 + 1;
               }

               if ((var8 == var4 || this.mHorizontalGap + var8 + var12 > var4) && var6.biggest != null) {
                  var21 = true;
               } else {
                  var21 = false;
               }

               var13 = var21;
               if (!var21) {
                  var13 = var21;
                  if (var9 > 0) {
                     var14 = this.mMaxElementsWrap;
                     var13 = var21;
                     if (var14 > 0) {
                        var13 = var21;
                        if (var9 % var14 == 0) {
                           var13 = true;
                        }
                     }
                  }
               }

               label152: {
                  if (var13) {
                     var15 = new Flow.WidgetsList(var3, this.mLeft, this.mTop, this.mRight, this.mBottom, var4);
                     var15.setStartIndex(var9);
                     this.mChainList.add(var15);
                  } else {
                     var15 = var6;
                     if (var9 > 0) {
                        var8 += this.mHorizontalGap + var12;
                        break label152;
                     }
                  }

                  var8 = var12;
                  var6 = var15;
               }

               var6.add(var11);
               ++var9;
               var7 = var10;
            }
         } else {
            var7 = 0;
            var8 = 0;
            var9 = 0;
            var15 = var6;

            while(true) {
               var10 = var7;
               if (var9 >= var2) {
                  break;
               }

               var11 = var1[var9];
               var12 = this.getWidgetHeight(var11, var4);
               var10 = var7;
               if (var11.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var10 = var7 + 1;
               }

               if ((var8 == var4 || this.mVerticalGap + var8 + var12 > var4) && var15.biggest != null) {
                  var21 = true;
               } else {
                  var21 = false;
               }

               var13 = var21;
               if (!var21) {
                  var13 = var21;
                  if (var9 > 0) {
                     var14 = this.mMaxElementsWrap;
                     var13 = var21;
                     if (var14 > 0) {
                        var13 = var21;
                        if (var9 % var14 == 0) {
                           var13 = true;
                        }
                     }
                  }
               }

               label132: {
                  if (var13) {
                     var6 = new Flow.WidgetsList(var3, this.mLeft, this.mTop, this.mRight, this.mBottom, var4);
                     var6.setStartIndex(var9);
                     this.mChainList.add(var6);
                  } else {
                     var6 = var15;
                     if (var9 > 0) {
                        var8 += this.mVerticalGap + var12;
                        var6 = var15;
                        break label132;
                     }
                  }

                  var8 = var12;
               }

               var6.add(var11);
               ++var9;
               var7 = var10;
               var15 = var6;
            }
         }

         int var16 = this.mChainList.size();
         ConstraintAnchor var19 = this.mLeft;
         ConstraintAnchor var23 = this.mTop;
         ConstraintAnchor var22 = this.mRight;
         ConstraintAnchor var25 = this.mBottom;
         var8 = this.getPaddingLeft();
         var9 = this.getPaddingTop();
         var12 = this.getPaddingRight();
         int var26 = this.getPaddingBottom();
         boolean var20;
         if (this.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            var20 = false;
         } else {
            var20 = true;
         }

         if (var10 > 0 && var20) {
            for(var2 = 0; var2 < var16; ++var2) {
               Flow.WidgetsList var17 = (Flow.WidgetsList)this.mChainList.get(var2);
               if (var3 == 0) {
                  var17.measureMatchConstraints(var4 - var17.getWidth());
               } else {
                  var17.measureMatchConstraints(var4 - var17.getHeight());
               }
            }
         }

         var10 = 0;
         var14 = 0;

         for(var2 = 0; var2 < var16; var14 = var7) {
            Flow.WidgetsList var18 = (Flow.WidgetsList)this.mChainList.get(var2);
            byte var24;
            ConstraintAnchor var27;
            if (var3 == 0) {
               if (var2 < var16 - 1) {
                  var25 = ((Flow.WidgetsList)this.mChainList.get(var2 + 1)).biggest.mTop;
                  var7 = 0;
               } else {
                  var25 = this.mBottom;
                  var7 = this.getPaddingBottom();
               }

               var27 = var18.biggest.mBottom;
               var18.setup(var3, var19, var23, var22, var25, var8, var9, var12, var7, var4);
               var9 = Math.max(var14, var18.getWidth());
               var26 = var10 + var18.getHeight();
               var10 = var26;
               if (var2 > 0) {
                  var10 = var26 + this.mVerticalGap;
               }

               var23 = var27;
               var24 = 0;
               var26 = var7;
               var7 = var9;
               var9 = var24;
            } else {
               if (var2 < var16 - 1) {
                  var22 = ((Flow.WidgetsList)this.mChainList.get(var2 + 1)).biggest.mLeft;
                  var7 = 0;
               } else {
                  var22 = this.mRight;
                  var7 = this.getPaddingRight();
               }

               var27 = var18.biggest.mRight;
               var18.setup(var3, var19, var23, var22, var25, var8, var9, var7, var26, var4);
               var8 = var14 + var18.getWidth();
               var14 = Math.max(var10, var18.getHeight());
               var10 = var8;
               if (var2 > 0) {
                  var10 = var8 + this.mHorizontalGap;
               }

               var12 = var7;
               var19 = var27;
               var24 = 0;
               var7 = var10;
               var10 = var14;
               var8 = var24;
            }

            ++var2;
         }

         var5[0] = var14;
         var5[1] = var10;
      }
   }

   private void measureNoWrap(ConstraintWidget[] var1, int var2, int var3, int var4, int[] var5) {
      if (var2 != 0) {
         Flow.WidgetsList var6;
         if (this.mChainList.size() == 0) {
            var6 = new Flow.WidgetsList(var3, this.mLeft, this.mTop, this.mRight, this.mBottom, var4);
            this.mChainList.add(var6);
         } else {
            var6 = (Flow.WidgetsList)this.mChainList.get(0);
            var6.clear();
            ConstraintAnchor var7 = this.mLeft;
            ConstraintAnchor var8 = this.mTop;
            ConstraintAnchor var9 = this.mRight;
            ConstraintAnchor var10 = this.mBottom;
            int var11 = this.getPaddingLeft();
            int var12 = this.getPaddingTop();
            int var13 = this.getPaddingRight();
            int var14 = this.getPaddingBottom();
            var6.setup(var3, var7, var8, var9, var10, var11, var12, var13, var14, var4);
         }

         for(var3 = 0; var3 < var2; ++var3) {
            var6.add(var1[var3]);
         }

         var5[0] = var6.getWidth();
         var5[1] = var6.getHeight();
      }
   }

   public void addToSolver(LinearSystem var1) {
      super.addToSolver(var1);
      boolean var2;
      if (this.getParent() != null) {
         var2 = ((ConstraintWidgetContainer)this.getParent()).isRtl();
      } else {
         var2 = false;
      }

      int var3 = this.mWrapMode;
      if (var3 != 0) {
         if (var3 != 1) {
            if (var3 == 2) {
               this.createAlignedConstraints(var2);
            }
         } else {
            int var4 = this.mChainList.size();

            for(var3 = 0; var3 < var4; ++var3) {
               Flow.WidgetsList var6 = (Flow.WidgetsList)this.mChainList.get(var3);
               boolean var5;
               if (var3 == var4 - 1) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               var6.createConstraints(var2, var3, var5);
            }
         }
      } else if (this.mChainList.size() > 0) {
         ((Flow.WidgetsList)this.mChainList.get(0)).createConstraints(var2, 0, true);
      }

      this.needsCallbackFromSolver(false);
   }

   public void copy(ConstraintWidget var1, HashMap<ConstraintWidget, ConstraintWidget> var2) {
      super.copy(var1, var2);
      Flow var3 = (Flow)var1;
      this.mHorizontalStyle = var3.mHorizontalStyle;
      this.mVerticalStyle = var3.mVerticalStyle;
      this.mFirstHorizontalStyle = var3.mFirstHorizontalStyle;
      this.mFirstVerticalStyle = var3.mFirstVerticalStyle;
      this.mLastHorizontalStyle = var3.mLastHorizontalStyle;
      this.mLastVerticalStyle = var3.mLastVerticalStyle;
      this.mHorizontalBias = var3.mHorizontalBias;
      this.mVerticalBias = var3.mVerticalBias;
      this.mFirstHorizontalBias = var3.mFirstHorizontalBias;
      this.mFirstVerticalBias = var3.mFirstVerticalBias;
      this.mLastHorizontalBias = var3.mLastHorizontalBias;
      this.mLastVerticalBias = var3.mLastVerticalBias;
      this.mHorizontalGap = var3.mHorizontalGap;
      this.mVerticalGap = var3.mVerticalGap;
      this.mHorizontalAlign = var3.mHorizontalAlign;
      this.mVerticalAlign = var3.mVerticalAlign;
      this.mWrapMode = var3.mWrapMode;
      this.mMaxElementsWrap = var3.mMaxElementsWrap;
      this.mOrientation = var3.mOrientation;
   }

   public void measure(int var1, int var2, int var3, int var4) {
      if (this.mWidgetsCount > 0 && !this.measureChildren()) {
         this.setMeasure(0, 0);
         this.needsCallbackFromSolver(false);
      } else {
         int var5 = this.getPaddingLeft();
         int var6 = this.getPaddingRight();
         int var7 = this.getPaddingTop();
         int var8 = this.getPaddingBottom();
         int[] var9 = new int[2];
         int var10 = var2 - var5 - var6;
         if (this.mOrientation == 1) {
            var10 = var4 - var7 - var8;
         }

         if (this.mOrientation == 0) {
            if (this.mHorizontalStyle == -1) {
               this.mHorizontalStyle = 0;
            }

            if (this.mVerticalStyle == -1) {
               this.mVerticalStyle = 0;
            }
         } else {
            if (this.mHorizontalStyle == -1) {
               this.mHorizontalStyle = 0;
            }

            if (this.mVerticalStyle == -1) {
               this.mVerticalStyle = 0;
            }
         }

         ConstraintWidget[] var11 = this.mWidgets;
         int var12 = 0;

         int var13;
         int var14;
         for(var13 = 0; var12 < this.mWidgetsCount; var13 = var14) {
            var14 = var13;
            if (this.mWidgets[var12].getVisibility() == 8) {
               var14 = var13 + 1;
            }

            ++var12;
         }

         var12 = this.mWidgetsCount;
         if (var13 > 0) {
            var11 = new ConstraintWidget[this.mWidgetsCount - var13];
            var14 = 0;

            for(var13 = 0; var14 < this.mWidgetsCount; var13 = var12) {
               ConstraintWidget var15 = this.mWidgets[var14];
               var12 = var13;
               if (var15.getVisibility() != 8) {
                  var11[var13] = var15;
                  var12 = var13 + 1;
               }

               ++var14;
            }

            var12 = var13;
         }

         this.mDisplayedWidgets = var11;
         this.mDisplayedWidgetsCount = var12;
         var13 = this.mWrapMode;
         if (var13 != 0) {
            if (var13 != 1) {
               if (var13 == 2) {
                  this.measureAligned(var11, var12, this.mOrientation, var10, var9);
               }
            } else {
               this.measureChainWrap(var11, var12, this.mOrientation, var10, var9);
            }
         } else {
            this.measureNoWrap(var11, var12, this.mOrientation, var10, var9);
         }

         boolean var16 = true;
         var10 = var9[0] + var5 + var6;
         var13 = var9[1] + var7 + var8;
         if (var1 == 1073741824) {
            var1 = var2;
         } else if (var1 == Integer.MIN_VALUE) {
            var1 = Math.min(var10, var2);
         } else if (var1 == 0) {
            var1 = var10;
         } else {
            var1 = 0;
         }

         if (var3 == 1073741824) {
            var2 = var4;
         } else if (var3 == Integer.MIN_VALUE) {
            var2 = Math.min(var13, var4);
         } else if (var3 == 0) {
            var2 = var13;
         } else {
            var2 = 0;
         }

         this.setMeasure(var1, var2);
         this.setWidth(var1);
         this.setHeight(var2);
         if (this.mWidgetsCount <= 0) {
            var16 = false;
         }

         this.needsCallbackFromSolver(var16);
      }
   }

   public void setFirstHorizontalBias(float var1) {
      this.mFirstHorizontalBias = var1;
   }

   public void setFirstHorizontalStyle(int var1) {
      this.mFirstHorizontalStyle = var1;
   }

   public void setFirstVerticalBias(float var1) {
      this.mFirstVerticalBias = var1;
   }

   public void setFirstVerticalStyle(int var1) {
      this.mFirstVerticalStyle = var1;
   }

   public void setHorizontalAlign(int var1) {
      this.mHorizontalAlign = var1;
   }

   public void setHorizontalBias(float var1) {
      this.mHorizontalBias = var1;
   }

   public void setHorizontalGap(int var1) {
      this.mHorizontalGap = var1;
   }

   public void setHorizontalStyle(int var1) {
      this.mHorizontalStyle = var1;
   }

   public void setLastHorizontalBias(float var1) {
      this.mLastHorizontalBias = var1;
   }

   public void setLastHorizontalStyle(int var1) {
      this.mLastHorizontalStyle = var1;
   }

   public void setLastVerticalBias(float var1) {
      this.mLastVerticalBias = var1;
   }

   public void setLastVerticalStyle(int var1) {
      this.mLastVerticalStyle = var1;
   }

   public void setMaxElementsWrap(int var1) {
      this.mMaxElementsWrap = var1;
   }

   public void setOrientation(int var1) {
      this.mOrientation = var1;
   }

   public void setVerticalAlign(int var1) {
      this.mVerticalAlign = var1;
   }

   public void setVerticalBias(float var1) {
      this.mVerticalBias = var1;
   }

   public void setVerticalGap(int var1) {
      this.mVerticalGap = var1;
   }

   public void setVerticalStyle(int var1) {
      this.mVerticalStyle = var1;
   }

   public void setWrapMode(int var1) {
      this.mWrapMode = var1;
   }

   private class WidgetsList {
      private ConstraintWidget biggest = null;
      int biggestDimension = 0;
      private ConstraintAnchor mBottom;
      private int mCount = 0;
      private int mHeight = 0;
      private ConstraintAnchor mLeft;
      private int mMax = 0;
      private int mNbMatchConstraintsWidgets = 0;
      private int mOrientation = 0;
      private int mPaddingBottom = 0;
      private int mPaddingLeft = 0;
      private int mPaddingRight = 0;
      private int mPaddingTop = 0;
      private ConstraintAnchor mRight;
      private int mStartIndex = 0;
      private ConstraintAnchor mTop;
      private int mWidth = 0;

      public WidgetsList(int var2, ConstraintAnchor var3, ConstraintAnchor var4, ConstraintAnchor var5, ConstraintAnchor var6, int var7) {
         this.mOrientation = var2;
         this.mLeft = var3;
         this.mTop = var4;
         this.mRight = var5;
         this.mBottom = var6;
         this.mPaddingLeft = Flow.this.getPaddingLeft();
         this.mPaddingTop = Flow.this.getPaddingTop();
         this.mPaddingRight = Flow.this.getPaddingRight();
         this.mPaddingBottom = Flow.this.getPaddingBottom();
         this.mMax = var7;
      }

      private void recomputeDimensions() {
         this.mWidth = 0;
         this.mHeight = 0;
         this.biggest = null;
         this.biggestDimension = 0;
         int var1 = this.mCount;

         for(int var2 = 0; var2 < var1 && this.mStartIndex + var2 < Flow.this.mDisplayedWidgetsCount; ++var2) {
            ConstraintWidget var3 = Flow.this.mDisplayedWidgets[this.mStartIndex + var2];
            int var4;
            int var5;
            if (this.mOrientation == 0) {
               var4 = var3.getWidth();
               var5 = Flow.this.mHorizontalGap;
               if (var3.getVisibility() == 8) {
                  var5 = 0;
               }

               this.mWidth += var4 + var5;
               var5 = Flow.this.getWidgetHeight(var3, this.mMax);
               if (this.biggest == null || this.biggestDimension < var5) {
                  this.biggest = var3;
                  this.biggestDimension = var5;
                  this.mHeight = var5;
               }
            } else {
               int var6 = Flow.this.getWidgetWidth(var3, this.mMax);
               var4 = Flow.this.getWidgetHeight(var3, this.mMax);
               var5 = Flow.this.mVerticalGap;
               if (var3.getVisibility() == 8) {
                  var5 = 0;
               }

               this.mHeight += var4 + var5;
               if (this.biggest == null || this.biggestDimension < var6) {
                  this.biggest = var3;
                  this.biggestDimension = var6;
                  this.mWidth = var6;
               }
            }
         }

      }

      public void add(ConstraintWidget var1) {
         int var2 = this.mOrientation;
         int var3 = 0;
         byte var4 = 0;
         if (var2 == 0) {
            var2 = Flow.this.getWidgetWidth(var1, this.mMax);
            if (var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               ++this.mNbMatchConstraintsWidgets;
               var2 = 0;
            }

            var3 = Flow.this.mHorizontalGap;
            if (var1.getVisibility() == 8) {
               var3 = var4;
            }

            this.mWidth += var2 + var3;
            var2 = Flow.this.getWidgetHeight(var1, this.mMax);
            if (this.biggest == null || this.biggestDimension < var2) {
               this.biggest = var1;
               this.biggestDimension = var2;
               this.mHeight = var2;
            }
         } else {
            int var5 = Flow.this.getWidgetWidth(var1, this.mMax);
            var2 = Flow.this.getWidgetHeight(var1, this.mMax);
            if (var1.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               ++this.mNbMatchConstraintsWidgets;
               var2 = 0;
            }

            int var6 = Flow.this.mVerticalGap;
            if (var1.getVisibility() != 8) {
               var3 = var6;
            }

            this.mHeight += var2 + var3;
            if (this.biggest == null || this.biggestDimension < var5) {
               this.biggest = var1;
               this.biggestDimension = var5;
               this.mWidth = var5;
            }
         }

         ++this.mCount;
      }

      public void clear() {
         this.biggestDimension = 0;
         this.biggest = null;
         this.mWidth = 0;
         this.mHeight = 0;
         this.mStartIndex = 0;
         this.mCount = 0;
         this.mNbMatchConstraintsWidgets = 0;
      }

      public void createConstraints(boolean var1, int var2, boolean var3) {
         int var4 = this.mCount;

         int var5;
         ConstraintWidget var6;
         for(var5 = 0; var5 < var4 && this.mStartIndex + var5 < Flow.this.mDisplayedWidgetsCount; ++var5) {
            var6 = Flow.this.mDisplayedWidgets[this.mStartIndex + var5];
            if (var6 != null) {
               var6.resetAnchors();
            }
         }

         if (var4 != 0 && this.biggest != null) {
            boolean var7;
            if (var3 && var2 == 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            var5 = 0;
            int var8 = -1;

            int var9;
            int var12;
            for(var9 = -1; var5 < var4; var9 = var12) {
               int var10;
               if (var1) {
                  var10 = var4 - 1 - var5;
               } else {
                  var10 = var5;
               }

               if (this.mStartIndex + var10 >= Flow.this.mDisplayedWidgetsCount) {
                  break;
               }

               int var11 = var8;
               var12 = var9;
               if (Flow.this.mDisplayedWidgets[this.mStartIndex + var10].getVisibility() == 0) {
                  var9 = var8;
                  if (var8 == -1) {
                     var9 = var5;
                  }

                  var12 = var5;
                  var11 = var9;
               }

               ++var5;
               var8 = var11;
            }

            var6 = null;
            ConstraintWidget var13 = null;
            ConstraintWidget var14;
            float var16;
            float var17;
            if (this.mOrientation == 0) {
               var14 = this.biggest;
               var14.setVerticalChainStyle(Flow.this.mVerticalStyle);
               var12 = this.mPaddingTop;
               var5 = var12;
               if (var2 > 0) {
                  var5 = var12 + Flow.this.mVerticalGap;
               }

               var14.mTop.connect(this.mTop, var5);
               if (var3) {
                  var14.mBottom.connect(this.mBottom, this.mPaddingBottom);
               }

               if (var2 > 0) {
                  this.mTop.mOwner.mBottom.connect(var14.mTop, 0);
               }

               label249: {
                  if (Flow.this.mVerticalAlign == 3 && !var14.hasBaseline()) {
                     for(var2 = 0; var2 < var4; ++var2) {
                        if (var1) {
                           var5 = var4 - 1 - var2;
                        } else {
                           var5 = var2;
                        }

                        if (this.mStartIndex + var5 >= Flow.this.mDisplayedWidgetsCount) {
                           break;
                        }

                        var6 = Flow.this.mDisplayedWidgets[this.mStartIndex + var5];
                        if (var6.hasBaseline()) {
                           break label249;
                        }
                     }
                  }

                  var6 = var14;
               }

               ConstraintWidget var15;
               for(var2 = 0; var2 < var4; var13 = var15) {
                  if (var1) {
                     var5 = var4 - 1 - var2;
                  } else {
                     var5 = var2;
                  }

                  if (this.mStartIndex + var5 >= Flow.this.mDisplayedWidgetsCount) {
                     break;
                  }

                  var15 = Flow.this.mDisplayedWidgets[this.mStartIndex + var5];
                  if (var2 == 0) {
                     var15.connect(var15.mLeft, this.mLeft, this.mPaddingLeft);
                  }

                  if (var5 == 0) {
                     var12 = Flow.this.mHorizontalStyle;
                     var16 = Flow.this.mHorizontalBias;
                     if (this.mStartIndex == 0 && Flow.this.mFirstHorizontalStyle != -1) {
                        var5 = Flow.this.mFirstHorizontalStyle;
                        var17 = Flow.this.mFirstHorizontalBias;
                     } else {
                        var5 = var12;
                        var17 = var16;
                        if (var3) {
                           var5 = var12;
                           var17 = var16;
                           if (Flow.this.mLastHorizontalStyle != -1) {
                              var5 = Flow.this.mLastHorizontalStyle;
                              var17 = Flow.this.mLastHorizontalBias;
                           }
                        }
                     }

                     var15.setHorizontalChainStyle(var5);
                     var15.setHorizontalBiasPercent(var17);
                  }

                  if (var2 == var4 - 1) {
                     var15.connect(var15.mRight, this.mRight, this.mPaddingRight);
                  }

                  if (var13 != null) {
                     var15.mLeft.connect(var13.mRight, Flow.this.mHorizontalGap);
                     if (var2 == var8) {
                        var15.mLeft.setGoneMargin(this.mPaddingLeft);
                     }

                     var13.mRight.connect(var15.mLeft, 0);
                     if (var2 == var9 + 1) {
                        var13.mRight.setGoneMargin(this.mPaddingRight);
                     }
                  }

                  if (var15 != var14) {
                     if (Flow.this.mVerticalAlign == 3 && var6.hasBaseline() && var15 != var6 && var15.hasBaseline()) {
                        var15.mBaseline.connect(var6.mBaseline, 0);
                     } else {
                        var5 = Flow.this.mVerticalAlign;
                        if (var5 != 0) {
                           if (var5 != 1) {
                              if (var7) {
                                 var15.mTop.connect(this.mTop, this.mPaddingTop);
                                 var15.mBottom.connect(this.mBottom, this.mPaddingBottom);
                              } else {
                                 var15.mTop.connect(var14.mTop, 0);
                                 var15.mBottom.connect(var14.mBottom, 0);
                              }
                           } else {
                              var15.mBottom.connect(var14.mBottom, 0);
                           }
                        } else {
                           var15.mTop.connect(var14.mTop, 0);
                        }
                     }
                  }

                  ++var2;
               }
            } else {
               var14 = this.biggest;
               var14.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
               var12 = this.mPaddingLeft;
               var5 = var12;
               if (var2 > 0) {
                  var5 = var12 + Flow.this.mHorizontalGap;
               }

               if (var1) {
                  var14.mRight.connect(this.mRight, var5);
                  if (var3) {
                     var14.mLeft.connect(this.mLeft, this.mPaddingRight);
                  }

                  if (var2 > 0) {
                     this.mRight.mOwner.mLeft.connect(var14.mRight, 0);
                  }
               } else {
                  var14.mLeft.connect(this.mLeft, var5);
                  if (var3) {
                     var14.mRight.connect(this.mRight, this.mPaddingRight);
                  }

                  if (var2 > 0) {
                     this.mLeft.mOwner.mRight.connect(var14.mLeft, 0);
                  }
               }

               for(var5 = 0; var5 < var4 && this.mStartIndex + var5 < Flow.this.mDisplayedWidgetsCount; var6 = var13) {
                  var13 = Flow.this.mDisplayedWidgets[this.mStartIndex + var5];
                  if (var5 == 0) {
                     var13.connect(var13.mTop, this.mTop, this.mPaddingTop);
                     var12 = Flow.this.mVerticalStyle;
                     var16 = Flow.this.mVerticalBias;
                     if (this.mStartIndex == 0 && Flow.this.mFirstVerticalStyle != -1) {
                        var2 = Flow.this.mFirstVerticalStyle;
                        var17 = Flow.this.mFirstVerticalBias;
                     } else {
                        var2 = var12;
                        var17 = var16;
                        if (var3) {
                           var2 = var12;
                           var17 = var16;
                           if (Flow.this.mLastVerticalStyle != -1) {
                              var2 = Flow.this.mLastVerticalStyle;
                              var17 = Flow.this.mLastVerticalBias;
                           }
                        }
                     }

                     var13.setVerticalChainStyle(var2);
                     var13.setVerticalBiasPercent(var17);
                  }

                  if (var5 == var4 - 1) {
                     var13.connect(var13.mBottom, this.mBottom, this.mPaddingBottom);
                  }

                  if (var6 != null) {
                     var13.mTop.connect(var6.mBottom, Flow.this.mVerticalGap);
                     if (var5 == var8) {
                        var13.mTop.setGoneMargin(this.mPaddingTop);
                     }

                     var6.mBottom.connect(var13.mTop, 0);
                     if (var5 == var9 + 1) {
                        var6.mBottom.setGoneMargin(this.mPaddingBottom);
                     }
                  }

                  if (var13 != var14) {
                     if (var1) {
                        var2 = Flow.this.mHorizontalAlign;
                        if (var2 != 0) {
                           if (var2 != 1) {
                              if (var2 == 2) {
                                 var13.mLeft.connect(var14.mLeft, 0);
                                 var13.mRight.connect(var14.mRight, 0);
                              }
                           } else {
                              var13.mLeft.connect(var14.mLeft, 0);
                           }
                        } else {
                           var13.mRight.connect(var14.mRight, 0);
                        }
                     } else {
                        var2 = Flow.this.mHorizontalAlign;
                        if (var2 != 0) {
                           if (var2 != 1) {
                              if (var2 == 2) {
                                 if (var7) {
                                    var13.mLeft.connect(this.mLeft, this.mPaddingLeft);
                                    var13.mRight.connect(this.mRight, this.mPaddingRight);
                                 } else {
                                    var13.mLeft.connect(var14.mLeft, 0);
                                    var13.mRight.connect(var14.mRight, 0);
                                 }
                              }
                           } else {
                              var13.mRight.connect(var14.mRight, 0);
                           }
                        } else {
                           var13.mLeft.connect(var14.mLeft, 0);
                        }
                     }
                  }

                  ++var5;
               }
            }
         }

      }

      public int getHeight() {
         return this.mOrientation == 1 ? this.mHeight - Flow.this.mVerticalGap : this.mHeight;
      }

      public int getWidth() {
         return this.mOrientation == 0 ? this.mWidth - Flow.this.mHorizontalGap : this.mWidth;
      }

      public void measureMatchConstraints(int var1) {
         int var2 = this.mNbMatchConstraintsWidgets;
         if (var2 != 0) {
            int var3 = this.mCount;
            var2 = var1 / var2;

            for(var1 = 0; var1 < var3 && this.mStartIndex + var1 < Flow.this.mDisplayedWidgetsCount; ++var1) {
               ConstraintWidget var4 = Flow.this.mDisplayedWidgets[this.mStartIndex + var1];
               if (this.mOrientation == 0) {
                  if (var4 != null && var4.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var4.mMatchConstraintDefaultWidth == 0) {
                     Flow.this.measure(var4, ConstraintWidget.DimensionBehaviour.FIXED, var2, var4.getVerticalDimensionBehaviour(), var4.getHeight());
                  }
               } else if (var4 != null && var4.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var4.mMatchConstraintDefaultHeight == 0) {
                  Flow.this.measure(var4, var4.getHorizontalDimensionBehaviour(), var4.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, var2);
               }
            }

            this.recomputeDimensions();
         }
      }

      public void setStartIndex(int var1) {
         this.mStartIndex = var1;
      }

      public void setup(int var1, ConstraintAnchor var2, ConstraintAnchor var3, ConstraintAnchor var4, ConstraintAnchor var5, int var6, int var7, int var8, int var9, int var10) {
         this.mOrientation = var1;
         this.mLeft = var2;
         this.mTop = var3;
         this.mRight = var4;
         this.mBottom = var5;
         this.mPaddingLeft = var6;
         this.mPaddingTop = var7;
         this.mPaddingRight = var8;
         this.mPaddingBottom = var9;
         this.mMax = var10;
      }
   }
}
