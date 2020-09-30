/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LocalCache;
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
    private static final ImmutableMap<String, ValueParser> VALUE_PARSERS = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", (InitialCapacityParser)((Object)new MaximumSizeParser())).put("maximumWeight", (InitialCapacityParser)((Object)new MaximumWeightParser())).put("concurrencyLevel", (InitialCapacityParser)((Object)new ConcurrencyLevelParser())).put("weakKeys", (InitialCapacityParser)((Object)new KeyStrengthParser(LocalCache.Strength.WEAK))).put("softValues", (InitialCapacityParser)((Object)new ValueStrengthParser(LocalCache.Strength.SOFT))).put("weakValues", (InitialCapacityParser)((Object)new ValueStrengthParser(LocalCache.Strength.WEAK))).put("recordStats", (InitialCapacityParser)((Object)new RecordStatsParser())).put("expireAfterAccess", (InitialCapacityParser)((Object)new AccessDurationParser())).put("expireAfterWrite", (InitialCapacityParser)((Object)new WriteDurationParser())).put("refreshAfterWrite", (InitialCapacityParser)((Object)new RefreshDurationParser())).put("refreshInterval", (InitialCapacityParser)((Object)new RefreshDurationParser())).build();
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

    private CacheBuilderSpec(String string2) {
        this.specification = string2;
    }

    public static CacheBuilderSpec disableCaching() {
        return CacheBuilderSpec.parse("maximumSize=0");
    }

    @NullableDecl
    private static Long durationInNanos(long l, @NullableDecl TimeUnit object) {
        if (object != null) return object.toNanos(l);
        return null;
    }

    private static String format(String string2, Object ... arrobject) {
        return String.format(Locale.ROOT, string2, arrobject);
    }

    public static CacheBuilderSpec parse(String object) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec((String)object);
        if (((String)object).isEmpty()) return cacheBuilderSpec;
        Iterator<String> iterator2 = KEYS_SPLITTER.split((CharSequence)object).iterator();
        while (iterator2.hasNext()) {
            String string2 = iterator2.next();
            object = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(string2));
            Preconditions.checkArgument(object.isEmpty() ^ true, "blank key-value pair");
            int n = object.size();
            boolean bl = false;
            boolean bl2 = n <= 2;
            Preconditions.checkArgument(bl2, "key-value pair %s with more than one equals sign", (Object)string2);
            string2 = (String)object.get(0);
            ValueParser valueParser = VALUE_PARSERS.get(string2);
            bl2 = bl;
            if (valueParser != null) {
                bl2 = true;
            }
            Preconditions.checkArgument(bl2, "unknown key %s", (Object)string2);
            object = object.size() == 1 ? null : (String)object.get(1);
            valueParser.parse(cacheBuilderSpec, string2, (String)object);
        }
        return cacheBuilderSpec;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CacheBuilderSpec)) {
            return false;
        }
        object = (CacheBuilderSpec)object;
        if (!Objects.equal(this.initialCapacity, ((CacheBuilderSpec)object).initialCapacity)) return false;
        if (!Objects.equal(this.maximumSize, ((CacheBuilderSpec)object).maximumSize)) return false;
        if (!Objects.equal(this.maximumWeight, ((CacheBuilderSpec)object).maximumWeight)) return false;
        if (!Objects.equal(this.concurrencyLevel, ((CacheBuilderSpec)object).concurrencyLevel)) return false;
        if (!Objects.equal((Object)this.keyStrength, (Object)((CacheBuilderSpec)object).keyStrength)) return false;
        if (!Objects.equal((Object)this.valueStrength, (Object)((CacheBuilderSpec)object).valueStrength)) return false;
        if (!Objects.equal(this.recordStats, ((CacheBuilderSpec)object).recordStats)) return false;
        if (!Objects.equal(CacheBuilderSpec.durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), CacheBuilderSpec.durationInNanos(((CacheBuilderSpec)object).writeExpirationDuration, ((CacheBuilderSpec)object).writeExpirationTimeUnit))) return false;
        if (!Objects.equal(CacheBuilderSpec.durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), CacheBuilderSpec.durationInNanos(((CacheBuilderSpec)object).accessExpirationDuration, ((CacheBuilderSpec)object).accessExpirationTimeUnit))) return false;
        if (!Objects.equal(CacheBuilderSpec.durationInNanos(this.refreshDuration, this.refreshTimeUnit), CacheBuilderSpec.durationInNanos(((CacheBuilderSpec)object).refreshDuration, ((CacheBuilderSpec)object).refreshTimeUnit))) return false;
        return bl;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, CacheBuilderSpec.durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), CacheBuilderSpec.durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), CacheBuilderSpec.durationInNanos(this.refreshDuration, this.refreshTimeUnit)});
    }

    CacheBuilder<Object, Object> toCacheBuilder() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        Object object = this.initialCapacity;
        if (object != null) {
            cacheBuilder.initialCapacity((int)object);
        }
        if ((object = this.maximumSize) != null) {
            cacheBuilder.maximumSize((Long)object);
        }
        if ((object = this.maximumWeight) != null) {
            cacheBuilder.maximumWeight((Long)object);
        }
        if ((object = this.concurrencyLevel) != null) {
            cacheBuilder.concurrencyLevel((int)object);
        }
        if (this.keyStrength != null) {
            if (1.$SwitchMap$com$google$common$cache$LocalCache$Strength[this.keyStrength.ordinal()] != 1) throw new AssertionError();
            cacheBuilder.weakKeys();
        }
        if (this.valueStrength != null) {
            int n = 1.$SwitchMap$com$google$common$cache$LocalCache$Strength[this.valueStrength.ordinal()];
            if (n != 1) {
                if (n != 2) throw new AssertionError();
                cacheBuilder.softValues();
            } else {
                cacheBuilder.weakValues();
            }
        }
        if ((object = this.recordStats) != null && ((Boolean)object).booleanValue()) {
            cacheBuilder.recordStats();
        }
        if ((object = this.writeExpirationTimeUnit) != null) {
            cacheBuilder.expireAfterWrite(this.writeExpirationDuration, (TimeUnit)((Object)object));
        }
        if ((object = this.accessExpirationTimeUnit) != null) {
            cacheBuilder.expireAfterAccess(this.accessExpirationDuration, (TimeUnit)((Object)object));
        }
        if ((object = this.refreshTimeUnit) == null) return cacheBuilder;
        cacheBuilder.refreshAfterWrite(this.refreshDuration, (TimeUnit)((Object)object));
        return cacheBuilder;
    }

    public String toParsableString() {
        return this.specification;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.toParsableString()).toString();
    }

    static class AccessDurationParser
    extends DurationParser {
        AccessDurationParser() {
        }

        @Override
        protected void parseDuration(CacheBuilderSpec cacheBuilderSpec, long l, TimeUnit timeUnit) {
            boolean bl = cacheBuilderSpec.accessExpirationTimeUnit == null;
            Preconditions.checkArgument(bl, "expireAfterAccess already set");
            cacheBuilderSpec.accessExpirationDuration = l;
            cacheBuilderSpec.accessExpirationTimeUnit = timeUnit;
        }
    }

    static class ConcurrencyLevelParser
    extends IntegerParser {
        ConcurrencyLevelParser() {
        }

        @Override
        protected void parseInteger(CacheBuilderSpec cacheBuilderSpec, int n) {
            boolean bl = cacheBuilderSpec.concurrencyLevel == null;
            Preconditions.checkArgument(bl, "concurrency level was already set to ", (Object)cacheBuilderSpec.concurrencyLevel);
            cacheBuilderSpec.concurrencyLevel = n;
        }
    }

    static abstract class DurationParser
    implements ValueParser {
        DurationParser() {
        }

        @Override
        public void parse(CacheBuilderSpec object, String string2, String string3) {
            boolean bl = string3 != null && !string3.isEmpty();
            Preconditions.checkArgument(bl, "value of key %s omitted", (Object)string2);
            try {
                TimeUnit timeUnit;
                char c = string3.charAt(string3.length() - 1);
                if (c != 'd') {
                    if (c != 'h') {
                        if (c != 'm') {
                            if (c != 's') {
                                object = new IllegalArgumentException(CacheBuilderSpec.format("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", new Object[]{string2, string3}));
                                throw object;
                            }
                            timeUnit = TimeUnit.SECONDS;
                        } else {
                            timeUnit = TimeUnit.MINUTES;
                        }
                    } else {
                        timeUnit = TimeUnit.HOURS;
                    }
                } else {
                    timeUnit = TimeUnit.DAYS;
                }
                this.parseDuration((CacheBuilderSpec)object, Long.parseLong(string3.substring(0, string3.length() - 1)), timeUnit);
                return;
            }
            catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", new Object[]{string2, string3}));
            }
        }

        protected abstract void parseDuration(CacheBuilderSpec var1, long var2, TimeUnit var4);
    }

    static class InitialCapacityParser
    extends IntegerParser {
        InitialCapacityParser() {
        }

        @Override
        protected void parseInteger(CacheBuilderSpec cacheBuilderSpec, int n) {
            boolean bl = cacheBuilderSpec.initialCapacity == null;
            Preconditions.checkArgument(bl, "initial capacity was already set to ", (Object)cacheBuilderSpec.initialCapacity);
            cacheBuilderSpec.initialCapacity = n;
        }
    }

    static abstract class IntegerParser
    implements ValueParser {
        IntegerParser() {
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string2, String string3) {
            boolean bl = string3 != null && !string3.isEmpty();
            Preconditions.checkArgument(bl, "value of key %s omitted", (Object)string2);
            try {
                this.parseInteger(cacheBuilderSpec, Integer.parseInt(string3));
                return;
            }
            catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", new Object[]{string2, string3}), numberFormatException);
            }
        }

        protected abstract void parseInteger(CacheBuilderSpec var1, int var2);
    }

    static class KeyStrengthParser
    implements ValueParser {
        private final LocalCache.Strength strength;

        public KeyStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string2, @NullableDecl String string3) {
            boolean bl = true;
            boolean bl2 = string3 == null;
            Preconditions.checkArgument(bl2, "key %s does not take values", (Object)string2);
            bl2 = cacheBuilderSpec.keyStrength == null ? bl : false;
            Preconditions.checkArgument(bl2, "%s was already set to %s", (Object)string2, (Object)cacheBuilderSpec.keyStrength);
            cacheBuilderSpec.keyStrength = this.strength;
        }
    }

    static abstract class LongParser
    implements ValueParser {
        LongParser() {
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string2, String string3) {
            boolean bl = string3 != null && !string3.isEmpty();
            Preconditions.checkArgument(bl, "value of key %s omitted", (Object)string2);
            try {
                this.parseLong(cacheBuilderSpec, Long.parseLong(string3));
                return;
            }
            catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException(CacheBuilderSpec.format("key %s value set to %s, must be integer", new Object[]{string2, string3}), numberFormatException);
            }
        }

        protected abstract void parseLong(CacheBuilderSpec var1, long var2);
    }

    static class MaximumSizeParser
    extends LongParser {
        MaximumSizeParser() {
        }

        @Override
        protected void parseLong(CacheBuilderSpec cacheBuilderSpec, long l) {
            Long l2 = cacheBuilderSpec.maximumSize;
            boolean bl = true;
            boolean bl2 = l2 == null;
            Preconditions.checkArgument(bl2, "maximum size was already set to ", (Object)cacheBuilderSpec.maximumSize);
            bl2 = cacheBuilderSpec.maximumWeight == null ? bl : false;
            Preconditions.checkArgument(bl2, "maximum weight was already set to ", (Object)cacheBuilderSpec.maximumWeight);
            cacheBuilderSpec.maximumSize = l;
        }
    }

    static class MaximumWeightParser
    extends LongParser {
        MaximumWeightParser() {
        }

        @Override
        protected void parseLong(CacheBuilderSpec cacheBuilderSpec, long l) {
            Long l2 = cacheBuilderSpec.maximumWeight;
            boolean bl = true;
            boolean bl2 = l2 == null;
            Preconditions.checkArgument(bl2, "maximum weight was already set to ", (Object)cacheBuilderSpec.maximumWeight);
            bl2 = cacheBuilderSpec.maximumSize == null ? bl : false;
            Preconditions.checkArgument(bl2, "maximum size was already set to ", (Object)cacheBuilderSpec.maximumSize);
            cacheBuilderSpec.maximumWeight = l;
        }
    }

    static class RecordStatsParser
    implements ValueParser {
        RecordStatsParser() {
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string2, @NullableDecl String string3) {
            boolean bl = false;
            boolean bl2 = string3 == null;
            Preconditions.checkArgument(bl2, "recordStats does not take values");
            bl2 = bl;
            if (cacheBuilderSpec.recordStats == null) {
                bl2 = true;
            }
            Preconditions.checkArgument(bl2, "recordStats already set");
            cacheBuilderSpec.recordStats = true;
        }
    }

    static class RefreshDurationParser
    extends DurationParser {
        RefreshDurationParser() {
        }

        @Override
        protected void parseDuration(CacheBuilderSpec cacheBuilderSpec, long l, TimeUnit timeUnit) {
            boolean bl = cacheBuilderSpec.refreshTimeUnit == null;
            Preconditions.checkArgument(bl, "refreshAfterWrite already set");
            cacheBuilderSpec.refreshDuration = l;
            cacheBuilderSpec.refreshTimeUnit = timeUnit;
        }
    }

    private static interface ValueParser {
        public void parse(CacheBuilderSpec var1, String var2, @NullableDecl String var3);
    }

    static class ValueStrengthParser
    implements ValueParser {
        private final LocalCache.Strength strength;

        public ValueStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string2, @NullableDecl String string3) {
            boolean bl = true;
            boolean bl2 = string3 == null;
            Preconditions.checkArgument(bl2, "key %s does not take values", (Object)string2);
            bl2 = cacheBuilderSpec.valueStrength == null ? bl : false;
            Preconditions.checkArgument(bl2, "%s was already set to %s", (Object)string2, (Object)cacheBuilderSpec.valueStrength);
            cacheBuilderSpec.valueStrength = this.strength;
        }
    }

    static class WriteDurationParser
    extends DurationParser {
        WriteDurationParser() {
        }

        @Override
        protected void parseDuration(CacheBuilderSpec cacheBuilderSpec, long l, TimeUnit timeUnit) {
            boolean bl = cacheBuilderSpec.writeExpirationTimeUnit == null;
            Preconditions.checkArgument(bl, "expireAfterWrite already set");
            cacheBuilderSpec.writeExpirationDuration = l;
            cacheBuilderSpec.writeExpirationTimeUnit = timeUnit;
        }
    }

}

