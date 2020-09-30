package com.fasterxml.jackson.core.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {
   protected final Type _type;

   protected TypeReference() {
      Type var1 = this.getClass().getGenericSuperclass();
      if (!(var1 instanceof Class)) {
         this._type = ((ParameterizedType)var1).getActualTypeArguments()[0];
      } else {
         throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
      }
   }

   public int compareTo(TypeReference<T> var1) {
      return 0;
   }

   public Type getType() {
      return this._type;
   }
}
