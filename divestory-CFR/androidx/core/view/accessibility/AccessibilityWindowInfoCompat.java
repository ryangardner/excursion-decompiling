/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityWindowInfo
 */
package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    private AccessibilityWindowInfoCompat(Object object) {
        this.mInfo = object;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        if (Build.VERSION.SDK_INT < 21) return null;
        return AccessibilityWindowInfoCompat.wrapNonNullInstance((Object)AccessibilityWindowInfo.obtain());
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat2;
        int n = Build.VERSION.SDK_INT;
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat3 = accessibilityWindowInfoCompat2 = null;
        if (n < 21) return accessibilityWindowInfoCompat3;
        if (accessibilityWindowInfoCompat != null) return AccessibilityWindowInfoCompat.wrapNonNullInstance((Object)AccessibilityWindowInfo.obtain((AccessibilityWindowInfo)((AccessibilityWindowInfo)accessibilityWindowInfoCompat.mInfo)));
        return accessibilityWindowInfoCompat2;
    }

    private static String typeToString(int n) {
        if (n == 1) return "TYPE_APPLICATION";
        if (n == 2) return "TYPE_INPUT_METHOD";
        if (n == 3) return "TYPE_SYSTEM";
        if (n == 4) return "TYPE_ACCESSIBILITY_OVERLAY";
        return "<UNKNOWN>";
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object object) {
        if (object == null) return null;
        return new AccessibilityWindowInfoCompat(object);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof AccessibilityWindowInfoCompat)) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat)object;
        object = this.mInfo;
        if (object == null) {
            if (accessibilityWindowInfoCompat.mInfo == null) return true;
            return false;
        }
        if (object.equals(accessibilityWindowInfoCompat.mInfo)) return true;
        return false;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        if (Build.VERSION.SDK_INT < 24) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)((AccessibilityWindowInfo)this.mInfo).getAnchor());
    }

    public void getBoundsInScreen(Rect rect) {
        if (Build.VERSION.SDK_INT < 21) return;
        ((AccessibilityWindowInfo)this.mInfo).getBoundsInScreen(rect);
    }

    public AccessibilityWindowInfoCompat getChild(int n) {
        if (Build.VERSION.SDK_INT < 21) return null;
        return AccessibilityWindowInfoCompat.wrapNonNullInstance((Object)((AccessibilityWindowInfo)this.mInfo).getChild(n));
    }

    public int getChildCount() {
        if (Build.VERSION.SDK_INT < 21) return 0;
        return ((AccessibilityWindowInfo)this.mInfo).getChildCount();
    }

    public int getId() {
        if (Build.VERSION.SDK_INT < 21) return -1;
        return ((AccessibilityWindowInfo)this.mInfo).getId();
    }

    public int getLayer() {
        if (Build.VERSION.SDK_INT < 21) return -1;
        return ((AccessibilityWindowInfo)this.mInfo).getLayer();
    }

    public AccessibilityWindowInfoCompat getParent() {
        if (Build.VERSION.SDK_INT < 21) return null;
        return AccessibilityWindowInfoCompat.wrapNonNullInstance((Object)((AccessibilityWindowInfo)this.mInfo).getParent());
    }

    public AccessibilityNodeInfoCompat getRoot() {
        if (Build.VERSION.SDK_INT < 21) return null;
        return AccessibilityNodeInfoCompat.wrapNonNullInstance((Object)((AccessibilityWindowInfo)this.mInfo).getRoot());
    }

    public CharSequence getTitle() {
        if (Build.VERSION.SDK_INT < 24) return null;
        return ((AccessibilityWindowInfo)this.mInfo).getTitle();
    }

    public int getType() {
        if (Build.VERSION.SDK_INT < 21) return -1;
        return ((AccessibilityWindowInfo)this.mInfo).getType();
    }

    public int hashCode() {
        Object object = this.mInfo;
        if (object != null) return object.hashCode();
        return 0;
    }

    public boolean isAccessibilityFocused() {
        if (Build.VERSION.SDK_INT < 21) return true;
        return ((AccessibilityWindowInfo)this.mInfo).isAccessibilityFocused();
    }

    public boolean isActive() {
        if (Build.VERSION.SDK_INT < 21) return true;
        return ((AccessibilityWindowInfo)this.mInfo).isActive();
    }

    public boolean isFocused() {
        if (Build.VERSION.SDK_INT < 21) return true;
        return ((AccessibilityWindowInfo)this.mInfo).isFocused();
    }

    public void recycle() {
        if (Build.VERSION.SDK_INT < 21) return;
        ((AccessibilityWindowInfo)this.mInfo).recycle();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = new Rect();
        this.getBoundsInScreen((Rect)object);
        stringBuilder.append("AccessibilityWindowInfo[");
        stringBuilder.append("id=");
        stringBuilder.append(this.getId());
        stringBuilder.append(", type=");
        stringBuilder.append(AccessibilityWindowInfoCompat.typeToString(this.getType()));
        stringBuilder.append(", layer=");
        stringBuilder.append(this.getLayer());
        stringBuilder.append(", bounds=");
        stringBuilder.append(object);
        stringBuilder.append(", focused=");
        stringBuilder.append(this.isFocused());
        stringBuilder.append(", active=");
        stringBuilder.append(this.isActive());
        stringBuilder.append(", hasParent=");
        object = this.getParent();
        boolean bl = true;
        boolean bl2 = object != null;
        stringBuilder.append(bl2);
        stringBuilder.append(", hasChildren=");
        bl2 = this.getChildCount() > 0 ? bl : false;
        stringBuilder.append(bl2);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

