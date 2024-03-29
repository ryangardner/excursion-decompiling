/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package androidx.core.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;
    private final Object mProvider;

    public AccessibilityNodeProviderCompat() {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mProvider = new AccessibilityNodeProviderApi19(this);
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.mProvider = new AccessibilityNodeProviderApi16(this);
            return;
        }
        this.mProvider = null;
    }

    public AccessibilityNodeProviderCompat(Object object) {
        this.mProvider = object;
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int n) {
        return null;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String string2, int n) {
        return null;
    }

    public AccessibilityNodeInfoCompat findFocus(int n) {
        return null;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    public boolean performAction(int n, int n2, Bundle bundle) {
        return false;
    }

    static class AccessibilityNodeProviderApi16
    extends AccessibilityNodeProvider {
        final AccessibilityNodeProviderCompat mCompat;

        AccessibilityNodeProviderApi16(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            this.mCompat = accessibilityNodeProviderCompat;
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int n) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.mCompat.createAccessibilityNodeInfo(n);
            if (accessibilityNodeInfoCompat != null) return accessibilityNodeInfoCompat.unwrap();
            return null;
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String object, int n) {
            List<AccessibilityNodeInfoCompat> list = this.mCompat.findAccessibilityNodeInfosByText((String)object, n);
            if (list == null) {
                return null;
            }
            object = new ArrayList();
            int n2 = list.size();
            n = 0;
            while (n < n2) {
                object.add(list.get(n).unwrap());
                ++n;
            }
            return object;
        }

        public boolean performAction(int n, int n2, Bundle bundle) {
            return this.mCompat.performAction(n, n2, bundle);
        }
    }

    static class AccessibilityNodeProviderApi19
    extends AccessibilityNodeProviderApi16 {
        AccessibilityNodeProviderApi19(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            super(accessibilityNodeProviderCompat);
        }

        public AccessibilityNodeInfo findFocus(int n) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.mCompat.findFocus(n);
            if (accessibilityNodeInfoCompat != null) return accessibilityNodeInfoCompat.unwrap();
            return null;
        }
    }

}

