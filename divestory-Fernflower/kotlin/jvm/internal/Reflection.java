package kotlin.jvm.internal;

import java.util.Arrays;
import java.util.Collections;
import kotlin.collections.ArraysKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;

public class Reflection {
   private static final KClass[] EMPTY_K_CLASS_ARRAY;
   static final String REFLECTION_NOT_AVAILABLE = " (Kotlin reflection is not available)";
   private static final ReflectionFactory factory;

   static {
      ReflectionFactory var0 = null;

      label18: {
         ReflectionFactory var1;
         try {
            var1 = (ReflectionFactory)Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
         } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException var2) {
            break label18;
         }

         var0 = var1;
      }

      if (var0 == null) {
         var0 = new ReflectionFactory();
      }

      factory = var0;
      EMPTY_K_CLASS_ARRAY = new KClass[0];
   }

   public static KClass createKotlinClass(Class var0) {
      return factory.createKotlinClass(var0);
   }

   public static KClass createKotlinClass(Class var0, String var1) {
      return factory.createKotlinClass(var0, var1);
   }

   public static KFunction function(FunctionReference var0) {
      return factory.function(var0);
   }

   public static KClass getOrCreateKotlinClass(Class var0) {
      return factory.getOrCreateKotlinClass(var0);
   }

   public static KClass getOrCreateKotlinClass(Class var0, String var1) {
      return factory.getOrCreateKotlinClass(var0, var1);
   }

   public static KClass[] getOrCreateKotlinClasses(Class[] var0) {
      int var1 = var0.length;
      if (var1 == 0) {
         return EMPTY_K_CLASS_ARRAY;
      } else {
         KClass[] var2 = new KClass[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = getOrCreateKotlinClass(var0[var3]);
         }

         return var2;
      }
   }

   public static KDeclarationContainer getOrCreateKotlinPackage(Class var0, String var1) {
      return factory.getOrCreateKotlinPackage(var0, var1);
   }

   public static KMutableProperty0 mutableProperty0(MutablePropertyReference0 var0) {
      return factory.mutableProperty0(var0);
   }

   public static KMutableProperty1 mutableProperty1(MutablePropertyReference1 var0) {
      return factory.mutableProperty1(var0);
   }

   public static KMutableProperty2 mutableProperty2(MutablePropertyReference2 var0) {
      return factory.mutableProperty2(var0);
   }

   public static KType nullableTypeOf(Class var0) {
      return factory.typeOf(getOrCreateKotlinClass(var0), Collections.emptyList(), true);
   }

   public static KType nullableTypeOf(Class var0, KTypeProjection var1) {
      return factory.typeOf(getOrCreateKotlinClass(var0), Collections.singletonList(var1), true);
   }

   public static KType nullableTypeOf(Class var0, KTypeProjection var1, KTypeProjection var2) {
      return factory.typeOf(getOrCreateKotlinClass(var0), Arrays.asList(var1, var2), true);
   }

   public static KType nullableTypeOf(Class var0, KTypeProjection... var1) {
      return factory.typeOf(getOrCreateKotlinClass(var0), ArraysKt.toList(var1), true);
   }

   public static KProperty0 property0(PropertyReference0 var0) {
      return factory.property0(var0);
   }

   public static KProperty1 property1(PropertyReference1 var0) {
      return factory.property1(var0);
   }

   public static KProperty2 property2(PropertyReference2 var0) {
      return factory.property2(var0);
   }

   public static String renderLambdaToString(FunctionBase var0) {
      return factory.renderLambdaToString(var0);
   }

   public static String renderLambdaToString(Lambda var0) {
      return factory.renderLambdaToString(var0);
   }

   public static KType typeOf(Class var0) {
      return factory.typeOf(getOrCreateKotlinClass(var0), Collections.emptyList(), false);
   }

   public static KType typeOf(Class var0, KTypeProjection var1) {
      return factory.typeOf(getOrCreateKotlinClass(var0), Collections.singletonList(var1), false);
   }

   public static KType typeOf(Class var0, KTypeProjection var1, KTypeProjection var2) {
      return factory.typeOf(getOrCreateKotlinClass(var0), Arrays.asList(var1, var2), false);
   }

   public static KType typeOf(Class var0, KTypeProjection... var1) {
      return factory.typeOf(getOrCreateKotlinClass(var0), ArraysKt.toList(var1), false);
   }
}
