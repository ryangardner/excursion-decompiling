package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 K2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001KB\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010B\u001a\u00020\u00122\b\u0010C\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010D\u001a\u00020EH\u0002J\b\u0010F\u001a\u00020GH\u0016J\u0012\u0010H\u001a\u00020\u00122\b\u0010I\u001a\u0004\u0018\u00010\u0002H\u0017J\b\u0010J\u001a\u00020-H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u001a\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001e\u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010 \u001a\u00020\u00128VX\u0097\u0004¢\u0006\f\u0012\u0004\b!\u0010\u0014\u001a\u0004\b \u0010\u0015R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001e\u0010$\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0010R\u001e\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u0010R\u0016\u0010)\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0016\u0010,\u001a\u0004\u0018\u00010-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R(\u00100\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00010\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b1\u0010\u0014\u001a\u0004\b2\u0010\u000bR\u0016\u00103\u001a\u0004\u0018\u00010-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010/R \u00105\u001a\b\u0012\u0004\u0012\u0002060\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b7\u0010\u0014\u001a\u0004\b8\u0010\u000bR \u00109\u001a\b\u0012\u0004\u0012\u00020:0\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b;\u0010\u0014\u001a\u0004\b<\u0010\u000bR\u001c\u0010=\u001a\u0004\u0018\u00010>8VX\u0097\u0004¢\u0006\f\u0012\u0004\b?\u0010\u0014\u001a\u0004\b@\u0010A¨\u0006L"},
   d2 = {"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "sealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "supertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "typeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "visibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ClassReference implements KClass<Object>, ClassBasedDeclarationContainer {
   public static final ClassReference.Companion Companion = new ClassReference.Companion((DefaultConstructorMarker)null);
   private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
   private static final HashMap<String, String> classFqNames;
   private static final HashMap<String, String> primitiveFqNames;
   private static final HashMap<String, String> primitiveWrapperFqNames;
   private static final Map<String, String> simpleNames;
   private final Class<?> jClass;

   static {
      int var0 = 0;
      Iterable var1 = (Iterable)CollectionsKt.listOf(new Class[]{Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class});
      Collection var2 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10)));

      Iterator var7;
      for(var7 = var1.iterator(); var7.hasNext(); ++var0) {
         Object var3 = var7.next();
         if (var0 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var2.add(TuplesKt.to((Class)var3, var0));
      }

      FUNCTION_CLASSES = MapsKt.toMap((Iterable)((List)var2));
      HashMap var9 = new HashMap();
      var9.put("boolean", "kotlin.Boolean");
      var9.put("char", "kotlin.Char");
      var9.put("byte", "kotlin.Byte");
      var9.put("short", "kotlin.Short");
      var9.put("int", "kotlin.Int");
      var9.put("float", "kotlin.Float");
      var9.put("long", "kotlin.Long");
      var9.put("double", "kotlin.Double");
      primitiveFqNames = var9;
      var9 = new HashMap();
      var9.put("java.lang.Boolean", "kotlin.Boolean");
      var9.put("java.lang.Character", "kotlin.Char");
      var9.put("java.lang.Byte", "kotlin.Byte");
      var9.put("java.lang.Short", "kotlin.Short");
      var9.put("java.lang.Integer", "kotlin.Int");
      var9.put("java.lang.Float", "kotlin.Float");
      var9.put("java.lang.Long", "kotlin.Long");
      var9.put("java.lang.Double", "kotlin.Double");
      primitiveWrapperFqNames = var9;
      var9 = new HashMap();
      var9.put("java.lang.Object", "kotlin.Any");
      var9.put("java.lang.String", "kotlin.String");
      var9.put("java.lang.CharSequence", "kotlin.CharSequence");
      var9.put("java.lang.Throwable", "kotlin.Throwable");
      var9.put("java.lang.Cloneable", "kotlin.Cloneable");
      var9.put("java.lang.Number", "kotlin.Number");
      var9.put("java.lang.Comparable", "kotlin.Comparable");
      var9.put("java.lang.Enum", "kotlin.Enum");
      var9.put("java.lang.annotation.Annotation", "kotlin.Annotation");
      var9.put("java.lang.Iterable", "kotlin.collections.Iterable");
      var9.put("java.util.Iterator", "kotlin.collections.Iterator");
      var9.put("java.util.Collection", "kotlin.collections.Collection");
      var9.put("java.util.List", "kotlin.collections.List");
      var9.put("java.util.Set", "kotlin.collections.Set");
      var9.put("java.util.ListIterator", "kotlin.collections.ListIterator");
      var9.put("java.util.Map", "kotlin.collections.Map");
      var9.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
      var9.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
      var9.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
      var9.putAll((Map)primitiveFqNames);
      var9.putAll((Map)primitiveWrapperFqNames);
      Collection var8 = primitiveFqNames.values();
      Intrinsics.checkExpressionValueIsNotNull(var8, "primitiveFqNames.values");
      var7 = ((Iterable)var8).iterator();

      while(var7.hasNext()) {
         Object var4 = var7.next();
         Map var11 = (Map)var9;
         String var16 = (String)var4;
         StringBuilder var5 = new StringBuilder();
         var5.append("kotlin.jvm.internal.");
         Intrinsics.checkExpressionValueIsNotNull(var16, "kotlinName");
         var5.append(StringsKt.substringAfterLast$default(var16, '.', (String)null, 2, (Object)null));
         var5.append("CompanionObject");
         String var20 = var5.toString();
         StringBuilder var6 = new StringBuilder();
         var6.append(var16);
         var6.append(".Companion");
         Pair var18 = TuplesKt.to(var20, var6.toString());
         var11.put(var18.getFirst(), var18.getSecond());
      }

      Map var10 = (Map)var9;
      var7 = FUNCTION_CLASSES.entrySet().iterator();

      while(var7.hasNext()) {
         Entry var19 = (Entry)var7.next();
         Class var14 = (Class)var19.getKey();
         var0 = ((Number)var19.getValue()).intValue();
         String var15 = var14.getName();
         StringBuilder var21 = new StringBuilder();
         var21.append("kotlin.Function");
         var21.append(var0);
         var9.put(var15, var21.toString());
      }

      classFqNames = var9;
      var10 = (Map)var9;
      Map var12 = (Map)(new LinkedHashMap(MapsKt.mapCapacity(var10.size())));
      Iterator var17 = ((Iterable)var10.entrySet()).iterator();

      while(var17.hasNext()) {
         Entry var13 = (Entry)var17.next();
         var12.put(var13.getKey(), StringsKt.substringAfterLast$default((String)var13.getValue(), '.', (String)null, 2, (Object)null));
      }

      simpleNames = var12;
   }

   public ClassReference(Class<?> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "jClass");
      super();
      this.jClass = var1;
   }

   private final Void error() {
      throw (Throwable)(new KotlinReflectionNotSupportedError());
   }

   // $FF: synthetic method
   public static void isAbstract$annotations() {
   }

   // $FF: synthetic method
   public static void isCompanion$annotations() {
   }

   // $FF: synthetic method
   public static void isData$annotations() {
   }

   // $FF: synthetic method
   public static void isFinal$annotations() {
   }

   // $FF: synthetic method
   public static void isInner$annotations() {
   }

   // $FF: synthetic method
   public static void isOpen$annotations() {
   }

   // $FF: synthetic method
   public static void isSealed$annotations() {
   }

   // $FF: synthetic method
   public static void sealedSubclasses$annotations() {
   }

   // $FF: synthetic method
   public static void supertypes$annotations() {
   }

   // $FF: synthetic method
   public static void typeParameters$annotations() {
   }

   // $FF: synthetic method
   public static void visibility$annotations() {
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof ClassReference && Intrinsics.areEqual((Object)JvmClassMappingKt.getJavaObjectType(this), (Object)JvmClassMappingKt.getJavaObjectType((KClass)var1))) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public List<Annotation> getAnnotations() {
      this.error();
      throw null;
   }

   public Collection<KFunction<Object>> getConstructors() {
      this.error();
      throw null;
   }

   public Class<?> getJClass() {
      return this.jClass;
   }

   public Collection<KCallable<?>> getMembers() {
      this.error();
      throw null;
   }

   public Collection<KClass<?>> getNestedClasses() {
      this.error();
      throw null;
   }

   public Object getObjectInstance() {
      this.error();
      throw null;
   }

   public String getQualifiedName() {
      return Companion.getClassQualifiedName(this.getJClass());
   }

   public List<KClass<? extends Object>> getSealedSubclasses() {
      this.error();
      throw null;
   }

   public String getSimpleName() {
      return Companion.getClassSimpleName(this.getJClass());
   }

   public List<KType> getSupertypes() {
      this.error();
      throw null;
   }

   public List<KTypeParameter> getTypeParameters() {
      this.error();
      throw null;
   }

   public KVisibility getVisibility() {
      this.error();
      throw null;
   }

   public int hashCode() {
      return JvmClassMappingKt.getJavaObjectType(this).hashCode();
   }

   public boolean isAbstract() {
      this.error();
      throw null;
   }

   public boolean isCompanion() {
      this.error();
      throw null;
   }

   public boolean isData() {
      this.error();
      throw null;
   }

   public boolean isFinal() {
      this.error();
      throw null;
   }

   public boolean isInner() {
      this.error();
      throw null;
   }

   public boolean isInstance(Object var1) {
      return Companion.isInstance(var1, this.getJClass());
   }

   public boolean isOpen() {
      this.error();
      throw null;
   }

   public boolean isSealed() {
      this.error();
      throw null;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getJClass().toString());
      var1.append(" (Kotlin reflection is not available)");
      return var1.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
      d2 = {"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final String getClassQualifiedName(Class<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "jClass");
         boolean var2 = var1.isAnonymousClass();
         Class var3 = null;
         String var4 = null;
         String var5;
         if (var2) {
            var5 = var3;
         } else if (var1.isLocalClass()) {
            var5 = var3;
         } else if (var1.isArray()) {
            var3 = var1.getComponentType();
            Intrinsics.checkExpressionValueIsNotNull(var3, "componentType");
            var5 = var4;
            if (var3.isPrimitive()) {
               String var7 = (String)ClassReference.classFqNames.get(var3.getName());
               var5 = var4;
               if (var7 != null) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append(var7);
                  var6.append("Array");
                  var5 = var6.toString();
               }
            }

            if (var5 == null) {
               var5 = "kotlin.Array";
            }
         } else {
            var4 = (String)ClassReference.classFqNames.get(var1.getName());
            if (var4 != null) {
               var5 = var4;
            } else {
               var5 = var1.getCanonicalName();
            }
         }

         return var5;
      }

      public final String getClassSimpleName(Class<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "jClass");
         boolean var2 = var1.isAnonymousClass();
         String var3 = "Array";
         Method var4 = null;
         String var5 = null;
         String var6;
         if (!var2) {
            StringBuilder var8;
            if (var1.isLocalClass()) {
               label34: {
                  var3 = var1.getSimpleName();
                  var4 = var1.getEnclosingMethod();
                  if (var4 != null) {
                     Intrinsics.checkExpressionValueIsNotNull(var3, "name");
                     var8 = new StringBuilder();
                     var8.append(var4.getName());
                     var8.append("$");
                     var5 = StringsKt.substringAfter$default(var3, var8.toString(), (String)null, 2, (Object)null);
                     if (var5 != null) {
                        var6 = var5;
                        break label34;
                     }
                  }

                  Constructor var7 = var1.getEnclosingConstructor();
                  if (var7 != null) {
                     Intrinsics.checkExpressionValueIsNotNull(var3, "name");
                     var8 = new StringBuilder();
                     var8.append(var7.getName());
                     var8.append("$");
                     var6 = StringsKt.substringAfter$default(var3, var8.toString(), (String)null, 2, (Object)null);
                  } else {
                     var6 = null;
                  }
               }

               if (var6 == null) {
                  Intrinsics.checkExpressionValueIsNotNull(var3, "name");
                  var6 = StringsKt.substringAfter$default(var3, '$', (String)null, 2, (Object)null);
               }

               return var6;
            }

            if (!var1.isArray()) {
               var5 = (String)ClassReference.simpleNames.get(var1.getName());
               if (var5 != null) {
                  var6 = var5;
               } else {
                  var6 = var1.getSimpleName();
               }

               return var6;
            }

            var1 = var1.getComponentType();
            Intrinsics.checkExpressionValueIsNotNull(var1, "componentType");
            var5 = var4;
            if (var1.isPrimitive()) {
               var6 = (String)ClassReference.simpleNames.get(var1.getName());
               var5 = var4;
               if (var6 != null) {
                  var8 = new StringBuilder();
                  var8.append(var6);
                  var8.append("Array");
                  var5 = var8.toString();
               }
            }

            var6 = var3;
            if (var5 == null) {
               return var6;
            }
         }

         var6 = var5;
         return var6;
      }

      public final boolean isInstance(Object var1, Class<?> var2) {
         Intrinsics.checkParameterIsNotNull(var2, "jClass");
         Map var3 = ClassReference.FUNCTION_CLASSES;
         if (var3 != null) {
            Integer var4 = (Integer)var3.get(var2);
            if (var4 != null) {
               return TypeIntrinsics.isFunctionOfArity(var1, ((Number)var4).intValue());
            } else {
               Class var5 = var2;
               if (var2.isPrimitive()) {
                  var5 = JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(var2));
               }

               return var5.isInstance(var1);
            }
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
         }
      }
   }
}
