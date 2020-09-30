package androidx.constraintlayout.solver;

import java.io.PrintStream;
import java.util.Arrays;

public class SolverVariableValues implements ArrayRow.ArrayRowVariables {
   private static final boolean DEBUG = false;
   private static final boolean HASH = true;
   private static float epsilon;
   private int HASH_SIZE = 16;
   private final int NONE = -1;
   private int SIZE = 16;
   int head = -1;
   int[] keys = new int[16];
   protected final Cache mCache;
   int mCount = 0;
   private final ArrayRow mRow;
   int[] next = new int[16];
   int[] nextKeys = new int[16];
   int[] previous = new int[16];
   float[] values = new float[16];
   int[] variables = new int[16];

   SolverVariableValues(ArrayRow var1, Cache var2) {
      this.mRow = var1;
      this.mCache = var2;
      this.clear();
   }

   private void addToHashMap(SolverVariable var1, int var2) {
      int var3 = var1.id % this.HASH_SIZE;
      int[] var6 = this.keys;
      int var4 = var6[var3];
      int var5 = var4;
      if (var4 == -1) {
         var6[var3] = var2;
      } else {
         while(true) {
            var6 = this.nextKeys;
            if (var6[var5] == -1) {
               var6[var5] = var2;
               break;
            }

            var5 = var6[var5];
         }
      }

      this.nextKeys[var2] = -1;
   }

   private void addVariable(int var1, SolverVariable var2, float var3) {
      this.variables[var1] = var2.id;
      this.values[var1] = var3;
      this.previous[var1] = -1;
      this.next[var1] = -1;
      var2.addToRow(this.mRow);
      ++var2.usageInRowCount;
      ++this.mCount;
   }

   private void displayHash() {
      for(int var1 = 0; var1 < this.HASH_SIZE; ++var1) {
         if (this.keys[var1] != -1) {
            StringBuilder var2 = new StringBuilder();
            var2.append(this.hashCode());
            var2.append(" hash [");
            var2.append(var1);
            var2.append("] => ");
            String var6 = var2.toString();
            int var3 = this.keys[var1];
            boolean var4 = false;

            while(!var4) {
               StringBuilder var5 = new StringBuilder();
               var5.append(var6);
               var5.append(" ");
               var5.append(this.variables[var3]);
               var6 = var5.toString();
               int[] var7 = this.nextKeys;
               if (var7[var3] != -1) {
                  var3 = var7[var3];
               } else {
                  var4 = true;
               }
            }

            System.out.println(var6);
         }
      }

   }

   private int findEmptySlot() {
      for(int var1 = 0; var1 < this.SIZE; ++var1) {
         if (this.variables[var1] == -1) {
            return var1;
         }
      }

      return -1;
   }

   private void increaseSize() {
      int var1 = this.SIZE * 2;
      this.variables = Arrays.copyOf(this.variables, var1);
      this.values = Arrays.copyOf(this.values, var1);
      this.previous = Arrays.copyOf(this.previous, var1);
      this.next = Arrays.copyOf(this.next, var1);
      this.nextKeys = Arrays.copyOf(this.nextKeys, var1);

      for(int var2 = this.SIZE; var2 < var1; ++var2) {
         this.variables[var2] = -1;
         this.nextKeys[var2] = -1;
      }

      this.SIZE = var1;
   }

   private void insertVariable(int var1, SolverVariable var2, float var3) {
      int var4 = this.findEmptySlot();
      this.addVariable(var4, var2, var3);
      int[] var5;
      if (var1 != -1) {
         this.previous[var4] = var1;
         var5 = this.next;
         var5[var4] = var5[var1];
         var5[var1] = var4;
      } else {
         this.previous[var4] = -1;
         if (this.mCount > 0) {
            this.next[var4] = this.head;
            this.head = var4;
         } else {
            this.next[var4] = -1;
         }
      }

      var5 = this.next;
      if (var5[var4] != -1) {
         this.previous[var5[var4]] = var4;
      }

      this.addToHashMap(var2, var4);
   }

