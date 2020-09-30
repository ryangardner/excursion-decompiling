package io.opencensus.trace.export;

import io.opencensus.trace.Link;
import java.util.List;

final class AutoValue_SpanData_Links extends SpanData.Links {
   private final int droppedLinksCount;
   private final List<Link> links;

   AutoValue_SpanData_Links(List<Link> var1, int var2) {
      if (var1 != null) {
         this.links = var1;
         this.droppedLinksCount = var2;
      } else {
         throw new NullPointerException("Null links");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanData.Links)) {
         return false;
      } else {
         SpanData.Links var3 = (SpanData.Links)var1;
         if (!this.links.equals(var3.getLinks()) || this.droppedLinksCount != var3.getDroppedLinksCount()) {
            var2 = false;
         }

         return var2;
      }
   }

   public int getDroppedLinksCount() {
      return this.droppedLinksCount;
   }

   public List<Link> getLinks() {
      return this.links;
   }

   public int hashCode() {
      return (this.links.hashCode() ^ 1000003) * 1000003 ^ this.droppedLinksCount;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Links{links=");
      var1.append(this.links);
      var1.append(", droppedLinksCount=");
      var1.append(this.droppedLinksCount);
      var1.append("}");
      return var1.toString();
   }
}
