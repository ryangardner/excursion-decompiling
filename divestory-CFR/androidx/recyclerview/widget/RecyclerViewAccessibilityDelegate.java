/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.recyclerview.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Map;
import java.util.WeakHashMap;

public class RecyclerViewAccessibilityDelegate
extends AccessibilityDelegateCompat {
    private final ItemDelegate mItemDelegate;
    final RecyclerView mRecyclerView;

    public RecyclerViewAccessibilityDelegate(RecyclerView object) {
        this.mRecyclerView = object;
        object = this.getItemDelegate();
        if (object != null && object instanceof ItemDelegate) {
            this.mItemDelegate = (ItemDelegate)object;
            return;
        }
        this.mItemDelegate = new ItemDelegate(this);
    }

    public AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }

    @Override
    public void onInitializeAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent((View)object, accessibilityEvent);
        if (!(object instanceof RecyclerView)) return;
        if (this.shouldIgnore()) return;
        if (((RecyclerView)(object = (RecyclerView)object)).getLayoutManager() == null) return;
        ((RecyclerView)object).getLayoutManager().onInitializeAccessibilityEvent(accessibilityEvent);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        if (this.shouldIgnore()) return;
        if (this.mRecyclerView.getLayoutManager() == null) return;
        this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat);
    }

    @Override
    public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
        if (super.performAccessibilityAction(view, n, bundle)) {
            return true;
        }
        if (this.shouldIgnore()) return false;
        if (this.mRecyclerView.getLayoutManager() == null) return false;
        return this.mRecyclerView.getLayoutManager().performAccessibilityAction(n, bundle);
    }

    boolean shouldIgnore() {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }

    public static class ItemDelegate
    extends AccessibilityDelegateCompat {
        private Map<View, AccessibilityDelegateCompat> mOriginalItemDelegates = new WeakHashMap<View, AccessibilityDelegateCompat>();
        final RecyclerViewAccessibilityDelegate mRecyclerViewDelegate;

        public ItemDelegate(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
            this.mRecyclerViewDelegate = recyclerViewAccessibilityDelegate;
        }

        @Override
        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat == null) return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            return accessibilityDelegateCompat.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        @Override
        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat == null) return super.getAccessibilityNodeProvider(view);
            return accessibilityDelegateCompat.getAccessibilityNodeProvider(view);
        }

        AccessibilityDelegateCompat getAndRemoveOriginalDelegateForItem(View view) {
            return this.mOriginalItemDelegates.remove((Object)view);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.onInitializeAccessibilityEvent(view, accessibilityEvent);
                return;
            }
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (!this.mRecyclerViewDelegate.shouldIgnore() && this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null) {
                this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
                AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
                if (accessibilityDelegateCompat != null) {
                    accessibilityDelegateCompat.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                    return;
                }
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                return;
            }
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        }

        @Override
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.onPopulateAccessibilityEvent(view, accessibilityEvent);
                return;
            }
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)viewGroup);
            if (accessibilityDelegateCompat == null) return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            return accessibilityDelegateCompat.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        @Override
        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            if (this.mRecyclerViewDelegate.shouldIgnore()) return super.performAccessibilityAction(view, n, bundle);
            if (this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() == null) return super.performAccessibilityAction(view, n, bundle);
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat != null) {
                if (!accessibilityDelegateCompat.performAccessibilityAction(view, n, bundle)) return this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(view, n, bundle);
                return true;
            }
            if (!super.performAccessibilityAction(view, n, bundle)) return this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(view, n, bundle);
            return true;
        }

        void saveOriginalDelegate(View view) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = ViewCompat.getAccessibilityDelegate(view);
            if (accessibilityDelegateCompat == null) return;
            if (accessibilityDelegateCompat == this) return;
            this.mOriginalItemDelegates.put(view, accessibilityDelegateCompat);
        }

        @Override
        public void sendAccessibilityEvent(View view, int n) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.sendAccessibilityEvent(view, n);
                return;
            }
            super.sendAccessibilityEvent(view, n);
        }

        @Override
        public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = this.mOriginalItemDelegates.get((Object)view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.sendAccessibilityEventUnchecked(view, accessibilityEvent);
                return;
            }
            super.sendAccessibilityEventUnchecked(view, accessibilityEvent);
        }
    }

}

