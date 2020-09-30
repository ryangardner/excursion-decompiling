package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty2;

public abstract class PropertyReference2 extends PropertyReference implements KProperty2 {
   protected KCallable computeReflected() {
      return Reflection.property2(this);
   }

   public Object getDelegate(Object var1, Object var2) {
      return ((KProperty2)this.getReflected()).getDelegate(var1, var2);
   }

   public KProperty2.Getter getGetter() {
      return ((KProperty2)this.getReflected()).getGetter();
   }

   public Object invoke(Object var1, Object var2) {
      return this.get(var1, var2);
   }
}
