package io.opencensus.tags;

import java.util.HashMap;
import java.util.Iterator;
import javax.annotation.Nullable;

public abstract class TagContext {
   public boolean equals(@Nullable Object var1) {
      if (!(var1 instanceof TagContext)) {
         return false;
      } else {
         TagContext var6 = (TagContext)var1;
         Iterator var2 = this.getIterator();
         Iterator var7 = var6.getIterator();
         HashMap var3 = new HashMap();

         while(var2 != null && var2.hasNext()) {
            Tag var4 = (Tag)var2.next();
            if (var3.containsKey(var4)) {
               var3.put(var4, (Integer)var3.get(var4) + 1);
            } else {
               var3.put(var4, 1);
            }
         }

         while(var7 != null && var7.hasNext()) {
            Tag var8 = (Tag)var7.next();
            if (!var3.containsKey(var8)) {
               return false;
            }

            int var5 = (Integer)var3.get(var8);
            if (var5 > 1) {
               var3.put(var8, var5 - 1);
            } else {
               var3.remove(var8);
            }
         }

         return var3.isEmpty();
      }
   }

   protected abstract Iterator<Tag> getIterator();

   public final int hashCode() {
      Iterator var1 = this.getIterator();
      int var2 = 0;
      if (var1 == null) {
         return 0;
      } else {
         while(var1.hasNext()) {
            Tag var3 = (Tag)var1.next();
            if (var3 != null) {
               var2 += var3.hashCode();
            }
         }

         return var2;
      }
   }

   public String toString() {
      return "TagContext";
   }
}
