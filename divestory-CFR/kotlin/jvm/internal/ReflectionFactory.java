/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.lang.reflect.Type;
import java.util.List;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference2;
import kotlin.jvm.internal.PackageReference;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference2;
import kotlin.jvm.internal.TypeReference;
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

public class ReflectionFactory {
    private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

    public KClass createKotlinClass(Class class_) {
        return new ClassReference(class_);
    }

    public KClass createKotlinClass(Class class_, String string2) {
        return new ClassReference(class_);
    }

    public KFunction function(FunctionReference functionReference) {
        return functionReference;
    }

    public KClass getOrCreateKotlinClass(Class class_) {
        return new ClassReference(class_);
    }

    public KClass getOrCreateKotlinClass(Class class_, String string2) {
        return new ClassReference(class_);
    }

    public KDeclarationContainer getOrCreateKotlinPackage(Class class_, String string2) {
        return new PackageReference(class_, string2);
    }

    public KMutableProperty0 mutableProperty0(MutablePropertyReference0 mutablePropertyReference0) {
        return mutablePropertyReference0;
    }

    public KMutableProperty1 mutableProperty1(MutablePropertyReference1 mutablePropertyReference1) {
        return mutablePropertyReference1;
    }

    public KMutableProperty2 mutableProperty2(MutablePropertyReference2 mutablePropertyReference2) {
        return mutablePropertyReference2;
    }

    public KProperty0 property0(PropertyReference0 propertyReference0) {
        return propertyReference0;
    }

    public KProperty1 property1(PropertyReference1 propertyReference1) {
        return propertyReference1;
    }

    public KProperty2 property2(PropertyReference2 propertyReference2) {
        return propertyReference2;
    }

    public String renderLambdaToString(FunctionBase object) {
        String string2 = object.getClass().getGenericInterfaces()[0].toString();
        object = string2;
        if (!string2.startsWith(KOTLIN_JVM_FUNCTIONS)) return object;
        return string2.substring(21);
    }

    public String renderLambdaToString(Lambda lambda2) {
        return this.renderLambdaToString((FunctionBase)lambda2);
    }

    public KType typeOf(KClassifier kClassifier, List<KTypeProjection> list, boolean bl) {
        return new TypeReference(kClassifier, list, bl);
    }
}

