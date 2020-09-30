package com.google.common.cache;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class CacheBuilderSpec {
   private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
   private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
   private static final ImmutableMap<String, CacheBuilderSpec.ValueParser> VALUE_PARSERS;
   long accessExpirationDuration;
   @MonotonicNonNullDecl
   TimeUnit accessExpirationTimeUnit;
   @MonotonicNonNullDecl
   Integer concurrencyLevel;
   @MonotonicNonNullDecl
   Integer initialCapacity;
   @MonotonicNonNullDecl
   LocalCache.Strength keyStrength;
   @MonotonicNonNullDecl
   Long maximumSize;
   @MonotonicNonNullDecl
   Long maximumWeight;
   @MonotonicNonNullDecl
   Boolean recordStats;
   long refreshDuration;
   @MonotonicNonNullDecl
   TimeUnit refreshTimeUnit;
   private final String specification;
   @MonotonicNonNullDecl
   LocalCache.Strength valueStrength;
   long writeExpirationDuration;
   @MonotonicNonNullDecl
   TimeUnit writeExpirationTimeUnit;

   static {
      VALUE_PARSERS = ImmutableMap.builder().put("initialCapacity", new CacheBuilderSpec.InitialCapacityParser()).put("maximumSize", new CacheBuilderSpec.MaximumSizeParser()).put("maximumWeight", new CacheBuilderSpec.MaximumWeightParser()).put("concurrencyLevel", new CacheBuilderSpec.ConcurrencyLevelParser()).put("weakKeys", new CacheBuilderSpec.KeyStrengthParser(LocalCache.Strength.WEAK)).put("softValues", new CacheBuilderSpec.ValueStrengthParser(LocalCache.Strength.SOFT)).put("weakValues", new CacheBuilderSpec.ValueStrengthParser(LocalCache.Strength.WEAK)).put("recordStats", new CacheBuilderSpec.RecordStatsParser()).put("expireAfterAccess", new CacheBuilderSpec.AccessDurationParser()).put("expireAfterWrite", new CacheBuilderSpec.WriteDurationParser()).put("refreshAfterWrite", new CacheBuilderSpec.RefreshDurationParser()).put("refreshInterval", new CacheBuilderSpec.RefreshDurationParser()).build();
   }

   private CacheBuilderSpec(String var1) {
      this.specification = var1;
   }

   public static CacheBuilderSpec disableCaching() {
      return parse("maximumSize=0");
   }

   @NullableDecl
   private static Long durationInNanos(long var0, @NullableDecl TimeUnit var2) {
      Long var3;
      if (var2 == null) {
         var3 = null;
      } else {
         var3 = var2.toNanos(var0);
      }

      return var3;
   }

   private static String format(String var0, Object... var1) {
      return String.format(Locale.ROOT, var0, var1);
   }

   public static CacheBuilderSpec parse(String var0) {
      CacheBuilderSpec var1 = new CacheBuilderSpec(var0);
      String var3;
      CacheBuilderSpec.ValueParser var7;
      if (!var0.isEmpty()) {
         for(Iterator var2 = KEYS_SPLITTER.split(var0).iterator(); var2.hasNext(); var7.parse(var1, var3, var0)) {
            var3 = (String)var2.next();
            ImmutableList var8 = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(var3));
            Preconditions.checkArgument(var8.isEmpty() ^ true, "blank key-value pair");
            int var4 = var8.size();
            boolean var5 = false;
            boolean var6;
            if (var4 <= 2) {
               var6 = true;
            } else {
               var6 = false;
            }

            Preconditions.checkArgument(var6, "key-value pair %s with more than one equals sign", (Object)var3);
            var3 = (String)var8.get(0);
            var7 = (CacheBuilderSpec.ValueParser)VALUE_PARSERS.get(var3);
            var6 = var5;
            if (var7 != null) {
               var6 = true;
            }

            Preconditions.checkArgument(var6, "unknown key %s", (Object)var3);
            if (var8.size() == 1) {
               var0 = null;
            } else {
               var0 = (String)var8.get(1);
            }
         }
      }

      return var1;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof CacheBuilderSpec)) {
         return false;
      } else {
         CacheBuilderSpec var3 = (CacheBuilderSpec)var1;
         if (!Objects.equal(this.initialCapacity, var3.initialCapacity) || !Objects.equal(this.maximumSize, var3.maximumSize) || !Objects.equal(this.maximumWeight, var3.maximumWeight) || !Objects.equal(this.concurrencyLevel, var3.concurrencyLevel) || !Objects.equal(this.keyStrength, var3.keyStrength) || !Objects.equal(this.valueStrength, var3.valueStrength) || !Objects.equal(this.recordStats, var3.recordStats) || !Objects.equal(durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(var3.writeExpirationDuration, var3.writeExpirationTimeUnit)) || !Objects.equal(durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(var3.accessExpirationDuration, var3.accessExpirationTimeUnit)) || !Objects.equal(durationInNanos(this.refreshDuration, this.refreshTimeUnit), durationInNanos(var3.refreshDuration, var3.refreshTimeUnit))) {
            var2 = false;
         }

         return var2;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), durationInNanos(this.refreshDuration, this.refreshTimeUnit));
   }

   CacheBuilder<Object, Object> toCacheBuilder() {
      CacheBuilder var1 = CacheBuilder.newBuilder();
      Integer var2 = this.initialCapacity;
      if (var2 != null) {
         var1.initialCapacity(var2);
      }

      Long var4 = this.maximumSize;
      if (var4 != null) {
         var1.maximumSize(var4);
      }

      var4 = this.maximumWeight;
      if (var4 != null) {
         var1.maximumWeight(var4);
      }

      var2 = this.concurrencyLevel;
      if (var2 != null) {
         var1.concurrencyLevel(var2);
      }

      if (this.keyStrength != null) {
         if (null.$SwitchMap$com$google$common$cache$LocalCache$Strength[this.keyStrength.ordinal()] != 1) {
            throw new AssertionError();
         }

         var1.weakKeys();
      }

      if (this.valueStrength != null) {
         int var3 = null.$SwitchMap$com$google$common$cache$LocalCache$Strength[this.valueStrength.ordinal()];
         if (var3 != 1) {
            if (var3 != 2) {
               throw new AssertionError();
            }

            var1.softValues();
         } else {
            var1.weakValues();
         }
      }

      Boolean var5 = this.recordStats;
      if (var5 != null && var5) {
         var1.recordStats();
      }

      TimeUnit var6 = this.writeExpirationTimeUnit;
      if (var6 != null) {
         var1.expireAfterWrite(this.writeExpirationDuration, var6);
      }

      var6 = this.accessExpirationTimeUnit;
      if (var6 != null) {
         var1.expireAfterAccess(this.accessExpirationDuration, var6);
      }

      var6 = this.refreshTimeUnit;
      if (var6 != null) {
         var1.refreshAfterWrite(this.refreshDuration, var6);
      }

      return var1;
   }

   public String toParsableString() {
      return this.specification;
   }

   public String toString() {
      return MoreObjects.toStringHelper((Object)this).addValue(this.toParsableString()).toString();
   }

   static class AccessDurationParser extends CacheBuilderSpec.DurationParser {
      protected void parseDuration(CacheBuilderSpec var1, long var2, TimeUnit var4) {
         boolean var5;
         if (var1.accessExpirationTimeUnit == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "expireAfterAccess already set");
         var1.accessExpirationDuration = var2;
         var1.accessExpirationTimeUnit = var4;
      }
   }

   static class ConcurrencyLevelParser extends CacheBuilderSpec.IntegerParser {
      protected void parseInteger(CacheBuilderSpec var1, int var2) {
         boolean var3;
         if (var1.concurrencyLevel == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "concurrency level was already set to ", (Object)var1.concurrencyLevel);
         var1.concurrencyLevel = var2;
      }
   }

   abstract static class DurationParser implements CacheBuilderSpec.ValueParser {
      public void parse(CacheBuilderSpec var1, String var2, String var3) {
         boolean var4;
         if (var3 != null && !var3.isEmpty()) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "value of key %s omitted", (Object)var2);

         boolean var10001;
         char var5;
         try {
            var5 = var3.charAt(var3.length() - 1);
         } catch (NumberFormatException var13) {
            var10001 = false;
            throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
         }

         TimeUnit var6;
         if (var5 != 'd') {
            if (var5 != 'h') {
               if (var5 != 'm') {
                  if (var5 != 's') {
                     try {
                        IllegalArgumentException var14 = new IllegalArgumentException(CacheBuilderSpec.format("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", var2, var3));
                        throw var14;
                     } catch (NumberFormatException var7) {
                        var10001 = false;
                        throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
                     }
                  }

                  try {
                     var6 = TimeUnit.SECONDS;
                  } catch (NumberFormatException var12) {
                     var10001 = false;
                     throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
                  }
               } else {
                  try {
                     var6 = TimeUnit.MINUTES;
                  } catch (NumberFormatException var11) {
                     var10001 = false;
                     throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
                  }
               }
            } else {
               try {
                  var6 = TimeUnit.HOURS;
               } catch (NumberFormatException var10) {
                  var10001 = false;
                  throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
               }
            }
         } else {
            try {
               var6 = TimeUnit.DAYS;
            } catch (NumberFormatException var9) {
               var10001 = false;
               throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
            }
         }

         try {
            this.parseDuration(var1, Long.parseLong(var3.substring(0, var3.length() - 1)), var6);
         } catch (NumberFormatException var8) {
            var10001 = false;
            throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3));
         }
      }

      protected abstract void parseDuration(CacheBuilderSpec var1, long var2, TimeUnit var4);
   }

   static class InitialCapacityParser extends CacheBuilderSpec.IntegerParser {
      protected void parseInteger(CacheBuilderSpec var1, int var2) {
         boolean var3;
         if (var1.initialCapacity == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "initial capacity was already set to ", (Object)var1.initialCapacity);
         var1.initialCapacity = var2;
      }
   }

   abstract static class IntegerParser implements CacheBuilderSpec.ValueParser {
      public void parse(CacheBuilderSpec var1, String var2, String var3) {
         boolean var4;
         if (var3 != null && !var3.isEmpty()) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "value of key %s omitted", (Object)var2);

         try {
            this.parseInteger(var1, Integer.parseInt(var3));
         } catch (NumberFormatException var5) {
            throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3), var5);
         }
      }

      protected abstract void parseInteger(CacheBuilderSpec var1, int var2);
   }

   static class KeyStrengthParser implements CacheBuilderSpec.ValueParser {
      private final LocalCache.Strength strength;

      public KeyStrengthParser(LocalCache.Strength var1) {
         this.strength = var1;
      }

      public void parse(CacheBuilderSpec var1, String var2, @NullableDecl String var3) {
         boolean var4 = true;
         boolean var5;
         if (var3 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "key %s does not take values", (Object)var2);
         if (var1.keyStrength == null) {
            var5 = var4;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "%s was already set to %s", var2, var1.keyStrength);
         var1.keyStrength = this.strength;
      }
   }

   abstract static class LongParser implements CacheBuilderSpec.ValueParser {
      public void parse(CacheBuilderSpec var1, String var2, String var3) {
         boolean var4;
         if (var3 != null && !var3.isEmpty()) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "value of key %s omitted", (Object)var2);

         try {
            this.parseLong(var1, Long.parseLong(var3));
         } catch (NumberFormatException var5) {
            throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", var2, var3), var5);
         }
      }

      protected abstract void parseLong(CacheBuilderSpec var1, long var2);
   }

   static class MaximumSizeParser extends CacheBuilderSpec.LongParser {
      protected void parseLong(CacheBuilderSpec var1, long var2) {
         Long var4 = var1.maximumSize;
         boolean var5 = true;
         boolean var6;
         if (var4 == null) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "maximum size was already set to ", (Object)var1.maximumSize);
         if (var1.maximumWeight == null) {
            var6 = var5;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "maximum weight was already set to ", (Object)var1.maximumWeight);
         var1.maximumSize = var2;
      }
   }

   static class MaximumWeightParser extends CacheBuilderSpec.LongParser {
      protected void parseLong(CacheBuilderSpec var1, long var2) {
         Long var4 = var1.maximumWeight;
         boolean var5 = true;
         boolean var6;
         if (var4 == null) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "maximum weight was already set to ", (Object)var1.maximumWeight);
         if (var1.maximumSize == null) {
            var6 = var5;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "maximum size was already set to ", (Object)var1.maximumSize);
         var1.maximumWeight = var2;
      }
   }

   static class RecordStatsParser implements CacheBuilderSpec.ValueParser {
      public void parse(CacheBuilderSpec var1, String var2, @NullableDecl String var3) {
         boolean var4 = false;
         boolean var5;
         if (var3 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "recordStats does not take values");
         var5 = var4;
         if (var1.recordStats == null) {
            var5 = true;
         }

         Preconditions.checkArgument(var5, "recordStats already set");
         var1.recordStats = true;
      }
   }

   static class RefreshDurationParser extends CacheBuilderSpec.DurationParser {
      protected void parseDuration(CacheBuilderSpec var1, long var2, TimeUnit var4) {
         boolean var5;
         if (var1.refreshTimeUnit == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "refreshAfterWrite already set");
         var1.refreshDuration = var2;
         var1.refreshTimeUnit = var4;
      }
   }

   private interface ValueParser {
      void parse(CacheBuilderSpec var1, String var2, @NullableDecl String var3);
   }

   static class ValueStrengthParser implements CacheBuilderSpec.ValueParser {
      private final LocalCache.Strength strength;

      public ValueStrengthParser(LocalCache.Strength var1) {
         this.strength = var1;
      }

      public void parse(CacheBuilderSpec var1, String var2, @NullableDecl String var3) {
         boolean var4 = true;
         boolean var5;
         if (var3 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "key %s does not take values", (Object)var2);
         if (var1.valueStrength == null) {
            var5 = var4;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "%s was already set to %s", var2, var1.valueStrength);
         var1.valueStrength = this.strength;
      }
   }

   static class WriteDurationParser extends CacheBuilderSpec.DurationParser {
      protected void parseDuration(CacheBuilderSpec var1, long var2, TimeUnit var4) {
         boolean var5;
         if (var1.writeExpirationTimeUnit == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "expireAfterWrite already set");
         var1.writeExpirationDuration = var2;
         var1.writeExpirationTimeUnit = var4;
      }
   }
}
