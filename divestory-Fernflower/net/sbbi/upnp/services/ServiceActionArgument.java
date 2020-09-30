package net.sbbi.upnp.services;

public class ServiceActionArgument {
   public static final String DIRECTION_IN = "in";
   public static final String DIRECTION_OUT = "out";
   protected String direction;
   protected String name;
   protected ServiceStateVariable relatedStateVariable;

   public String getDirection() {
      return this.direction;
   }

   public String getName() {
      return this.name;
   }

   public ServiceStateVariable getRelatedStateVariable() {
      return this.relatedStateVariable;
   }
}
