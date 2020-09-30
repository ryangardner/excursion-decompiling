/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package androidx.transition;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.view.ViewCompat;
import androidx.transition.ViewGroupOverlayApi14;
import androidx.transition.ViewOverlayImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

class ViewOverlayApi14
implements ViewOverlayImpl {
    protected OverlayViewGroup mOverlayViewGroup;

    ViewOverlayApi14(Context context, ViewGroup viewGroup, View view) {
        this.mOverlayViewGroup = new OverlayViewGroup(context, viewGroup, view, this);
    }

    static ViewOverlayApi14 createFrom(View view) {
        ViewGroup viewGroup = ViewOverlayApi14.getContentView(view);
        if (viewGroup == null) return null;
        int n = viewGroup.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view2 = viewGroup.getChildAt(n2);
            if (view2 instanceof OverlayViewGroup) {
                return ((OverlayViewGroup)view2).mViewOverlay;
            }
            ++n2;
        }
        return new ViewGroupOverlayApi14(viewGroup.getContext(), viewGroup, view);
    }

    static ViewGroup getContentView(View view) {
        while (view != null) {
            if (view.getId() == 16908290 && view instanceof ViewGroup) {
                return (ViewGroup)view;
            }
            if (!(view.getParent() instanceof ViewGroup)) continue;
            view = (ViewGroup)view.getParent();
        }
        return null;
    }

    @Override
    public void add(Drawable drawable2) {
        this.mOverlayViewGroup.add(drawable2);
    }

    @Override
    public void remove(Drawable drawable2) {
        this.mOverlayViewGroup.remove(drawable2);
    }

    static class OverlayViewGroup
    extends ViewGroup {
        static Method sInvalidateChildInParentFastMethod;
        private boolean mDisposed;
        ArrayList<Drawable> mDrawables = null;
        ViewGroup mHostView;
        View mRequestingView;
        ViewOverlayApi14 mViewOverlay;

        static {
            try {
                sInvalidateChildInParentFastMethod = ViewGroup.class.getDeclaredMethod("invalidateChildInParentFast", Integer.TYPE, Integer.TYPE, Rect.class);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return;
            }
        }

        OverlayViewGroup(Context context, ViewGroup viewGroup, View view, ViewOverlayApi14 viewOverlayApi14) {
            super(context);
            this.mHostView = viewGroup;
            this.mRequestingView = view;
            this.setRight(viewGroup.getWidth());
            this.setBottom(viewGroup.getHeight());
            viewGroup.addView((View)this);
            this.mViewOverlay = viewOverlayApi14;
        }

        private void assertNotDisposed() {
            if (this.mDisposed) throw new IllegalStateException("This overlay was disposed already. Please use a new one via ViewGroupUtils.getOverlay()");
        }

        private void disposeIfEmpty() {
            if (this.getChildCount() != 0) return;
            ArrayList<Drawable> arrayList = this.mDrawables;
            if (arrayList != null) {
                if (arrayList.size() != 0) return;
            }
            this.mDisposed = true;
            this.mHostView.removeView((View)this);
        }

        private void getOffset(int[] arrn) {
            int[] arrn2 = new int[2];
            int[] arrn3 = new int[2];
            this.mHostView.getLocationOnScreen(arrn2);
            this.mRequestingView.getLocationOnScreen(arrn3);
            arrn[0] = arrn3[0] - arrn2[0];
            arrn[1] = arrn3[1] - arrn2[1];
        }

        public void add(Drawable drawable2) {
            this.assertNotDisposed();
            if (this.mDrawables == null) {
                this.mDrawables = new ArrayList();
            }
            if (this.mDrawables.contains((Object)drawable2)) return;
            this.mDrawables.add(drawable2);
            this.invalidate(drawable2.getBounds());
            drawable2.setCallback((Drawable.Callback)this);
        }

        public void add(View view) {
            this.assertNotDisposed();
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view.getParent();
                if (viewGroup != this.mHostView && viewGroup.getParent() != null && ViewCompat.isAttachedToWindow((View)viewGroup)) {
                    int[] arrn = new int[2];
                    int[] arrn2 = new int[2];
                    viewGroup.getLocationOnScreen(arrn);
                    this.mHostView.getLocationOnScreen(arrn2);
                    ViewCompat.offsetLeftAndRight(view, arrn[0] - arrn2[0]);
                    ViewCompat.offsetTopAndBottom(view, arrn[1] - arrn2[1]);
                }
                viewGroup.removeView(view);
                if (view.getParent() != null) {
                    viewGroup.removeView(view);
                }
            }
            super.addView(view);
        }

        protected void dispatchDraw(Canvas canvas) {
            Object object = new int[2];
            int[] arrn = new int[2];
            this.mHostView.getLocationOnScreen((int[])object);
            this.mRequestingView.getLocationOnScreen(arrn);
            int n = 0;
            canvas.translate((float)(arrn[0] - object[0]), (float)(arrn[1] - object[1]));
            canvas.clipRect(new Rect(0, 0, this.mRequestingView.getWidth(), this.mRequestingView.getHeight()));
            super.dispatchDraw(canvas);
            object = this.mDrawables;
            int n2 = object == null ? 0 : ((ArrayList)object).size();
            while (n < n2) {
                this.mDrawables.get(n).draw(canvas);
                ++n;
            }
        }

        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public ViewParent invalidateChildInParent(int[] arrn, Rect rect) {
            if (this.mHostView == null) return null;
            rect.offset(arrn[0], arrn[1]);
            if (this.mHostView instanceof ViewGroup) {
                arrn[0] = 0;
                arrn[1] = 0;
                int[] arrn2 = new int[2];
                this.getOffset(arrn2);
                rect.offset(arrn2[0], arrn2[1]);
                return super.invalidateChildInParent(arrn, rect);
            }
            this.invalidate(rect);
            return null;
        }

        protected ViewParent invalidateChildInParentFast(int n, int n2, Rect rect) {
            if (!(this.mHostView instanceof ViewGroup)) return null;
            if (sInvalidateChildInParentFastMethod == null) return null;
            try {
                this.getOffset(new int[2]);
                sInvalidateChildInParentFastMethod.invoke((Object)this.mHostView, new Object[]{n, n2, rect});
                return null;
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
                return null;
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            return null;
        }

        public void invalidateDrawable(Drawable drawable2) {
            this.invalidate(drawable2.getBounds());
        }

        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        }

        public void remove(Drawable drawable2) {
            ArrayList<Drawable> arrayList = this.mDrawables;
            if (arrayList == null) return;
            arrayList.remove((Object)drawable2);
            this.invalidate(drawable2.getBounds());
            drawable2.setCallback(null);
            this.disposeIfEmpty();
        }

        public void remove(View view) {
            super.removeView(view);
            this.disposeIfEmpty();
        }

        protected boolean verifyDrawable(Drawable drawable2) {
            if (super.verifyDrawable(drawable2)) return true;
            ArrayList<Drawable> arrayList = this.mDrawables;
            if (arrayList == null) return false;
            if (!arrayList.contains((Object)drawable2)) return false;
            return true;
        }
    }

}

