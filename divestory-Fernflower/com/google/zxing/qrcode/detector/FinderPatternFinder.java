package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
   private static final int CENTER_QUORUM = 2;
   protected static final int MAX_MODULES = 57;
   protected static final int MIN_SKIP = 3;
   private final int[] crossCheckStateCount;
   private boolean hasSkipped;
   private final BitMatrix image;
   private final List<FinderPattern> possibleCenters;
   private final ResultPointCallback resultPointCallback;

   public FinderPatternFinder(BitMatrix var1) {
      this(var1, (ResultPointCallback)null);
   }

   public FinderPatternFinder(BitMatrix var1, ResultPointCallback var2) {
      this.image = var1;
      this.possibleCenters = new ArrayList();
      this.crossCheckStateCount = new int[5];
      this.resultPointCallback = var2;
   }

   private static float centerFromEnd(int[] var0, int var1) {
      return (float)(var1 - var0[4] - var0[3]) - (float)var0[2] / 2.0F;
   }

   private boolean crossCheckDiagonal(int var1, int var2, int var3, int var4) {
      int[] var5 = this.getCrossCheckStateCount();
      boolean var6 = false;

      int var7;
      int var10002;
      for(var7 = 0; var1 >= var7 && var2 >= var7 && this.image.get(var2 - var7, var1 - var7); ++var7) {
         var10002 = var5[2]++;
      }

      boolean var8 = var6;
      if (var1 >= var7) {
         int var9 = var7;
         if (var2 < var7) {
            var8 = var6;
         } else {
            while(var1 >= var9 && var2 >= var9 && !this.image.get(var2 - var9, var1 - var9) && var5[1] <= var3) {
               var10002 = var5[1]++;
               ++var9;
            }

            var8 = var6;
            if (var1 >= var9) {
               var8 = var6;
               if (var2 >= var9) {
                  if (var5[1] > var3) {
                     var8 = var6;
                  } else {
                     while(var1 >= var9 && var2 >= var9 && this.image.get(var2 - var9, var1 - var9) && var5[0] <= var3) {
                        var10002 = var5[0]++;
                        ++var9;
                     }

                     if (var5[0] > var3) {
                        return false;
                     }

                     int var10 = this.image.getHeight();
                     int var11 = this.image.getWidth();
                     var9 = 1;

                     int var12;
                     while(true) {
                        var7 = var1 + var9;
                        if (var7 >= var10) {
                           break;
                        }

                        var12 = var2 + var9;
                        if (var12 >= var11 || !this.image.get(var12, var7)) {
                           break;
                        }

                        var10002 = var5[2]++;
                        ++var9;
                     }

                     var8 = var6;
                     if (var7 < var10) {
                        var7 = var9;
                        if (var2 + var9 >= var11) {
                           var8 = var6;
                        } else {
                           while(true) {
                              var12 = var1 + var7;
                              if (var12 >= var10) {
                                 break;
                              }

                              var9 = var2 + var7;
                              if (var9 >= var11 || this.image.get(var9, var12) || var5[3] >= var3) {
                                 break;
                              }

                              var10002 = var5[3]++;
                              ++var7;
                           }

                           var8 = var6;
                           if (var12 < var10) {
                              var8 = var6;
                              if (var2 + var7 < var11) {
                                 if (var5[3] >= var3) {
                                    var8 = var6;
                                 } else {
                                    while(true) {
                                       var9 = var1 + var7;
                                       if (var9 >= var10) {
                                          break;
                                       }

                                       var12 = var2 + var7;
                                       if (var12 >= var11 || !this.image.get(var12, var9) || var5[4] >= var3) {
                                          break;
                                       }

                                       var10002 = var5[4]++;
                                       ++var7;
                                    }

                                    if (var5[4] >= var3) {
                                       return false;
                                    }

                                    var8 = var6;
                                    if (Math.abs(var5[0] + var5[1] + var5[2] + var5[3] + var5[4] - var4) < var4 * 2) {
                                       var8 = var6;
                                       if (foundPatternCross(var5)) {
                                          var8 = true;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var8;
   }

   private float crossCheckHorizontal(int var1, int var2, int var3, int var4) {
      BitMatrix var5 = this.image;
      int var6 = var5.getWidth();
      int[] var7 = this.getCrossCheckStateCount();

      int var8;
      int var10002;
      for(var8 = var1; var8 >= 0 && var5.get(var8, var2); --var8) {
         var10002 = var7[2]++;
      }

      float var9 = Float.NaN;
      int var10 = var8;
      if (var8 < 0) {
         return Float.NaN;
      } else {
         while(var10 >= 0 && !var5.get(var10, var2) && var7[1] <= var3) {
            var10002 = var7[1]++;
            --var10;
         }

         float var11 = var9;
         if (var10 >= 0) {
            if (var7[1] > var3) {
               var11 = var9;
            } else {
               while(var10 >= 0 && var5.get(var10, var2) && var7[0] <= var3) {
                  var10002 = var7[0]++;
                  --var10;
               }

               if (var7[0] > var3) {
                  return Float.NaN;
               }

               ++var1;

               while(var1 < var6 && var5.get(var1, var2)) {
                  var10002 = var7[2]++;
                  ++var1;
               }

               var10 = var1;
               if (var1 == var6) {
                  return Float.NaN;
               }

               while(var10 < var6 && !var5.get(var10, var2) && var7[3] < var3) {
                  var10002 = var7[3]++;
                  ++var10;
               }

               var11 = var9;
               if (var10 != var6) {
                  if (var7[3] >= var3) {
                     var11 = var9;
                  } else {
                     while(var10 < var6 && var5.get(var10, var2) && var7[4] < var3) {
                        var10002 = var7[4]++;
                        ++var10;
                     }

                     if (var7[4] >= var3) {
                        return Float.NaN;
                     }

                     if (Math.abs(var7[0] + var7[1] + var7[2] + var7[3] + var7[4] - var4) * 5 >= var4) {
                        return Float.NaN;
                     }

                     var11 = var9;
                     if (foundPatternCross(var7)) {
                        var11 = centerFromEnd(var7, var10);
                     }
                  }
               }
            }
         }

         return var11;
      }
   }

   private float crossCheckVertical(int var1, int var2, int var3, int var4) {
      BitMatrix var5 = this.image;
      int var6 = var5.getHeight();
      int[] var7 = this.getCrossCheckStateCount();

      int var8;
      int var10002;
      for(var8 = var1; var8 >= 0 && var5.get(var2, var8); --var8) {
         var10002 = var7[2]++;
      }

      float var9 = Float.NaN;
      int var10 = var8;
      if (var8 < 0) {
         return Float.NaN;
      } else {
         while(var10 >= 0 && !var5.get(var2, var10) && var7[1] <= var3) {
            var10002 = var7[1]++;
            --var10;
         }

         float var11 = var9;
         if (var10 >= 0) {
            if (var7[1] > var3) {
               var11 = var9;
            } else {
               while(var10 >= 0 && var5.get(var2, var10) && var7[0] <= var3) {
                  var10002 = var7[0]++;
                  --var10;
               }

               if (var7[0] > var3) {
                  return Float.NaN;
               }

               for(var8 = var1 + 1; var8 < var6 && var5.get(var2, var8); ++var8) {
                  var10002 = var7[2]++;
               }

               var1 = var8;
               if (var8 == var6) {
                  return Float.NaN;
               }

               while(var1 < var6 && !var5.get(var2, var1) && var7[3] < var3) {
                  var10002 = var7[3]++;
                  ++var1;
               }

               var11 = var9;
               if (var1 != var6) {
                  if (var7[3] >= var3) {
                     var11 = var9;
                  } else {
                     while(var1 < var6 && var5.get(var2, var1) && var7[4] < var3) {
                        var10002 = var7[4]++;
                        ++var1;
                     }

                     if (var7[4] >= var3) {
                        return Float.NaN;
                     }

                     if (Math.abs(var7[0] + var7[1] + var7[2] + var7[3] + var7[4] - var4) * 5 >= var4 * 2) {
                        return Float.NaN;
                     }

                     var11 = var9;
                     if (foundPatternCross(var7)) {
                        var11 = centerFromEnd(var7, var1);
                     }
                  }
               }
            }
         }

         return var11;
      }
   }

   private int findRowSkip() {
      if (this.possibleCenters.size() <= 1) {
         return 0;
      } else {
         FinderPattern var1 = null;
         Iterator var2 = this.possibleCenters.iterator();

         while(var2.hasNext()) {
            FinderPattern var3 = (FinderPattern)var2.next();
            if (var3.getCount() >= 2) {
               if (var1 != null) {
                  this.hasSkipped = true;
                  return (int)(Math.abs(var1.getX() - var3.getX()) - Math.abs(var1.getY() - var3.getY())) / 2;
               }

               var1 = var3;
            }
         }

         return 0;
      }
   }

   protected static boolean foundPatternCross(int[] var0) {
      boolean var1 = false;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < 5; ++var2) {
         int var4 = var0[var2];
         if (var4 == 0) {
            return false;
         }

         var3 += var4;
      }

      if (var3 < 7) {
         return false;
      } else {
         float var5 = (float)var3 / 7.0F;
         float var6 = var5 / 2.0F;
         boolean var7 = var1;
         if (Math.abs(var5 - (float)var0[0]) < var6) {
            var7 = var1;
            if (Math.abs(var5 - (float)var0[1]) < var6) {
               var7 = var1;
               if (Math.abs(var5 * 3.0F - (float)var0[2]) < 3.0F * var6) {
                  var7 = var1;
                  if (Math.abs(var5 - (float)var0[3]) < var6) {
                     var7 = var1;
                     if (Math.abs(var5 - (float)var0[4]) < var6) {
                        var7 = true;
                     }
                  }
               }
            }
         }

         return var7;
      }
   }

   private int[] getCrossCheckStateCount() {
      int[] var1 = this.crossCheckStateCount;
      var1[0] = 0;
      var1[1] = 0;
      var1[2] = 0;
      var1[3] = 0;
      var1[4] = 0;
      return var1;
   }

   private boolean haveMultiplyConfirmedCenters() {
      int var1 = this.possibleCenters.size();
      Iterator var2 = this.possibleCenters.iterator();
      float var3 = 0.0F;
      boolean var4 = false;
      int var5 = 0;
      float var6 = 0.0F;

      while(var2.hasNext()) {
         FinderPattern var7 = (FinderPattern)var2.next();
         if (var7.getCount() >= 2) {
            ++var5;
            var6 += var7.getEstimatedModuleSize();
         }
      }

      if (var5 < 3) {
         return false;
      } else {
         float var8 = var6 / (float)var1;

         for(var2 = this.possibleCenters.iterator(); var2.hasNext(); var3 += Math.abs(((FinderPattern)var2.next()).getEstimatedModuleSize() - var8)) {
         }

         if (var3 <= var6 * 0.05F) {
            var4 = true;
         }

         return var4;
      }
   }

   private FinderPattern[] selectBestPatterns() throws NotFoundException {
      int var1 = this.possibleCenters.size();
      if (var1 < 3) {
         throw NotFoundException.getNotFoundInstance();
      } else {
         float var2 = 0.0F;
         Iterator var3;
         float var5;
         if (var1 > 3) {
            var3 = this.possibleCenters.iterator();
            float var4 = 0.0F;

            float var6;
            for(var5 = 0.0F; var3.hasNext(); var5 += var6 * var6) {
               var6 = ((FinderPattern)var3.next()).getEstimatedModuleSize();
               var4 += var6;
            }

            var6 = (float)var1;
            var4 /= var6;
            var5 = (float)Math.sqrt((double)(var5 / var6 - var4 * var4));
            Collections.sort(this.possibleCenters, new FinderPatternFinder.FurthestFromAverageComparator(var4));
            var5 = Math.max(0.2F * var4, var5);

            int var7;
            for(var1 = 0; var1 < this.possibleCenters.size() && this.possibleCenters.size() > 3; var1 = var7 + 1) {
               var7 = var1;
               if (Math.abs(((FinderPattern)this.possibleCenters.get(var1)).getEstimatedModuleSize() - var4) > var5) {
                  this.possibleCenters.remove(var1);
                  var7 = var1 - 1;
               }
            }
         }

         if (this.possibleCenters.size() > 3) {
            var3 = this.possibleCenters.iterator();

            for(var5 = var2; var3.hasNext(); var5 += ((FinderPattern)var3.next()).getEstimatedModuleSize()) {
            }

            var5 /= (float)this.possibleCenters.size();
            Collections.sort(this.possibleCenters, new FinderPatternFinder.CenterComparator(var5));
            List var8 = this.possibleCenters;
            var8.subList(3, var8.size()).clear();
         }

         return new FinderPattern[]{(FinderPattern)this.possibleCenters.get(0), (FinderPattern)this.possibleCenters.get(1), (FinderPattern)this.possibleCenters.get(2)};
      }
   }

   final FinderPatternInfo find(Map<DecodeHintType, ?> var1) throws NotFoundException {
      boolean var2;
      if (var1 != null && var1.containsKey(DecodeHintType.TRY_HARDER)) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var3;
      if (var1 != null && var1.containsKey(DecodeHintType.PURE_BARCODE)) {
         var3 = true;
      } else {
         var3 = false;
      }

      int var4 = this.image.getHeight();
      int var5 = this.image.getWidth();
      int var6 = var4 * 3 / 228;
      if (var6 < 3 || var2) {
         var6 = 3;
      }

      int[] var12 = new int[5];
      int var7 = var6 - 1;

      boolean var11;
      for(boolean var8 = false; var7 < var4 && !var8; var8 = var11) {
         var12[0] = 0;
         var12[1] = 0;
         var12[2] = 0;
         var12[3] = 0;
         var12[4] = 0;
         int var9 = 0;

         int var14;
         for(var14 = 0; var9 < var5; ++var9) {
            int var10002;
            if (this.image.get(var9, var7)) {
               int var15 = var14;
               if ((var14 & 1) == 1) {
                  var15 = var14 + 1;
               }

               var10002 = var12[var15]++;
               var14 = var15;
            } else if ((var14 & 1) == 0) {
               if (var14 == 4) {
                  if (foundPatternCross(var12)) {
                     if (this.handlePossibleCenter(var12, var7, var9, var3)) {
                        if (this.hasSkipped) {
                           var11 = this.haveMultiplyConfirmedCenters();
                           var14 = var7;
                        } else {
                           var6 = this.findRowSkip();
                           var14 = var7;
                           var11 = var8;
                           if (var6 > var12[2]) {
                              var14 = var7 + (var6 - var12[2] - 2);
                              var9 = var5 - 1;
                              var11 = var8;
                           }
                        }

                        var12[0] = 0;
                        var12[1] = 0;
                        var12[2] = 0;
                        var12[3] = 0;
                        var12[4] = 0;
                        var6 = 2;
                        byte var10 = 0;
                        var7 = var14;
                        var8 = var11;
                        var14 = var10;
                        continue;
                     }

                     var12[0] = var12[2];
                     var12[1] = var12[3];
                     var12[2] = var12[4];
                     var12[3] = 1;
                     var12[4] = 0;
                  } else {
                     var12[0] = var12[2];
                     var12[1] = var12[3];
                     var12[2] = var12[4];
                     var12[3] = 1;
                     var12[4] = 0;
                  }

                  var14 = 3;
               } else {
                  ++var14;
                  var10002 = var12[var14]++;
               }
            } else {
               var10002 = var12[var14]++;
            }
         }

         var14 = var6;
         var11 = var8;
         if (foundPatternCross(var12)) {
            var14 = var6;
            var11 = var8;
            if (this.handlePossibleCenter(var12, var7, var5, var3)) {
               var9 = var12[0];
               var14 = var9;
               var11 = var8;
               if (this.hasSkipped) {
                  var11 = this.haveMultiplyConfirmedCenters();
                  var14 = var9;
               }
            }
         }

         var7 += var14;
         var6 = var14;
      }

      FinderPattern[] var13 = this.selectBestPatterns();
      ResultPoint.orderBestPatterns(var13);
      return new FinderPatternInfo(var13);
   }

   protected final BitMatrix getImage() {
      return this.image;
   }

   protected final List<FinderPattern> getPossibleCenters() {
      return this.possibleCenters;
   }

   protected final boolean handlePossibleCenter(int[] var1, int var2, int var3, boolean var4) {
      boolean var5 = false;
      int var6 = var1[0] + var1[1] + var1[2] + var1[3] + var1[4];
      var3 = (int)centerFromEnd(var1, var3);
      float var7 = this.crossCheckVertical(var2, var3, var1[2], var6);
      if (!Float.isNaN(var7)) {
         var2 = (int)var7;
         float var8 = this.crossCheckHorizontal(var3, var2, var1[2], var6);
         if (!Float.isNaN(var8) && (!var4 || this.crossCheckDiagonal(var2, (int)var8, var1[2], var6))) {
            float var9 = (float)var6 / 7.0F;
            var3 = 0;

            boolean var13;
            while(true) {
               var13 = var5;
               if (var3 >= this.possibleCenters.size()) {
                  break;
               }

               FinderPattern var11 = (FinderPattern)this.possibleCenters.get(var3);
               if (var11.aboutEquals(var9, var7, var8)) {
                  this.possibleCenters.set(var3, var11.combineEstimate(var7, var8, var9));
                  var13 = true;
                  break;
               }

               ++var3;
            }

            if (!var13) {
               FinderPattern var10 = new FinderPattern(var8, var7, var9);
               this.possibleCenters.add(var10);
               ResultPointCallback var12 = this.resultPointCallback;
               if (var12 != null) {
                  var12.foundPossibleResultPoint(var10);
               }
            }

            return true;
         }
      }

      return false;
   }

   private static final class CenterComparator implements Comparator<FinderPattern>, Serializable {
      private final float average;

      private CenterComparator(float var1) {
         this.average = var1;
      }

      // $FF: synthetic method
      CenterComparator(float var1, Object var2) {
         this(var1);
      }

      public int compare(FinderPattern var1, FinderPattern var2) {
         if (var2.getCount() == var1.getCount()) {
            float var3 = Math.abs(var2.getEstimatedModuleSize() - this.average);
            float var4 = Math.abs(var1.getEstimatedModuleSize() - this.average);
            byte var5;
            if (var3 < var4) {
               var5 = 1;
            } else if (var3 == var4) {
               var5 = 0;
            } else {
               var5 = -1;
            }

            return var5;
         } else {
            return var2.getCount() - var1.getCount();
         }
      }
   }

   private static final class FurthestFromAverageComparator implements Comparator<FinderPattern>, Serializable {
      private final float average;

      private FurthestFromAverageComparator(float var1) {
         this.average = var1;
      }

      // $FF: synthetic method
      FurthestFromAverageComparator(float var1, Object var2) {
         this(var1);
      }

      public int compare(FinderPattern var1, FinderPattern var2) {
         float var3 = Math.abs(var2.getEstimatedModuleSize() - this.average);
         float var4 = Math.abs(var1.getEstimatedModuleSize() - this.average);
         byte var5;
         if (var3 < var4) {
            var5 = -1;
         } else if (var3 == var4) {
            var5 = 0;
         } else {
            var5 = 1;
         }

         return var5;
      }
   }
}
