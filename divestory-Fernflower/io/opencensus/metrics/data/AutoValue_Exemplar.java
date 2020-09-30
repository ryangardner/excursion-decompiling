package io.opencensus.metrics.data;

import io.opencensus.common.Timestamp;
import java.util.Map;

final class AutoValue_Exemplar extends Exemplar {
   private final Map<String, AttachmentValue> attachments;
   private final Timestamp timestamp;
   private final double value;

   AutoValue_Exemplar(double var1, Timestamp var3, Map<String, AttachmentValue> var4) {
      this.value = var1;
      if (var3 != null) {
         this.timestamp = var3;
         if (var4 != null) {
            this.attachments = var4;
         } else {
            throw new NullPointerException("Null attachments");
         }
      } else {
         throw new NullPointerException("Null timestamp");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Exemplar)) {
         return false;
      } else {
         Exemplar var3 = (Exemplar)var1;
         if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(var3.getValue()) || !this.timestamp.equals(var3.getTimestamp()) || !this.attachments.equals(var3.getAttachments())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<String, AttachmentValue> getAttachments() {
      return this.attachments;
   }

   public Timestamp getTimestamp() {
      return this.timestamp;
   }

   public double getValue() {
      return this.value;
   }

   public int hashCode() {
      int var1 = (int)((long)1000003 ^ Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value));
      int var2 = this.timestamp.hashCode();
      return this.attachments.hashCode() ^ (var2 ^ var1 * 1000003) * 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Exemplar{value=");
      var1.append(this.value);
      var1.append(", timestamp=");
      var1.append(this.timestamp);
      var1.append(", attachments=");
      var1.append(this.attachments);
      var1.append("}");
      return var1.toString();
   }
}
