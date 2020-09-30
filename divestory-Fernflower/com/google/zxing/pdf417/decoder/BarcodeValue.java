package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class BarcodeValue {
   private final Map<Integer, Integer> values = new HashMap();

   public Integer getConfidence(int var1) {
      return (Integer)this.values.get(var1);
   }

   int[] getValue() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.values.entrySet().iterator();
      int var3 = -1;

      while(var2.hasNext()) {
         Entry var4 = (Entry)var2.next();
         if ((Integer)var4.getValue() > var3) {
            var3 = (Integer)var4.getValue();
            var1.clear();
            var1.add(var4.getKey());
         } else if ((Integer)var4.getValue() == var3) {
            var1.add(var4.getKey());
         }
      }

      return PDF417Common.toIntArray(var1);
   }

   void setValue(int var1) {
      Integer var2 = (Integer)this.values.get(var1);
      Integer var3 = var2;
      if (var2 == null) {
         var3 = 0;
      }

      int var4 = var3;
      this.values.put(var1, var4 + 1);
   }
}
