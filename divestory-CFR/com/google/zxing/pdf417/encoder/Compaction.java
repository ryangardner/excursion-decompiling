/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.encoder;

public final class Compaction
extends Enum<Compaction> {
    private static final /* synthetic */ Compaction[] $VALUES;
    public static final /* enum */ Compaction AUTO;
    public static final /* enum */ Compaction BYTE;
    public static final /* enum */ Compaction NUMERIC;
    public static final /* enum */ Compaction TEXT;

    static {
        Compaction compaction;
        AUTO = new Compaction();
        TEXT = new Compaction();
        BYTE = new Compaction();
        NUMERIC = compaction = new Compaction();
        $VALUES = new Compaction[]{AUTO, TEXT, BYTE, compaction};
    }

    public static Compaction valueOf(String string2) {
        return Enum.valueOf(Compaction.class, string2);
    }

    public static Compaction[] values() {
        return (Compaction[])$VALUES.clone();
    }
}

