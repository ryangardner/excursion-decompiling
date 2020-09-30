package io.opencensus.trace.samplers;

final class AutoValue_ProbabilitySampler extends ProbabilitySampler {
   private final long idUpperBound;
   private final double probability;

   AutoValue_ProbabilitySampler(double var1, long var3) {
      this.probability = var1;
      this.idUpperBound = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ProbabilitySampler)) {
         return false;
      } else {
         ProbabilitySampler var3 = (ProbabilitySampler)var1;
         if (Double.doubleToLongBits(this.probability) != Double.doubleToLongBits(var3.getProbability()) || this.idUpperBound != var3.getIdUpperBound()) {
            var2 = false;
         }

         return var2;
      }
   }

   long getIdUpperBound() {
      return this.idUpperBound;
   }

   double getProbability() {
      return this.probability;
   }

   public int hashCode() {
      long var1 = (long)((int)((long)1000003 ^ Double.doubleToLongBits(this.probability) >>> 32 ^ Double.doubleToLongBits(this.probability)) * 1000003);
      long var3 = this.idUpperBound;
      return (int)(var1 ^ var3 ^ var3 >>> 32);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ProbabilitySampler{probability=");
      var1.append(this.probability);
      var1.append(", idUpperBound=");
      var1.append(this.idUpperBound);
      var1.append("}");
      return var1.toString();
   }
}
