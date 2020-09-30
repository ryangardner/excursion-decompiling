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
import androidx.constraintlayout.motion.widget.KeyCache;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

public abstract class TimeCycleSplineSet {
    private static final int CURVE_OFFSET = 2;
    private static final int CURVE_PERIOD = 1;
    private static final int CURVE_VALUE = 0;
    private static final String TAG = "SplineSet";
    private static float VAL_2PI = 6.2831855f;
    private int count;
    float last_cycle = Float.NaN;
    long last_time;
    private float[] mCache = new float[3];
    protected boolean mContinue = false;
    protected CurveFit mCurveFit;
    protected int[] mTimePoints = new int[10];
    private String mType;
    protected float[][] mValues = new float[10][3];
    protected int mWaveShape = 0;

    static TimeCycleSplineSet makeCustomSpline(String string2, SparseArray<ConstraintAttribute> sparseArray) {
        return new CustomSet(string2, sparseArray);
    }

    /*
     * Unable to fully structure code
     */
    static TimeCycleSplineSet makeSpline(String var0, long var1_1) {
        block28 : {
            switch (var0.hashCode()) {
                default: {
                    break;
                }
                case 92909918: {
                    if (!var0.equals("alpha")) break;
                    var3_2 = 0;
                    break block28;
                }
                case 37232917: {
                    if (!var0.equals("transitionPathRotate")) break;
                    var3_2 = 5;
                    break block28;
                }
                case -4379043: {
                    if (!var0.equals("elevation")) break;
                    var3_2 = 1;
                    break block28;
                }
                case -40300674: {
                    if (!var0.equals("rotation")) break;
                    var3_2 = 2;
                    break block28;
                }
                case -908189617: {
                    if (!var0.equals("scaleY")) break;
                    var3_2 = 7;
                    break block28;
                }
                case -908189618: {
                    if (!var0.equals("scaleX")) break;
                    var3_2 = 6;
                    break block28;
                }
                case -1001078227: {
                    if (!var0.equals("progress")) break;
                    var3_2 = 11;
                    break block28;
                }
                case -1225497655: {
                    if (!var0.equals("translationZ")) break;
                    var3_2 = 10;
                    break block28;
                }
                case -1225497656: {
                    if (!var0.equals("translationY")) break;
                    var3_2 = 9;
                    break block28;
                }
                case -1225497657: {
                    if (!var0.equals("translationX")) break;
                    var3_2 = 8;
                    break block28;
                }
                case -1249320805: {
                    if (!var0.equals("rotationY")) break;
                    var3_2 = 4;
                    break block28;
                }
                case -1249320806: {
                    if (!var0.equals("rotationX")) break;
                    var3_2 = 3;
                    break block28;
                }
            }
            var3_2 = -1;
        }
        switch (var3_2) {
            default: {
                return null;
            }
            case 11: {
                var0 = new ProgressSet();
                ** break;
            }
            case 10: {
                var0 = new TranslationZset();
                ** break;
            }
            case 9: {
                var0 = new TranslationYset();
                ** break;
            }
            case 8: {
                var0 = new TranslationXset();
                ** break;
            }
            case 7: {
                var0 = new ScaleYset();
                ** break;
            }
            case 6: {
                var0 = new ScaleXset();
                ** break;
            }
            case 5: {
                var0 = new PathRotate();
                ** break;
            }
            case 4: {
                var0 = new RotationYset();
                ** break;
            }
            case 3: {
                var0 = new RotationXset();
                ** break;
            }
            case 2: {
                var0 = new RotationSet();
                ** break;
            }
            case 1: {
                var0 = new ElevationSet();
                ** break;
            }
            case 0: 
        }
        var0 = new AlphaSet();
lbl92: // 12 sources:
        var0.setStartTime(var1_1);
        return var0;
    }

    protected float calcWave(float f) {
        switch (this.mWaveShape) {
            default: {
                return (float)Math.sin(f * VAL_2PI);
            }
            case 6: {
                f = 1.0f - Math.abs(f * 4.0f % 4.0f - 2.0f);
                f *= f;
                return 1.0f - f;
            }
            case 5: {
                return (float)Math.cos(f * VAL_2PI);
            }
            case 4: {
                f = (f * 2.0f + 1.0f) % 2.0f;
                return 1.0f - f;
            }
            case 3: {
                return (f * 2.0f + 1.0f) % 2.0f - 1.0f;
            }
            case 2: {
                f = Math.abs(f);
                return 1.0f - f;
            }
            case 1: 
        }
        return Math.signum(f * VAL_2PI);
    }

