package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReference;

public final class CharsToNameCanonicalizer {
   private static final int DEFAULT_T_SIZE = 64;
   public static final int HASH_MULT = 33;
   static final int MAX_COLL_CHAIN_LENGTH = 100;
   static final int MAX_ENTRIES_FOR_REUSE = 12000;
   private static final int MAX_T_SIZE = 65536;
   private CharsToNameCanonicalizer.Bucket[] _buckets;
   private boolean _canonicalize;
   private final int _flags;
   private boolean _hashShared;
   private int _indexMask;
   private int _longestCollisionList;
   private BitSet _overflows;
   private final CharsToNameCanonicalizer _parent;
   private final int _seed;
   private int _size;
   private int _sizeThreshold;
   private String[] _symbols;
   private final AtomicReference<CharsToNameCanonicalizer.TableInfo> _tableInfo;

   private CharsToNameCanonicalizer(int var1) {
      this._parent = null;
      this._seed = var1;
      this._canonicalize = true;
      this._flags = -1;
      this._hashShared = false;
      this._longestCollisionList = 0;
      this._tableInfo = new AtomicReference(CharsToNameCanonicalizer.TableInfo.createInitial(64));
   }

   private CharsToNameCanonicalizer(CharsToNameCanonicalizer var1, int var2, int var3, CharsToNameCanonicalizer.TableInfo var4) {
      this._parent = var1;
      this._seed = var3;
      this._tableInfo = null;
      this._flags = var2;
      this._canonicalize = JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(var2);
      this._symbols = var4.symbols;
      this._buckets = var4.buckets;
      this._size = var4.size;
      this._longestCollisionList = var4.longestCollisionList;
      var2 = this._symbols.length;
      this._sizeThreshold = _thresholdSize(var2);
      this._indexMask = var2 - 1;
      this._hashShared = true;
   }

   private String _addSymbol(char[] var1, int var2, int var3, int var4, int var5) {
      if (this._hashShared) {
         this.copyArrays();
         this._hashShared = false;
      } else if (this._size >= this._sizeThreshold) {
         this.rehash();
         var5 = this._hashToIndex(this.calcHash(var1, var2, var3));
      }

      String var6 = new String(var1, var2, var3);
      String var7 = var6;
      if (JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
         var7 = InternCache.instance.intern(var6);
      }

      ++this._size;
      String[] var8 = this._symbols;
      if (var8[var5] == null) {
         var8[var5] = var7;
      } else {
         var3 = var5 >> 1;
         CharsToNameCanonicalizer.Bucket var9 = new CharsToNameCanonicalizer.Bucket(var7, this._buckets[var3]);
         var2 = var9.length;
         if (var2 > 100) {
            this._handleSpillOverflow(var3, var9, var5);
         } else {
            this._buckets[var3] = var9;
            this._longestCollisionList = Math.max(var2, this._longestCollisionList);
         }
      }

      return var7;
   }

   private String _findSymbol2(char[] var1, int var2, int var3, CharsToNameCanonicalizer.Bucket var4) {
      while(var4 != null) {
         String var5 = var4.has(var1, var2, var3);
         if (var5 != null) {
            return var5;
         }

         var4 = var4.next;
      }

      return null;
   }

   private void _handleSpillOverflow(int var1, CharsToNameCanonicalizer.Bucket var2, int var3) {
      BitSet var4 = this._overflows;
      if (var4 == null) {
         var4 = new BitSet();
         this._overflows = var4;
         var4.set(var1);
      } else if (var4.get(var1)) {
         if (JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
            this.reportTooManyCollisions(100);
         }

         this._canonicalize = false;
      } else {
         this._overflows.set(var1);
      }

      this._symbols[var3] = var2.symbol;
      this._buckets[var1] = null;
      this._size -= var2.length;
      this._longestCollisionList = -1;
   }

   private static int _thresholdSize(int var0) {
      return var0 - (var0 >> 2);
   }

   private void copyArrays() {
      String[] var1 = this._symbols;
      this._symbols = (String[])Arrays.copyOf(var1, var1.length);
      CharsToNameCanonicalizer.Bucket[] var2 = this._buckets;
      this._buckets = (CharsToNameCanonicalizer.Bucket[])Arrays.copyOf(var2, var2.length);
   }

   public static CharsToNameCanonicalizer createRoot() {
      long var0 = System.currentTimeMillis();
      return createRoot((int)var0 + (int)(var0 >>> 32) | 1);
   }

   protected static CharsToNameCanonicalizer createRoot(int var0) {
      return new CharsToNameCanonicalizer(var0);
   }

