package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class PropertyReference2Impl extends PropertyReference2 {
   private final String name;
   private final KDeclarationContainer owner;
   private final String signature;

   public PropertyReference2Impl(KDeclarationContainer var1, String var2, String var3) {
      this.owner = var1;
      this.name = var2;
      this.signature = var3;
   }

   public Object get(Object var1, Object var2) {
      return this.getGetter().call(new Object[]{var1, var2});
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
