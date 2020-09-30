package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
   private static final byte ABSENT = -1;
   private static final int BYTE_MASK = 255;
   private static final int BYTE_MAX_SIZE = 128;
   static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap((Object)null, new Object[0], 0);
   private static final int SHORT_MASK = 65535;
   private static final int SHORT_MAX_SIZE = 32768;
   private static final long serialVersionUID = 0L;
   final transient Object[] alternatingKeysAndValues;
   private final transient Object hashTable;
   private final transient int size;

   private RegularImmutableMap(Object var1, Object[] var2, int var3) {
      this.hashTable = var1;
      this.alternatingKeysAndValues = var2;
      this.size = var3;
   }

   static <K, V> RegularImmutableMap<K, V> create(int var0, Object[] var1) {
      if (var0 == 0) {
         return (RegularImmutableMap)EMPTY;
      } else if (var0 == 1) {
         CollectPreconditions.checkEntryNotNull(var1[0], var1[1]);
         return new RegularImmutableMap((Object)null, var1, 1);
      } else {
         Preconditions.checkPositionIndex(var0, var1.length >> 1);
         return new RegularImmutableMap(createHashTable(var1, var0, ImmutableSet.chooseTableSize(var0), 0), var1, var0);
      }
   }

   static Object createHashTable(Object[] var0, int var1, int var2, int var3) {
      if (var1 == 1) {
         CollectPreconditions.checkEntryNotNull(var0[var3], var0[var3 ^ 1]);
         return null;
      } else {
         int var4 = var2 - 1;
         byte var5 = 0;
         byte var6 = 0;
         byte var7 = 0;
         Object var9;
         Object var10;
         int var11;
         int var12;
         int var13;
         if (var2 <= 128) {
            byte[] var15 = new byte[var2];
            Arrays.fill(var15, (byte)-1);

            for(var2 = var7; var2 < var1; ++var2) {
               var11 = var2 * 2 + var3;
               var9 = var0[var11];
               var10 = var0[var11 ^ 1];
               CollectPreconditions.checkEntryNotNull(var9, var10);
               var13 = Hashing.smear(var9.hashCode());

               while(true) {
                  var12 = var13 & var4;
                  var13 = var15[var12] & 255;
                  if (var13 == 255) {
                     var15[var12] = (byte)((byte)var11);
                     break;
                  }

                  if (var0[var13].equals(var9)) {
                     throw duplicateKeyException(var9, var10, var0, var13);
                  }

                  var13 = var12 + 1;
               }
            }

            return var15;
         } else if (var2 <= 32768) {
            short[] var16 = new short[var2];
            Arrays.fill(var16, (short)-1);

            for(var2 = var5; var2 < var1; ++var2) {
               var11 = var2 * 2 + var3;
               var9 = var0[var11];
               Object var14 = var0[var11 ^ 1];
               CollectPreconditions.checkEntryNotNull(var9, var14);
               var13 = Hashing.smear(var9.hashCode());

               while(true) {
                  var12 = var13 & var4;
                  var13 = var16[var12] & '\uffff';
                  if (var13 == 65535) {
                     var16[var12] = (short)((short)var11);
                     break;
                  }

                  if (var0[var13].equals(var9)) {
                     throw duplicateKeyException(var9, var14, var0, var13);
                  }

                  var13 = var12 + 1;
               }
            }

            return var16;
         } else {
            int[] var8 = new int[var2];
            Arrays.fill(var8, -1);

            for(var2 = var6; var2 < var1; ++var2) {
               var11 = var2 * 2 + var3;
               var9 = var0[var11];
               var10 = var0[var11 ^ 1];
               CollectPreconditions.checkEntryNotNull(var9, var10);
               var13 = Hashing.smear(var9.hashCode());

               while(true) {
                  var13 &= var4;
                  var12 = var8[var13];
                  if (var12 == -1) {
                     var8[var13] = var11;
                     break;
                  }

                  if (var0[var12].equals(var9)) {
                     throw duplicateKeyException(var9, var10, var0, var12);
                  }

                  ++var13;
               }
            }

            return var8;
         }
      }
   }

   private static IllegalArgumentException duplicateKeyException(Object var0, Object var1, Object[] var2, int var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append("Multiple entries with same key: ");
      var4.append(var0);
      var4.append("=");
      var4.append(var1);
      var4.append(" and ");
      var4.append(var2[var3]);
      var4.append("=");
      var4.append(var2[var3 ^ 1]);
      return new IllegalArgumentException(var4.toString());
   }

   static Object get(@NullableDecl Object var0, @NullableDecl Object[] var1, int var2, int var3, @NullableDecl Object var4) {
      Object var5 = null;
      if (var4 == null) {
         return null;
      } else if (var2 == 1) {
         var0 = var5;
         if (var1[var3].equals(var4)) {
            var0 = var1[var3 ^ 1];
         }

         return var0;
      } else if (var0 == null) {
         return null;
      } else {
         int var6;
         if (var0 instanceof byte[]) {
            byte[] var9 = (byte[])var0;
            var3 = var9.length;
            var2 = Hashing.smear(var4.hashCode());

            while(true) {
               var6 = var2 & var3 - 1;
               var2 = var9[var6] & 255;
               if (var2 == 255) {
                  return null;
               }

               if (var1[var2].equals(var4)) {
                  return var1[var2 ^ 1];
               }

               var2 = var6 + 1;
            }
         } else if (var0 instanceof short[]) {
            short[] var8 = (short[])var0;
            var3 = var8.length;
            var2 = Hashing.smear(var4.hashCode());

            while(true) {
               var6 = var2 & var3 - 1;
               var2 = var8[var6] & '\uffff';
               if (var2 == 65535) {
                  return null;
               }

               if (var1[var2].equals(var4)) {
                  return var1[var2 ^ 1];
               }

               var2 = var6 + 1;
            }
         } else {
            int[] var7 = (int[])var0;
            var3 = var7.length;
            var2 = Hashing.smear(var4.hashCode());

            while(true) {
               var6 = var2 & var3 - 1;
               var2 = var7[var6];
               if (var2 == -1) {
                  return null;
               }

               if (var1[var2].equals(var4)) {
                  return var1[var2 ^ 1];
               }

               var2 = var6 + 1;
            }
         }
      }
   }

   ImmutableSet<Entry<K, V>> createEntrySet() {
      return new RegularImmutableMap.EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
   }

   ImmutableSet<K> createKeySet() {
      return new RegularImmutableMap.KeySet(this, new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
   }

   ImmutableCollection<V> createValues() {
      return new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
   }

   @NullableDecl
   public V get(@NullableDecl Object var1) {
      return get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, var1);
   }

   boolean isPartialView() {
      return false;
   }

   public int size() {
      return this.size;
   }

   static class EntrySet<K, V> extends ImmutableSet<Entry<K, V>> {
      private final transient Object[] alternatingKeysAndValues;
      private final transient int keyOffset;
      private final transient ImmutableMap<K, V> map;
      private final transient int size;

      EntrySet(ImmutableMap<K, V> var1, Object[] var2, int var3, int var4) {
         this.map = var1;
         this.alternatingKeysAndValues = var2;
         this.keyOffset = var3;
         this.size = var4;
      }

      public boolean contains(Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Entry var5 = (Entry)var1;
            var1 = var5.getKey();
            Object var6 = var5.getValue();
            var4 = var3;
            if (var6 != null) {
               var4 = var3;
               if (var6.equals(this.map.get(var1))) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      int copyIntoArray(Object[] var1, int var2) {
         return this.asList().copyIntoArray(var1, var2);
      }

      ImmutableList<Entry<K, V>> createAsList() {
         return new ImmutableList<Entry<K, V>>() {
            public Entry<K, V> get(int var1) {
               Preconditions.checkElementIndex(var1, EntrySet.this.size);
               Object[] var2 = EntrySet.this.alternatingKeysAndValues;
               var1 *= 2;
               return new SimpleImmutableEntry(var2[EntrySet.this.keyOffset + var1], EntrySet.this.alternatingKeysAndValues[var1 + (EntrySet.this.keyOffset ^ 1)]);
            }

            public boolean isPartialView() {
               return true;
            }

            public int size() {
               return EntrySet.this.size;
            }
         };
      }

      boolean isPartialView() {
         return true;
      }

      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.asList().iterator();
      }

      public int size() {
         return this.size;
      }
   }

   static final class KeySet<K> extends ImmutableSet<K> {
      private final transient ImmutableList<K> list;
      private final transient ImmutableMap<K, ?> map;

      KeySet(ImmutableMap<K, ?> var1, ImmutableList<K> var2) {
         this.map = var1;
         this.list = var2;
      }

      public ImmutableList<K> asList() {
         return this.list;
      }

      public boolean contains(@NullableDecl Object var1) {
         boolean var2;
         if (this.map.get(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      int copyIntoArray(Object[] var1, int var2) {
         return this.asList().copyIntoArray(var1, var2);
      }

      boolean isPartialView() {
         return true;
      }

      public UnmodifiableIterator<K> iterator() {
         return this.asList().iterator();
      }

      public int size() {
         return this.map.size();
      }
   }

   static final class KeysOrValuesAsList extends ImmutableList<Object> {
      private final transient Object[] alternatingKeysAndValues;
      private final transient int offset;
      private final transient int size;

      KeysOrValuesAsList(Object[] var1, int var2, int var3) {
         this.alternatingKeysAndValues = var1;
         this.offset = var2;
         this.size = var3;
      }

      public Object get(int var1) {
         Preconditions.checkElementIndex(var1, this.size);
         return this.alternatingKeysAndValues[var1 * 2 + this.offset];
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return this.size;
      }
   }
}
