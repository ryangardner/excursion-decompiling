/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.ServerStats;
import io.opencensus.common.ServerStatsDeserializationException;
import io.opencensus.common.ServerStatsFieldEnums;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ServerStatsEncoding {
    public static final byte CURRENT_VERSION = 0;

    private ServerStatsEncoding() {
    }

    public static ServerStats parseBytes(byte[] object) throws ServerStatsDeserializationException {
        long l;
        int n;
        long l2;
        ByteBuffer byteBuffer = ByteBuffer.wrap(object);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        if (!byteBuffer.hasRemaining()) throw new ServerStatsDeserializationException("Serialized ServerStats buffer is empty");
        int n2 = byteBuffer.get();
        if (n2 <= 0 && n2 >= 0) {
            l2 = 0L;
            l = 0L;
            n = n2 = 0;
        } else {
            object = new StringBuilder();
            object.append("Invalid ServerStats version: ");
            object.append(n2);
            throw new ServerStatsDeserializationException(object.toString());
        }
        while (byteBuffer.hasRemaining()) {
            object = ServerStatsFieldEnums.Id.valueOf(byteBuffer.get() & 255);
            if (object == null) {
                byteBuffer.position(byteBuffer.limit());
                continue;
            }
            n2 = 1.$SwitchMap$io$opencensus$common$ServerStatsFieldEnums$Id[object.ordinal()];
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) continue;
                    n = n2 = (int)byteBuffer.get();
                    continue;
                }
                l = byteBuffer.getLong();
                continue;
            }
            l2 = byteBuffer.getLong();
        }
        try {
            return ServerStats.create(l2, l, (byte)n);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = new StringBuilder();
            object.append("Serialized ServiceStats contains invalid values: ");
            object.append(illegalArgumentException.getMessage());
            throw new ServerStatsDeserializationException(object.toString());
        }
    }

    public static byte[] toBytes(ServerStats serverStats) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(ServerStatsFieldEnums.getTotalSize() + 1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte)0);
        byteBuffer.put((byte)ServerStatsFieldEnums.Id.SERVER_STATS_LB_LATENCY_ID.value());
        byteBuffer.putLong(serverStats.getLbLatencyNs());
        byteBuffer.put((byte)ServerStatsFieldEnums.Id.SERVER_STATS_SERVICE_LATENCY_ID.value());
        byteBuffer.putLong(serverStats.getServiceLatencyNs());
        byteBuffer.put((byte)ServerStatsFieldEnums.Id.SERVER_STATS_TRACE_OPTION_ID.value());
        byteBuffer.put(serverStats.getTraceOption());
        return byteBuffer.array();
    }

}

