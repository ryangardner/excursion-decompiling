/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.R;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.AutoScrollHelper;
import androidx.core.widget.ListViewAutoScrollHelper;
import java.lang.reflect.Field;

class DropDownListView
extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private Field mIsChildViewEnabled;
    private boolean mListSelectionHidden;
    private int mMotionPosition;
    ResolveHoverRunnable mResolveHoverRunnable;
    private ListViewAutoScrollHelper mScrollHelper;
    private int mSelectionBottomPadding = 0;
    private int mSelectionLeftPadding = 0;
    private int mSelectionRightPadding = 0;
    private int mSelectionTopPadding = 0;
    private GateKeeperDrawable mSelector;
    private final Rect mSelectorRect = new Rect();

    DropDownListView(Context object, boolean bl) {
        super((Context)object, null, R.attr.dropDownListViewStyle);
        this.mHijackFocus = bl;
        this.setCacheColorHint(0);
        try {
            object = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.mIsChildViewEnabled = object;
            ((Field)object).setAccessible(true);
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        this.setPressed(false);
        this.drawableStateChanged();
        Object object = this.getChildAt(this.mMotionPosition - this.getFirstVisiblePosition());
        if (object != null) {
            object.setPressed(false);
        }
        if ((object = this.mClickAnimation) == null) return;
        ((ViewPropertyAnimatorCompat)object).cancel();
        this.mClickAnimation = null;
    }

    private void clickPressedItem(View view, int n) {
        this.performItemClick(view, n, this.getItemIdAtPosition(n));
    }

    private void drawSelectorCompat(Canvas canvas) {
        if (this.mSelectorRect.isEmpty()) return;
        Drawable drawable2 = this.getSelector();
        if (drawable2 == null) return;
        drawable2.setBounds(this.mSelectorRect);
        drawable2.draw(canvas);
    }

    private void positionSelectorCompat(int n, View object) {
        Rect rect = this.mSelectorRect;
        rect.set(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        try {
            boolean bl = this.mIsChildViewEnabled.getBoolean((Object)this);
            if (object.isEnabled() == bl) return;
            object = this.mIsChildViewEnabled;
            bl = !bl;
            ((Field)object).set((Object)this, bl);
            if (n == -1) return;
            this.refreshDrawableState();
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    private void positionSelectorLikeFocusCompat(int n, View view) {
        Drawable drawable2 = this.getSelector();
        boolean bl = true;
        boolean bl2 = drawable2 != null && n != -1;
        if (bl2) {
            drawable2.setVisible(false, false);
        }
        this.positionSelectorCompat(n, view);
        if (!bl2) return;
        view = this.mSelectorRect;
        float f = view.exactCenterX();
        float f2 = view.exactCenterY();
        if (this.getVisibility() != 0) {
            bl = false;
        }
        drawable2.setVisible(bl, false);
        DrawableCompat.setHotspot(drawable2, f, f2);
    }

    private void positionSelectorLikeTouchCompat(int n, View view, float f, float f2) {
        this.positionSelectorLikeFocusCompat(n, view);
        view = this.getSelector();
        if (view == null) return;
        if (n == -1) return;
        DrawableCompat.setHotspot((Drawable)view, f, f2);
    }

    private void setPressedItem(View view, int n, float f, float f2) {
        View view2;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
            this.drawableHotspotChanged(f, f2);
        }
        if (!this.isPressed()) {
            this.setPressed(true);
        }
        this.layoutChildren();
        int n2 = this.mMotionPosition;
        if (n2 != -1 && (view2 = this.getChildAt(n2 - this.getFirstVisiblePosition())) != null && view2 != view && view2.isPressed()) {
            view2.setPressed(false);
        }
        this.mMotionPosition = n;
        float f3 = view.getLeft();
        float f4 = view.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(f - f3, f2 - f4);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        this.positionSelectorLikeTouchCompat(n, view, f, f2);
        this.setSelectorEnabled(false);
        this.refreshDrawableState();
    }

    private void setSelectorEnabled(boolean bl) {
        GateKeeperDrawable gateKeeperDrawable = this.mSelector;
        if (gateKeeperDrawable == null) return;
        gateKeeperDrawable.setEnabled(bl);
    }

    private boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState;
    }

    private void updateSelectorStateCompat() {
        Drawable drawable2 = this.getSelector();
        if (drawable2 == null) return;
        if (!this.touchModeDrawsInPressedStateCompat()) return;
        if (!this.isPressed()) return;
        drawable2.setState(this.getDrawableState());
    }

    protected void dispatchDraw(Canvas canvas) {
        this.drawSelectorCompat(canvas);
        super.dispatchDraw(canvas);
    }

    protected void drawableStateChanged() {
        if (this.mResolveHoverRunnable != null) {
            return;
        }
        super.drawableStateChanged();
        this.setSelectorEnabled(true);
        this.updateSelectorStateCompat();
    }

    public boolean hasFocus() {
        if (this.mHijackFocus) return true;
        if (super.hasFocus()) return true;
        return false;
    }

    public boolean hasWindowFocus() {
        if (this.mHijackFocus) return true;
        if (super.hasWindowFocus()) return true;
        return false;
    }

    public boolean isFocused() {
        if (this.mHijackFocus) return true;
        if (super.isFocused()) return true;
        return false;
    }

    public boolean isInTouchMode() {
        if (this.mHijackFocus) {
            if (this.mListSelectionHidden) return true;
        }
        if (super.isInTouchMode()) return true;
        return false;
    }

    public int lookForSelectablePosition(int n, boolean bl) {
        int n2;
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) return -1;
        if (this.isInTouchMode()) {
            return -1;
        }
        int n3 = listAdapter.getCount();
        if (this.getAdapter().areAllItemsEnabled()) {
            if (n < 0) return -1;
            if (n < n3) return n;
            return -1;
        }
        if (bl) {
            n = Math.max(0, n);
            do {
                n2 = n;
                if (n < n3) {
                    n2 = n;
                    if (!listAdapter.isEnabled(n)) {
                        ++n;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            n = Math.min(n, n3 - 1);
            do {
                n2 = n;
                if (n < 0) break;
                n2 = n;
                if (listAdapter.isEnabled(n)) break;
                --n;
            } while (true);
        }
        if (n2 < 0) return -1;
        if (n2 < n3) return n2;
        return -1;
    }

    public int measureHeightOfChildrenCompat(int n, int n2, int n3, int n4, int n5) {
        n2 = this.getListPaddingTop();
        n3 = this.getListPaddingBottom();
        int n6 = this.getDividerHeight();
        Drawable drawable2 = this.getDivider();
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) {
            return n2 + n3;
        }
        n3 = n2 + n3;
        if (n6 <= 0 || drawable2 == null) {
            n6 = 0;
        }
        int n7 = listAdapter.getCount();
        drawable2 = null;
        int n8 = 0;
        int n9 = 0;
        n2 = 0;
        while (n8 < n7) {
            int n10 = listAdapter.getItemViewType(n8);
            int n11 = n9;
            if (n10 != n9) {
                drawable2 = null;
                n11 = n10;
            }
            View view = listAdapter.getView(n8, (View)drawable2, (ViewGroup)this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            drawable2 = layoutParams;
            if (layoutParams == null) {
                drawable2 = this.generateDefaultLayoutParams();
                view.setLayoutParams((ViewGroup.LayoutParams)drawable2);
            }
            n9 = drawable2.height > 0 ? View.MeasureSpec.makeMeasureSpec((int)drawable2.height, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            view.measure(n, n9);
            view.forceLayout();
            n9 = n3;
            if (n8 > 0) {
                n9 = n3 + n6;
            }
            if ((n3 = n9 + view.getMeasuredHeight()) >= n4) {
                n = n4;
                if (n5 < 0) return n;
                n = n4;
                if (n8 <= n5) return n;
                n = n4;
                if (n2 <= 0) return n;
                n = n4;
                if (n3 == n4) return n;
                return n2;
            }
            n10 = n2;
            if (n5 >= 0) {
                n10 = n2;
                if (n8 >= n5) {
                    n10 = n3;
                }
            }
            ++n8;
            n9 = n11;
            drawable2 = view;
            n2 = n10;
        }
        return n3;
    }

    protected void onDetachedFromWindow() {
        this.mResolveHoverRunnable = null;
        super.onDetachedFromWindow();
    }

    /*
     * Unable to fully structure code
     */
    public boolean onForwardedEvent(MotionEvent var1_1, int var2_2) {
        block10 : {
            block8 : {
                block9 : {
                    var3_3 = var1_1.getActionMasked();
                    if (var3_3 == 1) break block8;
                    if (var3_3 == 2) break block9;
                    if (var3_3 == 3) ** GOTO lbl-1000
                    ** GOTO lbl26
                }
                var4_4 = true;
                break block10;
            }
            var4_4 = false;
        }
        var5_5 = var1_1.findPointerIndex(var2_2);
        if (var5_5 < 0) lbl-1000: // 2 sources:
        {
            var2_2 = 0;
            var4_4 = false;
        } else {
            var2_2 = (int)var1_1.getX(var5_5);
            var6_6 = this.pointToPosition(var2_2, var5_5 = (int)var1_1.getY(var5_5));
            if (var6_6 == -1) {
                var2_2 = 1;
            } else {
                var7_7 = this.getChildAt(var6_6 - this.getFirstVisiblePosition());
                this.setPressedItem(var7_7, var6_6, var2_2, var5_5);
                if (var3_3 == 1) {
                    this.clickPressedItem(var7_7, var6_6);
                }
lbl26: // 4 sources:
                var2_2 = 0;
                var4_4 = true;
            }
        }
        if (!var4_4 || var2_2 != 0) {
            this.clearPressedItem();
        }
        if (!var4_4) {
            var1_1 = this.mScrollHelper;
            if (var1_1 == null) return var4_4;
            var1_1.setEnabled(false);
            return var4_4;
        }
        if (this.mScrollHelper == null) {
            this.mScrollHelper = new ListViewAutoScrollHelper(this);
        }
        this.mScrollHelper.setEnabled(true);
        this.mScrollHelper.onTouch((View)this, (MotionEvent)var1_1);
        return var4_4;
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(motionEvent);
        }
        int n = motionEvent.getActionMasked();
        if (n == 10 && this.mResolveHoverRunnable == null) {
            ResolveHoverRunnable resolveHoverRunnable;
            this.mResolveHoverRunnable = resolveHoverRunnable = new ResolveHoverRunnable();
            resolveHoverRunnable.post();
        }
        boolean bl = super.onHoverEvent(motionEvent);
        if (n != 9 && n != 7) {
            this.setSelection(-1);
            return bl;
        }
        n = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        if (n == -1) return bl;
        if (n == this.getSelectedItemPosition()) return bl;
        motionEvent = this.getChildAt(n - this.getFirstVisiblePosition());
        if (motionEvent.isEnabled()) {
            this.setSelectionFromTop(n, motionEvent.getTop() - this.getTop());
        }
        this.updateSelectorStateCompat();
        return bl;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mMotionPosition = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        }
        ResolveHoverRunnable resolveHoverRunnable = this.mResolveHoverRunnable;
        if (resolveHoverRunnable == null) return super.onTouchEvent(motionEvent);
        resolveHoverRunnable.cancel();
        return super.onTouchEvent(motionEvent);
    }

    void setListSelectionHidden(boolean bl) {
        this.mListSelectionHidden = bl;
    }

    public void setSelector(Drawable drawable2) {
        GateKeeperDrawable gateKeeperDrawable = drawable2 != null ? new GateKeeperDrawable(drawable2) : null;
        this.mSelector = gateKeeperDrawable;
        super.setSelector((Drawable)gateKeeperDrawable);
        gateKeeperDrawable = new Rect();
        if (drawable2 != null) {
            drawable2.getPadding((Rect)gateKeeperDrawable);
        }
        this.mSelectionLeftPadding = ((Rect)gateKeeperDrawable).left;
        this.mSelectionTopPadding = ((Rect)gateKeeperDrawable).top;
        this.mSelectionRightPadding = ((Rect)gateKeeperDrawable).right;
        this.mSelectionBottomPadding = ((Rect)gateKeeperDrawable).bottom;
    }

    private static class GateKeeperDrawable
    extends DrawableWrapper {
        private boolean mEnabled = true;

        GateKeeperDrawable(Drawable drawable2) {
            super(drawable2);
        }

        @Override
        public void draw(Canvas canvas) {
            if (!this.mEnabled) return;
            super.draw(canvas);
        }

        void setEnabled(boolean bl) {
            this.mEnabled = bl;
        }

        @Override
        public void setHotspot(float f, float f2) {
            if (!this.mEnabled) return;
            super.setHotspot(f, f2);
        }

        @Override
        public void setHotspotBounds(int n, int n2, int n3, int n4) {
            if (!this.mEnabled) return;
            super.setHotspotBounds(n, n2, n3, n4);
        }

        @Override
        public boolean setState(int[] arrn) {
            if (!this.mEnabled) return false;
            return super.setState(arrn);
        }

        @Override
        public boolean setVisible(boolean bl, boolean bl2) {
            if (!this.mEnabled) return false;
            return super.setVisible(bl, bl2);
        }
    }

    private class ResolveHoverRunnable
    implements Runnable {
        ResolveHoverRunnable() {
        }

        public void cancel() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.removeCallbacks((Runnable)this);
        }

        public void post() {
            DropDownListView.this.post((Runnable)this);
        }

        @Override
        public void run() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.drawableStateChanged();
        }
    }

}

