package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {
   public static long ARRAY_ROW_CREATION;
   public static final boolean DEBUG = false;
   private static final boolean DEBUG_CONSTRAINTS = false;
   public static final boolean FULL_DEBUG = false;
   public static final boolean MEASURE = false;
   public static long OPTIMIZED_ARRAY_ROW_CREATION;
   public static boolean OPTIMIZED_ENGINE;
   private static int POOL_SIZE;
   static final boolean SIMPLIFY_SYNONYMS = false;
   private static final boolean USE_SYNONYMS = true;
   public static Metrics sMetrics;
   private int TABLE_SIZE = 32;
   public boolean graphOptimizer = false;
   private boolean[] mAlreadyTestedCandidates = new boolean[32];
   final Cache mCache;
   private LinearSystem.Row mGoal;
   private int mMaxColumns = 32;
   private int mMaxRows = 32;
   int mNumColumns = 1;
   int mNumRows = 0;
   private SolverVariable[] mPoolVariables;
   private int mPoolVariablesCount;
   ArrayRow[] mRows = null;
   private LinearSystem.Row mTempGoal;
   private HashMap<String, SolverVariable> mVariables = null;
   int mVariablesID = 0;
   public boolean newgraphOptimizer = false;

   public LinearSystem() {
      this.mPoolVariables = new SolverVariable[POOL_SIZE];
      this.mPoolVariablesCount = 0;
      this.mRows = new ArrayRow[32];
      this.releaseRows();
      this.mCache = new Cache();
      this.mGoal = new PriorityGoalRow(this.mCache);
      if (OPTIMIZED_ENGINE) {
         this.mTempGoal = new LinearSystem.ValuesRow(this.mCache);
      } else {
         this.mTempGoal = new ArrayRow(this.mCache);
      }

   }

   private SolverVariable acquireSolverVariable(SolverVariable.Type var1, String var2) {
      SolverVariable var3 = (SolverVariable)this.mCache.solverVariablePool.acquire();
      SolverVariable var6;
      if (var3 == null) {
         var3 = new SolverVariable(var1, var2);
         var3.setType(var1, var2);
         var6 = var3;
      } else {
         var3.reset();
         var3.setType(var1, var2);
         var6 = var3;
      }

      int var4 = this.mPoolVariablesCount;
      int var5 = POOL_SIZE;
      if (var4 >= var5) {
         var5 *= 2;
         POOL_SIZE = var5;
         this.mPoolVariables = (SolverVariable[])Arrays.copyOf(this.mPoolVariables, var5);
      }

      SolverVariable[] var7 = this.mPoolVariables;
      var5 = this.mPoolVariablesCount++;
      var7[var5] = var6;
      return var6;
   }

   private void addError(ArrayRow var1) {
      var1.addError(this, 0);
   }

   private final void addRow(ArrayRow var1) {
      if (OPTIMIZED_ENGINE) {
         if (this.mRows[this.mNumRows] != null) {
            this.mCache.optimizedArrayRowPool.release(this.mRows[this.mNumRows]);
         }
      } else if (this.mRows[this.mNumRows] != null) {
         this.mCache.arrayRowPool.release(this.mRows[this.mNumRows]);
      }

      this.mRows[this.mNumRows] = var1;
      var1.variable.definitionId = this.mNumRows++;
      var1.variable.updateReferencesWithNewDefinition(var1);
   }

   private void addSingleError(ArrayRow var1, int var2) {
      this.addSingleError(var1, var2, 0);
   }

   private void computeValues() {
      for(int var1 = 0; var1 < this.mNumRows; ++var1) {
         ArrayRow var2 = this.mRows[var1];
         var2.variable.computedValue = var2.constantValue;
      }

   }

   public static ArrayRow createRowDimensionPercent(LinearSystem var0, SolverVariable var1, SolverVariable var2, float var3) {
      return var0.createRow().createRowDimensionPercent(var1, var2, var3);
   }

   private SolverVariable createVariable(String var1, SolverVariable.Type var2) {
      Metrics var3 = sMetrics;
      if (var3 != null) {
         ++var3.variables;
      }

      if (this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var5 = this.acquireSolverVariable(var2, (String)null);
      var5.setName(var1);
      int var4 = this.mVariablesID + 1;
      this.mVariablesID = var4;
      ++this.mNumColumns;
      var5.id = var4;
      if (this.mVariables == null) {
         this.mVariables = new HashMap();
      }

      this.mVariables.put(var1, var5);
      this.mCache.mIndexedVariables[this.mVariablesID] = var5;
      return var5;
   }

   private void displayRows() {
      this.displaySolverVariables();
      String var1 = "";

      StringBuilder var3;
      for(int var2 = 0; var2 < this.mNumRows; ++var2) {
         var3 = new StringBuilder();
         var3.append(var1);
         var3.append(this.mRows[var2]);
         var1 = var3.toString();
         var3 = new StringBuilder();
         var3.append(var1);
         var3.append("\n");
         var1 = var3.toString();
      }

      var3 = new StringBuilder();
      var3.append(var1);
      var3.append(this.mGoal);
      var3.append("\n");
      var1 = var3.toString();
      System.out.println(var1);
   }

   private void displaySolverVariables() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Display Rows (");
      var1.append(this.mNumRows);
      var1.append("x");
      var1.append(this.mNumColumns);
      var1.append(")\n");
      String var2 = var1.toString();
      System.out.println(var2);
   }

   private int enforceBFS(LinearSystem.Row var1) throws Exception {
      int var2 = 0;

      boolean var19;
      while(true) {
         if (var2 >= this.mNumRows) {
            var19 = false;
            break;
         }

         if (this.mRows[var2].variable.mType != SolverVariable.Type.UNRESTRICTED && this.mRows[var2].constantValue < 0.0F) {
            var19 = true;
            break;
         }

         ++var2;
      }

      if (var19) {
         boolean var3 = false;

         int var4;
         for(var2 = 0; !var3; var2 = var4) {
            Metrics var17 = sMetrics;
            if (var17 != null) {
               ++var17.bfs;
            }

            var4 = var2 + 1;
            float var5 = Float.MAX_VALUE;
            int var6 = 0;
            int var7 = -1;
            var2 = -1;

            int var12;
            for(int var8 = 0; var6 < this.mNumRows; var8 = var12) {
               ArrayRow var18 = this.mRows[var6];
               float var9;
               int var10;
               int var11;
               if (var18.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                  var9 = var5;
                  var10 = var7;
                  var11 = var2;
                  var12 = var8;
               } else if (var18.isSimpleDefinition) {
                  var9 = var5;
                  var10 = var7;
                  var11 = var2;
                  var12 = var8;
               } else {
                  var9 = var5;
                  var10 = var7;
                  var11 = var2;
                  var12 = var8;
                  if (var18.constantValue < 0.0F) {
                     int var13 = 1;

                     while(true) {
                        var9 = var5;
                        var10 = var7;
                        var11 = var2;
                        var12 = var8;
                        if (var13 >= this.mNumColumns) {
                           break;
                        }

                        SolverVariable var14 = this.mCache.mIndexedVariables[var13];
                        float var15 = var18.variables.get(var14);
                        int var16;
                        if (var15 <= 0.0F) {
                           var9 = var5;
                           var12 = var7;
                           var11 = var2;
                           var16 = var8;
                        } else {
                           byte var20 = 0;
                           var10 = var2;
                           var2 = var20;

                           while(true) {
                              var9 = var5;
                              var12 = var7;
                              var11 = var10;
                              var16 = var8;
                              if (var2 >= 9) {
                                 break;
                              }

                              label118: {
                                 var9 = var14.strengthVector[var2] / var15;
                                 if (var9 >= var5 || var2 != var8) {
                                    var11 = var8;
                                    if (var2 <= var8) {
                                       break label118;
                                    }
                                 }

                                 var10 = var13;
                                 var11 = var2;
                                 var5 = var9;
                                 var7 = var6;
                              }

                              ++var2;
                              var8 = var11;
                           }
                        }

                        ++var13;
                        var5 = var9;
                        var7 = var12;
                        var2 = var11;
                        var8 = var16;
                     }
                  }
               }

               ++var6;
               var5 = var9;
               var7 = var10;
               var2 = var11;
            }

            if (var7 != -1) {
               ArrayRow var21 = this.mRows[var7];
               var21.variable.definitionId = -1;
               var17 = sMetrics;
               if (var17 != null) {
                  ++var17.pivots;
               }

               var21.pivot(this.mCache.mIndexedVariables[var2]);
               var21.variable.definitionId = var7;
               var21.variable.updateReferencesWithNewDefinition(var21);
            } else {
               var3 = true;
            }

            if (var4 > this.mNumColumns / 2) {
               var3 = true;
            }
         }
      } else {
         var2 = 0;
      }

      return var2;
   }

   private String getDisplaySize(int var1) {
      int var2 = var1 * 4;
      int var3 = var2 / 1024;
      var1 = var3 / 1024;
      StringBuilder var4;
      if (var1 > 0) {
         var4 = new StringBuilder();
         var4.append("");
         var4.append(var1);
         var4.append(" Mb");
         return var4.toString();
      } else if (var3 > 0) {
         var4 = new StringBuilder();
         var4.append("");
         var4.append(var3);
         var4.append(" Kb");
         return var4.toString();
      } else {
         var4 = new StringBuilder();
         var4.append("");
         var4.append(var2);
         var4.append(" bytes");
         return var4.toString();
      }
   }

   private String getDisplayStrength(int var1) {
      if (var1 == 1) {
         return "LOW";
      } else if (var1 == 2) {
         return "MEDIUM";
      } else if (var1 == 3) {
         return "HIGH";
      } else if (var1 == 4) {
         return "HIGHEST";
      } else if (var1 == 5) {
         return "EQUALITY";
      } else if (var1 == 8) {
         return "FIXED";
      } else {
         return var1 == 6 ? "BARRIER" : "NONE";
      }
   }

   public static Metrics getMetrics() {
      return sMetrics;
   }

   private void increaseTableSize() {
      int var1 = this.TABLE_SIZE * 2;
      this.TABLE_SIZE = var1;
      this.mRows = (ArrayRow[])Arrays.copyOf(this.mRows, var1);
      Cache var2 = this.mCache;
      var2.mIndexedVariables = (SolverVariable[])Arrays.copyOf(var2.mIndexedVariables, this.TABLE_SIZE);
      var1 = this.TABLE_SIZE;
      this.mAlreadyTestedCandidates = new boolean[var1];
      this.mMaxColumns = var1;
      this.mMaxRows = var1;
      Metrics var3 = sMetrics;
      if (var3 != null) {
         ++var3.tableSizeIncrease;
         var3 = sMetrics;
         var3.maxTableSize = Math.max(var3.maxTableSize, (long)this.TABLE_SIZE);
         var3 = sMetrics;
         var3.lastTableSize = var3.maxTableSize;
      }

   }

   private final int optimize(LinearSystem.Row var1, boolean var2) {
      Metrics var3 = sMetrics;
      if (var3 != null) {
         ++var3.optimize;
      }

      int var4;
      for(var4 = 0; var4 < this.mNumColumns; ++var4) {
         this.mAlreadyTestedCandidates[var4] = false;
      }

      boolean var5 = false;
      var4 = 0;

      while(true) {
         while(!var5) {
            var3 = sMetrics;
            if (var3 != null) {
               ++var3.iterations;
            }

            int var6 = var4 + 1;
            if (var6 >= this.mNumColumns * 2) {
               return var6;
            }

            if (var1.getKey() != null) {
               this.mAlreadyTestedCandidates[var1.getKey().id] = true;
            }

            SolverVariable var14 = var1.getPivotCandidate(this, this.mAlreadyTestedCandidates);
            if (var14 != null) {
               if (this.mAlreadyTestedCandidates[var14.id]) {
                  return var6;
               }

               this.mAlreadyTestedCandidates[var14.id] = true;
            }

            if (var14 != null) {
               float var7 = Float.MAX_VALUE;
               var4 = 0;

               int var8;
               ArrayRow var9;
               int var11;
               for(var8 = -1; var4 < this.mNumRows; var8 = var11) {
                  var9 = this.mRows[var4];
                  float var10;
                  if (var9.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                     var10 = var7;
                     var11 = var8;
                  } else if (var9.isSimpleDefinition) {
                     var10 = var7;
                     var11 = var8;
                  } else {
                     var10 = var7;
                     var11 = var8;
                     if (var9.hasVariable(var14)) {
                        float var12 = var9.variables.get(var14);
                        var10 = var7;
                        var11 = var8;
                        if (var12 < 0.0F) {
                           var12 = -var9.constantValue / var12;
                           var10 = var7;
                           var11 = var8;
                           if (var12 < var7) {
                              var11 = var4;
                              var10 = var12;
                           }
                        }
                     }
                  }

                  ++var4;
                  var7 = var10;
               }

               var4 = var6;
               if (var8 > -1) {
                  var9 = this.mRows[var8];
                  var9.variable.definitionId = -1;
                  Metrics var13 = sMetrics;
                  if (var13 != null) {
                     ++var13.pivots;
                  }

                  var9.pivot(var14);
                  var9.variable.definitionId = var8;
                  var9.variable.updateReferencesWithNewDefinition(var9);
                  var4 = var6;
               }
            } else {
               var5 = true;
               var4 = var6;
            }
         }

         return var4;
      }
   }

   private void releaseRows() {
      boolean var1 = OPTIMIZED_ENGINE;
      int var2 = 0;
      byte var3 = 0;
      ArrayRow[] var4;
      ArrayRow var5;
      if (var1) {
         var2 = var3;

         while(true) {
            var4 = this.mRows;
            if (var2 >= var4.length) {
               break;
            }

            var5 = var4[var2];
            if (var5 != null) {
               this.mCache.optimizedArrayRowPool.release(var5);
            }

            this.mRows[var2] = null;
            ++var2;
         }
      } else {
         while(true) {
            var4 = this.mRows;
            if (var2 >= var4.length) {
               break;
            }

            var5 = var4[var2];
            if (var5 != null) {
               this.mCache.arrayRowPool.release(var5);
            }

            this.mRows[var2] = null;
            ++var2;
         }
      }

   }

   public void addCenterPoint(ConstraintWidget var1, ConstraintWidget var2, float var3, int var4) {
      SolverVariable var5 = this.createObjectVariable(var1.getAnchor(ConstraintAnchor.Type.LEFT));
      SolverVariable var6 = this.createObjectVariable(var1.getAnchor(ConstraintAnchor.Type.TOP));
      SolverVariable var7 = this.createObjectVariable(var1.getAnchor(ConstraintAnchor.Type.RIGHT));
      SolverVariable var8 = this.createObjectVariable(var1.getAnchor(ConstraintAnchor.Type.BOTTOM));
      SolverVariable var18 = this.createObjectVariable(var2.getAnchor(ConstraintAnchor.Type.LEFT));
      SolverVariable var9 = this.createObjectVariable(var2.getAnchor(ConstraintAnchor.Type.TOP));
      SolverVariable var10 = this.createObjectVariable(var2.getAnchor(ConstraintAnchor.Type.RIGHT));
      SolverVariable var19 = this.createObjectVariable(var2.getAnchor(ConstraintAnchor.Type.BOTTOM));
      ArrayRow var11 = this.createRow();
      double var12 = (double)var3;
      double var14 = Math.sin(var12);
      double var16 = (double)var4;
      var11.createRowWithAngle(var6, var8, var9, var19, (float)(var14 * var16));
      this.addConstraint(var11);
      ArrayRow var20 = this.createRow();
      var20.createRowWithAngle(var5, var7, var18, var10, (float)(Math.cos(var12) * var16));
      this.addConstraint(var20);
   }

   public void addCentering(SolverVariable var1, SolverVariable var2, int var3, float var4, SolverVariable var5, SolverVariable var6, int var7, int var8) {
      ArrayRow var9 = this.createRow();
      var9.createRowCentering(var1, var2, var3, var4, var5, var6, var7);
      if (var8 != 8) {
         var9.addError(this, var8);
      }

      this.addConstraint(var9);
   }

   public void addConstraint(ArrayRow var1) {
      if (var1 != null) {
         Metrics var2 = sMetrics;
         if (var2 != null) {
            ++var2.constraints;
            if (var1.isSimpleDefinition) {
               var2 = sMetrics;
               ++var2.simpleconstraints;
            }
         }

         int var3 = this.mNumRows;
         boolean var4 = true;
         if (var3 + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
         }

         boolean var7 = false;
         if (!var1.isSimpleDefinition) {
            var1.updateFromSystem(this);
            if (var1.isEmpty()) {
               return;
            }

            var1.ensurePositiveConstant();
            if (var1.chooseSubject(this)) {
               SolverVariable var6 = this.createExtraVariable();
               var1.variable = var6;
               this.addRow(var1);
               this.mTempGoal.initFromRow(var1);
               this.optimize(this.mTempGoal, true);
               var7 = var4;
               if (var6.definitionId == -1) {
                  if (var1.variable == var6) {
                     SolverVariable var5 = var1.pickPivot(var6);
                     if (var5 != null) {
                        var2 = sMetrics;
                        if (var2 != null) {
                           ++var2.pivots;
                        }

                        var1.pivot(var5);
                     }
                  }

                  if (!var1.isSimpleDefinition) {
                     var1.variable.updateReferencesWithNewDefinition(var1);
                  }

                  --this.mNumRows;
                  var7 = var4;
               }
            } else {
               var7 = false;
            }

            if (!var1.hasKeyVariable()) {
               return;
            }
         }

         if (!var7) {
            this.addRow(var1);
         }

      }
   }

   public ArrayRow addEquality(SolverVariable var1, SolverVariable var2, int var3, int var4) {
      if (var4 == 8 && var2.isFinalValue && var1.definitionId == -1) {
         var1.setFinalValue(this, var2.computedValue + (float)var3);
         return null;
      } else {
         ArrayRow var5 = this.createRow();
         var5.createRowEquals(var1, var2, var3);
         if (var4 != 8) {
            var5.addError(this, var4);
         }

         this.addConstraint(var5);
         return var5;
      }
   }

   public void addEquality(SolverVariable var1, int var2) {
      if (var1.definitionId == -1) {
         var1.setFinalValue(this, (float)var2);
      } else {
         int var3 = var1.definitionId;
         ArrayRow var4;
         if (var1.definitionId != -1) {
            var4 = this.mRows[var3];
            if (var4.isSimpleDefinition) {
               var4.constantValue = (float)var2;
            } else if (var4.variables.getCurrentSize() == 0) {
               var4.isSimpleDefinition = true;
               var4.constantValue = (float)var2;
            } else {
               var4 = this.createRow();
               var4.createRowEquals(var1, var2);
               this.addConstraint(var4);
            }
         } else {
            var4 = this.createRow();
            var4.createRowDefinition(var1, var2);
            this.addConstraint(var4);
         }

      }
   }

   public void addGreaterBarrier(SolverVariable var1, SolverVariable var2, int var3, boolean var4) {
      ArrayRow var5 = this.createRow();
      SolverVariable var6 = this.createSlackVariable();
      var6.strength = 0;
      var5.createRowGreaterThan(var1, var2, var6, var3);
      this.addConstraint(var5);
   }

   public void addGreaterThan(SolverVariable var1, SolverVariable var2, int var3, int var4) {
      ArrayRow var5 = this.createRow();
      SolverVariable var6 = this.createSlackVariable();
      var6.strength = 0;
      var5.createRowGreaterThan(var1, var2, var6, var3);
      if (var4 != 8) {
         this.addSingleError(var5, (int)(var5.variables.get(var6) * -1.0F), var4);
      }

      this.addConstraint(var5);
   }

   public void addLowerBarrier(SolverVariable var1, SolverVariable var2, int var3, boolean var4) {
      ArrayRow var5 = this.createRow();
      SolverVariable var6 = this.createSlackVariable();
      var6.strength = 0;
      var5.createRowLowerThan(var1, var2, var6, var3);
      this.addConstraint(var5);
   }

   public void addLowerThan(SolverVariable var1, SolverVariable var2, int var3, int var4) {
      ArrayRow var5 = this.createRow();
      SolverVariable var6 = this.createSlackVariable();
      var6.strength = 0;
      var5.createRowLowerThan(var1, var2, var6, var3);
      if (var4 != 8) {
         this.addSingleError(var5, (int)(var5.variables.get(var6) * -1.0F), var4);
      }

      this.addConstraint(var5);
   }

   public void addRatio(SolverVariable var1, SolverVariable var2, SolverVariable var3, SolverVariable var4, float var5, int var6) {
      ArrayRow var7 = this.createRow();
      var7.createRowDimensionRatio(var1, var2, var3, var4, var5);
      if (var6 != 8) {
         var7.addError(this, var6);
      }

      this.addConstraint(var7);
   }

   void addSingleError(ArrayRow var1, int var2, int var3) {
      var1.addSingleError(this.createErrorVariable(var3, (String)null), var2);
   }

   final void cleanupRows() {
      int var3;
      for(int var1 = 0; var1 < this.mNumRows; var1 = var3 + 1) {
         ArrayRow var2 = this.mRows[var1];
         if (var2.variables.getCurrentSize() == 0) {
            var2.isSimpleDefinition = true;
         }

         var3 = var1;
         if (var2.isSimpleDefinition) {
            var2.variable.computedValue = var2.constantValue;
            var2.variable.removeFromRow(var2);
            var3 = var1;

            while(true) {
               int var4 = this.mNumRows;
               if (var3 >= var4 - 1) {
                  this.mRows[var4 - 1] = null;
                  this.mNumRows = var4 - 1;
                  var3 = var1 - 1;
                  break;
               }

               ArrayRow[] var5 = this.mRows;
               var4 = var3 + 1;
               var5[var3] = var5[var4];
               var3 = var4;
            }
         }
      }

   }

   public SolverVariable createErrorVariable(int var1, String var2) {
      Metrics var3 = sMetrics;
      if (var3 != null) {
         ++var3.errors;
      }

      if (this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var5 = this.acquireSolverVariable(SolverVariable.Type.ERROR, var2);
      int var4 = this.mVariablesID + 1;
      this.mVariablesID = var4;
      ++this.mNumColumns;
      var5.id = var4;
      var5.strength = var1;
      this.mCache.mIndexedVariables[this.mVariablesID] = var5;
      this.mGoal.addError(var5);
      return var5;
   }

   public SolverVariable createExtraVariable() {
      Metrics var1 = sMetrics;
      if (var1 != null) {
         ++var1.extravariables;
      }

      if (this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var3 = this.acquireSolverVariable(SolverVariable.Type.SLACK, (String)null);
      int var2 = this.mVariablesID + 1;
      this.mVariablesID = var2;
      ++this.mNumColumns;
      var3.id = var2;
      this.mCache.mIndexedVariables[this.mVariablesID] = var3;
      return var3;
   }

   public SolverVariable createObjectVariable(Object var1) {
      SolverVariable var2 = null;
      if (var1 == null) {
         return null;
      } else {
         if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
         }

         if (var1 instanceof ConstraintAnchor) {
            ConstraintAnchor var3 = (ConstraintAnchor)var1;
            var2 = var3.getSolverVariable();
            SolverVariable var5 = var2;
            if (var2 == null) {
               var3.resetSolverVariable(this.mCache);
               var5 = var3.getSolverVariable();
            }

            if (var5.id != -1 && var5.id <= this.mVariablesID) {
               var2 = var5;
               if (this.mCache.mIndexedVariables[var5.id] != null) {
                  return var2;
               }
            }

            if (var5.id != -1) {
               var5.reset();
            }

            int var4 = this.mVariablesID + 1;
            this.mVariablesID = var4;
            ++this.mNumColumns;
            var5.id = var4;
            var5.mType = SolverVariable.Type.UNRESTRICTED;
            this.mCache.mIndexedVariables[this.mVariablesID] = var5;
            var2 = var5;
         }

         return var2;
      }
   }

   public ArrayRow createRow() {
      Object var1;
      if (OPTIMIZED_ENGINE) {
         var1 = (ArrayRow)this.mCache.optimizedArrayRowPool.acquire();
         if (var1 == null) {
            var1 = new LinearSystem.ValuesRow(this.mCache);
            ++OPTIMIZED_ARRAY_ROW_CREATION;
         } else {
            ((ArrayRow)var1).reset();
         }
      } else {
         var1 = (ArrayRow)this.mCache.arrayRowPool.acquire();
         if (var1 == null) {
            var1 = new ArrayRow(this.mCache);
            ++ARRAY_ROW_CREATION;
         } else {
            ((ArrayRow)var1).reset();
         }
      }

      SolverVariable.increaseErrorId();
      return (ArrayRow)var1;
   }

   public SolverVariable createSlackVariable() {
      Metrics var1 = sMetrics;
      if (var1 != null) {
         ++var1.slackvariables;
      }

      if (this.mNumColumns + 1 >= this.mMaxColumns) {
         this.increaseTableSize();
      }

      SolverVariable var3 = this.acquireSolverVariable(SolverVariable.Type.SLACK, (String)null);
      int var2 = this.mVariablesID + 1;
      this.mVariablesID = var2;
      ++this.mNumColumns;
      var3.id = var2;
      this.mCache.mIndexedVariables[this.mVariablesID] = var3;
      return var3;
   }

   public void displayReadableRows() {
      this.displaySolverVariables();
      byte var1 = 0;
      String var2 = "";

      int var3;
      String var5;
      StringBuilder var6;
      for(var3 = 0; var3 < this.mVariablesID; var2 = var5) {
         SolverVariable var4 = this.mCache.mIndexedVariables[var3];
         var5 = var2;
         if (var4 != null) {
            var5 = var2;
            if (var4.isFinalValue) {
               var6 = new StringBuilder();
               var6.append(var2);
               var6.append(" $[");
               var6.append(var3);
               var6.append("] => ");
               var6.append(var4);
               var6.append(" = ");
               var6.append(var4.computedValue);
               var6.append("\n");
               var5 = var6.toString();
            }
         }

         ++var3;
      }

      var6 = new StringBuilder();
      var6.append(var2);
      var6.append("\n\n #  ");
      var2 = var6.toString();

      for(var3 = var1; var3 < this.mNumRows; ++var3) {
         var6 = new StringBuilder();
         var6.append(var2);
         var6.append(this.mRows[var3].toReadableString());
         var2 = var6.toString();
         var6 = new StringBuilder();
         var6.append(var2);
         var6.append("\n #  ");
         var2 = var6.toString();
      }

      var5 = var2;
      if (this.mGoal != null) {
         var6 = new StringBuilder();
         var6.append(var2);
         var6.append("Goal: ");
         var6.append(this.mGoal);
         var6.append("\n");
         var5 = var6.toString();
      }

      System.out.println(var5);
   }

   void displaySystemInformations() {
      int var1 = 0;

      int var2;
      ArrayRow[] var3;
      int var4;
      for(var2 = 0; var1 < this.TABLE_SIZE; var2 = var4) {
         var3 = this.mRows;
         var4 = var2;
         if (var3[var1] != null) {
            var4 = var2 + var3[var1].sizeInBytes();
         }

         ++var1;
      }

      var4 = 0;

      int var5;
      for(var1 = 0; var4 < this.mNumRows; var1 = var5) {
         var3 = this.mRows;
         var5 = var1;
         if (var3[var4] != null) {
            var5 = var1 + var3[var4].sizeInBytes();
         }

         ++var4;
      }

      PrintStream var6 = System.out;
      StringBuilder var7 = new StringBuilder();
      var7.append("Linear System -> Table size: ");
      var7.append(this.TABLE_SIZE);
      var7.append(" (");
      var4 = this.TABLE_SIZE;
      var7.append(this.getDisplaySize(var4 * var4));
      var7.append(") -- row sizes: ");
      var7.append(this.getDisplaySize(var2));
      var7.append(", actual size: ");
      var7.append(this.getDisplaySize(var1));
      var7.append(" rows: ");
      var7.append(this.mNumRows);
      var7.append("/");
      var7.append(this.mMaxRows);
      var7.append(" cols: ");
      var7.append(this.mNumColumns);
      var7.append("/");
      var7.append(this.mMaxColumns);
      var7.append(" ");
      var7.append(0);
      var7.append(" occupied cells, ");
      var7.append(this.getDisplaySize(0));
      var6.println(var7.toString());
   }

   public void displayVariablesReadableRows() {
      this.displaySolverVariables();
      String var1 = "";

      String var3;
      StringBuilder var5;
      for(int var2 = 0; var2 < this.mNumRows; var1 = var3) {
         var3 = var1;
         if (this.mRows[var2].variable.mType == SolverVariable.Type.UNRESTRICTED) {
            var5 = new StringBuilder();
            var5.append(var1);
            var5.append(this.mRows[var2].toReadableString());
            var3 = var5.toString();
            StringBuilder var4 = new StringBuilder();
            var4.append(var3);
            var4.append("\n");
            var3 = var4.toString();
         }

         ++var2;
      }

      var5 = new StringBuilder();
      var5.append(var1);
      var5.append(this.mGoal);
      var5.append("\n");
      var1 = var5.toString();
      System.out.println(var1);
   }

   public void fillMetrics(Metrics var1) {
      sMetrics = var1;
   }

   public Cache getCache() {
      return this.mCache;
   }

   LinearSystem.Row getGoal() {
      return this.mGoal;
   }

   public int getMemoryUsed() {
      int var1 = 0;

      int var2;
      int var4;
      for(var2 = 0; var1 < this.mNumRows; var2 = var4) {
         ArrayRow[] var3 = this.mRows;
         var4 = var2;
         if (var3[var1] != null) {
            var4 = var2 + var3[var1].sizeInBytes();
         }

         ++var1;
      }

      return var2;
   }

   public int getNumEquations() {
      return this.mNumRows;
   }

   public int getNumVariables() {
      return this.mVariablesID;
   }

   public int getObjectVariableValue(Object var1) {
      SolverVariable var2 = ((ConstraintAnchor)var1).getSolverVariable();
      return var2 != null ? (int)(var2.computedValue + 0.5F) : 0;
   }

   ArrayRow getRow(int var1) {
      return this.mRows[var1];
   }

   float getValueFor(String var1) {
      SolverVariable var2 = this.getVariable(var1, SolverVariable.Type.UNRESTRICTED);
      return var2 == null ? 0.0F : var2.computedValue;
   }

   SolverVariable getVariable(String var1, SolverVariable.Type var2) {
      if (this.mVariables == null) {
         this.mVariables = new HashMap();
      }

      SolverVariable var3 = (SolverVariable)this.mVariables.get(var1);
      SolverVariable var4 = var3;
      if (var3 == null) {
         var4 = this.createVariable(var1, var2);
      }

      return var4;
   }

   public void minimize() throws Exception {
      Metrics var1 = sMetrics;
      if (var1 != null) {
         ++var1.minimize;
      }

      if (!this.graphOptimizer && !this.newgraphOptimizer) {
         this.minimizeGoal(this.mGoal);
      } else {
         var1 = sMetrics;
         if (var1 != null) {
            ++var1.graphOptimizer;
         }

         boolean var2 = false;
         int var3 = 0;

         boolean var4;
         while(true) {
            if (var3 >= this.mNumRows) {
               var4 = true;
               break;
            }

            if (!this.mRows[var3].isSimpleDefinition) {
               var4 = var2;
               break;
            }

            ++var3;
         }

         if (!var4) {
            this.minimizeGoal(this.mGoal);
         } else {
            var1 = sMetrics;
            if (var1 != null) {
               ++var1.fullySolved;
            }

            this.computeValues();
         }
      }

   }

   void minimizeGoal(LinearSystem.Row var1) throws Exception {
      Metrics var2 = sMetrics;
      if (var2 != null) {
         ++var2.minimizeGoal;
         var2 = sMetrics;
         var2.maxVariables = Math.max(var2.maxVariables, (long)this.mNumColumns);
         var2 = sMetrics;
         var2.maxRows = Math.max(var2.maxRows, (long)this.mNumRows);
      }

      this.enforceBFS(var1);
      this.optimize(var1, false);
      this.computeValues();
   }

   public void removeRow(ArrayRow var1) {
      if (var1.isSimpleDefinition && var1.variable != null) {
         if (var1.variable.definitionId != -1) {
            int var2 = var1.variable.definitionId;

            while(true) {
               int var3 = this.mNumRows;
               if (var2 >= var3 - 1) {
                  this.mNumRows = var3 - 1;
                  break;
               }

               ArrayRow[] var4 = this.mRows;
               var3 = var2 + 1;
               var4[var2] = var4[var3];
               var2 = var3;
            }
         }

         var1.variable.setFinalValue(this, var1.constantValue);
      }

   }

   public void reset() {
      int var1;
      for(var1 = 0; var1 < this.mCache.mIndexedVariables.length; ++var1) {
         SolverVariable var2 = this.mCache.mIndexedVariables[var1];
         if (var2 != null) {
            var2.reset();
         }
      }

      this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
      this.mPoolVariablesCount = 0;
      Arrays.fill(this.mCache.mIndexedVariables, (Object)null);
      HashMap var3 = this.mVariables;
      if (var3 != null) {
         var3.clear();
      }

      this.mVariablesID = 0;
      this.mGoal.clear();
      this.mNumColumns = 1;

      for(var1 = 0; var1 < this.mNumRows; ++var1) {
         this.mRows[var1].used = false;
      }

      this.releaseRows();
      this.mNumRows = 0;
      if (OPTIMIZED_ENGINE) {
         this.mTempGoal = new LinearSystem.ValuesRow(this.mCache);
      } else {
         this.mTempGoal = new ArrayRow(this.mCache);
      }

   }

   interface Row {
      void addError(SolverVariable var1);

      void clear();

      SolverVariable getKey();

      SolverVariable getPivotCandidate(LinearSystem var1, boolean[] var2);

      void initFromRow(LinearSystem.Row var1);

      boolean isEmpty();

      void updateFromFinalVariable(LinearSystem var1, SolverVariable var2, boolean var3);

      void updateFromRow(ArrayRow var1, boolean var2);

      void updateFromSystem(LinearSystem var1);
   }

   class ValuesRow extends ArrayRow {
      public ValuesRow(Cache var2) {
         this.variables = new SolverVariableValues(this, var2);
      }
   }
}
