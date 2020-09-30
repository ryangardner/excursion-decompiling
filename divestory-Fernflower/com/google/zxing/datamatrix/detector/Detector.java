package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class Detector {
   private final BitMatrix image;
   private final WhiteRectangleDetector rectangleDetector;

   public Detector(BitMatrix var1) throws NotFoundException {
      this.image = var1;
      this.rectangleDetector = new WhiteRectangleDetector(var1);
   }

   private ResultPoint correctTopRight(ResultPoint var1, ResultPoint var2, ResultPoint var3, ResultPoint var4, int var5) {
      float var6 = (float)distance(var1, var2);
      float var7 = (float)var5;
      var6 /= var7;
      var5 = distance(var3, var4);
      float var8 = var4.getX();
      float var9 = var3.getX();
      float var10 = (float)var5;
      var8 = (var8 - var9) / var10;
      var10 = (var4.getY() - var3.getY()) / var10;
      ResultPoint var11 = new ResultPoint(var4.getX() + var8 * var6, var4.getY() + var6 * var10);
      var7 = (float)distance(var1, var3) / var7;
      var5 = distance(var2, var4);
      var8 = var4.getX();
      var10 = var2.getX();
      var6 = (float)var5;
      var10 = (var8 - var10) / var6;
      var6 = (var4.getY() - var2.getY()) / var6;
      var1 = new ResultPoint(var4.getX() + var10 * var7, var4.getY() + var7 * var6);
      if (!this.isValid(var11)) {
         return this.isValid(var1) ? var1 : null;
      } else if (!this.isValid(var1)) {
         return var11;
      } else {
         if (Math.abs(this.transitionsBetween(var3, var11).getTransitions() - this.transitionsBetween(var2, var11).getTransitions()) <= Math.abs(this.transitionsBetween(var3, var1).getTransitions() - this.transitionsBetween(var2, var1).getTransitions())) {
            var1 = var11;
         }

         return var1;
      }
   }

   private ResultPoint correctTopRightRectangular(ResultPoint var1, ResultPoint var2, ResultPoint var3, ResultPoint var4, int var5, int var6) {
      float var7 = (float)distance(var1, var2) / (float)var5;
      int var8 = distance(var3, var4);
      float var9 = var4.getX();
      float var10 = var3.getX();
      float var11 = (float)var8;
      var10 = (var9 - var10) / var11;
      var11 = (var4.getY() - var3.getY()) / var11;
      ResultPoint var12 = new ResultPoint(var4.getX() + var10 * var7, var4.getY() + var7 * var11);
      var7 = (float)distance(var1, var3) / (float)var6;
      var8 = distance(var2, var4);
      var9 = var4.getX();
      var10 = var2.getX();
      var11 = (float)var8;
      var10 = (var9 - var10) / var11;
      var11 = (var4.getY() - var2.getY()) / var11;
      var1 = new ResultPoint(var4.getX() + var10 * var7, var4.getY() + var7 * var11);
      if (!this.isValid(var12)) {
         return this.isValid(var1) ? var1 : null;
      } else if (!this.isValid(var1)) {
         return var12;
      } else {
         return Math.abs(var5 - this.transitionsBetween(var3, var12).getTransitions()) + Math.abs(var6 - this.transitionsBetween(var2, var12).getTransitions()) <= Math.abs(var5 - this.transitionsBetween(var3, var1).getTransitions()) + Math.abs(var6 - this.transitionsBetween(var2, var1).getTransitions()) ? var12 : var1;
      }
   }

   private static int distance(ResultPoint var0, ResultPoint var1) {
      return MathUtils.round(ResultPoint.distance(var0, var1));
   }

   private static void increment(Map<ResultPoint, Integer> var0, ResultPoint var1) {
      Integer var2 = (Integer)var0.get(var1);
      int var3 = 1;
      if (var2 != null) {
         var3 = 1 + var2;
      }

      var0.put(var1, var3);
   }

   private boolean isValid(ResultPoint var1) {
      boolean var2;
      if (var1.getX() >= 0.0F && var1.getX() < (float)this.image.getWidth() && var1.getY() > 0.0F && var1.getY() < (float)this.image.getHeight()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static BitMatrix sampleGrid(BitMatrix var0, ResultPoint var1, ResultPoint var2, ResultPoint var3, ResultPoint var4, int var5, int var6) throws NotFoundException {
      GridSampler var7 = GridSampler.getInstance();
      float var8 = (float)var5 - 0.5F;
      float var9 = (float)var6 - 0.5F;
      return var7.sampleGrid(var0, var5, var6, 0.5F, 0.5F, var8, 0.5F, var8, var9, 0.5F, var9, var1.getX(), var1.getY(), var4.getX(), var4.getY(), var3.getX(), var3.getY(), var2.getX(), var2.getY());
   }

   private Detector.ResultPointsAndTransitions transitionsBetween(ResultPoint var1, ResultPoint var2) {
      int var3 = (int)var1.getX();
      int var4 = (int)var1.getY();
      int var5 = (int)var2.getX();
      int var6 = (int)var2.getY();
      int var7 = Math.abs(var6 - var4);
      int var8 = Math.abs(var5 - var3);
      byte var9 = 0;
      byte var10 = 1;
      boolean var11;
      if (var7 > var8) {
         var11 = true;
      } else {
         var11 = false;
      }

      var8 = var3;
      var7 = var4;
      int var12 = var5;
      int var13 = var6;
      if (var11) {
         var7 = var3;
         var8 = var4;
         var13 = var5;
         var12 = var6;
      }

      int var14 = Math.abs(var12 - var8);
      int var15 = Math.abs(var13 - var7);
      var4 = -var14 / 2;
      byte var20;
      if (var7 < var13) {
         var20 = 1;
      } else {
         var20 = -1;
      }

      if (var8 >= var12) {
         var10 = -1;
      }

      BitMatrix var16 = this.image;
      if (var11) {
         var6 = var7;
      } else {
         var6 = var8;
      }

      if (var11) {
         var5 = var8;
      } else {
         var5 = var7;
      }

      boolean var17 = var16.get(var6, var5);
      var6 = var9;

      while(true) {
         var5 = var6;
         if (var8 == var12) {
            break;
         }

         var16 = this.image;
         if (var11) {
            var5 = var7;
         } else {
            var5 = var8;
         }

         int var21;
         if (var11) {
            var21 = var8;
         } else {
            var21 = var7;
         }

         boolean var18 = var16.get(var5, var21);
         var5 = var6;
         boolean var19 = var17;
         if (var18 != var17) {
            var5 = var6 + 1;
            var19 = var18;
         }

         var21 = var4 + var15;
         var6 = var7;
         var4 = var21;
         if (var21 > 0) {
            if (var7 == var13) {
               break;
            }

            var6 = var7 + var20;
            var4 = var21 - var14;
         }

         var8 += var10;
         var7 = var6;
         var6 = var5;
         var17 = var19;
      }

      return new Detector.ResultPointsAndTransitions(var1, var2, var5);
   }

   public DetectorResult detect() throws NotFoundException {
      ResultPoint[] var1 = this.rectangleDetector.detect();
      ResultPoint var2 = var1[0];
      ResultPoint var3 = var1[1];
      ResultPoint var4 = var1[2];
      ResultPoint var5 = var1[3];
      ArrayList var6 = new ArrayList(4);
      var6.add(this.transitionsBetween(var2, var3));
      var6.add(this.transitionsBetween(var2, var4));
      var6.add(this.transitionsBetween(var3, var5));
      var6.add(this.transitionsBetween(var4, var5));
      ResultPoint var7 = null;
      Collections.sort(var6, new Detector.ResultPointsAndTransitionsComparator());
      Detector.ResultPointsAndTransitions var15 = (Detector.ResultPointsAndTransitions)var6.get(0);
      Detector.ResultPointsAndTransitions var17 = (Detector.ResultPointsAndTransitions)var6.get(1);
      HashMap var8 = new HashMap();
      increment(var8, var15.getFrom());
      increment(var8, var15.getTo());
      increment(var8, var17.getFrom());
      increment(var8, var17.getTo());
      Iterator var9 = var8.entrySet().iterator();
      ResultPoint var10 = null;
      ResultPoint var18 = var10;

      ResultPoint var16;
      while(var9.hasNext()) {
         Entry var11 = (Entry)var9.next();
         var16 = (ResultPoint)var11.getKey();
         if ((Integer)var11.getValue() == 2) {
            var10 = var16;
         } else if (var7 == null) {
            var7 = var16;
         } else {
            var18 = var16;
         }
      }

      if (var7 != null && var10 != null && var18 != null) {
         var1 = new ResultPoint[]{var7, var10, var18};
         ResultPoint.orderBestPatterns(var1);
         ResultPoint var21 = var1[0];
         ResultPoint var20 = var1[1];
         var7 = var1[2];
         if (!var8.containsKey(var2)) {
            var16 = var2;
         } else if (!var8.containsKey(var3)) {
            var16 = var3;
         } else if (!var8.containsKey(var4)) {
            var16 = var4;
         } else {
            var16 = var5;
         }

         int var12 = this.transitionsBetween(var7, var16).getTransitions();
         int var13 = this.transitionsBetween(var21, var16).getTransitions();
         int var14 = var12;
         if ((var12 & 1) == 1) {
            var14 = var12 + 1;
         }

         var12 = var14 + 2;
         var14 = var13;
         if ((var13 & 1) == 1) {
            var14 = var13 + 1;
         }

         var14 += 2;
         BitMatrix var19;
         if (var12 * 4 < var14 * 7 && var14 * 4 < var12 * 7) {
            var18 = this.correctTopRight(var20, var21, var7, var16, Math.min(var14, var12));
            if (var18 != null) {
               var16 = var18;
            }

            var13 = Math.max(this.transitionsBetween(var7, var16).getTransitions(), this.transitionsBetween(var21, var16).getTransitions()) + 1;
            var14 = var13;
            if ((var13 & 1) == 1) {
               var14 = var13 + 1;
            }

            var19 = sampleGrid(this.image, var7, var20, var21, var16, var14, var14);
         } else {
            var10 = this.correctTopRightRectangular(var20, var21, var7, var16, var12, var14);
            if (var10 != null) {
               var16 = var10;
            }

            var13 = this.transitionsBetween(var7, var16).getTransitions();
            var12 = this.transitionsBetween(var21, var16).getTransitions();
            var14 = var13;
            if ((var13 & 1) == 1) {
               var14 = var13 + 1;
            }

            var13 = var12;
            if ((var12 & 1) == 1) {
               var13 = var12 + 1;
            }

            var19 = sampleGrid(this.image, var7, var20, var21, var16, var14, var13);
         }

         return new DetectorResult(var19, new ResultPoint[]{var7, var20, var21, var16});
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private static final class ResultPointsAndTransitions {
      private final ResultPoint from;
      private final ResultPoint to;
      private final int transitions;

      private ResultPointsAndTransitions(ResultPoint var1, ResultPoint var2, int var3) {
         this.from = var1;
         this.to = var2;
         this.transitions = var3;
      }

      // $FF: synthetic method
      ResultPointsAndTransitions(ResultPoint var1, ResultPoint var2, int var3, Object var4) {
         this(var1, var2, var3);
      }

      ResultPoint getFrom() {
         return this.from;
      }

      ResultPoint getTo() {
         return this.to;
      }

      public int getTransitions() {
         return this.transitions;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.from);
         var1.append("/");
         var1.append(this.to);
         var1.append('/');
         var1.append(this.transitions);
         return var1.toString();
      }
   }

   private static final class ResultPointsAndTransitionsComparator implements Comparator<Detector.ResultPointsAndTransitions>, Serializable {
      private ResultPointsAndTransitionsComparator() {
      }

      // $FF: synthetic method
      ResultPointsAndTransitionsComparator(Object var1) {
         this();
      }

      public int compare(Detector.ResultPointsAndTransitions var1, Detector.ResultPointsAndTransitions var2) {
         return var1.getTransitions() - var2.getTransitions();
      }
   }
}
