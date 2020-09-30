/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.crest.divestory.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {
    private static final int COUNT = 25;
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        int n = 1;
        while (n <= 25) {
            DummyContent.addItem(DummyContent.createDummyItem(n));
            ++n;
        }
    }

    private static void addItem(DummyItem dummyItem) {
        ITEMS.add(dummyItem);
        ITEM_MAP.put(dummyItem.id, dummyItem);
    }

    private static DummyItem createDummyItem(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Item ");
        stringBuilder.append(n);
        return new DummyItem(String.valueOf(n), stringBuilder.toString(), DummyContent.makeDetails(n));
    }

    private static String makeDetails(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Details about Item: ");
        stringBuilder.append(n);
        int n2 = 0;
        while (n2 < n) {
            stringBuilder.append("\nMore details information here.");
            ++n2;
        }
        return stringBuilder.toString();
    }

    public static class DummyItem {
        public final String content;
        public final String details;
        public final String id;

        public DummyItem(String string2, String string3, String string4) {
            this.id = string2;
            this.content = string3;
            this.details = string4;
        }

        public String toString() {
            return this.content;
        }
    }

}

