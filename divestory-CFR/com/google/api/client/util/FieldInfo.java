/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import com.google.api.client.util.NullValue;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Value;
import com.google.common.base.Ascii;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

public class FieldInfo {
    private static final Map<Field, FieldInfo> CACHE = new WeakHashMap<Field, FieldInfo>();
    private final Field field;
    private final boolean isPrimitive;
    private final String name;
    private final Method[] setters;

    FieldInfo(Field field, String string2) {
        this.field = field;
        string2 = string2 == null ? null : string2.intern();
        this.name = string2;
        this.isPrimitive = Data.isPrimitive(this.getType());
        this.setters = this.settersMethodForField(field);
    }

    public static Object getFieldValue(Field object, Object object2) {
        try {
            return ((Field)object).get(object2);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    public static FieldInfo of(Enum<?> enum_) {
        try {
            FieldInfo fieldInfo = FieldInfo.of(enum_.getClass().getField(enum_.name()));
            boolean bl = fieldInfo != null;
            Preconditions.checkArgument(bl, "enum constant missing @Value or @NullValue annotation: %s", enum_);
            return fieldInfo;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new RuntimeException(noSuchFieldException);
        }
    }

    public static FieldInfo of(Field field) {
        Object object = null;
        if (field == null) {
            return null;
        }
        Map<Field, FieldInfo> map = CACHE;
        synchronized (map) {
            FieldInfo fieldInfo = CACHE.get(field);
            boolean bl = field.isEnumConstant();
            Object object2 = fieldInfo;
            if (fieldInfo != null) return object2;
            if (!bl) {
                object2 = fieldInfo;
                if (Modifier.isStatic(field.getModifiers())) return object2;
            }
            if (bl) {
                object2 = field.getAnnotation(Value.class);
                if (object2 != null) {
                    object2 = object2.value();
                } else {
                    if (field.getAnnotation(NullValue.class) == null) return null;
                    object2 = object;
                }
            } else {
                object2 = field.getAnnotation(Key.class);
                if (object2 == null) {
                    return null;
                }
                object2 = object2.value();
                field.setAccessible(true);
            }
            object = object2;
            if ("##default".equals(object2)) {
                object = field.getName();
            }
            object2 = new FieldInfo(field, (String)object);
            CACHE.put(field, (FieldInfo)object2);
            return object2;
        }
    }

    public static void setFieldValue(Field field, Object object, Object object2) {
        if (Modifier.isFinal(field.getModifiers())) {
            Object object3 = FieldInfo.getFieldValue(field, object);
            if (object2 == null ? object3 == null : object2.equals(object3)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected final value <");
            stringBuilder.append(object3);
            stringBuilder.append("> but was <");
            stringBuilder.append(object2);
            stringBuilder.append("> on ");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field in ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        try {
            field.set(object, object2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
        catch (SecurityException securityException) {
            throw new IllegalArgumentException(securityException);
        }
    }

    private Method[] settersMethodForField(Field field) {
        ArrayList<Method> arrayList = new ArrayList<Method>();
        Method[] arrmethod = field.getDeclaringClass().getDeclaredMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = arrmethod[n2];
            String string2 = Ascii.toLowerCase(method.getName());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("set");
            stringBuilder.append(Ascii.toLowerCase(field.getName()));
            if (string2.equals(stringBuilder.toString()) && method.getParameterTypes().length == 1) {
                arrayList.add(method);
            }
            ++n2;
        }
        return arrayList.toArray(new Method[0]);
    }

    public <T extends Enum<T>> T enumValue() {
        return (T)Enum.valueOf(this.field.getDeclaringClass(), this.field.getName());
    }

    public ClassInfo getClassInfo() {
        return ClassInfo.of(this.field.getDeclaringClass());
    }

    public Field getField() {
        return this.field;
    }

    public Type getGenericType() {
        return this.field.getGenericType();
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getType() {
        return this.field.getType();
    }

    public Object getValue(Object object) {
        return FieldInfo.getFieldValue(this.field, object);
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.field.getModifiers());
    }

    public boolean isPrimitive() {
        return this.isPrimitive;
    }

    public void setValue(Object object, Object object2) {
        Method[] arrmethod = this.setters;
        if (arrmethod.length > 0) {
            for (Method method : arrmethod) {
                if (object2 != null && !method.getParameterTypes()[0].isAssignableFrom(object2.getClass())) continue;
                try {
                    method.invoke(object, object2);
                    return;
                }
                catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {}
            }
        }
        FieldInfo.setFieldValue(this.field, object, object2);
    }
}

