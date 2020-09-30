package com.google.zxing.pdf417.detector;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.List;

public final class PDF417DetectorResult {
   private final BitMatrix bits;
   private final List<ResultPoint[]> points;

   public PDF417DetectorResult(BitMatrix var1, List<ResultPoint[]> var2) {
      this.bits = var1;
      this.points = var2;
   }

   public BitMatrix getBits() {
      return this.bits;
   }

   public List<ResultPoint[]> getPoints() {
      return this.points;
   }
}
