/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Key {
    static final String ALPHA = "alpha";
    static final String CUSTOM = "CUSTOM";
    static final String ELEVATION = "elevation";
    static final String PIVOT_X = "transformPivotX";
    static final String PIVOT_Y = "transformPivotY";
    static final String PROGRESS = "progress";
    static final String ROTATION = "rotation";
    static final String ROTATION_X = "rotationX";
    static final String ROTATION_Y = "rotationY";
    static final String SCALE_X = "scaleX";
    static final String SCALE_Y = "scaleY";
    static final String TRANSITION_PATH_ROTATE = "transitionPathRotate";
    static final String TRANSLATION_X = "translationX";
    static final String TRANSLATION_Y = "translationY";
    static final String TRANSLATION_Z = "translationZ";
    public static int UNSET = -1;
    static final String WAVE_OFFSET = "waveOffset";
    static final String WAVE_PERIOD = "wavePeriod";
    static final String WAVE_VARIES_BY = "waveVariesBy";
    HashMap<String, ConstraintAttribute> mCustomConstraints;
    int mFramePosition;
    int mTargetId;
    String mTargetString;
    protected int mType;

    public Key() {
        int n;
        this.mFramePosition = n = UNSET;
        this.mTargetId = n;
        this.mTargetString = null;
    }

    public abstract void addValues(HashMap<String, SplineSet> var1);

    abstract void getAttributeNames(HashSet<String> var1);

    abstract void load(Context var1, AttributeSet var2);

    boolean matches(String string2) {
        String string3 = this.mTargetString;
        if (string3 == null) return false;
        if (string2 != null) return string2.matches(string3);
        return false;
    }

    public void setInterpolation(HashMap<String, Integer> hashMap) {
    }

    public abstract void setValue(String var1, Object var2);

    boolean toBoolean(Object object) {
        if (!(object instanceof Boolean)) return Boolean.parseBoolean(object.toString());
        return (Boolean)object;
    }

    float toFloat(Object object) {
        if (!(object instanceof Float)) return Float.parseFloat(object.toString());
        return ((Float)object).floatValue();
    }

    int toInt(Object object) {
        if (!(object instanceof Integer)) return Integer.parseInt(object.toString());
        return (Integer)object;
    }
}

