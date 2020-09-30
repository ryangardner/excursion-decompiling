/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import org.apache.commons.net.telnet.TelnetOptionHandler;

public class WindowSizeOptionHandler
extends TelnetOptionHandler {
    protected static final int WINDOW_SIZE = 31;
    private int m_nHeight = 24;
    private int m_nWidth = 80;

    public WindowSizeOptionHandler(int n, int n2) {
        super(31, false, false, false, false);
        this.m_nWidth = n;
        this.m_nHeight = n2;
    }

    public WindowSizeOptionHandler(int n, int n2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super(31, bl, bl2, bl3, bl4);
        this.m_nWidth = n;
        this.m_nHeight = n2;
    }

    @Override
    public int[] startSubnegotiationLocal() {
        int n = this.m_nWidth;
        int n2 = this.m_nHeight;
        int n3 = n % 256 == 255 ? 6 : 5;
        int n4 = n3;
        if (this.m_nWidth / 256 == 255) {
            n4 = n3 + 1;
        }
        n3 = n4;
        if (this.m_nHeight % 256 == 255) {
            n3 = n4 + 1;
        }
        int n5 = n3;
        if (this.m_nHeight / 256 == 255) {
            n5 = n3 + 1;
        }
        int[] arrn = new int[n5];
        arrn[0] = 31;
        n3 = 24;
        n4 = 1;
        while (n4 < n5) {
            arrn[n4] = (255 << n3 & 65536 * n + n2) >>> n3;
            int n6 = n4;
            if (arrn[n4] == 255) {
                n6 = n4 + 1;
                arrn[n6] = 255;
            }
            n4 = n6 + 1;
            n3 -= 8;
        }
        return arrn;
    }
}

