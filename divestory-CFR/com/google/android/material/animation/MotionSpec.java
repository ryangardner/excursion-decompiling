/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.Log
 *  android.util.Property
 */
package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Property;
import androidx.collection.SimpleArrayMap;
import com.google.android.material.animation.MotionTiming;
import java.util.ArrayList;
import java.util.List;

public class MotionSpec {
    private static final String TAG = "MotionSpec";
    private final SimpleArrayMap<String, PropertyValuesHolder[]> propertyValues = new SimpleArrayMap();
    private final SimpleArrayMap<String, MotionTiming> timings = new SimpleArrayMap();

    private static void addInfoFromAnimator(MotionSpec object, Animator animator2) {
        if (animator2 instanceof ObjectAnimator) {
            animator2 = (ObjectAnimator)animator2;
            ((MotionSpec)object).setPropertyValues(animator2.getPropertyName(), animator2.getValues());
            ((MotionSpec)object).setTiming(animator2.getPropertyName(), MotionTiming.createFromAnimator((ValueAnimator)animator2));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Animator must be an ObjectAnimator: ");
        ((StringBuilder)object).append((Object)animator2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private PropertyValuesHolder[] clonePropertyValuesHolder(PropertyValuesHolder[] arrpropertyValuesHolder) {
        PropertyValuesHolder[] arrpropertyValuesHolder2 = new PropertyValuesHolder[arrpropertyValuesHolder.length];
        int n = 0;
        while (n < arrpropertyValuesHolder.length) {
            arrpropertyValuesHolder2[n] = arrpropertyValuesHolder[n].clone();
            ++n;
        }
        return arrpropertyValuesHolder2;
    }

    public static MotionSpec createFromAttribute(Context context, TypedArray typedArray, int n) {
        if (!typedArray.hasValue(n)) return null;
        if ((n = typedArray.getResourceId(n, 0)) == 0) return null;
        return MotionSpec.createFromResource(context, n);
    }

    public static MotionSpec createFromResource(Context object, int n) {
        try {
            object = AnimatorInflater.loadAnimator((Context)object, (int)n);
            if (object instanceof AnimatorSet) {
                return MotionSpec.createSpecFromAnimators(((AnimatorSet)object).getChildAnimations());
            }
            if (object == null) return null;
            ArrayList<Animator> arrayList = new ArrayList<Animator>();
            arrayList.add((Animator)object);
            return MotionSpec.createSpecFromAnimators(arrayList);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't load animation resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            Log.w((String)TAG, (String)stringBuilder.toString(), (Throwable)exception);
            return null;
        }
    }

    private static MotionSpec createSpecFromAnimators(List<Animator> list) {
        MotionSpec motionSpec = new MotionSpec();
        int n = list.size();
        int n2 = 0;
        while (n2 < n) {
            MotionSpec.addInfoFromAnimator(motionSpec, list.get(n2));
            ++n2;
        }
        return motionSpec;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MotionSpec)) {
            return false;
        }
        object = (MotionSpec)object;
        return this.timings.equals(((MotionSpec)object).timings);
    }

    public <T> ObjectAnimator getAnimator(String string2, T object, Property<T, ?> property) {
        object = ObjectAnimator.ofPropertyValuesHolder(object, (PropertyValuesHolder[])this.getPropertyValues(string2));
        object.setProperty(property);
        this.getTiming(string2).apply((Animator)object);
        return object;
    }

    public PropertyValuesHolder[] getPropertyValues(String string2) {
        if (!this.hasPropertyValues(string2)) throw new IllegalArgumentException();
        return this.clonePropertyValuesHolder(this.propertyValues.get(string2));
    }

    public MotionTiming getTiming(String string2) {
        if (!this.hasTiming(string2)) throw new IllegalArgumentException();
        return this.timings.get(string2);
    }

    public long getTotalDuration() {
        int n = this.timings.size();
        long l = 0L;
        int n2 = 0;
        while (n2 < n) {
            MotionTiming motionTiming = this.timings.valueAt(n2);
            l = Math.max(l, motionTiming.getDelay() + motionTiming.getDuration());
            ++n2;
        }
        return l;
    }

    public boolean hasPropertyValues(String string2) {
        if (this.propertyValues.get(string2) == null) return false;
        return true;
    }

    public boolean hasTiming(String string2) {
        if (this.timings.get(string2) == null) return false;
        return true;
    }

    public int hashCode() {
        return this.timings.hashCode();
    }

    public void setPropertyValues(String string2, PropertyValuesHolder[] arrpropertyValuesHolder) {
        this.propertyValues.put(string2, arrpropertyValuesHolder);
    }

    public void setTiming(String string2, MotionTiming motionTiming) {
        this.timings.put(string2, motionTiming);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\n');
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append('{');
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" timings: ");
        stringBuilder.append(this.timings);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}

