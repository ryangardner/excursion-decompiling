package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder {
   private final ArrayList<Object> list;

   public SpreadBuilder(int var1) {
      this.list = new ArrayList(var1);
   }

   public void add(Object var1) {
      this.list.add(var1);
   }

   public void addSpread(Object var1) {
      if (var1 != null) {
         if (var1 instanceof Object[]) {
            Object[] var3 = (Object[])var1;
            if (var3.length > 0) {
               ArrayList var2 = this.list;
               var2.ensureCapacity(var2.size() + var3.length);
               Collections.addAll(this.list, var3);
            }
         } else if (var1 instanceof Collection) {
            this.list.addAll((Collection)var1);
         } else if (var1 instanceof Iterable) {
            Iterator var5 = ((Iterable)var1).iterator();

            while(var5.hasNext()) {
               var1 = var5.next();
               this.list.add(var1);
            }
         } else {
            if (!(var1 instanceof Iterator)) {
               StringBuilder var6 = new StringBuilder();
               var6.append("Don't know how to spread ");
               var6.append(var1.getClass());
               throw new UnsupportedOperationException(var6.toString());
            }

            Iterator var4 = (Iterator)var1;

            while(var4.hasNext()) {
               this.list.add(var4.next());
            }
         }

      }
   }

   public int size() {
      return this.list.size();
   }

   public Object[] toArray(Object[] var1) {
      return this.list.toArray(var1);
   }
}
