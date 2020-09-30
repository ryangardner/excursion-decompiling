package androidx.constraintlayout.solver;

import java.util.Arrays;
import java.util.HashSet;

public class SolverVariable {
   private static final boolean INTERNAL_DEBUG = false;
   static final int MAX_STRENGTH = 9;
   public static final int STRENGTH_BARRIER = 6;
   public static final int STRENGTH_CENTERING = 7;
   public static final int STRENGTH_EQUALITY = 5;
   public static final int STRENGTH_FIXED = 8;
   public static final int STRENGTH_HIGH = 3;
   public static final int STRENGTH_HIGHEST = 4;
   public static final int STRENGTH_LOW = 1;
   public static final int STRENGTH_MEDIUM = 2;
   public static final int STRENGTH_NONE = 0;
   private static final boolean VAR_USE_HASH = false;
   private static int uniqueConstantId;
   private static int uniqueErrorId;
   private static int uniqueId;
   private static int uniqueSlackId;
   private static int uniqueUnrestrictedId;
   public float computedValue;
   int definitionId = -1;
   float[] goalStrengthVector = new float[9];
   public int id = -1;
   public boolean inGoal;
   HashSet<ArrayRow> inRows = null;
   public boolean isFinalValue = false;
   ArrayRow[] mClientEquations = new ArrayRow[16];
   int mClientEquationsCount = 0;
   private String mName;
   SolverVariable.Type mType;
   public int strength = 0;
   float[] strengthVector = new float[9];
   public int usageInRowCount = 0;

   public SolverVariable(SolverVariable.Type var1, String var2) {
      this.mType = var1;
   }

   public SolverVariable(String var1, SolverVariable.Type var2) {
      this.mName = var1;
      this.mType = var2;
   }

