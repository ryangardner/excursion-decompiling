/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.ArraysKt;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001\u00a2\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003\u00a2\u0006\u0002\u0010\u0016\u001a8\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\b\u00a2\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u00a2\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003\u00a2\u0006\u0002\u0010!\u00a8\u0006\""}, d2={"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/ArraysKt")
class ArraysKt__ArraysKt
extends ArraysKt__ArraysJVMKt {
    public static final <T> boolean contentDeepEquals(T[] arrT, T[] arrT2) {
        Intrinsics.checkParameterIsNotNull(arrT, "$this$contentDeepEqualsImpl");
        Intrinsics.checkParameterIsNotNull(arrT2, "other");
        if (arrT == arrT2) {
            return true;
        }
        if (arrT.length != arrT2.length) {
            return false;
        }
        int n = arrT.length;
        int n2 = 0;
        while (n2 < n) {
            T t = arrT[n2];
            T t2 = arrT2[n2];
            if (t != t2) {
                if (t == null) return false;
                if (t2 == null) {
                    return false;
                }
                if (t instanceof Object[] && t2 instanceof Object[] ? !ArraysKt.contentDeepEquals((Object[])t, (Object[])t2) : (t instanceof byte[] && t2 instanceof byte[] ? !Arrays.equals((byte[])t, (byte[])t2) : (t instanceof short[] && t2 instanceof short[] ? !Arrays.equals((short[])t, (short[])t2) : (t instanceof int[] && t2 instanceof int[] ? !Arrays.equals((int[])t, (int[])t2) : (t instanceof long[] && t2 instanceof long[] ? !Arrays.equals((long[])t, (long[])t2) : (t instanceof float[] && t2 instanceof float[] ? !Arrays.equals((float[])t, (float[])t2) : (t instanceof double[] && t2 instanceof double[] ? !Arrays.equals((double[])t, (double[])t2) : (t instanceof char[] && t2 instanceof char[] ? !Arrays.equals((char[])t, (char[])t2) : (t instanceof boolean[] && t2 instanceof boolean[] ? !Arrays.equals((boolean[])t, (boolean[])t2) : (t instanceof UByteArray && t2 instanceof UByteArray ? !UArraysKt.contentEquals-kdPth3s(((UByteArray)t).unbox-impl(), ((UByteArray)t2).unbox-impl()) : (t instanceof UShortArray && t2 instanceof UShortArray ? !UArraysKt.contentEquals-mazbYpA(((UShortArray)t).unbox-impl(), ((UShortArray)t2).unbox-impl()) : (t instanceof UIntArray && t2 instanceof UIntArray ? !UArraysKt.contentEquals-ctEhBpI(((UIntArray)t).unbox-impl(), ((UIntArray)t2).unbox-impl()) : (t instanceof ULongArray && t2 instanceof ULongArray ? !UArraysKt.contentEquals-us8wMrg(((ULongArray)t).unbox-impl(), ((ULongArray)t2).unbox-impl()) : Intrinsics.areEqual(t, t2) ^ true))))))))))))) {
                    return false;
                }
            }
            ++n2;
        }
        return true;
    }

    public static final <T> String contentDeepToString(T[] object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$contentDeepToStringImpl");
        StringBuilder stringBuilder = new StringBuilder(RangesKt.coerceAtMost(((T[])object).length, 429496729) * 5 + 2);
        ArraysKt__ArraysKt.contentDeepToStringInternal$ArraysKt__ArraysKt(object, stringBuilder, new ArrayList());
        object = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(object, "StringBuilder(capacity).\u2026builderAction).toString()");
        return object;
    }

    private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] arrT, StringBuilder stringBuilder, List<Object[]> list) {
        if (list.contains(arrT)) {
            stringBuilder.append("[...]");
            return;
        }
        list.add(arrT);
        stringBuilder.append('[');
        int n = 0;
        int n2 = arrT.length;
        do {
            Object object;
            if (n >= n2) {
                stringBuilder.append(']');
                list.remove(CollectionsKt.getLastIndex(list));
                return;
            }
            if (n != 0) {
                stringBuilder.append(", ");
            }
            if ((object = arrT[n]) == null) {
                stringBuilder.append("null");
            } else if (object instanceof Object[]) {
                ArraysKt__ArraysKt.contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])object, stringBuilder, list);
            } else if (object instanceof byte[]) {
                object = Arrays.toString((byte[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof short[]) {
                object = Arrays.toString((short[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof int[]) {
                object = Arrays.toString((int[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof long[]) {
                object = Arrays.toString((long[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof float[]) {
                object = Arrays.toString((float[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof double[]) {
                object = Arrays.toString((double[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof char[]) {
                object = Arrays.toString((char[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof boolean[]) {
                object = Arrays.toString((boolean[])object);
                Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Arrays.toString(this)");
                stringBuilder.append((String)object);
            } else if (object instanceof UByteArray) {
                stringBuilder.append(UArraysKt.contentToString-GBYM_sE(((UByteArray)object).unbox-impl()));
            } else if (object instanceof UShortArray) {
                stringBuilder.append(UArraysKt.contentToString-rL5Bavg(((UShortArray)object).unbox-impl()));
            } else if (object instanceof UIntArray) {
                stringBuilder.append(UArraysKt.contentToString--ajY-9A(((UIntArray)object).unbox-impl()));
            } else if (object instanceof ULongArray) {
                stringBuilder.append(UArraysKt.contentToString-QwZRm1k(((ULongArray)object).unbox-impl()));
            } else {
                stringBuilder.append(object.toString());
            }
            ++n;
        } while (true);
    }

    public static final <T> List<T> flatten(T[][] arrT) {
        int n;
        Intrinsics.checkParameterIsNotNull(arrT, "$this$flatten");
        Object object = (Object[])arrT;
        int n2 = ((Object[])object).length;
        int n3 = 0;
        int n4 = 0;
        for (n = 0; n < n2; n4 += ((Object[])object[n]).length, ++n) {
        }
        object = new ArrayList(n4);
        n4 = arrT.length;
        n = n3;
        while (n < n4) {
            T[] arrT2 = arrT[n];
            CollectionsKt.addAll((Collection)object, arrT2);
            ++n;
        }
        return (List)object;
    }

    private static final <C extends Object[], R> R ifEmpty(C object, Function0<? extends R> function0) {
        boolean bl = ((C)object).length == 0;
        if (!bl) return (R)object;
        object = function0.invoke();
        return (R)object;
    }

    private static final boolean isNullOrEmpty(Object[] arrobject) {
        boolean bl = false;
        if (arrobject == null) return true;
        boolean bl2 = arrobject.length == 0;
        if (!bl2) return bl;
        return true;
    }

    public static final <T, R> Pair<List<T>, List<R>> unzip(Pair<? extends T, ? extends R>[] arrpair) {
        Intrinsics.checkParameterIsNotNull(arrpair, "$this$unzip");
        ArrayList<T> arrayList = new ArrayList<T>(arrpair.length);
        ArrayList<R> arrayList2 = new ArrayList<R>(arrpair.length);
        int n = arrpair.length;
        int n2 = 0;
        while (n2 < n) {
            Pair<T, R> pair = arrpair[n2];
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
            ++n2;
        }
        return TuplesKt.to(arrayList, arrayList2);
    }
}

