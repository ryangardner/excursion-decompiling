/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnHoverListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewConfiguration
 *  android.view.accessibility.AccessibilityManager
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.TooltipPopup;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;

class TooltipCompatHandler
implements View.OnLongClickListener,
View.OnHoverListener,
View.OnAttachStateChangeListener {
    private static final long HOVER_HIDE_TIMEOUT_MS = 15000L;
    private static final long HOVER_HIDE_TIMEOUT_SHORT_MS = 3000L;
    private static final long LONG_CLICK_HIDE_TIMEOUT_MS = 2500L;
    private static final String TAG = "TooltipCompatHandler";
    private static TooltipCompatHandler sActiveHandler;
    private static TooltipCompatHandler sPendingHandler;
    private final View mAnchor;
    private int mAnchorX;
    private int mAnchorY;
    private boolean mFromTouch;
    private final Runnable mHideRunnable = new Runnable(){

        @Override
        public void run() {
            TooltipCompatHandler.this.hide();
        }
    };
    private final int mHoverSlop;
    private TooltipPopup mPopup;
    private final Runnable mShowRunnable = new Runnable(){

        @Override
        public void run() {
            TooltipCompatHandler.this.show(false);
        }
    };
    private final CharSequence mTooltipText;

    private TooltipCompatHandler(View view, CharSequence charSequence) {
        this.mAnchor = view;
        this.mTooltipText = charSequence;
        this.mHoverSlop = ViewConfigurationCompat.getScaledHoverSlop(ViewConfiguration.get((Context)view.getContext()));
        this.clearAnchorPos();
        this.mAnchor.setOnLongClickListener((View.OnLongClickListener)this);
        this.mAnchor.setOnHoverListener((View.OnHoverListener)this);
    }

    private void cancelPendingShow() {
        this.mAnchor.removeCallbacks(this.mShowRunnable);
    }

    private void clearAnchorPos() {
        this.mAnchorX = Integer.MAX_VALUE;
        this.mAnchorY = Integer.MAX_VALUE;
    }

    private void scheduleShow() {
        this.mAnchor.postDelayed(this.mShowRunnable, (long)ViewConfiguration.getLongPressTimeout());
    }

    private static void setPendingHandler(TooltipCompatHandler tooltipCompatHandler) {
        TooltipCompatHandler tooltipCompatHandler2 = sPendingHandler;
        if (tooltipCompatHandler2 != null) {
            tooltipCompatHandler2.cancelPendingShow();
        }
        sPendingHandler = tooltipCompatHandler;
        if (tooltipCompatHandler == null) return;
        tooltipCompatHandler.scheduleShow();
    }

    public static void setTooltipText(View view, CharSequence object) {
        TooltipCompatHandler tooltipCompatHandler = sPendingHandler;
        if (tooltipCompatHandler != null && tooltipCompatHandler.mAnchor == view) {
            TooltipCompatHandler.setPendingHandler(null);
        }
        if (!TextUtils.isEmpty((CharSequence)object)) {
            new TooltipCompatHandler(view, (CharSequence)object);
            return;
        }
        object = sActiveHandler;
        if (object != null && ((TooltipCompatHandler)object).mAnchor == view) {
            ((TooltipCompatHandler)object).hide();
        }
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        view.setOnHoverListener(null);
    }

    private boolean updateAnchorPos(MotionEvent motionEvent) {
        int n = (int)motionEvent.getX();
        int n2 = (int)motionEvent.getY();
        if (Math.abs(n - this.mAnchorX) <= this.mHoverSlop && Math.abs(n2 - this.mAnchorY) <= this.mHoverSlop) {
            return false;
        }
        this.mAnchorX = n;
        this.mAnchorY = n2;
        return true;
    }

    void hide() {
        if (sActiveHandler == this) {
            sActiveHandler = null;
            TooltipPopup tooltipPopup = this.mPopup;
            if (tooltipPopup != null) {
                tooltipPopup.hide();
                this.mPopup = null;
                this.clearAnchorPos();
                this.mAnchor.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
            } else {
                Log.e((String)TAG, (String)"sActiveHandler.mPopup == null");
            }
        }
        if (sPendingHandler == this) {
            TooltipCompatHandler.setPendingHandler(null);
        }
        this.mAnchor.removeCallbacks(this.mHideRunnable);
    }

    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.mPopup != null && this.mFromTouch) {
            return false;
        }
        view = (AccessibilityManager)this.mAnchor.getContext().getSystemService("accessibility");
        if (view.isEnabled() && view.isTouchExplorationEnabled()) {
            return false;
        }
        int n = motionEvent.getAction();
        if (n == 7) {
            if (!this.mAnchor.isEnabled()) return false;
            if (this.mPopup != null) return false;
            if (!this.updateAnchorPos(motionEvent)) return false;
            TooltipCompatHandler.setPendingHandler(this);
            return false;
        }
        if (n != 10) {
            return false;
        }
        this.clearAnchorPos();
        this.hide();
        return false;
    }

    public boolean onLongClick(View view) {
        this.mAnchorX = view.getWidth() / 2;
        this.mAnchorY = view.getHeight() / 2;
        this.show(true);
        return true;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        this.hide();
    }

    void show(boolean bl) {
        long l;
        if (!ViewCompat.isAttachedToWindow(this.mAnchor)) {
            return;
        }
        TooltipCompatHandler.setPendingHandler(null);
        Object object = sActiveHandler;
        if (object != null) {
            ((TooltipCompatHandler)object).hide();
        }
        sActiveHandler = this;
        this.mFromTouch = bl;
        this.mPopup = object = new TooltipPopup(this.mAnchor.getContext());
        ((TooltipPopup)object).show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText);
        this.mAnchor.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        if (this.mFromTouch) {
            l = 2500L;
        } else {
            int n;
            if ((ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1) {
                l = 3000L;
                n = ViewConfiguration.getLongPressTimeout();
            } else {
                l = 15000L;
                n = ViewConfiguration.getLongPressTimeout();
            }
            l -= (long)n;
        }
        this.mAnchor.removeCallbacks(this.mHideRunnable);
        this.mAnchor.postDelayed(this.mHideRunnable, l);
    }

}

