/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 */
package androidx.constraintlayout.motion.widget;

import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;

public abstract class SplineSet {
    private static final String TAG = "SplineSet";
    private int count;
    protected CurveFit mCurveFit;
    protected int[] mTimePoints = new int[10];
    private String mType;
    protected float[] mValues = new float[10];

    static SplineSet makeCustomSpline(String string2, SparseArray<ConstraintAttribute> sparseArray) {
        return new CustomSet(string2, sparseArray);
    }

    static SplineSet makeSpline(String string2) {
        int n;
        block36 : {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 156108012: {
                    if (!string2.equals("waveOffset")) break;
                    n = 10;
                    break block36;
                }
                case 92909918: {
                    if (!string2.equals("alpha")) break;
                    n = 0;
                    break block36;
                }
                case 37232917: {
                    if (!string2.equals("transitionPathRotate")) break;
                    n = 7;
                    break block36;
                }
                case -4379043: {
                    if (!string2.equals("elevation")) break;
                    n = 1;
                    break block36;
                }
                case -40300674: {
                    if (!string2.equals("rotation")) break;
                    n = 2;
                    break block36;
                }
                case -760884509: {
                    if (!string2.equals("transformPivotY")) break;
                    n = 6;
                    break block36;
                }
                case -760884510: {
                    if (!string2.equals("transformPivotX")) break;
                    n = 5;
                    break block36;
                }
                case -797520672: {
                    if (!string2.equals("waveVariesBy")) break;
                    n = 11;
                    break block36;
                }
                case -908189617: {
                    if (!string2.equals("scaleY")) break;
                    n = 9;
                    break block36;
                }
                case -908189618: {
                    if (!string2.equals("scaleX")) break;
                    n = 8;
                    break block36;
                }
                case -1001078227: {
                    if (!string2.equals("progress")) break;
                    n = 15;
                    break block36;
                }
                case -1225497655: {
                    if (!string2.equals("translationZ")) break;
                    n = 14;
                    break block36;
                }
                case -1225497656: {
                    if (!string2.equals("translationY")) break;
                    n = 13;
                    break block36;
                }
                case -1225497657: {
                    if (!string2.equals("translationX")) break;
                    n = 12;
                    break block36;
                }
                case -1249320805: {
                    if (!string2.equals("rotationY")) break;
                    n = 4;
                    break block36;
                }
                case -1249320806: {
                    if (!string2.equals("rotationX")) break;
                    n = 3;
                    break block36;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                return null;
            }
            case 15: {
                return new ProgressSet();
            }
            case 14: {
                return new TranslationZset();
            }
            case 13: {
                return new TranslationYset();
            }
            case 12: {
                return new TranslationXset();
            }
            case 11: {
                return new AlphaSet();
            }
            case 10: {
                return new AlphaSet();
            }
            case 9: {
                return new ScaleYset();
            }
            case 8: {
                return new ScaleXset();
            }
            case 7: {
                return new PathRotate();
            }
            case 6: {
                return new PivotYset();
            }
            case 5: {
                return new PivotXset();
            }
            case 4: {
                return new RotationYset();
            }
            case 3: {
                return new RotationXset();
            }
            case 2: {
                return new RotationSet();
            }
            case 1: {
                return new ElevationSet();
            }
            case 0: 
        }
        return new AlphaSet();
    }

    public float get(float f) {
        return (float)this.mCurveFit.getPos((double)f, 0);
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public float getSlope(float f) {
        return (float)this.mCurveFit.getSlope((double)f, 0);
    }

    public void setPoint(int n, float f) {
        int[] arrn = this.mTimePoints;
        if (arrn.length < this.count + 1) {
            this.mTimePoints = Arrays.copyOf(arrn, arrn.length * 2);
            arrn = this.mValues;
            this.mValues = Arrays.copyOf((float[])arrn, arrn.length * 2);
        }
        arrn = this.mTimePoints;
        int n2 = this.count;
        arrn[n2] = n;
        this.mValues[n2] = f;
        this.count = n2 + 1;
    }

    public abstract void setProperty(View var1, float var2);

    public void setType(String string2) {
        this.mType = string2;
    }

    public void setup(int n) {
        int n2;
        int[] arrn;
        int n3 = this.count;
        if (n3 == 0) {
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, n3 - 1);
        int n4 = 1;
        for (n2 = 1; n2 < this.count; ++n2) {
            arrn = this.mTimePoints;
            n3 = n4;
            if (arrn[n2 - 1] != arrn[n2]) {
                n3 = n4 + 1;
            }
            n4 = n3;
        }
        double[] arrd = new double[n4];
        double[][] arrd2 = new double[n4][1];
        n3 = 0;
        n2 = 0;
        do {
            if (n3 >= this.count) {
                this.mCurveFit = CurveFit.get(n, arrd, arrd2);
                return;
            }
            if (n3 <= 0 || (arrn = this.mTimePoints)[n3] != arrn[n3 - 1]) {
                arrd[n2] = (double)this.mTimePoints[n3] * 0.01;
                arrd2[n2][0] = this.mValues[n3];
                ++n2;
            }
            ++n3;
        } while (true);
    }

    public String toString() {
        String string2 = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        int n = 0;
        while (n < this.count) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("[");
            stringBuilder.append(this.mTimePoints[n]);
            stringBuilder.append(" , ");
            stringBuilder.append(decimalFormat.format(this.mValues[n]));
            stringBuilder.append("] ");
            string2 = stringBuilder.toString();
            ++n;
        }
        return string2;
    }

