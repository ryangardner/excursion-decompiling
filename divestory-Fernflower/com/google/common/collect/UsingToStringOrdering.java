package com.google.common.collect;

import java.io.Serializable;

final class UsingToStringOrdering extends Ordering<Object> implements Serializable {
   static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
   private static final long serialVersionUID = 0L;

   private UsingToStringOrdering() {
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public int compare(Object var1, Object var2) {
      return var1.toString().compareTo(var2.toString());
   }

   public String toString() {
      return "Ordering.usingToString()";
   }
}
