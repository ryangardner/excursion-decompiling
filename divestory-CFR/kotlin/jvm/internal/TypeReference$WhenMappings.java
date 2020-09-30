/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KVariance;

@Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
public final class TypeReference$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static /* synthetic */ {
        int[] arrn = new int[KVariance.values().length];
        $EnumSwitchMapping$0 = arrn;
        arrn[KVariance.INVARIANT.ordinal()] = 1;
        TypeReference$WhenMappings.$EnumSwitchMapping$0[KVariance.IN.ordinal()] = 2;
        TypeReference$WhenMappings.$EnumSwitchMapping$0[KVariance.OUT.ordinal()] = 3;
    }
}

