package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.data.Entry;
import java.util.Comparator;

public class EntryXComparator implements Comparator<Entry> {
   public int compare(Entry var1, Entry var2) {
      float var4;
      int var3 = (var4 = var1.getX() - var2.getX() - 0.0F) == 0.0F ? 0 : (var4 < 0.0F ? -1 : 1);
      if (var3 == 0) {
         return 0;
      } else {
         return var3 > 0 ? 1 : -1;
      }
   }
}
