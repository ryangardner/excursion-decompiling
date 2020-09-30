/*
 * Decompiled with CFR <Could not determine version>.
 */
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

@Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a\u00a2\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G\u00a2\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018\u00c7\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001c"}, d2={"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class JvmClassMappingKt {
    public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(T object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$annotationClass");
        object = object.annotationType();
        Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.annot\u2026otation).annotationType()");
        object = JvmClassMappingKt.getKotlinClass(object);
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
        return object;
    }

    public static final <T> Class<T> getJavaClass(T object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$javaClass");
        object = object.getClass();
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        return object;
    }

    public static final <T> Class<T> getJavaClass(KClass<T> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$java");
        object = ((ClassBasedDeclarationContainer)object).getJClass();
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        return object;
    }

    public static final <T> Class<T> getJavaObjectType(KClass<T> class_) {
        Intrinsics.checkParameterIsNotNull(class_, "$this$javaObjectType");
        class_ = ((ClassBasedDeclarationContainer)((Object)class_)).getJClass();
        if (!class_.isPrimitive()) {
            if (class_ == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
            return class_;
        }
        String string2 = class_.getName();
        if (string2 != null) {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 109413500: {
                    if (!string2.equals("short")) break;
                    class_ = Short.class;
                    break;
                }
                case 97526364: {
                    if (!string2.equals("float")) break;
                    class_ = Float.class;
                    break;
                }
                case 64711720: {
                    if (!string2.equals("boolean")) break;
                    class_ = Boolean.class;
                    break;
                }
                case 3625364: {
                    if (!string2.equals("void")) break;
                    class_ = Void.class;
                    break;
                }
                case 3327612: {
                    if (!string2.equals("long")) break;
                    class_ = Long.class;
                    break;
                }
                case 3052374: {
                    if (!string2.equals("char")) break;
                    class_ = Character.class;
                    break;
                }
                case 3039496: {
                    if (!string2.equals("byte")) break;
                    class_ = Byte.class;
                    break;
                }
                case 104431: {
                    if (!string2.equals("int")) break;
                    class_ = Integer.class;
                    break;
                }
                case -1325958191: {
                    if (!string2.equals("double")) break;
                    class_ = Double.class;
                }
            }
        }
        if (class_ == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        return class_;
    }

    public static final <T> Class<T> getJavaPrimitiveType(KClass<T> class_) {
        Intrinsics.checkParameterIsNotNull(class_, "$this$javaPrimitiveType");
        class_ = ((ClassBasedDeclarationContainer)((Object)class_)).getJClass();
        if (class_.isPrimitive()) {
            if (class_ == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
            return class_;
        }
        if ((class_ = class_.getName()) == null) return null;
        switch (((String)((Object)class_)).hashCode()) {
            default: {
                return null;
            }
            case 761287205: {
                if (!((String)((Object)class_)).equals("java.lang.Double")) return null;
                return Double.TYPE;
            }
            case 399092968: {
                if (!((String)((Object)class_)).equals("java.lang.Void")) return null;
                return Void.TYPE;
            }
            case 398795216: {
                if (!((String)((Object)class_)).equals("java.lang.Long")) return null;
                return Long.TYPE;
            }
            case 398507100: {
                if (!((String)((Object)class_)).equals("java.lang.Byte")) return null;
                return Byte.TYPE;
            }
            case 344809556: {
                if (!((String)((Object)class_)).equals("java.lang.Boolean")) return null;
                return Boolean.TYPE;
            }
            case 155276373: {
                if (!((String)((Object)class_)).equals("java.lang.Character")) return null;
                return Character.TYPE;
            }
            case -515992664: {
                if (!((String)((Object)class_)).equals("java.lang.Short")) return null;
                return Short.TYPE;
            }
            case -527879800: {
                if (!((String)((Object)class_)).equals("java.lang.Float")) return null;
                return Float.TYPE;
            }
            case -2056817302: {
                if (!((String)((Object)class_)).equals("java.lang.Integer")) return null;
                return Integer.TYPE;
            }
        }
    }

    public static final <T> KClass<T> getKotlinClass(Class<T> class_) {
        Intrinsics.checkParameterIsNotNull(class_, "$this$kotlin");
        return Reflection.getOrCreateKotlinClass(class_);
    }

    public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(KClass<T> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$javaClass");
        object = ((Object)object).getClass();
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T>>");
        return object;
    }

    public static final /* synthetic */ <T> boolean isArrayOf(Object[] arrobject) {
        Intrinsics.checkParameterIsNotNull(arrobject, "$this$isArrayOf");
        Intrinsics.reifiedOperationMarker(4, "T");
        return Object.class.isAssignableFrom(arrobject.getClass().getComponentType());
    }

    public static /* synthetic */ void java$annotations(KClass kClass) {
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.", replaceWith=@ReplaceWith(expression="(this as Any).javaClass", imports={}))
    public static /* synthetic */ void javaClass$annotations(KClass kClass) {
    }
}

