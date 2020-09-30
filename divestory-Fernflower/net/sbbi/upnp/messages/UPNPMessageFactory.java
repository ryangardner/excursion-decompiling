package net.sbbi.upnp.messages;

import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.services.UPNPService;

public class UPNPMessageFactory {
   private UPNPService service;

   private UPNPMessageFactory(UPNPService var1) {
      this.service = var1;
   }

   public static UPNPMessageFactory getNewInstance(UPNPService var0) {
      return new UPNPMessageFactory(var0);
   }

   public ActionMessage getMessage(String var1) {
      ServiceAction var2 = this.service.getUPNPServiceAction(var1);
      return var2 != null ? new ActionMessage(this.service, var2) : null;
   }

   public StateVariableMessage getStateVariableMessage(String var1) {
      ServiceStateVariable var2 = this.service.getUPNPServiceStateVariable(var1);
      return var2 != null ? new StateVariableMessage(this.service, var2) : null;
   }
}