   private void mergeChild(CharsToNameCanonicalizer.TableInfo var1) {
      int var2 = var1.size;
      CharsToNameCanonicalizer.TableInfo var3 = (CharsToNameCanonicalizer.TableInfo)this._tableInfo.get();
      if (var2 != var3.size) {
         if (var2 > 12000) {
            var1 = CharsToNameCanonicalizer.TableInfo.createInitial(64);
         }

         this._tableInfo.compareAndSet(var3, var1);
      }
   }

   private void rehash() {
      String[] var1 = this._symbols;
      int var2 = var1.length;
      int var3 = var2 + var2;
      if (var3 > 65536) {
         this._size = 0;
         this._canonicalize = false;
         this._symbols = new String[64];
         this._buckets = new CharsToNameCanonicalizer.Bucket[32];
         this._indexMask = 63;
         this._hashShared = false;
      } else {
         CharsToNameCanonicalizer.Bucket[] var4 = this._buckets;
         this._symbols = new String[var3];
         this._buckets = new CharsToNameCanonicalizer.Bucket[var3 >> 1];
         this._indexMask = var3 - 1;
         this._sizeThreshold = _thresholdSize(var3);
         int var5 = 0;
         int var6 = 0;

         int var9;
         CharsToNameCanonicalizer.Bucket var12;
         for(var3 = 0; var5 < var2; var3 = var9) {
            String var7 = var1[var5];
            int var8 = var6;
            var9 = var3;
            if (var7 != null) {
               var8 = var6 + 1;
               var6 = this._hashToIndex(this.calcHash(var7));
               String[] var10 = this._symbols;
               if (var10[var6] == null) {
                  var10[var6] = var7;
                  var9 = var3;
               } else {
                  var6 >>= 1;
                  var12 = new CharsToNameCanonicalizer.Bucket(var7, this._buckets[var6]);
                  this._buckets[var6] = var12;
                  var9 = Math.max(var3, var12.length);
               }
            }

            ++var5;
            var6 = var8;
         }

         byte var14 = 0;
         var5 = var3;
         var9 = var6;

         for(var3 = var14; var3 < var2 >> 1; var5 = var6) {
            CharsToNameCanonicalizer.Bucket var11 = var4[var3];
            var6 = var5;

            for(var5 = var9; var11 != null; var11 = var11.next) {
               ++var5;
               String var15 = var11.symbol;
               var9 = this._hashToIndex(this.calcHash(var15));
               String[] var13 = this._symbols;
               if (var13[var9] == null) {
                  var13[var9] = var15;
               } else {
                  var9 >>= 1;
                  var12 = new CharsToNameCanonicalizer.Bucket(var15, this._buckets[var9]);
                  this._buckets[var9] = var12;
                  var6 = Math.max(var6, var12.length);
               }
            }

            ++var3;
            var9 = var5;
         }

         this._longestCollisionList = var5;
         this._overflows = null;
         if (var9 != this._size) {
            throw new IllegalStateException(String.format("Internal error on SymbolTable.rehash(): had %d entries; now have %d", this._size, var9));
         }
      }
   }

   public int _hashToIndex(int var1) {
      var1 += var1 >>> 15;
      var1 ^= var1 << 7;
      return var1 + (var1 >>> 3) & this._indexMask;
   }

   public int bucketCount() {
      return this._symbols.length;
   }

   public int calcHash(String var1) {
      int var2 = var1.length();
      int var3 = this._seed;

      int var4;
      for(var4 = 0; var4 < var2; ++var4) {
         var3 = var3 * 33 + var1.charAt(var4);
      }

      var4 = var3;
      if (var3 == 0) {
         var4 = 1;
      }

      return var4;
   }

   public int calcHash(char[] var1, int var2, int var3) {
      int var4 = this._seed;

      for(int var5 = var2; var5 < var3 + var2; ++var5) {
         var4 = var4 * 33 + var1[var5];
      }

      var2 = var4;
      if (var4 == 0) {
         var2 = 1;
      }

      return var2;
   }

   public int collisionCount() {
      CharsToNameCanonicalizer.Bucket[] var1 = this._buckets;
      int var2 = var1.length;
      int var3 = 0;

      int var4;
      int var6;
      for(var4 = 0; var3 < var2; var4 = var6) {
         CharsToNameCanonicalizer.Bucket var5 = var1[var3];
         var6 = var4;
         if (var5 != null) {
            var6 = var4 + var5.length;
         }

         ++var3;
      }

      return var4;
   }

