package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;

public abstract class CallableReference implements KCallable, Serializable {
   public static final Object NO_RECEIVER;
   protected final Object receiver;
   private transient KCallable reflected;

   static {
      NO_RECEIVER = CallableReference.NoReceiver.INSTANCE;
   }

   public CallableReference() {
      this(NO_RECEIVER);
   }

   protected CallableReference(Object var1) {
      this.receiver = var1;
   }

   public Object call(Object... var1) {
      return this.getReflected().call(var1);
   }

   public Object callBy(Map var1) {
      return this.getReflected().callBy(var1);
   }

   public KCallable compute() {
      KCallable var1 = this.reflected;
      KCallable var2 = var1;
      if (var1 == null) {
         var2 = this.computeReflected();
         this.reflected = var2;
      }

      return var2;
   }

   protected abstract KCallable computeReflected();

   public List<Annotation> getAnnotations() {
      return this.getReflected().getAnnotations();
   }

   public Object getBoundReceiver() {
      return this.receiver;
   }

   public String getName() {
      throw new AbstractMethodError();
   }

   public KDeclarationContainer getOwner() {
      throw new AbstractMethodError();
   }

   public List<KParameter> getParameters() {
      return this.getReflected().getParameters();
   }

   protected KCallable getReflected() {
      KCallable var1 = this.compute();
      if (var1 != this) {
         return var1;
      } else {
         throw new KotlinReflectionNotSupportedError();
      }
   }

   public KType getReturnType() {
      return this.getReflected().getReturnType();
   }

   public String getSignature() {
      throw new AbstractMethodError();
   }

   public List<KTypeParameter> getTypeParameters() {
      return this.getReflected().getTypeParameters();
   }

   public KVisibility getVisibility() {
      return this.getReflected().getVisibility();
   }

   public boolean isAbstract() {
      return this.getReflected().isAbstract();
   }

   public boolean isFinal() {
      return this.getReflected().isFinal();
   }

   public boolean isOpen() {
      return this.getReflected().isOpen();
   }

   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   private static class NoReceiver implements Serializable {
      private static final CallableReference.NoReceiver INSTANCE = new CallableReference.NoReceiver();

      private Object readResolve() throws ObjectStreamException {
         return INSTANCE;
      }
   }
}
