/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import java.util.TreeMap;
import javax.annotation.Nullable;

public final class ServerStatsFieldEnums {
    private static final int TOTALSIZE = ServerStatsFieldEnums.computeTotalSize();

    private ServerStatsFieldEnums() {
    }

    private static int computeTotalSize() {
        Size[] arrsize = Size.values();
        int n = arrsize.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            n3 = n3 + arrsize[n2].value() + 1;
            ++n2;
        }
        return n3;
    }

    public static int getTotalSize() {
        return TOTALSIZE;
    }

    public static final class Id
    extends Enum<Id> {
        private static final /* synthetic */ Id[] $VALUES;
        public static final /* enum */ Id SERVER_STATS_LB_LATENCY_ID;
        public static final /* enum */ Id SERVER_STATS_SERVICE_LATENCY_ID;
        public static final /* enum */ Id SERVER_STATS_TRACE_OPTION_ID;
        private static final TreeMap<Integer, Id> map;
        private final int value;

        static {
            int n = 0;
            SERVER_STATS_LB_LATENCY_ID = new Id(0);
            SERVER_STATS_SERVICE_LATENCY_ID = new Id(1);
            Id[] arrid = new Id(2);
            SERVER_STATS_TRACE_OPTION_ID = arrid;
            $VALUES = new Id[]{SERVER_STATS_LB_LATENCY_ID, SERVER_STATS_SERVICE_LATENCY_ID, arrid};
            map = new TreeMap();
            arrid = Id.values();
            int n2 = arrid.length;
            while (n < n2) {
                Id id2 = arrid[n];
                map.put(id2.value, id2);
                ++n;
            }
        }

        private Id(int n2) {
            this.value = n2;
        }

        @Nullable
        public static Id valueOf(int n) {
            return map.get(n);
        }

        public static Id valueOf(String string2) {
            return Enum.valueOf(Id.class, string2);
        }

        public static Id[] values() {
            return (Id[])$VALUES.clone();
        }

        public int value() {
            return this.value;
        }
    }

    public static final class Size
    extends Enum<Size> {
        private static final /* synthetic */ Size[] $VALUES;
        public static final /* enum */ Size SERVER_STATS_LB_LATENCY_SIZE;
        public static final /* enum */ Size SERVER_STATS_SERVICE_LATENCY_SIZE;
        public static final /* enum */ Size SERVER_STATS_TRACE_OPTION_SIZE;
        private final int value;

        static {
            Size size;
            SERVER_STATS_LB_LATENCY_SIZE = new Size(8);
            SERVER_STATS_SERVICE_LATENCY_SIZE = new Size(8);
            SERVER_STATS_TRACE_OPTION_SIZE = size = new Size(1);
            $VALUES = new Size[]{SERVER_STATS_LB_LATENCY_SIZE, SERVER_STATS_SERVICE_LATENCY_SIZE, size};
        }

        private Size(int n2) {
            this.value = n2;
        }

        public static Size valueOf(String string2) {
            return Enum.valueOf(Size.class, string2);
        }

        public static Size[] values() {
            return (Size[])$VALUES.clone();
        }

        public int value() {
            return this.value;
        }
    }

}

