package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class TreeMultimap<K, V> extends AbstractSortedKeySortedSetMultimap<K, V> {
   private static final long serialVersionUID = 0L;
   private transient Comparator<? super K> keyComparator;
   private transient Comparator<? super V> valueComparator;

   TreeMultimap(Comparator<? super K> var1, Comparator<? super V> var2) {
      super(new TreeMap(var1));
      this.keyComparator = var1;
      this.valueComparator = var2;
   }

   private TreeMultimap(Comparator<? super K> var1, Comparator<? super V> var2, Multimap<? extends K, ? extends V> var3) {
      this(var1, var2);
      this.putAll(var3);
   }

   public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create() {
      return new TreeMultimap(Ordering.natural(), Ordering.natural());
   }

   public static <K extends Comparable, V extends Comparable> TreeMultimap<K, V> create(Multimap<? extends K, ? extends V> var0) {
      return new TreeMultimap(Ordering.natural(), Ordering.natural(), var0);
   }

   public static <K, V> TreeMultimap<K, V> create(Comparator<? super K> var0, Comparator<? super V> var1) {
      return new TreeMultimap((Comparator)Preconditions.checkNotNull(var0), (Comparator)Preconditions.checkNotNull(var1));
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.keyComparator = (Comparator)Preconditions.checkNotNull((Comparator)var1.readObject());
      this.valueComparator = (Comparator)Preconditions.checkNotNull((Comparator)var1.readObject());
      this.setMap(new TreeMap(this.keyComparator));
      Serialization.populateMultimap(this, var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.keyComparator());
      var1.writeObject(this.valueComparator());
      Serialization.writeMultimap(this, var1);
   }

   public NavigableMap<K, Collection<V>> asMap() {
      return (NavigableMap)super.asMap();
   }

   Map<K, Collection<V>> createAsMap() {
      return this.createMaybeNavigableAsMap();
   }

   Collection<V> createCollection(@NullableDecl K var1) {
      if (var1 == null) {
         this.keyComparator().compare(var1, var1);
      }

      return super.createCollection(var1);
   }

   SortedSet<V> createCollection() {
      return new TreeSet(this.valueComparator);
   }

   public NavigableSet<V> get(@NullableDecl K var1) {
      return (NavigableSet)super.get(var1);
   }

   @Deprecated
   public Comparator<? super K> keyComparator() {
      return this.keyComparator;
   }

   public NavigableSet<K> keySet() {
      return (NavigableSet)super.keySet();
   }

   public Comparator<? super V> valueComparator() {
      return this.valueComparator;
   }
}
