package com.google.common.collect;

public class HashMultiset<E> extends AbstractMapBasedMultiset<E> {
   private static final long serialVersionUID = 0L;

   HashMultiset(int var1) {
      super(var1);
   }

   public static <E> HashMultiset<E> create() {
      return create(3);
   }

   public static <E> HashMultiset<E> create(int var0) {
      return new HashMultiset(var0);
   }

   public static <E> HashMultiset<E> create(Iterable<? extends E> var0) {
      HashMultiset var1 = create(Multisets.inferDistinctElements(var0));
      Iterables.addAll(var1, var0);
      return var1;
   }

   void init(int var1) {
      this.backingMap = new ObjectCountHashMap(var1);
   }
}
