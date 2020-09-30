/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringNumberConversionsKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\n\u0010\u0017\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010'\u001a\u00020(H\u0087\b\u001a\n\u0010)\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00105\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00106\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00107\u001a\r\u00108\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u00109\u001a\u00020#*\u00020(\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0087\b\u001a4\u0010B\u001a\u00020#*\u00020(2\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020(2\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010B\u001a\u00020#*\u00020\u00022\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010E\u001a\u00020\u0002*\u00020(2\u0006\u0010F\u001a\u00020\u0011\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00020N*\u00020(2\u0006\u0010O\u001a\u00020P2\b\b\u0002\u0010Q\u001a\u00020\u0011\u001a\u001c\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010U\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010V\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010V\u001a\u00020\u0014*\u00020\u00022\u0006\u0010W\u001a\u00020\u00142\b\b\u0002\u0010X\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010V\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010Y\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010Y\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010Z\u001a\u00020P*\u00020\u00022\b\b\u0002\u0010[\u001a\u00020\u0011H\u0087\b\u001a\r\u0010\\\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006]"}, d2={"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "charSequence", "", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringsJVMKt
extends StringsKt__StringNumberConversionsKt {
    private static final String String(StringBuffer stringBuffer) {
        return new String(stringBuffer);
    }

    private static final String String(StringBuilder stringBuilder) {
        return new String(stringBuilder);
    }

    private static final String String(byte[] arrby) {
        return new String(arrby, Charsets.UTF_8);
    }

    private static final String String(byte[] arrby, int n, int n2) {
        return new String(arrby, n, n2, Charsets.UTF_8);
    }

    private static final String String(byte[] arrby, int n, int n2, Charset charset) {
        return new String(arrby, n, n2, charset);
    }

    private static final String String(byte[] arrby, Charset charset) {
        return new String(arrby, charset);
    }

    private static final String String(char[] arrc) {
        return new String(arrc);
    }

    private static final String String(char[] arrc, int n, int n2) {
        return new String(arrc, n, n2);
    }

    private static final String String(int[] arrn, int n, int n2) {
        return new String(arrn, n, n2);
    }

    public static final String capitalize(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$capitalize");
        boolean bl = ((CharSequence)string2).length() > 0;
        CharSequence charSequence = string2;
        if (!bl) return charSequence;
        charSequence = string2;
        if (!Character.isLowerCase(string2.charAt(0))) return charSequence;
        charSequence = new StringBuilder();
        String string3 = string2.substring(0, 1);
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string3 = string3.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toUpperCase()");
        ((StringBuilder)charSequence).append(string3);
        string2 = string2.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    public static final String capitalize(String string2, Locale object) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$capitalize");
        Intrinsics.checkParameterIsNotNull(object, "locale");
        boolean bl = ((CharSequence)string2).length() > 0;
        if (!bl) return string2;
        char c = string2.charAt(0);
        if (!Character.isLowerCase(c)) return string2;
        StringBuilder stringBuilder = new StringBuilder();
        char c2 = Character.toTitleCase(c);
        if (c2 != Character.toUpperCase(c)) {
            stringBuilder.append(c2);
        } else {
            String string3 = string2.substring(0, 1);
            Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            object = string3.toUpperCase((Locale)object);
            Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).toUpperCase(locale)");
            stringBuilder.append((String)object);
        }
        string2 = string2.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        stringBuilder.append(string2);
        string2 = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(string2, "StringBuilder().apply(builderAction).toString()");
        return string2;
    }

    private static final int codePointAt(String string2, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.codePointAt(n);
    }

    private static final int codePointBefore(String string2, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.codePointBefore(n);
    }

    private static final int codePointCount(String string2, int n, int n2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.codePointCount(n, n2);
    }

    public static final int compareTo(String string2, String string3, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$compareTo");
        Intrinsics.checkParameterIsNotNull(string3, "other");
        if (!bl) return string2.compareTo(string3);
        return string2.compareToIgnoreCase(string3);
    }

    public static /* synthetic */ int compareTo$default(String string2, String string3, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.compareTo(string2, string3, bl);
        bl = false;
        return StringsKt.compareTo(string2, string3, bl);
    }

    public static final String concatToString(char[] arrc) {
        Intrinsics.checkParameterIsNotNull(arrc, "$this$concatToString");
        return new String(arrc);
    }

    public static final String concatToString(char[] arrc, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrc, "$this$concatToString");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, arrc.length);
        return new String(arrc, n, n2 - n);
    }

    public static /* synthetic */ String concatToString$default(char[] arrc, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return StringsKt.concatToString(arrc, n, n2);
        n2 = arrc.length;
        return StringsKt.concatToString(arrc, n, n2);
    }

    private static final boolean contentEquals(String string2, CharSequence charSequence) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.contentEquals(charSequence);
    }

    private static final boolean contentEquals(String string2, StringBuffer stringBuffer) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.contentEquals(stringBuffer);
    }

    public static final String decapitalize(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$decapitalize");
        boolean bl = ((CharSequence)string2).length() > 0;
        CharSequence charSequence = string2;
        if (!bl) return charSequence;
        charSequence = string2;
        if (!Character.isUpperCase(string2.charAt(0))) return charSequence;
        charSequence = new StringBuilder();
        String string3 = string2.substring(0, 1);
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string3 = string3.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase()");
        ((StringBuilder)charSequence).append(string3);
        string2 = string2.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    public static final String decapitalize(String string2, Locale object) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$decapitalize");
        Intrinsics.checkParameterIsNotNull(object, "locale");
        boolean bl = ((CharSequence)string2).length() > 0;
        CharSequence charSequence = string2;
        if (!bl) return charSequence;
        charSequence = string2;
        if (Character.isLowerCase(string2.charAt(0))) return charSequence;
        charSequence = new StringBuilder();
        String string3 = string2.substring(0, 1);
        Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        object = string3.toLowerCase((Locale)object);
        Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).toLowerCase(locale)");
        ((StringBuilder)charSequence).append((String)object);
        string2 = string2.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    public static final String decodeToString(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$decodeToString");
        return new String(arrby, Charsets.UTF_8);
    }

    public static final String decodeToString(byte[] object, int n, int n2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(object, "$this$decodeToString");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, ((byte[])object).length);
        if (!bl) {
            return new String((byte[])object, n, n2 - n, Charsets.UTF_8);
        }
        object = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(object, n, n2 - n)).toString();
        Intrinsics.checkExpressionValueIsNotNull(object, "decoder.decode(ByteBuffe\u2026- startIndex)).toString()");
        return object;
    }

    public static /* synthetic */ String decodeToString$default(byte[] arrby, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrby.length;
        }
        if ((n3 & 4) == 0) return StringsKt.decodeToString(arrby, n, n2, bl);
        bl = false;
        return StringsKt.decodeToString(arrby, n, n2, bl);
    }

    public static final byte[] encodeToByteArray(String arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$encodeToByteArray");
        arrby = arrby.getBytes(Charsets.UTF_8);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        return arrby;
    }

    public static final byte[] encodeToByteArray(String arrby, int n, int n2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$encodeToByteArray");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, arrby.length());
        if (!bl) {
            String string2 = arrby.substring(n, n2);
            Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            arrby = Charsets.UTF_8;
            if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            arrby = string2.getBytes((Charset)arrby);
            Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
            return arrby;
        }
        ByteBuffer byteBuffer = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap((CharSequence)arrby, n, n2));
        if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            n = byteBuffer.remaining();
            arrby = byteBuffer.array();
            if (arrby == null) {
                Intrinsics.throwNpe();
            }
            if (n == arrby.length) {
                arrby = byteBuffer.array();
                Intrinsics.checkExpressionValueIsNotNull(arrby, "byteBuffer.array()");
                return arrby;
            }
        }
        arrby = new byte[byteBuffer.remaining()];
        byteBuffer.get(arrby);
        return arrby;
    }

    public static /* synthetic */ byte[] encodeToByteArray$default(String string2, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = string2.length();
        }
        if ((n3 & 4) == 0) return StringsKt.encodeToByteArray(string2, n, n2, bl);
        bl = false;
        return StringsKt.encodeToByteArray(string2, n, n2, bl);
    }

    public static final boolean endsWith(String string2, String string3, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(string3, "suffix");
        if (bl) return StringsKt.regionMatches(string2, string2.length() - string3.length(), string3, 0, string3.length(), true);
        return string2.endsWith(string3);
    }

    public static /* synthetic */ boolean endsWith$default(String string2, String string3, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.endsWith(string2, string3, bl);
        bl = false;
        return StringsKt.endsWith(string2, string3, bl);
    }

    public static final boolean equals(String string2, String string3, boolean bl) {
        if (string2 == null) {
            if (string3 != null) return false;
            return true;
        }
        if (bl) return string2.equalsIgnoreCase(string3);
        return string2.equals(string3);
    }

    public static /* synthetic */ boolean equals$default(String string2, String string3, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.equals(string2, string3, bl);
        bl = false;
        return StringsKt.equals(string2, string3, bl);
    }

    private static final String format(String string2, Locale locale, Object ... arrobject) {
        string2 = String.format(locale, string2, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.String.format(locale, this, *args)");
        return string2;
    }

    private static final String format(String string2, Object ... arrobject) {
        string2 = String.format(string2, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.String.format(this, *args)");
        return string2;
    }

    private static final String format(StringCompanionObject object, String string2, Object ... arrobject) {
        object = String.format(string2, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(object, "java.lang.String.format(format, *args)");
        return object;
    }

    private static final String format(StringCompanionObject object, Locale locale, String string2, Object ... arrobject) {
        object = String.format(locale, string2, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(object, "java.lang.String.format(locale, format, *args)");
        return object;
    }

    public static final Comparator<String> getCASE_INSENSITIVE_ORDER(StringCompanionObject object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$CASE_INSENSITIVE_ORDER");
        object = String.CASE_INSENSITIVE_ORDER;
        Intrinsics.checkExpressionValueIsNotNull(object, "java.lang.String.CASE_INSENSITIVE_ORDER");
        return object;
    }

    private static final String intern(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.intern();
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).intern()");
        return string2;
    }

    public static final boolean isBlank(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$isBlank");
        int n = charSequence.length();
        boolean bl = false;
        if (n == 0) return true;
        Object object = StringsKt.getIndices(charSequence);
        if (!(object instanceof Collection) || !((Collection)object).isEmpty()) {
            object = object.iterator();
            while (object.hasNext()) {
                if (CharsKt.isWhitespace(charSequence.charAt(((IntIterator)object).nextInt()))) continue;
                n = 0;
                break;
            }
        } else {
            n = 1;
        }
        if (n == 0) return bl;
        return true;
    }

    private static final int nativeIndexOf(String string2, char c, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.indexOf(c, n);
    }

    private static final int nativeIndexOf(String string2, String string3, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.indexOf(string3, n);
    }

    private static final int nativeLastIndexOf(String string2, char c, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.lastIndexOf(c, n);
    }

    private static final int nativeLastIndexOf(String string2, String string3, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.lastIndexOf(string3, n);
    }

    private static final int offsetByCodePoints(String string2, int n, int n2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        return string2.offsetByCodePoints(n, n2);
    }

    public static final boolean regionMatches(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3, boolean bl) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$regionMatches");
        Intrinsics.checkParameterIsNotNull(charSequence2, "other");
        if (!(charSequence instanceof String)) return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, n2, n3, bl);
        if (!(charSequence2 instanceof String)) return StringsKt.regionMatchesImpl(charSequence, n, charSequence2, n2, n3, bl);
        return StringsKt.regionMatches((String)charSequence, n, (String)charSequence2, n2, n3, bl);
    }

    public static final boolean regionMatches(String string2, int n, String string3, int n2, int n3, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$regionMatches");
        Intrinsics.checkParameterIsNotNull(string3, "other");
        if (bl) return string2.regionMatches(bl, n, string3, n2, n3);
        return string2.regionMatches(n, string3, n2, n3);
    }

    public static /* synthetic */ boolean regionMatches$default(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 16) == 0) return StringsKt.regionMatches(charSequence, n, charSequence2, n2, n3, bl);
        bl = false;
        return StringsKt.regionMatches(charSequence, n, charSequence2, n2, n3, bl);
    }

    public static /* synthetic */ boolean regionMatches$default(String string2, int n, String string3, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 16) == 0) return StringsKt.regionMatches(string2, n, string3, n2, n3, bl);
        bl = false;
        return StringsKt.regionMatches(string2, n, string3, n2, n3, bl);
    }

    public static final String repeat(CharSequence arrc, int n) {
        Intrinsics.checkParameterIsNotNull(arrc, "$this$repeat");
        int n2 = 0;
        char c = '\u0001';
        int n3 = n >= 0 ? 1 : 0;
        if (n3 == 0) {
            arrc = new StringBuilder();
            arrc.append("Count 'n' must be non-negative, but was ");
            arrc.append(n);
            arrc.append('.');
            throw (Throwable)new IllegalArgumentException(arrc.toString().toString());
        }
        String string2 = "";
        CharSequence charSequence = string2;
        if (n == 0) return charSequence;
        if (n == 1) {
            return arrc.toString();
        }
        n3 = arrc.length();
        charSequence = string2;
        if (n3 == 0) return charSequence;
        if (n3 != 1) {
            charSequence = new StringBuilder(arrc.length() * n);
            if (1 <= n) {
                n3 = c;
                do {
                    ((StringBuilder)charSequence).append((CharSequence)arrc);
                    if (n3 == n) break;
                    ++n3;
                } while (true);
            }
            charSequence = ((StringBuilder)charSequence).toString();
            Intrinsics.checkExpressionValueIsNotNull(charSequence, "sb.toString()");
            return charSequence;
        }
        c = arrc.charAt(0);
        arrc = new char[n];
        n3 = n2;
        while (n3 < n) {
            arrc[n3] = c;
            ++n3;
        }
        return new String(arrc);
    }

    public static final String replace(String string2, char c, char c2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replace");
        if (bl) return SequencesKt.joinToString$default(StringsKt.splitToSequence$default((CharSequence)string2, new char[]{c}, bl, 0, 4, null), String.valueOf(c2), null, null, 0, null, null, 62, null);
        string2 = string2.replace(c, c2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026replace(oldChar, newChar)");
        return string2;
    }

    public static final String replace(String string2, String string3, String string4, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replace");
        Intrinsics.checkParameterIsNotNull(string3, "oldValue");
        Intrinsics.checkParameterIsNotNull(string4, "newValue");
        return SequencesKt.joinToString$default(StringsKt.splitToSequence$default((CharSequence)string2, new String[]{string3}, bl, 0, 4, null), string4, null, null, 0, null, null, 62, null);
    }

    public static /* synthetic */ String replace$default(String string2, char c, char c2, boolean bl, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replace(string2, c, c2, bl);
        bl = false;
        return StringsKt.replace(string2, c, c2, bl);
    }

    public static /* synthetic */ String replace$default(String string2, String string3, String string4, boolean bl, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replace(string2, string3, string4, bl);
        bl = false;
        return StringsKt.replace(string2, string3, string4, bl);
    }

    public static final String replaceFirst(String string2, char c, char c2, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replaceFirst");
        CharSequence charSequence = string2;
        int n = StringsKt.indexOf$default(charSequence, c, 0, bl, 2, null);
        if (n >= 0) return ((Object)StringsKt.replaceRange(charSequence, n, n + 1, (CharSequence)String.valueOf(c2))).toString();
        return string2;
    }

    public static final String replaceFirst(String string2, String string3, String string4, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$replaceFirst");
        Intrinsics.checkParameterIsNotNull(string3, "oldValue");
        Intrinsics.checkParameterIsNotNull(string4, "newValue");
        CharSequence charSequence = string2;
        int n = StringsKt.indexOf$default(charSequence, string3, 0, bl, 2, null);
        if (n >= 0) return ((Object)StringsKt.replaceRange(charSequence, n, string3.length() + n, (CharSequence)string4)).toString();
        return string2;
    }

    public static /* synthetic */ String replaceFirst$default(String string2, char c, char c2, boolean bl, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceFirst(string2, c, c2, bl);
        bl = false;
        return StringsKt.replaceFirst(string2, c, c2, bl);
    }

    public static /* synthetic */ String replaceFirst$default(String string2, String string3, String string4, boolean bl, int n, Object object) {
        if ((n & 4) == 0) return StringsKt.replaceFirst(string2, string3, string4, bl);
        bl = false;
        return StringsKt.replaceFirst(string2, string3, string4, bl);
    }

    public static final List<String> split(CharSequence arrstring, Pattern pattern, int n) {
        Intrinsics.checkParameterIsNotNull(arrstring, "$this$split");
        Intrinsics.checkParameterIsNotNull(pattern, "regex");
        int n2 = n >= 0 ? 1 : 0;
        if (n2 == 0) {
            arrstring = new StringBuilder();
            arrstring.append("Limit must be non-negative, but was ");
            arrstring.append(n);
            arrstring.append('.');
            throw (Throwable)new IllegalArgumentException(arrstring.toString().toString());
        }
        n2 = n;
        if (n == 0) {
            n2 = -1;
        }
        arrstring = pattern.split((CharSequence)arrstring, n2);
        Intrinsics.checkExpressionValueIsNotNull(arrstring, "regex.split(this, if (limit == 0) -1 else limit)");
        return ArraysKt.asList(arrstring);
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, Pattern pattern, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return StringsKt.split(charSequence, pattern, n);
        n = 0;
        return StringsKt.split(charSequence, pattern, n);
    }

    public static final boolean startsWith(String string2, String string3, int n, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(string3, "prefix");
        if (bl) return StringsKt.regionMatches(string2, n, string3, 0, string3.length(), bl);
        return string2.startsWith(string3, n);
    }

    public static final boolean startsWith(String string2, String string3, boolean bl) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(string3, "prefix");
        if (bl) return StringsKt.regionMatches(string2, 0, string3, 0, string3.length(), bl);
        return string2.startsWith(string3);
    }

    public static /* synthetic */ boolean startsWith$default(String string2, String string3, int n, boolean bl, int n2, Object object) {
        if ((n2 & 4) == 0) return StringsKt.startsWith(string2, string3, n, bl);
        bl = false;
        return StringsKt.startsWith(string2, string3, n, bl);
    }

    public static /* synthetic */ boolean startsWith$default(String string2, String string3, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return StringsKt.startsWith(string2, string3, bl);
        bl = false;
        return StringsKt.startsWith(string2, string3, bl);
    }

    private static final String substring(String string2, int n) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        return string2;
    }

    private static final String substring(String string2, int n, int n2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.substring(n, n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    private static final byte[] toByteArray(String arrby, Charset charset) {
        if (arrby == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        arrby = arrby.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        return arrby;
    }

    static /* synthetic */ byte[] toByteArray$default(String arrby, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if (arrby == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        arrby = arrby.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        return arrby;
    }

    private static final char[] toCharArray(String arrc) {
        if (arrc == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        arrc = arrc.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(arrc, "(this as java.lang.String).toCharArray()");
        return arrc;
    }

    public static final char[] toCharArray(String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toCharArray");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(n, n2, string2.length());
        char[] arrc = new char[n2 - n];
        string2.getChars(n, n2, arrc, 0);
        return arrc;
    }

    private static final char[] toCharArray(String string2, char[] arrc, int n, int n2, int n3) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2.getChars(n2, n3, arrc, n);
        return arrc;
    }

    public static /* synthetic */ char[] toCharArray$default(String string2, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return StringsKt.toCharArray(string2, n, n2);
        n2 = string2.length();
        return StringsKt.toCharArray(string2, n, n2);
    }

    static /* synthetic */ char[] toCharArray$default(String string2, char[] arrc, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n = 0;
        }
        if ((n4 & 4) != 0) {
            n2 = 0;
        }
        if ((n4 & 8) != 0) {
            n3 = string2.length();
        }
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2.getChars(n2, n3, arrc, n);
        return arrc;
    }

    private static final String toLowerCase(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toLowerCase()");
        return string2;
    }

    private static final String toLowerCase(String string2, Locale locale) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.toLowerCase(locale);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toLowerCase(locale)");
        return string2;
    }

    private static final Pattern toPattern(String object, int n) {
        object = Pattern.compile((String)object, n);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.regex.Pattern.compile(this, flags)");
        return object;
    }

    static /* synthetic */ Pattern toPattern$default(String object, int n, int n2, Object object2) {
        if ((n2 & 1) != 0) {
            n = 0;
        }
        object = Pattern.compile((String)object, n);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.regex.Pattern.compile(this, flags)");
        return object;
    }

    private static final String toUpperCase(String string2) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toUpperCase()");
        return string2;
    }

    private static final String toUpperCase(String string2, Locale locale) {
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.toUpperCase(locale);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toUpperCase(locale)");
        return string2;
    }
}