   public String findSymbol(char[] var1, int var2, int var3, int var4) {
      if (var3 < 1) {
         return "";
      } else if (!this._canonicalize) {
         return new String(var1, var2, var3);
      } else {
         int var5 = this._hashToIndex(var4);
         String var6 = this._symbols[var5];
         if (var6 != null) {
            if (var6.length() == var3) {
               int var7 = 0;

               while(var6.charAt(var7) == var1[var2 + var7]) {
                  int var8 = var7 + 1;
                  var7 = var8;
                  if (var8 == var3) {
                     return var6;
                  }
               }
            }

            CharsToNameCanonicalizer.Bucket var10 = this._buckets[var5 >> 1];
            if (var10 != null) {
               String var9 = var10.has(var1, var2, var3);
               if (var9 != null) {
                  return var9;
               }

               var6 = this._findSymbol2(var1, var2, var3, var10.next);
               if (var6 != null) {
                  return var6;
               }
            }
         }

         return this._addSymbol(var1, var2, var3, var4, var5);
      }
   }

   public int hashSeed() {
      return this._seed;
   }

   public CharsToNameCanonicalizer makeChild(int var1) {
      return new CharsToNameCanonicalizer(this, var1, this._seed, (CharsToNameCanonicalizer.TableInfo)this._tableInfo.get());
   }

   public int maxCollisionLength() {
      return this._longestCollisionList;
   }

   public boolean maybeDirty() {
      return this._hashShared ^ true;
   }

   public void release() {
      if (this.maybeDirty()) {
         CharsToNameCanonicalizer var1 = this._parent;
         if (var1 != null && this._canonicalize) {
            var1.mergeChild(new CharsToNameCanonicalizer.TableInfo(this));
            this._hashShared = true;
         }

      }
   }

   protected void reportTooManyCollisions(int var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Longest collision chain in symbol table (of size ");
      var2.append(this._size);
      var2.append(") now exceeds maximum, ");
      var2.append(var1);
      var2.append(" -- suspect a DoS attack based on hash collisions");
      throw new IllegalStateException(var2.toString());
   }

   public int size() {
      AtomicReference var1 = this._tableInfo;
      return var1 != null ? ((CharsToNameCanonicalizer.TableInfo)var1.get()).size : this._size;
   }

   protected void verifyInternalConsistency() {
      int var1 = this._symbols.length;
      int var2 = 0;

      int var3;
      int var4;
      for(var3 = 0; var2 < var1; var3 = var4) {
         var4 = var3;
         if (this._symbols[var2] != null) {
            var4 = var3 + 1;
         }

         ++var2;
      }

      for(var4 = 0; var4 < var1 >> 1; ++var4) {
         for(CharsToNameCanonicalizer.Bucket var5 = this._buckets[var4]; var5 != null; var5 = var5.next) {
            ++var3;
         }
      }

      if (var3 != this._size) {
         throw new IllegalStateException(String.format("Internal error: expected internal size %d vs calculated count %d", this._size, var3));
      }
   }

   static final class Bucket {
      public final int length;
      public final CharsToNameCanonicalizer.Bucket next;
      public final String symbol;

      public Bucket(String var1, CharsToNameCanonicalizer.Bucket var2) {
         this.symbol = var1;
         this.next = var2;
         int var3 = 1;
         if (var2 != null) {
            var3 = 1 + var2.length;
         }

         this.length = var3;
      }

      public String has(char[] var1, int var2, int var3) {
         if (this.symbol.length() != var3) {
            return null;
         } else {
            int var4 = 0;

            while(this.symbol.charAt(var4) == var1[var2 + var4]) {
               int var5 = var4 + 1;
               var4 = var5;
               if (var5 >= var3) {
                  return this.symbol;
               }
            }

            return null;
         }
      }
   }

   private static final class TableInfo {
      final CharsToNameCanonicalizer.Bucket[] buckets;
      final int longestCollisionList;
      final int size;
      final String[] symbols;

      public TableInfo(int var1, int var2, String[] var3, CharsToNameCanonicalizer.Bucket[] var4) {
         this.size = var1;
         this.longestCollisionList = var2;
         this.symbols = var3;
         this.buckets = var4;
      }

      public TableInfo(CharsToNameCanonicalizer var1) {
         this.size = var1._size;
         this.longestCollisionList = var1._longestCollisionList;
         this.symbols = var1._symbols;
         this.buckets = var1._buckets;
      }

      public static CharsToNameCanonicalizer.TableInfo createInitial(int var0) {
         return new CharsToNameCanonicalizer.TableInfo(0, 0, new String[var0], new CharsToNameCanonicalizer.Bucket[var0 >> 1]);
      }
   }
}
