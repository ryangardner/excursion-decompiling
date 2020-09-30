/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration
extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{16843284};
    public static final int HORIZONTAL = 0;
    private static final String TAG = "DividerItem";
    public static final int VERTICAL = 1;
    private final Rect mBounds = new Rect();
    private Drawable mDivider;
    private int mOrientation;

    public DividerItemDecoration(Context context, int n) {
        Drawable drawable2;
        context = context.obtainStyledAttributes(ATTRS);
        this.mDivider = drawable2 = context.getDrawable(0);
        if (drawable2 == null) {
            Log.w((String)TAG, (String)"@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        context.recycle();
        this.setOrientation(n);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        int n;
        int n2;
        canvas.save();
        boolean bl = recyclerView.getClipToPadding();
        int n3 = 0;
        if (bl) {
            n2 = recyclerView.getPaddingTop();
            n = recyclerView.getHeight() - recyclerView.getPaddingBottom();
            canvas.clipRect(recyclerView.getPaddingLeft(), n2, recyclerView.getWidth() - recyclerView.getPaddingRight(), n);
        } else {
            n = recyclerView.getHeight();
            n2 = 0;
        }
        int n4 = recyclerView.getChildCount();
        do {
            if (n3 >= n4) {
                canvas.restore();
                return;
            }
            View view = recyclerView.getChildAt(n3);
            recyclerView.getLayoutManager().getDecoratedBoundsWithMargins(view, this.mBounds);
            int n5 = this.mBounds.right + Math.round(view.getTranslationX());
            int n6 = this.mDivider.getIntrinsicWidth();
            this.mDivider.setBounds(n5 - n6, n2, n5, n);
            this.mDivider.draw(canvas);
            ++n3;
        } while (true);
    }

    private void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        int n;
        int n2;
        canvas.save();
        boolean bl = recyclerView.getClipToPadding();
        int n3 = 0;
        if (bl) {
            n2 = recyclerView.getPaddingLeft();
            n = recyclerView.getWidth() - recyclerView.getPaddingRight();
            canvas.clipRect(n2, recyclerView.getPaddingTop(), n, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            n = recyclerView.getWidth();
            n2 = 0;
        }
        int n4 = recyclerView.getChildCount();
        do {
            if (n3 >= n4) {
                canvas.restore();
                return;
            }
            View view = recyclerView.getChildAt(n3);
            recyclerView.getDecoratedBoundsWithMargins(view, this.mBounds);
            int n5 = this.mBounds.bottom + Math.round(view.getTranslationY());
            int n6 = this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(n2, n5 - n6, n, n5);
            this.mDivider.draw(canvas);
            ++n3;
        } while (true);
    }

    public Drawable getDrawable() {
        return this.mDivider;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        view = this.mDivider;
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return;
        }
        if (this.mOrientation == 1) {
            rect.set(0, 0, 0, view.getIntrinsicHeight());
            return;
        }
        rect.set(0, 0, view.getIntrinsicWidth(), 0);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (recyclerView.getLayoutManager() == null) return;
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            this.drawVertical(canvas, recyclerView);
            return;
        }
        this.drawHorizontal(canvas, recyclerView);
    }

    public void setDrawable(Drawable drawable2) {
        if (drawable2 == null) throw new IllegalArgumentException("Drawable cannot be null.");
        this.mDivider = drawable2;
    }

    public void setOrientation(int n) {
        if (n != 0) {
            if (n != 1) throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.mOrientation = n;
    }
}

