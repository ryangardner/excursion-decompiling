package org.apache.http.conn.routing;

public class BasicRouteDirector implements HttpRouteDirector {
   protected int directStep(RouteInfo var1, RouteInfo var2) {
      if (var2.getHopCount() > 1) {
         return -1;
      } else if (!var1.getTargetHost().equals(var2.getTargetHost())) {
         return -1;
      } else if (var1.isSecure() != var2.isSecure()) {
         return -1;
      } else {
         return var1.getLocalAddress() != null && !var1.getLocalAddress().equals(var2.getLocalAddress()) ? -1 : 0;
      }
   }

   protected int firstStep(RouteInfo var1) {
      int var2 = var1.getHopCount();
      byte var3 = 1;
      if (var2 > 1) {
         var3 = 2;
      }

      return var3;
   }

   public int nextStep(RouteInfo var1, RouteInfo var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("Planned route may not be null.");
      } else {
         int var3;
         if (var2 != null && var2.getHopCount() >= 1) {
            if (var1.getHopCount() > 1) {
               var3 = this.proxiedStep(var1, var2);
            } else {
               var3 = this.directStep(var1, var2);
            }
         } else {
            var3 = this.firstStep(var1);
         }

         return var3;
      }
   }

   protected int proxiedStep(RouteInfo var1, RouteInfo var2) {
      if (var2.getHopCount() <= 1) {
         return -1;
      } else if (!var1.getTargetHost().equals(var2.getTargetHost())) {
         return -1;
      } else {
         int var3 = var1.getHopCount();
         int var4 = var2.getHopCount();
         if (var3 < var4) {
            return -1;
         } else {
            for(int var5 = 0; var5 < var4 - 1; ++var5) {
               if (!var1.getHopTarget(var5).equals(var2.getHopTarget(var5))) {
                  return -1;
               }
            }

            if (var3 > var4) {
               return 4;
            } else if ((!var2.isTunnelled() || var1.isTunnelled()) && (!var2.isLayered() || var1.isLayered())) {
               if (var1.isTunnelled() && !var2.isTunnelled()) {
                  return 3;
               } else if (var1.isLayered() && !var2.isLayered()) {
                  return 5;
               } else {
                  return var1.isSecure() != var2.isSecure() ? -1 : 0;
               }
            } else {
               return -1;
            }
         }
      }
   }
}
