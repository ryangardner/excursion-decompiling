package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
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
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 ,2\u00060\u0001j\u0002`\u0002:\u0002,-B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001d\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n¢\u0006\u0002\u0010\u000bB\u000f\b\u0001\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u0011\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0004J\"\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00170\"J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u0016\u0010$\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040&2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010'\u001a\u00020\u001bJ\u0006\u0010(\u001a\u00020\rJ\b\u0010)\u001a\u00020\u0004H\u0016J\b\u0010*\u001a\u00020+H\u0002R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006."},
   d2 = {"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchEntire", "matches", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Regex implements Serializable {
   public static final Regex.Companion Companion = new Regex.Companion((DefaultConstructorMarker)null);
   private Set<? extends RegexOption> _options;
   private final Pattern nativePattern;

   public Regex(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "pattern");
      Pattern var2 = Pattern.compile(var1);
      Intrinsics.checkExpressionValueIsNotNull(var2, "Pattern.compile(pattern)");
      this(var2);
   }

   public Regex(String var1, Set<? extends RegexOption> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "pattern");
      Intrinsics.checkParameterIsNotNull(var2, "options");
      Pattern var3 = Pattern.compile(var1, Companion.ensureUnicodeCase(RegexKt.access$toInt((Iterable)var2)));
      Intrinsics.checkExpressionValueIsNotNull(var3, "Pattern.compile(pattern,…odeCase(options.toInt()))");
      this(var3);
   }

   public Regex(String var1, RegexOption var2) {
      Intrinsics.checkParameterIsNotNull(var1, "pattern");
      Intrinsics.checkParameterIsNotNull(var2, "option");
      Pattern var3 = Pattern.compile(var1, Companion.ensureUnicodeCase(var2.getValue()));
      Intrinsics.checkExpressionValueIsNotNull(var3, "Pattern.compile(pattern,…nicodeCase(option.value))");
      this(var3);
   }

   public Regex(Pattern var1) {
      Intrinsics.checkParameterIsNotNull(var1, "nativePattern");
      super();
      this.nativePattern = var1;
   }

   // $FF: synthetic method
   public static MatchResult find$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.find(var1, var2);
   }

   // $FF: synthetic method
   public static Sequence findAll$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.findAll(var1, var2);
   }

   // $FF: synthetic method
   public static List split$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.split(var1, var2);
   }

   private final Object writeReplace() {
      String var1 = this.nativePattern.pattern();
      Intrinsics.checkExpressionValueIsNotNull(var1, "nativePattern.pattern()");
      return new Regex.Serialized(var1, this.nativePattern.flags());
   }

   public final boolean containsMatchIn(CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      return this.nativePattern.matcher(var1).find();
   }

   public final MatchResult find(CharSequence var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Matcher var3 = this.nativePattern.matcher(var1);
      Intrinsics.checkExpressionValueIsNotNull(var3, "nativePattern.matcher(input)");
      return RegexKt.access$findNext(var3, var2, var1);
   }

   public final Sequence<MatchResult> findAll(final CharSequence var1, final int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      return SequencesKt.generateSequence((Function0)(new Function0<MatchResult>() {
         public final MatchResult invoke() {
            return Regex.this.find(var1, var2);
         }
      }), (Function1)null.INSTANCE);
   }

   public final Set<RegexOption> getOptions() {
      // $FF: Couldn't be decompiled
   }

   public final String getPattern() {
      String var1 = this.nativePattern.pattern();
      Intrinsics.checkExpressionValueIsNotNull(var1, "nativePattern.pattern()");
      return var1;
   }

   public final MatchResult matchEntire(CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Matcher var2 = this.nativePattern.matcher(var1);
      Intrinsics.checkExpressionValueIsNotNull(var2, "nativePattern.matcher(input)");
      return RegexKt.access$matchEntire(var2, var1);
   }

   public final boolean matches(CharSequence var1) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      return this.nativePattern.matcher(var1).matches();
   }

   public final String replace(CharSequence var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      String var3 = this.nativePattern.matcher(var1).replaceAll(var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "nativePattern.matcher(in…).replaceAll(replacement)");
      return var3;
   }

   public final String replace(CharSequence var1, Function1<? super MatchResult, ? extends CharSequence> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Intrinsics.checkParameterIsNotNull(var2, "transform");
      int var3 = 0;
      MatchResult var4 = find$default(this, var1, 0, 2, (Object)null);
      if (var4 == null) {
         return var1.toString();
      } else {
         int var5 = var1.length();
         StringBuilder var6 = new StringBuilder(var5);

         int var7;
         MatchResult var8;
         do {
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var6.append(var1, var3, var4.getRange().getStart());
            var6.append((CharSequence)var2.invoke(var4));
            var7 = var4.getRange().getEndInclusive() + 1;
            var8 = var4.next();
            if (var7 >= var5) {
               break;
            }

            var3 = var7;
            var4 = var8;
         } while(var8 != null);

         if (var7 < var5) {
            var6.append(var1, var7, var5);
         }

         String var9 = var6.toString();
         Intrinsics.checkExpressionValueIsNotNull(var9, "sb.toString()");
         return var9;
      }
   }

   public final String replaceFirst(CharSequence var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Intrinsics.checkParameterIsNotNull(var2, "replacement");
      String var3 = this.nativePattern.matcher(var1).replaceFirst(var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "nativePattern.matcher(in…replaceFirst(replacement)");
      return var3;
   }

   public final List<String> split(CharSequence var1, int var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      byte var3 = 0;
      boolean var4;
      if (var2 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4) {
         StringBuilder var8 = new StringBuilder();
         var8.append("Limit must be non-negative, but was ");
         var8.append(var2);
         var8.append('.');
         throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
      } else {
         Matcher var5 = this.nativePattern.matcher(var1);
         if (var5.find() && var2 != 1) {
            int var9 = 10;
            if (var2 > 0) {
               var9 = RangesKt.coerceAtMost(var2, 10);
            }

            ArrayList var6 = new ArrayList(var9);
            int var7 = var2 - 1;
            var2 = var3;

            do {
               var6.add(var1.subSequence(var2, var5.start()).toString());
               var9 = var5.end();
               if (var7 >= 0 && var6.size() == var7) {
                  break;
               }

               var2 = var9;
            } while(var5.find());

            var6.add(var1.subSequence(var9, var1.length()).toString());
            return (List)var6;
         } else {
            return CollectionsKt.listOf(var1.toString());
         }
      }
   }

   public final Pattern toPattern() {
      return this.nativePattern;
   }

   public String toString() {
      String var1 = this.nativePattern.toString();
      Intrinsics.checkExpressionValueIsNotNull(var1, "nativePattern.toString()");
      return var1;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007¨\u0006\f"},
      d2 = {"Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final int ensureUnicodeCase(int var1) {
         int var2 = var1;
         if ((var1 & 2) != 0) {
            var2 = var1 | 64;
         }

         return var2;
      }

      public final String escape(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "literal");
         var1 = Pattern.quote(var1);
         Intrinsics.checkExpressionValueIsNotNull(var1, "Pattern.quote(literal)");
         return var1;
      }

      public final String escapeReplacement(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "literal");
         var1 = Matcher.quoteReplacement(var1);
         Intrinsics.checkExpressionValueIsNotNull(var1, "Matcher.quoteReplacement(literal)");
         return var1;
      }

      public final Regex fromLiteral(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "literal");
         return new Regex(var1, RegexOption.LITERAL);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000e2\u00060\u0001j\u0002`\u0002:\u0001\u000eB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"},
      d2 = {"Lkotlin/text/Regex$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "flags", "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getPattern", "()Ljava/lang/String;", "readResolve", "", "Companion", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Serialized implements Serializable {
      public static final Regex.Serialized.Companion Companion = new Regex.Serialized.Companion((DefaultConstructorMarker)null);
      private static final long serialVersionUID = 0L;
      private final int flags;
      private final String pattern;

      public Serialized(String var1, int var2) {
         Intrinsics.checkParameterIsNotNull(var1, "pattern");
         super();
         this.pattern = var1;
         this.flags = var2;
      }

      private final Object readResolve() {
         Pattern var1 = Pattern.compile(this.pattern, this.flags);
         Intrinsics.checkExpressionValueIsNotNull(var1, "Pattern.compile(pattern, flags)");
         return new Regex(var1);
      }

      public final int getFlags() {
         return this.flags;
      }

      public final String getPattern() {
         return this.pattern;
      }

      @Metadata(
         bv = {1, 0, 3},
         d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
         d2 = {"Lkotlin/text/Regex$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"},
         k = 1,
         mv = {1, 1, 16}
      )
      public static final class Companion {
         private Companion() {
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }
      }
   }
}
