package com.crest.divestory.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {
   private static final int COUNT = 25;
   public static final List<DummyContent.DummyItem> ITEMS = new ArrayList();
   public static final Map<String, DummyContent.DummyItem> ITEM_MAP = new HashMap();

   static {
      for(int var0 = 1; var0 <= 25; ++var0) {
         addItem(createDummyItem(var0));
      }

   }

   private static void addItem(DummyContent.DummyItem var0) {
      ITEMS.add(var0);
      ITEM_MAP.put(var0.id, var0);
   }

   private static DummyContent.DummyItem createDummyItem(int var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("Item ");
      var1.append(var0);
      return new DummyContent.DummyItem(String.valueOf(var0), var1.toString(), makeDetails(var0));
   }

   private static String makeDetails(int var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("Details about Item: ");
      var1.append(var0);

      for(int var2 = 0; var2 < var0; ++var2) {
         var1.append("\nMore details information here.");
      }

      return var1.toString();
   }

   public static class DummyItem {
      public final String content;
      public final String details;
      public final String id;

      public DummyItem(String var1, String var2, String var3) {
         this.id = var1;
         this.content = var2;
         this.details = var3;
      }

      public String toString() {
         return this.content;
      }
   }
}
