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
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class SubscriberRegistry {
   private static final LoadingCache<Class<?>, ImmutableSet<Class<?>>> flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableSet<Class<?>>>() {
      public ImmutableSet<Class<?>> load(Class<?> var1) {
         return ImmutableSet.copyOf((Collection)TypeToken.of(var1).getTypes().rawTypes());
      }
   });
   private static final LoadingCache<Class<?>, ImmutableList<Method>> subscriberMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, ImmutableList<Method>>() {
      public ImmutableList<Method> load(Class<?> var1) throws Exception {
         return SubscriberRegistry.getAnnotatedMethodsNotCached(var1);
      }
   });
   private final EventBus bus;
   private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> subscribers = Maps.newConcurrentMap();

   SubscriberRegistry(EventBus var1) {
      this.bus = (EventBus)Preconditions.checkNotNull(var1);
   }

   private Multimap<Class<?>, Subscriber> findAllSubscribers(Object var1) {
      HashMultimap var2 = HashMultimap.create();
      UnmodifiableIterator var3 = getAnnotatedMethods(var1.getClass()).iterator();

      while(var3.hasNext()) {
         Method var4 = (Method)var3.next();
         var2.put(var4.getParameterTypes()[0], Subscriber.create(this.bus, var1, var4));
      }

      return var2;
   }

   static ImmutableSet<Class<?>> flattenHierarchy(Class<?> var0) {
      try {
         ImmutableSet var2 = (ImmutableSet)flattenHierarchyCache.getUnchecked(var0);
         return var2;
      } catch (UncheckedExecutionException var1) {
         throw Throwables.propagate(var1.getCause());
      }
   }

   private static ImmutableList<Method> getAnnotatedMethods(Class<?> var0) {
      return (ImmutableList)subscriberMethodsCache.getUnchecked(var0);
   }

   private static ImmutableList<Method> getAnnotatedMethodsNotCached(Class<?> var0) {
      Set var1 = TypeToken.of(var0).getTypes().rawTypes();
      HashMap var9 = Maps.newHashMap();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Method[] var10 = ((Class)var2.next()).getDeclaredMethods();
         int var3 = var10.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method var5 = var10[var4];
            if (var5.isAnnotationPresent(Subscribe.class) && !var5.isSynthetic()) {
               Class[] var6 = var5.getParameterTypes();
               int var7 = var6.length;
               boolean var8 = true;
               if (var7 != 1) {
                  var8 = false;
               }

               Preconditions.checkArgument(var8, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", var5, (int)var6.length);
               SubscriberRegistry.MethodIdentifier var11 = new SubscriberRegistry.MethodIdentifier(var5);
               if (!var9.containsKey(var11)) {
                  var9.put(var11, var5);
               }
            }
         }
      }

      return ImmutableList.copyOf(var9.values());
   }

   Iterator<Subscriber> getSubscribers(Object var1) {
      ImmutableSet var2 = flattenHierarchy(var1.getClass());
      ArrayList var4 = Lists.newArrayListWithCapacity(var2.size());
      UnmodifiableIterator var5 = var2.iterator();

      while(var5.hasNext()) {
         Class var3 = (Class)var5.next();
         CopyOnWriteArraySet var6 = (CopyOnWriteArraySet)this.subscribers.get(var3);
         if (var6 != null) {
            var4.add(var6.iterator());
         }
      }

      return Iterators.concat(var4.iterator());
   }

   Set<Subscriber> getSubscribersForTesting(Class<?> var1) {
      return (Set)MoreObjects.firstNonNull(this.subscribers.get(var1), ImmutableSet.of());
   }

   void register(Object var1) {
      Collection var4;
      CopyOnWriteArraySet var7;
      for(Iterator var2 = this.findAllSubscribers(var1).asMap().entrySet().iterator(); var2.hasNext(); var7.addAll(var4)) {
         Entry var6 = (Entry)var2.next();
         Class var3 = (Class)var6.getKey();
         var4 = (Collection)var6.getValue();
         CopyOnWriteArraySet var5 = (CopyOnWriteArraySet)this.subscribers.get(var3);
         var7 = var5;
         if (var5 == null) {
            var7 = new CopyOnWriteArraySet();
            var7 = (CopyOnWriteArraySet)MoreObjects.firstNonNull(this.subscribers.putIfAbsent(var3, var7), var7);
         }
      }

   }

   void unregister(Object var1) {
      Iterator var2 = this.findAllSubscribers(var1).asMap().entrySet().iterator();

      Collection var6;
      CopyOnWriteArraySet var7;
      do {
         if (!var2.hasNext()) {
            return;
         }

         Entry var3 = (Entry)var2.next();
         Class var4 = (Class)var3.getKey();
         var6 = (Collection)var3.getValue();
         var7 = (CopyOnWriteArraySet)this.subscribers.get(var4);
      } while(var7 != null && var7.removeAll(var6));

      StringBuilder var5 = new StringBuilder();
      var5.append("missing event subscriber for an annotated method. Is ");
      var5.append(var1);
      var5.append(" registered?");
      throw new IllegalArgumentException(var5.toString());
   }

   private static final class MethodIdentifier {
      private final String name;
      private final List<Class<?>> parameterTypes;

      MethodIdentifier(Method var1) {
         this.name = var1.getName();
         this.parameterTypes = Arrays.asList(var1.getParameterTypes());
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof SubscriberRegistry.MethodIdentifier;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            SubscriberRegistry.MethodIdentifier var5 = (SubscriberRegistry.MethodIdentifier)var1;
            var4 = var3;
            if (this.name.equals(var5.name)) {
               var4 = var3;
               if (this.parameterTypes.equals(var5.parameterTypes)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return Objects.hashCode(this.name, this.parameterTypes);
      }
   }
}