   private void removeFromHashMap(SolverVariable var1) {
      int var2 = var1.id % this.HASH_SIZE;
      int var3 = this.keys[var2];
      if (var3 != -1) {
         int var4 = var1.id;
         int var5 = var3;
         int[] var7;
         if (this.variables[var3] == var4) {
            int[] var6 = this.keys;
            var7 = this.nextKeys;
            var6[var2] = var7[var3];
            var7[var3] = -1;
         } else {
            while(true) {
               var7 = this.nextKeys;
               if (var7[var5] == -1 || this.variables[var7[var5]] == var4) {
                  var7 = this.nextKeys;
                  var3 = var7[var5];
                  if (var3 != -1 && this.variables[var3] == var4) {
                     var7[var5] = var7[var3];
                     var7[var3] = -1;
                  }
                  break;
               }

               var5 = var7[var5];
            }
         }

      }
   }

   public void add(SolverVariable var1, float var2, boolean var3) {
      float var4 = epsilon;
      if (var2 <= -var4 || var2 >= var4) {
         int var5 = this.indexOf(var1);
         if (var5 == -1) {
            this.put(var1, var2);
         } else {
            float[] var6 = this.values;
            var6[var5] += var2;
            var2 = var6[var5];
            var4 = epsilon;
            if (var2 > -var4 && var6[var5] < var4) {
               var6[var5] = 0.0F;
               this.remove(var1, var3);
            }
         }

      }
   }

   public void clear() {
      int var1 = this.mCount;

      int var2;
      for(var2 = 0; var2 < var1; ++var2) {
         SolverVariable var3 = this.getVariable(var2);
         if (var3 != null) {
            var3.removeFromRow(this.mRow);
         }
      }

      for(var2 = 0; var2 < this.SIZE; ++var2) {
         this.variables[var2] = -1;
         this.nextKeys[var2] = -1;
      }

      for(var2 = 0; var2 < this.HASH_SIZE; ++var2) {
         this.keys[var2] = -1;
      }

      this.mCount = 0;
      this.head = -1;
   }

