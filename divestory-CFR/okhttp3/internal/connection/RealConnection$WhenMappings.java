/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.connection;

import java.net.Proxy;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
public final class RealConnection$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static /* synthetic */ {
        int[] arrn = new int[Proxy.Type.values().length];
        $EnumSwitchMapping$0 = arrn;
        arrn[Proxy.Type.DIRECT.ordinal()] = 1;
        RealConnection$WhenMappings.$EnumSwitchMapping$0[Proxy.Type.HTTP.ordinal()] = 2;
    }
}

