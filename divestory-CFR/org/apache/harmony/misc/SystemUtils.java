/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.misc;

public class SystemUtils {
    public static final int ARC_IA32 = 1;
    public static final int ARC_IA64 = 2;
    public static final int ARC_UNKNOWN = -1;
    public static final int OS_LINUX = 2;
    public static final int OS_UNKNOWN = -1;
    public static final int OS_WINDOWS = 1;
    private static int arc;
    private static int os;

    public static int getOS() {
        if (os != 0) return os;
        String string2 = System.getProperty("os.name").substring(0, 3);
        if (string2.compareToIgnoreCase("win") == 0) {
            os = 1;
            return os;
        }
        if (string2.compareToIgnoreCase("lin") == 0) {
            os = 2;
            return os;
        }
        os = -1;
        return os;
    }
}

