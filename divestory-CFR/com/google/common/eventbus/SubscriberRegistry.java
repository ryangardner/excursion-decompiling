/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.eventbus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.Subscriber;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class SubscriberRegistry {
    private static final LoadingCache<Class<?>, ImmutableSet<Class<?>>> flattenHierarchyCache;
    private static final LoadingCache<Class<?>, ImmutableList<Method>> subscriberMethodsCache;
    private final EventBus bus;
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> subscribers = Maps.newConcurrentMap();

    static {
        subscriberMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableList<Method>>(){

            @Override
            public ImmutableList<Method> load(Class<?> class_) throws Exception {
                return SubscriberRegistry.getAnnotatedMethodsNotCached(class_);
            }
        });
        flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableSet<Class<?>>>(){

            @Override
            public ImmutableSet<Class<?>> load(Class<?> class_) {
                return ImmutableSet.copyOf(TypeToken.of(class_).getTypes().rawTypes());
            }
        });
    }

    SubscriberRegistry(EventBus eventBus) {
        this.bus = Preconditions.checkNotNull(eventBus);
    }

    private Multimap<Class<?>, Subscriber> findAllSubscribers(Object object) {
        HashMultimap<Class<?>, Subscriber> hashMultimap = HashMultimap.create();
        Iterator iterator2 = SubscriberRegistry.getAnnotatedMethods(object.getClass()).iterator();
        while (iterator2.hasNext()) {
            Method method = (Method)iterator2.next();
            hashMultimap.put(method.getParameterTypes()[0], Subscriber.create(this.bus, object, method));
        }
        return hashMultimap;
    }

    static ImmutableSet<Class<?>> flattenHierarchy(Class<?> serializable) {
        try {
            return flattenHierarchyCache.getUnchecked((Class<?>)serializable);
        }
        catch (UncheckedExecutionException uncheckedExecutionException) {
            throw Throwables.propagate(uncheckedExecutionException.getCause());
        }
    }

    private static ImmutableList<Method> getAnnotatedMethods(Class<?> class_) {
        return subscriberMethodsCache.getUnchecked(class_);
    }

    private static ImmutableList<Method> getAnnotatedMethodsNotCached(Class<?> serializable) {
        Method[] arrmethod = TypeToken.of(serializable).getTypes().rawTypes();
        serializable = Maps.newHashMap();
        Iterator iterator2 = arrmethod.iterator();
        block0 : while (iterator2.hasNext()) {
            arrmethod = iterator2.next().getDeclaredMethods();
            int n = arrmethod.length;
            int n2 = 0;
            do {
                if (n2 >= n) continue block0;
                Method method = arrmethod[n2];
                if (method.isAnnotationPresent(Subscribe.class) && !method.isSynthetic()) {
                    Object object = method.getParameterTypes();
                    int n3 = ((Class<?>[])object).length;
                    boolean bl = true;
                    if (n3 != 1) {
                        bl = false;
                    }
                    Preconditions.checkArgument(bl, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", (Object)method, ((Class<?>[])object).length);
                    object = new MethodIdentifier(method);
                    if (!serializable.containsKey(object)) {
                        serializable.put(object, method);
                    }
                }
                ++n2;
            } while (true);
            break;
        }
        return ImmutableList.copyOf(serializable.values());
    }

    Iterator<Subscriber> getSubscribers(Object arrayList) {
        Object object = SubscriberRegistry.flattenHierarchy(arrayList.getClass());
        arrayList = Lists.newArrayListWithCapacity(((AbstractCollection)object).size());
        object = ((ImmutableSet)object).iterator();
        while (object.hasNext()) {
            Serializable serializable = (Class)object.next();
            if ((serializable = (CopyOnWriteArraySet)this.subscribers.get(serializable)) == null) continue;
            arrayList.add(((CopyOnWriteArraySet)serializable).iterator());
        }
        return Iterators.concat(arrayList.iterator());
    }

    Set<Subscriber> getSubscribersForTesting(Class<?> class_) {
        return MoreObjects.firstNonNull(this.subscribers.get(class_), ImmutableSet.of());
    }

    void register(Object copyOnWriteArraySet) {
        Iterator<Map.Entry<Class<?>, Collection<Subscriber>>> iterator2 = this.findAllSubscribers(copyOnWriteArraySet).asMap().entrySet().iterator();
        while (iterator2.hasNext()) {
            copyOnWriteArraySet = iterator2.next();
            Class<?> class_ = copyOnWriteArraySet.getKey();
            Collection<Subscriber> collection = copyOnWriteArraySet.getValue();
            CopyOnWriteArraySet copyOnWriteArraySet2 = (CopyOnWriteArraySet)this.subscribers.get(class_);
            copyOnWriteArraySet = copyOnWriteArraySet2;
            if (copyOnWriteArraySet2 == null) {
                copyOnWriteArraySet = new CopyOnWriteArraySet<Subscriber>();
                copyOnWriteArraySet = MoreObjects.firstNonNull(this.subscribers.putIfAbsent(class_, copyOnWriteArraySet), copyOnWriteArraySet);
            }
            copyOnWriteArraySet.addAll(collection);
        }
    }

    void unregister(Object object) {
        Serializable serializable;
        Object object2;
        Object object3 = this.findAllSubscribers(object).asMap().entrySet().iterator();
        do {
            if (!object3.hasNext()) return;
            object2 = object3.next();
            serializable = object2.getKey();
            object2 = object2.getValue();
        } while ((serializable = (CopyOnWriteArraySet)this.subscribers.get(serializable)) != null && ((CopyOnWriteArraySet)serializable).removeAll((Collection<?>)object2));
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("missing event subscriber for an annotated method. Is ");
        ((StringBuilder)object3).append(object);
        ((StringBuilder)object3).append(" registered?");
        throw new IllegalArgumentException(((StringBuilder)object3).toString());
    }

    private static final class MethodIdentifier {
        private final String name;
        private final List<Class<?>> parameterTypes;

        MethodIdentifier(Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }

        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof MethodIdentifier;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (MethodIdentifier)object;
            bl3 = bl;
            if (!this.name.equals(((MethodIdentifier)object).name)) return bl3;
            bl3 = bl;
            if (!this.parameterTypes.equals(((MethodIdentifier)object).parameterTypes)) return bl3;
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.name, this.parameterTypes);
        }
    }

}

