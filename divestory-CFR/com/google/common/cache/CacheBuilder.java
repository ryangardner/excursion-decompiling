/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.LocalCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public final class CacheBuilder<K, V> {
    static final Supplier<AbstractCache.StatsCounter> CACHE_STATS_COUNTER;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_REFRESH_NANOS = 0;
    static final CacheStats EMPTY_STATS;
    static final Supplier<? extends AbstractCache.StatsCounter> NULL_STATS_COUNTER;
    static final Ticker NULL_TICKER;
    static final int UNSET_INT = -1;
    private static final Logger logger;
    int concurrencyLevel = -1;
    long expireAfterAccessNanos = -1L;
    long expireAfterWriteNanos = -1L;
    int initialCapacity = -1;
    @MonotonicNonNullDecl
    Equivalence<Object> keyEquivalence;
    @MonotonicNonNullDecl
    LocalCache.Strength keyStrength;
    long maximumSize = -1L;
    long maximumWeight = -1L;
    long refreshNanos = -1L;
    @MonotonicNonNullDecl
    RemovalListener<? super K, ? super V> removalListener;
    Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier = NULL_STATS_COUNTER;
    boolean strictParsing = true;
    @MonotonicNonNullDecl
    Ticker ticker;
    @MonotonicNonNullDecl
    Equivalence<Object> valueEquivalence;
    @MonotonicNonNullDecl
    LocalCache.Strength valueStrength;
    @MonotonicNonNullDecl
    Weigher<? super K, ? super V> weigher;

    static {
        NULL_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter(){

            @Override
            public void recordEviction() {
            }

            @Override
            public void recordHits(int n) {
            }

            @Override
            public void recordLoadException(long l) {
            }

            @Override
            public void recordLoadSuccess(long l) {
            }

            @Override
            public void recordMisses(int n) {
            }

            @Override
            public CacheStats snapshot() {
                return EMPTY_STATS;
            }
        });
        EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
        CACHE_STATS_COUNTER = new Supplier<AbstractCache.StatsCounter>(){

            @Override
            public AbstractCache.StatsCounter get() {
                return new AbstractCache.SimpleStatsCounter();
            }
        };
        NULL_TICKER = new Ticker(){

            @Override
            public long read() {
                return 0L;
            }
        };
        logger = Logger.getLogger(CacheBuilder.class.getName());
    }

    private CacheBuilder() {
    }

    private void checkNonLoadingCache() {
        boolean bl = this.refreshNanos == -1L;
        Preconditions.checkState(bl, "refreshAfterWrite requires a LoadingCache");
    }

    private void checkWeightWithWeigher() {
        Weigher<? super K, ? super V> weigher = this.weigher;
        boolean bl = true;
        boolean bl2 = true;
        if (weigher == null) {
            if (this.maximumWeight != -1L) {
                bl2 = false;
            }
            Preconditions.checkState(bl2, "maximumWeight requires weigher");
            return;
        }
        if (!this.strictParsing) {
            if (this.maximumWeight != -1L) return;
            logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
            return;
        }
        bl2 = this.maximumWeight != -1L ? bl : false;
        Preconditions.checkState(bl2, "weigher requires maximumWeight");
    }

    public static CacheBuilder<Object, Object> from(CacheBuilderSpec cacheBuilderSpec) {
        return cacheBuilderSpec.toCacheBuilder().lenientParsing();
    }

    public static CacheBuilder<Object, Object> from(String string2) {
        return CacheBuilder.from(CacheBuilderSpec.parse(string2));
    }

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<Object, Object>();
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        this.checkWeightWithWeigher();
        this.checkNonLoadingCache();
        return new LocalCache.LocalManualCache(this);
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> cacheLoader) {
        this.checkWeightWithWeigher();
        return new LocalCache.LocalLoadingCache<K1, V1>(this, cacheLoader);
    }

    public CacheBuilder<K, V> concurrencyLevel(int n) {
        int n2 = this.concurrencyLevel;
        boolean bl = true;
        boolean bl2 = n2 == -1;
        Preconditions.checkState(bl2, "concurrency level was already set to %s", this.concurrencyLevel);
        bl2 = n > 0 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.concurrencyLevel = n;
        return this;
    }

    public CacheBuilder<K, V> expireAfterAccess(long l, TimeUnit timeUnit) {
        long l2 = this.expireAfterAccessNanos;
        boolean bl = true;
        boolean bl2 = l2 == -1L;
        Preconditions.checkState(bl2, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        bl2 = l >= 0L ? bl : false;
        Preconditions.checkArgument(bl2, "duration cannot be negative: %s %s", l, (Object)timeUnit);
        this.expireAfterAccessNanos = timeUnit.toNanos(l);
        return this;
    }

    public CacheBuilder<K, V> expireAfterWrite(long l, TimeUnit timeUnit) {
        long l2 = this.expireAfterWriteNanos;
        boolean bl = true;
        boolean bl2 = l2 == -1L;
        Preconditions.checkState(bl2, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        bl2 = l >= 0L ? bl : false;
        Preconditions.checkArgument(bl2, "duration cannot be negative: %s %s", l, (Object)timeUnit);
        this.expireAfterWriteNanos = timeUnit.toNanos(l);
        return this;
    }

    int getConcurrencyLevel() {
        int n;
        int n2 = n = this.concurrencyLevel;
        if (n != -1) return n2;
        return 4;
    }

    long getExpireAfterAccessNanos() {
        long l;
        long l2 = l = this.expireAfterAccessNanos;
        if (l != -1L) return l2;
        return 0L;
    }

    long getExpireAfterWriteNanos() {
        long l;
        long l2 = l = this.expireAfterWriteNanos;
        if (l != -1L) return l2;
        return 0L;
    }

    int getInitialCapacity() {
        int n;
        int n2 = n = this.initialCapacity;
        if (n != -1) return n2;
        return 16;
    }

    Equivalence<Object> getKeyEquivalence() {
        return MoreObjects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
    }

    LocalCache.Strength getKeyStrength() {
        return MoreObjects.firstNonNull(this.keyStrength, LocalCache.Strength.STRONG);
    }

    long getMaximumWeight() {
        if (this.expireAfterWriteNanos == 0L) return 0L;
        if (this.expireAfterAccessNanos == 0L) {
            return 0L;
        }
        if (this.weigher != null) return this.maximumWeight;
        return this.maximumSize;
    }

    long getRefreshNanos() {
        long l;
        long l2 = l = this.refreshNanos;
        if (l != -1L) return l2;
        return 0L;
    }

    <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
        return MoreObjects.firstNonNull(this.removalListener, NullListener.INSTANCE);
    }

    Supplier<? extends AbstractCache.StatsCounter> getStatsCounterSupplier() {
        return this.statsCounterSupplier;
    }

    Ticker getTicker(boolean bl) {
        Ticker ticker = this.ticker;
        if (ticker != null) {
            return ticker;
        }
        if (!bl) return NULL_TICKER;
        return Ticker.systemTicker();
    }

    Equivalence<Object> getValueEquivalence() {
        return MoreObjects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
    }

    LocalCache.Strength getValueStrength() {
        return MoreObjects.firstNonNull(this.valueStrength, LocalCache.Strength.STRONG);
    }

    <K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher() {
        return MoreObjects.firstNonNull(this.weigher, OneWeigher.INSTANCE);
    }

    public CacheBuilder<K, V> initialCapacity(int n) {
        int n2 = this.initialCapacity;
        boolean bl = true;
        boolean bl2 = n2 == -1;
        Preconditions.checkState(bl2, "initial capacity was already set to %s", this.initialCapacity);
        bl2 = n >= 0 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.initialCapacity = n;
        return this;
    }

    boolean isRecordingStats() {
        if (this.statsCounterSupplier != CACHE_STATS_COUNTER) return false;
        return true;
    }

    CacheBuilder<K, V> keyEquivalence(Equivalence<Object> equivalence) {
        boolean bl = this.keyEquivalence == null;
        Preconditions.checkState(bl, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = Preconditions.checkNotNull(equivalence);
        return this;
    }

    CacheBuilder<K, V> lenientParsing() {
        this.strictParsing = false;
        return this;
    }

    public CacheBuilder<K, V> maximumSize(long l) {
        long l2 = this.maximumSize;
        boolean bl = true;
        boolean bl2 = l2 == -1L;
        Preconditions.checkState(bl2, "maximum size was already set to %s", this.maximumSize);
        bl2 = this.maximumWeight == -1L;
        Preconditions.checkState(bl2, "maximum weight was already set to %s", this.maximumWeight);
        bl2 = this.weigher == null;
        Preconditions.checkState(bl2, "maximum size can not be combined with weigher");
        bl2 = l >= 0L ? bl : false;
        Preconditions.checkArgument(bl2, "maximum size must not be negative");
        this.maximumSize = l;
        return this;
    }

    public CacheBuilder<K, V> maximumWeight(long l) {
        long l2 = this.maximumWeight;
        boolean bl = true;
        boolean bl2 = l2 == -1L;
        Preconditions.checkState(bl2, "maximum weight was already set to %s", this.maximumWeight);
        bl2 = this.maximumSize == -1L;
        Preconditions.checkState(bl2, "maximum size was already set to %s", this.maximumSize);
        this.maximumWeight = l;
        bl2 = l >= 0L ? bl : false;
        Preconditions.checkArgument(bl2, "maximum weight must not be negative");
        return this;
    }

    public CacheBuilder<K, V> recordStats() {
        this.statsCounterSupplier = CACHE_STATS_COUNTER;
        return this;
    }

    public CacheBuilder<K, V> refreshAfterWrite(long l, TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        long l2 = this.refreshNanos;
        boolean bl = true;
        boolean bl2 = l2 == -1L;
        Preconditions.checkState(bl2, "refresh was already set to %s ns", this.refreshNanos);
        bl2 = l > 0L ? bl : false;
        Preconditions.checkArgument(bl2, "duration must be positive: %s %s", l, (Object)timeUnit);
        this.refreshNanos = timeUnit.toNanos(l);
        return this;
    }

    @CheckReturnValue
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener) {
        boolean bl = this.removalListener == null;
        Preconditions.checkState(bl);
        this.removalListener = Preconditions.checkNotNull(removalListener);
        return this;
    }

    CacheBuilder<K, V> setKeyStrength(LocalCache.Strength strength) {
        boolean bl = this.keyStrength == null;
        Preconditions.checkState(bl, "Key strength was already set to %s", (Object)this.keyStrength);
        this.keyStrength = Preconditions.checkNotNull(strength);
        return this;
    }

    CacheBuilder<K, V> setValueStrength(LocalCache.Strength strength) {
        boolean bl = this.valueStrength == null;
        Preconditions.checkState(bl, "Value strength was already set to %s", (Object)this.valueStrength);
        this.valueStrength = Preconditions.checkNotNull(strength);
        return this;
    }

    public CacheBuilder<K, V> softValues() {
        return this.setValueStrength(LocalCache.Strength.SOFT);
    }

    public CacheBuilder<K, V> ticker(Ticker ticker) {
        boolean bl = this.ticker == null;
        Preconditions.checkState(bl);
        this.ticker = Preconditions.checkNotNull(ticker);
        return this;
    }

    public String toString() {
        Object object;
        long l;
        MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
        int n = this.initialCapacity;
        if (n != -1) {
            toStringHelper.add("initialCapacity", n);
        }
        if ((n = this.concurrencyLevel) != -1) {
            toStringHelper.add("concurrencyLevel", n);
        }
        if ((l = this.maximumSize) != -1L) {
            toStringHelper.add("maximumSize", l);
        }
        if ((l = this.maximumWeight) != -1L) {
            toStringHelper.add("maximumWeight", l);
        }
        if (this.expireAfterWriteNanos != -1L) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.expireAfterWriteNanos);
            ((StringBuilder)object).append("ns");
            toStringHelper.add("expireAfterWrite", ((StringBuilder)object).toString());
        }
        if (this.expireAfterAccessNanos != -1L) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.expireAfterAccessNanos);
            ((StringBuilder)object).append("ns");
            toStringHelper.add("expireAfterAccess", ((StringBuilder)object).toString());
        }
        if ((object = this.keyStrength) != null) {
            toStringHelper.add("keyStrength", Ascii.toLowerCase(((Enum)object).toString()));
        }
        if ((object = this.valueStrength) != null) {
            toStringHelper.add("valueStrength", Ascii.toLowerCase(((Enum)object).toString()));
        }
        if (this.keyEquivalence != null) {
            toStringHelper.addValue("keyEquivalence");
        }
        if (this.valueEquivalence != null) {
            toStringHelper.addValue("valueEquivalence");
        }
        if (this.removalListener == null) return toStringHelper.toString();
        toStringHelper.addValue("removalListener");
        return toStringHelper.toString();
    }

    CacheBuilder<K, V> valueEquivalence(Equivalence<Object> equivalence) {
        boolean bl = this.valueEquivalence == null;
        Preconditions.checkState(bl, "value equivalence was already set to %s", this.valueEquivalence);
        this.valueEquivalence = Preconditions.checkNotNull(equivalence);
        return this;
    }

    public CacheBuilder<K, V> weakKeys() {
        return this.setKeyStrength(LocalCache.Strength.WEAK);
    }

    public CacheBuilder<K, V> weakValues() {
        return this.setValueStrength(LocalCache.Strength.WEAK);
    }

    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
        Weigher<? super K, ? super V> weigher2 = this.weigher;
        boolean bl = true;
        boolean bl2 = weigher2 == null;
        Preconditions.checkState(bl2);
        if (this.strictParsing) {
            bl2 = this.maximumSize == -1L ? bl : false;
            Preconditions.checkState(bl2, "weigher can not be combined with maximum size", this.maximumSize);
        }
        this.weigher = Preconditions.checkNotNull(weigher);
        return this;
    }

    static final class NullListener
    extends Enum<NullListener>
    implements RemovalListener<Object, Object> {
        private static final /* synthetic */ NullListener[] $VALUES;
        public static final /* enum */ NullListener INSTANCE;

        static {
            NullListener nullListener;
            INSTANCE = nullListener = new NullListener();
            $VALUES = new NullListener[]{nullListener};
        }

        public static NullListener valueOf(String string2) {
            return Enum.valueOf(NullListener.class, string2);
        }

        public static NullListener[] values() {
            return (NullListener[])$VALUES.clone();
        }

        @Override
        public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
        }
    }

    static final class OneWeigher
    extends Enum<OneWeigher>
    implements Weigher<Object, Object> {
        private static final /* synthetic */ OneWeigher[] $VALUES;
        public static final /* enum */ OneWeigher INSTANCE;

        static {
            OneWeigher oneWeigher;
            INSTANCE = oneWeigher = new OneWeigher();
            $VALUES = new OneWeigher[]{oneWeigher};
        }

        public static OneWeigher valueOf(String string2) {
            return Enum.valueOf(OneWeigher.class, string2);
        }

        public static OneWeigher[] values() {
            return (OneWeigher[])$VALUES.clone();
        }

        @Override
        public int weigh(Object object, Object object2) {
            return 1;
        }
    }

}

