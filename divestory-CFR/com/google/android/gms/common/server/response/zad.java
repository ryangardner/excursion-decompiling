/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;

final class zad
implements FastParser.zaa<Boolean> {
    zad() {
    }

    @Override
    public final /* synthetic */ Object zaa(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return FastParser.zaa(fastParser, bufferedReader, false);
    }
}

