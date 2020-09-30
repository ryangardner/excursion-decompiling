/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Deprecated(level=DeprecationLevel.HIDDEN, message="Provided for binary compatibility")
@Metadata(bv={1, 0, 3}, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\t\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u0005H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\tH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u0005H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\tH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010!J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\"\u0010#J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b$\u0010%J\u001e\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010*J\u001e\u0010&\u001a\u00020+*\u00020\t2\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b,\u0010-J\u001e\u0010&\u001a\u00020.*\u00020\f2\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u00100J\u001e\u0010&\u001a\u000201*\u00020\u000f2\u0006\u0010&\u001a\u00020(H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u00103J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020'05*\u00020\u0005H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b6\u00107J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020+05*\u00020\tH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b8\u00109J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020.05*\u00020\fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b:\u0010;J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020105*\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b<\u0010=\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006>"}, d2={"Lkotlin/collections/UArraysKt;", "", "()V", "contentEquals", "", "Lkotlin/UByteArray;", "other", "contentEquals-kdPth3s", "([B[B)Z", "Lkotlin/UIntArray;", "contentEquals-ctEhBpI", "([I[I)Z", "Lkotlin/ULongArray;", "contentEquals-us8wMrg", "([J[J)Z", "Lkotlin/UShortArray;", "contentEquals-mazbYpA", "([S[S)Z", "contentHashCode", "", "contentHashCode-GBYM_sE", "([B)I", "contentHashCode--ajY-9A", "([I)I", "contentHashCode-QwZRm1k", "([J)I", "contentHashCode-rL5Bavg", "([S)I", "contentToString", "", "contentToString-GBYM_sE", "([B)Ljava/lang/String;", "contentToString--ajY-9A", "([I)Ljava/lang/String;", "contentToString-QwZRm1k", "([J)Ljava/lang/String;", "contentToString-rL5Bavg", "([S)Ljava/lang/String;", "random", "Lkotlin/UByte;", "Lkotlin/random/Random;", "random-oSF2wD8", "([BLkotlin/random/Random;)B", "Lkotlin/UInt;", "random-2D5oskM", "([ILkotlin/random/Random;)I", "Lkotlin/ULong;", "random-JzugnMA", "([JLkotlin/random/Random;)J", "Lkotlin/UShort;", "random-s5X_as8", "([SLkotlin/random/Random;)S", "toTypedArray", "", "toTypedArray-GBYM_sE", "([B)[Lkotlin/UByte;", "toTypedArray--ajY-9A", "([I)[Lkotlin/UInt;", "toTypedArray-QwZRm1k", "([J)[Lkotlin/ULong;", "toTypedArray-rL5Bavg", "([S)[Lkotlin/UShort;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class UArraysKt {
    public static final UArraysKt INSTANCE = new UArraysKt();

    private UArraysKt() {
    }

    @JvmStatic
    public static final boolean contentEquals-ctEhBpI(int[] arrn, int[] arrn2) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(arrn2, "other");
        return Arrays.equals(arrn, arrn2);
    }

    @JvmStatic
    public static final boolean contentEquals-kdPth3s(byte[] arrby, byte[] arrby2) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(arrby2, "other");
        return Arrays.equals(arrby, arrby2);
    }

    @JvmStatic
    public static final boolean contentEquals-mazbYpA(short[] arrs, short[] arrs2) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(arrs2, "other");
        return Arrays.equals(arrs, arrs2);
    }

    @JvmStatic
    public static final boolean contentEquals-us8wMrg(long[] arrl, long[] arrl2) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(arrl2, "other");
        return Arrays.equals(arrl, arrl2);
    }

    @JvmStatic
    public static final int contentHashCode--ajY-9A(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$contentHashCode");
        return Arrays.hashCode(arrn);
    }

    @JvmStatic
    public static final int contentHashCode-GBYM_sE(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$contentHashCode");
        return Arrays.hashCode(arrby);
    }

    @JvmStatic
    public static final int contentHashCode-QwZRm1k(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$contentHashCode");
        return Arrays.hashCode(arrl);
    }

    @JvmStatic
    public static final int contentHashCode-rL5Bavg(short[] arrs) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$contentHashCode");
        return Arrays.hashCode(arrs);
    }

    @JvmStatic
    public static final String contentToString--ajY-9A(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$contentToString");
        return CollectionsKt.joinToString$default(UIntArray.box-impl(arrn), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    public static final String contentToString-GBYM_sE(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$contentToString");
        return CollectionsKt.joinToString$default(UByteArray.box-impl(arrby), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    public static final String contentToString-QwZRm1k(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$contentToString");
        return CollectionsKt.joinToString$default(ULongArray.box-impl(arrl), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    public static final String contentToString-rL5Bavg(short[] arrs) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$contentToString");
        return CollectionsKt.joinToString$default(UShortArray.box-impl(arrs), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    public static final int random-2D5oskM(int[] arrn, Random random) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$random");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (UIntArray.isEmpty-impl(arrn)) throw (Throwable)new NoSuchElementException("Array is empty.");
        return UIntArray.get-impl(arrn, random.nextInt(UIntArray.getSize-impl(arrn)));
    }

    @JvmStatic
    public static final long random-JzugnMA(long[] arrl, Random random) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$random");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (ULongArray.isEmpty-impl(arrl)) throw (Throwable)new NoSuchElementException("Array is empty.");
        return ULongArray.get-impl(arrl, random.nextInt(ULongArray.getSize-impl(arrl)));
    }

    @JvmStatic
    public static final byte random-oSF2wD8(byte[] arrby, Random random) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$random");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (UByteArray.isEmpty-impl(arrby)) throw (Throwable)new NoSuchElementException("Array is empty.");
        return UByteArray.get-impl(arrby, random.nextInt(UByteArray.getSize-impl(arrby)));
    }

    @JvmStatic
    public static final short random-s5X_as8(short[] arrs, Random random) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$random");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (UShortArray.isEmpty-impl(arrs)) throw (Throwable)new NoSuchElementException("Array is empty.");
        return UShortArray.get-impl(arrs, random.nextInt(UShortArray.getSize-impl(arrs)));
    }

    @JvmStatic
    public static final UInt[] toTypedArray--ajY-9A(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$toTypedArray");
        int n = UIntArray.getSize-impl(arrn);
        UInt[] arruInt = new UInt[n];
        int n2 = 0;
        while (n2 < n) {
            arruInt[n2] = UInt.box-impl(UIntArray.get-impl(arrn, n2));
            ++n2;
        }
        return arruInt;
    }

    @JvmStatic
    public static final UByte[] toTypedArray-GBYM_sE(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$toTypedArray");
        int n = UByteArray.getSize-impl(arrby);
        UByte[] arruByte = new UByte[n];
        int n2 = 0;
        while (n2 < n) {
            arruByte[n2] = UByte.box-impl(UByteArray.get-impl(arrby, n2));
            ++n2;
        }
        return arruByte;
    }

    @JvmStatic
    public static final ULong[] toTypedArray-QwZRm1k(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$toTypedArray");
        int n = ULongArray.getSize-impl(arrl);
        ULong[] arruLong = new ULong[n];
        int n2 = 0;
        while (n2 < n) {
            arruLong[n2] = ULong.box-impl(ULongArray.get-impl(arrl, n2));
            ++n2;
        }
        return arruLong;
    }

    @JvmStatic
    public static final UShort[] toTypedArray-rL5Bavg(short[] arrs) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$toTypedArray");
        int n = UShortArray.getSize-impl(arrs);
        UShort[] arruShort = new UShort[n];
        int n2 = 0;
        while (n2 < n) {
            arruShort[n2] = UShort.box-impl(UShortArray.get-impl(arrs, n2));
            ++n2;
        }
        return arruShort;
    }
}

