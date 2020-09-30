/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.Regex$findAll
 *  kotlin.text.Regex$fromInt$
 *  kotlin.text.Regex$fromInt$$inlined
 *  kotlin.text.Regex$fromInt$$inlined$apply
 *  kotlin.text.Regex$fromInt$$inlined$apply$lambda
 */
package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.Regex$fromInt$;
import kotlin.text.RegexKt;
import kotlin.text.RegexOption;

@Metadata(bv={1, 0, 3}, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 ,2\u00060\u0001j\u0002`\u0002:\u0002,-B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bB\u001d\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n\u00a2\u0006\u0002\u0010\u000bB\u000f\b\u0001\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u0011\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0004J\"\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00170\"J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u0016\u0010$\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040&2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010'\u001a\u00020\u001bJ\u0006\u0010(\u001a\u00020\rJ\b\u0010)\u001a\u00020\u0004H\u0016J\b\u0010*\u001a\u00020+H\u0002R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006."}, d2={"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchEntire", "matches", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class Regex
implements Serializable {
    public static final Companion Companion = new Companion(null);
    private Set<? extends RegexOption> _options;
    private final Pattern nativePattern;

    public Regex(String object) {
        Intrinsics.checkParameterIsNotNull(object, "pattern");
        object = Pattern.compile((String)object);
        Intrinsics.checkExpressionValueIsNotNull(object, "Pattern.compile(pattern)");
        this((Pattern)object);
    }

    public Regex(String object, Set<? extends RegexOption> set) {
        Intrinsics.checkParameterIsNotNull(object, "pattern");
        Intrinsics.checkParameterIsNotNull(set, "options");
        object = Pattern.compile((String)object, Regex.Companion.ensureUnicodeCase(RegexKt.access$toInt(set)));
        Intrinsics.checkExpressionValueIsNotNull(object, "Pattern.compile(pattern,\u2026odeCase(options.toInt()))");
        this((Pattern)object);
    }

    public Regex(String object, RegexOption regexOption) {
        Intrinsics.checkParameterIsNotNull(object, "pattern");
        Intrinsics.checkParameterIsNotNull(regexOption, "option");
        object = Pattern.compile((String)object, Regex.Companion.ensureUnicodeCase(regexOption.getValue()));
        Intrinsics.checkExpressionValueIsNotNull(object, "Pattern.compile(pattern,\u2026nicodeCase(option.value))");
        this((Pattern)object);
    }

    public Regex(Pattern pattern) {
        Intrinsics.checkParameterIsNotNull(pattern, "nativePattern");
        this.nativePattern = pattern;
    }

    public static /* synthetic */ MatchResult find$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return regex.find(charSequence, n);
        n = 0;
        return regex.find(charSequence, n);
    }

    public static /* synthetic */ Sequence findAll$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return regex.findAll(charSequence, n);
        n = 0;
        return regex.findAll(charSequence, n);
    }

    public static /* synthetic */ List split$default(Regex regex, CharSequence charSequence, int n, int n2, Object object) {
        if ((n2 & 2) == 0) return regex.split(charSequence, n);
        n = 0;
        return regex.split(charSequence, n);
    }

    private final Object writeReplace() {
        String string2 = this.nativePattern.pattern();
        Intrinsics.checkExpressionValueIsNotNull(string2, "nativePattern.pattern()");
        return new Serialized(string2, this.nativePattern.flags());
    }

    public final boolean containsMatchIn(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        return this.nativePattern.matcher(charSequence).find();
    }

    public final MatchResult find(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        Matcher matcher = this.nativePattern.matcher(charSequence);
        Intrinsics.checkExpressionValueIsNotNull(matcher, "nativePattern.matcher(input)");
        return RegexKt.access$findNext(matcher, n, charSequence);
    }

    public final Sequence<MatchResult> findAll(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        return SequencesKt.generateSequence((Function0)new Function0<MatchResult>(this, charSequence, n){
            final /* synthetic */ CharSequence $input;
            final /* synthetic */ int $startIndex;
            final /* synthetic */ Regex this$0;
            {
                this.this$0 = regex;
                this.$input = charSequence;
                this.$startIndex = n;
                super(0);
            }

            public final MatchResult invoke() {
                return this.this$0.find(this.$input, this.$startIndex);
            }
        }, (Function1)findAll.2.INSTANCE);
    }

    public final Set<RegexOption> getOptions() {
        Set<RegexOption> set = this._options;
        if (set != null) {
            return set;
        }
        int n = this.nativePattern.flags();
        set = EnumSet.allOf(RegexOption.class);
        CollectionsKt.retainAll((Iterable)set, (Function1)new Function1<T, java.lang.Boolean>(n){
            final /* synthetic */ int $value$inlined;
            {
                this.$value$inlined = n;
                super(1);
            }

            public final boolean invoke(T object) {
                int n = this.$value$inlined;
                if ((n & (object = (kotlin.text.FlagEnum)object).getMask()) != object.getValue()) return false;
                return true;
            }
        });
        set = Collections.unmodifiableSet(set);
        Intrinsics.checkExpressionValueIsNotNull(set, "Collections.unmodifiable\u2026mask == it.value }\n    })");
        this._options = set;
        return set;
    }

    public final String getPattern() {
        String string2 = this.nativePattern.pattern();
        Intrinsics.checkExpressionValueIsNotNull(string2, "nativePattern.pattern()");
        return string2;
    }

    public final MatchResult matchEntire(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        Matcher matcher = this.nativePattern.matcher(charSequence);
        Intrinsics.checkExpressionValueIsNotNull(matcher, "nativePattern.matcher(input)");
        return RegexKt.access$matchEntire(matcher, charSequence);
    }

    public final boolean matches(CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        return this.nativePattern.matcher(charSequence).matches();
    }

    public final String replace(CharSequence charSequence, String string2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        Intrinsics.checkParameterIsNotNull(string2, "replacement");
        charSequence = this.nativePattern.matcher(charSequence).replaceAll(string2);
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "nativePattern.matcher(in\u2026).replaceAll(replacement)");
        return charSequence;
    }

    public final String replace(CharSequence charSequence, Function1<? super MatchResult, ? extends CharSequence> function1) {
        MatchResult matchResult;
        int n;
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        int n2 = 0;
        MatchResult matchResult2 = Regex.find$default(this, charSequence, 0, 2, null);
        if (matchResult2 == null) return ((Object)charSequence).toString();
        int n3 = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n3);
        do {
            if (matchResult2 == null) {
                Intrinsics.throwNpe();
            }
            stringBuilder.append(charSequence, n2, ((Integer)matchResult2.getRange().getStart()).intValue());
            stringBuilder.append(function1.invoke(matchResult2));
            n = (Integer)matchResult2.getRange().getEndInclusive() + 1;
            matchResult = matchResult2.next();
            if (n >= n3) break;
            n2 = n;
            matchResult2 = matchResult;
        } while (matchResult != null);
        if (n < n3) {
            stringBuilder.append(charSequence, n, n3);
        }
        charSequence = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "sb.toString()");
        return charSequence;
    }

    public final String replaceFirst(CharSequence charSequence, String string2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        Intrinsics.checkParameterIsNotNull(string2, "replacement");
        charSequence = this.nativePattern.matcher(charSequence).replaceFirst(string2);
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "nativePattern.matcher(in\u2026replaceFirst(replacement)");
        return charSequence;
    }

    public final List<String> split(CharSequence charSequence, int n) {
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        int n2 = 0;
        int n3 = n >= 0 ? 1 : 0;
        if (n3 == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Limit must be non-negative, but was ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append('.');
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (!matcher.find()) return CollectionsKt.listOf(((Object)charSequence).toString());
        if (n == 1) {
            return CollectionsKt.listOf(((Object)charSequence).toString());
        }
        n3 = 10;
        if (n > 0) {
            n3 = RangesKt.coerceAtMost(n, 10);
        }
        ArrayList<String> arrayList = new ArrayList<String>(n3);
        int n4 = n - 1;
        n = n2;
        do {
            arrayList.add(((Object)charSequence.subSequence(n, matcher.start())).toString());
            n3 = matcher.end();
            if (n4 >= 0 && arrayList.size() == n4) break;
            n = n3;
        } while (matcher.find());
        arrayList.add(((Object)charSequence.subSequence(n3, charSequence.length())).toString());
        return arrayList;
    }

    public final Pattern toPattern() {
        return this.nativePattern;
    }

    public String toString() {
        String string2 = this.nativePattern.toString();
        Intrinsics.checkExpressionValueIsNotNull(string2, "nativePattern.toString()");
        return string2;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007\u00a8\u0006\f"}, d2={"Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final int ensureUnicodeCase(int n) {
            int n2 = n;
            if ((n & 2) == 0) return n2;
            return n | 64;
        }

        public final String escape(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "literal");
            string2 = Pattern.quote(string2);
            Intrinsics.checkExpressionValueIsNotNull(string2, "Pattern.quote(literal)");
            return string2;
        }

        public final String escapeReplacement(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "literal");
            string2 = Matcher.quoteReplacement(string2);
            Intrinsics.checkExpressionValueIsNotNull(string2, "Matcher.quoteReplacement(literal)");
            return string2;
        }

        public final Regex fromLiteral(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "literal");
            return new Regex(string2, RegexOption.LITERAL);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000e2\u00060\u0001j\u0002`\u0002:\u0001\u000eB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lkotlin/text/Regex$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "flags", "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getPattern", "()Ljava/lang/String;", "readResolve", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    private static final class Serialized
    implements Serializable {
        public static final Companion Companion = new Companion(null);
        private static final long serialVersionUID = 0L;
        private final int flags;
        private final String pattern;

        public Serialized(String string2, int n) {
            Intrinsics.checkParameterIsNotNull(string2, "pattern");
            this.pattern = string2;
            this.flags = n;
        }

        private final Object readResolve() {
            Pattern pattern = Pattern.compile(this.pattern, this.flags);
            Intrinsics.checkExpressionValueIsNotNull(pattern, "Pattern.compile(pattern, flags)");
            return new Regex(pattern);
        }

        public final int getFlags() {
            return this.flags;
        }

        public final String getPattern() {
            return this.pattern;
        }

        @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lkotlin/text/Regex$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

    }

}

