package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class PropertyReference0Impl extends PropertyReference0 {
   private final String name;
   private final KDeclarationContainer owner;
   private final String signature;

   public PropertyReference0Impl(KDeclarationContainer var1, String var2, String var3) {
      this.owner = var1;
      this.name = var2;
      this.signature = var3;
   }

   public Object get() {
      return this.getGetter().call(new Object[0]);
   }

   public String getName() {
      return this.name;
   }

   public KDeclarationContainer getOwner() {
      return this.owner;
   }

   public String getSignature() {
      return this.signature;
   }
}
