/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.graphics;

public final class BlendModeCompat
extends Enum<BlendModeCompat> {
    private static final /* synthetic */ BlendModeCompat[] $VALUES;
    public static final /* enum */ BlendModeCompat CLEAR;
    public static final /* enum */ BlendModeCompat COLOR;
    public static final /* enum */ BlendModeCompat COLOR_BURN;
    public static final /* enum */ BlendModeCompat COLOR_DODGE;
    public static final /* enum */ BlendModeCompat DARKEN;
    public static final /* enum */ BlendModeCompat DIFFERENCE;
    public static final /* enum */ BlendModeCompat DST;
    public static final /* enum */ BlendModeCompat DST_ATOP;
    public static final /* enum */ BlendModeCompat DST_IN;
    public static final /* enum */ BlendModeCompat DST_OUT;
    public static final /* enum */ BlendModeCompat DST_OVER;
    public static final /* enum */ BlendModeCompat EXCLUSION;
    public static final /* enum */ BlendModeCompat HARD_LIGHT;
    public static final /* enum */ BlendModeCompat HUE;
    public static final /* enum */ BlendModeCompat LIGHTEN;
    public static final /* enum */ BlendModeCompat LUMINOSITY;
    public static final /* enum */ BlendModeCompat MODULATE;
    public static final /* enum */ BlendModeCompat MULTIPLY;
    public static final /* enum */ BlendModeCompat OVERLAY;
    public static final /* enum */ BlendModeCompat PLUS;
    public static final /* enum */ BlendModeCompat SATURATION;
    public static final /* enum */ BlendModeCompat SCREEN;
    public static final /* enum */ BlendModeCompat SOFT_LIGHT;
    public static final /* enum */ BlendModeCompat SRC;
    public static final /* enum */ BlendModeCompat SRC_ATOP;
    public static final /* enum */ BlendModeCompat SRC_IN;
    public static final /* enum */ BlendModeCompat SRC_OUT;
    public static final /* enum */ BlendModeCompat SRC_OVER;
    public static final /* enum */ BlendModeCompat XOR;

    static {
        BlendModeCompat blendModeCompat;
        CLEAR = new BlendModeCompat();
        SRC = new BlendModeCompat();
        DST = new BlendModeCompat();
        SRC_OVER = new BlendModeCompat();
        DST_OVER = new BlendModeCompat();
        SRC_IN = new BlendModeCompat();
        DST_IN = new BlendModeCompat();
        SRC_OUT = new BlendModeCompat();
        DST_OUT = new BlendModeCompat();
        SRC_ATOP = new BlendModeCompat();
        DST_ATOP = new BlendModeCompat();
        XOR = new BlendModeCompat();
        PLUS = new BlendModeCompat();
        MODULATE = new BlendModeCompat();
        SCREEN = new BlendModeCompat();
        OVERLAY = new BlendModeCompat();
        DARKEN = new BlendModeCompat();
        LIGHTEN = new BlendModeCompat();
        COLOR_DODGE = new BlendModeCompat();
        COLOR_BURN = new BlendModeCompat();
        HARD_LIGHT = new BlendModeCompat();
        SOFT_LIGHT = new BlendModeCompat();
        DIFFERENCE = new BlendModeCompat();
        EXCLUSION = new BlendModeCompat();
        MULTIPLY = new BlendModeCompat();
        HUE = new BlendModeCompat();
        SATURATION = new BlendModeCompat();
        COLOR = new BlendModeCompat();
        LUMINOSITY = blendModeCompat = new BlendModeCompat();
        $VALUES = new BlendModeCompat[]{CLEAR, SRC, DST, SRC_OVER, DST_OVER, SRC_IN, DST_IN, SRC_OUT, DST_OUT, SRC_ATOP, DST_ATOP, XOR, PLUS, MODULATE, SCREEN, OVERLAY, DARKEN, LIGHTEN, COLOR_DODGE, COLOR_BURN, HARD_LIGHT, SOFT_LIGHT, DIFFERENCE, EXCLUSION, MULTIPLY, HUE, SATURATION, COLOR, blendModeCompat};
    }

    public static BlendModeCompat valueOf(String string2) {
        return Enum.valueOf(BlendModeCompat.class, string2);
    }

    public static BlendModeCompat[] values() {
        return (BlendModeCompat[])$VALUES.clone();
    }
}

