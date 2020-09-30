package io.opencensus.metrics.export;

import io.opencensus.metrics.data.Exemplar;
import javax.annotation.Nullable;

final class AutoValue_Distribution_Bucket extends Distribution.Bucket {
   private final long count;
   private final Exemplar exemplar;

   AutoValue_Distribution_Bucket(long var1, @Nullable Exemplar var3) {
      this.count = var1;
      this.exemplar = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Distribution.Bucket)) {
         return false;
      } else {
         Distribution.Bucket var4 = (Distribution.Bucket)var1;
         if (this.count == var4.getCount()) {
            Exemplar var3 = this.exemplar;
            if (var3 == null) {
               if (var4.getExemplar() == null) {
                  return var2;
               }
            } else if (var3.equals(var4.getExemplar())) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }
   }

   public long getCount() {
      return this.count;
   }

   @Nullable
   public Exemplar getExemplar() {
      return this.exemplar;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.count;
      int var5 = (int)(var1 ^ var3 ^ var3 >>> 32);
      Exemplar var6 = this.exemplar;
      int var7;
      if (var6 == null) {
         var7 = 0;
      } else {
         var7 = var6.hashCode();
      }

      return var7 ^ var5 * 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Bucket{count=");
      var1.append(this.count);
      var1.append(", exemplar=");
      var1.append(this.exemplar);
      var1.append("}");
      return var1.toString();
   }
}
