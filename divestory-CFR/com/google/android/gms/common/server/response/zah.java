/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

final class zah
implements FastParser.zaa<BigDecimal> {
    zah() {
    }

    @Override
    public final /* synthetic */ Object zaa(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return FastParser.zag(fastParser, bufferedReader);
    }
}

