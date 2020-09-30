/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.net;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import java.util.AbstractCollection;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class InternetDomainName {
    private static final CharMatcher DASH_MATCHER;
    private static final CharMatcher DIGIT_MATCHER;
    private static final CharMatcher DOTS_MATCHER;
    private static final Joiner DOT_JOINER;
    private static final Splitter DOT_SPLITTER;
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
        CharMatcher charMatcher;
        DOTS_MATCHER = CharMatcher.anyOf(".\u3002\uff0e\uff61");
        DOT_SPLITTER = Splitter.on('.');
        DOT_JOINER = Joiner.on('.');
        DASH_MATCHER = CharMatcher.anyOf("-_");
        DIGIT_MATCHER = CharMatcher.inRange('0', '9');
        LETTER_MATCHER = charMatcher = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
        PART_CHAR_MATCHER = DIGIT_MATCHER.or(charMatcher).or(DASH_MATCHER);
    }

    InternetDomainName(String string2) {
        Object object = Ascii.toLowerCase(DOTS_MATCHER.replaceFrom((CharSequence)string2, '.'));
        boolean bl = ((String)object).endsWith(".");
        boolean bl2 = true;
        string2 = object;
        if (bl) {
            string2 = ((String)object).substring(0, ((String)object).length() - 1);
        }
        bl = string2.length() <= 253;
        Preconditions.checkArgument(bl, "Domain name too long: '%s':", (Object)string2);
        this.name = string2;
        this.parts = object = ImmutableList.copyOf(DOT_SPLITTER.split(string2));
        bl = ((AbstractCollection)object).size() <= 127 ? bl2 : false;
        Preconditions.checkArgument(bl, "Domain has too many parts: '%s'", (Object)string2);
        Preconditions.checkArgument(InternetDomainName.validateSyntax(this.parts), "Not a valid domain name: '%s'", (Object)string2);
        this.publicSuffixIndex = this.findSuffixOfType(Optional.<PublicSuffixType>absent());
        this.registrySuffixIndex = this.findSuffixOfType(Optional.of(PublicSuffixType.REGISTRY));
    }

    private InternetDomainName ancestor(int n) {
        Joiner joiner = DOT_JOINER;
        ImmutableList<String> immutableList = this.parts;
        return InternetDomainName.from(joiner.join(immutableList.subList(n, immutableList.size())));
    }

    private int findSuffixOfType(Optional<PublicSuffixType> optional) {
        int n = this.parts.size();
        int n2 = 0;
        while (n2 < n) {
            String string2 = DOT_JOINER.join(this.parts.subList(n2, n));
            if (InternetDomainName.matchesType(optional, Optional.fromNullable(PublicSuffixPatterns.EXACT.get(string2)))) {
                return n2;
            }
            if (PublicSuffixPatterns.EXCLUDED.containsKey(string2)) {
                return n2 + 1;
            }
            if (InternetDomainName.matchesWildcardSuffixType(optional, string2)) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static InternetDomainName from(String string2) {
        return new InternetDomainName(Preconditions.checkNotNull(string2));
    }

    public static boolean isValid(String string2) {
        try {
            InternetDomainName.from(string2);
            return true;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    private static boolean matchesType(Optional<PublicSuffixType> optional, Optional<PublicSuffixType> optional2) {
        if (!optional.isPresent()) return optional2.isPresent();
        return optional.equals(optional2);
    }

    private static boolean matchesWildcardSuffixType(Optional<PublicSuffixType> optional, String object) {
        object = DOT_SPLITTER.limit(2).splitToList((CharSequence)object);
        int n = object.size();
        boolean bl = true;
        if (n != 2) return false;
        if (!InternetDomainName.matchesType(optional, Optional.fromNullable(PublicSuffixPatterns.UNDER.get(object.get(1))))) return false;
        return bl;
    }

    private static boolean validatePart(String string2, boolean bl) {
        if (string2.length() < 1) return false;
        if (string2.length() > 63) {
            return false;
        }
        String string3 = CharMatcher.ascii().retainFrom(string2);
        if (!PART_CHAR_MATCHER.matchesAllOf(string3)) {
            return false;
        }
        if (DASH_MATCHER.matches(string2.charAt(0))) return false;
        if (DASH_MATCHER.matches(string2.charAt(string2.length() - 1))) {
            return false;
        }
        if (!bl) return true;
        if (!DIGIT_MATCHER.matches(string2.charAt(0))) return true;
        return false;
    }

    private static boolean validateSyntax(List<String> list) {
        int n = list.size() - 1;
        if (!InternetDomainName.validatePart(list.get(n), true)) {
            return false;
        }
        int n2 = 0;
        while (n2 < n) {
            if (!InternetDomainName.validatePart(list.get(n2), false)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public InternetDomainName child(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Preconditions.checkNotNull(string2));
        stringBuilder.append(".");
        stringBuilder.append(this.name);
        return InternetDomainName.from(stringBuilder.toString());
    }

    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof InternetDomainName)) return false;
        object = (InternetDomainName)object;
        return this.name.equals(((InternetDomainName)object).name);
    }

    public boolean hasParent() {
        int n = this.parts.size();
        boolean bl = true;
        if (n <= 1) return false;
        return bl;
    }

    public boolean hasPublicSuffix() {
        if (this.publicSuffixIndex == -1) return false;
        return true;
    }

    public boolean hasRegistrySuffix() {
        if (this.registrySuffixIndex == -1) return false;
        return true;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean isPublicSuffix() {
        if (this.publicSuffixIndex != 0) return false;
        return true;
    }

    public boolean isRegistrySuffix() {
        if (this.registrySuffixIndex != 0) return false;
        return true;
    }

    public boolean isTopDomainUnderRegistrySuffix() {
        int n = this.registrySuffixIndex;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public boolean isTopPrivateDomain() {
        int n = this.publicSuffixIndex;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public boolean isUnderPublicSuffix() {
        if (this.publicSuffixIndex <= 0) return false;
        return true;
    }

    public boolean isUnderRegistrySuffix() {
        if (this.registrySuffixIndex <= 0) return false;
        return true;
    }

    public InternetDomainName parent() {
        Preconditions.checkState(this.hasParent(), "Domain '%s' has no parent", (Object)this.name);
        return this.ancestor(1);
    }

    public ImmutableList<String> parts() {
        return this.parts;
    }

    public InternetDomainName publicSuffix() {
        if (!this.hasPublicSuffix()) return null;
        return this.ancestor(this.publicSuffixIndex);
    }

    public InternetDomainName registrySuffix() {
        if (!this.hasRegistrySuffix()) return null;
        return this.ancestor(this.registrySuffixIndex);
    }

    public String toString() {
        return this.name;
    }

    public InternetDomainName topDomainUnderRegistrySuffix() {
        if (this.isTopDomainUnderRegistrySuffix()) {
            return this;
        }
        Preconditions.checkState(this.isUnderRegistrySuffix(), "Not under a registry suffix: %s", (Object)this.name);
        return this.ancestor(this.registrySuffixIndex - 1);
    }

    public InternetDomainName topPrivateDomain() {
        if (this.isTopPrivateDomain()) {
            return this;
        }
        Preconditions.checkState(this.isUnderPublicSuffix(), "Not under a public suffix: %s", (Object)this.name);
        return this.ancestor(this.publicSuffixIndex - 1);
    }
}

