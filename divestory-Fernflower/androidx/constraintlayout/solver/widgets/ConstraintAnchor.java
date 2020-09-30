package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintAnchor {
   private static final boolean ALLOW_BINARY = false;
   private static final int UNSET_GONE_MARGIN = -1;
   private HashSet<ConstraintAnchor> mDependents = null;
   int mGoneMargin = -1;
   public int mMargin = 0;
   public final ConstraintWidget mOwner;
   SolverVariable mSolverVariable;
   public ConstraintAnchor mTarget;
   public final ConstraintAnchor.Type mType;

   public ConstraintAnchor(ConstraintWidget var1, ConstraintAnchor.Type var2) {
      this.mOwner = var1;
      this.mType = var2;
   }

   private boolean isConnectionToMe(ConstraintWidget var1, HashSet<ConstraintWidget> var2) {
      if (var2.contains(var1)) {
         return false;
      } else {
         var2.add(var1);
         if (var1 == this.getOwner()) {
            return true;
         } else {
            ArrayList var6 = var1.getAnchors();
            int var3 = var6.size();

            for(int var4 = 0; var4 < var3; ++var4) {
               ConstraintAnchor var5 = (ConstraintAnchor)var6.get(var4);
               if (var5.isSimilarDimensionConnection(this) && var5.isConnected() && this.isConnectionToMe(var5.getTarget().getOwner(), var2)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean connect(ConstraintAnchor var1, int var2) {
      return this.connect(var1, var2, -1, false);
   }

   public boolean connect(ConstraintAnchor var1, int var2, int var3, boolean var4) {
      if (var1 == null) {
         this.reset();
         return true;
      } else if (!var4 && !this.isValidConnection(var1)) {
         return false;
      } else {
         this.mTarget = var1;
         if (var1.mDependents == null) {
            var1.mDependents = new HashSet();
         }

         this.mTarget.mDependents.add(this);
         if (var2 > 0) {
            this.mMargin = var2;
         } else {
            this.mMargin = 0;
         }

         this.mGoneMargin = var3;
         return true;
      }
   }

   public void copyFrom(ConstraintAnchor var1, HashMap<ConstraintWidget, ConstraintWidget> var2) {
      ConstraintAnchor var3 = this.mTarget;
      if (var3 != null) {
         HashSet var5 = var3.mDependents;
         if (var5 != null) {
            var5.remove(this);
         }
      }

      var3 = var1.mTarget;
      if (var3 != null) {
         ConstraintAnchor.Type var6 = var3.getType();
         this.mTarget = ((ConstraintWidget)var2.get(var1.mTarget.mOwner)).getAnchor(var6);
      } else {
         this.mTarget = null;
      }

      ConstraintAnchor var4 = this.mTarget;
      if (var4 != null) {
         if (var4.mDependents == null) {
            var4.mDependents = new HashSet();
         }

         this.mTarget.mDependents.add(this);
      }

      this.mMargin = var1.mMargin;
      this.mGoneMargin = var1.mGoneMargin;
   }

   public int getMargin() {
      if (this.mOwner.getVisibility() == 8) {
         return 0;
      } else {
         if (this.mGoneMargin > -1) {
            ConstraintAnchor var1 = this.mTarget;
            if (var1 != null && var1.mOwner.getVisibility() == 8) {
               return this.mGoneMargin;
            }
         }

         return this.mMargin;
      }
   }

   public final ConstraintAnchor getOpposite() {
      switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 1:
      case 6:
      case 7:
      case 8:
      case 9:
         return null;
      case 2:
         return this.mOwner.mRight;
      case 3:
         return this.mOwner.mLeft;
      case 4:
         return this.mOwner.mBottom;
      case 5:
         return this.mOwner.mTop;
      default:
         throw new AssertionError(this.mType.name());
      }
   }

   public ConstraintWidget getOwner() {
      return this.mOwner;
   }

   public SolverVariable getSolverVariable() {
      return this.mSolverVariable;
   }

   public ConstraintAnchor getTarget() {
      return this.mTarget;
   }

   public ConstraintAnchor.Type getType() {
      return this.mType;
   }

   public boolean hasCenteredDependents() {
      HashSet var1 = this.mDependents;
      if (var1 == null) {
         return false;
      } else {
         Iterator var2 = var1.iterator();

         do {
            if (!var2.hasNext()) {
               return false;
            }
         } while(!((ConstraintAnchor)var2.next()).getOpposite().isConnected());

         return true;
      }
   }

   public boolean hasDependents() {
      HashSet var1 = this.mDependents;
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         if (var1.size() > 0) {
            var2 = true;
         }

         return var2;
      }
   }

   public boolean isConnected() {
      boolean var1;
      if (this.mTarget != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isConnectionAllowed(ConstraintWidget var1) {
      if (this.isConnectionToMe(var1, new HashSet())) {
         return false;
      } else {
         ConstraintWidget var2 = this.getOwner().getParent();
         if (var2 == var1) {
            return true;
         } else {
            return var1.getParent() == var2;
         }
      }
   }

   public boolean isConnectionAllowed(ConstraintWidget var1, ConstraintAnchor var2) {
      return this.isConnectionAllowed(var1);
   }

   public boolean isSideAnchor() {
      switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 1:
      case 6:
      case 7:
      case 8:
      case 9:
         return false;
      case 2:
      case 3:
      case 4:
      case 5:
         return true;
      default:
         throw new AssertionError(this.mType.name());
      }
   }

   public boolean isSimilarDimensionConnection(ConstraintAnchor var1) {
      ConstraintAnchor.Type var6 = var1.getType();
      ConstraintAnchor.Type var2 = this.mType;
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      if (var6 == var2) {
         return true;
      } else {
         switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
         case 1:
            if (var6 == ConstraintAnchor.Type.BASELINE) {
               var4 = false;
            }

            return var4;
         case 2:
         case 3:
         case 7:
            var4 = var3;
            if (var6 != ConstraintAnchor.Type.LEFT) {
               var4 = var3;
               if (var6 != ConstraintAnchor.Type.RIGHT) {
                  if (var6 == ConstraintAnchor.Type.CENTER_X) {
                     var4 = var3;
                  } else {
                     var4 = false;
                  }
               }
            }

            return var4;
         case 4:
         case 5:
         case 6:
         case 8:
            var4 = var5;
            if (var6 != ConstraintAnchor.Type.TOP) {
               var4 = var5;
               if (var6 != ConstraintAnchor.Type.BOTTOM) {
                  var4 = var5;
                  if (var6 != ConstraintAnchor.Type.CENTER_Y) {
                     if (var6 == ConstraintAnchor.Type.BASELINE) {
                        var4 = var5;
                     } else {
                        var4 = false;
                     }
                  }
               }
            }

            return var4;
         case 9:
            return false;
         default:
            throw new AssertionError(this.mType.name());
         }
      }
   }

   public boolean isValidConnection(ConstraintAnchor var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      if (var1 == null) {
         return false;
      } else {
         ConstraintAnchor.Type var5 = var1.getType();
         ConstraintAnchor.Type var6 = this.mType;
         if (var5 == var6) {
            return var6 != ConstraintAnchor.Type.BASELINE || var1.getOwner().hasBaseline() && this.getOwner().hasBaseline();
         } else {
            boolean var7;
            switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
            case 1:
               var7 = var3;
               if (var5 != ConstraintAnchor.Type.BASELINE) {
                  var7 = var3;
                  if (var5 != ConstraintAnchor.Type.CENTER_X) {
                     var7 = var3;
                     if (var5 != ConstraintAnchor.Type.CENTER_Y) {
                        var7 = true;
                     }
                  }
               }

               return var7;
            case 2:
            case 3:
               if (var5 != ConstraintAnchor.Type.LEFT && var5 != ConstraintAnchor.Type.RIGHT) {
                  var7 = false;
               } else {
                  var7 = true;
               }

               var3 = var7;
               if (var1.getOwner() instanceof Guideline) {
                  label76: {
                     if (!var7) {
                        var7 = var2;
                        if (var5 != ConstraintAnchor.Type.CENTER_X) {
                           break label76;
                        }
                     }

                     var7 = true;
                  }

                  var3 = var7;
               }

               return var3;
            case 4:
            case 5:
               if (var5 != ConstraintAnchor.Type.TOP && var5 != ConstraintAnchor.Type.BOTTOM) {
                  var7 = false;
               } else {
                  var7 = true;
               }

               var3 = var7;
               if (var1.getOwner() instanceof Guideline) {
                  label60: {
                     if (!var7) {
                        var7 = var4;
                        if (var5 != ConstraintAnchor.Type.CENTER_Y) {
                           break label60;
                        }
                     }

                     var7 = true;
                  }

                  var3 = var7;
               }

               return var3;
            case 6:
            case 7:
            case 8:
            case 9:
               return false;
            default:
               throw new AssertionError(this.mType.name());
            }
         }
      }
   }

   public boolean isVerticalAnchor() {
      switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[this.mType.ordinal()]) {
      case 1:
      case 2:
      case 3:
      case 7:
         return false;
      case 4:
      case 5:
      case 6:
      case 8:
      case 9:
         return true;
      default:
         throw new AssertionError(this.mType.name());
      }
   }

   public void reset() {
      ConstraintAnchor var1 = this.mTarget;
      if (var1 != null) {
         HashSet var2 = var1.mDependents;
         if (var2 != null) {
            var2.remove(this);
         }
      }

      this.mTarget = null;
      this.mMargin = 0;
      this.mGoneMargin = -1;
   }

   public void resetSolverVariable(Cache var1) {
      SolverVariable var2 = this.mSolverVariable;
      if (var2 == null) {
         this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED, (String)null);
      } else {
         var2.reset();
      }

   }

   public void setGoneMargin(int var1) {
      if (this.isConnected()) {
         this.mGoneMargin = var1;
      }

   }

   public void setMargin(int var1) {
      if (this.isConnected()) {
         this.mMargin = var1;
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.mOwner.getDebugName());
      var1.append(":");
      var1.append(this.mType.toString());
      return var1.toString();
   }

   public static enum Type {
      BASELINE,
      BOTTOM,
      CENTER,
      CENTER_X,
      CENTER_Y,
      LEFT,
      NONE,
      RIGHT,
      TOP;

      static {
         ConstraintAnchor.Type var0 = new ConstraintAnchor.Type("CENTER_Y", 8);
         CENTER_Y = var0;
      }
   }
}
