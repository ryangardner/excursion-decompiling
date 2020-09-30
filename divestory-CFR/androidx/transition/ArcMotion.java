/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.util.AttributeSet
 */
package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.PathMotion;
import androidx.transition.Styleable;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion
extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float)Math.tan(Math.toRadians(35.0));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle = 70.0f;
    private float mMaximumTangent = DEFAULT_MAX_TANGENT;
    private float mMinimumHorizontalAngle = 0.0f;
    private float mMinimumHorizontalTangent = 0.0f;
    private float mMinimumVerticalAngle = 0.0f;
    private float mMinimumVerticalTangent = 0.0f;

    public ArcMotion() {
    }

    public ArcMotion(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        context = context.obtainStyledAttributes(object, Styleable.ARC_MOTION);
        object = (XmlPullParser)object;
        this.setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat((TypedArray)context, (XmlPullParser)object, "minimumVerticalAngle", 1, 0.0f));
        this.setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat((TypedArray)context, (XmlPullParser)object, "minimumHorizontalAngle", 0, 0.0f));
        this.setMaximumAngle(TypedArrayUtils.getNamedFloat((TypedArray)context, (XmlPullParser)object, "maximumAngle", 2, 70.0f));
        context.recycle();
    }

    private static float toTangent(float f) {
        if (f < 0.0f) throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        if (f > 90.0f) throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        return (float)Math.tan(Math.toRadians(f / 2.0f));
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    @Override
    public Path getPath(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(f, f2);
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = f5 * f5 + f6 * f6;
        float f8 = (f + f3) / 2.0f;
        float f9 = (f2 + f4) / 2.0f;
        float f10 = 0.25f * f7;
        boolean bl = f2 > f4;
        if (Math.abs(f5) < Math.abs(f6)) {
            f5 = Math.abs(f7 / (f6 * 2.0f));
            if (bl) {
                f5 += f4;
                f6 = f3;
            } else {
                f5 += f2;
                f6 = f;
            }
            f7 = this.mMinimumVerticalTangent;
        } else {
            f6 = f7 / (f5 * 2.0f);
            if (bl) {
                f5 = f2;
                f6 += f;
            } else {
                f6 = f3 - f6;
                f5 = f4;
            }
            f7 = this.mMinimumHorizontalTangent;
        }
        f7 = f10 * f7 * f7;
        float f11 = f8 - f6;
        float f12 = f9 - f5;
        f11 = f11 * f11 + f12 * f12;
        f12 = this.mMaximumTangent;
        f10 = f10 * f12 * f12;
        if (!(f11 < f7)) {
            f7 = f11 > f10 ? f10 : 0.0f;
        }
        f12 = f5;
        f10 = f6;
        if (f7 != 0.0f) {
            f7 = (float)Math.sqrt(f7 / f11);
            f10 = (f6 - f8) * f7 + f8;
            f12 = f9 + f7 * (f5 - f9);
        }
        path.cubicTo((f + f10) / 2.0f, (f2 + f12) / 2.0f, (f10 + f3) / 2.0f, (f12 + f4) / 2.0f, f3, f4);
        return path;
    }

    public void setMaximumAngle(float f) {
        this.mMaximumAngle = f;
        this.mMaximumTangent = ArcMotion.toTangent(f);
    }

    public void setMinimumHorizontalAngle(float f) {
        this.mMinimumHorizontalAngle = f;
        this.mMinimumHorizontalTangent = ArcMotion.toTangent(f);
    }

    public void setMinimumVerticalAngle(float f) {
        this.mMinimumVerticalAngle = f;
        this.mMinimumVerticalTangent = ArcMotion.toTangent(f);
    }
}

