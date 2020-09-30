package kotlin.text;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001d"},
   d2 = {"Lkotlin/text/MatcherMatchResult;", "Lkotlin/text/MatchResult;", "matcher", "Ljava/util/regex/Matcher;", "input", "", "(Ljava/util/regex/Matcher;Ljava/lang/CharSequence;)V", "groupValues", "", "", "getGroupValues", "()Ljava/util/List;", "groupValues_", "groups", "Lkotlin/text/MatchGroupCollection;", "getGroups", "()Lkotlin/text/MatchGroupCollection;", "matchResult", "Ljava/util/regex/MatchResult;", "getMatchResult", "()Ljava/util/regex/MatchResult;", "range", "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", "value", "getValue", "()Ljava/lang/String;", "next", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
final class MatcherMatchResult implements MatchResult {
   private List<String> groupValues_;
   private final MatchGroupCollection groups;
   private final CharSequence input;
   private final Matcher matcher;

   public MatcherMatchResult(Matcher var1, CharSequence var2) {
      Intrinsics.checkParameterIsNotNull(var1, "matcher");
      Intrinsics.checkParameterIsNotNull(var2, "input");
      super();
      this.matcher = var1;
      this.input = var2;
      this.groups = (MatchGroupCollection)(new MatchNamedGroupCollection() {
         public MatchGroup get(int var1) {
            IntRange var2 = RegexKt.access$range(MatcherMatchResult.this.getMatchResult(), var1);
            MatchGroup var4;
            if (var2.getStart() >= 0) {
               String var3 = MatcherMatchResult.this.getMatchResult().group(var1);
               Intrinsics.checkExpressionValueIsNotNull(var3, "matchResult.group(index)");
               var4 = new MatchGroup(var3, var2);
            } else {
               var4 = null;
            }

            return var4;
         }

         public MatchGroup get(String var1) {
            Intrinsics.checkParameterIsNotNull(var1, "name");
            return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.this.getMatchResult(), var1);
         }

         public int getSize() {
            return MatcherMatchResult.this.getMatchResult().groupCount() + 1;
         }

         public boolean isEmpty() {
            return false;
         }

         public Iterator<MatchGroup> iterator() {
            return SequencesKt.map(CollectionsKt.asSequence((Iterable)CollectionsKt.getIndices(this)), (Function1)(new Function1<Integer, MatchGroup>() {
               public final MatchGroup invoke(int var1) {
                  return get(var1);
               }
            })).iterator();
         }
      });
   }

   private final java.util.regex.MatchResult getMatchResult() {
      return (java.util.regex.MatchResult)this.matcher;
   }

   public MatchResult.Destructured getDestructured() {
      return MatchResult.DefaultImpls.getDestructured(this);
   }

   public List<String> getGroupValues() {
      if (this.groupValues_ == null) {
         this.groupValues_ = (List)(new AbstractList<String>() {
            public String get(int var1) {
               String var2 = MatcherMatchResult.this.getMatchResult().group(var1);
               if (var2 == null) {
                  var2 = "";
               }

               return var2;
            }

            public int getSize() {
               return MatcherMatchResult.this.getMatchResult().groupCount() + 1;
            }
         });
      }

      List var1 = this.groupValues_;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1;
   }

   public MatchGroupCollection getGroups() {
      return this.groups;
   }

   public IntRange getRange() {
      return RegexKt.access$range(this.getMatchResult());
   }

   public String getValue() {
      String var1 = this.getMatchResult().group();
      Intrinsics.checkExpressionValueIsNotNull(var1, "matchResult.group()");
      return var1;
   }

   public MatchResult next() {
      int var1 = this.getMatchResult().end();
      byte var2;
      if (this.getMatchResult().end() == this.getMatchResult().start()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      int var4 = var1 + var2;
      MatchResult var5;
      if (var4 <= this.input.length()) {
         Matcher var3 = this.matcher.pattern().matcher(this.input);
         Intrinsics.checkExpressionValueIsNotNull(var3, "matcher.pattern().matcher(input)");
         var5 = RegexKt.access$findNext(var3, var4, this.input);
      } else {
         var5 = null;
      }

      return var5;
   }
}
