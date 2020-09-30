/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class CycleDetectingLockFactory {
    private static final ThreadLocal<ArrayList<LockGraphNode>> acquiredLocks;
    private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, LockGraphNode>> lockGraphNodesPerType;
    private static final Logger logger;
    final Policy policy;

    static {
        lockGraphNodesPerType = new MapMaker().weakKeys().makeMap();
        logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
        acquiredLocks = new ThreadLocal<ArrayList<LockGraphNode>>(){

            @Override
            protected ArrayList<LockGraphNode> initialValue() {
                return Lists.newArrayListWithCapacity(3);
            }
        };
    }

    private CycleDetectingLockFactory(Policy policy) {
        this.policy = Preconditions.checkNotNull(policy);
    }

    private void aboutToAcquire(CycleDetectingLock object) {
        if (object.isAcquiredByCurrentThread()) return;
        ArrayList<LockGraphNode> arrayList = acquiredLocks.get();
        object = object.getLockGraphNode();
        ((LockGraphNode)object).checkAcquiredLocks(this.policy, arrayList);
        arrayList.add((LockGraphNode)object);
    }

    static <E extends Enum<E>> Map<E, LockGraphNode> createNodes(Class<E> serializable) {
        LockGraphNode lockGraphNode;
        int n;
        EnumMap<E, LockGraphNode> enumMap = Maps.newEnumMap(serializable);
        Object object = (Enum[])((Class)serializable).getEnumConstants();
        int n2 = ((Enum[])object).length;
        serializable = Lists.newArrayListWithCapacity(n2);
        int n3 = ((Enum[])object).length;
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            Enum enum_ = object[n];
            lockGraphNode = new LockGraphNode(CycleDetectingLockFactory.getLockName(enum_));
            ((ArrayList)serializable).add(lockGraphNode);
            enumMap.put(enum_, lockGraphNode);
        }
        n3 = 1;
        do {
            n = n4;
            if (n3 >= n2) {
                while (n < n2 - 1) {
                    lockGraphNode = (LockGraphNode)((ArrayList)serializable).get(n);
                    object = Policies.DISABLED;
                    lockGraphNode.checkAcquiredLocks((Policy)object, ((ArrayList)serializable).subList(++n, n2));
                }
                return Collections.unmodifiableMap(enumMap);
            }
            ((LockGraphNode)((ArrayList)serializable).get(n3)).checkAcquiredLocks(Policies.THROW, ((ArrayList)serializable).subList(0, n3));
            ++n3;
        } while (true);
    }

    private static String getLockName(Enum<?> enum_) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(enum_.getDeclaringClass().getSimpleName());
        stringBuilder.append(".");
        stringBuilder.append(enum_.name());
        return stringBuilder.toString();
    }

    private static Map<? extends Enum, LockGraphNode> getOrCreateNodes(Class<? extends Enum> class_) {
        Map<? extends Enum, LockGraphNode> map = (Map<? extends Enum, LockGraphNode>)lockGraphNodesPerType.get(class_);
        if (map != null) {
            return map;
        }
        map = CycleDetectingLockFactory.createNodes(class_);
        return MoreObjects.firstNonNull(lockGraphNodesPerType.putIfAbsent(class_, map), map);
    }

    private static void lockStateChanged(CycleDetectingLock object) {
        if (object.isAcquiredByCurrentThread()) return;
        ArrayList<LockGraphNode> arrayList = acquiredLocks.get();
        object = object.getLockGraphNode();
        int n = arrayList.size() - 1;
        while (n >= 0) {
            if (arrayList.get(n) == object) {
                arrayList.remove(n);
                return;
            }
            --n;
        }
    }

    public static CycleDetectingLockFactory newInstance(Policy policy) {
        return new CycleDetectingLockFactory(policy);
    }

    public static <E extends Enum<E>> WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> class_, Policy policy) {
        Preconditions.checkNotNull(class_);
        Preconditions.checkNotNull(policy);
        return new WithExplicitOrdering<Enum>(policy, CycleDetectingLockFactory.getOrCreateNodes(class_));
    }

    public ReentrantLock newReentrantLock(String string2) {
        return this.newReentrantLock(string2, false);
    }

    public ReentrantLock newReentrantLock(String object, boolean bl) {
        if (this.policy != Policies.DISABLED) return new CycleDetectingReentrantLock(new LockGraphNode((String)object), bl);
        return new ReentrantLock(bl);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String string2) {
        return this.newReentrantReadWriteLock(string2, false);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String object, boolean bl) {
        if (this.policy != Policies.DISABLED) return new CycleDetectingReentrantReadWriteLock(new LockGraphNode((String)object), bl);
        return new ReentrantReadWriteLock(bl);
    }

    private static interface CycleDetectingLock {
        public LockGraphNode getLockGraphNode();

        public boolean isAcquiredByCurrentThread();
    }

    final class CycleDetectingReentrantLock
    extends ReentrantLock
    implements CycleDetectingLock {
        private final LockGraphNode lockGraphNode;

        private CycleDetectingReentrantLock(LockGraphNode lockGraphNode, boolean bl) {
            super(bl);
            this.lockGraphNode = Preconditions.checkNotNull(lockGraphNode);
        }

        @Override
        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        @Override
        public boolean isAcquiredByCurrentThread() {
            return this.isHeldByCurrentThread();
        }

        @Override
        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lock();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lockInterruptibly();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override
        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                boolean bl = super.tryLock();
                return bl;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                boolean bl = super.tryLock(l, timeUnit);
                return bl;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        @Override
        public void unlock() {
            try {
                super.unlock();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }
    }

    private class CycleDetectingReentrantReadLock
    extends ReentrantReadWriteLock.ReadLock {
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantReadLock(CycleDetectingReentrantReadWriteLock cycleDetectingReentrantReadWriteLock) {
            super(cycleDetectingReentrantReadWriteLock);
            this.readWriteLock = cycleDetectingReentrantReadWriteLock;
        }

        @Override
        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean bl = super.tryLock();
                return bl;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean bl = super.tryLock(l, timeUnit);
                return bl;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public void unlock() {
            try {
                super.unlock();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }
    }

    final class CycleDetectingReentrantReadWriteLock
    extends ReentrantReadWriteLock
    implements CycleDetectingLock {
        private final LockGraphNode lockGraphNode;
        private final CycleDetectingReentrantReadLock readLock;
        private final CycleDetectingReentrantWriteLock writeLock;

        private CycleDetectingReentrantReadWriteLock(LockGraphNode lockGraphNode, boolean bl) {
            super(bl);
            this.readLock = new CycleDetectingReentrantReadLock(this);
            this.writeLock = new CycleDetectingReentrantWriteLock(this);
            this.lockGraphNode = Preconditions.checkNotNull(lockGraphNode);
        }

        @Override
        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        @Override
        public boolean isAcquiredByCurrentThread() {
            if (this.isWriteLockedByCurrentThread()) return true;
            if (this.getReadHoldCount() > 0) return true;
            return false;
        }

        @Override
        public ReentrantReadWriteLock.ReadLock readLock() {
            return this.readLock;
        }

        @Override
        public ReentrantReadWriteLock.WriteLock writeLock() {
            return this.writeLock;
        }
    }

    private class CycleDetectingReentrantWriteLock
    extends ReentrantReadWriteLock.WriteLock {
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantWriteLock(CycleDetectingReentrantReadWriteLock cycleDetectingReentrantReadWriteLock) {
            super(cycleDetectingReentrantReadWriteLock);
            this.readWriteLock = cycleDetectingReentrantReadWriteLock;
        }

        @Override
        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean bl = super.tryLock();
                return bl;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                boolean bl = super.tryLock(l, timeUnit);
                return bl;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        @Override
        public void unlock() {
            try {
                super.unlock();
                return;
            }
            finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }
    }

    private static class ExampleStackTrace
    extends IllegalStateException {
        static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
        static final ImmutableSet<String> EXCLUDED_CLASS_NAMES = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), LockGraphNode.class.getName());

        ExampleStackTrace(LockGraphNode arrstackTraceElement, LockGraphNode lockGraphNode) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(arrstackTraceElement.getLockName());
            stringBuilder.append(" -> ");
            stringBuilder.append(lockGraphNode.getLockName());
            super(stringBuilder.toString());
            arrstackTraceElement = this.getStackTrace();
            int n = arrstackTraceElement.length;
            int n2 = 0;
            while (n2 < n) {
                if (WithExplicitOrdering.class.getName().equals(arrstackTraceElement[n2].getClassName())) {
                    this.setStackTrace(EMPTY_STACK_TRACE);
                    return;
                }
                if (!EXCLUDED_CLASS_NAMES.contains(arrstackTraceElement[n2].getClassName())) {
                    this.setStackTrace(Arrays.copyOfRange(arrstackTraceElement, n2, n));
                    return;
                }
                ++n2;
            }
        }
    }

    private static class LockGraphNode {
        final Map<LockGraphNode, ExampleStackTrace> allowedPriorLocks = new MapMaker().weakKeys().makeMap();
        final Map<LockGraphNode, PotentialDeadlockException> disallowedPriorLocks = new MapMaker().weakKeys().makeMap();
        final String lockName;

        LockGraphNode(String string2) {
            this.lockName = Preconditions.checkNotNull(string2);
        }

        @NullableDecl
        private ExampleStackTrace findPathTo(LockGraphNode object, Set<LockGraphNode> set) {
            ExampleStackTrace exampleStackTrace;
            LockGraphNode lockGraphNode;
            if (!set.add(this)) {
                return null;
            }
            Object object2 = this.allowedPriorLocks.get(object);
            if (object2 != null) {
                return object2;
            }
            Iterator<Map.Entry<LockGraphNode, ExampleStackTrace>> iterator2 = this.allowedPriorLocks.entrySet().iterator();
            do {
                if (!iterator2.hasNext()) return null;
            } while ((exampleStackTrace = (lockGraphNode = (LockGraphNode)(object2 = iterator2.next()).getKey()).findPathTo((LockGraphNode)object, set)) == null);
            object = new ExampleStackTrace(lockGraphNode, this);
            ((Throwable)object).setStackTrace(((ExampleStackTrace)object2.getValue()).getStackTrace());
            ((Throwable)object).initCause(exampleStackTrace);
            return object;
        }

        void checkAcquiredLock(Policy policy, LockGraphNode lockGraphNode) {
            boolean bl = this != lockGraphNode;
            Preconditions.checkState(bl, "Attempted to acquire multiple locks with the same rank %s", (Object)lockGraphNode.getLockName());
            if (this.allowedPriorLocks.containsKey(lockGraphNode)) {
                return;
            }
            ExampleStackTrace exampleStackTrace = this.disallowedPriorLocks.get(lockGraphNode);
            if (exampleStackTrace != null) {
                policy.handlePotentialDeadlock(new PotentialDeadlockException(lockGraphNode, this, ((PotentialDeadlockException)exampleStackTrace).getConflictingStackTrace()));
                return;
            }
            exampleStackTrace = lockGraphNode.findPathTo(this, Sets.<LockGraphNode>newIdentityHashSet());
            if (exampleStackTrace == null) {
                this.allowedPriorLocks.put(lockGraphNode, new ExampleStackTrace(lockGraphNode, this));
                return;
            }
            exampleStackTrace = new PotentialDeadlockException(lockGraphNode, this, exampleStackTrace);
            this.disallowedPriorLocks.put(lockGraphNode, (PotentialDeadlockException)exampleStackTrace);
            policy.handlePotentialDeadlock((PotentialDeadlockException)exampleStackTrace);
        }

        void checkAcquiredLocks(Policy policy, List<LockGraphNode> list) {
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                this.checkAcquiredLock(policy, list.get(n2));
                ++n2;
            }
        }

        String getLockName() {
            return this.lockName;
        }
    }

    public static abstract class Policies
    extends Enum<Policies>
    implements Policy {
        private static final /* synthetic */ Policies[] $VALUES;
        public static final /* enum */ Policies DISABLED;
        public static final /* enum */ Policies THROW;
        public static final /* enum */ Policies WARN;

        static {
            Policies policies;
            THROW = new Policies(){

                @Override
                public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                    throw potentialDeadlockException;
                }
            };
            WARN = new Policies(){

                @Override
                public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                    logger.log(Level.SEVERE, "Detected potential deadlock", potentialDeadlockException);
                }
            };
            DISABLED = policies = new Policies(){

                @Override
                public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                }
            };
            $VALUES = new Policies[]{THROW, WARN, policies};
        }

        public static Policies valueOf(String string2) {
            return Enum.valueOf(Policies.class, string2);
        }

        public static Policies[] values() {
            return (Policies[])$VALUES.clone();
        }

    }

    public static interface Policy {
        public void handlePotentialDeadlock(PotentialDeadlockException var1);
    }

    public static final class PotentialDeadlockException
    extends ExampleStackTrace {
        private final ExampleStackTrace conflictingStackTrace;

        private PotentialDeadlockException(LockGraphNode lockGraphNode, LockGraphNode lockGraphNode2, ExampleStackTrace exampleStackTrace) {
            super(lockGraphNode, lockGraphNode2);
            this.conflictingStackTrace = exampleStackTrace;
            this.initCause(exampleStackTrace);
        }

        public ExampleStackTrace getConflictingStackTrace() {
            return this.conflictingStackTrace;
        }

        @Override
        public String getMessage() {
            StringBuilder stringBuilder = new StringBuilder(super.getMessage());
            Throwable throwable = this.conflictingStackTrace;
            while (throwable != null) {
                stringBuilder.append(", ");
                stringBuilder.append(throwable.getMessage());
                throwable = throwable.getCause();
            }
            return stringBuilder.toString();
        }
    }

    public static final class WithExplicitOrdering<E extends Enum<E>>
    extends CycleDetectingLockFactory {
        private final Map<E, LockGraphNode> lockGraphNodes;

        WithExplicitOrdering(Policy policy, Map<E, LockGraphNode> map) {
            super(policy);
            this.lockGraphNodes = map;
        }

        public ReentrantLock newReentrantLock(E e) {
            return this.newReentrantLock(e, false);
        }

        public ReentrantLock newReentrantLock(E object, boolean bl) {
            if (this.policy != Policies.DISABLED) return new CycleDetectingReentrantLock(this.lockGraphNodes.get(object), bl);
            return new ReentrantLock(bl);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e) {
            return this.newReentrantReadWriteLock(e, false);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E object, boolean bl) {
            if (this.policy != Policies.DISABLED) return new CycleDetectingReentrantReadWriteLock(this.lockGraphNodes.get(object), bl);
            return new ReentrantReadWriteLock(bl);
        }
    }

}

