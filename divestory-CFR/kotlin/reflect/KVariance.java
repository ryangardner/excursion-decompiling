/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.reflect;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/reflect/KVariance;", "", "(Ljava/lang/String;I)V", "INVARIANT", "IN", "OUT", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class KVariance
extends Enum<KVariance> {
    private static final /* synthetic */ KVariance[] $VALUES;
    public static final /* enum */ KVariance IN;
    public static final /* enum */ KVariance INVARIANT;
    public static final /* enum */ KVariance OUT;

    static {
        KVariance kVariance;
        KVariance kVariance2;
        KVariance kVariance3;
        INVARIANT = kVariance2 = new KVariance();
        IN = kVariance3 = new KVariance();
        OUT = kVariance = new KVariance();
        $VALUES = new KVariance[]{kVariance2, kVariance3, kVariance};
    }

    public static KVariance valueOf(String string2) {
        return Enum.valueOf(KVariance.class, string2);
    }

    public static KVariance[] values() {
        return (KVariance[])$VALUES.clone();
    }
}

