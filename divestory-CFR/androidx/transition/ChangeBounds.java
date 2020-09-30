/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.transition.ObjectAnimatorUtils;
import androidx.transition.PathMotion;
import androidx.transition.PropertyValuesHolderUtils;
import androidx.transition.RectEvaluator;
import androidx.transition.Styleable;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionUtils;
import androidx.transition.TransitionValues;
import androidx.transition.ViewGroupUtils;
import androidx.transition.ViewUtils;
import java.util.Map;

public class ChangeBounds
extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY;
    private static final Property<View, PointF> POSITION_PROPERTY;
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
    private static RectEvaluator sRectEvaluator;
    private static final String[] sTransitionProperties;
    private boolean mReparent = false;
    private boolean mResizeClip = false;
    private int[] mTempLocation = new int[2];

    static {
        sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
        DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin"){
            private Rect mBounds = new Rect();

            public PointF get(Drawable drawable2) {
                drawable2.copyBounds(this.mBounds);
                return new PointF((float)this.mBounds.left, (float)this.mBounds.top);
            }

            public void set(Drawable drawable2, PointF pointF) {
                drawable2.copyBounds(this.mBounds);
                this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
                drawable2.setBounds(this.mBounds);
            }
        };
        TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft"){

            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setTopLeft(pointF);
            }
        };
        BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight"){

            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setBottomRight(pointF);
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                ViewUtils.setLeftTopRightBottom(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
            }
        };
        POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                int n = Math.round(pointF.x);
                int n2 = Math.round(pointF.y);
                ViewUtils.setLeftTopRightBottom(view, n, n2, view.getWidth() + n, view.getHeight() + n2);
            }
        };
        sRectEvaluator = new RectEvaluator();
    }

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_BOUNDS);
        boolean bl = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlResourceParser)attributeSet, "resizeClip", 0, false);
        context.recycle();
        this.setResizeClip(bl);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (!ViewCompat.isLaidOut(view) && view.getWidth() == 0) {
            if (view.getHeight() == 0) return;
        }
        transitionValues.values.put(PROPNAME_BOUNDS, (Object)new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        transitionValues.values.put(PROPNAME_PARENT, (Object)transitionValues.view.getParent());
        if (this.mReparent) {
            transitionValues.view.getLocationInWindow(this.mTempLocation);
            transitionValues.values.put(PROPNAME_WINDOW_X, this.mTempLocation[0]);
            transitionValues.values.put(PROPNAME_WINDOW_Y, this.mTempLocation[1]);
        }
        if (!this.mResizeClip) return;
        transitionValues.values.put(PROPNAME_CLIP, (Object)ViewCompat.getClipBounds(view));
    }

    private boolean parentMatches(View view, View view2) {
        boolean bl;
        boolean bl2 = this.mReparent;
        boolean bl3 = bl = true;
        if (!bl2) return bl3;
        TransitionValues transitionValues = this.getMatchedTransitionValues(view, true);
        if (transitionValues == null) {
            if (view != view2) return false;
            return bl;
        }
        if (view2 == transitionValues.view) return bl;
        return false;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(final ViewGroup viewGroup, TransitionValues object, TransitionValues object2) {
        View view;
        ObjectAnimator objectAnimator;
        block22 : {
            int n;
            int n2;
            int n3;
            int n4;
            ViewGroup viewGroup2;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            int n12;
            int n13;
            block27 : {
                int n14;
                block26 : {
                    block25 : {
                        block23 : {
                            block24 : {
                                if (object == null) return null;
                                if (object2 == null) {
                                    return null;
                                }
                                objectAnimator = ((TransitionValues)object).values;
                                view = object2.values;
                                objectAnimator = (ObjectAnimator)objectAnimator.get(PROPNAME_PARENT);
                                viewGroup2 = (ViewGroup)view.get(PROPNAME_PARENT);
                                if (objectAnimator == null) return null;
                                if (viewGroup2 == null) {
                                    return null;
                                }
                                view = object2.view;
                                if (!this.parentMatches((View)objectAnimator, (View)viewGroup2)) break block22;
                                viewGroup = (Rect)((TransitionValues)object).values.get(PROPNAME_BOUNDS);
                                objectAnimator = (Rect)object2.values.get(PROPNAME_BOUNDS);
                                n = viewGroup.left;
                                n6 = objectAnimator.left;
                                n13 = viewGroup.top;
                                n2 = objectAnimator.top;
                                n12 = viewGroup.right;
                                n9 = objectAnimator.right;
                                n8 = viewGroup.bottom;
                                n5 = objectAnimator.bottom;
                                n3 = n12 - n;
                                n11 = n8 - n13;
                                n7 = n9 - n6;
                                n10 = n5 - n2;
                                object = (Rect)((TransitionValues)object).values.get(PROPNAME_CLIP);
                                objectAnimator = (Rect)object2.values.get(PROPNAME_CLIP);
                                if ((n3 == 0 || n11 == 0) && (n7 == 0 || n10 == 0)) break block23;
                                n4 = n == n6 && n13 == n2 ? 0 : 1;
                                if (n12 != n9) break block24;
                                n14 = n4;
                                if (n8 == n5) break block25;
                            }
                            n14 = n4 + 1;
                            break block25;
                        }
                        n14 = 0;
                    }
                    if (object != null && !object.equals((Object)objectAnimator)) break block26;
                    n4 = n14;
                    if (object != null) break block27;
                    n4 = n14;
                    if (objectAnimator == null) break block27;
                }
                n4 = n14 + 1;
            }
            if (n4 <= 0) return null;
            if (!this.mResizeClip) {
                viewGroup = view;
                ViewUtils.setLeftTopRightBottom((View)viewGroup, n, n13, n12, n8);
                if (n4 == 2) {
                    if (n3 == n7 && n11 == n10) {
                        object = this.getPathMotion().getPath(n, n13, n6, n2);
                        viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup, POSITION_PROPERTY, (Path)object);
                    } else {
                        object = new ViewBounds((View)viewGroup);
                        viewGroup = this.getPathMotion().getPath(n, n13, n6, n2);
                        object2 = ObjectAnimatorUtils.ofPointF(object, TOP_LEFT_PROPERTY, (Path)viewGroup);
                        viewGroup = this.getPathMotion().getPath(n12, n8, n9, n5);
                        objectAnimator = ObjectAnimatorUtils.ofPointF(object, BOTTOM_RIGHT_PROPERTY, (Path)viewGroup);
                        viewGroup = new AnimatorSet();
                        viewGroup.playTogether(new Animator[]{object2, objectAnimator});
                        viewGroup.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewBounds)object){
                            private ViewBounds mViewBounds;
                            final /* synthetic */ ViewBounds val$viewBounds;
                            {
                                this.val$viewBounds = viewBounds;
                                this.mViewBounds = this.val$viewBounds;
                            }
                        });
                    }
                } else if (n == n6 && n13 == n2) {
                    object = this.getPathMotion().getPath(n12, n8, n9, n5);
                    viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup, BOTTOM_RIGHT_ONLY_PROPERTY, (Path)object);
                } else {
                    object = this.getPathMotion().getPath(n, n13, n6, n2);
                    viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup, TOP_LEFT_ONLY_PROPERTY, (Path)object);
                }
            } else {
                viewGroup2 = view;
                ViewUtils.setLeftTopRightBottom((View)viewGroup2, n, n13, Math.max(n3, n7) + n, Math.max(n11, n10) + n13);
                if (n == n6 && n13 == n2) {
                    viewGroup = null;
                } else {
                    viewGroup = this.getPathMotion().getPath(n, n13, n6, n2);
                    viewGroup = ObjectAnimatorUtils.ofPointF(viewGroup2, POSITION_PROPERTY, (Path)viewGroup);
                }
                if (object == null) {
                    object = new Rect(0, 0, n3, n11);
                }
                if (!object.equals(object2 = objectAnimator == null ? new Rect(0, 0, n7, n10) : objectAnimator)) {
                    ViewCompat.setClipBounds((View)viewGroup2, (Rect)object);
                    object = ObjectAnimator.ofObject((Object)viewGroup2, (String)"clipBounds", (TypeEvaluator)sRectEvaluator, (Object[])new Object[]{object, object2});
                    object.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((View)viewGroup2, (Rect)objectAnimator, n6, n2, n9, n5){
                        private boolean mIsCanceled;
                        final /* synthetic */ int val$endBottom;
                        final /* synthetic */ int val$endLeft;
                        final /* synthetic */ int val$endRight;
                        final /* synthetic */ int val$endTop;
                        final /* synthetic */ Rect val$finalClip;
                        final /* synthetic */ View val$view;
                        {
                            this.val$view = view;
                            this.val$finalClip = rect;
                            this.val$endLeft = n;
                            this.val$endTop = n2;
                            this.val$endRight = n3;
                            this.val$endBottom = n4;
                        }

                        public void onAnimationCancel(Animator animator2) {
                            this.mIsCanceled = true;
                        }

                        public void onAnimationEnd(Animator animator2) {
                            if (this.mIsCanceled) return;
                            ViewCompat.setClipBounds(this.val$view, this.val$finalClip);
                            ViewUtils.setLeftTopRightBottom(this.val$view, this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
                        }
                    });
                } else {
                    object = null;
                }
                viewGroup = TransitionUtils.mergeAnimators((Animator)viewGroup, (Animator)object);
            }
            if (!(view.getParent() instanceof ViewGroup)) return viewGroup;
            object = (ViewGroup)view.getParent();
            ViewGroupUtils.suppressLayout((ViewGroup)object, true);
            this.addListener(new TransitionListenerAdapter((ViewGroup)object){
                boolean mCanceled = false;
                final /* synthetic */ ViewGroup val$parent;
                {
                    this.val$parent = viewGroup;
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    ViewGroupUtils.suppressLayout(this.val$parent, false);
                    this.mCanceled = true;
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (!this.mCanceled) {
                        ViewGroupUtils.suppressLayout(this.val$parent, false);
                    }
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    ViewGroupUtils.suppressLayout(this.val$parent, false);
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    ViewGroupUtils.suppressLayout(this.val$parent, true);
                }
            });
            return viewGroup;
        }
        int n = (Integer)((TransitionValues)object).values.get(PROPNAME_WINDOW_X);
        int n15 = (Integer)((TransitionValues)object).values.get(PROPNAME_WINDOW_Y);
        int n16 = (Integer)object2.values.get(PROPNAME_WINDOW_X);
        int n17 = (Integer)object2.values.get(PROPNAME_WINDOW_Y);
        if (n == n16) {
            if (n15 == n17) return null;
        }
        viewGroup.getLocationInWindow(this.mTempLocation);
        object = Bitmap.createBitmap((int)view.getWidth(), (int)view.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        view.draw(new Canvas((Bitmap)object));
        object = new BitmapDrawable((Bitmap)object);
        float f = ViewUtils.getTransitionAlpha(view);
        ViewUtils.setTransitionAlpha(view, 0.0f);
        ViewUtils.getOverlay((View)viewGroup).add((Drawable)object);
        object2 = this.getPathMotion();
        objectAnimator = this.mTempLocation;
        object2 = object2.getPath(n - objectAnimator[0], n15 - objectAnimator[1], n16 - objectAnimator[0], n17 - objectAnimator[1]);
        object2 = ObjectAnimator.ofPropertyValuesHolder((Object)object, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, (Path)object2)});
        object2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((BitmapDrawable)object, view, f){
            final /* synthetic */ BitmapDrawable val$drawable;
            final /* synthetic */ float val$transitionAlpha;
            final /* synthetic */ View val$view;
            {
                this.val$drawable = bitmapDrawable;
                this.val$view = view;
                this.val$transitionAlpha = f;
            }

            public void onAnimationEnd(Animator animator2) {
                ViewUtils.getOverlay((View)viewGroup).remove((Drawable)this.val$drawable);
                ViewUtils.setTransitionAlpha(this.val$view, this.val$transitionAlpha);
            }
        });
        return object2;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean bl) {
        this.mResizeClip = bl;
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }

        void setBottomRight(PointF pointF) {
            int n;
            this.mRight = Math.round(pointF.x);
            this.mBottom = Math.round(pointF.y);
            this.mBottomRightCalls = n = this.mBottomRightCalls + 1;
            if (this.mTopLeftCalls != n) return;
            this.setLeftTopRightBottom();
        }

        void setTopLeft(PointF pointF) {
            int n;
            this.mLeft = Math.round(pointF.x);
            this.mTop = Math.round(pointF.y);
            this.mTopLeftCalls = n = this.mTopLeftCalls + 1;
            if (n != this.mBottomRightCalls) return;
            this.setLeftTopRightBottom();
        }
    }

}

