package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty2;

public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KMutableProperty2 {
   protected KCallable computeReflected() {
      return Reflection.mutableProperty2(this);
   }

   public Object getDelegate(Object var1, Object var2) {
      return ((KMutableProperty2)this.getReflected()).getDelegate(var1, var2);
   }

   public KProperty2.Getter getGetter() {
      return ((KMutableProperty2)this.getReflected()).getGetter();
   }

   public KMutableProperty2.Setter getSetter() {
      return ((KMutableProperty2)this.getReflected()).getSetter();
   }

   public Object invoke(Object var1, Object var2) {
      return this.get(var1, var2);
   }
}
