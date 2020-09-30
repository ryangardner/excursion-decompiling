/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityRecord
 */
package androidx.customview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityRecord;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewParentCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import androidx.customview.widget.FocusStrategy;
import java.util.ArrayList;
import java.util.List;

public abstract class ExploreByTouchHelper
extends AccessibilityDelegateCompat {
    private static final String DEFAULT_CLASS_NAME = "android.view.View";
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER = new FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat>(){

        @Override
        public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            accessibilityNodeInfoCompat.getBoundsInParent(rect);
        }
    };
    private static final FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER = new FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>(){

        @Override
        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat, int n) {
            return sparseArrayCompat.valueAt(n);
        }

        @Override
        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat) {
            return sparseArrayCompat.size();
        }
    };
    int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    private final View mHost;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private MyNodeProvider mNodeProvider;
    private final int[] mTempGlobalRect = new int[2];
    private final Rect mTempParentRect = new Rect();
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempVisibleRect = new Rect();

    public ExploreByTouchHelper(View view) {
        if (view == null) throw new IllegalArgumentException("View may not be null");
        this.mHost = view;
        this.mManager = (AccessibilityManager)view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(view) != 0) return;
        ViewCompat.setImportantForAccessibility(view, 1);
    }

    private boolean clearAccessibilityFocus(int n) {
        if (this.mAccessibilityFocusedVirtualViewId != n) return false;
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mHost.invalidate();
        this.sendEventForVirtualView(n, 65536);
        return true;
    }

    private boolean clickKeyboardFocusedVirtualView() {
        int n = this.mKeyboardFocusedVirtualViewId;
        if (n == Integer.MIN_VALUE) return false;
        if (!this.onPerformActionForVirtualView(n, 16, null)) return false;
        return true;
    }

    private AccessibilityEvent createEvent(int n, int n2) {
        if (n == -1) return this.createEventForHost(n2);
        return this.createEventForChild(n, n2);
    }

    private AccessibilityEvent createEventForChild(int n, int n2) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n2);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.obtainAccessibilityNodeInfo(n);
        accessibilityEvent.getText().add(accessibilityNodeInfoCompat.getText());
        accessibilityEvent.setContentDescription(accessibilityNodeInfoCompat.getContentDescription());
        accessibilityEvent.setScrollable(accessibilityNodeInfoCompat.isScrollable());
        accessibilityEvent.setPassword(accessibilityNodeInfoCompat.isPassword());
        accessibilityEvent.setEnabled(accessibilityNodeInfoCompat.isEnabled());
        accessibilityEvent.setChecked(accessibilityNodeInfoCompat.isChecked());
        this.onPopulateEventForVirtualView(n, accessibilityEvent);
        if (accessibilityEvent.getText().isEmpty()) {
            if (accessibilityEvent.getContentDescription() == null) throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEvent.setClassName(accessibilityNodeInfoCompat.getClassName());
        AccessibilityRecordCompat.setSource((AccessibilityRecord)accessibilityEvent, this.mHost, n);
        accessibilityEvent.setPackageName((CharSequence)this.mHost.getContext().getPackageName());
        return accessibilityEvent;
    }

    private AccessibilityEvent createEventForHost(int n) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n);
        this.mHost.onInitializeAccessibilityEvent(accessibilityEvent);
        return accessibilityEvent;
    }

    private AccessibilityNodeInfoCompat createNodeForChild(int n) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain();
        accessibilityNodeInfoCompat.setEnabled(true);
        accessibilityNodeInfoCompat.setFocusable(true);
        accessibilityNodeInfoCompat.setClassName(DEFAULT_CLASS_NAME);
        accessibilityNodeInfoCompat.setBoundsInParent(INVALID_PARENT_BOUNDS);
        accessibilityNodeInfoCompat.setBoundsInScreen(INVALID_PARENT_BOUNDS);
        accessibilityNodeInfoCompat.setParent(this.mHost);
        this.onPopulateNodeForVirtualView(n, accessibilityNodeInfoCompat);
        if (accessibilityNodeInfoCompat.getText() == null) {
            if (accessibilityNodeInfoCompat.getContentDescription() == null) throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompat.getBoundsInParent(this.mTempParentRect);
        if (this.mTempParentRect.equals((Object)INVALID_PARENT_BOUNDS)) throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        int n2 = accessibilityNodeInfoCompat.getActions();
        if ((n2 & 64) != 0) throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        if ((n2 & 128) != 0) throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        accessibilityNodeInfoCompat.setPackageName(this.mHost.getContext().getPackageName());
        accessibilityNodeInfoCompat.setSource(this.mHost, n);
        if (this.mAccessibilityFocusedVirtualViewId == n) {
            accessibilityNodeInfoCompat.setAccessibilityFocused(true);
            accessibilityNodeInfoCompat.addAction(128);
        } else {
            accessibilityNodeInfoCompat.setAccessibilityFocused(false);
            accessibilityNodeInfoCompat.addAction(64);
        }
        boolean bl = this.mKeyboardFocusedVirtualViewId == n;
        if (bl) {
            accessibilityNodeInfoCompat.addAction(2);
        } else if (accessibilityNodeInfoCompat.isFocusable()) {
            accessibilityNodeInfoCompat.addAction(1);
        }
        accessibilityNodeInfoCompat.setFocused(bl);
        this.mHost.getLocationOnScreen(this.mTempGlobalRect);
        accessibilityNodeInfoCompat.getBoundsInScreen(this.mTempScreenRect);
        if (this.mTempScreenRect.equals((Object)INVALID_PARENT_BOUNDS)) {
            accessibilityNodeInfoCompat.getBoundsInParent(this.mTempScreenRect);
            if (accessibilityNodeInfoCompat.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain();
                n = accessibilityNodeInfoCompat.mParentVirtualDescendantId;
                while (n != -1) {
                    accessibilityNodeInfoCompat2.setParent(this.mHost, -1);
                    accessibilityNodeInfoCompat2.setBoundsInParent(INVALID_PARENT_BOUNDS);
                    this.onPopulateNodeForVirtualView(n, accessibilityNodeInfoCompat2);
                    accessibilityNodeInfoCompat2.getBoundsInParent(this.mTempParentRect);
                    this.mTempScreenRect.offset(this.mTempParentRect.left, this.mTempParentRect.top);
                    n = accessibilityNodeInfoCompat2.mParentVirtualDescendantId;
                }
                accessibilityNodeInfoCompat2.recycle();
            }
            this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
        }
        if (!this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) return accessibilityNodeInfoCompat;
        this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
        if (!this.mTempScreenRect.intersect(this.mTempVisibleRect)) return accessibilityNodeInfoCompat;
        accessibilityNodeInfoCompat.setBoundsInScreen(this.mTempScreenRect);
        if (!this.isVisibleToUser(this.mTempScreenRect)) return accessibilityNodeInfoCompat;
        accessibilityNodeInfoCompat.setVisibleToUser(true);
        return accessibilityNodeInfoCompat;
    }

    private AccessibilityNodeInfoCompat createNodeForHost() {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(this.mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, accessibilityNodeInfoCompat);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        this.getVisibleVirtualViews(arrayList);
        if (accessibilityNodeInfoCompat.getChildCount() > 0) {
            if (arrayList.size() > 0) throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int n = 0;
        int n2 = arrayList.size();
        while (n < n2) {
            accessibilityNodeInfoCompat.addChild(this.mHost, arrayList.get(n));
            ++n;
        }
        return accessibilityNodeInfoCompat;
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> getAllNodes() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        this.getVisibleVirtualViews(arrayList);
        SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat = new SparseArrayCompat<AccessibilityNodeInfoCompat>();
        int n = 0;
        while (n < arrayList.size()) {
            sparseArrayCompat.put(n, this.createNodeForChild(n));
            ++n;
        }
        return sparseArrayCompat;
    }

    private void getBoundsInParent(int n, Rect rect) {
        this.obtainAccessibilityNodeInfo(n).getBoundsInParent(rect);
    }

    private static Rect guessPreviouslyFocusedRect(View view, int n, Rect rect) {
        int n2 = view.getWidth();
        int n3 = view.getHeight();
        if (n == 17) {
            rect.set(n2, 0, n2, n3);
            return rect;
        }
        if (n == 33) {
            rect.set(0, n3, n2, n3);
            return rect;
        }
        if (n != 66) {
            if (n != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            rect.set(0, -1, n2, -1);
            return rect;
        }
        rect.set(-1, 0, -1, n3);
        return rect;
    }

    private boolean isVisibleToUser(Rect rect) {
        boolean bl;
        boolean bl2 = bl = false;
        if (rect == null) return bl2;
        if (rect.isEmpty()) {
            return bl;
        }
        if (this.mHost.getWindowVisibility() != 0) {
            return false;
        }
        rect = this.mHost.getParent();
        do {
            if (!(rect instanceof View)) {
                bl2 = bl;
                if (rect == null) return bl2;
                return true;
            }
            if ((rect = (View)rect).getAlpha() <= 0.0f) return false;
            if (rect.getVisibility() != 0) {
                return false;
            }
            rect = rect.getParent();
        } while (true);
    }

    private static int keyToDirection(int n) {
        if (n == 19) return 33;
        if (n == 21) return 17;
        if (n == 22) return 66;
        return 130;
    }

    private boolean moveFocus(int n, Rect object) {
        SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat = this.getAllNodes();
        int n2 = this.mKeyboardFocusedVirtualViewId;
        int n3 = Integer.MIN_VALUE;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = n2 == Integer.MIN_VALUE ? null : sparseArrayCompat.get(n2);
        if (n != 1 && n != 2) {
            if (n != 17 && n != 33 && n != 66) {
                if (n != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            Rect rect = new Rect();
            n2 = this.mKeyboardFocusedVirtualViewId;
            if (n2 != Integer.MIN_VALUE) {
                this.getBoundsInParent(n2, rect);
            } else if (object != null) {
                rect.set(object);
            } else {
                ExploreByTouchHelper.guessPreviouslyFocusedRect(this.mHost, n, rect);
            }
            object = FocusStrategy.findNextFocusInAbsoluteDirection(sparseArrayCompat, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, accessibilityNodeInfoCompat, rect, n);
        } else {
            boolean bl = ViewCompat.getLayoutDirection(this.mHost) == 1;
            object = FocusStrategy.findNextFocusInRelativeDirection(sparseArrayCompat, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, accessibilityNodeInfoCompat, n, bl, false);
        }
        if (object == null) {
            n = n3;
            return this.requestKeyboardFocusForVirtualView(n);
        }
        n = sparseArrayCompat.keyAt(sparseArrayCompat.indexOfValue((AccessibilityNodeInfoCompat)object));
        return this.requestKeyboardFocusForVirtualView(n);
    }

    private boolean performActionForChild(int n, int n2, Bundle bundle) {
        if (n2 == 1) return this.requestKeyboardFocusForVirtualView(n);
        if (n2 == 2) return this.clearKeyboardFocusForVirtualView(n);
        if (n2 == 64) return this.requestAccessibilityFocus(n);
        if (n2 == 128) return this.clearAccessibilityFocus(n);
        return this.onPerformActionForVirtualView(n, n2, bundle);
    }

    private boolean performActionForHost(int n, Bundle bundle) {
        return ViewCompat.performAccessibilityAction(this.mHost, n, bundle);
    }

    private boolean requestAccessibilityFocus(int n) {
        if (!this.mManager.isEnabled()) return false;
        if (!this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        int n2 = this.mAccessibilityFocusedVirtualViewId;
        if (n2 == n) return false;
        if (n2 != Integer.MIN_VALUE) {
            this.clearAccessibilityFocus(n2);
        }
        this.mAccessibilityFocusedVirtualViewId = n;
        this.mHost.invalidate();
        this.sendEventForVirtualView(n, 32768);
        return true;
    }

    private void updateHoveredVirtualView(int n) {
        int n2 = this.mHoveredVirtualViewId;
        if (n2 == n) {
            return;
        }
        this.mHoveredVirtualViewId = n;
        this.sendEventForVirtualView(n, 128);
        this.sendEventForVirtualView(n2, 256);
    }

    public final boolean clearKeyboardFocusForVirtualView(int n) {
        if (this.mKeyboardFocusedVirtualViewId != n) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        this.onVirtualViewKeyboardFocusChanged(n, false);
        this.sendEventForVirtualView(n, 8);
        return true;
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean bl;
        boolean bl2 = this.mManager.isEnabled();
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        if (!this.mManager.isTouchExplorationEnabled()) {
            return bl;
        }
        int n = motionEvent.getAction();
        if (n != 7 && n != 9) {
            if (n != 10) {
                return false;
            }
            if (this.mHoveredVirtualViewId == Integer.MIN_VALUE) return false;
            this.updateHoveredVirtualView(Integer.MIN_VALUE);
            return true;
        }
        n = this.getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
        this.updateHoveredVirtualView(n);
        bl3 = bl;
        if (n == Integer.MIN_VALUE) return bl3;
        return true;
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int n = keyEvent.getAction();
        boolean bl = false;
        int n2 = 0;
        boolean bl2 = bl;
        if (n == 1) return bl2;
        n = keyEvent.getKeyCode();
        if (n != 61) {
            if (n != 66) {
                switch (n) {
                    default: {
                        return bl;
                    }
                    case 19: 
                    case 20: 
                    case 21: 
                    case 22: {
                        bl2 = bl;
                        if (!keyEvent.hasNoModifiers()) return bl2;
                        n = ExploreByTouchHelper.keyToDirection(n);
                        int n3 = keyEvent.getRepeatCount();
                        bl2 = false;
                        while (n2 < n3 + 1) {
                            if (!this.moveFocus(n, null)) return bl2;
                            ++n2;
                            bl2 = true;
                        }
                        return bl2;
                    }
                    case 23: 
                }
            }
            bl2 = bl;
            if (!keyEvent.hasNoModifiers()) return bl2;
            bl2 = bl;
            if (keyEvent.getRepeatCount() != 0) return bl2;
            this.clickKeyboardFocusedVirtualView();
            return true;
        }
        if (keyEvent.hasNoModifiers()) {
            return this.moveFocus(2, null);
        }
        bl2 = bl;
        if (!keyEvent.hasModifiers(1)) return bl2;
        return this.moveFocus(1, null);
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.mAccessibilityFocusedVirtualViewId;
    }

    @Override
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider != null) return this.mNodeProvider;
        this.mNodeProvider = new MyNodeProvider();
        return this.mNodeProvider;
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return this.getAccessibilityFocusedVirtualViewId();
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.mKeyboardFocusedVirtualViewId;
    }

    protected abstract int getVirtualViewAt(float var1, float var2);

    protected abstract void getVisibleVirtualViews(List<Integer> var1);

    public final void invalidateRoot() {
        this.invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int n) {
        this.invalidateVirtualView(n, 0);
    }

    public final void invalidateVirtualView(int n, int n2) {
        if (n == Integer.MIN_VALUE) return;
        if (!this.mManager.isEnabled()) return;
        ViewParent viewParent = this.mHost.getParent();
        if (viewParent == null) return;
        AccessibilityEvent accessibilityEvent = this.createEvent(n, 2048);
        AccessibilityEventCompat.setContentChangeTypes(accessibilityEvent, n2);
        ViewParentCompat.requestSendAccessibilityEvent(viewParent, this.mHost, accessibilityEvent);
    }

    AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int n) {
        if (n != -1) return this.createNodeForChild(n);
        return this.createNodeForHost();
    }

    public final void onFocusChanged(boolean bl, int n, Rect rect) {
        int n2 = this.mKeyboardFocusedVirtualViewId;
        if (n2 != Integer.MIN_VALUE) {
            this.clearKeyboardFocusForVirtualView(n2);
        }
        if (!bl) return;
        this.moveFocus(n, rect);
    }

    @Override
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        this.onPopulateEventForHost(accessibilityEvent);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        this.onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    protected abstract boolean onPerformActionForVirtualView(int var1, int var2, Bundle var3);

    protected void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateEventForVirtualView(int n, AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    protected abstract void onPopulateNodeForVirtualView(int var1, AccessibilityNodeInfoCompat var2);

    protected void onVirtualViewKeyboardFocusChanged(int n, boolean bl) {
    }

    boolean performAction(int n, int n2, Bundle bundle) {
        if (n == -1) return this.performActionForHost(n2, bundle);
        return this.performActionForChild(n, n2, bundle);
    }

    public final boolean requestKeyboardFocusForVirtualView(int n) {
        if (!this.mHost.isFocused() && !this.mHost.requestFocus()) {
            return false;
        }
        int n2 = this.mKeyboardFocusedVirtualViewId;
        if (n2 == n) {
            return false;
        }
        if (n2 != Integer.MIN_VALUE) {
            this.clearKeyboardFocusForVirtualView(n2);
        }
        this.mKeyboardFocusedVirtualViewId = n;
        this.onVirtualViewKeyboardFocusChanged(n, true);
        this.sendEventForVirtualView(n, 8);
        return true;
    }

    public final boolean sendEventForVirtualView(int n, int n2) {
        if (n == Integer.MIN_VALUE) return false;
        if (!this.mManager.isEnabled()) {
            return false;
        }
        ViewParent viewParent = this.mHost.getParent();
        if (viewParent == null) {
            return false;
        }
        AccessibilityEvent accessibilityEvent = this.createEvent(n, n2);
        return ViewParentCompat.requestSendAccessibilityEvent(viewParent, this.mHost, accessibilityEvent);
    }

    private class MyNodeProvider
    extends AccessibilityNodeProviderCompat {
        MyNodeProvider() {
        }

        @Override
        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int n) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(n));
        }

        @Override
        public AccessibilityNodeInfoCompat findFocus(int n) {
            n = n == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId;
            if (n != Integer.MIN_VALUE) return this.createAccessibilityNodeInfo(n);
            return null;
        }

        @Override
        public boolean performAction(int n, int n2, Bundle bundle) {
            return ExploreByTouchHelper.this.performAction(n, n2, bundle);
        }
    }

}

