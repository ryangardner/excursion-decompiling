package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils {
   public static final String KEY_NEXT_PAGE_TOKEN = "next_page_token";
   public static final String KEY_PREV_PAGE_TOKEN = "prev_page_token";

   private DataBufferUtils() {
   }

   public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> var0) {
      ArrayList var1 = new ArrayList(var0.getCount());

      label78: {
         Throwable var10000;
         label77: {
            boolean var10001;
            Iterator var2;
            try {
               var2 = var0.iterator();
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label77;
            }

            while(true) {
               try {
                  if (!var2.hasNext()) {
                     break label78;
                  }

                  var1.add(((Freezable)var2.next()).freeze());
               } catch (Throwable var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var9 = var10000;
         var0.close();
         throw var9;
      }

      var0.close();
      return var1;
   }

   public static boolean hasData(DataBuffer<?> var0) {
      return var0 != null && var0.getCount() > 0;
   }

   public static boolean hasNextPage(DataBuffer<?> var0) {
      Bundle var1 = var0.getMetadata();
      return var1 != null && var1.getString("next_page_token") != null;
   }

   public static boolean hasPrevPage(DataBuffer<?> var0) {
      Bundle var1 = var0.getMetadata();
      return var1 != null && var1.getString("prev_page_token") != null;
   }
}
