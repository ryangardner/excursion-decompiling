/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.LayoutTransition
 *  android.util.Log
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.util.Log;
import android.view.ViewGroup;
import androidx.transition.R;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtilsApi14 {
    private static final int LAYOUT_TRANSITION_CHANGING = 4;
    private static final String TAG = "ViewGroupUtilsApi14";
    private static Method sCancelMethod;
    private static boolean sCancelMethodFetched;
    private static LayoutTransition sEmptyLayoutTransition;
    private static Field sLayoutSuppressedField;
    private static boolean sLayoutSuppressedFieldFetched;

    private ViewGroupUtilsApi14() {
    }

    private static void cancelLayoutTransition(LayoutTransition layoutTransition) {
        Method method;
        if (!sCancelMethodFetched) {
            try {
                sCancelMethod = method = LayoutTransition.class.getDeclaredMethod("cancel", new Class[0]);
                method.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Failed to access cancel method by reflection");
            }
            sCancelMethodFetched = true;
        }
        if ((method = sCancelMethod) == null) return;
        try {
            method.invoke((Object)layoutTransition, new Object[0]);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.i((String)TAG, (String)"Failed to invoke cancel method by reflection");
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)TAG, (String)"Failed to access cancel method by reflection");
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    static void suppressLayout(ViewGroup viewGroup, boolean bl) {
        Object object;
        block13 : {
            object = sEmptyLayoutTransition;
            boolean bl2 = false;
            boolean bl3 = false;
            if (object == null) {
                object = new LayoutTransition(){

                    public boolean isChangingLayout() {
                        return true;
                    }
                };
                sEmptyLayoutTransition = object;
                object.setAnimator(2, null);
                sEmptyLayoutTransition.setAnimator(0, null);
                sEmptyLayoutTransition.setAnimator(1, null);
                sEmptyLayoutTransition.setAnimator(3, null);
                sEmptyLayoutTransition.setAnimator(4, null);
            }
            if (bl) {
                object = viewGroup.getLayoutTransition();
                if (object != null) {
                    if (object.isRunning()) {
                        ViewGroupUtilsApi14.cancelLayoutTransition((LayoutTransition)object);
                    }
                    if (object != sEmptyLayoutTransition) {
                        viewGroup.setTag(R.id.transition_layout_save, object);
                    }
                }
                viewGroup.setLayoutTransition(sEmptyLayoutTransition);
                return;
            }
            viewGroup.setLayoutTransition(null);
            if (!sLayoutSuppressedFieldFetched) {
                try {
                    object = ViewGroup.class.getDeclaredField("mLayoutSuppressed");
                    sLayoutSuppressedField = object;
                    ((Field)object).setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.i((String)TAG, (String)"Failed to access mLayoutSuppressed field by reflection");
                }
                sLayoutSuppressedFieldFetched = true;
            }
            object = sLayoutSuppressedField;
            bl = bl2;
            if (object == null) break block13;
            bl = ((Field)object).getBoolean((Object)viewGroup);
            if (!bl) break block13;
            try {
                sLayoutSuppressedField.setBoolean((Object)viewGroup, false);
            }
            catch (IllegalAccessException illegalAccessException) {
                block14 : {
                    break block14;
                    catch (IllegalAccessException illegalAccessException2) {
                        bl = bl3;
                    }
                }
                Log.i((String)TAG, (String)"Failed to get mLayoutSuppressed field by reflection");
            }
        }
        if (bl) {
            viewGroup.requestLayout();
        }
        if ((object = (LayoutTransition)viewGroup.getTag(R.id.transition_layout_save)) == null) return;
        viewGroup.setTag(R.id.transition_layout_save, null);
        viewGroup.setLayoutTransition((LayoutTransition)object);
    }

}

