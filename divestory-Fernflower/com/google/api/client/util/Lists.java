package com.google.api.client.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class Lists {
   private Lists() {
   }

   public static <E> ArrayList<E> newArrayList() {
      return new ArrayList();
   }

   public static <E> ArrayList<E> newArrayList(Iterable<? extends E> var0) {
      ArrayList var1;
      if (var0 instanceof Collection) {
         var1 = new ArrayList(Collections2.cast(var0));
      } else {
         var1 = newArrayList(var0.iterator());
      }

      return var1;
   }

   public static <E> ArrayList<E> newArrayList(Iterator<? extends E> var0) {
      ArrayList var1 = newArrayList();

      while(var0.hasNext()) {
         var1.add(var0.next());
      }

      return var1;
   }

   public static <E> ArrayList<E> newArrayListWithCapacity(int var0) {
      return new ArrayList(var0);
   }
}
