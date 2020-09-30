package com.google.common.cache;

import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public final class CacheBuilder<K, V> {
   static final Supplier<AbstractCache.StatsCounter> CACHE_STATS_COUNTER = new Supplier<AbstractCache.StatsCounter>() {
      public AbstractCache.StatsCounter get() {
         return new AbstractCache.SimpleStatsCounter();
      }
   };
   private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
   private static final int DEFAULT_EXPIRATION_NANOS = 0;
   private static final int DEFAULT_INITIAL_CAPACITY = 16;
   private static final int DEFAULT_REFRESH_NANOS = 0;
   static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
   static final Supplier<? extends AbstractCache.StatsCounter> NULL_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter() {
      public void recordEviction() {
      }

      public void recordHits(int var1) {
      }

      public void recordLoadException(long var1) {
      }

      public void recordLoadSuccess(long var1) {
      }

      public void recordMisses(int var1) {
      }

      public CacheStats snapshot() {
         return CacheBuilder.EMPTY_STATS;
      }
   });
   static final Ticker NULL_TICKER = new Ticker() {
      public long read() {
         return 0L;
      }
   };
   static final int UNSET_INT = -1;
   private static final Logger logger = Logger.getLogger(CacheBuilder.class.getName());
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
   Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier;
   boolean strictParsing = true;
   @MonotonicNonNullDecl
   Ticker ticker;
   @MonotonicNonNullDecl
   Equivalence<Object> valueEquivalence;
   @MonotonicNonNullDecl
   LocalCache.Strength valueStrength;
   @MonotonicNonNullDecl
   Weigher<? super K, ? super V> weigher;

   private CacheBuilder() {
      this.statsCounterSupplier = NULL_STATS_COUNTER;
   }

   private void checkNonLoadingCache() {
      boolean var1;
      if (this.refreshNanos == -1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkState(var1, "refreshAfterWrite requires a LoadingCache");
   }

   private void checkWeightWithWeigher() {
      Weigher var1 = this.weigher;
      boolean var2 = true;
      boolean var3 = true;
      if (var1 == null) {
         if (this.maximumWeight != -1L) {
            var3 = false;
         }

         Preconditions.checkState(var3, "maximumWeight requires weigher");
      } else if (this.strictParsing) {
         if (this.maximumWeight != -1L) {
            var3 = var2;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "weigher requires maximumWeight");
      } else if (this.maximumWeight == -1L) {
         logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
      }

   }

   public static CacheBuilder<Object, Object> from(CacheBuilderSpec var0) {
      return var0.toCacheBuilder().lenientParsing();
   }

   public static CacheBuilder<Object, Object> from(String var0) {
      return from(CacheBuilderSpec.parse(var0));
   }

   public static CacheBuilder<Object, Object> newBuilder() {
      return new CacheBuilder();
   }

   public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
      this.checkWeightWithWeigher();
      this.checkNonLoadingCache();
      return new LocalCache.LocalManualCache(this);
   }

   public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> var1) {
      this.checkWeightWithWeigher();
      return new LocalCache.LocalLoadingCache(this, var1);
   }

   public CacheBuilder<K, V> concurrencyLevel(int var1) {
      int var2 = this.concurrencyLevel;
      boolean var3 = true;
      boolean var4;
      if (var2 == -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4, "concurrency level was already set to %s", this.concurrencyLevel);
      if (var1 > 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.concurrencyLevel = var1;
      return this;
   }

   public CacheBuilder<K, V> expireAfterAccess(long var1, TimeUnit var3) {
      long var4 = this.expireAfterAccessNanos;
      boolean var6 = true;
      boolean var7;
      if (var4 == -1L) {
         var7 = true;
      } else {
         var7 = false;
      }

      Preconditions.checkState(var7, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
      if (var1 >= 0L) {
         var7 = var6;
      } else {
         var7 = false;
      }

      Preconditions.checkArgument(var7, "duration cannot be negative: %s %s", var1, var3);
      this.expireAfterAccessNanos = var3.toNanos(var1);
      return this;
   }

   public CacheBuilder<K, V> expireAfterWrite(long var1, TimeUnit var3) {
      long var4 = this.expireAfterWriteNanos;
      boolean var6 = true;
      boolean var7;
      if (var4 == -1L) {
         var7 = true;
      } else {
         var7 = false;
      }

      Preconditions.checkState(var7, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
      if (var1 >= 0L) {
         var7 = var6;
      } else {
         var7 = false;
      }

      Preconditions.checkArgument(var7, "duration cannot be negative: %s %s", var1, var3);
      this.expireAfterWriteNanos = var3.toNanos(var1);
      return this;
   }

   int getConcurrencyLevel() {
      int var1 = this.concurrencyLevel;
      int var2 = var1;
      if (var1 == -1) {
         var2 = 4;
      }

      return var2;
   }

   long getExpireAfterAccessNanos() {
      long var1 = this.expireAfterAccessNanos;
      long var3 = var1;
      if (var1 == -1L) {
         var3 = 0L;
      }

      return var3;
   }

   long getExpireAfterWriteNanos() {
      long var1 = this.expireAfterWriteNanos;
      long var3 = var1;
      if (var1 == -1L) {
         var3 = 0L;
      }

      return var3;
   }

   int getInitialCapacity() {
      int var1 = this.initialCapacity;
      int var2 = var1;
      if (var1 == -1) {
         var2 = 16;
      }

      return var2;
   }

   Equivalence<Object> getKeyEquivalence() {
      return (Equivalence)MoreObjects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
   }

   LocalCache.Strength getKeyStrength() {
      return (LocalCache.Strength)MoreObjects.firstNonNull(this.keyStrength, LocalCache.Strength.STRONG);
   }

   long getMaximumWeight() {
      if (this.expireAfterWriteNanos != 0L && this.expireAfterAccessNanos != 0L) {
         long var1;
         if (this.weigher == null) {
            var1 = this.maximumSize;
         } else {
            var1 = this.maximumWeight;
         }

         return var1;
      } else {
         return 0L;
      }
   }

   long getRefreshNanos() {
      long var1 = this.refreshNanos;
      long var3 = var1;
      if (var1 == -1L) {
         var3 = 0L;
      }

      return var3;
   }

   <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
      return (RemovalListener)MoreObjects.firstNonNull(this.removalListener, CacheBuilder.NullListener.INSTANCE);
   }

   Supplier<? extends AbstractCache.StatsCounter> getStatsCounterSupplier() {
      return this.statsCounterSupplier;
   }

   Ticker getTicker(boolean var1) {
      Ticker var2 = this.ticker;
      if (var2 != null) {
         return var2;
      } else {
         if (var1) {
            var2 = Ticker.systemTicker();
         } else {
            var2 = NULL_TICKER;
         }

         return var2;
      }
   }

   Equivalence<Object> getValueEquivalence() {
      return (Equivalence)MoreObjects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
   }

   LocalCache.Strength getValueStrength() {
      return (LocalCache.Strength)MoreObjects.firstNonNull(this.valueStrength, LocalCache.Strength.STRONG);
   }

   <K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher() {
      return (Weigher)MoreObjects.firstNonNull(this.weigher, CacheBuilder.OneWeigher.INSTANCE);
   }

   public CacheBuilder<K, V> initialCapacity(int var1) {
      int var2 = this.initialCapacity;
      boolean var3 = true;
      boolean var4;
      if (var2 == -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4, "initial capacity was already set to %s", this.initialCapacity);
      if (var1 >= 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.initialCapacity = var1;
      return this;
   }

   boolean isRecordingStats() {
      boolean var1;
      if (this.statsCounterSupplier == CACHE_STATS_COUNTER) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   CacheBuilder<K, V> keyEquivalence(Equivalence<Object> var1) {
      boolean var2;
      if (this.keyEquivalence == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "key equivalence was already set to %s", (Object)this.keyEquivalence);
      this.keyEquivalence = (Equivalence)Preconditions.checkNotNull(var1);
      return this;
   }

   CacheBuilder<K, V> lenientParsing() {
      this.strictParsing = false;
      return this;
   }

   public CacheBuilder<K, V> maximumSize(long var1) {
      long var3 = this.maximumSize;
      boolean var5 = true;
      boolean var6;
      if (var3 == -1L) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkState(var6, "maximum size was already set to %s", this.maximumSize);
      if (this.maximumWeight == -1L) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkState(var6, "maximum weight was already set to %s", this.maximumWeight);
      if (this.weigher == null) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkState(var6, "maximum size can not be combined with weigher");
      if (var1 >= 0L) {
         var6 = var5;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "maximum size must not be negative");
      this.maximumSize = var1;
      return this;
   }

   public CacheBuilder<K, V> maximumWeight(long var1) {
      long var3 = this.maximumWeight;
      boolean var5 = true;
      boolean var6;
      if (var3 == -1L) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkState(var6, "maximum weight was already set to %s", this.maximumWeight);
      if (this.maximumSize == -1L) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkState(var6, "maximum size was already set to %s", this.maximumSize);
      this.maximumWeight = var1;
      if (var1 >= 0L) {
         var6 = var5;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "maximum weight must not be negative");
      return this;
   }

   public CacheBuilder<K, V> recordStats() {
      this.statsCounterSupplier = CACHE_STATS_COUNTER;
      return this;
   }

   public CacheBuilder<K, V> refreshAfterWrite(long var1, TimeUnit var3) {
      Preconditions.checkNotNull(var3);
      long var4 = this.refreshNanos;
      boolean var6 = true;
      boolean var7;
      if (var4 == -1L) {
         var7 = true;
      } else {
         var7 = false;
      }

      Preconditions.checkState(var7, "refresh was already set to %s ns", this.refreshNanos);
      if (var1 > 0L) {
         var7 = var6;
      } else {
         var7 = false;
      }

      Preconditions.checkArgument(var7, "duration must be positive: %s %s", var1, var3);
      this.refreshNanos = var3.toNanos(var1);
      return this;
   }

   @CheckReturnValue
   public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> var1) {
      boolean var2;
      if (this.removalListener == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2);
      this.removalListener = (RemovalListener)Preconditions.checkNotNull(var1);
      return this;
   }

   CacheBuilder<K, V> setKeyStrength(LocalCache.Strength var1) {
      boolean var2;
      if (this.keyStrength == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "Key strength was already set to %s", (Object)this.keyStrength);
      this.keyStrength = (LocalCache.Strength)Preconditions.checkNotNull(var1);
      return this;
   }

   CacheBuilder<K, V> setValueStrength(LocalCache.Strength var1) {
      boolean var2;
      if (this.valueStrength == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "Value strength was already set to %s", (Object)this.valueStrength);
      this.valueStrength = (LocalCache.Strength)Preconditions.checkNotNull(var1);
      return this;
   }

   public CacheBuilder<K, V> softValues() {
      return this.setValueStrength(LocalCache.Strength.SOFT);
   }

   public CacheBuilder<K, V> ticker(Ticker var1) {
      boolean var2;
      if (this.ticker == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2);
      this.ticker = (Ticker)Preconditions.checkNotNull(var1);
      return this;
   }

   public String toString() {
      MoreObjects.ToStringHelper var1 = MoreObjects.toStringHelper((Object)this);
      int var2 = this.initialCapacity;
      if (var2 != -1) {
         var1.add("initialCapacity", var2);
      }

      var2 = this.concurrencyLevel;
      if (var2 != -1) {
         var1.add("concurrencyLevel", var2);
      }

      long var3 = this.maximumSize;
      if (var3 != -1L) {
         var1.add("maximumSize", var3);
      }

      var3 = this.maximumWeight;
      if (var3 != -1L) {
         var1.add("maximumWeight", var3);
      }

      StringBuilder var5;
      if (this.expireAfterWriteNanos != -1L) {
         var5 = new StringBuilder();
         var5.append(this.expireAfterWriteNanos);
         var5.append("ns");
         var1.add("expireAfterWrite", var5.toString());
      }

      if (this.expireAfterAccessNanos != -1L) {
         var5 = new StringBuilder();
         var5.append(this.expireAfterAccessNanos);
         var5.append("ns");
         var1.add("expireAfterAccess", var5.toString());
      }

      LocalCache.Strength var6 = this.keyStrength;
      if (var6 != null) {
         var1.add("keyStrength", Ascii.toLowerCase(var6.toString()));
      }

      var6 = this.valueStrength;
      if (var6 != null) {
         var1.add("valueStrength", Ascii.toLowerCase(var6.toString()));
      }

      if (this.keyEquivalence != null) {
         var1.addValue("keyEquivalence");
      }

      if (this.valueEquivalence != null) {
         var1.addValue("valueEquivalence");
      }

      if (this.removalListener != null) {
         var1.addValue("removalListener");
      }

      return var1.toString();
   }

   CacheBuilder<K, V> valueEquivalence(Equivalence<Object> var1) {
      boolean var2;
      if (this.valueEquivalence == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "value equivalence was already set to %s", (Object)this.valueEquivalence);
      this.valueEquivalence = (Equivalence)Preconditions.checkNotNull(var1);
      return this;
   }

   public CacheBuilder<K, V> weakKeys() {
      return this.setKeyStrength(LocalCache.Strength.WEAK);
   }

   public CacheBuilder<K, V> weakValues() {
      return this.setValueStrength(LocalCache.Strength.WEAK);
   }

   public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> var1) {
      Weigher var2 = this.weigher;
      boolean var3 = true;
      boolean var4;
      if (var2 == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);
      if (this.strictParsing) {
         if (this.maximumSize == -1L) {
            var4 = var3;
         } else {
            var4 = false;
         }

         Preconditions.checkState(var4, "weigher can not be combined with maximum size", this.maximumSize);
      }

      this.weigher = (Weigher)Preconditions.checkNotNull(var1);
      return this;
   }

   static enum NullListener implements RemovalListener<Object, Object> {
      INSTANCE;

      static {
         CacheBuilder.NullListener var0 = new CacheBuilder.NullListener("INSTANCE", 0);
         INSTANCE = var0;
      }

      public void onRemoval(RemovalNotification<Object, Object> var1) {
      }
   }

   static enum OneWeigher implements Weigher<Object, Object> {
      INSTANCE;

      static {
         CacheBuilder.OneWeigher var0 = new CacheBuilder.OneWeigher("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int weigh(Object var1, Object var2) {
         return 1;
      }
   }
}
