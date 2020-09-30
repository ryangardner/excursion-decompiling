package androidx.constraintlayout.solver;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityGoalRow extends ArrayRow {
   private static final boolean DEBUG = false;
   static final int NOT_FOUND = -1;
   private static final float epsilon = 1.0E-4F;
   private int TABLE_SIZE = 128;
   PriorityGoalRow.GoalVariableAccessor accessor = new PriorityGoalRow.GoalVariableAccessor(this);
   private SolverVariable[] arrayGoals = new SolverVariable[128];
   Cache mCache;
   private int numGoals = 0;
   private SolverVariable[] sortArray = new SolverVariable[128];

   public PriorityGoalRow(Cache var1) {
      super(var1);
      this.mCache = var1;
   }

   private final void addToGoal(SolverVariable var1) {
      int var2 = this.numGoals;
      SolverVariable[] var3 = this.arrayGoals;
      if (var2 + 1 > var3.length) {
         var3 = (SolverVariable[])Arrays.copyOf(var3, var3.length * 2);
         this.arrayGoals = var3;
         this.sortArray = (SolverVariable[])Arrays.copyOf(var3, var3.length * 2);
      }

      var3 = this.arrayGoals;
      var2 = this.numGoals;
      var3[var2] = var1;
      ++var2;
      this.numGoals = var2;
      if (var2 > 1 && var3[var2 - 1].id > var1.id) {
         byte var4 = 0;
         var2 = 0;

         while(true) {
            int var5 = this.numGoals;
            if (var2 >= var5) {
               Arrays.sort(this.sortArray, 0, var5, new Comparator<SolverVariable>() {
                  public int compare(SolverVariable var1, SolverVariable var2) {
                     return var1.id - var2.id;
                  }
               });

               for(var2 = var4; var2 < this.numGoals; ++var2) {
                  this.arrayGoals[var2] = this.sortArray[var2];
               }
               break;
            }

            this.sortArray[var2] = this.arrayGoals[var2];
            ++var2;
         }
      }

      var1.inGoal = true;
      var1.addToRow(this);
   }

   private final void removeGoal(SolverVariable var1) {
      for(int var2 = 0; var2 < this.numGoals; ++var2) {
         if (this.arrayGoals[var2] == var1) {
            while(true) {
               int var3 = this.numGoals;
               if (var2 >= var3 - 1) {
                  this.numGoals = var3 - 1;
                  var1.inGoal = false;
                  return;
               }

               SolverVariable[] var4 = this.arrayGoals;
               var3 = var2 + 1;
               var4[var2] = var4[var3];
               var2 = var3;
            }
         }
      }

   }

   public void addError(SolverVariable var1) {
      this.accessor.init(var1);
      this.accessor.reset();
      var1.goalStrengthVector[var1.strength] = 1.0F;
      this.addToGoal(var1);
   }

   public void clear() {
      this.numGoals = 0;
      this.constantValue = 0.0F;
   }

   public SolverVariable getPivotCandidate(LinearSystem var1, boolean[] var2) {
      int var3 = 0;

      int var4;
      int var5;
      for(var4 = -1; var3 < this.numGoals; var4 = var5) {
         SolverVariable var6 = this.arrayGoals[var3];
         if (var2[var6.id]) {
            var5 = var4;
         } else {
            label33: {
               this.accessor.init(var6);
               if (var4 == -1) {
                  var5 = var4;
                  if (!this.accessor.isNegative()) {
                     break label33;
                  }
               } else {
                  var5 = var4;
                  if (!this.accessor.isSmallerThan(this.arrayGoals[var4])) {
                     break label33;
                  }
               }

               var5 = var3;
            }
         }

         ++var3;
      }

      if (var4 == -1) {
         return null;
      } else {
         return this.arrayGoals[var4];
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("");
      var1.append(" goal -> (");
      var1.append(this.constantValue);
      var1.append(") : ");
      String var4 = var1.toString();

      for(int var2 = 0; var2 < this.numGoals; ++var2) {
         SolverVariable var3 = this.arrayGoals[var2];
         this.accessor.init(var3);
         StringBuilder var5 = new StringBuilder();
         var5.append(var4);
         var5.append(this.accessor);
         var5.append(" ");
         var4 = var5.toString();
      }

      return var4;
   }

   public void updateFromRow(ArrayRow var1, boolean var2) {
      SolverVariable var3 = var1.variable;
      if (var3 != null) {
         ArrayRow.ArrayRowVariables var4 = var1.variables;
         int var5 = var4.getCurrentSize();

         for(int var6 = 0; var6 < var5; ++var6) {
            SolverVariable var7 = var4.getVariable(var6);
            float var8 = var4.getVariableValue(var6);
            this.accessor.init(var7);
            if (this.accessor.addToGoal(var3, var8)) {
               this.addToGoal(var7);
            }

            this.constantValue += var1.constantValue * var8;
         }

         this.removeGoal(var3);
      }
   }

   class GoalVariableAccessor implements Comparable {
      PriorityGoalRow row;
      SolverVariable variable;

      public GoalVariableAccessor(PriorityGoalRow var2) {
         this.row = var2;
      }

      public void add(SolverVariable var1) {
         for(int var2 = 0; var2 < 9; ++var2) {
            float[] var3 = this.variable.goalStrengthVector;
            var3[var2] += var1.goalStrengthVector[var2];
            if (Math.abs(this.variable.goalStrengthVector[var2]) < 1.0E-4F) {
               this.variable.goalStrengthVector[var2] = 0.0F;
            }
         }

      }

      public boolean addToGoal(SolverVariable var1, float var2) {
         boolean var3 = this.variable.inGoal;
         boolean var4 = true;
         int var5 = 0;
         if (var3) {
            for(var5 = 0; var5 < 9; ++var5) {
               float[] var6 = this.variable.goalStrengthVector;
               var6[var5] += var1.goalStrengthVector[var5] * var2;
               if (Math.abs(this.variable.goalStrengthVector[var5]) < 1.0E-4F) {
                  this.variable.goalStrengthVector[var5] = 0.0F;
               } else {
                  var4 = false;
               }
            }

            if (var4) {
               PriorityGoalRow.this.removeGoal(this.variable);
            }

            return false;
         } else {
            for(; var5 < 9; ++var5) {
               float var7 = var1.goalStrengthVector[var5];
               if (var7 != 0.0F) {
                  float var8 = var7 * var2;
                  var7 = var8;
                  if (Math.abs(var8) < 1.0E-4F) {
                     var7 = 0.0F;
                  }

                  this.variable.goalStrengthVector[var5] = var7;
               } else {
                  this.variable.goalStrengthVector[var5] = 0.0F;
               }
            }

            return true;
         }
      }

      public int compareTo(Object var1) {
         SolverVariable var2 = (SolverVariable)var1;
         return this.variable.id - var2.id;
      }

      public void init(SolverVariable var1) {
         this.variable = var1;
      }

      public final boolean isNegative() {
         for(int var1 = 8; var1 >= 0; --var1) {
            float var2 = this.variable.goalStrengthVector[var1];
            if (var2 > 0.0F) {
               return false;
            }

            if (var2 < 0.0F) {
               return true;
            }
         }

         return false;
      }

      public final boolean isNull() {
         for(int var1 = 0; var1 < 9; ++var1) {
            if (this.variable.goalStrengthVector[var1] != 0.0F) {
               return false;
            }
         }

         return true;
      }

      public final boolean isSmallerThan(SolverVariable var1) {
         for(int var2 = 8; var2 >= 0; --var2) {
            float var3 = var1.goalStrengthVector[var2];
            float var4 = this.variable.goalStrengthVector[var2];
            if (var4 != var3) {
               if (var4 < var3) {
                  return true;
               }
               break;
            }
         }

         return false;
      }

      public void reset() {
         Arrays.fill(this.variable.goalStrengthVector, 0.0F);
      }

      public String toString() {
         SolverVariable var1 = this.variable;
         String var2 = "[ ";
         String var3 = var2;
         if (var1 != null) {
            int var4 = 0;

            while(true) {
               var3 = var2;
               if (var4 >= 9) {
                  break;
               }

               StringBuilder var6 = new StringBuilder();
               var6.append(var2);
               var6.append(this.variable.goalStrengthVector[var4]);
               var6.append(" ");
               var2 = var6.toString();
               ++var4;
            }
         }

         StringBuilder var5 = new StringBuilder();
         var5.append(var3);
         var5.append("] ");
         var5.append(this.variable);
         return var5.toString();
      }
   }
}
