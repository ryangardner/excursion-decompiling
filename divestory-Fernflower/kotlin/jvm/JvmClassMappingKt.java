package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a¢\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"},
   d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class JvmClassMappingKt {
   public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(T var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$annotationClass");
      Class var1 = var0.annotationType();
      Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.annot…otation).annotationType()");
      KClass var2 = getKotlinClass(var1);
      if (var2 != null) {
         return var2;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
      }
   }

   public static final <T> Class<T> getJavaClass(T var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$javaClass");
      Class var1 = var0.getClass();
      if (var1 != null) {
         return var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
      }
   }

   public static final <T> Class<T> getJavaClass(KClass<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$java");
      Class var1 = ((ClassBasedDeclarationContainer)var0).getJClass();
      if (var1 != null) {
         return var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
      }
   }

   public static final <T> Class<T> getJavaObjectType(KClass<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$javaObjectType");
      Class var2 = ((ClassBasedDeclarationContainer)var0).getJClass();
      if (!var2.isPrimitive()) {
         if (var2 != null) {
            return var2;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
         }
      } else {
         String var1 = var2.getName();
         if (var1 != null) {
            switch(var1.hashCode()) {
            case -1325958191:
               if (var1.equals("double")) {
                  var2 = Double.class;
               }
               break;
            case 104431:
               if (var1.equals("int")) {
                  var2 = Integer.class;
               }
               break;
            case 3039496:
               if (var1.equals("byte")) {
                  var2 = Byte.class;
               }
               break;
            case 3052374:
               if (var1.equals("char")) {
                  var2 = Character.class;
               }
               break;
            case 3327612:
               if (var1.equals("long")) {
                  var2 = Long.class;
               }
               break;
            case 3625364:
               if (var1.equals("void")) {
                  var2 = Void.class;
               }
               break;
            case 64711720:
               if (var1.equals("boolean")) {
                  var2 = Boolean.class;
               }
               break;
            case 97526364:
               if (var1.equals("float")) {
                  var2 = Float.class;
               }
               break;
            case 109413500:
               if (var1.equals("short")) {
                  var2 = Short.class;
               }
            }
         }

         if (var2 != null) {
            return var2;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
         }
      }
   }

   public static final <T> Class<T> getJavaPrimitiveType(KClass<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$javaPrimitiveType");
      Class var1 = ((ClassBasedDeclarationContainer)var0).getJClass();
      if (var1.isPrimitive()) {
         if (var1 != null) {
            return var1;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
         }
      } else {
         String var2 = var1.getName();
         if (var2 != null) {
            switch(var2.hashCode()) {
            case -2056817302:
               if (var2.equals("java.lang.Integer")) {
                  var1 = Integer.TYPE;
                  return var1;
               }
               break;
            case -527879800:
               if (var2.equals("java.lang.Float")) {
                  var1 = Float.TYPE;
                  return var1;
               }
               break;
            case -515992664:
               if (var2.equals("java.lang.Short")) {
                  var1 = Short.TYPE;
                  return var1;
               }
               break;
            case 155276373:
               if (var2.equals("java.lang.Character")) {
                  var1 = Character.TYPE;
                  return var1;
               }
               break;
            case 344809556:
               if (var2.equals("java.lang.Boolean")) {
                  var1 = Boolean.TYPE;
                  return var1;
               }
               break;
            case 398507100:
               if (var2.equals("java.lang.Byte")) {
                  var1 = Byte.TYPE;
                  return var1;
               }
               break;
            case 398795216:
               if (var2.equals("java.lang.Long")) {
                  var1 = Long.TYPE;
                  return var1;
               }
               break;
            case 399092968:
               if (var2.equals("java.lang.Void")) {
                  var1 = Void.TYPE;
                  return var1;
               }
               break;
            case 761287205:
               if (var2.equals("java.lang.Double")) {
                  var1 = Double.TYPE;
                  return var1;
               }
            }
         }

         var1 = null;
         return var1;
      }
   }

   public static final <T> KClass<T> getKotlinClass(Class<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$kotlin");
      return Reflection.getOrCreateKotlinClass(var0);
   }

   public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(KClass<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$javaClass");
      Class var1 = ((Object)var0).getClass();
      if (var1 != null) {
         return var1;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T>>");
      }
   }

   // $FF: synthetic method
   public static final <T> boolean isArrayOf(Object[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$isArrayOf");
      Intrinsics.reifiedOperationMarker(4, "T");
      return Object.class.isAssignableFrom(var0.getClass().getComponentType());
   }

   // $FF: synthetic method
   public static void java$annotations(KClass var0) {
   }

   // $FF: synthetic method
   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.",
      replaceWith = @ReplaceWith(
   expression = "(this as Any).javaClass",
   imports = {}
)
   )
   public static void javaClass$annotations(KClass var0) {
   }
}
