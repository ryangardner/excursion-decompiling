package com.google.common.reflect;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class TypeResolver {
   private final TypeResolver.TypeTable typeTable;

   public TypeResolver() {
      this.typeTable = new TypeResolver.TypeTable();
   }

   private TypeResolver(TypeResolver.TypeTable var1) {
      this.typeTable = var1;
   }

   // $FF: synthetic method
   TypeResolver(TypeResolver.TypeTable var1, Object var2) {
      this(var1);
   }

   static TypeResolver covariantly(Type var0) {
      return (new TypeResolver()).where(TypeResolver.TypeMappingIntrospector.getTypeMappings(var0));
   }

   private static <T> T expectArgument(Class<T> var0, Object var1) {
      try {
         Object var4 = var0.cast(var1);
         return var4;
      } catch (ClassCastException var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" is not a ");
         var2.append(var0.getSimpleName());
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static TypeResolver invariantly(Type var0) {
      var0 = TypeResolver.WildcardCapturer.INSTANCE.capture(var0);
      return (new TypeResolver()).where(TypeResolver.TypeMappingIntrospector.getTypeMappings(var0));
   }

   private static void populateTypeMappings(final Map<TypeResolver.TypeVariableKey, Type> var0, Type var1, final Type var2) {
      if (!var1.equals(var2)) {
         (new TypeVisitor() {
            void visitClass(Class<?> var1) {
               if (!(var2 instanceof WildcardType)) {
                  StringBuilder var2x = new StringBuilder();
                  var2x.append("No type mapping from ");
                  var2x.append(var1);
                  var2x.append(" to ");
                  var2x.append(var2);
                  throw new IllegalArgumentException(var2x.toString());
               }
            }

            void visitGenericArrayType(GenericArrayType var1) {
               Type var2x = var2;
               if (!(var2x instanceof WildcardType)) {
                  var2x = Types.getComponentType(var2x);
                  boolean var3;
                  if (var2x != null) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  Preconditions.checkArgument(var3, "%s is not an array type.", (Object)var2);
                  TypeResolver.populateTypeMappings(var0, var1.getGenericComponentType(), var2x);
               }
            }

            void visitParameterizedType(ParameterizedType var1) {
               Type var2x = var2;
               if (!(var2x instanceof WildcardType)) {
                  ParameterizedType var9 = (ParameterizedType)TypeResolver.expectArgument(ParameterizedType.class, var2x);
                  if (var1.getOwnerType() != null && var9.getOwnerType() != null) {
                     TypeResolver.populateTypeMappings(var0, var1.getOwnerType(), var9.getOwnerType());
                  }

                  Preconditions.checkArgument(var1.getRawType().equals(var9.getRawType()), "Inconsistent raw type: %s vs. %s", var1, var2);
                  Type[] var3 = var1.getActualTypeArguments();
                  Type[] var4 = var9.getActualTypeArguments();
                  int var5 = var3.length;
                  int var6 = var4.length;
                  int var7 = 0;
                  boolean var8;
                  if (var5 == var6) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  Preconditions.checkArgument(var8, "%s not compatible with %s", var1, var9);

                  while(var7 < var3.length) {
                     TypeResolver.populateTypeMappings(var0, var3[var7], var4[var7]);
                     ++var7;
                  }

               }
            }

            void visitTypeVariable(TypeVariable<?> var1) {
               var0.put(new TypeResolver.TypeVariableKey(var1), var2);
            }

            void visitWildcardType(WildcardType var1) {
               Type var2x = var2;
               if (var2x instanceof WildcardType) {
                  WildcardType var3 = (WildcardType)var2x;
                  Type[] var10 = var1.getUpperBounds();
                  Type[] var4 = var3.getUpperBounds();
                  Type[] var5 = var1.getLowerBounds();
                  Type[] var11 = var3.getLowerBounds();
                  int var6 = var10.length;
                  int var7 = var4.length;
                  byte var8 = 0;
                  boolean var9;
                  if (var6 == var7 && var5.length == var11.length) {
                     var9 = true;
                  } else {
                     var9 = false;
                  }

                  Preconditions.checkArgument(var9, "Incompatible type: %s vs. %s", var1, var2);
                  var7 = 0;

                  while(true) {
                     var6 = var8;
                     if (var7 >= var10.length) {
                        while(var6 < var5.length) {
                           TypeResolver.populateTypeMappings(var0, var5[var6], var11[var6]);
                           ++var6;
                        }

                        return;
                     }

                     TypeResolver.populateTypeMappings(var0, var10[var7], var4[var7]);
                     ++var7;
                  }
               }
            }
         }).visit(new Type[]{var1});
      }
   }

   private Type resolveGenericArrayType(GenericArrayType var1) {
      return Types.newArrayType(this.resolveType(var1.getGenericComponentType()));
   }

   private ParameterizedType resolveParameterizedType(ParameterizedType var1) {
      Type var2 = var1.getOwnerType();
      if (var2 == null) {
         var2 = null;
      } else {
         var2 = this.resolveType(var2);
      }

      Type var3 = this.resolveType(var1.getRawType());
      Type[] var4 = this.resolveTypes(var1.getActualTypeArguments());
      return Types.newParameterizedTypeWithOwner(var2, (Class)var3, var4);
   }

   private Type[] resolveTypes(Type[] var1) {
      Type[] var2 = new Type[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = this.resolveType(var1[var3]);
      }

      return var2;
   }

   private WildcardType resolveWildcardType(WildcardType var1) {
      Type[] var2 = var1.getLowerBounds();
      Type[] var3 = var1.getUpperBounds();
      return new Types.WildcardTypeImpl(this.resolveTypes(var2), this.resolveTypes(var3));
   }

   public Type resolveType(Type var1) {
      Preconditions.checkNotNull(var1);
      if (var1 instanceof TypeVariable) {
         return this.typeTable.resolve((TypeVariable)var1);
      } else if (var1 instanceof ParameterizedType) {
         return this.resolveParameterizedType((ParameterizedType)var1);
      } else if (var1 instanceof GenericArrayType) {
         return this.resolveGenericArrayType((GenericArrayType)var1);
      } else {
         Object var2 = var1;
         if (var1 instanceof WildcardType) {
            var2 = this.resolveWildcardType((WildcardType)var1);
         }

         return (Type)var2;
      }
   }

   Type[] resolveTypesInPlace(Type[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = this.resolveType(var1[var2]);
      }

      return var1;
   }

   public TypeResolver where(Type var1, Type var2) {
      HashMap var3 = Maps.newHashMap();
      populateTypeMappings(var3, (Type)Preconditions.checkNotNull(var1), (Type)Preconditions.checkNotNull(var2));
      return this.where(var3);
   }

   TypeResolver where(Map<TypeResolver.TypeVariableKey, ? extends Type> var1) {
      return new TypeResolver(this.typeTable.where(var1));
   }

   private static final class TypeMappingIntrospector extends TypeVisitor {
      private final Map<TypeResolver.TypeVariableKey, Type> mappings = Maps.newHashMap();

      static ImmutableMap<TypeResolver.TypeVariableKey, Type> getTypeMappings(Type var0) {
         Preconditions.checkNotNull(var0);
         TypeResolver.TypeMappingIntrospector var1 = new TypeResolver.TypeMappingIntrospector();
         var1.visit(new Type[]{var0});
         return ImmutableMap.copyOf(var1.mappings);
      }

      private void map(TypeResolver.TypeVariableKey var1, Type var2) {
         if (!this.mappings.containsKey(var1)) {
            for(Type var3 = var2; var3 != null; var3 = (Type)this.mappings.get(TypeResolver.TypeVariableKey.forLookup(var3))) {
               if (var1.equalsType(var3)) {
                  while(var2 != null) {
                     var2 = (Type)this.mappings.remove(TypeResolver.TypeVariableKey.forLookup(var2));
                  }

                  return;
               }
            }

            this.mappings.put(var1, var2);
         }
      }

      void visitClass(Class<?> var1) {
         this.visit(new Type[]{var1.getGenericSuperclass()});
         this.visit(var1.getGenericInterfaces());
      }

      void visitParameterizedType(ParameterizedType var1) {
         Class var2 = (Class)var1.getRawType();
         TypeVariable[] var3 = var2.getTypeParameters();
         Type[] var4 = var1.getActualTypeArguments();
         boolean var5;
         if (var3.length == var4.length) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkState(var5);

         for(int var6 = 0; var6 < var3.length; ++var6) {
            this.map(new TypeResolver.TypeVariableKey(var3[var6]), var4[var6]);
         }

         this.visit(new Type[]{var2});
         this.visit(new Type[]{var1.getOwnerType()});
      }

      void visitTypeVariable(TypeVariable<?> var1) {
         this.visit(var1.getBounds());
      }

      void visitWildcardType(WildcardType var1) {
         this.visit(var1.getUpperBounds());
      }
   }

   private static class TypeTable {
      private final ImmutableMap<TypeResolver.TypeVariableKey, Type> map;

      TypeTable() {
         this.map = ImmutableMap.of();
      }

      private TypeTable(ImmutableMap<TypeResolver.TypeVariableKey, Type> var1) {
         this.map = var1;
      }

      final Type resolve(final TypeVariable<?> var1) {
         return this.resolveInternal(var1, new TypeResolver.TypeTable() {
            public Type resolveInternal(TypeVariable<?> var1x, TypeResolver.TypeTable var2) {
               return (Type)(var1x.getGenericDeclaration().equals(var1.getGenericDeclaration()) ? var1x : TypeTable.this.resolveInternal(var1x, var2));
            }
         });
      }

      Type resolveInternal(TypeVariable<?> var1, TypeResolver.TypeTable var2) {
         Type var3 = (Type)this.map.get(new TypeResolver.TypeVariableKey(var1));
         if (var3 == null) {
            Type[] var5 = var1.getBounds();
            if (var5.length == 0) {
               return var1;
            } else {
               Type[] var4 = (new TypeResolver(var2)).resolveTypes(var5);
               return Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY && Arrays.equals(var5, var4) ? var1 : Types.newArtificialTypeVariable(var1.getGenericDeclaration(), var1.getName(), var4);
            }
         } else {
            return (new TypeResolver(var2)).resolveType(var3);
         }
      }

      final TypeResolver.TypeTable where(Map<TypeResolver.TypeVariableKey, ? extends Type> var1) {
         ImmutableMap.Builder var2 = ImmutableMap.builder();
         var2.putAll((Map)this.map);
         Iterator var3 = var1.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            TypeResolver.TypeVariableKey var5 = (TypeResolver.TypeVariableKey)var4.getKey();
            Type var6 = (Type)var4.getValue();
            Preconditions.checkArgument(var5.equalsType(var6) ^ true, "Type variable %s bound to itself", (Object)var5);
            var2.put(var5, var6);
         }

         return new TypeResolver.TypeTable(var2.build());
      }
   }

   static final class TypeVariableKey {
      private final TypeVariable<?> var;

      TypeVariableKey(TypeVariable<?> var1) {
         this.var = (TypeVariable)Preconditions.checkNotNull(var1);
      }

      private boolean equalsTypeVariable(TypeVariable<?> var1) {
         boolean var2;
         if (this.var.getGenericDeclaration().equals(var1.getGenericDeclaration()) && this.var.getName().equals(var1.getName())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      static TypeResolver.TypeVariableKey forLookup(Type var0) {
         return var0 instanceof TypeVariable ? new TypeResolver.TypeVariableKey((TypeVariable)var0) : null;
      }

      public boolean equals(Object var1) {
         return var1 instanceof TypeResolver.TypeVariableKey ? this.equalsTypeVariable(((TypeResolver.TypeVariableKey)var1).var) : false;
      }

      boolean equalsType(Type var1) {
         return var1 instanceof TypeVariable ? this.equalsTypeVariable((TypeVariable)var1) : false;
      }

      public int hashCode() {
         return Objects.hashCode(this.var.getGenericDeclaration(), this.var.getName());
      }

      public String toString() {
         return this.var.toString();
      }
   }

   private static class WildcardCapturer {
      static final TypeResolver.WildcardCapturer INSTANCE = new TypeResolver.WildcardCapturer();
      private final AtomicInteger id;

      private WildcardCapturer() {
         this(new AtomicInteger());
      }

      private WildcardCapturer(AtomicInteger var1) {
         this.id = var1;
      }

      // $FF: synthetic method
      WildcardCapturer(AtomicInteger var1, Object var2) {
         this(var1);
      }

      private Type captureNullable(@NullableDecl Type var1) {
         return var1 == null ? null : this.capture(var1);
      }

      private TypeResolver.WildcardCapturer forTypeVariable(final TypeVariable<?> var1) {
         return new TypeResolver.WildcardCapturer(this.id) {
            TypeVariable<?> captureAsTypeVariable(Type[] var1x) {
               LinkedHashSet var2 = new LinkedHashSet(Arrays.asList(var1x));
               var2.addAll(Arrays.asList(var1.getBounds()));
               if (var2.size() > 1) {
                  var2.remove(Object.class);
               }

               return super.captureAsTypeVariable((Type[])var2.toArray(new Type[0]));
            }
         };
      }

      private TypeResolver.WildcardCapturer notForTypeVariable() {
         return new TypeResolver.WildcardCapturer(this.id);
      }

      final Type capture(Type var1) {
         Preconditions.checkNotNull(var1);
         if (var1 instanceof Class) {
            return (Type)var1;
         } else if (var1 instanceof TypeVariable) {
            return (Type)var1;
         } else if (var1 instanceof GenericArrayType) {
            GenericArrayType var7 = (GenericArrayType)var1;
            return Types.newArrayType(this.notForTypeVariable().capture(var7.getGenericComponentType()));
         } else if (!(var1 instanceof ParameterizedType)) {
            if (var1 instanceof WildcardType) {
               WildcardType var8 = (WildcardType)var1;
               if (var8.getLowerBounds().length == 0) {
                  var1 = this.captureAsTypeVariable(var8.getUpperBounds());
               }

               return (Type)var1;
            } else {
               throw new AssertionError("must have been one of the known types");
            }
         } else {
            ParameterizedType var6 = (ParameterizedType)var1;
            Class var2 = (Class)var6.getRawType();
            TypeVariable[] var3 = var2.getTypeParameters();
            Type[] var4 = var6.getActualTypeArguments();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               var4[var5] = this.forTypeVariable(var3[var5]).capture(var4[var5]);
            }

            return Types.newParameterizedTypeWithOwner(this.notForTypeVariable().captureNullable(var6.getOwnerType()), var2, var4);
         }
      }

      TypeVariable<?> captureAsTypeVariable(Type[] var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("capture#");
         var2.append(this.id.incrementAndGet());
         var2.append("-of ? extends ");
         var2.append(Joiner.on('&').join((Object[])var1));
         return Types.newArtificialTypeVariable(TypeResolver.WildcardCapturer.class, var2.toString(), var1);
      }
   }
}
