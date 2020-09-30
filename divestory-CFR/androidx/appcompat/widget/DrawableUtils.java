/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.DrawableContainer
 *  android.graphics.drawable.DrawableContainer$DrawableContainerState
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ScaleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.Log;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DrawableUtils {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int[] EMPTY_STATE_SET = new int[0];
    public static final Rect INSETS_NONE = new Rect();
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        if (Build.VERSION.SDK_INT < 18) return;
        try {
            sInsetsClazz = Class.forName("android.graphics.Insets");
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return;
        }
    }

    private DrawableUtils() {
    }

    public static boolean canSafelyMutateDrawable(Drawable arrdrawable) {
        if (Build.VERSION.SDK_INT < 15 && arrdrawable instanceof InsetDrawable) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 15 && arrdrawable instanceof GradientDrawable) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 && arrdrawable instanceof LayerDrawable) {
            return false;
        }
        if (!(arrdrawable instanceof DrawableContainer)) {
            if (arrdrawable instanceof WrappedDrawable) {
                return DrawableUtils.canSafelyMutateDrawable(((WrappedDrawable)arrdrawable).getWrappedDrawable());
            }
            if (arrdrawable instanceof DrawableWrapper) {
                return DrawableUtils.canSafelyMutateDrawable(((DrawableWrapper)arrdrawable).getWrappedDrawable());
            }
            if (!(arrdrawable instanceof ScaleDrawable)) return true;
            return DrawableUtils.canSafelyMutateDrawable(((ScaleDrawable)arrdrawable).getDrawable());
        }
        if (!((arrdrawable = arrdrawable.getConstantState()) instanceof DrawableContainer.DrawableContainerState)) return true;
        arrdrawable = ((DrawableContainer.DrawableContainerState)arrdrawable).getChildren();
        int n = arrdrawable.length;
        int n2 = 0;
        while (n2 < n) {
            if (!DrawableUtils.canSafelyMutateDrawable(arrdrawable[n2])) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    static void fixDrawable(Drawable drawable2) {
        if (Build.VERSION.SDK_INT != 21) return;
        if (!VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable2.getClass().getName())) return;
        DrawableUtils.fixVectorDrawableTinting(drawable2);
    }

    private static void fixVectorDrawableTinting(Drawable drawable2) {
        int[] arrn = drawable2.getState();
        if (arrn != null && arrn.length != 0) {
            drawable2.setState(EMPTY_STATE_SET);
        } else {
            drawable2.setState(CHECKED_STATE_SET);
        }
        drawable2.setState(arrn);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    public static Rect getOpticalBounds(Drawable object) {
        if (Build.VERSION.SDK_INT >= 29) {
            Insets insets = object.getOpticalInsets();
            object = new Rect();
            ((Rect)object).left = insets.left;
            ((Rect)object).right = insets.right;
            ((Rect)object).top = insets.top;
            ((Rect)object).bottom = insets.bottom;
            return object;
        }
        if (sInsetsClazz == null) return INSETS_NONE;
        try {
            object = DrawableCompat.unwrap((Drawable)object);
            Object object2 = object.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(object, new Object[0]);
            if (object2 == null) return INSETS_NONE;
            Rect rect = new Rect();
            object = sInsetsClazz.getFields();
            int n = ((Object)object).length;
            int n2 = 0;
            while (n2 < n) {
                Object object3 = object[n2];
                String string2 = ((Field)object3).getName();
                int n3 = -1;
                switch (string2.hashCode()) {
                    default: {
                        break;
                    }
                    case 108511772: {
                        if (!string2.equals("right")) break;
                        n3 = 2;
                        break;
                    }
                    case 3317767: {
                        if (!string2.equals("left")) break;
                        n3 = 0;
                        break;
                    }
                    case 115029: {
                        if (!string2.equals("top")) break;
                        n3 = 1;
                        break;
                    }
                    case -1383228885: {
                        if (!string2.equals("bottom")) break;
                        n3 = 3;
                    }
                }
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 == 3) {
                                rect.bottom = ((Field)object3).getInt(object2);
                            }
                        } else {
                            rect.right = ((Field)object3).getInt(object2);
                        }
                    } else {
                        rect.top = ((Field)object3).getInt(object2);
                    }
                } else {
                    rect.left = ((Field)object3).getInt(object2);
                }
                ++n2;
            }
            return rect;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)"Couldn't obtain the optical insets. Ignoring.");
        }
        return INSETS_NONE;
    }

    public static PorterDuff.Mode parseTintMode(int n, PorterDuff.Mode mode) {
        if (n == 3) return PorterDuff.Mode.SRC_OVER;
        if (n == 5) return PorterDuff.Mode.SRC_IN;
        if (n == 9) return PorterDuff.Mode.SRC_ATOP;
        switch (n) {
            default: {
                return mode;
            }
            case 16: {
                return PorterDuff.Mode.ADD;
            }
            case 15: {
                return PorterDuff.Mode.SCREEN;
            }
            case 14: 
        }
        return PorterDuff.Mode.MULTIPLY;
    }
}

