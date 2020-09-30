package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSS14Reader extends AbstractRSSReader {
   private static final int[][] FINDER_PATTERNS;
   private static final int[] INSIDE_GSUM = new int[]{0, 336, 1036, 1516};
   private static final int[] INSIDE_ODD_TOTAL_SUBSET = new int[]{4, 20, 48, 81};
   private static final int[] INSIDE_ODD_WIDEST = new int[]{2, 4, 6, 8};
   private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = new int[]{1, 10, 34, 70, 126};
   private static final int[] OUTSIDE_GSUM = new int[]{0, 161, 961, 2015, 2715};
   private static final int[] OUTSIDE_ODD_WIDEST = new int[]{8, 6, 4, 3, 1};
   private final List<Pair> possibleLeftPairs = new ArrayList();
   private final List<Pair> possibleRightPairs = new ArrayList();

   static {
      int[] var0 = new int[]{3, 8, 2, 1};
      int[] var1 = new int[]{3, 1, 9, 1};
      int[] var2 = new int[]{2, 5, 6, 1};
      int[] var3 = new int[]{2, 3, 8, 1};
      int[] var4 = new int[]{1, 3, 9, 1};
      FINDER_PATTERNS = new int[][]{var0, {3, 5, 5, 1}, {3, 3, 7, 1}, var1, {2, 7, 4, 1}, var2, var3, {1, 5, 7, 1}, var4};
   }

   private static void addOrTally(Collection<Pair> var0, Pair var1) {
      if (var1 != null) {
         boolean var2 = false;
         Iterator var3 = var0.iterator();

         boolean var4;
         while(true) {
            var4 = var2;
            if (!var3.hasNext()) {
               break;
            }

            Pair var5 = (Pair)var3.next();
            if (var5.getValue() == var1.getValue()) {
               var5.incrementCount();
               var4 = true;
               break;
            }
         }

         if (!var4) {
            var0.add(var1);
         }

      }
   }

   private void adjustOddEvenCounts(boolean var1, int var2) throws NotFoundException {
      int var3 = count(this.getOddCounts());
      int var4 = count(this.getEvenCounts());
      int var5 = var3 + var4 - var2;
      boolean var6 = false;
      boolean var7 = true;
      boolean var8 = true;
      boolean var9;
      if ((var3 & 1) == var1) {
         var9 = true;
      } else {
         var9 = false;
      }

      boolean var10;
      if ((var4 & 1) == 1) {
         var10 = true;
      } else {
         var10 = false;
      }

      boolean var11;
      boolean var12;
      boolean var13;
      boolean var15;
      label134: {
         boolean var14;
         label133: {
            label132: {
               if (var1 != 0) {
                  if (var3 > 12) {
                     var11 = false;
                     var15 = true;
                  } else {
                     if (var3 < 4) {
                        var11 = true;
                     } else {
                        var11 = false;
                     }

                     var15 = false;
                  }

                  if (var4 > 12) {
                     var12 = var11;
                     var11 = var15;
                     break label133;
                  }

                  var13 = var11;
                  var14 = var15;
                  if (var4 < 4) {
                     var12 = var11;
                     var11 = var15;
                     break label132;
                  }
               } else {
                  if (var3 > 11) {
                     var15 = false;
                     var11 = true;
                  } else {
                     if (var3 < 5) {
                        var15 = true;
                     } else {
                        var15 = false;
                     }

                     var11 = false;
                  }

                  if (var4 > 10) {
                     var12 = var15;
                     break label133;
                  }

                  var13 = var15;
                  var14 = var11;
                  if (var4 < 4) {
                     var12 = var15;
                     break label132;
                  }
               }

               var12 = false;
               var11 = var14;
               var15 = var13;
               var13 = var6;
               break label134;
            }

            var14 = false;
            var13 = true;
            var15 = var12;
            var12 = var14;
            break label134;
         }

         var14 = true;
         var13 = var6;
         var15 = var12;
         var12 = var14;
      }

      label118: {
         label117: {
            if (var5 == 1) {
               if (var9) {
                  if (var10) {
                     throw NotFoundException.getNotFoundInstance();
                  }
                  break label117;
               }

               if (!var10) {
                  throw NotFoundException.getNotFoundInstance();
               }
            } else {
               if (var5 == -1) {
                  if (var9) {
                     if (var10) {
                        throw NotFoundException.getNotFoundInstance();
                     }

                     var15 = var7;
                  } else {
                     if (!var10) {
                        throw NotFoundException.getNotFoundInstance();
                     }

                     var13 = true;
                  }
                  break label118;
               }

               if (var5 != 0) {
                  throw NotFoundException.getNotFoundInstance();
               }

               if (!var9) {
                  if (var10) {
                     throw NotFoundException.getNotFoundInstance();
                  }
                  break label118;
               }

               if (!var10) {
                  throw NotFoundException.getNotFoundInstance();
               }

               if (var3 >= var4) {
                  var13 = true;
                  break label117;
               }

               var15 = var8;
            }

            var12 = true;
            break label118;
         }

         var11 = true;
      }

      if (var15) {
         if (var11) {
            throw NotFoundException.getNotFoundInstance();
         }

         increment(this.getOddCounts(), this.getOddRoundingErrors());
      }

      if (var11) {
         decrement(this.getOddCounts(), this.getOddRoundingErrors());
      }

      if (var13) {
         if (var12) {
            throw NotFoundException.getNotFoundInstance();
         }

         increment(this.getEvenCounts(), this.getOddRoundingErrors());
      }

      if (var12) {
         decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
      }

   }

   private static boolean checkChecksum(Pair var0, Pair var1) {
      int var2 = var0.getChecksumPortion();
      int var3 = var1.getChecksumPortion();
      int var4 = var0.getFinderPattern().getValue() * 9 + var1.getFinderPattern().getValue();
      int var5 = var4;
      if (var4 > 72) {
         var5 = var4 - 1;
      }

      var4 = var5;
      if (var5 > 8) {
         var4 = var5 - 1;
      }

      boolean var6;
      if ((var2 + var3 * 16) % 79 == var4) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   private static Result constructResult(Pair var0, Pair var1) {
      String var2 = String.valueOf((long)var0.getValue() * 4537077L + (long)var1.getValue());
      StringBuilder var3 = new StringBuilder(14);

      int var4;
      for(var4 = 13 - var2.length(); var4 > 0; --var4) {
         var3.append('0');
      }

      var3.append(var2);
      var4 = 0;

      int var5;
      for(var5 = 0; var4 < 13; ++var4) {
         int var6 = var3.charAt(var4) - 48;
         int var7 = var6;
         if ((var4 & 1) == 0) {
            var7 = var6 * 3;
         }

         var5 += var7;
      }

      var5 = 10 - var5 % 10;
      var4 = var5;
      if (var5 == 10) {
         var4 = 0;
      }

      var3.append(var4);
      ResultPoint[] var8 = var0.getFinderPattern().getResultPoints();
      ResultPoint[] var12 = var1.getFinderPattern().getResultPoints();
      String var11 = String.valueOf(var3.toString());
      ResultPoint var10 = var8[0];
      ResultPoint var15 = var8[1];
      ResultPoint var14 = var12[0];
      ResultPoint var13 = var12[1];
      BarcodeFormat var9 = BarcodeFormat.RSS_14;
      return new Result(var11, (byte[])null, new ResultPoint[]{var10, var15, var14, var13}, var9);
   }

   private DataCharacter decodeDataCharacter(BitArray var1, FinderPattern var2, boolean var3) throws NotFoundException {
      int[] var4 = this.getDataCharacterCounters();
      var4[0] = 0;
      var4[1] = 0;
      var4[2] = 0;
      var4[3] = 0;
      var4[4] = 0;
      var4[5] = 0;
      var4[6] = 0;
      var4[7] = 0;
      int var5;
      int var6;
      int var7;
      if (var3) {
         recordPatternInReverse(var1, var2.getStartEnd()[0], var4);
      } else {
         recordPattern(var1, var2.getStartEnd()[1] + 1, var4);
         var5 = var4.length - 1;

         for(var6 = 0; var6 < var5; --var5) {
            var7 = var4[var6];
            var4[var6] = var4[var5];
            var4[var5] = var7;
            ++var6;
         }
      }

      byte var16;
      if (var3) {
         var16 = 16;
      } else {
         var16 = 15;
      }

      float var8 = (float)count(var4) / (float)var16;
      int[] var14 = this.getOddCounts();
      int[] var15 = this.getEvenCounts();
      float[] var9 = this.getOddRoundingErrors();
      float[] var10 = this.getEvenRoundingErrors();

      int var12;
      for(var7 = 0; var7 < var4.length; ++var7) {
         float var11 = (float)var4[var7] / var8;
         var12 = (int)(0.5F + var11);
         if (var12 < 1) {
            var6 = 1;
         } else {
            var6 = var12;
            if (var12 > 8) {
               var6 = 8;
            }
         }

         var12 = var7 / 2;
         if ((var7 & 1) == 0) {
            var14[var12] = var6;
            var9[var12] = var11 - (float)var6;
         } else {
            var15[var12] = var6;
            var10[var12] = var11 - (float)var6;
         }
      }

      this.adjustOddEvenCounts(var3, var16);
      var7 = var14.length - 1;
      var5 = 0;

      for(var6 = 0; var7 >= 0; --var7) {
         var5 = var5 * 9 + var14[var7];
         var6 += var14[var7];
      }

      var12 = var15.length - 1;
      int var13 = 0;

      for(var7 = 0; var12 >= 0; --var12) {
         var13 = var13 * 9 + var15[var12];
         var7 += var15[var12];
      }

      var5 += var13 * 3;
      if (var3) {
         if ((var6 & 1) == 0 && var6 <= 12 && var6 >= 4) {
            var7 = (12 - var6) / 2;
            var12 = OUTSIDE_ODD_WIDEST[var7];
            var6 = RSSUtils.getRSSvalue(var14, var12, false);
            var12 = RSSUtils.getRSSvalue(var15, 9 - var12, true);
            return new DataCharacter(var6 * OUTSIDE_EVEN_TOTAL_SUBSET[var7] + var12 + OUTSIDE_GSUM[var7], var5);
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else if ((var7 & 1) == 0 && var7 <= 10 && var7 >= 4) {
         var6 = (10 - var7) / 2;
         var12 = INSIDE_ODD_WIDEST[var6];
         var7 = RSSUtils.getRSSvalue(var14, var12, true);
         return new DataCharacter(RSSUtils.getRSSvalue(var15, 9 - var12, false) * INSIDE_ODD_TOTAL_SUBSET[var6] + var7 + INSIDE_GSUM[var6], var5);
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private Pair decodePair(BitArray var1, boolean var2, int var3, Map<DecodeHintType, ?> var4) {
      int[] var5;
      FinderPattern var6;
      boolean var10001;
      try {
         var5 = this.findFinderPattern(var1, 0, var2);
         var6 = this.parseFoundFinderPattern(var1, var3, var2, var5);
      } catch (NotFoundException var13) {
         var10001 = false;
         return null;
      }

      ResultPointCallback var16;
      if (var4 == null) {
         var16 = null;
      } else {
         try {
            var16 = (ResultPointCallback)var4.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
         } catch (NotFoundException var12) {
            var10001 = false;
            return null;
         }
      }

      if (var16 != null) {
         float var7 = (float)(var5[0] + var5[1]) / 2.0F;
         float var8 = var7;
         if (var2) {
            try {
               var8 = (float)(var1.getSize() - 1) - var7;
            } catch (NotFoundException var11) {
               var10001 = false;
               return null;
            }
         }

         try {
            ResultPoint var18 = new ResultPoint(var8, (float)var3);
            var16.foundPossibleResultPoint(var18);
         } catch (NotFoundException var10) {
            var10001 = false;
            return null;
         }
      }

      try {
         DataCharacter var17 = this.decodeDataCharacter(var1, var6, true);
         DataCharacter var14 = this.decodeDataCharacter(var1, var6, false);
         Pair var15 = new Pair(var17.getValue() * 1597 + var14.getValue(), var17.getChecksumPortion() + var14.getChecksumPortion() * 4, var6);
         return var15;
      } catch (NotFoundException var9) {
         var10001 = false;
         return null;
      }
   }

   private int[] findFinderPattern(BitArray var1, int var2, boolean var3) throws NotFoundException {
      int[] var4 = this.getDecodeFinderCounters();
      var4[0] = 0;
      var4[1] = 0;
      var4[2] = 0;
      var4[3] = 0;
      int var5 = var1.getSize();

      boolean var6;
      for(var6 = false; var2 < var5; ++var2) {
         var6 = var1.get(var2) ^ true;
         if (var3 == var6) {
            break;
         }
      }

      int var7 = var2;
      byte var8 = 0;
      int var9 = var2;

      for(var2 = var8; var9 < var5; ++var9) {
         if (var1.get(var9) ^ var6) {
            int var10002 = var4[var2]++;
         } else {
            if (var2 == 3) {
               if (isFinderPattern(var4)) {
                  return new int[]{var7, var9};
               }

               var7 += var4[0] + var4[1];
               var4[0] = var4[2];
               var4[1] = var4[3];
               var4[2] = 0;
               var4[3] = 0;
               --var2;
            } else {
               ++var2;
            }

            var4[var2] = 1;
            var6 ^= true;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private FinderPattern parseFoundFinderPattern(BitArray var1, int var2, boolean var3, int[] var4) throws NotFoundException {
      boolean var5 = var1.get(var4[0]);

      int var6;
      for(var6 = var4[0] - 1; var6 >= 0 && var1.get(var6) ^ var5; --var6) {
      }

      int var7 = var6 + 1;
      var6 = var4[0];
      int[] var8 = this.getDecodeFinderCounters();
      System.arraycopy(var8, 0, var8, 1, var8.length - 1);
      var8[0] = var6 - var7;
      int var9 = parseFinderValue(var8, FINDER_PATTERNS);
      var6 = var4[1];
      int var10;
      if (var3) {
         var10 = var1.getSize();
         var6 = var1.getSize() - 1 - var6;
         var10 = var10 - 1 - var7;
      } else {
         var10 = var7;
      }

      return new FinderPattern(var9, new int[]{var7, var4[1]}, var10, var6, var2);
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException {
      Pair var4 = this.decodePair(var2, false, var1, var3);
      addOrTally(this.possibleLeftPairs, var4);
      var2.reverse();
      Pair var9 = this.decodePair(var2, true, var1, var3);
      addOrTally(this.possibleRightPairs, var9);
      var2.reverse();
      int var5 = this.possibleLeftPairs.size();

      for(var1 = 0; var1 < var5; ++var1) {
         var9 = (Pair)this.possibleLeftPairs.get(var1);
         if (var9.getCount() > 1) {
            int var6 = this.possibleRightPairs.size();

            for(int var7 = 0; var7 < var6; ++var7) {
               Pair var8 = (Pair)this.possibleRightPairs.get(var7);
               if (var8.getCount() > 1 && checkChecksum(var9, var8)) {
                  return constructResult(var9, var8);
               }
            }
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   public void reset() {
      this.possibleLeftPairs.clear();
      this.possibleRightPairs.clear();
   }
}
