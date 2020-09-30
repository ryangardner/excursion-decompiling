/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package androidx.transition;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.core.view.ViewCompat;
import androidx.transition.CanvasUtils;
import androidx.transition.GhostView;
import androidx.transition.GhostViewHolder;
import androidx.transition.R;
import androidx.transition.ViewUtils;

class GhostViewPort
extends ViewGroup
implements GhostView {
    private Matrix mMatrix;
    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener(){

        public boolean onPreDraw() {
            ViewCompat.postInvalidateOnAnimation((View)GhostViewPort.this);
            if (GhostViewPort.this.mStartParent == null) return true;
            if (GhostViewPort.this.mStartView == null) return true;
            GhostViewPort.this.mStartParent.endViewTransition(GhostViewPort.this.mStartView);
            ViewCompat.postInvalidateOnAnimation((View)GhostViewPort.this.mStartParent);
            GhostViewPort.this.mStartParent = null;
            GhostViewPort.this.mStartView = null;
            return true;
        }
    };
    int mReferences;
    ViewGroup mStartParent;
    View mStartView;
    final View mView;

    GhostViewPort(View view) {
        super(view.getContext());
        this.mView = view;
        this.setWillNotDraw(false);
        this.setLayerType(2, null);
    }

    static GhostViewPort addGhost(View object, ViewGroup viewGroup, Matrix object2) {
        if (!(object.getParent() instanceof ViewGroup)) throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
        GhostViewHolder ghostViewHolder = GhostViewHolder.getHolder(viewGroup);
        GhostViewPort ghostViewPort = GhostViewPort.getGhostView((View)object);
        int n = 0;
        GhostViewPort ghostViewPort2 = ghostViewPort;
        int n2 = n;
        if (ghostViewPort != null) {
            GhostViewHolder ghostViewHolder2 = (GhostViewHolder)ghostViewPort.getParent();
            ghostViewPort2 = ghostViewPort;
            n2 = n;
            if (ghostViewHolder2 != ghostViewHolder) {
                n2 = ghostViewPort.mReferences;
                ghostViewHolder2.removeView((View)ghostViewPort);
                ghostViewPort2 = null;
            }
        }
        if (ghostViewPort2 == null) {
            ghostViewPort2 = object2;
            if (object2 == null) {
                ghostViewPort2 = new Matrix();
                GhostViewPort.calculateMatrix((View)object, viewGroup, (Matrix)ghostViewPort2);
            }
            object2 = new GhostViewPort((View)object);
            ((GhostViewPort)object2).setMatrix((Matrix)ghostViewPort2);
            if (ghostViewHolder == null) {
                object = new GhostViewHolder(viewGroup);
            } else {
                ghostViewHolder.popToOverlayTop();
                object = ghostViewHolder;
            }
            GhostViewPort.copySize((View)viewGroup, (View)object);
            GhostViewPort.copySize((View)viewGroup, (View)object2);
            ((GhostViewHolder)((Object)object)).addGhostView((GhostViewPort)object2);
            ((GhostViewPort)object2).mReferences = n2;
            object = object2;
        } else {
            object = ghostViewPort2;
            if (object2 != null) {
                ghostViewPort2.setMatrix((Matrix)object2);
                object = ghostViewPort2;
            }
        }
        ++((GhostViewPort)object).mReferences;
        return object;
    }

    static void calculateMatrix(View view, ViewGroup viewGroup, Matrix matrix) {
        view = (ViewGroup)view.getParent();
        matrix.reset();
        ViewUtils.transformMatrixToGlobal(view, matrix);
        matrix.preTranslate((float)(-view.getScrollX()), (float)(-view.getScrollY()));
        ViewUtils.transformMatrixToLocal((View)viewGroup, matrix);
    }

    static void copySize(View view, View view2) {
        ViewUtils.setLeftTopRightBottom(view2, view2.getLeft(), view2.getTop(), view2.getLeft() + view.getWidth(), view2.getTop() + view.getHeight());
    }

    static GhostViewPort getGhostView(View view) {
        return (GhostViewPort)view.getTag(R.id.ghost_view);
    }

    static void removeGhost(View object) {
        int n;
        if ((object = GhostViewPort.getGhostView(object)) == null) return;
        object.mReferences = n = object.mReferences - 1;
        if (n > 0) return;
        ((GhostViewHolder)object.getParent()).removeView(object);
    }

    static void setGhostView(View view, GhostViewPort ghostViewPort) {
        view.setTag(R.id.ghost_view, (Object)ghostViewPort);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        GhostViewPort.setGhostView(this.mView, this);
        this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.setTransitionVisibility(this.mView, 4);
        if (this.mView.getParent() == null) return;
        ((View)this.mView.getParent()).invalidate();
    }

    protected void onDetachedFromWindow() {
        this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.setTransitionVisibility(this.mView, 0);
        GhostViewPort.setGhostView(this.mView, null);
        if (this.mView.getParent() != null) {
            ((View)this.mView.getParent()).invalidate();
        }
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        CanvasUtils.enableZ(canvas, true);
        canvas.setMatrix(this.mMatrix);
        ViewUtils.setTransitionVisibility(this.mView, 0);
        this.mView.invalidate();
        ViewUtils.setTransitionVisibility(this.mView, 4);
        this.drawChild(canvas, this.mView, this.getDrawingTime());
        CanvasUtils.enableZ(canvas, false);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
    }

    @Override
    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.mStartParent = viewGroup;
        this.mStartView = view;
    }

    void setMatrix(Matrix matrix) {
        this.mMatrix = matrix;
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        if (GhostViewPort.getGhostView(this.mView) != this) return;
        n = n == 0 ? 4 : 0;
        ViewUtils.setTransitionVisibility(this.mView, n);
    }

}

