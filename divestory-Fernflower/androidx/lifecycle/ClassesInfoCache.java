package androidx.lifecycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ClassesInfoCache {
   private static final int CALL_TYPE_NO_ARG = 0;
   private static final int CALL_TYPE_PROVIDER = 1;
   private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
   static ClassesInfoCache sInstance = new ClassesInfoCache();
   private final Map<Class<?>, ClassesInfoCache.CallbackInfo> mCallbackMap = new HashMap();
   private final Map<Class<?>, Boolean> mHasLifecycleMethods = new HashMap();

   private ClassesInfoCache.CallbackInfo createInfo(Class<?> var1, Method[] var2) {
      Class var3 = var1.getSuperclass();
      HashMap var4 = new HashMap();
      if (var3 != null) {
         ClassesInfoCache.CallbackInfo var12 = this.getInfo(var3);
         if (var12 != null) {
            var4.putAll(var12.mHandlerToEvent);
         }
      }

      Class[] var5 = var1.getInterfaces();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Iterator var13 = this.getInfo(var5[var7]).mHandlerToEvent.entrySet().iterator();

         while(var13.hasNext()) {
            Entry var8 = (Entry)var13.next();
            this.verifyAndPutHandler(var4, (ClassesInfoCache.MethodReference)var8.getKey(), (Lifecycle.Event)var8.getValue(), var1);
         }
      }

      if (var2 == null) {
         var2 = this.getDeclaredMethods(var1);
      }

      int var9 = var2.length;
      var6 = 0;

      boolean var10;
      for(var10 = false; var6 < var9; ++var6) {
         Method var14 = var2[var6];
         OnLifecycleEvent var15 = (OnLifecycleEvent)var14.getAnnotation(OnLifecycleEvent.class);
         if (var15 != null) {
            Class[] var18 = var14.getParameterTypes();
            byte var17;
            if (var18.length > 0) {
               if (!var18[0].isAssignableFrom(LifecycleOwner.class)) {
                  throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
               }

               var17 = 1;
            } else {
               var17 = 0;
            }

            Lifecycle.Event var16 = var15.value();
            if (var18.length > 1) {
               if (!var18[1].isAssignableFrom(Lifecycle.Event.class)) {
                  throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
               }

               if (var16 != Lifecycle.Event.ON_ANY) {
                  throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
               }

               var17 = 2;
            }

            if (var18.length > 2) {
               throw new IllegalArgumentException("cannot have more than 2 params");
            }

            this.verifyAndPutHandler(var4, new ClassesInfoCache.MethodReference(var17, var14), var16, var1);
            var10 = true;
         }
      }

      ClassesInfoCache.CallbackInfo var11 = new ClassesInfoCache.CallbackInfo(var4);
      this.mCallbackMap.put(var1, var11);
      this.mHasLifecycleMethods.put(var1, var10);
      return var11;
   }

   private Method[] getDeclaredMethods(Class<?> var1) {
      try {
         Method[] var3 = var1.getDeclaredMethods();
         return var3;
      } catch (NoClassDefFoundError var2) {
         throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", var2);
      }
   }

   private void verifyAndPutHandler(Map<ClassesInfoCache.MethodReference, Lifecycle.Event> var1, ClassesInfoCache.MethodReference var2, Lifecycle.Event var3, Class<?> var4) {
      Lifecycle.Event var5 = (Lifecycle.Event)var1.get(var2);
      if (var5 != null && var3 != var5) {
         Method var6 = var2.mMethod;
         StringBuilder var7 = new StringBuilder();
         var7.append("Method ");
         var7.append(var6.getName());
         var7.append(" in ");
         var7.append(var4.getName());
         var7.append(" already declared with different @OnLifecycleEvent value: previous value ");
         var7.append(var5);
         var7.append(", new value ");
         var7.append(var3);
         throw new IllegalArgumentException(var7.toString());
      } else {
         if (var5 == null) {
            var1.put(var2, var3);
         }

      }
   }

   ClassesInfoCache.CallbackInfo getInfo(Class<?> var1) {
      ClassesInfoCache.CallbackInfo var2 = (ClassesInfoCache.CallbackInfo)this.mCallbackMap.get(var1);
      return var2 != null ? var2 : this.createInfo(var1, (Method[])null);
   }

   boolean hasLifecycleMethods(Class<?> var1) {
      Boolean var2 = (Boolean)this.mHasLifecycleMethods.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         Method[] var5 = this.getDeclaredMethods(var1);
         int var3 = var5.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            if ((OnLifecycleEvent)var5[var4].getAnnotation(OnLifecycleEvent.class) != null) {
               this.createInfo(var1, var5);
               return true;
            }
         }

         this.mHasLifecycleMethods.put(var1, false);
         return false;
      }
   }

   static class CallbackInfo {
      final Map<Lifecycle.Event, List<ClassesInfoCache.MethodReference>> mEventToHandlers;
      final Map<ClassesInfoCache.MethodReference, Lifecycle.Event> mHandlerToEvent;

      CallbackInfo(Map<ClassesInfoCache.MethodReference, Lifecycle.Event> var1) {
         this.mHandlerToEvent = var1;
         this.mEventToHandlers = new HashMap();

         Entry var3;
         Object var6;
         for(Iterator var2 = var1.entrySet().iterator(); var2.hasNext(); ((List)var6).add(var3.getKey())) {
            var3 = (Entry)var2.next();
            Lifecycle.Event var4 = (Lifecycle.Event)var3.getValue();
            List var5 = (List)this.mEventToHandlers.get(var4);
            var6 = var5;
            if (var5 == null) {
               var6 = new ArrayList();
               this.mEventToHandlers.put(var4, var6);
            }
         }

      }

      private static void invokeMethodsForEvent(List<ClassesInfoCache.MethodReference> var0, LifecycleOwner var1, Lifecycle.Event var2, Object var3) {
         if (var0 != null) {
            for(int var4 = var0.size() - 1; var4 >= 0; --var4) {
               ((ClassesInfoCache.MethodReference)var0.get(var4)).invokeCallback(var1, var2, var3);
            }
         }

      }

      void invokeCallbacks(LifecycleOwner var1, Lifecycle.Event var2, Object var3) {
         invokeMethodsForEvent((List)this.mEventToHandlers.get(var2), var1, var2, var3);
         invokeMethodsForEvent((List)this.mEventToHandlers.get(Lifecycle.Event.ON_ANY), var1, var2, var3);
      }
   }

   static class MethodReference {
      final int mCallType;
      final Method mMethod;

      MethodReference(int var1, Method var2) {
         this.mCallType = var1;
         this.mMethod = var2;
         var2.setAccessible(true);
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            ClassesInfoCache.MethodReference var3 = (ClassesInfoCache.MethodReference)var1;
            if (this.mCallType != var3.mCallType || !this.mMethod.getName().equals(var3.mMethod.getName())) {
               var2 = false;
            }

            return var2;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.mCallType * 31 + this.mMethod.getName().hashCode();
      }

      void invokeCallback(LifecycleOwner var1, Lifecycle.Event var2, Object var3) {
         InvocationTargetException var15;
         label49: {
            IllegalAccessException var10000;
            label48: {
               int var4;
               boolean var10001;
               try {
                  var4 = this.mCallType;
               } catch (InvocationTargetException var11) {
                  var15 = var11;
                  var10001 = false;
                  break label49;
               } catch (IllegalAccessException var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label48;
               }

               if (var4 != 0) {
                  if (var4 != 1) {
                     if (var4 != 2) {
                        return;
                     }

                     try {
                        this.mMethod.invoke(var3, var1, var2);
                        return;
                     } catch (InvocationTargetException var5) {
                        var15 = var5;
                        var10001 = false;
                        break label49;
                     } catch (IllegalAccessException var6) {
                        var10000 = var6;
                        var10001 = false;
                     }
                  } else {
                     try {
                        this.mMethod.invoke(var3, var1);
                        return;
                     } catch (InvocationTargetException var7) {
                        var15 = var7;
                        var10001 = false;
                        break label49;
                     } catch (IllegalAccessException var8) {
                        var10000 = var8;
                        var10001 = false;
                     }
                  }
               } else {
                  try {
                     this.mMethod.invoke(var3);
                     return;
                  } catch (InvocationTargetException var9) {
                     var15 = var9;
                     var10001 = false;
                     break label49;
                  } catch (IllegalAccessException var10) {
                     var10000 = var10;
                     var10001 = false;
                  }
               }
            }

            IllegalAccessException var13 = var10000;
            throw new RuntimeException(var13);
         }

         InvocationTargetException var14 = var15;
         throw new RuntimeException("Failed to call observer method", var14.getCause());
      }
   }
}
