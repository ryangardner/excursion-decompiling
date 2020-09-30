package kotlin.jvm.internal;

import kotlin.reflect.KMutableProperty;

public abstract class MutablePropertyReference extends PropertyReference implements KMutableProperty {
   public MutablePropertyReference() {
   }

   public MutablePropertyReference(Object var1) {
      super(var1);
   }
}
