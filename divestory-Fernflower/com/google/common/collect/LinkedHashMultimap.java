package com.google.common.collect;

import com.google.common.base.Objects;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class LinkedHashMultimap<K, V> extends LinkedHashMultimapGwtSerializationDependencies<K, V> {
   private static final int DEFAULT_KEY_CAPACITY = 16;
   private static final int DEFAULT_VALUE_SET_CAPACITY = 2;
   static final double VALUE_SET_LOAD_FACTOR = 1.0D;
   private static final long serialVersionUID = 1L;
   private transient LinkedHashMultimap.ValueEntry<K, V> multimapHeaderEntry;
   transient int valueSetCapacity = 2;

   private LinkedHashMultimap(int var1, int var2) {
      super(Platform.newLinkedHashMapWithExpectedSize(var1));
      CollectPreconditions.checkNonnegative(var2, "expectedValuesPerKey");
      this.valueSetCapacity = var2;
      LinkedHashMultimap.ValueEntry var3 = new LinkedHashMultimap.ValueEntry((Object)null, (Object)null, 0, (LinkedHashMultimap.ValueEntry)null);
      this.multimapHeaderEntry = var3;
      succeedsInMultimap(var3, var3);
   }

   public static <K, V> LinkedHashMultimap<K, V> create() {
      return new LinkedHashMultimap(16, 2);
   }

   public static <K, V> LinkedHashMultimap<K, V> create(int var0, int var1) {
      return new LinkedHashMultimap(Maps.capacity(var0), Maps.capacity(var1));
   }

   public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      LinkedHashMultimap var1 = create(var0.keySet().size(), 2);
      var1.putAll(var0);
      return var1;
   }

   private static <K, V> void deleteFromMultimap(LinkedHashMultimap.ValueEntry<K, V> var0) {
      succeedsInMultimap(var0.getPredecessorInMultimap(), var0.getSuccessorInMultimap());
   }

   private static <K, V> void deleteFromValueSet(LinkedHashMultimap.ValueSetLink<K, V> var0) {
      succeedsInValueSet(var0.getPredecessorInValueSet(), var0.getSuccessorInValueSet());
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      byte var2 = 0;
      LinkedHashMultimap.ValueEntry var3 = new LinkedHashMultimap.ValueEntry((Object)null, (Object)null, 0, (LinkedHashMultimap.ValueEntry)null);
      this.multimapHeaderEntry = var3;
      succeedsInMultimap(var3, var3);
      this.valueSetCapacity = 2;
      int var4 = var1.readInt();
      Map var8 = Platform.newLinkedHashMapWithExpectedSize(12);

      int var5;
      Object var6;
      for(var5 = 0; var5 < var4; ++var5) {
         var6 = var1.readObject();
         var8.put(var6, this.createCollection(var6));
      }

      var4 = var1.readInt();

      for(var5 = var2; var5 < var4; ++var5) {
         Object var7 = var1.readObject();
         var6 = var1.readObject();
         ((Collection)var8.get(var7)).add(var6);
      }

      this.setMap(var8);
   }

   private static <K, V> void succeedsInMultimap(LinkedHashMultimap.ValueEntry<K, V> var0, LinkedHashMultimap.ValueEntry<K, V> var1) {
      var0.setSuccessorInMultimap(var1);
      var1.setPredecessorInMultimap(var0);
   }

   private static <K, V> void succeedsInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var0, LinkedHashMultimap.ValueSetLink<K, V> var1) {
      var0.setSuccessorInValueSet(var1);
      var1.setPredecessorInValueSet(var0);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeInt(this.keySet().size());
      Iterator var2 = this.keySet().iterator();

      while(var2.hasNext()) {
         var1.writeObject(var2.next());
      }

      var1.writeInt(this.size());
      Iterator var3 = this.entries().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var1.writeObject(var4.getKey());
         var1.writeObject(var4.getValue());
      }

   }

   public void clear() {
      super.clear();
      LinkedHashMultimap.ValueEntry var1 = this.multimapHeaderEntry;
      succeedsInMultimap(var1, var1);
   }

   Collection<V> createCollection(K var1) {
      return new LinkedHashMultimap.ValueSet(var1, this.valueSetCapacity);
   }

   Set<V> createCollection() {
      return Platform.newLinkedHashSetWithExpectedSize(this.valueSetCapacity);
   }

   public Set<Entry<K, V>> entries() {
      return super.entries();
   }

   Iterator<Entry<K, V>> entryIterator() {
      return new Iterator<Entry<K, V>>() {
         LinkedHashMultimap.ValueEntry<K, V> nextEntry;
         @NullableDecl
         LinkedHashMultimap.ValueEntry<K, V> toRemove;

         {
            this.nextEntry = LinkedHashMultimap.this.multimapHeaderEntry.successorInMultimap;
         }

         public boolean hasNext() {
            boolean var1;
            if (this.nextEntry != LinkedHashMultimap.this.multimapHeaderEntry) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public Entry<K, V> next() {
            if (this.hasNext()) {
               LinkedHashMultimap.ValueEntry var1 = this.nextEntry;
               this.toRemove = var1;
               this.nextEntry = var1.successorInMultimap;
               return var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            boolean var1;
            if (this.toRemove != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            CollectPreconditions.checkRemove(var1);
            LinkedHashMultimap.this.remove(this.toRemove.getKey(), this.toRemove.getValue());
            this.toRemove = null;
         }
      };
   }

   public Set<K> keySet() {
      return super.keySet();
   }

   public Set<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2) {
      return super.replaceValues(var1, var2);
   }

   Iterator<V> valueIterator() {
      return Maps.valueIterator(this.entryIterator());
   }

   public Collection<V> values() {
      return super.values();
   }

   static final class ValueEntry<K, V> extends ImmutableEntry<K, V> implements LinkedHashMultimap.ValueSetLink<K, V> {
      @NullableDecl
      LinkedHashMultimap.ValueEntry<K, V> nextInValueBucket;
      @NullableDecl
      LinkedHashMultimap.ValueEntry<K, V> predecessorInMultimap;
      @NullableDecl
      LinkedHashMultimap.ValueSetLink<K, V> predecessorInValueSet;
      final int smearedValueHash;
      @NullableDecl
      LinkedHashMultimap.ValueEntry<K, V> successorInMultimap;
      @NullableDecl
      LinkedHashMultimap.ValueSetLink<K, V> successorInValueSet;

      ValueEntry(@NullableDecl K var1, @NullableDecl V var2, int var3, @NullableDecl LinkedHashMultimap.ValueEntry<K, V> var4) {
         super(var1, var2);
         this.smearedValueHash = var3;
         this.nextInValueBucket = var4;
      }

      public LinkedHashMultimap.ValueEntry<K, V> getPredecessorInMultimap() {
         return this.predecessorInMultimap;
      }

      public LinkedHashMultimap.ValueSetLink<K, V> getPredecessorInValueSet() {
         return this.predecessorInValueSet;
      }

      public LinkedHashMultimap.ValueEntry<K, V> getSuccessorInMultimap() {
         return this.successorInMultimap;
      }

      public LinkedHashMultimap.ValueSetLink<K, V> getSuccessorInValueSet() {
         return this.successorInValueSet;
      }

      boolean matchesValue(@NullableDecl Object var1, int var2) {
         boolean var3;
         if (this.smearedValueHash == var2 && Objects.equal(this.getValue(), var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public void setPredecessorInMultimap(LinkedHashMultimap.ValueEntry<K, V> var1) {
         this.predecessorInMultimap = var1;
      }

      public void setPredecessorInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.predecessorInValueSet = var1;
      }

      public void setSuccessorInMultimap(LinkedHashMultimap.ValueEntry<K, V> var1) {
         this.successorInMultimap = var1;
      }

      public void setSuccessorInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.successorInValueSet = var1;
      }
   }

   final class ValueSet extends Sets.ImprovedAbstractSet<V> implements LinkedHashMultimap.ValueSetLink<K, V> {
      private LinkedHashMultimap.ValueSetLink<K, V> firstEntry;
      LinkedHashMultimap.ValueEntry<K, V>[] hashTable;
      private final K key;
      private LinkedHashMultimap.ValueSetLink<K, V> lastEntry;
      private int modCount = 0;
      private int size = 0;

      ValueSet(K var2, int var3) {
         this.key = var2;
         this.firstEntry = this;
         this.lastEntry = this;
         this.hashTable = new LinkedHashMultimap.ValueEntry[Hashing.closedTableSize(var3, 1.0D)];
      }

      private int mask() {
         return this.hashTable.length - 1;
      }

      private void rehashIfNecessary() {
         if (Hashing.needsResizing(this.size, this.hashTable.length, 1.0D)) {
            int var1 = this.hashTable.length * 2;
            LinkedHashMultimap.ValueEntry[] var2 = new LinkedHashMultimap.ValueEntry[var1];
            this.hashTable = var2;

            for(LinkedHashMultimap.ValueSetLink var3 = this.firstEntry; var3 != this; var3 = var3.getSuccessorInValueSet()) {
               LinkedHashMultimap.ValueEntry var4 = (LinkedHashMultimap.ValueEntry)var3;
               int var5 = var4.smearedValueHash & var1 - 1;
               var4.nextInValueBucket = var2[var5];
               var2[var5] = var4;
            }
         }

      }

      public boolean add(@NullableDecl V var1) {
         int var2 = Hashing.smearedHash(var1);
         int var3 = this.mask() & var2;
         LinkedHashMultimap.ValueEntry var4 = this.hashTable[var3];

         for(LinkedHashMultimap.ValueEntry var5 = var4; var5 != null; var5 = var5.nextInValueBucket) {
            if (var5.matchesValue(var1, var2)) {
               return false;
            }
         }

         LinkedHashMultimap.ValueEntry var6 = new LinkedHashMultimap.ValueEntry(this.key, var1, var2, var4);
         LinkedHashMultimap.succeedsInValueSet(this.lastEntry, var6);
         LinkedHashMultimap.succeedsInValueSet(var6, this);
         LinkedHashMultimap.succeedsInMultimap(LinkedHashMultimap.this.multimapHeaderEntry.getPredecessorInMultimap(), var6);
         LinkedHashMultimap.succeedsInMultimap(var6, LinkedHashMultimap.this.multimapHeaderEntry);
         this.hashTable[var3] = var6;
         ++this.size;
         ++this.modCount;
         this.rehashIfNecessary();
         return true;
      }

      public void clear() {
         Arrays.fill(this.hashTable, (Object)null);
         this.size = 0;

         for(LinkedHashMultimap.ValueSetLink var1 = this.firstEntry; var1 != this; var1 = var1.getSuccessorInValueSet()) {
            LinkedHashMultimap.deleteFromMultimap((LinkedHashMultimap.ValueEntry)var1);
         }

         LinkedHashMultimap.succeedsInValueSet(this, this);
         ++this.modCount;
      }

      public boolean contains(@NullableDecl Object var1) {
         int var2 = Hashing.smearedHash(var1);

         for(LinkedHashMultimap.ValueEntry var3 = this.hashTable[this.mask() & var2]; var3 != null; var3 = var3.nextInValueBucket) {
            if (var3.matchesValue(var1, var2)) {
               return true;
            }
         }

         return false;
      }

      public LinkedHashMultimap.ValueSetLink<K, V> getPredecessorInValueSet() {
         return this.lastEntry;
      }

      public LinkedHashMultimap.ValueSetLink<K, V> getSuccessorInValueSet() {
         return this.firstEntry;
      }

      public Iterator<V> iterator() {
         return new Iterator<V>() {
            int expectedModCount;
            LinkedHashMultimap.ValueSetLink<K, V> nextEntry;
            @NullableDecl
            LinkedHashMultimap.ValueEntry<K, V> toRemove;

            {
               this.nextEntry = ValueSet.this.firstEntry;
               this.expectedModCount = ValueSet.this.modCount;
            }

            private void checkForComodification() {
               if (ValueSet.this.modCount != this.expectedModCount) {
                  throw new ConcurrentModificationException();
               }
            }

            public boolean hasNext() {
               this.checkForComodification();
               boolean var1;
               if (this.nextEntry != ValueSet.this) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               return var1;
            }

            public V next() {
               if (this.hasNext()) {
                  LinkedHashMultimap.ValueEntry var1 = (LinkedHashMultimap.ValueEntry)this.nextEntry;
                  Object var2 = var1.getValue();
                  this.toRemove = var1;
                  this.nextEntry = var1.getSuccessorInValueSet();
                  return var2;
               } else {
                  throw new NoSuchElementException();
               }
            }

            public void remove() {
               this.checkForComodification();
               boolean var1;
               if (this.toRemove != null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               CollectPreconditions.checkRemove(var1);
               ValueSet.this.remove(this.toRemove.getValue());
               this.expectedModCount = ValueSet.this.modCount;
               this.toRemove = null;
            }
         };
      }

      public boolean remove(@NullableDecl Object var1) {
         int var2 = Hashing.smearedHash(var1);
         int var3 = this.mask() & var2;
         LinkedHashMultimap.ValueEntry var4 = this.hashTable[var3];

         LinkedHashMultimap.ValueEntry var6;
         for(LinkedHashMultimap.ValueEntry var5 = null; var4 != null; var4 = var6) {
            if (var4.matchesValue(var1, var2)) {
               if (var5 == null) {
                  this.hashTable[var3] = var4.nextInValueBucket;
               } else {
                  var5.nextInValueBucket = var4.nextInValueBucket;
               }

               LinkedHashMultimap.deleteFromValueSet(var4);
               LinkedHashMultimap.deleteFromMultimap(var4);
               --this.size;
               ++this.modCount;
               return true;
            }

            var6 = var4.nextInValueBucket;
            var5 = var4;
         }

         return false;
      }

      public void setPredecessorInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.lastEntry = var1;
      }

      public void setSuccessorInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var1) {
         this.firstEntry = var1;
      }

      public int size() {
         return this.size;
      }
   }

   private interface ValueSetLink<K, V> {
      LinkedHashMultimap.ValueSetLink<K, V> getPredecessorInValueSet();

      LinkedHashMultimap.ValueSetLink<K, V> getSuccessorInValueSet();

      void setPredecessorInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var1);

      void setSuccessorInValueSet(LinkedHashMultimap.ValueSetLink<K, V> var1);
   }
}
