/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/LazyThreadSafetyMode;", "", "(Ljava/lang/String;I)V", "SYNCHRONIZED", "PUBLICATION", "NONE", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class LazyThreadSafetyMode
extends Enum<LazyThreadSafetyMode> {
    private static final /* synthetic */ LazyThreadSafetyMode[] $VALUES;
    public static final /* enum */ LazyThreadSafetyMode NONE;
    public static final /* enum */ LazyThreadSafetyMode PUBLICATION;
    public static final /* enum */ LazyThreadSafetyMode SYNCHRONIZED;

    static {
        LazyThreadSafetyMode lazyThreadSafetyMode;
        LazyThreadSafetyMode lazyThreadSafetyMode2;
        LazyThreadSafetyMode lazyThreadSafetyMode3;
        SYNCHRONIZED = lazyThreadSafetyMode2 = new LazyThreadSafetyMode();
        PUBLICATION = lazyThreadSafetyMode3 = new LazyThreadSafetyMode();
        NONE = lazyThreadSafetyMode = new LazyThreadSafetyMode();
        $VALUES = new LazyThreadSafetyMode[]{lazyThreadSafetyMode2, lazyThreadSafetyMode3, lazyThreadSafetyMode};
    }

    public static LazyThreadSafetyMode valueOf(String string2) {
        return Enum.valueOf(LazyThreadSafetyMode.class, string2);
    }

    public static LazyThreadSafetyMode[] values() {
        return (LazyThreadSafetyMode[])$VALUES.clone();
    }
}

