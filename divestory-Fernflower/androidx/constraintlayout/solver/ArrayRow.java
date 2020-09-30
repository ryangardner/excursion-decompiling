package androidx.constraintlayout.solver;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayRow implements LinearSystem.Row {
   private static final boolean DEBUG = false;
   private static final boolean FULL_NEW_CHECK = false;
   float constantValue = 0.0F;
   boolean isSimpleDefinition = false;
   boolean used = false;
   SolverVariable variable = null;
   public ArrayRow.ArrayRowVariables variables;
   ArrayList<SolverVariable> variablesToUpdate = new ArrayList();

   public ArrayRow() {
   }

   public ArrayRow(Cache var1) {
      this.variables = new ArrayLinkedVariables(this, var1);
   }

   private boolean isNew(SolverVariable var1, LinearSystem var2) {
      int var3 = var1.usageInRowCount;
      boolean var4 = true;
      if (var3 > 1) {
         var4 = false;
      }

      return var4;
   }

   private SolverVariable pickPivotInVariables(boolean[] var1, SolverVariable var2) {
      int var3 = this.variables.getCurrentSize();
      SolverVariable var4 = null;
      int var5 = 0;

      float var9;
      for(float var6 = 0.0F; var5 < var3; var6 = var9) {
         float var7 = this.variables.getVariableValue(var5);
         SolverVariable var8 = var4;
         var9 = var6;
         if (var7 < 0.0F) {
            label35: {
               SolverVariable var10 = this.variables.getVariable(var5);
               if (var1 != null) {
                  var8 = var4;
                  var9 = var6;
                  if (var1[var10.id]) {
                     break label35;
                  }
               }

               var8 = var4;
               var9 = var6;
               if (var10 != var2) {
                  label36: {
                     if (var10.mType != SolverVariable.Type.SLACK) {
                        var8 = var4;
                        var9 = var6;
                        if (var10.mType != SolverVariable.Type.ERROR) {
                           break label36;
                        }
                     }

                     var8 = var4;
                     var9 = var6;
                     if (var7 < var6) {
                        var9 = var7;
                        var8 = var10;
                     }
                  }
               }
            }
         }

         ++var5;
         var4 = var8;
      }

      return var4;
   }

   public ArrayRow addError(LinearSystem var1, int var2) {
      this.variables.put(var1.createErrorVariable(var2, "ep"), 1.0F);
      this.variables.put(var1.createErrorVariable(var2, "em"), -1.0F);
      return this;
   }

   public void addError(SolverVariable var1) {
      int var2 = var1.strength;
      float var3 = 1.0F;
      if (var2 != 1) {
         if (var1.strength == 2) {
            var3 = 1000.0F;
         } else if (var1.strength == 3) {
            var3 = 1000000.0F;
         } else if (var1.strength == 4) {
            var3 = 1.0E9F;
         } else if (var1.strength == 5) {
            var3 = 1.0E12F;
         }
      }

      this.variables.put(var1, var3);
   }

   ArrayRow addSingleError(SolverVariable var1, int var2) {
      this.variables.put(var1, (float)var2);
      return this;
   }

   boolean chooseSubject(LinearSystem var1) {
      SolverVariable var3 = this.chooseSubjectInVariables(var1);
      boolean var2;
      if (var3 == null) {
         var2 = true;
      } else {
         this.pivot(var3);
         var2 = false;
      }

      if (this.variables.getCurrentSize() == 0) {
         this.isSimpleDefinition = true;
      }

      return var2;
   }

   SolverVariable chooseSubjectInVariables(LinearSystem var1) {
      int var2 = this.variables.getCurrentSize();
      SolverVariable var3 = null;
      SolverVariable var4 = null;
      int var5 = 0;
      boolean var6 = false;
      boolean var7 = false;
      float var8 = 0.0F;

      float var17;
      for(float var9 = 0.0F; var5 < var2; var9 = var17) {
         float var10 = this.variables.getVariableValue(var5);
         SolverVariable var11 = this.variables.getVariable(var5);
         boolean var12;
         SolverVariable var13;
         SolverVariable var14;
         boolean var15;
         float var16;
         if (var11.mType == SolverVariable.Type.UNRESTRICTED) {
            label40: {
               if (var3 == null) {
                  var12 = this.isNew(var11, var1);
               } else {
                  if (var8 <= var10) {
                     var13 = var3;
                     var14 = var4;
                     var12 = var6;
                     var15 = var7;
                     var16 = var8;
                     var17 = var9;
                     if (!var6) {
                        var13 = var3;
                        var14 = var4;
                        var12 = var6;
                        var15 = var7;
                        var16 = var8;
                        var17 = var9;
                        if (this.isNew(var11, var1)) {
                           var12 = true;
                           var13 = var11;
                           var14 = var4;
                           var15 = var7;
                           var16 = var10;
                           var17 = var9;
                        }
                     }
                     break label40;
                  }

                  var12 = this.isNew(var11, var1);
               }

               var13 = var11;
               var14 = var4;
               var15 = var7;
               var16 = var10;
               var17 = var9;
            }
         } else {
            var13 = var3;
            var14 = var4;
            var12 = var6;
            var15 = var7;
            var16 = var8;
            var17 = var9;
            if (var3 == null) {
               var13 = var3;
               var14 = var4;
               var12 = var6;
               var15 = var7;
               var16 = var8;
               var17 = var9;
               if (var10 < 0.0F) {
                  label59: {
                     if (var4 == null) {
                        var12 = this.isNew(var11, var1);
                     } else {
                        if (var9 <= var10) {
                           var13 = var3;
                           var14 = var4;
                           var12 = var6;
                           var15 = var7;
                           var16 = var8;
                           var17 = var9;
                           if (!var7) {
                              var13 = var3;
                              var14 = var4;
                              var12 = var6;
                              var15 = var7;
                              var16 = var8;
                              var17 = var9;
                              if (this.isNew(var11, var1)) {
                                 var15 = true;
                                 var17 = var10;
                                 var16 = var8;
                                 var12 = var6;
                                 var14 = var11;
                                 var13 = var3;
                              }
                           }
                           break label59;
                        }

                        var12 = this.isNew(var11, var1);
                     }

                     var15 = var12;
                     var13 = var3;
                     var14 = var11;
                     var12 = var6;
                     var16 = var8;
                     var17 = var10;
                  }
               }
            }
         }

         ++var5;
         var3 = var13;
         var4 = var14;
         var6 = var12;
         var7 = var15;
         var8 = var16;
      }

      if (var3 != null) {
         return var3;
      } else {
         return var4;
      }
   }

   public void clear() {
      this.variables.clear();
      this.variable = null;
      this.constantValue = 0.0F;
   }

   ArrayRow createRowCentering(SolverVariable var1, SolverVariable var2, int var3, float var4, SolverVariable var5, SolverVariable var6, int var7) {
      if (var2 == var5) {
         this.variables.put(var1, 1.0F);
         this.variables.put(var6, 1.0F);
         this.variables.put(var2, -2.0F);
         return this;
      } else {
         if (var4 == 0.5F) {
            this.variables.put(var1, 1.0F);
            this.variables.put(var2, -1.0F);
            this.variables.put(var5, -1.0F);
            this.variables.put(var6, 1.0F);
            if (var3 > 0 || var7 > 0) {
               this.constantValue = (float)(-var3 + var7);
            }
         } else if (var4 <= 0.0F) {
            this.variables.put(var1, -1.0F);
            this.variables.put(var2, 1.0F);
            this.constantValue = (float)var3;
         } else if (var4 >= 1.0F) {
            this.variables.put(var6, -1.0F);
            this.variables.put(var5, 1.0F);
            this.constantValue = (float)(-var7);
         } else {
            ArrayRow.ArrayRowVariables var8 = this.variables;
            float var9 = 1.0F - var4;
            var8.put(var1, var9 * 1.0F);
            this.variables.put(var2, var9 * -1.0F);
            this.variables.put(var5, -1.0F * var4);
            this.variables.put(var6, 1.0F * var4);
            if (var3 > 0 || var7 > 0) {
               this.constantValue = (float)(-var3) * var9 + (float)var7 * var4;
            }
         }

         return this;
      }
   }

   ArrayRow createRowDefinition(SolverVariable var1, int var2) {
      this.variable = var1;
      float var3 = (float)var2;
      var1.computedValue = var3;
      this.constantValue = var3;
      this.isSimpleDefinition = true;
      return this;
   }

   ArrayRow createRowDimensionPercent(SolverVariable var1, SolverVariable var2, float var3) {
      this.variables.put(var1, -1.0F);
      this.variables.put(var2, var3);
      return this;
   }

   public ArrayRow createRowDimensionRatio(SolverVariable var1, SolverVariable var2, SolverVariable var3, SolverVariable var4, float var5) {
      this.variables.put(var1, -1.0F);
      this.variables.put(var2, 1.0F);
      this.variables.put(var3, var5);
      this.variables.put(var4, -var5);
      return this;
   }

   public ArrayRow createRowEqualDimension(float var1, float var2, float var3, SolverVariable var4, int var5, SolverVariable var6, int var7, SolverVariable var8, int var9, SolverVariable var10, int var11) {
      if (var2 != 0.0F && var1 != var3) {
         var1 = var1 / var2 / (var3 / var2);
         this.constantValue = (float)(-var5 - var7) + (float)var9 * var1 + (float)var11 * var1;
         this.variables.put(var4, 1.0F);
         this.variables.put(var6, -1.0F);
         this.variables.put(var10, var1);
         this.variables.put(var8, -var1);
      } else {
         this.constantValue = (float)(-var5 - var7 + var9 + var11);
         this.variables.put(var4, 1.0F);
         this.variables.put(var6, -1.0F);
         this.variables.put(var10, 1.0F);
         this.variables.put(var8, -1.0F);
      }

      return this;
   }

   public ArrayRow createRowEqualMatchDimensions(float var1, float var2, float var3, SolverVariable var4, SolverVariable var5, SolverVariable var6, SolverVariable var7) {
      this.constantValue = 0.0F;
      if (var2 != 0.0F && var1 != var3) {
         if (var1 == 0.0F) {
            this.variables.put(var4, 1.0F);
            this.variables.put(var5, -1.0F);
         } else if (var3 == 0.0F) {
            this.variables.put(var6, 1.0F);
            this.variables.put(var7, -1.0F);
         } else {
            var1 = var1 / var2 / (var3 / var2);
            this.variables.put(var4, 1.0F);
            this.variables.put(var5, -1.0F);
            this.variables.put(var7, var1);
            this.variables.put(var6, -var1);
         }
      } else {
         this.variables.put(var4, 1.0F);
         this.variables.put(var5, -1.0F);
         this.variables.put(var7, 1.0F);
         this.variables.put(var6, -1.0F);
      }

      return this;
   }

   public ArrayRow createRowEquals(SolverVariable var1, int var2) {
      if (var2 < 0) {
         this.constantValue = (float)(var2 * -1);
         this.variables.put(var1, 1.0F);
      } else {
         this.constantValue = (float)var2;
         this.variables.put(var1, -1.0F);
      }

      return this;
   }

   public ArrayRow createRowEquals(SolverVariable var1, SolverVariable var2, int var3) {
      boolean var4 = false;
      boolean var5 = false;
      if (var3 != 0) {
         var4 = var5;
         int var6 = var3;
         if (var3 < 0) {
            var6 = var3 * -1;
            var4 = true;
         }

         this.constantValue = (float)var6;
      }

      if (!var4) {
         this.variables.put(var1, -1.0F);
         this.variables.put(var2, 1.0F);
      } else {
         this.variables.put(var1, 1.0F);
         this.variables.put(var2, -1.0F);
      }

      return this;
   }

   public ArrayRow createRowGreaterThan(SolverVariable var1, int var2, SolverVariable var3) {
      this.constantValue = (float)var2;
      this.variables.put(var1, -1.0F);
      return this;
   }

   public ArrayRow createRowGreaterThan(SolverVariable var1, SolverVariable var2, SolverVariable var3, int var4) {
      boolean var5 = false;
      boolean var6 = false;
      if (var4 != 0) {
         var5 = var6;
         int var7 = var4;
         if (var4 < 0) {
            var7 = var4 * -1;
            var5 = true;
         }

         this.constantValue = (float)var7;
      }

      if (!var5) {
         this.variables.put(var1, -1.0F);
         this.variables.put(var2, 1.0F);
         this.variables.put(var3, 1.0F);
      } else {
         this.variables.put(var1, 1.0F);
         this.variables.put(var2, -1.0F);
         this.variables.put(var3, -1.0F);
      }

      return this;
   }

   public ArrayRow createRowLowerThan(SolverVariable var1, SolverVariable var2, SolverVariable var3, int var4) {
      boolean var5 = false;
      boolean var6 = false;
      if (var4 != 0) {
         var5 = var6;
         int var7 = var4;
         if (var4 < 0) {
            var7 = var4 * -1;
            var5 = true;
         }

         this.constantValue = (float)var7;
      }

      if (!var5) {
         this.variables.put(var1, -1.0F);
         this.variables.put(var2, 1.0F);
         this.variables.put(var3, -1.0F);
      } else {
         this.variables.put(var1, 1.0F);
         this.variables.put(var2, -1.0F);
         this.variables.put(var3, 1.0F);
      }

      return this;
   }

   public ArrayRow createRowWithAngle(SolverVariable var1, SolverVariable var2, SolverVariable var3, SolverVariable var4, float var5) {
      this.variables.put(var3, 0.5F);
      this.variables.put(var4, 0.5F);
      this.variables.put(var1, -0.5F);
      this.variables.put(var2, -0.5F);
      this.constantValue = -var5;
      return this;
   }

   void ensurePositiveConstant() {
      float var1 = this.constantValue;
      if (var1 < 0.0F) {
         this.constantValue = var1 * -1.0F;
         this.variables.invert();
      }

   }

   public SolverVariable getKey() {
      return this.variable;
   }

   public SolverVariable getPivotCandidate(LinearSystem var1, boolean[] var2) {
      return this.pickPivotInVariables(var2, (SolverVariable)null);
   }

   boolean hasKeyVariable() {
      SolverVariable var1 = this.variable;
      boolean var2;
      if (var1 == null || var1.mType != SolverVariable.Type.UNRESTRICTED && this.constantValue < 0.0F) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   boolean hasVariable(SolverVariable var1) {
      return this.variables.contains(var1);
   }

   public void initFromRow(LinearSystem.Row var1) {
      if (var1 instanceof ArrayRow) {
         ArrayRow var2 = (ArrayRow)var1;
         this.variable = null;
         this.variables.clear();

         for(int var3 = 0; var3 < var2.variables.getCurrentSize(); ++var3) {
            SolverVariable var5 = var2.variables.getVariable(var3);
            float var4 = var2.variables.getVariableValue(var3);
            this.variables.add(var5, var4, true);
         }
      }

   }

   public boolean isEmpty() {
      boolean var1;
      if (this.variable == null && this.constantValue == 0.0F && this.variables.getCurrentSize() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public SolverVariable pickPivot(SolverVariable var1) {
      return this.pickPivotInVariables((boolean[])null, var1);
   }

   void pivot(SolverVariable var1) {
      SolverVariable var2 = this.variable;
      if (var2 != null) {
         this.variables.put(var2, -1.0F);
         this.variable = null;
      }

      float var3 = this.variables.remove(var1, true) * -1.0F;
      this.variable = var1;
      if (var3 != 1.0F) {
         this.constantValue /= var3;
         this.variables.divideByAmount(var3);
      }
   }

   public void reset() {
      this.variable = null;
      this.variables.clear();
      this.constantValue = 0.0F;
      this.isSimpleDefinition = false;
   }

   int sizeInBytes() {
      byte var1;
      if (this.variable != null) {
         var1 = 4;
      } else {
         var1 = 0;
      }

      return var1 + 4 + 4 + this.variables.sizeInBytes();
   }

   String toReadableString() {
      String var1;
      StringBuilder var10;
      if (this.variable == null) {
         var1 = "0";
      } else {
         var10 = new StringBuilder();
         var10.append("");
         var10.append(this.variable);
         var1 = var10.toString();
      }

      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(" = ");
      var1 = var2.toString();
      float var3 = this.constantValue;
      int var4 = 0;
      boolean var5;
      if (var3 != 0.0F) {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append(this.constantValue);
         var1 = var2.toString();
         var5 = true;
      } else {
         var5 = false;
      }

      String var12;
      for(int var6 = this.variables.getCurrentSize(); var4 < var6; ++var4) {
         SolverVariable var11 = this.variables.getVariable(var4);
         if (var11 != null) {
            float var7 = this.variables.getVariableValue(var4);
            float var13;
            int var8 = (var13 = var7 - 0.0F) == 0.0F ? 0 : (var13 < 0.0F ? -1 : 1);
            if (var8 != 0) {
               String var9;
               label51: {
                  var9 = var11.toString();
                  if (!var5) {
                     var12 = var1;
                     var3 = var7;
                     if (var7 >= 0.0F) {
                        break label51;
                     }

                     var2 = new StringBuilder();
                     var2.append(var1);
                     var2.append("- ");
                     var12 = var2.toString();
                  } else {
                     if (var8 > 0) {
                        var2 = new StringBuilder();
                        var2.append(var1);
                        var2.append(" + ");
                        var12 = var2.toString();
                        var3 = var7;
                        break label51;
                     }

                     var2 = new StringBuilder();
                     var2.append(var1);
                     var2.append(" - ");
                     var12 = var2.toString();
                  }

                  var3 = var7 * -1.0F;
               }

               if (var3 == 1.0F) {
                  var10 = new StringBuilder();
                  var10.append(var12);
                  var10.append(var9);
                  var1 = var10.toString();
               } else {
                  var10 = new StringBuilder();
                  var10.append(var12);
                  var10.append(var3);
                  var10.append(" ");
                  var10.append(var9);
                  var1 = var10.toString();
               }

               var5 = true;
            }
         }
      }

      var12 = var1;
      if (!var5) {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append("0.0");
         var12 = var2.toString();
      }

      return var12;
   }

   public String toString() {
      return this.toReadableString();
   }

   public void updateFromFinalVariable(LinearSystem var1, SolverVariable var2, boolean var3) {
      if (var2.isFinalValue) {
         float var4 = this.variables.get(var2);
         this.constantValue += var2.computedValue * var4;
         this.variables.remove(var2, var3);
         if (var3) {
            var2.removeFromRow(this);
         }

      }
   }

   public void updateFromRow(ArrayRow var1, boolean var2) {
      float var3 = this.variables.use(var1, var2);
      this.constantValue += var1.constantValue * var3;
      if (var2) {
         var1.variable.removeFromRow(this);
      }

   }

   public void updateFromSystem(LinearSystem var1) {
      if (var1.mRows.length != 0) {
         boolean var2 = false;

         while(true) {
            while(!var2) {
               int var3 = this.variables.getCurrentSize();

               SolverVariable var5;
               for(int var4 = 0; var4 < var3; ++var4) {
                  var5 = this.variables.getVariable(var4);
                  if (var5.definitionId != -1 || var5.isFinalValue) {
                     this.variablesToUpdate.add(var5);
                  }
               }

               if (this.variablesToUpdate.size() > 0) {
                  Iterator var6 = this.variablesToUpdate.iterator();

                  while(var6.hasNext()) {
                     var5 = (SolverVariable)var6.next();
                     if (var5.isFinalValue) {
                        this.updateFromFinalVariable(var1, var5, true);
                     } else {
                        this.updateFromRow(var1.mRows[var5.definitionId], true);
                     }
                  }

                  this.variablesToUpdate.clear();
               } else {
                  var2 = true;
               }
            }

            return;
         }
      }
   }

   public interface ArrayRowVariables {
      void add(SolverVariable var1, float var2, boolean var3);

      void clear();

      boolean contains(SolverVariable var1);

      void display();

      void divideByAmount(float var1);

      float get(SolverVariable var1);

      int getCurrentSize();

      SolverVariable getVariable(int var1);

      float getVariableValue(int var1);

      int indexOf(SolverVariable var1);

      void invert();

      void put(SolverVariable var1, float var2);

      float remove(SolverVariable var1, boolean var2);

      int sizeInBytes();

      float use(ArrayRow var1, boolean var2);
   }
}
