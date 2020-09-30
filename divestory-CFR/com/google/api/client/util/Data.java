/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.DataMap;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
    public static final BigDecimal NULL_BIG_DECIMAL;
    public static final BigInteger NULL_BIG_INTEGER;
    public static final Boolean NULL_BOOLEAN;
    public static final Byte NULL_BYTE;
    private static final ConcurrentHashMap<Class<?>, Object> NULL_CACHE;
    public static final Character NULL_CHARACTER;
    public static final DateTime NULL_DATE_TIME;
    public static final Double NULL_DOUBLE;
    public static final Float NULL_FLOAT;
    public static final Integer NULL_INTEGER;
    public static final Long NULL_LONG;
    public static final Short NULL_SHORT;
    public static final String NULL_STRING;

    static {
        NULL_BOOLEAN = new Boolean(true);
        NULL_STRING = new String();
        NULL_CHARACTER = new Character('\u0000');
        NULL_BYTE = new Byte(0);
        NULL_SHORT = new Short(0);
        NULL_INTEGER = new Integer(0);
        NULL_FLOAT = new Float(0.0f);
        NULL_LONG = new Long(0L);
        NULL_DOUBLE = new Double(0.0);
        NULL_BIG_INTEGER = new BigInteger("0");
        NULL_BIG_DECIMAL = new BigDecimal("0");
        NULL_DATE_TIME = new DateTime(0L);
        ConcurrentHashMap<Class<Boolean>, Boolean> concurrentHashMap = new ConcurrentHashMap<Class<Boolean>, Boolean>();
        NULL_CACHE = concurrentHashMap;
        concurrentHashMap.put(Boolean.class, NULL_BOOLEAN);
        NULL_CACHE.put(String.class, NULL_STRING);
        NULL_CACHE.put(Character.class, NULL_CHARACTER);
        NULL_CACHE.put(Byte.class, NULL_BYTE);
        NULL_CACHE.put(Short.class, NULL_SHORT);
        NULL_CACHE.put(Integer.class, NULL_INTEGER);
        NULL_CACHE.put(Float.class, NULL_FLOAT);
        NULL_CACHE.put(Long.class, NULL_LONG);
        NULL_CACHE.put(Double.class, NULL_DOUBLE);
        NULL_CACHE.put(BigInteger.class, NULL_BIG_INTEGER);
        NULL_CACHE.put(BigDecimal.class, NULL_BIG_DECIMAL);
        NULL_CACHE.put(DateTime.class, NULL_DATE_TIME);
    }

    public static <T> T clone(T object) {
        if (object == null) return object;
        if (Data.isPrimitive(object.getClass())) {
            return object;
        }
        if (object instanceof GenericData) {
            return (T)((GenericData)object).clone();
        }
        Class<?> class_ = object.getClass();
        if (class_.isArray()) {
            class_ = Array.newInstance(class_.getComponentType(), Array.getLength(object));
        } else if (object instanceof ArrayMap) {
            class_ = ((ArrayMap)object).clone();
        } else {
            if ("java.util.Arrays$ArrayList".equals(class_.getName())) {
                object = ((List)object).toArray();
                Data.deepCopy(object, object);
                return (T)Arrays.asList(object);
            }
            class_ = Types.newInstance(class_);
        }
        Data.deepCopy(object, class_);
        return (T)class_;
    }

    private static Object createNullInstance(Class<?> class_) {
        Class<?> class_2;
        int n;
        boolean bl = class_.isArray();
        int n2 = 0;
        if (!bl) {
            if (!class_.isEnum()) return Types.newInstance(class_);
            FieldInfo fieldInfo = ClassInfo.of(class_).getFieldInfo(null);
            Preconditions.checkNotNull(fieldInfo, "enum missing constant with @NullValue annotation: %s", class_);
            return fieldInfo.enumValue();
        }
        do {
            class_2 = class_.getComponentType();
            n2 = n = n2 + 1;
            class_ = class_2;
        } while (class_2.isArray());
        return Array.newInstance(class_2, new int[n]);
    }

    public static void deepCopy(Object entry, Object object) {
        Object object2 = entry.getClass();
        Object object3 = object.getClass();
        boolean bl = true;
        int n = 0;
        int n2 = 0;
        boolean bl2 = object2 == object3;
        Preconditions.checkArgument(bl2);
        if (((Class)object2).isArray()) {
            bl2 = Array.getLength(entry) == Array.getLength(object) ? bl : false;
            Preconditions.checkArgument(bl2);
            entry = Types.iterableOf(entry).iterator();
            n = n2;
            while (entry.hasNext()) {
                Array.set(object, n, Data.clone(entry.next()));
                ++n;
            }
            return;
        }
        if (Collection.class.isAssignableFrom((Class<?>)object2)) {
            entry = (Collection)((Object)entry);
            if (ArrayList.class.isAssignableFrom((Class<?>)object2)) {
                ((ArrayList)object).ensureCapacity(entry.size());
            }
            object = (Collection)object;
            entry = entry.iterator();
            while (entry.hasNext()) {
                object.add(Data.clone(entry.next()));
            }
            return;
        }
        bl2 = GenericData.class.isAssignableFrom((Class<?>)object2);
        if (!bl2 && Map.class.isAssignableFrom((Class<?>)object2)) {
            if (ArrayMap.class.isAssignableFrom((Class<?>)object2)) {
                object = (ArrayMap)object;
                entry = (ArrayMap)((Object)entry);
                n2 = ((ArrayMap)((Object)entry)).size();
                while (n < n2) {
                    ((ArrayMap)object).set(n, Data.clone(((ArrayMap)((Object)entry)).getValue(n)));
                    ++n;
                }
                return;
            }
            object = (Map)object;
            object2 = ((Map)((Object)entry)).entrySet().iterator();
            while (object2.hasNext()) {
                entry = object2.next();
                object.put(entry.getKey(), Data.clone(entry.getValue()));
            }
            return;
        }
        object2 = bl2 ? ((GenericData)entry).classInfo : ClassInfo.of(object2);
        Iterator<String> iterator2 = ((ClassInfo)object2).names.iterator();
        while (iterator2.hasNext()) {
            FieldInfo fieldInfo = ((ClassInfo)object2).getFieldInfo(iterator2.next());
            if (fieldInfo.isFinal() || bl2 && fieldInfo.isPrimitive() || (object3 = fieldInfo.getValue(entry)) == null) continue;
            fieldInfo.setValue(object, Data.clone(object3));
        }
    }

    public static boolean isNull(Object object) {
        if (object == null) return false;
        if (object != NULL_CACHE.get(object.getClass())) return false;
        return true;
    }

    public static boolean isPrimitive(Type type) {
        Type type2 = type;
        if (type instanceof WildcardType) {
            type2 = Types.getBound((WildcardType)type);
        }
        boolean bl = type2 instanceof Class;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        type = (Class)type2;
        if (((Class)type).isPrimitive()) return true;
        if (type == Character.class) return true;
        if (type == String.class) return true;
        if (type == Integer.class) return true;
        if (type == Long.class) return true;
        if (type == Short.class) return true;
        if (type == Byte.class) return true;
        if (type == Float.class) return true;
        if (type == Double.class) return true;
        if (type == BigInteger.class) return true;
        if (type == BigDecimal.class) return true;
        if (type == DateTime.class) return true;
        if (type != Boolean.class) return bl2;
        return true;
    }

    public static boolean isValueOfPrimitiveType(Object object) {
        if (object == null) return true;
        if (Data.isPrimitive(object.getClass())) return true;
        return false;
    }

    public static Map<String, Object> mapOf(Object object) {
        if (object == null) return Collections.emptyMap();
        if (Data.isNull(object)) {
            return Collections.emptyMap();
        }
        if (!(object instanceof Map)) return new DataMap(object, false);
        return (Map)object;
    }

    public static Collection<Object> newCollectionInstance(Type type) {
        Class class_ = type;
        if (type instanceof WildcardType) {
            class_ = Types.getBound((WildcardType)type);
        }
        type = class_;
        if (class_ instanceof ParameterizedType) {
            type = ((ParameterizedType)((Object)class_)).getRawType();
        }
        class_ = type instanceof Class ? (Class)type : null;
        if (type == null) return new ArrayList<Object>();
        if (type instanceof GenericArrayType) return new ArrayList<Object>();
        if (class_ != null) {
            if (class_.isArray()) return new ArrayList<Object>();
            if (class_.isAssignableFrom(ArrayList.class)) {
                return new ArrayList<Object>();
            }
        }
        if (class_ == null) {
            class_ = new StringBuilder();
            ((StringBuilder)((Object)class_)).append("unable to create new instance of type: ");
            ((StringBuilder)((Object)class_)).append(type);
            throw new IllegalArgumentException(((StringBuilder)((Object)class_)).toString());
        }
        if (class_.isAssignableFrom(HashSet.class)) {
            return new HashSet<Object>();
        }
        if (!class_.isAssignableFrom(TreeSet.class)) return (Collection)Types.newInstance(class_);
        return new TreeSet<Object>();
    }

    public static Map<String, Object> newMapInstance(Class<?> class_) {
        if (class_ == null) return ArrayMap.create();
        if (class_.isAssignableFrom(ArrayMap.class)) {
            return ArrayMap.create();
        }
        if (!class_.isAssignableFrom(TreeMap.class)) return (Map)Types.newInstance(class_);
        return new TreeMap<String, Object>();
    }

    public static <T> T nullOf(Class<T> object) {
        Object object2;
        Object object3 = object2 = NULL_CACHE.get(object);
        if (object2 != null) return (T)object3;
        object3 = Data.createNullInstance(object);
        if ((object = NULL_CACHE.putIfAbsent((Class<?>)object, object3)) == null) {
            return (T)object3;
        }
        object3 = object;
        return (T)object3;
    }

    /*
     * Unable to fully structure code
     */
    public static Object parsePrimitiveValue(Type var0, String var1_1) {
        var2_2 = var0 instanceof Class != false ? (Class)var0 : null;
        if (var0 == null || var2_2 != null) {
            if (var2_2 == Void.class) {
                return null;
            }
            if (var1_1 == null) return var1_1;
            if (var2_2 == null) return var1_1;
            if (var2_2.isAssignableFrom(String.class)) {
                return var1_1;
            }
            if (var2_2 != Character.class && var2_2 != Character.TYPE) {
                if (var2_2 == Boolean.class) return Boolean.valueOf((String)var1_1);
                if (var2_2 == Boolean.TYPE) {
                    return Boolean.valueOf((String)var1_1);
                }
                if (var2_2 == Byte.class) return Byte.valueOf((String)var1_1);
                if (var2_2 == Byte.TYPE) {
                    return Byte.valueOf((String)var1_1);
                }
                if (var2_2 == Short.class) return Short.valueOf((String)var1_1);
                if (var2_2 == Short.TYPE) {
                    return Short.valueOf((String)var1_1);
                }
                if (var2_2 == Integer.class) return Integer.valueOf((String)var1_1);
                if (var2_2 == Integer.TYPE) {
                    return Integer.valueOf((String)var1_1);
                }
                if (var2_2 == Long.class) return Long.valueOf((String)var1_1);
                if (var2_2 == Long.TYPE) {
                    return Long.valueOf((String)var1_1);
                }
                if (var2_2 == Float.class) return Float.valueOf((String)var1_1);
                if (var2_2 == Float.TYPE) {
                    return Float.valueOf((String)var1_1);
                }
                if (var2_2 == Double.class) return Double.valueOf((String)var1_1);
                if (var2_2 == Double.TYPE) {
                    return Double.valueOf((String)var1_1);
                }
                if (var2_2 == DateTime.class) {
                    return DateTime.parseRfc3339((String)var1_1);
                }
                if (var2_2 == BigInteger.class) {
                    return new BigInteger((String)var1_1);
                }
                if (var2_2 == BigDecimal.class) {
                    return new BigDecimal((String)var1_1);
                }
                if (var2_2.isEnum()) {
                    if (!ClassInfo.of(var2_2).names.contains(var1_1)) throw new IllegalArgumentException(String.format("given enum name %s not part of enumeration", new Object[]{var1_1}));
                    return ClassInfo.of(var2_2).getFieldInfo((String)var1_1).enumValue();
                } else {
                    ** GOTO lbl-1000
                }
            }
        } else lbl-1000: // 3 sources:
        {
            var1_1 = new StringBuilder();
            var1_1.append("expected primitive class, but got: ");
            var1_1.append(var0);
            throw new IllegalArgumentException(var1_1.toString());
        }
        if (var1_1.length() == 1) {
            return Character.valueOf(var1_1.charAt(0));
        }
        var0 = new StringBuilder();
        var0.append("expected type Character/char but got ");
        var0.append(var2_2);
        throw new IllegalArgumentException(var0.toString());
    }

    public static Type resolveWildcardTypeOrTypeVariable(List<Type> list, Type type) {
        Type type2 = type;
        if (type instanceof WildcardType) {
            type2 = Types.getBound((WildcardType)type);
        }
        while (type2 instanceof TypeVariable) {
            Type type3 = Types.resolveTypeVariable(list, (TypeVariable)type2);
            type = type2;
            if (type3 != null) {
                type = type3;
            }
            type2 = type;
            if (!(type instanceof TypeVariable)) continue;
            type2 = ((TypeVariable)type).getBounds()[0];
        }
        return type2;
    }
}

