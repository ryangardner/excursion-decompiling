package io.opencensus.trace;

import java.util.List;

final class AutoValue_Tracestate extends Tracestate {
   private final List<Tracestate.Entry> entries;

   AutoValue_Tracestate(List<Tracestate.Entry> var1) {
      if (var1 != null) {
         this.entries = var1;
      } else {
         throw new NullPointerException("Null entries");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof Tracestate) {
         Tracestate var2 = (Tracestate)var1;
         return this.entries.equals(var2.getEntries());
      } else {
         return false;
      }
   }

   public List<Tracestate.Entry> getEntries() {
      return this.entries;
   }

   public int hashCode() {
      return this.entries.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Tracestate{entries=");
      var1.append(this.entries);
      var1.append("}");
      return var1.toString();
   }
}
