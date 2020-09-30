/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 */
package androidx.constraintlayout.motion.widget;

import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public abstract class KeyCycleOscillator {
    private static final String TAG = "KeyCycleOscillator";
    private CurveFit mCurveFit;
    protected ConstraintAttribute mCustom;
    private CycleOscillator mCycleOscillator;
    private String mType;
    public int mVariesBy = 0;
    ArrayList<WavePoint> mWavePoints = new ArrayList();
    private int mWaveShape = 0;

    static KeyCycleOscillator makeSpline(String string2) {
        if (string2.startsWith("CUSTOM")) {
            return new CustomSet();
        }
        int n = -1;
        switch (string2.hashCode()) {
            default: {
                break;
            }
            case 156108012: {
                if (!string2.equals("waveOffset")) break;
                n = 8;
                break;
            }
            case 92909918: {
                if (!string2.equals("alpha")) break;
                n = 0;
                break;
            }
            case 37232917: {
                if (!string2.equals("transitionPathRotate")) break;
                n = 5;
                break;
            }
            case -4379043: {
                if (!string2.equals("elevation")) break;
                n = 1;
                break;
            }
            case -40300674: {
                if (!string2.equals("rotation")) break;
                n = 2;
                break;
            }
            case -797520672: {
                if (!string2.equals("waveVariesBy")) break;
                n = 9;
                break;
            }
            case -908189617: {
                if (!string2.equals("scaleY")) break;
                n = 7;
                break;
            }
            case -908189618: {
                if (!string2.equals("scaleX")) break;
                n = 6;
                break;
            }
            case -1001078227: {
                if (!string2.equals("progress")) break;
                n = 13;
                break;
            }
            case -1225497655: {
                if (!string2.equals("translationZ")) break;
                n = 12;
                break;
            }
            case -1225497656: {
                if (!string2.equals("translationY")) break;
                n = 11;
                break;
            }
            case -1225497657: {
                if (!string2.equals("translationX")) break;
                n = 10;
                break;
            }
            case -1249320805: {
                if (!string2.equals("rotationY")) break;
                n = 4;
                break;
            }
            case -1249320806: {
                if (!string2.equals("rotationX")) break;
                n = 3;
            }
        }
        switch (n) {
            default: {
                return null;
            }
            case 13: {
                return new ProgressSet();
            }
            case 12: {
                return new TranslationZset();
            }
            case 11: {
                return new TranslationYset();
            }
            case 10: {
                return new TranslationXset();
            }
            case 9: {
                return new AlphaSet();
            }
            case 8: {
                return new AlphaSet();
            }
            case 7: {
                return new ScaleYset();
            }
            case 6: {
                return new ScaleXset();
            }
            case 5: {
                return new PathRotateSet();
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
        return (float)this.mCycleOscillator.getValues(f);
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    public float getSlope(float f) {
        return (float)this.mCycleOscillator.getSlope(f);
    }

    public void setPoint(int n, int n2, int n3, float f, float f2, float f3) {
        this.mWavePoints.add(new WavePoint(n, f, f2, f3));
        if (n3 != -1) {
            this.mVariesBy = n3;
        }
        this.mWaveShape = n2;
    }

    public void setPoint(int n, int n2, int n3, float f, float f2, float f3, ConstraintAttribute constraintAttribute) {
        this.mWavePoints.add(new WavePoint(n, f, f2, f3));
        if (n3 != -1) {
            this.mVariesBy = n3;
        }
        this.mWaveShape = n2;
        this.mCustom = constraintAttribute;
    }

    public abstract void setProperty(View var1, float var2);

    public void setType(String string2) {
        this.mType = string2;
    }

    public void setup(float f) {
        int n = this.mWavePoints.size();
        if (n == 0) {
            return;
        }
        Collections.sort(this.mWavePoints, new Comparator<WavePoint>(){

            @Override
            public int compare(WavePoint wavePoint, WavePoint wavePoint2) {
                return Integer.compare(wavePoint.mPosition, wavePoint2.mPosition);
            }
        });
        double[] arrd = new double[n];
        double[][] arrd2 = new double[n][2];
        this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mVariesBy, n);
        Iterator<WavePoint> iterator2 = this.mWavePoints.iterator();
        n = 0;
        do {
            if (!iterator2.hasNext()) {
                this.mCycleOscillator.setup(f);
                this.mCurveFit = CurveFit.get(0, arrd, arrd2);
                return;
            }
            WavePoint wavePoint = iterator2.next();
            arrd[n] = (double)wavePoint.mPeriod * 0.01;
            arrd2[n][0] = wavePoint.mValue;
            arrd2[n][1] = wavePoint.mOffset;
            this.mCycleOscillator.setPoint(n, wavePoint.mPosition, wavePoint.mPeriod, wavePoint.mOffset, wavePoint.mValue);
            ++n;
        } while (true);
    }

    public String toString() {
        String string2 = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        Iterator<WavePoint> iterator2 = this.mWavePoints.iterator();
        while (iterator2.hasNext()) {
            WavePoint wavePoint = iterator2.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("[");
            stringBuilder.append(wavePoint.mPosition);
            stringBuilder.append(" , ");
            stringBuilder.append(decimalFormat.format(wavePoint.mValue));
            stringBuilder.append("] ");
            string2 = stringBuilder.toString();
        }
        return string2;
    }

    public boolean variesByPath() {
        int n = this.mVariesBy;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    static class AlphaSet
    extends KeyCycleOscillator {
        AlphaSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setAlpha(this.get(f));
        }
    }

    static class CustomSet
    extends KeyCycleOscillator {
        float[] value = new float[1];

        CustomSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            this.value[0] = this.get(f);
            this.mCustom.setInterpolatedValue(view, this.value);
        }
    }

    static class CycleOscillator {
        private static final String TAG = "CycleOscillator";
        static final int UNSET = -1;
        CurveFit mCurveFit;
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap();
        float[] mOffset;
        Oscillator mOscillator = new Oscillator();
        float mPathLength;
        float[] mPeriod;
        double[] mPosition;
        float[] mScale;
        double[] mSplineSlopeCache;
        double[] mSplineValueCache;
        float[] mValues;
        private final int mVariesBy;
        int mWaveShape;

        CycleOscillator(int n, int n2, int n3) {
            this.mWaveShape = n;
            this.mVariesBy = n2;
            this.mOscillator.setType(n);
            this.mValues = new float[n3];
            this.mPosition = new double[n3];
            this.mPeriod = new float[n3];
            this.mOffset = new float[n3];
            this.mScale = new float[n3];
        }

        private ConstraintAttribute get(String object, ConstraintAttribute.AttributeType object2) {
            if (!this.mCustomConstraints.containsKey(object)) {
                object2 = new ConstraintAttribute((String)object, (ConstraintAttribute.AttributeType)((Object)object2));
                this.mCustomConstraints.put((String)object, (ConstraintAttribute)object2);
                return object2;
            }
            if (((ConstraintAttribute)(object = this.mCustomConstraints.get(object))).getType() == object2) {
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ConstraintAttribute is already a ");
            ((StringBuilder)object2).append(((ConstraintAttribute)object).getType().name());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }

        public double getSlope(float f) {
            double d;
            Object object = this.mCurveFit;
            if (object != null) {
                d = f;
                ((CurveFit)object).getSlope(d, this.mSplineSlopeCache);
                this.mCurveFit.getPos(d, this.mSplineValueCache);
            } else {
                object = this.mSplineSlopeCache;
                object[0] = 0.0;
                object[1] = 0.0;
            }
            object = this.mOscillator;
            double d2 = f;
            d = ((Oscillator)object).getValue(d2);
            d2 = this.mOscillator.getSlope(d2);
            object = this.mSplineSlopeCache;
            return (double)(object[0] + d * object[1] + d2 * this.mSplineValueCache[1]);
        }

        public double getValues(float f) {
            double[] arrd = this.mCurveFit;
            if (arrd != null) {
                arrd.getPos((double)f, this.mSplineValueCache);
                return this.mSplineValueCache[0] + this.mOscillator.getValue(f) * this.mSplineValueCache[1];
            }
            arrd = this.mSplineValueCache;
            arrd[0] = this.mOffset[0];
            arrd[1] = this.mValues[0];
            return this.mSplineValueCache[0] + this.mOscillator.getValue(f) * this.mSplineValueCache[1];
        }

        public void setPoint(int n, int n2, float f, float f2, float f3) {
            this.mPosition[n] = (double)n2 / 100.0;
            this.mPeriod[n] = f;
            this.mOffset[n] = f2;
            this.mValues[n] = f3;
        }

        public void setup(float f) {
            int n;
            this.mPathLength = f;
            double[][] arrd = new double[this.mPosition.length][2];
            float[] arrf = this.mValues;
            this.mSplineValueCache = new double[arrf.length + 1];
            this.mSplineSlopeCache = new double[arrf.length + 1];
            if (this.mPosition[0] > 0.0) {
                this.mOscillator.addPoint(0.0, this.mPeriod[0]);
            }
            if ((arrf = this.mPosition)[n = arrf.length - 1] < 1.0) {
                this.mOscillator.addPoint(1.0, this.mPeriod[n]);
            }
            for (n = 0; n < arrd.length; ++n) {
                arrd[n][0] = this.mOffset[n];
                for (int i = 0; i < (arrf = this.mValues).length; ++i) {
                    arrd[i][1] = arrf[i];
                }
                this.mOscillator.addPoint(this.mPosition[n], this.mPeriod[n]);
            }
            this.mOscillator.normalize();
            arrf = this.mPosition;
            if (arrf.length > 1) {
                this.mCurveFit = CurveFit.get(0, arrf, arrd);
                return;
            }
            this.mCurveFit = null;
        }
    }

    static class ElevationSet
    extends KeyCycleOscillator {
        ElevationSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT < 21) return;
            view.setElevation(this.get(f));
        }
    }

    private static class IntDoubleSort {
        private IntDoubleSort() {
        }

        private static int partition(int[] arrn, float[] arrf, int n, int n2) {
            int n3 = arrn[n2];
            int n4 = n;
            do {
                if (n >= n2) {
                    IntDoubleSort.swap(arrn, arrf, n4, n2);
                    return n4;
                }
                int n5 = n4;
                if (arrn[n] <= n3) {
                    IntDoubleSort.swap(arrn, arrf, n4, n);
                    n5 = n4 + 1;
                }
                ++n;
                n4 = n5;
            } while (true);
        }

        static void sort(int[] arrn, float[] arrf, int n, int n2) {
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
                int n5 = IntDoubleSort.partition(arrn, arrf, n3, n4);
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

        private static void swap(int[] arrn, float[] arrf, int n, int n2) {
            int n3 = arrn[n];
            arrn[n] = arrn[n2];
            arrn[n2] = n3;
            float f = arrf[n];
            arrf[n] = arrf[n2];
            arrf[n2] = f;
        }
    }

    private static class IntFloatFloatSort {
        private IntFloatFloatSort() {
        }

        private static int partition(int[] arrn, float[] arrf, float[] arrf2, int n, int n2) {
            int n3 = arrn[n2];
            int n4 = n;
            do {
                if (n >= n2) {
                    IntFloatFloatSort.swap(arrn, arrf, arrf2, n4, n2);
                    return n4;
                }
                int n5 = n4;
                if (arrn[n] <= n3) {
                    IntFloatFloatSort.swap(arrn, arrf, arrf2, n4, n);
                    n5 = n4 + 1;
                }
                ++n;
                n4 = n5;
            } while (true);
        }

        static void sort(int[] arrn, float[] arrf, float[] arrf2, int n, int n2) {
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
                int n5 = IntFloatFloatSort.partition(arrn, arrf, arrf2, n3, n4);
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

        private static void swap(int[] arrn, float[] arrf, float[] arrf2, int n, int n2) {
            int n3 = arrn[n];
            arrn[n] = arrn[n2];
            arrn[n2] = n3;
            float f = arrf[n];
            arrf[n] = arrf[n2];
            arrf[n2] = f;
            f = arrf2[n];
            arrf2[n] = arrf2[n2];
            arrf2[n2] = f;
        }
    }

    static class PathRotateSet
    extends KeyCycleOscillator {
        PathRotateSet() {
        }

        public void setPathRotate(View view, float f, double d, double d2) {
            view.setRotation(this.get(f) + (float)Math.toDegrees(Math.atan2(d2, d)));
        }

        @Override
        public void setProperty(View view, float f) {
        }
    }

    static class ProgressSet
    extends KeyCycleOscillator {
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
                Log.e((String)KeyCycleOscillator.TAG, (String)"unable to setProgress", (Throwable)invocationTargetException);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)KeyCycleOscillator.TAG, (String)"unable to setProgress", (Throwable)illegalAccessException);
            }
        }
    }

    static class RotationSet
    extends KeyCycleOscillator {
        RotationSet() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setRotation(this.get(f));
        }
    }

    static class RotationXset
    extends KeyCycleOscillator {
        RotationXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setRotationX(this.get(f));
        }
    }

    static class RotationYset
    extends KeyCycleOscillator {
        RotationYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setRotationY(this.get(f));
        }
    }

    static class ScaleXset
    extends KeyCycleOscillator {
        ScaleXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setScaleX(this.get(f));
        }
    }

    static class ScaleYset
    extends KeyCycleOscillator {
        ScaleYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setScaleY(this.get(f));
        }
    }

    static class TranslationXset
    extends KeyCycleOscillator {
        TranslationXset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setTranslationX(this.get(f));
        }
    }

    static class TranslationYset
    extends KeyCycleOscillator {
        TranslationYset() {
        }

        @Override
        public void setProperty(View view, float f) {
            view.setTranslationY(this.get(f));
        }
    }

    static class TranslationZset
    extends KeyCycleOscillator {
        TranslationZset() {
        }

        @Override
        public void setProperty(View view, float f) {
            if (Build.VERSION.SDK_INT < 21) return;
            view.setTranslationZ(this.get(f));
        }
    }

    static class WavePoint {
        float mOffset;
        float mPeriod;
        int mPosition;
        float mValue;

        public WavePoint(int n, float f, float f2, float f3) {
            this.mPosition = n;
            this.mValue = f3;
            this.mOffset = f2;
            this.mPeriod = f;
        }
    }

}

