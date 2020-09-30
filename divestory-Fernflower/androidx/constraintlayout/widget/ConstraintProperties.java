package androidx.constraintlayout.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ConstraintProperties {
   public static final int BASELINE = 5;
   public static final int BOTTOM = 4;
   public static final int END = 7;
   public static final int LEFT = 1;
   public static final int MATCH_CONSTRAINT = 0;
   public static final int MATCH_CONSTRAINT_SPREAD = 0;
   public static final int MATCH_CONSTRAINT_WRAP = 1;
   public static final int PARENT_ID = 0;
   public static final int RIGHT = 2;
   public static final int START = 6;
   public static final int TOP = 3;
   public static final int UNSET = -1;
   public static final int WRAP_CONTENT = -2;
   ConstraintLayout.LayoutParams mParams;
   View mView;

   public ConstraintProperties(View var1) {
      LayoutParams var2 = var1.getLayoutParams();
      if (var2 instanceof ConstraintLayout.LayoutParams) {
         this.mParams = (ConstraintLayout.LayoutParams)var2;
         this.mView = var1;
      } else {
         throw new RuntimeException("Only children of ConstraintLayout.LayoutParams supported");
      }
   }

   private String sideToString(int var1) {
      switch(var1) {
      case 1:
         return "left";
      case 2:
         return "right";
      case 3:
         return "top";
      case 4:
         return "bottom";
      case 5:
         return "baseline";
      case 6:
         return "start";
      case 7:
         return "end";
      default:
         return "undefined";
      }
   }

   public ConstraintProperties addToHorizontalChain(int var1, int var2) {
      byte var3;
      if (var1 == 0) {
         var3 = 1;
      } else {
         var3 = 2;
      }

      this.connect(1, var1, var3, 0);
      if (var2 == 0) {
         var3 = 2;
      } else {
         var3 = 1;
      }

      this.connect(2, var2, var3, 0);
      if (var1 != 0) {
         (new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var1))).connect(2, this.mView.getId(), 1, 0);
      }

      if (var2 != 0) {
         (new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var2))).connect(1, this.mView.getId(), 2, 0);
      }

      return this;
   }

   public ConstraintProperties addToHorizontalChainRTL(int var1, int var2) {
      byte var3;
      if (var1 == 0) {
         var3 = 6;
      } else {
         var3 = 7;
      }

      this.connect(6, var1, var3, 0);
      if (var2 == 0) {
         var3 = 7;
      } else {
         var3 = 6;
      }

      this.connect(7, var2, var3, 0);
      if (var1 != 0) {
         (new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var1))).connect(7, this.mView.getId(), 6, 0);
      }

      if (var2 != 0) {
         (new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var2))).connect(6, this.mView.getId(), 7, 0);
      }

      return this;
   }

   public ConstraintProperties addToVerticalChain(int var1, int var2) {
      byte var3;
      if (var1 == 0) {
         var3 = 3;
      } else {
         var3 = 4;
      }

      this.connect(3, var1, var3, 0);
      if (var2 == 0) {
         var3 = 4;
      } else {
         var3 = 3;
      }

      this.connect(4, var2, var3, 0);
      if (var1 != 0) {
         (new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var1))).connect(4, this.mView.getId(), 3, 0);
      }

      if (var2 != 0) {
         (new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var2))).connect(3, this.mView.getId(), 4, 0);
      }

      return this;
   }

   public ConstraintProperties alpha(float var1) {
      this.mView.setAlpha(var1);
      return this;
   }

   public void apply() {
   }

   public ConstraintProperties center(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      if (var3 >= 0) {
         if (var6 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
         } else if (var7 > 0.0F && var7 <= 1.0F) {
            if (var2 != 1 && var2 != 2) {
               if (var2 != 6 && var2 != 7) {
                  this.connect(3, var1, var2, var3);
                  this.connect(4, var4, var5, var6);
                  this.mParams.verticalBias = var7;
               } else {
                  this.connect(6, var1, var2, var3);
                  this.connect(7, var4, var5, var6);
                  this.mParams.horizontalBias = var7;
               }
            } else {
               this.connect(1, var1, var2, var3);
               this.connect(2, var4, var5, var6);
               this.mParams.horizontalBias = var7;
            }

            return this;
         } else {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
         }
      } else {
         throw new IllegalArgumentException("margin must be > 0");
      }
   }

   public ConstraintProperties centerHorizontally(int var1) {
      if (var1 == 0) {
         this.center(0, 1, 0, 0, 2, 0, 0.5F);
      } else {
         this.center(var1, 2, 0, var1, 1, 0, 0.5F);
      }

      return this;
   }

   public ConstraintProperties centerHorizontally(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      this.connect(1, var1, var2, var3);
      this.connect(2, var4, var5, var6);
      this.mParams.horizontalBias = var7;
      return this;
   }

   public ConstraintProperties centerHorizontallyRtl(int var1) {
      if (var1 == 0) {
         this.center(0, 6, 0, 0, 7, 0, 0.5F);
      } else {
         this.center(var1, 7, 0, var1, 6, 0, 0.5F);
      }

      return this;
   }

   public ConstraintProperties centerHorizontallyRtl(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      this.connect(6, var1, var2, var3);
      this.connect(7, var4, var5, var6);
      this.mParams.horizontalBias = var7;
      return this;
   }

   public ConstraintProperties centerVertically(int var1) {
      if (var1 == 0) {
         this.center(0, 3, 0, 0, 4, 0, 0.5F);
      } else {
         this.center(var1, 4, 0, var1, 3, 0, 0.5F);
      }

      return this;
   }

   public ConstraintProperties centerVertically(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      this.connect(3, var1, var2, var3);
      this.connect(4, var4, var5, var6);
      this.mParams.verticalBias = var7;
      return this;
   }

   public ConstraintProperties connect(int var1, int var2, int var3, int var4) {
      StringBuilder var5;
      switch(var1) {
      case 1:
         if (var3 == 1) {
            this.mParams.leftToLeft = var2;
            this.mParams.leftToRight = -1;
         } else {
            if (var3 != 2) {
               var5 = new StringBuilder();
               var5.append("Left to ");
               var5.append(this.sideToString(var3));
               var5.append(" undefined");
               throw new IllegalArgumentException(var5.toString());
            }

            this.mParams.leftToRight = var2;
            this.mParams.leftToLeft = -1;
         }

         this.mParams.leftMargin = var4;
         break;
      case 2:
         if (var3 == 1) {
            this.mParams.rightToLeft = var2;
            this.mParams.rightToRight = -1;
         } else {
            if (var3 != 2) {
               var5 = new StringBuilder();
               var5.append("right to ");
               var5.append(this.sideToString(var3));
               var5.append(" undefined");
               throw new IllegalArgumentException(var5.toString());
            }

            this.mParams.rightToRight = var2;
            this.mParams.rightToLeft = -1;
         }

         this.mParams.rightMargin = var4;
         break;
      case 3:
         if (var3 == 3) {
            this.mParams.topToTop = var2;
            this.mParams.topToBottom = -1;
            this.mParams.baselineToBaseline = -1;
         } else {
            if (var3 != 4) {
               var5 = new StringBuilder();
               var5.append("right to ");
               var5.append(this.sideToString(var3));
               var5.append(" undefined");
               throw new IllegalArgumentException(var5.toString());
            }

            this.mParams.topToBottom = var2;
            this.mParams.topToTop = -1;
            this.mParams.baselineToBaseline = -1;
         }

         this.mParams.topMargin = var4;
         break;
      case 4:
         if (var3 == 4) {
            this.mParams.bottomToBottom = var2;
            this.mParams.bottomToTop = -1;
            this.mParams.baselineToBaseline = -1;
         } else {
            if (var3 != 3) {
               var5 = new StringBuilder();
               var5.append("right to ");
               var5.append(this.sideToString(var3));
               var5.append(" undefined");
               throw new IllegalArgumentException(var5.toString());
            }

            this.mParams.bottomToTop = var2;
            this.mParams.bottomToBottom = -1;
            this.mParams.baselineToBaseline = -1;
         }

         this.mParams.bottomMargin = var4;
         break;
      case 5:
         if (var3 != 5) {
            var5 = new StringBuilder();
            var5.append("right to ");
            var5.append(this.sideToString(var3));
            var5.append(" undefined");
            throw new IllegalArgumentException(var5.toString());
         }

         this.mParams.baselineToBaseline = var2;
         this.mParams.bottomToBottom = -1;
         this.mParams.bottomToTop = -1;
         this.mParams.topToTop = -1;
         this.mParams.topToBottom = -1;
         break;
      case 6:
         if (var3 == 6) {
            this.mParams.startToStart = var2;
            this.mParams.startToEnd = -1;
         } else {
            if (var3 != 7) {
               var5 = new StringBuilder();
               var5.append("right to ");
               var5.append(this.sideToString(var3));
               var5.append(" undefined");
               throw new IllegalArgumentException(var5.toString());
            }

            this.mParams.startToEnd = var2;
            this.mParams.startToStart = -1;
         }

         if (VERSION.SDK_INT >= 17) {
            this.mParams.setMarginStart(var4);
         }
         break;
      case 7:
         if (var3 == 7) {
            this.mParams.endToEnd = var2;
            this.mParams.endToStart = -1;
         } else {
            if (var3 != 6) {
               var5 = new StringBuilder();
               var5.append("right to ");
               var5.append(this.sideToString(var3));
               var5.append(" undefined");
               throw new IllegalArgumentException(var5.toString());
            }

            this.mParams.endToStart = var2;
            this.mParams.endToEnd = -1;
         }

         if (VERSION.SDK_INT >= 17) {
            this.mParams.setMarginEnd(var4);
         }
         break;
      default:
         var5 = new StringBuilder();
         var5.append(this.sideToString(var1));
         var5.append(" to ");
         var5.append(this.sideToString(var3));
         var5.append(" unknown");
         throw new IllegalArgumentException(var5.toString());
      }

      return this;
   }

   public ConstraintProperties constrainDefaultHeight(int var1) {
      this.mParams.matchConstraintDefaultHeight = var1;
      return this;
   }

   public ConstraintProperties constrainDefaultWidth(int var1) {
      this.mParams.matchConstraintDefaultWidth = var1;
      return this;
   }

   public ConstraintProperties constrainHeight(int var1) {
      this.mParams.height = var1;
      return this;
   }

   public ConstraintProperties constrainMaxHeight(int var1) {
      this.mParams.matchConstraintMaxHeight = var1;
      return this;
   }

   public ConstraintProperties constrainMaxWidth(int var1) {
      this.mParams.matchConstraintMaxWidth = var1;
      return this;
   }

   public ConstraintProperties constrainMinHeight(int var1) {
      this.mParams.matchConstraintMinHeight = var1;
      return this;
   }

   public ConstraintProperties constrainMinWidth(int var1) {
      this.mParams.matchConstraintMinWidth = var1;
      return this;
   }

   public ConstraintProperties constrainWidth(int var1) {
      this.mParams.width = var1;
      return this;
   }

   public ConstraintProperties dimensionRatio(String var1) {
      this.mParams.dimensionRatio = var1;
      return this;
   }

   public ConstraintProperties elevation(float var1) {
      if (VERSION.SDK_INT >= 21) {
         this.mView.setElevation(var1);
      }

      return this;
   }

   public ConstraintProperties goneMargin(int var1, int var2) {
      switch(var1) {
      case 1:
         this.mParams.goneLeftMargin = var2;
         break;
      case 2:
         this.mParams.goneRightMargin = var2;
         break;
      case 3:
         this.mParams.goneTopMargin = var2;
         break;
      case 4:
         this.mParams.goneBottomMargin = var2;
         break;
      case 5:
         throw new IllegalArgumentException("baseline does not support margins");
      case 6:
         this.mParams.goneStartMargin = var2;
         break;
      case 7:
         this.mParams.goneEndMargin = var2;
         break;
      default:
         throw new IllegalArgumentException("unknown constraint");
      }

      return this;
   }

   public ConstraintProperties horizontalBias(float var1) {
      this.mParams.horizontalBias = var1;
      return this;
   }

   public ConstraintProperties horizontalChainStyle(int var1) {
      this.mParams.horizontalChainStyle = var1;
      return this;
   }

   public ConstraintProperties horizontalWeight(float var1) {
      this.mParams.horizontalWeight = var1;
      return this;
   }

   public ConstraintProperties margin(int var1, int var2) {
      switch(var1) {
      case 1:
         this.mParams.leftMargin = var2;
         break;
      case 2:
         this.mParams.rightMargin = var2;
         break;
      case 3:
         this.mParams.topMargin = var2;
         break;
      case 4:
         this.mParams.bottomMargin = var2;
         break;
      case 5:
         throw new IllegalArgumentException("baseline does not support margins");
      case 6:
         this.mParams.setMarginStart(var2);
         break;
      case 7:
         this.mParams.setMarginEnd(var2);
         break;
      default:
         throw new IllegalArgumentException("unknown constraint");
      }

      return this;
   }

   public ConstraintProperties removeConstraints(int var1) {
      switch(var1) {
      case 1:
         this.mParams.leftToRight = -1;
         this.mParams.leftToLeft = -1;
         this.mParams.leftMargin = -1;
         this.mParams.goneLeftMargin = -1;
         break;
      case 2:
         this.mParams.rightToRight = -1;
         this.mParams.rightToLeft = -1;
         this.mParams.rightMargin = -1;
         this.mParams.goneRightMargin = -1;
         break;
      case 3:
         this.mParams.topToBottom = -1;
         this.mParams.topToTop = -1;
         this.mParams.topMargin = -1;
         this.mParams.goneTopMargin = -1;
         break;
      case 4:
         this.mParams.bottomToTop = -1;
         this.mParams.bottomToBottom = -1;
         this.mParams.bottomMargin = -1;
         this.mParams.goneBottomMargin = -1;
         break;
      case 5:
         this.mParams.baselineToBaseline = -1;
         break;
      case 6:
         this.mParams.startToEnd = -1;
         this.mParams.startToStart = -1;
         this.mParams.setMarginStart(-1);
         this.mParams.goneStartMargin = -1;
         break;
      case 7:
         this.mParams.endToStart = -1;
         this.mParams.endToEnd = -1;
         this.mParams.setMarginEnd(-1);
         this.mParams.goneEndMargin = -1;
         break;
      default:
         throw new IllegalArgumentException("unknown constraint");
      }

      return this;
   }

   public ConstraintProperties removeFromHorizontalChain() {
      int var1 = this.mParams.leftToRight;
      int var2 = this.mParams.rightToLeft;
      ConstraintLayout.LayoutParams var3 = this.mParams;
      ConstraintProperties var5;
      ConstraintLayout.LayoutParams var6;
      ConstraintProperties var7;
      if (var1 == -1 && var2 == -1) {
         var2 = var3.startToEnd;
         int var4 = this.mParams.endToStart;
         if (var2 != -1 || var4 != -1) {
            var5 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var2));
            var7 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var4));
            if (var2 != -1 && var4 != -1) {
               var5.connect(7, var4, 6, 0);
               var7.connect(6, var1, 7, 0);
            } else if (var1 != -1 || var4 != -1) {
               var1 = this.mParams.rightToRight;
               var6 = this.mParams;
               if (var1 != -1) {
                  var5.connect(7, var6.rightToRight, 7, 0);
               } else {
                  var1 = var6.leftToLeft;
                  var6 = this.mParams;
                  if (var1 != -1) {
                     var7.connect(6, var6.leftToLeft, 6, 0);
                  }
               }
            }
         }

         this.removeConstraints(6);
         this.removeConstraints(7);
      } else {
         var5 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var1));
         var7 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var2));
         if (var1 != -1 && var2 != -1) {
            var5.connect(2, var2, 1, 0);
            var7.connect(1, var1, 2, 0);
         } else if (var1 != -1 || var2 != -1) {
            var1 = this.mParams.rightToRight;
            var6 = this.mParams;
            if (var1 != -1) {
               var5.connect(2, var6.rightToRight, 2, 0);
            } else {
               var1 = var6.leftToLeft;
               var6 = this.mParams;
               if (var1 != -1) {
                  var7.connect(1, var6.leftToLeft, 1, 0);
               }
            }
         }

         this.removeConstraints(1);
         this.removeConstraints(2);
      }

      return this;
   }

   public ConstraintProperties removeFromVerticalChain() {
      int var1 = this.mParams.topToBottom;
      int var2 = this.mParams.bottomToTop;
      if (var1 != -1 || var2 != -1) {
         ConstraintProperties var3 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var1));
         ConstraintProperties var4 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(var2));
         if (var1 != -1 && var2 != -1) {
            var3.connect(4, var2, 3, 0);
            var4.connect(3, var1, 4, 0);
         } else if (var1 != -1 || var2 != -1) {
            var1 = this.mParams.bottomToBottom;
            ConstraintLayout.LayoutParams var5 = this.mParams;
            if (var1 != -1) {
               var3.connect(4, var5.bottomToBottom, 4, 0);
            } else {
               var1 = var5.topToTop;
               var5 = this.mParams;
               if (var1 != -1) {
                  var4.connect(3, var5.topToTop, 3, 0);
               }
            }
         }
      }

      this.removeConstraints(3);
      this.removeConstraints(4);
      return this;
   }

   public ConstraintProperties rotation(float var1) {
      this.mView.setRotation(var1);
      return this;
   }

   public ConstraintProperties rotationX(float var1) {
      this.mView.setRotationX(var1);
      return this;
   }

   public ConstraintProperties rotationY(float var1) {
      this.mView.setRotationY(var1);
      return this;
   }

   public ConstraintProperties scaleX(float var1) {
      this.mView.setScaleY(var1);
      return this;
   }

   public ConstraintProperties scaleY(float var1) {
      return this;
   }

   public ConstraintProperties transformPivot(float var1, float var2) {
      this.mView.setPivotX(var1);
      this.mView.setPivotY(var2);
      return this;
   }

   public ConstraintProperties transformPivotX(float var1) {
      this.mView.setPivotX(var1);
      return this;
   }

   public ConstraintProperties transformPivotY(float var1) {
      this.mView.setPivotY(var1);
      return this;
   }

   public ConstraintProperties translation(float var1, float var2) {
      this.mView.setTranslationX(var1);
      this.mView.setTranslationY(var2);
      return this;
   }

   public ConstraintProperties translationX(float var1) {
      this.mView.setTranslationX(var1);
      return this;
   }

   public ConstraintProperties translationY(float var1) {
      this.mView.setTranslationY(var1);
      return this;
   }

   public ConstraintProperties translationZ(float var1) {
      if (VERSION.SDK_INT >= 21) {
         this.mView.setTranslationZ(var1);
      }

      return this;
   }

   public ConstraintProperties verticalBias(float var1) {
      this.mParams.verticalBias = var1;
      return this;
   }

   public ConstraintProperties verticalChainStyle(int var1) {
      this.mParams.verticalChainStyle = var1;
      return this;
   }

   public ConstraintProperties verticalWeight(float var1) {
      this.mParams.verticalWeight = var1;
      return this;
   }

   public ConstraintProperties visibility(int var1) {
      this.mView.setVisibility(var1);
      return this;
   }
}
