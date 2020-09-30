/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accessibilityservice.AccessibilityServiceInfo
 *  android.content.pm.PackageManager
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;

    private AccessibilityServiceInfoCompat() {
    }

    public static String capabilityToString(int n) {
        if (n == 1) return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
        if (n == 2) return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
        if (n == 4) return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
        if (n == 8) return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
        return "UNKNOWN";
    }

    public static String feedbackTypeToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        do {
            if (n <= 0) {
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
            int n2 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n2;
            if (stringBuilder.length() > 1) {
                stringBuilder.append(", ");
            }
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 4) {
                        if (n2 != 8) {
                            if (n2 != 16) continue;
                            stringBuilder.append("FEEDBACK_GENERIC");
                            continue;
                        }
                        stringBuilder.append("FEEDBACK_VISUAL");
                        continue;
                    }
                    stringBuilder.append("FEEDBACK_AUDIBLE");
                    continue;
                }
                stringBuilder.append("FEEDBACK_HAPTIC");
                continue;
            }
            stringBuilder.append("FEEDBACK_SPOKEN");
        } while (true);
    }

    public static String flagToString(int n) {
        if (n == 1) return "DEFAULT";
        if (n == 2) return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
        if (n == 4) return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
        if (n == 8) return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
        if (n == 16) return "FLAG_REPORT_VIEW_IDS";
        if (n == 32) return "FLAG_REQUEST_FILTER_KEY_EVENTS";
        return null;
    }

    public static int getCapabilities(AccessibilityServiceInfo accessibilityServiceInfo) {
        if (Build.VERSION.SDK_INT >= 18) {
            return accessibilityServiceInfo.getCapabilities();
        }
        if (!accessibilityServiceInfo.getCanRetrieveWindowContent()) return 0;
        return 1;
    }

    public static String loadDescription(AccessibilityServiceInfo accessibilityServiceInfo, PackageManager packageManager) {
        if (Build.VERSION.SDK_INT < 16) return accessibilityServiceInfo.getDescription();
        return accessibilityServiceInfo.loadDescription(packageManager);
    }
}

