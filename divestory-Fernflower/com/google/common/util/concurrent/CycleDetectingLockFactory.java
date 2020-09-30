package com.google.common.util.concurrent;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class CycleDetectingLockFactory {
   private static final ThreadLocal<ArrayList<CycleDetectingLockFactory.LockGraphNode>> acquiredLocks = new ThreadLocal<ArrayList<CycleDetectingLockFactory.LockGraphNode>>() {
      protected ArrayList<CycleDetectingLockFactory.LockGraphNode> initialValue() {
         return Lists.newArrayListWithCapacity(3);
      }
   };
   private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, CycleDetectingLockFactory.LockGraphNode>> lockGraphNodesPerType = (new MapMaker()).weakKeys().makeMap();
   private static final Logger logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
   final CycleDetectingLockFactory.Policy policy;

   private CycleDetectingLockFactory(CycleDetectingLockFactory.Policy var1) {
      this.policy = (CycleDetectingLockFactory.Policy)Preconditions.checkNotNull(var1);
   }

   // $FF: synthetic method
   CycleDetectingLockFactory(CycleDetectingLockFactory.Policy var1, Object var2) {
      this(var1);
   }

   private void aboutToAcquire(CycleDetectingLockFactory.CycleDetectingLock var1) {
      if (!var1.isAcquiredByCurrentThread()) {
         ArrayList var2 = (ArrayList)acquiredLocks.get();
         CycleDetectingLockFactory.LockGraphNode var3 = var1.getLockGraphNode();
         var3.checkAcquiredLocks(this.policy, var2);
         var2.add(var3);
      }

   }

   static <E extends Enum<E>> Map<E, CycleDetectingLockFactory.LockGraphNode> createNodes(Class<E> var0) {
      EnumMap var1 = Maps.newEnumMap(var0);
      Enum[] var2 = (Enum[])var0.getEnumConstants();
      int var3 = var2.length;
      ArrayList var9 = Lists.newArrayListWithCapacity(var3);
      int var4 = var2.length;
      byte var5 = 0;

      int var6;
      CycleDetectingLockFactory.LockGraphNode var8;
      for(var6 = 0; var6 < var4; ++var6) {
         Enum var7 = var2[var6];
         var8 = new CycleDetectingLockFactory.LockGraphNode(getLockName(var7));
         var9.add(var8);
         var1.put(var7, var8);
      }

      var4 = 1;

      while(true) {
         var6 = var5;
         if (var4 >= var3) {
            while(var6 < var3 - 1) {
               var8 = (CycleDetectingLockFactory.LockGraphNode)var9.get(var6);
               CycleDetectingLockFactory.Policies var10 = CycleDetectingLockFactory.Policies.DISABLED;
               ++var6;
               var8.checkAcquiredLocks(var10, var9.subList(var6, var3));
            }

            return Collections.unmodifiableMap(var1);
         }

         ((CycleDetectingLockFactory.LockGraphNode)var9.get(var4)).checkAcquiredLocks(CycleDetectingLockFactory.Policies.THROW, var9.subList(0, var4));
         ++var4;
      }
   }

   private static String getLockName(Enum<?> var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0.getDeclaringClass().getSimpleName());
      var1.append(".");
      var1.append(var0.name());
      return var1.toString();
   }

   private static Map<? extends Enum, CycleDetectingLockFactory.LockGraphNode> getOrCreateNodes(Class<? extends Enum> var0) {
      Map var1 = (Map)lockGraphNodesPerType.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         var1 = createNodes(var0);
         return (Map)MoreObjects.firstNonNull((Map)lockGraphNodesPerType.putIfAbsent(var0, var1), var1);
      }
   }

   private static void lockStateChanged(CycleDetectingLockFactory.CycleDetectingLock var0) {
      if (!var0.isAcquiredByCurrentThread()) {
         ArrayList var1 = (ArrayList)acquiredLocks.get();
         CycleDetectingLockFactory.LockGraphNode var3 = var0.getLockGraphNode();

         for(int var2 = var1.size() - 1; var2 >= 0; --var2) {
            if (var1.get(var2) == var3) {
               var1.remove(var2);
               break;
            }
         }
      }

   }

   public static CycleDetectingLockFactory newInstance(CycleDetectingLockFactory.Policy var0) {
      return new CycleDetectingLockFactory(var0);
   }

   public static <E extends Enum<E>> CycleDetectingLockFactory.WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> var0, CycleDetectingLockFactory.Policy var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new CycleDetectingLockFactory.WithExplicitOrdering(var1, getOrCreateNodes(var0));
   }

   public ReentrantLock newReentrantLock(String var1) {
      return this.newReentrantLock(var1, false);
   }

   public ReentrantLock newReentrantLock(String var1, boolean var2) {
      Object var3;
      if (this.policy == CycleDetectingLockFactory.Policies.DISABLED) {
         var3 = new ReentrantLock(var2);
      } else {
         var3 = new CycleDetectingLockFactory.CycleDetectingReentrantLock(new CycleDetectingLockFactory.LockGraphNode(var1), var2);
      }

      return (ReentrantLock)var3;
   }

   public ReentrantReadWriteLock newReentrantReadWriteLock(String var1) {
      return this.newReentrantReadWriteLock(var1, false);
   }

   public ReentrantReadWriteLock newReentrantReadWriteLock(String var1, boolean var2) {
      Object var3;
      if (this.policy == CycleDetectingLockFactory.Policies.DISABLED) {
         var3 = new ReentrantReadWriteLock(var2);
      } else {
         var3 = new CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock(new CycleDetectingLockFactory.LockGraphNode(var1), var2);
      }

      return (ReentrantReadWriteLock)var3;
   }

   private interface CycleDetectingLock {
      CycleDetectingLockFactory.LockGraphNode getLockGraphNode();

      boolean isAcquiredByCurrentThread();
   }

   final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLockFactory.CycleDetectingLock {
      private final CycleDetectingLockFactory.LockGraphNode lockGraphNode;

      private CycleDetectingReentrantLock(CycleDetectingLockFactory.LockGraphNode var2, boolean var3) {
         super(var3);
         this.lockGraphNode = (CycleDetectingLockFactory.LockGraphNode)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      CycleDetectingReentrantLock(CycleDetectingLockFactory.LockGraphNode var2, boolean var3, Object var4) {
         this(var2, var3);
      }

      public CycleDetectingLockFactory.LockGraphNode getLockGraphNode() {
         return this.lockGraphNode;
      }

      public boolean isAcquiredByCurrentThread() {
         return this.isHeldByCurrentThread();
      }

      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

      }

      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

      }

      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

         return var1;
      }

      public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         boolean var4;
         try {
            var4 = super.tryLock(var1, var3);
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

         return var4;
      }

      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this);
         }

      }
   }

   private class CycleDetectingReentrantReadLock extends ReadLock {
      final CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock readWriteLock;

      CycleDetectingReentrantReadLock(CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock var2) {
         super(var2);
         this.readWriteLock = var2;
      }

      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var1;
      }

      public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var4;
         try {
            var4 = super.tryLock(var1, var3);
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var4;
      }

      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }
   }

   final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLockFactory.CycleDetectingLock {
      private final CycleDetectingLockFactory.LockGraphNode lockGraphNode;
      private final CycleDetectingLockFactory.CycleDetectingReentrantReadLock readLock;
      private final CycleDetectingLockFactory.CycleDetectingReentrantWriteLock writeLock;

      private CycleDetectingReentrantReadWriteLock(CycleDetectingLockFactory.LockGraphNode var2, boolean var3) {
         super(var3);
         this.readLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantReadLock(this);
         this.writeLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantWriteLock(this);
         this.lockGraphNode = (CycleDetectingLockFactory.LockGraphNode)Preconditions.checkNotNull(var2);
      }

      // $FF: synthetic method
      CycleDetectingReentrantReadWriteLock(CycleDetectingLockFactory.LockGraphNode var2, boolean var3, Object var4) {
         this(var2, var3);
      }

      public CycleDetectingLockFactory.LockGraphNode getLockGraphNode() {
         return this.lockGraphNode;
      }

      public boolean isAcquiredByCurrentThread() {
         boolean var1;
         if (!this.isWriteLockedByCurrentThread() && this.getReadHoldCount() <= 0) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public ReadLock readLock() {
         return this.readLock;
      }

      public WriteLock writeLock() {
         return this.writeLock;
      }
   }

   private class CycleDetectingReentrantWriteLock extends WriteLock {
      final CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock readWriteLock;

      CycleDetectingReentrantWriteLock(CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock var2) {
         super(var2);
         this.readWriteLock = var2;
      }

      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }

      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var1;
      }

      public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var4;
         try {
            var4 = super.tryLock(var1, var3);
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

         return var4;
      }

      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
         }

      }
   }

   private static class ExampleStackTrace extends IllegalStateException {
      static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
      static final ImmutableSet<String> EXCLUDED_CLASS_NAMES = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), CycleDetectingLockFactory.ExampleStackTrace.class.getName(), CycleDetectingLockFactory.LockGraphNode.class.getName());

      ExampleStackTrace(CycleDetectingLockFactory.LockGraphNode var1, CycleDetectingLockFactory.LockGraphNode var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1.getLockName());
         var3.append(" -> ");
         var3.append(var2.getLockName());
         super(var3.toString());
         StackTraceElement[] var6 = this.getStackTrace();
         int var4 = var6.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            if (CycleDetectingLockFactory.WithExplicitOrdering.class.getName().equals(var6[var5].getClassName())) {
               this.setStackTrace(EMPTY_STACK_TRACE);
               break;
            }

            if (!EXCLUDED_CLASS_NAMES.contains(var6[var5].getClassName())) {
               this.setStackTrace((StackTraceElement[])Arrays.copyOfRange(var6, var5, var4));
               break;
            }
         }

      }
   }

   private static class LockGraphNode {
      final Map<CycleDetectingLockFactory.LockGraphNode, CycleDetectingLockFactory.ExampleStackTrace> allowedPriorLocks = (new MapMaker()).weakKeys().makeMap();
      final Map<CycleDetectingLockFactory.LockGraphNode, CycleDetectingLockFactory.PotentialDeadlockException> disallowedPriorLocks = (new MapMaker()).weakKeys().makeMap();
      final String lockName;

      LockGraphNode(String var1) {
         this.lockName = (String)Preconditions.checkNotNull(var1);
      }

      @NullableDecl
      private CycleDetectingLockFactory.ExampleStackTrace findPathTo(CycleDetectingLockFactory.LockGraphNode var1, Set<CycleDetectingLockFactory.LockGraphNode> var2) {
         if (!var2.add(this)) {
            return null;
         } else {
            CycleDetectingLockFactory.ExampleStackTrace var3 = (CycleDetectingLockFactory.ExampleStackTrace)this.allowedPriorLocks.get(var1);
            if (var3 != null) {
               return var3;
            } else {
               Iterator var4 = this.allowedPriorLocks.entrySet().iterator();

               CycleDetectingLockFactory.LockGraphNode var5;
               CycleDetectingLockFactory.ExampleStackTrace var6;
               Entry var8;
               do {
                  if (!var4.hasNext()) {
                     return null;
                  }

                  var8 = (Entry)var4.next();
                  var5 = (CycleDetectingLockFactory.LockGraphNode)var8.getKey();
                  var6 = var5.findPathTo(var1, var2);
               } while(var6 == null);

               CycleDetectingLockFactory.ExampleStackTrace var7 = new CycleDetectingLockFactory.ExampleStackTrace(var5, this);
               var7.setStackTrace(((CycleDetectingLockFactory.ExampleStackTrace)var8.getValue()).getStackTrace());
               var7.initCause(var6);
               return var7;
            }
         }
      }

      void checkAcquiredLock(CycleDetectingLockFactory.Policy var1, CycleDetectingLockFactory.LockGraphNode var2) {
         boolean var3;
         if (this != var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "Attempted to acquire multiple locks with the same rank %s", (Object)var2.getLockName());
         if (!this.allowedPriorLocks.containsKey(var2)) {
            CycleDetectingLockFactory.PotentialDeadlockException var4 = (CycleDetectingLockFactory.PotentialDeadlockException)this.disallowedPriorLocks.get(var2);
            if (var4 != null) {
               var1.handlePotentialDeadlock(new CycleDetectingLockFactory.PotentialDeadlockException(var2, this, var4.getConflictingStackTrace()));
            } else {
               CycleDetectingLockFactory.ExampleStackTrace var5 = var2.findPathTo(this, Sets.newIdentityHashSet());
               if (var5 == null) {
                  this.allowedPriorLocks.put(var2, new CycleDetectingLockFactory.ExampleStackTrace(var2, this));
               } else {
                  var4 = new CycleDetectingLockFactory.PotentialDeadlockException(var2, this, var5);
                  this.disallowedPriorLocks.put(var2, var4);
                  var1.handlePotentialDeadlock(var4);
               }

            }
         }
      }

      void checkAcquiredLocks(CycleDetectingLockFactory.Policy var1, List<CycleDetectingLockFactory.LockGraphNode> var2) {
         int var3 = var2.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            this.checkAcquiredLock(var1, (CycleDetectingLockFactory.LockGraphNode)var2.get(var4));
         }

      }

      String getLockName() {
         return this.lockName;
      }
   }

   public static enum Policies implements CycleDetectingLockFactory.Policy {
      DISABLED,
      THROW {
         public void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException var1) {
            throw var1;
         }
      },
      WARN {
         public void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException var1) {
            CycleDetectingLockFactory.logger.log(Level.SEVERE, "Detected potential deadlock", var1);
         }
      };

      static {
         CycleDetectingLockFactory.Policies var0 = new CycleDetectingLockFactory.Policies("DISABLED", 2) {
            public void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException var1) {
            }
         };
         DISABLED = var0;
      }

      private Policies() {
      }

      // $FF: synthetic method
      Policies(Object var3) {
         this();
      }
   }

   public interface Policy {
      void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException var1);
   }

   public static final class PotentialDeadlockException extends CycleDetectingLockFactory.ExampleStackTrace {
      private final CycleDetectingLockFactory.ExampleStackTrace conflictingStackTrace;

      private PotentialDeadlockException(CycleDetectingLockFactory.LockGraphNode var1, CycleDetectingLockFactory.LockGraphNode var2, CycleDetectingLockFactory.ExampleStackTrace var3) {
         super(var1, var2);
         this.conflictingStackTrace = var3;
         this.initCause(var3);
      }

      // $FF: synthetic method
      PotentialDeadlockException(CycleDetectingLockFactory.LockGraphNode var1, CycleDetectingLockFactory.LockGraphNode var2, CycleDetectingLockFactory.ExampleStackTrace var3, Object var4) {
         this(var1, var2, var3);
      }

      public CycleDetectingLockFactory.ExampleStackTrace getConflictingStackTrace() {
         return this.conflictingStackTrace;
      }

      public String getMessage() {
         StringBuilder var1 = new StringBuilder(super.getMessage());

         for(Object var2 = this.conflictingStackTrace; var2 != null; var2 = ((Throwable)var2).getCause()) {
            var1.append(", ");
            var1.append(((Throwable)var2).getMessage());
         }

         return var1.toString();
      }
   }

   public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
      private final Map<E, CycleDetectingLockFactory.LockGraphNode> lockGraphNodes;

      WithExplicitOrdering(CycleDetectingLockFactory.Policy var1, Map<E, CycleDetectingLockFactory.LockGraphNode> var2) {
         super(var1, null);
         this.lockGraphNodes = var2;
      }

      public ReentrantLock newReentrantLock(E var1) {
         return this.newReentrantLock(var1, false);
      }

      public ReentrantLock newReentrantLock(E var1, boolean var2) {
         Object var3;
         if (this.policy == CycleDetectingLockFactory.Policies.DISABLED) {
            var3 = new ReentrantLock(var2);
         } else {
            var3 = new CycleDetectingLockFactory.CycleDetectingReentrantLock((CycleDetectingLockFactory.LockGraphNode)this.lockGraphNodes.get(var1), var2);
         }

         return (ReentrantLock)var3;
      }

      public ReentrantReadWriteLock newReentrantReadWriteLock(E var1) {
         return this.newReentrantReadWriteLock(var1, false);
      }

      public ReentrantReadWriteLock newReentrantReadWriteLock(E var1, boolean var2) {
         Object var3;
         if (this.policy == CycleDetectingLockFactory.Policies.DISABLED) {
            var3 = new ReentrantReadWriteLock(var2);
         } else {
            var3 = new CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock((CycleDetectingLockFactory.LockGraphNode)this.lockGraphNodes.get(var1), var2);
         }

         return (ReentrantReadWriteLock)var3;
      }
   }
}
