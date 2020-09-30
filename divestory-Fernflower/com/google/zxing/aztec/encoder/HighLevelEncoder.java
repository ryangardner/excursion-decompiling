package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public final class HighLevelEncoder {
   private static final int[][] CHAR_MAP;
   static final int[][] LATCH_TABLE = new int[][]{{0, 327708, 327710, 327709, 656318}, {590318, 0, 327710, 327709, 656318}, {262158, 590300, 0, 590301, 932798}, {327709, 327708, 656318, 0, 327710}, {327711, 656380, 656382, 656381, 0}};
   static final int MODE_DIGIT = 2;
   static final int MODE_LOWER = 1;
   static final int MODE_MIXED = 3;
   static final String[] MODE_NAMES = new String[]{"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
   static final int MODE_PUNCT = 4;
   static final int MODE_UPPER = 0;
   static final int[][] SHIFT_TABLE;
   private final byte[] text;

   static {
      int[][] var0 = new int[5][256];
      CHAR_MAP = var0;
      var0[0][32] = 1;

      int var1;
      for(var1 = 65; var1 <= 90; ++var1) {
         CHAR_MAP[0][var1] = var1 - 65 + 2;
      }

      CHAR_MAP[1][32] = 1;

      for(var1 = 97; var1 <= 122; ++var1) {
         CHAR_MAP[1][var1] = var1 - 97 + 2;
      }

      CHAR_MAP[2][32] = 1;

      for(var1 = 48; var1 <= 57; ++var1) {
         CHAR_MAP[2][var1] = var1 - 48 + 2;
      }

      var0 = CHAR_MAP;
      var0[2][44] = 12;
      var0[2][46] = 13;

      for(var1 = 0; var1 < 28; CHAR_MAP[3][(new int[]{0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, 126, 127})[var1]] = var1++) {
      }

      int[] var3 = new int[]{0, 13, 0, 0, 0, 0, 33, 39, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 91, 93, 123, 125};

      for(var1 = 0; var1 < 31; ++var1) {
         if (var3[var1] > 0) {
            CHAR_MAP[4][var3[var1]] = var1;
         }
      }

      var0 = new int[6][6];
      SHIFT_TABLE = var0;
      int var2 = var0.length;

      for(var1 = 0; var1 < var2; ++var1) {
         Arrays.fill(var0[var1], -1);
      }

      var0 = SHIFT_TABLE;
      var0[0][4] = 0;
      var0[1][4] = 0;
      var0[1][0] = 28;
      var0[3][4] = 0;
      var0[2][4] = 0;
      var0[2][0] = 15;
   }

   public HighLevelEncoder(byte[] var1) {
      this.text = var1;
   }

   private static Collection<State> simplifyStates(Iterable<State> var0) {
      LinkedList var1 = new LinkedList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         State var7 = (State)var2.next();
         boolean var3 = true;
         Iterator var4 = var1.iterator();

         boolean var5;
         while(true) {
            var5 = var3;
            if (!var4.hasNext()) {
               break;
            }

            State var6 = (State)var4.next();
            if (var6.isBetterThanOrEqualTo(var7)) {
               var5 = false;
               break;
            }

            if (var7.isBetterThanOrEqualTo(var6)) {
               var4.remove();
            }
         }

         if (var5) {
            var1.add(var7);
         }
      }

      return var1;
   }

   private void updateStateForChar(State var1, int var2, Collection<State> var3) {
      char var4 = (char)(this.text[var2] & 255);
      int var5 = CHAR_MAP[var1.getMode()][var4];
      int var6 = 0;
      boolean var11;
      if (var5 > 0) {
         var11 = true;
      } else {
         var11 = false;
      }

      State var9;
      for(State var7 = null; var6 <= 4; var7 = var9) {
         int var8 = CHAR_MAP[var6][var4];
         var9 = var7;
         if (var8 > 0) {
            State var10 = var7;
            if (var7 == null) {
               var10 = var1.endBinaryShift(var2);
            }

            if (!var11 || var6 == var1.getMode() || var6 == 2) {
               var3.add(var10.latchAndAppend(var6, var8));
            }

            var9 = var10;
            if (!var11) {
               var9 = var10;
               if (SHIFT_TABLE[var1.getMode()][var6] >= 0) {
                  var3.add(var10.shiftAndAppend(var6, var8));
                  var9 = var10;
               }
            }
         }

         ++var6;
      }

      if (var1.getBinaryShiftByteCount() > 0 || CHAR_MAP[var1.getMode()][var4] == 0) {
         var3.add(var1.addBinaryShiftChar(var2));
      }

   }

   private static void updateStateForPair(State var0, int var1, int var2, Collection<State> var3) {
      State var4 = var0.endBinaryShift(var1);
      var3.add(var4.latchAndAppend(4, var2));
      if (var0.getMode() != 4) {
         var3.add(var4.shiftAndAppend(4, var2));
      }

      if (var2 == 3 || var2 == 4) {
         var3.add(var4.latchAndAppend(2, 16 - var2).latchAndAppend(2, 1));
      }

      if (var0.getBinaryShiftByteCount() > 0) {
         var3.add(var0.addBinaryShiftChar(var1).addBinaryShiftChar(var1 + 1));
      }

   }

   private Collection<State> updateStateListForChar(Iterable<State> var1, int var2) {
      LinkedList var3 = new LinkedList();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         this.updateStateForChar((State)var4.next(), var2, var3);
      }

      return simplifyStates(var3);
   }

   private static Collection<State> updateStateListForPair(Iterable<State> var0, int var1, int var2) {
      LinkedList var3 = new LinkedList();
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         updateStateForPair((State)var4.next(), var1, var2, var3);
      }

      return simplifyStates(var3);
   }

   public BitArray encode() {
      Object var1 = Collections.singletonList(State.INITIAL_STATE);
      int var2 = 0;

      while(true) {
         byte[] var3 = this.text;
         if (var2 >= var3.length) {
            return ((State)Collections.min((Collection)var1, new Comparator<State>() {
               public int compare(State var1, State var2) {
                  return var1.getBitCount() - var2.getBitCount();
               }
            })).toBitArray(this.text);
         }

         int var4 = var2 + 1;
         byte var5;
         if (var4 < var3.length) {
            var5 = var3[var4];
         } else {
            var5 = 0;
         }

         byte var7;
         label41: {
            byte var6 = this.text[var2];
            if (var6 != 13) {
               if (var6 != 44) {
                  if (var6 != 46) {
                     if (var6 == 58 && var5 == 32) {
                        var7 = 5;
                        break label41;
                     }
                  } else if (var5 == 32) {
                     var7 = 3;
                     break label41;
                  }
               } else if (var5 == 32) {
                  var7 = 4;
                  break label41;
               }
            } else if (var5 == 10) {
               var7 = 2;
               break label41;
            }

            var7 = 0;
         }

         int var8;
         if (var7 > 0) {
            var1 = updateStateListForPair((Iterable)var1, var2, var7);
            var8 = var4;
         } else {
            var1 = this.updateStateListForChar((Iterable)var1, var2);
            var8 = var2;
         }

         var2 = var8 + 1;
      }
   }
}
