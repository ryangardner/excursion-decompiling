/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import java.util.Set;
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
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 K2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001KB\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010B\u001a\u00020\u00122\b\u0010C\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010D\u001a\u00020EH\u0002J\b\u0010F\u001a\u00020GH\u0016J\u0012\u0010H\u001a\u00020\u00122\b\u0010I\u001a\u0004\u0018\u00010\u0002H\u0017J\b\u0010J\u001a\u00020-H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u001a\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001e\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010 \u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b!\u0010\u0014\u001a\u0004\b \u0010\u0015R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001e\u0010$\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030%0\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u0010R\u001e\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u0010R\u0016\u0010)\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010+R\u0016\u0010,\u001a\u0004\u0018\u00010-8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R(\u00100\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00010\b8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b1\u0010\u0014\u001a\u0004\b2\u0010\u000bR\u0016\u00103\u001a\u0004\u0018\u00010-8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u0010/R \u00105\u001a\b\u0012\u0004\u0012\u0002060\b8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b7\u0010\u0014\u001a\u0004\b8\u0010\u000bR \u00109\u001a\b\u0012\u0004\u0012\u00020:0\b8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b;\u0010\u0014\u001a\u0004\b<\u0010\u000bR\u001c\u0010=\u001a\u0004\u0018\u00010>8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b?\u0010\u0014\u001a\u0004\b@\u0010A\u00a8\u0006L"}, d2={"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "sealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "supertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "typeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "visibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class ClassReference
implements KClass<Object>,
ClassBasedDeclarationContainer {
    public static final Companion Companion;
    private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
    private static final HashMap<String, String> classFqNames;
    private static final HashMap<String, String> primitiveFqNames;
    private static final HashMap<String, String> primitiveWrapperFqNames;
    private static final Map<String, String> simpleNames;
    private final Class<?> jClass;

    static {
        Object object;
        Companion = new Companion(null);
        int n = 0;
        Object object2 = CollectionsKt.listOf(Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class);
        Map<Object, String> map = new ArrayList(CollectionsKt.collectionSizeOrDefault(object2, 10));
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            map.add(TuplesKt.to((Class)object, n));
            ++n;
        }
        FUNCTION_CLASSES = MapsKt.toMap((List)((Object)map));
        map = new HashMap();
        ((HashMap)map).put("boolean", "kotlin.Boolean");
        ((HashMap)map).put("char", "kotlin.Char");
        ((HashMap)map).put("byte", "kotlin.Byte");
        ((HashMap)map).put("short", "kotlin.Short");
        ((HashMap)map).put("int", "kotlin.Int");
        ((HashMap)map).put("float", "kotlin.Float");
        ((HashMap)map).put("long", "kotlin.Long");
        ((HashMap)map).put("double", "kotlin.Double");
        primitiveFqNames = map;
        map = new HashMap<String, String>();
        ((HashMap)map).put("java.lang.Boolean", "kotlin.Boolean");
        ((HashMap)map).put("java.lang.Character", "kotlin.Char");
        ((HashMap)map).put("java.lang.Byte", "kotlin.Byte");
        ((HashMap)map).put("java.lang.Short", "kotlin.Short");
        ((HashMap)map).put("java.lang.Integer", "kotlin.Int");
        ((HashMap)map).put("java.lang.Float", "kotlin.Float");
        ((HashMap)map).put("java.lang.Long", "kotlin.Long");
        ((HashMap)map).put("java.lang.Double", "kotlin.Double");
        primitiveWrapperFqNames = map;
        map = new HashMap();
        ((HashMap)map).put("java.lang.Object", "kotlin.Any");
        ((HashMap)map).put("java.lang.String", "kotlin.String");
        ((HashMap)map).put("java.lang.CharSequence", "kotlin.CharSequence");
        ((HashMap)map).put("java.lang.Throwable", "kotlin.Throwable");
        ((HashMap)map).put("java.lang.Cloneable", "kotlin.Cloneable");
        ((HashMap)map).put("java.lang.Number", "kotlin.Number");
        ((HashMap)map).put("java.lang.Comparable", "kotlin.Comparable");
        ((HashMap)map).put("java.lang.Enum", "kotlin.Enum");
        ((HashMap)map).put("java.lang.annotation.Annotation", "kotlin.Annotation");
        ((HashMap)map).put("java.lang.Iterable", "kotlin.collections.Iterable");
        ((HashMap)map).put("java.util.Iterator", "kotlin.collections.Iterator");
        ((HashMap)map).put("java.util.Collection", "kotlin.collections.Collection");
        ((HashMap)map).put("java.util.List", "kotlin.collections.List");
        ((HashMap)map).put("java.util.Set", "kotlin.collections.Set");
        ((HashMap)map).put("java.util.ListIterator", "kotlin.collections.ListIterator");
        ((HashMap)map).put("java.util.Map", "kotlin.collections.Map");
        ((HashMap)map).put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        ((HashMap)map).put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        ((HashMap)map).put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        ((HashMap)map).putAll((Map)primitiveFqNames);
        ((HashMap)map).putAll((Map)primitiveWrapperFqNames);
        object2 = primitiveFqNames.values();
        Intrinsics.checkExpressionValueIsNotNull(object2, "primitiveFqNames.values");
        for (Object e : (Iterable)object2) {
            object = map;
            String string2 = (String)e;
            CharSequence charSequence = new StringBuilder();
            charSequence.append("kotlin.jvm.internal.");
            Intrinsics.checkExpressionValueIsNotNull(string2, "kotlinName");
            charSequence.append(StringsKt.substringAfterLast$default(string2, '.', null, 2, null));
            charSequence.append("CompanionObject");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(".Companion");
            Pair<StringBuilder, String> object3 = TuplesKt.to(charSequence, stringBuilder.toString());
            object.put(object3.getFirst(), object3.getSecond());
        }
        object2 = map;
        for (Map.Entry<Class<Function<?>>, Integer> entry : FUNCTION_CLASSES.entrySet()) {
            object = entry.getKey();
            n = ((Number)entry.getValue()).intValue();
            object = ((Class)object).getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("kotlin.Function");
            stringBuilder.append(n);
            ((HashMap)map).put(object, stringBuilder.toString());
        }
        classFqNames = map;
        object2 = map;
        map = new LinkedHashMap(MapsKt.mapCapacity(object2.size()));
        object = ((Iterable)object2.entrySet()).iterator();
        do {
            if (!object.hasNext()) {
                simpleNames = map;
                return;
            }
            object2 = (Map.Entry)object.next();
            map.put(object2.getKey(), StringsKt.substringAfterLast$default((String)object2.getValue(), '.', null, 2, null));
        } while (true);
    }

    public ClassReference(Class<?> class_) {
        Intrinsics.checkParameterIsNotNull(class_, "jClass");
        this.jClass = class_;
    }

    public static final /* synthetic */ Map access$getSimpleNames$cp() {
        return simpleNames;
    }

    private final Void error() {
        throw (Throwable)new KotlinReflectionNotSupportedError();
    }

    public static /* synthetic */ void isAbstract$annotations() {
    }

    public static /* synthetic */ void isCompanion$annotations() {
    }

    public static /* synthetic */ void isData$annotations() {
    }

    public static /* synthetic */ void isFinal$annotations() {
    }

    public static /* synthetic */ void isInner$annotations() {
    }

    public static /* synthetic */ void isOpen$annotations() {
    }

    public static /* synthetic */ void isSealed$annotations() {
    }

    public static /* synthetic */ void sealedSubclasses$annotations() {
    }

    public static /* synthetic */ void supertypes$annotations() {
    }

    public static /* synthetic */ void typeParameters$annotations() {
    }

    public static /* synthetic */ void visibility$annotations() {
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClassReference)) return false;
        if (!Intrinsics.areEqual(JvmClassMappingKt.getJavaObjectType(this), JvmClassMappingKt.getJavaObjectType((KClass)object))) return false;
        return true;
    }

    @Override
    public List<Annotation> getAnnotations() {
        this.error();
        throw null;
    }

    @Override
    public Collection<KFunction<Object>> getConstructors() {
        this.error();
        throw null;
    }

    @Override
    public Class<?> getJClass() {
        return this.jClass;
    }

    @Override
    public Collection<KCallable<?>> getMembers() {
        this.error();
        throw null;
    }

    @Override
    public Collection<KClass<?>> getNestedClasses() {
        this.error();
        throw null;
    }

    @Override
    public Object getObjectInstance() {
        this.error();
        throw null;
    }

    @Override
    public String getQualifiedName() {
        return Companion.getClassQualifiedName(this.getJClass());
    }

    @Override
    public List<KClass<? extends Object>> getSealedSubclasses() {
        this.error();
        throw null;
    }

    @Override
    public String getSimpleName() {
        return Companion.getClassSimpleName(this.getJClass());
    }

    @Override
    public List<KType> getSupertypes() {
        this.error();
        throw null;
    }

    @Override
    public List<KTypeParameter> getTypeParameters() {
        this.error();
        throw null;
    }

    @Override
    public KVisibility getVisibility() {
        this.error();
        throw null;
    }

    @Override
    public int hashCode() {
        return JvmClassMappingKt.getJavaObjectType(this).hashCode();
    }

    @Override
    public boolean isAbstract() {
        this.error();
        throw null;
    }

    @Override
    public boolean isCompanion() {
        this.error();
        throw null;
    }

    @Override
    public boolean isData() {
        this.error();
        throw null;
    }

    @Override
    public boolean isFinal() {
        this.error();
        throw null;
    }

    @Override
    public boolean isInner() {
        this.error();
        throw null;
    }

    @Override
    public boolean isInstance(Object object) {
        return Companion.isInstance(object, this.getJClass());
    }

    @Override
    public boolean isOpen() {
        this.error();
        throw null;
    }

    @Override
    public boolean isSealed() {
        this.error();
        throw null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getJClass().toString());
        stringBuilder.append(" (Kotlin reflection is not available)");
        return stringBuilder.toString();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getClassQualifiedName(Class<?> object) {
            Intrinsics.checkParameterIsNotNull(object, "jClass");
            boolean bl = ((Class)object).isAnonymousClass();
            Object object2 = null;
            String string2 = null;
            if (bl) {
                return object2;
            }
            if (((Class)object).isLocalClass()) {
                return object2;
            }
            if (((Class)object).isArray()) {
                object2 = ((Class)object).getComponentType();
                Intrinsics.checkExpressionValueIsNotNull(object2, "componentType");
                object = string2;
                if (((Class)object2).isPrimitive()) {
                    object2 = (String)classFqNames.get(((Class)object2).getName());
                    object = string2;
                    if (object2 != null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append("Array");
                        object = ((StringBuilder)object).toString();
                    }
                }
                if (object == null) return "kotlin.Array";
                return object;
            }
            string2 = (String)classFqNames.get(((Class)object).getName());
            if (string2 == null) return ((Class)object).getCanonicalName();
            return string2;
        }

        /*
         * Unable to fully structure code
         */
        public final String getClassSimpleName(Class<?> var1_1) {
            block8 : {
                Intrinsics.checkParameterIsNotNull(var1_1, "jClass");
                var2_2 = var1_1.isAnonymousClass();
                var3_3 = "Array";
                var4_4 = null;
                var5_5 = null;
                if (var2_2 != false) return var5_5;
                if (!var1_1.isLocalClass()) break block8;
                var3_3 = var1_1.getSimpleName();
                var4_4 = var1_1.getEnclosingMethod();
                if (var4_4 == null) ** GOTO lbl-1000
                Intrinsics.checkExpressionValueIsNotNull(var3_3, "name");
                var5_5 = new StringBuilder();
                var5_5.append(var4_4.getName());
                var5_5.append("$");
                var5_5 = StringsKt.substringAfter$default(var3_3, var5_5.toString(), null, 2, null);
                if (var5_5 != null) {
                    var1_1 = var5_5;
                } else if ((var1_1 = var1_1.getEnclosingConstructor()) != null) {
                    Intrinsics.checkExpressionValueIsNotNull(var3_3, "name");
                    var5_5 = new StringBuilder();
                    var5_5.append(var1_1.getName());
                    var5_5.append("$");
                    var1_1 = StringsKt.substringAfter$default(var3_3, var5_5.toString(), null, 2, null);
                } else {
                    var1_1 = null;
                }
                if (var1_1 != null) {
                    return var1_1;
                }
                Intrinsics.checkExpressionValueIsNotNull(var3_3, "name");
                return StringsKt.substringAfter$default(var3_3, '$', null, 2, null);
            }
            if (var1_1.isArray()) {
                var1_1 = var1_1.getComponentType();
                Intrinsics.checkExpressionValueIsNotNull(var1_1, "componentType");
                var5_5 = var4_4;
                if (var1_1.isPrimitive()) {
                    var1_1 = (String)ClassReference.access$getSimpleNames$cp().get(var1_1.getName());
                    var5_5 = var4_4;
                    if (var1_1 != null) {
                        var5_5 = new StringBuilder();
                        var5_5.append((String)var1_1);
                        var5_5.append("Array");
                        var5_5 = var5_5.toString();
                    }
                }
                var1_1 = var3_3;
                if (var5_5 == null) return var1_1;
                return var5_5;
            }
            var5_5 = (String)ClassReference.access$getSimpleNames$cp().get(var1_1.getName());
            if (var5_5 == null) return var1_1.getSimpleName();
            return var5_5;
        }

        public final boolean isInstance(Object object, Class<?> class_) {
            Intrinsics.checkParameterIsNotNull(class_, "jClass");
            Class<?> class_2 = FUNCTION_CLASSES;
            if (class_2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
            if ((class_2 = (Integer)class_2.get(class_)) != null) {
                return TypeIntrinsics.isFunctionOfArity(object, ((Number)((Object)class_2)).intValue());
            }
            class_2 = class_;
            if (!class_.isPrimitive()) return class_2.isInstance(object);
            class_2 = JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(class_));
            return class_2.isInstance(object);
        }
    }

}

