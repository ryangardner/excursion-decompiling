/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class CacheStats {
    private final long evictionCount;
    private final long hitCount;
    private final long loadExceptionCount;
    private final long loadSuccessCount;
    private final long missCount;
    private final long totalLoadTime;

    public CacheStats(long l, long l2, long l3, long l4, long l5, long l6) {
        boolean bl = true;
        boolean bl2 = l >= 0L;
        Preconditions.checkArgument(bl2);
        bl2 = l2 >= 0L;
        Preconditions.checkArgument(bl2);
        bl2 = l3 >= 0L;
        Preconditions.checkArgument(bl2);
        bl2 = l4 >= 0L;
        Preconditions.checkArgument(bl2);
        bl2 = l5 >= 0L;
        Preconditions.checkArgument(bl2);
        bl2 = l6 >= 0L ? bl : false;
        Preconditions.checkArgument(bl2);
        this.hitCount = l;
        this.missCount = l2;
        this.loadSuccessCount = l3;
        this.loadExceptionCount = l4;
        this.totalLoadTime = l5;
        this.evictionCount = l6;
    }

    public double averageLoadPenalty() {
        long l = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
        if (l != 0L) return (double)this.totalLoadTime / (double)l;
        return 0.0;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof CacheStats;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (CacheStats)object;
        bl3 = bl;
        if (this.hitCount != ((CacheStats)object).hitCount) return bl3;
        bl3 = bl;
        if (this.missCount != ((CacheStats)object).missCount) return bl3;
        bl3 = bl;
        if (this.loadSuccessCount != ((CacheStats)object).loadSuccessCount) return bl3;
        bl3 = bl;
        if (this.loadExceptionCount != ((CacheStats)object).loadExceptionCount) return bl3;
        bl3 = bl;
        if (this.totalLoadTime != ((CacheStats)object).totalLoadTime) return bl3;
        bl3 = bl;
        if (this.evictionCount != ((CacheStats)object).evictionCount) return bl3;
        return true;
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public int hashCode() {
        return Objects.hashCode(this.hitCount, this.missCount, this.loadSuccessCount, this.loadExceptionCount, this.totalLoadTime, this.evictionCount);
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long l = this.requestCount();
        if (l != 0L) return (double)this.hitCount / (double)l;
        return 1.0;
    }

    public long loadCount() {
        return LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
    }

    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }

    public double loadExceptionRate() {
        long l = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
        if (l != 0L) return (double)this.loadExceptionCount / (double)l;
        return 0.0;
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public CacheStats minus(CacheStats cacheStats) {
        return new CacheStats(Math.max(0L, LongMath.saturatedSubtract(this.hitCount, cacheStats.hitCount)), Math.max(0L, LongMath.saturatedSubtract(this.missCount, cacheStats.missCount)), Math.max(0L, LongMath.saturatedSubtract(this.loadSuccessCount, cacheStats.loadSuccessCount)), Math.max(0L, LongMath.saturatedSubtract(this.loadExceptionCount, cacheStats.loadExceptionCount)), Math.max(0L, LongMath.saturatedSubtract(this.totalLoadTime, cacheStats.totalLoadTime)), Math.max(0L, LongMath.saturatedSubtract(this.evictionCount, cacheStats.evictionCount)));
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long l = this.requestCount();
        if (l != 0L) return (double)this.missCount / (double)l;
        return 0.0;
    }

    public CacheStats plus(CacheStats cacheStats) {
        return new CacheStats(LongMath.saturatedAdd(this.hitCount, cacheStats.hitCount), LongMath.saturatedAdd(this.missCount, cacheStats.missCount), LongMath.saturatedAdd(this.loadSuccessCount, cacheStats.loadSuccessCount), LongMath.saturatedAdd(this.loadExceptionCount, cacheStats.loadExceptionCount), LongMath.saturatedAdd(this.totalLoadTime, cacheStats.totalLoadTime), LongMath.saturatedAdd(this.evictionCount, cacheStats.evictionCount));
    }

    public long requestCount() {
        return LongMath.saturatedAdd(this.hitCount, this.missCount);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }
}

