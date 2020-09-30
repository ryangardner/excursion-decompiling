/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.thirdparty.publicsuffix;

public final class PublicSuffixType
extends Enum<PublicSuffixType> {
    private static final /* synthetic */ PublicSuffixType[] $VALUES;
    public static final /* enum */ PublicSuffixType PRIVATE;
    public static final /* enum */ PublicSuffixType REGISTRY;
    private final char innerNodeCode;
    private final char leafNodeCode;

    static {
        PublicSuffixType publicSuffixType;
        PRIVATE = new PublicSuffixType(':', ',');
        REGISTRY = publicSuffixType = new PublicSuffixType('!', '?');
        $VALUES = new PublicSuffixType[]{PRIVATE, publicSuffixType};
    }

    private PublicSuffixType(char c, char c2) {
        this.innerNodeCode = c;
        this.leafNodeCode = c2;
    }

    static PublicSuffixType fromCode(char c) {
        Object object = PublicSuffixType.values();
        int n = ((PublicSuffixType[])object).length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("No enum corresponding to given code: ");
                ((StringBuilder)object).append(c);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            PublicSuffixType publicSuffixType = object[n2];
            if (publicSuffixType.getInnerNodeCode() == c) return publicSuffixType;
            if (publicSuffixType.getLeafNodeCode() == c) {
                return publicSuffixType;
            }
            ++n2;
        } while (true);
    }

    static PublicSuffixType fromIsPrivate(boolean bl) {
        if (!bl) return REGISTRY;
        return PRIVATE;
    }

    public static PublicSuffixType valueOf(String string2) {
        return Enum.valueOf(PublicSuffixType.class, string2);
    }

    public static PublicSuffixType[] values() {
        return (PublicSuffixType[])$VALUES.clone();
    }

    char getInnerNodeCode() {
        return this.innerNodeCode;
    }

    char getLeafNodeCode() {
        return this.leafNodeCode;
    }
}

