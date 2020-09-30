package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
   private static final long serialVersionUID = 0L;
   @MonotonicNonNullDecl
   private transient Map<K, V> delegate;
   @MonotonicNonNullDecl
   private transient Set<Entry<K, V>> entrySet;
   @MonotonicNonNullDecl
   transient AbstractBiMap<V, K> inverse;
   @MonotonicNonNullDecl
   private transient Set<K> keySet;
   @MonotonicNonNullDecl
   private transient Set<V> valueSet;

   private AbstractBiMap(Map<K, V> var1, AbstractBiMap<V, K> var2) {
      this.delegate = var1;
      this.inverse = var2;
   }

   // $FF: synthetic method
   AbstractBiMap(Map var1, AbstractBiMap var2, Object var3) {
      this(var1, var2);
   }

   AbstractBiMap(Map<K, V> var1, Map<V, K> var2) {
      this.setDelegates(var1, var2);
   }

   private V putInBothMaps(@NullableDecl K var1, @NullableDecl V var2, boolean var3) {
      this.checkKey(var1);
      this.checkValue(var2);
      boolean var4 = this.containsKey(var1);
      if (var4 && Objects.equal(var2, this.get(var1))) {
         return var2;
      } else {
         if (var3) {
            this.inverse().remove(var2);
         } else {
            Preconditions.checkArgument(this.containsValue(var2) ^ true, "value already present: %s", var2);
         }

         Object var5 = this.delegate.put(var1, var2);
         this.updateInverseMap(var1, var4, var5, var2);
         return var5;
      }
   }

   private V removeFromBothMaps(Object var1) {
      var1 = this.delegate.remove(var1);
      this.removeFromInverseMap(var1);
      return var1;
   }

   private void removeFromInverseMap(V var1) {
      this.inverse.delegate.remove(var1);
   }

   private void updateInverseMap(K var1, boolean var2, V var3, V var4) {
      if (var2) {
         this.removeFromInverseMap(var3);
      }

      this.inverse.delegate.put(var4, var1);
   }

   K checkKey(@NullableDecl K var1) {
      return var1;
   }

   V checkValue(@NullableDecl V var1) {
      return var1;
   }

   public void clear() {
      this.delegate.clear();
      this.inverse.delegate.clear();
   }

   public boolean containsValue(@NullableDecl Object var1) {
      return this.inverse.containsKey(var1);
   }

   protected Map<K, V> delegate() {
      return this.delegate;
   }

   public Set<Entry<K, V>> entrySet() {
      Set var1 = this.entrySet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new AbstractBiMap.EntrySet();
         this.entrySet = (Set)var2;
      }

      return (Set)var2;
   }

   Iterator<Entry<K, V>> entrySetIterator() {
      return new Iterator<Entry<K, V>>(this.delegate.entrySet().iterator()) {
         @NullableDecl
         Entry<K, V> entry;
         // $FF: synthetic field
         final Iterator val$iterator;

         {
            this.val$iterator = var2;
         }

         public boolean hasNext() {
            return this.val$iterator.hasNext();
         }

         public Entry<K, V> next() {
            this.entry = (Entry)this.val$iterator.next();
            return AbstractBiMap.this.new BiMapEntry(this.entry);
         }

         public void remove() {
            boolean var1;
            if (this.entry != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            CollectPreconditions.checkRemove(var1);
            Object var2 = this.entry.getValue();
            this.val$iterator.remove();
            AbstractBiMap.this.removeFromInverseMap(var2);
            this.entry = null;
         }
      };
   }

   public V forcePut(@NullableDecl K var1, @NullableDecl V var2) {
      return this.putInBothMaps(var1, var2, true);
   }

   public BiMap<V, K> inverse() {
      return this.inverse;
   }

   public Set<K> keySet() {
      Set var1 = this.keySet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new AbstractBiMap.KeySet();
         this.keySet = (Set)var2;
      }

      return (Set)var2;
   }

   AbstractBiMap<V, K> makeInverse(Map<V, K> var1) {
      return new AbstractBiMap.Inverse(var1, this);
   }

   public V put(@NullableDecl K var1, @NullableDecl V var2) {
      return this.putInBothMaps(var1, var2, false);
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         this.put(var3.getKey(), var3.getValue());
      }

   }

   public V remove(@NullableDecl Object var1) {
      if (this.containsKey(var1)) {
         var1 = this.removeFromBothMaps(var1);
      } else {
         var1 = null;
      }

      return var1;
   }

   void setDelegates(Map<K, V> var1, Map<V, K> var2) {
      Map var3 = this.delegate;
      boolean var4 = true;
      boolean var5;
      if (var3 == null) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5);
      if (this.inverse == null) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5);
      Preconditions.checkArgument(var1.isEmpty());
      Preconditions.checkArgument(var2.isEmpty());
      if (var1 != var2) {
         var5 = var4;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      this.delegate = var1;
      this.inverse = this.makeInverse(var2);
   }

   void setInverse(AbstractBiMap<V, K> var1) {
      this.inverse = var1;
   }

   public Set<V> values() {
      Set var1 = this.valueSet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new AbstractBiMap.ValueSet();
         this.valueSet = (Set)var2;
      }

      return (Set)var2;
   }

   class BiMapEntry extends ForwardingMapEntry<K, V> {
      private final Entry<K, V> delegate;

      BiMapEntry(Entry<K, V> var2) {
         this.delegate = var2;
      }

      protected Entry<K, V> delegate() {
         return this.delegate;
      }

      public V setValue(V var1) {
         AbstractBiMap.this.checkValue(var1);
         Preconditions.checkState(AbstractBiMap.this.entrySet().contains(this), "entry no longer in map");
         if (Objects.equal(var1, this.getValue())) {
            return var1;
         } else {
            Preconditions.checkArgument(AbstractBiMap.this.containsValue(var1) ^ true, "value already present: %s", var1);
            Object var2 = this.delegate.setValue(var1);
            Preconditions.checkState(Objects.equal(var1, AbstractBiMap.this.get(this.getKey())), "entry no longer in map");
            AbstractBiMap.this.updateInverseMap(this.getKey(), true, var2, var1);
            return var2;
         }
      }
   }

   private class EntrySet extends ForwardingSet<Entry<K, V>> {
      final Set<Entry<K, V>> esDelegate;

      private EntrySet() {
         this.esDelegate = AbstractBiMap.this.delegate.entrySet();
      }

      // $FF: synthetic method
      EntrySet(Object var2) {
         this();
      }

      public void clear() {
         AbstractBiMap.this.clear();
      }

      public boolean contains(Object var1) {
         return Maps.containsEntryImpl(this.delegate(), var1);
      }

      public boolean containsAll(Collection<?> var1) {
         return this.standardContainsAll(var1);
      }

      protected Set<Entry<K, V>> delegate() {
         return this.esDelegate;
      }

      public Iterator<Entry<K, V>> iterator() {
         return AbstractBiMap.this.entrySetIterator();
      }

      public boolean remove(Object var1) {
         if (!this.esDelegate.contains(var1)) {
            return false;
         } else {
            Entry var2 = (Entry)var1;
            AbstractBiMap.this.inverse.delegate.remove(var2.getValue());
            this.esDelegate.remove(var2);
            return true;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         return this.standardRemoveAll(var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return this.standardRetainAll(var1);
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public <T> T[] toArray(T[] var1) {
         return this.standardToArray(var1);
      }
   }

   static class Inverse<K, V> extends AbstractBiMap<K, V> {
      private static final long serialVersionUID = 0L;

      Inverse(Map<K, V> var1, AbstractBiMap<V, K> var2) {
         super(var1, var2, null);
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         this.setInverse((AbstractBiMap)var1.readObject());
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         var1.writeObject(this.inverse());
      }

      K checkKey(K var1) {
         return this.inverse.checkValue(var1);
      }

      V checkValue(V var1) {
         return this.inverse.checkKey(var1);
      }

      Object readResolve() {
         return this.inverse().inverse();
      }
   }

   private class KeySet extends ForwardingSet<K> {
      private KeySet() {
      }

      // $FF: synthetic method
      KeySet(Object var2) {
         this();
      }

      public void clear() {
         AbstractBiMap.this.clear();
      }

      protected Set<K> delegate() {
         return AbstractBiMap.this.delegate.keySet();
      }

      public Iterator<K> iterator() {
         return Maps.keyIterator(AbstractBiMap.this.entrySet().iterator());
      }

      public boolean remove(Object var1) {
         if (!this.contains(var1)) {
            return false;
         } else {
            AbstractBiMap.this.removeFromBothMaps(var1);
            return true;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         return this.standardRemoveAll(var1);
      }

      public boolean retainAll(Collection<?> var1) {
         return this.standardRetainAll(var1);
      }
   }

   private class ValueSet extends ForwardingSet<V> {
      final Set<V> valuesDelegate;

      private ValueSet() {
         this.valuesDelegate = AbstractBiMap.this.inverse.keySet();
      }

      // $FF: synthetic method
      ValueSet(Object var2) {
         this();
      }

      protected Set<V> delegate() {
         return this.valuesDelegate;
      }

      public Iterator<V> iterator() {
         return Maps.valueIterator(AbstractBiMap.this.entrySet().iterator());
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public <T> T[] toArray(T[] var1) {
         return this.standardToArray(var1);
      }

      public String toString() {
         return this.standardToString();
      }
   }
}
