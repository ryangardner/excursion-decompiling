/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;

public interface RouteInfo {
    public int getHopCount();

    public HttpHost getHopTarget(int var1);

    public LayerType getLayerType();

    public InetAddress getLocalAddress();

    public HttpHost getProxyHost();

    public HttpHost getTargetHost();

    public TunnelType getTunnelType();

    public boolean isLayered();

    public boolean isSecure();

    public boolean isTunnelled();

    public static final class LayerType
    extends Enum<LayerType> {
        private static final /* synthetic */ LayerType[] $VALUES;
        public static final /* enum */ LayerType LAYERED;
        public static final /* enum */ LayerType PLAIN;

        static {
            LayerType layerType;
            PLAIN = new LayerType();
            LAYERED = layerType = new LayerType();
            $VALUES = new LayerType[]{PLAIN, layerType};
        }

        public static LayerType valueOf(String string2) {
            return Enum.valueOf(LayerType.class, string2);
        }

        public static final LayerType[] values() {
            return (LayerType[])$VALUES.clone();
        }
    }

    public static final class TunnelType
    extends Enum<TunnelType> {
        private static final /* synthetic */ TunnelType[] $VALUES;
        public static final /* enum */ TunnelType PLAIN;
        public static final /* enum */ TunnelType TUNNELLED;

        static {
            TunnelType tunnelType;
            PLAIN = new TunnelType();
            TUNNELLED = tunnelType = new TunnelType();
            $VALUES = new TunnelType[]{PLAIN, tunnelType};
        }

        public static TunnelType valueOf(String string2) {
            return Enum.valueOf(TunnelType.class, string2);
        }

        public static final TunnelType[] values() {
            return (TunnelType[])$VALUES.clone();
        }
    }

}

