/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

public final class BoundType
extends Enum<BoundType> {
    private static final /* synthetic */ BoundType[] $VALUES;
    public static final /* enum */ BoundType CLOSED;
    public static final /* enum */ BoundType OPEN;
    final boolean inclusive;

    static {
        BoundType boundType;
        OPEN = new BoundType(false);
        CLOSED = boundType = new BoundType(true);
        $VALUES = new BoundType[]{OPEN, boundType};
    }

    private BoundType(boolean bl) {
        this.inclusive = bl;
    }

    static BoundType forBoolean(boolean bl) {
        if (!bl) return OPEN;
        return CLOSED;
    }

    public static BoundType valueOf(String string2) {
        return Enum.valueOf(BoundType.class, string2);
    }

    public static BoundType[] values() {
        return (BoundType[])$VALUES.clone();
    }

    BoundType flip() {
        return BoundType.forBoolean(this.inclusive ^ true);
    }
}

