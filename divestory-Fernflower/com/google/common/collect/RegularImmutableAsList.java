package com.google.common.collect;

class RegularImmutableAsList<E> extends ImmutableAsList<E> {
   private final ImmutableCollection<E> delegate;
   private final ImmutableList<? extends E> delegateList;

   RegularImmutableAsList(ImmutableCollection<E> var1, ImmutableList<? extends E> var2) {
      this.delegate = var1;
      this.delegateList = var2;
   }

   RegularImmutableAsList(ImmutableCollection<E> var1, Object[] var2) {
      this(var1, ImmutableList.asImmutableList(var2));
   }

   RegularImmutableAsList(ImmutableCollection<E> var1, Object[] var2, int var3) {
      this(var1, ImmutableList.asImmutableList(var2, var3));
   }

   int copyIntoArray(Object[] var1, int var2) {
      return this.delegateList.copyIntoArray(var1, var2);
   }

   ImmutableCollection<E> delegateCollection() {
      return this.delegate;
   }

   ImmutableList<? extends E> delegateList() {
      return this.delegateList;
   }

   public E get(int var1) {
      return this.delegateList.get(var1);
   }

   Object[] internalArray() {
      return this.delegateList.internalArray();
   }

   int internalArrayEnd() {
      return this.delegateList.internalArrayEnd();
   }

   int internalArrayStart() {
      return this.delegateList.internalArrayStart();
   }

   public UnmodifiableListIterator<E> listIterator(int var1) {
      return this.delegateList.listIterator(var1);
   }
}