   public boolean contains(SolverVariable var1) {
      boolean var2;
      if (this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void display() {
      int var1 = this.mCount;
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
      int var2 = this.mCount;
      int var3 = this.head;

      for(int var4 = 0; var4 < var2; ++var4) {
         float[] var5 = this.values;
         var5[var3] /= var1;
         var3 = this.next[var3];
         if (var3 == -1) {
            break;
         }
      }

   }

   public float get(SolverVariable var1) {
      int var2 = this.indexOf(var1);
      return var2 != -1 ? this.values[var2] : 0.0F;
   }

   public int getCurrentSize() {
      return this.mCount;
   }

   public SolverVariable getVariable(int var1) {
      int var2 = this.mCount;
      if (var2 == 0) {
         return null;
      } else {
         int var3 = this.head;

         for(int var4 = 0; var4 < var2; ++var4) {
            if (var4 == var1 && var3 != -1) {
               return this.mCache.mIndexedVariables[this.variables[var3]];
            }

            var3 = this.next[var3];
            if (var3 == -1) {
               break;
            }
         }

         return null;
      }
   }

   public float getVariableValue(int var1) {
      int var2 = this.mCount;
      int var3 = this.head;

      for(int var4 = 0; var4 < var2; ++var4) {
         if (var4 == var1) {
            return this.values[var3];
         }

         var3 = this.next[var3];
         if (var3 == -1) {
            break;
         }
      }

      return 0.0F;
   }

   public int indexOf(SolverVariable var1) {
      if (this.mCount == 0) {
         return -1;
      } else {
         int var2 = var1.id;
         int var3 = this.HASH_SIZE;
         int var4 = this.keys[var2 % var3];
         if (var4 == -1) {
            return -1;
         } else {
            var3 = var4;
            if (this.variables[var4] == var2) {
               return var4;
            } else {
               while(true) {
                  int[] var5 = this.nextKeys;
                  if (var5[var3] == -1 || this.variables[var5[var3]] == var2) {
                     var5 = this.nextKeys;
                     if (var5[var3] == -1) {
                        return -1;
                     } else {
                        return this.variables[var5[var3]] == var2 ? var5[var3] : -1;
                     }
                  }

                  var3 = var5[var3];
               }
            }
         }
      }
   }

   public void invert() {
      int var1 = this.mCount;
      int var2 = this.head;

      for(int var3 = 0; var3 < var1; ++var3) {
         float[] var4 = this.values;
         var4[var2] *= -1.0F;
         var2 = this.next[var2];
         if (var2 == -1) {
            break;
         }
      }

   }

   public void put(SolverVariable var1, float var2) {
      float var3 = epsilon;
      if (var2 > -var3 && var2 < var3) {
         this.remove(var1, true);
      } else {
         int var4 = this.mCount;
         int var5 = 0;
         if (var4 == 0) {
            this.addVariable(0, var1, var2);
            this.addToHashMap(var1, 0);
            this.head = 0;
         } else {
            var4 = this.indexOf(var1);
            if (var4 != -1) {
               this.values[var4] = var2;
            } else {
               if (this.mCount + 1 >= this.SIZE) {
                  this.increaseSize();
               }

               int var6 = this.mCount;
               var4 = this.head;
               int var7 = -1;

               while(true) {
                  int var8 = var7;
                  if (var5 < var6) {
                     if (this.variables[var4] == var1.id) {
                        this.values[var4] = var2;
                        return;
                     }

                     if (this.variables[var4] < var1.id) {
                        var7 = var4;
                     }

                     var4 = this.next[var4];
                     if (var4 != -1) {
                        ++var5;
                        continue;
                     }

                     var8 = var7;
                  }

                  this.insertVariable(var8, var1, var2);
                  break;
               }
            }
         }

      }
   }

   public float remove(SolverVariable var1, boolean var2) {
      int var3 = this.indexOf(var1);
      if (var3 == -1) {
         return 0.0F;
      } else {
         this.removeFromHashMap(var1);
         float var4 = this.values[var3];
         if (this.head == var3) {
            this.head = this.next[var3];
         }

         this.variables[var3] = -1;
         int[] var5 = this.previous;
         int[] var6;
         if (var5[var3] != -1) {
            var6 = this.next;
            var6[var5[var3]] = var6[var3];
         }

         var6 = this.next;
         if (var6[var3] != -1) {
            var5 = this.previous;
            var5[var6[var3]] = var5[var3];
         }

         --this.mCount;
         --var1.usageInRowCount;
         if (var2) {
            var1.removeFromRow(this.mRow);
         }

         return var4;
      }
   }

   public int sizeInBytes() {
      return 0;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.hashCode());
      var1.append(" { ");
      String var7 = var1.toString();
      int var2 = this.mCount;

      StringBuilder var8;
      for(int var3 = 0; var3 < var2; ++var3) {
         SolverVariable var4 = this.getVariable(var3);
         if (var4 != null) {
            StringBuilder var5 = new StringBuilder();
            var5.append(var7);
            var5.append(var4);
            var5.append(" = ");
            var5.append(this.getVariableValue(var3));
            var5.append(" ");
            var7 = var5.toString();
            int var6 = this.indexOf(var4);
            var8 = new StringBuilder();
            var8.append(var7);
            var8.append("[p: ");
            var7 = var8.toString();
            if (this.previous[var6] != -1) {
               var8 = new StringBuilder();
               var8.append(var7);
               var8.append(this.mCache.mIndexedVariables[this.variables[this.previous[var6]]]);
               var7 = var8.toString();
            } else {
               var8 = new StringBuilder();
               var8.append(var7);
               var8.append("none");
               var7 = var8.toString();
            }

            var8 = new StringBuilder();
            var8.append(var7);
            var8.append(", n: ");
            var7 = var8.toString();
            if (this.next[var6] != -1) {
               var8 = new StringBuilder();
               var8.append(var7);
               var8.append(this.mCache.mIndexedVariables[this.variables[this.next[var6]]]);
               var7 = var8.toString();
            } else {
               var8 = new StringBuilder();
               var8.append(var7);
               var8.append("none");
               var7 = var8.toString();
            }

            var8 = new StringBuilder();
            var8.append(var7);
            var8.append("]");
            var7 = var8.toString();
         }
      }

      var8 = new StringBuilder();
      var8.append(var7);
      var8.append(" }");
      return var8.toString();
   }

   public float use(ArrayRow var1, boolean var2) {
      float var3 = this.get(var1.variable);
      this.remove(var1.variable, var2);
      SolverVariableValues var9 = (SolverVariableValues)var1.variables;
      int var4 = var9.getCurrentSize();
      int var5 = var9.head;
      int var6 = 0;

      int var7;
      for(var5 = 0; var6 < var4; var6 = var7) {
         var7 = var6;
         if (var9.variables[var5] != -1) {
            float var8 = var9.values[var5];
            this.add(this.mCache.mIndexedVariables[var9.variables[var5]], var8 * var3, var2);
            var7 = var6 + 1;
         }

         ++var5;
      }

      return var3;
   }
}
