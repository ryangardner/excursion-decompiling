package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KProperty0;

public abstract class MutablePropertyReference0 extends MutablePropertyReference implements KMutableProperty0 {
   public MutablePropertyReference0() {
   }

   public MutablePropertyReference0(Object var1) {
      super(var1);
   }

   protected KCallable computeReflected() {
      return Reflection.mutableProperty0(this);
   }

   public Object getDelegate() {
      return ((KMutableProperty0)this.getReflected()).getDelegate();
   }

   public KProperty0.Getter getGetter() {
      return ((KMutableProperty0)this.getReflected()).getGetter();
   }

   public KMutableProperty0.Setter getSetter() {
      return ((KMutableProperty0)this.getReflected()).getSetter();
   }

   public Object invoke() {
      return this.get();
   }
}