   private static String getUniqueName(SolverVariable.Type var0, String var1) {
      StringBuilder var3;
      if (var1 != null) {
         var3 = new StringBuilder();
         var3.append(var1);
         var3.append(uniqueErrorId);
         return var3.toString();
      } else {
         int var2 = null.$SwitchMap$androidx$constraintlayout$solver$SolverVariable$Type[var0.ordinal()];
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  if (var2 != 4) {
                     if (var2 == 5) {
                        var3 = new StringBuilder();
                        var3.append("V");
                        var2 = uniqueId + 1;
                        uniqueId = var2;
                        var3.append(var2);
                        return var3.toString();
                     } else {
                        throw new AssertionError(var0.name());
                     }
                  } else {
                     var3 = new StringBuilder();
                     var3.append("e");
                     var2 = uniqueErrorId + 1;
                     uniqueErrorId = var2;
                     var3.append(var2);
                     return var3.toString();
                  }
               } else {
                  var3 = new StringBuilder();
                  var3.append("S");
                  var2 = uniqueSlackId + 1;
                  uniqueSlackId = var2;
                  var3.append(var2);
                  return var3.toString();
               }
            } else {
               var3 = new StringBuilder();
               var3.append("C");
               var2 = uniqueConstantId + 1;
               uniqueConstantId = var2;
               var3.append(var2);
               return var3.toString();
            }
         } else {
            var3 = new StringBuilder();
            var3.append("U");
            var2 = uniqueUnrestrictedId + 1;
            uniqueUnrestrictedId = var2;
            var3.append(var2);
            return var3.toString();
         }
      }
   }

   static void increaseErrorId() {
      ++uniqueErrorId;
   }

   public final void addToRow(ArrayRow var1) {
      int var2 = 0;

      while(true) {
         int var3 = this.mClientEquationsCount;
         if (var2 >= var3) {
            ArrayRow[] var4 = this.mClientEquations;
            if (var3 >= var4.length) {
               this.mClientEquations = (ArrayRow[])Arrays.copyOf(var4, var4.length * 2);
            }

            var4 = this.mClientEquations;
            var2 = this.mClientEquationsCount;
            var4[var2] = var1;
            this.mClientEquationsCount = var2 + 1;
            return;
         }

         if (this.mClientEquations[var2] == var1) {
            return;
         }

         ++var2;
      }
   }

   void clearStrengths() {
      for(int var1 = 0; var1 < 9; ++var1) {
         this.strengthVector[var1] = 0.0F;
      }

   }

   public String getName() {
      return this.mName;
   }

   public final void removeFromRow(ArrayRow var1) {
      int var2 = this.mClientEquationsCount;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (this.mClientEquations[var3] == var1) {
            while(var3 < var2 - 1) {
               ArrayRow[] var5 = this.mClientEquations;
               int var4 = var3 + 1;
               var5[var3] = var5[var4];
               var3 = var4;
            }

            --this.mClientEquationsCount;
            return;
         }
      }

   }

   public void reset() {
      this.mName = null;
      this.mType = SolverVariable.Type.UNKNOWN;
      this.strength = 0;
      this.id = -1;
      this.definitionId = -1;
      this.computedValue = 0.0F;
      this.isFinalValue = false;
      int var1 = this.mClientEquationsCount;

      for(int var2 = 0; var2 < var1; ++var2) {
         this.mClientEquations[var2] = null;
      }

      this.mClientEquationsCount = 0;
      this.usageInRowCount = 0;
      this.inGoal = false;
      Arrays.fill(this.goalStrengthVector, 0.0F);
   }

   public void setFinalValue(LinearSystem var1, float var2) {
      this.computedValue = var2;
      this.isFinalValue = true;
      int var3 = this.mClientEquationsCount;

      for(int var4 = 0; var4 < var3; ++var4) {
         this.mClientEquations[var4].updateFromFinalVariable(var1, this, false);
      }

      this.mClientEquationsCount = 0;
   }

   public void setName(String var1) {
      this.mName = var1;
   }

   public void setType(SolverVariable.Type var1, String var2) {
      this.mType = var1;
   }

   String strengthsToString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this);
      var1.append("[");
      String var2 = var1.toString();
      int var3 = 0;
      boolean var4 = false;

      boolean var5;
      String var6;
      StringBuilder var8;
      for(var5 = true; var3 < this.strengthVector.length; ++var3) {
         var1 = new StringBuilder();
         var1.append(var2);
         var1.append(this.strengthVector[var3]);
         var6 = var1.toString();
         float[] var7 = this.strengthVector;
         if (var7[var3] > 0.0F) {
            var4 = false;
         } else if (var7[var3] < 0.0F) {
            var4 = true;
         }

         if (this.strengthVector[var3] != 0.0F) {
            var5 = false;
         }

         if (var3 < this.strengthVector.length - 1) {
            var8 = new StringBuilder();
            var8.append(var6);
            var8.append(", ");
            var2 = var8.toString();
         } else {
            var8 = new StringBuilder();
            var8.append(var6);
            var8.append("] ");
            var2 = var8.toString();
         }
      }

      var6 = var2;
      if (var4) {
         var1 = new StringBuilder();
         var1.append(var2);
         var1.append(" (-)");
         var6 = var1.toString();
      }

      var2 = var6;
      if (var5) {
         var8 = new StringBuilder();
         var8.append(var6);
         var8.append(" (*)");
         var2 = var8.toString();
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1;
      String var2;
      if (this.mName != null) {
         var1 = new StringBuilder();
         var1.append("");
         var1.append(this.mName);
         var2 = var1.toString();
      } else {
         var1 = new StringBuilder();
         var1.append("");
         var1.append(this.id);
         var2 = var1.toString();
      }

      return var2;
   }

   public final void updateReferencesWithNewDefinition(ArrayRow var1) {
      int var2 = this.mClientEquationsCount;

      for(int var3 = 0; var3 < var2; ++var3) {
         this.mClientEquations[var3].updateFromRow(var1, false);
      }

      this.mClientEquationsCount = 0;
   }

   public static enum Type {
      CONSTANT,
      ERROR,
      SLACK,
      UNKNOWN,
      UNRESTRICTED;

      static {
         SolverVariable.Type var0 = new SolverVariable.Type("UNKNOWN", 4);
         UNKNOWN = var0;
      }
   }
}
