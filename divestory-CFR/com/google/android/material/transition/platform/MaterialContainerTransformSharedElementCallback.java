/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.SharedElementCallback
 *  android.content.Context
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.os.Parcelable
 *  android.transition.Transition
 *  android.transition.Transition$TransitionListener
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.Window
 */
package com.google.android.material.transition.platform;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.TransitionListenerAdapter;
import com.google.android.material.transition.platform.TransitionUtils;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class MaterialContainerTransformSharedElementCallback
extends SharedElementCallback {
    private static WeakReference<View> capturedSharedElement;
    private boolean entering = true;
    private Rect returnEndBounds;
    private ShapeProvider shapeProvider = new ShapeableViewShapeProvider();
    private boolean sharedElementReenterTransitionEnabled = false;
    private boolean transparentWindowBackgroundEnabled = true;

    private static void removeWindowBackground(Window window) {
        window.getDecorView().getBackground().mutate().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(0, BlendModeCompat.CLEAR));
    }

    private static void restoreWindowBackground(Window window) {
        window.getDecorView().getBackground().mutate().clearColorFilter();
    }

    private void setUpEnterTransform(final Window window) {
        Transition transition = window.getSharedElementEnterTransition();
        if (!(transition instanceof MaterialContainerTransform)) return;
        transition = (MaterialContainerTransform)transition;
        if (!this.sharedElementReenterTransitionEnabled) {
            window.setSharedElementReenterTransition(null);
        }
        if (!this.transparentWindowBackgroundEnabled) return;
        MaterialContainerTransformSharedElementCallback.updateBackgroundFadeDuration(window, (MaterialContainerTransform)transition);
        transition.addListener((Transition.TransitionListener)new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition) {
                MaterialContainerTransformSharedElementCallback.restoreWindowBackground(window);
            }

            @Override
            public void onTransitionStart(Transition transition) {
                MaterialContainerTransformSharedElementCallback.removeWindowBackground(window);
            }
        });
    }

    private void setUpReturnTransform(final Activity activity, final Window window) {
        Transition transition = window.getSharedElementReturnTransition();
        if (!(transition instanceof MaterialContainerTransform)) return;
        transition = (MaterialContainerTransform)transition;
        transition.setHoldAtEndEnabled(true);
        transition.addListener((Transition.TransitionListener)new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition) {
                if (capturedSharedElement != null && (transition = (View)capturedSharedElement.get()) != null) {
                    transition.setAlpha(1.0f);
                    capturedSharedElement = null;
                }
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        });
        if (!this.transparentWindowBackgroundEnabled) return;
        MaterialContainerTransformSharedElementCallback.updateBackgroundFadeDuration(window, (MaterialContainerTransform)transition);
        transition.addListener((Transition.TransitionListener)new TransitionListenerAdapter(){

            @Override
            public void onTransitionStart(Transition transition) {
                MaterialContainerTransformSharedElementCallback.removeWindowBackground(window);
            }
        });
    }

    private static void updateBackgroundFadeDuration(Window window, MaterialContainerTransform materialContainerTransform) {
        window.setTransitionBackgroundFadeDuration(materialContainerTransform.getDuration());
    }

    public ShapeProvider getShapeProvider() {
        return this.shapeProvider;
    }

    public boolean isSharedElementReenterTransitionEnabled() {
        return this.sharedElementReenterTransitionEnabled;
    }

    public boolean isTransparentWindowBackgroundEnabled() {
        return this.transparentWindowBackgroundEnabled;
    }

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
        capturedSharedElement = new WeakReference<View>(view);
        return super.onCaptureSharedElementSnapshot(view, matrix, rectF);
    }

    public View onCreateSnapshotView(Context context, Parcelable object) {
        if ((context = super.onCreateSnapshotView(context, (Parcelable)object)) == null) return context;
        object = capturedSharedElement;
        if (object == null) return context;
        if (this.shapeProvider == null) return context;
        if ((object = (View)((Reference)object).get()) == null) return context;
        if ((object = this.shapeProvider.provideShape((View)object)) == null) return context;
        context.setTag(R.id.mtrl_motion_snapshot_view, object);
        return context;
    }

    public void onMapSharedElements(List<String> view, Map<String, View> window) {
        if (view.isEmpty()) return;
        if (window.isEmpty()) return;
        if ((view = window.get(view.get(0))) == null) return;
        if ((view = ContextUtils.getActivity(view.getContext())) == null) return;
        window = view.getWindow();
        if (this.entering) {
            this.setUpEnterTransform(window);
            return;
        }
        this.setUpReturnTransform((Activity)view, window);
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
        if (!list2.isEmpty() && list2.get(0).getTag(R.id.mtrl_motion_snapshot_view) instanceof View) {
            list2.get(0).setTag(R.id.mtrl_motion_snapshot_view, null);
        }
        if (!this.entering && !list2.isEmpty()) {
            this.returnEndBounds = TransitionUtils.getRelativeBoundsRect(list2.get(0));
        }
        this.entering = false;
    }

    public void onSharedElementStart(List<String> view, List<View> list, List<View> list2) {
        if (!list.isEmpty() && !list2.isEmpty()) {
            list.get(0).setTag(R.id.mtrl_motion_snapshot_view, (Object)list2.get(0));
        }
        if (this.entering) return;
        if (list.isEmpty()) return;
        if (this.returnEndBounds == null) return;
        view = list.get(0);
        view.measure(View.MeasureSpec.makeMeasureSpec((int)this.returnEndBounds.width(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)this.returnEndBounds.height(), (int)1073741824));
        view.layout(this.returnEndBounds.left, this.returnEndBounds.top, this.returnEndBounds.right, this.returnEndBounds.bottom);
    }

    public void setShapeProvider(ShapeProvider shapeProvider) {
        this.shapeProvider = shapeProvider;
    }

    public void setSharedElementReenterTransitionEnabled(boolean bl) {
        this.sharedElementReenterTransitionEnabled = bl;
    }

    public void setTransparentWindowBackgroundEnabled(boolean bl) {
        this.transparentWindowBackgroundEnabled = bl;
    }

    public static interface ShapeProvider {
        public ShapeAppearanceModel provideShape(View var1);
    }

    public static class ShapeableViewShapeProvider
    implements ShapeProvider {
        @Override
        public ShapeAppearanceModel provideShape(View object) {
            if (!(object instanceof Shapeable)) return null;
            return ((Shapeable)object).getShapeAppearanceModel();
        }
    }

}

