package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\f\u0010\u0017\u001a\u00020\u0013*\u00020\u0006H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"},
   d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "()Z", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "equals", "other", "", "hashCode", "", "toString", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class TypeReference implements KType {
   private final List<KTypeProjection> arguments;
   private final KClassifier classifier;
   private final boolean isMarkedNullable;

   public TypeReference(KClassifier var1, List<KTypeProjection> var2, boolean var3) {
      Intrinsics.checkParameterIsNotNull(var1, "classifier");
      Intrinsics.checkParameterIsNotNull(var2, "arguments");
      super();
      this.classifier = var1;
      this.arguments = var2;
      this.isMarkedNullable = var3;
   }

   private final String asString() {
      KClassifier var1 = this.getClassifier();
      boolean var2 = var1 instanceof KClass;
      String var3 = null;
      if (!var2) {
         var1 = null;
      }

      KClass var4 = (KClass)var1;
      Class var6 = var3;
      if (var4 != null) {
         var6 = JvmClassMappingKt.getJavaClass(var4);
      }

      String var7;
      if (var6 == null) {
         var7 = this.getClassifier().toString();
      } else if (var6.isArray()) {
         var7 = this.getArrayClassName(var6);
      } else {
         var7 = var6.getName();
      }

      var2 = this.getArguments().isEmpty();
      String var8 = "";
      if (var2) {
         var3 = "";
      } else {
         var3 = CollectionsKt.joinToString$default((Iterable)this.getArguments(), (CharSequence)", ", (CharSequence)"<", (CharSequence)">", 0, (CharSequence)null, (Function1)(new Function1<KTypeProjection, String>() {
            public final String invoke(KTypeProjection var1) {
               Intrinsics.checkParameterIsNotNull(var1, "it");
               return TypeReference.this.asString(var1);
            }
         }), 24, (Object)null);
      }

      if (this.isMarkedNullable()) {
         var8 = "?";
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(var7);
      var5.append(var3);
      var5.append(var8);
      return var5.toString();
   }

   private final String asString(KTypeProjection var1) {
      if (var1.getVariance() == null) {
         return "*";
      } else {
         KType var2 = var1.getType();
         KType var3 = var2;
         if (!(var2 instanceof TypeReference)) {
            var3 = null;
         }

         String var9;
         label34: {
            TypeReference var8 = (TypeReference)var3;
            if (var8 != null) {
               var9 = var8.asString();
               if (var9 != null) {
                  break label34;
               }
            }

            var9 = String.valueOf(var1.getType());
         }

         KVariance var5 = var1.getVariance();
         if (var5 != null) {
            int var4 = TypeReference$WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
            String var6 = var9;
            if (var4 != 1) {
               StringBuilder var7;
               if (var4 != 2) {
                  if (var4 != 3) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var7 = new StringBuilder();
                  var7.append("out ");
                  var7.append(var9);
                  var6 = var7.toString();
               } else {
                  var7 = new StringBuilder();
                  var7.append("in ");
                  var7.append(var9);
                  var6 = var7.toString();
               }
            }

            return var6;
         } else {
            throw new NoWhenBranchMatchedException();
         }
      }
   }

   private final String getArrayClassName(Class<?> var1) {
      String var2;
      if (Intrinsics.areEqual((Object)var1, (Object)boolean[].class)) {
         var2 = "kotlin.BooleanArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)char[].class)) {
         var2 = "kotlin.CharArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)byte[].class)) {
         var2 = "kotlin.ByteArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)short[].class)) {
         var2 = "kotlin.ShortArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)int[].class)) {
         var2 = "kotlin.IntArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)float[].class)) {
         var2 = "kotlin.FloatArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)long[].class)) {
         var2 = "kotlin.LongArray";
      } else if (Intrinsics.areEqual((Object)var1, (Object)double[].class)) {
         var2 = "kotlin.DoubleArray";
      } else {
         var2 = "kotlin.Array";
      }

      return var2;
   }

   public boolean equals(Object var1) {
      boolean var3;
      if (var1 instanceof TypeReference) {
         KClassifier var2 = this.getClassifier();
         TypeReference var4 = (TypeReference)var1;
         if (Intrinsics.areEqual((Object)var2, (Object)var4.getClassifier()) && Intrinsics.areEqual((Object)this.getArguments(), (Object)var4.getArguments()) && this.isMarkedNullable() == var4.isMarkedNullable()) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public List<Annotation> getAnnotations() {
      return CollectionsKt.emptyList();
   }

   public List<KTypeProjection> getArguments() {
      return this.arguments;
   }

   public KClassifier getClassifier() {
      return this.classifier;
   }

   public int hashCode() {
      return (this.getClassifier().hashCode() * 31 + this.getArguments().hashCode()) * 31 + Boolean.valueOf(this.isMarkedNullable()).hashCode();
   }

   public boolean isMarkedNullable() {
      return this.isMarkedNullable;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.asString());
      var1.append(" (Kotlin reflection is not available)");
      return var1.toString();
   }
}
