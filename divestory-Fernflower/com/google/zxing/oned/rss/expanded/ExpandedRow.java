package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;

final class ExpandedRow {
   private final List<ExpandedPair> pairs;
   private final int rowNumber;
   private final boolean wasReversed;

   ExpandedRow(List<ExpandedPair> var1, int var2, boolean var3) {
      this.pairs = new ArrayList(var1);
      this.rowNumber = var2;
      this.wasReversed = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof ExpandedRow;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         ExpandedRow var4 = (ExpandedRow)var1;
         var2 = var3;
         if (this.pairs.equals(var4.getPairs())) {
            var2 = var3;
            if (this.wasReversed == var4.wasReversed) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   List<ExpandedPair> getPairs() {
      return this.pairs;
   }

   int getRowNumber() {
      return this.rowNumber;
   }

   public int hashCode() {
      return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
   }

   boolean isEquivalent(List<ExpandedPair> var1) {
      return this.pairs.equals(var1);
   }

   boolean isReversed() {
      return this.wasReversed;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{ ");
      var1.append(this.pairs);
      var1.append(" }");
      return var1.toString();
   }
}