    public float get(float f, long l, View view, KeyCache keyCache) {
        float f2;
        this.mCurveFit.getPos((double)f, this.mCache);
        float[] arrf = this.mCache;
        f = arrf[1];
        float f3 = f FCMPL 0.0f;
        if (f3 == false) {
            this.mContinue = false;
            return arrf[2];
        }
        if (Float.isNaN(this.last_cycle)) {
            this.last_cycle = f2 = keyCache.getFloatValue((Object)view, this.mType, 0);
            if (Float.isNaN(f2)) {
                this.last_cycle = 0.0f;
            }
        }
        long l2 = this.last_time;
        this.last_cycle = f = (float)(((double)this.last_cycle + (double)(l - l2) * 1.0E-9 * (double)f) % 1.0);
        keyCache.setFloatValue((Object)view, this.mType, 0, f);
        this.last_time = l;
        f = this.mCache[0];
        f2 = this.calcWave(this.last_cycle);
        float f4 = this.mCache[2];
        boolean bl = f != 0.0f || f3 != false;
        this.mContinue = bl;
        return f2 * f + f4;
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public void setPoint(int n, float f, float f2, int n2, float f3) {
        int[] arrn = this.mTimePoints;
        int n3 = this.count++;
        arrn[n3] = n;
        arrn = this.mValues;
        arrn[n3][0] = f;
        arrn[n3][1] = f2;
        arrn[n3][2] = f3;
        this.mWaveShape = Math.max(this.mWaveShape, n2);
    }

    public abstract boolean setProperty(View var1, float var2, long var3, KeyCache var5);

    protected void setStartTime(long l) {
        this.last_time = l;
    }

    public void setType(String string2) {
        this.mType = string2;
    }

    public void setup(int n) {
        int n2;
        int[] arrn;
        int n3 = this.count;
        if (n3 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error no points added to ");
            stringBuilder.append(this.mType);
            Log.e((String)"SplineSet", (String)stringBuilder.toString());
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, n3 - 1);
        n3 = 0;
        for (n2 = 1; n2 < (arrn = this.mTimePoints).length; ++n2) {
            int n4 = n3;
            if (arrn[n2] != arrn[n2 - 1]) {
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        n2 = n3;
        if (n3 == 0) {
            n2 = 1;
        }
        arrn = new double[n2];
        double[][] arrd = new double[n2][3];
        n3 = 0;
        n2 = 0;
        do {
            int[] arrn2;
            if (n3 >= this.count) {
                this.mCurveFit = CurveFit.get(n, arrn, arrd);
                return;
            }
            if (n3 <= 0 || (arrn2 = this.mTimePoints)[n3] != arrn2[n3 - 1]) {
                arrn[n2] = (int)((double)this.mTimePoints[n3] * 0.01);
                arrn2 = arrd[n2];
                float[][] arrf = this.mValues;
                arrn2[0] = (int)arrf[n3][0];
                arrd[n2][1] = arrf[n3][1];
                arrd[n2][2] = arrf[n3][2];
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
    extends TimeCycleSplineSet {
        AlphaSet() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setAlpha(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class CustomSet
    extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        SparseArray<ConstraintAttribute> mConstraintAttributeList;
        float[] mTempValues;
        SparseArray<float[]> mWaveProperties = new SparseArray();

        public CustomSet(String string2, SparseArray<ConstraintAttribute> sparseArray) {
            this.mAttributeName = string2.split(",")[1];
            this.mConstraintAttributeList = sparseArray;
        }

        @Override
        public void setPoint(int n, float f, float f2, int n2, float f3) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void setPoint(int n, ConstraintAttribute constraintAttribute, float f, int n2, float f2) {
            this.mConstraintAttributeList.append(n, (Object)constraintAttribute);
            this.mWaveProperties.append(n, (Object)new float[]{f, f2});
            this.mWaveShape = Math.max(this.mWaveShape, n2);
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache arrf) {
            this.mCurveFit.getPos((double)f, this.mTempValues);
            arrf = this.mTempValues;
            f = arrf[arrf.length - 2];
            float f2 = arrf[arrf.length - 1];
            long l2 = this.last_time;
            this.last_cycle = (float)(((double)this.last_cycle + (double)(l - l2) * 1.0E-9 * (double)f) % 1.0);
            this.last_time = l;
            float f3 = this.calcWave(this.last_cycle);
            this.mContinue = false;
            int n = 0;
            do {
                if (n >= this.mCache.length) {
                    ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).setInterpolatedValue(view, this.mCache);
                    if (f == 0.0f) return this.mContinue;
                    this.mContinue = true;
                    return this.mContinue;
                }
                boolean bl = this.mContinue;
                boolean bl2 = (double)this.mTempValues[n] != 0.0;
                this.mContinue = bl | bl2;
                this.mCache[n] = this.mTempValues[n] * f3 + f2;
                ++n;
            } while (true);
        }

        @Override
        public void setup(int n) {
            int n2 = this.mConstraintAttributeList.size();
            int n3 = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).noOfInterpValues();
            double[] arrd = new double[n2];
            int n4 = n3 + 2;
            this.mTempValues = new float[n4];
            this.mCache = new float[n3];
            double[][] arrd2 = new double[n2][n4];
            n4 = 0;
            do {
                if (n4 >= n2) {
                    this.mCurveFit = CurveFit.get(n, arrd, arrd2);
                    return;
                }
                int n5 = this.mConstraintAttributeList.keyAt(n4);
                float[] arrf = (float[])this.mConstraintAttributeList.valueAt(n4);
                float[] arrf2 = (float[])this.mWaveProperties.valueAt(n4);
                arrd[n4] = (double)n5 * 0.01;
                arrf.getValuesToInterpolate(this.mTempValues);
                for (n5 = 0; n5 < (arrf = this.mTempValues).length; ++n5) {
                    arrd2[n4][n5] = arrf[n5];
                }
                arrd2[n4][n3] = arrf2[0];
                arrd2[n4][n3 + 1] = arrf2[1];
                ++n4;
            } while (true);
        }
    }

    static class ElevationSet
    extends TimeCycleSplineSet {
        ElevationSet() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            if (Build.VERSION.SDK_INT < 21) return this.mContinue;
            view.setElevation(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class PathRotate
    extends TimeCycleSplineSet {
        PathRotate() {
        }

        public boolean setPathRotate(View view, KeyCache keyCache, float f, long l, double d, double d2) {
            view.setRotation(this.get(f, l, view, keyCache) + (float)Math.toDegrees(Math.atan2(d2, d)));
            return this.mContinue;
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            return this.mContinue;
        }
    }

    static class ProgressSet
    extends TimeCycleSplineSet {
        boolean mNoMethod = false;

        ProgressSet() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            if (view instanceof MotionLayout) {
                ((MotionLayout)view).setProgress(this.get(f, l, view, keyCache));
                return this.mContinue;
            }
            if (this.mNoMethod) {
                return false;
            }
            Method method = null;
            try {
                Method method2;
                method = method2 = view.getClass().getMethod("setProgress", Float.TYPE);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                this.mNoMethod = true;
            }
            if (method == null) return this.mContinue;
            try {
                method.invoke((Object)view, Float.valueOf(this.get(f, l, view, keyCache)));
                return this.mContinue;
            }
            catch (InvocationTargetException invocationTargetException) {
                Log.e((String)TimeCycleSplineSet.TAG, (String)"unable to setProgress", (Throwable)invocationTargetException);
                return this.mContinue;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TimeCycleSplineSet.TAG, (String)"unable to setProgress", (Throwable)illegalAccessException);
            }
            return this.mContinue;
        }
    }

    static class RotationSet
    extends TimeCycleSplineSet {
        RotationSet() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setRotation(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class RotationXset
    extends TimeCycleSplineSet {
        RotationXset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setRotationX(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class RotationYset
    extends TimeCycleSplineSet {
        RotationYset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setRotationY(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class ScaleXset
    extends TimeCycleSplineSet {
        ScaleXset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setScaleX(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class ScaleYset
    extends TimeCycleSplineSet {
        ScaleYset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setScaleY(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    private static class Sort {
        private Sort() {
        }

        static void doubleQuickSort(int[] arrn, float[][] arrf, int n, int n2) {
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
                n = n2 + 1;
                arrn2[n2] = n5 - 1;
                n2 = n + 1;
                arrn2[n] = n3;
                n3 = n2 + 1;
                arrn2[n2] = n4;
                n = n3 + 1;
                arrn2[n3] = n5 + 1;
            }
        }

        private static int partition(int[] arrn, float[][] arrf, int n, int n2) {
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

        private static void swap(int[] arrn, float[][] arrf, int n, int n2) {
            int n3 = arrn[n];
            arrn[n] = arrn[n2];
            arrn[n2] = n3;
            arrn = arrf[n];
            arrf[n] = arrf[n2];
            arrf[n2] = arrn;
        }
    }

    static class TranslationXset
    extends TimeCycleSplineSet {
        TranslationXset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setTranslationX(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class TranslationYset
    extends TimeCycleSplineSet {
        TranslationYset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            view.setTranslationY(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

    static class TranslationZset
    extends TimeCycleSplineSet {
        TranslationZset() {
        }

        @Override
        public boolean setProperty(View view, float f, long l, KeyCache keyCache) {
            if (Build.VERSION.SDK_INT < 21) return this.mContinue;
            view.setTranslationZ(this.get(f, l, view, keyCache));
            return this.mContinue;
        }
    }

}

