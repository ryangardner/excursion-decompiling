/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.util;

public final class Pools {
    private Pools() {
    }

    public static interface Pool<T> {
        public T acquire();

        public boolean release(T var1);
    }

    public static class SimplePool<T>
    implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        public SimplePool(int n) {
            if (n <= 0) throw new IllegalArgumentException("The max pool size must be > 0");
            this.mPool = new Object[n];
        }

        private boolean isInPool(T t) {
            int n = 0;
            while (n < this.mPoolSize) {
                if (this.mPool[n] == t) {
                    return true;
                }
                ++n;
            }
            return false;
        }

        @Override
        public T acquire() {
            int n = this.mPoolSize;
            if (n <= 0) return null;
            int n2 = n - 1;
            Object[] arrobject = this.mPool;
            Object object = arrobject[n2];
            arrobject[n2] = null;
            this.mPoolSize = n - 1;
            return (T)object;
        }

        @Override
        public boolean release(T t) {
            if (this.isInPool(t)) throw new IllegalStateException("Already in the pool!");
            int n = this.mPoolSize;
            Object[] arrobject = this.mPool;
            if (n >= arrobject.length) return false;
            arrobject[n] = t;
            this.mPoolSize = n + 1;
            return true;
        }
    }

    public static class SynchronizedPool<T>
    extends SimplePool<T> {
        private final Object mLock = new Object();

        public SynchronizedPool(int n) {
            super(n);
        }

        @Override
        public T acquire() {
            Object object = this.mLock;
            synchronized (object) {
                Object t = super.acquire();
                return t;
            }
        }

        @Override
        public boolean release(T t) {
            Object object = this.mLock;
            synchronized (object) {
                return super.release(t);
            }
        }
    }

}

