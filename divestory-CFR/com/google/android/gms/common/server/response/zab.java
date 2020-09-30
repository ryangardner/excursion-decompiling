/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;

final class zab
implements FastParser.zaa<Float> {
    zab() {
    }

    @Override
    public final /* synthetic */ Object zaa(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return Float.valueOf(FastParser.zac(fastParser, bufferedReader));
    }
}

