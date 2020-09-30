/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.lifecycle.ClassesInfoCache;
import androidx.lifecycle.CompositeGeneratedAdaptersObserver;
import androidx.lifecycle.FullLifecycleObserver;
import androidx.lifecycle.FullLifecycleObserverAdapter;
import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ReflectiveGenericLifecycleObserver;
import androidx.lifecycle.SingleGeneratedAdapterObserver;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lifecycling {
    private static final int GENERATED_CALLBACK = 2;
    private static final int REFLECTIVE_CALLBACK = 1;
    private static Map<Class<?>, Integer> sCallbackCache = new HashMap();
    private static Map<Class<?>, List<Constructor<? extends GeneratedAdapter>>> sClassToAdapters = new HashMap();

    private Lifecycling() {
    }

    private static GeneratedAdapter createGeneratedAdapter(Constructor<? extends GeneratedAdapter> object, Object object2) {
        try {
            return ((Constructor)object).newInstance(object2);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException);
        }
        catch (InstantiationException instantiationException) {
            throw new RuntimeException(instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }

    private static Constructor<? extends GeneratedAdapter> generatedConstructor(Class<?> genericDeclaration) {
        try {
            Object object = ((Class)genericDeclaration).getPackage();
            String string2 = ((Class)genericDeclaration).getCanonicalName();
            object = object != null ? ((Package)object).getName() : "";
            if (!((String)object).isEmpty()) {
                string2 = string2.substring(((String)object).length() + 1);
            }
            string2 = Lifecycling.getAdapterName(string2);
            if (((String)object).isEmpty()) {
                object = string2;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(".");
                stringBuilder.append(string2);
                object = stringBuilder.toString();
            }
            genericDeclaration = Class.forName((String)object).getDeclaredConstructor(new Class[]{genericDeclaration});
            if (((AccessibleObject)((Object)genericDeclaration)).isAccessible()) return genericDeclaration;
            ((Constructor)genericDeclaration).setAccessible(true);
            return genericDeclaration;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static String getAdapterName(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2.replace(".", "_"));
        stringBuilder.append("_LifecycleAdapter");
        return stringBuilder.toString();
    }

    @Deprecated
    static GenericLifecycleObserver getCallback(Object object) {
        return new GenericLifecycleObserver(Lifecycling.lifecycleEventObserver(object)){
            final /* synthetic */ LifecycleEventObserver val$observer;
            {
                this.val$observer = lifecycleEventObserver;
            }

            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                this.val$observer.onStateChanged(lifecycleOwner, event);
            }
        };
    }

    private static int getObserverConstructorType(Class<?> class_) {
        Integer n = sCallbackCache.get(class_);
        if (n != null) {
            return n;
        }
        int n2 = Lifecycling.resolveObserverCallbackType(class_);
        sCallbackCache.put(class_, n2);
        return n2;
    }

    private static boolean isLifecycleParent(Class<?> class_) {
        if (class_ == null) return false;
        if (!LifecycleObserver.class.isAssignableFrom(class_)) return false;
        return true;
    }

    static LifecycleEventObserver lifecycleEventObserver(Object object) {
        boolean bl = object instanceof LifecycleEventObserver;
        boolean bl2 = object instanceof FullLifecycleObserver;
        if (bl && bl2) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver)object, (LifecycleEventObserver)object);
        }
        if (bl2) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver)object, null);
        }
        if (bl) {
            return (LifecycleEventObserver)object;
        }
        Class<?> class_ = object.getClass();
        if (Lifecycling.getObserverConstructorType(class_) != 2) return new ReflectiveGenericLifecycleObserver(object);
        class_ = sClassToAdapters.get(class_);
        int n = class_.size();
        int n2 = 0;
        if (n == 1) {
            return new SingleGeneratedAdapterObserver(Lifecycling.createGeneratedAdapter((Constructor)class_.get(0), object));
        }
        GeneratedAdapter[] arrgeneratedAdapter = new GeneratedAdapter[class_.size()];
        while (n2 < class_.size()) {
            arrgeneratedAdapter[n2] = Lifecycling.createGeneratedAdapter((Constructor)class_.get(n2), object);
            ++n2;
        }
        return new CompositeGeneratedAdaptersObserver(arrgeneratedAdapter);
    }

    private static int resolveObserverCallbackType(Class<?> class_) {
        if (class_.getCanonicalName() == null) {
            return 1;
        }
        Object object = Lifecycling.generatedConstructor(class_);
        if (object != null) {
            sClassToAdapters.put(class_, Collections.singletonList(object));
            return 2;
        }
        if (ClassesInfoCache.sInstance.hasLifecycleMethods(class_)) {
            return 1;
        }
        ArrayList arrayList = class_.getSuperclass();
        object = null;
        if (Lifecycling.isLifecycleParent(arrayList)) {
            if (Lifecycling.getObserverConstructorType(arrayList) == 1) {
                return 1;
            }
            object = new ArrayList(sClassToAdapters.get(arrayList));
        }
        Class<?>[] arrclass = class_.getInterfaces();
        int n = arrclass.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (object == null) return 1;
                sClassToAdapters.put(class_, (List<Constructor<? extends GeneratedAdapter>>)object);
                return 2;
            }
            Class<?> class_2 = arrclass[n2];
            if (Lifecycling.isLifecycleParent(class_2)) {
                if (Lifecycling.getObserverConstructorType(class_2) == 1) {
                    return 1;
                }
                arrayList = object;
                if (object == null) {
                    arrayList = new ArrayList();
                }
                arrayList.addAll(sClassToAdapters.get(class_2));
                object = arrayList;
            }
            ++n2;
        } while (true);
    }

}

