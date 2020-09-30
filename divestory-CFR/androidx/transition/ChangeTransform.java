/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.transition.AnimatorUtils;
import androidx.transition.FloatArrayEvaluator;
import androidx.transition.GhostView;
import androidx.transition.GhostViewUtils;
import androidx.transition.MatrixUtils;
import androidx.transition.PathMotion;
import androidx.transition.PropertyValuesHolderUtils;
import androidx.transition.R;
import androidx.transition.Styleable;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionSet;
import androidx.transition.TransitionValues;
import androidx.transition.ViewUtils;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform
extends Transition {
    private static final Property<PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY;
    private static final String PROPNAME_INTERMEDIATE_MATRIX = "android:changeTransform:intermediateMatrix";
    private static final String PROPNAME_INTERMEDIATE_PARENT_MATRIX = "android:changeTransform:intermediateParentMatrix";
    private static final String PROPNAME_MATRIX = "android:changeTransform:matrix";
    private static final String PROPNAME_PARENT = "android:changeTransform:parent";
    private static final String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
    private static final String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
    private static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION;
    private static final Property<PathAnimatorMatrix, PointF> TRANSLATIONS_PROPERTY;
    private static final String[] sTransitionProperties;
    private boolean mReparent = true;
    private Matrix mTempMatrix = new Matrix();
    boolean mUseOverlay = true;

    static {
        sTransitionProperties = new String[]{PROPNAME_MATRIX, PROPNAME_TRANSFORMS, PROPNAME_PARENT_MATRIX};
        NON_TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, float[]>(float[].class, "nonTranslations"){

            public float[] get(PathAnimatorMatrix pathAnimatorMatrix) {
                return null;
            }

            public void set(PathAnimatorMatrix pathAnimatorMatrix, float[] arrf) {
                pathAnimatorMatrix.setValues(arrf);
            }
        };
        TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, PointF>(PointF.class, "translations"){

            public PointF get(PathAnimatorMatrix pathAnimatorMatrix) {
                return null;
            }

            public void set(PathAnimatorMatrix pathAnimatorMatrix, PointF pointF) {
                pathAnimatorMatrix.setTranslation(pointF);
            }
        };
        boolean bl = Build.VERSION.SDK_INT >= 21;
        SUPPORTS_VIEW_REMOVAL_SUPPRESSION = bl;
    }

    public ChangeTransform() {
    }

    public ChangeTransform(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        context = context.obtainStyledAttributes(object, Styleable.CHANGE_TRANSFORM);
        object = (XmlPullParser)object;
        this.mUseOverlay = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlPullParser)object, "reparentWithOverlay", 1, true);
        this.mReparent = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlPullParser)object, "reparent", 0, true);
        context.recycle();
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        transitionValues.values.put(PROPNAME_PARENT, (Object)view.getParent());
        Transforms transforms = new Transforms(view);
        transitionValues.values.put(PROPNAME_TRANSFORMS, transforms);
        transforms = view.getMatrix();
        transforms = transforms != null && !transforms.isIdentity() ? new Matrix((Matrix)transforms) : null;
        transitionValues.values.put(PROPNAME_MATRIX, transforms);
        if (!this.mReparent) return;
        transforms = new Matrix();
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        ViewUtils.transformMatrixToGlobal((View)viewGroup, (Matrix)transforms);
        transforms.preTranslate((float)(-viewGroup.getScrollX()), (float)(-viewGroup.getScrollY()));
        transitionValues.values.put(PROPNAME_PARENT_MATRIX, transforms);
        transitionValues.values.put(PROPNAME_INTERMEDIATE_MATRIX, view.getTag(R.id.transition_transform));
        transitionValues.values.put(PROPNAME_INTERMEDIATE_PARENT_MATRIX, view.getTag(R.id.parent_matrix));
    }

    private void createGhostView(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        View view = transitionValues2.view;
        Object object2 = new Matrix((Matrix)transitionValues2.values.get(PROPNAME_PARENT_MATRIX));
        ViewUtils.transformMatrixToLocal((View)object, (Matrix)object2);
        object2 = GhostViewUtils.addGhost(view, object, (Matrix)object2);
        if (object2 == null) {
            return;
        }
        object2.reserveEndViewTransition((ViewGroup)transitionValues.values.get(PROPNAME_PARENT), transitionValues.view);
        object = this;
        while (object.mParent != null) {
            object = object.mParent;
        }
        object.addListener(new GhostListener(view, (GhostView)object2));
        if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) return;
        if (transitionValues.view != transitionValues2.view) {
            ViewUtils.setTransitionAlpha(transitionValues.view, 0.0f);
        }
        ViewUtils.setTransitionAlpha(view, 1.0f);
    }

    private ObjectAnimator createTransformAnimator(TransitionValues transitionValues, TransitionValues transitionValues2, final boolean bl) {
        transitionValues = (Matrix)transitionValues.values.get(PROPNAME_MATRIX);
        Object object = (Matrix)transitionValues2.values.get(PROPNAME_MATRIX);
        Object object2 = transitionValues;
        if (transitionValues == null) {
            object2 = MatrixUtils.IDENTITY_MATRIX;
        }
        transitionValues = object;
        if (object == null) {
            transitionValues = MatrixUtils.IDENTITY_MATRIX;
        }
        if (object2.equals((Object)transitionValues)) {
            return null;
        }
        object = (Transforms)transitionValues2.values.get(PROPNAME_TRANSFORMS);
        transitionValues2 = transitionValues2.view;
        ChangeTransform.setIdentityTransforms((View)transitionValues2);
        Path path = new float[9];
        object2.getValues((float[])path);
        float[] arrf = new float[9];
        transitionValues.getValues(arrf);
        object2 = new PathAnimatorMatrix((View)transitionValues2, (float[])path);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, (TypeEvaluator)new FloatArrayEvaluator(new float[9]), (Object[])new float[][]{path, arrf});
        path = this.getPathMotion().getPath(path[2], path[5], arrf[2], arrf[5]);
        path = ObjectAnimator.ofPropertyValuesHolder((Object)object2, (PropertyValuesHolder[])new PropertyValuesHolder[]{propertyValuesHolder, PropertyValuesHolderUtils.ofPointF(TRANSLATIONS_PROPERTY, path)});
        transitionValues = new AnimatorListenerAdapter((Matrix)transitionValues, (View)transitionValues2, (Transforms)object, (PathAnimatorMatrix)object2){
            private boolean mIsCanceled;
            private Matrix mTempMatrix = new Matrix();
            final /* synthetic */ Matrix val$finalEndMatrix;
            final /* synthetic */ PathAnimatorMatrix val$pathAnimatorMatrix;
            final /* synthetic */ Transforms val$transforms;
            final /* synthetic */ View val$view;
            {
                this.val$finalEndMatrix = matrix;
                this.val$view = view;
                this.val$transforms = transforms;
                this.val$pathAnimatorMatrix = pathAnimatorMatrix;
            }

            private void setCurrentMatrix(Matrix matrix) {
                this.mTempMatrix.set(matrix);
                this.val$view.setTag(R.id.transition_transform, (Object)this.mTempMatrix);
                this.val$transforms.restore(this.val$view);
            }

            public void onAnimationCancel(Animator animator2) {
                this.mIsCanceled = true;
            }

            public void onAnimationEnd(Animator animator2) {
                if (!this.mIsCanceled) {
                    if (bl && ChangeTransform.this.mUseOverlay) {
                        this.setCurrentMatrix(this.val$finalEndMatrix);
                    } else {
                        this.val$view.setTag(R.id.transition_transform, null);
                        this.val$view.setTag(R.id.parent_matrix, null);
                    }
                }
                ViewUtils.setAnimationMatrix(this.val$view, null);
                this.val$transforms.restore(this.val$view);
            }

            public void onAnimationPause(Animator animator2) {
                this.setCurrentMatrix(this.val$pathAnimatorMatrix.getMatrix());
            }

            public void onAnimationResume(Animator animator2) {
                ChangeTransform.setIdentityTransforms(this.val$view);
            }
        };
        path.addListener((Animator.AnimatorListener)transitionValues);
        AnimatorUtils.addPauseListener((Animator)path, (AnimatorListenerAdapter)transitionValues);
        return path;
    }

    private boolean parentsMatch(ViewGroup object, ViewGroup viewGroup) {
        boolean bl = this.isValidTarget((View)object);
        boolean bl2 = true;
        boolean bl3 = false;
        if (bl && this.isValidTarget((View)viewGroup)) {
            if ((object = this.getMatchedTransitionValues((View)object, true)) == null) return bl3;
            if (viewGroup != object.view) return false;
            return bl2;
        }
        if (object != viewGroup) return false;
        return bl2;
    }

    static void setIdentityTransforms(View view) {
        ChangeTransform.setTransforms(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    private void setMatricesForParent(TransitionValues transitionValues, TransitionValues transitionValues2) {
        Matrix matrix = (Matrix)transitionValues2.values.get(PROPNAME_PARENT_MATRIX);
        transitionValues2.view.setTag(R.id.parent_matrix, (Object)matrix);
        Matrix matrix2 = this.mTempMatrix;
        matrix2.reset();
        matrix.invert(matrix2);
        matrix = (Matrix)transitionValues.values.get(PROPNAME_MATRIX);
        transitionValues2 = matrix;
        if (matrix == null) {
            transitionValues2 = new Matrix();
            transitionValues.values.put(PROPNAME_MATRIX, transitionValues2);
        }
        transitionValues2.postConcat((Matrix)transitionValues.values.get(PROPNAME_PARENT_MATRIX));
        transitionValues2.postConcat(matrix2);
    }

    static void setTransforms(View view, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        ViewCompat.setTranslationZ(view, f3);
        view.setScaleX(f4);
        view.setScaleY(f5);
        view.setRotationX(f6);
        view.setRotationY(f7);
        view.setRotation(f8);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
        if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) return;
        ((ViewGroup)transitionValues.view.getParent()).startViewTransition(transitionValues.view);
    }

    @Override
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) return null;
        if (transitionValues2 == null) return null;
        if (!transitionValues.values.containsKey(PROPNAME_PARENT)) return null;
        if (!transitionValues2.values.containsKey(PROPNAME_PARENT)) {
            return null;
        }
        ViewGroup viewGroup2 = (ViewGroup)transitionValues.values.get(PROPNAME_PARENT);
        ViewGroup viewGroup3 = (ViewGroup)transitionValues2.values.get(PROPNAME_PARENT);
        boolean bl = this.mReparent && !this.parentsMatch(viewGroup2, viewGroup3);
        viewGroup3 = (Matrix)transitionValues.values.get(PROPNAME_INTERMEDIATE_MATRIX);
        if (viewGroup3 != null) {
            transitionValues.values.put(PROPNAME_MATRIX, (Object)viewGroup3);
        }
        if ((viewGroup3 = (Matrix)transitionValues.values.get(PROPNAME_INTERMEDIATE_PARENT_MATRIX)) != null) {
            transitionValues.values.put(PROPNAME_PARENT_MATRIX, (Object)viewGroup3);
        }
        if (bl) {
            this.setMatricesForParent(transitionValues, transitionValues2);
        }
        viewGroup3 = this.createTransformAnimator(transitionValues, transitionValues2, bl);
        if (bl && viewGroup3 != null && this.mUseOverlay) {
            this.createGhostView(viewGroup, transitionValues, transitionValues2);
            return viewGroup3;
        }
        if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) return viewGroup3;
        viewGroup2.endViewTransition(transitionValues.view);
        return viewGroup3;
    }

    public boolean getReparent() {
        return this.mReparent;
    }

    public boolean getReparentWithOverlay() {
        return this.mUseOverlay;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setReparent(boolean bl) {
        this.mReparent = bl;
    }

    public void setReparentWithOverlay(boolean bl) {
        this.mUseOverlay = bl;
    }

    private static class GhostListener
    extends TransitionListenerAdapter {
        private GhostView mGhostView;
        private View mView;

        GhostListener(View view, GhostView ghostView) {
            this.mView = view;
            this.mGhostView = ghostView;
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
            GhostViewUtils.removeGhost(this.mView);
            this.mView.setTag(R.id.transition_transform, null);
            this.mView.setTag(R.id.parent_matrix, null);
        }

        @Override
        public void onTransitionPause(Transition transition) {
            this.mGhostView.setVisibility(4);
        }

        @Override
        public void onTransitionResume(Transition transition) {
            this.mGhostView.setVisibility(0);
        }
    }

    private static class PathAnimatorMatrix {
        private final Matrix mMatrix = new Matrix();
        private float mTranslationX;
        private float mTranslationY;
        private final float[] mValues;
        private final View mView;

        PathAnimatorMatrix(View arrf, float[] arrf2) {
            this.mView = arrf;
            arrf = (float[])arrf2.clone();
            this.mValues = arrf;
            this.mTranslationX = arrf[2];
            this.mTranslationY = arrf[5];
            this.setAnimationMatrix();
        }

        private void setAnimationMatrix() {
            float[] arrf = this.mValues;
            arrf[2] = this.mTranslationX;
            arrf[5] = this.mTranslationY;
            this.mMatrix.setValues(arrf);
            ViewUtils.setAnimationMatrix(this.mView, this.mMatrix);
        }

        Matrix getMatrix() {
            return this.mMatrix;
        }

        void setTranslation(PointF pointF) {
            this.mTranslationX = pointF.x;
            this.mTranslationY = pointF.y;
            this.setAnimationMatrix();
        }

        void setValues(float[] arrf) {
            System.arraycopy(arrf, 0, this.mValues, 0, arrf.length);
            this.setAnimationMatrix();
        }
    }

    private static class Transforms {
        final float mRotationX;
        final float mRotationY;
        final float mRotationZ;
        final float mScaleX;
        final float mScaleY;
        final float mTranslationX;
        final float mTranslationY;
        final float mTranslationZ;

        Transforms(View view) {
            this.mTranslationX = view.getTranslationX();
            this.mTranslationY = view.getTranslationY();
            this.mTranslationZ = ViewCompat.getTranslationZ(view);
            this.mScaleX = view.getScaleX();
            this.mScaleY = view.getScaleY();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mRotationZ = view.getRotation();
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Transforms;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Transforms)object;
            bl = bl2;
            if (((Transforms)object).mTranslationX != this.mTranslationX) return bl;
            bl = bl2;
            if (((Transforms)object).mTranslationY != this.mTranslationY) return bl;
            bl = bl2;
            if (((Transforms)object).mTranslationZ != this.mTranslationZ) return bl;
            bl = bl2;
            if (((Transforms)object).mScaleX != this.mScaleX) return bl;
            bl = bl2;
            if (((Transforms)object).mScaleY != this.mScaleY) return bl;
            bl = bl2;
            if (((Transforms)object).mRotationX != this.mRotationX) return bl;
            bl = bl2;
            if (((Transforms)object).mRotationY != this.mRotationY) return bl;
            bl = bl2;
            if (((Transforms)object).mRotationZ != this.mRotationZ) return bl;
            return true;
        }

        public int hashCode() {
            float f = this.mTranslationX;
            int n = 0;
            int n2 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mTranslationY;
            int n3 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mTranslationZ;
            int n4 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mScaleX;
            int n5 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mScaleY;
            int n6 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mRotationX;
            int n7 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mRotationY;
            int n8 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mRotationZ;
            if (f == 0.0f) return ((((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n;
            n = Float.floatToIntBits(f);
            return ((((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n;
        }

        public void restore(View view) {
            ChangeTransform.setTransforms(view, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mScaleX, this.mScaleY, this.mRotationX, this.mRotationY, this.mRotationZ);
        }
    }

}

