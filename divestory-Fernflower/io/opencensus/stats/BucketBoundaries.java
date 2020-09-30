package io.opencensus.stats;

import io.opencensus.internal.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BucketBoundaries {
   private static final Logger logger = Logger.getLogger(BucketBoundaries.class.getName());

   public static final BucketBoundaries create(List<Double> var0) {
      Utils.checkNotNull(var0, "bucketBoundaries");
      ArrayList var7 = new ArrayList(var0);
      if (var7.size() > 1) {
         double var1 = (Double)var7.get(0);

         double var4;
         for(int var3 = 1; var3 < var7.size(); var1 = var4) {
            var4 = (Double)var7.get(var3);
            boolean var6;
            if (var1 < var4) {
               var6 = true;
            } else {
               var6 = false;
            }

            Utils.checkArgument(var6, "Bucket boundaries not sorted.");
            ++var3;
         }
      }

      return new AutoValue_BucketBoundaries(Collections.unmodifiableList(dropNegativeBucketBounds(var7)));
   }

   private static List<Double> dropNegativeBucketBounds(List<Double> var0) {
      Iterator var1 = var0.iterator();
      int var2 = 0;
      int var3 = 0;

      while(var1.hasNext()) {
         Double var4 = (Double)var1.next();
         if (var4 > 0.0D) {
            break;
         }

         if (var4 == 0.0D) {
            ++var3;
         } else {
            ++var2;
         }
      }

      if (var2 > 0) {
         Logger var6 = logger;
         Level var5 = Level.WARNING;
         StringBuilder var7 = new StringBuilder();
         var7.append("Dropping ");
         var7.append(var2);
         var7.append(" negative bucket boundaries, the values must be strictly > 0.");
         var6.log(var5, var7.toString());
      }

      return var0.subList(var2 + var3, var0.size());
   }

   public abstract List<Double> getBoundaries();
}
