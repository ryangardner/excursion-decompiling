package com.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

abstract class ImmutableAsList<E> extends ImmutableList<E> {
   private void readObject(ObjectInputStream var1) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   public boolean contains(Object var1) {
      return this.delegateCollection().contains(var1);
   }

   abstract ImmutableCollection<E> delegateCollection();

   public boolean isEmpty() {
      return this.delegateCollection().isEmpty();
   }

   boolean isPartialView() {
      return this.delegateCollection().isPartialView();
   }

   public int size() {
      return this.delegateCollection().size();
   }

   Object writeReplace() {
      return new ImmutableAsList.SerializedForm(this.delegateCollection());
   }

   static class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      final ImmutableCollection<?> collection;

      SerializedForm(ImmutableCollection<?> var1) {
         this.collection = var1;
      }

      Object readResolve() {
         return this.collection.asList();
      }
   }
}
