/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class TestUtils {
    private static final String UTF_8 = "UTF-8";

    private TestUtils() {
    }

    public static Map<String, String> parseQuery(String object) throws IOException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        object = Splitter.on('&').split((CharSequence)object).iterator();
        while (object.hasNext()) {
            ArrayList<String> arrayList = (String)object.next();
            arrayList = Lists.newArrayList(Splitter.on('=').split((CharSequence)((Object)arrayList)));
            if (arrayList.size() != 2) throw new IOException("Invalid Query String");
            hashMap.put(URLDecoder.decode((String)arrayList.get(0), UTF_8), URLDecoder.decode((String)arrayList.get(1), UTF_8));
        }
        return hashMap;
    }
}

