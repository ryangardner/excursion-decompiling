package com.google.common.collect;

import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
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

   public MapMaker concurrencyLevel(int var1) {
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

   int getConcurrencyLevel() {
      int var1 = this.concurrencyLevel;
      int var2 = var1;
      if (var1 == -1) {
         var2 = 4;
      }

      return var2;
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

   MapMakerInternalMap.Strength getKeyStrength() {
      return (MapMakerInternalMap.Strength)MoreObjects.firstNonNull(this.keyStrength, MapMakerInternalMap.Strength.STRONG);
   }

   MapMakerInternalMap.Strength getValueStrength() {
      return (MapMakerInternalMap.Strength)MoreObjects.firstNonNull(this.valueStrength, MapMakerInternalMap.Strength.STRONG);
   }

   public MapMaker initialCapacity(int var1) {
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

   MapMaker keyEquivalence(Equivalence<Object> var1) {
      boolean var2;
      if (this.keyEquivalence == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "key equivalence was already set to %s", (Object)this.keyEquivalence);
      this.keyEquivalence = (Equivalence)Preconditions.checkNotNull(var1);
      this.useCustomMap = true;
      return this;
   }

   public <K, V> ConcurrentMap<K, V> makeMap() {
      return (ConcurrentMap)(!this.useCustomMap ? new ConcurrentHashMap(this.getInitialCapacity(), 0.75F, this.getConcurrencyLevel()) : MapMakerInternalMap.create(this));
   }

   MapMaker setKeyStrength(MapMakerInternalMap.Strength var1) {
      boolean var2;
      if (this.keyStrength == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "Key strength was already set to %s", (Object)this.keyStrength);
      this.keyStrength = (MapMakerInternalMap.Strength)Preconditions.checkNotNull(var1);
      if (var1 != MapMakerInternalMap.Strength.STRONG) {
         this.useCustomMap = true;
      }

      return this;
   }

   MapMaker setValueStrength(MapMakerInternalMap.Strength var1) {
      boolean var2;
      if (this.valueStrength == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "Value strength was already set to %s", (Object)this.valueStrength);
      this.valueStrength = (MapMakerInternalMap.Strength)Preconditions.checkNotNull(var1);
      if (var1 != MapMakerInternalMap.Strength.STRONG) {
         this.useCustomMap = true;
      }

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

      MapMakerInternalMap.Strength var3 = this.keyStrength;
      if (var3 != null) {
         var1.add("keyStrength", Ascii.toLowerCase(var3.toString()));
      }

      var3 = this.valueStrength;
      if (var3 != null) {
         var1.add("valueStrength", Ascii.toLowerCase(var3.toString()));
      }

      if (this.keyEquivalence != null) {
         var1.addValue("keyEquivalence");
      }

      return var1.toString();
   }

   public MapMaker weakKeys() {
      return this.setKeyStrength(MapMakerInternalMap.Strength.WEAK);
   }

   public MapMaker weakValues() {
      return this.setValueStrength(MapMakerInternalMap.Strength.WEAK);
   }

   static enum Dummy {
      VALUE;

      static {
         MapMaker.Dummy var0 = new MapMaker.Dummy("VALUE", 0);
         VALUE = var0;
      }
   }
}
