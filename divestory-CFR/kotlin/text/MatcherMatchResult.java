/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.text.MatcherMatchResult$groupValues
 *  kotlin.text.MatcherMatchResult$groups
 */
package kotlin.text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchNamedGroupCollection;
import kotlin.text.MatchResult;
import kotlin.text.MatcherMatchResult;
import kotlin.text.RegexKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001d"}, d2={"Lkotlin/text/MatcherMatchResult;", "Lkotlin/text/MatchResult;", "matcher", "Ljava/util/regex/Matcher;", "input", "", "(Ljava/util/regex/Matcher;Ljava/lang/CharSequence;)V", "groupValues", "", "", "getGroupValues", "()Ljava/util/List;", "groupValues_", "groups", "Lkotlin/text/MatchGroupCollection;", "getGroups", "()Lkotlin/text/MatchGroupCollection;", "matchResult", "Ljava/util/regex/MatchResult;", "getMatchResult", "()Ljava/util/regex/MatchResult;", "range", "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", "value", "getValue", "()Ljava/lang/String;", "next", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
final class MatcherMatchResult
implements MatchResult {
    private List<String> groupValues_;
    private final MatchGroupCollection groups;
    private final CharSequence input;
    private final Matcher matcher;

    public MatcherMatchResult(Matcher matcher, CharSequence charSequence) {
        Intrinsics.checkParameterIsNotNull(matcher, "matcher");
        Intrinsics.checkParameterIsNotNull(charSequence, "input");
        this.matcher = matcher;
        this.input = charSequence;
        this.groups = new MatchNamedGroupCollection(this){
            final /* synthetic */ MatcherMatchResult this$0;
            {
                this.this$0 = matcherMatchResult;
            }

            public kotlin.text.MatchGroup get(int n) {
                IntRange intRange = RegexKt.access$range(MatcherMatchResult.access$getMatchResult$p(this.this$0), n);
                if ((java.lang.Integer)intRange.getStart() < 0) return null;
                String string2 = MatcherMatchResult.access$getMatchResult$p(this.this$0).group(n);
                Intrinsics.checkExpressionValueIsNotNull(string2, "matchResult.group(index)");
                return new kotlin.text.MatchGroup(string2, intRange);
            }

            public kotlin.text.MatchGroup get(String string2) {
                Intrinsics.checkParameterIsNotNull(string2, "name");
                return kotlin.internal.PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.access$getMatchResult$p(this.this$0), string2);
            }

            public int getSize() {
                return MatcherMatchResult.access$getMatchResult$p(this.this$0).groupCount() + 1;
            }

            public boolean isEmpty() {
                return false;
            }

            public java.util.Iterator<kotlin.text.MatchGroup> iterator() {
                return kotlin.sequences.SequencesKt.map(kotlin.collections.CollectionsKt.asSequence(kotlin.collections.CollectionsKt.getIndices(this)), (kotlin.jvm.functions.Function1)new kotlin.jvm.functions.Function1<java.lang.Integer, kotlin.text.MatchGroup>(this){
                    final /* synthetic */ groups.1 this$0;
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }

                    public final kotlin.text.MatchGroup invoke(int n) {
                        return this.this$0.get(n);
                    }
                }).iterator();
            }
        };
    }

    public static final /* synthetic */ java.util.regex.MatchResult access$getMatchResult$p(MatcherMatchResult matcherMatchResult) {
        return matcherMatchResult.getMatchResult();
    }

    private final java.util.regex.MatchResult getMatchResult() {
        return this.matcher;
    }

    @Override
    public MatchResult.Destructured getDestructured() {
        return MatchResult.DefaultImpls.getDestructured(this);
    }

    @Override
    public List<String> getGroupValues() {
        List<String> list;
        if (this.groupValues_ == null) {
            this.groupValues_ = new AbstractList<String>(this){
                final /* synthetic */ MatcherMatchResult this$0;
                {
                    this.this$0 = matcherMatchResult;
                }

                public String get(int n) {
                    String string2 = MatcherMatchResult.access$getMatchResult$p(this.this$0).group(n);
                    if (string2 == null) return "";
                    return string2;
                }

                public int getSize() {
                    return MatcherMatchResult.access$getMatchResult$p(this.this$0).groupCount() + 1;
                }
            };
        }
        if ((list = this.groupValues_) != null) return list;
        Intrinsics.throwNpe();
        return list;
    }

    @Override
    public MatchGroupCollection getGroups() {
        return this.groups;
    }

    @Override
    public IntRange getRange() {
        return RegexKt.access$range(this.getMatchResult());
    }

    @Override
    public String getValue() {
        String string2 = this.getMatchResult().group();
        Intrinsics.checkExpressionValueIsNotNull(string2, "matchResult.group()");
        return string2;
    }

    @Override
    public MatchResult next() {
        int n = this.getMatchResult().end();
        int n2 = this.getMatchResult().end() == this.getMatchResult().start() ? 1 : 0;
        n2 = n + n2;
        if (n2 > this.input.length()) return null;
        Matcher matcher = this.matcher.pattern().matcher(this.input);
        Intrinsics.checkExpressionValueIsNotNull(matcher, "matcher.pattern().matcher(input)");
        return RegexKt.access$findNext(matcher, n2, this.input);
    }
}

