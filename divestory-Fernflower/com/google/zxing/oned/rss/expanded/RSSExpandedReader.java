package com.google.zxing.oned.rss.expanded;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSSExpandedReader extends AbstractRSSReader {
   private static final int[] EVEN_TOTAL_SUBSET = new int[]{4, 20, 52, 104, 204};
   private static final int[][] FINDER_PATTERNS;
   private static final int[][] FINDER_PATTERN_SEQUENCES;
   private static final int FINDER_PAT_A = 0;
   private static final int FINDER_PAT_B = 1;
   private static final int FINDER_PAT_C = 2;
   private static final int FINDER_PAT_D = 3;
   private static final int FINDER_PAT_E = 4;
   private static final int FINDER_PAT_F = 5;
   private static final int[] GSUM = new int[]{0, 348, 1388, 2948, 3988};
   private static final int MAX_PAIRS = 11;
   private static final int[] SYMBOL_WIDEST = new int[]{7, 5, 4, 3, 1};
   private static final int[][] WEIGHTS;
   private final List<ExpandedPair> pairs = new ArrayList(11);
   private final List<ExpandedRow> rows = new ArrayList();
   private final int[] startEnd = new int[2];
   private boolean startFromEven;

   static {
      int[] var0 = new int[]{3, 6, 4, 1};
      int[] var1 = new int[]{3, 2, 8, 1};
      FINDER_PATTERNS = new int[][]{{1, 8, 4, 1}, var0, {3, 4, 6, 1}, var1, {2, 6, 5, 1}, {2, 2, 9, 1}};
      var0 = new int[]{20, 60, 180, 118, 143, 7, 21, 63};
      var1 = new int[]{193, 157, 49, 147, 19, 57, 171, 91};
      int[] var2 = new int[]{113, 128, 173, 97, 80, 29, 87, 50};
      int[] var3 = new int[]{46, 138, 203, 187, 139, 206, 196, 166};
      int[] var4 = new int[]{76, 17, 51, 153, 37, 111, 122, 155};
      int[] var5 = new int[]{134, 191, 151, 31, 93, 68, 204, 190};
      int[] var6 = new int[]{6, 18, 54, 162, 64, 192, 154, 40};
      int[] var7 = new int[]{45, 135, 194, 160, 58, 174, 100, 89};
      WEIGHTS = new int[][]{{1, 3, 9, 27, 81, 32, 96, 77}, var0, {189, 145, 13, 39, 117, 140, 209, 205}, var1, {62, 186, 136, 197, 169, 85, 44, 132}, {185, 133, 188, 142, 4, 12, 36, 108}, var2, {150, 28, 84, 41, 123, 158, 52, 156}, var3, var4, {43, 129, 176, 106, 107, 110, 119, 146}, {16, 48, 144, 10, 30, 90, 59, 177}, {109, 116, 137, 200, 178, 112, 125, 164}, {70, 210, 208, 202, 184, 130, 179, 115}, var5, {148, 22, 66, 198, 172, 94, 71, 2}, var6, {120, 149, 25, 75, 14, 42, 126, 167}, {79, 26, 78, 23, 69, 207, 199, 175}, {103, 98, 83, 38, 114, 131, 182, 124}, {161, 61, 183, 127, 170, 88, 53, 159}, {55, 165, 73, 8, 24, 72, 5, 15}, var7};
      var0 = new int[]{0, 0};
      var1 = new int[]{0, 1, 1};
      var2 = new int[]{0, 4, 1, 3, 2};
      var3 = new int[]{0, 0, 1, 1, 2, 2, 3, 3};
      var4 = new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5};
      FINDER_PATTERN_SEQUENCES = new int[][]{var0, var1, {0, 2, 1, 3}, var2, {0, 4, 1, 3, 3, 5}, {0, 4, 1, 3, 4, 5, 5}, var3, {0, 0, 1, 1, 2, 2, 3, 4, 4}, {0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, var4};
   }

   private void adjustOddEvenCounts(int var1) throws NotFoundException {
      int var2 = count(this.getOddCounts());
      int var3 = count(this.getEvenCounts());
      int var4 = var2 + var3 - var1;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = true;
      boolean var8 = true;
      boolean var9;
      if ((var2 & 1) == 1) {
         var9 = true;
      } else {
         var9 = false;
      }

      boolean var10;
      if ((var3 & 1) == 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      boolean var11;
      boolean var12;
      if (var2 > 13) {
         var12 = false;
         var11 = true;
      } else {
         if (var2 < 4) {
            var12 = true;
         } else {
            var12 = false;
         }

         var11 = false;
      }

      if (var3 > 13) {
         var6 = true;
      } else {
         var5 = var6;
         if (var3 < 4) {
            var5 = true;
         }

         var6 = false;
      }

      label108: {
         label107: {
            if (var4 == 1) {
               if (var9) {
                  if (var10) {
                     throw NotFoundException.getNotFoundInstance();
                  }
                  break label107;
               }

               if (!var10) {
                  throw NotFoundException.getNotFoundInstance();
               }
            } else {
               if (var4 == -1) {
                  if (var9) {
                     if (var10) {
                        throw NotFoundException.getNotFoundInstance();
                     }

                     var12 = var7;
                  } else {
                     if (!var10) {
                        throw NotFoundException.getNotFoundInstance();
                     }

                     var5 = true;
                  }
                  break label108;
               }

               if (var4 != 0) {
                  throw NotFoundException.getNotFoundInstance();
               }

               if (!var9) {
                  if (var10) {
                     throw NotFoundException.getNotFoundInstance();
                  }
                  break label108;
               }

               if (!var10) {
                  throw NotFoundException.getNotFoundInstance();
               }

               if (var2 >= var3) {
                  var5 = true;
                  break label107;
               }

               var12 = var8;
            }

            var6 = true;
            break label108;
         }

         var11 = true;
      }

      if (var12) {
         if (var11) {
            throw NotFoundException.getNotFoundInstance();
         }

         increment(this.getOddCounts(), this.getOddRoundingErrors());
      }

      if (var11) {
         decrement(this.getOddCounts(), this.getOddRoundingErrors());
      }

      if (var5) {
         if (var6) {
            throw NotFoundException.getNotFoundInstance();
         }

         increment(this.getEvenCounts(), this.getOddRoundingErrors());
      }

      if (var6) {
         decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
      }

   }

   private boolean checkChecksum() {
      List var1 = this.pairs;
      boolean var2 = false;
      ExpandedPair var3 = (ExpandedPair)var1.get(0);
      DataCharacter var9 = var3.getLeftChar();
      DataCharacter var10 = var3.getRightChar();
      if (var10 == null) {
         return false;
      } else {
         int var4 = var10.getChecksumPortion();
         int var5 = 2;

         for(int var6 = 1; var6 < this.pairs.size(); ++var6) {
            var3 = (ExpandedPair)this.pairs.get(var6);
            int var7 = var4 + var3.getLeftChar().getChecksumPortion();
            int var8 = var5 + 1;
            var10 = var3.getRightChar();
            var4 = var7;
            var5 = var8;
            if (var10 != null) {
               var4 = var7 + var10.getChecksumPortion();
               var5 = var8 + 1;
            }
         }

         if ((var5 - 4) * 211 + var4 % 211 == var9.getValue()) {
            var2 = true;
         }

         return var2;
      }
   }

   private List<ExpandedPair> checkRows(List<ExpandedRow> var1, int var2) throws NotFoundException {
      for(; var2 < this.rows.size(); ++var2) {
         ExpandedRow var3 = (ExpandedRow)this.rows.get(var2);
         this.pairs.clear();
         int var4 = var1.size();

         for(int var5 = 0; var5 < var4; ++var5) {
            this.pairs.addAll(((ExpandedRow)var1.get(var5)).getPairs());
         }

         this.pairs.addAll(var3.getPairs());
         if (isValidSequence(this.pairs)) {
            if (this.checkChecksum()) {
               return this.pairs;
            }

            ArrayList var6 = new ArrayList();
            var6.addAll(var1);
            var6.add(var3);

            try {
               List var8 = this.checkRows(var6, var2 + 1);
               return var8;
            } catch (NotFoundException var7) {
            }
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private List<ExpandedPair> checkRows(boolean var1) {
      int var2 = this.rows.size();
      List var3 = null;
      if (var2 > 25) {
         this.rows.clear();
         return null;
      } else {
         this.pairs.clear();
         if (var1) {
            Collections.reverse(this.rows);
         }

         label21: {
            List var6;
            try {
               ArrayList var4 = new ArrayList();
               var6 = this.checkRows(var4, 0);
            } catch (NotFoundException var5) {
               break label21;
            }

            var3 = var6;
         }

         if (var1) {
            Collections.reverse(this.rows);
         }

         return var3;
      }
   }

   static Result constructResult(List<ExpandedPair> var0) throws NotFoundException, FormatException {
      String var1 = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(var0)).parseInformation();
      ResultPoint[] var2 = ((ExpandedPair)var0.get(0)).getFinderPattern().getResultPoints();
      ResultPoint[] var3 = ((ExpandedPair)var0.get(var0.size() - 1)).getFinderPattern().getResultPoints();
      ResultPoint var6 = var2[0];
      ResultPoint var4 = var2[1];
      ResultPoint var7 = var3[0];
      ResultPoint var8 = var3[1];
      BarcodeFormat var5 = BarcodeFormat.RSS_EXPANDED;
      return new Result(var1, (byte[])null, new ResultPoint[]{var6, var4, var7, var8}, var5);
   }

   private void findNextPair(BitArray var1, List<ExpandedPair> var2, int var3) throws NotFoundException {
      int[] var4 = this.getDecodeFinderCounters();
      var4[0] = 0;
      var4[1] = 0;
      var4[2] = 0;
      var4[3] = 0;
      int var5 = var1.getSize();
      if (var3 < 0) {
         if (var2.isEmpty()) {
            var3 = 0;
         } else {
            var3 = ((ExpandedPair)var2.get(var2.size() - 1)).getFinderPattern().getStartEnd()[1];
         }
      }

      boolean var6;
      if (var2.size() % 2 != 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7 = var6;
      if (this.startFromEven) {
         var7 = var6 ^ true;
      }

      for(var6 = false; var3 < var5; ++var3) {
         var6 = var1.get(var3) ^ true;
         if (!var6) {
            break;
         }
      }

      boolean var8 = var6;
      int var12 = 0;
      int var10 = var3;

      for(var3 = var3; var10 < var5; ++var10) {
         if (var1.get(var10) ^ var8) {
            int var10002 = var4[var12]++;
         } else {
            if (var12 == 3) {
               if (var7) {
                  reverseCounters(var4);
               }

               if (isFinderPattern(var4)) {
                  int[] var11 = this.startEnd;
                  var11[0] = var3;
                  var11[1] = var10;
                  return;
               }

               if (var7) {
                  reverseCounters(var4);
               }

               var3 += var4[0] + var4[1];
               var4[0] = var4[2];
               var4[1] = var4[3];
               var4[2] = 0;
               var4[3] = 0;
               --var12;
            } else {
               ++var12;
            }

            var4[var12] = 1;
            var8 ^= true;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private static int getNextSecondBar(BitArray var0, int var1) {
      if (var0.get(var1)) {
         var1 = var0.getNextSet(var0.getNextUnset(var1));
      } else {
         var1 = var0.getNextUnset(var0.getNextSet(var1));
      }

      return var1;
   }

   private static boolean isNotA1left(FinderPattern var0, boolean var1, boolean var2) {
      if (var0.getValue() == 0 && var1 && var2) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static boolean isPartialRow(Iterable<ExpandedPair> var0, Iterable<ExpandedRow> var1) {
      Iterator var2 = var1.iterator();

      boolean var8;
      label36:
      do {
         boolean var3 = var2.hasNext();
         boolean var4 = false;
         if (!var3) {
            return false;
         }

         ExpandedRow var5 = (ExpandedRow)var2.next();
         Iterator var9 = var0.iterator();

         label33:
         do {
            if (!var9.hasNext()) {
               var8 = true;
               continue label36;
            }

            ExpandedPair var6 = (ExpandedPair)var9.next();
            Iterator var7 = var5.getPairs().iterator();

            do {
               if (!var7.hasNext()) {
                  var8 = false;
                  continue label33;
               }
            } while(!var6.equals((ExpandedPair)var7.next()));

            var8 = true;
         } while(var8);

         var8 = var4;
      } while(!var8);

      return true;
   }

   private static boolean isValidSequence(List<ExpandedPair> var0) {
      int[][] var1 = FINDER_PATTERN_SEQUENCES;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         int[] var4 = var1[var3];
         if (var0.size() <= var4.length) {
            int var5 = 0;

            boolean var6;
            while(true) {
               if (var5 >= var0.size()) {
                  var6 = true;
                  break;
               }

               if (((ExpandedPair)var0.get(var5)).getFinderPattern().getValue() != var4[var5]) {
                  var6 = false;
                  break;
               }

               ++var5;
            }

            if (var6) {
               return true;
            }
         }
      }

      return false;
   }

   private FinderPattern parseFoundFinderPattern(BitArray var1, int var2, boolean var3) {
      int var4;
      int var5;
      int var6;
      int[] var10;
      if (var3) {
         for(var4 = this.startEnd[0] - 1; var4 >= 0 && !var1.get(var4); --var4) {
         }

         ++var4;
         var10 = this.startEnd;
         var5 = var10[0] - var4;
         var6 = var10[1];
      } else {
         int[] var7 = this.startEnd;
         var4 = var7[0];
         var5 = var1.getNextUnset(var7[1] + 1);
         int var8 = this.startEnd[1];
         var6 = var5;
         var5 -= var8;
      }

      var10 = this.getDecodeFinderCounters();
      System.arraycopy(var10, 0, var10, 1, var10.length - 1);
      var10[0] = var5;

      try {
         var5 = parseFinderValue(var10, FINDER_PATTERNS);
      } catch (NotFoundException var9) {
         return null;
      }

      return new FinderPattern(var5, new int[]{var4, var6}, var4, var6, var2);
   }

   private static void removePartialRows(List<ExpandedPair> var0, List<ExpandedRow> var1) {
      Iterator var8 = var1.iterator();

      while(true) {
         ExpandedRow var2;
         do {
            if (!var8.hasNext()) {
               return;
            }

            var2 = (ExpandedRow)var8.next();
         } while(var2.getPairs().size() == var0.size());

         Iterator var3 = var2.getPairs().iterator();

         boolean var6;
         while(true) {
            boolean var4 = var3.hasNext();
            boolean var5 = false;
            var6 = true;
            if (!var4) {
               var6 = true;
               break;
            }

            ExpandedPair var9 = (ExpandedPair)var3.next();
            Iterator var7 = var0.iterator();

            do {
               if (!var7.hasNext()) {
                  var6 = false;
                  break;
               }
            } while(!var9.equals((ExpandedPair)var7.next()));

            if (!var6) {
               var6 = var5;
               break;
            }
         }

         if (var6) {
            var8.remove();
         }
      }
   }

   private static void reverseCounters(int[] var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1 / 2; ++var2) {
         int var3 = var0[var2];
         int var4 = var1 - var2 - 1;
         var0[var2] = var0[var4];
         var0[var4] = var3;
      }

   }

   private void storeRow(int var1, boolean var2) {
      boolean var3 = false;
      int var4 = 0;
      boolean var5 = false;

      boolean var6;
      while(true) {
         var6 = var3;
         if (var4 >= this.rows.size()) {
            break;
         }

         ExpandedRow var7 = (ExpandedRow)this.rows.get(var4);
         if (var7.getRowNumber() > var1) {
            var6 = var7.isEquivalent(this.pairs);
            break;
         }

         var5 = var7.isEquivalent(this.pairs);
         ++var4;
      }

      if (!var6 && !var5) {
         if (isPartialRow(this.pairs, this.rows)) {
            return;
         }

         this.rows.add(var4, new ExpandedRow(this.pairs, var1, var2));
         removePartialRows(this.pairs, this.rows);
      }

   }

   DataCharacter decodeDataCharacter(BitArray var1, FinderPattern var2, boolean var3, boolean var4) throws NotFoundException {
      int[] var5 = this.getDataCharacterCounters();
      var5[0] = 0;
      var5[1] = 0;
      var5[2] = 0;
      var5[3] = 0;
      var5[4] = 0;
      var5[5] = 0;
      var5[6] = 0;
      var5[7] = 0;
      int var6;
      int var7;
      int var8;
      if (var4 != 0) {
         recordPatternInReverse(var1, var2.getStartEnd()[0], var5);
      } else {
         recordPattern(var1, var2.getStartEnd()[1], var5);
         var6 = var5.length - 1;

         for(var7 = 0; var7 < var6; --var6) {
            var8 = var5[var7];
            var5[var7] = var5[var6];
            var5[var6] = var8;
            ++var7;
         }
      }

      float var9 = (float)count(var5) / (float)17;
      float var10 = (float)(var2.getStartEnd()[1] - var2.getStartEnd()[0]) / 15.0F;
      if (Math.abs(var9 - var10) / var10 <= 0.3F) {
         int[] var11 = this.getOddCounts();
         int[] var17 = this.getEvenCounts();
         float[] var12 = this.getOddRoundingErrors();
         float[] var13 = this.getEvenRoundingErrors();

         for(var7 = 0; var7 < var5.length; ++var7) {
            var10 = (float)var5[var7] * 1.0F / var9;
            var8 = (int)(0.5F + var10);
            if (var8 < 1) {
               if (var10 < 0.3F) {
                  throw NotFoundException.getNotFoundInstance();
               }

               var6 = 1;
            } else {
               var6 = var8;
               if (var8 > 8) {
                  if (var10 > 8.7F) {
                     throw NotFoundException.getNotFoundInstance();
                  }

                  var6 = 8;
               }
            }

            var8 = var7 / 2;
            if ((var7 & 1) == 0) {
               var11[var8] = var6;
               var12[var8] = var10 - (float)var6;
            } else {
               var17[var8] = var6;
               var13[var8] = var10 - (float)var6;
            }
         }

         this.adjustOddEvenCounts(17);
         var7 = var2.getValue();
         byte var18;
         if (var3) {
            var18 = 0;
         } else {
            var18 = 2;
         }

         int var14 = var7 * 4 + var18 + (var4 ^ 1) - 1;
         var8 = var11.length - 1;
         var6 = 0;

         int var15;
         for(var7 = 0; var8 >= 0; var6 = var15) {
            var15 = var6;
            if (isNotA1left(var2, var3, (boolean)var4)) {
               var15 = WEIGHTS[var14][var8 * 2];
               var15 = var6 + var11[var8] * var15;
            }

            var7 += var11[var8];
            --var8;
         }

         var15 = var17.length - 1;

         int var16;
         for(var8 = 0; var15 >= 0; var8 = var16) {
            var16 = var8;
            if (isNotA1left(var2, var3, (boolean)var4)) {
               var16 = WEIGHTS[var14][var15 * 2 + 1];
               var16 = var8 + var17[var15] * var16;
            }

            --var15;
         }

         if ((var7 & 1) == 0 && var7 <= 13 && var7 >= 4) {
            var7 = (13 - var7) / 2;
            var16 = SYMBOL_WIDEST[var7];
            var15 = RSSUtils.getRSSvalue(var11, var16, true);
            var16 = RSSUtils.getRSSvalue(var17, 9 - var16, false);
            return new DataCharacter(var15 * EVEN_TOTAL_SUBSET[var7] + var16 + GSUM[var7], var6 + var8);
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, FormatException {
      this.pairs.clear();
      this.startFromEven = false;

      try {
         Result var5 = constructResult(this.decodeRow2pairs(var1, var2));
         return var5;
      } catch (NotFoundException var4) {
         this.pairs.clear();
         this.startFromEven = true;
         return constructResult(this.decodeRow2pairs(var1, var2));
      }
   }

   List<ExpandedPair> decodeRow2pairs(int var1, BitArray var2) throws NotFoundException {
      while(true) {
         try {
            ExpandedPair var3 = this.retrieveNextPair(var2, this.pairs, var1);
            this.pairs.add(var3);
         } catch (NotFoundException var5) {
            if (!this.pairs.isEmpty()) {
               if (this.checkChecksum()) {
                  return this.pairs;
               }

               boolean var4 = this.rows.isEmpty();
               this.storeRow(var1, false);
               if (var4 ^ true) {
                  List var6 = this.checkRows(false);
                  if (var6 != null) {
                     return var6;
                  }

                  var6 = this.checkRows(true);
                  if (var6 != null) {
                     return var6;
                  }
               }

               throw NotFoundException.getNotFoundInstance();
            }

            throw var5;
         }
      }
   }

   List<ExpandedRow> getRows() {
      return this.rows;
   }

   public void reset() {
      this.pairs.clear();
      this.rows.clear();
   }

   ExpandedPair retrieveNextPair(BitArray var1, List<ExpandedPair> var2, int var3) throws NotFoundException {
      boolean var4;
      if (var2.size() % 2 == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5 = var4;
      if (this.startFromEven) {
         var5 = var4 ^ true;
      }

      int var6 = -1;
      boolean var7 = true;

      FinderPattern var8;
      boolean var9;
      do {
         this.findNextPair(var1, var2, var6);
         var8 = this.parseFoundFinderPattern(var1, var3, var5);
         if (var8 == null) {
            var6 = getNextSecondBar(var1, this.startEnd[0]);
            var9 = var7;
         } else {
            var9 = false;
         }

         var7 = var9;
      } while(var9);

      DataCharacter var10 = this.decodeDataCharacter(var1, var8, var5, true);
      if (!var2.isEmpty() && ((ExpandedPair)var2.get(var2.size() - 1)).mustBeLast()) {
         throw NotFoundException.getNotFoundInstance();
      } else {
         DataCharacter var12;
         try {
            var12 = this.decodeDataCharacter(var1, var8, var5, false);
         } catch (NotFoundException var11) {
            var12 = null;
         }

         return new ExpandedPair(var10, var12, var8, true);
      }
   }
}
