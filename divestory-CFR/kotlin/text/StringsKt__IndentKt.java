/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.StringsKt__IndentKt$getIndentFunction
 *  kotlin.text.StringsKt__IndentKt$prependIndent
 */
package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__AppendableKt;
import kotlin.text.StringsKt__IndentKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002\u00a2\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b\u00a2\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u00a8\u0006\u0015"}, d2={"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__IndentKt
extends StringsKt__AppendableKt {
    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String object) {
        boolean bl = ((CharSequence)object).length() == 0;
        if (!bl) return new Function1<String, String>((String)object){
            final /* synthetic */ String $indent;
            {
                this.$indent = string2;
                super(1);
            }

            public final String invoke(String string2) {
                Intrinsics.checkParameterIsNotNull(string2, "line");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.$indent);
                stringBuilder.append(string2);
                return stringBuilder.toString();
            }
        };
        return getIndentFunction.1.INSTANCE;
    }

    private static final int indentWidth$StringsKt__IndentKt(String string2) {
        int n;
        int n2;
        block2 : {
            CharSequence charSequence = string2;
            n = charSequence.length();
            for (n2 = 0; n2 < n; ++n2) {
                if (!(CharsKt.isWhitespace(charSequence.charAt(n2)) ^ true)) {
                    continue;
                }
                break block2;
            }
            n2 = -1;
        }
        n = n2;
        if (n2 != -1) return n;
        return string2.length();
    }

    public static final String prependIndent(String string2, String string3) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$prependIndent");
        Intrinsics.checkParameterIsNotNull(string3, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence(string2), (Function1)new Function1<String, String>(string3){
            final /* synthetic */ String $indent;
            {
                this.$indent = string2;
                super(1);
            }

            public final String invoke(String string2) {
                CharSequence charSequence;
                Intrinsics.checkParameterIsNotNull(string2, "it");
                if (StringsKt.isBlank(string2)) {
                    charSequence = string2;
                    if (string2.length() >= this.$indent.length()) return charSequence;
                    return this.$indent;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.$indent);
                ((StringBuilder)charSequence).append(string2);
                return ((StringBuilder)charSequence).toString();
            }
        }), "\n", null, null, 0, null, null, 62, null);
    }

    public static /* synthetic */ String prependIndent$default(String string2, String string3, int n, Object object) {
        if ((n & 1) == 0) return StringsKt.prependIndent(string2, string3);
        string3 = "    ";
        return StringsKt.prependIndent(string2, string3);
    }

    private static final String reindent$StringsKt__IndentKt(List<String> object, int n, Function1<? super String, String> function1, Function1<? super String, String> function12) {
        int n2 = CollectionsKt.getLastIndex(object);
        object = (Iterable)object;
        Collection collection = new ArrayList();
        Iterator<String> iterator2 = object.iterator();
        int n3 = 0;
        do {
            if (!iterator2.hasNext()) {
                object = ((StringBuilder)CollectionsKt.joinTo$default((List)collection, new StringBuilder(n), "\n", null, null, 0, null, null, 124, null)).toString();
                Intrinsics.checkExpressionValueIsNotNull(object, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
                return object;
            }
            object = iterator2.next();
            if (n3 < 0) {
                if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) throw (Throwable)new ArithmeticException("Index overflow has happened.");
                CollectionsKt.throwIndexOverflow();
            }
            String string2 = (String)object;
            if ((n3 == 0 || n3 == n2) && StringsKt.isBlank(string2)) {
                object = null;
            } else {
                String string3 = function12.invoke(string2);
                object = string2;
                if (string3 != null) {
                    string3 = function1.invoke(string3);
                    object = string2;
                    if (string3 != null) {
                        object = string3;
                    }
                }
            }
            if (object != null) {
                collection.add(object);
            }
            ++n3;
        } while (true);
    }

    public static final String replaceIndent(String object, String string2) {
        Object object2;
        Intrinsics.checkParameterIsNotNull(object, "$this$replaceIndent");
        Intrinsics.checkParameterIsNotNull(string2, "newIndent");
        Collection<String> collection = StringsKt.lines((CharSequence)object);
        Object object3 = collection;
        Iterator iterator2 = new ArrayList();
        Iterator iterator3 = object3.iterator();
        while (iterator3.hasNext()) {
            object2 = iterator3.next();
            if (!(StringsKt.isBlank((String)object2) ^ true)) continue;
            iterator2.add(object2);
        }
        iterator2 = (List)((Object)iterator2);
        object2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterator2, 10));
        iterator2 = iterator2.iterator();
        while (iterator2.hasNext()) {
            object2.add(StringsKt__IndentKt.indentWidth$StringsKt__IndentKt((String)iterator2.next()));
        }
        object2 = (Integer)CollectionsKt.min((List)object2);
        int n = 0;
        int n2 = object2 != null ? (Integer)object2 : 0;
        int n3 = ((String)object).length();
        int n4 = string2.length();
        int n5 = collection.size();
        object2 = StringsKt__IndentKt.getIndentFunction$StringsKt__IndentKt(string2);
        int n6 = CollectionsKt.getLastIndex(collection);
        collection = new ArrayList();
        iterator2 = object3.iterator();
        do {
            if (!iterator2.hasNext()) {
                object = ((StringBuilder)CollectionsKt.joinTo$default(collection, new StringBuilder(n3 + n4 * n5), "\n", null, null, 0, null, null, 124, null)).toString();
                Intrinsics.checkExpressionValueIsNotNull(object, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
                return object;
            }
            object = iterator2.next();
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            string2 = object;
            if ((n == 0 || n == n6) && StringsKt.isBlank(string2)) {
                object = null;
            } else {
                object3 = StringsKt.drop(string2, n2);
                object = string2;
                if (object3 != null) {
                    object3 = (String)object2.invoke(object3);
                    object = string2;
                    if (object3 != null) {
                        object = object3;
                    }
                }
            }
            if (object != null) {
                collection.add((String)object);
            }
            ++n;
        } while (true);
    }

    public static /* synthetic */ String replaceIndent$default(String string2, String string3, int n, Object object) {
        if ((n & 1) == 0) return StringsKt.replaceIndent(string2, string3);
        string3 = "";
        return StringsKt.replaceIndent(string2, string3);
    }

    public static final String replaceIndentByMargin(String object, String string2, String string3) {
        Intrinsics.checkParameterIsNotNull(object, "$this$replaceIndentByMargin");
        Intrinsics.checkParameterIsNotNull(string2, "newIndent");
        Intrinsics.checkParameterIsNotNull(string3, "marginPrefix");
        if (!(StringsKt.isBlank(string3) ^ true)) throw (Throwable)new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        Object object2 = StringsKt.lines((CharSequence)object);
        int n = ((String)object).length();
        int n2 = string2.length();
        int n3 = object2.size();
        Function1<String, String> function1 = StringsKt__IndentKt.getIndentFunction$StringsKt__IndentKt(string2);
        int n4 = CollectionsKt.getLastIndex(object2);
        object = (Iterable)object2;
        Collection collection = new ArrayList();
        Iterator iterator2 = object.iterator();
        int n5 = 0;
        do {
            if (!iterator2.hasNext()) {
                object = ((StringBuilder)CollectionsKt.joinTo$default((List)collection, new StringBuilder(n + n2 * n3), "\n", null, null, 0, null, null, 124, null)).toString();
                Intrinsics.checkExpressionValueIsNotNull(object, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
                return object;
            }
            object = iterator2.next();
            if (n5 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            object2 = (String)object;
            string2 = null;
            if ((n5 == 0 || n5 == n4) && StringsKt.isBlank((CharSequence)object2)) {
                object = null;
            } else {
                int n6;
                int n7;
                block11 : {
                    object = (CharSequence)object2;
                    n6 = object.length();
                    for (n7 = 0; n7 < n6; ++n7) {
                        if (!(CharsKt.isWhitespace(object.charAt(n7)) ^ true)) {
                            continue;
                        }
                        break block11;
                    }
                    n7 = -1;
                }
                if (n7 != -1 && StringsKt.startsWith$default((String)object2, string3, n7, false, 4, null)) {
                    n6 = string3.length();
                    if (object2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    string2 = ((String)object2).substring(n7 + n6);
                    Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                }
                object = object2;
                if (string2 != null) {
                    string2 = function1.invoke(string2);
                    object = object2;
                    if (string2 != null) {
                        object = string2;
                    }
                }
            }
            if (object != null) {
                collection.add(object);
            }
            ++n5;
        } while (true);
    }

    public static /* synthetic */ String replaceIndentByMargin$default(String string2, String string3, String string4, int n, Object object) {
        if ((n & 1) != 0) {
            string3 = "";
        }
        if ((n & 2) == 0) return StringsKt.replaceIndentByMargin(string2, string3, string4);
        string4 = "|";
        return StringsKt.replaceIndentByMargin(string2, string3, string4);
    }

    public static final String trimIndent(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$trimIndent");
        return StringsKt.replaceIndent(string2, "");
    }

    public static final String trimMargin(String string2, String string3) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$trimMargin");
        Intrinsics.checkParameterIsNotNull(string3, "marginPrefix");
        return StringsKt.replaceIndentByMargin(string2, "", string3);
    }

    public static /* synthetic */ String trimMargin$default(String string2, String string3, int n, Object object) {
        if ((n & 1) == 0) return StringsKt.trimMargin(string2, string3);
        string3 = "|";
        return StringsKt.trimMargin(string2, string3);
    }
}

