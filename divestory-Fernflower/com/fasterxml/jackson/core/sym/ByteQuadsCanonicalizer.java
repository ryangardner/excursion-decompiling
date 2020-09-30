package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteQuadsCanonicalizer {
   private static final int DEFAULT_T_SIZE = 64;
   static final int MAX_ENTRIES_FOR_REUSE = 6000;
   private static final int MAX_T_SIZE = 65536;
   private static final int MIN_HASH_SIZE = 16;
   private static final int MULT = 33;
   private static final int MULT2 = 65599;
   private static final int MULT3 = 31;
   private int _count;
   private final boolean _failOnDoS;
   private int[] _hashArea;
   private boolean _hashShared;
   private int _hashSize;
   private boolean _intern;
   private int _longNameOffset;
   private String[] _names;
   private final ByteQuadsCanonicalizer _parent;
   private int _secondaryStart;
   private final int _seed;
   private int _spilloverEnd;
   private final AtomicReference<ByteQuadsCanonicalizer.TableInfo> _tableInfo;
   private int _tertiaryShift;
   private int _tertiaryStart;

   private ByteQuadsCanonicalizer(int var1, boolean var2, int var3, boolean var4) {
      this._parent = null;
      this._seed = var3;
      this._intern = var2;
      this._failOnDoS = var4;
      byte var5 = 16;
      if (var1 < 16) {
         var3 = 16;
      } else {
         var3 = var1;
         if ((var1 - 1 & var1) != 0) {
            for(var3 = var5; var3 < var1; var3 += var3) {
            }
         }
      }

      this._tableInfo = new AtomicReference(ByteQuadsCanonicalizer.TableInfo.createInitial(var3));
   }

   private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer var1, boolean var2, int var3, boolean var4, ByteQuadsCanonicalizer.TableInfo var5) {
      this._parent = var1;
      this._seed = var3;
      this._intern = var2;
      this._failOnDoS = var4;
      this._tableInfo = null;
      this._count = var5.count;
      var3 = var5.size;
      this._hashSize = var3;
      var3 <<= 2;
      this._secondaryStart = var3;
      this._tertiaryStart = var3 + (var3 >> 1);
      this._tertiaryShift = var5.tertiaryShift;
      this._hashArea = var5.mainHash;
      this._names = var5.names;
      this._spilloverEnd = var5.spilloverEnd;
      this._longNameOffset = var5.longNameOffset;
      this._hashShared = true;
   }

   private int _appendLongName(int[] var1, int var2) {
      int var3 = this._longNameOffset;
      int var4 = var3 + var2;
      int[] var5 = this._hashArea;
      if (var4 > var5.length) {
         int var6 = var5.length;
         int var7 = Math.min(4096, this._hashSize);
         int var8 = this._hashArea.length;
         var4 = Math.max(var4 - var6, var7);
         this._hashArea = Arrays.copyOf(this._hashArea, var8 + var4);
      }

      System.arraycopy(var1, 0, this._hashArea, var3, var2);
      this._longNameOffset += var2;
      return var3;
   }

   private final int _calcOffset(int var1) {
      return (var1 & this._hashSize - 1) << 2;
   }

   static int _calcTertiaryShift(int var0) {
      var0 >>= 2;
      if (var0 < 64) {
         return 4;
      } else if (var0 <= 256) {
         return 5;
      } else {
         return var0 <= 1024 ? 6 : 7;
      }
   }

   private boolean _checkNeedForRehash() {
      if (this._count > this._hashSize >> 1) {
         int var1 = this._spilloverEnd;
         int var2 = this._spilloverStart();
         int var3 = this._count;
         if (var1 - var2 >> 2 > var3 + 1 >> 7 || (double)var3 > (double)this._hashSize * 0.8D) {
            return true;
         }
      }

      return false;
   }

   private int _findOffsetForAdd(int var1) {
      int var2 = this._calcOffset(var1);
      int[] var3 = this._hashArea;
      if (var3[var2 + 3] == 0) {
         return var2;
      } else if (this._checkNeedForRehash()) {
         return this._resizeAndFindOffsetForAdd(var1);
      } else {
         int var4 = this._secondaryStart + (var2 >> 3 << 2);
         if (var3[var4 + 3] == 0) {
            return var4;
         } else {
            var4 = this._tertiaryStart;
            int var5 = this._tertiaryShift;
            var4 += var2 >> var5 + 2 << var5;

            for(var2 = var4; var2 < (1 << var5) + var4; var2 += 4) {
               if (var3[var2 + 3] == 0) {
                  return var2;
               }
            }

            var2 = this._spilloverEnd;
            var4 = var2 + 4;
            this._spilloverEnd = var4;
            if (var4 >= this._hashSize << 3) {
               if (this._failOnDoS) {
                  this._reportTooManyCollisions();
               }

               return this._resizeAndFindOffsetForAdd(var1);
            } else {
               return var2;
            }
         }
      }
   }

   private String _findSecondary(int var1, int var2) {
      int var3 = this._tertiaryStart;
      int var4 = this._tertiaryShift;
      var3 += var1 >> var4 + 2 << var4;
      int[] var5 = this._hashArea;

      for(var1 = var3; var1 < (1 << var4) + var3; var1 += 4) {
         int var6 = var5[var1 + 3];
         if (var2 == var5[var1] && 1 == var6) {
            return this._names[var1 >> 2];
         }

         if (var6 == 0) {
            return null;
         }
      }

      for(var1 = this._spilloverStart(); var1 < this._spilloverEnd; var1 += 4) {
         if (var2 == var5[var1] && 1 == var5[var1 + 3]) {
            return this._names[var1 >> 2];
         }
      }

      return null;
   }

   private String _findSecondary(int var1, int var2, int var3) {
      int var4 = this._tertiaryStart;
      int var5 = this._tertiaryShift;
      var4 += var1 >> var5 + 2 << var5;
      int[] var6 = this._hashArea;

      for(var1 = var4; var1 < (1 << var5) + var4; var1 += 4) {
         int var7 = var6[var1 + 3];
         if (var2 == var6[var1] && var3 == var6[var1 + 1] && 2 == var7) {
            return this._names[var1 >> 2];
         }

         if (var7 == 0) {
            return null;
         }
      }

      for(var1 = this._spilloverStart(); var1 < this._spilloverEnd; var1 += 4) {
         if (var2 == var6[var1] && var3 == var6[var1 + 1] && 2 == var6[var1 + 3]) {
            return this._names[var1 >> 2];
         }
      }

      return null;
   }

   private String _findSecondary(int var1, int var2, int var3, int var4) {
      int var5 = this._tertiaryStart;
      int var6 = this._tertiaryShift;
      var5 += var1 >> var6 + 2 << var6;
      int[] var7 = this._hashArea;

      for(var1 = var5; var1 < (1 << var6) + var5; var1 += 4) {
         int var8 = var7[var1 + 3];
         if (var2 == var7[var1] && var3 == var7[var1 + 1] && var4 == var7[var1 + 2] && 3 == var8) {
            return this._names[var1 >> 2];
         }

         if (var8 == 0) {
            return null;
         }
      }

      for(var1 = this._spilloverStart(); var1 < this._spilloverEnd; var1 += 4) {
         if (var2 == var7[var1] && var3 == var7[var1 + 1] && var4 == var7[var1 + 2] && 3 == var7[var1 + 3]) {
            return this._names[var1 >> 2];
         }
      }

      return null;
   }

   private String _findSecondary(int var1, int var2, int[] var3, int var4) {
      int var5 = this._tertiaryStart;
      int var6 = this._tertiaryShift;
      var5 += var1 >> var6 + 2 << var6;
      int[] var7 = this._hashArea;

      for(var1 = var5; var1 < (1 << var6) + var5; var1 += 4) {
         int var8 = var7[var1 + 3];
         if (var2 == var7[var1] && var4 == var8 && this._verifyLongName(var3, var4, var7[var1 + 1])) {
            return this._names[var1 >> 2];
         }

         if (var8 == 0) {
            return null;
         }
      }

      for(var1 = this._spilloverStart(); var1 < this._spilloverEnd; var1 += 4) {
         if (var2 == var7[var1] && var4 == var7[var1 + 3] && this._verifyLongName(var3, var4, var7[var1 + 1])) {
            return this._names[var1 >> 2];
         }
      }

      return null;
   }

   private int _resizeAndFindOffsetForAdd(int var1) {
      this.rehash();
      var1 = this._calcOffset(var1);
      int[] var2 = this._hashArea;
      if (var2[var1 + 3] == 0) {
         return var1;
      } else {
         int var3 = this._secondaryStart + (var1 >> 3 << 2);
         if (var2[var3 + 3] == 0) {
            return var3;
         } else {
            var3 = this._tertiaryStart;
            int var4 = this._tertiaryShift;
            var3 += var1 >> var4 + 2 << var4;

            for(var1 = var3; var1 < (1 << var4) + var3; var1 += 4) {
               if (var2[var1 + 3] == 0) {
                  return var1;
               }
            }

            var1 = this._spilloverEnd;
            this._spilloverEnd = var1 + 4;
            return var1;
         }
      }
   }

   private final int _spilloverStart() {
      int var1 = this._hashSize;
      return (var1 << 3) - var1;
   }

   private boolean _verifyLongName(int[] var1, int var2, int var3) {
      int[] var4;
      int var5;
      label64: {
         label65: {
            label66: {
               var4 = this._hashArea;
               byte var7;
               switch(var2) {
               case 4:
                  var2 = 0;
                  break label64;
               case 5:
                  var2 = 0;
                  break label65;
               case 6:
                  var2 = 0;
                  break label66;
               case 7:
                  var7 = 0;
                  break;
               case 8:
                  if (var1[0] != var4[var3]) {
                     return false;
                  }

                  ++var3;
                  var7 = 1;
                  break;
               default:
                  return this._verifyLongName2(var1, var2, var3);
               }

               var5 = var7 + 1;
               if (var1[var7] != var4[var3]) {
                  return false;
               }

               ++var3;
               var2 = var5;
            }

            var5 = var2 + 1;
            if (var1[var2] != var4[var3]) {
               return false;
            }

            ++var3;
            var2 = var5;
         }

         var5 = var2 + 1;
         if (var1[var2] != var4[var3]) {
            return false;
         }

         ++var3;
         var2 = var5;
      }

      var5 = var2 + 1;
      int var6 = var1[var2];
      var2 = var3 + 1;
      if (var6 != var4[var3]) {
         return false;
      } else {
         var3 = var5 + 1;
         var6 = var1[var5];
         var5 = var2 + 1;
         if (var6 != var4[var2]) {
            return false;
         } else if (var1[var3] != var4[var5]) {
            return false;
         } else if (var1[var3 + 1] != var4[var5 + 1]) {
            return false;
         } else {
            return true;
         }
      }
   }

   private boolean _verifyLongName2(int[] var1, int var2, int var3) {
      int var4 = 0;

      while(true) {
         int var5 = var4 + 1;
         if (var1[var4] != this._hashArea[var3]) {
            return false;
         }

         if (var5 >= var2) {
            return true;
         }

         var4 = var5;
         ++var3;
      }
   }

   private void _verifySharing() {
      if (this._hashShared) {
         int[] var1 = this._hashArea;
         this._hashArea = Arrays.copyOf(var1, var1.length);
         String[] var2 = this._names;
         this._names = (String[])Arrays.copyOf(var2, var2.length);
         this._hashShared = false;
      }

   }

   public static ByteQuadsCanonicalizer createRoot() {
      long var0 = System.currentTimeMillis();
      return createRoot((int)var0 + (int)(var0 >>> 32) | 1);
   }

   protected static ByteQuadsCanonicalizer createRoot(int var0) {
      return new ByteQuadsCanonicalizer(64, true, var0, true);
   }

   private void mergeChild(ByteQuadsCanonicalizer.TableInfo var1) {
      int var2 = var1.count;
      ByteQuadsCanonicalizer.TableInfo var3 = (ByteQuadsCanonicalizer.TableInfo)this._tableInfo.get();
      if (var2 != var3.count) {
         if (var2 > 6000) {
            var1 = ByteQuadsCanonicalizer.TableInfo.createInitial(64);
         }

         this._tableInfo.compareAndSet(var3, var1);
      }
   }

   private void nukeSymbols(boolean var1) {
      this._count = 0;
      this._spilloverEnd = this._spilloverStart();
      this._longNameOffset = this._hashSize << 3;
      if (var1) {
         Arrays.fill(this._hashArea, 0);
         Arrays.fill(this._names, (Object)null);
      }

   }

   private void rehash() {
      this._hashShared = false;
      int[] var1 = this._hashArea;
      String[] var2 = this._names;
      int var3 = this._hashSize;
      int var4 = this._count;
      int var5 = var3 + var3;
      int var6 = this._spilloverEnd;
      if (var5 > 65536) {
         this.nukeSymbols(true);
      } else {
         this._hashArea = new int[var1.length + (var3 << 3)];
         this._hashSize = var5;
         var3 = var5 << 2;
         this._secondaryStart = var3;
         this._tertiaryStart = var3 + (var3 >> 1);
         this._tertiaryShift = _calcTertiaryShift(var5);
         this._names = new String[var2.length << 1];
         this.nukeSymbols(false);
         int[] var7 = new int[16];
         var5 = 0;

         int[] var9;
         for(var3 = 0; var5 < var6; var7 = var9) {
            int var8 = var1[var5 + 3];
            if (var8 == 0) {
               var9 = var7;
            } else {
               ++var3;
               String var10 = var2[var5 >> 2];
               if (var8 != 1) {
                  if (var8 != 2) {
                     if (var8 != 3) {
                        var9 = var7;
                        if (var8 > var7.length) {
                           var9 = new int[var8];
                        }

                        System.arraycopy(var1, var1[var5 + 1], var9, 0, var8);
                        this.addName(var10, var9, var8);
                     } else {
                        var7[0] = var1[var5];
                        var7[1] = var1[var5 + 1];
                        var7[2] = var1[var5 + 2];
                        this.addName(var10, var7, 3);
                        var9 = var7;
                     }
                  } else {
                     var7[0] = var1[var5];
                     var7[1] = var1[var5 + 1];
                     this.addName(var10, var7, 2);
                     var9 = var7;
                  }
               } else {
                  var7[0] = var1[var5];
                  this.addName(var10, var7, 1);
                  var9 = var7;
               }
            }

            var5 += 4;
         }

         if (var3 != var4) {
            StringBuilder var11 = new StringBuilder();
            var11.append("Failed rehash(): old count=");
            var11.append(var4);
            var11.append(", copyCount=");
            var11.append(var3);
            throw new IllegalStateException(var11.toString());
         }
      }
   }

   protected void _reportTooManyCollisions() {
      if (this._hashSize > 1024) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Spill-over slots in symbol table with ");
         var1.append(this._count);
         var1.append(" entries, hash area of ");
         var1.append(this._hashSize);
         var1.append(" slots is now full (all ");
         var1.append(this._hashSize >> 3);
         var1.append(" slots -- suspect a DoS attack based on hash collisions. You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
         throw new IllegalStateException(var1.toString());
      }
   }

   public String addName(String var1, int var2) {
      this._verifySharing();
      String var3 = var1;
      if (this._intern) {
         var3 = InternCache.instance.intern(var1);
      }

      int var4 = this._findOffsetForAdd(this.calcHash(var2));
      int[] var5 = this._hashArea;
      var5[var4] = var2;
      var5[var4 + 3] = 1;
      this._names[var4 >> 2] = var3;
      ++this._count;
      return var3;
   }

   public String addName(String var1, int var2, int var3) {
      this._verifySharing();
      String var4 = var1;
      if (this._intern) {
         var4 = InternCache.instance.intern(var1);
      }

      int var5;
      if (var3 == 0) {
         var5 = this.calcHash(var2);
      } else {
         var5 = this.calcHash(var2, var3);
      }

      var5 = this._findOffsetForAdd(var5);
      int[] var6 = this._hashArea;
      var6[var5] = var2;
      var6[var5 + 1] = var3;
      var6[var5 + 3] = 2;
      this._names[var5 >> 2] = var4;
      ++this._count;
      return var4;
   }

   public String addName(String var1, int var2, int var3, int var4) {
      this._verifySharing();
      String var5 = var1;
      if (this._intern) {
         var5 = InternCache.instance.intern(var1);
      }

      int var6 = this._findOffsetForAdd(this.calcHash(var2, var3, var4));
      int[] var7 = this._hashArea;
      var7[var6] = var2;
      var7[var6 + 1] = var3;
      var7[var6 + 2] = var4;
      var7[var6 + 3] = 3;
      this._names[var6 >> 2] = var5;
      ++this._count;
      return var5;
   }

   public String addName(String var1, int[] var2, int var3) {
      this._verifySharing();
      String var4 = var1;
      if (this._intern) {
         var4 = InternCache.instance.intern(var1);
      }

      int[] var7;
      if (var3 != 1) {
         if (var3 != 2) {
            if (var3 != 3) {
               int var5 = this.calcHash(var2, var3);
               int var6 = this._findOffsetForAdd(var5);
               this._hashArea[var6] = var5;
               var5 = this._appendLongName(var2, var3);
               var7 = this._hashArea;
               var7[var6 + 1] = var5;
               var7[var6 + 3] = var3;
               var3 = var6;
            } else {
               var3 = this._findOffsetForAdd(this.calcHash(var2[0], var2[1], var2[2]));
               var7 = this._hashArea;
               var7[var3] = var2[0];
               var7[var3 + 1] = var2[1];
               var7[var3 + 2] = var2[2];
               var7[var3 + 3] = 3;
            }
         } else {
            var3 = this._findOffsetForAdd(this.calcHash(var2[0], var2[1]));
            var7 = this._hashArea;
            var7[var3] = var2[0];
            var7[var3 + 1] = var2[1];
            var7[var3 + 3] = 2;
         }
      } else {
         var3 = this._findOffsetForAdd(this.calcHash(var2[0]));
         var7 = this._hashArea;
         var7[var3] = var2[0];
         var7[var3 + 3] = 1;
      }

      this._names[var3 >> 2] = var4;
      ++this._count;
      return var4;
   }

   public int bucketCount() {
      return this._hashSize;
   }

   public int calcHash(int var1) {
      var1 ^= this._seed;
      var1 += var1 >>> 16;
      var1 ^= var1 << 3;
      return var1 + (var1 >>> 12);
   }

   public int calcHash(int var1, int var2) {
      var1 += var1 >>> 15;
      var1 = (var1 ^ var1 >>> 9) + var2 * 33 ^ this._seed;
      var1 += var1 >>> 16;
      var1 ^= var1 >>> 4;
      return var1 + (var1 << 3);
   }

   public int calcHash(int var1, int var2, int var3) {
      var1 ^= this._seed;
      var1 = ((var1 + (var1 >>> 9)) * 31 + var2) * 33;
      var1 = var1 + (var1 >>> 15) ^ var3;
      var1 += var1 >>> 4;
      var1 += var1 >>> 15;
      return var1 ^ var1 << 9;
   }

   public int calcHash(int[] var1, int var2) {
      if (var2 < 4) {
         throw new IllegalArgumentException();
      } else {
         int var3 = var1[0] ^ this._seed;
         var3 = var3 + (var3 >>> 9) + var1[1];
         var3 = (var3 + (var3 >>> 15)) * 33 ^ var1[2];
         int var4 = var3 + (var3 >>> 4);

         for(var3 = 3; var3 < var2; ++var3) {
            int var5 = var1[var3];
            var4 += var5 ^ var5 >> 21;
         }

         var2 = var4 * 65599;
         var2 += var2 >>> 19;
         return var2 << 5 ^ var2;
      }
   }

   public String findName(int var1) {
      int var2 = this._calcOffset(this.calcHash(var1));
      int[] var3 = this._hashArea;
      int var4 = var3[var2 + 3];
      if (var4 == 1) {
         if (var3[var2] == var1) {
            return this._names[var2 >> 2];
         }
      } else if (var4 == 0) {
         return null;
      }

      var4 = this._secondaryStart + (var2 >> 3 << 2);
      int var5 = var3[var4 + 3];
      if (var5 == 1) {
         if (var3[var4] == var1) {
            return this._names[var4 >> 2];
         }
      } else if (var5 == 0) {
         return null;
      }

      return this._findSecondary(var2, var1);
   }

   public String findName(int var1, int var2) {
      int var3 = this._calcOffset(this.calcHash(var1, var2));
      int[] var4 = this._hashArea;
      int var5 = var4[var3 + 3];
      if (var5 == 2) {
         if (var1 == var4[var3] && var2 == var4[var3 + 1]) {
            return this._names[var3 >> 2];
         }
      } else if (var5 == 0) {
         return null;
      }

      int var6 = this._secondaryStart + (var3 >> 3 << 2);
      var5 = var4[var6 + 3];
      if (var5 == 2) {
         if (var1 == var4[var6] && var2 == var4[var6 + 1]) {
            return this._names[var6 >> 2];
         }
      } else if (var5 == 0) {
         return null;
      }

      return this._findSecondary(var3, var1, var2);
   }

   public String findName(int var1, int var2, int var3) {
      int var4 = this._calcOffset(this.calcHash(var1, var2, var3));
      int[] var5 = this._hashArea;
      int var6 = var5[var4 + 3];
      if (var6 == 3) {
         if (var1 == var5[var4] && var5[var4 + 1] == var2 && var5[var4 + 2] == var3) {
            return this._names[var4 >> 2];
         }
      } else if (var6 == 0) {
         return null;
      }

      int var7 = this._secondaryStart + (var4 >> 3 << 2);
      var6 = var5[var7 + 3];
      if (var6 == 3) {
         if (var1 == var5[var7] && var5[var7 + 1] == var2 && var5[var7 + 2] == var3) {
            return this._names[var7 >> 2];
         }
      } else if (var6 == 0) {
         return null;
      }

      return this._findSecondary(var4, var1, var2, var3);
   }

   public String findName(int[] var1, int var2) {
      if (var2 < 4) {
         if (var2 != 1) {
            if (var2 != 2) {
               return var2 != 3 ? "" : this.findName(var1[0], var1[1], var1[2]);
            } else {
               return this.findName(var1[0], var1[1]);
            }
         } else {
            return this.findName(var1[0]);
         }
      } else {
         int var3 = this.calcHash(var1, var2);
         int var4 = this._calcOffset(var3);
         int[] var5 = this._hashArea;
         int var6 = var5[var4 + 3];
         if (var3 == var5[var4] && var6 == var2 && this._verifyLongName(var1, var2, var5[var4 + 1])) {
            return this._names[var4 >> 2];
         } else if (var6 == 0) {
            return null;
         } else {
            var6 = this._secondaryStart + (var4 >> 3 << 2);
            int var7 = var5[var6 + 3];
            return var3 == var5[var6] && var7 == var2 && this._verifyLongName(var1, var2, var5[var6 + 1]) ? this._names[var6 >> 2] : this._findSecondary(var4, var3, var1, var2);
         }
      }
   }

   public int hashSeed() {
      return this._seed;
   }

   public ByteQuadsCanonicalizer makeChild(int var1) {
      return new ByteQuadsCanonicalizer(this, JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(var1), this._seed, JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(var1), (ByteQuadsCanonicalizer.TableInfo)this._tableInfo.get());
   }

   public boolean maybeDirty() {
      return this._hashShared ^ true;
   }

   public int primaryCount() {
      int var1 = this._secondaryStart;
      int var2 = 0;

      int var4;
      for(int var3 = 3; var3 < var1; var2 = var4) {
         var4 = var2;
         if (this._hashArea[var3] != 0) {
            var4 = var2 + 1;
         }

         var3 += 4;
      }

      return var2;
   }

   public void release() {
      if (this._parent != null && this.maybeDirty()) {
         this._parent.mergeChild(new ByteQuadsCanonicalizer.TableInfo(this));
         this._hashShared = true;
      }

   }

   public int secondaryCount() {
      int var1 = this._secondaryStart + 3;
      int var2 = this._tertiaryStart;

      int var3;
      int var4;
      for(var3 = 0; var1 < var2; var3 = var4) {
         var4 = var3;
         if (this._hashArea[var1] != 0) {
            var4 = var3 + 1;
         }

         var1 += 4;
      }

      return var3;
   }

   public int size() {
      AtomicReference var1 = this._tableInfo;
      return var1 != null ? ((ByteQuadsCanonicalizer.TableInfo)var1.get()).count : this._count;
   }

   public int spilloverCount() {
      return this._spilloverEnd - this._spilloverStart() >> 2;
   }

   public int tertiaryCount() {
      int var1 = this._tertiaryStart + 3;
      int var2 = this._hashSize;
      int var3 = 0;

      int var5;
      for(int var4 = var1; var4 < var2 + var1; var3 = var5) {
         var5 = var3;
         if (this._hashArea[var4] != 0) {
            var5 = var3 + 1;
         }

         var4 += 4;
      }

      return var3;
   }

   public String toString() {
      int var1 = this.primaryCount();
      int var2 = this.secondaryCount();
      int var3 = this.tertiaryCount();
      int var4 = this.spilloverCount();
      int var5 = this.totalCount();
      return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", this.getClass().getName(), this._count, this._hashSize, var1, var2, var3, var4, var1 + var2 + var3 + var4, var5);
   }

   public int totalCount() {
      int var1 = this._hashSize;
      int var2 = 3;

      int var3;
      int var4;
      for(var3 = 0; var2 < var1 << 3; var3 = var4) {
         var4 = var3;
         if (this._hashArea[var2] != 0) {
            var4 = var3 + 1;
         }

         var2 += 4;
      }

      return var3;
   }

   private static final class TableInfo {
      public final int count;
      public final int longNameOffset;
      public final int[] mainHash;
      public final String[] names;
      public final int size;
      public final int spilloverEnd;
      public final int tertiaryShift;

      public TableInfo(int var1, int var2, int var3, int[] var4, String[] var5, int var6, int var7) {
         this.size = var1;
         this.count = var2;
         this.tertiaryShift = var3;
         this.mainHash = var4;
         this.names = var5;
         this.spilloverEnd = var6;
         this.longNameOffset = var7;
      }

      public TableInfo(ByteQuadsCanonicalizer var1) {
         this.size = var1._hashSize;
         this.count = var1._count;
         this.tertiaryShift = var1._tertiaryShift;
         this.mainHash = var1._hashArea;
         this.names = var1._names;
         this.spilloverEnd = var1._spilloverEnd;
         this.longNameOffset = var1._longNameOffset;
      }

      public static ByteQuadsCanonicalizer.TableInfo createInitial(int var0) {
         int var1 = var0 << 3;
         return new ByteQuadsCanonicalizer.TableInfo(var0, 0, ByteQuadsCanonicalizer._calcTertiaryShift(var0), new int[var1], new String[var0 << 1], var1 - var0, var1);
      }
   }
}
