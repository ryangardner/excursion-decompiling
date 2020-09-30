/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

public final class SymbolShapeHint
extends Enum<SymbolShapeHint> {
    private static final /* synthetic */ SymbolShapeHint[] $VALUES;
    public static final /* enum */ SymbolShapeHint FORCE_NONE;
    public static final /* enum */ SymbolShapeHint FORCE_RECTANGLE;
    public static final /* enum */ SymbolShapeHint FORCE_SQUARE;

    static {
        SymbolShapeHint symbolShapeHint;
        FORCE_NONE = new SymbolShapeHint();
        FORCE_SQUARE = new SymbolShapeHint();
        FORCE_RECTANGLE = symbolShapeHint = new SymbolShapeHint();
        $VALUES = new SymbolShapeHint[]{FORCE_NONE, FORCE_SQUARE, symbolShapeHint};
    }

    public static SymbolShapeHint valueOf(String string2) {
        return Enum.valueOf(SymbolShapeHint.class, string2);
    }

    public static SymbolShapeHint[] values() {
        return (SymbolShapeHint[])$VALUES.clone();
    }
}

