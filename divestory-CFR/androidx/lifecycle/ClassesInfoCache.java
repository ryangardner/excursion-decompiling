/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ClassesInfoCache {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
    static ClassesInfoCache sInstance = new ClassesInfoCache();
    private final Map<Class<?>, CallbackInfo> mCallbackMap = new HashMap();
    private final Map<Class<?>, Boolean> mHasLifecycleMethods = new HashMap();

    ClassesInfoCache() {
    }

    private CallbackInfo createInfo(Class<?> class_, Method[] object) {
        int n;
        Object object2 = class_.getSuperclass();
        HashMap<MethodReference, Lifecycle.Event> hashMap = new HashMap<MethodReference, Lifecycle.Event>();
        if (object2 != null && (object2 = this.getInfo((Class<?>)object2)) != null) {
            hashMap.putAll(((CallbackInfo)object2).mHandlerToEvent);
        }
        Object object3 = class_.getInterfaces();
        int n2 = ((Class<?>[])object3).length;
        for (n = 0; n < n2; ++n) {
            for (Map.Entry<MethodReference, Lifecycle.Event> arrclass : this.getInfo(object3[n]).mHandlerToEvent.entrySet()) {
                this.verifyAndPutHandler(hashMap, arrclass.getKey(), arrclass.getValue(), class_);
            }
        }
        if (object == null) {
            object = this.getDeclaredMethods(class_);
        }
        int n3 = ((Object)object).length;
        n2 = 0;
        boolean bl = false;
        do {
            if (n2 >= n3) {
                object = new CallbackInfo(hashMap);
                this.mCallbackMap.put(class_, (CallbackInfo)object);
                this.mHasLifecycleMethods.put(class_, bl);
                return object;
            }
            object2 = object[n2];
            object3 = ((Method)object2).getAnnotation(OnLifecycleEvent.class);
            if (object3 != null) {
                Class<?>[] arrclass = ((Method)object2).getParameterTypes();
                if (arrclass.length > 0) {
                    if (!arrclass[0].isAssignableFrom(LifecycleOwner.class)) throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    n = 1;
                } else {
                    n = 0;
                }
                object3 = object3.value();
                if (arrclass.length > 1) {
                    if (!arrclass[1].isAssignableFrom(Lifecycle.Event.class)) throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    if (object3 != Lifecycle.Event.ON_ANY) throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    n = 2;
                }
                if (arrclass.length > 2) throw new IllegalArgumentException("cannot have more than 2 params");
                this.verifyAndPutHandler((Map<MethodReference, Lifecycle.Event>)hashMap, new MethodReference(n, (Method)object2), (Lifecycle.Event)((Object)object3), class_);
                bl = true;
            }
            ++n2;
        } while (true);
    }

    private Method[] getDeclaredMethods(Class<?> arrmethod) {
        try {
            return arrmethod.getDeclaredMethods();
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", noClassDefFoundError);
        }
    }

    private void verifyAndPutHandler(Map<MethodReference, Lifecycle.Event> object, MethodReference object2, Lifecycle.Event event, Class<?> class_) {
        Lifecycle.Event event2 = object.get(object2);
        if (event2 != null && event != event2) {
            object = ((MethodReference)object2).mMethod;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Method ");
            ((StringBuilder)object2).append(((Method)object).getName());
            ((StringBuilder)object2).append(" in ");
            ((StringBuilder)object2).append(class_.getName());
            ((StringBuilder)object2).append(" already declared with different @OnLifecycleEvent value: previous value ");
            ((StringBuilder)object2).append((Object)event2);
            ((StringBuilder)object2).append(", new value ");
            ((StringBuilder)object2).append((Object)event);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        if (event2 != null) return;
        object.put((MethodReference)object2, (Lifecycle.Event)event);
    }

    CallbackInfo getInfo(Class<?> class_) {
        CallbackInfo callbackInfo = this.mCallbackMap.get(class_);
        if (callbackInfo == null) return this.createInfo(class_, null);
        return callbackInfo;
    }

    boolean hasLifecycleMethods(Class<?> class_) {
        Method[] arrmethod = this.mHasLifecycleMethods.get(class_);
        if (arrmethod != null) {
            return arrmethod.booleanValue();
        }
        arrmethod = this.getDeclaredMethods(class_);
        int n = arrmethod.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mHasLifecycleMethods.put(class_, false);
                return false;
            }
            if (arrmethod[n2].getAnnotation(OnLifecycleEvent.class) != null) {
                this.createInfo(class_, arrmethod);
                return true;
            }
            ++n2;
        } while (true);
    }

    static class CallbackInfo {
        final Map<Lifecycle.Event, List<MethodReference>> mEventToHandlers;
        final Map<MethodReference, Lifecycle.Event> mHandlerToEvent;

        CallbackInfo(Map<MethodReference, Lifecycle.Event> arrayList) {
            this.mHandlerToEvent = arrayList;
            this.mEventToHandlers = new HashMap<Lifecycle.Event, List<MethodReference>>();
            Iterator<Map.Entry<MethodReference, Lifecycle.Event>> iterator2 = arrayList.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<MethodReference, Lifecycle.Event> entry = iterator2.next();
                Lifecycle.Event event = entry.getValue();
                List<MethodReference> list = this.mEventToHandlers.get((Object)event);
                arrayList = list;
                if (list == null) {
                    arrayList = new ArrayList<MethodReference>();
                    this.mEventToHandlers.put(event, arrayList);
                }
                arrayList.add(entry.getKey());
            }
        }

        private static void invokeMethodsForEvent(List<MethodReference> list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
            if (list == null) return;
            int n = list.size() - 1;
            while (n >= 0) {
                list.get(n).invokeCallback(lifecycleOwner, event, object);
                --n;
            }
        }

        void invokeCallbacks(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
            CallbackInfo.invokeMethodsForEvent(this.mEventToHandlers.get((Object)event), lifecycleOwner, event, object);
            CallbackInfo.invokeMethodsForEvent(this.mEventToHandlers.get((Object)Lifecycle.Event.ON_ANY), lifecycleOwner, event, object);
        }
    }

    static class MethodReference {
        final int mCallType;
        final Method mMethod;

        MethodReference(int n, Method method) {
            this.mCallType = n;
            this.mMethod = method;
            method.setAccessible(true);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object == null) return false;
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (MethodReference)object;
            if (this.mCallType != ((MethodReference)object).mCallType) return false;
            if (!this.mMethod.getName().equals(((MethodReference)object).mMethod.getName())) return false;
            return bl;
        }

        public int hashCode() {
            return this.mCallType * 31 + this.mMethod.getName().hashCode();
        }

        void invokeCallback(LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object object) {
            try {
                int n = this.mCallType;
                if (n == 0) {
                    this.mMethod.invoke(object, new Object[0]);
                    return;
                }
                if (n == 1) {
                    this.mMethod.invoke(object, lifecycleOwner);
                    return;
                }
                if (n != 2) {
                    return;
                }
                this.mMethod.invoke(object, new Object[]{lifecycleOwner, event});
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException("Failed to call observer method", invocationTargetException.getCause());
            }
        }
    }

}

