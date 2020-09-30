package com.fasterxml.jackson.core.type;

public abstract class ResolvedType {
   public abstract ResolvedType containedType(int var1);

   public abstract int containedTypeCount();

   public abstract String containedTypeName(int var1);

   public abstract ResolvedType getContentType();

   public abstract ResolvedType getKeyType();

   @Deprecated
   public Class<?> getParameterSource() {
      return null;
   }

   public abstract Class<?> getRawClass();

   public abstract ResolvedType getReferencedType();

   public abstract boolean hasGenericTypes();

   public abstract boolean hasRawClass(Class<?> var1);

   public abstract boolean isAbstract();

   public abstract boolean isArrayType();

   public abstract boolean isCollectionLikeType();

   public abstract boolean isConcrete();

   public abstract boolean isContainerType();

   public abstract boolean isEnumType();

   public abstract boolean isFinal();

   public abstract boolean isInterface();

   public abstract boolean isMapLikeType();

   public abstract boolean isPrimitive();

   public boolean isReferenceType() {
      boolean var1;
      if (this.getReferencedType() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract boolean isThrowable();

   public abstract String toCanonical();
}