    static class AlphaSet
    extends SplineSet {
        AlphaSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setAlpha(this.get(f));
        }
    }

    static class CustomSet
    extends SplineSet {
        String mAttributeName;
        SparseArray<ConstraintAttribute> mConstraintAttributeList;
        float[] mTempValues;

        public CustomSet(String string2, SparseArray<ConstraintAttribute> sparseArray) {
            this.mAttributeName = string2.split(",")[1];
            this.mConstraintAttributeList = sparseArray;
        }

        @Override
        public void setPoint(int n, float f) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        public void setPoint(int n, ConstraintAttribute constraintAttribute) {
            this.mConstraintAttributeList.append(n, (Object)constraintAttribute);
        }

        @Override
        public void setProperty(View view, float f) {
            this.mCurveFit.getPos((double)f, this.mTempValues);
            ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).setInterpolatedValue(view, this.mTempValues);
        }

        @Override
        public void setup(int n) {
            int n2 = this.mConstraintAttributeList.size();
            int n3 = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).noOfInterpValues();
            double[] arrd = new double[n2];
            this.mTempValues = new float[n3];
            double[][] arrd2 = new double[n2][n3];
            n3 = 0;
            do {
                if (n3 >= n2) {
                    this.mCurveFit = CurveFit.get(n, arrd, arrd2);
                    return;
                }
                int n4 = this.mConstraintAttributeList.keyAt(n3);
                float[] arrf = (float[])this.mConstraintAttributeList.valueAt(n3);
                arrd[n3] = (double)n4 * 0.01;
                arrf.getValuesToInterpolate(this.mTempValues);
                for (n4 = 0; n4 < (arrf = this.mTempValues).length; ++n4) {
                    arrd2[n3][n4] = arrf[n4];
                }
                ++n3;
            } while (true);
        }
    }

    static class ElevationSet
    extends SplineSet {
        ElevationSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT < 21) return;
            view.setElevation(this.get(f));
        }
    }

    static class PathRotate
    extends SplineSet {
        PathRotate() {
        }

        public void setPathRotate(View view, float f, double d, double d2) {
            view.setRotation(this.get(f) + (float)Math.toDegrees(Math.atan2(d2, d)));
        }

        @Override
        public void setProperty(View view, float f) {
        }
    }

    static class PivotXset
    extends SplineSet {
        PivotXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setPivotX(this.get(f));
        }
    }

    static class PivotYset
    extends SplineSet {
        PivotYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setPivotY(this.get(f));
        }
    }

    static class ProgressSet
    extends SplineSet {
        boolean mNoMethod = false;

        ProgressSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            if (view instanceof MotionLayout) {
                ((MotionLayout)view).setProgress(this.get(f));
                return;
            }
            if (this.mNoMethod) {
                return;
            }
            Method method = null;
            try {
                Method method2;
                method = method2 = view.getClass().getMethod("setProgress", Float.TYPE);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                this.mNoMethod = true;
            }
            if (method == null) return;
            try {
                method.invoke((Object)view, Float.valueOf(this.get(f)));
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.e((String)SplineSet.TAG, (String)"unable to setProgress", (Throwable)invocationTargetException);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)SplineSet.TAG, (String)"unable to setProgress", (Throwable)illegalAccessException);
            }
        }
    }

    static class RotationSet
    extends SplineSet {
        RotationSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setRotation(this.get(f));
        }
    }

    static class RotationXset
    extends SplineSet {
        RotationXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setRotationX(this.get(f));
        }
    }

    static class RotationYset
    extends SplineSet {
        RotationYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setRotationY(this.get(f));
        }
    }

    static class ScaleXset
    extends SplineSet {
        ScaleXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setScaleX(this.get(f));
        }
    }

    static class ScaleYset
    extends SplineSet {
        ScaleYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setScaleY(this.get(f));
        }
    }

    private static class Sort {
        private Sort() {
        }

        static void doubleQuickSort(int[] arrn, float[] arrf, int n, int n2) {
            int[] arrn2 = new int[arrn.length + 10];
            arrn2[0] = n2;
            arrn2[1] = n;
            n = 2;
            while (n > 0) {
                int n3 = arrn2[--n];
                n2 = n - 1;
                int n4 = arrn2[n2];
                n = n2;
                if (n3 >= n4) continue;
                int n5 = Sort.partition(arrn, arrf, n3, n4);
                int n6 = n2 + 1;
                arrn2[n2] = n5 - 1;
                n = n6 + 1;
                arrn2[n6] = n3;
                n2 = n + 1;
                arrn2[n] = n4;
                n = n2 + 1;
                arrn2[n2] = n5 + 1;
            }
        }

        private static int partition(int[] arrn, float[] arrf, int n, int n2) {
            int n3 = arrn[n2];
            int n4 = n;
            do {
                if (n >= n2) {
                    Sort.swap(arrn, arrf, n4, n2);
                    return n4;
                }
                int n5 = n4;
                if (arrn[n] <= n3) {
                    Sort.swap(arrn, arrf, n4, n);
                    n5 = n4 + 1;
                }
                ++n;
                n4 = n5;
            } while (true);
        }

        private static void swap(int[] arrn, float[] arrf, int n, int n2) {
            int n3 = arrn[n];
            arrn[n] = arrn[n2];
            arrn[n2] = n3;
            float f = arrf[n];
            arrf[n] = arrf[n2];
            arrf[n2] = f;
        }
    }

    static class TranslationXset
    extends SplineSet {
        TranslationXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setTranslationX(this.get(f));
        }
    }

    static class TranslationYset
    extends SplineSet {
        TranslationYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setTranslationY(this.get(f));
        }
    }

    static class TranslationZset
    extends SplineSet {
        TranslationZset() {
        }

        @Override
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT < 21) return;
            view.setTranslationZ(this.get(f));
        }
    }

}

