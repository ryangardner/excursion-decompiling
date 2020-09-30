/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver;

final class Pools {
    private static final boolean DEBUG = false;

    private Pools() {
    }

    static interface Pool<T> {
        public T acquire();

        public boolean release(T var1);

        public void releaseAll(T[] var1, int var2);
    }

    static class SimplePool<T>
    implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        SimplePool(int n) {
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
            int n = this.mPoolSize;
            Object[] arrobject = this.mPool;
            if (n >= arrobject.length) return false;
            arrobject[n] = t;
            this.mPoolSize = n + 1;
            return true;
        }

        @Override
        public void releaseAll(T[] arrT, int n) {
            int n2 = n;
            if (n > arrT.length) {
                n2 = arrT.length;
            }
            n = 0;
            while (n < n2) {
                T t = arrT[n];
                int n3 = this.mPoolSize;
                Object[] arrobject = this.mPool;
                if (n3 < arrobject.length) {
                    arrobject[n3] = t;
                    this.mPoolSize = n3 + 1;
                }
                ++n;
            }
        }
    }

}

