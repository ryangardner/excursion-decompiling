/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMakerInternalMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public final class MapMaker {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int UNSET_INT = -1;
    int concurrencyLevel = -1;
    int initialCapacity = -1;
    @MonotonicNonNullDecl
    Equivalence<Object> keyEquivalence;
    @MonotonicNonNullDecl
    MapMakerInternalMap.Strength keyStrength;
    boolean useCustomMap;
    @MonotonicNonNullDecl
    MapMakerInternalMap.Strength valueStrength;

    public MapMaker concurrencyLevel(int n) {
        int n2 = this.concurrencyLevel;
        boolean bl = true;
        boolean bl2 = n2 == -1;
        Preconditions.checkState(bl2, "concurrency level was already set to %s", this.concurrencyLevel);
        bl2 = n > 0 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.concurrencyLevel = n;
        return this;
    }

    int getConcurrencyLevel() {
        int n;
        int n2 = n = this.concurrencyLevel;
        if (n != -1) return n2;
        return 4;
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

    MapMakerInternalMap.Strength getKeyStrength() {
        return MoreObjects.firstNonNull(this.keyStrength, MapMakerInternalMap.Strength.STRONG);
    }

    MapMakerInternalMap.Strength getValueStrength() {
        return MoreObjects.firstNonNull(this.valueStrength, MapMakerInternalMap.Strength.STRONG);
    }

    public MapMaker initialCapacity(int n) {
        int n2 = this.initialCapacity;
        boolean bl = true;
        boolean bl2 = n2 == -1;
        Preconditions.checkState(bl2, "initial capacity was already set to %s", this.initialCapacity);
        bl2 = n >= 0 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.initialCapacity = n;
        return this;
    }

    MapMaker keyEquivalence(Equivalence<Object> equivalence) {
        boolean bl = this.keyEquivalence == null;
        Preconditions.checkState(bl, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (this.useCustomMap) return MapMakerInternalMap.create(this);
        return new ConcurrentHashMap(this.getInitialCapacity(), 0.75f, this.getConcurrencyLevel());
    }

    MapMaker setKeyStrength(MapMakerInternalMap.Strength strength) {
        boolean bl = this.keyStrength == null;
        Preconditions.checkState(bl, "Key strength was already set to %s", (Object)this.keyStrength);
        this.keyStrength = Preconditions.checkNotNull(strength);
        if (strength == MapMakerInternalMap.Strength.STRONG) return this;
        this.useCustomMap = true;
        return this;
    }

    MapMaker setValueStrength(MapMakerInternalMap.Strength strength) {
        boolean bl = this.valueStrength == null;
        Preconditions.checkState(bl, "Value strength was already set to %s", (Object)this.valueStrength);
        this.valueStrength = Preconditions.checkNotNull(strength);
        if (strength == MapMakerInternalMap.Strength.STRONG) return this;
        this.useCustomMap = true;
        return this;
    }

    public String toString() {
        MapMakerInternalMap.Strength strength;
        MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
        int n = this.initialCapacity;
        if (n != -1) {
            toStringHelper.add("initialCapacity", n);
        }
        if ((n = this.concurrencyLevel) != -1) {
            toStringHelper.add("concurrencyLevel", n);
        }
        if ((strength = this.keyStrength) != null) {
            toStringHelper.add("keyStrength", Ascii.toLowerCase(strength.toString()));
        }
        if ((strength = this.valueStrength) != null) {
            toStringHelper.add("valueStrength", Ascii.toLowerCase(strength.toString()));
        }
        if (this.keyEquivalence == null) return toStringHelper.toString();
        toStringHelper.addValue("keyEquivalence");
        return toStringHelper.toString();
    }

    public MapMaker weakKeys() {
        return this.setKeyStrength(MapMakerInternalMap.Strength.WEAK);
    }

    public MapMaker weakValues() {
        return this.setValueStrength(MapMakerInternalMap.Strength.WEAK);
    }

    static final class Dummy
    extends Enum<Dummy> {
        private static final /* synthetic */ Dummy[] $VALUES;
        public static final /* enum */ Dummy VALUE;

        static {
            Dummy dummy;
            VALUE = dummy = new Dummy();
            $VALUES = new Dummy[]{dummy};
        }

        public static Dummy valueOf(String string2) {
            return Enum.valueOf(Dummy.class, string2);
        }

        public static Dummy[] values() {
            return (Dummy[])$VALUES.clone();
        }
    }

}

