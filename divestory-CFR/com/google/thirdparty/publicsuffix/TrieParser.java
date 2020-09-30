/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.thirdparty.publicsuffix;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import java.util.List;

final class TrieParser {
    private static final Joiner PREFIX_JOINER = Joiner.on("");

    TrieParser() {
    }

    private static int doParseTrieToBuilder(List<CharSequence> list, CharSequence charSequence, int n, ImmutableMap.Builder<String, PublicSuffixType> builder) {
        int n2;
        block6 : {
            int n3;
            String string2;
            int n4 = charSequence.length();
            n2 = n;
            int n5 = 0;
            do {
                n3 = n5;
                if (n2 >= n4) break;
                n3 = n5 = charSequence.charAt(n2);
                if (n5 == 38) break;
                n3 = n5;
                if (n5 == 63) break;
                n3 = n5;
                if (n5 == 33) break;
                n3 = n5;
                if (n5 == 58) break;
                if (n5 == 44) {
                    n3 = n5;
                    break;
                }
                ++n2;
            } while (true);
            list.add(0, TrieParser.reverse(charSequence.subSequence(n, n2)));
            if ((n3 == 33 || n3 == 63 || n3 == 58 || n3 == 44) && (string2 = PREFIX_JOINER.join(list)).length() > 0) {
                builder.put(string2, PublicSuffixType.fromCode((char)n3));
            }
            n2 = n5 = n2 + 1;
            if (n3 != 63) {
                n2 = n5;
                if (n3 != 44) {
                    do {
                        n2 = n5;
                        if (n5 >= n4) break block6;
                        n2 = n5 + TrieParser.doParseTrieToBuilder(list, charSequence, n5, builder);
                        if (charSequence.charAt(n2) == '?') break;
                        n5 = n2;
                    } while (charSequence.charAt(n2) != ',');
                    ++n2;
                }
            }
        }
        list.remove(0);
        return n2 - n;
    }

    static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence charSequence) {
        ImmutableMap.Builder<String, PublicSuffixType> builder = ImmutableMap.builder();
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            n2 += TrieParser.doParseTrieToBuilder(Lists.<CharSequence>newLinkedList(), charSequence, n2, builder);
        }
        return builder.build();
    }

    private static CharSequence reverse(CharSequence charSequence) {
        return new StringBuilder(charSequence).reverse();
    }
}

