package androidx.constraintlayout.solver;

import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables implements ArrayRow.ArrayRowVariables {
   private static final boolean DEBUG = false;
   private static final boolean FULL_NEW_CHECK = false;
   static final int NONE = -1;
   private static float epsilon;
   private int ROW_SIZE = 8;
   private SolverVariable candidate = null;
   int currentSize = 0;
   private int[] mArrayIndices = new int[8];
   private int[] mArrayNextIndices = new int[8];
   private float[] mArrayValues = new float[8];
   protected final Cache mCache;
   private boolean mDidFillOnce = false;
   private int mHead = -1;
   private int mLast = -1;
   private final ArrayRow mRow;

   ArrayLinkedVariables(ArrayRow var1, Cache var2) {
      this.mRow = var1;
      this.mCache = var2;
   }

   public void add(SolverVariable var1, float var2, boolean var3) {
      float var4 = epsilon;
      if (var2 <= -var4 || var2 >= var4) {
         int var5 = this.mHead;
         int[] var11;
         if (var5 == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = var2;
            this.mArrayIndices[0] = var1.id;
            this.mArrayNextIndices[this.mHead] = -1;
            ++var1.usageInRowCount;
            var1.addToRow(this.mRow);
            ++this.currentSize;
            if (!this.mDidFillOnce) {
               var5 = this.mLast + 1;
               this.mLast = var5;
               var11 = this.mArrayIndices;
               if (var5 >= var11.length) {
                  this.mDidFillOnce = true;
                  this.mLast = var11.length - 1;
               }
            }

         } else {
            int var6 = 0;

            int var7;
            int[] var9;
            for(var7 = -1; var5 != -1 && var6 < this.currentSize; ++var6) {
               if (this.mArrayIndices[var5] == var1.id) {
                  var4 = this.mArrayValues[var5] + var2;
                  float var8 = epsilon;
                  var2 = var4;
                  if (var4 > -var8) {
                     var2 = var4;
                     if (var4 < var8) {
                        var2 = 0.0F;
                     }
                  }

                  this.mArrayValues[var5] = var2;
                  if (var2 == 0.0F) {
                     if (var5 == this.mHead) {
                        this.mHead = this.mArrayNextIndices[var5];
                     } else {
                        var9 = this.mArrayNextIndices;
                        var9[var7] = var9[var5];
                     }

                     if (var3) {
                        var1.removeFromRow(this.mRow);
                     }

                     if (this.mDidFillOnce) {
                        this.mLast = var5;
                     }

                     --var1.usageInRowCount;
                     --this.currentSize;
                  }

                  return;
               }

               if (this.mArrayIndices[var5] < var1.id) {
                  var7 = var5;
               }

               var5 = this.mArrayNextIndices[var5];
            }

            var5 = this.mLast;
            if (this.mDidFillOnce) {
               var9 = this.mArrayIndices;
               if (var9[var5] != -1) {
                  var5 = var9.length;
               }
            } else {
               ++var5;
            }

            var9 = this.mArrayIndices;
            var6 = var5;
            if (var5 >= var9.length) {
               var6 = var5;
               if (this.currentSize < var9.length) {
                  int var10 = 0;

                  while(true) {
                     var9 = this.mArrayIndices;
                     var6 = var5;
                     if (var10 >= var9.length) {
                        break;
                     }

                     if (var9[var10] == -1) {
                        var6 = var10;
                        break;
                     }

                     ++var10;
                  }
               }
            }

            var9 = this.mArrayIndices;
            var5 = var6;
            if (var6 >= var9.length) {
               var5 = var9.length;
               var6 = this.ROW_SIZE * 2;
               this.ROW_SIZE = var6;
               this.mDidFillOnce = false;
               this.mLast = var5 - 1;
               this.mArrayValues = Arrays.copyOf(this.mArrayValues, var6);
               this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
               this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
            }

            this.mArrayIndices[var5] = var1.id;
            this.mArrayValues[var5] = var2;
            if (var7 != -1) {
               var9 = this.mArrayNextIndices;
               var9[var5] = var9[var7];
               var9[var7] = var5;
            } else {
               this.mArrayNextIndices[var5] = this.mHead;
               this.mHead = var5;
            }

            ++var1.usageInRowCount;
            var1.addToRow(this.mRow);
            ++this.currentSize;
            if (!this.mDidFillOnce) {
               ++this.mLast;
            }

            var5 = this.mLast;
            var11 = this.mArrayIndices;
            if (var5 >= var11.length) {
               this.mDidFillOnce = true;
               this.mLast = var11.length - 1;
            }

         }
      }
   }

   public final void clear() {
      int var1 = this.mHead;

      for(int var2 = 0; var1 != -1 && var2 < this.currentSize; ++var2) {
         SolverVariable var3 = this.mCache.mIndexedVariables[this.mArrayIndices[var1]];
         if (var3 != null) {
            var3.removeFromRow(this.mRow);
         }

         var1 = this.mArrayNextIndices[var1];
      }

      this.mHead = -1;
      this.mLast = -1;
      this.mDidFillOnce = false;
      this.currentSize = 0;
   }

   public boolean contains(SolverVariable var1) {
      int var2 = this.mHead;
      if (var2 == -1) {
         return false;
      } else {
         for(int var3 = 0; var2 != -1 && var3 < this.currentSize; ++var3) {
            if (this.mArrayIndices[var2] == var1.id) {
               return true;
            }

            var2 = this.mArrayNextIndices[var2];
         }

         return false;
      }
   }

   public void display() {
      int var1 = this.currentSize;
      System.out.print("{ ");

      for(int var2 = 0; var2 < var1; ++var2) {
         SolverVariable var3 = this.getVariable(var2);
         if (var3 != null) {
            PrintStream var4 = System.out;
            StringBuilder var5 = new StringBuilder();
            var5.append(var3);
            var5.append(" = ");
            var5.append(this.getVariableValue(var2));
            var5.append(" ");
            var4.print(var5.toString());
         }
      }

      System.out.println(" }");
   }

   public void divideByAmount(float var1) {
      int var2 = this.mHead;

      for(int var3 = 0; var2 != -1 && var3 < this.currentSize; ++var3) {
         float[] var4 = this.mArrayValues;
         var4[var2] /= var1;
         var2 = this.mArrayNextIndices[var2];
      }

   }

   public final float get(SolverVariable var1) {
      int var2 = this.mHead;

      for(int var3 = 0; var2 != -1 && var3 < this.currentSize; ++var3) {
         if (this.mArrayIndices[var2] == var1.id) {
            return this.mArrayValues[var2];
         }

         var2 = this.mArrayNextIndices[var2];
      }

      return 0.0F;
   }

   public int getCurrentSize() {
      return this.currentSize;
   }

   public int getHead() {
      return this.mHead;
   }

   public final int getId(int var1) {
      return this.mArrayIndices[var1];
   }

   public final int getNextIndice(int var1) {
      return this.mArrayNextIndices[var1];
   }

   SolverVariable getPivotCandidate() {
      SolverVariable var1 = this.candidate;
      if (var1 != null) {
         return var1;
      } else {
         int var2 = this.mHead;
         int var3 = 0;

         SolverVariable var4;
         for(var1 = null; var2 != -1 && var3 < this.currentSize; var1 = var4) {
            var4 = var1;
            if (this.mArrayValues[var2] < 0.0F) {
               label24: {
                  SolverVariable var5 = this.mCache.mIndexedVariables[this.mArrayIndices[var2]];
                  if (var1 != null) {
                     var4 = var1;
                     if (var1.strength >= var5.strength) {
                        break label24;
                     }
                  }

                  var4 = var5;
               }
            }

            var2 = this.mArrayNextIndices[var2];
            ++var3;
         }

         return var1;
      }
   }

   public final float getValue(int var1) {
      return this.mArrayValues[var1];
   }

   public SolverVariable getVariable(int var1) {
      int var2 = this.mHead;

      for(int var3 = 0; var2 != -1 && var3 < this.currentSize; ++var3) {
         if (var3 == var1) {
            return this.mCache.mIndexedVariables[this.mArrayIndices[var2]];
         }

         var2 = this.mArrayNextIndices[var2];
      }

      return null;
   }

   public float getVariableValue(int var1) {
      int var2 = this.mHead;

      for(int var3 = 0; var2 != -1 && var3 < this.currentSize; ++var3) {
         if (var3 == var1) {
            return this.mArrayValues[var2];
         }

         var2 = this.mArrayNextIndices[var2];
      }

      return 0.0F;
   }

   boolean hasAtLeastOnePositiveVariable() {
      int var1 = this.mHead;

      for(int var2 = 0; var1 != -1 && var2 < this.currentSize; ++var2) {
         if (this.mArrayValues[var1] > 0.0F) {
            return true;
         }

         var1 = this.mArrayNextIndices[var1];
      }

      return false;
   }

   public int indexOf(SolverVariable var1) {
      int var2 = this.mHead;
      if (var2 == -1) {
         return -1;
      } else {
         for(int var3 = 0; var2 != -1 && var3 < this.currentSize; ++var3) {
            if (this.mArrayIndices[var2] == var1.id) {
               return var2;
            }

            var2 = this.mArrayNextIndices[var2];
         }

         return -1;
      }
   }

   public void invert() {
      int var1 = this.mHead;

      for(int var2 = 0; var1 != -1 && var2 < this.currentSize; ++var2) {
         float[] var3 = this.mArrayValues;
         var3[var1] *= -1.0F;
         var1 = this.mArrayNextIndices[var1];
      }

   }

   public final void put(SolverVariable var1, float var2) {
      if (var2 == 0.0F) {
         this.remove(var1, true);
      } else {
         int var3 = this.mHead;
         int[] var8;
         if (var3 == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = var2;
            this.mArrayIndices[0] = var1.id;
            this.mArrayNextIndices[this.mHead] = -1;
            ++var1.usageInRowCount;
            var1.addToRow(this.mRow);
            ++this.currentSize;
            if (!this.mDidFillOnce) {
               var3 = this.mLast + 1;
               this.mLast = var3;
               var8 = this.mArrayIndices;
               if (var3 >= var8.length) {
                  this.mDidFillOnce = true;
                  this.mLast = var8.length - 1;
               }
            }

         } else {
            int var4 = 0;

            int var5;
            for(var5 = -1; var3 != -1 && var4 < this.currentSize; ++var4) {
               if (this.mArrayIndices[var3] == var1.id) {
                  this.mArrayValues[var3] = var2;
                  return;
               }

               if (this.mArrayIndices[var3] < var1.id) {
                  var5 = var3;
               }

               var3 = this.mArrayNextIndices[var3];
            }

            var3 = this.mLast;
            int[] var6;
            if (this.mDidFillOnce) {
               var6 = this.mArrayIndices;
               if (var6[var3] != -1) {
                  var3 = var6.length;
               }
            } else {
               ++var3;
            }

            var6 = this.mArrayIndices;
            var4 = var3;
            if (var3 >= var6.length) {
               var4 = var3;
               if (this.currentSize < var6.length) {
                  int var7 = 0;

                  while(true) {
                     var6 = this.mArrayIndices;
                     var4 = var3;
                     if (var7 >= var6.length) {
                        break;
                     }

                     if (var6[var7] == -1) {
                        var4 = var7;
                        break;
                     }

                     ++var7;
                  }
               }
            }

            var6 = this.mArrayIndices;
            var3 = var4;
            if (var4 >= var6.length) {
               var3 = var6.length;
               var4 = this.ROW_SIZE * 2;
               this.ROW_SIZE = var4;
               this.mDidFillOnce = false;
               this.mLast = var3 - 1;
               this.mArrayValues = Arrays.copyOf(this.mArrayValues, var4);
               this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
               this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
            }

            this.mArrayIndices[var3] = var1.id;
            this.mArrayValues[var3] = var2;
            if (var5 != -1) {
               var6 = this.mArrayNextIndices;
               var6[var3] = var6[var5];
               var6[var5] = var3;
            } else {
               this.mArrayNextIndices[var3] = this.mHead;
               this.mHead = var3;
            }

            ++var1.usageInRowCount;
            var1.addToRow(this.mRow);
            ++this.currentSize;
            if (!this.mDidFillOnce) {
               ++this.mLast;
            }

            if (this.currentSize >= this.mArrayIndices.length) {
               this.mDidFillOnce = true;
            }

            var3 = this.mLast;
            var8 = this.mArrayIndices;
            if (var3 >= var8.length) {
               this.mDidFillOnce = true;
               this.mLast = var8.length - 1;
            }

         }
      }
   }

   public final float remove(SolverVariable var1, boolean var2) {
      if (this.candidate == var1) {
         this.candidate = null;
      }

      int var3 = this.mHead;
      if (var3 == -1) {
         return 0.0F;
      } else {
         int var4 = 0;

         int var7;
         for(int var5 = -1; var3 != -1 && var4 < this.currentSize; var3 = var7) {
            if (this.mArrayIndices[var3] == var1.id) {
               if (var3 == this.mHead) {
                  this.mHead = this.mArrayNextIndices[var3];
               } else {
                  int[] var6 = this.mArrayNextIndices;
                  var6[var5] = var6[var3];
               }

               if (var2) {
                  var1.removeFromRow(this.mRow);
               }

               --var1.usageInRowCount;
               --this.currentSize;
               this.mArrayIndices[var3] = -1;
               if (this.mDidFillOnce) {
                  this.mLast = var3;
               }

               return this.mArrayValues[var3];
            }

            var7 = this.mArrayNextIndices[var3];
            ++var4;
            var5 = var3;
         }

         return 0.0F;
      }
   }

   public int sizeInBytes() {
      return this.mArrayIndices.length * 4 * 3 + 0 + 36;
   }

   public String toString() {
      int var1 = this.mHead;
      String var2 = "";

      for(int var3 = 0; var1 != -1 && var3 < this.currentSize; ++var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var2);
         var4.append(" -> ");
         String var6 = var4.toString();
         StringBuilder var5 = new StringBuilder();
         var5.append(var6);
         var5.append(this.mArrayValues[var1]);
         var5.append(" : ");
         var2 = var5.toString();
         var4 = new StringBuilder();
         var4.append(var2);
         var4.append(this.mCache.mIndexedVariables[this.mArrayIndices[var1]]);
         var2 = var4.toString();
         var1 = this.mArrayNextIndices[var1];
      }

      return var2;
   }

   public float use(ArrayRow var1, boolean var2) {
      float var3 = this.get(var1.variable);
      this.remove(var1.variable, var2);
      ArrayRow.ArrayRowVariables var4 = var1.variables;
      int var5 = var4.getCurrentSize();

      for(int var6 = 0; var6 < var5; ++var6) {
         SolverVariable var7 = var4.getVariable(var6);
         this.add(var7, var4.get(var7) * var3, var2);
      }

      return var3;
   }
}
