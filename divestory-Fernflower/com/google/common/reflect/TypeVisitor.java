package com.google.common.reflect;

import com.google.common.collect.Sets;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Set;

abstract class TypeVisitor {
   private final Set<Type> visited = Sets.newHashSet();

   public final void visit(Type... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type var4 = var1[var3];
         if (var4 != null && this.visited.add(var4)) {
            boolean var7 = false;

            try {
               var7 = true;
               if (var4 instanceof TypeVariable) {
                  this.visitTypeVariable((TypeVariable)var4);
                  var7 = false;
               } else if (var4 instanceof WildcardType) {
                  this.visitWildcardType((WildcardType)var4);
                  var7 = false;
               } else if (var4 instanceof ParameterizedType) {
                  this.visitParameterizedType((ParameterizedType)var4);
                  var7 = false;
               } else if (var4 instanceof Class) {
                  this.visitClass((Class)var4);
                  var7 = false;
               } else {
                  if (!(var4 instanceof GenericArrayType)) {
                     StringBuilder var9 = new StringBuilder();
                     var9.append("Unknown type: ");
                     var9.append(var4);
                     AssertionError var5 = new AssertionError(var9.toString());
                     throw var5;
                  }

                  this.visitGenericArrayType((GenericArrayType)var4);
                  var7 = false;
               }
            } finally {
               if (var7) {
                  this.visited.remove(var4);
               }
            }
         }
      }

   }

   void visitClass(Class<?> var1) {
   }

   void visitGenericArrayType(GenericArrayType var1) {
   }

   void visitParameterizedType(ParameterizedType var1) {
   }

   void visitTypeVariable(TypeVariable<?> var1) {
   }

   void visitWildcardType(WildcardType var1) {
   }
}
