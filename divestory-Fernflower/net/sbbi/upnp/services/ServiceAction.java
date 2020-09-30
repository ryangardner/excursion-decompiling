package net.sbbi.upnp.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceAction {
   protected String name;
   private List orderedActionArguments;
   private List orderedInputActionArguments;
   private List orderedInputActionArgumentsNames;
   private List orderedOutputActionArguments;
   private List orderedOutputActionArgumentsNames;
   protected UPNPService parent;

   protected ServiceAction() {
   }

   private List getListForActionArgument(List var1, String var2) {
      Object var3 = null;
      if (var1 == null) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            ServiceActionArgument var5 = (ServiceActionArgument)var6.next();
            if (var5.getDirection() == var2) {
               var4.add(var5);
            }
         }

         ArrayList var7;
         if (var4.size() == 0) {
            var7 = (ArrayList)var3;
         } else {
            var7 = var4;
         }

         return var7;
      }
   }

   private List getListForActionArgumentNames(List var1, String var2) {
      Object var3 = null;
      if (var1 == null) {
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            ServiceActionArgument var6 = (ServiceActionArgument)var5.next();
            if (var6.getDirection() == var2) {
               var4.add(var6.getName());
            }
         }

         ArrayList var7;
         if (var4.size() == 0) {
            var7 = (ArrayList)var3;
         } else {
            var7 = var4;
         }

         return var7;
      }
   }

   public ServiceActionArgument getActionArgument(String var1) {
      List var2 = this.orderedActionArguments;
      if (var2 == null) {
         return null;
      } else {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            ServiceActionArgument var3 = (ServiceActionArgument)var4.next();
            if (var3.getName().equals(var1)) {
               return var3;
            }
         }

         return null;
      }
   }

   public List getActionArguments() {
      return this.orderedActionArguments;
   }

   public ServiceActionArgument getInputActionArgument(String var1) {
      List var2 = this.orderedInputActionArguments;
      if (var2 == null) {
         return null;
      } else {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            ServiceActionArgument var3 = (ServiceActionArgument)var4.next();
            if (var3.getName().equals(var1)) {
               return var3;
            }
         }

         return null;
      }
   }

   public List getInputActionArguments() {
      return this.orderedInputActionArguments;
   }

   public List getInputActionArgumentsNames() {
      return this.orderedInputActionArgumentsNames;
   }

   public String getName() {
      return this.name;
   }

   public ServiceActionArgument getOutputActionArgument(String var1) {
      List var2 = this.orderedOutputActionArguments;
      if (var2 == null) {
         return null;
      } else {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            ServiceActionArgument var3 = (ServiceActionArgument)var4.next();
            if (var3.getName().equals(var1)) {
               return var3;
            }
         }

         return null;
      }
   }

   public List getOutputActionArguments() {
      return this.orderedOutputActionArguments;
   }

   public List getOutputActionArgumentsNames() {
      return this.orderedOutputActionArgumentsNames;
   }

   public UPNPService getParent() {
      return this.parent;
   }

   protected void setActionArguments(List var1) {
      this.orderedActionArguments = var1;
      this.orderedInputActionArguments = this.getListForActionArgument(var1, "in");
      this.orderedOutputActionArguments = this.getListForActionArgument(var1, "out");
      this.orderedInputActionArgumentsNames = this.getListForActionArgumentNames(var1, "in");
      this.orderedOutputActionArgumentsNames = this.getListForActionArgumentNames(var1, "out");
   }
}
