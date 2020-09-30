package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class FuturesGetChecked {
   private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() {
      public Boolean apply(Constructor<?> var1) {
         return Arrays.asList(var1.getParameterTypes()).contains(String.class);
      }
   }).reverse();

   private FuturesGetChecked() {
   }

   private static FuturesGetChecked.GetCheckedTypeValidator bestGetCheckedTypeValidator() {
      return FuturesGetChecked.GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
   }

   static void checkExceptionClassValidity(Class<? extends Exception> var0) {
      Preconditions.checkArgument(isCheckedException(var0), "Futures.getChecked exception type (%s) must not be a RuntimeException", (Object)var0);
      Preconditions.checkArgument(hasConstructorUsableByGetChecked(var0), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", (Object)var0);
   }

   static FuturesGetChecked.GetCheckedTypeValidator classValueValidator() {
      return FuturesGetChecked.GetCheckedTypeValidatorHolder.ClassValueValidator.INSTANCE;
   }

   static <V, X extends Exception> V getChecked(FuturesGetChecked.GetCheckedTypeValidator var0, Future<V> var1, Class<X> var2) throws X {
      var0.validateClass(var2);

      try {
         Object var5 = var1.get();
         return var5;
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         throw newWithCause(var2, var3);
      } catch (ExecutionException var4) {
         wrapAndThrowExceptionOrError(var4.getCause(), var2);
         throw new AssertionError();
      }
   }

   static <V, X extends Exception> V getChecked(Future<V> var0, Class<X> var1) throws X {
      return getChecked(bestGetCheckedTypeValidator(), var0, var1);
   }

   static <V, X extends Exception> V getChecked(Future<V> var0, Class<X> var1, long var2, TimeUnit var4) throws X {
      bestGetCheckedTypeValidator().validateClass(var1);

      try {
         Object var8 = var0.get(var2, var4);
         return var8;
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         throw newWithCause(var1, var5);
      } catch (TimeoutException var6) {
         throw newWithCause(var1, var6);
      } catch (ExecutionException var7) {
         wrapAndThrowExceptionOrError(var7.getCause(), var1);
         throw new AssertionError();
      }
   }

   private static boolean hasConstructorUsableByGetChecked(Class<? extends Exception> var0) {
      try {
         Exception var1 = new Exception();
         newWithCause(var0, var1);
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   static boolean isCheckedException(Class<? extends Exception> var0) {
      return RuntimeException.class.isAssignableFrom(var0) ^ true;
   }

   @NullableDecl
   private static <X> X newFromConstructor(Constructor<X> var0, Throwable var1) {
      Class[] var2 = var0.getParameterTypes();
      Object[] var3 = new Object[var2.length];

      for(int var4 = 0; var4 < var2.length; ++var4) {
         Class var5 = var2[var4];
         if (var5.equals(String.class)) {
            var3[var4] = var1.toString();
         } else {
            if (!var5.equals(Throwable.class)) {
               return null;
            }

            var3[var4] = var1;
         }
      }

      try {
         Object var7 = var0.newInstance(var3);
         return var7;
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException var6) {
         return null;
      }
   }

   private static <X extends Exception> X newWithCause(Class<X> var0, Throwable var1) {
      Iterator var2 = preferringStrings(Arrays.asList(var0.getConstructors())).iterator();

      Exception var3;
      do {
         if (!var2.hasNext()) {
            StringBuilder var4 = new StringBuilder();
            var4.append("No appropriate constructor for exception of type ");
            var4.append(var0);
            var4.append(" in response to chained exception");
            throw new IllegalArgumentException(var4.toString(), var1);
         }

         var3 = (Exception)newFromConstructor((Constructor)var2.next(), var1);
      } while(var3 == null);

      if (var3.getCause() == null) {
         var3.initCause(var1);
      }

      return var3;
   }

   private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> var0) {
      return WITH_STRING_PARAM_FIRST.sortedCopy(var0);
   }

   static FuturesGetChecked.GetCheckedTypeValidator weakSetValidator() {
      return FuturesGetChecked.GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
   }

   private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable var0, Class<X> var1) throws X {
      if (!(var0 instanceof Error)) {
         if (var0 instanceof RuntimeException) {
            throw new UncheckedExecutionException(var0);
         } else {
            throw newWithCause(var1, var0);
         }
      } else {
         throw new ExecutionError((Error)var0);
      }
   }

   interface GetCheckedTypeValidator {
      void validateClass(Class<? extends Exception> var1);
   }

   static class GetCheckedTypeValidatorHolder {
      static final FuturesGetChecked.GetCheckedTypeValidator BEST_VALIDATOR;
      static final String CLASS_VALUE_VALIDATOR_NAME;

      static {
         StringBuilder var0 = new StringBuilder();
         var0.append(FuturesGetChecked.GetCheckedTypeValidatorHolder.class.getName());
         var0.append("$ClassValueValidator");
         CLASS_VALUE_VALIDATOR_NAME = var0.toString();
         BEST_VALIDATOR = getBestValidator();
      }

      static FuturesGetChecked.GetCheckedTypeValidator getBestValidator() {
         try {
            FuturesGetChecked.GetCheckedTypeValidator var0 = (FuturesGetChecked.GetCheckedTypeValidator)Class.forName(CLASS_VALUE_VALIDATOR_NAME).getEnumConstants()[0];
            return var0;
         } finally {
            ;
         }
      }

      static enum ClassValueValidator implements FuturesGetChecked.GetCheckedTypeValidator {
         INSTANCE;

         private static final ClassValue<Boolean> isValidClass;

         static {
            FuturesGetChecked.GetCheckedTypeValidatorHolder.ClassValueValidator var0 = new FuturesGetChecked.GetCheckedTypeValidatorHolder.ClassValueValidator("INSTANCE", 0);
            INSTANCE = var0;
            isValidClass = new ClassValue<Boolean>() {
               protected Boolean computeValue(Class<?> var1) {
                  FuturesGetChecked.checkExceptionClassValidity(var1.asSubclass(Exception.class));
                  return true;
               }
            };
         }

         public void validateClass(Class<? extends Exception> var1) {
            isValidClass.get(var1);
         }
      }

      static enum WeakSetValidator implements FuturesGetChecked.GetCheckedTypeValidator {
         INSTANCE;

         private static final Set<WeakReference<Class<? extends Exception>>> validClasses;

         static {
            FuturesGetChecked.GetCheckedTypeValidatorHolder.WeakSetValidator var0 = new FuturesGetChecked.GetCheckedTypeValidatorHolder.WeakSetValidator("INSTANCE", 0);
            INSTANCE = var0;
            validClasses = new CopyOnWriteArraySet();
         }

         public void validateClass(Class<? extends Exception> var1) {
            Iterator var2 = validClasses.iterator();

            do {
               if (!var2.hasNext()) {
                  FuturesGetChecked.checkExceptionClassValidity(var1);
                  if (validClasses.size() > 1000) {
                     validClasses.clear();
                  }

                  validClasses.add(new WeakReference(var1));
                  return;
               }
            } while(!var1.equals(((WeakReference)var2.next()).get()));

         }
      }
   }
}
