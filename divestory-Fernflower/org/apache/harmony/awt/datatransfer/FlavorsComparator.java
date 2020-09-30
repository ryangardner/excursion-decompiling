package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.util.Comparator;

public class FlavorsComparator implements Comparator<DataFlavor> {
   public int compare(DataFlavor var1, DataFlavor var2) {
      if (!var1.isFlavorTextType() && !var2.isFlavorTextType()) {
         return 0;
      } else {
         boolean var3 = var1.isFlavorTextType();
         byte var4 = -1;
         if (!var3 && var2.isFlavorTextType()) {
            return -1;
         } else if (var1.isFlavorTextType() && !var2.isFlavorTextType()) {
            return 1;
         } else {
            if (DataFlavor.selectBestTextFlavor(new DataFlavor[]{var1, var2}) != var1) {
               var4 = 1;
            }

            return var4;
         }
      }
   }
}
