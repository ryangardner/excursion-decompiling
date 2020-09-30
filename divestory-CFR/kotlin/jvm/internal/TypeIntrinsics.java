/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Function;
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
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableIterable;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.jvm.internal.markers.KMutableSet;

public class TypeIntrinsics {
    public static Collection asMutableCollection(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToCollection(object);
        if (object instanceof KMutableCollection) return TypeIntrinsics.castToCollection(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableCollection");
        return TypeIntrinsics.castToCollection(object);
    }

    public static Collection asMutableCollection(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToCollection(object);
        if (object instanceof KMutableCollection) return TypeIntrinsics.castToCollection(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToCollection(object);
    }

    public static Iterable asMutableIterable(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToIterable(object);
        if (object instanceof KMutableIterable) return TypeIntrinsics.castToIterable(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableIterable");
        return TypeIntrinsics.castToIterable(object);
    }

    public static Iterable asMutableIterable(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToIterable(object);
        if (object instanceof KMutableIterable) return TypeIntrinsics.castToIterable(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToIterable(object);
    }

    public static Iterator asMutableIterator(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToIterator(object);
        if (object instanceof KMutableIterator) return TypeIntrinsics.castToIterator(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableIterator");
        return TypeIntrinsics.castToIterator(object);
    }

    public static Iterator asMutableIterator(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToIterator(object);
        if (object instanceof KMutableIterator) return TypeIntrinsics.castToIterator(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToIterator(object);
    }

    public static List asMutableList(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToList(object);
        if (object instanceof KMutableList) return TypeIntrinsics.castToList(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableList");
        return TypeIntrinsics.castToList(object);
    }

    public static List asMutableList(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToList(object);
        if (object instanceof KMutableList) return TypeIntrinsics.castToList(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToList(object);
    }

    public static ListIterator asMutableListIterator(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToListIterator(object);
        if (object instanceof KMutableListIterator) return TypeIntrinsics.castToListIterator(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableListIterator");
        return TypeIntrinsics.castToListIterator(object);
    }

    public static ListIterator asMutableListIterator(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToListIterator(object);
        if (object instanceof KMutableListIterator) return TypeIntrinsics.castToListIterator(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToListIterator(object);
    }

    public static Map asMutableMap(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToMap(object);
        if (object instanceof KMutableMap) return TypeIntrinsics.castToMap(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableMap");
        return TypeIntrinsics.castToMap(object);
    }

    public static Map asMutableMap(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToMap(object);
        if (object instanceof KMutableMap) return TypeIntrinsics.castToMap(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToMap(object);
    }

    public static Map.Entry asMutableMapEntry(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToMapEntry(object);
        if (object instanceof KMutableMap.Entry) return TypeIntrinsics.castToMapEntry(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableMap.MutableEntry");
        return TypeIntrinsics.castToMapEntry(object);
    }

    public static Map.Entry asMutableMapEntry(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToMapEntry(object);
        if (object instanceof KMutableMap.Entry) return TypeIntrinsics.castToMapEntry(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToMapEntry(object);
    }

    public static Set asMutableSet(Object object) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToSet(object);
        if (object instanceof KMutableSet) return TypeIntrinsics.castToSet(object);
        TypeIntrinsics.throwCce(object, "kotlin.collections.MutableSet");
        return TypeIntrinsics.castToSet(object);
    }

    public static Set asMutableSet(Object object, String string2) {
        if (!(object instanceof KMappedMarker)) return TypeIntrinsics.castToSet(object);
        if (object instanceof KMutableSet) return TypeIntrinsics.castToSet(object);
        TypeIntrinsics.throwCce(string2);
        return TypeIntrinsics.castToSet(object);
    }

    public static Object beforeCheckcastToFunctionOfArity(Object object, int n) {
        if (object == null) return object;
        if (TypeIntrinsics.isFunctionOfArity(object, n)) return object;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("kotlin.jvm.functions.Function");
        stringBuilder.append(n);
        TypeIntrinsics.throwCce(object, stringBuilder.toString());
        return object;
    }

    public static Object beforeCheckcastToFunctionOfArity(Object object, int n, String string2) {
        if (object == null) return object;
        if (TypeIntrinsics.isFunctionOfArity(object, n)) return object;
        TypeIntrinsics.throwCce(string2);
        return object;
    }

    public static Collection castToCollection(Object object) {
        try {
            return (Collection)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static Iterable castToIterable(Object object) {
        try {
            return (Iterable)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static Iterator castToIterator(Object object) {
        try {
            return (Iterator)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static List castToList(Object object) {
        try {
            return (List)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static ListIterator castToListIterator(Object object) {
        try {
            return (ListIterator)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static Map castToMap(Object object) {
        try {
            return (Map)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static Map.Entry castToMapEntry(Object object) {
        try {
            return (Map.Entry)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static Set castToSet(Object object) {
        try {
            return (Set)object;
        }
        catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static int getFunctionArity(Object object) {
        if (object instanceof FunctionBase) {
            return ((FunctionBase)object).getArity();
        }
        if (object instanceof Function0) {
            return 0;
        }
        if (object instanceof Function1) {
            return 1;
        }
        if (object instanceof Function2) {
            return 2;
        }
        if (object instanceof Function3) {
            return 3;
        }
        if (object instanceof Function4) {
            return 4;
        }
        if (object instanceof Function5) {
            return 5;
        }
        if (object instanceof Function6) {
            return 6;
        }
        if (object instanceof Function7) {
            return 7;
        }
        if (object instanceof Function8) {
            return 8;
        }
        if (object instanceof Function9) {
            return 9;
        }
        if (object instanceof Function10) {
            return 10;
        }
        if (object instanceof Function11) {
            return 11;
        }
        if (object instanceof Function12) {
            return 12;
        }
        if (object instanceof Function13) {
            return 13;
        }
        if (object instanceof Function14) {
            return 14;
        }
        if (object instanceof Function15) {
            return 15;
        }
        if (object instanceof Function16) {
            return 16;
        }
        if (object instanceof Function17) {
            return 17;
        }
        if (object instanceof Function18) {
            return 18;
        }
        if (object instanceof Function19) {
            return 19;
        }
        if (object instanceof Function20) {
            return 20;
        }
        if (object instanceof Function21) {
            return 21;
        }
        if (!(object instanceof Function22)) return -1;
        return 22;
    }

    public static boolean isFunctionOfArity(Object object, int n) {
        if (!(object instanceof Function)) return false;
        if (TypeIntrinsics.getFunctionArity(object) != n) return false;
        return true;
    }

    public static boolean isMutableCollection(Object object) {
        if (!(object instanceof Collection)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableCollection)) return false;
        return true;
    }

    public static boolean isMutableIterable(Object object) {
        if (!(object instanceof Iterable)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableIterable)) return false;
        return true;
    }

    public static boolean isMutableIterator(Object object) {
        if (!(object instanceof Iterator)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableIterator)) return false;
        return true;
    }

    public static boolean isMutableList(Object object) {
        if (!(object instanceof List)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableList)) return false;
        return true;
    }

    public static boolean isMutableListIterator(Object object) {
        if (!(object instanceof ListIterator)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableListIterator)) return false;
        return true;
    }

    public static boolean isMutableMap(Object object) {
        if (!(object instanceof Map)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableMap)) return false;
        return true;
    }

    public static boolean isMutableMapEntry(Object object) {
        if (!(object instanceof Map.Entry)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableMap.Entry)) return false;
        return true;
    }

    public static boolean isMutableSet(Object object) {
        if (!(object instanceof Set)) return false;
        if (!(object instanceof KMappedMarker)) return true;
        if (!(object instanceof KMutableSet)) return false;
        return true;
    }

    private static <T extends Throwable> T sanitizeStackTrace(T t) {
        return Intrinsics.sanitizeStackTrace(t, TypeIntrinsics.class.getName());
    }

    public static ClassCastException throwCce(ClassCastException classCastException) {
        throw TypeIntrinsics.sanitizeStackTrace(classCastException);
    }

    public static void throwCce(Object object, String string2) {
        object = object == null ? "null" : object.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(" cannot be cast to ");
        stringBuilder.append(string2);
        TypeIntrinsics.throwCce(stringBuilder.toString());
    }

    public static void throwCce(String string2) {
        throw TypeIntrinsics.throwCce(new ClassCastException(string2));
    }
}

