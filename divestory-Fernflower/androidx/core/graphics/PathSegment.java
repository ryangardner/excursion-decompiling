package androidx.core.graphics;

import android.graphics.PointF;
import androidx.core.util.Preconditions;

public final class PathSegment {
   private final PointF mEnd;
   private final float mEndFraction;
   private final PointF mStart;
   private final float mStartFraction;

   public PathSegment(PointF var1, float var2, PointF var3, float var4) {
      this.mStart = (PointF)Preconditions.checkNotNull(var1, "start == null");
      this.mStartFraction = var2;
      this.mEnd = (PointF)Preconditions.checkNotNull(var3, "end == null");
      this.mEndFraction = var4;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PathSegment)) {
         return false;
      } else {
         PathSegment var3 = (PathSegment)var1;
         if (Float.compare(this.mStartFraction, var3.mStartFraction) != 0 || Float.compare(this.mEndFraction, var3.mEndFraction) != 0 || !this.mStart.equals(var3.mStart) || !this.mEnd.equals(var3.mEnd)) {
            var2 = false;
         }

         return var2;
      }
   }

   public PointF getEnd() {
      return this.mEnd;
   }

   public float getEndFraction() {
      return this.mEndFraction;
   }

   public PointF getStart() {
      return this.mStart;
   }

   public float getStartFraction() {
      return this.mStartFraction;
   }

   public int hashCode() {
      int var1 = this.mStart.hashCode();
      float var2 = this.mStartFraction;
      int var3 = 0;
      int var4;
      if (var2 != 0.0F) {
         var4 = Float.floatToIntBits(var2);
      } else {
         var4 = 0;
      }

      int var5 = this.mEnd.hashCode();
      var2 = this.mEndFraction;
      if (var2 != 0.0F) {
         var3 = Float.floatToIntBits(var2);
      }

      return ((var1 * 31 + var4) * 31 + var5) * 31 + var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("PathSegment{start=");
      var1.append(this.mStart);
      var1.append(", startFraction=");
      var1.append(this.mStartFraction);
      var1.append(", end=");
      var1.append(this.mEnd);
      var1.append(", endFraction=");
      var1.append(this.mEndFraction);
      var1.append('}');
      return var1.toString();
   }
}
