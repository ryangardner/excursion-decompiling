package com.google.common.net;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class InternetDomainName {
   private static final CharMatcher DASH_MATCHER = CharMatcher.anyOf("-_");
   private static final CharMatcher DIGIT_MATCHER = CharMatcher.inRange('0', '9');
   private static final CharMatcher DOTS_MATCHER = CharMatcher.anyOf(".。．｡");
   private static final Joiner DOT_JOINER = Joiner.on('.');
   private static final Splitter DOT_SPLITTER = Splitter.on('.');
   private static final CharMatcher LETTER_MATCHER;
   private static final int MAX_DOMAIN_PART_LENGTH = 63;
   private static final int MAX_LENGTH = 253;
   private static final int MAX_PARTS = 127;
   private static final int NO_SUFFIX_FOUND = -1;
   private static final CharMatcher PART_CHAR_MATCHER;
   private final String name;
   private final ImmutableList<String> parts;
   private final int publicSuffixIndex;
   private final int registrySuffixIndex;

   static {
      CharMatcher var0 = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
      LETTER_MATCHER = var0;
      PART_CHAR_MATCHER = DIGIT_MATCHER.or(var0).or(DASH_MATCHER);
   }

   InternetDomainName(String var1) {
      String var2 = Ascii.toLowerCase(DOTS_MATCHER.replaceFrom(var1, '.'));
      boolean var3 = var2.endsWith(".");
      boolean var4 = true;
      var1 = var2;
      if (var3) {
         var1 = var2.substring(0, var2.length() - 1);
      }

      if (var1.length() <= 253) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Domain name too long: '%s':", (Object)var1);
      this.name = var1;
      ImmutableList var5 = ImmutableList.copyOf(DOT_SPLITTER.split(var1));
      this.parts = var5;
      if (var5.size() <= 127) {
         var3 = var4;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Domain has too many parts: '%s'", (Object)var1);
      Preconditions.checkArgument(validateSyntax(this.parts), "Not a valid domain name: '%s'", (Object)var1);
      this.publicSuffixIndex = this.findSuffixOfType(Optional.absent());
      this.registrySuffixIndex = this.findSuffixOfType(Optional.of(PublicSuffixType.REGISTRY));
   }

   private InternetDomainName ancestor(int var1) {
      Joiner var2 = DOT_JOINER;
      ImmutableList var3 = this.parts;
      return from(var2.join((Iterable)var3.subList(var1, var3.size())));
   }

   private int findSuffixOfType(Optional<PublicSuffixType> var1) {
      int var2 = this.parts.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = DOT_JOINER.join((Iterable)this.parts.subList(var3, var2));
         if (matchesType(var1, Optional.fromNullable(PublicSuffixPatterns.EXACT.get(var4)))) {
            return var3;
         }

         if (PublicSuffixPatterns.EXCLUDED.containsKey(var4)) {
            return var3 + 1;
         }

         if (matchesWildcardSuffixType(var1, var4)) {
            return var3;
         }
      }

      return -1;
   }

   public static InternetDomainName from(String var0) {
      return new InternetDomainName((String)Preconditions.checkNotNull(var0));
   }

   public static boolean isValid(String var0) {
      try {
         from(var0);
         return true;
      } catch (IllegalArgumentException var1) {
         return false;
      }
   }

   private static boolean matchesType(Optional<PublicSuffixType> var0, Optional<PublicSuffixType> var1) {
      boolean var2;
      if (var0.isPresent()) {
         var2 = var0.equals(var1);
      } else {
         var2 = var1.isPresent();
      }

      return var2;
   }

   private static boolean matchesWildcardSuffixType(Optional<PublicSuffixType> var0, String var1) {
      List var4 = DOT_SPLITTER.limit(2).splitToList(var1);
      int var2 = var4.size();
      boolean var3 = true;
      if (var2 != 2 || !matchesType(var0, Optional.fromNullable(PublicSuffixPatterns.UNDER.get(var4.get(1))))) {
         var3 = false;
      }

      return var3;
   }

   private static boolean validatePart(String var0, boolean var1) {
      if (var0.length() >= 1 && var0.length() <= 63) {
         String var2 = CharMatcher.ascii().retainFrom(var0);
         if (!PART_CHAR_MATCHER.matchesAllOf(var2)) {
            return false;
         }

         if (!DASH_MATCHER.matches(var0.charAt(0)) && !DASH_MATCHER.matches(var0.charAt(var0.length() - 1))) {
            if (var1 && DIGIT_MATCHER.matches(var0.charAt(0))) {
               return false;
            }

            return true;
         }
      }

      return false;
   }

   private static boolean validateSyntax(List<String> var0) {
      int var1 = var0.size() - 1;
      if (!validatePart((String)var0.get(var1), true)) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1; ++var2) {
            if (!validatePart((String)var0.get(var2), false)) {
               return false;
            }
         }

         return true;
      }
   }

   public InternetDomainName child(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append((String)Preconditions.checkNotNull(var1));
      var2.append(".");
      var2.append(this.name);
      return from(var2.toString());
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof InternetDomainName) {
         InternetDomainName var2 = (InternetDomainName)var1;
         return this.name.equals(var2.name);
      } else {
         return false;
      }
   }

   public boolean hasParent() {
      int var1 = this.parts.size();
      boolean var2 = true;
      if (var1 <= 1) {
         var2 = false;
      }

      return var2;
   }

   public boolean hasPublicSuffix() {
      boolean var1;
      if (this.publicSuffixIndex != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasRegistrySuffix() {
      boolean var1;
      if (this.registrySuffixIndex != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean isPublicSuffix() {
      boolean var1;
      if (this.publicSuffixIndex == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isRegistrySuffix() {
      boolean var1;
      if (this.registrySuffixIndex == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isTopDomainUnderRegistrySuffix() {
      int var1 = this.registrySuffixIndex;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public boolean isTopPrivateDomain() {
      int var1 = this.publicSuffixIndex;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public boolean isUnderPublicSuffix() {
      boolean var1;
      if (this.publicSuffixIndex > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isUnderRegistrySuffix() {
      boolean var1;
      if (this.registrySuffixIndex > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public InternetDomainName parent() {
      Preconditions.checkState(this.hasParent(), "Domain '%s' has no parent", (Object)this.name);
      return this.ancestor(1);
   }

   public ImmutableList<String> parts() {
      return this.parts;
   }

   public InternetDomainName publicSuffix() {
      InternetDomainName var1;
      if (this.hasPublicSuffix()) {
         var1 = this.ancestor(this.publicSuffixIndex);
      } else {
         var1 = null;
      }

      return var1;
   }

   public InternetDomainName registrySuffix() {
      InternetDomainName var1;
      if (this.hasRegistrySuffix()) {
         var1 = this.ancestor(this.registrySuffixIndex);
      } else {
         var1 = null;
      }

      return var1;
   }

   public String toString() {
      return this.name;
   }

   public InternetDomainName topDomainUnderRegistrySuffix() {
      if (this.isTopDomainUnderRegistrySuffix()) {
         return this;
      } else {
         Preconditions.checkState(this.isUnderRegistrySuffix(), "Not under a registry suffix: %s", (Object)this.name);
         return this.ancestor(this.registrySuffixIndex - 1);
      }
   }

   public InternetDomainName topPrivateDomain() {
      if (this.isTopPrivateDomain()) {
         return this;
      } else {
         Preconditions.checkState(this.isUnderPublicSuffix(), "Not under a public suffix: %s", (Object)this.name);
         return this.ancestor(this.publicSuffixIndex - 1);
      }
   }
}
