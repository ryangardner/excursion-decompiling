/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.FormatException;
import java.util.HashMap;
import java.util.Map;

public final class CharacterSetECI
extends Enum<CharacterSetECI> {
    private static final /* synthetic */ CharacterSetECI[] $VALUES;
    public static final /* enum */ CharacterSetECI ASCII;
    public static final /* enum */ CharacterSetECI Big5;
    public static final /* enum */ CharacterSetECI Cp1250;
    public static final /* enum */ CharacterSetECI Cp1251;
    public static final /* enum */ CharacterSetECI Cp1252;
    public static final /* enum */ CharacterSetECI Cp1256;
    public static final /* enum */ CharacterSetECI Cp437;
    public static final /* enum */ CharacterSetECI EUC_KR;
    public static final /* enum */ CharacterSetECI GB18030;
    public static final /* enum */ CharacterSetECI ISO8859_1;
    public static final /* enum */ CharacterSetECI ISO8859_10;
    public static final /* enum */ CharacterSetECI ISO8859_11;
    public static final /* enum */ CharacterSetECI ISO8859_13;
    public static final /* enum */ CharacterSetECI ISO8859_14;
    public static final /* enum */ CharacterSetECI ISO8859_15;
    public static final /* enum */ CharacterSetECI ISO8859_16;
    public static final /* enum */ CharacterSetECI ISO8859_2;
    public static final /* enum */ CharacterSetECI ISO8859_3;
    public static final /* enum */ CharacterSetECI ISO8859_4;
    public static final /* enum */ CharacterSetECI ISO8859_5;
    public static final /* enum */ CharacterSetECI ISO8859_6;
    public static final /* enum */ CharacterSetECI ISO8859_7;
    public static final /* enum */ CharacterSetECI ISO8859_8;
    public static final /* enum */ CharacterSetECI ISO8859_9;
    private static final Map<String, CharacterSetECI> NAME_TO_ECI;
    public static final /* enum */ CharacterSetECI SJIS;
    public static final /* enum */ CharacterSetECI UTF8;
    public static final /* enum */ CharacterSetECI UnicodeBigUnmarked;
    private static final Map<Integer, CharacterSetECI> VALUE_TO_ECI;
    private final String[] otherEncodingNames;
    private final int[] values;

    static {
        CharacterSetECI characterSetECI;
        Cp437 = new CharacterSetECI(new int[]{0, 2}, new String[0]);
        ISO8859_1 = new CharacterSetECI(new int[]{1, 3}, "ISO-8859-1");
        ISO8859_2 = new CharacterSetECI(4, "ISO-8859-2");
        ISO8859_3 = new CharacterSetECI(5, "ISO-8859-3");
        ISO8859_4 = new CharacterSetECI(6, "ISO-8859-4");
        ISO8859_5 = new CharacterSetECI(7, "ISO-8859-5");
        ISO8859_6 = new CharacterSetECI(8, "ISO-8859-6");
        ISO8859_7 = new CharacterSetECI(9, "ISO-8859-7");
        ISO8859_8 = new CharacterSetECI(10, "ISO-8859-8");
        ISO8859_9 = new CharacterSetECI(11, "ISO-8859-9");
        ISO8859_10 = new CharacterSetECI(12, "ISO-8859-10");
        ISO8859_11 = new CharacterSetECI(13, "ISO-8859-11");
        ISO8859_13 = new CharacterSetECI(15, "ISO-8859-13");
        ISO8859_14 = new CharacterSetECI(16, "ISO-8859-14");
        ISO8859_15 = new CharacterSetECI(17, "ISO-8859-15");
        ISO8859_16 = new CharacterSetECI(18, "ISO-8859-16");
        SJIS = new CharacterSetECI(20, "Shift_JIS");
        Cp1250 = new CharacterSetECI(21, "windows-1250");
        Cp1251 = new CharacterSetECI(22, "windows-1251");
        Cp1252 = new CharacterSetECI(23, "windows-1252");
        Cp1256 = new CharacterSetECI(24, "windows-1256");
        UnicodeBigUnmarked = new CharacterSetECI(25, "UTF-16BE", "UnicodeBig");
        UTF8 = new CharacterSetECI(26, "UTF-8");
        ASCII = new CharacterSetECI(new int[]{27, 170}, "US-ASCII");
        Big5 = new CharacterSetECI(28);
        GB18030 = new CharacterSetECI(29, "GB2312", "EUC_CN", "GBK");
        EUC_KR = characterSetECI = new CharacterSetECI(30, "EUC-KR");
        $VALUES = new CharacterSetECI[]{Cp437, ISO8859_1, ISO8859_2, ISO8859_3, ISO8859_4, ISO8859_5, ISO8859_6, ISO8859_7, ISO8859_8, ISO8859_9, ISO8859_10, ISO8859_11, ISO8859_13, ISO8859_14, ISO8859_15, ISO8859_16, SJIS, Cp1250, Cp1251, Cp1252, Cp1256, UnicodeBigUnmarked, UTF8, ASCII, Big5, GB18030, characterSetECI};
        VALUE_TO_ECI = new HashMap<Integer, CharacterSetECI>();
        NAME_TO_ECI = new HashMap<String, CharacterSetECI>();
        CharacterSetECI[] arrcharacterSetECI = CharacterSetECI.values();
        int n = arrcharacterSetECI.length;
        int n2 = 0;
        while (n2 < n) {
            characterSetECI = arrcharacterSetECI[n2];
            for (int n3 : characterSetECI.values) {
                VALUE_TO_ECI.put(n3, characterSetECI);
            }
            NAME_TO_ECI.put(characterSetECI.name(), characterSetECI);
            for (String string2 : characterSetECI.otherEncodingNames) {
                NAME_TO_ECI.put(string2, characterSetECI);
            }
            ++n2;
        }
    }

    private CharacterSetECI(int n2) {
        this(new int[]{n2}, new String[0]);
    }

    private CharacterSetECI(int n2, String ... arrstring) {
        this.values = new int[]{n2};
        this.otherEncodingNames = arrstring;
    }

    private CharacterSetECI(int[] arrn, String ... arrstring) {
        this.values = arrn;
        this.otherEncodingNames = arrstring;
    }

    public static CharacterSetECI getCharacterSetECIByName(String string2) {
        return NAME_TO_ECI.get(string2);
    }

    public static CharacterSetECI getCharacterSetECIByValue(int n) throws FormatException {
        if (n < 0) throw FormatException.getFormatInstance();
        if (n >= 900) throw FormatException.getFormatInstance();
        return VALUE_TO_ECI.get(n);
    }

    public static CharacterSetECI valueOf(String string2) {
        return Enum.valueOf(CharacterSetECI.class, string2);
    }

    public static CharacterSetECI[] values() {
        return (CharacterSetECI[])$VALUES.clone();
    }

    public int getValue() {
        return this.values[0];
    }
}

