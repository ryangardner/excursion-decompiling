/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference2;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference2;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
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
        ReflectionFactory reflectionFactory = null;
        try {
            ReflectionFactory reflectionFactory2;
            reflectionFactory = reflectionFactory2 = (ReflectionFactory)Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        }
        catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException exception) {
            // empty catch block
        }
        if (reflectionFactory == null) {
            reflectionFactory = new ReflectionFactory();
        }
        factory = reflectionFactory;
        EMPTY_K_CLASS_ARRAY = new KClass[0];
    }

    public static KClass createKotlinClass(Class class_) {
        return factory.createKotlinClass(class_);
    }

    public static KClass createKotlinClass(Class class_, String string2) {
        return factory.createKotlinClass(class_, string2);
    }

    public static KFunction function(FunctionReference functionReference) {
        return factory.function(functionReference);
    }

    public static KClass getOrCreateKotlinClass(Class class_) {
        return factory.getOrCreateKotlinClass(class_);
    }

    public static KClass getOrCreateKotlinClass(Class class_, String string2) {
        return factory.getOrCreateKotlinClass(class_, string2);
    }

    public static KClass[] getOrCreateKotlinClasses(Class[] arrclass) {
        int n = arrclass.length;
        if (n == 0) {
            return EMPTY_K_CLASS_ARRAY;
        }
        KClass[] arrkClass = new KClass[n];
        int n2 = 0;
        while (n2 < n) {
            arrkClass[n2] = Reflection.getOrCreateKotlinClass(arrclass[n2]);
            ++n2;
        }
        return arrkClass;
    }

    public static KDeclarationContainer getOrCreateKotlinPackage(Class class_, String string2) {
        return factory.getOrCreateKotlinPackage(class_, string2);
    }

    public static KMutableProperty0 mutableProperty0(MutablePropertyReference0 mutablePropertyReference0) {
        return factory.mutableProperty0(mutablePropertyReference0);
    }

    public static KMutableProperty1 mutableProperty1(MutablePropertyReference1 mutablePropertyReference1) {
        return factory.mutableProperty1(mutablePropertyReference1);
    }

    public static KMutableProperty2 mutableProperty2(MutablePropertyReference2 mutablePropertyReference2) {
        return factory.mutableProperty2(mutablePropertyReference2);
    }

    public static KType nullableTypeOf(Class class_) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), Collections.<KTypeProjection>emptyList(), true);
    }

    public static KType nullableTypeOf(Class class_, KTypeProjection kTypeProjection) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), Collections.singletonList(kTypeProjection), true);
    }

    public static KType nullableTypeOf(Class class_, KTypeProjection kTypeProjection, KTypeProjection kTypeProjection2) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), Arrays.asList(kTypeProjection, kTypeProjection2), true);
    }

    public static KType nullableTypeOf(Class class_, KTypeProjection ... arrkTypeProjection) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), ArraysKt.toList(arrkTypeProjection), true);
    }

    public static KProperty0 property0(PropertyReference0 propertyReference0) {
        return factory.property0(propertyReference0);
    }

    public static KProperty1 property1(PropertyReference1 propertyReference1) {
        return factory.property1(propertyReference1);
    }

    public static KProperty2 property2(PropertyReference2 propertyReference2) {
        return factory.property2(propertyReference2);
    }

    public static String renderLambdaToString(FunctionBase functionBase) {
        return factory.renderLambdaToString(functionBase);
    }

    public static String renderLambdaToString(Lambda lambda2) {
        return factory.renderLambdaToString(lambda2);
    }

    public static KType typeOf(Class class_) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), Collections.<KTypeProjection>emptyList(), false);
    }

    public static KType typeOf(Class class_, KTypeProjection kTypeProjection) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), Collections.singletonList(kTypeProjection), false);
    }

    public static KType typeOf(Class class_, KTypeProjection kTypeProjection, KTypeProjection kTypeProjection2) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), Arrays.asList(kTypeProjection, kTypeProjection2), false);
    }

    public static KType typeOf(Class class_, KTypeProjection ... arrkTypeProjection) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(class_), ArraysKt.toList(arrkTypeProjection), false);
    }
}

